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

    protected static final Predicate<BlockState> IS_MYCELIUM = BlockStateMatcher.forBlock(Blocks.MYCELIUM);
    protected static final Predicate<BlockState> IS_BROWN_MUSHROOM = BlockStateMatcher.forBlock(Blocks.BROWN_MUSHROOM);
    protected static final Predicate<BlockState> IS_RED_MUSHROOM = BlockStateMatcher.forBlock(Blocks.RED_MUSHROOM);

    public EnhancedWaterAvoidingRandomWalkingEatingGoalMooshroom(CreatureEntity creature, double speedIn, int length, float probabilityIn, int wanderExecutionChance, int depth, int hungerModifier) {
        super(creature, speedIn, length, probabilityIn, wanderExecutionChance, depth, hungerModifier);
    }

    @Override
    protected boolean checkForFood() {
        BlockPos blockpos = new BlockPos(this.creature);

        //TODO add the predicate for different blocks to eat based on temperaments and animal type.
        BlockState blockState = this.entityWorld.getBlockState(blockpos);
        if (IS_GRASS.test(this.entityWorld.getBlockState(blockpos)) || IS_TALL_GRASS_BLOCK.test(this.entityWorld.getBlockState(blockpos)) || IS_BROWN_MUSHROOM.test(this.entityWorld.getBlockState(blockpos)) || IS_RED_MUSHROOM.test(this.entityWorld.getBlockState(blockpos))) {
            return true;
        } else {
            BlockState blockStateDown = this.entityWorld.getBlockState(blockpos.down());
            return blockStateDown.getBlock() == Blocks.GRASS_BLOCK || blockStateDown.getBlock() == ModBlocks.SPARSE_GRASS_BLOCK || blockStateDown.getBlock() == Blocks.MYCELIUM || blockStateDown.getBlock() == ModBlocks.PATCHY_MYCELIUM_BLOCK;
        }
    }

    @Override
    protected void eatBlocks() {
        BlockPos blockpos = new BlockPos(this.creature);
        if (IS_GRASS.test(this.entityWorld.getBlockState(blockpos)) || IS_TALL_GRASS_BLOCK.test(this.entityWorld.getBlockState(blockpos)) || IS_BROWN_MUSHROOM.test(this.entityWorld.getBlockState(blockpos)) || IS_RED_MUSHROOM.test(this.entityWorld.getBlockState(blockpos))) {
            if (net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.entityWorld, this.creature)) {
                this.entityWorld.destroyBlock(blockpos, false);
            }
            this.creature.eatGrassBonus();
        } else {
            BlockPos blockposDown = blockpos.down();
            if (this.entityWorld.getBlockState(blockposDown).getBlock() == Blocks.GRASS_BLOCK) {
                if (net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.entityWorld, this.creature)) {
                    this.entityWorld.playEvent(2001, blockposDown, Block.getStateId(Blocks.GRASS_BLOCK.getDefaultState()));
                    this.entityWorld.setBlockState(blockposDown, ModBlocks.SPARSE_GRASS_BLOCK.getDefaultState(), 2);
                }
                this.creature.eatGrassBonus();
            }else if (this.entityWorld.getBlockState(blockposDown).getBlock() == Blocks.MYCELIUM) {
                if (net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.entityWorld, this.creature)) {
                    this.entityWorld.playEvent(2001, blockposDown, Block.getStateId(Blocks.MYCELIUM.getDefaultState()));
                    this.entityWorld.setBlockState(blockposDown, ModBlocks.PATCHY_MYCELIUM_BLOCK.getDefaultState(), 2);
                }
                this.creature.eatGrassBonus();
            } else if (this.entityWorld.getBlockState(blockposDown).getBlock() == ModBlocks.SPARSE_GRASS_BLOCK) {
                if (net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.entityWorld, this.creature)) {
                    this.entityWorld.playEvent(2001, blockposDown, Block.getStateId(Blocks.GRASS_BLOCK.getDefaultState()));
                    this.entityWorld.setBlockState(blockposDown, Blocks.DIRT.getDefaultState(), 2);
                }

                this.creature.eatGrassBonus();
            } else if (this.entityWorld.getBlockState(blockposDown).getBlock() == ModBlocks.PATCHY_MYCELIUM_BLOCK) {
                if (net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.entityWorld, this.creature)) {
                    this.entityWorld.playEvent(2001, blockposDown, Block.getStateId(Blocks.MYCELIUM.getDefaultState()));
                    this.entityWorld.setBlockState(blockposDown, Blocks.DIRT.getDefaultState(), 2);
                }

                this.creature.eatGrassBonus();
            }
        }

    }

    @Override
    protected boolean shouldMoveTo(IWorldReader worldIn, BlockPos pos) {
        BlockState blockstate = worldIn.getBlockState(pos);
        if (IS_GRASS_BLOCK.test(blockstate) || IS_GRASS.test(blockstate) || IS_SPARSE_GRASS_BLOCK.test(blockstate) || IS_TALL_GRASS_BLOCK.test(blockstate) || IS_MYCELIUM.test(blockstate) || IS_BROWN_MUSHROOM.test(blockstate) || IS_RED_MUSHROOM.test(blockstate)) {
            return true;
        }
        return false;
    }

}
