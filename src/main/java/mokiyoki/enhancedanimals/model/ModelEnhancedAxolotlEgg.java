package mokiyoki.enhancedanimals.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import mokiyoki.enhancedanimals.entity.EnhancedAxolotlEgg;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;

public class ModelEnhancedAxolotlEgg<T extends EnhancedAxolotlEgg> extends EntityModel<T> {
    private final ModelPart root;
    private final ModelPart egg;
    private final ModelPart embryo;

    public ModelEnhancedAxolotlEgg(ModelPart modelPart, Function<ResourceLocation, RenderType> renderType) {
        super(renderType);
        this.root = modelPart;
        this.egg = modelPart.getChild("egg");
        this.embryo = modelPart.getChild("embryo");
        this.root.children.put("egg", this.egg);
        this.egg.children.put("embryo", this.embryo);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        partdefinition.addOrReplaceChild("egg",
            CubeListBuilder.create()
                    .texOffs(0, 0).addBox(-3.0F, 0.0F, -3.0F, 6, 6, 6)
            , PartPose.ZERO
        );
        partdefinition.addOrReplaceChild("embryo",
                CubeListBuilder.create()
                        .texOffs(0, 12).addBox(-2.0F, -2.0F, -2.0F, 4, 4, 4)
                , PartPose.offset(0.0F, 3.0F, 0.0F)
        );
        return LayerDefinition.create(meshdefinition, 32, 32);
    }

    @Override
    public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        EnhancedAxolotlEgg egg = entityIn;
        this.egg.y = 0.5F * ((float) Math.cos(0.03F * egg.getAddAnimationTick()));
        this.egg.x = 0.5F * ((float) Math.cos((0.05F * egg.getAddAnimationTick()+1)));
        this.egg.z = 0.5F * ((float) Math.cos((0.05F * egg.getAddAnimationTick()+2)));

        if (egg.getWiggleAnimationTick() > 1) {
            this.embryo.xRot = 0.3F * Mth.sin(ageInTicks*0.3F);
            this.embryo.yRot = 0.2F * Mth.sin(ageInTicks*0.25F);
            this.embryo.zRot = 0.3F * Mth.sin(ageInTicks*0.2F);
        } else {
            this.embryo.xRot = Mth.lerp(this.embryo.xRot, 0.0F, 0.001F);
            this.embryo.yRot = Mth.lerp(this.embryo.yRot, 0.0F, 0.001F);
            this.embryo.zRot = Mth.lerp(this.embryo.zRot, 0.0F, 0.001F);
            if (Mth.abs(this.embryo.xRot) < 0.001F && Mth.abs(this.embryo.yRot) < 0.001F && Mth.abs(this.embryo.zRot) < 0.001F) {
                this.embryo.setRotation(0.0F, 0.0F, 0.0F);
                egg.setWiggleTime(0);
            }
        }
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        poseStack.pushPose();
        poseStack.translate(0.0D, 0.0F, 0.0D);
        poseStack.scale(1.0F, 1.0F, 1.0F);
        this.egg.render(poseStack, vertexConsumer, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        poseStack.popPose();
    }
}
