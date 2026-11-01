package mokiyoki.enhancedanimals.ai.brain;

import mokiyoki.enhancedanimals.entity.EnhancedAnimalAbstract;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.level.pathfinder.PathComputationType;

public class ValidatePath {

    public static boolean isValidPath(EnhancedAnimalAbstract eaa, BlockPos destination, int nodeSearchLimit) {
        if (eaa.level() instanceof ServerLevel serverLevel) {
            Path path = eaa.getNavigation().createPath(destination, 0, nodeSearchLimit);

            return path != null && !path.isDone() && path.getNodeCount() > 0 && isPathReachable(serverLevel, path, destination);
        }
        return false;
    }

    private static boolean isPathReachable(ServerLevel serverLevel, Path path, BlockPos destination) {
        BlockPos finalPathPoint = path.getEndNode().asBlockPos();

        if (!finalPathPoint.closerThan(destination, 1.0)) {
            return false;
        }

        BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();
        for (int i = 0; i < path.getNodeCount(); i++) {
            mutablePos.set(path.getNode(i).asBlockPos());
            if (!isPathfindable(serverLevel.getBlockState(mutablePos), serverLevel, mutablePos, PathComputationType.LAND)) {
                return false;
            }
        }

        return true;
    }

    public static boolean isPathfindable(BlockState blockState, BlockGetter blockGetter, BlockPos destination, PathComputationType computationType) {
        switch (computationType) {
            case LAND, AIR -> {
                return !blockState.isCollisionShapeFullBlock(blockGetter, destination);
            }
            case WATER -> {
                return blockGetter.getFluidState(destination).is(FluidTags.WATER);
            }
            default -> {
                return false;
            }
        }
    }

}
