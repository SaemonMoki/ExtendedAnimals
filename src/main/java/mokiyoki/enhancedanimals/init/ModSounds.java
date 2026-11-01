package mokiyoki.enhancedanimals.init;

import mokiyoki.enhancedanimals.util.Reference;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.registries.DeferredHolder;

public class ModSounds {

    public static final DeferredRegister<SoundEvent> SOUND_EVENT_DEFERRED_REGISTER = DeferredRegister.create(Registries.SOUND_EVENT, Reference.MODID);
    public static final DeferredHolder<SoundEvent, SoundEvent> ROOSTER_CROW = SOUND_EVENT_DEFERRED_REGISTER.register("rooster_crow", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.parse("eanimod:rooster_crow")));

    public static void register(IEventBus modEventBus) {
        SOUND_EVENT_DEFERRED_REGISTER.register(modEventBus);
    }

}
