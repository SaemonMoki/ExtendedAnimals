package mokiyoki.enhancedanimals.util.handlers;

import mokiyoki.enhancedanimals.init.ModBlocks;
import mokiyoki.enhancedanimals.init.ModItems;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@EventBusSubscriber(Side.CLIENT)
public class ModelRegistryHandler {

    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {
        registerModel(ModItems.EggWhite);
        registerModel(ModItems.EggCream);
        registerModel(ModItems.EggCreamDark);
        registerModel(ModItems.EggPink);
        registerModel(ModItems.EggPinkDark);
        registerModel(ModItems.EggBrown);
        registerModel(ModItems.EggBrownDark);
        registerModel(ModItems.EggBlue);
        registerModel(ModItems.EggGreenLight);
        registerModel(ModItems.EggGreen);
        registerModel(ModItems.EggGrey);
        registerModel(ModItems.EggGreyGreen);
        registerModel(ModItems.EggOlive);
        registerModel(ModItems.EggGreenDark);
        registerModel(ModItems.RawChickenDark);
        registerModel(ModItems.CookedChickenDark);

        registerModel(Item.getItemFromBlock(ModBlocks.PostAcacia));
        registerModel(Item.getItemFromBlock(ModBlocks.PostBirch));
        registerModel(Item.getItemFromBlock(ModBlocks.PostDarkOak));
        registerModel(Item.getItemFromBlock(ModBlocks.PostJungle));
        registerModel(Item.getItemFromBlock(ModBlocks.PostOak));
        registerModel(Item.getItemFromBlock(ModBlocks.PostSpruce));

    }

    private static void registerModel(Item item) {
        ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
    }

}

