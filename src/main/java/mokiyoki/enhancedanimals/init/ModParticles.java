package mokiyoki.enhancedanimals.init;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static mokiyoki.enhancedanimals.GeneticAnimals.MODID;

public class ModParticles {

    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES_DEFERRED_REGISTRY = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, MODID);

    public static final RegistryObject<SimpleParticleType> FEATHER = PARTICLE_TYPES_DEFERRED_REGISTRY.register("feather", () -> new SimpleParticleType(false));

    public static void register(IEventBus modEventBus) {
        PARTICLE_TYPES_DEFERRED_REGISTRY.register(modEventBus);
    }
}
