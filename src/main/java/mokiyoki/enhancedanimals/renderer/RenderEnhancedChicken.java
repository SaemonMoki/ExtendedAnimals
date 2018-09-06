package mokiyoki.enhancedanimals.renderer;

import com.google.common.collect.Maps;
import mokiyoki.enhancedanimals.entity.EnhancedChicken;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.LayeredTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Map;

/**
 * Created by saemon on 2/09/2018.
 */
@SideOnly(Side.CLIENT)
public class RenderEnhancedChicken extends RenderLiving<EnhancedChicken>
{
    private static final Map<String, ResourceLocation> LAYERED_LOCATION_CACHE = Maps.<String, ResourceLocation>newHashMap();

    public RenderEnhancedChicken(RenderManager render)
    {
        super(render, new ModelEnhancedChicken(), 0.75F);
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(EnhancedChicken entity)
    {
            ResourceLocation resourcelocation = new ResourceLocation("enhancedChicken/");
            Minecraft.getMinecraft().getTextureManager().loadTexture(resourcelocation, new LayeredTexture(entity.getVariantTexturePaths()));
            LAYERED_LOCATION_CACHE.put("enhancedChicken/", resourcelocation);

        return resourcelocation;
    }
}