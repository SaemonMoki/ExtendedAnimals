package mokiyoki.enhancedanimals.proxy;

import mokiyoki.enhancedanimals.entity.*;
import mokiyoki.enhancedanimals.renderer.*;
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
        RenderingRegistry.registerEntityRenderingHandler(EnhancedCow.class, manager -> new RenderEnhancedCow(manager));
        RenderingRegistry.registerEntityRenderingHandler(EnhancedPig.class, manager -> new RenderEnhancedPig(manager));
        RenderingRegistry.registerEntityRenderingHandler(EnhancedMooshroom.class, manager -> new RenderEnhancedMooshroom(manager));
    }
}
