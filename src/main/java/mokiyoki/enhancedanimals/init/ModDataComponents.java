package mokiyoki.enhancedanimals.init;

import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.component.CustomData;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import static mokiyoki.enhancedanimals.util.Reference.MODID;

public class ModDataComponents {

    public static final DeferredRegister<DataComponentType<?>> DATA_COMPONENT_TYPES_DEFERRED_REGISTRY = DeferredRegister.create(Registries.DATA_COMPONENT_TYPE, MODID);

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<CustomData>> EGG_DATA = DATA_COMPONENT_TYPES_DEFERRED_REGISTRY.register("egg_data", () -> DataComponentType.<CustomData>builder().persistent(CustomData.CODEC).networkSynchronized(CustomData.STREAM_CODEC).build());

    public static void register(IEventBus modEventBus) {
        DATA_COMPONENT_TYPES_DEFERRED_REGISTRY.register(modEventBus);
    }
}
