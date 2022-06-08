package mokiyoki.enhancedanimals.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import mokiyoki.enhancedanimals.entity.EnhancedPig;
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
import java.util.Map;

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

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition base = meshdefinition.getRoot().addOrReplaceChild("base", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition bPig = base.addOrReplaceChild("bPig", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 14.0F, 0.0F, 0.0F, 0.0F, Mth.HALF_PI));
        PartDefinition bBody = bPig.addOrReplaceChild("bBody", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition bButt = bBody.addOrReplaceChild("bButt", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition bNeck = bBody.addOrReplaceChild("bNeck", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition bHead = bNeck.addOrReplaceChild("bHead", CubeListBuilder.create(), PartPose.offset(0.0F, -10.0F, 0.0F));
        PartDefinition bMouth = bHead.addOrReplaceChild("bMouth", CubeListBuilder.create(), PartPose.offset(0.0F, -10.0F, 0.0F));
        PartDefinition bEarLeft = bHead.addOrReplaceChild("bEarL", CubeListBuilder.create(), PartPose.offset(4.0F, 0.0F, -3.0F));
        PartDefinition bEarRight = bHead.addOrReplaceChild("bEarR", CubeListBuilder.create(), PartPose.offset(-4.0F, 0.0F, -3.0F));
        PartDefinition bLegFrontLeft = bPig.addOrReplaceChild("bLegFL", CubeListBuilder.create(), PartPose.offset(3.0F, 0.0F, -10.0F));
        PartDefinition bLegFrontRight = bPig.addOrReplaceChild("bLegFR", CubeListBuilder.create(), PartPose.offset(-3.0F, 0.0F, -10.0F));
        PartDefinition bLegBackLeft = bPig.addOrReplaceChild("bLegBL", CubeListBuilder.create(), PartPose.offset(3.0F, 0.0F, 9.0F));
        PartDefinition bLegBackRight = bPig.addOrReplaceChild("bLegBR", CubeListBuilder.create(), PartPose.offset(-3.0F, 0.0F, 9.0F));
        PartDefinition bTail = bBody.addOrReplaceChild("bTail", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 22.0F));

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
                PartPose.offset(0.0F, -5.5F, -3.0F)
        );
        bHead.addOrReplaceChild("snout", CubeListBuilder.create()
                        .texOffs(49, 22)
                        .addBox(-2.0F, -5.0F, 0.0F, 4, 6, 3),
                PartPose.ZERO
        );

        bHead.addOrReplaceChild("jaw", CubeListBuilder.create()
                        .texOffs(63, 22)
                        .addBox(-1.0F, -5.0F, 0.0F, 2, 6, 1),
                PartPose.offset(0.0F, 1.0F, 4.0F)
        );
        bHead.addOrReplaceChild("tuskTL", CubeListBuilder.create()
                        .texOffs(69, 22)
                        .addBox(-0.5F, -2.0F, -0.5F, 1, 2, 1, new CubeDeformation(-0.1F)),
                PartPose.offset(0.5F, 0.0F, 0.0F)
        );
        bHead.addOrReplaceChild("tuskTR", CubeListBuilder.create()
                        .texOffs(69, 22)
                        .addBox(-0.5F, -2.0F, -0.5F, 1, 2, 1, new CubeDeformation(-0.1F)),
                PartPose.offset(-0.5F, 0.0F, 0.0F)
        );
        bHead.addOrReplaceChild("tuskBL", CubeListBuilder.create()
                        .texOffs(69, 22)
                        .addBox(-0.5F, -2.0F, -0.5F, 1, 2, 1, new CubeDeformation(-0.1F))
                        .addBox(-0.5F, -2.8F, -0.5F, 1, 1, 2, new CubeDeformation(-0.1F)),
                PartPose.offset(0.5F, 0.0F, 0.0F)
        );
        bHead.addOrReplaceChild("tuskBR", CubeListBuilder.create()
                        .texOffs(69, 22)
                        .addBox(-0.5F, -2.0F, -0.5F, 1, 2, 1, new CubeDeformation(-0.1F))
                        .addBox(-0.5F, -2.8F, -0.5F, 1, 1, 2, new CubeDeformation(-0.1F)),
                PartPose.offset(-0.5F, 0.0F, 0.0F)
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
                        .texOffs(3, 9)
                        .addBox(2.5F, 0.0F, -9.0F, 2, 4, 2),
                PartPose.ZERO
        );
        bNeck.addOrReplaceChild("waddleR", CubeListBuilder.create()
                        .texOffs(25,9)
                        .addBox(-4.5F, 0.0F, -9.0F, 2, 4, 2),
                PartPose.ZERO
        );

        bBody.addOrReplaceChild("body11", CubeListBuilder.create()
                        .texOffs(0, 23)
                        .addBox(-5.0F, 0.0F, 0.0F, 10, 11, 10),
                PartPose.offset(0.0F, 18.1F, -4.0F)
        );
        bBody.addOrReplaceChild("body12", CubeListBuilder.create()
                        .texOffs(0, 23)
                        .addBox(-5.0F, 0.0F, 0.0F, 10, 12, 10),
                PartPose.offset(0.0F, 18.1F, -5.0F)
        );
        bBody.addOrReplaceChild("body13", CubeListBuilder.create()
                        .texOffs(0, 23)
                        .addBox(-5.0F, 0.0F, 0.0F, 10, 13, 10),
                PartPose.offset(0.0F, 18.1F, -6.0F)
        );
        bBody.addOrReplaceChild("body14", CubeListBuilder.create()
                        .texOffs(0, 23)
                        .addBox(-5.0F, 0.0F, 0.0F, 10, 14, 10),
                PartPose.offset(0.0F, 18.1F, -7.0F)
        );
        bBody.addOrReplaceChild("body15", CubeListBuilder.create()
                        .texOffs(0, 23)
                        .addBox(-5.0F, 0.0F, 0.0F, 10, 15, 10),
                PartPose.offset(0.0F, 18.1F, -8.0F)
        );

        bButt.addOrReplaceChild("butt5", CubeListBuilder.create()
                        .texOffs(0, 53)
                        .addBox(-4.5F, 0.0F, 0.0F, 9, 5, 9),
                PartPose.offset(0.0F, 18.0F, 5.5F)
        );
        bButt.addOrReplaceChild("butt6", CubeListBuilder.create()
                        .texOffs(0, 53)
                        .addBox(-4.5F, 0.0F, 0.0F, 9, 6, 9),
                PartPose.offset(0.0F, 18.0F, 6.5F)
        );
        bButt.addOrReplaceChild("butt7", CubeListBuilder.create()
                        .texOffs(0, 53)
                        .addBox(-4.5F, 0.0F, 0.0F, 9, 7, 9),
                PartPose.offset(0.0F, 18.0F, 7.5F)
        );
        bButt.addOrReplaceChild("butt8", CubeListBuilder.create()
                        .texOffs(0, 53)
                        .addBox(-4.5F, 0.0F, 0.0F, 9, 8, 9),
                PartPose.offset(0.0F, 18.0F, 8.5F)
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

        bTail.addOrReplaceChild("tail0", CubeListBuilder.create()
                        .texOffs(36, 0)
                        .addBox(-0.5F, 0.0F, 0.0F, 1, 2, 1, new CubeDeformation(-0.05F)),
                PartPose.ZERO
        );
        bTail.addOrReplaceChild("tail1", CubeListBuilder.create()
                        .texOffs(36, 3)
                        .addBox(-0.5F, 0.0F, 0.0F, 1, 2, 1, new CubeDeformation(-0.05F)),
                PartPose.ZERO
        );
        bTail.addOrReplaceChild("tail2", CubeListBuilder.create()
                        .texOffs(36, 6)
                        .addBox(-0.5F, 0.0F, 0.0F, 1, 2, 1, new CubeDeformation(-0.05F)),
                PartPose.ZERO
        );
        bTail.addOrReplaceChild("tail3", CubeListBuilder.create()
                        .texOffs(36, 9)
                        .addBox(-0.5F, 0.0F, 0.0F, 1, 2, 1, new CubeDeformation(-0.05F)),
                PartPose.ZERO
        );
        bTail.addOrReplaceChild("tail4", CubeListBuilder.create()
                        .texOffs(36, 12)
                        .addBox(-0.5F, 0.0F, 0.0F, 1, 2, 1, new CubeDeformation(-0.05F)),
                PartPose.ZERO
        );
        bTail.addOrReplaceChild("tail5", CubeListBuilder.create()
                        .texOffs(36, 15)
                        .addBox(-0.5F, 0.0F, 0.0F, 1, 2, 1, new CubeDeformation(-0.05F)),
                PartPose.ZERO
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
        
        this.eyes = new WrappedModelPart("eyes", bHead);

        this.head = new WrappedModelPart("head", bHead);
        this.cheeks = new WrappedModelPart("cheeks", bHead);
        this.snout = new WrappedModelPart("snout", bHead);
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
        this.thePig.addChild(this.legFrontLeft);
        this.thePig.addChild(this.legFrontRight);
        this.thePig.addChild(this.legBackLeft);
        this.thePig.addChild(this.legBackRight);
        
        this.theHead.addChild(this.head);
        this.theHead.addChild(this.eyes);
        this.theHead.addChild(this.cheeks);

        this.theMouth.addChild(this.snout);
        this.theMouth.addChild(this.jaw);

        this.snout.addChild(this.tusksTopLeft);
        this.snout.addChild(this.tusksTopRight);
        
        this.jaw.addChild(this.tusksBottomLeft);
        this.jaw.addChild(this.tusksBottomRight);
        
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
        
        this.tail0.addChild(this.tail1);
        this.tail1.addChild(this.tail2);
        this.tail2.addChild(this.tail3);
        this.tail3.addChild(this.tail4);
        this.tail4.addChild(this.tail5);
    }

    private void resetCubes() {

    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        PigModelData pigModelData = getPigModelData();
        PigPhenotype pig = pigModelData.getPhenotype();

        resetCubes();
        
        if (pig!=null) {
            super.renderToBuffer(poseStack, vertexConsumer, packedLightIn, packedOverlayIn, red, green, blue, alpha);

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
            this.body12.show(pig.shape == 1);
            this.body13.show(pig.shape == 2);
            this.body14.show(pig.shape == 3);
            this.body15.show(pig.shape == 4);
            
            this.butt5.show(pig.shape == 0 || pig.shape == 1);
            this.butt6.show(pig.shape == 2);
            this.butt7.show(pig.shape == 3);
            this.butt8.show(pig.shape == 4);

            poseStack.pushPose();
            poseStack.scale(1.0F, 1.0F, 1.0F);
            poseStack.translate(0.0F, 0.0F, 0.0F);

            gaRender(this.theNeck, null, poseStack, vertexConsumer, packedLightIn, packedOverlayIn, red, green, blue, alpha);

            poseStack.popPose();
        }
    }

    protected Map<String, Vector3f> saveAnimationValues(PigPhenotype pig) {
        Map<String, Vector3f> map = new HashMap<>();

        return map;
    }

    private void setupInitialAnimationValues(T entityIn, PigModelData modelData, PigPhenotype pig) {
        
    }
    
    @Override
    public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.currentAnimal = entityIn.getId();
        PigModelData pigModelData = getCreatePigModelData(entityIn);
        PigPhenotype pig = pigModelData.getPhenotype();
        float drive = ageInTicks + (1000 * pigModelData.random);

        if (pig != null) {

        }

    }

    private class PigModelData extends AnimalModelData {
        public PigPhenotype getPhenotype() {
            return (PigPhenotype) this.phenotype;
        }
    }

    private PigModelData getPigModelData() {
        return (PigModelData) getAnimalModelData();
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
    protected void additionalModelDataInfo(AnimalModelData animalModelData, T enhancedAnimal) {
        animalModelData.offsets = saveAnimationValues(((PigModelData) animalModelData).getPhenotype());
    }

    @Override
    protected Phenotype createPhenotype(T enhancedAnimal) {
        return new PigPhenotype(enhancedAnimal.getSharedGenes().getAutosomalGenes(), enhancedAnimal.getStringUUID().charAt(1));
    }

    protected class PigPhenotype implements Phenotype {
        float earFlopMod;
        int earSizeMod = 0;
        boolean tailCurl;
        boolean hasWaddles;
        float snoutLength;
        float tailCurlAmount;
        int shape;

        PigPhenotype(int[] gene,char uuid) {
            this.tailCurl = Character.isLetter(uuid);

            float earSize = 0.0F;
            float earFlop = 1.0F;
            //SSC1
            if (gene[68] == 2 || gene[69] == 2) {
                earSize = earSize + 0.12F;
                earFlop = earFlop - 0.44F;
            } else if (gene[68] == 3 || gene[69] == 3) {
                earSize = earSize + 0.03F;
            }
            //SSC5
            if (gene[70] == 2 || gene[71] == 2) {
                earSize = earSize + 0.35F;
                earFlop = earFlop - 0.48F;
            } else if (gene[70] == 3 && gene[71] == 3) {
                earSize = earSize + 0.09F;
            }
            //SSC6
            if (gene[72] == 2 || gene[73] == 2) {
                earSize = earSize - 0.031F;
            }
            //SSC7
            if (gene[74] == 2 || gene[75] == 2) {
                earSize = earSize + 0.29F;
                earFlop = earFlop - 0.62F;
            } else if (gene[70] == 3 && gene[71] == 3) {
                earSize = earSize + 0.09F;
            }
            //SSC9
            if (gene[76] == 2 || gene[77] == 2) {
                earSize = earSize + 0.11F;
                earFlop = earFlop - 0.32F;
            } else if (gene[70] == 3 && gene[71] == 3) {
                earSize = earSize + 0.03F;
            }
            //SSC12
            if (gene[78] == 2 || gene[79] == 2) {
                earSize = earSize + 0.11F;
                earFlop = earFlop - 0.14F;
            } else if (gene[78] == 3 && gene[79] == 3) {
                earSize = earSize + 0.03F;
            }

            for (int i = 80; i < 100; i+= 2) {
                if (gene[i] != 1 && gene[i+1] != 1) {
                    if (gene[i] == 2 || gene[i+1] == 2) {
                        earSize = earSize + 0.007F;
                    } else if (gene[i] == 3 || gene[i+1] == 3){
                        earSize = earSize + 0.007F;
                        earFlop = earFlop - 0.004F;
                    } else {
                        earSize = earSize + 0.007F;
                        earFlop = earFlop - 0.007F;
                    }
                }
            }
            for (int i = 100; i < 120; i+= 2) {
                if (gene[i] != 1 && gene[i+1] != 1) {
                    if (gene[i] == 2 || gene[i+1] == 2) {
                        earSize = earSize - 0.007F;
                    } else if (gene[i] == 3 || gene[i+1] == 3){
                        earSize = earSize - 0.007F;
                        earFlop = earFlop + 0.004F;
                    } else {
                        earSize = earSize - 0.007F;
                        earFlop = earFlop + 0.007F;
                    }
                }
            }

            if (earFlop > 1.0F) {
                this.earFlopMod = 1.0F;
            } else {
                this.earFlopMod = earFlop < -1.0F ? -1.0F : earFlop;
            }

            if (earSize > 0.2F) {
                if (earSize > 0.5F) {
                    this.earSizeMod = 2;
                } else {
                    this.earSizeMod = 1;
                }
            }

            //snoutLength
            float snoutlength1 = 1.0F;
            float snoutlength2 = 1.0F;
            float snoutlength = 0.0F;

            // 1 - 5, longest - shortest
            for (int i = 1; i < gene[18];i++){
                snoutlength1 = snoutlength1 - 0.1F;
            }

            for (int i = 1; i < gene[19];i++){
                snoutlength2 = snoutlength2 - 0.1F;
            }

            for (int i = 1; i < gene[66];i++){
                snoutlength1 = snoutlength1 - 0.1F;
            }

            for (int i = 1; i < gene[67];i++){
                snoutlength2 = snoutlength2 - 0.1F;
            }

            //causes partial dominance of longer nose over shorter nose.
            if (snoutlength1 > snoutlength2){
                snoutlength = (snoutlength1*0.75F) + (snoutlength2*0.25F);
            }else if (snoutlength1 < snoutlength2){
                snoutlength = (snoutlength1*0.25F) + (snoutlength2*0.75F);
            }else{
                snoutlength = snoutlength1;
            }

            // 1 - 4, longest - shortest
            if (gene[42] >= gene[43]) {
                snoutlength = snoutlength + ((4 - gene[42])/8.0F);
            } else {
                snoutlength = snoutlength + ((4 - gene[43])/8.0F);
            }

            if (gene[44] >= 2 && gene[45] >= 2) {
                if (gene[44] == 2 || gene[45] == 2) {
                    snoutlength = snoutlength * 0.9F;
                } else {
                    snoutlength = snoutlength * 0.75F;
                }
            }

            if (gene[46] == 2 && gene[47] == 2) {
                snoutlength = snoutlength * 0.6F;
            }

            if (snoutlength > 1.0F) {
                this.snoutLength = 1.0F;
            } else if (snoutlength < 0.0F) {
                this.snoutLength = 0.0F;
            } else {
                this.snoutLength = snoutlength;
            }

            float inbreedingFactor = 0.0F;

            if (gene[20] == gene[21]) {
                inbreedingFactor = 0.1667F;
            }
            if (gene[22] == gene[23]) {
                inbreedingFactor = inbreedingFactor + 0.1667F;
            }
            if (gene[24] == gene[25]) {
                inbreedingFactor = inbreedingFactor + 0.1667F;
            }
            if (gene[26] == gene[27]) {
                inbreedingFactor = inbreedingFactor + 0.1667F;
            }
            if (gene[28] == gene[29]) {
                inbreedingFactor = inbreedingFactor + 0.1667F;
            }
            if (gene[30] == gene[31]) {
                inbreedingFactor = inbreedingFactor + 0.1667F;
            }

            this.tailCurlAmount = inbreedingFactor;

            int shape = 1;

            if (gene != null) {
                if (gene[56] != 1 && gene[57] != 1) {
                    if (gene[56] + gene[57] <= 8) {
                        shape = 0;
                    } else if (gene[56] == 5 || gene[57] == 5) {
                        shape = 2;
                    } else if (gene[56] == 6 || gene[57] == 6) {
                        shape = 3;
                    } else if (gene[56] == 7 && gene[57] == 7) {
                        shape = 4;
                    }
                }
            }
            this.shape = shape;

            this.hasWaddles = gene[32] == 1 || gene[33] == 1;
        }
    }
}
//
//import com.mojang.blaze3d.vertex.PoseStack;
//import com.mojang.blaze3d.vertex.VertexConsumer;
//import mokiyoki.enhancedanimals.config.EanimodCommonConfig;
//import mokiyoki.enhancedanimals.entity.EnhancedPig;
//import mokiyoki.enhancedanimals.items.CustomizableCollar;
//import mokiyoki.enhancedanimals.items.CustomizableSaddleEnglish;
//import mokiyoki.enhancedanimals.items.CustomizableSaddleWestern;
//import mokiyoki.enhancedanimals.model.util.ModelHelper;
//import mokiyoki.enhancedanimals.renderer.EnhancedRendererModelNew;
//import net.minecraft.client.model.EntityModel;
//import net.minecraft.client.model.geom.ModelPart;
//import net.minecraft.client.multiplayer.ClientLevel;
//import net.minecraft.world.SimpleContainer;
//import net.minecraft.world.item.Item;
//import net.minecraft.world.item.ItemStack;
//import net.minecraft.util.Mth;
//import net.minecraftforge.api.distmarker.Dist;
//import net.minecraftforge.api.distmarker.OnlyIn;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//@OnlyIn(Dist.CLIENT)
//public class ModelEnhancedPig <T extends EnhancedPig> extends EntityModel<T> {
//
//    private Map<Integer, PigModelData> pigModelDataCache = new HashMap<>();
//    private int clearCacheTimer = 0;
//    private float headRotationAngleX;
//
//        /**
//         * Equipment stuff
//         */
//
//        this.chest1 = new ModelPart(this, 80, 14);
//        this.chest1.addBox(-3.0F, 0.0F, 0.0F, 8, 8, 3);
//        this.chest1.setPos(-8.0F, 8.0F, 3.0F);
//        this.chest1.yRot = ((float)Math.PI / 2F);
//        this.chest2 = new ModelPart(this, 80, 25);
//        this.chest2.addBox(-3.0F, 0.0F, 0.0F, 8, 8, 3);
//        this.chest2.setPos(5.0F, 8.0F, 3.0F);
//        this.chest2.yRot = ((float)Math.PI / 2F);
//
//        this.saddle = new EnhancedRendererModelNew(this, 0, 0, "Saddle");
//
//        this.saddleWestern = new EnhancedRendererModelNew(this, 210, 0, "WesternSaddle");
//        this.saddleWestern.addBox(-5.0F, -2.0F, -5.0F, 10, 2, 13, 0.0F);
//        this.saddleWestern.texOffs(210, 15);
//        this.saddleWestern.addBox(-4.0F, -3.0F, 5.0F, 8, 2, 4, 0.0F);
//        this.saddleWestern.texOffs(230, 15);
//        this.saddleWestern.addBox(-3.5F, -4.0F, 8.0F, 7, 2, 2, 0.0F);
//
//        this.saddleEnglish = new EnhancedRendererModelNew(this, 211, 1, "EnglishSaddle");
//        this.saddleEnglish.addBox(-5.0F, -1.0F, -4.0F, 10, 2, 12, 0.0F);
//        this.saddleEnglish.texOffs(210, 15);
//        this.saddleEnglish.addBox(-4.0F, -1.5F, 5.0F, 8, 2, 4, 0.0F);
//        this.saddleEnglish.texOffs(230, 15);
//        this.saddleEnglish.addBox(-3.5F, -2.0F, 7.5F, 7, 2, 2, 0.0F);
//
//        this.saddleHorn = new EnhancedRendererModelNew(this, 234, 19, "SaddleHorn");
//        this.saddleHorn.addBox(-4.0F, -2.0F, -3.0F, 8, 2, 3, 0.0F);
//
//        this.saddlePomel = new EnhancedRendererModelNew(this, 243, 0, "SaddlePomel");
//        this.saddlePomel.addBox(-1.0F, -3.0F, -2.0F, 2, 4, 2, -0.25F);
//        this.saddlePomel.setPos(0.0F, -2.0F, -2.0F);
//
//        this.saddleSideL = new EnhancedRendererModelNew(this, 234, 49, "SaddleLeft");
//        this.saddleSideL.addBox(0.0F, 0.0F, 0.0F, 3, 4, 8);
//
//        this.saddleSideR = new EnhancedRendererModelNew(this, 234, 61, "SaddleRight");
//        this.saddleSideR.addBox(-3.0F, 0.0F, 0.0F, 3, 4, 8);
//
//        this.stirrup2DWideL = new EnhancedRendererModelNew(this, 248, 24, "2DStirrupL");
//        this.stirrup2DWideL.addBox(0.0F, 0.0F, 0.0F, 0, 10, 4); // strap
//
//        this.stirrup2DWideR = new EnhancedRendererModelNew(this, 248, 24, "2DStirrupR");
//        this.stirrup2DWideR.addBox(0.0F, 0.0F, 0.0F, 0, 10, 4); // strap
//
//        this.stirrup3DNarrowL = new EnhancedRendererModelNew(this, 249, 27, "3DStirrupL");
//        this.stirrup3DNarrowL.addBox(-1.0F, 0.0F, 0.0F, 1, 10, 1); // strap
//
//        this.stirrup3DNarrowR = new EnhancedRendererModelNew(this, 251, 27, "3DStirrupR");
//        this.stirrup3DNarrowR.addBox(0.0F, 0.0F, 0.0F, 1, 10, 1);
//
//        this.stirrup = new EnhancedRendererModelNew(this, 210, 0, "Stirrup");
//        this.stirrup.addBox(-0.5F, 9.5F, -1.0F, 1, 1, 1);
//        this.stirrup.texOffs(214, 0);
//        this.stirrup.addBox(-0.5F, 9.5F, 1.0F, 1, 1, 1);
//        this.stirrup.texOffs(210, 2);
//        this.stirrup.addBox(-0.5F, 10.5F, -1.5F, 1, 3, 1);
//        this.stirrup.texOffs(214, 2);
//        this.stirrup.addBox(-0.5F, 10.5F, 1.5F, 1, 3, 1);
//        this.stirrup.texOffs(211, 7);
//        this.stirrup.addBox(-0.5F, 12.5F, -0.5F, 1, 1, 2);
//
//        this.saddlePad = new EnhancedRendererModelNew(this, 194, 24, "SaddlePad");
//        this.saddlePad.addBox(-8.0F, -1.0F, -6.0F, 16, 10, 15, -1.0F);
//
//        this.saddleHorn.addChild(this.saddlePomel);
//
//        this.collar = new EnhancedRendererModelNew(this, 80, 1);
//        this.collar.addBox(-5.5F, -5.5F, -3.0F, 11, 2, 11, 0.001F);
//        this.collar.texOffs(80, 4);
//        this.collar.addBox(0.0F, -5.0F, -5.0F, 0, 3, 3, 0.0F);
//        this.collar.texOffs(116, 6);
//        this.collar.addBox(-1.5F, -5.0F, -6.25F, 3, 3, 3, 0.0F);
//        this.collar.setPos(0.0F, -1.0F, -7.5F);
//        this.neck.addChild(this.collar);
//        this.neckLong.addChild(this.collar);
//        this.neckBigger.addChild(this.collar);
//        this.neckBiggerLong.addChild(this.collar);
//
//        //western
//        this.body11.addChild(this.saddleWestern);
//        this.body12.addChild(this.saddleWestern);
//        this.body13.addChild(this.saddleWestern);
//        this.body14.addChild(this.saddleWestern);
//        this.body15.addChild(this.saddleWestern);
//        this.saddleWestern.addChild(this.saddleHorn);
//        this.saddleWestern.addChild(this.saddleSideL);
//        this.saddleWestern.addChild(this.saddleSideR);
//        this.saddleWestern.addChild(this.saddlePad);
//        this.saddleWestern.addChild(this.stirrup2DWideL);
//        this.saddleWestern.addChild(this.stirrup2DWideR);
//        this.stirrup2DWideL.addChild(this.stirrup);
//        this.stirrup2DWideR.addChild(this.stirrup);
//        //english
//        this.body11.addChild(this.saddleEnglish);
//        this.body12.addChild(this.saddleEnglish);
//        this.body13.addChild(this.saddleEnglish);
//        this.body14.addChild(this.saddleEnglish);
//        this.body15.addChild(this.saddleEnglish);
//        this.saddleEnglish.addChild(this.saddleHorn);
//        this.saddleEnglish.addChild(this.saddleSideL);
//        this.saddleEnglish.addChild(this.saddleSideR);
//        this.saddleEnglish.addChild(this.saddlePad);
//        this.saddleEnglish.addChild(this.stirrup3DNarrowL);
//        this.saddleEnglish.addChild(this.stirrup3DNarrowR);
//        this.stirrup3DNarrowL.addChild(this.stirrup);
//        this.stirrup3DNarrowR.addChild(this.stirrup);
//        //vanilla
//        this.body11.addChild(this.saddle);
//        this.body12.addChild(this.saddle);
//        this.body13.addChild(this.saddle);
//        this.body14.addChild(this.saddle);
//        this.body15.addChild(this.saddle);
//        this.saddle.addChild(this.saddlePad);
//        this.saddle.addChild(this.stirrup3DNarrowL);
//        this.saddle.addChild(this.stirrup3DNarrowR);
//    }
//
//    @Override
//    public void renderToBuffer(PoseStack matrixStackIn, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
//
//        PigModelData pigModelData = getPigModelData();
//        Phenotype pig = pigModelData.phenotype;
//        if (pig != null) {
//            float size = pigModelData.size;
//            size = size < 0 ? 0 : size;
//
//            float age = 1.0F;
//            if (!(pigModelData.birthTime == null) && !pigModelData.birthTime.equals("") && !pigModelData.birthTime.equals("0")) {
//                int ageTime = (int) (pigModelData.clientGameTime - Long.parseLong(pigModelData.birthTime));
//                if (ageTime <= (pigModelData.adultAge * 1.25F)) {
//                    age = ageTime < 0 ? 0 : ageTime / (pigModelData.adultAge * 1.25F);
//                }
//            }
//
//            float finalPigSize = ((3.0F * size * age) + size) / 4.0F;
//            matrixStackIn.pushPose();
//            matrixStackIn.scale(finalPigSize, finalPigSize, finalPigSize);
//            matrixStackIn.translate(0.0F, -1.5F + 1.5F / finalPigSize, 0.0F);
//
//            this.wattles.visible = pig.hasWaddles;
//
//            if (pigModelData.blink >= 6) {
//                this.eyeLeft.visible = true;
//                this.eyeRight.visible = true;
//            } else {
//                this.eyeLeft.visible = false;
//                this.eyeRight.visible = false;
//            }
//
//            this.earSmallL.visible = false;
//            this.earSmallR.visible = false;
//            this.earMediumL.visible = false;
//            this.earMediumR.visible = false;
//            this.earLargeL.visible = false;
//            this.earLargeR.visible = false;
//            switch (pig.earSizeMod) {
//                case 0:
//                    this.earSmallL.visible = true;
//                    this.earSmallR.visible = true;
//                    break;
//                case 1:
//                    this.earMediumL.visible = true;
//                    this.earMediumR.visible = true;
//                    break;
//                case 2:
//                    this.earLargeL.visible = true;
//                    this.earLargeR.visible = true;
//                    break;
//            }
//
//            if (pigModelData.collar) {
//                this.collar.visible = true;
//            } else {
//                this.collar.visible = false;
//            }
//
//            this.chest1.visible = false;
//            this.chest2.visible = false;
//            if (pigModelData.hasChest) {
//                this.chest1.visible = true;
//                this.chest2.visible = true;
//                this.chest1.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
//                this.chest2.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
//            }
//
//            this.body11.visible = false;
//            this.body12.visible = false;
//            this.body13.visible = false;
//            this.body14.visible = false;
//            this.body15.visible = false;
//            this.butt5.visible = false;
//            this.butt6.visible = false;
//            this.butt7.visible = false;
//            this.butt8.visible = false;
//            if (false) {
//                this.body11.visible = true;
//            } else if (false) {
//                this.body12.visible = true;
//            } else if (false) {
//                this.body13.visible = true;
//            } else if (false) {
//                this.body14.visible = true;
//            } else {
//                this.body15.visible = true;
//            }
//
//            this.neck.visible = false;
//            this.neckLong.visible = false;
//            this.neckBigger.visible = false;
//            this.neckBiggerLong.visible = false;
//            this.neck.visible = true;
//
//            this.butt5.visible = true;
//
//            if (pigModelData.saddle != null) {
//                if (!(pigModelData.saddle.isEmpty() || pigModelData.saddle.getItem() instanceof CustomizableCollar)) {
//                    renderPigandSaddle(pigModelData.saddle, pigModelData.unrenderedModels, matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
//                } else {
//                    this.thePig.render(matrixStackIn, bufferIn, null, new ArrayList<>(), false, packedLightIn, packedOverlayIn, red, green, blue, alpha);
//                }
//            }
//
//            matrixStackIn.popPose();
//        }
//    }
//
//    private void renderPigandSaddle( ItemStack saddleStack,List<String> unrenderedModels, PoseStack matrixStackIn, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
//
//            Map<String, List<Float>> mapOfScale = new HashMap<>();
//            float saddleScale = 0.75F;
//
//            this.saddleWestern.visible = false;
//            this.saddleEnglish.visible = false;
//            this.saddle.visible = false;
//            this.saddlePomel.visible = false;
//
//            if (saddleStack!=null) {
//                if (!saddleStack.isEmpty()) {
//                    List<Float> scalingsForSaddle = ModelHelper.createScalings(saddleScale, saddleScale, saddleScale, 0.0F, -saddleScale * 0.01F, (saddleScale - 1.0F) * 0.04F);
//                    Item saddle = saddleStack.getItem();
//                    if (saddle instanceof CustomizableSaddleWestern) {
//                        this.saddleWestern.visible = true;
//                        this.saddlePomel.visible = true;
//                        mapOfScale.put("WesternSaddle", scalingsForSaddle);
//                    } else if (saddle instanceof CustomizableSaddleEnglish) {
//                        this.saddleEnglish.visible = true;
//                        saddleScale = 1.125F;
//                        List<Float> scalingsForSaddlePad = ModelHelper.createScalings(saddleScale, saddleScale, saddleScale, 0.0F, -saddleScale * 0.01F, (saddleScale - 1.0F) * 0.04F);
//                        mapOfScale.put("SaddlePad", scalingsForSaddlePad);
//                        mapOfScale.put("EnglishSaddle", scalingsForSaddle);
//                    } else if (!(saddle instanceof CustomizableCollar)) {
//                        this.saddle.visible = true;
//                        mapOfScale.put("Saddle", scalingsForSaddle);
//                    }
//                }
//            }
//        this.thePig.render(matrixStackIn, bufferIn , mapOfScale, unrenderedModels, false, packedLightIn, packedOverlayIn, red, green, blue, alpha);
//    }
//
//    @Override
//    public void prepareMobModel(T entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTickTime) {
//        super.prepareMobModel(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTickTime);
//
//        PigModelData pigModelData = getCreatePigModelData(entitylivingbaseIn);
//        Phenotype pig = pigModelData.phenotype;
//
//        if (pig != null) {
//            this.currentPig = entitylivingbaseIn.getId();
//
//            float onGround;
//
//            boolean sleeping = pigModelData.sleeping;
//
//            if (sleeping) {
//                onGround = sleepingAnimation();
//            } else {
//                onGround = standingAnimation();
//            }
//
//            this.neck.z = onGround - (entitylivingbaseIn).getHeadRotationPointY(partialTickTime) * 4.5F;
//            this.neckLong.z = (onGround - (entitylivingbaseIn).getHeadRotationPointY(partialTickTime) * 4.5F) + 1;
//            this.headRotationAngleX = (entitylivingbaseIn).getHeadRotationAngleX(partialTickTime);
//
//            float snoutLength = pig.snoutLength;
//            if (!(pigModelData.birthTime == null) && !pigModelData.birthTime.equals("") && !pigModelData.birthTime.equals("0")) {
//                int ageTime = (int) (pigModelData.clientGameTime - Long.parseLong(pigModelData.birthTime));
//                if (ageTime < pigModelData.adultAge) {
//                    float age = ageTime < 0 ? 0 : ageTime / (float)pigModelData.adultAge;
//                    snoutLength = snoutLength * age;
//                }
//            }
//
//        /*
//            shortest nose Y rotation point:
//                snout = -2.0F
//                topTusks = -4.5F
//                bottomTusks = -5.0F
//
//            longest nose Y rotation point:
//                snout = -7.0F
//                topTusks = -1.0F
//                bottomTusks = -2.0F
//         */
//
//            //TODO check that baby thePig snoots are short and cute
////        this.snout.rotateAngleX = -snoutLength;
//            this.snout.y = -(2.0F + (4.75F * snoutLength));
//            this.tuskTL.y = -(4.5F - (3.5F * snoutLength));
//            this.tuskTR.y = this.tuskTL.y;
//            this.tuskBL.y = -(5.0F - (3.0F * snoutLength));
//            this.tuskBR.y = this.tuskBL.y;
//
//            this.tail0.xRot = 1.5F * pig.tailCurlAmount;
//            this.tail1.xRot = 1.1111F * pig.tailCurlAmount;
//            this.tail2.xRot = 1.2222F * pig.tailCurlAmount;
//            this.tail3.xRot = 1.3333F * pig.tailCurlAmount;
//            this.tail4.xRot = 1.5F * pig.tailCurlAmount;
//            this.tail5.xRot = 0.1F * pig.tailCurlAmount;
//
//            if (pig.tailCurl) {
//                this.tail0.yRot = 0.3555F * pig.tailCurlAmount;
//                this.tail1.yRot = 0.3555F * pig.tailCurlAmount;
//                this.tail2.yRot = 0.3555F * pig.tailCurlAmount;
//                this.tail3.yRot = 0.3555F * pig.tailCurlAmount;
//            } else {
//                this.tail0.yRot = -0.3555F * pig.tailCurlAmount;
//                this.tail1.yRot = -0.3555F * pig.tailCurlAmount;
//                this.tail2.yRot = -0.3555F * pig.tailCurlAmount;
//                this.tail3.yRot = -0.3555F * pig.tailCurlAmount;
//            }
//
//            /**
//             * longer body11 adjustments prototype
//             */
//
//            /**
//             this.body14.rotationPointZ = -6.0F; //-4.0F
//             this.tail0.rotationPointY = 9.0F; //  5.0F
//             this.leg1.rotationPointZ = -9.0F; // -7.0F
//             this.leg2.rotationPointZ = -9.0F; // -7.0F
//             this.leg3.rotationPointZ = 10.5F;  // 7.0F
//             this.leg4.rotationPointZ = 10.5F; //  7.0F
//             */
//            if (this.body11.visible) {
//                this.leg1.z = -7.0F;
//                this.leg2.z = -7.0F;
//            } else if (this.body12.visible) {
//                this.leg1.z = -8.0F;
//                this.leg2.z = -8.0F;
//            } else if (this.body13.visible) {
//                this.leg1.z = -9.0F;
//                this.leg2.z = -9.0F;
//            } else if (this.body14.visible) {
//                this.leg1.z = -10.0F;
//                this.leg2.z = -10.0F;
//            } else if (this.body15.visible) {
//                this.leg1.z = -11.0F;
//                this.leg2.z = -11.0F;
//            }
//
//            float movebutt = 1F;
//
//            //butt selection probs should have mostly to do with shape?
//            if (this.butt5.visible) {
//                this.tail0.y = 5.0F;
//                this.butt5.z = 6.0F + movebutt;
//                this.leg3.z = 7.0F + movebutt;
//                this.leg4.z = 7.0F + movebutt;
//            } else if (this.butt6.visible) {
//                this.tail0.y = 6.0F;
//                this.butt6.z = 6.0F + movebutt;
//                this.leg3.z = 8.0F + movebutt;
//                this.leg4.z = 8.0F + movebutt;
//            } else if (this.butt7.visible) {
//                this.tail0.y = 7.0F;
//                this.butt7.z = 6.0F + movebutt;
//                this.leg3.z = 9.0F + movebutt;
//                this.leg4.z = 9.0F + movebutt;
//            } else if (this.butt8.visible) {
//                this.tail0.y = 8.0F;
//
//                /**
//                 * if body15 butt8 should be from -3 to 1
//                 *
//                 */
//
//                this.butt8.z = 6.0F + movebutt;
//                this.leg3.z = 10.0F + movebutt;
//                this.leg4.z = 10.0F + movebutt;
//            }
//        }
//    }
//
//    @Override
//    public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
//        PigModelData pigModelData = getPigModelData();
//        Phenotype pig = pigModelData.phenotype;
//
//        if (pig != null) {
//            ItemStack saddleStack = pigModelData.saddle;
////        List<String> unrenderedModels = new ArrayList<>();
//
//            float twoPi = (float) Math.PI * 2.0F;
//            float halfPi = (float) Math.PI / 2F;
//
////        this.neck.rotateAngleX = twoPi;
////        this.neckLong.rotateAngleX = twoPi;
//            this.body11.xRot = halfPi;
//            this.body12.xRot = halfPi;
//            this.body13.xRot = halfPi;
//            this.body14.xRot = halfPi;
//            this.body15.xRot = halfPi;
//            this.butt5.xRot = halfPi;
//            this.butt6.xRot = halfPi;
//            this.butt7.xRot = halfPi;
//            this.butt8.xRot = halfPi;
//
//            /**
//             * change only earFlopMod for ear effects. If anything is tweaked it needs to be through that.
//             */
//            float earFlopMod = pig.earFlopMod; //[-1.0 = full droop, -0.65 = half droop, -0.25 = over eyes flop, 0 = stiff flop, 0.5F is half flop, 1 is no flop]
//
//            float earFlopContinuationMod;
//
//            if (earFlopMod < -0.25) {
//                earFlopContinuationMod = (1 + earFlopMod) / 0.75F;
//                earFlopMod = -0.25F;
//            } else {
//                earFlopContinuationMod = 1.0F;
//            }
//
//            this.earSmallL.xRot = ((-(float) Math.PI / 2.0F) * earFlopMod * earFlopContinuationMod) + ((((float) Math.PI / 2.2F)) * (1.0F - earFlopContinuationMod));
//            this.earSmallR.xRot = ((-(float) Math.PI / 2.0F) * earFlopMod * earFlopContinuationMod) + ((((float) Math.PI / 2.2F)) * (1.0F - earFlopContinuationMod));
//
//            this.earSmallL.yRot = ((float) Math.PI / 3F) * earFlopContinuationMod;
//            this.earSmallR.yRot = -((float) Math.PI / 3F) * earFlopContinuationMod;
//
//            this.earSmallL.zRot = (-((float) Math.PI / 10F) * earFlopContinuationMod) + (((float) Math.PI / 2.2F) * (1.0F - earFlopContinuationMod));
//            this.earSmallR.zRot = (((float) Math.PI / 10F) * earFlopContinuationMod) - (((float) Math.PI / 2.2F) * (1.0F - earFlopContinuationMod));
//
//            if (earFlopMod == -0.25F) {
//                this.earSmallL.z = 3.0F * earFlopContinuationMod;
//                this.earSmallR.z = 3.0F * earFlopContinuationMod;
//            } else {
//                this.earSmallL.z = 3.0F;
//                this.earSmallR.z = 3.0F;
//            }
//
////            this.earSmallL.rotateAngleX = -((float) Math.PI / 2.0F) * earFlopMod * earFlopContinuationMod;
////            this.earSmallR.rotateAngleX = -((float) Math.PI / 2.0F) * earFlopMod;
////
////            this.earSmallL.rotateAngleY = ((float) Math.PI / 3F) * 1.0F * earFlopContinuationMod;
////            this.earSmallR.rotateAngleY = -((float) Math.PI / 3F);
////
////            this.earSmallL.rotateAngleZ = -((float) Math.PI / 10F) * 1.0F * earFlopContinuationMod;
////            this.earSmallR.rotateAngleZ = ((float) Math.PI / 10F);
////
////
////            this.earSmallR.rotateAngleX = ((float) Math.PI / 2.2F);
////            this.earSmallR.rotateAngleY = 0.0F;
////            this.earSmallR.rotateAngleZ = ((float) Math.PI / -2F);
//
//            ModelHelper.copyModelPositioning(earSmallL, earMediumL);
//            ModelHelper.copyModelPositioning(earSmallR, earMediumR);
//            ModelHelper.copyModelPositioning(earSmallL, earLargeL);
//            ModelHelper.copyModelPositioning(earSmallR, earLargeR);
//
///**
// * these are the cause of the trouble with pigs
// * netHeadYaw must be getting some unusual numbers when in gui form
// * try to get a more reasonable set of numbers somehow
// */
//            this.neck.xRot = twoPi + ((headPitch * 0.017453292F) / 5.0F) + (this.headRotationAngleX / 2.0F);
//            this.head.xRot = ((headPitch * 0.017453292F) / 5.0F) + (this.headRotationAngleX / 2.0F);
//
//            float headYaw = netHeadYaw - Math.round(netHeadYaw / 180) * 180;
//            this.head.zRot = headYaw * 0.017453292F * -0.5F;
//
////        System.out.print(headYaw);
////        System.out.println();
//
//            if (!pigModelData.sleeping) {
//                this.neck.zRot = headYaw * 0.017453292F * -0.5F;
//                this.leg1.xRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
//                this.leg2.xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
//                this.leg3.xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
//                this.leg4.xRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
//            }
//
//            this.mouth.xRot = -0.12F + this.headRotationAngleX;
//
//            this.tuskTL.zRot = 1.3F;
//            this.tuskTR.zRot = -1.3F;
//
//            this.tuskBL.zRot = 1.5F;
//            this.tuskBR.zRot = -1.5F;
//
//            ModelHelper.copyModelRotations(this.neck, this.neckLong);
//            ModelHelper.copyModelPositioning(this.neck, neckBigger);
//
//            this.tail0.xRot = -((float) Math.PI / 2F);
//
//            if (saddleStack != null) {
//                if (!saddleStack.isEmpty()) {
//                    Item saddle = saddleStack.getItem();
//                    if (saddle instanceof CustomizableSaddleWestern) {
//                        this.saddleWestern.xRot = -((float) Math.PI / 2F);
//                        this.saddleWestern.setPos(0.0F, 4.0F, 10.0F);
//                        this.saddleSideL.setPos(5.0F, -1.0F, -5.25F);
//                        this.saddleSideR.setPos(-5.0F, -1.0F, -5.25F);
//                        this.saddleHorn.setPos(0.0F, -2.0F, -2.0F);
//                        this.saddleHorn.xRot = (float) Math.PI / 8.0F;
//                        this.saddlePomel.setPos(0.0F, -1.5F, -0.5F);
//                        this.saddlePomel.xRot = -0.2F;
//                        this.stirrup2DWideL.setPos(7.5F, 0.0F, -3.5F);
//                        this.stirrup2DWideR.setPos(-7.5F, 0.0F, -3.5F);
//                    } else if (saddle instanceof CustomizableSaddleEnglish) {
//                        this.saddleEnglish.xRot = -((float) Math.PI / 2F);
//                        this.saddleEnglish.setPos(0.0F, 4.0F, 10.0F);
//                        this.saddleSideL.setPos(3.25F, -0.5F, -4.0F);
//                        this.saddleSideR.setPos(-3.25F, -0.5F, -4.0F);
//                        this.saddleHorn.setPos(0.0F, -1.0F, -1.0F);
//                        this.saddleHorn.xRot = (float) Math.PI / 4.5F;
//                        this.stirrup3DNarrowL.setPos(8.0F, -0.5F, -1.5F);
//                        this.stirrup3DNarrowR.setPos(-8.0F, -0.5F, -1.5F);
//                    } else if (!(saddle instanceof CustomizableCollar)) {
//                        this.saddle.xRot = -((float) Math.PI / 2F);
//                        this.saddle.setPos(0.0F, 4.0F, 10.0F);
//                        this.stirrup3DNarrowL.setPos(8.0F, 0.0F, 0.0F);
//                        this.stirrup3DNarrowR.setPos(-8.0F, 0.0F, 0.0F);
//                    }
//                }
//            }
//        }
//    }
//
//    private float sleepingAnimation() {
//        float onGround;
//
//        onGround = 9.80F;
//
//        this.thePig.zRot = (float)Math.PI / 2.0F;
//        this.thePig.setPos(15.0F, 19.0F, 0.0F);
//        this.leg1.zRot = -0.8F;
//        this.leg1.xRot = 0.3F;
//        this.leg3.zRot = -0.8F;
//        this.leg3.xRot = -0.3F;
//        this.neck.zRot = 0.2F;
//
//        return onGround;
//    }
//
//    private float standingAnimation() {
//        float onGround;
//        onGround = 8.75F;
//
//        this.thePig.zRot = 0.0F;
//        this.thePig.setPos(0.0F, 0.0F, 0.0F);
//        this.leg1.zRot = 0.0F;
//        this.leg3.zRot = 0.0F;
//
//        return onGround;
//    }
//
//    private class PigModelData {
//        Phenotype phenotype;
//        String birthTime;
//        float size = 1.0F;
//        boolean sleeping = false;
//        int blink = 0;
//        int lastAccessed = 0;
//        long clientGameTime = 0;
//        List<String> unrenderedModels = new ArrayList<>();
//        ItemStack saddle;
//        ItemStack bridle;
//        ItemStack harness;
//        boolean collar = false;
//        boolean hasChest = false;
//        int adultAge;
//    }
//
//    private PigModelData getPigModelData() {
//        if (this.currentPig == null || !pigModelDataCache.containsKey(this.currentPig)) {
//            return new PigModelData();
//        }
//        return pigModelDataCache.get(this.currentPig);
//    }
//
//    private PigModelData getCreatePigModelData(T enhancedPig) {
//        clearCacheTimer++;
//        if(clearCacheTimer > 50000) {
//            pigModelDataCache.values().removeIf(value -> value.lastAccessed==1);
//            for (PigModelData pigModelData : pigModelDataCache.values()){
//                pigModelData.lastAccessed = 1;
//            }
//            clearCacheTimer = 0;
//        }
//
//        if (pigModelDataCache.containsKey(enhancedPig.getId())) {
//            PigModelData pigModelData = pigModelDataCache.get(enhancedPig.getId());
//            pigModelData.lastAccessed = 0;
////            pigModelData.dataReset++;
////            if (pigModelData.dataReset > 5000) {
//
////                pigModelData.dataReset = 0;
////            }
//            pigModelData.sleeping = enhancedPig.isAnimalSleeping();
//            pigModelData.blink = enhancedPig.getBlink();
//            pigModelData.birthTime = enhancedPig.getBirthTime();
//            pigModelData.clientGameTime = (((ClientLevel)enhancedPig.level).getLevelData()).getGameTime();
//            pigModelData.saddle = enhancedPig.getEnhancedInventory().getItem(1);
//            pigModelData.bridle = enhancedPig.getEnhancedInventory().getItem(3);
//            pigModelData.harness = enhancedPig.getEnhancedInventory().getItem(5);
//            pigModelData.collar = hasCollar(enhancedPig.getEnhancedInventory());
//            pigModelData.hasChest = !enhancedPig.getEnhancedInventory().getItem(0).isEmpty();
//
//
//            return pigModelData;
//        } else {
//            PigModelData pigModelData = new PigModelData();
//            if (enhancedPig.getSharedGenes()!=null) {
//                pigModelData.phenotype = new Phenotype(enhancedPig.getSharedGenes().getAutosomalGenes(), enhancedPig.getStringUUID().charAt(1));
//            }
//            pigModelData.size = enhancedPig.getAnimalSize();
//            pigModelData.sleeping = enhancedPig.isAnimalSleeping();
//            pigModelData.blink = enhancedPig.getBlink();
//            pigModelData.birthTime = enhancedPig.getBirthTime();
//            pigModelData.clientGameTime = (((ClientLevel)enhancedPig.level).getLevelData()).getGameTime();
//            pigModelData.saddle = enhancedPig.getEnhancedInventory().getItem(1);
//            pigModelData.bridle = enhancedPig.getEnhancedInventory().getItem(3);
//            pigModelData.harness = enhancedPig.getEnhancedInventory().getItem(5);
//            pigModelData.collar = hasCollar(enhancedPig.getEnhancedInventory());
//            pigModelData.hasChest = !enhancedPig.getEnhancedInventory().getItem(0).isEmpty();
//            pigModelData.adultAge = EanimodCommonConfig.COMMON.adultAgePig.get();
//
//            if(pigModelData.phenotype != null) {
//                pigModelDataCache.put(enhancedPig.getId(), pigModelData);
//            }
//
//            return pigModelData;
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
//    private class Phenotype{
//        float earFlopMod;
//        int earSizeMod = 0;
//        boolean tailCurl;
//        boolean hasWaddles;
//        float snoutLength;
//        float tailCurlAmount;
//        int shape;
//
//        Phenotype(int[] gene, char uuid) {
//            this.tailCurl = Character.isLetter(uuid);
//
//            float earSize = 0.0F;
//            float earFlop = 1.0F;
//            //SSC1
//            if (gene[68] == 2 || gene[69] == 2) {
//                earSize = earSize + 0.12F;
//                earFlop = earFlop - 0.44F;
//            } else if (gene[68] == 3 || gene[69] == 3) {
//                earSize = earSize + 0.03F;
//            }
//            //SSC5
//            if (gene[70] == 2 || gene[71] == 2) {
//                earSize = earSize + 0.35F;
//                earFlop = earFlop - 0.48F;
//            } else if (gene[70] == 3 && gene[71] == 3) {
//                earSize = earSize + 0.09F;
//            }
//            //SSC6
//            if (gene[72] == 2 || gene[73] == 2) {
//                earSize = earSize - 0.031F;
//            }
//            //SSC7
//            if (gene[74] == 2 || gene[75] == 2) {
//                earSize = earSize + 0.29F;
//                earFlop = earFlop - 0.62F;
//            } else if (gene[70] == 3 && gene[71] == 3) {
//                earSize = earSize + 0.09F;
//            }
//            //SSC9
//            if (gene[76] == 2 || gene[77] == 2) {
//                earSize = earSize + 0.11F;
//                earFlop = earFlop - 0.32F;
//            } else if (gene[70] == 3 && gene[71] == 3) {
//                earSize = earSize + 0.03F;
//            }
//            //SSC12
//            if (gene[78] == 2 || gene[79] == 2) {
//                earSize = earSize + 0.11F;
//                earFlop = earFlop - 0.14F;
//            } else if (gene[78] == 3 && gene[79] == 3) {
//                earSize = earSize + 0.03F;
//            }
//
//            for (int i = 80; i < 100; i+= 2) {
//                if (gene[i] != 1 && gene[i+1] != 1) {
//                    if (gene[i] == 2 || gene[i+1] == 2) {
//                        earSize = earSize + 0.007F;
//                    } else if (gene[i] == 3 || gene[i+1] == 3){
//                        earSize = earSize + 0.007F;
//                        earFlop = earFlop - 0.004F;
//                    } else {
//                        earSize = earSize + 0.007F;
//                        earFlop = earFlop - 0.007F;
//                    }
//                }
//            }
//            for (int i = 100; i < 120; i+= 2) {
//                if (gene[i] != 1 && gene[i+1] != 1) {
//                    if (gene[i] == 2 || gene[i+1] == 2) {
//                        earSize = earSize - 0.007F;
//                    } else if (gene[i] == 3 || gene[i+1] == 3){
//                        earSize = earSize - 0.007F;
//                        earFlop = earFlop + 0.004F;
//                    } else {
//                        earSize = earSize - 0.007F;
//                        earFlop = earFlop + 0.007F;
//                    }
//                }
//            }
//
//            if (earFlop > 1.0F) {
//                this.earFlopMod = 1.0F;
//            } else {
//                this.earFlopMod = earFlop < -1.0F ? -1.0F : earFlop;
//            }
//
//            if (earSize > 0.2F) {
//                if (earSize > 0.5F) {
//                    this.earSizeMod = 2;
//                } else {
//                    this.earSizeMod = 1;
//                }
//            }
//
//            //snoutLength
//            float snoutlength1 = 1.0F;
//            float snoutlength2 = 1.0F;
//            float snoutlength = 0.0F;
//
//            // 1 - 5, longest - shortest
//            for (int i = 1; i < gene[18];i++){
//                snoutlength1 = snoutlength1 - 0.1F;
//            }
//
//            for (int i = 1; i < gene[19];i++){
//                snoutlength2 = snoutlength2 - 0.1F;
//            }
//
//            for (int i = 1; i < gene[66];i++){
//                snoutlength1 = snoutlength1 - 0.1F;
//            }
//
//            for (int i = 1; i < gene[67];i++){
//                snoutlength2 = snoutlength2 - 0.1F;
//            }
//
//            //causes partial dominance of longer nose over shorter nose.
//            if (snoutlength1 > snoutlength2){
//                snoutlength = (snoutlength1*0.75F) + (snoutlength2*0.25F);
//            }else if (snoutlength1 < snoutlength2){
//                snoutlength = (snoutlength1*0.25F) + (snoutlength2*0.75F);
//            }else{
//                snoutlength = snoutlength1;
//            }
//
//            // 1 - 4, longest - shortest
//            if (gene[42] >= gene[43]) {
//                snoutlength = snoutlength + ((4 - gene[42])/8.0F);
//            } else {
//                snoutlength = snoutlength + ((4 - gene[43])/8.0F);
//            }
//
//            if (gene[44] >= 2 && gene[45] >= 2) {
//                if (gene[44] == 2 || gene[45] == 2) {
//                    snoutlength = snoutlength * 0.9F;
//                } else {
//                    snoutlength = snoutlength * 0.75F;
//                }
//            }
//
//            if (gene[46] == 2 && gene[47] == 2) {
//                snoutlength = snoutlength * 0.6F;
//            }
//
//            if (snoutlength > 1.0F) {
//                this.snoutLength = 1.0F;
//            } else if (snoutlength < 0.0F) {
//                this.snoutLength = 0.0F;
//            } else {
//                this.snoutLength = snoutlength;
//            }
//
//            float inbreedingFactor = 0.0F;
//
//            if (gene[20] == gene[21]) {
//                inbreedingFactor = 0.1667F;
//            }
//            if (gene[22] == gene[23]) {
//                inbreedingFactor = inbreedingFactor + 0.1667F;
//            }
//            if (gene[24] == gene[25]) {
//                inbreedingFactor = inbreedingFactor + 0.1667F;
//            }
//            if (gene[26] == gene[27]) {
//                inbreedingFactor = inbreedingFactor + 0.1667F;
//            }
//            if (gene[28] == gene[29]) {
//                inbreedingFactor = inbreedingFactor + 0.1667F;
//            }
//            if (gene[30] == gene[31]) {
//                inbreedingFactor = inbreedingFactor + 0.1667F;
//            }
//
//            this.tailCurlAmount = inbreedingFactor;
//
//            int shape = 1;
//
//            if (gene != null) {
//                if (gene[56] != 1 && gene[57] != 1) {
//                    if (gene[56] + gene[57] <= 8) {
//                        shape = 0;
//                    } else if (gene[56] == 5 || gene[57] == 5) {
//                        shape = 2;
//                    } else if (gene[56] == 6 || gene[57] == 6) {
//                        shape = 3;
//                    } else if (gene[56] == 7 && gene[57] == 7) {
//
//                    }
//                }
//            }
//            this.shape = shape;
//
//            this.hasWaddles = gene[32] == 1 || gene[33] == 1;
//        }
//    }
//}
