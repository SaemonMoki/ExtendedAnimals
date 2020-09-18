package mokiyoki.enhancedanimals.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CropsBlock;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.client.event.ColorHandlerEvent;

import java.util.Random;

public class GrowablePlant extends CropsBlock {
    private static final VoxelShape[] SHAPE_BY_AGE = new VoxelShape[]{Block.makeCuboidShape(5.0D, 0.0D, 5.0D, 11.0D, 2.0D, 11.0D), Block.makeCuboidShape(5.0D, 0.0D, 5.0D, 11.0D, 4.0D, 11.0D), Block.makeCuboidShape(5.0D, 0.0D, 5.0D, 11.0D, 5.0D, 11.0D), Block.makeCuboidShape(5.0D, 0.0D, 5.0D, 11.0D, 6.0D, 11.0D), Block.makeCuboidShape(5.0D, 0.0D, 5.0D, 11.0D, 7.0D, 11.0D), Block.makeCuboidShape(5.0D, 0.0D, 5.0D, 11.0D, 8.0D, 11.0D), Block.makeCuboidShape(5.0D, 0.0D, 5.0D, 11.0D, 9.0D, 11.0D), Block.makeCuboidShape(5.0D, 0.0D, 5.0D, 11.0D, 10.0D, 11.0D)};
    private IItemProvider plantType;
    private boolean onlyGrowsOnGrass;
    public GrowablePlant(Block.Properties properties, net.minecraft.util.IItemProvider plant, boolean growsOnDirt) {
        super(properties);
        setPlantType(plant);
        setOnlyGrowsOnGrass(growsOnDirt);
    }

    @Override
    public void tick(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand) {
        Block groundType = worldIn.getBlockState(pos.down()).getBlock();
        if (!getOnlyGrowsOnGrass() || groundType == Blocks.GRASS_BLOCK || groundType == Blocks.FARMLAND) {
            if (!worldIn.isRemote && this.isMaxAge(state)) {
                worldIn.setBlockState(pos, getBlockFromItem(getSeedsItem().asItem()).getDefaultState());
            }
            super.tick(state, worldIn, pos, rand);
        }
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        Vec3d vec3d = state.getOffset(worldIn, pos);
        return SHAPE_BY_AGE[state.get(this.getAgeProperty())].withOffset(vec3d.x, vec3d.y, vec3d.z);
    }

    /**
     * Get the OffsetType for this Block. Determines if the model is rendered slightly offset.
     */
    public Block.OffsetType getOffsetType() {
        return Block.OffsetType.XZ;
    }

    @Override
    protected IItemProvider getSeedsItem() {
        return this.plantType;
    }

    @Override
    protected int getBonemealAgeIncrease(World worldIn) {
        return 7;
    }

    protected void setPlantType(net.minecraft.util.IItemProvider plant) {
        this.plantType = plant;
    }

    private void setOnlyGrowsOnGrass(boolean growsOnDirt) {
        this.onlyGrowsOnGrass = !growsOnDirt;
    }

    private boolean getOnlyGrowsOnGrass() {
        return this.onlyGrowsOnGrass;
    }

    @Override
    protected boolean isValidGround(BlockState state, IBlockReader worldIn, BlockPos pos) {
        Block block = state.getBlock();
        return block == Blocks.GRASS_BLOCK || block == Blocks.DIRT || block == Blocks.COARSE_DIRT || block == Blocks.PODZOL || block == Blocks.FARMLAND;
    }
}
