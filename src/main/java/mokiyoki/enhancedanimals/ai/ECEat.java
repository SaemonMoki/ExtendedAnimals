package mokiyoki.enhancedanimals.ai;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.predicate.BlockStatePredicate;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.ForgeEventFactory;

import java.util.EnumSet;
import java.util.function.Predicate;

public class ECEat extends Goal {
    private static final Predicate<BlockState> IS_TALL_GRASS = BlockStatePredicate.forBlock(Blocks.GRASS);

    private final Mob eatingEntity;
    private final Level entityWorld;
    int eatingTimer;

    public ECEat(Mob chickenEntity) {
        this.eatingEntity = chickenEntity;
        this.entityWorld = chickenEntity.level();
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK, Goal.Flag.JUMP));
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean canUse() {
        if (this.eatingEntity.getRandom().nextInt(this.eatingEntity.isBaby() ? 50 : 1000) != 0) {
            return false;
        } else {
            BlockPos blockpos = BlockPos.containing(this.eatingEntity.getX(), this.eatingEntity.getY(), this.eatingEntity.getZ());
            if (IS_TALL_GRASS.test(this.entityWorld.getBlockState(blockpos))) {
                return true;
            } else {
                return this.entityWorld.getBlockState(blockpos.below()).getBlock() == Blocks.GRASS_BLOCK;
            }
        }
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void start() {
        this.eatingTimer = 40;
        this.entityWorld.broadcastEntityEvent(this.eatingEntity, (byte)10);
        this.eatingEntity.getNavigation().stop();
    }

    /**
     * Reset the task's internal state. Called when this task is interrupted by another one
     */
    public void stop() {
        this.eatingTimer = 0;
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean canContinueToUse() {
        return this.eatingTimer > 0;
    }

    /**
     * Number of ticks since the entity started to eat grass
     */
    public int getEatingGrassTimer() {
        return this.eatingTimer;
    }

    /**
     * Keep ticking a continuous task that has already been started
     */
    public void updateTask() {
        this.eatingTimer = Math.max(0, this.eatingTimer - 1);
        if (this.eatingTimer == 4) {
            BlockPos blockpos = BlockPos.containing(this.eatingEntity.getX(), this.eatingEntity.getY(), this.eatingEntity.getZ());
            //this is to eat the tall grass if the chicken is IN the tall grass
            if (IS_TALL_GRASS.test(this.entityWorld.getBlockState(blockpos))) {
                if (ForgeEventFactory.getMobGriefingEvent(this.entityWorld, this.eatingEntity)) {
                    this.entityWorld.destroyBlock(blockpos, false);
                }
                this.eatingEntity.ate();
            } else {
                BlockPos blockpos1 = blockpos.below();
                if (this.entityWorld.getBlockState(blockpos1).getBlock() == Blocks.GRASS) {
                    if (ForgeEventFactory.getMobGriefingEvent(this.entityWorld, this.eatingEntity)) {
                        this.entityWorld.levelEvent(2001, blockpos1, Block.getId(Blocks.GRASS_BLOCK.defaultBlockState()));
                        this.entityWorld.setBlock(blockpos1, Blocks.DIRT.defaultBlockState(), 2);
                    }

                    this.eatingEntity.ate();
                }
            }
        }
    }
}
