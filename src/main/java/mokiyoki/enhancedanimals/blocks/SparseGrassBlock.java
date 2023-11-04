package mokiyoki.enhancedanimals.blocks;

import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SpreadingSnowyDirtBlock;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.material.FluidState;
import net.minecraftforge.common.PlantType;

import static net.minecraftforge.common.PlantType.BEACH;
import static net.minecraftforge.common.PlantType.CAVE;
import static net.minecraftforge.common.PlantType.CROP;
import static net.minecraftforge.common.PlantType.DESERT;
import static net.minecraftforge.common.PlantType.NETHER;
import static net.minecraftforge.common.PlantType.PLAINS;
import static net.minecraftforge.common.PlantType.WATER;

public class SparseGrassBlock extends SpreadingSnowyDirtBlock {

    public SparseGrassBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource randomSource) {
        super.randomTick(state, level, pos, randomSource);
        if (!level.isClientSide) {
            if (!level.isAreaLoaded(pos, 3))
                return; // Forge: prevent loading unloaded chunks when checking neighbor's light and spreading
            if (level.getMaxLocalRawBrightness(pos.above()) >= 4) {
                if (level.getMaxLocalRawBrightness(pos.above()) >= 9) {
                    BlockState blockstate = this.defaultBlockState();

                    for (int i = 0; i < 4; ++i) {
                        BlockPos blockpos = pos.offset(randomSource.nextInt(3) - 1, randomSource.nextInt(5) - 3, randomSource.nextInt(3) - 1);
                        if (level.getBlockState(blockpos).getBlock() == Blocks.DIRT) {
                            level.setBlockAndUpdate(blockpos, blockstate.setValue(SNOWY, Boolean.valueOf(level.getBlockState(blockpos.above()).getBlock() == Blocks.SNOW)));
                        }
                    }
                }

            }
//            level.removeBlock(pos, false);
            level.setBlockAndUpdate(pos, Blocks.GRASS_BLOCK.defaultBlockState());
        }
    }

    @Override
    public boolean canSustainPlant(BlockState state, BlockGetter world, BlockPos pos, Direction facing, net.minecraftforge.common.IPlantable plantable) {
        if (plantable instanceof GrowablePlant || plantable instanceof GrowableDoubleHigh) return true;
        PlantType type = plantable.getPlantType(world, pos.relative(facing));

        if (DESERT.equals(type) || NETHER.equals(type) || CROP.equals(type) || WATER.equals(type)) {
            return false;
        } else if (CAVE.equals(type) || PLAINS.equals(type)) {
            return true;
        } else if (BEACH.equals(type)) {
            BlockPos blockpos = pos.below();
            for(Direction direction : Direction.Plane.HORIZONTAL) {
                BlockState blockstate1 = world.getBlockState(blockpos.relative(direction));
                FluidState fluidstate = world.getFluidState(blockpos.relative(direction));
                if (state.canBeHydrated(world, pos, fluidstate, blockpos.relative(direction)) || blockstate1.is(Blocks.FROSTED_ICE)) {
                    return true;
                }
            }
        }

        return false;
    }
}
