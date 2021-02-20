package mokiyoki.enhancedanimals.renderer;

import mokiyoki.enhancedanimals.entity.EnhancedChicken;
import mokiyoki.enhancedanimals.entity.util.Colouration;
import mokiyoki.enhancedanimals.model.ModelEnhancedChicken;
import mokiyoki.enhancedanimals.renderer.layers.SilkieChickenLayer;
import mokiyoki.enhancedanimals.renderer.texture.EnhancedLayeredTexture;
import mokiyoki.enhancedanimals.renderer.util.LayeredTextureCacher;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;


/**
 * Created by saemon on 2/09/2018.
 */
@OnlyIn(Dist.CLIENT)
public class RenderEnhancedChicken extends MobRenderer<EnhancedChicken, ModelEnhancedChicken<EnhancedChicken>> {
    private static final LayeredTextureCacher textureCache = new LayeredTextureCacher();
    private static final String ENHANCED_CHICKEN_TEXTURE_LOCATION = "eanimod:textures/entities/chicken/";
    private static final String ENHANCED_CHICKENSILKIE_TEXTURE_LOCATION = "eanimod:textures/entities/chickensilkie/";
    private static final ResourceLocation ERROR_TEXTURE_LOCATION = new ResourceLocation("eanimod:textures/entities/chicken/chickenbase.png");

    public RenderEnhancedChicken(EntityRendererManager render)
    {
        super(render, new ModelEnhancedChicken<>(), 0.5F);
//        this.addLayer(new SilkieChickenLayer(this));
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    public ResourceLocation getEntityTexture(EnhancedChicken entity) {
        entity.getEntityWorld().getProfiler().startSection("ChickenRenderer");
        String s = entity.getChickenTexture();
        Colouration colourRGB = entity.getRgb();
        boolean silkie = entity.getSharedGenes().testGenes(106, 1, 2);

        if (s == null || s.isEmpty() || colourRGB == null) {
            return ERROR_TEXTURE_LOCATION;
        }

        s = s + colourRGB.getRGBStrings();

        ResourceLocation resourcelocation = textureCache.getFromCache(s);

        if (resourcelocation == null) {
            String[] textures = entity.getVariantTexturePaths();

            if (textures == null || textures.length == 0) {
                return ERROR_TEXTURE_LOCATION;
            }

            try {
                resourcelocation = new ResourceLocation(s);

                Minecraft.getInstance().getTextureManager().loadTexture(resourcelocation, new EnhancedLayeredTexture(silkie ? ENHANCED_CHICKENSILKIE_TEXTURE_LOCATION : ENHANCED_CHICKEN_TEXTURE_LOCATION, textures, null, entity.colouration));
//                if (genes[106] == 1 || genes[107] == 1) {
//                    Minecraft.getInstance().getTextureManager().loadTexture(resourcelocation, new EnhancedLayeredTexture(ENHANCED_CHICKEN_TEXTURE_LOCATION, null, textures, null));
//                } else {
//                    Minecraft.getInstance().getTextureManager().loadTexture(resourcelocation, new EnhancedLayeredTexture(ENHANCED_CHICKENSILKIE_TEXTURE_LOCATION, null, textures, null));
//                }

                textureCache.putInCache(s, resourcelocation);
            } catch (IllegalStateException e) {
                return ERROR_TEXTURE_LOCATION;
            }
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