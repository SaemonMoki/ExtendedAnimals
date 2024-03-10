package mokiyoki.enhancedanimals.init;

import com.mojang.serialization.Codec;
import mokiyoki.enhancedanimals.util.Reference;
import net.minecraft.core.SerializableUUID;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.Optional;
import java.util.UUID;

public class ModMemoryModuleTypes {

    public static final DeferredRegister<MemoryModuleType<?>> MEMORY_MODULE_TYPE_DEFERRED_REGISTRY = DeferredRegister.create(ForgeRegistries.MEMORY_MODULE_TYPES, Reference.MODID);

    public static final RegistryObject<MemoryModuleType<Boolean>> HAS_EGG = MEMORY_MODULE_TYPE_DEFERRED_REGISTRY.register("has_egg", () -> new MemoryModuleType<Boolean>(Optional.of(Codec.BOOL)));
    public static final RegistryObject<MemoryModuleType<Boolean>> PAUSE_BRAIN = MEMORY_MODULE_TYPE_DEFERRED_REGISTRY.register("pause_brain", () -> new MemoryModuleType<Boolean>(Optional.of(Codec.BOOL)));
    public static final RegistryObject<MemoryModuleType<Integer>> PAUSE_BETWEEN_EATING = MEMORY_MODULE_TYPE_DEFERRED_REGISTRY.register("pause_between_eating", () -> new MemoryModuleType<Integer>(Optional.of(Codec.INT)));
    public static final RegistryObject<MemoryModuleType<Boolean>> FOCUS_BRAIN = MEMORY_MODULE_TYPE_DEFERRED_REGISTRY.register("focus_brain", () -> new MemoryModuleType<Boolean>(Optional.of(Codec.BOOL)));
    public static final RegistryObject<MemoryModuleType<Boolean>> SEEKING_SHELTER = MEMORY_MODULE_TYPE_DEFERRED_REGISTRY.register("seeking_shelter", () -> new MemoryModuleType<Boolean>(Optional.of(Codec.BOOL)));
    public static final RegistryObject<MemoryModuleType<Boolean>> BROODING = MEMORY_MODULE_TYPE_DEFERRED_REGISTRY.register("brooding", () -> new MemoryModuleType<Boolean>(Optional.of(Codec.BOOL)));
    public static final RegistryObject<MemoryModuleType<Boolean>> ROOSTING = MEMORY_MODULE_TYPE_DEFERRED_REGISTRY.register("roosting", () -> new MemoryModuleType<Boolean>(Optional.of(Codec.BOOL)));
    public static final RegistryObject<MemoryModuleType<Boolean>> SLEEPING = MEMORY_MODULE_TYPE_DEFERRED_REGISTRY.register("sleeping", () -> new MemoryModuleType<Boolean>(Optional.of(Codec.BOOL)));
    public static final RegistryObject<MemoryModuleType<Boolean>> HUNGRY = MEMORY_MODULE_TYPE_DEFERRED_REGISTRY.register("hungry", () -> new MemoryModuleType<Boolean>(Optional.of(Codec.BOOL)));
    public static final RegistryObject<MemoryModuleType<UUID>> MOTHER_UUID = MEMORY_MODULE_TYPE_DEFERRED_REGISTRY.register("mother_uuid", () -> new MemoryModuleType<UUID>(Optional.of(SerializableUUID.CODEC)));
    public static final RegistryObject<MemoryModuleType<LivingEntity>> MOTHER = MEMORY_MODULE_TYPE_DEFERRED_REGISTRY.register("mother", () -> new MemoryModuleType<LivingEntity>(Optional.empty()));

    public static void register(IEventBus modEventBus) {
        MEMORY_MODULE_TYPE_DEFERRED_REGISTRY.register(modEventBus);
    }
}
