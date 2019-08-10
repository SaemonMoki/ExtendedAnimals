package mokiyoki.enhancedanimals.proxy;

import mokiyoki.enhancedanimals.entity.EnhancedChicken;
import mokiyoki.enhancedanimals.entity.EnhancedCow;
import mokiyoki.enhancedanimals.entity.EnhancedRabbit;
import mokiyoki.enhancedanimals.entity.EnhancedSheep;
import mokiyoki.enhancedanimals.renderer.RenderEnhancedChicken;
import mokiyoki.enhancedanimals.renderer.RenderEnhancedRabbit;
import mokiyoki.enhancedanimals.renderer.RenderEnhancedSheep;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

/**
 * Created by moki on 24/08/2018.
 */
public class ClientProxy implements IProxy {

    public void registerItemRenderer(Item item, int meta, String id){
        ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(item.getRegistryName(), id));
    }


    @Override
    public void preInit(FMLPreInitializationEvent event) {
        RenderingRegistry.registerEntityRenderingHandler(EnhancedChicken.class, renderManager -> new RenderEnhancedChicken(renderManager));
        RenderingRegistry.registerEntityRenderingHandler(EnhancedRabbit.class, renderManager -> new RenderEnhancedRabbit(renderManager));
        RenderingRegistry.registerEntityRenderingHandler(EnhancedSheep.class, renderManager -> new RenderEnhancedSheep(renderManager));
//        RenderingRegistry.registerEntityRenderingHandler(EnhancedCow.class, renderManager -> new RenderEnhancedCow(renderManager));
    }

    @Override
    public void init(FMLInitializationEvent event) {

    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {

    }

//    @SubscribeEvent
//    private static void registerEntityRenderers() {
//        RenderingRegistry.registerEntityRenderingHandler(EnhancedChicken.class, renderManager -> new RenderEnhancedChicken(renderManager));
//    }
}
