package mokiyoki.enhancedanimals.items;

import mokiyoki.enhancedanimals.util.Reference;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class CookedChicken extends Item {

    public CookedChicken(String unlocalizedName, String registryName) {
        setUnlocalizedName(Reference.MODID + "." + unlocalizedName);
        setRegistryName(registryName);
        setCreativeTab(CreativeTabs.FOOD);
        setMaxStackSize(64);
    }

}
