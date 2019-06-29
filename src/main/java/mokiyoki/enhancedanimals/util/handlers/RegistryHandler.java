package mokiyoki.enhancedanimals.util.handlers;


import mokiyoki.enhancedanimals.capability.egg.EggCapabilityProvider;
//import mokiyoki.enhancedanimals.capability.woolcolour.WoolColourCapabilityProvider;
import mokiyoki.enhancedanimals.entity.EnhancedChicken;
import mokiyoki.enhancedanimals.entity.EnhancedEntityEgg;
import mokiyoki.enhancedanimals.entity.EnhancedEntityLlamaSpit;
import mokiyoki.enhancedanimals.entity.EnhancedRabbit;
import mokiyoki.enhancedanimals.entity.EnhancedSheep;
import mokiyoki.enhancedanimals.entity.EnhancedLlama;
import mokiyoki.enhancedanimals.entity.EnhancedCow;
import mokiyoki.enhancedanimals.entity.EnhancedPig;
import mokiyoki.enhancedanimals.init.ModBlocks;
import mokiyoki.enhancedanimals.init.ModItems;
import mokiyoki.enhancedanimals.items.EnhancedEgg;
import mokiyoki.enhancedanimals.util.Reference;
import net.minecraft.block.Block;
//import net.minecraft.client.renderer.color.BlockColors;
//import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.block.DispenserBlock;
import net.minecraft.dispenser.IPosition;
import net.minecraft.dispenser.ProjectileDispenseBehavior;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.IProjectile;
import net.minecraft.item.*;
import net.minecraft.world.GrassColors;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
//import net.minecraft.world.biome.BiomeColors;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.gen.Heightmap;
//import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Set;

//import static mokiyoki.enhancedanimals.capability.woolcolour.WoolColourCapabilityProvider.WOOL_COLOUR_CAP;

/**
 * Created by moki on 24/08/2018.
 */
@Mod.EventBusSubscriber(bus= Mod.EventBusSubscriber.Bus.MOD)
public class RegistryHandler {

    public static final EntityType<EnhancedEntityLlamaSpit> ENHANCED_LLAMA_SPIT = EntityType.Builder.<EnhancedEntityLlamaSpit>create(EnhancedEntityLlamaSpit::new, EntityClassification.MISC).size(0.25F, 0.25F).build(Reference.MODID + ":enhanced_entity_llama_spit");
    public static final EntityType<EnhancedEntityEgg> ENHANCED_ENTITY_EGG_ENTITY_TYPE = EntityType.Builder.<EnhancedEntityEgg>create(EnhancedEntityEgg::new, EntityClassification.MISC).size(0.25F, 0.25F).build(Reference.MODID + ":enhanced_entity_egg");
    public static final EntityType<EnhancedChicken> ENHANCED_CHICKEN = EntityType.Builder.create(EnhancedChicken::new, EntityClassification.CREATURE).size(0.4F, 0.7F).build(Reference.MODID + ":enhanced_chicken");
    public static final EntityType<EnhancedRabbit> ENHANCED_RABBIT = EntityType.Builder.create(EnhancedRabbit::new, EntityClassification.CREATURE).size(0.4F, 0.5F).build(Reference.MODID + ":enhanced_rabbit");
    public static final EntityType<EnhancedSheep> ENHANCED_SHEEP = EntityType.Builder.create(EnhancedSheep::new, EntityClassification.CREATURE).size(0.4F, 1F).build(Reference.MODID + ":enhanced_sheep");
    public static final EntityType<EnhancedLlama> ENHANCED_LLAMA = EntityType.Builder.create(EnhancedLlama::new, EntityClassification.CREATURE).size(0.9F, 1.87F).build(Reference.MODID + ":enhanced_llama");
    public static final EntityType<EnhancedCow> ENHANCED_COW = EntityType.Builder.create(EnhancedCow::new, EntityClassification.CREATURE).size(0.4F, 1F).build(Reference.MODID + ":enhanced_cow");
    public static final EntityType<EnhancedPig> ENHANCED_PIG = EntityType.Builder.create(EnhancedPig.class, EnhancedPig::new).tracker(64, 1, true).build(Reference.MODID + ":enhanced_pig");

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
                new BlockItem(ModBlocks.Post_Acacia, new Item.Properties().group(ItemGroup.BUILDING_BLOCKS)).setRegistryName(ModBlocks.Post_Acacia.getRegistryName()),
                new BlockItem(ModBlocks.Post_Birch, new Item.Properties().group(ItemGroup.BUILDING_BLOCKS)).setRegistryName(ModBlocks.Post_Birch.getRegistryName()),
                new BlockItem(ModBlocks.Post_Dark_Oak, new Item.Properties().group(ItemGroup.BUILDING_BLOCKS)).setRegistryName(ModBlocks.Post_Dark_Oak.getRegistryName()),
                new BlockItem(ModBlocks.Post_Jungle, new Item.Properties().group(ItemGroup.BUILDING_BLOCKS)).setRegistryName(ModBlocks.Post_Jungle.getRegistryName()),
                new BlockItem(ModBlocks.Post_Oak, new Item.Properties().group(ItemGroup.BUILDING_BLOCKS)).setRegistryName(ModBlocks.Post_Oak.getRegistryName()),
                new BlockItem(ModBlocks.Post_Spruce, new Item.Properties().group(ItemGroup.BUILDING_BLOCKS)).setRegistryName(ModBlocks.Post_Spruce.getRegistryName()),
        };


        event.getRegistry().register(new SpawnEggItem(ENHANCED_CHICKEN, 0xFFFCF0,0xCC0000, new Item.Properties()
                .group(ItemGroup.MISC)).setRegistryName("enhanced_chicken_spawn_egg"));
        event.getRegistry().register(new SpawnEggItem(ENHANCED_LLAMA, 0xCDB29C,0x7B4B34, new Item.Properties()
                .group(ItemGroup.MISC)).setRegistryName("enhanced_llama_spawn_egg"));
        event.getRegistry().register(new SpawnEggItem(ENHANCED_SHEEP, 0xFFFFFF,0xFF8C8C, new Item.Properties()
                .group(ItemGroup.MISC)).setRegistryName("enhanced_sheep_spawn_egg"));
        event.getRegistry().register(new SpawnEggItem(ENHANCED_RABBIT, 0xCA8349,0x553C36, new Item.Properties()
                .group(ItemGroup.MISC)).setRegistryName("enhanced_rabbit_spawn_egg"));
        event.getRegistry().register(new SpawnEggItem(ENHANCED_COW, 0x260800,0xf9f9f7, new Item.Properties()
                .group(ItemGroup.MISC)).setRegistryName("enhanced_cow_spawn_egg"));
        event.getRegistry().register(new SpawnEggItem(ENHANCED_PIG, 0x000000,0x000000, new Item.Properties()
                .group(ItemGroup.MISC)).setRegistryName("enhanced_pig_spawn_egg"));


        event.getRegistry().registerAll(items);
        event.getRegistry().registerAll(itemBlocks);

        DispenserBlock.registerDispenseBehavior(ModItems.Egg_White,  new ProjectileDispenseBehavior() {
            /**
             * Return the projectile entity spawned by this dispense behavior.
             */
            protected IProjectile getProjectileEntity(World worldIn, IPosition position, ItemStack stackIn) {
                EnhancedEntityEgg egg = new EnhancedEntityEgg(worldIn, position.getX(), position.getY(), position.getZ());
                egg.setGenes(stackIn.getCapability(EggCapabilityProvider.EGG_CAP, null).orElse(null).getGenes());
                return egg;
            }
        });

        DispenserBlock.registerDispenseBehavior(ModItems.Egg_Cream,  new ProjectileDispenseBehavior() {
            /**
             * Return the projectile entity spawned by this dispense behavior.
             */
            protected IProjectile getProjectileEntity(World worldIn, IPosition position, ItemStack stackIn) {
                EnhancedEntityEgg egg = new EnhancedEntityEgg(worldIn, position.getX(), position.getY(), position.getZ());
                egg.setGenes(stackIn.getCapability(EggCapabilityProvider.EGG_CAP, null).orElse(null).getGenes());
                return egg;
            }
        });

        DispenserBlock.registerDispenseBehavior(ModItems.Egg_CreamDark,  new ProjectileDispenseBehavior() {
            /**
             * Return the projectile entity spawned by this dispense behavior.
             */
            protected IProjectile getProjectileEntity(World worldIn, IPosition position, ItemStack stackIn) {
                EnhancedEntityEgg egg = new EnhancedEntityEgg(worldIn, position.getX(), position.getY(), position.getZ());
                egg.setGenes(stackIn.getCapability(EggCapabilityProvider.EGG_CAP, null).orElse(null).getGenes());
                return egg;
            }
        });

        DispenserBlock.registerDispenseBehavior(ModItems.Egg_Pink,  new ProjectileDispenseBehavior() {
            /**
             * Return the projectile entity spawned by this dispense behavior.
             */
            protected IProjectile getProjectileEntity(World worldIn, IPosition position, ItemStack stackIn) {
                EnhancedEntityEgg egg = new EnhancedEntityEgg(worldIn, position.getX(), position.getY(), position.getZ());
                egg.setGenes(stackIn.getCapability(EggCapabilityProvider.EGG_CAP, null).orElse(null).getGenes());
                return egg;
            }
        });

        DispenserBlock.registerDispenseBehavior(ModItems.Egg_PinkDark,  new ProjectileDispenseBehavior() {
            /**
             * Return the projectile entity spawned by this dispense behavior.
             */
            protected IProjectile getProjectileEntity(World worldIn, IPosition position, ItemStack stackIn) {
                EnhancedEntityEgg egg = new EnhancedEntityEgg(worldIn, position.getX(), position.getY(), position.getZ());
                egg.setGenes(stackIn.getCapability(EggCapabilityProvider.EGG_CAP, null).orElse(null).getGenes());
                return egg;
            }
        });

        DispenserBlock.registerDispenseBehavior(ModItems.Egg_Brown,  new ProjectileDispenseBehavior() {
            /**
             * Return the projectile entity spawned by this dispense behavior.
             */
            protected IProjectile getProjectileEntity(World worldIn, IPosition position, ItemStack stackIn) {
                EnhancedEntityEgg egg = new EnhancedEntityEgg(worldIn, position.getX(), position.getY(), position.getZ());
                egg.setGenes(stackIn.getCapability(EggCapabilityProvider.EGG_CAP, null).orElse(null).getGenes());
                return egg;
            }
        });

        DispenserBlock.registerDispenseBehavior(ModItems.Egg_BrownDark,  new ProjectileDispenseBehavior() {
            /**
             * Return the projectile entity spawned by this dispense behavior.
             */
            protected IProjectile getProjectileEntity(World worldIn, IPosition position, ItemStack stackIn) {
                EnhancedEntityEgg egg = new EnhancedEntityEgg(worldIn, position.getX(), position.getY(), position.getZ());
                egg.setGenes(stackIn.getCapability(EggCapabilityProvider.EGG_CAP, null).orElse(null).getGenes());
                return egg;
            }
        });

        DispenserBlock.registerDispenseBehavior(ModItems.Egg_Blue,  new ProjectileDispenseBehavior() {
            /**
             * Return the projectile entity spawned by this dispense behavior.
             */
            protected IProjectile getProjectileEntity(World worldIn, IPosition position, ItemStack stackIn) {
                EnhancedEntityEgg egg = new EnhancedEntityEgg(worldIn, position.getX(), position.getY(), position.getZ());
                egg.setGenes(stackIn.getCapability(EggCapabilityProvider.EGG_CAP, null).orElse(null).getGenes());
                return egg;
            }
        });

        DispenserBlock.registerDispenseBehavior(ModItems.Egg_GreenLight,  new ProjectileDispenseBehavior() {
            /**
             * Return the projectile entity spawned by this dispense behavior.
             */
            protected IProjectile getProjectileEntity(World worldIn, IPosition position, ItemStack stackIn) {
                EnhancedEntityEgg egg = new EnhancedEntityEgg(worldIn, position.getX(), position.getY(), position.getZ());
                egg.setGenes(stackIn.getCapability(EggCapabilityProvider.EGG_CAP, null).orElse(null).getGenes());
                return egg;
            }
        });

        DispenserBlock.registerDispenseBehavior(ModItems.Egg_Green,  new ProjectileDispenseBehavior() {
            /**
             * Return the projectile entity spawned by this dispense behavior.
             */
            protected IProjectile getProjectileEntity(World worldIn, IPosition position, ItemStack stackIn) {
                EnhancedEntityEgg egg = new EnhancedEntityEgg(worldIn, position.getX(), position.getY(), position.getZ());
                egg.setGenes(stackIn.getCapability(EggCapabilityProvider.EGG_CAP, null).orElse(null).getGenes());
                return egg;
            }
        });

        DispenserBlock.registerDispenseBehavior(ModItems.Egg_Grey,  new ProjectileDispenseBehavior() {
            /**
             * Return the projectile entity spawned by this dispense behavior.
             */
            protected IProjectile getProjectileEntity(World worldIn, IPosition position, ItemStack stackIn) {
                EnhancedEntityEgg egg = new EnhancedEntityEgg(worldIn, position.getX(), position.getY(), position.getZ());
                egg.setGenes(stackIn.getCapability(EggCapabilityProvider.EGG_CAP, null).orElse(null).getGenes());
                return egg;
            }
        });

        DispenserBlock.registerDispenseBehavior(ModItems.Egg_GreyGreen,  new ProjectileDispenseBehavior() {
            /**
             * Return the projectile entity spawned by this dispense behavior.
             */
            protected IProjectile getProjectileEntity(World worldIn, IPosition position, ItemStack stackIn) {
                EnhancedEntityEgg egg = new EnhancedEntityEgg(worldIn, position.getX(), position.getY(), position.getZ());
                egg.setGenes(stackIn.getCapability(EggCapabilityProvider.EGG_CAP, null).orElse(null).getGenes());
                return egg;
            }
        });

        DispenserBlock.registerDispenseBehavior(ModItems.Egg_Olive,  new ProjectileDispenseBehavior() {
            /**
             * Return the projectile entity spawned by this dispense behavior.
             */
            protected IProjectile getProjectileEntity(World worldIn, IPosition position, ItemStack stackIn) {
                EnhancedEntityEgg egg = new EnhancedEntityEgg(worldIn, position.getX(), position.getY(), position.getZ());
                egg.setGenes(stackIn.getCapability(EggCapabilityProvider.EGG_CAP, null).orElse(null).getGenes());
                return egg;
            }
        });

        DispenserBlock.registerDispenseBehavior(ModItems.Egg_GreenDark,  new ProjectileDispenseBehavior() {
            /**
             * Return the projectile entity spawned by this dispense behavior.
             */
            protected IProjectile getProjectileEntity(World worldIn, IPosition position, ItemStack stackIn) {
                EnhancedEntityEgg egg = new EnhancedEntityEgg(worldIn, position.getX(), position.getY(), position.getZ());
                egg.setGenes(stackIn.getCapability(EggCapabilityProvider.EGG_CAP, null).orElse(null).getGenes());
                return egg;
            }
        });

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
    public static void onEntitiesRegistry(final RegistryEvent.Register<EntityType<?>> event) {
        event.getRegistry().register(ENHANCED_CHICKEN.setRegistryName("enhanced_chicken"));
        event.getRegistry().register(ENHANCED_RABBIT.setRegistryName("enhanced_rabbit"));
        event.getRegistry().register(ENHANCED_SHEEP.setRegistryName("enhanced_sheep"));
        event.getRegistry().register(ENHANCED_LLAMA.setRegistryName("enhanced_llama"));
        event.getRegistry().register(ENHANCED_COW.setRegistryName("enhanced_cow"));
        event.getRegistry().register(ENHANCED_PIG.setRegistryName("enhanced_pig"));
        event.getRegistry().register(ENHANCED_ENTITY_EGG_ENTITY_TYPE.setRegistryName("enhanced_entity_egg"));
        event.getRegistry().register(ENHANCED_LLAMA_SPIT.setRegistryName("enhanced_entity_llama_spit"));

//        EntitySpawnPlacementRegistry.register(ENHANCED_CHICKEN, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES);
//        EntitySpawnPlacementRegistry.register(ENHANCED_RABBIT, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES);
//        EntitySpawnPlacementRegistry.register(ENHANCED_SHEEP, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES);
//        EntitySpawnPlacementRegistry.register(ENHANCED_LLAMA, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES);
//        EntitySpawnPlacementRegistry.register(ENHANCED_COW, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES);
//        EntitySpawnPlacementRegistry.register(ENHANCED_PIG, EntitySpawnPlacementRegistry.SpawnPlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, null);

        for (Biome biome : ForgeRegistries.BIOMES) {

            Set<BiomeDictionary.Type> types = BiomeDictionary.getTypes(biome);

            //Enhanced Chicken Spawning
            if (!biome.getRegistryName().equals(Biomes.OCEAN.getRegistryName()) && !biome.getRegistryName().equals(Biomes.LUKEWARM_OCEAN.getRegistryName()) && !biome.getRegistryName().equals(Biomes.DEEP_LUKEWARM_OCEAN.getRegistryName()) && !biome.getRegistryName().equals(Biomes.DEEP_OCEAN.getRegistryName()) && !biome.getRegistryName().equals(Biomes.DEEP_LUKEWARM_OCEAN.getRegistryName()) &&  !biome.getRegistryName().equals(Biomes.DEEP_COLD_OCEAN.getRegistryName()) &&  !biome.getRegistryName().equals(Biomes.DEEP_FROZEN_OCEAN.getRegistryName()) &&
                    !biome.getRegistryName().equals(Biomes.DESERT.getRegistryName()) && !biome.getRegistryName().equals(Biomes.DESERT_HILLS.getRegistryName()) && !biome.getRegistryName().equals(Biomes.DESERT_LAKES.getRegistryName()) &&
                    !biome.getRegistryName().equals(Biomes.THE_END.getRegistryName()) && !biome.getRegistryName().equals(Biomes.NETHER.getRegistryName())) {
                biome.getSpawns(EntityClassification.CREATURE).add(new Biome.SpawnListEntry(ENHANCED_CHICKEN, 10, 4, 4));
            }

            //Enhanced Rabbit Spawning
            if (biome.getRegistryName().equals(Biomes.SNOWY_TUNDRA.getRegistryName()) || biome.getRegistryName().equals(Biomes.ICE_SPIKES.getRegistryName()) || biome.getRegistryName().equals(Biomes.SNOWY_MOUNTAINS.getRegistryName()) || biome.getRegistryName().equals(Biomes.SNOWY_TAIGA.getRegistryName()) || biome.getRegistryName().equals(Biomes.SNOWY_TAIGA_HILLS.getRegistryName()) || biome.getRegistryName().equals(Biomes.SNOWY_TAIGA_MOUNTAINS.getRegistryName()) || biome.getRegistryName().equals(Biomes.TAIGA.getRegistryName()) || biome.getRegistryName().equals(Biomes.TAIGA_HILLS.getRegistryName()) || biome.getRegistryName().equals(Biomes.TAIGA_MOUNTAINS.getRegistryName()) || biome.getRegistryName().equals(Biomes.GIANT_TREE_TAIGA.getRegistryName()) || biome.getRegistryName().equals(Biomes.GIANT_TREE_TAIGA_HILLS.getRegistryName()) ||
                    biome.getRegistryName().equals(Biomes.FLOWER_FOREST.getRegistryName()) ||
                    biome.getRegistryName().equals(Biomes.DESERT.getRegistryName()) || biome.getRegistryName().equals(Biomes.DESERT_LAKES.getRegistryName()) || biome.getRegistryName().equals(Biomes.DESERT_HILLS.getRegistryName()) ) {
                biome.getSpawns(EntityClassification.CREATURE).add(new Biome.SpawnListEntry(ENHANCED_RABBIT, 10, 2, 3));
            }

            //Enhanced Llama Spawning
            if (biome.getRegistryName().equals(Biomes.MOUNTAINS.getRegistryName()) || biome.getRegistryName().equals(Biomes.TAIGA_MOUNTAINS.getRegistryName()) ||
                    biome.getRegistryName().equals(Biomes.SAVANNA.getRegistryName()) || biome.getRegistryName().equals(Biomes.SHATTERED_SAVANNA.getRegistryName()) || biome.getRegistryName().equals(Biomes.SAVANNA_PLATEAU.getRegistryName()) || biome.getRegistryName().equals(Biomes.SHATTERED_SAVANNA_PLATEAU.getRegistryName()) ) {
                biome.getSpawns(EntityClassification.CREATURE).add(new Biome.SpawnListEntry(ENHANCED_LLAMA, 5, 4, 6));
            }

            //Enhanced Sheep Spawning
            if (!biome.getRegistryName().equals(Biomes.OCEAN.getRegistryName()) && !biome.getRegistryName().equals(Biomes.LUKEWARM_OCEAN.getRegistryName()) && !biome.getRegistryName().equals(Biomes.DEEP_LUKEWARM_OCEAN.getRegistryName()) && !biome.getRegistryName().equals(Biomes.DEEP_OCEAN.getRegistryName()) && !biome.getRegistryName().equals(Biomes.DEEP_LUKEWARM_OCEAN.getRegistryName()) &&  !biome.getRegistryName().equals(Biomes.DEEP_COLD_OCEAN.getRegistryName()) &&  !biome.getRegistryName().equals(Biomes.DEEP_FROZEN_OCEAN.getRegistryName()) &&
                    !biome.getRegistryName().equals(Biomes.DESERT.getRegistryName()) && !biome.getRegistryName().equals(Biomes.DESERT_HILLS.getRegistryName()) && !biome.getRegistryName().equals(Biomes.DESERT_LAKES.getRegistryName()) &&
                    !biome.getRegistryName().equals(Biomes.THE_END.getRegistryName()) && !biome.getRegistryName().equals(Biomes.NETHER.getRegistryName())){
                biome.getSpawns(EntityClassification.CREATURE).add(new Biome.SpawnListEntry(ENHANCED_SHEEP, 5, 4, 6));
            }

            //Enhanced Cow Spawning
            if (!biome.getRegistryName().equals(Biomes.OCEAN.getRegistryName()) && !biome.getRegistryName().equals(Biomes.LUKEWARM_OCEAN.getRegistryName()) && !biome.getRegistryName().equals(Biomes.DEEP_LUKEWARM_OCEAN.getRegistryName()) && !biome.getRegistryName().equals(Biomes.DEEP_OCEAN.getRegistryName()) && !biome.getRegistryName().equals(Biomes.DEEP_LUKEWARM_OCEAN.getRegistryName()) &&  !biome.getRegistryName().equals(Biomes.DEEP_COLD_OCEAN.getRegistryName()) &&  !biome.getRegistryName().equals(Biomes.DEEP_FROZEN_OCEAN.getRegistryName()) &&
                    !biome.getRegistryName().equals(Biomes.DESERT.getRegistryName()) && !biome.getRegistryName().equals(Biomes.DESERT_HILLS.getRegistryName()) && !biome.getRegistryName().equals(Biomes.DESERT_LAKES.getRegistryName()) &&
                    !biome.getRegistryName().equals(Biomes.THE_END.getRegistryName()) && !biome.getRegistryName().equals(Biomes.NETHER.getRegistryName())){
                biome.getSpawns(EntityClassification.CREATURE).add(new Biome.SpawnListEntry(ENHANCED_COW, 5, 4, 6));
            }

        }
    }

}
