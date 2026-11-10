package mokiyoki.enhancedanimals.util.handlers;

import net.minecraft.client.Minecraft;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.MenuAccess;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import java.util.function.Supplier;

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
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.world.item.BlockItem;
import mokiyoki.enhancedanimals.items.CustomizableAnimalEquipment;
import net.minecraft.util.FastColor;
import net.minecraft.world.level.GrassColor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import mokiyoki.enhancedanimals.renderer.RenderEnhancedAxolotlBucket;

import static mokiyoki.enhancedanimals.init.ModEntities.*;
import static mokiyoki.enhancedanimals.init.ModTileEntities.CHICKEN_NEST_TILE_ENTITY;
import static mokiyoki.enhancedanimals.util.handlers.EventRegistry.EGG_CARTON_CONTAINER;
import static mokiyoki.enhancedanimals.init.ModTileEntities.EGG_CARTON_TILE_ENTITY;
import static mokiyoki.enhancedanimals.util.handlers.EventRegistry.ENHANCED_ANIMAL_CONTAINER;

public class ClientEventRegistry {

    public static void registerItemRenderers(ItemRendererRegistrar event) {
        event.register(ModItems.ENHANCED_AXOLOTL_BUCKET.get(), () -> RenderEnhancedAxolotlBucket.AXOLOTL_BUCKET_RENDERER);
    }

    public static void registerParticleFactories(ParticleProviderRegistrar event) {
        event.register(ModParticles.FEATHER.get(), new FeatherParticle.Provider());
    }

    public static void registerMenuScreens(MenuScreenRegistrar event) {
        event.register(EGG_CARTON_CONTAINER.get(), EggCartonScreen::new);
        event.register(ENHANCED_ANIMAL_CONTAINER.get(), EnhancedAnimalScreen::new);
    }

    public static void registerRenderLayers(RenderLayerRegistrar event) {
        event.register(ModBlocks.SPARSEGRASS_BLOCK.get(), RenderType.cutoutMipped());
        event.register(ModBlocks.PATCHYMYCELIUM_BLOCK.get(), RenderType.cutoutMipped());
        event.register(ModBlocks.UNBOUNDHAY_BLOCK.get(), RenderType.cutout());
        event.register(ModBlocks.GROWABLE_ALLIUM.get(), RenderType.cutout());
        event.register(ModBlocks.GROWABLE_AZURE_BLUET.get(), RenderType.cutout());
        event.register(ModBlocks.GROWABLE_BLUE_ORCHID.get(), RenderType.cutout());
        event.register(ModBlocks.GROWABLE_CORNFLOWER.get(), RenderType.cutout());
        event.register(ModBlocks.GROWABLE_DANDELION.get(), RenderType.cutout());
        event.register(ModBlocks.GROWABLE_OXEYE_DAISY.get(), RenderType.cutout());
        event.register(ModBlocks.GROWABLE_GRASS.get(), RenderType.cutout());
        event.register(ModBlocks.GROWABLE_FERN.get(), RenderType.cutout());
        event.register(ModBlocks.GROWABLE_ROSE_BUSH.get(), RenderType.cutout());
        event.register(ModBlocks.GROWABLE_SUNFLOWER.get(), RenderType.cutout());
        event.register(ModBlocks.GROWABLE_TALL_GRASS.get(), RenderType.cutout());
        event.register(ModBlocks.GROWABLE_LARGE_FERN.get(), RenderType.cutout());
        event.register(ModBlocks.POST_ACACIA.get(), RenderType.cutout());
        event.register(ModBlocks.POST_BIRCH.get(), RenderType.cutout());
        event.register(ModBlocks.POST_DARK_OAK.get(), RenderType.cutout());
        event.register(ModBlocks.POST_JUNGLE.get(), RenderType.cutout());
        event.register(ModBlocks.POST_OAK.get(), RenderType.cutout());
        event.register(ModBlocks.POST_SPRUCE.get(), RenderType.cutout());
        event.register(ModBlocks.CHICKEN_NEST.get(), RenderType.cutout());
    }

    public static void onBlockColouredEvent(BlockColorRegistrar event) {
        event.register((state, blockAndTintGetter, pos, tintIndex) ->
                blockAndTintGetter != null && pos != null ? BiomeColors.getAverageGrassColor(blockAndTintGetter, pos) : GrassColor.get(0.5D, 1.0D),
                ModBlocks.SPARSEGRASS_BLOCK.get(), ModBlocks.GROWABLE_GRASS.get(), ModBlocks.GROWABLE_TALL_GRASS.get(), ModBlocks.GROWABLE_FERN.get(), ModBlocks.GROWABLE_LARGE_FERN.get());
//        event.getBlockColors().register((state, blockAndTintGetter, pos, tintIndex) ->
//                pos != null ? EnhancedChickenEggBlock.getEggColour(state, pos, tintIndex) : -1,
//                ModBlocks.CHICKEN_NEST.get());
    }

    public static void onItemColouredEvent(ItemColorRegistrar event) {
        event.register((stack, tintIndex) -> {
                    BlockState BlockState = ((BlockItem)stack.getItem()).getBlock().defaultBlockState();
                    return Minecraft.getInstance().getBlockColors().getColor(BlockState, null, null, tintIndex); },
                ModBlocks.SPARSEGRASS_BLOCK.get(), ModBlocks.GROWABLE_GRASS.get(), ModBlocks.GROWABLE_TALL_GRASS.get(), ModBlocks.GROWABLE_FERN.get(), ModBlocks.GROWABLE_LARGE_FERN.get());

        event.register((itemStack, tintIndex) -> tintIndex > 0 ? -1 : FastColor.ARGB32.opaque(((CustomizableAnimalEquipment)itemStack.getItem()).getColor(itemStack)),
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

    public static void onEntityRenderersRegistry(EntityRendererRegistrar event) {
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

    public static void registerLayerDefinition(LayerDefinitionRegistrar event) {
        event.register(RenderEnhancedAxolotlEgg.AXOLOTL_EGG_LAYER, ModelEnhancedAxolotlEgg::createBodyLayer);
        event.register(RenderEnhancedAxolotl.AXOLOTL_LAYER, ModelEnhancedAxolotl::createBodyLayer);
        event.register(RenderEnhancedChicken.CHICKEN_LAYER, ModelEnhancedChicken::createBodyLayer);
        event.register(RenderEnhancedCow.COW_LAYER, ModelEnhancedCow::createBodyLayer);
        event.register(RenderEnhancedSheep.SHEEP_LAYER, ModelEnhancedSheep::createBodyLayer);
        event.register(RenderEnhancedRabbit.RABBIT_LAYER, ModelEnhancedRabbit::createBodyLayer);
        event.register(RenderEnhancedMooshroom.MOOSHROOM_LAYER, ModelEnhancedCow::createBodyLayer);
        event.register(RenderEnhancedMoobloom.MOOBLOOM_LAYER, ModelEnhancedCow::createBodyLayer);
        event.register(RenderEnhancedPig.PIG_LAYER, ModelEnhancedPig::createBodyLayer);
        event.register(RenderEnhancedLlama.LLAMA_LAYER, ModelEnhancedLlama::createBodyLayer);
        event.register(RenderEnhancedTurtle.TURTLE_LAYER, ModelEnhancedTurtle::createBodyLayer);
        event.register(EggCartonTileEntityRenderer.EGG_CARTON, EggCartonTileEntityRenderer::createBodyLayer);
        event.register(ChickenNestTileEntityRenderer.CHICKEN_NEST, ChickenNestTileEntityRenderer::createBodyLayer);
        event.register(ModelLayers.AXOLOTL_BUCKET_LAYER, EnhancedAxolotlBucketModel::createLayer);
    }

    public interface EntityRendererRegistrar {
        <E extends Entity> void registerEntityRenderer(EntityType<? extends E> entityType, EntityRendererProvider<E> provider);

        <T extends BlockEntity> void registerBlockEntityRenderer(BlockEntityType<? extends T> blockEntityType, BlockEntityRendererProvider<T> provider);
    }

    @FunctionalInterface
    public interface LayerDefinitionRegistrar {
        void register(ModelLayerLocation layer, Supplier<LayerDefinition> definition);
    }

    @FunctionalInterface
    public interface ParticleProviderRegistrar {
        <T extends ParticleOptions> void register(ParticleType<T> type, ParticleProvider<T> provider);
    }

    @FunctionalInterface
    public interface MenuScreenRegistrar {
        <M extends AbstractContainerMenu, U extends Screen & MenuAccess<M>> void register(MenuType<? extends M> menuType, MenuScreens.ScreenConstructor<M, U> screenConstructor);
    }

    @FunctionalInterface
    public interface BlockColorRegistrar {
        void register(BlockColor color, Block... blocks);
    }

    @FunctionalInterface
    public interface ItemColorRegistrar {
        void register(ItemColor color, ItemLike... items);
    }

    @FunctionalInterface
    public interface RenderLayerRegistrar {
        void register(Block block, RenderType renderType);
    }

    @FunctionalInterface
    public interface ItemRendererRegistrar {
        void register(Item item, Supplier<BlockEntityWithoutLevelRenderer> renderer);
    }
}
