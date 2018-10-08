package mokiyoki.enhancedanimals.util.handlers;

import mokiyoki.enhancedanimals.capability.egg.EggCapability;
import mokiyoki.enhancedanimals.capability.egg.EggCapabilityProvider;
import mokiyoki.enhancedanimals.capability.post.PostCapabilityProvider;
import mokiyoki.enhancedanimals.items.EnhancedEgg;
import mokiyoki.enhancedanimals.util.Reference;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * Created by saemon on 29/09/2018.
 */
@EventBusSubscriber
public class CapabilityHandler {

    public static final ResourceLocation POST_CAP = new ResourceLocation(Reference.MODID, "postcap");
    public static final ResourceLocation EGG_CAP = new ResourceLocation(Reference.MODID, "eggcap");

    @SubscribeEvent
    public static void onAddCapabilitiesWorld(AttachCapabilitiesEvent<World> event) {
        event.addCapability(POST_CAP, new PostCapabilityProvider());
    }

    @SubscribeEvent
    public static void onAddCapabilitiesItemStack(AttachCapabilitiesEvent<ItemStack> event)
    {
//        if (event.getObject().getItem() instanceof EnhancedEgg) {
//            if (!event.getObject().hasCapability(EggCapabilityProvider.EGG_CAP, null)) {
//                event.addCapability(EGG_CAP, new EggCapabilityProvider());
//            }
//        }
    }

}
