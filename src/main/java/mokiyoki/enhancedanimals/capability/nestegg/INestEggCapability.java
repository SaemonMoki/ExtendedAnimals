package mokiyoki.enhancedanimals.capability.nestegg;

import mokiyoki.enhancedanimals.util.Genes;
import net.minecraft.core.BlockPos;

import java.util.List;
import java.util.Map;

public interface INestEggCapability {

    Map<BlockPos, List<EggHolder>> getAllNestEggPos();

    void addNestEggPos(BlockPos blockPos, String sire, String dam, Genes genes, boolean hasParents);

    void removeNestPos(BlockPos blockPos);

    void setAllNestEggPos(Map<BlockPos, List<EggHolder>> blockPosList);

    EggHolder removeEggFromNest (BlockPos blockPos);

    List<EggHolder> removeEggsFromNest (BlockPos blockPos);

    List<EggHolder> getEggsInNest(BlockPos blockPos);

    EggHolder getEggInNest(BlockPos blockPos);

}
