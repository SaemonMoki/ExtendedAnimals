package mokiyoki.enhancedanimals.init;

import mokiyoki.enhancedanimals.EnhancedAnimals;
import mokiyoki.enhancedanimals.blocks.PostBlock;
import mokiyoki.enhancedanimals.items.*;
import mokiyoki.enhancedanimals.util.Reference;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static mokiyoki.enhancedanimals.init.ModBlocks.EGG_CARTON;
import static mokiyoki.enhancedanimals.init.ModBlocks.GROWABLE_ALLIUM;
import static mokiyoki.enhancedanimals.init.ModBlocks.GROWABLE_AZURE_BLUET;
import static mokiyoki.enhancedanimals.init.ModBlocks.GROWABLE_BLUE_ORCHID;
import static mokiyoki.enhancedanimals.init.ModBlocks.GROWABLE_CORNFLOWER;
import static mokiyoki.enhancedanimals.init.ModBlocks.GROWABLE_DANDELION;
import static mokiyoki.enhancedanimals.init.ModBlocks.GROWABLE_FERN;
import static mokiyoki.enhancedanimals.init.ModBlocks.GROWABLE_GRASS;
import static mokiyoki.enhancedanimals.init.ModBlocks.GROWABLE_LARGE_FERN;
import static mokiyoki.enhancedanimals.init.ModBlocks.GROWABLE_OXEYE_DAISY;
import static mokiyoki.enhancedanimals.init.ModBlocks.GROWABLE_ROSE_BUSH;
import static mokiyoki.enhancedanimals.init.ModBlocks.GROWABLE_SUNFLOWER;
import static mokiyoki.enhancedanimals.init.ModBlocks.GROWABLE_TALL_GRASS;
import static mokiyoki.enhancedanimals.init.ModBlocks.PATCHYMYCELIUM_BLOCK;
import static mokiyoki.enhancedanimals.init.ModBlocks.POST_ACACIA;
import static mokiyoki.enhancedanimals.init.ModBlocks.POST_BIRCH;
import static mokiyoki.enhancedanimals.init.ModBlocks.POST_DARK_OAK;
import static mokiyoki.enhancedanimals.init.ModBlocks.POST_JUNGLE;
import static mokiyoki.enhancedanimals.init.ModBlocks.POST_OAK;
import static mokiyoki.enhancedanimals.init.ModBlocks.POST_SPRUCE;
import static mokiyoki.enhancedanimals.init.ModBlocks.SPARSEGRASS_BLOCK;
import static mokiyoki.enhancedanimals.init.ModBlocks.TURTLE_EGG;
import static mokiyoki.enhancedanimals.init.ModBlocks.UNBOUNDHAY_BLOCK;

/**
 * Created by moki on 24/08/2018.
 */
//@ObjectHolder(Reference.MODID)
public class ModItems {

    private static final DeferredRegister<Item> ITEMS_DEFERRED_REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, Reference.MODID);

    public static final RegistryObject<Item> EGG_WHITE = ITEMS_DEFERRED_REGISTRY.register("egg_white", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_CREAMLIGHT = ITEMS_DEFERRED_REGISTRY.register("egg_creamlight", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_CREAM = ITEMS_DEFERRED_REGISTRY.register("egg_cream", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_CREAMDARK = ITEMS_DEFERRED_REGISTRY.register("egg_creamdark", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_CREAMDARKEST = ITEMS_DEFERRED_REGISTRY.register("egg_creamdarkest", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));  //carmel
    public static final RegistryObject<Item> EGG_CARMELDARK = ITEMS_DEFERRED_REGISTRY.register("egg_carmeldark", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_GARNET = ITEMS_DEFERRED_REGISTRY.register("egg_garnet", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_PINKLIGHT = ITEMS_DEFERRED_REGISTRY.register("egg_pinklight", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_PINK = ITEMS_DEFERRED_REGISTRY.register("egg_pink", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_PINKDARK = ITEMS_DEFERRED_REGISTRY.register("egg_pinkdark", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_PINKDARKEST = ITEMS_DEFERRED_REGISTRY.register("egg_pinkdarkest", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));  //cherry
    public static final RegistryObject<Item> EGG_CHERRYDARK = ITEMS_DEFERRED_REGISTRY.register("egg_cherrydark", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_PLUM = ITEMS_DEFERRED_REGISTRY.register("egg_plum", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_BROWNLIGHT = ITEMS_DEFERRED_REGISTRY.register("egg_brownlight", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_BROWN = ITEMS_DEFERRED_REGISTRY.register("egg_brown", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_BROWNDARK = ITEMS_DEFERRED_REGISTRY.register("egg_browndark", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_CHOCOLATE = ITEMS_DEFERRED_REGISTRY.register("egg_chocolate", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_CHOCOLATEDARK = ITEMS_DEFERRED_REGISTRY.register("egg_chocolatedark", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_CHOCOLATECOSMOS = ITEMS_DEFERRED_REGISTRY.register("egg_chocolatecosmos", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_BLUE = ITEMS_DEFERRED_REGISTRY.register("egg_blue", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_GREENLIGHT = ITEMS_DEFERRED_REGISTRY.register("egg_greenlight", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_GREENYELLOW = ITEMS_DEFERRED_REGISTRY.register("egg_greenyellow", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_OLIVELIGHT = ITEMS_DEFERRED_REGISTRY.register("egg_olivelight", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_OLIVE = ITEMS_DEFERRED_REGISTRY.register("egg_olive", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_OLIVEDARK = ITEMS_DEFERRED_REGISTRY.register("egg_olivedark", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_ARMY = ITEMS_DEFERRED_REGISTRY.register("egg_army", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_BLUEGREY = ITEMS_DEFERRED_REGISTRY.register("egg_bluegrey", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_GREY = ITEMS_DEFERRED_REGISTRY.register("egg_grey", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_GREYGREEN = ITEMS_DEFERRED_REGISTRY.register("egg_greygreen", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_AVOCADO = ITEMS_DEFERRED_REGISTRY.register("egg_avocado", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_AVOCADODARK = ITEMS_DEFERRED_REGISTRY.register("egg_avocadodark", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_FELDGRAU = ITEMS_DEFERRED_REGISTRY.register("egg_feldgrau", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_MINT = ITEMS_DEFERRED_REGISTRY.register("egg_mint", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_GREEN = ITEMS_DEFERRED_REGISTRY.register("egg_green", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_GREENDARK = ITEMS_DEFERRED_REGISTRY.register("egg_greendark", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_PINE = ITEMS_DEFERRED_REGISTRY.register("egg_pine", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_PINEDARK = ITEMS_DEFERRED_REGISTRY.register("egg_pinedark", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_PINEBLACK = ITEMS_DEFERRED_REGISTRY.register("egg_pineblack", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_POWDERBLUE = ITEMS_DEFERRED_REGISTRY.register("egg_powderblue", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_TEA = ITEMS_DEFERRED_REGISTRY.register("egg_tea", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_MATCHA = ITEMS_DEFERRED_REGISTRY.register("egg_matcha", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_MATCHADARK = ITEMS_DEFERRED_REGISTRY.register("egg_matchadark", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_MOSS = ITEMS_DEFERRED_REGISTRY.register("egg_moss", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_MOSSDARK = ITEMS_DEFERRED_REGISTRY.register("egg_mossdark", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_GREENUMBER = ITEMS_DEFERRED_REGISTRY.register("egg_greenumber", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_GREYBLUE = ITEMS_DEFERRED_REGISTRY.register("egg_greyblue", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_GREYNEUTRAL = ITEMS_DEFERRED_REGISTRY.register("egg_greyneutral", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_LAUREL = ITEMS_DEFERRED_REGISTRY.register("egg_laurel", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_RESEDA = ITEMS_DEFERRED_REGISTRY.register("egg_reseda", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_GREENPEWTER = ITEMS_DEFERRED_REGISTRY.register("egg_greenpewter", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_GREYDARK = ITEMS_DEFERRED_REGISTRY.register("egg_greydark", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_CELADON = ITEMS_DEFERRED_REGISTRY.register("egg_celadon", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_FERN = ITEMS_DEFERRED_REGISTRY.register("egg_fern", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_ASPARAGUS = ITEMS_DEFERRED_REGISTRY.register("egg_asparagus", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_HUNTER = ITEMS_DEFERRED_REGISTRY.register("egg_hunter", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_HUNTERDARK = ITEMS_DEFERRED_REGISTRY.register("egg_hunterdark", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_TREEDARK = ITEMS_DEFERRED_REGISTRY.register("egg_treedark", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_PALEBLUE = ITEMS_DEFERRED_REGISTRY.register("egg_paleblue", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_HONEYDEW = ITEMS_DEFERRED_REGISTRY.register("egg_honeydew", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_EARTH = ITEMS_DEFERRED_REGISTRY.register("egg_earth", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_KHAKI = ITEMS_DEFERRED_REGISTRY.register("egg_khaki", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_GRULLO = ITEMS_DEFERRED_REGISTRY.register("egg_grullo", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_KHAKIDARK = ITEMS_DEFERRED_REGISTRY.register("egg_khakidark", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_CAROB = ITEMS_DEFERRED_REGISTRY.register("egg_carob", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_COOLGREY = ITEMS_DEFERRED_REGISTRY.register("egg_coolgrey", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_PINKGREY = ITEMS_DEFERRED_REGISTRY.register("egg_pinkgrey", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_WARMGREY = ITEMS_DEFERRED_REGISTRY.register("egg_warmgrey", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_ARTICHOKE = ITEMS_DEFERRED_REGISTRY.register("egg_artichoke", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_MYRTLEGREY = ITEMS_DEFERRED_REGISTRY.register("egg_myrtlegrey", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_RIFLE = ITEMS_DEFERRED_REGISTRY.register("egg_rifle", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_JADE = ITEMS_DEFERRED_REGISTRY.register("egg_jade", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_PISTACHIO = ITEMS_DEFERRED_REGISTRY.register("egg_pistachio", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_SAGE = ITEMS_DEFERRED_REGISTRY.register("egg_sage", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_ROSEMARY = ITEMS_DEFERRED_REGISTRY.register("egg_rosemary", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_GREENBROWN = ITEMS_DEFERRED_REGISTRY.register("egg_greenbrown", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_UMBER = ITEMS_DEFERRED_REGISTRY.register("egg_umber", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));

    public static final RegistryObject<Item> EGG_CREAM_SPOT = ITEMS_DEFERRED_REGISTRY.register("egg_cream_spot", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_CREAMDARK_SPOT = ITEMS_DEFERRED_REGISTRY.register("egg_creamdark_spot", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_CARMEL_SPOT = ITEMS_DEFERRED_REGISTRY.register("egg_carmel_spot", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));  //carmel
    public static final RegistryObject<Item> EGG_CARMELDARK_SPOT = ITEMS_DEFERRED_REGISTRY.register("egg_carmeldark_spot", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_GARNET_SPOT = ITEMS_DEFERRED_REGISTRY.register("egg_garnet_spot", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_PINK_SPOT = ITEMS_DEFERRED_REGISTRY.register("egg_pink_spot", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_PINKDARK_SPOT = ITEMS_DEFERRED_REGISTRY.register("egg_pinkdark_spot", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_CHERRY_SPOT = ITEMS_DEFERRED_REGISTRY.register("egg_cherry_spot", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));  //cherry
    public static final RegistryObject<Item> EGG_CHERRYDARK_SPOT = ITEMS_DEFERRED_REGISTRY.register("egg_cherrydark_spot", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_PLUM_SPOT = ITEMS_DEFERRED_REGISTRY.register("egg_plum_spot", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_BROWNLIGHT_SPOT = ITEMS_DEFERRED_REGISTRY.register("egg_brownlight_spot", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_BROWN_SPOT = ITEMS_DEFERRED_REGISTRY.register("egg_brown_spot", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_BROWNDARK_SPOT = ITEMS_DEFERRED_REGISTRY.register("egg_browndark_spot", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_CHOCOLATE_SPOT = ITEMS_DEFERRED_REGISTRY.register("egg_chocolate_spot", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_CHOCOLATEDARK_SPOT = ITEMS_DEFERRED_REGISTRY.register("egg_chocolatedark_spot", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_GREENYELLOW_SPOT = ITEMS_DEFERRED_REGISTRY.register("egg_greenyellow_spot", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_OLIVELIGHT_SPOT = ITEMS_DEFERRED_REGISTRY.register("egg_olivelight_spot", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_OLIVE_SPOT = ITEMS_DEFERRED_REGISTRY.register("egg_olive_spot", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_OLIVEDARK_SPOT = ITEMS_DEFERRED_REGISTRY.register("egg_olivedark_spot", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_ARMY_SPOT = ITEMS_DEFERRED_REGISTRY.register("egg_army_spot", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_GREY_SPOT = ITEMS_DEFERRED_REGISTRY.register("egg_grey_spot", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_GREYGREEN_SPOT = ITEMS_DEFERRED_REGISTRY.register("egg_greygreen_spot", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_AVOCADO_SPOT = ITEMS_DEFERRED_REGISTRY.register("egg_avocado_spot", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_AVOCADODARK_SPOT = ITEMS_DEFERRED_REGISTRY.register("egg_avocadodark_spot", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_FELDGRAU_SPOT = ITEMS_DEFERRED_REGISTRY.register("egg_feldgrau_spot", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_MINT_SPOT = ITEMS_DEFERRED_REGISTRY.register("egg_mint_spot", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_GREEN_SPOT = ITEMS_DEFERRED_REGISTRY.register("egg_green_spot", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_GREENDARK_SPOT = ITEMS_DEFERRED_REGISTRY.register("egg_greendark_spot", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_PINE_SPOT = ITEMS_DEFERRED_REGISTRY.register("egg_pine_spot", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_PINEDARK_SPOT = ITEMS_DEFERRED_REGISTRY.register("egg_pinedark_spot", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_PINEBLACK_SPOT = ITEMS_DEFERRED_REGISTRY.register("egg_pineblack_spot", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));

    public static final RegistryObject<Item> EGG_CREAM_SPATTER = ITEMS_DEFERRED_REGISTRY.register("egg_cream_spatter", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_CREAMDARK_SPATTER = ITEMS_DEFERRED_REGISTRY.register("egg_creamdark_spatter", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_CARMEL_SPATTER = ITEMS_DEFERRED_REGISTRY.register("egg_carmel_spatter", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));  //carmel
    public static final RegistryObject<Item> EGG_CARMELDARK_SPATTER = ITEMS_DEFERRED_REGISTRY.register("egg_carmeldark_spatter", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_GARNET_SPATTER = ITEMS_DEFERRED_REGISTRY.register("egg_garnet_spatter", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_PINK_SPATTER = ITEMS_DEFERRED_REGISTRY.register("egg_pink_spatter", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_PINKDARK_SPATTER = ITEMS_DEFERRED_REGISTRY.register("egg_pinkdark_spatter", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_CHERRY_SPATTER = ITEMS_DEFERRED_REGISTRY.register("egg_cherry_spatter", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));  //cherry
    public static final RegistryObject<Item> EGG_CHERRYDARK_SPATTER = ITEMS_DEFERRED_REGISTRY.register("egg_cherrydark_spatter", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_PLUM_SPATTER = ITEMS_DEFERRED_REGISTRY.register("egg_plum_spatter", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_BROWNLIGHT_SPATTER = ITEMS_DEFERRED_REGISTRY.register("egg_brownlight_spatter", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_BROWN_SPATTER = ITEMS_DEFERRED_REGISTRY.register("egg_brown_spatter", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_BROWNDARK_SPATTER = ITEMS_DEFERRED_REGISTRY.register("egg_browndark_spatter", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_CHOCOLATE_SPATTER = ITEMS_DEFERRED_REGISTRY.register("egg_chocolate_spatter", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_CHOCOLATEDARK_SPATTER = ITEMS_DEFERRED_REGISTRY.register("egg_chocolatedark_spatter", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_GREENYELLOW_SPATTER = ITEMS_DEFERRED_REGISTRY.register("egg_greenyellow_spatter", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_OLIVELIGHT_SPATTER = ITEMS_DEFERRED_REGISTRY.register("egg_olivelight_spatter", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_OLIVE_SPATTER = ITEMS_DEFERRED_REGISTRY.register("egg_olive_spatter", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_OLIVEDARK_SPATTER = ITEMS_DEFERRED_REGISTRY.register("egg_olivedark_spatter", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_ARMY_SPATTER = ITEMS_DEFERRED_REGISTRY.register("egg_army_spatter", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_GREY_SPATTER = ITEMS_DEFERRED_REGISTRY.register("egg_grey_spatter", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_GREYGREEN_SPATTER = ITEMS_DEFERRED_REGISTRY.register("egg_greygreen_spatter", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_AVOCADO_SPATTER = ITEMS_DEFERRED_REGISTRY.register("egg_avocado_spatter", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_AVOCADODARK_SPATTER = ITEMS_DEFERRED_REGISTRY.register("egg_avocadodark_spatter", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_FELDGRAU_SPATTER = ITEMS_DEFERRED_REGISTRY.register("egg_feldgrau_spatter", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_MINT_SPATTER = ITEMS_DEFERRED_REGISTRY.register("egg_mint_spatter", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_GREEN_SPATTER = ITEMS_DEFERRED_REGISTRY.register("egg_green_spatter", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_GREENDARK_SPATTER = ITEMS_DEFERRED_REGISTRY.register("egg_greendark_spatter", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_PINE_SPATTER = ITEMS_DEFERRED_REGISTRY.register("egg_pine_spatter", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_PINEDARK_SPATTER = ITEMS_DEFERRED_REGISTRY.register("egg_pinedark_spatter", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_PINEBLACK_SPATTER = ITEMS_DEFERRED_REGISTRY.register("egg_pineblack_spatter", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));

    public static final RegistryObject<Item> EGG_CREAM_SPECKLE = ITEMS_DEFERRED_REGISTRY.register("egg_cream_speckle", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_CREAMDARK_SPECKLE = ITEMS_DEFERRED_REGISTRY.register("egg_creamdark_speckle", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_CARMEL_SPECKLE = ITEMS_DEFERRED_REGISTRY.register("egg_carmel_speckle", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));  //carmel
    public static final RegistryObject<Item> EGG_CARMELDARK_SPECKLE = ITEMS_DEFERRED_REGISTRY.register("egg_carmeldark_speckle", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_GARNET_SPECKLE = ITEMS_DEFERRED_REGISTRY.register("egg_garnet_speckle", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_PINK_SPECKLE = ITEMS_DEFERRED_REGISTRY.register("egg_pink_speckle", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_PINKDARK_SPECKLE = ITEMS_DEFERRED_REGISTRY.register("egg_pinkdark_speckle", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_CHERRY_SPECKLE = ITEMS_DEFERRED_REGISTRY.register("egg_cherry_speckle", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));  //cherry
    public static final RegistryObject<Item> EGG_CHERRYDARK_SPECKLE = ITEMS_DEFERRED_REGISTRY.register("egg_cherrydark_speckle", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_PLUM_SPECKLE = ITEMS_DEFERRED_REGISTRY.register("egg_plum_speckle", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_BROWNLIGHT_SPECKLE = ITEMS_DEFERRED_REGISTRY.register("egg_brownlight_speckle", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_BROWN_SPECKLE = ITEMS_DEFERRED_REGISTRY.register("egg_brown_speckle", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_BROWNDARK_SPECKLE = ITEMS_DEFERRED_REGISTRY.register("egg_browndark_speckle", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_CHOCOLATE_SPECKLE = ITEMS_DEFERRED_REGISTRY.register("egg_chocolate_speckle", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_CHOCOLATEDARK_SPECKLE = ITEMS_DEFERRED_REGISTRY.register("egg_chocolatedark_speckle", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_GREENYELLOW_SPECKLE = ITEMS_DEFERRED_REGISTRY.register("egg_greenyellow_speckle", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_OLIVELIGHT_SPECKLE = ITEMS_DEFERRED_REGISTRY.register("egg_olivelight_speckle", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_OLIVE_SPECKLE = ITEMS_DEFERRED_REGISTRY.register("egg_olive_speckle", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_OLIVEDARK_SPECKLE = ITEMS_DEFERRED_REGISTRY.register("egg_olivedark_speckle", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_ARMY_SPECKLE = ITEMS_DEFERRED_REGISTRY.register("egg_army_speckle", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_GREY_SPECKLE = ITEMS_DEFERRED_REGISTRY.register("egg_grey_speckle", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_GREYGREEN_SPECKLE = ITEMS_DEFERRED_REGISTRY.register("egg_greygreen_speckle", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_AVOCADO_SPECKLE = ITEMS_DEFERRED_REGISTRY.register("egg_avocado_speckle", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_AVOCADODARK_SPECKLE = ITEMS_DEFERRED_REGISTRY.register("egg_avocadodark_speckle", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_FELDGRAU_SPECKLE = ITEMS_DEFERRED_REGISTRY.register("egg_feldgrau_speckle", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_MINT_SPECKLE = ITEMS_DEFERRED_REGISTRY.register("egg_mint_speckle", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_GREEN_SPECKLE = ITEMS_DEFERRED_REGISTRY.register("egg_green_speckle", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_GREENDARK_SPECKLE = ITEMS_DEFERRED_REGISTRY.register("egg_greendark_speckle", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_PINE_SPECKLE = ITEMS_DEFERRED_REGISTRY.register("egg_pine_speckle", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_PINEDARK_SPECKLE = ITEMS_DEFERRED_REGISTRY.register("egg_pinedark_speckle", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_PINEBLACK_SPECKLE = ITEMS_DEFERRED_REGISTRY.register("egg_pineblack_speckle", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));

    public static final RegistryObject<Item> EGG_EARTH_SPOT = ITEMS_DEFERRED_REGISTRY.register("egg_earth_spot", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_KHAKI_SPOT = ITEMS_DEFERRED_REGISTRY.register("egg_khaki_spot", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_GRULLO_SPOT = ITEMS_DEFERRED_REGISTRY.register("egg_grullo_spot", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_KHAKIDARK_SPOT = ITEMS_DEFERRED_REGISTRY.register("egg_khakidark_spot", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_CAROB_SPOT = ITEMS_DEFERRED_REGISTRY.register("egg_carob_spot", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_PINKGREY_SPOT = ITEMS_DEFERRED_REGISTRY.register("egg_pinkgrey_spot", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_WARMGREY_SPOT = ITEMS_DEFERRED_REGISTRY.register("egg_warmgrey_spot", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_ARTICHOKE_SPOT = ITEMS_DEFERRED_REGISTRY.register("egg_artichoke_spot", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_MYRTLEGREY_SPOT = ITEMS_DEFERRED_REGISTRY.register("egg_myrtlegrey_spot", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_RIFLE_SPOT = ITEMS_DEFERRED_REGISTRY.register("egg_rifle_spot", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_JADE_SPOT = ITEMS_DEFERRED_REGISTRY.register("egg_jade_spot", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_PISTACHIO_SPOT = ITEMS_DEFERRED_REGISTRY.register("egg_pistachio_spot", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_SAGE_SPOT = ITEMS_DEFERRED_REGISTRY.register("egg_sage_spot", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_ROSEMARY_SPOT = ITEMS_DEFERRED_REGISTRY.register("egg_rosemary_spot", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_GREENBROWN_SPOT = ITEMS_DEFERRED_REGISTRY.register("egg_greenbrown_spot", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_UMBER_SPOT = ITEMS_DEFERRED_REGISTRY.register("egg_umber_spot", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> Egg_Matcha_Spot = ITEMS_DEFERRED_REGISTRY.register("egg_matcha_spot", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> Egg_MatchaDark_Spot = ITEMS_DEFERRED_REGISTRY.register("egg_matchadark_spot", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_MOSS_SPOT = ITEMS_DEFERRED_REGISTRY.register("egg_moss_spot", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_MOSSDARK_SPOT = ITEMS_DEFERRED_REGISTRY.register("egg_mossdark_spot", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_GREENUMBER_SPOT = ITEMS_DEFERRED_REGISTRY.register("egg_greenumber_spot", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_GREYNEUTRAL_SPOT = ITEMS_DEFERRED_REGISTRY.register("egg_greyneutral_spot", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_LAUREL_SPOT = ITEMS_DEFERRED_REGISTRY.register("egg_laurel_spot", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_RESEDA_SPOT = ITEMS_DEFERRED_REGISTRY.register("egg_reseda_spot", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_GREENPEWTER_SPOT = ITEMS_DEFERRED_REGISTRY.register("egg_greenpewter_spot", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_GREYDARK_SPOT = ITEMS_DEFERRED_REGISTRY.register("egg_greydark_spot", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_CELADON_SPOT = ITEMS_DEFERRED_REGISTRY.register("egg_celadon_spot", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_FERN_SPOT = ITEMS_DEFERRED_REGISTRY.register("egg_fern_spot", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_ASPARAGUS_SPOT = ITEMS_DEFERRED_REGISTRY.register("egg_asparagus_spot", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_HUNTER_SPOT = ITEMS_DEFERRED_REGISTRY.register("egg_hunter_spot", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_HUNTERDARK_SPOT = ITEMS_DEFERRED_REGISTRY.register("egg_hunterdark_spot", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_TREEDARK_SPOT = ITEMS_DEFERRED_REGISTRY.register("egg_treedark_spot", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));

    public static final RegistryObject<Item> EGG_EARTH_SPATTER = ITEMS_DEFERRED_REGISTRY.register("egg_earth_spatter", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_KHAKI_SPATTER = ITEMS_DEFERRED_REGISTRY.register("egg_khaki_spatter", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_GRULLO_SPATTER = ITEMS_DEFERRED_REGISTRY.register("egg_grullo_spatter", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_KHAKIDARK_SPATTER = ITEMS_DEFERRED_REGISTRY.register("egg_khakidark_spatter", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_CAROB_SPATTER = ITEMS_DEFERRED_REGISTRY.register("egg_carob_spatter", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_PINKGREY_SPATTER = ITEMS_DEFERRED_REGISTRY.register("egg_pinkgrey_spatter", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_WARMGREY_SPATTER = ITEMS_DEFERRED_REGISTRY.register("egg_warmgrey_spatter", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_ARTICHOKE_SPATTER = ITEMS_DEFERRED_REGISTRY.register("egg_artichoke_spatter", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_MYRTLEGREY_SPATTER = ITEMS_DEFERRED_REGISTRY.register("egg_myrtlegrey_spatter", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_RIFLE_SPATTER = ITEMS_DEFERRED_REGISTRY.register("egg_rifle_spatter", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_JADE_SPATTER = ITEMS_DEFERRED_REGISTRY.register("egg_jade_spatter", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_PISTACHIO_SPATTER = ITEMS_DEFERRED_REGISTRY.register("egg_pistachio_spatter", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_SAGE_SPATTER = ITEMS_DEFERRED_REGISTRY.register("egg_sage_spatter", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_ROSEMARY_SPATTER = ITEMS_DEFERRED_REGISTRY.register("egg_rosemary_spatter", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_GREENBROWN_SPATTER = ITEMS_DEFERRED_REGISTRY.register("egg_greenbrown_spatter", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_UMBER_SPATTER = ITEMS_DEFERRED_REGISTRY.register("egg_umber_spatter", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_MATCHA_SPATTER = ITEMS_DEFERRED_REGISTRY.register("egg_matcha_spatter", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_MATCHADARK_SPATTER = ITEMS_DEFERRED_REGISTRY.register("egg_matchadark_spatter", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_MOSS_SPATTER = ITEMS_DEFERRED_REGISTRY.register("egg_moss_spatter", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_MOSSDARK_SPATTER = ITEMS_DEFERRED_REGISTRY.register("egg_mossdark_spatter", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_GREENUMBER_SPATTER = ITEMS_DEFERRED_REGISTRY.register("egg_greenumber_spatter", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_GREYNEUTRAL_SPATTER = ITEMS_DEFERRED_REGISTRY.register("egg_greyneutral_spatter", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_LAUREL_SPATTER = ITEMS_DEFERRED_REGISTRY.register("egg_laurel_spatter", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_RESEDA_SPATTER = ITEMS_DEFERRED_REGISTRY.register("egg_reseda_spatter", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_GREENPEWTER_SPATTER = ITEMS_DEFERRED_REGISTRY.register("egg_greenpewter_spatter", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_GREYDARK_SPATTER = ITEMS_DEFERRED_REGISTRY.register("egg_greydark_spatter", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_CELADON_SPATTER = ITEMS_DEFERRED_REGISTRY.register("egg_celadon_spatter", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_FERN_SPATTER = ITEMS_DEFERRED_REGISTRY.register("egg_fern_spatter", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_ASPARAGUS_SPATTER = ITEMS_DEFERRED_REGISTRY.register("egg_asparagus_spatter", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_HUNTER_SPATTER = ITEMS_DEFERRED_REGISTRY.register("egg_hunter_spatter", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_HUNTERDARK_SPATTER = ITEMS_DEFERRED_REGISTRY.register("egg_hunterdark_spatter", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_TREEDARK_SPATTER = ITEMS_DEFERRED_REGISTRY.register("egg_treedark_spatter", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));

    public static final RegistryObject<Item> EGG_EARTH_SPECKLE = ITEMS_DEFERRED_REGISTRY.register("egg_earth_speckle", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_KHAKI_SPECKLE = ITEMS_DEFERRED_REGISTRY.register("egg_khaki_speckle", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_GRULLO_SPECKLE = ITEMS_DEFERRED_REGISTRY.register("egg_grullo_speckle", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_KHAKIDARK_SPECKLE = ITEMS_DEFERRED_REGISTRY.register("egg_khakidark_speckle", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_CAROB_SPECKLE = ITEMS_DEFERRED_REGISTRY.register("egg_carob_speckle", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_PINKGREY_SPECKLE = ITEMS_DEFERRED_REGISTRY.register("egg_pinkgrey_speckle", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_WARMGREY_SPECKLE = ITEMS_DEFERRED_REGISTRY.register("egg_warmgrey_speckle", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_ARTICHOKE_SPECKLE = ITEMS_DEFERRED_REGISTRY.register("egg_artichoke_speckle", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_MYRTLEGREY_SPECKLE = ITEMS_DEFERRED_REGISTRY.register("egg_myrtlegrey_speckle", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_RIFLE_SPECKLE = ITEMS_DEFERRED_REGISTRY.register("egg_rifle_speckle", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_JADE_SPECKLE = ITEMS_DEFERRED_REGISTRY.register("egg_jade_speckle", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_PISTACHIO_SPECKLE = ITEMS_DEFERRED_REGISTRY.register("egg_pistachio_speckle", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_SAGE_SPECKLE = ITEMS_DEFERRED_REGISTRY.register("egg_sage_speckle", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_ROSEMARY_SPECKLE = ITEMS_DEFERRED_REGISTRY.register("egg_rosemary_speckle", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_GREENBROWN_SPECKLE = ITEMS_DEFERRED_REGISTRY.register("egg_greenbrown_speckle", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_UMBER_SPECKLE = ITEMS_DEFERRED_REGISTRY.register("egg_umber_speckle", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_MATCHA_SPECKLE = ITEMS_DEFERRED_REGISTRY.register("egg_matcha_speckle", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_MATCHADARK_SPECKLE = ITEMS_DEFERRED_REGISTRY.register("egg_matchadark_speckle", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_MOSS_SPECKLE = ITEMS_DEFERRED_REGISTRY.register("egg_moss_speckle", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_MOSSDARK_SPECKLE = ITEMS_DEFERRED_REGISTRY.register("egg_mossdark_speckle", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_GREENUMBER_SPECKLE = ITEMS_DEFERRED_REGISTRY.register("egg_greenumber_speckle", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_GREYNEUTRAL_SPECKLE = ITEMS_DEFERRED_REGISTRY.register("egg_greyneutral_speckle", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_LAUREL_SPECKLE = ITEMS_DEFERRED_REGISTRY.register("egg_laurel_speckle", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_RESEDA_SPECKLE = ITEMS_DEFERRED_REGISTRY.register("egg_reseda_speckle", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_GREENPEWTER_SPECKLE = ITEMS_DEFERRED_REGISTRY.register("egg_greenpewter_speckle", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_GREYDARK_SPECKLE = ITEMS_DEFERRED_REGISTRY.register("egg_greydark_speckle", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_CELADON_SPECKLE = ITEMS_DEFERRED_REGISTRY.register("egg_celadon_speckle", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_FERN_SPECKLE = ITEMS_DEFERRED_REGISTRY.register("egg_fern_speckle", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_ASPARAGUS_SPECKLE = ITEMS_DEFERRED_REGISTRY.register("egg_asparagus_speckle", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_HUNTER_SPECKLE = ITEMS_DEFERRED_REGISTRY.register("egg_hunter_speckle", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_HUNTERDARK_SPECKLE = ITEMS_DEFERRED_REGISTRY.register("egg_hunterdark_speckle", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_TREEDARK_SPECKLE = ITEMS_DEFERRED_REGISTRY.register("egg_treedark_speckle", () -> new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));

    public static final RegistryObject<Item> RAWCHICKEN_DARKSMALL = ITEMS_DEFERRED_REGISTRY.register("rawchicken_darksmall", () -> new RawChicken(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(64).food((new FoodProperties.Builder()).nutrition(1).saturationMod(0.2F).effect(new MobEffectInstance(MobEffects.HUNGER, 600, 0), 0.3F).meat().build())));
    public static final RegistryObject<Item> RAWCHICKEN_DARK = ITEMS_DEFERRED_REGISTRY.register("rawchicken_dark", () -> new RawChicken(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(64).food((new FoodProperties.Builder()).nutrition(1).saturationMod(0.3F).effect(new MobEffectInstance(MobEffects.HUNGER, 600, 0), 0.3F).meat().build())));
    public static final RegistryObject<Item> RAWCHICKEN_DARKBIG = ITEMS_DEFERRED_REGISTRY.register("rawchicken_darkbig", () -> new RawChicken(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(64).food((new FoodProperties.Builder()).nutrition(2).saturationMod(0.3F).effect(new MobEffectInstance(MobEffects.HUNGER, 600, 0), 0.3F).meat().build())));
    public static final RegistryObject<Item> RAWCHICKEN_PALESMALL = ITEMS_DEFERRED_REGISTRY.register("rawchicken_palesmall", () -> new RawChicken(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(64).food((new FoodProperties.Builder()).nutrition(1).saturationMod(0.2F).effect(new MobEffectInstance(MobEffects.HUNGER, 600, 0), 0.3F).meat().build())));
    public static final RegistryObject<Item> RAWCHICKEN_PALE = ITEMS_DEFERRED_REGISTRY.register("rawchicken_pale", () -> new RawChicken(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(64).food((new FoodProperties.Builder()).nutrition(1).saturationMod(0.3F).effect(new MobEffectInstance(MobEffects.HUNGER, 600, 0), 0.3F).meat().build())));
    public static final RegistryObject<Item> COOKEDCHICKEN_DARKSMALL = ITEMS_DEFERRED_REGISTRY.register("cookedchicken_darksmall", () -> new CookedChicken(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(64).food((new FoodProperties.Builder()).nutrition(2).saturationMod(0.6F).meat().build())));
    public static final RegistryObject<Item> COOKEDCHICKEN_DARK = ITEMS_DEFERRED_REGISTRY.register("cookedchicken_dark", () -> new CookedChicken(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(64).food((new FoodProperties.Builder()).nutrition(4).saturationMod(0.6F).meat().build())));
    public static final RegistryObject<Item> COOKEDCHICKEN_DARKBIG = ITEMS_DEFERRED_REGISTRY.register("cookedchicken_darkbig", () -> new CookedChicken(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(64).food((new FoodProperties.Builder()).nutrition(6).saturationMod(0.6F).meat().build())));
    public static final RegistryObject<Item> COOKEDCHICKEN_PALESMALL = ITEMS_DEFERRED_REGISTRY.register("cookedchicken_palesmall", () -> new CookedChicken(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(64).food((new FoodProperties.Builder()).nutrition(2).saturationMod(0.6F).meat().build())));
    public static final RegistryObject<Item> COOKEDCHICKEN_PALE = ITEMS_DEFERRED_REGISTRY.register("cookedchicken_pale", () -> new CookedChicken(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(64).food((new FoodProperties.Builder()).nutrition(4).saturationMod(0.6F).meat().build())));
    public static final RegistryObject<Item> RAWRABBIT_SMALL = ITEMS_DEFERRED_REGISTRY.register("rawrabbit_small", () -> new RawRabbit(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(64).food((new FoodProperties.Builder()).nutrition(2).saturationMod(0.3F).meat().build())));
    public static final RegistryObject<Item> COOKEDRABBIT_SMALL = ITEMS_DEFERRED_REGISTRY.register("cookedrabbit_small", () -> new CookedRabbit(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(64).food((new FoodProperties.Builder()).nutrition(3).saturationMod(0.6F).meat().build())));
    public static final RegistryObject<Item> RABBITSTEW_WEAK = ITEMS_DEFERRED_REGISTRY.register("rabbitstew_weak", () -> new RabbitStew(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(64).food((new FoodProperties.Builder()).nutrition(8).saturationMod(0.5F).meat().build())));

    public static final RegistryObject<Item> HALF_MILK_BOTTLE = ITEMS_DEFERRED_REGISTRY.register("half_milk_bottle", () -> new MilkBottleHalf(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1).craftRemainder(Items.GLASS_BOTTLE).food((new FoodProperties.Builder()).alwaysEat().nutrition(0).saturationMod(0F).build())));
    public static final RegistryObject<Item> MILK_BOTTLE = ITEMS_DEFERRED_REGISTRY.register("milk_bottle", () -> new MilkBottle(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1).craftRemainder(Items.GLASS_BOTTLE).food((new FoodProperties.Builder()).alwaysEat().nutrition(0).saturationMod(0F).build())));
    public static final RegistryObject<Item> ONESIXTH_MILK_BUCKET = ITEMS_DEFERRED_REGISTRY.register("onesixth_milk_bucket", () -> new MilkBucketOneSixth(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1).craftRemainder(Items.BUCKET).food((new FoodProperties.Builder()).alwaysEat().nutrition(0).saturationMod(0F).build())));
    public static final RegistryObject<Item> ONETHIRD_MILK_BUCKET = ITEMS_DEFERRED_REGISTRY.register("onethird_milk_bucket", () -> new MilkBucketOneThird(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1).craftRemainder(Items.BUCKET).food((new FoodProperties.Builder()).alwaysEat().nutrition(0).saturationMod(0F).build())));
    public static final RegistryObject<Item> HALF_MILK_BUCKET = ITEMS_DEFERRED_REGISTRY.register("half_milk_bucket", () -> new MilkBucketHalf(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1).craftRemainder(Items.BUCKET).food((new FoodProperties.Builder()).alwaysEat().nutrition(0).saturationMod(0F).build())));
    public static final RegistryObject<Item> TWOTHIRDS_MILK_BUCKET = ITEMS_DEFERRED_REGISTRY.register("twothirds_milk_bucket", () -> new MilkBucketTwoThirds(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1).craftRemainder(Items.BUCKET).food((new FoodProperties.Builder()).alwaysEat().nutrition(0).saturationMod(0F).build())));
    public static final RegistryObject<Item> FIVESIXTHS_MILK_BUCKET = ITEMS_DEFERRED_REGISTRY.register("fivesixths_milk_bucket", () -> new MilkBucketFiveSixths(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1).craftRemainder(Items.BUCKET).food((new FoodProperties.Builder()).alwaysEat().nutrition(0).saturationMod(0F).build())));

                        //ITEMNAME_VARIENT_TEXTUREVARIENT
    public static final RegistryObject<Item> SADDLE_BASIC_LEATHER = ITEMS_DEFERRED_REGISTRY.register("saddle_basic_leather", () -> new CustomizableSaddleVanilla(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1),10511680));
    public static final RegistryObject<Item> SADDLE_BASIC_LEATHER_GOLD = ITEMS_DEFERRED_REGISTRY.register("saddle_basic_leather_gold", () -> new CustomizableSaddleVanilla(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1),10511680));
    public static final RegistryObject<Item> SADDLE_BASIC_LEATHER_DIAMOND = ITEMS_DEFERRED_REGISTRY.register("saddle_basic_leather_diamond", () -> new CustomizableSaddleVanilla(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1),10511680));
    public static final RegistryObject<Item> SADDLE_BASIC_LEATHER_WOOD = ITEMS_DEFERRED_REGISTRY.register("saddle_basic_leather_wood", () -> new CustomizableSaddleVanilla(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1),10511680));
    public static final RegistryObject<Item> SADDLE_BASIC_CLOTH = ITEMS_DEFERRED_REGISTRY.register("saddle_basic_cloth", () -> new CustomizableSaddleVanilla(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1),16777215));
    public static final RegistryObject<Item> SADDLE_BASIC_CLOTH_GOLD = ITEMS_DEFERRED_REGISTRY.register("saddle_basic_cloth_gold", () -> new CustomizableSaddleVanilla(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1),16777215));
    public static final RegistryObject<Item> SADDLE_BASIC_CLOTH_DIAMOND = ITEMS_DEFERRED_REGISTRY.register("saddle_basic_cloth_diamond", () -> new CustomizableSaddleVanilla(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1),16777215));
    public static final RegistryObject<Item> SADDLE_BASIC_CLOTH_WOOD = ITEMS_DEFERRED_REGISTRY.register("saddle_basic_cloth_wood", () -> new CustomizableSaddleVanilla(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1),16777215));
    public static final RegistryObject<Item> SADDLE_BASIC_LEATHERCLOTHSEAT = ITEMS_DEFERRED_REGISTRY.register("saddle_basic_leatherclothseat", () -> new CustomizableSaddleVanilla(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1),16777215));
    public static final RegistryObject<Item> SADDLE_BASIC_LEATHERCLOTHSEAT_GOLD = ITEMS_DEFERRED_REGISTRY.register("saddle_basic_leatherclothseat_gold", () -> new CustomizableSaddleVanilla(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1),16777215));
    public static final RegistryObject<Item> SADDLE_BASIC_LEATHERCLOTHSEAT_DIAMOND = ITEMS_DEFERRED_REGISTRY.register("saddle_basic_leatherclothseat_diamond", () -> new CustomizableSaddleVanilla(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1),16777215));
    public static final RegistryObject<Item> SADDLE_BASIC_LEATHERCLOTHSEAT_WOOD = ITEMS_DEFERRED_REGISTRY.register("saddle_basic_leatherclothseat_wood", () -> new CustomizableSaddleVanilla(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1),16777215));
    public static final RegistryObject<Item> SADDLE_BASICPOMEL_LEATHER = ITEMS_DEFERRED_REGISTRY.register("saddle_basicpomel_leather", () -> new CustomizableSaddleWestern(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1),10511680));
    public static final RegistryObject<Item> SADDLE_BASICPOMEL_LEATHER_GOLD = ITEMS_DEFERRED_REGISTRY.register("saddle_basicpomel_leather_gold", () -> new CustomizableSaddleWestern(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1),10511680));
    public static final RegistryObject<Item> SADDLE_BASICPOMEL_LEATHER_DIAMOND = ITEMS_DEFERRED_REGISTRY.register("saddle_basicpomel_leather_diamond", () -> new CustomizableSaddleWestern(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1),10511680));
    public static final RegistryObject<Item> SADDLE_BASICPOMEL_LEATHER_WOOD = ITEMS_DEFERRED_REGISTRY.register("saddle_basicpomel_leather_wood", () -> new CustomizableSaddleWestern(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1),10511680));
    public static final RegistryObject<Item> SADDLE_BASICPOMEL_CLOTH = ITEMS_DEFERRED_REGISTRY.register("saddle_basicpomel_cloth", () -> new CustomizableSaddleWestern(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1),16777215));
    public static final RegistryObject<Item> SADDLE_BASICPOMEL_CLOTH_GOLD = ITEMS_DEFERRED_REGISTRY.register("saddle_basicpomel_cloth_gold", () -> new CustomizableSaddleWestern(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1),16777215));
    public static final RegistryObject<Item> SADDLE_BASICPOMEL_CLOTH_DIAMOND = ITEMS_DEFERRED_REGISTRY.register("saddle_basicpomel_cloth_diamond", () -> new CustomizableSaddleWestern(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1),16777215));
    public static final RegistryObject<Item> SADDLE_BASICPOMEL_CLOTH_WOOD = ITEMS_DEFERRED_REGISTRY.register("saddle_basicpomel_cloth_wood", () -> new CustomizableSaddleWestern(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1),16777215));
    public static final RegistryObject<Item> SADDLE_BASICPOMEL_LEATHERCLOTHSEAT = ITEMS_DEFERRED_REGISTRY.register("saddle_basicpomel_leatherclothseat", () -> new CustomizableSaddleWestern(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1),16777215));
    public static final RegistryObject<Item> SADDLE_BASICPOMEL_LEATHERCLOTHSEAT_GOLD = ITEMS_DEFERRED_REGISTRY.register("saddle_basicpomel_leatherclothseat_gold", () -> new CustomizableSaddleWestern(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1),16777215));
    public static final RegistryObject<Item> SADDLE_BASICPOMEL_LEATHERCLOTHSEAT_DIAMOND = ITEMS_DEFERRED_REGISTRY.register("saddle_basicpomel_leatherclothseat_diamond", () -> new CustomizableSaddleWestern(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1),16777215));
    public static final RegistryObject<Item> SADDLE_BASICPOMEL_LEATHERCLOTHSEAT_WOOD = ITEMS_DEFERRED_REGISTRY.register("saddle_basicpomel_leatherclothseat_wood", () -> new CustomizableSaddleWestern(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1),16777215));
    public static final RegistryObject<Item> SADDLE_ENGLISH_LEATHER = ITEMS_DEFERRED_REGISTRY.register("saddle_english_leather", () -> new CustomizableSaddleEnglish(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1),10511680));
    public static final RegistryObject<Item> SADDLE_ENGLISH_LEATHER_GOLD = ITEMS_DEFERRED_REGISTRY.register("saddle_english_leather_gold", () -> new CustomizableSaddleEnglish(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1),10511680));
    public static final RegistryObject<Item> SADDLE_ENGLISH_LEATHER_DIAMOND = ITEMS_DEFERRED_REGISTRY.register("saddle_english_leather_diamond", () -> new CustomizableSaddleEnglish(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1),10511680));
    public static final RegistryObject<Item> SADDLE_ENGLISH_LEATHER_WOOD = ITEMS_DEFERRED_REGISTRY.register("saddle_english_leather_wood", () -> new CustomizableSaddleEnglish(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1),10511680));
    public static final RegistryObject<Item> SADDLE_ENGLISH_CLOTH = ITEMS_DEFERRED_REGISTRY.register("saddle_english_cloth", () -> new CustomizableSaddleEnglish(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1),16777215));
    public static final RegistryObject<Item> SADDLE_ENGLISH_CLOTH_GOLD = ITEMS_DEFERRED_REGISTRY.register("saddle_english_cloth_gold", () -> new CustomizableSaddleEnglish(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1),16777215));
    public static final RegistryObject<Item> SADDLE_ENGLISH_CLOTH_DIAMOND = ITEMS_DEFERRED_REGISTRY.register("saddle_english_cloth_diamond", () -> new CustomizableSaddleEnglish(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1),16777215));
    public static final RegistryObject<Item> SADDLE_ENGLISH_CLOTH_WOOD = ITEMS_DEFERRED_REGISTRY.register("saddle_english_cloth_wood", () -> new CustomizableSaddleEnglish(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1),16777215));
    public static final RegistryObject<Item> SADDLE_ENGLISH_LEATHERCLOTHSEAT = ITEMS_DEFERRED_REGISTRY.register("saddle_english_leatherclothseat", () -> new CustomizableSaddleEnglish(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1),16777215));
    public static final RegistryObject<Item> SADDLE_ENGLISH_LEATHERCLOTHSEAT_GOLD = ITEMS_DEFERRED_REGISTRY.register("saddle_english_leatherclothseat_gold", () -> new CustomizableSaddleEnglish(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1),16777215));
    public static final RegistryObject<Item> SADDLE_ENGLISH_LEATHERCLOTHSEAT_DIAMOND = ITEMS_DEFERRED_REGISTRY.register("saddle_english_leatherclothseat_diamond", () -> new CustomizableSaddleEnglish(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1),16777215));
    public static final RegistryObject<Item> SADDLE_ENGLISH_LEATHERCLOTHSEAT_WOOD = ITEMS_DEFERRED_REGISTRY.register("saddle_english_leatherclothseat_wood", () -> new CustomizableSaddleEnglish(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1),16777215));
    public static final RegistryObject<Item> BRIDLE_BASIC_LEATHER = ITEMS_DEFERRED_REGISTRY.register("bridle_basic_leather", () -> new CustomizableBridle(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1),10511680));
    public static final RegistryObject<Item> BRIDLE_BASIC_LEATHER_GOLD = ITEMS_DEFERRED_REGISTRY.register("bridle_basic_leather_gold", () -> new CustomizableBridle(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1),10511680));
    public static final RegistryObject<Item> BRIDLE_BASIC_LEATHER_DIAMOND = ITEMS_DEFERRED_REGISTRY.register("bridle_basic_leather_diamond", () -> new CustomizableBridle(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1),10511680));
    public static final RegistryObject<Item> BRIDLE_BASIC_CLOTH = ITEMS_DEFERRED_REGISTRY.register("bridle_basic_cloth", () -> new CustomizableBridle(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1),16777215));
    public static final RegistryObject<Item> BRIDLE_BASIC_CLOTH_GOLD = ITEMS_DEFERRED_REGISTRY.register("bridle_basic_cloth_gold", () -> new CustomizableBridle(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1),16777215));
    public static final RegistryObject<Item> BRIDLE_BASIC_CLOTH_DIAMOND = ITEMS_DEFERRED_REGISTRY.register("bridle_basic_cloth_diamond", () -> new CustomizableBridle(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1),16777215));
    public static final RegistryObject<Item> COLLAR_BASIC_CLOTH = ITEMS_DEFERRED_REGISTRY.register("collar_basic_cloth", () -> new CustomizableCollar(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1),16777215, false));
    public static final RegistryObject<Item> COLLAR_BASIC_CLOTH_IRONRING = ITEMS_DEFERRED_REGISTRY.register("collar_basic_cloth_ironring", () -> new CustomizableCollar(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1),16777215, false));
    public static final RegistryObject<Item> COLLAR_BASIC_CLOTH_IRONBELL = ITEMS_DEFERRED_REGISTRY.register("collar_basic_cloth_ironbell", () -> new CustomizableCollar(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1),16777215, true));
    public static final RegistryObject<Item> COLLAR_BASIC_CLOTH_GOLDRING = ITEMS_DEFERRED_REGISTRY.register("collar_basic_cloth_goldring", () -> new CustomizableCollar(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1),16777215, false));
    public static final RegistryObject<Item> COLLAR_BASIC_CLOTH_GOLDBELL = ITEMS_DEFERRED_REGISTRY.register("collar_basic_cloth_goldbell", () -> new CustomizableCollar(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1),16777215, true));
    public static final RegistryObject<Item> COLLAR_BASIC_CLOTH_DIAMONDRING = ITEMS_DEFERRED_REGISTRY.register("collar_basic_cloth_diamondring", () -> new CustomizableCollar(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1),16777215, false));
    public static final RegistryObject<Item> COLLAR_BASIC_CLOTH_DIAMONDBELL = ITEMS_DEFERRED_REGISTRY.register("collar_basic_cloth_diamondbell", () -> new CustomizableCollar(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1),16777215, true));
    public static final RegistryObject<Item> COLLAR_BASIC_LEATHER = ITEMS_DEFERRED_REGISTRY.register("collar_basic_leather", () -> new CustomizableCollar(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1),10511680, false));
    public static final RegistryObject<Item> COLLAR_BASIC_LEATHER_IRONRING = ITEMS_DEFERRED_REGISTRY.register("collar_basic_leather_ironring", () -> new CustomizableCollar(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1),10511680, false));
    public static final RegistryObject<Item> COLLAR_BASIC_LEATHER_IRONBELL = ITEMS_DEFERRED_REGISTRY.register("collar_basic_leather_ironbell", () -> new CustomizableCollar(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1),10511680, true));
    public static final RegistryObject<Item> COLLAR_BASIC_LEATHER_GOLDRING = ITEMS_DEFERRED_REGISTRY.register("collar_basic_leather_goldring", () -> new CustomizableCollar(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1),10511680, false));
    public static final RegistryObject<Item> COLLAR_BASIC_LEATHER_GOLDBELL = ITEMS_DEFERRED_REGISTRY.register("collar_basic_leather_goldbell", () -> new CustomizableCollar(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1),10511680, true));
    public static final RegistryObject<Item> COLLAR_BASIC_LEATHER_DIAMONDRING = ITEMS_DEFERRED_REGISTRY.register("collar_basic_leather_diamondring", () -> new CustomizableCollar(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1),10511680, false));
    public static final RegistryObject<Item> COLLAR_BASIC_LEATHER_DIAMONDBELL = ITEMS_DEFERRED_REGISTRY.register("collar_basic_leather_diamondbell", () -> new CustomizableCollar(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1),10511680, true));

    public static final RegistryObject<Item> ENHANCED_AXOLOTL_BUCKET = ITEMS_DEFERRED_REGISTRY.register("enhanced_axolotl_bucket", () -> new EnhancedAxolotlBucket(new Item.Properties().stacksTo(1), ModEntities.ENHANCED_AXOLOTL, () -> Fluids.WATER, () -> SoundEvents.BUCKET_EMPTY_AXOLOTL));

    public static final RegistryObject<Item> GENETICS_ENCYCLOPEDIA = ITEMS_DEFERRED_REGISTRY.register("genetics_encyclopedia", () -> new GeneticsEncyclopedia(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> DEBUG_GENE_BOOK = ITEMS_DEFERRED_REGISTRY.register("debug_gene_book", () -> new DebugGenesBook(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
//    public static final RegistryObject<Item> MIXABLE_MILK = ITEMS_DEFERRED_REGISTRY.register("rawchicken_darksmall", new MixableMilkBucket(new Item.Properties().group(EnhancedAnimals.GENETICS_ANIMALS_GROUP).maxStackSize(1)));

//    public static final RegistryObject<Item> ENHANCED_AXOLOTL_EGG = ITEMS_DEFERRED_REGISTRY.register("enhanced_axolotl_spawn_egg", new ForgeSpawnEggItem(ENHANCED_AXOLOTL, 0xFFFFDD,0x00DDCC, new Item.Properties()));

    /*
     * Blocks
     */
    public static final RegistryObject<Item> POST_ACACIA_ITEM = ITEMS_DEFERRED_REGISTRY.register("post_acacia", () -> new BlockItem(POST_ACACIA.get(), new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP)));
    public static final RegistryObject<Item> POST_BIRCH_ITEM = ITEMS_DEFERRED_REGISTRY.register("post_birch", () -> new BlockItem(POST_BIRCH.get(), new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP)));
    public static final RegistryObject<Item> POST_DARK_OAK_ITEM = ITEMS_DEFERRED_REGISTRY.register("post_dark_oak", () -> new BlockItem(POST_DARK_OAK.get(), new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP)));
    public static final RegistryObject<Item> POST_JUNGLE_ITEM = ITEMS_DEFERRED_REGISTRY.register("post_jungle", () -> new BlockItem(POST_JUNGLE.get(), new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP)));
    public static final RegistryObject<Item> POST_OAK_ITEM = ITEMS_DEFERRED_REGISTRY.register("post_oak", () -> new BlockItem(POST_OAK.get(), new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP)));
    public static final RegistryObject<Item> POST_SPRUCE_ITEM = ITEMS_DEFERRED_REGISTRY.register("post_spruce", () -> new BlockItem(POST_SPRUCE.get(), new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP)));

    public static final RegistryObject<Item> EGG_CARTON_ITEM = ITEMS_DEFERRED_REGISTRY.register("egg_carton", () -> new BlockItem(EGG_CARTON.get(), new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP)));
    public static final RegistryObject<Item> TURTLE_EGG_ITEM = ITEMS_DEFERRED_REGISTRY.register("turtle_egg", () -> new BlockItem(TURTLE_EGG.get(), new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP)));
    public static final RegistryObject<Item> UNBOUNDHAY_BLOCK_ITEM = ITEMS_DEFERRED_REGISTRY.register("unboundhay_block", () -> new BlockItem(UNBOUNDHAY_BLOCK.get(), new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP)));
    public static final RegistryObject<Item> SPARSEGRASS_BLOCK_ITEM = ITEMS_DEFERRED_REGISTRY.register("sparsegrass_block", () -> new BlockItem(SPARSEGRASS_BLOCK.get(), new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP)));
    public static final RegistryObject<Item> PATCHYMYCELIUM_BLOCK_ITEM = ITEMS_DEFERRED_REGISTRY.register("patchymycelium_block", () -> new BlockItem(PATCHYMYCELIUM_BLOCK.get(), new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP)));
    public static final RegistryObject<Item> GROWABLE_ALLIUM_ITEM = ITEMS_DEFERRED_REGISTRY.register("growable_allium", () -> new BlockItem(GROWABLE_ALLIUM.get(), new Item.Properties()));
    public static final RegistryObject<Item> GROWABLE_AZURE_BLUET_ITEM = ITEMS_DEFERRED_REGISTRY.register("growable_azure_bluet", () -> new BlockItem(GROWABLE_AZURE_BLUET.get(), new Item.Properties()));
    public static final RegistryObject<Item> GROWABLE_BLUE_ORCHID_ITEM = ITEMS_DEFERRED_REGISTRY.register("growable_blue_orchid", () -> new BlockItem(GROWABLE_BLUE_ORCHID.get(), new Item.Properties()));
    public static final RegistryObject<Item> GROWABLE_CORNFLOWER_ITEM = ITEMS_DEFERRED_REGISTRY.register("growable_cornflower", () -> new BlockItem(GROWABLE_CORNFLOWER.get(), new Item.Properties()));
    public static final RegistryObject<Item> GROWABLE_DANDELION_ITEM = ITEMS_DEFERRED_REGISTRY.register("growable_dandelion", () -> new BlockItem(GROWABLE_DANDELION.get(), new Item.Properties()));
    public static final RegistryObject<Item> GROWABLE_OXEYE_DAISY_ITEM = ITEMS_DEFERRED_REGISTRY.register("growable_oxeye_daisy", () -> new BlockItem(GROWABLE_OXEYE_DAISY.get(), new Item.Properties()));
    public static final RegistryObject<Item> GROWABLE_GRASS_ITEM = ITEMS_DEFERRED_REGISTRY.register("growable_grass", () -> new BlockItem(GROWABLE_GRASS.get(), new Item.Properties()));
    public static final RegistryObject<Item> GROWABLE_FERN_ITEM = ITEMS_DEFERRED_REGISTRY.register("growable_fern", () -> new BlockItem(GROWABLE_FERN.get(), new Item.Properties()));
    public static final RegistryObject<Item> GROWABLE_ROSE_BUSH_ITEM = ITEMS_DEFERRED_REGISTRY.register("growable_rose_bush", () -> new BlockItem(GROWABLE_ROSE_BUSH.get(), new Item.Properties()));
    public static final RegistryObject<Item> GROWABLE_SUNFLOWER_ITEM = ITEMS_DEFERRED_REGISTRY.register("growable_sunflower", () -> new BlockItem(GROWABLE_SUNFLOWER.get(), new Item.Properties()));
    public static final RegistryObject<Item> GROWABLE_TALL_GRASS_ITEM = ITEMS_DEFERRED_REGISTRY.register("growable_tall_grass", () -> new BlockItem(GROWABLE_TALL_GRASS.get(), new Item.Properties()));
    public static final RegistryObject<Item> GROWABLE_LARGE_FERN_ITEM = ITEMS_DEFERRED_REGISTRY.register("growable_large_fern", () -> new BlockItem(GROWABLE_LARGE_FERN.get(), new Item.Properties()));

    /*
    * Spawn Eggs
    */
    private static final RegistryObject<ForgeSpawnEggItem> ENHANCED_AXOLOTL_EGG = ITEMS_DEFERRED_REGISTRY.register("enhanced_axolotl_spawn_egg", () ->  new ForgeSpawnEggItem(ModEntities.ENHANCED_AXOLOTL, 0x632B63, 0xA62D74, new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP)));
    private static final RegistryObject<ForgeSpawnEggItem> ENHANCED_TURTLE_EGG = ITEMS_DEFERRED_REGISTRY.register("enhanced_turtle_spawn_egg", () -> new ForgeSpawnEggItem(ModEntities.ENHANCED_TURTLE, 0xFFFFDD, 0x00DDCC, new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP)));
    private static final RegistryObject<ForgeSpawnEggItem> ENHANCED_CHICKEN_EGG = ITEMS_DEFERRED_REGISTRY.register("enhanced_chicken_spawn_egg", () ->  new ForgeSpawnEggItem(ModEntities.ENHANCED_CHICKEN, 0xFFFCF0,0xCC0000, new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP)));
    private static final RegistryObject<ForgeSpawnEggItem> ENHANCED_LLAMA_EGG = ITEMS_DEFERRED_REGISTRY.register("enhanced_llama_spawn_egg", () ->  new ForgeSpawnEggItem(ModEntities.ENHANCED_LLAMA, 0xCDB29C, 0x7B4B34, new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP)));
    private static final RegistryObject<ForgeSpawnEggItem> ENHANCED_SHEEP_EGG = ITEMS_DEFERRED_REGISTRY.register("enhanced_sheep_spawn_egg", () -> new ForgeSpawnEggItem(ModEntities.ENHANCED_SHEEP, 0xFFFFFF, 0xFF8C8C, new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP)));
    private static final RegistryObject<ForgeSpawnEggItem> ENHANCED_RABBIT_EGG = ITEMS_DEFERRED_REGISTRY.register("enhanced_rabbit_spawn_egg", () ->  new ForgeSpawnEggItem(ModEntities.ENHANCED_RABBIT, 0xCA8349, 0x553C36, new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP)));
    private static final RegistryObject<ForgeSpawnEggItem> ENHANCED_COW_EGG = ITEMS_DEFERRED_REGISTRY.register("enhanced_cow_spawn_egg", () -> new ForgeSpawnEggItem(ModEntities.ENHANCED_COW, 0x260800,0xf9f9f7, new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP)));
    private static final RegistryObject<ForgeSpawnEggItem> ENHANCED_MOOSHROOM_EGG = ITEMS_DEFERRED_REGISTRY.register("enhanced_mooshroom_spawn_egg", () ->  new ForgeSpawnEggItem(ModEntities.ENHANCED_MOOSHROOM, 0xFF0000,0xCCCCCC, new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP)));
    private static final RegistryObject<ForgeSpawnEggItem> ENHANCED_PIG_EGG = ITEMS_DEFERRED_REGISTRY.register("enhanced_pig_spawn_egg", () ->  new ForgeSpawnEggItem(ModEntities.ENHANCED_PIG, 0xFFA4A4,0xB34d4d, new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP)));

    public static void register(IEventBus modEventBus) {
        ITEMS_DEFERRED_REGISTRY.register(modEventBus);
    }


}
