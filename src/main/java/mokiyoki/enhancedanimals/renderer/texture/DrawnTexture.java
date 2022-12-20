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

public class DrawnTexture extends AbstractTexture {
    private static final Logger LOGGER = LogManager.getLogger();
    int[] imageArray;
    boolean[][] cuttoutArray;
    private String resourceLocation;
    private ResourceLocation resource;
    private EnhancedLayeredTexturer enhancedLayeredTexturer;

    public DrawnTexture(int[] imageArray, String baseImageLocation) {
        this.imageArray = imageArray;
        this.resourceLocation = baseImageLocation;
    }

    public DrawnTexture(String baseImageLocation, EnhancedLayeredTexturer texturer, boolean[][] cuttoutArray) {
        this.cuttoutArray = cuttoutArray;
        this.enhancedLayeredTexturer = texturer;
        this.resourceLocation = baseImageLocation;
    }

    @Override
    public void load(ResourceManager manager) throws IOException {
        try (
                Resource iresource = manager.getResource(new ResourceLocation(this.resourceLocation));
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
                NativeImage baseImage = this.enhancedLayeredTexturer.getImage();

                for (int x = 0; x < w; x++) {
                    for (int y = 0; y < h; y++) {
                        if (cuttoutArray == null) {
                            nativeimage.setPixelRGBA(x, y, NativeImage.combine(0, 0, 0, 0));
                        } else {
                            nativeimage.setPixelRGBA(x, y, cuttoutArray[x][y] ? baseImage.getPixelRGBA(x, y) : 0);
                        }
                    }
                }
            }

            if (this.enhancedLayeredTexturer!=null) {
                this.enhancedLayeredTexturer.closeImage();
            }

            TextureUtil.prepareImage(this.getId(), nativeimage.getWidth(), nativeimage.getHeight());
            nativeimage.upload(0, 0, 0, true);

        } catch (IOException ioexception) {
            LOGGER.error("Couldn't load base image in DrawnTexture", (Throwable)ioexception);
        }

    }
}
