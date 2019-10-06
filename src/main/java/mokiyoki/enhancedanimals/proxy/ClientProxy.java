package mokiyoki.enhancedanimals.proxy;

import mokiyoki.enhancedanimals.entity.*;
import mokiyoki.enhancedanimals.gui.EncyclopediaScreen;
import mokiyoki.enhancedanimals.init.ModBlocks;
import mokiyoki.enhancedanimals.renderer.*;
import mokiyoki.enhancedanimals.tileentity.EggCartonTileEntity;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.GrassColors;
import net.minecraft.world.biome.BiomeColors;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;

/**
 * Created by moki on 24/08/2018.
 */
public class ClientProxy implements IProxy {


    @Override
    public void init(FMLCommonSetupEvent event) {
        RenderingRegistry.registerEntityRenderingHandler(EnhancedChicken.class, manager -> new RenderEnhancedChicken(manager));
        RenderingRegistry.registerEntityRenderingHandler(EnhancedRabbit.class, manager -> new RenderEnhancedRabbit(manager));
        RenderingRegistry.registerEntityRenderingHandler(EnhancedSheep.class, manager -> new RenderEnhancedSheep(manager));
        RenderingRegistry.registerEntityRenderingHandler(EnhancedLlama.class, manager -> new RenderEnhancedLlama(manager));
        RenderingRegistry.registerEntityRenderingHandler(EnhancedCow.class, manager -> new RenderEnhancedCow(manager));
        RenderingRegistry.registerEntityRenderingHandler(EnhancedPig.class, manager -> new RenderEnhancedPig(manager));
        RenderingRegistry.registerEntityRenderingHandler(EnhancedHorse.class, manager -> new RenderEnhancedHorse(manager));
        RenderingRegistry.registerEntityRenderingHandler(EnhancedMooshroom.class, manager -> new RenderEnhancedMooshroom(manager));

        ClientRegistry.bindTileEntitySpecialRenderer(EggCartonTileEntity.class, new EggCartonTileEntityRenderer<>());
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
}
