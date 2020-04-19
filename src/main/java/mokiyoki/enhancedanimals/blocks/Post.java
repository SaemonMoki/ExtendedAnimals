package mokiyoki.enhancedanimals.blocks;

import mokiyoki.enhancedanimals.capability.post.PostCapabilityProvider;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.IWaterLoggable;
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
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import javax.annotation.Nullable;

/**
 * Created by moki on 25/08/2018.
 */
public class Post extends Block implements IWaterLoggable {

    public static final DirectionProperty FACING = BlockStateProperties.FACING;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    private static final VoxelShape PILLAR_AABB = Block.makeCuboidShape(6D, 0.0D, 6D, 10D, 16D, 10D);
    private static final VoxelShape EASTWEST_AABB = Block.makeCuboidShape(0.0D, 6D, 6D, 16D, 10D, 10D);
    private static final VoxelShape NORTHSOUTH_AABB = Block.makeCuboidShape(6D, 6D, 0.0D, 10D, 10D, 16D);

    private static final VoxelShape FENCE_EAST = Block.makeCuboidShape(6D, 0.0D, 6D, 10D, 24D, 10D);
    private static final VoxelShape FENCE_WEST = Block.makeCuboidShape(6D, 0.0D, 6D, 10D, 24D, 10D);
    private static final VoxelShape FENCE_NORTH = Block.makeCuboidShape(6D, 0.0D, 6D, 10D, 24D, 10D);
    private static final VoxelShape FENCE_SOUTH = Block.makeCuboidShape(6D, 0.0D, 6D, 10D, 24D, 10D);

    private static final VoxelShape BELOW = Block.makeCuboidShape(6D, 0.0D, 6D, 10D, 16D, 10D);
    private static final VoxelShape ABOVE = Block.makeCuboidShape(6D, 0.0D, 6D, 10D, 16D, 10D);
    private static final VoxelShape EAST = Block.makeCuboidShape(0.0D, 6D, 6D, 16D, 10D, 10D);
    private static final VoxelShape WEST = Block.makeCuboidShape(0.0D, 6D, 6D, 16D, 10D, 10D);
    private static final VoxelShape NORTH = Block.makeCuboidShape(6D, 6D, 0.0D, 10D, 10D, 16D);
    private static final VoxelShape SOUTH = Block.makeCuboidShape(6D, 6D, 0.0D, 10D, 10D, 16D);

        //TODO should add voxel shapes together to make final shape
//    protected static final VoxelShape SHAPE = VoxelShapes.combine();


    public Post(Properties properties) {
        super(properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(FACING, Direction.NORTH).with(WATERLOGGED, Boolean.valueOf(false)));
    }

    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        Direction enumfacing = state.get(FACING);
        switch(enumfacing) {
            case UP:
                default:
                return PILLAR_AABB;
            case DOWN:
                return PILLAR_AABB;
            case NORTH:
                return NORTHSOUTH_AABB;
            case SOUTH:
                return NORTHSOUTH_AABB;
            case EAST:
                return EASTWEST_AABB;
            case WEST:
                return EASTWEST_AABB;
        }
    }

    @Nullable
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        IFluidState ifluidstate = context.getWorld().getFluidState(context.getPos());
        return this.getDefaultState().with(FACING, context.getFace()).with(WATERLOGGED, Boolean.valueOf(ifluidstate.getFluid() == Fluids.WATER));
    }

    @Override
    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        if (stateIn.get(WATERLOGGED)) {
            worldIn.getPendingFluidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickRate(worldIn));
        }

        return super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
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
    public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return getShape(state, worldIn, pos, context);
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
        builder.add(FACING, WATERLOGGED);
    }

    public boolean allowsMovement(BlockState state, IBlockReader worldIn, BlockPos pos, PathType type) {
        return false;
    }

    //TODO when in pillar state check if it can connect to fences
    //TODO needs to connect to other posts dynamically to make perches

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos blockPos, BlockState state, LivingEntity placer, ItemStack stack) {
        worldIn.getCapability(PostCapabilityProvider.POST_CAP, null).orElse(new PostCapabilityProvider()).addPostPos(blockPos);
    }


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
