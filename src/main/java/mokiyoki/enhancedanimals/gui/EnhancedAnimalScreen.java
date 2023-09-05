package mokiyoki.enhancedanimals.gui;

import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.math.Matrix4f;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import mokiyoki.enhancedanimals.config.EanimodCommonConfig;
import mokiyoki.enhancedanimals.items.CustomizableCollar;
import mokiyoki.enhancedanimals.util.EnhancedAnimalInfo;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.TextComponent;
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
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.loading.FMLPaths;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Consumer;

import static mokiyoki.enhancedanimals.gui.EnhancedAnimalScreen.PhotoMode.BACKGROUND;
import static mokiyoki.enhancedanimals.gui.EnhancedAnimalScreen.PhotoMode.RGB;
import static mokiyoki.enhancedanimals.gui.EnhancedAnimalScreen.PhotoMode.TRANSPARENCY;
import static mokiyoki.enhancedanimals.gui.EnhancedAnimalScreen.PhotoPose.STANDARD_POSE;

@OnlyIn(Dist.CLIENT)
public class EnhancedAnimalScreen extends AbstractContainerScreen<EnhancedAnimalContainer> {
    private static final ResourceLocation GUI_TEXTURE = new ResourceLocation("eanimod:textures/gui/genetic_animal_gui.png");
    private static final ResourceLocation PHOTO_MODE_GUI_TEXTURE = new ResourceLocation("eanimod:textures/gui/genetic_animal_photo_gui.png");
    private static ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation("eanimod:textures/gui/photo_backgrounds/basic.png");

    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss");

    private Container inventory;
    /** The mouse x-position recorded during the last rendered frame. */
    private float mousePosx;
    /** The mouse y-position recorded during the last renderered frame. */
    private float mousePosY;

    /** temp booleans */
    boolean chestTabEnabled = false;
    boolean omniToggle = false;

    /** Photo Mode */
    boolean photoModeEnabled = false;

    int selectedBackground = 0;
    int maxBackgrounds = 0;

    boolean previousSelectionWasFile = false;

    boolean prepareForTransparentScreenshot = false;

    int currentBackgroundColour = -16777216; // default as black
    int greenScreenColour = -16711936;
    int transparentColour = 0;

    private EditBox rBox;
    private EditBox gBox;
    private EditBox bBox;

    double xPos = 0;
    int yPos = 0;

    boolean dragCamera = false;
    float dragOriginX = 0.0f;
    float dragOriginY = 0.0f;
    float dragOffsetX = 0.0f;
    float dragOffsetY = 0.0f;

    int photoWidth = 302;
    int photoHeight = 166;

    int backgroundWidth = 0;
    int backgroundHeight = 0;

    PhotoMode currentMode = RGB;

    PhotoPose currentPose = STANDARD_POSE;

    public enum PhotoMode {
        BACKGROUND,
        RGB,
        TRANSPARENCY

    }

    public enum PhotoPose {
        STANDARD_POSE(51, 25),
        FROM_BELOW_TO_LEFT_POSE(51, 25);

        private final int x;
        private final int y;

        PhotoPose(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }
    }

    public static EnhancedAnimalInfo enhancedAnimalInfo = new EnhancedAnimalInfo();

    public EnhancedAnimalScreen(EnhancedAnimalContainer screenContainer, Inventory inv, Component titleIn) {
        super(screenContainer, inv, titleIn);
        setAnimalInfo();
        if (this.getMenu().getEnhancedAnimalInventory().getItem(0).getItem() == Items.CHEST) {
            chestTabEnabled = true;
        }
        toggleSlots();
    }

    public void render(PoseStack matrixStack, int mouseX, int mouseY, float p_render_3_) {
        if (!enhancedAnimalInfo.created) {setAnimalInfo();}
        toggleSlots();
        this.renderBackground(matrixStack);
        this.mousePosx = (float)mouseX;
        this.mousePosY = (float)mouseY;
        super.render(matrixStack, mouseX, mouseY, p_render_3_);
        renderCameraMode(matrixStack, mouseX, mouseY, p_render_3_);
        this.renderTooltip(matrixStack, mouseX, mouseY);
        this.renderInfoToolTip(matrixStack, mouseX, mouseY);
    }

    protected void init() {
        this.rBox = new EditBox(this.font, this.leftPos + 60, this.topPos + 60, 80, 9, new TranslatableComponent("photomode.rBox"));
        this.rBox.setMaxLength(3);
        this.rBox.setBordered(false);
        this.rBox.setVisible(false);
        this.rBox.setTextColor(16777215);
        this.addWidget(this.rBox);

        this.gBox = new EditBox(this.font, this.leftPos + 60, this.topPos + 80, 80, 9, new TranslatableComponent("photomode.gBox"));
        this.gBox.setMaxLength(3);
        this.gBox.setBordered(false);
        this.gBox.setVisible(false);
        this.gBox.setTextColor(16777215);
        this.addWidget(this.gBox);

        this.bBox = new EditBox(this.font, this.leftPos + 60, this.topPos + 100, 80, 9, new TranslatableComponent("photomode.bBox"));
        this.bBox.setMaxLength(3);
        this.bBox.setBordered(false);
        this.bBox.setVisible(false);
        this.bBox.setTextColor(16777215);
        this.addWidget(this.bBox);
    }


    private void renderCameraMode(PoseStack matrixStack, int mouseX, int mouseY, float p_render_3_) {
        if (this.photoModeEnabled) {
            int photoI = (this.width - photoWidth) / 2;
            int photoJ = (this.height - photoWidth) / 2;

            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

            RenderSystem.setShaderTexture(0, GUI_TEXTURE);
            renderTabs(matrixStack, photoI, photoJ+68, 63, 0, 256, 256);

            RenderSystem.setShaderTexture(0, PHOTO_MODE_GUI_TEXTURE);

            //Take Photo Button
            this.blit(matrixStack, photoI+26, photoJ+40, 0, 335, 112, 28, 31, 384, 256);

            //Direction Buttons
            this.blit(matrixStack, photoI+304, photoJ+236, 0, 323, 168, 18, 18, 384, 256); //left
            this.blit(matrixStack, photoI+322, photoJ+218, 0, 342, 149, 18, 18, 384, 256); //up
            this.blit(matrixStack, photoI+340, photoJ+236, 0, 361, 168, 18, 18, 384, 256); //right
            this.blit(matrixStack, photoI+322, photoJ+254, 0, 342, 187, 18, 18, 384, 256); //down
            this.blit(matrixStack, photoI+325, photoJ+239, 0, 345, 171, 12, 12, 384, 256); //middle

            //highlight tab
            if (this.currentMode == RGB ) {
                //Background Button
                this.blit(matrixStack, photoI-46, photoJ+78, 0, 303, 16, 28, 31, 384, 256);
                //RGB Button
                this.blit(matrixStack, photoI-46, photoJ+113, 0, 335, 48, 28, 31, 384, 256);
                //Transparency Button
                this.blit(matrixStack, photoI-46, photoJ+148, 0, 303, 80, 28, 31, 384, 256);

                renderCameraBackground(matrixStack, photoI, photoJ+68, 0, 0, 0, photoWidth, photoHeight, 384, 256);

                //RGB Box Backgrounds
                this.blit(matrixStack, photoI-35, photoJ+170, 0, 0, 28, 167, 181, 384, 256);
                this.blit(matrixStack, photoI-35, photoJ+190, 0, 0, 28, 167, 181, 384, 256);
                this.blit(matrixStack, photoI-35, photoJ+210, 0, 0, 28, 167, 181, 384, 256);

                //RGB Boxes
                this.rBox.render(matrixStack, mouseX, mouseY, p_render_3_);
                this.rBox.setFocus(true);
                this.gBox.render(matrixStack, mouseX, mouseY, p_render_3_);
                this.bBox.render(matrixStack, mouseX, mouseY, p_render_3_);
                this.rBox.render(matrixStack, mouseX, mouseY, p_render_3_);
                this.gBox.render(matrixStack, mouseX, mouseY, p_render_3_);
                this.bBox.render(matrixStack, mouseX, mouseY, p_render_3_);

            } else if (this.currentMode == BACKGROUND) {
                //Background Button
                this.blit(matrixStack, photoI-46, photoJ+78, 0, 335, 16, 28, 31, 384, 256);
                //Arrows
                this.blit(matrixStack, photoI-15, photoJ+90, 0, 368, 26, 5, 7, 384, 256);
                this.blit(matrixStack, photoI-8, photoJ+90, 0, 375, 26, 5, 7, 384, 256);
                //RGB Button
                this.blit(matrixStack, photoI-46, photoJ+113, 0, 303, 48, 28, 31, 384, 256);
                //Transparency Button
                this.blit(matrixStack, photoI-46, photoJ+148, 0, 303, 80, 28, 31, 384, 256);

                RenderSystem.setShaderTexture(0, BACKGROUND_TEXTURE);

                // Start drawing the image at position (x, y) and stretch it to 300x200
                int x = (width - photoWidth) / 2;
                int y = (height - photoHeight) / 2;
                drawScaledImage(x, y, 1, 1);

            } else if (this.currentMode == TRANSPARENCY) {
                //Background Button
                this.blit(matrixStack, photoI-46, photoJ+78, 0, 303, 16, 28, 31, 384, 256);
                //RGB Button
                this.blit(matrixStack, photoI-46, photoJ+113, 0, 303, 48, 28, 31, 384, 256);
                //Transparency Button
                this.blit(matrixStack, photoI-46, photoJ+148, 0, 335, 80, 28, 31, 384, 256);
                if (this.prepareForTransparentScreenshot) {
                    int tempColourHolder = this.currentBackgroundColour;
                    this.currentBackgroundColour = this.greenScreenColour;
                    renderCameraBackground(matrixStack, photoI, photoJ+68, 0, 0, 0, photoWidth, photoHeight, 384, 256);
                    this.currentBackgroundColour = tempColourHolder;
                }
            }

            renderEntityPhotoMode(photoI + 170, photoJ + 200, 70, (float)(photoI + currentPose.x), (float)(photoJ + currentPose.y), this.menu.getAnimal());

            if (this.prepareForTransparentScreenshot) {
                screenshotAnimal((animalScreenshotComponent) -> {
                    this.minecraft.execute(() -> {
                        this.minecraft.gui.getChat().addMessage(animalScreenshotComponent);
                    });
                });
                this.prepareForTransparentScreenshot = false;
            }
        }
    }

    private void drawScaledImage(int x, int y, float scaleX, float scaleY) {
        Tesselator tesselator = Tesselator.getInstance();
        BufferBuilder buffer = tesselator.getBuilder();
        buffer.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);

        buffer.vertex(x, y + photoHeight, 0).uv(0, scaleY).endVertex();
        buffer.vertex(x + photoWidth, y + photoHeight, 0).uv(scaleX, scaleY).endVertex();
        buffer.vertex(x + photoWidth, y, 0).uv(scaleX, 0).endVertex();
        buffer.vertex(x, y, 0).uv(0, 0).endVertex();

        tesselator.end();
    }

    public void renderCameraBackground(PoseStack matrixStack, int startX, int startY, int p_93147_, float p_93148_, float p_93149_, int width, int height, int textureXWidth, int textureYWidth) {
        innerRenderCameraBackground(matrixStack, startX, startX + width, startY, startY + height, p_93147_, width, height, p_93148_, p_93149_, textureXWidth, textureYWidth);
    }

    private void innerRenderCameraBackground(PoseStack matrixStack, int startX, int startXPlusWidth, int startY, int startYPlusHeight, int p_93193_, int width, int height, float p_93196_, float p_93197_, int textureXWidth, int textureYHeight) {
        completeRenderCameraBackground(matrixStack.last().pose(), startX, startXPlusWidth, startY, startYPlusHeight, p_93193_, (p_93196_ + 0.0F) / (float)textureXWidth, (p_93196_ + (float)width) / (float)textureXWidth, (p_93197_ + 0.0F) / (float)textureYHeight, (p_93197_ + (float)height) / (float)textureYHeight);
    }

    private void completeRenderCameraBackground(Matrix4f pose, int startX, int startXPlusWidth, int startY, int startYPlusHeight, int p_93118_, float p_93119_DivideTextureXWidth, float p_93120_PlusTextureWidthDivideTextureXWidth, float p_93121TextureYHeight, float p_93122_PlusTextureHeightDivideTextureYHeight) {
        RenderSystem.setShader(GameRenderer::getPositionTexColorShader);
        BufferBuilder bufferbuilder = Tesselator.getInstance().getBuilder();
        bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR);
        bufferbuilder.vertex(pose, (float)startX, (float)startYPlusHeight, (float)p_93118_).uv(p_93119_DivideTextureXWidth, p_93122_PlusTextureHeightDivideTextureYHeight).color(currentBackgroundColour).endVertex();
        bufferbuilder.vertex(pose, (float)startXPlusWidth, (float)startYPlusHeight, (float)p_93118_).uv(p_93120_PlusTextureWidthDivideTextureXWidth, p_93122_PlusTextureHeightDivideTextureYHeight).color(currentBackgroundColour).endVertex();
        bufferbuilder.vertex(pose, (float)startXPlusWidth, (float)startY, (float)p_93118_).uv(p_93120_PlusTextureWidthDivideTextureXWidth, p_93121TextureYHeight).color(currentBackgroundColour).endVertex();
        bufferbuilder.vertex(pose, (float)startX, (float)startY, (float)p_93118_).uv(p_93119_DivideTextureXWidth, p_93121TextureYHeight).color(currentBackgroundColour).endVertex();
        bufferbuilder.end();
        BufferUploader.end(bufferbuilder);
    }

    private void renderInfoToolTip(PoseStack matrixStack, int mouseX, int mouseY) {
        if (!this.photoModeEnabled) {
            if (this.isHovering(127, 5, 7, 9, (double)mouseX, (double)mouseY)) {
                if (EanimodCommonConfig.COMMON.omnigenders.get()) {
                    if (this.enhancedAnimalInfo.pregnant > 0) {
                        if (this.omniToggle) {
                            this.renderTooltip(matrixStack, new TranslatableComponent("eanimod.animalinfocontainer.femalepregnant"), mouseX, mouseY);
                        } else {
                            this.renderTooltip(matrixStack, new TranslatableComponent("eanimod.animalinfocontainer.malepregnant"), mouseX, mouseY);
                        }
                    } else {
                        if (this.omniToggle) {
                            this.renderTooltip(matrixStack, new TranslatableComponent("eanimod.animalinfocontainer.female"), mouseX, mouseY);
                        } else {
                            this.renderTooltip(matrixStack, new TranslatableComponent("eanimod.animalinfocontainer.male"), mouseX, mouseY);
                        }
                    }
                } else if (this.enhancedAnimalInfo.isFemale) {
                    if (this.enhancedAnimalInfo.pregnant > 0) {
                        this.renderTooltip(matrixStack, new TranslatableComponent("eanimod.animalinfocontainer.femalepregnant"), mouseX, mouseY);
                    } else {
                        this.renderTooltip(matrixStack, new TranslatableComponent("eanimod.animalinfocontainer.female"), mouseX, mouseY);
                    }
                } else {
                    if (this.enhancedAnimalInfo.pregnant > 0) {
                        this.renderTooltip(matrixStack, new TranslatableComponent("eanimod.animalinfocontainer.malepregnant"), mouseX, mouseY);
                    } else {
                        this.renderTooltip(matrixStack, new TranslatableComponent("eanimod.animalinfocontainer.male"), mouseX, mouseY);
                    }
                }
            } else {
                this.omniToggle = !this.omniToggle;
            }
            if (this.isHovering(136, 5, 8, 9, (double)mouseX, (double)mouseY)) {
                this.renderTooltip(matrixStack, new TranslatableComponent("eanimod.animalinfocontainer.health"), mouseX, mouseY);
            }
            if (this.isHovering(147, 5, 7, 9, (double)mouseX, (double)mouseY)) {
                this.renderTooltip(matrixStack, new TranslatableComponent("eanimod.animalinfocontainer.hunger"), mouseX, mouseY);
            }
        } else {
            if (this.isHoveringPhotoMode(-106, 11, 27, 23, (double)mouseX, (double)mouseY)) {
                this.renderTooltip(matrixStack, new TranslatableComponent("eanimod.animalinfocontainer.photomode.background"), mouseX, mouseY);
            }
            if (this.isHoveringPhotoMode(-106, 46, 27, 23, (double)mouseX, (double)mouseY)) {
                this.renderTooltip(matrixStack, new TranslatableComponent("eanimod.animalinfocontainer.photomode.rgb"), mouseX, mouseY);
            }
            if (this.isHoveringPhotoMode(-106, 83, 27, 23, (double)mouseX, (double)mouseY)) {
                this.renderTooltip(matrixStack, new TranslatableComponent("eanimod.animalinfocontainer.photomode.transparent"), mouseX, mouseY);
            }
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
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;
        double d0;
        double d1;

        System.out.println("MouseX: " + p_mouseClicked_1_ + " i = " + i);
        System.out.println("MouseY: " + p_mouseClicked_3_+ " j = " + j);


        if (this.menu.enhancedAnimal.canHaveChest()) {
            if (EanimodCommonConfig.COMMON.tabsOnTop.get()) {
                d0 = p_mouseClicked_1_ - (double) (i + 140);
                d1 = p_mouseClicked_3_ - (double) (j - 28);
                if (d0 >= 0.0D && d1 >= 0.0D && d0 < 27.0D && d1 < 27.0D && (chestTabEnabled || photoModeEnabled)) {
                    this.chestTabEnabled = false;
                    this.photoModeEnabled = false;
                    this.menu.getAnimal().isInPhotoMode = false;
                    toggleSlots();
                    return true;
                }

                d0 = p_mouseClicked_1_ - (double) (i + 111);
                d1 = p_mouseClicked_3_ - (double) (j - 28);
                if (d0 >= 0.0D && d1 >= 0.0D && d0 < 27.0D && d1 < 27.0D && !chestTabEnabled) {
                    this.chestTabEnabled = true;
                    this.photoModeEnabled = false;
                    this.menu.getAnimal().isInPhotoMode = false;
                    toggleSlots();
                    return true;
                }


            } else {
                d0 = p_mouseClicked_1_ - (double) (i + 176);
                d1 = p_mouseClicked_3_ - (double) (j + 42);
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

        if (this.photoModeEnabled) {
            //Take screenshot
            d0 = p_mouseClicked_1_ - (double) (i - 33);
            d1 = p_mouseClicked_3_ - (double) (j - 24);
            if (d0 >= 0.0D && d1 >= 0.0D && d0 < 27.0D && d1 < 23.0D) {
                if (this.currentMode == TRANSPARENCY) {
                    this.prepareForTransparentScreenshot = true;
                    return true;
                }

                screenshotAnimal((animalScreenshotComponent) -> {
                    this.minecraft.execute(() -> {
                        this.minecraft.gui.getChat().addMessage(animalScreenshotComponent);
                    });
                });
                return true;
            }

            //Modes
            d0 = p_mouseClicked_1_ - (double) (i - 106);
            d1 = p_mouseClicked_3_ - (double) (j + 12);
            if (d0 >= 0.0D && d1 >= 0.0D && d0 < 27.0D && d1 < 23.0D) {
                this.currentMode = BACKGROUND;
                this.selectedBackground = 0;
                initialiseBackgrounds();
                return true;
            }

            d0 = p_mouseClicked_1_ - (double) (i - 106);
            d1 = p_mouseClicked_3_ - (double) (j + 48);
            if (d0 >= 0.0D && d1 >= 0.0D && d0 < 27.0D && d1 < 23.0D) {
                this.currentMode = RGB;
                this.rBox.setVisible(true);
                this.gBox.setVisible(true);
                this.bBox.setVisible(true);
                this.selectedBackground = 0;
                return true;
            }

            d0 = p_mouseClicked_1_ - (double) (i - 106);
            d1 = p_mouseClicked_3_ - (double) (j + 83);
            if (d0 >= 0.0D && d1 >= 0.0D && d0 < 27.0D && d1 < 23.0D) {
                this.currentMode = TRANSPARENCY;
                this.selectedBackground = 0;
                return true;
            }
            //-------------

            //Mode Functions

            //Background arrows
            if (this.currentMode == BACKGROUND) {
                //Left Arrow
                d0 = p_mouseClicked_1_ - (double) (i - 77.5);
                d1 = p_mouseClicked_3_ - (double) (j + 24.5);
                if (d0 >= 0.0D && d1 >= 0.0D && d0 < 4.3D && d1 < 5.5D) {
                    if (this.selectedBackground > 0) {
                        this.selectedBackground -= 1;
                        initialiseBackgrounds();
                    }
                    return true;
                }
                //Right Arrow
                d0 = p_mouseClicked_1_ - (double) (i - 71);
                d1 = p_mouseClicked_3_ - (double) (j + 24.5);
                if (d0 >= 0.0D && d1 >= 0.0D && d0 < 4.0D && d1 < 5.5D) {
                    if (this.selectedBackground < this.maxBackgrounds) {
                        this.selectedBackground += 1;
                        initialiseBackgrounds();
                    }
                    return true;
                }
            }

            //Angle Rotation Arrows
            //Left Arrow
            d0 = p_mouseClicked_1_ - (double) (i + 243);
            d1 = p_mouseClicked_3_ - (double) (j + 171);
            if (d0 >= 0.0D && d1 >= 0.0D && d0 < 15.5D && d1 < 15.5D) {
                xPos -= 1;
                return true;
            }

            //Right Arrow
            d0 = p_mouseClicked_1_ - (double) (i + 278);
            d1 = p_mouseClicked_3_ - (double) (j + 171);
            if (d0 >= 0.0D && d1 >= 0.0D && d0 < 15.5D && d1 < 15.5D) {
                xPos += 1;
                return true;
            }

            //Up Arrow
            d0 = p_mouseClicked_1_ - (double) (i + 260);
            d1 = p_mouseClicked_3_ - (double) (j + 151);
            if (d0 >= 0.0D && d1 >= 0.0D && d0 < 15.5D && d1 < 15.5D) {
                yPos -= 1;
                return true;
            }

            //Down Arrow
            d0 = p_mouseClicked_1_ - (double) (i + 260);
            d1 = p_mouseClicked_3_ - (double) (j + 191);
            if (d0 >= 0.0D && d1 >= 0.0D && d0 < 15.5D && d1 < 15.5D) {
                yPos += 1;
                return true;
            }

            //Centre Reset
            d0 = p_mouseClicked_1_ - (double) (i + 263);
            d1 = p_mouseClicked_3_ - (double) (j + 171);
            if (d0 >= 0.0D && d1 >= 0.0D && d0 < 10.5D && d1 < 10.5D) {
                xPos = 0;
                yPos = 0;
                return true;
            }

        }

        if (!this.photoModeEnabled) {
            d0 = p_mouseClicked_1_ - (double) (i + 82);
            d1 = p_mouseClicked_3_ - (double) (j - 28);
            if (d0 >= 0.0D && d1 >= 0.0D && d0 < 27.0D && d1 < 27.0D) {
                this.photoModeEnabled = true;
                this.chestTabEnabled = false;
                this.menu.getAnimal().isInPhotoMode = true;
                this.selectedBackground = 0;
                return true;
            }
        }

        this.dragCamera = true;
        this.dragOriginX = (float)p_mouseClicked_1_;
        this.dragOriginY = (float)p_mouseClicked_3_;


        return super.mouseClicked(p_mouseClicked_1_, p_mouseClicked_3_, p_mouseClicked_5_);
    }

    public boolean mouseReleased(double p_mouseClicked_1_, double p_mouseClicked_3_, int p_mouseClicked_5_) {
        if (p_mouseClicked_5_ == 0) {
            this.dragCamera = false;
        }

        return super.mouseReleased(p_mouseClicked_1_, p_mouseClicked_3_, p_mouseClicked_5_);
    }

    public boolean mouseDragged(double p_98535_, double p_98536_, int p_98537_, double p_98538_, double p_98539_) {
        if (this.dragCamera) {
            this.dragOffsetX = -(this.dragOriginX-((float)p_98535_));
            this.dragOffsetY = (this.dragOriginY-((float)p_98536_));
            return true;
        } else {
            return super.mouseDragged(p_98535_, p_98536_, p_98537_, p_98538_, p_98539_);
        }
    }

    private void initialiseBackgrounds() {
        File configDirectory = new File(FMLPaths.CONFIGDIR.get().toString() + File.separator + "genetic_animals" + File.separator + "photo_backgrounds");

        if (configDirectory.exists() || configDirectory.mkdirs()) {
            File[] files = configDirectory.listFiles();

            if (files != null) {
                List<Object> combinedList = new ArrayList<>();
                List<File> fileList = Arrays.asList(files);
//                fileList.sort(Comparator.comparing(File::getName));

                combinedList.addAll(fileList);

                try {
                    Collection<ResourceLocation> backgroundResources = Minecraft.getInstance().getResourceManager().listResources("textures/gui/photo_backgrounds", (pic) -> {
                        String[] possibleSuffixes = { ".jpeg", ".jpg", ".png" };
                        return Arrays.stream(possibleSuffixes)
                                .anyMatch(pic::endsWith);
                    });

                    combinedList.addAll(backgroundResources);

                    this.maxBackgrounds = combinedList.size()-1;

                    combinedList.sort(Comparator.comparing(o -> {
                        if (o instanceof File) {
                            return ((File) o).getName();
                        } else if (o instanceof ResourceLocation) {
                            return ((ResourceLocation) o).getPath();
                        }
                        return "";
                    }));


                    Object selectedImage = combinedList.get(this.selectedBackground);

                    if (previousSelectionWasFile) {
                        //This is to unload the file from memory rather than keeping it loaded and re-registering it everytime
                        Minecraft.getInstance().getTextureManager().release(BACKGROUND_TEXTURE);
                    }

                    if (selectedImage instanceof File) {
                        previousSelectionWasFile = true;
                        InputStream inputStream = Files.newInputStream(((File)selectedImage).toPath());

                        NativeImage nativeImage = NativeImage.read(inputStream);

                        this.backgroundWidth = nativeImage.getWidth();
                        this.backgroundHeight = nativeImage.getHeight();

                        DynamicTexture dynamicTexture = new DynamicTexture(nativeImage);
                        BACKGROUND_TEXTURE = Minecraft.getInstance().getTextureManager().register(((File)selectedImage).getName(), dynamicTexture);

                    } else if (selectedImage instanceof ResourceLocation) {
                        previousSelectionWasFile = false;
                        BACKGROUND_TEXTURE = (ResourceLocation) selectedImage;
                        NativeImage resourceBackgroundAsNative = NativeImage.read(Minecraft.getInstance().getResourceManager().getResource(BACKGROUND_TEXTURE) .getInputStream());

                        this.backgroundWidth = resourceBackgroundAsNative.getWidth();
                        this.backgroundHeight = resourceBackgroundAsNative.getHeight();
                    }


                } catch (Exception e) {
                }

            }


        }
    }

    private void screenshotAnimal(Consumer<Component> uiMessage) {
        NativeImage nativeimage = takeScreenshot(this.minecraft.getMainRenderTarget());

        File file1 = new File(this.minecraft.gameDirectory, "screenshots");
        file1.mkdir();
        File file2 = getFile(file1);

        net.minecraftforge.client.event.ScreenshotEvent event = net.minecraftforge.client.ForgeHooksClient.onScreenshot(nativeimage, file2);
        if (event.isCanceled()) {
            uiMessage.accept(event.getCancelMessage());
            return;
        }
        final File target = event.getScreenshotFile();

        Util.ioPool().execute(() -> {
            try {
                nativeimage.writeToFile(target);
                Component component = (new TextComponent(file2.getName())).withStyle(ChatFormatting.UNDERLINE).withStyle((p_168608_) -> {
                    return p_168608_.withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_FILE, target.getAbsolutePath()));
                });
                if (event.getResultMessage() != null)
                    uiMessage.accept(event.getResultMessage());
                else
                    uiMessage.accept(new TranslatableComponent("screenshot.success", component));
            } catch (Exception exception) {
                System.out.print("Couldn't save screenshot: " + (Throwable)exception);
                uiMessage.accept(new TranslatableComponent("screenshot.failure", exception.getMessage()));
            } finally {
                nativeimage.close();
            }
        });
    }

    public NativeImage takeScreenshot(RenderTarget renderTarget) {
        int i = renderTarget.width;
        int j = renderTarget.height;
        NativeImage nativeimage = new NativeImage(i, j, false);
        RenderSystem.bindTexture(renderTarget.getColorTextureId());
        nativeimage.downloadTexture(0, true);
        nativeimage.flipY();
        return selectPhotoArea(nativeimage);
    }

    private NativeImage selectPhotoArea(NativeImage wholeScreenshot) {
        NativeImage selectedPhotoArea = new NativeImage(wholeScreenshot.getWidth()/2, wholeScreenshot.getHeight()/2, false);

        int startWidth = wholeScreenshot.getWidth() / 4;
        int startHeight = wholeScreenshot.getHeight() / 4;

        for(int i = 0; i < selectedPhotoArea.getHeight(); ++i) {
            for(int j = 0; j < selectedPhotoArea.getWidth(); ++j) {
                int pixel = wholeScreenshot.getPixelRGBA(startWidth+j, startHeight+i) | 255 << NativeImage.Format.RGBA.alphaOffset();
                if (this.currentMode == TRANSPARENCY && pixel == greenScreenColour) {
                    selectedPhotoArea.setPixelRGBA(j, i, transparentColour);
                } else {
                    selectedPhotoArea.setPixelRGBA(j, i, pixel);
                }
            }
        }
        return selectedPhotoArea;
    }

    private File getFile(File p_92288_) {
        String s = DATE_FORMAT.format(new Date());
        int i = 1;

        while(true) {
            File file1 = new File(p_92288_, getAnimalName() + "_" + s + (i == 1 ? "" : "_" + i) + ".png");
            if (!file1.exists()) {
                return file1;
            }

            ++i;
        }
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
    protected void renderLabels(PoseStack matrixStack, int mouseX, int mouseY) {
        if (!this.photoModeEnabled) {
            String name = getAnimalName();
            this.font.draw(matrixStack, name, 8.0F, (float)(this.imageHeight - 160), 4210752);
            //TODO how to get the correct inventory name? old : this.inventory.toString().getDisplayName().getString()
            this.font.draw(matrixStack, "Inventory", 8.0F, (float)(this.imageHeight - 94), 4210752);
        }
    }

    @NotNull
    private String getAnimalName() {
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
                age = new TranslatableComponent("eanimod.animalinfocontainer.young").getString();
            } else if (age.equals("BABY")) {
                age = new TranslatableComponent("eanimod.animalinfocontainer.baby").getString();
            } else {
                age = new TranslatableComponent("eanimod.animalinfocontainer.newborn").getString();
            }
            name = age+" "+name;
        }

        if (name.length() > 20) {
            name = name.substring(0, 20);
        }
        return name;
    }

    @Override
    protected void renderBg(PoseStack matrixStack, float partialTicks, int mouseX, int mouseY) {
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

        if (!photoModeEnabled) {
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            RenderSystem.setShaderTexture(0, GUI_TEXTURE);
            Container retrievedInventory = this.menu.getEnhancedAnimalInventory();
            int i = (this.width - this.imageWidth) / 2;
            int j = (this.height - this.imageHeight) / 2;
            int shiftY = 17;
            int shiftX = 7;
            this.blit(matrixStack, i, j, 0, 0, this.imageWidth, this.imageHeight);

            if (EanimodCommonConfig.COMMON.omnigenders.get()) {
                this.blit(matrixStack, i + 126, j + 5, 125, this.imageHeight + 74, 8, 10); // pregnancy icon
                int pregnancy = enhancedAnimalInfo.pregnant;
                this.blit(matrixStack, i + 126, j + 4 + (11 - pregnancy), 133, this.imageHeight + 74 + (10 - pregnancy), 8, pregnancy); // pregnancy icon
            } else {
                if (enhancedAnimalInfo.isFemale) {
                    this.blit(matrixStack, i + 126, j + 5, 117, this.imageHeight + 54, 8, 10); // female icon
                    int pregnancy = enhancedAnimalInfo.pregnant;
                    this.blit(matrixStack, i + 126, j + 4 + (11 - pregnancy), 117, this.imageHeight + 64 + (10 - pregnancy), 8, pregnancy); // female icon
                } else {
                    this.blit(matrixStack, i + 126, j + 5, 108, this.imageHeight + 54, 8, 10); // male icon
                    int pregnancy = enhancedAnimalInfo.pregnant;
                    this.blit(matrixStack, i + 126, j + 4 + (11 - pregnancy), 108, this.imageHeight + 64 + (10 - pregnancy), 8, pregnancy); // male icon
                }
            }

            this.blit(matrixStack, i + 136, j + 5, 125, this.imageHeight + 54, 9, 10); // health icon
            this.blit(matrixStack, i + 147, j + 5, 134, this.imageHeight + 54, 9, 10); // hunger icon
            this.blit(matrixStack, i + 158, j + 5, 143, this.imageHeight + 54, 10, 10); // tameness icon

            int health = Math.min(enhancedAnimalInfo.health, 10);
            int hunger = 10 - enhancedAnimalInfo.hunger;
    //        int tameness = enhancedAnimalInfo.tameness;
            this.blit(matrixStack, i + 136, j + 5 + (10-health), 125, this.imageHeight + 64 + (10-health), 9, health); // health icon
            this.blit(matrixStack, i + 147, j + 5 + (10-hunger), 134, this.imageHeight + 64 + (10-hunger), 9, hunger); // hunger icon
    //        this.blit(i + 158, j + 5 + (10-tameness), 143, this.ySize + 64 + (10-tameness), 10, tameness); // tameness icon

            if (this.menu.enhancedAnimal.canHaveSaddle()) {
                if (retrievedInventory.getItem(1).isEmpty()) {
                    this.blit(matrixStack, i + shiftX, j + shiftY, 0, this.imageHeight + 54, 18, 18);
                } else {
                    this.blit(matrixStack, i + shiftX, j + shiftY, 54, this.imageHeight + 36, 18, 18);
                }
                shiftY = shiftY + 18;
            }
            if (this.menu.enhancedAnimal.canHaveBridle()) {
                if (retrievedInventory.getItem(3).isEmpty()) {
                    this.blit(matrixStack, i + shiftX, j + shiftY, 18, this.imageHeight + 54, 18, 18);
                } else {
                    this.blit(matrixStack, i + shiftX, j + shiftY, 54, this.imageHeight + 36, 18, 18);
                }
                shiftY = shiftY + 18;
            }
            if (this.menu.enhancedAnimal.canHaveArmour()) {
                if (retrievedInventory.getItem(2).isEmpty()) {
                    this.blit(matrixStack, i + shiftX, j + shiftY, 36, this.imageHeight + 54, 18, 18);
                } else {
                    this.blit(matrixStack, i + shiftX, j + shiftY, 54, this.imageHeight + 36, 18, 18);
                }
                shiftY = shiftY + 18;
                if (shiftY >= 69) {
                    shiftY = 17;
                    shiftX = 79;
                }
            }
            if (this.menu.enhancedAnimal.canHaveBlanket() && (shiftX == 7 || !this.chestTabEnabled)) {
                if (retrievedInventory.getItem(4).isEmpty()) {
                    this.blit(matrixStack, i + shiftX, j + shiftY, 54, this.imageHeight + 54, 18, 18);
                } else {
                    this.blit(matrixStack, i + shiftX, j + shiftY, 54, this.imageHeight + 36, 18, 18);
                }
                shiftY = shiftY + 18;
                if (shiftY >= 69) {
                    shiftY = 17;
                    shiftX = 79;
                }
            }
            if (this.menu.enhancedAnimal.canHaveBanner() && (shiftX == 7 || !this.chestTabEnabled)) {
                if (retrievedInventory.getItem(6).isEmpty()) {
                    this.blit(matrixStack, i + shiftX, j + shiftY, 72, this.imageHeight + 54, 18, 18);
                } else {
                    this.blit(matrixStack, i + shiftX, j + shiftY, 54, this.imageHeight + 36, 18, 18);
                }
                shiftY = shiftY + 18;
                if (shiftY >= 69) {
                    shiftY = 17;
                    shiftX = 79;
                }
            }
            if (this.menu.enhancedAnimal.canHaveHarness() && (shiftX == 7 || !this.chestTabEnabled)) {
                if (retrievedInventory.getItem(5).isEmpty()) {
                    this.blit(matrixStack, i + shiftX, j + shiftY, 90, this.imageHeight + 54, 18, 18);
                } else {
                    this.blit(matrixStack, i + shiftX, j + shiftY, 54, this.imageHeight + 36, 18, 18);
                }
                shiftY = shiftY + 18;
                if (shiftY >= 69) {
                    shiftY = 17;
                    shiftX = 79;
                }
            }

            if (shiftY==17 && shiftX==7) {
                this.blit(matrixStack, i + 7, j + 17, 0, this.imageHeight + 72, 18, 18);
            }

            renderTabs(matrixStack, i, j, 0, 0, 256, 256);

            if (this.enhancedAnimalInfo.created && !this.photoModeEnabled) {
                InventoryScreen.renderEntityInInventory(i + 51, j + 60, 17, (float)(i + 51) - this.mousePosx, (float)(j + 75 - 50) - this.mousePosY, (LivingEntity) this.menu.getAnimal());
            }

            if (!this.chestTabEnabled && !this.photoModeEnabled) {
                Integer ageInt = this.enhancedAnimalInfo.age/24000;
    //            Float ageFloat = ageInt >= 20 ? (float)(ageInt/10) : (float)ageInt/10.0F;
                String age = "";
                if (ageInt < 8) {
                    age = ageInt.toString() + (new TranslatableComponent("eanimod.animalinfocontainer.days").getString());
                } else if (ageInt < 96) {
                    ageInt = ageInt/8;
                    age = ageInt.toString() + (new TranslatableComponent("eanimod.animalinfocontainer.months").getString());
                } else if (ageInt < 959040) {
                    ageInt = ageInt/96;
                    age = ageInt.toString() + (new TranslatableComponent("eanimod.animalinfocontainer.years").getString());
                } else {
                    age = new TranslatableComponent("eanimod.animalinfocontainer.ancient").getString();
                }
                this.font.draw(matrixStack, (new TranslatableComponent("eanimod.animalinfocontainer.age").getString()) + ":" + age, i + 99, j + 20, 4210752);

                String sireName = this.enhancedAnimalInfo.sire;
                int s = sireName.length();
                String damName = this.enhancedAnimalInfo.dam;
                int d = damName.length();
                if (s > 8) {
                    this.font.draw(matrixStack, new TranslatableComponent("eanimod.animalinfocontainer.sire").getString()+":", i + 99, j + 30, 4210752);
                    this.font.draw(matrixStack, s > 12 ? sireName.substring(0, 12) : sireName, i + 99, j + 40, 4210752);
                    if (d > 8) {
                        this.font.draw(matrixStack, new TranslatableComponent("eanimod.animalinfocontainer.dam").getString()+":", i + 99, j + 51, 4210752);
                        this.font.draw(matrixStack, d > 12 ? damName.substring(0, 12) : damName, i + 99, j + 60, 4210752);
                    } else {
                        this.font.draw(matrixStack, new TranslatableComponent("eanimod.animalinfocontainer.dam").getString()+":" + damName, i + 99, j + 50, 4210752);
                    }
                } else {
                    this.font.draw(matrixStack, new TranslatableComponent("eanimod.animalinfocontainer.sire").getString()+":" + sireName, i + 99, j + 30, 4210752);
                    if (d > 8) {
                        this.font.draw(matrixStack, new TranslatableComponent("eanimod.animalinfocontainer.dam").getString()+":", i + 99, j + 41, 4210752);
                        this.font.draw(matrixStack, d > 12 ? damName.substring(0, 12) : damName, i + 99, j + 50, 4210752);
                    } else {
                        this.font.draw(matrixStack, new TranslatableComponent("eanimod.animalinfocontainer.dam").getString()+":" + damName, i + 99, j + 40, 4210752);
                    }
                }
            }

        }
    }

    private void renderTabs(PoseStack matrixStack, int i, int j, int offsetX, int offsetTextureX, int sizeX, int sizeY) {
        if (this.photoModeEnabled) {
            if (EanimodCommonConfig.COMMON.tabsOnTop.get()) {
                this.blit(matrixStack, i + 82+offsetX, j - 28, 0, 209+offsetTextureX, 131, 28, 31, sizeX, sizeY); //highlight tab
                this.blit(matrixStack, i + 111+offsetX,j - 28, 0,   177+offsetTextureX, 131, 28, 31, sizeX, sizeY); //shadow tab
                this.blit(matrixStack, i + 140+offsetX,j - 28, 0,   177+offsetTextureX, 131, 28, 31, sizeX, sizeY); //shadow tab
                this.blit(matrixStack, i + 88+offsetX,j - 19, 0,   217+offsetTextureX, 107, 18, 14, sizeX, sizeY); //highlight photo logo
                this.blit(matrixStack, i + 117+offsetX, j - 18, 0,   184+offsetTextureX, 23, 15, 14, sizeX, sizeY); //shadow chest logo
                this.blit(matrixStack, i + 144+offsetX, j - 17, 0,   182+offsetTextureX, 51, 18, 14, sizeX, sizeY); //shadow info logo
            } else {
                this.blit(matrixStack, i + 173+offsetX, j + 13, 209+offsetTextureX, 16, 31, 28, sizeX, sizeY); //highlight photo
                this.blit(matrixStack, i + 173+offsetX, j + 41, 177+offsetTextureX, 16, 30, 28, sizeX, sizeY); //shadow chest
                this.blit(matrixStack, i + 173+offsetX, j + 69, 177+offsetTextureX, 44, 30, 28, sizeX, sizeY); //shadow info
            }
        } else if (this.chestTabEnabled) {
            if (EanimodCommonConfig.COMMON.tabsOnTop.get()) {
                this.blit(matrixStack, i + 82+offsetX, j - 28, 0,   177+offsetTextureX, 131, 28, 31, sizeX, sizeY); //shadow tab
                this.blit(matrixStack, i + 111+offsetX, j - 28, 0,   209+offsetTextureX, 131, 28, 31, sizeX, sizeY); //highlight tab
                this.blit(matrixStack, i + 140+offsetX, j - 28, 0,   177+offsetTextureX, 131, 28, 31, sizeX, sizeY); //shadow tab
                this.blit(matrixStack, i + 88+offsetX, j - 18, 0,   184+offsetTextureX, 107, 18, 14, sizeX, sizeY); //shadow photo logo
                this.blit(matrixStack, i + 117+offsetX, j - 19, 0,   217+offsetTextureX, 23, 15, 14, sizeX, sizeY); //highlight chest logo
                this.blit(matrixStack, i + 144+offsetX, j - 17, 0,   182+offsetTextureX, 51, 18, 14, sizeX, sizeY); //shadow info logo
            } else {
                this.blit(matrixStack, i + 173+offsetX, j + 13, 0,   177+offsetTextureX, 16, 30, 28, sizeX, sizeY); //shadow photo
                this.blit(matrixStack, i + 173+offsetX, j + 41, 0,   209+offsetTextureX, 16, 31, 28, sizeX, sizeY); //highlight chest
                this.blit(matrixStack, i + 173+offsetX, j + 69, 0,   177+offsetTextureX, 44, 30, 28, sizeX, sizeY); //shadow info
            }
            Container retrievedInventory = this.menu.getEnhancedAnimalInventory();
            boolean hasItemsInChest = isHasItemsInChest(retrievedInventory);
            int invSize = 5;

            if (retrievedInventory.getItem(0).getItem() == Items.CHEST) {
                if (hasItemsInChest) {
                    this.blit(matrixStack, i + 79, j + 17, 0, this.imageHeight, 18*invSize, 54);
                } else {
                    this.blit(matrixStack, i + 79, j + 17, 90, this.imageHeight, 90, 54);
//                        this.blit(matrixStack, i + 112, j + 31, 180, this.ySize, 24, 26);
                }
            } else {
                this.blit(matrixStack, i + 79, j + 17, 90, this.imageHeight, 90, 54);
            }
        } else {
            if (EanimodCommonConfig.COMMON.tabsOnTop.get()) {
                this.blit(matrixStack, i + 82+offsetX, j - 28, 0,   177+offsetTextureX, 131, 28, 31, sizeX, sizeY); //shadow tab
                this.blit(matrixStack, i + 111+offsetX, j - 28, 0,   177+offsetTextureX, 131, 28, 31, sizeX, sizeY); //shadow tab
                this.blit(matrixStack, i + 140+offsetX, j - 28, 0,   209+offsetTextureX, 131, 28, 31, sizeX, sizeY); //highlight tab
                this.blit(matrixStack, i + 88+offsetX, j - 18, 0,   184+offsetTextureX, 107, 18, 14, sizeX, sizeY); //shadow photo logo
                this.blit(matrixStack, i + 117+offsetX, j - 18, 0,   184+offsetTextureX, 23, 15, 14, sizeX, sizeY); //shadow chest logo
                this.blit(matrixStack, i + 144+offsetX, j - 18, 0,   215+offsetTextureX, 51, 18, 14, sizeX, sizeY); //highlight info logo
            } else {
                this.blit(matrixStack, i + 173+offsetX, j + 13, 0,   177+offsetTextureX, 16, 30, 28, sizeX, sizeY); //shadow photo
                this.blit(matrixStack, i + 173+offsetX, j + 41, 0,   177+offsetTextureX, 16, 30, 28, sizeX, sizeY); //shadow chest
                this.blit(matrixStack, i + 173+offsetX, j + 69, 0,   209+offsetTextureX, 44, 31, 28, sizeX, sizeY); //highlight info
            }
        }
    }

    public void renderEntityPhotoMode(int xPos, int yPose, int scale, float facingDirectionX, float facingDirectionY, LivingEntity entity) {
        float f = (float)Math.atan((facingDirectionX / 40.0F));
        float f1 = (float)Math.atan((facingDirectionY / 40.0F));
        PoseStack posestack = RenderSystem.getModelViewStack();
        posestack.pushPose();
        posestack.translate(xPos+ this.xPos, yPose+ yPos, 1050.0D);
        posestack.scale(1.0F, 1.0F, -1.0F);
        RenderSystem.applyModelViewMatrix();
        PoseStack posestack1 = new PoseStack();
        posestack1.translate(0.0D, 0.0D, 1000.0D);
        posestack1.scale((float)scale, (float)scale, (float)scale);
        Quaternion quaternion = Vector3f.ZP.rotationDegrees(180.0F);
        Quaternion quaternion1 = Vector3f.XP.rotationDegrees(f1 * 20.0F + dragOffsetY);
        Quaternion quaternion2 = Vector3f.YP.rotationDegrees(f1 * 20.0F + dragOffsetX);
        quaternion.mul(quaternion1);
        quaternion.mul(quaternion2);
        posestack1.mulPose(quaternion);
        float originalYBodyRot = entity.yBodyRot;
        float originalYRot = entity.getYRot();
        float originalXRot = entity.getXRot();
        float originalYHeadRot0 = entity.yHeadRotO;
        float originalYHeadRot = entity.yHeadRot;
        entity.yBodyRot = 180.0F + f * 20.0F;
        entity.setYRot(180.0F + f * 40.0F);
        entity.setXRot(-f1 * 20.0F);
        entity.yHeadRot = entity.getYRot();
        entity.yHeadRotO = entity.getYRot();
        Lighting.setupForEntityInInventory();
        EntityRenderDispatcher entityrenderdispatcher = Minecraft.getInstance().getEntityRenderDispatcher();
        quaternion1.conj();
        entityrenderdispatcher.overrideCameraOrientation(quaternion1);
        entityrenderdispatcher.setRenderShadow(false);
        MultiBufferSource.BufferSource multibuffersource$buffersource = Minecraft.getInstance().renderBuffers().bufferSource();
        RenderSystem.runAsFancy(() -> {
            entityrenderdispatcher.render(entity, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F, posestack1, multibuffersource$buffersource, 15728880);
        });
        multibuffersource$buffersource.endBatch();
        entityrenderdispatcher.setRenderShadow(true);
        entity.yBodyRot = originalYBodyRot;
        entity.setYRot(originalYRot);
        entity.setXRot(originalXRot);
        entity.yHeadRotO = originalYHeadRot0;
        entity.yHeadRot = originalYHeadRot;
        posestack.popPose();
        RenderSystem.applyModelViewMatrix();
        Lighting.setupFor3DItems();
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

    protected boolean isHovering(int p_97768_, int p_97769_, int p_97770_, int p_97771_, double p_97772_, double p_97773_) {
        if (!this.photoModeEnabled) {
            return super.isHovering(p_97768_, p_97769_, p_97770_, p_97771_, p_97772_, p_97773_);
        }
        return false;
    }

    protected boolean isHoveringPhotoMode(int p_97768_, int p_97769_, int p_97770_, int p_97771_, double p_97772_, double p_97773_) {
        return super.isHovering(p_97768_, p_97769_, p_97770_, p_97771_, p_97772_, p_97773_);
    }

    @Override
    public void onClose() {
        this.menu.getAnimal().isInPhotoMode = false;
        super.onClose();
    }

}
