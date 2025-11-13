package mokiyoki.enhancedanimals.capability.nestegg;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OccupiedNestCapabilityProvider extends NestCapabilityProvider implements INestEntityCapability {
    private static Block blockHost;
    Map<BlockPos, List<EntityHolder>> entitiesInNests = new HashMap<>();

    @Override
    public Map<BlockPos, List<EntityHolder>> getAllNestEntityPos() {
        return this.entitiesInNests;
    }

    @Override
    public void addNestEntityPos(BlockPos blockPos, EntityHolder entity) {
        List<EntityHolder> entityList = this.entitiesInNests.containsKey(blockPos) ? this.entitiesInNests.get(blockPos) : new ArrayList<>();
        entityList.add(entity);
        this.entitiesInNests.put(blockPos, entityList);
    }

    @Override
    public void removeNestEntityPos(BlockPos blockPos) {
        this.entitiesInNests.remove(blockPos);
    }

    @Override
    public void setAllNestEntityPos(Map<BlockPos, List<EntityHolder>> blockPosList) {
        this.entitiesInNests = blockPosList;
    }

    @Override
    public EntityHolder removeEntityFromNest(BlockPos blockPos) {
        List<EntityHolder> entitiesInNest = this.entitiesInNests.get(blockPos);
        if (entitiesInNest==null || entitiesInNest.isEmpty()) {
            return new EntityHolder();
        }
        return entitiesInNest.remove(0);
    }

    @Override
    public List<EntityHolder> getEntitiesInNest(BlockPos blockPos) {
        return this.entitiesInNests.get(blockPos);
    }

    @Override
    public EntityHolder getEntityInNest(BlockPos blockPos) {
        List<EntityHolder> entitiesInNest = this.entitiesInNests.get(blockPos);
        return entitiesInNest == null ? null : entitiesInNest.get(0);
    }
}
