package mokiyoki.enhancedanimals.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SpreadableSnowyDirtBlock;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.PlantType;
import net.minecraftforge.common.ToolType;

import javax.annotation.Nullable;
import java.util.Random;

import static net.minecraftforge.common.PlantType.BEACH;
import static net.minecraftforge.common.PlantType.CAVE;
import static net.minecraftforge.common.PlantType.CROP;
import static net.minecraftforge.common.PlantType.DESERT;
import static net.minecraftforge.common.PlantType.NETHER;
import static net.minecraftforge.common.PlantType.PLAINS;
import static net.minecraftforge.common.PlantType.WATER;

public class PatchyMyceliumBlock extends SpreadableSnowyDirtBlock {

    public PatchyMyceliumBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void tick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) {
        super.tick(state, worldIn, pos, random);
        if (!worldIn.isRemote) {
            if (!worldIn.isAreaLoaded(pos, 3))
                return; // Forge: prevent loading unloaded chunks when checking neighbor's light and spreading
            if (worldIn.getLight(pos.up()) >= 4) {
                if (worldIn.getLight(pos.up()) >= 9) {
                    BlockState blockstate = this.getDefaultState();

                    for (int i = 0; i < 4; ++i) {
                        BlockPos blockpos = pos.add(random.nextInt(3) - 1, random.nextInt(5) - 3, random.nextInt(3) - 1);
                        if (worldIn.getBlockState(blockpos).getBlock() == Blocks.DIRT) {
                            worldIn.setBlockState(blockpos, blockstate.with(SNOWY, Boolean.valueOf(worldIn.getBlockState(blockpos.up()).getBlock() == Blocks.SNOW)));
                        }
                    }
                }

            }
//            worldIn.removeBlock(pos, false);
            worldIn.setBlockState(pos, Blocks.MYCELIUM.getDefaultState());
        }
    }

    @Override
    public boolean canSustainPlant(BlockState state, IBlockReader world, BlockPos pos, Direction facing, net.minecraftforge.common.IPlantable plantable)
    {
        PlantType type = plantable.getPlantType(world, pos.offset(facing));

        if (DESERT.equals(type) || NETHER.equals(type) || CROP.equals(type) || PLAINS.equals(type) || WATER.equals(type) || BEACH.equals(type)) {
            return false;
        } else if (CAVE.equals(type)) {
            return true;
        }

        return false;
    }

    @Nullable
    @Override
    public ToolType getHarvestTool(BlockState p_getHarvestTool_1_) {
        return ToolType.SHOVEL;
    }
}
