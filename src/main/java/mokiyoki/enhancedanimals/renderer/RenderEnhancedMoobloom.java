package mokiyoki.enhancedanimals.renderer;


import com.google.common.collect.Maps;
import mokiyoki.enhancedanimals.entity.EnhancedMoobloom;
import mokiyoki.enhancedanimals.model.ModelEnhancedCow;
import mokiyoki.enhancedanimals.renderer.texture.EnhancedLayeredTexture;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Map;

//import mokiyoki.enhancedanimals.renderer.layers.EnhancedMooshroomMushroomLayer;

@OnlyIn(Dist.CLIENT)
public class RenderEnhancedMoobloom extends MobRenderer<EnhancedMoobloom, ModelEnhancedCow<EnhancedMoobloom>> {
    private static final Map<String, ResourceLocation> LAYERED_LOCATION_CACHE = Maps.<String, ResourceLocation>newHashMap();
    private static final String ENHANCED_COW_TEXTURE_LOCATION = "eanimod:textures/entities/cow/";
    private static final ResourceLocation ERROR_TEXTURE_LOCATION = new ResourceLocation("eanimod:textures/entities/cow/cowbase.png");

    public RenderEnhancedMoobloom(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new ModelEnhancedCow<>(), 0.7F);
    }

    public ResourceLocation getEntityTexture(EnhancedMoobloom entity) {
        String s = entity.getCowTexture();
        float[] colourRGB = entity.getRgb();

        if (s == null || s.isEmpty() || colourRGB == null) {
            return ERROR_TEXTURE_LOCATION;
        }

        if (colourRGB[0] == 1.0 && colourRGB[1] == 1.0 && colourRGB[2] == 1.0) {
            colourRGB = null;
        } else {
            s = s + colourRGB[0] + colourRGB[1] + colourRGB[2] + colourRGB[3] + colourRGB[4] + colourRGB[5];
        }

        ResourceLocation resourcelocation = LAYERED_LOCATION_CACHE.get(s);

        if (resourcelocation == null) {
            String[] textures = entity.getVariantTexturePaths();

            if (textures == null || textures.length == 0) {
                return ERROR_TEXTURE_LOCATION;
            }

            try {
                resourcelocation = new ResourceLocation(s);
                Minecraft.getInstance().getTextureManager().loadTexture(resourcelocation, new EnhancedLayeredTexture(ENHANCED_COW_TEXTURE_LOCATION, colourRGB, textures, null));
                LAYERED_LOCATION_CACHE.put(s, resourcelocation);
            } catch (IllegalStateException e) {
                return ERROR_TEXTURE_LOCATION;
            }
        }

        return resourcelocation;
    }
}