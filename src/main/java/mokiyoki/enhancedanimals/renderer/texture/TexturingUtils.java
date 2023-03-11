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
    private final static float COLOUR_DEGREE = 1F/255F;

    public static void createTexture(TextureLayer layer, String modLocation, ResourceManager manager, int x, int y) {
        layer.setTextureImage(loadNativeImage(layer.getTexture(), manager, modLocation, x, y));
    }

    public static NativeImage loadNativeImage(String texture, ResourceManager manager, String modLocation, int x, int y) {
        try {
            Resource iresource = manager.getResource(new ResourceLocation(modLocation+texture));
            NativeImage image = NativeImage.read(iresource.getInputStream());
            if (image.getWidth() != x || image.getHeight() != y) {
                NativeImage image1 = new NativeImage(x, y, true);
                image1.downloadTexture(0, true);
                int xb = image.getWidth();
                int yb = image.getHeight();
                if (xb >= x && yb >= y) {
                    int scale = (int)(((float) xb)/((float) x));
                    int scaleSize = scale*scale;
                    for (int i = 0; i < y; i++) {
                        for (int j = 0; j < x;j++) {
                            float r = 0.0F;
                            float g = 0.0F;
                            float b = 0.0F;
                            float a = 0.0F;
                            float f = 1F/255F;
                            for (int k = 0; k < scale; k++) {
                                for (int l = 0; l < scale; l++) {
                                    int getX = (j*scale)+l;
                                    int getY = (i*scale)+k;
                                    int color = image.getPixelRGBA(getX, getY);
                                    float val = getR(color)*f;
                                    r += val*val;
                                    val = (float)getG(color)*f;
                                    g += val*val;
                                    val = (float)getB(color)*f;
                                    b += val*val;
                                    val = (float)getA(color)*f;
                                    a += val*val;
                                }
                            }
                            image1.setPixelRGBA(j, i, combine( ((int)(255*(Math.sqrt(a/scaleSize)))), ((int)(255*(Math.sqrt(b/scaleSize)))), ((int)(255*(Math.sqrt(g/scaleSize)))), ((int)(255*(Math.sqrt(r/scaleSize))))));
                        }
                    }

                } else if (xb <= x && yb <=y) {
                    int scale = (int)(((float) x)/((float) xb));
                    for (int i = 0; i < yb; i++) {
                        for (int j = 0; j < xb; j++) {
                            image1.fillRect(j*scale, i*scale, scale, scale, image.getPixelRGBA(j, i));
                        }
                    }
                }
                image = image1;
            }
            return image;
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
        float layerA = (float)getA(colIn) * COLOUR_DEGREE;
        float layerB = (float)getB(colIn) * COLOUR_DEGREE;
        float layerG = (float)getG(colIn) * COLOUR_DEGREE;
        float layerR = (float)getR(colIn) * COLOUR_DEGREE;
        float baseA = (float)getA(i) * COLOUR_DEGREE;
        float baseB = (float)getB(i) * COLOUR_DEGREE;
        float baseG = (float)getG(i) * COLOUR_DEGREE;
        float baseR = (float)getR(i) * COLOUR_DEGREE;
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
        baseImage.setPixelRGBA(xIn, yIn, combine(j, k, l, i1));
    }

    private static void maskAlpha(int xIn, int yIn, NativeImage maskingImage, NativeImage nativeImage) {
        int i = nativeImage.getPixelRGBA(xIn, yIn);
        int maskRGBA = maskingImage.getPixelRGBA(xIn, yIn);

        float originalAlpha = ((float)(i >> 24 & 255))/255F;
        float maskingAlpha = ((float)(maskRGBA >> 24 & 255))/255F;

        int j = (int)(maskingAlpha * originalAlpha * 255F);

        maskingImage.setPixelRGBA(xIn, yIn, j << 24 | (i >> 16 & 255) << 16 | (i >> 8 & 255) << 8 | (i & 255));
    }

    protected static void cutoutAlpha(int xIn, int yIn, NativeImage maskingImage, NativeImage nativeImage) {
        int maskA = maskingImage.getPixelRGBA(xIn, yIn) >> 24 & 255;
        if (maskA != 255) {
            int colour = nativeImage.getPixelRGBA(xIn, yIn);
            maskA = 255-maskA;
            float a = ((float)maskA)/255F;
            a = a * ((colour >> 24 & 255)/255F);
            maskA = (int)(a * 255F);

            maskingImage.setPixelRGBA(xIn, yIn, maskA << 24 | (colour >> 16 & 255) << 16 | (colour >> 8 & 255) << 8 | (colour & 255));
        } else {
            maskingImage.setPixelRGBA(xIn, yIn, 0);
        }
    }

    private static void averageAlpha(NativeImage baseImage, int xIn, int yIn, List<Integer> colIns) {
        int i = baseImage.getPixelRGBA(xIn, yIn);

        float size = 1.0F / (colIns.size()+1.0F);
        float alpha = getA(i) * size * COLOUR_DEGREE;
        float red = getR(i) * alpha;
        float green = getG(i) * alpha;
        float blue = getB(i) * alpha;

        for (Integer layerColor : colIns) {
            float layerAlpha = getA(layerColor) * size * COLOUR_DEGREE;
            alpha += layerAlpha;
            red += getR(layerColor) * layerAlpha;
            green += getG(layerColor) * layerAlpha;
            blue += getB(layerColor) * layerAlpha;
        }

        baseImage.setPixelRGBA(xIn, yIn, combine((int)(cleanColourValue(alpha*255.0F)), (int)cleanColourValue(blue), (int)cleanColourValue(green), (int)cleanColourValue(red)));
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
