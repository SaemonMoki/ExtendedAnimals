package mokiyoki.enhancedanimals.ai.general;

import mokiyoki.enhancedanimals.entity.EnhancedAnimalAbstract;
import net.minecraft.block.GrassBlock;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;

import java.util.EnumSet;

public class EnhancedTemptGoal extends Goal {
    private static final EntityPredicate CLOSE_SEARCH_ENTITY_PREDICATE = (new EntityPredicate()).setDistance(5.0D).allowInvulnerable().allowFriendlyFire().setSkipAttackChecks().setLineOfSiteRequired();
    private static final EntityPredicate REGULAR_ENTITY_PREDICATE = (new EntityPredicate()).setDistance(10.0D).allowInvulnerable().allowFriendlyFire().setSkipAttackChecks().setLineOfSiteRequired();

    private final double speed;
    private final double fastspeed;
    private double targetX;
    private double targetY;
    private double targetZ;
    private double pitch;
    private double yaw;
    protected PlayerEntity closestPlayer;
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
        this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    public boolean shouldExecute() {
        if (this.delayTemptCounter > 0) {
            --this.delayTemptCounter;
            return false;
        } else if (eanimal.isAnimalSleeping()) {
            return false;
        } else {
            this.closestPlayer = this.eanimal.world.getClosestPlayer(getSearchPredicate(), this.eanimal);
            if (this.closestPlayer == null) {
                return false;
            } else if (this.temptItem != Items.AIR && (this.temptItem == this.closestPlayer.getHeldItemMainhand().getItem() || this.temptItem == this.closestPlayer.getHeldItemOffhand().getItem())) {
                return true;
            } else {
                 if (this.eanimal.world.getBlockState(this.eanimal.getPosition().down()).getBlock() instanceof GrassBlock) {
                     return this.eanimal.isFoodItem(this.closestPlayer.getHeldItemMainhand()) || this.eanimal.isFoodItem(this.closestPlayer.getHeldItemOffhand());
                }
                return this.isTempting(this.closestPlayer.getHeldItemMainhand()) || this.isTempting(this.closestPlayer.getHeldItemOffhand());
            }
        }
    }

    private EntityPredicate getSearchPredicate() {
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

    public boolean shouldContinueExecuting() {
        if (this.isScaredByPlayerMovement()) {
            if (this.eanimal.getDistanceSq(this.closestPlayer) < getDistanceFromPlayer()) {
                if (this.closestPlayer.getDistanceSq(this.targetX, this.targetY, this.targetZ) > 0.010000000000000002D) {
                    return false;
                }

                if (Math.abs((double)this.closestPlayer.rotationPitch - this.pitch) > 5.0D || Math.abs((double)this.closestPlayer.rotationYaw - this.yaw) > 5.0D) {
                    return false;
                }
            } else {
                this.targetX = this.closestPlayer.getPosX();
                this.targetY = this.closestPlayer.getPosY();
                this.targetZ = this.closestPlayer.getPosZ();
            }

            this.pitch = (double)this.closestPlayer.rotationPitch;
            this.yaw = (double)this.closestPlayer.rotationYaw;
        }

        return this.shouldExecute();
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

    public void startExecuting() {
        this.targetX = this.closestPlayer.getPosX();
        this.targetY = this.closestPlayer.getPosY();
        this.targetZ = this.closestPlayer.getPosZ();
        this.isRunning = true;
    }

    public void resetTask() {
        this.closestPlayer = null;
        this.eanimal.getNavigator().clearPath();
        this.delayTemptCounter = 100;
        this.isRunning = false;
    }

    public void tick() {
        this.eanimal.getLookController().setLookPositionWithEntity(this.closestPlayer, (float)(this.eanimal.getHorizontalFaceSpeed() + 20), (float)this.eanimal.getVerticalFaceSpeed());
        if (this.eanimal.getDistanceSq(this.closestPlayer) < 6.25D) {
            this.eanimal.getNavigator().clearPath();
        } else {
            this.eanimal.getNavigator().tryMoveToEntityLiving(this.closestPlayer, getWalkingToPlayerSpeed());
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
