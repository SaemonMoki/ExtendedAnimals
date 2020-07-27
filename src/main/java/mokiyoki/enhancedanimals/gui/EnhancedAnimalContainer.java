package mokiyoki.enhancedanimals.gui;

import mokiyoki.enhancedanimals.entity.EnhancedAnimal;
import mokiyoki.enhancedanimals.entity.EnhancedAnimalAbstract;
import mokiyoki.enhancedanimals.items.CustomizableBridle;
import mokiyoki.enhancedanimals.items.CustomizableCollar;
import mokiyoki.enhancedanimals.items.CustomizableSaddleEnglish;
import mokiyoki.enhancedanimals.items.CustomizableSaddleVanilla;
import mokiyoki.enhancedanimals.items.CustomizableSaddleWestern;
import mokiyoki.enhancedanimals.util.EnhancedAnimalInfo;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.Item;
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
        int xShift = 8;
        int yShift = 18;
        boolean hasEquipment = false;

        if (enhancedAnimal.canHaveSaddle()) {
            hasEquipment = true;
            this.addSlot(new Slot(retrievedInventory, 1, xShift, yShift) {

                public boolean isItemValid(ItemStack stack) {
                    Item saddle = stack.getItem();
                    return saddle == Items.SADDLE || saddle instanceof CustomizableSaddleVanilla || saddle instanceof CustomizableSaddleWestern ||
                            saddle instanceof CustomizableSaddleEnglish || collarCheck(stack.getItem(), 1);
                }

                public int getSlotStackLimit() {
                    return 1;
                }

            });
            yShift = yShift + 18;
        }

        if (enhancedAnimal.canHaveBridle()) {
            hasEquipment = true;
            this.addSlot(new Slot(retrievedInventory, 3, xShift, yShift) {

                public boolean isItemValid(ItemStack stack) {
                    return stack.getItem() instanceof CustomizableBridle || collarCheck(stack.getItem(), 3);
                }

                public int getSlotStackLimit() {
                    return 1;
                }

            });
            yShift = yShift + 18;
        }

        if (enhancedAnimal.canHaveArmour()) {
            hasEquipment = true;
            this.addSlot(new Slot(retrievedInventory, 2, xShift, yShift) {

                public boolean isItemValid(ItemStack stack) {
                    return stack.getItem() == Items.LEATHER_HORSE_ARMOR || stack.getItem() == Items.IRON_HORSE_ARMOR || stack.getItem() == Items.GOLDEN_HORSE_ARMOR ||
                            stack.getItem() == Items.DIAMOND_HORSE_ARMOR || collarCheck(stack.getItem(), 2);
                }

                public int getSlotStackLimit() {
                    return 1;
                }

            });
            yShift = yShift + 18;
            if (yShift >= 54) {
                yShift = 18;
                xShift = 80;
            }
        }

        if (enhancedAnimal.canHaveBlanket()) {
            hasEquipment = true;
            this.addSlot(new Slot(retrievedInventory, 4, xShift, yShift) {

                public boolean isItemValid(ItemStack stack) {
                    return stack.getItem() == Items.BLACK_CARPET || stack.getItem() == Items.WHITE_CARPET || stack.getItem() == Items.BLUE_CARPET ||
                            stack.getItem() == Items.BROWN_CARPET || stack.getItem() == Items.CYAN_CARPET || stack.getItem() == Items.GRAY_CARPET ||
                            stack.getItem() == Items.GREEN_CARPET || stack.getItem() == Items.LIGHT_BLUE_CARPET || stack.getItem() == Items.LIGHT_GRAY_CARPET ||
                            stack.getItem() == Items.LIME_CARPET || stack.getItem() == Items.MAGENTA_CARPET || stack.getItem() == Items.ORANGE_CARPET ||
                            stack.getItem() == Items.PINK_CARPET || stack.getItem() == Items.PURPLE_CARPET || stack.getItem() == Items.RED_CARPET ||
                            stack.getItem() == Items.YELLOW_CARPET || collarCheck(stack.getItem(), 4);
                }

                public int getSlotStackLimit() {
                    return 1;
                }

            });
            yShift = yShift + 18;
            if (yShift >= 54) {
                yShift = 18;
                xShift = 80;
            }
        }

        if (enhancedAnimal.canHaveBanner()) {
            hasEquipment = true;
            this.addSlot(new Slot(retrievedInventory, 6, xShift, yShift) {

                public boolean isItemValid(ItemStack stack) {
                    return stack.getItem() == Items.BLACK_BANNER || stack.getItem() == Items.WHITE_BANNER || stack.getItem() == Items.BLUE_BANNER ||
                            stack.getItem() == Items.BROWN_BANNER || stack.getItem() == Items.CYAN_BANNER || stack.getItem() == Items.GRAY_BANNER ||
                            stack.getItem() == Items.GREEN_BANNER || stack.getItem() == Items.LIGHT_BLUE_BANNER || stack.getItem() == Items.LIGHT_GRAY_BANNER ||
                            stack.getItem() == Items.LIME_BANNER || stack.getItem() == Items.MAGENTA_BANNER || stack.getItem() == Items.ORANGE_BANNER ||
                            stack.getItem() == Items.PINK_BANNER || stack.getItem() == Items.PURPLE_BANNER || stack.getItem() == Items.RED_BANNER ||
                            stack.getItem() == Items.YELLOW_BANNER || collarCheck(stack.getItem(), 6);
                }

                public int getSlotStackLimit() {
                    return 1;
                }

            });
            yShift = yShift + 18;
            if (yShift >= 54) {
                yShift = 18;
                xShift = 80;
            }
        }

        if (enhancedAnimal.canHaveHarness()) {
            hasEquipment = true;
            this.addSlot(new Slot(retrievedInventory, 5, xShift, yShift) {

                public boolean isItemValid(ItemStack stack) {
                    return collarCheck(stack.getItem(), 5);
                }

                public int getSlotStackLimit() {
                    return 1;
                }

            });
            yShift = yShift + 18;
            if (yShift >= 54) {
                yShift = 18;
                xShift = 80;
            }
        }

        if (!hasEquipment) {
            this.addSlot(new Slot(retrievedInventory, 1, xShift, yShift) {

                public boolean isItemValid(ItemStack stack) {
                    return stack.getItem() instanceof CustomizableCollar;
                }

                public int getSlotStackLimit() {
                    return 1;
                }

            });
            yShift = yShift + 18;
        }

        if (enhancedAnimal.canHaveChest()) {
            if (retrievedInventory.getStackInSlot(0).getItem() == Items.CHEST) {
                for (int k = 0; k < i; ++k) {
                    for (int l = 0; l < j; ++l) {
                        this.addSlot(new Slot(retrievedInventory, 7, 80 + (l * 18), 18 + (k * 18)) {

        //                    public int getSlotStackLimit() {
        //                        return 64;
        //                    }
                        });
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

    public boolean collarCheck(Item item, int thisSlot) {
        if (item instanceof CustomizableCollar) {
            for (int i = 1; i <= 6; i++) {
                if (i != thisSlot && getEnhancedAnimalInventory().getStackInSlot(i).getItem() instanceof CustomizableCollar) {
                    return false;
                }
            }
            return true;
        }
        return false;
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
