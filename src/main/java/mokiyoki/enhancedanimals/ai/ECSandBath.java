package mokiyoki.enhancedanimals.ai;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

import java.util.EnumSet;

import net.minecraft.world.entity.ai.goal.Goal.Flag;

public class ECSandBath  extends Goal
{
    /** The entity owner of this AITask */
    private final Mob sandBatherEntity;
    /** The world the sand bather entity is bathing from */
    private final Level entityWorld;
    /** Number of ticks since the entity started to bathe */
    int sandBathTimer;
    //TODO needs to tell what kind of sand they are bathing on

    public void getSandType() {

    }

    public ECSandBath(Mob sandBatherEntityIn) {
        this.sandBatherEntity = sandBatherEntityIn;
        this.entityWorld = sandBatherEntityIn.level();
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.JUMP));
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean canUse()
    {
        if (this.sandBatherEntity.getRandom().nextInt(this.sandBatherEntity.isBaby() ? 50 : 1000) != 0)
        {
            return false;
        }
        else
        {
            BlockPos blockpos = BlockPos.containing(this.sandBatherEntity.getX(), this.sandBatherEntity.getY(), this.sandBatherEntity.getZ());
            return this.entityWorld.getBlockState(blockpos.below()).getBlock() == Blocks.SAND;
        }
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void start()
    {
        this.sandBathTimer = 40;
        this.entityWorld.broadcastEntityEvent(this.sandBatherEntity, (byte)10);
        this.sandBatherEntity.getNavigation().stop();
    }

    /**
     * Reset the task's internal state. Called when this task is interrupted by another one
     */
    public void stop()
    {
        this.sandBathTimer = 0;
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean canContinueToUse()
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
            BlockPos blockpos = BlockPos.containing(this.sandBatherEntity.getX(), this.sandBatherEntity.getY(), this.sandBatherEntity.getZ());
                BlockPos blockpos1 = blockpos.below();

                if (this.entityWorld.getBlockState(blockpos1).getBlock() == Blocks.SAND) {
                    if (net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.entityWorld, this.sandBatherEntity)) {
                        this.entityWorld.levelEvent(2001, blockpos1, Block.getId(Blocks.SAND.defaultBlockState()));
                        this.entityWorld.setBlock(blockpos1, Blocks.SAND.defaultBlockState(), 2);
                    }
                    this.sandBatherEntity.ate();
                }

        }
    }
}