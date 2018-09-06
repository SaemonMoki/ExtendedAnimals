package mokiyoki.enhancedanimals.items;

import mokiyoki.enhancedanimals.Main;
import mokiyoki.enhancedanimals.init.ModItems;
import mokiyoki.enhancedanimals.util.IHasModel;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

/**
 * Created by moki on 24/08/2018.
 */
public class ItemBase extends Item implements IHasModel {

    public ItemBase(String name)
    {
        setUnlocalizedName(name);
        setRegistryName(name);
        setCreativeTab(CreativeTabs.MATERIALS);

        ModItems.ITEMS.add(this);
    }

    @Override
    public void registerModels() {
        Main.proxy.registerItemRenderer(this, 0, "inventory");
    }
}
