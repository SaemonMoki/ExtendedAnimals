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

    public static final RegistryObject<Item> EGG_WHITE = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_CREAMLIGHT = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_CREAM = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_CREAMDARK = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_CREAMDARKEST = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));  //carmel
    public static final RegistryObject<Item> EGG_CARMELDARK = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_GARNET = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_PINKLIGHT = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_PINK = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_PINKDARK = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_PINKDARKEST = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));  //cherry
    public static final RegistryObject<Item> EGG_CHERRYDARK = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_PLUM = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_BROWNLIGHT = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_BROWN = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_BROWNDARK = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_CHOCOLATE = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_CHOCOLATEDARK = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_CHOCOLATECOSMOS = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_BLUE = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_GREENLIGHT = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_GREENYELLOW = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_OLIVELIGHT = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_OLIVE = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_OLIVEDARK = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_ARMY = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_BLUEGREY = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_GREY = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_GREYGREEN = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_AVOCADO = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_AVOCADODARK = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_FELDGRAU = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_MINT = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_GREEN = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_GREENDARK = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_PINE = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_PINEDARK = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_PINEBLACK = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_POWDERBLUE = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_TEA = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_MATCHA = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_MATCHADARK = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_MOSS = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_MOSSDARK = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_GREENUMBER = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_GREYBLUE = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_GREYNEUTRAL = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_LAUREL = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_RESEDA = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_GREENPEWTER = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_GREYDARK = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_CELADON = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_FERN = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_ASPARAGUS = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_HUNTER = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_HUNTERDARK = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_TREEDARK = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_PALEBLUE = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_HONEYDEW = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_EARTH = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_KHAKI = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_GRULLO = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_KHAKIDARK = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_CAROB = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_COOLGREY = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_PINKGREY = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_WARMGREY = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_ARTICHOKE = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_MYRTLEGREY = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_RIFLE = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_JADE = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_PISTACHIO = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_SAGE = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_ROSEMARY = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_GREENBROWN = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_UMBER = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));

    public static final RegistryObject<Item> Egg_Cream_Spot = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> Egg_CreamDark_Spot = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> Egg_Carmel_Spot = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));  //carmel
    public static final RegistryObject<Item> Egg_CarmelDark_Spot = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> Egg_Garnet_Spot = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> Egg_Pink_Spot = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> Egg_PinkDark_Spot = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> Egg_Cherry_Spot = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));  //cherry
    public static final RegistryObject<Item> Egg_CherryDark_Spot = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> Egg_Plum_Spot = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> Egg_BrownLight_Spot = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> Egg_Brown_Spot = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> Egg_BrownDark_Spot = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> Egg_Chocolate_Spot = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> Egg_ChocolateDark_Spot = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> Egg_GreenYellow_Spot = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> Egg_OliveLight_Spot = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> Egg_Olive_Spot = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> Egg_OliveDark_Spot = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> Egg_Army_Spot = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> Egg_Grey_Spot = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> Egg_GreyGreen_Spot = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> Egg_Avocado_Spot = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> Egg_AvocadoDark_Spot = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> Egg_Feldgrau_Spot = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> Egg_Mint_Spot = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> Egg_Green_Spot = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> Egg_GreenDark_Spot = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> Egg_Pine_Spot = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> Egg_PineDark_Spot = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> Egg_PineBlack_Spot = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));

    public static final RegistryObject<Item> Egg_Cream_Spatter = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> Egg_CreamDark_Spatter = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> Egg_Carmel_Spatter = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));  //carmel
    public static final RegistryObject<Item> Egg_CarmelDark_Spatter = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> Egg_Garnet_Spatter = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> Egg_Pink_Spatter = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> Egg_PinkDark_Spatter = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> Egg_Cherry_Spatter = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));  //cherry
    public static final RegistryObject<Item> Egg_CherryDark_Spatter = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> Egg_Plum_Spatter = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> Egg_BrownLight_Spatter = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> Egg_Brown_Spatter = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> Egg_BrownDark_Spatter = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> Egg_Chocolate_Spatter = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> Egg_ChocolateDark_Spatter = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> Egg_GreenYellow_Spatter = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> Egg_OliveLight_Spatter = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> Egg_Olive_Spatter = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> Egg_OliveDark_Spatter = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> Egg_Army_Spatter = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> Egg_Grey_Spatter = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> Egg_GreyGreen_Spatter = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> Egg_Avocado_Spatter = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> Egg_AvocadoDark_Spatter = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> Egg_Feldgrau_Spatter = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> Egg_Mint_Spatter = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> Egg_Green_Spatter = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> Egg_GreenDark_Spatter = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> Egg_Pine_Spatter = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> Egg_PineDark_Spatter = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> Egg_PineBlack_Spatter = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));

    public static final RegistryObject<Item> Egg_Cream_Speckle = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> Egg_CreamDark_Speckle = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> Egg_Carmel_Speckle = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));  //carmel
    public static final RegistryObject<Item> Egg_CarmelDark_Speckle = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> Egg_Garnet_Speckle = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> Egg_Pink_Speckle = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> Egg_PinkDark_Speckle = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> Egg_Cherry_Speckle = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));  //cherry
    public static final RegistryObject<Item> Egg_CherryDark_Speckle = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> Egg_Plum_Speckle = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> Egg_BrownLight_Speckle = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> Egg_Brown_Speckle = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> Egg_BrownDark_Speckle = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> Egg_Chocolate_Speckle = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> Egg_ChocolateDark_Speckle = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> Egg_GreenYellow_Speckle = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> Egg_OliveLight_Speckle = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> Egg_Olive_Speckle = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> Egg_OliveDark_Speckle = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> Egg_Army_Speckle = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> Egg_Grey_Speckle = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> Egg_GreyGreen_Speckle = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> Egg_Avocado_Speckle = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> Egg_AvocadoDark_Speckle = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> Egg_Feldgrau_Speckle = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> Egg_Mint_Speckle = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> Egg_Green_Speckle = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> Egg_GreenDark_Speckle = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> Egg_Pine_Speckle = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> Egg_PineDark_Speckle = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> Egg_PineBlack_Speckle = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));

    public static final RegistryObject<Item> EGG_EARTH_SPOT = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_KHAKI_SPOT = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_GRULLO_SPOT = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_KHAKIDARK_SPOT = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_CAROB_SPOT = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_PINKGREY_SPOT = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_WARMGREY_SPOT = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_ARTICHOKE_SPOT = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_MYRTLEGREY_SPOT = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_RIFLE_SPOT = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_JADE_SPOT = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_PISTACHIO_SPOT = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_SAGE_SPOT = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_ROSEMARY_SPOT = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_GREENBROWN_SPOT = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_UMBER_SPOT = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> Egg_Matcha_Spot = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> Egg_MatchaDark_Spot = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_MOSS_SPOT = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_MOSSDARK_SPOT = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_GREENUMBER_SPOT = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_GREYNEUTRAL_SPOT = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_LAUREL_SPOT = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_RESEDA_SPOT = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_GREENPEWTER_SPOT = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_GREYDARK_SPOT = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_CELADON_SPOT = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_FERN_SPOT = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_ASPARAGUS_SPOT = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_HUNTER_SPOT = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_HUNTERDARK_SPOT = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_TREEDARK_SPOT = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));

    public static final RegistryObject<Item> EGG_EARTH_SPATTER = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_KHAKI_SPATTER = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_GRULLO_SPATTER = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_KHAKIDARK_SPATTER = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_CAROB_SPATTER = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_PINKGREY_SPATTER = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_WARMGREY_SPATTER = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_ARTICHOKE_SPATTER = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_MYRTLEGREY_SPATTER = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_RIFLE_SPATTER = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_JADE_SPATTER = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_PISTACHIO_SPATTER = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_SAGE_SPATTER = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_ROSEMARY_SPATTER = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_GREENBROWN_SPATTER = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_UMBER_SPATTER = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_MATCHA_SPATTER = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_MATCHADARK_SPATTER = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_MOSS_SPATTER = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_MOSSDARK_SPATTER = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_GREENUMBER_SPATTER = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_GREYNEUTRAL_SPATTER = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_LAUREL_SPATTER = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_RESEDA_SPATTER = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_GREENPEWTER_SPATTER = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_GREYDARK_SPATTER = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_CELADON_SPATTER = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_FERN_SPATTER = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_ASPARAGUS_SPATTER = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_HUNTER_SPATTER = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_HUNTERDARK_SPATTER = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_TREEDARK_SPATTER = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));

    public static final RegistryObject<Item> EGG_EARTH_SPECKLE = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_KHAKI_SPECKLE = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_GRULLO_SPECKLE = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_KHAKIDARK_SPECKLE = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_CAROB_SPECKLE = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_PINKGREY_SPECKLE = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_WARMGREY_SPECKLE = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_ARTICHOKE_SPECKLE = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_MYRTLEGREY_SPECKLE = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_RIFLE_SPECKLE = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_JADE_SPECKLE = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_PISTACHIO_SPECKLE = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_SAGE_SPECKLE = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_ROSEMARY_SPECKLE = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_GREENBROWN_SPECKLE = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_UMBER_SPECKLE = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_MATCHA_SPECKLE = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_MATCHADARK_SPECKLE = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_MOSS_SPECKLE = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_MOSSDARK_SPECKLE = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_GREENUMBER_SPECKLE = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_GREYNEUTRAL_SPECKLE = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_LAUREL_SPECKLE = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_RESEDA_SPECKLE = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_GREENPEWTER_SPECKLE = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_GREYDARK_SPECKLE = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_CELADON_SPECKLE = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_FERN_SPECKLE = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_ASPARAGUS_SPECKLE = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_HUNTER_SPECKLE = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_HUNTERDARK_SPECKLE = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> EGG_TREEDARK_SPECKLE = createDeferred("rawchicken_darksmall", new EnhancedEgg(new Item.Properties().tab(EnhancedAnimals.GENETICS_ANIMALS_GROUP).stacksTo(1)));

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
