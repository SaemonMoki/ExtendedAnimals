package mokiyoki.enhancedanimals.blocks;

import mokiyoki.enhancedanimals.util.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.EnumFacing;

/**
 * Created by moki on 29/08/2018.
 */
public class Post_Spruce extends Block {
    public Post_Spruce(Material material, String unlocalizedName, String registryName) {
        this(material, SoundType.WOOD, unlocalizedName, registryName);
    }

    public Post_Spruce(Material material, SoundType sound, String unlocalizedName, String registryName) {
        super(material);
        setUnlocalizedName(Reference.MODID + "." + unlocalizedName);
        setRegistryName(registryName);
        setCreativeTab(CreativeTabs.MISC);
        setSoundType(sound);
        setHardness(2.0F);
        setResistance(15.0F);
        setHarvestLevel("axe", 0);
        setLightOpacity(0);
    }
}