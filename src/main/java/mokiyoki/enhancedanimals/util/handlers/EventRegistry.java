package mokiyoki.enhancedanimals.util.handlers;


import mokiyoki.enhancedanimals.EnhancedAnimals;
import mokiyoki.enhancedanimals.blocks.EggCartonContainer;
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
import mokiyoki.enhancedanimals.entity.EnhancedChicken;
import mokiyoki.enhancedanimals.entity.EnhancedEntityEgg;
import mokiyoki.enhancedanimals.entity.EnhancedEntityLlamaSpit;
import mokiyoki.enhancedanimals.entity.EnhancedHorse;
import mokiyoki.enhancedanimals.entity.EnhancedMoobloom;
import mokiyoki.enhancedanimals.entity.EnhancedMooshroom;
import mokiyoki.enhancedanimals.entity.EnhancedRabbit;
import mokiyoki.enhancedanimals.entity.EnhancedLlama;
import mokiyoki.enhancedanimals.entity.EnhancedCow;
import mokiyoki.enhancedanimals.entity.EnhancedPig;
//import mokiyoki.enhancedanimals.entity.EnhancedSheep;
import mokiyoki.enhancedanimals.entity.EnhancedTurtle;
import mokiyoki.enhancedanimals.gui.EnhancedAnimalContainer;
import mokiyoki.enhancedanimals.init.ModBlocks;
import mokiyoki.enhancedanimals.init.ModItems;
import mokiyoki.enhancedanimals.init.ModTileEntities;
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

import static mokiyoki.enhancedanimals.init.ModBlocks.EGG_CARTON;
import static mokiyoki.enhancedanimals.init.ModBlocks.TURTLE_EGG;
import static mokiyoki.enhancedanimals.init.ModTileEntities.EGG_CARTON_TILE_ENTITY;

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

    public static final RegistryObject<EntityType<EnhancedEntityLlamaSpit>> ENHANCED_LLAMA_SPIT = ENTITIES_DEFERRED_REGISTRY.register("enhanced_entity_llama_spit", () -> EntityType.Builder.<EnhancedEntityLlamaSpit>of(EnhancedEntityLlamaSpit::new, MobCategory.MISC).sized(0.25F, 0.25F).build(Reference.MODID + ":enhanced_entity_llama_spit"));
    public static final RegistryObject<EntityType<EnhancedEntityEgg>> ENHANCED_ENTITY_EGG_ENTITY_TYPE = ENTITIES_DEFERRED_REGISTRY.register("enhanced_entity_egg", () -> EntityType.Builder.<EnhancedEntityEgg>of(EnhancedEntityEgg::new, MobCategory.MISC).sized(0.25F, 0.25F).build(Reference.MODID + ":enhanced_entity_egg"));
    public static final RegistryObject<EntityType<EnhancedChicken>> ENHANCED_CHICKEN = ENTITIES_DEFERRED_REGISTRY.register("enhanced_chicken", () -> EntityType.Builder.of(EnhancedChicken::new, MobCategory.CREATURE).sized(0.4F, 0.7F).setTrackingRange(64).setUpdateInterval(1).build(Reference.MODID + ":enhanced_chicken"));
    public static final RegistryObject<EntityType<EnhancedRabbit>> ENHANCED_RABBIT = ENTITIES_DEFERRED_REGISTRY.register("enhanced_rabbit", () -> EntityType.Builder.of(EnhancedRabbit::new, MobCategory.CREATURE).sized(0.4F, 0.5F).build(Reference.MODID + ":enhanced_rabbit"));
//    public static final RegistryObject<EntityType<EnhancedSheep>> ENHANCED_SHEEP = ENTITIES_DEFERRED_REGISTRY.register("enhanced_sheep", () -> EntityType.Builder.of(EnhancedSheep::new, MobCategory.CREATURE).sized(0.9F, 1.3F).build(Reference.MODID + ":enhanced_sheep"));
    public static final RegistryObject<EntityType<EnhancedLlama>> ENHANCED_LLAMA = ENTITIES_DEFERRED_REGISTRY.register("enhanced_llama", () -> EntityType.Builder.of(EnhancedLlama::new, MobCategory.CREATURE).sized(0.9F, 1.87F).build(Reference.MODID + ":enhanced_llama"));
    public static final RegistryObject<EntityType<EnhancedCow>> ENHANCED_COW = ENTITIES_DEFERRED_REGISTRY.register("enhanced_cow", () -> EntityType.Builder.of(EnhancedCow::new, MobCategory.CREATURE).sized(1.0F, 1.5F).build(Reference.MODID + ":enhanced_cow"));
    public static final RegistryObject<EntityType<EnhancedMooshroom>> ENHANCED_MOOSHROOM = ENTITIES_DEFERRED_REGISTRY.register("enhanced_mooshroom", () -> EntityType.Builder.of(EnhancedMooshroom::new, MobCategory.CREATURE).sized(1.0F, 1.5F).build(Reference.MODID + ":enhanced_mooshroom"));
    public static final RegistryObject<EntityType<EnhancedMoobloom>> ENHANCED_MOOBLOOM = ENTITIES_DEFERRED_REGISTRY.register("enhanced_moobloom", () -> EntityType.Builder.of(EnhancedMoobloom::new, MobCategory.CREATURE).sized(1.0F, 1.5F).build(Reference.MODID + ":enhanced_moobloom"));
    public static final RegistryObject<EntityType<EnhancedPig>> ENHANCED_PIG = ENTITIES_DEFERRED_REGISTRY.register("enhanced_pig", () -> EntityType.Builder.of(EnhancedPig::new, MobCategory.CREATURE).sized(0.9F, 0.9F).build(Reference.MODID + ":enhanced_pig"));
    public static final RegistryObject<EntityType<EnhancedHorse>> ENHANCED_HORSE = ENTITIES_DEFERRED_REGISTRY.register("enhanced_horse", () -> EntityType.Builder.of(EnhancedHorse::new, MobCategory.CREATURE).sized(1.0F, 1.6F).build(Reference.MODID + ":enhanced_horse"));
    public static final RegistryObject<EntityType<EnhancedTurtle>> ENHANCED_TURTLE = ENTITIES_DEFERRED_REGISTRY.register("enhanced_turtle", () -> EntityType.Builder.of(EnhancedTurtle::new, MobCategory.CREATURE).sized(1.2F, 0.4F).build(Reference.MODID + ":enhanced_turtle"));
    public static final RegistryObject<EntityType<EnhancedAxolotl>> ENHANCED_AXOLOTL = ENTITIES_DEFERRED_REGISTRY.register("enhanced_axolotl", () -> EntityType.Builder.of(EnhancedAxolotl::new, MobCategory.CREATURE).sized(1.2F, 0.4F).build(Reference.MODID + ":enhanced_axolotl"));
//    public static final EntityType<EnhancedCat> ENHANCED_CAT = EntityType.Builder.create(EnhancedCat::new, EntityClassification.CREATURE).size(0.6F, 0.7F).build(Reference.MODID + ":enhanced_cat");
//    public static final EntityType<EnhancedBee> ENHANCED_BEE = EntityType.Builder.create(EnhancedBee::new, EntityClassification.CREATURE).size(0.4F, 0.4F).build(Reference.MODID + ":enhanced_bee");

    public static final MenuType<EggCartonContainer> EGG_CARTON_CONTAINER = IForgeMenuType.create(EggCartonContainer::new);
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
//            new ForgeSpawnEggItem(ENHANCED_AXOLOTL, 0xFFFFDD,0x00DDCC.get(), new Item.Properties()
//                    .tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP))
//    );

    @SubscribeEvent
    public static void onRegisterItems(final RegistryEvent.Register<Item> event) {
        final Item[] itemEggs = {ModItems.EGG_WHITE.get(), ModItems.EGG_CREAMLIGHT.get(), ModItems.EGG_CREAM.get(), ModItems.EGG_CREAMDARK.get(), ModItems.EGG_CREAMDARKEST.get(), ModItems.EGG_CARMELDARK.get(), ModItems.EGG_GARNET.get(), ModItems.EGG_PINKLIGHT.get(), ModItems.EGG_PINK.get(), ModItems.EGG_PINKDARK.get(), ModItems.EGG_PINKDARKEST.get(), ModItems.EGG_CHERRYDARK.get(), ModItems.EGG_PLUM.get(), ModItems.EGG_BROWNLIGHT.get(), ModItems.EGG_BROWN.get(), ModItems.EGG_BROWNDARK.get(), ModItems.EGG_CHOCOLATE.get(), ModItems.EGG_CHOCOLATEDARK.get(), ModItems.EGG_CHOCOLATECOSMOS.get(),
                 ModItems.EGG_BLUE.get(), ModItems.EGG_GREENLIGHT.get(), ModItems.EGG_GREENYELLOW.get(), ModItems.EGG_OLIVELIGHT.get(), ModItems.EGG_OLIVE.get(), ModItems.EGG_OLIVEDARK.get(), ModItems.EGG_ARMY.get(), ModItems.EGG_MINT.get(), ModItems.EGG_GREEN.get(), ModItems.EGG_GREENDARK.get(), ModItems.EGG_PINE.get(), ModItems.EGG_PINEDARK.get(), ModItems.EGG_PINEBLACK.get(), ModItems.EGG_BLUEGREY.get(), ModItems.EGG_GREY.get(), ModItems.EGG_GREYGREEN.get(), ModItems.EGG_AVOCADO.get(), ModItems.EGG_AVOCADODARK.get(), ModItems.EGG_FELDGRAU.get(),
                 ModItems.EGG_POWDERBLUE.get(), ModItems.EGG_TEA.get(), ModItems.EGG_MATCHA.get(), ModItems.EGG_MATCHADARK.get(), ModItems.EGG_MOSS.get(), ModItems.EGG_MOSSDARK.get(), ModItems.EGG_GREENUMBER.get(), ModItems.EGG_CELADON.get(), ModItems.EGG_FERN.get(), ModItems.EGG_ASPARAGUS.get(), ModItems.EGG_HUNTER.get(), ModItems.EGG_HUNTERDARK.get(), ModItems.EGG_TREEDARK.get(), ModItems.EGG_GREYBLUE.get(), ModItems.EGG_GREYNEUTRAL.get(), ModItems.EGG_LAUREL.get(), ModItems.EGG_RESEDA.get(), ModItems.EGG_GREENPEWTER.get(), ModItems.EGG_GREYDARK.get(),
                 ModItems.EGG_PALEBLUE.get(), ModItems.EGG_HONEYDEW.get(), ModItems.EGG_EARTH.get(), ModItems.EGG_KHAKI.get(), ModItems.EGG_GRULLO.get(), ModItems.EGG_KHAKIDARK.get(), ModItems.EGG_CAROB.get(), ModItems.EGG_JADE.get(), ModItems.EGG_PISTACHIO.get(), ModItems.EGG_SAGE.get(), ModItems.EGG_ROSEMARY.get(), ModItems.EGG_GREENBROWN.get(), ModItems.EGG_UMBER.get(), ModItems.EGG_COOLGREY.get(), ModItems.EGG_PINKGREY.get(), ModItems.EGG_WARMGREY.get(), ModItems.EGG_ARTICHOKE.get(), ModItems.EGG_MYRTLEGREY.get(), ModItems.EGG_RIFLE.get(),
                 ModItems.Egg_Cream_Spot.get(), ModItems.Egg_CreamDark_Spot.get(), ModItems.Egg_Carmel_Spot.get(), ModItems.Egg_CarmelDark_Spot.get(), ModItems.Egg_Garnet_Spot.get(), ModItems.Egg_Pink_Spot.get(), ModItems.Egg_PinkDark_Spot.get(), ModItems.Egg_Cherry_Spot.get(), ModItems.Egg_CherryDark_Spot.get(), ModItems.Egg_Plum_Spot.get(), ModItems.Egg_BrownLight_Spot.get(), ModItems.Egg_Brown_Spot.get(), ModItems.Egg_BrownDark_Spot.get(), ModItems.Egg_Chocolate_Spot.get(), ModItems.Egg_ChocolateDark_Spot.get(),
                 ModItems.Egg_GreenYellow_Spot.get(), ModItems.Egg_OliveLight_Spot.get(), ModItems.Egg_Olive_Spot.get(), ModItems.Egg_OliveDark_Spot.get(), ModItems.Egg_Army_Spot.get(), ModItems.Egg_Mint_Spot.get(), ModItems.Egg_Green_Spot.get(), ModItems.Egg_GreenDark_Spot.get(), ModItems.Egg_Pine_Spot.get(), ModItems.Egg_PineDark_Spot.get(), ModItems.Egg_PineBlack_Spot.get(), ModItems.Egg_Grey_Spot.get(), ModItems.Egg_GreyGreen_Spot.get(), ModItems.Egg_Avocado_Spot.get(), ModItems.Egg_AvocadoDark_Spot.get(), ModItems.Egg_Feldgrau_Spot.get(),
                 ModItems.Egg_Matcha_Spot.get(), ModItems.Egg_MatchaDark_Spot.get(), ModItems.EGG_MOSS_SPOT.get(), ModItems.EGG_MOSSDARK_SPOT.get(), ModItems.EGG_GREENUMBER_SPOT.get(), ModItems.EGG_CELADON_SPOT.get(), ModItems.EGG_FERN_SPOT.get(), ModItems.EGG_ASPARAGUS_SPOT.get(), ModItems.EGG_HUNTER_SPOT.get(), ModItems.EGG_HUNTERDARK_SPOT.get(), ModItems.EGG_TREEDARK_SPOT.get(), ModItems.EGG_GREYNEUTRAL_SPOT.get(), ModItems.EGG_LAUREL_SPOT.get(), ModItems.EGG_RESEDA_SPOT.get(), ModItems.EGG_GREENPEWTER_SPOT.get(), ModItems.EGG_GREYDARK_SPOT.get(),
                 ModItems.EGG_EARTH_SPOT.get(), ModItems.EGG_KHAKI_SPOT.get(), ModItems.EGG_GRULLO_SPOT.get(), ModItems.EGG_KHAKIDARK_SPOT.get(), ModItems.EGG_CAROB_SPOT.get(), ModItems.EGG_JADE_SPOT.get(), ModItems.EGG_PISTACHIO_SPOT.get(), ModItems.EGG_SAGE_SPOT.get(), ModItems.EGG_ROSEMARY_SPOT.get(), ModItems.EGG_GREENBROWN_SPOT.get(), ModItems.EGG_UMBER_SPOT.get(), ModItems.EGG_PINKGREY_SPOT.get(), ModItems.EGG_WARMGREY_SPOT.get(), ModItems.EGG_ARTICHOKE_SPOT.get(), ModItems.EGG_MYRTLEGREY_SPOT.get(), ModItems.EGG_RIFLE_SPOT.get(),
                 ModItems.Egg_Cream_Speckle.get(), ModItems.Egg_CreamDark_Speckle.get(), ModItems.Egg_Carmel_Speckle.get(), ModItems.Egg_CarmelDark_Speckle.get(), ModItems.Egg_Garnet_Speckle.get(), ModItems.Egg_Pink_Speckle.get(), ModItems.Egg_PinkDark_Speckle.get(), ModItems.Egg_Cherry_Speckle.get(), ModItems.Egg_CherryDark_Speckle.get(), ModItems.Egg_Plum_Speckle.get(), ModItems.Egg_BrownLight_Speckle.get(), ModItems.Egg_Brown_Speckle.get(), ModItems.Egg_BrownDark_Speckle.get(), ModItems.Egg_Chocolate_Speckle.get(), ModItems.Egg_ChocolateDark_Speckle.get(),
                 ModItems.Egg_GreenYellow_Speckle.get(), ModItems.Egg_OliveLight_Speckle.get(), ModItems.Egg_Olive_Speckle.get(), ModItems.Egg_OliveDark_Speckle.get(), ModItems.Egg_Army_Speckle.get(), ModItems.Egg_Mint_Speckle.get(), ModItems.Egg_Green_Speckle.get(), ModItems.Egg_GreenDark_Speckle.get(), ModItems.Egg_Pine_Speckle.get(), ModItems.Egg_PineDark_Speckle.get(), ModItems.Egg_PineBlack_Speckle.get(), ModItems.Egg_Grey_Speckle.get(), ModItems.Egg_GreyGreen_Speckle.get(), ModItems.Egg_Avocado_Speckle.get(), ModItems.Egg_AvocadoDark_Speckle.get(), ModItems.Egg_Feldgrau_Speckle.get(),
                 ModItems.EGG_MATCHA_SPECKLE.get(), ModItems.EGG_MATCHADARK_SPECKLE.get(), ModItems.EGG_MOSS_SPECKLE.get(), ModItems.EGG_MOSSDARK_SPECKLE.get(), ModItems.EGG_GREENUMBER_SPECKLE.get(), ModItems.EGG_CELADON_SPECKLE.get(), ModItems.EGG_FERN_SPECKLE.get(), ModItems.EGG_ASPARAGUS_SPECKLE.get(), ModItems.EGG_HUNTER_SPECKLE.get(), ModItems.EGG_HUNTERDARK_SPECKLE.get(), ModItems.EGG_TREEDARK_SPECKLE.get(), ModItems.EGG_GREYNEUTRAL_SPECKLE.get(), ModItems.EGG_LAUREL_SPECKLE.get(), ModItems.EGG_RESEDA_SPECKLE.get(), ModItems.EGG_GREENPEWTER_SPECKLE.get(), ModItems.EGG_GREYDARK_SPECKLE.get(),
                 ModItems.EGG_EARTH_SPECKLE.get(), ModItems.EGG_KHAKI_SPECKLE.get(), ModItems.EGG_GRULLO_SPECKLE.get(), ModItems.EGG_KHAKIDARK_SPECKLE.get(), ModItems.EGG_CAROB_SPECKLE.get(), ModItems.EGG_JADE_SPECKLE.get(), ModItems.EGG_PISTACHIO_SPECKLE.get(), ModItems.EGG_SAGE_SPECKLE.get(), ModItems.EGG_ROSEMARY_SPECKLE.get(), ModItems.EGG_GREENBROWN_SPECKLE.get(), ModItems.EGG_UMBER_SPECKLE.get(), ModItems.EGG_PINKGREY_SPECKLE.get(), ModItems.EGG_WARMGREY_SPECKLE.get(), ModItems.EGG_ARTICHOKE_SPECKLE.get(), ModItems.EGG_MYRTLEGREY_SPECKLE.get(), ModItems.EGG_RIFLE_SPECKLE.get(),
                 ModItems.Egg_Cream_Spatter.get(), ModItems.Egg_CreamDark_Spatter.get(), ModItems.Egg_Carmel_Spatter.get(), ModItems.Egg_CarmelDark_Spatter.get(), ModItems.Egg_Garnet_Spatter.get(), ModItems.Egg_Pink_Spatter.get(), ModItems.Egg_PinkDark_Spatter.get(), ModItems.Egg_Cherry_Spatter.get(), ModItems.Egg_CherryDark_Spatter.get(), ModItems.Egg_Plum_Spatter.get(), ModItems.Egg_BrownLight_Spatter.get(), ModItems.Egg_Brown_Spatter.get(), ModItems.Egg_BrownDark_Spatter.get(), ModItems.Egg_Chocolate_Spatter.get(), ModItems.Egg_ChocolateDark_Spatter.get(),
                 ModItems.Egg_GreenYellow_Spatter.get(), ModItems.Egg_OliveLight_Spatter.get(), ModItems.Egg_Olive_Spatter.get(), ModItems.Egg_OliveDark_Spatter.get(), ModItems.Egg_Army_Spatter.get(), ModItems.Egg_Mint_Spatter.get(), ModItems.Egg_Green_Spatter.get(), ModItems.Egg_GreenDark_Spatter.get(), ModItems.Egg_Pine_Spatter.get(), ModItems.Egg_PineDark_Spatter.get(), ModItems.Egg_PineBlack_Spatter.get(), ModItems.Egg_Grey_Spatter.get(), ModItems.Egg_GreyGreen_Spatter.get(), ModItems.Egg_Avocado_Spatter.get(), ModItems.Egg_AvocadoDark_Spatter.get(), ModItems.Egg_Feldgrau_Spatter.get(),
                 ModItems.EGG_MATCHA_SPATTER.get(), ModItems.EGG_MATCHADARK_SPATTER.get(), ModItems.EGG_MOSS_SPATTER.get(), ModItems.EGG_MOSSDARK_SPATTER.get(), ModItems.EGG_GREENUMBER_SPATTER.get(), ModItems.EGG_CELADON_SPATTER.get(), ModItems.EGG_FERN_SPATTER.get(), ModItems.EGG_ASPARAGUS_SPATTER.get(), ModItems.EGG_HUNTER_SPATTER.get(), ModItems.EGG_HUNTERDARK_SPATTER.get(), ModItems.EGG_TREEDARK_SPATTER.get(), ModItems.EGG_GREYNEUTRAL_SPATTER.get(), ModItems.EGG_LAUREL_SPATTER.get(), ModItems.EGG_RESEDA_SPATTER.get(), ModItems.EGG_GREENPEWTER_SPATTER.get(), ModItems.EGG_GREYDARK_SPATTER.get(),
                 ModItems.EGG_EARTH_SPATTER.get(), ModItems.EGG_KHAKI_SPATTER.get(), ModItems.EGG_GRULLO_SPATTER.get(), ModItems.EGG_KHAKIDARK_SPATTER.get(), ModItems.EGG_CAROB_SPATTER.get(), ModItems.EGG_JADE_SPATTER.get(), ModItems.EGG_PISTACHIO_SPATTER.get(), ModItems.EGG_SAGE_SPATTER.get(), ModItems.EGG_ROSEMARY_SPATTER.get(), ModItems.EGG_GREENBROWN_SPATTER.get(), ModItems.EGG_UMBER_SPATTER.get(), ModItems.EGG_PINKGREY_SPATTER.get(), ModItems.EGG_WARMGREY_SPATTER.get(), ModItems.EGG_ARTICHOKE_SPATTER.get(), ModItems.EGG_MYRTLEGREY_SPATTER.get(), ModItems.EGG_RIFLE_SPATTER.get()};

        final Item[] items = {ModItems.RAWCHICKEN_DARKSMALL.get(), ModItems.RAWCHICKEN_DARKBIG.get(), ModItems.RAWCHICKEN_DARK.get(), ModItems.COOKEDCHICKEN_DARKSMALL.get(), ModItems.COOKEDCHICKEN_DARK.get(),
                ModItems.COOKEDCHICKEN_DARKBIG.get(), ModItems.RAWCHICKEN_PALESMALL.get(), ModItems.RAWCHICKEN_PALE.get(), ModItems.COOKEDCHICKEN_PALESMALL.get(), ModItems.COOKEDCHICKEN_PALE.get(),
                ModItems.RAWRABBIT_SMALL.get(), ModItems.COOKEDRABBIT_SMALL.get(), ModItems.RABBITSTEW_WEAK.get(), ModItems.HALF_MILK_BOTTLE.get(), ModItems.MILK_BOTTLE.get(), ModItems.ONESIXTH_MILK_BUCKET.get(),
                ModItems.ONETHIRD_MILK_BUCKET.get(), ModItems.HALF_MILK_BUCKET.get(), ModItems.TWOTHIRDS_MILK_BUCKET.get(), ModItems.FIVESIXTHS_MILK_BUCKET.get(),
                ModItems.BRIDLE_BASIC_LEATHER.get(), ModItems.BRIDLE_BASIC_LEATHER_GOLD.get(), ModItems.BRIDLE_BASIC_LEATHER_DIAMOND.get(),
                ModItems.BRIDLE_BASIC_CLOTH.get(), ModItems.BRIDLE_BASIC_CLOTH_GOLD.get(), ModItems.BRIDLE_BASIC_CLOTH_DIAMOND.get(),
                ModItems.SADDLE_BASIC_LEATHER.get(), ModItems.SADDLE_BASIC_LEATHER_DIAMOND.get(), ModItems.SADDLE_BASIC_LEATHER_GOLD.get(), ModItems.SADDLE_BASIC_LEATHER_WOOD.get(),
                ModItems.SADDLE_BASIC_CLOTH.get(), ModItems.SADDLE_BASIC_CLOTH_GOLD.get(), ModItems.SADDLE_BASIC_CLOTH_DIAMOND.get(), ModItems.SADDLE_BASIC_CLOTH_WOOD.get(),
                ModItems.SADDLE_BASIC_LEATHERCLOTHSEAT.get(), ModItems.SADDLE_BASIC_LEATHERCLOTHSEAT_GOLD.get(), ModItems.SADDLE_BASIC_LEATHERCLOTHSEAT_DIAMOND.get(), ModItems.SADDLE_BASIC_LEATHERCLOTHSEAT_WOOD.get(),
                ModItems.SADDLE_BASICPOMEL_LEATHER.get(), ModItems.SADDLE_BASICPOMEL_LEATHER_GOLD.get(), ModItems.SADDLE_BASICPOMEL_LEATHER_DIAMOND.get(), ModItems.SADDLE_BASICPOMEL_LEATHER_WOOD.get(),
                ModItems.SADDLE_BASICPOMEL_CLOTH.get(), ModItems.SADDLE_BASICPOMEL_CLOTH_GOLD.get(), ModItems.SADDLE_BASICPOMEL_CLOTH_DIAMOND.get(), ModItems.SADDLE_BASICPOMEL_CLOTH_WOOD.get(),
                ModItems.SADDLE_BASICPOMEL_LEATHERCLOTHSEAT.get(), ModItems.SADDLE_BASICPOMEL_LEATHERCLOTHSEAT_GOLD.get(), ModItems.SADDLE_BASICPOMEL_LEATHERCLOTHSEAT_DIAMOND.get(), ModItems.SADDLE_BASICPOMEL_LEATHERCLOTHSEAT_WOOD.get(),
                ModItems.SADDLE_ENGLISH_LEATHER.get(), ModItems.SADDLE_ENGLISH_LEATHER_GOLD.get(), ModItems.SADDLE_ENGLISH_LEATHER_DIAMOND.get(), ModItems.SADDLE_ENGLISH_LEATHER_WOOD.get(),
                ModItems.SADDLE_ENGLISH_CLOTH.get(), ModItems.SADDLE_ENGLISH_CLOTH_GOLD.get(), ModItems.SADDLE_ENGLISH_CLOTH_DIAMOND.get(), ModItems.SADDLE_ENGLISH_CLOTH_WOOD.get(),
                ModItems.SADDLE_ENGLISH_LEATHERCLOTHSEAT.get(), ModItems.SADDLE_ENGLISH_LEATHERCLOTHSEAT_GOLD.get(), ModItems.SADDLE_ENGLISH_LEATHERCLOTHSEAT_DIAMOND.get(), ModItems.SADDLE_ENGLISH_LEATHERCLOTHSEAT_WOOD.get(),
                ModItems.COLLAR_BASIC_LEATHER.get(), ModItems.COLLAR_BASIC_LEATHER_IRONBELL.get(), ModItems.COLLAR_BASIC_LEATHER_IRONRING.get(), ModItems.COLLAR_BASIC_LEATHER_GOLDBELL.get(), ModItems.COLLAR_BASIC_LEATHER_GOLDRING.get(), ModItems.COLLAR_BASIC_LEATHER_DIAMONDBELL.get(), ModItems.COLLAR_BASIC_LEATHER_DIAMONDRING.get(),
                ModItems.COLLAR_BASIC_CLOTH.get(), ModItems.COLLAR_BASIC_CLOTH_IRONBELL.get(), ModItems.COLLAR_BASIC_CLOTH_IRONRING.get(), ModItems.COLLAR_BASIC_CLOTH_GOLDBELL.get(), ModItems.COLLAR_BASIC_CLOTH_GOLDRING.get(), ModItems.COLLAR_BASIC_CLOTH_DIAMONDBELL.get(), ModItems.COLLAR_BASIC_CLOTH_DIAMONDRING.get(), ModItems.DEBUG_GENE_BOOK.get()};

        final Item[] itemBlocks = {
                new BlockItem(ModBlocks.POST_ACACIA.get(), new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP)).setRegistryName(ModBlocks.POST_ACACIA.get().getRegistryName()),
                new BlockItem(ModBlocks.POST_BIRCH.get(), new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP)).setRegistryName(ModBlocks.POST_BIRCH.get().getRegistryName()),
                new BlockItem(ModBlocks.POST_DARK_OAK.get(), new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP)).setRegistryName(ModBlocks.POST_DARK_OAK.get().getRegistryName()),
                new BlockItem(ModBlocks.POST_JUNGLE.get(), new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP)).setRegistryName(ModBlocks.POST_JUNGLE.get().getRegistryName()),
                new BlockItem(ModBlocks.POST_OAK.get(), new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP)).setRegistryName(ModBlocks.POST_OAK.get().getRegistryName()),
                new BlockItem(ModBlocks.POST_SPRUCE.get(), new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP)).setRegistryName(ModBlocks.POST_SPRUCE.get().getRegistryName()),
                new BlockItem(ModBlocks.UNBOUNDHAY_BLOCK.get(), new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP)).setRegistryName(ModBlocks.UNBOUNDHAY_BLOCK.get().getRegistryName()),
                new BlockItem(ModBlocks.SPARSEGRASS_BLOCK.get(), new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP)).setRegistryName(ModBlocks.SPARSEGRASS_BLOCK.get().getRegistryName()),
                new BlockItem(ModBlocks.PATCHYMYCELIUM_BLOCK.get(), new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP)).setRegistryName(ModBlocks.PATCHYMYCELIUM_BLOCK.get().getRegistryName()),
                new BlockItem(ModBlocks.GROWABLE_ALLIUM.get(), new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP)).setRegistryName(ModBlocks.GROWABLE_ALLIUM.get().getRegistryName()),
                new BlockItem(ModBlocks.GROWABLE_AZURE_BLUET.get(), new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP)).setRegistryName(ModBlocks.GROWABLE_AZURE_BLUET.get().getRegistryName()),
                new BlockItem(ModBlocks.GROWABLE_BLUE_ORCHID.get(), new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP)).setRegistryName(ModBlocks.GROWABLE_BLUE_ORCHID.get().getRegistryName()),
                new BlockItem(ModBlocks.GROWABLE_CORNFLOWER.get(), new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP)).setRegistryName(ModBlocks.GROWABLE_CORNFLOWER.get().getRegistryName()),
                new BlockItem(ModBlocks.GROWABLE_DANDELION.get(), new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP)).setRegistryName(ModBlocks.GROWABLE_DANDELION.get().getRegistryName()),
                new BlockItem(ModBlocks.GROWABLE_OXEYE_DAISY.get(), new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP)).setRegistryName(ModBlocks.GROWABLE_OXEYE_DAISY.get().getRegistryName()),
                new BlockItem(ModBlocks.GROWABLE_GRASS.get(), new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP)).setRegistryName(ModBlocks.GROWABLE_GRASS.get().getRegistryName()),
                new BlockItem(ModBlocks.GROWABLE_FERN.get(), new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP)).setRegistryName(ModBlocks.GROWABLE_FERN.get().getRegistryName()),
                new BlockItem(ModBlocks.GROWABLE_ROSE_BUSH.get(), new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP)).setRegistryName(ModBlocks.GROWABLE_ROSE_BUSH.get().getRegistryName()),
                new BlockItem(ModBlocks.GROWABLE_SUNFLOWER.get(), new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP)).setRegistryName(ModBlocks.GROWABLE_SUNFLOWER.get().getRegistryName()),
                new BlockItem(ModBlocks.GROWABLE_TALL_GRASS.get(), new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP)).setRegistryName(ModBlocks.GROWABLE_TALL_GRASS.get().getRegistryName()),
                new BlockItem(ModBlocks.GROWABLE_LARGE_FERN.get(), new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP)).setRegistryName(ModBlocks.GROWABLE_LARGE_FERN.get().getRegistryName()),
                new BlockItem(TURTLE_EGG.get(), new Item.Properties().stacksTo(1).tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP)).setRegistryName(TURTLE_EGG.get().getRegistryName()),
                new BlockItem(EGG_CARTON.get(), new Item.Properties().stacksTo(1).tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP)).setRegistryName(EGG_CARTON.get().getRegistryName()),
        };
//
//        event.getRegistry().register(new ForgeSpawnEggItem(ENHANCED_TURTLE, 0xFFFFDD,0x00DDCC.get(), new Item.Properties()
//                .tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP)).setRegistryName("enhanced_turtle_spawn_egg"));
//        event.getRegistry().register(new ForgeSpawnEggItem(ENHANCED_CHICKEN, 0xFFFCF0,0xCC0000.get(), new Item.Properties()
//                .tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP)).setRegistryName("enhanced_chicken_spawn_egg"));
//        event.getRegistry().register(new ForgeSpawnEggItem(ENHANCED_LLAMA, 0xCDB29C,0x7B4B34.get(), new Item.Properties()
//                .tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP)).setRegistryName("enhanced_llama_spawn_egg"));
//        event.getRegistry().register(new ForgeSpawnEggItem(ENHANCED_SHEEP, 0xFFFFFF,0xFF8C8C.get(), new Item.Properties()
//                .tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP)).setRegistryName("enhanced_sheep_spawn_egg"));
//        event.getRegistry().register(new ForgeSpawnEggItem(ENHANCED_RABBIT, 0xCA8349,0x553C36.get(), new Item.Properties()
//                .tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP)).setRegistryName("enhanced_rabbit_spawn_egg"));
//        event.getRegistry().register(new ForgeSpawnEggItem(ENHANCED_COW, 0x260800,0xf9f9f7.get(), new Item.Properties()
//                .tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP)).setRegistryName("enhanced_cow_spawn_egg"));
//        event.getRegistry().register(new ForgeSpawnEggItem(ENHANCED_MOOSHROOM, 0xFF0000,0xCCCCCC.get(), new Item.Properties()
//                .tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP)).setRegistryName("enhanced_mooshroom_spawn_egg"));
//        event.getRegistry().register(new ForgeSpawnEggItem(ENHANCED_PIG, 0xFFA4A4,0xB34d4d.get(), new Item.Properties()
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
