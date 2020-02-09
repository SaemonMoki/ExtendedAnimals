package mokiyoki.enhancedanimals.renderer;

import com.google.common.collect.Maps;
import mokiyoki.enhancedanimals.entity.EnhancedHorse;
import mokiyoki.enhancedanimals.model.ModelEnhancedHorse;
import mokiyoki.enhancedanimals.renderer.texture.EnhancedLayeredTexture;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

import java.util.Map;

public class RenderEnhancedHorse extends MobRenderer<EnhancedHorse, ModelEnhancedHorse<EnhancedHorse>> {
    private static final Map<String, ResourceLocation> LAYERED_LOCATION_CACHE = Maps.<String, ResourceLocation>newHashMap();
    private static final String ENHANCED_HORSE_TEXTURE_LOCATION = "eanimod:textures/entities/horse/";

    public RenderEnhancedHorse(EntityRendererManager render)
    {
        super(render, new ModelEnhancedHorse<>(), 0.75F);
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(EnhancedHorse entity) {
        String s = entity.getHorseTexture();

        float[] colourRGB = entity.getRgb();
        if (colourRGB[0] == 1.0 && colourRGB[1] == 1.0 && colourRGB[2] == 1.0) {
            colourRGB = null;
        } else {
            s = s + colourRGB[0] + colourRGB[1] + colourRGB[2] + colourRGB[3] + colourRGB[4] + colourRGB[5];
        }

        ResourceLocation resourcelocation = LAYERED_LOCATION_CACHE.get(s);

        if (resourcelocation == null)
        {
            resourcelocation = new ResourceLocation(s);
            Minecraft.getInstance().getTextureManager().loadTexture(resourcelocation, new EnhancedLayeredTexture(ENHANCED_HORSE_TEXTURE_LOCATION, colourRGB, entity.getVariantTexturePaths(), null));
            LAYERED_LOCATION_CACHE.put(s, resourcelocation);
        }

        return resourcelocation;
    }
}
