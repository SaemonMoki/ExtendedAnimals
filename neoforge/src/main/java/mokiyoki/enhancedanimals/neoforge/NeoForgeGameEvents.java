package mokiyoki.enhancedanimals.neoforge;

import mokiyoki.enhancedanimals.util.handlers.EventSubscriber;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Mob;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.AddReloadListenerEvent;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.EntityLeaveLevelEvent;
import net.neoforged.neoforge.event.entity.living.BabyEntitySpawnEvent;
import net.neoforged.neoforge.event.entity.living.FinalizeSpawnEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

public class NeoForgeGameEvents {

    private final EventSubscriber handler = EventSubscriber.INSTANCE;

    @SubscribeEvent(priority = EventPriority.NORMAL)
    public void onEntityJoinLevel(EntityJoinLevelEvent event) {
        this.handler.onEntityJoinLevel(event.getEntity(), event.getLevel(), event.loadedFromDisk());
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onEntityLeaveLevel(EntityLeaveLevelEvent event) {
        this.handler.onEntityLeaveLevel(event.getEntity(), event.getLevel());
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public void onBabyEntitySpawn(BabyEntitySpawnEvent event) {
        if (this.handler.shouldCancelBabySpawn(event.getParentA(), event.getChild())) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent(priority = EventPriority.NORMAL)
    public void onAddReloadListener(AddReloadListenerEvent event) {
        this.handler.createServerReloadListeners().values().forEach(event::addListener);
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onEntityTick(EntityTickEvent.Pre event) {
        if (this.handler.onEntityTickPre(event.getEntity())) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public void onStartTracking(PlayerEvent.StartTracking event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            this.handler.onStartTracking(event.getTarget(), player);
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public void onFinalizeSpawn(FinalizeSpawnEvent event) {
        Mob mob = event.getEntity();
        this.handler.onFinalizeSpawn(mob, event.getLevel());
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        this.handler.onRightClickBlock(event.getEntity(), event.getLevel(), event.getPos(), event.getItemStack());
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onLivingIncomingDamage(LivingIncomingDamageEvent event) {
        if (this.handler.shouldCancelIncomingDamage(event.getEntity(), event.getSource())) {
            event.setCanceled(true);
        }
    }
}
