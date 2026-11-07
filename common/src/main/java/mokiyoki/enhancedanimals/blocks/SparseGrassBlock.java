package mokiyoki.enhancedanimals.blocks;

import com.mojang.serialization.MapCodec;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SpreadingSnowyDirtBlock;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.server.level.ServerLevel;
import mokiyoki.enhancedanimals.util.LegacyPlantType;

import org.jetbrains.annotations.Nullable;

import static mokiyoki.enhancedanimals.util.LegacyPlantType.BEACH;
import static mokiyoki.enhancedanimals.util.LegacyPlantType.CAVE;
import static mokiyoki.enhancedanimals.util.LegacyPlantType.CROP;
import static mokiyoki.enhancedanimals.util.LegacyPlantType.DESERT;
import static mokiyoki.enhancedanimals.util.LegacyPlantType.NETHER;
import static mokiyoki.enhancedanimals.util.LegacyPlantType.PLAINS;
import static mokiyoki.enhancedanimals.util.LegacyPlantType.WATER;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class SparseGrassBlock extends SpreadingSnowyDirtBlock implements PlantSustainer {

    public static final MapCodec<SparseGrassBlock> CODEC = simpleCodec(SparseGrassBlock::new);

    @Override
    protected MapCodec<SparseGrassBlock> codec() {
        return CODEC;
    }

    public SparseGrassBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        super.randomTick(state, level, pos, random);
        if (!level.isClientSide) {
            if (!level.hasChunksAt(pos.offset(-3, -3, -3), pos.offset(3, 3, 3)))
                return; // Forge: prevent loading unloaded chunks when checking neighbor's light and spreading
            if (level.getMaxLocalRawBrightness(pos.above()) >= 4) {
                if (level.getMaxLocalRawBrightness(pos.above()) >= 9) {
                    BlockState blockstate = this.defaultBlockState();

                    for (int i = 0; i < 4; ++i) {
                        BlockPos blockpos = pos.offset(random.nextInt(3) - 1, random.nextInt(5) - 3, random.nextInt(3) - 1);
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

    public PlantSustainer.Result canSustainGeneticPlant(BlockState state, BlockGetter world, BlockPos pos, Direction facing, BlockState plantable) {
        if (plantable.getBlock() instanceof GrowablePlant || plantable.getBlock() instanceof GrowableDoubleHigh) return PlantSustainer.Result.TRUE;
        LegacyPlantType type = LegacyPlantType.of(plantable);

        if (DESERT.equals(type) || NETHER.equals(type) || CROP.equals(type) || WATER.equals(type)) {
            return PlantSustainer.Result.FALSE;
        } else if (CAVE.equals(type) || PLAINS.equals(type)) {
            return PlantSustainer.Result.TRUE;
        } else if (BEACH.equals(type)) {
            boolean hasWater = (isWater(world.getBlockState(pos.east())) ||
                    isWater(world.getBlockState(pos.west())) ||
                    isWater(world.getBlockState(pos.north())) ||
                    isWater(world.getBlockState(pos.south())));
            return hasWater ? PlantSustainer.Result.TRUE : PlantSustainer.Result.FALSE;
        }

        return PlantSustainer.Result.FALSE;
    }

    private static boolean isWater(BlockState state) {
        return state.is(Blocks.WATER) || state.is(Blocks.BUBBLE_COLUMN);
    }

//    @Nullable
//    @Override
//    public ToolType getHarvestTool(BlockState p_getHarvestTool_1_) {
//        return ToolType.SHOVEL;
//    }
}
