//package mokiyoki.enhancedanimals.renderer.colour;
//
//import net.minecraft.block.BlockState;
//import net.minecraft.client.renderer.color.IBlockColor;
//import net.minecraft.util.math.BlockPos;
//import net.minecraft.world.GrassColors;
//import net.minecraft.world.IEnviromentBlockReader;
//import net.minecraft.world.biome.BiomeColors;
//
//import javax.annotation.Nullable;
//
//public class SparseGrassBlockColour implements IBlockColor {
//
//    @Override
//    public int getColor(BlockState blockState, @Nullable IEnviromentBlockReader iEnviromentBlockReader, @Nullable BlockPos blockPos, int tintIndex) {
//        if (iEnviromentBlockReader != null && blockPos != null) {
//            return BiomeColors.getGrassColor(iEnviromentBlockReader, blockPos);
//        }
//
//        return GrassColors.get(0.5d, 1.0d);
//    }
//}
