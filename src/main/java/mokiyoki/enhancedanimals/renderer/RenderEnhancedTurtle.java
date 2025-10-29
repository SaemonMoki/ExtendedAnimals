package mokiyoki.enhancedanimals.renderer;

import mokiyoki.enhancedanimals.entity.EnhancedTurtle;
import mokiyoki.enhancedanimals.entity.util.Colouration;
import mokiyoki.enhancedanimals.model.ModelEnhancedTurtle;
import mokiyoki.enhancedanimals.renderer.texture.EnhancedLayeredTexture;
import mokiyoki.enhancedanimals.renderer.texture.EnhancedLayeredTexturer;
import mokiyoki.enhancedanimals.renderer.texture.TextureGrouping;
import mokiyoki.enhancedanimals.renderer.util.LayeredTextureCacher;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

import static mokiyoki.enhancedanimals.GeneticAnimals.MODID;

public class RenderEnhancedTurtle extends MobRenderer<EnhancedTurtle, ModelEnhancedTurtle<EnhancedTurtle>> {
    private static final LayeredTextureCacher textureCache = new LayeredTextureCacher();
    private static final String ENHANCED_TURTLE_TEXTURE_LOCATION = "eanimod:textures/entity/turtle/";
    private static final ResourceLocation ERROR_TEXTURE_LOCATION = new ResourceLocation("eanimod:textures/entity/turtle/turtlebase.png");
    public static final ModelLayerLocation TURTLE_LAYER = new ModelLayerLocation(new ResourceLocation(MODID, "turtle"), "turtle_layer");

    public RenderEnhancedTurtle(EntityRendererProvider.Context renderManager) {
        super(renderManager, new ModelEnhancedTurtle<>(renderManager.bakeLayer(TURTLE_LAYER)), 0.5F);
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    public ResourceLocation getTextureLocation(EnhancedTurtle entity) {
        String s = entity.getTexture();
        Colouration colourRGB = entity.getRgb();

        if (s == null || s.isEmpty() || colourRGB == null) {
            return ERROR_TEXTURE_LOCATION;
        }

        s = s + colourRGB.getRGBStrings();

        ResourceLocation resourcelocation = textureCache.getFromCache(s);

        if (resourcelocation == null) {
            TextureGrouping textureGrouping = entity.getTextureGrouping();

            if (textureGrouping == null || !textureGrouping.isPopulated()) {
                return ERROR_TEXTURE_LOCATION;
            }

            try {
                resourcelocation = new ResourceLocation(s);

                Minecraft.getInstance().getTextureManager().register(resourcelocation, new EnhancedLayeredTexturer(ENHANCED_TURTLE_TEXTURE_LOCATION, textureGrouping, entity.colouration, 128, 64));

                textureCache.putInCache(s, resourcelocation);
            } catch (IllegalStateException e) {
                return ERROR_TEXTURE_LOCATION;
            }
        }

        return resourcelocation;
    }

    protected boolean shouldShowName(EnhancedTurtle entity) {
        if (entity.isInPhotoMode) return false;
        return super.shouldShowName(entity);
    }
}
