package mokiyoki.enhancedanimals.ai.general.pig;

import mokiyoki.enhancedanimals.ai.general.EnhancedWaterAvoidingRandomWalkingEatingGoal;
import mokiyoki.enhancedanimals.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.pattern.BlockStateMatcher;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.util.math.BlockPos;

import java.util.function.Predicate;

public class EnhancedWaterAvoidingRandomWalkingEatingGoalPig extends EnhancedWaterAvoidingRandomWalkingEatingGoal {

    protected static final Predicate<BlockState> IS_MUSHROOM = BlockStateMatcher.forBlock(Blocks.BROWN_MUSHROOM);

    public EnhancedWaterAvoidingRandomWalkingEatingGoalPig(CreatureEntity creature, double speedIn, int length, float probabilityIn, int wanderExecutionChance, int depth, int hungerModifier) {
        super(creature, speedIn, length, probabilityIn, wanderExecutionChance, depth, hungerModifier);
    }

    @Override
    protected void eatBlocks() {
        int root = this.entityWorld.rand.nextInt(3);
        if (root == 2) {
            //just nibbling
            super.eatBlocks();
        } else {

            BlockPos blockpos = new BlockPos(this.creature);
            if (IS_GRASS.test(this.entityWorld.getBlockState(blockpos)) || IS_TALL_GRASS_BLOCK.test(this.entityWorld.getBlockState(blockpos))|| IS_MUSHROOM.test(this.entityWorld.getBlockState(blockpos))) {
                if (net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.entityWorld, this.creature)) {
                    this.entityWorld.destroyBlock(blockpos, false);
                }
                this.creature.eatGrassBonus();
            } else {
                BlockPos blockpos1 = blockpos.down();
                if (this.entityWorld.getBlockState(blockpos1).getBlock() == Blocks.GRASS_BLOCK) {
                    if (net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.entityWorld, this.creature)) {
                        this.entityWorld.playEvent(2001, blockpos1, Block.getStateId(Blocks.GRASS_BLOCK.getDefaultState()));
                        this.entityWorld.setBlockState(blockpos1, Blocks.FARMLAND.getDefaultState(), 2);
                    }

                    this.creature.eatGrassBonus();
                } else if (this.entityWorld.getBlockState(blockpos1).getBlock() == ModBlocks.SparseGrass_Block) {
                    if (net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.entityWorld, this.creature)) {
                        this.entityWorld.playEvent(2001, blockpos1, Block.getStateId(Blocks.GRASS_BLOCK.getDefaultState()));
                        this.entityWorld.setBlockState(blockpos1, Blocks.FARMLAND.getDefaultState(), 2);
                    }

                    this.creature.eatGrassBonus();
                }
            }

        }

    }
}
