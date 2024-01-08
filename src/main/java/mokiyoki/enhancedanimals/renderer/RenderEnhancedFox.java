package mokiyoki.enhancedanimals.renderer;

import mokiyoki.enhancedanimals.entity.EnhancedFox;
import mokiyoki.enhancedanimals.entity.util.Colouration;
import mokiyoki.enhancedanimals.model.ModelEnhancedFox;
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


@OnlyIn(Dist.CLIENT)
public class RenderEnhancedFox extends MobRenderer<EnhancedFox, ModelEnhancedFox<EnhancedFox>> {

    private static final LayeredTextureCacher textureCache = new LayeredTextureCacher();
    private static final String ENHANCED_FOX_TEXTURE_LOCATION = "eanimod:textures/entities/fox/";
    private static final ResourceLocation ERROR_TEXTURE_LOCATION = new ResourceLocation("eanimod:textures/entities/fox/base1.png");
    public static final ModelLayerLocation FOX_LAYER = new ModelLayerLocation(new ResourceLocation(Reference.MODID, "fox"), "fox_layer");

    public RenderEnhancedFox(EntityRendererProvider.Context renderManager)
    {
        super(renderManager, new ModelEnhancedFox<>(renderManager.bakeLayer(FOX_LAYER)), 0.8F);
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    public ResourceLocation getTextureLocation(EnhancedFox entity) {
        String s = entity.getTexture();
        Colouration colourRGB = entity.getRgb();

        if (s == null || s.isEmpty() || colourRGB == null) {
            return ERROR_TEXTURE_LOCATION;
        }

        s = s + colourRGB.getRGBStrings();

        ResourceLocation resourcelocation = textureCache.getFromCache(s);

        if (resourcelocation == null) {
            TextureGrouping textureGrouping = entity.getTextureGrouping();

            if (textureGrouping == null || !textureGrouping.isPopulated()) {
                return ERROR_TEXTURE_LOCATION;
            }

            try {
                resourcelocation = new ResourceLocation(s);
                Minecraft.getInstance().getTextureManager().register(resourcelocation, new EnhancedLayeredTexturer(ENHANCED_FOX_TEXTURE_LOCATION, textureGrouping, entity.colouration, 64));

                textureCache.putInCache(s, resourcelocation);
            } catch (IllegalStateException e) {
                return ERROR_TEXTURE_LOCATION;
            }
        }

        return resourcelocation;
    }

}
