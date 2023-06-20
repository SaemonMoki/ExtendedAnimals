package mokiyoki.enhancedanimals;

import mokiyoki.enhancedanimals.init.*;
import mokiyoki.enhancedanimals.init.ModSensorTypes;
import mokiyoki.enhancedanimals.network.EAEquipmentPacket;
import mokiyoki.enhancedanimals.network.axolotl.AxolotlBucketTexturePacket;
import mokiyoki.enhancedanimals.util.handlers.CapabilityEvents;
import mokiyoki.enhancedanimals.config.EanimodCommonConfig;
import mokiyoki.enhancedanimals.util.handlers.EventSubscriber;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.resources.ResourceLocation;
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

    public static final EanimodCommonConfig commonConfig = new EanimodCommonConfig();

    public EnhancedAnimals() {
        instance = this;
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, EanimodCommonConfig.getConfigSpecForLoader(), EanimodCommonConfig.getFileNameForLoader());

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);

        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(new EventSubscriber());
        MinecraftForge.EVENT_BUS.register(new CapabilityEvents());
        MinecraftForge.EVENT_BUS.register(instance);

        ModItems.register(FMLJavaModLoadingContext.get().getModEventBus());
        ModBlocks.register(FMLJavaModLoadingContext.get().getModEventBus());
        ModEntities.register(FMLJavaModLoadingContext.get().getModEventBus());
        ModTileEntities.register(FMLJavaModLoadingContext.get().getModEventBus());
        ModSensorTypes.register(FMLJavaModLoadingContext.get().getModEventBus());
        ModActivities.register(FMLJavaModLoadingContext.get().getModEventBus());
        ModSounds.register(FMLJavaModLoadingContext.get().getModEventBus());
        ModMemoryModuleTypes.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    private void setup(final FMLCommonSetupEvent event) {
        int messageNumber = 0;
        channel.messageBuilder(EAEquipmentPacket.class, messageNumber++).encoder(EAEquipmentPacket::writePacketData).decoder(EAEquipmentPacket::new).consumer(EAEquipmentPacket::processPacket).add();
        channel.messageBuilder(AxolotlBucketTexturePacket.class, messageNumber++).encoder(AxolotlBucketTexturePacket::writePacketData).decoder(AxolotlBucketTexturePacket::new).consumer(AxolotlBucketTexturePacket::processPacket).add();
    }

}

