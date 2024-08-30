package mokiyoki.enhancedanimals.init;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static mokiyoki.enhancedanimals.GeneticAnimals.MODID;

public class ModSounds {

    public static final DeferredRegister<SoundEvent> SOUND_EVENT_DEFERRED_REGISTER = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, MODID);
    public static final RegistryObject<SoundEvent> ROOSTER_CROW = SOUND_EVENT_DEFERRED_REGISTER.register("rooster_crow", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("eanimod:rooster_crow")));

    public static void register(IEventBus modEventBus) {
        SOUND_EVENT_DEFERRED_REGISTER.register(modEventBus);
    }

}
