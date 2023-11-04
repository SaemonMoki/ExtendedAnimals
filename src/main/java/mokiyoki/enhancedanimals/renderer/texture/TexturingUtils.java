package mokiyoki.enhancedanimals.renderer.texture;

import com.mojang.blaze3d.platform.NativeImage;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TexturingUtils {

    final static Logger LOGGER = LogManager.getLogger();
    private final static float COLOUR_DEGREE = 1F/255F;

    public static void createTexture(TextureLayer layer, String modLocation, ResourceManager manager) {
        layer.setTextureImage(loadNativeImage(layer.getTexture(), manager, modLocation));
    }

    public static NativeImage loadNativeImage(String texture, ResourceManager manager, String modLocation) {
        try {
            Resource iresource = manager.getResource(new ResourceLocation(modLocation+texture)).get();
            return NativeImage.read(iresource.open());
        } catch (Exception e) {
            throw new IllegalStateException("Couldn't load layered image", e);
        }
    }

    public static NativeImage applyRGBBlend(NativeImage textureImage, int rgb) {
        for(int i = 0; i < textureImage.getHeight(); ++i) {
            for (int j = 0; j < textureImage.getWidth(); ++j) {
                blendRGB(j, i, rgb, textureImage);
            }
        }
        return textureImage;
    }

    public static NativeImage applyBGRBlend(NativeImage textureImage, int rgb) {
        for(int i = 0; i < textureImage.getHeight(); ++i) {
            for (int j = 0; j < textureImage.getWidth(); ++j) {
                blendBGR(j, i, rgb, textureImage);
            }
        }
        return textureImage;
    }

    public static void applyPixelLayer(NativeImage baseImage, NativeImage appliedImage) {
        for(int i = 0; i < appliedImage.getHeight(); ++i) {
            for(int j = 0; j < appliedImage.getWidth(); ++j) {
                layerPixel(baseImage, j, i, appliedImage.getPixelRGBA(j, i));
            }
        }
    }

    public static void applyAlphaMaskBlend(NativeImage maskingImage, NativeImage appliedImage) {
        for (int i = 0; i < appliedImage.getHeight(); ++i) {
            for (int j = 0; j < appliedImage.getWidth(); ++j) {
                maskAlpha(j, i, maskingImage, appliedImage);
            }
        }
    }

    public static void applyAverageBlend(NativeImage baseImage, List<NativeImage> appliedImages) {
        for(int i = 0; i < baseImage.getHeight(); ++i) {
            for(int j = 0; j < baseImage.getWidth(); ++j) {
                averageAlpha(baseImage, j, i, retrieveColColours(appliedImages, j, i));
            }
        }
    }

    private static List<Integer> retrieveColColours(List<NativeImage> appliedImages, int j, int i) {
        List<Integer> colColours = new ArrayList<>();

        for (NativeImage appliedImage: appliedImages) {
            colColours.add(appliedImage.getPixelRGBA(j, i));
        }

        return colColours;
    }

    //Layers the base image's and supplied image's pixels together
    private static void layerPixel(NativeImage baseImage, int xIn, int yIn, int colIn) {
        int i = baseImage.getPixelRGBA(xIn, yIn);
        float layerA = (float)FastColor.ABGR32.alpha(colIn) * COLOUR_DEGREE;
        float layerB = (float)FastColor.ABGR32.blue(colIn) * COLOUR_DEGREE;
        float layerG = (float)FastColor.ABGR32.green(colIn) * COLOUR_DEGREE;
        float layerR = (float)FastColor.ABGR32.red(colIn) * COLOUR_DEGREE;
        float baseA = (float)FastColor.ABGR32.alpha(i) * COLOUR_DEGREE;
        float baseB = (float)FastColor.ABGR32.blue(i) * COLOUR_DEGREE;
        float baseG = (float)FastColor.ABGR32.green(i) * COLOUR_DEGREE;
        float baseR = (float)FastColor.ABGR32.red(i) * COLOUR_DEGREE;
        float inverseBaseA = 1.0F - baseA;
        float inverseLayerA = 1.0F - layerA;
        float outAlpha = cleanValue(baseA+(inverseBaseA * layerA));
        float outBlue = cleanValue((layerB * layerA) + (inverseLayerA * baseB));
        float outGreen = cleanValue((layerG * layerA) + (inverseLayerA * baseG));
        float outRed = cleanValue((layerR * layerA) + (inverseLayerA * baseR));

        int j = (int)(outAlpha * 255.0F);
        int k = (int)(outBlue * 255.0F);
        int l = (int)(outGreen * 255.0F);
        int i1 = (int)(outRed * 255.0F);
        baseImage.setPixelRGBA(xIn, yIn, FastColor.ABGR32.color(j, k, l, i1));
    }

    private static void maskAlpha(int xIn, int yIn, NativeImage maskingImage, NativeImage nativeImage) {
        int i = nativeImage.getPixelRGBA(xIn, yIn);
        int maskRGBA = maskingImage.getPixelRGBA(xIn, yIn);

        float originalAlpha = ((float)(i >> 24 & 255))/255F;
        float maskingAlpha = ((float)(maskRGBA >> 24 & 255))/255F;

        int j = (int)(maskingAlpha * originalAlpha * 255F);

        maskingImage.setPixelRGBA(xIn, yIn, j << 24 | (i >> 16 & 255) << 16 | (i >> 8 & 255) << 8 | (i & 255));
    }

    private static void averageAlpha(NativeImage baseImage, int xIn, int yIn, List<Integer> colIns) {
        int i = baseImage.getPixelRGBA(xIn, yIn);

        float size = 1.0F / (colIns.size()+1.0F);
        float alpha = FastColor.ABGR32.alpha(i) * size * COLOUR_DEGREE;
        float red = FastColor.ABGR32.red(i) * alpha;
        float green = FastColor.ABGR32.green(i) * alpha;
        float blue = FastColor.ABGR32.blue(i) * alpha;

        for (Integer layerColor : colIns) {
            float layerAlpha = FastColor.ABGR32.alpha(layerColor) * size * COLOUR_DEGREE;
            alpha += layerAlpha;
            red += FastColor.ABGR32.red(layerColor) * layerAlpha;
            green += FastColor.ABGR32.green(layerColor) * layerAlpha;
            blue += FastColor.ABGR32.blue(layerColor) * layerAlpha;
        }

        baseImage.setPixelRGBA(xIn, yIn, FastColor.ABGR32.color((int)(cleanColourValue(alpha*255.0F)), (int)cleanColourValue(blue), (int)cleanColourValue(green), (int)cleanColourValue(red)));
    }

    //Blends the supplied image with a specified bgr
    private static void blendBGR(int xIn, int yIn, int rgbDye, NativeImage nativeimage) {
        int i = nativeimage.getPixelRGBA(xIn, yIn);

        float r = (float)(rgbDye >> 16 & 255) * COLOUR_DEGREE;
        float g = (float)(rgbDye >> 8 & 255) * COLOUR_DEGREE;
        float b = (float)(rgbDye >> 0 & 255) * COLOUR_DEGREE;
        float originalAlpha = (float)(i >> 24 & 255) * COLOUR_DEGREE;
        float originalBlue = (float)(i >> 16 & 255) * COLOUR_DEGREE;
        float originalGreen = (float)(i >> 8 & 255) * COLOUR_DEGREE;
        float originalRed = (float)(i >> 0 & 255) * COLOUR_DEGREE;

        if(originalAlpha != 0.0) {
            float f10 = b * 255 * originalBlue;
            float f11 = g * 255 * originalGreen;
            float f12 = r * 255 * originalRed;

            int j = (int)(originalAlpha * 255.0F);
            int k = (int)(f10);
            int l = (int)(f11);
            int i1 = (int)(f12);

            nativeimage.setPixelRGBA(xIn, yIn, j << 24 | k << 16 | l << 8 | i1 << 0);
        }
    }

    //Blends the supplied image with a specified rbg
    private static void blendRGB(int xIn, int yIn, int rgbDye, NativeImage nativeimage) {
        int i = nativeimage.getPixelRGBA(xIn, yIn);

        float f1 = (float)(rgbDye >> 16 & 255) * COLOUR_DEGREE;
        float f2 = (float)(rgbDye >> 8 & 255) * COLOUR_DEGREE;
        float f3 = (float)(rgbDye >> 0 & 255) * COLOUR_DEGREE;
        float originalAlpha = (float)(i >> 24 & 255) * COLOUR_DEGREE;
        float r = (float)(i >> 16 & 255) * COLOUR_DEGREE;
        float f6 = (float)(i >> 8 & 255) * COLOUR_DEGREE;
        float f7 = (float)(i >> 0 & 255) * COLOUR_DEGREE;

        if(originalAlpha != 0.0) {
            float f10 = f1 * 255 * r;
            float f11 = f2 * 255 * f6;
            float f12 = f3 * 255 * f7;

            int j = (int)(originalAlpha * 255.0F);
            int k = (int)(f10);
            int l = (int)(f11);
            int i1 = (int)(f12);

            nativeimage.setPixelRGBA(xIn, yIn, j << 24 | k << 16 | l << 8 | i1 << 0);
        }
    }

    private static float cleanValue(float value) {
        if (value > 1.0F) {
            return 1.0F;
        }
        return value;
    }

    private static float cleanColourValue(float value) {
        if (value > 255.0F) {
            return 255.0F;
        }
        return Math.max(value, 0.0F);
    }

}
