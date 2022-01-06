package mokiyoki.enhancedanimals.proxy;

import mokiyoki.enhancedanimals.gui.EggCartonScreen;
import mokiyoki.enhancedanimals.gui.EncyclopediaScreen;
import mokiyoki.enhancedanimals.gui.EnhancedAnimalScreen;
import mokiyoki.enhancedanimals.init.ModBlocks;
import mokiyoki.enhancedanimals.init.ModItems;
import mokiyoki.enhancedanimals.renderer.*;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.client.renderer.entity.LlamaSpitRenderer;
import net.minecraft.client.renderer.entity.SpriteRenderer;
import net.minecraft.item.BlockItem;
import net.minecraft.item.IDyeableArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.GrassColors;
import net.minecraft.world.biome.BiomeColors;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;

import static mokiyoki.enhancedanimals.init.ModTileEntities.EGG_CARTON_TILE_ENTITY;
import static mokiyoki.enhancedanimals.util.handlers.EventRegistry.EGG_CARTON_CONTAINER;
import static mokiyoki.enhancedanimals.util.handlers.EventRegistry.ENHANCED_ANIMAL_CONTAINER;
import static mokiyoki.enhancedanimals.util.handlers.EventRegistry.ENHANCED_CHICKEN;
import static mokiyoki.enhancedanimals.util.handlers.EventRegistry.ENHANCED_COW;
import static mokiyoki.enhancedanimals.util.handlers.EventRegistry.ENHANCED_ENTITY_EGG_ENTITY_TYPE;
import static mokiyoki.enhancedanimals.util.handlers.EventRegistry.ENHANCED_LLAMA;
import static mokiyoki.enhancedanimals.util.handlers.EventRegistry.ENHANCED_LLAMA_SPIT;
import static mokiyoki.enhancedanimals.util.handlers.EventRegistry.ENHANCED_MOOSHROOM;
import static mokiyoki.enhancedanimals.util.handlers.EventRegistry.ENHANCED_MOOBLOOM;
import static mokiyoki.enhancedanimals.util.handlers.EventRegistry.ENHANCED_PIG;
import static mokiyoki.enhancedanimals.util.handlers.EventRegistry.ENHANCED_RABBIT;
import static mokiyoki.enhancedanimals.util.handlers.EventRegistry.ENHANCED_SHEEP;
import static mokiyoki.enhancedanimals.util.handlers.EventRegistry.ENHANCED_HORSE;
import static mokiyoki.enhancedanimals.util.handlers.EventRegistry.ENHANCED_TURTLE;
//import static mokiyoki.enhancedanimals.util.handlers.EventRegistry.ENHANCED_CAT;
//import static mokiyoki.enhancedanimals.util.handlers.EventRegistry.ENHANCED_BEE;

/**
 * Created by moki on 24/08/2018.
 */
public class ClientProxy implements IProxy {
    @Override
    public void init(FMLCommonSetupEvent event) {
    }

    @OnlyIn(Dist.CLIENT)
    public void initClientSetup(FMLClientSetupEvent event) {
        RenderingRegistry.registerEntityRenderingHandler(ENHANCED_CHICKEN, RenderEnhancedChicken::new);
        RenderingRegistry.registerEntityRenderingHandler(ENHANCED_RABBIT, RenderEnhancedRabbit::new);
        RenderingRegistry.registerEntityRenderingHandler(ENHANCED_SHEEP, RenderEnhancedSheep::new);
        RenderingRegistry.registerEntityRenderingHandler(ENHANCED_LLAMA, RenderEnhancedLlama::new);
        RenderingRegistry.registerEntityRenderingHandler(ENHANCED_COW, RenderEnhancedCow::new);
        RenderingRegistry.registerEntityRenderingHandler(ENHANCED_PIG, RenderEnhancedPig::new);
        RenderingRegistry.registerEntityRenderingHandler(ENHANCED_HORSE, RenderEnhancedHorse::new);
//        RenderingRegistry.registerEntityRenderingHandler(ENHANCED_CAT, RenderEnhancedCat::new);
//        RenderingRegistry.registerEntityRenderingHandler(ENHANCED_BEE, RenderEnhancedBee::new);
        RenderingRegistry.registerEntityRenderingHandler(ENHANCED_MOOSHROOM, RenderEnhancedMooshroom::new);
        RenderingRegistry.registerEntityRenderingHandler(ENHANCED_MOOBLOOM, RenderEnhancedMoobloom::new);
        RenderingRegistry.registerEntityRenderingHandler(ENHANCED_TURTLE, RenderEnhancedTurtle::new);
        RenderingRegistry.registerEntityRenderingHandler(ENHANCED_LLAMA_SPIT, EnhancedLlamaSpitRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ENHANCED_ENTITY_EGG_ENTITY_TYPE, renderManager -> new SpriteRenderer(renderManager, Minecraft.getInstance().getItemRenderer()));

        ScreenManager.registerFactory(EGG_CARTON_CONTAINER, EggCartonScreen::new);
        ScreenManager.registerFactory(ENHANCED_ANIMAL_CONTAINER, EnhancedAnimalScreen::new);
        ClientRegistry.bindTileEntityRenderer(EGG_CARTON_TILE_ENTITY, EggCartonTileEntityRenderer::new);

        RenderTypeLookup.setRenderLayer(ModBlocks.SPARSEGRASS_BLOCK, RenderType.getCutoutMipped());
        RenderTypeLookup.setRenderLayer(ModBlocks.PATCHYMYCELIUM_BLOCK, RenderType.getCutoutMipped());
        RenderTypeLookup.setRenderLayer(ModBlocks.TURTLE_EGG, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(ModBlocks.AXOLOTL_EGG, RenderType.getTranslucent());
        RenderTypeLookup.setRenderLayer(ModBlocks.UNBOUNDHAY_BLOCK, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(ModBlocks.GROWABLE_ALLIUM, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(ModBlocks.GROWABLE_AZURE_BLUET, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(ModBlocks.GROWABLE_BLUE_ORCHID, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(ModBlocks.GROWABLE_CORNFLOWER, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(ModBlocks.GROWABLE_DANDELION, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(ModBlocks.GROWABLE_OXEYE_DAISY, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(ModBlocks.GROWABLE_GRASS, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(ModBlocks.GROWABLE_FERN, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(ModBlocks.GROWABLE_ROSE_BUSH, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(ModBlocks.GROWABLE_SUNFLOWER, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(ModBlocks.GROWABLE_TALL_GRASS, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(ModBlocks.GROWABLE_LARGE_FERN, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(ModBlocks.POST_ACACIA, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(ModBlocks.POST_BIRCH, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(ModBlocks.POST_DARK_OAK, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(ModBlocks.POST_JUNGLE, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(ModBlocks.POST_OAK, RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(ModBlocks.POST_SPRUCE, RenderType.getCutout());
    }

    @Override
    public void initLoadComplete(FMLLoadCompleteEvent event) {
        BlockColors blockColours = Minecraft.getInstance().getBlockColors();
        ItemColors itemColours = Minecraft.getInstance().getItemColors();

        //Grass Colouring
        blockColours.register((state, world, pos, tintIndex) ->
                world != null && pos != null ? BiomeColors.getGrassColor(world, pos) : GrassColors.get(0.5D, 1.0D),
            ModBlocks.SPARSEGRASS_BLOCK, ModBlocks.GROWABLE_GRASS, ModBlocks.GROWABLE_TALL_GRASS, ModBlocks.GROWABLE_FERN, ModBlocks.GROWABLE_LARGE_FERN);

        //Item Colouring
        itemColours.register((stack, tintIndex) -> {
                BlockState BlockState = ((BlockItem)stack.getItem()).getBlock().getDefaultState();
                return blockColours.getColor(BlockState, null, null, tintIndex); },
            ModBlocks.SPARSEGRASS_BLOCK, ModBlocks.GROWABLE_GRASS, ModBlocks.GROWABLE_TALL_GRASS, ModBlocks.GROWABLE_FERN, ModBlocks.GROWABLE_LARGE_FERN);

        //Dyeable Item colouring
        //TODO figure out how to use colour map for some items
        itemColours.register((itemStack, tintIndex) -> {
            return tintIndex > 0 ? -1 : ((IDyeableArmorItem)itemStack.getItem()).getColor(itemStack); },
            ModItems.BRIDLE_BASIC_LEATHER, ModItems.BRIDLE_BASIC_LEATHER_GOLD, ModItems.BRIDLE_BASIC_LEATHER_DIAMOND,
                ModItems.BRIDLE_BASIC_CLOTH, ModItems.BRIDLE_BASIC_CLOTH_GOLD, ModItems.BRIDLE_BASIC_CLOTH_DIAMOND,
                ModItems.SADDLE_BASIC_LEATHER, ModItems.SADDLE_BASIC_LEATHER_DIAMOND, ModItems.SADDLE_BASIC_LEATHER_GOLD, ModItems.SADDLE_BASIC_LEATHER_WOOD,
                ModItems.SADDLE_BASIC_CLOTH, ModItems.SADDLE_BASIC_CLOTH_GOLD, ModItems.SADDLE_BASIC_CLOTH_DIAMOND, ModItems.SADDLE_BASIC_CLOTH_WOOD,
                ModItems.SADDLE_BASIC_LEATHERCLOTHSEAT, ModItems.SADDLE_BASIC_LEATHERCLOTHSEAT_GOLD, ModItems.SADDLE_BASIC_LEATHERCLOTHSEAT_DIAMOND, ModItems.SADDLE_BASIC_LEATHERCLOTHSEAT_WOOD,
                ModItems.SADDLE_BASICPOMEL_LEATHER, ModItems.SADDLE_BASICPOMEL_LEATHER_GOLD, ModItems.SADDLE_BASICPOMEL_LEATHER_DIAMOND, ModItems.SADDLE_BASICPOMEL_LEATHER_WOOD,
                ModItems.SADDLE_BASICPOMEL_CLOTH, ModItems.SADDLE_BASICPOMEL_CLOTH_GOLD, ModItems.SADDLE_BASICPOMEL_CLOTH_DIAMOND, ModItems.SADDLE_BASICPOMEL_CLOTH_WOOD,
                ModItems.SADDLE_BASICPOMEL_LEATHERCLOTHSEAT, ModItems.SADDLE_BASICPOMEL_LEATHERCLOTHSEAT_GOLD, ModItems.SADDLE_BASICPOMEL_LEATHERCLOTHSEAT_DIAMOND, ModItems.SADDLE_BASICPOMEL_LEATHERCLOTHSEAT_WOOD,
                ModItems.SADDLE_ENGLISH_LEATHER, ModItems.SADDLE_ENGLISH_LEATHER_GOLD, ModItems.SADDLE_ENGLISH_LEATHER_DIAMOND, ModItems.SADDLE_ENGLISH_LEATHER_WOOD,
                ModItems.SADDLE_ENGLISH_CLOTH,ModItems.SADDLE_ENGLISH_CLOTH_GOLD, ModItems.SADDLE_ENGLISH_CLOTH_DIAMOND, ModItems.SADDLE_ENGLISH_CLOTH_WOOD,
                ModItems.SADDLE_ENGLISH_LEATHERCLOTHSEAT, ModItems.SADDLE_ENGLISH_LEATHERCLOTHSEAT_GOLD, ModItems.SADDLE_ENGLISH_LEATHERCLOTHSEAT_DIAMOND, ModItems.SADDLE_ENGLISH_LEATHERCLOTHSEAT_WOOD,
                ModItems.COLLAR_BASIC_LEATHER, ModItems.COLLAR_BASIC_LEATHER_IRONBELL, ModItems.COLLAR_BASIC_LEATHER_IRONRING, ModItems.COLLAR_BASIC_LEATHER_GOLDBELL, ModItems.COLLAR_BASIC_LEATHER_GOLDRING, ModItems.COLLAR_BASIC_LEATHER_DIAMONDBELL, ModItems.COLLAR_BASIC_LEATHER_DIAMONDRING,
                ModItems.COLLAR_BASIC_CLOTH, ModItems.COLLAR_BASIC_CLOTH_IRONBELL, ModItems.COLLAR_BASIC_CLOTH_IRONRING, ModItems.COLLAR_BASIC_CLOTH_GOLDBELL, ModItems.COLLAR_BASIC_CLOTH_GOLDRING, ModItems.COLLAR_BASIC_CLOTH_DIAMONDBELL, ModItems.COLLAR_BASIC_CLOTH_DIAMONDRING);

    }

    @Override
    public void setEncylopediaInfo(ItemStack itemStack) {
        EncyclopediaScreen.encyclopedia = itemStack;
    }

    @Override
    public void openEncyclodepia() {
        DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> Minecraft.getInstance().displayGuiScreen(EncyclopediaScreen.currentEncyclopedia));
    }
}
