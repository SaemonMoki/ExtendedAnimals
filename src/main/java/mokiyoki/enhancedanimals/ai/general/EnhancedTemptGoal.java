package mokiyoki.enhancedanimals.ai.general;

import mokiyoki.enhancedanimals.entity.EnhancedAnimalAbstract;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.GrassBlock;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.EnumSet;

public class EnhancedTemptGoal extends Goal {
    private static final TargetingConditions CLOSE_SEARCH_ENTITY_PREDICATE = TargetingConditions.forNonCombat().range(5.0D).ignoreLineOfSight();
    private static final TargetingConditions REGULAR_ENTITY_PREDICATE = TargetingConditions.forNonCombat().range(10.0D).ignoreLineOfSight();

    private final double speed;
    private final double fastspeed;
    private double targetX;
    private double targetY;
    private double targetZ;
    private double pitch;
    private double yaw;
    protected Player closestPlayer;
    private int delayTemptCounter;
    private boolean isRunning;
    private final Item temptItem;
    private final boolean scaredByPlayerMovement;
    private static final int VERY_HUNGRY = 24000; //If above this will follow without tempt item
    private static final int REGULAR_HUNGRY = 12000; //If above this but below the other, will act as regular minecraft

    protected final EnhancedAnimalAbstract eanimal;

    public EnhancedTemptGoal(EnhancedAnimalAbstract eanimal, double walkspeed, double fastspeed, boolean scaredByPlayerMovementIn, Item item) {
        this.eanimal = eanimal;
        this.speed = walkspeed;
        this.fastspeed = fastspeed;
        this.temptItem = item;
        this.scaredByPlayerMovement = scaredByPlayerMovementIn;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    public boolean canUse() {
        if (this.delayTemptCounter > 0) {
            --this.delayTemptCounter;
            return false;
        } else if (eanimal.isAnimalSleeping()) {
            return false;
        } else {
            this.closestPlayer = this.eanimal.level().getNearestPlayer(getSearchPredicate(), this.eanimal);
            if (this.closestPlayer == null) {
                return false;
            } else if (this.temptItem != Items.AIR && (this.temptItem == this.closestPlayer.getMainHandItem().getItem() || this.temptItem == this.closestPlayer.getOffhandItem().getItem())) {
                return true;
            } else {
                 if (this.eanimal.level().getBlockState(this.eanimal.blockPosition().below()).getBlock() instanceof GrassBlock) {
                     return this.eanimal.isFoodItem(this.closestPlayer.getMainHandItem()) || this.eanimal.isFoodItem(this.closestPlayer.getOffhandItem());
                }
                return this.isTempting(this.closestPlayer.getMainHandItem()) || this.isTempting(this.closestPlayer.getOffhandItem());
            }
        }
    }

    private TargetingConditions getSearchPredicate() {
        if (this.eanimal.getHunger() > REGULAR_HUNGRY) {
            return REGULAR_ENTITY_PREDICATE;
        } else {
            return CLOSE_SEARCH_ENTITY_PREDICATE;
        }
    }

    protected boolean isTempting(ItemStack stack) {
        if (this.eanimal.getHunger() > VERY_HUNGRY) {
            return true;
        }
        return this.eanimal.isFoodItem(stack);
    }

    public boolean canContinueToUse() {
        if (this.isScaredByPlayerMovement()) {
            if (this.eanimal.distanceToSqr(this.closestPlayer) < getDistanceFromPlayer()) {
                if (this.closestPlayer.distanceToSqr(this.targetX, this.targetY, this.targetZ) > 0.010000000000000002D) {
                    return false;
                }

                if (Math.abs((double)this.closestPlayer.getXRot() - this.pitch) > 5.0D || Math.abs((double)this.closestPlayer.getYRot() - this.yaw) > 5.0D) {
                    return false;
                }
            } else {
                this.targetX = this.closestPlayer.getX();
                this.targetY = this.closestPlayer.getY();
                this.targetZ = this.closestPlayer.getZ();
            }

            this.pitch = (double)this.closestPlayer.getXRot();
            this.yaw = (double)this.closestPlayer.getYRot();
        }

        return this.canUse();
    }

    private double getDistanceFromPlayer() {
        if (this.eanimal.getHunger() > VERY_HUNGRY) {
            return 24.0D;
        } else if (this.eanimal.getHunger() > REGULAR_HUNGRY) {
            return 36.0D;
        } else {
            return 58.0D;
        }

    }

    protected boolean isScaredByPlayerMovement() {
        return this.scaredByPlayerMovement;
    }

    public void start() {
        this.targetX = this.closestPlayer.getX();
        this.targetY = this.closestPlayer.getY();
        this.targetZ = this.closestPlayer.getZ();
        this.isRunning = true;
    }

    public void stop() {
        this.closestPlayer = null;
        this.eanimal.getNavigation().stop();
        this.delayTemptCounter = 100;
        this.isRunning = false;
    }

    public void tick() {
        this.eanimal.getLookControl().setLookAt(this.closestPlayer, (float)(this.eanimal.getMaxHeadYRot() + 20), (float)this.eanimal.getMaxHeadXRot());
        if (this.eanimal.distanceToSqr(this.closestPlayer) < 6.25D) {
            this.eanimal.getNavigation().stop();
        } else {
            this.eanimal.getNavigation().moveTo(this.closestPlayer, getWalkingToPlayerSpeed());
        }

    }

    private double getWalkingToPlayerSpeed() {
        if (this.eanimal.getHunger() > REGULAR_HUNGRY) {
            return this.fastspeed;
        } else {
            return (this.fastspeed + this.speed )/2;
        }

    }

    //TODO this is for cats when we get to them
//    public boolean isRunning() {
//        return this.isRunning;
//    }


}
