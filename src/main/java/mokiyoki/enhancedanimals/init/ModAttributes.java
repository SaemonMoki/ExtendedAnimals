package mokiyoki.enhancedanimals.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import static mokiyoki.enhancedanimals.util.Reference.MODID;

public class ModAttributes {

    public static final DeferredRegister<Attribute> ATTRIBUTES_DEFERRED_REGISTRY = DeferredRegister.create(Registries.ATTRIBUTE, MODID);

    public static final DeferredHolder<Attribute, Attribute> JUMP_STRENGTH = ATTRIBUTES_DEFERRED_REGISTRY.register("jump_strength", () -> new RangedAttribute("ea.jumpStrength", 0.7D, 0.0D, 2.0D).setSyncable(true));

    public static void register(IEventBus modEventBus) {
        ATTRIBUTES_DEFERRED_REGISTRY.register(modEventBus);
    }
}
