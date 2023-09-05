package mokiyoki.enhancedanimals.tileentity;

import mokiyoki.enhancedanimals.blocks.EggCartonBlock;
import mokiyoki.enhancedanimals.blocks.EggCartonContainer;
import mokiyoki.enhancedanimals.init.ModTileEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.entity.LidBlockEntity;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.phys.AABB;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.Vec3;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;
import java.util.stream.IntStream;

@OnlyIn(
        value = Dist.CLIENT,
        _interface = LidBlockEntity.class
)
public class EggCartonTileEntity extends RandomizableContainerBlockEntity implements WorldlyContainer, LidBlockEntity {
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
    protected static final VoxelShape SHAPE_DEFAULT = Block.box(1.0D, 0.0D, 1.0D, 14.0D, 8.0D, 12.0D);
    protected static final VoxelShape SHAPE_EASTWEST = Block.box(1.0D, 0.0D, 1.0D, 12.0D, 8.0D, 14.0D);
    protected static final VoxelShape SHAPE_NORTHSOUTH = Block.box(1.0D, 0.0D, 1.0D, 14.0D, 8.0D, 12.0D);


    public EggCartonTileEntity(BlockPos blockPos, BlockState blockState) {
        super(ModTileEntities.EGG_CARTON_TILE_ENTITY.get(), blockPos, blockState);
    }

    public static void tick(Level level, BlockPos blockPos, BlockState blockState, EggCartonTileEntity eggCartonTileEntity) {
        eggCartonTileEntity.runTick();
    }

    public void runTick() {
        int i = this.worldPosition.getX();
        int j = this.worldPosition.getY();
        int k = this.worldPosition.getZ();
        ++this.ticksSinceSync;
        this.numPlayersUsing = getNumberOfPlayersUsing(this.level, this, this.ticksSinceSync, i, j, k, this.openCount);
        this.prevLidAngle = this.lidAngle;
        float f = 0.05F; // speed the lid opens/closes
        if (this.numPlayersUsing > 0 && this.lidAngle == 0.0F) {
            this.playSound(SoundEvents.CHEST_OPEN);
        }

        if (this.numPlayersUsing == 0 && this.lidAngle < 0.0F || this.numPlayersUsing > 0 && this.lidAngle > -0.25F) {
            float f1 = this.lidAngle;
            if (this.numPlayersUsing > 0) {
                this.lidAngle -= f;
            } else {
                this.lidAngle += f;
            }

            if (this.lidAngle < -0.25F) {
                this.lidAngle = -0.25F;
            }

            float f2 = -0.125F; // when to play the sound
            if (this.lidAngle > f2 && f1 <= f2) {
                this.playSound(SoundEvents.CHEST_CLOSE);
            }

            if (this.lidAngle > 0.0F) {
                this.lidAngle = 0.0F;
            }
        }
    }

    public EggCartonTileEntity.AnimationStatus getAnimationStatus() {
        return this.animationStatus;
    }


    public AABB getBoundingBox(BlockState p_190584_1_) {
        return this.getBoundingBox(p_190584_1_.getValue(EggCartonBlock.FACING));
    }

    public AABB getBoundingBox(Direction p_190587_1_) {
        float f = this.getProgress(1.0F);
        return Shapes.block().bounds().expandTowards((double)(0.5F * f * (float)p_190587_1_.getStepX()), (double)(0.5F * f * (float)p_190587_1_.getStepY()), (double)(0.5F * f * (float)p_190587_1_.getStepZ()));
    }

    private AABB getTopBoundingBox(Direction p_190588_1_) {
        Direction direction = p_190588_1_.getOpposite();
        return this.getBoundingBox(p_190588_1_).contract((double)direction.getStepX(), (double)direction.getStepY(), (double)direction.getStepZ());
    }

    private void playSound(SoundEvent soundIn) {
        double d0 = (double)this.worldPosition.getX() + 0.5D;
        double d1 = (double)this.worldPosition.getY() + 0.5D;
        double d2 = (double)this.worldPosition.getZ() + 0.5D;

        this.level.playSound((Player)null, d0, d1, d2, soundIn, SoundSource.BLOCKS, 0.5F, this.level.random.nextFloat() * 0.1F + 0.9F);
    }

    public static int getNumberOfPlayersUsing(Level p_213977_0_, RandomizableContainerBlockEntity thisEggCartonTileEntity, int ticksSinceSync, int cartonX, int cartonY, int cartonZ, int numPlayersUsing) {
        if (!p_213977_0_.isClientSide && numPlayersUsing != 0 && (ticksSinceSync + cartonX + cartonY + cartonZ) % 200 == 0) {
            numPlayersUsing = getNumberOfPlayersUsing(p_213977_0_, thisEggCartonTileEntity, cartonX, cartonY, cartonZ);
        }

        return numPlayersUsing;
    }

    public static int getNumberOfPlayersUsing(Level p_213976_0_, RandomizableContainerBlockEntity p_213976_1_, int p_213976_2_, int p_213976_3_, int p_213976_4_) {
        int i = 0;

//        for(Player playerentity : p_213976_0_.getEntitiesOfClass(Player.class, new AABB((double)((float)p_213976_2_ - 5.0F), (double)((float)p_213976_3_ - 5.0F), (double)((float)p_213976_4_ - 5.0F), (double)((float)(p_213976_2_ + 1) + 5.0F), (double)((float)(p_213976_3_ + 1) + 5.0F), (double)((float)(p_213976_4_ + 1) + 5.0F)))) {
//            if (playerentity.containerMenu instanceof EggCartonContainer) {
//                Container iinventory = ((EggCartonContainer)playerentity.containerMenu).getEggCartonInventory();
//                if (iinventory == p_213976_1_) {
//                    ++i;
//                }
//            }
//        }

        return i;
    }

    private void moveCollidedEntities() {
        BlockState blockstate = this.level.getBlockState(this.getBlockPos());
        if (blockstate.getBlock() instanceof EggCartonBlock) {
            Direction direction = blockstate.getValue(EggCartonBlock.FACING);
            AABB axisalignedbb = this.getTopBoundingBox(direction).move(this.worldPosition);
            List<Entity> list = this.level.getEntities((Entity)null, axisalignedbb);
            if (!list.isEmpty()) {
                for(int i = 0; i < list.size(); ++i) {
                    Entity entity = list.get(i);
                    if (entity.getPistonPushReaction() != PushReaction.IGNORE) {
                        double d0 = 0.0D;
                        double d1 = 0.0D;
                        double d2 = 0.0D;
                        AABB axisalignedbb1 = entity.getBoundingBox();
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

                        entity.move(MoverType.SHULKER_BOX, new Vec3(d0 * (double)direction.getStepX(), d1 * (double)direction.getStepY(), d2 * (double)direction.getStepZ()));
                    }
                }

            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    public float getOpenNess(float partialTicks) {
        return Mth.lerp(partialTicks, this.prevLidAngle, this.lidAngle);
    }

    /**
     * Returns the number of slots in the inventory.
     */
    public int getContainerSize() {
        return this.items.size();
    }

    public boolean triggerEvent(int id, int type) {
        if (id == 1) {
            this.openCount = type;
            if (type == 0) {
                this.doNeighborUpdates();
            }

            if (type == 1) {
                this.doNeighborUpdates();
            }

            return true;
        } else {
            return super.triggerEvent(id, type);
        }
    }

    private void doNeighborUpdates() {
        this.getBlockState().updateNeighbourShapes(this.getLevel(), this.getBlockPos(), 3);
    }

    public void startOpen(Player player) {
        if (!player.isSpectator()) {
            if (this.openCount < 0) {
                this.openCount = 0;
            }

            ++this.openCount;
            this.level.blockEvent(this.worldPosition, this.getBlockState().getBlock(), 1, this.openCount);
            if (this.openCount == 1) {
                this.level.playSound((Player)null, this.worldPosition, SoundEvents.CHEST_OPEN, SoundSource.BLOCKS, 0.5F, this.level.random.nextFloat() * 0.1F + 0.9F);
            }
        }

    }

    public void stopOpen(Player player) {
        if (!player.isSpectator()) {
            --this.openCount;
            this.level.blockEvent(this.worldPosition, this.getBlockState().getBlock(), 1, this.openCount);
            if (this.openCount <= 0) {
                this.level.playSound((Player)null, this.worldPosition, SoundEvents.CHEST_CLOSE, SoundSource.BLOCKS, 0.5F, this.level.random.nextFloat() * 0.1F + 0.9F);
            }
        }

    }

    protected Component getDefaultName() {
        return new TranslatableComponent("Egg Carton");
    }

    @Override
    public void load(CompoundTag compound) {
        super.load(compound);
        this.loadFromNbt(compound);
    }

    @Override
    public void saveAdditional(CompoundTag compound) {
        super.saveAdditional(compound);
        this.saveToNbt(compound);
    }

    public void loadFromNbt(CompoundTag compound) {
        this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        if (!this.tryLoadLootTable(compound) && compound.contains("Items", 9)) {
            ContainerHelper.loadAllItems(compound, this.items);
        }

    }

    public CompoundTag saveToNbt(CompoundTag compound) {
        if (!this.trySaveLootTable(compound)) {
            ContainerHelper.saveAllItems(compound, this.items, false);
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
    public boolean canPlaceItemThroughFace(int index, ItemStack itemStackIn, @Nullable Direction direction) {
        return !(Block.byItem(itemStackIn.getItem()) instanceof EggCartonBlock);
    }

    /**
     * Returns true if automation can extract the given item in the given slot from the given side.
     */
    public boolean canTakeItemThroughFace(int index, ItemStack stack, Direction direction) {
        return true;
    }

    public float getProgress(float p_190585_1_) {
        return Mth.lerp(p_190585_1_, this.progressOld, this.progress);
    }

    protected AbstractContainerMenu createMenu(int id, Inventory player) {
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
