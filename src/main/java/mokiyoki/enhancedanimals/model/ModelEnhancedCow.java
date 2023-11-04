package mokiyoki.enhancedanimals.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import mokiyoki.enhancedanimals.entity.EnhancedCow;
import mokiyoki.enhancedanimals.entity.EnhancedMoobloom;
import mokiyoki.enhancedanimals.entity.EnhancedMooshroom;
import mokiyoki.enhancedanimals.entity.EntityState;
import mokiyoki.enhancedanimals.model.modeldata.AnimalModelData;
import mokiyoki.enhancedanimals.model.modeldata.CowModelData;
import mokiyoki.enhancedanimals.model.modeldata.CowPhenotype;
import mokiyoki.enhancedanimals.model.modeldata.HornType;
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
import org.joml.Vector3f;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

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
    protected WrappedModelPart theLegBottomFrontLeft;
    protected WrappedModelPart theLegBottomFrontRight;
    protected WrappedModelPart theLegBottomBackLeft;
    protected WrappedModelPart theLegBottomBackRight;
    protected WrappedModelPart theTail;

    private final WrappedModelPart headFemale;
    private final WrappedModelPart headMale;
    private final WrappedModelPart noseFemale;
    private final WrappedModelPart noseMale;
    private final WrappedModelPart jaw;
    private final WrappedModelPart headHair;
    private final WrappedModelPart headNubXSmall;
    private final WrappedModelPart headNubSmall;
    private final WrappedModelPart headNubMedium;
    private final WrappedModelPart headNubLarge;
    private final WrappedModelPart headNubXLarge;

    private final WrappedModelPart earXShortL;
    private final WrappedModelPart earShortL;
    private final WrappedModelPart earMediumL;
    private final WrappedModelPart earLongL;
    private final WrappedModelPart earXLongL;
    private final WrappedModelPart earXShortR;
    private final WrappedModelPart earShortR;
    private final WrappedModelPart earMediumR;
    private final WrappedModelPart earLongR;
    private final WrappedModelPart earXLongR;

    private final WrappedModelPart hornLeft[] = new WrappedModelPart[10];
    private final WrappedModelPart hornRight[] = new WrappedModelPart[10];

    private final WrappedModelPart neckFemale;
    private final WrappedModelPart neckMale;

    private final WrappedModelPart bodyNormal;
    private final WrappedModelPart bodyHairy;

    private final WrappedModelPart humpXXSmall;
    private final WrappedModelPart humpXSmall;
    private final WrappedModelPart humpSmall;
    private final WrappedModelPart humpMedium;
    private final WrappedModelPart humpLarge;
    private final WrappedModelPart humpXLarge;
    private final WrappedModelPart humpXXLarge;

    private final WrappedModelPart udder;
    private final WrappedModelPart nipples;

    private final WrappedModelPart legFrontLeft;
    private final WrappedModelPart legFrontRight;
    private final WrappedModelPart legBackLeft;
    private final WrappedModelPart legBackRight;
    private final WrappedModelPart legFrontLeftDwarf;
    private final WrappedModelPart legFrontRightDwarf;
    private final WrappedModelPart legBackLeftDwarf;
    private final WrappedModelPart legBackRightDwarf;

    private final WrappedModelPart legBottomFrontLeft;
    private final WrappedModelPart legBottomFrontRight;
    private final WrappedModelPart legBottomBackLeft;
    private final WrappedModelPart legBottomBackRight;
    private final WrappedModelPart legBottomFrontLeftDwarf;
    private final WrappedModelPart legBottomFrontRightDwarf;
    private final WrappedModelPart legBottomBackLeftDwarf;
    private final WrappedModelPart legBottomBackRightDwarf;

    private WrappedModelPart tailBase;
    private WrappedModelPart tailMiddle;
    private WrappedModelPart tailEnd;
    private WrappedModelPart tailBrush;

    private WrappedModelPart mushroom[] = new WrappedModelPart[8];

    private WrappedModelPart bridleNose[] = new WrappedModelPart[2];

    private CowModelData cowModelData;

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition base = meshdefinition.getRoot().addOrReplaceChild("base", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition bCow = base.addOrReplaceChild("bCow", CubeListBuilder.create(), PartPose.offset(0.0F, 14.0F, 0.0F));
        PartDefinition bBody = bCow.addOrReplaceChild("bBody", CubeListBuilder.create(), PartPose.offset(0.0F, -11.0F, -10.0F));
        PartDefinition bHump = bBody.addOrReplaceChild("bHump", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 1.0F));
        PartDefinition bNeck = bBody.addOrReplaceChild("bNeck", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 2.0F));
        PartDefinition bHead = bNeck.addOrReplaceChild("bHead", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, -10.0F));
        PartDefinition bEarLeft = bHead.addOrReplaceChild("bEarL", CubeListBuilder.create(), PartPose.offset(4.0F, 0.0F, -3.0F));
        PartDefinition bEarRight = bHead.addOrReplaceChild("bEarR", CubeListBuilder.create(), PartPose.offset(-4.0F, 0.0F, -3.0F));
        PartDefinition bHornNub = bHead.addOrReplaceChild("bHornNub", CubeListBuilder.create(), PartPose.offset(0.0F, -1.0F, -0.5F));
        PartDefinition bHornLeft = bHornNub.addOrReplaceChild("bHornL", CubeListBuilder.create(), PartPose.offsetAndRotation(1.0F, 0.5F, -2.5F, Mth.HALF_PI, -Mth.HALF_PI, 0.0F));
        PartDefinition bHornRight = bHornNub.addOrReplaceChild("bHornR", CubeListBuilder.create(), PartPose.offsetAndRotation(-1.0F, 0.5F, -2.5F, Mth.HALF_PI, Mth.HALF_PI, 0.0F));
        PartDefinition bLegFrontLeft = bCow.addOrReplaceChild("bLegFL", CubeListBuilder.create(), PartPose.offset(3.0F, 0.0F, -10.0F));
        PartDefinition bLegFrontRight = bCow.addOrReplaceChild("bLegFR", CubeListBuilder.create(), PartPose.offset(-3.0F, 0.0F, -10.0F));
        PartDefinition bLegBackLeft = bCow.addOrReplaceChild("bLegBL", CubeListBuilder.create(), PartPose.offset(3.0F, 0.0F, 6.0F));
        PartDefinition bLegBackRight = bCow.addOrReplaceChild("bLegBR", CubeListBuilder.create(), PartPose.offset(-3.0F, 0.0F, 6.0F));
            bCow.addOrReplaceChild("bLegBFL", CubeListBuilder.create(), PartPose.offset(0.0F, 5.0F, 0.0F));
            bCow.addOrReplaceChild("bLegBFR", CubeListBuilder.create(), PartPose.offset(0.0F, 5.0F, 0.0F));
            bCow.addOrReplaceChild("bLegBBL", CubeListBuilder.create(), PartPose.offset(0.0F, 5.0F, 0.0F));
            bCow.addOrReplaceChild("bLegBBR", CubeListBuilder.create(), PartPose.offset(0.0F, 5.0F, 0.0F));
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
                        .addBox(-0.5F, -7.0F, -0.5F, 3, 6, 1, new CubeDeformation(0.5F, 0.5F, 0.0F)),
                PartPose.ZERO
        );
        bEarLeft.addOrReplaceChild("earXLL", CubeListBuilder.create()
                        .texOffs(8, 51)
                        .addBox(-1.0F, -9.0F, -0.5F, 3, 7, 1, new CubeDeformation(1.0F, 1.0F, 0.0F)),
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
                        .addBox(-2.5F, -6.5F, -0.5F, 3, 6, 1, new CubeDeformation(0.5F, 0.5F, 0.0F)),
                PartPose.ZERO
        );
        bEarRight.addOrReplaceChild("earXLR", CubeListBuilder.create()
                        .texOffs(0, 51)
                        .addBox(-2.0F, -8.0F, -0.5F, 3, 7, 1, new CubeDeformation(1.0F, 1.0F, 0.0F)),
                PartPose.ZERO
        );

        for (int i = 0; i < 10; i++) {
            float scale = i < 5 ? -1.0F - (i*0.001F) : -1.0F - ((i-4)*0.1F);
            CubeListBuilder hornBitL = CubeListBuilder.create()
                    .texOffs(57, i < 5 ? 85 + (i*8) : 117)
                    .addBox(-2.0F, -4.0F, -2.0F, 4, 4, 4, new CubeDeformation(scale));
            CubeListBuilder hornBitR = CubeListBuilder.create()
                    .texOffs(57, i < 5 ? 85 + (i*8) : 117)
                    .addBox(-2.0F, -4.0F, -2.0F, 4, 4, 4, new CubeDeformation(scale));
            bHornLeft.addOrReplaceChild("hornL"+i, hornBitL, PartPose.ZERO);
            bHornRight.addOrReplaceChild("hornR"+i, hornBitR, PartPose.ZERO);
        }

        bNeck.addOrReplaceChild("neckF", CubeListBuilder.create()
                        .texOffs(46, 0)
                        .addBox(-3.0F, 0.5F, -11.0F, 6, 7, 11),
                PartPose.ZERO
        );
        bNeck.addOrReplaceChild("neckM", CubeListBuilder.create()
                        .texOffs(46, 0)
                        .addBox(-3.0F, 0.0001F, -11.0F, 6, 8, 11),
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

        bBody.addOrReplaceChild("udder", CubeListBuilder.create()
                        .texOffs(24, 72)
                        .addBox(-2.5F, -2.5F, -4.5F, 5, 5, 5, new CubeDeformation(-0.5F, -0.5F, 0.5F)),
                PartPose.offset(0.0F, 10.0F, 21.25F)
        );
        bBody.addOrReplaceChild("nipples", CubeListBuilder.create()
                        .texOffs(24, 82)
                        .addBox(-2.0F, 0.0F, -1.0F, 1, 2, 1, new CubeDeformation(-0.15F))
                        .texOffs(29, 82)
                        .addBox(1.0F, 0.0F, -1.0F, 1, 2, 1, new CubeDeformation(-0.15F))
                        .texOffs(35, 82)
                        .addBox(-2.0F, 0.0F, 2.0F, 1, 2, 1, new CubeDeformation(-0.15F))
                        .texOffs(40, 82)
                        .addBox(1.0F, 0.0F, 2.0F, 1, 2, 1, new CubeDeformation(-0.15F)),
                PartPose.offset(0.0F, 1.5F, -3.5F)
        );

        bHump.addOrReplaceChild("humpXXS", CubeListBuilder.create()
                        .texOffs(0,8)
                        .addBox(-2.0F, 0.0F, 0.0F, 4, 8, 6, new CubeDeformation(-1.0F)),
                PartPose.offset(0.0F, 0.0F, -3.0F)
        );
        bHump.addOrReplaceChild("humpXS", CubeListBuilder.create()
                        .texOffs(0,8)
                        .addBox(-2.0F, 0.0F, 0.0F, 4, 8, 6, new CubeDeformation(-0.5F)),
                PartPose.offset(0.0F, 0.0F, -3.0F)
        );
        bHump.addOrReplaceChild("humpS", CubeListBuilder.create()
                        .texOffs(0,8)
                        .addBox(-2.0F, 0.0F, 0.0F, 4, 8, 6, new CubeDeformation(-0.25F)),
                PartPose.offset(0.0F, 0.0F, -3.0F)
        );
        bHump.addOrReplaceChild("humpM", CubeListBuilder.create()
                        .texOffs(0,8)
                        .addBox(-2.0F, 0.0F, 0.0F, 4, 8, 6),
                PartPose.offset(0.0F, 0.0F, -3.0F)
        );
        bHump.addOrReplaceChild("humpL", CubeListBuilder.create()
                        .texOffs(0,8)
                        .addBox(-2.0F, 0.0F, 0.0F, 4, 8, 6, new CubeDeformation(0.5F)),
                PartPose.offset(0.0F, 0.0F, -3.0F)
        );
        bHump.addOrReplaceChild("humpXL", CubeListBuilder.create()
                        .texOffs(0,8)
                        .addBox(-2.0F, 0.0F, 0.0F, 4, 8, 6, new CubeDeformation(1.0F)),
                PartPose.offset(0.0F, 0.0F, -3.0F)
        );
        bHump.addOrReplaceChild("humpXXL", CubeListBuilder.create()
                        .texOffs(0,8)
                        .addBox(-2.0F, 0.0F, 0.0F, 4, 8, 6, new CubeDeformation(1.5F)),
                PartPose.offset(0.0F, 0.0F, -3.0F)
        );

        bLegFrontLeft.addOrReplaceChild("legFL", CubeListBuilder.create()
                        .texOffs(0, 59)
                        .addBox(0.0F, 0.0F, 0.0F, 3, 5, 3),
                PartPose.ZERO
        );
        bLegFrontLeft.addOrReplaceChild("legFLD", CubeListBuilder.create()
                        .texOffs(0, 59)
                        .addBox(0.0F, 0.0F, 0.0F, 3, 4, 3),
                PartPose.ZERO
        );
        bLegFrontLeft.addOrReplaceChild("legBFL", CubeListBuilder.create()
                        .texOffs(0, 64)
                        .addBox(0.0F, 0.0F, 0.0F, 3, 5, 3),
                PartPose.ZERO
        );
        bLegFrontLeft.addOrReplaceChild("legBFLD", CubeListBuilder.create()
                        .texOffs(0, 62)
                        .addBox(0.0F, 0.0F, 0.0F, 3, 4, 3),
                PartPose.ZERO
        );


        bLegFrontRight.addOrReplaceChild("legFR", CubeListBuilder.create()
                        .texOffs(12, 59)
                        .addBox(-3.0F, 0.0F, 0.0F, 3, 5, 3),
                PartPose.ZERO
        );
        bLegFrontRight.addOrReplaceChild("legFRD", CubeListBuilder.create()
                        .texOffs(12, 59)
                        .addBox(-3.0F, 0.0F, 0.0F, 3, 4, 3),
                PartPose.ZERO
        );
        bLegFrontRight.addOrReplaceChild("legBFR", CubeListBuilder.create()
                        .texOffs(12, 64)
                        .addBox(-3.0F, 0.0F, 0.0F, 3, 5, 3),
                PartPose.ZERO
        );
        bLegFrontRight.addOrReplaceChild("legBFRD", CubeListBuilder.create()
                        .texOffs(12, 62)
                        .addBox(-3.0F, 0.0F, 0.0F, 3, 4, 3),
                PartPose.ZERO
        );

        bLegBackLeft.addOrReplaceChild("legBL", CubeListBuilder.create()
                        .texOffs(0, 72)
                        .addBox(0.0F, 0.0F, 0.0F, 3, 5, 3),
                PartPose.ZERO
        );
        bLegBackLeft.addOrReplaceChild("legBLD", CubeListBuilder.create()
                        .texOffs(0, 72)
                        .addBox(0.0F, 0.0F, 0.0F, 3, 4, 3),
                PartPose.ZERO
        );
        bLegBackLeft.addOrReplaceChild("legBBL", CubeListBuilder.create()
                        .texOffs(0, 77)
                        .addBox(0.0F, 0.0F, 0.0F, 3, 5, 3),
                PartPose.ZERO
        );
        bLegBackLeft.addOrReplaceChild("legBBLD", CubeListBuilder.create()
                        .texOffs(0, 75)
                        .addBox(0.0F, 0.0F, 0.0F, 3, 4, 3),
                PartPose.ZERO
        );

        bLegBackRight.addOrReplaceChild("legBR", CubeListBuilder.create()
                        .texOffs(12, 72)
                        .addBox(-3.0F, 0.0F, 0.0F, 3, 5, 3),
                PartPose.ZERO
        );
        bLegBackRight.addOrReplaceChild("legBRD", CubeListBuilder.create()
                        .texOffs(12, 72)
                        .addBox(-3.0F, 0.0F, 0.0F, 3, 4, 3),
                PartPose.ZERO
        );
        bLegBackRight.addOrReplaceChild("legBBR", CubeListBuilder.create()
                        .texOffs(12, 77)
                        .addBox(-3.0F, 0.0F, 0.0F, 3, 5, 3),
                PartPose.ZERO
        );
        bLegBackRight.addOrReplaceChild("legBBRD", CubeListBuilder.create()
                        .texOffs(12, 75)
                        .addBox(-3.0F, 0.0F, 0.0F, 3, 4, 3),
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
                        .addBox(-1.0F, 0.0F, -0.5F, 2, 4, 2),
                PartPose.offset(0.0F, 3.0F, 0.0F)
        );

        CubeListBuilder mushroomBox = CubeListBuilder.create()
                .texOffs(54, 64)
                .addBox(0.0F, -8.0F, -4.0F, 1, 8, 8)
                .texOffs(62, 71)
                .addBox(-3.0F, -8.0F, 0.0F, 8, 8, 1);
        for (int i = 0; i < 8; i++) {
            bCow.addOrReplaceChild("mushroom"+i, mushroomBox, PartPose.ZERO);
        }

        /**
         *      Equipment Stuff
         */

        base.addOrReplaceChild("blanket", CubeListBuilder.create()
                        .texOffs(25, 61)
                        .addBox(1.0F, 0.0F, -5.0F, 4, 9, 1, new CubeDeformation(0.001F))
                        .texOffs(34, 61)
                        .addBox(1.0F, 0.0F, 4.0F, 4, 9, 1, new CubeDeformation(0.001F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, Mth.HALF_PI, 0.0F)
        );

        base.addOrReplaceChild("chestL", CubeListBuilder.create()
                        .texOffs(80, 0)
                        .addBox(-3.0F, 0.0F, 0.0F, 8, 8, 3),
                PartPose.offsetAndRotation(-8.75F, 3.0F, 17.0F, 0.0F, Mth.HALF_PI, 0.0F)
        );
        base.addOrReplaceChild("chestR", CubeListBuilder.create()
                        .texOffs(80, 11)
                        .addBox(-3.0F, 0.0F, 0.0F, 8, 8, 3),
                PartPose.offsetAndRotation(0.0F, 0.0F, 14.5F, 0.0F, 0.0F, 0.0F)
        );

        base.addOrReplaceChild("collar", CubeListBuilder.create()
                        .texOffs(80, 68)
                        .addBox(-3.5F, 1.0F, -0.5F, 7,  2, 9)
                        .texOffs(81, 71)
                        .addBox(0.0F, 0.5F, 8.5F, 0,  3, 3)
                        .texOffs(103, 71)
                        .addBox(-1.5F, 0.5F, 10.0F, 3, 3, 3, new CubeDeformation(-0.25F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, -5.0F, -Mth.HALF_PI, 0.0F, 0.0F)
        );

        base.addOrReplaceChild("bridle", CubeListBuilder.create()
                        .texOffs(0, 92)
                        .addBox(-8.0F, 0.0F, -14.0F, 16, 14, 12, new CubeDeformation(-3.8F, -3.2F, -2.8F)),
                PartPose.offset(0.0F, -3.0F, 4.0F)
        );
        base.addOrReplaceChild("bridleM", CubeListBuilder.create()
                        .texOffs(0, 118)
                        .addBox(-4.0F, -0.15F, -10.0F, 8, 12, 8, new CubeDeformation(-1.65F, -2.65F, -1.65F)),
                PartPose.offset(0.0F, 0.0F, -5.0F)
        );
        base.addOrReplaceChild("bridleF", CubeListBuilder.create()
                        .texOffs(0, 118)
                        .addBox(-4.0F, 0.6F, -10.75F, 8, 12, 8, new CubeDeformation(-2.15F, -3.15F, -2.15F)),
                PartPose.offset(0.0F, 0.0F, -5.0F)
        );

        base.addOrReplaceChild("saddle", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 8.0F));
        base.addOrReplaceChild("saddleW", CubeListBuilder.create()
                        .texOffs(210, 0)
                        .addBox(-5.0F, -2.0F, -5.0F, 10, 2, 13)
                        .texOffs(210, 15)
                        .addBox(-4.0F, -3.0F, 5.0F, 8, 2, 4)
                        .texOffs(230, 15)
                        .addBox(-3.5F, -4.0F, 8.0F, 7, 2, 2),
                PartPose.offset(0.0F, 0.0F, 8.0F)
        );
        base.addOrReplaceChild("saddleE", CubeListBuilder.create()
                        .texOffs(211, 1)
                        .addBox(-5.0F, -1.0F, -4.0F, 10, 2, 12)
                        .texOffs(210, 15)
                        .addBox(-4.0F, -1.5F, 5.0F, 8, 2, 4)
                        .texOffs(230, 15)
                        .addBox(-3.5F, -2.0F, 7.5F, 7, 2, 2),
                PartPose.offset(0.0F, 0.0F, 8.0F)
        );
        base.addOrReplaceChild("saddleH", CubeListBuilder.create()
                        .texOffs(234, 19)
                        .addBox(-4.0F, -2.0F, -3.0F, 8, 2, 3),
                PartPose.ZERO
        );
        base.addOrReplaceChild("saddleP", CubeListBuilder.create()
                        .texOffs(243, 0)
                        .addBox(-1.0F, -3.0F, -2.0F, 2, 4, 2, new CubeDeformation(-0.25F)),
                PartPose.offset(0.0F, -2.0F, -2.0F)
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
                PartPose.offset(0.0F, 0.0F, 0.0F)
        );
        base.addOrReplaceChild("stirrupWR", CubeListBuilder.create()
                        .texOffs(248, 24)
                        .addBox(0.0F, 0.0F, 0.0F, 0, 10, 4),
                PartPose.offset(0.0F, 0.0F, 0.0F)
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

        return LayerDefinition.create(meshdefinition, 256, 256);
    }

    public ModelEnhancedCow(ModelPart modelPart, boolean isMooshroom) {
        super(modelPart);
        ModelPart base = modelPart.getChild("base");
        ModelPart bCow = base.getChild("bCow");
        ModelPart bBody = bCow.getChild("bBody");
        ModelPart bHump = bBody.getChild("bHump");
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
        this.theHump = new WrappedModelPart(bHump, "bHump");
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
        this.theLegBottomFrontLeft = new WrappedModelPart("bLegBFL", bCow);
        this.theLegBottomFrontRight = new WrappedModelPart("bLegBFR", bCow);
        this.theLegBottomBackLeft = new WrappedModelPart("bLegBBL", bCow);
        this.theLegBottomBackRight = new WrappedModelPart("bLegBBR", bCow);
        this.theTail = new WrappedModelPart(bTail, "bTail");

        this.bodyNormal = new WrappedModelPart("bodyN", bBody);
        this.bodyHairy = new WrappedModelPart("bodyH", bBody);

        this.udder = new WrappedModelPart("udder", bBody);
        this.nipples = new WrappedModelPart("nipples", bBody);

        this.humpXXSmall = new WrappedModelPart("humpXXS", bHump);
        this.humpXSmall = new WrappedModelPart("humpXS", bHump);
        this.humpSmall = new WrappedModelPart("humpS", bHump);
        this.humpMedium = new WrappedModelPart("humpM", bHump);
        this.humpLarge = new WrappedModelPart("humpL", bHump);
        this.humpXLarge = new WrappedModelPart("humpXL", bHump);
        this.humpXXLarge = new WrappedModelPart("humpXXL", bHump);

        this.neckFemale = new WrappedModelPart("neckF", bNeck);
        this.neckMale = new WrappedModelPart("neckM", bNeck);

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

        for (int i = 9; i >= 0; i--) {
            this.hornLeft[i] = i !=9 ? new WrappedModelPart("hornL"+i, bHornLeft, this.hornLeft[i+1]) : new WrappedModelPart("hornL"+i, bHornLeft);
            this.hornRight[i] = i !=9 ? new WrappedModelPart("hornR"+i, bHornRight, this.hornRight[i+1]) : new WrappedModelPart("hornR"+i, bHornRight);
        }

        this.legFrontLeft = new WrappedModelPart("legFL", bLegFL);
        this.legFrontRight = new WrappedModelPart("legFR", bLegFR);
        this.legBackLeft = new WrappedModelPart("legBL", bLegBL);
        this.legBackRight = new WrappedModelPart("legBR", bLegBR);
        this.legFrontLeftDwarf = new WrappedModelPart("legFLD", bLegFL);
        this.legFrontRightDwarf = new WrappedModelPart("legFRD", bLegFR);
        this.legBackLeftDwarf = new WrappedModelPart("legBLD", bLegBL);
        this.legBackRightDwarf = new WrappedModelPart("legBRD", bLegBR);

        this.legBottomFrontLeft = new WrappedModelPart("legBFL", bLegFL);
        this.legBottomFrontRight = new WrappedModelPart("legBFR", bLegFR);
        this.legBottomBackLeft = new WrappedModelPart("legBBL", bLegBL);
        this.legBottomBackRight = new WrappedModelPart("legBBR", bLegBR);
        this.legBottomFrontLeftDwarf = new WrappedModelPart("legBFLD", bLegFL);
        this.legBottomFrontRightDwarf = new WrappedModelPart("legBFRD", bLegFR);
        this.legBottomBackLeftDwarf = new WrappedModelPart("legBBLD", bLegBL);
        this.legBottomBackRightDwarf = new WrappedModelPart("legBBRD", bLegBR);

        this.tailBase = new WrappedModelPart("tail0", bTail);
        this.tailMiddle = new WrappedModelPart("tail1", bTail);
        this.tailEnd = new WrappedModelPart("tail2", bTail);
        this.tailBrush = new WrappedModelPart("tailB", bTail);

        this.theCow.addChild(this.theBody);
        this.theBody.addChild(this.theNeck);
        this.theBody.addChild(this.theHump);
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
        this.theLegFrontLeft.addChild(this.theLegBottomFrontLeft);
        this.theLegFrontRight.addChild(this.theLegBottomFrontRight);
        this.theLegBackLeft.addChild(this.theLegBottomBackLeft);
        this.theLegBackRight.addChild(this.theLegBottomBackRight);
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

        this.theHornLeft.addChild(this.hornLeft[0]);
        this.theHornRight.addChild(this.hornRight[0]);

        this.theEarRight.addChild(this.earXShortR);
        this.theEarRight.addChild(this.earShortR);
        this.theEarRight.addChild(this.earMediumR);
        this.theEarRight.addChild(this.earLongR);
        this.theEarRight.addChild(this.earXLongR);

        this.theNeck.addChild(this.neckFemale);
        this.theNeck.addChild(this.neckMale);

        this.theBody.addChild(this.bodyNormal);
        this.theBody.addChild(this.bodyHairy);
        this.theBody.addChild(this.udder);
        this.udder.addChild(this.nipples);

        this.theHump.addChild(this.humpXXSmall);
        this.theHump.addChild(this.humpXSmall);
        this.theHump.addChild(this.humpSmall);
        this.theHump.addChild(this.humpMedium);
        this.theHump.addChild(this.humpLarge);
        this.theHump.addChild(this.humpXLarge);
        this.theHump.addChild(this.humpXXLarge);

        this.theLegFrontLeft.addChild(this.legFrontLeft);
        this.theLegFrontLeft.addChild(this.legFrontLeftDwarf);

        this.theLegFrontRight.addChild(this.legFrontRight);
        this.theLegFrontRight.addChild(this.legFrontRightDwarf);

        this.theLegBackLeft.addChild(this.legBackLeft);
        this.theLegBackLeft.addChild(this.legBackLeftDwarf);

        this.theLegBackRight.addChild(this.legBackRight);
        this.theLegBackRight.addChild(this.legBackRightDwarf);

        this.theLegBottomFrontLeft.addChild(this.legBottomFrontLeft);
        this.theLegBottomFrontLeft.addChild(this.legBottomFrontLeftDwarf);

        this.theLegBottomFrontRight.addChild(this.legBottomFrontRight);
        this.theLegBottomFrontRight.addChild(this.legBottomFrontRightDwarf);

        this.theLegBottomBackLeft.addChild(this.legBottomBackLeft);
        this.theLegBottomBackLeft.addChild(this.legBottomBackLeftDwarf);

        this.theLegBottomBackRight.addChild(this.legBottomBackRight);
        this.theLegBottomBackRight.addChild(this.legBottomBackRightDwarf);

        this.theTail.addChild(this.tailBase);
        this.tailBase.addChild(this.tailMiddle);
        this.tailMiddle.addChild(this.tailEnd);
        this.tailEnd.addChild(this.tailBrush);

        if (isMooshroom) {
            for (int i = 0; i < 8; i++) {
                this.mushroom[i] = new WrappedModelPart("mushroom"+i, bCow);
                if (i < 5) {
                    this.theCow.addChild(this.mushroom[i]);
                } else if (i < 7) {
                    this.theNeck.addChild(this.mushroom[i]);
                } else {
                    this.theHornNub.addChild(this.mushroom[i]);
                }
            }
        }

        /**
         *      Equipment
         */

        this.chests = new WrappedModelPart("chestL", base);
        this.chestsR = new WrappedModelPart("chestR", base);
        this.collar = new WrappedModelPart("collar", base);
        this.blanket = new WrappedModelPart("blanket", base);
        this.bridle = new WrappedModelPart("bridle", base);
        this.bridleNose[0] = new WrappedModelPart("bridleM", base);
        this.bridleNose[1] = new WrappedModelPart("bridleF", base);
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

        this.theBody.addChild(this.saddleVanilla);
        this.theBody.addChild(this.saddleWestern);
        this.theBody.addChild(this.saddleEnglish);

        this.saddleVanilla.addChild(this.saddlePad);
        this.saddleVanilla.addChild(this.stirrupNarrowLeft);
        this.saddleVanilla.addChild(this.stirrupNarrowRight);

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

        this.theNeck.addChild(this.collar);
        this.theHead.addChild(this.blanket);
        this.theHead.addChild(this.bridle);
        this.theBody.addChild(this.chests);
        this.chests.addChild(this.chestsR);
        this.bridle.addChild(this.bridleNose[0]);
        this.bridle.addChild(this.bridleNose[1]);
        this.saddleHorn.addChild(this.saddlePomel);
        this.stirrupWideLeft.addChild(this.stirrup);
        this.stirrupWideRight.addChild(this.stirrup);
        this.stirrupNarrowLeft.addChild(this.stirrup);
        this.stirrupNarrowRight.addChild(this.stirrup);

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

        for (WrappedModelPart part : this.theHump.children) {
            part.hide();
        }
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        if (this.cowModelData != null && this.cowModelData.getPhenotype() != null) {
            CowPhenotype cow = this.cowModelData.getPhenotype();

            resetCubes();

            super.renderToBuffer(this.cowModelData, poseStack, vertexConsumer, packedLightIn, packedOverlayIn, red, green, blue, alpha);
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

            this.bridleNose[0].show(!cow.isFemale);
            this.bridleNose[1].show(cow.isFemale);

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

            if (this.theHornLeft.show(cowModelData.offsets.containsKey("bHornL"))) {
                for (int i = 0; i < 10; i++) {
                    this.hornLeft[i].boxIsRendered = cowModelData.offsets.containsKey("hL"+i);
                }
            }
            if (this.theHornRight.show(cowModelData.offsets.containsKey("bHornR"))) {
                for (int i = 0; i < 10; i++) {
                    this.hornRight[i].boxIsRendered = cowModelData.offsets.containsKey("hR"+i);
                }
            }

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
                this.legBottomFrontLeftDwarf.show();
                this.legBottomFrontRightDwarf.show();
                this.legBottomBackLeftDwarf.show();
                this.legBottomBackRightDwarf.show();

                this.legFrontLeft.hide();
                this.legFrontRight.hide();
                this.legBackLeft.hide();
                this.legBackRight.hide();
                this.legBottomFrontLeft.hide();
                this.legBottomFrontRight.hide();
                this.legBottomBackLeft.hide();
                this.legBottomBackRight.hide();
            } else {
                this.legFrontLeft.show();
                this.legFrontRight.show();
                this.legBackLeft.show();
                this.legBackRight.show();
                this.legBottomFrontLeft.show();
                this.legBottomFrontRight.show();
                this.legBottomBackLeft.show();
                this.legBottomBackRight.show();

                this.legFrontLeftDwarf.hide();
                this.legFrontRightDwarf.hide();
                this.legBackLeftDwarf.hide();
                this.legBackRightDwarf.hide();
                this.legBottomFrontLeftDwarf.hide();
                this.legBottomFrontRightDwarf.hide();
                this.legBottomBackLeftDwarf.hide();
                this.legBottomBackRightDwarf.hide();
            }

            /**
             *      Hump
             */
            switch (cow.hump) {
                case 1 -> this.humpXXSmall.show();
                case 2, 3 -> this.humpXSmall.show();
                case 4, 5 -> this.humpSmall.show();
                case 6, 7 -> this.humpMedium.show();
                case 8, 9 -> this.humpLarge.show();
                case 10, 11 -> this.humpXLarge.show();
                case 12 -> this.humpXXLarge.show();
            }

            /**
             *      Udder
             */
            if (cowModelData.bagSize != -1.0F) {
                this.udder.show();
                this.nipples.show();
                float nipScale = 1.5F / (0.5F + cowModelData.bagSize);
                float bagthickness = cowModelData.bagSize * cowModelData.bagSize;
                mapOfScale.put("udder", ModelHelper.createScalings(bagthickness, cowModelData.bagSize, bagthickness, 0.0F, 0.0F, 0.0F));
                mapOfScale.put("nipples", ModelHelper.createScalings(nipScale, nipScale, nipScale, 0.0F, (cowModelData.bagSize - 1.0F) * 0.05F, 0.0F));
            } else {
                this.udder.hide();
                this.nipples.hide();
            }

            float babyScale;

            if (cowModelData.growthAmount != 1.0F) {
                babyScale = 2.0F - cowModelData.growthAmount;
                mapOfScale.put("bLegFL", ModelHelper.createScalings(1.0F, babyScale, 1.0F, 0.0F, 0.0F, 0.0F));
                mapOfScale.put("bLegFR", ModelHelper.createScalings(1.0F, babyScale, 1.0F, 0.0F, 0.0F, 0.0F));
                mapOfScale.put("bLegBL", ModelHelper.createScalings(1.0F, babyScale, 1.0F, 0.0F, 0.0F, 0.0F));
                mapOfScale.put("bLegBR", ModelHelper.createScalings(1.0F, babyScale, 1.0F, 0.0F, 0.0F, 0.0F));
            }

            if (cow.mushrooms != null) {
                for (int i = 0; i < 8; i++) {
                    if (this.mushroom[i].modelPart.visible = cowModelData.offsets.containsKey("mushroom"+i)) {
                        if (cowModelData.growthAmount != 1.0F) {
                            mapOfScale.put("mushroom" + i, ModelHelper.createScalings(cowModelData.growthAmount, 0.0F, 0.0F, 0.0F));
                        }
                    }
                }
            }

            poseStack.pushPose();

            float d = 0.0F;
            if (!cowModelData.sleeping) {
                if (cow.dwarf) {
                    d = 0.2F * (1.0F - cowModelData.growthAmount);
                } else {
                    d = 0.3F * (1.0F - cowModelData.growthAmount);
                }
            }

            float finalCowSize = ((((cow.isFemale ? 1.8F : 2.0F) * cowModelData.growthAmount) + 1.0F) / 3.0F) * cowModelData.size;
            float width = finalCowSize + (finalCowSize * cow.bodyWidth * cowModelData.growthAmount);
            float length = finalCowSize + (finalCowSize * cow.bodyLength * cowModelData.growthAmount);
            poseStack.scale(width, finalCowSize, length);
            poseStack.translate(0.0F, (-1.35F + (1.35F / finalCowSize)) - d, 0.0F);

            gaRender(this.theCow, mapOfScale, poseStack, vertexConsumer, packedLightIn, packedOverlayIn, red, green, blue, alpha);

            poseStack.popPose();
        }
    }

    protected void saveAnimationValues(CowModelData data, CowPhenotype cow) {
        Map<String, Vector3f> map = data.offsets;
        map.put("bCowPos", this.getPosVector(this.theCow));
        map.put("bHumpPos", this.getPosVector(this.theHump));
        map.put("bCow", this.getRotationVector(this.theCow));
        map.put("bBody", this.getRotationVector(this.theBody));
        map.put("bHump", this.getRotationVector(this.theHump));
        map.put("bNeck", this.getRotationVector(this.theNeck));
        map.put("bHead", this.getRotationVector(this.theHead));
        map.put("jaw", this.getRotationVector(this.jaw));
        map.put("bEarL", this.getRotationVector(this.theEarLeft));
        map.put("bEarR", this.getRotationVector(this.theEarRight));
        map.put("bLegFL", this.getRotationVector(this.theLegFrontLeft));
        map.put("bLegFLPos", this.getPosVector(this.theLegFrontLeft));
        map.put("bLegFR", this.getRotationVector(this.theLegFrontRight));
        map.put("bLegFRPos", this.getPosVector(this.theLegFrontRight));
        map.put("bLegBL", this.getRotationVector(this.theLegBackLeft));
        map.put("bLegBLPos", this.getPosVector(this.theLegBackLeft));
        map.put("bLegBR", this.getRotationVector(this.theLegBackRight));
        map.put("bLegBRPos", this.getPosVector(this.theLegBackRight));
        map.put("bLegBFLPos", this.getPosVector(this.theLegBottomFrontLeft));
        map.put("bLegBFRPos", this.getPosVector(this.theLegBottomFrontRight));
        map.put("bLegBBLPos", this.getPosVector(this.theLegBottomBackLeft));
        map.put("bLegBBRPos", this.getPosVector(this.theLegBottomBackRight));
        map.put("bTail", this.getRotationVector(this.theTail));
        map.put("tail0", this.getRotationVector(this.tailBase));
        map.put("tail1", this.getRotationVector(this.tailMiddle));
        map.put("tail2", this.getRotationVector(this.tailEnd));
        map.put("tailB", this.getRotationVector(this.tailBrush));

        saveHorns(cow, data.hornGrowth, map);
    }

    private void saveHorns(CowPhenotype cow, float hornGrowth, Map<String, Vector3f> map) {
        if (cow.hornType != HornType.POLLED) {
            if (!map.containsKey("hL"+cow.leftHornLength)) {
                for (int l = 10 - (int) ((float) (10 - cow.leftHornLength) * hornGrowth); l < 10; l++) {
                    if (l >= 0) {
                        if (map.containsKey("hL" + l)) {
                            break;
                        } else {
                            map.put("hL" + l, new Vector3f(-cow.hornGeneticsX[l],cow.hornGeneticsY[l], cow.hornGeneticsZ[l]));
                            map.put("hPosL" + (l + 1), new Vector3f(0.0F, l > 4 ? -(2.0F - ((l - 4) * 0.2F)) : -2.0F, 0.0F));
                        }
                    }
                }
            }
            if (!map.containsKey("hR"+cow.rightHornLength)) {
                for (int r = 10 - (int)(((10F - cow.rightHornLength) * hornGrowth)); r < 10; r++) {
                    if (r >= 0) {
                        if (map.containsKey("hR" + r)) {
                            break;
                        } else {
                            map.put("hR" + r, new Vector3f(-cow.hornGeneticsX[r], -cow.hornGeneticsY[r], -cow.hornGeneticsZ[r]));
                            map.put("hPosR" + (r + 1), new Vector3f(0.0F, r > 4 ? -(2.0F - ((r - 4) * 0.2F)) : -2.0F, 0.0F));
                        }
                    }
                }
            }
        }
    }

    private void saveSetMushrooms(CowPhenotype cow, Map<String, Vector3f> map) {
        if (cow.mushrooms != null) {
            //body
            if (cow.mushrooms[0]!=-1) map.put("mushroom0", new Vector3f(cow.mushrooms[0]-6, -10.5F, cow.mushrooms[1]-7));
            if (cow.mushrooms[2]!=-1) map.put("mushroom1", new Vector3f(cow.mushrooms[2]+1, -10.5F, cow.mushrooms[3]-3));
            if (cow.mushrooms[4]!=-1) map.put("mushroom2", new Vector3f(cow.mushrooms[4]-6, -10.5F, cow.mushrooms[5]+1));
            if (cow.mushrooms[6]!=-1) map.put("mushroom3", new Vector3f(cow.mushrooms[6]+1, -10.5F, cow.mushrooms[7]+5));
            if (cow.mushrooms[8]!=-1) map.put("mushroom4", new Vector3f((cow.mushrooms[8]*2.0F)-3.0F, -10.5F, cow.mushrooms[9]+9));

            //neck
            if (cow.mushrooms[10]!=-1) map.put("mushroom5", new Vector3f(cow.mushrooms[10]-3.0F, 0.5F, (cow.mushrooms[11]*2.0F) - 8));
            if (cow.mushrooms[12]!=-1) map.put("mushroom6", new Vector3f(cow.mushrooms[12], 0.5F, ((cow.mushrooms[13]*2.0F)+2.0F) - 8));

            //headnub
            if (cow.mushrooms[14]!=-1) map.put("mushroom7", new Vector3f(cow.mushrooms[14]-2.0F, -0.5F, -2.0F));

            setMushrooms(map);
        }
    }

    private void setMushrooms(Map<String, Vector3f> map) {
            for (int i = 0; i < 8; i++) {
                if (map.containsKey("mushroom"+i)) {
                    this.mushroom[i].setPos(map.get("mushroom"+i));
                }
            }
    }

    /**
     *      Animations
     */
    private void readInitialAnimationValues(CowModelData data, CowPhenotype cow) {
        Map<String, Vector3f> map = data.offsets;
        if (map.isEmpty()) {
            this.theCow.setY(data.sleeping ? 20.5F : (cow.dwarf ? 16.0F : 14.0F));
            this.theHump.setPos(new Vector3f(0.0F, cow.humpPlacement, 3.0F));
            this.theHump.setXRot(-0.2F);
            this.theHead.setXRot(Mth.HALF_PI*0.2F);
            this.theNeck.setXRot(Mth.HALF_PI*0.2F);
            this.jaw.setXRot(-Mth.HALF_PI*0.2F);
            this.theEarLeft.setZRot( cow.averageEars ? 1.1F + (cow.earSize * 0.16F) : cow.earFloppiness);
            this.theEarRight.setZRot(-(cow.averageEars ? 1.1F + (cow.earSize * 0.16F) : cow.earFloppiness));
            this.theLegBottomFrontLeft.setY(cow.dwarf ? 4.0F : 5.0F);
            this.theLegBottomFrontRight.setY(cow.dwarf ? 4.0F : 5.0F);
            this.theLegBottomBackLeft.setY(cow.dwarf ? 4.0F : 5.0F);
            this.theLegBottomBackRight.setY(cow.dwarf ? 4.0F : 5.0F);
            this.tailBase.setXRot(0.4F);
            this.tailMiddle.setXRot(-0.2F);
            this.tailEnd.setXRot(-0.2F);

            Vector3f base = new Vector3f(0.0F, cow.hornType.getPlacement(), 0.01F);
            this.theHornNub.setPos(base);
            map.put("bHornNubPos", base);

            if (cow.hornType != HornType.POLLED) {
                Vector3f vector3f = new Vector3f(0.0F, 0.0F, 0.0F);
                map.put("bHornL", vector3f);
                map.put("bHornR", vector3f);
            }

            if (cow.mushrooms != null) {
                saveSetMushrooms(cow, map);
            }
        } else {
            this.theCow.setPos(map.get("bCowPos"));
            this.theHump.setPosYAndRot(map.get("bHumpPos"), map.get("bHump"));
            this.theHead.setRotation(map.get("bHead"));
            this.theNeck.setRotation(map.get("bNeck"));
            this.jaw.setRotation(map.get("jaw"));
            this.theEarLeft.setRotation(map.get("bEarL"));
            this.theEarRight.setRotation(map.get("bEarR"));
            this.theLegFrontLeft.setPosYAndRot(map.get("bLegFLPos"), map.get("bLegFL"));
            this.theLegFrontRight.setPosYAndRot(map.get("bLegFRPos"), map.get("bLegFR"));
            this.theLegBackLeft.setPosYAndRot(map.get("bLegBLPos"), map.get("bLegBL"));
            this.theLegBackRight.setPosYAndRot(map.get("bLegBRPos"), map.get("bLegBR"));
            this.theLegBottomFrontLeft.setY(map.get("bLegBFLPos").y());
            this.theLegBottomFrontRight.setY(map.get("bLegBFRPos").y());
            this.theLegBottomBackLeft.setY(map.get("bLegBBLPos").y());
            this.theLegBottomBackRight.setY(map.get("bLegBBRPos").y());
            this.theTail.setRotation(map.get("bTail"));
            this.tailBase.setRotation(map.get("tail0"));
            this.tailMiddle.setRotation(map.get("tail1"));
            this.tailEnd.setRotation(map.get("tail2"));
            this.tailBrush.setRotation(map.get("tailB"));

            this.theHornNub.setPos(map.get("bHornNubPos"));
            Vector3f vector3f = new Vector3f(0.0F, 0.0F, 0.0F);
            boolean l = true;
            boolean r = true;
            for (int i = 0; i < 10; i++) {
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

            if (cow.mushrooms != null) {
                setMushrooms(map);
            }
        }
    }


    @Override
    public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.cowModelData = getCreateCowModelData(entityIn);

        if (this.cowModelData!=null) {
            CowPhenotype cow = this.cowModelData.getPhenotype();
            readInitialAnimationValues(this.cowModelData, cow);
            boolean isMoving = entityIn.getDeltaMovement().horizontalDistanceSqr() > 1.0E-7D || entityIn.xOld != entityIn.getX() || entityIn.zOld != entityIn.getZ();

            if (this.cowModelData.earTwitchTimer <= ageInTicks) {
                if (this.theEarLeft.getXRot() != 0F || this.theEarRight.getXRot() != 0F) {
                    this.theEarLeft.setXRot(this.lerpTo(0.1F, this.theEarLeft.getXRot(), 0.0F));
                    this.theEarRight.setXRot(this.lerpTo(0.1F,this.theEarRight.getXRot(), 0.0F));
                    this.theEarLeft.setYRot(this.lerpTo(0.15F, this.theEarLeft.getYRot(), 0.0F));
                    this.theEarRight.setYRot(this.lerpTo(0.15F, this.theEarRight.getYRot(), 0.0F));
                } else {
                    this.cowModelData.earTwitchSide = entityIn.getRandom().nextBoolean();
                    this.cowModelData.earTwitchTimer = (int) ageInTicks + (entityIn.getRandom().nextInt(this.cowModelData.sleeping ? 60 : 30) * 20) + 30;
                }
            } else if (this.cowModelData.earTwitchTimer <= ageInTicks + 30) {
                twitchEarAnimation(this.cowModelData.earTwitchSide, (int)ageInTicks);
            }

            if (this.cowModelData.sleeping && !isMoving) {
                if (this.cowModelData.sleepDelay == -1) {
                    this.cowModelData.sleepDelay = (int) ageInTicks + ((entityIn.getRandom().nextInt(10)) * 20) + 10;
                } else if (this.cowModelData.sleepDelay <= ageInTicks+50) {
                    if (this.cowModelData.sleepDelay <= ageInTicks) {
                        this.cowModelData.sleepDelay = 0;
                        layDownAnimation(true);
                    } else {
                        layDownAnimation(false);
                        headLookingAnimation(netHeadYaw, headPitch);
                    }
                }
            } else {
                if (this.cowModelData.sleepDelay != -1) {
                    this.cowModelData.sleepDelay = -1;
                }
                notSleeping(cow.dwarf);
                boolean flag = true;
                if (this.cowModelData.isEating != 0) {
                    if (this.cowModelData.isEating == -1) {
                        this.cowModelData.isEating = (int)ageInTicks + 90;
                    } else if (this.cowModelData.isEating < ageInTicks) {
                        this.cowModelData.isEating = 0;
                    }
                    flag = grazingAnimation(this.cowModelData.isEating - (int)ageInTicks);
                }

                if (flag) {
                    if (netHeadYaw == 0 && headPitch == 0) {
                        moveHeadAnimation();
                    } else {
                        headLookingAnimation(netHeadYaw, headPitch);
                    }
                }

                flag = true;
                if (this.cowModelData.tailSwishTimer <= ageInTicks) {
                    this.cowModelData.tailSwishSide = entityIn.getRandom().nextBoolean();
                    this.cowModelData.tailSwishTimer = (int) ageInTicks + (entityIn.getRandom().nextInt(30) * 20) + 40;
                } else if (this.cowModelData.tailSwishTimer <= ageInTicks + 40) {
                    flag = swishTailAnimation(this.cowModelData.tailSwishSide, (int)ageInTicks);
                }

                if (isMoving) {
                    movingAnimation(ageInTicks, limbSwing, limbSwingAmount);
                    if (flag) {
                        walkingTailAnimation(limbSwing, limbSwingAmount);
                    }
                } else {
                    standingAnimation();
                    if (flag) {
                        stillTailAnimation();
                    }
                }
            }

            if (this.cowModelData.saddle != SaddleType.NONE) {
                orientateSaddle(this.cowModelData.saddle);
            }

            articulateLegs(isMoving);

            saveAnimationValues(this.cowModelData, cow);
        }
    }

    private void layDownAnimation(boolean asleep) {
        this.theCow.setY(Mth.lerp(0.05F, this.theCow.getY(), 20.5F));
        if (this.theNeck.getZRot() == 0.0F) {
            this.theNeck.setZRot(ThreadLocalRandom.current().nextBoolean()? 0.0001F : -0.0001F);
        } else if (this.theNeck.getZRot() > 0.0F) {
            this.theNeck.setXRot(this.lerpTo(this.theNeck.getXRot(), 0.6F));
            this.theNeck.setYRot(this.lerpTo(this.theNeck.getYRot(), 0.75F));
            this.theNeck.setZRot(this.lerpTo(this.theNeck.getZRot(), 0.1F));

            this.theHead.setXRot(this.lerpTo(this.theHead.getXRot(), 0.3F));
            this.theHead.setYRot(this.lerpTo(this.theHead.getYRot(), 0.5F));
            this.theHead.setZRot(this.lerpTo(this.theHead.getZRot(), 0.3F));
        } else {
            this.theNeck.setXRot(this.lerpTo(this.theNeck.getXRot(), 0.6F));
            this.theNeck.setYRot(this.lerpTo(this.theNeck.getYRot(), -0.75F));
            this.theNeck.setZRot(this.lerpTo(this.theNeck.getZRot(), -0.1F));

            this.theHead.setXRot(this.lerpTo(this.theHead.getXRot(), 0.3F));
            this.theHead.setYRot(this.lerpTo(this.theHead.getYRot(), -0.5F));
            this.theHead.setZRot(this.lerpTo(this.theHead.getZRot(), -0.3F));
        }

        this.theLegFrontLeft.setXRot(this.lerpTo(this.theLegFrontLeft.getXRot(), Mth.HALF_PI));
        this.theLegFrontRight.setXRot(this.lerpTo(this.theLegFrontRight.getXRot(), Mth.HALF_PI));

        this.theLegBackLeft.setXRot(this.lerpTo(this.theLegBackLeft.getXRot(), -Mth.HALF_PI));
        this.theLegBackRight.setXRot(this.lerpTo(this.theLegBackRight.getXRot(), -Mth.HALF_PI));

        stillTailAnimation();
    }

    private void notSleeping(boolean dwarf) {
        this.theCow.setY(Mth.lerp(0.05F, this.theCow.getY(), dwarf ? 16.0F : 14.0F));

        this.theNeck.setYRot(this.lerpTo(this.theNeck.getYRot(), 0.0F));
        this.theNeck.setZRot(this.lerpTo(this.theNeck.getZRot(), 0.0F));

        this.theHead.setYRot(this.lerpTo(this.theHead.getYRot(), 0.0F));
        this.theHead.setZRot(this.lerpTo(this.theHead.getZRot(), 0.0F));
    }

    private void standingAnimation() {
        standingLegsAnimation();
    }

    private void movingAnimation(float age, float limbSwing, float limbSwingAmount) {
        walkingLegsAnimation(limbSwing, limbSwingAmount);
    }

    private void standingLegsAnimation() {
        this.theLegFrontLeft.setXRot(this.lerpTo(this.theLegFrontLeft.getXRot(), 0.0F));
        this.theLegFrontRight.setXRot(this.lerpTo(this.theLegFrontRight.getXRot(), 0.0F));
        this.theLegBackLeft.setXRot(this.lerpTo(this.theLegBackLeft.getXRot(), 0.0F));
        this.theLegBackRight.setXRot(this.lerpTo(this.theLegBackRight.getXRot(), 0.0F));
    }

    private void walkingLegsAnimation(float limbSwing, float limbSwingAmount) {
        float f = (Mth.cos(limbSwing * 0.6662F)) * 1.4F * limbSwingAmount;
        float f1 = (Mth.cos(limbSwing * 0.6662F + Mth.PI) * 1.4F * limbSwingAmount);

        this.theLegFrontLeft.setXRot(f);
        this.theLegFrontRight.setXRot(f1);
        this.theLegBackLeft.setXRot(f);
        this.theLegBackRight.setXRot(f1);
    }

    private void moveHeadAnimation() {
        this.theNeck.setXRot(this.lerpTo(this.theNeck.getXRot(), 0.2F));
        this.theNeck.setYRot(this.lerpTo(this.theNeck.getYRot(), 0.0F));
        this.theHead.setXRot(this.lerpTo(this.theHead.getXRot(), Mth.HALF_PI*0.2F));
        this.theHead.setYRot(this.lerpTo(this.theHead.getYRot(), 0.0F));
        this.jaw.setXRot(this.lerpTo(this.jaw.getXRot(), Mth.HALF_PI*-0.2F));
        this.theHump.setYRot(this.lerpTo(this.theHump.getYRot(), 0.0F));
    }

    private void headLookingAnimation(float netHeadYaw, float headPitch) {
            netHeadYaw = (netHeadYaw * 0.017453292F);
            headPitch = ((headPitch * 0.017453292F));
            float lookRotX = Math.min((headPitch * 0.65F)-0.2F, 0.0F);
            float lookRotY = netHeadYaw * 0.65F;

            this.theNeck.setXRot(this.lerpTo(this.theNeck.getXRot(), lookRotX));
            this.theNeck.setYRot(this.lerpTo(this.theNeck.getYRot(), lookRotY));
            this.theHead.setXRot(this.lerpTo(this.theHead.getXRot(), (headPitch - lookRotX) + Mth.HALF_PI*0.2F));
            this.theHead.setYRot(this.lerpTo(this.theHead.getYRot(), limit(netHeadYaw - lookRotY, Mth.HALF_PI * 0.75F)));
            this.jaw.setXRot(this.lerpTo(this.jaw.getXRot(), Mth.HALF_PI*-0.2F));
            this.theHump.setYRot((this.theHead.getYRot()+this.theNeck.getYRot())*0.5F);
    }

    private boolean grazingAnimation(float ticks) {
        if (ticks < 50) {
            float neckRot = this.theNeck.getXRot();
            if (neckRot < Mth.HALF_PI*0.4F) {
                this.theNeck.setXRot(this.lerpTo(this.theNeck.getXRot(), Mth.HALF_PI*0.55F));
            } else if (neckRot < Mth.HALF_PI*0.5F){
                this.theNeck.setXRot(this.lerpTo(this.theNeck.getXRot(), Mth.HALF_PI*0.55F));
                this.theHead.setXRot(this.lerpTo(this.theHead.getXRot(), Mth.HALF_PI*0.1F));
                this.jaw.setXRot(this.lerpTo(this.jaw.getXRot(), Mth.HALF_PI*-0.1F));
            } else {
                float loop = (float) Math.cos(ticks*0.5F);
                if (loop > 0) {
                    this.theNeck.setXRot(this.lerpTo(this.theNeck.getXRot(), Mth.HALF_PI*0.55F));
                    this.theHead.setXRot(this.lerpTo(this.theHead.getXRot(), Mth.HALF_PI*0.2F));
                    this.jaw.setXRot(this.lerpTo(this.jaw.getXRot(), Mth.HALF_PI*-0.2F));
                } else {
                    this.theNeck.setXRot(this.lerpTo(this.theNeck.getXRot(), Mth.HALF_PI*0.5F));
                    this.theHead.setXRot(this.lerpTo(this.theHead.getXRot(), Mth.HALF_PI*0.5F));
                    this.jaw.setXRot(this.lerpTo(this.jaw.getXRot(), Mth.HALF_PI*-0.05F));
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
        this.tailBase.setZRot(this.lerpTo(0.1F, this.theTail.getZRot(), 0.0F));
        this.tailMiddle.setZRot(this.lerpTo(0.1F, this.tailMiddle.getZRot(), 0.0F));
        this.tailEnd.setZRot(this.lerpTo(0.1F, this.tailEnd.getZRot(), 0.0F));
        this.tailBrush.setZRot(this.lerpTo(0.1F, this.tailBrush.getZRot(), 0.0F));
    }

    private void walkingTailAnimation(float limbSwing, float limbSwingAmount) {
        float f = (Mth.cos(limbSwing * 0.6662F)) * limbSwingAmount;

        this.tailBase.setZRot(Mth.HALF_PI * 0.3F * f);
        this.tailMiddle.setZRot(Mth.HALF_PI * 0.2F * f);
        this.tailEnd.setZRot(Mth.HALF_PI * 0.1F * f);
        this.tailBrush.setZRot(Mth.HALF_PI * 0.2F * f);
    }

    private boolean swishTailAnimation(boolean side, float ticks) {
        float loop = (float) Math.cos(ticks*0.05F) + 1.0F;
        float setSide = side?Mth.HALF_PI:-Mth.HALF_PI;

        if (loop > 1.5F) {
            return true;
        } else if (loop > 1.0F) {
            float stage = (loop-1.0F)/0.5F;
            float inverse = 1.0F-stage;
            this.tailBase.setZRot(((-0.8726F*inverse) + (0.349F*stage)) * setSide);
            this.tailMiddle.setZRot(((-0.349F*inverse) + (0.0436F*stage)) * setSide);
            this.tailEnd.setZRot(((-0.4363F*inverse) + (0.2618F*stage)) * setSide);
            this.tailBrush.setZRot(((-0.3927F*inverse) + (0.3054F*stage)) * setSide);
        } else if (loop > 0.5417F) {
            float stage = (loop-0.5417F)/0.4583F;
            float inverse = 1.0F-stage;
            this.tailBase.setZRot(((0.5236F*inverse) + (-0.8726F*stage)) * setSide);
            this.tailMiddle.setZRot(((0.7854F*inverse) + (-0.349F*stage)) * setSide);
            this.tailEnd.setZRot(((0.7854F*inverse) + (-0.4363F*stage)) * setSide);
            this.tailBrush.setZRot(((0.5672F*inverse) + (-0.3927F*stage)) * setSide);
        } else if (loop > 0.25) {
            float stage = (loop-0.25F)/0.2917F;
            this.tailBase.setZRot(0.5236F*stage * setSide);
            this.tailMiddle.setZRot(0.7854F*stage * setSide);
            this.tailEnd.setZRot(0.7854F*stage * setSide);
            this.tailBrush.setZRot(0.5672F*stage * setSide);
        } else {
            return true;
        }

        return false;
    }

    private void orientateSaddle(SaddleType saddleType) {
        switch (saddleType) {
            case VANILLA -> {
                this.saddleVanilla.setY(-0.1F);
                this.stirrupNarrowLeft.setPos(7.5F, 0.0F, 0.0F);
                this.stirrupNarrowRight.setPos(-7.5F, 0.0F, 0.0F);
            }
            case ENGLISH -> {
                this.saddleEnglish.setY(-0.1F);
                this.saddleSideLeft.setPos(3.25F, -0.5F, -4.0F);
                this.saddleSideRight.setPos(-3.25F, -0.5F, -4.0F);
                this.saddleHorn.setPos(0.0F, -1.0F, -1.0F);
                this.saddleHorn.setXRot(Mth.PI / 4.5F);
                this.stirrupNarrowLeft.setPos(7.25F, -0.25F, -1.5F);
                this.stirrupNarrowRight.setPos(-7.25F, -0.25F, -1.5F);
            }
            case WESTERN -> {
                this.saddleWestern.setY(-0.1F);
                this.saddleSideLeft.setPos(4.75F, -1.0F, -5.25F);
                this.saddleSideRight.setPos(-4.75F, -1.0F, -5.25F);
                this.saddleHorn.setPos(0.0F, -2.0F, -2.0F);
                this.saddleHorn.setXRot(Mth.PI * 0.125F);
                this.stirrupWideLeft.setX(7.5F);
                this.stirrupWideRight.setX(-7.5F);
            }
        }
    }

    private void articulateLegs(boolean isMoving) {
        float f = isMoving ? -1.0F : this.theLegFrontLeft.getXRot();
        if (f < 0.0F) {
            this.theLegFrontLeft.setY(0.0F);
            this.theLegFrontRight.setY(0.0F);
        } else if (f > 0.0F){
            f *= HALF_PI_FRACTION;
            this.theLegFrontLeft.setY(f * 3.0F);
            this.theLegFrontRight.setY(f * 3.0F);
        }
        f = isMoving ? 1.0F : theLegBackLeft.getXRot();
        if (f > 0.0F) {
            this.theLegBackLeft.setZ(9.0F);
            this.theLegBackRight.setZ(9.0F);
        } else if (f < 0.0F){
            f *= HALF_PI_FRACTION;
            this.theLegBackLeft.setZ(9.0F - (f * 3.0F));
            this.theLegBackRight.setZ(9.0F - (f * 3.0F));
        }
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
    protected void additionalModelDataInfo(AnimalModelData animalModelData, T enhancedAnimal) {
        ((CowModelData) animalModelData).bagSize = (enhancedAnimal.getEntityStatus().equals(EntityState.MOTHER.toString()) || enhancedAnimal.getEntityStatus().equals(EntityState.PREGNANT.toString())) ? enhancedAnimal.getBagSize() : -1.0F;
    }

    @Override
    protected void additionalUpdateModelDataInfo(AnimalModelData animalModelData, T enhancedAnimal) {
        ((CowModelData)animalModelData).hornGrowth = enhancedAnimal.hornGrowthAmount();
        ((CowModelData) animalModelData).bagSize = (enhancedAnimal.getEntityStatus().equals(EntityState.MOTHER.toString()) || enhancedAnimal.getEntityStatus().equals(EntityState.PREGNANT.toString())) ? enhancedAnimal.getBagSize() : -1.0F;
        animalModelData.saddle = getSaddle(enhancedAnimal.getEnhancedInventory());
        animalModelData.bridle = enhancedAnimal.hasBridle();
        animalModelData.blanket = enhancedAnimal.hasBlanket();
        animalModelData.chests = enhancedAnimal.hasChest();
    }

    @Override
    protected CowPhenotype createPhenotype(T enhancedAnimal) {
        char[] uuid = (enhancedAnimal.getMooshroomUUID().isEmpty() || enhancedAnimal.getMooshroomUUID().equals("0")) ? enhancedAnimal.getStringUUID().toCharArray() : enhancedAnimal.getMooshroomUUID().toCharArray();
        return new CowPhenotype(enhancedAnimal.getSharedGenes().getAutosomalGenes(), uuid, enhancedAnimal.getOrSetIsFemale(), enhancedAnimal instanceof EnhancedMooshroom || enhancedAnimal instanceof EnhancedMoobloom);
    }
}

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
//            cowModelData.collar = collarSlot!=0;
//            cowModelData.saddle = collarSlot!=1 ? enhancedCow.getEnhancedInventory().getItem(1) : ItemStack.EMPTY;
//            cowModelData.bridle = !enhancedCow.getEnhancedInventory().getItem(3).isEmpty() && collarSlot!=3;
//            cowModelData.harness = !enhancedCow.getEnhancedInventory().getItem(5).isEmpty() && collarSlot!=5;
//            cowModelData.hasChest = !enhancedCow.getEnhancedInventory().getItem(0).isEmpty();

