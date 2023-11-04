package mokiyoki.enhancedanimals.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import mokiyoki.enhancedanimals.config.GeneticAnimalsConfig;
import mokiyoki.enhancedanimals.items.CustomizableCollar;
import mokiyoki.enhancedanimals.util.EnhancedAnimalInfo;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class EnhancedAnimalScreen extends AbstractContainerScreen<EnhancedAnimalContainer> {

    private static final ResourceLocation GUI_TEXTURE = new ResourceLocation("eanimod:textures/gui/genetic_animal_gui.png");
    private Container inventory;
    /** The mouse x-position recorded during the last rendered frame. */
    private float mousePosx;
    /** The mouse y-position recorded during the last renderered frame. */
    private float mousePosY;

    /** temp booleans */
    boolean chestTabEnabled = false;
    boolean omniToggle = false;

    public static EnhancedAnimalInfo enhancedAnimalInfo = new EnhancedAnimalInfo();

    public EnhancedAnimalScreen(EnhancedAnimalContainer screenContainer, Inventory inv, Component titleIn) {
        super(screenContainer, inv, titleIn);
        setAnimalInfo();
        if (this.getMenu().getEnhancedAnimalInventory().getItem(0).getItem() == Items.CHEST) {
            chestTabEnabled = true;
        }
        toggleSlots();
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float p_render_3_) {
        if (!enhancedAnimalInfo.created) {setAnimalInfo();}
        toggleSlots();
        this.renderBackground(guiGraphics);
        this.mousePosx = (float)mouseX;
        this.mousePosY = (float)mouseY;
        super.render(guiGraphics, mouseX, mouseY, p_render_3_);
        this.renderTooltip(guiGraphics, mouseX, mouseY);
        this.renderInfoToolTip(guiGraphics, mouseX, mouseY);
    }

    private void renderInfoToolTip(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        if (this.isHovering(127, 5, 7, 9, (double)mouseX, (double)mouseY)) {
            if (GeneticAnimalsConfig.COMMON.omnigenders.get()) {
                if (this.enhancedAnimalInfo.pregnant > 0) {
                    if (this.omniToggle) {
                        guiGraphics.renderTooltip(this.font, Component.translatable("eanimod.animalinfocontainer.femalepregnant"), mouseX, mouseY);
                    } else {
                        guiGraphics.renderTooltip(this.font, Component.translatable("eanimod.animalinfocontainer.malepregnant"), mouseX, mouseY);
                    }
                } else {
                    if (this.omniToggle) {
                        guiGraphics.renderTooltip(this.font, Component.translatable("eanimod.animalinfocontainer.female"), mouseX, mouseY);
                    } else {
                        guiGraphics.renderTooltip(this.font, Component.translatable("eanimod.animalinfocontainer.male"), mouseX, mouseY);
                    }
                }
            } else if (this.enhancedAnimalInfo.isFemale) {
                if (this.enhancedAnimalInfo.pregnant > 0) {
                    guiGraphics.renderTooltip(this.font, Component.translatable("eanimod.animalinfocontainer.femalepregnant"), mouseX, mouseY);
                } else {
                    guiGraphics.renderTooltip(this.font, Component.translatable("eanimod.animalinfocontainer.female"), mouseX, mouseY);
                }
            } else {
                if (this.enhancedAnimalInfo.pregnant > 0) {
                    guiGraphics.renderTooltip(this.font, Component.translatable("eanimod.animalinfocontainer.malepregnant"), mouseX, mouseY);
                } else {
                    guiGraphics.renderTooltip(this.font, Component.translatable("eanimod.animalinfocontainer.male"), mouseX, mouseY);
                }
            }
        } else {
            this.omniToggle = !this.omniToggle;
        }
        if (this.isHovering(136, 5, 8, 9, (double)mouseX, (double)mouseY)) {
            guiGraphics.renderTooltip(this.font, Component.translatable("eanimod.animalinfocontainer.health"), mouseX, mouseY);
        }
        if (this.isHovering(147, 5, 7, 9, (double)mouseX, (double)mouseY)) {
            guiGraphics.renderTooltip(this.font, Component.translatable("eanimod.animalinfocontainer.hunger"), mouseX, mouseY);
        }
    }

    protected void slotClicked(Slot slotIn, int slotId, int mouseButton, ClickType type) {
        /**
         *  slotIn.slotNumber = slotId
         */
        if (type == ClickType.QUICK_MOVE && slotIn != null && slotIn.hasItem()) {
            Item itemIn = slotIn.getItem().getItem();
            Item chestInSlot = this.menu.getEnhancedAnimalInventory().getItem(0).getItem();

            boolean hasChestEquipped = chestInSlot == Items.CHEST;
            boolean slotIDIsAnEquipmentSlot = slotId <= this.menu.numberOfEquipmentSlots;

            if (slotIDIsAnEquipmentSlot) {

            } else if (!hasChestEquipped && !(itemIn == Items.CHEST) && !this.menu.isQuickTransferable(itemIn)) {
                return;
            }
        }

        super.slotClicked(slotIn, slotId, mouseButton, type);
    }

    public boolean mouseClicked(double p_mouseClicked_1_, double p_mouseClicked_3_, int p_mouseClicked_5_) {
        if (this.menu.enhancedAnimal.canHaveChest()) {
            int i = (this.width - this.imageWidth) / 2;
            int j = (this.height - this.imageHeight) / 2;

            if (GeneticAnimalsConfig.COMMON.tabsOnTop.get()) {
                double d0 = p_mouseClicked_1_ - (double) (i + 140);
                double d1 = p_mouseClicked_3_ - (double) (j - 28);
                if (d0 >= 0.0D && d1 >= 0.0D && d0 < 27.0D && d1 < 27.0D && chestTabEnabled) {
                    this.chestTabEnabled = false;
                    toggleSlots();
                    return true;
                }

                d0 = p_mouseClicked_1_ - (double) (i + 111);
                d1 = p_mouseClicked_3_ - (double) (j - 28);
                if (d0 >= 0.0D && d1 >= 0.0D && d0 < 27.0D && d1 < 27.0D && !chestTabEnabled) {
                    this.chestTabEnabled = true;
                    toggleSlots();
                    return true;
                }
            } else {
                double d0 = p_mouseClicked_1_ - (double) (i + 176);
                double d1 = p_mouseClicked_3_ - (double) (j + 42);
                if (d0 >= 0.0D && d1 >= 0.0D && d0 < 27.0D && d1 < 27.0D && chestTabEnabled) {
                    this.chestTabEnabled = false;
                    toggleSlots();
                    return true;
                }

                d0 = p_mouseClicked_1_ - (double) (i + 176);
                d1 = p_mouseClicked_3_ - (double) (j + 17);
                if (d0 >= 0.0D && d1 >= 0.0D && d0 < 27.0D && d1 < 27.0D && !chestTabEnabled) {
                    this.chestTabEnabled = true;
                    toggleSlots();
                    return true;
                }
            }
        }

        return super.mouseClicked(p_mouseClicked_1_, p_mouseClicked_3_, p_mouseClicked_5_);
    }

    private void toggleSlots() {
        Container retrievedInventory = this.menu.getEnhancedAnimalInventory();
        for (Slot slot : this.menu.slots) {
            if (slot instanceof EnhancedSlot) {
                if (!chestTabEnabled) {
                    ((EnhancedSlot)slot).setEnabled(false);
                } else {
                    if (slot.getSlotIndex() == 0){
                        if (!isHasItemsInChest(this.menu.getEnhancedAnimalInventory())){
                            ((EnhancedSlot)slot).setEnabled(true);
                        } else {
                            ((EnhancedSlot)slot).setEnabled(false);
                        }
                    } else {
                        if (slot.getSlotIndex() == 14) {
                            if (isHasItemsInChest(this.menu.getEnhancedAnimalInventory())) {
                                ((EnhancedSlot)slot).setEnabled(true);
                            } else {
                                ((EnhancedSlot)slot).setEnabled(false);
                            }
                        } else {
                            if (retrievedInventory.getItem(0).getItem() == Items.CHEST) {
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
    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        Container retrievedInventory = this.menu.getEnhancedAnimalInventory();
        int collarSlot = getCollarSlot(retrievedInventory);
        String name = enhancedAnimalInfo.name;
        boolean flag = true;

        if (collarSlot != 0) {
            ItemStack collarStack = retrievedInventory.getItem(collarSlot);
            String collarName = ((CustomizableCollar)collarStack.getItem()).getCollarName(collarStack);
            if (!collarName.equals("")) {
                name = collarName;
                flag = false;
            }
        }

        if (flag && name.startsWith("entity.eanimod")) {
            name = I18n.get(name);
        }

        if (!enhancedAnimalInfo.agePrefix.equals("ADULT")) {
            String age = enhancedAnimalInfo.agePrefix;
            if (age.equals("YOUNG")) {
                age = Component.translatable("eanimod.animalinfocontainer.young").getString();
            } else if (age.equals("BABY")) {
                age = Component.translatable("eanimod.animalinfocontainer.baby").getString();
            } else {
                age = Component.translatable("eanimod.animalinfocontainer.newborn").getString();
            }
            name = age+" "+name;
        }

        if (name.length() > 20) {
            name = name.substring(0, 20);
        }

        guiGraphics.drawString(this.font, name, 8.0F, (float)(this.imageHeight - 160), 4210752, false);
        //TODO how to get the correct inventory name? old : this.inventory.toString().getDisplayName().getString()
        guiGraphics.drawString(this.font, "Inventory", 8.0F, (float)(this.imageHeight - 94), 4210752, false);

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
    protected void renderBg(GuiGraphics guiGraphics, float partialTicks, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, GUI_TEXTURE);
        Container retrievedInventory = this.menu.getEnhancedAnimalInventory();
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;
        int invSize = 5;
        int shiftY = 17;
        int shiftX = 7;
        guiGraphics.blit(GUI_TEXTURE, i, j, 0, 0, this.imageWidth, this.imageHeight);

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

        if (GeneticAnimalsConfig.COMMON.omnigenders.get()) {
            guiGraphics.blit(GUI_TEXTURE, i + 126, j + 5, 125, this.imageHeight + 74, 8, 10); // pregnancy icon
            int pregnancy = enhancedAnimalInfo.pregnant;
            guiGraphics.blit(GUI_TEXTURE, i + 126, j + 4 + (11 - pregnancy), 133, this.imageHeight + 74 + (10 - pregnancy), 8, pregnancy); // pregnancy icon
        } else {
            if (enhancedAnimalInfo.isFemale) {
                guiGraphics.blit(GUI_TEXTURE, i + 126, j + 5, 117, this.imageHeight + 54, 8, 10); // female icon
                int pregnancy = enhancedAnimalInfo.pregnant;
                guiGraphics.blit(GUI_TEXTURE, i + 126, j + 4 + (11 - pregnancy), 117, this.imageHeight + 64 + (10 - pregnancy), 8, pregnancy); // female icon
            } else {
                guiGraphics.blit(GUI_TEXTURE, i + 126, j + 5, 108, this.imageHeight + 54, 8, 10); // male icon
                int pregnancy = enhancedAnimalInfo.pregnant;
                guiGraphics.blit(GUI_TEXTURE, i + 126, j + 4 + (11 - pregnancy), 108, this.imageHeight + 64 + (10 - pregnancy), 8, pregnancy); // male icon
            }
        }

        guiGraphics.blit(GUI_TEXTURE, i + 136, j + 5, 125, this.imageHeight + 54, 9, 10); // health icon
        guiGraphics.blit(GUI_TEXTURE, i + 147, j + 5, 134, this.imageHeight + 54, 9, 10); // hunger icon
        guiGraphics.blit(GUI_TEXTURE, i + 158, j + 5, 143, this.imageHeight + 54, 10, 10); // tameness icon

        int health = Math.min(enhancedAnimalInfo.health, 10);
        int hunger = 10 - enhancedAnimalInfo.hunger;
//        int tameness = enhancedAnimalInfo.tameness;
        guiGraphics.blit(GUI_TEXTURE, i + 136, j + 5 + (10-health), 125, this.imageHeight + 64 + (10-health), 9, health); // health icon
        guiGraphics.blit(GUI_TEXTURE, i + 147, j + 5 + (10-hunger), 134, this.imageHeight + 64 + (10-hunger), 9, hunger); // hunger icon
//        this.blit(i + 158, j + 5 + (10-tameness), 143, this.ySize + 64 + (10-tameness), 10, tameness); // tameness icon

        if (this.menu.enhancedAnimal.canHaveSaddle()) {
            if (retrievedInventory.getItem(1).isEmpty()) {
                guiGraphics.blit(GUI_TEXTURE, i + shiftX, j + shiftY, 0, this.imageHeight + 54, 18, 18);
            } else {
                guiGraphics.blit(GUI_TEXTURE, i + shiftX, j + shiftY, 54, this.imageHeight + 36, 18, 18);
            }
            shiftY = shiftY + 18;
        }
        if (this.menu.enhancedAnimal.canHaveBridle()) {
            if (retrievedInventory.getItem(3).isEmpty()) {
                guiGraphics.blit(GUI_TEXTURE, i + shiftX, j + shiftY, 18, this.imageHeight + 54, 18, 18);
            } else {
                guiGraphics.blit(GUI_TEXTURE, i + shiftX, j + shiftY, 54, this.imageHeight + 36, 18, 18);
            }
            shiftY = shiftY + 18;
        }
        if (this.menu.enhancedAnimal.canHaveArmour()) {
            if (retrievedInventory.getItem(2).isEmpty()) {
                guiGraphics.blit(GUI_TEXTURE, i + shiftX, j + shiftY, 36, this.imageHeight + 54, 18, 18);
            } else {
                guiGraphics.blit(GUI_TEXTURE, i + shiftX, j + shiftY, 54, this.imageHeight + 36, 18, 18);
            }
            shiftY = shiftY + 18;
            if (shiftY >= 69) {
                shiftY = 17;
                shiftX = 79;
            }
        }
        if (this.menu.enhancedAnimal.canHaveBlanket() && (shiftX == 7 || !this.chestTabEnabled)) {
            if (retrievedInventory.getItem(4).isEmpty()) {
                guiGraphics.blit(GUI_TEXTURE, i + shiftX, j + shiftY, 54, this.imageHeight + 54, 18, 18);
            } else {
                guiGraphics.blit(GUI_TEXTURE, i + shiftX, j + shiftY, 54, this.imageHeight + 36, 18, 18);
            }
            shiftY = shiftY + 18;
            if (shiftY >= 69) {
                shiftY = 17;
                shiftX = 79;
            }
        }
        if (this.menu.enhancedAnimal.canHaveBanner() && (shiftX == 7 || !this.chestTabEnabled)) {
            if (retrievedInventory.getItem(6).isEmpty()) {
                guiGraphics.blit(GUI_TEXTURE, i + shiftX, j + shiftY, 72, this.imageHeight + 54, 18, 18);
            } else {
                guiGraphics.blit(GUI_TEXTURE, i + shiftX, j + shiftY, 54, this.imageHeight + 36, 18, 18);
            }
            shiftY = shiftY + 18;
            if (shiftY >= 69) {
                shiftY = 17;
                shiftX = 79;
            }
        }
        if (this.menu.enhancedAnimal.canHaveHarness() && (shiftX == 7 || !this.chestTabEnabled)) {
            if (retrievedInventory.getItem(5).isEmpty()) {
                guiGraphics.blit(GUI_TEXTURE, i + shiftX, j + shiftY, 90, this.imageHeight + 54, 18, 18);
            } else {
                guiGraphics.blit(GUI_TEXTURE, i + shiftX, j + shiftY, 54, this.imageHeight + 36, 18, 18);
            }
            shiftY = shiftY + 18;
            if (shiftY >= 69) {
                shiftY = 17;
                shiftX = 79;
            }
        }

        if (shiftY==17 && shiftX==7) {
            guiGraphics.blit(GUI_TEXTURE, i + 7, j + 17, 0, this.imageHeight + 72, 18, 18);
        }

        boolean hasItemsInChest = isHasItemsInChest(retrievedInventory);

        if (this.menu.enhancedAnimal.canHaveChest()) {
            if (this.chestTabEnabled) {
                if (GeneticAnimalsConfig.COMMON.tabsOnTop.get()) {
                    guiGraphics.blit(GUI_TEXTURE, i + 111, j - 28, 209, 100, 28, 31); //highlight tab
                    guiGraphics.blit(GUI_TEXTURE, i + 140, j - 28, 177, 100, 28, 31); //shadow tab
                    guiGraphics.blit(GUI_TEXTURE, i + 117, j - 19, 217, 23, 15, 14); //highlight chest logo
                    guiGraphics.blit(GUI_TEXTURE, i + 144, j - 17, 182, 51, 18, 14); //shadow info logo
                } else {
                    guiGraphics.blit(GUI_TEXTURE, i + 173, j + 13, 209, 16, 31, 28); //highlight chest
                    guiGraphics.blit(GUI_TEXTURE, i + 173, j + 41, 177, 44, 30, 28); //shadow info
                }
                if (retrievedInventory.getItem(0).getItem() == Items.CHEST) {
                    if (hasItemsInChest) {
                        guiGraphics.blit(GUI_TEXTURE, i + 79, j + 17, 0, this.imageHeight, 18*invSize, 54);
                    } else {
                        guiGraphics.blit(GUI_TEXTURE, i + 79, j + 17, 90, this.imageHeight, 90, 54);
//                        this.blit(matrixStack, i + 112, j + 31, 180, this.ySize, 24, 26);
                    }
                } else {
                    guiGraphics.blit(GUI_TEXTURE, i + 79, j + 17, 90, this.imageHeight, 90, 54);
                }
            } else {
                if (GeneticAnimalsConfig.COMMON.tabsOnTop.get()) {
                    guiGraphics.blit(GUI_TEXTURE, i + 140, j - 28, 209, 100, 28, 31); //highlight tab
                    guiGraphics.blit(GUI_TEXTURE, i + 111, j - 28, 177, 100, 28, 31); //shadow tab
                    guiGraphics.blit(GUI_TEXTURE, i + 117, j - 18, 184, 23, 15, 14); //shadow chest logo
                    guiGraphics.blit(GUI_TEXTURE, i + 144, j - 18, 215, 51, 18, 14); //highlight info logo
                } else {
                    guiGraphics.blit(GUI_TEXTURE, i + 173, j + 13, 177, 16, 30, 28); //shadow chest
                    guiGraphics.blit(GUI_TEXTURE, i + 173, j + 41, 209, 44, 31, 28); //highlight info
                }
            }
        }

        if (this.enhancedAnimalInfo.created) {
            InventoryScreen.renderEntityInInventoryFollowsMouse(guiGraphics, i + 51, j + 60, 17, (float)(i + 51) - this.mousePosx, (float)(j + 75 - 50) - this.mousePosY, (LivingEntity) this.menu.getAnimal());
        }

        if (!this.chestTabEnabled) {
            Integer ageInt = this.enhancedAnimalInfo.age/24000;
//            Float ageFloat = ageInt >= 20 ? (float)(ageInt/10) : (float)ageInt/10.0F;
            String age = "";
            if (ageInt < 8) {
                age = ageInt.toString() + (Component.translatable("eanimod.animalinfocontainer.days").getString());
            } else if (ageInt < 96) {
                ageInt = ageInt/8;
                age = ageInt.toString() + (Component.translatable("eanimod.animalinfocontainer.months").getString());
            } else if (ageInt < 959040) {
                ageInt = ageInt/96;
                age = ageInt.toString() + (Component.translatable("eanimod.animalinfocontainer.years").getString());
            } else {
                age = Component.translatable("eanimod.animalinfocontainer.ancient").getString();
            }
            guiGraphics.drawString(this.font, (Component.translatable("eanimod.animalinfocontainer.age").getString()) + ":" + age, i + 99, j + 20, 4210752, false);

            String sireName = this.enhancedAnimalInfo.sire;
            int s = sireName.length();
            String damName = this.enhancedAnimalInfo.dam;
            int d = damName.length();
            if (s > 8) {
                guiGraphics.drawString(this.font, Component.translatable("eanimod.animalinfocontainer.sire").getString()+":", i + 99, j + 30, 4210752, false);
                guiGraphics.drawString(this.font, s > 12 ? sireName.substring(0, 12) : sireName, i + 99, j + 40, 4210752, false);
                if (d > 8) {
                    guiGraphics.drawString(this.font, Component.translatable("eanimod.animalinfocontainer.dam").getString()+":", i + 99, j + 51, 4210752, false);
                    guiGraphics.drawString(this.font, d > 12 ? damName.substring(0, 12) : damName, i + 99, j + 60, 4210752, false);
                } else {
                    guiGraphics.drawString(this.font, Component.translatable("eanimod.animalinfocontainer.dam").getString()+":" + damName, i + 99, j + 50, 4210752, false);
                }
            } else {
                guiGraphics.drawString(this.font, Component.translatable("eanimod.animalinfocontainer.sire").getString()+":" + sireName, i + 99, j + 30, 4210752, false);
                if (d > 8) {
                    guiGraphics.drawString(this.font, Component.translatable("eanimod.animalinfocontainer.dam").getString()+":", i + 99, j + 41, 4210752, false);
                    guiGraphics.drawString(this.font, d > 12 ? damName.substring(0, 12) : damName, i + 99, j + 50, 4210752, false);
                } else {
                    guiGraphics.drawString(this.font, Component.translatable("eanimod.animalinfocontainer.dam").getString()+":" + damName, i + 99, j + 40, 4210752, false);
                }
            }
        }
    }

    private boolean isHasItemsInChest(Container retrievedInventory) {
        for (int a = 7; a <= retrievedInventory.getContainerSize(); a++) {
            if (!retrievedInventory.getItem(a).isEmpty() && !(retrievedInventory.getItem(a).getItem() == Items.AIR)) {
                return true;
            }
        }
        return false;
    }

    private void setAnimalInfo() {
        this.enhancedAnimalInfo = this.menu.animalInfo;
    }

    private int getCollarSlot(Container retrievedInventory) {
        for (int i = 1; i <= 6; i++) {
            if (retrievedInventory.getItem(i).getItem() instanceof CustomizableCollar) {
                return i;
            }
        }
        return 0;
    }

}
