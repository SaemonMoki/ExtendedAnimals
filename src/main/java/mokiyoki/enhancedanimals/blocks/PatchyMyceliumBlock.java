package mokiyoki.enhancedanimals.blocks;

import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SpreadingSnowyDirtBlock;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.common.PlantType;

import javax.annotation.Nullable;
import java.util.Random;

import static net.minecraftforge.common.PlantType.BEACH;
import static net.minecraftforge.common.PlantType.CAVE;
import static net.minecraftforge.common.PlantType.CROP;
import static net.minecraftforge.common.PlantType.DESERT;
import static net.minecraftforge.common.PlantType.NETHER;
import static net.minecraftforge.common.PlantType.PLAINS;
import static net.minecraftforge.common.PlantType.WATER;

public class PatchyMyceliumBlock extends SpreadingSnowyDirtBlock {

    public PatchyMyceliumBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel worldIn, BlockPos pos, Random random) {
        super.randomTick(state, worldIn, pos, random);
        if (!worldIn.isClientSide) {
            if (!worldIn.isAreaLoaded(pos, 3))
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

    @Override
    public boolean canSustainPlant(BlockState state, BlockGetter world, BlockPos pos, Direction facing, net.minecraftforge.common.IPlantable plantable)
    {
        PlantType type = plantable.getPlantType(world, pos.relative(facing));

        if (DESERT.equals(type) || NETHER.equals(type) || CROP.equals(type) || PLAINS.equals(type) || WATER.equals(type) || BEACH.equals(type)) {
            return false;
        } else if (CAVE.equals(type)) {
            return true;
        }

        return false;
    }

    //TODO find out what the new way is
//    @Nullable
//    @Override
//    public ToolType getHarvestTool(BlockState p_getHarvestTool_1_) {
//        return ToolType.SHOVEL;
//    }
}
