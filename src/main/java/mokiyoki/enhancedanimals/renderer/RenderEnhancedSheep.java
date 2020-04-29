package mokiyoki.enhancedanimals.renderer;

import com.google.common.collect.Maps;
import mokiyoki.enhancedanimals.entity.EnhancedSheep;
import mokiyoki.enhancedanimals.model.ModelEnhancedSheep;
import mokiyoki.enhancedanimals.renderer.texture.EnhancedLayeredTexture;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Map;

@OnlyIn(Dist.CLIENT)
public class RenderEnhancedSheep extends MobRenderer<EnhancedSheep, ModelEnhancedSheep<EnhancedSheep>> {

    private static final Map<String, ResourceLocation> LAYERED_LOCATION_CACHE = Maps.<String, ResourceLocation>newHashMap();
    private static final String ENHANCED_SHEEP_TEXTURE_LOCATION = "eanimod:textures/entities/sheep/";
    private static final ResourceLocation ERROR_TEXTURE_LOCATION = new ResourceLocation("eanimod:textures/entities/sheep/sheep.png");

    public RenderEnhancedSheep(EntityRendererManager render) {
        super(render, new ModelEnhancedSheep<>(), 0.6F);
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    public ResourceLocation getEntityTexture(EnhancedSheep entity) {
        String s = entity.getFleeceDyeColour().getName() + entity.getSheepTexture();

        if (s == null || s.isEmpty()) {
            return ERROR_TEXTURE_LOCATION;
        }

        ResourceLocation resourcelocation = LAYERED_LOCATION_CACHE.get(s);

        if (resourcelocation == null) {
            float[] dyeRGB = EnhancedSheep.getDyeRgb(entity.getFleeceDyeColour());
            String[] textures = entity.getVariantTexturePaths();

            if (dyeRGB == null || dyeRGB.length == 0 || textures == null || textures.length == 0) {
                return ERROR_TEXTURE_LOCATION;
            }

            if (dyeRGB[0] == 1.0 && dyeRGB[1] == 1.0 && dyeRGB[2] == 1.0) {
                dyeRGB = null;
            }

            resourcelocation = new ResourceLocation(s);
            Minecraft.getInstance().getTextureManager().loadTexture(resourcelocation, new EnhancedLayeredTexture(ENHANCED_SHEEP_TEXTURE_LOCATION, dyeRGB, textures, null));
            LAYERED_LOCATION_CACHE.put(s, resourcelocation);
        }

        return resourcelocation;
    }

}
