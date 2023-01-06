package mokiyoki.enhancedanimals.capability.post;

import net.minecraft.core.BlockPos;

import java.util.List;

/**
 * Created by saemon on 29/09/2018.
 */
public interface IPostCapability {

    List<BlockPos> getAllPostPos();

    void addPostPos(BlockPos blockPos);

    void removePostPos(BlockPos blockPos);

    void setAllPostPos(List<BlockPos> blockPosList);
}
