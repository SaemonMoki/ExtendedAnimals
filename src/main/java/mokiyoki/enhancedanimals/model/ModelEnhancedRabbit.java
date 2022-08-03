package mokiyoki.enhancedanimals.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import mokiyoki.enhancedanimals.entity.EnhancedRabbit;
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

import java.util.Map;

@OnlyIn(Dist.CLIENT)
public class ModelEnhancedRabbit<T extends EnhancedRabbit> extends EnhancedAnimalModel<T> {
    protected WrappedModelPart theRabbit;

    protected WrappedModelPart theHead;
    protected WrappedModelPart theNose;
    protected WrappedModelPart theEarLeft;
    protected WrappedModelPart theEarRight;
    protected WrappedModelPart theNeck;
    protected WrappedModelPart theBody;
    protected WrappedModelPart theButt;
    protected WrappedModelPart theLegFrontLeft;
    protected WrappedModelPart theLegFrontRight;
    protected WrappedModelPart theLegBackLeft;
    protected WrappedModelPart theTibiaBackLeft;
    protected WrappedModelPart theFootBackLeft;
    protected WrappedModelPart theLegBackRight;
    protected WrappedModelPart theTibiaBackRight;
    protected WrappedModelPart theFootBackRight;
    protected WrappedModelPart theTail;

    protected WrappedModelPart headLeft;
    protected WrappedModelPart headRight;
    protected WrappedModelPart muzzle;
    protected WrappedModelPart nose;
    protected WrappedModelPart jaw;

    protected WrappedModelPart earLeft;
    protected WrappedModelPart earTopLeft;

    protected WrappedModelPart earRight;
    protected WrappedModelPart earTopRight;

    protected WrappedModelPart body;
    protected WrappedModelPart butt;

    private WrappedModelPart legFrontLeft;
    private WrappedModelPart legFrontRight;
    private WrappedModelPart legBackLeft;
    private WrappedModelPart tibiaBackLeft;
    private WrappedModelPart footBackLeft;
    private WrappedModelPart legBackRight;
    private WrappedModelPart tibiaBackRight;
    private WrappedModelPart footBackRight;

    private WrappedModelPart tail;

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition base = meshdefinition.getRoot().addOrReplaceChild("base", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition bRabbit = base.addOrReplaceChild("bRabbit", CubeListBuilder.create(), PartPose.offset(0.0F, 16.5F, 0.0F));
        PartDefinition bBody = bRabbit.addOrReplaceChild("bBody", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition bButt = bRabbit.addOrReplaceChild("bButt", CubeListBuilder.create(), PartPose.offset(0.0F, -1.0F, 7.0F));
        PartDefinition bNeck = bBody.addOrReplaceChild("bNeck", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition bHead = bNeck.addOrReplaceChild("bHead", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, -6.0F));
        PartDefinition bNose = bHead.addOrReplaceChild("bNose", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, -2.0F));
        PartDefinition bEarLeft = bHead.addOrReplaceChild("bEarL", CubeListBuilder.create(), PartPose.offset(4.0F, 0.0F, 2.5F));
        PartDefinition bEarRight = bHead.addOrReplaceChild("bEarR", CubeListBuilder.create(), PartPose.offset(-4.0F, 0.0F, 2.5F));
        PartDefinition bLegFrontLeft = bRabbit.addOrReplaceChild("bLegFL", CubeListBuilder.create(), PartPose.offsetAndRotation(-5.1F, 5.5F, 0.0F, -Mth.HALF_PI, 0.0F, 0.0F));
        PartDefinition bLegFrontRight = bRabbit.addOrReplaceChild("bLegFR", CubeListBuilder.create(), PartPose.offsetAndRotation(2.1F, 5.5F, 0.0F, -Mth.HALF_PI, 0.0F, 0.0F));
        PartDefinition bLegBackLeft = bRabbit.addOrReplaceChild("bLegBL", CubeListBuilder.create(), PartPose.offset(1.5F, 1.0F, 2.5F));
        PartDefinition bLegBackRight = bRabbit.addOrReplaceChild("bLegBR", CubeListBuilder.create(), PartPose.offset(-4.5F, 1.0F, 2.5F));
        PartDefinition bTibiaBackLeft = bRabbit.addOrReplaceChild("bTibiaBL", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 6.5F, 0.0F, Mth.HALF_PI * 0.5F, 0.0F, 0.0F));
        PartDefinition bTibiaBackRight = bRabbit.addOrReplaceChild("bTibiaBR", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 6.5F, 0.0F, Mth.HALF_PI * 0.5F, 0.0F, 0.0F));
        PartDefinition bFootBackLeft = bRabbit.addOrReplaceChild("bFootBL", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 5.0F, -Mth.HALF_PI * 0.5F, 0.0F, 0.0F));
        PartDefinition bFootBackRight = bRabbit.addOrReplaceChild("bFootBR", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 5.0F, -Mth.HALF_PI * 0.5F, 0.0F, 0.0F));
        PartDefinition bTail = bBody.addOrReplaceChild("bTail", CubeListBuilder.create(), PartPose.offset(0.0F, 6.0F, 8.5F));

        bHead.addOrReplaceChild("eyes", CubeListBuilder.create()
                        .texOffs(0, 16)
                        .addBox(2.0F, 0.0F, 0.0F, 1, 2, 2, new CubeDeformation(0.01F))
                        .texOffs(0, 20)
                        .addBox(-3.0F, 0.0F, 0.0F, 1, 2, 2, new CubeDeformation(0.01F)),
                PartPose.offset(0.0F, 2.0F, 1.0F)
        );

        bHead.addOrReplaceChild("headL", CubeListBuilder.create()
                        .texOffs(0, 24)
                        .addBox(0.0F, 0.0F, 0.0F, 3, 6, 6),
                PartPose.ZERO
        );
        bHead.addOrReplaceChild("headR", CubeListBuilder.create()
                        .texOffs(18, 24)
                        .addBox(-3.0F, 0F, 0.0F, 3, 6, 6),
                PartPose.ZERO
        );
        bNose.addOrReplaceChild("muzzle", CubeListBuilder.create()
                        .texOffs(0, 8)
                        .addBox(-2.0F, 1.5F, 0.0F, 4, 4, 4),
                PartPose.ZERO
        );
        bNose.addOrReplaceChild("nose", CubeListBuilder.create()
                        .texOffs(0, 8)
                        .addBox(-0.5F, 0.0F, 0.0F, 1, 1, 1),
                PartPose.offset(0.0F, 1.6F, -0.1F)
        );

        bEarLeft.addOrReplaceChild("earL", CubeListBuilder.create()
                        .texOffs(10, 0)
                        .addBox(-3.0F, -7.0F, -0.5F, 4, 7, 1),
                PartPose.ZERO
        );
//        bEarLeft.addOrReplaceChild("earTL", CubeListBuilder.create()
//                        .texOffs(46, 0)
//                        .addBox(-1.0F, -2.0F, 0.0F, 4, 7, 1),
//                PartPose.ZERO
//        );

        bEarRight.addOrReplaceChild("earR", CubeListBuilder.create()
                        .texOffs(0, 0)
                        .addBox(-1.0F, -7.0F, -0.5F, 4, 7, 1),
                PartPose.ZERO
        );
//        bEarRight.addOrReplaceChild("earTR", CubeListBuilder.create()
//                        .texOffs(70, 0)
//                        .addBox(-2.0F, -2.0F, 0.0F, 3, 4, 1),
//                PartPose.ZERO
//        );

        bBody.addOrReplaceChild("body", CubeListBuilder.create()
                        .texOffs(7, 8)
                        .addBox(-3.5F, 0.0F, 0.0F, 7, 7, 9, new CubeDeformation(0.5F)),
                PartPose.ZERO
        );

        bButt.addOrReplaceChild("butt", CubeListBuilder.create()
                        .texOffs(30, 0)
                        .addBox(-4.0F, 0.0F, 0.0F, 8, 8, 8, new CubeDeformation(0.5F)),
                PartPose.ZERO
        );

        bLegFrontLeft.addOrReplaceChild("legFL", CubeListBuilder.create()
                        .texOffs(16, 56)
                        .addBox(0.0F, 0.0F, 0.0F, 3, 6, 2),
                PartPose.ZERO
        );
        bLegFrontRight.addOrReplaceChild("legFR", CubeListBuilder.create()
                        .texOffs(26, 56)
                        .addBox(0.0F, 0.0F, 0.0F, 3, 6, 2),
                PartPose.ZERO
        );
        bLegBackLeft.addOrReplaceChild("legBL", CubeListBuilder.create()
                        .texOffs(0, 37)
                        .addBox(0.0F, 0.0F, 0.0F, 3, 6, 6),
                PartPose.ZERO
        );
        bLegBackRight.addOrReplaceChild("legBR", CubeListBuilder.create()
                        .texOffs(18, 37)
                        .addBox(0.0F, 0.0F, 0.0F, 3, 6, 6),
                PartPose.ZERO
        );
        bTibiaBackLeft.addOrReplaceChild("tibiaBL", CubeListBuilder.create()
                        .texOffs(0, 49)
                        .addBox(0.0F, 0.0F, 0.0F, 3, 1, 5),
                PartPose.ZERO
        );
        bTibiaBackRight.addOrReplaceChild("tibiaBR", CubeListBuilder.create()
                        .texOffs(18, 49)
                        .addBox(0.0F, 0.0F, 0.0F, 3, 1, 5),
                PartPose.ZERO
        );
        bFootBackLeft.addOrReplaceChild("footBL", CubeListBuilder.create()
                        .texOffs( 0, 55)
                        .addBox(0.0F, 0.0F, 0.0F, 3, 8, 1),
                PartPose.ZERO
        );
        bFootBackRight.addOrReplaceChild("footBR", CubeListBuilder.create()
                        .texOffs(8, 55)
                        .addBox(0.0F, 0.0F, 0.0F, 3, 8, 1),
                PartPose.ZERO
        );

        bTail.addOrReplaceChild("tail", CubeListBuilder.create()
                        .texOffs(20, 0)
                        .addBox(-1.5F, -4.0F, 0.0F, 3, 4, 2),
                PartPose.ZERO
        );

        return LayerDefinition.create(meshdefinition, 128, 64);
    }

    public ModelEnhancedRabbit(ModelPart modelPart) {
        super(modelPart);
        ModelPart base = modelPart.getChild("base");
        ModelPart bRabbit = base.getChild("bRabbit");
        ModelPart bBody = bRabbit.getChild("bBody");
        ModelPart bButt = bRabbit.getChild("bButt");
        ModelPart bNeck = bBody.getChild("bNeck");
        ModelPart bHead = bNeck.getChild("bHead");
        ModelPart bNose = bHead.getChild("bNose");
        ModelPart bEarLeft = bHead.getChild("bEarL");
        ModelPart bEarRight = bHead.getChild("bEarR");
        ModelPart bLegFL = bRabbit.getChild("bLegFL");
        ModelPart bLegFR = bRabbit.getChild("bLegFR");
        ModelPart bLegBL = bRabbit.getChild("bLegBL");
        ModelPart bTibiaBL = bRabbit.getChild("bTibiaBL");
        ModelPart bFootBL = bRabbit.getChild("bFootBL");
        ModelPart bLegBR = bRabbit.getChild("bLegBR");
        ModelPart bTibiaBR = bRabbit.getChild("bTibiaBR");
        ModelPart bFootBR = bRabbit.getChild("bFootBR");
        ModelPart bTail = bBody.getChild("bTail");

        this.theRabbit = new WrappedModelPart(bRabbit, "bRabbit");
        this.theBody = new WrappedModelPart(bBody, "bBody");
        this.theButt = new WrappedModelPart(bButt, "bButt");
        this.theNeck = new WrappedModelPart(bNeck, "bNeck");
        this.theHead = new WrappedModelPart(bHead, "bHead");
        this.theNose = new WrappedModelPart(bNose, "bNose");
        this.theEarLeft = new WrappedModelPart(bEarLeft, "bEarL");
        this.theEarRight = new WrappedModelPart(bEarRight, "bEarR");
        this.theLegFrontLeft = new WrappedModelPart(bLegFL, "bLegFL");
        this.theLegFrontRight = new WrappedModelPart(bLegFR, "bLegFR");
        this.theLegBackLeft = new WrappedModelPart(bLegBL, "bLegBL");
        this.theLegBackRight = new WrappedModelPart(bLegBR, "bLegBR");
        this.theTibiaBackLeft = new WrappedModelPart(bTibiaBL, "bTibiaBL");
        this.theTibiaBackRight = new WrappedModelPart(bTibiaBR, "bTibiaBR");
        this.theFootBackLeft = new WrappedModelPart(bFootBL, "bFootBL");
        this.theFootBackRight = new WrappedModelPart(bFootBR, "bFootBR");
        this.theTail = new WrappedModelPart(bTail, "bTail");

        this.headLeft = new WrappedModelPart("headL", bHead);
        this.headRight = new WrappedModelPart("headR", bHead);

        this.eyes = new WrappedModelPart("eyes", bHead);

        this.muzzle = new WrappedModelPart("muzzle", bNose);

        this.nose = new WrappedModelPart("nose", bNose);

        this.earLeft = new WrappedModelPart("earL", bEarLeft);
//        this.earTopLeft = new WrappedModelPart("earTL", bEarLeft);

        this.earRight = new WrappedModelPart("earR", bEarRight);
//        this.earTopRight = new WrappedModelPart("earTR", bEarRight);

        this.body = new WrappedModelPart("body", bBody);

        this.butt = new WrappedModelPart("butt", bButt);

        this.legFrontLeft = new WrappedModelPart("legFL", bLegFL);
        this.legFrontRight = new WrappedModelPart("legFR", bLegFR);
        this.legBackLeft = new WrappedModelPart("legBL", bLegBL);
        this.legBackRight = new WrappedModelPart("legBR", bLegBR);
        this.tibiaBackLeft = new WrappedModelPart("tibiaBL", bTibiaBL);
        this.tibiaBackRight = new WrappedModelPart("tibiaBR", bTibiaBR);
        this.footBackLeft = new WrappedModelPart("footBL", bFootBL);
        this.footBackRight = new WrappedModelPart("footBR", bFootBR);

        this.tail = new WrappedModelPart("tail", bTail);

        this.theRabbit.addChild(this.theBody);
        this.theBody.addChild(this.theNeck);
        this.theBody.addChild(this.theButt);
        this.theNeck.addChild(this.theHead);
        this.theHead.addChild(this.theEarLeft);
        this.theHead.addChild(this.theEarRight);
        this.theHead.addChild(this.theNose);
        this.theBody.addChild(this.theLegFrontLeft);
        this.theBody.addChild(this.theLegFrontRight);
        this.theButt.addChild(this.theLegBackLeft);
        this.theLegBackLeft.addChild(this.theTibiaBackLeft);
        this.theTibiaBackLeft.addChild(this.theFootBackLeft);
        this.theButt.addChild(this.theLegBackRight);
        this.theLegBackRight.addChild(this.theTibiaBackRight);
        this.theTibiaBackRight.addChild(this.theFootBackRight);
        this.theButt.addChild(this.theTail);

        this.theHead.addChild(this.headLeft);
        this.theHead.addChild(this.headRight);
        this.theHead.addChild(this.eyes);

        this.theEarLeft.addChild(earLeft);
        this.theEarRight.addChild(earRight);

        this.theNose.addChild(this.muzzle);
        this.theNose.addChild(this.nose);

        this.theBody.addChild(this.body);

        this.theButt.addChild(this.butt);

        this.theLegFrontLeft.addChild(this.legFrontLeft);
        this.theLegFrontRight.addChild(this.legFrontRight);
        this.theLegBackLeft.addChild(this.legBackLeft);
        this.theLegBackRight.addChild(this.legBackRight);
        this.theTibiaBackLeft.addChild(this.tibiaBackLeft);
        this.theTibiaBackRight.addChild(this.tibiaBackRight);
        this.theFootBackRight.addChild(this.footBackRight);
        this.theFootBackLeft.addChild(this.footBackLeft);

        this.theTail.addChild(this.tail);
    }

    private void resetCubes() {

    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        RabbitModelData rabbitModelData = getRabbitModelData();
        RabbitPhenotype rabbit = rabbitModelData.getPhenotype();

        resetCubes();

        if (rabbit!=null) {
            super.renderToBuffer(poseStack, vertexConsumer, packedLightIn, packedOverlayIn, red, green, blue, alpha);

            poseStack.pushPose();
            poseStack.scale(1.0F, 1.0F, 1.0F);
            poseStack.translate(0.0F, 0.0F, 0.0F);

            gaRender(this.theRabbit, null, poseStack, vertexConsumer, packedLightIn, packedOverlayIn, red, green, blue, alpha);

            poseStack.popPose();
        }
    }

    protected Map<String, Vector3f> saveAnimationValues(T animal, RabbitPhenotype rabbit) {
        Map<String, Vector3f> map = animal.getModelRotationValues();
        return map;
    }

    private void setupInitialAnimationValues(T entityIn, RabbitModelData modelData, RabbitPhenotype rabbit) {

    }

    @Override
    public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.currentAnimal = entityIn.getId();
        RabbitModelData rabbitModelData = getCreateRabbitModelData(entityIn);
        RabbitPhenotype rabbit = rabbitModelData.getPhenotype();
        float drive = ageInTicks + (1000 * rabbitModelData.random);

        if (rabbit != null) {

        }

    }

    private class RabbitModelData extends AnimalModelData {
        public RabbitPhenotype getPhenotype() {
            return (RabbitPhenotype) this.phenotype;
        }
    }

    private RabbitModelData getRabbitModelData() {
        return (RabbitModelData) getAnimalModelData();
    }

    private RabbitModelData getCreateRabbitModelData(T enhancedRabbit) {
        return (RabbitModelData) getCreateAnimalModelData(enhancedRabbit);
    }

    @Override
    protected void setInitialModelData(T enhancedRabbit) {
        RabbitModelData rabbitModelData = new RabbitModelData();
        setBaseInitialModelData(rabbitModelData, enhancedRabbit);
    }

    @Override
    protected Phenotype createPhenotype(T enhancedAnimal) {
        return new RabbitPhenotype(enhancedAnimal.getSharedGenes().getAutosomalGenes(), enhancedAnimal.getStringUUID().charAt(1));
    }

    protected class RabbitPhenotype implements Phenotype {

        RabbitPhenotype(int[] gene,char uuid) {

        }
    }
}
