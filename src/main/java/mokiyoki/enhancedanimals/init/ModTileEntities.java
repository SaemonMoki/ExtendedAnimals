package mokiyoki.enhancedanimals.init;

import mokiyoki.enhancedanimals.tileentity.EggCartonTileEntity;
import mokiyoki.enhancedanimals.util.Reference;
import net.minecraft.Util;
import net.minecraft.util.datafix.fixes.References;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModTileEntities {

    public static final DeferredRegister<BlockEntityType<?>> TILE_ENTITIES_DEFERRED_REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, Reference.MODID);

    public static final RegistryObject<BlockEntityType<EggCartonTileEntity>> EGG_CARTON_TILE_ENTITY = TILE_ENTITIES_DEFERRED_REGISTRY.register("egg_carton_tile_entity", () -> BlockEntityType.Builder.of(EggCartonTileEntity::new, ModBlocks.EGG_CARTON.get()).build(null));

    public static void register(IEventBus modEventBus) {
        TILE_ENTITIES_DEFERRED_REGISTRY.register(modEventBus);
    }
}
