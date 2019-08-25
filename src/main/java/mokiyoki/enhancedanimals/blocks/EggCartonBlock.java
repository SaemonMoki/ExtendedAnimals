package mokiyoki.enhancedanimals.blocks;

import mokiyoki.enhancedanimals.tileentity.EggCartonTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.ContainerBlock;
import net.minecraft.client.util.ITooltipFlag;
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
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
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

public class EggCartonBlock extends ContainerBlock {

    public static final DirectionProperty FACING = BlockStateProperties.FACING;
    public static final ResourceLocation field_220169_b = new ResourceLocation("contents");

    public EggCartonBlock(Properties properties) {
        super(properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(FACING, Direction.NORTH));
    }

    public TileEntity createNewTileEntity(IBlockReader worldIn) {
        return new EggCartonTileEntity();
    }

    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }


    public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (worldIn.isRemote) {
            return true;
        } else if (player.isSpectator()) {
            return true;
        } else {
            TileEntity tileentity = worldIn.getTileEntity(pos);
            if (tileentity instanceof EggCartonTileEntity) {
                Direction direction = state.get(FACING);
                EggCartonTileEntity eggCartonTileEntity = (EggCartonTileEntity)tileentity;
                boolean flag;
//                if (shulkerboxtileentity.getAnimationStatus() == EggCartonTileEntity.AnimationStatus.CLOSED) {
//                    AxisAlignedBB axisalignedbb = VoxelShapes.fullCube().getBoundingBox().expand((double)(0.5F * (float)direction.getXOffset()), (double)(0.5F * (float)direction.getYOffset()), (double)(0.5F * (float)direction.getZOffset())).contract((double)direction.getXOffset(), (double)direction.getYOffset(), (double)direction.getZOffset());
//                    flag = worldIn.areCollisionShapesEmpty(axisalignedbb.offset(pos.offset(direction)));
//                } else {
                    flag = true;
//                }

                if (flag) {
                    player.openContainer(eggCartonTileEntity);
                    player.addStat(Stats.OPEN_CHEST);
                }

                return true;
            } else {
                return false;
            }
        }
    }

    @Nullable
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.getDefaultState().with(FACING, context.getFace());
    }


    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    public void onBlockHarvested(World worldIn, BlockPos pos, BlockState state, PlayerEntity player) {
        TileEntity tileentity = worldIn.getTileEntity(pos);
        if (tileentity instanceof EggCartonTileEntity) {
            EggCartonTileEntity eggCartonTileEntity = (EggCartonTileEntity)tileentity;
            if (!worldIn.isRemote && player.isCreative() && !eggCartonTileEntity.isEmpty()) {
//                ItemStack itemstack = getColoredItemStack(this.getColor());
                CompoundNBT compoundnbt = eggCartonTileEntity.saveToNbt(new CompoundNBT());
                if (!compoundnbt.isEmpty()) {
//                    itemstack.setTagInfo("BlockEntityTag", compoundnbt);
                }

                if (eggCartonTileEntity.hasCustomName()) {
//                    itemstack.setDisplayName(shulkerboxtileentity.getCustomName());
                }

//                ItemEntity itementity = new ItemEntity(worldIn, (double)pos.getX(), (double)pos.getY(), (double)pos.getZ(), itemstack);
//                itementity.setDefaultPickupDelay();
//                worldIn.addEntity(itementity);
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
                NonNullList<ItemStack> nonnulllist = NonNullList.withSize(27, ItemStack.EMPTY);
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

    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        TileEntity tileentity = worldIn.getTileEntity(pos);
        return tileentity instanceof EggCartonTileEntity ? VoxelShapes.create(((EggCartonTileEntity)tileentity).getBoundingBox(state)) : VoxelShapes.fullCube();
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
    public boolean isSolid(BlockState state) {
        return false;
    }

    @Override
    public boolean hasComparatorInputOverride(BlockState state) {
        return true;
    }

    @Override
    public int getComparatorInputOverride(BlockState blockState, World worldIn, BlockPos pos) {
        return Container.calcRedstoneFromInventory((IInventory)worldIn.getTileEntity(pos));
    }

}
