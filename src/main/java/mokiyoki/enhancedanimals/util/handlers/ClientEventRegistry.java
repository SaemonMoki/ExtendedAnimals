package mokiyoki.enhancedanimals.util.handlers;

import mokiyoki.enhancedanimals.util.Reference;
import net.minecraft.client.renderer.Atlases;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static mokiyoki.enhancedanimals.renderer.EggCartonTileEntityRenderer.EGG_CARTON_TEXTURE;

@Mod.EventBusSubscriber(modid = Reference.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientEventRegistry {

    @SubscribeEvent
    public static void onStitchEvent(TextureStitchEvent.Pre event) {
        if (event.getMap().getTextureLocation().equals(Atlases.CHEST_ATLAS)) {
            event.addSprite(EGG_CARTON_TEXTURE);
        }
    }

}
