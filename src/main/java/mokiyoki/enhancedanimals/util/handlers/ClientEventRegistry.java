package mokiyoki.enhancedanimals.util.handlers;

import mokiyoki.enhancedanimals.gui.EggCartonScreen;
import mokiyoki.enhancedanimals.gui.EnhancedAnimalScreen;
import mokiyoki.enhancedanimals.init.ModBlocks;
import mokiyoki.enhancedanimals.init.ModItems;
import mokiyoki.enhancedanimals.init.ModParticles;
import mokiyoki.enhancedanimals.model.EnhancedAxolotlBucketModel;
import mokiyoki.enhancedanimals.model.ModelEnhancedAxolotl;
import mokiyoki.enhancedanimals.model.ModelEnhancedAxolotlEgg;
import mokiyoki.enhancedanimals.model.ModelEnhancedChicken;
import mokiyoki.enhancedanimals.model.ModelEnhancedCow;
import mokiyoki.enhancedanimals.model.ModelEnhancedLlama;
import mokiyoki.enhancedanimals.model.ModelEnhancedPig;
import mokiyoki.enhancedanimals.model.ModelEnhancedRabbit;
import mokiyoki.enhancedanimals.model.ModelEnhancedSheep;
import mokiyoki.enhancedanimals.model.ModelEnhancedTurtle;
import mokiyoki.enhancedanimals.particle.FeatherParticle;
import mokiyoki.enhancedanimals.renderer.ChickenNestTileEntityRenderer;
import mokiyoki.enhancedanimals.renderer.EggCartonTileEntityRenderer;
import mokiyoki.enhancedanimals.renderer.EnhancedLlamaSpitRenderer;
import mokiyoki.enhancedanimals.renderer.ModelLayers;
import mokiyoki.enhancedanimals.renderer.RenderEnhancedAxolotl;
import mokiyoki.enhancedanimals.renderer.RenderEnhancedAxolotlEgg;
import mokiyoki.enhancedanimals.renderer.RenderEnhancedChicken;
import mokiyoki.enhancedanimals.renderer.RenderEnhancedCow;
import mokiyoki.enhancedanimals.renderer.RenderEnhancedLlama;
import mokiyoki.enhancedanimals.renderer.RenderEnhancedMoobloom;
import mokiyoki.enhancedanimals.renderer.RenderEnhancedMooshroom;
import mokiyoki.enhancedanimals.renderer.RenderEnhancedPig;
import mokiyoki.enhancedanimals.renderer.RenderEnhancedRabbit;
import mokiyoki.enhancedanimals.renderer.RenderEnhancedSheep;
import mokiyoki.enhancedanimals.renderer.RenderEnhancedTurtle;
import mokiyoki.enhancedanimals.util.Reference;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.world.item.BlockItem;
import mokiyoki.enhancedanimals.items.CustomizableAnimalEquipment;
import net.minecraft.util.FastColor;
import net.minecraft.world.level.GrassColor;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import mokiyoki.enhancedanimals.renderer.RenderEnhancedAxolotlBucket;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

import static mokiyoki.enhancedanimals.init.ModEntities.*;
import static mokiyoki.enhancedanimals.init.ModTileEntities.CHICKEN_NEST_TILE_ENTITY;
import static mokiyoki.enhancedanimals.util.handlers.EventRegistry.EGG_CARTON_CONTAINER;
import static mokiyoki.enhancedanimals.init.ModTileEntities.EGG_CARTON_TILE_ENTITY;
import static mokiyoki.enhancedanimals.util.handlers.EventRegistry.ENHANCED_ANIMAL_CONTAINER;

@EventBusSubscriber(modid = Reference.MODID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
public class ClientEventRegistry {

    @SubscribeEvent
    public static void registerClientExtensions(RegisterClientExtensionsEvent event) {
        event.registerItem(new IClientItemExtensions() {
            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                return RenderEnhancedAxolotlBucket.AXOLOTL_BUCKET_RENDERER;
            }
        }, ModItems.ENHANCED_AXOLOTL_BUCKET.get());
    }

    @SubscribeEvent
    public static void registerParticleFactories(RegisterParticleProvidersEvent event) {
        event.registerSpecial(ModParticles.FEATHER.get(), new FeatherParticle.Provider());
    }

    @SubscribeEvent
    public static void registerMenuScreens(RegisterMenuScreensEvent event) {
        event.register(EGG_CARTON_CONTAINER, EggCartonScreen::new);
        event.register(ENHANCED_ANIMAL_CONTAINER, EnhancedAnimalScreen::new);
    }

    @SubscribeEvent
    public static void initClientSetup(FMLClientSetupEvent event) {
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.SPARSEGRASS_BLOCK.get(), RenderType.cutoutMipped());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.PATCHYMYCELIUM_BLOCK.get(), RenderType.cutoutMipped());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.UNBOUNDHAY_BLOCK.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.GROWABLE_ALLIUM.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.GROWABLE_AZURE_BLUET.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.GROWABLE_BLUE_ORCHID.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.GROWABLE_CORNFLOWER.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.GROWABLE_DANDELION.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.GROWABLE_OXEYE_DAISY.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.GROWABLE_GRASS.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.GROWABLE_FERN.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.GROWABLE_ROSE_BUSH.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.GROWABLE_SUNFLOWER.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.GROWABLE_TALL_GRASS.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.GROWABLE_LARGE_FERN.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.POST_ACACIA.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.POST_BIRCH.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.POST_DARK_OAK.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.POST_JUNGLE.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.POST_OAK.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.POST_SPRUCE.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.CHICKEN_NEST.get(), RenderType.cutout());
    }

    @SubscribeEvent
    public static void onBlockColouredEvent(RegisterColorHandlersEvent.Block event) {
        event.getBlockColors().register((state, blockAndTintGetter, pos, tintIndex) ->
                blockAndTintGetter != null && pos != null ? BiomeColors.getAverageGrassColor(blockAndTintGetter, pos) : GrassColor.get(0.5D, 1.0D),
                ModBlocks.SPARSEGRASS_BLOCK.get(), ModBlocks.GROWABLE_GRASS.get(), ModBlocks.GROWABLE_TALL_GRASS.get(), ModBlocks.GROWABLE_FERN.get(), ModBlocks.GROWABLE_LARGE_FERN.get());
//        event.getBlockColors().register((state, blockAndTintGetter, pos, tintIndex) ->
//                pos != null ? EnhancedChickenEggBlock.getEggColour(state, pos, tintIndex) : -1,
//                ModBlocks.CHICKEN_NEST.get());
    }

    @SubscribeEvent
    public static void onItemColouredEvent(RegisterColorHandlersEvent.Item event) {
        event.getItemColors().register((stack, tintIndex) -> {
                    BlockState BlockState = ((BlockItem)stack.getItem()).getBlock().defaultBlockState();
                    return event.getBlockColors().getColor(BlockState, null, null, tintIndex); },
                ModBlocks.SPARSEGRASS_BLOCK.get(), ModBlocks.GROWABLE_GRASS.get(), ModBlocks.GROWABLE_TALL_GRASS.get(), ModBlocks.GROWABLE_FERN.get(), ModBlocks.GROWABLE_LARGE_FERN.get());

        event.getItemColors().register((itemStack, tintIndex) -> tintIndex > 0 ? -1 : FastColor.ARGB32.opaque(((CustomizableAnimalEquipment)itemStack.getItem()).getColor(itemStack)),
                ModItems.BRIDLE_BASIC_LEATHER.get(), ModItems.BRIDLE_BASIC_LEATHER_GOLD.get(), ModItems.BRIDLE_BASIC_LEATHER_DIAMOND.get(),
                ModItems.BRIDLE_BASIC_CLOTH.get(), ModItems.BRIDLE_BASIC_CLOTH_GOLD.get(), ModItems.BRIDLE_BASIC_CLOTH_DIAMOND.get(),
                ModItems.SADDLE_BASIC_LEATHER.get(), ModItems.SADDLE_BASIC_LEATHER_DIAMOND.get(), ModItems.SADDLE_BASIC_LEATHER_GOLD.get(), ModItems.SADDLE_BASIC_LEATHER_WOOD.get(),
                ModItems.SADDLE_BASIC_CLOTH.get(), ModItems.SADDLE_BASIC_CLOTH_GOLD.get(), ModItems.SADDLE_BASIC_CLOTH_DIAMOND.get(), ModItems.SADDLE_BASIC_CLOTH_WOOD.get(),
                ModItems.SADDLE_BASIC_LEATHERCLOTHSEAT.get(), ModItems.SADDLE_BASIC_LEATHERCLOTHSEAT_GOLD.get(), ModItems.SADDLE_BASIC_LEATHERCLOTHSEAT_DIAMOND.get(), ModItems.SADDLE_BASIC_LEATHERCLOTHSEAT_WOOD.get(),
                ModItems.SADDLE_BASICPOMEL_LEATHER.get(), ModItems.SADDLE_BASICPOMEL_LEATHER_GOLD.get(), ModItems.SADDLE_BASICPOMEL_LEATHER_DIAMOND.get(), ModItems.SADDLE_BASICPOMEL_LEATHER_WOOD.get(),
                ModItems.SADDLE_BASICPOMEL_CLOTH.get(), ModItems.SADDLE_BASICPOMEL_CLOTH_GOLD.get(), ModItems.SADDLE_BASICPOMEL_CLOTH_DIAMOND.get(), ModItems.SADDLE_BASICPOMEL_CLOTH_WOOD.get(),
                ModItems.SADDLE_BASICPOMEL_LEATHERCLOTHSEAT.get(), ModItems.SADDLE_BASICPOMEL_LEATHERCLOTHSEAT_GOLD.get(), ModItems.SADDLE_BASICPOMEL_LEATHERCLOTHSEAT_DIAMOND.get(), ModItems.SADDLE_BASICPOMEL_LEATHERCLOTHSEAT_WOOD.get(),
                ModItems.SADDLE_ENGLISH_LEATHER.get(), ModItems.SADDLE_ENGLISH_LEATHER_GOLD.get(), ModItems.SADDLE_ENGLISH_LEATHER_DIAMOND.get(), ModItems.SADDLE_ENGLISH_LEATHER_WOOD.get(),
                ModItems.SADDLE_ENGLISH_CLOTH.get(),ModItems.SADDLE_ENGLISH_CLOTH_GOLD.get(), ModItems.SADDLE_ENGLISH_CLOTH_DIAMOND.get(), ModItems.SADDLE_ENGLISH_CLOTH_WOOD.get(),
                ModItems.SADDLE_ENGLISH_LEATHERCLOTHSEAT.get(), ModItems.SADDLE_ENGLISH_LEATHERCLOTHSEAT_GOLD.get(), ModItems.SADDLE_ENGLISH_LEATHERCLOTHSEAT_DIAMOND.get(), ModItems.SADDLE_ENGLISH_LEATHERCLOTHSEAT_WOOD.get(),
                ModItems.COLLAR_BASIC_LEATHER.get(), ModItems.COLLAR_BASIC_LEATHER_IRONBELL.get(), ModItems.COLLAR_BASIC_LEATHER_IRONRING.get(), ModItems.COLLAR_BASIC_LEATHER_GOLDBELL.get(), ModItems.COLLAR_BASIC_LEATHER_GOLDRING.get(), ModItems.COLLAR_BASIC_LEATHER_DIAMONDBELL.get(), ModItems.COLLAR_BASIC_LEATHER_DIAMONDRING.get(),
                ModItems.COLLAR_BASIC_CLOTH.get(), ModItems.COLLAR_BASIC_CLOTH_IRONBELL.get(), ModItems.COLLAR_BASIC_CLOTH_IRONRING.get(), ModItems.COLLAR_BASIC_CLOTH_GOLDBELL.get(), ModItems.COLLAR_BASIC_CLOTH_GOLDRING.get(), ModItems.COLLAR_BASIC_CLOTH_DIAMONDBELL.get(), ModItems.COLLAR_BASIC_CLOTH_DIAMONDRING.get());
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void onEntityRenderersRegistry(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ENHANCED_AXOLOTL_EGG.get(), RenderEnhancedAxolotlEgg::new);
        event.registerEntityRenderer(ENHANCED_AXOLOTL.get(), RenderEnhancedAxolotl::new);
        event.registerEntityRenderer(ENHANCED_CHICKEN.get(), RenderEnhancedChicken::new);
        event.registerEntityRenderer(ENHANCED_COW.get(), RenderEnhancedCow::new);
        event.registerEntityRenderer(ENHANCED_MOOSHROOM.get(), RenderEnhancedMooshroom::new);
        event.registerEntityRenderer(ENHANCED_RABBIT.get(), RenderEnhancedRabbit::new);
        event.registerEntityRenderer(ENHANCED_SHEEP.get(), RenderEnhancedSheep::new);
        event.registerEntityRenderer(ENHANCED_LLAMA.get(), RenderEnhancedLlama::new);
        event.registerEntityRenderer(ENHANCED_PIG.get(), RenderEnhancedPig::new);
//        event.registerEntityRenderer(ENHANCED_HORSE.get(), RenderEnhancedHorse::new);
        event.registerEntityRenderer(ENHANCED_MOOBLOOM.get(), RenderEnhancedMoobloom::new);
        event.registerEntityRenderer(ENHANCED_TURTLE.get(), RenderEnhancedTurtle::new);
        event.registerEntityRenderer(ENHANCED_LLAMA_SPIT.get(), EnhancedLlamaSpitRenderer::new);
        event.registerEntityRenderer(ENHANCED_ENTITY_EGG_ENTITY_TYPE.get(), ThrownItemRenderer::new);

        event.registerBlockEntityRenderer(EGG_CARTON_TILE_ENTITY.get(), EggCartonTileEntityRenderer::new);
        event.registerBlockEntityRenderer(CHICKEN_NEST_TILE_ENTITY.get(), ChickenNestTileEntityRenderer::new);
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void registerLayerDefinition(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(RenderEnhancedAxolotlEgg.AXOLOTL_EGG_LAYER, ModelEnhancedAxolotlEgg::createBodyLayer);
        event.registerLayerDefinition(RenderEnhancedAxolotl.AXOLOTL_LAYER, ModelEnhancedAxolotl::createBodyLayer);
        event.registerLayerDefinition(RenderEnhancedChicken.CHICKEN_LAYER, ModelEnhancedChicken::createBodyLayer);
        event.registerLayerDefinition(RenderEnhancedCow.COW_LAYER, ModelEnhancedCow::createBodyLayer);
        event.registerLayerDefinition(RenderEnhancedSheep.SHEEP_LAYER, ModelEnhancedSheep::createBodyLayer);
        event.registerLayerDefinition(RenderEnhancedRabbit.RABBIT_LAYER, ModelEnhancedRabbit::createBodyLayer);
        event.registerLayerDefinition(RenderEnhancedMooshroom.MOOSHROOM_LAYER, ModelEnhancedCow::createBodyLayer);
        event.registerLayerDefinition(RenderEnhancedMoobloom.MOOBLOOM_LAYER, ModelEnhancedCow::createBodyLayer);
        event.registerLayerDefinition(RenderEnhancedPig.PIG_LAYER, ModelEnhancedPig::createBodyLayer);
        event.registerLayerDefinition(RenderEnhancedLlama.LLAMA_LAYER, ModelEnhancedLlama::createBodyLayer);
        event.registerLayerDefinition(RenderEnhancedTurtle.TURTLE_LAYER, ModelEnhancedTurtle::createBodyLayer);
        event.registerLayerDefinition(EggCartonTileEntityRenderer.EGG_CARTON, EggCartonTileEntityRenderer::createBodyLayer);
        event.registerLayerDefinition(ChickenNestTileEntityRenderer.CHICKEN_NEST, ChickenNestTileEntityRenderer::createBodyLayer);
        event.registerLayerDefinition(ModelLayers.AXOLOTL_BUCKET_LAYER, EnhancedAxolotlBucketModel::createLayer);
    }

}
