package mokiyoki.enhancedanimals.fabric.mixin.client;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import mokiyoki.enhancedanimals.util.handlers.EventSubscriber;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// NeoForge: EntityJoinLevelEvent and EntityTickEvent.Pre on the client
@Mixin(ClientLevel.class)
public abstract class ClientLevelMixin {

    @Inject(method = "addEntity", at = @At("HEAD"))
    private void eanimod$onEntityJoinLevel(Entity entity, CallbackInfo ci) {
        EventSubscriber.INSTANCE.onEntityJoinLevel(entity, (ClientLevel) (Object) this, false);
    }

    @WrapWithCondition(method = "tickNonPassenger", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;tick()V"))
    private boolean eanimod$onEntityTickPre(Entity entity) {
        return !EventSubscriber.INSTANCE.onEntityTickPre(entity);
    }
}
