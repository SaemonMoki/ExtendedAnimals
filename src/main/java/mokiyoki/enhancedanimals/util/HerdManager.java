package mokiyoki.enhancedanimals.util;

import mokiyoki.enhancedanimals.config.GeneticAnimalsConfig;
import mokiyoki.enhancedanimals.entity.EnhancedAnimalAbstract;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

public final class HerdManager {

    /** Radius used when searching for herd-mates on spawn / join checks. */
    public static final double JOIN_RADIUS = 10.0;

    /** Entity further than this from its herd centre will try to return or leave. */
    public static final double STRAY_DISTANCE = 50.0;

    /** Entity closer than this to a foreign herd centre may defect to it. */
    public static final double DEFECT_APPROACH_DISTANCE = 10.0;

    /** Radius within which separation pushes entities apart. */
    public static final double SEPARATION_RADIUS = 4.0;

    /** When closer than this to the personal wander target, cohesion is suppressed. */
    public static final double COHESION_DEAD_ZONE = 6.0;

    /** How far ahead of the herd centre the shared wander target sits. */
    public static final double WANDER_DISTANCE = 18.0;

    /** Per-entity random spread around the shared wander target. */
    public static final double JITTER_RADIUS = 6.0;

    /** Length of the pause between moving bursts, in ticks. */
    private static final long IDLE_DURATION_TICKS = 100L;

    // Separate salts so the phase-offset RNG and direction RNG don't share state.
    private static final long MOVE_SEED_PRIME = 0x9e3779b97f4a7c15L;
    private static final long DIR_SEED_SALT   = 0xDEADBEEFCAFEL;

    /** How long a derived herd snapshot stays valid, in ticks. */
    private static final long HERD_SNAPSHOT_TTL_TICKS = 10L;

    /** Once the snapshot cache grows past this many distinct herds, expired entries are swept. */
    private static final int HERD_SNAPSHOT_SWEEP_THRESHOLD = 200;

    /** Once the roster map grows past this many herds, dead ids and empty herds are swept. */
    private static final int HERD_ROSTER_SWEEP_THRESHOLD = 500;

    /** Sentinel for "no member of this herd is currently being led". */
    private static final int NO_MEMBER = -1;

    /**
     * Authoritative membership, by herd id. Holds entity ids rather than entity references, so
     * nothing here pins an entity in memory or goes stale into a dangling reference. Maintained
     * from EnhancedAnimalAbstract.setHerdId, which every join, leave, defect, spawn and load
     * already passes through.
     */
    private static final Map<UUID, Set<Integer>> herdRosters = new HashMap<>();

    /** Positions and leashed-member lookup derived from the rosters, cached for a few ticks. */
    private static final Map<UUID, HerdSnapshot> herdSnapshotCache = new HashMap<>();

    private HerdManager() {}

    // ---------------------------------------------------------------
    // Roster upkeep
    // ---------------------------------------------------------------

    /**
     * Moves an animal between herd rosters. Called from setHerdId, so it covers spawning,
     * straying, defecting and loading from disk (entity ids aren't persisted, but a chunk
     * reloading re-registers its animals through the same setter).
     */
    public static void updateHerdMembership(EnhancedAnimalAbstract entity,
                                            @Nullable UUID previousHerdId,
                                            @Nullable UUID newHerdId) {
        if (entity.level.isClientSide || Objects.equals(previousHerdId, newHerdId)) return;

        if (previousHerdId != null) {
            Set<Integer> previous = herdRosters.get(previousHerdId);
            if (previous != null) {
                previous.remove(entity.getId());
                if (previous.isEmpty()) herdRosters.remove(previousHerdId);
            }
            herdSnapshotCache.remove(previousHerdId);
        }

        if (newHerdId != null) {
            herdRosters.computeIfAbsent(newHerdId, id -> new HashSet<>()).add(entity.getId());
            herdSnapshotCache.remove(newHerdId);
        }

        if (herdRosters.size() > HERD_ROSTER_SWEEP_THRESHOLD) {
            sweepRosters(entity);
        }
    }

    /**
     * Re-registers an animal that has fallen out of its own herd's roster. Rosters drop ids
     * that don't resolve, which can catch an animal mid-load before it has been added to the
     * level, so members re-assert themselves rather than silently going missing from their
     * own herd. Costs a map lookup and a set lookup when everything is already correct.
     */
    public static void ensureRegistered(EnhancedAnimalAbstract entity) {
        if (entity.level.isClientSide) return;
        UUID herdId = entity.getHerdId();
        if (herdId == null) return;

        Set<Integer> roster = herdRosters.get(herdId);
        if (roster != null && roster.contains(entity.getId())) return;

        herdRosters.computeIfAbsent(herdId, id -> new HashSet<>()).add(entity.getId());
        herdSnapshotCache.remove(herdId);
    }

    /**
     * Drops ids that no longer resolve to a living member, and herds left empty by that. Only
     * needed for herds nothing is querying any more - a herd with a loaded member prunes itself
     * on read - so this runs off the back of membership changes once the map gets large.
     */
    private static void sweepRosters(EnhancedAnimalAbstract context) {
        Iterator<Map.Entry<UUID, Set<Integer>>> herds = herdRosters.entrySet().iterator();
        while (herds.hasNext()) {
            Map.Entry<UUID, Set<Integer>> entry = herds.next();
            UUID herdId = entry.getKey();
            Set<Integer> roster = entry.getValue();

            roster.removeIf(memberId -> resolveMember(context, herdId, memberId) == null);
            if (roster.isEmpty()) {
                herds.remove();
                herdSnapshotCache.remove(herdId);
            }
        }
    }

    /** The live animal behind a roster id, or null if it is gone, unloaded, or has moved herds. */
    @Nullable
    private static EnhancedAnimalAbstract resolveMember(EnhancedAnimalAbstract context, UUID herdId, int memberId) {
        return context.level.getEntity(memberId) instanceof EnhancedAnimalAbstract member
                && member.isAlive()
                && herdId.equals(member.getHerdId())
                ? member : null;
    }

    // ---------------------------------------------------------------
    // Spawn-time initialisation
    // ---------------------------------------------------------------

    /**
     * Called once when the entity first spawns (or loads with no herd ID).
     *
     * 1. If any nearby animal already has a herd ID, join the largest such herd.
     * 2. Otherwise, create a new UUID and assign it to all nearby un-herded animals
     *    too, so a group spawning together starts as one herd rather than solo herds.
     * 3. If truly alone, start a solo herd.
     */
    @SuppressWarnings("unchecked")
    public static <T extends EnhancedAnimalAbstract> void initialiseHerd(T entity) {
        if (entity.level.isClientSide) return;

        // Discovery is genuinely spatial - at this point we don't know which herds are nearby,
        // so there's no roster to consult yet.
        AABB searchBox = entity.getBoundingBox().inflate(JOIN_RADIUS);
        List<T> nearby = (List<T>) entity.level.getEntitiesOfClass(
                entity.getClass(), searchBox, m -> m != entity && m.isAlive());

        // Dedup by herd id - the size of a herd depends on the id, not on which member of it
        // we happened to see first.
        Set<UUID> seenHerds = new HashSet<>();
        UUID bestHerd = null;
        int bestSize = 0;
        for (T other : nearby) {
            UUID otherId = other.getHerdId();
            if (otherId == null || !seenHerds.add(otherId)) continue;
            int size = countHerdSize(entity, otherId, JOIN_RADIUS * 4);
            if (size > bestSize) {
                bestSize = size;
                bestHerd = otherId;
            }
        }

        if (bestHerd != null) {
            entity.setHerdId(bestHerd);
        } else {
            // No herded animals nearby — group self and all nearby un-herded animals together
            UUID newHerd = UUID.randomUUID();
            entity.setHerdId(newHerd);
            for (T other : nearby) {
                if (other.getHerdId() == null) {
                    other.setHerdId(newHerd);
                }
            }
        }
    }

    // ---------------------------------------------------------------
    // Periodic membership checks (run on their own schedule in HerdGoal)
    // ---------------------------------------------------------------

    /**
     * Leaves the current herd and creates a brand-new one if the entity
     * is over STRAY_DISTANCE blocks from the herd centre.
     *
     * @return true if the entity left its herd.
     */
    public static <T extends EnhancedAnimalAbstract> boolean checkLeaveHerd(T entity) {
        if (entity.level.isClientSide) return false;
        UUID herdId = entity.getHerdId();
        if (herdId == null) return false;

        Vec3 centre = computeHerdCentre(entity, herdId);
        if (centre == null) {
            entity.setHerdId(UUID.randomUUID());
            return true;
        }

        if (entity.position().distanceTo(centre) > STRAY_DISTANCE) {
            entity.setHerdId(UUID.randomUUID());
            return true;
        }
        return false;
    }

    /**
     * Considers switching to a nearby foreign herd if:
     *  - the foreign herd is larger than the entity's current herd, OR
     *  - the entity is further than STRAY_DISTANCE from its own centre
     *    AND within DEFECT_APPROACH_DISTANCE of the foreign centre.
     *
     * @return true if the entity switched herds.
     */
    public static <T extends EnhancedAnimalAbstract> boolean checkJoinNearbyHerd(T entity) {
        if (entity.level.isClientSide) return false;
        UUID currentHerdId = entity.getHerdId();
        if (currentHerdId == null) return false;

        // Which herds are near me is a discovery question, so this one stays a spatial scan.
        List<T> nearby = getNearbyAnimals(entity, entity.getClass(), JOIN_RADIUS * 3);

        int currentSize = countHerdSize(entity, currentHerdId, JOIN_RADIUS * 4);
        Vec3 ownCentre = computeHerdCentre(entity, currentHerdId);
        double distToOwn = ownCentre != null ? entity.position().distanceTo(ownCentre) : Double.MAX_VALUE;

        Set<UUID> seenForeignHerds = new HashSet<>();
        UUID bestForeignId = null;
        double bestForeignDist = Double.MAX_VALUE;

        for (T other : nearby) {
            UUID otherId = other.getHerdId();
            if (otherId == null || otherId.equals(currentHerdId)) continue;
            if (!seenForeignHerds.add(otherId)) continue;

            Vec3 foreignCentre = computeHerdCentre(entity, otherId);
            if (foreignCentre == null) continue;
            double distToForeign = entity.position().distanceTo(foreignCentre);

            int foreignSize = countHerdSize(entity, otherId, JOIN_RADIUS * 4);
            boolean largerHerd = foreignSize > currentSize;
            boolean strayedAndClose = distToOwn > STRAY_DISTANCE && distToForeign < DEFECT_APPROACH_DISTANCE;

            if ((largerHerd || strayedAndClose) && distToForeign < bestForeignDist) {
                bestForeignDist = distToForeign;
                bestForeignId = otherId;
            }
        }

        if (bestForeignId != null) {
            entity.setHerdId(bestForeignId);
            return true;
        }
        return false;
    }

    // ---------------------------------------------------------------
    // Herd phase query (used by HerdGoal.canUse / canContinueToUse)
    // ---------------------------------------------------------------

    /**
     * Returns true if this herd is in a moving burst at the given game time.
     * All members of the same herd return the same value for the same game time,
     * regardless of when they individually started the goal.
     */
    public static boolean isHerdMoving(UUID herdId, long gameTime) {
        long moveDuration = moveDurationTicks();
        long cycleLen = moveDuration + IDLE_DURATION_TICKS;
        return cyclePosition(herdId, gameTime, cycleLen) < moveDuration;
    }

    /** Length of a moving burst, in ticks — configurable via herdMoveDurationTicks. */
    private static long moveDurationTicks() {
        return GeneticAnimalsConfig.COMMON.herdMoveDurationTicks.get();
    }

    /** Fixed per-herd offset so different herds' move/idle bursts don't sync up. */
    private static long herdPhaseOffset(UUID herdId, long cycleLen) {
        return Math.floorMod((long) herdId.hashCode(), cycleLen);
    }

    /** Position within the current move+idle cycle, in [0, cycleLen). */
    private static long cyclePosition(UUID herdId, long gameTime, long cycleLen) {
        return Math.floorMod(gameTime + herdPhaseOffset(herdId, cycleLen), cycleLen);
    }

    /** Which cycle (i.e. which moving burst) the given game time falls in. */
    private static long cycleIndex(UUID herdId, long gameTime, long cycleLen) {
        return Math.floorDiv(gameTime + herdPhaseOffset(herdId, cycleLen), cycleLen);
    }

    // ---------------------------------------------------------------
    // Herd steering (called every PATH_RECALC_INTERVAL ticks in HerdGoal)
    // ---------------------------------------------------------------

    /**
     * Computes a normalised steering vector for this entity.
     * Returns null when the entity is already within the dead zone of its personal target.
     */
    @Nullable
    public static <T extends EnhancedAnimalAbstract> Vec3 computeHerdSteering(T entity) {
        if (entity.level.isClientSide) return null;

        UUID herdId = entity.getHerdId();
        if (herdId == null) return null;

        Vec3 selfPos = entity.position();
        List<Vec3> mates = nearbyHerdMatePositions(entity, herdId, JOIN_RADIUS * 3);

        // Herd centre including self
        Vec3 centre = selfPos;
        int count = 1;
        for (Vec3 p : mates) {
            centre = centre.add(p);
            count++;
        }
        centre = centre.scale(1.0 / count);

        // Shared wander direction — one per moving burst
        long gameTime = entity.level.getGameTime();
        long cycleLen = moveDurationTicks() + IDLE_DURATION_TICKS;
        long cycleIndex = cycleIndex(herdId, gameTime, cycleLen);
        Vec3 wanderDir = dirForCycle(herdId, cycleIndex);

        // Per-entity jitter — stable within a burst, spreads members naturally
        Random jitterRng = new Random(entity.getId() ^ cycleIndex);
        Vec3 jitter = new Vec3(
                (jitterRng.nextDouble() - 0.5) * JITTER_RADIUS * 2.0,
                0.0,
                (jitterRng.nextDouble() - 0.5) * JITTER_RADIUS * 2.0
        );

        Vec3 personalTarget = centre.add(wanderDir.scale(WANDER_DISTANCE)).add(jitter);

        // Cohesion: steer toward personal target only if outside the dead zone
        Vec3 cohesion = Vec3.ZERO;
        if (selfPos.distanceTo(personalTarget) > COHESION_DEAD_ZONE) {
            cohesion = personalTarget.subtract(selfPos).normalize();
        }

        Vec3 separation = computeSeparation(selfPos, mates);

        Vec3 steering = cohesion.scale(0.6).add(separation.scale(1.5));
        double len = steering.length();
        return len > 1e-6 ? steering.normalize() : null;
    }

    // ---------------------------------------------------------------
    // Leash following
    // ---------------------------------------------------------------

    /**
     * The same-herd member currently being led on a lead, or null if there isn't one.
     * Answered from the herd's roster, so it costs an O(1) snapshot lookup on the hot path
     * rather than an area search.
     */
    @Nullable
    public static EnhancedAnimalAbstract findLeashedHerdMate(EnhancedAnimalAbstract entity) {
        if (entity.level.isClientSide) return null;
        UUID herdId = entity.getHerdId();
        if (herdId == null) return null;

        int leashedId = herdSnapshot(entity, herdId, entity.level.getGameTime()).leashedMemberId;
        if (leashedId == NO_MEMBER || leashedId == entity.getId()) return null;

        // Re-check the resolved mate instead of trusting the snapshot, so followers stop on the
        // same tick the lead comes off rather than waiting for the snapshot to expire.
        if (!(entity.level.getEntity(leashedId) instanceof EnhancedAnimalAbstract mate)) return null;
        if (!mate.isAlive() || !mate.isLedByEntity() || !herdId.equals(mate.getHerdId())) return null;
        return mate;
    }

    /**
     * Steers toward a herd-mate that's being led, using the same cohesion/separation
     * blend as computeHerdSteering but targeting the leader directly instead of the
     * shared wander target. Returns null once within the cohesion dead zone of the leader.
     */
    @Nullable
    public static <T extends EnhancedAnimalAbstract> Vec3 computeLeaderSteering(T entity, T leader) {
        if (entity.level.isClientSide) return null;

        UUID herdId = entity.getHerdId();
        Vec3 selfPos = entity.position();
        Vec3 leaderPos = leader.position();

        Vec3 cohesion = Vec3.ZERO;
        if (selfPos.distanceTo(leaderPos) > COHESION_DEAD_ZONE) {
            cohesion = leaderPos.subtract(selfPos).normalize();
        }

        List<Vec3> mates = herdId != null
                ? nearbyHerdMatePositions(entity, herdId, JOIN_RADIUS * 3)
                : List.of();
        Vec3 separation = computeSeparation(selfPos, mates);

        Vec3 steering = cohesion.scale(0.6).add(separation.scale(1.5));
        double len = steering.length();
        return len > 1e-6 ? steering.normalize() : null;
    }

    /** Push away from too-close herd-mates; shared by wander and leash-follow steering. */
    private static Vec3 computeSeparation(Vec3 selfPos, List<Vec3> matePositions) {
        Vec3 separation = Vec3.ZERO;
        for (Vec3 p : matePositions) {
            Vec3 diff = selfPos.subtract(p);
            double dist = diff.length();
            if (dist > 0.0 && dist < SEPARATION_RADIUS) {
                separation = separation.add(diff.normalize().scale(1.0 - (dist / SEPARATION_RADIUS)));
            }
        }
        return separation;
    }

    // ---------------------------------------------------------------
    // Snapshots derived from the rosters
    //
    // The first member of a herd to ask resolves the roster; everyone else reuses the result
    // for the next few ticks. Resolution is an O(1) id lookup per member rather than a spatial
    // scan, and an id that no longer resolves to a living member of this herd is dropped from
    // the roster there and then - so death, unloading and defection all clean up on read
    // without needing hooks of their own.
    // ---------------------------------------------------------------

    private static final class HerdSnapshot {
        final long tick;
        final Map<Integer, Vec3> positionsById;
        final int leashedMemberId;

        HerdSnapshot(long tick, Map<Integer, Vec3> positionsById, int leashedMemberId) {
            this.tick = tick;
            this.positionsById = positionsById;
            this.leashedMemberId = leashedMemberId;
        }
    }

    private static HerdSnapshot herdSnapshot(EnhancedAnimalAbstract entity, UUID herdId, long gameTime) {
        HerdSnapshot cached = herdSnapshotCache.get(herdId);
        if (cached != null && isFresh(cached, gameTime)) {
            return cached;
        }

        Map<Integer, Vec3> positions = new HashMap<>();
        int leashedMemberId = NO_MEMBER;

        Set<Integer> roster = herdRosters.get(herdId);
        if (roster != null) {
            Iterator<Integer> members = roster.iterator();
            while (members.hasNext()) {
                int memberId = members.next();
                EnhancedAnimalAbstract member = resolveMember(entity, herdId, memberId);
                if (member == null) {
                    members.remove();
                    continue;
                }
                positions.put(memberId, member.position());
                if (leashedMemberId == NO_MEMBER && member.isLedByEntity()) {
                    leashedMemberId = memberId;
                }
            }
            if (roster.isEmpty()) herdRosters.remove(herdId);
        }

        if (herdSnapshotCache.size() > HERD_SNAPSHOT_SWEEP_THRESHOLD) {
            sweepExpiredSnapshots(gameTime);
        }
        HerdSnapshot snapshot = new HerdSnapshot(gameTime, positions, leashedMemberId);
        herdSnapshotCache.put(herdId, snapshot);
        return snapshot;
    }

    /**
     * Bounded at both ends: an entry dated in the future means the game time went backwards
     * (a different world loaded in the same session), so it belongs to a world we're no longer
     * in and must not be trusted rather than being treated as indefinitely fresh.
     */
    private static boolean isFresh(HerdSnapshot snapshot, long gameTime) {
        long age = gameTime - snapshot.tick;
        return age >= 0 && age < HERD_SNAPSHOT_TTL_TICKS;
    }

    private static void sweepExpiredSnapshots(long gameTime) {
        herdSnapshotCache.values().removeIf(s -> !isFresh(s, gameTime));
    }

    /** Herd-mate positions, excluding the querying entity, within radius of its current box. */
    private static List<Vec3> nearbyHerdMatePositions(EnhancedAnimalAbstract entity, UUID herdId, double radius) {
        Map<Integer, Vec3> positions = herdSnapshot(entity, herdId, entity.level.getGameTime()).positionsById;
        AABB box = entity.getBoundingBox().inflate(radius);
        List<Vec3> result = new ArrayList<>();
        for (Map.Entry<Integer, Vec3> e : positions.entrySet()) {
            if (e.getKey() == entity.getId()) continue;
            Vec3 p = e.getValue();
            if (box.contains(p.x, p.y, p.z)) result.add(p);
        }
        return result;
    }

    // ---------------------------------------------------------------
    // Query helpers
    // ---------------------------------------------------------------

    /**
     * Geometric centre of all living same-herd members excluding the querying entity,
     * or null if none are within STRAY_DISTANCE.
     */
    @Nullable
    private static Vec3 computeHerdCentre(EnhancedAnimalAbstract entity, UUID herdId) {
        List<Vec3> positions = nearbyHerdMatePositions(entity, herdId, STRAY_DISTANCE);
        if (positions.isEmpty()) return null;

        double sx = 0, sy = 0, sz = 0;
        for (Vec3 p : positions) {
            sx += p.x; sy += p.y; sz += p.z;
        }
        int n = positions.size();
        return new Vec3(sx / n, sy / n, sz / n);
    }

    private static Vec3 dirForCycle(UUID herdId, long cycleIndex) {
        long seed = ((long) herdId.hashCode() ^ (cycleIndex * MOVE_SEED_PRIME)) ^ DIR_SEED_SALT;
        double angle = new Random(seed).nextDouble() * 2.0 * Math.PI;
        return new Vec3(Math.cos(angle), 0.0, Math.sin(angle));
    }

    /** All nearby living animals of the same class that have any herd ID (for join/leave checks). */
    @SuppressWarnings("unchecked")
    private static <T extends EnhancedAnimalAbstract> List<T> getNearbyAnimals(
            T entity, Class<? extends EnhancedAnimalAbstract> clazz, double radius) {
        AABB box = entity.getBoundingBox().inflate(radius);
        return (List<T>) entity.level.getEntitiesOfClass(
                clazz, box, m -> m != entity && m.isAlive() && m.getHerdId() != null);
    }

    /** How many members of the given herd are within radius of this entity. */
    private static int countHerdSize(EnhancedAnimalAbstract entity, UUID herdId, double radius) {
        Map<Integer, Vec3> positions = herdSnapshot(entity, herdId, entity.level.getGameTime()).positionsById;
        AABB box = entity.getBoundingBox().inflate(radius);
        int count = 0;
        for (Vec3 p : positions.values()) {
            if (box.contains(p.x, p.y, p.z)) count++;
        }
        return count;
    }
}
