package mokiyoki.enhancedanimals.util.handlers;

import mokiyoki.enhancedanimals.capability.egg.EggCapabilityProvider;
import mokiyoki.enhancedanimals.capability.hay.HayCapabilityProvider;
import mokiyoki.enhancedanimals.capability.post.PostCapabilityProvider;
import mokiyoki.enhancedanimals.capability.turtleegg.NestCapabilityProvider;
import mokiyoki.enhancedanimals.util.Reference;
import net.minecraft.world.item.ItemStack;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import static mokiyoki.enhancedanimals.init.ModBlocks.TURTLE_EGG;

/**
 * Created by saemon on 29/09/2018.
 */
public class CapabilityEvents {

    public static final ResourceLocation POST_CAP = new ResourceLocation(Reference.MODID, "postcap");
    public static final ResourceLocation HAY_CAP = new ResourceLocation(Reference.MODID, "haycap");
    public static final ResourceLocation EGG_CAP = new ResourceLocation(Reference.MODID, "eggcap");
    public static final ResourceLocation NEST_CAP = new ResourceLocation(Reference.MODID, "nestcap");

    @SubscribeEvent
    public void onAddCapabilitiesWorld(AttachCapabilitiesEvent<Level> event) {
        event.addCapability(POST_CAP, new PostCapabilityProvider());
        event.addCapability(HAY_CAP, new HayCapabilityProvider());
        event.addCapability(NEST_CAP, new NestCapabilityProvider());
    }

    @SubscribeEvent
    public void onAddCapabilitiesItemStack(AttachCapabilitiesEvent<ItemStack> event) {
        if (event.getObject().getItem() == TURTLE_EGG.asItem()) {
            event.addCapability(EGG_CAP, new EggCapabilityProvider());
        }
    }

}
