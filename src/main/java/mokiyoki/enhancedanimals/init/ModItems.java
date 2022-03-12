package mokiyoki.enhancedanimals.init;

import mokiyoki.enhancedanimals.EnhancedAnimals;
import mokiyoki.enhancedanimals.items.*;
import mokiyoki.enhancedanimals.util.Reference;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

/**
 * Created by moki on 24/08/2018.
 */
//@ObjectHolder(Reference.MODID)
public class ModItems {

    private static final DeferredRegister<Item> ITEMS_DEFERRED_REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, Reference.MODID);

    public static final RegistryObject<Item> EGG_WHITE = createDeferredItem("egg_white", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_CREAMLIGHT = createDeferredItem("egg_creamlight", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_CREAM = createDeferredItem("egg_cream", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_CREAMDARK = createDeferredItem("egg_creamdark", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_CREAMDARKEST = createDeferredItem("egg_creamdarkest", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));  //carmel
    public static final RegistryObject<Item> EGG_CARMELDARK = createDeferredItem("egg_carmeldark", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_GARNET = createDeferredItem("egg_garnet", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_PINKLIGHT = createDeferredItem("egg_pinklight", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_PINK = createDeferredItem("egg_pink", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_PINKDARK = createDeferredItem("egg_pinkdark", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_PINKDARKEST = createDeferredItem("egg_pinkdarkest", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));  //cherry
    public static final RegistryObject<Item> EGG_CHERRYDARK = createDeferredItem("egg_cherrydark", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_PLUM = createDeferredItem("egg_plum", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_BROWNLIGHT = createDeferredItem("egg_brownlight", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_BROWN = createDeferredItem("egg_brown", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_BROWNDARK = createDeferredItem("egg_browndark", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_CHOCOLATE = createDeferredItem("egg_chocolate", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_CHOCOLATEDARK = createDeferredItem("egg_chocolatedark", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_CHOCOLATECOSMOS = createDeferredItem("egg_chocolatecosmos", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_BLUE = createDeferredItem("egg_blue", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_GREENLIGHT = createDeferredItem("egg_greenlight", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_GREENYELLOW = createDeferredItem("egg_greenyellow", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_OLIVELIGHT = createDeferredItem("egg_olivelight", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_OLIVE = createDeferredItem("egg_olive", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_OLIVEDARK = createDeferredItem("egg_olivedark", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_ARMY = createDeferredItem("egg_army", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_BLUEGREY = createDeferredItem("egg_bluegrey", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_GREY = createDeferredItem("egg_grey", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_GREYGREEN = createDeferredItem("egg_greygreen", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_AVOCADO = createDeferredItem("egg_avocado", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_AVOCADODARK = createDeferredItem("egg_avocadodark", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_FELDGRAU = createDeferredItem("egg_feldgrau", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_MINT = createDeferredItem("egg_mint", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_GREEN = createDeferredItem("egg_green", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_GREENDARK = createDeferredItem("egg_greendark", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_PINE = createDeferredItem("egg_pine", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_PINEDARK = createDeferredItem("egg_pinedark", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_PINEBLACK = createDeferredItem("egg_pineblack", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_POWDERBLUE = createDeferredItem("egg_powderblue", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_TEA = createDeferredItem("egg_tea", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_MATCHA = createDeferredItem("egg_matcha", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_MATCHADARK = createDeferredItem("egg_matchadark", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_MOSS = createDeferredItem("egg_moss", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_MOSSDARK = createDeferredItem("egg_mossdark", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_GREENUMBER = createDeferredItem("egg_greenumber", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_GREYBLUE = createDeferredItem("egg_greyblue", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_GREYNEUTRAL = createDeferredItem("egg_greyneutral", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_LAUREL = createDeferredItem("egg_laurel", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_RESEDA = createDeferredItem("egg_reseda", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_GREENPEWTER = createDeferredItem("egg_greenpewter", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_GREYDARK = createDeferredItem("egg_greydark", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_CELADON = createDeferredItem("egg_celadon", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_FERN = createDeferredItem("egg_fern", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_ASPARAGUS = createDeferredItem("egg_asparagus", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_HUNTER = createDeferredItem("egg_hunter", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_HUNTERDARK = createDeferredItem("egg_hunterdark", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_TREEDARK = createDeferredItem("egg_treedark", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_PALEBLUE = createDeferredItem("egg_paleblue", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_HONEYDEW = createDeferredItem("egg_honeydew", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_EARTH = createDeferredItem("egg_earth", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_KHAKI = createDeferredItem("egg_khaki", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_GRULLO = createDeferredItem("egg_grullo", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_KHAKIDARK = createDeferredItem("egg_khakidark", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_CAROB = createDeferredItem("egg_carob", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_COOLGREY = createDeferredItem("egg_coolgrey", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_PINKGREY = createDeferredItem("egg_pinkgrey", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_WARMGREY = createDeferredItem("egg_warmgrey", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_ARTICHOKE = createDeferredItem("egg_artichoke", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_MYRTLEGREY = createDeferredItem("egg_myrtlegrey", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_RIFLE = createDeferredItem("egg_rifle", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_JADE = createDeferredItem("egg_jade", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_PISTACHIO = createDeferredItem("egg_pistachio", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_SAGE = createDeferredItem("egg_sage", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_ROSEMARY = createDeferredItem("egg_rosemary", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_GREENBROWN = createDeferredItem("egg_greenbrown", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_UMBER = createDeferredItem("egg_umber", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));

    public static final RegistryObject<Item> EGG_CREAM_SPOT = createDeferredItem("egg_cream_spot", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_CREAMDARK_SPOT = createDeferredItem("egg_creamdark_spot", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_CARMEL_SPOT = createDeferredItem("egg_carmel_spot", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));  //carmel
    public static final RegistryObject<Item> EGG_CARMELDARK_SPOT = createDeferredItem("egg_carmeldark_spot", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_GARNET_SPOT = createDeferredItem("egg_garnet_spot", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_PINK_SPOT = createDeferredItem("egg_pink_spot", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_PINKDARK_SPOT = createDeferredItem("egg_pinkdark_spot", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_CHERRY_SPOT = createDeferredItem("egg_cherry_spot", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));  //cherry
    public static final RegistryObject<Item> EGG_CHERRYDARK_SPOT = createDeferredItem("egg_cherrydark_spot", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_PLUM_SPOT = createDeferredItem("egg_plum_spot", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_BROWNLIGHT_SPOT = createDeferredItem("egg_brownlight_spot", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_BROWN_SPOT = createDeferredItem("egg_brown_spot", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_BROWNDARK_SPOT = createDeferredItem("egg_browndark_spot", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_CHOCOLATE_SPOT = createDeferredItem("egg_chocolate_spot", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_CHOCOLATEDARK_SPOT = createDeferredItem("egg_chocolatedark_spot", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_GREENYELLOW_SPOT = createDeferredItem("egg_greenyellow_spot", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_OLIVELIGHT_SPOT = createDeferredItem("egg_olivelight_spot", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_OLIVE_SPOT = createDeferredItem("egg_olive_spot", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_OLIVEDARK_SPOT = createDeferredItem("egg_olivedark_spot", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_ARMY_SPOT = createDeferredItem("egg_army_spot", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_GREY_SPOT = createDeferredItem("egg_grey_spot", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_GREYGREEN_SPOT = createDeferredItem("egg_greygreen_spot", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_AVOCADO_SPOT = createDeferredItem("egg_avocado_spot", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_AVOCADODARK_SPOT = createDeferredItem("egg_avocadodark_spot", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_FELDGRAU_SPOT = createDeferredItem("egg_feldgrau_spot", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_MINT_SPOT = createDeferredItem("egg_mint_spot", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_GREEN_SPOT = createDeferredItem("egg_green_spot", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_GREENDARK_SPOT = createDeferredItem("egg_greendark_spot", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_PINE_SPOT = createDeferredItem("egg_pine_spot", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_PINEDARK_SPOT = createDeferredItem("egg_pinedark_spot", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_PINEBLACK_SPOT = createDeferredItem("egg_pineblack_spot", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));

    public static final RegistryObject<Item> EGG_CREAM_SPATTER = createDeferredItem("egg_cream_spatter", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_CREAMDARK_SPATTER = createDeferredItem("egg_creamdark_spatter", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_CARMEL_SPATTER = createDeferredItem("egg_carmel_spatter", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));  //carmel
    public static final RegistryObject<Item> EGG_CARMELDARK_SPATTER = createDeferredItem("egg_carmeldark_spatter", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_GARNET_SPATTER = createDeferredItem("egg_garnet_spatter", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_PINK_SPATTER = createDeferredItem("egg_pink_spatter", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_PINKDARK_SPATTER = createDeferredItem("egg_pinkdark_spatter", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_CHERRY_SPATTER = createDeferredItem("egg_cherry_spatter", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));  //cherry
    public static final RegistryObject<Item> EGG_CHERRYDARK_SPATTER = createDeferredItem("egg_cherrydark_spatter", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_PLUM_SPATTER = createDeferredItem("egg_plum_spatter", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_BROWNLIGHT_SPATTER = createDeferredItem("egg_brownlight_spatter", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_BROWN_SPATTER = createDeferredItem("egg_brown_spatter", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_BROWNDARK_SPATTER = createDeferredItem("egg_browndark_spatter", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_CHOCOLATE_SPATTER = createDeferredItem("egg_chocolate_spatter", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_CHOCOLATEDARK_SPATTER = createDeferredItem("egg_chocolatedark_spatter", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_GREENYELLOW_SPATTER = createDeferredItem("egg_greenyellow_spatter", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_OLIVELIGHT_SPATTER = createDeferredItem("egg_olivelight_spatter", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_OLIVE_SPATTER = createDeferredItem("egg_olive_spatter", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_OLIVEDARK_SPATTER = createDeferredItem("egg_olivedark_spatter", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_ARMY_SPATTER = createDeferredItem("egg_army_spatter", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_GREY_SPATTER = createDeferredItem("egg_grey_spatter", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_GREYGREEN_SPATTER = createDeferredItem("egg_greygreen_spatter", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_AVOCADO_SPATTER = createDeferredItem("egg_avocado_spatter", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_AVOCADODARK_SPATTER = createDeferredItem("egg_avocadodark_spatter", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_FELDGRAU_SPATTER = createDeferredItem("egg_feldgrau_spatter", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_MINT_SPATTER = createDeferredItem("egg_mint_spatter", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_GREEN_SPATTER = createDeferredItem("egg_green_spatter", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_GREENDARK_SPATTER = createDeferredItem("egg_greendark_spatter", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_PINE_SPATTER = createDeferredItem("egg_pine_spatter", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_PINEDARK_SPATTER = createDeferredItem("egg_pinedark_spatter", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_PINEBLACK_SPATTER = createDeferredItem("egg_pineblack_spatter", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));

    public static final RegistryObject<Item> EGG_CREAM_SPECKLE = createDeferredItem("egg_cream_speckle", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_CREAMDARK_SPECKLE = createDeferredItem("egg_creamdark_speckle", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_CARMEL_SPECKLE = createDeferredItem("egg_carmel_speckle", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));  //carmel
    public static final RegistryObject<Item> EGG_CARMELDARK_SPECKLE = createDeferredItem("egg_carmeldark_speckle", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_GARNET_SPECKLE = createDeferredItem("egg_garnet_speckle", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_PINK_SPECKLE = createDeferredItem("egg_pink_speckle", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_PINKDARK_SPECKLE = createDeferredItem("egg_pinkdark_speckle", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_CHERRY_SPECKLE = createDeferredItem("egg_cherry_speckle", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));  //cherry
    public static final RegistryObject<Item> EGG_CHERRYDARK_SPECKLE = createDeferredItem("egg_cherrydark_speckle", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_PLUM_SPECKLE = createDeferredItem("egg_plum_speckle", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_BROWNLIGHT_SPECKLE = createDeferredItem("egg_brownlight_speckle", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_BROWN_SPECKLE = createDeferredItem("egg_brown_speckle", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_BROWNDARK_SPECKLE = createDeferredItem("egg_browndark_speckle", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_CHOCOLATE_SPECKLE = createDeferredItem("egg_chocolate_speckle", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_CHOCOLATEDARK_SPECKLE = createDeferredItem("egg_chocolatedark_speckle", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_GREENYELLOW_SPECKLE = createDeferredItem("egg_greenyellow_speckle", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_OLIVELIGHT_SPECKLE = createDeferredItem("egg_olivelight_speckle", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_OLIVE_SPECKLE = createDeferredItem("egg_olive_speckle", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_OLIVEDARK_SPECKLE = createDeferredItem("egg_olivedark_speckle", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_ARMY_SPECKLE = createDeferredItem("egg_army_speckle", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_GREY_SPECKLE = createDeferredItem("egg_grey_speckle", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_GREYGREEN_SPECKLE = createDeferredItem("egg_greygreen_speckle", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_AVOCADO_SPECKLE = createDeferredItem("egg_avocado_speckle", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_AVOCADODARK_SPECKLE = createDeferredItem("egg_avocadodark_speckle", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_FELDGRAU_SPECKLE = createDeferredItem("egg_feldgrau_speckle", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_MINT_SPECKLE = createDeferredItem("egg_mint_speckle", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_GREEN_SPECKLE = createDeferredItem("egg_green_speckle", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_GREENDARK_SPECKLE = createDeferredItem("egg_greendark_speckle", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_PINE_SPECKLE = createDeferredItem("egg_pine_speckle", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_PINEDARK_SPECKLE = createDeferredItem("egg_pinedark_speckle", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_PINEBLACK_SPECKLE = createDeferredItem("egg_pineblack_speckle", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));

    public static final RegistryObject<Item> EGG_EARTH_SPOT = createDeferredItem("egg_earth_spot", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_KHAKI_SPOT = createDeferredItem("egg_khaki_spot", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_GRULLO_SPOT = createDeferredItem("egg_grullo_spot", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_KHAKIDARK_SPOT = createDeferredItem("egg_khakidark_spot", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_CAROB_SPOT = createDeferredItem("egg_carob_spot", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_PINKGREY_SPOT = createDeferredItem("egg_pinkgrey_spot", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_WARMGREY_SPOT = createDeferredItem("egg_warmgrey_spot", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_ARTICHOKE_SPOT = createDeferredItem("egg_artichoke_spot", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_MYRTLEGREY_SPOT = createDeferredItem("egg_myrtlegrey_spot", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_RIFLE_SPOT = createDeferredItem("egg_rifle_spot", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_JADE_SPOT = createDeferredItem("egg_jade_spot", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_PISTACHIO_SPOT = createDeferredItem("egg_pistachio_spot", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_SAGE_SPOT = createDeferredItem("egg_sage_spot", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_ROSEMARY_SPOT = createDeferredItem("egg_rosemary_spot", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_GREENBROWN_SPOT = createDeferredItem("egg_greenbrown_spot", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_UMBER_SPOT = createDeferredItem("egg_umber_spot", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> Egg_Matcha_Spot = createDeferredItem("egg_matcha_spot", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> Egg_MatchaDark_Spot = createDeferredItem("egg_matchadark_spot", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_MOSS_SPOT = createDeferredItem("egg_moss_spot", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_MOSSDARK_SPOT = createDeferredItem("egg_mossdark_spot", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_GREENUMBER_SPOT = createDeferredItem("egg_greenumber_spot", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_GREYNEUTRAL_SPOT = createDeferredItem("egg_greyneutral_spot", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_LAUREL_SPOT = createDeferredItem("egg_laurel_spot", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_RESEDA_SPOT = createDeferredItem("egg_reseda_spot", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_GREENPEWTER_SPOT = createDeferredItem("egg_greenpewter_spot", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_GREYDARK_SPOT = createDeferredItem("egg_greydark_spot", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_CELADON_SPOT = createDeferredItem("egg_celadon_spot", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_FERN_SPOT = createDeferredItem("egg_fern_spot", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_ASPARAGUS_SPOT = createDeferredItem("egg_asparagus_spot", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_HUNTER_SPOT = createDeferredItem("egg_hunter_spot", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_HUNTERDARK_SPOT = createDeferredItem("egg_hunterdark_spot", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_TREEDARK_SPOT = createDeferredItem("egg_treedark_spot", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));

    public static final RegistryObject<Item> EGG_EARTH_SPATTER = createDeferredItem("egg_earth_spatter", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_KHAKI_SPATTER = createDeferredItem("egg_khaki_spatter", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_GRULLO_SPATTER = createDeferredItem("egg_grullo_spatter", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_KHAKIDARK_SPATTER = createDeferredItem("egg_khakidark_spatter", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_CAROB_SPATTER = createDeferredItem("egg_carob_spatter", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_PINKGREY_SPATTER = createDeferredItem("egg_pinkgrey_spatter", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_WARMGREY_SPATTER = createDeferredItem("egg_warmgrey_spatter", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_ARTICHOKE_SPATTER = createDeferredItem("egg_artichoke_spatter", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_MYRTLEGREY_SPATTER = createDeferredItem("egg_myrtlegrey_spatter", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_RIFLE_SPATTER = createDeferredItem("egg_rifle_spatter", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_JADE_SPATTER = createDeferredItem("egg_jade_spatter", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_PISTACHIO_SPATTER = createDeferredItem("egg_pistachio_spatter", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_SAGE_SPATTER = createDeferredItem("egg_sage_spatter", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_ROSEMARY_SPATTER = createDeferredItem("egg_rosemary_spatter", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_GREENBROWN_SPATTER = createDeferredItem("egg_greenbrown_spatter", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_UMBER_SPATTER = createDeferredItem("egg_umber_spatter", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_MATCHA_SPATTER = createDeferredItem("egg_matcha_spatter", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_MATCHADARK_SPATTER = createDeferredItem("egg_matchadark_spatter", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_MOSS_SPATTER = createDeferredItem("egg_moss_spatter", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_MOSSDARK_SPATTER = createDeferredItem("egg_mossdark_spatter", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_GREENUMBER_SPATTER = createDeferredItem("egg_greenumber_spatter", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_GREYNEUTRAL_SPATTER = createDeferredItem("egg_greyneutral_spatter", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_LAUREL_SPATTER = createDeferredItem("egg_laurel_spatter", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_RESEDA_SPATTER = createDeferredItem("egg_reseda_spatter", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_GREENPEWTER_SPATTER = createDeferredItem("egg_greenpewter_spatter", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_GREYDARK_SPATTER = createDeferredItem("egg_greydark_spatter", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_CELADON_SPATTER = createDeferredItem("egg_celadon_spatter", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_FERN_SPATTER = createDeferredItem("egg_fern_spatter", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_ASPARAGUS_SPATTER = createDeferredItem("egg_asparagus_spatter", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_HUNTER_SPATTER = createDeferredItem("egg_hunter_spatter", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_HUNTERDARK_SPATTER = createDeferredItem("egg_hunterdark_spatter", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_TREEDARK_SPATTER = createDeferredItem("egg_treedark_spatter", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));

    public static final RegistryObject<Item> EGG_EARTH_SPECKLE = createDeferredItem("egg_earth_speckle", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_KHAKI_SPECKLE = createDeferredItem("egg_khaki_speckle", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_GRULLO_SPECKLE = createDeferredItem("egg_grullo_speckle", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_KHAKIDARK_SPECKLE = createDeferredItem("egg_khakidark_speckle", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_CAROB_SPECKLE = createDeferredItem("egg_carob_speckle", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_PINKGREY_SPECKLE = createDeferredItem("egg_pinkgrey_speckle", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_WARMGREY_SPECKLE = createDeferredItem("egg_warmgrey_speckle", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_ARTICHOKE_SPECKLE = createDeferredItem("egg_artichoke_speckle", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_MYRTLEGREY_SPECKLE = createDeferredItem("egg_myrtlegrey_speckle", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_RIFLE_SPECKLE = createDeferredItem("egg_rifle_speckle", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_JADE_SPECKLE = createDeferredItem("egg_jade_speckle", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_PISTACHIO_SPECKLE = createDeferredItem("egg_pistachio_speckle", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_SAGE_SPECKLE = createDeferredItem("egg_sage_speckle", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_ROSEMARY_SPECKLE = createDeferredItem("egg_rosemary_speckle", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_GREENBROWN_SPECKLE = createDeferredItem("egg_greenbrown_speckle", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_UMBER_SPECKLE = createDeferredItem("egg_umber_speckle", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_MATCHA_SPECKLE = createDeferredItem("egg_matcha_speckle", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_MATCHADARK_SPECKLE = createDeferredItem("egg_matchadark_speckle", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_MOSS_SPECKLE = createDeferredItem("egg_moss_speckle", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_MOSSDARK_SPECKLE = createDeferredItem("egg_mossdark_speckle", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_GREENUMBER_SPECKLE = createDeferredItem("egg_greenumber_speckle", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_GREYNEUTRAL_SPECKLE = createDeferredItem("egg_greyneutral_speckle", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_LAUREL_SPECKLE = createDeferredItem("egg_laurel_speckle", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_RESEDA_SPECKLE = createDeferredItem("egg_reseda_speckle", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_GREENPEWTER_SPECKLE = createDeferredItem("egg_greenpewter_speckle", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_GREYDARK_SPECKLE = createDeferredItem("egg_greydark_speckle", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_CELADON_SPECKLE = createDeferredItem("egg_celadon_speckle", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_FERN_SPECKLE = createDeferredItem("egg_fern_speckle", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_ASPARAGUS_SPECKLE = createDeferredItem("egg_asparagus_speckle", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_HUNTER_SPECKLE = createDeferredItem("egg_hunter_speckle", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_HUNTERDARK_SPECKLE = createDeferredItem("egg_hunterdark_speckle", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_TREEDARK_SPECKLE = createDeferredItem("egg_treedark_speckle", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));

    public static final RegistryObject<Item> RAWCHICKEN_DARKSMALL = createDeferredItem("rawchicken_darksmall", new RawChicken(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(64).food((new FoodProperties.Builder()).nutrition(1).saturationMod(0.2F).effect(new MobEffectInstance(MobEffects.HUNGER, 600, 0), 0.3F).meat().build())));
    public static final RegistryObject<Item> RAWCHICKEN_DARK = createDeferredItem("rawchicken_dark", new RawChicken(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(64).food((new FoodProperties.Builder()).nutrition(1).saturationMod(0.3F).effect(new MobEffectInstance(MobEffects.HUNGER, 600, 0), 0.3F).meat().build())));
    public static final RegistryObject<Item> RAWCHICKEN_DARKBIG = createDeferredItem("rawchicken_darkbig", new RawChicken(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(64).food((new FoodProperties.Builder()).nutrition(2).saturationMod(0.3F).effect(new MobEffectInstance(MobEffects.HUNGER, 600, 0), 0.3F).meat().build())));
    public static final RegistryObject<Item> RAWCHICKEN_PALESMALL = createDeferredItem("rawchicken_palesmall", new RawChicken(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(64).food((new FoodProperties.Builder()).nutrition(1).saturationMod(0.2F).effect(new MobEffectInstance(MobEffects.HUNGER, 600, 0), 0.3F).meat().build())));
    public static final RegistryObject<Item> RAWCHICKEN_PALE = createDeferredItem("rawchicken_pale", new RawChicken(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(64).food((new FoodProperties.Builder()).nutrition(1).saturationMod(0.3F).effect(new MobEffectInstance(MobEffects.HUNGER, 600, 0), 0.3F).meat().build())));
    public static final RegistryObject<Item> COOKEDCHICKEN_DARKSMALL = createDeferredItem("cookedchicken_darksmall", new CookedChicken(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(64).food((new FoodProperties.Builder()).nutrition(2).saturationMod(0.6F).meat().build())));
    public static final RegistryObject<Item> COOKEDCHICKEN_DARK = createDeferredItem("cookedchicken_dark", new CookedChicken(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(64).food((new FoodProperties.Builder()).nutrition(4).saturationMod(0.6F).meat().build())));
    public static final RegistryObject<Item> COOKEDCHICKEN_DARKBIG = createDeferredItem("cookedchicken_darkbig", new CookedChicken(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(64).food((new FoodProperties.Builder()).nutrition(6).saturationMod(0.6F).meat().build())));
    public static final RegistryObject<Item> COOKEDCHICKEN_PALESMALL = createDeferredItem("cookedchicken_palesmall", new CookedChicken(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(64).food((new FoodProperties.Builder()).nutrition(2).saturationMod(0.6F).meat().build())));
    public static final RegistryObject<Item> COOKEDCHICKEN_PALE = createDeferredItem("cookedchicken_pale", new CookedChicken(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(64).food((new FoodProperties.Builder()).nutrition(4).saturationMod(0.6F).meat().build())));
    public static final RegistryObject<Item> RAWRABBIT_SMALL = createDeferredItem("rawrabbit_small", new RawRabbit(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(64).food((new FoodProperties.Builder()).nutrition(2).saturationMod(0.3F).meat().build())));
    public static final RegistryObject<Item> COOKEDRABBIT_SMALL = createDeferredItem("cookedrabbit_small", new CookedRabbit(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(64).food((new FoodProperties.Builder()).nutrition(3).saturationMod(0.6F).meat().build())));
    public static final RegistryObject<Item> RABBITSTEW_WEAK = createDeferredItem("rabbitstew_weak", new RabbitStew(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(64).food((new FoodProperties.Builder()).nutrition(8).saturationMod(0.5F).meat().build())));

    public static final RegistryObject<Item> HALF_MILK_BOTTLE = createDeferredItem("half_milk_bottle", new MilkBottleHalf(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1).craftRemainder(Items.GLASS_BOTTLE).food((new FoodProperties.Builder()).alwaysEat().nutrition(0).saturationMod(0F).build())));
    public static final RegistryObject<Item> MILK_BOTTLE = createDeferredItem("milk_bottle", new MilkBottle(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1).craftRemainder(Items.GLASS_BOTTLE).food((new FoodProperties.Builder()).alwaysEat().nutrition(0).saturationMod(0F).build())));
    public static final RegistryObject<Item> ONESIXTH_MILK_BUCKET = createDeferredItem("onesixth_milk_bucket", new MilkBucketOneSixth(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1).craftRemainder(Items.BUCKET).food((new FoodProperties.Builder()).alwaysEat().nutrition(0).saturationMod(0F).build())));
    public static final RegistryObject<Item> ONETHIRD_MILK_BUCKET = createDeferredItem("onethird_milk_bucket", new MilkBucketOneThird(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1).craftRemainder(Items.BUCKET).food((new FoodProperties.Builder()).alwaysEat().nutrition(0).saturationMod(0F).build())));
    public static final RegistryObject<Item> HALF_MILK_BUCKET = createDeferredItem("half_milk_bucket", new MilkBucketHalf(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1).craftRemainder(Items.BUCKET).food((new FoodProperties.Builder()).alwaysEat().nutrition(0).saturationMod(0F).build())));
    public static final RegistryObject<Item> TWOTHIRDS_MILK_BUCKET = createDeferredItem("twothirds_milk_bucket", new MilkBucketTwoThirds(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1).craftRemainder(Items.BUCKET).food((new FoodProperties.Builder()).alwaysEat().nutrition(0).saturationMod(0F).build())));
    public static final RegistryObject<Item> FIVESIXTHS_MILK_BUCKET = createDeferredItem("fivesixths_milk_bucket", new MilkBucketFiveSixths(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1).craftRemainder(Items.BUCKET).food((new FoodProperties.Builder()).alwaysEat().nutrition(0).saturationMod(0F).build())));

                        //ITEMNAME_VARIENT_TEXTUREVARIENT
    public static final RegistryObject<Item> SADDLE_BASIC_LEATHER = createDeferredItem("saddle_basic_leather", new CustomizableSaddleVanilla(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1),10511680));
    public static final RegistryObject<Item> SADDLE_BASIC_LEATHER_GOLD = createDeferredItem("saddle_basic_leather_gold", new CustomizableSaddleVanilla(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1),10511680));
    public static final RegistryObject<Item> SADDLE_BASIC_LEATHER_DIAMOND = createDeferredItem("saddle_basic_leather_diamond", new CustomizableSaddleVanilla(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1),10511680));
    public static final RegistryObject<Item> SADDLE_BASIC_LEATHER_WOOD = createDeferredItem("saddle_basic_leather_wood", new CustomizableSaddleVanilla(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1),10511680));
    public static final RegistryObject<Item> SADDLE_BASIC_CLOTH = createDeferredItem("saddle_basic_cloth", new CustomizableSaddleVanilla(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1),16777215));
    public static final RegistryObject<Item> SADDLE_BASIC_CLOTH_GOLD = createDeferredItem("saddle_basic_cloth_gold", new CustomizableSaddleVanilla(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1),16777215));
    public static final RegistryObject<Item> SADDLE_BASIC_CLOTH_DIAMOND = createDeferredItem("saddle_basic_cloth_diamond", new CustomizableSaddleVanilla(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1),16777215));
    public static final RegistryObject<Item> SADDLE_BASIC_CLOTH_WOOD = createDeferredItem("saddle_basic_cloth_wood", new CustomizableSaddleVanilla(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1),16777215));
    public static final RegistryObject<Item> SADDLE_BASIC_LEATHERCLOTHSEAT = createDeferredItem("saddle_basic_leatherclothseat", new CustomizableSaddleVanilla(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1),16777215));
    public static final RegistryObject<Item> SADDLE_BASIC_LEATHERCLOTHSEAT_GOLD = createDeferredItem("saddle_basic_leatherclothseat_gold", new CustomizableSaddleVanilla(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1),16777215));
    public static final RegistryObject<Item> SADDLE_BASIC_LEATHERCLOTHSEAT_DIAMOND = createDeferredItem("saddle_basic_leatherclothseat_diamond", new CustomizableSaddleVanilla(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1),16777215));
    public static final RegistryObject<Item> SADDLE_BASIC_LEATHERCLOTHSEAT_WOOD = createDeferredItem("saddle_basic_leatherclothseat_wood", new CustomizableSaddleVanilla(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1),16777215));
    public static final RegistryObject<Item> SADDLE_BASICPOMEL_LEATHER = createDeferredItem("saddle_basicpomel_leather", new CustomizableSaddleWestern(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1),10511680));
    public static final RegistryObject<Item> SADDLE_BASICPOMEL_LEATHER_GOLD = createDeferredItem("saddle_basicpomel_leather_gold", new CustomizableSaddleWestern(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1),10511680));
    public static final RegistryObject<Item> SADDLE_BASICPOMEL_LEATHER_DIAMOND = createDeferredItem("saddle_basicpomel_leather_diamond", new CustomizableSaddleWestern(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1),10511680));
    public static final RegistryObject<Item> SADDLE_BASICPOMEL_LEATHER_WOOD = createDeferredItem("saddle_basicpomel_leather_wood", new CustomizableSaddleWestern(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1),10511680));
    public static final RegistryObject<Item> SADDLE_BASICPOMEL_CLOTH = createDeferredItem("saddle_basicpomel_cloth", new CustomizableSaddleWestern(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1),16777215));
    public static final RegistryObject<Item> SADDLE_BASICPOMEL_CLOTH_GOLD = createDeferredItem("saddle_basicpomel_cloth_gold", new CustomizableSaddleWestern(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1),16777215));
    public static final RegistryObject<Item> SADDLE_BASICPOMEL_CLOTH_DIAMOND = createDeferredItem("saddle_basicpomel_cloth_diamond", new CustomizableSaddleWestern(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1),16777215));
    public static final RegistryObject<Item> SADDLE_BASICPOMEL_CLOTH_WOOD = createDeferredItem("saddle_basicpomel_cloth_wood", new CustomizableSaddleWestern(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1),16777215));
    public static final RegistryObject<Item> SADDLE_BASICPOMEL_LEATHERCLOTHSEAT = createDeferredItem("saddle_basicpomel_leatherclothseat", new CustomizableSaddleWestern(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1),16777215));
    public static final RegistryObject<Item> SADDLE_BASICPOMEL_LEATHERCLOTHSEAT_GOLD = createDeferredItem("saddle_basicpomel_leatherclothseat_gold", new CustomizableSaddleWestern(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1),16777215));
    public static final RegistryObject<Item> SADDLE_BASICPOMEL_LEATHERCLOTHSEAT_DIAMOND = createDeferredItem("saddle_basicpomel_leatherclothseat_diamond", new CustomizableSaddleWestern(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1),16777215));
    public static final RegistryObject<Item> SADDLE_BASICPOMEL_LEATHERCLOTHSEAT_WOOD = createDeferredItem("saddle_basicpomel_leatherclothseat_wood", new CustomizableSaddleWestern(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1),16777215));
    public static final RegistryObject<Item> SADDLE_ENGLISH_LEATHER = createDeferredItem("saddle_english_leather", new CustomizableSaddleEnglish(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1),10511680));
    public static final RegistryObject<Item> SADDLE_ENGLISH_LEATHER_GOLD = createDeferredItem("saddle_english_leather_gold", new CustomizableSaddleEnglish(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1),10511680));
    public static final RegistryObject<Item> SADDLE_ENGLISH_LEATHER_DIAMOND = createDeferredItem("saddle_english_leather_diamond", new CustomizableSaddleEnglish(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1),10511680));
    public static final RegistryObject<Item> SADDLE_ENGLISH_LEATHER_WOOD = createDeferredItem("saddle_english_leather_wood", new CustomizableSaddleEnglish(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1),10511680));
    public static final RegistryObject<Item> SADDLE_ENGLISH_CLOTH = createDeferredItem("saddle_english_cloth", new CustomizableSaddleEnglish(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1),16777215));
    public static final RegistryObject<Item> SADDLE_ENGLISH_CLOTH_GOLD = createDeferredItem("saddle_english_cloth_gold", new CustomizableSaddleEnglish(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1),16777215));
    public static final RegistryObject<Item> SADDLE_ENGLISH_CLOTH_DIAMOND = createDeferredItem("saddle_english_cloth_diamond", new CustomizableSaddleEnglish(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1),16777215));
    public static final RegistryObject<Item> SADDLE_ENGLISH_CLOTH_WOOD = createDeferredItem("saddle_english_cloth_wood", new CustomizableSaddleEnglish(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1),16777215));
    public static final RegistryObject<Item> SADDLE_ENGLISH_LEATHERCLOTHSEAT = createDeferredItem("saddle_english_leatherclothseat", new CustomizableSaddleEnglish(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1),16777215));
    public static final RegistryObject<Item> SADDLE_ENGLISH_LEATHERCLOTHSEAT_GOLD = createDeferredItem("saddle_english_leatherclothseat_gold", new CustomizableSaddleEnglish(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1),16777215));
    public static final RegistryObject<Item> SADDLE_ENGLISH_LEATHERCLOTHSEAT_DIAMOND = createDeferredItem("saddle_english_leatherclothseat_diamond", new CustomizableSaddleEnglish(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1),16777215));
    public static final RegistryObject<Item> SADDLE_ENGLISH_LEATHERCLOTHSEAT_WOOD = createDeferredItem("saddle_english_leatherclothseat_wood", new CustomizableSaddleEnglish(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1),16777215));
    public static final RegistryObject<Item> BRIDLE_BASIC_LEATHER = createDeferredItem("bridle_basic_leather", new CustomizableBridle(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1),10511680));
    public static final RegistryObject<Item> BRIDLE_BASIC_LEATHER_GOLD = createDeferredItem("bridle_basic_leather_gold", new CustomizableBridle(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1),10511680));
    public static final RegistryObject<Item> BRIDLE_BASIC_LEATHER_DIAMOND = createDeferredItem("bridle_basic_leather_diamond", new CustomizableBridle(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1),10511680));
    public static final RegistryObject<Item> BRIDLE_BASIC_CLOTH = createDeferredItem("bridle_basic_cloth", new CustomizableBridle(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1),16777215));
    public static final RegistryObject<Item> BRIDLE_BASIC_CLOTH_GOLD = createDeferredItem("bridle_basic_cloth_gold", new CustomizableBridle(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1),16777215));
    public static final RegistryObject<Item> BRIDLE_BASIC_CLOTH_DIAMOND = createDeferredItem("bridle_basic_cloth_diamond", new CustomizableBridle(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1),16777215));
    public static final RegistryObject<Item> COLLAR_BASIC_CLOTH = createDeferredItem("collar_basic_cloth", new CustomizableCollar(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1),16777215, false));
    public static final RegistryObject<Item> COLLAR_BASIC_CLOTH_IRONRING = createDeferredItem("collar_basic_cloth_ironring", new CustomizableCollar(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1),16777215, false));
    public static final RegistryObject<Item> COLLAR_BASIC_CLOTH_IRONBELL = createDeferredItem("collar_basic_cloth_ironbell", new CustomizableCollar(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1),16777215, true));
    public static final RegistryObject<Item> COLLAR_BASIC_CLOTH_GOLDRING = createDeferredItem("collar_basic_cloth_goldring", new CustomizableCollar(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1),16777215, false));
    public static final RegistryObject<Item> COLLAR_BASIC_CLOTH_GOLDBELL = createDeferredItem("collar_basic_cloth_goldbell", new CustomizableCollar(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1),16777215, true));
    public static final RegistryObject<Item> COLLAR_BASIC_CLOTH_DIAMONDRING = createDeferredItem("collar_basic_cloth_diamondring", new CustomizableCollar(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1),16777215, false));
    public static final RegistryObject<Item> COLLAR_BASIC_CLOTH_DIAMONDBELL = createDeferredItem("collar_basic_cloth_diamondbell", new CustomizableCollar(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1),16777215, true));
    public static final RegistryObject<Item> COLLAR_BASIC_LEATHER = createDeferredItem("collar_basic_leather", new CustomizableCollar(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1),10511680, false));
    public static final RegistryObject<Item> COLLAR_BASIC_LEATHER_IRONRING = createDeferredItem("collar_basic_leather_ironring", new CustomizableCollar(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1),10511680, false));
    public static final RegistryObject<Item> COLLAR_BASIC_LEATHER_IRONBELL = createDeferredItem("collar_basic_leather_ironbell", new CustomizableCollar(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1),10511680, true));
    public static final RegistryObject<Item> COLLAR_BASIC_LEATHER_GOLDRING = createDeferredItem("collar_basic_leather_goldring", new CustomizableCollar(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1),10511680, false));
    public static final RegistryObject<Item> COLLAR_BASIC_LEATHER_GOLDBELL = createDeferredItem("collar_basic_leather_goldbell", new CustomizableCollar(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1),10511680, true));
    public static final RegistryObject<Item> COLLAR_BASIC_LEATHER_DIAMONDRING = createDeferredItem("collar_basic_leather_diamondring", new CustomizableCollar(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1),10511680, false));
    public static final RegistryObject<Item> COLLAR_BASIC_LEATHER_DIAMONDBELL = createDeferredItem("collar_basic_leather_diamondbell", new CustomizableCollar(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1),10511680, true));

    public static final RegistryObject<Item> GENETICS_ENCYCLOPEDIA = createDeferredItem("genetics_encyclopedia", new GeneticsEncyclopedia(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> DEBUG_GENE_BOOK = createDeferredItem("debug_gene_book", new DebugGenesBook(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
//    public static final RegistryObject<Item> MIXABLE_MILK = createDeferredItem("rawchicken_darksmall", new MixableMilkBucket(new Item.Properties().group(EnhancedAnimals.GENETICS_ANIMALS_GROUP).maxStackSize(1)));

//    public static final RegistryObject<Item> ENHANCED_AXOLOTL_EGG = createDeferredItem("enhanced_axolotl_spawn_egg", new ForgeSpawnEggItem(ENHANCED_AXOLOTL, 0xFFFFDD,0x00DDCC, new Item.Properties()));

    private static final RegistryObject<ForgeSpawnEggItem> ENHANCED_AXOLOTL_EGG = createDeferredSpawnEgg("enhanced_axolotl_spawn_egg", new ForgeSpawnEggItem(ModEntities.ENHANCED_AXOLOTL, 0x632B63, 0xA62D74, new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP)));
//    private static final RegistryObject<ForgeSpawnEggItem> ENHANCED_TURTLE_EGG = createDeferredSpawnEgg("enhanced_turtle_spawn_egg", new ForgeSpawnEggItem(ModEntities.ENHANCED_TURTLE, 0xFFFFDD, 0x00DDCC, new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP)));
    private static final RegistryObject<ForgeSpawnEggItem> ENHANCED_CHICKEN_EGG = createDeferredSpawnEgg("enhanced_chicken_spawn_egg", new ForgeSpawnEggItem(ModEntities.ENHANCED_CHICKEN, 0xFFFCF0,0xCC0000, new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP)));
//    private static final RegistryObject<ForgeSpawnEggItem> ENHANCED_LLAMA_EGG = createDeferredSpawnEgg("enhanced_llama_spawn_egg", new ForgeSpawnEggItem(ModEntities.ENHANCED_LLAMA, 0xCDB29C, 0x7B4B34, new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP)));
//    private static final RegistryObject<ForgeSpawnEggItem> ENHANCED_SHEEP_EGG = createDeferredSpawnEgg("enhanced_sheep_spawn_egg", new ForgeSpawnEggItem(ModEntities.ENHANCED_SHEEP, 0xFFFFFF, 0xFF8C8C, new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP)));
//    private static final RegistryObject<ForgeSpawnEggItem> ENHANCED_RABBIT_EGG = createDeferredSpawnEgg("enhanced_rabbit_spawn_egg", new ForgeSpawnEggItem(ModEntities.ENHANCED_RABBIT, 0xCA8349, 0x553C36, new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP)));
//    private static final RegistryObject<ForgeSpawnEggItem> ENHANCED_COW_EGG = createDeferredSpawnEgg("enhanced_cow_spawn_egg", new ForgeSpawnEggItem(ModEntities.ENHANCED_COW, 0x260800,0xf9f9f7, new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP)));
//    private static final RegistryObject<ForgeSpawnEggItem> ENHANCED_MOOSHROOM_EGG = createDeferredSpawnEgg("enhanced_mooshroom_spawn_egg", new ForgeSpawnEggItem(ModEntities.ENHANCED_MOOSHROOM, 0xFF0000,0xCCCCCC, new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP)));
//    private static final RegistryObject<ForgeSpawnEggItem> ENHANCED_PIG_EGG = createDeferredSpawnEgg("enhanced_pig_spawn_egg", new ForgeSpawnEggItem(ModEntities.ENHANCED_PIG, 0xFFA4A4,0xB34d4d, new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP)));

    private static RegistryObject<Item> createDeferredItem(String registryName, Item item) {
        return ITEMS_DEFERRED_REGISTRY.register(registryName, () -> item);
    }

    private static RegistryObject<ForgeSpawnEggItem> createDeferredSpawnEgg(String registryName, ForgeSpawnEggItem spawnEggItem) {
        return ITEMS_DEFERRED_REGISTRY.register(registryName, () -> spawnEggItem);
    }

//    private static RegistryObject<ForgeSpawnEggItem> createDeferred(String registryName, Item item) {
//        return ITEMS_DEFERRED_REGISTRY.register(registryName, () -> item);
//    }

    public static void register(IEventBus modEventBus) {
        ITEMS_DEFERRED_REGISTRY.register(modEventBus);
    }


}
