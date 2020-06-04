package mokiyoki.enhancedanimals.init;

import mokiyoki.enhancedanimals.blocks.EggCartonBlock;
import mokiyoki.enhancedanimals.blocks.PatchyMyceliumBlock;
import mokiyoki.enhancedanimals.blocks.Post;
import mokiyoki.enhancedanimals.blocks.SparseGrassBlock;
import mokiyoki.enhancedanimals.blocks.UnboundHayBlock;
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

    public static final Block POST_ACACIA = new Post(Block.Properties.create(Material.WOOD, MaterialColor.ADOBE).hardnessAndResistance(2.0F,15.0F).sound(SoundType.WOOD)).setRegistryName("post_acacia");
    public static final Block POST_BIRCH = new Post(Block.Properties.create(Material.WOOD, MaterialColor.SAND).hardnessAndResistance(2.0F,15.0F).sound(SoundType.WOOD)).setRegistryName("post_birch");
    public static final Block POST_DARK_OAK = new Post(Block.Properties.create(Material.WOOD, MaterialColor.BROWN).hardnessAndResistance(2.0F,15.0F).sound(SoundType.WOOD)).setRegistryName("post_dark_oak");
    public static final Block POST_JUNGLE = new Post(Block.Properties.create(Material.WOOD, MaterialColor.DIRT).hardnessAndResistance(2.0F,15.0F).sound(SoundType.WOOD)).setRegistryName("post_jungle");
    public static final Block POST_OAK = new Post(Block.Properties.create(Material.WOOD, MaterialColor.WOOD).hardnessAndResistance(2.0F,15.0F).sound(SoundType.WOOD)).setRegistryName("post_oak");
    public static final Block POST_SPRUCE = new Post(Block.Properties.create(Material.WOOD, MaterialColor.OBSIDIAN).hardnessAndResistance(2.0F,15.0F).sound(SoundType.WOOD)).setRegistryName("post_spruce");

    public static final Block EGG_CARTON = new EggCartonBlock(Block.Properties.create(Material.WOOL, MaterialColor.LIGHT_GRAY).hardnessAndResistance(0.0F).sound(SoundType.CLOTH).notSolid()).setRegistryName("eanimod:egg_carton");
    public static final Block UNBOUNDHAY_BLOCK = new UnboundHayBlock(Block.Properties.create(Material.ORGANIC, MaterialColor.YELLOW).hardnessAndResistance(0.5F).sound(SoundType.PLANT).notSolid()).setRegistryName("eanimod:unboundhay_block");
    public static final Block SPARSE_GRASS_BLOCK = new SparseGrassBlock(Block.Properties.create(Material.ORGANIC, MaterialColor.DIRT).tickRandomly().hardnessAndResistance(0.5F).sound(SoundType.PLANT)).setRegistryName("eanimod:sparsegrass_block");
    public static final Block PATCHY_MYCELIUM_BLOCK = new PatchyMyceliumBlock(Block.Properties.create(Material.ORGANIC, MaterialColor.PURPLE).tickRandomly().hardnessAndResistance(0.5F).sound(SoundType.PLANT)).setRegistryName("eanimod:patchymycelium_block");

}