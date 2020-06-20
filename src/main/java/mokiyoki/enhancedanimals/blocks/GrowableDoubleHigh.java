package mokiyoki.enhancedanimals.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CropsBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;
import java.util.Random;

public class GrowableDoubleHigh extends CropsBlock {
    public static final EnumProperty<DoubleBlockHalf> HALF = BlockStateProperties.DOUBLE_BLOCK_HALF;
//    private static final VoxelShape[] SHAPE_BY_AGE = new VoxelShape[]{Block.makeCuboidShape(5.0D, 0.0D, 5.0D, 11.0D, 2.0D, 11.0D), Block.makeCuboidShape(5.0D, 0.0D, 5.0D, 11.0D, 4.0D, 11.0D), Block.makeCuboidShape(5.0D, 0.0D, 5.0D, 11.0D, 5.0D, 11.0D), Block.makeCuboidShape(5.0D, 0.0D, 5.0D, 11.0D, 6.0D, 11.0D), Block.makeCuboidShape(5.0D, 0.0D, 5.0D, 11.0D, 7.0D, 11.0D), Block.makeCuboidShape(5.0D, 0.0D, 5.0D, 11.0D, 8.0D, 11.0D), Block.makeCuboidShape(5.0D, 0.0D, 5.0D, 11.0D, 9.0D, 11.0D), Block.makeCuboidShape(5.0D, 0.0D, 5.0D, 11.0D, 10.0D, 11.0D)};
    IItemProvider plantType;
    public GrowableDoubleHigh(Properties properties, IItemProvider plant) {
        super(properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(HALF, DoubleBlockHalf.LOWER));
        setPlantType(plant);
    }

    @Nullable
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        BlockPos blockpos = context.getPos();
        return blockpos.getY() < context.getWorld().getDimension().getHeight() - 1 && context.getWorld().getBlockState(blockpos.up()).isReplaceable(context) ? super.getStateForPlacement(context) : null;
    }


    /**
     * Called by ItemBlocks after a block is set in the world, to allow post-place logic
     */
    public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
        worldIn.setBlockState(pos.up(), this.getDefaultState().with(HALF, DoubleBlockHalf.UPPER).with(AGE, 0));
    }

    public void placeAt(IWorld worldIn, BlockPos pos, int flags) {
        worldIn.setBlockState(pos, this.getDefaultState().with(HALF, DoubleBlockHalf.LOWER), flags);
        worldIn.setBlockState(pos.up(), this.getDefaultState().with(HALF, DoubleBlockHalf.UPPER), flags);
    }

    /**
     * Spawns the block's drops in the world. By the time this is called the Block has possibly been set to air via
     * Block.removedByPlayer
     */
    public void harvestBlock(World worldIn, PlayerEntity player, BlockPos pos, BlockState state, @Nullable TileEntity te, ItemStack stack) {
        super.harvestBlock(worldIn, player, pos, Blocks.AIR.getDefaultState(), te, stack);
    }

    /**
     * Called before the Block is set to air in the world. Called regardless of if the player's tool can actually collect
     * this block
     */

    public void onBlockHarvested(World worldIn, BlockPos pos, BlockState state, PlayerEntity player) {
        DoubleBlockHalf doubleblockhalf = state.get(HALF);
        BlockPos blockpos = doubleblockhalf == DoubleBlockHalf.LOWER ? pos.up() : pos.down();
        BlockState blockstate = worldIn.getBlockState(blockpos);
        if (blockstate.getBlock() == this && blockstate.get(HALF) != doubleblockhalf) {
            worldIn.setBlockState(blockpos, Blocks.AIR.getDefaultState(), 35);
            worldIn.playEvent(player, 2001, blockpos, Block.getStateId(blockstate));
            if (!worldIn.isRemote && !player.isCreative()) {
                spawnDrops(state, worldIn, pos, (TileEntity)null, player, player.getHeldItemMainhand());
                spawnDrops(blockstate, worldIn, blockpos, (TileEntity)null, player, player.getHeldItemMainhand());
            }
        }

        super.onBlockHarvested(worldIn, pos, state, player);
    }

    @Override
    public void grow(World worldIn, BlockPos pos, BlockState state) {
//        int i = this.getAge(state) + this.getBonemealAgeIncrease(worldIn);
//        int j = this.getMaxAge();
//
//        if (i > 5) {
//
//        }
//        if (i > j) {
//            i = j;
//        }
//
//        worldIn.setBlockState(pos, this.withAge(i), 2);
//        worldIn.setBlockState(pos.up(), this.withAge(i), 2);
    }

    @Override
    public void tick(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand) {
        if (state.get(HALF) != DoubleBlockHalf.UPPER) {
        int bottomAge = getAge(worldIn.getBlockState(pos));
            if (bottomAge < 5) {
                super.tick(state, worldIn, pos, rand);
            } else {
                if (worldIn.getBlockState(pos.up()).getBlock() != this) {
                    worldIn.setBlockState(pos.up(), this.getDefaultState().with(AGE, 0).with(HALF, DoubleBlockHalf.UPPER));
                }
                int topAge = getAge(worldIn.getBlockState(pos.up()));
                if (topAge < 5) {
                    super.tick(worldIn.getBlockState(pos.up()), worldIn, pos.up(), rand);
                    int ageUpdate = getAge(worldIn.getBlockState(pos.up()));
                    if (topAge != ageUpdate) {
                        this.setHalfToUpper(worldIn, pos, ageUpdate);
                    }
                } else if (topAge >= 7 && bottomAge >= 7) {
                    worldIn.setBlockState(pos.up(), getBlockFromItem(getPlantType().asItem()).getDefaultState().with(HALF, DoubleBlockHalf.UPPER));
                    worldIn.setBlockState(pos, getBlockFromItem(getPlantType().asItem()).getDefaultState().with(HALF, DoubleBlockHalf.LOWER));
                } else{
                    super.tick(worldIn.getBlockState(pos.up()), worldIn, pos.up(), rand);
                    int ageUpdate = getAge(worldIn.getBlockState(pos.up()));
                    if (topAge != ageUpdate) {
                        this.setHalfToUpper(worldIn, pos, ageUpdate);
                    }
                    super.tick(state, worldIn, pos, rand);
                }
            }
        }
    }

    public void setHalfToUpper(World world, BlockPos pos, int age) {
            world.setBlockState(pos.up(), this.withAge(age).with(HALF, DoubleBlockHalf.UPPER));
    }

//    @Override
//    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
//        Vec3d vec3d = state.getOffset(worldIn, pos);
//        return SHAPE_BY_AGE[state.get(this.getAgeProperty())].withOffset(vec3d.x, vec3d.y, vec3d.z);
//    }

    /**
     * Get the OffsetType for this Block. Determines if the model is rendered slightly offset.
     */
    public OffsetType getOffsetType() {
        return OffsetType.XZ;
    }

    @Override
    protected IItemProvider getSeedsItem() { return this.plantType; }

    protected IItemProvider getPlantType() { return this.plantType; }

    protected void setPlantType(IItemProvider flower) {
        this.plantType = flower;
    }

    @Override
    protected boolean isValidGround(BlockState state, IBlockReader worldIn, BlockPos pos) {
        Block block = state.getBlock();
        return block == Blocks.GRASS_BLOCK || block == Blocks.DIRT || block == Blocks.COARSE_DIRT || block == Blocks.PODZOL || block == Blocks.FARMLAND;
    }

    @Override
    public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
        if (state.get(HALF) != DoubleBlockHalf.UPPER) {
            return (super.isValidPosition(state, worldIn, pos) || worldIn.getBlockState(pos.up()).getBlock() == this);
        } else {
            BlockState blockstate = worldIn.getBlockState(pos.down());
            if (state.getBlock() != this) {
                return super.isValidPosition(state, worldIn, pos); //Forge: This function is called during world gen and placement, before this block is set, so if we are not 'here' then assume it's the pre-check.
            }
            return blockstate.getBlock() == this && blockstate.get(HALF) == DoubleBlockHalf.LOWER;
        }
    }

//    @OnlyIn(Dist.CLIENT)
//    public long getPositionRandom(BlockState state, BlockPos pos) {
//        return MathHelper.getCoordinateRandom(pos.getX(), pos.down(state.get(HALF) == DoubleBlockHalf.LOWER ? 0 : 1).getY(), pos.getZ());
//    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        super.fillStateContainer(builder);
        builder.add(HALF);
    }

}
