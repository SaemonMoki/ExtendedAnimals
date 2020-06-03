package mokiyoki.enhancedanimals.ai.general;

import mokiyoki.enhancedanimals.entity.EnhancedAnimal;
import mokiyoki.enhancedanimals.entity.EnhancedCow;
import mokiyoki.enhancedanimals.entity.EntityState;
import mokiyoki.enhancedanimals.entity.Temperament;
import mokiyoki.enhancedanimals.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.pattern.BlockStateMatcher;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.EnumSet;
import java.util.Map;
import java.util.function.Predicate;

public class EnhancedGrassGoal extends Goal {

    protected static final Predicate<BlockState> IS_GRASSBLOCK = BlockStateMatcher.forBlock(Blocks.GRASS);
//    private static final Predicate<BlockState> IS_GRASS = BlockStateMatcher.forBlock(Blocks.GRASS);
    protected static final Predicate<BlockState> IS_TALLGRASS = BlockStateMatcher.forBlock(Blocks.TALL_GRASS);
    protected final MobEntity grassEaterEntity;
    protected final World entityWorld;
    protected int eatingGrassTimer;
    Map<Temperament, Integer> temperaments;

    public EnhancedGrassGoal(MobEntity grassEaterEntityIn, Map<Temperament, Integer> temperaments) {
        this.grassEaterEntity = grassEaterEntityIn;
        this.entityWorld = grassEaterEntityIn.world;
        this.temperaments = temperaments;
        this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK, Goal.Flag.JUMP));
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute() {
        //TODO make the amount needed before 'hungry' using temperaments
        if (grassEaterEntity instanceof EnhancedCow && ((EnhancedCow)grassEaterEntity).getEntityStatus().equals(EntityState.CHILD_STAGE_ONE)) {
            //first stage babies should NOT eat grass
            return false;
        }
        int eatingModifier = Math.round(((EnhancedAnimal)grassEaterEntity).getHunger()/50);

        if (((EnhancedAnimal)grassEaterEntity).getHunger() > 12000) {
            eatingModifier = 999;
        }

        int chanceToEat = (this.grassEaterEntity.isChild() ? 50 : 1000) - eatingModifier;

        if (chanceToEat < 1) {
            chanceToEat = 1;
        }


        if (this.grassEaterEntity.getRNG().nextInt(chanceToEat) != 0) {
            return false;
        } else {
            BlockPos blockpos = new BlockPos(this.grassEaterEntity);

            //TODO add the predicate for different blocks to eat based on temperaments and animal type.
            if (IS_GRASSBLOCK.test(this.entityWorld.getBlockState(blockpos))) {
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
        ((EnhancedAnimal)this.grassEaterEntity).decreaseHunger(3000);
        this.eatingGrassTimer = 40;
        this.entityWorld.setEntityState(this.grassEaterEntity, (byte)10);
        this.grassEaterEntity.getNavigator().clearPath();
    }

    /**
     * Reset the task's internal state. Called when this task is interrupted by another one
     */
    public void resetTask() {
        this.eatingGrassTimer = 0;
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean shouldContinueExecuting() {
        return this.eatingGrassTimer > 0;
    }

    /**
     * Number of ticks since the entity started to eat grass
     */
    public int getEatingGrassTimer() {
        return this.eatingGrassTimer;
    }

    /**
     * Keep ticking a continuous task that has already been started
     */
    public void tick() {
        this.eatingGrassTimer = Math.max(0, this.eatingGrassTimer - 1);
        if (this.eatingGrassTimer == 4) {
            BlockPos blockpos = new BlockPos(this.grassEaterEntity);
            if (IS_TALLGRASS.test(this.entityWorld.getBlockState(blockpos))) {
                if (net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.entityWorld, this.grassEaterEntity)) {
                    this.entityWorld.destroyBlock(blockpos, false);
                    this.entityWorld.setBlockState(blockpos, Blocks.GRASS.getDefaultState());
                }

                this.grassEaterEntity.eatGrassBonus();

            } else if (IS_GRASSBLOCK.test(this.entityWorld.getBlockState(blockpos))) {
                if (net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.entityWorld, this.grassEaterEntity)) {
                    this.entityWorld.destroyBlock(blockpos, false);
                }

                this.grassEaterEntity.eatGrassBonus();

            } else {
                BlockPos blockpos1 = blockpos.down();
                if (this.entityWorld.getBlockState(blockpos1).getBlock() == Blocks.GRASS_BLOCK) {
                    if (net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.entityWorld, this.grassEaterEntity)) {
                        this.entityWorld.playEvent(2001, blockpos1, Block.getStateId(Blocks.GRASS_BLOCK.getDefaultState()));
//                        this.entityWorld.setBlockState(blockpos1, Blocks.DIRT.getDefaultState(), 2);
                        this.entityWorld.setBlockState(blockpos1, ModBlocks.SparseGrass_Block.getDefaultState(), 2);
                    }

                    this.grassEaterEntity.eatGrassBonus();
                } else if (this.entityWorld.getBlockState(blockpos1).getBlock() == ModBlocks.SparseGrass_Block) {
                    if (net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.entityWorld, this.grassEaterEntity)) {
                        this.entityWorld.playEvent(2001, blockpos1, Block.getStateId(Blocks.GRASS_BLOCK.getDefaultState()));
                        this.entityWorld.setBlockState(blockpos1, Blocks.DIRT.getDefaultState(), 2);
                    }

                    this.grassEaterEntity.eatGrassBonus();
                }
            }

        }
    }

}
