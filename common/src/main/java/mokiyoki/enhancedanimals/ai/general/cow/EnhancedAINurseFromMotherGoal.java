//package mokiyoki.enhancedanimals.ai.general.cow;
//
//import mokiyoki.enhancedanimals.entity.EnhancedAnimalAbstract;
//import mokiyoki.enhancedanimals.entity.EnhancedCow;
//import mokiyoki.enhancedanimals.entity.EnhancedSheep;
//import net.minecraft.world.entity.ai.goal.Goal;
//import net.minecraft.world.entity.animal.Animal;
//
//import java.util.List;
//
//public class EnhancedAINurseFromMotherGoal extends Goal {
//    private final EnhancedAnimalAbstract childEntity;
//    private EnhancedAnimalAbstract motherEntity;
//    private String motherUUID;
//    private final double moveSpeed;
//    private int delayCounter;
//
//    public EnhancedAINurseFromMotherGoal(EnhancedAnimalAbstract child, String motherUUID, double speed) {
//        this.childEntity = child;
//        this.motherUUID = motherUUID;
//        this.moveSpeed = speed;
//    }
//
//    /**
//     * Returns whether the EntityAIBase should begin execution.
//     */
//    public boolean canUse() {
//        if (this.childEntity.isVehicle() || this.childEntity.isAnimalSleeping()) {
//            return false;
//        }
//
//        if (!this.childEntity.isChild() || this.childEntity.getNoActionTime() >= 100) {
//            return false;
//        } else if (((EnhancedAnimalAbstract)this.childEntity).getHunger() > 1000) {
//            List<Animal> list = this.childEntity.level.getEntitiesOfClass(this.childEntity.getClass(), this.childEntity.getBoundingBox().inflate(8.0D, 4.0D, 8.0D));
//            Animal animalentity = null;
//            double d0 = Double.MAX_VALUE;
//
//            for(Animal animalentity1 : list) {
//                if (animalentity1.getUUID().equals(motherUUID)) {
//                    double d1 = this.childEntity.distanceToSqr(animalentity1);
//                    if (!(d1 > d0)) {
//                        d0 = d1;
//                        animalentity = animalentity1;
//                    }
//                }
//            }
//
//            if (animalentity == null) {
//                return false;
//            } else if (d0 < 2.0D) {
//                return false;
//            } else {
//                if (animalentity instanceof EnhancedAnimalAbstract) {
//                    this.motherEntity = (EnhancedAnimalAbstract)animalentity;
//                    return true;
//                }
//                return false;
//            }
//        }
//
//        return false;
//    }
//
//    /**
//     * Returns whether an in-progress EntityAIBase should continue executing
//     */
//    public boolean canContinueToUse() {
//        if (!this.childEntity.isChild()) {
//            return false;
//        } else if (!this.motherEntity.isAlive()) {
//            return false;
//        } else {
//            double d0 = this.childEntity.distanceToSqr(this.motherEntity);
//            boolean shouldContinue = !(d0 < 2.0D) && !(d0 > 256.0D);
//            if (!shouldContinue) {
//                if (motherEntity instanceof EnhancedCow) {
//                    if (((EnhancedCow)motherEntity).decreaseMilk(2)) {
//                        ((EnhancedAnimalAbstract)childEntity).decreaseHunger(6000);
//                    }
//                } else if (motherEntity instanceof EnhancedSheep) {
//                    if (((EnhancedSheep)motherEntity).decreaseMilk(1)) {
//                        ((EnhancedAnimalAbstract)childEntity).decreaseHunger(6000);
//                    }
//                } else {
//                    ((EnhancedAnimalAbstract)childEntity).decreaseHunger(6000);
//                }
//            }
//
//            return shouldContinue;
//        }
//    }
//
//    /**
//     * Execute a one shot task or start executing a continuous task
//     */
//    public void start() {
//        this.delayCounter = 0;
//    }
//
//    /**
//     * Keep ticking a continuous task that has already been started
//     */
//    public void tick() {
//        if (--this.delayCounter <= 0) {
//            this.delayCounter = 10;
//            this.childEntity.getNavigation().moveTo(this.motherEntity, this.moveSpeed);
//        }
//    }
//}