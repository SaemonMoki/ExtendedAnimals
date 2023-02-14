package mokiyoki.enhancedanimals.init;

import mokiyoki.enhancedanimals.util.Reference;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModActivities {

    public static final DeferredRegister<Activity> ACTIVITIES_DEFERRED_REGISTRY = DeferredRegister.create(ForgeRegistries.ACTIVITIES, Reference.MODID);

    public static final RegistryObject<Activity> PAUSE_BRAIN = ACTIVITIES_DEFERRED_REGISTRY.register("pause_brain", () -> new Activity("pause_brain"));

    public static void register(IEventBus modEventBus) {
        ACTIVITIES_DEFERRED_REGISTRY.register(modEventBus);
    }
}
