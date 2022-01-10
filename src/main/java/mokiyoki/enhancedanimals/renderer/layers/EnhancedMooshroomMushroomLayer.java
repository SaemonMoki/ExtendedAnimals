//package mokiyoki.enhancedanimals.renderer.layers;
//
////
//// Source code recreated from a .class file by IntelliJ IDEA
//// (powered by Fernflower decompiler)
////
//
//import com.mojang.blaze3d.vertex.PoseStack;
//import net.minecraft.client.renderer.MultiBufferSource;
//import net.minecraft.client.renderer.entity.RenderLayerParent;
//import net.minecraft.client.renderer.entity.layers.RenderLayer;
//import mokiyoki.enhancedanimals.entity.EnhancedMooshroom;
//import mokiyoki.enhancedanimals.model.ModelEnhancedCow;
//import net.minecraftforge.api.distmarker.Dist;
//import net.minecraftforge.api.distmarker.OnlyIn;
//
//@OnlyIn(Dist.CLIENT)
//public class EnhancedMooshroomMushroomLayer<T extends EnhancedMooshroom> extends RenderLayer<T, ModelEnhancedCow<T>> {
//
//    private float size;
//
//    public EnhancedMooshroomMushroomLayer(RenderLayerParent<T, ModelEnhancedCow<T>> p_i50931_1_) {
//        super(p_i50931_1_);
//    }
//
//
//    public void render(PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn, T entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
//        if (!entitylivingbaseIn.isBaby() && !entitylivingbaseIn.isInvisible()) {
//
////            EnhancedMooshroom enhancedMooshroom = entitylivingbaseIn;
////
////            int[] sharedGenes = (entitylivingbaseIn).getSharedGenes();
////            char[] uuidArry = enhancedMooshroom.getCachedUniqueIdString().toCharArray();
////            this.size = enhancedMooshroom.getSize();
////            float horns = 0.05F;
////            float dwarf;
////
////            if (sharedGenes[13] == 1 || sharedGenes[14] == 1) {
////                //should be polled unless...
////                //african horn genes
////                if (sharedGenes[76] == 1 && sharedGenes[77] == 1) {
////                    //horned
////                } else if (sharedGenes[76] == 1 || sharedGenes[77] == 1) {
////                    //sex determined horned
////                    if (Character.isLetter(uuidArry[0]) || uuidArry[0] - 48 >= 8) {
////                        //horned if male
////                    } else {
////                        //polled if female unless
////                        if (sharedGenes[78] == 1 && sharedGenes[79] == 1) {
////                            //she is scured
////                        } else {
////                            //polled
////                            horns = 0.0F;
////                        }
////                    }
////                } else {
////                    //polled
////                    if (sharedGenes[78] == 1 && sharedGenes[79] == 1) {
////                        //scured
////                    } else if (sharedGenes[78] == 1 || sharedGenes[79] == 1) {
////                        //sex determined scured
////                        if (Character.isLetter(uuidArry[0]) || uuidArry[0] - 48 >= 8) {
////                            //scurred
////                        } else {
////                            //polled
////                            horns = 0.0F;
////                        }
////                    } else {
////                        //polled
////                        horns = 0.0F;
////                    }
////                }
////            } else {
////                //horned
////            }
////
////            if (sharedGenes[26] == 1 || sharedGenes[27] == 1){
////                //dwarf
////                dwarf = 0.2F;
////            } else {
////                dwarf = 0.0F;
////            }
//
////            BlockState blockstate = entityIn.getMooshroomType().getRenderState();
////            this.bindTexture(AtlasTexture.LOCATION_BLOCKS_TEXTURE);
////            GlStateManager.enableCull();
////            GlStateManager.cullFace(GlStateManager.CullFace.FRONT);
////            GlStateManager.pushMatrix();
////            GlStateManager.scalef(1.0F, -1.0F, 1.0F);
////            GlStateManager.translatef(0.2F, size - 0.7F - dwarf, 0.5F);
////            GlStateManager.rotatef(42.0F, 0.0F, 1.0F, 0.0F);
////            BlockRendererDispatcher blockrendererdispatcher = Minecraft.getInstance().getBlockRendererDispatcher();
////            GlStateManager.pushMatrix();
////            GlStateManager.translatef(-0.5F, -0.5F, 0.5F);
////            blockrendererdispatcher.renderBlockBrightness(blockstate, 1.0F);
////            GlStateManager.popMatrix();
////            GlStateManager.pushMatrix();
////            GlStateManager.translatef(0.1F, 0.0F, -0.6F);
////            GlStateManager.rotatef(42.0F, 0.0F, 1.0F, 0.0F);
////            GlStateManager.translatef(-0.5F, -0.5F, 0.5F);
////            blockrendererdispatcher.renderBlockBrightness(blockstate, 1.0F);
////            GlStateManager.popMatrix();
////            GlStateManager.popMatrix();
////            GlStateManager.pushMatrix();
////            this.getEntityModel().getHead().postRender(0.0625F);
////            GlStateManager.scalef(1.0F, -1.0F, 1.0F);
////            GlStateManager.translatef(0.0F, 0.7F, 0.0F);
////            GlStateManager.rotatef(12.0F, 0.0F, 1.0F, 0.0F);
////            GlStateManager.translatef(-0.5F, size - 1.75F + horns - dwarf, 0.45F - ((size-0.6F) * 1.2777F));
////            blockrendererdispatcher.renderBlockBrightness(blockstate, 1.0F);
////            GlStateManager.popMatrix();
////            GlStateManager.cullFace(GlStateManager.CullFace.BACK);
////            GlStateManager.disableCull();
//        }
//    }
//
//    public boolean shouldCombineTextures() {
//        return true;
//    }
//}
