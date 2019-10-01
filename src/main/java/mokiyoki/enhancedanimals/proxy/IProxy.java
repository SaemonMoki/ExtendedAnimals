package mokiyoki.enhancedanimals.proxy;


import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;

/**
 * Created by moki on 24/08/2018.
 */
public interface IProxy {
    public void init(FMLCommonSetupEvent event);

    void initLoadComplete(FMLLoadCompleteEvent event);
}
