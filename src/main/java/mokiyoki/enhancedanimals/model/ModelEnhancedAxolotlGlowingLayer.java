package mokiyoki.enhancedanimals.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import mokiyoki.enhancedanimals.entity.EnhancedAxolotl;
import mokiyoki.enhancedanimals.entity.util.Colouration;
import mokiyoki.enhancedanimals.renderer.texture.DrawnTexture;
import mokiyoki.enhancedanimals.renderer.texture.EnhancedLayeredTexture;
import mokiyoki.enhancedanimals.renderer.util.LayeredTextureCacher;
import mokiyoki.enhancedanimals.util.Genes;
import mokiyoki.enhancedanimals.util.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.CatModel;
import net.minecraft.client.model.EndermanModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.Cat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

public class ModelEnhancedAxolotlGlowingLayer extends EyesLayer<EnhancedAxolotl, ModelEnhancedAxolotl<EnhancedAxolotl>> {
    private static final LayeredTextureCacher textureCache = new LayeredTextureCacher();
    private static final ResourceLocation ERROR_TEXTURE_LOCATION = new ResourceLocation("eanimod:textures/entities/axolotl/axolotlbase.png");

    public ModelEnhancedAxolotlGlowingLayer(RenderLayerParent<EnhancedAxolotl, ModelEnhancedAxolotl<EnhancedAxolotl>> layerParent) {
        super(layerParent);
    }

    @Override
    public void render(PoseStack p_116983_, MultiBufferSource p_116984_, int p_116985_, EnhancedAxolotl axolotl, float p_116987_, float p_116988_, float p_116989_, float p_116990_, float p_116991_, float p_116992_) {
        VertexConsumer vertexconsumer = p_116984_.getBuffer(RenderType.eyes(this.getTextureLocation(axolotl)));
        this.getParentModel().renderToBuffer(p_116983_, vertexconsumer, 15728640, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
    }

    @Override //blank as we override render also
    public RenderType renderType() {
        return null;
    }

    @Override
    public ResourceLocation getTextureLocation(EnhancedAxolotl entity) {
        String s = entity.getTexture();
        Colouration colourRGB = entity.getRgb();
        Genes genes = entity.getSharedGenes();

        if (s == null || s.isEmpty() || colourRGB == null || genes == null) {
            return ERROR_TEXTURE_LOCATION;
        }

        s = "g" + s + colourRGB.getRGBStrings();

        ResourceLocation resourcelocation = textureCache.getFromCache(s);

        if (resourcelocation == null) {
            String[] textures = entity.getVariantTexturePaths();

            if (textures == null || textures.length == 0) {
                return ERROR_TEXTURE_LOCATION;
            }

            try {
                resourcelocation = new ResourceLocation(s);
                DrawnTexture texture = new DrawnTexture(super.getTextureLocation(entity), getGlowingParts(genes));
                Minecraft.getInstance().getTextureManager().register(resourcelocation, texture);
                textureCache.putInCache(s, resourcelocation);
            } catch (IllegalStateException e) {
                return ERROR_TEXTURE_LOCATION;
            }
        }

        return resourcelocation;
    }

    private static boolean[][] getGlowingParts(Genes genes) {
        boolean[][] cutout = new boolean[64][64];

        if (genes.has(10, 3) && !genes.has(10, 2)) {
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
        }

        return cutout;

//        List<int[]> image = new ArrayList();
//        List<Integer> imageY = new ArrayList();
//        if (genes.has(10, 3) && !genes.has(10, 2)) {
//            int[] bodyList = new int[]{
//                    0, 25, 0, 33,
//                    26, 30, 0, 49,
//                    36, 51, 0, 12,
//                    58, 60, 1, 27
//            };
//            for (int i = 0, l = bodyList.length; i < l; i+=4) {
//                for (int x = bodyList[i], w = bodyList[i+1]; x <= w; x++) {
//                    imageY.add(x);
//                    for (int y = bodyList[i+2], h = bodyList[i+3]; y <= h; y++) {
//                        imageY.add(y);
//                    }
//                    int [] innerArray = new int[imageY.size()];
//                    for (int j = 0, k=innerArray.length; j < k; j++) innerArray[j] = imageY.get(j);
//                    image.add(innerArray);
//                    imageY.clear();
//                }
//            }
//            int [][] imageArray = new int[image.size()][];
//            for (int j = 0, k=imageArray.length; j < k; j++) imageArray[j] = image.get(j);
//            return imageArray;
//        } else {
//            return new int[1][1];
//        }
    }

}
