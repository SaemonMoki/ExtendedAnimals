package mokiyoki.enhancedanimals.renderer;

import com.google.common.collect.Lists;
import net.minecraft.client.renderer.texture.LayeredTexture;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.Closeable;
import java.io.IOException;

/**
 * Created by saemon on 8/09/2018.
 */
@SideOnly(Side.CLIENT)
public class EnhancedLayeredTexture extends LayeredTexture {

    private static final Logger LOGGER = LogManager.getLogger();
    protected  int dyeRGB = 0;
    protected  int cowBlackRGB = 0;
    protected  int cowRedRGB = 0;
    private String modLocation = "";

    public EnhancedLayeredTexture(String modLocation, float[] dyeRGB, String... textureNames) {
        super(textureNames);
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
    public void loadTexture(IResourceManager resourceManager) throws IOException
    {
        this.deleteGlTexture();
        BufferedImage bufferedimage = null;

        for (String s : this.layeredTextureNames)
        {
            IResource iresource = null;

            try
            {
                if (s != null)
                {
                    iresource = resourceManager.getResource(new ResourceLocation(modLocation+s));
                    BufferedImage bufferedimage1 = TextureUtil.readBufferedImage(iresource.getInputStream());

                    if(s.startsWith("c_") && dyeRGB!=0) {
                        for(int i = 0; i < bufferedimage1.getHeight(); ++i) {
                            for (int j = 0; j < bufferedimage1.getWidth(); ++j) {
                                blendDye(j, i, dyeRGB, bufferedimage1);
                            }
                        }
                    } else if(s.startsWith("r_") && cowRedRGB!=0) {
                        for(int i = 0; i < bufferedimage1.getHeight(); ++i) {
                            for (int j = 0; j < bufferedimage1.getWidth(); ++j) {
                                blendDye(j, i, cowRedRGB, bufferedimage1);
                            }
                        }
                    } else if(s.startsWith("b_") && cowBlackRGB!=0) {
                        for(int i = 0; i < bufferedimage1.getHeight(); ++i) {
                            for (int j = 0; j < bufferedimage1.getWidth(); ++j) {
                                blendDye(j, i, cowBlackRGB, bufferedimage1);
                            }
                        }
                    }

                    if (bufferedimage == null)
                    {
                        bufferedimage = new BufferedImage(bufferedimage1.getWidth(), bufferedimage1.getHeight(), 2);
                    }

                    bufferedimage.getGraphics().drawImage(bufferedimage1, 0, 0, (ImageObserver)null);
                }

                continue;
            }
            catch (IOException ioexception)
            {
                LOGGER.error("Couldn't load enhanced layered image", (Throwable)ioexception);
            }
            finally
            {
                IOUtils.closeQuietly((Closeable)iresource);
            }

            return;
        }

        TextureUtil.uploadTextureImage(this.getGlTextureId(), bufferedimage);
    }

    public void blendDye(int xIn, int yIn, int rgbDye, BufferedImage nativeimage) {
        int i = nativeimage.getRGB(xIn, yIn);

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

            nativeimage.setRGB(xIn, yIn, j << 24 | k << 16 | l << 8 | i1 << 0);
        }
    }


}
