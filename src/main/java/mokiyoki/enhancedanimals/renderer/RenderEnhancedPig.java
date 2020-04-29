package mokiyoki.enhancedanimals.renderer;

import com.google.common.collect.Maps;
import mokiyoki.enhancedanimals.entity.EnhancedPig;
import mokiyoki.enhancedanimals.model.ModelEnhancedPig;
import mokiyoki.enhancedanimals.renderer.texture.EnhancedLayeredTexture;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Map;

@OnlyIn(Dist.CLIENT)
public class RenderEnhancedPig extends MobRenderer<EnhancedPig, ModelEnhancedPig<EnhancedPig>>
{

    private static final Map<String, ResourceLocation> LAYERED_LOCATION_CACHE = Maps.<String, ResourceLocation>newHashMap();
    private static final String ENHANCED_PIG_TEXTURE_LOCATION = "eanimod:textures/entities/pig/";
    private static final ResourceLocation ERROR_TEXTURE_LOCATION = new ResourceLocation("eanimod:textures/entities/pig/pigbase.png");

    public RenderEnhancedPig(EntityRendererManager render)
    {
        super(render, new ModelEnhancedPig<>(), 0.8F);
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    public ResourceLocation getEntityTexture(EnhancedPig entity) {
        String s = entity.getPigTexture();

        if (s == null || s.isEmpty()) {
            return ERROR_TEXTURE_LOCATION;
        }

        ResourceLocation resourcelocation = LAYERED_LOCATION_CACHE.get(s);

        if (resourcelocation == null) {
            String[] textures = entity.getVariantTexturePaths();

            if (textures == null || textures.length == 0) {
                return ERROR_TEXTURE_LOCATION;
            }

            resourcelocation = new ResourceLocation(s);
            Minecraft.getInstance().getTextureManager().loadTexture(resourcelocation, new EnhancedLayeredTexture(ENHANCED_PIG_TEXTURE_LOCATION, null, textures, entity.getVariantAlphaTexturePaths()));
           LAYERED_LOCATION_CACHE.put(s, resourcelocation);
        }

        return resourcelocation;
    }
    
}
