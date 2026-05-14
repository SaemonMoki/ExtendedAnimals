package mokiyoki.enhancedanimals.ai.general;

import mokiyoki.enhancedanimals.entity.EnhancedAnimalAbstract;
import mokiyoki.enhancedanimals.util.HerdManager;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;
import java.util.UUID;

/**
 * Herd movement goal using shared wander direction + separation.
 *
 * Register at a low priority (high number) so that panic, breeding, tempt,
 * and grazing goals all override it via the MOVE flag conflict mechanism.
 * Example: goalSelector.addGoal(7, new HerdGoal(this, 1.0));
 *
 * canUse() returns false during idle windows (~35% of the time), which releases
 * Flag.MOVE so vanilla stroll and graze goals can run naturally during those periods.
 *
 * Membership recalculation runs every 500 ticks. Path recalculation runs
 * every 20 ticks.
 */
public class HerdGoal extends Goal {

    private static final int MEMBERSHIP_RECHECK_INTERVAL = 500;
    private static final int PATH_RECALC_INTERVAL = 20;

    private final EnhancedAnimalAbstract entity;
    private final PathNavigation navigation;
    private final double speed;

    private int membershipTimer = 0;
    private int pathTimer = 0;

    public HerdGoal(EnhancedAnimalAbstract entity, double speed) {
        this.entity = entity;
        this.navigation = entity.getNavigation();
        this.speed = speed;
        this.setFlags(EnumSet.of(Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        if (entity.level.isClientSide) return false;
        if (entity.isAnimalSleeping()) return false;
        UUID herdId = entity.getHerdId();
        if (herdId == null) return false;
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
        membershipTimer = 0;
    }

    @Override
    public void stop() {
        navigation.stop();
    }

    @Override
    public void tick() {
        if (--membershipTimer <= 0) {
            membershipTimer = MEMBERSHIP_RECHECK_INTERVAL;
            if (!HerdManager.checkLeaveHerd(entity)) {
                HerdManager.checkJoinNearbyHerd(entity);
            }
        }

        if (--pathTimer > 0) return;
        pathTimer = PATH_RECALC_INTERVAL;

        Vec3 steering = HerdManager.computeHerdSteering(entity);
        if (steering == null) return;

        Vec3 target = entity.position().add(steering.scale(8.0));
        navigation.moveTo(target.x, target.y, target.z, speed);
    }
}
