package mokiyoki.enhancedanimals.tileentity;

import mokiyoki.enhancedanimals.init.ModTileEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.concurrent.ThreadLocalRandom;

public class ChickenNestTileEntity extends BlockEntity implements Container {
    private NonNullList<ItemStack> items = NonNullList.withSize(12, ItemStack.EMPTY);

    public ChickenNestTileEntity(BlockPos p_155229_, BlockState p_155230_) {
        super(ModTileEntities.CHICKEN_NEST_TILE_ENTITY.get(), p_155229_, p_155230_);
    }

    @Override
    public void load(CompoundTag compound) {
        super.load(compound);
        this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        if (compound.contains("Items", 9)) {
            ContainerHelper.loadAllItems(compound, this.items);
        }
    }

    @Override
    protected void saveAdditional(CompoundTag compound) {
        super.saveAdditional(compound);
        ContainerHelper.saveAllItems(compound, this.items, false);
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag tag = new CompoundTag();
        saveAdditional(tag);
        return tag;
    }

    @Override
    public void handleUpdateTag(CompoundTag nbt) {
        load(nbt);
        super.handleUpdateTag(nbt);
    }

    @Override
    public int getContainerSize() {
        return 12;
    }

    @Override
    public boolean isEmpty() {
        for (int i = 0; i<getContainerSize();i++) {
            if (!this.items.get(i).isEmpty()) return false;
        }

        return true;
    }

    @Override
    public ItemStack getItem(int itemslot) {
        return this.items.isEmpty()?null:this.items.get(itemslot);
    }

    public int getSlotWithEgg() {
        for (int i=11; i>=0;i--) {
            if (!this.items.get(i).isEmpty()) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public ItemStack removeItem(int slot, int count) {
        ItemStack stack = slot < getContainerSize() && !this.items.get(slot).isEmpty() ? this.items.get(slot).split(count) : ItemStack.EMPTY;
        this.setChanged();
        return stack;
    }

    @Override
    public ItemStack removeItemNoUpdate(int p_18951_) {
        return null;
    }

    @Override
    public void setItem(int p_18944_, ItemStack p_18945_) {

    }

    @Override
    public boolean stillValid(Player p_18946_) {
        return false;
    }

    @Override
    public void clearContent() {

    }

    public boolean isFull() {
        for (int i = 0; i<getContainerSize();i++) {
            if (this.items.get(i).isEmpty()) return false;
        }

        return true;
    }

    public void addEggToNest(ItemStack itemStack) {
        for (int i=0; i<getContainerSize();i++) {
            if (this.items.get(i).isEmpty()) {
                this.items.set(i, itemStack);
                this.setChanged();
                return;
            }
        }
        this.setChanged();
    }


}
