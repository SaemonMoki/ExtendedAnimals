package mokiyoki.enhancedanimals.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import mokiyoki.enhancedanimals.entity.EnhancedSheep;
import mokiyoki.enhancedanimals.entity.EntityState;
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
public class ModelEnhancedSheep<T extends EnhancedSheep> extends EnhancedAnimalModel<T> {
    protected WrappedModelPart theSheep;

    protected WrappedModelPart theHead;
    protected WrappedModelPart theHornLeft;
    protected WrappedModelPart theHornRight;
    protected WrappedModelPart theEarLeft;
    protected WrappedModelPart theEarRight;
    protected WrappedModelPart theNeck;
    protected WrappedModelPart theBody;
    protected WrappedModelPart theLegFrontLeft;
    protected WrappedModelPart theLegFrontLeftTop;
    protected WrappedModelPart theLegFrontLeftBottom;
    protected WrappedModelPart theLegFrontRight;
    protected WrappedModelPart theLegFrontRightTop;
    protected WrappedModelPart theLegFrontRightBottom;
    protected WrappedModelPart theLegBackLeft;
    protected WrappedModelPart theLegBackLeftTop;
    protected WrappedModelPart theLegBackLeftBottom;
    protected WrappedModelPart theLegBackRight;
    protected WrappedModelPart theLegBackRightTop;
    protected WrappedModelPart theLegBackRightBottom;
    protected WrappedModelPart theTail;

    protected WrappedModelPart head;
    protected WrappedModelPart nose;

    protected WrappedModelPart earLeft;

    protected WrappedModelPart earRight;

    protected WrappedModelPart neck;
    protected WrappedModelPart body;

    protected WrappedModelPart udder;
    protected WrappedModelPart nippleLeft;
    protected WrappedModelPart nippleRight;

    private WrappedModelPart legFrontLeftTop;
    private WrappedModelPart legFrontRightTop;
    private WrappedModelPart legBackLeftTop;
    private WrappedModelPart legBackRightTop;

    private WrappedModelPart legFrontLeftBottom;
    private WrappedModelPart legFrontRightBottom;
    private WrappedModelPart legBackLeftBottom;
    private WrappedModelPart legBackRightBottom;

    private WrappedModelPart tailBase;
    private WrappedModelPart tailMiddle;
    private WrappedModelPart tailTip;

    private WrappedModelPart hornL[] = new WrappedModelPart[19];
    private WrappedModelPart hornR[] = new WrappedModelPart[19];


    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition base = meshdefinition.getRoot().addOrReplaceChild("base", CubeListBuilder.create(), PartPose.ZERO);
        PartDefinition bSheep = base.addOrReplaceChild("bSheep", CubeListBuilder.create(), PartPose.ZERO);
        PartDefinition bBody = bSheep.addOrReplaceChild("bBody", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, -6.0F));
        PartDefinition bNeck = bSheep.addOrReplaceChild("bNeck", CubeListBuilder.create(), PartPose.offset(0.0F, 11.0F, -1.0F));
        PartDefinition bHead = bSheep.addOrReplaceChild("bHead", CubeListBuilder.create(), PartPose.offset(0.0F, -10.0F, 1.0F));
        PartDefinition bEarLeft = bSheep.addOrReplaceChild("bEarL", CubeListBuilder.create(), PartPose.offset(2.5F, 2.0F, -1.0F));
        PartDefinition bEarRight = bSheep.addOrReplaceChild("bEarR", CubeListBuilder.create(), PartPose.offset(-2.5F, 2.0F, -1.0F));
        PartDefinition bLegFrontLeft = bSheep.addOrReplaceChild("bLegFL", CubeListBuilder.create(), PartPose.offset(-4.0F, 11.0F, -6.0F));
        bSheep.addOrReplaceChild("bLegFLT", CubeListBuilder.create(), PartPose.offset(0.0F, 3.0F, 1.0F));
        bSheep.addOrReplaceChild("bLegFLB", CubeListBuilder.create(), PartPose.offset(0.0F, 5.0F, -1.0F));
        PartDefinition bLegFrontRight = bSheep.addOrReplaceChild("bLegFR", CubeListBuilder.create(), PartPose.offset(1.0F, 11.0F, -6.0F));
        bSheep.addOrReplaceChild("bLegFRT", CubeListBuilder.create(), PartPose.offset(0.0F, 3.0F, 1.0F));
        bSheep.addOrReplaceChild("bLegFRB", CubeListBuilder.create(), PartPose.offset(0.0F, 5.0F, -1.0F));
        PartDefinition bLegBackLeft = bSheep.addOrReplaceChild("bLegBL", CubeListBuilder.create(), PartPose.offset(-4.0F, 11.0F, 7.0F));
        bSheep.addOrReplaceChild("bLegBLT", CubeListBuilder.create(), PartPose.offset(0.0F, 3.0F, -2.0F));
        bSheep.addOrReplaceChild("bLegBLB", CubeListBuilder.create(), PartPose.offset(0.0F, 5.0F, 1.0F));
        PartDefinition bLegBackRight = bSheep.addOrReplaceChild("bLegBR", CubeListBuilder.create(), PartPose.offset(1.0F, 11.0F, 7.0F));
        bSheep.addOrReplaceChild("bLegBRT", CubeListBuilder.create(), PartPose.offset(0.0F, 3.0F, -2.0F));
        bSheep.addOrReplaceChild("bLegBRB", CubeListBuilder.create(), PartPose.offset(0.0F, 5.0F, 1.0F));
        PartDefinition bTail = bSheep.addOrReplaceChild("bTail", CubeListBuilder.create(), PartPose.offset(0.0F, 9.0F, 14.0F));
        PartDefinition bHornL = bSheep.addOrReplaceChild("bHornL", CubeListBuilder.create(), PartPose.offset(-0.75F, 3.0F, 0.0F));
        PartDefinition bHornR = bSheep.addOrReplaceChild("bHornR", CubeListBuilder.create(), PartPose.offset(0.75F, 3.0F, 0.0F));

        bHead.addOrReplaceChild("eyes", CubeListBuilder.create()
                        .texOffs(12, 8)
                        .addBox(1.5F, 1.0F, -4.0F, 1, 1, 2, new CubeDeformation(0.01F))
                        .texOffs( 12, 13)
                        .addBox(-2.5F, 1.0F, -4.0F, 1, 1, 2, new CubeDeformation(0.01F)),
                PartPose.ZERO
        );

        bHead.addOrReplaceChild("head", CubeListBuilder.create()
                        .texOffs(0, 0)
                        .addBox(-2.5F, 0.0F, -4.0F, 5, 4, 4),
                PartPose.ZERO
        );
        bHead.addOrReplaceChild("nose", CubeListBuilder.create()
                        .texOffs(0, 8)
                        .addBox(-2.0F, 0.0F, -3.0F, 4, 3, 3),
                PartPose.offset(0.0F, 0.0F, -4.0F)
        );

//        bHead.addOrReplaceChild("jaw", CubeListBuilder.create()
//                        .texOffs(49, 22)
//                        .addBox(-2.0F, -5.0F, -3.0F, 4, 6, 3),
//                PartPose.offset(0.0F, 1.0F, -4.0F)
//        );

        bEarLeft.addOrReplaceChild("earL", CubeListBuilder.create()
                        .texOffs(57, 4)
                        .addBox(0.0F, -2.0F, 0.0F, 3, 2, 1, new CubeDeformation(0.01F)),
                PartPose.ZERO
        );
        bEarLeft.addOrReplaceChild("earTL", CubeListBuilder.create()
                        .texOffs(57, 4)
                        .addBox(0.0F, -2.0F, 0.0F, 3, 2, 1, new CubeDeformation(0.01F)),
                PartPose.ZERO
        );

        bEarRight.addOrReplaceChild("earR", CubeListBuilder.create()
                        .texOffs(50, 4)
                        .addBox(-3.0F, -2.0F, 0.0F, 3, 2, 1, new CubeDeformation(0.01F)),
                PartPose.ZERO
        );
        bEarRight.addOrReplaceChild("earTR", CubeListBuilder.create()
                        .texOffs(50, 4)
                        .addBox(-3.0F, -2.0F, 0.0F, 3, 2, 1, new CubeDeformation(0.01F)),
                PartPose.ZERO
        );

        bNeck.addOrReplaceChild("neck", CubeListBuilder.create()
                        .texOffs(34, 0)
                        .addBox(-2.0F, -9.0F, -2.0F, 4, 9, 4),
                PartPose.ZERO
        );

        bBody.addOrReplaceChild("body", CubeListBuilder.create()
                        .texOffs(2,0)
                        .addBox(-4.0F, 8.0F, -2.0F, 8, 6, 16),
                PartPose.ZERO
        );

        bLegFrontLeft.addOrReplaceChild("legFLT", CubeListBuilder.create()
                        .texOffs( 0, 22)
                        .addBox(0.0F, 0.0F, -3.0F, 3, 5, 3),
                PartPose.ZERO
        );
        bLegFrontRight.addOrReplaceChild("legFRT", CubeListBuilder.create()
                        .texOffs(12, 22)
                        .addBox(0.0F, 0.0F, -3.0F, 3, 5, 3),
                PartPose.ZERO
        );
        bLegBackLeft.addOrReplaceChild("legBLT", CubeListBuilder.create()
                        .texOffs(24, 22)
                        .addBox(0.0F, 0.0F, 0.0F, 3, 5, 3),
                PartPose.ZERO
        );
        bLegBackRight.addOrReplaceChild("legBRT", CubeListBuilder.create()
                        .texOffs(36, 22)
                        .addBox(0.0F, 0.0F, 0.0F, 3, 5, 3),
                PartPose.ZERO
        );

        bLegFrontLeft.addOrReplaceChild("legFLB", CubeListBuilder.create()
                        .texOffs( 0, 22)
                        .addBox(0.0F, 0.0F, -2.0F, 3, 5, 3, new CubeDeformation(0.01F)),
                PartPose.ZERO
        );
        bLegFrontRight.addOrReplaceChild("legFRB", CubeListBuilder.create()
                        .texOffs(12, 22)
                        .addBox(0.0F, 0.0F, -2.0F, 3, 5, 3, new CubeDeformation(0.01F)),
                PartPose.ZERO
        );
        bLegBackLeft.addOrReplaceChild("legBLB", CubeListBuilder.create()
                        .texOffs(24, 22)
                        .addBox(0.0F, 0.0F, -1.0F, 3, 5, 3, new CubeDeformation(0.01F)),
                PartPose.ZERO
        );
        bLegBackRight.addOrReplaceChild("legBRB", CubeListBuilder.create()
                        .texOffs(36, 22)
                        .addBox(0.0F, 0.0F, -1.0F, 3, 5, 3, new CubeDeformation(0.01F)),
                PartPose.ZERO
        );

        bTail.addOrReplaceChild("tailB", CubeListBuilder.create()
                        .texOffs(50, 0)
                        .addBox(-1.0F, 0.0F, 0.0F, 2, 3, 1),
                PartPose.ZERO
        );
        bTail.addOrReplaceChild("tailM", CubeListBuilder.create()
                        .texOffs(56, 0)
                        .addBox(-0.5F, 0.0F, 0.0F, 1, 3, 1),
                PartPose.offset(0.0F, 3.0F, 0.0F)
        );
        bTail.addOrReplaceChild("tailT", CubeListBuilder.create()
                        .texOffs(60, 0)
                        .addBox(-0.5F, 0.0F, 0.0F, 1, 3, 1),
                PartPose.offset(0.0F, 3.0F, 0.0F)
        );

        bBody.addOrReplaceChild("udder", CubeListBuilder.create()
                        .texOffs(46, 55)
                        .addBox(-2.0F, 0.0F, 0.0F, 4, 4, 5),
                PartPose.offset(0.0F, 12.0F, 9.5F)
        );
        bBody.addOrReplaceChild("nippleL", CubeListBuilder.create()
                        .texOffs(46, 55)
                        .addBox(-0.5F, 0.0F, -0.5F, 1, 3, 1),
                PartPose.ZERO
        );
        bBody.addOrReplaceChild("nippleR", CubeListBuilder.create()
                        .texOffs(60, 55)
                        .addBox(-0.5F, 0.0F, -0.5F, 1, 3, 1),
                PartPose.ZERO
        );

        for (int i = 0; i < 19; i++) {
            float scale = i < 10 ? -0.5F : -(0.5F + (((float)(i-10)*0.75F)/9F));
            float tran = i < 10 ? -1.95F : -(1.8F - (((i-10)*1.4F)/9F));
            CubeListBuilder hornBitL = CubeListBuilder.create()
                    .texOffs(50, 12)
                    .addBox(-3.0F, -3.0F, -1.5F, 3, 3, 3, new CubeDeformation(scale));
            CubeListBuilder hornBitR = CubeListBuilder.create()
                    .texOffs(50, 12)
                    .addBox(0.0F, -3.0F, -1.5F, 3, 3, 3, new CubeDeformation(scale));
            bHornL.addOrReplaceChild("hornL"+i, hornBitL, PartPose.offset(0.0F,  tran, 0.0F));
            bHornR.addOrReplaceChild("hornR"+i, hornBitR, PartPose.offset(0.0F, tran, 0.0F));
        }

        return LayerDefinition.create(meshdefinition, 128, 64);
    }

    public ModelEnhancedSheep(ModelPart modelPart) {
        super(modelPart);
        ModelPart base = modelPart.getChild("base");
        ModelPart bSheep = base.getChild("bSheep");
        ModelPart bBody = bSheep.getChild("bBody");
        ModelPart bNeck = bSheep.getChild("bNeck");
        ModelPart bHead = bSheep.getChild("bHead");
        ModelPart bEarLeft = bSheep.getChild("bEarL");
        ModelPart bEarRight = bSheep.getChild("bEarR");
        ModelPart bLegFL = bSheep.getChild("bLegFL");
        ModelPart bLegFLT = bSheep.getChild("bLegFLT");
        ModelPart bLegFLB = bSheep.getChild("bLegFLB");
        ModelPart bLegFR = bSheep.getChild("bLegFR");
        ModelPart bLegFRT = bSheep.getChild("bLegFRT");
        ModelPart bLegFRB = bSheep.getChild("bLegFRB");
        ModelPart bLegBL = bSheep.getChild("bLegBL");
        ModelPart bLegBLT = bSheep.getChild("bLegBLT");
        ModelPart bLegBLB = bSheep.getChild("bLegBLB");
        ModelPart bLegBR = bSheep.getChild("bLegBR");
        ModelPart bLegBRT = bSheep.getChild("bLegBRT");
        ModelPart bLegBRB = bSheep.getChild("bLegBRB");
        ModelPart bTail = bSheep.getChild("bTail");
        ModelPart bHornL = bSheep.getChild("bHornL");
        ModelPart bHornR = bSheep.getChild("bHornR");

        this.theSheep = new WrappedModelPart(bSheep, "bSheep");
        this.theBody = new WrappedModelPart(bBody, "bBody");
        this.theNeck = new WrappedModelPart(bNeck, "bNeck");
        this.theHead = new WrappedModelPart(bHead, "bHead");
        this.theEarLeft = new WrappedModelPart(bEarLeft, "bEarL");
        this.theEarRight = new WrappedModelPart(bEarRight, "bEarR");
        this.theLegFrontLeft = new WrappedModelPart(bLegFL, "bLegFL");
        this.theLegFrontLeftTop = new WrappedModelPart(bLegFLT, "bLegFLT");
        this.theLegFrontLeftBottom = new WrappedModelPart(bLegFLB, "bLegFLB");
        this.theLegFrontRight = new WrappedModelPart(bLegFR, "bLegFR");
        this.theLegFrontRightTop = new WrappedModelPart(bLegFRT, "bLegFRT");
        this.theLegFrontRightBottom = new WrappedModelPart(bLegFRB, "bLegFRB");
        this.theLegBackLeft = new WrappedModelPart(bLegBL, "bLegBL");
        this.theLegBackLeftTop = new WrappedModelPart(bLegBLT, "bLegBLT");
        this.theLegBackLeftBottom = new WrappedModelPart(bLegBLB, "bLegBLB");
        this.theLegBackRight = new WrappedModelPart(bLegBR, "bLegBR");
        this.theLegBackRightTop = new WrappedModelPart(bLegBRT, "bLegBRT");
        this.theLegBackRightBottom = new WrappedModelPart(bLegBRB, "bLegBRB");
        this.theTail = new WrappedModelPart(bTail, "bTail");
        this.theHornLeft = new WrappedModelPart(bHornL, "bHornL");
        this.theHornRight = new WrappedModelPart(bHornR, "bHornR");

        this.eyes = new WrappedModelPart("eyes", bHead);

        this.head = new WrappedModelPart("head", bHead);

        this.nose = new WrappedModelPart("nose", bHead);

        this.earLeft = new WrappedModelPart("earL", bEarLeft);

        this.earRight = new WrappedModelPart("earR", bEarRight);

        this.neck = new WrappedModelPart("neck", bNeck);

        this.body = new WrappedModelPart("body", bBody);

        this.udder = new WrappedModelPart("udder", bBody);
        this.nippleLeft = new WrappedModelPart("nippleL", bBody);
        this.nippleRight = new WrappedModelPart("nippleR", bBody);
        
        this.legFrontLeftTop = new WrappedModelPart("legFLT", bLegFL);
        this.legFrontRightTop = new WrappedModelPart("legFRT", bLegFR);
        this.legBackLeftTop = new WrappedModelPart("legBLT", bLegBL);
        this.legBackRightTop = new WrappedModelPart("legBRT", bLegBR);

        this.legFrontLeftBottom = new WrappedModelPart("legFLB", bLegFL);
        this.legFrontRightBottom = new WrappedModelPart("legFRB", bLegFR);
        this.legBackLeftBottom = new WrappedModelPart("legBLB", bLegBL);
        this.legBackRightBottom = new WrappedModelPart("legBRB", bLegBR);

        this.tailBase = new WrappedModelPart("tailB", bTail);
        this.tailMiddle = new WrappedModelPart("tailM", bTail);
        this.tailTip = new WrappedModelPart("tailT", bTail);

        for (int i = 0; i < 19;i++) {
            this.hornL[i] = new WrappedModelPart("hornL"+i, bHornL);
            this.hornR[i] = new WrappedModelPart("hornR"+i, bHornR);
        }

        this.theSheep.addChild(this.theBody);

        this.theSheep.addChild(this.theLegFrontLeft);
        this.theLegFrontLeft.addChild(this.theLegFrontLeftTop);
        this.theLegFrontLeftTop.addChild(this.theLegFrontLeftBottom);
        this.theSheep.addChild(this.theLegFrontRight);
        this.theLegFrontRight.addChild(this.theLegFrontRightTop);
        this.theLegFrontRightTop.addChild(this.theLegFrontRightBottom);
        this.theSheep.addChild(this.theLegBackLeft);
        this.theLegBackLeft.addChild(this.theLegBackLeftTop);
        this.theLegBackLeftTop.addChild(this.theLegBackLeftBottom);

        this.theSheep.addChild(this.theLegBackRight);
        this.theLegBackRight.addChild(this.theLegBackRightTop);
        this.theLegBackRightTop.addChild(this.theLegBackRightBottom);
        this.theBody.addChild(this.theNeck);
        this.theBody.addChild(this.theTail);
        this.theNeck.addChild(this.theHead);
        this.theHead.addChild(this.theEarLeft);
        this.theHead.addChild(this.theEarRight);
        this.theHead.addChild(this.theHornLeft);
        this.theHead.addChild(this.theHornRight);

        this.theHead.addChild(this.head);
        this.theHead.addChild(this.eyes);
        this.theHead.addChild(this.nose);

        this.theEarLeft.addChild(this.earLeft);
        this.theEarRight.addChild(this.earRight);

        this.theNeck.addChild(this.neck);

        this.theBody.addChild(this.body);

        this.theBody.addChild(this.udder);
        this.udder.addChild(this.nippleLeft);
        this.udder.addChild(this.nippleRight);

        this.theTail.addChild(this.tailBase);
        this.tailBase.addChild(this.tailMiddle);
        this.tailMiddle.addChild(this.tailTip);

        this.theLegFrontLeftTop.addChild(this.legFrontLeftTop);
        
        this.theLegFrontLeftBottom.addChild(this.legFrontLeftBottom);

        this.theLegFrontRightTop.addChild(this.legFrontRightTop);

        this.theLegFrontRightBottom.addChild(this.legFrontRightBottom);

        this.theLegBackLeftTop.addChild(this.legBackLeftTop);

        this.theLegBackLeftBottom.addChild(this.legBackLeftBottom);

        this.theLegBackRightTop.addChild(this.legBackRightTop);

        this.theLegBackRightBottom.addChild(this.legBackRightBottom);

        this.theHornLeft.addChild(this.hornL[0]);
        this.theHornRight.addChild(this.hornR[0]);
        for (int i = 0; i < 18; i++) {
            this.hornL[i].addChild(this.hornL[i+1]);
            this.hornR[i].addChild(this.hornR[i+1]);
        }
    }

    private void resetCubes() {

    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        SheepModelData sheepModelData = getSheepModelData();
        SheepPhenotype sheep = sheepModelData.getPhenotype();

        resetCubes();

        if (sheep!=null) {
            super.renderToBuffer(poseStack, vertexConsumer, packedLightIn, packedOverlayIn, red, green, blue, alpha);

            this.udder.show(sheepModelData.bagSize >= 0.0F);

            poseStack.pushPose();
            poseStack.scale(1.0F, 1.0F, 1.0F);
            poseStack.translate(0.0F, 0.0F, 0.0F);

            gaRender(this.theSheep, null, poseStack, vertexConsumer, packedLightIn, packedOverlayIn, red, green, blue, alpha);

            poseStack.popPose();
        }
    }

    protected Map<String, Vector3f> saveAnimationValues(SheepModelData data, SheepPhenotype sheep, float hornGrowthAmount) {
        Map<String, Vector3f> map = data.offsets;
        map.put("bSheep_O", new Vector3f(0.0F, this.theSheep.getY(), 0.0F));
        map.put("bSheep", this.getRotationVector(this.theSheep));
        map.put("bBody", this.getRotationVector(this.theBody));
        map.put("bHead", this.getRotationVector(this.theHead));
        map.put("bNeck", this.getRotationVector(this.theNeck));
        map.put("bEarL", this.getRotationVector(this.theEarLeft));
        map.put("bEarR", this.getRotationVector(this.theEarRight));
        map.put("bLegFL", this.getRotationVector(this.theLegFrontLeft));
        map.put("bLegFLT", this.getRotationVector(this.theLegFrontLeftTop));
        map.put("bLegFLB", this.getRotationVector(this.theLegFrontLeftBottom));
        map.put("bLegFR", this.getRotationVector(this.theLegFrontRight));
        map.put("bLegFRT", this.getRotationVector(this.theLegFrontRightTop));
        map.put("bLegFRB", this.getRotationVector(this.theLegFrontRightBottom));
        map.put("bLegBL", this.getRotationVector(this.theLegBackLeft));
        map.put("bLegBLT", this.getRotationVector(this.theLegBackLeftTop));
        map.put("bLegBLB", this.getRotationVector(this.theLegBackLeftBottom));
        map.put("bLegBR", this.getRotationVector(this.theLegBackRight));
        map.put("bLegBRT", this.getRotationVector(this.theLegBackRightTop));
        map.put("bLegBRB", this.getRotationVector(this.theLegBackRightBottom));
        map.put("bTail", this.getRotationVector(this.theTail));
        map.put("tailB", this.getRotationVector(this.tailBase));
        map.put("tailM", this.getRotationVector(this.tailMiddle));
        map.put("tailT", this.getRotationVector(this.tailTip));
        return map;
    }

    private void saveHornRotations(SheepPhenotype sheep, Map<String, Vector3f> map) {
        float poly = sheep.polyHorns? -0.001F : 1.0F;
        if (sheep.hornType != HornType.POLLED) {
            for (int i = 0; i <= 19; i++) {
                map.put("hL"+i, new Vector3f(-(sheep.hornGeneticsX[i] + (0.2F*poly)), sheep.hornGeneticsY[i], -(sheep.hornGeneticsZ[i] - (0.3F*poly))));
                map.put("hR"+i, new Vector3f(-(sheep.hornGeneticsX[i] + (0.2F*poly)), -sheep.hornGeneticsY[i], sheep.hornGeneticsZ[i] - (0.3F*poly)));
            }
        }
    }

    private void updateHornRotations(SheepPhenotype sheep, float hornGrowthAmount, Map<String, Vector3f> map) {
        Float[] hornY = {0.0F, -2.0F, -2.0F, -2.0F, -2.0F, -1.8F, -1.6F, -1.4F, -1.2F, -1.0F};
        int lengthL = sheep.leftHornLength;
        int lengthR = sheep.rightHornLength;
        lengthL = lengthL + ((int)((12-lengthL) * (1.0F-(hornGrowthAmount))));
        lengthR = lengthR + ((int)((12-lengthR) * (1.0F-(hornGrowthAmount))));
        for (int i = 0; i <= 9; i++) {
            if (map.containsKey("hL"+i)) {
                if (lengthL <= i) {
                    map.put("hornL" + i, map.get("hL" + i));
                    map.remove("hL" + i);
                }
                map.put("hornPosL"+(i), new Vector3f(0.0F, hornY[i], 0.0F));
            }
            if (map.containsKey("hR"+i)) {
                if (lengthR <= i){
                    map.put("hornR" + i, map.get("hR" + i));
                    map.remove("hR" + i);
                }
                map.put("hornPosR"+(i), new Vector3f(0.0F, hornY[i], 0.0F));
            }
        }
    }

    private void setupInitialAnimationValues(SheepModelData data, SheepPhenotype sheep, float hornGrowthAmount) {
        Map<String, Vector3f> map = data.offsets;
        if (map.isEmpty()) {
            this.theSheep.setY(0.0F);
            this.theNeck.setRotation(Mth.HALF_PI, 0.0F, 0.0F);

        } else {
            this.theSheep.setY(map.get("bSheep_O").y());
            this.setRotationFromVector(this.theSheep, map.get("bSheep"));
            this.setRotationFromVector(this.theBody, map.get("bBody"));
            this.setRotationFromVector(this.theHead, map.get("bHead"));
            this.setRotationFromVector(this.theNeck, map.get("bNeck"));
            this.setRotationFromVector(this.theEarLeft, map.get("bEarL"));
            this.setRotationFromVector(this.theEarRight, map.get("bEarR"));
            this.setRotationFromVector(this.theLegFrontLeft, map.get("bLegFL"));
            this.setRotationFromVector(this.theLegFrontLeftTop, map.get("bLegFLT"));
            this.setRotationFromVector(this.theLegFrontLeftBottom, map.get("bLegFLB"));
            this.setRotationFromVector(this.theLegFrontRight, map.get("bLegFR"));
            this.setRotationFromVector(this.theLegFrontRightTop, map.get("bLegFRT"));
            this.setRotationFromVector(this.theLegFrontRightBottom, map.get("bLegFRB"));
            this.setRotationFromVector(this.theLegBackLeft, map.get("bLegBL"));
            this.setRotationFromVector(this.theLegBackLeftTop, map.get("bLegBLT"));
            this.setRotationFromVector(this.theLegBackLeftBottom, map.get("bLegBLB"));
            this.setRotationFromVector(this.theLegBackRight, map.get("bLegBR"));
            this.setRotationFromVector(this.theLegBackRightTop, map.get("bLegBRT"));
            this.setRotationFromVector(this.theLegBackRightBottom, map.get("bLegBRB"));
            this.setRotationFromVector(this.theTail, map.get("bTail"));
            this.setRotationFromVector(this.tailBase, map.get("tailB"));
            this.setRotationFromVector(this.tailMiddle, map.get("tailM"));
            this.setRotationFromVector(this.tailTip, map.get("tailT"));
        }
        saveAnimationValues(data, sheep, hornGrowthAmount);
    }

    @Override
    public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.currentAnimal = entityIn.getId();
        SheepModelData sheepModelData = getCreateSheepModelData(entityIn);
        SheepPhenotype sheep = sheepModelData.getPhenotype();
        float drive = ageInTicks + (1000 * sheepModelData.random);

        if (sheep != null) {
            setupInitialAnimationValues(sheepModelData, sheep, sheepModelData.hornGrowth);

            boolean isMoving = entityIn.getDeltaMovement().horizontalDistanceSqr() > 1.0E-7D || entityIn.getXRot() != entityIn.xRotO || entityIn.getYRot() != entityIn.yRotO || entityIn.xOld != entityIn.getX() || entityIn.zOld != entityIn.getZ();

            if (sheepModelData.sleeping && !isMoving) {
                sleepingAnimation();
            } else {
                if (isMoving) {
                    movingAnimation(sheepModelData.growthAmount, limbSwing, limbSwingAmount);
                } else {
                    standingAnimation(netHeadYaw, headPitch);
                }
            }

            saveAnimationValues(sheepModelData, sheep, sheepModelData.hornGrowth);
        }

    }

    private void sleepingAnimation() {
        this.theSheep.setY(Mth.lerp(0.05F, this.theSheep.getY(), 9.0F));

        this.theHead.setXRot(this.lerpTo(this.theHead.getXRot(), 0.0F));
        this.theHead.setYRot(this.lerpTo(this.theHead.getYRot(), 0.0F));
        this.theNeck.setXRot(this.lerpTo(this.theNeck.getXRot(), Mth.PI * 0.55F));
        this.theNeck.setYRot(this.lerpTo(this.theNeck.getYRot(), 0.8F));

        this.theLegFrontLeft.setXRot(this.lerpTo(this.theLegFrontLeft.getXRot(), 0.0F));
        this.theLegFrontRight.setXRot(this.lerpTo(this.theLegFrontRight.getXRot(), 0.0F));

        this.theLegBackLeft.setXRot(this.lerpTo(this.theLegBackLeft.getXRot(), -Mth.HALF_PI*0.75F));
        this.theLegBackRight.setXRot(this.lerpTo(this.theLegBackRight.getXRot(), -Mth.HALF_PI*0.75F));

        this.theLegBackLeftTop.setXRot(this.lerpTo(this.theLegBackLeftTop.getXRot(), Mth.HALF_PI + -this.theLegBackLeft.getXRot()));
        this.theLegBackRightTop.setXRot(this.lerpTo(this.theLegBackRightTop.getXRot(), Mth.HALF_PI + -this.theLegBackRight.getXRot()));

        this.theLegFrontLeftTop.setXRot(this.lerpTo(this.theLegFrontLeftTop.getXRot(), -Mth.HALF_PI));
        this.theLegFrontRightTop.setXRot(this.lerpTo(this.theLegFrontRightTop.getXRot(), -Mth.HALF_PI));

        this.theLegBackLeftTop.setXRot(this.lerpTo(this.theLegBackLeftTop.getXRot(), Mth.HALF_PI));
        this.theLegBackRightTop.setXRot(this.lerpTo(this.theLegBackRightTop.getXRot(), Mth.HALF_PI));

        this.theLegFrontLeftBottom.setXRot(this.lerpTo(this.theLegFrontLeftBottom.getXRot(), Mth.PI));
        this.theLegFrontRightBottom.setXRot(this.lerpTo(this.theLegFrontRightBottom.getXRot(), Mth.PI));

        this.theLegBackLeftBottom.setXRot(this.lerpTo(this.theLegBackLeftBottom.getXRot(), -Mth.PI*0.75F));
        this.theLegBackRightBottom.setXRot(this.lerpTo(this.theLegBackRightBottom.getXRot(), -Mth.PI*0.75F));
    }

    private void standingAnimation(float netHeadYaw, float headPitch) {
        this.theSheep.setY(Mth.lerp(0.05F, this.theSheep.getY(), -0.5F));

        netHeadYaw = (netHeadYaw * 0.017453292F);
        headPitch = ((headPitch * 0.017453292F));
        float lookRotX = headPitch*0.65F;
        lookRotX = Math.min(lookRotX, 0.0F);
        float lookRotY = netHeadYaw*0.65F;

        this.theNeck.setXRot(this.lerpTo(this.theNeck.getXRot(), (headPitch-lookRotX)+(Mth.HALF_PI*0.5F)));
        this.theNeck.setYRot(this.lerpTo(this.theNeck.getYRot(), lookRotY));
        this.theHead.setXRot(this.lerpTo(this.theHead.getXRot(), lookRotX));
        this.theHead.setYRot(this.lerpTo(this.theHead.getYRot(), limit(netHeadYaw-lookRotY,Mth.HALF_PI*0.75F)));

        this.theTail.setZRot(this.lerpTo(this.theTail.getZRot(), 0.0F));
        this.tailMiddle.setZRot(this.lerpTo(this.theTail.getZRot(), 0.0F));
        this.tailTip.setZRot(this.lerpTo(this.theTail.getZRot(), 0.0F));

        standingLegsAnimation();
    }

    private void movingAnimation(float age, float limbSwing, float limbSwingAmount) {
        float f = Mth.cos(limbSwing * 0.6662F);
        float f1 = Mth.cos(limbSwing * 0.6662F + Mth.PI);

        this.theTail.setZRot(this.lerpTo(this.theTail.getZRot(), f * (1.3F - (0.9F * age)) * limbSwingAmount));
        this.tailMiddle.setZRot(this.lerpTo(this.theTail.getZRot(), f * (1.4F - (0.9F * age)) * limbSwingAmount));
        this.tailTip.setZRot(this.lerpTo(this.theTail.getZRot(), f * (1.5F - (0.9F * age)) * limbSwingAmount));

        walkingLegsAnimation(limbSwing, limbSwingAmount);
    }

    private void standingLegsAnimation() {

        this.theLegFrontLeft.setXRot(this.lerpTo(this.theLegFrontLeft.getXRot(), 0.0F));
        this.theLegFrontRight.setXRot(this.lerpTo(this.theLegFrontRight.getXRot(), 0.0F));

        this.theLegBackLeft.setXRot(this.lerpTo(this.theLegBackLeft.getXRot(), 0.0F));
        this.theLegBackRight.setXRot(this.lerpTo(this.theLegBackRight.getXRot(), 0.0F));

        this.theLegFrontLeftTop.setXRot(this.lerpTo(this.theLegFrontLeftTop.getXRot(), 0.0F));
        this.theLegFrontRightTop.setXRot(this.lerpTo(this.theLegFrontRightTop.getXRot(), 0.0F));

        this.theLegBackLeftTop.setXRot(this.lerpTo(this.theLegBackLeftTop.getXRot(), 0.0F));
        this.theLegBackRightTop.setXRot(this.lerpTo(this.theLegBackRightTop.getXRot(), 0.0F));

        this.theLegFrontLeftBottom.setXRot(this.lerpTo(this.theLegFrontLeftBottom.getXRot(), 0.0F));
        this.theLegFrontRightBottom.setXRot(this.lerpTo(this.theLegFrontRightBottom.getXRot(), 0.0F));

        this.theLegBackLeftBottom.setXRot(this.lerpTo(this.theLegBackLeftBottom.getXRot(), 0.0F));
        this.theLegBackRightBottom.setXRot(this.lerpTo(this.theLegBackRightBottom.getXRot(), 0.0F));
    }

    private void walkingLegsAnimation(float limbSwing, float limbSwingAmount) {
        float f = (Mth.cos(limbSwing * 0.6662F)) * 1.4F * limbSwingAmount;
        float f1 = (Mth.cos(limbSwing * 0.6662F + Mth.PI) * 1.4F * limbSwingAmount);

        this.theLegFrontLeft.setXRot(this.lerpTo(this.theLegFrontLeft.getXRot(), 0.0F));
        this.theLegFrontRight.setXRot(this.lerpTo(this.theLegFrontRight.getXRot(), 0.0F));
        this.theLegBackLeft.setXRot(this.lerpTo(this.theLegBackLeft.getXRot(), 0.0F));
        this.theLegBackRight.setXRot(this.lerpTo(this.theLegBackRight.getXRot(), 0.0F));

        this.theLegFrontLeftTop.setXRot(f);
        this.theLegFrontRightTop.setXRot(f1);
        this.theLegBackLeftTop.setXRot(f);
        this.theLegBackRightTop.setXRot(f1);

        this.theLegFrontLeftTop.setXRot(this.lerpTo(this.theLegFrontLeftTop.getXRot(), 0.0F));
        this.theLegFrontRightTop.setXRot(this.lerpTo(this.theLegFrontRightTop.getXRot(), 0.0F));

        this.theLegBackLeftTop.setXRot(this.lerpTo(this.theLegBackLeftTop.getXRot(), 0.0F));
        this.theLegBackRightTop.setXRot(this.lerpTo(this.theLegBackRightTop.getXRot(), 0.0F));

        this.theLegFrontLeftBottom.setXRot(this.lerpTo(this.theLegFrontLeftBottom.getXRot(), 0.0F));
        this.theLegFrontRightBottom.setXRot(this.lerpTo(this.theLegFrontRightBottom.getXRot(), 0.0F));

        this.theLegBackLeftBottom.setXRot(this.lerpTo(this.theLegBackLeftBottom.getXRot(), 0.0F));
        this.theLegBackRightBottom.setXRot(this.lerpTo(this.theLegBackRightBottom.getXRot(), 0.0F));
    }

    private class SheepModelData extends AnimalModelData {
        float bagSize = 0.0F;
        float hornGrowth = 0.0F;
        int coatLength = 0;
        public SheepPhenotype getPhenotype() {
            return (SheepPhenotype) this.phenotype;
        }
    }

    private SheepModelData getSheepModelData() {
        return (SheepModelData) getAnimalModelData();
    }

    private SheepModelData getCreateSheepModelData(T enhancedSheep) {
        return (SheepModelData) getCreateAnimalModelData(enhancedSheep);
    }

    @Override
    protected void setInitialModelData(T enhancedSheep) {
        SheepModelData sheepModelData = new SheepModelData();
        setBaseInitialModelData(sheepModelData, enhancedSheep);
    }

    @Override
    protected void additionalModelDataInfo(AnimalModelData animalModelData, T enhancedAnimal) {
        ((SheepModelData) animalModelData).bagSize = (enhancedAnimal.getEntityStatus().equals(EntityState.MOTHER) || enhancedAnimal.getEntityStatus().equals(EntityState.PREGNANT)) ? enhancedAnimal.getBagSize() : -1.0F;
        ((SheepModelData) animalModelData).coatLength = Math.min(enhancedAnimal.getCoatLength(), 15);
    }

    @Override
    protected void additionalUpdateModelDataInfo(AnimalModelData animalModelData, T enhancedAnimal) {
        if (((SheepModelData)animalModelData).hornGrowth != 1.0F) {
            updateHornRotations(((SheepModelData) animalModelData).getPhenotype(), enhancedAnimal.hornGrowthAmount(), animalModelData.offsets);
            ((SheepModelData)animalModelData).hornGrowth = enhancedAnimal.hornGrowthAmount();
        }
        ((SheepModelData) animalModelData).bagSize = (enhancedAnimal.getEntityStatus().equals(EntityState.MOTHER) || enhancedAnimal.getEntityStatus().equals(EntityState.PREGNANT)) ? enhancedAnimal.getBagSize() : -1.0F;
        ((SheepModelData) animalModelData).coatLength = Math.min(enhancedAnimal.getCoatLength(), 15);
    }

    @Override
    protected Phenotype createPhenotype(T enhancedAnimal) {
        return new SheepPhenotype(enhancedAnimal.getSharedGenes().getAutosomalGenes(), enhancedAnimal.getOrSetIsFemale(), enhancedAnimal.getStringUUID().toCharArray());
    }

    protected class SheepPhenotype implements Phenotype {
        int faceWool;
        float hornScale = 1.0F;
        boolean polyHorns;
        HornType hornType;
        private int leftHornLength = 9;
        private int rightHornLength = 9;
        private float[] hornGeneticsX;
        private float[] hornGeneticsY;
        private float[] hornGeneticsZ;


        SheepPhenotype(int[] gene, boolean isFemale, char[] uuid) {
            faceWool = 0;
            if (gene[42] == 1 || gene[43] == 1) {
                if (gene[40] == 1) {
                    faceWool++;
                }
                if (gene[41] == 1) {
                    faceWool++;
                }
                if (gene[38] == 1 || gene[39] == 1) {
                    faceWool++;
                } else if (gene[38] == 3 && gene[39] == 3) {
                    faceWool--;
                }
            }

            if (gene[6] == 2 || gene[7] == 2) {
                this.hornType = gene[6] == 1 || gene[7] == 1 ? HornType.SCURRED : HornType.HORNED;
            } else if (gene[6] != 1 && gene[7] != 1){
                // genderfied horns
                this.hornType = isFemale ? HornType.POLLED : HornType.HORNED;
            }

            this.polyHorns = (gene[36] == 1 || gene[37] == 1) && (isFemale ? uuid[2] - 48 <= 3 : Character.isLetter(uuid[2]) || uuid[2] - 48 >= 3);

            float a = 0.2F + ((1.0F - (this.polyHorns ? -0.001F : 1.0F))* -0.05F);
            float b = 0.3F + ((1.0F - (this.polyHorns ? -0.001F : 1.0F))* 0.05F);
            float x = a * ( 1.0F + (b * 1.5F));

            this.hornGeneticsZ = new float[]{(a) + 0.25F, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a};
            this.hornGeneticsX = new float[]{(x) + 0.25F, x, x, x, x, x, x, x, x, x, a*(1.0F+(b*1.4F)), a*(1.0F+(b*1.3F)), a*(1.0F+(b*1.2F)), a*(1.0F+(b*1.1F)), a*(1.0F+(b*1.0F)), a*(1.0F+(b*0.9F)), a*(1.0F+(b*0.8F)), a*(1.0F+(b*0.7F)), a*(1.0F+(b*0.6F))};
            this.hornGeneticsY = new float[]{(a) + 0.25F, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a};


        }
    }

    private enum HornType {
        POLLED,
        SCURRED,
        HORNED
    }
}
