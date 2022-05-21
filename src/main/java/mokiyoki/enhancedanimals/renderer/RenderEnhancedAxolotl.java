package mokiyoki.enhancedanimals.renderer;

import com.mojang.blaze3d.platform.NativeImage;
import mokiyoki.enhancedanimals.entity.EnhancedAxolotl;
import mokiyoki.enhancedanimals.entity.util.Colouration;
import mokiyoki.enhancedanimals.model.ModelEnhancedAxolotl;
import mokiyoki.enhancedanimals.renderer.texture.EnhancedLayeredTexture;
import mokiyoki.enhancedanimals.renderer.util.LayeredTextureCacher;
import mokiyoki.enhancedanimals.util.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ReloadableResourceManager;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceProvider;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.MinecraftForgeClient;

import java.io.IOException;
import java.io.InputStream;

@OnlyIn(Dist.CLIENT)
public class RenderEnhancedAxolotl extends MobRenderer<EnhancedAxolotl, ModelEnhancedAxolotl<EnhancedAxolotl>> {
    private static final LayeredTextureCacher textureCache = new LayeredTextureCacher();
    private static final String ENHANCED_AXOLOTL_TEXTURE_LOCATION = "eanimod:textures/entities/axolotl/";
    private static final ResourceLocation ERROR_TEXTURE_LOCATION = new ResourceLocation("eanimod:textures/entities/axolotl/axolotlbase.png");
    public static final ModelLayerLocation AXOLOTL_LAYER = new ModelLayerLocation(new ResourceLocation(Reference.MODID, "axolotl"), "axolotl_layer");

    public RenderEnhancedAxolotl(EntityRendererProvider.Context renderManager) {
        super(renderManager, new ModelEnhancedAxolotl<>(renderManager.bakeLayer(AXOLOTL_LAYER)), 0.5F);
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    public ResourceLocation getTextureLocation(EnhancedAxolotl entity) {
        String s = entity.getTexture();
        Colouration colourRGB = entity.getRgb();

        if (s == null || s.isEmpty() || colourRGB == null) {
            return ERROR_TEXTURE_LOCATION;
        }

        s = s + colourRGB.getRGBStrings();

        ResourceLocation resourcelocation = textureCache.getFromCache(s);

        if (resourcelocation == null) {
            String[] textures = entity.getVariantTexturePaths();

            if (textures == null || textures.length == 0) {
                return ERROR_TEXTURE_LOCATION;
            }

            try {
                resourcelocation = new ResourceLocation(s);
                EnhancedLayeredTexture texture = new EnhancedLayeredTexture(ENHANCED_AXOLOTL_TEXTURE_LOCATION, textures, null, entity.colouration);
                Minecraft.getInstance().getTextureManager().register(resourcelocation, texture);
                textureCache.putInCache(s, resourcelocation);
                entity.setBucketImage(texture);
            } catch (IllegalStateException e) {
                return ERROR_TEXTURE_LOCATION;
            }
        }

        return resourcelocation;
    }
}
