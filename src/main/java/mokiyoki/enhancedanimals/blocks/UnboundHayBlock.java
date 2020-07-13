package mokiyoki.enhancedanimals.blocks;

import mokiyoki.enhancedanimals.capability.hay.HayCapabilityProvider;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FallingBlock;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.fluid.IFluidState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class UnboundHayBlock extends FallingBlock implements IWaterLoggable {

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
                world.getWorld().getCapability(HayCapabilityProvider.HAY_CAP, null).orElse(new HayCapabilityProvider()).removeHayPos(pos);
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
                worldIn.getWorld().getCapability(HayCapabilityProvider.HAY_CAP, null).orElse(new HayCapabilityProvider()).removeHayPos(pos);
                worldIn.removeBlock(pos, false);
            }
    }

    @Nullable
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        IFluidState ifluidstate = context.getWorld().getFluidState(context.getPos());
//        if (Boolean.valueOf(ifluidstate.getFluid() == Fluids.WATER)){
//            this.(context.getPos(), true);
//        }
        return this.getDefaultState().with(WATERLOGGED, Boolean.valueOf(ifluidstate.getFluid() == Fluids.WATER));
    }

    @Override
    public BlockState updatePostPlacement(BlockState state, Direction facing, BlockState facingState, IWorld worldIn, BlockPos pos, BlockPos facingPos) {
        if (state.get(WATERLOGGED)) {
            worldIn.getWorld().getCapability(HayCapabilityProvider.HAY_CAP, null).orElse(new HayCapabilityProvider()).removeHayPos(pos);
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

    //TODO add rotation? should adopt RotatedPillarBlock state from hayblock

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
        entityIn.onLivingFall(fallDistance, 0.2F + (bites*0.1F));
    }

    @Override
    public void onPlayerDestroy(IWorld worldIn, BlockPos pos, BlockState state) {
        worldIn.getWorld().getCapability(HayCapabilityProvider.HAY_CAP, null).orElse(new HayCapabilityProvider()).removeHayPos(pos);
        super.onPlayerDestroy(worldIn, pos, state);
    }

    @Override
    public void onExplosionDestroy(World worldIn, BlockPos pos, Explosion explosionIn) {
        worldIn.getWorld().getCapability(HayCapabilityProvider.HAY_CAP, null).orElse(new HayCapabilityProvider()).removeHayPos(pos);
        super.onExplosionDestroy(worldIn, pos, explosionIn);
    }

    @Override
    public void onBlockHarvested(World worldIn, BlockPos pos, BlockState state, PlayerEntity player) {
        worldIn.getWorld().getCapability(HayCapabilityProvider.HAY_CAP, null).orElse(new HayCapabilityProvider()).removeHayPos(pos);
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
        worldIn.getWorld().getCapability(HayCapabilityProvider.HAY_CAP, null).orElse(new HayCapabilityProvider()).addHayPos(pos);
        super.onBlockAdded(state, worldIn, pos, oldState, isMoving);
    }

    @Override
    public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        worldIn.getWorld().getCapability(HayCapabilityProvider.HAY_CAP, null).orElse(new HayCapabilityProvider()).removeHayPos(pos);
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
