package mokiyoki.enhancedanimals.blocks;

import com.google.common.collect.ImmutableMap;
import mokiyoki.enhancedanimals.capability.post.PostCapabilityProvider;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.PipeBlock;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.LeadItem;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

/**
 * Created by moki on 25/08/2018.
 */
public class PostBlock extends Block implements SimpleWaterloggedBlock {

    public static final DirectionProperty FACING = BlockStateProperties.FACING; //only NORTH, EAST, UP
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final BooleanProperty NORTH = PipeBlock.NORTH;
    public static final BooleanProperty SOUTH = PipeBlock.SOUTH;
    public static final BooleanProperty EAST = PipeBlock.EAST;
    public static final BooleanProperty WEST = PipeBlock.WEST;
    public static final BooleanProperty UP = PipeBlock.UP;
    public static final BooleanProperty DOWN = PipeBlock.DOWN;

    private static final VoxelShape PILLAR = Block.box(6D, 0.0D, 6D, 10D, 16D, 10D);
    private static final VoxelShape EASTWEST = Block.box(0.0D, 6D, 6D, 16D, 10D, 10D);
    private static final VoxelShape NORTHSOUTH = Block.box(6D, 6D, 0.0D, 10D, 10D, 16D);

    private static final VoxelShape NORTHFENCE = Block.box(7D, 6D, -6D, 9D, 15D, 6D);
    private static final VoxelShape NORTHFENCECOLLISION = Block.box(7D, 0D, -8D, 9D, 24D, 8D);
    private static final VoxelShape NORTHEXTENSION = Block.box(6D, 6D, -6D, 10D, 10D, 0D);
    private static final VoxelShape SOUTHFENCE = Block.box(7D, 6D, 10D, 9D, 15D, 22D);
    private static final VoxelShape SOUTHFENCECOLLISION = Block.box(7D, 0D, 8D, 9D, 24D, 24D);
    private static final VoxelShape SOUTHEXTENSION = Block.box(6D, 6D, 16D, 10D, 10D, 22D);
    private static final VoxelShape EASTFENCE = Block.box(10D, 6D, 7D, 22D, 15D, 9D);
    private static final VoxelShape EASTFENCECOLLISION = Block.box(8D, 0D, 7D, 24D, 24D, 9D);
    private static final VoxelShape EASTEXTENSION = Block.box(16D, 6D, 6D, 22D, 10D, 10D);
    private static final VoxelShape WESTFENCE = Block.box(-6D, 6D, 7D, 6D, 15D, 9D);
    private static final VoxelShape WESTFENCECOLLISION = Block.box(-8D, 0D, 7D, 8D, 24D, 9D);
    private static final VoxelShape WESTEXTENSION = Block.box(-6D, 6D, 6D, 0D, 10D, 10D);
    private static final VoxelShape UPCONNECTOR = Block.box(6D, 10D, 6D, 10D, 16D, 10D);
    private static final VoxelShape DOWNCONNECTOR = Block.box(6D, 0D, 6D, 10D, 6D, 10D);

    private final Map<BlockState, VoxelShape> stateToShapeMap;
    private final Map<BlockState, VoxelShape> stateToCollisionMap;

    public PostBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(WATERLOGGED, Boolean.valueOf(false))
                .setValue(NORTH, Boolean.valueOf(false))
                .setValue(EAST, Boolean.valueOf(false))
                .setValue(SOUTH, Boolean.valueOf(false))
                .setValue(WEST, Boolean.valueOf(false))
                .setValue(UP, Boolean.valueOf(false))
                .setValue(DOWN, Boolean.valueOf(false)));
        this.stateToShapeMap = ImmutableMap.copyOf(this.stateDefinition.getPossibleStates().stream().collect(Collectors.toMap(Function.identity(), PostBlock::getShapeForState)));
        this.stateToCollisionMap = ImmutableMap.copyOf(this.stateDefinition.getPossibleStates().stream().collect(Collectors.toMap(Function.identity(), PostBlock::getCollisionForState)));
    }

    private static VoxelShape getShapeForState(BlockState state) {
        VoxelShape voxelshape = Shapes.empty();
        Direction facing = state.getValue(FACING);

        if (facing == Direction.UP) {
            voxelshape = PILLAR;

            if (state.getValue(NORTH)) {
                voxelshape = Shapes.or(voxelshape, NORTHFENCE);
            }

            if (state.getValue(SOUTH)) {
                voxelshape = Shapes.or(voxelshape, SOUTHFENCE);
            }

            if (state.getValue(EAST)) {
                voxelshape = Shapes.or(voxelshape, EASTFENCE);
            }

            if (state.getValue(WEST)) {
                voxelshape = Shapes.or(voxelshape, WESTFENCE);
            }

        } else {
            if (state.getValue(UP)) {
                voxelshape = Shapes.or(voxelshape, UPCONNECTOR);
            }

            if (state.getValue(DOWN)) {
                voxelshape = Shapes.or(voxelshape, DOWNCONNECTOR);
            }

            if (facing == Direction.NORTH) {
                voxelshape = NORTHSOUTH;

                if (state.getValue(NORTH)) {
                    voxelshape = Shapes.or(voxelshape, NORTHEXTENSION);
                }

                if (state.getValue(SOUTH)) {
                    voxelshape = Shapes.or(voxelshape, SOUTHEXTENSION);
                }

            } else if (facing == Direction.EAST) {
                voxelshape = EASTWEST;

                if (state.getValue(EAST)) {
                    voxelshape = Shapes.or(voxelshape, EASTEXTENSION);
                }

                if (state.getValue(WEST)) {
                    voxelshape = Shapes.or(voxelshape, WESTEXTENSION);
                }
            }

        }

        return voxelshape;
    }

    private static VoxelShape getCollisionForState(BlockState state) {
        VoxelShape voxelshape = Shapes.empty();
        Direction facing = state.getValue(FACING);

        if (facing == Direction.UP) {
            voxelshape = PILLAR;

            if (state.getValue(NORTH)) {
                voxelshape = Shapes.or(voxelshape, NORTHFENCECOLLISION);
            }

            if (state.getValue(SOUTH)) {
                voxelshape = Shapes.or(voxelshape, SOUTHFENCECOLLISION);
            }

            if (state.getValue(EAST)) {
                voxelshape = Shapes.or(voxelshape, EASTFENCECOLLISION);
            }

            if (state.getValue(WEST)) {
                voxelshape = Shapes.or(voxelshape, WESTFENCECOLLISION);
            }
        } else {
            return getShapeForState(state);
        }

        return voxelshape;
    }

    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return this.stateToShapeMap.get(state);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return this.stateToCollisionMap.get(state);
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockGetter iblockreader = context.getLevel();
        BlockPos blockpos = context.getClickedPos();
        FluidState fluidstate = context.getLevel().getFluidState(context.getClickedPos());
        Direction thisFacing = context.getClickedFace();
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
        BlockPos blockpos5 = blockpos.above();
        BlockPos blockpos6 = blockpos.below();
        BlockState blockstate1 = iblockreader.getBlockState(blockpos1);
        BlockState blockstate2 = iblockreader.getBlockState(blockpos2);
        BlockState blockstate3 = iblockreader.getBlockState(blockpos3);
        BlockState blockstate4 = iblockreader.getBlockState(blockpos4);
        BlockState blockstate5 = iblockreader.getBlockState(blockpos5);
        BlockState blockstate6 = iblockreader.getBlockState(blockpos6);

        return super.getStateForPlacement(context)
                .setValue(FACING, thisFacing)
                .setValue(NORTH, thisFacing != Direction.EAST && this.canConnect(blockstate1, thisFacing))
                .setValue(EAST, thisFacing != Direction.NORTH && this.canConnect(blockstate2, thisFacing))
                .setValue(WEST, thisFacing != Direction.NORTH && this.canConnect(blockstate4, thisFacing))
                .setValue(SOUTH, thisFacing != Direction.EAST && this.canConnect(blockstate3, thisFacing))
                .setValue(UP, thisFacing != Direction.UP && this.canConnect(blockstate5, thisFacing))
                .setValue(DOWN, thisFacing != Direction.UP && this.canConnect(blockstate6, thisFacing))
                .setValue(WATERLOGGED, Boolean.valueOf(fluidstate.getType() == Fluids.WATER));
    }

    public boolean canConnect(BlockState state, Direction facing) {
        Block block = state.getBlock();
        if (facing == Direction.UP) {
            return this.isFence(state);
        } else if (block instanceof PostBlock) {
            return state.getValue(FACING) != facing;
        }
            return this.isFence(state);
    }

    private boolean isFence(BlockState block) {
        return block.is(BlockTags.FENCES) || block.is(BlockTags.WALLS);
    }

    @Override
    public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor level, BlockPos pos, BlockPos facingPos) {
        if (state.getValue(WATERLOGGED)) {
            level.getLiquidTicks().scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }

            Direction orientation = state.getValue(FACING);
            switch (facing) {
                case NORTH:
                    return state.setValue(NORTH, orientation != Direction.EAST && canConnect(facingState, orientation));
                case SOUTH:
                    return state.setValue(SOUTH, orientation != Direction.EAST && canConnect(facingState, orientation));
                case EAST:
                    return state.setValue(EAST, orientation != Direction.NORTH && canConnect(facingState, orientation));
                case WEST:
                    return state.setValue(WEST, orientation != Direction.NORTH && canConnect(facingState, orientation));
                case UP:
                    return state.setValue(UP, orientation != Direction.UP && canConnect(facingState, orientation));
                case DOWN:
                    return state.setValue(DOWN, orientation != Direction.UP && canConnect(facingState, orientation));
            }
            return state;
    }

    public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
        if (worldIn.isClientSide) {
            ItemStack itemstack = player.getItemInHand(handIn);
            return itemstack.getItem() == Items.LEAD ? InteractionResult.SUCCESS : InteractionResult.PASS;
        } else {
            return LeadItem.bindPlayerMobs(player, worldIn, pos);
        }
    }

    @Override
    public boolean propagatesSkylightDown(BlockState state, BlockGetter reader, BlockPos pos) {
        return !state.getValue(WATERLOGGED);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, WATERLOGGED, NORTH, SOUTH, EAST, WEST, UP, DOWN);
    }

    public boolean isPathfindable(BlockState state, BlockGetter worldIn, BlockPos pos, PathComputationType type) {
        return false;
    }

    @Override
    public void setPlacedBy(Level worldIn, BlockPos blockPos, BlockState state, LivingEntity placer, ItemStack stack) {
        if (state.getValue(FACING) != Direction.UP) {
            worldIn.getCapability(PostCapabilityProvider.POST_CAP, null).orElse(new PostCapabilityProvider()).addPostPos(blockPos);
        }
    }

    @Override
    public void wasExploded(Level worldIn, BlockPos pos, Explosion explosionIn) {
        worldIn.getCapability(PostCapabilityProvider.POST_CAP, null).orElse(new PostCapabilityProvider()).removePostPos(pos);
    }

    @Override
    public void playerWillDestroy(Level worldIn, BlockPos pos, BlockState state, Player player) {
        worldIn.getCapability(PostCapabilityProvider.POST_CAP, null).orElse(new PostCapabilityProvider()).removePostPos(pos);
        super.playerWillDestroy(worldIn, pos, state, player);
    }

}
