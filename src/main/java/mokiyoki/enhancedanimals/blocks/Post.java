package mokiyoki.enhancedanimals.blocks;

import mokiyoki.enhancedanimals.capability.post.PostCapabilityProvider;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;

/**
 * Created by moki on 25/08/2018.
 */
public class Post extends Block {

    public static final DirectionProperty FACING = BlockStateProperties.FACING;

    protected static final VoxelShape PILLAR_AABB = Block.makeCuboidShape(6D, 0.0D, 6D, 10D, 16D, 10D);
    protected static final VoxelShape EASTWEST_AABB = Block.makeCuboidShape(0.0D, 6D, 6D, 16D, 10D, 10D);
    protected static final VoxelShape NORTHSOUTH_AABB = Block.makeCuboidShape(6D, 6D, 0.0D, 10D, 10D, 16D);


    public Post(Properties properties) {
        super(properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(FACING, EnumFacing.NORTH));
    }

    public VoxelShape getShape(IBlockState state, IBlockReader worldIn, BlockPos pos) {
        EnumFacing enumfacing = state.get(FACING);
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
    public IBlockState getStateForPlacement(BlockItemUseContext context) {
        return this.getDefaultState().with(FACING, context.getFace());
    }


    public IBlockState rotate(IBlockState state, Rotation rot) {
        return state.with(FACING, rot.rotate(state.get(FACING)));
    }

    public VoxelShape getCollisionShape(IBlockState state, IBlockReader worldIn, BlockPos pos) {
        return getShape(state,worldIn,pos);
    }

    protected void fillStateContainer(StateContainer.Builder<Block, IBlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

    public boolean allowsMovement(IBlockState state, IBlockReader worldIn, BlockPos pos, PathType type) {
        return false;
    }


    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos blockPos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        worldIn.getCapability(PostCapabilityProvider.POST_CAP, null).orElse(null).addPostPos(blockPos);
    }


}
