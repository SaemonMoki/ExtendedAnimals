package mokiyoki.enhancedanimals.init;

import mokiyoki.enhancedanimals.util.Reference;
import net.minecraft.world.entity.schedule.Activity;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.registries.DeferredHolder;

public class ModActivities {

    public static final DeferredRegister<Activity> ACTIVITIES_DEFERRED_REGISTRY = DeferredRegister.create(Registries.ACTIVITY, Reference.MODID);

    public static final DeferredHolder<Activity, Activity> PAUSE_BRAIN = ACTIVITIES_DEFERRED_REGISTRY.register("pause_brain", () -> new Activity("pause_brain"));

    public static void register(IEventBus modEventBus) {
        ACTIVITIES_DEFERRED_REGISTRY.register(modEventBus);
    }
}
