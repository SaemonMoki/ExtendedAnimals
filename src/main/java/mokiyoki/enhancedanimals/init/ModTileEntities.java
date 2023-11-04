package mokiyoki.enhancedanimals.init;

import mokiyoki.enhancedanimals.util.tileentity.EggCartonTileEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static mokiyoki.enhancedanimals.GeneticAnimals.MODID;

public class ModTileEntities {

    public static final DeferredRegister<BlockEntityType<?>> TILE_ENTITIES_DEFERRED_REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, MODID);

    public static final RegistryObject<BlockEntityType<EggCartonTileEntity>> EGG_CARTON_TILE_ENTITY = TILE_ENTITIES_DEFERRED_REGISTRY.register("egg_carton_tile_entity", () -> BlockEntityType.Builder.of(EggCartonTileEntity::new, ModBlocks.EGG_CARTON.get()).build(null));

    public static void register(IEventBus modEventBus) {
        TILE_ENTITIES_DEFERRED_REGISTRY.register(modEventBus);
    }
}
