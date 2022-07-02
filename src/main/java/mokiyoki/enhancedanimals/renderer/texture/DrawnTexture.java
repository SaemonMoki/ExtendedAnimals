package mokiyoki.enhancedanimals.renderer.texture;

import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.platform.TextureUtil;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.*;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class DrawnTexture extends AbstractTexture {
    private static final Logger LOGGER = LogManager.getLogger();
    int[] imageArray;
    boolean[][] cuttoutArray;
    private ResourceLocation resourceLocation;

    public DrawnTexture(int[] imageArray, String baseImageLocation) {
        this.imageArray = imageArray;
        this.resourceLocation = new ResourceLocation(baseImageLocation);
    }

    public DrawnTexture(ResourceLocation baseImageLocation, boolean[][] cuttoutArray) {
        this.cuttoutArray = cuttoutArray;
        this.resourceLocation = baseImageLocation;
    }

    @Override
    public void load(ResourceManager manager) throws IOException {
        try (
                Resource iresource = manager.getResource(this.resourceLocation);
                NativeImage nativeimage = NativeImage.read(iresource.getInputStream());
        ) {
            if (imageArray!=null) {
                int w = nativeimage.getWidth();
                int h = nativeimage.getHeight();
                for (int i = 0, l = imageArray.length; i < l; i = i + 3) {
                    int x = imageArray[i];
                    int y = imageArray[i + 1];
                    if (w >= x && h >= y) {
                        nativeimage.setPixelRGBA(x, y, imageArray[i + 2]);
                    }
                }
            } else {
                int w = nativeimage.getWidth();
                int h = nativeimage.getHeight();

                for (int x = 0; x < w; x++) {
                    for (int y = 0; y < h; y++) {
                        if (cuttoutArray==null || !cuttoutArray[x][y]) {
                            nativeimage.setPixelRGBA(x, y, -1);
                        }
                    }
                }
//                NativeImage image = new NativeImage(w, h, true);
//                for (int i = 0, l = cuttoutArray.length; i < l; i = i + 2) {
//                    int x = cuttoutArray[i][0];
//                    for (int j = 1, k = cuttoutArray[i].length; j < k; j++) {
//                        int y = cuttoutArray[i][j];
//                        image.setPixelRGBA(x, y, nativeimage.getPixelRGBA(x, y));
//                    }
//                }
//                TextureUtil.prepareImage(this.getId(), image.getWidth(), image.getHeight());
//                image.upload(0, 0, 0, true);
//                nativeimage.close();
            }

            TextureUtil.prepareImage(this.getId(), nativeimage.getWidth(), nativeimage.getHeight());
            nativeimage.upload(0, 0, 0, true);

        } catch (IOException ioexception) {
            LOGGER.error("Couldn't load base image in DrawnTexture", (Throwable)ioexception);
        }

    }
}
