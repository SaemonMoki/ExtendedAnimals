package mokiyoki.enhancedanimals.capability.turtleegg;

import mokiyoki.enhancedanimals.util.Genes;
import net.minecraft.util.math.BlockPos;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface INestEggCapability {

    Map<BlockPos, List<EggHolder>> getAllNestEggPos();

    void addNestEggPos(BlockPos blockPos, String sire, String dam, Genes genes);

    void removeNestPos(BlockPos blockPos);

    void setAllNestEggPos(Map<BlockPos, List<EggHolder>> blockPosList);

    EggHolder removeEggFromNest (BlockPos blockPos);

    List<EggHolder> removeEggsFromNest (BlockPos blockPos);



}
