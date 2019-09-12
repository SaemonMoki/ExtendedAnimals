package mokiyoki.enhancedanimals.capability.hay;

import net.minecraft.util.math.BlockPos;

import java.util.List;

/**
 * Created by saemon on 29/09/2018.
 */
public interface IHayCapability {

    List<BlockPos> getAllHayPos();

    void addHayPos(BlockPos blockPos);

    void removeHayPos(BlockPos blockPos);

    void setAllHayPos(List<BlockPos> blockPosList);
}
