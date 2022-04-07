package mokiyoki.enhancedanimals.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import mokiyoki.enhancedanimals.entity.EnhancedCow;
import mokiyoki.enhancedanimals.entity.EnhancedMoobloom;
import mokiyoki.enhancedanimals.entity.EnhancedMooshroom;
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

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@OnlyIn(Dist.CLIENT)
public class ModelEnhancedCow<T extends EnhancedCow> extends EnhancedAnimalModel<T> {
    protected WrappedModelPart theCow;

    protected WrappedModelPart theHead;
    protected WrappedModelPart theEarLeft;
    protected WrappedModelPart theEarRight;
    protected WrappedModelPart theHornNub;
    protected WrappedModelPart theHornLeft;
    protected WrappedModelPart theHornRight;
    protected WrappedModelPart theNeck;
    protected WrappedModelPart theHump;
    protected WrappedModelPart theBody;
    protected WrappedModelPart theLegFrontLeft;
    protected WrappedModelPart theLegFrontRight;
    protected WrappedModelPart theLegBackLeft;
    protected WrappedModelPart theLegBackRight;
    protected WrappedModelPart theTail;

    private WrappedModelPart headFemale;
    private WrappedModelPart headMale;
    private WrappedModelPart noseFemale;
    private WrappedModelPart noseMale;
    private WrappedModelPart jaw;
    private WrappedModelPart headHair;
    private WrappedModelPart headNubXSmall;
    private WrappedModelPart headNubSmall;
    private WrappedModelPart headNubMedium;
    private WrappedModelPart headNubLarge;
    private WrappedModelPart headNubXLarge;
    
    private WrappedModelPart earXShortL;
    private WrappedModelPart earShortL;
    private WrappedModelPart earMediumL;
    private WrappedModelPart earLongL;
    private WrappedModelPart earXLongL;
    private WrappedModelPart earXShortR;
    private WrappedModelPart earShortR;
    private WrappedModelPart earMediumR;
    private WrappedModelPart earLongR;
    private WrappedModelPart earXLongR;

    private WrappedModelPart hornL0;
    private WrappedModelPart hornL1;
    private WrappedModelPart hornL2;
    private WrappedModelPart hornL3;
    private WrappedModelPart hornL4;
    private WrappedModelPart hornL5;
    private WrappedModelPart hornL6;
    private WrappedModelPart hornL7;
    private WrappedModelPart hornL8;
    private WrappedModelPart hornL9;
    private WrappedModelPart hornR0;
    private WrappedModelPart hornR1;
    private WrappedModelPart hornR2;
    private WrappedModelPart hornR3;
    private WrappedModelPart hornR4;
    private WrappedModelPart hornR5;
    private WrappedModelPart hornR6;
    private WrappedModelPart hornR7;
    private WrappedModelPart hornR8;
    private WrappedModelPart hornR9;

    private WrappedModelPart neckFemale;
    private WrappedModelPart neckMale;

    private WrappedModelPart bodyNormal;
    private WrappedModelPart bodyHairy;
    private WrappedModelPart legFrontLeft;
    private WrappedModelPart legFrontRight;
    private WrappedModelPart legBackLeft;
    private WrappedModelPart legBackRight;
    private WrappedModelPart legFrontLeftDwarf;
    private WrappedModelPart legFrontRightDwarf;
    private WrappedModelPart legBackLeftDwarf;
    private WrappedModelPart legBackRightDwarf;

    private WrappedModelPart tailBase;
    private WrappedModelPart tailMiddle;
    private WrappedModelPart tailEnd;
    private WrappedModelPart tailBrush;

    private WrappedModelPart mushroom0;
    private WrappedModelPart mushroom1;
    private WrappedModelPart mushroom2;
    private WrappedModelPart mushroom3;
    private WrappedModelPart mushroom4;
    private WrappedModelPart mushroom5;
    private WrappedModelPart mushroom6;
    private WrappedModelPart mushroom7;
    private WrappedModelPart mushroom8;
    private WrappedModelPart mushroom9;
    private WrappedModelPart mushroom10;
    private WrappedModelPart mushroom11;
    private WrappedModelPart mushroom12;

    private WrappedModelPart chest;

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition base = meshdefinition.getRoot().addOrReplaceChild("base", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition bCow = base.addOrReplaceChild("bCow", CubeListBuilder.create(), PartPose.offset(0.0F, 14.0F, 0.0F));
        PartDefinition bBody = bCow.addOrReplaceChild("bBody", CubeListBuilder.create(), PartPose.offset(0.0F, -11.0F, -10.0F));
        PartDefinition bHump = bBody.addOrReplaceChild("bHump", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition bNeck = bBody.addOrReplaceChild("bNeck", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, -1.0F));
        PartDefinition bHead = bNeck.addOrReplaceChild("bHead", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, -7.0F));
        PartDefinition bEarLeft = bHead.addOrReplaceChild("bEarL", CubeListBuilder.create(), PartPose.offset(3.0F, 0.5F, -2.5F));
        PartDefinition bEarRight = bHead.addOrReplaceChild("bEarR", CubeListBuilder.create(), PartPose.offset(-3.0F, 0.0F, -4.0F));
        PartDefinition bHornNub = bHead.addOrReplaceChild("bHornNub", CubeListBuilder.create(), PartPose.offset(0.0F, -1.0F, -0.5F));
        PartDefinition bHornLeft = bHornNub.addOrReplaceChild("bHornL", CubeListBuilder.create(), PartPose.offset(1.0F, 1.5F, -2.0F));
        PartDefinition bHornRight = bHornNub.addOrReplaceChild("bHornR", CubeListBuilder.create(), PartPose.offset(-1.0F, 1.5F, -2.0F));
        PartDefinition bLegFrontLeft = bCow.addOrReplaceChild("bLegFL", CubeListBuilder.create(), PartPose.offset(3.0F, 0.0F, -10.0F));
        PartDefinition bLegFrontRight = bCow.addOrReplaceChild("bLegFR", CubeListBuilder.create(), PartPose.offset(-3.0F, 0.0F, -10.0F));
        PartDefinition bLegBackLeft = bCow.addOrReplaceChild("bLegBL", CubeListBuilder.create(), PartPose.offset(3.0F, 0.0F, 9.0F));
        PartDefinition bLegBackRight = bCow.addOrReplaceChild("bLegBR", CubeListBuilder.create(), PartPose.offset(-3.0F, 0.0F, 9.0F));
        PartDefinition bTail = bBody.addOrReplaceChild("bTail", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 22.0F));

        bHead.addOrReplaceChild("eyes", CubeListBuilder.create()
                    .texOffs(22, 40)
                    .addBox(3.0F, 2.0F, -7.0F, 1, 2, 2, new CubeDeformation(0.01F))
                    .texOffs(0, 40)
                    .addBox(-4.0F, 2.0F, -7.0F, 1, 2, 2, new CubeDeformation(0.01F)),
                PartPose.ZERO
        );
        bHead.addOrReplaceChild("headF", CubeListBuilder.create()
                        .texOffs(0, 38)
                        .addBox(-4.0F, 0.0F, -7.0F, 8, 7, 6),
                PartPose.ZERO
        );
        bHead.addOrReplaceChild("headM", CubeListBuilder.create()
                        .texOffs(0, 38)
                        .addBox(-4.0F, 0.0F, -7.0F, 8, 7, 6),
                PartPose.ZERO
        );
        bHead.addOrReplaceChild("noseF", CubeListBuilder.create()
                        .texOffs(28, 38)
                        .addBox(-2.0F, 0.6F, -10.75F, 4, 4, 4, new CubeDeformation(-0.25F))
                        .texOffs(16, 51)
                        .addBox(-2.5F, 0.7F, -13.0F, 5, 4, 3, new CubeDeformation(-0.125F)),
                PartPose.ZERO
        );
        bHead.addOrReplaceChild("noseM", CubeListBuilder.create()
                        .texOffs(28, 38)
                        .addBox(-2.0F, -0.15F, -10.0F, 4, 5, 4, new CubeDeformation(0.25F))
                        .texOffs(16, 51)
                        .addBox(-2.5F, 0.2F, -13.0F, 5, 4, 3),
                PartPose.ZERO
        );
        bHead.addOrReplaceChild("jaw", CubeListBuilder.create()
                        .texOffs(25, 51)
                        .addBox(-1.5F, 1.0F, -10.0F, 3, 3, 7, new CubeDeformation(0.1F))
                        .texOffs(38, 51)
                        .addBox(-1.5F, 3.0F, -10.0F, 3, 1, 6, new CubeDeformation(-0.1F)),
                PartPose.offset(0.0F, 4.0F, -2.0F)
        );
        bHead.addOrReplaceChild("headHair", CubeListBuilder.create()
                        .texOffs(51, 50)
                        .addBox(-5.0F, -1.0F, -8.0F, 10, 8, 8),
                PartPose.ZERO
        );

        bHornNub.addOrReplaceChild("nubXS", CubeListBuilder.create()
                        .texOffs(44, 42)
                        .addBox(-2.0F, 1.0F, -0.5F, 4, 2, 2),
                PartPose.rotation(-Mth.HALF_PI, 0.0F, 0.0F)
        );
        bHornNub.addOrReplaceChild("nubS", CubeListBuilder.create()
                        .texOffs(44, 41)
                        .addBox(-2.0F, 1.0F, -0.5F, 4, 3, 2),
                PartPose.rotation(-Mth.HALF_PI, 0.0F, 0.0F)
        );
        bHornNub.addOrReplaceChild("nubM", CubeListBuilder.create()
                        .texOffs(44, 40)
                        .addBox(-2.0F, 1.0F, -0.5F, 4, 4, 2),
                PartPose.rotation(-Mth.HALF_PI, 0.0F, 0.0F)
        );
        bHornNub.addOrReplaceChild("nubL", CubeListBuilder.create()
                        .texOffs(44, 39)
                        .addBox(-2.0F, 1.0F, -0.5F, 4, 5, 2),
                PartPose.rotation(-Mth.HALF_PI, 0.0F, 0.0F)
        );
        bHornNub.addOrReplaceChild("nubXL", CubeListBuilder.create()
                        .texOffs(44, 38)
                        .addBox(-2.0F, 1.0F, -0.5F, 4, 6, 2),
                PartPose.rotation(-Mth.HALF_PI, 0.0F, 0.0F)
        );

        bEarLeft.addOrReplaceChild("earXSL", CubeListBuilder.create()
                        .texOffs(8, 51)
                        .addBox(0.0F, -3.0F, -0.5F, 3, 3, 1),
                PartPose.ZERO
        );
        bEarLeft.addOrReplaceChild("earSL", CubeListBuilder.create()
                        .texOffs(8, 51)
                        .addBox(0.0F, -4.0F, -0.5F, 3, 4, 1),
                PartPose.ZERO
        );
        bEarLeft.addOrReplaceChild("earML", CubeListBuilder.create()
                        .texOffs(8, 51)
                        .addBox(0.0F, -5.0F, -0.5F, 3, 5, 1),
                PartPose.ZERO
        );
        bEarLeft.addOrReplaceChild("earLL", CubeListBuilder.create()
                        .texOffs(8, 51)
                        .addBox(0.0F, -6.0F, -0.5F, 3, 6, 1, new CubeDeformation(0.15F, 0.15F, 0.0F)),
                PartPose.ZERO
        );
        bEarLeft.addOrReplaceChild("earXLL", CubeListBuilder.create()
                        .texOffs(8, 51)
                        .addBox(0.0F, -7.0F, -0.5F, 3, 7, 1, new CubeDeformation(0.3F, 0.3F, 0.0F)),
                PartPose.ZERO
        );

        bEarRight.addOrReplaceChild("earXSR", CubeListBuilder.create()
                        .texOffs(0, 51)
                        .addBox(-3.0F, -3.0F, -0.5F, 3, 3, 1),
                PartPose.ZERO
        );
        bEarRight.addOrReplaceChild("earSR", CubeListBuilder.create()
                        .texOffs(0, 51)
                        .addBox(-3.0F, -4.0F, -0.5F, 3, 4, 1),
                PartPose.ZERO
        );
        bEarRight.addOrReplaceChild("earMR", CubeListBuilder.create()
                        .texOffs(0, 51)
                        .addBox(-3.0F, -5.0F, -0.5F, 3, 5, 1),
                PartPose.ZERO
        );
        bEarRight.addOrReplaceChild("earLR", CubeListBuilder.create()
                        .texOffs(0, 51)
                        .addBox(-3.0F, -6.0F, -0.5F, 3, 6, 1, new CubeDeformation(0.15F, 0.15F, 0.0F)),
                PartPose.ZERO
        );
        bEarRight.addOrReplaceChild("earXLR", CubeListBuilder.create()
                        .texOffs(0, 51)
                        .addBox(-3.0F, -7.0F, -0.5F, 3, 7, 1, new CubeDeformation(0.3F, 0.3F, 0.0F)),
                PartPose.ZERO
        );

        bHornLeft.addOrReplaceChild("hornL0", CubeListBuilder.create()
                        .texOffs(57, 85)
                        .addBox(-2.0F, -4.0F, -2.0F, 4, 4, 4, new CubeDeformation(-1.0F)),
                PartPose.ZERO
        );
        bHornLeft.addOrReplaceChild("hornL1", CubeListBuilder.create()
                        .texOffs(57, 93)
                        .addBox(-2.0F, -4.0F, -2.0F, 4, 4, 4, new CubeDeformation(-1.001F)),
                PartPose.ZERO
        );
        bHornLeft.addOrReplaceChild("hornL2", CubeListBuilder.create()
                        .texOffs(57, 101)
                        .addBox(-2.0F, -4.0F, -2.0F, 4, 4, 4, new CubeDeformation(-1.002F)),
                PartPose.ZERO
        );
        bHornLeft.addOrReplaceChild("hornL3", CubeListBuilder.create()
                        .texOffs(57, 109)
                        .addBox(-2.0F, -4.0F, -2.0F, 4, 4, 4, new CubeDeformation(-1.003F)),
                PartPose.ZERO
        );
        bHornLeft.addOrReplaceChild("hornL4", CubeListBuilder.create()
                        .texOffs(57, 117)
                        .addBox(-2.0F, -4.0F, -2.0F, 4, 4, 4, new CubeDeformation(-1.004F)),
                PartPose.ZERO
        );
        bHornLeft.addOrReplaceChild("hornL5", CubeListBuilder.create()
                        .texOffs(57, 117)
                        .addBox(-2.0F, -4.0F, -2.0F, 4, 4, 4, new CubeDeformation(-1.1F)),
                PartPose.ZERO
        );
        bHornLeft.addOrReplaceChild("hornL6", CubeListBuilder.create()
                        .texOffs(57, 117)
                        .addBox(-2.0F, -4.0F, -2.0F, 4, 4, 4, new CubeDeformation(-1.2F)),
                PartPose.ZERO
        );
        bHornLeft.addOrReplaceChild("hornL7", CubeListBuilder.create()
                        .texOffs(57, 117)
                        .addBox(-2.0F, -4.0F, -2.0F, 4, 4, 4, new CubeDeformation(-1.3F)),
                PartPose.ZERO
        );
        bHornLeft.addOrReplaceChild("hornL8", CubeListBuilder.create()
                        .texOffs(57, 117)
                        .addBox(-2.0F, -4.0F, -2.0F, 4, 4, 4, new CubeDeformation(-1.4F)),
                PartPose.ZERO
        );
        bHornLeft.addOrReplaceChild("hornL9", CubeListBuilder.create()
                        .texOffs(57, 117)
                        .addBox(-2.0F, -4.0F, -2.0F, 4, 4, 4, new CubeDeformation(-1.5F)),
                PartPose.ZERO
        );

        bHornRight.addOrReplaceChild("hornR0", CubeListBuilder.create()
                        .texOffs(57, 85)
                        .addBox(-2.0F, -4.0F, -2.0F, 4, 4, 4, new CubeDeformation(-1.0F)),
                PartPose.ZERO
        );
        bHornRight.addOrReplaceChild("hornR1", CubeListBuilder.create()
                        .texOffs(57, 93)
                        .addBox(-2.0F, -4.0F, -2.0F, 4, 4, 4, new CubeDeformation(-1.001F)),
                PartPose.ZERO
        );
        bHornRight.addOrReplaceChild("hornR2", CubeListBuilder.create()
                        .texOffs(57, 101)
                        .addBox(-2.0F, -4.0F, -2.0F, 4, 4, 4, new CubeDeformation(-1.002F)),
                PartPose.ZERO
        );
        bHornRight.addOrReplaceChild("hornR3", CubeListBuilder.create()
                        .texOffs(57, 109)
                        .addBox(-2.0F, -4.0F, -2.0F, 4, 4, 4, new CubeDeformation(-1.003F)),
                PartPose.ZERO
        );
        bHornRight.addOrReplaceChild("hornR4", CubeListBuilder.create()
                        .texOffs(57, 117)
                        .addBox(-2.0F, -4.0F, -2.0F, 4, 4, 4, new CubeDeformation(-1.004F)),
                PartPose.ZERO
        );
        bHornRight.addOrReplaceChild("hornR5", CubeListBuilder.create()
                        .texOffs(57, 117)
                        .addBox(-2.0F, -4.0F, -2.0F, 4, 4, 4, new CubeDeformation(-1.1F)),
                PartPose.ZERO
        );
        bHornRight.addOrReplaceChild("hornR6", CubeListBuilder.create()
                        .texOffs(57, 117)
                        .addBox(-2.0F, -4.0F, -2.0F, 4, 4, 4, new CubeDeformation(-1.2F)),
                PartPose.ZERO
        );
        bHornRight.addOrReplaceChild("hornR7", CubeListBuilder.create()
                        .texOffs(57, 117)
                        .addBox(-2.0F, -4.0F, -2.0F, 4, 4, 4, new CubeDeformation(-1.3F)),
                PartPose.ZERO
        );
        bHornRight.addOrReplaceChild("hornR8", CubeListBuilder.create()
                        .texOffs(57, 117)
                        .addBox(-2.0F, -4.0F, -2.0F, 4, 4, 4, new CubeDeformation(-1.4F)),
                PartPose.ZERO
        );
        bHornRight.addOrReplaceChild("hornR9", CubeListBuilder.create()
                        .texOffs(57, 117)
                        .addBox(-2.0F, -4.0F, -2.0F, 4, 4, 4, new CubeDeformation(-1.5F)),
                PartPose.ZERO
        );

        bNeck.addOrReplaceChild("neckF", CubeListBuilder.create()
                        .texOffs(46, 0)
                        .addBox(-3.0F, 0.0F, -8.0F, 6, 8, 11),
                PartPose.ZERO
        );
        bNeck.addOrReplaceChild("neckM", CubeListBuilder.create()
                        .texOffs(46, 0)
                        .addBox(-3.0F, 0.0F, -8.0F, 6, 8, 11),
                PartPose.ZERO
        );

        bBody.addOrReplaceChild("bodyN", CubeListBuilder.create()
                        .texOffs(0, 0)
                        .addBox(-6.0F, 0.0F, 0.0F, 12, 11, 22)
                        .texOffs(47, 24)
                        .addBox(-6.0F, 11.0F, 0.0F, 12, 0, 22),
                PartPose.ZERO
        );
        bBody.addOrReplaceChild("bodyH", CubeListBuilder.create()
                        .texOffs(0, 0)
                        .addBox(-6.0F, 0.0F, 0.0F, 12, 16, 22, new CubeDeformation(0.01F))
                        .texOffs(47, 24)
                        .addBox(-6.0F, 11.0F, 0.0F, 12, 0, 22, new CubeDeformation(0.01F)),
                PartPose.ZERO
        );

        bLegFrontLeft.addOrReplaceChild("legFL", CubeListBuilder.create()
                        .texOffs(0, 59)
                        .addBox(0.0F, 0.0F, 0.0F, 3, 10, 3),
                PartPose.ZERO
        );
        bLegFrontLeft.addOrReplaceChild("legFLD", CubeListBuilder.create()
                        .texOffs(0, 59)
                        .addBox(0.0F, 0.0F, 0.0F, 3, 7, 3),
                PartPose.ZERO
        );

        bLegFrontRight.addOrReplaceChild("legFR", CubeListBuilder.create()
                        .texOffs(12, 59)
                        .addBox(-3.0F, 0.0F, 0.0F, 3, 10, 3),
                PartPose.ZERO
        );
        bLegFrontRight.addOrReplaceChild("legFRD", CubeListBuilder.create()
                        .texOffs(12, 59)
                        .addBox(-3.0F, 0.0F, 0.0F, 3, 7, 3),
                PartPose.ZERO
        );

        bLegBackLeft.addOrReplaceChild("legBL", CubeListBuilder.create()
                        .texOffs(0, 72)
                        .addBox(0.0F, 0.0F, 0.0F, 3, 10, 3),
                PartPose.ZERO
        );
        bLegBackLeft.addOrReplaceChild("legBLD", CubeListBuilder.create()
                        .texOffs(0, 72)
                        .addBox(0.0F, 0.0F, 0.0F, 3, 7, 3),
                PartPose.ZERO
        );

        bLegBackRight.addOrReplaceChild("legBR", CubeListBuilder.create()
                        .texOffs(12, 72)
                        .addBox(-3.0F, 0.0F, 0.0F, 3, 10, 3),
                PartPose.ZERO
        );
        bLegBackRight.addOrReplaceChild("legBRD", CubeListBuilder.create()
                        .texOffs(12, 72)
                        .addBox(-3.0F, 0.0F, 0.0F, 3, 7, 3),
                PartPose.ZERO
        );

        bTail.addOrReplaceChild("tail0", CubeListBuilder.create()
                        .texOffs(0, 0)
                        .addBox(-1.0F, 0.0F, 0.0F, 2, 4, 1),
                PartPose.ZERO
        );
        bTail.addOrReplaceChild("tail1", CubeListBuilder.create()
                        .texOffs(6, 0)
                        .addBox(-0.5F, 0.0F, 0.0F, 1, 4, 1),
                PartPose.offset(0.0F, 4.0F, 0.0F)
        );
        bTail.addOrReplaceChild("tail2", CubeListBuilder.create()
                        .texOffs(10, 0)
                        .addBox(-0.5F, 0.0F, 0.0F, 1, 3, 1, new CubeDeformation(-0.01F)),
                PartPose.offset(0.0F, 3.0F, 0.0F)
        );
        bTail.addOrReplaceChild("tailB", CubeListBuilder.create()
                        .texOffs(14, 0)
                        .addBox(-1.0F, 0.0F, -0.5F, 2, 3, 2),
                PartPose.offset(0.0F, 3.0F, 0.0F)
        );

        bCow.addOrReplaceChild("mushroom0", CubeListBuilder.create()
                        .texOffs(54, 64)
                        .addBox(0.0F, -8.0F, 0.0F, 1, 8, 8)
                        .texOffs(62, 71)
                        .addBox(-3.0F, -8.0F, 4.0F, 8, 8, 1),
                PartPose.ZERO
        );
        bCow.addOrReplaceChild("mushroom1", CubeListBuilder.create()
                        .texOffs(54, 64)
                        .addBox(0.0F, -8.0F, 0.0F, 1, 8, 8)
                        .texOffs(62, 71)
                        .addBox(-3.0F, -8.0F, 4.0F, 8, 8, 1),
                PartPose.ZERO
        );
        bCow.addOrReplaceChild("mushroom2", CubeListBuilder.create()
                        .texOffs(54, 64)
                        .addBox(0.0F, -8.0F, 0.0F, 1, 8, 8)
                        .texOffs(62, 71)
                        .addBox(-3.0F, -8.0F, 4.0F, 8, 8, 1),
                PartPose.ZERO
        );
        bCow.addOrReplaceChild("mushroom3", CubeListBuilder.create()
                        .texOffs(54, 64)
                        .addBox(0.0F, -8.0F, 0.0F, 1, 8, 8)
                        .texOffs(62, 71)
                        .addBox(-3.0F, -8.0F, 4.0F, 8, 8, 1),
                PartPose.ZERO
        );
        bCow.addOrReplaceChild("mushroom4", CubeListBuilder.create()
                        .texOffs(54, 64)
                        .addBox(0.0F, -8.0F, 0.0F, 1, 8, 8)
                        .texOffs(62, 71)
                        .addBox(-3.0F, -8.0F, 4.0F, 8, 8, 1),
                PartPose.ZERO
        );
        bCow.addOrReplaceChild("mushroom5", CubeListBuilder.create()
                        .texOffs(54, 64)
                        .addBox(0.0F, -8.0F, 0.0F, 1, 8, 8)
                        .texOffs(62, 71)
                        .addBox(-3.0F, -8.0F, 4.0F, 8, 8, 1),
                PartPose.ZERO
        );
        bCow.addOrReplaceChild("mushroom6", CubeListBuilder.create()
                        .texOffs(54, 64)
                        .addBox(0.0F, -8.0F, 0.0F, 1, 8, 8)
                        .texOffs(62, 71)
                        .addBox(-3.0F, -8.0F, 4.0F, 8, 8, 1),
                PartPose.ZERO
        );
        bCow.addOrReplaceChild("mushroom7", CubeListBuilder.create()
                        .texOffs(54, 64)
                        .addBox(0.0F, -8.0F, 0.0F, 1, 8, 8)
                        .texOffs(62, 71)
                        .addBox(-3.0F, -8.0F, 4.0F, 8, 8, 1),
                PartPose.ZERO
        );
        bCow.addOrReplaceChild("mushroom8", CubeListBuilder.create()
                        .texOffs(54, 64)
                        .addBox(0.0F, -8.0F, 0.0F, 1, 8, 8)
                        .texOffs(62, 71)
                        .addBox(-3.0F, -8.0F, 4.0F, 8, 8, 1),
                PartPose.ZERO
        );
        bCow.addOrReplaceChild("mushroom9", CubeListBuilder.create()
                        .texOffs(54, 64)
                        .addBox(0.0F, -8.0F, 0.0F, 1, 8, 8)
                        .texOffs(62, 71)
                        .addBox(-3.0F, -8.0F, 4.0F, 8, 8, 1),
                PartPose.ZERO
        );
        bCow.addOrReplaceChild("mushroom10", CubeListBuilder.create()
                        .texOffs(54, 64)
                        .addBox(0.0F, -8.0F, 0.0F, 1, 8, 8)
                        .texOffs(62, 71)
                        .addBox(-3.0F, -8.0F, 4.0F, 8, 8, 1),
                PartPose.ZERO
        );
        bCow.addOrReplaceChild("mushroom11", CubeListBuilder.create()
                        .texOffs(54, 64)
                        .addBox(0.0F, -8.0F, 0.0F, 1, 8, 8)
                        .texOffs(62, 71)
                        .addBox(-3.0F, -8.0F, 4.0F, 8, 8, 1),
                PartPose.ZERO
        );
        bCow.addOrReplaceChild("mushroom12", CubeListBuilder.create()
                        .texOffs(54, 64)
                        .addBox(0.0F, -8.0F, 0.0F, 1, 8, 8)
                        .texOffs(62, 71)
                        .addBox(-3.0F, -8.0F, 4.0F, 8, 8, 1),
                PartPose.ZERO
        );

        return LayerDefinition.create(meshdefinition, 256, 256);
    }

    public ModelEnhancedCow(ModelPart modelPart, boolean isMooshroom) {
        super(modelPart);

        ModelPart base = modelPart.getChild("base");
        ModelPart bCow = base.getChild("bCow");
        ModelPart bBody = bCow.getChild("bBody");
        ModelPart bNeck = bBody.getChild("bNeck");
        ModelPart bHead = bNeck.getChild("bHead");
        ModelPart bEarLeft = bHead.getChild("bEarL");
        ModelPart bEarRight = bHead.getChild("bEarR");
        ModelPart bHornNub = bHead.getChild("bHornNub");
        ModelPart bHornLeft = bHornNub.getChild("bHornL");
        ModelPart bHornRight = bHornNub.getChild("bHornR");
        ModelPart bLegFL = bCow.getChild("bLegFL");
        ModelPart bLegFR = bCow.getChild("bLegFR");
        ModelPart bLegBL = bCow.getChild("bLegBL");
        ModelPart bLegBR = bCow.getChild("bLegBR");
        ModelPart bTail = bBody.getChild("bTail");

        this.theCow = new WrappedModelPart(bCow, "bCow");
        this.theBody = new WrappedModelPart(bBody, "bBody");
        this.theNeck = new WrappedModelPart(bNeck, "bNeck");
        this.theHead = new WrappedModelPart(bHead, "bHead");
        this.theEarLeft = new WrappedModelPart(bEarLeft, "bEarL");
        this.theEarRight = new WrappedModelPart(bEarRight, "bEarR");
        this.theHornNub = new WrappedModelPart(bHornNub, "bHornNub");
        this.theHornLeft = new WrappedModelPart(bHornLeft, "bHornL");
        this.theHornRight = new WrappedModelPart(bHornRight, "bHornR");
        this.theLegFrontLeft = new WrappedModelPart(bLegFL, "bLegFL");
        this.theLegFrontRight = new WrappedModelPart(bLegFR, "bLegFR");
        this.theLegBackLeft = new WrappedModelPart(bLegBL, "bLegBL");
        this.theLegBackRight = new WrappedModelPart(bLegBR, "bLegBR");
        this.theTail = new WrappedModelPart(bTail, "bTail");

        this.bodyNormal = new WrappedModelPart("bodyN", bBody);
        this.bodyHairy = new WrappedModelPart("bodyH", bBody);

        this.neckFemale = new WrappedModelPart("neckF", bNeck);
        this.neckMale = new WrappedModelPart("neckF", bNeck);

        this.headFemale = new WrappedModelPart("headF", bHead);
        this.headMale = new WrappedModelPart("headM", bHead);
        this.noseFemale = new WrappedModelPart("noseF", bHead);
        this.noseMale = new WrappedModelPart("noseM", bHead);
        this.jaw = new WrappedModelPart("jaw", bHead);
        this.headHair = new WrappedModelPart("headHair", bHead);

        this.headNubXSmall = new WrappedModelPart("nubXS", bHornNub);
        this.headNubSmall = new WrappedModelPart("nubS", bHornNub);
        this.headNubMedium = new WrappedModelPart("nubM", bHornNub);
        this.headNubLarge = new WrappedModelPart("nubL", bHornNub);
        this.headNubXLarge = new WrappedModelPart("nubXL", bHornNub);

        this.eyes = new WrappedModelPart("eyes", bHead);

        this.earXShortL = new WrappedModelPart("earXSL", bEarLeft);
        this.earShortL = new WrappedModelPart("earSL", bEarLeft);
        this.earMediumL = new WrappedModelPart("earML", bEarLeft);
        this.earLongL = new WrappedModelPart("earLL", bEarLeft);
        this.earXLongL = new WrappedModelPart("earXLL", bEarLeft);
        this.earXShortR = new WrappedModelPart("earXSR", bEarRight);
        this.earShortR = new WrappedModelPart("earSR", bEarRight);
        this.earMediumR = new WrappedModelPart("earMR", bEarRight);
        this.earLongR = new WrappedModelPart("earLR", bEarRight);
        this.earXLongR = new WrappedModelPart("earXLR", bEarRight);

        this.hornL9 = new WrappedModelPart("hornL9", bHornLeft, true);
        this.hornL8 = new WrappedModelPart("hornL8", bHornLeft, true, this.hornL9);
        this.hornL7 = new WrappedModelPart("hornL7", bHornLeft, true, this.hornL8);
        this.hornL6 = new WrappedModelPart("hornL6", bHornLeft, true, this.hornL7);
        this.hornL5 = new WrappedModelPart("hornL5", bHornLeft, true, this.hornL6);
        this.hornL4 = new WrappedModelPart("hornL4", bHornLeft, true, this.hornL5);
        this.hornL3 = new WrappedModelPart("hornL3", bHornLeft, true, this.hornL4);
        this.hornL2 = new WrappedModelPart("hornL2", bHornLeft, true, this.hornL3);
        this.hornL1 = new WrappedModelPart("hornL1", bHornLeft, true, this.hornL2);
        this.hornL0 = new WrappedModelPart("hornL0", bHornLeft, true, this.hornL1);
        this.hornR9 = new WrappedModelPart("hornR9", bHornRight, true);
        this.hornR8 = new WrappedModelPart("hornR8", bHornRight, true, this.hornR9);
        this.hornR7 = new WrappedModelPart("hornR7", bHornRight, true, this.hornR8);
        this.hornR6 = new WrappedModelPart("hornR6", bHornRight, true, this.hornR7);
        this.hornR5 = new WrappedModelPart("hornR5", bHornRight, true, this.hornR6);
        this.hornR4 = new WrappedModelPart("hornR4", bHornRight, true, this.hornR5);
        this.hornR3 = new WrappedModelPart("hornR3", bHornRight, true, this.hornR4);
        this.hornR2 = new WrappedModelPart("hornR2", bHornRight, true, this.hornR3);
        this.hornR1 = new WrappedModelPart("hornR1", bHornRight, true, this.hornR2);
        this.hornR0 = new WrappedModelPart("hornR0", bHornRight, true, this.hornR1);

        this.legFrontLeft = new WrappedModelPart("legFL", bLegFL);
        this.legFrontLeftDwarf = new WrappedModelPart("legFLD", bLegFL);
        this.legFrontRight = new WrappedModelPart("legFR", bLegFR);
        this.legFrontRightDwarf = new WrappedModelPart("legFRD", bLegFR);
        this.legBackLeft = new WrappedModelPart("legBL", bLegBL);
        this.legBackLeftDwarf = new WrappedModelPart("legBLD", bLegBL);
        this.legBackRight = new WrappedModelPart("legBR", bLegBR);
        this.legBackRightDwarf = new WrappedModelPart("legBRD", bLegBR);

        this.tailBase = new WrappedModelPart("tail0", bTail);
        this.tailMiddle = new WrappedModelPart("tail1", bTail);
        this.tailEnd = new WrappedModelPart("tail2", bTail);
        this.tailBrush = new WrappedModelPart("tailB", bTail);

        this.theCow.addChild(this.theBody);
        this.theBody.addChild(this.theNeck);
        this.theNeck.addChild(this.theHead);
        this.theHead.addChild(this.theEarLeft);
        this.theHead.addChild(this.theEarRight);
        this.theHead.addChild(this.theHornNub);
        this.theHornNub.addChild(this.theHornLeft);
        this.theHornNub.addChild(this.theHornRight);
        this.theCow.addChild(this.theLegFrontLeft);
        this.theCow.addChild(this.theLegFrontRight);
        this.theCow.addChild(this.theLegBackLeft);
        this.theCow.addChild(this.theLegBackRight);
        this.theBody.addChild(this.theTail);

        this.theHead.addChild(this.eyes);
        this.theHead.addChild(this.jaw);
        this.theHead.addChild(this.headFemale);
        this.theHead.addChild(this.headMale);
        this.theHead.addChild(this.noseFemale);
        this.theHead.addChild(this.noseMale);
        this.theHead.addChild(this.headHair);

        this.theHornNub.addChild(this.headNubXSmall);
        this.theHornNub.addChild(this.headNubSmall);
        this.theHornNub.addChild(this.headNubMedium);
        this.theHornNub.addChild(this.headNubLarge);
        this.theHornNub.addChild(this.headNubXLarge);
        
        this.theEarLeft.addChild(this.earXShortL);
        this.theEarLeft.addChild(this.earShortL);
        this.theEarLeft.addChild(this.earMediumL);
        this.theEarLeft.addChild(this.earLongL);
        this.theEarLeft.addChild(this.earXLongL);

        this.theHornLeft.addChild(this.hornL0);
        this.theHornRight.addChild(this.hornR0);

        this.theEarRight.addChild(this.earXShortR);
        this.theEarRight.addChild(this.earShortR);
        this.theEarRight.addChild(this.earMediumR);
        this.theEarRight.addChild(this.earLongR);
        this.theEarRight.addChild(this.earXLongR);

        this.theNeck.addChild(this.neckFemale);
        this.theNeck.addChild(this.neckMale);

        this.theBody.addChild(this.bodyNormal);
        this.theBody.addChild(this.bodyHairy);
        
        this.theLegFrontLeft.addChild(this.legFrontLeft);
        this.theLegFrontLeft.addChild(this.legFrontLeftDwarf);

        this.theLegFrontRight.addChild(this.legFrontRight);
        this.theLegFrontRight.addChild(this.legFrontRightDwarf);

        this.theLegBackLeft.addChild(this.legBackLeft);
        this.theLegBackLeft.addChild(this.legBackLeftDwarf);

        this.theLegBackRight.addChild(this.legBackRight);
        this.theLegBackRight.addChild(this.legBackRightDwarf);

        this.theTail.addChild(this.tailBase);
        this.tailBase.addChild(this.tailMiddle);
        this.tailMiddle.addChild(this.tailEnd);
        this.tailEnd.addChild(this.tailBrush);

        if (isMooshroom) {
            this.mushroom0 = new WrappedModelPart("mushroom0", bCow);
            this.theBody.addChild(this.mushroom0);
            this.mushroom1 = new WrappedModelPart("mushroom1", bCow);
            this.theBody.addChild(this.mushroom1);
            this.mushroom2 = new WrappedModelPart("mushroom2", bCow);
            this.theBody.addChild(this.mushroom2);
            this.mushroom3 = new WrappedModelPart("mushroom3", bCow);
            this.theBody.addChild(this.mushroom3);
            this.mushroom4 = new WrappedModelPart("mushroom4", bCow);
            this.theBody.addChild(this.mushroom4);

//            this.mushroom5 = new WrappedModelPart("mushroom5", bCow);
//            this.theNeck.addChild(this.mushroom5);
//            this.mushroom6 = new WrappedModelPart("mushroom6", bCow);
//            this.theNeck.addChild(this.mushroom6);
//            this.mushroom7 = new WrappedModelPart("mushroom7", bCow);
//            this.theNeck.addChild(this.mushroom7);
//
//            this.mushroom8 = new WrappedModelPart("mushroom8", bCow);
//            this.theHead.addChild(this.mushroom8);
//            this.mushroom9 = new WrappedModelPart("mushroom9", bCow);
//            this.theHead.addChild(this.mushroom9);
//
//            this.mushroom10 = new WrappedModelPart("mushroom10", bCow);
//            this.theHornNub.addChild(this.mushroom10);
//            this.mushroom11 = new WrappedModelPart("mushroom11", bCow);
//            this.theHornNub.addChild(this.mushroom11);
        }

    }

    private void resetCubes() {

        this.bodyNormal.hide();
        this.bodyHairy.hide();

        this.neckFemale.hide();
        this.neckMale.hide();

        this.headFemale.hide();
        this.noseFemale.hide();

        this.headMale.hide();
        this.noseMale.hide();

        this.headNubXSmall.hide();
        this.headNubSmall.hide();
        this.headNubMedium.hide();
        this.headNubLarge.hide();
        this.headNubXLarge.hide();
        this.headHair.hide();

        for (WrappedModelPart part : this.theEarLeft.children) {
            part.hide();
        }
        for (WrappedModelPart part : this.theEarRight.children) {
            part.hide();
        }

        for (WrappedModelPart part : this.theLegFrontLeft.children) {
            part.hide();
        }
        for (WrappedModelPart part : this.theLegFrontRight.children) {
            part.hide();
        }
        for (WrappedModelPart part : this.theLegBackLeft.children) {
            part.hide();
        }
        for (WrappedModelPart part : this.theLegBackRight.children) {
            part.hide();
        }
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        CowModelData cowModelData = getCowModelData();
        CowPhenotype cow = (CowPhenotype) cowModelData.phenotype;

        resetCubes();
        if (cow != null) {
            Map<String, List<Float>> mapOfScale = new HashMap<>();

            if (cow.isFemale) {
                this.neckFemale.show();
                this.headFemale.show();
                this.noseFemale.show();
            } else {
                this.neckMale.show();
                this.headMale.show();
                this.noseMale.show();
            }

            switch (cow.hornNubLength) {
                default:
                case 0:
                    this.headNubXSmall.show();
                    break;
                case 1 :
                    this.headNubSmall.show();
                    break;
                case 2:
                    this.headNubMedium.show();
                    break;
                case 3:
                    this.headNubLarge.show();
                    break;
                case 4:
                    this.headNubXLarge.show();
                    break;
            }

            /**
             *      Horns
             */
            List<Float> scalingsForHorn = ModelHelper.createScalings(cow.hornScale,0.0F, 0.0F, 0.0F);
            mapOfScale.put("bHornL", scalingsForHorn);
            mapOfScale.put("bHornR", ModelHelper.mirrorX(scalingsForHorn));

            /**
             *      Ears
             */
            switch ((int) cow.earSize) {
                case 0, 1 -> {
                    this.earXShortL.show();
                    this.earXShortR.show();
                }
                case 2, 3 -> {
                    this.earShortL.show();
                    this.earShortR.show();
                }
                case 4, 5 -> {
                    this.earMediumL.show();
                    this.earMediumR.show();
                }
                case 6, 7 -> {
                    this.earLongL.show();
                    this.earLongR.show();
                }
                default -> {
                    this.earXLongL.show();
                    this.earXLongR.show();
                }
            }

            if (cow.hairy) {
                this.bodyHairy.show();
                this.headHair.show();
            } else {
                this.bodyNormal.show();
            }

            if (cow.dwarf) {
                this.legFrontLeftDwarf.show();
                this.legFrontRightDwarf.show();
                this.legBackLeftDwarf.show();
                this.legBackRightDwarf.show();
            } else {
                this.legFrontLeft.show();
                this.legFrontRight.show();
                this.legBackLeft.show();
                this.legBackRight.show();
            }

            poseStack.pushPose();

//            float d = 0.0F;
//            if (!cowModelData.sleeping) {
//                if (dwarf) {
//                    d = 0.2F * (1.0F - age);
//                } else {
//                    d = 0.3F * (1.0F - age);
//                }
//            } else {
//                babyScale = 1.0F;
//            }

            float finalCowSize = ((((cow.isFemale ? 1.8F : 2.0F) * cowModelData.growthAmount) + 1.0F) / 3.0F) * cowModelData.size;
            poseStack.scale(finalCowSize + (finalCowSize * cow.bodyWidth * cowModelData.growthAmount), finalCowSize, finalCowSize + (finalCowSize * cow.bodyLength * cowModelData.growthAmount));
            poseStack.translate(0.0F, -1.5F + 1.5F / finalCowSize, 0.0F);

            gaRender(this.theCow, mapOfScale, poseStack, vertexConsumer, packedLightIn, packedOverlayIn, red, green, blue, alpha);

            poseStack.popPose();
        }
    }

        private void renderHorns(float hornScale, List<String> unrenderedModels, PoseStack matrixStackIn, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        Map<String, List<Float>> mapOfScale = new HashMap<>();
        List<Float> scalingsForHorn = ModelHelper.createScalings(hornScale, hornScale, hornScale,0.0F, 0.0F, 0.0F);
        mapOfScale.put("HornL0", scalingsForHorn);
        mapOfScale.put("HornR0", ModelHelper.mirrorX(scalingsForHorn));
//        this.hornGranparent.render(matrixStackIn, bufferIn , mapOfScale, unrenderedModels, false, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    }

    @Override
    public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.currentAnimal = entityIn.getId();
        CowModelData cowModelData = getCreateCowModelData(entityIn);
        CowPhenotype cow = cowModelData.getPhenotype();

        if (cow!=null) {
            float headRotationAngleX = (entityIn).getHeadRotationAngleX(ageInTicks);
            setHornRotations(cowModelData);

            this.theCow.setY(cow.dwarf ? 16.0F : 14.0F);
            this.theEarLeft.setZRot(cow.averageEars ? 1.1F + (cow.earSize / 6.25F) : cow.earFloppiness);
            this.theEarRight.setZRot(-(cow.averageEars ? 1.1F + (cow.earSize / 6.25F) : cow.earFloppiness));
            this.theHead.setXRot(0.5F);
            this.jaw.setXRot(this.theNeck.getXRot() <= 0 ? -0.3F : -0.3F + (headRotationAngleX / 2.0F));

            this.tailBase.setZRot(Mth.cos(limbSwing * 0.6662F) * 0.4F * limbSwingAmount);
            this.tailMiddle.setZRot(Mth.cos(limbSwing * 0.6662F) * 0.4F * limbSwingAmount);
            this.tailEnd.setZRot(Mth.cos(limbSwing * 0.6662F) * 0.45F * limbSwingAmount);
            this.tailBrush.setZRot(Mth.cos(limbSwing * 0.6662F) * 0.6F * limbSwingAmount);

            this.tailBase.setXRot(0.4F);
            this.tailMiddle.setXRot(-0.2F);
            this.tailEnd.setXRot(-0.2F);
            this.tailBrush.setXRot(0.0F);

            if (cow.mushrooms != null) {

                setMushroomPlacement(this.mushroom0, -6, -3, cow.mushrooms[0]);
                setMushroomPlacement(this.mushroom1, 2, 1, cow.mushrooms[1]);
                setMushroomPlacement(this.mushroom2, -6, 5, cow.mushrooms[2]);
                setMushroomPlacement(this.mushroom3, 2, 9, cow.mushrooms[3]);
                setMushroomPlacement(this.mushroom4, -2, 12, cow.mushrooms[4]);

                //body
                //[-6 to 6 , -3 to 17]
//                this.mushroom0.setPos(/*((cow.mushrooms[0]*0.25F)) */-6.0F, 0.0F, (int)(cow.mushrooms[0] - (cow.mushrooms[0]*0.25F)) -3.0F);
//                this.mushroom1.setPos(((cow.mushrooms[1]*0.25F) + 2.0F), -2.0F,  - 3.0F);
//                this.mushroom2.setPos(((cow.mushrooms[2]*0.25F) - 6.0F), -4.0F, (cow.mushrooms[2] - (cow.mushrooms[2]*0.25F)) - 3.0F);
//                this.mushroom3.setPos(((cow.mushrooms[3]*0.25F) + 2.0F), -6.0F, (cow.mushrooms[3] - (cow.mushrooms[3]*0.25F)) - 3.0F);
//                this.mushroom4.setPos(((cow.mushrooms[4]*0.25F) - 2.0F), -8.0F, (cow.mushrooms[4] - (cow.mushrooms[4]*0.25F)) + 12.0F);

//                //neck
//                //[-3 to 3 , -4 to -10]
//                this.mushroom5.setPos(0.0F, 0.0F, -4.0F);
//                this.mushroom6.setPos(0.0F, 0.0F, -7.0F);
//                this.mushroom7.setPos(0.0F, 0.0F, -10.0F);
//
//                //head
//                //[-4 to 4 , -8 to -10]
//                this.mushroom8.setPos(0.0F, 0.0F, -7.0F);
//                this.mushroom9.setPos(0.0F, 0.0F, -11.0F);
//
//                //hornnub
//                //[-2 to 2 , -6 to -7]
//                this.mushroom10.setPos(0.0F, -0.5F, -6.0F);
//                this.mushroom11.setPos(0.0F, -0.5F, -7.0F);
            }

            if (!cowModelData.sleeping) {
                float headYaw = netHeadYaw - Math.round(netHeadYaw / 180) * 180;
                this.theHead.setYRot(headYaw * 0.017453292F * 0.4F);
                this.theNeck.setYRot(headYaw * 0.017453292F * 0.6F);
                this.theNeck.setXRot(headPitch * 0.017453292F);


                this.theLegFrontLeft.setXRot(Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount);
                this.theLegFrontRight.setXRot(Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount);
                this.theLegBackLeft.setXRot(Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount);
                this.theLegBackRight.setXRot(Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount);
            }

//            this.head.yRot = headYaw * 0.017453292F * 0.4F;
//
//            this.neck.xRot = (headPitch * 0.017453292F);
//            this.neck.yRot = headYaw * 0.017453292F * 0.6F;
//
//            this.humpXLarge.xRot = (this.neck.xRot / 2.5F) - 0.2F;
//            this.humpXLarge.yRot = this.neck.yRot / 2.5F;

//            this.neck.xRot = this.headRotationAngleX;
//            this.head.xRot = 0.5F;
//            this.mouth.xRot = this.neck.xRot <= 0 ? -0.3F : -0.3F + (this.headRotationAngleX / 2.0F);

        }
    }

    private void setMushroomPlacement(WrappedModelPart mushroom, int x, int z, int placement) {
        mushroom.show();
        switch (placement) {
            case 1:
                mushroom.hide();
                break;
            case 2:
                x=x+2;
                break;
            case 3:
                mushroom.hide();
                break;
            case 4:
                z=z+1;
                break;
            case 5:
                mushroom.hide();
                z=z+1;
                break;
            case 6:
                x=x+2;
                z=z+1;
                break;
            case 7:
                mushroom.hide();
                z=z+1;
                break;
            case 8:
                z=z+2;
                break;
            case 9:
                mushroom.hide();
                z=z+2;
                break;
            case 10:
                x=x+2;
                z=z+2;
                break;
            case 11:
                mushroom.hide();
                z=z+2;
                break;
            case 12:
                z=z+3;
                break;
            case 13:
                mushroom.hide();
                z=z+3;
                break;
            case 14:
                x=x+2;
                z=z+3;
                break;
            case 15:
                mushroom.hide();
                z=z+3;
                break;

        }
        mushroom.setPos(x, 0.0F, z);
    }

    private void setHornRotations(CowModelData cowModelData) {
        CowPhenotype cow = cowModelData.getPhenotype();
        HornType horns = cow.hornType;

        Float[] hornGrowthL = {-1.0F, -2.0F, -2.0F, -2.0F, -2.0F, -1.8F, -1.6F, -1.4F, -1.2F, -1.0F};
        Float[] hornGrowthR = {-1.0F, -2.0F, -2.0F, -2.0F, -2.0F, -1.8F, -1.6F, -1.4F, -1.2F, -1.0F};

        int lengthL = cow.leftHornLength;
        int lengthR = cow.rightHornLength;

        if (cowModelData.growthAmount < 1.0F) {
            //this grows the horns from nothing to their adult size
            float age = (float)cowModelData.growthAmount/(cowModelData.growthAmount * 1.2857F);
            lengthL = lengthL + ((int)((12-lengthL) * (1.0F-(age))));
            lengthR = lengthR + ((int)((12-lengthR) * (1.0F-(age))));
        }

        if (horns != HornType.POLLED) {
            switch (lengthL) {
                case 0 -> this.hornL0.setPos(0.0F, 0.0F, 0.0F);
                case 1 -> this.hornL1.setPos(0.0F, 0.0F, 0.0F);
                case 2 -> this.hornL2.setPos(0.0F, 0.0F, 0.0F);
                case 3 -> this.hornL3.setPos(0.0F, 0.0F, 0.0F);
                case 4 -> this.hornL4.setPos(0.0F, 0.0F, 0.0F);
                case 5 -> this.hornL5.setPos(0.0F, 0.0F, 0.0F);
                case 6 -> this.hornL6.setPos(0.0F, 0.0F, 0.0F);
                case 7 -> this.hornL7.setPos(0.0F, 0.0F, 0.0F);
                case 8 -> this.hornL8.setPos(0.0F, 0.0F, 0.0F);
                case 9 -> this.hornL9.setPos(0.0F, 0.0F, 0.0F);
            }
        }

            this.theHornLeft.setYAndSeen(0.0F, cow.hornType != HornType.POLLED);
            this.hornL0.setYAndSeen(0.0F, cow.hornType != HornType.POLLED && lengthL <= 0);
            this.hornL1.setYAndSeen(lengthL <= 0 ? -2.0F : 0.0F, cow.hornType != HornType.POLLED && lengthL <= 1);
            this.hornL2.setYAndSeen(lengthL <= 1 ? -2.0F : 0.0F, cow.hornType != HornType.POLLED && lengthL <= 2);
            this.hornL3.setYAndSeen(lengthL <= 2 ? -2.0F : 0.0F, cow.hornType != HornType.POLLED && lengthL <= 3);
            this.hornL4.setYAndSeen(lengthL <= 3 ? -2.0F : 0.0F, cow.hornType != HornType.POLLED && lengthL <= 4);
            this.hornL5.setYAndSeen(lengthL <= 4 ? -1.8F : 0.0F, cow.hornType != HornType.POLLED && lengthL <= 5);
            this.hornL6.setYAndSeen(lengthL <= 5 ? -1.6F : 0.0F, cow.hornType != HornType.POLLED && lengthL <= 6);
            this.hornL7.setYAndSeen(lengthL <= 6 ? -1.4F : 0.0F, cow.hornType != HornType.POLLED && lengthL <= 7);
            this.hornL8.setYAndSeen(lengthL <= 7 ? -1.2F : 0.0F, cow.hornType != HornType.POLLED && lengthL <= 8);
            this.hornL9.setYAndSeen(lengthL <= 8 ? -1.0F : 0.0F, cow.hornType != HornType.POLLED && lengthL <= 9);

            this.theHornRight.setYAndSeen(0.0F, cow.hornType != HornType.POLLED);
            this.hornR0.setYAndSeen(0.0F, cow.hornType != HornType.POLLED && lengthR <= 0);
            this.hornR1.setYAndSeen(lengthR <= 0 ? -2.0F : 0.0F, cow.hornType != HornType.POLLED && lengthR <= 1);
            this.hornR2.setYAndSeen(lengthR <= 1 ? -2.0F : 0.0F, cow.hornType != HornType.POLLED && lengthR <= 2);
            this.hornR3.setYAndSeen(lengthR <= 2 ? -2.0F : 0.0F, cow.hornType != HornType.POLLED && lengthR <= 3);
            this.hornR4.setYAndSeen(lengthR <= 3 ? -2.0F : 0.0F, cow.hornType != HornType.POLLED && lengthR <= 4);
            this.hornR5.setYAndSeen(lengthR <= 4 ? -1.8F : 0.0F, cow.hornType != HornType.POLLED && lengthR <= 5);
            this.hornR6.setYAndSeen(lengthR <= 5 ? -1.6F : 0.0F, cow.hornType != HornType.POLLED && lengthR <= 6);
            this.hornR7.setYAndSeen(lengthR <= 6 ? -1.4F : 0.0F, cow.hornType != HornType.POLLED && lengthR <= 7);
            this.hornR8.setYAndSeen(lengthR <= 7 ? -1.2F : 0.0F, cow.hornType != HornType.POLLED && lengthR <= 8);
            this.hornR9.setYAndSeen(lengthR <= 8 ? -1.0F : 0.0F, cow.hornType != HornType.POLLED && lengthR <= 9);

        this.theHornNub.setY(horns.getPlacement());

        //horn shape controllers
        if (horns != HornType.POLLED) {

            float[] hornCalculationsZ = cow.hornGeneticsZ;
            float[] hornCalculationsX = cow.hornGeneticsX;
            float[] hornCalculationsY = cow.hornGeneticsY;

            for (int z = 0; z <= 9; z++) {
                hornGrowthL[z] = lengthL <= z ? 1.0F : 0.0F;
                hornGrowthR[z] = lengthR <= z ? 1.0F : 0.0F;
            }

            this.theHornLeft.setXRot((float)Math.PI * 0.1F);
            this.theHornRight.setXRot((float)Math.PI * 0.1F);

            float flip = 1F;

            this.hornL0.setRotation(hornCalculationsX[0], -hornCalculationsY[0], hornCalculationsZ[0]);
            this.hornR0.setRotation(hornCalculationsX[0], hornCalculationsY[0], -hornCalculationsZ[0]);

            this.hornL1.setRotation(-hornCalculationsX[1] * hornGrowthL[1],hornCalculationsY[1] * hornGrowthL[1],hornCalculationsZ[1] * hornGrowthL[1]);
            this.hornL2.setRotation(-hornCalculationsX[2] * hornGrowthL[2],hornCalculationsY[2] * hornGrowthL[2],hornCalculationsZ[2] * hornGrowthL[2]);
            this.hornL3.setRotation(-hornCalculationsX[3] * hornGrowthL[3],hornCalculationsY[3] * hornGrowthL[3],hornCalculationsZ[3] * hornGrowthL[3]);
            this.hornL4.setRotation(-hornCalculationsX[4] * hornGrowthL[4],hornCalculationsY[4] * hornGrowthL[4],hornCalculationsZ[4] * hornGrowthL[4]);
            this.hornL5.setRotation(-hornCalculationsX[5] * hornGrowthL[5],hornCalculationsY[5] * hornGrowthL[5],hornCalculationsZ[5] * hornGrowthL[5]);
            this.hornL6.setRotation(-hornCalculationsX[6] * hornGrowthL[6],hornCalculationsY[6] * hornGrowthL[6],hornCalculationsZ[6] * hornGrowthL[6]);
            this.hornL7.setRotation(-hornCalculationsX[7] * hornGrowthL[7],hornCalculationsY[7] * hornGrowthL[7],hornCalculationsZ[7] * hornGrowthL[7]);
            this.hornL8.setRotation(-hornCalculationsX[8] * hornGrowthL[8],hornCalculationsY[8] * hornGrowthL[8],hornCalculationsZ[8] * hornGrowthL[8]);
            this.hornL9.setRotation(-hornCalculationsX[9] * hornGrowthL[9],hornCalculationsY[9] * hornGrowthL[9],hornCalculationsZ[9] * hornGrowthL[9]);

            this.hornR1.setRotation(-hornCalculationsX[1] * hornGrowthR[1],-hornCalculationsY[1] * hornGrowthR[1],-hornCalculationsZ[1] * hornGrowthR[1]);
            this.hornR2.setRotation(-hornCalculationsX[2] * hornGrowthR[2],-hornCalculationsY[2] * hornGrowthR[2],-hornCalculationsZ[2] * hornGrowthR[2]);
            this.hornR3.setRotation(-hornCalculationsX[3] * hornGrowthR[3],-hornCalculationsY[3] * hornGrowthR[3],-hornCalculationsZ[3] * hornGrowthR[3]);
            this.hornR4.setRotation(-hornCalculationsX[4] * hornGrowthR[4],-hornCalculationsY[4] * hornGrowthR[4],-hornCalculationsZ[4] * hornGrowthR[4]);
            this.hornR5.setRotation(-hornCalculationsX[5] * hornGrowthR[5],-hornCalculationsY[5] * hornGrowthR[5],-hornCalculationsZ[5] * hornGrowthR[5]);
            this.hornR6.setRotation(-hornCalculationsX[6] * hornGrowthR[6],-hornCalculationsY[6] * hornGrowthR[6],-hornCalculationsZ[6] * hornGrowthR[6]);
            this.hornR7.setRotation(-hornCalculationsX[7] * hornGrowthR[7],-hornCalculationsY[7] * hornGrowthR[7],-hornCalculationsZ[7] * hornGrowthR[7]);
            this.hornR8.setRotation(-hornCalculationsX[8] * hornGrowthR[8],-hornCalculationsY[8] * hornGrowthR[8],-hornCalculationsZ[8] * hornGrowthR[8]);
            this.hornR9.setRotation(-hornCalculationsX[9] * hornGrowthR[9],-hornCalculationsY[9] * hornGrowthR[9],-hornCalculationsZ[9] * hornGrowthR[9]);
        }
    }

    private class CowModelData extends AnimalModelData {
        public CowPhenotype getPhenotype() {
            return (CowPhenotype) this.phenotype;
        }
    }

    private CowModelData getCowModelData() {
        return (CowModelData) getAnimalModelData();
    }

    private CowModelData getCreateCowModelData(T enhancedCow) {
        return (CowModelData) getCreateAnimalModelData(enhancedCow);
    }

    @Override
    protected void setInitialModelData(T enhancedCow) {
        CowModelData cowModelData = new CowModelData();
        setBaseInitialModelData(cowModelData, enhancedCow);
    }

    @Override
    protected CowPhenotype createPhenotype(T enhancedAnimal) {
        char[] uuid = (enhancedAnimal.getMooshroomUUID().isEmpty() || enhancedAnimal.getMooshroomUUID().equals("0")) ? enhancedAnimal.getStringUUID().toCharArray() : enhancedAnimal.getMooshroomUUID().toCharArray();
        return new CowPhenotype(enhancedAnimal.getSharedGenes().getAutosomalGenes(), uuid, enhancedAnimal.getOrSetIsFemale(), enhancedAnimal instanceof EnhancedMooshroom || enhancedAnimal instanceof EnhancedMoobloom);
    }

    protected class CowPhenotype implements Phenotype {
        private boolean isFemale;
        private HornType hornType;
        private float[] hornGeneticsX;
        private float[] hornGeneticsY;
        private float[] hornGeneticsZ;
        private float hornScale;
        private boolean dwarf;
        private float bodyWidth;
        private float bodyLength;
        private int hump;
        private float humpPlacement;
        private float hornBaseHeight;
        private int leftHornLength = 9;
        private int rightHornLength = 9;
        private int hornNubLength;
        private boolean averageEars;
        private float earSize;
        private float earFloppiness;
        private boolean hairy = false;
        private int[] mushrooms;

        CowPhenotype(int[] gene, char[] uuidArray, boolean isFemale, boolean isMooshroom) {
            super();
            this.isFemale = isFemale;
            this.hornType = calculateHornType(gene, isFemale);
            this.hornScale = getHornScale(gene, this.hornType);
            this.setHornLengths(gene, uuidArray[4], this.hornType);
            this.setHornGenetics(gene, this.hornType);
            this.dwarf = gene[26] == 1 || gene[27] == 1;

//            float bodyWidth = isFemale ? -0.175F : 0.0F;
            float bodyWidth = isFemale ? -0.175F : 0.0F;
            float bodyLength = 0.0F;
            int hump = 0;

//            float genderModifier = isFemale ? 0.01725F : 0.0825F;
            float genderModifier = isFemale ? 0.045F : 0.0825F;
            for (int i = 1; i < gene[54]; i++) {
                bodyWidth = bodyWidth + genderModifier;
            }
            for (int i = 1; i < gene[55]; i++) {
                bodyWidth = bodyWidth + genderModifier;
            }

            if (bodyWidth >= 0.0F) {
                bodyLength = bodyWidth;
            }

            for (int i = 1; i < gene[38]; i++) {
                hump++;
            }

            for (int i = 1; i < gene[39]; i++) {
                hump++;
            }

            if (!(gene[48] == 1 || gene[49] == 1)){
                this.hairy = gene[50] + gene[51] + gene[52] + gene[53] > 5;
            }

            this.bodyWidth = bodyWidth;
            this.bodyLength = bodyLength;
            this.hump = (int) (hump * (isFemale ? 0.6 : 1));
            this.humpPlacement = setHumpPlacement(gene);
            this.hornBaseHeight = setHornBaseHeight(gene);
            this.hornNubLength = setHornNubLength(gene);
            this.setEarRotations(gene);

            if (isMooshroom) {
                this.mushrooms = new int[]{
                        getNum(uuidArray[20]),
                        getNum(uuidArray[21]),
                        getNum(uuidArray[22]),
                        getNum(uuidArray[24]),
                        getNum(uuidArray[25]),
                        getNum(uuidArray[26]),
                        getNum(uuidArray[27]),
                        getNum(uuidArray[28]),
                        getNum(uuidArray[29]),
                        getNum(uuidArray[30]),
                        getNum(uuidArray[31]),
                        getNum(uuidArray[32]),
                        getNum(uuidArray[33]),
                        getNum(uuidArray[34]),
                        getNum(uuidArray[35])
                };
            }
        }

        private int getNum(char charac) {
            if (Character.isDigit(charac)) {
                return charac-48;
            }
            return switch (charac) {
                case 'a' -> 10;
                case 'b' -> 11;
                case 'c' -> 12;
                case 'd' -> 13;
                case 'e' -> 14;
                case 'f' -> 15;
                default -> throw new IllegalStateException("Unexpected value: " + charac);
            };
        }

        private void setHornGenetics(int[] genes, HornType horns) {
            Float[] hornGenetics = {1.5F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F};

            if (horns != HornType.POLLED) {
                int a = 100;
                for (int i = 1; i <= 9; i++) {
                    hornGenetics[i] = (float) (((genes[a] % 10) + (genes[a + 1] % 10)) - 3) / -30;
                    hornGenetics[10 + i] = (float) ((((genes[a] / 10) % 10) + ((genes[a + 1] / 10) % 10)) - 6) / 30;
                    a = a + 2;
                }

                hornGenetics[29] = (float) ((genes[94] % 10) + ((genes[95]) % 10)) / 18.0F;
                hornGenetics[28] = (float) (((genes[94] / 10) % 10) + ((genes[95] / 10) % 10)) / 18.0F;
                hornGenetics[27] = (float) (((genes[94] / 100) % 10) + ((genes[95] / 100) % 10)) / 18.0F;
                hornGenetics[26] = (float) (((genes[94] / 1000) % 10) + ((genes[95] / 1000) % 10)) / 18.0F;
                hornGenetics[25] = (float) (((genes[94] / 10000) % 10) + ((genes[95] / 10000) % 10)) / 18.0F;
                hornGenetics[24] = (float) ((genes[94] / 100000) + (genes[95] / 100000)) / 18.0F;
                hornGenetics[23] = (float) ((genes[96] % 10) + ((genes[97]) % 10)) / 18.0F;
                hornGenetics[22] = (float) (((genes[96] / 10) % 10) + ((genes[97] / 10) % 10)) / 18.0F;
                hornGenetics[21] = (float) (((genes[96] / 100) % 10) + ((genes[97] / 100) % 10)) / 18.0F;


                float averageMod = 1 - ((hornGenetics[29] + hornGenetics[28] + hornGenetics[27] + hornGenetics[26] + hornGenetics[25] + hornGenetics[24] + hornGenetics[23] + hornGenetics[22] + hornGenetics[21]) / 9);
                for (int i = 21; i <= 29; i++) {
                    hornGenetics[i] = hornGenetics[i] * (((float) ((genes[96] / 1000) + (genes[97] / 1000) - (averageMod * 5))) / 8.0F);
                }

                // if b is lower horns curl more near ends
                int b = ((genes[84] - 1) + (genes[85] - 1)) / 6;

//             [straight] , [ 1 2 3 ] , [ 4 5 6 ], [ 7 8 9 ]
                float f = (1 + (float) (Math.abs((genes[92] % 10) - 3) + Math.abs((genes[92] % 10) - 3)) / 36);
                for (int i = 1; i <= 29; i++) {
                    if (i % 10 <= 3) {
                        // horn 1, 2, 3 grab allele section [ O x x x ]
                        int c = (genes[92] / 1000) + (genes[93] / 1000);
                        hornGenetics[i] = hornGenetics[i] * ((((c) * ((1 + (float) (Math.abs((genes[92] % 10) - 3) + Math.abs((genes[92] % 10) - 3)) / 36)))) / 24.0F);
                    } else if (i % 10 <= 6) {
                        // horn 4, 5, 6 grab allele section [ x O x x ]
                        int d = ((genes[92] / 100) % 10) + ((genes[93] / 100) % 10);
                        hornGenetics[i] = (1.3F - (b / 3.0F)) * hornGenetics[i] * ((d * f) / 24.0F);
                    } else {
                        // horn 7, 8, 9 grab allele section [ x x O x ]
                        int e = ((genes[92] / 10) % 10) + ((genes[93] / 10) % 10);
                        hornGenetics[i] = (1.5F - (b / 2.0F)) * hornGenetics[i] * ((e * f) / 24.0F);
                    }
                }

                // if b is lower horns stick more out the side of the head
                hornGenetics[10] = (b * (((genes[92] % 10) - 3.0F) + ((genes[92] % 10) - 3.0F)) / -9.0F) * 0.5F;
                hornGenetics[20] = hornGenetics[20] * (1 - b);
            }

            this.hornGeneticsZ = new float[]{hornGenetics[0], hornGenetics[1], hornGenetics[2], hornGenetics[3], hornGenetics[4], hornGenetics[5], hornGenetics[6], hornGenetics[7], hornGenetics[8], hornGenetics[9]};
            this.hornGeneticsX = new float[]{hornGenetics[10], hornGenetics[11], hornGenetics[12], hornGenetics[13], hornGenetics[14], hornGenetics[15], hornGenetics[16], hornGenetics[17], hornGenetics[18], hornGenetics[19]};
            this.hornGeneticsY = new float[]{hornGenetics[20], hornGenetics[21], hornGenetics[22], hornGenetics[23], hornGenetics[24], hornGenetics[25], hornGenetics[26], hornGenetics[27], hornGenetics[28], hornGenetics[29]};

        }

        private HornType calculateHornType(int[] gene, boolean isFemale) {
            if (gene != null) {
                if (gene[12] == 1 || gene[13] == 1) {
                    //should be polled unless...
                    //african horn genes
                    if (gene[76] == 2 && gene[77] == 2) {
                        //polled
                        if (gene[78] == 1 && gene[79] == 1) {
                            //scured
                            return HornType.SCURED;
                        } else if (gene[78] == 1 || gene[79] == 1) {
                            //sex determined scured
                            return isFemale ? HornType.POLLED : HornType.SCURED;
                        } else {
                            //polled
                            return HornType.POLLED;
                        }
                    } else if (isFemale && gene[76] != gene[77]) {
                        return (gene[78] == 1 && gene[79] == 1) ? HornType.SCURED : HornType.POLLED;
                    }
                }
            }

            return HornType.HORNED;
        }

        private float getHornScale(int[] genes, HornType horns) {
            //size [0.75 to 2.5]
            float hornScaleCalc = 1.0F;
            if (horns != HornType.POLLED) {
                if (horns == HornType.HORNED) {
                    //normal horns
                    for (int i = 86; i <= 89; i++) {
                        if (genes[i] == 2) {
                            hornScaleCalc = hornScaleCalc * 1.15F;
                        }
                    }
                    if (genes[90] == 2) {
                        hornScaleCalc = hornScaleCalc * 1.25F;
                    }
                    if (genes[91] == 2) {
                        hornScaleCalc = hornScaleCalc * 1.25F;
                    }
                    if (genes[80] >= 3) {
                        hornScaleCalc = hornScaleCalc * 0.95F;
                    }
                    if (genes[81] >= 3) {
                        hornScaleCalc = hornScaleCalc * 0.95F;
                    }
                } else {
                    //scurs
                    hornScaleCalc = (hornScaleCalc + 0.75F) * 0.5F;
                }
                hornScaleCalc = hornScaleCalc * (((2.0F / (float) (genes[122] + genes[123])) + 1.5F) / 2.5F);
            }

            return hornScaleCalc;
        }

        private float setHornBaseHeight(int[] gene) {
            float hbh = -1.0F;

            if (gene[70] == 2) {
                hbh = hbh + 0.05F;
            }
            if (gene[71] == 2) {
                hbh = hbh + 0.05F;
            }
            if (gene[72] != 1) {
                hbh = hbh + 0.1F;
            }
            if (gene[73] != 1) {
                hbh = hbh + 0.1F;
            }
            if (gene[72] == 3 && gene[73] == 3) {
                hbh = hbh + 0.1F;
            }
            if (gene[74] == 1) {
                hbh = hbh * 0.75F;
            }
            if (gene[75] == 1) {
                hbh = hbh * 0.75F;
            }

            return hbh;
        }

        private void setHornLengths(int[] gene, char uuid, HornType horns) {
            if (horns != HornType.POLLED) {
                int lengthL = 2;

                if (gene[80] != 2 || gene[81] != 2) {
                    if (gene[80] >= 3 || gene[81] >= 3) {
                        if (gene[80] == 4 && gene[81] == 4) {
                            lengthL = -1;
                        } else {
                            lengthL = 1;
                        }
                    } else {
                        lengthL = 0;
                    }
                }

                int lengthR = lengthL;

                if (horns == HornType.SCURED) {
                    lengthL = lengthL + 5;
                    lengthR = lengthR + 5;

                    if (Character.isDigit(uuid)) {
                        if ((uuid - 48) <= 3) {
                            //shorten left horn
                            lengthL = lengthL + (uuid - 48);
                        } else if ((uuid - 48) <= 7) {
                            //shorten right horn
                            lengthR = lengthR + (uuid - 52);
                        } else {
                            // shorten evenly
                            lengthL = lengthL + (uuid - 55);
                            lengthR = lengthL;
                        }
                    } else {
                        char a = uuid;
                        switch (a) {
                            case 'a':
                                lengthL = lengthL + 1;
                                lengthR = lengthR + 2;
                            case 'b':
                                lengthL = lengthL + 2;
                                lengthR = lengthR + 1;
                            case 'c':
                                lengthL = lengthL + 1;
                                lengthR = lengthR + 3;
                            case 'd':
                                lengthL = lengthL + 3;
                                lengthR = lengthR + 1;
                            case 'e':
                                lengthL = lengthL + 2;
                                lengthR = lengthR + 3;
                            case 'f':
                                lengthL = lengthL + 3;
                                lengthR = lengthR + 2;
                        }
                    }
                } else {
                    //if horns are not scurred shorten horns if they are extra thicc
                    if (gene[82] == 2) {
                        lengthL = lengthL + 1;
                    }
                    if (gene[83] == 2) {
                        lengthL = lengthL + 1;
                    }

                    if (gene[88] == 2) {
                        lengthL = lengthL + 1;
                    }
                    if (gene[89] == 2) {
                        lengthL = lengthL + 1;
                    }
                    if (gene[90] == 2 || gene[91] == 2) {
                        lengthL = lengthL + 2;
                    }
                    lengthR = lengthL;
                }

                if (lengthL > 9) {
                    lengthL = 9;
                }
                if (lengthR > 9) {
                    lengthR = 9;
                }

                this.leftHornLength = lengthL;
                this.rightHornLength = lengthR;
            }
        }

        private int setHornNubLength(int[] gene) {
            int hornNubLength = 4;
            if (gene[70] == 1 || gene[71] == 1){
                hornNubLength--;
            }

            if (gene[72] == 1 || gene[73] == 1){
                hornNubLength = hornNubLength - 2;
            }else if (gene[72] == 2 || gene[73] == 2){
                hornNubLength--;
            }

            if (gene[74] == 1 || gene[75] == 1) {
                hornNubLength++;
            }
            return hornNubLength;
        }

        private float setHumpPlacement(int[] gene) {
            float humpCalc = 0.0F;
            if (gene[40] != 1 && gene[41] != 1) {
                for (int i = 1; i < gene[40]; i++) {
                    humpCalc = humpCalc - 0.5F;
                }
                for (int i = 1; i < gene[41]; i++) {
                    humpCalc = humpCalc - 0.5F;
                }
            }
            return humpCalc;
        }

        private void setEarRotations(int[] gene) {
            if (gene[46] == 2 && gene[47] == 2) {
                float earSize = 0;
                for (int i = 1; i < gene[42]; i++) {
                    earSize = earSize + 1.0F;
                }

                for (int i = 1; i < gene[43]; i++) {
                    earSize = earSize + 1.0F;
                }

                if (gene[44] == 1 || gene[45] == 1) {
                    earSize = earSize - earSize / 3.0F;
                }

                float floppiness = (earSize / 6.25F) + 0.2F;

                this.averageEars = true;
                this.earFloppiness = floppiness;
                this.earSize = earSize;

            } else {
                float floppiness = 0.9F;
                float earSize = 0;

                for (int i = 1; i < gene[42]; i++) {
                    floppiness = floppiness + 0.16F;
                    earSize = earSize + 1.0F;
                }

                for (int i = 1; i < gene[43]; i++) {
                    floppiness = floppiness + 0.16F;
                    earSize = earSize + 1.0F;
                }

                if (gene[44] == 1 || gene[45] == 1) {
                    floppiness = floppiness - floppiness / 3.0F;
                    earSize = earSize - earSize / 3.0F;
                }

                for (int i = 1; i < gene[46]; i++) {
                    floppiness = floppiness + 0.1F;
                }
                for (int i = 1; i < gene[47]; i++) {
                    floppiness = floppiness + 0.1F;
                }

                this.averageEars = false;
                this.earFloppiness = floppiness;
                this.earSize = earSize;
            }
        }

    }

//    private Map<Integer, CowModelData> cowModelDataCache = new HashMap<>();
//    private int clearCacheTimer = 0;
//    private float headRotationAngleX;
//
//    private final EnhancedRendererModelNew head;
//    private final EnhancedRendererModelNew noseMale;
//    private final EnhancedRendererModelNew noseFemale;
//    private final EnhancedRendererModelNew eyeLeft;
//    private final EnhancedRendererModelNew eyeRight;
//    private final EnhancedRendererModelNew mouth;
//    private final EnhancedRendererModelNew earSmallestL;
//    private final EnhancedRendererModelNew earSmallL;
//    private final EnhancedRendererModelNew earMediumL;
//    private final EnhancedRendererModelNew earLongL;
//    private final EnhancedRendererModelNew earLongestL;
//    private final EnhancedRendererModelNew earSmallestR;
//    private final EnhancedRendererModelNew earSmallR;
//    private final EnhancedRendererModelNew earMediumR;
//    private final EnhancedRendererModelNew earLongR;
//    private final EnhancedRendererModelNew earLongestR;
//    private final EnhancedRendererModelNew hornNub1;
//    private final EnhancedRendererModelNew hornNub2;
//    private final EnhancedRendererModelNew hornNub3;
//    private final EnhancedRendererModelNew hornNub4;
//    private final EnhancedRendererModelNew hornNub5;

//    private final EnhancedRendererModelNew neck;
//    private final EnhancedRendererModelNew body;
//    private final EnhancedRendererModelNew udder;
//    private final EnhancedRendererModelNew nipples;
//    private final EnhancedRendererModelNew humpXSmall;
//    private final EnhancedRendererModelNew humpSmall;
//    private final EnhancedRendererModelNew humpSmallish;
//    private final EnhancedRendererModelNew humpMedium;
//    private final EnhancedRendererModelNew humpLargeish;
//    private final EnhancedRendererModelNew humpLarge;
//    private final EnhancedRendererModelNew humpXLarge;
//    private final EnhancedRendererModelNew tail0;
//    private final EnhancedRendererModelNew tail1;
//    private final EnhancedRendererModelNew tail2;
//    private final EnhancedRendererModelNew tailBrush;
//    private final ModelPart leg1;
//    private final ModelPart leg2;
//    private final ModelPart leg3;
//    private final ModelPart leg4;
//    private final ModelPart shortLeg1;
//    private final ModelPart shortLeg2;
//    private final ModelPart shortLeg3;
//    private final ModelPart shortLeg4;
//    private final ModelPart mushroomBody1;
//    private final ModelPart mushroomBody2;
//    private final EnhancedRendererModelNew mushroomHead;
//    private final EnhancedRendererModelNew saddle;
//    private final EnhancedRendererModelNew saddleWestern;
//    private final EnhancedRendererModelNew saddleEnglish;
//    private final EnhancedRendererModelNew saddleHorn;
//    private final EnhancedRendererModelNew saddlePomel;
//    private final EnhancedRendererModelNew saddleSideL;
//    private final EnhancedRendererModelNew stirrup2DWideL;
//    private final EnhancedRendererModelNew stirrup2DWideR;
//    private final EnhancedRendererModelNew stirrup3DNarrowL;
//    private final EnhancedRendererModelNew stirrup3DNarrowR;
//    private final EnhancedRendererModelNew stirrup;
//    private final EnhancedRendererModelNew saddleSideR;
//    private final EnhancedRendererModelNew saddlePad;
//    private final EnhancedRendererModelNew headTassles;
//    private final EnhancedRendererModelNew collar;
//    private final EnhancedRendererModelNew bridle;
//    private final EnhancedRendererModelNew bridleMale;
//    private final EnhancedRendererModelNew bridleFemale;
//
//    private final List<EnhancedRendererModelNew> leftHorns = new ArrayList<>();
//    private final List<EnhancedRendererModelNew> rightHorns = new ArrayList<>();
//
//    private Integer currentCow = null;
//
//    public ModelEnhancedCow() {
//
//        this.texWidth = 256;
//        this.texHeight = 256;
//

//

//
//        this.hornNub1 = new EnhancedRendererModelNew(this, 44, 33, "HornNub1");
//        this.hornNub1.addBox(-2.0F, 0.0F, 0.0F, 4, 2, 2);
//        this.hornNub1.setPos(0.0F, 1.0F, -1.0F);
//
//        this.hornNub2 = new EnhancedRendererModelNew(this, 44, 33, "HornNub2");
//        this.hornNub2.addBox(-2.0F, 0.0F, 0.0F, 4, 3, 2);
//        this.hornNub2.setPos(0.0F, 1.0F, -1.0F);
//
//        this.hornNub3 = new EnhancedRendererModelNew(this, 44, 33, "HornNub3");
//        this.hornNub3.addBox(-2.0F, 0.0F, 0.0F, 4, 4, 2);
//        this.hornNub3.setPos(0.0F, 1.0F, -1.0F);
//
//        this.hornNub4 = new EnhancedRendererModelNew(this, 44, 33, "HornNub4");
//        this.hornNub4.addBox(-2.0F, 0.0F, 0.0F, 4, 5, 2);
//        this.hornNub4.setPos(0.0F, 1.0F, -1.0F);
//
//        this.hornNub5 = new EnhancedRendererModelNew(this, 44, 33, "HornNub5");
//        this.hornNub5.addBox(-2.0F, 0.0F, 0.0F, 4, 6, 2);
//        this.hornNub5.setPos(0.0F, 1.0F, -1.0F);
//
//        this.hornGranparent = new EnhancedRendererModelNew(this, 0, 0);
//        this.hornParent = new EnhancedRendererModelNew(this, 0, 0, "hornParent");
//        this.hornGranparent.addChild(hornParent);
//
//        this.hornL0 = new EnhancedRendererModelNew(this, 64, 29, "HornL0");
//        this.hornL0.addBox(-2.0F, -4.0F, -2.0F, 4, 4, 4, -1.0F);
//        leftHorns.add(hornL0);
//
//        this.hornL1 = new EnhancedRendererModelNew(this, 64, 37, "HornL1");
//        this.hornL1.addBox(-2.0F, -4.0F, -2.0F, 4, 4, 4, -1.001F);
//        leftHorns.add(hornL1);
//
//        this.hornL2 = new EnhancedRendererModelNew(this, 64, 45, "HornL2");
//        this.hornL2.addBox(-2.0F, -4.0F, -2.0F, 4, 4, 4, -1.002F);
//        leftHorns.add(hornL2);
//
//        this.hornL3 = new EnhancedRendererModelNew(this, 64, 53, "HornL3");
//        this.hornL3.addBox(-2.0F, -4.0F, -2.0F, 4, 4, 4, -1.003F);
//        leftHorns.add(hornL3);
//
//        this.hornL4 = new EnhancedRendererModelNew(this, 64, 61, "HornL4");
//        this.hornL4.addBox(-2.0F, -4.0F, -2.0F, 4, 4, 4, -1.004F);
//        leftHorns.add(hornL4);
//
//        this.hornL5 = new EnhancedRendererModelNew(this, 64, 61, "HornL5");
//        this.hornL5.addBox(-2.0F, -4.0F, -2.0F, 4, 4, 4, -1.1F);
//        leftHorns.add(hornL5);
//
//        this.hornL6 = new EnhancedRendererModelNew(this, 64, 61, "HornL6");
//        this.hornL6.addBox(-2.0F, -4.0F, -2.0F, 4, 4, 4, -1.2F);
//        leftHorns.add(hornL6);
//
//        this.hornL7 = new EnhancedRendererModelNew(this, 64, 61, "HornL7");
//        this.hornL7.addBox(-2.0F, -4.0F, -2.0F, 4, 4, 4, -1.3F);
//        leftHorns.add(hornL7);
//
//        this.hornL8 = new EnhancedRendererModelNew(this, 64, 61, "HornL8");
//        this.hornL8.addBox(-2.0F, -4.0F, -2.0F, 4, 4, 4, -1.4F);
//        leftHorns.add(hornL8);
//
//        this.hornL9 = new EnhancedRendererModelNew(this, 64, 61, "HornL9");
//        this.hornL9.addBox(-2.0F, -4.0F, -2.0F, 4, 4, 4, -1.5F);
//        leftHorns.add(hornL9);
//
//        this.hornR0 = new EnhancedRendererModelNew(this, 64, 29, "HornR0");
//        this.hornR0.addBox(-2.0F, -4.0F, -2.0F, 4, 4, 4, -1.0F);
//        rightHorns.add(hornR0);
//
//        this.hornR1 = new EnhancedRendererModelNew(this, 64, 37, "HornR1");
//        this.hornR1.addBox(-2.0F, -4.0F, -2.0F, 4, 4, 4, -1.001F);
//        rightHorns.add(hornR1);
//
//        this.hornR2 = new EnhancedRendererModelNew(this, 64, 45, "HornR2");
//        this.hornR2.addBox(-2.0F, -4.0F, -2.0F, 4, 4, 4, -1.002F);
//        rightHorns.add(hornR2);
//
//        this.hornR3 = new EnhancedRendererModelNew(this, 64, 53, "HornR3");
//        this.hornR3.addBox(-2.0F, -4.0F, -2.0F, 4, 4, 4, -1.003F);
//        rightHorns.add(hornR3);
//
//        this.hornR4 = new EnhancedRendererModelNew(this, 64, 61, "HornR4");
//        this.hornR4.addBox(-2.0F, -4.0F, -2.0F, 4, 4, 4, -1.004F);
//        rightHorns.add(hornR4);
//
//        this.hornR5 = new EnhancedRendererModelNew(this, 64, 61, "HornR5");
//        this.hornR5.addBox(-2.0F, -4.0F, -2.0F, 4, 4, 4, -1.1F);
//        rightHorns.add(hornR5);
//
//        this.hornR6 = new EnhancedRendererModelNew(this, 64, 61, "HornR6");
//        this.hornR6.addBox(-2.0F, -4.0F, -2.0F, 4, 4, 4, -1.2F);
//        rightHorns.add(hornR6);
//
//        this.hornR7 = new EnhancedRendererModelNew(this, 64, 61, "HornR7");
//        this.hornR7.addBox(-2.0F, -4.0F, -2.0F, 4, 4, 4, -1.3F);
//        rightHorns.add(hornR7);
//
//        this.hornR8 = new EnhancedRendererModelNew(this, 64, 61, "HornR8");
//        this.hornR8.addBox(-2.0F, -4.0F, -2.0F, 4, 4, 4, -1.4F);
//        rightHorns.add(hornR8);
//
//        this.hornR9 = new EnhancedRendererModelNew(this, 64, 61, "HornR9");
//        this.hornR9.addBox(-2.0F, -4.0F, -2.0F, 4, 4, 4, -1.5F);
//        rightHorns.add(hornR9);
//
//        this.body = new EnhancedRendererModelNew(this, 0, 0);
//        this.body.addBox(-6.0F, 0.0F, 0.0F, 12, 11, 22, 0.0F);
//        this.body.setPos(0.0F, 2.5F, -10.0F);
//
//        this.udder = new EnhancedRendererModelNew(this, 24, 67, "Udder");
//        this.udder.addBox(-2.0F, -2.0F, -5.0F, 4, 4, 6, 0.0F);
//        this.udder.setPos(0.0F, 10.0F, 21.25F);
//
//        this.nipples = new EnhancedRendererModelNew(this, 24, 77, "Nipples");
//        this.nipples.addBox(-2.0F, 0.0F, -1.0F, 1, 2, 1, -0.15F);
//        this.nipples.texOffs(29, 77);
//        this.nipples.addBox(1.0F, 0.0F, -1.0F, 1, 2, 1, -0.15F);
//        this.nipples.texOffs(35, 77);
//        this.nipples.addBox(-2.0F, 0.0F, 2.0F, 1, 2, 1, -0.15F);
//        this.nipples.texOffs(40, 77);
//        this.nipples.addBox(1.0F, 0.0F, 2.0F, 1, 2, 1, -0.15F);
//        this.nipples.setPos(0.0F, 1.5F, -3.5F);
//
//        this.humpXSmall = new EnhancedRendererModelNew(this, 0, 8, "HumpXXS");
//        this.humpXSmall.addBox(-2.0F, 0.0F, 0.0F, 4, 8, 6, -1.0F);
//        this.humpXSmall.setPos(0.0F, 0.0F, 1.0F);
//
//        this.humpSmall = new EnhancedRendererModelNew(this, 0, 8, "HumpXS");
//        this.humpSmall.addBox(-2.0F, 0.0F, 0.0F, 4, 8, 6, -0.5F);
//        this.humpSmall.setPos(0.0F, 0.0F, 1.0F);
//
//        this.humpSmallish = new EnhancedRendererModelNew(this, 0, 8, "HumpS");
//        this.humpSmallish.addBox(-2.0F, 0.0F, 0.0F, 4, 8, 6, -0.25F);
//        this.humpSmallish.setPos(0.0F, 0.0F, 1.0F);
//
//        this.humpMedium = new EnhancedRendererModelNew(this, 0, 8, "Hump");
//        this.humpMedium.addBox(-2.0F, 0.0F, 0.0F, 4, 8, 6, 0.0F);
//        this.humpMedium.setPos(0.0F, 0.0F, 1.0F);
//
//        this.humpLargeish = new EnhancedRendererModelNew(this, 0, 8, "HumpL");
//        this.humpLargeish.addBox(-2.0F, 0.0F, 0.0F, 4, 8, 6, 0.5F);
//        this.humpLargeish.setPos(0.0F, 0.0F, 1.0F);
//
//        this.humpLarge = new EnhancedRendererModelNew(this, 0, 8, "HumpXL");
//        this.humpLarge.addBox(-2.0F, 0.0F, 0.0F, 4, 8, 6, 1.0F);
//        this.humpLarge.setPos(0.0F, 0.0F, 1.0F);
//
//        this.humpXLarge = new EnhancedRendererModelNew(this, 0, 8, "HumpXXL");
//        this.humpXLarge.addBox(-2.0F, 0.0F, 0.0F, 4, 8, 6, 1.5F);
//        this.humpXLarge.setPos(0.0F, 0.0F, 1.0F);
//
//        this.tail0 = new EnhancedRendererModelNew(this, 0,0, "Tail");
//        this.tail0.addBox(-1.0F, 0.0F, 0.0F, 2, 4, 1);
//        this.tail0.setPos(0.0F, 0.0F, 22.0F);
//
//        this.tail1 = new EnhancedRendererModelNew(this, 6,0);
//        this.tail1.addBox(-0.5F, 0.0F, 0.0F, 1, 4, 1);
//        this.tail1.setPos(0.0F, 4.0F, 0.0F);
//
//        this.tail2 = new EnhancedRendererModelNew(this, 10,0);
//        this.tail2.addBox(-0.5F, 0.0F, 0.0F, 1, 3, 1, -0.01F);
//        this.tail2.setPos(0.0F, 3.0F, 0.0F);
//
//        this.tailBrush = new EnhancedRendererModelNew(this, 14,0);
//        this.tailBrush.addBox(-1.0F, 0.0F, -0.5F, 2, 3, 2);
//        this.tailBrush.setPos(0.0F, 3.0F, 0.0F);
//
//        this.leg1 = new ModelPart(this, 0, 54);
//        this.leg1.addBox(0.0F, 0.0F, 0.0F, 3, 10, 3, 0.0F);
//        this.leg1.setPos(-6.0F, 13.5F, -10.0F);
//
//        this.leg2 = new ModelPart(this, 12, 54);
//        this.leg2.addBox(0.0F, 0.0F, 0.0F, 3, 10, 3, 0.0F);
//        this.leg2.setPos(3.0F, 13.5F, -10.0F);
//
//        this.leg3 = new ModelPart(this, 0, 67);
//        this.leg3.addBox(0.0F, 0.0F, 0.0F, 3, 10, 3, 0.0F);
//        this.leg3.setPos(-6.0F, 13.5F, 9.0F);
//
//        this.leg4 = new ModelPart(this, 12, 67);
//        this.leg4.addBox(0.0F, 0.0F, 0.0F, 3, 10, 3, 0.0F);
//        this.leg4.setPos(3.0F, 13.5F, 9.0F);
//
//        this.shortLeg1 = new ModelPart(this, 0, 54);
//        this.shortLeg1.addBox(0.0F, 0.0F, 0.0F, 3, 7, 3, 0.0F);
//        this.shortLeg1.setPos(-6.0F, 13.5F, -10.0F);
//
//        this.shortLeg2 = new ModelPart(this, 12, 54);
//        this.shortLeg2.addBox(0.0F, 0.0F, 0.0F, 3, 7, 3, 0.0F);
//        this.shortLeg2.setPos(3.0F, 13.5F, -10.0F);
//
//        this.shortLeg3 = new ModelPart(this, 0, 67);
//        this.shortLeg3.addBox(0.0F, 0.0F, 0.0F, 3, 7, 3, 0.0F);
//        this.shortLeg3.setPos(-6.0F, 13.5F, 9.0F);
//
//        this.shortLeg4 = new ModelPart(this, 12, 67);
//        this.shortLeg4.addBox(0.0F, 0.0F, 0.0F, 3, 7, 3, 0.0F);
//        this.shortLeg4.setPos(3.0F, 13.5F, 9.0F);
//
//        this.mushroomBody1 = new ModelPart(this, 54, 64);
//        this.mushroomBody1.addBox(0.0F, -8.0F, 0.0F, 1, 8, 8);
//        this.mushroomBody1.texOffs(62, 71);
//        this.mushroomBody1.addBox(-3.0F, -8.0F, 4.0F, 8, 8, 1);
//        this.mushroomBody1.setPos(-3.0F, 0.0F, -5.0F);
//
//        this.mushroomBody2 = new ModelPart(this, 54, 64);
//        this.mushroomBody2.addBox(0.0F, -8.0F, 0.0F, 1, 8, 8);
//        this.mushroomBody2.texOffs(62, 71);
//        this.mushroomBody2.addBox(-3.0F, -8.0F, 4.0F, 8, 8, 1);
//        this.mushroomBody2.setPos(3.0F, 0.0F, 5.0F);
//
//        this.mushroomHead = new EnhancedRendererModelNew(this, 54, 64);
//        this.mushroomHead.addBox(0.0F, -8.0F, 0.0F, 1, 8, 8);
//        this.mushroomHead.texOffs(62, 71);
//        this.mushroomHead.addBox(-3.0F, -8.0F, 4.0F, 8, 8, 1);
//        this.mushroomHead.setPos(0.0F, -0.5F, -6.0F);
//
//        this.neck.addChild(this.head);
//        this.head.addChild(this.earLongestL);
//        this.head.addChild(this.earLongL);
//        this.head.addChild(this.earMediumL);
//        this.head.addChild(this.earSmallL);
//        this.head.addChild(this.earSmallestL);
//        this.head.addChild(this.earLongestR);
//        this.head.addChild(this.earLongR);
//        this.head.addChild(this.earMediumR);
//        this.head.addChild(this.earSmallR);
//        this.head.addChild(this.earSmallestR);
//        this.head.addChild(this.noseMale);
//        this.head.addChild(this.noseFemale);
//        this.head.addChild(this.mouth);
//        this.head.addChild(this.eyeLeft);
//        this.head.addChild(this.eyeRight);
//        this.head.addChild(this.hornNub1);
//        this.head.addChild(this.hornNub2);
//        this.head.addChild(this.hornNub3);
//        this.head.addChild(this.hornNub4);
//        this.head.addChild(this.hornNub5);
//        this.hornParent.addChild(this.hornL0);
//        this.hornL0.addChild(this.hornL1);
//        this.hornL1.addChild(this.hornL2);
//        this.hornL2.addChild(this.hornL3);
//        this.hornL3.addChild(this.hornL4);
//        this.hornL4.addChild(this.hornL5);
//        this.hornL5.addChild(this.hornL6);
//        this.hornL6.addChild(this.hornL7);
//        this.hornL7.addChild(this.hornL8);
//        this.hornL8.addChild(this.hornL9);
//        this.hornParent.addChild(this.hornR0);
//        this.hornR0.addChild(this.hornR1);
//        this.hornR1.addChild(this.hornR2);
//        this.hornR2.addChild(this.hornR3);
//        this.hornR3.addChild(this.hornR4);
//        this.hornR4.addChild(this.hornR5);
//        this.hornR5.addChild(this.hornR6);
//        this.hornR6.addChild(this.hornR7);
//        this.hornR7.addChild(this.hornR8);
//        this.hornR8.addChild(this.hornR9);
//        this.body.addChild(this.humpXSmall);
//        this.body.addChild(this.humpSmall);
//        this.body.addChild(this.humpSmallish);
//        this.body.addChild(this.humpMedium);
//        this.body.addChild(this.humpLargeish);
//        this.body.addChild(this.humpLarge);
//        this.body.addChild(this.humpXLarge);
//        this.body.addChild(this.tail0);
//        this.tail0.addChild(this.tail1);
//        this.tail1.addChild(this.tail2);
//        this.tail2.addChild(this.tailBrush);
//        this.body.addChild(this.udder);
//        this.udder.addChild(this.nipples);
//
//        this.head.addChild(this.mushroomHead);
//
//        /**
//         * Equipment stuff
//         */
//
//        this.chest1 = new ModelPart(this, 80, 0);
//        this.chest1.addBox(-3.0F, 0.0F, 0.0F, 8, 8, 3);
//        this.chest1.setPos(-8.75F, 3.0F, 6.0F);
//        this.chest1.yRot = ((float)Math.PI / 2F);
//        this.chest2 = new ModelPart(this, 80, 11);
//        this.chest2.addBox(-3.0F, 0.0F, 0.0F, 8, 8, 3);
//        this.chest2.setPos(5.75F, 3.0F, 6.0F);
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
//        this.headTassles = new EnhancedRendererModelNew(this, 25, 56);
//        this.headTassles.addBox(1.0F, 0.0F, -5.0F, 4, 9, 1);
//        this.headTassles.texOffs(34, 56);
//        this.headTassles.addBox(1.0F, 0.0F, 4.0F, 4, 9, 1);
//
//        this.collar = new EnhancedRendererModelNew(this, 80, 68);
//        this.collar.addBox(-3.5F, 1.0F, -0.5F, 7,  2, 9);
//        this.collar.texOffs(81, 71);
//        this.collar.addBox(0.0F, 0.5F, 8.5F, 0,  3, 3);
//        this.collar.texOffs(103, 71);
//        this.collar.addBox(-1.5F, 0.5F, 10.0F, 3, 3, 3, -0.25F);
//        this.collar.xRot = (float)-Math.PI/2.0F;
//        this.neck.addChild(this.collar);
//
//        this.body.addChild(this.saddle);
//        this.saddleHorn.addChild(this.saddlePomel);
//
//        //western
//        this.body.addChild(this.saddleWestern);
//        this.saddleWestern.addChild(this.saddleHorn);
//        this.saddleWestern.addChild(this.saddleSideL);
//        this.saddleWestern.addChild(this.saddleSideR);
//        this.saddleWestern.addChild(this.saddlePad);
//        this.saddleWestern.addChild(this.stirrup2DWideL);
//        this.saddleWestern.addChild(this.stirrup2DWideR);
//        this.stirrup2DWideL.addChild(this.stirrup);
//        this.stirrup2DWideR.addChild(this.stirrup);
//        //english
//        this.body.addChild(this.saddleEnglish);
//        this.saddleEnglish.addChild(this.saddleHorn);
//        this.saddleEnglish.addChild(this.saddleSideL);
//        this.saddleEnglish.addChild(this.saddleSideR);
//        this.saddleEnglish.addChild(this.saddlePad);
//        this.saddleEnglish.addChild(this.stirrup3DNarrowL);
//        this.saddleEnglish.addChild(this.stirrup3DNarrowR);
//        this.stirrup3DNarrowL.addChild(this.stirrup);
//        this.stirrup3DNarrowR.addChild(this.stirrup);
//        //vanilla
//        this.saddle.addChild(saddlePad);
//        this.saddle.addChild(this.stirrup3DNarrowL);
//        this.saddle.addChild(this.stirrup3DNarrowR);
//
//        //blanket deco
//        this.head.addChild(this.headTassles);
//
//        //bridle
//        this.texHeight = 128;
//        this.texWidth = 128;
//        this.bridle = new EnhancedRendererModelNew(this, 0, 40);
//        this.bridle.addBox(-4.0F, 0.0F, -7.0F, 8, 7, 6, 0.1F);
//
//        this.bridleMale = new EnhancedRendererModelNew(this, 0, 53);
//        this.bridleMale.addBox(-2.0F, -0.15F, -10.0F, 4, 6, 4, 0.35F);
//
//        this.bridleFemale = new EnhancedRendererModelNew(this, 0, 53);
//        this.bridleFemale.addBox(-2.0F, 0.6F, -10.75F, 4, 6, 4, -0.15F);
//
//        this.head.addChild(this.bridle);
//        this.bridle.addChild(this.bridleMale);
//        this.bridle.addChild(this.bridleFemale);
//
//
//    }
//
//    public void renderToBuffer(PoseStack matrixStackIn, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
//        CowModelData cowModelData = getCowModelData();
//
//        resetAllCubes();
//
//        Phenotype cow = cowModelData.phenotype;
//
//        if (cow != null) {
//            HornType horns = cow.hornType;
//
//            boolean dwarf = cow.dwarf;
//            float bodyWidth = cow.bodyWidth;
//            float bodyLength = cow.bodyLength;
//            int hump = cow.hump;
//            float hornScale = cow.hornScale;
//
//            float age = 1.0F;
//            float babyScale = 1.0F;
//            if (!(cowModelData.birthTime == null) && !cowModelData.birthTime.equals("") && !cowModelData.birthTime.equals("0")) {
//                int ageTime = (int) (cowModelData.clientGameTime - Long.parseLong(cowModelData.birthTime));
//                if (ageTime <= cowModelData.adultAge) {
//                    age = ageTime < 0 ? 0 : ageTime / (float)cowModelData.adultAge;
//                    babyScale = (3.0F - age) / 2;
//                }
//            }
//
//            float d = 0.0F;
//            if (!cowModelData.sleeping) {
//                if (dwarf) {
//                    d = 0.2F * (1.0F - age);
//                } else {
//                    d = 0.3F * (1.0F - age);
//                }
//            } else {
//                babyScale = 1.0F;
//            }
//
//            float genderModifier = cow.isFemale ? 1.8F : 2.0F;
//            float finalCowSize = (((genderModifier * age) + 1.0F) / 3.0F) * cowModelData.cowSize;
//            bodyWidth = finalCowSize + (finalCowSize * bodyWidth * age);
//            bodyLength = finalCowSize + (finalCowSize * bodyLength * age);
//
//            matrixStackIn.pushPose();
//            matrixStackIn.scale(bodyWidth, finalCowSize, bodyLength);
//            matrixStackIn.translate(0.0F, (-1.45F + 1.45F / (finalCowSize)) - d, 0.0F);
//
//            if (cowModelData.blink >= 6) {
//                this.eyeLeft.visible = true;
//                this.eyeRight.visible = true;
//            } else {
//                this.eyeLeft.visible = false;
//                this.eyeRight.visible = false;
//            }
//
//            if (cow.isFemale) {
//                this.noseFemale.visible = true;
//                this.bridleFemale.visible = true;
//            } else {
//                this.noseMale.visible = true;
//                this.bridleMale.visible = true;
//            }
//
//            if (cow.earSize <= 1) {
//                this.earSmallestL.visible = true;
//                this.earSmallestR.visible = true;
//            } else if (cow.earSize <= 3) {
//                this.earSmallL.visible = true;
//                this.earSmallR.visible = true;
//            } else if (cow.earSize <= 5) {
//                this.earMediumL.visible = true;
//                this.earMediumR.visible = true;
//            } else if (cow.earSize <= 7) {
//                this.earLongL.visible = true;
//                this.earLongR.visible = true;
//            } else {
//                this.earLongestL.visible = true;
//                this.earLongestR.visible = true;
//            }
//
//            if (cowModelData.collar) {
//                this.collar.visible = true;
//            }
//
//            if (cowModelData.bridle) {
//                this.bridle.visible = true;
//            }
//
//            this.mushroomHead.visible = false;
//            if (!this.young) {
//                this.mushroomBody1.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
//                this.mushroomBody2.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
//                this.mushroomHead.visible = true;
//            }
//
//            if (cowModelData.hasChest) {
//                this.chest1.visible = true;
//                this.chest2.visible = true;
//                this.chest1.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
//                this.chest2.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
//            }
//
//            renderHump(hump, cowModelData.unrenderedModels, matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
//
//            renderBodySaddleAndUdder(cowModelData.saddle, cowModelData.cowStatus, cowModelData.bagSize, cowModelData.unrenderedModels, matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
//
//            this.neck.render(matrixStackIn, bufferIn, null, cowModelData.unrenderedModels, true, packedLightIn, packedOverlayIn, red, green, blue, alpha);
//
//            if (horns != HornType.POLLED) {
//                renderHorns(hornScale, cowModelData.unrenderedModels, matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
//            }
//
//            matrixStackIn.popPose();
//
//            matrixStackIn.pushPose();
//            matrixStackIn.scale(bodyWidth, finalCowSize * babyScale, bodyLength);
//            matrixStackIn.translate(0.0F, -1.45F + 1.45F / (finalCowSize * babyScale), 0.0F);
//
//            renderLegs(dwarf, matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
//
//            matrixStackIn.popPose();
//        }
//
////        matrixStackIn.push();
////        //TODO fix up cow necks/heads
//////        Map<String, List<Float>> mapOfScale = new HashMap<>();
//////
//////        List<Float> scalings = new ArrayList<>();
//////
//////        //scaling
//////        scalings.add(bodyWidth);
//////        scalings.add(finalCowSize);
//////        scalings.add(bodyLength);
//////        //translations
//////        scalings.add(0.0F);
//////        scalings.add((-1.45F + 1.45F / (finalCowSize)) - d);
//////        scalings.add(0.0F);
//////        mapOfScale.put("Neck", scalings);
////
////        matrixStackIn.pop();
//
//    }
//
//    private void resetAllCubes() {
//        this.earSmallestL.visible = false;
//        this.earSmallL.visible = false;
//        this.earMediumL.visible = false;
//        this.earLongL.visible = false;
//        this.earLongestL.visible = false;
//        this.earSmallestR.visible = false;
//        this.earSmallR.visible = false;
//        this.earMediumR.visible = false;
//        this.earLongR.visible = false;
//        this.earLongestR.visible = false;
//        this.udder.visible = false;
//        this.humpXSmall.visible = false;
//        this.humpSmall.visible = false;
//        this.humpSmallish.visible = false;
//        this.humpMedium.visible = false;
//        this.humpLargeish.visible = false;
//        this.humpLarge.visible = false;
//        this.humpXLarge.visible = false;
//        this.mushroomHead.visible = false;
//        this.saddle.visible = false;
//        this.saddleWestern.visible = false;
//        this.saddleEnglish.visible = false;
//        this.saddlePomel.visible = false;
//        this.headTassles.visible = false;
//        this.collar.visible = false;
//        this.bridle.visible = false;
//        this.bridleMale.visible = false;
//        this.noseMale.visible = false;
//        this.bridleFemale.visible = false;
//        this.noseFemale.visible = false;
//        this.chest1.visible = false;
//        this.chest2.visible = false;
//    }
//
//
//    private void renderBodySaddleAndUdder(ItemStack saddleStack, String cowStatus, float bagSize, List<String> unrenderedModels, PoseStack matrixStackIn, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
//        float nipScale = 1.5F/(0.5F+bagSize);
//        float bagthickness = bagSize * bagSize;
//        Map<String, List<Float>> mapOfScale = new HashMap<>();
//        List<Float> scalingsForUdder = ModelHelper.createScalings(bagthickness, bagSize, bagthickness, 0.0F, 0.0F/*-(bagSize-1.0F)*0.4F*/, 0.0F/*-(bagSize-1.0F)*0.85F*/);
//        List<Float> scalingsForNipples = ModelHelper.createScalings(nipScale, nipScale, nipScale, 0.0F, (bagSize-1.0F)*0.05F, 0.0F);
//        mapOfScale.put("Udder", scalingsForUdder);
//        mapOfScale.put("Nipples", scalingsForNipples);
//
//        if ((cowStatus.equals(EntityState.PREGNANT.toString()) || cowStatus.equals(EntityState.MOTHER.toString()))) {
//            this.udder.visible = true;
//        }
//
//        if (saddleStack!=null) {
//            if (!saddleStack.isEmpty()) {
//                Item saddle = saddleStack.getItem();
//                float saddleScale = 0.875F;
//                List<Float> scalingsForSaddle = ModelHelper.createScalings(saddleScale, saddleScale, saddleScale, 0.0F, -saddleScale * 0.01F, (saddleScale - 1.0F) * 0.04F);
//
//                if (saddle instanceof CustomizableSaddleWestern) {
//                    this.saddleWestern.visible = true;
//                    this.saddlePomel.visible = true;
//                    mapOfScale.put("WesternSaddle", scalingsForSaddle);
//                } else if (saddle instanceof CustomizableSaddleEnglish) {
//                    this.saddleEnglish.visible = true;
//                    mapOfScale.put("EnglishSaddle", scalingsForSaddle);
//                } else if (!(saddle instanceof CustomizableCollar)) {
//                    this.saddle.visible = true;
//                    mapOfScale.put("Saddle", scalingsForSaddle);
//                }
//            }
//        }
//
//        this.body.render(matrixStackIn, bufferIn , mapOfScale, unrenderedModels, false, packedLightIn, packedOverlayIn, red, green, blue, alpha);
//    }
//
//    private void renderLegs(boolean dwarf, PoseStack matrixStackIn, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
//        if (dwarf) {
//            ImmutableList.of(this.shortLeg1, this.shortLeg2, this.shortLeg3, this.shortLeg4).forEach((renderer) -> {
//                renderer.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
//            });
//        } else {
//            ImmutableList.of(this.leg1, this.leg2, this.leg3, this.leg4).forEach((renderer) -> {
//                renderer.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
//            });
//        }
//    }
//
//    private void renderHump(int hump, List<String> unrenderedModels, PoseStack matrixStackIn, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
//        if(hump == 12){
//            this.humpXLarge.visible = true;
//        }else if (hump >= 10){
//            this.humpLarge.visible = true;
//        }else if (hump >= 8){
//            this.humpLargeish.visible = true;
//        }else if (hump >= 6){
//            this.humpMedium.visible = true;
//        }else if (hump >= 4){
//            this.humpSmallish.visible = true;
//        }else if (hump >= 2){
//            this.humpSmall.visible = true;
//        }else if (hump == 1){
//            this.humpXSmall.visible = true;
//        }
//    }
//
//    private void renderTail(float scale, float cowSize, float babyScale, List<String> unrenderedModels) {
//            Map<String, List<Float>> mapOfScale = new HashMap<>();
//
//            //TODO update scalings to use X, Y, Z
//            List<Float> scalingsForTail = ModelHelper.createScalings(cowSize, cowSize, cowSize, 0.0F, (-1.45F + 1.45F / (cowSize*babyScale)), 0.0F);
//            mapOfScale.put("Tail", scalingsForTail);
//    }
//
//    @Override
//    public void prepareMobModel(T entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTickTime) {
//        super.prepareMobModel(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTickTime);
//        CowModelData cowModelData = getCreateCowModelData(entitylivingbaseIn);
//        Phenotype cow = cowModelData.phenotype;
//        this.currentCow = entitylivingbaseIn.getId();
//
//        if (cow != null) {
//            float onGround = 2.75F;
//
//            if (cowModelData.sleeping) {
//                onGround = sleepingAnimation();
//            } else {
//                onGround = standingAnimation(cow.dwarf);
//            }
//            this.neck.y = onGround + (entitylivingbaseIn).getHeadRotationPointY(partialTickTime) * 9.0F;
//            this.headRotationAngleX = (entitylivingbaseIn).getHeadRotationAngleX(partialTickTime);
//        }
//    }
//
//    @Override
//    public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
//        CowModelData cowModelData = getCowModelData();
//        Phenotype cow = cowModelData.phenotype;
//        if (cow != null) {
//            ItemStack saddleStack = getCowModelData().saddle;
//            List<String> unrenderedModels = new ArrayList<>();
//
//            if (cow.isFemale) {
//                this.neck.z = -10F;
//            } else {
//                this.neck.z = -9F;
//            }
//
//            float headYaw = netHeadYaw - Math.round(netHeadYaw / 180) * 180;
//            this.head.yRot = headYaw * 0.017453292F * 0.4F;
//
//            this.neck.xRot = (headPitch * 0.017453292F);
//            this.neck.yRot = headYaw * 0.017453292F * 0.6F;
//
//            this.humpXLarge.xRot = (this.neck.xRot / 2.5F) - 0.2F;
//            this.humpXLarge.yRot = this.neck.yRot / 2.5F;

//            this.neck.xRot = this.headRotationAngleX;
//            this.head.xRot = 0.5F;
//            this.mouth.xRot = this.neck.xRot <= 0 ? -0.3F : -0.3F + (this.headRotationAngleX / 2.0F);
//
////        this.head.rotateAngleX = 0.05F + this.head.rotateAngleX;   //might need to merge this with another line
//
//            ModelHelper.copyModelRotations(humpXLarge, humpLarge);
//            ModelHelper.copyModelRotations(humpXLarge, humpLargeish);
//            ModelHelper.copyModelRotations(humpXLarge, humpMedium);
//            ModelHelper.copyModelRotations(humpXLarge, humpSmallish);
//            ModelHelper.copyModelRotations(humpXLarge, humpSmall);
//            ModelHelper.copyModelRotations(humpXLarge, humpXSmall);
//
//            if (!cowModelData.sleeping) {
//                this.leg1.xRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
//                this.leg2.xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
//                this.leg3.xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
//                this.leg4.xRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
//            }
//
//            ModelHelper.copyModelPositioning(leg1, shortLeg1);
//            ModelHelper.copyModelPositioning(leg2, shortLeg2);
//            ModelHelper.copyModelPositioning(leg3, shortLeg3);
//            ModelHelper.copyModelPositioning(leg4, shortLeg4);
//

//
//            this.tail0.zRot = Mth.cos(limbSwing * 0.6662F) * 0.4F * limbSwingAmount;
//            this.tail1.zRot = Mth.cos(limbSwing * 0.6662F) * 0.4F * limbSwingAmount;
//            this.tail2.zRot = Mth.cos(limbSwing * 0.6662F) * 0.45F * limbSwingAmount;
//            this.tailBrush.zRot = Mth.cos(limbSwing * 0.6662F) * 0.6F * limbSwingAmount;
//
//            this.tail0.xRot = 0.4F;
//            this.tail1.xRot = -0.2F;
//            this.tail2.xRot = -0.2F;
//            this.tailBrush.xRot = 0F;
//
//            setEarRotations(cow.earFloppiness, cow.earSize, cow.averageEars);
//
//            setHornRotations(cowModelData, unrenderedModels);
//
//            float hump = cow.humpPlacement;
//            this.humpXLarge.y = hump;
//            this.humpLarge.y = hump;
//            this.humpLargeish.y = hump;
//            this.humpMedium.y = hump;
//            this.humpSmallish.y = hump;
//            this.humpSmall.y = hump;
//            this.humpXSmall.y = hump;
//
//            cowModelData.unrenderedModels.addAll(unrenderedModels);
//
//            if (saddleStack != null) {
//                if (!saddleStack.isEmpty()) {
//                    Item saddle = saddleStack.getItem();
//                    if (saddle instanceof CustomizableSaddleWestern) {
//                        this.saddleWestern.z = 9.0F;
//                        this.saddleSideL.setPos(5.0F, -1.0F, -5.25F);
//                        this.saddleSideR.setPos(-5.0F, -1.0F, -5.25F);
//                        this.saddleHorn.setPos(0.0F, -2.0F, -2.0F);
//                        this.saddleHorn.xRot = (float) Math.PI / 8.0F;
//                        this.saddlePomel.setPos(0.0F, -1.5F, -0.5F);
//                        this.saddlePomel.xRot = -0.2F;
//                        this.stirrup2DWideL.setPos(7.5F, 0.0F, -3.5F);
//                        this.stirrup2DWideR.setPos(-7.5F, 0.0F, -3.5F);
//                    } else if (saddle instanceof CustomizableSaddleEnglish) {
//                        this.saddleEnglish.z = 9.0F;
//                        this.saddleSideL.setPos(3.25F, -0.5F, -4.0F);
//                        this.saddleSideR.setPos(-3.25F, -0.5F, -4.0F);
//                        this.saddleHorn.setPos(0.0F, -1.0F, -1.0F);
//                        this.saddleHorn.xRot = (float) Math.PI / 4.5F;
//                        this.stirrup3DNarrowL.setPos(7.25F, -0.25F, -1.5F);
//                        this.stirrup3DNarrowR.setPos(-7.25F, -0.25F, -1.5F);
//                    } else if (!(saddle instanceof CustomizableCollar)) {
//                        this.saddle.z = 9.0F;
//                        this.stirrup3DNarrowL.setPos(8.0F, 0.0F, 0.0F);
//                        this.stirrup3DNarrowR.setPos(-8.0F, 0.0F, 0.0F);
//                    }
//                }
//            }
//
//            this.headTassles.yRot = (float) Math.PI / 2.0F;
//        }
//    }
//
//
//    private void setEarRotations(float floppiness, float earSize, boolean averageEars) {
//        if (averageEars) {
//            if (earSize <= 1) {
//                this.earSmallestL.setPos(3.0F, 0.5F, -2.5F);
//                this.earSmallestL.zRot = 1.1F;
//                this.earSmallestR.setPos(-3.0F, 0.5F, -2.5F);
//                this.earSmallestR.zRot = -1.1F;
//            } else if (earSize <= 3) {
//                this.earSmallL.setPos(3.0F, 0.75F, -2.5F);
//                this.earSmallL.zRot = 1.1F + (earSize / 6.25F);
//                this.earSmallR.setPos(-3.0F, 0.75F, -2.5F);
//                this.earSmallR.zRot = -(1.1F + (earSize / 6.25F));
//            } else if (earSize <= 5) {
//                this.earMediumL.setPos(3.0F + (floppiness / 3), 1.0F, -2.5F);
//                this.earMediumL.zRot = 1.1F + (earSize / 6.25F);
//                this.earMediumR.setPos(-(3.0F + (floppiness / 3)), 1.0F, -2.5F);
//                this.earMediumR.zRot = -(1.1F + (earSize / 6.25F));
//            } else if (earSize <= 7) {
//                this.earLongL.setPos(3.0F + (floppiness / 2), 1.0F, -2.5F);
//                this.earLongL.zRot = 1.1F + (earSize / 6.25F);
//                this.earLongR.setPos(-(3.0F + (floppiness / 2)), 1.0F, -2.5F);
//                this.earLongR.zRot = -(1.1F + (earSize / 6.25F));
//            } else {
//                this.earLongestL.setPos(3.0F + floppiness, 1.0F, -2.5F);
//                this.earLongestL.zRot = 1.1F + (earSize / 6.25F);
//                this.earLongestR.setPos(-(3.0F + floppiness), 1.0F, -2.5F);
//                this.earLongestR.zRot = -(1.1F + (earSize / 6.25F));
//            }
//        } else {
//            if (earSize <= 1) {
//                this.earSmallestL.setPos(3.0F, 0.5F, -2.5F);
//                this.earSmallestL.zRot = floppiness;
//                this.earSmallestR.setPos(-3.0F, 0.5F, -2.5F);
//                this.earSmallestR.zRot = -floppiness;
//            } else if (earSize <= 3) {
//                this.earSmallL.setPos(3.0F, 0.75F, -2.5F);
//                this.earSmallL.zRot = floppiness;
//                this.earSmallR.setPos(-3.0F, 0.75F, -2.5F);
//                this.earSmallR.zRot = -floppiness;
//            } else if (earSize <= 5) {
//                this.earMediumL.setPos(3.0F + (floppiness / 3), 1.0F, -2.5F);
//                this.earMediumL.zRot = floppiness;
//                this.earMediumR.setPos(-(3.0F + (floppiness / 3)), 1.0F, -2.5F);
//                this.earMediumR.zRot = -floppiness;
//            } else if (earSize <= 7) {
//                this.earLongL.setPos(3.0F + (floppiness / 2), 1.0F, -2.5F);
//                this.earLongL.zRot = floppiness;
//                this.earLongR.setPos(-(3.0F + (floppiness / 2)), 1.0F, -2.5F);
//                this.earLongR.zRot = -floppiness;
//            } else {
//                this.earLongestL.setPos(3.0F + floppiness, 1.0F, -2.5F);
//                this.earLongestL.zRot = floppiness;
//                this.earLongestR.setPos(-(3.0F + floppiness), 1.0F, -2.5F);
//                this.earLongestR.zRot = -floppiness;
//            }
//
//        }
//    }
//
//    private float sleepingAnimation() {
//        float onGround;
//
//        onGround = 9.80F;
//        this.body.y = 9.75F;
//
//        this.mushroomBody1.y = 9.75F;
//        this.mushroomBody2.y = 9.75F;
//
//        this.leg1.xRot = 1.575F;
//        this.leg2.xRot = 1.575F;
//        this.leg3.xRot = -1.575F;
//        this.leg4.xRot = -1.575F;
//
//        this.leg1.setPos(-6.0F, 14.0F+onGround, -10.0F);
//        this.leg2.setPos(3.0F, 14.0F+onGround, -10.0F);
//        this.leg3.setPos(-6.0F, 11.0F+onGround, 12.0F);
//        this.leg4.setPos(3.0F, 11.0F+onGround, 12.0F);
//        return onGround;
//    }
//
//    private float standingAnimation(boolean dwarf) {
//        float onGround;
//        if (dwarf){
//            //dwarf
//            onGround = 5.75F;
//            this.body.y = 5.5F;
//
//            this.mushroomBody1.y = 5.5F;
//            this.mushroomBody2.y = 5.5F;
//
//            this.leg1.setPos(-6.0F, 16.5F, -10.0F);
//            this.leg2.setPos(3.0F, 16.5F, -10.0F);
//            this.leg3.setPos(-6.0F, 16.5F, 9.0F);
//            this.leg4.setPos(3.0F, 16.5F, 9.0F);
//
//            this.chest1.setPos(-8.75F, 6.0F, 6.0F);
//            this.chest2.setPos(5.75F, 6.0F, 6.0F);
//
//        } else {
//            onGround = 2.75F;
//            this.body.y = 2.5F;
//
//            this.mushroomBody1.y = 2.5F;
//            this.mushroomBody2.y = 2.5F;
//
//            this.leg1.setPos(-6.0F, 13.5F, -10.0F);
//            this.leg2.setPos(3.0F, 13.5F, -10.0F);
//            this.leg3.setPos(-6.0F, 13.5F, 9.0F);
//            this.leg4.setPos(3.0F, 13.5F, 9.0F);
//
//            this.chest1.setPos(-8.75F, 3.0F, 6.0F);
//            this.chest2.setPos(5.75F, 3.0F, 6.0F);
//        }
//
//        return onGround;
//    }
//
//    public ModelPart getHead() {
//        return this.neck;
//    }
//
//    private class CowModelData {
////        boolean isCow = true;
//        Phenotype phenotype;
//        String birthTime;
//        char[] uuidArray;
//        float cowSize = 1.0F;
////        boolean isFemale;
//        int lastAccessed = 0;
//        float bagSize = 1.0F;
//        String cowStatus = "";
//        boolean sleeping = false;
//        int blink = 0;
//        int dataReset = 0;
//        long clientGameTime = 0;
//        List<String> unrenderedModels = new ArrayList<>();
//        ItemStack saddle;
//        boolean bridle = false;
//        boolean harness = false;
//        boolean collar = false;
//        boolean hasChest = false;
//        int adultAge;
//    }
//
//    private CowModelData getCowModelData() {
//        if (this.currentCow == null || !cowModelDataCache.containsKey(this.currentCow)) {
//            return new CowModelData();
//        }
//        return cowModelDataCache.get(this.currentCow);
//    }
//
//    private CowModelData getCreateCowModelData(T enhancedCow) {
//        clearCacheTimer++;
//        if(clearCacheTimer > 50000) {
//            cowModelDataCache.values().removeIf(value -> value.lastAccessed==1);
//            for (CowModelData cowModelData : cowModelDataCache.values()){
//                cowModelData.lastAccessed = 1;
//            }
//            clearCacheTimer = 0;
//        }
//
//        if (cowModelDataCache.containsKey(enhancedCow.getId())) {
//            CowModelData cowModelData = cowModelDataCache.get(enhancedCow.getId());
//            cowModelData.lastAccessed = 0;
//            cowModelData.dataReset++;
//            cowModelData.cowStatus = enhancedCow.getEntityStatus();
//            cowModelData.bagSize = enhancedCow.getBagSize();
//            cowModelData.sleeping = enhancedCow.isAnimalSleeping();
//            cowModelData.blink = enhancedCow.getBlink();
//            cowModelData.birthTime = enhancedCow.getBirthTime();
//            cowModelData.clientGameTime = (((ClientLevel)enhancedCow.level).getLevelData()).getGameTime();
//            int collarSlot = hasCollar(enhancedCow.getEnhancedInventory());
//            cowModelData.collar = collarSlot!=0;
//            cowModelData.saddle = collarSlot!=1 ? enhancedCow.getEnhancedInventory().getItem(1) : ItemStack.EMPTY;
//            cowModelData.bridle = !enhancedCow.getEnhancedInventory().getItem(3).isEmpty() && collarSlot!=3;
//            cowModelData.harness = !enhancedCow.getEnhancedInventory().getItem(5).isEmpty() && collarSlot!=5;
//            cowModelData.hasChest = !enhancedCow.getEnhancedInventory().getItem(0).isEmpty();
//            cowModelData.unrenderedModels = new ArrayList<>();
//
//            return cowModelData;
//        } else {
//            //initial grab
//            CowModelData cowModelData = new CowModelData();
//            if (enhancedCow.getSharedGenes()!=null) {
//                char[] uuid = (enhancedCow.getMooshroomUUID().isEmpty() || enhancedCow.getMooshroomUUID().equals("0")) ? enhancedCow.getStringUUID().toCharArray() : enhancedCow.getMooshroomUUID().toCharArray();
//                cowModelData.phenotype = new Phenotype(enhancedCow.getSharedGenes().getAutosomalGenes(), uuid, enhancedCow.getOrSetIsFemale());
//            }
//            cowModelData.cowSize = enhancedCow.getAnimalSize();
//            cowModelData.bagSize = enhancedCow.getBagSize();
//            cowModelData.cowStatus = enhancedCow.getEntityStatus();
//            cowModelData.sleeping = enhancedCow.isAnimalSleeping();
//            cowModelData.blink = enhancedCow.getBlink();
//            cowModelData.birthTime = enhancedCow.getBirthTime();
//            cowModelData.clientGameTime = (((ClientLevel)enhancedCow.level).getLevelData()).getGameTime();
//            int collarSlot = hasCollar(enhancedCow.getEnhancedInventory());
//            cowModelData.collar = collarSlot!=0;
//            cowModelData.saddle = collarSlot!=1 ? enhancedCow.getEnhancedInventory().getItem(1) : ItemStack.EMPTY;
//            cowModelData.bridle = !enhancedCow.getEnhancedInventory().getItem(3).isEmpty() && collarSlot!=3;
//            cowModelData.harness = !enhancedCow.getEnhancedInventory().getItem(5).isEmpty() && collarSlot!=5;
//            cowModelData.hasChest = !enhancedCow.getEnhancedInventory().getItem(0).isEmpty();
//            cowModelData.adultAge = EanimodCommonConfig.COMMON.adultAgeCow.get();
//
//            if(cowModelData.phenotype != null) {
//                cowModelDataCache.put(enhancedCow.getId(), cowModelData);
//            }
//
//            return cowModelData;
//        }
//    }
//
//    private int hasCollar(SimpleContainer inventory) {
//        for (int i = 1; i < 6; i++) {
//            if (inventory.getItem(i).getItem() instanceof CustomizableCollar) {
//                return i;
//            }
//        }
//        return 0;
//    }


    private enum HornType {
        HORNED (-1.0F),
        SCURED (-0.75F),
        POLLED (0.0F);
        private float placement;

        HornType(float placement) {
            this.placement = placement;
        }

        public float getPlacement(){
            return this.placement;
        }
    }
}
