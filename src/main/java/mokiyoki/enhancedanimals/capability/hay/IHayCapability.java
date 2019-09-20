package mokiyoki.enhancedanimals.capability.hay;

import net.minecraft.util.math.BlockPos;

import java.util.List;
import java.util.Set;

/**
 * Created by saemon on 29/09/2018.
 */
public interface IHayCapability {

    Set<BlockPos> getAllHayPos();

    void addHayPos(BlockPos blockPos);

    void removeHayPos(BlockPos blockPos);

    void setAllHayPos(Set<BlockPos> blockPosList);
}
