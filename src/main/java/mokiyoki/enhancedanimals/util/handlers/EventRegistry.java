package mokiyoki.enhancedanimals.util.handlers;


import mokiyoki.enhancedanimals.EnhancedAnimals;
import mokiyoki.enhancedanimals.blocks.EggCartonContainer;
import mokiyoki.enhancedanimals.capability.egg.EggCapabilityProvider;
//import mokiyoki.enhancedanimals.capability.woolcolour.WoolColourCapabilityProvider;
import mokiyoki.enhancedanimals.entity.EnhancedChicken;
import mokiyoki.enhancedanimals.entity.EnhancedEntityEgg;
import mokiyoki.enhancedanimals.entity.EnhancedEntityLlamaSpit;
import mokiyoki.enhancedanimals.entity.EnhancedHorse;
import mokiyoki.enhancedanimals.entity.EnhancedMooshroom;
import mokiyoki.enhancedanimals.entity.EnhancedRabbit;
import mokiyoki.enhancedanimals.entity.EnhancedLlama;
import mokiyoki.enhancedanimals.entity.EnhancedCow;
import mokiyoki.enhancedanimals.entity.EnhancedPig;
import mokiyoki.enhancedanimals.entity.EnhancedSheep;
import mokiyoki.enhancedanimals.init.ModBlocks;
import mokiyoki.enhancedanimals.init.ModItems;
import mokiyoki.enhancedanimals.init.ModTileEntities;
import mokiyoki.enhancedanimals.util.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.DispenserBlock;
import net.minecraft.dispenser.IPosition;
import net.minecraft.dispenser.ProjectileDispenseBehavior;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.IProjectile;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.*;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.registries.ForgeRegistries;

//import static mokiyoki.enhancedanimals.capability.woolcolour.WoolColourCapabilityProvider.WOOL_COLOUR_CAP;

/**
 * Created by moki on 24/08/2018.
 */
@Mod.EventBusSubscriber(bus= Mod.EventBusSubscriber.Bus.MOD)
public class EventRegistry {

    public static final EntityType<EnhancedEntityLlamaSpit> ENHANCED_LLAMA_SPIT = EntityType.Builder.<EnhancedEntityLlamaSpit>create(EnhancedEntityLlamaSpit::new, EntityClassification.MISC).size(0.25F, 0.25F).build(Reference.MODID + ":enhanced_entity_llama_spit");
    public static final EntityType<EnhancedEntityEgg> ENHANCED_ENTITY_EGG_ENTITY_TYPE = EntityType.Builder.<EnhancedEntityEgg>create(EnhancedEntityEgg::new, EntityClassification.MISC).size(0.25F, 0.25F).build(Reference.MODID + ":enhanced_entity_egg");
    public static final EntityType<EnhancedChicken> ENHANCED_CHICKEN = EntityType.Builder.create(EnhancedChicken::new, EntityClassification.CREATURE).size(0.4F, 0.7F).setTrackingRange(64).setUpdateInterval(1).build(Reference.MODID + ":enhanced_chicken");
    public static final EntityType<EnhancedRabbit> ENHANCED_RABBIT = EntityType.Builder.create(EnhancedRabbit::new, EntityClassification.CREATURE).size(0.4F, 0.5F).build(Reference.MODID + ":enhanced_rabbit");
    public static final EntityType<EnhancedSheep> ENHANCED_SHEEP = EntityType.Builder.create(EnhancedSheep::new, EntityClassification.CREATURE).size(0.4F, 1F).build(Reference.MODID + ":enhanced_sheep");
    public static final EntityType<EnhancedLlama> ENHANCED_LLAMA = EntityType.Builder.create(EnhancedLlama::new, EntityClassification.CREATURE).size(0.9F, 1.87F).build(Reference.MODID + ":enhanced_llama");
    public static final EntityType<EnhancedCow> ENHANCED_COW = EntityType.Builder.create(EnhancedCow::new, EntityClassification.CREATURE).size(0.4F, 1F).build(Reference.MODID + ":enhanced_cow");
    public static final EntityType<EnhancedMooshroom> ENHANCED_MOOSHROOM = EntityType.Builder.create(EnhancedMooshroom::new, EntityClassification.CREATURE).size(0.4F, 1F).build(Reference.MODID + ":enhanced_mooshroom");
    public static final EntityType<EnhancedPig> ENHANCED_PIG = EntityType.Builder.create(EnhancedPig::new, EntityClassification.CREATURE).size(0.9F, 0.9F).build(Reference.MODID + ":enhanced_pig");
    public static final EntityType<EnhancedHorse> ENHANCED_HORSE = EntityType.Builder.create(EnhancedHorse::new, EntityClassification.CREATURE).size(0.9F, 0.9F).build(Reference.MODID + ":enhanced_horse");

    public static final ContainerType<EggCartonContainer> EGG_CARTON_CONTAINER = IForgeContainerType.create(EggCartonContainer::new);


    @SubscribeEvent
    public static void onRegisterBlocks(final RegistryEvent.Register<Block> event) {
        final Block[] blocks = {ModBlocks.Post_Acacia, ModBlocks.Post_Birch, ModBlocks.Post_Dark_Oak, ModBlocks.Post_Jungle, ModBlocks.Post_Oak, ModBlocks.Post_Spruce, ModBlocks.UnboundHay_Block, ModBlocks.SparseGrass_Block, ModBlocks.Egg_Carton
        };
            event.getRegistry().registerAll(blocks);
    }

    @SubscribeEvent
    public static void onRegisterItems(final RegistryEvent.Register<Item> event) {
        final Item[] itemEggs = {ModItems.Egg_White, ModItems.Egg_Cream, ModItems.Egg_CreamDark, ModItems.Egg_Pink, ModItems.Egg_PinkDark, ModItems.Egg_Brown, ModItems.Egg_BrownDark,
                ModItems.Egg_Blue, ModItems.Egg_GreenLight, ModItems.Egg_Green, ModItems.Egg_Grey, ModItems.Egg_GreyGreen, ModItems.Egg_Olive, ModItems.Egg_GreenDark};

        final Item[] items = {ModItems.RawChicken_DarkSmall, ModItems.RawChicken_Dark, ModItems.RawChicken_DarkBig, ModItems.CookedChicken_DarkSmall, ModItems.CookedChicken_Dark,
                ModItems.CookedChicken_DarkBig, ModItems.RawChicken_PaleSmall, ModItems.RawChicken_Pale, ModItems.CookedChicken_PaleSmall, ModItems.CookedChicken_Pale,
                ModItems.RawRabbit_Small, ModItems.CookedRabbit_Small, ModItems.RabbitStew_Weak, ModItems.Half_Milk_Bottle, ModItems.Milk_Bottle, ModItems.OneSixth_Milk_Bucket,
                ModItems.OneThird_Milk_Bucket, ModItems.Half_Milk_Bucket, ModItems.TwoThirds_Milk_Bucket, ModItems.FiveSixths_Milk_Bucket, ModItems.Debug_Gene_Book};

        final Item[] itemBlocks = {
                new BlockItem(ModBlocks.Post_Acacia, new Item.Properties().group(EnhancedAnimals.GENETICS_ANIMALS_GROUP)).setRegistryName(ModBlocks.Post_Acacia.getRegistryName()),
                new BlockItem(ModBlocks.Post_Birch, new Item.Properties().group(EnhancedAnimals.GENETICS_ANIMALS_GROUP)).setRegistryName(ModBlocks.Post_Birch.getRegistryName()),
                new BlockItem(ModBlocks.Post_Dark_Oak, new Item.Properties().group(EnhancedAnimals.GENETICS_ANIMALS_GROUP)).setRegistryName(ModBlocks.Post_Dark_Oak.getRegistryName()),
                new BlockItem(ModBlocks.Post_Jungle, new Item.Properties().group(EnhancedAnimals.GENETICS_ANIMALS_GROUP)).setRegistryName(ModBlocks.Post_Jungle.getRegistryName()),
                new BlockItem(ModBlocks.Post_Oak, new Item.Properties().group(EnhancedAnimals.GENETICS_ANIMALS_GROUP)).setRegistryName(ModBlocks.Post_Oak.getRegistryName()),
                new BlockItem(ModBlocks.Post_Spruce, new Item.Properties().group(EnhancedAnimals.GENETICS_ANIMALS_GROUP)).setRegistryName(ModBlocks.Post_Spruce.getRegistryName()),
                new BlockItem(ModBlocks.UnboundHay_Block, new Item.Properties().group(EnhancedAnimals.GENETICS_ANIMALS_GROUP)).setRegistryName(ModBlocks.UnboundHay_Block.getRegistryName()),
                new BlockItem(ModBlocks.SparseGrass_Block, new Item.Properties().group(EnhancedAnimals.GENETICS_ANIMALS_GROUP)).setRegistryName(ModBlocks.SparseGrass_Block.getRegistryName()),
                new BlockItem(ModBlocks.Egg_Carton, new Item.Properties().maxStackSize(1).group(EnhancedAnimals.GENETICS_ANIMALS_GROUP)).setRegistryName(ModBlocks.Egg_Carton.getRegistryName()),
        };


        event.getRegistry().register(new SpawnEggItem(ENHANCED_CHICKEN, 0xFFFCF0,0xCC0000, new Item.Properties()
                .group(EnhancedAnimals.GENETICS_ANIMALS_GROUP)).setRegistryName("enhanced_chicken_spawn_egg"));
        event.getRegistry().register(new SpawnEggItem(ENHANCED_LLAMA, 0xCDB29C,0x7B4B34, new Item.Properties()
                .group(EnhancedAnimals.GENETICS_ANIMALS_GROUP)).setRegistryName("enhanced_llama_spawn_egg"));
        event.getRegistry().register(new SpawnEggItem(ENHANCED_SHEEP, 0xFFFFFF,0xFF8C8C, new Item.Properties()
                .group(EnhancedAnimals.GENETICS_ANIMALS_GROUP)).setRegistryName("enhanced_sheep_spawn_egg"));
        event.getRegistry().register(new SpawnEggItem(ENHANCED_RABBIT, 0xCA8349,0x553C36, new Item.Properties()
                .group(EnhancedAnimals.GENETICS_ANIMALS_GROUP)).setRegistryName("enhanced_rabbit_spawn_egg"));
        event.getRegistry().register(new SpawnEggItem(ENHANCED_COW, 0x260800,0xf9f9f7, new Item.Properties()
                .group(EnhancedAnimals.GENETICS_ANIMALS_GROUP)).setRegistryName("enhanced_cow_spawn_egg"));
        event.getRegistry().register(new SpawnEggItem(ENHANCED_MOOSHROOM, 0xFF0000,0xCCCCCC, new Item.Properties()
                .group(EnhancedAnimals.GENETICS_ANIMALS_GROUP)).setRegistryName("enhanced_mooshroom_spawn_egg"));
        event.getRegistry().register(new SpawnEggItem(ENHANCED_PIG, 0x000000,0x000000, new Item.Properties()
                .group(EnhancedAnimals.GENETICS_ANIMALS_GROUP)).setRegistryName("enhanced_pig_spawn_egg"));
        event.getRegistry().register(new SpawnEggItem(ENHANCED_HORSE, 0x000000,0x000000, new Item.Properties()
                .group(EnhancedAnimals.GENETICS_ANIMALS_GROUP)).setRegistryName("enhanced_horse_spawn_egg"));


        event.getRegistry().registerAll(itemEggs);
        event.getRegistry().registerAll(items);
        event.getRegistry().registerAll(itemBlocks);

        for (Item egg : itemEggs) {
            DispenserBlock.registerDispenseBehavior(egg,  new ProjectileDispenseBehavior() {
                /**
                 * Return the projectile entity spawned by this dispense behavior.
                 */
                protected IProjectile getProjectileEntity(World worldIn, IPosition position, ItemStack stackIn) {
                    EnhancedEntityEgg egg = new EnhancedEntityEgg(worldIn, position.getX(), position.getY(), position.getZ());
                    egg.setGenes(stackIn.getCapability(EggCapabilityProvider.EGG_CAP, null).orElse(null).getGenes());
                    return egg;
                }
            });
        }

        //TODO dispensers should be able to turn hay to unbound hay if they contain a sharp tool and are facing a hay block

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


//    @SubscribeEvent
//    public static void registerBlockColourHandlers(final ColorHandlerEvent.Block event) {
//        final BlockColors blockColors = event.getBlockColors();
//
//        blockColors.register(new SparseGrassBlockColour(), ModBlocks.SparseGrass_Block);
//    }


//    @SubscribeEvent
//    public static void registerItemColourHandlers(final ColorHandlerEvent.Item event) {
//        final ItemColors itemColors = event.getItemColors();
//
//        itemColors.register(new SparseGrassItemColour(), ModBlocks.SparseGrass_Block);
//    }


    @SubscribeEvent
    public static void onTileEntitiesRegistry(final RegistryEvent.Register<TileEntityType<?>> event) {
        event.getRegistry().register(ModTileEntities.EGG_CARTON_TILE_ENTITY.setRegistryName("egg_carton_tile_entity"));
    }

    @SubscribeEvent
    public static void onContainerTypeRegistry(final RegistryEvent.Register<ContainerType<?>> event) {
        event.getRegistry().register(EGG_CARTON_CONTAINER.setRegistryName("egg_carton_container_box"));
    }

//    @SubscribeEvent
//    public static void registerBlockColors(final ColorHandlerEvent.Block event)
//    {
//        final BlockColors blockColors = event.getBlockColors();


//        blockColors.register((p_210239_0_, p_210239_1_) -> {
//            return p_210239_1_ > 0 ? -1 : ((ItemArmorDyeable)p_210239_0_.getItem()).getColor(p_210239_0_);
//        }, Items.LEATHER_HELMET, Items.LEATHER_CHESTPLATE, Items.LEATHER_LEGGINGS, Items.LEATHER_BOOTS);

//        blockColors.register((p_210225_0_, p_210225_1_, p_210225_2_, p_210225_3_) ->
//        {
//            return p_210225_1_ != null && p_210225_2_ != null ? BiomeColors.getGrassColor(p_210225_1_, p_210225_2_) : GrassColors.get(0.5D, 1.0D);
//        }, MubbleBlocks.GREEN_HILL_GRASS_BLOCK);
//    }

//    @SubscribeEvent
//    public static void registerItemColors(final ColorHandlerEvent.Item event)
//    {
//        final ItemColors itemColors = event.getItemColors();
//
//        itemColors.register((itemStack, integer) -> {
//            int colour = itemStack.getCapability(WOOL_COLOUR_CAP).orElse(new WoolColourCapabilityProvider()).getColour();
//            return colour;
//        }, Blocks.WHITE_WOOL);
//    }


    @SubscribeEvent
    public static void onEntitiesRegistry(RegistryEvent.Register<EntityType<?>> event) {
        event.getRegistry().register(ENHANCED_CHICKEN.setRegistryName("enhanced_chicken"));
        event.getRegistry().register(ENHANCED_RABBIT.setRegistryName("enhanced_rabbit"));
        event.getRegistry().register(ENHANCED_SHEEP.setRegistryName("enhanced_sheep"));
        event.getRegistry().register(ENHANCED_LLAMA.setRegistryName("enhanced_llama"));
        event.getRegistry().register(ENHANCED_COW.setRegistryName("enhanced_cow"));
        event.getRegistry().register(ENHANCED_MOOSHROOM.setRegistryName("enhanced_mooshroom"));
        event.getRegistry().register(ENHANCED_PIG.setRegistryName("enhanced_pig"));
        event.getRegistry().register(ENHANCED_HORSE.setRegistryName("enhanced_horse"));
        event.getRegistry().register(ENHANCED_ENTITY_EGG_ENTITY_TYPE.setRegistryName("enhanced_entity_egg"));
        event.getRegistry().register(ENHANCED_LLAMA_SPIT.setRegistryName("enhanced_entity_llama_spit"));

//        EntitySpawnPlacementRegistry.register(ENHANCED_CHICKEN, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES);
//        EntitySpawnPlacementRegistry.register(ENHANCED_RABBIT, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES);
//        EntitySpawnPlacementRegistry.register(ENHANCED_SHEEP, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES);
//        EntitySpawnPlacementRegistry.register(ENHANCED_LLAMA, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES);
//        EntitySpawnPlacementRegistry.register(ENHANCED_COW, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES);
//        EntitySpawnPlacementRegistry.register(ENHANCED_PIG, EntitySpawnPlacementRegistry.SpawnPlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, null);
    }

    @SubscribeEvent
    public static void onLoadComplete(FMLLoadCompleteEvent event) {
//        if (Dimension.isSurfaceWorld()) {
            for (Biome biome : ForgeRegistries.BIOMES) {
//
                if (!biome.getRegistryName().equals(Biomes.OCEAN.getRegistryName()) && !biome.getRegistryName().equals(Biomes.LUKEWARM_OCEAN.getRegistryName()) && !biome.getRegistryName().equals(Biomes.DEEP_LUKEWARM_OCEAN.getRegistryName()) && !biome.getRegistryName().equals(Biomes.DEEP_OCEAN.getRegistryName()) && !biome.getRegistryName().equals(Biomes.DEEP_LUKEWARM_OCEAN.getRegistryName()) &&  !biome.getRegistryName().equals(Biomes.DEEP_COLD_OCEAN.getRegistryName()) &&  !biome.getRegistryName().equals(Biomes.DEEP_FROZEN_OCEAN.getRegistryName()) &&
                        !biome.getRegistryName().equals(Biomes.THE_END.getRegistryName()) && !biome.getRegistryName().equals(Biomes.SMALL_END_ISLANDS.getRegistryName()) && !biome.getRegistryName().equals(Biomes.END_BARRENS.getRegistryName()) && !biome.getRegistryName().equals(Biomes.END_HIGHLANDS.getRegistryName()) && !biome.getRegistryName().equals(Biomes.END_MIDLANDS.getRegistryName()) && !biome.getRegistryName().equals(Biomes.NETHER.getRegistryName()))
                {

                    if (!biome.getRegistryName().equals(Biomes.MUSHROOM_FIELDS.getRegistryName()) && !biome.getRegistryName().equals(Biomes.MUSHROOM_FIELD_SHORE.getRegistryName())){

                        if (!biome.getRegistryName().equals(Biomes.DESERT.getRegistryName()) && !biome.getRegistryName().equals(Biomes.DESERT_HILLS.getRegistryName()) && !biome.getRegistryName().equals(Biomes.DESERT_LAKES.getRegistryName()))
                        {
                            //Enhanced Pig Spawning
                            biome.getSpawns(EntityClassification.CREATURE).add(new Biome.SpawnListEntry(ENHANCED_PIG, 6, 2, 3));
                            //Enhanced Sheep Spawning
                            biome.getSpawns(EntityClassification.CREATURE).add(new Biome.SpawnListEntry(ENHANCED_SHEEP, 12, 4, 4));
                            //Enhanced Cow Spawning
                            biome.getSpawns(EntityClassification.CREATURE).add(new Biome.SpawnListEntry(ENHANCED_COW, 8, 4, 4));
                            //Enhanced Chicken Spawning
                            biome.getSpawns(EntityClassification.CREATURE).add(new Biome.SpawnListEntry(ENHANCED_CHICKEN, 10, 4, 4));
                            //Enhanced Llama Spawning
                            if (biome.getRegistryName().equals(Biomes.MOUNTAINS.getRegistryName()) || biome.getRegistryName().equals(Biomes.TAIGA_MOUNTAINS.getRegistryName()) ||
                                    biome.getRegistryName().equals(Biomes.SAVANNA.getRegistryName()) || biome.getRegistryName().equals(Biomes.SHATTERED_SAVANNA.getRegistryName()) || biome.getRegistryName().equals(Biomes.SAVANNA_PLATEAU.getRegistryName()) || biome.getRegistryName().equals(Biomes.SHATTERED_SAVANNA_PLATEAU.getRegistryName())
                            ) { biome.getSpawns(EntityClassification.CREATURE).add(new Biome.SpawnListEntry(ENHANCED_LLAMA, 4, 2, 3)); }
                            //Enhanced Rabbit Spawning
                            if (biome.getRegistryName().equals(Biomes.SNOWY_TUNDRA.getRegistryName()) || biome.getRegistryName().equals(Biomes.ICE_SPIKES.getRegistryName()) || biome.getRegistryName().equals(Biomes.SNOWY_MOUNTAINS.getRegistryName()) || biome.getRegistryName().equals(Biomes.SNOWY_TAIGA.getRegistryName()) || biome.getRegistryName().equals(Biomes.SNOWY_TAIGA_HILLS.getRegistryName()) || biome.getRegistryName().equals(Biomes.SNOWY_TAIGA_MOUNTAINS.getRegistryName()) || biome.getRegistryName().equals(Biomes.TAIGA.getRegistryName()) || biome.getRegistryName().equals(Biomes.TAIGA_HILLS.getRegistryName()) || biome.getRegistryName().equals(Biomes.TAIGA_MOUNTAINS.getRegistryName()) || biome.getRegistryName().equals(Biomes.GIANT_TREE_TAIGA.getRegistryName()) || biome.getRegistryName().equals(Biomes.GIANT_TREE_TAIGA_HILLS.getRegistryName()) ||
                                    biome.getRegistryName().equals(Biomes.FLOWER_FOREST.getRegistryName())
                            ) { biome.getSpawns(EntityClassification.CREATURE).add(new Biome.SpawnListEntry(ENHANCED_RABBIT, 4, 2, 3)); }
                        } else {
                            biome.getSpawns(EntityClassification.CREATURE).add(new Biome.SpawnListEntry(ENHANCED_RABBIT, 4, 2, 3));
                        }
                    }else {
                        //Enhanced Mooshroom Spawning
                        biome.getSpawns(EntityClassification.CREATURE).add(new Biome.SpawnListEntry(ENHANCED_MOOSHROOM, 8, 4, 4));
                    }


                }

            }
//        }

    }

}
