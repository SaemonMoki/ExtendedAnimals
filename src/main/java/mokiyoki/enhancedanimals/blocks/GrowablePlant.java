package mokiyoki.enhancedanimals.blocks;

import mokiyoki.enhancedanimals.init.ModBlocks;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.ItemLike;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;

import java.util.Random;

public class GrowablePlant extends CropBlock {
    private static final VoxelShape[] SHAPE_BY_AGE = new VoxelShape[]{Block.box(5.0D, 0.0D, 5.0D, 11.0D, 2.0D, 11.0D), Block.box(5.0D, 0.0D, 5.0D, 11.0D, 4.0D, 11.0D), Block.box(5.0D, 0.0D, 5.0D, 11.0D, 5.0D, 11.0D), Block.box(5.0D, 0.0D, 5.0D, 11.0D, 6.0D, 11.0D), Block.box(5.0D, 0.0D, 5.0D, 11.0D, 7.0D, 11.0D), Block.box(5.0D, 0.0D, 5.0D, 11.0D, 8.0D, 11.0D), Block.box(5.0D, 0.0D, 5.0D, 11.0D, 9.0D, 11.0D), Block.box(5.0D, 0.0D, 5.0D, 11.0D, 10.0D, 11.0D)};
    private ItemLike plantType;
    private boolean onlyGrowsOnGrass;

    public GrowablePlant(Block.Properties properties, net.minecraft.world.level.ItemLike plant, boolean growsOnDirt) {
        super(properties);
        setPlantType(plant);
        setOnlyGrowsOnGrass(growsOnDirt);
    }

    @Override
    public void tick(BlockState state, ServerLevel worldIn, BlockPos pos, Random rand) {
        Block groundType = worldIn.getBlockState(pos.below()).getBlock();
        if (!getOnlyGrowsOnGrass() || groundType == Blocks.GRASS_BLOCK || groundType == Blocks.FARMLAND) {
            if (!worldIn.isClientSide && this.isMaxAge(state)) {
                worldIn.setBlockAndUpdate(pos, byItem(getBaseSeedId().asItem()).defaultBlockState());
            }
            super.tick(state, worldIn, pos, rand);
        }
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        Vec3 vec3d = state.getOffset(worldIn, pos);
        return SHAPE_BY_AGE[state.getValue(this.getAgeProperty())].move(vec3d.x, vec3d.y, vec3d.z);
    }

    /**
     * Get the OffsetType for this Block. Determines if the model is rendered slightly offset.
     */
    public Block.OffsetType getOffsetType() {
        return Block.OffsetType.XZ;
    }

    @Override
    public ItemLike getBaseSeedId() {
        return this.plantType;
    }

    @Override
    protected int getBonemealAgeIncrease(Level worldIn) {
        return 7;
    }

    protected void setPlantType(net.minecraft.world.level.ItemLike plant) {
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
        return block == Blocks.GRASS_BLOCK || block == Blocks.DIRT || block == Blocks.COARSE_DIRT || block == Blocks.PODZOL || block == Blocks.FARMLAND || block == ModBlocks.SPARSEGRASS_BLOCK;
    }
}
