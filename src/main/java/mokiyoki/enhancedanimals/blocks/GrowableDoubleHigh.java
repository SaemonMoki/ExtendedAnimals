package mokiyoki.enhancedanimals.blocks;

import mokiyoki.enhancedanimals.init.ModBlocks;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.ItemLike;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;

import javax.annotation.Nullable;
import java.util.Random;

import net.minecraft.world.level.block.state.BlockBehaviour.OffsetType;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class GrowableDoubleHigh extends CropBlock {
    public static final EnumProperty<DoubleBlockHalf> HALF = BlockStateProperties.DOUBLE_BLOCK_HALF;
//    private static final VoxelShape[] SHAPE_BY_AGE = new VoxelShape[]{Block.makeCuboidShape(5.0D, 0.0D, 5.0D, 11.0D, 2.0D, 11.0D), Block.makeCuboidShape(5.0D, 0.0D, 5.0D, 11.0D, 4.0D, 11.0D), Block.makeCuboidShape(5.0D, 0.0D, 5.0D, 11.0D, 5.0D, 11.0D), Block.makeCuboidShape(5.0D, 0.0D, 5.0D, 11.0D, 6.0D, 11.0D), Block.makeCuboidShape(5.0D, 0.0D, 5.0D, 11.0D, 7.0D, 11.0D), Block.makeCuboidShape(5.0D, 0.0D, 5.0D, 11.0D, 8.0D, 11.0D), Block.makeCuboidShape(5.0D, 0.0D, 5.0D, 11.0D, 9.0D, 11.0D), Block.makeCuboidShape(5.0D, 0.0D, 5.0D, 11.0D, 10.0D, 11.0D)};
    private ItemLike plantType;
    private boolean onlyGrowsOnGrass;
    public GrowableDoubleHigh(Properties properties, ItemLike plant, boolean growsOnDirt) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(HALF, DoubleBlockHalf.LOWER));
        setPlantType(plant);
        setOnlyGrowsOnGrass(growsOnDirt);
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockPos blockpos = context.getClickedPos();
        return blockpos.getY() < context.getLevel().dimensionType().logicalHeight() - 1 && context.getLevel().getBlockState(blockpos.above()).canBeReplaced(context) ? super.getStateForPlacement(context) : null;
    }


    /**
     * Called by ItemBlocks after a block is set in the world, to allow post-place logic
     */
    public void setPlacedBy(Level worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
        worldIn.setBlockAndUpdate(pos.above(), this.defaultBlockState().setValue(HALF, DoubleBlockHalf.UPPER).setValue(AGE, 0));
    }

    public void placeAt(LevelAccessor worldIn, BlockPos pos, int flags) {
        worldIn.setBlock(pos, this.defaultBlockState().setValue(HALF, DoubleBlockHalf.LOWER), flags);
        worldIn.setBlock(pos.above(), this.defaultBlockState().setValue(HALF, DoubleBlockHalf.UPPER), flags);
    }

    /**
     * Spawns the block's drops in the world. By the time this is called the Block has possibly been set to air via
     * Block.removedByPlayer
     */
    public void playerDestroy(Level worldIn, Player player, BlockPos pos, BlockState state, @Nullable BlockEntity te, ItemStack stack) {
        super.playerDestroy(worldIn, player, pos, Blocks.AIR.defaultBlockState(), te, stack);
    }

    /**
     * Called before the Block is set to air in the world. Called regardless of if the player's tool can actually collect
     * this block
     */

    public void playerWillDestroy(Level worldIn, BlockPos pos, BlockState state, Player player) {
        DoubleBlockHalf doubleblockhalf = state.getValue(HALF);
        BlockPos blockpos = doubleblockhalf == DoubleBlockHalf.LOWER ? pos.above() : pos.below();
        BlockState blockstate = worldIn.getBlockState(blockpos);
        if (blockstate.getBlock() == this && blockstate.getValue(HALF) != doubleblockhalf) {
            worldIn.setBlock(blockpos, Blocks.AIR.defaultBlockState(), 35);
            worldIn.levelEvent(player, 2001, blockpos, Block.getId(blockstate));
            if (!worldIn.isClientSide && !player.isCreative()) {
                dropResources(state, worldIn, pos, (BlockEntity)null, player, player.getMainHandItem());
                dropResources(blockstate, worldIn, blockpos, (BlockEntity)null, player, player.getMainHandItem());
            }
        }

        super.playerWillDestroy(worldIn, pos, state, player);
    }

    @Override
    public void growCrops(Level worldIn, BlockPos pos, BlockState state) {
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
    public void tick(BlockState state, ServerLevel worldIn, BlockPos pos, RandomSource randomSource) {
        if (state.getValue(HALF) != DoubleBlockHalf.UPPER) {
            Block groundType = worldIn.getBlockState(pos.below()).getBlock();
            if (!getOnlyGrowsOnGrass() || groundType == Blocks.GRASS_BLOCK || groundType == Blocks.FARMLAND) {
                int bottomAge = getAge(worldIn.getBlockState(pos));
                if (bottomAge < 5) {
                    super.tick(state, worldIn, pos, randomSource);
                } else {
                    if (worldIn.getBlockState(pos.above()).getBlock() != this) {
                        worldIn.setBlockAndUpdate(pos.above(), this.defaultBlockState().setValue(AGE, 0).setValue(HALF, DoubleBlockHalf.UPPER));
                    }
                    int topAge = getAge(worldIn.getBlockState(pos.above()));
                    if (topAge < 5) {
                        super.tick(worldIn.getBlockState(pos.above()), worldIn, pos.above(), randomSource);
//                        int ageUpdate = getAge(worldIn.getBlockState(pos.up()));
//                        if (topAge != ageUpdate) {
//                            this.setHalfToUpper(worldIn, pos, ageUpdate);
//                        }
                    } else if (topAge >= 7 && bottomAge >= 7) {
                        worldIn.setBlockAndUpdate(pos.above(), byItem(getPlantType().asItem()).defaultBlockState().setValue(HALF, DoubleBlockHalf.UPPER));
                        worldIn.setBlockAndUpdate(pos, byItem(getPlantType().asItem()).defaultBlockState().setValue(HALF, DoubleBlockHalf.LOWER));
                    } else {
                        super.tick(worldIn.getBlockState(pos.above()), worldIn, pos.above(), randomSource);
//                        int ageUpdate = getAge(worldIn.getBlockState(pos.up()));
//                        if (topAge != ageUpdate) {
//                            this.setHalfToUpper(worldIn, pos, ageUpdate);
//                        }
                        super.tick(state, worldIn, pos, randomSource);
                    }
                }
            }
        } else {
            int age = state.getValue(AGE);
            if (age != 0) {
                BlockState bottomState = worldIn.getBlockState(pos.below());
                Block bottomBlock = bottomState.getBlock();
                if (bottomBlock instanceof GrowableDoubleHigh && bottomState.getValue(AGE) < 7) {
                    int bottomAge = bottomState.getValue(AGE);
                    age = bottomAge + age;
                    worldIn.setBlockAndUpdate(pos.below(), this.defaultBlockState().setValue(HALF, DoubleBlockHalf.LOWER).setValue(AGE, age > 7 ? 7 : age));
                    age = age - 7;
                    worldIn.setBlockAndUpdate(pos, this.defaultBlockState().setValue(HALF, DoubleBlockHalf.UPPER).setValue(AGE, age < 0 ? 0 : age));
                }
            }
        }
    }

    public void setHalfToUpper(Level world, BlockPos pos, int age) {
            world.setBlockAndUpdate(pos.above(), this.getStateForAge(age).setValue(HALF, DoubleBlockHalf.UPPER));
    }

//    @Override
//    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
//        Vector3d vec3d = state.getOffset(worldIn, pos);
//        return SHAPE_BY_AGE[state.get(this.getAgeProperty())].withOffset(vec3d.x, vec3d.y, vec3d.z);
//    }

    /**
     * Get the OffsetType for this Block. Determines if the model is rendered slightly offset.
     */
    public OffsetType getOffsetType() {
        return OffsetType.XZ;
    }

    @Override
    public ItemLike getBaseSeedId() { return this.plantType; }

    protected ItemLike getPlantType() { return this.plantType; }

    protected void setPlantType(ItemLike plant) {
        this.plantType = plant;
    }

    private void setOnlyGrowsOnGrass(boolean growsOnDirt) {
        this.onlyGrowsOnGrass = !growsOnDirt;
    }

    private boolean getOnlyGrowsOnGrass() {
        return this.onlyGrowsOnGrass;
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter worldIn, BlockPos pos) {
        Block block = state.getBlock();
        return block == Blocks.GRASS_BLOCK || block == Blocks.DIRT || block == Blocks.COARSE_DIRT || block == Blocks.PODZOL || block == Blocks.FARMLAND || block == ModBlocks.SPARSEGRASS_BLOCK.get();
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader worldIn, BlockPos pos) {
        if (state.getValue(HALF) != DoubleBlockHalf.UPPER) {
            return (super.canSurvive(state, worldIn, pos) || worldIn.getBlockState(pos.above()).getBlock() == this);
        } else {
            BlockState blockstate = worldIn.getBlockState(pos.below());
            if (state.getBlock() != this) {
                return super.canSurvive(state, worldIn, pos); //Forge: This function is called during world gen and placement, before this block is set, so if we are not 'here' then assume it's the pre-check.
            }
            return blockstate.getBlock() == this && blockstate.getValue(HALF) == DoubleBlockHalf.LOWER;
        }
    }

//    @OnlyIn(Dist.CLIENT)
//    public long getPositionRandom(BlockState state, BlockPos pos) {
//        return MathHelper.getCoordinateRandom(pos.getX(), pos.down(state.get(HALF) == DoubleBlockHalf.LOWER ? 0 : 1).getY(), pos.getZ());
//    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(HALF);
    }

}
