package mokiyoki.enhancedanimals.fabric.mixin;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import mokiyoki.enhancedanimals.util.handlers.EventSubscriber;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

// NeoForge: EntityTickEvent.Pre
@Mixin(ServerLevel.class)
public abstract class ServerLevelMixin {

    @WrapWithCondition(method = "tickNonPassenger", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;tick()V"))
    private boolean eanimod$onEntityTickPre(Entity entity) {
        return !EventSubscriber.INSTANCE.onEntityTickPre(entity);
    }
}
