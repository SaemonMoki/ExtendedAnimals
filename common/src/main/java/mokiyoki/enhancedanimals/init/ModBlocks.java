package mokiyoki.enhancedanimals.init;

import mokiyoki.enhancedanimals.blocks.EggCartonBlock;
import mokiyoki.enhancedanimals.blocks.EnhancedAxolotlEggBlock;
import mokiyoki.enhancedanimals.blocks.EnhancedChickenEggBlock;
import mokiyoki.enhancedanimals.blocks.EnhancedTurtleEggBlock;
import mokiyoki.enhancedanimals.blocks.GrowableDoubleHigh;
import mokiyoki.enhancedanimals.blocks.GrowablePlant;
import mokiyoki.enhancedanimals.blocks.PatchyMyceliumBlock;
import mokiyoki.enhancedanimals.blocks.PostBlock;
import mokiyoki.enhancedanimals.blocks.SparseGrassBlock;
import mokiyoki.enhancedanimals.blocks.UnboundHayBlock;
import mokiyoki.enhancedanimals.util.Reference;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.item.Items;
import mokiyoki.enhancedanimals.platform.registry.RegistrationProvider;
import net.minecraft.core.registries.Registries;
import mokiyoki.enhancedanimals.platform.registry.RegistryEntry;

/**
 * Created by moki on 25/08/2018.
 */
public class ModBlocks {

    public static final RegistrationProvider<Block> BLOCKS_DEFERRED_REGISTRY = RegistrationProvider.get(Registries.BLOCK, Reference.MODID);

    public static final RegistryEntry<Block, Block> POST_ACACIA = BLOCKS_DEFERRED_REGISTRY.register("post_acacia", () -> new PostBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_ORANGE).forceSolidOn().instrument(NoteBlockInstrument.BASS).ignitedByLava().strength(2.0F,15.0F).sound(SoundType.WOOD)));
    public static final RegistryEntry<Block, Block> POST_BIRCH = BLOCKS_DEFERRED_REGISTRY.register("post_birch", () -> new PostBlock(BlockBehaviour.Properties.of().mapColor(MapColor.SAND).forceSolidOn().instrument(NoteBlockInstrument.BASS).ignitedByLava().strength(2.0F,15.0F).sound(SoundType.WOOD)));
    public static final RegistryEntry<Block, Block> POST_DARK_OAK = BLOCKS_DEFERRED_REGISTRY.register("post_dark_oak", () -> new PostBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).forceSolidOn().instrument(NoteBlockInstrument.BASS).ignitedByLava().strength(2.0F,15.0F).sound(SoundType.WOOD)));
    public static final RegistryEntry<Block, Block> POST_JUNGLE = BLOCKS_DEFERRED_REGISTRY.register("post_jungle", () -> new PostBlock(BlockBehaviour.Properties.of().mapColor(MapColor.DIRT).forceSolidOn().instrument(NoteBlockInstrument.BASS).ignitedByLava().strength(2.0F,15.0F).sound(SoundType.WOOD)));
    public static final RegistryEntry<Block, Block> POST_OAK = BLOCKS_DEFERRED_REGISTRY.register("post_oak", () -> new PostBlock(BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).forceSolidOn().instrument(NoteBlockInstrument.BASS).ignitedByLava().strength(2.0F,15.0F).sound(SoundType.WOOD)));
    public static final RegistryEntry<Block, Block> POST_SPRUCE = BLOCKS_DEFERRED_REGISTRY.register("post_spruce", () -> new PostBlock(BlockBehaviour.Properties.of().mapColor(MapColor.PODZOL).forceSolidOn().instrument(NoteBlockInstrument.BASS).ignitedByLava().strength(2.0F,15.0F).sound(SoundType.WOOD)));

    public static final RegistryEntry<Block, Block> EGG_CARTON = BLOCKS_DEFERRED_REGISTRY.register("egg_carton", () -> new EggCartonBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_GRAY).forceSolidOn().pushReaction(PushReaction.DESTROY).instrument(NoteBlockInstrument.GUITAR).ignitedByLava().strength(0.0F).sound(SoundType.WOOL).noOcclusion()));
    public static final RegistryEntry<Block, Block> TURTLE_EGG = BLOCKS_DEFERRED_REGISTRY.register("turtle_egg", () -> new EnhancedTurtleEggBlock(BlockBehaviour.Properties.of().mapColor(MapColor.SAND).forceSolidOn().pushReaction(PushReaction.DESTROY).strength(0.5F).sound(SoundType.METAL).randomTicks().noOcclusion()));
    public static final RegistryEntry<Block, Block> AXOLOTL_EGG = BLOCKS_DEFERRED_REGISTRY.register("axolotl_egg", () -> new EnhancedAxolotlEggBlock(BlockBehaviour.Properties.of().mapColor(MapColor.CLAY).forceSolidOn().pushReaction(PushReaction.DESTROY).strength(0.5F).sound(SoundType.SLIME_BLOCK).randomTicks().noOcclusion().noCollission()));
    public static final RegistryEntry<Block, Block> CHICKEN_NEST = BLOCKS_DEFERRED_REGISTRY.register("chicken_nest", () -> new EnhancedChickenEggBlock(BlockBehaviour.Properties.of().mapColor(MapColor.CLAY).forceSolidOn().pushReaction(PushReaction.DESTROY).strength(0.5F).sound(SoundType.BONE_BLOCK).randomTicks().noOcclusion().noCollission()));
    public static final RegistryEntry<Block, Block> UNBOUNDHAY_BLOCK = BLOCKS_DEFERRED_REGISTRY.register("unboundhay_block", () -> new UnboundHayBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_YELLOW).strength(0.5F).sound(SoundType.GRASS).noOcclusion().isSuffocating((a,b,c)->false)));

    public static final RegistryEntry<Block, Block> SPARSEGRASS_BLOCK = BLOCKS_DEFERRED_REGISTRY.register("sparsegrass_block", () -> new SparseGrassBlock(BlockBehaviour.Properties.of().mapColor(MapColor.DIRT).randomTicks().strength(0.5F).sound(SoundType.GRASS)));
    public static final RegistryEntry<Block, Block> PATCHYMYCELIUM_BLOCK = BLOCKS_DEFERRED_REGISTRY.register("patchymycelium_block", () -> new PatchyMyceliumBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PURPLE).randomTicks().strength(0.5F).sound(SoundType.GRASS)));
    public static final RegistryEntry<Block, Block> GROWABLE_ALLIUM = BLOCKS_DEFERRED_REGISTRY.register("growable_allium", () -> new GrowablePlant(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).offsetType(BlockBehaviour.OffsetType.XZ).pushReaction(PushReaction.DESTROY).randomTicks().noCollission().strength(0.0F).sound(SoundType.GRASS), Items.ALLIUM, true));
    public static final RegistryEntry<Block, Block> GROWABLE_AZURE_BLUET = BLOCKS_DEFERRED_REGISTRY.register("growable_azure_bluet", () -> new GrowablePlant(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).offsetType(BlockBehaviour.OffsetType.XZ).pushReaction(PushReaction.DESTROY).randomTicks().noCollission().strength(0.0F).sound(SoundType.GRASS), Items.AZURE_BLUET, true));
    public static final RegistryEntry<Block, Block> GROWABLE_BLUE_ORCHID = BLOCKS_DEFERRED_REGISTRY.register("growable_blue_orchid", () -> new GrowablePlant(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).offsetType(BlockBehaviour.OffsetType.XZ).pushReaction(PushReaction.DESTROY).randomTicks().noCollission().strength(0.0F).sound(SoundType.GRASS), Items.BLUE_ORCHID, true));
    public static final RegistryEntry<Block, Block> GROWABLE_CORNFLOWER = BLOCKS_DEFERRED_REGISTRY.register("growable_cornflower", () -> new GrowablePlant(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).offsetType(BlockBehaviour.OffsetType.XZ).pushReaction(PushReaction.DESTROY).randomTicks().noCollission().strength(0.0F).sound(SoundType.GRASS), Items.CORNFLOWER, true));
    public static final RegistryEntry<Block, Block> GROWABLE_DANDELION = BLOCKS_DEFERRED_REGISTRY.register("growable_dandelion", () -> new GrowablePlant(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).offsetType(BlockBehaviour.OffsetType.XZ).pushReaction(PushReaction.DESTROY).randomTicks().noCollission().strength(0.0F).sound(SoundType.GRASS), Items.DANDELION, true));
    public static final RegistryEntry<Block, Block> GROWABLE_OXEYE_DAISY = BLOCKS_DEFERRED_REGISTRY.register("growable_oxeye_daisy", () -> new GrowablePlant(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).offsetType(BlockBehaviour.OffsetType.XZ).pushReaction(PushReaction.DESTROY).randomTicks().noCollission().strength(0.0F).sound(SoundType.GRASS), Items.OXEYE_DAISY, true));
    public static final RegistryEntry<Block, Block> GROWABLE_GRASS = BLOCKS_DEFERRED_REGISTRY.register("growable_grass", () -> new GrowablePlant(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).offsetType(BlockBehaviour.OffsetType.XZ).pushReaction(PushReaction.DESTROY).randomTicks().noCollission().strength(0.0F).sound(SoundType.GRASS), Items.SHORT_GRASS, false));
    public static final RegistryEntry<Block, Block> GROWABLE_FERN = BLOCKS_DEFERRED_REGISTRY.register("growable_fern", () -> new GrowablePlant(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).offsetType(BlockBehaviour.OffsetType.XZ).pushReaction(PushReaction.DESTROY).randomTicks().noCollission().strength(0.0F).sound(SoundType.GRASS), Items.FERN, true));
    public static final RegistryEntry<Block, Block> GROWABLE_ROSE_BUSH = BLOCKS_DEFERRED_REGISTRY.register("growable_rose_bush", () -> new GrowableDoubleHigh(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).replaceable().offsetType(BlockBehaviour.OffsetType.XZ).ignitedByLava().pushReaction(PushReaction.DESTROY).randomTicks().noCollission().strength(0.0F).sound(SoundType.GRASS), Items.ROSE_BUSH, true));
    public static final RegistryEntry<Block, Block> GROWABLE_SUNFLOWER = BLOCKS_DEFERRED_REGISTRY.register("growable_sunflower", () -> new GrowableDoubleHigh(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).replaceable().offsetType(BlockBehaviour.OffsetType.XZ).ignitedByLava().pushReaction(PushReaction.DESTROY).randomTicks().noCollission().strength(0.0F).sound(SoundType.GRASS), Items.SUNFLOWER, true));
    public static final RegistryEntry<Block, Block> GROWABLE_TALL_GRASS = BLOCKS_DEFERRED_REGISTRY.register("growable_tall_grass", () -> new GrowableDoubleHigh(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).replaceable().offsetType(BlockBehaviour.OffsetType.XZ).ignitedByLava().pushReaction(PushReaction.DESTROY).randomTicks().noCollission().strength(0.0F).sound(SoundType.GRASS), Items.TALL_GRASS, false));
    public static final RegistryEntry<Block, Block> GROWABLE_LARGE_FERN = BLOCKS_DEFERRED_REGISTRY.register("growable_large_fern", () -> new GrowableDoubleHigh(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).replaceable().offsetType(BlockBehaviour.OffsetType.XZ).ignitedByLava().pushReaction(PushReaction.DESTROY).randomTicks().noCollission().strength(0.0F).sound(SoundType.GRASS), Items.LARGE_FERN, true));

    public static void init() {
    }
}
