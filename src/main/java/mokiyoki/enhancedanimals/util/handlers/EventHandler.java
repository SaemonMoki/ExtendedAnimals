package mokiyoki.enhancedanimals.util.handlers;

import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.*;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

/**
 * Created by saemon on 8/09/2018.
 */
//@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
public class EventHandler {

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void replaceVanillaMobs(EntityJoinWorldEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof EntityChicken) {
            if(!ConfigHandler.COMMON.spawnVanillaMobs.get()) {
                event.setCanceled(true);
            }
        }
        if (entity instanceof EntityRabbit) {
            if(!ConfigHandler.COMMON.spawnVanillaMobs.get()) {
                event.setCanceled(true);
            }
        }
        if (entity instanceof EntityLlama) {
            if(!ConfigHandler.COMMON.spawnVanillaMobs.get()) {
                event.setCanceled(true);
            }
        }
        if (entity instanceof EntitySheep) {
            if(!ConfigHandler.COMMON.spawnVanillaMobs.get()) {
                event.setCanceled(true);
            }
        }
        if (entity instanceof EntityCow) {
            if(!ConfigHandler.COMMON.spawnVanillaMobs.get()) {
                event.setCanceled(true);
            }
        }

    }

}
