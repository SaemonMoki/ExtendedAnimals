package mokiyoki.enhancedanimals.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import mokiyoki.enhancedanimals.entity.EnhancedAxolotlEgg;
import mokiyoki.enhancedanimals.model.ModelEnhancedAxolotlEgg;
import mokiyoki.enhancedanimals.util.Reference;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class RenderEnhancedAxolotlEgg extends EntityRenderer<EnhancedAxolotlEgg> {
    private static final ResourceLocation ENHANCED_AXOLOTL_EGGS = new ResourceLocation("eanimod:textures/entities/axolotl_egg/axolotl_eggs.png");
    private final ModelEnhancedAxolotlEgg<EnhancedAxolotlEgg> model;
    public static final ModelLayerLocation AXOLOTL_EGG_LAYER = new ModelLayerLocation(new ResourceLocation(Reference.MODID, "axolotl_egg"), "axolotl_egg_layer");

    public RenderEnhancedAxolotlEgg(EntityRendererProvider.Context context) {
        super(context);
        this.model = new ModelEnhancedAxolotlEgg<>(context.bakeLayer(AXOLOTL_EGG_LAYER), RenderType::entityTranslucent);
    }

    @Override
    public void render(EnhancedAxolotlEgg enhancedAxolotlEgg, float p_114486_, float p_114487_, PoseStack pose, MultiBufferSource p_114489_, int packedLight) {
        this.model.renderToBuffer(pose, p_114489_.getBuffer(RenderType.entityTranslucent(ENHANCED_AXOLOTL_EGGS)), packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        this.model.setupAnim(enhancedAxolotlEgg, 0, 0, 0, 0, 0);
    }

    @Override
    public ResourceLocation getTextureLocation(EnhancedAxolotlEgg p_114482_) {
        return ENHANCED_AXOLOTL_EGGS;
    }
}
