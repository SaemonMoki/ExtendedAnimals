package mokiyoki.enhancedanimals.ai.general.cow;

import mokiyoki.enhancedanimals.entity.EnhancedAnimalAbstract;
import mokiyoki.enhancedanimals.entity.EnhancedCow;
import mokiyoki.enhancedanimals.entity.EnhancedSheep;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.passive.AnimalEntity;

import java.util.List;

public class EnhancedAINurseFromMotherGoal extends Goal {
    private final AnimalEntity childEntity;
    private AnimalEntity motherEntity;
    private String motherUUID;
    private final double moveSpeed;
    private int delayCounter;

    public EnhancedAINurseFromMotherGoal(AnimalEntity child, String motherUUID, double speed) {
        this.childEntity = child;
        this.motherUUID = motherUUID;
        this.moveSpeed = speed;
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute() {
        if (this.childEntity.getGrowingAge() >= 0 || this.childEntity.getIdleTime() >= 100) {
            return false;
        } else if (((EnhancedAnimalAbstract)this.childEntity).getHunger() > 1000) {
            List<AnimalEntity> list = this.childEntity.world.getEntitiesWithinAABB(this.childEntity.getClass(), this.childEntity.getBoundingBox().grow(8.0D, 4.0D, 8.0D));
            AnimalEntity animalentity = null;
            double d0 = Double.MAX_VALUE;

            for(AnimalEntity animalentity1 : list) {
                if (animalentity1.getUniqueID().equals(motherUUID)) {
                    double d1 = this.childEntity.getDistanceSq(animalentity1);
                    if (!(d1 > d0)) {
                        d0 = d1;
                        animalentity = animalentity1;
                    }
                }
            }

            if (animalentity == null) {
                return false;
            } else if (d0 < 2.0D) {
                return false;
            } else {
                this.motherEntity = animalentity;
                return true;
            }
        }

        return false;
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean shouldContinueExecuting() {
        if (this.childEntity.getGrowingAge() >= 0) {
            return false;
        } else if (!this.motherEntity.isAlive()) {
            return false;
        } else {
            double d0 = this.childEntity.getDistanceSq(this.motherEntity);
            boolean shouldContinue = !(d0 < 2.0D) && !(d0 > 256.0D);
            if (!shouldContinue) {
                if (motherEntity instanceof EnhancedCow) {
                    if (((EnhancedCow)motherEntity).decreaseMilk(2)) {
                        ((EnhancedAnimalAbstract)childEntity).decreaseHunger(6000);
                    }
                } else if (motherEntity instanceof EnhancedSheep) {
                    if (((EnhancedSheep)motherEntity).decreaseMilk(1)) {
                        ((EnhancedAnimalAbstract)childEntity).decreaseHunger(6000);
                    }
                } else {
                    ((EnhancedAnimalAbstract)childEntity).decreaseHunger(6000);
                }
            }

            return shouldContinue;
        }
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting() {
        this.delayCounter = 0;
    }

    /**
     * Keep ticking a continuous task that has already been started
     */
    public void tick() {
        if (--this.delayCounter <= 0) {
            this.delayCounter = 10;
            this.childEntity.getNavigator().tryMoveToEntityLiving(this.motherEntity, this.moveSpeed);
        }
    }
}