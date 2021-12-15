package mokiyoki.enhancedanimals.ai.general.mooshroom;

import mokiyoki.enhancedanimals.ai.general.GrazingGoal;
import mokiyoki.enhancedanimals.entity.EnhancedAnimalAbstract;
import mokiyoki.enhancedanimals.init.ModBlocks;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.predicate.BlockStatePredicate;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;

import java.util.function.Predicate;

public class GrazingGoalMooshroom extends GrazingGoal {

    protected static final Predicate<BlockState> IS_MYCELIUM = BlockStatePredicate.forBlock(Blocks.MYCELIUM);
    protected static final Predicate<BlockState> IS_BROWN_MUSHROOM = BlockStatePredicate.forBlock(Blocks.BROWN_MUSHROOM);
    protected static final Predicate<BlockState> IS_RED_MUSHROOM = BlockStatePredicate.forBlock(Blocks.RED_MUSHROOM);

    public GrazingGoalMooshroom(EnhancedAnimalAbstract eanimal, double movementSpeed) {
        super(eanimal, movementSpeed);
    }

    @Override
    protected void eatBlocks() {
        BlockPos blockpos = new BlockPos(this.eanimal.blockPosition());
        if (IS_GRASS.test(this.eanimal.level.getBlockState(blockpos)) || IS_TALL_GRASS_BLOCK.test(this.eanimal.level.getBlockState(blockpos)) || IS_BROWN_MUSHROOM.test(this.eanimal.level.getBlockState(blockpos)) || IS_RED_MUSHROOM.test(this.eanimal.level.getBlockState(blockpos))) {
            if (net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.eanimal.level, this.eanimal)) {
                this.eanimal.level.destroyBlock(blockpos, false);
            }
            this.eanimal.ate();
        } else {
            BlockPos blockposDown = blockpos.below();
            if (this.eanimal.level.getBlockState(blockposDown).getBlock() == Blocks.GRASS_BLOCK) {
                if (net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.eanimal.level, this.eanimal)) {
                    this.eanimal.level.levelEvent(2001, blockposDown, Block.getId(Blocks.GRASS_BLOCK.defaultBlockState()));
                    this.eanimal.level.setBlock(blockposDown, ModBlocks.SPARSEGRASS_BLOCK.defaultBlockState(), 2);
                }
                this.eanimal.ate();
            }else if (this.eanimal.level.getBlockState(blockposDown).getBlock() == Blocks.MYCELIUM) {
                if (net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.eanimal.level, this.eanimal)) {
                    this.eanimal.level.levelEvent(2001, blockposDown, Block.getId(Blocks.MYCELIUM.defaultBlockState()));
                    this.eanimal.level.setBlock(blockposDown, ModBlocks.PATCHYMYCELIUM_BLOCK.defaultBlockState(), 2);
                }
                this.eanimal.ate();
            } else if (this.eanimal.level.getBlockState(blockposDown).getBlock() == ModBlocks.SPARSEGRASS_BLOCK) {
                if (net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.eanimal.level, this.eanimal)) {
                    this.eanimal.level.levelEvent(2001, blockposDown, Block.getId(Blocks.GRASS_BLOCK.defaultBlockState()));
                    this.eanimal.level.setBlock(blockposDown, Blocks.DIRT.defaultBlockState(), 2);
                }

                this.eanimal.ate();
            } else if (this.eanimal.level.getBlockState(blockposDown).getBlock() == ModBlocks.PATCHYMYCELIUM_BLOCK) {
                if (net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.eanimal.level, this.eanimal)) {
                    this.eanimal.level.levelEvent(2001, blockposDown, Block.getId(Blocks.MYCELIUM.defaultBlockState()));
                    this.eanimal.level.setBlock(blockposDown, Blocks.DIRT.defaultBlockState(), 2);
                }

                this.eanimal.ate();
            }
        }

    }

    @Override
    protected boolean isEdibleBlock(LevelReader worldIn, BlockPos pos) {
        BlockState blockstate = worldIn.getBlockState(pos);
        if (IS_GRASS_BLOCK.test(blockstate) || IS_BROWN_MUSHROOM.test(blockstate) || IS_RED_MUSHROOM.test(blockstate)/* || IS_GRASS.test(blockstate) || IS_SPARSE_GRASS_BLOCK.test(blockstate) || IS_TALL_GRASS_BLOCK.test(blockstate) || IS_MYCELIUM.test(blockstate)*/) {
            return true;
        }
        return false;
    }


}
