package mokiyoki.enhancedanimals.ai.general;

import mokiyoki.enhancedanimals.entity.EnhancedAnimalAbstract;
import net.minecraft.world.entity.ai.goal.Goal;

import javax.annotation.Nullable;
import java.util.List;

import static mokiyoki.enhancedanimals.util.scheduling.Schedules.*;

public class EnhancedFollowParentGoal extends Goal {
    private final EnhancedAnimalAbstract child;

    @Nullable
    private EnhancedAnimalAbstract parent;
    private final double speedModifier;
    private int timeToRecalcPath;

    private double distanceFromParentMinimum;

    public EnhancedFollowParentGoal(EnhancedAnimalAbstract child, double speed, double distanceFromParentMinimum) {
        this.speedModifier = speed;
        this.child = child;
        this.parent = child.getMother();
        this.distanceFromParentMinimum = distanceFromParentMinimum;
    }

    public boolean canUse() {
        if (this.child.isVehicle() || this.child.isAnimalSleeping() || this.child.getAge() >= 0) {
            return false;
        } else if (this.parent == null) {
            List<? extends EnhancedAnimalAbstract> list = this.child.level.getEntitiesOfClass(this.child.getClass(), this.child.getBoundingBox().inflate(8.0D, 4.0D, 8.0D));
            EnhancedAnimalAbstract animal = null;
            double d0 = Double.MAX_VALUE;

            for(EnhancedAnimalAbstract animal1 : list) {
                if (animal1.getAge() >= 0) {
                    double d1 = this.child.distanceToSqr(animal1);
                    if (!(d1 > d0)) {
                        d0 = d1;
                        animal = animal1;
                    }
                }
            }

            if (animal == null) {
                return false;
            } else if (d0 < distanceFromParentMinimum) {
                return false;
            } else {
                this.parent = animal;
                return true;
            }
        } else if (this.child.distanceToSqr(this.parent) > distanceFromParentMinimum) {
            return true;
        }


        return false;
    }

    public boolean canContinueToUse() {
        if (this.child.getAge() >= 0 || this.child.isPassenger()) {
            return false;
        } else if (!this.parent.isAlive()) {
            return false;
        } else {
            double d0 = this.child.distanceToSqr(this.parent);
            return !(d0 < distanceFromParentMinimum) && !(d0 > 256.0D);
        }
    }

    public void start() {
        this.timeToRecalcPath = 0;
    }

    public void tick() {
        if (--this.timeToRecalcPath <= 0) {
            this.timeToRecalcPath = this.adjustedTickDelay(10);

            if (this.child.growthAmount()<0.25F && this.child.getRandom().nextInt(0, 100) > 95 && !this.child.isAnimalSleeping() && this.parent.getPassengers().isEmpty() && !this.child.scheduledToRun.containsKey(RIDE_MOTHER_HEN_SCHEDULE.funcName)) {
                int mountInTicks = this.child.getRandom().nextInt(100, 1000);
                int dismountInTicks = this.child.getRandom().nextInt(mountInTicks+100, mountInTicks+1000);
                this.child.scheduledToRun.put(RIDE_MOTHER_HEN_SCHEDULE.funcName, RIDE_MOTHER_HEN_SCHEDULE.function.apply(mountInTicks));
                this.child.scheduledToRun.put(DISMOUNT_SCHEDULE.funcName, DISMOUNT_SCHEDULE.function.apply(dismountInTicks));
            } else {
                this.child.getNavigation().moveTo(this.parent, this.speedModifier);
            }
        }
    }

    @Nullable
    public EnhancedAnimalAbstract getParent() {
        return parent;
    }

}
