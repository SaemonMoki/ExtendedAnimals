/*
package mokiyoki.enhancedanimals.ai.general;

import mokiyoki.enhancedanimals.entity.EnhancedAnimalAbstract;
import mokiyoki.enhancedanimals.entity.EntityState;
import mokiyoki.enhancedanimals.entity.Temperament;
import mokiyoki.enhancedanimals.init.ModBlocks;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.predicate.BlockStatePredicate;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

import java.util.EnumSet;
import java.util.Map;
import java.util.function.Predicate;

public class EnhancedGrassGoal extends Goal {

    protected static final Predicate<BlockState> IS_GRASSBLOCK = BlockStatePredicate.forBlock(Blocks.GRASS);
//    private static final Predicate<BlockState> IS_GRASS = BlockStateMatcher.forBlock(Blocks.GRASS);
    protected static final Predicate<BlockState> IS_TALLGRASS = BlockStatePredicate.forBlock(Blocks.TALL_GRASS);
    protected final Mob grassEaterEntity;
    protected final Level entityWorld;
    protected int eatingGrassTimer;
    Map<Temperament, Integer> temperaments;

    public EnhancedGrassGoal(Mob grassEaterEntityIn, Map<Temperament, Integer> temperaments) {
        this.grassEaterEntity = grassEaterEntityIn;
        this.entityWorld = grassEaterEntityIn.level;
        this.temperaments = temperaments;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK, Goal.Flag.JUMP));
    }

    */
/**
     * Returns whether the EntityAIBase should begin execution.
     *//*

    public boolean canUse() {
        //TODO make the amount needed before 'hungry' using temperaments
        if (grassEaterEntity instanceof EnhancedCow && ((EnhancedCow)grassEaterEntity).getEntityStatus().equals(EntityState.CHILD_STAGE_ONE)) {
            //first stage babies should NOT eat grass
            return false;
        }
        int eatingModifier = Math.round(((EnhancedAnimalAbstract)grassEaterEntity).getHunger()/50);

        if (((EnhancedAnimalAbstract)grassEaterEntity).getHunger() > 12000) {
            eatingModifier = 999;
        }

        int chanceToEat = (this.grassEaterEntity.isBaby() ? 50 : 1000) - eatingModifier;

        if (chanceToEat < 1) {
            chanceToEat = 1;
        }


        if (this.grassEaterEntity.getRandom().nextInt(chanceToEat) != 0) {
            return false;
        } else {
            BlockPos blockpos = new BlockPos(this.grassEaterEntity.blockPosition());

            //TODO add the predicate for different blocks to eat based on temperaments and animal type.
            if (IS_GRASSBLOCK.test(this.entityWorld.getBlockState(blockpos))) {
                return true;
            } else {
                return this.entityWorld.getBlockState(blockpos.below()).getBlock() == Blocks.GRASS_BLOCK;
            }
        }
    }

    */
/**
     * Execute a one shot task or start executing a continuous task
     *//*

    public void start() {
        ((EnhancedAnimalAbstract)this.grassEaterEntity).decreaseHunger(3000);
        this.eatingGrassTimer = 40;
        this.entityWorld.broadcastEntityEvent(this.grassEaterEntity, (byte)10);
        this.grassEaterEntity.getNavigation().stop();
    }

    */
/**
     * Reset the task's internal state. Called when this task is interrupted by another one
     *//*

    public void stop() {
        this.eatingGrassTimer = 0;
    }

    */
/**
     * Returns whether an in-progress EntityAIBase should continue executing
     *//*

    public boolean canContinueToUse() {
        return this.eatingGrassTimer > 0;
    }

    */
/**
     * Number of ticks since the entity started to eat grass
     *//*

    public int getEatingGrassTimer() {
        return this.eatingGrassTimer;
    }

    */
/**
     * Keep ticking a continuous task that has already been started
     *//*

    public void tick() {
        this.eatingGrassTimer = Math.max(0, this.eatingGrassTimer - 1);
        if (this.eatingGrassTimer == 4) {
            BlockPos blockpos = new BlockPos(this.grassEaterEntity.blockPosition());
            if (IS_TALLGRASS.test(this.entityWorld.getBlockState(blockpos))) {
                if (net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.entityWorld, this.grassEaterEntity)) {
                    this.entityWorld.destroyBlock(blockpos, false);
                    this.entityWorld.setBlockAndUpdate(blockpos, Blocks.GRASS.defaultBlockState());
                }

                this.grassEaterEntity.ate();

            } else if (IS_GRASSBLOCK.test(this.entityWorld.getBlockState(blockpos))) {
                if (net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.entityWorld, this.grassEaterEntity)) {
                    this.entityWorld.destroyBlock(blockpos, false);
                }

                this.grassEaterEntity.ate();

            } else {
                BlockPos blockpos1 = blockpos.below();
                if (this.entityWorld.getBlockState(blockpos1).getBlock() == Blocks.GRASS_BLOCK) {
                    if (net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.entityWorld, this.grassEaterEntity)) {
                        this.entityWorld.levelEvent(2001, blockpos1, Block.getId(Blocks.GRASS_BLOCK.defaultBlockState()));
//                        this.entityWorld.setBlockState(blockpos1, Blocks.DIRT.getDefaultState(), 2);
                        this.entityWorld.setBlock(blockpos1, ModBlocks.SPARSEGRASS_BLOCK.get().defaultBlockState(), 2);
                    }

                    this.grassEaterEntity.ate();
                } else if (this.entityWorld.getBlockState(blockpos1).getBlock() == ModBlocks.SPARSEGRASS_BLOCK.get()) {
                    if (net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.entityWorld, this.grassEaterEntity)) {
                        this.entityWorld.levelEvent(2001, blockpos1, Block.getId(Blocks.GRASS_BLOCK.defaultBlockState()));
                        this.entityWorld.setBlock(blockpos1, Blocks.DIRT.defaultBlockState(), 2);
                    }

                    this.grassEaterEntity.ate();
                }
            }

        }
    }

}
*/
