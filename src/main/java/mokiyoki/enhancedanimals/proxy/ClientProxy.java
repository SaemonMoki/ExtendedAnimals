package mokiyoki.enhancedanimals.proxy;

import mokiyoki.enhancedanimals.gui.EggCartonScreen;
import mokiyoki.enhancedanimals.gui.EncyclopediaScreen;
import mokiyoki.enhancedanimals.init.ModBlocks;
import mokiyoki.enhancedanimals.renderer.*;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.item.BlockItem;
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

import static mokiyoki.enhancedanimals.util.handlers.EventRegistry.EGG_CARTON_CONTAINER;
import static mokiyoki.enhancedanimals.util.handlers.EventRegistry.EGG_CARTON_TILE_ENTITY_TILE_ENTITY_TYPE;
import static mokiyoki.enhancedanimals.util.handlers.EventRegistry.ENHANCED_CHICKEN;
import static mokiyoki.enhancedanimals.util.handlers.EventRegistry.ENHANCED_COW;
import static mokiyoki.enhancedanimals.util.handlers.EventRegistry.ENHANCED_HORSE;
import static mokiyoki.enhancedanimals.util.handlers.EventRegistry.ENHANCED_LLAMA;
import static mokiyoki.enhancedanimals.util.handlers.EventRegistry.ENHANCED_MOOSHROOM;
import static mokiyoki.enhancedanimals.util.handlers.EventRegistry.ENHANCED_PIG;
import static mokiyoki.enhancedanimals.util.handlers.EventRegistry.ENHANCED_RABBIT;
import static mokiyoki.enhancedanimals.util.handlers.EventRegistry.ENHANCED_SHEEP;

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
        RenderingRegistry.registerEntityRenderingHandler(ENHANCED_MOOSHROOM, RenderEnhancedMooshroom::new);

        ScreenManager.registerFactory(EGG_CARTON_CONTAINER, EggCartonScreen::new);
        ClientRegistry.bindTileEntityRenderer(EGG_CARTON_TILE_ENTITY_TILE_ENTITY_TYPE, EggCartonTileEntityRenderer::new);
    }

    @Override
    public void initLoadComplete(FMLLoadCompleteEvent event) {
        BlockColors blockColours = Minecraft.getInstance().getBlockColors();
        ItemColors itemColours = Minecraft.getInstance().getItemColors();

        //Grass Colouring
        blockColours.register((state, world, pos, tintIndex) ->
                        world != null && pos != null ? BiomeColors.getGrassColor(world, pos) : GrassColors.get(0.5D, 1.0D),
                ModBlocks.SparseGrass_Block);

        //Item Colouring
        itemColours.register((stack, tintIndex) -> {
                    BlockState BlockState = ((BlockItem)stack.getItem()).getBlock().getDefaultState();
                    return blockColours.getColor(BlockState, null, null, tintIndex); },
                ModBlocks.SparseGrass_Block);
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
