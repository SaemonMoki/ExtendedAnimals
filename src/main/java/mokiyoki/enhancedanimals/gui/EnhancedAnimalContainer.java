package mokiyoki.enhancedanimals.gui;

import mokiyoki.enhancedanimals.entity.EnhancedAnimal;
import mokiyoki.enhancedanimals.entity.EnhancedAnimalAbstract;
import mokiyoki.enhancedanimals.init.ModItems;
import mokiyoki.enhancedanimals.util.EnhancedAnimalInfo;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

import static mokiyoki.enhancedanimals.util.handlers.EventRegistry.ENHANCED_ANIMAL_CONTAINER;

public class EnhancedAnimalContainer extends Container {
    private IInventory inventory;
    public EnhancedAnimalAbstract enhancedAnimal;
    public EnhancedAnimalInfo animalInfo;

    public EnhancedAnimalContainer(int p_i50066_1_, PlayerInventory playerInventoryIn, EnhancedAnimalAbstract enhancedAnimal, EnhancedAnimalInfo animalInfo) {
        super(ENHANCED_ANIMAL_CONTAINER, p_i50066_1_);
        Inventory retrievedInventory = enhancedAnimal.getEnhancedInventory();
        this.enhancedAnimal = enhancedAnimal;
        this.animalInfo = animalInfo;
        this.inventory = retrievedInventory;
        retrievedInventory.openInventory(playerInventoryIn.player);
        int i = 3; // inv height
        int j = 5; // inv width
        int equipmentShift = 1;
        int xShift = 8;
        int yShift = 18;

        if (enhancedAnimal.canHaveSaddle()) {
            this.addSlot(new Slot(retrievedInventory, equipmentShift, xShift, yShift) {

                public boolean isItemValid(ItemStack stack) {
                    return stack.getItem() == Items.SADDLE || stack.getItem() == ModItems.BASIC_LEATHER_SADDLE || stack.getItem() == ModItems.BASIC_CLOTH_SADDLE;
                }

                public int getSlotStackLimit() {
                    return 1;
                }

            });
            equipmentShift = equipmentShift + 1;
            yShift = yShift + 18;
        }

        if (enhancedAnimal.canHaveBridle()) {
            this.addSlot(new Slot(retrievedInventory, equipmentShift, xShift, yShift) {

                public boolean isItemValid(ItemStack stack) {
                    return stack.getItem() == ModItems.BASIC_LEATHER_BRIDLE || stack.getItem() == ModItems.BASIC_CLOTH_BRIDLE;
                }

                public int getSlotStackLimit() {
                    return 1;
                }

            });
            equipmentShift = equipmentShift + 1;
            yShift = yShift + 18;
        }

        if (enhancedAnimal.canHaveArmour()) {
            this.addSlot(new Slot(retrievedInventory, equipmentShift, xShift, yShift) {

                public boolean isItemValid(ItemStack stack) {
                    return stack.getItem() == Items.LEATHER_HORSE_ARMOR || stack.getItem() == Items.IRON_HORSE_ARMOR || stack.getItem() == Items.GOLDEN_HORSE_ARMOR || stack.getItem() == Items.DIAMOND_HORSE_ARMOR;
                }

                public int getSlotStackLimit() {
                    return 1;
                }

            });
            equipmentShift = equipmentShift + 1;
            yShift = yShift + 18;
            if (yShift >= 54) {
                yShift = 18;
                xShift = 80;
            }
        }

        if (enhancedAnimal.canHaveBlanket()) {
            this.addSlot(new Slot(retrievedInventory, equipmentShift, xShift, yShift) {

                public boolean isItemValid(ItemStack stack) {
                    return stack.getItem() == Items.BLACK_CARPET || stack.getItem() == Items.WHITE_CARPET || stack.getItem() == Items.BLUE_CARPET || stack.getItem() == Items.BROWN_CARPET || stack.getItem() == Items.CYAN_CARPET || stack.getItem() == Items.GRAY_CARPET || stack.getItem() == Items.GREEN_CARPET || stack.getItem() == Items.LIGHT_BLUE_CARPET || stack.getItem() == Items.LIGHT_GRAY_CARPET || stack.getItem() == Items.LIME_CARPET || stack.getItem() == Items.MAGENTA_CARPET || stack.getItem() == Items.ORANGE_CARPET || stack.getItem() == Items.PINK_CARPET || stack.getItem() == Items.PURPLE_CARPET || stack.getItem() == Items.RED_CARPET || stack.getItem() == Items.YELLOW_CARPET;
                }

                public int getSlotStackLimit() {
                    return 1;
                }

            });
            equipmentShift = equipmentShift + 1;
            yShift = yShift + 18;
            if (yShift >= 54) {
                yShift = 18;
                xShift = 80;
            }
        }

        if (enhancedAnimal.canHaveBanner()) {
            this.addSlot(new Slot(retrievedInventory, equipmentShift, xShift, yShift) {

                public boolean isItemValid(ItemStack stack) {
                    return stack.getItem() == Items.BLACK_BANNER || stack.getItem() == Items.WHITE_BANNER || stack.getItem() == Items.BLUE_BANNER || stack.getItem() == Items.BROWN_BANNER || stack.getItem() == Items.CYAN_BANNER || stack.getItem() == Items.GRAY_BANNER || stack.getItem() == Items.GREEN_BANNER || stack.getItem() == Items.LIGHT_BLUE_BANNER || stack.getItem() == Items.LIGHT_GRAY_BANNER || stack.getItem() == Items.LIME_BANNER || stack.getItem() == Items.MAGENTA_BANNER || stack.getItem() == Items.ORANGE_BANNER || stack.getItem() == Items.PINK_BANNER || stack.getItem() == Items.PURPLE_BANNER || stack.getItem() == Items.RED_BANNER || stack.getItem() == Items.YELLOW_BANNER;
                }

                public int getSlotStackLimit() {
                    return 1;
                }

            });
            equipmentShift = equipmentShift + 1;
            yShift = yShift + 18;
            if (yShift >= 54) {
                yShift = 18;
                xShift = 80;
            }
        }

        if (enhancedAnimal.canHaveHarness()) {
            this.addSlot(new Slot(retrievedInventory, equipmentShift, xShift, yShift) {

                public boolean isItemValid(ItemStack stack) {
                    return stack.getItem() == Items.SADDLE;
                }

                public int getSlotStackLimit() {
                    return 1;
                }

            });
            equipmentShift = equipmentShift + 1;
            yShift = yShift + 18;
            if (yShift >= 54) {
                yShift = 18;
                xShift = 80;
            }
        }

        if (enhancedAnimal.canHaveChest()) {
            if (retrievedInventory.getStackInSlot(0).getItem() == Items.CHEST) {
                for (int k = 0; k < i; ++k) {
                    for (int l = 0; l < j; ++l) {
                        this.addSlot(new Slot(retrievedInventory, equipmentShift, 80 + (l * 18), 18 + (k * 18)) {

        //                    public int getSlotStackLimit() {
        //                        return 64;
        //                    }
                        });
                        equipmentShift++;
                    }
                }
            }
        }

        //player inventory
        for(int i1 = 0; i1 < 3; ++i1) {
            for(int k1 = 0; k1 < 9; ++k1) {
                this.addSlot(new Slot(playerInventoryIn, k1 + i1 * 9 + 9, 8 + k1 * 18, 84 + i1 * 18));
            }
        }

        //tool bar inventory
        for(int j1 = 0; j1 < 9; ++j1) {
            this.addSlot(new Slot(playerInventoryIn, j1, 8 + j1 * 18, 142));
        }
        int testBreakpointint = 0;
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

    public void onContainerClosed(PlayerEntity playerIn) {
        super.onContainerClosed(playerIn);
        this.inventory.closeInventory(playerIn);
    }

    public EnhancedAnimal getAnimal() {
        return enhancedAnimal;
    }
}
