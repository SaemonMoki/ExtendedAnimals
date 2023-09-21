package mokiyoki.enhancedanimals.blocks;

import mokiyoki.enhancedanimals.init.ModItems;
import mokiyoki.enhancedanimals.items.EnhancedEgg;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.common.data.ForgeItemTagsProvider;

import static mokiyoki.enhancedanimals.util.handlers.EventRegistry.EGG_CARTON_CONTAINER;

public class EggCartonContainer extends AbstractContainerMenu {
    private final Container inventory;

    public EggCartonContainer(int p_i50065_1_, Inventory p_i50065_2_) {
        this(p_i50065_1_, p_i50065_2_, new SimpleContainer(16));
    }

    public EggCartonContainer(int i, Inventory playerInventory, FriendlyByteBuf packetBuffer) {
        this(i, playerInventory, new SimpleContainer(16));
    }

    public EggCartonContainer(int p_i50066_1_, Inventory playerInventoryIn, Container p_i50066_3_) {
        super(EGG_CARTON_CONTAINER, p_i50066_1_);
        checkContainerSize(p_i50066_3_, 16);
        this.inventory = p_i50066_3_;
        p_i50066_3_.startOpen(playerInventoryIn.player);

        for(int k = 0; k < 2; ++k) {
            for(int l = 0; l < 8; ++l) {
                this.addSlot(new Slot(p_i50066_3_, l + k * 8, 17 + l * 18, 18 + k * 18){
                    /**
                     * Check if the stack is allowed to be placed in this slot, used for armor slots as well as furnace fuel.
                     */
                    public boolean mayPlace(ItemStack stack) { return ((stack.getItem() instanceof EnhancedEgg) || stack.getItem() == Items.TURTLE_EGG || stack.getItem() == ModItems.TURTLE_EGG_ITEM.get()); }

                    public int getMaxStackSize() {
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

    public boolean stillValid(Player playerIn) {
        return this.inventory.stillValid(playerIn);
    }

    /**
     * Handle when the stack in slot {@code index} is shift-clicked. Normally this moves the stack between the player
     * inventory and the other inventory(s).
     */
    public ItemStack quickMoveStack(Player playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            if (index < this.inventory.getContainerSize()) {
                if (!this.moveItemStackTo(itemstack1, this.inventory.getContainerSize(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(itemstack1, 0, this.inventory.getContainerSize(), false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }

        return itemstack;
    }

    public Container getEggCartonInventory() {
        return this.inventory;
    }

    /**
     * Called when the container is closed.
     */
    public void removed(Player playerIn) {
        super.removed(playerIn);
        this.inventory.stopOpen(playerIn);
    }
}
