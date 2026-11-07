package mokiyoki.enhancedanimals.blocks;

import com.mojang.serialization.MapCodec;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockBehaviour;
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

public class PatchyMyceliumBlock extends SpreadingSnowyDirtBlock implements PlantSustainer {

    public static final MapCodec<PatchyMyceliumBlock> CODEC = simpleCodec(PatchyMyceliumBlock::new);

    @Override
    protected MapCodec<PatchyMyceliumBlock> codec() {
        return CODEC;
    }

    public PatchyMyceliumBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel worldIn, BlockPos pos, RandomSource random) {
        super.randomTick(state, worldIn, pos, random);
        if (!worldIn.isClientSide) {
            if (!worldIn.hasChunksAt(pos.offset(-3, -3, -3), pos.offset(3, 3, 3)))
                return; // Forge: prevent loading unloaded chunks when checking neighbor's light and spreading
            if (worldIn.getMaxLocalRawBrightness(pos.above()) >= 4) {
                if (worldIn.getMaxLocalRawBrightness(pos.above()) >= 9) {
                    BlockState blockstate = this.defaultBlockState();

                    for (int i = 0; i < 4; ++i) {
                        BlockPos blockpos = pos.offset(random.nextInt(3) - 1, random.nextInt(5) - 3, random.nextInt(3) - 1);
                        if (worldIn.getBlockState(blockpos).getBlock() == Blocks.DIRT) {
                            worldIn.setBlockAndUpdate(blockpos, blockstate.setValue(SNOWY, Boolean.valueOf(worldIn.getBlockState(blockpos.above()).getBlock() == Blocks.SNOW)));
                        }
                    }
                }

            }
//            worldIn.removeBlock(pos, false);
            worldIn.setBlockAndUpdate(pos, Blocks.MYCELIUM.defaultBlockState());
        }
    }

    public PlantSustainer.Result canSustainGeneticPlant(BlockState state, BlockGetter world, BlockPos pos, Direction facing, BlockState plantable)
    {
        LegacyPlantType type = LegacyPlantType.of(plantable);

        if (DESERT.equals(type) || NETHER.equals(type) || CROP.equals(type) || PLAINS.equals(type) || WATER.equals(type) || BEACH.equals(type)) {
            return PlantSustainer.Result.FALSE;
        } else if (CAVE.equals(type)) {
            return PlantSustainer.Result.TRUE;
        }

        return PlantSustainer.Result.FALSE;
    }

    //TODO find out what the new way is in 1.18+
//    @Nullable
//    @Override
//    public ToolType getHarvestTool(BlockState p_getHarvestTool_1_) {
//        return ToolType.SHOVEL;
//    }
}
