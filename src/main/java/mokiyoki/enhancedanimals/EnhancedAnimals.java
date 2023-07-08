package mokiyoki.enhancedanimals;

import mokiyoki.enhancedanimals.init.*;
import mokiyoki.enhancedanimals.init.ModSensorTypes;
import mokiyoki.enhancedanimals.items.CustomizableAnimalEquipment;
import mokiyoki.enhancedanimals.network.EAEquipmentPacket;
import mokiyoki.enhancedanimals.network.axolotl.AxolotlBucketTexturePacket;
import mokiyoki.enhancedanimals.util.handlers.CapabilityEvents;
import mokiyoki.enhancedanimals.config.EanimodCommonConfig;
import mokiyoki.enhancedanimals.util.handlers.EventSubscriber;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

import static mokiyoki.enhancedanimals.init.ModItems.BRIDLE_BASIC_CLOTH;
import static mokiyoki.enhancedanimals.init.ModItems.BRIDLE_BASIC_CLOTH_GOLD;
import static mokiyoki.enhancedanimals.init.ModItems.BRIDLE_BASIC_LEATHER;
import static mokiyoki.enhancedanimals.init.ModItems.BRIDLE_BASIC_LEATHER_DIAMOND;
import static mokiyoki.enhancedanimals.init.ModItems.BRIDLE_BASIC_LEATHER_GOLD;
import static mokiyoki.enhancedanimals.init.ModItems.COLLAR_BASIC_CLOTH;
import static mokiyoki.enhancedanimals.init.ModItems.COLLAR_BASIC_CLOTH_DIAMONDBELL;
import static mokiyoki.enhancedanimals.init.ModItems.COLLAR_BASIC_CLOTH_DIAMONDRING;
import static mokiyoki.enhancedanimals.init.ModItems.COLLAR_BASIC_CLOTH_GOLDBELL;
import static mokiyoki.enhancedanimals.init.ModItems.COLLAR_BASIC_CLOTH_GOLDRING;
import static mokiyoki.enhancedanimals.init.ModItems.COLLAR_BASIC_CLOTH_IRONBELL;
import static mokiyoki.enhancedanimals.init.ModItems.COLLAR_BASIC_CLOTH_IRONRING;
import static mokiyoki.enhancedanimals.init.ModItems.COLLAR_BASIC_LEATHER;
import static mokiyoki.enhancedanimals.init.ModItems.COLLAR_BASIC_LEATHER_DIAMONDBELL;
import static mokiyoki.enhancedanimals.init.ModItems.COLLAR_BASIC_LEATHER_DIAMONDRING;
import static mokiyoki.enhancedanimals.init.ModItems.COLLAR_BASIC_LEATHER_GOLDBELL;
import static mokiyoki.enhancedanimals.init.ModItems.COLLAR_BASIC_LEATHER_GOLDRING;
import static mokiyoki.enhancedanimals.init.ModItems.COLLAR_BASIC_LEATHER_IRONBELL;
import static mokiyoki.enhancedanimals.init.ModItems.COLLAR_BASIC_LEATHER_IRONRING;
import static mokiyoki.enhancedanimals.init.ModItems.SADDLE_BASICPOMEL_CLOTH;
import static mokiyoki.enhancedanimals.init.ModItems.SADDLE_BASICPOMEL_CLOTH_DIAMOND;
import static mokiyoki.enhancedanimals.init.ModItems.SADDLE_BASICPOMEL_CLOTH_GOLD;
import static mokiyoki.enhancedanimals.init.ModItems.SADDLE_BASICPOMEL_CLOTH_WOOD;
import static mokiyoki.enhancedanimals.init.ModItems.SADDLE_BASICPOMEL_LEATHER;
import static mokiyoki.enhancedanimals.init.ModItems.SADDLE_BASICPOMEL_LEATHERCLOTHSEAT;
import static mokiyoki.enhancedanimals.init.ModItems.SADDLE_BASICPOMEL_LEATHERCLOTHSEAT_DIAMOND;
import static mokiyoki.enhancedanimals.init.ModItems.SADDLE_BASICPOMEL_LEATHERCLOTHSEAT_GOLD;
import static mokiyoki.enhancedanimals.init.ModItems.SADDLE_BASICPOMEL_LEATHERCLOTHSEAT_WOOD;
import static mokiyoki.enhancedanimals.init.ModItems.SADDLE_BASICPOMEL_LEATHER_DIAMOND;
import static mokiyoki.enhancedanimals.init.ModItems.SADDLE_BASICPOMEL_LEATHER_GOLD;
import static mokiyoki.enhancedanimals.init.ModItems.SADDLE_BASICPOMEL_LEATHER_WOOD;
import static mokiyoki.enhancedanimals.init.ModItems.SADDLE_BASIC_CLOTH;
import static mokiyoki.enhancedanimals.init.ModItems.SADDLE_BASIC_CLOTH_DIAMOND;
import static mokiyoki.enhancedanimals.init.ModItems.SADDLE_BASIC_CLOTH_GOLD;
import static mokiyoki.enhancedanimals.init.ModItems.SADDLE_BASIC_CLOTH_WOOD;
import static mokiyoki.enhancedanimals.init.ModItems.SADDLE_BASIC_LEATHER;
import static mokiyoki.enhancedanimals.init.ModItems.SADDLE_BASIC_LEATHERCLOTHSEAT;
import static mokiyoki.enhancedanimals.init.ModItems.SADDLE_BASIC_LEATHERCLOTHSEAT_DIAMOND;
import static mokiyoki.enhancedanimals.init.ModItems.SADDLE_BASIC_LEATHERCLOTHSEAT_GOLD;
import static mokiyoki.enhancedanimals.init.ModItems.SADDLE_BASIC_LEATHERCLOTHSEAT_WOOD;
import static mokiyoki.enhancedanimals.init.ModItems.SADDLE_BASIC_LEATHER_DIAMOND;
import static mokiyoki.enhancedanimals.init.ModItems.SADDLE_BASIC_LEATHER_GOLD;
import static mokiyoki.enhancedanimals.init.ModItems.SADDLE_BASIC_LEATHER_WOOD;
import static mokiyoki.enhancedanimals.init.ModItems.SADDLE_ENGLISH_CLOTH;
import static mokiyoki.enhancedanimals.init.ModItems.SADDLE_ENGLISH_CLOTH_DIAMOND;
import static mokiyoki.enhancedanimals.init.ModItems.SADDLE_ENGLISH_CLOTH_GOLD;
import static mokiyoki.enhancedanimals.init.ModItems.SADDLE_ENGLISH_CLOTH_WOOD;
import static mokiyoki.enhancedanimals.init.ModItems.SADDLE_ENGLISH_LEATHER;
import static mokiyoki.enhancedanimals.init.ModItems.SADDLE_ENGLISH_LEATHERCLOTHSEAT;
import static mokiyoki.enhancedanimals.init.ModItems.SADDLE_ENGLISH_LEATHERCLOTHSEAT_DIAMOND;
import static mokiyoki.enhancedanimals.init.ModItems.SADDLE_ENGLISH_LEATHERCLOTHSEAT_GOLD;
import static mokiyoki.enhancedanimals.init.ModItems.SADDLE_ENGLISH_LEATHERCLOTHSEAT_WOOD;
import static mokiyoki.enhancedanimals.init.ModItems.SADDLE_ENGLISH_LEATHER_DIAMOND;
import static mokiyoki.enhancedanimals.init.ModItems.SADDLE_ENGLISH_LEATHER_GOLD;
import static mokiyoki.enhancedanimals.init.ModItems.SADDLE_ENGLISH_LEATHER_WOOD;
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
        ModMemoryModuleTypes.register(FMLJavaModLoadingContext.get().getModEventBus());

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setupCauldronInteractions);
    }

    private void setup(final FMLCommonSetupEvent event) {
        int messageNumber = 0;
        channel.messageBuilder(EAEquipmentPacket.class, messageNumber++).encoder(EAEquipmentPacket::writePacketData).decoder(EAEquipmentPacket::new).consumer(EAEquipmentPacket::processPacket).add();
        channel.messageBuilder(AxolotlBucketTexturePacket.class, messageNumber++).encoder(AxolotlBucketTexturePacket::writePacketData).decoder(AxolotlBucketTexturePacket::new).consumer(AxolotlBucketTexturePacket::processPacket).add();
    }

    private void setupCauldronInteractions(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            CauldronInteraction.WATER.put(SADDLE_BASIC_LEATHER.get(), DYEABLE_ITEM);
            CauldronInteraction.WATER.put(SADDLE_BASIC_LEATHER_GOLD.get(), DYEABLE_ITEM);
            CauldronInteraction.WATER.put(SADDLE_BASIC_LEATHER_DIAMOND.get(), DYEABLE_ITEM);
            CauldronInteraction.WATER.put(SADDLE_BASIC_LEATHER_WOOD.get(), DYEABLE_ITEM);
            CauldronInteraction.WATER.put(SADDLE_BASIC_CLOTH.get(), DYEABLE_ITEM);
            CauldronInteraction.WATER.put(SADDLE_BASIC_CLOTH_GOLD.get(), DYEABLE_ITEM);
            CauldronInteraction.WATER.put(SADDLE_BASIC_CLOTH_DIAMOND.get(), DYEABLE_ITEM);
            CauldronInteraction.WATER.put(SADDLE_BASIC_CLOTH_WOOD.get(), DYEABLE_ITEM);
            CauldronInteraction.WATER.put(SADDLE_BASIC_LEATHERCLOTHSEAT.get(), DYEABLE_ITEM);
            CauldronInteraction.WATER.put(SADDLE_BASIC_LEATHERCLOTHSEAT_GOLD.get(), DYEABLE_ITEM);
            CauldronInteraction.WATER.put(SADDLE_BASIC_LEATHERCLOTHSEAT_DIAMOND.get(), DYEABLE_ITEM);
            CauldronInteraction.WATER.put(SADDLE_BASIC_LEATHERCLOTHSEAT_WOOD.get(), DYEABLE_ITEM);
            CauldronInteraction.WATER.put(SADDLE_BASICPOMEL_LEATHER.get(), DYEABLE_ITEM);
            CauldronInteraction.WATER.put(SADDLE_BASICPOMEL_LEATHER_GOLD.get(), DYEABLE_ITEM);
            CauldronInteraction.WATER.put(SADDLE_BASICPOMEL_LEATHER_DIAMOND.get(), DYEABLE_ITEM);
            CauldronInteraction.WATER.put(SADDLE_BASICPOMEL_LEATHER_WOOD.get(), DYEABLE_ITEM);
            CauldronInteraction.WATER.put(SADDLE_BASICPOMEL_CLOTH.get(), DYEABLE_ITEM);
            CauldronInteraction.WATER.put(SADDLE_BASICPOMEL_CLOTH_GOLD.get(), DYEABLE_ITEM);
            CauldronInteraction.WATER.put(SADDLE_BASICPOMEL_CLOTH_DIAMOND.get(), DYEABLE_ITEM);
            CauldronInteraction.WATER.put(SADDLE_BASICPOMEL_CLOTH_WOOD.get(), DYEABLE_ITEM);
            CauldronInteraction.WATER.put(SADDLE_BASICPOMEL_LEATHERCLOTHSEAT.get(), DYEABLE_ITEM);
            CauldronInteraction.WATER.put(SADDLE_BASICPOMEL_LEATHERCLOTHSEAT_GOLD.get(), DYEABLE_ITEM);
            CauldronInteraction.WATER.put(SADDLE_BASICPOMEL_LEATHERCLOTHSEAT_DIAMOND.get(), DYEABLE_ITEM);
            CauldronInteraction.WATER.put(SADDLE_BASICPOMEL_LEATHERCLOTHSEAT_WOOD.get(), DYEABLE_ITEM);
            CauldronInteraction.WATER.put(SADDLE_ENGLISH_LEATHER.get(), DYEABLE_ITEM);
            CauldronInteraction.WATER.put(SADDLE_ENGLISH_LEATHER_GOLD.get(), DYEABLE_ITEM);
            CauldronInteraction.WATER.put(SADDLE_ENGLISH_LEATHER_DIAMOND.get(), DYEABLE_ITEM);
            CauldronInteraction.WATER.put(SADDLE_ENGLISH_LEATHER_WOOD.get(), DYEABLE_ITEM);
            CauldronInteraction.WATER.put(SADDLE_ENGLISH_CLOTH.get(), DYEABLE_ITEM);
            CauldronInteraction.WATER.put(SADDLE_ENGLISH_CLOTH_GOLD.get(), DYEABLE_ITEM);
            CauldronInteraction.WATER.put(SADDLE_ENGLISH_CLOTH_DIAMOND.get(), DYEABLE_ITEM);
            CauldronInteraction.WATER.put(SADDLE_ENGLISH_CLOTH_WOOD.get(), DYEABLE_ITEM);
            CauldronInteraction.WATER.put(SADDLE_ENGLISH_LEATHERCLOTHSEAT.get(), DYEABLE_ITEM);
            CauldronInteraction.WATER.put(SADDLE_ENGLISH_LEATHERCLOTHSEAT_GOLD.get(), DYEABLE_ITEM);
            CauldronInteraction.WATER.put(SADDLE_ENGLISH_LEATHERCLOTHSEAT_DIAMOND.get(), DYEABLE_ITEM);
            CauldronInteraction.WATER.put(SADDLE_ENGLISH_LEATHERCLOTHSEAT_WOOD.get(), DYEABLE_ITEM);
            CauldronInteraction.WATER.put(BRIDLE_BASIC_LEATHER.get(), DYEABLE_ITEM);
            CauldronInteraction.WATER.put(BRIDLE_BASIC_LEATHER_GOLD.get(), DYEABLE_ITEM);
            CauldronInteraction.WATER.put(BRIDLE_BASIC_LEATHER_DIAMOND.get(), DYEABLE_ITEM);
            CauldronInteraction.WATER.put(BRIDLE_BASIC_CLOTH.get(), DYEABLE_ITEM);
            CauldronInteraction.WATER.put(BRIDLE_BASIC_CLOTH_GOLD.get(), DYEABLE_ITEM);
            CauldronInteraction.WATER.put(COLLAR_BASIC_CLOTH.get(), DYEABLE_ITEM);
            CauldronInteraction.WATER.put(COLLAR_BASIC_CLOTH_IRONRING.get(), DYEABLE_ITEM);
            CauldronInteraction.WATER.put(COLLAR_BASIC_CLOTH_IRONBELL.get(), DYEABLE_ITEM);
            CauldronInteraction.WATER.put(COLLAR_BASIC_CLOTH_GOLDRING.get(), DYEABLE_ITEM);
            CauldronInteraction.WATER.put(COLLAR_BASIC_CLOTH_GOLDBELL.get(), DYEABLE_ITEM);
            CauldronInteraction.WATER.put(COLLAR_BASIC_CLOTH_DIAMONDRING.get(), DYEABLE_ITEM);
            CauldronInteraction.WATER.put(COLLAR_BASIC_CLOTH_DIAMONDBELL.get(), DYEABLE_ITEM);
            CauldronInteraction.WATER.put(COLLAR_BASIC_LEATHER.get(), DYEABLE_ITEM);
            CauldronInteraction.WATER.put(COLLAR_BASIC_LEATHER_IRONRING.get(), DYEABLE_ITEM);
            CauldronInteraction.WATER.put(COLLAR_BASIC_LEATHER_IRONBELL.get(), DYEABLE_ITEM);
            CauldronInteraction.WATER.put(COLLAR_BASIC_LEATHER_GOLDRING.get(), DYEABLE_ITEM);
            CauldronInteraction.WATER.put(COLLAR_BASIC_LEATHER_GOLDBELL.get(), DYEABLE_ITEM);
            CauldronInteraction.WATER.put(COLLAR_BASIC_LEATHER_DIAMONDRING.get(), DYEABLE_ITEM);
            CauldronInteraction.WATER.put(COLLAR_BASIC_LEATHER_DIAMONDBELL.get(), DYEABLE_ITEM);
        });
    }

    CauldronInteraction DYEABLE_ITEM = (p_175629_, p_175630_, p_175631_, p_175632_, p_175633_, p_175634_) -> {
        Item item = p_175634_.getItem();
        if (!(item instanceof CustomizableAnimalEquipment)) {
            return InteractionResult.PASS;
        } else {
            CustomizableAnimalEquipment customizableAnimalEquipment = (CustomizableAnimalEquipment)item;
            if (!customizableAnimalEquipment.hasCustomColor(p_175634_)) {
                return InteractionResult.PASS;
            } else {
                if (!p_175630_.isClientSide) {
                    customizableAnimalEquipment.clearColor(p_175634_);
                    LayeredCauldronBlock.lowerFillLevel(p_175629_, p_175630_, p_175631_);
                }

                return InteractionResult.sidedSuccess(p_175630_.isClientSide);
            }
        }
    };

}

