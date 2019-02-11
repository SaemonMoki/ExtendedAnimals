package mokiyoki.enhancedanimals.renderer;

import com.google.common.collect.Maps;
import mokiyoki.enhancedanimals.entity.EnhancedHorse;
import mokiyoki.enhancedanimals.model.ModelEnhancedHorse;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Map;

@SideOnly(Side.CLIENT)
public class RenderEnhancedHorse extends RenderLiving<EnhancedHorse>
{
    private static final Map<String, ResourceLocation> LAYERED_LOCATION_CACHE = Maps.<String, ResourceLocation>newHashMap();
    private static final String ENHANCED_HORSE_TEXTURE_LOCATION = "eanimod:textures/entities/horse/";

    public RenderEnhancedHorse(RenderManager render)
    {
        super(render, new ModelEnhancedHorse(), 0.25F);
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(EnhancedHorse entity)
    {
        String s = entity.getHorseTexture();
        ResourceLocation resourcelocation = LAYERED_LOCATION_CACHE.get(s);

        if (resourcelocation == null)
        {
            resourcelocation = new ResourceLocation(s);
            Minecraft.getMinecraft().getTextureManager().loadTexture(resourcelocation, new EnhancedLayeredTexture(ENHANCED_HORSE_TEXTURE_LOCATION, entity.getVariantTexturePaths()));
            LAYERED_LOCATION_CACHE.put(s, resourcelocation);
        }

        return resourcelocation;
    }
}