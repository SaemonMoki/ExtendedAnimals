package mokiyoki.enhancedanimals.renderer;

import mokiyoki.enhancedanimals.entity.EnhancedChicken;
import mokiyoki.enhancedanimals.entity.util.Colouration;
import mokiyoki.enhancedanimals.model.ModelEnhancedChicken;
import mokiyoki.enhancedanimals.renderer.texture.EnhancedLayeredTexture;
import mokiyoki.enhancedanimals.renderer.texture.EnhancedLayeredTexturer;
import mokiyoki.enhancedanimals.renderer.texture.TextureGrouping;
import mokiyoki.enhancedanimals.renderer.util.LayeredTextureCacher;
import mokiyoki.enhancedanimals.util.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Map;


/**
 * Created by saemon on 2/09/2018.
 */
@OnlyIn(Dist.CLIENT)
public class RenderEnhancedChicken extends MobRenderer<EnhancedChicken, ModelEnhancedChicken<EnhancedChicken>> {
    private static final LayeredTextureCacher textureCache = new LayeredTextureCacher();
    private static final String ENHANCED_CHICKEN_TEXTURE_LOCATION = "eanimod:textures/entities/chicken/";
    private static final ResourceLocation ERROR_TEXTURE_LOCATION = new ResourceLocation("eanimod:textures/entities/chicken/chickenbase.png");
    public static final ModelLayerLocation CHICKEN_LAYER = new ModelLayerLocation(new ResourceLocation(Reference.MODID, "chicken"), "chicken_layer");

    public RenderEnhancedChicken(EntityRendererProvider.Context renderManager) {
        super(renderManager, new ModelEnhancedChicken<>(renderManager.bakeLayer(CHICKEN_LAYER)), 0.5F);
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    public ResourceLocation getTextureLocation(EnhancedChicken entity) {
        String s = entity.getTexture();
        Colouration colourRGB = entity.getRgb();
        boolean silkie = false;
        if (entity.getSharedGenes() != null) {
            silkie = entity.getSharedGenes().isHomozygousFor(106, 2);
        }

        if (s == null || s.isEmpty() || colourRGB == null) {
            return ERROR_TEXTURE_LOCATION;
        }

        s = s + colourRGB.getRGBStrings();

        s = silkie ? s + "1" : s + "-";

        ResourceLocation resourcelocation = textureCache.getFromCache(s);

        if (resourcelocation == null) {
            TextureGrouping textureGrouping = entity.getTextureGrouping();

            if (textureGrouping == null || !textureGrouping.isPopulated()) {
                return ERROR_TEXTURE_LOCATION;
            }

            try {
                resourcelocation = new ResourceLocation(s);

                Minecraft.getInstance().getTextureManager().register(resourcelocation, new EnhancedLayeredTexturer(ENHANCED_CHICKEN_TEXTURE_LOCATION, textureGrouping, entity.colouration, silkie ? 64 : 320));

                textureCache.putInCache(s, resourcelocation);
            } catch (IllegalStateException e) {
                return ERROR_TEXTURE_LOCATION;
            }
        }

        return resourcelocation;
    }

    protected boolean shouldShowName(EnhancedChicken entity) {
        if (entity.isInPhotoMode) return false;
        return super.shouldShowName(entity);
    }

}