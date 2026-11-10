package mokiyoki.enhancedanimals.init;

import mokiyoki.enhancedanimals.util.Reference;
import net.minecraft.world.entity.schedule.Activity;
import mokiyoki.enhancedanimals.platform.registry.RegistrationProvider;
import net.minecraft.core.registries.Registries;
import mokiyoki.enhancedanimals.platform.registry.RegistryEntry;

public class ModActivities {

    public static final RegistrationProvider<Activity> ACTIVITIES_DEFERRED_REGISTRY = RegistrationProvider.get(Registries.ACTIVITY, Reference.MODID);

    public static final RegistryEntry<Activity, Activity> PAUSE_BRAIN = ACTIVITIES_DEFERRED_REGISTRY.register("pause_brain", () -> new Activity("pause_brain"));

    public static void init() {
    }
}
