package mokiyoki.enhancedanimals.renderer;

import mokiyoki.enhancedanimals.entity.EnhancedCow;
import mokiyoki.enhancedanimals.entity.util.Colouration;
import mokiyoki.enhancedanimals.model.ModelEnhancedCow;
import mokiyoki.enhancedanimals.renderer.texture.EnhancedLayeredTexturer;
import mokiyoki.enhancedanimals.renderer.texture.TextureGrouping;
import mokiyoki.enhancedanimals.renderer.util.LayeredTextureCacher;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import static mokiyoki.enhancedanimals.GeneticAnimals.MODID;

@OnlyIn(Dist.CLIENT)
public class RenderEnhancedCow extends MobRenderer<EnhancedCow, ModelEnhancedCow<EnhancedCow>> {
    private static final LayeredTextureCacher textureCache = new LayeredTextureCacher();
    private static final String ENHANCED_COW_TEXTURE_LOCATION = "eanimod:textures/entities/cow/";
    private static final ResourceLocation ERROR_TEXTURE_LOCATION = new ResourceLocation("eanimod:textures/entities/cow/cowbase.png");
    public static final ModelLayerLocation COW_LAYER = new ModelLayerLocation(new ResourceLocation(MODID, "cow"), "cow_layer");

    public RenderEnhancedCow(EntityRendererProvider.Context renderManager) {
        super(renderManager, new ModelEnhancedCow<>(renderManager.bakeLayer(COW_LAYER), false), 0.8F);
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    public ResourceLocation getTextureLocation(EnhancedCow entity) {
        String s = entity.getTexture();
        Colouration colourRGB = entity.getRgb();

        if (s == null || s.isEmpty() || colourRGB == null) {
            return ERROR_TEXTURE_LOCATION;
        }

        s = s + colourRGB.getRGBStrings();

        textureCache.removeFromCache(entity.updateCashe(s));

        ResourceLocation resourcelocation = textureCache.getFromCache(s);

        if (resourcelocation == null) {
            TextureGrouping textureGrouping = entity.getTextureGrouping();

            if (textureGrouping == null || !textureGrouping.isPopulated()) {
                return ERROR_TEXTURE_LOCATION;
            }

            try {
                resourcelocation = new ResourceLocation(s);
                Minecraft.getInstance().getTextureManager().register(resourcelocation, new EnhancedLayeredTexturer(ENHANCED_COW_TEXTURE_LOCATION, textureGrouping, colourRGB, 256));

                textureCache.putInCache(s, resourcelocation);
            } catch (IllegalStateException e) {
                return ERROR_TEXTURE_LOCATION;
            }
        }
        return resourcelocation;
    }

    protected boolean shouldShowName(EnhancedCow entity) {
        if (entity.isInPhotoMode) return false;
        return super.shouldShowName(entity);
    }
}
