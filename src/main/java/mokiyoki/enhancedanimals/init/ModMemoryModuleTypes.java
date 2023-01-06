package mokiyoki.enhancedanimals.init;

import com.mojang.serialization.Codec;
import mokiyoki.enhancedanimals.util.Reference;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.Optional;

public class ModMemoryModuleTypes {

    public static final DeferredRegister<MemoryModuleType<?>> MEMORY_MODULE_TYPE_DEFERRED_REGISTRY = DeferredRegister.create(ForgeRegistries.MEMORY_MODULE_TYPES, Reference.MODID);

    public static final RegistryObject<MemoryModuleType<Boolean>> HAS_EGG = MEMORY_MODULE_TYPE_DEFERRED_REGISTRY.register("has_egg", () -> new MemoryModuleType<Boolean>(Optional.of(Codec.BOOL)));

    public static void register(IEventBus modEventBus) {
        MEMORY_MODULE_TYPE_DEFERRED_REGISTRY.register(modEventBus);
    }
}
