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
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Map;

@OnlyIn(Dist.CLIENT)
public class ModelEnhancedRabbit<T extends EnhancedRabbit> extends EnhancedAnimalModel<T> {
    protected WrappedModelPart theRabbit;

    protected WrappedModelPart theHead;
    protected WrappedModelPart theEarLeft;
    protected WrappedModelPart theEarRight;
    protected WrappedModelPart theNeck;
    protected WrappedModelPart theBody;
    protected WrappedModelPart theLegFrontLeft;
    protected WrappedModelPart theLegFrontRight;
    protected WrappedModelPart theLegBackLeft;
    protected WrappedModelPart theLegBackRight;
    protected WrappedModelPart theTail;

    protected WrappedModelPart head;
    protected WrappedModelPart nose;
    protected WrappedModelPart jaw;

    protected WrappedModelPart earLeft;
    protected WrappedModelPart earTopLeft;

    protected WrappedModelPart earRight;
    protected WrappedModelPart earTopRight;

    protected WrappedModelPart neck;
    protected WrappedModelPart body;

    private WrappedModelPart legFrontLeft;
    private WrappedModelPart legFrontRight;
    private WrappedModelPart legBackLeft;
    private WrappedModelPart legBackRight;

    private WrappedModelPart tail;

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition base = meshdefinition.getRoot().addOrReplaceChild("base", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition bRabbit = base.addOrReplaceChild("bRabbit", CubeListBuilder.create(), PartPose.offset(0.0F, 14.0F, 0.0F));
        PartDefinition bBody = bRabbit.addOrReplaceChild("bBody", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition bNeck = bBody.addOrReplaceChild("bNeck", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition bHead = bNeck.addOrReplaceChild("bHead", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition bEarLeft = bHead.addOrReplaceChild("bEarL", CubeListBuilder.create(), PartPose.offset(4.0F, 0.0F, -3.0F));
        PartDefinition bEarRight = bHead.addOrReplaceChild("bEarR", CubeListBuilder.create(), PartPose.offset(-4.0F, 0.0F, -3.0F));
        PartDefinition bLegFrontLeft = bRabbit.addOrReplaceChild("bLegFL", CubeListBuilder.create(), PartPose.offset(3.0F, 0.0F, -10.0F));
        PartDefinition bLegFrontRight = bRabbit.addOrReplaceChild("bLegFR", CubeListBuilder.create(), PartPose.offset(-3.0F, 0.0F, -10.0F));
        PartDefinition bLegBackLeft = bRabbit.addOrReplaceChild("bLegBL", CubeListBuilder.create(), PartPose.offset(3.0F, 0.0F, 9.0F));
        PartDefinition bLegBackRight = bRabbit.addOrReplaceChild("bLegBR", CubeListBuilder.create(), PartPose.offset(-3.0F, 0.0F, 9.0F));
        PartDefinition bTail = bBody.addOrReplaceChild("bTail", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 22.0F));

        bHead.addOrReplaceChild("eyes", CubeListBuilder.create()
                        .texOffs(69, 15)
                        .addBox(2.5F, 0.0F, 0.0F, 1, 1, 1, new CubeDeformation(0.01F))
                        .texOffs(0, 40)
                        .addBox(-3.5F, 0.0F, 0.0F, 1, 1, 1, new CubeDeformation(0.01F)),
                PartPose.ZERO
        );

        bHead.addOrReplaceChild("head", CubeListBuilder.create()
                        .texOffs(49, 0)
                        .addBox(-3.5F, -5.0F, -4.0F, 7, 6, 7),
                PartPose.ZERO
        );
        bHead.addOrReplaceChild("nose", CubeListBuilder.create()
                        .texOffs(49, 22)
                        .addBox(-2.0F, -5.0F, -3.0F, 4, 6, 3),
                PartPose.ZERO
        );
        bHead.addOrReplaceChild("jaw", CubeListBuilder.create()
                        .texOffs(49, 22)
                        .addBox(-2.0F, -5.0F, -3.0F, 4, 6, 3),
                PartPose.offset(0.0F, 1.0F, -4.0F)
        );

        bEarLeft.addOrReplaceChild("earL", CubeListBuilder.create()
                        .texOffs(46, 0)
                        .addBox(-1.0F, -2.0F, 0.0F, 3, 4, 1),
                PartPose.ZERO
        );
        bEarLeft.addOrReplaceChild("earTL", CubeListBuilder.create()
                        .texOffs(46, 0)
                        .addBox(-1.0F, -3.0F, 0.0F, 3.5F, 5, 1),
                PartPose.ZERO
        );

        bEarRight.addOrReplaceChild("earR", CubeListBuilder.create()
                        .texOffs(70, 0)
                        .addBox(-2.0F, -2.0F, 0.0F, 3, 4, 1),
                PartPose.ZERO
        );
        bEarRight.addOrReplaceChild("earTR", CubeListBuilder.create()
                        .texOffs(70, 0)
                        .addBox(-2.5F, -3.0F, 0.0F, 3.5F, 5, 1),
                PartPose.ZERO
        );

        bNeck.addOrReplaceChild("neck", CubeListBuilder.create()
                        .texOffs(0, 0)
                        .addBox(-4.5F, -6.75F, -9.0F, 9, 7, 9),
                PartPose.ZERO
        );

        bBody.addOrReplaceChild("body", CubeListBuilder.create()
                        .texOffs(0, 23)
                        .addBox(-5.0F, 0.0F, 0.0F, 10, 11, 10),
                PartPose.offset(0.0F, 18.1F, -4.0F)
        );

        bLegFrontLeft.addOrReplaceChild("legFL", CubeListBuilder.create()
                        .texOffs(49, 32)
                        .addBox(-3.0F, 0.0F, 0.0F, 3, 8, 3),
                PartPose.ZERO
        );
        bLegFrontRight.addOrReplaceChild("legFR", CubeListBuilder.create()
                        .texOffs(61, 32)
                        .addBox(0.0F, 0.0F, 0.0F, 3, 8, 3),
                PartPose.ZERO
        );
        bLegBackLeft.addOrReplaceChild("legBL", CubeListBuilder.create()
                        .texOffs(49, 44)
                        .addBox(-3.0F, 0.0F, 0.0F, 3, 8, 3),
                PartPose.ZERO
        );
        bLegBackRight.addOrReplaceChild("legBR", CubeListBuilder.create()
                        .texOffs(61, 44)
                        .addBox(0.0F, 0.0F, 0.0F, 3, 8, 3),
                PartPose.ZERO
        );

        bTail.addOrReplaceChild("tail", CubeListBuilder.create()
                        .texOffs(36, 0)
                        .addBox(-0.5F, 0.0F, 0.0F, 1, 2, 1, new CubeDeformation(-0.05F)),
                PartPose.ZERO
        );

        return LayerDefinition.create(meshdefinition, 256, 256);
    }

    public ModelEnhancedRabbit(ModelPart modelPart) {
        super(modelPart);
        ModelPart base = modelPart.getChild("base");
        ModelPart bRabbit = base.getChild("bRabbit");
        ModelPart bBody = bRabbit.getChild("bBody");
        ModelPart bNeck = bBody.getChild("bNeck");
        ModelPart bHead = bNeck.getChild("bHead");
        ModelPart bEarLeft = bHead.getChild("bEarL");
        ModelPart bEarRight = bHead.getChild("bEarR");
        ModelPart bLegFL = bRabbit.getChild("bLegFL");
        ModelPart bLegFR = bRabbit.getChild("bLegFR");
        ModelPart bLegBL = bRabbit.getChild("bLegBL");
        ModelPart bLegBR = bRabbit.getChild("bLegBR");
        ModelPart bTail = bBody.getChild("bTail");

        this.theRabbit = new WrappedModelPart(bRabbit, "bRabbit");
        this.theBody = new WrappedModelPart(bBody, "bBody");
        this.theNeck = new WrappedModelPart(bNeck, "bNeck");
        this.theHead = new WrappedModelPart(bHead, "bHead");
        this.theEarLeft = new WrappedModelPart(bEarLeft, "bEarL");
        this.theEarRight = new WrappedModelPart(bEarRight, "bEarR");
        this.theLegFrontLeft = new WrappedModelPart(bLegFL, "bLegFL");
        this.theLegFrontRight = new WrappedModelPart(bLegFR, "bLegFR");
        this.theLegBackLeft = new WrappedModelPart(bLegBL, "bLegBL");
        this.theLegBackRight = new WrappedModelPart(bLegBR, "bLegBR");
        this.theTail = new WrappedModelPart(bTail, "bTail");

        this.eyes = new WrappedModelPart("eyes", bHead);

        this.head = new WrappedModelPart("head", bHead);

        this.jaw = new WrappedModelPart("jaw", bHead);

        this.earLeft = new WrappedModelPart("earL", bEarLeft);
        this.earTopLeft = new WrappedModelPart("earTL", bEarLeft);

        this.earRight = new WrappedModelPart("earR", bEarRight);
        this.earTopRight = new WrappedModelPart("earTR", bEarRight);

        this.neck = new WrappedModelPart("neck", bNeck);

        this.body = new WrappedModelPart("body", bBody);

        this.legFrontLeft = new WrappedModelPart("legFL", bLegFL);
        this.legFrontRight = new WrappedModelPart("legFR", bLegFR);
        this.legBackLeft = new WrappedModelPart("legBL", bLegBL);
        this.legBackRight = new WrappedModelPart("legBR", bLegBR);

        this.tail = new WrappedModelPart("tail", bTail);

        this.theRabbit.addChild(this.theBody);
        this.theBody.addChild(this.theNeck);
        this.theNeck.addChild(this.theHead);
        this.theHead.addChild(this.theEarLeft);
        this.theHead.addChild(this.theEarRight);
        this.theRabbit.addChild(this.legFrontLeft);
        this.theRabbit.addChild(this.legFrontRight);
        this.theRabbit.addChild(this.legBackLeft);
        this.theRabbit.addChild(this.legBackRight);

        this.theHead.addChild(this.head);
        this.theHead.addChild(this.eyes);
        this.theHead.addChild(this.nose);
        this.theHead.addChild(this.jaw);

        this.theBody.addChild(this.body);

        this.theLegFrontLeft.addChild(this.legFrontLeft);

        this.theLegFrontRight.addChild(this.legFrontRight);

        this.theLegBackLeft.addChild(this.legBackLeft);

        this.theLegBackRight.addChild(this.legBackRight);
    }

    private void resetCubes() {

    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        RabbitModelData rabbitModelData = getRabbitModelData();
        RabbitPhenotype rabbit = rabbitModelData.getPhenotype();

        resetCubes();

        if (rabbit!=null) {

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

//package mokiyoki.enhancedanimals.model;
//
//import com.mojang.blaze3d.vertex.PoseStack;
//import com.mojang.blaze3d.vertex.VertexConsumer;
//import mokiyoki.enhancedanimals.config.EanimodCommonConfig;
//import mokiyoki.enhancedanimals.entity.EnhancedRabbit;
//import mokiyoki.enhancedanimals.items.CustomizableCollar;
//import mokiyoki.enhancedanimals.model.util.ModelHelper;
//import net.minecraft.client.model.EntityModel;
//import net.minecraft.client.model.geom.ModelPart;
//import net.minecraft.client.multiplayer.ClientLevel;
//import net.minecraft.world.SimpleContainer;
//import net.minecraft.util.Mth;
//import net.minecraftforge.api.distmarker.Dist;
//import net.minecraftforge.api.distmarker.OnlyIn;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@OnlyIn(Dist.CLIENT)
//public class ModelEnhancedRabbit <T extends EnhancedRabbit> extends EnhancedAnimalModel<T> {
//
//    private Map<Integer, RabbitModelData> rabbitModelDataCache = new HashMap<>();
//    private int clearCacheTimer = 0;
//
//    private float earX = 0;
//    private float earY = 0.2617994F;
//    private float earZ = 0;
//
//    private final ModelPart rabbitLeftFoot;
//    private final ModelPart rabbitRightFoot;
//    private final ModelPart rabbitLeftCalf;
//    private final ModelPart rabbitRightCalf;
//    private final ModelPart rabbitLeftThigh;
//    private final ModelPart rabbitRightThigh;
//    private final ModelPart rabbitBody;
//    private final ModelPart rabbitBodyAngora1;
//    private final ModelPart rabbitBodyAngora2;
//    private final ModelPart rabbitBodyAngora3;
//    private final ModelPart rabbitBodyAngora4;
//    private final ModelPart rabbitButtRound;
//    private final ModelPart rabbitButt;
//    private final ModelPart rabbitButtAngora1;
//    private final ModelPart rabbitButtAngora2;
//    private final ModelPart rabbitButtAngora3;
//    private final ModelPart buttAngora4;
//    private final ModelPart rabbitButtTube;
//    private final ModelPart leftArm;
//    private final ModelPart rightArm;
//    private final ModelPart headLeft;
//    private final ModelPart headRight;
//    private final ModelPart eyeLeft;
//    private final ModelPart eyeRight;
//    private final ModelPart rabbitHeadMuzzle;
//    private final ModelPart rabbitNose;
//    private final ModelPart rabbitHeadMuzzleDwarf;
//    private final ModelPart rabbitNoseDwarf;
//    private final ModelPart lionHeadManeDouble;
//    private final ModelPart lionHeadManeSingle;
//    private final ModelPart lionHeadDouble;
//    private final ModelPart lionHeadSingle;
//    private final ModelPart lionHeadCheeks;
//    private final ModelPart earHelper;
//    private final ModelPart earL;
//    private final ModelPart earR;
//    private final ModelPart dwarfEarL;
//    private final ModelPart dwarfEarR;
//    private final ModelPart rabbitTail;
//    private final ModelPart collar;
//    private float jumpRotation;
//
//    private Integer currentRabbit = null;
//
//    public ModelEnhancedRabbit()
//    {
//        this.texWidth = 128;
//        this.texHeight = 64;
//
//        float xMove = -2.0F;
//
//        this.rabbitLeftFoot = new ModelPart(this, 0, 55);
//        this.rabbitLeftFoot.addBox(0F, 0F, 0F, 3, 8, 1);
//        this.rabbitLeftFoot.setPos(0.0F, 5.0F, 1.0F + xMove);
//        this.setRotationOffset(this.rabbitLeftFoot, 3.0F, 0.0F, 0.0F);
//        this.rabbitLeftFoot.mirror = true;
//
//        this.rabbitRightFoot = new ModelPart(this, 8, 55);
//        this.rabbitRightFoot.addBox(0F, 0F, 0F, 3, 8, 1);
//        this.rabbitRightFoot.setPos(0F, 5.0F, 1.0F + xMove);
//        this.setRotationOffset(this.rabbitRightFoot, 3.0F, 0.0F, 0.0F);
//        this.rabbitRightFoot.mirror = true;
//
//        this.rabbitLeftCalf = new ModelPart(this, 0, 49);
//        this.rabbitLeftCalf.addBox(0F, 0F, 0F, 3, 5, 1);
//        this.rabbitLeftCalf.setPos(0.0F, 6.5F, 2.7F + xMove);
//        this.setRotationOffset(this.rabbitLeftCalf, 2.0F, 0.0F, 0.0F);
//        this.rabbitLeftCalf.addChild(rabbitLeftFoot);
//
//        this.rabbitRightCalf = new ModelPart(this, 18, 49);
//        this.rabbitRightCalf.addBox(0F, 0F, 0F, 3, 5, 1);
//        this.rabbitRightCalf.setPos(0, 6.5F, 2.7F + xMove);
//        this.setRotationOffset(this.rabbitRightCalf, 2.0F, 0.0F, 0.0F);
//        this.rabbitRightCalf.addChild(rabbitRightFoot);
//
//        this.rabbitLeftThigh = new ModelPart(this, 0, 37);
//        this.rabbitLeftThigh.addBox(0F, 0F, 0F, 3, 6, 6);
//        this.rabbitLeftThigh.setPos(-4.5F, 1.0F, 2.5F);
//        this.rabbitLeftThigh.addChild(rabbitLeftCalf);
//
//        this.rabbitRightThigh = new ModelPart(this, 18, 37);
//        this.rabbitRightThigh.addBox(0F, 0F, 0F, 3, 6, 6);
//        this.rabbitRightThigh.setPos(1.5F, 1.0F, 2.5F);
//        this.rabbitRightThigh.addChild(rabbitRightCalf);
//
//        this.rabbitBody = new ModelPart(this, 7, 8);
//        this.rabbitBody.addBox(-3.5F, 0.0F, 0.0F, 7, 7, 9,0.5F);
//        this.rabbitBody.setPos(0.0F, 16.0F, -4.5F + xMove);
//
//        this.rabbitBodyAngora1 = new ModelPart(this, 7, 8);
//        this.rabbitBodyAngora1.addBox(-3.5F, 0F, 0F, 7, 7, 9, 1F);
//        this.rabbitBodyAngora1.setPos(0.0F, 15.5F, -4.5F);
//
//        this.rabbitBodyAngora2 = new ModelPart(this, 7, 8);
//        this.rabbitBodyAngora2.addBox(-3.5F, 0F, 0F, 7, 7, 9, 2F);
//        this.rabbitBodyAngora2.setPos(0.0F, 15.0F, -4.5F);
//
//        this.rabbitBodyAngora3 = new ModelPart(this, 7, 8);
//        this.rabbitBodyAngora3.addBox(-3.5F, 0F, 0F, 7, 7, 9, 3F);
//        this.rabbitBodyAngora3.setPos(0.0F, 14.5F, -4.5F);
//
//        this.rabbitBodyAngora4 = new ModelPart(this, 7, 8);
//        this.rabbitBodyAngora4.addBox(-3.5F, 0F, 0F, 7, 7, 9, 4F);
//        this.rabbitBodyAngora4.setPos(0.0F, 14.0F, -4.5F);
//
//        this.rabbitButtRound = new ModelPart(this, 30, 0);
//        this.rabbitButtRound.addBox(-4.0F, 0.0F, 0.0F, 8, 8, 8, 0.5F);
//        this.rabbitButtRound.setPos(0.0F, 14.0F, 2.5F + xMove);
//        this.rabbitButtRound.addChild(this.rabbitLeftThigh);
//        this.rabbitButtRound.addChild(this.rabbitRightThigh);
//
//        this.rabbitButt = new ModelPart(this, 30, 0);
//        this.rabbitButt.addBox(-4.0F, 0.0F, 0.0F, 8, 8, 8);
//        this.rabbitButt.setPos(0.0F, 15.0F, 2.5F + xMove);
//        this.rabbitButt.addChild(this.rabbitLeftThigh);
//        this.rabbitButt.addChild(this.rabbitRightThigh);
//
//        this.rabbitButtTube = new ModelPart(this, 30, 0);
//        this.rabbitButtTube.addBox(-4.0F, 0.0F, 0.0F, 8, 8, 8, -0.49F);
//        this.rabbitButtTube.setPos(0.0F, 16.1F, 2.5F + xMove);
//        this.rabbitButtTube.addChild(this.rabbitLeftThigh);
//        this.rabbitButtTube.addChild(this.rabbitRightThigh);
//
//        this.rabbitButtAngora1 = new ModelPart(this, 30, 0);
//        this.rabbitButtAngora1.addBox(-4.0F, 0.0F, 0.0F, 8, 8, 8, 1F);
//        this.rabbitButtAngora1.setPos(0.0F, 14.5F, 2.5F);
//        this.rabbitButtAngora1.addChild(this.rabbitLeftThigh);
//        this.rabbitButtAngora1.addChild(this.rabbitRightThigh);
//
//        this.rabbitButtAngora2 = new ModelPart(this, 30, 0);
//        this.rabbitButtAngora2.addBox(-4.0F, 0.0F, 0.0F, 8, 8, 8, 2F);
//        this.rabbitButtAngora2.setPos(0.0F, 14.0F, 2.5F);
//        this.rabbitButtAngora2.addChild(this.rabbitLeftThigh);
//        this.rabbitButtAngora2.addChild(this.rabbitRightThigh);
//
//        this.rabbitButtAngora3 = new ModelPart(this, 30, 0);
//        this.rabbitButtAngora3.addBox(-4.0F, 0.0F, 0.0F, 8, 8, 8, 3F);
//        this.rabbitButtAngora3.setPos(0.0F, 13.5F, 2.5F);
//        this.rabbitButtAngora3.addChild(this.rabbitLeftThigh);
//        this.rabbitButtAngora3.addChild(this.rabbitRightThigh);
//
//        this.buttAngora4 = new ModelPart(this, 30, 0);
//        this.buttAngora4.addBox(-4.0F, 0.0F, 0.0F, 8, 8, 8, 4F);
//        this.buttAngora4.setPos(0.0F, 13.0F, 2.5F);
//        this.buttAngora4.addChild(this.rabbitLeftThigh);
//        this.buttAngora4.addChild(this.rabbitRightThigh);
//
//        this.leftArm = new ModelPart(this, 16, 56);
//        this.leftArm.addBox(0.0F, 0.0F, 0.0F, 3, 6, 2);
//        this.leftArm.setPos(-3.5F, 23.5F, -2.0F + xMove);
//        this.setRotationOffset(this.leftArm, -1.6F, 0.0F, 0.0F);
//
//        this.rightArm = new ModelPart(this, 26, 56);
//        this.rightArm.addBox(0.0F, 0F, 0F, 3, 6, 2);
//        this.rightArm.setPos(0.5F, 23.5F, -2.0F + xMove);
//        this.setRotationOffset(this.rightArm, -1.6F, 0.0F, 0.0F);
//
//        this.headLeft = new ModelPart(this, 0, 24);
//        this.headLeft.addBox(0.0F, 0.0F, -6.0F, 3, 6, 6);
//        this.headLeft.setPos(0.0F, 14.0F, -7.0F);
//
//        this.headRight = new ModelPart(this, 18, 24);
//        this.headRight.addBox(-3.0F, 0F, -6.0F, 3, 6, 6);
//        this.headRight.setPos(0.0F, 14.0F, -7.0F);
//
//        this.eyeLeft = new ModelPart(this, 0, 16);
//        this.eyeLeft.addBox(2.0F, 2.0F, -4.0F, 1, 2, 2, 0.01F);
//
//        this.eyeRight = new ModelPart(this, 0, 20);
//        this.eyeRight.addBox(-3.0F, 2.0F, -4.0F, 1, 2, 2, 0.01F);
//
//        this.rabbitHeadMuzzle = new ModelPart(this, 0, 8);
//        this.rabbitHeadMuzzle.addBox(-2F, 1.5F, -8F, 4, 4, 4);
//        this.rabbitHeadMuzzle.setPos(0.0F, 0.0F, 0.0F + xMove);
//
//        this.rabbitNose = new ModelPart(this, 0, 8);
//        this.rabbitNose.addBox(-0.5F, 1.6F, -8.1F, 1, 1, 1);
//
//        this.rabbitHeadMuzzleDwarf = new ModelPart(this, 0, 8);
//        this.rabbitHeadMuzzleDwarf.addBox(-2F, 1.5F, -7F, 4, 4, 4);
//        this.rabbitHeadMuzzleDwarf.setPos(0.0F, 0.0F, 0.0F + xMove);
//
//        this.rabbitNoseDwarf = new ModelPart(this, 0, 8);
//        this.rabbitNoseDwarf.addBox(-0.5F, 1.6F, -7.1F, 1, 1, 1);
//
//        this.lionHeadManeDouble = new ModelPart(this, 98, 23);
//        this.lionHeadManeDouble.addBox(-7.5F, -5.5F, 0.0F, 15, 15, 0);
//
//        this.lionHeadManeSingle = new ModelPart(this, 98, 23);
//        this.lionHeadManeSingle.addBox(-7.5F, -3.5F, 0.0F, 15, 13, 0);
//
//        this.lionHeadDouble = new ModelPart(this, 106, 10);
//        this.lionHeadDouble.addBox(-5.5F, -4.0F, -2.0F, 11, 13, 0);
//        this.lionHeadDouble.setTextureOffset(112, 31);
//        this.lionHeadDouble.addBox(0.0F, -3.0F, -6.0F, 0, 7, 8);
//        this.lionHeadDouble.setTextureOffset(106, 0);
//        this.lionHeadDouble.addBox(-5.5F, -1.0F, -3.0F, 11, 9, 0);
//
//        this.lionHeadSingle = new ModelPart(this, 106, 12);
//        this.lionHeadSingle.addBox(-5.5F, -2.0F, -2.0F, 11, 11, 0);
//        this.lionHeadSingle.setTextureOffset(113, 32);
//        this.lionHeadSingle.addBox(0.0F, -2.0F, -6.0F, 0, 6, 7);
//
//        this.lionHeadCheeks = new ModelPart(this, 100, 35);
//        this.lionHeadCheeks.addBox(-5.5F, 4.0F, -4.0F, 11, 4, 0);
//
//        this.earHelper = new ModelPart(this, 0, 0);
//        this.earHelper.setPos(0.0F, 0.0F, -6.0F);
//
//        this.earL = new ModelPart(this, 10, 0);
//        this.earL.addBox(-3.0F, -7.0F, -0.5F, 4, 7, 1);
//        this.earR = new ModelPart(this, 0, 0);
//        this.earR.addBox(-1.0F, -7.0F, -0.5F, 4, 7, 1);
//
//        this.dwarfEarL = new ModelPart(this, 10, 0);
//        this.dwarfEarL.addBox(-3.0F, -5.0F, -0.5F, 4, 5, 1);
//        this.dwarfEarR = new ModelPart(this, 0, 0);
//        this.dwarfEarR.addBox(-1.0F, -5.0F, -0.5F, 4, 5, 1);
//
//        this.rabbitTail = new ModelPart(this, 20, 0);
//        this.rabbitTail.addBox(-1.5F, 2.0F, 8.0F, 3, 4, 2);
//        this.rabbitTail.setPos(0.0F, 0.0F, 0.0F + xMove);
//
//        this.headLeft.addChild(this.eyeLeft);
//        this.headRight.addChild(this.eyeRight);
//        this.headLeft.addChild(this.earHelper);
//        this.headLeft.addChild(this.lionHeadDouble);
//        this.headLeft.addChild(this.lionHeadSingle);
//        this.headLeft.addChild(this.lionHeadCheeks);
//
//        this.earHelper.addChild(this.earL);
//        this.earHelper.addChild(this.earR);
//        this.earHelper.addChild(this.dwarfEarL);
//        this.earHelper.addChild(this.dwarfEarR);
//
//        this.collar = new ModelPart(this, 36, 55);
//        this.collar.addBox(-3.5F, -1.0F, -0.5F, 7, 2, 7);
//        this.collar.texOffs(35, 51);
//        this.collar.addBox(0.0F, -1.5F, 5.5F, 0,  3, 3);
//        this.collar.texOffs(12, 37);
//        this.collar.addBox(-1.5F, -1.5F, 7.0F, 3, 3, 3, -0.5F);
//        this.headLeft.addChild(this.collar);
//    }
//
//    private void setRotationOffset(ModelPart renderer, float x, float y, float z) {
//        renderer.xRot = x;
//        renderer.yRot = y;
//        renderer.zRot = z;
//    }
//
//    /**
//     * Sets the models various rotation angles then renders the model.
//     */
//    @Override
//    public void renderToBuffer(PoseStack matrixStackIn, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
//        RabbitModelData rabbitModelData = getRabbitModelData();
//        RabbitPhenotype rabbit = rabbitModelData.rabbitPhenotype;
//
//        if (rabbit!=null) {
//            int coatLength = rabbitModelData.coatlength;
//
//            float age = 1.0F;
//            if (!(rabbitModelData.birthTime == null) && !rabbitModelData.birthTime.equals("") && !rabbitModelData.birthTime.equals("0")) {
//                int ageTime = (int) (rabbitModelData.clientGameTime - Long.parseLong(rabbitModelData.birthTime));
//                if (ageTime <= rabbitModelData.adultAge) {
//                    age = ageTime < 0 ? 0 : ageTime / (float) rabbitModelData.adultAge;
//                }
//            }
//
//            float finalRabbitSize = ((3.0F * rabbit.size * age) + rabbit.size) / 4.0F;
//
//            matrixStackIn.pushPose();
//            matrixStackIn.scale(finalRabbitSize, finalRabbitSize, finalRabbitSize);
//            matrixStackIn.translate(0.0F, -1.45F + 1.45F / finalRabbitSize, 0.0F);
//            if (rabbitModelData.blink >= 6) {
//                this.eyeLeft.visible = true;
//                this.eyeRight.visible = true;
//            } else {
//                this.eyeLeft.visible = false;
//                this.eyeRight.visible = false;
//            }
//
//            this.earL.visible = false;
//            this.earR.visible = false;
//            this.dwarfEarL.visible = false;
//            this.dwarfEarR.visible = false;
//
//            this.lionHeadDouble.showModel = false;
//            this.lionHeadSingle.showModel = false;
//            this.lionHeadCheeks.showModel = false;
//
//            switch (rabbit.lionsmane) {
//                case NONE:
//                    break;
//                case SINGLE:
//                    this.lionHeadManeSingle.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
//                    this.lionHeadSingle.showModel = true;
//                    this.lionHeadCheeks.showModel = true;
//                    break;
//                case DOUBLE:
//                    this.lionHeadManeDouble.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
//                    this.lionHeadDouble.showModel = true;
//                    this.lionHeadCheeks.showModel = true;
//                    break;
//            }
//
//            this.earL.visible = true;
//            this.earR.visible = true;
//            this.headLeft.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
//            this.headRight.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
//
//            if (rabbit.dwarf) {
//                this.rabbitHeadMuzzleDwarf.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
//                this.rabbitNoseDwarf.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
//                this.dwarfEarL.visible = true;
//                this.dwarfEarR.visible = true;
//            } else {
//                this.rabbitHeadMuzzle.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
//                this.rabbitNose.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
//                this.earL.visible = true;
//                this.earR.visible = true;
//            }
//
//            if (coatLength == 0) {
//                this.rabbitBody.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
//                //this.rabbitButtRound.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
//                this.rabbitButt.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
//                //this.rabbitButtTube.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
//            } else {
//                if (coatLength == 1) {
//                    this.rabbitBodyAngora1.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
//                    //this.rabbitButtRoundAngora1.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
//                    this.rabbitButtAngora1.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
//                    //this.rabbitButtTubeAngora1.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
//                } else if (coatLength == 2) {
//                    this.rabbitBodyAngora2.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
//                    //this.rabbitButtRoundAngora2.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
//                    this.rabbitButtAngora2.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
//                    //this.rabbitButtTubeAngora2.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
//                } else if (coatLength == 3) {
//                    this.rabbitBodyAngora3.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
//                    //this.rabbitButtRoundAngora3.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
//                    this.rabbitButtAngora3.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
//                    //this.rabbitButtTubeAngora3.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
//                } else if (coatLength == 4) {
//                    this.rabbitBodyAngora4.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
//                    //this.rabbitButtRoundAngora4.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
//                    this.buttAngora4.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
//                    //this.rabbitButtTubeAngora4.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
//                }
//            }
//
//            this.leftArm.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
//            this.rightArm.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
//            this.rabbitTail.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
//            matrixStackIn.popPose();
//        }
//    }
//
//    /**
//     * Used for easily adding entity-dependent animations. The second and third float params here are the same second
//     * and third as in the setRotationAngles method.
//     */
//    @Override
//    public void prepareMobModel(T entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTickTime) {
//        super.prepareMobModel(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTickTime);
//        this.jumpRotation = Mth.sin(((EnhancedRabbit)entitylivingbaseIn).getJumpCompletion(partialTickTime) * (float)Math.PI);
//        RabbitModelData rabbitModelData = getCreateRabbitModelData(entitylivingbaseIn);
//        this.currentRabbit = entitylivingbaseIn.getId();
//    }
//
//    /**
//     * Sets the model's various rotation angles. For bipeds, par1 and par2 are used for animating the movement of arms
//     * and legs, where par1 represents the time(so that arms and legs swing back and forth) and par2 represents how
//     * "far" arms and legs can swing at most.
//     */
//    @Override
//    public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
//        float f = ageInTicks - (float)entityIn.tickCount;
//
//        RabbitModelData rabbitModelData = getRabbitModelData();
//        RabbitPhenotype rabbit = rabbitModelData.rabbitPhenotype;
//
//        if (rabbit!=null) {
//
//            this.headLeft.xRot = headPitch * 0.017453292F;
//            this.headLeft.yRot = netHeadYaw * 0.017453292F;
//            ModelHelper.copyModelPositioning(headLeft, headRight);
//            ModelHelper.copyModelPositioning(headLeft, rabbitHeadMuzzle);
//            ModelHelper.copyModelPositioning(headLeft, rabbitHeadMuzzleDwarf);
//            ModelHelper.copyModelPositioning(headLeft, rabbitNose);
//
//            this.collar.xRot = -(this.headLeft.xRot / 2.0F) - ((float) Math.PI / 2.0F);
//            this.collar.yRot = -(this.headLeft.yRot / 2.0F);
//
////        this.rabbitNose.rotateAngleY = netHeadYaw * 0.017453292F;
////        this.earL.rotateAngleY = Math.abs(this.rabbitNose.rotateAngleY) * 1.0F;
////        this.earR.rotateAngleY = -Math.abs(this.rabbitNose.rotateAngleY) * 1.0F;
//
//            float lookMod = Math.abs(this.rabbitNose.yRot);
//
//            float lop = rabbit.lop;
//
//            float earPointX = 1.0F/* + (lookMod / 4.0F)*/;
//            float earPointY = 2.0F;
//            float earPointZ = 4.5F;
//
//            float earRotateX = (lookMod * 0.7F) - 1.35F;
//            float earRotateY = 0.0F;
//            float earRotateZ = (lookMod * 0.9F) - 1.4F;
//
//            float floppyEarPointX = 3.5F;
//            float floppyEarPointY = 2.0F;
//            float floppyEarPointZ = 2.0F;
//
//            float floppyEarRotateX = 0.3F;
//            float floppyEarRotateY = 1.8F;
//            float floppyEarRotateZ = 3.2F;
//
//            earPointX = (earPointX * (1.0F - lop) + floppyEarPointX * (lop));
//            earPointY = (earPointY * (1.0F - lop) + floppyEarPointY * (lop));
//            earPointZ = (earPointZ * (1.0F - lop) + floppyEarPointZ * (lop));
//
//            this.earL.setPos(-earPointX, earPointY, earPointZ);
//            this.earR.setPos(earPointX, earPointY, earPointZ);
//
//            this.earL.xRot = (earRotateX * (1.0F - lop) + floppyEarRotateX * (-lop));
//            this.earR.xRot = (earRotateX * (1.0F - lop) + floppyEarRotateX * (-lop));
//            this.earL.yRot = (earRotateY * (1.0F - lop) + floppyEarRotateY * (lop));
//            this.earR.yRot = (earRotateY * (1.0F - lop) + (floppyEarRotateY) * (-lop));
//            this.earL.zRot = (earRotateZ * (1.0F - lop) + floppyEarRotateZ * (lop));
//            this.earR.zRot = (-earRotateZ * (1.0F - lop) + floppyEarRotateZ * (lop));
//
//            ModelHelper.copyModelPositioning(earL, dwarfEarL);
//            ModelHelper.copyModelPositioning(earR, dwarfEarR);
//
//            //changes some rotation angles
//            this.rabbitButtRound.z = 2.5F;
//            this.rabbitButt.z = 2.5F;
//            this.rabbitButtTube.z = 2.5F;
//
//            if (rabbit.dwarf) {
//                this.rabbitButtRound.z = 0.5F;
//                this.rabbitButt.z = 0.5F;
//                this.rabbitButtTube.z = 0.5F;
//            }
//
//            this.jumpRotation = Mth.sin(((EnhancedRabbit) entityIn).getJumpCompletion(f) * (float) Math.PI);
//            if (this.jumpRotation == 0.0F) {
//                this.rabbitLeftFoot.xRot = 3.0F;
//                this.rabbitRightFoot.xRot = 3.0F;
//                this.rabbitLeftCalf.xRot = 2.1F;
//                this.rabbitRightCalf.xRot = 2.1F;
//                this.leftArm.xRot = -1.6F;
//                this.rightArm.xRot = -1.6F;
//                this.rabbitBody.xRot = 0.0F;
//                this.earHelper.xRot = this.headLeft.xRot + 0.4F;
////            this.LionEarParent.rotateAngleX = this.headLeft.rotateAngleX + 0.4F;
////            this.LionEarParent1.rotateAngleX = this.headLeft.rotateAngleX + 0.4F;
////            this.DwarfParent.rotateAngleX = this.headLeft.rotateAngleX + 0.4F;
//                this.rabbitBodyAngora1.xRot = 0.0F;
//                this.rabbitBodyAngora2.xRot = 0.0F;
//                this.rabbitBodyAngora3.xRot = 0.0F;
//                this.rabbitBodyAngora4.xRot = 0.0F;
//                this.rabbitButtRound.xRot = 0.0F;
//                this.rabbitButt.xRot = 0.0F;
//                this.rabbitButtAngora1.xRot = 0.0F;
//                this.rabbitButtAngora2.xRot = 0.0F;
//                this.rabbitButtAngora3.xRot = 0.0F;
//                this.buttAngora4.xRot = 0.0F;
//                this.rabbitButtTube.xRot = 0.0F;
//            } else {
//                this.rabbitLeftFoot.xRot = 3.0F + this.jumpRotation * 80.0F * ((float) Math.PI / 180F);
//                this.rabbitRightFoot.xRot = 3.0F + this.jumpRotation * 80.0F * ((float) Math.PI / 180F);
//                this.rabbitLeftCalf.xRot = 2.1F - (this.jumpRotation * +50.0F * ((float) Math.PI / 180F));
//                this.rabbitRightCalf.xRot = 2.1F - (this.jumpRotation * +50.0F * ((float) Math.PI / 180F));
//                this.leftArm.xRot = -1.6F - (this.jumpRotation * -40.0F - 11.0F) * ((float) Math.PI / 180F);
//                this.rightArm.xRot = -1.6F - (this.jumpRotation * -40.0F - 11.0F) * ((float) Math.PI / 180F);
//                this.rabbitButt.xRot = (this.jumpRotation * +30.0F) * ((float) Math.PI / 180F);
//                this.rabbitButtAngora1.xRot = (this.jumpRotation * +30.0F) * ((float) Math.PI / 180F);
//                this.rabbitButtAngora2.xRot = (this.jumpRotation * +30.0F) * ((float) Math.PI / 180F);
//                this.rabbitButtAngora3.xRot = (this.jumpRotation * +30.0F) * ((float) Math.PI / 180F);
//                this.buttAngora4.xRot = (this.jumpRotation * +30.0F) * ((float) Math.PI / 180F);
//                this.rabbitBody.xRot = (this.jumpRotation * +15.0F) * ((float) Math.PI / 180F);
//                this.earHelper.xRot = ((this.jumpRotation * +15.0F) * ((float) Math.PI / 180F)) + this.headLeft.xRot + 0.4F;
////            this.LionEarParent.rotateAngleX = ((this.jumpRotation * +15.0F) * ((float)Math.PI / 180F)) + this.headLeft.rotateAngleX + 0.4F;
////            this.LionEarParent.rotateAngleX = ((this.jumpRotation * +15.0F) * ((float)Math.PI / 180F)) + this.headLeft.rotateAngleX + 0.4F;
////            this.DwarfParent.rotateAngleX = ((this.jumpRotation * +15.0F) * ((float)Math.PI / 180F)) + this.headLeft.rotateAngleX + 0.4F;
//                this.rabbitBodyAngora1.xRot = (this.jumpRotation * +15.0F) * ((float) Math.PI / 180F);
//                this.rabbitBodyAngora2.xRot = (this.jumpRotation * +15.0F) * ((float) Math.PI / 180F);
//                this.rabbitBodyAngora3.xRot = (this.jumpRotation * +15.0F) * ((float) Math.PI / 180F);
//                this.rabbitBodyAngora4.xRot = (this.jumpRotation * +15.0F) * ((float) Math.PI / 180F);
//            }
//            this.rabbitLeftThigh.xRot = (this.jumpRotation * 50.0F - 21.0F) * ((float) Math.PI / 180F);
//            this.rabbitRightThigh.xRot = (this.jumpRotation * 50.0F - 21.0F) * ((float) Math.PI / 180F);
//
//            ModelHelper.copyModelPositioning(this.rabbitButt, this.rabbitTail);
//            ModelHelper.copyModelPositioning(this.rabbitBody, this.lionHeadManeDouble);
//            this.lionHeadManeDouble.rotationPointZ = this.lionHeadManeDouble.rotationPointZ + 0.5F;
//            ModelHelper.copyModelRotations(this.headLeft, this.lionHeadManeDouble, 0.6F);
//            ModelHelper.copyModelPositioning(this.lionHeadManeDouble, this.lionHeadManeSingle);
//
//            this.rabbitNose.y = 14.2F + (float) entityIn.getNoseTwitch() / 4.0F;
//
//            ModelHelper.copyModelPositioning(rabbitNose, rabbitNoseDwarf);
//        }
//    }
//
//    private class RabbitModelData {
//        RabbitPhenotype rabbitPhenotype;
//        String birthTime;
//        int coatlength = 0;
//        boolean sleeping = false;
//        int blink = 0;
//        boolean collar = false;
//        int lastAccessed = 0;
//        int dataReset = 0;
//        long clientGameTime = 0;
//        int adultAge = 0;
//    }
//
//    private RabbitModelData getRabbitModelData() {
//        if (this.currentRabbit == null || !rabbitModelDataCache.containsKey(this.currentRabbit)) {
//            return new RabbitModelData();
//        }
//        return rabbitModelDataCache.get(this.currentRabbit);
//    }
//
//    private RabbitModelData getCreateRabbitModelData(T enhancedRabbit) {
//        clearCacheTimer++;
//        if(clearCacheTimer > 50000) {
//            rabbitModelDataCache.values().removeIf(value -> value.lastAccessed==1);
//            for (RabbitModelData rabbitModelData : rabbitModelDataCache.values()){
//                rabbitModelData.lastAccessed = 1;
//            }
//            clearCacheTimer = 0;
//        }
//
//        if (rabbitModelDataCache.containsKey(enhancedRabbit.getId())) {
//            RabbitModelData rabbitModelData = rabbitModelDataCache.get(enhancedRabbit.getId());
//            rabbitModelData.lastAccessed = 0;
//            rabbitModelData.dataReset++;
//            rabbitModelData.coatlength = enhancedRabbit.getCoatLength();
//            rabbitModelData.sleeping = enhancedRabbit.isAnimalSleeping();
//            rabbitModelData.collar = hasCollar(enhancedRabbit.getEnhancedInventory());
//            rabbitModelData.blink = enhancedRabbit.getBlink();
//            rabbitModelData.birthTime = enhancedRabbit.getBirthTime();
//            rabbitModelData.clientGameTime = (((ClientLevel)enhancedRabbit.level).getLevelData()).getGameTime();
//
//            return rabbitModelData;
//        } else {
//            RabbitModelData rabbitModelData = new RabbitModelData();
//            if (enhancedRabbit.getSharedGenes()!=null) {
//                rabbitModelData.rabbitPhenotype = new RabbitPhenotype(enhancedRabbit.getSharedGenes().getAutosomalGenes(), enhancedRabbit.getAnimalSize());
//            }
//            rabbitModelData.coatlength = enhancedRabbit.getCoatLength();
//            rabbitModelData.sleeping = enhancedRabbit.isAnimalSleeping();
//            rabbitModelData.blink = enhancedRabbit.getBlink();
//            rabbitModelData.birthTime = enhancedRabbit.getBirthTime();
//            rabbitModelData.collar = hasCollar(enhancedRabbit.getEnhancedInventory());
//            rabbitModelData.clientGameTime = (((ClientLevel)enhancedRabbit.level).getLevelData()).getGameTime();
//            rabbitModelData.adultAge = EanimodCommonConfig.COMMON.adultAgeRabbit.get();
//
//            if(rabbitModelData.rabbitPhenotype != null) {
//                rabbitModelDataCache.put(enhancedRabbit.getId(), rabbitModelData);
//            }
//
//            return rabbitModelData;
//        }
//    }
//
//    private boolean hasCollar(SimpleContainer inventory) {
//        for (int i = 1; i < 6; i++) {
//            if (inventory.getItem(i).getItem() instanceof CustomizableCollar) {
//                return true;
//            }
//        }
//        return false;
//    }
//
//    private class RabbitPhenotype implements Phenotype{
//        boolean dwarf = false;
//        LionsMane lionsmane = LionsMane.NONE;
//        float size;
//        float lop = 0;
//
//        RabbitPhenotype(int[] gene, float size) {
//            if (gene[34] == 2 || gene[35] == 2) {
//                this.dwarf = true;
//                size = 0.3F + ((size - 0.3F)/2F);
//            }
//
//            if (gene[24] == 2 || gene[25] == 2) {
//                this.lionsmane = gene[24] == gene[25] ? LionsMane.DOUBLE : LionsMane.SINGLE;
//            }
//
//            float lop = 0;
//
//            // genes 36 to 43
//            if (gene[36] == 3 && gene[37] == 3) {
//                lop = 0.25F;
//            } else {
//                if (gene[36] == 2) {
//                    lop = 0.1F;
//                }
//                if (gene[37] == 2) {
//                    lop = 0.1F;
//                }
//            }
//            if (gene[38] == 2 && gene[39] == 2) {
//                lop = lop * 4.0F;
//            }
//
//            lop = lop + ((gene[40] - 1) * 0.1F);
//            lop = lop + ((gene[41] - 1) * 0.1F);
//
//            if (gene[42] == 3 && gene[43] == 3) {
//                lop = lop + 0.1F;
//            }
//
//            this.lop = lop >= 0.75F ? 1.0F : 0.0F;
//
//            this.size = size;
//        }
//
//    }
//
//    private enum LionsMane {
//        DOUBLE,
//        SINGLE,
//        NONE
//    }
//}
