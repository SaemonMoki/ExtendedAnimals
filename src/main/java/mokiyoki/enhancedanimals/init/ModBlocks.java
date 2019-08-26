package mokiyoki.enhancedanimals.init;

import mokiyoki.enhancedanimals.blocks.EggCartonBlock;
import mokiyoki.enhancedanimals.blocks.Post;
import mokiyoki.enhancedanimals.util.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraftforge.registries.ObjectHolder;

/**
 * Created by moki on 25/08/2018.
 */
@ObjectHolder(Reference.MODID)
public class ModBlocks {

    public static final Block Post_Acacia = new Post(Block.Properties.create(Material.WOOD, MaterialColor.ADOBE).hardnessAndResistance(2.0F,15.0F).sound(SoundType.WOOD)).setRegistryName("post_acacia");
    public static final Block Post_Birch = new Post(Block.Properties.create(Material.WOOD, MaterialColor.SAND).hardnessAndResistance(2.0F,15.0F).sound(SoundType.WOOD)).setRegistryName("post_birch");
    public static final Block Post_Dark_Oak = new Post(Block.Properties.create(Material.WOOD, MaterialColor.BROWN).hardnessAndResistance(2.0F,15.0F).sound(SoundType.WOOD)).setRegistryName("post_dark_oak");
    public static final Block Post_Jungle = new Post(Block.Properties.create(Material.WOOD, MaterialColor.DIRT).hardnessAndResistance(2.0F,15.0F).sound(SoundType.WOOD)).setRegistryName("post_jungle");
    public static final Block Post_Oak = new Post(Block.Properties.create(Material.WOOD, MaterialColor.WOOD).hardnessAndResistance(2.0F,15.0F).sound(SoundType.WOOD)).setRegistryName("post_oak");
    public static final Block Post_Spruce = new Post(Block.Properties.create(Material.WOOD, MaterialColor.OBSIDIAN).hardnessAndResistance(2.0F,15.0F).sound(SoundType.WOOD)).setRegistryName("post_spruce");

    public static final Block Egg_Carton = new EggCartonBlock(Block.Properties.create(Material.WOOL, MaterialColor.SAND).hardnessAndResistance(0.0F).sound(SoundType.CLOTH)).setRegistryName("eanimod:egg_carton");


}