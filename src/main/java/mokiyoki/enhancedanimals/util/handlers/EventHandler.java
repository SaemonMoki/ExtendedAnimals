package mokiyoki.enhancedanimals.util.handlers;

import mokiyoki.enhancedanimals.entity.EnhancedChicken;
import mokiyoki.enhancedanimals.entity.EnhancedLlama;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityLlama;
import net.minecraft.entity.passive.EntityRabbit;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
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
        World world = event.getWorld();
        if (entity instanceof EntityChicken) {
            EnhancedChicken enhancedChicken = new EnhancedChicken(world);
            enhancedChicken.setLocationAndAngles(entity.posX, entity.posY, entity.posZ, 0.0F, 0.0F);
            enhancedChicken.onInitialSpawn(world.getDifficultyForLocation(new BlockPos(enhancedChicken)), (IEntityLivingData)null, null);
            world.spawnEntity(enhancedChicken);
            event.setCanceled(true);
        }
        if (entity instanceof EntityRabbit) {
//            EnhancedChicken enhancedChicken = new EnhancedChicken(world);
//            enhancedChicken.setLocationAndAngles(entity.posX, entity.posY, entity.posZ, 0, 0);
//            world.spawnEntity(enhancedChicken);
//            event.setCanceled(true);
        }
        if (entity instanceof EntityLlama) {
            EnhancedLlama enhancedLlama = new EnhancedLlama(world);
            enhancedLlama.setLocationAndAngles(entity.posX, entity.posY, entity.posZ, 0.0F, 0.0F);
            enhancedLlama.onInitialSpawn(world.getDifficultyForLocation(new BlockPos(enhancedLlama)), (IEntityLivingData)null, null);
            world.spawnEntity(enhancedLlama);
            event.setCanceled(true);
        }

    }

}
