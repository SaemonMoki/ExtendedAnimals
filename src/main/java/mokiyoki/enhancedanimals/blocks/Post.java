package mokiyoki.enhancedanimals.blocks;

import com.sun.org.apache.xpath.internal.operations.Bool;
import mokiyoki.enhancedanimals.capability.post.PostCapabilityProvider;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FenceBlock;
import net.minecraft.block.FourWayBlock;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.block.SixWayBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.fluid.IFluidState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.LeadItem;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.ActionResultType;
//import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import javax.xml.bind.annotation.XmlType;
import java.util.Map;

/**
 * Created by moki on 25/08/2018.
 */
public class Post extends Block implements IWaterLoggable {

    public static final DirectionProperty FACING = BlockStateProperties.FACING; //only NORTH, EAST, UP
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final BooleanProperty NORTH = SixWayBlock.NORTH;
    public static final BooleanProperty SOUTH = SixWayBlock.SOUTH;
    public static final BooleanProperty EAST = SixWayBlock.EAST;
    public static final BooleanProperty WEST = SixWayBlock.WEST;
    public static final BooleanProperty UP = SixWayBlock.UP;
    public static final BooleanProperty DOWN = SixWayBlock.DOWN;
    public static final BooleanProperty FENCENORTH = BooleanProperty.create("fencenorth");
    public static final BooleanProperty FENCESOUTH = BooleanProperty.create("fencesouth");
    public static final BooleanProperty FENCEEAST = BooleanProperty.create("fenceeast");
    public static final BooleanProperty FENCEWEST = BooleanProperty.create("fencewest");

    private static final VoxelShape PILLAR = Block.makeCuboidShape(6D, 0.0D, 6D, 10D, 16D, 10D);
    private static final VoxelShape EASTWEST = Block.makeCuboidShape(0.0D, 6D, 6D, 16D, 10D, 10D);
    private static final VoxelShape NORTHSOUTH = Block.makeCuboidShape(6D, 6D, 0.0D, 10D, 10D, 16D);

    private static final VoxelShape NORTHFENCE = Block.makeCuboidShape(7D, 6D, -6D, 9D, 15D, 6D);
    private static final VoxelShape NORTHFENCECOLLISION = Block.makeCuboidShape(7D, 0D, -8D, 9D, 24D, 8D);
    private static final VoxelShape NORTHEXTENSION = Block.makeCuboidShape(6D, 6D, -6D, 10D, 10D, 0D);
    private static final VoxelShape SOUTHFENCE = Block.makeCuboidShape(7D, 6D, 10D, 9D, 15D, 22D);
    private static final VoxelShape SOUTHFENCECOLLISION = Block.makeCuboidShape(7D, 0D, 8D, 9D, 24D, 24D);
    private static final VoxelShape SOUTHEXTENSION = Block.makeCuboidShape(6D, 6D, 16D, 10D, 10D, 22D);
    private static final VoxelShape EASTFENCE = Block.makeCuboidShape(10D, 6D, 7D, 22D, 15D, 9D);
    private static final VoxelShape EASTFENCECOLLISION = Block.makeCuboidShape(8D, 0D, 7D, 24D, 24D, 9D);
    private static final VoxelShape EASTEXTENSION = Block.makeCuboidShape(16D, 6D, 6D, 22D, 10D, 10D);
    private static final VoxelShape WESTFENCE = Block.makeCuboidShape(-6D, 6D, 7D, 6D, 15D, 9D);
    private static final VoxelShape WESTFENCECOLLISION = Block.makeCuboidShape(-8D, 0D, 7D, 8D, 24D, 9D);
    private static final VoxelShape WESTEXTENSION = Block.makeCuboidShape(-6D, 6D, 6D, 0D, 10D, 10D);
    private static final VoxelShape UPCONNECTOR = Block.makeCuboidShape(6D, 10D, 6D, 10D, 16D, 10D);
    private static final VoxelShape DOWNCONNECTOR = Block.makeCuboidShape(6D, 0D, 6D, 10D, 6D, 10D);

    public Post(Properties properties) {
        super(properties);
        this.setDefaultState(this.stateContainer.getBaseState()
                .with(FACING, Direction.NORTH)
                .with(WATERLOGGED, Boolean.valueOf(false))
                .with(NORTH, Boolean.valueOf(false))
                .with(EAST, Boolean.valueOf(false))
                .with(SOUTH, Boolean.valueOf(false))
                .with(WEST, Boolean.valueOf(false))
                .with(UP, Boolean.valueOf(false))
                .with(DOWN, Boolean.valueOf(false))
                .with(FENCENORTH, Boolean.valueOf(false))
                .with(FENCEEAST, Boolean.valueOf(false))
                .with(FENCEWEST, Boolean.valueOf(false))
                .with(FENCESOUTH, Boolean.valueOf(false)));
    }

    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        VoxelShape postShape;
            Direction facing = state.get(FACING);
            if (facing == Direction.NORTH) {
                postShape = NORTHSOUTH;
                if (state.get(NORTH)) {
                    postShape = VoxelShapes.combineAndSimplify(postShape, NORTHEXTENSION, IBooleanFunction.OR);
                }
                if (state.get(SOUTH)) {
                    postShape = VoxelShapes.combine(postShape, SOUTHEXTENSION, IBooleanFunction.OR);
                }
                if (state.get(UP)) {
                    postShape = VoxelShapes.combine(postShape, UPCONNECTOR, IBooleanFunction.OR);
                }
                if (state.get(DOWN)) {
                    postShape = VoxelShapes.combine(postShape, DOWNCONNECTOR, IBooleanFunction.OR);
                }
            } else if (facing == Direction.EAST) {
                postShape = EASTWEST;
                if (state.get(EAST)) {
                    postShape = VoxelShapes.combine(postShape, EASTEXTENSION, IBooleanFunction.OR);
                }
                if (state.get(WEST)) {
                    postShape = VoxelShapes.combine(postShape, WESTEXTENSION, IBooleanFunction.OR);
                }
                if (state.get(UP)) {
                    postShape = VoxelShapes.combine(postShape, UPCONNECTOR, IBooleanFunction.OR);
                }
                if (state.get(DOWN)) {
                    postShape = VoxelShapes.combine(postShape, DOWNCONNECTOR, IBooleanFunction.OR);
                }
            } else {
                postShape = PILLAR;
                if (state.get(FENCENORTH)) {
                    postShape = VoxelShapes.combine(postShape, NORTHFENCE, IBooleanFunction.OR);
                }
                if (state.get(FENCESOUTH)) {
                    postShape = VoxelShapes.combine(postShape, SOUTHFENCE, IBooleanFunction.OR);
                }
                if (state.get(FENCEEAST)) {
                    postShape = VoxelShapes.combine(postShape, EASTFENCE, IBooleanFunction.OR);
                }
                if (state.get(FENCEWEST)) {
                    postShape = VoxelShapes.combine(postShape, WESTFENCE, IBooleanFunction.OR);
                }
            }

        return postShape;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        Direction Orientation = state.get(FACING);
        if (Orientation == Direction.UP) {
            VoxelShape fenceConnected = getShape(state, worldIn, pos, context);
            if (state.get(FENCENORTH)) {
                fenceConnected = VoxelShapes.combine(fenceConnected, NORTHFENCECOLLISION, IBooleanFunction.OR);
            }
            if (state.get(FENCESOUTH)) {
                fenceConnected = VoxelShapes.combine(fenceConnected, SOUTHFENCECOLLISION, IBooleanFunction.OR);
            }
            if (state.get(FENCEEAST)) {
                fenceConnected = VoxelShapes.combine(fenceConnected, EASTFENCECOLLISION, IBooleanFunction.OR);
            }
            if (state.get(FENCEWEST)) {
                fenceConnected = VoxelShapes.combine(fenceConnected, WESTFENCECOLLISION, IBooleanFunction.OR);
            }
            return fenceConnected;
        } else {
            return getShape(state, worldIn, pos, context);
        }
    }

    @Nullable
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        IBlockReader iblockreader = context.getWorld();
        BlockPos blockpos = context.getPos();
        IFluidState ifluidstate = context.getWorld().getFluidState(context.getPos());
        Direction thisFacing = context.getFace();
        if (thisFacing == Direction.SOUTH) {
            thisFacing = Direction.NORTH;
        } else if (thisFacing == Direction.WEST) {
            thisFacing = Direction.EAST;
        } else if (thisFacing == Direction.DOWN) {
            thisFacing = Direction.UP;
        }
        return this.getDefaultState().with(FACING, thisFacing)
                .with(WATERLOGGED, ifluidstate.getFluid() == Fluids.WATER)
                .with(NORTH, this.shouldConnectTo(iblockreader.getBlockState(blockpos.north()), Direction.NORTH, thisFacing, false))
                .with(SOUTH, this.shouldConnectTo(iblockreader.getBlockState(blockpos.south()), Direction.SOUTH, thisFacing, false))
                .with(EAST, this.shouldConnectTo(iblockreader.getBlockState(blockpos.east()), Direction.EAST, thisFacing, false))
                .with(WEST, this.shouldConnectTo(iblockreader.getBlockState(blockpos.west()), Direction.WEST, thisFacing, false))
                .with(UP, this.shouldConnectTo(iblockreader.getBlockState(blockpos.up()), Direction.UP, thisFacing, false))
                .with(DOWN, this.shouldConnectTo(iblockreader.getBlockState(blockpos.down()), Direction.DOWN, thisFacing, false))
                .with(FENCENORTH, this.shouldConnectTo(iblockreader.getBlockState(blockpos.north()), Direction.NORTH, thisFacing, true))
                .with(FENCESOUTH, this.shouldConnectTo(iblockreader.getBlockState(blockpos.south()), Direction.SOUTH, thisFacing, true))
                .with(FENCEEAST, this.shouldConnectTo(iblockreader.getBlockState(blockpos.east()), Direction.EAST, thisFacing, true))
                .with(FENCEWEST, this.shouldConnectTo(iblockreader.getBlockState(blockpos.west()), Direction.WEST, thisFacing, true));
    }

    @Override
    public BlockState updatePostPlacement(BlockState state, Direction facing, BlockState facingState, IWorld worldIn, BlockPos pos, BlockPos facingPos) {
        if (state.get(WATERLOGGED)) {
            worldIn.getPendingFluidTicks().scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(worldIn));
        }

            Direction orientation = state.get(FACING);
            switch (facing) {
                case NORTH:
                    return state.with(NORTH, shouldConnectTo(facingState, facing, orientation, false))
                            .with(FENCENORTH, shouldConnectTo(facingState, facing, orientation, true));
                case SOUTH:
                    return state.with(SOUTH, shouldConnectTo(facingState, facing, orientation, false))
                            .with(FENCESOUTH, shouldConnectTo(facingState, facing, orientation, true));
                case EAST:
                    return state.with(EAST, shouldConnectTo(facingState, facing, orientation, false))
                            .with(FENCEEAST, shouldConnectTo(facingState, facing, orientation, true));
                case WEST:
                    return state.with(WEST, shouldConnectTo(facingState, facing, orientation, false))
                            .with(FENCEWEST, shouldConnectTo(facingState, facing, orientation, true));
                case UP:
                    return state.with(UP, shouldConnectTo(facingState, facing, orientation, false));
                case DOWN:
                    return state.with(DOWN, shouldConnectTo(facingState, facing, orientation, false));
            }
            return state;
    }

    public boolean shouldConnectTo(BlockState thatBlocksState, Direction thatBlocksDirection, Direction thisBlocksOrientation, Boolean fenceConnection) {
        Block thatBlock = thatBlocksState.getBlock();
        boolean isConnectable = thatBlock instanceof FenceBlock || thatBlock instanceof Post;
        if (isConnectable) {
            if (thisBlocksOrientation == Direction.UP || thisBlocksOrientation == Direction.DOWN) {
                if (fenceConnection && thatBlock instanceof FenceBlock && thatBlocksDirection != Direction.UP && thatBlocksDirection != Direction.DOWN) {
                    return true;
                }
            } else if (!fenceConnection){
                isConnectable = thatBlock instanceof FenceBlock;
                if ((thisBlocksOrientation == Direction.NORTH || thisBlocksOrientation == Direction.SOUTH) && thatBlocksDirection != Direction.EAST && thatBlocksDirection != Direction.WEST ) {
                    if (isConnectable) {
                        return true;
                    }
                    Direction thatBlocksFacing = thatBlocksState.get(FACING);
                    if (thatBlocksDirection == Direction.UP || thatBlocksDirection == Direction.DOWN) {
                        if (thatBlocksFacing == Direction.UP || thatBlocksFacing == Direction.DOWN) {
                            return true;
                        }
                    } else if (thatBlocksFacing != Direction.NORTH && thatBlocksFacing != Direction.SOUTH) {
                        return true;
                    }
                } else if ((thisBlocksOrientation == Direction.EAST || thisBlocksOrientation == Direction.WEST) && thatBlocksDirection != Direction.NORTH && thatBlocksDirection != Direction.SOUTH ) {
                    if (isConnectable) {
                        return true;
                    }
                    Direction thatBlocksFacing = thatBlocksState.get(FACING);
                    if (thatBlocksDirection == Direction.UP || thatBlocksDirection == Direction.DOWN) {
                        if (thatBlocksFacing == Direction.UP || thatBlocksFacing == Direction.DOWN) {
                            return true;
                        }
                    } else if (thatBlocksFacing != Direction.EAST && thatBlocksFacing != Direction.WEST) {
                        return true;
                    }
                }
            }
        }
            return false;
    }

    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (worldIn.isRemote) {
            ItemStack itemstack = player.getHeldItem(handIn);
            return itemstack.getItem() == Items.LEAD ? ActionResultType.SUCCESS : ActionResultType.PASS;
        } else {
            return LeadItem.func_226641_a_(player, worldIn, pos);
        }
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rot) {
        return state.with(FACING, rot.rotate(state.get(FACING)));
    }

    @Override
    public boolean propagatesSkylightDown(BlockState state, IBlockReader reader, BlockPos pos) {
        return !state.get(WATERLOGGED);
    }

    @Override
    public IFluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStillFluidState(false) : super.getFluidState(state);
    }

    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACING, WATERLOGGED, NORTH, SOUTH, EAST, WEST, UP, DOWN, FENCENORTH, FENCESOUTH, FENCEEAST, FENCEWEST);
    }

    public boolean allowsMovement(BlockState state, IBlockReader worldIn, BlockPos pos, PathType type) {
        return false;
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos blockPos, BlockState state, LivingEntity placer, ItemStack stack) {
        if (state.get(FACING) != Direction.UP) {
            worldIn.getCapability(PostCapabilityProvider.POST_CAP, null).orElse(new PostCapabilityProvider()).addPostPos(blockPos);
        }
    }


    //TODO add facing check to removing POST_CAP
    @Override
    public void onPlayerDestroy(IWorld worldIn, BlockPos pos, BlockState state) {
        worldIn.getWorld().getCapability(PostCapabilityProvider.POST_CAP, null).orElse(new PostCapabilityProvider()).removePostPos(pos);
    }

    @Override
    public void onExplosionDestroy(World worldIn, BlockPos pos, Explosion explosionIn) {
        worldIn.getWorld().getCapability(PostCapabilityProvider.POST_CAP, null).orElse(new PostCapabilityProvider()).removePostPos(pos);
    }

    @Override
    public void onBlockHarvested(World worldIn, BlockPos pos, BlockState state, PlayerEntity player) {
        worldIn.getWorld().getCapability(PostCapabilityProvider.POST_CAP, null).orElse(new PostCapabilityProvider()).removePostPos(pos);
        super.onBlockHarvested(worldIn, pos, state, player);
    }

}
