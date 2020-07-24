package mokiyoki.enhancedanimals.ai.general.mooshroom;

import mokiyoki.enhancedanimals.ai.general.GrazingGoal;
import mokiyoki.enhancedanimals.entity.EnhancedAnimalAbstract;
import mokiyoki.enhancedanimals.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.pattern.BlockStateMatcher;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;

import java.util.function.Predicate;

public class GrazingGoalMooshroom extends GrazingGoal {

    protected static final Predicate<BlockState> IS_MYCELIUM = BlockStateMatcher.forBlock(Blocks.MYCELIUM);
    protected static final Predicate<BlockState> IS_BROWN_MUSHROOM = BlockStateMatcher.forBlock(Blocks.BROWN_MUSHROOM);
    protected static final Predicate<BlockState> IS_RED_MUSHROOM = BlockStateMatcher.forBlock(Blocks.RED_MUSHROOM);

    public GrazingGoalMooshroom(EnhancedAnimalAbstract eanimal, double movementSpeed) {
        super(eanimal, movementSpeed);
    }

    @Override
    protected void eatBlocks() {
        BlockPos blockpos = new BlockPos(this.eanimal);
        if (IS_GRASS.test(this.eanimal.world.getBlockState(blockpos)) || IS_TALL_GRASS_BLOCK.test(this.eanimal.world.getBlockState(blockpos)) || IS_BROWN_MUSHROOM.test(this.eanimal.world.getBlockState(blockpos)) || IS_RED_MUSHROOM.test(this.eanimal.world.getBlockState(blockpos))) {
            if (net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.eanimal.world, this.eanimal)) {
                this.eanimal.world.destroyBlock(blockpos, false);
            }
            this.eanimal.eatGrassBonus();
        } else {
            BlockPos blockposDown = blockpos.down();
            if (this.eanimal.world.getBlockState(blockposDown).getBlock() == Blocks.GRASS_BLOCK) {
                if (net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.eanimal.world, this.eanimal)) {
                    this.eanimal.world.playEvent(2001, blockposDown, Block.getStateId(Blocks.GRASS_BLOCK.getDefaultState()));
                    this.eanimal.world.setBlockState(blockposDown, ModBlocks.SPARSE_GRASS_BLOCK.getDefaultState(), 2);
                }
                this.eanimal.eatGrassBonus();
            }else if (this.eanimal.world.getBlockState(blockposDown).getBlock() == Blocks.MYCELIUM) {
                if (net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.eanimal.world, this.eanimal)) {
                    this.eanimal.world.playEvent(2001, blockposDown, Block.getStateId(Blocks.MYCELIUM.getDefaultState()));
                    this.eanimal.world.setBlockState(blockposDown, ModBlocks.PATCHY_MYCELIUM_BLOCK.getDefaultState(), 2);
                }
                this.eanimal.eatGrassBonus();
            } else if (this.eanimal.world.getBlockState(blockposDown).getBlock() == ModBlocks.SPARSE_GRASS_BLOCK) {
                if (net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.eanimal.world, this.eanimal)) {
                    this.eanimal.world.playEvent(2001, blockposDown, Block.getStateId(Blocks.GRASS_BLOCK.getDefaultState()));
                    this.eanimal.world.setBlockState(blockposDown, Blocks.DIRT.getDefaultState(), 2);
                }

                this.eanimal.eatGrassBonus();
            } else if (this.eanimal.world.getBlockState(blockposDown).getBlock() == ModBlocks.PATCHY_MYCELIUM_BLOCK) {
                if (net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.eanimal.world, this.eanimal)) {
                    this.eanimal.world.playEvent(2001, blockposDown, Block.getStateId(Blocks.MYCELIUM.getDefaultState()));
                    this.eanimal.world.setBlockState(blockposDown, Blocks.DIRT.getDefaultState(), 2);
                }

                this.eanimal.eatGrassBonus();
            }
        }

    }

    @Override
    protected boolean isEdibleBlock(IWorldReader worldIn, BlockPos pos) {
        BlockState blockstate = worldIn.getBlockState(pos);
        if (IS_GRASS_BLOCK.test(blockstate) || IS_GRASS.test(blockstate) || IS_SPARSE_GRASS_BLOCK.test(blockstate) || IS_TALL_GRASS_BLOCK.test(blockstate) || IS_MYCELIUM.test(blockstate) || IS_BROWN_MUSHROOM.test(blockstate) || IS_RED_MUSHROOM.test(blockstate)) {
            return true;
        }
        return false;
    }


}
