package mokiyoki.enhancedanimals.gui;

import com.mojang.blaze3d.platform.GlStateManager;
import mokiyoki.enhancedanimals.util.EnhancedAnimalInfo;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.screen.inventory.InventoryScreen;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class EnhancedAnimalScreen extends ContainerScreen<EnhancedAnimalContainer> {
    private static final ResourceLocation GUI_TEXTURE = new ResourceLocation("eanimod:textures/gui/genetic_animal_screen.png");
    private IInventory inventory;
    /** The mouse x-position recorded during the last rendered frame. */
    private float mousePosx;
    /** The mouse y-position recorded during the last renderered frame. */
    private float mousePosY;

    /** temp booleans */
    boolean tabToggle = true;


    public static EnhancedAnimalInfo enhancedAnimalInfo = new EnhancedAnimalInfo();

    public EnhancedAnimalScreen(EnhancedAnimalContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);
        setAnimalInfo();
    }

    public void render(int p_render_1_, int p_render_2_, float p_render_3_) {
        if (!enhancedAnimalInfo.created) {setAnimalInfo();}
        this.renderBackground();
        this.mousePosx = (float)p_render_1_;
        this.mousePosY = (float)p_render_2_;
        super.render(p_render_1_, p_render_2_, p_render_3_);
        this.renderHoveredToolTip(p_render_1_, p_render_2_);
    }

    /**
     * Draw the foreground layer for the GuiContainer (everything in front of the items)
     */
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        this.font.drawString(this.title.getFormattedText(), 8.0F, 6.0F, 4210752);
        this.font.drawString(this.playerInventory.getDisplayName().getFormattedText(), 8.0F, (float)(this.ySize - 96 + 2), 4210752);
        this.font.drawString(this.playerInventory.getDisplayName().getFormattedText(), 8.0F, (float)(this.ySize - 96 + 2), 4210752);
        // ageTag + name
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
         *  this.blit(drawFromX, drawFromY, imageX, imageY, sizeX, sizeY)
         */

        if (enhancedAnimalInfo.canHaveSaddle) {
            this.blit(i + shiftX, j + shiftY, 0, this.ySize + 54, 18, 18);
            shiftY = shiftY + 18;
            countEquipment++;
        }
        if (enhancedAnimalInfo.canHaveBridle) {
            this.blit(i + shiftX, j + shiftY, 18, this.ySize + 54, 18, 18);
            shiftY = shiftY + 18;
            countEquipment++;
        }
        if (enhancedAnimalInfo.canHaveArmour) {
            this.blit(i + shiftX, j + shiftY, 36, this.ySize + 54, 18, 18);
            shiftY = shiftY + 18;
            if (shiftY >= 69) {
                shiftY = 17;
                shiftX = 79;
            }
            countEquipment++;
        }
        if (enhancedAnimalInfo.canHaveBlanket && (shiftX == 7 || !tabToggle)) {
            this.blit(i + shiftX, j + shiftY, 54, this.ySize + 54, 18, 18);
            shiftY = shiftY + 18;
            if (shiftY >= 69) {
                shiftY = 17;
                shiftX = 79;
            }
            countEquipment++;
        }
        if (enhancedAnimalInfo.canHaveBanner && (shiftX == 7 || !tabToggle)) {
            this.blit(i + shiftX, j + shiftY, 72, this.ySize + 54, 18, 18);
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

        if (enhancedAnimalInfo.canHaveChest && tabToggle) {
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
        }

        if (enhancedAnimalInfo.created) {
            InventoryScreen.drawEntityOnScreen(i + 51, j + 60, 17, (float)(i + 51) - this.mousePosx, (float)(j + 75 - 50) - this.mousePosY, (LivingEntity) this.container.getAnimal());
        }
    }

    private void setAnimalInfo() {
        this.enhancedAnimalInfo = this.container.animalInfo;
    }
}
