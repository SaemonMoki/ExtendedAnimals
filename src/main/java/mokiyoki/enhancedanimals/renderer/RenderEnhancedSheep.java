package mokiyoki.enhancedanimals.renderer;

import com.google.common.collect.Maps;
import mokiyoki.enhancedanimals.entity.EnhancedSheep;
import mokiyoki.enhancedanimals.model.ModelEnhancedSheep;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Map;

@OnlyIn(Dist.CLIENT)
public class RenderEnhancedSheep extends RenderLiving<EnhancedSheep> {

    private static final Map<String, ResourceLocation> LAYERED_LOCATION_CACHE = Maps.<String, ResourceLocation>newHashMap();
    private static final String ENHANCED_SHEEP_TEXTURE_LOCATION = "eanimod:textures/entities/sheep/";

    public RenderEnhancedSheep(RenderManager render) {
        super(render, new ModelEnhancedSheep(), 0.75F);
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(EnhancedSheep entity)
    {
        String s = entity.getFleeceDyeColour().getName() + entity.getSheepTexture();
        ResourceLocation resourcelocation = LAYERED_LOCATION_CACHE.get(s);

        if (resourcelocation == null)
        {
            float[] dyeRGB = EnhancedSheep.getDyeRgb(entity.getFleeceDyeColour());
//            if (dyeRGB != null) {
//                GlStateManager.color3f(dyeRGB[0], dyeRGB[1], dyeRGB[2]);
//            }
            if (dyeRGB[0] == 1.0 && dyeRGB[1] == 1.0 && dyeRGB[2] == 1.0) {
                dyeRGB = null;
            }

            resourcelocation = new ResourceLocation(s);
            Minecraft.getInstance().getTextureManager().loadTexture(resourcelocation, new EnhancedLayeredTexture(ENHANCED_SHEEP_TEXTURE_LOCATION, dyeRGB, entity.getVariantTexturePaths()));
            LAYERED_LOCATION_CACHE.put(s, resourcelocation);
        }

        return resourcelocation;
    }

}
