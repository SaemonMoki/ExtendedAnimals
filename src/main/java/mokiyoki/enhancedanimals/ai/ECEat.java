package mokiyoki.enhancedanimals.ai;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.BlockStateMatcher;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;

import java.util.function.Predicate;

public class ECEat extends EntityAIBase {
    private static final Predicate<IBlockState> IS_TALL_GRASS = BlockStateMatcher.forBlock(Blocks.GRASS);

    private final EntityLiving eatingEntity;
    private final World entityWorld;
    int eatingTimer;

    public ECEat(EntityLiving chickenEntity) {
        this.eatingEntity = chickenEntity;
        this.entityWorld = chickenEntity.world;
        this.setMutexBits(7);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute() {
        if (this.eatingEntity.getRNG().nextInt(this.eatingEntity.isChild() ? 50 : 1000) != 0) {
            return false;
        } else {
            BlockPos blockpos = new BlockPos(this.eatingEntity.posX, this.eatingEntity.posY, this.eatingEntity.posZ);
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
            BlockPos blockpos = new BlockPos(this.eatingEntity.posX, this.eatingEntity.posY, this.eatingEntity.posZ);
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
