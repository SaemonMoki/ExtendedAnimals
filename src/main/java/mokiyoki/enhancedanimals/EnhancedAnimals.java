package mokiyoki.enhancedanimals;

import mokiyoki.enhancedanimals.capability.egg.EggCapability;
import mokiyoki.enhancedanimals.capability.egg.EggCapabilityStorage;
import mokiyoki.enhancedanimals.capability.egg.IEggCapability;
import mokiyoki.enhancedanimals.capability.post.IPostCapability;
import mokiyoki.enhancedanimals.capability.post.PostCapability;
import mokiyoki.enhancedanimals.capability.post.PostCapabilityStorage;
import mokiyoki.enhancedanimals.loot.EnhancedChickenLootCondition;
import mokiyoki.enhancedanimals.proxy.ClientProxy;
import mokiyoki.enhancedanimals.proxy.IProxy;
import mokiyoki.enhancedanimals.proxy.ServerProxy;
import mokiyoki.enhancedanimals.util.Reference;
import mokiyoki.enhancedanimals.util.handlers.CapabilityHandler;
import mokiyoki.enhancedanimals.util.handlers.EventHandler;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraft.world.storage.loot.conditions.LootConditionManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by moki on 24/08/2018.
 */

@Mod(value = "eanimod")
public class EnhancedAnimals {

    private static final Logger LOGGER = LogManager.getLogger();

    public static EnhancedAnimals instance;

    public static IProxy proxy = DistExecutor.runForDist( () -> () -> new ClientProxy(), () -> () -> new ServerProxy() );

    public EnhancedAnimals() {
        instance = this;
        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        // Register the doClientStuff method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientSetup);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(new EventHandler());
//        MinecraftForge.EVENT_BUS.register(new RegistryHandler());
        MinecraftForge.EVENT_BUS.register(new CapabilityHandler());
        MinecraftForge.EVENT_BUS.register(instance);
    }

    private void setup(final FMLCommonSetupEvent event) {
        // some preinit code
        proxy.init(event);
        CapabilityManager.INSTANCE.register(IPostCapability.class, new PostCapabilityStorage(), PostCapability::new);
        CapabilityManager.INSTANCE.register(IEggCapability.class, new EggCapabilityStorage(), EggCapability::new);

        LootTableList.register(new ResourceLocation(Reference.MODID, "enhanced_chicken"));
        LootConditionManager.registerCondition(new EnhancedChickenLootCondition.Serializer());

    }

    private void doClientSetup(final FMLClientSetupEvent event) {
//        RenderingRegistry.registerEntityRenderingHandler(EnhancedChicken.class, manager -> new RenderEnhancedChicken(manager));
//        RenderEntities.entityRender();

    }
}

