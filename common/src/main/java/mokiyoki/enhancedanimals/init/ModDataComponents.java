package mokiyoki.enhancedanimals.init;

import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.component.CustomData;
import mokiyoki.enhancedanimals.platform.registry.RegistryEntry;
import mokiyoki.enhancedanimals.platform.registry.RegistrationProvider;

import static mokiyoki.enhancedanimals.util.Reference.MODID;

public class ModDataComponents {

    public static final RegistrationProvider<DataComponentType<?>> DATA_COMPONENT_TYPES_DEFERRED_REGISTRY = RegistrationProvider.get(Registries.DATA_COMPONENT_TYPE, MODID);

    public static final RegistryEntry<DataComponentType<?>, DataComponentType<CustomData>> EGG_DATA = DATA_COMPONENT_TYPES_DEFERRED_REGISTRY.register("egg_data", () -> DataComponentType.<CustomData>builder().persistent(CustomData.CODEC).networkSynchronized(CustomData.STREAM_CODEC).build());

    public static void init() {
    }
}
