package mokiyoki.enhancedanimals.gui;

import com.mojang.blaze3d.platform.GlStateManager;
import mokiyoki.enhancedanimals.config.EanimodCommonConfig;
import mokiyoki.enhancedanimals.items.CustomizableCollar;
import mokiyoki.enhancedanimals.util.EnhancedAnimalInfo;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.screen.inventory.InventoryScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;
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
    boolean chestToggle = false;

    public static EnhancedAnimalInfo enhancedAnimalInfo = new EnhancedAnimalInfo();

    public EnhancedAnimalScreen(EnhancedAnimalContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);
        setAnimalInfo();
        if (this.getContainer().getEnhancedAnimalInventory().getStackInSlot(0).getItem() == Items.CHEST) {
            chestToggle = true;
        }
        toggleSlots();
    }

    public void render(int mouseX, int mouseY, float p_render_3_) {
        if (!enhancedAnimalInfo.created) {setAnimalInfo();}
        prework();
        this.renderBackground();
        this.mousePosx = (float)mouseX;
        this.mousePosY = (float)mouseY;
        super.render(mouseX, mouseY, p_render_3_);
        this.renderHoveredToolTip(mouseX, mouseY);
        this.renderInfoToolTip(mouseX, mouseY);
    }

    private void prework() {
        IInventory retrievedInventory = this.container.getEnhancedAnimalInventory();

        if ((retrievedInventory.getStackInSlot(0).getItem() != Items.CHEST && this.chestToggle)
                || (retrievedInventory.getStackInSlot(0).getItem() == Items.CHEST && !this.chestToggle)) {
            toggleSlots();
        }
    }

    private void renderInfoToolTip(int mouseX, int mouseY) {
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        if (this.isPointInRegion(i+3, j-30, 5, 5, (double)mouseX, (double)mouseY)) {
            if (this.enhancedAnimalInfo.pregnant > 0) {
                this.renderTooltip(I18n.format("eanimod.animalinfocontainer.pregnant"), mouseX, mouseY);
            }
            if (this.enhancedAnimalInfo.isFemale) {
                this.renderTooltip(I18n.format("eanimod.animalinfocontainer.female"), mouseX, mouseY);
            } else {
                this.renderTooltip(I18n.format("eanimod.animalinfocontainer.male"), mouseX, mouseY);
            }
        }
        if (this.isPointInRegion(i+12, j-30, 7, 5, (double)mouseX, (double)mouseY)) {
            this.renderTooltip(I18n.format("eanimod.animalinfocontainer.health"), mouseX, mouseY);
        }
        if (this.isPointInRegion(i+24, j-30, 5, 5, (double)mouseX, (double)mouseY)) {
            this.renderTooltip(I18n.format("eanimod.animalinfocontainer.hunger"), mouseX, mouseY);
        }
    }

    public boolean mouseClicked(double p_mouseClicked_1_, double p_mouseClicked_3_, int p_mouseClicked_5_) {
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;

        double d0 = p_mouseClicked_1_ - (double)(i + 176);
        double d1 = p_mouseClicked_3_ - (double)(j + 42);
        if (d0 >= 0.0D && d1 >= 0.0D && d0 < 27.0D && d1 < 27.0D && chestToggle) {
            this.chestToggle = false;
            toggleSlots();
            return true;
        }

        d0 = p_mouseClicked_1_ - (double)(i + 176);
        d1 = p_mouseClicked_3_ - (double)(j + 17);
        if (d0 >= 0.0D && d1 >= 0.0D && d0 < 27.0D && d1 < 27.0D && !chestToggle) {
            this.chestToggle = true;
            toggleSlots();
            return true;
        }

        return super.mouseClicked(p_mouseClicked_1_, p_mouseClicked_3_, p_mouseClicked_5_);
    }

    private void toggleSlots() {
        for (Slot slot : this.container.inventorySlots) {
            if (slot instanceof EnhancedSlot) {
                ((EnhancedSlot)slot).setEnabled(chestToggle);
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
        boolean hasItemsInChest = false;
        int countEquipment = 0;

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
            this.blit(i + shiftX, j + shiftY, 0, this.ySize + 54, 18, 18);
            shiftY = shiftY + 18;
            countEquipment++;
        }
        if (this.container.enhancedAnimal.canHaveBridle()) {
            this.blit(i + shiftX, j + shiftY, 18, this.ySize + 54, 18, 18);
            shiftY = shiftY + 18;
            countEquipment++;
        }
        if (this.container.enhancedAnimal.canHaveArmour()) {
            this.blit(i + shiftX, j + shiftY, 36, this.ySize + 54, 18, 18);
            shiftY = shiftY + 18;
            if (shiftY >= 69) {
                shiftY = 17;
                shiftX = 79;
            }
            countEquipment++;
        }
        if (this.container.enhancedAnimal.canHaveBlanket() && (shiftX == 7 || !this.chestToggle)) {
            this.blit(i + shiftX, j + shiftY, 54, this.ySize + 54, 18, 18);
            shiftY = shiftY + 18;
            if (shiftY >= 69) {
                shiftY = 17;
                shiftX = 79;
            }
            countEquipment++;
        }
        if (this.container.enhancedAnimal.canHaveBanner() && (shiftX == 7 || !this.chestToggle)) {
            this.blit(i + shiftX, j + shiftY, 72, this.ySize + 54, 18, 18);
            shiftY = shiftY + 18;
            if (shiftY >= 69) {
                shiftY = 17;
                shiftX = 79;
            }
            countEquipment++;
        }
        if (this.container.enhancedAnimal.canHaveHarness() && (shiftX == 7 || !this.chestToggle)) {
            this.blit(i + shiftX, j + shiftY, 90, this.ySize + 54, 18, 18);
            shiftY = shiftY + 18;
            if (shiftY >= 69) {
                shiftY = 17;
                shiftX = 79;
            }
            countEquipment++;
        }

        for (int a = countEquipment; a <= retrievedInventory.getSizeInventory() && !hasItemsInChest; a++) {
            if (!retrievedInventory.getStackInSlot(a).isEmpty()) {
                hasItemsInChest = true;
            }
        }

        if (this.container.enhancedAnimal.canHaveChest()) {
            if (this.chestToggle) {
                this.blit(i + 173, j + 13, 209, 16, 31, 28);
                this.blit(i + 173, j + 41, 177, 44, 30, 28);  //broken image... no idea why or how
                if (retrievedInventory.getStackInSlot(0).getItem() == Items.CHEST) {
                    if (hasItemsInChest) {
                        this.blit(i + 79, j + 17, 0, this.ySize, 18*invSize, 54);
                    } else {
                        this.blit(i + 79, j + 17, 90, this.ySize, 90, 54);
                        this.blit(i + 112, j + 31, 180, this.ySize, 24, 26);
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
