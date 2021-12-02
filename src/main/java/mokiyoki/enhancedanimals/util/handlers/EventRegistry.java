package mokiyoki.enhancedanimals.util.handlers;


import mokiyoki.enhancedanimals.EnhancedAnimals;
import mokiyoki.enhancedanimals.blocks.EggCartonContainer;
import mokiyoki.enhancedanimals.capability.egg.EggCapabilityProvider;
//import mokiyoki.enhancedanimals.capability.woolcolour.WoolColourCapabilityProvider;
import mokiyoki.enhancedanimals.entity.EnhancedAnimalAbstract;
//import mokiyoki.enhancedanimals.entity.EnhancedBee;
//import mokiyoki.enhancedanimals.entity.EnhancedCat;
import mokiyoki.enhancedanimals.entity.EnhancedChicken;
import mokiyoki.enhancedanimals.entity.EnhancedEntityEgg;
import mokiyoki.enhancedanimals.entity.EnhancedEntityLlamaSpit;
//import mokiyoki.enhancedanimals.entity.EnhancedHorse;
import mokiyoki.enhancedanimals.entity.EnhancedHorse;
import mokiyoki.enhancedanimals.entity.EnhancedMoobloom;
import mokiyoki.enhancedanimals.entity.EnhancedMooshroom;
import mokiyoki.enhancedanimals.entity.EnhancedRabbit;
import mokiyoki.enhancedanimals.entity.EnhancedLlama;
import mokiyoki.enhancedanimals.entity.EnhancedCow;
import mokiyoki.enhancedanimals.entity.EnhancedPig;
import mokiyoki.enhancedanimals.entity.EnhancedSheep;
import mokiyoki.enhancedanimals.entity.EnhancedTurtle;
import mokiyoki.enhancedanimals.gui.EnhancedAnimalContainer;
import mokiyoki.enhancedanimals.init.ModBlocks;
import mokiyoki.enhancedanimals.init.ModItems;
import mokiyoki.enhancedanimals.init.ModTileEntities;
import mokiyoki.enhancedanimals.util.EnhancedAnimalInfo;
import mokiyoki.enhancedanimals.util.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.DispenserBlock;
import net.minecraft.dispenser.BeehiveDispenseBehavior;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IPosition;
import net.minecraft.dispenser.ProjectileDispenseBehavior;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.*;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.IForgeShearable;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static mokiyoki.enhancedanimals.init.ModBlocks.EGG_CARTON;
import static mokiyoki.enhancedanimals.init.ModBlocks.TURTLE_EGG;

//import static mokiyoki.enhancedanimals.capability.woolcolour.WoolColourCapabilityProvider.WOOL_COLOUR_CAP;

/**
 * Created by moki on 24/08/2018.
 */
@Mod.EventBusSubscriber(modid = Reference.MODID, bus= Mod.EventBusSubscriber.Bus.MOD)
public class EventRegistry {
    private static final Logger eventLogger = LogManager.getLogger();

    public static final EntityType<EnhancedEntityLlamaSpit> ENHANCED_LLAMA_SPIT = EntityType.Builder.<EnhancedEntityLlamaSpit>create(EnhancedEntityLlamaSpit::new, EntityClassification.MISC).size(0.25F, 0.25F).build(Reference.MODID + ":enhanced_entity_llama_spit");
    public static final EntityType<EnhancedEntityEgg> ENHANCED_ENTITY_EGG_ENTITY_TYPE = EntityType.Builder.<EnhancedEntityEgg>create(EnhancedEntityEgg::new, EntityClassification.MISC).size(0.25F, 0.25F).build(Reference.MODID + ":enhanced_entity_egg");
    public static final EntityType<EnhancedChicken> ENHANCED_CHICKEN = EntityType.Builder.create(EnhancedChicken::new, EntityClassification.CREATURE).size(0.4F, 0.7F).setTrackingRange(64).setUpdateInterval(1).build(Reference.MODID + ":enhanced_chicken");
    public static final EntityType<EnhancedRabbit> ENHANCED_RABBIT = EntityType.Builder.create(EnhancedRabbit::new, EntityClassification.CREATURE).size(0.4F, 0.5F).build(Reference.MODID + ":enhanced_rabbit");
    public static final EntityType<EnhancedSheep> ENHANCED_SHEEP = EntityType.Builder.create(EnhancedSheep::new, EntityClassification.CREATURE).size(0.9F, 1.3F).build(Reference.MODID + ":enhanced_sheep");
    public static final EntityType<EnhancedLlama> ENHANCED_LLAMA = EntityType.Builder.create(EnhancedLlama::new, EntityClassification.CREATURE).size(0.9F, 1.87F).build(Reference.MODID + ":enhanced_llama");
    public static final EntityType<EnhancedCow> ENHANCED_COW = EntityType.Builder.create(EnhancedCow::new, EntityClassification.CREATURE).size(1.0F, 1.5F).build(Reference.MODID + ":enhanced_cow");
    public static final EntityType<EnhancedMooshroom> ENHANCED_MOOSHROOM = EntityType.Builder.create(EnhancedMooshroom::new, EntityClassification.CREATURE).size(1.0F, 1.5F).build(Reference.MODID + ":enhanced_mooshroom");
    public static final EntityType<EnhancedMoobloom> ENHANCED_MOOBLOOM = EntityType.Builder.create(EnhancedMoobloom::new, EntityClassification.CREATURE).size(1.0F, 1.5F).build(Reference.MODID + ":enhanced_moobloom");
    public static final EntityType<EnhancedPig> ENHANCED_PIG = EntityType.Builder.create(EnhancedPig::new, EntityClassification.CREATURE).size(0.9F, 0.9F).build(Reference.MODID + ":enhanced_pig");
    public static final EntityType<EnhancedHorse> ENHANCED_HORSE = EntityType.Builder.create(EnhancedHorse::new, EntityClassification.CREATURE).size(1.0F, 1.6F).build(Reference.MODID + ":enhanced_horse");
    public static final EntityType<EnhancedTurtle> ENHANCED_TURTLE = EntityType.Builder.create(EnhancedTurtle::new, EntityClassification.CREATURE).size(1.2F, 0.4F).build(Reference.MODID + ":enhanced_turtle");
//    public static final EntityType<EnhancedCat> ENHANCED_CAT = EntityType.Builder.create(EnhancedCat::new, EntityClassification.CREATURE).size(0.6F, 0.7F).build(Reference.MODID + ":enhanced_cat");
//    public static final EntityType<EnhancedBee> ENHANCED_BEE = EntityType.Builder.create(EnhancedBee::new, EntityClassification.CREATURE).size(0.4F, 0.4F).build(Reference.MODID + ":enhanced_bee");

    public static final ContainerType<EggCartonContainer> EGG_CARTON_CONTAINER = IForgeContainerType.create(EggCartonContainer::new);
    public static final ContainerType<EnhancedAnimalContainer> ENHANCED_ANIMAL_CONTAINER = IForgeContainerType.create((windowId, inv, data) -> {
        Entity entity = inv.player.world.getEntityByID(data.readInt());
        EnhancedAnimalInfo animalInfo = new EnhancedAnimalInfo(data.readString());
        if(entity instanceof EnhancedAnimalAbstract) {
            return new EnhancedAnimalContainer(windowId, inv, (EnhancedAnimalAbstract) entity, animalInfo);
        } else {
            return null;
        }
    });

    @SubscribeEvent
    public static void onRegisterBlocks(final RegistryEvent.Register<Block> event) {
        final Block[] blocks = {ModBlocks.POST_ACACIA, ModBlocks.POST_BIRCH, ModBlocks.POST_DARK_OAK, ModBlocks.POST_JUNGLE, ModBlocks.POST_OAK,
                ModBlocks.POST_SPRUCE, ModBlocks.UNBOUNDHAY_BLOCK, ModBlocks.SPARSEGRASS_BLOCK, ModBlocks.PATCHYMYCELIUM_BLOCK, ModBlocks.EGG_CARTON,
                ModBlocks.TURTLE_EGG, ModBlocks.GROWABLE_ALLIUM, ModBlocks.GROWABLE_AZURE_BLUET, ModBlocks.GROWABLE_BLUE_ORCHID, ModBlocks.GROWABLE_CORNFLOWER,
                ModBlocks.GROWABLE_DANDELION, ModBlocks.GROWABLE_OXEYE_DAISY, ModBlocks.GROWABLE_GRASS, ModBlocks.GROWABLE_FERN, ModBlocks.GROWABLE_ROSE_BUSH,
                ModBlocks.GROWABLE_SUNFLOWER, ModBlocks.GROWABLE_TALL_GRASS, ModBlocks.GROWABLE_LARGE_FERN
        };
            event.getRegistry().registerAll(blocks);
    }

    @SubscribeEvent
    public static void onRegisterItems(final RegistryEvent.Register<Item> event) {
        final Item[] itemEggs = {ModItems.Egg_White, ModItems.Egg_CreamLight, ModItems.Egg_Cream, ModItems.Egg_CreamDark, ModItems.Egg_CreamDarkest, ModItems.Egg_CarmelDark, ModItems.Egg_Garnet, ModItems.Egg_PinkLight, ModItems.Egg_Pink, ModItems.Egg_PinkDark, ModItems.Egg_PinkDarkest, ModItems.Egg_CherryDark, ModItems.Egg_Plum, ModItems.Egg_BrownLight, ModItems.Egg_Brown, ModItems.Egg_BrownDark, ModItems.Egg_Chocolate, ModItems.Egg_ChocolateDark, ModItems.Egg_ChocolateCosmos,
                 ModItems.Egg_Blue, ModItems.Egg_GreenLight, ModItems.Egg_GreenYellow, ModItems.Egg_OliveLight, ModItems.Egg_Olive, ModItems.Egg_OliveDark, ModItems.Egg_Army, ModItems.Egg_Mint, ModItems.Egg_Green, ModItems.Egg_GreenDark, ModItems.Egg_Pine, ModItems.Egg_PineDark, ModItems.Egg_PineBlack, ModItems.Egg_BlueGrey, ModItems.Egg_Grey, ModItems.Egg_GreyGreen, ModItems.Egg_Avocado, ModItems.Egg_AvocadoDark, ModItems.Egg_Feldgrau,
                 ModItems.Egg_PowderBlue, ModItems.Egg_Tea, ModItems.Egg_Matcha, ModItems.Egg_MatchaDark, ModItems.Egg_Moss, ModItems.Egg_MossDark, ModItems.Egg_GreenUmber, ModItems.Egg_Celadon, ModItems.Egg_Fern, ModItems.Egg_Asparagus, ModItems.Egg_Hunter, ModItems.Egg_HunterDark, ModItems.Egg_TreeDark, ModItems.EGG_GREYBLUE, ModItems.Egg_GreyNeutral, ModItems.Egg_Laurel, ModItems.Egg_Reseda, ModItems.Egg_GreenPewter, ModItems.Egg_GreyDark,
                 ModItems.EGG_PALEBLUE, ModItems.EGG_HONEYDEW, ModItems.Egg_Earth, ModItems.Egg_Khaki, ModItems.Egg_Grullo, ModItems.Egg_KhakiDark, ModItems.Egg_Carob, ModItems.Egg_Jade, ModItems.Egg_Pistachio, ModItems.Egg_Sage, ModItems.Egg_Rosemary, ModItems.Egg_GreenBrown, ModItems.Egg_Umber, ModItems.EGG_COOLGREY, ModItems.Egg_PinkGrey, ModItems.Egg_WarmGrey, ModItems.Egg_Artichoke, ModItems.Egg_MyrtleGrey, ModItems.Egg_Rifle,
                 ModItems.Egg_Cream_Spot, ModItems.Egg_CreamDark_Spot, ModItems.Egg_Carmel_Spot, ModItems.Egg_CarmelDark_Spot, ModItems.Egg_Garnet_Spot, ModItems.Egg_Pink_Spot, ModItems.Egg_PinkDark_Spot, ModItems.Egg_Cherry_Spot, ModItems.Egg_CherryDark_Spot, ModItems.Egg_Plum_Spot, ModItems.Egg_BrownLight_Spot, ModItems.Egg_Brown_Spot, ModItems.Egg_BrownDark_Spot, ModItems.Egg_Chocolate_Spot, ModItems.Egg_ChocolateDark_Spot,
                 ModItems.Egg_GreenYellow_Spot, ModItems.Egg_OliveLight_Spot, ModItems.Egg_Olive_Spot, ModItems.Egg_OliveDark_Spot, ModItems.Egg_Army_Spot, ModItems.Egg_Mint_Spot, ModItems.Egg_Green_Spot, ModItems.Egg_GreenDark_Spot, ModItems.Egg_Pine_Spot, ModItems.Egg_PineDark_Spot, ModItems.Egg_PineBlack_Spot, ModItems.Egg_Grey_Spot, ModItems.Egg_GreyGreen_Spot, ModItems.Egg_Avocado_Spot, ModItems.Egg_AvocadoDark_Spot, ModItems.Egg_Feldgrau_Spot,
                 ModItems.Egg_Matcha_Spot, ModItems.Egg_MatchaDark_Spot, ModItems.EGG_MOSS_SPOT, ModItems.EGG_MOSSDARK_SPOT, ModItems.EGG_GREENUMBER_SPOT, ModItems.EGG_CELADON_SPOT, ModItems.EGG_FERN_SPOT, ModItems.EGG_ASPARAGUS_SPOT, ModItems.EGG_HUNTER_SPOT, ModItems.EGG_HUNTERDARK_SPOT, ModItems.EGG_TREEDARK_SPOT, ModItems.EGG_GREYNEUTRAL_SPOT, ModItems.EGG_LAUREL_SPOT, ModItems.EGG_RESEDA_SPOT, ModItems.EGG_GREENPEWTER_SPOT, ModItems.EGG_GREYDARK_SPOT,
                 ModItems.EGG_EARTH_SPOT, ModItems.EGG_KHAKI_SPOT, ModItems.EGG_GRULLO_SPOT, ModItems.EGG_KHAKIDARK_SPOT, ModItems.EGG_CAROB_SPOT, ModItems.EGG_JADE_SPOT, ModItems.EGG_PISTACHIO_SPOT, ModItems.EGG_SAGE_SPOT, ModItems.EGG_ROSEMARY_SPOT, ModItems.EGG_GREENBROWN_SPOT, ModItems.EGG_UMBER_SPOT, ModItems.EGG_PINKGREY_SPOT, ModItems.EGG_WARMGREY_SPOT, ModItems.EGG_ARTICHOKE_SPOT, ModItems.EGG_MYRTLEGREY_SPOT, ModItems.EGG_RIFLE_SPOT,
                 ModItems.Egg_Cream_Speckle, ModItems.Egg_CreamDark_Speckle, ModItems.Egg_Carmel_Speckle, ModItems.Egg_CarmelDark_Speckle, ModItems.Egg_Garnet_Speckle, ModItems.Egg_Pink_Speckle, ModItems.Egg_PinkDark_Speckle, ModItems.Egg_Cherry_Speckle, ModItems.Egg_CherryDark_Speckle, ModItems.Egg_Plum_Speckle, ModItems.Egg_BrownLight_Speckle, ModItems.Egg_Brown_Speckle, ModItems.Egg_BrownDark_Speckle, ModItems.Egg_Chocolate_Speckle, ModItems.Egg_ChocolateDark_Speckle,
                 ModItems.Egg_GreenYellow_Speckle, ModItems.Egg_OliveLight_Speckle, ModItems.Egg_Olive_Speckle, ModItems.Egg_OliveDark_Speckle, ModItems.Egg_Army_Speckle, ModItems.Egg_Mint_Speckle, ModItems.Egg_Green_Speckle, ModItems.Egg_GreenDark_Speckle, ModItems.Egg_Pine_Speckle, ModItems.Egg_PineDark_Speckle, ModItems.Egg_PineBlack_Speckle, ModItems.Egg_Grey_Speckle, ModItems.Egg_GreyGreen_Speckle, ModItems.Egg_Avocado_Speckle, ModItems.Egg_AvocadoDark_Speckle, ModItems.Egg_Feldgrau_Speckle,
                 ModItems.EGG_MATCHA_SPECKLE, ModItems.EGG_MATCHADARK_SPECKLE, ModItems.EGG_MOSS_SPECKLE, ModItems.EGG_MOSSDARK_SPECKLE, ModItems.EGG_GREENUMBER_SPECKLE, ModItems.EGG_CELADON_SPECKLE, ModItems.EGG_FERN_SPECKLE, ModItems.EGG_ASPARAGUS_SPECKLE, ModItems.EGG_HUNTER_SPECKLE, ModItems.EGG_HUNTERDARK_SPECKLE, ModItems.EGG_TREEDARK_SPECKLE, ModItems.EGG_GREYNEUTRAL_SPECKLE, ModItems.EGG_LAUREL_SPECKLE, ModItems.EGG_RESEDA_SPECKLE, ModItems.EGG_GREENPEWTER_SPECKLE, ModItems.EGG_GREYDARK_SPECKLE,
                 ModItems.EGG_EARTH_SPECKLE, ModItems.EGG_KHAKI_SPECKLE, ModItems.EGG_GRULLO_SPECKLE, ModItems.EGG_KHAKIDARK_SPECKLE, ModItems.EGG_CAROB_SPECKLE, ModItems.EGG_JADE_SPECKLE, ModItems.EGG_PISTACHIO_SPECKLE, ModItems.EGG_SAGE_SPECKLE, ModItems.EGG_ROSEMARY_SPECKLE, ModItems.EGG_GREENBROWN_SPECKLE, ModItems.EGG_UMBER_SPECKLE, ModItems.EGG_PINKGREY_SPECKLE, ModItems.EGG_WARMGREY_SPECKLE, ModItems.EGG_ARTICHOKE_SPECKLE, ModItems.EGG_MYRTLEGREY_SPECKLE, ModItems.EGG_RIFLE_SPECKLE,
                 ModItems.Egg_Cream_Spatter, ModItems.Egg_CreamDark_Spatter, ModItems.Egg_Carmel_Spatter, ModItems.Egg_CarmelDark_Spatter, ModItems.Egg_Garnet_Spatter, ModItems.Egg_Pink_Spatter, ModItems.Egg_PinkDark_Spatter, ModItems.Egg_Cherry_Spatter, ModItems.Egg_CherryDark_Spatter, ModItems.Egg_Plum_Spatter, ModItems.Egg_BrownLight_Spatter, ModItems.Egg_Brown_Spatter, ModItems.Egg_BrownDark_Spatter, ModItems.Egg_Chocolate_Spatter, ModItems.Egg_ChocolateDark_Spatter,
                 ModItems.Egg_GreenYellow_Spatter, ModItems.Egg_OliveLight_Spatter, ModItems.Egg_Olive_Spatter, ModItems.Egg_OliveDark_Spatter, ModItems.Egg_Army_Spatter, ModItems.Egg_Mint_Spatter, ModItems.Egg_Green_Spatter, ModItems.Egg_GreenDark_Spatter, ModItems.Egg_Pine_Spatter, ModItems.Egg_PineDark_Spatter, ModItems.Egg_PineBlack_Spatter, ModItems.Egg_Grey_Spatter, ModItems.Egg_GreyGreen_Spatter, ModItems.Egg_Avocado_Spatter, ModItems.Egg_AvocadoDark_Spatter, ModItems.Egg_Feldgrau_Spatter,
                 ModItems.EGG_MATCHA_SPATTER, ModItems.EGG_MATCHADARK_SPATTER, ModItems.EGG_MOSS_SPATTER, ModItems.EGG_MOSSDARK_SPATTER, ModItems.EGG_GREENUMBER_SPATTER, ModItems.EGG_CELADON_SPATTER, ModItems.EGG_FERN_SPATTER, ModItems.EGG_ASPARAGUS_SPATTER, ModItems.EGG_HUNTER_SPATTER, ModItems.EGG_HUNTERDARK_SPATTER, ModItems.EGG_TREEDARK_SPATTER, ModItems.EGG_GREYNEUTRAL_SPATTER, ModItems.EGG_LAUREL_SPATTER, ModItems.EGG_RESEDA_SPATTER, ModItems.EGG_GREENPEWTER_SPATTER, ModItems.EGG_GREYDARK_SPATTER,
                 ModItems.EGG_EARTH_SPATTER, ModItems.EGG_KHAKI_SPATTER, ModItems.EGG_GRULLO_SPATTER, ModItems.EGG_KHAKIDARK_SPATTER, ModItems.EGG_CAROB_SPATTER, ModItems.EGG_JADE_SPATTER, ModItems.EGG_PISTACHIO_SPATTER, ModItems.EGG_SAGE_SPATTER, ModItems.EGG_ROSEMARY_SPATTER, ModItems.EGG_GREENBROWN_SPATTER, ModItems.EGG_UMBER_SPATTER, ModItems.EGG_PINKGREY_SPATTER, ModItems.EGG_WARMGREY_SPATTER, ModItems.EGG_ARTICHOKE_SPATTER, ModItems.EGG_MYRTLEGREY_SPATTER, ModItems.EGG_RIFLE_SPATTER};

        final Item[] items = {ModItems.RAWCHICKEN_DARKSMALL, ModItems.RAWCHICKEN_DARKBIG, ModItems.RAWCHICKEN_DARK, ModItems.COOKEDCHICKEN_DARKSMALL, ModItems.COOKEDCHICKEN_DARK,
                ModItems.COOKEDCHICKEN_DARKBIG, ModItems.RAWCHICKEN_PALESMALL, ModItems.RAWCHICKEN_PALE, ModItems.COOKEDCHICKEN_PALESMALL, ModItems.COOKEDCHICKEN_PALE,
                ModItems.RAWRABBIT_SMALL, ModItems.COOKEDRABBIT_SMALL, ModItems.RABBITSTEW_WEAK, ModItems.HALF_MILK_BOTTLE, ModItems.MILK_BOTTLE, ModItems.ONESIXTH_MILK_BUCKET,
                ModItems.ONETHIRD_MILK_BUCKET, ModItems.HALF_MILK_BUCKET, ModItems.TWOTHIRDS_MILK_BUCKET, ModItems.FIVESIXTHS_MILK_BUCKET,
                ModItems.BRIDLE_BASIC_LEATHER, ModItems.BRIDLE_BASIC_LEATHER_GOLD, ModItems.BRIDLE_BASIC_LEATHER_DIAMOND,
                ModItems.BRIDLE_BASIC_CLOTH, ModItems.BRIDLE_BASIC_CLOTH_GOLD, ModItems.BRIDLE_BASIC_CLOTH_DIAMOND,
                ModItems.SADDLE_BASIC_LEATHER, ModItems.SADDLE_BASIC_LEATHER_DIAMOND, ModItems.SADDLE_BASIC_LEATHER_GOLD, ModItems.SADDLE_BASIC_LEATHER_WOOD,
                ModItems.SADDLE_BASIC_CLOTH, ModItems.SADDLE_BASIC_CLOTH_GOLD, ModItems.SADDLE_BASIC_CLOTH_DIAMOND, ModItems.SADDLE_BASIC_CLOTH_WOOD,
                ModItems.SADDLE_BASIC_LEATHERCLOTHSEAT, ModItems.SADDLE_BASIC_LEATHERCLOTHSEAT_GOLD, ModItems.SADDLE_BASIC_LEATHERCLOTHSEAT_DIAMOND, ModItems.SADDLE_BASIC_LEATHERCLOTHSEAT_WOOD,
                ModItems.SADDLE_BASICPOMEL_LEATHER, ModItems.SADDLE_BASICPOMEL_LEATHER_GOLD, ModItems.SADDLE_BASICPOMEL_LEATHER_DIAMOND, ModItems.SADDLE_BASICPOMEL_LEATHER_WOOD,
                ModItems.SADDLE_BASICPOMEL_CLOTH, ModItems.SADDLE_BASICPOMEL_CLOTH_GOLD, ModItems.SADDLE_BASICPOMEL_CLOTH_DIAMOND, ModItems.SADDLE_BASICPOMEL_CLOTH_WOOD,
                ModItems.SADDLE_BASICPOMEL_LEATHERCLOTHSEAT, ModItems.SADDLE_BASICPOMEL_LEATHERCLOTHSEAT_GOLD, ModItems.SADDLE_BASICPOMEL_LEATHERCLOTHSEAT_DIAMOND, ModItems.SADDLE_BASICPOMEL_LEATHERCLOTHSEAT_WOOD,
                ModItems.SADDLE_ENGLISH_LEATHER, ModItems.SADDLE_ENGLISH_LEATHER_GOLD, ModItems.SADDLE_ENGLISH_LEATHER_DIAMOND, ModItems.SADDLE_ENGLISH_LEATHER_WOOD,
                ModItems.SADDLE_ENGLISH_CLOTH,ModItems.SADDLE_ENGLISH_CLOTH_GOLD, ModItems.SADDLE_ENGLISH_CLOTH_DIAMOND, ModItems.SADDLE_ENGLISH_CLOTH_WOOD,
                ModItems.SADDLE_ENGLISH_LEATHERCLOTHSEAT, ModItems.SADDLE_ENGLISH_LEATHERCLOTHSEAT_GOLD, ModItems.SADDLE_ENGLISH_LEATHERCLOTHSEAT_DIAMOND, ModItems.SADDLE_ENGLISH_LEATHERCLOTHSEAT_WOOD,
                ModItems.COLLAR_BASIC_LEATHER, ModItems.COLLAR_BASIC_LEATHER_IRONBELL, ModItems.COLLAR_BASIC_LEATHER_IRONRING, ModItems.COLLAR_BASIC_LEATHER_GOLDBELL, ModItems.COLLAR_BASIC_LEATHER_GOLDRING, ModItems.COLLAR_BASIC_LEATHER_DIAMONDBELL, ModItems.COLLAR_BASIC_LEATHER_DIAMONDRING,
                ModItems.COLLAR_BASIC_CLOTH, ModItems.COLLAR_BASIC_CLOTH_IRONBELL, ModItems.COLLAR_BASIC_CLOTH_IRONRING, ModItems.COLLAR_BASIC_CLOTH_GOLDBELL, ModItems.COLLAR_BASIC_CLOTH_GOLDRING, ModItems.COLLAR_BASIC_CLOTH_DIAMONDBELL, ModItems.COLLAR_BASIC_CLOTH_DIAMONDRING, ModItems.DEBUG_GENE_BOOK};

        final Item[] itemBlocks = {
                new BlockItem(ModBlocks.POST_ACACIA, new Item.Properties().group(EnhancedAnimals.GENETICS_ANIMALS_GROUP)).setRegistryName(ModBlocks.POST_ACACIA.getRegistryName()),
                new BlockItem(ModBlocks.POST_BIRCH, new Item.Properties().group(EnhancedAnimals.GENETICS_ANIMALS_GROUP)).setRegistryName(ModBlocks.POST_BIRCH.getRegistryName()),
                new BlockItem(ModBlocks.POST_DARK_OAK, new Item.Properties().group(EnhancedAnimals.GENETICS_ANIMALS_GROUP)).setRegistryName(ModBlocks.POST_DARK_OAK.getRegistryName()),
                new BlockItem(ModBlocks.POST_JUNGLE, new Item.Properties().group(EnhancedAnimals.GENETICS_ANIMALS_GROUP)).setRegistryName(ModBlocks.POST_JUNGLE.getRegistryName()),
                new BlockItem(ModBlocks.POST_OAK, new Item.Properties().group(EnhancedAnimals.GENETICS_ANIMALS_GROUP)).setRegistryName(ModBlocks.POST_OAK.getRegistryName()),
                new BlockItem(ModBlocks.POST_SPRUCE, new Item.Properties().group(EnhancedAnimals.GENETICS_ANIMALS_GROUP)).setRegistryName(ModBlocks.POST_SPRUCE.getRegistryName()),
                new BlockItem(ModBlocks.UNBOUNDHAY_BLOCK, new Item.Properties().group(EnhancedAnimals.GENETICS_ANIMALS_GROUP)).setRegistryName(ModBlocks.UNBOUNDHAY_BLOCK.getRegistryName()),
                new BlockItem(ModBlocks.SPARSEGRASS_BLOCK, new Item.Properties().group(EnhancedAnimals.GENETICS_ANIMALS_GROUP)).setRegistryName(ModBlocks.SPARSEGRASS_BLOCK.getRegistryName()),
                new BlockItem(ModBlocks.PATCHYMYCELIUM_BLOCK, new Item.Properties().group(EnhancedAnimals.GENETICS_ANIMALS_GROUP)).setRegistryName(ModBlocks.PATCHYMYCELIUM_BLOCK.getRegistryName()),
                new BlockItem(ModBlocks.GROWABLE_ALLIUM, new Item.Properties().group(EnhancedAnimals.GENETICS_ANIMALS_GROUP)).setRegistryName(ModBlocks.GROWABLE_ALLIUM.getRegistryName()),
                new BlockItem(ModBlocks.GROWABLE_AZURE_BLUET, new Item.Properties().group(EnhancedAnimals.GENETICS_ANIMALS_GROUP)).setRegistryName(ModBlocks.GROWABLE_AZURE_BLUET.getRegistryName()),
                new BlockItem(ModBlocks.GROWABLE_BLUE_ORCHID, new Item.Properties().group(EnhancedAnimals.GENETICS_ANIMALS_GROUP)).setRegistryName(ModBlocks.GROWABLE_BLUE_ORCHID.getRegistryName()),
                new BlockItem(ModBlocks.GROWABLE_CORNFLOWER, new Item.Properties().group(EnhancedAnimals.GENETICS_ANIMALS_GROUP)).setRegistryName(ModBlocks.GROWABLE_CORNFLOWER.getRegistryName()),
                new BlockItem(ModBlocks.GROWABLE_DANDELION, new Item.Properties().group(EnhancedAnimals.GENETICS_ANIMALS_GROUP)).setRegistryName(ModBlocks.GROWABLE_DANDELION.getRegistryName()),
                new BlockItem(ModBlocks.GROWABLE_OXEYE_DAISY, new Item.Properties().group(EnhancedAnimals.GENETICS_ANIMALS_GROUP)).setRegistryName(ModBlocks.GROWABLE_OXEYE_DAISY.getRegistryName()),
                new BlockItem(ModBlocks.GROWABLE_GRASS, new Item.Properties().group(EnhancedAnimals.GENETICS_ANIMALS_GROUP)).setRegistryName(ModBlocks.GROWABLE_GRASS.getRegistryName()),
                new BlockItem(ModBlocks.GROWABLE_FERN, new Item.Properties().group(EnhancedAnimals.GENETICS_ANIMALS_GROUP)).setRegistryName(ModBlocks.GROWABLE_FERN.getRegistryName()),
                new BlockItem(ModBlocks.GROWABLE_ROSE_BUSH, new Item.Properties().group(EnhancedAnimals.GENETICS_ANIMALS_GROUP)).setRegistryName(ModBlocks.GROWABLE_ROSE_BUSH.getRegistryName()),
                new BlockItem(ModBlocks.GROWABLE_SUNFLOWER, new Item.Properties().group(EnhancedAnimals.GENETICS_ANIMALS_GROUP)).setRegistryName(ModBlocks.GROWABLE_SUNFLOWER.getRegistryName()),
                new BlockItem(ModBlocks.GROWABLE_TALL_GRASS, new Item.Properties().group(EnhancedAnimals.GENETICS_ANIMALS_GROUP)).setRegistryName(ModBlocks.GROWABLE_TALL_GRASS.getRegistryName()),
                new BlockItem(ModBlocks.GROWABLE_LARGE_FERN, new Item.Properties().group(EnhancedAnimals.GENETICS_ANIMALS_GROUP)).setRegistryName(ModBlocks.GROWABLE_LARGE_FERN.getRegistryName()),
                new BlockItem(TURTLE_EGG, new Item.Properties().maxStackSize(1).group(EnhancedAnimals.GENETICS_ANIMALS_GROUP)).setRegistryName(TURTLE_EGG.getRegistryName()),
                new BlockItem(EGG_CARTON, new Item.Properties().maxStackSize(1).group(EnhancedAnimals.GENETICS_ANIMALS_GROUP)).setRegistryName(EGG_CARTON.getRegistryName()),
        };

        event.getRegistry().register(new SpawnEggItem(ENHANCED_TURTLE, 0xFFFFDD,0x00DDCC, new Item.Properties()
                .group(EnhancedAnimals.GENETICS_ANIMALS_GROUP)).setRegistryName("enhanced_turtle_spawn_egg"));
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


        event.getRegistry().registerAll(itemEggs);
        event.getRegistry().registerAll(items);
        event.getRegistry().registerAll(itemBlocks);

        for (Item egg : itemEggs) {
            DispenserBlock.registerDispenseBehavior(egg,  new ProjectileDispenseBehavior() {
                /**
                 * Return the projectile entity spawned by this dispense behavior.
                 */
                protected ProjectileEntity getProjectileEntity(World worldIn, IPosition position, ItemStack stackIn) {
                    EnhancedEntityEgg eggItem = new EnhancedEntityEgg(worldIn, position.getX(), position.getY(), position.getZ(), egg);
                    eggItem.setEggData(stackIn.getCapability(EggCapabilityProvider.EGG_CAP, null).orElse(null).getEggHolder(stackIn));
                    return eggItem;
                }
            });
        }

        DispenserBlock.registerDispenseBehavior(Items.SHEARS.asItem(), new GeneticShearDispenseBehavior());

        //TODO dispensers should be able to turn hay to unbound hay if they contain a sharp tool and are facing a hay block
    }


//    @SubscribeEvent
//    public static void registerBlockColourHandlers(final ColorHandlerEvent.Block event) {
//        final BlockColors blockColors = event.getBlockColors();
//
//        blockColors.register(new SparseGrassBlockColour(), ModBlocks.SPARSEGRASS_BLOCK);
//    }


//    @SubscribeEvent
//    public static void registerItemColourHandlers(final ColorHandlerEvent.Item event) {
//        final ItemColors itemColors = event.getItemColors();
//
//        itemColors.register(new SparseGrassItemColour(), ModBlocks.SPARSEGRASS_BLOCK);
//    }


    @SubscribeEvent
    public static void onTileEntitiesRegistry(final RegistryEvent.Register<TileEntityType<?>> event) {
        event.getRegistry().register(ModTileEntities.EGG_CARTON_TILE_ENTITY.setRegistryName("egg_carton_tile_entity"));
    }

    @SubscribeEvent
    public static void onContainerTypeRegistry(final RegistryEvent.Register<ContainerType<?>> event) {
        event.getRegistry().register(EGG_CARTON_CONTAINER.setRegistryName("egg_carton_container_box"));
        event.getRegistry().register(ENHANCED_ANIMAL_CONTAINER.setRegistryName("enhanced_animal_container"));
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
        event.getRegistry().register(ENHANCED_MOOBLOOM.setRegistryName("enhanced_moobloom"));
        event.getRegistry().register(ENHANCED_PIG.setRegistryName("enhanced_pig"));
        event.getRegistry().register(ENHANCED_HORSE.setRegistryName("enhanced_horse"));
        event.getRegistry().register(ENHANCED_TURTLE.setRegistryName("enhanced_turtle"));
//        event.getRegistry().register(ENHANCED_CAT.setRegistryName("enhanced_cat"));
        event.getRegistry().register(ENHANCED_ENTITY_EGG_ENTITY_TYPE.setRegistryName("enhanced_entity_egg"));
        event.getRegistry().register(ENHANCED_LLAMA_SPIT.setRegistryName("enhanced_entity_llama_spit"));

        EntitySpawnPlacementRegistry.register(ENHANCED_PIG, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AnimalEntity::canAnimalSpawn);
        EntitySpawnPlacementRegistry.register(ENHANCED_SHEEP, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AnimalEntity::canAnimalSpawn);
        EntitySpawnPlacementRegistry.register(ENHANCED_COW, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AnimalEntity::canAnimalSpawn);
        EntitySpawnPlacementRegistry.register(ENHANCED_LLAMA, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AnimalEntity::canAnimalSpawn);
        EntitySpawnPlacementRegistry.register(ENHANCED_CHICKEN, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AnimalEntity::canAnimalSpawn);
        EntitySpawnPlacementRegistry.register(ENHANCED_RABBIT, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AnimalEntity::canAnimalSpawn);
        EntitySpawnPlacementRegistry.register(ENHANCED_MOOSHROOM, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, EnhancedMooshroom::canMooshroomSpawn);
        EntitySpawnPlacementRegistry.register(ENHANCED_TURTLE, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, EnhancedTurtle::canTurtleSpawn);

    }

    private static class GeneticShearDispenseBehavior extends BeehiveDispenseBehavior {

        @Override
        protected ItemStack dispenseStack(IBlockSource source, ItemStack stack) {
            World world = source.getWorld();
            if (!world.isRemote()) {
                BlockPos blockpos = source.getBlockPos().offset(source.getBlockState().get(DispenserBlock.FACING));
                this.setSuccessful(shearGeneticAnimals((ServerWorld)world, blockpos));
                if (this.isSuccessful() && stack.attemptDamageItem(1, world.getRandom(), (ServerPlayerEntity)null)) {
                    stack.setCount(0);
                }
                if (this.isSuccessful()) {
                    return stack;
                }
            }

            return super.dispenseStack(source, stack);
        }

        private boolean shearGeneticAnimals(ServerWorld world, BlockPos pos) {
            for(LivingEntity livingentity : world.getEntitiesWithinAABB(LivingEntity.class, new AxisAlignedBB(pos), EntityPredicates.NOT_SPECTATING)) {
                if (livingentity instanceof EnhancedAnimalAbstract && livingentity instanceof IForgeShearable) {
                    IForgeShearable ishearable = (IForgeShearable)livingentity;
                    if (ishearable.isShearable(ItemStack.EMPTY, world, pos)) {
                        List<ItemStack> wool = ishearable.onSheared(null, ItemStack.EMPTY, world, pos, 0);
                        wool.forEach(d -> {
                            net.minecraft.entity.item.ItemEntity ent = livingentity.entityDropItem(d, 1.0F);
                            ent.setMotion(ent.getMotion().add((double)(ThreadLocalRandom.current().nextFloat() * 0.1F), (double)(ThreadLocalRandom.current().nextFloat() * 0.05F), (double)((ThreadLocalRandom.current().nextFloat()) * 0.1F)));
                        });
                        return true;
                    }
                }
            }

            return false;
        }

    }

}
