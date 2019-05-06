package mokiyoki.enhancedanimals.util.handlers;


import mokiyoki.enhancedanimals.entity.*;
import mokiyoki.enhancedanimals.init.ModBlocks;
import mokiyoki.enhancedanimals.init.ModItems;
import mokiyoki.enhancedanimals.util.Reference;
import net.minecraft.block.Block;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Biomes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemSpawnEgg;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.Heightmap;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Set;

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
    public static final EntityType<EnhancedCow> ENHANCED_COW = EntityType.Builder.create(EnhancedCow.class, EnhancedCow::new).tracker(64, 1, true).build(Reference.MODID + ":enhanced_cow");


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
                ModItems.CookedChicken_DarkBig, ModItems.RawChicken_PaleSmall, ModItems.RawChicken_Pale, ModItems.CookedChicken_PaleSmall, ModItems.CookedChicken_Pale,
                ModItems.RawRabbit_Small, ModItems.CookedRabbit_Small, ModItems.RabbitStew_Weak, ModItems.Debug_Gene_Book};

        final Item[] itemBlocks = {
                new ItemBlock(ModBlocks.Post_Acacia, new Item.Properties().group(ItemGroup.BUILDING_BLOCKS)).setRegistryName(ModBlocks.Post_Acacia.getRegistryName()),
                new ItemBlock(ModBlocks.Post_Birch, new Item.Properties().group(ItemGroup.BUILDING_BLOCKS)).setRegistryName(ModBlocks.Post_Birch.getRegistryName()),
                new ItemBlock(ModBlocks.Post_Dark_Oak, new Item.Properties().group(ItemGroup.BUILDING_BLOCKS)).setRegistryName(ModBlocks.Post_Dark_Oak.getRegistryName()),
                new ItemBlock(ModBlocks.Post_Jungle, new Item.Properties().group(ItemGroup.BUILDING_BLOCKS)).setRegistryName(ModBlocks.Post_Jungle.getRegistryName()),
                new ItemBlock(ModBlocks.Post_Oak, new Item.Properties().group(ItemGroup.BUILDING_BLOCKS)).setRegistryName(ModBlocks.Post_Oak.getRegistryName()),
                new ItemBlock(ModBlocks.Post_Spruce, new Item.Properties().group(ItemGroup.BUILDING_BLOCKS)).setRegistryName(ModBlocks.Post_Spruce.getRegistryName()),
        };


        event.getRegistry().register(new ItemSpawnEgg(ENHANCED_CHICKEN, 0xFFFCF0,0xCC0000, new Item.Properties()
                .group(ItemGroup.MISC)).setRegistryName("enhanced_chicken_spawn_egg"));
        event.getRegistry().register(new ItemSpawnEgg(ENHANCED_LLAMA, 0xCDB29C,0x7B4B34, new Item.Properties()
                .group(ItemGroup.MISC)).setRegistryName("enhanced_llama_spawn_egg"));
        event.getRegistry().register(new ItemSpawnEgg(ENHANCED_SHEEP, 0xFFFFFF,0xFF8C8C, new Item.Properties()
                .group(ItemGroup.MISC)).setRegistryName("enhanced_sheep_spawn_egg"));
        event.getRegistry().register(new ItemSpawnEgg(ENHANCED_RABBIT, 0xCA8349,0x553C36, new Item.Properties()
                .group(ItemGroup.MISC)).setRegistryName("enhanced_rabbit_spawn_egg"));
        event.getRegistry().register(new ItemSpawnEgg(ENHANCED_COW, 0x000000,0x000000, new Item.Properties()
                .group(ItemGroup.MISC)).setRegistryName("enhanced_cow_spawn_egg"));


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
        event.getRegistry().register(ENHANCED_COW.setRegistryName("enhanced_cow"));
        event.getRegistry().register(ENHANCED_ENTITY_EGG_ENTITY_TYPE.setRegistryName("enhanced_entity_egg"));

        EntitySpawnPlacementRegistry.register(ENHANCED_CHICKEN, EntitySpawnPlacementRegistry.SpawnPlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, null);
        EntitySpawnPlacementRegistry.register(ENHANCED_RABBIT, EntitySpawnPlacementRegistry.SpawnPlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, null);
        EntitySpawnPlacementRegistry.register(ENHANCED_SHEEP, EntitySpawnPlacementRegistry.SpawnPlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, null);
        EntitySpawnPlacementRegistry.register(ENHANCED_LLAMA, EntitySpawnPlacementRegistry.SpawnPlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, null);
//        EntitySpawnPlacementRegistry.register(ENHANCED_COW, EntitySpawnPlacementRegistry.SpawnPlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, null);

        for (Biome biome : ForgeRegistries.BIOMES) {

            Set<BiomeDictionary.Type> types = BiomeDictionary.getTypes(biome);

            //Enhanced Chicken Spawning
            if (!biome.getRegistryName().equals(Biomes.OCEAN.getRegistryName()) && !biome.getRegistryName().equals(Biomes.LUKEWARM_OCEAN.getRegistryName()) && !biome.getRegistryName().equals(Biomes.DEEP_LUKEWARM_OCEAN.getRegistryName()) && !biome.getRegistryName().equals(Biomes.DEEP_OCEAN.getRegistryName()) && !biome.getRegistryName().equals(Biomes.DEEP_LUKEWARM_OCEAN.getRegistryName()) &&  !biome.getRegistryName().equals(Biomes.DEEP_COLD_OCEAN.getRegistryName()) &&  !biome.getRegistryName().equals(Biomes.DEEP_FROZEN_OCEAN.getRegistryName()) &&
                    !biome.getRegistryName().equals(Biomes.DESERT.getRegistryName()) && !biome.getRegistryName().equals(Biomes.DESERT_HILLS.getRegistryName()) && !biome.getRegistryName().equals(Biomes.DESERT_LAKES.getRegistryName()) &&
                    !biome.getRegistryName().equals(Biomes.THE_END.getRegistryName()) && !biome.getRegistryName().equals(Biomes.NETHER.getRegistryName())) {
                biome.getSpawns(EnumCreatureType.CREATURE).add(new Biome.SpawnListEntry(ENHANCED_CHICKEN, 10, 4, 4));
            }

            //Enhanced Rabbit Spawning
            if (biome.getRegistryName().equals(Biomes.SNOWY_TUNDRA.getRegistryName()) || biome.getRegistryName().equals(Biomes.ICE_SPIKES.getRegistryName()) || biome.getRegistryName().equals(Biomes.SNOWY_MOUNTAINS.getRegistryName()) || biome.getRegistryName().equals(Biomes.SNOWY_TAIGA.getRegistryName()) || biome.getRegistryName().equals(Biomes.SNOWY_TAIGA_HILLS.getRegistryName()) || biome.getRegistryName().equals(Biomes.SNOWY_TAIGA_MOUNTAINS.getRegistryName()) || biome.getRegistryName().equals(Biomes.TAIGA.getRegistryName()) || biome.getRegistryName().equals(Biomes.TAIGA_HILLS.getRegistryName()) || biome.getRegistryName().equals(Biomes.TAIGA_MOUNTAINS.getRegistryName()) || biome.getRegistryName().equals(Biomes.GIANT_TREE_TAIGA.getRegistryName()) || biome.getRegistryName().equals(Biomes.GIANT_TREE_TAIGA_HILLS.getRegistryName()) ||
                    biome.getRegistryName().equals(Biomes.FLOWER_FOREST.getRegistryName()) ||
                    biome.getRegistryName().equals(Biomes.DESERT.getRegistryName()) || biome.getRegistryName().equals(Biomes.DESERT_LAKES.getRegistryName()) || biome.getRegistryName().equals(Biomes.DESERT_HILLS.getRegistryName()) ) {
                biome.getSpawns(EnumCreatureType.CREATURE).add(new Biome.SpawnListEntry(ENHANCED_RABBIT, 10, 2, 3));
            }

            //Enhanced Llama Spawning
            if (biome.getRegistryName().equals(Biomes.MOUNTAINS.getRegistryName()) || biome.getRegistryName().equals(Biomes.TAIGA_MOUNTAINS.getRegistryName()) ||
                    biome.getRegistryName().equals(Biomes.SAVANNA.getRegistryName()) || biome.getRegistryName().equals(Biomes.SHATTERED_SAVANNA.getRegistryName()) || biome.getRegistryName().equals(Biomes.SAVANNA_PLATEAU.getRegistryName()) || biome.getRegistryName().equals(Biomes.SHATTERED_SAVANNA_PLATEAU.getRegistryName()) ) {
                biome.getSpawns(EnumCreatureType.CREATURE).add(new Biome.SpawnListEntry(ENHANCED_LLAMA, 5, 4, 6));
            }

            //Enhanced Sheep Spawning
            if (!biome.getRegistryName().equals(Biomes.OCEAN.getRegistryName()) && !biome.getRegistryName().equals(Biomes.LUKEWARM_OCEAN.getRegistryName()) && !biome.getRegistryName().equals(Biomes.DEEP_LUKEWARM_OCEAN.getRegistryName()) && !biome.getRegistryName().equals(Biomes.DEEP_OCEAN.getRegistryName()) && !biome.getRegistryName().equals(Biomes.DEEP_LUKEWARM_OCEAN.getRegistryName()) &&  !biome.getRegistryName().equals(Biomes.DEEP_COLD_OCEAN.getRegistryName()) &&  !biome.getRegistryName().equals(Biomes.DEEP_FROZEN_OCEAN.getRegistryName()) &&
                    !biome.getRegistryName().equals(Biomes.DESERT.getRegistryName()) && !biome.getRegistryName().equals(Biomes.DESERT_HILLS.getRegistryName()) && !biome.getRegistryName().equals(Biomes.DESERT_LAKES.getRegistryName()) &&
                    !biome.getRegistryName().equals(Biomes.THE_END.getRegistryName()) && !biome.getRegistryName().equals(Biomes.NETHER.getRegistryName())){
                biome.getSpawns(EnumCreatureType.CREATURE).add(new Biome.SpawnListEntry(ENHANCED_SHEEP, 5, 4, 6));
            }

        }
    }

}
