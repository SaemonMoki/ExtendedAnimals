package mokiyoki.enhancedanimals.tileentity;

import mokiyoki.enhancedanimals.blocks.EggCartonBlock;
import mokiyoki.enhancedanimals.blocks.EggCartonContainer;
import mokiyoki.enhancedanimals.init.ModTileEntities;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.PushReaction;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.DyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.IChestLid;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;
import java.util.stream.IntStream;

public class EggCartonTileEntity extends LockableLootTileEntity implements ISidedInventory, IChestLid, ITickableTileEntity {
    private static final int[] SLOTS = IntStream.range(0, 16).toArray();
    private NonNullList<ItemStack> items = NonNullList.withSize(16, ItemStack.EMPTY);
    private int openCount;
    private EggCartonTileEntity.AnimationStatus animationStatus = EggCartonTileEntity.AnimationStatus.CLOSED;
    private float progress;
    private float progressOld;

    protected float lidAngle;
    protected float prevLidAngle;
    protected int numPlayersUsing;
    private int ticksSinceSync;

    public static final DirectionProperty FACING = BlockStateProperties.FACING;
    protected static final VoxelShape SHAPE_DEFAULT = Block.makeCuboidShape(1.0D, 0.0D, 1.0D, 14.0D, 8.0D, 12.0D);
    protected static final VoxelShape SHAPE_EASTWEST = Block.makeCuboidShape(1.0D, 0.0D, 1.0D, 12.0D, 8.0D, 14.0D);
    protected static final VoxelShape SHAPE_NORTHSOUTH = Block.makeCuboidShape(1.0D, 0.0D, 1.0D, 14.0D, 8.0D, 12.0D);


    public EggCartonTileEntity() {
        super(ModTileEntities.EGG_CARTON_TILE_ENTITY);
    }

    public void tick() {
        int i = this.pos.getX();
        int j = this.pos.getY();
        int k = this.pos.getZ();
        ++this.ticksSinceSync;
        this.numPlayersUsing = func_213977_a(this.world, this, this.ticksSinceSync, i, j, k, this.numPlayersUsing);
        this.prevLidAngle = this.lidAngle;
        float f = 0.1F;
        if (this.numPlayersUsing > 0 && this.lidAngle == 0.0F) {
            this.playSound(SoundEvents.BLOCK_CHEST_OPEN);
        }

        if (this.numPlayersUsing == 0 && this.lidAngle > 0.0F || this.numPlayersUsing > 0 && this.lidAngle < 1.0F) {
            float f1 = this.lidAngle;
            if (this.numPlayersUsing > 0) {
                this.lidAngle += 0.1F;
            } else {
                this.lidAngle -= 0.1F;
            }

            if (this.lidAngle > 1.0F) {
                this.lidAngle = 1.0F;
            }

            float f2 = 0.5F;
            if (this.lidAngle < 0.5F && f1 >= 0.5F) {
                this.playSound(SoundEvents.BLOCK_CHEST_CLOSE);
            }

            if (this.lidAngle < 0.0F) {
                this.lidAngle = 0.0F;
            }
        }

    }

    public EggCartonTileEntity.AnimationStatus getAnimationStatus() {
        return this.animationStatus;
    }


    public AxisAlignedBB getBoundingBox(BlockState p_190584_1_) {
        return this.getBoundingBox(p_190584_1_.get(EggCartonBlock.FACING));
    }

    public AxisAlignedBB getBoundingBox(Direction p_190587_1_) {
        float f = this.getProgress(1.0F);
        return VoxelShapes.fullCube().getBoundingBox().expand((double)(0.5F * f * (float)p_190587_1_.getXOffset()), (double)(0.5F * f * (float)p_190587_1_.getYOffset()), (double)(0.5F * f * (float)p_190587_1_.getZOffset()));
    }

    private AxisAlignedBB getTopBoundingBox(Direction p_190588_1_) {
        Direction direction = p_190588_1_.getOpposite();
        return this.getBoundingBox(p_190588_1_).contract((double)direction.getXOffset(), (double)direction.getYOffset(), (double)direction.getZOffset());
    }

    private void playSound(SoundEvent soundIn) {
        double d0 = (double)this.pos.getX() + 0.5D;
        double d1 = (double)this.pos.getY() + 0.5D;
        double d2 = (double)this.pos.getZ() + 0.5D;

        this.world.playSound((PlayerEntity)null, d0, d1, d2, soundIn, SoundCategory.BLOCKS, 0.5F, this.world.rand.nextFloat() * 0.1F + 0.9F);
    }

    public static int func_213977_a(World p_213977_0_, LockableLootTileEntity p_213977_1_, int p_213977_2_, int p_213977_3_, int p_213977_4_, int p_213977_5_, int p_213977_6_) {
        if (!p_213977_0_.isRemote && p_213977_6_ != 0 && (p_213977_2_ + p_213977_3_ + p_213977_4_ + p_213977_5_) % 200 == 0) {
            p_213977_6_ = func_213976_a(p_213977_0_, p_213977_1_, p_213977_3_, p_213977_4_, p_213977_5_);
        }

        return p_213977_6_;
    }

    public static int func_213976_a(World p_213976_0_, LockableLootTileEntity p_213976_1_, int p_213976_2_, int p_213976_3_, int p_213976_4_) {
        int i = 0;

        for(PlayerEntity playerentity : p_213976_0_.getEntitiesWithinAABB(PlayerEntity.class, new AxisAlignedBB((double)((float)p_213976_2_ - 5.0F), (double)((float)p_213976_3_ - 5.0F), (double)((float)p_213976_4_ - 5.0F), (double)((float)(p_213976_2_ + 1) + 5.0F), (double)((float)(p_213976_3_ + 1) + 5.0F), (double)((float)(p_213976_4_ + 1) + 5.0F)))) {
            if (playerentity.openContainer instanceof EggCartonContainer) {
                IInventory iinventory = ((EggCartonContainer)playerentity.openContainer).getEggCartonInventory();
                if (iinventory == p_213976_1_) {
                    ++i;
                }
            }
        }

        return i;
    }

    private void moveCollidedEntities() {
        BlockState blockstate = this.world.getBlockState(this.getPos());
        if (blockstate.getBlock() instanceof EggCartonBlock) {
            Direction direction = blockstate.get(EggCartonBlock.FACING);
            AxisAlignedBB axisalignedbb = this.getTopBoundingBox(direction).offset(this.pos);
            List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity((Entity)null, axisalignedbb);
            if (!list.isEmpty()) {
                for(int i = 0; i < list.size(); ++i) {
                    Entity entity = list.get(i);
                    if (entity.getPushReaction() != PushReaction.IGNORE) {
                        double d0 = 0.0D;
                        double d1 = 0.0D;
                        double d2 = 0.0D;
                        AxisAlignedBB axisalignedbb1 = entity.getBoundingBox();
                        switch(direction.getAxis()) {
                            case X:
                                if (direction.getAxisDirection() == Direction.AxisDirection.POSITIVE) {
                                    d0 = axisalignedbb.maxX - axisalignedbb1.minX;
                                } else {
                                    d0 = axisalignedbb1.maxX - axisalignedbb.minX;
                                }

                                d0 = d0 + 0.01D;
                                break;
                            case Y:
                                if (direction.getAxisDirection() == Direction.AxisDirection.POSITIVE) {
                                    d1 = axisalignedbb.maxY - axisalignedbb1.minY;
                                } else {
                                    d1 = axisalignedbb1.maxY - axisalignedbb.minY;
                                }

                                d1 = d1 + 0.01D;
                                break;
                            case Z:
                                if (direction.getAxisDirection() == Direction.AxisDirection.POSITIVE) {
                                    d2 = axisalignedbb.maxZ - axisalignedbb1.minZ;
                                } else {
                                    d2 = axisalignedbb1.maxZ - axisalignedbb.minZ;
                                }

                                d2 = d2 + 0.01D;
                        }

                        entity.move(MoverType.SHULKER_BOX, new Vec3d(d0 * (double)direction.getXOffset(), d1 * (double)direction.getYOffset(), d2 * (double)direction.getZOffset()));
                    }
                }

            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    public float getLidAngle(float partialTicks) {
        return MathHelper.lerp(partialTicks, this.prevLidAngle, this.lidAngle);
    }

    /**
     * Returns the number of slots in the inventory.
     */
    public int getSizeInventory() {
        return this.items.size();
    }

    /**
     * See {@link Block#eventReceived} for more information. This must return true serverside before it is called
     * clientside.
     */
    public boolean receiveClientEvent(int id, int type) {
        if (id == 1) {
            this.openCount = type;
            if (type == 0) {
//                this.animationStatus = ShulkerBoxTileEntity.AnimationStatus.CLOSING;
                this.func_213975_v();
            }

            if (type == 1) {
//                this.animationStatus = ShulkerBoxTileEntity.AnimationStatus.OPENING;
                this.func_213975_v();
            }

            return true;
        } else {
            return super.receiveClientEvent(id, type);
        }
    }

    private void func_213975_v() {
        this.getBlockState().updateNeighbors(this.getWorld(), this.getPos(), 3);
    }

    public void openInventory(PlayerEntity player) {
        if (!player.isSpectator()) {
            if (this.openCount < 0) {
                this.openCount = 0;
            }

            ++this.openCount;
            this.world.addBlockEvent(this.pos, this.getBlockState().getBlock(), 1, this.openCount);
            if (this.openCount == 1) {
                this.world.playSound((PlayerEntity)null, this.pos, SoundEvents.BLOCK_CHEST_OPEN, SoundCategory.BLOCKS, 0.5F, this.world.rand.nextFloat() * 0.1F + 0.9F);
            }
        }

    }

    public void closeInventory(PlayerEntity player) {
        if (!player.isSpectator()) {
            --this.openCount;
            this.world.addBlockEvent(this.pos, this.getBlockState().getBlock(), 1, this.openCount);
            if (this.openCount <= 0) {
                this.world.playSound((PlayerEntity)null, this.pos, SoundEvents.BLOCK_CHEST_CLOSE, SoundCategory.BLOCKS, 0.5F, this.world.rand.nextFloat() * 0.1F + 0.9F);
            }
        }

    }

    protected ITextComponent getDefaultName() {
        return new TranslationTextComponent("Egg Carton");
    }

    public void read(CompoundNBT compound) {
        super.read(compound);
        this.loadFromNbt(compound);
    }

    public CompoundNBT write(CompoundNBT compound) {
        super.write(compound);
        return this.saveToNbt(compound);
    }

    public void loadFromNbt(CompoundNBT compound) {
        this.items = NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);
        if (!this.checkLootAndRead(compound) && compound.contains("Items", 9)) {
            ItemStackHelper.loadAllItems(compound, this.items);
        }

    }

    public CompoundNBT saveToNbt(CompoundNBT compound) {
        if (!this.checkLootAndWrite(compound)) {
            ItemStackHelper.saveAllItems(compound, this.items, false);
        }

        return compound;
    }

    protected NonNullList<ItemStack> getItems() {
        return this.items;
    }

    protected void setItems(NonNullList<ItemStack> itemsIn) {
        this.items = itemsIn;
    }

    public boolean isEmpty() {
        for(ItemStack itemstack : this.items) {
            if (!itemstack.isEmpty()) {
                return false;
            }
        }

        return true;
    }

    public int[] getSlotsForFace(Direction side) {
        return SLOTS;
    }

    /**
     * Returns true if automation can insert the given item in the given slot from the given side.
     */
    public boolean canInsertItem(int index, ItemStack itemStackIn, @Nullable Direction direction) {
        return !(Block.getBlockFromItem(itemStackIn.getItem()) instanceof EggCartonBlock);
    }

    /**
     * Returns true if automation can extract the given item in the given slot from the given side.
     */
    public boolean canExtractItem(int index, ItemStack stack, Direction direction) {
        return true;
    }

    public float getProgress(float p_190585_1_) {
        return MathHelper.lerp(p_190585_1_, this.progressOld, this.progress);
    }

    protected Container createMenu(int id, PlayerInventory player) {
        return new EggCartonContainer(id, player, this);
    }

    @Override
    protected net.minecraftforge.items.IItemHandler createUnSidedHandler() {
        return new net.minecraftforge.items.wrapper.SidedInvWrapper(this, Direction.UP);
    }

    public static enum AnimationStatus {
        CLOSED,
        OPENING,
        OPENED,
        CLOSING;
    }
}