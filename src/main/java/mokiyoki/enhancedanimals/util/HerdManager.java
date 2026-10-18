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

    // Radius used when searching for herd-mates on spawn / join checks.
    public static final double JOIN_RADIUS = 10.0;

    // Entity further than this from its herd centre will try to return or leave.
    public static final double STRAY_DISTANCE = 50.0;

    // Entity closer than this to a foreign herd centre may defect to it.
    public static final double DEFECT_APPROACH_DISTANCE = 10.0;

    // Radius within which separation pushes entities apart.
    public static final double SEPARATION_RADIUS = 4.0;

    // When closer than this to the personal wander target, cohesion is suppressed.
    public static final double COHESION_DEAD_ZONE = 6.0;

    // How far ahead of the herd centre the shared wander target sits.
    public static final double WANDER_DISTANCE = 18.0;

    // Per-entity random spread around the shared wander target.
    public static final double JITTER_RADIUS = 6.0;

    // No catch-up speed applies within this distance of the herd centre.
    public static final double CATCHUP_MIN_DISTANCE = 16.0;

    // Catch-up speed climbs exponentially past this distance.
    public static final double CATCHUP_KNEE_DISTANCE = 30.0;

    // Multiplier at the knee, before the exponential run to the cap.
    private static final double CATCHUP_KNEE_MULTIPLIER = 1.15;

    // Fastest a straggler will travel, as a multiple of its normal speed.
    public static final double CATCHUP_MAX_MULTIPLIER = 1.5;

    // How sharply the multiplier closes on the cap past the knee.
    private static final double CATCHUP_EXPONENT = 0.15;

    // Longest pause between moving bursts, in ticks; the shortest is half this.
    private static final long IDLE_DURATION_TICKS = 1000L;

    // Separate salts so the phase RNG and direction RNG don't share state.
    private static final long MOVE_SEED_PRIME = 0x9e3779b97f4a7c15L;
    private static final long DIR_SEED_SALT   = 0xDEADBEEFCAFEL;
    private static final long PHASE_SEED_SALT = 0xB16B00B5L;

    // How long a snapshot stays valid, in ticks.
    private static final long HERD_SNAPSHOT_TTL_TICKS = 10L;

    // Snapshot cache size that triggers a sweep of expired entries.
    private static final int HERD_SNAPSHOT_SWEEP_THRESHOLD = 200;

    // Roster size that triggers a sweep of dead ids and empty herds.
    private static final int HERD_ROSTER_SWEEP_THRESHOLD = 500;

    // No member of this herd is being led.
    private static final int NO_MEMBER = -1;

    // Membership by herd id. Deliberately entity ids, not references, so nothing here pins an
    // entity in memory or dangles. Maintained from setHerdId.
    private static final Map<UUID, Set<Integer>> herdRosters = new HashMap<>();

    // Positions and leashed-member lookup derived from the rosters, cached for a few ticks.
    private static final Map<UUID, HerdSnapshot> herdSnapshotCache = new HashMap<>();

    // Each herd's move/idle rhythm. Stored rather than derived from game time because the
    // durations are randomised, so there is no fixed grid for members to recompute from.
    private static final Map<UUID, HerdPhase> herdPhases = new HashMap<>();

    private HerdManager() {}

    // ---------------------------------------------------------------
    // Roster upkeep
    // ---------------------------------------------------------------

    // Moves an animal between rosters. Called from setHerdId, so loading re-registers too.
    public static void updateHerdMembership(EnhancedAnimalAbstract entity,
                                            @Nullable UUID previousHerdId,
                                            @Nullable UUID newHerdId) {
        if (entity.level.isClientSide || Objects.equals(previousHerdId, newHerdId)) return;

        if (previousHerdId != null) {
            Set<Integer> previous = herdRosters.get(previousHerdId);
            if (previous != null) {
                previous.remove(entity.getId());
                if (previous.isEmpty()) forgetHerd(previousHerdId);
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

    // Re-adds an animal missing from its own roster. Needed because pruning can catch one
    // mid-load, before it is in the level and resolvable.
    public static void ensureRegistered(EnhancedAnimalAbstract entity) {
        if (entity.level.isClientSide) return;
        UUID herdId = entity.getHerdId();
        if (herdId == null) return;

        Set<Integer> roster = herdRosters.get(herdId);
        if (roster != null && roster.contains(entity.getId())) return;

        herdRosters.computeIfAbsent(herdId, id -> new HashSet<>()).add(entity.getId());
        herdSnapshotCache.remove(herdId);
    }

    // Catch-all for herds nothing queries any more; one with a loaded member prunes on read.
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
                herdPhases.remove(herdId);
            }
        }
    }

    // Drops every trace of a herd that no longer has any members.
    private static void forgetHerd(UUID herdId) {
        herdRosters.remove(herdId);
        herdSnapshotCache.remove(herdId);
        herdPhases.remove(herdId);
    }

    // The live animal behind a roster id, or null if it is gone, unloaded, or has moved herds.
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

    // Called once on first spawn, or on load with no herd id. Joins the largest herd nearby,
    // otherwise starts one and pulls in any un-herded neighbours, so a group that spawns
    // together begins as a single herd.
    @SuppressWarnings("unchecked")
    public static <T extends EnhancedAnimalAbstract> void initialiseHerd(T entity) {
        if (entity.level.isClientSide) return;

        // Spatial because there is no herd id yet to look a roster up by.
        AABB searchBox = entity.getBoundingBox().inflate(JOIN_RADIUS);
        List<T> nearby = (List<T>) entity.level.getEntitiesOfClass(
                entity.getClass(), searchBox, m -> m != entity && m.isAlive());

        // Dedup by herd id; size depends on the id, not which member we saw first.
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

    // Leaves for a brand-new herd when over STRAY_DISTANCE from the centre. True if it left.
    public static <T extends EnhancedAnimalAbstract> boolean checkLeaveHerd(T entity) {
        if (entity.level.isClientSide) return false;
        UUID herdId = entity.getHerdId();
        if (herdId == null) return false;

        Vec3 centre = computeHerdCentre(entity, herdId);
        if (centre == null) {
            // Already a herd of one: nothing to leave, and a fresh id would only churn.
            return false;
        }

        if (entity.position().distanceTo(centre) > STRAY_DISTANCE) {
            entity.setHerdId(UUID.randomUUID());
            return true;
        }
        return false;
    }

    // Switches to a nearby herd that is either larger than this one, or within
    // DEFECT_APPROACH_DISTANCE while this animal has strayed past STRAY_DISTANCE from its own
    // centre. True if it switched.
    public static <T extends EnhancedAnimalAbstract> boolean checkJoinNearbyHerd(T entity) {
        if (entity.level.isClientSide) return false;
        UUID currentHerdId = entity.getHerdId();
        if (currentHerdId == null) return false;

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

    // True while the herd is mid-burst. Members share one record, so they move as one.
    public static boolean isHerdMoving(UUID herdId, long gameTime) {
        return phaseFor(herdId, gameTime).moving;
    }

    // Identifies the current moving burst, so a herd picks one wander direction per burst.
    public static long currentBurstId(UUID herdId, long gameTime) {
        return phaseFor(herdId, gameTime).burstId;
    }

    private static HerdPhase phaseFor(UUID herdId, long gameTime) {
        HerdPhase phase = herdPhases.get(herdId);
        if (phase == null) {
            // Start resting so a batch loading together doesn't all set off at once.
            phase = new HerdPhase(false, gameTime + nextIdleTicks(herdId, gameTime), 0L);
            herdPhases.put(herdId, phase);
            return phase;
        }

        // An end further out than any phase can last means game time went backwards.
        boolean timeWentBackwards = gameTime < phase.endsAtTick - maxPhaseTicks();
        if (gameTime >= phase.endsAtTick || timeWentBackwards) {
            phase.moving = timeWentBackwards ? false : !phase.moving;
            if (phase.moving) phase.burstId++;
            phase.endsAtTick = gameTime + (phase.moving
                    ? nextMoveTicks(herdId, gameTime)
                    : nextIdleTicks(herdId, gameTime));
        }
        return phase;
    }

    // A fifth of the configured duration up to all of it.
    private static long nextMoveTicks(UUID herdId, long gameTime) {
        long max = moveDurationTicks();
        return randomBetween(Math.max(1L, max / 5L), max, herdId, gameTime);
    }

    // Half of IDLE_DURATION_TICKS up to all of it.
    private static long nextIdleTicks(UUID herdId, long gameTime) {
        return randomBetween(Math.max(1L, IDLE_DURATION_TICKS / 2L), IDLE_DURATION_TICKS, herdId, gameTime);
    }

    private static long randomBetween(long min, long max, UUID herdId, long gameTime) {
        if (max <= min) return min;
        long seed = ((long) herdId.hashCode() ^ (gameTime * MOVE_SEED_PRIME)) ^ PHASE_SEED_SALT;
        return min + (long) (new Random(seed).nextDouble() * (max - min + 1L));
    }

    private static long maxPhaseTicks() {
        return Math.max(moveDurationTicks(), IDLE_DURATION_TICKS);
    }

    private static long moveDurationTicks() {
        return GeneticAnimalsConfig.COMMON.herdMoveDurationTicks.get();
    }

    private static final class HerdPhase {
        boolean moving;
        long endsAtTick;
        long burstId;

        HerdPhase(boolean moving, long endsAtTick, long burstId) {
            this.moving = moving;
            this.endsAtTick = endsAtTick;
            this.burstId = burstId;
        }
    }

    // ---------------------------------------------------------------
    // Herd steering (called every PATH_RECALC_INTERVAL ticks in HerdGoal)
    // ---------------------------------------------------------------

    // Normalised steering, or null once inside the dead zone of the personal target.
    @Nullable
    public static <T extends EnhancedAnimalAbstract> Vec3 computeHerdSteering(T entity) {
        if (entity.level.isClientSide) return null;

        UUID herdId = entity.getHerdId();
        if (herdId == null) return null;

        Vec3 selfPos = entity.position();

        // One pass for both the local centre and the push away from crowding mates.
        Map<Integer, Vec3> positions = herdSnapshot(entity, herdId, entity.level.getGameTime()).positionsById;
        AABB neighbourhood = entity.getBoundingBox().inflate(JOIN_RADIUS * 3);

        Vec3 centreSum = selfPos;
        int count = 1;
        Vec3 separation = Vec3.ZERO;
        for (Map.Entry<Integer, Vec3> mate : positions.entrySet()) {
            if (mate.getKey() == entity.getId()) continue;

            Vec3 matePos = mate.getValue();
            if (!neighbourhood.contains(matePos.x, matePos.y, matePos.z)) continue;

            centreSum = centreSum.add(matePos);
            count++;

            Vec3 awayFromMate = selfPos.subtract(matePos);
            double gap = awayFromMate.length();
            if (gap > 0.0 && gap < SEPARATION_RADIUS) {
                separation = separation.add(awayFromMate.normalize().scale(1.0 - (gap / SEPARATION_RADIUS)));
            }
        }
        Vec3 centre = centreSum.scale(1.0 / count);

        long burstId = currentBurstId(herdId, entity.level.getGameTime());
        Vec3 wanderDir = dirForCycle(herdId, burstId);

        // Stable within a burst, so members keep their own spot in the group.
        Random jitterRng = new Random(entity.getId() ^ burstId);
        Vec3 jitter = new Vec3(
                (jitterRng.nextDouble() - 0.5) * JITTER_RADIUS * 2.0,
                0.0,
                (jitterRng.nextDouble() - 0.5) * JITTER_RADIUS * 2.0
        );

        Vec3 personalTarget = centre.add(wanderDir.scale(WANDER_DISTANCE)).add(jitter);

        Vec3 cohesion = Vec3.ZERO;
        if (selfPos.distanceTo(personalTarget) > COHESION_DEAD_ZONE) {
            cohesion = personalTarget.subtract(selfPos).normalize();
        }

        Vec3 steering = cohesion.scale(0.6).add(separation.scale(1.5));
        double len = steering.length();
        return len > 1e-6 ? steering.normalize() : null;
    }

    // ---------------------------------------------------------------
    // Leash following
    // ---------------------------------------------------------------

    // The herd-mate being led, or null. An O(1) snapshot read rather than an area search.
    @Nullable
    public static EnhancedAnimalAbstract findLeashedHerdMate(EnhancedAnimalAbstract entity) {
        if (entity.level.isClientSide) return null;
        UUID herdId = entity.getHerdId();
        if (herdId == null) return null;

        int leashedId = herdSnapshot(entity, herdId, entity.level.getGameTime()).leashedMemberId;
        if (leashedId == NO_MEMBER || leashedId == entity.getId()) return null;

        // Re-checked rather than trusted, so followers stop the tick the lead comes off.
        if (!(entity.level.getEntity(leashedId) instanceof EnhancedAnimalAbstract mate)) return null;
        if (!mate.isAlive() || !mate.isLedByEntity() || !herdId.equals(mate.getHerdId())) return null;
        return mate;
    }

    // As computeHerdSteering, but aimed at the leader rather than a shared wander target.
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

        Vec3 separation = herdId != null
                ? computeSeparation(entity, herdId, selfPos)
                : Vec3.ZERO;

        Vec3 steering = cohesion.scale(0.6).add(separation.scale(1.5));
        double len = steering.length();
        return len > 1e-6 ? steering.normalize() : null;
    }

    // Push away from too-close herd-mates; shared by wander and leash-follow steering.
    private static Vec3 computeSeparation(EnhancedAnimalAbstract entity, UUID herdId, Vec3 selfPos) {
        Map<Integer, Vec3> positions = herdSnapshot(entity, herdId, entity.level.getGameTime()).positionsById;
        Vec3 separation = Vec3.ZERO;
        for (Map.Entry<Integer, Vec3> mate : positions.entrySet()) {
            if (mate.getKey() == entity.getId()) continue;

            Vec3 awayFromMate = selfPos.subtract(mate.getValue());
            double gap = awayFromMate.length();
            if (gap > 0.0 && gap < SEPARATION_RADIUS) {
                separation = separation.add(awayFromMate.normalize().scale(1.0 - (gap / SEPARATION_RADIUS)));
            }
        }
        return separation;
    }

    // ---------------------------------------------------------------
    // Snapshots derived from the rosters
    //
    // The first member to ask resolves the roster; the rest reuse it for a few ticks. Ids that
    // no longer resolve are dropped as we go, so death, unloading and defection self-clean.
    // ---------------------------------------------------------------

    private static final class HerdSnapshot {
        final long tick;
        final Map<Integer, Vec3> positionsById;
        final int leashedMemberId;
        // Sum of every member position, so any one member can derive a centre in O(1).
        final Vec3 positionSum;

        HerdSnapshot(long tick, Map<Integer, Vec3> positionsById, int leashedMemberId, Vec3 positionSum) {
            this.tick = tick;
            this.positionsById = positionsById;
            this.leashedMemberId = leashedMemberId;
            this.positionSum = positionSum;
        }

        // Centre excluding one member. Subtracts its recorded position to match the sum.
        @Nullable
        Vec3 centreExcluding(int memberId) {
            Vec3 recorded = positionsById.get(memberId);
            int others = positionsById.size() - (recorded != null ? 1 : 0);
            if (others <= 0) return null;
            Vec3 sumOfOthers = recorded != null ? positionSum.subtract(recorded) : positionSum;
            return sumOfOthers.scale(1.0 / others);
        }
    }

    private static HerdSnapshot herdSnapshot(EnhancedAnimalAbstract entity, UUID herdId, long gameTime) {
        HerdSnapshot cached = herdSnapshotCache.get(herdId);
        if (cached != null && isFresh(cached, gameTime)) {
            return cached;
        }

        Map<Integer, Vec3> positions = new HashMap<>();
        int leashedMemberId = NO_MEMBER;
        Vec3 positionSum = Vec3.ZERO;

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
                Vec3 position = member.position();
                positions.put(memberId, position);
                positionSum = positionSum.add(position);
                if (leashedMemberId == NO_MEMBER && member.isLedByEntity()) {
                    leashedMemberId = memberId;
                }
            }
            if (roster.isEmpty()) forgetHerd(herdId);
        }

        if (herdSnapshotCache.size() > HERD_SNAPSHOT_SWEEP_THRESHOLD) {
            sweepExpiredSnapshots(gameTime);
        }
        HerdSnapshot snapshot = new HerdSnapshot(gameTime, positions, leashedMemberId, positionSum);
        herdSnapshotCache.put(herdId, snapshot);
        return snapshot;
    }

    // Bounded both ways: a future-dated entry means game time moved back, so distrust it.
    private static boolean isFresh(HerdSnapshot snapshot, long gameTime) {
        long age = gameTime - snapshot.tick;
        return age >= 0 && age < HERD_SNAPSHOT_TTL_TICKS;
    }

    private static void sweepExpiredSnapshots(long gameTime) {
        herdSnapshotCache.values().removeIf(s -> !isFresh(s, gameTime));
    }

    // Herd-mate positions, excluding the querying entity, within radius of its current box.
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

    // Speed multiplier for rejoining the herd, between 1.0 and CATCHUP_MAX_MULTIPLIER.
    // Measured against the centre excluding this animal, so a straggler doesn't drag the
    // centre towards itself and read as closer than it is.
    public static double catchUpSpeedMultiplier(EnhancedAnimalAbstract entity) {
        UUID herdId = entity.getHerdId();
        if (herdId == null) return 1.0;

        Vec3 centre = herdSnapshot(entity, herdId, entity.level.getGameTime())
                .centreExcluding(entity.getId());
        if (centre == null) return 1.0;

        // Squared, so the common already-with-the-herd case costs no sqrt.
        double distanceSqr = entity.position().distanceToSqr(centre);
        if (distanceSqr < CATCHUP_MIN_DISTANCE * CATCHUP_MIN_DISTANCE) return 1.0;

        return catchUpMultiplierForDistance(Math.sqrt(distanceSqr));
    }

    private static double catchUpMultiplierForDistance(double distance) {
        if (distance <= CATCHUP_KNEE_DISTANCE) {
            double towardsKnee = (distance - CATCHUP_MIN_DISTANCE)
                    / (CATCHUP_KNEE_DISTANCE - CATCHUP_MIN_DISTANCE);
            return 1.0 + towardsKnee * (CATCHUP_KNEE_MULTIPLIER - 1.0);
        }

        // Closes on the cap without passing it.
        double remaining = CATCHUP_MAX_MULTIPLIER - CATCHUP_KNEE_MULTIPLIER;
        double decay = Math.exp(-CATCHUP_EXPONENT * (distance - CATCHUP_KNEE_DISTANCE));
        return Math.min(CATCHUP_MAX_MULTIPLIER, CATCHUP_MAX_MULTIPLIER - remaining * decay);
    }

    // ---------------------------------------------------------------
    // Query helpers
    // ---------------------------------------------------------------

    // Centre of same-herd members within STRAY_DISTANCE, excluding self; null if none.
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

    // Nearby animals of the same class that belong to some herd.
    @SuppressWarnings("unchecked")
    private static <T extends EnhancedAnimalAbstract> List<T> getNearbyAnimals(
            T entity, Class<? extends EnhancedAnimalAbstract> clazz, double radius) {
        AABB box = entity.getBoundingBox().inflate(radius);
        return (List<T>) entity.level.getEntitiesOfClass(
                clazz, box, m -> m != entity && m.isAlive() && m.getHerdId() != null);
    }

    // How many members of the given herd are within radius of this entity.
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
