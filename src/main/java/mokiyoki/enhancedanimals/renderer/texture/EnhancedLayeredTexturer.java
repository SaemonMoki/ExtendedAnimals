package mokiyoki.enhancedanimals.renderer.texture;

import mokiyoki.enhancedanimals.entity.util.Colouration;
import com.mojang.blaze3d.platform.NativeImage;
import net.minecraft.client.renderer.texture.AbstractTexture;
import com.mojang.blaze3d.platform.TextureUtil;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

@OnlyIn(Dist.CLIENT)
public class EnhancedLayeredTexturer extends AbstractTexture {

    private static final Logger LOGGER = LogManager.getLogger();
    protected final TextureGrouping animalTextureGrouping;
    Colouration colouration;
    protected String modLocation = "";
    private boolean hasImage = false;
    private NativeImage image;

    public EnhancedLayeredTexturer(String modLocation, TextureGrouping textureGrouping, Colouration colouration) {
        this.modLocation = modLocation;
        this.animalTextureGrouping = textureGrouping;
        this.colouration = colouration;
    }

    @Override
    public void load(ResourceManager manager) throws IOException {
        if (animalTextureGrouping != null) {
            NativeImage nativeImage = animalTextureGrouping.processGrouping(this.modLocation, manager, this.colouration);
            TextureUtil.prepareImage(this.getId(), nativeImage.getWidth(), nativeImage.getHeight());
            nativeImage.upload(0, 0, 0, false);

            this.image = new NativeImage(nativeImage.format(), nativeImage.getWidth(), nativeImage.getHeight(), true);
            this.image.copyFrom(nativeImage);
            this.hasImage = true;
        }
    }

    public NativeImage getImage() {
        return image;
    }

    public boolean hasImage() {
        return hasImage;
    }

    public void closeImage() {
        this.hasImage = false;
        this.image.close();
    }
}
