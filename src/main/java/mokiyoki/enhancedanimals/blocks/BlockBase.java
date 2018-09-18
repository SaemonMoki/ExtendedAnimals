package mokiyoki.enhancedanimals.blocks;

import mokiyoki.enhancedanimals.Main;

import mokiyoki.enhancedanimals.util.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

/**
 * Created by moki on 25/08/2018.
 */
public class BlockBase extends Block {

    public BlockBase(Material material, String unlocalizedName, String registryName) {
        this(material, SoundType.WOOD, unlocalizedName, registryName);
    }

    public BlockBase(Material material, SoundType sound, String unlocalizedName, String registryName) {
        super(material);
        setUnlocalizedName(Reference.MODID + "." + unlocalizedName);
        setRegistryName(registryName);
        setCreativeTab(CreativeTabs.MISC);
        setSoundType(sound);
    }

}

//public class BlockBase extends Block implements IHasModel {
//    public BlockBase(String name, Material material){
//
//        super(material);
//        setUnlocalizedName(name);
//        setRegistryName(name);
//        setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
//
//        ModBlocks.BLOCKS.add(this);
//        ModItems.I.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
//    }
//
//    @Override
//    public void registerModels(){
////        Main.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
//    }
//}
