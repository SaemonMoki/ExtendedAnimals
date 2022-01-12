package mokiyoki.enhancedanimals.init;

import mokiyoki.enhancedanimals.EnhancedAnimals;
import mokiyoki.enhancedanimals.items.*;
import mokiyoki.enhancedanimals.util.Reference;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ObjectHolder;
import net.minecraftforge.registries.RegistryObject;

import static mokiyoki.enhancedanimals.util.handlers.EventRegistry.ENHANCED_AXOLOTL;

/**
 * Created by moki on 24/08/2018.
 */
//@ObjectHolder(Reference.MODID)
public class ModItems {

    private static final DeferredRegister<Item> ITEMS_DEFERRED_REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, Reference.MODID);

    public static final RegistryObject<Item> EGG_WHITE = createDeferred("egg_white", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_CREAMLIGHT = createDeferred("egg_creamlight", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_CREAM = createDeferred("egg_cream", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_CREAMDARK = createDeferred("egg_creamdark", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_CREAMDARKEST = createDeferred("egg_creamdarkest", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));  //carmel
    public static final RegistryObject<Item> EGG_CARMELDARK = createDeferred("egg_carmeldark", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_GARNET = createDeferred("egg_garnet", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_PINKLIGHT = createDeferred("egg_pinklight", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_PINK = createDeferred("egg_pink", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_PINKDARK = createDeferred("egg_pinkdark", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_PINKDARKEST = createDeferred("egg_pinkdarkest", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));  //cherry
    public static final RegistryObject<Item> EGG_CHERRYDARK = createDeferred("egg_cherrydark", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_PLUM = createDeferred("egg_plum", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_BROWNLIGHT = createDeferred("egg_brownlight", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_BROWN = createDeferred("egg_brown", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_BROWNDARK = createDeferred("egg_browndark", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_CHOCOLATE = createDeferred("egg_chocolate", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_CHOCOLATEDARK = createDeferred("egg_chocolatedark", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_CHOCOLATECOSMOS = createDeferred("egg_chocolatecosmos", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_BLUE = createDeferred("egg_blue", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_GREENLIGHT = createDeferred("egg_greenlight", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_GREENYELLOW = createDeferred("egg_greenyellow", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_OLIVELIGHT = createDeferred("egg_olivelight", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_OLIVE = createDeferred("egg_olive", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_OLIVEDARK = createDeferred("egg_olivedark", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_ARMY = createDeferred("egg_army", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_BLUEGREY = createDeferred("egg_bluegrey", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_GREY = createDeferred("egg_grey", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_GREYGREEN = createDeferred("egg_greygreen", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_AVOCADO = createDeferred("egg_avocado", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_AVOCADODARK = createDeferred("egg_avocadodark", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_FELDGRAU = createDeferred("egg_feldgrau", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_MINT = createDeferred("egg_mint", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_GREEN = createDeferred("egg_green", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_GREENDARK = createDeferred("egg_greendark", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_PINE = createDeferred("egg_pine", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_PINEDARK = createDeferred("egg_pinedark", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_PINEBLACK = createDeferred("egg_pineblack", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_POWDERBLUE = createDeferred("egg_powderblue", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_TEA = createDeferred("egg_tea", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_MATCHA = createDeferred("egg_matcha", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_MATCHADARK = createDeferred("egg_matchadark", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_MOSS = createDeferred("egg_moss", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_MOSSDARK = createDeferred("egg_mossdark", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_GREENUMBER = createDeferred("egg_greenumber", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_GREYBLUE = createDeferred("egg_greyblue", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_GREYNEUTRAL = createDeferred("egg_greyneutral", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_LAUREL = createDeferred("egg_laurel", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_RESEDA = createDeferred("egg_reseda", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_GREENPEWTER = createDeferred("egg_greenpewter", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_GREYDARK = createDeferred("egg_greydark", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_CELADON = createDeferred("egg_celadon", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_FERN = createDeferred("egg_fern", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_ASPARAGUS = createDeferred("egg_asparagus", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_HUNTER = createDeferred("egg_hunter", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_HUNTERDARK = createDeferred("egg_hunterdark", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_TREEDARK = createDeferred("egg_treedark", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_PALEBLUE = createDeferred("egg_paleblue", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_HONEYDEW = createDeferred("egg_honeydew", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_EARTH = createDeferred("egg_earth", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_KHAKI = createDeferred("egg_khaki", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_GRULLO = createDeferred("egg_grullo", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_KHAKIDARK = createDeferred("egg_khakidark", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_CAROB = createDeferred("egg_carob", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_COOLGREY = createDeferred("egg_coolgrey", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_PINKGREY = createDeferred("egg_pinkgrey", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_WARMGREY = createDeferred("egg_warmgrey", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_ARTICHOKE = createDeferred("egg_artichoke", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_MYRTLEGREY = createDeferred("egg_myrtlegrey", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_RIFLE = createDeferred("egg_rifle", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_JADE = createDeferred("egg_jade", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_PISTACHIO = createDeferred("egg_pistachio", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_SAGE = createDeferred("egg_sage", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_ROSEMARY = createDeferred("egg_rosemary", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_GREENBROWN = createDeferred("egg_greenbrown", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_UMBER = createDeferred("egg_umber", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));

    public static final RegistryObject<Item> Egg_Cream_Spot = createDeferred("egg_cream_spot", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> Egg_CreamDark_Spot = createDeferred("egg_creamdark_spot", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> Egg_Carmel_Spot = createDeferred("egg_carmel_spot", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));  //carmel
    public static final RegistryObject<Item> Egg_CarmelDark_Spot = createDeferred("egg_carmeldark_spot", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> Egg_Garnet_Spot = createDeferred("egg_garnet_spot", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> Egg_Pink_Spot = createDeferred("egg_pink_spot", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> Egg_PinkDark_Spot = createDeferred("egg_pinkdark_spot", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> Egg_Cherry_Spot = createDeferred("egg_cherry_spot", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));  //cherry
    public static final RegistryObject<Item> Egg_CherryDark_Spot = createDeferred("egg_cherrydark_spot", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> Egg_Plum_Spot = createDeferred("egg_plum_spot", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> Egg_BrownLight_Spot = createDeferred("egg_brownlight_spot", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> Egg_Brown_Spot = createDeferred("egg_brown_spot", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> Egg_BrownDark_Spot = createDeferred("egg_browndark_spot", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> Egg_Chocolate_Spot = createDeferred("egg_chocolate_spot", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> Egg_ChocolateDark_Spot = createDeferred("egg_chocolatedark_spot", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> Egg_GreenYellow_Spot = createDeferred("egg_greenyellow_spot", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> Egg_OliveLight_Spot = createDeferred("egg_olivelight_spot", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> Egg_Olive_Spot = createDeferred("egg_olive_spot", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> Egg_OliveDark_Spot = createDeferred("egg_olivedark_spot", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> Egg_Army_Spot = createDeferred("egg_army_spot", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> Egg_Grey_Spot = createDeferred("egg_grey_spot", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> Egg_GreyGreen_Spot = createDeferred("egg_greygreen_spot", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> Egg_Avocado_Spot = createDeferred("egg_avocado_spot", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> Egg_AvocadoDark_Spot = createDeferred("egg_avocadodark_spot", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> Egg_Feldgrau_Spot = createDeferred("egg_feldgrau_spot", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> Egg_Mint_Spot = createDeferred("egg_mint_spot", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> Egg_Green_Spot = createDeferred("egg_green_spot", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> Egg_GreenDark_Spot = createDeferred("egg_greendark_spot", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> Egg_Pine_Spot = createDeferred("egg_pine_spot", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> Egg_PineDark_Spot = createDeferred("egg_pinedark_spot", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> Egg_PineBlack_Spot = createDeferred("egg_pineblack_spot", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));

    public static final RegistryObject<Item> Egg_Cream_Spatter = createDeferred("egg_cream_spatter", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> Egg_CreamDark_Spatter = createDeferred("egg_creamdark_spatter", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> Egg_Carmel_Spatter = createDeferred("egg_carmel_spatter", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));  //carmel
    public static final RegistryObject<Item> Egg_CarmelDark_Spatter = createDeferred("egg_carmeldark_spatter", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> Egg_Garnet_Spatter = createDeferred("egg_garnet_spatter", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> Egg_Pink_Spatter = createDeferred("egg_pink_spatter", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> Egg_PinkDark_Spatter = createDeferred("egg_pinkdark_spatter", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> Egg_Cherry_Spatter = createDeferred("egg_cherry_spatter", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));  //cherry
    public static final RegistryObject<Item> Egg_CherryDark_Spatter = createDeferred("egg_cherrydark_spatter", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> Egg_Plum_Spatter = createDeferred("egg_plum_spatter", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> Egg_BrownLight_Spatter = createDeferred("egg_brownlight_spatter", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> Egg_Brown_Spatter = createDeferred("egg_brown_spatter", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> Egg_BrownDark_Spatter = createDeferred("egg_browndark_spatter", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> Egg_Chocolate_Spatter = createDeferred("egg_chocolate_spatter", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> Egg_ChocolateDark_Spatter = createDeferred("egg_chocolatedark_spatter", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> Egg_GreenYellow_Spatter = createDeferred("egg_greenyellow_spatter", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> Egg_OliveLight_Spatter = createDeferred("egg_olivelight_spatter", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> Egg_Olive_Spatter = createDeferred("egg_olive_spatter", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> Egg_OliveDark_Spatter = createDeferred("egg_olivedark_spatter", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> Egg_Army_Spatter = createDeferred("egg_army_spatter", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> Egg_Grey_Spatter = createDeferred("egg_grey_spatter", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> Egg_GreyGreen_Spatter = createDeferred("egg_greygreen_spatter", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> Egg_Avocado_Spatter = createDeferred("egg_avocado_spatter", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> Egg_AvocadoDark_Spatter = createDeferred("egg_avocadodark_spatter", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> Egg_Feldgrau_Spatter = createDeferred("egg_feldgrau_spatter", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> Egg_Mint_Spatter = createDeferred("egg_mint_spatter", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> Egg_Green_Spatter = createDeferred("egg_green_spatter", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> Egg_GreenDark_Spatter = createDeferred("egg_greendark_spatter", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> Egg_Pine_Spatter = createDeferred("egg_pine_spatter", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> Egg_PineDark_Spatter = createDeferred("egg_pinedark_spatter", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> Egg_PineBlack_Spatter = createDeferred("egg_pineblack_spatter", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));

    public static final RegistryObject<Item> Egg_Cream_Speckle = createDeferred("egg_cream_speckle", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> Egg_CreamDark_Speckle = createDeferred("egg_creamdark_speckle", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> Egg_Carmel_Speckle = createDeferred("egg_carmel_speckle", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));  //carmel
    public static final RegistryObject<Item> Egg_CarmelDark_Speckle = createDeferred("egg_carmeldark_speckle", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> Egg_Garnet_Speckle = createDeferred("egg_garnet_speckle", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> Egg_Pink_Speckle = createDeferred("egg_pink_speckle", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> Egg_PinkDark_Speckle = createDeferred("egg_pinkdark_speckle", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> Egg_Cherry_Speckle = createDeferred("egg_cherry_speckle", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));  //cherry
    public static final RegistryObject<Item> Egg_CherryDark_Speckle = createDeferred("egg_cherrydark_speckle", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> Egg_Plum_Speckle = createDeferred("egg_plum_speckle", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> Egg_BrownLight_Speckle = createDeferred("egg_brownlight_speckle", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> Egg_Brown_Speckle = createDeferred("egg_brown_speckle", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> Egg_BrownDark_Speckle = createDeferred("egg_browndark_speckle", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> Egg_Chocolate_Speckle = createDeferred("egg_chocolate_speckle", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> Egg_ChocolateDark_Speckle = createDeferred("egg_chocolatedark_speckle", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> Egg_GreenYellow_Speckle = createDeferred("egg_greenyellow_speckle", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> Egg_OliveLight_Speckle = createDeferred("egg_olivelight_speckle", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> Egg_Olive_Speckle = createDeferred("egg_olive_speckle", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> Egg_OliveDark_Speckle = createDeferred("egg_olivedark_speckle", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> Egg_Army_Speckle = createDeferred("egg_army_speckle", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> Egg_Grey_Speckle = createDeferred("egg_grey_speckle", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> Egg_GreyGreen_Speckle = createDeferred("egg_greygreen_speckle", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> Egg_Avocado_Speckle = createDeferred("egg_avocado_speckle", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> Egg_AvocadoDark_Speckle = createDeferred("egg_avocadodark_speckle", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> Egg_Feldgrau_Speckle = createDeferred("egg_feldgrau_speckle", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> Egg_Mint_Speckle = createDeferred("egg_mint_speckle", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> Egg_Green_Speckle = createDeferred("egg_green_speckle", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> Egg_GreenDark_Speckle = createDeferred("egg_greendark_speckle", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> Egg_Pine_Speckle = createDeferred("egg_pine_speckle", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> Egg_PineDark_Speckle = createDeferred("egg_pinedark_speckle", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> Egg_PineBlack_Speckle = createDeferred("egg_pineblack_speckle", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));

    public static final RegistryObject<Item> EGG_EARTH_SPOT = createDeferred("egg_earth_spot", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_KHAKI_SPOT = createDeferred("egg_khaki_spot", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_GRULLO_SPOT = createDeferred("egg_grullo_spot", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_KHAKIDARK_SPOT = createDeferred("egg_khakidark_spot", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_CAROB_SPOT = createDeferred("egg_carob_spot", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_PINKGREY_SPOT = createDeferred("egg_pinkgrey_spot", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_WARMGREY_SPOT = createDeferred("egg_warmgrey_spot", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_ARTICHOKE_SPOT = createDeferred("egg_artichoke_spot", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_MYRTLEGREY_SPOT = createDeferred("egg_myrtlegrey_spot", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_RIFLE_SPOT = createDeferred("egg_rifle_spot", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_JADE_SPOT = createDeferred("egg_jade_spot", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_PISTACHIO_SPOT = createDeferred("egg_pistachio_spot", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_SAGE_SPOT = createDeferred("egg_sage_spot", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_ROSEMARY_SPOT = createDeferred("egg_rosemary_spot", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_GREENBROWN_SPOT = createDeferred("egg_greenbrown_spot", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_UMBER_SPOT = createDeferred("egg_umber_spot", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> Egg_Matcha_Spot = createDeferred("egg_matcha_spot", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> Egg_MatchaDark_Spot = createDeferred("egg_matchadark_spot", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_MOSS_SPOT = createDeferred("egg_moss_spot", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_MOSSDARK_SPOT = createDeferred("egg_mossdark_spot", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_GREENUMBER_SPOT = createDeferred("egg_greenumber_spot", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_GREYNEUTRAL_SPOT = createDeferred("egg_greyneutral_spot", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_LAUREL_SPOT = createDeferred("egg_laurel_spot", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_RESEDA_SPOT = createDeferred("egg_reseda_spot", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_GREENPEWTER_SPOT = createDeferred("egg_greenpewter_spot", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_GREYDARK_SPOT = createDeferred("egg_greydark_spot", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_CELADON_SPOT = createDeferred("egg_celadon_spot", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_FERN_SPOT = createDeferred("egg_fern_spot", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_ASPARAGUS_SPOT = createDeferred("egg_asparagus_spot", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_HUNTER_SPOT = createDeferred("egg_hunter_spot", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_HUNTERDARK_SPOT = createDeferred("egg_hunterdark_spot", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_TREEDARK_SPOT = createDeferred("egg_treedark_spot", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));

    public static final RegistryObject<Item> EGG_EARTH_SPATTER = createDeferred("egg_earth_spatter", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_KHAKI_SPATTER = createDeferred("egg_khaki_spatter", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_GRULLO_SPATTER = createDeferred("egg_grullo_spatter", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_KHAKIDARK_SPATTER = createDeferred("egg_khakidark_spatter", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_CAROB_SPATTER = createDeferred("egg_carob_spatter", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_PINKGREY_SPATTER = createDeferred("egg_pinkgrey_spatter", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_WARMGREY_SPATTER = createDeferred("egg_warmgrey_spatter", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_ARTICHOKE_SPATTER = createDeferred("egg_artichoke_spatter", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_MYRTLEGREY_SPATTER = createDeferred("egg_myrtlegrey_spatter", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_RIFLE_SPATTER = createDeferred("egg_rifle_spatter", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_JADE_SPATTER = createDeferred("egg_jade_spatter", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_PISTACHIO_SPATTER = createDeferred("egg_pistachio_spatter", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_SAGE_SPATTER = createDeferred("egg_sage_spatter", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_ROSEMARY_SPATTER = createDeferred("egg_rosemary_spatter", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_GREENBROWN_SPATTER = createDeferred("egg_greenbrown_spatter", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_UMBER_SPATTER = createDeferred("egg_umber_spatter", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_MATCHA_SPATTER = createDeferred("egg_matcha_spatter", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_MATCHADARK_SPATTER = createDeferred("egg_matchadark_spatter", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_MOSS_SPATTER = createDeferred("egg_moss_spatter", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_MOSSDARK_SPATTER = createDeferred("egg_mossdark_spatter", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_GREENUMBER_SPATTER = createDeferred("egg_greenumber_spatter", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_GREYNEUTRAL_SPATTER = createDeferred("egg_greyneutral_spatter", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_LAUREL_SPATTER = createDeferred("egg_laurel_spatter", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_RESEDA_SPATTER = createDeferred("egg_reseda_spatter", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_GREENPEWTER_SPATTER = createDeferred("egg_greenpewter_spatter", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_GREYDARK_SPATTER = createDeferred("egg_greydark_spatter", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_CELADON_SPATTER = createDeferred("egg_celadon_spatter", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_FERN_SPATTER = createDeferred("egg_fern_spatter", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_ASPARAGUS_SPATTER = createDeferred("egg_asparagus_spatter", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_HUNTER_SPATTER = createDeferred("egg_hunter_spatter", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_HUNTERDARK_SPATTER = createDeferred("egg_hunterdark_spatter", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_TREEDARK_SPATTER = createDeferred("egg_treedark_spatter", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));

    public static final RegistryObject<Item> EGG_EARTH_SPECKLE = createDeferred("egg_earth_speckle", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_KHAKI_SPECKLE = createDeferred("egg_khaki_speckle", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_GRULLO_SPECKLE = createDeferred("egg_grullo_speckle", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_KHAKIDARK_SPECKLE = createDeferred("egg_khakidark_speckle", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_CAROB_SPECKLE = createDeferred("egg_carob_speckle", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_PINKGREY_SPECKLE = createDeferred("egg_pinkgrey_speckle", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_WARMGREY_SPECKLE = createDeferred("egg_warmgrey_speckle", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_ARTICHOKE_SPECKLE = createDeferred("egg_artichoke_speckle", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_MYRTLEGREY_SPECKLE = createDeferred("egg_myrtlegrey_speckle", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_RIFLE_SPECKLE = createDeferred("egg_rifle_speckle", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_JADE_SPECKLE = createDeferred("egg_jade_speckle", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_PISTACHIO_SPECKLE = createDeferred("egg_pistachio_speckle", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_SAGE_SPECKLE = createDeferred("egg_sage_speckle", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_ROSEMARY_SPECKLE = createDeferred("egg_rosemary_speckle", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_GREENBROWN_SPECKLE = createDeferred("egg_greenbrown_speckle", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_UMBER_SPECKLE = createDeferred("egg_umber_speckle", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_MATCHA_SPECKLE = createDeferred("egg_matcha_speckle", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_MATCHADARK_SPECKLE = createDeferred("egg_matchadark_speckle", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_MOSS_SPECKLE = createDeferred("egg_moss_speckle", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_MOSSDARK_SPECKLE = createDeferred("egg_mossdark_speckle", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_GREENUMBER_SPECKLE = createDeferred("egg_greenumber_speckle", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_GREYNEUTRAL_SPECKLE = createDeferred("egg_greyneutral_speckle", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_LAUREL_SPECKLE = createDeferred("egg_laurel_speckle", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_RESEDA_SPECKLE = createDeferred("egg_reseda_speckle", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_GREENPEWTER_SPECKLE = createDeferred("egg_greenpewter_speckle", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_GREYDARK_SPECKLE = createDeferred("egg_greydark_speckle", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_CELADON_SPECKLE = createDeferred("egg_celadon_speckle", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_FERN_SPECKLE = createDeferred("egg_fern_speckle", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_ASPARAGUS_SPECKLE = createDeferred("egg_asparagus_speckle", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_HUNTER_SPECKLE = createDeferred("egg_hunter_speckle", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_HUNTERDARK_SPECKLE = createDeferred("egg_hunterdark_speckle", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_TREEDARK_SPECKLE = createDeferred("egg_treedark_speckle", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));

    public static final RegistryObject<Item> RAWCHICKEN_DARKSMALL = createDeferred("rawchicken_darksmall", new RawChicken(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(64).food((new FoodProperties.Builder()).nutrition(1).saturationMod(0.2F).effect(new MobEffectInstance(MobEffects.HUNGER, 600, 0), 0.3F).meat().build())));
    public static final RegistryObject<Item> RAWCHICKEN_DARK = createDeferred("rawchicken_dark", new RawChicken(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(64).food((new FoodProperties.Builder()).nutrition(1).saturationMod(0.3F).effect(new MobEffectInstance(MobEffects.HUNGER, 600, 0), 0.3F).meat().build())));
    public static final RegistryObject<Item> RAWCHICKEN_DARKBIG = createDeferred("rawchicken_darkbig", new RawChicken(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(64).food((new FoodProperties.Builder()).nutrition(2).saturationMod(0.3F).effect(new MobEffectInstance(MobEffects.HUNGER, 600, 0), 0.3F).meat().build())));
    public static final RegistryObject<Item> RAWCHICKEN_PALESMALL = createDeferred("rawchicken_palesmall", new RawChicken(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(64).food((new FoodProperties.Builder()).nutrition(1).saturationMod(0.2F).effect(new MobEffectInstance(MobEffects.HUNGER, 600, 0), 0.3F).meat().build())));
    public static final RegistryObject<Item> RAWCHICKEN_PALE = createDeferred("rawchicken_pale", new RawChicken(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(64).food((new FoodProperties.Builder()).nutrition(1).saturationMod(0.3F).effect(new MobEffectInstance(MobEffects.HUNGER, 600, 0), 0.3F).meat().build())));
    public static final RegistryObject<Item> COOKEDCHICKEN_DARKSMALL = createDeferred("cookedchicken_darksmall", new CookedChicken(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(64).food((new FoodProperties.Builder()).nutrition(2).saturationMod(0.6F).meat().build())));
    public static final RegistryObject<Item> COOKEDCHICKEN_DARK = createDeferred("cookedchicken_dark", new CookedChicken(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(64).food((new FoodProperties.Builder()).nutrition(4).saturationMod(0.6F).meat().build())));
    public static final RegistryObject<Item> COOKEDCHICKEN_DARKBIG = createDeferred("cookedchicken_darkbig", new CookedChicken(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(64).food((new FoodProperties.Builder()).nutrition(6).saturationMod(0.6F).meat().build())));
    public static final RegistryObject<Item> COOKEDCHICKEN_PALESMALL = createDeferred("cookedchicken_palesmall", new CookedChicken(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(64).food((new FoodProperties.Builder()).nutrition(2).saturationMod(0.6F).meat().build())));
    public static final RegistryObject<Item> COOKEDCHICKEN_PALE = createDeferred("cookedchicken_pale", new CookedChicken(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(64).food((new FoodProperties.Builder()).nutrition(4).saturationMod(0.6F).meat().build())));
    public static final RegistryObject<Item> RAWRABBIT_SMALL = createDeferred("rawrabbit_small", new RawRabbit(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(64).food((new FoodProperties.Builder()).nutrition(2).saturationMod(0.3F).meat().build())));
    public static final RegistryObject<Item> COOKEDRABBIT_SMALL = createDeferred("cookedrabbit_small", new CookedRabbit(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(64).food((new FoodProperties.Builder()).nutrition(3).saturationMod(0.6F).meat().build())));
    public static final RegistryObject<Item> RABBITSTEW_WEAK = createDeferred("rabbitstew_weak", new RabbitStew(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(64).food((new FoodProperties.Builder()).nutrition(8).saturationMod(0.5F).meat().build())));

    public static final RegistryObject<Item> HALF_MILK_BOTTLE = createDeferred("half_milk_bottle", new MilkBottleHalf(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1).craftRemainder(Items.GLASS_BOTTLE).food((new FoodProperties.Builder()).alwaysEat().nutrition(0).saturationMod(0F).build())));
    public static final RegistryObject<Item> MILK_BOTTLE = createDeferred("milk_bottle", new MilkBottle(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1).craftRemainder(Items.GLASS_BOTTLE).food((new FoodProperties.Builder()).alwaysEat().nutrition(0).saturationMod(0F).build())));
    public static final RegistryObject<Item> ONESIXTH_MILK_BUCKET = createDeferred("onesixth_milk_bucket", new MilkBucketOneSixth(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1).craftRemainder(Items.BUCKET).food((new FoodProperties.Builder()).alwaysEat().nutrition(0).saturationMod(0F).build())));
    public static final RegistryObject<Item> ONETHIRD_MILK_BUCKET = createDeferred("onethird_milk_bucket", new MilkBucketOneThird(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1).craftRemainder(Items.BUCKET).food((new FoodProperties.Builder()).alwaysEat().nutrition(0).saturationMod(0F).build())));
    public static final RegistryObject<Item> HALF_MILK_BUCKET = createDeferred("half_milk_bucket", new MilkBucketHalf(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1).craftRemainder(Items.BUCKET).food((new FoodProperties.Builder()).alwaysEat().nutrition(0).saturationMod(0F).build())));
    public static final RegistryObject<Item> TWOTHIRDS_MILK_BUCKET = createDeferred("twothirds_milk_bucket", new MilkBucketTwoThirds(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1).craftRemainder(Items.BUCKET).food((new FoodProperties.Builder()).alwaysEat().nutrition(0).saturationMod(0F).build())));
    public static final RegistryObject<Item> FIVESIXTHS_MILK_BUCKET = createDeferred("fivesixths_milk_bucket", new MilkBucketFiveSixths(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1).craftRemainder(Items.BUCKET).food((new FoodProperties.Builder()).alwaysEat().nutrition(0).saturationMod(0F).build())));

                        //ITEMNAME_VARIENT_TEXTUREVARIENT
    public static final RegistryObject<Item> SADDLE_BASIC_LEATHER = createDeferred("saddle_basic_leather", new CustomizableSaddleVanilla(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1),10511680));
    public static final RegistryObject<Item> SADDLE_BASIC_LEATHER_GOLD = createDeferred("saddle_basic_leather_gold", new CustomizableSaddleVanilla(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1),10511680));
    public static final RegistryObject<Item> SADDLE_BASIC_LEATHER_DIAMOND = createDeferred("saddle_basic_leather_diamond", new CustomizableSaddleVanilla(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1),10511680));
    public static final RegistryObject<Item> SADDLE_BASIC_LEATHER_WOOD = createDeferred("saddle_basic_leather_wood", new CustomizableSaddleVanilla(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1),10511680));
    public static final RegistryObject<Item> SADDLE_BASIC_CLOTH = createDeferred("saddle_basic_cloth", new CustomizableSaddleVanilla(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1),16777215));
    public static final RegistryObject<Item> SADDLE_BASIC_CLOTH_GOLD = createDeferred("saddle_basic_cloth_gold", new CustomizableSaddleVanilla(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1),16777215));
    public static final RegistryObject<Item> SADDLE_BASIC_CLOTH_DIAMOND = createDeferred("saddle_basic_cloth_diamond", new CustomizableSaddleVanilla(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1),16777215));
    public static final RegistryObject<Item> SADDLE_BASIC_CLOTH_WOOD = createDeferred("saddle_basic_cloth_wood", new CustomizableSaddleVanilla(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1),16777215));
    public static final RegistryObject<Item> SADDLE_BASIC_LEATHERCLOTHSEAT = createDeferred("saddle_basic_leatherclothseat", new CustomizableSaddleVanilla(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1),16777215));
    public static final RegistryObject<Item> SADDLE_BASIC_LEATHERCLOTHSEAT_GOLD = createDeferred("saddle_basic_leatherclothseat_gold", new CustomizableSaddleVanilla(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1),16777215));
    public static final RegistryObject<Item> SADDLE_BASIC_LEATHERCLOTHSEAT_DIAMOND = createDeferred("saddle_basic_leatherclothseat_diamond", new CustomizableSaddleVanilla(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1),16777215));
    public static final RegistryObject<Item> SADDLE_BASIC_LEATHERCLOTHSEAT_WOOD = createDeferred("saddle_basic_leatherclothseat_wood", new CustomizableSaddleVanilla(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1),16777215));
    public static final RegistryObject<Item> SADDLE_BASICPOMEL_LEATHER = createDeferred("saddle_basicpomel_leather", new CustomizableSaddleWestern(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1),10511680));
    public static final RegistryObject<Item> SADDLE_BASICPOMEL_LEATHER_GOLD = createDeferred("saddle_basicpomel_leather_gold", new CustomizableSaddleWestern(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1),10511680));
    public static final RegistryObject<Item> SADDLE_BASICPOMEL_LEATHER_DIAMOND = createDeferred("saddle_basicpomel_leather_diamond", new CustomizableSaddleWestern(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1),10511680));
    public static final RegistryObject<Item> SADDLE_BASICPOMEL_LEATHER_WOOD = createDeferred("saddle_basicpomel_leather_wood", new CustomizableSaddleWestern(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1),10511680));
    public static final RegistryObject<Item> SADDLE_BASICPOMEL_CLOTH = createDeferred("saddle_basicpomel_cloth", new CustomizableSaddleWestern(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1),16777215));
    public static final RegistryObject<Item> SADDLE_BASICPOMEL_CLOTH_GOLD = createDeferred("saddle_basicpomel_cloth_gold", new CustomizableSaddleWestern(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1),16777215));
    public static final RegistryObject<Item> SADDLE_BASICPOMEL_CLOTH_DIAMOND = createDeferred("saddle_basicpomel_cloth_diamond", new CustomizableSaddleWestern(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1),16777215));
    public static final RegistryObject<Item> SADDLE_BASICPOMEL_CLOTH_WOOD = createDeferred("saddle_basicpomel_cloth_wood", new CustomizableSaddleWestern(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1),16777215));
    public static final RegistryObject<Item> SADDLE_BASICPOMEL_LEATHERCLOTHSEAT = createDeferred("saddle_basicpomel_leatherclothseat", new CustomizableSaddleWestern(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1),16777215));
    public static final RegistryObject<Item> SADDLE_BASICPOMEL_LEATHERCLOTHSEAT_GOLD = createDeferred("saddle_basicpomel_leatherclothseat_gold", new CustomizableSaddleWestern(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1),16777215));
    public static final RegistryObject<Item> SADDLE_BASICPOMEL_LEATHERCLOTHSEAT_DIAMOND = createDeferred("saddle_basicpomel_leatherclothseat_diamond", new CustomizableSaddleWestern(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1),16777215));
    public static final RegistryObject<Item> SADDLE_BASICPOMEL_LEATHERCLOTHSEAT_WOOD = createDeferred("saddle_basicpomel_leatherclothseat_wood", new CustomizableSaddleWestern(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1),16777215));
    public static final RegistryObject<Item> SADDLE_ENGLISH_LEATHER = createDeferred("saddle_english_leather", new CustomizableSaddleEnglish(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1),10511680));
    public static final RegistryObject<Item> SADDLE_ENGLISH_LEATHER_GOLD = createDeferred("saddle_english_leather_gold", new CustomizableSaddleEnglish(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1),10511680));
    public static final RegistryObject<Item> SADDLE_ENGLISH_LEATHER_DIAMOND = createDeferred("saddle_english_leather_diamond", new CustomizableSaddleEnglish(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1),10511680));
    public static final RegistryObject<Item> SADDLE_ENGLISH_LEATHER_WOOD = createDeferred("saddle_english_leather_wood", new CustomizableSaddleEnglish(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1),10511680));
    public static final RegistryObject<Item> SADDLE_ENGLISH_CLOTH = createDeferred("saddle_english_cloth", new CustomizableSaddleEnglish(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1),16777215));
    public static final RegistryObject<Item> SADDLE_ENGLISH_CLOTH_GOLD = createDeferred("saddle_english_cloth_gold", new CustomizableSaddleEnglish(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1),16777215));
    public static final RegistryObject<Item> SADDLE_ENGLISH_CLOTH_DIAMOND = createDeferred("saddle_english_cloth_diamond", new CustomizableSaddleEnglish(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1),16777215));
    public static final RegistryObject<Item> SADDLE_ENGLISH_CLOTH_WOOD = createDeferred("saddle_english_cloth_wood", new CustomizableSaddleEnglish(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1),16777215));
    public static final RegistryObject<Item> SADDLE_ENGLISH_LEATHERCLOTHSEAT = createDeferred("saddle_english_leatherclothseat", new CustomizableSaddleEnglish(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1),16777215));
    public static final RegistryObject<Item> SADDLE_ENGLISH_LEATHERCLOTHSEAT_GOLD = createDeferred("saddle_english_leatherclothseat_gold", new CustomizableSaddleEnglish(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1),16777215));
    public static final RegistryObject<Item> SADDLE_ENGLISH_LEATHERCLOTHSEAT_DIAMOND = createDeferred("saddle_english_leatherclothseat_diamond", new CustomizableSaddleEnglish(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1),16777215));
    public static final RegistryObject<Item> SADDLE_ENGLISH_LEATHERCLOTHSEAT_WOOD = createDeferred("saddle_english_leatherclothseat_wood", new CustomizableSaddleEnglish(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1),16777215));
    public static final RegistryObject<Item> BRIDLE_BASIC_LEATHER = createDeferred("bridle_basic_leather", new CustomizableBridle(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1),10511680));
    public static final RegistryObject<Item> BRIDLE_BASIC_LEATHER_GOLD = createDeferred("bridle_basic_leather_gold", new CustomizableBridle(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1),10511680));
    public static final RegistryObject<Item> BRIDLE_BASIC_LEATHER_DIAMOND = createDeferred("bridle_basic_leather_diamond", new CustomizableBridle(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1),10511680));
    public static final RegistryObject<Item> BRIDLE_BASIC_CLOTH = createDeferred("bridle_basic_cloth", new CustomizableBridle(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1),16777215));
    public static final RegistryObject<Item> BRIDLE_BASIC_CLOTH_GOLD = createDeferred("bridle_basic_cloth_gold", new CustomizableBridle(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1),16777215));
    public static final RegistryObject<Item> BRIDLE_BASIC_CLOTH_DIAMOND = createDeferred("bridle_basic_cloth_diamond", new CustomizableBridle(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1),16777215));
    public static final RegistryObject<Item> COLLAR_BASIC_CLOTH = createDeferred("collar_basic_cloth", new CustomizableCollar(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1),16777215, false));
    public static final RegistryObject<Item> COLLAR_BASIC_CLOTH_IRONRING = createDeferred("collar_basic_cloth_ironring", new CustomizableCollar(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1),16777215, false));
    public static final RegistryObject<Item> COLLAR_BASIC_CLOTH_IRONBELL = createDeferred("collar_basic_cloth_ironbell", new CustomizableCollar(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1),16777215, true));
    public static final RegistryObject<Item> COLLAR_BASIC_CLOTH_GOLDRING = createDeferred("collar_basic_cloth_goldring", new CustomizableCollar(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1),16777215, false));
    public static final RegistryObject<Item> COLLAR_BASIC_CLOTH_GOLDBELL = createDeferred("collar_basic_cloth_goldbell", new CustomizableCollar(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1),16777215, true));
    public static final RegistryObject<Item> COLLAR_BASIC_CLOTH_DIAMONDRING = createDeferred("collar_basic_cloth_diamondring", new CustomizableCollar(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1),16777215, false));
    public static final RegistryObject<Item> COLLAR_BASIC_CLOTH_DIAMONDBELL = createDeferred("collar_basic_cloth_diamondbell", new CustomizableCollar(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1),16777215, true));
    public static final RegistryObject<Item> COLLAR_BASIC_LEATHER = createDeferred("collar_basic_leather", new CustomizableCollar(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1),10511680, false));
    public static final RegistryObject<Item> COLLAR_BASIC_LEATHER_IRONRING = createDeferred("collar_basic_leather_ironring", new CustomizableCollar(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1),10511680, false));
    public static final RegistryObject<Item> COLLAR_BASIC_LEATHER_IRONBELL = createDeferred("collar_basic_leather_ironbell", new CustomizableCollar(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1),10511680, true));
    public static final RegistryObject<Item> COLLAR_BASIC_LEATHER_GOLDRING = createDeferred("collar_basic_leather_goldring", new CustomizableCollar(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1),10511680, false));
    public static final RegistryObject<Item> COLLAR_BASIC_LEATHER_GOLDBELL = createDeferred("collar_basic_leather_goldbell", new CustomizableCollar(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1),10511680, true));
    public static final RegistryObject<Item> COLLAR_BASIC_LEATHER_DIAMONDRING = createDeferred("collar_basic_leather_diamondring", new CustomizableCollar(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1),10511680, false));
    public static final RegistryObject<Item> COLLAR_BASIC_LEATHER_DIAMONDBELL = createDeferred("collar_basic_leather_diamondbell", new CustomizableCollar(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1),10511680, true));

    public static final RegistryObject<Item> GENETICS_ENCYCLOPEDIA = createDeferred("genetics_encyclopedia", new GeneticsEncyclopedia(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> DEBUG_GENE_BOOK = createDeferred("debug_gene_book", new DebugGenesBook(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
//    public static final RegistryObject<Item> MIXABLE_MILK = createDeferred("rawchicken_darksmall", new MixableMilkBucket(new Item.Properties().group(EnhancedAnimals.GENETICS_ANIMALS_GROUP).maxStackSize(1)));

//    public static final RegistryObject<Item> ENHANCED_AXOLOTL_EGG = createDeferred("enhanced_axolotl_spawn_egg", new ForgeSpawnEggItem(ENHANCED_AXOLOTL, 0xFFFFDD,0x00DDCC, new Item.Properties()));

    private static RegistryObject<Item> createDeferred(String registryName, Item item) {
        return ITEMS_DEFERRED_REGISTRY.register(registryName, () -> item);
    }

//    private static RegistryObject<ForgeSpawnEggItem> createDeferred(String registryName, Item item) {
//        return ITEMS_DEFERRED_REGISTRY.register(registryName, () -> item);
//    }

    public static void register(IEventBus modEventBus) {
        ITEMS_DEFERRED_REGISTRY.register(modEventBus);
    }


}
