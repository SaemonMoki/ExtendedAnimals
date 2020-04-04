package mokiyoki.enhancedanimals.renderer;

import com.google.common.collect.Maps;
import mokiyoki.enhancedanimals.entity.EnhancedChicken;
import mokiyoki.enhancedanimals.model.ModelEnhancedChicken;
import mokiyoki.enhancedanimals.renderer.texture.EnhancedLayeredTexture;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Map;

/**
 * Created by saemon on 2/09/2018.
 */
@OnlyIn(Dist.CLIENT)
public class RenderEnhancedChicken extends MobRenderer<EnhancedChicken, ModelEnhancedChicken<EnhancedChicken>>
{
    private static final Map<String, ResourceLocation> LAYERED_LOCATION_CACHE = Maps.<String, ResourceLocation>newHashMap();
    private static final String ENHANCED_CHICKEN_TEXTURE_LOCATION = "eanimod:textures/entities/chicken/";
    private static final String ENHANCED_CHICKENSILKIE_TEXTURE_LOCATION = "eanimod:textures/entities/chickensilkie/";

    public RenderEnhancedChicken(EntityRendererManager render)
    {
        super(render, new ModelEnhancedChicken<>(), 0.5F);
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(EnhancedChicken entity)
    {
        String s = entity.getChickenTexture();
        int[] genes = entity.getSharedGenes();
        ResourceLocation resourcelocation = LAYERED_LOCATION_CACHE.get(s);

        if (resourcelocation == null)
        {
            resourcelocation = new ResourceLocation(s);

            if (genes[106] == 1 || genes[107] == 1) {
                Minecraft.getInstance().getTextureManager().loadTexture(resourcelocation, new EnhancedLayeredTexture(ENHANCED_CHICKEN_TEXTURE_LOCATION, null, entity.getVariantTexturePaths(), null));
            } else {
                Minecraft.getInstance().getTextureManager().loadTexture(resourcelocation, new EnhancedLayeredTexture(ENHANCED_CHICKENSILKIE_TEXTURE_LOCATION, null, entity.getVariantTexturePaths(), null));
            }

            LAYERED_LOCATION_CACHE.put(s, resourcelocation);
        }

        return resourcelocation;
    }

    protected float handleRotationFloat(EnhancedChicken livingBase, float partialTicks)
    {
        float f = livingBase.oFlap + (livingBase.wingRotation - livingBase.oFlap) * partialTicks;
        float f1 = livingBase.oFlapSpeed + (livingBase.destPos - livingBase.oFlapSpeed) * partialTicks;
        return (MathHelper.sin(f) + 1.0F) * f1;
    }
}