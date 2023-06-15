package mokiyoki.enhancedanimals.renderer.texture;

import com.mojang.blaze3d.platform.NativeImage;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.resources.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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

    public static NativeImage applyHueShift(NativeImage textureImage, int ahsb) {
//        if (ahsb!=0) {
            for(int i = 0; i < textureImage.getHeight(); ++i) {
                for (int j = 0; j < textureImage.getWidth(); ++j) {
                    blendAH(j, i, ahsb, textureImage);
                }
            }
//        }
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

    private static void blendAH(int x, int y, int ah, NativeImage nativeimage) {
        int i = nativeimage.getPixelRGBA(x, y);

        int a = ah >> 8 & 255;
        if (a!=255) {
            if (a!=0) {
                a = (int)(((float)a*COLOUR_DEGREE)*((float)(i >> 24 & 255) * COLOUR_DEGREE)*255F);
                int h = ah & 255;

                int r = i >> 16 & 255;
                int g = i >> 8 & 255;
                int b = i & 255;

                if (h!=0) {
                    float hue, saturation, brightness;
                    int cmax = Math.max(r, g);
                    if (b > cmax) cmax = b;
                    int cmin = Math.min(r, g);
                    if (b < cmin) cmin = b;

                    brightness = ((float) cmax) / 255.0f;

                    if (cmax != 0) {
                        saturation = ((float) (cmax - cmin)) / ((float) cmax);
                    } else {
                        saturation = 0;
                    }
                    if (saturation == 0) {
                        hue = 0;
                    } else {
                        float redc = ((float) (cmax - r)) / ((float) (cmax - cmin));
                        float greenc = ((float) (cmax - g)) / ((float) (cmax - cmin));
                        float bluec = ((float) (cmax - b)) / ((float) (cmax - cmin));
                        if (r == cmax) {
                            hue = bluec - greenc;
                        } else if (g == cmax) {
                            hue = 2.0f + redc - bluec;
                        } else {
                            hue = 4.0f + greenc - redc;
                            hue = hue / 6.0f;
                            if (hue < 0) {
                                hue = hue + 1.0f;
                            }
                        }
                    }
                    hue += ((float) h) * COLOUR_DEGREE;
                    hue = hue > 1.0F ? hue-1.0F : hue;

                    if (saturation == 0) {
                        r = g = b = (int) (brightness * 255.0f + 0.5f);
                    } else {
                        float hueConversionVal = (hue - (float)Math.floor(hue)) * 6.0f;
                        float f = hueConversionVal - (float)java.lang.Math.floor(hueConversionVal);
                        float p = brightness * (1.0f - saturation);
                        float q = brightness * (1.0f - saturation * f);
                        float t = brightness * (1.0f - (saturation * (1.0f - f)));
                        switch ((int) hueConversionVal) {
                            case 0:
                                r = (int) (brightness * 255.0f + 0.5f);
                                g = (int) (t * 255.0f + 0.5f);
                                b = (int) (p * 255.0f + 0.5f);
                                break;
                            case 1:
                                r = (int) (q * 255.0f + 0.5f);
                                g = (int) (brightness * 255.0f + 0.5f);
                                b = (int) (p * 255.0f + 0.5f);
                                break;
                            case 2:
                                r = (int) (p * 255.0f + 0.5f);
                                g = (int) (brightness * 255.0f + 0.5f);
                                b = (int) (t * 255.0f + 0.5f);
                                break;
                            case 3:
                                r = (int) (p * 255.0f + 0.5f);
                                g = (int) (q * 255.0f + 0.5f);
                                b = (int) (brightness * 255.0f + 0.5f);
                                break;
                            case 4:
                                r = (int) (t * 255.0f + 0.5f);
                                g = (int) (p * 255.0f + 0.5f);
                                b = (int) (brightness * 255.0f + 0.5f);
                                break;
                            case 5:
                                r = (int) (brightness * 255.0f + 0.5f);
                                g = (int) (p * 255.0f + 0.5f);
                                b = (int) (q * 255.0f + 0.5f);
                                break;
                        }
                    }
                }

                nativeimage.setPixelRGBA(x, y, a << 24 | r << 16 | g << 8 | b);
            }
        } else {
            nativeimage.setPixelRGBA(x, y, 0);
        }
    }

    protected static int[][] getFeatherColour(NativeImage baseImage) {
        int[][] featherColour = new int[128][25];
        for (int y = 0; y < 25; y++) {
            for (int x = 0; x < 128; x++) {
                featherColour[x][y] = baseImage.getPixelRGBA(92+x, 170+y);
                baseImage.setPixelRGBA(92+x, 170+y, 0);
            }
        }
        return featherColour;
    }

    protected static void processFeathers(NativeImage baseImage, NativeImage feathers, int[][] map, int[][] featherColour) {
        int h = feathers.getHeight();
        int w = feathers.getWidth();
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                int colour = feathers.getPixelRGBA(x, y);
                if (colour!=0) {
                    int blue = colour >> 16 & 255;
                    int green = colour >> 8 & 255;
                    int red = colour & 255;
                    if (red > 0) {
                        if (red == 255) {
                            colour = featherColour[green/2][0];
                        } else {
                            float dif = colour/255F;
                            colour = Math.round((dif*featherColour[green/2][0]) + ((1.0F-dif)*featherColour[green/2][1]));
                        }
                    } else {
                        int map_x = 0;
                        int map_y = 0;
                        int testColour;
                        while (map_x < 128) {
                            while (map_y < 25) {
                                testColour = map[map_x][map_y];
                                if (map_y==0 && (testColour & 255) > 0) {
                                    map_y++;
                                    continue;
                                }

                                int test = testColour >> 8 & 255;
                                int blueValue = testColour >> 16 & 255;

                                if (test==green && blueValue==blue) {
                                    colour = featherColour[map_x][map_y];
                                    map_x=128;
                                    map_y=25;
                                } else {
                                    test = Math.abs(test-green) + Math.abs(blueValue-blue);

                                    int testRight = map_x==127?0:map[map_x+1][map_y];
                                    int testDownRight = map_x==127||map_y==24?0:map[map_x+1][map_y+1];
                                    int testDown = map_y==24?0:map[map_x][map_y+1];

                                    testRight = testRight==0?511:Math.abs((testRight >> 8 & 255)-green) + Math.abs((testRight >> 16 & 255)-blue);
                                    testDownRight = testDownRight==0?511:Math.abs((testDownRight >> 8 & 255)-green) + Math.abs((testDownRight >> 16 & 255)-blue);
                                    testDown = testDown==0?511:Math.abs((testDown >> 8 & 255)-green) + Math.abs((testDown >> 16 & 255)-blue);

                                    if (test < testRight) {
                                        if (test < testDownRight) {
                                            if (test < testDown) {
                                                colour = featherColour[map_x][map_y];
                                                map_x=128;
                                                map_y=25;
                                            } else {
                                                map_y++;
                                            }
                                        } else if (testDownRight < testDown) {
                                            map_x++;
                                            map_y++;
                                        } else {
                                            map_y++;
                                        }
                                    } else if (testRight < testDownRight) {
                                        if (testRight < testDown) {
                                            map_x++;
                                        } else {
                                            map_y++;
                                        }
                                    } else if (testDownRight < testDown) {
                                        map_x++;
                                        map_y++;
                                    } else {
                                        map_y++;
                                    }
                                }
                            }
                        }
                    }
                    if (colour!=0) {
                        baseImage.setPixelRGBA(x, y, colour);
                    }
                }
            }
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
