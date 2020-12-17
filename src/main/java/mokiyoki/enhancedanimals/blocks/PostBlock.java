package mokiyoki.enhancedanimals.blocks;

import com.google.common.collect.ImmutableMap;
import mokiyoki.enhancedanimals.capability.post.PostCapabilityProvider;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.block.SixWayBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.LeadItem;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by moki on 25/08/2018.
 */
public class PostBlock extends Block implements IWaterLoggable {

    public static final DirectionProperty FACING = BlockStateProperties.FACING; //only NORTH, EAST, UP
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final BooleanProperty NORTH = SixWayBlock.NORTH;
    public static final BooleanProperty SOUTH = SixWayBlock.SOUTH;
    public static final BooleanProperty EAST = SixWayBlock.EAST;
    public static final BooleanProperty WEST = SixWayBlock.WEST;
    public static final BooleanProperty UP = SixWayBlock.UP;
    public static final BooleanProperty DOWN = SixWayBlock.DOWN;

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

    private final Map<BlockState, VoxelShape> stateToShapeMap;
    private final Map<BlockState, VoxelShape> stateToCollisionMap;

    public PostBlock(Properties properties) {
        super(properties);
        this.setDefaultState(this.stateContainer.getBaseState()
                .with(FACING, Direction.NORTH)
                .with(WATERLOGGED, Boolean.valueOf(false))
                .with(NORTH, Boolean.valueOf(false))
                .with(EAST, Boolean.valueOf(false))
                .with(SOUTH, Boolean.valueOf(false))
                .with(WEST, Boolean.valueOf(false))
                .with(UP, Boolean.valueOf(false))
                .with(DOWN, Boolean.valueOf(false)));
        this.stateToShapeMap = ImmutableMap.copyOf(this.stateContainer.getValidStates().stream().collect(Collectors.toMap(Function.identity(), PostBlock::getShapeForState)));
        this.stateToCollisionMap = ImmutableMap.copyOf(this.stateContainer.getValidStates().stream().collect(Collectors.toMap(Function.identity(), PostBlock::getCollisionForState)));
    }

    private static VoxelShape getShapeForState(BlockState state) {
        VoxelShape voxelshape = VoxelShapes.empty();
        Direction facing = state.get(FACING);

        if (facing == Direction.UP) {
            voxelshape = PILLAR;

            if (state.get(NORTH)) {
                voxelshape = VoxelShapes.or(voxelshape, NORTHFENCE);
            }

            if (state.get(SOUTH)) {
                voxelshape = VoxelShapes.or(voxelshape, SOUTHFENCE);
            }

            if (state.get(EAST)) {
                voxelshape = VoxelShapes.or(voxelshape, EASTFENCE);
            }

            if (state.get(WEST)) {
                voxelshape = VoxelShapes.or(voxelshape, WESTFENCE);
            }

        } else {
            if (state.get(UP)) {
                voxelshape = VoxelShapes.or(voxelshape, UPCONNECTOR);
            }

            if (state.get(DOWN)) {
                voxelshape = VoxelShapes.or(voxelshape, DOWNCONNECTOR);
            }

            if (facing == Direction.NORTH) {
                voxelshape = NORTHSOUTH;

                if (state.get(NORTH)) {
                    voxelshape = VoxelShapes.or(voxelshape, NORTHEXTENSION);
                }

                if (state.get(SOUTH)) {
                    voxelshape = VoxelShapes.or(voxelshape, SOUTHEXTENSION);
                }

            } else if (facing == Direction.EAST) {
                voxelshape = EASTWEST;

                if (state.get(EAST)) {
                    voxelshape = VoxelShapes.or(voxelshape, EASTEXTENSION);
                }

                if (state.get(WEST)) {
                    voxelshape = VoxelShapes.or(voxelshape, WESTEXTENSION);
                }
            }

        }

        return voxelshape;
    }

    private static VoxelShape getCollisionForState(BlockState state) {
        VoxelShape voxelshape = VoxelShapes.empty();
        Direction facing = state.get(FACING);

        if (facing == Direction.UP) {
            voxelshape = PILLAR;

            if (state.get(NORTH)) {
                voxelshape = VoxelShapes.or(voxelshape, NORTHFENCECOLLISION);
            }

            if (state.get(SOUTH)) {
                voxelshape = VoxelShapes.or(voxelshape, SOUTHFENCECOLLISION);
            }

            if (state.get(EAST)) {
                voxelshape = VoxelShapes.or(voxelshape, EASTFENCECOLLISION);
            }

            if (state.get(WEST)) {
                voxelshape = VoxelShapes.or(voxelshape, WESTFENCECOLLISION);
            }
        } else {
            return getShapeForState(state);
        }

        return voxelshape;
    }

    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return this.stateToShapeMap.get(state);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return this.stateToCollisionMap.get(state);
    }

    @Nullable
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        IBlockReader iblockreader = context.getWorld();
        BlockPos blockpos = context.getPos();
        FluidState fluidstate = context.getWorld().getFluidState(context.getPos());
        Direction thisFacing = context.getFace();
        if (thisFacing == Direction.SOUTH) {
            thisFacing = Direction.NORTH;
        } else if (thisFacing == Direction.WEST) {
            thisFacing = Direction.EAST;
        } else if (thisFacing == Direction.DOWN) {
            thisFacing = Direction.UP;
        }

        BlockPos blockpos1 = blockpos.north();
        BlockPos blockpos2 = blockpos.east();
        BlockPos blockpos3 = blockpos.south();
        BlockPos blockpos4 = blockpos.west();
        BlockPos blockpos5 = blockpos.up();
        BlockPos blockpos6 = blockpos.down();
        BlockState blockstate1 = iblockreader.getBlockState(blockpos1);
        BlockState blockstate2 = iblockreader.getBlockState(blockpos2);
        BlockState blockstate3 = iblockreader.getBlockState(blockpos3);
        BlockState blockstate4 = iblockreader.getBlockState(blockpos4);
        BlockState blockstate5 = iblockreader.getBlockState(blockpos5);
        BlockState blockstate6 = iblockreader.getBlockState(blockpos6);

        return super.getStateForPlacement(context)
                .with(FACING, thisFacing)
                .with(NORTH, thisFacing != Direction.EAST && this.canConnect(blockstate1, thisFacing))
                .with(EAST, thisFacing != Direction.NORTH && this.canConnect(blockstate2, thisFacing))
                .with(WEST, thisFacing != Direction.NORTH && this.canConnect(blockstate4, thisFacing))
                .with(SOUTH, thisFacing != Direction.EAST && this.canConnect(blockstate3, thisFacing))
                .with(UP, thisFacing != Direction.UP && this.canConnect(blockstate5, thisFacing))
                .with(DOWN, thisFacing != Direction.UP && this.canConnect(blockstate6, thisFacing))
                .with(WATERLOGGED, Boolean.valueOf(fluidstate.getFluid() == Fluids.WATER));
    }

    public boolean canConnect(BlockState state, Direction facing) {
        Block block = state.getBlock();
        if (facing == Direction.UP) {
            return this.isFence(block);
        } else if (block instanceof PostBlock) {
            return state.get(FACING) != facing;
        }
            return this.isFence(block);
    }

    private boolean isFence(Block block) {
        return block.isIn(BlockTags.FENCES) || block.isIn(BlockTags.WALLS);
    }

    @Override
    public BlockState updatePostPlacement(BlockState state, Direction facing, BlockState facingState, IWorld worldIn, BlockPos pos, BlockPos facingPos) {
        if (state.get(WATERLOGGED)) {
            worldIn.getPendingFluidTicks().scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(worldIn));
        }

            Direction orientation = state.get(FACING);
            switch (facing) {
                case NORTH:
                    return state.with(NORTH, orientation != Direction.EAST && canConnect(facingState, orientation));
                case SOUTH:
                    return state.with(SOUTH, orientation != Direction.EAST && canConnect(facingState, orientation));
                case EAST:
                    return state.with(EAST, orientation != Direction.NORTH && canConnect(facingState, orientation));
                case WEST:
                    return state.with(WEST, orientation != Direction.NORTH && canConnect(facingState, orientation));
                case UP:
                    return state.with(UP, orientation != Direction.UP && canConnect(facingState, orientation));
                case DOWN:
                    return state.with(DOWN, orientation != Direction.UP && canConnect(facingState, orientation));
            }
            return state;
    }

    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (worldIn.isRemote) {
            ItemStack itemstack = player.getHeldItem(handIn);
            return itemstack.getItem() == Items.LEAD ? ActionResultType.SUCCESS : ActionResultType.PASS;
        } else {
            return LeadItem.bindPlayerMobs(player, worldIn, pos);
        }
    }

    @Override
    public boolean propagatesSkylightDown(BlockState state, IBlockReader reader, BlockPos pos) {
        return !state.get(WATERLOGGED);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStillFluidState(false) : super.getFluidState(state);
    }

    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACING, WATERLOGGED, NORTH, SOUTH, EAST, WEST, UP, DOWN);
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

    @Override
    public void onExplosionDestroy(World worldIn, BlockPos pos, Explosion explosionIn) {
        worldIn.getCapability(PostCapabilityProvider.POST_CAP, null).orElse(new PostCapabilityProvider()).removePostPos(pos);
    }

    @Override
    public void onBlockHarvested(World worldIn, BlockPos pos, BlockState state, PlayerEntity player) {
        worldIn.getCapability(PostCapabilityProvider.POST_CAP, null).orElse(new PostCapabilityProvider()).removePostPos(pos);
        super.onBlockHarvested(worldIn, pos, state, player);
    }

}
