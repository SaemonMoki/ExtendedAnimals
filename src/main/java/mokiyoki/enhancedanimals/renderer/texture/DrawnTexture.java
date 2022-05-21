package mokiyoki.enhancedanimals.renderer.texture;

import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.platform.TextureUtil;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Iterator;

public class DrawnTexture extends AbstractTexture {
    private static final Logger LOGGER = LogManager.getLogger();
    int[] imageArray;
    protected String baseImageLocation;

    public DrawnTexture(int[] imageArray, String baseImageLocation) {
        this.imageArray = imageArray;
        this.baseImageLocation = baseImageLocation;
    }

    @Override
    public void load(ResourceManager manager) throws IOException {
        try (
                Resource iresource = manager.getResource(new ResourceLocation(baseImageLocation));
                NativeImage nativeimage = NativeImage.read(iresource.getInputStream());

        ) {
            int w = nativeimage.getWidth();
            int h = nativeimage.getHeight();
            for (int i = 0, l = imageArray.length; i < l; i=i+3) {
                int x = imageArray[i];
                int y = imageArray[i+1];
                if (w >= x && h >= y) {
                    nativeimage.setPixelRGBA(x, y, imageArray[i+2]);
                }
            }

            TextureUtil.prepareImage(this.getId(), nativeimage.getWidth(), nativeimage.getHeight());
            nativeimage.upload(0, 0, 0, true);
        } catch (IOException ioexception) {
            LOGGER.error("Couldn't load base image in DrawnTexture", (Throwable)ioexception);
        }

    }
}
