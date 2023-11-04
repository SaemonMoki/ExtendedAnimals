package mokiyoki.enhancedanimals.init;

import mokiyoki.enhancedanimals.blocks.EggCartonBlock;
import mokiyoki.enhancedanimals.blocks.EnhancedAxolotlEggBlock;
import mokiyoki.enhancedanimals.blocks.EnhancedTurtleEggBlock;
import mokiyoki.enhancedanimals.blocks.GrowableDoubleHigh;
import mokiyoki.enhancedanimals.blocks.GrowablePlant;
import mokiyoki.enhancedanimals.blocks.PatchyMyceliumBlock;
import mokiyoki.enhancedanimals.blocks.PostBlock;
import mokiyoki.enhancedanimals.blocks.SparseGrassBlock;
import mokiyoki.enhancedanimals.blocks.UnboundHayBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static mokiyoki.enhancedanimals.GeneticAnimals.MODID;

/**
 * Created by moki on 25/08/2018.
 */
public class ModBlocks {

    public static final DeferredRegister<Block> BLOCKS_DEFERRED_REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);

    public static final RegistryObject<Block> POST_ACACIA = BLOCKS_DEFERRED_REGISTRY.register("post_acacia", () -> new PostBlock(Block.Properties.of().ignitedByLava().instrument(NoteBlockInstrument.BASS).mapColor(MapColor.WOOD).strength(2.0F,15.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> POST_BIRCH = BLOCKS_DEFERRED_REGISTRY.register("post_birch", () -> new PostBlock(BlockBehaviour.Properties.of().ignitedByLava().instrument(NoteBlockInstrument.BASS).mapColor(MapColor.WOOD).strength(2.0F,15.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> POST_DARK_OAK = BLOCKS_DEFERRED_REGISTRY.register("post_dark_oak", () -> new PostBlock(Block.Properties.of().ignitedByLava().instrument(NoteBlockInstrument.BASS).mapColor(MapColor.WOOD).strength(2.0F,15.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> POST_JUNGLE = BLOCKS_DEFERRED_REGISTRY.register("post_jungle", () -> new PostBlock(Block.Properties.of().ignitedByLava().instrument(NoteBlockInstrument.BASS).mapColor(MapColor.WOOD).strength(2.0F,15.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> POST_OAK = BLOCKS_DEFERRED_REGISTRY.register("post_oak", () -> new PostBlock(Block.Properties.of().ignitedByLava().instrument(NoteBlockInstrument.BASS).mapColor(MapColor.WOOD).strength(2.0F,15.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> POST_SPRUCE = BLOCKS_DEFERRED_REGISTRY.register("post_spruce", () -> new PostBlock(Block.Properties.of().ignitedByLava().instrument(NoteBlockInstrument.BASS).mapColor(MapColor.WOOD).strength(2.0F,15.0F).sound(SoundType.WOOD)));

    public static final RegistryObject<Block> EGG_CARTON = BLOCKS_DEFERRED_REGISTRY.register("egg_carton", () -> new EggCartonBlock(Block.Properties.of().ignitedByLava().mapColor(MapColor.WOOL).strength(0.0F).sound(SoundType.WOOL).noOcclusion()));
    public static final RegistryObject<Block> TURTLE_EGG = BLOCKS_DEFERRED_REGISTRY.register("turtle_egg", () -> new EnhancedTurtleEggBlock(BlockBehaviour.Properties.of().pushReaction(PushReaction.DESTROY).mapColor(MapColor.PLANT).strength(0.5F).sound(SoundType.METAL).randomTicks().noOcclusion()));
    public static final RegistryObject<Block> AXOLOTL_EGG = BLOCKS_DEFERRED_REGISTRY.register("axolotl_egg", () -> new EnhancedAxolotlEggBlock(BlockBehaviour.Properties.of().pushReaction(PushReaction.DESTROY).mapColor(MapColor.PLANT).strength(0.5F).sound(SoundType.SLIME_BLOCK).randomTicks().noOcclusion().noCollission()));
//    public static final RegistryObject<Block> CHICKEN_NEST = BLOCKS_DEFERRED_REGISTRY.register("chicken_nest", () -> new EnhancedChickenEggBlock(BlockBehaviour.Properties.of(Material.EGG, MaterialColor.CLAY).strength(0.5F).sound(SoundType.BONE_BLOCK).randomTicks().noOcclusion().noCollission()));
    public static final RegistryObject<Block> UNBOUNDHAY_BLOCK = BLOCKS_DEFERRED_REGISTRY.register("unboundhay_block", () -> new UnboundHayBlock(Block.Properties.of().mapColor(MapColor.GRASS).strength(0.5F).sound(SoundType.GRASS).noOcclusion()));
    public static final RegistryObject<Block> SPARSEGRASS_BLOCK = BLOCKS_DEFERRED_REGISTRY.register("sparsegrass_block", () -> new SparseGrassBlock(Block.Properties.of().mapColor(MapColor.GRASS).randomTicks().strength(0.5F).sound(SoundType.GRASS)));
    public static final RegistryObject<Block> PATCHYMYCELIUM_BLOCK = BLOCKS_DEFERRED_REGISTRY.register("patchymycelium_block", () -> new PatchyMyceliumBlock(Block.Properties.of().mapColor(MapColor.GRASS).randomTicks().strength(0.5F).sound(SoundType.GRASS)));
    public static final RegistryObject<Block> GROWABLE_ALLIUM = BLOCKS_DEFERRED_REGISTRY.register("growable_allium", () -> new GrowablePlant(Block.Properties.of().pushReaction(PushReaction.DESTROY).mapColor(MapColor.PLANT).randomTicks().noCollission().strength(0.0F).sound(SoundType.GRASS), Items.ALLIUM, true));
    public static final RegistryObject<Block> GROWABLE_AZURE_BLUET = BLOCKS_DEFERRED_REGISTRY.register("growable_azure_bluet", () -> new GrowablePlant(Block.Properties.of().pushReaction(PushReaction.DESTROY).mapColor(MapColor.PLANT).randomTicks().noCollission().strength(0.0F).sound(SoundType.GRASS), Items.AZURE_BLUET, true));
    public static final RegistryObject<Block> GROWABLE_BLUE_ORCHID = BLOCKS_DEFERRED_REGISTRY.register("growable_blue_orchid", () -> new GrowablePlant(Block.Properties.of().pushReaction(PushReaction.DESTROY).mapColor(MapColor.PLANT).randomTicks().noCollission().strength(0.0F).sound(SoundType.GRASS), Items.BLUE_ORCHID, true));
    public static final RegistryObject<Block> GROWABLE_CORNFLOWER = BLOCKS_DEFERRED_REGISTRY.register("growable_cornflower", () -> new GrowablePlant(Block.Properties.of().pushReaction(PushReaction.DESTROY).mapColor(MapColor.PLANT).randomTicks().noCollission().strength(0.0F).sound(SoundType.GRASS), Items.CORNFLOWER, true));
    public static final RegistryObject<Block> GROWABLE_DANDELION = BLOCKS_DEFERRED_REGISTRY.register("growable_dandelion", () -> new GrowablePlant(Block.Properties.of().pushReaction(PushReaction.DESTROY).mapColor(MapColor.PLANT).randomTicks().noCollission().strength(0.0F).sound(SoundType.GRASS), Items.DANDELION, true));
    public static final RegistryObject<Block> GROWABLE_OXEYE_DAISY = BLOCKS_DEFERRED_REGISTRY.register("growable_oxeye_daisy", () -> new GrowablePlant(Block.Properties.of().pushReaction(PushReaction.DESTROY).mapColor(MapColor.PLANT).randomTicks().noCollission().strength(0.0F).sound(SoundType.GRASS), Items.OXEYE_DAISY, true));
    public static final RegistryObject<Block> GROWABLE_GRASS = BLOCKS_DEFERRED_REGISTRY.register("growable_grass", () -> new GrowablePlant(Block.Properties.of().pushReaction(PushReaction.DESTROY).mapColor(MapColor.PLANT).randomTicks().noCollission().strength(0.0F).sound(SoundType.GRASS), Items.GRASS, false));
    public static final RegistryObject<Block> GROWABLE_FERN = BLOCKS_DEFERRED_REGISTRY.register("growable_fern", () -> new GrowablePlant(Block.Properties.of().pushReaction(PushReaction.DESTROY).mapColor(MapColor.PLANT).randomTicks().noCollission().strength(0.0F).sound(SoundType.GRASS), Items.FERN, true));
    public static final RegistryObject<Block> GROWABLE_ROSE_BUSH = BLOCKS_DEFERRED_REGISTRY.register("growable_rose_bush", () -> new GrowableDoubleHigh(Block.Properties.of().ignitedByLava().replaceable().pushReaction(PushReaction.DESTROY).mapColor(MapColor.PLANT).randomTicks().noCollission().strength(0.0F).sound(SoundType.GRASS), Items.ROSE_BUSH, true));
    public static final RegistryObject<Block> GROWABLE_SUNFLOWER = BLOCKS_DEFERRED_REGISTRY.register("growable_sunflower", () -> new GrowableDoubleHigh(Block.Properties.of().ignitedByLava().replaceable().pushReaction(PushReaction.DESTROY).mapColor(MapColor.PLANT).randomTicks().noCollission().strength(0.0F).sound(SoundType.GRASS), Items.SUNFLOWER, true));
    public static final RegistryObject<Block> GROWABLE_TALL_GRASS = BLOCKS_DEFERRED_REGISTRY.register("growable_tall_grass", () -> new GrowableDoubleHigh(Block.Properties.of().ignitedByLava().replaceable().pushReaction(PushReaction.DESTROY).mapColor(MapColor.PLANT).randomTicks().noCollission().strength(0.0F).sound(SoundType.GRASS), Items.TALL_GRASS, false));
    public static final RegistryObject<Block> GROWABLE_LARGE_FERN = BLOCKS_DEFERRED_REGISTRY.register("growable_large_fern", () -> new GrowableDoubleHigh(Block.Properties.of().ignitedByLava().replaceable().pushReaction(PushReaction.DESTROY).mapColor(MapColor.PLANT).randomTicks().noCollission().strength(0.0F).sound(SoundType.GRASS), Items.LARGE_FERN, true));

    public static void register(IEventBus modEventBus) {
        BLOCKS_DEFERRED_REGISTRY.register(modEventBus);
    }
}
