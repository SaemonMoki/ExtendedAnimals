package mokiyoki.enhancedanimals;

import mokiyoki.enhancedanimals.init.ModBlocks;
import mokiyoki.enhancedanimals.init.ModEntities;
import mokiyoki.enhancedanimals.init.ModItems;
import mokiyoki.enhancedanimals.network.EAEquipmentPacket;
//import mokiyoki.enhancedanimals.proxy.ClientProxy;
import mokiyoki.enhancedanimals.network.axolotl.AxolotlBucketTexturePacket;
import mokiyoki.enhancedanimals.proxy.IProxy;
//import mokiyoki.enhancedanimals.proxy.ServerProxy;
import mokiyoki.enhancedanimals.util.handlers.CapabilityEvents;
import mokiyoki.enhancedanimals.config.EanimodCommonConfig;
//import mokiyoki.enhancedanimals.util.handlers.EventSubscriber;
import mokiyoki.enhancedanimals.util.handlers.EventSubscriber;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

import static mokiyoki.enhancedanimals.util.Reference.MODID;

/**
 * Created by moki on 24/08/2018.
 */

@Mod(value = "eanimod")
public class EnhancedAnimals {

    private static final String PROTOCOL_VERSION = "1.0";

    public static SimpleChannel channel = NetworkRegistry.ChannelBuilder
            .named(new ResourceLocation(MODID, "eanetwork"))
            .clientAcceptedVersions(PROTOCOL_VERSION::equals)
            .serverAcceptedVersions(PROTOCOL_VERSION::equals)
            .networkProtocolVersion(() -> PROTOCOL_VERSION)
            .simpleChannel();

    public static final CreativeModeTab GENETICS_ANIMALS_GROUP = new CreativeModeTab(MODID) {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItems.EGG_BLUE.get());
        }
    };

    public static EnhancedAnimals instance;

//    public static IProxy proxy = DistExecutor.safeRunForDist( () -> () -> new ClientProxy(), () -> () -> new ServerProxy() );

    public static final EanimodCommonConfig commonConfig = new EanimodCommonConfig();

    public EnhancedAnimals() {
        instance = this;
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, EanimodCommonConfig.getConfigSpecForLoader(), EanimodCommonConfig.getFileNameForLoader());

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
//        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientSetup);
//        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::loadComplete);

        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(new EventSubscriber());
        MinecraftForge.EVENT_BUS.register(new CapabilityEvents());
        MinecraftForge.EVENT_BUS.register(instance);

        ModItems.register(FMLJavaModLoadingContext.get().getModEventBus());
        ModBlocks.register(FMLJavaModLoadingContext.get().getModEventBus());
        ModEntities.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    private void setup(final FMLCommonSetupEvent event) {
//        proxy.init(event);

        int messageNumber = 0;
        channel.messageBuilder(EAEquipmentPacket.class, messageNumber++).encoder(EAEquipmentPacket::writePacketData).decoder(EAEquipmentPacket::new).consumer(EAEquipmentPacket::processPacket).add();
        channel.messageBuilder(AxolotlBucketTexturePacket.class, messageNumber++).encoder(AxolotlBucketTexturePacket::writePacketData).decoder(AxolotlBucketTexturePacket::new).consumer(AxolotlBucketTexturePacket::processPacket).add();
    }

//    private void doClientSetup(final FMLClientSetupEvent event) {
//        proxy.initClientSetup(event);
//    }
//
//    private void loadComplete(final FMLLoadCompleteEvent event) {
//        proxy.initLoadComplete(event);
//    }

}

