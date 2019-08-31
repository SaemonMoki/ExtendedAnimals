package mokiyoki.enhancedanimals.util.handlers;

import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.entity.passive.MooshroomEntity;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.entity.passive.RabbitEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.passive.horse.LlamaEntity;
import net.minecraft.entity.passive.horse.TraderLlamaEntity;
import net.minecraft.world.spawner.WanderingTraderSpawner;
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
        //TODO figure out how to not delete named entities and maybe convert them instead.
        if (entity instanceof ChickenEntity) {
            if(!ConfigHandler.COMMON.spawnVanillaMobs.get()) {
                event.setCanceled(true);
            }
        }
        if (entity instanceof RabbitEntity) {
            if(!ConfigHandler.COMMON.spawnVanillaMobs.get()) {
                event.setCanceled(true);
            }
        }
        if (entity instanceof LlamaEntity && !(entity instanceof TraderLlamaEntity)) {
            if(!ConfigHandler.COMMON.spawnVanillaMobs.get()) {
                event.setCanceled(true);
            }
        }
        if (entity instanceof SheepEntity) {
            if(!ConfigHandler.COMMON.spawnVanillaMobs.get()) {
                event.setCanceled(true);
            }
        }
        if (entity instanceof CowEntity && !(entity instanceof MooshroomEntity)) {
            if(!ConfigHandler.COMMON.spawnVanillaMobs.get()) {
                event.setCanceled(true);
            }
        }
        if (entity instanceof MooshroomEntity) {
            if(!ConfigHandler.COMMON.spawnVanillaMobs.get()) {
                event.setCanceled(true);
            }
        }
        if (entity instanceof PigEntity) {
            if(!ConfigHandler.COMMON.spawnVanillaMobs.get()) {
                event.setCanceled(true);
            }
        }

    }

}
