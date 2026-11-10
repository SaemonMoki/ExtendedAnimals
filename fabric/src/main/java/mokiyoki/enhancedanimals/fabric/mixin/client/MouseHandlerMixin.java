package mokiyoki.enhancedanimals.fabric.mixin.client;

import mokiyoki.enhancedanimals.util.handlers.CarryClientHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.MouseHandler;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MouseHandler.class)
public abstract class MouseHandlerMixin {

    @Shadow @Final private Minecraft minecraft;

    @Inject(method = "onScroll", at = @At("HEAD"), cancellable = true)
    private void eanimod$onMouseScroll(long windowPointer, double xOffset, double yOffset, CallbackInfo ci) {
        if (this.minecraft.screen == null && CarryClientHandler.onMouseScroll()) {
            ci.cancel();
        }
    }
}
