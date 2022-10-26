package mokiyoki.enhancedanimals.renderer;


import mokiyoki.enhancedanimals.entity.EnhancedMoobloom;
import mokiyoki.enhancedanimals.entity.util.Colouration;
import mokiyoki.enhancedanimals.model.ModelEnhancedCow;
import mokiyoki.enhancedanimals.renderer.texture.EnhancedLayeredTexture;
import mokiyoki.enhancedanimals.renderer.util.LayeredTextureCacher;
import mokiyoki.enhancedanimals.util.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;


@OnlyIn(Dist.CLIENT)
public class RenderEnhancedMoobloom extends MobRenderer<EnhancedMoobloom, ModelEnhancedCow<EnhancedMoobloom>> {
    private static final LayeredTextureCacher textureCache = new LayeredTextureCacher();
    private static final String ENHANCED_COW_TEXTURE_LOCATION = "eanimod:textures/entities/cow/";
    private static final ResourceLocation ERROR_TEXTURE_LOCATION = new ResourceLocation("eanimod:textures/entities/cow/cowbase.png");
    public static final ModelLayerLocation MOOBLOOM_LAYER = new ModelLayerLocation(new ResourceLocation(Reference.MODID, "moobloom"), "moobloom_layer");

    public RenderEnhancedMoobloom(EntityRendererProvider.Context renderManager) {
        super(renderManager, new ModelEnhancedCow<>(renderManager.bakeLayer(MOOBLOOM_LAYER), true), 0.7F);
    }

    public ResourceLocation getTextureLocation(EnhancedMoobloom entity) {
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
                Minecraft.getInstance().getTextureManager().register(resourcelocation, new EnhancedLayeredTexture(ENHANCED_COW_TEXTURE_LOCATION, textures, null, colourRGB));

                textureCache.putInCache(s, resourcelocation);
            } catch (IllegalStateException e) {
                return ERROR_TEXTURE_LOCATION;
            }
        }

        return resourcelocation;
    }
}