package mokiyoki.enhancedanimals.init;

import com.mojang.serialization.Codec;
import mokiyoki.enhancedanimals.util.Reference;
import net.minecraft.core.UUIDUtil;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.Optional;
import java.util.UUID;

public class ModMemoryModuleTypes {

    public static final DeferredRegister<MemoryModuleType<?>> MEMORY_MODULE_TYPE_DEFERRED_REGISTRY = DeferredRegister.create(Registries.MEMORY_MODULE_TYPE, Reference.MODID);

    public static final DeferredHolder<MemoryModuleType<?>, MemoryModuleType<Boolean>> HAS_EGG = MEMORY_MODULE_TYPE_DEFERRED_REGISTRY.register("has_egg", () -> new MemoryModuleType<Boolean>(Optional.of(Codec.BOOL)));
    public static final DeferredHolder<MemoryModuleType<?>, MemoryModuleType<Boolean>> PAUSE_BRAIN = MEMORY_MODULE_TYPE_DEFERRED_REGISTRY.register("pause_brain", () -> new MemoryModuleType<Boolean>(Optional.of(Codec.BOOL)));
    public static final DeferredHolder<MemoryModuleType<?>, MemoryModuleType<Boolean>> PAUSE_WALKING = MEMORY_MODULE_TYPE_DEFERRED_REGISTRY.register("pause_walking", () -> new MemoryModuleType<Boolean>(Optional.of(Codec.BOOL)));
    public static final DeferredHolder<MemoryModuleType<?>, MemoryModuleType<Integer>> PAUSE_BETWEEN_EATING = MEMORY_MODULE_TYPE_DEFERRED_REGISTRY.register("pause_between_eating", () -> new MemoryModuleType<Integer>(Optional.of(Codec.INT)));
    public static final DeferredHolder<MemoryModuleType<?>, MemoryModuleType<Boolean>> FOCUS_BRAIN = MEMORY_MODULE_TYPE_DEFERRED_REGISTRY.register("focus_brain", () -> new MemoryModuleType<Boolean>(Optional.of(Codec.BOOL)));
    public static final DeferredHolder<MemoryModuleType<?>, MemoryModuleType<Boolean>> SEEKING_SHELTER = MEMORY_MODULE_TYPE_DEFERRED_REGISTRY.register("seeking_shelter", () -> new MemoryModuleType<Boolean>(Optional.of(Codec.BOOL)));
    public static final DeferredHolder<MemoryModuleType<?>, MemoryModuleType<Boolean>> SEEKING_FOOD = MEMORY_MODULE_TYPE_DEFERRED_REGISTRY.register("seeking_food", () -> new MemoryModuleType<Boolean>(Optional.of(Codec.BOOL)));
    public static final DeferredHolder<MemoryModuleType<?>, MemoryModuleType<Boolean>> BROODY = MEMORY_MODULE_TYPE_DEFERRED_REGISTRY.register("broody", () -> new MemoryModuleType<Boolean>(Optional.of(Codec.BOOL)));
    public static final DeferredHolder<MemoryModuleType<?>, MemoryModuleType<Boolean>> EGG_LAYING = MEMORY_MODULE_TYPE_DEFERRED_REGISTRY.register("egg_laying", () -> new MemoryModuleType<Boolean>(Optional.of(Codec.BOOL)));
    public static final DeferredHolder<MemoryModuleType<?>, MemoryModuleType<Boolean>> ROOSTING = MEMORY_MODULE_TYPE_DEFERRED_REGISTRY.register("roosting", () -> new MemoryModuleType<Boolean>(Optional.of(Codec.BOOL)));
    public static final DeferredHolder<MemoryModuleType<?>, MemoryModuleType<Boolean>> SEEKING_NEST = MEMORY_MODULE_TYPE_DEFERRED_REGISTRY.register("nesting", () -> new MemoryModuleType<Boolean>(Optional.of(Codec.BOOL)));
    public static final DeferredHolder<MemoryModuleType<?>, MemoryModuleType<Boolean>> SLEEPING = MEMORY_MODULE_TYPE_DEFERRED_REGISTRY.register("sleeping", () -> new MemoryModuleType<Boolean>(Optional.of(Codec.BOOL)));
    public static final DeferredHolder<MemoryModuleType<?>, MemoryModuleType<Boolean>> HUNGRY = MEMORY_MODULE_TYPE_DEFERRED_REGISTRY.register("hungry", () -> new MemoryModuleType<Boolean>(Optional.of(Codec.BOOL)));
    public static final DeferredHolder<MemoryModuleType<?>, MemoryModuleType<UUID>> MOTHER_UUID = MEMORY_MODULE_TYPE_DEFERRED_REGISTRY.register("mother_uuid", () -> new MemoryModuleType<UUID>(Optional.of(UUIDUtil.CODEC)));
    public static final DeferredHolder<MemoryModuleType<?>, MemoryModuleType<LivingEntity>> MOTHER = MEMORY_MODULE_TYPE_DEFERRED_REGISTRY.register("mother", () -> new MemoryModuleType<LivingEntity>(Optional.empty()));

    public static void register(IEventBus modEventBus) {
        MEMORY_MODULE_TYPE_DEFERRED_REGISTRY.register(modEventBus);
    }
}
