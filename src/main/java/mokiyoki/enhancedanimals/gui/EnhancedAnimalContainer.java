package mokiyoki.enhancedanimals.gui;

import mokiyoki.enhancedanimals.entity.EnhancedAnimalAbstract;
import mokiyoki.enhancedanimals.items.CustomizableBridle;
import mokiyoki.enhancedanimals.items.CustomizableCollar;
import mokiyoki.enhancedanimals.items.CustomizableSaddleEnglish;
import mokiyoki.enhancedanimals.items.CustomizableSaddleVanilla;
import mokiyoki.enhancedanimals.items.CustomizableSaddleWestern;
import mokiyoki.enhancedanimals.util.EnhancedAnimalInfo;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import static mokiyoki.enhancedanimals.util.handlers.EventRegistry.ENHANCED_ANIMAL_CONTAINER;

public class EnhancedAnimalContainer extends AbstractContainerMenu {
    private Container inventory;
    public EnhancedAnimalAbstract enhancedAnimal;
    public EnhancedAnimalInfo animalInfo;
    public int numberOfEquipmentSlots = 0;
    public int totalNumberOfAnimalSlots = 0;

    public EnhancedAnimalContainer(int p_i50066_1_, Inventory playerInventoryIn, EnhancedAnimalAbstract enhancedAnimal, EnhancedAnimalInfo animalInfo) {
        super(ENHANCED_ANIMAL_CONTAINER, p_i50066_1_);
        SimpleContainer retrievedInventory = enhancedAnimal.getEnhancedInventory();
        this.enhancedAnimal = enhancedAnimal;
        this.animalInfo = animalInfo;
        this.inventory = retrievedInventory;
        retrievedInventory.startOpen(playerInventoryIn.player);
        int i = 3; // inv height
        int j = 5; // inv width
        int xShift = 8;
        int yShift = 18;
        boolean hasEquipment = false;

        if (enhancedAnimal.canHaveSaddle()) {
            hasEquipment = true;
            this.addSlot(new Slot(retrievedInventory, 1, xShift, yShift) {

                public boolean mayPlace(ItemStack stack) {
                    Item item = stack.getItem();
                    return checkSaddle(item) || collarCheck(item, 1);
                }

                public int getMaxStackSize() {
                    return 1;
                }

            });
            yShift = yShift + 18;
            numberOfEquipmentSlots++;
        }

        if (enhancedAnimal.canHaveBridle()) {
            hasEquipment = true;
            this.addSlot(new Slot(retrievedInventory, 3, xShift, yShift) {

                public boolean mayPlace(ItemStack stack) {
                    return checkBridle(stack.getItem()) || collarCheck(stack.getItem(), 3);
                }

                public int getMaxStackSize() {
                    return 1;
                }

            });
            yShift = yShift + 18;
            numberOfEquipmentSlots++;
        }

        if (enhancedAnimal.canHaveArmour()) {
            hasEquipment = true;
            this.addSlot(new Slot(retrievedInventory, 2, xShift, yShift) {

                public boolean mayPlace(ItemStack stack) {
                    return checkArmour(stack.getItem()) || collarCheck(stack.getItem(), 2);
                }

                public int getMaxStackSize() {
                    return 1;
                }

            });
            yShift = yShift + 18;
            if (yShift >= 54) {
                yShift = 18;
                xShift = 80;
            }
            numberOfEquipmentSlots++;
        }

        if (enhancedAnimal.canHaveBlanket()) {
            hasEquipment = true;
            this.addSlot(new Slot(retrievedInventory, 4, xShift, yShift) {

                public boolean mayPlace(ItemStack stack) {
                    return checkBlanket(stack.getItem()) || collarCheck(stack.getItem(), 4);
                }

                public int getMaxStackSize() {
                    return 1;
                }

            });
            yShift = yShift + 18;
            if (yShift >= 54) {
                yShift = 18;
                xShift = 80;
            }
            numberOfEquipmentSlots++;
        }

        if (enhancedAnimal.canHaveBanner()) {
            hasEquipment = true;
            this.addSlot(new Slot(retrievedInventory, 6, xShift, yShift) {

                public boolean mayPlace(ItemStack stack) {
                    return checkBanner(stack.getItem()) || collarCheck(stack.getItem(), 6);
                }

                public int getMaxStackSize() {
                    return 1;
                }

            });
            yShift = yShift + 18;
            if (yShift >= 54) {
                yShift = 18;
                xShift = 80;
            }
            numberOfEquipmentSlots++;
        }

        if (enhancedAnimal.canHaveHarness()) {
            hasEquipment = true;
            this.addSlot(new Slot(retrievedInventory, 5, xShift, yShift) {

                public boolean mayPlace(ItemStack stack) {
                    return collarCheck(stack.getItem(), 5);
                }

                public int getMaxStackSize() {
                    return 1;
                }

            });
            yShift = yShift + 18;
            if (yShift >= 54) {
                yShift = 18;
                xShift = 80;
            }
            numberOfEquipmentSlots++;
        }

        if (!hasEquipment) {
            this.addSlot(new Slot(retrievedInventory, 1, xShift, yShift) {

                public boolean mayPlace(ItemStack stack) {
                    return stack.getItem() instanceof CustomizableCollar;
                }

                public int getMaxStackSize() {
                    return 1;
                }

            });
            yShift = yShift + 18;
            numberOfEquipmentSlots++;
        }

        this.addSlot(new EnhancedSlot(retrievedInventory, 0, 116, 36) {
            public boolean mayPlace(ItemStack stack) {
                //TODO add option for item to be in forge:chests/wooden
                return stack.getItem() == Items.CHEST;
            }

            public int getMaxStackSize() {
            return 1; }
        });

        totalNumberOfAnimalSlots = numberOfEquipmentSlots;

        int inventoryShift = 7;
        if (enhancedAnimal.canHaveChest()) {
            int limit = enhancedAnimal.getInventorySize();
                for (int k = 0; k < i; ++k) {
                    if (inventoryShift >= limit) {
                        break;
                    }
                    for (int l = 0; l < j; ++l) {
                        if (inventoryShift >= limit) {
                            break;
                        }
                        this.addSlot(new EnhancedSlot(retrievedInventory, inventoryShift, 80 + (l * 18), 18 + (k * 18)) {
                        });
                        inventoryShift++;
                        totalNumberOfAnimalSlots++;
                    }
                }
        }

        //player inventory
        for(int i1 = 0; i1 < 3; ++i1) {
            for(int k1 = 0; k1 < 9; ++k1) {
                this.addSlot(new Slot(playerInventoryIn, k1 + i1 * 9 + 9, 8 + k1 * 18, 84 + i1 * 18) {
                    @Override
                    public boolean isActive() {
                        return !enhancedAnimal.isInPhotoMode;
                    }
                });
            }
        }

        //tool bar inventory
        for(int j1 = 0; j1 < 9; ++j1) {
            this.addSlot(new Slot(playerInventoryIn, j1, 8 + j1 * 18, 142){
                @Override
                public boolean isActive() {
                    return !enhancedAnimal.isInPhotoMode;
                }
            });
        }
    }

    public boolean isQuickTransferable(Item item) {
        if (item instanceof CustomizableCollar) {
            boolean canEquip = false;

            if (enhancedAnimal.canHaveSaddle()) {
                if (!equipmentSlotEmpty(1)) {
                    if (this.inventory.getItem(1).getItem() instanceof CustomizableCollar) {
                        return false;
                    }
                } else {
                    canEquip = true;
                }

            }
            if (enhancedAnimal.canHaveBridle()) {
                if (!equipmentSlotEmpty(3)) {
                    if (this.inventory.getItem(3).getItem() instanceof CustomizableCollar) {
                        return false;
                    }
                } else {
                    canEquip = true;
                }
            }

            if (enhancedAnimal.canHaveArmour()) {
                if (!equipmentSlotEmpty(2)) {
                    if (this.inventory.getItem(2).getItem() instanceof CustomizableCollar) {
                        return false;
                    }
                } else {
                    canEquip = true;
                }
            }

            if (enhancedAnimal.canHaveBlanket() ) {
                if (!equipmentSlotEmpty(4)) {
                    if (this.inventory.getItem(4).getItem() instanceof CustomizableCollar) {
                        return false;
                    }
                } else {
                    canEquip = true;
                }
            }

            if (enhancedAnimal.canHaveBanner()) {
                if (!equipmentSlotEmpty(6)) {
                    if (this.inventory.getItem(6).getItem() instanceof CustomizableCollar) {
                        return false;
                    }
                } else {
                    canEquip = true;
                }
            }

            if (enhancedAnimal.canHaveHarness()) {
                if (!equipmentSlotEmpty(5)) {
                    if (this.inventory.getItem(5).getItem() instanceof CustomizableCollar) {
                        return false;
                    }
                } else {
                    canEquip = true;
                }
            }

            return canEquip;
        }

        if (enhancedAnimal.canHaveSaddle()) {
            if (checkSaddle(item) && equipmentSlotEmpty(1)) return true;
        }
        if (enhancedAnimal.canHaveBridle()) {
            if (checkBridle(item) && equipmentSlotEmpty(3)) return true;
        }

        if (enhancedAnimal.canHaveArmour()) {
            if (checkArmour(item) && equipmentSlotEmpty(2)) return true;
        }

        if (enhancedAnimal.canHaveBlanket() ) {
            if (checkBlanket(item) && equipmentSlotEmpty(4)) return true;
        }

        if (enhancedAnimal.canHaveBanner()) {
            if (checkBanner(item) && equipmentSlotEmpty(6)) return true;
        }

        if (enhancedAnimal.canHaveHarness()) {
            if (checkHarness(item) &&  equipmentSlotEmpty(5)) return true;
        }

        return false;

    }

    private boolean equipmentSlotEmpty(int slotIndex) {
        return this.inventory.getItem(slotIndex).getItem() == Items.AIR;
    }

    private boolean checkSaddle(Item item) {
        return item == Items.SADDLE || item instanceof CustomizableSaddleVanilla || item instanceof CustomizableSaddleWestern ||
                item instanceof CustomizableSaddleEnglish;
    }

    private boolean checkBridle(Item item) {
        return item instanceof CustomizableBridle;
    }

    private boolean checkArmour(Item item) {
        return item == Items.LEATHER_HORSE_ARMOR || item == Items.IRON_HORSE_ARMOR || item == Items.GOLDEN_HORSE_ARMOR ||
                item == Items.DIAMOND_HORSE_ARMOR;
    }

    private boolean checkBlanket(Item item) {
        return item == Items.BLACK_CARPET || item == Items.WHITE_CARPET || item == Items.BLUE_CARPET ||
                item == Items.BROWN_CARPET || item == Items.CYAN_CARPET || item == Items.GRAY_CARPET ||
                item == Items.GREEN_CARPET || item == Items.LIGHT_BLUE_CARPET || item == Items.LIGHT_GRAY_CARPET ||
                item == Items.LIME_CARPET || item == Items.MAGENTA_CARPET || item == Items.ORANGE_CARPET ||
                item == Items.PINK_CARPET || item == Items.PURPLE_CARPET || item == Items.RED_CARPET ||
                item == Items.YELLOW_CARPET;
    }

    private boolean checkBanner(Item item) {
        return item == Items.BLACK_BANNER || item == Items.WHITE_BANNER || item == Items.BLUE_BANNER ||
                item == Items.BROWN_BANNER || item == Items.CYAN_BANNER || item == Items.GRAY_BANNER ||
                item == Items.GREEN_BANNER || item == Items.LIGHT_BLUE_BANNER || item == Items.LIGHT_GRAY_BANNER ||
                item == Items.LIME_BANNER || item == Items.MAGENTA_BANNER || item == Items.ORANGE_BANNER ||
                item == Items.PINK_BANNER || item == Items.PURPLE_BANNER || item == Items.RED_BANNER ||
                item == Items.YELLOW_BANNER;
    }

    public boolean stillValid(Player playerIn) {
        return this.inventory.stillValid(playerIn);
    }

    private boolean checkHarness(Item item) {
        return false;
    }

    public boolean collarCheck(Item item, int thisSlot) {
        if (item instanceof CustomizableCollar) {
            for (int i = 1; i <= 6; i++) {
                if (i != thisSlot && getEnhancedAnimalInventory().getItem(i).getItem() instanceof CustomizableCollar) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public ItemStack quickMoveStack(Player playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            if (index < totalNumberOfAnimalSlots) {
                if (!this.moveItemStackTo(itemstack1, this.inventory.getContainerSize()-numberOfEquipmentSlots, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(itemstack1, 0, this.inventory.getContainerSize()-numberOfEquipmentSlots, false)) {
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

    public Container getEnhancedAnimalInventory() {
        return this.inventory;
    }

    public void removed(Player playerIn) {
        super.removed(playerIn);
        this.inventory.stopOpen(playerIn);
    }

    public EnhancedAnimalAbstract getAnimal() {
        return enhancedAnimal;
    }
}
