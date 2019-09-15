package mokiyoki.enhancedanimals.ai.general.mooshroom;

import mokiyoki.enhancedanimals.ai.general.EnhancedWaterAvoidingRandomWalkingEatingGoal;
import mokiyoki.enhancedanimals.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.pattern.BlockStateMatcher;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;

import java.util.function.Predicate;

public class EnhancedWaterAvoidingRandomWalkingEatingGoalMooshroom extends EnhancedWaterAvoidingRandomWalkingEatingGoal {

    protected static final Predicate<BlockState> IS_MYCELIUM = BlockStateMatcher.forBlock(Blocks.TALL_GRASS);
    protected static final Predicate<BlockState> IS_BROWN_MUSHROOM = BlockStateMatcher.forBlock(Blocks.BROWN_MUSHROOM);
    protected static final Predicate<BlockState> IS_RED_MUSHROOM = BlockStateMatcher.forBlock(Blocks.RED_MUSHROOM);

    public EnhancedWaterAvoidingRandomWalkingEatingGoalMooshroom(CreatureEntity creature, double speedIn, int length, float probabilityIn, int wanderExecutionChance, int depth) {
        super(creature, speedIn, length, probabilityIn, wanderExecutionChance, depth);
    }

    @Override
    protected boolean shouldMoveTo(IWorldReader worldIn, BlockPos pos) {
        BlockState blockstate = worldIn.getBlockState(pos);
        if (IS_GRASS_BLOCK.test(blockstate) || IS_GRASS.test(blockstate) || IS_SPARSE_GRASS_BLOCK.test(blockstate) || IS_TALL_GRASS_BLOCK.test(blockstate) || IS_MYCELIUM.test(blockstate) || IS_BROWN_MUSHROOM.test(blockstate) || IS_RED_MUSHROOM.test(blockstate)) {
            return true;
        }
        return false;
    }

    @Override
    protected void eatBlocks() {
            BlockPos blockpos = new BlockPos(this.creature);
            BlockState blockState = this.entityWorld.getBlockState(blockpos);
            if (IS_GRASS.test(blockState) || IS_TALL_GRASS_BLOCK.test(blockState) || IS_BROWN_MUSHROOM.test(blockState) || IS_RED_MUSHROOM.test(blockState)) {
                if (net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.entityWorld, this.creature)) {
                    this.entityWorld.destroyBlock(blockpos, false);
                }
                this.creature.eatGrassBonus();
            } else {
                BlockPos blockposDown = blockpos.down();
                BlockState blockStateDown = this.entityWorld.getBlockState(blockposDown);
                if (blockStateDown.getBlock() == Blocks.GRASS_BLOCK) {
                    if (net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.entityWorld, this.creature)) {
                        this.entityWorld.playEvent(2001, blockposDown, Block.getStateId(Blocks.GRASS_BLOCK.getDefaultState()));
                        this.entityWorld.setBlockState(blockposDown, ModBlocks.SparseGrass_Block.getDefaultState(), 2);
                    }

                    this.creature.eatGrassBonus();
                } else if (blockStateDown.getBlock() == ModBlocks.SparseGrass_Block || blockStateDown.getBlock() == Blocks.MYCELIUM) {
                    if (net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.entityWorld, this.creature)) {
                        this.entityWorld.playEvent(2001, blockposDown, Block.getStateId(Blocks.GRASS_BLOCK.getDefaultState()));
                        this.entityWorld.setBlockState(blockposDown, Blocks.DIRT.getDefaultState(), 2);
                    }

                    this.creature.eatGrassBonus();
                }
            }

    }
}
