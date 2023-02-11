package mokiyoki.enhancedanimals.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import mokiyoki.enhancedanimals.entity.EnhancedTurtle;
import mokiyoki.enhancedanimals.model.modeldata.AnimalModelData;
import mokiyoki.enhancedanimals.model.modeldata.Phenotype;
import mokiyoki.enhancedanimals.model.modeldata.TurtleModelData;
import mokiyoki.enhancedanimals.model.modeldata.TurtlePhenotype;
import mokiyoki.enhancedanimals.model.util.ModelHelper;
import mokiyoki.enhancedanimals.model.util.WrappedModelPart;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@OnlyIn(Dist.CLIENT)
public class ModelEnhancedTurtle<T extends EnhancedTurtle> extends EnhancedAnimalModel<T> {
    protected WrappedModelPart theTurtle;

    protected WrappedModelPart theHead;
    protected WrappedModelPart theBody;
    protected WrappedModelPart theLegFrontLeft;
    protected WrappedModelPart theLegFrontRight;
    protected WrappedModelPart theLegBackLeft;
    protected WrappedModelPart theLegBackRight;
    protected WrappedModelPart theTail;

    protected WrappedModelPart head;
    protected WrappedModelPart body;
    protected WrappedModelPart pregnantBody;

    private WrappedModelPart legFrontLeft;
    private WrappedModelPart legFrontRight;
    private WrappedModelPart legBackLeft;
    private WrappedModelPart legBackRight;

    private TurtleModelData turtleModelData;

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition base = meshdefinition.getRoot().addOrReplaceChild("base", CubeListBuilder.create(), PartPose.ZERO);
        PartDefinition bTurtle = base.addOrReplaceChild("bTurtle", CubeListBuilder.create(), PartPose.offset(0.0F, 21.0F, -10.0F));
        PartDefinition bBody = bTurtle.addOrReplaceChild("bBody", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, Mth.HALF_PI, 0.0F, 0.0F));
        PartDefinition bHead = bBody.addOrReplaceChild("bHead", CubeListBuilder.create(), PartPose.offset(0.0F, -3.0F, 2.0F));
        PartDefinition bLegFrontLeft = bTurtle.addOrReplaceChild("bLegFL", CubeListBuilder.create(), PartPose.offset(5.0F, 0.0F, 3.0F));
        PartDefinition bLegFrontRight = bTurtle.addOrReplaceChild("bLegFR", CubeListBuilder.create(), PartPose.offset(-5.0F, 0.0F, 3.0F));
        PartDefinition bLegBackLeft = bTurtle.addOrReplaceChild("bLegBL", CubeListBuilder.create(), PartPose.offset(3.5F, 0.0F, 18.0F));
        PartDefinition bLegBackRight = bTurtle.addOrReplaceChild("bLegBR", CubeListBuilder.create(), PartPose.offset(-3.5F, 0.0F, 18.0F));

        bHead.addOrReplaceChild("eyes", CubeListBuilder.create()
                        .texOffs(69, 15)
                        .addBox(2.5F, 0.0F, 0.0F, 1, 1, 1, new CubeDeformation(0.01F))
                        .texOffs(0, 40)
                        .addBox(-3.5F, 0.0F, 0.0F, 1, 1, 1, new CubeDeformation(0.01F)),
                PartPose.ZERO
        );

        bHead.addOrReplaceChild("head", CubeListBuilder.create()
                        .texOffs(3, 0)
                        .addBox(-3.0F, -1.0F, -3.0F, 6, 5, 6),
                PartPose.ZERO
        );

        bBody.addOrReplaceChild("body", CubeListBuilder.create()
                        .texOffs(7, 37)
                        .addBox(-9.5F, 0.0F, 0.0F, 19, 20, 6)
                        .texOffs(31, 1)
                        .addBox(-5.5F, 0.0F, -3.0F, 11, 18, 3),
                PartPose.offset(0.0F, 0.1F, 0.0F)
        );
        bBody.addOrReplaceChild("bodyP", CubeListBuilder.create()
                        .texOffs(7, 37)
                        .addBox(-9.5F, 0.0F, 0.0F, 19, 20, 6)
                        .texOffs(31, 1)
                        .addBox(-5.5F, 0.0F, -1.0F, 11, 18, 3)
                        .texOffs(70, 33)
                        .addBox(-4.5F, 0.0F, -4.0F, 9, 18, 1),
                PartPose.offset(0.0F, 0.1F, 0.0F)
        );

        bLegFrontLeft.addOrReplaceChild("legFL", CubeListBuilder.create()
                        .texOffs(27, 24)
                        .addBox(0.0F, 0.0F, -2.0F, 13, 1, 5),
                PartPose.ZERO
        );
        bLegFrontRight.addOrReplaceChild("legFR", CubeListBuilder.create()
                        .texOffs(27, 30)
                        .addBox(-13.0F, 0.0F, -2.0F, 13, 1, 5),
                PartPose.ZERO
        );
        bLegBackLeft.addOrReplaceChild("legBL", CubeListBuilder.create()
                        .texOffs(1, 12)
                        .addBox(-2.0F, 0.0F, 0.0F, 4, 1, 10),
                PartPose.ZERO
        );
        bLegBackRight.addOrReplaceChild("legBR", CubeListBuilder.create()
                        .texOffs(1, 23)
                        .addBox(-2.0F, 0.0F, 0.0F, 4, 1, 10),
                PartPose.ZERO
        );

        base.addOrReplaceChild("collar", CubeListBuilder.create()
                        .texOffs(59, 54)
                        .addBox(-3.5F, -1.0F, -0.5F, 7, 2, 7)
                        .texOffs(82, 14)
                        .addBox(0.0F, -1.5F, 5.5F, 0,  3, 3)
                        .texOffs(59, 0)
                        .addBox(-1.5F, -1.5F, 7.0F, 3, 3, 3, new CubeDeformation(-0.5F)),
                PartPose.offsetAndRotation(0.0F, -1.5F, 2.0F, -Mth.HALF_PI, 0.0F, 0.0F)
        );
        
        return LayerDefinition.create(meshdefinition, 128, 64);
    }

    public ModelEnhancedTurtle(ModelPart modelPart) {
        super(modelPart);
        ModelPart base = modelPart.getChild("base");
        ModelPart bTurtle = base.getChild("bTurtle");
        ModelPart bBody = bTurtle.getChild("bBody");
        ModelPart bHead = bBody.getChild("bHead");
        ModelPart bLegFL = bTurtle.getChild("bLegFL");
        ModelPart bLegFR = bTurtle.getChild("bLegFR");
        ModelPart bLegBL = bTurtle.getChild("bLegBL");
        ModelPart bLegBR = bTurtle.getChild("bLegBR");

        this.theTurtle = new WrappedModelPart(bTurtle, "bTurtle");
        this.theBody = new WrappedModelPart(bBody, "bBody");
        this.theHead = new WrappedModelPart(bHead, "bHead");
        this.theLegFrontLeft = new WrappedModelPart(bLegFL, "bLegFL");
        this.theLegFrontRight = new WrappedModelPart(bLegFR, "bLegFR");
        this.theLegBackLeft = new WrappedModelPart(bLegBL, "bLegBL");
        this.theLegBackRight = new WrappedModelPart(bLegBR, "bLegBR");

        this.eyes = new WrappedModelPart("eyes", bHead);

        this.head = new WrappedModelPart("head", bHead);

        this.body = new WrappedModelPart("body", bBody);
        this.pregnantBody = new WrappedModelPart("bodyP", bBody);

        this.legFrontLeft = new WrappedModelPart("legFL", bLegFL);
        this.legFrontRight = new WrappedModelPart("legFR", bLegFR);
        this.legBackLeft = new WrappedModelPart("legBL", bLegBL);
        this.legBackRight = new WrappedModelPart("legBR", bLegBR);

        this.collar = new WrappedModelPart("collar", base);

        this.theTurtle.addChild(this.theBody);
        this.theBody.addChild(this.theHead);
        this.theTurtle.addChild(this.theLegFrontLeft);
        this.theTurtle.addChild(this.theLegFrontRight);
        this.theTurtle.addChild(this.theLegBackLeft);
        this.theTurtle.addChild(this.theLegBackRight);

        this.theHead.addChild(this.head);
        this.theHead.addChild(this.eyes);

        this.theBody.addChild(this.body);
        this.theBody.addChild(this.pregnantBody);

        this.theLegFrontLeft.addChild(this.legFrontLeft);

        this.theLegFrontRight.addChild(this.legFrontRight);

        this.theLegBackLeft.addChild(this.legBackLeft);

        this.theLegBackRight.addChild(this.legBackRight);

        this.theHead.addChild(this.collar);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        if (this.turtleModelData!=null && this.turtleModelData.getPhenotype() != null) {
            Map<String, List<Float>> mapOfScale = new HashMap<>();

            super.renderToBuffer(this.turtleModelData, poseStack, vertexConsumer, packedLightIn, packedOverlayIn, red, green, blue, alpha);

            if (turtleModelData.hasEggs) {
                this.pregnantBody.show();
                this.body.hide();
            } else {
                this.pregnantBody.hide();
                this.body.show();
            }

            float size = ((1.0F + (turtleModelData.growthAmount * 11.0F))/12.0F) * turtleModelData.size;

            if (turtleModelData.growthAmount < 1.0F) {
                float bh = (1.0F-turtleModelData.growthAmount)*0.5F;
                List<Float> babyHead = ModelHelper.createScalings(1.5F-(turtleModelData.growthAmount*0.5F),0.0F, bh*-0.1F, bh*-0.08F);
                mapOfScale.put("bHead", babyHead);
            }

            poseStack.pushPose();
            poseStack.scale(size, size, size);
            poseStack.translate(0.0F, -1.5F + 1.5F/(size), 0.0F);

            gaRender(this.theTurtle, mapOfScale, poseStack, vertexConsumer, packedLightIn, packedOverlayIn, red, green, blue, alpha);

            poseStack.popPose();
        }
    }

    protected Map<String, Vector3f> saveAnimationValues(TurtlePhenotype animal) {
        Map<String, Vector3f> map = new HashMap<>();
        return map;
    }

    private void setupInitialAnimationValues(T entityIn, TurtleModelData modelData, TurtlePhenotype turtle) {

    }

    @Override
    public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.turtleModelData = getCreateTurtleModelData(entityIn);

        if (this.turtleModelData != null) {
            this.theHead.setXRot((headPitch * (Mth.PI / 180F))-Mth.HALF_PI);
            this.theHead.setZRot(-netHeadYaw * (Mth.PI / 180F));

            if (!entityIn.isInWater() && entityIn.isOnGround()) {
                float f = entityIn.isDigging() ? 4.0F : 1.0F;
                float f1 = entityIn.isDigging() ? 2.0F : 1.0F;
                float f2 = 5.0F;
                this.theLegFrontLeft.setYRot(Mth.cos(f * limbSwing * 5.0F) * 8.0F * limbSwingAmount * f1);
                this.theLegFrontLeft.setZRot(0.0F);
                this.theLegFrontRight.setYRot(Mth.cos(f * limbSwing * 5.0F + (float)Math.PI) * 8.0F * limbSwingAmount * f1);
                this.theLegFrontRight.setZRot(0.0F);
                this.theLegBackLeft.setXRot(0.0F);
                this.theLegBackLeft.setYRot(Mth.cos(limbSwing * 5.0F) * 3.0F * limbSwingAmount);
                this.theLegBackRight.setXRot(0.0F);
                this.theLegBackRight.setYRot(Mth.cos(limbSwing * 5.0F + (float)Math.PI) * 3.0F * limbSwingAmount);
            } else {
                this.theLegFrontLeft.setYRot(0.0F);
                this.theLegFrontLeft.setZRot(Mth.cos(limbSwing * 0.6662F * 0.6F) * 0.5F * limbSwingAmount);
                this.theLegFrontRight.setYRot(0.0F);
                this.theLegFrontRight.setZRot(Mth.cos(limbSwing * 0.6662F * 0.6F + (float)Math.PI) * 0.5F * limbSwingAmount);
                this.theLegBackLeft.setXRot(Mth.cos(limbSwing * 0.6662F * 0.6F + (float)Math.PI) * 0.5F * limbSwingAmount);
                this.theLegBackLeft.setYRot(0.0F);
                this.theLegBackRight.setXRot(Mth.cos(limbSwing * 0.6662F * 0.6F) * 0.5F * limbSwingAmount);
                this.theLegBackRight.setYRot(0.0F);
            }
        }

    }

    private TurtleModelData getCreateTurtleModelData(T enhancedTurtle) {
        return (TurtleModelData) getCreateAnimalModelData(enhancedTurtle);
    }

    @Override
    protected void setInitialModelData(T enhancedTurtle) {
        TurtleModelData turtleModelData = new TurtleModelData();
        setBaseInitialModelData(turtleModelData, enhancedTurtle);
    }

    @Override
    protected void additionalModelDataInfo(AnimalModelData animalModelData, T enhancedAnimal) {
        ((TurtleModelData)animalModelData).hasEggs = enhancedAnimal.hasEgg();
    }

    @Override
    protected void additionalUpdateModelDataInfo(AnimalModelData animalModelData, T enhancedAnimal) {
        ((TurtleModelData)animalModelData).hasEggs = enhancedAnimal.hasEgg();
    }

    @Override
    protected Phenotype createPhenotype(T enhancedAnimal) {
        return new TurtlePhenotype(enhancedAnimal.getSharedGenes().getAutosomalGenes());
    }
}
