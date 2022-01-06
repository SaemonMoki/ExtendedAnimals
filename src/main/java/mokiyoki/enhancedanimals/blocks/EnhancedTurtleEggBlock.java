package mokiyoki.enhancedanimals.blocks;

import mokiyoki.enhancedanimals.capability.egg.EggCapabilityProvider;
import mokiyoki.enhancedanimals.capability.turtleegg.EggHolder;
import mokiyoki.enhancedanimals.capability.turtleegg.NestCapabilityProvider;
import mokiyoki.enhancedanimals.entity.EnhancedTurtle;
import mokiyoki.enhancedanimals.items.EnhancedEgg;
import mokiyoki.enhancedanimals.util.Genes;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.entity.passive.BatEntity;
import net.minecraft.entity.passive.TurtleEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.stats.Stats;
import net.minecraft.tags.BlockTags;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.GameRules;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import static mokiyoki.enhancedanimals.util.handlers.EventRegistry.ENHANCED_TURTLE;

public class EnhancedTurtleEggBlock extends NestBlock {
    private static final VoxelShape ONE_EGG_SHAPE = Block.makeCuboidShape(3.0D, 0.0D, 3.0D, 12.0D, 7.0D, 12.0D);
    private static final VoxelShape MULTI_EGG_SHAPE = Block.makeCuboidShape(1.0D, 0.0D, 1.0D, 15.0D, 7.0D, 15.0D);
    public static final IntegerProperty HATCH = BlockStateProperties.HATCH_0_2;
    public static final IntegerProperty EGGS = BlockStateProperties.EGGS_1_4;

    public EnhancedTurtleEggBlock(AbstractBlock.Properties properties) {
        super(properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(HATCH, Integer.valueOf(0)).with(EGGS, Integer.valueOf(1)));
    }

    @Override
    protected void subtractEggState(World world, BlockPos pos, BlockState state) {
        world.setBlockState(pos, state.with(EGGS, Integer.valueOf(state.get(EGGS) - 1)), 2);
        world.playEvent(2001, pos, Block.getStateId(state));
    }

    @Override
    protected void addEggState(World world, BlockPos pos, BlockState state) {
        world.setBlockState(pos, state.with(EGGS, Integer.valueOf(state.get(EGGS) + 1)), 2);
        world.playEvent(2001, pos, Block.getStateId(state));
    }

    @Override
    protected int getNumberOfEggs(BlockState state) {
        return state.get(HATCH);
    }

    /**
     * Called when the given entity walks on this Block
     */
    public void onEntityWalk(World worldIn, BlockPos pos, Entity entityIn) {
        this.tryTrample(worldIn, pos, entityIn, 100);
        super.onEntityWalk(worldIn, pos, entityIn);
    }

    /**
     * Block's chance to react to a living entity falling on it.
     */
    public void onFallenUpon(World worldIn, BlockPos pos, Entity entityIn, float fallDistance) {
        if (!(entityIn instanceof ZombieEntity)) {
            this.tryTrample(worldIn, pos, entityIn, 3);
        }

        super.onFallenUpon(worldIn, pos, entityIn, fallDistance);
    }

    private void tryTrample(World worldIn, BlockPos pos, Entity trampler, int chances) {
        if (this.canTrample(worldIn, trampler)) {
            if (!worldIn.isRemote && worldIn.rand.nextInt(chances) == 0) {
                BlockState blockstate = worldIn.getBlockState(pos);
                if (blockstate.isIn(Blocks.TURTLE_EGG)) {
                    this.removeOneEgg(worldIn, pos, blockstate);
                }
            }

        }
    }


    /**
     * Performs a random tick on a block.
     */
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (this.canGrow(world) && hasProperHabitat(world, pos)) {
            int i = state.get(HATCH);
            if (i < 2) {
                world.playSound((PlayerEntity)null, pos, SoundEvents.ENTITY_TURTLE_EGG_CRACK, SoundCategory.BLOCKS, 0.7F, 0.9F + random.nextFloat() * 0.2F);
                world.setBlockState(pos, state.with(HATCH, Integer.valueOf(i + 1)), 2);
            } else {
                world.playSound((PlayerEntity)null, pos, SoundEvents.ENTITY_TURTLE_EGG_HATCH, SoundCategory.BLOCKS, 0.7F, 0.9F + random.nextFloat() * 0.2F);
                world.removeBlock(pos, false);

                List<EggHolder> eggList = getEggsRemoveNestCapability(world, pos);
                int j = 1;

                if (eggList!=null) {
                    for (EggHolder egg : eggList) {
                        world.playEvent(2001, pos, Block.getStateId(state));
                        EnhancedTurtle turtle = ENHANCED_TURTLE.create(world);
                        turtle.setGenes(egg.getGenes());
                        turtle.setSharedGenes(egg.getGenes());
                        turtle.setSireName(egg.getSire());
                        turtle.setDamName(egg.getDam());
                        turtle.setGrowingAge();
                        turtle.initilizeAnimalSize();
                        turtle.setBirthTime();
                        turtle.setLocationAndAngles((double) pos.getX() + 0.3D + (double) j++ * 0.2D, (double) pos.getY(), (double) pos.getZ() + 0.3D, 0.0F, 0.0F);
                        turtle.setHome(pos);
                        turtle.setHasScute();
                        world.addEntity(turtle);
                    }
                } else {
                    for (int k = 0; k < state.get(EGGS); k++) {
                        world.playEvent(2001, pos, Block.getStateId(state));
                        EnhancedTurtle turtle = ENHANCED_TURTLE.create(world);
                        Genes turtleGenes = turtle.createInitialBreedGenes(turtle.getEntityWorld(), turtle.getPosition(), "WanderingTrader");
                        turtle.setGenes(turtleGenes);
                        turtle.setSharedGenes(turtleGenes);
                        turtle.setSireName("???");
                        turtle.setDamName("???");
                        turtle.setGrowingAge();
                        turtle.initilizeAnimalSize();
                        turtle.setBirthTime();
                        turtle.setLocationAndAngles((double) pos.getX() + 0.3D + (double) j++ * 0.2D, (double) pos.getY(), (double) pos.getZ() + 0.3D, 0.0F, 0.0F);
                        turtle.setHome(pos);
                        turtle.setHasScute();
                        world.addEntity(turtle);
                    }
                }
            }
        }

    }

    public static boolean hasProperHabitat(IBlockReader reader, BlockPos blockReader) {
        return isProperHabitat(reader, blockReader.down());
    }

    public static boolean isProperHabitat(IBlockReader reader, BlockPos pos) {
        return reader.getBlockState(pos).isIn(BlockTags.SAND);
    }

    public void onBlockAdded(BlockState state, World worldIn, BlockPos pos, BlockState oldState, boolean isMoving) {
        if (hasProperHabitat(worldIn, pos) && !worldIn.isRemote) {
            worldIn.playEvent(2005, pos, 0);
        }

    }

    private boolean canGrow(World worldIn) {
        float f = worldIn.func_242415_f(1.0F);
        if ((double)f < 0.69D && (double)f > 0.65D) {
            return true;
        } else {
            return worldIn.rand.nextInt(500) == 0;
        }
    }

    public boolean isReplaceable(BlockState state, BlockItemUseContext useContext) {
        return useContext.getItem().getItem() == this.asItem() && state.get(EGGS) < 4 || super.isReplaceable(state, useContext);
    }

    @Nullable
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        BlockState blockstate = context.getWorld().getBlockState(context.getPos());

        return blockstate.isIn(this) ? Objects.requireNonNull(super.getStateForPlacement(context)).with(EGGS, Integer.valueOf(Math.min(4, blockstate.get(EGGS) + 1))) :  super.getStateForPlacement(context);
    }

    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return state.get(EGGS) > 1 ? MULTI_EGG_SHAPE : ONE_EGG_SHAPE;
    }

    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(HATCH, EGGS);
    }

    private boolean canTrample(World worldIn, Entity trampler) {
        if (!(trampler instanceof TurtleEntity) && !(trampler instanceof BatEntity) && !(trampler instanceof EnhancedTurtle)) {
            if (!(trampler instanceof LivingEntity)) {
                return false;
            } else {
                return trampler instanceof PlayerEntity || net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(worldIn, trampler);
            }
        } else {
            return false;
        }
    }
}
