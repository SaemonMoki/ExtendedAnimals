package mokiyoki.enhancedanimals.blocks;

import com.google.common.collect.ImmutableMap;
import mokiyoki.enhancedanimals.capability.hay.HayCapabilityProvider;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FallingBlock;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class UnboundHayBlock extends FallingBlock implements IWaterLoggable {

    public static final EnumProperty<Direction.Axis> AXIS = BlockStateProperties.AXIS;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final IntegerProperty BITES = BlockStateProperties.LEVEL_0_8;

    private static final VoxelShape HAY_BLACK = Block.makeCuboidShape(0D, 0D, 0D, 5D, 16D, 5D);
    private static final VoxelShape HAY_GREY = Block.makeCuboidShape(5D, 0D, 0D, 11D, 16D, 5D);
    private static final VoxelShape HAY_WHITE = Block.makeCuboidShape(11D, 0D, 0D, 16D, 16D, 5D);
    private static final VoxelShape HAY_YELLOW = Block.makeCuboidShape(0D, 0D, 5D, 5D, 16D, 11D);
    private static final VoxelShape HAY_FUSHIA = Block.makeCuboidShape(5D, 0D, 5D, 11D, 16D, 11D);
    private static final VoxelShape HAY_CYAN = Block.makeCuboidShape(11D, 0D, 5D, 16D, 16D, 11D);
    private static final VoxelShape HAY_GREEN = Block.makeCuboidShape(0D, 0D, 11D, 5D, 16D, 16D);
    private static final VoxelShape HAY_RED = Block.makeCuboidShape(5D, 0D, 11D, 11D, 16D, 16D);
    private static final VoxelShape HAY_BLUE = Block.makeCuboidShape(11D, 0D, 11D, 16D, 16D, 16D);

    private static final VoxelShape X_HAY_BLACK = Block.makeCuboidShape(0D, 0D, 0D, 16D, 5D, 5D);
    private static final VoxelShape X_HAY_GREY = Block.makeCuboidShape(0D, 5D, 0D, 16D, 11D, 5D);
    private static final VoxelShape X_HAY_WHITE = Block.makeCuboidShape(0D, 11D, 0D, 16D, 16D, 5D);
    private static final VoxelShape X_HAY_YELLOW = Block.makeCuboidShape(0D, 0D, 5D, 16D, 5D, 11D);
    private static final VoxelShape X_HAY_FUSHIA = Block.makeCuboidShape(0D, 5D, 5D, 16D, 11D, 11D);
    private static final VoxelShape X_HAY_CYAN = Block.makeCuboidShape(0D, 11D, 5D, 16D, 16D, 11D);
    private static final VoxelShape X_HAY_GREEN = Block.makeCuboidShape(0D, 0D, 11D, 16D, 5D, 16D);
    private static final VoxelShape X_HAY_RED = Block.makeCuboidShape(0D, 5D, 11D, 16D, 11D, 16D);
    private static final VoxelShape X_HAY_BLUE = Block.makeCuboidShape(0D, 11D, 11D, 16D, 16D, 16D);

    private static final VoxelShape Z_HAY_BLACK = Block.makeCuboidShape(0D, 0D, 0D, 5D, 5D, 16D);
    private static final VoxelShape Z_HAY_GREY = Block.makeCuboidShape(5D, 0D, 0D, 11D, 5D, 16D);
    private static final VoxelShape Z_HAY_WHITE = Block.makeCuboidShape(11D, 0D, 0D, 16D, 5D, 16D);
    private static final VoxelShape Z_HAY_YELLOW = Block.makeCuboidShape(0D, 5D, 0D, 5D, 11D, 16D);
    private static final VoxelShape Z_HAY_FUSHIA = Block.makeCuboidShape(5D, 5D, 0D, 11D, 11D, 16D);
    private static final VoxelShape Z_HAY_CYAN = Block.makeCuboidShape(11D, 5D, 0D, 16D, 11D, 16D);
    private static final VoxelShape Z_HAY_GREEN = Block.makeCuboidShape(0D, 11D, 0D, 5D, 16D, 16D);
    private static final VoxelShape Z_HAY_RED = Block.makeCuboidShape(5D, 11D, 0D, 11D, 16D, 16D);
    private static final VoxelShape Z_HAY_BLUE = Block.makeCuboidShape(11D, 11D, 0D, 16D, 16D, 16D);
    
    private final Map<BlockState, VoxelShape> stateToShapeMap;

    public UnboundHayBlock(AbstractBlock.Properties properties) {
        super(properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(BITES, Integer.valueOf(0)).with(WATERLOGGED, Boolean.valueOf(false)).with(AXIS, Direction.Axis.Y));
        this.stateToShapeMap = ImmutableMap.copyOf(this.stateContainer.getValidStates().stream().collect(Collectors.toMap(Function.identity(), UnboundHayBlock::getShapeForState)));
    }

    private static VoxelShape getShapeForState(BlockState state) {
        VoxelShape voxelshape = VoxelShapes.empty();
        Direction.Axis axis = state.get(AXIS);
        int bites = 9 - (state.get(BITES));

        switch ((Direction.Axis)state.get(AXIS)) {
            case X:
            default:
                if (bites > 0) {
                    voxelshape = X_HAY_BLACK;
                }
                if (bites > 1) {
                    voxelshape = VoxelShapes.or(voxelshape, X_HAY_YELLOW);
                }
                if (bites > 2) {
                    voxelshape = VoxelShapes.or(voxelshape, X_HAY_GREEN);
                }
                if (bites > 3) {
                    voxelshape = VoxelShapes.or(voxelshape, X_HAY_FUSHIA);
                }
                if (bites > 4) {
                    voxelshape = VoxelShapes.or(voxelshape, X_HAY_RED);
                }
                if (bites > 5) {
                    voxelshape = VoxelShapes.or(voxelshape, X_HAY_GREY);
                }
                if (bites > 6) {
                    voxelshape = VoxelShapes.or(voxelshape, X_HAY_CYAN);
                }
                if (bites > 7) {
                    voxelshape = VoxelShapes.or(voxelshape, X_HAY_WHITE);
                }
                if (bites > 8) {
                    voxelshape = VoxelShapes.or(voxelshape, X_HAY_BLUE);
                }
                break;
            case Z:
                if (bites > 0) {
                    voxelshape = Z_HAY_BLACK;
                }
                if (bites > 1) {
                    voxelshape = VoxelShapes.or(voxelshape, Z_HAY_GREY);
                }
                if (bites > 2) {
                    voxelshape = VoxelShapes.or(voxelshape, Z_HAY_WHITE);
                }
                if (bites > 3) {
                    voxelshape = VoxelShapes.or(voxelshape, Z_HAY_FUSHIA);
                }
                if (bites > 4) {
                    voxelshape = VoxelShapes.or(voxelshape, Z_HAY_CYAN);
                }
                if (bites > 5) {
                    voxelshape = VoxelShapes.or(voxelshape, Z_HAY_YELLOW);
                }
                if (bites > 6) {
                    voxelshape = VoxelShapes.or(voxelshape, Z_HAY_RED);
                }
                if (bites > 7) {
                    voxelshape = VoxelShapes.or(voxelshape, Z_HAY_GREEN);
                }
                if (bites > 8) {
                    voxelshape = VoxelShapes.or(voxelshape, Z_HAY_BLUE);
                }
                break;
            case Y:
                if (bites > 0) {
                    voxelshape = HAY_GREEN;
                }
                if (bites > 1) {
                    voxelshape = VoxelShapes.or(voxelshape, HAY_RED);
                }
                if (bites > 2) {
                    voxelshape = VoxelShapes.or(voxelshape, HAY_BLUE);
                }
                if (bites > 3) {
                    voxelshape = VoxelShapes.or(voxelshape, HAY_FUSHIA);
                }
                if (bites > 4) {
                    voxelshape = VoxelShapes.or(voxelshape, HAY_CYAN);
                }
                if (bites > 5) {
                    voxelshape = VoxelShapes.or(voxelshape, HAY_YELLOW);
                }
                if (bites > 6) {
                    voxelshape = VoxelShapes.or(voxelshape, HAY_GREY);
                }
                if (bites > 7) {
                    voxelshape = VoxelShapes.or(voxelshape, HAY_BLACK);
                }
                if (bites > 8) {
                    voxelshape = VoxelShapes.or(voxelshape, HAY_WHITE);
                }
                break;
        }

        return voxelshape;
    }

    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return this.stateToShapeMap.get(state);
    }

    public VoxelShape getShape(BlockState state) {
        return this.stateToShapeMap.get(state);
    }

    public BlockState rotate(BlockState state, Rotation rot) {
        switch(rot) {
            case COUNTERCLOCKWISE_90:
            case CLOCKWISE_90:
                switch((Direction.Axis)state.get(AXIS)) {
                    case X:
                        return state.with(AXIS, Direction.Axis.Z);
                    case Z:
                        return state.with(AXIS, Direction.Axis.X);
                    default:
                        return state;
                }
            default:
                return state;
        }
    }

    public void eatFromBlock(World world, BlockState state, BlockPos pos) {
        passBiteUp(world, state, pos);
    }

    public void passBiteUp(World world, BlockState state, BlockPos pos) {
        if (world.getBlockState(pos.up()).getBlock() instanceof UnboundHayBlock) {
            ((UnboundHayBlock) world.getBlockState(pos.up()).getBlock()).passBiteUp(world, world.getBlockState(pos.up()), pos.up());
        } else {
            int bites = state.get(BITES);
            if (bites < 8) {
                world.setBlockState(pos, state.with(BITES, Integer.valueOf(bites + 1)).with(WATERLOGGED, state.get(WATERLOGGED)));
            } else {
                world.getCapability(HayCapabilityProvider.HAY_CAP, null).orElse(new HayCapabilityProvider()).removeHayPos(pos);
                world.removeBlock(pos, false);
            }
        }
    }

//    public void bringBitesUp(World world, BlockState state, BlockPos pos) {
//        int bites = state.get(BITES);
//        BlockState blockunder = world.getBlockState(pos.down());
//        if (blockunder.getBlock() instanceof UnboundHayBlock) {
//            int underbites = blockunder.get(BITES);
//
//
//
//        }
//    }

    @Override
    public void fillWithRain(World worldIn, BlockPos pos) {
        BlockState state = worldIn.getBlockState(pos);
        int i = state.get(BITES);
            if (i < 8) {
                worldIn.setBlockState(pos, state.with(BITES, Integer.valueOf(i + 1)), 3);
            } else {
                worldIn.getCapability(HayCapabilityProvider.HAY_CAP, null).orElse(new HayCapabilityProvider()).removeHayPos(pos);
                worldIn.removeBlock(pos, false);
            }
    }

    @Nullable
    public BlockState getStateForPlacement(BlockItemUseContext context) {

        FluidState fluidstate = context.getWorld().getFluidState(context.getPos());

        return this.getDefaultState().with(WATERLOGGED, Boolean.valueOf(fluidstate.getFluid() == Fluids.WATER)).with(AXIS, context.getFace().getAxis());
    }

    @Override
    public BlockState updatePostPlacement(BlockState state, Direction facing, BlockState facingState, IWorld worldIn, BlockPos pos, BlockPos facingPos) {
        if (state.get(WATERLOGGED)) {
            if (worldIn instanceof ServerWorld) {
                ((ServerWorld)worldIn).getCapability(HayCapabilityProvider.HAY_CAP, null).orElse(new HayCapabilityProvider()).removeHayPos(pos);
            }
            worldIn.removeBlock(pos, false);
            worldIn.getPendingFluidTicks().scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(worldIn));
        }

        //TODO add way to pass bites up on block update

        return super.updatePostPlacement(state, facing, facingState, worldIn, pos, facingPos);
    }

    public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
        if (state.get(WATERLOGGED)) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStillFluidState(false) : super.getFluidState(state);
    }

//    @Override
//    public BlockState rotate(BlockState state, Rotation rot) {
//        return state.with(FACING, rot.rotate(state.get(FACING)));
//    }

    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(BITES, WATERLOGGED, AXIS);
    }


    public void onEntityCollision(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {
        entityIn.setOnGround(true);

        double entityx = entityIn.getPosX() - pos.getX();
        double entityy = entityIn.getPosY() - pos.getY();
        double entityz = entityIn.getPosZ() - pos.getZ();

        AxisAlignedBB box = this.getShape(state).getBoundingBox();

        if (box.contains(entityx, entityy, entityz)) {
            entityIn.setMotionMultiplier(state, new Vector3d(0.9D, (double) 0.05F, 0.9D));
            super.onEntityCollision(state, worldIn, pos, entityIn);
        }
    }

    /**
     * Block's chance to react to a living entity falling on it.
     */
    public void onFallenUpon(World worldIn, BlockPos pos, Entity entityIn, float fallDistance) {
        BlockState state = worldIn.getBlockState(pos);
        float bites = state.get(BITES);
        entityIn.onLivingFall(fallDistance, 0.2F + (bites*0.1F));
    }

    @Override
    public void onExplosionDestroy(World worldIn, BlockPos pos, Explosion explosionIn) {
        worldIn.getCapability(HayCapabilityProvider.HAY_CAP, null).orElse(new HayCapabilityProvider()).removeHayPos(pos);
        super.onExplosionDestroy(worldIn, pos, explosionIn);
    }

    @Override
    public void onBlockHarvested(World worldIn, BlockPos pos, BlockState state, PlayerEntity player) {
        worldIn.getCapability(HayCapabilityProvider.HAY_CAP, null).orElse(new HayCapabilityProvider()).removeHayPos(pos);
        if (!worldIn.isRemote && !player.isCreative()) {
            int bites = state.get(BITES);
            ItemStack itemstack = new ItemStack(Items.WHEAT, (9-bites));
            ItemEntity itementity = new ItemEntity(worldIn, (double)pos.getX(), (double)pos.getY(), (double)pos.getZ(), itemstack);
            itementity.setDefaultPickupDelay();
            worldIn.addEntity(itementity);
        }
        super.onBlockHarvested(worldIn, pos, state, player);
    }

    @Override
    public void onBlockAdded(BlockState state, World worldIn, BlockPos pos, BlockState oldState, boolean isMoving) {
        worldIn.getCapability(HayCapabilityProvider.HAY_CAP, null).orElse(new HayCapabilityProvider()).addHayPos(pos);
        super.onBlockAdded(state, worldIn, pos, oldState, isMoving);
    }

    @Override
    public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        worldIn.getCapability(HayCapabilityProvider.HAY_CAP, null).orElse(new HayCapabilityProvider()).removeHayPos(pos);
        super.onReplaced(state, worldIn, pos, newState, isMoving);
    }

    //TODO add being able to add loose wheat back to the block
//    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn) {
//        int i = state.get(BITES);
//        if (i > 0 && player.getHeldItem(handIn).getItem() == Items.WHEAT) {
//            worldIn.setBlockState(pos, state.with(BITES, Integer.valueOf(i - 1)));
//            worldIn.playSound((PlayerEntity)null, pos, SoundEvents.BLOCK_SWEET_BERRY_BUSH_PLACE, SoundCategory.BLOCKS, 1.0F, 0.8F + worldIn.rand.nextFloat() * 0.4F);
//            return ActionResultType.SUCCESS;
//        }
//        return ActionResultType.PASS;
//    }
}
