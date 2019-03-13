package mokiyoki.enhancedanimals.util.handlers;


import mokiyoki.enhancedanimals.entity.EnhancedChicken;
import mokiyoki.enhancedanimals.entity.EnhancedEntityEgg;
import mokiyoki.enhancedanimals.entity.EnhancedRabbit;
import mokiyoki.enhancedanimals.entity.EnhancedSheep;
import mokiyoki.enhancedanimals.entity.EnhancedLlama;
import mokiyoki.enhancedanimals.init.ModBlocks;
import mokiyoki.enhancedanimals.init.ModItems;
import mokiyoki.enhancedanimals.util.Reference;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemSpawnEgg;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * Created by moki on 24/08/2018.
 */
@Mod.EventBusSubscriber(bus= Mod.EventBusSubscriber.Bus.MOD)
public class RegistryHandler {

    public static final EntityType<EnhancedEntityEgg> ENHANCED_ENTITY_EGG_ENTITY_TYPE = EntityType.Builder.create(EnhancedEntityEgg.class, EnhancedEntityEgg::new).tracker(54,1,true).build(Reference.MODID + ":enhanced_entity_egg");
    public static final EntityType<EnhancedChicken> ENHANCED_CHICKEN = EntityType.Builder.create(EnhancedChicken.class, EnhancedChicken::new).tracker(64, 1, true).build(Reference.MODID + ":enhanced_chicken");
    public static final EntityType<EnhancedRabbit> ENHANCED_RABBIT = EntityType.Builder.create(EnhancedRabbit.class, EnhancedRabbit::new).tracker(64, 1, true).build(Reference.MODID + ":enhanced_rabbit");
    public static final EntityType<EnhancedSheep> ENHANCED_SHEEP = EntityType.Builder.create(EnhancedSheep.class, EnhancedSheep::new).tracker(64, 1, true).build(Reference.MODID + ":enhanced_sheep");
    public static final EntityType<EnhancedLlama> ENHANCED_LLAMA = EntityType.Builder.create(EnhancedLlama.class, EnhancedLlama::new).tracker(64, 1, true).build(Reference.MODID + ":enhanced_llama");


    @SubscribeEvent
    public static void onRegisterBlocks(final RegistryEvent.Register<Block> event) {
        final Block[] blocks = {ModBlocks.Post_Acacia, ModBlocks.Post_Birch, ModBlocks.Post_Dark_Oak, ModBlocks.Post_Jungle, ModBlocks.Post_Oak, ModBlocks.Post_Spruce
        };
            event.getRegistry().registerAll(blocks);
    }

    @SubscribeEvent
    public static void onRegisterItems(final RegistryEvent.Register<Item> event) {
        final Item[] items = {ModItems.Egg_White, ModItems.Egg_Cream, ModItems.Egg_CreamDark, ModItems.Egg_Pink, ModItems.Egg_PinkDark, ModItems.Egg_Brown, ModItems.Egg_BrownDark,
                ModItems.Egg_Blue, ModItems.Egg_GreenLight, ModItems.Egg_Green, ModItems.Egg_Grey, ModItems.Egg_GreyGreen, ModItems.Egg_Olive, ModItems.Egg_GreenDark,
                ModItems.RawChicken_DarkSmall, ModItems.RawChicken_Dark, ModItems.RawChicken_DarkBig, ModItems.CookedChicken_DarkSmall, ModItems.CookedChicken_Dark,
                ModItems.CookedChicken_DarkBig, ModItems.RawChicken_PaleSmall, ModItems.RawChicken_Pale, ModItems.CookedChicken_PaleSmall, ModItems.CookedChicken_Pale};

        final Item[] itemBlocks = {
                new ItemBlock(ModBlocks.Post_Acacia, new Item.Properties().group(ItemGroup.BUILDING_BLOCKS)).setRegistryName(ModBlocks.Post_Acacia.getRegistryName()),
                new ItemBlock(ModBlocks.Post_Birch, new Item.Properties().group(ItemGroup.BUILDING_BLOCKS)).setRegistryName(ModBlocks.Post_Birch.getRegistryName()),
                new ItemBlock(ModBlocks.Post_Dark_Oak, new Item.Properties().group(ItemGroup.BUILDING_BLOCKS)).setRegistryName(ModBlocks.Post_Dark_Oak.getRegistryName()),
                new ItemBlock(ModBlocks.Post_Jungle, new Item.Properties().group(ItemGroup.BUILDING_BLOCKS)).setRegistryName(ModBlocks.Post_Jungle.getRegistryName()),
                new ItemBlock(ModBlocks.Post_Oak, new Item.Properties().group(ItemGroup.BUILDING_BLOCKS)).setRegistryName(ModBlocks.Post_Oak.getRegistryName()),
                new ItemBlock(ModBlocks.Post_Spruce, new Item.Properties().group(ItemGroup.BUILDING_BLOCKS)).setRegistryName(ModBlocks.Post_Spruce.getRegistryName()),
        };


        event.getRegistry().register(new ItemSpawnEgg(ENHANCED_CHICKEN, 0,1, new Item.Properties()
                .group(ItemGroup.MISC)).setRegistryName("enhanced_chicken_spawn_egg"));

        event.getRegistry().registerAll(items);
        event.getRegistry().registerAll(itemBlocks);

//        OreDictionary.registerOre("egg", ModItems.Egg_White);
//        OreDictionary.registerOre("egg", ModItems.Egg_Cream);
//        OreDictionary.registerOre("egg", ModItems.Egg_CreamDark);
//        OreDictionary.registerOre("egg", ModItems.Egg_Pink);
//        OreDictionary.registerOre("egg", ModItems.Egg_PinkDark);
//        OreDictionary.registerOre("egg", ModItems.Egg_Brown);
//        OreDictionary.registerOre("egg", ModItems.Egg_BrownDark);
//        OreDictionary.registerOre("egg", ModItems.Egg_Blue);
//        OreDictionary.registerOre("egg", ModItems.Egg_GreenLight);
//        OreDictionary.registerOre("egg", ModItems.Egg_Green);
//        OreDictionary.registerOre("egg", ModItems.Egg_Grey);
//        OreDictionary.registerOre("egg", ModItems.Egg_GreyGreen);
//        OreDictionary.registerOre("egg", ModItems.Egg_Olive);
//        OreDictionary.registerOre("egg", ModItems.Egg_GreenDark);

    }

    @SubscribeEvent
    public static void onEntitiesRegistry(final RegistryEvent.Register<EntityType<?>> event) {
        event.getRegistry().register(ENHANCED_CHICKEN.setRegistryName("enhanced_chicken"));
        event.getRegistry().register(ENHANCED_RABBIT.setRegistryName("enhanced_rabbit"));
        event.getRegistry().register(ENHANCED_SHEEP.setRegistryName("enhanced_sheep"));
        event.getRegistry().register(ENHANCED_LLAMA.setRegistryName("enhanced_llama"));
        event.getRegistry().register(ENHANCED_ENTITY_EGG_ENTITY_TYPE.setRegistryName("enhanced_entity_egg"));
    }

}
