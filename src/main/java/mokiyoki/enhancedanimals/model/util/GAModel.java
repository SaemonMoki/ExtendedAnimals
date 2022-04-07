package mokiyoki.enhancedanimals.model.util;


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

@OnlyIn(Dist.CLIENT)
public abstract class GAModel<E extends Entity> extends EntityModel<E> {

    protected GAModel(boolean p_102023_, float p_102024_, float p_102025_) {
        this(p_102023_, p_102024_, p_102025_, 2.0F, 2.0F, 24.0F);
    }

    protected GAModel(boolean p_102027_, float p_102028_, float p_102029_, float p_102030_, float p_102031_, float p_102032_) {
        this(RenderType::entityCutoutNoCull, p_102027_, p_102028_, p_102029_, p_102030_, p_102031_, p_102032_);
    }

    protected GAModel(Function<ResourceLocation, RenderType> p_102015_, boolean p_102016_, float p_102017_, float p_102018_, float p_102019_, float p_102020_, float p_102021_) {
        super(p_102015_);
    }

    protected GAModel() {
        this(false, 5.0F, 2.0F);
    }

    public void gaRender(WrappedModelPart wrapped, Map<String, List<Float>> mapOfScale, PoseStack poseStack, VertexConsumer vertexConsumer, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        if (wrapped.modelPart.visible) {
            if (!wrapped.modelPart.cubes.isEmpty() || !wrapped.children.isEmpty()) {
                poseStack.pushPose();
                wrapped.modelPart.translateAndRotate(poseStack);

                if (mapOfScale != null) {
                    if (mapOfScale.containsKey(wrapped.boxName)) {
                        List<Float> scaleAmounts = mapOfScale.get(wrapped.boxName);
                        if (scaleAmounts.get(0) != null && scaleAmounts.get(1) != null && scaleAmounts.get(2) != null) {
                            poseStack.scale(scaleAmounts.get(0), scaleAmounts.get(1), scaleAmounts.get(2));
                        }
                        if (scaleAmounts.get(3) != null && scaleAmounts.get(4) != null && scaleAmounts.get(5) != null) {
                            poseStack.translate(scaleAmounts.get(3), scaleAmounts.get(4), scaleAmounts.get(5));
                        }
                    }
                }

                if (wrapped.boxIsRendered) {
                    compile(wrapped.modelPart, poseStack.last(), vertexConsumer, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                }

                for(WrappedModelPart childPart : wrapped.children) {
                    gaRender(childPart, mapOfScale, poseStack, vertexConsumer, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                }

                poseStack.popPose();
            }
        }
    }

    private void compile(ModelPart modelPart, PoseStack.Pose posestack, VertexConsumer vertexConsumer, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        for(ModelPart.Cube modelpart$cube : modelPart.cubes) {
            modelpart$cube.compile(posestack, vertexConsumer, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        }

    }
}
