package mokiyoki.enhancedanimals.gui;

import net.minecraft.client.gui.GuiGraphics;
import com.mojang.blaze3d.platform.GlStateManager;
import mokiyoki.enhancedanimals.blocks.EggCartonContainer;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;

public class EggCartonScreen extends AbstractContainerScreen<EggCartonContainer> {
    private static final ResourceLocation GUI_TEXTURE = ResourceLocation.parse("eanimod:textures/gui/egg_carton.png");

    public EggCartonScreen(EggCartonContainer container, Inventory playerInventory, Component textComponent) {
        super(container, playerInventory, textComponent);
        this.imageHeight = 148;
        this.inventoryLabelY = this.imageHeight - 94;
    }

    public void render(GuiGraphics guiGraphics, int p_render_1_, int p_render_2_, float p_render_3_) {
        super.render(guiGraphics, p_render_1_, p_render_2_, p_render_3_);
        this.renderTooltip(guiGraphics, p_render_1_, p_render_2_);
    }

    /**
     * Draw the foreground layer for the GuiContainer (everything in front of the items)
     */
//    protected void renderLabels(PoseStack matrixStack, int mouseX, int mouseY) {
//        this.font.draw(matrixStack, this.title.getString(), 8.0F, 6.0F, 4210752);
//        this.font.draw(matrixStack, this.inventory.getDisplayName().getString(), 8.0F, (float)(this.imageHeight - 113 + 2), 4210752);
//    }

    /**
     * Draws the background layer of this container (behind the items).
     */
    protected void renderBg(GuiGraphics guiGraphics, float partialTicks, int mouseX, int mouseY) {
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;
        guiGraphics.blit(GUI_TEXTURE, i, j, 0, 0, this.imageWidth, this.imageHeight);
    }
}
