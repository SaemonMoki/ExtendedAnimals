package mokiyoki.enhancedanimals.renderer.texture;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.platform.TextureUtil;
import net.minecraft.client.renderer.texture.NativeImage;
import net.minecraft.client.renderer.texture.Texture;
import net.minecraft.resources.IResource;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

/**
 * Created by saemon on 8/09/2018.
 */
@OnlyIn(Dist.CLIENT)
public class EnhancedLayeredTexture extends Texture {

    private static final Logger LOGGER = LogManager.getLogger();
    protected final List<String> layeredTextureNames;
    protected  int dyeRGB = 0;
    protected  int cowBlackRGB = 0;
    protected  int cowRedRGB = 0;
    protected String modLocation = "";

    public EnhancedLayeredTexture(String modLocation, float[] dyeRGB, String... textureNames) {
        this.layeredTextureNames = Lists.newArrayList(textureNames);

        if (this.layeredTextureNames.isEmpty()) {
            throw new IllegalStateException("Layered texture with no layers.");
        }
        this.modLocation = modLocation;

        if (dyeRGB != null) {
            int j = (int)(0.4 * 255.0F);
            int k = (int)(dyeRGB[2] * 255.0F);
            int l = (int)(dyeRGB[1] * 255.0F);
            int i1 = (int)(dyeRGB[0] * 255.0F);

            this.dyeRGB = j << 24 | k << 16 | l << 8 | i1 << 0;
            this.cowBlackRGB = this.dyeRGB;

            if(dyeRGB.length>3) {
                int j2 = (int)(0.4 * 255.0F);
                int k2 = (int)(dyeRGB[5] * 255.0F);
                int l2 = (int)(dyeRGB[4] * 255.0F);
                int i2 = (int)(dyeRGB[3] * 255.0F);

                this.cowRedRGB = j2 << 24 | k2 << 16 | l2 << 8 | i2 << 0;
            }

        }

    }

    @Override
    public void loadTexture(IResourceManager manager) throws IOException {
        Iterator<String> iterator = this.layeredTextureNames.iterator();
        String s = iterator.next();

        try (
                IResource iresource = manager.getResource(new ResourceLocation(modLocation+s));
                NativeImage nativeimage = NativeImage.read(iresource.getInputStream());

        ) {
            if(s.startsWith("c_") && dyeRGB!=0) {
                for(int i = 0; i < nativeimage.getHeight(); ++i) {
                    for (int j = 0; j < nativeimage.getWidth(); ++j) {
                        blendDye(j, i, dyeRGB, nativeimage);
                    }
                }
            } else if(s.startsWith("r_") && cowRedRGB!=0) {
                for(int i = 0; i < nativeimage.getHeight(); ++i) {
                    for (int j = 0; j < nativeimage.getWidth(); ++j) {
                        blendDye(j, i, cowRedRGB, nativeimage);
                    }
                }
            } else if(s.startsWith("b_") && cowBlackRGB!=0) {
                for(int i = 0; i < nativeimage.getHeight(); ++i) {
                    for (int j = 0; j < nativeimage.getWidth(); ++j) {
                        blendDye(j, i, cowBlackRGB, nativeimage);
                    }
                }
            }
            while(true) {
                if (!iterator.hasNext()) {
                    TextureUtil.prepareImage(this.getGlTextureId(), nativeimage.getWidth(), nativeimage.getHeight());
                    nativeimage.uploadTextureSub(0, 0, 0, false);
                    break;
                }

                String s1 = iterator.next();
                if (s1 != null) {
                    try (
                            IResource iresource1 = manager.getResource(new ResourceLocation(modLocation+s1));
                            NativeImage nativeimage1 = NativeImage.read(iresource1.getInputStream());
                    ) {

                        if(s1.startsWith("c_") && dyeRGB!=0) {
                            for(int i = 0; i < nativeimage1.getHeight(); ++i) {
                                for (int j = 0; j < nativeimage1.getWidth(); ++j) {
                                    blendDye(j, i, dyeRGB, nativeimage1);
                                }
                            }
                        } else if(s.startsWith("r_") && cowRedRGB!=0) {
                            for(int i = 0; i < nativeimage.getHeight(); ++i) {
                                for (int j = 0; j < nativeimage.getWidth(); ++j) {
                                    blendDye(j, i, cowRedRGB, nativeimage);
                                }
                            }
                        } else if(s.startsWith("b_") && cowBlackRGB!=0) {
                            for(int i = 0; i < nativeimage.getHeight(); ++i) {
                                for (int j = 0; j < nativeimage.getWidth(); ++j) {
                                    blendDye(j, i, cowBlackRGB, nativeimage);
                                }
                            }
                        }

                        for(int i = 0; i < nativeimage1.getHeight(); ++i) {
                            for(int j = 0; j < nativeimage1.getWidth(); ++j) {
                                nativeimage.blendPixel(j, i, nativeimage1.getPixelRGBA(j, i));
                            }
                        }
                    }
                }
            }
        } catch (IOException ioexception) {
            LOGGER.error("Couldn't load layered image", (Throwable)ioexception);
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

}
