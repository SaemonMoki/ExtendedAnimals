package mokiyoki.enhancedanimals.fabric.mixin;

import mokiyoki.enhancedanimals.util.handlers.EventSubscriber;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.entity.EntityAccess;
import net.minecraft.world.level.entity.PersistentEntitySectionManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// NeoForge: EntityJoinLevelEvent
@Mixin(PersistentEntitySectionManager.class)
public abstract class PersistentEntitySectionManagerMixin<T extends EntityAccess> {

    @Inject(method = "addEntity(Lnet/minecraft/world/level/entity/EntityAccess;Z)Z", at = @At("HEAD"))
    private void eanimod$onEntityJoinLevel(T entityAccess, boolean worldGenSpawned, CallbackInfoReturnable<Boolean> cir) {
        if (entityAccess instanceof Entity entity) {
            EventSubscriber.INSTANCE.onEntityJoinLevel(entity, entity.level(), worldGenSpawned);
        }
    }
}
