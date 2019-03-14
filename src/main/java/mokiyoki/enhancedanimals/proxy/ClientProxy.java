package mokiyoki.enhancedanimals.proxy;

import mokiyoki.enhancedanimals.entity.EnhancedChicken;
import mokiyoki.enhancedanimals.entity.EnhancedLlama;
import mokiyoki.enhancedanimals.entity.EnhancedRabbit;
import mokiyoki.enhancedanimals.entity.EnhancedSheep;
import mokiyoki.enhancedanimals.renderer.RenderEnhancedChicken;
import mokiyoki.enhancedanimals.renderer.RenderEnhancedLlama;
import mokiyoki.enhancedanimals.renderer.RenderEnhancedRabbit;
import mokiyoki.enhancedanimals.renderer.RenderEnhancedSheep;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

/**
 * Created by moki on 24/08/2018.
 */
public class ClientProxy implements IProxy {


    @Override
    public void init(FMLCommonSetupEvent event) {
        RenderingRegistry.registerEntityRenderingHandler(EnhancedChicken.class, manager -> new RenderEnhancedChicken(manager));
        RenderingRegistry.registerEntityRenderingHandler(EnhancedRabbit.class, manager -> new RenderEnhancedRabbit(manager));
        RenderingRegistry.registerEntityRenderingHandler(EnhancedSheep.class, manager -> new RenderEnhancedSheep(manager));
        RenderingRegistry.registerEntityRenderingHandler(EnhancedLlama.class, manager -> new RenderEnhancedLlama(manager));
    }
}
