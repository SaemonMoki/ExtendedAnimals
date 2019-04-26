package mokiyoki.enhancedanimals.util.handlers;

import mokiyoki.enhancedanimals.entity.EnhancedChicken;
import mokiyoki.enhancedanimals.entity.EnhancedRabbit;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityRabbit;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * Created by saemon on 8/09/2018.
 */
@EventBusSubscriber
public class EventHandler {

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void replaceVanillaMobs(EntityJoinWorldEvent event) {
        Entity entity = event.getEntity();
        World world = event.getWorld();
        if (entity instanceof EntityChicken) {
            EnhancedChicken enhancedChicken = new EnhancedChicken(world);
            enhancedChicken.setLocationAndAngles(entity.posX, entity.posY, entity.posZ, 0, 0);
            enhancedChicken.onInitialSpawn(world.getDifficultyForLocation(new BlockPos(enhancedChicken)), (IEntityLivingData)null);
            world.spawnEntity(enhancedChicken);
//            event.setCanceled(true);
        }
        if (entity instanceof EntityRabbit) {
            EnhancedRabbit enhancedRabbit = new EnhancedRabbit(world);
            enhancedRabbit.setLocationAndAngles(entity.posX, entity.posY, entity.posZ, 0, 0);
            enhancedRabbit.onInitialSpawn(world.getDifficultyForLocation(new BlockPos(enhancedRabbit)), (IEntityLivingData)null);
            world.spawnEntity(enhancedRabbit);
//            event.setCanceled(true);
        }

    }

}
