package mokiyoki.enhancedanimals.items;

import mokiyoki.enhancedanimals.util.Reference;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemFood;

public class CookedChicken extends ItemFood {

    public CookedChicken(String unlocalizedName, String registryName, int amount, float saturation, boolean isWolfFood)
    {
        super(amount, saturation, isWolfFood);
        setUnlocalizedName(Reference.MODID + "." + unlocalizedName);
        setRegistryName(registryName);
        setCreativeTab(CreativeTabs.FOOD);
        setMaxStackSize(64);
    }
}
