package mokiyoki.enhancedanimals.ai;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.pattern.BlockStateMatcher;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;

import java.util.EnumSet;
import java.util.function.Predicate;

public class ECEat extends Goal {
    private static final Predicate<BlockState> IS_TALL_GRASS = BlockStateMatcher.forBlock(Blocks.GRASS);

    private final MobEntity eatingEntity;
    private final World entityWorld;
    int eatingTimer;

    public ECEat(MobEntity chickenEntity) {
        this.eatingEntity = chickenEntity;
        this.entityWorld = chickenEntity.world;
        this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK, Goal.Flag.JUMP));
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute() {
        if (this.eatingEntity.getRNG().nextInt(this.eatingEntity.isChild() ? 50 : 1000) != 0) {
            return false;
        } else {
            BlockPos blockpos = new BlockPos(this.eatingEntity.getPosX(), this.eatingEntity.getPosY(), this.eatingEntity.getPosZ());
            if (IS_TALL_GRASS.test(this.entityWorld.getBlockState(blockpos))) {
                return true;
            } else {
                return this.entityWorld.getBlockState(blockpos.down()).getBlock() == Blocks.GRASS_BLOCK;
            }
        }
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting() {
        this.eatingTimer = 40;
        this.entityWorld.setEntityState(this.eatingEntity, (byte)10);
        this.eatingEntity.getNavigator().clearPath();
    }

    /**
     * Reset the task's internal state. Called when this task is interrupted by another one
     */
    public void resetTask() {
        this.eatingTimer = 0;
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean shouldContinueExecuting() {
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
            BlockPos blockpos = new BlockPos(this.eatingEntity.getPosX(), this.eatingEntity.getPosY(), this.eatingEntity.getPosZ());
            //this is to eat the tall grass if the chicken is IN the tall grass
            if (IS_TALL_GRASS.test(this.entityWorld.getBlockState(blockpos))) {
                if (ForgeEventFactory.getMobGriefingEvent(this.entityWorld, this.eatingEntity)) {
                    this.entityWorld.destroyBlock(blockpos, false);
                }
                this.eatingEntity.eatGrassBonus();
            } else {
                BlockPos blockpos1 = blockpos.down();
                if (this.entityWorld.getBlockState(blockpos1).getBlock() == Blocks.GRASS) {
                    if (ForgeEventFactory.getMobGriefingEvent(this.entityWorld, this.eatingEntity)) {
                        this.entityWorld.playEvent(2001, blockpos1, Block.getStateId(Blocks.GRASS_BLOCK.getDefaultState()));
                        this.entityWorld.setBlockState(blockpos1, Blocks.DIRT.getDefaultState(), 2);
                    }

                    this.eatingEntity.eatGrassBonus();
                }
            }
        }
    }
}
