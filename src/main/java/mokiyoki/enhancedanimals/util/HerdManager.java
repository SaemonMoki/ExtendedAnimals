package mokiyoki.enhancedanimals.util;

import mokiyoki.enhancedanimals.entity.EnhancedAnimalAbstract;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * Herd membership is stored as a UUID on each entity (herdId).
 * There is no central registry — membership is resolved at runtime by
 * querying nearby entities, keeping this stateless and chunk-safe.
 *
 * Movement uses cohesion toward a shared wander target + separation:
 *   - A direction is derived by seeding RNG with (herdId ^ timeSlot) so all
 *     members independently compute the same direction each time window.
 *   - Each entity's nav target is: herdCentre + wanderDir*WANDER_DISTANCE + entityJitter.
 *   - ~35% of windows are idle — HerdGoal.canUse() returns false, releasing Flag.MOVE
 *     so vanilla stroll/graze goals can run. Separation still applies via those goals.
 *   - Direction blends over the last TRANSITION_TICKS of each window to avoid snapping.
 */
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

    /** Game ticks per wander direction window (~25 seconds). */
    public static final long WANDER_INTERVAL = 500L;

    /** Ticks at the end of each window over which direction blends into the next. */
    private static final long TRANSITION_TICKS = 100L;

    /** Fraction of time windows where the herd actively moves. */
    private static final double MOVE_PROBABILITY = 0.65;

    // Separate salts so the move-check RNG and direction RNG don't share state.
    private static final long MOVE_SEED_PRIME = 0x9e3779b97f4a7c15L;
    private static final long DIR_SEED_SALT   = 0xDEADBEEFCAFEL;

    private HerdManager() {}

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

        AABB searchBox = entity.getBoundingBox().inflate(JOIN_RADIUS);
        List<T> nearby = (List<T>) entity.level.getEntitiesOfClass(
                entity.getClass(), searchBox, m -> m != entity && m.isAlive());

        // First pass: find the largest existing herd within range
        UUID bestHerd = null;
        int bestSize = 0;
        for (T other : nearby) {
            UUID otherId = other.getHerdId();
            if (otherId == null) continue;
            int size = countHerdSize(entity, entity.getClass(), otherId, JOIN_RADIUS * 4);
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
    // Periodic membership checks (run every 500 ticks in HerdGoal)
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

        Vec3 centre = computeHerdCentre(entity, entity.getClass(), herdId);
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

        List<T> nearby = getNearbyAnimals(entity, entity.getClass(), JOIN_RADIUS * 3);

        int currentSize = countHerdSize(entity, entity.getClass(), currentHerdId, JOIN_RADIUS * 4);
        Vec3 ownCentre = computeHerdCentre(entity, entity.getClass(), currentHerdId);
        double distToOwn = ownCentre != null ? entity.position().distanceTo(ownCentre) : Double.MAX_VALUE;

        UUID bestForeignId = null;
        double bestForeignDist = Double.MAX_VALUE;

        for (T other : nearby) {
            UUID otherId = other.getHerdId();
            if (otherId == null || otherId.equals(currentHerdId)) continue;

            Vec3 foreignCentre = computeHerdCentre(entity, entity.getClass(), otherId);
            if (foreignCentre == null) continue;
            double distToForeign = entity.position().distanceTo(foreignCentre);

            int foreignSize = countHerdSize(entity, entity.getClass(), otherId, JOIN_RADIUS * 4);
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
     * Returns true if this herd is in a moving window at the given game time.
     * All members of the same herd return the same value for the same game time,
     * regardless of when they individually started the goal.
     */
    public static boolean isHerdMoving(UUID herdId, long gameTime) {
        long timeSlot = gameTime / WANDER_INTERVAL;
        long seed = (long) herdId.hashCode() ^ (timeSlot * MOVE_SEED_PRIME);
        return new Random(seed).nextDouble() < MOVE_PROBABILITY;
    }

    // ---------------------------------------------------------------
    // Herd steering (called every PATH_RECALC_INTERVAL ticks in HerdGoal)
    // ---------------------------------------------------------------

    /**
     * Computes a normalised steering vector for this entity.
     * Returns null when the entity is already within the dead zone of its personal target.
     *
     * Only called during moving windows (HerdGoal.canUse() gates idle phases).
     * Direction blends smoothly into the next window over the final TRANSITION_TICKS
     * to avoid abrupt pivots at slot boundaries.
     */
    @Nullable
    public static <T extends EnhancedAnimalAbstract> Vec3 computeHerdSteering(T entity) {
        if (entity.level.isClientSide) return null;

        UUID herdId = entity.getHerdId();
        if (herdId == null) return null;

        List<T> mates = getSameHerdMembers(entity, entity.getClass(), JOIN_RADIUS * 3);

        Vec3 selfPos = entity.position();

        // Herd centre including self
        Vec3 centre = selfPos;
        int count = 1;
        for (T m : mates) {
            centre = centre.add(m.position());
            count++;
        }
        centre = centre.scale(1.0 / count);

        // Shared wander direction — blended near slot boundaries for smooth turns
        long gameTime = entity.level.getGameTime();
        long timeSlot  = gameTime / WANDER_INTERVAL;
        long tickInSlot = gameTime % WANDER_INTERVAL;

        Vec3 wanderDir;
        if (tickInSlot >= WANDER_INTERVAL - TRANSITION_TICKS) {
            double t = (tickInSlot - (WANDER_INTERVAL - TRANSITION_TICKS)) / (double) TRANSITION_TICKS;
            Vec3 current = dirForSlot(herdId, timeSlot);
            Vec3 next    = dirForSlot(herdId, timeSlot + 1);
            wanderDir = current.scale(1.0 - t).add(next.scale(t)).normalize();
        } else {
            wanderDir = dirForSlot(herdId, timeSlot);
        }

        // Per-entity jitter — stable within a window, spreads members naturally
        Random jitterRng = new Random(entity.getId() ^ timeSlot);
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

        // Separation: push away from too-close herd-mates
        Vec3 separation = Vec3.ZERO;
        for (T m : mates) {
            Vec3 diff = selfPos.subtract(m.position());
            double dist = diff.length();
            if (dist > 0.0 && dist < SEPARATION_RADIUS) {
                separation = separation.add(diff.normalize().scale(1.0 - (dist / SEPARATION_RADIUS)));
            }
        }

        Vec3 steering = cohesion.scale(0.6).add(separation.scale(1.5));
        double len = steering.length();
        return len > 1e-6 ? steering.normalize() : null;
    }

    // ---------------------------------------------------------------
    // Query helpers
    // ---------------------------------------------------------------

    /**
     * Geometric centre of all living same-herd members excluding the querying entity,
     * or null if none found within STRAY_DISTANCE.
     */
    @Nullable
    @SuppressWarnings("unchecked")
    public static <T extends EnhancedAnimalAbstract> Vec3 computeHerdCentre(
            T entity, Class<? extends T> clazz, UUID herdId) {

        AABB box = entity.getBoundingBox().inflate(STRAY_DISTANCE);
        List<? extends T> members = (List<? extends T>) entity.level.getEntitiesOfClass(
                clazz, box, m -> m != entity && herdId.equals(m.getHerdId()) && m.isAlive());

        if (members.isEmpty()) return null;

        double sx = 0, sy = 0, sz = 0;
        for (T m : members) {
            Vec3 p = m.position();
            sx += p.x; sy += p.y; sz += p.z;
        }
        int n = members.size();
        return new Vec3(sx / n, sy / n, sz / n);
    }

    private static Vec3 dirForSlot(UUID herdId, long timeSlot) {
        long seed = ((long) herdId.hashCode() ^ (timeSlot * MOVE_SEED_PRIME)) ^ DIR_SEED_SALT;
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

    /** All nearby living animals sharing the same herd ID (for steering). */
    @SuppressWarnings("unchecked")
    private static <T extends EnhancedAnimalAbstract> List<T> getSameHerdMembers(
            T entity, Class<? extends EnhancedAnimalAbstract> clazz, double radius) {
        UUID herdId = entity.getHerdId();
        if (herdId == null) return List.of();
        AABB box = entity.getBoundingBox().inflate(radius);
        return (List<T>) entity.level.getEntitiesOfClass(
                clazz, box, m -> m != entity && m.isAlive() && herdId.equals(m.getHerdId()));
    }

    private static <T extends EnhancedAnimalAbstract> int countHerdSize(
            T entity, Class<? extends EnhancedAnimalAbstract> clazz, UUID herdId, double radius) {
        AABB box = entity.getBoundingBox().inflate(radius);
        return entity.level.getEntitiesOfClass(
                clazz, box, m -> m.isAlive() && herdId.equals(m.getHerdId())).size();
    }
}
