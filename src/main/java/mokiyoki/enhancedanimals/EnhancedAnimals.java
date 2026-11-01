package mokiyoki.enhancedanimals;

import com.mojang.datafixers.util.Pair;
import mokiyoki.enhancedanimals.init.*;
import mokiyoki.enhancedanimals.init.ModSensorTypes;
import mokiyoki.enhancedanimals.items.CustomizableAnimalEquipment;
import mokiyoki.enhancedanimals.network.EAEquipmentPacket;
import mokiyoki.enhancedanimals.network.axolotl.AxolotlBucketTexturePacket;
import mokiyoki.enhancedanimals.config.GeneticAnimalsConfig;
import mokiyoki.enhancedanimals.util.handlers.EventSubscriber;
import mokiyoki.enhancedanimals.util.handlers.SpawnRegistry;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.event.server.ServerAboutToStartEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

import java.util.ArrayList;
import java.util.List;

import static mokiyoki.enhancedanimals.init.ModItems.BRIDLE_BASIC_CLOTH;
import static mokiyoki.enhancedanimals.init.ModItems.BRIDLE_BASIC_CLOTH_DIAMOND;
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

    public static EnhancedAnimals instance;

    public static final GeneticAnimalsConfig commonConfig = new GeneticAnimalsConfig();

    public EnhancedAnimals(IEventBus modEventBus, ModContainer modContainer) {
        instance = this;
        modContainer.registerConfig(ModConfig.Type.STARTUP, GeneticAnimalsConfig.getConfigSpecForLoader(), GeneticAnimalsConfig.getFileNameForLoader());

        modEventBus.addListener(this::registerPayloads);

        NeoForge.EVENT_BUS.register(new EventSubscriber());

        SpawnRegistry.register(modEventBus);

        ModAttributes.register(modEventBus);
        ModAttachments.register(modEventBus);
        ModDataComponents.register(modEventBus);
        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModEntities.register(modEventBus);
        ModTileEntities.register(modEventBus);
        ModCreativeTabs.register(modEventBus);
        ModSensorTypes.register(modEventBus);
        ModActivities.register(modEventBus);
        ModSounds.register(modEventBus);
        ModParticles.register(modEventBus);
        ModMemoryModuleTypes.register(modEventBus);

        modEventBus.addListener(this::setupCauldronInteractions);

        NeoForge.EVENT_BUS.addListener(this::addAnimalsToVillages);
    }

    private void registerPayloads(final RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar(PROTOCOL_VERSION);
        registrar.playToClient(EAEquipmentPacket.TYPE, EAEquipmentPacket.STREAM_CODEC, EAEquipmentPacket::processPacket);
        registrar.playToServer(AxolotlBucketTexturePacket.TYPE, AxolotlBucketTexturePacket.STREAM_CODEC, AxolotlBucketTexturePacket::processPacket);
    }

    private void addAnimalsToVillages(final ServerAboutToStartEvent event) {
        try {
            StructureTemplatePool oldPool = event.getServer().registryAccess().registryOrThrow(Registries.TEMPLATE_POOL).get(ResourceLocation.withDefaultNamespace("village/common/animals"));

            if (oldPool != null) {
                oldPool.rawTemplates = new ArrayList<>(oldPool.rawTemplates);
                List<StructurePoolElement> jigsawPieces = oldPool.templates;

                StructurePoolElement chickenPiece = StructurePoolElement.legacy("village/common/animals/chickens_1").apply(StructureTemplatePool.Projection.RIGID);
                oldPool.rawTemplates.add(Pair.of(chickenPiece, 7));
                jigsawPieces.add(chickenPiece);
                jigsawPieces.add(chickenPiece);
                jigsawPieces.add(chickenPiece);
                jigsawPieces.add(chickenPiece);
                jigsawPieces.add(chickenPiece);
                jigsawPieces.add(chickenPiece);
                jigsawPieces.add(chickenPiece);

                StructurePoolElement cow2Piece = StructurePoolElement.legacy("village/common/animals/cows_2").apply(StructureTemplatePool.Projection.RIGID);
                oldPool.rawTemplates.add(Pair.of(cow2Piece, 1));
                jigsawPieces.add(cow2Piece);

                StructurePoolElement cow3Piece = StructurePoolElement.legacy("village/common/animals/cows_3").apply(StructureTemplatePool.Projection.RIGID);
                oldPool.rawTemplates.add(Pair.of(cow3Piece, 1));
                jigsawPieces.add(cow3Piece);

                StructurePoolElement llamaPiece = StructurePoolElement.legacy("village/common/animals/llamas_1").apply(StructureTemplatePool.Projection.RIGID);
                oldPool.rawTemplates.add(Pair.of(llamaPiece, 3));
                jigsawPieces.add(llamaPiece);
                jigsawPieces.add(llamaPiece);
                jigsawPieces.add(llamaPiece);

                StructurePoolElement sheep3Piece = StructurePoolElement.legacy("village/common/animals/sheep_3").apply(StructureTemplatePool.Projection.RIGID);
                oldPool.rawTemplates.add(Pair.of(sheep3Piece, 1));
                jigsawPieces.add(sheep3Piece);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void setupCauldronInteractions(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            CauldronInteraction.WATER.map().put(SADDLE_BASIC_LEATHER.get(), DYEABLE_ITEM);
            CauldronInteraction.WATER.map().put(SADDLE_BASIC_LEATHER_GOLD.get(), DYEABLE_ITEM);
            CauldronInteraction.WATER.map().put(SADDLE_BASIC_LEATHER_DIAMOND.get(), DYEABLE_ITEM);
            CauldronInteraction.WATER.map().put(SADDLE_BASIC_LEATHER_WOOD.get(), DYEABLE_ITEM);
            CauldronInteraction.WATER.map().put(SADDLE_BASIC_CLOTH.get(), DYEABLE_ITEM);
            CauldronInteraction.WATER.map().put(SADDLE_BASIC_CLOTH_GOLD.get(), DYEABLE_ITEM);
            CauldronInteraction.WATER.map().put(SADDLE_BASIC_CLOTH_DIAMOND.get(), DYEABLE_ITEM);
            CauldronInteraction.WATER.map().put(SADDLE_BASIC_CLOTH_WOOD.get(), DYEABLE_ITEM);
            CauldronInteraction.WATER.map().put(SADDLE_BASIC_LEATHERCLOTHSEAT.get(), DYEABLE_ITEM);
            CauldronInteraction.WATER.map().put(SADDLE_BASIC_LEATHERCLOTHSEAT_GOLD.get(), DYEABLE_ITEM);
            CauldronInteraction.WATER.map().put(SADDLE_BASIC_LEATHERCLOTHSEAT_DIAMOND.get(), DYEABLE_ITEM);
            CauldronInteraction.WATER.map().put(SADDLE_BASIC_LEATHERCLOTHSEAT_WOOD.get(), DYEABLE_ITEM);
            CauldronInteraction.WATER.map().put(SADDLE_BASICPOMEL_LEATHER.get(), DYEABLE_ITEM);
            CauldronInteraction.WATER.map().put(SADDLE_BASICPOMEL_LEATHER_GOLD.get(), DYEABLE_ITEM);
            CauldronInteraction.WATER.map().put(SADDLE_BASICPOMEL_LEATHER_DIAMOND.get(), DYEABLE_ITEM);
            CauldronInteraction.WATER.map().put(SADDLE_BASICPOMEL_LEATHER_WOOD.get(), DYEABLE_ITEM);
            CauldronInteraction.WATER.map().put(SADDLE_BASICPOMEL_CLOTH.get(), DYEABLE_ITEM);
            CauldronInteraction.WATER.map().put(SADDLE_BASICPOMEL_CLOTH_GOLD.get(), DYEABLE_ITEM);
            CauldronInteraction.WATER.map().put(SADDLE_BASICPOMEL_CLOTH_DIAMOND.get(), DYEABLE_ITEM);
            CauldronInteraction.WATER.map().put(SADDLE_BASICPOMEL_CLOTH_WOOD.get(), DYEABLE_ITEM);
            CauldronInteraction.WATER.map().put(SADDLE_BASICPOMEL_LEATHERCLOTHSEAT.get(), DYEABLE_ITEM);
            CauldronInteraction.WATER.map().put(SADDLE_BASICPOMEL_LEATHERCLOTHSEAT_GOLD.get(), DYEABLE_ITEM);
            CauldronInteraction.WATER.map().put(SADDLE_BASICPOMEL_LEATHERCLOTHSEAT_DIAMOND.get(), DYEABLE_ITEM);
            CauldronInteraction.WATER.map().put(SADDLE_BASICPOMEL_LEATHERCLOTHSEAT_WOOD.get(), DYEABLE_ITEM);
            CauldronInteraction.WATER.map().put(SADDLE_ENGLISH_LEATHER.get(), DYEABLE_ITEM);
            CauldronInteraction.WATER.map().put(SADDLE_ENGLISH_LEATHER_GOLD.get(), DYEABLE_ITEM);
            CauldronInteraction.WATER.map().put(SADDLE_ENGLISH_LEATHER_DIAMOND.get(), DYEABLE_ITEM);
            CauldronInteraction.WATER.map().put(SADDLE_ENGLISH_LEATHER_WOOD.get(), DYEABLE_ITEM);
            CauldronInteraction.WATER.map().put(SADDLE_ENGLISH_CLOTH.get(), DYEABLE_ITEM);
            CauldronInteraction.WATER.map().put(SADDLE_ENGLISH_CLOTH_GOLD.get(), DYEABLE_ITEM);
            CauldronInteraction.WATER.map().put(SADDLE_ENGLISH_CLOTH_DIAMOND.get(), DYEABLE_ITEM);
            CauldronInteraction.WATER.map().put(SADDLE_ENGLISH_CLOTH_WOOD.get(), DYEABLE_ITEM);
            CauldronInteraction.WATER.map().put(SADDLE_ENGLISH_LEATHERCLOTHSEAT.get(), DYEABLE_ITEM);
            CauldronInteraction.WATER.map().put(SADDLE_ENGLISH_LEATHERCLOTHSEAT_GOLD.get(), DYEABLE_ITEM);
            CauldronInteraction.WATER.map().put(SADDLE_ENGLISH_LEATHERCLOTHSEAT_DIAMOND.get(), DYEABLE_ITEM);
            CauldronInteraction.WATER.map().put(SADDLE_ENGLISH_LEATHERCLOTHSEAT_WOOD.get(), DYEABLE_ITEM);
            CauldronInteraction.WATER.map().put(BRIDLE_BASIC_LEATHER.get(), DYEABLE_ITEM);
            CauldronInteraction.WATER.map().put(BRIDLE_BASIC_LEATHER_GOLD.get(), DYEABLE_ITEM);
            CauldronInteraction.WATER.map().put(BRIDLE_BASIC_LEATHER_DIAMOND.get(), DYEABLE_ITEM);
            CauldronInteraction.WATER.map().put(BRIDLE_BASIC_CLOTH.get(), DYEABLE_ITEM);
            CauldronInteraction.WATER.map().put(BRIDLE_BASIC_CLOTH_GOLD.get(), DYEABLE_ITEM);
            CauldronInteraction.WATER.map().put(BRIDLE_BASIC_CLOTH_DIAMOND.get(), DYEABLE_ITEM);
            CauldronInteraction.WATER.map().put(COLLAR_BASIC_CLOTH.get(), DYEABLE_ITEM);
            CauldronInteraction.WATER.map().put(COLLAR_BASIC_CLOTH_IRONRING.get(), DYEABLE_ITEM);
            CauldronInteraction.WATER.map().put(COLLAR_BASIC_CLOTH_IRONBELL.get(), DYEABLE_ITEM);
            CauldronInteraction.WATER.map().put(COLLAR_BASIC_CLOTH_GOLDRING.get(), DYEABLE_ITEM);
            CauldronInteraction.WATER.map().put(COLLAR_BASIC_CLOTH_GOLDBELL.get(), DYEABLE_ITEM);
            CauldronInteraction.WATER.map().put(COLLAR_BASIC_CLOTH_DIAMONDRING.get(), DYEABLE_ITEM);
            CauldronInteraction.WATER.map().put(COLLAR_BASIC_CLOTH_DIAMONDBELL.get(), DYEABLE_ITEM);
            CauldronInteraction.WATER.map().put(COLLAR_BASIC_LEATHER.get(), DYEABLE_ITEM);
            CauldronInteraction.WATER.map().put(COLLAR_BASIC_LEATHER_IRONRING.get(), DYEABLE_ITEM);
            CauldronInteraction.WATER.map().put(COLLAR_BASIC_LEATHER_IRONBELL.get(), DYEABLE_ITEM);
            CauldronInteraction.WATER.map().put(COLLAR_BASIC_LEATHER_GOLDRING.get(), DYEABLE_ITEM);
            CauldronInteraction.WATER.map().put(COLLAR_BASIC_LEATHER_GOLDBELL.get(), DYEABLE_ITEM);
            CauldronInteraction.WATER.map().put(COLLAR_BASIC_LEATHER_DIAMONDRING.get(), DYEABLE_ITEM);
            CauldronInteraction.WATER.map().put(COLLAR_BASIC_LEATHER_DIAMONDBELL.get(), DYEABLE_ITEM);
        });
    }

    CauldronInteraction DYEABLE_ITEM = (p_175629_, p_175630_, p_175631_, p_175632_, p_175633_, p_175634_) -> {
        Item item = p_175634_.getItem();
        if (!(item instanceof CustomizableAnimalEquipment)) {
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        } else {
            CustomizableAnimalEquipment customizableAnimalEquipment = (CustomizableAnimalEquipment)item;
            if (!customizableAnimalEquipment.hasCustomColor(p_175634_)) {
                return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
            } else {
                if (!p_175630_.isClientSide) {
                    customizableAnimalEquipment.clearColor(p_175634_);
                    LayeredCauldronBlock.lowerFillLevel(p_175629_, p_175630_, p_175631_);
                }

                return ItemInteractionResult.sidedSuccess(p_175630_.isClientSide);
            }
        }
    };

}

