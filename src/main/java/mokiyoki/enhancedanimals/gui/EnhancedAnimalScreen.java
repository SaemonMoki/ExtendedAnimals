package mokiyoki.enhancedanimals.gui;

import com.mojang.blaze3d.platform.GlStateManager;
import mokiyoki.enhancedanimals.items.CustomizableCollar;
import mokiyoki.enhancedanimals.util.EnhancedAnimalInfo;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.screen.inventory.InventoryScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class EnhancedAnimalScreen extends ContainerScreen<EnhancedAnimalContainer> {
    private static final ResourceLocation GUI_TEXTURE = new ResourceLocation("eanimod:textures/gui/genetic_animal_gui.png");
    private IInventory inventory;
    /** The mouse x-position recorded during the last rendered frame. */
    private float mousePosx;
    /** The mouse y-position recorded during the last renderered frame. */
    private float mousePosY;

    /** temp booleans */
    boolean chestTabEnabled = false;

    public static EnhancedAnimalInfo enhancedAnimalInfo = new EnhancedAnimalInfo();

    public EnhancedAnimalScreen(EnhancedAnimalContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);
        setAnimalInfo();
        if (this.getContainer().getEnhancedAnimalInventory().getStackInSlot(0).getItem() == Items.CHEST) {
            chestTabEnabled = true;
        }
        toggleSlots();
    }

    public void render(int mouseX, int mouseY, float p_render_3_) {
        if (!enhancedAnimalInfo.created) {setAnimalInfo();}
        toggleSlots();
        this.renderBackground();
        this.mousePosx = (float)mouseX;
        this.mousePosY = (float)mouseY;
        super.render(mouseX, mouseY, p_render_3_);
        this.renderHoveredToolTip(mouseX, mouseY);
        this.renderInfoToolTip(mouseX, mouseY);
    }

    private void renderInfoToolTip(int mouseX, int mouseY) {
        if (this.isPointInRegion(127, 5, 7, 9, (double)mouseX, (double)mouseY)) {
            if (this.enhancedAnimalInfo.pregnant > 0) {
                this.renderTooltip(I18n.format("eanimod.animalinfocontainer.pregnant"), mouseX, mouseY);
            }
            if (this.enhancedAnimalInfo.isFemale) {
                this.renderTooltip(I18n.format("eanimod.animalinfocontainer.female"), mouseX, mouseY);
            } else {
                this.renderTooltip(I18n.format("eanimod.animalinfocontainer.male"), mouseX, mouseY);
            }
        }
        if (this.isPointInRegion(136, 5, 8, 9, (double)mouseX, (double)mouseY)) {
            this.renderTooltip(I18n.format("eanimod.animalinfocontainer.health"), mouseX, mouseY);
        }
        if (this.isPointInRegion(147, 5, 7, 9, (double)mouseX, (double)mouseY)) {
            this.renderTooltip(I18n.format("eanimod.animalinfocontainer.hunger"), mouseX, mouseY);
        }
    }

    protected void handleMouseClick(Slot slotIn, int slotId, int mouseButton, ClickType type) {
        if (type == ClickType.QUICK_MOVE && slotIn != null && slotIn.getHasStack()) {
            Item itemIn = slotIn.getStack().getItem();
            Item chestInSlot = this.container.getEnhancedAnimalInventory().getStackInSlot(0).getItem();

            boolean hasChestEquipped = chestInSlot == Items.CHEST;
            boolean slotIDIsAnEquipmentSlot = slotId <= this.container.numberOfEquipmentSlots;

            if (slotIDIsAnEquipmentSlot) {

            } else if (!hasChestEquipped && !(itemIn == Items.CHEST) && !this.container.isQuickTransferable(itemIn)) {
                return;
            }
        }

        super.handleMouseClick(slotIn, slotId, mouseButton, type);
    }

    public boolean mouseClicked(double p_mouseClicked_1_, double p_mouseClicked_3_, int p_mouseClicked_5_) {
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;

        double d0 = p_mouseClicked_1_ - (double)(i + 176);
        double d1 = p_mouseClicked_3_ - (double)(j + 42);
        if (d0 >= 0.0D && d1 >= 0.0D && d0 < 27.0D && d1 < 27.0D && chestTabEnabled) {
            this.chestTabEnabled = false;
            toggleSlots();
            return true;
        }

        d0 = p_mouseClicked_1_ - (double)(i + 176);
        d1 = p_mouseClicked_3_ - (double)(j + 17);
        if (d0 >= 0.0D && d1 >= 0.0D && d0 < 27.0D && d1 < 27.0D && !chestTabEnabled) {
            this.chestTabEnabled = true;
            toggleSlots();
            return true;
        }

        return super.mouseClicked(p_mouseClicked_1_, p_mouseClicked_3_, p_mouseClicked_5_);
    }

    private void toggleSlots() {
        IInventory retrievedInventory = this.container.getEnhancedAnimalInventory();
        for (Slot slot : this.container.inventorySlots) {
            if (slot instanceof EnhancedSlot) {
                if (!chestTabEnabled) {
                    ((EnhancedSlot)slot).setEnabled(false);
                } else {
                    if (slot.getSlotIndex() == 0){
                        if (!isHasItemsInChest(this.container.getEnhancedAnimalInventory())){
                            ((EnhancedSlot)slot).setEnabled(true);
                        } else {
                            ((EnhancedSlot)slot).setEnabled(false);
                        }
                    } else {
                        if (slot.getSlotIndex() == 14) {
                            if (isHasItemsInChest(this.container.getEnhancedAnimalInventory())) {
                                ((EnhancedSlot)slot).setEnabled(true);
                            } else {
                                ((EnhancedSlot)slot).setEnabled(false);
                            }
                        } else {
                            if (retrievedInventory.getStackInSlot(0).getItem() == Items.CHEST) {
                                ((EnhancedSlot)slot).setEnabled(true);
                            } else {
                                ((EnhancedSlot)slot).setEnabled(false);
                            }
                        }
                    }
                }
            }
        }
    }


    /**
     * Draw the foreground layer for the GuiContainer (everything in front of the items)
     */
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        IInventory retrievedInventory = this.container.getEnhancedAnimalInventory();
        int collarSlot = getCollarSlot(retrievedInventory);
        String name = enhancedAnimalInfo.name;

        if (collarSlot != 0) {
            ItemStack collarStack = retrievedInventory.getStackInSlot(collarSlot);
            String collarName = ((CustomizableCollar)collarStack.getItem()).getCollarName(collarStack);
            if (!collarName.equals("")) {
                name = collarName;
            }
        }

        if (!enhancedAnimalInfo.agePrefix.equals("ADULT")) {
            name = enhancedAnimalInfo.agePrefix+name;
        }

        this.font.drawString(name, 8.0F, 6.0F, 4210752);
        this.font.drawString(this.playerInventory.getDisplayName().getFormattedText(), 8.0F, (float)(this.ySize - 96 + 2), 4210752);

        //(health points / max health points * 10) + "/" + "10"
        /**
         * hunger needs a buffer
         */
        //(hunger points / max hunger points * 10) + "/" + "10"
        /**
         * taming points are out of how much an animal can be tamed.
         */
        //(taming level / 10 )
        //birth parent (the mother basically)
        //contributing parent (the father basically)
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bindTexture(GUI_TEXTURE);
        IInventory retrievedInventory = this.container.getEnhancedAnimalInventory();
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        int invSize = 5;
        int shiftY = 17;
        int shiftX = 7;
        this.blit(i, j, 0, 0, this.xSize, this.ySize);

        /**
         *  screenPlacementFromX : X coordinate of where you want the top left corner of the image to be placed on the screen in game. start with int i and + || - what you need to place it.
         *  screenPlacementFromY : Y coordinate of where you want the top left corner of the image to be placed on the screen in game. start with int j and + || - what you need to place it.
         *
         *  selectImageFromX : X coordinate of where the left most pixel of the image you want in the .png is.
         *  selectImageFromY : Y coordinate of where the top most pixel of the image you want in the .png is.
         *
         *  imageSizeX : how big in pixels the image you are placing is from left to right.
         *  imageSizeY : how big in pixels the image you are placing is from top to bottom.
         *
         *  this.blit(i + screenPlacementFromX, j + screenPlacementFromY, selectImageFromX, selectImageFromY, imageSizeX, imageSizeY)
         */

        if (enhancedAnimalInfo.isFemale) {
            this.blit(i + 126, j + 5, 117, this.ySize + 54, 8, 10); // female icon
                int pregnancy = enhancedAnimalInfo.pregnant;
            this.blit(i + 126, j + 5 + (10-pregnancy) , 117, this.ySize + 64 + (10-pregnancy), 8, pregnancy); // female icon

        } else {
            this.blit(i + 126, j + 5, 108, this.ySize + 54, 8, 10); // male icon
                int pregnancy = enhancedAnimalInfo.pregnant;
            this.blit(i + 126, j + 5 + (10-pregnancy) , 108, this.ySize + 64 + (10-pregnancy), 8, pregnancy); // female icon
        }

        this.blit(i + 136, j + 5, 125, this.ySize + 54, 9, 10); // health icon
        this.blit(i + 147, j + 5, 134, this.ySize + 54, 9, 10); // hunger icon
        this.blit(i + 158, j + 5, 143, this.ySize + 54, 10, 10); // tameness icon

        int health = enhancedAnimalInfo.health;
        int hunger = 10 - enhancedAnimalInfo.hunger;
//        int tameness = enhancedAnimalInfo.tameness;
        this.blit(i + 136, j + 5 + (10-health), 125, this.ySize + 64 + (10-health), 9, health); // health icon
        this.blit(i + 147, j + 5 + (10-hunger), 134, this.ySize + 64 + (10-hunger), 9, hunger); // hunger icon
//        this.blit(i + 158, j + 5 + (10-tameness), 143, this.ySize + 64 + (10-tameness), 10, tameness); // tameness icon

        if (this.container.enhancedAnimal.canHaveSaddle()) {
            if (retrievedInventory.getStackInSlot(1).isEmpty()) {
                this.blit(i + shiftX, j + shiftY, 0, this.ySize + 54, 18, 18);
            } else {
                this.blit(i + shiftX, j + shiftY, 54, this.ySize + 36, 18, 18);
            }
            shiftY = shiftY + 18;
        }
        if (this.container.enhancedAnimal.canHaveBridle()) {
            if (retrievedInventory.getStackInSlot(3).isEmpty()) {
                this.blit(i + shiftX, j + shiftY, 18, this.ySize + 54, 18, 18);
            } else {
                this.blit(i + shiftX, j + shiftY, 54, this.ySize + 36, 18, 18);
            }
            shiftY = shiftY + 18;
        }
        if (this.container.enhancedAnimal.canHaveArmour()) {
            if (retrievedInventory.getStackInSlot(2).isEmpty()) {
                this.blit(i + shiftX, j + shiftY, 36, this.ySize + 54, 18, 18);
            } else {
                this.blit(i + shiftX, j + shiftY, 54, this.ySize + 36, 18, 18);
            }
            shiftY = shiftY + 18;
            if (shiftY >= 69) {
                shiftY = 17;
                shiftX = 79;
            }
        }
        if (this.container.enhancedAnimal.canHaveBlanket() && (shiftX == 7 || !this.chestTabEnabled)) {
            if (retrievedInventory.getStackInSlot(4).isEmpty()) {
                this.blit(i + shiftX, j + shiftY, 54, this.ySize + 54, 18, 18);
            } else {
                this.blit(i + shiftX, j + shiftY, 54, this.ySize + 36, 18, 18);
            }
            shiftY = shiftY + 18;
            if (shiftY >= 69) {
                shiftY = 17;
                shiftX = 79;
            }
        }
        if (this.container.enhancedAnimal.canHaveBanner() && (shiftX == 7 || !this.chestTabEnabled)) {
            if (retrievedInventory.getStackInSlot(6).isEmpty()) {
                this.blit(i + shiftX, j + shiftY, 72, this.ySize + 54, 18, 18);
            } else {
                this.blit(i + shiftX, j + shiftY, 54, this.ySize + 36, 18, 18);
            }
            shiftY = shiftY + 18;
            if (shiftY >= 69) {
                shiftY = 17;
                shiftX = 79;
            }
        }
        if (this.container.enhancedAnimal.canHaveHarness() && (shiftX == 7 || !this.chestTabEnabled)) {
            if (retrievedInventory.getStackInSlot(5).isEmpty()) {
                this.blit(i + shiftX, j + shiftY, 90, this.ySize + 54, 18, 18);
            } else {
                this.blit(i + shiftX, j + shiftY, 54, this.ySize + 36, 18, 18);
            }
            shiftY = shiftY + 18;
            if (shiftY >= 69) {
                shiftY = 17;
                shiftX = 79;
            }
        }

        if (shiftY==17 && shiftX==7) {
            this.blit(i + 7, j + 17, 0, this.ySize + 72, 18, 18);
        }

        boolean hasItemsInChest = isHasItemsInChest(retrievedInventory);

        if (this.container.enhancedAnimal.canHaveChest()) {
            if (this.chestTabEnabled) {
                this.blit(i + 173, j + 13, 209, 16, 31, 28);
                this.blit(i + 173, j + 41, 177, 44, 30, 28);  //broken image... no idea why or how
                if (retrievedInventory.getStackInSlot(0).getItem() == Items.CHEST) {
                    if (hasItemsInChest) {
                        this.blit(i + 79, j + 17, 0, this.ySize, 18*invSize, 54);
                    } else {
                        this.blit(i + 79, j + 17, 90, this.ySize, 90, 54);
//                        this.blit(i + 112, j + 31, 180, this.ySize, 24, 26);
                    }
                } else {
                    this.blit(i + 79, j + 17, 90, this.ySize, 90, 54);
                }
            } else {
                this.blit(i + 173, j + 13, 177, 16, 30, 28);
                this.blit(i + 173, j + 41, 209, 44, 31, 28);
            }
        }

        if (this.enhancedAnimalInfo.created) {
            InventoryScreen.drawEntityOnScreen(i + 51, j + 60, 17, (float)(i + 51) - this.mousePosx, (float)(j + 75 - 50) - this.mousePosY, (LivingEntity) this.container.getAnimal());
        }
    }

    private boolean isHasItemsInChest(IInventory retrievedInventory) {
        for (int a = 7; a <= retrievedInventory.getSizeInventory(); a++) {
            if (!retrievedInventory.getStackInSlot(a).isEmpty() && !(retrievedInventory.getStackInSlot(a).getItem() == Items.AIR)) {
                return true;
            }
        }
        return false;
    }

    private void setAnimalInfo() {
        this.enhancedAnimalInfo = this.container.animalInfo;
    }

    private int getCollarSlot(IInventory retrievedInventory) {
        for (int i = 1; i <= 6; i++) {
            if (retrievedInventory.getStackInSlot(i).getItem() instanceof CustomizableCollar) {
                return i;
            }
        }
        return 0;
    }

}
