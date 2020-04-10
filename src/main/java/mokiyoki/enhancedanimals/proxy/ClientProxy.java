package mokiyoki.enhancedanimals.proxy;

import mokiyoki.enhancedanimals.entity.*;
//import mokiyoki.enhancedanimals.gui.EncyclopediaScreen;
import mokiyoki.enhancedanimals.gui.EncyclopediaScreen;
import mokiyoki.enhancedanimals.init.ModBlocks;
import mokiyoki.enhancedanimals.renderer.*;
import mokiyoki.enhancedanimals.tileentity.EggCartonTileEntity;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.entity.EntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.world.GrassColors;
import net.minecraft.world.biome.BiomeColors;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;

/**
 * Created by moki on 24/08/2018.
 */
public class ClientProxy implements IProxy {
    public static EntityType<EnhancedChicken> enhancedChickenEntityType;
    public static EntityType<EnhancedRabbit> enhancedRabbitEntityType;
    public static EntityType<EnhancedSheep> enhancedSheepEntityType;
    public static EntityType<EnhancedLlama> enhancedLlamaEntityType;
    public static EntityType<EnhancedCow> enhancedCowEntityType;
    public static EntityType<EnhancedPig> enhancedPigEntityType;
    public static EntityType<EnhancedHorse> enhancedHorseEntityType;
    public static EntityType<EnhancedMooshroom> enhancedMooshroomEntityType;

    public static TileEntityType<EggCartonTileEntity> eggCartonTileEntityTileEntityType;

    @Override
    public void init(FMLCommonSetupEvent event) {
        RenderingRegistry.registerEntityRenderingHandler(enhancedChickenEntityType, manager -> new RenderEnhancedChicken(manager));
        RenderingRegistry.registerEntityRenderingHandler(enhancedRabbitEntityType, manager -> new RenderEnhancedRabbit(manager));
        RenderingRegistry.registerEntityRenderingHandler(enhancedSheepEntityType, manager -> new RenderEnhancedSheep(manager));
        RenderingRegistry.registerEntityRenderingHandler(enhancedLlamaEntityType, manager -> new RenderEnhancedLlama(manager));
        RenderingRegistry.registerEntityRenderingHandler(enhancedCowEntityType, manager -> new RenderEnhancedCow(manager));
        RenderingRegistry.registerEntityRenderingHandler(enhancedPigEntityType, manager -> new RenderEnhancedPig(manager));
        RenderingRegistry.registerEntityRenderingHandler(enhancedHorseEntityType, manager -> new RenderEnhancedHorse(manager));
        RenderingRegistry.registerEntityRenderingHandler(enhancedMooshroomEntityType, manager -> new RenderEnhancedMooshroom(manager));

        ClientRegistry.bindTileEntityRenderer(eggCartonTileEntityTileEntityType, manager -> new EggCartonTileEntityRenderer<>());
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
    public void setEncylopediaInfo(CompoundNBT geneticEncyclopediaNBT) {
//        EncyclopediaScreen.geneticEncyclopediaNBT = geneticEncyclopediaNBT;
    }

    @Override
    public void openEncyclodepia() {
        DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> Minecraft.getInstance().displayGuiScreen(EncyclopediaScreen.currentEncyclopedia));
    }
}
