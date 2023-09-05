package mokiyoki.enhancedanimals.blocks;

import it.unimi.dsi.fastutil.floats.Float2FloatFunction;
import mokiyoki.enhancedanimals.tileentity.EggCartonTileEntity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.stats.Stats;
import net.minecraft.world.level.block.entity.LidBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.DoubleBlockCombiner;
import net.minecraft.world.InteractionResult;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.phys.AABB;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.BiPredicate;

import static mokiyoki.enhancedanimals.init.ModTileEntities.EGG_CARTON_TILE_ENTITY;

public class EggCartonBlock extends BaseEntityBlock {

    public static final DirectionProperty FACING = BlockStateProperties.FACING;
    public static final ResourceLocation CONTENTS = new ResourceLocation("contents");
    protected static final VoxelShape SHAPE_DEFAULT = Block.box(1.0D, 0.0D, 2.0D, 15.0D, 8.0D, 14.0D);
    protected static final VoxelShape SHAPE_EASTWEST = Block.box(2.0D, 0.0D, 1.0D, 14.0D, 8.0D, 15.0D);
    protected static final VoxelShape SHAPE_NORTHSOUTH = Block.box(1.0D, 0.0D, 2.0D, 15.0D, 8.0D, 14.0D);


    public EggCartonBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new EggCartonTileEntity(blockPos, blockState);
    }

    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }

    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> blockEntityType) {
        return createTickerHelper(blockEntityType, EGG_CARTON_TILE_ENTITY.get(), EggCartonTileEntity::tick);
    }

    public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
        if (worldIn.isClientSide) {
            return InteractionResult.SUCCESS;
        } else if (player.isSpectator()) {
            return InteractionResult.SUCCESS;
        } else {
            BlockEntity tileentity = worldIn.getBlockEntity(pos);
            if (tileentity instanceof EggCartonTileEntity) {
                Direction direction = state.getValue(FACING);
                EggCartonTileEntity eggCartonTileEntity = (EggCartonTileEntity)tileentity;
                boolean flag;
                if (eggCartonTileEntity.getAnimationStatus() == EggCartonTileEntity.AnimationStatus.CLOSED) {
                    AABB axisalignedbb = Shapes.block().bounds().expandTowards((double)(0.5F * (float)direction.getStepX()), (double)(0.5F * (float)direction.getStepY()), (double)(0.5F * (float)direction.getStepZ())).contract((double)direction.getStepX(), (double)direction.getStepY(), (double)direction.getStepZ());
                    flag = worldIn.noCollision(axisalignedbb.move(pos.relative(direction)));
                } else {
                    flag = true;
                }

                if (flag) {
                    player.openMenu(eggCartonTileEntity);
                    player.awardStat(Stats.OPEN_CHEST);
                }

                return InteractionResult.SUCCESS;
            } else {
                return InteractionResult.PASS;
            }
        }
    }

//    @Nullable
//    public BlockState getStateForPlacement(BlockItemUseContext context) {
//        return this.getDefaultState().with(FACING, context.getFace());
//    }

    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Direction direction = context.getHorizontalDirection().getOpposite();
        return this.defaultBlockState().setValue(FACING, direction);
    }

    @OnlyIn(Dist.CLIENT)
    public static DoubleBlockCombiner.Combiner<EggCartonTileEntity, Float2FloatFunction> getLid(final LidBlockEntity p_226917_0_) {
        return new DoubleBlockCombiner.Combiner<EggCartonTileEntity, Float2FloatFunction>() {
            @Override
            public Float2FloatFunction acceptDouble(EggCartonTileEntity p_225539_1_, EggCartonTileEntity p_225539_2_) {
                return (p_226921_2_) -> {
                    return Math.max(p_225539_1_.getOpenNess(p_226921_2_), p_225539_2_.getOpenNess(p_226921_2_));
                };
            }

            @Override
            public Float2FloatFunction acceptSingle(EggCartonTileEntity p_225538_1_) {
                return p_225538_1_::getOpenNess;
            }

            @Override
            public Float2FloatFunction acceptNone() {
                return p_226917_0_::getOpenNess;
            }
        };
    }

    public DoubleBlockCombiner.NeighborCombineResult<? extends EggCartonTileEntity> getWrapper(BlockState blockState, Level world, BlockPos blockPos, boolean p_225536_4_) {
        BiPredicate<LevelAccessor, BlockPos> biPredicate;
        if (p_225536_4_) {
            biPredicate = (p_226918_0_, p_226918_1_) -> false;
        }
        else {
            biPredicate = EggCartonBlock::isBlocked;
        }

        return DoubleBlockCombiner.combineWithNeigbour(EGG_CARTON_TILE_ENTITY.get(), EggCartonBlock::getMergerType, EggCartonBlock::getDirectionToAttached, FACING, blockState, world, blockPos, biPredicate);
    }

    public static DoubleBlockCombiner.BlockType getMergerType(BlockState blockState) {
        return DoubleBlockCombiner.BlockType.SINGLE;
    }

    public static Direction getDirectionToAttached(BlockState state) {
        Direction direction = state.getValue(FACING);
        return direction.getCounterClockWise();
    }

    private static boolean isBlocked(LevelAccessor iWorld, BlockPos blockPos) {
        return isBelowSolidBlock(iWorld, blockPos);
    }

    private static boolean isBelowSolidBlock(BlockGetter iBlockReader, BlockPos worldIn) {
        BlockPos blockpos = worldIn.above();
        return iBlockReader.getBlockState(blockpos).isRedstoneConductor(iBlockReader, blockpos);
    }


    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    public void playerWillDestroy(Level worldIn, BlockPos pos, BlockState state, Player player) {
        BlockEntity tileentity = worldIn.getBlockEntity(pos);
        if (tileentity instanceof EggCartonTileEntity) {
            EggCartonTileEntity eggCartonTileEntity = (EggCartonTileEntity)tileentity;
            if (!worldIn.isClientSide && player.isCreative() && !eggCartonTileEntity.isEmpty()) {
                ItemStack itemstack = /*new ItemStack(ModBlocks.EGG_CARTON)*/ new ItemStack(Blocks.SHULKER_BOX);
                CompoundTag compoundnbt = eggCartonTileEntity.saveToNbt(new CompoundTag());
                if (!compoundnbt.isEmpty()) {
                    itemstack.addTagElement("BlockEntityTag", compoundnbt);
                }

                if (eggCartonTileEntity.hasCustomName()) {
                    itemstack.setHoverName(eggCartonTileEntity.getCustomName());
                }

                ItemEntity itementity = new ItemEntity(worldIn, (double)pos.getX(), (double)pos.getY(), (double)pos.getZ(), itemstack);
                itementity.setDefaultPickUpDelay();
                worldIn.addFreshEntity(itementity);
            } else {
                eggCartonTileEntity.unpackLootTable(player);
            }
        }

        super.playerWillDestroy(worldIn, pos, state, player);
    }


    public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder) {
        BlockEntity tileentity = builder.getOptionalParameter(LootContextParams.BLOCK_ENTITY);
        if (tileentity instanceof EggCartonTileEntity) {
            EggCartonTileEntity eggCartonTileEntity = (EggCartonTileEntity)tileentity;
            builder = builder.withDynamicDrop(CONTENTS, (p_220168_1_, p_220168_2_) -> {
                for(int i = 0; i < eggCartonTileEntity.getContainerSize(); ++i) {
                    p_220168_2_.accept(eggCartonTileEntity.getItem(i));
                }

            });
        }

        return super.getDrops(state, builder);
    }

    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable BlockGetter worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        CompoundTag compoundnbt = stack.getTagElement("BlockEntityTag");
        if (compoundnbt != null) {
            if (compoundnbt.contains("LootTable", 8)) {
                tooltip.add(new TextComponent("???????"));
            }

            if (compoundnbt.contains("Items", 9)) {
                NonNullList<ItemStack> nonnulllist = NonNullList.withSize(17, ItemStack.EMPTY);
                ContainerHelper.loadAllItems(compoundnbt, nonnulllist);
                int i = 0;
                int j = 0;

                for(ItemStack itemstack : nonnulllist) {
                    if (!itemstack.isEmpty()) {
                        ++j;
                        if (i <= 4) {
                            ++i;
                            Component itextcomponent = itemstack.getHoverName().copy();
                            ((MutableComponent) itextcomponent).append(" x").append(String.valueOf(itemstack.getCount()));
                            tooltip.add(itextcomponent);
                        }
                    }
                }

                if (j - i > 0) {
                    tooltip.add((new TranslatableComponent("container.egg_carton.more", j - i)).withStyle(ChatFormatting.ITALIC));
                }
            }
        }

    }

    @Override
    public PushReaction getPistonPushReaction(BlockState state) {
        return PushReaction.DESTROY;
    }


    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        BlockEntity tileentity = worldIn.getBlockEntity(pos);
        Direction enumfacing = state.getValue(FACING);
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

    public ItemStack getCloneItemStack(BlockGetter worldIn, BlockPos pos, BlockState state) {
        ItemStack itemstack = super.getCloneItemStack(worldIn, pos, state);
        EggCartonTileEntity eggCartonTileEntity = (EggCartonTileEntity)worldIn.getBlockEntity(pos);
        CompoundTag compoundnbt = eggCartonTileEntity.saveToNbt(new CompoundTag());
        if (!compoundnbt.isEmpty()) {
            itemstack.addTagElement("BlockEntityTag", compoundnbt);
        }
        return itemstack;
    }

    @Override
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor worldIn, BlockPos currentPos, BlockPos facingPos) {
        return super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rot) {
        return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return getShape(state, worldIn, pos, context);
    }

    public BlockState mirror(BlockState state, Mirror mirrorIn) {
        return state.rotate(mirrorIn.getRotation(state.getValue(FACING)));
    }

    @Override
    public boolean hasAnalogOutputSignal(BlockState state) {
        return true;
    }

    @Override
    public int getAnalogOutputSignal(BlockState blockState, Level worldIn, BlockPos pos) {
        return AbstractContainerMenu.getRedstoneSignalFromContainer((Container)worldIn.getBlockEntity(pos));
    }

}
