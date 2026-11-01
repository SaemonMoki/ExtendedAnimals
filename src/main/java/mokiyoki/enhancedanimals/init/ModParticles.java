package mokiyoki.enhancedanimals.init;

import mokiyoki.enhancedanimals.util.Reference;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.registries.DeferredHolder;

public class ModParticles {

    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES_DEFERRED_REGISTRY = DeferredRegister.create(Registries.PARTICLE_TYPE, Reference.MODID);

    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> FEATHER = PARTICLE_TYPES_DEFERRED_REGISTRY.register("feather", () -> new SimpleParticleType(false));

    public static void register(IEventBus modEventBus) {
        PARTICLE_TYPES_DEFERRED_REGISTRY.register(modEventBus);
    }
}
