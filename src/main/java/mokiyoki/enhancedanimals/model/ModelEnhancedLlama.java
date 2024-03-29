package mokiyoki.enhancedanimals.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import mokiyoki.enhancedanimals.entity.EnhancedLlama;
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
public class ModelEnhancedLlama<T extends EnhancedLlama> extends EnhancedAnimalModel<T> {
    private static final List<Float> SADDLE_SCALE = ModelHelper.createScalings(0.875F, 0.875F, 0.875F, 0.0F, -0.00875F, -0.005F);
    private static final List<Float>[] COLLAR_SCALE = new List[] {
            ModelHelper.createScalings(1.0F, 1.0F, 0.9F, 0.0F, 0.0F, 0.005F),
            ModelHelper.createScalings(1.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F),
            ModelHelper.createScalings(1.125F, 1.0F, 1.125F, 0.0F, 0.0F, -0.00625F),
            ModelHelper.createScalings(1.125F, 1.0F, 1.25F, 0.0F, 0.0F, -0.0125F),
            ModelHelper.createScalings(1.25F, 1.0F, 1.3F, 0.0F, 0.0F, -0.015F)
    };
    private static final List<Float>[] COLLAR_HARDWARE_SCALE = new List[] {
            ModelHelper.createScalings(1.0F, 1.0F, 1.111111F, 0.0F, 0.0F, 0.02F),
            ModelHelper.createScalings(1.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F),
            ModelHelper.createScalings(0.888888F, 1.0F, 0.892857F, 0.0F, 0.0F, -0.02F),
            ModelHelper.createScalings(0.888888F, 1.0F, 0.869565F, 0.0F, 0.0F, -0.029F),
            ModelHelper.createScalings(0.8F, 1.0F, 0.769231F, 0.0F, 0.0F, -0.0565F)
    };
    protected WrappedModelPart theLlama;

    protected WrappedModelPart theHead;
    protected WrappedModelPart theEarLeft;
    protected WrappedModelPart theEarRight;
    protected WrappedModelPart theNeck;
    protected WrappedModelPart theBody;
    protected WrappedModelPart theLegFrontLeft;
    protected WrappedModelPart theLegBottomFrontLeft;
    protected WrappedModelPart theToeFrontLeft;
    protected WrappedModelPart theLegFrontRight;
    protected WrappedModelPart theLegBottomFrontRight;
    protected WrappedModelPart theToeFrontRight;
    protected WrappedModelPart theLegBackLeft;
    protected WrappedModelPart theLegBottomBackLeft;
    protected WrappedModelPart theToeBackLeft;
    protected WrappedModelPart theLegBackRight;
    protected WrappedModelPart theLegBottomBackRight;
    protected WrappedModelPart theToeBackRight;
    protected WrappedModelPart theTail;

    protected WrappedModelPart head;
    protected WrappedModelPart nose;
    protected WrappedModelPart jaw;

    protected WrappedModelPart earLeft;
    protected WrappedModelPart earTopLeft;

    protected WrappedModelPart earRight;
    protected WrappedModelPart earTopRight;

    protected WrappedModelPart neck;
    protected WrappedModelPart neckWool1;
    protected WrappedModelPart neckWool2;
    protected WrappedModelPart neckWool3;
    protected WrappedModelPart neckWool4;
    protected WrappedModelPart neckWool5;

    protected WrappedModelPart body;
    protected WrappedModelPart bodyWool1;
    protected WrappedModelPart bodyWool2;
    protected WrappedModelPart bodyWool3;
    protected WrappedModelPart bodyWool4;
    protected WrappedModelPart bodyWool5;

    private WrappedModelPart legFrontLeft;
    private WrappedModelPart legFrontRight;
    private WrappedModelPart legBackLeft;
    private WrappedModelPart legBackRight;

    private WrappedModelPart legBottomFrontLeft;
    private WrappedModelPart legBottomFrontRight;
    private WrappedModelPart legBottomBackLeft;
    private WrappedModelPart legBottomBackRight;

    private WrappedModelPart legWool1FrontLeft;
    private WrappedModelPart legWool1FrontRight;
    private WrappedModelPart legWool1BackLeft;
    private WrappedModelPart legWool1BackRight;
    private WrappedModelPart legWool2FrontLeft;
    private WrappedModelPart legWool2FrontRight;
    private WrappedModelPart legWool2BackLeft;
    private WrappedModelPart legWool2BackRight;
    private WrappedModelPart legWool3FrontLeft;
    private WrappedModelPart legWool3FrontRight;
    private WrappedModelPart legWool3BackLeft;
    private WrappedModelPart legWool3BackRight;
    private WrappedModelPart legWool4FrontLeft;
    private WrappedModelPart legWool4FrontRight;
    private WrappedModelPart legWool4BackLeft;
    private WrappedModelPart legWool4BackRight;

    private WrappedModelPart toeFrontLeftInner;
    private WrappedModelPart toeFrontLeftOuter;
    private WrappedModelPart toeFrontRightInner;
    private WrappedModelPart toeFrontRightOuter;
    private WrappedModelPart toeBackLeftInner;
    private WrappedModelPart toeBackLeftOuter;
    private WrappedModelPart toeBackRightInner;
    private WrappedModelPart toeBackRightOuter;

    private WrappedModelPart tail0;
    private WrappedModelPart tail1;
    private WrappedModelPart tail2;
    private WrappedModelPart tail3;
    private WrappedModelPart tail4;

    private WrappedModelPart blanketH;
    private WrappedModelPart blanket0;
    private WrappedModelPart blanket1;
    private WrappedModelPart blanket2;
    private WrappedModelPart blanket3;
    private WrappedModelPart blanket4;
    private WrappedModelPart blanket5;

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition base = meshdefinition.getRoot().addOrReplaceChild("base", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition bLlama = base.addOrReplaceChild("bLlama", CubeListBuilder.create(), PartPose.offset(0.0F, 12.0F, -6.0F));
        PartDefinition bBody = bLlama.addOrReplaceChild("bBody", CubeListBuilder.create(), PartPose.offset(0.0F, -10.0F, 0.0F));
        PartDefinition bNeck = bBody.addOrReplaceChild("bNeck", CubeListBuilder.create(), PartPose.offset(0.0F, 3.0F, -1.0F));
        PartDefinition bHead = bNeck.addOrReplaceChild("bHead", CubeListBuilder.create(), PartPose.offset(0.0F, -8.0F, 0.0F));
        PartDefinition bEarLeft = bHead.addOrReplaceChild("bEarL", CubeListBuilder.create(), PartPose.offset(-1.0F, -6.0F, 1.0F));
        PartDefinition bEarRight = bHead.addOrReplaceChild("bEarR", CubeListBuilder.create(), PartPose.offset(1.0F, -6.0F, 1.0F));
        PartDefinition bLegFrontLeft = bLlama.addOrReplaceChild("bLegFL", CubeListBuilder.create(), PartPose.offset(3.0F, 0.0F, 3.0F));
            bLlama.addOrReplaceChild("bLegBFL", CubeListBuilder.create(), PartPose.offset(0.0F, 6.0F, -3.0F));
        PartDefinition bLegFrontRight = bLlama.addOrReplaceChild( "bLegFR", CubeListBuilder.create(), PartPose.offset(-6.0F, 0.0F, 3.0F));
            bLlama.addOrReplaceChild( "bLegBFR", CubeListBuilder.create(), PartPose.offset(0.0F, 6.0F, -3.0F));
        PartDefinition bLegBackLeft = bLlama.addOrReplaceChild("bLegBL", CubeListBuilder.create(), PartPose.offset(3.0F, 0.0F, 15.0F));
            bLlama.addOrReplaceChild("bLegBBL", CubeListBuilder.create(), PartPose.offset(0.0F, 6.0F, 0.0F));
        PartDefinition bLegBackRight = bLlama.addOrReplaceChild("bLegBR", CubeListBuilder.create(), PartPose.offset(-6.0F, 0.0F, 15.0F));
            bLlama.addOrReplaceChild("bLegBBR", CubeListBuilder.create(), PartPose.offset(0.0F, 6.0F, 0.0F));
        PartDefinition bToeFrontLeft = bLegFrontLeft.addOrReplaceChild("bToeFL", CubeListBuilder.create(), PartPose.offset(1.5F, 4.0F, 2.0F));
        PartDefinition bToeFrontRight = bLegFrontRight.addOrReplaceChild("bToeFR", CubeListBuilder.create(), PartPose.offset(1.5F, 4.0F, 2.0F));
        PartDefinition bToeBackLeft = bLegBackLeft.addOrReplaceChild("bToeBL", CubeListBuilder.create(), PartPose.offset(1.5F, 4.0F, 2.0F));
        PartDefinition bToeBackRight = bLegBackRight.addOrReplaceChild("bToeBR", CubeListBuilder.create(), PartPose.offset(1.5F, 4.0F, 2.0F));
        PartDefinition bTail = bBody.addOrReplaceChild("bTail", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 16.0F));

        bHead.addOrReplaceChild("eyes", CubeListBuilder.create()
                        .texOffs(22, 3)
                        .addBox(2.0F, -1.0F, -6.0F, 2, 2, 1, new CubeDeformation(0.01F))
                        .texOffs(0, 3)
                        .addBox(-4.0F, -1.0F, -6.0F, 2, 2, 1, new CubeDeformation(0.01F)),
                PartPose.offset(0.0F, -4.0F, 3.0F)
        );
        bHead.addOrReplaceChild("head", CubeListBuilder.create()
                        .texOffs(0, 0)
                        .addBox(-4.0F, -6.0F, -3.0F, 8, 6, 6),
                PartPose.ZERO
        );
        bHead.addOrReplaceChild("nose", CubeListBuilder.create()
                        .texOffs(28, 0)
                        .addBox(-2.0F, -4.0F, -7.0F, 4, 4, 4),
                PartPose.ZERO
        );

        bEarLeft.addOrReplaceChild("earL", CubeListBuilder.create()
                        .texOffs(54, 0)
                        .addBox(-3.0F, -3.0F, -2.0F, 3, 3, 2),
                PartPose.ZERO
        );
        bEarLeft.addOrReplaceChild("earTL", CubeListBuilder.create()
                        .texOffs(74, 0)
                        .addBox(-3.0F, -1.0F, -2.0F, 3, 1, 2),
                PartPose.offset(0.0F, -3.0F, 0.0F)
        );

        bEarRight.addOrReplaceChild("earR", CubeListBuilder.create()
                        .texOffs(44, 0)
                        .addBox(0.0F, -3.0F, -2.0F, 3, 3, 2),
                PartPose.ZERO
        );
        bEarRight.addOrReplaceChild("earTR", CubeListBuilder.create()
                        .texOffs(64, 0)
                        .addBox(0.0F, -1.0F, -2.0F, 3, 1, 2),
                PartPose.offset(0.0F, -3.0F, 0.0F)
        );

        bNeck.addOrReplaceChild("neck0", CubeListBuilder.create()
                        .texOffs(0, 12)
                        .addBox(-4.0F, -9.0F, -1.1F, 8, 12, 6, new CubeDeformation(-1.0F)),
                PartPose.ZERO
        );
        bNeck.addOrReplaceChild("neck1", CubeListBuilder.create()
                        .texOffs(0, 12)
                        .addBox(-4.0F, -8.0F, -2.0F, 8, 12, 6),
                PartPose.ZERO
        );
        bNeck.addOrReplaceChild("neck2", CubeListBuilder.create()
                        .texOffs(0, 12)
                        .addBox(-4.0F, -8.5F, -2.0F, 8, 12, 6, new CubeDeformation(0.5F)),
                PartPose.ZERO
        );
        bNeck.addOrReplaceChild("neck3", CubeListBuilder.create()
                        .texOffs(0, 12)
                        .addBox(-4.0F, -7.5F, -2.0F, 8, 12, 6, new CubeDeformation(1.0F)),
                PartPose.ZERO
        );
        bNeck.addOrReplaceChild("neck4", CubeListBuilder.create()
                        .texOffs(0, 12)
                        .addBox(-4.0F, -7.0F, -2.0F, 8, 12, 6, new CubeDeformation(1.5F)),
                PartPose.ZERO
        );
        bNeck.addOrReplaceChild("neck5", CubeListBuilder.create()
                        .texOffs(0, 12)
                        .addBox(-4.0F, -6.5F, -2.0F, 8, 12, 6, new CubeDeformation(2.0F)),
                PartPose.ZERO
        );

        bBody.addOrReplaceChild("body0", CubeListBuilder.create()
                        .texOffs(0, 39)
                        .addBox(-6.0F, 1.0F, 0.0F, 12, 10, 18, new CubeDeformation(-1.0F)),
                PartPose.ZERO
        );
        bBody.addOrReplaceChild("body1", CubeListBuilder.create()
                        .texOffs(0, 39)
                        .addBox(-6.0F, 0.0F, 0.0F, 12, 10, 18),
                PartPose.ZERO
        );
        bBody.addOrReplaceChild("body2", CubeListBuilder.create()
                        .texOffs(0, 39)
                        .addBox(-6.0F, 0.0F, 0.0F, 12, 10, 18, new CubeDeformation(0.501F)),
                PartPose.ZERO
        );
        bBody.addOrReplaceChild("body3", CubeListBuilder.create()
                        .texOffs(0, 39)
                        .addBox(-6.0F, 0.0F, 0.0F, 12, 10, 18, new CubeDeformation(1.001F)),
                PartPose.ZERO
        );
        bBody.addOrReplaceChild("body4", CubeListBuilder.create()
                        .texOffs(0, 39)
                        .addBox(-6.0F, 0.0F, 0.0F, 12, 10, 18, new CubeDeformation(1.501F)),
                PartPose.ZERO
        );
        bBody.addOrReplaceChild("body5", CubeListBuilder.create()
                        .texOffs(0, 39)
                        .addBox(-6.0F, 0.0F, 0.0F, 12, 10, 18, new CubeDeformation(2.001F)),
                PartPose.ZERO
        );

        bLegFrontLeft.addOrReplaceChild("legFL", CubeListBuilder.create()
                        .texOffs(0, 68)
                        .addBox(0.0F, 0.0F, -3.0F, 3, 6, 3, new CubeDeformation(0.01F)),
                PartPose.ZERO
        );
        bLegFrontLeft.addOrReplaceChild("legBFL", CubeListBuilder.create()
                        .texOffs(0, 74)
                        .addBox(0.0F, 0.0F, 0.0F, 3, 5, 3),
                PartPose.ZERO
        );
        bLegFrontLeft.addOrReplaceChild("legFL1", CubeListBuilder.create()
                        .texOffs(0, 68)
                        .addBox(0.0F, 0.0F, 0.0F, 3, 7, 3, new CubeDeformation(0.5F)),
                PartPose.ZERO
        );
        bLegFrontLeft.addOrReplaceChild("legFL2", CubeListBuilder.create()
                        .texOffs(0, 68)
                        .addBox(0.0F, 0.0F, 0.0F, 3, 7, 3, new CubeDeformation(1.0F)),
                PartPose.ZERO
        );
        bLegFrontLeft.addOrReplaceChild("legFL3", CubeListBuilder.create()
                        .texOffs(0, 68)
                        .addBox(0.0F, 0.0F, -3.0F, 3, 7, 3, new CubeDeformation(1.5F)),
                PartPose.ZERO
        );
        bLegFrontLeft.addOrReplaceChild("legFL4", CubeListBuilder.create()
                        .texOffs(0, 68)
                        .addBox(0.0F, 0.0F, -3.0F, 3, 7, 3, new CubeDeformation(2.0F)),
                PartPose.ZERO
        );

        bLegFrontRight.addOrReplaceChild("legFR", CubeListBuilder.create()
                        .texOffs(12, 68)
                        .addBox(0.0F, 0.0F, -3.0F, 3, 6, 3, new CubeDeformation(0.01F)),
                PartPose.ZERO
        );
        bLegFrontRight.addOrReplaceChild("legBFR", CubeListBuilder.create()
                        .texOffs(12, 74)
                        .addBox(0.0F, 0.0F, 0.0F, 3, 5, 3),
                PartPose.ZERO
        );
        bLegFrontRight.addOrReplaceChild("legFR1", CubeListBuilder.create()
                        .texOffs(12, 68)
                        .addBox(0.0F, 0.0F, 0.0F, 3, 7, 3, new CubeDeformation(0.5F)),
                PartPose.ZERO
        );
        bLegFrontRight.addOrReplaceChild("legFR2", CubeListBuilder.create()
                        .texOffs(12, 68)
                        .addBox(0.0F, 0.0F, -3.0F, 3, 7, 3, new CubeDeformation(1.0F)),
                PartPose.ZERO
        );
        bLegFrontRight.addOrReplaceChild("legFR3", CubeListBuilder.create()
                        .texOffs(12, 68)
                        .addBox(0.0F, 0.0F, -3.0F, 3, 7, 3, new CubeDeformation(1.5F)),
                PartPose.ZERO
        );
        bLegFrontRight.addOrReplaceChild("legFR4", CubeListBuilder.create()
                        .texOffs(12, 68)
                        .addBox(0.0F, 0.0F, -3.0F, 3, 7, 3, new CubeDeformation(2.0F)),
                PartPose.ZERO
        );

        bLegBackLeft.addOrReplaceChild("legBL", CubeListBuilder.create()
                        .texOffs(0, 82)
                        .addBox(0.0F, 0.0F, 0.0F, 3, 6, 3, new CubeDeformation(0.01F)),
                PartPose.ZERO
        );
        bLegBackLeft.addOrReplaceChild("legBBL", CubeListBuilder.create()
                        .texOffs(0, 88)
                        .addBox(0.0F, 0.0F, 0.0F, 3, 5, 3),
                PartPose.ZERO
        );
        bLegBackLeft.addOrReplaceChild("legBL1", CubeListBuilder.create()
                        .texOffs(0, 82)
                        .addBox(0.0F, 0.0F, 0.0F, 3, 7, 3, new CubeDeformation(0.5F)),
                PartPose.ZERO
        );
        bLegBackLeft.addOrReplaceChild("legBL2", CubeListBuilder.create()
                        .texOffs(0, 82)
                        .addBox(0.0F, 0.0F, 0.0F, 3, 7, 3, new CubeDeformation(1.0F)),
                PartPose.ZERO
        );
        bLegBackLeft.addOrReplaceChild("legBL3", CubeListBuilder.create()
                        .texOffs(0, 82)
                        .addBox(0.0F, 0.0F, 0.0F, 3, 7, 3, new CubeDeformation(1.5F)),
                PartPose.ZERO
        );
        bLegBackLeft.addOrReplaceChild("legBL4", CubeListBuilder.create()
                        .texOffs(0, 82)
                        .addBox(0.0F, 0.0F, 0.0F, 3, 7, 3, new CubeDeformation(2.0F)),
                PartPose.ZERO
        );

        bLegBackRight.addOrReplaceChild("legBR", CubeListBuilder.create()
                        .texOffs(12, 82)
                        .addBox(0.0F, 0.0F, 0.0F, 3, 6, 3, new CubeDeformation(0.01F)),
                PartPose.ZERO
        );
        bLegBackRight.addOrReplaceChild("legBBR", CubeListBuilder.create()
                        .texOffs(12, 88)
                        .addBox(0.0F, 0.0F, 0.0F, 3, 5, 3),
                PartPose.ZERO
        );
        bLegBackRight.addOrReplaceChild("legBR1", CubeListBuilder.create()
                        .texOffs(12, 82)
                        .addBox(0.0F, 0.0F, 0.0F, 3, 7, 3, new CubeDeformation(0.5F)),
                PartPose.ZERO
        );
        bLegBackRight.addOrReplaceChild("legBR2", CubeListBuilder.create()
                        .texOffs(12, 82)
                        .addBox(0.0F, 0.0F, 0.0F, 3, 7, 3, new CubeDeformation(1.0F)),
                PartPose.ZERO
        );
        bLegBackRight.addOrReplaceChild("legBR3", CubeListBuilder.create()
                        .texOffs(12, 82)
                        .addBox(0.0F, 0.0F, 0.0F, 3, 7, 3, new CubeDeformation(1.5F)),
                PartPose.ZERO
        );
        bLegBackRight.addOrReplaceChild("legBR4", CubeListBuilder.create()
                        .texOffs(12, 82)
                        .addBox(0.0F, 0.0F, 0.0F, 3, 7, 3, new CubeDeformation(2.0F)),
                PartPose.ZERO
        );

        bToeFrontLeft.addOrReplaceChild("toeOFL", CubeListBuilder.create()
                        .texOffs(62, 70)
                        .addBox(-3.0F, 0.0F, -4.0F, 3, 3, 4, new CubeDeformation(-0.75F)),
                PartPose.offset(0.5F, 0.0F, 0.0F)
        );
        bToeFrontLeft.addOrReplaceChild("toeIFL", CubeListBuilder.create()
                        .texOffs(80, 70)
                        .addBox(0.0F, 0.0F, -4.0F, 3, 3, 4, new CubeDeformation(-0.75F)),
                PartPose.offset(-0.5F, 0.0F, 0.0F)
        );

        bToeFrontRight.addOrReplaceChild("toeOFR", CubeListBuilder.create()
                        .texOffs(26, 70)
                        .addBox(0.0F, 0.0F, -4.0F, 3, 3, 4, new CubeDeformation(-0.75F)),
                PartPose.offset(-0.5F, 0.0F, 0.0F)
        );
        bToeFrontRight.addOrReplaceChild("toeIFR", CubeListBuilder.create()
                        .texOffs(44, 70)
                        .addBox(-3.0F, 0.0F, -4.0F, 3, 3, 4, new CubeDeformation(-0.75F)),
                PartPose.offset(0.5F, 0.0F, 0.0F)
        );

        bToeBackLeft.addOrReplaceChild("toeOBL", CubeListBuilder.create()
                        .texOffs(62, 84)
                        .addBox(-3.0F, 0.0F, -4.0F, 3, 3, 4, new CubeDeformation(-0.75F)),
                PartPose.offset(0.5F, 0.0F, 0.0F)
        );
        bToeBackLeft.addOrReplaceChild("toeIBL", CubeListBuilder.create()
                        .texOffs(80, 84)
                        .addBox(0.0F, 0.0F, -4.0F, 3, 3, 4, new CubeDeformation(-0.75F)),
                PartPose.offset(-0.5F, 0.0F, 0.0F)
        );

        bToeBackRight.addOrReplaceChild("toeOBR", CubeListBuilder.create()
                        .texOffs(26, 84)
                        .addBox(0.0F, 0.0F, -4.0F, 3, 3, 4, new CubeDeformation(-0.75F)),
                PartPose.offset(-0.5F, 0.0F, 0.0F)
        );
        bToeBackRight.addOrReplaceChild("toeIBR", CubeListBuilder.create()
                        .texOffs(44, 84)
                        .addBox(-3.0F, 0.0F, -4.0F, 3, 3, 4, new CubeDeformation(-0.75F)),
                PartPose.offset(0.5F, 0.0F, 0.0F)
        );

        bTail.addOrReplaceChild("tail0", CubeListBuilder.create()
                        .texOffs(42, 39)
                        .addBox(-3.0F, -2.0F, 0.0F, 6, 6, 6),
                PartPose.ZERO
        );
        bTail.addOrReplaceChild("tail1", CubeListBuilder.create()
                        .texOffs(42, 39)
                        .addBox(-3.0F, -1.75F, 0.25F, 6, 6, 6, new CubeDeformation(0.25F)),
                PartPose.ZERO
        );
        bTail.addOrReplaceChild("tail2", CubeListBuilder.create()
                        .texOffs(42, 39)
                        .addBox(-3.0F, -1.5F, 0.5F, 6, 6, 6, new CubeDeformation(0.5F)),
                PartPose.ZERO
        );
        bTail.addOrReplaceChild("tail3", CubeListBuilder.create()
                        .texOffs(42, 39)
                        .addBox(-3.0F, -1.25F, 0.75F, 6, 6, 6, new CubeDeformation(0.75F)),
                PartPose.ZERO
        );
        bTail.addOrReplaceChild("tail4", CubeListBuilder.create()
                        .texOffs(42, 39)
                        .addBox(-3.0F, -0.75F, 1.25F, 6, 6, 6, new CubeDeformation(1.25F)),
                PartPose.ZERO
        );


        /**
         * Equipment stuff
         */

        PartDefinition bBlanket = base.addOrReplaceChild("bBlanket", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        bBlanket.addOrReplaceChild("blanketH", CubeListBuilder.create()
                        .texOffs(28, 8)
                        .addBox(-4.0F, -6.0F, -3.0F, 8, 10, 6, new CubeDeformation(0.505F)),
                PartPose.ZERO
        );
        bBlanket.addOrReplaceChild("blanket0", CubeListBuilder.create()
                        .texOffs(28, 23)
                        .addBox(-4.0F, -2.0F, -1.1F, 8, 10, 6, new CubeDeformation(0.01F)),
                PartPose.ZERO
        );
        bBlanket.addOrReplaceChild("blanket1", CubeListBuilder.create()
                        .texOffs(28, 23)
                        .addBox(-4.0F, -3.75F, -2.0F, 8, 10, 6, new CubeDeformation(0.51F)),
                PartPose.ZERO
        );
        bBlanket.addOrReplaceChild("blanket2", CubeListBuilder.create()
                        .texOffs(28, 23)
                        .addBox(-4.0F, -2.25F, -2.0F, 8, 10, 6, new CubeDeformation(0.75F)),
                PartPose.ZERO
        );
        bBlanket.addOrReplaceChild("blanket3", CubeListBuilder.create()
                        .texOffs(28, 23)
                        .addBox(-4.0F, -3.7F, -2.0F, 8, 10, 6, new CubeDeformation(1.2F)),
                PartPose.ZERO
        );
        bBlanket.addOrReplaceChild("blanket4", CubeListBuilder.create()
                        .texOffs(28, 23)
                        .addBox(-4.0F, -3.1F, -2.0F, 8, 10, 6, new CubeDeformation(1.6F)),
                PartPose.ZERO
        );
        bBlanket.addOrReplaceChild("blanket5", CubeListBuilder.create()
                        .texOffs(28,23)
                        .addBox(-4.0F, -2.55F, -2.0F, 8, 10, 6, new CubeDeformation(2.05F)),
                PartPose.ZERO
        );

        base.addOrReplaceChild("chestL", CubeListBuilder.create()
                        .texOffs(74, 44)
                        .addBox(0.0F, 0.0F, 0.0F, 8, 8, 3),
                PartPose.offsetAndRotation(-8.0F, -8.0F, 16.0F, 0.0F, Mth.HALF_PI, 0.0F)
        );
        base.addOrReplaceChild("chestR", CubeListBuilder.create()
                        .texOffs(74, 57)
                        .addBox(0.0F, 0.0F, -3.0F, 8, 8, 3),
                PartPose.offset(0.0F, 0.0F, 16.0F)
        );

        base.addOrReplaceChild("collar", CubeListBuilder.create()
                        .texOffs(88, 84)
                        .addBox(-5.0F, -4.5F, -3.0F, 10, 2, 8),
                PartPose.ZERO
        );
        base.addOrReplaceChild("collarH", CubeListBuilder.create()
                        .texOffs(127, 88)
                        .addBox(0.0F, -5.0F, -5.0F, 0, 3, 3)
                        .texOffs(116, 84)
                        .addBox(-1.5F, -2.5F, -5.75F, 3, 3, 3),
                PartPose.ZERO
        );
        base.addOrReplaceChild("bridle", CubeListBuilder.create()
                        .texOffs(0, 112)
                        .addBox(-8.0F, -9.05F, -6.0F, 16, 12, 12, new CubeDeformation(-3.8F, -2.8F, -2.8F)),
                PartPose.ZERO
        );
        base.addOrReplaceChild("bridleN", CubeListBuilder.create()
                        .texOffs(0, 96)
                        .addBox(-4.0F, -6.0F, -9.0F, 8, 8, 8, new CubeDeformation(-1.8F)),
                PartPose.ZERO
        );

        base.addOrReplaceChild("saddle", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 8.0F));
        base.addOrReplaceChild("saddleW", CubeListBuilder.create()
                        .texOffs(146, 0)
                        .addBox(-5.0F, -2.0F, -5.0F, 10, 2, 13)
                        .texOffs(146, 15)
                        .addBox(-4.0F, -3.0F, 5.0F, 8, 2, 4)
                        .texOffs(222, 15)
                        .addBox(-3.5F, -4.0F, 8.0F, 7, 2, 2),
                PartPose.offset(0.0F, 0.0F, 8.0F)
        );
        base.addOrReplaceChild("saddleE", CubeListBuilder.create()
                        .texOffs(147, 1)
                        .addBox(-5.0F, -1.0F, -4.0F, 10, 2, 12)
                        .texOffs(146, 15)
                        .addBox(-4.0F, -1.5F, 5.0F, 8, 2, 4)
                        .texOffs(222, 15)
                        .addBox(-3.5F, -2.0F, 7.5F, 7, 2, 2),
                PartPose.offset(0.0F, 0.0F, 8.0F)
        );
        base.addOrReplaceChild("saddleH", CubeListBuilder.create()
                        .texOffs(170, 19)
                        .addBox(-4.0F, -2.0F, -3.0F, 8, 2, 3),
                PartPose.ZERO
        );
        base.addOrReplaceChild("saddleP", CubeListBuilder.create()
                        .texOffs(179, 0)
                        .addBox(-1.0F, -3.0F, -2.0F, 2, 4, 2, new CubeDeformation(-0.25F)),
                PartPose.offsetAndRotation(0.0F, -1.5F, -0.5F, -0.2F, 0.0F, 0.0F)
        );
        base.addOrReplaceChild("saddleL", CubeListBuilder.create()
                        .texOffs(170, 49)
                        .addBox(0.0F, 0.0F, 0.0F, 3, 4, 8),
                PartPose.ZERO
        );
        base.addOrReplaceChild("saddleR", CubeListBuilder.create()
                        .texOffs(170, 61)
                        .addBox(-3.0F, 0.0F, 0.0F, 3, 4, 8),
                PartPose.ZERO
        );
        base.addOrReplaceChild("stirrupWL", CubeListBuilder.create()
                        .texOffs(184, 24)
                        .addBox(0.0F, 0.0F, 0.0F, 0, 10, 4),
                PartPose.offset(0.0F, 0.0F, -3.5F)
        );
        base.addOrReplaceChild("stirrupWR", CubeListBuilder.create()
                        .texOffs(184, 24)
                        .addBox(0.0F, 0.0F, 0.0F, 0, 10, 4),
                PartPose.offset(0.0F, 0.0F, -3.5F)
        );
        base.addOrReplaceChild("stirrupNL", CubeListBuilder.create()
                        .texOffs(185, 27)
                        .addBox(-1.0F, 0.0F, 0.0F, 1, 10, 1),
                PartPose.ZERO
        );
        base.addOrReplaceChild("stirrupNR", CubeListBuilder.create()
                        .texOffs(187, 27)
                        .addBox(0.0F, 0.0F, 0.0F, 1, 10, 1),
                PartPose.ZERO
        );
        base.addOrReplaceChild("stirrup", CubeListBuilder.create()
                        .texOffs(146, 0)
                        .addBox(-0.5F, 9.5F, -1.0F, 1, 1, 1)
                        .texOffs(150, 0)
                        .addBox(-0.5F, 9.5F, 1.0F, 1, 1, 1)
                        .texOffs(146, 2)
                        .addBox(-0.5F, 10.5F, -1.5F, 1, 3, 1)
                        .texOffs(150, 2)
                        .addBox(-0.5F, 10.5F, 1.5F, 1, 3, 1)
                        .texOffs(147, 7)
                        .addBox(-0.5F, 12.5F, -0.5F, 1, 1, 2),
                PartPose.ZERO
        );
        base.addOrReplaceChild("saddlePad", CubeListBuilder.create()
                        .texOffs(130, 24)
                        .addBox(-8.0F, -1.0F, -6.0F, 16, 10, 15, new CubeDeformation(-1.0F)),
                PartPose.ZERO
        );

        return LayerDefinition.create(meshdefinition, 256, 256);
    }

    public ModelEnhancedLlama(ModelPart modelPart) {
        super(modelPart);
        ModelPart base = modelPart.getChild("base");
        ModelPart bLlama = base.getChild("bLlama");
        ModelPart bBody = bLlama.getChild("bBody");
        ModelPart bNeck = bBody.getChild("bNeck");
        ModelPart bHead = bNeck.getChild("bHead");
        ModelPart bEarLeft = bHead.getChild("bEarL");
        ModelPart bEarRight = bHead.getChild("bEarR");
        ModelPart bLegFL = bLlama.getChild("bLegFL");
        ModelPart bLegFR = bLlama.getChild("bLegFR");
        ModelPart bLegBL = bLlama.getChild("bLegBL");
        ModelPart bLegBR = bLlama.getChild("bLegBR");
        ModelPart bToeBL = bLegBL.getChild("bToeBL");
        ModelPart bToeFL = bLegFL.getChild("bToeFL");
        ModelPart bToeFR = bLegFR.getChild("bToeFR");
        ModelPart bToeBR = bLegBR.getChild("bToeBR");
        ModelPart bTail = bBody.getChild("bTail");

        this.theLlama = new WrappedModelPart(bLlama, "bLlama");
        this.theBody = new WrappedModelPart(bBody, "bBody");
        this.theNeck = new WrappedModelPart(bNeck, "bNeck");
        this.theHead = new WrappedModelPart(bHead, "bHead");
        this.theEarLeft = new WrappedModelPart(bEarLeft, "bEarL");
        this.theEarRight = new WrappedModelPart(bEarRight, "bEarR");
        this.theLegFrontLeft = new WrappedModelPart(bLegFL, "bLegFL");
        this.theLegBottomFrontLeft = new WrappedModelPart("bLegBFL", bLlama);
        this.theLegBottomFrontRight = new WrappedModelPart("bLegBFR", bLlama);
        this.theLegBottomBackLeft = new WrappedModelPart("bLegBBL", bLlama);
        this.theLegBottomBackRight = new WrappedModelPart("bLegBBR", bLlama);
        this.theLegFrontRight = new WrappedModelPart(bLegFR, "bLegFR");
        this.theLegBackLeft = new WrappedModelPart(bLegBL, "bLegBL");
        this.theLegBackRight = new WrappedModelPart(bLegBR, "bLegBR");
        this.theToeFrontLeft = new WrappedModelPart(bToeFL, "bToeFL");
        this.theToeFrontRight = new WrappedModelPart(bToeFR, "bToeFR");
        this.theToeBackLeft = new WrappedModelPart(bToeBL, "bToeBL");
        this.theToeBackRight = new WrappedModelPart(bToeBR, "bToeBR");
        this.theTail = new WrappedModelPart(bTail, "bTail");

        this.eyes = new WrappedModelPart("eyes", bHead);
        this.head = new WrappedModelPart("head", bHead);
        this.nose = new WrappedModelPart("nose", bHead);

        this.earLeft = new WrappedModelPart("earL", bEarLeft);
        this.earTopLeft = new WrappedModelPart("earTL", bEarLeft);

        this.earRight = new WrappedModelPart("earR", bEarRight);
        this.earTopRight = new WrappedModelPart("earTR", bEarRight);

        this.neck = new WrappedModelPart("neck0", bNeck);
        this.neckWool1 = new WrappedModelPart("neck1", bNeck);
        this.neckWool2 = new WrappedModelPart("neck2", bNeck);
        this.neckWool3 = new WrappedModelPart("neck3", bNeck);
        this.neckWool4 = new WrappedModelPart("neck4", bNeck);
        this.neckWool5 = new WrappedModelPart("neck5", bNeck);

        this.body = new WrappedModelPart("body0", bBody);
        this.bodyWool1 = new WrappedModelPart("body1", bBody);
        this.bodyWool2 = new WrappedModelPart("body2", bBody);
        this.bodyWool3 = new WrappedModelPart("body3", bBody);
        this.bodyWool4 = new WrappedModelPart("body4", bBody);
        this.bodyWool5 = new WrappedModelPart("body5", bBody);

        this.legFrontLeft = new WrappedModelPart("legFL", bLegFL);
        this.legWool1FrontLeft = new WrappedModelPart("legFL1", bLegFL);
        this.legWool2FrontLeft = new WrappedModelPart("legFL2", bLegFL);
        this.legWool3FrontLeft = new WrappedModelPart("legFL3", bLegFL);
        this.legWool4FrontLeft = new WrappedModelPart("legFL4", bLegFL);
        this.legFrontRight = new WrappedModelPart("legFR", bLegFR);
        this.legWool1FrontRight = new WrappedModelPart("legFR1", bLegFR);
        this.legWool2FrontRight = new WrappedModelPart("legFR2", bLegFR);
        this.legWool3FrontRight = new WrappedModelPart("legFR3", bLegFR);
        this.legWool4FrontRight = new WrappedModelPart("legFR4", bLegFR);
        this.legBackLeft = new WrappedModelPart("legBL", bLegBL);
        this.legWool1BackLeft = new WrappedModelPart("legBL1", bLegBL);
        this.legWool2BackLeft = new WrappedModelPart("legBL2", bLegBL);
        this.legWool3BackLeft = new WrappedModelPart("legBL3", bLegBL);
        this.legWool4BackLeft = new WrappedModelPart("legBL4", bLegBL);
        this.legBackRight = new WrappedModelPart("legBR", bLegBR);
        this.legWool1BackRight = new WrappedModelPart("legBR1", bLegBR);
        this.legWool2BackRight = new WrappedModelPart("legBR2", bLegBR);
        this.legWool3BackRight = new WrappedModelPart("legBR3", bLegBR);
        this.legWool4BackRight = new WrappedModelPart("legBR4", bLegBR);

        this.legBottomFrontLeft = new WrappedModelPart("legBFL", bLegFL);
        this.legBottomFrontRight = new WrappedModelPart("legBFR", bLegFR);
        this.legBottomBackLeft = new WrappedModelPart("legBBL", bLegBL);
        this.legBottomBackRight = new WrappedModelPart("legBBR", bLegBR);

        this.toeFrontLeftInner = new WrappedModelPart("toeIFL", bToeFL);
        this.toeFrontLeftOuter = new WrappedModelPart("toeOFL", bToeFL);
        this.toeFrontRightInner = new WrappedModelPart("toeIFR", bToeFR);
        this.toeFrontRightOuter = new WrappedModelPart("toeOFR", bToeFR);
        this.toeBackLeftInner = new WrappedModelPart("toeIBL", bToeBL);
        this.toeBackLeftOuter = new WrappedModelPart("toeOBL", bToeBL);
        this.toeBackRightInner = new WrappedModelPart("toeIBR", bToeBR);
        this.toeBackRightOuter = new WrappedModelPart("toeOBR", bToeBR);

        this.tail0 = new WrappedModelPart("tail0", bTail);
        this.tail1 = new WrappedModelPart("tail1", bTail);
        this.tail2 = new WrappedModelPart("tail2", bTail);
        this.tail3 = new WrappedModelPart("tail3", bTail);
        this.tail4 = new WrappedModelPart("tail4", bTail);

        this.theLlama.addChild(this.theBody);
        this.theBody.addChild(this.theNeck);
        this.theNeck.addChild(this.theHead);
        this.theHead.addChild(this.theEarLeft);
        this.theHead.addChild(this.theEarRight);
        this.theLlama.addChild(this.theLegFrontLeft);
        this.theLlama.addChild(this.theLegFrontRight);
        this.theLlama.addChild(this.theLegBackLeft);
        this.theLlama.addChild(this.theLegBackRight);
        this.theLegFrontLeft.addChild(this.theLegBottomFrontLeft);
        this.theLegFrontRight.addChild(this.theLegBottomFrontRight);
        this.theLegBackLeft.addChild(this.theLegBottomBackLeft);
        this.theLegBackRight.addChild(this.theLegBottomBackRight);
        this.theLegBottomFrontLeft.addChild(this.theToeFrontLeft);
        this.theLegBottomFrontRight.addChild(this.theToeFrontRight);
        this.theLegBottomBackLeft.addChild(this.theToeBackLeft);
        this.theLegBottomBackRight.addChild(this.theToeBackRight);
        this.theBody.addChild(this.theTail);

        this.theHead.addChild(this.head);
        this.theHead.addChild(this.eyes);
        this.theHead.addChild(this.nose);

        this.theEarLeft.addChild(this.earLeft);
        this.earLeft.addChild(this.earTopLeft);
        this.theEarRight.addChild(this.earRight);
        this.earRight.addChild(this.earTopRight);

        this.theNeck.addChild(this.neck);
        this.theNeck.addChild(this.neckWool1);
        this.theNeck.addChild(this.neckWool2);
        this.theNeck.addChild(this.neckWool3);
        this.theNeck.addChild(this.neckWool4);
        this.theNeck.addChild(this.neckWool5);

//        this.theBody.addChild(this.body);
        this.theBody.addChild(this.bodyWool1);
        this.theBody.addChild(this.bodyWool2);
        this.theBody.addChild(this.bodyWool3);
        this.theBody.addChild(this.bodyWool4);
        this.theBody.addChild(this.bodyWool5);

        this.theLegFrontLeft.addChild(this.legFrontLeft);
        this.theLegFrontLeft.addChild(this.legWool1FrontLeft);
        this.theLegFrontLeft.addChild(this.legWool2FrontLeft);
        this.theLegFrontLeft.addChild(this.legWool3FrontLeft);
        this.theLegFrontLeft.addChild(this.legWool4FrontLeft);

        this.theLegFrontRight.addChild(this.legFrontRight);
        this.theLegFrontRight.addChild(this.legWool1FrontRight);
        this.theLegFrontRight.addChild(this.legWool2FrontRight);
        this.theLegFrontRight.addChild(this.legWool3FrontRight);
        this.theLegFrontRight.addChild(this.legWool4FrontRight);

        this.theLegBackLeft.addChild(this.legBackLeft);
        this.theLegBackLeft.addChild(this.legWool1BackLeft);
        this.theLegBackLeft.addChild(this.legWool2BackLeft);
        this.theLegBackLeft.addChild(this.legWool3BackLeft);
        this.theLegBackLeft.addChild(this.legWool4BackLeft);

        this.theLegBackRight.addChild(this.legBackRight);
        this.theLegBackRight.addChild(this.legWool1BackRight);
        this.theLegBackRight.addChild(this.legWool2BackRight);
        this.theLegBackRight.addChild(this.legWool3BackRight);
        this.theLegBackRight.addChild(this.legWool4BackRight);

        this.theLegBottomFrontLeft.addChild(this.legBottomFrontLeft);
        this.theLegBottomFrontRight.addChild(this.legBottomFrontRight);
        this.theLegBottomBackLeft.addChild(this.legBottomBackLeft);
        this.theLegBottomBackRight.addChild(this.legBottomBackRight);

        this.theToeFrontLeft.addChild(this.toeFrontLeftInner);
        this.theToeFrontLeft.addChild(this.toeFrontLeftOuter);
        this.theToeFrontRight.addChild(this.toeFrontRightInner);
        this.theToeFrontRight.addChild(this.toeFrontRightOuter);
        this.theToeBackLeft.addChild(this.toeBackLeftInner);
        this.theToeBackLeft.addChild(this.toeBackLeftOuter);
        this.theToeBackRight.addChild(this.toeBackRightInner);
        this.theToeBackRight.addChild(this.toeBackRightOuter);

        this.theTail.addChild(this.tail0);
        this.theTail.addChild(this.tail1);
        this.theTail.addChild(this.tail2);
        this.theTail.addChild(this.tail3);
        this.theTail.addChild(this.tail4);

        /**
         *      Equipment
         */
        ModelPart bBlanket = base.getChild("bBlanket");

        this.blanket = new WrappedModelPart("bBlanket", base);

        this.blanketH = new WrappedModelPart("blanketH", bBlanket);
        this.blanket0 = new WrappedModelPart("blanket0", bBlanket);
        this.blanket1 = new WrappedModelPart("blanket1", bBlanket);
        this.blanket2 = new WrappedModelPart("blanket2", bBlanket);
        this.blanket3 = new WrappedModelPart("blanket3", bBlanket);
        this.blanket4 = new WrappedModelPart("blanket4", bBlanket);
        this.blanket5 = new WrappedModelPart("blanket5", bBlanket);
        this.blanket.addChild(this.blanket0, this.blanket1, this.blanket2, this.blanket3, this.blanket4, this.blanket5);

        this.chests = new WrappedModelPart("chestL", base);
        this.chestsR = new WrappedModelPart("chestR", base);

        this.collar = new WrappedModelPart("collar", base);
        this.collarHardware = new WrappedModelPart("collarH", base);

        this.bridle = new WrappedModelPart("bridle", base);
        this.bridleNose = new WrappedModelPart("bridleN", base);

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

        this.theLlama.addChild(this.chests);
        this.theBody.addChild(this.saddleVanilla);
        this.theBody.addChild(this.saddleWestern);
        this.theBody.addChild(this.saddleEnglish);
        this.theNeck.addChild(this.blanket);
        this.theNeck.addChild(this.collar);
        this.theHead.addChild(this.blanketH);
        this.theHead.addChild(this.bridle);

        this.chests.addChild(this.chestsR);
        this.nose.addChild(this.bridleNose);

        this.saddleVanilla.addChild(this.saddlePad);
        this.saddleVanilla.addChild(this.stirrupNarrowLeft);
        this.saddleVanilla.addChild(this.stirrupNarrowRight);

        this.saddleWestern.addChild(this.saddleHorn);
        this.saddleWestern.addChild(this.saddlePad);
        this.saddleWestern.addChild(this.stirrupWideLeft);
        this.saddleWestern.addChild(this.stirrupWideRight);

        this.saddleEnglish.addChild(this.saddleHorn);
        this.saddleEnglish.addChild(this.saddlePad);
        this.saddleEnglish.addChild(this.stirrupNarrowLeft);
        this.saddleEnglish.addChild(this.stirrupNarrowRight);

        this.saddleHorn.addChild(this.saddlePomel);
        this.saddlePad.addChild(this.saddleSideLeft);
        this.saddlePad.addChild(this.saddleSideRight);
        this.stirrupWideLeft.addChild(this.stirrup);
        this.stirrupWideRight.addChild(this.stirrup);
        this.stirrupNarrowLeft.addChild(this.stirrup);
        this.stirrupNarrowRight.addChild(this.stirrup);
        this.collar.addChild(this.collarHardware);
    }

    private void resetCubes() {

    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        LlamaModelData data = getLlamaModelData();
        LlamaPhenotype llama = data.getPhenotype();

        resetCubes();

        if (llama!=null) {
            super.renderToBuffer(poseStack, vertexConsumer, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            Map<String, List<Float>> mapOfScale = new HashMap<>();

            int maxCoatLength = data.growthAmount == 1.0F ? llama.maxCoat : (int)(llama.maxCoat*data.growthAmount);

            this.neck.show(data.coatlength == -1);
            this.neckWool1.show(data.coatlength == 0);
            this.neckWool2.show(data.coatlength == 1);
            this.neckWool3.show(data.coatlength == 2);
            this.neckWool4.show(data.coatlength == 3);
            this.neckWool5.show(data.coatlength >= 4);

            this.bodyWool1.show(data.coatlength == 0 || data.coatlength == -1);
            this.bodyWool2.show(data.coatlength == 1);
            this.bodyWool3.show(data.coatlength == 2);
            this.bodyWool4.show(data.coatlength == 3);
            this.bodyWool5.show(data.coatlength >= 4);

            this.legWool1FrontLeft.show(maxCoatLength == 1);
            this.legWool2FrontLeft.show(maxCoatLength == 2);
            this.legWool3FrontLeft.show(maxCoatLength == 3);
            this.legWool4FrontLeft.show(maxCoatLength >= 4);
            this.legWool1FrontRight.show(maxCoatLength == 1);
            this.legWool2FrontRight.show(maxCoatLength == 2);
            this.legWool3FrontRight.show(maxCoatLength == 3);
            this.legWool4FrontRight.show(maxCoatLength >= 4);
            this.legWool1BackLeft.show(maxCoatLength == 1);
            this.legWool2BackLeft.show(maxCoatLength == 2);
            this.legWool3BackLeft.show(maxCoatLength == 3);
            this.legWool4BackLeft.show(maxCoatLength >= 4);
            this.legWool1BackRight.show(maxCoatLength == 1);
            this.legWool2BackRight.show(maxCoatLength == 2);
            this.legWool3BackRight.show(maxCoatLength == 3);
            this.legWool4BackRight.show(maxCoatLength >= 4);

            this.tail0.show(llama.maxCoat == 0);
            this.tail1.show(llama.maxCoat == 1);
            this.tail2.show(llama.maxCoat == 2);
            this.tail3.show(llama.maxCoat == 3);
            this.tail4.show(llama.maxCoat >= 4);

            this.blanket0.show(data.coatlength == -1);
            this.blanket1.show(data.coatlength == 0);
            this.blanket2.show(data.coatlength == 1);
            this.blanket3.show(data.coatlength == 2);
            this.blanket4.show(data.coatlength == 3);
            this.blanket5.show(data.coatlength == 4);

            if (data.collar) {
                int i = Math.max(data.coatlength, 0);
                mapOfScale.put("collar", COLLAR_SCALE[i]);
                mapOfScale.put("collarH", COLLAR_HARDWARE_SCALE[i]);
            }

            if (data.saddle != SaddleType.NONE) {
                float coatMod = 1.0F;

                switch (data.coatlength) {
                    case 1 -> coatMod = 1.0625F;
                    case 2 -> coatMod = 1.15F;
                    case 3 -> coatMod = 1.25F;
                    case 4 -> coatMod = 1.375F;
                }

                mapOfScale.put(data.saddle.getName(), SADDLE_SCALE);
                mapOfScale.put("saddlePad", ModelHelper.createScalings(coatMod, 0.875F, 0.875F, 0.0F, -0.00875F, -0.005F));
                this.saddlePomel.show(data.saddle == SaddleType.WESTERN);
                this.saddleSideLeft.show(data.saddle != SaddleType.VANILLA);
                this.saddleSideRight.show(data.saddle != SaddleType.VANILLA);
            }

            /**
             *      Growth scaling
             */
            float llamaSize = ((2.0F * data.size * data.growthAmount) + data.size) / 3.0F;
            float d = 0.0F;
            if (!data.sleeping && data.growthAmount != 1.0F) {
                float babySize = (3.0F - data.growthAmount) * 0.5F;
                d = 0.33333F * (1.0F - data.growthAmount);
                mapOfScale.put("bNeck", ModelHelper.createScalings(1.0F, babySize,1.0F,0.0F, 0.0F, 0.0F));
                mapOfScale.put("bLegFL", ModelHelper.createScalings(1.0F, babySize,1.0F,0.0F, 0.0F, 0.0F));
                mapOfScale.put("bLegFR", ModelHelper.createScalings(1.0F, babySize,1.0F,0.0F, 0.0F, 0.0F));
                mapOfScale.put("bLegBL", ModelHelper.createScalings(1.0F, babySize,1.0F,0.0F, 0.0F, 0.0F));
                mapOfScale.put("bLegBR", ModelHelper.createScalings(1.0F, babySize,1.0F,0.0F, 0.0F, 0.0F));
            }

            poseStack.pushPose();
            poseStack.scale(llamaSize, llamaSize, llamaSize);
            poseStack.translate(0.0F, (-1.45F + 1.45F / (llamaSize))-d, 0.0F);

            gaRender(this.theLlama, mapOfScale, poseStack, vertexConsumer, packedLightIn, packedOverlayIn, red, green, blue, alpha);

            poseStack.popPose();
        }
    }

    protected Map<String, Vector3f> saveAnimationValues(LlamaModelData data, LlamaPhenotype llama) {
        Map<String, Vector3f> map = data.offsets;
        map.put("bLlama", this.getRotationVector(this.theLlama));
        map.put("bLlamaPos", this.getPosVector(this.theLlama));
        map.put("bNeck", this.getRotationVector(this.theNeck));
        map.put("bNeckPos", this.getPosVector(this.theNeck));
        map.put("bHead", this.getRotationVector(this.theHead));
        map.put("bHeadPos", this.getPosVector(this.theHead));
        map.put("bLlamaFL", this.getRotationVector(this.theLegFrontLeft));
        map.put("bLlamaFR", this.getRotationVector(this.theLegFrontRight));
        map.put("bLlamaBL", this.getRotationVector(this.theLegBackLeft));
        map.put("bLlamaBR", this.getRotationVector(this.theLegBackRight));
        map.put("bLlamaBFL", this.getRotationVector(this.theLegBottomFrontLeft));
        map.put("bLlamaBFR", this.getRotationVector(this.theLegBottomFrontRight));
        map.put("bLlamaBBL", this.getRotationVector(this.theLegBottomBackLeft));
        map.put("bLlamaBBR", this.getRotationVector(this.theLegBottomBackRight));
        map.put("bEarL", this.getRotationVector(this.theEarLeft));
        map.put("bEarR", this.getRotationVector(this.theEarRight));
        map.put("earT", new Vector3f(this.earTopLeft.getX(), 0.0F, 0.0F));
        if (data.growthAmount == 1.0F) {
            map.put("nose", new Vector3f(0.0F, llama.nosePlacement, 0.0F));
        }
        return map;
    }

    private void readInitialAnimationValues(LlamaModelData data, LlamaPhenotype llama) {
        Map<String, Vector3f> map = data.offsets;
        if (map.isEmpty()) {
            this.theBody.setY(llama.suri ? Math.max(data.coatlength, 0) -10.0F : -10.0F);
            this.chests.setY(llama.suri ? -8.0F : -8.0F - (Math.max(data.coatlength, 0)));
            this.earTopLeft.setX(llama.banana ? 0.5F : 0.0F);
            this.earTopRight.setX(-this.earTopLeft.getX());
            this.nose.setPos(0.0F, llama.nosePlacement, 1.0F - data.growthAmount);
        } else {
            this.theLlama.setRotation(map.get("bLlama"));
            this.theLlama.setPos(map.get("bLlamaPos"));
            this.theBody.setY(llama.suri ? Math.max(data.coatlength, 0) -10.0F : -10.0F);
            this.theNeck.setRotation(map.get("bNeck"));
            this.theNeck.setPos(map.get("bNeckPos"));
            this.theHead.setRotation(map.get("bHead"));
            this.theHead.setPos(map.get("bHeadPos"));
            this.theLegFrontLeft.setRotation(map.get("bLlamaFL"));
            this.theLegFrontRight.setRotation(map.get("bLlamaFR"));
            this.theLegBackLeft.setRotation(map.get("bLlamaBL"));
            this.theLegBackRight.setRotation(map.get("bLlamaBR"));
            this.theLegBottomFrontLeft.setRotation(map.get("bLlamaBFL"));
            this.theLegBottomFrontRight.setRotation(map.get("bLlamaBFR"));
            this.theLegBottomBackLeft.setRotation(map.get("bLlamaBBL"));
            this.theLegBottomBackRight.setRotation(map.get("bLlamaBBR"));
            this.chests.setY(llama.suri ? -8.0F : -8.0F - (Math.max(data.coatlength, 0)));
            this.theEarLeft.setRotation(map.get("bEarL"));
            this.theEarRight.setRotation(map.get("bEarR"));
            this.earTopLeft.setX(map.get("earT").x());
            this.earTopRight.setX(-map.get("earT").x());
            if (map.containsKey("nose")) {
                this.nose.setPos(map.get("nose"));
            } else {
                this.nose.setPos(0.0F, llama.nosePlacement, 1.0F - data.growthAmount);
            }
        }
    }

    @Override
    public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.currentAnimal = entityIn.getId();
        LlamaModelData data = getCreateLlamaModelData(entityIn);

        if (data != null) {
            LlamaPhenotype llama = data.getPhenotype();
            readInitialAnimationValues(data, llama);

            boolean isMoving = entityIn.getDeltaMovement().horizontalDistanceSqr() > 1.0E-7D || entityIn.xOld != entityIn.getX() || entityIn.zOld != entityIn.getZ();

            if (data.earTwitchTimer <= ageInTicks) {
                if (this.theEarLeft.getZRot() != 0.0F || this.theEarRight.getZRot() != 0.0F || this.theEarLeft.getYRot() != 0.0F || this.theEarRight.getYRot() != 0.0F) {
                    this.theEarLeft.setZRot(this.lerpTo(0.1F, this.theEarLeft.getZRot(), 0.0F));
                    this.theEarRight.setZRot(this.lerpTo(0.1F,this.theEarRight.getZRot(), 0.0F));
                    this.theEarLeft.setYRot(this.lerpTo(0.15F, this.theEarLeft.getYRot(), 0.0F));
                    this.theEarRight.setYRot(this.lerpTo(0.15F, this.theEarRight.getYRot(), 0.0F));
                } else {
                    data.earTwitchSide = entityIn.getRandom().nextBoolean();
                    data.earTwitchTimer = (int) ageInTicks + (entityIn.getRandom().nextInt(data.sleeping ? 60 : 30) * 20) + 30;
                }
            } else if (data.earTwitchTimer <= ageInTicks + 30) {
                twitchEarAnimation(data.earTwitchSide, (int)ageInTicks);
            }

            if (!isMoving && data.sleeping) {
                if (data.sleepDelay == -1) {
                    data.sleepDelay = (int) ageInTicks + ((entityIn.getRandom().nextInt(10)) * 20) + 10;
                } else if (data.sleepDelay <= ageInTicks+50) {
                    if (data.sleepDelay <= ageInTicks) {
                        data.sleepDelay = 0;
                        layDownAnimation(true);
                    } else {
                        layDownAnimation(false);
                        headLookingAnimation(netHeadYaw, headPitch);
                    }
                }
            } else {
                if (data.sleepDelay != -1) {
                    data.sleepDelay = -1;
                }
                if (this.theLlama.getY() > 12.0F) {
                    standUpAnimation(isMoving, limbSwing, limbSwingAmount);
                }

                if (isMoving) {
                    walkingLegsAnimation(limbSwing, limbSwingAmount);
                } else if (this.theLlama.getY() <= 12.0F){
                    standingLegsAnimation();
                }

                boolean flag = true;
                if (data.isEating != 0) {
                    if (data.isEating == -1) {
                        data.isEating = (int)ageInTicks + 90;
                    } else if (data.isEating < ageInTicks) {
                        data.isEating = 0;
                    }
                    flag = grazingAnimation(data.isEating - (int)ageInTicks);
                }

                if (flag) {
                    if (netHeadYaw == 0 && headPitch == 0) {
                        moveHeadAnimation();
                    } else {
                        headLookingAnimation(netHeadYaw, headPitch);
                    }
                }
            }

            articulateLegs();

            if (data.saddle != SaddleType.NONE) {
                orientateSaddle(data.coatlength >= 1 ? data.coatlength / 1.825F : data.coatlength, data.saddle);
            }

            saveAnimationValues(data, llama);
        }

    }
    private void quickUp() {
        this.theLlama.setY(this.theLlama.getY()-0.05F);
        if (this.theLlama.getY() <= 12.0F) {
            this.theLlama.setY(12.0F);
            this.theLlama.setXRot(0.0F);
            this.theLegFrontLeft.setXRot(0.0F);
            this.theLegFrontRight.setXRot(0.0F);
            this.theLegBackLeft.setXRot(0.0F);
            this.theLegBackRight.setXRot(0.0F);
        } else {
            this.theLlama.setXRot(this.lerpTo(0.1F,this.theLlama.getXRot(), 0.0F));
            this.theLegFrontLeft.setXRot(this.lerpTo(0.1F, this.theLegFrontLeft.getXRot(), 0.0F));
            this.theLegFrontRight.setXRot(this.lerpTo(0.1F, this.theLegFrontRight.getXRot(), 0.0F));
            this.theLegBackLeft.setXRot(this.lerpTo(0.1F,this.theLegBackLeft.getXRot(), 0.0F));
            this.theLegBackRight.setXRot(this.lerpTo(0.1F,this.theLegBackRight.getXRot(), 0.0F));
        }
    }

    private void standUpAnimation(boolean isMoving, float limbSwing, float limbSwingAmount) {
        if (this.theLlama.getY() <= 16.5F) {
            //part 2
            if (this.theLlama.getY() == 16.5F) {
                float l = this.theLegFrontLeft.getXRot();
                float r = this.theLegFrontRight.getXRot();
                if (l <= Mth.HALF_PI * -0.8F || r <= Mth.HALF_PI * -0.8F) {
                    this.theLlama.setY(this.lerpTo(0.1F, this.theLlama.getY(), 12.0F));
                    this.theLlama.setXRot(this.lerpTo(0.05F, this.theLlama.getXRot(), 0.0F));
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
                this.theLlama.setY(this.lerpTo(0.1F, this.theLlama.getY(), 12.0F));
                this.theLlama.setXRot(this.lerpTo(0.05F, this.theLlama.getXRot(), 0.0F));
                if (isMoving) {
                    walkingLegsAnimation(limbSwing, limbSwingAmount);
                } else {
                    standingLegsAnimation();
                }
            }
        } else {
            //part 1
        this.theLlama.setY(this.lerpTo(0.1F, this.theLlama.getY(), 15.5F));
        this.theLlama.setXRot(this.lerpTo(this.theLlama.getXRot(), Mth.HALF_PI * -0.1F));
        this.theLegFrontLeft.setXRot(this.lerpTo(this.theLegFrontLeft.getXRot(), Mth.HALF_PI * 0.1F));
        this.theLegFrontRight.setXRot(this.lerpTo(this.theLegFrontRight.getXRot(), Mth.HALF_PI * 0.1F));
        this.theLegBottomFrontLeft.setXRot(this.lerpTo(this.theLegBottomFrontLeft.getXRot(), Mth.HALF_PI * 0.9F));
        this.theLegBottomFrontRight.setXRot(this.lerpTo(this.theLegBottomFrontRight.getXRot(), Mth.HALF_PI * 0.9F));
        this.theLegBackLeft.setXRot(this.lerpTo(this.theLegBackLeft.getXRot(), Mth.HALF_PI * -0.87F));
        this.theLegBackRight.setXRot(this.lerpTo(this.theLegBackRight.getXRot(), Mth.HALF_PI * -0.87F));
        this.theLegBottomBackLeft.setXRot(this.lerpTo(this.theLegBottomBackLeft.getXRot(), Mth.HALF_PI * 0.2F));
        this.theLegBottomBackRight.setXRot(this.lerpTo(this.theLegBottomBackRight.getXRot(), Mth.HALF_PI * 0.2F));
            if (theLlama.getY() < 16.5F) {
                this.theLlama.setY(16.5F);
            }
        }
    }

    private void layDownAnimation(boolean asleep) {
        if (asleep) {
            this.theNeck.setYRot(this.lerpTo(this.theNeck.getYRot(), 0.0F));
            this.theHead.setYRot(this.lerpTo(this.theHead.getYRot(), 0.0F));
        }
        float v = this.theLlama.getY();
        if (v >= 18.75F) {
            if (v < 21.5F) {
                this.theLlama.setY(18.75F + ((this.theLegBottomFrontLeft.getXRot()/Mth.PI * 0.85F)*3.25F));
            } else if (v > 21.5F) {
                this.theLlama.setY(21.25F);
            }
//            this.theLlama.setY(21.25F);
            if (asleep) {
                this.theNeck.setY(this.lerpTo(this.theNeck.getY(), 6.0F));
                this.theHead.setY(this.lerpTo(this.theHead.getY(), -13.0F));
                this.theHead.setZ(this.lerpTo(this.theHead.getZ(), 0.0F));
                this.theNeck.setXRot(this.lerpTo(this.theNeck.getXRot(), Mth.HALF_PI * 1.35F));
                this.theHead.setXRot(this.lerpTo(this.theHead.getXRot(), Mth.HALF_PI * -1.35F));
            }
                //part 2
            this.theLlama.setXRot(this.lerpTo(0.05F, this.theLlama.getXRot(), 0.0F));
            this.theLegFrontLeft.setXRot(this.lerpTo(0.05F, this.theLegFrontLeft.getXRot(), Mth.HALF_PI*-0.7F));
            this.theLegFrontRight.setXRot(this.lerpTo(0.05F, this.theLegFrontRight.getXRot(), Mth.HALF_PI*-0.7F));
            this.theLegBottomFrontLeft.setXRot(this.lerpTo(0.05F, this.theLegBottomFrontLeft.getXRot(), Mth.PI * 0.85F));
            this.theLegBottomFrontRight.setXRot(this.lerpTo(0.05F, this.theLegBottomFrontRight.getXRot(), Mth.PI * 0.85F));
            this.theLegBackLeft.setXRot(this.lerpTo(0.05F, this.theLegBackLeft.getXRot(), Mth.HALF_PI * -1.3F));
            this.theLegBackRight.setXRot(this.lerpTo(0.05F, this.theLegBackRight.getXRot(), Mth.HALF_PI * -1.3F));
            this.theLegBottomBackLeft.setXRot(this.lerpTo(0.05F, this.theLegBottomBackLeft.getXRot(), Mth.HALF_PI * 0.3F));
            this.theLegBottomBackRight.setXRot(this.lerpTo(0.05F, this.theLegBottomBackRight.getXRot(), Mth.HALF_PI * 0.3F));
        } else {
            //part1  -  y starts at 12.0F
            this.theLlama.setY(12.0F + ((this.theLegBottomFrontLeft.getXRot()/Mth.HALF_PI)*7.8F));
            this.theLlama.setXRot(this.lerpTo(this.theLlama.getXRot(), Mth.HALF_PI * 0.3F));
            this.theLegFrontLeft.setXRot(this.lerpTo(this.theLegFrontLeft.getXRot(), Mth.HALF_PI * -0.3F));
            this.theLegFrontRight.setXRot(this.lerpTo(this.theLegFrontRight.getXRot(), Mth.HALF_PI * -0.3F));
            this.theLegBottomFrontLeft.setXRot(this.lerpTo(this.theLegBottomFrontLeft.getXRot(), Mth.HALF_PI));
            this.theLegBottomFrontRight.setXRot(this.lerpTo(this.theLegBottomFrontRight.getXRot(), Mth.HALF_PI));
            this.theLegBackLeft.setXRot(this.lerpTo(this.theLegBackLeft.getXRot(), Mth.HALF_PI * -0.3F));
            this.theLegBackRight.setXRot(this.lerpTo(this.theLegBackRight.getXRot(), Mth.HALF_PI * -0.3F));
            this.theLegBottomBackLeft.setXRot(this.lerpTo(this.theLegBottomBackLeft.getXRot(), 0.0F));
            this.theLegBottomBackRight.setXRot(this.lerpTo(this.theLegBottomBackRight.getXRot(), 0.0F));
        }
    }

    private boolean grazingAnimation(float ticks) {
        if (ticks < 50) {
            float neckRot = this.theNeck.getXRot();
//            this.theNeck.setY(this.lerpTo(this.theNeck.getY(), 0.0F));
            if (neckRot < Mth.HALF_PI) {
                this.theNeck.setXRot(this.lerpTo(this.theNeck.getXRot(), Mth.HALF_PI*1.55F));
            } else if (neckRot < Mth.HALF_PI*1.25F){
                this.theNeck.setXRot(this.lerpTo(this.theNeck.getXRot(), Mth.HALF_PI*1.55F));
                this.theHead.setXRot(this.lerpTo(this.theHead.getXRot(), -Mth.HALF_PI*0.5F));
                this.theNeck.setY(this.lerpTo(this.theNeck.getY(), 4.0F));
                this.theHead.setY(this.lerpTo(this.theHead.getY(), -9.0F));
                this.theHead.setZ(this.lerpTo(this.theHead.getZ(), -2.0F));
//                this.jaw.setXRot(this.lerpTo(this.jaw.getXRot(), Mth.HALF_PI*-0.1F));
            } else {
                float loop = (float) Math.cos(ticks*0.5F);
                this.theNeck.setY(this.lerpTo(this.theNeck.getY(), 7.0F));
                this.theHead.setY(this.lerpTo(this.theHead.getY(), -10.0F));
                this.theHead.setZ(this.lerpTo(this.theHead.getZ(), -3.0F));
                if (loop > 0) {
                    this.theNeck.setXRot(this.lerpTo(this.theNeck.getXRot(), Mth.HALF_PI*1.55F));
                    this.theHead.setXRot(this.lerpTo(this.theHead.getXRot(), -Mth.HALF_PI));
//                    this.jaw.setXRot(this.lerpTo(this.jaw.getXRot(), Mth.HALF_PI*-0.2F));
                } else {
                    this.theNeck.setXRot(this.lerpTo(this.theNeck.getXRot(), Mth.HALF_PI*1.5F));
                    this.theHead.setXRot(this.lerpTo(this.theHead.getXRot(), -Mth.HALF_PI*0.75F));
//                    this.jaw.setXRot(this.lerpTo(this.jaw.getXRot(), Mth.HALF_PI*-0.05F));
                }
            }
            return false;
        } else {
            return true;
        }
    }

    private void moveHeadAnimation() {
        this.theNeck.setY(this.lerpTo(this.theNeck.getY(), 3.0F));
        this.theHead.setY(this.lerpTo(this.theHead.getY(), -8.0F));
        this.theHead.setZ(this.lerpTo(this.theHead.getZ(), 0.0F));
        this.lerpPart(this.theNeck, 0.0F, 0.0F, 0.0F);
        this.lerpPart(this.theHead, 0.0F, 0.0F, 0.0F);
    }

    private void headLookingAnimation(float netHeadYaw, float headPitch) {
        netHeadYaw = (netHeadYaw * 0.017453292F);
        headPitch = ((headPitch * 0.017453292F));
        float lookRotX = Math.min((headPitch * 0.85F), 0.0F);
        float lookRotY = netHeadYaw * 0.5F;

        this.theNeck.setY(this.lerpTo(this.theNeck.getY(), 3.0F));
        this.theHead.setY(this.lerpTo(this.theHead.getY(), -8.0F));
        this.theHead.setZ(this.lerpTo(this.theHead.getZ(), 0.0F));
        this.theNeck.setXRot(this.lerpTo(this.theNeck.getXRot(), headPitch - lookRotX));
        this.theNeck.setYRot(this.lerpTo(this.theNeck.getYRot(), lookRotY));
        this.theHead.setXRot(this.lerpTo(this.theHead.getXRot(), lookRotX));
        this.theHead.setYRot(this.lerpTo(this.theHead.getYRot(), limit(netHeadYaw - lookRotY, Mth.HALF_PI * 0.75F)));
    }

    private void walkingLegsAnimation(float limbSwing, float limbSwingAmount) {
        float f = ((Mth.cos(limbSwing * 0.6662F)) * 1.4F * limbSwingAmount);
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

    private void twitchEarAnimation(boolean side, float ticks) {
        boolean direction = Math.cos(ticks*0.8F) > 0;
        if (side) {
            this.theEarLeft.setZRot(this.lerpTo(0.15F, this.theEarLeft.getZRot(), Mth.HALF_PI * 0.5F * (direction?-1F:-0.5F)));
            this.theEarLeft.setYRot(this.lerpTo(0.15F, this.theEarLeft.getYRot(), Mth.HALF_PI * 0.5F * (direction?1F:0.5F)));
            this.theEarRight.setZRot(this.lerpTo(0.15F, this.theEarRight.getZRot(), 0.0F));
            this.theEarRight.setYRot(this.lerpTo(0.15F, this.theEarRight.getYRot(), 0.0F));
        } else {
            this.theEarRight.setZRot(this.lerpTo(0.15F, this.theEarRight.getZRot(), Mth.HALF_PI * 0.5F * (direction?1F:0.5F)));
            this.theEarRight.setYRot(this.lerpTo(0.15F, this.theEarRight.getYRot(), Mth.HALF_PI * 0.5F * (direction?-1F:-0.5F)));
            this.theEarLeft.setZRot(this.lerpTo(0.15F, this.theEarLeft.getZRot(), 0.0F));
            this.theEarLeft.setYRot(this.lerpTo(0.15F, this.theEarLeft.getYRot(), 0.0F));
        }
    }

    private void orientateSaddle(float coatLength, SaddleType saddleType) {
        switch (saddleType) {
            case VANILLA -> {
                this.saddleVanilla.setY(-0.1F -coatLength);
                this.stirrupNarrowLeft.setPos(7.5F + coatLength, 0.0F, 0.0F);
                this.stirrupNarrowRight.setPos(-7.5F - coatLength, 0.0F, 0.0F);
            }
            case ENGLISH -> {
                this.saddleEnglish.setY(-0.1F -coatLength);
                this.saddleSideLeft.setPos(3.25F, -0.5F, -4.0F);
                this.saddleSideRight.setPos(-3.25F, -0.5F, -4.0F);
                this.saddleHorn.setPos(0.0F, -1.0F, -1.0F);
                this.saddleHorn.setXRot(Mth.PI / 4.5F);
                this.stirrupNarrowLeft.setPos(7.25F + coatLength, -0.25F, -1.5F);
                this.stirrupNarrowRight.setPos(-7.25F - coatLength, -0.25F, -1.5F);
            }
            case WESTERN -> {
                this.saddleWestern.setY(-0.1F - coatLength);
                this.saddleSideLeft.setPos(4.75F, -1.0F, -5.25F);
                this.saddleSideRight.setPos(-4.75F, -1.0F, -5.25F);
                this.saddleHorn.setPos(0.0F, -2.0F, -2.0F);
                this.saddleHorn.setXRot(Mth.PI * 0.125F);
                this.stirrupWideLeft.setX(7.5F + coatLength);
                this.stirrupWideRight.setX(-7.5F - coatLength);
            }
        }
    }

    private void articulateLegs() {
        float flag = this.theLegBottomFrontLeft.getXRot();
        if (flag > 0.0F) {
            this.theLegBottomFrontLeft.setZ(-3.0F + (6.0F * (Math.min(flag, Mth.PI * 0.7F) / Mth.PI * 0.7F)));
            this.theToeFrontLeft.setXRot(flag * 0.5F);
            this.theToeFrontLeft.setZ(2.0F + (-1.0F * flag));
        } else if (this.theLegBottomFrontLeft.getZ() != -3.0F) {
            this.theLegBottomFrontLeft.setZ(-3.0F);
            this.theToeFrontLeft.setXRot(0.0F);
            this.theToeFrontLeft.setZ(2.0F);
        }
        flag = this.theLegBottomFrontRight.getXRot();
        if (flag > 0.0F) {
            this.theLegBottomFrontRight.setZ(-3.0F + (6.0F * (Math.min(flag, Mth.PI * 0.7F) / Mth.PI * 0.7F)));
            this.theToeFrontRight.setXRot(flag * 0.5F);
            this.theToeFrontRight.setZ(2.0F + (-1.0F * flag));
        } else if (this.theLegBottomFrontRight.getZ() != -3.0F) {
            this.theLegBottomFrontRight.setZ(-3.0F);
            this.theToeFrontRight.setXRot(0.0F);
            this.theToeFrontRight.setZ(2.0F);
        }
        flag = this.theLegBottomBackLeft.getXRot();
        if (flag > 0.0F) {
            this.theLegBackLeft.setY(flag * -1.0F);
            this.theLegBackLeft.setZ(15.0F + (flag * 6.0F));
            this.theLegBottomBackLeft.setZ(3.0F * flag);
            this.theLegBottomBackLeft.setY(6.0F - (11.0F * flag));
            this.theToeBackLeft.setXRot(flag * 3.5F);
            this.theToeBackLeft.setZ(2.0F + (-3.0F * flag));
        } else if (flag == 0.0F) {
            this.theLegBackLeft.setY(0.0F);
            this.theLegBackLeft.setZ(15.0F);
            this.theLegBottomBackLeft.setY(6.0F);
            this.theLegBottomBackLeft.setZ(0.0F);
            this.theToeBackLeft.setXRot(0.0F);
            this.theToeBackLeft.setZ(2.0F);
        }
        flag = this.theLegBottomBackRight.getXRot();
        if (flag > 0.0F) {
            this.theLegBackRight.setY(flag * -1.0F);
            this.theLegBackRight.setZ(15.0F + (flag * 6.0F));
            this.theLegBottomBackRight.setY(6.0F - (11.0F * flag));
            this.theLegBottomBackRight.setZ(3.0F * flag);
            this.theToeBackRight.setXRot(flag * 3.5F);
            this.theToeBackRight.setZ(2.0F + (-3.0F * flag));
        } else if (flag == 0.0F) {
            this.theLegBackRight.setY(0.0F);
            this.theLegBackRight.setZ(15.0F);
            this.theLegBottomBackRight.setY(6.0F);
            this.theLegBottomBackRight.setZ(0.0F);
            this.theToeBackRight.setXRot(0.0F);
            this.theToeBackRight.setZ(2.0F);
        }
    }

    private class LlamaModelData extends AnimalModelData {
        int coatlength;
        int maxCoat;
        public LlamaPhenotype getPhenotype() {
            return (LlamaPhenotype) this.phenotype;
        }
    }

    private LlamaModelData getLlamaModelData() {
        return (LlamaModelData) getAnimalModelData();
    }

    private LlamaModelData getCreateLlamaModelData(T enhancedLlama) {
        return (LlamaModelData) getCreateAnimalModelData(enhancedLlama);
    }

    @Override
    protected void setInitialModelData(T enhancedLlama) {
        LlamaModelData llamaModelData = new LlamaModelData();
        additionalModelDataInfo(llamaModelData, enhancedLlama);
        setBaseInitialModelData(llamaModelData, enhancedLlama);
    }

    @Override
    protected void additionalUpdateModelDataInfo(AnimalModelData animalModelData, T enhancedAnimal) {
        ((LlamaModelData) animalModelData).coatlength = enhancedAnimal.getCoatLength();
        animalModelData.chests = enhancedAnimal.hasChest();
        animalModelData.blanket = enhancedAnimal.hasBlanket();
        animalModelData.bridle = enhancedAnimal.hasBridle();
        animalModelData.saddle = getSaddle(enhancedAnimal.getEnhancedInventory());
    }

    @Override
    protected Phenotype createPhenotype(T enhancedAnimal) {
        return new LlamaPhenotype(enhancedAnimal.getSharedGenes().getAutosomalGenes());
    }

    protected class LlamaPhenotype implements Phenotype {
        boolean banana;
        boolean suri;
        int maxCoat;
        float nosePlacement;

        LlamaPhenotype(int[] gene) {
            this.banana = gene[18] != 1 && gene[19] != 1 && (gene[18] == 2 || gene[19] == 2);
            this.suri = gene[20] == 2 && gene[21] == 2;
            
            float maxCoatLength = 0.0F;

            if (gene[22] >= 2 || gene[23] >= 2){
                if (gene[22] == 3 && gene[23] == 3){
                    maxCoatLength = 1.25F;
                }else if (gene[22] == 3 || gene[23] == 3) {
                    maxCoatLength = 1F;
                }else if (gene[22] == 2 && gene[23] == 2) {
                    maxCoatLength = 0.75F;
                }else {
                    maxCoatLength = 0.5F;
                }

                if (gene[24] == 2){
                    maxCoatLength = maxCoatLength - 0.25F;
                }
                if (gene[25] == 2){
                    maxCoatLength = maxCoatLength - 0.25F;
                }

                if (gene[26] == 2 && gene[27] == 2){
                    maxCoatLength = maxCoatLength + (0.75F * (maxCoatLength/1.75F));
                }

            }else{
                maxCoatLength = 0;
            }

            if (maxCoatLength < 0.5){
                maxCoatLength = 0;
            }else if (maxCoatLength < 1){
                maxCoatLength = 1;
            }else if (maxCoatLength < 1.5){
                maxCoatLength = 2;
            }else if (maxCoatLength < 2) {
                maxCoatLength = 3;
            }else{
                maxCoatLength = 4;
            }

            this.maxCoat = (int)maxCoatLength;

            float noseHeight = 0.0F;
            if (gene[28] != 2) {
                if (gene[28] == 1) {
                    noseHeight = 0.15F;
                } else if (gene[28] == 3) {
                    noseHeight = 0.1F;
                } else {
                    noseHeight = -0.1F;
                }
            }

                if (gene[29] != 2) {
                    if (gene[29] == 1) {
                        noseHeight = noseHeight + 0.05F;
                    } else if (gene[29] == 3) {
                        noseHeight = noseHeight + 0.1F;
                    } else {
                        noseHeight = noseHeight - 0.1F;
                    }
                }

                if (gene[30] != 1) {
                    if (gene[30] == 2) {
                        noseHeight = noseHeight + 0.15F;
                    } else if (gene[30] == 3) {
                        noseHeight = noseHeight + 0.1F;
                    } else {
                        noseHeight = noseHeight - 0.1F;
                    }
                }

                if (gene[31] != 1) {
                    if (gene[31] == 2) {
                        noseHeight = noseHeight + 0.05F;
                    } else if (gene[31] == 3) {
                        noseHeight = noseHeight + 0.1F;
                    } else {
                        noseHeight = noseHeight - 0.1F;
                    }
                }

                if (gene[32] != 1) {
                    if (gene[32] == 2) {
                        noseHeight = noseHeight + 0.15F;
                    } else if (gene[32] == 3) {
                        noseHeight = noseHeight + 0.2F;
                    } else if (gene[32] == 4) {
                        noseHeight = noseHeight - 0.15F;
                    } else {
                        noseHeight = noseHeight - 0.2F;
                    }
                }

                if (gene[33] != 1) {
                    if (gene[33] == 2) {
                        noseHeight = noseHeight + 0.15F;
                    } else if (gene[33] == 3) {
                        noseHeight = noseHeight + 0.2F;
                    } else if (gene[33] == 4) {
                        noseHeight = noseHeight - 0.15F;
                    } else {
                        noseHeight = noseHeight - 0.2F;
                    }
                }

            this.nosePlacement = noseHeight - ((Mth.abs(noseHeight)*1.25F) * 0.2F);
        }
    }
}

//package mokiyoki.enhancedanimals.model;
//
//import com.google.common.collect.ImmutableList;
//import com.mojang.blaze3d.vertex.PoseStack;
//import com.mojang.blaze3d.vertex.VertexConsumer;
//import mokiyoki.enhancedanimals.config.EanimodCommonConfig;
//import mokiyoki.enhancedanimals.entity.EnhancedLlama;
//import mokiyoki.enhancedanimals.items.CustomizableBridle;
//import mokiyoki.enhancedanimals.items.CustomizableCollar;
//import mokiyoki.enhancedanimals.items.CustomizableSaddleEnglish;
//import mokiyoki.enhancedanimals.items.CustomizableSaddleVanilla;
//import mokiyoki.enhancedanimals.items.CustomizableSaddleWestern;
//import mokiyoki.enhancedanimals.model.util.ModelHelper;
//import mokiyoki.enhancedanimals.renderer.EnhancedRendererModelNew;
//import net.minecraft.client.model.EntityModel;
//import net.minecraft.client.model.geom.ModelPart;
//import net.minecraft.client.multiplayer.ClientLevel;
//import net.minecraft.world.SimpleContainer;
//import net.minecraft.world.item.Item;
//import net.minecraft.world.item.ItemStack;
//import net.minecraft.world.item.SaddleItem;
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
//public class ModelEnhancedLlama <T extends EnhancedLlama> extends EntityModel<T> {
//
//    private Map<Integer, LlamaModelData> llamaModelDataCache = new HashMap<>();
//    private int clearCacheTimer = 0;
//
//    private final ModelPart chest1;
//    private final ModelPart chest2;
//
//    private boolean banana = false;
//    private boolean suri = false;
//
//    private final EnhancedRendererModelNew nose;
//    private final EnhancedRendererModelNew head;
//    private final EnhancedRendererModelNew neckBone;
//    private final ModelPart neck;
//    private final ModelPart neckWool0;
//    private final ModelPart neckWool1;
//    private final ModelPart neckWool2;
//    private final ModelPart neckWool3;
//    private final ModelPart neckWool4;
//    private final EnhancedRendererModelNew earsR;
//    private final EnhancedRendererModelNew earsL;
//    private final EnhancedRendererModelNew earsTopR;
//    private final EnhancedRendererModelNew earsTopL;
//    private final EnhancedRendererModelNew earsTopBananaR;
//    private final EnhancedRendererModelNew earsTopBananaL;
//    private final ModelPart body;
//    private final ModelPart bodyShaved;
//    private final ModelPart body1;
//    private final ModelPart body2;
//    private final ModelPart body3;
//    private final ModelPart body4;
//    private final ModelPart tail;
//    private final ModelPart tail1;
//    private final ModelPart tail2;
//    private final ModelPart tail3;
//    private final ModelPart tail4;
//    private final ModelPart leg1;
//    private final ModelPart leg1Wool1;
//    private final ModelPart leg1Wool2;
//    private final ModelPart leg1Wool3;
//    private final ModelPart leg1Wool4;
//    private final ModelPart leg2;
//    private final ModelPart leg2Wool1;
//    private final ModelPart leg2Wool2;
//    private final ModelPart leg2Wool3;
//    private final ModelPart leg2Wool4;
//    private final ModelPart leg3;
//    private final ModelPart leg3Wool1;
//    private final ModelPart leg3Wool2;
//    private final ModelPart leg3Wool3;
//    private final ModelPart leg3Wool4;
//    private final ModelPart leg4;
//    private final ModelPart leg4Wool1;
//    private final ModelPart leg4Wool2;
//    private final ModelPart leg4Wool3;
//    private final ModelPart leg4Wool4;
//    private final ModelPart toeOuterFrontR;
//    private final ModelPart toeInnerFrontR;
//    private final ModelPart toeOuterFrontL;
//    private final ModelPart toeInnerFrontL;
//    private final ModelPart toeOuterBackR;
//    private final ModelPart toeInnerBackR;
//    private final ModelPart toeOuterBackL;
//    private final ModelPart toeInnerBackL;
//    private final EnhancedRendererModelNew eyeLeft;
//    private final EnhancedRendererModelNew eyeRight;
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
//    private final EnhancedRendererModelNew collar;
//    private final EnhancedRendererModelNew collarHardware;
//    private final EnhancedRendererModelNew bridle;
//    private final EnhancedRendererModelNew bridleNose;
//
//    private Integer currentLlama = null;
//
//    public ModelEnhancedLlama(float scale)
//    {
//        this.texWidth = 256;
//        this.texHeight = 256;
//
//        float headAdjust = -2.0F;
//
//        this.nose = new EnhancedRendererModelNew(this,28, 0);
//        this.nose.addBox(-2.0F, 0.0F, -7.0F, 4, 4, 4, scale); //nose
//        this.nose.setPos(0, 0, 0);
//
//        this.head = new EnhancedRendererModelNew(this, 0, 0);
//        this.head.addBox(-4.0F, -3.0F, -3.0F, 8, 6, 6, scale); //head
//        this.head.texOffs(28,8);
//        this.head.addBox(-4.0F, -3.0F, -3.0F, 8, 10, 6, scale + 0.505F); //deco
//        this.head.setPos(0.0F, -11.0F, scale + 1.0F);
////        this.head.setTextureOffset(28, 0);
////        this.head.addBox(-2.0F, -12.0F, -4.0F, 4, 4, 4, 0.0F); //nose
//
//        this.neckBone = new EnhancedRendererModelNew(this, 0, 0, "Neck");
//        this.neckBone.setPos(0, 5, -6);
//
//        this.neck = new ModelPart(this,0, 12);
//        this.neck.addBox(-4.0F, -9.0F, -1.1F, 8, 12, 6, scale - 1.0F); //neck
//        this.neck.texOffs(28,23);
//        this.neck.addBox(-4.0F, -2.0F, -1.1F, 8, 10, 6, scale + 0.01F); //deco
//        this.neck.setPos(0, 5, -6);
//
//        this.neckWool0 = new ModelPart(this, 0, 12);
//        this.neckWool0.addBox(-4.0F, -8.0F, headAdjust, 8, 12, 6, scale + 0.0F); //neck
//        this.neckWool0.texOffs(28,23);
//        this.neckWool0.addBox(-4.0F, -3.75F, headAdjust, 8, 10, 6, scale + 0.51F); //deco
//
//        this.neckWool1 = new ModelPart(this, 0, 12);
//        this.neckWool1.addBox(-4.0F, -8.5F, headAdjust, 8, 12, 6, scale + 0.5F); //neck
//        this.neckWool1.texOffs(28,23);
//        this.neckWool1.addBox(-4.0F, -2.25F, headAdjust, 8, 10, 6, scale + 0.75F); //deco
//
//        this.neckWool2 = new ModelPart(this, 0, 12);
//        this.neckWool2.addBox(-4.0F, -7.5F, headAdjust, 8, 12, 6, scale + 1.0F); //neck
//        this.neckWool2.texOffs(28,23);
//        this.neckWool2.addBox(-4.0F, -3.7F, headAdjust, 8, 10, 6, scale + 1.2F); //deco
//
//        this.neckWool3 = new ModelPart(this, 0, 12);
//        this.neckWool3.addBox(-4.0F, -7.0F, headAdjust, 8, 12, 6, scale + 1.5F); //neck
//        this.neckWool3.texOffs(28,23);
//        this.neckWool3.addBox(-4.0F, -3.1F, headAdjust, 8, 10, 6, scale + 1.6F); //deco
//
//        this.neckWool4 = new ModelPart(this, 0, 12);
//        this.neckWool4.addBox(-4.0F, -6.5F, headAdjust, 8, 12, 6, scale + 2.0F); //neck
//        this.neckWool4.texOffs(28,23);
//        this.neckWool4.addBox(-4.0F, -2.55F, -2.0F, 8, 10, 6, scale + 2.05F); //deco
//
//        this.earsR = new EnhancedRendererModelNew(this, 44, 0);
//        this.earsR.addBox(-4.0F, -6.0F, 0.0F, 3, 3, 2, scale + 0.0F); //ear right
//
//        this.earsL = new EnhancedRendererModelNew(this, 54, 0);
//        this.earsL.addBox(1.0F, -6.0F, 0.0F, 3, 3, 2, scale + 0.0F); //ear left
//
//        this.earsTopR = new EnhancedRendererModelNew(this, 64, 0);
//        this.earsTopR.addBox(-4.0F, -7.0F, 0.0F, 3, 1, 2, scale + 0.0F); //ear right
//
//        this.earsTopL = new EnhancedRendererModelNew(this, 74, 0);
//        this.earsTopL.addBox(1.0F, -7.0F, 0.0F, 3, 1, 2, scale + 0.0F); //ear left
//
//        this.earsTopBananaR = new EnhancedRendererModelNew(this, 64, 0);
//        this.earsTopBananaR.addBox(-3.5F, -7.0F, 0.0F, 3, 1, 2, scale + 0.0F); //ear right
//        this.earsTopBananaL = new EnhancedRendererModelNew(this, 74, 0);
//        this.earsTopBananaL.addBox(0.5F, -7.0F, 0.0F, 3, 1, 2, scale + 0.0F); //ear left
//
//        this.body = new ModelPart(this, 0, 39);
//        this.body.addBox(-6.0F, 0.0F, 0.0F, 12, 10, 18, scale);
//        this.body.setPos(0.0F, 2.0F, -8.0F);
//
//        this.bodyShaved = new ModelPart(this, 0, 39);
//        this.bodyShaved.addBox(-6.0F, 1.0F, 0.0F, 12, 10, 18, scale - 1.0F);
//        this.bodyShaved.setPos(0.0F, 2.0F, -2.0F);
//
//        this.body1 = new ModelPart(this, 0, 39);
//        this.body1.addBox(-6.0F, 0.0F, 0.0F, 12, 10, 18, scale + 0.5F);
//        this.body1.setPos(0.0F, 2.0F, -2.0F);
//
//        this.body2 = new ModelPart(this, 0, 39);
//        this.body2.addBox(-6.0F, 0.0F, 0.0F, 12, 10, 18, scale + 1.0F);
//        this.body2.setPos(0.0F, 2.0F, -2.0F);
//
//        this.body3 = new ModelPart(this, 0, 39);
//        this.body3.addBox(-6.0F, 0.0F, 0.0F, 12, 10, 18, scale + 1.5F);
//        this.body3.setPos(0.0F, 2.0F, -2.0F);
//
//        this.body4 = new ModelPart(this, 0, 39);
//        this.body4.addBox(-6.0F, 0.0F, 0.0F, 12, 10, 18, scale + 2.0F);
//        this.body4.setPos(0.0F, 2.0F, -2.0F);
//
//        this.chest1 = new ModelPart(this, 74, 44);
//        this.chest1.addBox(-3.0F, 0.0F, 0.0F, 8, 8, 3, scale);
//        this.chest1.setPos(-8.5F, 3.0F, 4.0F);
//        this.chest1.yRot = ((float)Math.PI / 2F);
//        this.chest2 = new ModelPart(this, 74, 57);
//        this.chest2.addBox(-3.0F, 0.0F, 0.0F, 8, 8, 3, scale);
//        this.chest2.setPos(5.5F, 3.0F, 4.0F);
//        this.chest2.yRot = ((float)Math.PI / 2F);
//
//        this.tail = new ModelPart(this, 42, 39);
//        this.tail.addBox(-3.0F, -2.0F, 15.0F, 6, 6, 6, scale);
//
//        this.tail1 = new ModelPart(this, 42, 39);
//        this.tail1.addBox(-3.0F, -1.75F, 15.25F, 6, 6, 6, scale + 0.25F);
//
//        this.tail2 = new ModelPart(this, 42, 39);
//        this.tail2.addBox(-3.0F, -1.5F, 15.5F, 6, 6, 6, scale + 0.5F);
//
//        this.tail3 = new ModelPart(this, 42, 39);
//        this.tail3.addBox(-3.0F, -1.25F, 15.75F, 6, 6, 6, scale + 0.75F);
//
//        this.tail4 = new ModelPart(this, 42, 39);
//        this.tail4.addBox(-3.0F, -0.75F, 16.25F, 6, 6, 6, scale + 1.25F);
//
//        this.leg1 = new ModelPart(this, 0, 68);
//        this.leg1.addBox(0.0F, 0.0F, 0.0F, 3, 11, 3, scale);
//        this.leg1.setPos(-5.0F, 12.0F,-7.0F);
//
//        this.leg1Wool1 = new ModelPart(this, 0, 68);
//        this.leg1Wool1.addBox(0.0F, 0.0F, 0.0F, 3, 7, 3, scale + 0.5F);
//        this.leg1Wool1.setPos(-5.0F, 12.0F,-1.0F);
//
//        this.leg1Wool2 = new ModelPart(this, 0, 68);
//        this.leg1Wool2.addBox(0.0F, 0.0F, 0.0F, 3, 7, 3, scale + 1.0F);
//        this.leg1Wool2.setPos(-5.0F, 12.0F,-1.0F);
//
//        this.leg1Wool3 = new ModelPart(this, 0, 68);
//        this.leg1Wool3.addBox(0.0F, 0.0F, 0.0F, 3, 7, 3, scale + 1.5F);
//        this.leg1Wool3.setPos(-5.0F, 12.0F,-1.0F);
//
//        this.leg1Wool4 = new ModelPart(this, 0, 68);
//        this.leg1Wool4.addBox(0.0F, 0.0F, 0.0F, 3, 7, 3, scale + 2.0F);
//        this.leg1Wool4.setPos(-5.0F, 12.0F,-1.0F);
//
//        this.leg2 = new ModelPart(this, 12, 68);
//        this.leg2.addBox(0.0F, 0.0F, 0.0F, 3, 11, 3, scale);
//        this.leg2.setPos(2.0F, 12.0F,-7.0F);
//
//        this.leg2Wool1 = new ModelPart(this, 12, 68);
//        this.leg2Wool1.addBox(0.0F, 0.0F, 0.0F, 3, 7, 3, scale + 0.5F);
//        this.leg2Wool1.setPos(2.0F, 12.0F,-1.0F);
//
//        this.leg2Wool2 = new ModelPart(this, 12, 68);
//        this.leg2Wool2.addBox(0.0F, 0.0F, 0.0F, 3, 7, 3, scale + 1.0F);
//        this.leg2Wool2.setPos(2.0F, 12.0F,-1.0F);
//
//        this.leg2Wool3 = new ModelPart(this, 12, 68);
//        this.leg2Wool3.addBox(0.0F, 0.0F, 0.0F, 3, 7, 3, scale + 1.5F);
//        this.leg2Wool3.setPos(2.0F, 12.0F,-1.0F);
//
//        this.leg2Wool4 = new ModelPart(this, 12, 68);
//        this.leg2Wool4.addBox(0.0F, 0.0F, 0.0F, 3, 7, 3, scale + 2.0F);
//        this.leg2Wool4.setPos(2.0F, 12.0F,-1.0F);
//
//        this.leg3 = new ModelPart(this, 0, 82);
//        this.leg3.addBox(0.0F, 0F, 0.0F, 3, 11, 3, scale);
//        this.leg3.setPos(-5.0F, 12.0F,6.0F);
//
//        this.leg3Wool1 = new ModelPart(this, 0, 82);
//        this.leg3Wool1.addBox(0.0F, 0.0F, 0.0F, 3, 7, 3, scale + 0.5F);
//        this.leg3Wool1.setPos(-5.0F, 12.0F,12.0F);
//
//        this.leg3Wool2 = new ModelPart(this, 0, 82);
//        this.leg3Wool2.addBox(0.0F, 0.0F, 0.0F, 3, 7, 3, scale + 1.0F);
//        this.leg3Wool2.setPos(-5.0F, 12.0F,12.0F);
//
//        this.leg3Wool3 = new ModelPart(this, 0, 82);
//        this.leg3Wool3.addBox(0.0F, 0.0F, 0.0F, 3, 7, 3, scale + 1.5F);
//        this.leg3Wool3.setPos(-5.0F, 12.0F,12.0F);
//
//        this.leg3Wool4 = new ModelPart(this, 0, 82);
//        this.leg3Wool4.addBox(0.0F, 0.0F, 0.0F, 3, 7, 3, scale + 2.0F);
//        this.leg3Wool4.setPos(-5.0F, 12.0F,12.0F);
//
//        this.leg4 = new ModelPart(this, 12, 82);
//        this.leg4.addBox(0.0F, 0.0F, 0.0F, 3, 11, 3, scale);
//        this.leg4.setPos(2.0F, 12.0F,6.0F);
//
//        this.leg4Wool1 = new ModelPart(this, 12, 82);
//        this.leg4Wool1.addBox(0.0F, 0.0F, 0.0F, 3, 7, 3, scale + 0.5F);
//        this.leg4Wool1.setPos(2.0F, 12.0F,12.0F);
//
//        this.leg4Wool2 = new ModelPart(this, 12, 82);
//        this.leg4Wool2.addBox(0.0F, 0.0F, 0.0F, 3, 7, 3, scale + 1.0F);
//        this.leg4Wool2.setPos(2.0F, 12.0F,12.0F);
//
//        this.leg4Wool3 = new ModelPart(this, 12, 82);
//        this.leg4Wool3.addBox(0.0F, 0.0F, 0.0F, 3, 7, 3, scale + 1.5F);
//        this.leg4Wool3.setPos(2.0F, 12.0F,12.0F);
//
//        this.leg4Wool4 = new ModelPart(this, 12, 82);
//        this.leg4Wool4.addBox(0.0F, 0.0F, 0.0F, 3, 7, 3, scale + 2.0F);
//        this.leg4Wool4.setPos(2.0F, 12.0F,12.0F);
//
//        this.toeOuterFrontR = new ModelPart(this, 26, 70);
//        this.toeOuterFrontR.addBox(-0.85F, 10.0F, -2.5F, 3, 3, 4, scale - 0.75F);
//        this.toeInnerFrontR = new ModelPart(this, 44, 70);
//        this.toeInnerFrontR.addBox(0.75F, 10.0F, -2.5F, 3, 3, 4, scale - 0.75F);
//
//        this.toeOuterFrontL = new ModelPart(this, 62, 70);
//        this.toeOuterFrontL.addBox(0.85F, 10.0F, -2.5F, 3, 3, 4, scale - 0.75F);
//        this.toeInnerFrontL = new ModelPart(this, 80, 70);
//        this.toeInnerFrontL.addBox(-0.75F, 10.0F, -2.5F, 3, 3, 4, scale - 0.75F);
//
//        this.toeOuterBackR = new ModelPart(this, 26, 84);
//        this.toeOuterBackR.addBox(-0.85F, 10.0F, -2.5F, 3, 3, 4, scale - 0.75F);
//        this.toeInnerBackR = new ModelPart(this, 44, 84);
//        this.toeInnerBackR.addBox(0.75F, 10.0F, -2.5F, 3, 3, 4, scale - 0.75F);
//
//        this.toeOuterBackL = new ModelPart(this, 62, 84);
//        this.toeOuterBackL.addBox(0.85F, 10.0F, -2.5F, 3, 3, 4, scale - 0.75F);
//        this.toeInnerBackL = new ModelPart(this, 80, 84);
//        this.toeInnerBackL.addBox(-0.75F, 10.0F, -2.5F, 3, 3, 4, scale - 0.75F);
//
//        this.eyeLeft = new EnhancedRendererModelNew(this, 22, 3);
//        this.eyeLeft.addBox(0.0F, 0.0F, 0.0F, 2, 2, 1, 0.01F);
//        this.eyeLeft.setPos(2.0F, -1.0F, -3.0F);
//
//        this.eyeRight = new EnhancedRendererModelNew(this, 0, 3);
//        this.eyeRight.addBox(0.0F, 0.0F, 0.0F, 2, 2, 1, 0.01F);
//        this.eyeRight.setPos(-4.0F, -1.0F, -3.0F);
//
////        leg1.addChild(toeOuterFrontR);
////        leg1.addChild(toeInnerFrontR);
////        leg2.addChild(toeOuterFrontL);
////        leg2.addChild(toeInnerFrontL);
////        leg3.addChild(toeOuterBackR);
////        leg3.addChild(toeInnerBackR);
////        leg4.addChild(toeOuterBackL);
////        leg4.addChild(toeInnerBackL);
//
////        head.addChild(nose);
//        this.neckBone.addChild(head);
//        this.head.addChild(nose);
//        this.head.addChild(earsL);
//        this.head.addChild(earsR);
//        this.head.addChild(earsTopL);
//        this.head.addChild(earsTopR);
//        this.head.addChild(earsTopBananaL);
//        this.head.addChild(earsTopBananaR);
//        this.head.addChild(eyeLeft);
//        this.head.addChild(eyeRight);
//
//        /**
//         * Equipment stuff
//         */
//
//        this.saddle = new EnhancedRendererModelNew(this, 0, 0, "Saddle");
//
//        this.saddleWestern = new EnhancedRendererModelNew(this, 146, 0, "WesternSaddle");
//        this.saddleWestern.addBox(-5.0F, -2.0F, -5.0F, 10, 2, 13, 0.0F);
//        this.saddleWestern.texOffs(146, 15);
//        this.saddleWestern.addBox(-4.0F, -3.0F, 5.0F, 8, 2, 4, 0.0F);
//        this.saddleWestern.texOffs(222, 15);
//        this.saddleWestern.addBox(-3.5F, -4.0F, 8.0F, 7, 2, 2, 0.0F);
//
//        this.saddleEnglish = new EnhancedRendererModelNew(this, 147, 1, "EnglishSaddle");
//        this.saddleEnglish.addBox(-5.0F, -1.0F, -4.0F, 10, 2, 12, 0.0F);
//        this.saddleEnglish.texOffs(146, 15);
//        this.saddleEnglish.addBox(-4.0F, -1.5F, 5.0F, 8, 2, 4, 0.0F);
//        this.saddleEnglish.texOffs(222, 15);
//        this.saddleEnglish.addBox(-3.5F, -2.0F, 7.5F, 7, 2, 2, 0.0F);
//
//        this.saddleHorn = new EnhancedRendererModelNew(this, 170, 19, "SaddleHorn");
//        this.saddleHorn.addBox(-4.0F, -2.0F, -3.0F, 8, 2, 3, 0.0F);
//
//        this.saddlePomel = new EnhancedRendererModelNew(this, 179, 0, "SaddlePomel");
//        this.saddlePomel.addBox(-1.0F, -3.0F, -2.0F, 2, 4, 2, -0.25F);
//        this.saddlePomel.setPos(0.0F, -2.0F, -2.0F);
//
//        this.saddleSideL = new EnhancedRendererModelNew(this, 170, 49, "SaddleLeft");
//        this.saddleSideL.addBox(0.0F, 0.0F, 0.0F, 3, 4, 8);
//
//        this.saddleSideR = new EnhancedRendererModelNew(this, 170, 61, "SaddleRight");
//        this.saddleSideR.addBox(-3.0F, 0.0F, 0.0F, 3, 4, 8);
//
//        this.stirrup2DWideL = new EnhancedRendererModelNew(this, 184, 24, "2DStirrupL");
//        this.stirrup2DWideL.addBox(0.0F, 0.0F, 0.0F, 0, 10, 4); // strap
//
//        this.stirrup2DWideR = new EnhancedRendererModelNew(this, 184, 24, "2DStirrupR");
//        this.stirrup2DWideR.addBox(0.0F, 0.0F, 0.0F, 0, 10, 4); // strap
//
//        this.stirrup3DNarrowL = new EnhancedRendererModelNew(this, 185, 27, "3DStirrupL");
//        this.stirrup3DNarrowL.addBox(-1.0F, 0.0F, 0.0F, 1, 10, 1); // strap
//
//        this.stirrup3DNarrowR = new EnhancedRendererModelNew(this, 187, 27, "3DStirrupR");
//        this.stirrup3DNarrowR.addBox(0.0F, 0.0F, 0.0F, 1, 10, 1);
//
//        this.stirrup = new EnhancedRendererModelNew(this, 146, 0, "Stirrup");
//        this.stirrup.addBox(-0.5F, 9.5F, -1.0F, 1, 1, 1);
//        this.stirrup.texOffs(150, 0);
//        this.stirrup.addBox(-0.5F, 9.5F, 1.0F, 1, 1, 1);
//        this.stirrup.texOffs(146, 2);
//        this.stirrup.addBox(-0.5F, 10.5F, -1.5F, 1, 3, 1);
//        this.stirrup.texOffs(150, 2);
//        this.stirrup.addBox(-0.5F, 10.5F, 1.5F, 1, 3, 1);
//        this.stirrup.texOffs(147, 7);
//        this.stirrup.addBox(-0.5F, 12.5F, -0.5F, 1, 1, 2);
//
//        this.saddlePad = new EnhancedRendererModelNew(this, 130, 24, "SaddlePad");
//        this.saddlePad.addBox(-8.0F, -1.0F, -6.0F, 16, 10, 15, -1.0F);
//
//        this.collar = new EnhancedRendererModelNew(this, 88, 84, "Collar");
//        this.collar.addBox(-5.0F, -4.5F, -3.0F, 10, 2, 8, 0.0F);
//
//        this.collarHardware = new EnhancedRendererModelNew(this, 127, 88, "CollarHardware");
//        this.collarHardware.addBox(0.0F, -5.0F, -5.0F, 0, 3, 3, 0.0F);
//        this.collarHardware.texOffs(116, 84);
//        this.collarHardware.addBox(-1.5F, -2.5F, -5.75F, 3, 3, 3, 0.0F);
//        this.neckBone.addChild(this.collar);
//        this.collar.addChild(this.collarHardware);
//
//        this.body.addChild(saddle);
//        this.saddle.addChild(saddlePad);
//        this.saddle.addChild(stirrup3DNarrowL);
//        this.saddle.addChild(stirrup3DNarrowR);
//        this.saddleHorn.addChild(saddlePomel);
//        this.saddlePad.addChild(saddleSideL);
//        this.saddlePad.addChild(saddleSideR);
//
//        //western
//        this.body.addChild(saddleWestern);
//        this.saddleWestern.addChild(saddleHorn);
//        this.saddleWestern.addChild(saddlePad);
//        this.saddleWestern.addChild(stirrup2DWideL);
//        this.saddleWestern.addChild(stirrup2DWideR);
//        this.stirrup2DWideL.addChild(stirrup);
//        this.stirrup2DWideR.addChild(stirrup);
//        //english
//        this.body.addChild(saddleEnglish);
//        this.saddleEnglish.addChild(saddleHorn);
//        this.saddleEnglish.addChild(saddlePad);
//        this.saddleEnglish.addChild(stirrup3DNarrowL);
//        this.saddleEnglish.addChild(stirrup3DNarrowR);
//        this.stirrup3DNarrowL.addChild(stirrup);
//        this.stirrup3DNarrowR.addChild(stirrup);
//
//        this.texHeight = 128;
//        this.texWidth = 128;
//        //bridle
//        this.bridle = new EnhancedRendererModelNew(this, 0, 56);
//        this.bridle.addBox(-4.0F, -3.0F, -3.0F, 8, 6, 6, scale+ 0.1F);
//
//        this.bridleNose = new EnhancedRendererModelNew(this, 0, 48);
//        this.bridleNose.addBox(-2.0F, 0.0F, -7.0F, 4, 4, 4, scale + 0.1F);
//        this.bridle.addChild(this.bridleNose);
//
//        this.head.addChild(this.bridle);
//    }
//
//    @Override
//    public void renderToBuffer(PoseStack matrixStackIn, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
//        LlamaModelData llamaModelData = getLlamaModelData();
//
//        int[] genes = llamaModelData.llamaGenes;
//        int coatlength = llamaModelData.coatlength;
//        int maxCoatlength = llama.maxCoat;
//        boolean sleeping = llamaModelData.sleeping;
//
//        if (genes != null) {
//            // banana ears
//            if (genes[18] != 1 && genes[19] != 1) {
//                if (genes[18] == 2 || genes[19] == 2) {
//                    banana = true;
//                }
//            }
//
//            if (genes[20] == 2 && genes[21] == 2) {
//                suri = true;
//            }
//        }
//
//        float age = 1.0F;
//        if (!(llamaModelData.birthTime == null) && !llamaModelData.birthTime.equals("") && !llamaModelData.birthTime.equals("0")) {
//            int ageTime = (int)(llamaModelData.clientGameTime - Long.parseLong(llamaModelData.birthTime));
//            if (ageTime <= llamaModelData.adultAge) {
//                age = ageTime < 0 ? 0 : ageTime/(float) llamaModelData.adultAge;
//            }
//        }
//
//        float size = llamaModelData.size;
//        float finalLlamaSize = (( 2.0F * size * age) + size) / 3.0F;
//        float babyScale = 1.0F;
//        float d = 0.0F;
//        if (!sleeping) {
//            babyScale = (2.0F - age)/1;
//            d = 0.5F * (1.0F-age);
//        }
//
//        matrixStackIn.pushPose();
//        matrixStackIn.scale(finalLlamaSize, finalLlamaSize, finalLlamaSize);
//        matrixStackIn.translate(0.0F, (-1.5F + 1.5F / finalLlamaSize) - d, 0.0F);
//
//        if (llamaModelData.blink >= 6) {
//            this.eyeLeft.visible = true;
//            this.eyeRight.visible = true;
//        } else {
//            this.eyeLeft.visible = false;
//            this.eyeRight.visible = false;
//        }
//
//        this.earsTopR.visible = false;
//        this.earsTopL.visible = false;
//        this.earsTopBananaR.visible = false;
//        this.earsTopBananaL.visible = false;
//
//        if (banana){
//            this.earsTopBananaR.visible = true;
//            this.earsTopBananaL.visible = true;
//        }else {
//            this.earsTopR.visible = true;
//            this.earsTopL.visible = true;
//        }
//
//        if (llamaModelData.bridle!=null) {
//            if (llamaModelData.bridle.getItem() instanceof CustomizableBridle) {
//                this.bridle.visible = true;
//            } else {
//                this.bridle.visible = false;
//            }
//        }
//
//        renderCollar(llamaModelData.collar, coatlength, llamaModelData.unrenderedModels, matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
//
//        this.chest1.visible = false;
//        this.chest2.visible = false;
//        if (llamaModelData.hasChest) {
//            this.chest1.visible = true;
//            this.chest2.visible = true;
//            this.chest1.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
//            this.chest2.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
//        }
//
//                if (coatlength == 0) {
//                ImmutableList.of(this.neckWool0, this.body).forEach((renderer) -> {
//                        renderer.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
//                    });
//                } else if (coatlength == 1 ) {
//                    ImmutableList.of(this.neckWool1, this.body1).forEach((renderer) -> {
//                        renderer.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
//                    });
//                } else if (coatlength == 2 ) {
//                    ImmutableList.of(this.neckWool2, this.body2).forEach((renderer) -> {
//                        renderer.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
//                    });
//                } else if (coatlength == 3 ) {
//                    ImmutableList.of(this.neckWool3, this.body3).forEach((renderer) -> {
//                        renderer.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
//                    });
//                } else if (coatlength == 4 ) {
//                    ImmutableList.of(this.neckWool4, this.body4).forEach((renderer) -> {
//                        renderer.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
//                    });
//                } else {
//                    ImmutableList.of(this.neck, this.bodyShaved).forEach((renderer) -> {
//                        renderer.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
//                    });
//                }
//
//                if (maxCoatlength <= 0 || this.young) {
//                    this.tail.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
//                } else if (maxCoatlength == 1 ) {
//                    ImmutableList.of(this.tail1, leg1Wool1, leg2Wool1, leg3Wool1, leg4Wool1).forEach((renderer) -> {
//                        renderer.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
//                    });
//                } else if (maxCoatlength == 2 ) {
//                    ImmutableList.of(this.tail2, leg1Wool2, leg2Wool2, leg3Wool2, leg4Wool2).forEach((renderer) -> {
//                        renderer.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
//                    });
//                } else if (maxCoatlength == 3 ) {
//                    ImmutableList.of(this.tail3, leg1Wool3, leg2Wool3, leg3Wool3, leg4Wool3).forEach((renderer) -> {
//                        renderer.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
//                    });
//                } else {
//                    ImmutableList.of(this.tail4, leg1Wool4, leg2Wool4, leg3Wool4, leg4Wool4).forEach((renderer) -> {
//                        renderer.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
//                    });
//                }
//
//                renderSaddle(llamaModelData.saddle, coatlength, llamaModelData.unrenderedModels, matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
//
//        matrixStackIn.popPose();
//
//        matrixStackIn.pushPose();
//        matrixStackIn.scale(finalLlamaSize, finalLlamaSize * babyScale, finalLlamaSize);
//        matrixStackIn.translate(0.0F, -1.5F + 1.5F / (finalLlamaSize * babyScale), 0.0F);
//
//        ImmutableList.of(this.leg1, this.leg2, this.leg3, this.leg4).forEach((renderer) -> {
//            renderer.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
//        });
//            if (!sleeping) {
//                ImmutableList.of(this.toeOuterFrontR, this.toeInnerFrontR, this.toeOuterFrontL, this.toeInnerFrontL, this.toeOuterBackR, this.toeInnerBackR, this.toeOuterBackL, this.toeInnerBackL).forEach((renderer) -> {
//                    renderer.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
//                });
//            }
//
//        matrixStackIn.popPose();
//
//    }
//
//    private void renderCollar(boolean hasCollar, int coatLength, List<String> unrenderedModels, PoseStack matrixStackIn, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
//        Map<String, List<Float>> mapOfScale = new HashMap<>();
//        if (hasCollar) {
//            this.collar.visible = true;
//            List<Float> collarScale = ModelHelper.createScalings(0.9F, 1.0F, 0.75F, 0.0F, 0.0F, 0.080F);
//            List<Float> hardwareScale = ModelHelper.createScalings(1.0F/0.9F, 1.0F, 1.0F/0.75F, 0.0F, 0.0F, 0.048F);
//            if (coatLength == 0) {
//                collarScale = ModelHelper.createScalings(1.0F, 1.0F, 0.9F, 0.0F, 0.0F, 0.005F);
//                hardwareScale = ModelHelper.createScalings(1.0F, 1.0F, 1.0F/0.9F, 0.0F, 0.0F, 0.02F);
//            } else if (coatLength == 1) {
//                collarScale = ModelHelper.createScalings(1.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F);
//                hardwareScale = ModelHelper.createScalings(1.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F);
//            } else if (coatLength == 2) {
//                collarScale = ModelHelper.createScalings(1.125F, 1.0F, 1.125F, 0.0F, 0.0F, -0.00625F);
//                hardwareScale = ModelHelper.createScalings(1.0F/1.125F, 1.0F, 1.0F/1.125F, 0.0F, 0.0F, -0.02F);
//            } else if (coatLength == 3) {
//                collarScale = ModelHelper.createScalings(1.125F, 1.0F, 1.25F, 0.0F, 0.0F, -0.0125F);
//                hardwareScale = ModelHelper.createScalings(1.0F/1.125F, 1.0F, 1.0F/1.15F, 0.0F, 0.0F, -0.029F);
//            } else if (coatLength == 4) {
//                collarScale = ModelHelper.createScalings(1.25F, 1.0F, 1.3F, 0.0F, 0.0F, -0.015F);
//                hardwareScale = ModelHelper.createScalings(1.0F/1.25F, 1.0F, 1.0F/1.3F, 0.0F, 0.0F, -0.0565F);
//            }
//            mapOfScale.put("Collar", collarScale);
//            mapOfScale.put("CollarHardware", hardwareScale);
//        } else {
//            this.collar.visible = false;
//        }
//        this.neckBone.render(matrixStackIn, bufferIn , mapOfScale, unrenderedModels, false, packedLightIn, packedOverlayIn, red, green, blue, alpha);
//    }
//
//    private void renderSaddle(ItemStack saddleStack, int coatLength, List<String> unrenderedModels, PoseStack matrixStackIn, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
//        Map<String, List<Float>> mapOfScale = new HashMap<>();
//
//        this.saddleWestern.visible = false;
//        this.saddleEnglish.visible = false;
//        this.saddle.visible = false;
//        this.saddlePomel.visible = false;
//        this.saddleSideL.visible = true;
//        this.saddleSideR.visible = true;
//
//        float coatMod = 1.0F;
//
//        if (coatLength != 0) {
//            if (coatLength == -1) {
//                coatMod = 0.875F;
//            } else if (coatLength == 1) {
//                coatMod = 1.0625F;
//            } else if (coatLength == 2) {
//                coatMod = 1.15F;
//            } else if (coatLength == 3) {
//                coatMod = 1.25F;
//            } else {
//                coatMod = 1.375F;
//            }
//        }
//
//        if (saddleStack!=null) {
//            if (!saddleStack.isEmpty()) {
//                Item saddle = saddleStack.getItem();
//                float saddleScale = 0.875F;
//                List<Float> scalingsForSaddle = ModelHelper.createScalings(saddleScale, saddleScale, saddleScale, 0.0F, -saddleScale * 0.01F, (saddleScale - 1.0F) * 0.04F);
//                List<Float> scalingsForPad = ModelHelper.createScalings(coatMod, saddleScale, saddleScale, 0.0F, -saddleScale * 0.01F, (saddleScale - 1.0F) * 0.04F);
//
//                if (saddle instanceof CustomizableSaddleWestern) {
//                    this.saddleWestern.visible = true;
//                    this.saddlePomel.visible = true;
//                    mapOfScale.put("WesternSaddle", scalingsForSaddle);
//                    mapOfScale.put("SaddlePad", scalingsForPad);
//                    this.saddleWestern.render(matrixStackIn, bufferIn, mapOfScale, unrenderedModels, false, packedLightIn, packedOverlayIn, red, green, blue, alpha);
//                } else if (saddle instanceof CustomizableSaddleEnglish) {
//                    this.saddleEnglish.visible = true;
//                    mapOfScale.put("EnglishSaddle", scalingsForSaddle);
//                    mapOfScale.put("SaddlePad", scalingsForPad);
//                    this.saddleEnglish.render(matrixStackIn, bufferIn, mapOfScale, unrenderedModels, false, packedLightIn, packedOverlayIn, red, green, blue, alpha);
//                } else if (saddle instanceof CustomizableSaddleVanilla || saddle instanceof SaddleItem) {
//                    this.saddle.visible = true;
//                    mapOfScale.put("Saddle", scalingsForSaddle);
//                    mapOfScale.put("SaddlePad", scalingsForPad);
//                    this.saddleSideL.visible = false;
//                    this.saddleSideR.visible = false;
//                    this.saddle.render(matrixStackIn, bufferIn, mapOfScale, unrenderedModels, false, packedLightIn, packedOverlayIn, red, green, blue, alpha);
//                }
//            }
//        }
//    }
//
//        @Override
//    public void prepareMobModel(T entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTickTime) {
//        LlamaModelData llamaModelData = getCreateLlamaModelData(entitylivingbaseIn);
//        this.currentLlama = entitylivingbaseIn.getId();
//
//        boolean sleeping = llamaModelData.sleeping;
//        float onGround;
//
//        this.body.y = 2.0F;
//
//        if (sleeping) {
//            onGround = sleepingAnimation(young);
//        } else {
//            onGround = standingAnimation();
//        }
//
//        this.neck.y = onGround;
//    }
//
//    @Override
//    public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
//        //TODO if llama is angry turn ears back
//
//        LlamaModelData llamaModelData = getLlamaModelData();
//        int[] sharedGenes = llamaModelData.llamaGenes;
//        ItemStack saddleStack = llamaModelData.saddle;
//
//        this.neck.yRot = netHeadYaw * 0.017453292F;
//
//        if (!llamaModelData.sleeping) {
//            this.neck.xRot = headPitch * 0.017453292F;
//            this.leg1.xRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
//            this.leg2.xRot = Mth.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
//            this.leg3.xRot = Mth.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
//            this.leg4.xRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
//        }
//
//        ModelHelper.copyModelPositioning(neck, neckWool0);
//        ModelHelper.copyModelPositioning(neck, neckWool1);
//
//        ModelHelper.copyModelPositioning(body, bodyShaved);
//        ModelHelper.copyModelPositioning(body, body1);
//
//        ModelHelper.copyModelPositioning(leg1, leg1Wool1);
//        ModelHelper.copyModelPositioning(leg2, leg2Wool1);
//        ModelHelper.copyModelPositioning(leg3, leg3Wool1);
//        ModelHelper.copyModelPositioning(leg4, leg4Wool1);
//
//        ModelHelper.copyModelPositioning(body, tail);
//        ModelHelper.copyModelPositioning(body, tail1);
//
//        if ( suri && llamaModelData.coatlength >= 0 ) {
//            if (llamaModelData.coatlength >= 1) {
//                this.neckWool1.y = this.neck.y + (llamaModelData.coatlength/2.0F);
//                this.body1.y = this.body.y + (llamaModelData.coatlength/2.0F);
//                this.tail1.y = this.body.y + (llamaModelData.coatlength/3.0F);
//                this.leg1Wool1.y = this.leg1.y + (llamaModelData.coatlength/2.0F);
//                this.leg2Wool1.y = this.leg2.y + (llamaModelData.coatlength/2.0F);
//                this.leg3Wool1.y = this.leg3.y + (llamaModelData.coatlength/2.0F);
//                this.leg4Wool1.y = this.leg4.y + (llamaModelData.coatlength/2.0F);
//            }
//        }
//        ModelHelper.copyModelPositioning(neck, neckBone);
//        ModelHelper.copyModelPositioning(neck, neckWool0);
//        ModelHelper.copyModelPositioning(neck, neckWool1);
//        ModelHelper.copyModelPositioning(neckWool1, neckWool2);
//        ModelHelper.copyModelPositioning(neckWool1, neckWool3);
//        ModelHelper.copyModelPositioning(neckWool1, neckWool4);
//
////        copyModelPositioning(head, earsR);
////        copyModelPositioning(head, earsL);
////        copyModelPositioning(head, earsTopR);
////        copyModelPositioning(head, earsTopL);
////        copyModelPositioning(head, earsTopBananaR);
////        copyModelPositioning(head, earsTopBananaL);
//
//        ModelHelper.copyModelPositioning(body, body2);
//        ModelHelper.copyModelPositioning(body, body3);
//        ModelHelper.copyModelPositioning(body, body4);
//
//        ModelHelper.copyModelPositioning(tail1, tail2);
//        ModelHelper.copyModelPositioning(tail1, tail3);
//        ModelHelper.copyModelPositioning(tail1, tail4);
//
//        //TODO fix the toes so they angle properly and maintain the angle while they walk
//
//        ModelHelper.copyModelPositioning(leg1Wool1, leg1Wool2);
//        ModelHelper.copyModelPositioning(leg1Wool1, leg1Wool3);
//        ModelHelper.copyModelPositioning(leg1Wool1, leg1Wool4);
//        ModelHelper.copyModelPositioning(leg1, toeOuterFrontR);
//        ModelHelper.copyModelPositioning(leg1, toeInnerFrontR);
//
//
//        ModelHelper.copyModelPositioning(leg2Wool1, leg2Wool2);
//        ModelHelper.copyModelPositioning(leg2Wool1, leg2Wool3);
//        ModelHelper.copyModelPositioning(leg2Wool1, leg2Wool4);
//        ModelHelper.copyModelPositioning(leg2, toeOuterFrontL);
//        ModelHelper.copyModelPositioning(leg2, toeInnerFrontL);
//
//        ModelHelper.copyModelPositioning(leg3Wool1, leg3Wool2);
//        ModelHelper.copyModelPositioning(leg3Wool1, leg3Wool3);
//        ModelHelper.copyModelPositioning(leg3Wool1, leg3Wool4);
//        ModelHelper.copyModelPositioning(leg3, toeOuterBackR);
//        ModelHelper.copyModelPositioning(leg3, toeInnerBackR);
//
//        ModelHelper.copyModelPositioning(leg4Wool1, leg4Wool2);
//        ModelHelper.copyModelPositioning(leg4Wool1, leg4Wool3);
//        ModelHelper.copyModelPositioning(leg4Wool1, leg4Wool4);
//        ModelHelper.copyModelPositioning(leg4, toeOuterBackL);
//        ModelHelper.copyModelPositioning(leg4, toeInnerBackL);
//
//        setNoseRotations(sharedGenes, llamaModelData);
//
//        float coatLength = (float)llamaModelData.coatlength;
//        if (coatLength >= 1) {
//            coatLength = coatLength/1.825F;
//        } else if (coatLength == -1) {
//            coatLength = -1.25F;
//        }
//
//        if (saddleStack!=null) {
//            if (!saddleStack.isEmpty()) {
//                Item saddle = saddleStack.getItem();
//                if (saddle instanceof CustomizableSaddleWestern) {
//                    this.saddleWestern.y = 2.0F - coatLength;
//                    this.saddleSideL.setPos(4.75F, -1.0F, -5.25F);
//                    this.saddleSideR.setPos(-4.75F, -1.0F, -5.25F);
//                    this.saddleHorn.setPos(0.0F, -2.0F, -2.0F);
//                    this.saddleHorn.xRot = (float) Math.PI / 8.0F;
//                    this.saddlePomel.setPos(0.0F, -1.5F, -0.5F);
//                    this.saddlePomel.xRot = -0.2F;
//                    this.stirrup2DWideL.setPos(7.5F + coatLength, 0.0F, -3.5F);
//                    this.stirrup2DWideR.setPos(-7.5F - coatLength, 0.0F, -3.5F);
//                } else if (saddle instanceof CustomizableSaddleEnglish) {
//                    this.saddleEnglish.y = 2.0F - coatLength;
//                    this.saddleSideL.setPos(3.25F, -0.5F, -4.0F);
//                    this.saddleSideR.setPos(-3.25F, -0.5F, -4.0F);
//                    this.saddleHorn.setPos(0.0F, -1.0F, -1.0F);
//                    this.saddleHorn.xRot = (float) Math.PI / 4.5F;
//                    this.stirrup3DNarrowL.setPos(7.25F + coatLength, -0.25F, -1.5F);
//                    this.stirrup3DNarrowR.setPos(-7.25F - coatLength, -0.25F, -1.5F);
//                } else if (saddle instanceof CustomizableSaddleVanilla || saddle instanceof SaddleItem) {
//                    this.saddle.y = 2.0F - coatLength;
//                    this.stirrup3DNarrowL.setPos(7.5F + coatLength, 0.0F, 0.0F);
//                    this.stirrup3DNarrowR.setPos(-7.5F - coatLength, 0.0F, 0.0F);
//                }
//            }
//        }
//
//            ModelHelper.copyModelPositioning(this.nose, this.bridleNose);
//    }
//
//    private void setNoseRotations(int[] sharedGenes, LlamaModelData llamaModelData) {
//
//        float age = 1.0F;
//        if (!(llamaModelData.birthTime == null) && !llamaModelData.birthTime.equals("") && !llamaModelData.birthTime.equals("0")) {
//            int ageTime = (int)(llamaModelData.clientGameTime - Long.parseLong(llamaModelData.birthTime));
//            if (ageTime <= llamaModelData.adultAge) {
//                age = ageTime < 0 ? 0 : (float) ageTime/(float) llamaModelData.adultAge;
//            }
//        }
//
//        this.nose.z = 1.5F - (age*1.5F);
//
//        //range from -12.1 to 10.5
//        float noseHeight = 0F;
//        if (sharedGenes != null) {
//            if (sharedGenes[28] == 1) {
//                noseHeight = 0.1F;
//            } else if (sharedGenes[28] == 2) {
//                noseHeight = 0.15F;
//            } else if (sharedGenes[28] == 3) {
//                noseHeight = 0.0F;
//            } else {
//                noseHeight = -0.1F;
//            }
//
//            if (sharedGenes[29] == 1) {
//                noseHeight = noseHeight + 0.1F;
//            } else if (sharedGenes[28] == 2) {
//                noseHeight = noseHeight + 0.05F;
//            } else if (sharedGenes[28] == 3) {
//                noseHeight = noseHeight + 0.0F;
//            } else {
//                noseHeight = noseHeight - 0.1F;
//            }
//
//            if (sharedGenes[30] == 1) {
//                noseHeight = noseHeight + 0.1F;
//            } else if (sharedGenes[30] == 2) {
//                noseHeight = noseHeight + 0.15F;
//            } else if (sharedGenes[30] == 3) {
//                noseHeight = noseHeight + 0.0F;
//            } else {
//                noseHeight = noseHeight - 0.1F;
//            }
//
//            if (sharedGenes[31] == 1) {
//                noseHeight = noseHeight + 0.1F;
//            } else if (sharedGenes[31] == 2) {
//                noseHeight = noseHeight + 0.05F;
//            } else if (sharedGenes[31] == 3) {
//                noseHeight = noseHeight + 0.0F;
//            } else {
//                noseHeight = noseHeight - 0.1F;
//            }
//
//            if (sharedGenes[32] == 1) {
//                noseHeight = noseHeight + 0.2F;
//            } else if (sharedGenes[32] == 2) {
//                noseHeight = noseHeight + 0.15F;
//            } else if (sharedGenes[32] == 3) {
//                noseHeight = noseHeight + 0.0F;
//            } else if (sharedGenes[32] == 4) {
//                noseHeight = noseHeight - 0.15F;
//            } else {
//                noseHeight = noseHeight - 0.2F;
//            }
//
//            if (sharedGenes[33] == 1) {
//                noseHeight = noseHeight + 0.2F;
//            } else if (sharedGenes[33] == 2) {
//                noseHeight = noseHeight + 0.15F;
//            } else if (sharedGenes[33] == 3) {
//                noseHeight = noseHeight + 0.0F;
//            } else if (sharedGenes[33] == 4) {
//                noseHeight = noseHeight - 0.15F;
//            } else {
//                noseHeight = noseHeight - 0.2F;
//            }
//        }
//
//        this.nose.y = -0.3F - noseHeight;
//    }
//
//    private float sleepingAnimation(boolean isChild) {
//        float onGround;
//
//        if (isChild) {
//            onGround = 20.0F;
//            this.body.y = 14.0F;
//        } else {
//            onGround = 20.0F;
//            this.body.y = 11.0F;
//        }
//
//        this.neck.xRot = 1.6F;
//        this.neck.z = -9.0F;
//
//        this.head.xRot = -1.6F;
//
//        this.leg1.xRot = 1.575F;
//        this.leg1.yRot = 0.2F;
//        this.leg2.xRot = 1.575F;
//        this.leg2.yRot = -0.2F;
//        this.leg3.xRot = -1.575F;
//        this.leg4.xRot = -1.575F;
//
//        this.chest1.y = 12.0F;
//        this.chest2.y = 12.0F;
//
//        this.leg1.setPos(-5.0F, 24.0F, -10.0F);
//        this.leg2.setPos(2.0F, 24.0F, -10.0F);
//        this.leg3.setPos(-5.0F, 21.0F, 10.0F);
//        this.leg4.setPos(2.0F, 21.0F, 10.0F);
//        return onGround;
//    }
//
//    private float standingAnimation() {
//        float onGround;
//        onGround = 5.0F;
//
//        this.body.y = 2.0F;
//        this.leg1.yRot = 0.0F;
//        this.leg3.yRot = 0.0F;
//
//        this.neck.z = -10.0F;
//
//        this.head.xRot = 0.0F;
//
//        this.chest1.y = 3.0F;
//        this.chest2.y = 3.0F;
//
//        if (this.getLlamaModelData().angry) {
//            this.earsR.yRot = (float)Math.PI/4.0F;
//        }
//
//        this.leg1.setPos(-5.0F, 12.0F,-7.0F);
//        this.leg2.setPos(2.0F, 12.0F,-7.0F);
//        this.leg3.setPos(-5.0F, 12.0F,6.0F);
//        this.leg4.setPos(2.0F, 12.0F,6.0F);
//
//        return onGround;
//    }
//
//    private class LlamaModelData {
//        int[] llamaGenes;
//        int coatlength = 0;
//        int maxCoatlength = 0;
//        String birthTime;
//        boolean sleeping = false;
//        int blink = 0;
//        int lastAccessed = 0;
//        boolean angry = false;
//        long clientGameTime = 0;
////        int dataReset = 0;
//        List<String> unrenderedModels = new ArrayList<>();
//        ItemStack saddle;
//        ItemStack bridle;
//        ItemStack harness;
//        boolean collar = false;
//        boolean hasChest = false;
//        float size;
//        int adultAge;
//    }
//
//    private LlamaModelData getLlamaModelData() {
//        if (this.currentLlama == null || !llamaModelDataCache.containsKey(this.currentLlama)) {
//            return new LlamaModelData();
//        }
//        return llamaModelDataCache.get(this.currentLlama);
//    }
//
//    private LlamaModelData getCreateLlamaModelData(T enhancedLlama) {
//        clearCacheTimer++;
//        if(clearCacheTimer > 50000) {
//            llamaModelDataCache.values().removeIf(value -> value.lastAccessed==1);
//            for (LlamaModelData llamaModelData : llamaModelDataCache.values()){
//                llamaModelData.lastAccessed = 1;
//            }
//            clearCacheTimer = 0;
//        }
//
//        if (llamaModelDataCache.containsKey(enhancedLlama.getId())) {
//            LlamaModelData llamaModelData = llamaModelDataCache.get(enhancedLlama.getId());
//            llamaModelData.lastAccessed = 0;
////            llamaModelData.dataReset++;
////            if (llamaModelData.dataReset > 5000) {
////                llamaModelData.dataReset = 0;
////            }
//            llamaModelData.coatlength = enhancedLlama.getCoatLength();
//            llamaModelData.sleeping = enhancedLlama.isAnimalSleeping();
//            llamaModelData.blink = enhancedLlama.getBlink();
//            llamaModelData.birthTime = enhancedLlama.getBirthTime();
//            llamaModelData.angry = (enhancedLlama.isAggressive());
//            llamaModelData.clientGameTime = (((ClientLevel)enhancedLlama.level).getLevelData()).getGameTime();
//            llamaModelData.unrenderedModels = new ArrayList<>();
//            llamaModelData.saddle = enhancedLlama.getEnhancedInventory().getItem(1);
//            llamaModelData.bridle = enhancedLlama.getEnhancedInventory().getItem(3);
//            llamaModelData.harness = enhancedLlama.getEnhancedInventory().getItem(5);
//            llamaModelData.collar = hasCollar(enhancedLlama.getEnhancedInventory());
//            llamaModelData.hasChest = !enhancedLlama.getEnhancedInventory().getItem(0).isEmpty();
//
//            return llamaModelData;
//        } else {
//            LlamaModelData llamaModelData = new LlamaModelData();
//            if (enhancedLlama.getSharedGenes()!=null) {
//                llamaModelData.llamaGenes = enhancedLlama.getSharedGenes().getAutosomalGenes();
//            }
//            llamaModelData.coatlength = enhancedLlama.getCoatLength();
//            llama.maxCoat = enhancedLlama.getCoatLength();
//            llamaModelData.sleeping = enhancedLlama.isAnimalSleeping();
//            llamaModelData.blink = enhancedLlama.getBlink();
//            llamaModelData.birthTime = enhancedLlama.getBirthTime();
//            llamaModelData.angry = (!(enhancedLlama.getLastHurtByMob() == null));
//            llamaModelData.clientGameTime = (((ClientLevel)enhancedLlama.level).getLevelData()).getGameTime();
//            llamaModelData.saddle = enhancedLlama.getEnhancedInventory().getItem(1);
//            llamaModelData.bridle = enhancedLlama.getEnhancedInventory().getItem(3);
//            llamaModelData.harness = enhancedLlama.getEnhancedInventory().getItem(5);
//            llamaModelData.collar = hasCollar(enhancedLlama.getEnhancedInventory());
//            llamaModelData.hasChest = !enhancedLlama.getEnhancedInventory().getItem(0).isEmpty();
//            llamaModelData.size = enhancedLlama.getAnimalSize();
//            llamaModelData.adultAge = EanimodCommonConfig.COMMON.adultAgeLlama.get();
//
//            if(llamaModelData.llamaGenes != null) {
//                llamaModelDataCache.put(enhancedLlama.getId(), llamaModelData);
//            }
//
//            return llamaModelData;
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
//}
