package mokiyoki.enhancedanimals.renderer.texture;

import com.google.common.collect.Lists;
import mokiyoki.enhancedanimals.entity.util.Colouration;
import com.mojang.blaze3d.platform.NativeImage;
import net.minecraft.client.renderer.texture.AbstractTexture;
import com.mojang.blaze3d.platform.TextureUtil;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@OnlyIn(Dist.CLIENT)
public class EnhancedLayeredTexture extends AbstractTexture {

    private static final Logger LOGGER = LogManager.getLogger();
    protected final List<String> layeredTextureNames;
    protected final List<String> maskingTextureNames = new ArrayList<>();
    protected  int dyeRGB = 0;
    protected  int blackRGB = 0;
    protected  int redRGB = 0;
    protected  int eyeLRGB = 0;
    protected  int eyeRRGB = 0;
    protected  int dyeSaddleRGB = 0;
    protected  int dyeArmourRGB = 0;
    protected  int dyeBridleRGB = 0;
    protected  int dyeHarnessRGB = 0;
    protected  int dyeCollarRGB = 0;
    //TODO add banner part colouring? will probably need an array
    protected String modLocation = "";
    private boolean hasImage = false;
    private NativeImage image;

    public EnhancedLayeredTexture(String modLocation, String[] textureNames, String[] maskingTextureNames, Colouration colouration) {
        this.layeredTextureNames = Lists.newArrayList(textureNames);
        if (maskingTextureNames != null) {
            this.maskingTextureNames.addAll(Lists.newArrayList(maskingTextureNames));
        }

        if (this.layeredTextureNames.isEmpty()) {
            throw new IllegalStateException("Layered texture with no layers.");
        }
        this.modLocation = modLocation;

        if (colouration.getDyeColour()!=-1) {
            this.dyeRGB = colouration.getDyeColour();
        }

        if (colouration.getMelaninColour()!=-1) {
            this.blackRGB = colouration.getMelaninColour();
        }

        if (colouration.getPheomelaninColour()!=-1) {
            this.redRGB = colouration.getPheomelaninColour();
        }

        if (colouration.getLeftEyeColour()!=-1) {
            this.eyeLRGB = colouration.getLeftEyeColour();
        }

        if (colouration.getRightEyeColour()!=-1) {
            this.eyeRRGB = colouration.getRightEyeColour();
        }

        if (colouration.getSaddleColour()!=-1) {
            this.dyeSaddleRGB = colouration.getSaddleColour();
        }

        if (colouration.getArmourColour()!=-1) {
            this.dyeArmourRGB = colouration.getArmourColour();
        }

        if (colouration.getBridleColour()!=-1) {
            this.dyeBridleRGB = colouration.getBridleColour();
        }

        if (colouration.getHarnessColour()!=-1) {
            this.dyeHarnessRGB = colouration.getHarnessColour();
        }

        if (colouration.getCollarColour()!=-1) {
            this.dyeCollarRGB = colouration.getCollarColour();
        }
    }

    @Override
    public void load(ResourceManager manager) throws IOException {
        NativeImage maskingImage = null;
        if (!this.maskingTextureNames.isEmpty()) {
            try {
                maskingImage = generateMaskingImage(manager);
            } catch (IOException ioexception) {
                LOGGER.error("Couldn't load masking image", (Throwable)ioexception);
            }
        }

        Iterator<String> iterator = this.layeredTextureNames.iterator();
        String s = iterator.next();

        try {
            Resource iresource = manager.getResourceOrThrow(new ResourceLocation(modLocation+s));
            NativeImage nativeimage = NativeImage.read(iresource.open());

            if (s.startsWith("c_") && dyeRGB!=0) {
                for(int i = 0; i < nativeimage.getHeight(); ++i) {
                    for (int j = 0; j < nativeimage.getWidth(); ++j) {
                        blendDye(j, i, dyeRGB, nativeimage);
                    }
                }
            } else if(s.startsWith("r_") && redRGB !=0) {
                for(int i = 0; i < nativeimage.getHeight(); ++i) {
                    for (int j = 0; j < nativeimage.getWidth(); ++j) {
                        blendDye(j, i, redRGB, nativeimage);
                    }
                }
            } else if(s.startsWith("b_") && blackRGB !=0) {
                for(int i = 0; i < nativeimage.getHeight(); ++i) {
                    for (int j = 0; j < nativeimage.getWidth(); ++j) {
                        shadeMelanin(j, i, blackRGB, nativeimage);
                    }
                }
            } else if(s.startsWith("eyel_") && eyeLRGB !=0) {
                for(int i = 0; i < nativeimage.getHeight(); ++i) {
                    for (int j = 0; j < nativeimage.getWidth(); ++j) {
                        blendDye(j, i, eyeLRGB, nativeimage);
                    }
                }
            } else if(s.startsWith("eyer_") && eyeRRGB !=0) {
                for(int i = 0; i < nativeimage.getHeight(); ++i) {
                    for (int j = 0; j < nativeimage.getWidth(); ++j) {
                        blendDye(j, i, eyeRRGB, nativeimage);
                    }
                }
            } else if(s.startsWith("d_s") && dyeSaddleRGB !=0) {
                for(int i = 0; i < nativeimage.getHeight(); ++i) {
                    for (int j = 0; j < nativeimage.getWidth(); ++j) {
                        blendDye(j, i, dyeSaddleRGB, nativeimage);
                    }
                }
            } else if(s.startsWith("d_a") && dyeArmourRGB !=0) {
                for(int i = 0; i < nativeimage.getHeight(); ++i) {
                    for (int j = 0; j < nativeimage.getWidth(); ++j) {
                        blendDye(j, i, dyeSaddleRGB, nativeimage);
                    }
                }
            } else if(s.startsWith("d_b") && dyeBridleRGB !=0) {
                for(int i = 0; i < nativeimage.getHeight(); ++i) {
                    for (int j = 0; j < nativeimage.getWidth(); ++j) {
                        blendDye(j, i, dyeSaddleRGB, nativeimage);
                    }
                }
            } else if(s.startsWith("d_h") && dyeHarnessRGB !=0) {
                for(int i = 0; i < nativeimage.getHeight(); ++i) {
                    for (int j = 0; j < nativeimage.getWidth(); ++j) {
                        blendDye(j, i, dyeSaddleRGB, nativeimage);
                    }
                }
            } else if(s.startsWith("d_c") && dyeCollarRGB !=0) {
                for(int i = 0; i < nativeimage.getHeight(); ++i) {
                    for (int j = 0; j < nativeimage.getWidth(); ++j) {
                        blendDye(j, i, dyeCollarRGB, nativeimage);
                    }
                }
            }
            while(true) {
                if (!iterator.hasNext()) {
                    TextureUtil.prepareImage(this.getId(), nativeimage.getWidth(), nativeimage.getHeight());
                    nativeimage.upload(0, 0, 0, false);
                    break;
                }

                String s1 = iterator.next();
                if (s1 != null) {
                    if (s1.equals("alpha_group_start") || s1.equals("alpha_group_end")) {
                        if (maskingImage != null) {
                            combineAlphaGroup(maskingImage, nativeimage, iterator, manager);
                        }
                    } else {
                        try {
                            Resource iresource1 = manager.getResourceOrThrow(new ResourceLocation(modLocation+s1));
                            NativeImage nativeimage1 = NativeImage.read(iresource1.open());

                            if(s1.startsWith("c_") && dyeRGB!=0) {
                                for(int i = 0; i < nativeimage1.getHeight(); ++i) {
                                    for (int j = 0; j < nativeimage1.getWidth(); ++j) {
                                        blendDye(j, i, dyeRGB, nativeimage1);
                                    }
                                }
                            } else if(s1.startsWith("r_") && redRGB !=0) {
                                for(int i = 0; i < nativeimage1.getHeight(); ++i) {
                                    for (int j = 0; j < nativeimage1.getWidth(); ++j) {
                                        blendDye(j, i, redRGB, nativeimage1);
                                    }
                                }
                            } else if(s1.startsWith("b_") && blackRGB !=0) {
                                for(int i = 0; i < nativeimage1.getHeight(); ++i) {
                                    for (int j = 0; j < nativeimage1.getWidth(); ++j) {
                                        shadeMelanin(j, i, blackRGB, nativeimage1);
                                    }
                                }
                            } else if(s1.startsWith("eyel_") && eyeLRGB !=0) {
                                for(int i = 0; i < nativeimage1.getHeight(); ++i) {
                                    for (int j = 0; j < nativeimage1.getWidth(); ++j) {
                                        blendDye(j, i, eyeLRGB, nativeimage1);
                                    }
                                }
                            } else if(s1.startsWith("eyer_") && eyeRRGB !=0) {
                                for(int i = 0; i < nativeimage1.getHeight(); ++i) {
                                    for (int j = 0; j < nativeimage1.getWidth(); ++j) {
                                        blendDye(j, i, eyeRRGB, nativeimage1);
                                    }
                                }
                            } else if(s1.startsWith("d_s") && dyeSaddleRGB !=0) {
                                for(int i = 0; i < nativeimage1.getHeight(); ++i) {
                                    for (int j = 0; j < nativeimage1.getWidth(); ++j) {
                                        blendDye(j, i, dyeSaddleRGB, nativeimage1);
                                    }
                                }
                            } else if(s1.startsWith("d_a") && dyeArmourRGB !=0) {
                                for(int i = 0; i < nativeimage1.getHeight(); ++i) {
                                    for (int j = 0; j < nativeimage1.getWidth(); ++j) {
                                        blendDye(j, i, dyeArmourRGB, nativeimage1);
                                    }
                                }
                            } else if(s1.startsWith("d_b") && dyeBridleRGB !=0) {
                                for(int i = 0; i < nativeimage1.getHeight(); ++i) {
                                    for (int j = 0; j < nativeimage1.getWidth(); ++j) {
                                        blendDye(j, i, dyeBridleRGB, nativeimage1);
                                    }
                                }
                            } else if(s1.startsWith("d_h") && dyeHarnessRGB !=0) {
                                for(int i = 0; i < nativeimage1.getHeight(); ++i) {
                                    for (int j = 0; j < nativeimage1.getWidth(); ++j) {
                                        blendDye(j, i, dyeHarnessRGB, nativeimage1);
                                    }
                                }
                            } else if(s1.startsWith("d_c") && dyeCollarRGB !=0) {
                                for(int i = 0; i < nativeimage1.getHeight(); ++i) {
                                    for (int j = 0; j < nativeimage1.getWidth(); ++j) {
                                        blendDye(j, i, dyeCollarRGB, nativeimage1);
                                    }
                                }
                            }

                            for(int i = 0; i < nativeimage1.getHeight(); ++i) {
                                for(int j = 0; j < nativeimage1.getWidth(); ++j) {
                                    blendPixel(nativeimage, j, i, nativeimage1.getPixelRGBA(j, i));
                                }
                            }
                        } catch (IOException ioexception) {
                            LOGGER.error("Couldn't load layered image", (Throwable)ioexception);
                        }
                    }
                }
            }
            this.image = new NativeImage(nativeimage.format(), nativeimage.getWidth(), nativeimage.getHeight(), true);
            this.image.copyFrom(nativeimage);
            this.hasImage = true;
        } catch (IOException ioexception) {
            LOGGER.error("Couldn't load layered image", (Throwable)ioexception);
        }

    }

    private void combineAlphaGroup(NativeImage maskingImage, NativeImage baseImage, Iterator<String> iterator, ResourceManager manager) {
        if (iterator.hasNext()) {
            String s = iterator.next();
            try {
                Resource iresource = manager.getResourceOrThrow(new ResourceLocation(modLocation + s));
                NativeImage firstGroupImage = NativeImage.read(iresource.open());

                if (s.startsWith("c_") && dyeRGB != 0) {
                    for (int i = 0; i < firstGroupImage.getHeight(); ++i) {
                        for (int j = 0; j < firstGroupImage.getWidth(); ++j) {
                            blendDye(j, i, dyeRGB, firstGroupImage);
                        }
                    }
                } else if (s.startsWith("r_") && redRGB != 0) {
                    for (int i = 0; i < firstGroupImage.getHeight(); ++i) {
                        for (int j = 0; j < firstGroupImage.getWidth(); ++j) {
                            blendDye(j, i, redRGB, firstGroupImage);
                        }
                    }
                } else if (s.startsWith("b_") && blackRGB != 0) {
                    if (dyeRGB != 0) {
                        for (int i = 0; i < firstGroupImage.getHeight(); ++i) {
                            for (int j = 0; j < firstGroupImage.getWidth(); ++j) {
                                shadeMelanin(j, i, blackRGB, firstGroupImage);
                                blendDye(j, i, dyeRGB, firstGroupImage);
                            }
                        }
                    } else {
                        for (int i = 0; i < firstGroupImage.getHeight(); ++i) {
                            for (int j = 0; j < firstGroupImage.getWidth(); ++j) {
                                shadeMelanin(j, i, blackRGB, firstGroupImage);
                            }
                        }
                    }
                }
//            else if(s.startsWith("eyel_") && eyeLRGB !=0) {
//                for(int i = 0; i < firstGroupImage.getHeight(); ++i) {
//                    for (int j = 0; j < firstGroupImage.getWidth(); ++j) {
//                        blendDye(j, i, eyeLRGB, firstGroupImage);
//                    }
//                }
//            } else if(s.startsWith("eyer_") && eyeRRGB !=0) {
//                for(int i = 0; i < firstGroupImage.getHeight(); ++i) {
//                    for (int j = 0; j < firstGroupImage.getWidth(); ++j) {
//                        blendDye(j, i, eyeRRGB, firstGroupImage);
//                    }
//                }
//            }

                while (true) {
                    if (iterator.hasNext()) {
                        String s1 = iterator.next();
                        if (s1.equals("alpha_group_end")) {
                            for (int i = 0; i < firstGroupImage.getHeight(); ++i) {
                                for (int j = 0; j < firstGroupImage.getWidth(); ++j) {
                                    blendAlpha(j, i, maskingImage, firstGroupImage);
                                }
                            }
                            for (int i = 0; i < firstGroupImage.getHeight(); ++i) {
                                for (int j = 0; j < firstGroupImage.getWidth(); ++j) {
                                    blendPixel(baseImage, j, i, firstGroupImage.getPixelRGBA(j, i));
                                }
                            }

                            break;
                        }


                        if (s1 != null) {
                            try {
                                Resource iresource1 = manager.getResourceOrThrow(new ResourceLocation(modLocation + s1));
                                NativeImage nativeimage1 = NativeImage.read(iresource1.open());
                                if (s1.startsWith("c_") && dyeRGB != 0) {
                                    for (int i = 0; i < nativeimage1.getHeight(); ++i) {
                                        for (int j = 0; j < nativeimage1.getWidth(); ++j) {
                                            blendDye(j, i, dyeRGB, nativeimage1);
                                        }
                                    }
                                } else if (s1.startsWith("r_") && redRGB != 0) {
                                    for (int i = 0; i < nativeimage1.getHeight(); ++i) {
                                        for (int j = 0; j < nativeimage1.getWidth(); ++j) {
                                            blendDye(j, i, redRGB, nativeimage1);
                                        }
                                    }
                                } else if (s1.startsWith("b_") && blackRGB != 0) {
                                    for (int i = 0; i < nativeimage1.getHeight(); ++i) {
                                        for (int j = 0; j < nativeimage1.getWidth(); ++j) {
                                            shadeMelanin(j, i, blackRGB, nativeimage1);
                                        }
                                    }
                                }
//                        else if(s1.startsWith("eyel_") && eyeLRGB !=0) {
//                            for(int i = 0; i < nativeimage1.getHeight(); ++i) {
//                                for (int j = 0; j < nativeimage1.getWidth(); ++j) {
//                                    blendDye(j, i, eyeLRGB, nativeimage1);
//                                }
//                            }
//                        } else if(s1.startsWith("eyer_") && eyeRRGB !=0) {
//                            for(int i = 0; i < nativeimage1.getHeight(); ++i) {
//                                for (int j = 0; j < nativeimage1.getWidth(); ++j) {
//                                    blendDye(j, i, eyeRRGB, nativeimage1);
//                                }
//                            }
//                        }

                                for (int i = 0; i < nativeimage1.getHeight(); ++i) {
                                    for (int j = 0; j < nativeimage1.getWidth(); ++j) {
                                        blendPixel(firstGroupImage, j, i, nativeimage1.getPixelRGBA(j, i));
                                    }
                                }
                            } catch (IOException ioexception) {
                                LOGGER.error("Couldn't load layered image", (Throwable) ioexception);
                            }
                        }
                    } else {
                        break;
                    }
                }
            } catch (IOException ioexception) {
                LOGGER.error("Couldn't load layered image", (Throwable) ioexception);
            }
        }
    }

    private NativeImage generateMaskingImage(ResourceManager manager) throws IOException {
        Iterator<String> iterator = this.maskingTextureNames.iterator();
        String s = iterator.next();

                Resource iresource = manager.getResourceOrThrow(new ResourceLocation(modLocation+s));
                NativeImage nativeimage = NativeImage.read(iresource.open());

            while(true) {
                if (!iterator.hasNext()) {
                    TextureUtil.prepareImage(this.getId(), nativeimage.getWidth(), nativeimage.getHeight());
                    return nativeimage;
                }

                String s1 = iterator.next();
                if (s1 != null) {
                    try {
                        Resource iresource1 = manager.getResourceOrThrow(new ResourceLocation(modLocation+s1));
                        NativeImage nativeimage1 = NativeImage.read(iresource1.open());

                        for(int i = 0; i < nativeimage1.getHeight(); ++i) {
                            for(int j = 0; j < nativeimage1.getWidth(); ++j) {
                                blendPixel(nativeimage, j, i, nativeimage1.getPixelRGBA(j, i));
                            }
                        }
                    } catch (IOException ioexception) {
                        LOGGER.error("Couldn't load layered image", (Throwable) ioexception);
                    }
                }
            }
    }

    public void blendDye(int xIn, int yIn, int rgbDye, NativeImage nativeimage) {
        int i = nativeimage.getPixelRGBA(xIn, yIn);

        float f1 = (float)(rgbDye >> 16 & 255) / 255.0F;
        float f2 = (float)(rgbDye >> 8 & 255) / 255.0F;
        float f3 = (float)(rgbDye >> 0 & 255) / 255.0F;
        float originalAlpha = (float)(i >> 24 & 255) / 255.0F;
        float f5 = (float)(i >> 16 & 255) / 255.0F;
        float f6 = (float)(i >> 8 & 255) / 255.0F;
        float f7 = (float)(i >> 0 & 255) / 255.0F;

        if(originalAlpha != 0.0) {
            float f10 = (f1 * 255 ) * f5;
            float f11 = (f2 * 255 ) * f6;
            float f12 = (f3 * 255 ) * f7;

            int j = (int)(originalAlpha * 255.0F);
            int k = (int)(f10);
            int l = (int)(f11);
            int i1 = (int)(f12);

            nativeimage.setPixelRGBA(xIn, yIn, j << 24 | k << 16 | l << 8 | i1 << 0);
        }
    }

    public void shadeMelanin(int xIn, int yIn, int rgbaDye, NativeImage nativeimage) {
        int i = nativeimage.getPixelRGBA(xIn, yIn);

        float layerAlpha = 1.0F - ((float)(rgbaDye >> 24 & 255) / 255.0F);
        float layerBlue = (float)(rgbaDye >> 16 & 255) / 255.0F;
        float layerGreen = (float)(rgbaDye >> 8 & 255) / 255.0F;
        float layerRed = (float)(rgbaDye >> 0 & 255) / 255.0F;
        float originalAlpha = (float)(i >> 24 & 255) / 255.0F;
//        float oBlue = (float)(i >> 16 & 255) / 255.0F;
//        float oGreen = (float)(i >> 8 & 255) / 255.0F;
//        float oRed = (float)(i >> 0 & 255) / 255.0F;

        if (originalAlpha != 0.0F) {

//            float mod = 0.5F;
//            float inverse = 1F - mod;
//            if (layerAlpha > originalAlpha) {
//                originalAlpha = layerAlpha*inverse > originalAlpha ? 0 : 0.5F*(1-((layerAlpha-originalAlpha)/mod));
//            } else {
//                originalAlpha = originalAlpha*inverse > layerAlpha ? 1 : 0.5F + (0.5F*((originalAlpha-layerAlpha)/mod));
//            }

            if (layerAlpha == originalAlpha) {
                originalAlpha = 0.5F;
            } else if (layerAlpha > originalAlpha) {
                originalAlpha = (originalAlpha*0.5F)/layerAlpha;
            } else {
                originalAlpha = 1.0F - (((1.0F-originalAlpha)*0.5F)/(1.0F-layerAlpha));
            }

            originalAlpha = originalAlpha > 1.0F ? 1.0F : originalAlpha;
            originalAlpha = originalAlpha < 0.0F ? 0.0F : originalAlpha;

            int j = (int) (originalAlpha * 255.0F);
            int k = (int)(layerBlue * 255.0F);
            int l = (int)(layerGreen * 255.0F);
            int i1 = (int)(layerRed * 255.0F);

            nativeimage.setPixelRGBA(xIn, yIn, j << 24 | k << 16 | l << 8 | i1 << 0);
        }
    }

    public void blendMelanin(int xIn, int yIn, int rgbaDye, NativeImage nativeimage) {
        int i = nativeimage.getPixelRGBA(xIn, yIn);

        float layerAlpha = 1.0F - ((float)(rgbaDye >> 24 & 255) / 255.0F);
        float layerBlue = (float)(rgbaDye >> 16 & 255) / 255.0F;
        float layerGreen = (float)(rgbaDye >> 8 & 255) / 255.0F;
        float layerRed = (float)(rgbaDye >> 0 & 255) / 255.0F;
        float originalAlpha = (float)(i >> 24 & 255) / 255.0F;
        float oBlue = (float)(i >> 16 & 255) / 255.0F;
        float oGreen = (float)(i >> 8 & 255) / 255.0F;
        float oRed = (float)(i >> 0 & 255) / 255.0F;

        if (originalAlpha != 0.0F) {

            float mod = 0.125F;
            float inverse = 1F - mod;
            if (layerAlpha > originalAlpha) {
                originalAlpha = layerAlpha*inverse > originalAlpha ? 0F : 0.5F*(1-((layerAlpha-originalAlpha)/mod));
            } else {
                originalAlpha = originalAlpha*inverse > layerAlpha ? 1F : 0.5F + (0.5F*((originalAlpha-layerAlpha)/mod));
            }

            originalAlpha = originalAlpha > 1.0F ? 1.0F : originalAlpha;
            originalAlpha = originalAlpha < 0.0F ? 0.0F : originalAlpha;

            float blue = (layerBlue * 255) * oBlue;
            float green = (layerGreen * 255) * oGreen;
            float f12 = (layerRed * 255) * oRed;

            int j = (int) (originalAlpha * 255.0F);
            int k = (int) (blue);
            int l = (int) (green);
            int i1 = (int) (f12);

            nativeimage.setPixelRGBA(xIn, yIn, j << 24 | k << 16 | l << 8 | i1);
        }
    }

    public void blendAlpha(int xIn, int yIn, NativeImage maskingImage, NativeImage nativeImage) {
        int i = nativeImage.getPixelRGBA(xIn, yIn);
        int iAlpha = maskingImage.getPixelRGBA(xIn, yIn);

        float originalAlpha = (float)(i >> 24 & 255) / 255.0F;
        float maskingAlpha = (float)(iAlpha >> 24 & 255) / 255.0F;
        float f5 = (float)(i >> 16 & 255) / 255.0F;
        float f6 = (float)(i >> 8 & 255) / 255.0F;
        float f7 = (float)(i >> 0 & 255) / 255.0F;

        //TODO bgr doesn't need to be unpacked and repacked here

        if(originalAlpha != 0.0) {

            int j = (int)(maskingAlpha * 255.0F);
            int k = (int)(f5*255);
            int l = (int)(f6*255);
            int i1 = (int)(f7*255);

            nativeImage.setPixelRGBA(xIn, yIn, j << 24 | k << 16 | l << 8 | i1);
        }
    }

    public void blendPixel(NativeImage baseImage, int xIn, int yIn, int colIn) {
        int baseI = baseImage.getPixelRGBA(xIn, yIn);
        float alpha = (float) FastColor.ABGR32.alpha(colIn) / 255.0F;
        float blue = (float)FastColor.ABGR32.blue(colIn) / 255.0F;
        float green = (float)FastColor.ABGR32.green(colIn) / 255.0F;
        float red = (float)FastColor.ABGR32.red(colIn) / 255.0F;
        float baseAlpha = (float)FastColor.ABGR32.alpha(baseI) / 255.0F;
        float baseBlue = (float)FastColor.ABGR32.blue(baseI) / 255.0F;
        float baseGreen = (float)FastColor.ABGR32.green(baseI) / 255.0F;
        float baseRed = (float)FastColor.ABGR32.red(baseI) / 255.0F;
        float inverseAlpha = 1.0F - alpha;
        float a = alpha * alpha + baseAlpha * inverseAlpha;
        float b = blue * alpha + baseBlue * inverseAlpha;
        float g = green * alpha + baseGreen * inverseAlpha;
        float r = red * alpha + baseRed * inverseAlpha;
        if (a > 1.0F) {
            a = 1.0F;
        }

        if (b > 1.0F) {
            b = 1.0F;
        }

        if (g > 1.0F) {
            g = 1.0F;
        }

        if (r > 1.0F) {
            r = 1.0F;
        }

        int j = (int)(a * 255.0F);
        int k = (int)(b * 255.0F);
        int l = (int)(g * 255.0F);
        int i1 = (int)(r * 255.0F);
        baseImage.setPixelRGBA(xIn, yIn, FastColor.ABGR32.color(j, k, l, i1));
    }

    public void multiplyPixel(NativeImage baseImage, int xIn, int yIn, int layerABGR) {
        int baseABGR = baseImage.getPixelRGBA(xIn, yIn);
        float layerAlpha = (float)FastColor.ABGR32.alpha(layerABGR) / 255.0F;
        float layerBlue = (float)FastColor.ABGR32.blue(layerABGR) / 255.0F;
        float layerGreen = (float)FastColor.ABGR32.green(layerABGR) / 255.0F;
        float layerRed = (float)FastColor.ABGR32.red(layerABGR) / 255.0F;
        float baseAlpha = (float)FastColor.ABGR32.alpha(baseABGR) / 255.0F;
        float baseBlue = (float)FastColor.ABGR32.blue(baseABGR) / 255.0F;
        float baseGreen = (float)FastColor.ABGR32.green(baseABGR) / 255.0F;
        float baseRed = (float)FastColor.ABGR32.red(baseABGR) / 255.0F;
        float remainder = 1.0F - layerAlpha;
        float alpha = layerAlpha * layerAlpha + baseAlpha * remainder;
        float blue = (layerBlue * baseBlue * layerAlpha) + baseBlue * remainder;
        float green = (layerGreen * baseGreen * layerAlpha) + baseGreen * remainder;
        float red = (layerRed * baseRed * layerAlpha) + baseRed * remainder;
        if (alpha > 1.0F) {
            alpha = 1.0F;
        }

        if (blue > 1.0F) {
            blue = 1.0F;
        }

        if (green > 1.0F) {
            green = 1.0F;
        }

        if (red > 1.0F) {
            red = 1.0F;
        }

        int j = (int)(alpha * 255.0F);
        int k = (int)(blue * 255.0F);
        int l = (int)(green * 255.0F);
        int i1 = (int)(red * 255.0F);
        baseImage.setPixelRGBA(xIn, yIn, FastColor.ABGR32.color(j, k, l, i1));
    }

    public NativeImage getImage() {
        return image;
    }

    public boolean hasImage() {
        return hasImage;
    }

    public void closeImage() {
        this.hasImage = false;
        if (this.image!=null) {
            this.image.close();
        }
    }
}
