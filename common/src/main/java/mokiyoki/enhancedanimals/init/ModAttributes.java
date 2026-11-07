package mokiyoki.enhancedanimals.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import mokiyoki.enhancedanimals.platform.registry.RegistryEntry;
import mokiyoki.enhancedanimals.platform.registry.RegistrationProvider;

import static mokiyoki.enhancedanimals.util.Reference.MODID;

public class ModAttributes {

    public static final RegistrationProvider<Attribute> ATTRIBUTES_DEFERRED_REGISTRY = RegistrationProvider.get(Registries.ATTRIBUTE, MODID);

    public static final RegistryEntry<Attribute, Attribute> JUMP_STRENGTH = ATTRIBUTES_DEFERRED_REGISTRY.register("jump_strength", () -> new RangedAttribute("ea.jumpStrength", 0.7D, 0.0D, 2.0D).setSyncable(true));

    public static void init() {
    }
}
