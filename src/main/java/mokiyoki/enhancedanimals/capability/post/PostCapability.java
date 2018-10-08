package mokiyoki.enhancedanimals.capability.post;

import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by saemon on 29/09/2018.
 */
public class PostCapability implements IPostCapability {

    List<BlockPos> postBlockPositions = new ArrayList<>();

    @Override
    public List<BlockPos> getAllPostPos() {
        return postBlockPositions;
    }

    @Override
    public void addPostPos(BlockPos blockPos) {
        postBlockPositions.add(blockPos);
    }

    @Override
    public void removePostPos(BlockPos blockPos) {
        postBlockPositions.remove(blockPos);
    }

    @Override
    public void setAllPostPos(List<BlockPos> blockPosList) {
        this.postBlockPositions = blockPosList;
    }
}
