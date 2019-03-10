package mokiyoki.enhancedanimals.renderer;

import net.minecraft.client.renderer.texture.LayeredTexture;
import net.minecraft.client.renderer.texture.NativeImage;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.resources.IResource;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.Closeable;
import java.io.IOException;
import java.util.Iterator;

/**
 * Created by saemon on 8/09/2018.
 */
@OnlyIn(Dist.CLIENT)
public class EnhancedLayeredTexture extends LayeredTexture {

    private static final Logger LOGGER = LogManager.getLogger();
    private String modLocation = "";

    public EnhancedLayeredTexture(String modLocation, String... textureNames)
    {
        super(textureNames);
        this.modLocation = modLocation;

    }

    @Override
    public void loadTexture(IResourceManager manager) throws IOException {
        Iterator<String> iterator = this.layeredTextureNames.iterator();
        String s = iterator.next();

        try (
                IResource iresource = manager.getResource(new ResourceLocation(modLocation+s));
                NativeImage nativeimage = NativeImage.read(iresource.getInputStream());
        ) {
            while(true) {
                if (!iterator.hasNext()) {
                    TextureUtil.allocateTexture(this.getGlTextureId(), nativeimage.getWidth(), nativeimage.getHeight());
                    nativeimage.uploadTextureSub(0, 0, 0, false);
                    break;
                }

                String s1 = iterator.next();
                if (s1 != null) {
                    try (
                            IResource iresource1 = manager.getResource(new ResourceLocation(modLocation+s1));
                            NativeImage nativeimage1 = NativeImage.read(iresource1.getInputStream());
                    ) {
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

}
