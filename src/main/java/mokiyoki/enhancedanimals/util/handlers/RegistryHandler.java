package mokiyoki.enhancedanimals.util.handlers;

import mokiyoki.enhancedanimals.blocks.BlockBase;
import mokiyoki.enhancedanimals.capability.post.PostCapabilityProvider;
import mokiyoki.enhancedanimals.entity.EnhancedChicken;
import mokiyoki.enhancedanimals.entity.EnhancedRabbit;
import mokiyoki.enhancedanimals.entity.EnhancedSheep;
import mokiyoki.enhancedanimals.init.ModBlocks;
import mokiyoki.enhancedanimals.init.ModItems;

import mokiyoki.enhancedanimals.util.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.RegistryEvent.Register;

import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;
import net.minecraftforge.oredict.OreDictionary;

/**
 * Created by moki on 24/08/2018.
 */
@EventBusSubscriber
public class RegistryHandler {

    @SubscribeEvent
    public static void registerBlocks(Register<Block> event) {
        final Block[] blocks = {ModBlocks.PostAcacia,ModBlocks.PostBirch,ModBlocks.PostDarkOak,ModBlocks.PostJungle,ModBlocks.PostOak,ModBlocks.PostSpruce
        };

            event.getRegistry().registerAll(blocks);
    }

    @SubscribeEvent
    public static void registerItems(Register<Item> event) {
        final Item[] items = {ModItems.EggWhite,ModItems.EggCream,ModItems.EggCreamDark,ModItems.EggPink,ModItems.EggPinkDark,ModItems.EggBrown,ModItems.EggBrownDark,
                              ModItems.EggBlue,ModItems.EggGreenLight,ModItems.EggGreen,ModItems.EggGrey,ModItems.EggGreyGreen,ModItems.EggOlive,ModItems.EggGreenDark,
                              ModItems.RawChickenDarkSmall, ModItems.RawChickenDark, ModItems.RawChickenDarkBig, ModItems.CookedChickenDarkSmall, ModItems.CookedChickenDark,
                              ModItems.CookedChickenDarkBig, ModItems.RawChickenPaleSmall, ModItems.RawChickenPaleBig, ModItems.CookedChickenPaleSmall, ModItems.CookedChickenPaleBig};

        final Item[] itemBlocks = {
                new ItemBlock(ModBlocks.PostAcacia).setRegistryName(ModBlocks.PostAcacia.getRegistryName()),
                new ItemBlock(ModBlocks.PostBirch).setRegistryName(ModBlocks.PostBirch.getRegistryName()),
                new ItemBlock(ModBlocks.PostDarkOak).setRegistryName(ModBlocks.PostDarkOak.getRegistryName()),
                new ItemBlock(ModBlocks.PostJungle).setRegistryName(ModBlocks.PostJungle.getRegistryName()),
                new ItemBlock(ModBlocks.PostOak).setRegistryName(ModBlocks.PostOak.getRegistryName()),
                new ItemBlock(ModBlocks.PostSpruce).setRegistryName(ModBlocks.PostSpruce.getRegistryName()),
        };

        event.getRegistry().registerAll(items);
        event.getRegistry().registerAll(itemBlocks);

        OreDictionary.registerOre("egg", ModItems.EggWhite);
        OreDictionary.registerOre("egg", ModItems.EggCream);
        OreDictionary.registerOre("egg", ModItems.EggCreamDark);
        OreDictionary.registerOre("egg", ModItems.EggPink);
        OreDictionary.registerOre("egg", ModItems.EggPinkDark);
        OreDictionary.registerOre("egg", ModItems.EggBrown);
        OreDictionary.registerOre("egg", ModItems.EggBrownDark);
        OreDictionary.registerOre("egg", ModItems.EggBlue);
        OreDictionary.registerOre("egg", ModItems.EggGreenLight);
        OreDictionary.registerOre("egg", ModItems.EggGreen);
        OreDictionary.registerOre("egg", ModItems.EggGrey);
        OreDictionary.registerOre("egg", ModItems.EggGreyGreen);
        OreDictionary.registerOre("egg", ModItems.EggOlive);
        OreDictionary.registerOre("egg", ModItems.EggGreenDark);

    }

//    @SubscribeEvent
//    public static void onBlockRegister(RegistryEvent.Register<Block> event){
//        event.getRegistry().registerAll(ModBlocks.BLOCKS.toArray(new Block[0]));
//    }

    @SubscribeEvent
    public static void onEntitiesRegistry(RegistryEvent.Register<EntityEntry> event)
    {
        event.getRegistry().register(
                EntityEntryBuilder.create().entity(EnhancedChicken.class).name("enhanced_chicken").id(Reference.MODID + ":enhanced_chicken", Reference.ENHANCED_CHICKEN).tracker(64, 1, true).egg(0,1).build());
        event.getRegistry().register(
                EntityEntryBuilder.create().entity(EnhancedRabbit.class).name("enhanced_rabbit").id(Reference.MODID + ":enhanced_rabbit", Reference.ENHANCED_RABBIT).tracker(64, 1, true).egg(111111,1).build());
        event.getRegistry().register(
                EntityEntryBuilder.create().entity(EnhancedSheep.class).name("enhanced_sheep").id(Reference.MODID + ":enhanced_sheep", Reference.ENHANCED_SHEEP).tracker(64, 1, true).egg(1,0).build());
    }

}
