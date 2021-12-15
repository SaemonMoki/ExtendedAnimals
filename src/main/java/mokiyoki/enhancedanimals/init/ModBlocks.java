package mokiyoki.enhancedanimals.init;

import mokiyoki.enhancedanimals.blocks.EggCartonBlock;
import mokiyoki.enhancedanimals.blocks.EnhancedTurtleEggBlock;
import mokiyoki.enhancedanimals.blocks.GrowableDoubleHigh;
import mokiyoki.enhancedanimals.blocks.GrowablePlant;
import mokiyoki.enhancedanimals.blocks.PatchyMyceliumBlock;
import mokiyoki.enhancedanimals.blocks.PostBlock;
import mokiyoki.enhancedanimals.blocks.SparseGrassBlock;
import mokiyoki.enhancedanimals.blocks.UnboundHayBlock;
import mokiyoki.enhancedanimals.util.Reference;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.item.Items;
import net.minecraftforge.registries.ObjectHolder;

/**
 * Created by moki on 25/08/2018.
 */
@ObjectHolder(Reference.MODID)
public class ModBlocks {

    public static final Block POST_ACACIA = new PostBlock(Block.Properties.of(Material.WOOD, MaterialColor.COLOR_ORANGE).strength(2.0F,15.0F).sound(SoundType.WOOD)).setRegistryName("post_acacia");
    public static final Block POST_BIRCH = new PostBlock(Block.Properties.of(Material.WOOD, MaterialColor.SAND).strength(2.0F,15.0F).sound(SoundType.WOOD)).setRegistryName("post_birch");
    public static final Block POST_DARK_OAK = new PostBlock(Block.Properties.of(Material.WOOD, MaterialColor.COLOR_BROWN).strength(2.0F,15.0F).sound(SoundType.WOOD)).setRegistryName("post_dark_oak");
    public static final Block POST_JUNGLE = new PostBlock(Block.Properties.of(Material.WOOD, MaterialColor.DIRT).strength(2.0F,15.0F).sound(SoundType.WOOD)).setRegistryName("post_jungle");
    public static final Block POST_OAK = new PostBlock(Block.Properties.of(Material.WOOD, MaterialColor.WOOD).strength(2.0F,15.0F).sound(SoundType.WOOD)).setRegistryName("post_oak");
    public static final Block POST_SPRUCE = new PostBlock(Block.Properties.of(Material.WOOD, MaterialColor.PODZOL).strength(2.0F,15.0F).sound(SoundType.WOOD)).setRegistryName("post_spruce");

    public static final Block EGG_CARTON = new EggCartonBlock(Block.Properties.of(Material.WOOL, MaterialColor.COLOR_LIGHT_GRAY).strength(0.0F).sound(SoundType.WOOL).noOcclusion()).setRegistryName("eanimod:egg_carton");
    public static final Block TURTLE_EGG = new EnhancedTurtleEggBlock(BlockBehaviour.Properties.of(Material.EGG, MaterialColor.SAND).strength(0.5F).sound(SoundType.METAL).randomTicks().noOcclusion()).setRegistryName("eanimod:turtle_egg");
    public static final Block UNBOUNDHAY_BLOCK = new UnboundHayBlock(Block.Properties.of(Material.GRASS, MaterialColor.COLOR_YELLOW).strength(0.5F).sound(SoundType.GRASS).noOcclusion()).setRegistryName("eanimod:unboundhay_block");
    public static final Block SPARSEGRASS_BLOCK = new SparseGrassBlock(Block.Properties.of(Material.GRASS, MaterialColor.DIRT).randomTicks().strength(0.5F).sound(SoundType.GRASS)).setRegistryName("eanimod:sparsegrass_block");
    public static final Block PATCHYMYCELIUM_BLOCK = new PatchyMyceliumBlock(Block.Properties.of(Material.GRASS, MaterialColor.COLOR_PURPLE).randomTicks().strength(0.5F).sound(SoundType.GRASS)).setRegistryName("eanimod:patchymycelium_block");
    public static final Block GROWABLE_ALLIUM = new GrowablePlant(Block.Properties.of(Material.PLANT).randomTicks().noCollission().strength(0.0F).sound(SoundType.GRASS), Items.ALLIUM, true).setRegistryName("eanimod:growable_allium");
    public static final Block GROWABLE_AZURE_BLUET = new GrowablePlant(Block.Properties.of(Material.PLANT).randomTicks().noCollission().strength(0.0F).sound(SoundType.GRASS), Items.AZURE_BLUET, true).setRegistryName("eanimod:growable_azure_bluet");
    public static final Block GROWABLE_BLUE_ORCHID = new GrowablePlant(Block.Properties.of(Material.PLANT).randomTicks().noCollission().strength(0.0F).sound(SoundType.GRASS), Items.BLUE_ORCHID, true).setRegistryName("eanimod:growable_blue_orchid");
    public static final Block GROWABLE_CORNFLOWER = new GrowablePlant(Block.Properties.of(Material.PLANT).randomTicks().noCollission().strength(0.0F).sound(SoundType.GRASS), Items.CORNFLOWER, true).setRegistryName("eanimod:growable_cornflower");
    public static final Block GROWABLE_DANDELION = new GrowablePlant(Block.Properties.of(Material.PLANT).randomTicks().noCollission().strength(0.0F).sound(SoundType.GRASS), Items.DANDELION, true).setRegistryName("eanimod:growable_dandelion");
    public static final Block GROWABLE_OXEYE_DAISY = new GrowablePlant(Block.Properties.of(Material.PLANT).randomTicks().noCollission().strength(0.0F).sound(SoundType.GRASS), Items.OXEYE_DAISY, true).setRegistryName("eanimod:growable_oxeye_daisy");
    public static final Block GROWABLE_GRASS = new GrowablePlant(Block.Properties.of(Material.PLANT).randomTicks().noCollission().strength(0.0F).sound(SoundType.GRASS), Items.GRASS, false).setRegistryName("eanimod:growable_grass");
    public static final Block GROWABLE_FERN = new GrowablePlant(Block.Properties.of(Material.PLANT).randomTicks().noCollission().strength(0.0F).sound(SoundType.GRASS), Items.FERN, true).setRegistryName("eanimod:growable_fern");
    public static final Block GROWABLE_ROSE_BUSH = new GrowableDoubleHigh(Block.Properties.of(Material.REPLACEABLE_PLANT).randomTicks().noCollission().strength(0.0F).sound(SoundType.GRASS), Items.ROSE_BUSH, true).setRegistryName("eanimod:growable_rose_bush");
    public static final Block GROWABLE_SUNFLOWER = new GrowableDoubleHigh(Block.Properties.of(Material.REPLACEABLE_PLANT).randomTicks().noCollission().strength(0.0F).sound(SoundType.GRASS), Items.SUNFLOWER, true).setRegistryName("eanimod:growable_sunflower");
    public static final Block GROWABLE_TALL_GRASS = new GrowableDoubleHigh(Block.Properties.of(Material.REPLACEABLE_PLANT).randomTicks().noCollission().strength(0.0F).sound(SoundType.GRASS), Items.TALL_GRASS, false).setRegistryName("eanimod:growable_tall_grass");
    public static final Block GROWABLE_LARGE_FERN = new GrowableDoubleHigh(Block.Properties.of(Material.REPLACEABLE_PLANT).randomTicks().noCollission().strength(0.0F).sound(SoundType.GRASS), Items.LARGE_FERN, true).setRegistryName("eanimod:growable_large_fern");
}