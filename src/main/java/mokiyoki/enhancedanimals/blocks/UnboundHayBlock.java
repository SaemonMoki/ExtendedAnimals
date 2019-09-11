package mokiyoki.enhancedanimals.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FallingBlock;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.fluid.Fluids;
import net.minecraft.fluid.IFluidState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Direction;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class UnboundHayBlock extends FallingBlock  implements IWaterLoggable {

    public static final DirectionProperty FACING = BlockStateProperties.FACING;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final IntegerProperty BITES = BlockStateProperties.LEVEL_0_8;

//    protected static final VoxelShape HAY_BLACK = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 5D, 16D, 5D);
//    protected static final VoxelShape HAY_GREY = Block.makeCuboidShape(4.5D, 0.0D, 0.0D, 6D, 10D, 5D);
//    protected static final VoxelShape HAY_WHITE = Block.makeCuboidShape(11.0D, 0.0D, 0.0D, 5D, 10D, 5D);
//    protected static final VoxelShape HAY_YELLOW = Block.makeCuboidShape(0.0D, 0.0D, 4.5D, 5D, 16D, 6D);
//    protected static final VoxelShape HAY_FUSHIA = Block.makeCuboidShape(4.5D, 0.0D, 4.5D, 6D, 10D, 6D);
//    protected static final VoxelShape HAY_CYAN = Block.makeCuboidShape(11.0D, 0.0D, 4.5D, 5D, 10D, 6D);
//    protected static final VoxelShape HAY_GREEN = Block.makeCuboidShape(0.0D, 0.0D, 11.0D, 5D, 16D, 5D);
//    protected static final VoxelShape HAY_RED = Block.makeCuboidShape(4.5D, 0.0D, 11.0D, 6D, 10D, 5D);
//    protected static final VoxelShape HAY_BLUE = Block.makeCuboidShape(11.0D, 0.0D, 11.0D, 5D, 10D, 5D);

    public UnboundHayBlock(Properties properties) {
        super(properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(BITES, Integer.valueOf(0)).with(WATERLOGGED, Boolean.valueOf(false)));
    }

//    public void fillWithRain(World worldIn, BlockPos pos) {
//        if (worldIn.rand.nextInt(20) == 1) {
//            float f = worldIn.getBiome(pos).getTemperature(pos);
//            if (!(f < 0.15F)) {
//                BlockState blockstate = worldIn.getBlockState(pos);
//                if (blockstate.get(LEVEL) < 3) {
//                    worldIn.setBlockState(pos, blockstate.cycle(LEVEL), 2);
//                }
//
//            }
//        }
//    }

    @Nullable
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        IFluidState ifluidstate = context.getWorld().getFluidState(context.getPos());
//        if (Boolean.valueOf(ifluidstate.getFluid() == Fluids.WATER)){
//            this.(context.getPos(), true);
//        }
        return this.getDefaultState().with(WATERLOGGED, Boolean.valueOf(ifluidstate.getFluid() == Fluids.WATER));
    }

    @Override
    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        if (stateIn.get(WATERLOGGED)) {
            worldIn.destroyBlock(currentPos, true);
            worldIn.getPendingFluidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickRate(worldIn));
        }

        return super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }

    public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
        if (state.get(WATERLOGGED)) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public IFluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStillFluidState(false) : super.getFluidState(state);
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rot) {
        return state.with(FACING, rot.rotate(state.get(FACING)));
    }

    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(BITES, WATERLOGGED);
    }

    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    //TODO make usable by animals
    //TODO make react to getting wet
    //TODO add rotation? should adopt RotatedPillarBlock state from hayblock
    //TODO replace hayblock with this block when shears, sword or axe is used on hayblock
    //TODO drop wheat items left in block when harvested

    public boolean causesSuffocation(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return false;
    }

    public void onEntityCollision(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {
        entityIn.setMotionMultiplier(state, new Vec3d(0.10D, (double)0.10F, 0.10D));
    }

    /**
     * Block's chance to react to a living entity falling on it.
     */
    public void onFallenUpon(World worldIn, BlockPos pos, Entity entityIn, float fallDistance) {
        BlockState state = worldIn.getBlockState(pos);
        float bites = state.get(BITES);
        entityIn.fall(fallDistance, 0.2F + (bites*0.1F));
    }

}
