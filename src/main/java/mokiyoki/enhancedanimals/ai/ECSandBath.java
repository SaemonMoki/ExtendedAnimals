package mokiyoki.enhancedanimals.ai;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.EnumSet;

public class ECSandBath  extends Goal
{
    /** The entity owner of this AITask */
    private final MobEntity sandBatherEntity;
    /** The world the sand bather entity is bathing from */
    private final World entityWorld;
    /** Number of ticks since the entity started to bathe */
    int sandBathTimer;
    //TODO needs to tell what kind of sand they are bathing on

    public void getSandType() {

    }

    public ECSandBath(MobEntity sandBatherEntityIn) {
        this.sandBatherEntity = sandBatherEntityIn;
        this.entityWorld = sandBatherEntityIn.world;
        this.setMutexFlags(EnumSet.of(Flag.MOVE, Flag.JUMP));
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
        if (this.sandBatherEntity.getRNG().nextInt(this.sandBatherEntity.isChild() ? 50 : 1000) != 0)
        {
            return false;
        }
        else
        {
            BlockPos blockpos = new BlockPos(this.sandBatherEntity.getPosX(), this.sandBatherEntity.getPosY(), this.sandBatherEntity.getPosZ());
            return this.entityWorld.getBlockState(blockpos.down()).getBlock() == Blocks.SAND;
        }
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting()
    {
        this.sandBathTimer = 40;
        this.entityWorld.setEntityState(this.sandBatherEntity, (byte)10);
        this.sandBatherEntity.getNavigator().clearPath();
    }

    /**
     * Reset the task's internal state. Called when this task is interrupted by another one
     */
    public void resetTask()
    {
        this.sandBathTimer = 0;
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean shouldContinueExecuting()
    {
        return this.sandBathTimer > 0;
    }

    /**
     * Number of ticks since the entity started to eat grass
     */
    public int getSandBathTimer()
    {
        return this.sandBathTimer;
    }

    /**
     * Keep ticking a continuous task that has already been started
     */
    public void updateTask() {
        this.sandBathTimer = Math.max(0, this.sandBathTimer - 1);

        if (this.sandBathTimer == 4) {
            BlockPos blockpos = new BlockPos(this.sandBatherEntity.getPosX(), this.sandBatherEntity.getPosY(), this.sandBatherEntity.getPosZ());
                BlockPos blockpos1 = blockpos.down();

                if (this.entityWorld.getBlockState(blockpos1).getBlock() == Blocks.SAND) {
                    if (net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.entityWorld, this.sandBatherEntity)) {
                        this.entityWorld.playEvent(2001, blockpos1, Block.getStateId(Blocks.SAND.getDefaultState()));
                        this.entityWorld.setBlockState(blockpos1, Blocks.SAND.getDefaultState(), 2);
                    }
                    this.sandBatherEntity.eatGrassBonus();
                }

        }
    }
}