package mokiyoki.enhancedanimals.blocks;

import mokiyoki.enhancedanimals.items.EnhancedEgg;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;

import static mokiyoki.enhancedanimals.util.handlers.RegistryHandler.EGG_CARTON_CONTAINER;

public class EggCartonContainer extends Container {
    private final IInventory inventory;

    public EggCartonContainer(int p_i50065_1_, PlayerInventory p_i50065_2_) {
        this(p_i50065_1_, p_i50065_2_, new Inventory(16));
    }

    public EggCartonContainer(int i, PlayerInventory playerInventory, PacketBuffer packetBuffer) {
        this(i, playerInventory, new Inventory(16));
    }

    public EggCartonContainer(int p_i50066_1_, PlayerInventory playerInventoryIn, IInventory p_i50066_3_) {
        super(EGG_CARTON_CONTAINER, p_i50066_1_);
        assertInventorySize(p_i50066_3_, 16);
        this.inventory = p_i50066_3_;
        p_i50066_3_.openInventory(playerInventoryIn.player);
        int i = 2;
        int j = 8;

        for(int k = 0; k < 2; ++k) {
            for(int l = 0; l < 8; ++l) {
                this.addSlot(new Slot(p_i50066_3_, l + k * 8, 17 + l * 18, 18 + k * 18){
                    /**
                     * Check if the stack is allowed to be placed in this slot, used for armor slots as well as furnace fuel.
                     */
                    public boolean isItemValid(ItemStack stack) {
                        return (stack.getItem() instanceof EnhancedEgg);
                    }

                    public int getSlotStackLimit() {
                        return 1;
                    }
                });
            }
        }

        for(int i1 = 0; i1 < 3; ++i1) {
            for(int k1 = 0; k1 < 9; ++k1) {
                this.addSlot(new Slot(playerInventoryIn, k1 + i1 * 9 + 9, 8 + k1 * 18, 66 + i1 * 18));
            }
        }

        for(int j1 = 0; j1 < 9; ++j1) {
            this.addSlot(new Slot(playerInventoryIn, j1, 8 + j1 * 18, 124));
        }
    }

    public boolean canInteractWith(PlayerEntity playerIn) {
        return this.inventory.isUsableByPlayer(playerIn);
    }

    /**
     * Handle when the stack in slot {@code index} is shift-clicked. Normally this moves the stack between the player
     * inventory and the other inventory(s).
     */
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);
        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();
            if (index < this.inventory.getSizeInventory()) {
                if (!this.mergeItemStack(itemstack1, this.inventory.getSizeInventory(), this.inventorySlots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.mergeItemStack(itemstack1, 0, this.inventory.getSizeInventory(), false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }
        }

        return itemstack;
    }

    /**
     * Called when the container is closed.
     */
    public void onContainerClosed(PlayerEntity playerIn) {
        super.onContainerClosed(playerIn);
        this.inventory.closeInventory(playerIn);
    }
}