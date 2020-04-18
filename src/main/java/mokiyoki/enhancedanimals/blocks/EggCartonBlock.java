package mokiyoki.enhancedanimals.blocks;

import it.unimi.dsi.fastutil.floats.Float2FloatFunction;
import mokiyoki.enhancedanimals.init.ModBlocks;
import mokiyoki.enhancedanimals.tileentity.EggCartonTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.ContainerBlock;
import net.minecraft.block.material.PushReaction;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.stats.Stats;
import net.minecraft.tileentity.IChestLid;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityMerger;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.Mirror;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.LootParameters;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Supplier;

import static mokiyoki.enhancedanimals.init.ModTileEntities.EGG_CARTON_TILE_ENTITY;

public class EggCartonBlock extends ContainerBlock {

    public static final DirectionProperty FACING = BlockStateProperties.FACING;
    public static final ResourceLocation field_220169_b = new ResourceLocation("contents");
    protected static final VoxelShape SHAPE_DEFAULT = Block.makeCuboidShape(1.0D, 0.0D, 2.0D, 15.0D, 8.0D, 14.0D);
    protected static final VoxelShape SHAPE_EASTWEST = Block.makeCuboidShape(2.0D, 0.0D, 1.0D, 14.0D, 8.0D, 15.0D);
    protected static final VoxelShape SHAPE_NORTHSOUTH = Block.makeCuboidShape(1.0D, 0.0D, 2.0D, 15.0D, 8.0D, 14.0D);


    public EggCartonBlock(Properties properties) {
        super(properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(FACING, Direction.NORTH));
    }

    public TileEntity createNewTileEntity(IBlockReader worldIn) {
        return new EggCartonTileEntity();
    }

    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.ENTITYBLOCK_ANIMATED;
    }


    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (worldIn.isRemote) {
            return ActionResultType.SUCCESS;
        } else if (player.isSpectator()) {
            return ActionResultType.SUCCESS;
        } else {
            TileEntity tileentity = worldIn.getTileEntity(pos);
            if (tileentity instanceof EggCartonTileEntity) {
                Direction direction = state.get(FACING);
                EggCartonTileEntity eggCartonTileEntity = (EggCartonTileEntity)tileentity;
                boolean flag;
                if (eggCartonTileEntity.getAnimationStatus() == EggCartonTileEntity.AnimationStatus.CLOSED) {
                    AxisAlignedBB axisalignedbb = VoxelShapes.fullCube().getBoundingBox().expand((double)(0.5F * (float)direction.getXOffset()), (double)(0.5F * (float)direction.getYOffset()), (double)(0.5F * (float)direction.getZOffset())).contract((double)direction.getXOffset(), (double)direction.getYOffset(), (double)direction.getZOffset());
                    flag = worldIn.func_226664_a_(axisalignedbb.offset(pos.offset(direction)));
                } else {
                    flag = true;
                }

                if (flag) {
                    player.openContainer(eggCartonTileEntity);
                    player.addStat(Stats.OPEN_CHEST);
                }

                return ActionResultType.SUCCESS;
            } else {
                return ActionResultType.PASS;
            }
        }
    }

//    @Nullable
//    public BlockState getStateForPlacement(BlockItemUseContext context) {
//        return this.getDefaultState().with(FACING, context.getFace());
//    }

    public BlockState getStateForPlacement(BlockItemUseContext context) {
        Direction direction = context.getPlacementHorizontalFacing().getOpposite();
        return this.getDefaultState().with(FACING, direction);
    }

    @OnlyIn(Dist.CLIENT)
    public static TileEntityMerger.ICallback<EggCartonTileEntity, Float2FloatFunction> getLid(final IChestLid p_226917_0_) {
        return new TileEntityMerger.ICallback<EggCartonTileEntity, Float2FloatFunction>() {
            @Override
            public Float2FloatFunction func_225539_a_(EggCartonTileEntity p_225539_1_, EggCartonTileEntity p_225539_2_) {
                return (p_226921_2_) -> {
                    return Math.max(p_225539_1_.getLidAngle(p_226921_2_), p_225539_2_.getLidAngle(p_226921_2_));
                };
            }

            @Override
            public Float2FloatFunction func_225538_a_(EggCartonTileEntity p_225538_1_) {
                return p_225538_1_::getLidAngle;
            }

            @Override
            public Float2FloatFunction func_225537_b_() {
                return p_226917_0_::getLidAngle;
            }
        };
    }

    public TileEntityMerger.ICallbackWrapper<? extends EggCartonTileEntity> getWrapper(BlockState blockState, World world, BlockPos blockPos, boolean p_225536_4_) {
        BiPredicate<IWorld, BlockPos> biPredicate;
        if (p_225536_4_) {
            biPredicate = (p_226918_0_, p_226918_1_) -> false;
        }
        else {
            biPredicate = EggCartonBlock::isBlocked;
        }

        return TileEntityMerger.func_226924_a_(EGG_CARTON_TILE_ENTITY, EggCartonBlock::getMergerType, EggCartonBlock::getDirectionToAttached, FACING, blockState, world, blockPos, biPredicate);
    }

    public static TileEntityMerger.Type getMergerType(BlockState blockState) {
        return TileEntityMerger.Type.SINGLE;
    }

    public static Direction getDirectionToAttached(BlockState state) {
        Direction direction = state.get(FACING);
        return direction.rotateYCCW();
    }

    private static boolean isBlocked(IWorld iWorld, BlockPos blockPos) {
        return isBelowSolidBlock(iWorld, blockPos);
    }

    private static boolean isBelowSolidBlock(IBlockReader iBlockReader, BlockPos worldIn) {
        BlockPos blockpos = worldIn.up();
        return iBlockReader.getBlockState(blockpos).isNormalCube(iBlockReader, blockpos);
    }


    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    public void onBlockHarvested(World worldIn, BlockPos pos, BlockState state, PlayerEntity player) {
        TileEntity tileentity = worldIn.getTileEntity(pos);
        if (tileentity instanceof EggCartonTileEntity) {
            EggCartonTileEntity eggCartonTileEntity = (EggCartonTileEntity)tileentity;
            if (!worldIn.isRemote && player.isCreative() && !eggCartonTileEntity.isEmpty()) {
                ItemStack itemstack = new ItemStack(ModBlocks.Egg_Carton);
                CompoundNBT compoundnbt = eggCartonTileEntity.saveToNbt(new CompoundNBT());
                if (!compoundnbt.isEmpty()) {
                    itemstack.setTagInfo("BlockEntityTag", compoundnbt);
                }

                if (eggCartonTileEntity.hasCustomName()) {
                    itemstack.setDisplayName(eggCartonTileEntity.getCustomName());
                }

                ItemEntity itementity = new ItemEntity(worldIn, (double)pos.getX(), (double)pos.getY(), (double)pos.getZ(), itemstack);
                itementity.setDefaultPickupDelay();
                worldIn.addEntity(itementity);
            } else {
                eggCartonTileEntity.fillWithLoot(player);
            }
        }

        super.onBlockHarvested(worldIn, pos, state, player);
    }


    public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder) {
        TileEntity tileentity = builder.get(LootParameters.BLOCK_ENTITY);
        if (tileentity instanceof EggCartonTileEntity) {
            EggCartonTileEntity eggCartonTileEntity = (EggCartonTileEntity)tileentity;
            builder = builder.withDynamicDrop(field_220169_b, (p_220168_1_, p_220168_2_) -> {
                for(int i = 0; i < eggCartonTileEntity.getSizeInventory(); ++i) {
                    p_220168_2_.accept(eggCartonTileEntity.getStackInSlot(i));
                }

            });
        }

        return super.getDrops(state, builder);
    }

    @OnlyIn(Dist.CLIENT)
    public void addInformation(ItemStack stack, @Nullable IBlockReader worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        CompoundNBT compoundnbt = stack.getChildTag("BlockEntityTag");
        if (compoundnbt != null) {
            if (compoundnbt.contains("LootTable", 8)) {
                tooltip.add(new StringTextComponent("???????"));
            }

            if (compoundnbt.contains("Items", 9)) {
                NonNullList<ItemStack> nonnulllist = NonNullList.withSize(17, ItemStack.EMPTY);
                ItemStackHelper.loadAllItems(compoundnbt, nonnulllist);
                int i = 0;
                int j = 0;

                for(ItemStack itemstack : nonnulllist) {
                    if (!itemstack.isEmpty()) {
                        ++j;
                        if (i <= 4) {
                            ++i;
                            ITextComponent itextcomponent = itemstack.getDisplayName().deepCopy();
                            itextcomponent.appendText(" x").appendText(String.valueOf(itemstack.getCount()));
                            tooltip.add(itextcomponent);
                        }
                    }
                }

                if (j - i > 0) {
                    tooltip.add((new TranslationTextComponent("container.egg_carton.more", j - i)).applyTextStyle(TextFormatting.ITALIC));
                }
            }
        }

    }

    @Override
    public PushReaction getPushReaction(BlockState state) {
        return PushReaction.DESTROY;
    }


    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        TileEntity tileentity = worldIn.getTileEntity(pos);
        Direction enumfacing = state.get(FACING);
        switch(enumfacing) {
            case UP:
            default:
                return SHAPE_DEFAULT;
            case DOWN:
                return SHAPE_DEFAULT;
            case NORTH:
                return SHAPE_NORTHSOUTH;
            case SOUTH:
                return SHAPE_NORTHSOUTH;
            case EAST:
                return SHAPE_EASTWEST;
            case WEST:
                return SHAPE_EASTWEST;
        }
    }

    public ItemStack getItem(IBlockReader worldIn, BlockPos pos, BlockState state) {
        ItemStack itemstack = super.getItem(worldIn, pos, state);
        EggCartonTileEntity eggCartonTileEntity = (EggCartonTileEntity)worldIn.getTileEntity(pos);
        CompoundNBT compoundnbt = eggCartonTileEntity.saveToNbt(new CompoundNBT());
        if (!compoundnbt.isEmpty()) {
            itemstack.setTagInfo("BlockEntityTag", compoundnbt);
        }
        return itemstack;
    }

    @Override
    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        return super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rot) {
        return state.with(FACING, rot.rotate(state.get(FACING)));
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return getShape(state, worldIn, pos, context);
    }

    public BlockState mirror(BlockState state, Mirror mirrorIn) {
        return state.rotate(mirrorIn.toRotation(state.get(FACING)));
    }

    @Override
    public boolean hasComparatorInputOverride(BlockState state) {
        return true;
    }

    @Override
    public int getComparatorInputOverride(BlockState blockState, World worldIn, BlockPos pos) {
        return Container.calcRedstoneFromInventory((IInventory)worldIn.getTileEntity(pos));
    }

    //TODO make it so it will break when moved by piston
//    @Override
//    public PushReaction getPushReaction(BlockState state) {
//        return PushReaction.DESTROY;
//    }
}
