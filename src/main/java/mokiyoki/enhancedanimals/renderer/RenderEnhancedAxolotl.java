package mokiyoki.enhancedanimals.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import mokiyoki.enhancedanimals.entity.EnhancedAxolotl;
import mokiyoki.enhancedanimals.entity.util.Colouration;
import mokiyoki.enhancedanimals.model.ModelEnhancedAxolotl;
import mokiyoki.enhancedanimals.model.ModelEnhancedAxolotlGlowingLayer;
import mokiyoki.enhancedanimals.renderer.texture.EnhancedLayeredTexture;
import mokiyoki.enhancedanimals.renderer.util.LayeredTextureCacher;
import mokiyoki.enhancedanimals.util.Genes;
import mokiyoki.enhancedanimals.util.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.EnderEyesLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;

@OnlyIn(Dist.CLIENT)
public class RenderEnhancedAxolotl<T extends EnhancedAxolotl> extends MobRenderer<EnhancedAxolotl, ModelEnhancedAxolotl<EnhancedAxolotl>> {
    private static final LayeredTextureCacher textureCache = new LayeredTextureCacher();
    private static final String ENHANCED_AXOLOTL_TEXTURE_LOCATION = "eanimod:textures/entities/axolotl/";
    private static final ResourceLocation ERROR_TEXTURE_LOCATION = new ResourceLocation("eanimod:textures/entities/axolotl/axolotlbase.png");
    public static final ModelLayerLocation AXOLOTL_LAYER = new ModelLayerLocation(new ResourceLocation(Reference.MODID, "axolotl"), "axolotl_layer");

    public RenderEnhancedAxolotl(EntityRendererProvider.Context renderManager) {
        super(renderManager, new ModelEnhancedAxolotl<>(renderManager.bakeLayer(AXOLOTL_LAYER), RenderType::entityCutoutNoCull), 0.5F);
//        addLayer(new ModelEnhancedAxolotlGlowingLayer(this));
    }

//    @Nullable
//    @Override
//    protected RenderType getRenderType(EnhancedAxolotl axolotl, boolean p_115323_, boolean p_115324_, boolean p_115325_) {
//        RenderType type = super.getRenderType(axolotl, p_115323_, p_115324_, p_115325_);
//        return RenderType.eyes(getTextureLocation(axolotl));
//    }

//    @Override
//    public void render(EnhancedAxolotl axolotl, float p_115456_, float p_115457_, PoseStack poseStack, MultiBufferSource multiBufferSource, int p_115460_) {
//        VertexConsumer vertexconsumer = multiBufferSource.getBuffer(RenderType.eyes(getTextureLocation(axolotl)));
//        this.getModel().renderToBuffer(poseStack, vertexconsumer, 15728640, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
//        super.render(axolotl, p_115456_, p_115457_, poseStack, multiBufferSource, p_115460_);
//    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    public ResourceLocation getTextureLocation(EnhancedAxolotl entity) {
        String s = entity.getTexture();
        Colouration colourRGB = entity.getRgb();

        if (s == null || s.isEmpty() || colourRGB == null) {
            return ERROR_TEXTURE_LOCATION;
        }

        s = s + colourRGB.getRGBStrings();

        ResourceLocation resourcelocation = textureCache.getFromCache(s);

        if (resourcelocation == null || entity.getBucketImage().isEmpty()) {
            String[] textures = entity.getVariantTexturePaths();

            if (textures == null || textures.length == 0) {
                return ERROR_TEXTURE_LOCATION;
            }

            try {
                resourcelocation = new ResourceLocation(s);
                EnhancedLayeredTexture texture = new EnhancedLayeredTexture(ENHANCED_AXOLOTL_TEXTURE_LOCATION, textures, null, entity.colouration);
                Minecraft.getInstance().getTextureManager().register(resourcelocation, texture);
                textureCache.putInCache(s, resourcelocation);
                entity.setBucketImageData(texture);
            } catch (IllegalStateException e) {
                return ERROR_TEXTURE_LOCATION;
            }
        }

        return resourcelocation;
    }
}
