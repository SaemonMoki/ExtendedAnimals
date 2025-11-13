package mokiyoki.enhancedanimals.capability.nestegg;

import mokiyoki.enhancedanimals.util.Genes;
import net.minecraft.core.BlockPos;

import java.util.List;
import java.util.Map;

public interface INestEntityCapability {

    Map<BlockPos, List<EntityHolder>> getAllNestEntityPos();

    void addNestEntityPos(BlockPos blockPos, EntityHolder entityHolder);

    void removeNestEntityPos(BlockPos blockPos);

    void setAllNestEntityPos(Map<BlockPos, List<EntityHolder>> blockPosList);

    EntityHolder removeEntityFromNest (BlockPos blockPos);

    List<EntityHolder> getEntitiesInNest(BlockPos blockPos);

    EntityHolder getEntityInNest(BlockPos blockPos);
}
