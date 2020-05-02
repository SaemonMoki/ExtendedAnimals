package mokiyoki.enhancedanimals.util.handlers;


import mokiyoki.enhancedanimals.EnhancedAnimals;
import mokiyoki.enhancedanimals.blocks.EggCartonContainer;
import mokiyoki.enhancedanimals.capability.egg.EggCapabilityProvider;
//import mokiyoki.enhancedanimals.capability.woolcolour.WoolColourCapabilityProvider;
import mokiyoki.enhancedanimals.entity.EnhancedBee;
import mokiyoki.enhancedanimals.entity.EnhancedCat;
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
import net.minecraft.client.renderer.Atlases;
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
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static mokiyoki.enhancedanimals.init.ModBlocks.Egg_Carton;
import static mokiyoki.enhancedanimals.renderer.EggCartonTileEntityRenderer.EGG_CARTON_TEXTURE;

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
    public static final EntityType<EnhancedSheep> ENHANCED_SHEEP = EntityType.Builder.create(EnhancedSheep::new, EntityClassification.CREATURE).size(0.9F, 1.3F).build(Reference.MODID + ":enhanced_sheep");
    public static final EntityType<EnhancedLlama> ENHANCED_LLAMA = EntityType.Builder.create(EnhancedLlama::new, EntityClassification.CREATURE).size(0.9F, 1.87F).build(Reference.MODID + ":enhanced_llama");
    public static final EntityType<EnhancedCow> ENHANCED_COW = EntityType.Builder.create(EnhancedCow::new, EntityClassification.CREATURE).size(1.2F, 1.5F).build(Reference.MODID + ":enhanced_cow");
    public static final EntityType<EnhancedMooshroom> ENHANCED_MOOSHROOM = EntityType.Builder.create(EnhancedMooshroom::new, EntityClassification.CREATURE).size(1.2F, 1.5F).build(Reference.MODID + ":enhanced_mooshroom");
    public static final EntityType<EnhancedPig> ENHANCED_PIG = EntityType.Builder.create(EnhancedPig::new, EntityClassification.CREATURE).size(0.9F, 0.9F).build(Reference.MODID + ":enhanced_pig");
    public static final EntityType<EnhancedBee> ENHANCED_BEE = EntityType.Builder.create(EnhancedBee::new, EntityClassification.CREATURE).size(0.4F, 0.4F).build(Reference.MODID + ":enhanced_bee");
    public static final EntityType<EnhancedHorse> ENHANCED_HORSE = EntityType.Builder.create(EnhancedHorse::new, EntityClassification.CREATURE).size(1.4F, 1.6F).build(Reference.MODID + ":enhanced_horse");
    public static final EntityType<EnhancedCat> ENHANCED_CAT = EntityType.Builder.create(EnhancedCat::new, EntityClassification.CREATURE).size(0.6F, 0.7F).build(Reference.MODID + ":enhanced_cat");

    public static final ContainerType<EggCartonContainer> EGG_CARTON_CONTAINER = IForgeContainerType.create(EggCartonContainer::new);

    @SubscribeEvent
    public static void onRegisterBlocks(final RegistryEvent.Register<Block> event) {
        final Block[] blocks = {ModBlocks.Post_Acacia, ModBlocks.Post_Birch, ModBlocks.Post_Dark_Oak, ModBlocks.Post_Jungle, ModBlocks.Post_Oak, ModBlocks.Post_Spruce, ModBlocks.UnboundHay_Block, ModBlocks.SparseGrass_Block, Egg_Carton
        };
            event.getRegistry().registerAll(blocks);
    }

    @SubscribeEvent
    public static void onRegisterItems(final RegistryEvent.Register<Item> event) {
        final Item[] itemEggs = {ModItems.Egg_White, ModItems.Egg_CreamLight, ModItems.Egg_Cream, ModItems.Egg_CreamDark, ModItems.Egg_CreamDarkest, ModItems.Egg_CarmelDark, ModItems.Egg_Garnet, ModItems.Egg_PinkLight, ModItems.Egg_Pink, ModItems.Egg_PinkDark, ModItems.Egg_PinkDarkest, ModItems.Egg_CherryDark, ModItems.Egg_Plum, ModItems.Egg_BrownLight, ModItems.Egg_Brown, ModItems.Egg_BrownDark, ModItems.Egg_Chocolate, ModItems.Egg_ChocolateDark, ModItems.Egg_ChocolateCosmos,
                                 ModItems.Egg_Blue, ModItems.Egg_GreenLight, ModItems.Egg_GreenYellow, ModItems.Egg_OliveLight, ModItems.Egg_Olive, ModItems.Egg_OliveDark, ModItems.Egg_Army, ModItems.Egg_Mint, ModItems.Egg_Green, ModItems.Egg_GreenDark, ModItems.Egg_Pine, ModItems.Egg_PineDark, ModItems.Egg_PineBlack, ModItems.Egg_BlueGrey, ModItems.Egg_Grey, ModItems.Egg_GreyGreen, ModItems.Egg_Avocado, ModItems.Egg_AvocadoDark, ModItems.Egg_Feldgrau,
                                 ModItems.Egg_PowderBlue, ModItems.Egg_Tea, ModItems.Egg_Matcha, ModItems.Egg_MatchaDark, ModItems.Egg_Moss, ModItems.Egg_MossDark, ModItems.Egg_GreenUmber, ModItems.Egg_Celadon, ModItems.Egg_Fern, ModItems.Egg_Asparagus, ModItems.Egg_Hunter, ModItems.Egg_HunterDark, ModItems.Egg_TreeDark, ModItems.Egg_GreyBlue, ModItems.Egg_GreyNeutral, ModItems.Egg_Laurel, ModItems.Egg_Reseda, ModItems.Egg_GreenPewter, ModItems.Egg_GreyDark,
                                 ModItems.Egg_PaleBlue, ModItems.Egg_HoneyDew, ModItems.Egg_Earth, ModItems.Egg_Khaki, ModItems.Egg_Grullo, ModItems.Egg_KhakiDark, ModItems.Egg_Carob, ModItems.Egg_Jade, ModItems.Egg_Pistachio, ModItems.Egg_Sage, ModItems.Egg_Rosemary, ModItems.Egg_GreenBrown, ModItems.Egg_Umber, ModItems.Egg_CoolGrey, ModItems.Egg_PinkGrey, ModItems.Egg_WarmGrey, ModItems.Egg_Artichoke, ModItems.Egg_MyrtleGrey, ModItems.Egg_Rifle,
                                 ModItems.Egg_Cream_Spot, ModItems.Egg_CreamDark_Spot, ModItems.Egg_Carmel_Spot, ModItems.Egg_CarmelDark_Spot, ModItems.Egg_Garnet_Spot, ModItems.Egg_Pink_Spot, ModItems.Egg_PinkDark_Spot, ModItems.Egg_Cherry_Spot, ModItems.Egg_CherryDark_Spot, ModItems.Egg_Plum_Spot, ModItems.Egg_BrownLight_Spot, ModItems.Egg_Brown_Spot, ModItems.Egg_BrownDark_Spot, ModItems.Egg_Chocolate_Spot, ModItems.Egg_ChocolateDark_Spot,
                                 ModItems.Egg_GreenYellow_Spot, ModItems.Egg_OliveLight_Spot, ModItems.Egg_Olive_Spot, ModItems.Egg_OliveDark_Spot, ModItems.Egg_Army_Spot, ModItems.Egg_Mint_Spot, ModItems.Egg_Green_Spot, ModItems.Egg_GreenDark_Spot, ModItems.Egg_Pine_Spot, ModItems.Egg_PineDark_Spot, ModItems.Egg_PineBlack_Spot, ModItems.Egg_Grey_Spot, ModItems.Egg_GreyGreen_Spot, ModItems.Egg_Avocado_Spot, ModItems.Egg_AvocadoDark_Spot, ModItems.Egg_Feldgrau_Spot,
                                 ModItems.Egg_Matcha_Spot, ModItems.Egg_MatchaDark_Spot, ModItems.Egg_Moss_Spot, ModItems.Egg_MossDark_Spot, ModItems.Egg_GreenUmber_Spot, ModItems.Egg_Celadon_Spot, ModItems.Egg_Fern_Spot, ModItems.Egg_Asparagus_Spot, ModItems.Egg_Hunter_Spot, ModItems.Egg_HunterDark_Spot, ModItems.Egg_TreeDark_Spot, ModItems.Egg_GreyNeutral_Spot, ModItems.Egg_Laurel_Spot, ModItems.Egg_Reseda_Spot, ModItems.Egg_GreenPewter_Spot, ModItems.Egg_GreyDark_Spot,
                                 ModItems.Egg_Earth_Spot, ModItems.Egg_Khaki_Spot, ModItems.Egg_Grullo_Spot, ModItems.Egg_KhakiDark_Spot, ModItems.Egg_Carob_Spot, ModItems.Egg_Jade_Spot, ModItems.Egg_Pistachio_Spot, ModItems.Egg_Sage_Spot, ModItems.Egg_Rosemary_Spot, ModItems.Egg_GreenBrown_Spot, ModItems.Egg_Umber_Spot, ModItems.Egg_PinkGrey_Spot, ModItems.Egg_WarmGrey_Spot, ModItems.Egg_Artichoke_Spot, ModItems.Egg_MyrtleGrey_Spot, ModItems.Egg_Rifle_Spot,
                                 ModItems.Egg_Cream_Speckle, ModItems.Egg_CreamDark_Speckle, ModItems.Egg_Carmel_Speckle, ModItems.Egg_CarmelDark_Speckle, ModItems.Egg_Garnet_Speckle, ModItems.Egg_Pink_Speckle, ModItems.Egg_PinkDark_Speckle, ModItems.Egg_Cherry_Speckle, ModItems.Egg_CherryDark_Speckle, ModItems.Egg_Plum_Speckle, ModItems.Egg_BrownLight_Speckle, ModItems.Egg_Brown_Speckle, ModItems.Egg_BrownDark_Speckle, ModItems.Egg_Chocolate_Speckle, ModItems.Egg_ChocolateDark_Speckle,
                                 ModItems.Egg_GreenYellow_Speckle, ModItems.Egg_OliveLight_Speckle, ModItems.Egg_Olive_Speckle, ModItems.Egg_OliveDark_Speckle, ModItems.Egg_Army_Speckle, ModItems.Egg_Mint_Speckle, ModItems.Egg_Green_Speckle, ModItems.Egg_GreenDark_Speckle, ModItems.Egg_Pine_Speckle, ModItems.Egg_PineDark_Speckle, ModItems.Egg_PineBlack_Speckle, ModItems.Egg_Grey_Speckle, ModItems.Egg_GreyGreen_Speckle, ModItems.Egg_Avocado_Speckle, ModItems.Egg_AvocadoDark_Speckle, ModItems.Egg_Feldgrau_Speckle,
                                 ModItems.Egg_Matcha_Speckle, ModItems.Egg_MatchaDark_Speckle, ModItems.Egg_Moss_Speckle, ModItems.Egg_MossDark_Speckle, ModItems.Egg_GreenUmber_Speckle, ModItems.Egg_Celadon_Speckle, ModItems.Egg_Fern_Speckle, ModItems.Egg_Asparagus_Speckle, ModItems.Egg_Hunter_Speckle, ModItems.Egg_HunterDark_Speckle, ModItems.Egg_TreeDark_Speckle, ModItems.Egg_GreyNeutral_Speckle, ModItems.Egg_Laurel_Speckle, ModItems.Egg_Reseda_Speckle, ModItems.Egg_GreenPewter_Speckle, ModItems.Egg_GreyDark_Speckle,
                                 ModItems.Egg_Earth_Speckle, ModItems.Egg_Khaki_Speckle, ModItems.Egg_Grullo_Speckle, ModItems.Egg_KhakiDark_Speckle, ModItems.Egg_Carob_Speckle, ModItems.Egg_Jade_Speckle, ModItems.Egg_Pistachio_Speckle, ModItems.Egg_Sage_Speckle, ModItems.Egg_Rosemary_Speckle, ModItems.Egg_GreenBrown_Speckle, ModItems.Egg_Umber_Speckle, ModItems.Egg_PinkGrey_Speckle, ModItems.Egg_WarmGrey_Speckle, ModItems.Egg_Artichoke_Speckle, ModItems.Egg_MyrtleGrey_Speckle, ModItems.Egg_Rifle_Speckle,
                                 ModItems.Egg_Cream_Spatter, ModItems.Egg_CreamDark_Spatter, ModItems.Egg_Carmel_Spatter, ModItems.Egg_CarmelDark_Spatter, ModItems.Egg_Garnet_Spatter, ModItems.Egg_Pink_Spatter, ModItems.Egg_PinkDark_Spatter, ModItems.Egg_Cherry_Spatter, ModItems.Egg_CherryDark_Spatter, ModItems.Egg_Plum_Spatter, ModItems.Egg_BrownLight_Spatter, ModItems.Egg_Brown_Spatter, ModItems.Egg_BrownDark_Spatter, ModItems.Egg_Chocolate_Spatter, ModItems.Egg_ChocolateDark_Spatter,
                                 ModItems.Egg_GreenYellow_Spatter, ModItems.Egg_OliveLight_Spatter, ModItems.Egg_Olive_Spatter, ModItems.Egg_OliveDark_Spatter, ModItems.Egg_Army_Spatter, ModItems.Egg_Mint_Spatter, ModItems.Egg_Green_Spatter, ModItems.Egg_GreenDark_Spatter, ModItems.Egg_Pine_Spatter, ModItems.Egg_PineDark_Spatter, ModItems.Egg_PineBlack_Spatter, ModItems.Egg_Grey_Spatter, ModItems.Egg_GreyGreen_Spatter, ModItems.Egg_Avocado_Spatter, ModItems.Egg_AvocadoDark_Spatter, ModItems.Egg_Feldgrau_Spatter,
                                 ModItems.Egg_Matcha_Spatter, ModItems.Egg_MatchaDark_Spatter, ModItems.Egg_Moss_Spatter, ModItems.Egg_MossDark_Spatter, ModItems.Egg_GreenUmber_Spatter, ModItems.Egg_Celadon_Spatter, ModItems.Egg_Fern_Spatter, ModItems.Egg_Asparagus_Spatter, ModItems.Egg_Hunter_Spatter, ModItems.Egg_HunterDark_Spatter, ModItems.Egg_TreeDark_Spatter, ModItems.Egg_GreyNeutral_Spatter, ModItems.Egg_Laurel_Spatter, ModItems.Egg_Reseda_Spatter, ModItems.Egg_GreenPewter_Spatter, ModItems.Egg_GreyDark_Spatter,
                                 ModItems.Egg_Earth_Spatter, ModItems.Egg_Khaki_Spatter, ModItems.Egg_Grullo_Spatter, ModItems.Egg_KhakiDark_Spatter, ModItems.Egg_Carob_Spatter, ModItems.Egg_Jade_Spatter, ModItems.Egg_Pistachio_Spatter, ModItems.Egg_Sage_Spatter, ModItems.Egg_Rosemary_Spatter, ModItems.Egg_GreenBrown_Spatter, ModItems.Egg_Umber_Spatter, ModItems.Egg_PinkGrey_Spatter, ModItems.Egg_WarmGrey_Spatter, ModItems.Egg_Artichoke_Spatter, ModItems.Egg_MyrtleGrey_Spatter, ModItems.Egg_Rifle_Spatter};

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
                new BlockItem(Egg_Carton, new Item.Properties().maxStackSize(1).group(EnhancedAnimals.GENETICS_ANIMALS_GROUP)).setRegistryName(Egg_Carton.getRegistryName()),
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
        event.getRegistry().register(new SpawnEggItem(ENHANCED_PIG, 0xFFA4A4,0xB34d4d, new Item.Properties()
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
        event.getRegistry().register(ENHANCED_CAT.setRegistryName("enhanced_cat"));
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
        removeVanillaFromBiomes(ForgeRegistries.BIOMES);

        for (Biome biome : ForgeRegistries.BIOMES) {
            //Enhanced Rabbit Spawning
            if (ConfigHandler.COMMON.spawnGeneticRabbits.get() && (biome.getRegistryName().equals(Biomes.SNOWY_MOUNTAINS.getRegistryName()) || biome.getRegistryName().equals(Biomes.SNOWY_TAIGA_HILLS.getRegistryName()) || biome.getRegistryName().equals(Biomes.SNOWY_TAIGA_MOUNTAINS.getRegistryName()) || biome.getRegistryName().equals(Biomes.TAIGA_HILLS.getRegistryName()) || biome.getRegistryName().equals(Biomes.TAIGA_MOUNTAINS.getRegistryName()) || biome.getRegistryName().equals(Biomes.GIANT_TREE_TAIGA_HILLS.getRegistryName()))) {
                biome.getSpawns(EntityClassification.CREATURE).add(new Biome.SpawnListEntry(ENHANCED_RABBIT, 4, 2, 3));
            }
        }
    }

    private static void removeVanillaFromBiomes(IForgeRegistry<Biome> biomes) {
        Iterator<Biome> biomeIterator = biomes.iterator();

        while (biomeIterator.hasNext()) {
            Biome biome = biomeIterator.next();
            Iterator<Biome.SpawnListEntry> spawns = biome.getSpawns(EntityClassification.CREATURE).iterator();

            ArrayList<Biome.SpawnListEntry> addSpawns = new ArrayList<Biome.SpawnListEntry>();
            while (spawns.hasNext()) {
                Biome.SpawnListEntry entry = spawns.next();
                //add and remove pigs
                if (entry.entityType == EntityType.PIG) {
                    if(!ConfigHandler.COMMON.spawnVanillaPigs.get()) {
                        spawns.remove();
                    }
                    addSpawns.add(new Biome.SpawnListEntry(ENHANCED_PIG, 6, 2, 3));
                }
                //add and remove sheep
                if (entry.entityType == EntityType.SHEEP) {
                    if (!ConfigHandler.COMMON.spawnVanillaSheep.get()) {
                        spawns.remove();
                    }
                    addSpawns.add(new Biome.SpawnListEntry(ENHANCED_SHEEP, 12, 4, 4));
                }
                //add and remove cow
                if (entry.entityType == EntityType.COW) {
                    if(!ConfigHandler.COMMON.spawnVanillaCows.get()) {
                        spawns.remove();
                    }
                    addSpawns.add(new Biome.SpawnListEntry(ENHANCED_COW, 8, 4, 4));
                }
                //add and remove llama
                if (entry.entityType == EntityType.LLAMA) {
                    if(!ConfigHandler.COMMON.spawnVanillaLlamas.get()) {
                        spawns.remove();
                    }
                    addSpawns.add(new Biome.SpawnListEntry(ENHANCED_LLAMA, 4, 2, 3));
                }
                //add and remove chicken
                if (entry.entityType == EntityType.CHICKEN) {
                    if (!ConfigHandler.COMMON.spawnVanillaChickens.get()) {
                        spawns.remove();
                    }
                    addSpawns.add(new Biome.SpawnListEntry(ENHANCED_CHICKEN, 10, 4, 4));
                }
                //add and remove rabbit
                if (entry.entityType == EntityType.RABBIT) {
                    if (!ConfigHandler.COMMON.spawnVanillaRabbits.get()) {
                        spawns.remove();
                    }
                    addSpawns.add(new Biome.SpawnListEntry(ENHANCED_RABBIT, 4, 2, 3));
                }
                //add and remove mooshroom
                if (entry.entityType == EntityType.MOOSHROOM) {
                    if (!ConfigHandler.COMMON.spawnVanillaMooshroom.get()) {
                        spawns.remove();
                    }
                    addSpawns.add(new Biome.SpawnListEntry(ENHANCED_MOOSHROOM, 8, 4, 4));
                }
            }
            if (!addSpawns.isEmpty()) {
                biome.getSpawns(EntityClassification.CREATURE).addAll(addSpawns);
            }
        }
    }

}
