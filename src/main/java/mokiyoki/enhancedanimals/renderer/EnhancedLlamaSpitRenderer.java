//package mokiyoki.enhancedanimals.renderer;
//
//import com.mojang.blaze3d.vertex.PoseStack;
//import com.mojang.blaze3d.vertex.VertexConsumer;
//import mokiyoki.enhancedanimals.entity.EnhancedEntityLlamaSpit;
//import net.minecraft.client.model.geom.ModelLayers;
//import net.minecraft.client.renderer.MultiBufferSource;
//import net.minecraft.client.renderer.entity.EntityRenderer;
//import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
//import net.minecraft.client.model.LlamaSpitModel;
//import net.minecraft.client.renderer.entity.EntityRendererProvider;
//import net.minecraft.client.renderer.texture.OverlayTexture;
//import net.minecraft.resources.ResourceLocation;
//import net.minecraft.util.Mth;
//import com.mojang.math.Vector3f;
//
//public class EnhancedLlamaSpitRenderer extends EntityRenderer<EnhancedEntityLlamaSpit> {
//    private static final ResourceLocation LLAMA_SPIT_TEXTURE = new ResourceLocation("textures/entity/llama/spit.png");
//    private final LlamaSpitModel<EnhancedEntityLlamaSpit> model;
//
//    public EnhancedLlamaSpitRenderer(EntityRendererProvider.Context renderManager) {
//        super(renderManager);
//        this.model = new LlamaSpitModel<>(renderManager.bakeLayer(ModelLayers.LLAMA_SPIT));
//    }
//
//    public void render(EnhancedEntityLlamaSpit entityIn, float entityYaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
//        matrixStackIn.pushPose();
//        matrixStackIn.translate(0.0D, (double)0.15F, 0.0D);
//        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(Mth.lerp(partialTicks, entityIn.yRotO, entityIn.getYRot()) - 90.0F));
//        matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(Mth.lerp(partialTicks, entityIn.xRotO, entityIn.getXRot())));
//        this.model.setupAnim(entityIn, partialTicks, 0.0F, -0.1F, 0.0F, 0.0F);
//        VertexConsumer ivertexbuilder = bufferIn.getBuffer(this.model.renderType(LLAMA_SPIT_TEXTURE));
//        this.model.renderToBuffer(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
//        matrixStackIn.popPose();
//        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
//    }
//
//    /**
//     * Returns the location of an entity's texture.
//     */
//    public ResourceLocation getTextureLocation(EnhancedEntityLlamaSpit entity) {
//        return LLAMA_SPIT_TEXTURE;
//    }
//}
