package mokiyoki.enhancedanimals.renderer.texture;

import com.mojang.blaze3d.platform.NativeImage;

public class TextureLayer {

    private TexturingType texturingType = TexturingType.NONE;

    private int RGB = -1;

    private String texture;

    private NativeImage textureImage;

    public TextureLayer(String texture) {
        this.texture = texture;
    }
    public TextureLayer(TexturingType texturingType, String texture) {
        this.texturingType = texturingType;
        this.texture = texture;
    }

    public TextureLayer(TexturingType texturingType, int rgb, String texture) {
        this.texturingType = texturingType;
        this.RGB = rgb;
        this.texture = texture;
    }

    public TexturingType getTexturingType() {
        return texturingType;
    }

    public int getRGB() {
        return RGB;
    }

    public void setRGB(Integer RGB) {
        this.RGB = RGB;
    }

    public String getTexture() {
        return texture;
    }

    public void setTextureImage(NativeImage nativeImage) {
        this.textureImage = nativeImage;
    }

    public NativeImage getTextureImage() {
        return textureImage;
    }
}
