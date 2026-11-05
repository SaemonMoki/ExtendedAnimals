package mokiyoki.enhancedanimals.gui;

import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.math.Matrix4f;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import mokiyoki.enhancedanimals.config.GeneticAnimalsConfig;
import mokiyoki.enhancedanimals.entity.EnhancedAnimalAbstract;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Checkbox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.loading.FMLPaths;
import org.lwjgl.opengl.GL11;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.function.Consumer;

@OnlyIn(Dist.CLIENT)
public class EnhancedAnimalPhotoScreen extends Screen {
    private static final ResourceLocation PHOTO_MODE_GUI_TEXTURE = new ResourceLocation("eanimod:textures/gui/genetic_animal_photo_gui.png");
    private static final int PHOTO_GUI_TEXTURE_WIDTH = 384;
    private static final int PHOTO_GUI_TEXTURE_HEIGHT = 256;
    private static final int INVENTORY_GUI_TEXTURE_SIZE = 256;
    // where the plain and highlighted versions of the mode buttons sit across the photo gui texture
    private static final int BUTTON_U = 303;
    private static final int BUTTON_HIGHLIGHTED_U = 335;

    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss");

    private static final int BLACK = 0xFF000000;
    private static final int WHITE = 0xFFFFFFFF;
    private static final int TRANSPARENT_COLOUR = 0;
    private static final int STICKER_INNER_OUTLINE_COLOUR = BLACK;
    private static final int STICKER_OUTER_OUTLINE_COLOUR = WHITE;

    // everything below is placed relative to leftPos and topPos, the same corner the animal's inventory screen uses

    // the photo area starts at the bottom of the tabs and take photo button, which hang 3 pixels into the frame
    private static final int PHOTO_X = -63;
    private static final int PHOTO_Y = 3;
    private static final int PHOTO_WIDTH = 302;
    private static final int PHOTO_HEIGHT = 163;

    // where the animal stands, and the screen positions its starting facing is worked out from, the same way vanilla's
    // inventory works it out from the mouse
    private static final int ANIMAL_X = 107;
    private static final int ANIMAL_Y = 132;
    private static final int FACING_X = -12;
    private static final int FACING_Y = -43;

    private static final PhotoButton TAKE_PHOTO_BUTTON = new PhotoButton(-37, -28, 28, 31);
    private static final PhotoButton RESET_BUTTON = new PhotoButton(262, 171, 12, 12);
    private static final PhotoButton BACKGROUND_BUTTON = new PhotoButton(-109, 10, 28, 31);
    private static final PhotoButton RGB_BUTTON = new PhotoButton(-109, 45, 28, 31);
    private static final PhotoButton TRANSPARENT_BUTTON = new PhotoButton(-109, 80, 28, 31);
    private static final PhotoButton PREVIOUS_BACKGROUND_ARROW = new PhotoButton(-78, 22, 5, 7);
    private static final PhotoButton NEXT_BACKGROUND_ARROW = new PhotoButton(-71, 22, 5, 7);
    private static final PhotoButton STICKER_STYLE_BOX = new PhotoButton(-107, 125, 20, 20);
    private static final PhotoButton CHEST_TAB = new PhotoButton(111, -28, 28, 31);
    private static final PhotoButton INFO_TAB = new PhotoButton(140, -28, 28, 31);

    // the paint style colour picker the rgb button opens, picking a colour from it switches to rgb mode with that colour
    private static final int PICKER_X = -59;
    private static final int PICKER_Y = 32;
    private static final int PICKER_SPECTRUM_WIDTH = 128;
    private static final int PICKER_SPECTRUM_HEIGHT = 64;
    private static final int PICKER_GREY_HEIGHT = 8;
    private static final int PICKER_GAP = 2;
    private static final int PICKER_BORDER = 2;
    private static final int PICKER_BACKGROUND_COLOUR = 0xFFC6C6C6;

    // scrolling zooms the animal in and out, the gui is orthographic so this changes its scale rather than its distance
    private static final float DEFAULT_SCALE = 70.0F;
    private static final float MIN_SCALE = 20.0F;
    private static final float MAX_SCALE = 250.0F;
    private static final double SCALE_STEP = 1.1D;

    // the gui only draws between z -1000 and 1000 and the animal is centred around z 50, so the backgrounds sit right at
    // the back where even an animal a few blocks deep at MAX_SCALE can't reach behind them and go invisible
    private static final int BACKGROUND_DEPTH = -950;

    private final EnhancedAnimalScreen inventoryScreen;
    private final EnhancedAnimalAbstract animal;

    private int leftPos;
    private int topPos;

    private PhotoMode currentMode = PhotoMode.TRANSPARENCY;

    private ResourceLocation backgroundTexture = new ResourceLocation("eanimod:textures/gui/photo_backgrounds/basic.png");
    private int selectedBackground = 0;
    private int maxBackgrounds = 0;
    private boolean previousSelectionWasFile = false;

    private int currentBackgroundColour = BLACK;

    // the photo is taken partway through the next render, before the colour picker and tooltips are drawn over it
    private boolean prepareForScreenshot = false;

    private boolean colourPickerOpen = false;
    // the part of the picker being dragged on, the colour follows the mouse until it's released and the picker closes
    private PickerArea pickerDragArea = PickerArea.NONE;

    private Checkbox stickerStyleBox;

    private float scale = DEFAULT_SCALE;
    private float animalYaw = 0.0F;
    private float animalPitch = 0.0F;
    private double animalOffsetX = 0.0D;
    private double animalOffsetY = 0.0D;

    private boolean draggingAnimal = false;
    // left drag rotates the animal and right drag moves it around the photo, this is the button holding the current drag
    private int dragButton = 0;
    private float dragOriginX = 0.0F;
    private float dragOriginY = 0.0F;
    // the rotation and position when the current drag started, so a new drag carries on from them instead of starting over
    private float dragStartYaw = 0.0F;
    private float dragStartPitch = 0.0F;
    private double dragStartOffsetX = 0.0D;
    private double dragStartOffsetY = 0.0D;

    private enum PhotoMode {
        BACKGROUND,
        RGB,
        TRANSPARENCY
    }

    private enum PickerArea {
        NONE,
        SPECTRUM,
        GREY
    }

    // one place for each button's position and size, used for drawing it, clicking it and its tooltip
    private record PhotoButton(int x, int y, int width, int height) {}

    public EnhancedAnimalPhotoScreen(EnhancedAnimalScreen inventoryScreen) {
        super(inventoryScreen.getTitle());
        this.inventoryScreen = inventoryScreen;
        this.animal = inventoryScreen.getMenu().getAnimal();
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(poseStack);
        renderCameraMode(poseStack, mouseX, mouseY, partialTick);
        super.render(poseStack, mouseX, mouseY, partialTick);
        this.renderInfoToolTip(poseStack, mouseX, mouseY);
    }

    @Override
    protected void init() {
        // same placement the inventory screen uses, so all the photo mode offsets line up
        this.leftPos = (this.width - this.inventoryScreen.getXSize()) / 2;
        this.topPos = (this.height - this.inventoryScreen.getYSize()) / 2;

        // this screen is kept by the inventory screen and re-initialised each time it's shown, so keep the previous value
        boolean stickerStyle = this.stickerStyleBox != null && this.stickerStyleBox.selected();

        // not added as a widget, it only takes clicks in transparent mode where it's shown
        this.stickerStyleBox = new Checkbox(this.leftPos + STICKER_STYLE_BOX.x(), this.topPos + STICKER_STYLE_BOX.y(), STICKER_STYLE_BOX.width(), STICKER_STYLE_BOX.height(), new TranslatableComponent("photomode.stickerstyle"), stickerStyle, false);
    }

    private void renderCameraMode(PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
        int photoLeft = photoLeft();
        int photoTop = photoTop();

        // the container screen left depth testing on before photo mode drew, keep that the same here
        RenderSystem.enableDepthTest();

        // drawn before the tabs so they sit on top of its top edge
        renderPhotoFrameBorder(poseStack, photoLeft, photoTop);

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

        RenderSystem.setShaderTexture(0, EnhancedAnimalScreen.GUI_TEXTURE);
        renderTabs(poseStack, this.leftPos, this.topPos);

        RenderSystem.setShaderTexture(0, PHOTO_MODE_GUI_TEXTURE);

        //Take Photo Button
        renderButton(poseStack, TAKE_PHOTO_BUTTON, 335, 112);

        //Reset Button
        renderButton(poseStack, RESET_BUTTON, 345, 171);

        //highlight tab
        if (this.currentMode == PhotoMode.RGB) {
            //Background Button
            renderButton(poseStack, BACKGROUND_BUTTON, BUTTON_U, 16);
            //RGB Button
            renderButton(poseStack, RGB_BUTTON, BUTTON_HIGHLIGHTED_U, 48);
            //Transparency Button
            renderButton(poseStack, TRANSPARENT_BUTTON, BUTTON_U, 80);

            renderCameraBackground(poseStack, photoLeft, photoTop, BACKGROUND_DEPTH, 0, 0, PHOTO_WIDTH, PHOTO_HEIGHT, PHOTO_GUI_TEXTURE_WIDTH, PHOTO_GUI_TEXTURE_HEIGHT);

        } else if (this.currentMode == PhotoMode.BACKGROUND) {
            //Background Button
            renderButton(poseStack, BACKGROUND_BUTTON, BUTTON_HIGHLIGHTED_U, 16);
            //Arrows
            renderButton(poseStack, PREVIOUS_BACKGROUND_ARROW, 368, 26);
            renderButton(poseStack, NEXT_BACKGROUND_ARROW, 375, 26);
            //RGB Button, highlighted while its colour picker is open
            renderButton(poseStack, RGB_BUTTON, this.colourPickerOpen ? BUTTON_HIGHLIGHTED_U : BUTTON_U, 48);
            //Transparency Button
            renderButton(poseStack, TRANSPARENT_BUTTON, BUTTON_U, 80);

            RenderSystem.setShaderTexture(0, this.backgroundTexture);

            // stretch the background image to fill the photo
            drawScaledImage(photoLeft, photoTop);

        } else if (this.currentMode == PhotoMode.TRANSPARENCY) {
            //Background Button
            renderButton(poseStack, BACKGROUND_BUTTON, BUTTON_U, 16);
            //RGB Button, highlighted while its colour picker is open
            renderButton(poseStack, RGB_BUTTON, this.colourPickerOpen ? BUTTON_HIGHLIGHTED_U : BUTTON_U, 48);
            //Transparency Button
            renderButton(poseStack, TRANSPARENT_BUTTON, BUTTON_HIGHLIGHTED_U, 80);

            if (this.prepareForScreenshot) {
                // first of the two passes, the animal is captured on black here and again on white once it's drawn
                renderSolidBackground(poseStack, photoLeft, photoTop, BLACK);
            } else {
                this.stickerStyleBox.render(poseStack, mouseX, mouseY, partialTick);
            }
        }

        renderPhotoAnimal();

        if (this.prepareForScreenshot) {
            NativeImage screenshot = takeScreenshot(this.minecraft.getMainRenderTarget());
            NativeImage screenshotOnWhite = null;
            if (this.currentMode == PhotoMode.TRANSPARENCY) {
                // second pass, the same animal on white. clearing depth first lets the white cover the first pass
                RenderSystem.clear(GL11.GL_DEPTH_BUFFER_BIT, Minecraft.ON_OSX);
                renderSolidBackground(poseStack, photoLeft, photoTop, WHITE);
                renderPhotoAnimal();
                screenshotOnWhite = takeScreenshot(this.minecraft.getMainRenderTarget());
            }

            // nothing to save when the animal has been moved or zoomed entirely out of a transparent photo
            NativeImage photo = selectPhotoArea(screenshot, screenshotOnWhite);
            if (photo != null) {
                screenshotAnimal(photo, (animalScreenshotComponent) -> {
                    this.minecraft.execute(() -> {
                        this.minecraft.gui.getChat().addMessage(animalScreenshotComponent);
                    });
                });
            }
            screenshot.close();
            if (screenshotOnWhite != null) {
                screenshotOnWhite.close();
            }
            this.prepareForScreenshot = false;
        }

        if (this.colourPickerOpen) {
            renderColourPicker(poseStack);
        }
    }

    private void renderButton(PoseStack poseStack, PhotoButton button, int u, int v) {
        this.blit(poseStack, this.leftPos + button.x(), this.topPos + button.y(), 0, u, v, button.width(), button.height(), PHOTO_GUI_TEXTURE_WIDTH, PHOTO_GUI_TEXTURE_HEIGHT);
    }

    private boolean isOver(PhotoButton button, double mouseX, double mouseY) {
        double x = mouseX - (this.leftPos + button.x());
        double y = mouseY - (this.topPos + button.y());
        return x >= 0.0D && y >= 0.0D && x < button.width() && y < button.height();
    }

    private int photoLeft() {
        return this.leftPos + PHOTO_X;
    }

    private int photoTop() {
        return this.topPos + PHOTO_Y;
    }

    private void renderPhotoAnimal() {
        // clip the animal to the photo frame so a zoomed-in or moved animal doesn't spill over the buttons and tabs
        enablePhotoFrameScissor(photoLeft(), photoTop());
        renderEntityPhotoMode(this.leftPos + ANIMAL_X, this.topPos + ANIMAL_Y, this.scale, (float)(this.leftPos + FACING_X), (float)(this.topPos + FACING_Y), this.animal);
        RenderSystem.disableScissor();
    }

    // a white line with a black edge just outside the photo frame, showing what will be in the photo without being in it
    private void renderPhotoFrameBorder(PoseStack poseStack, int x, int y) {
        renderOutline(poseStack, x - 2, y - 2, x + PHOTO_WIDTH + 2, y + PHOTO_HEIGHT + 2, BLACK);
        renderOutline(poseStack, x - 1, y - 1, x + PHOTO_WIDTH + 1, y + PHOTO_HEIGHT + 1, WHITE);
    }

    // a one pixel line just inside the given edges, leaving the middle untouched
    private void renderOutline(PoseStack poseStack, int left, int top, int right, int bottom, int colour) {
        fill(poseStack, left, top, right, top + 1, colour);
        fill(poseStack, left, bottom - 1, right, bottom, colour);
        fill(poseStack, left, top + 1, left + 1, bottom - 1, colour);
        fill(poseStack, right - 1, top + 1, right, bottom - 1, colour);
    }

    // a plain colour with no texture under it, at the back so the animal is always in front of it
    private void renderSolidBackground(PoseStack poseStack, int x, int y, int colour) {
        poseStack.pushPose();
        poseStack.translate(0.0D, 0.0D, BACKGROUND_DEPTH);
        fill(poseStack, x, y, x + PHOTO_WIDTH, y + PHOTO_HEIGHT, colour);
        poseStack.popPose();
    }

    // scissor works in window pixels from the bottom left, the same conversion the vanilla scrolling lists use
    private void enablePhotoFrameScissor(int x, int y) {
        double guiScale = this.minecraft.getWindow().getGuiScale();
        RenderSystem.enableScissor((int)(x * guiScale), (int)(this.minecraft.getWindow().getHeight() - (y + PHOTO_HEIGHT) * guiScale), (int)(PHOTO_WIDTH * guiScale), (int)(PHOTO_HEIGHT * guiScale));
    }

    // sits over the left of the photo, level with the rgb button
    private int pickerLeft() {
        return this.leftPos + PICKER_X;
    }

    private int pickerTop() {
        return this.topPos + PICKER_Y;
    }

    private int pickerWidth() {
        return PICKER_SPECTRUM_WIDTH + PICKER_BORDER * 2;
    }

    private int pickerHeight() {
        return PICKER_SPECTRUM_HEIGHT + PICKER_GAP + PICKER_GREY_HEIGHT + PICKER_BORDER * 2;
    }

    private void renderColourPicker(PoseStack poseStack) {
        int left = pickerLeft();
        int top = pickerTop();
        int spectrumLeft = left + PICKER_BORDER;
        int spectrumTop = top + PICKER_BORDER;
        int greyTop = spectrumTop + PICKER_SPECTRUM_HEIGHT + PICKER_GAP;

        // a zoomed-in animal can come right up to the screen, so draw over it regardless of depth
        RenderSystem.disableDepthTest();
        fill(poseStack, left, top, left + pickerWidth(), top + pickerHeight(), BLACK);
        fill(poseStack, left + 1, top + 1, left + pickerWidth() - 1, top + pickerHeight() - 1, PICKER_BACKGROUND_COLOUR);

        // one flat quad per colour, so what is clicked is exactly the colour that was shown
        Matrix4f pose = poseStack.last().pose();
        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        BufferBuilder bufferbuilder = Tesselator.getInstance().getBuilder();
        bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
        for (int column = 0; column < PICKER_SPECTRUM_WIDTH; column++) {
            for (int row = 0; row < PICKER_SPECTRUM_HEIGHT; row++) {
                addPickerCell(bufferbuilder, pose, spectrumLeft + column, spectrumTop + row, 1, spectrumColour(column, row));
            }
            addPickerCell(bufferbuilder, pose, spectrumLeft + column, greyTop, PICKER_GREY_HEIGHT, greyColour(column));
        }
        bufferbuilder.end();
        BufferUploader.end(bufferbuilder);
        RenderSystem.enableDepthTest();
    }

    private void addPickerCell(BufferBuilder bufferbuilder, Matrix4f pose, int x, int y, int height, int colour) {
        bufferbuilder.vertex(pose, (float)x, (float)(y + height), 0.0F).color(colour).endVertex();
        bufferbuilder.vertex(pose, (float)(x + 1), (float)(y + height), 0.0F).color(colour).endVertex();
        bufferbuilder.vertex(pose, (float)(x + 1), (float)y, 0.0F).color(colour).endVertex();
        bufferbuilder.vertex(pose, (float)x, (float)y, 0.0F).color(colour).endVertex();
    }

    // hue runs left to right, and each column fades from white at the top through the full colour to black at the bottom
    private int spectrumColour(int column, int row) {
        float hue = (float)column / PICKER_SPECTRUM_WIDTH;
        float fade = (float)row / (PICKER_SPECTRUM_HEIGHT - 1);
        int rgb = fade < 0.5F ? Mth.hsvToRgb(hue, fade * 2.0F, 1.0F) : Mth.hsvToRgb(hue, 1.0F, 2.0F - fade * 2.0F);
        return 255 << 24 | rgb;
    }

    // white to black, since the spectrum on its own has no greys
    private int greyColour(int column) {
        int grey = 255 - column * 255 / (PICKER_SPECTRUM_WIDTH - 1);
        return 255 << 24 | grey << 16 | grey << 8 | grey;
    }

    private boolean isOverColourPicker(double mouseX, double mouseY) {
        return mouseX >= pickerLeft() && mouseX < pickerLeft() + pickerWidth() && mouseY >= pickerTop() && mouseY < pickerTop() + pickerHeight();
    }

    // which part of the picker a press landed on, presses on the border land on neither
    private PickerArea pickerAreaAt(double mouseX, double mouseY) {
        int column = Mth.floor(mouseX) - (pickerLeft() + PICKER_BORDER);
        int y = Mth.floor(mouseY) - (pickerTop() + PICKER_BORDER);
        if (column < 0 || column >= PICKER_SPECTRUM_WIDTH) {
            return PickerArea.NONE;
        }

        if (y >= 0 && y < PICKER_SPECTRUM_HEIGHT) {
            return PickerArea.SPECTRUM;
        } else if (y >= PICKER_SPECTRUM_HEIGHT + PICKER_GAP && y < PICKER_SPECTRUM_HEIGHT + PICKER_GAP + PICKER_GREY_HEIGHT) {
            return PickerArea.GREY;
        }
        return PickerArea.NONE;
    }

    // switches to rgb mode with the colour under the mouse. a drag stays on the part it started on and is held to its
    // edges, so dragging past the side of the spectrum keeps the edge colour instead of jumping to the grey bar
    private void pickColourAt(double mouseX, double mouseY, PickerArea area) {
        int column = Mth.clamp(Mth.floor(mouseX) - (pickerLeft() + PICKER_BORDER), 0, PICKER_SPECTRUM_WIDTH - 1);
        if (area == PickerArea.SPECTRUM) {
            int row = Mth.clamp(Mth.floor(mouseY) - (pickerTop() + PICKER_BORDER), 0, PICKER_SPECTRUM_HEIGHT - 1);
            this.currentBackgroundColour = spectrumColour(column, row);
        } else {
            this.currentBackgroundColour = greyColour(column);
        }

        switchMode(PhotoMode.RGB);
    }

    // leaving background mode frees the background image, it's loaded again when the player comes back to it
    private void switchMode(PhotoMode mode) {
        if (this.currentMode == PhotoMode.BACKGROUND && mode != PhotoMode.BACKGROUND) {
            releaseBackground();
        }
        this.currentMode = mode;
    }

    private void drawScaledImage(int x, int y) {
        Tesselator tesselator = Tesselator.getInstance();
        BufferBuilder buffer = tesselator.getBuilder();
        buffer.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);

        buffer.vertex(x, y + PHOTO_HEIGHT, BACKGROUND_DEPTH).uv(0, 1).endVertex();
        buffer.vertex(x + PHOTO_WIDTH, y + PHOTO_HEIGHT, BACKGROUND_DEPTH).uv(1, 1).endVertex();
        buffer.vertex(x + PHOTO_WIDTH, y, BACKGROUND_DEPTH).uv(1, 0).endVertex();
        buffer.vertex(x, y, BACKGROUND_DEPTH).uv(0, 0).endVertex();

        tesselator.end();
    }

    private void renderCameraBackground(PoseStack poseStack, int x, int y, int z, float u, float v, int width, int height, int textureWidth, int textureHeight) {
        innerRenderCameraBackground(poseStack, x, x + width, y, y + height, z, width, height, u, v, textureWidth, textureHeight);
    }

    private void innerRenderCameraBackground(PoseStack poseStack, int left, int right, int top, int bottom, int z, int width, int height, float u, float v, int textureWidth, int textureHeight) {
        completeRenderCameraBackground(poseStack.last().pose(), left, right, top, bottom, z, (u + 0.0F) / (float)textureWidth, (u + (float)width) / (float)textureWidth, (v + 0.0F) / (float)textureHeight, (v + (float)height) / (float)textureHeight);
    }

    private void completeRenderCameraBackground(Matrix4f pose, int left, int right, int top, int bottom, int z, float minU, float maxU, float minV, float maxV) {
        RenderSystem.setShader(GameRenderer::getPositionTexColorShader);
        BufferBuilder bufferbuilder = Tesselator.getInstance().getBuilder();
        bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR);
        bufferbuilder.vertex(pose, (float)left, (float)bottom, (float)z).uv(minU, maxV).color(currentBackgroundColour).endVertex();
        bufferbuilder.vertex(pose, (float)right, (float)bottom, (float)z).uv(maxU, maxV).color(currentBackgroundColour).endVertex();
        bufferbuilder.vertex(pose, (float)right, (float)top, (float)z).uv(maxU, minV).color(currentBackgroundColour).endVertex();
        bufferbuilder.vertex(pose, (float)left, (float)top, (float)z).uv(minU, minV).color(currentBackgroundColour).endVertex();
        bufferbuilder.end();
        BufferUploader.end(bufferbuilder);
    }

    private void renderInfoToolTip(PoseStack poseStack, int mouseX, int mouseY) {
        if (isOver(BACKGROUND_BUTTON, mouseX, mouseY)) {
            this.renderTooltip(poseStack, new TranslatableComponent("eanimod.animalinfocontainer.photomode.background"), mouseX, mouseY);
        }
        if (isOver(RGB_BUTTON, mouseX, mouseY)) {
            this.renderTooltip(poseStack, new TranslatableComponent("eanimod.animalinfocontainer.photomode.rgb"), mouseX, mouseY);
        }
        if (isOver(TRANSPARENT_BUTTON, mouseX, mouseY)) {
            this.renderTooltip(poseStack, new TranslatableComponent("eanimod.animalinfocontainer.photomode.transparent"), mouseX, mouseY);
        }
        if (this.currentMode == PhotoMode.TRANSPARENCY && isOver(STICKER_STYLE_BOX, mouseX, mouseY)) {
            this.renderTooltip(poseStack, new TranslatableComponent("eanimod.animalinfocontainer.photomode.stickerstyle"), mouseX, mouseY);
        }

        if (isOver(TAKE_PHOTO_BUTTON, mouseX, mouseY)) {
            this.renderTooltip(poseStack, new TranslatableComponent("eanimod.animalinfocontainer.photomode.takephoto"), mouseX, mouseY);
        }

        if (isOver(RESET_BUTTON, mouseX, mouseY)) {
            this.renderTooltip(poseStack, new TranslatableComponent("eanimod.animalinfocontainer.photomode.reset"), mouseX, mouseY);
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (GeneticAnimalsConfig.COMMON.tabsOnTop.get()) {
            if (isOver(INFO_TAB, mouseX, mouseY)) {
                returnToInventory(false);
                return true;
            }

            if (this.animal.canHaveChest() && isOver(CHEST_TAB, mouseX, mouseY)) {
                returnToInventory(true);
                return true;
            }
        }

        //Colour Picker
        if (this.colourPickerOpen && isOverColourPicker(mouseX, mouseY)) {
            this.pickerDragArea = pickerAreaAt(mouseX, mouseY);
            if (this.pickerDragArea != PickerArea.NONE) {
                pickColourAt(mouseX, mouseY, this.pickerDragArea);
            }
            return true;
        }

        //Take screenshot
        if (isOver(TAKE_PHOTO_BUTTON, mouseX, mouseY)) {
            this.prepareForScreenshot = true;
            return true;
        }

        //Modes
        if (isOver(BACKGROUND_BUTTON, mouseX, mouseY)) {
            switchMode(PhotoMode.BACKGROUND);
            this.colourPickerOpen = false;
            this.selectedBackground = 0;
            initialiseBackgrounds();
            return true;
        }

        //RGB opens and closes the colour picker, rgb mode only turns on once a colour is picked
        if (isOver(RGB_BUTTON, mouseX, mouseY)) {
            this.colourPickerOpen = !this.colourPickerOpen;
            return true;
        }

        if (isOver(TRANSPARENT_BUTTON, mouseX, mouseY)) {
            switchMode(PhotoMode.TRANSPARENCY);
            this.colourPickerOpen = false;
            return true;
        }

        //Background arrows
        if (this.currentMode == PhotoMode.BACKGROUND) {
            if (isOver(PREVIOUS_BACKGROUND_ARROW, mouseX, mouseY)) {
                if (this.selectedBackground > 0) {
                    this.selectedBackground -= 1;
                    initialiseBackgrounds();
                }
                return true;
            }
            if (isOver(NEXT_BACKGROUND_ARROW, mouseX, mouseY)) {
                if (this.selectedBackground < this.maxBackgrounds) {
                    this.selectedBackground += 1;
                    initialiseBackgrounds();
                }
                return true;
            }
        }

        //Centre Reset
        if (isOver(RESET_BUTTON, mouseX, mouseY)) {
            this.animalOffsetX = 0.0D;
            this.animalOffsetY = 0.0D;
            this.animalYaw = 0.0F;
            this.animalPitch = 0.0F;
            this.scale = DEFAULT_SCALE;
            return true;
        }

        //Sticker style checkbox, only shown and usable in transparent mode
        if (this.currentMode == PhotoMode.TRANSPARENCY && this.stickerStyleBox.mouseClicked(mouseX, mouseY, button)) {
            return true;
        }

        if (button != 0 && button != 1) {
            return super.mouseClicked(mouseX, mouseY, button);
        }

        this.draggingAnimal = true;
        this.dragButton = button;
        this.dragOriginX = (float)mouseX;
        this.dragOriginY = (float)mouseY;
        this.dragStartYaw = this.animalYaw;
        this.dragStartPitch = this.animalPitch;
        this.dragStartOffsetX = this.animalOffsetX;
        this.dragStartOffsetY = this.animalOffsetY;

        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        if (button == this.dragButton) {
            this.draggingAnimal = false;
        }

        // letting go of a colour keeps it and closes the picker
        if (this.pickerDragArea != PickerArea.NONE) {
            this.pickerDragArea = PickerArea.NONE;
            this.colourPickerOpen = false;
            return true;
        }

        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        if (this.pickerDragArea != PickerArea.NONE) {
            pickColourAt(mouseX, mouseY, this.pickerDragArea);
            return true;
        } else if (this.draggingAnimal) {
            if (this.dragButton == 1) {
                this.animalOffsetX = this.dragStartOffsetX + (mouseX - this.dragOriginX);
                this.animalOffsetY = this.dragStartOffsetY + (mouseY - this.dragOriginY);
            } else {
                this.animalYaw = this.dragStartYaw - (this.dragOriginX - ((float)mouseX));
                this.animalPitch = this.dragStartPitch + (this.dragOriginY - ((float)mouseY));
            }
            return true;
        } else {
            return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
        }
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double delta) {
        // multiply rather than add so each notch feels the same at any zoom, and touchpad fractions still count
        this.scale = Mth.clamp((float)(this.scale * Math.pow(SCALE_STEP, delta)), MIN_SCALE, MAX_SCALE);
        return true;
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (super.keyPressed(keyCode, scanCode, modifiers)) {
            return true;
        }
        // the inventory key closes the animal's inventory like it did before photo mode was its own screen
        if (this.minecraft.options.keyInventory.isActiveAndMatches(InputConstants.getKey(keyCode, scanCode))) {
            this.onClose();
            return true;
        }
        return false;
    }

    private void initialiseBackgrounds() {
        File configDirectory = new File(FMLPaths.CONFIGDIR.get().toString() + File.separator + "genetic_animals" + File.separator + "photo_backgrounds");

        if (configDirectory.exists() || configDirectory.mkdirs()) {
            File[] files = configDirectory.listFiles();

            if (files != null) {
                List<Object> combinedList = new ArrayList<>(Arrays.asList(files));

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

                    if (this.previousSelectionWasFile) {
                        //This is to unload the file from memory rather than keeping it loaded and re-registering it everytime
                        Minecraft.getInstance().getTextureManager().release(this.backgroundTexture);
                    }

                    if (selectedImage instanceof File) {
                        this.previousSelectionWasFile = true;

                        // the texture keeps the image, the file itself is only open while it's read
                        try (InputStream inputStream = Files.newInputStream(((File)selectedImage).toPath())) {
                            DynamicTexture dynamicTexture = new DynamicTexture(NativeImage.read(inputStream));
                            this.backgroundTexture = Minecraft.getInstance().getTextureManager().register(((File)selectedImage).getName().toLowerCase(), dynamicTexture);
                        }

                    } else if (selectedImage instanceof ResourceLocation) {
                        this.previousSelectionWasFile = false;
                        this.backgroundTexture = (ResourceLocation) selectedImage;
                    }

                } catch (Exception e) {}
            }
        }
    }

    private void screenshotAnimal(NativeImage photo, Consumer<Component> uiMessage) {
        File screenshotDirectory = new File(this.minecraft.gameDirectory, "screenshots");
        screenshotDirectory.mkdir();
        File screenshotFile = getScreenshotFile(screenshotDirectory);

        net.minecraftforge.client.event.ScreenshotEvent event = net.minecraftforge.client.ForgeHooksClient.onScreenshot(photo, screenshotFile);
        if (event.isCanceled()) {
            uiMessage.accept(event.getCancelMessage());
            photo.close();
            return;
        }
        final File target = event.getScreenshotFile();

        Util.ioPool().execute(() -> {
            try {
                photo.writeToFile(target);
                Component component = (new TextComponent(screenshotFile.getName())).withStyle(ChatFormatting.UNDERLINE).withStyle((style) -> {
                    return style.withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_FILE, target.getAbsolutePath()));
                });
                if (event.getResultMessage() != null)
                    uiMessage.accept(event.getResultMessage());
                else
                    uiMessage.accept(new TranslatableComponent("screenshot.success", component));
            } catch (Exception exception) {
                uiMessage.accept(new TranslatableComponent("screenshot.failure", exception.getMessage()));
            } finally {
                photo.close();
            }
        });
    }

    private NativeImage takeScreenshot(RenderTarget renderTarget) {
        NativeImage screenshot = new NativeImage(renderTarget.width, renderTarget.height, false);
        RenderSystem.bindTexture(renderTarget.getColorTextureId());
        screenshot.downloadTexture(0, true);
        screenshot.flipY();
        return screenshot;
    }

    // wholeScreenshotOnWhite is only given for transparent photos, where wholeScreenshot is the pass on black.
    // returns null when a transparent photo has no animal in it
    private NativeImage selectPhotoArea(NativeImage wholeScreenshot, NativeImage wholeScreenshotOnWhite) {
        // exactly the photo frame, worked out the same way as the scissor that clips the animal to it
        double guiScale = this.minecraft.getWindow().getGuiScale();
        int areaLeft = Mth.clamp((int)(photoLeft() * guiScale), 0, wholeScreenshot.getWidth() - 1);
        int areaTop = Mth.clamp((int)(photoTop() * guiScale), 0, wholeScreenshot.getHeight() - 1);
        int areaWidth = Math.min((int)(PHOTO_WIDTH * guiScale), wholeScreenshot.getWidth() - areaLeft);
        int areaHeight = Math.min((int)(PHOTO_HEIGHT * guiScale), wholeScreenshot.getHeight() - areaTop);
        NativeImage photoArea = new NativeImage(areaWidth, areaHeight, false);

        if (this.currentMode == PhotoMode.TRANSPARENCY) {
            int firstColumn = areaWidth;
            int firstRow = areaHeight;
            int lastColumn = 0;
            int lastRow = 0;

            for (int row = 0; row < photoArea.getHeight(); ++row) {
                for (int column = 0; column < photoArea.getWidth(); ++column) {
                    int pixel = cutOutPixel(wholeScreenshot.getPixelRGBA(areaLeft + column, areaTop + row), wholeScreenshotOnWhite.getPixelRGBA(areaLeft + column, areaTop + row));
                    if (pixel == TRANSPARENT_COLOUR) {
                        photoArea.setPixelRGBA(column, row, TRANSPARENT_COLOUR);
                    } else {
                        if (column < firstColumn) {
                            firstColumn = column;
                        }
                        if (row < firstRow) {
                            firstRow = row;
                        }
                        if (column > lastColumn) {
                            lastColumn = column;
                        }
                        if (row > lastRow) {
                            lastRow = row;
                        }
                        photoArea.setPixelRGBA(column, row, pixel);
                    }
                }
            }

            int width = lastColumn - firstColumn;
            int height = lastRow - firstRow;
            if (width < 0 || height < 0) {
                photoArea.close();
                return null;
            }

            NativeImage croppedImage;
            if (this.stickerStyleBox.selected()) {
                croppedImage = new NativeImage(width + 6, height + 6, false);
                boolean[][] hasSubject = new boolean[width + 6][height + 6];
                for (int row = 0; row < height + 6; ++row) {
                    for (int column = 0; column < width + 6; ++column) {
                        if (column < 3 || column >= width + 3 || row < 3 || row >= height + 3) {
                            croppedImage.setPixelRGBA(column, row, TRANSPARENT_COLOUR);
                            hasSubject[column][row] = false;
                        } else {
                            int pixel = photoArea.getPixelRGBA(firstColumn + (column - 2), firstRow + (row - 2));
                            croppedImage.setPixelRGBA(column, row, pixel);
                            hasSubject[column][row] = pixel != TRANSPARENT_COLOUR;
                        }
                    }
                }

                for (int row = 0; row < height + 6; ++row) {
                    for (int column = 0; column < width + 6; ++column) {
                        if (!hasSubject[column][row]) {
                            makeOutline(column, width, row, height, hasSubject, croppedImage, STICKER_INNER_OUTLINE_COLOUR);
                        }
                    }
                }

            } else {
                croppedImage = new NativeImage(width, height, false);
                for (int row = 0; row < height; ++row) {
                    for (int column = 0; column < width; ++column) {
                        croppedImage.setPixelRGBA(column, row, photoArea.getPixelRGBA(firstColumn + column, firstRow + row));
                    }
                }
            }

            photoArea.close();
            return croppedImage;
        }

        for (int row = 0; row < photoArea.getHeight(); ++row) {
            for (int column = 0; column < photoArea.getWidth(); ++column) {
                int pixel = wholeScreenshot.getPixelRGBA(areaLeft + column, areaTop + row) | 255 << NativeImage.Format.RGBA.alphaOffset();
                photoArea.setPixelRGBA(column, row, pixel);
            }
        }

        return photoArea;
    }

    // on black a pixel is alpha * colour and on white it's alpha * colour + (1 - alpha), so how much it changed between
    // the two passes gives the alpha, and dividing the black pass by that alpha gives the colour without the black mixed in.
    // this keeps see-through parts like axolotl tails see-through without picking up any tint from the background
    private int cutOutPixel(int onBlack, int onWhite) {
        int change = (NativeImage.getR(onWhite) - NativeImage.getR(onBlack))
                + (NativeImage.getG(onWhite) - NativeImage.getG(onBlack))
                + (NativeImage.getB(onWhite) - NativeImage.getB(onBlack));
        int alpha = Mth.clamp(255 - Math.round(change / 3.0F), 0, 255);
        if (alpha == 0) {
            return TRANSPARENT_COLOUR;
        }

        int r = Math.min(255, Math.round(NativeImage.getR(onBlack) * 255.0F / alpha));
        int g = Math.min(255, Math.round(NativeImage.getG(onBlack) * 255.0F / alpha));
        int b = Math.min(255, Math.round(NativeImage.getB(onBlack) * 255.0F / alpha));
        return NativeImage.combine(alpha, b, g, r);
    }

    private void makeOutline(int column, int width, int row, int height, boolean[][] hasSubject, NativeImage croppedImage, int outlineColour) {
        for (int rowStep = -1; rowStep < 2; rowStep++) {
            for (int columnStep = -1; columnStep < 2; columnStep++) {
                if (!(rowStep == 0 && columnStep == 0)) {
                    if (!(column + columnStep == -1 || column + columnStep == width + 6 || row + rowStep == -1 || row + rowStep == height + 6)) {
                        if (hasSubject[column + columnStep][row + rowStep]) {
                            makeThickOutline(column, width, row, height, hasSubject, croppedImage, STICKER_OUTER_OUTLINE_COLOUR);
                            if (rowStep == 0 || columnStep == 0) {
                                croppedImage.setPixelRGBA(column, row, outlineColour);
                                return;
                            }
                        }
                    }
                }
            }
        }
    }

    private void makeThickOutline(int column, int width, int row, int height, boolean[][] hasSubject, NativeImage croppedImage, int outlineColour) {
        for (int rowStep = -2; rowStep < 3; rowStep++) {
            for (int columnStep = -2; columnStep < 3; columnStep++) {
                if (rowStep == columnStep && !(rowStep == -1 || rowStep == 1)) continue;
                if (hasSubject[column + columnStep][row + rowStep]) continue;
                if (!(column + columnStep == -1 || column + columnStep == width + 6 || row + rowStep == -1 || row + rowStep == height + 6)) {
                    if (croppedImage.getPixelRGBA(column + columnStep, row + rowStep) == TRANSPARENT_COLOUR) {
                        croppedImage.setPixelRGBA(column + columnStep, row + rowStep, outlineColour);
                    }
                }
            }
        }
    }

    private File getScreenshotFile(File screenshotDirectory) {
        String timestamp = DATE_FORMAT.format(new Date());
        int attempt = 1;

        while (true) {
            File screenshotFile = new File(screenshotDirectory, this.inventoryScreen.getAnimalName() + "_" + timestamp + (attempt == 1 ? "" : "_" + attempt) + ".png");
            if (!screenshotFile.exists()) {
                return screenshotFile;
            }

            ++attempt;
        }
    }

    private void renderTabs(PoseStack poseStack, int x, int y) {
        if (GeneticAnimalsConfig.COMMON.tabsOnTop.get()) {
            this.blit(poseStack, x + 82, y - 28, 0, 209, 131, 28, 31, INVENTORY_GUI_TEXTURE_SIZE, INVENTORY_GUI_TEXTURE_SIZE); //highlight tab
            this.blit(poseStack, x + 111, y - 28, 0, 177, 131, 28, 31, INVENTORY_GUI_TEXTURE_SIZE, INVENTORY_GUI_TEXTURE_SIZE); //shadow tab
            this.blit(poseStack, x + 140, y - 28, 0, 177, 131, 28, 31, INVENTORY_GUI_TEXTURE_SIZE, INVENTORY_GUI_TEXTURE_SIZE); //shadow tab
            this.blit(poseStack, x + 88, y - 19, 0, 217, 107, 18, 14, INVENTORY_GUI_TEXTURE_SIZE, INVENTORY_GUI_TEXTURE_SIZE); //highlight photo logo
            this.blit(poseStack, x + 117, y - 18, 0, 184, 23, 15, 14, INVENTORY_GUI_TEXTURE_SIZE, INVENTORY_GUI_TEXTURE_SIZE); //shadow chest logo
            this.blit(poseStack, x + 144, y - 17, 0, 182, 51, 18, 14, INVENTORY_GUI_TEXTURE_SIZE, INVENTORY_GUI_TEXTURE_SIZE); //shadow info logo
        } else {
            this.blit(poseStack, x + 173, y + 13, 209, 16, 31, 28, INVENTORY_GUI_TEXTURE_SIZE, INVENTORY_GUI_TEXTURE_SIZE); //highlight photo
            this.blit(poseStack, x + 173, y + 41, 177, 16, 30, 28, INVENTORY_GUI_TEXTURE_SIZE, INVENTORY_GUI_TEXTURE_SIZE); //shadow chest
            this.blit(poseStack, x + 173, y + 69, 177, 44, 30, 28, INVENTORY_GUI_TEXTURE_SIZE, INVENTORY_GUI_TEXTURE_SIZE); //shadow info
        }
    }

    private void renderEntityPhotoMode(int x, int y, float scale, float facingX, float facingY, LivingEntity entity) {
        float facingYaw = (float)Math.atan((facingX / 40.0F));
        float facingPitch = (float)Math.atan((facingY / 40.0F));
        PoseStack modelViewStack = RenderSystem.getModelViewStack();
        modelViewStack.pushPose();
        modelViewStack.translate(x + this.animalOffsetX, y + this.animalOffsetY, 1050.0D);
        modelViewStack.scale(1.0F, 1.0F, -1.0F);
        RenderSystem.applyModelViewMatrix();
        PoseStack entityPoseStack = new PoseStack();
        entityPoseStack.translate(0.0D, 0.0D, 1000.0D);
        entityPoseStack.scale(scale, scale, scale);
        Quaternion rotation = Vector3f.ZP.rotationDegrees(180.0F);
        Quaternion pitchRotation = Vector3f.XP.rotationDegrees(facingPitch * 20.0F + this.animalPitch);
        Quaternion yawRotation = Vector3f.YP.rotationDegrees(facingPitch * 20.0F + this.animalYaw);
        rotation.mul(pitchRotation);
        rotation.mul(yawRotation);
        entityPoseStack.mulPose(rotation);
        float originalYBodyRot = entity.yBodyRot;
        float originalYRot = entity.getYRot();
        float originalXRot = entity.getXRot();
        float originalYHeadRot = entity.yHeadRot;
        entity.yBodyRot = 180.0F + facingYaw * 20.0F;
        entity.setYRot(180.0F + facingYaw * 40.0F);
        entity.setXRot(-facingPitch * 20.0F);
        entity.yHeadRot = 180.0F + facingYaw * 20.0F;
        Lighting.setupForEntityInInventory();
        EntityRenderDispatcher dispatcher = Minecraft.getInstance().getEntityRenderDispatcher();
        pitchRotation.conj();
        dispatcher.overrideCameraOrientation(pitchRotation);
        dispatcher.setRenderShadow(false);
        MultiBufferSource.BufferSource bufferSource = Minecraft.getInstance().renderBuffers().bufferSource();
        RenderSystem.runAsFancy(() -> {
            dispatcher.render(entity, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F, entityPoseStack, bufferSource, LightTexture.FULL_BRIGHT);
        });
        bufferSource.endBatch();
        dispatcher.setRenderShadow(true);
        entity.yBodyRot = originalYBodyRot;
        entity.setYRot(originalYRot);
        entity.setXRot(originalXRot);
        entity.yHeadRot = originalYHeadRot;
        modelViewStack.popPose();
        RenderSystem.applyModelViewMatrix();
        Lighting.setupFor3DItems();
    }

    private void returnToInventory(boolean chestTab) {
        this.animal.isInPhotoMode = false;
        this.inventoryScreen.chestTabEnabled = chestTab;
        this.minecraft.setScreen(this.inventoryScreen);
    }

    @Override
    public void onClose() {
        this.animal.isInPhotoMode = false;
        releaseBackground();
        this.minecraft.player.closeContainer();
    }

    void resetBackgroundSelection() {
        this.selectedBackground = 0;
        if (this.currentMode == PhotoMode.BACKGROUND) {
            initialiseBackgrounds();
        }
    }

    void releaseBackground() {
        Minecraft.getInstance().getTextureManager().release(this.backgroundTexture);
        this.previousSelectionWasFile = false;
    }
}
