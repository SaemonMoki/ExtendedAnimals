package mokiyoki.enhancedanimals.renderer.texture;

import com.mojang.blaze3d.platform.NativeImage;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.resources.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.mojang.blaze3d.platform.NativeImage.combine;
import static com.mojang.blaze3d.platform.NativeImage.getA;
import static com.mojang.blaze3d.platform.NativeImage.getB;
import static com.mojang.blaze3d.platform.NativeImage.getG;
import static com.mojang.blaze3d.platform.NativeImage.getR;

public class TexturingUtils {

    final static Logger LOGGER = LogManager.getLogger();

    public static void createTexture(TextureLayer layer, String modLocation, ResourceManager manager) {
        layer.setTextureImage(loadNativeImage(layer.getTexture(), manager, modLocation));
    }

    public static NativeImage loadNativeImage(String texture, ResourceManager manager, String modLocation) {
        try {
            Resource iresource = manager.getResource(new ResourceLocation(modLocation+texture));
            return NativeImage.read(iresource.getInputStream());
        } catch (IOException e) {
            LOGGER.error("Couldn't load layered image", (Throwable)e);
        }

        return null;
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

    public static void applyPixelBlend(NativeImage baseImage, NativeImage appliedImage) {
        for(int i = 0; i < appliedImage.getHeight(); ++i) {
            for(int j = 0; j < appliedImage.getWidth(); ++j) {
                blendPixel(baseImage, j, i, appliedImage.getPixelRGBA(j, i));
            }
        }
    }

    public static void applyAlphaMaskBlend(NativeImage maskingImage, NativeImage appliedImage) {
        for (int i = 0; i < appliedImage.getHeight(); ++i) {
            for (int j = 0; j < appliedImage.getWidth(); ++j) {
                blendAlpha(j, i, maskingImage, appliedImage);
            }
        }
    }

    public static void applyAverageBlend(NativeImage baseImage, List<NativeImage> appliedImages) {
        for(int i = 0; i < baseImage.getHeight(); ++i) {
            for(int j = 0; j < baseImage.getWidth(); ++j) {
                averagePixel(baseImage, j, i, retrieveColColours(appliedImages, j, i));
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

    //Blends the base image's and supplied image's pixels together
    private static void blendPixel(NativeImage baseImage, int xIn, int yIn, int colIn) {
        int i = baseImage.getPixelRGBA(xIn, yIn);
        float layerA = (float)getA(colIn) / 255.0F;
        float f1 = (float)getB(colIn) / 255.0F;
        float f2 = (float)getG(colIn) / 255.0F;
        float f3 = (float)getR(colIn) / 255.0F;
        float baseA = (float)getA(i) / 255.0F;
        float baseB = (float)getB(i) / 255.0F;
        float baseG = (float)getG(i) / 255.0F;
        float baseR = (float)getR(i) / 255.0F;
        float inverseLayerA = 1.0F - layerA;
        float outAlpha = cleanValue(layerA * layerA + baseA * inverseLayerA);
        float outBlue = cleanValue(f1 * layerA + baseB * inverseLayerA);
        float outGreen = cleanValue(f2 * layerA + baseG * inverseLayerA);
        float outRed = cleanValue(f3 * layerA + baseR * inverseLayerA);

        int j = (int)(outAlpha * 255.0F);
        int k = (int)(outBlue * 255.0F);
        int l = (int)(outGreen * 255.0F);
        int i1 = (int)(outRed * 255.0F);
        baseImage.setPixelRGBA(xIn, yIn, combine(j, k, l, i1));
    }

    private static void blendAlpha(int xIn, int yIn, NativeImage maskingImage, NativeImage nativeImage) {
        int i = nativeImage.getPixelRGBA(xIn, yIn);
        int iAlpha = maskingImage.getPixelRGBA(xIn, yIn);

        float originalAlpha = ((float)(i >> 24 & 255))/255F;
        float maskingAlpha = ((float)(iAlpha >> 24 & 255))/255F;

        int j = (int)(originalAlpha * maskingAlpha * 255F);

        maskingImage.setPixelRGBA(xIn, yIn, j << 24 | (i >> 16 & 255) << 16 | (i >> 8 & 255) << 8 | (i >> 0 & 255));
    }

    private static void averagePixel(NativeImage baseImage, int xIn, int yIn, List<Integer> colIns) {
        int i = baseImage.getPixelRGBA(xIn, yIn);

        float f = (float)getA(i);
        float f1 = (float)getB(i);
        float f2 = (float)getG(i);
        float f3 = (float)getR(i);

        for (Integer colIn: colIns) {
            f = f + (float)getA(colIn);
            f1 = f1 + (float)getB(colIn);
            f2 = f2 + (float)getG(colIn);
            f3 = f3 + (float)getR(colIn);
        }

        f = cleanColourValue(f / (colIns.size()+1));
        f1 = cleanColourValue(f1 / (colIns.size()+1));
        f2 = cleanColourValue(f2 / (colIns.size()+1));
        f3 = cleanColourValue(f3 / (colIns.size()+1));

        baseImage.setPixelRGBA(xIn, yIn, combine((int)(f), (int)(f1), (int)(f2), (int)(f3)));
    }

    //Blends the supplied image with a specified bgr
    private static void blendBGR(int xIn, int yIn, int rgbDye, NativeImage nativeimage) {
        int i = nativeimage.getPixelRGBA(xIn, yIn);

        float r = (float)(rgbDye >> 16 & 255) / 255.0F;
        float g = (float)(rgbDye >> 8 & 255) / 255.0F;
        float b = (float)(rgbDye >> 0 & 255) / 255.0F;
        float originalAlpha = (float)(i >> 24 & 255) / 255.0F;
        float originalBlue = (float)(i >> 16 & 255) / 255.0F;
        float originalGreen = (float)(i >> 8 & 255) / 255.0F;
        float originalRed = (float)(i >> 0 & 255) / 255.0F;

        if(originalAlpha != 0.0) {
            float f10 = (b * 255 ) * originalBlue;
            float f11 = (g * 255 ) * originalGreen;
            float f12 = (r * 255 ) * originalRed;

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

        float f1 = (float)(rgbDye >> 16 & 255) / 255.0F;
        float f2 = (float)(rgbDye >> 8 & 255) / 255.0F;
        float f3 = (float)(rgbDye >> 0 & 255) / 255.0F;
        float originalAlpha = (float)(i >> 24 & 255) / 255.0F;
        float r = (float)(i >> 16 & 255) / 255.0F;
        float f6 = (float)(i >> 8 & 255) / 255.0F;
        float f7 = (float)(i >> 0 & 255) / 255.0F;

        if(originalAlpha != 0.0) {
            float f10 = (f1 * 255 ) * r;
            float f11 = (f2 * 255 ) * f6;
            float f12 = (f3 * 255 ) * f7;

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
        return value;
    }

}
