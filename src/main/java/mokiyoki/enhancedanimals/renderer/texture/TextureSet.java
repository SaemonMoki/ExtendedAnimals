package mokiyoki.enhancedanimals.renderer.texture;

import com.google.common.collect.Lists;
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
import java.util.List;

public class TextureSet extends AbstractTexture {
    private static final Logger LOGGER = LogManager.getLogger();
    protected String modLocation = "";
    private boolean hasImage = false;
    private NativeImage image;
    int colour;
    BlendType blendType;
    List<String> textures;
    List<TextureSet> subTextureSets;

    TextureSet(String texture) {
        this.colour = -1;
        this.blendType = BlendType.NORMAL;
        this.textures = Lists.newArrayList(texture);
    }

    TextureSet(String... texture) {
        this.colour = -1;
        this.blendType = BlendType.NORMAL;
        this.textures = Lists.newArrayList(texture);
    }

    TextureSet(int colour, BlendType blendType, String texture) {
        this.colour = colour;
        this.blendType = blendType;
        this.textures = Lists.newArrayList(texture);
    }

    TextureSet(int colour, BlendType blendType, String... textures) {
        this.colour = colour;
        this.blendType = blendType;
        this.textures = Lists.newArrayList(textures);
    }

    TextureSet(int colour, BlendType blendType, String[] textures, TextureSet[] subTextureSets) {

    }

    private List<String> getTextures() {
        return this.textures;
    }

    private List<TextureSet> getSubTextureSets() {
        return this.subTextureSets;
    }

    private boolean hasSubTextures() {
        return this.subTextureSets !=null && !this.subTextureSets.isEmpty();
    }

    @Override
    public void load(ResourceManager manager) throws IOException {
        Iterator<TextureSet> textureSetIterator;
        TextureSet textureSet = this;
        Iterator<String> iterator;
        String s;

        if (textureSet.hasSubTextures()) {
            textureSetIterator = textureSet.getSubTextureSets().iterator();
            textureSet = textureSetIterator.next();
        }
        iterator = textureSet.getTextures().iterator();
        s = iterator.next();

        try (
                Resource iresource = manager.getResource(new ResourceLocation(modLocation+s));
                NativeImage nativeimage = NativeImage.read(iresource.getInputStream());
        ) {
            switch (textureSet.blendType) {
                case NORMAL -> {

                }
            }
            while(true) {
                if (!iterator.hasNext()) {
                    TextureUtil.prepareImage(this.getId(), nativeimage.getWidth(), nativeimage.getHeight());
                    nativeimage.upload(0, 0, 0, false);
                    break;
                }
            }
            this.image = new NativeImage(nativeimage.format(), nativeimage.getWidth(), nativeimage.getHeight(), true);
            this.image.copyFrom(nativeimage);
            this.hasImage = true;
        } catch (IOException ioexception) {
            LOGGER.error("Couldn't load image in TextureSet", (Throwable)ioexception);
        }
    }

    public enum BlendType {
        NORMAL,
        MULTIPLY,
        AVERAGE,
        CUTOUT,
        MASK,
        PHEOMELANIN
    }
}
