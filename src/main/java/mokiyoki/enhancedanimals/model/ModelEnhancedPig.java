package mokiyoki.enhancedanimals.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import mokiyoki.enhancedanimals.entity.EnhancedPig;
import mokiyoki.enhancedanimals.model.modeldata.AnimalModelData;
import mokiyoki.enhancedanimals.model.modeldata.Phenotype;
import mokiyoki.enhancedanimals.model.modeldata.PigModelData;
import mokiyoki.enhancedanimals.model.modeldata.PigPhenotype;
import mokiyoki.enhancedanimals.model.modeldata.SaddleType;
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
public class ModelEnhancedPig<T extends EnhancedPig> extends EnhancedAnimalModel<T> {
    protected WrappedModelPart thePig;

    protected WrappedModelPart theHead;
    protected WrappedModelPart theMouth;
    protected WrappedModelPart theEarLeft;
    protected WrappedModelPart theEarRight;
    protected WrappedModelPart theNeck;
    protected WrappedModelPart theBody;
    protected WrappedModelPart theButt;
    protected WrappedModelPart theLegFrontLeft;
    protected WrappedModelPart theLegFrontRight;
    protected WrappedModelPart theLegBackLeft;
    protected WrappedModelPart theLegBackRight;
    protected WrappedModelPart theTail;

    protected WrappedModelPart head;
    protected WrappedModelPart cheeks;
    protected WrappedModelPart theSnout;
    protected WrappedModelPart snout;
    protected WrappedModelPart jaw;
    protected WrappedModelPart tusksTopLeft;
    protected WrappedModelPart tusksTopRight;
    protected WrappedModelPart tusksBottomLeft;
    protected WrappedModelPart tusksBottomRight;
    
    protected WrappedModelPart earSmallLeft;
    protected WrappedModelPart earMediumLeft;
    protected WrappedModelPart earLargeLeft;

    protected WrappedModelPart earSmallRight;
    protected WrappedModelPart earMediumRight;
    protected WrappedModelPart earLargeRight;
    
    protected WrappedModelPart neckShort;
    protected WrappedModelPart neckLong;
    protected WrappedModelPart neckShortBig;
    protected WrappedModelPart neckLongBig;
    
    protected WrappedModelPart waddleLeft;
    protected WrappedModelPart waddleRight;
    
    protected WrappedModelPart body11;
    protected WrappedModelPart body12;
    protected WrappedModelPart body13;
    protected WrappedModelPart body14;
    protected WrappedModelPart body15;
    
    protected WrappedModelPart butt5;
    protected WrappedModelPart butt6;
    protected WrappedModelPart butt7;
    protected WrappedModelPart butt8;

    private WrappedModelPart legFrontLeft;
    private WrappedModelPart legFrontRight;
    private WrappedModelPart legBackLeft;
    private WrappedModelPart legBackRight;
    
    private WrappedModelPart tail0;
    private WrappedModelPart tail1;
    private WrappedModelPart tail2;
    private WrappedModelPart tail3;
    private WrappedModelPart tail4;
    private WrappedModelPart tail5;

    private PigModelData pigModelData;

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition base = meshdefinition.getRoot().addOrReplaceChild("base", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition bPig = base.addOrReplaceChild("bPig", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 16.0F, -4.0F, Mth.HALF_PI, 0.0F, 0.0F));
        PartDefinition bBody = bPig.addOrReplaceChild("bBody", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, -2.0F));
        PartDefinition bButt = bBody.addOrReplaceChild("bButt", CubeListBuilder.create(), PartPose.offset(0.0F, 11.0F, 0.0F));
        PartDefinition bNeck = bBody.addOrReplaceChild("bNeck", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 9.5F));
        PartDefinition bHead = bNeck.addOrReplaceChild("bHead", CubeListBuilder.create(), PartPose.offset(0.0F, -7.0F, -4.0F));
        PartDefinition bSnout = bHead.addOrReplaceChild("bSnout", CubeListBuilder.create(), PartPose.offset(0.0F, 3.5F, -3.0F));
            bHead.addOrReplaceChild("bMouth", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition bEarLeft = bHead.addOrReplaceChild("bEarL", CubeListBuilder.create(), PartPose.offset(4.0F, -3.0F, -3.0F));
        PartDefinition bEarRight = bHead.addOrReplaceChild("bEarR", CubeListBuilder.create(), PartPose.offset(-4.0F, -3.0F, -3.0F));
        PartDefinition bLegFrontLeft = bPig.addOrReplaceChild("bLegFL", CubeListBuilder.create(), PartPose.offset(4.0F, 0.0F, 0.0F));
        PartDefinition bLegFrontRight = bPig.addOrReplaceChild("bLegFR", CubeListBuilder.create(), PartPose.offset(-4.0F, 0.0F, 0.0F));
        PartDefinition bLegBackLeft = bPig.addOrReplaceChild("bLegBL", CubeListBuilder.create(), PartPose.offset(4.0F, 14.0F, -2.0F));
        PartDefinition bLegBackRight = bPig.addOrReplaceChild("bLegBR", CubeListBuilder.create(), PartPose.offset(-4.0F, 14.0F, -2.0F));
        PartDefinition bTail = bBody.addOrReplaceChild("bTail", CubeListBuilder.create(), PartPose.offset(0.0F, 6.0F, 8.0F));

        bHead.addOrReplaceChild("eyes", CubeListBuilder.create()
                        .texOffs(69, 15)
                        .addBox(2.5F, 0.0F, 0.0F, 1, 1, 1, new CubeDeformation(0.01F))
                        .texOffs(49, 15)
                        .addBox(-3.5F, 0.0F, 0.0F, 1, 1, 1, new CubeDeformation(0.01F)),
                PartPose.offset(0.0F, -5.0F, 0.0F)
        );

        bHead.addOrReplaceChild("head", CubeListBuilder.create()
                        .texOffs(49, 0)
                        .addBox(-3.5F, -5.0F, -4.0F, 7, 6, 7),
                PartPose.ZERO
        );
        bHead.addOrReplaceChild("cheeks", CubeListBuilder.create()
                        .texOffs(49, 13)
                        .addBox(-4.0F, 0.0F, 0.0F, 8, 5, 4, new CubeDeformation(0.25F)),
                PartPose.offset(0.0F, -5.5F, -4.0F)
        );

        bSnout.addOrReplaceChild("snout", CubeListBuilder.create()
                        .texOffs(49, 22)
                        .addBox(-2.0F, -5.0F, 0.0F, 4, 6, 3),
                PartPose.ZERO
        );

        bHead.addOrReplaceChild("jaw", CubeListBuilder.create()
                        .texOffs(63, 22)
                        .addBox(-1.0F, -5.0F, 0.0F, 2, 6, 1),
                PartPose.offset(0.0F, 4.5F, -4.0F)
        );

        bHead.addOrReplaceChild("tuskTL", CubeListBuilder.create()
                        .texOffs(69, 22)
                        .addBox(-0.5F, -2.0F, -0.5F, 1, 2, 1, new CubeDeformation(-0.1F)),
                PartPose.offsetAndRotation(0.5F, 0.0F, 0.5F, 0.0F, 0.0F, 1.3F)
        );
        bHead.addOrReplaceChild("tuskTR", CubeListBuilder.create()
                        .texOffs(69, 22)
                        .addBox(-0.5F, -2.0F, -0.5F, 1, 2, 1, new CubeDeformation(-0.1F)),
                PartPose.offsetAndRotation(-0.5F, 0.0F, 0.5F, 0.0F, 0.0F, -1.3F)
        );
        bHead.addOrReplaceChild("tuskBL", CubeListBuilder.create()
                        .texOffs(69, 22)
                        .addBox(-0.5F, -2.0F, -0.5F, 1, 2, 1, new CubeDeformation(-0.1F))
                        .addBox(-0.5F, -2.8F, -0.5F, 1, 1, 2, new CubeDeformation(-0.1F)),
                PartPose.offsetAndRotation(0.5F, 0.0F, 1.0F, 0.0F, 0.0F, 1.5F)
        );
        bHead.addOrReplaceChild("tuskBR", CubeListBuilder.create()
                        .texOffs(69, 22)
                        .addBox(-0.5F, -2.0F, -0.5F, 1, 2, 1, new CubeDeformation(-0.1F))
                        .addBox(-0.5F, -2.8F, -0.5F, 1, 1, 2, new CubeDeformation(-0.1F)),
                PartPose.offsetAndRotation(-0.5F, 0.0F, 1.0F, 0.0F, 0.0F, -1.5F)
        );

        bEarLeft.addOrReplaceChild("earSL", CubeListBuilder.create()
                        .texOffs(46, 0)
                        .addBox(-1.0F, -2.0F, 0.0F, 3, 4, 1),
                PartPose.ZERO
        );
        bEarLeft.addOrReplaceChild("earML", CubeListBuilder.create()
                        .texOffs(46, 0)
                        .addBox(-1.0F, -3.0F, 0.0F, 3.5F, 5, 1),
                PartPose.ZERO
        );
        bEarLeft.addOrReplaceChild("earLL", CubeListBuilder.create()
                        .texOffs(46, 0)
                        .addBox(-1.0F, -4.0F, 0.0F, 4, 6, 1),
                PartPose.ZERO
        );

        bEarRight.addOrReplaceChild("earSR", CubeListBuilder.create()
                        .texOffs(70, 0)
                        .addBox(-2.0F, -2.0F, 0.0F, 3, 4, 1),
                PartPose.ZERO
        );
        bEarRight.addOrReplaceChild("earMR", CubeListBuilder.create()
                        .texOffs(70, 0)
                        .addBox(-2.5F, -3.0F, 0.0F, 3.5F, 5, 1),
                PartPose.ZERO
        );
        bEarRight.addOrReplaceChild("earLR", CubeListBuilder.create()
                        .texOffs(70, 0)
                        .addBox(-3.0F, -4.0F, 0.0F, 4, 6, 1),
                PartPose.ZERO
        );

        bNeck.addOrReplaceChild("neckS", CubeListBuilder.create()
                        .texOffs(0, 0)
                        .addBox(-4.5F, -6.75F, -9.0F, 9, 7, 9),
                PartPose.ZERO
        );
        bNeck.addOrReplaceChild("neckL", CubeListBuilder.create()
                        .texOffs(0, 0)
                        .addBox(-4.5F, -6.75F, -9.0F, 9, 8, 9),
                PartPose.ZERO
        );
        bNeck.addOrReplaceChild("neckSB", CubeListBuilder.create()
                        .texOffs(0, 0)
                        .addBox(-4.5F, -5.75F, -9.0F, 9, 7, 9, new CubeDeformation(1.0F)),
                PartPose.ZERO
        );
        bNeck.addOrReplaceChild("neckLB", CubeListBuilder.create()
                        .texOffs(0, 0)
                        .addBox(-4.5F, -5.75F, -9.0F, 9, 7, 9, new CubeDeformation(1.0F)),
                PartPose.ZERO
        );
        bNeck.addOrReplaceChild("waddleL", CubeListBuilder.create()
                        .texOffs(64, 57)
                        .addBox(1.5F, 0.0F, -9.0F, 2, 4, 2),
                PartPose.offsetAndRotation(0.0F, 3.5F, -8.0F, -Mth.HALF_PI, 0.0F, 0.0F)
        );
        bNeck.addOrReplaceChild("waddleR", CubeListBuilder.create()
                        .texOffs(64,63)
                        .addBox(-3.5F, 0.0F, -9.0F, 2, 4, 2),
                PartPose.offsetAndRotation(0.0F, 3.5F, -8.0F, -Mth.HALF_PI, 0.0F, 0.0F)
        );

        bBody.addOrReplaceChild("body11", CubeListBuilder.create()
                        .texOffs(0, 23)
                        .addBox(-5.0F, 0.0F, 0.0F, 10, 11, 10),
                PartPose.ZERO
        );
        bBody.addOrReplaceChild("body12", CubeListBuilder.create()
                        .texOffs(0, 23)
                        .addBox(-5.0F, 0.0F, 0.0F, 10, 12, 10),
                PartPose.ZERO
        );
        bBody.addOrReplaceChild("body13", CubeListBuilder.create()
                        .texOffs(0, 23)
                        .addBox(-5.0F, 0.0F, 0.0F, 10, 13, 10),
                PartPose.ZERO
        );
        bBody.addOrReplaceChild("body14", CubeListBuilder.create()
                        .texOffs(0, 23)
                        .addBox(-5.0F, 0.0F, 0.0F, 10, 14, 10),
                PartPose.ZERO
        );
        bBody.addOrReplaceChild("body15", CubeListBuilder.create()
                        .texOffs(0, 23)
                        .addBox(-5.0F, 0.0F, 0.0F, 10, 15, 10),
                PartPose.ZERO
        );

        bButt.addOrReplaceChild("butt5", CubeListBuilder.create()
                        .texOffs(0, 53)
                        .addBox(-4.5F, 0.0F, 0.0F, 9, 5, 9),
                PartPose.ZERO
        );
        bButt.addOrReplaceChild("butt6", CubeListBuilder.create()
                        .texOffs(0, 53)
                        .addBox(-4.5F, 0.0F, 0.0F, 9, 6, 9),
                PartPose.ZERO
        );
        bButt.addOrReplaceChild("butt6_0", CubeListBuilder.create()
                        .texOffs(0, 53)
                        .addBox(-4.5F, -1F, 0.0F, 9, 6, 9),
                PartPose.ZERO
        );
        bButt.addOrReplaceChild("butt6_2", CubeListBuilder.create()
                        .texOffs(0, 53)
                        .addBox(-4.5F, 1.5F, 0.0F, 9, 6, 9),
                PartPose.ZERO
        );
        bButt.addOrReplaceChild("butt7", CubeListBuilder.create()
                        .texOffs(0, 53)
                        .addBox(-4.5F, 0.0F, 0.0F, 9, 7, 9),
                PartPose.ZERO
        );
        bButt.addOrReplaceChild("butt8", CubeListBuilder.create()
                        .texOffs(0, 53)
                        .addBox(-4.5F, 0.0F, 0.0F, 9, 8, 9),
                PartPose.ZERO
        );

        bLegFrontLeft.addOrReplaceChild("legFL", CubeListBuilder.create()
                        .texOffs(49, 32)
                        .addBox(-3.0F, 0.0F, -3.0F, 3, 8, 3),
                PartPose.ZERO
        );
        bLegFrontRight.addOrReplaceChild("legFR", CubeListBuilder.create()
                        .texOffs(61, 32)
                        .addBox(0.0F, 0.0F, -3.0F, 3, 8, 3),
                PartPose.ZERO
        );
        bLegBackLeft.addOrReplaceChild("legBL", CubeListBuilder.create()
                        .texOffs(49, 44)
                        .addBox(-3.0F, 0.0F, 0.0F, 3, 6, 3),
                PartPose.ZERO
        );
        bLegBackRight.addOrReplaceChild("legBR", CubeListBuilder.create()
                        .texOffs(61, 44)
                        .addBox(0.0F, 0.0F, 0.0F, 3, 6, 3),
                PartPose.ZERO
        );

        bTail.addOrReplaceChild("tail0", CubeListBuilder.create()
                        .texOffs(36, 0)
                        .addBox(-0.5F, 0.0F, 0.0F, 1, 2, 1, new CubeDeformation(-0.05F)),
                PartPose.ZERO
        );
        bTail.addOrReplaceChild("tail1", CubeListBuilder.create()
                        .texOffs(36, 3)
                        .addBox(-0.5F, 0.0F, 0.0F, 1, 2, 1, new CubeDeformation(-0.1F)),
                PartPose.offset(0.0F, 1.9F, 0.0F)
        );
        bTail.addOrReplaceChild("tail2", CubeListBuilder.create()
                        .texOffs(36, 6)
                        .addBox(-0.5F, 0.0F, 0.0F, 1, 2, 1, new CubeDeformation(-0.15F)),
                PartPose.offset(0.0F, 1.8F, 0.0F)
        );
        bTail.addOrReplaceChild("tail3", CubeListBuilder.create()
                        .texOffs(36, 9)
                        .addBox(-0.5F, 0.0F, 0.0F, 1, 2, 1, new CubeDeformation(-0.2F)),
                PartPose.offset(0.0F, 1.7F, 0.0F)
        );
        bTail.addOrReplaceChild("tail4", CubeListBuilder.create()
                        .texOffs(36, 12)
                        .addBox(-0.5F, 0.0F, 0.0F, 1, 2, 1, new CubeDeformation(-0.25F)),
                PartPose.offset(0.0F, 1.6F, 0.0F)
        );
        bTail.addOrReplaceChild("tail5", CubeListBuilder.create()
                        .texOffs(36, 15)
                        .addBox(-0.5F, 0.0F, 0.0F, 1, 2, 1, new CubeDeformation(-0.3F)),
                PartPose.offset(0.0F, 1.5F, 0.0F)
        );

        /**
         * Equipment stuff
         */
        base.addOrReplaceChild("chests", CubeListBuilder.create()
                        .texOffs(80, 14)
                        .addBox(-8.0F, 0.0F, 0.0F, 8, 8, 3)
                        .texOffs(80, 25)
                        .addBox(8.0F, 0.0F, 0.0F, 8, 8, 3),
                PartPose.offsetAndRotation(0.0F, 8.0F, 3.0F, 0.0F, Mth.HALF_PI, 0.0F)
        );
        base.addOrReplaceChild("saddle", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 4.0F, 10.0F, -Mth.HALF_PI, 0.0F, 0.0F));
        base.addOrReplaceChild("saddleW", CubeListBuilder.create()
                        .texOffs(210, 0)
                        .addBox(-5.0F, -2.0F, -5.0F, 10, 2, 13)
                        .texOffs(210, 15)
                        .addBox(-4.0F, -3.0F, 5.0F, 8, 2, 4)
                        .texOffs(230, 15)
                        .addBox(-3.5F, -4.0F, 8.0F, 7, 2, 2),
                PartPose.offsetAndRotation(0.0F, 4.0F, 10.0F, -Mth.HALF_PI, 0.0F, 0.0F)
        );
        base.addOrReplaceChild("saddleE", CubeListBuilder.create()
                        .texOffs(211, 1)
                        .addBox(-5.0F, -1.0F, -4.0F, 10, 2, 12)
                        .texOffs(210, 15)
                        .addBox(-4.0F, -1.5F, 5.0F, 8, 2, 4)
                        .texOffs(230, 15)
                        .addBox(-3.5F, -2.0F, 7.5F, 7, 2, 2),
                PartPose.offsetAndRotation(0.0F, 4.0F, 10.0F, -Mth.HALF_PI, 0.0F, 0.0F)
        );
        base.addOrReplaceChild("saddleH", CubeListBuilder.create()
                        .texOffs(234, 19)
                        .addBox(-4.0F, -2.0F, -3.0F, 8, 2, 3),
                PartPose.ZERO
        );
        base.addOrReplaceChild("saddleP", CubeListBuilder.create()
                        .texOffs(243, 0)
                        .addBox(-1.0F, -3.0F, -2.0F, 2, 4, 2, new CubeDeformation(-0.25F)),
                PartPose.offsetAndRotation(0.0F, -1.5F, -0.5F, -0.2F, 0.0F, 0.0F)
        );
        base.addOrReplaceChild("saddleL", CubeListBuilder.create()
                        .texOffs(234, 49)
                        .addBox(0.0F, 0.0F, 0.0F, 3, 4, 8),
                PartPose.ZERO
        );
        base.addOrReplaceChild("saddleR", CubeListBuilder.create()
                        .texOffs(234, 61)
                        .addBox(-3.0F, 0.0F, 0.0F, 3, 4, 8),
                PartPose.ZERO
        );
        base.addOrReplaceChild("stirrupWL", CubeListBuilder.create()
                        .texOffs(248, 24)
                        .addBox(0.0F, 0.0F, 0.0F, 0, 10, 4),
                PartPose.offset(7.5F, 0.0F, -3.5F)
        );
        base.addOrReplaceChild("stirrupWR", CubeListBuilder.create()
                        .texOffs(248, 24)
                        .addBox(0.0F, 0.0F, 0.0F, 0, 10, 4),
                PartPose.offset(-7.5F, 0.0F, -3.5F)
        );
        base.addOrReplaceChild("stirrupNL", CubeListBuilder.create()
                        .texOffs(249, 27)
                        .addBox(-1.0F, 0.0F, 0.0F, 1, 10, 1),
                PartPose.ZERO
        );
        base.addOrReplaceChild("stirrupNR", CubeListBuilder.create()
                        .texOffs(251, 27)
                        .addBox(0.0F, 0.0F, 0.0F, 1, 10, 1),
                PartPose.ZERO
        );
        base.addOrReplaceChild("stirrup", CubeListBuilder.create()
                        .texOffs(210, 0)
                        .addBox(-0.5F, 9.5F, -1.0F, 1, 1, 1)
                        .texOffs(214, 0)
                        .addBox(-0.5F, 9.5F, 1.0F, 1, 1, 1)
                        .texOffs(210, 2)
                        .addBox(-0.5F, 10.5F, -1.5F, 1, 3, 1)
                        .texOffs(214, 2)
                        .addBox(-0.5F, 10.5F, 1.5F, 1, 3, 1)
                        .texOffs(211, 7)
                        .addBox(-0.5F, 12.5F, -0.5F, 1, 1, 2),
                PartPose.ZERO
        );
        base.addOrReplaceChild("saddlePad", CubeListBuilder.create()
                        .texOffs(194, 24)
                        .addBox(-8.0F, -1.0F, -6.0F, 16, 10, 15, new CubeDeformation(-1.0F)),
                PartPose.ZERO
        );
        base.addOrReplaceChild("collar", CubeListBuilder.create()
                        .texOffs(80, 1)
                        .addBox(-5.5F, -5.5F, -3.0F, 11, 2, 11, new CubeDeformation(0.001F))
                        .texOffs(80, 4)
                        .addBox(0.0F, -5.0F, -5.0F, 0, 3, 3)
                        .texOffs(116, 6)
                        .addBox(-1.5F, -5.0F, -6.25F, 3, 3, 3),
                PartPose.offset(0.0F, -1.0F, -7.5F)
        );

        return LayerDefinition.create(meshdefinition, 256, 256);
    }

    public ModelEnhancedPig(ModelPart modelPart) {
        super(modelPart);
        ModelPart base = modelPart.getChild("base");
        ModelPart bPig = base.getChild("bPig");
        ModelPart bBody = bPig.getChild("bBody");
        ModelPart bButt = bBody.getChild("bButt");
        ModelPart bNeck = bBody.getChild("bNeck");
        ModelPart bHead = bNeck.getChild("bHead");
        ModelPart bMouth = bHead.getChild("bMouth");
        ModelPart bEarLeft = bHead.getChild("bEarL");
        ModelPart bEarRight = bHead.getChild("bEarR");
        ModelPart bSnout = bHead.getChild("bSnout");
        ModelPart bLegFL = bPig.getChild("bLegFL");
        ModelPart bLegFR = bPig.getChild("bLegFR");
        ModelPart bLegBL = bPig.getChild("bLegBL");
        ModelPart bLegBR = bPig.getChild("bLegBR");
        ModelPart bTail = bBody.getChild("bTail");

        this.thePig = new WrappedModelPart(bPig, "bPig");
        this.theBody = new WrappedModelPart(bBody, "bBody");
        this.theButt = new WrappedModelPart(bButt, "bButt");
        this.theNeck = new WrappedModelPart(bNeck, "bNeck");
        this.theHead = new WrappedModelPart(bHead, "bHead");
        this.theMouth = new WrappedModelPart(bMouth, "bMouth");
        this.theEarLeft = new WrappedModelPart(bEarLeft, "bEarL");
        this.theEarRight = new WrappedModelPart(bEarRight, "bEarR");
        this.theLegFrontLeft = new WrappedModelPart(bLegFL, "bLegFL");
        this.theLegFrontRight = new WrappedModelPart(bLegFR, "bLegFR");
        this.theLegBackLeft = new WrappedModelPart(bLegBL, "bLegBL");
        this.theLegBackRight = new WrappedModelPart(bLegBR, "bLegBR");
        this.theTail = new WrappedModelPart(bTail, "bTail");
        this.theSnout = new WrappedModelPart("bSnout", bHead);

        this.eyes = new WrappedModelPart("eyes", bHead);

        this.head = new WrappedModelPart("head", bHead);
        this.cheeks = new WrappedModelPart("cheeks", bHead);
        this.snout = new WrappedModelPart("snout", bSnout);
        this.tusksTopLeft = new WrappedModelPart("tuskTL", bHead);
        this.tusksTopRight = new WrappedModelPart("tuskTR", bHead);
        
        this.jaw = new WrappedModelPart("jaw", bHead);
        this.tusksBottomLeft = new WrappedModelPart("tuskBL", bHead);
        this.tusksBottomRight = new WrappedModelPart("tuskBR", bHead);
        
        this.earSmallLeft = new WrappedModelPart("earSL", bEarLeft);
        this.earMediumLeft = new WrappedModelPart("earML", bEarLeft);
        this.earLargeLeft = new WrappedModelPart("earLL", bEarLeft);

        this.earSmallRight = new WrappedModelPart("earSR", bEarRight);
        this.earMediumRight = new WrappedModelPart("earMR", bEarRight);
        this.earLargeRight = new WrappedModelPart("earLR", bEarRight);
        
        this.neckShort = new WrappedModelPart("neckS", bNeck);
        this.neckLong = new WrappedModelPart("neckL", bNeck);
        this.neckShortBig = new WrappedModelPart("neckSB", bNeck);
        this.neckLongBig = new WrappedModelPart("neckLB", bNeck);
        
        this.waddleLeft = new WrappedModelPart("waddleL", bNeck);
        this.waddleRight = new WrappedModelPart("waddleR", bNeck);
        
        this.body11 = new WrappedModelPart("body11", bBody);
        this.body12 = new WrappedModelPart("body12", bBody);
        this.body13 = new WrappedModelPart("body13", bBody);
        this.body14 = new WrappedModelPart("body14", bBody);
        this.body15 = new WrappedModelPart("body15", bBody);
        
        this.butt5 = new WrappedModelPart("butt5", bButt);
        this.butt6 = new WrappedModelPart("butt6", bButt);
        this.butt7 = new WrappedModelPart("butt7", bButt);
        this.butt8 = new WrappedModelPart("butt8", bButt);
        
        this.legFrontLeft = new WrappedModelPart("legFL", bLegFL);
        this.legFrontRight = new WrappedModelPart("legFR", bLegFR);
        this.legBackLeft = new WrappedModelPart("legBL", bLegBL);
        this.legBackRight = new WrappedModelPart("legBR", bLegBR);
        
        this.tail0 = new WrappedModelPart("tail0", bTail);
        this.tail1 = new WrappedModelPart("tail1", bTail);
        this.tail2 = new WrappedModelPart("tail2", bTail);
        this.tail3 = new WrappedModelPart("tail3", bTail);
        this.tail4 = new WrappedModelPart("tail4", bTail);
        this.tail5 = new WrappedModelPart("tail5", bTail);
        
        this.thePig.addChild(this.theBody);
        this.theBody.addChild(this.theNeck);
        this.theBody.addChild(this.theButt);
        this.theButt.addChild(this.theTail);
        this.theNeck.addChild(this.theHead);
        this.theHead.addChild(this.theMouth);
        this.theHead.addChild(this.theEarLeft);
        this.theHead.addChild(this.theEarRight);
        this.thePig.addChild(this.theLegFrontLeft);
        this.thePig.addChild(this.theLegFrontRight);
        this.thePig.addChild(this.theLegBackLeft);
        this.thePig.addChild(this.theLegBackRight);
        
        this.theHead.addChild(this.head);
        this.theHead.addChild(this.eyes);
        this.theHead.addChild(this.cheeks);

        this.theEarLeft.addChild(this.earSmallLeft);
        this.theEarLeft.addChild(this.earMediumLeft);
        this.theEarLeft.addChild(this.earLargeLeft);

        this.theEarRight.addChild(this.earSmallRight);
        this.theEarRight.addChild(this.earMediumRight);
        this.theEarRight.addChild(this.earLargeRight);

        this.theMouth.addChild(this.theSnout);
        this.theMouth.addChild(this.jaw);

        this.theSnout.addChild(this.snout);
        this.snout.addChild(this.tusksTopLeft);
        this.snout.addChild(this.tusksTopRight);
        
        this.jaw.addChild(this.tusksBottomLeft);
        this.jaw.addChild(this.tusksBottomRight);
        
        this.theNeck.addChild(this.neckShort);
        this.theNeck.addChild(this.neckLong);
        this.theNeck.addChild(this.neckShortBig);
        this.theNeck.addChild(this.neckLongBig);
        this.theNeck.addChild(this.waddleLeft);
        this.theNeck.addChild(this.waddleRight);
        
        this.theBody.addChild(this.body11);
        this.theBody.addChild(this.body12);
        this.theBody.addChild(this.body13);
        this.theBody.addChild(this.body14);
        this.theBody.addChild(this.body15);

        this.theButt.addChild(this.butt5);
        this.theButt.addChild(this.butt6);
        this.theButt.addChild(this.butt7);
        this.theButt.addChild(this.butt8);

        this.theLegFrontLeft.addChild(this.legFrontLeft);

        this.theLegFrontRight.addChild(this.legFrontRight);

        this.theLegBackLeft.addChild(this.legBackLeft);

        this.theLegBackRight.addChild(this.legBackRight);

        this.theTail.addChild(this.tail0);
        this.tail0.addChild(this.tail1);
        this.tail1.addChild(this.tail2);
        this.tail2.addChild(this.tail3);
        this.tail3.addChild(this.tail4);
        this.tail4.addChild(this.tail5);

        /**
         *      Equipment
         */
        this.collar = new WrappedModelPart("collar", base);
        this.chests = new WrappedModelPart("chests", base);
        this.saddleWestern = new WrappedModelPart("saddleW", base);
        this.saddleEnglish = new WrappedModelPart("saddleE", base);
        this.saddleVanilla = new WrappedModelPart("saddle", base);
        this.saddleSideLeft = new WrappedModelPart("saddleL", base);
        this.saddleSideRight = new WrappedModelPart("saddleR", base);
        this.saddlePad = new WrappedModelPart("saddlePad", base);
        this.saddleHorn = new WrappedModelPart("saddleH", base);
        this.saddlePomel = new WrappedModelPart("saddleP", base);
        this.stirrupWideLeft = new WrappedModelPart("stirrupWL", base);
        this.stirrupWideRight = new WrappedModelPart("stirrupWR", base);
        this.stirrupNarrowLeft = new WrappedModelPart("stirrupNL", base);
        this.stirrupNarrowRight = new WrappedModelPart("stirrupNR", base);
        this.stirrup = new WrappedModelPart("stirrup", base);

        this.theNeck.addChild(this.collar);
        this.theBody.addChild(this.saddleWestern);
        this.theBody.addChild(this.saddleEnglish);
        this.theBody.addChild(this.saddleVanilla);

        this.saddleWestern.addChild(this.saddleHorn);
        this.saddleWestern.addChild(this.saddleSideLeft);
        this.saddleWestern.addChild(this.saddleSideRight);
        this.saddleWestern.addChild(this.saddlePad);
        this.saddleWestern.addChild(this.stirrupWideLeft);
        this.saddleWestern.addChild(this.stirrupWideRight);

        this.saddleEnglish.addChild(this.saddleHorn);
        this.saddleEnglish.addChild(this.saddleSideLeft);
        this.saddleEnglish.addChild(this.saddleSideRight);
        this.saddleEnglish.addChild(this.saddlePad);
        this.saddleEnglish.addChild(this.stirrupNarrowLeft);
        this.saddleEnglish.addChild(this.stirrupNarrowRight);

        this.saddleVanilla.addChild(this.saddlePad);
        this.saddleVanilla.addChild(this.stirrupNarrowLeft);
        this.saddleVanilla.addChild(this.stirrupNarrowRight);

        this.saddleHorn.addChild(this.saddlePomel);
        this.stirrupWideLeft.addChild(this.stirrup);
        this.stirrupWideRight.addChild(this.stirrup);
        this.stirrupNarrowLeft.addChild(this.stirrup);
        this.stirrupNarrowRight.addChild(this.stirrup);
    }

    private void resetCubes() {

    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        if (this.pigModelData!=null && this.pigModelData.getPhenotype() != null) {
            PigPhenotype pig = pigModelData.getPhenotype();

            resetCubes();
            super.renderToBuffer(this.pigModelData, poseStack, vertexConsumer, packedLightIn, packedOverlayIn, red, green, blue, alpha);

            Map<String, List<Float>> mapOfScale = new HashMap<>();

            this.waddleLeft.show(pig.hasWaddles);
            this.waddleRight.show(pig.hasWaddles);
            
            this.earSmallLeft.show(pig.earSizeMod == 0);
            this.earMediumLeft.show(pig.earSizeMod == 1);
            this.earLargeLeft.show(pig.earSizeMod == 2);

            this.earSmallRight.show(pig.earSizeMod == 0);
            this.earMediumRight.show(pig.earSizeMod == 1);
            this.earLargeRight.show(pig.earSizeMod == 2);
            
            this.neckShortBig.show(pig.shape == 0 || pig.shape == 1);
            this.neckShort.show(pig.shape == 2);
            this.neckLongBig.show(pig.shape == 3);
            this.neckLong.show(pig.shape == 4);

            this.body11.show(pig.shape == 0);
//            this.body12.show(pig.shape == 1);
            this.body12.show(pig.bodyLength == 12);
            //this.body13.show(pig.shape == 2);
            this.body13.show(pig.bodyLength == 13);
            this.body14.show(pig.bodyLength == 14);
            //this.body14.show(pig.shape == 3);
            this.body15.show(pig.shape == 4);
            
            this.butt5.show(pig.shape == 0 || pig.shape == 1);
            this.butt6.show(pig.shape == 2);
            this.butt7.show(pig.shape == 3);
            this.butt8.show(pig.shape == 4);

            if (pigModelData.saddle != SaddleType.NONE) {
                if (pigModelData.saddle == SaddleType.ENGLISH) {
                    mapOfScale.put("saddlePad", ModelHelper.createScalings(1.125F, 1.125F, 1.125F, 0.0F, -1.125F * 0.01F, (1.125F - 1.0F) * 0.04F));
                }
                mapOfScale.put(pigModelData.saddle.getName(), ModelHelper.createScalings((pig.bodyScale+pig.bodyWidth)*0.75F, pig.bodyScale*0.75F, pig.bodyScale*0.75F, 0.0F, (1.0F-pig.bodyScale) - (pig.bodyZ + 0.02F), ((0.75F - 1.0F) * 0.04F)*pig.lengthScaling));
            }

            float finalPigSize = ((3.0F * pigModelData.size * pigModelData.growthAmount) + pigModelData.size) / 4.0F;

            mapOfScale.put("neckS", pig.neckScalings);
            mapOfScale.put("butt6", pig.buttScalings);
            mapOfScale.put("body12", pig.bodyScalings);
            mapOfScale.put("body13", pig.bodyScalings);
            mapOfScale.put("body14", pig.bodyScalings);
            mapOfScale.put("cheeks", pig.cheekScalings);
            mapOfScale.put("bEarL", pig.earScalings);
            mapOfScale.put("bEarR", pig.earScalings);
            mapOfScale.put("bHead", pig.headScalings);

            poseStack.pushPose();
            poseStack.scale(finalPigSize, finalPigSize, finalPigSize);
            poseStack.translate(0.0F, -1.5F + 1.5F / finalPigSize, 0.0F);

            gaRender(this.thePig, mapOfScale, poseStack, vertexConsumer, packedLightIn, packedOverlayIn, red, green, blue, alpha);

            poseStack.popPose();
        }
    }

    protected void saveAnimationValues(PigModelData data, PigPhenotype pig) {
        Map<String, Vector3f> map = data.offsets;
        map.put("bPig", this.getRotationVector(this.thePig));
        map.put("bPigPos", this.getPosVector(this.thePig));
        map.put("bBodyPos", this.getPosVector(this.theBody));
        map.put("bSnout", this.getRotationVector(this.theSnout));
        map.put("snoutRot", this.getRotationVector(this.snout));
        map.put("snoutPos", this.getPosVector(this.snout));
        map.put("jaw", this.getRotationVector(this.jaw));
        map.put("bNeck", this.getRotationVector(this.theNeck));
        map.put("bHead", this.getRotationVector(this.theHead));
        if (data.growthAmount == 1.0F && !map.containsKey("bMouthPos")) {
            map.put("bMouthPos",this.getPosVector(this.theMouth));
            map.put("tuskPos",new Vector3f(-4.25F - (-2.5F * pig.snoutLength), -4.75F - (-2.5F * pig.snoutLength), 0.0F));
        }
        map.put("bEarL", this.getRotationVector(this.theEarLeft));
        map.put("bEarR", this.getRotationVector(this.theEarRight));
        map.put("bEarLPos", this.getPosVector(this.theEarLeft));
        map.put("bEarRPos", this.getPosVector(this.theEarRight));
        map.put("bLegFL", this.getRotationVector(this.theLegFrontLeft));
        map.put("bLegFR", this.getRotationVector(this.theLegFrontRight));
        map.put("bLegBL", this.getRotationVector(this.theLegBackLeft));
        map.put("bLegBLPos", this.getPosVector(this.theLegBackLeft));
        map.put("bLegBR", this.getRotationVector(this.theLegBackRight));
        map.put("bLegBRPos", this.getPosVector(this.theLegBackRight));
        map.put("bButtPos", this.getPosVector(this.theButt));
        map.put("tail0", this.getRotationVector(this.tail0));
        map.put("tail1", this.getRotationVector(this.tail1));
        map.put("tail2", this.getRotationVector(this.tail2));
        map.put("tail3", this.getRotationVector(this.tail3));
        map.put("tail4", this.getRotationVector(this.tail4));
        map.put("tail5", this.getRotationVector(this.tail5));
    }

    private void readInitialAnimationValues(PigModelData data, PigPhenotype pig) {
        Map<String, Vector3f> map = data.offsets;
        if (map.isEmpty()) {
            calculateEars(pig);
            calculateTail(pig);
            this.theMouth.setY((-4.0F-data.growthAmount) - ((2.5F + (2.5F * data.growthAmount)) * pig.snoutLength));
            this.theLegFrontLeft.setXRot(-Mth.HALF_PI);
            this.theLegFrontRight.setXRot(-Mth.HALF_PI);
            this.snout.setXRot(pig.snoutAngle);
            this.snout.setZ(5F*pig.snoutAngle);
            this.theLegBackLeft.setXRot(-Mth.HALF_PI);
            this.theLegBackRight.setXRot(-Mth.HALF_PI);
            this.theLegBackLeft.setY((pig.bodyLength+1)+pig.buttTranslation);
            this.theLegBackRight.setY((pig.bodyLength+1)+pig.buttTranslation);;
            this.theButt.setY((pig.bodyLength-2)+pig.buttTranslation);
            this.thePig.setZ(-(pig.buttTranslation+2.5F));
        } else {
            this.theLegBackLeft.setY(map.get("bLegBLPos").y());
            this.theLegBackRight.setY(map.get("bLegBRPos").y());;
            this.theButt.setY(map.get("bButtPos").y());
            this.thePig.setRotation(map.get("bPig"));
            this.thePig.setPos(map.get("bPigPos"));
            this.theNeck.setRotation(map.get("bNeck"));
            this.theHead.setRotation(map.get("bHead"));
            if (map.containsKey("bMouthPos")) {
                this.theMouth.setPos(map.get("bMouthPos"));
                this.tusksTopLeft.setY(map.get("tuskPos").x());
                this.tusksTopRight.setY(map.get("tuskPos").x());
                this.tusksBottomLeft.setY(map.get("tuskPos").y());
                this.tusksBottomRight.setY(map.get("tuskPos").y());
            } else {
                this.theMouth.setY((-4.0F-data.growthAmount) - ((2.5F + (2.5F * data.growthAmount)) * pig.snoutLength));
            }
            this.theSnout.setRotation(map.get("bSnout"));
            this.snout.setRotation(map.get("snoutRot"));
            this.snout.setPos(map.get("snoutPos"));
            this.jaw.setRotation(map.get("jaw"));
            this.theEarLeft.setRotation(map.get("bEarL"));
            this.theEarRight.setRotation(map.get("bEarR"));
            this.theEarLeft.setPos(map.get("bEarLPos"));
            this.theEarRight.setPos(map.get("bEarRPos"));
            this.theLegFrontLeft.setRotation(map.get("bLegFL"));
            this.theLegFrontRight.setRotation(map.get("bLegFR"));
            this.theLegBackLeft.setRotation(map.get("bLegBL"));
            this.theLegBackRight.setRotation(map.get("bLegBR"));
            this.tail0.setRotation(map.get("tail0"));
            this.tail1.setRotation(map.get("tail1"));
            this.tail2.setRotation(map.get("tail2"));
            this.tail3.setRotation(map.get("tail3"));
            this.tail4.setRotation(map.get("tail4"));
            this.tail5.setRotation(map.get("tail5"));
        }
    }

    private void calculateTail(PigPhenotype pig) {
        this.tail0.setXRot((1.5F * pig.tailCurlAmount) - Mth.HALF_PI);
        this.tail1.setXRot(1.1111F * pig.tailCurlAmount);
        this.tail2.setXRot(1.2222F * pig.tailCurlAmount);
        this.tail3.setXRot(1.3333F * pig.tailCurlAmount);
        this.tail4.setXRot(1.5F * pig.tailCurlAmount);
        this.tail5.setXRot(0.1F * pig.tailCurlAmount);

        if (pig.tailCurl) {
            this.tail0.setYRot(0.3555F * pig.tailCurlAmount);
            this.tail1.setYRot(0.3555F * pig.tailCurlAmount);
            this.tail2.setYRot(0.3555F * pig.tailCurlAmount);
            this.tail3.setYRot(0.3555F * pig.tailCurlAmount);
        } else {
            this.tail0.setYRot(-0.3555F * pig.tailCurlAmount);
            this.tail1.setYRot(-0.3555F * pig.tailCurlAmount);
            this.tail2.setYRot(-0.3555F * pig.tailCurlAmount);
            this.tail3.setYRot(-0.3555F * pig.tailCurlAmount);
        }
    }

    private void calculateEars(PigPhenotype pig) {
        float earFlopMod = pig.earFlopMod; //[-1.0 = full droop, -0.65 = half droop, -0.25 = over eyes flop, 0 = stiff flop, 0.5F is half flop, 1 is no flop]

        float earFlopContinuationMod;

        if (earFlopMod < -0.25) {
            earFlopContinuationMod = (1 + earFlopMod) / 0.75F;
            earFlopMod = -0.25F;
        } else {
            earFlopContinuationMod = 1.0F;
        }

        this.theEarLeft.setXRot(((-(float) Math.PI / 2.0F) * earFlopMod * earFlopContinuationMod) + ((((float) Math.PI / 2.2F)) * (1.0F - earFlopContinuationMod)));
        this.theEarRight.setXRot(((-(float) Math.PI / 2.0F) * earFlopMod * earFlopContinuationMod) + ((((float) Math.PI / 2.2F)) * (1.0F - earFlopContinuationMod)));

        this.theEarLeft.setYRot(((float) Math.PI / 3F) * earFlopContinuationMod);
        this.theEarRight.setYRot(-((float) Math.PI / 3F) * earFlopContinuationMod);

        this.theEarLeft.setZRot((-((float) Math.PI / 10F) * earFlopContinuationMod) + (((float) Math.PI / 2.2F) * (1.0F - earFlopContinuationMod)));
        this.theEarRight.setZRot((((float) Math.PI / 10F) * earFlopContinuationMod) - (((float) Math.PI / 2.2F) * (1.0F - earFlopContinuationMod)));

        if (earFlopMod == -0.25F) {
            this.theEarLeft.setZ(3.0F * earFlopContinuationMod);
            this.theEarRight.setZ(3.0F * earFlopContinuationMod);
        } else {
            this.theEarLeft.setZ(3.0F);
            this.theEarRight.setZ(3.0F);
        }
    }

    /**
     *      Animations
     */
    
    @Override
    public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.pigModelData = getCreatePigModelData(entityIn);

        if (this.pigModelData!=null) {
            PigPhenotype pig = this.pigModelData.getPhenotype();
            readInitialAnimationValues(this.pigModelData, pig);
            boolean isMoving = entityIn.getDeltaMovement().horizontalDistanceSqr() > 1.0E-7D || entityIn.xOld != entityIn.getX() || entityIn.zOld != entityIn.getZ();

            if (this.pigModelData.earTwitchTimer <= ageInTicks) {
                float[] oldRot;
                boolean flag;
                if (this.pigModelData.earTwitchSide) {
                    oldRot = new float[]{this.theEarLeft.getXRot(), this.theEarLeft.getYRot(), this.theEarLeft.getZRot()};
                    calculateEars(pig);
                    flag = oldRot[0] == this.theEarLeft.getXRot() && oldRot[1] == this.theEarLeft.getYRot() && oldRot[2] == this.theEarLeft.getZRot();
                } else {
                    oldRot = new float[]{this.theEarRight.getXRot(), this.theEarRight.getYRot(), this.theEarRight.getZRot()};
                    calculateEars(pig);
                    flag = oldRot[0] == this.theEarRight.getXRot() && oldRot[1] == this.theEarRight.getYRot() && oldRot[2] == this.theEarRight.getZRot();
                }

                if (flag) {
                    this.pigModelData.earTwitchSide = entityIn.getRandom().nextBoolean();
                    this.pigModelData.earTwitchTimer = (int) ageInTicks + (entityIn.getRandom().nextInt(this.pigModelData.sleeping ? 60 : 30) * 20) + 30;
                } else {
                    if (this.pigModelData.earTwitchSide) {
                        this.theEarLeft.setXRot(this.lerpTo(oldRot[0], this.theEarLeft.getXRot()));
                        this.theEarLeft.setYRot(this.lerpTo(oldRot[1], this.theEarLeft.getYRot()));
                        this.theEarLeft.setZRot(this.lerpTo(oldRot[2], this.theEarLeft.getZRot()));
                    } else {
                        this.theEarRight.setXRot(this.lerpTo(oldRot[0], this.theEarRight.getXRot()));
                        this.theEarRight.setYRot(this.lerpTo(oldRot[1], this.theEarRight.getYRot()));
                        this.theEarRight.setZRot(this.lerpTo(oldRot[2], this.theEarRight.getZRot()));
                    }
                }
            } else if (this.pigModelData.earTwitchTimer <= ageInTicks + 30) {
                twitchEarAnimation(this.pigModelData.earTwitchSide, (int)ageInTicks);
            }

            if (this.pigModelData.sleeping && !isMoving) {
                if (this.thePig.getY() < 22.0F && this.thePig.getZRot() == 0.0F) {
                    layDownAnimation();
                } else {
                    rollOnSideAnimation();
                }
            } else {

                if (this.thePig.getY() != 16.0F) {
                    if (this.thePig.getZRot() == 0.0F) {
                        standUpAnimation();
                    } else {
                        rightSelfAnimation();
                    }
                }

                boolean flag = true;
                if (this.pigModelData.isEating != 0) {
                    if (this.pigModelData.isEating == -1) {
                        this.pigModelData.isEating = (int)ageInTicks + 90;
                    } else if (this.pigModelData.isEating < ageInTicks) {
                        this.pigModelData.isEating = 0;
                    }
                    flag = grazingAnimation(this.pigModelData.isEating - (int)ageInTicks);
                }

                if (flag) {
                    if (netHeadYaw == 0 && headPitch == 0) {
                        moveHeadAnimation();
                    } else {
                        headLookingAnimation(netHeadYaw, headPitch);
                    }
                }

                if (isMoving) {
                    walkingLegsAnimation(limbSwing, limbSwingAmount);
                } else if (this.thePig.getY() == 16.0F ){
                    standingLegsAnimation();
                }
            }

            if (this.pigModelData.saddle != SaddleType.NONE) {
                switch (this.pigModelData.saddle) {
                    case VANILLA -> {
                        this.stirrupNarrowLeft.setPos(8.0F, 0.0F, 0.0F);
                        this.stirrupNarrowRight.setPos(-8.0F, 0.0F, 0.0F);
                    }
                    case ENGLISH -> {
                        this.saddleSideLeft.setPos(3.25F, -0.5F, -4.0F);
                        this.saddleSideRight.setPos(-3.25F, -0.5F, -4.0F);
                        this.saddleHorn.setPos(0.0F, -1.0F, -1.0F);
                        this.saddleHorn.setXRot(Mth.PI / 4.5F);
                        this.stirrupNarrowLeft.setPos(8.0F, -0.5F, -1.5F);
                        this.stirrupNarrowRight.setPos(-8.0F, -0.5F, -1.5F);
                    }
                    case WESTERN -> {
                        this.saddleSideLeft.setPos(5.0F, -1.0F, -5.25F);
                        this.saddleSideRight.setPos(-5.0F, -1.0F, -5.25F);
                        this.saddleHorn.setPos(0.0F, -2.0F, -2.0F);
                        this.saddleHorn.setXRot(Mth.PI * 0.125F);
                    }
                }
            }

           saveAnimationValues(this.pigModelData, pig);
        }

    }

    private boolean grazingAnimation(float ticks) {
        if (ticks < 50) {
            float neckRot = this.theNeck.getXRot();
            if (neckRot < Mth.HALF_PI*0.1F) {
                this.theNeck.setXRot(this.lerpTo(this.theNeck.getXRot(), Mth.HALF_PI*0.2F));
            } else if (neckRot < Mth.HALF_PI*0.17F){
                this.theNeck.setXRot(this.lerpTo(this.theNeck.getXRot(), Mth.HALF_PI*0.2F));
                this.theHead.setXRot(this.lerpTo(this.theHead.getXRot(), Mth.HALF_PI*0.1F));
                this.theSnout.setXRot(this.lerpTo(this.theSnout.getXRot(), Mth.HALF_PI*0.0F));
                this.jaw.setXRot(this.lerpTo(this.jaw.getXRot(), Mth.HALF_PI*-0.1F));
            } else {
                float loop = (float) Math.cos(ticks*0.5F);
                if (loop > 0) {
                    this.theNeck.setXRot(this.lerpTo(this.theNeck.getXRot(), Mth.HALF_PI*0.2F));
                    this.theHead.setXRot(this.lerpTo(this.theHead.getXRot(), Mth.HALF_PI*0.3F));
                    this.theSnout.setXRot(this.lerpTo(this.theSnout.getXRot(), Mth.HALF_PI*0.0F));
                    this.jaw.setXRot(this.lerpTo(this.jaw.getXRot(), Mth.HALF_PI*-0.2F));
                } else {
                    this.theNeck.setXRot(this.lerpTo(this.theNeck.getXRot(), Mth.HALF_PI*0.2F));
                    this.theHead.setXRot(this.lerpTo(this.theHead.getXRot(), Mth.HALF_PI*0.1F));
                    this.theSnout.setXRot(this.lerpTo(this.theSnout.getXRot(), Mth.HALF_PI*-0.2F));
                    this.jaw.setXRot(this.lerpTo(this.jaw.getXRot(), Mth.HALF_PI*-0.05F));
                }
            }
            return false;
        } else {
            return true;
        }
    }

    private void rightSelfAnimation() {
        float zRot = this.thePig.getZRot();
        this.thePig.setZRot(zRot > 0.0F ? Math.max(zRot - 0.01F, 0.0F) : Math.min(zRot + 0.01F, 0.0F));
        if (this.thePig.getZRot()!=0.0F) {
            this.thePig.setX(this.lerpTo(0.02F, this.thePig.getX(), 0.0F));
            this.thePig.setY(this.lerpTo(0.02F, this.thePig.getY(), 22.0F));
            this.thePig.setXRot(this.lerpTo(0.02F, this.thePig.getXRot(), Mth.HALF_PI));
            this.theLegFrontLeft.setXRot(this.lerpTo(0.02F, this.theLegFrontLeft.getXRot(), 0.0F));
            this.theLegFrontLeft.setYRot(this.lerpTo(0.02F, this.theLegFrontLeft.getYRot(), 0.0F));
            this.theLegFrontRight.setXRot(this.lerpTo(0.02F, this.theLegFrontRight.getXRot(), 0.0F));
            this.theLegFrontRight.setYRot(this.lerpTo(0.02F, this.theLegFrontRight.getYRot(), 0.0F));
            this.theLegBackLeft.setXRot(this.lerpTo(0.02F, this.theLegBackLeft.getXRot(), -Mth.PI));
            this.theLegBackLeft.setYRot(this.lerpTo(0.02F, this.theLegBackLeft.getYRot(), 0.0F));
            this.theLegBackRight.setXRot(this.lerpTo(0.02F, this.theLegBackRight.getXRot(), -Mth.PI));
            this.theLegBackRight.setYRot(this.lerpTo(0.02F, this.theLegBackRight.getYRot(), 0.0F));
        } else {
            this.thePig.setX(0.0F);
            this.thePig.setY(22.0F);
            this.thePig.setXRot(Mth.HALF_PI);
            this.theLegFrontLeft.setXRot(0.0F);
            this.theLegFrontLeft.setYRot(0.0F);
            this.theLegFrontRight.setXRot(0.0F);
            this.theLegFrontRight.setYRot(0.0F);
            this.theLegBackLeft.setXRot(-Mth.PI);
            this.theLegBackLeft.setYRot(0.0F);
            this.theLegBackRight.setXRot(-Mth.PI);
            this.theLegBackRight.setYRot(0.0F);
        }
    }

    private void standUpAnimation() {
        if (this.thePig.getY() < 20.0F) {
            this.thePig.setY(this.lerpTo(0.02F,this.thePig.getY(), 16.0F));
            this.thePig.setXRot(this.lerpTo(0.02F,this.thePig.getXRot(), Mth.HALF_PI));
            this.theLegFrontLeft.setXRot(this.lerpTo(this.theLegFrontLeft.getXRot(), -Mth.HALF_PI));
            this.theLegFrontRight.setXRot(this.lerpTo(this.theLegFrontRight.getXRot(), -Mth.HALF_PI));
            this.theLegBackLeft.setXRot(this.lerpTo(0.05F,this.theLegBackLeft.getXRot(), -Mth.HALF_PI));
            this.theLegBackRight.setXRot(this.lerpTo(0.05F,this.theLegBackRight.getXRot(), -Mth.HALF_PI));
        } else {
            this.thePig.setY(this.lerpTo(0.02F,this.thePig.getY(), 19.8F));
            this.thePig.setXRot(this.lerpTo(0.02F,this.thePig.getXRot(), Mth.HALF_PI*0.85F));
            this.theLegFrontLeft.setXRot(this.lerpTo(0.05F,this.theLegFrontLeft.getXRot(), -Mth.HALF_PI));
            this.theLegFrontRight.setXRot(this.lerpTo(0.05F,this.theLegFrontRight.getXRot(), -Mth.HALF_PI));
            this.theLegBackLeft.setXRot(this.lerpTo(0.05F, this.theLegBackLeft.getXRot(), -Mth.HALF_PI * 1.85F));
            this.theLegBackRight.setXRot(this.lerpTo(0.05F, this.theLegBackRight.getXRot(), -Mth.HALF_PI * 1.85F));
        }
    }

    private void layDownAnimation() {
        if (this.thePig.getY() >= 20.0F) {
            this.thePig.setY(this.lerpTo(0.02F, this.thePig.getY(), 22.2F));
            this.thePig.setXRot(this.lerpTo(0.02F, this.thePig.getXRot(), Mth.HALF_PI));
            this.theLegFrontLeft.setXRot(this.lerpTo(0.02F, this.theLegFrontLeft.getXRot(), 0.0F));
            this.theLegFrontRight.setXRot(this.lerpTo(0.02F, this.theLegFrontRight.getXRot(), 0.0F));
            this.theLegBackLeft.setXRot(this.lerpTo(0.02F, this.theLegBackLeft.getXRot(), -Mth.PI));
            this.theLegBackRight.setXRot(this.lerpTo(0.02F, this.theLegBackRight.getXRot(), -Mth.PI));
        } else {
            this.thePig.setY(this.lerpTo(0.02F, this.thePig.getY(), 20.2F));
            this.thePig.setXRot(this.lerpTo(0.02F, this.thePig.getXRot(), Mth.HALF_PI*1.15F));
            this.theLegFrontLeft.setXRot(this.lerpTo(0.02F, this.theLegFrontLeft.getXRot(), -Mth.HALF_PI * 0.4F));
            this.theLegFrontRight.setXRot(this.lerpTo(0.02F, this.theLegFrontRight.getXRot(), -Mth.HALF_PI * 0.4F));
            this.theLegBackLeft.setXRot(this.lerpTo(this.theLegBackLeft.getXRot(), -Mth.HALF_PI));
            this.theLegBackRight.setXRot(this.lerpTo(this.theLegBackRight.getXRot(), -Mth.HALF_PI));
        }
    }
    private void rollOnSideAnimation() {
        if (this.thePig.getZRot() == 0.0F) {
            this.thePig.setZRot(ThreadLocalRandom.current().nextBoolean() ? 0.0001F : -0.0001F);
        }
        if (this.thePig.getZRot() > 0.0F) {
            this.thePig.setZRot(this.lerpTo(this.thePig.getZRot(), Mth.HALF_PI));
            this.theNeck.setZRot(this.lerpTo(this.theNeck.getZRot(), Mth.HALF_PI * 0.05F));
            this.theHead.setZRot(this.lerpTo(this.theHead.getZRot(), Mth.HALF_PI * 0.1F));
            this.theLegFrontLeft.setXRot(this.lerpTo(this.theLegFrontLeft.getXRot(), -Mth.HALF_PI * 0.75F));
            this.theLegFrontRight.setXRot(this.lerpTo(this.theLegFrontRight.getXRot(), -Mth.HALF_PI));
            this.theLegFrontRight.setYRot(this.lerpTo(this.theLegFrontRight.getYRot(), -Mth.HALF_PI * 0.25F));
            this.theLegBackLeft.setXRot(this.lerpTo(this.theLegBackLeft.getXRot(), -Mth.HALF_PI * 1.25F));
            this.theLegBackRight.setXRot(this.lerpTo(this.theLegBackRight.getXRot(), -Mth.HALF_PI));
            this.theLegBackRight.setYRot(this.lerpTo(this.theLegBackRight.getYRot(), -Mth.HALF_PI * 0.25F));

        } else {
            this.thePig.setZRot(this.lerpTo(this.thePig.getZRot(), -Mth.HALF_PI));
            this.theNeck.setZRot(this.lerpTo(this.theNeck.getZRot(), -Mth.HALF_PI * 0.05F));
            this.theHead.setZRot(this.lerpTo(this.theHead.getZRot(), -Mth.HALF_PI * 0.1F));
            this.theLegFrontLeft.setXRot(this.lerpTo(this.theLegFrontLeft.getXRot(), -Mth.HALF_PI));
            this.theLegFrontLeft.setYRot(this.lerpTo(this.theLegFrontLeft.getYRot(), Mth.HALF_PI * 0.25F));
            this.theLegFrontRight.setXRot(this.lerpTo(this.theLegFrontRight.getXRot(), -Mth.HALF_PI * 0.75F));
            this.theLegBackLeft.setXRot(this.lerpTo(this.theLegBackLeft.getXRot(), -Mth.HALF_PI));
            this.theLegBackLeft.setYRot(this.lerpTo(this.theLegBackLeft.getYRot(), Mth.HALF_PI * 0.25F));
            this.theLegBackRight.setXRot(this.lerpTo(this.theLegBackRight.getXRot(), -Mth.HALF_PI * 1.25F));
        }
        this.thePig.setY(this.lerpTo(this.thePig.getY(), 20.0F));
        this.theNeck.setXRot(this.lerpTo(this.theNeck.getXRot(), 0.0F));
        this.theHead.setXRot(this.lerpTo(this.theHead.getXRot(), 0.0F));
    }

    private void headLookingAnimation(float netHeadYaw, float headPitch) {
        netHeadYaw = (netHeadYaw * 0.017453292F);
        headPitch = ((headPitch * 0.017453292F));
        float lookRotX = Math.min((headPitch * 0.65F), 0.0F);
        float lookRotZ = netHeadYaw * 0.2F;

        this.theNeck.setXRot(this.lerpTo(this.theNeck.getXRot(), headPitch - lookRotX));
        this.theNeck.setZRot(this.lerpTo(this.theNeck.getZRot(), -lookRotZ));
        this.theHead.setXRot(this.lerpTo(this.theHead.getXRot(), lookRotX));
        this.theHead.setZRot(this.lerpTo(this.theHead.getZRot(), -limit(netHeadYaw - lookRotZ, Mth.HALF_PI * 0.75F)));
        this.jaw.setXRot(this.lerpTo(this.jaw.getXRot(), 0.0F));
    }

    private void moveHeadAnimation() {
        this.lerpPart(this.theNeck, 0.0F, 0.0F, 0.0F);
        this.lerpPart(this.theHead, 0.0F, 0.0F, 0.0F);
        this.theSnout.setXRot(this.lerpTo(this.theSnout.getXRot(), 0.0F));
        this.jaw.setXRot(this.lerpTo(this.jaw.getXRot(), 0.0F));
    }

    private void walkingLegsAnimation(float limbSwing, float limbSwingAmount) {
        float f = -Mth.HALF_PI + ((Mth.cos(limbSwing * 0.6662F)) * 1.4F * limbSwingAmount);
        float f1 = -Mth.HALF_PI + (Mth.cos(limbSwing * 0.6662F + Mth.PI) * 1.4F * limbSwingAmount);

        this.theLegFrontLeft.setXRot(f);
        this.theLegFrontRight.setXRot(f1);
        this.theLegBackLeft.setXRot(f);
        this.theLegBackRight.setXRot(f1);
    }

    private void standingLegsAnimation() {
        this.theLegFrontLeft.setXRot(this.lerpTo(this.theLegFrontLeft.getXRot(), -Mth.HALF_PI));
        this.theLegFrontRight.setXRot(this.lerpTo(this.theLegFrontRight.getXRot(), -Mth.HALF_PI));
        this.theLegBackLeft.setXRot(this.lerpTo(this.theLegBackLeft.getXRot(), -Mth.HALF_PI));
        this.theLegBackRight.setXRot(this.lerpTo(this.theLegBackRight.getXRot(), -Mth.HALF_PI));
    }

    private void twitchEarAnimation(boolean side, float ticks) {
        boolean direction = Math.cos(ticks*0.8F) > 0;
        if (side) {
            this.theEarLeft.setXRot(this.theEarLeft.getXRot() + (direction ? 0.07F : -0.07F));
            this.theEarLeft.setZRot(this.theEarLeft.getZRot() + (direction ? 0.05F : -0.05F));
        } else {
            this.theEarRight.setXRot(this.theEarRight.getXRot() + (direction ? -0.07F : 0.07F));
            this.theEarRight.setZRot(this.theEarRight.getZRot() + (direction ? -0.05F : 0.05F));
        }
    }

    private PigModelData getCreatePigModelData(T enhancedPig) {
        return (PigModelData) getCreateAnimalModelData(enhancedPig);
    }

    @Override
    protected void setInitialModelData(T enhancedPig) {
        PigModelData pigModelData = new PigModelData();
        setBaseInitialModelData(pigModelData, enhancedPig);
    }

    @Override
    protected void additionalUpdateModelDataInfo(AnimalModelData animalModelData, T enhancedAnimal) {
        animalModelData.saddle = getSaddle(enhancedAnimal.getEnhancedInventory());
    }

    @Override
    protected Phenotype createPhenotype(T enhancedAnimal) {
        return new PigPhenotype(enhancedAnimal.getSharedGenes().getAutosomalGenes(), enhancedAnimal.getStringUUID().charAt(1));
    }


}
