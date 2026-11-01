package mokiyoki.enhancedanimals.init;

import mokiyoki.enhancedanimals.tileentity.ChickenNestTileEntity;
import mokiyoki.enhancedanimals.tileentity.EggCartonTileEntity;
import mokiyoki.enhancedanimals.util.Reference;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.registries.DeferredHolder;

public class ModTileEntities {

    public static final DeferredRegister<BlockEntityType<?>> TILE_ENTITIES_DEFERRED_REGISTRY = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, Reference.MODID);

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<EggCartonTileEntity>> EGG_CARTON_TILE_ENTITY = TILE_ENTITIES_DEFERRED_REGISTRY.register("egg_carton_tile_entity", () -> BlockEntityType.Builder.of(EggCartonTileEntity::new, ModBlocks.EGG_CARTON.get()).build(null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<ChickenNestTileEntity>> CHICKEN_NEST_TILE_ENTITY = TILE_ENTITIES_DEFERRED_REGISTRY.register("chicken_nest_tile_entity", () -> BlockEntityType.Builder.of(ChickenNestTileEntity::new, ModBlocks.CHICKEN_NEST.get()).build(null));

    public static void register(IEventBus modEventBus) {
        TILE_ENTITIES_DEFERRED_REGISTRY.register(modEventBus);
    }
}
