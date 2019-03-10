package mokiyoki.enhancedanimals.init;

import mokiyoki.enhancedanimals.items.CookedChicken;
import mokiyoki.enhancedanimals.items.EnhancedEgg;
import mokiyoki.enhancedanimals.items.RawChicken;
import mokiyoki.enhancedanimals.util.Reference;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
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

    public static final Item RawChicken_DarkSmall = new RawChicken(new Item.Properties().group(ItemGroup.FOOD).maxStackSize(64),1, 0.5F, true).setRegistryName(Reference.MODID, "rawchicken_darksmall");
    public static final Item RawChicken_Dark = new RawChicken(new Item.Properties().group(ItemGroup.FOOD).maxStackSize(64),2, 1.2F, true).setRegistryName(Reference.MODID, "rawchicken_dark");
    public static final Item RawChicken_DarkBig = new RawChicken(new Item.Properties().group(ItemGroup.FOOD).maxStackSize(64),2, 2.2F, true).setRegistryName(Reference.MODID, "rawchicken_darkbig");
    public static final Item RawChicken_PaleSmall = new RawChicken(new Item.Properties().group(ItemGroup.FOOD).maxStackSize(64),1, 0.5F, true).setRegistryName(Reference.MODID, "rawchicken_palesmall");
    public static final Item RawChicken_Pale = new RawChicken(new Item.Properties().group(ItemGroup.FOOD).maxStackSize(64),2, 2.2F, true).setRegistryName(Reference.MODID, "rawchicken_pale");
    public static final Item CookedChicken_DarkSmall = new CookedChicken(new Item.Properties().group(ItemGroup.FOOD).maxStackSize(64),3, 3.6F, true).setRegistryName(Reference.MODID, "cookedchicken_darksmall");
    public static final Item CookedChicken_Dark = new CookedChicken(new Item.Properties().group(ItemGroup.FOOD).maxStackSize(64),6, 7.2F, true).setRegistryName(Reference.MODID, "cookedchicken_dark");
    public static final Item CookedChicken_DarkBig = new CookedChicken(new Item.Properties().group(ItemGroup.FOOD).maxStackSize(64),6, 10/2F, true).setRegistryName(Reference.MODID, "cookedchicken_darkbig");
    public static final Item CookedChicken_PaleSmall = new CookedChicken(new Item.Properties().group(ItemGroup.FOOD).maxStackSize(64),3, 3.6F, true).setRegistryName(Reference.MODID, "cookedchicken_palesmall");
    public static final Item CookedChicken_Pale = new CookedChicken(new Item.Properties().group(ItemGroup.FOOD).maxStackSize(64),6, 10.2F, true).setRegistryName(Reference.MODID, "cookedchicken_pale");

}

