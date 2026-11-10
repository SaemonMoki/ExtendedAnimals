package mokiyoki.enhancedanimals.init;

import mokiyoki.enhancedanimals.util.Reference;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import mokiyoki.enhancedanimals.platform.registry.RegistrationProvider;
import net.minecraft.core.registries.Registries;
import mokiyoki.enhancedanimals.platform.registry.RegistryEntry;

public class ModSounds {

    public static final RegistrationProvider<SoundEvent> SOUND_EVENT_DEFERRED_REGISTER = RegistrationProvider.get(Registries.SOUND_EVENT, Reference.MODID);
    public static final RegistryEntry<SoundEvent, SoundEvent> ROOSTER_CROW = SOUND_EVENT_DEFERRED_REGISTER.register("rooster_crow", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.parse("eanimod:rooster_crow")));

    public static void init() {
    }

}
