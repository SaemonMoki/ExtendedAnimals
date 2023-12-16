package mokiyoki.enhancedanimals.renderer.texture;

import com.google.common.collect.Lists;
import mokiyoki.enhancedanimals.entity.util.Colouration;
import com.mojang.blaze3d.platform.NativeImage;
import net.minecraft.client.renderer.texture.AbstractTexture;
import com.mojang.blaze3d.platform.TextureUtil;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.mojang.blaze3d.platform.NativeImage.combine;
import static com.mojang.blaze3d.platform.NativeImage.getA;
import static com.mojang.blaze3d.platform.NativeImage.getB;
import static com.mojang.blaze3d.platform.NativeImage.getG;
import static com.mojang.blaze3d.platform.NativeImage.getR;

@OnlyIn(Dist.CLIENT)
public class EnhancedLayeredTexturer extends AbstractTexture {

    private static final Logger LOGGER = LogManager.getLogger();
    protected final TextureGrouping animalTextureGrouping;
    Colouration colouration;
    protected String modLocation = "";
    private boolean hasImage = false;
    private NativeImage image;

    private final int x;
    private final int y;

    public EnhancedLayeredTexturer(String modLocation, TextureGrouping textureGrouping, Colouration colouration, int s) {
        this.modLocation = modLocation;
        this.animalTextureGrouping = textureGrouping;
        this.colouration = colouration;
        this.x = s;
        this.y = s;
    }
    public EnhancedLayeredTexturer(String modLocation, TextureGrouping textureGrouping, Colouration colouration, int x, int y) {
        this.modLocation = modLocation;
        this.animalTextureGrouping = textureGrouping;
        this.colouration = colouration;
        this.x = x;
        this.y = y;
    }

    @Override
    public void load(ResourceManager manager) throws IOException {
        if (animalTextureGrouping != null) {
            NativeImage nativeImage = animalTextureGrouping.processGrouping(this.modLocation, manager, this.colouration, this.x, this.y);
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
