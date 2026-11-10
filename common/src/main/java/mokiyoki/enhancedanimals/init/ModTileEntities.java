package mokiyoki.enhancedanimals.init;

import mokiyoki.enhancedanimals.tileentity.ChickenNestTileEntity;
import mokiyoki.enhancedanimals.tileentity.EggCartonTileEntity;
import mokiyoki.enhancedanimals.util.Reference;
import net.minecraft.world.level.block.entity.BlockEntityType;
import mokiyoki.enhancedanimals.platform.registry.RegistrationProvider;
import net.minecraft.core.registries.Registries;
import mokiyoki.enhancedanimals.platform.registry.RegistryEntry;

public class ModTileEntities {

    public static final RegistrationProvider<BlockEntityType<?>> TILE_ENTITIES_DEFERRED_REGISTRY = RegistrationProvider.get(Registries.BLOCK_ENTITY_TYPE, Reference.MODID);

    public static final RegistryEntry<BlockEntityType<?>, BlockEntityType<EggCartonTileEntity>> EGG_CARTON_TILE_ENTITY = TILE_ENTITIES_DEFERRED_REGISTRY.register("egg_carton_tile_entity", () -> BlockEntityType.Builder.of(EggCartonTileEntity::new, ModBlocks.EGG_CARTON.get()).build(null));
    public static final RegistryEntry<BlockEntityType<?>, BlockEntityType<ChickenNestTileEntity>> CHICKEN_NEST_TILE_ENTITY = TILE_ENTITIES_DEFERRED_REGISTRY.register("chicken_nest_tile_entity", () -> BlockEntityType.Builder.of(ChickenNestTileEntity::new, ModBlocks.CHICKEN_NEST.get()).build(null));

    public static void init() {
    }
}
