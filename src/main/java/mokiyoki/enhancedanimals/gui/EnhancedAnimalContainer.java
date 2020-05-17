package mokiyoki.enhancedanimals.gui;

import mokiyoki.enhancedanimals.entity.EnhancedAnimal;
import mokiyoki.enhancedanimals.entity.EnhancedChicken;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;

import static mokiyoki.enhancedanimals.util.handlers.EventRegistry.ENHANCED_ANIMAL_CONTAINER;

public class EnhancedAnimalContainer extends Container {
    private IInventory inventory;

    public EnhancedAnimalContainer(int i, PlayerInventory playerInventory, PacketBuffer packetBuffer) {
        this(i, playerInventory, (EnhancedAnimal) null);
    }

    public EnhancedAnimalContainer(int p_i50066_1_, PlayerInventory playerInventoryIn, EnhancedAnimal enhancedAnimal) {
        super(ENHANCED_ANIMAL_CONTAINER, p_i50066_1_);
        Inventory retrievedInventory;

        if (enhancedAnimal != null) {
            retrievedInventory = enhancedAnimal.getEnhancedInventory();
        } else {
            retrievedInventory = new Inventory(15);
        }

        assertInventorySize(retrievedInventory, 15);
        this.inventory = retrievedInventory;
        retrievedInventory.openInventory(playerInventoryIn.player);
        int i = 3; // inv height
        int j = 5; // inv width
        for(int k = 0; k < i; ++k) {
            for(int l = 0; l < j; ++l) {
                this.addSlot(new Slot(retrievedInventory, l + k * j, 17 + l * 18, 18 + k * 18){
                    public int getSlotStackLimit() {
                        return 64;
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

    public IInventory getEnhancedAnimalInventory() {
        return this.inventory;
    }

    @Override
    public void onContainerClosed(PlayerEntity player) {
        super.onContainerClosed(player);
    }

}
