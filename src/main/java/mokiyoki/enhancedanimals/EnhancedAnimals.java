package mokiyoki.enhancedanimals;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import mokiyoki.enhancedanimals.capability.egg.EggCapabilityProvider;
import mokiyoki.enhancedanimals.capability.egg.EggCapabilityStorage;
import mokiyoki.enhancedanimals.capability.egg.IEggCapability;
import mokiyoki.enhancedanimals.capability.hay.HayCapabilityProvider;
import mokiyoki.enhancedanimals.capability.hay.HayCapabilityStorage;
import mokiyoki.enhancedanimals.capability.hay.IHayCapability;
import mokiyoki.enhancedanimals.capability.post.IPostCapability;
import mokiyoki.enhancedanimals.capability.post.PostCapabilityProvider;
import mokiyoki.enhancedanimals.capability.post.PostCapabilityStorage;
import mokiyoki.enhancedanimals.config.EanimodConfig;
import mokiyoki.enhancedanimals.config.EanimodConfigHelper;
import mokiyoki.enhancedanimals.init.ModItems;
import mokiyoki.enhancedanimals.proxy.ClientProxy;
import mokiyoki.enhancedanimals.proxy.IProxy;
import mokiyoki.enhancedanimals.proxy.ServerProxy;
import mokiyoki.enhancedanimals.util.Reference;
import mokiyoki.enhancedanimals.util.handlers.CapabilityEvents;
import mokiyoki.enhancedanimals.config.EanimodCommonConfig;
import mokiyoki.enhancedanimals.util.handlers.EventSubscriber;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.file.Path;

/**
 * Created by moki on 24/08/2018.
 */

@Mod(value = "eanimod")
public class EnhancedAnimals {

    private static final Logger LOGGER = LogManager.getLogger();

    public static final ItemGroup GENETICS_ANIMALS_GROUP = new ItemGroup(Reference.MODID) {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(ModItems.Egg_Blue);
        }
    };



    public static EnhancedAnimals instance;

    public static IProxy proxy = DistExecutor.runForDist( () -> () -> new ClientProxy(), () -> () -> new ServerProxy() );

    public static final EanimodCommonConfig commonConfig = new EanimodCommonConfig();

    public EnhancedAnimals() {
        instance = this;
        EanimodConfigHelper.registerConfig(ModLoadingContext.get().getActiveContainer(), commonConfig);
        Path path = FMLPaths.CONFIGDIR.get().resolve("eanimod-common.toml");
        loadConfig(EanimodCommonConfig.COMMON_SPEC, path);

        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        // Register the doClientStuff method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientSetup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::loadComplete);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(new EventSubscriber());
//        MinecraftForge.EVENT_BUS.register(new EventRegistry());
        MinecraftForge.EVENT_BUS.register(new CapabilityEvents());
        MinecraftForge.EVENT_BUS.register(instance);

//        ModLoadingContext.get().registerConfig(EanimodConfig.Type.SERVER, EanimodCommonConfig.COMMON_SPEC);
//        Path path = FMLPaths.CONFIGDIR.get().resolve("eanimod-common.toml");
//        loadConfig(EanimodCommonConfig.COMMON_SPEC, path);

    }

    private void setup(final FMLCommonSetupEvent event) {
        // some preinit code
        proxy.init(event);
        CapabilityManager.INSTANCE.register(IPostCapability.class, new PostCapabilityStorage(), PostCapabilityProvider::new);
        CapabilityManager.INSTANCE.register(IHayCapability.class, new HayCapabilityStorage(), HayCapabilityProvider::new);
        CapabilityManager.INSTANCE.register(IEggCapability.class, new EggCapabilityStorage(), EggCapabilityProvider::new);

//        LootTables.func_215796_a().add(new ResourceLocation(Reference.MODID, "enhanced_chicken"));
//        LootConditionManager.registerCondition(new EnhancedChickenLootCondition.Serializer());
//
//        LootTables.func_215796_a().add(new ResourceLocation(Reference.MODID, "enhanced_rabbit"));
//        LootConditionManager.registerCondition(new EnhancedRabbitLootCondition.Serializer());
//
//        LootTables.func_215796_a().add(new ResourceLocation(Reference.MODID, "enhanced_llama"));
//        LootConditionManager.registerCondition(new EnhancedLlamaLootCondition.Serializer());

    }

    private void doClientSetup(final FMLClientSetupEvent event) {
        proxy.initClientSetup(event);
    }

    private void loadComplete(final FMLLoadCompleteEvent event) {
        proxy.initLoadComplete(event);
    }

    public static void loadConfig(ForgeConfigSpec spec, Path path) {

        final CommentedFileConfig configData = CommentedFileConfig.builder(path)
                .sync()
                .autosave()
                .writingMode(WritingMode.REPLACE)
                .build();

        configData.load();
        spec.setConfig(configData);
    }

}

