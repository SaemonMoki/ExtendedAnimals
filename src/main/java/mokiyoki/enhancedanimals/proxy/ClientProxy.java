package mokiyoki.enhancedanimals.proxy;

import mokiyoki.enhancedanimals.gui.EggCartonScreen;
import mokiyoki.enhancedanimals.gui.EncyclopediaScreen;
import mokiyoki.enhancedanimals.gui.EnhancedAnimalScreen;
import mokiyoki.enhancedanimals.init.ModBlocks;
import mokiyoki.enhancedanimals.init.ModItems;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GrassColor;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;

import static mokiyoki.enhancedanimals.util.handlers.EventRegistry.EGG_CARTON_CONTAINER;
import static mokiyoki.enhancedanimals.util.handlers.EventRegistry.ENHANCED_ANIMAL_CONTAINER;

/**
 * Created by moki on 24/08/2018.
 */
public class ClientProxy implements IProxy {
    @Override
    public void init(FMLCommonSetupEvent event) {
    }

    @OnlyIn(Dist.CLIENT)
    public void initClientSetup(FMLClientSetupEvent event) {

        MenuScreens.register(EGG_CARTON_CONTAINER, EggCartonScreen::new);
        MenuScreens.register(ENHANCED_ANIMAL_CONTAINER, EnhancedAnimalScreen::new);

        ItemBlockRenderTypes.setRenderLayer(ModBlocks.SPARSEGRASS_BLOCK.get(), RenderType.cutoutMipped());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.PATCHYMYCELIUM_BLOCK.get(), RenderType.cutoutMipped());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.TURTLE_EGG.get(), RenderType.cutout());
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
    }

    @Override
    public void initLoadComplete(FMLLoadCompleteEvent event) {
        BlockColors blockColours = Minecraft.getInstance().getBlockColors();
        ItemColors itemColours = Minecraft.getInstance().getItemColors();

        //Grass Colouring
        blockColours.register((state, world, pos, tintIndex) ->
                world != null && pos != null ? BiomeColors.getAverageGrassColor(world, pos) : GrassColor.get(0.5D, 1.0D),
            ModBlocks.SPARSEGRASS_BLOCK.get(), ModBlocks.GROWABLE_GRASS.get(), ModBlocks.GROWABLE_TALL_GRASS.get(), ModBlocks.GROWABLE_FERN.get(), ModBlocks.GROWABLE_LARGE_FERN.get());

        //Item Colouring
        itemColours.register((stack, tintIndex) -> {
                BlockState BlockState = ((BlockItem)stack.getItem()).getBlock().defaultBlockState();
                return blockColours.getColor(BlockState, null, null, tintIndex); },
            ModBlocks.SPARSEGRASS_BLOCK.get(), ModBlocks.GROWABLE_GRASS.get(), ModBlocks.GROWABLE_TALL_GRASS.get(), ModBlocks.GROWABLE_FERN.get(), ModBlocks.GROWABLE_LARGE_FERN.get());

        //Dyeable Item colouring
        //TODO figure out how to use colour map for some items
        itemColours.register((itemStack, tintIndex) -> {
            return tintIndex > 0 ? -1 : ((DyeableLeatherItem)itemStack.getItem()).getColor(itemStack); },
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
        DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> Minecraft.getInstance().setScreen(EncyclopediaScreen.currentEncyclopedia));
    }
}
