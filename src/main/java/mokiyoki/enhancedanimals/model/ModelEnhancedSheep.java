package mokiyoki.enhancedanimals.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import mokiyoki.enhancedanimals.entity.EnhancedSheep;
import mokiyoki.enhancedanimals.entity.EntityState;
import mokiyoki.enhancedanimals.model.modeldata.AnimalModelData;
import mokiyoki.enhancedanimals.model.modeldata.HornType;
import mokiyoki.enhancedanimals.model.modeldata.Phenotype;
import mokiyoki.enhancedanimals.model.modeldata.SheepModelData;
import mokiyoki.enhancedanimals.model.modeldata.SheepPhenotype;
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
import java.util.concurrent.ThreadLocalRandom;

@OnlyIn(Dist.CLIENT)
public class ModelEnhancedSheep<T extends EnhancedSheep> extends EnhancedAnimalModel<T> {
    protected WrappedModelPart theSheep;

    protected WrappedModelPart theHead;
    protected WrappedModelPart theHornLeft;
    protected WrappedModelPart theHornPolyLeft;
    protected WrappedModelPart theHornRight;
    protected WrappedModelPart theHornPolyRight;
    protected WrappedModelPart theEarLeft;
    protected WrappedModelPart theEarRight;
    protected WrappedModelPart theNeck;
    protected WrappedModelPart theBody;
    protected WrappedModelPart theLegFrontLeft;
    protected WrappedModelPart theLegFrontRight;
    protected WrappedModelPart theLegBackLeft;
    protected WrappedModelPart theLegBackRight;
    protected WrappedModelPart theLegBottomFrontLeft;
    protected WrappedModelPart theLegBottomFrontRight;
    protected WrappedModelPart theLegBottomBackLeft;
    protected WrappedModelPart theLegBottomBackRight;
    protected WrappedModelPart theTail;

    protected WrappedModelPart jaw;

    protected WrappedModelPart head;
    protected WrappedModelPart nose;

    protected WrappedModelPart earLeft;

    protected WrappedModelPart earRight;

    protected WrappedModelPart neck;

    protected WrappedModelPart body;
    protected WrappedModelPart bodyLongHair;

    protected WrappedModelPart udder;
    protected WrappedModelPart nippleLeft;
    protected WrappedModelPart nippleRight;

    private WrappedModelPart legFrontLeft;
    private WrappedModelPart legFrontRight;
    private WrappedModelPart legBackLeft;
    private WrappedModelPart legBackRight;

    private WrappedModelPart legFrontLeftBottom;
    private WrappedModelPart legFrontRightBottom;
    private WrappedModelPart legBackLeftBottom;
    private WrappedModelPart legBackRightBottom;

    protected WrappedModelPart headWool;
    protected WrappedModelPart noseWool;
    protected WrappedModelPart cheekWool;

    protected WrappedModelPart tail[] = new WrappedModelPart[4];
    protected WrappedModelPart neckWool[] = new WrappedModelPart[7];

    private WrappedModelPart hornLeft[] = new WrappedModelPart[19];
    private WrappedModelPart hornRight[] = new WrappedModelPart[19];

    private SheepModelData sheepModelData;


    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition base = meshdefinition.getRoot().addOrReplaceChild("base", CubeListBuilder.create(), PartPose.ZERO);
        PartDefinition bSheep = base.addOrReplaceChild("bSheep", CubeListBuilder.create(), PartPose.offset(0.0F, 11.0F, 0.0F));
        PartDefinition bBody = bSheep.addOrReplaceChild("bBody", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition bNeck = bSheep.addOrReplaceChild("bNeck", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, -7.0F));
        PartDefinition bHead = bSheep.addOrReplaceChild("bHead", CubeListBuilder.create(), PartPose.offset(0.0F, -10.0F, 1.0F));
        PartDefinition bEarLeft = bSheep.addOrReplaceChild("bEarL", CubeListBuilder.create(), PartPose.offset(2.5F, 2.0F, -1.0F));
        PartDefinition bEarRight = bSheep.addOrReplaceChild("bEarR", CubeListBuilder.create(), PartPose.offset(-2.5F, 2.0F, -1.0F));
        PartDefinition bLegFrontLeft = bSheep.addOrReplaceChild("bLegFL", CubeListBuilder.create(), PartPose.offset(1.01F, 3.0F, -5.0F));
            bSheep.addOrReplaceChild("bLegBFL", CubeListBuilder.create(), PartPose.offset(-0.005F, 5.0F, -3.0F));
        PartDefinition bLegFrontRight = bSheep.addOrReplaceChild("bLegFR", CubeListBuilder.create(), PartPose.offset(-4.01F, 3.0F, -5.0F));
            bSheep.addOrReplaceChild("bLegBFR", CubeListBuilder.create(), PartPose.offset(0.005F, 5.0F, -3.0F));
        PartDefinition bLegBackLeft = bSheep.addOrReplaceChild("bLegBL", CubeListBuilder.create(), PartPose.offset(1.01F, 3.0F, 5.0F));
            bSheep.addOrReplaceChild("bLegBBL", CubeListBuilder.create(), PartPose.offset(-0.005F, 5.0F, 0.0F));
        PartDefinition bLegBackRight = bSheep.addOrReplaceChild("bLegBR", CubeListBuilder.create(), PartPose.offset(-4.01F, 3.0F, 5.0F));
            bSheep.addOrReplaceChild("bLegBBR", CubeListBuilder.create(), PartPose.offset(0.005F, 5.0F, 0.0F));
        PartDefinition bTail = bSheep.addOrReplaceChild("bTail", CubeListBuilder.create(), PartPose.offset(0.0F, -2.0F, 8.0F));
        PartDefinition bHornLeft = bSheep.addOrReplaceChild("bHornL", CubeListBuilder.create(), PartPose.offset(0.0F, 0.5F, -1.0F));
        PartDefinition bHornRight = bSheep.addOrReplaceChild("bHornR", CubeListBuilder.create(), PartPose.offset(0.0F, 0.5F, -1.0F));
            bSheep.addOrReplaceChild("bHornPL", CubeListBuilder.create(), PartPose.offsetAndRotation(2.0F, -1.0F, -1.0F, -((Mth.PI * 0.9F) + 0.2F), -0.1F,-Mth.HALF_PI * 0.3F));
            bSheep.addOrReplaceChild("bHornPR", CubeListBuilder.create(), PartPose.offsetAndRotation(-2.0F, -1.0F, -1.0F, -((Mth.PI * 0.9F) + 0.2F), 0.1F,Mth.HALF_PI * 0.3F));

        bHead.addOrReplaceChild("eyes", CubeListBuilder.create()
                        .texOffs(12, 10)
                        .addBox(1.5F, 1.0F, -4.0F, 1, 1, 2, new CubeDeformation(0.01F))
                        .texOffs( 0, 10)
                        .addBox(-2.5F, 1.0F, -4.0F, 1, 1, 2, new CubeDeformation(0.01F)),
                PartPose.ZERO
        );

        bHead.addOrReplaceChild("head", CubeListBuilder.create()
                        .texOffs(0, 0)
                        .addBox(-2.5F, 0.0F, -4.0F, 5, 4, 4),
                PartPose.ZERO
        );
        bHead.addOrReplaceChild("nose", CubeListBuilder.create()
                        .texOffs(0, 9)
                        .addBox(-2.5F, 0.0F, -3.5F, 5, 3, 4, new CubeDeformation(-0.5F)),
                PartPose.offset(0.0F, 0.0F, -4.0F)
        );
        bHead.addOrReplaceChild("headW", CubeListBuilder.create()
                        .texOffs(0, 55)
                        .addBox(-2.5F, 0.0F, 0.0F, 5, 5, 4, new CubeDeformation(0.5F)),
                PartPose.offsetAndRotation(0.0F, 4.0F, -4.0F, Mth.HALF_PI, 0.0F, 0.0F)
        );
        bHead.addOrReplaceChild("cheekW", CubeListBuilder.create()
                        .texOffs(14, 47)
                        .addBox(-2.5F, 0.0F, 0.0F, 5, 2, 2, new CubeDeformation(0.51F)),
                PartPose.offset(0.0F, 2.25F, -5.0F)
        );
        bHead.addOrReplaceChild("noseW", CubeListBuilder.create()
                        .texOffs(0, 50)
                        .addBox(-2.0F, 0.0F, 0.0F, 4, 2, 3, new CubeDeformation(0.5F)),
                PartPose.offset(0.0F, 0.25F, -2.0F)
        );

        bHead.addOrReplaceChild("jaw", CubeListBuilder.create()
                        .texOffs(50, 1)
                        .addBox(-1.5F, 2.0F, -3.0F, 3, 2, 4)
                        .texOffs(30, 58)
                        .addBox(-1.5F, 3.0F, -3.0F, 3, 1, 4, new CubeDeformation(-0.1F)),
                PartPose.offset(0.0F, 0.0F, -2.75F)
        );

        bEarLeft.addOrReplaceChild("earL", CubeListBuilder.create()
                        .texOffs(18, 54)
                        .addBox(0.0F, 0.0F, 0.0F, 2, 3, 1, new CubeDeformation(0.01F)),
                PartPose.rotation(0.0F, 0.0F, -Mth.HALF_PI)
        );

        bEarRight.addOrReplaceChild("earR", CubeListBuilder.create()
                        .texOffs(24, 54)
                        .addBox(-2.0F, 0.0F, 0.0F, 2, 3, 1, new CubeDeformation(0.01F)),
                PartPose.rotation(0.0F, 0.0F, Mth.HALF_PI)
        );

        bNeck.addOrReplaceChild("neck", CubeListBuilder.create()
                        .texOffs(34, 0)
                        .addBox(-2.0F, -9.0F, -2.0F, 4, 9, 4),
                PartPose.ZERO
        );

        bBody.addOrReplaceChild("body", CubeListBuilder.create()
                        .texOffs(2,0)
                        .addBox(-4.0F, -3.0F, -8.0F, 8, 7, 16)
                        .texOffs(70, 48)
                        .addBox(-4.0F, 4.0F, -8.0F, 8, 0, 16),
                PartPose.offset(0.0F, 0.0F, 0.0F)
        );
        bBody.addOrReplaceChild("bodyL", CubeListBuilder.create()
                        .texOffs(2,0)
                        .addBox(-4.0F, -3.0F, -8.0F, 8, 13, 16, new CubeDeformation(0.2F))
                        .texOffs(70, 48)
                        .addBox(-4.0F, 4.0F, -8.0F, 8, 0, 16, new CubeDeformation(0.2F)),
                PartPose.offset(0.0F, 0.0F, 0.0F)
        );

        bLegFrontLeft.addOrReplaceChild("legFL", CubeListBuilder.create()
                        .texOffs( 0, 32)
                        .addBox(0.0F, -5.0F, -3.0F, 3, 5, 3, new CubeDeformation(0.001F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, -3.0F, Mth.PI, 0.0F, 0.0F)
        );
        bLegFrontRight.addOrReplaceChild("legFR", CubeListBuilder.create()
                        .texOffs(12, 32)
                        .addBox(0.0F, -5.0F, -3.0F, 3, 5, 3, new CubeDeformation(0.001F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, -3.0F, Mth.PI, 0.0F, 0.0F)
        );
        bLegBackLeft.addOrReplaceChild("legBL", CubeListBuilder.create()
                        .texOffs(24, 32)
                        .addBox(0.0F, -5.0F, 0.0F, 3, 5, 3, new CubeDeformation(0.001F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 3.0F, Mth.PI, 0.0F, 0.0F)
        );
        bLegBackRight.addOrReplaceChild("legBR", CubeListBuilder.create()
                        .texOffs(36, 32)
                        .addBox(0.0F, -5.0F, 0.0F, 3, 5, 3, new CubeDeformation(0.001F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 3.0F, Mth.PI, 0.0F, 0.0F)
        );

        bLegFrontLeft.addOrReplaceChild("legBFL", CubeListBuilder.create()
                        .texOffs( 0, 41)
                        .addBox(0.0F, -5.0F, 0.0F, 3, 5, 3),
                PartPose.offsetAndRotation(0.0F, 0.0F, 3.0F, Mth.PI, 0.0F, 0.0F)
        );
        bLegFrontRight.addOrReplaceChild("legBFR", CubeListBuilder.create()
                        .texOffs(12, 41)
                        .addBox(0.0F, -5.0F, 0.0F, 3, 5, 3),
                PartPose.offsetAndRotation(0.0F, 0.0F, 3.0F, Mth.PI, 0.0F, 0.0F)
        );
        bLegBackLeft.addOrReplaceChild("legBBL", CubeListBuilder.create()
                        .texOffs(24, 41)
                        .addBox(0.0F, -5.0F, 0.0F, 3, 5, 3),
                PartPose.offsetAndRotation(0.0F, 0.0F, 3.0F, Mth.PI, 0.0F, 0.0F)
        );
        bLegBackRight.addOrReplaceChild("legBBR", CubeListBuilder.create()
                        .texOffs(36, 41)
                        .addBox(0.0F, -5.0F, 0.0F, 3, 5, 3),
                PartPose.offsetAndRotation(0.0F, 0.0F, 3.0F, Mth.PI, 0.0F, 0.0F)
        );

        bTail.addOrReplaceChild("tail0", CubeListBuilder.create()
                        .texOffs(86, 43)
                        .addBox(-1.0F, 0.0F, 0.0F, 2, 3, 2),
                PartPose.ZERO
        );
        bTail.addOrReplaceChild("tail1", CubeListBuilder.create()
                        .texOffs(94, 43)
                        .addBox(-1.0F, 0.0F, 0.0F, 2, 3, 2),
                PartPose.offset(0.0F, 3.0F, 0.0F)
        );
        bTail.addOrReplaceChild("tail2", CubeListBuilder.create()
                        .texOffs(102, 43)
                        .addBox(-1.0F, 0.0F, 0.0F, 2, 3, 2),
                PartPose.offset(0.0F, 3.0F, 0.0F)
        );
        bTail.addOrReplaceChild("tail3", CubeListBuilder.create()
                        .texOffs(110, 43)
                        .addBox(-1.0F, 0.0F, 0.0F, 2, 3, 2),
                PartPose.offset(0.0F, 3.0F, 0.0F)
        );

        bBody.addOrReplaceChild("udder", CubeListBuilder.create()
                        .texOffs(46, 55)
                        .addBox(-2.0F, 0.0F, 0.0F, 4, 4, 5),
                PartPose.offset(0.0F, 1.0F, 3.5F)
        );
        bBody.addOrReplaceChild("nippleL", CubeListBuilder.create()
                        .texOffs(46, 55)
                        .addBox(-2.0F, 0.0F, 0.0F, 1, 3, 1),
                PartPose.offset(0.0F, 3.0F, 0.0F)
        );
        bBody.addOrReplaceChild("nippleR", CubeListBuilder.create()
                        .texOffs(60, 55)
                        .addBox(1.0F, 0.0F, 0.0F, 1, 3, 1),
                PartPose.offset(0.0F, 3.0F, 0.0F)
        );

        for (int i = 0; i < 7; i++) {
            float[] f = {0.4F, 0.75F, 1.1F, 1.5F, 2.0F, 2.5F, 3.0F};
            CubeListBuilder neckWool = CubeListBuilder.create()
                    .texOffs(34, 0)
                    .addBox(-2.0F, -5.0F, -2.0F, 4, 9-i, 4, new CubeDeformation(f[i]));
            bNeck.addOrReplaceChild("neckW"+i, neckWool, PartPose.ZERO);
        }

        for (int i = 0; i < 19; i++) {
            float scale = i < 10 ? -0.5F : -(0.5F + (((float)(i-10)*0.75F)/9F));
            CubeListBuilder hornBitL = CubeListBuilder.create()
                    .texOffs(50, 12)
                    .addBox(-3.0F, -3.0F, -1.5F, 3, 3, 3, new CubeDeformation(scale));
            CubeListBuilder hornBitR = CubeListBuilder.create()
                    .texOffs(50, 12)
                    .addBox(0.0F, -3.0F, -1.5F, 3, 3, 3, new CubeDeformation(scale));
            bHornLeft.addOrReplaceChild("hornL"+i, hornBitL, PartPose.ZERO);
            bHornRight.addOrReplaceChild("hornR"+i, hornBitR, PartPose.ZERO);
        }

        /**
         *      Equipment
         */

        base.addOrReplaceChild("chestL", CubeListBuilder.create()
                        .texOffs(64, 42)
                        .addBox(-3.0F, 0.0F, 0.0F, 8, 8, 3, new CubeDeformation(0.01F)),
                PartPose.offsetAndRotation(-7.0F, -2.0F, 4.0F, 0.0F, Mth.HALF_PI, 0.0F)
        );
        base.addOrReplaceChild("chestR", CubeListBuilder.create()
                        .texOffs(64, 53)
                        .addBox(-3.0F, 0.0F, 0.0F, 8, 8, 3, new CubeDeformation(0.01F)),
                PartPose.offset(0.0F, 0.0F, 11.0F)
        );

        base.addOrReplaceChild("collar", CubeListBuilder.create()
                        .texOffs(54, 0)
                        .addBox(-5.0F, 0.0F, -9.0F, 10, 2, 10, new CubeDeformation(-2.5F, -0.5F, -2.5F))
                        .texOffs(86, 2)
                        .addBox(0.0F, -0.75F, -8.25F, 0, 4, 4, new CubeDeformation(0.0F, -1.0F, -1.0F)),
                PartPose.offset(0.0F, -5.0F, 4.0F)
        );
        base.addOrReplaceChild("collarH", CubeListBuilder.create()
                        .texOffs(94, 0)
                        .addBox(-1.5F, 0.375F, -1.5F, 3, 3, 3, new CubeDeformation(-0.75F, -0.75F, -0.75F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, -6.5F, -Mth.HALF_PI * 0.5F, 0.0F, 0.0F)
        );

        base.addOrReplaceChild("bridle", CubeListBuilder.create()
                        .texOffs(48, 24)
                        .addBox(-5.0F, 0.0F, -10.0F, 10, 8, 8, new CubeDeformation(-2.4F, -1.9F, -1.9F)),
                PartPose.offset(0.0F, -2.0F, 4.0F)
        );
        base.addOrReplaceChild("bridleN", CubeListBuilder.create()
                        .texOffs(56, 12)
                        .addBox(-4.0F, 0.0F, -14.0F, 8, 6, 6, new CubeDeformation(-1.9F, -1.4F, -1.4F)),
                PartPose.offset(0.0F, 1.0F, 2.0F)
        );

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
        ModelPart bLegFR = bSheep.getChild("bLegFR");
        ModelPart bLegBL = bSheep.getChild("bLegBL");
        ModelPart bLegBR = bSheep.getChild("bLegBR");
        ModelPart bLegBFL = bSheep.getChild("bLegBFL");
        ModelPart bLegBFR = bSheep.getChild("bLegBFR");
        ModelPart bLegBBL = bSheep.getChild("bLegBBL");
        ModelPart bLegBBR = bSheep.getChild("bLegBBR");
        ModelPart bTail = bSheep.getChild("bTail");
        ModelPart bHornL = bSheep.getChild("bHornL");
        ModelPart bHornR = bSheep.getChild("bHornR");
        ModelPart bHornPL = bSheep.getChild("bHornPL");
        ModelPart bHornPR = bSheep.getChild("bHornPR");

        this.theSheep = new WrappedModelPart(bSheep, "bSheep");
        this.theBody = new WrappedModelPart(bBody, "bBody");
        this.theNeck = new WrappedModelPart(bNeck, "bNeck");
        this.theHead = new WrappedModelPart(bHead, "bHead");
        this.theEarLeft = new WrappedModelPart(bEarLeft, "bEarL");
        this.theEarRight = new WrappedModelPart(bEarRight, "bEarR");
        this.theLegFrontLeft = new WrappedModelPart(bLegFL, "bLegFL");
        this.theLegBottomFrontLeft = new WrappedModelPart(bLegBFL, "bLegFLB");
        this.theLegFrontRight = new WrappedModelPart(bLegFR, "bLegFR");
        this.theLegBottomFrontRight = new WrappedModelPart(bLegBFR, "bLegFRB");
        this.theLegBackLeft = new WrappedModelPart(bLegBL, "bLegBL");
        this.theLegBottomBackLeft = new WrappedModelPart(bLegBBL, "bLegBLB");
        this.theLegBackRight = new WrappedModelPart(bLegBR, "bLegBR");
        this.theLegBottomBackRight = new WrappedModelPart(bLegBBR, "bLegBRB");
        this.theTail = new WrappedModelPart(bTail, "bTail");
        this.theHornLeft = new WrappedModelPart(bHornL, "bHornL");
        this.theHornPolyLeft = new WrappedModelPart(bHornPL, "bHornPL");
        this.theHornRight = new WrappedModelPart(bHornR, "bHornR");
        this.theHornPolyRight = new WrappedModelPart(bHornPR, "bHornR");

        this.jaw = new WrappedModelPart("jaw", bHead);

        this.eyes = new WrappedModelPart("eyes", bHead);

        this.head = new WrappedModelPart("head", bHead);

        this.nose = new WrappedModelPart("nose", bHead);

        this.earLeft = new WrappedModelPart("earL", bEarLeft);

        this.earRight = new WrappedModelPart("earR", bEarRight);

        this.neck = new WrappedModelPart("neck", bNeck);

        this.body = new WrappedModelPart("body", bBody);
//        this.bodyLongHair = new WrappedModelPart("bodyL", bBody);

        this.headWool = new WrappedModelPart("headW", bHead);
        this.cheekWool = new WrappedModelPart("cheekW", bHead);
        this.noseWool = new WrappedModelPart("noseW", bHead);

        this.udder = new WrappedModelPart("udder", bBody);
        this.nippleLeft = new WrappedModelPart("nippleL", bBody);
        this.nippleRight = new WrappedModelPart("nippleR", bBody);
        
        this.legFrontLeft = new WrappedModelPart("legFL", bLegFL);
        this.legFrontRight = new WrappedModelPart("legFR", bLegFR);
        this.legBackLeft = new WrappedModelPart("legBL", bLegBL);
        this.legBackRight = new WrappedModelPart("legBR", bLegBR);

        this.legFrontLeftBottom = new WrappedModelPart("legBFL", bLegFL);
        this.legFrontRightBottom = new WrappedModelPart("legBFR", bLegFR);
        this.legBackLeftBottom = new WrappedModelPart("legBBL", bLegBL);
        this.legBackRightBottom = new WrappedModelPart("legBBR", bLegBR);

        for (int i = 0; i < 4; i++) {
            this.tail[i] = new WrappedModelPart("tail"+i, bTail);
        }

        for (int i = 0; i < 7; i++) {
            this.neckWool[i] = new WrappedModelPart("neckW"+i, bNeck);
        }

        for (int i = 0; i < 19; i++) {
            this.hornLeft[i] = new WrappedModelPart("hornL"+i, bHornL);
            this.hornRight[i] = new WrappedModelPart("hornR"+i, bHornR);
        }

        this.theSheep.addChild(this.theBody);

        this.theSheep.addChild(this.theLegFrontLeft);
        this.theLegFrontLeft.addChild(this.theLegBottomFrontLeft);
        this.theSheep.addChild(this.theLegFrontRight);
        this.theLegFrontRight.addChild(this.theLegBottomFrontRight);
        this.theSheep.addChild(this.theLegBackLeft);
        this.theLegBackLeft.addChild(this.theLegBottomBackLeft);

        this.theSheep.addChild(this.theLegBackRight);
        this.theLegBackRight.addChild(this.theLegBottomBackRight);
        this.theBody.addChild(this.theNeck);
        this.theBody.addChild(this.theTail);
        this.theNeck.addChild(this.theHead);
        this.theHead.addChild(this.theEarLeft);
        this.theHead.addChild(this.theEarRight);
        this.theHead.addChild(this.theHornLeft);
        this.theHead.addChild(this.theHornRight);
        this.theHead.addChild(this.theHornPolyLeft);
        this.theHead.addChild(this.theHornPolyRight);

        this.theHead.addChild(this.jaw);
        this.theHead.addChild(this.head);
        this.theHead.addChild(this.eyes);
        this.theHead.addChild(this.nose);
        this.theHead.addChild(this.headWool);
        this.theHead.addChild(this.cheekWool);
        this.nose.addChild(this.noseWool);

        this.theEarLeft.addChild(this.earLeft);
        this.theEarRight.addChild(this.earRight);

        this.theNeck.addChild(this.neck);

        this.theBody.addChild(this.body);
//        this.theBody.addChild(this.bodyLongHair);

        this.theBody.addChild(this.udder);
        this.udder.addChild(this.nippleLeft);
        this.udder.addChild(this.nippleRight);

        this.theTail.addChild(this.tail[0]);
        this.tail[0].addChild(this.tail[1]);
        this.tail[1].addChild(this.tail[2]);
        this.tail[2].addChild(this.tail[3]);

        this.theLegFrontLeft.addChild(this.legFrontLeft);
        
        this.theLegBottomFrontLeft.addChild(this.legFrontLeftBottom);

        this.theLegFrontRight.addChild(this.legFrontRight);

        this.theLegBottomFrontRight.addChild(this.legFrontRightBottom);

        this.theLegBackLeft.addChild(this.legBackLeft);

        this.theLegBottomBackLeft.addChild(this.legBackLeftBottom);

        this.theLegBackRight.addChild(this.legBackRight);

        this.theLegBottomBackRight.addChild(this.legBackRightBottom);

        for (int i = 0; i < 7; i++) {
            this.theNeck.addChild(this.neckWool[i]);
        }

        this.theHornLeft.addChild(this.hornLeft[0]);
        this.theHornRight.addChild(this.hornRight[0]);
        this.theHornPolyLeft.addChild(this.hornLeft[10]);
        this.theHornPolyRight.addChild(this.hornRight[10]);

        for (int i = 0; i < 18; i++) {
            this.hornLeft[i].addChild(this.hornLeft[i+1]);
            this.hornRight[i].addChild(this.hornRight[i+1]);
        }

        /**
         *      Equipment
         */
        this.chests = new WrappedModelPart("chestL", base);
        this.chestsR = new WrappedModelPart("chestR", base);
        this.collar = new WrappedModelPart("collar", base);
        this.collarHardware = new WrappedModelPart("collarH", base);
        this.bridle = new WrappedModelPart("bridle", base);
        this.bridleNose = new WrappedModelPart("bridleN", base);

        this.theBody.addChild(this.chests);
        this.chests.addChild(this.chestsR);
        this.theNeck.addChild(this.collar);
        this.collar.addChild(this.collarHardware);
        this.theHead.addChild(this.bridle);
        this.bridle.addChild(this.bridleNose);
    }

    private void resetCubes() {

    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        if (this.sheepModelData !=null && this.sheepModelData.getPhenotype() != null) {
            SheepPhenotype sheep = this.sheepModelData.getPhenotype();
            resetCubes();

            super.renderToBuffer(this.sheepModelData, poseStack, vertexConsumer, packedLightIn, packedOverlayIn, red, green, blue, alpha);

            Map<String, List<Float>> mapOfScale = new HashMap<>();
            /**
             *      Wool
             */

            float woolScale = 1.0F + (this.sheepModelData.coatLength/22.5F);
            float woolScale2 = 1.0F + (this.sheepModelData.coatLength/45F);
            mapOfScale.put("body", ModelHelper.createScalings(woolScale, woolScale, woolScale2, 0.0F, 0.0F, 0.0F));

            for (int i = 0, l = neckWool.length; i < l; i++) {
                this.neckWool[i].hide();
            }

            //{0.4F, 0.75F, 1.1F, 1.5F, 2.0F, 2.5F, 3.0F}
            switch (this.sheepModelData.coatLength) {
                case 1, 2 -> {
                    this.neckWool[0].show();
                    mapOfScale.put("collar", ModelHelper.createScalings(1.01F, 0.0F, 0.0F, 0.008F));
                }
                case 3, 4 -> {
                    this.neckWool[1].show();
                    mapOfScale.put("collar", ModelHelper.createScalings(1.2F, 0.0F, 0.0F, 0.03F));
                }
                case 5, 6 -> {
                    this.neckWool[2].show();
                    mapOfScale.put("collar", ModelHelper.createScalings(1.35F, 0.0F, 0.0F, 0.06F));
                }
                case 7, 8 -> {
                    this.neckWool[3].show();
                    mapOfScale.put("collar", ModelHelper.createScalings(1.5F, 0.0F, 0.0F, 0.085F));
                }
                case 9, 10 -> {
                    this.neckWool[4].show();
                    mapOfScale.put("collar", ModelHelper.createScalings(1.675F, 0.0F, 0.0F, 0.1F));
                }
                case 11, 12 -> {
                    this.neckWool[5].show();
                    mapOfScale.put("collar", ModelHelper.createScalings(1.9F, 0.0F, 0.0F, 0.12F));
                }
                case 13, 14, 15 -> {
                    this.neckWool[6].show();
                    mapOfScale.put("collar", ModelHelper.createScalings(2.1F, 0.0F, 0.0F, 0.13F));
                }
            }

            if (this.sheepModelData.coatLength > 2 && sheep.faceWool >= 1) {
                this.headWool.show();
                this.cheekWool.show(sheep.faceWool >= 2);
                mapOfScale.put("bridle", ModelHelper.createScalings(1.2F, 1.2F, 1.5F, 0.0F, -0.05F, 0.14F));
                if (sheep.faceWool >= 3) {
                    this.noseWool.show();
                    mapOfScale.put("bridleN", ModelHelper.createScalings(1.0F, 1.0F, 0.6F, 0.0F, -0.01F, -0.35F));
                } else {
                    mapOfScale.put("bridleN", ModelHelper.createScalings(0.833333F, 0.833333F, 0.666666F, 0.0F, 0.05F, -0.25F));
                    this.noseWool.hide();
                }
            } else {
                this.headWool.hide();
                this.cheekWool.hide();
                this.noseWool.hide();
            }

            /**
             *      Udder
             */
            if (this.sheepModelData.bagSize != -1.0F) {
                this.udder.show();
                float bagthickness = this.sheepModelData.bagSize * this.sheepModelData.bagSize;
                mapOfScale.put("Udder", ModelHelper.createScalings(bagthickness, this.sheepModelData.bagSize, bagthickness, 0.0F, 0.0F, 0.0F));
                if (bagthickness != 0.0F) {
                    mapOfScale.put("NippleL", ModelHelper.createScalings(1.0F / bagthickness, -this.sheepModelData.bagSize, 1.0F / bagthickness, 0.0F, 0.0F, 0.0F));
                    mapOfScale.put("NippleR", ModelHelper.createScalings(1.0F / bagthickness, -this.sheepModelData.bagSize, 1.0F / bagthickness, 0.0F, 0.0F, 0.0F));
                }
            } else {
                this.udder.hide();
            }

            /**
             *      Horns
             */
            if (sheep.hornType == HornType.POLLED ) {
                this.theHornLeft.hide();
                this.theHornRight.hide();
                this.theHornPolyLeft.hide();
                this.theHornPolyRight.hide();
            } else {
                this.theHornLeft.show();
                this.theHornRight.show();
                this.theHornPolyLeft.show(sheep.polyHorns);
                this.theHornPolyRight.show(sheep.polyHorns);
                for (int i = 0; i < 19; i++) {
                    this.hornLeft[i].boxIsRendered = this.sheepModelData.offsets.containsKey("hL" + i);
                    this.hornRight[i].boxIsRendered = this.sheepModelData.offsets.containsKey("hR" + i);
                }
            }

            /**
             *      Tail
             */
            this.tail[0].show(true);
            this.tail[1].show(sheep.tailLength >= 2);
            this.tail[2].show(sheep.tailLength >= 3);
            this.tail[3].show(sheep.tailLength == 4);


            if (sheep.tailFat > 0.0F) {
                float tailFat = (sheep.tailFat * 3.5F) + 1.0F;
                mapOfScale.put("bTail", ModelHelper.createScalings(tailFat, (sheep.tailFat * 0.5F) + 1.0F, (sheep.tailFat * 0.75F) + 1.0F, 0.0F, 0.0F, 0.0F));
            }

            /**
             *      Growth scaling
             */
            float sheepSize = ((2.0F * this.sheepModelData.size * this.sheepModelData.growthAmount) + this.sheepModelData.size) / 3.0F;
            float d = 0.0F;
            if (!this.sheepModelData.sleeping && this.sheepModelData.growthAmount != 1.0F) {
                float babySize = (3.0F - this.sheepModelData.growthAmount) * 0.5F;
                d = 0.33333F * (1.0F - this.sheepModelData.growthAmount);
                mapOfScale.put("bLegFL", ModelHelper.createScalings(1.0F, babySize,1.0F,0.0F, 0.0F, 0.0F));
                mapOfScale.put("bLegFR", ModelHelper.createScalings(1.0F, babySize,1.0F,0.0F, 0.0F, 0.0F));
                mapOfScale.put("bLegBL", ModelHelper.createScalings(1.0F, babySize,1.0F,0.0F, 0.0F, 0.0F));
                mapOfScale.put("bLegBR", ModelHelper.createScalings(1.0F, babySize,1.0F,0.0F, 0.0F, 0.0F));
            }

            poseStack.pushPose();
            poseStack.scale(sheepSize, sheepSize, sheepSize);
            poseStack.translate(0.0F, (-1.5F + 1.5F / (sheepSize))-d, 0.0F);

            gaRender(this.theSheep, mapOfScale, poseStack, vertexConsumer, packedLightIn, packedOverlayIn, red, green, blue, alpha);

            poseStack.popPose();
        }
    }

    protected void saveAnimationValues(SheepModelData data, SheepPhenotype sheep) {
        Map<String, Vector3f> map = data.offsets;
        map.put("bSheepPos", new Vector3f(0.0F, this.theSheep.getY(), 0.0F));
        map.put("bSheep", this.getRotationVector(this.theSheep));
        map.put("bBody", this.getRotationVector(this.theBody));
        map.put("bHead", this.getRotationVector(this.theHead));
        map.put("bNeck", this.getRotationVector(this.theNeck));
        map.put("bEarL", this.getRotationVector(this.theEarLeft));
        map.put("bEarR", this.getRotationVector(this.theEarRight));
        map.put("bLegFL", this.getRotationVector(this.theLegFrontLeft));
        map.put("bLegFLB", this.getRotationVector(this.theLegBottomFrontLeft));
        map.put("bLegFR", this.getRotationVector(this.theLegFrontRight));
        map.put("bLegFRB", this.getRotationVector(this.theLegBottomFrontRight));
        map.put("bLegBL", this.getRotationVector(this.theLegBackLeft));
        map.put("bLegBLB", this.getRotationVector(this.theLegBottomBackLeft));
        map.put("bLegBR", this.getRotationVector(this.theLegBackRight));
        map.put("bLegBRB", this.getRotationVector(this.theLegBottomBackRight));
        map.put("bTail", this.getRotationVector(this.theTail));
        map.put("tail0", this.getRotationVector(this.tail[0]));
        map.put("tail1", this.getRotationVector(this.tail[1]));
        map.put("tail2", this.getRotationVector(this.tail[2]));
        map.put("tail3", this.getRotationVector(this.tail[3]));
        map.put("bHornL", this.getRotationVector(this.theHornLeft));
        map.put("bHornR", this.getRotationVector(this.theHornRight));
        map.put("jaw", this.getRotationVector(this.jaw));

        saveHorns(sheep, data.hornGrowth, map);
    }

    private void saveHorns(SheepPhenotype sheep, float hornGrowth, Map<String, Vector3f> map) {
        if (sheep.hornType != HornType.POLLED) {
            if (!map.containsKey("hL"+sheep.leftHornLength)) {
                for (int l = 19 - (int) ((float) (19 - sheep.leftHornLength) * hornGrowth); l < 19; l++) {
                    if (l >= 0) {
                        if (map.containsKey("hL" + l)) {
                            break;
                        } else {
                            map.put("hL" + l, new Vector3f(-sheep.hornGeneticsX[l], -sheep.hornGeneticsY[l], sheep.hornGeneticsZ[l]));
                            map.put("hPosL" + (l + 1), new Vector3f(0.0F,l < 10 ? -1.95F : -(1.8F - (((l - 10) * 1.4F) / 9F)), 0.0F));
                        }
                    }
                }
            }
            if (!map.containsKey("hR"+sheep.rightHornLength)) {
                for (int r = 19 - (int)(((19F - sheep.rightHornLength) * hornGrowth)); r < 19; r++) {
                    if (r >= 0) {
                        if (map.containsKey("hR" + r)) {
                            break;
                        } else {
                            map.put("hR" + r, new Vector3f(-sheep.hornGeneticsX[r], sheep.hornGeneticsY[r], -sheep.hornGeneticsZ[r]));
                            map.put("hPosR" + (r + 1), new Vector3f(0.0F, r < 10 ? -1.95F : -(1.8F - (((r - 10) * 1.4F) / 9F)), 0.0F));
                        }
                    }
                }
            }
        }
    }

    private void setupInitialAnimationValues(SheepModelData data, SheepPhenotype sheep, float hornGrowthAmount) {
        Map<String, Vector3f> map = data.offsets;
        if (map.isEmpty()) {
            this.theNeck.setRotation(Mth.HALF_PI, 0.0F, 0.0F);
            this.jaw.setXRot(Mth.HALF_PI*-0.2F);
            if (!sheep.hornType.equals(HornType.POLLED)) {
                if (sheep.polyHorns) {
                    this.theHornLeft.setRotation(-0.2F, -0.1F,-Mth.HALF_PI * 0.5F);
                    this.theHornRight.setRotation(-0.2F, 0.1F,Mth.HALF_PI * 0.5F);
                } else {
                    this.theHornLeft.setRotation(-0.45F, 0.0F, -Mth.HALF_PI * 0.3F);
                    this.theHornRight.setRotation(-0.45F, 0.0F,Mth.HALF_PI * 0.3F);
                }
            }
        } else {
            this.theSheep.setPosYAndRot(map.get("bSheepPos"), map.get("bSheep"));
            this.setRotationFromVector(this.theBody, map.get("bBody"));
            this.setRotationFromVector(this.theNeck, map.get("bNeck"));
            this.setRotationFromVector(this.theHead, map.get("bHead"));
            this.setRotationFromVector(this.theEarLeft, map.get("bEarL"));
            this.setRotationFromVector(this.theEarRight, map.get("bEarR"));
            this.setRotationFromVector(this.theLegFrontLeft, map.get("bLegFL"));
            this.setRotationFromVector(this.theLegBottomFrontLeft, map.get("bLegFLB"));
            this.setRotationFromVector(this.theLegFrontRight, map.get("bLegFR"));
            this.setRotationFromVector(this.theLegBottomFrontRight, map.get("bLegFRB"));
            this.setRotationFromVector(this.theLegBackLeft, map.get("bLegBL"));
            this.setRotationFromVector(this.theLegBottomBackLeft, map.get("bLegBLB"));
            this.setRotationFromVector(this.theLegBackRight, map.get("bLegBR"));
            this.setRotationFromVector(this.theLegBottomBackRight, map.get("bLegBRB"));
            this.setRotationFromVector(this.theTail, map.get("bTail"));
            this.setRotationFromVector(this.tail[0], map.get("tail0"));
            this.setRotationFromVector(this.tail[1], map.get("tail1"));
            this.setRotationFromVector(this.tail[2], map.get("tail2"));
            this.setRotationFromVector(this.tail[3], map.get("tail3"));
            this.jaw.setXRot(map.get("jaw").x());

            this.setRotationFromVector(this.theHornLeft, map.get("bHornL"));
            this.setRotationFromVector(this.theHornRight, map.get("bHornR"));

            Vector3f vector3f = new Vector3f(0.0F, 0.0F, 0.0F);
            boolean l = true;
            boolean r = true;
            for (int i = 0; i < 19; i++) {
                if (map.containsKey("hL"+i)) {
                    this.hornLeft[i].setPosYAndRot(map.get("hPosL" + i), l ? vector3f : map.get("hL" + i));
                    if (l) {
                        l=false;
                    }
                } else {
                    this.hornLeft[i].setPosYAndRot(vector3f, vector3f);
                }
                if (map.containsKey("hR"+i)) {
                    this.hornRight[i].setPosYAndRot(map.get("hPosR" + i), r ? vector3f : map.get("hR" + i));
                    if (r) {
                        r=false;
                    }
                } else {
                    this.hornRight[i].setPosYAndRot(vector3f, vector3f);
                }
            }
        }
    }

    @Override
    public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.sheepModelData = getCreateSheepModelData(entityIn);

        if (this.sheepModelData != null) {
            SheepPhenotype sheep = this.sheepModelData.getPhenotype();
            setupInitialAnimationValues(this.sheepModelData, sheep, this.sheepModelData.hornGrowth);

            boolean isMoving = entityIn.getDeltaMovement().horizontalDistanceSqr() > 1.0E-7D || entityIn.xOld != entityIn.getX() || entityIn.zOld != entityIn.getZ();

            if (this.sheepModelData.earTwitchTimer <= ageInTicks) {
                if (this.theEarLeft.getXRot() != 0F || this.theEarRight.getXRot() != 0F) {
                    this.theEarLeft.setXRot(this.lerpTo(0.1F, this.theEarLeft.getXRot(), 0.0F));
                    this.theEarRight.setXRot(this.lerpTo(0.1F,this.theEarRight.getXRot(), 0.0F));
                    this.theEarLeft.setYRot(this.lerpTo(0.15F, this.theEarLeft.getYRot(), 0.0F));
                    this.theEarRight.setYRot(this.lerpTo(0.15F, this.theEarRight.getYRot(), 0.0F));
                } else {
                    this.sheepModelData.earTwitchSide = entityIn.getRandom().nextBoolean();
                    this.sheepModelData.earTwitchTimer = (int) ageInTicks + (entityIn.getRandom().nextInt(sheepModelData.sleeping ? 60 : 30) * 20) + 30;
                }
            } else if (this.sheepModelData.earTwitchTimer <= ageInTicks + 30) {
                twitchEarAnimation(this.sheepModelData.earTwitchSide, (int)ageInTicks);
            }



            if (this.sheepModelData.sleeping && !isMoving) {
                if (this.sheepModelData.sleepDelay == -1) {
                    this.sheepModelData.sleepDelay = (int) ageInTicks + ((entityIn.getRandom().nextInt(10)) * 20) + 10;
                } else if (this.sheepModelData.sleepDelay <= ageInTicks+50) {
                    if (this.sheepModelData.sleepDelay <= ageInTicks) {
                        this.sheepModelData.sleepDelay = 0;
                        layDownAnimation(true);
                    } else {
                        layDownAnimation(false);
                        headLookingAnimation(netHeadYaw, headPitch);
                    }
                } else {
                    standingLegsAnimation();
                }
            } else {
                if (this.sheepModelData.sleepDelay != -1) {
                    this.sheepModelData.sleepDelay = -1;
                }

                if (this.theSheep.getY() > 11.0F) {
                    standUpAnimation(isMoving, limbSwing, limbSwingAmount);
                }

                boolean flag = true;
                if (this.sheepModelData.isEating != 0) {
                    if (this.sheepModelData.isEating == -1) {
                        this.sheepModelData.isEating = (int)ageInTicks + 90;
                    } else if (this.sheepModelData.isEating < ageInTicks) {
                        this.sheepModelData.isEating = 0;
                    }
                    flag = grazingAnimation(this.sheepModelData.isEating - ageInTicks);
                }

                if (flag) {
                    if (netHeadYaw == 0 && headPitch == 0) {
                        moveHeadAnimation();
                    } else {
                        headLookingAnimation(netHeadYaw, headPitch);
                    }
                }

                flag = true;
                if (this.sheepModelData.tailSwishTimer <= ageInTicks) {
                    this.sheepModelData.tailSwishSide = entityIn.getRandom().nextBoolean();
                    this.sheepModelData.tailSwishTimer = (int) ageInTicks + (entityIn.getRandom().nextInt(30) * 20) + 30;
                } else if (this.sheepModelData.tailSwishTimer <= ageInTicks + 30) {
                    flag = wiggleTailAnimation(this.sheepModelData.tailSwishSide, ageInTicks, sheep.tailFat);
                }

                if (isMoving) {
                    walkingLegsAnimation(limbSwing, limbSwingAmount);
                    if (flag) {
                        walkingTailAnimation(limbSwing, limbSwingAmount);
                    }
                } else {
                    standingLegsAnimation();
                    if (flag) {
                        stillTailAnimation();
                    }
                }
            }

            articulateLegs();

            saveAnimationValues(this.sheepModelData, sheep);
        }

    }

    private void headLookingAnimation(float netHeadYaw, float headPitch) {
        netHeadYaw = (netHeadYaw * 0.017453292F);
        headPitch = ((headPitch * 0.017453292F));
        float lookRotX = headPitch*0.65F;
        lookRotX = Math.min(lookRotX, 0.0F);
        float lookRotY = netHeadYaw*0.65F;

        this.theNeck.setXRot(this.lerpTo(this.theNeck.getXRot(), (headPitch-lookRotX)+(Mth.HALF_PI*0.5F)));
        this.theNeck.setYRot(this.lerpTo(this.theNeck.getYRot(), lookRotY));
        this.theHead.setXRot(this.lerpTo(this.theHead.getXRot(), lookRotX));
        this.theHead.setYRot(this.lerpTo(this.theHead.getYRot(), limit(netHeadYaw-lookRotY,Mth.HALF_PI*0.75F)));
        this.jaw.setXRot(this.lerpTo(this.jaw.getXRot(), Mth.HALF_PI*-0.2F));
    }

    private void moveHeadAnimation() {
        this.theNeck.setXRot(this.lerpTo(this.theNeck.getXRot(), Mth.HALF_PI * 0.5F));
        this.theNeck.setYRot(this.lerpTo(this.theNeck.getYRot(), 0.0F));
        this.theHead.setXRot(this.lerpTo(this.theHead.getXRot(), 0.0F));
        this.theHead.setYRot(this.lerpTo(this.theHead.getYRot(), 0.0F));
        this.jaw.setXRot(this.lerpTo(this.jaw.getXRot(), Mth.HALF_PI*-0.2F));
    }

    private void standUpAnimation(boolean isMoving, float limbSwing, float limbSwingAmount) {
        if (this.theSheep.getY() <= 14.0F) {
            //part 2
            if (this.theSheep.getY() == 14.0F) {
                float l = this.theLegFrontLeft.getXRot();
                float r = this.theLegFrontRight.getXRot();
                if (l <= Mth.HALF_PI * -0.8F || r <= Mth.HALF_PI * -0.8F) {
                    this.theSheep.setY(this.lerpTo(this.theSheep.getY(), 14.0F));
                    this.theSheep.setXRot(this.lerpTo(0.05F, this.theSheep.getXRot(), 0.0F));
                    if (isMoving) {
                        walkingLegsAnimation(limbSwing, limbSwingAmount);
                    } else {
                        standingLegsAnimation();
                    }
                } else if (l == r) {
                    if (ThreadLocalRandom.current().nextBoolean()) {
                        this.theLegFrontLeft.setXRot(l - 0.001F);
                    } else {
                        this.theLegFrontRight.setXRot(r - 0.001F);
                    }
                } else if (l < r) {
                    this.theLegFrontLeft.setXRot(this.lerpTo(l, Mth.HALF_PI * -0.9F));
                } else {
                    this.theLegFrontRight.setXRot(this.lerpTo(r, Mth.HALF_PI * -0.9F));
                }
            } else {
                this.theSheep.setY(this.lerpTo(0.1F, this.theSheep.getY(), 11.0F));
                this.theSheep.setXRot(this.lerpTo(0.05F, this.theSheep.getXRot(), 0.0F));
                if (isMoving) {
                    walkingLegsAnimation(limbSwing, limbSwingAmount);
                } else {
                    standingLegsAnimation();
                }
            }
        } else {
            //part 1
            this.theSheep.setY(this.lerpTo(0.05F, this.theSheep.getY(), 13.0F));
            this.theSheep.setXRot(this.lerpTo(this.theSheep.getXRot(), Mth.HALF_PI * -0.1F));
            this.theLegFrontLeft.setXRot(this.lerpTo(this.theLegFrontLeft.getXRot(), Mth.HALF_PI * 0.1F));
            this.theLegFrontRight.setXRot(this.lerpTo(this.theLegFrontRight.getXRot(), Mth.HALF_PI * 0.1F));
            this.theLegBottomFrontLeft.setXRot(this.lerpTo(this.theLegBottomFrontLeft.getXRot(), Mth.HALF_PI * 0.9F));
            this.theLegBottomFrontRight.setXRot(this.lerpTo(this.theLegBottomFrontRight.getXRot(), Mth.HALF_PI * 0.9F));
            this.theLegBackLeft.setXRot(this.lerpTo(this.theLegBackLeft.getXRot(), Mth.HALF_PI * -0.87F));
            this.theLegBackRight.setXRot(this.lerpTo(this.theLegBackRight.getXRot(), Mth.HALF_PI * -0.87F));
            this.theLegBottomBackLeft.setXRot(this.lerpTo(this.theLegBottomBackLeft.getXRot(), Mth.HALF_PI * 0.2F));
            this.theLegBottomBackRight.setXRot(this.lerpTo(this.theLegBottomBackRight.getXRot(), Mth.HALF_PI * 0.2F));
            if (theSheep.getY() < 3.0F) {
                this.theSheep.setY(3.0F);
            }
        }
    }
    
    private void layDownAnimation(boolean asleep) {
        float v = this.theSheep.getY();
        if (v >= 12.5F) {
            if (v < 18.1F) {
                this.theSheep.setY(11.0F + ((this.theLegBottomFrontLeft.getXRot()/Mth.HALF_PI * 1.6F)*7.1F));
            } else if (v > 18.1F) {
                this.theSheep.setY(18.1F);
            }
            if (asleep) {
                if (this.theNeck.getYRot() == 0) {
                    this.theNeck.setXRot(ThreadLocalRandom.current().nextBoolean() ? 0.0001F : -0.0001F);
                }
                if (this.theNeck.getYRot() > 0) {
                    this.theNeck.setXRot(this.lerpTo(0.025F, this.theNeck.getXRot(), Mth.PI * 0.55F));
                    this.theNeck.setYRot(this.lerpTo(0.025F, this.theNeck.getYRot(), 0.9F));
                    if (this.theNeck.getXRot() > Mth.HALF_PI) {
                        this.theHead.setYRot(this.lerpTo(0.025F, this.theHead.getYRot(), Mth.HALF_PI * 0.75F));
                    }
                } else {
                    this.theNeck.setXRot(this.lerpTo(0.025F, this.theNeck.getXRot(), Mth.PI * 0.55F));
                    this.theNeck.setYRot(this.lerpTo(0.025F, this.theNeck.getYRot(), -0.9F));
                    if (this.theNeck.getXRot() < -Mth.HALF_PI) {
                        this.theHead.setYRot(this.lerpTo(0.025F, this.theHead.getYRot(), Mth.HALF_PI * -0.75F));
                    }
                }
                this.theHead.setXRot(this.lerpTo(0.025F, this.theHead.getXRot(), 0.0F));
            }
            //part 2
            this.theSheep.setXRot(this.lerpTo(0.05F, this.theSheep.getXRot(), 0.0F));
            this.theLegFrontLeft.setXRot(this.lerpTo(0.025F, this.theLegFrontLeft.getXRot(), Mth.HALF_PI*-0.6F));
            this.theLegFrontRight.setXRot(this.lerpTo(0.025F, this.theLegFrontRight.getXRot(), Mth.HALF_PI*-0.6F));
            this.theLegBottomFrontLeft.setXRot(this.lerpTo(0.025F, this.theLegBottomFrontLeft.getXRot(), Mth.HALF_PI * 1.6F));
            this.theLegBottomFrontRight.setXRot(this.lerpTo(0.025F, this.theLegBottomFrontRight.getXRot(), Mth.HALF_PI * 1.6F));
            this.theLegBackLeft.setXRot(this.lerpTo(0.025F, this.theLegBackLeft.getXRot(), Mth.HALF_PI * -1.3F));
            this.theLegBackRight.setXRot(this.lerpTo(0.025F, this.theLegBackRight.getXRot(), Mth.HALF_PI * -1.3F));
            this.theLegBottomBackLeft.setXRot(this.lerpTo(0.025F, this.theLegBottomBackLeft.getXRot(), Mth.HALF_PI * 0.3F));
            this.theLegBottomBackRight.setXRot(this.lerpTo(0.025F, this.theLegBottomBackRight.getXRot(), Mth.HALF_PI * 0.3F));
        } else {
            //part1  -  y starts at 11.0F
            this.theSheep.setY(11.0F + (this.theLegBottomFrontLeft.getXRot()/Mth.HALF_PI)*3.1F);
            this.theSheep.setXRot(this.lerpTo(0.025F, this.theSheep.getXRot(), Mth.HALF_PI * 0.25F));
            this.theLegFrontLeft.setXRot(this.lerpTo(0.025F, this.theLegFrontLeft.getXRot(), Mth.HALF_PI * -0.25F));
            this.theLegFrontRight.setXRot(this.lerpTo(0.025F, this.theLegFrontRight.getXRot(), Mth.HALF_PI * -0.25F));
            this.theLegBottomFrontLeft.setXRot(this.lerpTo(0.025F, this.theLegBottomFrontLeft.getXRot(), Mth.HALF_PI));
            this.theLegBottomFrontRight.setXRot(this.lerpTo(0.025F, this.theLegBottomFrontRight.getXRot(), Mth.HALF_PI));
            this.theLegBackLeft.setXRot(this.lerpTo(0.025F, this.theLegBackLeft.getXRot(), Mth.HALF_PI * -0.25F));
            this.theLegBackRight.setXRot(this.lerpTo(0.025F, this.theLegBackRight.getXRot(), Mth.HALF_PI * -0.25F));
            this.theLegBottomBackLeft.setXRot(this.lerpTo(0.025F, this.theLegBottomBackLeft.getXRot(), 0.0F));
            this.theLegBottomBackRight.setXRot(this.lerpTo(0.025F, this.theLegBottomBackRight.getXRot(), 0.0F));
        }
    }

    private void standingLegsAnimation() {
        this.theLegFrontLeft.setXRot(this.lerpTo(this.theLegFrontLeft.getXRot(), 0.0F));
        this.theLegFrontRight.setXRot(this.lerpTo(this.theLegFrontRight.getXRot(), 0.0F));

        this.theLegBackLeft.setXRot(this.lerpTo(this.theLegBackLeft.getXRot(), 0.0F));
        this.theLegBackRight.setXRot(this.lerpTo(this.theLegBackRight.getXRot(), 0.0F));

        this.theLegBottomFrontLeft.setXRot(this.lerpTo(this.theLegBottomFrontLeft.getXRot(), 0.0F));
        this.theLegBottomFrontRight.setXRot(this.lerpTo(this.theLegBottomFrontRight.getXRot(), 0.0F));

        this.theLegBottomBackLeft.setXRot(this.lerpTo(this.theLegBottomBackLeft.getXRot(), 0.0F));
        this.theLegBottomBackRight.setXRot(this.lerpTo(this.theLegBottomBackRight.getXRot(), 0.0F));
    }

    private void walkingLegsAnimation(float limbSwing, float limbSwingAmount) {
        float f = (Mth.cos(limbSwing * 0.6662F)) * 1.4F * limbSwingAmount;
        float f1 = (Mth.cos(limbSwing * 0.6662F + Mth.PI) * 1.4F * limbSwingAmount);

        this.theLegFrontLeft.setXRot(f);
        this.theLegFrontRight.setXRot(f1);
        this.theLegBackLeft.setXRot(f);
        this.theLegBackRight.setXRot(f1);

        this.theLegBottomFrontLeft.setXRot(this.lerpTo(this.theLegBottomFrontLeft.getXRot(), 0.0F));
        this.theLegBottomFrontRight.setXRot(this.lerpTo(this.theLegBottomFrontRight.getXRot(), 0.0F));

        this.theLegBottomBackLeft.setXRot(this.lerpTo(this.theLegBottomBackLeft.getXRot(), 0.0F));
        this.theLegBottomBackRight.setXRot(this.lerpTo(this.theLegBottomBackRight.getXRot(), 0.0F));
    }

    private boolean grazingAnimation(float ticks) {
        if (ticks < 50) {
            float neckRot = this.theNeck.getXRot();
            if (neckRot < Mth.HALF_PI*0.75F) {
                this.theNeck.setXRot(this.lerpTo(this.theNeck.getXRot(), Mth.HALF_PI));
            } else if (neckRot < Mth.HALF_PI){
                this.theNeck.setXRot(this.lerpTo(this.theNeck.getXRot(), Mth.HALF_PI*1.05F));
                this.jaw.setXRot(this.lerpTo(this.jaw.getXRot(), Mth.HALF_PI*-0.2F));
            } else {
                float loop = (float) Math.cos(ticks*0.5F);
                if (loop > 0) {
                    this.theNeck.setXRot(this.lerpTo(this.theNeck.getXRot(), Mth.HALF_PI*1.4F));
                    this.theHead.setXRot(this.lerpTo(this.theHead.getXRot(), 0.0F));
                    this.jaw.setXRot(this.lerpTo(this.jaw.getXRot(), Mth.HALF_PI*-0.2F));
                } else {
                    this.theNeck.setXRot(this.lerpTo(this.theNeck.getXRot(), Mth.HALF_PI * 1.3F));
                    this.theHead.setXRot(this.lerpTo(this.theHead.getXRot(), -Mth.HALF_PI*0.2F));
                    this.jaw.setXRot(this.lerpTo(this.jaw.getXRot(), 0.0F));
                }
            }
            return false;
        } else {
            return true;
        }
    }

    private void twitchEarAnimation(boolean side, float ticks) {
        boolean direction = Math.cos(ticks*0.8F) > 0;
        if (side) {
            this.theEarLeft.setXRot(this.lerpTo(0.15F, this.theEarLeft.getXRot(), Mth.HALF_PI * 0.5F * (direction?-1F:0.5F)));
            this.theEarLeft.setYRot(this.lerpTo(0.15F, this.theEarLeft.getYRot(), Mth.HALF_PI * 0.25F * (direction?-1F:0.5F)));
            this.theEarRight.setXRot(this.lerpTo(0.15F, this.theEarRight.getXRot(), 0.0F));
            this.theEarRight.setYRot(this.lerpTo(0.15F, this.theEarRight.getYRot(), 0.0F));
        } else {
            this.theEarRight.setXRot(this.lerpTo(0.15F, this.theEarRight.getXRot(), Mth.HALF_PI * 0.5F * (direction?-1F:0.5F)));
            this.theEarRight.setYRot(this.lerpTo(0.15F, this.theEarRight.getYRot(), Mth.HALF_PI * 0.25F * (direction?1F:-0.5F)));
            this.theEarLeft.setXRot(this.lerpTo(0.15F, this.theEarLeft.getXRot(), 0.0F));
            this.theEarLeft.setYRot(this.lerpTo(0.15F, this.theEarLeft.getYRot(), 0.0F));
        }
    }

    private void stillTailAnimation() {
        this.tail[0].setZRot(this.lerpTo(0.1F, this.tail[0].getZRot(), 0.0F));
        this.tail[1].setZRot(this.lerpTo(0.1F, this.tail[1].getZRot(), 0.0F));
        this.tail[2].setZRot(this.lerpTo(0.1F, this.tail[2].getZRot(), 0.0F));
        this.tail[3].setZRot(this.lerpTo(0.1F, this.tail[3].getZRot(), 0.0F));
    }

    private boolean wiggleTailAnimation(boolean side, float ticks, float tailFat) {
        float loop = (side ? (float)Math.cos(ticks) : -(float)Math.cos(ticks)) * (1.0F-tailFat);

        this.tail[0].setZRot(Mth.HALF_PI * 0.7F * loop);
        this.tail[1].setZRot(Mth.HALF_PI * 0.5F * loop);
        this.tail[2].setZRot(Mth.HALF_PI * 0.3F * loop);
        this.tail[3].setZRot(Mth.HALF_PI * 0.2F * loop);

        return false;
    }

    private void walkingTailAnimation(float limbSwing, float limbSwingAmount) {
        float f = (Mth.cos(limbSwing * 0.6662F)) * limbSwingAmount;

        this.tail[0].setZRot(Mth.HALF_PI * 0.3F * f);
        this.tail[1].setZRot(Mth.HALF_PI * 0.2F * f);
        this.tail[2].setZRot(Mth.HALF_PI * 0.1F * f);
        this.tail[3].setZRot(Mth.HALF_PI * 0.1F * f);
    }

    private void articulateLegs() {
        float flag = this.theLegBottomFrontLeft.getXRot();
        if (flag > 0.0F) {
            this.theLegBottomFrontLeft.setZ(-3.0F + (9.0F * (Math.min(flag, Mth.PI * 0.6F) / Mth.PI * 0.6F)));
        } else if (this.theLegBottomFrontLeft.getZ() != -3.0F) {
            this.theLegBottomFrontLeft.setZ(-3.0F);
        }
        flag = this.theLegBottomFrontRight.getXRot();
        if (flag > 0.0F) {
            this.theLegBottomFrontRight.setZ(-3.0F + (9.0F * (Math.min(flag, Mth.PI * 0.6F) / Mth.PI * 0.6F)));
        } else if (this.theLegBottomFrontRight.getZ() != -3.0F) {
            this.theLegBottomFrontRight.setZ(-3.0F);
        }

        flag = this.theLegBottomBackLeft.getXRot();
        if (flag > 0.0F) {
            this.theLegBackLeft.setZ(5.0F + (flag * 8.0F));
            this.theLegBottomBackLeft.setY(5.0F - (5.0F * flag));
            this.theLegBottomBackLeft.setZ(3.0F * flag);
        } else if (flag == 0.0F) {
            this.theLegBackLeft.setY(3.0F);
            this.theLegBackLeft.setZ(5.0F);
            this.theLegBottomBackLeft.setY(5.0F);
            this.theLegBottomBackLeft.setZ(0.0F);
        }
        flag = this.theLegBottomBackRight.getXRot();
        if (flag > 0.0F) {
            this.theLegBackRight.setZ(5.0F + (flag * 8.0F));
            this.theLegBottomBackRight.setY(5.0F - (5.0F * flag));
            this.theLegBottomBackRight.setZ(3.0F * flag);
        } else if (flag == 0.0F) {
            this.theLegBackRight.setY(3.0F);
            this.theLegBackRight.setZ(5.0F);
            this.theLegBottomBackRight.setY(5.0F);
            this.theLegBottomBackRight.setZ(0.0F);
        }
    }

    private SheepModelData getCreateSheepModelData(T enhancedSheep) {
        return (SheepModelData) getCreateAnimalModelData(enhancedSheep);
    }

    @Override
    protected void setInitialModelData(T enhancedAnimal) {
        SheepModelData sheepModelData = new SheepModelData();
        setBaseInitialModelData(sheepModelData, enhancedAnimal);
    }

    @Override
    protected void additionalModelDataInfo(AnimalModelData animalModelData, T enhancedAnimal) {
        ((SheepModelData) animalModelData).bagSize = (enhancedAnimal.getEntityStatus().equals(EntityState.MOTHER.toString()) || enhancedAnimal.getEntityStatus().equals(EntityState.PREGNANT.toString())) ? enhancedAnimal.getBagSize() : -1.0F;
        ((SheepModelData) animalModelData).coatLength = Math.min(enhancedAnimal.getCoatLength(), 15);
    }

    @Override
    protected void additionalUpdateModelDataInfo(AnimalModelData animalModelData, T enhancedAnimal) {
        if (((SheepModelData)animalModelData).hornGrowth != 1.0F) {
            ((SheepModelData)animalModelData).hornGrowth = enhancedAnimal.hornGrowthAmount();
        }
        ((SheepModelData) animalModelData).bagSize = (enhancedAnimal.getEntityStatus().equals(EntityState.MOTHER.toString()) || enhancedAnimal.getEntityStatus().equals(EntityState.PREGNANT.toString())) ? enhancedAnimal.getBagSize() : -1.0F;
        ((SheepModelData) animalModelData).coatLength = Math.min(enhancedAnimal.getCoatLength(), 15);
        animalModelData.bridle = enhancedAnimal.hasBridle();
        animalModelData.chests = enhancedAnimal.hasChest();
    }

    @Override
    protected Phenotype createPhenotype(T enhancedAnimal) {
        return new SheepPhenotype(enhancedAnimal.getSharedGenes().getAutosomalGenes(), enhancedAnimal.getOrSetIsFemale(), enhancedAnimal.getStringUUID().toCharArray());
    }
}
