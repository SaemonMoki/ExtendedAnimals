package mokiyoki.enhancedanimals.fabric.mixin;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import mokiyoki.enhancedanimals.util.handlers.EventSubscriber;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

// NeoForge: EntityTickEvent.Pre (passengers)
@Mixin(Entity.class)
public abstract class EntityMixin {

    @WrapWithCondition(method = "rideTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;tick()V"))
    private boolean eanimod$onEntityTickPre(Entity entity) {
        return !EventSubscriber.INSTANCE.onEntityTickPre(entity);
    }
}
