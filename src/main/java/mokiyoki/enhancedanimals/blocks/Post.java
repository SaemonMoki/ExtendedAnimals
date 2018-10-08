package mokiyoki.enhancedanimals.blocks;

import mokiyoki.enhancedanimals.capability.post.PostCapability;
import mokiyoki.enhancedanimals.capability.post.PostCapabilityProvider;
import mokiyoki.enhancedanimals.util.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.block.material.Material;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Created by moki on 25/08/2018.
 */
public class Post extends Block {

    public static final net.minecraft.block.properties.PropertyDirection FACING = PropertyDirection.create("facing");
    public static final AxisAlignedBB [] BOUNDING_BOXES = new AxisAlignedBB[] {new AxisAlignedBB(0.375D, 0.0D, 0.375D, 0.625D, 1D, 0.625D), new AxisAlignedBB(0.0D, 0.375D, 0.375D, 1D, 0.625D, 0.625D), new AxisAlignedBB(0.375D, 0.375D, 0.0D, 0.625D, 0.625D, 1D)};
    public static final AxisAlignedBB PILLAR_AABB = new AxisAlignedBB(0.375D, 0.0D, 0.375D, 0.625D, 1D, 0.625D);
    public static final AxisAlignedBB EASTWEST_AABB = new AxisAlignedBB(0.0D, 0.375D, 0.375D, 1D, 0.625D, 0.625D);
    public static final AxisAlignedBB NORTHSOUTH_AABB = new AxisAlignedBB(0.375D, 0.375D, 0.0D, 0.625D, 0.625D, 1D);

    public Post(Material material, String unlocalizedName, String registryName) {
        this(material, SoundType.WOOD, unlocalizedName, registryName);
    }

    public Post(Material material, SoundType sound, String unlocalizedName, String registryName) {
        super(material);
        setUnlocalizedName(Reference.MODID + "." + unlocalizedName);
        setRegistryName(registryName);
        setCreativeTab(CreativeTabs.DECORATIONS);
        setSoundType(sound);
        setHardness(2.0F);
        setResistance(15.0F);
        setHarvestLevel("axe", 0);
        setLightOpacity(0);
        setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
    }

//    public Post(String name, Material material) {
//        super(name, material);
//        setSoundType(SoundType.WOOD);
//        setHardness(2.0F);
//        setResistance(15.0F);
//        setHarvestLevel("axe", 0);
//        setLightOpacity(0);
////        setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
//    }

    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        return this.getDefaultState().withProperty(FACING, facing);
    }

    public IBlockState getStateFromMeta(int meta)
    {
        EnumFacing enumfacing = EnumFacing.getFront(meta);

        return this.getDefaultState().withProperty(FACING, enumfacing);
    }

    /**
     * Convert the BlockState into the correct metadata value
     */
    public int getMetaFromState(IBlockState state)
    {
        return ((EnumFacing)state.getValue(FACING)).getIndex();
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[]{FACING});

    }

    @Override
    public boolean isFullBlock(IBlockState state) {
        return false;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState blockState, IBlockAccess source, BlockPos pos) {
        if ((blockState.getValue(FACING).equals(EnumFacing.UP)) || (blockState.getValue(FACING).equals(EnumFacing.DOWN))) {
            return PILLAR_AABB;
        } else if ((blockState.getValue(FACING).equals(EnumFacing.NORTH)) || (blockState.getValue(FACING).equals(EnumFacing.SOUTH))) {
            return NORTHSOUTH_AABB;
        }
        return EASTWEST_AABB;
    }

//    @Override
//    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean isActualState) {
//        super.addCollisionBoxToList(pos, entityBox, collidingBoxes, PILLAR_AABB);
//        super.addCollisionBoxToList(pos, entityBox, collidingBoxes, EASTWEST_AABB);
//        super.addCollisionBoxToList(pos, entityBox, collidingBoxes, NORTHSOUTH_AABB);
//    }

    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos)
    {
        if ((blockState.getValue(FACING).equals(EnumFacing.UP)) || (blockState.getValue(FACING).equals(EnumFacing.DOWN))) {
            return PILLAR_AABB;
        } else if ((blockState.getValue(FACING).equals(EnumFacing.NORTH)) || (blockState.getValue(FACING).equals(EnumFacing.SOUTH))) {
            return NORTHSOUTH_AABB;
        }
        return EASTWEST_AABB;
//        return blockState.getBoundingBox(worldIn, pos);
    }

    @Override
    public IBlockState withRotation(IBlockState state, Rotation rot)
    {
        return state.withProperty(FACING, rot.rotate((EnumFacing)state.getValue(FACING)));
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        worldIn.getCapability(PostCapabilityProvider.POST_CAP, null).addPostPos(pos);
    }

}
