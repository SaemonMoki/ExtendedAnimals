package mokiyoki.enhancedanimals.init;

import mokiyoki.enhancedanimals.util.Reference;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModSounds {

    public static final DeferredRegister<SoundEvent> SOUND_EVENT_DEFERRED_REGISTER = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, Reference.MODID);
    public static final RegistryObject<SoundEvent> ROOSTER_CROW = SOUND_EVENT_DEFERRED_REGISTER.register("rooster_crow", () -> new SoundEvent(new ResourceLocation("eanimod:rooster_crow")));

    public static void register(IEventBus modEventBus) {
        SOUND_EVENT_DEFERRED_REGISTER.register(modEventBus);
    }

}
