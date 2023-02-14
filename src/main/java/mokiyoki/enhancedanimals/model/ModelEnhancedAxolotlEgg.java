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

import java.util.function.Function;

public class ModelEnhancedAxolotlEgg<T extends EnhancedAxolotlEgg> extends EntityModel<T> {
    private final ModelPart root;
    private final ModelPart egg;

    public ModelEnhancedAxolotlEgg(ModelPart modelPart, Function<ResourceLocation, RenderType> renderType) {
        super(renderType);
        this.root = modelPart;
        this.egg = modelPart.getChild("egg");
        this.root.children.put("egg", this.egg);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        partdefinition.addOrReplaceChild("egg",
            CubeListBuilder.create()
                    .texOffs(0, 0).addBox(-3.0F, 0.0F, -3.0F, 6, 6, 6)
                    .texOffs(0, 12).addBox(-2.0F, 1.0F, -2.0F, 4, 4, 4)
            , PartPose.ZERO
        );
        return LayerDefinition.create(meshdefinition, 32, 32);
    }

    @Override
    public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        if (entityIn instanceof EnhancedAxolotlEgg) {
            EnhancedAxolotlEgg axolotlEgg = (EnhancedAxolotlEgg) entityIn;
            this.egg.y = 0.5F * ((float) Math.cos(0.03F * axolotlEgg.getAddAnimationTick()));
            this.egg.x = 0.5F * ((float) Math.cos((0.05F * axolotlEgg.getAddAnimationTick()+1)));
            this.egg.z = 0.5F * ((float) Math.cos((0.05F * axolotlEgg.getAddAnimationTick()+2)));
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
