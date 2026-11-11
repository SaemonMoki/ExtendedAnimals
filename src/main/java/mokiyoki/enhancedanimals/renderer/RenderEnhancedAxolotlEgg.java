package mokiyoki.enhancedanimals.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import mokiyoki.enhancedanimals.GeneticAnimals;
import mokiyoki.enhancedanimals.entity.EnhancedAxolotlEgg;
import mokiyoki.enhancedanimals.model.ModelEnhancedAxolotlEgg;
import mokiyoki.enhancedanimals.renderer.texture.EnhancedLayeredTexturer;
import mokiyoki.enhancedanimals.renderer.texture.TextureGrouping;
import mokiyoki.enhancedanimals.renderer.util.LayeredTextureCacher;
import mokiyoki.enhancedanimals.util.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class RenderEnhancedAxolotlEgg extends EntityRenderer<EnhancedAxolotlEgg> {
    private static final LayeredTextureCacher textureCache = new LayeredTextureCacher();
    private static final String ENHANCED_AXOLOTL_EGG_TEXTURE_LOCATION = "eanimod:textures/entity/axolotl_egg/";
    private static final ResourceLocation ERROR_TEXTURE_LOCATION = new ResourceLocation("eanimod:textures/entity/axolotl_egg/shell.png");
    private final ModelEnhancedAxolotlEgg<EnhancedAxolotlEgg> model;
    public static final ModelLayerLocation AXOLOTL_EGG_LAYER = new ModelLayerLocation(new ResourceLocation(GeneticAnimals.MODID, "axolotl_egg"), "axolotl_egg_layer");

    public RenderEnhancedAxolotlEgg(EntityRendererProvider.Context context) {
        super(context);
        this.model = new ModelEnhancedAxolotlEgg<>(context.bakeLayer(AXOLOTL_EGG_LAYER), RenderType::entityTranslucent);
    }

    @Override
    public void render(EnhancedAxolotlEgg enhancedAxolotlEgg, float p_114486_, float p_114487_, PoseStack pose, MultiBufferSource buffer, int packedLight) {
        this.model.renderToBuffer(pose, buffer.getBuffer(RenderType.entityTranslucent(getTextureLocation(enhancedAxolotlEgg))), packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        this.model.setupAnim(enhancedAxolotlEgg, 0, 0, enhancedAxolotlEgg.tickCount, 0, 0);
    }

    @Override
    public ResourceLocation getTextureLocation(EnhancedAxolotlEgg entity) {
        String s = entity.getTexture();

        if (s == null || s.isEmpty()) {
            return ERROR_TEXTURE_LOCATION;
        }

        ResourceLocation resourcelocation = textureCache.getFromCache(s);

        if (resourcelocation == null) {
            TextureGrouping textureGrouping = entity.getTextureGrouping();

            if (textureGrouping == null || !textureGrouping.isPopulated()) {
                return ERROR_TEXTURE_LOCATION;
            }

            try {
                resourcelocation = new ResourceLocation(s);
                EnhancedLayeredTexturer texture = new EnhancedLayeredTexturer(ENHANCED_AXOLOTL_EGG_TEXTURE_LOCATION, textureGrouping, null, 32, 32);
                Minecraft.getInstance().getTextureManager().register(resourcelocation, texture);
                textureCache.putInCache(s, resourcelocation);
            } catch (IllegalStateException e) {
                return ERROR_TEXTURE_LOCATION;
            }
        }

        return resourcelocation;
    }
}
