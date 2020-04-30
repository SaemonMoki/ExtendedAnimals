package mokiyoki.enhancedanimals.renderer;

import com.google.common.collect.Maps;
import mokiyoki.enhancedanimals.entity.EnhancedRabbit;
import mokiyoki.enhancedanimals.model.ModelEnhancedRabbit;
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
public class RenderEnhancedRabbit extends MobRenderer<EnhancedRabbit, ModelEnhancedRabbit<EnhancedRabbit>>
{
    private static final Map<String, ResourceLocation> LAYERED_LOCATION_CACHE = Maps.<String, ResourceLocation>newHashMap();
    private static final String ENHANCED_RABBIT_TEXTURE_LOCATION = "eanimod:textures/entities/rabbit/";
    private static final ResourceLocation ERROR_TEXTURE_LOCATION = new ResourceLocation("eanimod:textures/entities/rabbit/rabbitbase.png");

    public RenderEnhancedRabbit(EntityRendererManager render)
    {
        super(render, new ModelEnhancedRabbit<>(), 0.35F);
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    public ResourceLocation getEntityTexture(EnhancedRabbit entity) {
        String s = entity.getRabbitTexture();

        if (s == null || s.isEmpty()) {
            return ERROR_TEXTURE_LOCATION;
        }

        ResourceLocation resourcelocation = LAYERED_LOCATION_CACHE.get(s);

        if (resourcelocation == null) {
            String[] textures = entity.getVariantTexturePaths();

            if (textures == null || textures.length == 0) {
                return ERROR_TEXTURE_LOCATION;
            }

            try {
                resourcelocation = new ResourceLocation(s);
                Minecraft.getInstance().getTextureManager().loadTexture(resourcelocation, new EnhancedLayeredTexture(ENHANCED_RABBIT_TEXTURE_LOCATION, null, textures, null));
                LAYERED_LOCATION_CACHE.put(s, resourcelocation);
            } catch (IllegalStateException e) {
                return ERROR_TEXTURE_LOCATION;
            }

        }

        return resourcelocation;
    }
}