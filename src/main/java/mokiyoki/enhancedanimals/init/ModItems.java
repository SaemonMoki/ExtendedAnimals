package mokiyoki.enhancedanimals.init;

import mokiyoki.enhancedanimals.blocks.EggCartonBlock;
import mokiyoki.enhancedanimals.items.*;
import mokiyoki.enhancedanimals.util.Reference;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Items;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraftforge.registries.ObjectHolder;

/**
 * Created by moki on 24/08/2018.
 */
@ObjectHolder(Reference.MODID)
public class ModItems {

    public static final Item Egg_White = new EnhancedEgg(new Item.Properties().group(ItemGroup.FOOD).maxStackSize(1)).setRegistryName(Reference.MODID, "egg_white");
    public static final Item Egg_Cream = new EnhancedEgg(new Item.Properties().group(ItemGroup.FOOD).maxStackSize(1)).setRegistryName(Reference.MODID, "egg_cream");
    public static final Item Egg_CreamDark = new EnhancedEgg(new Item.Properties().group(ItemGroup.FOOD).maxStackSize(1)).setRegistryName(Reference.MODID, "egg_creamdark");
    public static final Item Egg_Pink = new EnhancedEgg(new Item.Properties().group(ItemGroup.FOOD).maxStackSize(1)).setRegistryName(Reference.MODID, "egg_pink");
    public static final Item Egg_PinkDark = new EnhancedEgg(new Item.Properties().group(ItemGroup.FOOD).maxStackSize(1)).setRegistryName(Reference.MODID, "egg_pinkdark");
    public static final Item Egg_Brown = new EnhancedEgg(new Item.Properties().group(ItemGroup.FOOD).maxStackSize(1)).setRegistryName(Reference.MODID, "egg_brown");
    public static final Item Egg_BrownDark = new EnhancedEgg(new Item.Properties().group(ItemGroup.FOOD).maxStackSize(1)).setRegistryName(Reference.MODID, "egg_browndark");
    public static final Item Egg_Blue = new EnhancedEgg(new Item.Properties().group(ItemGroup.FOOD).maxStackSize(1)).setRegistryName(Reference.MODID, "egg_blue");
    public static final Item Egg_GreenLight = new EnhancedEgg(new Item.Properties().group(ItemGroup.FOOD).maxStackSize(1)).setRegistryName(Reference.MODID, "egg_greenlight");
    public static final Item Egg_Green = new EnhancedEgg(new Item.Properties().group(ItemGroup.FOOD).maxStackSize(1)).setRegistryName(Reference.MODID, "egg_green");
    public static final Item Egg_Grey = new EnhancedEgg(new Item.Properties().group(ItemGroup.FOOD).maxStackSize(1)).setRegistryName(Reference.MODID, "egg_grey");
    public static final Item Egg_GreyGreen = new EnhancedEgg(new Item.Properties().group(ItemGroup.FOOD).maxStackSize(1)).setRegistryName(Reference.MODID, "egg_greygreen");
    public static final Item Egg_Olive = new EnhancedEgg(new Item.Properties().group(ItemGroup.FOOD).maxStackSize(1)).setRegistryName(Reference.MODID, "egg_olive");
    public static final Item Egg_GreenDark = new EnhancedEgg(new Item.Properties().group(ItemGroup.FOOD).maxStackSize(1)).setRegistryName(Reference.MODID, "egg_greendark");

    public static final Item RawChicken_DarkSmall = new RawChicken(new Item.Properties().group(ItemGroup.FOOD).maxStackSize(64).food((new Food.Builder()).hunger(1).saturation(0.5F).effect(new EffectInstance(Effects.HUNGER, 600, 0), 0.3F).meat().build())).setRegistryName(Reference.MODID, "rawchicken_darksmall");
    public static final Item RawChicken_Dark = new RawChicken(new Item.Properties().group(ItemGroup.FOOD).maxStackSize(64).food((new Food.Builder()).hunger(2).saturation(1.2F).effect(new EffectInstance(Effects.HUNGER, 600, 0), 0.3F).meat().build())).setRegistryName(Reference.MODID, "rawchicken_dark");
    public static final Item RawChicken_DarkBig = new RawChicken(new Item.Properties().group(ItemGroup.FOOD).maxStackSize(64).food((new Food.Builder()).hunger(2).saturation(2.2F).effect(new EffectInstance(Effects.HUNGER, 600, 0), 0.3F).meat().build())).setRegistryName(Reference.MODID, "rawchicken_darkbig");
    public static final Item RawChicken_PaleSmall = new RawChicken(new Item.Properties().group(ItemGroup.FOOD).maxStackSize(64).food((new Food.Builder()).hunger(1).saturation(0.5F).effect(new EffectInstance(Effects.HUNGER, 600, 0), 0.3F).meat().build())).setRegistryName(Reference.MODID, "rawchicken_palesmall");
    public static final Item RawChicken_Pale = new RawChicken(new Item.Properties().group(ItemGroup.FOOD).maxStackSize(64).food((new Food.Builder()).hunger(2).saturation(2.2F).effect(new EffectInstance(Effects.HUNGER, 600, 0), 0.3F).meat().build())).setRegistryName(Reference.MODID, "rawchicken_pale");
    public static final Item CookedChicken_DarkSmall = new CookedChicken(new Item.Properties().group(ItemGroup.FOOD).maxStackSize(64).food((new Food.Builder()).hunger(3).saturation(3.6F).meat().build())).setRegistryName(Reference.MODID, "cookedchicken_darksmall");
    public static final Item CookedChicken_Dark = new CookedChicken(new Item.Properties().group(ItemGroup.FOOD).maxStackSize(64).food((new Food.Builder()).hunger(6).saturation(7.2F).meat().build())).setRegistryName(Reference.MODID, "cookedchicken_dark");
    public static final Item CookedChicken_DarkBig = new CookedChicken(new Item.Properties().group(ItemGroup.FOOD).maxStackSize(64).food((new Food.Builder()).hunger(6).saturation(10.2F).meat().build())).setRegistryName(Reference.MODID, "cookedchicken_darkbig");
    public static final Item CookedChicken_PaleSmall = new CookedChicken(new Item.Properties().group(ItemGroup.FOOD).maxStackSize(64).food((new Food.Builder()).hunger(3).saturation(3.6F).meat().build())).setRegistryName(Reference.MODID, "cookedchicken_palesmall");
    public static final Item CookedChicken_Pale = new CookedChicken(new Item.Properties().group(ItemGroup.FOOD).maxStackSize(64).food((new Food.Builder()).hunger(6).saturation(10.2F).meat().build())).setRegistryName(Reference.MODID, "cookedchicken_pale");
    public static final Item RawRabbit_Small = new RawRabbit(new Item.Properties().group(ItemGroup.FOOD).maxStackSize(64).food((new Food.Builder()).hunger(1).saturation(1.1F).meat().build())).setRegistryName(Reference.MODID, "rawrabbit_small");
    public static final Item CookedRabbit_Small = new CookedRabbit(new Item.Properties().group(ItemGroup.FOOD).maxStackSize(64).food((new Food.Builder()).hunger(4).saturation(5F).meat().build())).setRegistryName(Reference.MODID, "cookedrabbit_small");
    public static final Item RabbitStew_Weak = new RabbitStew(new Item.Properties().group(ItemGroup.FOOD).maxStackSize(64).food((new Food.Builder()).hunger(8).saturation(10F).meat().build())).setRegistryName(Reference.MODID, "rabbitstew_weak");

    public static final Item Milk_Bottle = new MilkBottle(new Item.Properties().group(ItemGroup.FOOD).maxStackSize(1).containerItem(Items.GLASS_BOTTLE).food((new Food.Builder()).setAlwaysEdible().hunger(0).saturation(0F).build())).setRegistryName(Reference.MODID, "milkbottle");

    public static final Item Debug_Gene_Book = new DebugGenesBook(new Item.Properties().group(ItemGroup.MISC).maxStackSize(1)).setRegistryName(Reference.MODID, "debug_gene_book");
}

