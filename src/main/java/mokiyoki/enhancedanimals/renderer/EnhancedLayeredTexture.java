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
    private String modLocation = "";

    public EnhancedLayeredTexture(String modLocation, String... textureNames)
    {
        super(textureNames);
        this.modLocation = modLocation;

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
                LOGGER.error("Couldn't load layered image", (Throwable)ioexception);
            }
            finally
            {
                IOUtils.closeQuietly((Closeable)iresource);
            }

            return;
        }

        TextureUtil.uploadTextureImage(this.getGlTextureId(), bufferedimage);
    }

}
