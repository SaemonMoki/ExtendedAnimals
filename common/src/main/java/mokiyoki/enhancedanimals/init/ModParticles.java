package mokiyoki.enhancedanimals.init;

import mokiyoki.enhancedanimals.util.Reference;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import mokiyoki.enhancedanimals.platform.registry.RegistrationProvider;
import net.minecraft.core.registries.Registries;
import mokiyoki.enhancedanimals.platform.registry.RegistryEntry;

public class ModParticles {

    public static final RegistrationProvider<ParticleType<?>> PARTICLE_TYPES_DEFERRED_REGISTRY = RegistrationProvider.get(Registries.PARTICLE_TYPE, Reference.MODID);

    public static final RegistryEntry<ParticleType<?>, SimpleParticleType> FEATHER = PARTICLE_TYPES_DEFERRED_REGISTRY.register("feather", () -> new SimpleParticleType(false));

    public static void init() {
    }
}
