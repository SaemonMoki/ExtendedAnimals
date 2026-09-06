package mokiyoki.enhancedanimals.ai.general;

import mokiyoki.enhancedanimals.ai.brain.ValidatePath;
import mokiyoki.enhancedanimals.config.GeneticAnimalsConfig;
import mokiyoki.enhancedanimals.entity.EnhancedAnimalAbstract;
import mokiyoki.enhancedanimals.util.HerdManager;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;
import java.util.UUID;

public class HerdGoal extends Goal {

    private static final int MEMBERSHIP_RECHECK_INTERVAL = 500;
    private static final int PATH_RECALC_INTERVAL = 20;
    private static final int PATH_VALIDATION_NODE_LIMIT = 16;

    private final EnhancedAnimalAbstract entity;
    private final PathNavigation navigation;
    private final double speed;
    private final boolean leashFollow;

    private int membershipTimer;
    private int pathTimer = 0;
    private EnhancedAnimalAbstract leashedLeader;

    public HerdGoal(EnhancedAnimalAbstract entity, double speed) {
        this(entity, speed, false);
    }

    public HerdGoal(EnhancedAnimalAbstract entity, double speed, boolean leashFollow) {
        this.entity = entity;
        this.navigation = entity.getNavigation();
        this.speed = speed;
        this.leashFollow = leashFollow;
        // Stagger the first membership check by entity id so a batch of animals spawning
        // together doesn't run their (expensive) checks all on the same tick.
        this.membershipTimer = Math.floorMod(entity.getId(), MEMBERSHIP_RECHECK_INTERVAL);
        this.setFlags(EnumSet.of(Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        if (entity.level.isClientSide) return false;
        if (entity.isAnimalSleeping()) return false;

        if (entity.isLeashed()) return false;

        UUID herdId = entity.getHerdId();
        if (herdId == null) return false;

        if (leashFollow) {
            if (!GeneticAnimalsConfig.COMMON.herdLeashFollowEnabled.get()) return false;
            leashedLeader = HerdManager.findLeashedHerdMate(entity);
            return leashedLeader != null;
        }

        // Membership upkeep is herd bookkeeping, not herd movement: it runs on its own
        // schedule whether or not this goal ends up running, and regardless of whether
        // group movement is switched on.
        HerdManager.ensureRegistered(entity);
        maybeRecheckMembership();

        if (!GeneticAnimalsConfig.COMMON.herdMovementEnabled.get()) return false;
        return HerdManager.isHerdMoving(herdId, entity.level.getGameTime());
    }

    @Override
    public boolean canContinueToUse() {
        return canUse();
    }

    @Override
    public boolean isInterruptable() {
        return true;
    }

    @Override
    public void start() {
        pathTimer = 0;
    }

    @Override
    public void stop() {
        navigation.stop();
        leashedLeader = null;
    }

    @Override
    public void tick() {
        if (leashFollow) {
            tickLeashFollow();
        } else {
            tickWander();
        }
    }

    private void tickWander() {
        if (--pathTimer > 0) return;
        pathTimer = PATH_RECALC_INTERVAL;

        Vec3 steering = HerdManager.computeHerdSteering(entity);
        if (steering == null) return;

        moveToIfReachable(entity.position().add(steering.scale(8.0)));
    }

    private void tickLeashFollow() {
        if (--pathTimer > 0) return;
        pathTimer = PATH_RECALC_INTERVAL;

        if (leashedLeader == null) return;

        Vec3 steering = HerdManager.computeLeaderSteering(entity, leashedLeader);
        Vec3 target = steering != null
                ? entity.position().add(steering.scale(8.0))
                : leashedLeader.position();
        moveToIfReachable(target);
    }

    private void moveToIfReachable(Vec3 target) {
        if (!ValidatePath.isValidPath(entity, new BlockPos(target), PATH_VALIDATION_NODE_LIMIT)) return;
        navigation.moveTo(target.x, target.y, target.z, speed);
    }

    /** Leaves/joins herds on its own schedule, independent of whether this goal is running. */
    private void maybeRecheckMembership() {
        if (--membershipTimer > 0) return;
        membershipTimer = MEMBERSHIP_RECHECK_INTERVAL;
        if (!HerdManager.checkLeaveHerd(entity)) {
            HerdManager.checkJoinNearbyHerd(entity);
        }
    }

}
