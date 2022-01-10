package mokiyoki.enhancedanimals.util.handlers;


import mokiyoki.enhancedanimals.EnhancedAnimals;
//import mokiyoki.enhancedanimals.blocks.EggCartonContainer;
import mokiyoki.enhancedanimals.capability.egg.EggCapabilityProvider;
//import mokiyoki.enhancedanimals.capability.woolcolour.WoolColourCapabilityProvider;
import mokiyoki.enhancedanimals.capability.egg.IEggCapability;
import mokiyoki.enhancedanimals.capability.hay.IHayCapability;
import mokiyoki.enhancedanimals.capability.post.IPostCapability;
import mokiyoki.enhancedanimals.capability.turtleegg.INestEggCapability;
import mokiyoki.enhancedanimals.entity.EnhancedAnimalAbstract;
//import mokiyoki.enhancedanimals.entity.EnhancedBee;
//import mokiyoki.enhancedanimals.entity.EnhancedCat;
import mokiyoki.enhancedanimals.entity.EnhancedAxolotl;
//import mokiyoki.enhancedanimals.entity.EnhancedChicken;
//import mokiyoki.enhancedanimals.entity.EnhancedEntityEgg;
//import mokiyoki.enhancedanimals.entity.EnhancedEntityLlamaSpit;
//import mokiyoki.enhancedanimals.entity.EnhancedHorse;
//import mokiyoki.enhancedanimals.entity.EnhancedHorse;
//import mokiyoki.enhancedanimals.entity.EnhancedMoobloom;
//import mokiyoki.enhancedanimals.entity.EnhancedMooshroom;
//import mokiyoki.enhancedanimals.entity.EnhancedRabbit;
//import mokiyoki.enhancedanimals.entity.EnhancedLlama;
//import mokiyoki.enhancedanimals.entity.EnhancedCow;
//import mokiyoki.enhancedanimals.entity.EnhancedPig;
//import mokiyoki.enhancedanimals.entity.EnhancedSheep;
//import mokiyoki.enhancedanimals.entity.EnhancedTurtle;
import mokiyoki.enhancedanimals.gui.EnhancedAnimalContainer;
import mokiyoki.enhancedanimals.init.ModBlocks;
import mokiyoki.enhancedanimals.init.ModItems;
//import mokiyoki.enhancedanimals.init.ModTileEntities;
//import mokiyoki.enhancedanimals.renderer.EggCartonTileEntityRenderer;
//import mokiyoki.enhancedanimals.renderer.EnhancedLlamaSpitRenderer;
//import mokiyoki.enhancedanimals.renderer.RenderEnhancedChicken;
//import mokiyoki.enhancedanimals.renderer.RenderEnhancedCow;
//import mokiyoki.enhancedanimals.renderer.RenderEnhancedHorse;
//import mokiyoki.enhancedanimals.renderer.RenderEnhancedLlama;
//import mokiyoki.enhancedanimals.renderer.RenderEnhancedMoobloom;
//import mokiyoki.enhancedanimals.renderer.RenderEnhancedMooshroom;
//import mokiyoki.enhancedanimals.renderer.RenderEnhancedPig;
//import mokiyoki.enhancedanimals.renderer.RenderEnhancedRabbit;
//import mokiyoki.enhancedanimals.renderer.RenderEnhancedSheep;
//import mokiyoki.enhancedanimals.renderer.RenderEnhancedTurtle;
import mokiyoki.enhancedanimals.renderer.RenderEnhancedAxolotl;
import mokiyoki.enhancedanimals.util.EnhancedAnimalInfo;
import mokiyoki.enhancedanimals.util.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.world.entity.ai.attributes.DefaultAttributes;
import net.minecraft.world.entity.animal.Pig;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.core.dispenser.ShearsDispenseItemBehavior;
import net.minecraft.core.BlockSource;
import net.minecraft.core.Position;
import net.minecraft.core.dispenser.AbstractProjectileDispenseBehavior;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.phys.AABB;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.client.ClientRegistry;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.common.IForgeShearable;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;


import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

//import static mokiyoki.enhancedanimals.init.ModBlocks.EGG_CARTON;
//import static mokiyoki.enhancedanimals.init.ModBlocks.TURTLE_EGG;
//import static mokiyoki.enhancedanimals.init.ModTileEntities.EGG_CARTON_TILE_ENTITY;

//import static mokiyoki.enhancedanimals.capability.woolcolour.WoolColourCapabilityProvider.WOOL_COLOUR_CAP;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

/**
 * Created by moki on 24/08/2018.
 */
@Mod.EventBusSubscriber(modid = Reference.MODID, bus= Mod.EventBusSubscriber.Bus.MOD)
public class EventRegistry {

    public static final DeferredRegister<EntityType<?>> ENTITIES_DEFERRED_REGISTRY = DeferredRegister.create(ForgeRegistries.ENTITIES, Reference.MODID);

//    public static final RegistryObject<EntityType<EnhancedEntityLlamaSpit>> ENHANCED_LLAMA_SPIT = ENTITIES_DEFERRED_REGISTRY.register("test_entity", () -> EntityType.Builder.<EnhancedEntityLlamaSpit>of(EnhancedEntityLlamaSpit::new, MobCategory.MISC).sized(0.25F, 0.25F).build(Reference.MODID + ":enhanced_entity_llama_spit"));
//    public static final RegistryObject<EntityType<EnhancedEntityEgg>> ENHANCED_ENTITY_EGG_ENTITY_TYPE = ENTITIES_DEFERRED_REGISTRY.register("test_entity", () -> EntityType.Builder.<EnhancedEntityEgg>of(EnhancedEntityEgg::new, MobCategory.MISC).sized(0.25F, 0.25F).build(Reference.MODID + ":enhanced_entity_egg"));
//    public static final RegistryObject<EntityType<EnhancedChicken>> ENHANCED_CHICKEN = ENTITIES_DEFERRED_REGISTRY.register("test_entity", () -> EntityType.Builder.of(EnhancedChicken::new, MobCategory.CREATURE).sized(0.4F, 0.7F).setTrackingRange(64).setUpdateInterval(1).build(Reference.MODID + ":enhanced_chicken"));
//    public static final RegistryObject<EntityType<EnhancedRabbit>> ENHANCED_RABBIT = ENTITIES_DEFERRED_REGISTRY.register("test_entity", () -> EntityType.Builder.of(EnhancedRabbit::new, MobCategory.CREATURE).sized(0.4F, 0.5F).build(Reference.MODID + ":enhanced_rabbit"));
//    public static final RegistryObject<EntityType<EnhancedSheep>> ENHANCED_SHEEP = ENTITIES_DEFERRED_REGISTRY.register("test_entity", () -> EntityType.Builder.of(EnhancedSheep::new, MobCategory.CREATURE).sized(0.9F, 1.3F).build(Reference.MODID + ":enhanced_sheep"));
//    public static final RegistryObject<EntityType<EnhancedLlama>> ENHANCED_LLAMA = ENTITIES_DEFERRED_REGISTRY.register("test_entity", () -> EntityType.Builder.of(EnhancedLlama::new, MobCategory.CREATURE).sized(0.9F, 1.87F).build(Reference.MODID + ":enhanced_llama"));
//    public static final RegistryObject<EntityType<EnhancedCow>> ENHANCED_COW = ENTITIES_DEFERRED_REGISTRY.register("test_entity", () -> EntityType.Builder.of(EnhancedCow::new, MobCategory.CREATURE).sized(1.0F, 1.5F).build(Reference.MODID + ":enhanced_cow"));
//    public static final RegistryObject<EntityType<EnhancedMooshroom>> ENHANCED_MOOSHROOM = ENTITIES_DEFERRED_REGISTRY.register("test_entity", () -> EntityType.Builder.of(EnhancedMooshroom::new, MobCategory.CREATURE).sized(1.0F, 1.5F).build(Reference.MODID + ":enhanced_mooshroom"));
//    public static final RegistryObject<EntityType<EnhancedMoobloom>> ENHANCED_MOOBLOOM = ENTITIES_DEFERRED_REGISTRY.register("test_entity", () -> EntityType.Builder.of(EnhancedMoobloom::new, MobCategory.CREATURE).sized(1.0F, 1.5F).build(Reference.MODID + ":enhanced_moobloom"));
//    public static final RegistryObject<EntityType<EnhancedPig>> ENHANCED_PIG = ENTITIES_DEFERRED_REGISTRY.register("test_entity", () -> EntityType.Builder.of(EnhancedPig::new, MobCategory.CREATURE).sized(0.9F, 0.9F).build(Reference.MODID + ":enhanced_pig"));
//    public static final RegistryObject<EntityType<EnhancedHorse>> ENHANCED_HORSE = ENTITIES_DEFERRED_REGISTRY.register("test_entity", () -> EntityType.Builder.of(EnhancedHorse::new, MobCategory.CREATURE).sized(1.0F, 1.6F).build(Reference.MODID + ":enhanced_horse"));
//    public static final RegistryObject<EntityType<EnhancedTurtle>> ENHANCED_TURTLE = ENTITIES_DEFERRED_REGISTRY.register("test_entity", () -> EntityType.Builder.of(EnhancedTurtle::new, MobCategory.CREATURE).sized(1.2F, 0.4F).build(Reference.MODID + ":enhanced_turtle"));
    public static final RegistryObject<EntityType<EnhancedAxolotl>> ENHANCED_AXOLOTL = ENTITIES_DEFERRED_REGISTRY.register("enhanced_axolotl", () -> EntityType.Builder.of(EnhancedAxolotl::new, MobCategory.CREATURE).sized(1.2F, 0.4F).build(Reference.MODID + ":enhanced_axolotl"));
//    public static final EntityType<EnhancedCat> ENHANCED_CAT = EntityType.Builder.create(EnhancedCat::new, EntityClassification.CREATURE).size(0.6F, 0.7F).build(Reference.MODID + ":enhanced_cat");
//    public static final EntityType<EnhancedBee> ENHANCED_BEE = EntityType.Builder.create(EnhancedBee::new, EntityClassification.CREATURE).size(0.4F, 0.4F).build(Reference.MODID + ":enhanced_bee");

//    public static final MenuType<EggCartonContainer> EGG_CARTON_CONTAINER = IForgeMenuType.create(EggCartonContainer::new);
    public static final MenuType<EnhancedAnimalContainer> ENHANCED_ANIMAL_CONTAINER = IForgeMenuType.create((windowId, inv, data) -> {
        Entity entity = inv.player.level.getEntity(data.readInt());
        EnhancedAnimalInfo animalInfo = new EnhancedAnimalInfo(data.readUtf());
        if(entity instanceof EnhancedAnimalAbstract) {
            return new EnhancedAnimalContainer(windowId, inv, (EnhancedAnimalAbstract) entity, animalInfo);
        } else {
            return null;
        }
    });


//    private static final RegistryObject<ForgeSpawnEggItem> ENHANCED_TURTLE_EGG = ModItems.register("enhanced_turtle_spawn_egg", () ->
//            new ForgeSpawnEggItem(ENHANCED_AXOLOTL, 0xFFFFDD,0x00DDCC, new Item.Properties()
//                    .tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP))
//    );

    @SubscribeEvent
    public static void onRegisterItems(final RegistryEvent.Register<Item> event) {
//        final Item[] itemEggs = {ModItems.EGG_WHITE, ModItems.EGG_CREAMLIGHT, ModItems.EGG_CREAM, ModItems.EGG_CREAMDARK, ModItems.EGG_CREAMDARKEST, ModItems.EGG_CARMELDARK, ModItems.EGG_GARNET, ModItems.EGG_PINKLIGHT, ModItems.EGG_PINK, ModItems.EGG_PINKDARK, ModItems.EGG_PINKDARKEST, ModItems.EGG_CHERRYDARK, ModItems.EGG_PLUM, ModItems.EGG_BROWNLIGHT, ModItems.EGG_BROWN, ModItems.EGG_BROWNDARK, ModItems.EGG_CHOCOLATE, ModItems.EGG_CHOCOLATEDARK, ModItems.EGG_CHOCOLATECOSMOS,
//                 ModItems.EGG_BLUE, ModItems.EGG_GREENLIGHT, ModItems.EGG_GREENYELLOW, ModItems.EGG_OLIVELIGHT, ModItems.EGG_OLIVE, ModItems.EGG_OLIVEDARK, ModItems.EGG_ARMY, ModItems.EGG_MINT, ModItems.EGG_GREEN, ModItems.EGG_GREENDARK, ModItems.EGG_PINE, ModItems.EGG_PINEDARK, ModItems.EGG_PINEBLACK, ModItems.EGG_BLUEGREY, ModItems.EGG_GREY, ModItems.EGG_GREYGREEN, ModItems.EGG_AVOCADO, ModItems.EGG_AVOCADODARK, ModItems.EGG_FELDGRAU,
//                 ModItems.EGG_POWDERBLUE, ModItems.EGG_TEA, ModItems.EGG_MATCHA, ModItems.EGG_MATCHADARK, ModItems.EGG_MOSS, ModItems.EGG_MOSSDARK, ModItems.EGG_GREENUMBER, ModItems.EGG_CELADON, ModItems.EGG_FERN, ModItems.EGG_ASPARAGUS, ModItems.EGG_HUNTER, ModItems.EGG_HUNTERDARK, ModItems.EGG_TREEDARK, ModItems.EGG_GREYBLUE, ModItems.EGG_GREYNEUTRAL, ModItems.EGG_LAUREL, ModItems.EGG_RESEDA, ModItems.EGG_GREENPEWTER, ModItems.EGG_GREYDARK,
//                 ModItems.EGG_PALEBLUE, ModItems.EGG_HONEYDEW, ModItems.EGG_EARTH, ModItems.EGG_KHAKI, ModItems.EGG_GRULLO, ModItems.EGG_KHAKIDARK, ModItems.EGG_CAROB, ModItems.EGG_JADE, ModItems.EGG_PISTACHIO, ModItems.EGG_SAGE, ModItems.EGG_ROSEMARY, ModItems.EGG_GREENBROWN, ModItems.EGG_UMBER, ModItems.EGG_COOLGREY, ModItems.EGG_PINKGREY, ModItems.EGG_WARMGREY, ModItems.EGG_ARTICHOKE, ModItems.EGG_MYRTLEGREY, ModItems.EGG_RIFLE,
//                 ModItems.Egg_Cream_Spot, ModItems.Egg_CreamDark_Spot, ModItems.Egg_Carmel_Spot, ModItems.Egg_CarmelDark_Spot, ModItems.Egg_Garnet_Spot, ModItems.Egg_Pink_Spot, ModItems.Egg_PinkDark_Spot, ModItems.Egg_Cherry_Spot, ModItems.Egg_CherryDark_Spot, ModItems.Egg_Plum_Spot, ModItems.Egg_BrownLight_Spot, ModItems.Egg_Brown_Spot, ModItems.Egg_BrownDark_Spot, ModItems.Egg_Chocolate_Spot, ModItems.Egg_ChocolateDark_Spot,
//                 ModItems.Egg_GreenYellow_Spot, ModItems.Egg_OliveLight_Spot, ModItems.Egg_Olive_Spot, ModItems.Egg_OliveDark_Spot, ModItems.Egg_Army_Spot, ModItems.Egg_Mint_Spot, ModItems.Egg_Green_Spot, ModItems.Egg_GreenDark_Spot, ModItems.Egg_Pine_Spot, ModItems.Egg_PineDark_Spot, ModItems.Egg_PineBlack_Spot, ModItems.Egg_Grey_Spot, ModItems.Egg_GreyGreen_Spot, ModItems.Egg_Avocado_Spot, ModItems.Egg_AvocadoDark_Spot, ModItems.Egg_Feldgrau_Spot,
//                 ModItems.Egg_Matcha_Spot, ModItems.Egg_MatchaDark_Spot, ModItems.EGG_MOSS_SPOT, ModItems.EGG_MOSSDARK_SPOT, ModItems.EGG_GREENUMBER_SPOT, ModItems.EGG_CELADON_SPOT, ModItems.EGG_FERN_SPOT, ModItems.EGG_ASPARAGUS_SPOT, ModItems.EGG_HUNTER_SPOT, ModItems.EGG_HUNTERDARK_SPOT, ModItems.EGG_TREEDARK_SPOT, ModItems.EGG_GREYNEUTRAL_SPOT, ModItems.EGG_LAUREL_SPOT, ModItems.EGG_RESEDA_SPOT, ModItems.EGG_GREENPEWTER_SPOT, ModItems.EGG_GREYDARK_SPOT,
//                 ModItems.EGG_EARTH_SPOT, ModItems.EGG_KHAKI_SPOT, ModItems.EGG_GRULLO_SPOT, ModItems.EGG_KHAKIDARK_SPOT, ModItems.EGG_CAROB_SPOT, ModItems.EGG_JADE_SPOT, ModItems.EGG_PISTACHIO_SPOT, ModItems.EGG_SAGE_SPOT, ModItems.EGG_ROSEMARY_SPOT, ModItems.EGG_GREENBROWN_SPOT, ModItems.EGG_UMBER_SPOT, ModItems.EGG_PINKGREY_SPOT, ModItems.EGG_WARMGREY_SPOT, ModItems.EGG_ARTICHOKE_SPOT, ModItems.EGG_MYRTLEGREY_SPOT, ModItems.EGG_RIFLE_SPOT,
//                 ModItems.Egg_Cream_Speckle, ModItems.Egg_CreamDark_Speckle, ModItems.Egg_Carmel_Speckle, ModItems.Egg_CarmelDark_Speckle, ModItems.Egg_Garnet_Speckle, ModItems.Egg_Pink_Speckle, ModItems.Egg_PinkDark_Speckle, ModItems.Egg_Cherry_Speckle, ModItems.Egg_CherryDark_Speckle, ModItems.Egg_Plum_Speckle, ModItems.Egg_BrownLight_Speckle, ModItems.Egg_Brown_Speckle, ModItems.Egg_BrownDark_Speckle, ModItems.Egg_Chocolate_Speckle, ModItems.Egg_ChocolateDark_Speckle,
//                 ModItems.Egg_GreenYellow_Speckle, ModItems.Egg_OliveLight_Speckle, ModItems.Egg_Olive_Speckle, ModItems.Egg_OliveDark_Speckle, ModItems.Egg_Army_Speckle, ModItems.Egg_Mint_Speckle, ModItems.Egg_Green_Speckle, ModItems.Egg_GreenDark_Speckle, ModItems.Egg_Pine_Speckle, ModItems.Egg_PineDark_Speckle, ModItems.Egg_PineBlack_Speckle, ModItems.Egg_Grey_Speckle, ModItems.Egg_GreyGreen_Speckle, ModItems.Egg_Avocado_Speckle, ModItems.Egg_AvocadoDark_Speckle, ModItems.Egg_Feldgrau_Speckle,
//                 ModItems.EGG_MATCHA_SPECKLE, ModItems.EGG_MATCHADARK_SPECKLE, ModItems.EGG_MOSS_SPECKLE, ModItems.EGG_MOSSDARK_SPECKLE, ModItems.EGG_GREENUMBER_SPECKLE, ModItems.EGG_CELADON_SPECKLE, ModItems.EGG_FERN_SPECKLE, ModItems.EGG_ASPARAGUS_SPECKLE, ModItems.EGG_HUNTER_SPECKLE, ModItems.EGG_HUNTERDARK_SPECKLE, ModItems.EGG_TREEDARK_SPECKLE, ModItems.EGG_GREYNEUTRAL_SPECKLE, ModItems.EGG_LAUREL_SPECKLE, ModItems.EGG_RESEDA_SPECKLE, ModItems.EGG_GREENPEWTER_SPECKLE, ModItems.EGG_GREYDARK_SPECKLE,
//                 ModItems.EGG_EARTH_SPECKLE, ModItems.EGG_KHAKI_SPECKLE, ModItems.EGG_GRULLO_SPECKLE, ModItems.EGG_KHAKIDARK_SPECKLE, ModItems.EGG_CAROB_SPECKLE, ModItems.EGG_JADE_SPECKLE, ModItems.EGG_PISTACHIO_SPECKLE, ModItems.EGG_SAGE_SPECKLE, ModItems.EGG_ROSEMARY_SPECKLE, ModItems.EGG_GREENBROWN_SPECKLE, ModItems.EGG_UMBER_SPECKLE, ModItems.EGG_PINKGREY_SPECKLE, ModItems.EGG_WARMGREY_SPECKLE, ModItems.EGG_ARTICHOKE_SPECKLE, ModItems.EGG_MYRTLEGREY_SPECKLE, ModItems.EGG_RIFLE_SPECKLE,
//                 ModItems.Egg_Cream_Spatter, ModItems.Egg_CreamDark_Spatter, ModItems.Egg_Carmel_Spatter, ModItems.Egg_CarmelDark_Spatter, ModItems.Egg_Garnet_Spatter, ModItems.Egg_Pink_Spatter, ModItems.Egg_PinkDark_Spatter, ModItems.Egg_Cherry_Spatter, ModItems.Egg_CherryDark_Spatter, ModItems.Egg_Plum_Spatter, ModItems.Egg_BrownLight_Spatter, ModItems.Egg_Brown_Spatter, ModItems.Egg_BrownDark_Spatter, ModItems.Egg_Chocolate_Spatter, ModItems.Egg_ChocolateDark_Spatter,
//                 ModItems.Egg_GreenYellow_Spatter, ModItems.Egg_OliveLight_Spatter, ModItems.Egg_Olive_Spatter, ModItems.Egg_OliveDark_Spatter, ModItems.Egg_Army_Spatter, ModItems.Egg_Mint_Spatter, ModItems.Egg_Green_Spatter, ModItems.Egg_GreenDark_Spatter, ModItems.Egg_Pine_Spatter, ModItems.Egg_PineDark_Spatter, ModItems.Egg_PineBlack_Spatter, ModItems.Egg_Grey_Spatter, ModItems.Egg_GreyGreen_Spatter, ModItems.Egg_Avocado_Spatter, ModItems.Egg_AvocadoDark_Spatter, ModItems.Egg_Feldgrau_Spatter,
//                 ModItems.EGG_MATCHA_SPATTER, ModItems.EGG_MATCHADARK_SPATTER, ModItems.EGG_MOSS_SPATTER, ModItems.EGG_MOSSDARK_SPATTER, ModItems.EGG_GREENUMBER_SPATTER, ModItems.EGG_CELADON_SPATTER, ModItems.EGG_FERN_SPATTER, ModItems.EGG_ASPARAGUS_SPATTER, ModItems.EGG_HUNTER_SPATTER, ModItems.EGG_HUNTERDARK_SPATTER, ModItems.EGG_TREEDARK_SPATTER, ModItems.EGG_GREYNEUTRAL_SPATTER, ModItems.EGG_LAUREL_SPATTER, ModItems.EGG_RESEDA_SPATTER, ModItems.EGG_GREENPEWTER_SPATTER, ModItems.EGG_GREYDARK_SPATTER,
//                 ModItems.EGG_EARTH_SPATTER, ModItems.EGG_KHAKI_SPATTER, ModItems.EGG_GRULLO_SPATTER, ModItems.EGG_KHAKIDARK_SPATTER, ModItems.EGG_CAROB_SPATTER, ModItems.EGG_JADE_SPATTER, ModItems.EGG_PISTACHIO_SPATTER, ModItems.EGG_SAGE_SPATTER, ModItems.EGG_ROSEMARY_SPATTER, ModItems.EGG_GREENBROWN_SPATTER, ModItems.EGG_UMBER_SPATTER, ModItems.EGG_PINKGREY_SPATTER, ModItems.EGG_WARMGREY_SPATTER, ModItems.EGG_ARTICHOKE_SPATTER, ModItems.EGG_MYRTLEGREY_SPATTER, ModItems.EGG_RIFLE_SPATTER};
//
//        final Item[] items = {ModItems.RAWCHICKEN_DARKSMALL, ModItems.RAWCHICKEN_DARKBIG, ModItems.RAWCHICKEN_DARK, ModItems.COOKEDCHICKEN_DARKSMALL, ModItems.COOKEDCHICKEN_DARK,
//                ModItems.COOKEDCHICKEN_DARKBIG, ModItems.RAWCHICKEN_PALESMALL, ModItems.RAWCHICKEN_PALE, ModItems.COOKEDCHICKEN_PALESMALL, ModItems.COOKEDCHICKEN_PALE,
//                ModItems.RAWRABBIT_SMALL, ModItems.COOKEDRABBIT_SMALL, ModItems.RABBITSTEW_WEAK, ModItems.HALF_MILK_BOTTLE, ModItems.MILK_BOTTLE, ModItems.ONESIXTH_MILK_BUCKET,
//                ModItems.ONETHIRD_MILK_BUCKET, ModItems.HALF_MILK_BUCKET, ModItems.TWOTHIRDS_MILK_BUCKET, ModItems.FIVESIXTHS_MILK_BUCKET,
//                ModItems.BRIDLE_BASIC_LEATHER, ModItems.BRIDLE_BASIC_LEATHER_GOLD, ModItems.BRIDLE_BASIC_LEATHER_DIAMOND,
//                ModItems.BRIDLE_BASIC_CLOTH, ModItems.BRIDLE_BASIC_CLOTH_GOLD, ModItems.BRIDLE_BASIC_CLOTH_DIAMOND,
//                ModItems.SADDLE_BASIC_LEATHER, ModItems.SADDLE_BASIC_LEATHER_DIAMOND, ModItems.SADDLE_BASIC_LEATHER_GOLD, ModItems.SADDLE_BASIC_LEATHER_WOOD,
//                ModItems.SADDLE_BASIC_CLOTH, ModItems.SADDLE_BASIC_CLOTH_GOLD, ModItems.SADDLE_BASIC_CLOTH_DIAMOND, ModItems.SADDLE_BASIC_CLOTH_WOOD,
//                ModItems.SADDLE_BASIC_LEATHERCLOTHSEAT, ModItems.SADDLE_BASIC_LEATHERCLOTHSEAT_GOLD, ModItems.SADDLE_BASIC_LEATHERCLOTHSEAT_DIAMOND, ModItems.SADDLE_BASIC_LEATHERCLOTHSEAT_WOOD,
//                ModItems.SADDLE_BASICPOMEL_LEATHER, ModItems.SADDLE_BASICPOMEL_LEATHER_GOLD, ModItems.SADDLE_BASICPOMEL_LEATHER_DIAMOND, ModItems.SADDLE_BASICPOMEL_LEATHER_WOOD,
//                ModItems.SADDLE_BASICPOMEL_CLOTH, ModItems.SADDLE_BASICPOMEL_CLOTH_GOLD, ModItems.SADDLE_BASICPOMEL_CLOTH_DIAMOND, ModItems.SADDLE_BASICPOMEL_CLOTH_WOOD,
//                ModItems.SADDLE_BASICPOMEL_LEATHERCLOTHSEAT, ModItems.SADDLE_BASICPOMEL_LEATHERCLOTHSEAT_GOLD, ModItems.SADDLE_BASICPOMEL_LEATHERCLOTHSEAT_DIAMOND, ModItems.SADDLE_BASICPOMEL_LEATHERCLOTHSEAT_WOOD,
//                ModItems.SADDLE_ENGLISH_LEATHER, ModItems.SADDLE_ENGLISH_LEATHER_GOLD, ModItems.SADDLE_ENGLISH_LEATHER_DIAMOND, ModItems.SADDLE_ENGLISH_LEATHER_WOOD,
//                ModItems.SADDLE_ENGLISH_CLOTH,ModItems.SADDLE_ENGLISH_CLOTH_GOLD, ModItems.SADDLE_ENGLISH_CLOTH_DIAMOND, ModItems.SADDLE_ENGLISH_CLOTH_WOOD,
//                ModItems.SADDLE_ENGLISH_LEATHERCLOTHSEAT, ModItems.SADDLE_ENGLISH_LEATHERCLOTHSEAT_GOLD, ModItems.SADDLE_ENGLISH_LEATHERCLOTHSEAT_DIAMOND, ModItems.SADDLE_ENGLISH_LEATHERCLOTHSEAT_WOOD,
//                ModItems.COLLAR_BASIC_LEATHER, ModItems.COLLAR_BASIC_LEATHER_IRONBELL, ModItems.COLLAR_BASIC_LEATHER_IRONRING, ModItems.COLLAR_BASIC_LEATHER_GOLDBELL, ModItems.COLLAR_BASIC_LEATHER_GOLDRING, ModItems.COLLAR_BASIC_LEATHER_DIAMONDBELL, ModItems.COLLAR_BASIC_LEATHER_DIAMONDRING,
//                ModItems.COLLAR_BASIC_CLOTH, ModItems.COLLAR_BASIC_CLOTH_IRONBELL, ModItems.COLLAR_BASIC_CLOTH_IRONRING, ModItems.COLLAR_BASIC_CLOTH_GOLDBELL, ModItems.COLLAR_BASIC_CLOTH_GOLDRING, ModItems.COLLAR_BASIC_CLOTH_DIAMONDBELL, ModItems.COLLAR_BASIC_CLOTH_DIAMONDRING, ModItems.DEBUG_GENE_BOOK};
//
//        final Item[] itemBlocks = {
//                new BlockItem(ModBlocks.POST_ACACIA, new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP)).setRegistryName(ModBlocks.POST_ACACIA.getRegistryName()),
//                new BlockItem(ModBlocks.POST_BIRCH, new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP)).setRegistryName(ModBlocks.POST_BIRCH.getRegistryName()),
//                new BlockItem(ModBlocks.POST_DARK_OAK, new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP)).setRegistryName(ModBlocks.POST_DARK_OAK.getRegistryName()),
//                new BlockItem(ModBlocks.POST_JUNGLE, new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP)).setRegistryName(ModBlocks.POST_JUNGLE.getRegistryName()),
//                new BlockItem(ModBlocks.POST_OAK, new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP)).setRegistryName(ModBlocks.POST_OAK.getRegistryName()),
//                new BlockItem(ModBlocks.POST_SPRUCE, new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP)).setRegistryName(ModBlocks.POST_SPRUCE.getRegistryName()),
//                new BlockItem(ModBlocks.UNBOUNDHAY_BLOCK, new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP)).setRegistryName(ModBlocks.UNBOUNDHAY_BLOCK.getRegistryName()),
//                new BlockItem(ModBlocks.SPARSEGRASS_BLOCK, new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP)).setRegistryName(ModBlocks.SPARSEGRASS_BLOCK.getRegistryName()),
//                new BlockItem(ModBlocks.PATCHYMYCELIUM_BLOCK, new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP)).setRegistryName(ModBlocks.PATCHYMYCELIUM_BLOCK.getRegistryName()),
//                new BlockItem(ModBlocks.GROWABLE_ALLIUM, new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP)).setRegistryName(ModBlocks.GROWABLE_ALLIUM.getRegistryName()),
//                new BlockItem(ModBlocks.GROWABLE_AZURE_BLUET, new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP)).setRegistryName(ModBlocks.GROWABLE_AZURE_BLUET.getRegistryName()),
//                new BlockItem(ModBlocks.GROWABLE_BLUE_ORCHID, new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP)).setRegistryName(ModBlocks.GROWABLE_BLUE_ORCHID.getRegistryName()),
//                new BlockItem(ModBlocks.GROWABLE_CORNFLOWER, new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP)).setRegistryName(ModBlocks.GROWABLE_CORNFLOWER.getRegistryName()),
//                new BlockItem(ModBlocks.GROWABLE_DANDELION, new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP)).setRegistryName(ModBlocks.GROWABLE_DANDELION.getRegistryName()),
//                new BlockItem(ModBlocks.GROWABLE_OXEYE_DAISY, new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP)).setRegistryName(ModBlocks.GROWABLE_OXEYE_DAISY.getRegistryName()),
//                new BlockItem(ModBlocks.GROWABLE_GRASS, new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP)).setRegistryName(ModBlocks.GROWABLE_GRASS.getRegistryName()),
//                new BlockItem(ModBlocks.GROWABLE_FERN, new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP)).setRegistryName(ModBlocks.GROWABLE_FERN.getRegistryName()),
//                new BlockItem(ModBlocks.GROWABLE_ROSE_BUSH, new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP)).setRegistryName(ModBlocks.GROWABLE_ROSE_BUSH.getRegistryName()),
//                new BlockItem(ModBlocks.GROWABLE_SUNFLOWER, new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP)).setRegistryName(ModBlocks.GROWABLE_SUNFLOWER.getRegistryName()),
//                new BlockItem(ModBlocks.GROWABLE_TALL_GRASS, new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP)).setRegistryName(ModBlocks.GROWABLE_TALL_GRASS.getRegistryName()),
//                new BlockItem(ModBlocks.GROWABLE_LARGE_FERN, new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP)).setRegistryName(ModBlocks.GROWABLE_LARGE_FERN.getRegistryName()),
//                new BlockItem(TURTLE_EGG, new Item.Properties().stacksTo(1).tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP)).setRegistryName(TURTLE_EGG.getRegistryName()),
//                new BlockItem(EGG_CARTON, new Item.Properties().stacksTo(1).tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP)).setRegistryName(EGG_CARTON.getRegistryName()),
//        };
//
//        event.getRegistry().register(new ForgeSpawnEggItem(ENHANCED_TURTLE, 0xFFFFDD,0x00DDCC, new Item.Properties()
//                .tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP)).setRegistryName("enhanced_turtle_spawn_egg"));
//        event.getRegistry().register(new ForgeSpawnEggItem(ENHANCED_CHICKEN, 0xFFFCF0,0xCC0000, new Item.Properties()
//                .tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP)).setRegistryName("enhanced_chicken_spawn_egg"));
//        event.getRegistry().register(new ForgeSpawnEggItem(ENHANCED_LLAMA, 0xCDB29C,0x7B4B34, new Item.Properties()
//                .tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP)).setRegistryName("enhanced_llama_spawn_egg"));
//        event.getRegistry().register(new ForgeSpawnEggItem(ENHANCED_SHEEP, 0xFFFFFF,0xFF8C8C, new Item.Properties()
//                .tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP)).setRegistryName("enhanced_sheep_spawn_egg"));
//        event.getRegistry().register(new ForgeSpawnEggItem(ENHANCED_RABBIT, 0xCA8349,0x553C36, new Item.Properties()
//                .tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP)).setRegistryName("enhanced_rabbit_spawn_egg"));
//        event.getRegistry().register(new ForgeSpawnEggItem(ENHANCED_COW, 0x260800,0xf9f9f7, new Item.Properties()
//                .tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP)).setRegistryName("enhanced_cow_spawn_egg"));
//        event.getRegistry().register(new ForgeSpawnEggItem(ENHANCED_MOOSHROOM, 0xFF0000,0xCCCCCC, new Item.Properties()
//                .tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP)).setRegistryName("enhanced_mooshroom_spawn_egg"));
//        event.getRegistry().register(new ForgeSpawnEggItem(ENHANCED_PIG, 0xFFA4A4,0xB34d4d, new Item.Properties()
//                .tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP)).setRegistryName("enhanced_pig_spawn_egg"));


//        event.getRegistry().registerAll(itemEggs);
//        event.getRegistry().registerAll(items);
//        event.getRegistry().registerAll(itemBlocks);
//
//        for (Item egg : itemEggs) {
//            DispenserBlock.registerBehavior(egg,  new AbstractProjectileDispenseBehavior() {
//                /**
//                 * Return the projectile entity spawned by this dispense behavior.
//                 */
//                protected Projectile getProjectile(Level worldIn, Position position, ItemStack stackIn) {
//                    EnhancedEntityEgg eggItem = new EnhancedEntityEgg(worldIn, position.x(), position.y(), position.z(), egg);
//                    eggItem.setEggData(stackIn.getCapability(EggCapabilityProvider.EGG_CAP, null).orElse(null).getEggHolder(stackIn));
//                    return eggItem;
//                }
//            });
//        }
//
//        DispenserBlock.registerBehavior(Items.SHEARS.asItem(), new GeneticShearDispenseBehavior());

        //TODO dispensers should be able to turn hay to unbound hay if they contain a sharp tool and are facing a hay block
    }

    @SubscribeEvent
    public static void onCapabilitiesRegistry(RegisterCapabilitiesEvent event) {
        event.register(IPostCapability.class);
        event.register(IHayCapability.class);
        event.register(IEggCapability.class);
        event.register(INestEggCapability.class);
    }

    @SubscribeEvent
    public static void onEntityAttributeCreationRegistry(EntityAttributeCreationEvent event) {
        event.put(ENHANCED_AXOLOTL.get(), EnhancedAxolotl.prepareAttributes().build());
//        event.put(ENHANCED_CHICKEN.get(), EnhancedChicken.prepareAttributes().build());
//        event.put(ENHANCED_RABBIT.get(), EnhancedRabbit.prepareAttributes().build());
//        event.put(ENHANCED_SHEEP.get(), EnhancedSheep.prepareAttributes().build());
//        event.put(ENHANCED_LLAMA.get(), EnhancedLlama.prepareAttributes().build());
//        event.put(ENHANCED_COW.get(), EnhancedCow.prepareAttributes().build());
//        event.put(ENHANCED_MOOSHROOM.get(), EnhancedMooshroom.prepareAttributes().build());
//        event.put(ENHANCED_MOOBLOOM.get(), EnhancedMoobloom.prepareAttributes().build());
//        event.put(ENHANCED_PIG.get(), EnhancedPig.prepareAttributes().build());
//        event.put(ENHANCED_HORSE.get(), EnhancedHorse.prepareAttributes().build());
//        event.put(ENHANCED_TURTLE.get(), EnhancedTurtle.prepareAttributes().build());

    }

//    @SubscribeEvent
//    public static void onTileEntitiesRegistry(final RegistryEvent.Register<BlockEntityType<?>> event) {
//        event.getRegistry().register(ModTileEntities.EGG_CARTON_TILE_ENTITY.setRegistryName("egg_carton_tile_entity"));
//    }

    @SubscribeEvent
    public static void onContainerTypeRegistry(final RegistryEvent.Register<MenuType<?>> event) {
//        event.getRegistry().register(EGG_CARTON_CONTAINER.setRegistryName("egg_carton_container_box"));
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
//        SpawnPlacements.register(ENHANCED_AXOLOTL.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::checkAnimalSpawnRules);
//        SpawnPlacements.register(ENHANCED_PIG.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::checkAnimalSpawnRules);
//        SpawnPlacements.register(ENHANCED_SHEEP.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::checkAnimalSpawnRules);
//        SpawnPlacements.register(ENHANCED_COW.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::checkAnimalSpawnRules);
//        SpawnPlacements.register(ENHANCED_LLAMA.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::checkAnimalSpawnRules);
//        SpawnPlacements.register(ENHANCED_CHICKEN.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::checkAnimalSpawnRules);
//        SpawnPlacements.register(ENHANCED_RABBIT.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::checkAnimalSpawnRules);
//        SpawnPlacements.register(ENHANCED_MOOSHROOM.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EnhancedMooshroom::canMooshroomSpawn);
//        SpawnPlacements.register(ENHANCED_TURTLE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EnhancedTurtle::canTurtleSpawn);
    }

    private static class GeneticShearDispenseBehavior extends ShearsDispenseItemBehavior {

        @Override
        protected ItemStack execute(BlockSource source, ItemStack stack) {
            Level world = source.getLevel();
            if (!world.isClientSide()) {
                BlockPos blockpos = source.getPos().relative(source.getBlockState().getValue(DispenserBlock.FACING));
                this.setSuccess(shearGeneticAnimals((ServerLevel)world, blockpos));
                if (this.isSuccess() && stack.hurt(1, world.getRandom(), (ServerPlayer)null)) {
                    stack.setCount(0);
                }
                if (this.isSuccess()) {
                    return stack;
                }
            }

            return super.execute(source, stack);
        }

        private boolean shearGeneticAnimals(ServerLevel world, BlockPos pos) {
            for(LivingEntity livingentity : world.getEntitiesOfClass(LivingEntity.class, new AABB(pos), EntitySelector.NO_SPECTATORS)) {
                if (livingentity instanceof EnhancedAnimalAbstract && livingentity instanceof IForgeShearable) {
                    IForgeShearable ishearable = (IForgeShearable)livingentity;
                    if (ishearable.isShearable(ItemStack.EMPTY, world, pos)) {
                        List<ItemStack> wool = ishearable.onSheared(null, ItemStack.EMPTY, world, pos, 0);
                        wool.forEach(d -> {
                            net.minecraft.world.entity.item.ItemEntity ent = livingentity.spawnAtLocation(d, 1.0F);
                            ent.setDeltaMovement(ent.getDeltaMovement().add((double)(ThreadLocalRandom.current().nextFloat() * 0.1F), (double)(ThreadLocalRandom.current().nextFloat() * 0.05F), (double)((ThreadLocalRandom.current().nextFloat()) * 0.1F)));
                        });
                        return true;
                    }
                }
            }

            return false;
        }

    }

}
