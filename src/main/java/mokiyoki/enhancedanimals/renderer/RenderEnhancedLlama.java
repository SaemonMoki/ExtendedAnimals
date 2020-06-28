package mokiyoki.enhancedanimals.renderer;

import com.google.common.collect.Maps;
import mokiyoki.enhancedanimals.entity.EnhancedLlama;
import mokiyoki.enhancedanimals.entity.util.Colouration;
import mokiyoki.enhancedanimals.model.ModelEnhancedLlama;
import mokiyoki.enhancedanimals.renderer.texture.EnhancedLayeredTexture;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Map;

/**
 * Created by saemon on 2/09/2018.
 */
@OnlyIn(Dist.CLIENT)
public class RenderEnhancedLlama extends MobRenderer<EnhancedLlama, ModelEnhancedLlama<EnhancedLlama>>
{
    private static final Map<String, ResourceLocation> LAYERED_LOCATION_CACHE = Maps.<String, ResourceLocation>newHashMap();
    private static final String ENHANCED_LLAMA_TEXTURE_LOCATION = "eanimod:textures/entities/llama/";
    private static final ResourceLocation ERROR_TEXTURE_LOCATION = new ResourceLocation("eanimod:textures/entities/llama/llamabase.png");

    public RenderEnhancedLlama(EntityRendererManager render)
    {
        super(render, new ModelEnhancedLlama<>(0.0F), 0.75F);
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    public ResourceLocation getEntityTexture(EnhancedLlama entity) {
        String s = entity.getLlamaTexture();
        Colouration colourRGB = entity.getRgb();

        if (s == null || s.isEmpty() || colourRGB == null) {
            return ERROR_TEXTURE_LOCATION;
        }

        s = s + colourRGB.getRGBStrings();

        ResourceLocation resourcelocation = LAYERED_LOCATION_CACHE.get(s);

        if (resourcelocation == null) {
            String[] textures = entity.getVariantTexturePaths();

            if (textures == null || textures.length == 0) {
                return ERROR_TEXTURE_LOCATION;
            }

            try {
                resourcelocation = new ResourceLocation(s);
                Minecraft.getInstance().getTextureManager().loadTexture(resourcelocation, new EnhancedLayeredTexture(ENHANCED_LLAMA_TEXTURE_LOCATION, textures, null, colourRGB));
                LAYERED_LOCATION_CACHE.put(s, resourcelocation);
            } catch (IllegalStateException e) {
                return ERROR_TEXTURE_LOCATION;
            }
        }

        return resourcelocation;
    }
}