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
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ObjectHolder;
import net.minecraftforge.registries.RegistryObject;

/**
 * Created by moki on 25/08/2018.
 */
@ObjectHolder(Reference.MODID)
public class ModBlocks {

    public static final DeferredRegister<Block> BLOCKS_DEFERRED_REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCKS, Reference.MODID);

    public static final RegistryObject<Block> POST_ACACIA = createDeferred(new PostBlock(Block.Properties.of(Material.WOOD, MaterialColor.COLOR_ORANGE).strength(2.0F,15.0F).sound(SoundType.WOOD)).setRegistryName("post_acacia"));
    public static final RegistryObject<Block> POST_BIRCH = createDeferred(new PostBlock(Block.Properties.of(Material.WOOD, MaterialColor.SAND).strength(2.0F,15.0F).sound(SoundType.WOOD)).setRegistryName("post_birch"));
    public static final RegistryObject<Block> POST_DARK_OAK = createDeferred(new PostBlock(Block.Properties.of(Material.WOOD, MaterialColor.COLOR_BROWN).strength(2.0F,15.0F).sound(SoundType.WOOD)).setRegistryName("post_dark_oak"));
    public static final RegistryObject<Block> POST_JUNGLE = createDeferred(new PostBlock(Block.Properties.of(Material.WOOD, MaterialColor.DIRT).strength(2.0F,15.0F).sound(SoundType.WOOD)).setRegistryName("post_jungle"));
    public static final RegistryObject<Block> POST_OAK = createDeferred(new PostBlock(Block.Properties.of(Material.WOOD, MaterialColor.WOOD).strength(2.0F,15.0F).sound(SoundType.WOOD)).setRegistryName("post_oak"));
    public static final RegistryObject<Block> POST_SPRUCE = createDeferred(new PostBlock(Block.Properties.of(Material.WOOD, MaterialColor.PODZOL).strength(2.0F,15.0F).sound(SoundType.WOOD)).setRegistryName("post_spruce"));

    public static final RegistryObject<Block> EGG_CARTON = createDeferred(new EggCartonBlock(Block.Properties.of(Material.WOOL, MaterialColor.COLOR_LIGHT_GRAY).strength(0.0F).sound(SoundType.WOOL).noOcclusion()).setRegistryName("eanimod:egg_carton"));
    public static final RegistryObject<Block> TURTLE_EGG = createDeferred(new EnhancedTurtleEggBlock(BlockBehaviour.Properties.of(Material.EGG, MaterialColor.SAND).strength(0.5F).sound(SoundType.METAL).randomTicks().noOcclusion()).setRegistryName("eanimod:turtle_egg"));
    public static final RegistryObject<Block> UNBOUNDHAY_BLOCK = createDeferred(new UnboundHayBlock(Block.Properties.of(Material.GRASS, MaterialColor.COLOR_YELLOW).strength(0.5F).sound(SoundType.GRASS).noOcclusion()).setRegistryName("eanimod:unboundhay_block"));
    public static final RegistryObject<Block> SPARSEGRASS_BLOCK = createDeferred(new SparseGrassBlock(Block.Properties.of(Material.GRASS, MaterialColor.DIRT).randomTicks().strength(0.5F).sound(SoundType.GRASS)).setRegistryName("eanimod:sparsegrass_block"));
    public static final RegistryObject<Block> PATCHYMYCELIUM_BLOCK = createDeferred(new PatchyMyceliumBlock(Block.Properties.of(Material.GRASS, MaterialColor.COLOR_PURPLE).randomTicks().strength(0.5F).sound(SoundType.GRASS)).setRegistryName("eanimod:patchymycelium_block"));
    public static final RegistryObject<Block> GROWABLE_ALLIUM = createDeferred(new GrowablePlant(Block.Properties.of(Material.PLANT).randomTicks().noCollission().strength(0.0F).sound(SoundType.GRASS), Items.ALLIUM, true).setRegistryName("eanimod:growable_allium"));
    public static final RegistryObject<Block> GROWABLE_AZURE_BLUET = createDeferred(new GrowablePlant(Block.Properties.of(Material.PLANT).randomTicks().noCollission().strength(0.0F).sound(SoundType.GRASS), Items.AZURE_BLUET, true).setRegistryName("eanimod:growable_azure_bluet"));
    public static final RegistryObject<Block> GROWABLE_BLUE_ORCHID = createDeferred(new GrowablePlant(Block.Properties.of(Material.PLANT).randomTicks().noCollission().strength(0.0F).sound(SoundType.GRASS), Items.BLUE_ORCHID, true).setRegistryName("eanimod:growable_blue_orchid"));
    public static final RegistryObject<Block> GROWABLE_CORNFLOWER = createDeferred(new GrowablePlant(Block.Properties.of(Material.PLANT).randomTicks().noCollission().strength(0.0F).sound(SoundType.GRASS), Items.CORNFLOWER, true).setRegistryName("eanimod:growable_cornflower"));
    public static final RegistryObject<Block> GROWABLE_DANDELION = createDeferred(new GrowablePlant(Block.Properties.of(Material.PLANT).randomTicks().noCollission().strength(0.0F).sound(SoundType.GRASS), Items.DANDELION, true).setRegistryName("eanimod:growable_dandelion"));
    public static final RegistryObject<Block> GROWABLE_OXEYE_DAISY = createDeferred(new GrowablePlant(Block.Properties.of(Material.PLANT).randomTicks().noCollission().strength(0.0F).sound(SoundType.GRASS), Items.OXEYE_DAISY, true).setRegistryName("eanimod:growable_oxeye_daisy"));
    public static final RegistryObject<Block> GROWABLE_GRASS = createDeferred(new GrowablePlant(Block.Properties.of(Material.PLANT).randomTicks().noCollission().strength(0.0F).sound(SoundType.GRASS), Items.GRASS, false).setRegistryName("eanimod:growable_grass"));
    public static final RegistryObject<Block> GROWABLE_FERN = createDeferred(new GrowablePlant(Block.Properties.of(Material.PLANT).randomTicks().noCollission().strength(0.0F).sound(SoundType.GRASS), Items.FERN, true).setRegistryName("eanimod:growable_fern"));
    public static final RegistryObject<Block> GROWABLE_ROSE_BUSH = createDeferred(new GrowableDoubleHigh(Block.Properties.of(Material.REPLACEABLE_PLANT).randomTicks().noCollission().strength(0.0F).sound(SoundType.GRASS), Items.ROSE_BUSH, true).setRegistryName("eanimod:growable_rose_bush"));
    public static final RegistryObject<Block> GROWABLE_SUNFLOWER = createDeferred(new GrowableDoubleHigh(Block.Properties.of(Material.REPLACEABLE_PLANT).randomTicks().noCollission().strength(0.0F).sound(SoundType.GRASS), Items.SUNFLOWER, true).setRegistryName("eanimod:growable_sunflower"));
    public static final RegistryObject<Block> GROWABLE_TALL_GRASS = createDeferred(new GrowableDoubleHigh(Block.Properties.of(Material.REPLACEABLE_PLANT).randomTicks().noCollission().strength(0.0F).sound(SoundType.GRASS), Items.TALL_GRASS, false).setRegistryName("eanimod:growable_tall_grass"));
    public static final RegistryObject<Block> GROWABLE_LARGE_FERN = createDeferred(new GrowableDoubleHigh(Block.Properties.of(Material.REPLACEABLE_PLANT).randomTicks().noCollission().strength(0.0F).sound(SoundType.GRASS), Items.LARGE_FERN, true).setRegistryName("eanimod:growable_large_fern"));

    private static RegistryObject<Block> createDeferred(Block block) {
        return BLOCKS_DEFERRED_REGISTRY.register(block.getRegistryName().getNamespace(), () -> block);
    }
}