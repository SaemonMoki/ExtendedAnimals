package mokiyoki.enhancedanimals.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import mokiyoki.enhancedanimals.entity.EnhancedAxolotl;
import mokiyoki.enhancedanimals.entity.util.Colouration;
import mokiyoki.enhancedanimals.model.modeldata.AxolotlModelData;
import mokiyoki.enhancedanimals.renderer.texture.DrawnTexture;
import mokiyoki.enhancedanimals.renderer.texture.EnhancedLayeredTexturer;
import mokiyoki.enhancedanimals.renderer.texture.TextureGrouping;
import mokiyoki.enhancedanimals.renderer.util.LayeredTextureCacher;
import mokiyoki.enhancedanimals.util.Genes;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class ModelEnhancedAxolotlGlowingLayer extends EyesLayer<EnhancedAxolotl, ModelEnhancedAxolotl<EnhancedAxolotl>> {
    private static final LayeredTextureCacher textureCache = new LayeredTextureCacher();
    private static final String ENHANCED_AXOLOTL_TEXTURE_LOCATION = "eanimod:textures/entity/axolotl/";
    private static final ResourceLocation ERROR_TEXTURE_LOCATION = ResourceLocation.parse("eanimod:textures/entity/axolotl/base.png");

    public ModelEnhancedAxolotlGlowingLayer(RenderLayerParent<EnhancedAxolotl, ModelEnhancedAxolotl<EnhancedAxolotl>> layerParent) {
        super(layerParent);
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource multiBufferSource, int packedLight, EnhancedAxolotl axolotl, float p_116987_, float p_116988_, float p_116989_, float p_116990_, float p_116991_, float p_116992_) {
        AxolotlModelData modelData = ((AxolotlModelData)this.getParentModel().getCreateAnimalModelData(axolotl));
        if (modelData != null && modelData.getPhenotype() != null && (modelData.getPhenotype().glowingBody || modelData.getPhenotype().glowingEyes || modelData.getPhenotype().glowingGills)) {
            VertexConsumer vertexconsumer = multiBufferSource.getBuffer(RenderType.eyes(this.getTextureLocation(axolotl)));
            this.getParentModel().renderToBuffer(poseStack, vertexconsumer, packedLight, OverlayTexture.NO_OVERLAY, -1);
        }
    }

    @Override //blank as we override render also
    public RenderType renderType() {
        return null;
    }

    @Override
    public ResourceLocation getTextureLocation(EnhancedAxolotl entity) {
        String s = entity.getTexture();
        Colouration colourRGB = entity.getRgb();
        Genes genes = entity.getGenes();

        if (s == null || s.isEmpty() || colourRGB == null || genes == null) {
            return ERROR_TEXTURE_LOCATION;
        }

        s = "g" + getGlowingPartsKey(genes) + "-" + s + colourRGB.getRGBStrings();

        ResourceLocation resourcelocation = textureCache.getFromCache(s);

        if (resourcelocation == null) {
            TextureGrouping textureGrouping = entity.getTextureGrouping();

            if (textureGrouping == null || !textureGrouping.isPopulated()) {
                return ERROR_TEXTURE_LOCATION;
            }

            try {
                resourcelocation = ResourceLocation.parse(s);
                EnhancedLayeredTexturer layeredTexture = new EnhancedLayeredTexturer(ENHANCED_AXOLOTL_TEXTURE_LOCATION, textureGrouping, entity.colouration, 64, 64);
                Minecraft.getInstance().getTextureManager().register(resourcelocation, layeredTexture);
                DrawnTexture texture = new DrawnTexture("eanimod:textures/entity/axolotl/blank.png", layeredTexture, getGlowingParts(genes));
                Minecraft.getInstance().getTextureManager().register(resourcelocation, texture);
                textureCache.putInCache(s, resourcelocation);
            } catch (IllegalStateException e) {
                return ERROR_TEXTURE_LOCATION;
            }
        }

        return resourcelocation;
    }

    private static boolean glowingBody(Genes genes) {
        return genes.has(10, 3) && !genes.has(10, 2);
    }

    private static boolean glowingEyes(Genes genes) {
        return genes.has(20, 6);
    }

    private static boolean glowingGills(Genes genes) {
        return genes.has(38, 3) && !genes.has(38, 2);
    }

    private static String getGlowingPartsKey(Genes genes) {
        if (glowingBody(genes)) return "b";
        return (glowingEyes(genes) ? "e" : "") + (glowingGills(genes) ? "g" : "");
    }

    private static boolean[][] getGlowingParts(Genes genes) {
        boolean[][] cutout = new boolean[64][64];

        if (glowingBody(genes)) {
            int[] bodyList = new int[]{
                    0, 25, 0, 33,
                    26, 30, 0, 49,
                    36, 51, 0, 12,
                    58, 60, 1, 27
            };
            for (int i = 0, l = bodyList.length; i < l; i+=4) {
                for (int x = bodyList[i], w = bodyList[i+1]; x <= w; x++) {
                    for (int y = bodyList[i+2], h = bodyList[i+3]; y <= h; y++) {
                        cutout[x][y] = true;
                    }
                }
            }
        } else {
            if (glowingEyes(genes)) {
                cutout[12][2] = true;
                cutout[15][5] = true;
                cutout[5][2] = true;
                cutout[2][5] = true;
            }
            if (glowingGills(genes)) {
                int[] gillsList = new int[]{39,48,0,4,36,40,5,12,47,51,5,13};
                for (int i = 0, l = gillsList.length; i < l; i+=4) {
                    for (int x = gillsList[i], w = gillsList[i+1]; x <= w; x++) {
                        for (int y = gillsList[i+2], h = gillsList[i+3]; y <= h; y++) {
                            cutout[x][y] = true;
                        }
                    }
                }
            }
        }

        return cutout;
    }
}
