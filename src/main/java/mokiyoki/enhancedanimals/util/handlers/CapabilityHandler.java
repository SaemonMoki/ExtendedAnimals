package mokiyoki.enhancedanimals.util.handlers;

import mokiyoki.enhancedanimals.capability.hay.HayCapabilityProvider;
import mokiyoki.enhancedanimals.capability.post.PostCapabilityProvider;
import mokiyoki.enhancedanimals.util.Reference;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

/**
 * Created by saemon on 29/09/2018.
 */
//@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
public class CapabilityHandler {

    public static final ResourceLocation POST_CAP = new ResourceLocation(Reference.MODID, "postcap");
    public static final ResourceLocation HAY_CAP = new ResourceLocation(Reference.MODID, "haycap");
    public static final ResourceLocation EGG_CAP = new ResourceLocation(Reference.MODID, "eggcap");

    @SubscribeEvent
    public void onAddCapabilitiesWorld(AttachCapabilitiesEvent<World> event) {
        event.addCapability(POST_CAP, new PostCapabilityProvider());
        event.addCapability(HAY_CAP, new HayCapabilityProvider());
    }

    @SubscribeEvent
    public void onAddCapabilitiesItemStack(AttachCapabilitiesEvent<ItemStack> event)
    {
//        if (event.getObject().getItem() instanceof EnhancedEgg) {
//            if (!event.getObject().hasCapability(EggCapabilityProvider.EGG_CAP, null)) {
//                event.addCapability(EGG_CAP, new EggCapabilityProvider());
//            }
//        }
    }

}
