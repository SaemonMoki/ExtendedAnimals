package mokiyoki.enhancedanimals.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import mokiyoki.enhancedanimals.entity.EnhancedChicken;
import mokiyoki.enhancedanimals.model.modeldata.ChickenModelData;
import mokiyoki.enhancedanimals.model.modeldata.ChickenPhenotype;
import mokiyoki.enhancedanimals.model.modeldata.ChickenPhenotypeEnums.*;
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

/**
 * Created by saemon on 8/09/2018.
 */
@OnlyIn(Dist.CLIENT)
public class ModelEnhancedChicken<T extends EnhancedChicken> extends EnhancedAnimalModel<T> {
    private WrappedModelPart theChicken;

    private WrappedModelPart theHead;
    private WrappedModelPart theComb;
    private WrappedModelPart theWaddles;
    private WrappedModelPart theCrest;
    private WrappedModelPart theBeard;
    private WrappedModelPart theNeck;
    private WrappedModelPart theBody;
    private WrappedModelPart theWingLeft;
    private WrappedModelPart theWingRight;
    private WrappedModelPart theLegLeft;
    private WrappedModelPart theLegRight;
    private WrappedModelPart theFootLeft;
    private WrappedModelPart theFootRight;
    private WrappedModelPart theSaddle;
    private WrappedModelPart theTailCoverts;
    private WrappedModelPart theTail;

    private WrappedModelPart head;
    private WrappedModelPart headFeathers;

    private WrappedModelPart beak;
    private WrappedModelPart ears;

    private WrappedModelPart waddlesSmall;
    private WrappedModelPart waddlesMedium;
    private WrappedModelPart waddlesLarge;
    private WrappedModelPart waddlesPea;
    private WrappedModelPart waddlesBearded;

    private WrappedModelPart combDuplex;
    private WrappedModelPart combSingleXs;
    private WrappedModelPart combSingleS;
    private WrappedModelPart combSingleM;
    private WrappedModelPart combSingleL;
    private WrappedModelPart combSingleXl;
    private WrappedModelPart combRoseTallS;
    private WrappedModelPart combRoseTallM;
    private WrappedModelPart combRoseTallL;
    private WrappedModelPart combRoseFlatS;
    private WrappedModelPart combRoseFlatM;
    private WrappedModelPart combRoseFlatL;
    private WrappedModelPart combPeaS;
    private WrappedModelPart combPeaM;
    private WrappedModelPart combPeaL;
    private WrappedModelPart combWalnutS;
    private WrappedModelPart combWalnutM;
    private WrappedModelPart combWalnutL;
    private WrappedModelPart combV;

    private WrappedModelPart crestSmallF;
    private WrappedModelPart crestMediumF;
    private WrappedModelPart crestLargeF;
//    private WrappedModelPart crestSmallM;
//    private WrappedModelPart crestMediumM;
//    private WrappedModelPart crestLargeM;

    private WrappedModelPart beardNakedNeck;
    private WrappedModelPart beardLarge;

    private WrappedModelPart earTuftLeft;
    private WrappedModelPart earTuftRight;

    private WrappedModelPart neck;
    private WrappedModelPart hackle;

    private WrappedModelPart bodyNaked;
    private WrappedModelPart bodySmall;
    private WrappedModelPart bodyMedium;
    private WrappedModelPart bodyLarge;

    private WrappedModelPart wingLeftNaked;
    private WrappedModelPart wingRightNaked;
    private WrappedModelPart wingLeftSmall;
    private WrappedModelPart wingRightSmall;
    private WrappedModelPart wingLeftMedium;
    private WrappedModelPart wingRightMedium;

    private WrappedModelPart thighLeft;
    private WrappedModelPart thighRight;
    private WrappedModelPart bloomersLeft;
    private WrappedModelPart bloomersRight;

    private WrappedModelPart legLeftShort;
    private WrappedModelPart legLeftMedium;
    private WrappedModelPart legLeftLong;
    private WrappedModelPart legRightShort;
    private WrappedModelPart legRightMedium;
    private WrappedModelPart legRightLong;

    private WrappedModelPart footLeft;
    private WrappedModelPart footRight;

    private WrappedModelPart pantsLeft;
    private WrappedModelPart pantsLeftLong;
    private WrappedModelPart pantsRight;
    private WrappedModelPart pantsRightLong;
    private WrappedModelPart bootsLeft;
    private WrappedModelPart bootsRight;
    private WrappedModelPart toeFeathersLeft;
    private WrappedModelPart toeFeathersRight;
    private WrappedModelPart vultureHockLeft;
    private WrappedModelPart vultureHockRight;

    private WrappedModelPart tailFeathers;

    private WrappedModelPart cushion;
    private WrappedModelPart saddle;
    private WrappedModelPart tailCoverShort;
    private WrappedModelPart tailCoverMedium;
    private WrappedModelPart tailCoverLong;
    private WrappedModelPart tailShort;

    private ChickenModelData chickenModelData;

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition base = meshdefinition.getRoot().addOrReplaceChild("base", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition bChicken = base.addOrReplaceChild("bChicken", CubeListBuilder.create(), PartPose.offset(0.0F, 17.5F, 0.0F));
        PartDefinition bBody = bChicken.addOrReplaceChild("bBody", CubeListBuilder.create(), PartPose.offset(0.0F, 2.0F, 0.0F));
        PartDefinition bNeck = bBody.addOrReplaceChild("bNeck", CubeListBuilder.create(), PartPose.offset(0.0F, -2.5F, -3.5F));
        PartDefinition bHead = bNeck.addOrReplaceChild("bHead", CubeListBuilder.create(), PartPose.offset(0.0F, -4.75F, 0.3F));
        PartDefinition bComb = bHead.addOrReplaceChild("bComb", CubeListBuilder.create(), PartPose.offset(0.0F, -1.0F, 0.0F));
        PartDefinition bWaddles = bHead.addOrReplaceChild("bWaddles", CubeListBuilder.create(), PartPose.offset(0.0F, -0.5F, -1.0F));
        PartDefinition bCrest = bHead.addOrReplaceChild("bCrest", CubeListBuilder.create(), PartPose.offset(0.0F, -2.0F,-1.0F));
        PartDefinition bBeard = bHead.addOrReplaceChild("bBeard", CubeListBuilder.create(), PartPose.offset(0.0F, 2.5F, 0.0F));
        PartDefinition bLeftWing = bBody.addOrReplaceChild("bWingL", CubeListBuilder.create(), PartPose.offset(2.5F, -5.0F, -3.0F));
        PartDefinition bRightWing = bBody.addOrReplaceChild("bWingR", CubeListBuilder.create(), PartPose.offset(-2.5F, -5.0F, -3.0F));
        PartDefinition bLeftLeg = bChicken.addOrReplaceChild("bLegL", CubeListBuilder.create(), PartPose.offset(0.0F, -2.5F, -0.5F));
        PartDefinition bRightLeg = bChicken.addOrReplaceChild("bLegR", CubeListBuilder.create(), PartPose.offset(0.0F, -2.5F, -0.5F));
            bChicken.addOrReplaceChild("bFootL", CubeListBuilder.create(), PartPose.ZERO);
            bChicken.addOrReplaceChild("bFootR", CubeListBuilder.create(), PartPose.ZERO);
        PartDefinition bSaddle = bBody.addOrReplaceChild("bSaddle", CubeListBuilder.create(), PartPose.offset(0.0F, -6.0F, 2.5F));
        PartDefinition bCoverts = bSaddle.addOrReplaceChild("bCoverts", CubeListBuilder.create(), PartPose.offset(0.0F,2.0F, 2.0F));
        PartDefinition bTail = bCoverts.addOrReplaceChild("bTail", CubeListBuilder.create(), PartPose.offset(0.0F, -4.0F, 0.0F));

        /**
         *      - Heads and HeadParts
         */
        bHead.addOrReplaceChild("headF",
                CubeListBuilder.create()
                        .texOffs(12, 0)
                        .addBox(-2.0F, -2.5F, -3.0F, 4, 4, 4, new CubeDeformation(-0.5F)),
                PartPose.ZERO
        );
        bHead.addOrReplaceChild("head",
                CubeListBuilder.create()
                        .texOffs(45, 57)
                        .addBox(-2.0F, -2.49F, -2.995F, 4, 3, 4, new CubeDeformation(-0.51F)),
                PartPose.ZERO
        );
        bHead.addOrReplaceChild("beak",
                CubeListBuilder.create()
                        .texOffs(0, 21)
                        .addBox(-0.5F, 0.0F, -1.76F, 1, 1, 2),
                PartPose.offset(0.0F,  -1.25F, -2.0F)
        );
        bHead.addOrReplaceChild("jaw",
                CubeListBuilder.create()
                        .texOffs(0, 22)
                        .addBox(-0.5F, 0.25F, -1.51F, 1, 1, 2, new CubeDeformation(-0.005F)),
                PartPose.offset(0.0F,  -4.5F, -2.0F)
        );
        bHead.addOrReplaceChild("ears",
                CubeListBuilder.create()
                        .texOffs(1, 52)
                        .addBox(-2.2F, -6.0F, -2.2F, 1, 6, 3, false)
                        .addBox(1.2F, -6.0F, -2.4F, 1, 6, 3, true),
                PartPose.ZERO
        );
        bHead.addOrReplaceChild("eyes",
                CubeListBuilder.create()
                        .texOffs(23, 0)
                        .addBox(0.61F, 0.05F, -0.201F, 1, 1, 1, new CubeDeformation(-0.1F))
                        .texOffs(11, 0)
                        .addBox(-1.61F, 0.05F, -0.201F, 1, 1, 1, new CubeDeformation(-0.1F)),
                PartPose.offset(0.0F, -1.6F, -2.41F)
        );

        bWaddles.addOrReplaceChild("waddlesS",
                CubeListBuilder.create()
                        .texOffs(0, 1)
                        .addBox(-0.5F, 0.0F, -2.0F, 0, 1, 2)
                        .addBox(0.5F, 0.0F, -2.0F, 0, 1, 2),
                PartPose.ZERO
        );
        bWaddles.addOrReplaceChild("waddlesM",
                CubeListBuilder.create()
                        .texOffs(0, 1)
                        .addBox(-0.5F, 0.0F, -2.0F, 0, 2, 2)
                        .addBox(0.5F, 0.0F, -2.0F, 0, 2, 2),
                PartPose.ZERO
        );
        bWaddles.addOrReplaceChild("waddlesL",
                    CubeListBuilder.create()
                            .texOffs(0, 1)
                            .addBox(-0.5F, 0.0F, -2.0F, 0, 3, 2)
                            .addBox(0.5F, 0.0F, -2.0F, 0, 3, 2),
                    PartPose.ZERO
        );
        bWaddles.addOrReplaceChild("waddlesP",
                    CubeListBuilder.create()
                            .texOffs(0, 1)
                            .addBox(0.0F, 0.0F, -2.0F, 0, 2, 2),
                    PartPose.ZERO
        );
        bWaddles.addOrReplaceChild("waddlesB",
                    CubeListBuilder.create()
                            .texOffs(0, 1)
                            .addBox(-1.5F, 0.0F, -1.0F, 3, 1, 1),
                    PartPose.ZERO
        );

        bBeard.addOrReplaceChild("beardL",
            CubeListBuilder.create()
                .texOffs(2, 29)
                .addBox(-3.0F, -4.0F, -2.0F, 2, 2, 2)
                .addBox(-2.5F, -3.5F, -2.75F, 2, 2, 2)
                .texOffs(3, 30)
                .addBox(-1.0F, -2.25F, -3.25F, 2, 2, 2)
//                .addBox(-0.5F, -2.25F, -3.75F, 1, 1, 1)
                .texOffs(2, 29)
                .addBox(1F, -4.0F, -2F, 2, 2, 2)
                .addBox(0.5F, -3.5F, -2.75F, 2, 2, 2),
            PartPose.ZERO
        );
        bBeard.addOrReplaceChild("beardNN", CubeListBuilder.create()
                    .texOffs(2, 29)
                    .addBox(-3F, -4F, -2F, 1, 2, 2)
                    .addBox(-2F, -3.0F, -2.75F, 2, 2, 2)
                    .texOffs(3, 30)
                    .addBox(-0.5F, -2.0F, -3.5F, 1, 1, 1)
                    .texOffs(2, 29)
                    .addBox(2F, -4F, -2F, 1, 2, 2)
                    .addBox(0F, -3F, -2.75F, 2, 2, 2),
            PartPose.ZERO
        );

        /**
         *      EarTufts
         */
        bHead.addOrReplaceChild("earTuftL", CubeListBuilder.create()
                .texOffs(54, 26)
                .addBox(-2.0F, 0.0F, 0.0F, 2, 0, 3),
            PartPose.offsetAndRotation(-1.5F, -4.5F, -1.0F, 1.4F, 0.0F, -1.4F)
        );
        bHead.addOrReplaceChild("earTuftR", CubeListBuilder.create()
                .mirror(true)
                .texOffs(54, 26)
                .addBox(0.0F, 0.0F, 0.0F, 2, 0, 3),
            PartPose.offsetAndRotation(1.5F, -4.5F, -1.0F, 1.4F, 0.0F, 1.4F)
        );

        /**
         *      Crests
         */
        bCrest.addOrReplaceChild("crestSF", CubeListBuilder.create()
                        .texOffs(2, 43)
                        .addBox(-1.5F, -3.0F, -1.5F, 3, 3, 3, new CubeDeformation(0.1F)),
                PartPose.ZERO
        );
        bCrest.addOrReplaceChild("crestMF", CubeListBuilder.create()
                        .texOffs(2, 43)
                        .addBox(-1.5F, -3.0F, -1.5F, 3, 3, 3, new CubeDeformation(0.6F)),
                PartPose.ZERO
        );
        bCrest.addOrReplaceChild("crestLF", CubeListBuilder.create()
                        .texOffs(1, 42)
                        .addBox(-2.0F, -4.0F, -2.0F, 4, 4, 4, new CubeDeformation(0.5F)),
                PartPose.ZERO
        );

        /**
         *      Combs
         */
        bComb.addOrReplaceChild("combSingleXs",
                CubeListBuilder.create()
                    .texOffs(0, 0)
                    .addBox(-0.5F, -1.5F, -2.5F, 1, 2, 1, new CubeDeformation(-0.25F))
                    .addBox(-0.5F, -2.0F, -2.0F, 1, 2, 1, new CubeDeformation(-0.25F))
                    .addBox(-0.5F, -1.75F, -1.5F, 1, 1, 1, new CubeDeformation(-0.25F))
                    .addBox(-0.5F, -2.25F, -1.0F, 1, 2, 1, new CubeDeformation(-0.25F))
                    .addBox(-0.5F, -1.75F, -0.5F, 1, 1, 1, new CubeDeformation(-0.25F))
                    .addBox(-0.5F, -2.0F, 0.0F, 1, 1, 1, new CubeDeformation(-0.25F)),
                PartPose.ZERO
            );
        bComb.addOrReplaceChild("combSingleS",
                    CubeListBuilder.create()
                        .texOffs(0, 0)
                        .addBox(-0.5F, -1.75F, -2.25F, 1, 2, 1, new CubeDeformation(-0.125F))
                        .addBox(-0.5F, -2.5F, -1.75F, 1, 1, 1, new CubeDeformation(-0.125F))
                        .addBox(-0.5F, -1.75F, -1.5F, 1, 1, 2, new CubeDeformation(-0.125F))
                        .addBox(-0.5F, -2.5F, -0.5F, 1, 1, 1, new CubeDeformation(-0.125F))
                        .addBox(-0.5F, -1.75F, 0F, 1, 1, 1, new CubeDeformation(-0.125F))
                        .addBox(-0.5F, -2.0F, 0.75F, 1, 1, 1, new CubeDeformation(-0.125F)),
                    PartPose.ZERO
            );
        bComb.addOrReplaceChild("combSingleM",
                CubeListBuilder.create()
                        .texOffs(0, 0)
                        .addBox(-0.5F, -1.0F, -3.0F, 1, 1, 1)
                        .addBox(-0.5F, -1.5F, -3.0F, 1, 1, 1)
                        .addBox(-0.5F, -2.5F, -2.0F, 1, 2, 1)
                        .addBox(-0.5F, -2.0F, -1.0F, 1, 1, 1)
                        .addBox(-0.5F, -2.5F, 0.0F, 1, 2, 1)
                        .addBox(-0.5F, -1.5F, 1.0F, 1, 1, 1),
                PartPose.ZERO
            );
        bComb.addOrReplaceChild("combSingleL",
                CubeListBuilder.create()
                    .texOffs(0, 0)
                    .addBox(-0.5F, -2.5F, -3.5F, 1, 1, 1)
                    .addBox(-0.5F, -1.5F, -3.0F, 1, 2, 1)
                    .addBox(-0.5F, -3.5F, -2.0F, 1, 3, 1)
                    .addBox(-0.5F, -2.5F, -1.0F, 1, 2, 1)
                    .addBox(-0.5F, -4.0F, 0.0F, 1, 3, 1)
                    .addBox(-0.5F, -2.5F, 1.0F, 1, 2, 1)
                    .addBox(-0.5F, -3.5F, 2.0F, 1, 3, 1)
                    .addBox(-0.5F, -2.0F, 3.0F, 1, 1, 1),
                PartPose.ZERO
            );
        bComb.addOrReplaceChild("combSingleXl",
                CubeListBuilder.create()
                    .texOffs(0, 0)
                    .addBox(-0.5F, -3.5F, -3.5F, 1, 2, 1)
                    .addBox(-0.5F, -2.5F, -3.0F, 1, 3, 1)
                    .addBox(-0.5F, -5.0F, -2.0F, 1, 4, 1)
                    .addBox(-0.5F, -3.5F, -1.0F, 1, 3, 1)
                    .addBox(-0.5F, -4.5F, 0.0F, 1, 4, 1)
                    .addBox(-0.5F, -5.5F, 0.5F, 1, 1, 1)
                    .addBox(-0.5F, -3.5F, 1.0F, 1, 3, 1)
                    .addBox(-0.5F, -4.5F, 2.0F, 1, 4, 1)
                    .addBox(-0.5F, -5.0F, 2.5F, 1, 1, 1)
                    .addBox(-0.5F, -3.0F, 3.0F, 1, 2, 1)
                    .addBox(-0.5F, -3.5F, 4.0F, 1, 2, 1),
                PartPose.ZERO
            );
        bComb.addOrReplaceChild("combRoseTallS",
                CubeListBuilder.create()
                    .texOffs(0, 0)
                    .addBox(-1F, -1.25F, -2.75F, 2, 2, 1, new CubeDeformation(-0.25F))
                    .addBox(-0.5F, -2.0F, -2.25F, 1, 2, 1, new CubeDeformation(-0.1F))
                    .addBox(-0.5F, -2.4F, -1.75F, 1, 1, 1, new CubeDeformation(-0.25F)),
                PartPose.ZERO
            );
        bComb.addOrReplaceChild("combRoseTallM",
                CubeListBuilder.create()
                    .texOffs(0, 0)
                    .addBox(-0.5F, -1.0F, -3.0F, 1, 1, 1, new CubeDeformation(0.5F))
                    .addBox(-0.5F, -2.0F, -2.0F, 1, 1, 1, new CubeDeformation(0.25F))
                    .addBox(-0.5F, -3.0F, -1.0F, 1, 1, 1),
                PartPose.ZERO
            );
        bComb.addOrReplaceChild("combRoseTallL",
                CubeListBuilder.create()
                    .texOffs(0, 0)
                    .addBox(-1.0F, -1.0F, -3.0F, 2, 2, 1)
                    .addBox(-0.5F, -2.0F, -2.5F, 1, 1, 2, new CubeDeformation(0.2F))
                    .addBox(-0.5F, -3.0F, -0.5F, 1, 2, 1)
                    .addBox(-0.5F, -4.0F, -0.0F, 1, 1, 1),
                PartPose.ZERO
            );
        bComb.addOrReplaceChild("combRoseFlatS",
                CubeListBuilder.create()
                        .texOffs(0, 0)
                        .addBox(-0.5F, -1.0F, -2.5F, 1, 1, 1, new CubeDeformation(0.1F))
                        .addBox(-0.5F, -1.5F, -2.0F, 1, 1, 1)
                        .addBox(-0.5F, -1.4F, -1F, 1, 1, 1),
                PartPose.ZERO
            );
        bComb.addOrReplaceChild("combRoseFlatM",
                CubeListBuilder.create()
                        .texOffs(0, 0)
                        .addBox(-0.5F, -1.0F, -3F, 1, 1, 1, new CubeDeformation(0.5F))
                        .addBox(-0.5F, -2.0F, -2F, 1, 1, 1, new CubeDeformation(0.25F))
                        .addBox(-0.5F, -2.0F, -1F, 1, 1, 1),
                PartPose.ZERO
            );
        bComb.addOrReplaceChild("combRoseFlatL",
                CubeListBuilder.create()
                        .texOffs(0, 0)
                        .addBox(-1.0F, -1.0F, -3.0F, 2, 2, 1)
                        .addBox(-1.0F, -1.75F, -2.5F, 2, 1, 2)
                        .addBox(-0.5F, -1.5F, -0.5F, 1, 1, 1, new CubeDeformation(0.2F)),
                PartPose.ZERO
            );
        bComb.addOrReplaceChild("combPeaS",
                CubeListBuilder.create()
                        .texOffs(0, 0)
                        .addBox(-0.5F, -1.0F, -2.5F, 1, 1, 1, new CubeDeformation(-0.25F))
                        .addBox(-0.5F, -1.5F, -2.5F, 1, 1, 1, new CubeDeformation(-0.125F))
                        .addBox(-0.5F, -1.5F, -1.9F, 1, 1, 1, new CubeDeformation(-0.25F)),
                PartPose.ZERO
            );
        bComb.addOrReplaceChild("combPeaM",
                CubeListBuilder.create()
                        .texOffs(0, 0)
                        .addBox(-0.5F, -1.0F, -3.0F, 1, 1, 2, new CubeDeformation(-0.2F))
                        .addBox(-0.5F, -1.5F, -2.5F, 1, 1, 1)
                        .addBox(-0.5F, -2.0F, -2.0F, 1, 2, 1, new CubeDeformation(-0.2F)),
                PartPose.ZERO
            );
        bComb.addOrReplaceChild("combPeaL",
                    CubeListBuilder.create()
                            .texOffs(0, 0)
                            .addBox(-1.0F, -0.75F, -2.25F, 2, 1, 1)
                            .addBox(-0.5F, -1.75F, -2.4F, 1, 2, 2)
                            .texOffs(0, 14)
                            .addBox(0.5F, -1.75F, -2.4F, 1, 2, 2)
                            .addBox(-0.5F, -2.0F, -1.1F, 1, 1, 1),
                    PartPose.ZERO
            );
        bComb.addOrReplaceChild("combWalnutS",
                    CubeListBuilder.create()
                            .texOffs(0, 0)
                            .addBox(-0.5F, -1.5F, -2.5F, 1, 1, 1, new CubeDeformation(-0.25F)),
                    PartPose.ZERO
            );
        bComb.addOrReplaceChild("combWalnutM",
                    CubeListBuilder.create()
                            .texOffs(0, 0)
                            .addBox(-0.5F, -1.5F, -2.5F, 1, 1, 1),
                    PartPose.ZERO
            );
        bComb.addOrReplaceChild("combWalnutL",
                    CubeListBuilder.create()
                            .texOffs(0, 0)
                            .addBox(-1.0F, -2.0F, -2.5F, 2, 2, 1, new CubeDeformation(-0.125F))
                            .addBox(-0.5F, -1.5F, -2.0F, 1, 1, 1),
                    PartPose.ZERO
            );
        bComb.addOrReplaceChild("combV",
                    CubeListBuilder.create()
                        .texOffs(0, 0)
                        .addBox(-0.5F, -1.5F, -2.5F, 1, 1, 1)
                        .addBox(0.0F, -2.0F, -2.25F, 1, 1, 1, new CubeDeformation(-0.2F))
                        .addBox(-1.0F, -2.0F, -2.25F, 1, 1, 1, new CubeDeformation(-0.2F))
                        .addBox(0.1F, -2.3F, -2F, 1, 1, 1, new CubeDeformation(-0.3F))
                        .addBox(-1.1F, -2.3F, -2F, 1, 1, 1, new CubeDeformation(-0.3F)),
                    PartPose.ZERO
            );
        bComb.addOrReplaceChild("combDuplex", CubeListBuilder.create(), PartPose.ZERO);

        /**
         *      Necks
         */
        bNeck.addOrReplaceChild("neck", CubeListBuilder.create()
                        .texOffs(0, 15)
                        .addBox(-0.5F, -5.0F, -1.0F, 1, 5, 1),
                PartPose.rotation(Mth.HALF_PI*-0.1F, 0.0F, 0.0F)
        );
        bNeck.addOrReplaceChild("hackle", CubeListBuilder.create()
                        .texOffs(48, 13)
                        .addBox(-2.0F, -5.0F, -3.0F, 4, 6, 4, new CubeDeformation(-0.4F)),
                PartPose.offset(0.0F, 0.0F, 1.0F)
        );

        /**
         *      Bodies
         */
        bBody.addOrReplaceChild("bodyN", CubeListBuilder.create()
                        .texOffs(40, 52)
                        .addBox(-2.5F, -6.0F, -3.5F, 5, 5, 7, new CubeDeformation(-0.51F)),
                PartPose.ZERO
        );
        bBody.addOrReplaceChild("bodyS", CubeListBuilder.create()
                        .texOffs(12, 10)
                        .addBox(-3.0F, -6.0F, -4.0F, 6, 6, 8, new CubeDeformation(-0.5F)),
                PartPose.offset(0.0F, 0.0F, 0.0F)
        );
        bBody.addOrReplaceChild("bodyM", CubeListBuilder.create()
                        .texOffs(12, 10)
                        .addBox(-3.0F, -6.0F, -4.0F, 6, 6, 8),
                PartPose.offset(0.0F, 0.0F, 0.0F)
        );

        bBody.addOrReplaceChild("bodyL", CubeListBuilder.create()
                        .texOffs(12, 10)
                        .addBox(-3.0F, -6.0F, -4.0F, 6, 6, 8, new CubeDeformation(0.5F)),
                PartPose.offset(0.0F, 0.0F, 0.0F)
        );

        /**
         *      Wings
         */
        bLeftWing.addOrReplaceChild("wingLM", CubeListBuilder.create()
                        .texOffs(16, 24)
                        .addBox(0.0F, 0.0F, 0.0F, 1, 4, 6),
                PartPose.ZERO
        );
        bLeftWing.addOrReplaceChild("wingLS", CubeListBuilder.create()
                        .texOffs(17, 25)
                        .addBox(0.0F, 0.0F, 0.0F, 1, 3, 5),
                PartPose.ZERO
        );
        bLeftWing.addOrReplaceChild("wingLN", CubeListBuilder.create()
                        .texOffs(47, 58)
                        .addBox(0.0F, 0.0F, 0.0F, 1, 2, 3),
                PartPose.ZERO
        );

        bRightWing.addOrReplaceChild("wingRM", CubeListBuilder.create()
                        .texOffs(30, 24)
                        .addBox(-1.0F, 0.0F, 0.0F, 1, 4, 6),
                PartPose.ZERO
        );
        bRightWing.addOrReplaceChild("wingRS", CubeListBuilder.create()
                        .texOffs(31, 25)
                        .addBox(-1.0F, 0.0F, 0.0F, 1, 3, 5),
                PartPose.ZERO
        );
        bRightWing.addOrReplaceChild("wingRN", CubeListBuilder.create()
                        .texOffs(47, 58)
                        .addBox(-1.0F, 0.0F, 0.0F, 1, 2, 3),
                PartPose.ZERO
        );

        /**
         *      Legs
         */
        bLeftLeg.addOrReplaceChild("thighL", CubeListBuilder.create()
                        .texOffs(40, 59)
                        .addBox(-2.5F, 3.0F, 0.5F, 2, 2, 3),
                PartPose.ZERO
        );
        bRightLeg.addOrReplaceChild("thighR", CubeListBuilder.create()
                        .texOffs(54, 59)
                        .addBox(0.5F, 3.0F, 0.5F, 2, 2, 3),
                PartPose.ZERO
        );

        bLeftLeg.addOrReplaceChild("legLS", CubeListBuilder.create()
                        .texOffs(8, 21)
                        .addBox(-2.0F, 3.5F, 1.0F, 1, 3, 1),
                PartPose.ZERO
        );
        bLeftLeg.addOrReplaceChild("legLM", CubeListBuilder.create()
                        .texOffs(8, 21)
                        .addBox(-2.0F, 3.5F, 1.0F, 1, 5, 1),
                PartPose.ZERO
        );
        bLeftLeg.addOrReplaceChild("legLL", CubeListBuilder.create()
                        .texOffs(8, 21)
                        .addBox(-2.0F, 3.5F, 1.0F, 1, 2, 1)
                        .addBox(-2.0F, 5.5F, 1.0F, 1, 5, 1),
                PartPose.ZERO
        );
        bRightLeg.addOrReplaceChild("legRS", CubeListBuilder.create()
                        .texOffs(8, 21)
                        .addBox(1.0F, 3.5F, 1.0F, 1, 3, 1),
                PartPose.ZERO
        );
        bRightLeg.addOrReplaceChild("legRM", CubeListBuilder.create()
                        .texOffs(8, 21)
                        .addBox(1.0F, 3.5F, 1.0F, 1, 5, 1),
                PartPose.ZERO
        );
        bRightLeg.addOrReplaceChild("legRL", CubeListBuilder.create()
                        .texOffs(8, 21)
                        .addBox(1.0F, 3.5F, 1.0F, 1, 2, 1)
                        .addBox(1.0F, 5.5F, 1.0F, 1, 5, 1),
                PartPose.ZERO
        );

        bLeftLeg.addOrReplaceChild("footL", CubeListBuilder.create()
                        .texOffs(1, 26)
                        .addBox(-3.0F, 0.0F, 0.0F, 3, 1, 1)
                        .texOffs(3, 26)
                        .addBox(-2.0F, 0.0F, -1.0F, 1, 1, 1),
                PartPose.ZERO
        );
        bRightLeg.addOrReplaceChild("footR", CubeListBuilder.create()
                        .texOffs(1, 26)
                        .addBox(0.0F, 0.0F, 0.0F, 3, 1, 1)
                        .texOffs(3, 26)
                        .addBox(1.0F, 0.0F, -1.0F, 1, 1, 1),
                PartPose.ZERO
        );

        /**
         *      Leg Feathers
         */
        bLeftLeg.addOrReplaceChild("bloomersL", CubeListBuilder.create()
                        .texOffs(50, 23)
                        .addBox(-3.75F, 3.0F, 0.0F, 3, 3, 4, new CubeDeformation(0.5F)),
                PartPose.ZERO
        );
        bRightLeg.addOrReplaceChild("bloomersR", CubeListBuilder.create()
                        .texOffs(38, 20)
                        .addBox(0.75F, 3.0F, 0.0F, 3, 3, 4, new CubeDeformation(0.5F)),
                PartPose.ZERO
        );

        bLeftLeg.addOrReplaceChild("pantsL", CubeListBuilder.create()
                        .texOffs(40, 11)
                        .addBox(-3.5F, 5.901F, 0.5F, 2, 3, 2),
                PartPose.ZERO
        );
        bLeftLeg.addOrReplaceChild("pantsLL", CubeListBuilder.create()
                        .texOffs(40, 11)
                        .addBox(-3.5F, 5.901F, 0.5F, 2, 4, 2),
                PartPose.ZERO
        );
        bRightLeg.addOrReplaceChild("pantsR", CubeListBuilder.create()
                        .texOffs(32, 11)
                        .addBox(1.5F, 5.901F, 0.5F, 2, 3, 2),
                PartPose.ZERO
        );
        bRightLeg.addOrReplaceChild("pantsLR", CubeListBuilder.create()
                        .texOffs(32, 11)
                        .addBox(1.5F, 5.901F, 0.5F, 2, 4, 2),
                PartPose.ZERO
        );

        bLeftLeg.addOrReplaceChild("bootsL", CubeListBuilder.create()
                        .texOffs(38, 0)
                        .addBox(0.0F, 3.9F, 0.0F, 5, 5, 0),
                PartPose.offsetAndRotation(-2.25F, 0.0F, 4.0F, 0.0F, Mth.HALF_PI, 0.0F)
        );
        bRightLeg.addOrReplaceChild("bootsR", CubeListBuilder.create()
                        .texOffs(28, 0)
                        .addBox(0.0F, 3.9F, 0.0F, 5, 5, 0),
                PartPose.offsetAndRotation(2.25F, 0.0F, 4.0F, 0.0F, Mth.HALF_PI, 0.0F)
        );

        bLeftLeg.addOrReplaceChild("toeFeathersL", CubeListBuilder.create()
                        .texOffs(4, 12)
                        .addBox(-6.0F, 8.9F, -2.0F, 5, 0, 6),
                PartPose.ZERO
        );
        bRightLeg.addOrReplaceChild("toeFeathersR", CubeListBuilder.create()
                        .texOffs(6, 24)
                        .addBox(1.0F, 8.9F, -2.0F, 5, 0, 6),
                PartPose.ZERO
        );

        bLeftLeg.addOrReplaceChild("vultureHocksL", CubeListBuilder.create()
                        .texOffs(33, 32)
                        .addBox(-3.5F, 3.0F, 2.5F, 0, 3, 4, new CubeDeformation(-0.2F)),
                PartPose.ZERO
        );
        bRightLeg.addOrReplaceChild("vultureHocksR", CubeListBuilder.create()
                        .texOffs(28, 23)
                        .addBox(3.5F, 3.0F, 2.5F, 0, 3, 4, new CubeDeformation(-0.2F)),
                PartPose.ZERO
        );

        /**
         *      Saddles
         */
        bSaddle.addOrReplaceChild("cushionM", CubeListBuilder.create()
                        .texOffs(28, 0)
                        .addBox(-2.5F, -3.5F, 0.0F, 5, 5, 5, new CubeDeformation(-0.001F)),
                PartPose.ZERO
        );

        /**
         *      Tail Coverts
         */
        bCoverts.addOrReplaceChild("covertsMF", CubeListBuilder.create()
                        .texOffs(48, 0)
                        .addBox(-1.0F, -7.25F, -1.5F, 2, 7, 6),
                PartPose.ZERO
        );

        /**
         *      Tails
         */
        bTail.addOrReplaceChild("tail", CubeListBuilder.create()
                        .texOffs(24, 14)
                        .addBox(0.0F, -9.5F, -2.0F, 0, 16, 20),
                PartPose.ZERO
        );

        /**
         *      Equipment
         */
        bChicken.addOrReplaceChild("collar", CubeListBuilder.create()
                        .texOffs(0, 54)
                        .addBox(-5.0F, -2.0F, -3.0F, 10,  2, 8)
                        .texOffs(28, 54)
                        .addBox(0.0F, -2.6666F, -5.0F, 0,  4, 4),
                PartPose.offsetAndRotation(0.0F, 3.5F, -0.25F, Mth.HALF_PI * 0.5F, 0.0F, 0.0F)
        );
        bChicken.addOrReplaceChild("collarH", CubeListBuilder.create()
                        .texOffs(30, 52)
                        .addBox(-1.5F, 0.0F, -1.5F, 3, 3, 3, new CubeDeformation(-1.0F)),
                PartPose.offsetAndRotation(0.0F, -1.5F, -3.0F, Mth.HALF_PI * -0.5F, 0.0F, 0.0F)
        );

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    public ModelEnhancedChicken(ModelPart modelPart) {
        super(modelPart);
        ModelPart base = modelPart.getChild("base");
        ModelPart bChicken = base.getChild("bChicken");
        ModelPart bLeftLeg = bChicken.getChild("bLegL");
        ModelPart bRightLeg = bChicken.getChild("bLegR");
        ModelPart bBody = bChicken.getChild("bBody");
        ModelPart bNeck = bBody.getChild("bNeck");
        ModelPart bHead = bNeck.getChild("bHead");
        ModelPart bComb = bHead.getChild("bComb");
        ModelPart bWaddles = bHead.getChild("bWaddles");
        ModelPart bCrest = bHead.getChild("bCrest");
        ModelPart bBeard = bHead.getChild("bBeard");
        ModelPart bLeftWing = bBody.getChild("bWingL");
        ModelPart bRightWing = bBody.getChild("bWingR");
        ModelPart bSaddle = bBody.getChild("bSaddle");
        ModelPart bCoverts = bSaddle.getChild("bCoverts");
        ModelPart bTail = bCoverts.getChild("bTail");

        this.theChicken = new WrappedModelPart(bChicken, "bChicken");
        this.theBody = new WrappedModelPart(bBody, "bBody");
        this.theLegLeft = new WrappedModelPart(bLeftLeg, "bLegL");
        this.theLegRight = new WrappedModelPart(bRightLeg, "bLegR");
        this.theFootLeft = new WrappedModelPart("bFootL", bChicken);
        this.theFootRight = new WrappedModelPart("bFootR", bChicken);
        this.theNeck = new WrappedModelPart(bNeck, "bNeck");
        this.theHead = new WrappedModelPart(bHead,"bHead");
        this.theWingLeft = new WrappedModelPart(bLeftWing, "bWingL");
        this.theWingRight = new WrappedModelPart(bRightWing, "bWingR");
        this.theSaddle = new WrappedModelPart(bSaddle, "bSaddle");
        this.theTailCoverts = new WrappedModelPart(bCoverts, "bCoverts");
        this.theTail = new WrappedModelPart(bTail, "bTail");
        this.theComb = new WrappedModelPart(bComb, "bComb");
        this.theWaddles = new WrappedModelPart(bWaddles,"bWaddles");
        this.theCrest = new WrappedModelPart(bCrest, "bCrest");
        this.theBeard = new WrappedModelPart(bBeard,"bBeard");


        this.head = new WrappedModelPart("head", bHead);
        this.headFeathers = new WrappedModelPart("headF", bHead);

        this.beak = new WrappedModelPart("beak", bHead);
        this.eyes = new WrappedModelPart("eyes", bHead);
        this.ears = new WrappedModelPart("ears", bHead);

        this.hackle = new WrappedModelPart("hackle", bNeck);
        this.neck = new WrappedModelPart("neck", bNeck);

        this.combSingleXs = new WrappedModelPart("combSingleXs", bComb);
        this.combSingleS = new WrappedModelPart("combSingleS", bComb);
        this.combSingleM = new WrappedModelPart("combSingleM", bComb);
        this.combSingleL = new WrappedModelPart("combSingleL", bComb);
        this.combSingleXl = new WrappedModelPart("combSingleXl", bComb);
        this.combRoseTallS = new WrappedModelPart("combRoseTallS", bComb);
        this.combRoseTallM = new WrappedModelPart("combRoseTallM", bComb);
        this.combRoseTallL = new WrappedModelPart("combRoseTallL", bComb);
        this.combRoseFlatS = new WrappedModelPart("combRoseFlatS", bComb);
        this.combRoseFlatM = new WrappedModelPart("combRoseFlatM", bComb);
        this.combRoseFlatL = new WrappedModelPart("combRoseFlatL", bComb);
        this.combPeaS = new WrappedModelPart("combPeaS", bComb);
        this.combPeaM = new WrappedModelPart("combPeaM", bComb);
        this.combPeaL = new WrappedModelPart("combPeaL", bComb);
        this.combWalnutS = new WrappedModelPart("combWalnutS", bComb);
        this.combWalnutM = new WrappedModelPart("combWalnutM", bComb);
        this.combWalnutL = new WrappedModelPart("combWalnutL", bComb);
        this.combV = new WrappedModelPart("combV", bComb);
        this.combDuplex = new WrappedModelPart("combDuplex", bComb);

        this.waddlesSmall = new WrappedModelPart("waddlesS", bWaddles);
        this.waddlesMedium = new WrappedModelPart("waddlesM", bWaddles);
        this.waddlesLarge = new WrappedModelPart("waddlesL", bWaddles);
        this.waddlesPea = new WrappedModelPart("waddlesP", bWaddles);
        this.waddlesBearded = new WrappedModelPart("waddlesB", bWaddles);

        this.crestSmallF = new WrappedModelPart("crestSF", bCrest);
        this.crestMediumF = new WrappedModelPart("crestMF", bCrest);
        this.crestLargeF = new WrappedModelPart("crestLF", bCrest);

        this.earTuftLeft = new WrappedModelPart("earTuftL", bHead);
        this.earTuftRight = new WrappedModelPart("earTuftR", bHead);

        this.beardNakedNeck = new WrappedModelPart("beardNN", bBeard);
        this.beardLarge = new WrappedModelPart("beardL", bBeard);

        this.bodyNaked = new WrappedModelPart("bodyN", bBody);
        this.bodySmall = new WrappedModelPart("bodyS", bBody);
        this.bodyMedium = new WrappedModelPart("bodyM", bBody);
        this.bodyLarge = new WrappedModelPart("bodyL", bBody);

        this.wingLeftSmall = new WrappedModelPart("wingLS", bLeftWing);
        this.wingLeftMedium = new WrappedModelPart("wingLM", bLeftWing);
        this.wingRightSmall = new WrappedModelPart("wingRS", bRightWing);
        this.wingRightMedium = new WrappedModelPart("wingRM", bRightWing);

        this.thighLeft = new WrappedModelPart("thighL", bLeftLeg);
        this.thighRight = new WrappedModelPart("thighR", bRightLeg);

        this.legLeftShort = new WrappedModelPart("legLS", bLeftLeg);
        this.legLeftMedium = new WrappedModelPart("legLM", bLeftLeg);
        this.legLeftLong = new WrappedModelPart("legLL", bLeftLeg);
        this.legRightShort = new WrappedModelPart("legRS", bRightLeg);
        this.legRightMedium = new WrappedModelPart("legRM", bRightLeg);
        this.legRightLong = new WrappedModelPart("legRL", bRightLeg);

        this.footLeft = new WrappedModelPart("footL", bLeftLeg);
        this.footRight = new WrappedModelPart("footR", bRightLeg);

        this.bloomersLeft = new WrappedModelPart("bloomersL", bLeftLeg);
        this.bloomersRight = new WrappedModelPart("bloomersR", bRightLeg);

        this.pantsLeft = new WrappedModelPart("pantsL", bLeftLeg);
        this.pantsLeftLong = new WrappedModelPart("pantsLL", bLeftLeg);
        this.pantsRight = new WrappedModelPart("pantsR", bRightLeg);
        this.pantsRightLong = new WrappedModelPart("pantsLR", bRightLeg);
        this.bootsLeft = new WrappedModelPart("bootsL", bLeftLeg);
        this.bootsRight = new WrappedModelPart("bootsR", bRightLeg);
        this.toeFeathersLeft = new WrappedModelPart("toeFeathersL", bLeftLeg);
        this.toeFeathersRight = new WrappedModelPart("toeFeathersR", bRightLeg);
        this.vultureHockLeft = new WrappedModelPart("vultureHocksL", bLeftLeg);
        this.vultureHockRight = new WrappedModelPart("vultureHocksR", bRightLeg);

        this.cushion = new WrappedModelPart("cushionM", bSaddle);

        this.tailCoverMedium = new WrappedModelPart("covertsMF", bCoverts);

        this.tailFeathers = new WrappedModelPart("tail", bTail);

        this.theChicken.addChild(this.theBody);
        this.theChicken.addChild(this.theLegLeft);
        this.theChicken.addChild(this.theLegRight);
        this.theBody.addChild(this.theNeck);
        this.theBody.addChild(this.theWingLeft);
        this.theBody.addChild(this.theWingRight);
        this.theBody.addChild(this.theSaddle);
        this.theNeck.addChild(this.theHead);
        this.theHead.addChild(this.theComb);
        this.theHead.addChild(this.theWaddles);
        this.theHead.addChild(this.theCrest);
        this.theHead.addChild(this.theBeard);
        this.theLegLeft.addChild(this.theFootLeft);
        this.theLegRight.addChild(this.theFootRight);

        this.theNeck.addChild(this.hackle);
        this.theNeck.addChild(this.neck);

        this.theHead.addChild(this.head);
        this.theHead.addChild(this.headFeathers);
        this.theHead.addChild(this.beak);
        this.theHead.addChild(this.eyes);
//        this.theHead.addChild(this.ears);
//        this.theHead.addChild(this.earTuftLeft);
//        this.theHead.addChild(this.earTuftRight);
//        this.theHead.addChild(this.collar);

        this.theComb.addChild(this.combSingleXs);
        this.theComb.addChild(this.combSingleS);
        this.theComb.addChild(this.combSingleM);
        this.theComb.addChild(this.combSingleL);
        this.theComb.addChild(this.combSingleXl);
        this.theComb.addChild(this.combRoseTallS);
        this.theComb.addChild(this.combRoseTallM);
        this.theComb.addChild(this.combRoseTallL);
        this.theComb.addChild(this.combRoseFlatS);
        this.theComb.addChild(this.combRoseFlatM);
        this.theComb.addChild(this.combRoseFlatL);
        this.theComb.addChild(this.combPeaS);
        this.theComb.addChild(this.combPeaM);
        this.theComb.addChild(this.combPeaL);
        this.theComb.addChild(this.combWalnutS);
        this.theComb.addChild(this.combWalnutM);
        this.theComb.addChild(this.combWalnutL);
        this.theComb.addChild(this.combV);
        this.theComb.addChild(this.combDuplex);

        for (WrappedModelPart part : this.theComb.children) {
            if (part == this.combDuplex) {
                break;
            }
            this.combDuplex.addChild(part);
        }

        this.theWaddles.addChild(this.waddlesSmall);
        this.theWaddles.addChild(this.waddlesMedium);
        this.theWaddles.addChild(this.waddlesLarge);
        this.theWaddles.addChild(this.waddlesPea);
        this.theWaddles.addChild(this.waddlesBearded);

        this.theCrest.addChild(this.crestSmallF);
        this.theCrest.addChild(this.crestMediumF);
        this.theCrest.addChild(this.crestLargeF);

        this.theBeard.addChild(this.beardLarge);
        this.theBeard.addChild(this.beardNakedNeck);

        this.theBody.addChild(this.bodyNaked);
        this.theBody.addChild(this.bodySmall);
        this.theBody.addChild(this.bodyMedium);
        this.theBody.addChild(this.bodyLarge);

        this.theWingLeft.addChild(this.wingLeftSmall);
        this.theWingLeft.addChild(this.wingLeftMedium);

        this.theWingRight.addChild(this.wingRightSmall);
        this.theWingRight.addChild(this.wingRightMedium);

        this.theLegLeft.addChild(this.legLeftShort);
        this.theLegLeft.addChild(this.legLeftMedium);
        this.theLegLeft.addChild(this.legLeftLong);
        this.theLegLeft.addChild(this.thighLeft);
        this.theLegLeft.addChild(this.bloomersLeft);
        this.theLegLeft.addChild(this.pantsLeft);
        this.theLegLeft.addChild(this.pantsLeftLong);
        this.theLegLeft.addChild(this.bootsLeft);
        this.theLegLeft.addChild(this.toeFeathersLeft);
        this.theLegLeft.addChild(this.vultureHockLeft);
        this.theLegRight.addChild(this.legRightShort);
        this.theLegRight.addChild(this.legRightMedium);
        this.theLegRight.addChild(this.legRightLong);
        this.theLegRight.addChild(this.thighRight);
        this.theLegRight.addChild(this.bloomersRight);
        this.theLegRight.addChild(this.pantsRight);
        this.theLegRight.addChild(this.pantsRightLong);
        this.theLegRight.addChild(this.bootsRight);
        this.theLegRight.addChild(this.toeFeathersRight);
        this.theLegRight.addChild(this.vultureHockRight);

        this.theFootLeft.addChild(this.footLeft);
        this.theFootRight.addChild(this.footRight);

        this.theSaddle.addChild(this.theTailCoverts);
        this.theSaddle.addChild(this.cushion);

        this.theTailCoverts.addChild(this.theTail);
        this.theTailCoverts.addChild(this.tailCoverMedium);

        this.theTail.addChild(this.tailFeathers);


        /**
        this.xtraShortTail = new ModelPart(this, 36, 10);
        this.xtraShortTail.addBox(-0.5F, 12F, 3F, 1, 4, 3F);
        this.xtraShortTail.texOffs(37, 11);
        this.xtraShortTail.addBox(-0.5F, 11F, 4F, 1, 1, 2F);

        this.shortTail = new ModelPart(this, 34, 11);
        this.shortTail.addBox(-0.5F, 12F, 3F, 1, 4, 4F);
        this.shortTail.texOffs(36, 11);
        this.shortTail.addBox(-0.5F, 11F, 4F, 1, 1, 3F);

        this.tail = new EnhancedRendererModelNew(this, 30, 0, "NormalTail");
        this.tail.addBox(-0.5F, 0.0F, 3F, 1, 4, 5F);
        this.tail.texOffs(44, 3);
        this.tail.addBox(-0.5F, -1.0F, 4F, 1, 1, 4F);
        this.tail.setPos(0.0F, -6.5F, -0.5F);

        this.longTail = new ModelPart(this, 34, 10);
        this.longTail.addBox(-0.5F, 12F, 3F, 1, 4, 5F);
        this.longTail.texOffs(35, 11);
        this.longTail.addBox(-0.5F, 11F, 4F, 1, 1, 4F);
        this.longTail.texOffs(38, 13);
        this.longTail.addBox(-0.5F, 12F, 8F, 1, 3, 2F);
        this.longTail.texOffs(39, 15);
        this.longTail.addBox(-0.5F, 13F, 10F, 1, 1, 1F);

        this.xtraLongTail = new ModelPart(this, 34, 10);
        this.xtraLongTail.addBox(-0.5F, 12F, 4F, 1, 4, 5F);
        this.xtraLongTail.texOffs(35, 11);
        this.xtraLongTail.addBox(-0.5F, 11F, 5F, 1, 1, 4F);
        this.xtraLongTail.texOffs(38, 13);
        this.xtraLongTail.addBox(-0.5F, 12F, 9F, 1, 3, 2F);
        this.xtraLongTail.addBox(-0.5F, 15F, 9F, 1, 3, 3F);
        this.xtraLongTail.texOffs(39, 15);
        this.xtraLongTail.addBox(-0.5F, 18F, 11F, 1, 1, 1F);
        this.xtraLongTail.addBox(-0.5F, 16F, 5F, 1, 1, 1F);
         **/

        /**
         *      Equipment
         */
        this.collar = new WrappedModelPart("collar", bChicken);
        this.collarHardware = new WrappedModelPart("collarH", bChicken);

        this.theHead.addChild(this.collar);
        this.collar.addChild(this.collarHardware);
    }

    private void resetCubes() {
        for (WrappedModelPart comb : this.theComb.children) {
            comb.hide();
        }

        for (WrappedModelPart waddles : this.theWaddles.children) {
            waddles.hide();
        }

        this.crestSmallF.hide();
        this.crestMediumF.hide();
        this.crestLargeF.hide();

        this.beardLarge.hide();
        this.beardNakedNeck.hide();

        this.bodySmall.hide();
        this.bodyMedium.hide();
        this.bodyLarge.hide();

        this.wingLeftSmall.hide();
        this.wingLeftMedium.hide();
        this.wingRightSmall.hide();
        this.wingRightMedium.hide();

        for (WrappedModelPart legPart : this.theLegLeft.children) {
            legPart.hide();
        }
        for (WrappedModelPart legPart : this.theLegRight.children) {
            legPart.hide();
        }
        this.thighLeft.show();
        this.thighRight.show();

        this.theFootLeft.show();
        this.theFootRight.show();

        this.ears.show();

    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha)  {
        if (this.chickenModelData != null && this.chickenModelData.getPhenotype() != null) {
            ChickenPhenotype chicken = (ChickenPhenotype) this.chickenModelData.phenotype;

            resetCubes();

            super.renderToBuffer(this.chickenModelData, poseStack, vertexConsumer, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            Map<String, List<Float>> mapOfScale = new HashMap<>();

            mapOfScale.put("bComb", ModelHelper.createScalings(0.99F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F));
            mapOfScale.put("bWaddles", ModelHelper.createScalings(1.01F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F));

            float size = this.chickenModelData.size;
            float finalChickenSize = ((3.0F * size * this.chickenModelData.growthAmount) + size) / 4.0F;

            /**
             *      Comb
             */
            if (this.chickenModelData.growthAmount <= 0.3F) {
//                this.ears.hide();
            } else if (chicken.isCombed()){
                this.theComb.show();

                if (chicken.waddleSize >= 2) {
                    if (chicken.isBearded() && (!chicken.comb.hasPeaComb())) {
                        this.waddlesBearded.show();
                    }
                    if (!chicken.isBearded()) {
                        if (chicken.comb.hasPeaComb()) {
                            this.waddlesPea.show();
                        }
                    }
                }
                if (!chicken.isBearded() && (!chicken.comb.hasPeaComb())) {
                    if (chicken.waddleSize >= 3) {
                        this.waddlesLarge.show();
                    } else if (chicken.waddleSize >= 1) {
                        this.waddlesMedium.show();
                    } else {
                        this.waddlesLarge.show();
                    }
                }

                if (chicken.comb == Comb.SINGLE && (chicken.crestType == Crested.NONE || chicken.combSize >= 3)) {
                    switch (chicken.combSize) {
                        case 0:
                            this.combSingleXs.show();
                            break;
                        case 1:
                            this.combSingleS.show();
                            break;
                        case 2:
                            this.combSingleM.show();
                            break;
                        case 3:
                            this.combSingleL.show();
                            break;
                        case 4:
                        default:
                            this.combSingleXl.show();
                            break;

                    }
                } else if (chicken.comb == Comb.ROSE_ONE && chicken.crestType == Crested.NONE) {
                    switch (chicken.combSize) {
                        case 0:
                            this.combRoseTallS.show();
                            break;
                        case 1:
                        case 2:
                        case 3:
                            this.combRoseTallM.show();
                            break;
                        case 4:
                        default:
                            this.combRoseTallL.show();
                            break;
                    }
                } else if (chicken.comb == Comb.ROSE_TWO) {
                    switch (chicken.combSize) {
                        case 0:
                            this.combRoseFlatS.show();
                            break;
                        case 1:
                        case 2:
                        case 3:
                            this.combRoseFlatM.show();
                            break;
                        case 4:
                        default:
                            this.combRoseFlatL.show();
                            break;
                    }
                } else if (chicken.comb == Comb.PEA || (chicken.comb == Comb.SINGLE && chicken.crestType != Crested.NONE)) {
                    switch (chicken.combSize) {
                        case 1:
                        case 2:
                            this.combPeaS.show();
                            break;
                        case 3:
                            this.combPeaM.show();
                            break;
                        case 4:
                        default:
                            this.combPeaL.show();
                            break;
                    }
                } else if (chicken.comb == Comb.WALNUT || ((chicken.comb == Comb.ROSE_ONE || chicken.comb == Comb.ROSE_TWO) && chicken.crestType != Crested.NONE)) {
                    switch (chicken.combSize) {
                        case 1:
                        case 2:
                            this.combWalnutS.show();
                            break;
                        case 3:
                            this.combWalnutM.show();
                            break;
                        case 4:
                        default:
                            this.combWalnutL.show();
                            break;
                    }
                } else if (chicken.comb == Comb.V) {
                    this.combV.show();
                }

                if (chicken.butterCup) {
                    this.combDuplex.show();
                }
            }

            /**
             *      Legs
             */
            if (this.chickenModelData.offsets.containsKey("bBodyPos")) {
                if (this.chickenModelData.offsets.get("bBodyPos").y() < 8.0F + (15.5F - chicken.height)) {
                    if (chicken.creeper) {
                        if (chicken.hasLongLegs()) {
                            this.legLeftMedium.show();
                            this.legRightMedium.show();
                        } else {
                            this.legLeftShort.show();
                            this.legRightShort.show();
                        }
                    } else {
                        if (chicken.hasLongLegs()) {
                            this.legLeftLong.show();
                            this.legRightLong.show();
                        } else {
                            this.legLeftMedium.show();
                            this.legRightMedium.show();
                        }
                    }
                }
            }

            if (!chicken.isScaleless) {

                /**
                 *      Head
                 */

                /**
                 *      Neck
                 */
                this.hackle.show(!chicken.isNakedNeck);

                /**
                 *      Beard
                 */
                switch (chicken.beard) {
                    case BIG_BEARD -> {
                        this.beardLarge.show();
                    }
                    case SMALL_BEARD, NN_BEARD -> {
                        this.beardNakedNeck.show();
                    }
                }

                /**
                 *      EarTufts
                 */
                this.earTuftLeft.show(chicken.earTufts);
                this.earTuftRight.show(chicken.earTufts);


                /**
                 *      Crest
                 */
                if ((chicken.crestType == Crested.SMALL_CREST || chicken.crestType == Crested.SMALL_FORWARDCREST) || (chicken.crestType != Crested.NONE && this.chickenModelData.growthAmount > 0.5F)) {
                    this.crestSmallF.show();
                } else if (chicken.crestType == Crested.BIG_FORWARDCREST) {
                    this.crestMediumF.show();
                } else if (chicken.crestType == Crested.BIG_CREST) {
                    this.crestLargeF.show();
                }

                /**
                 *      Wings
                 */
                if (chicken.wingSize == 2) {
                    this.wingLeftMedium.show();
                    this.wingRightMedium.show();
                } else {
                    this.wingLeftSmall.show();
                    this.wingRightSmall.show();
                }

                /**
                 *      Leg Feathers
                 */
                this.bloomersLeft.show();
                this.bloomersRight.show();

                switch (chicken.footFeatherType) {
                    case BIG_TOEFEATHERS:
                    case TOEFEATHERS:
                        this.toeFeathersLeft.show();
                        this.toeFeathersRight.show();
                    case FOOTFEATHERS:
                        this.bootsLeft.show();
                        this.bootsRight.show();
                    case LEGFEATHERS:
                        if (!this.chickenModelData.sleeping) {
                            this.pantsLeft.show();
                            this.pantsRight.show();
                            if (chicken.longHockFeathers || chicken.hasLongLegs()) {
                                this.pantsLeftLong.show();
                                this.pantsRightLong.show();
                            }
                        }

                        if (chicken.isVultureHocked) {
                            this.vultureHockLeft.show();
                            this.vultureHockRight.show();
                        }
                        break;
                }


                /**
                 *      Body
                 */
                switch (chicken.bodyType) {
                    case -1:
                        this.bodySmall.show();
                        this.bodyMedium.hide();
                        this.bodyLarge.hide();
                        break;
                    case 0:
                        this.bodySmall.hide();
                        this.bodyMedium.show();
                        this.bodyLarge.hide();
                        break;
                    default:
                        this.bodySmall.hide();
                        this.bodyMedium.hide();
                        this.bodyLarge.show();
                        break;
                }

                /**
                 *      Tail
                 */
                this.theSaddle.show(!chicken.rumpless || this.chickenModelData.growthAmount > 0.15F);
                this.cushion.show();
                this.tailCoverMedium.show();
                this.tailFeathers.show();
            }

            if (this.chickenModelData.growthAmount != 1.0F) {
                mapOfScale.put("bNeck", ModelHelper.createScalings(1.0F + ((1.0F-this.chickenModelData.growthAmount)*0.3F), 0.0F, (1.0F-this.chickenModelData.growthAmount)*0.1F, 0.0F));
            }
            mapOfScale.put("collar", ModelHelper.createScalings(0.5F, 0.0F, 0.0F, 0.0F));
            mapOfScale.put("collarH", ModelHelper.createScalings(2.0F, 0.0F, 0.0F, 0.0F));

            poseStack.pushPose();
            poseStack.scale(finalChickenSize, finalChickenSize, finalChickenSize);
            poseStack.translate(0.0F, -1.5F + 1.5F / finalChickenSize, 0.0F);

            gaRender(this.theChicken, mapOfScale, poseStack, vertexConsumer, packedLightIn, packedOverlayIn, red, green, blue, alpha);

            poseStack.popPose();
        }
  }

    /**
     *      Animations
     */
    protected void saveAnimationValues(ChickenModelData data, ChickenPhenotype chicken) {
        Map<String, Vector3f> map = data.offsets;
        map.put("bChicken", this.getRotationVector(this.theChicken));
        map.put("bChickenPos", this.getPosVector(this.theChicken));
        map.put("bBody", this.getRotationVector(this.theBody));
        map.put("bBodyPos", this.getPosVector(this.theBody));
        map.put("bHead", this.getRotationVector(this.theHead));
        map.put("bHeadPos", this.getPosVector(this.theHead));
        map.put("bLegL", this.getRotationVector(this.theLegLeft));
        map.put("bLegR", this.getRotationVector(this.theLegRight));
        map.put("bLegLPos", this.getPosVector(this.theLegLeft));
        map.put("bLegRPos", this.getPosVector(this.theLegRight));
        map.put("bWingL", this.getRotationVector(this.theWingLeft));
        map.put("bWingR", this.getRotationVector(this.theWingRight));
        map.put("bWingLPos", this.getPosVector(this.theWingLeft));
        map.put("bWingRPos", this.getPosVector(this.theWingRight));
        map.put("bFootL", this.getRotationVector(this.theFootLeft));
        map.put("bFootR", this.getRotationVector(this.theFootRight));
        map.put("bFootLPos", this.getPosVector(this.theFootLeft));
        map.put("bFootRPos", this.getPosVector(this.theFootRight));
        map.put("bNeck", this.getRotationVector(this.theNeck));
        map.put("bNeckPos", this.getPosVector(this.theNeck));
        map.put("bSaddle", this.getRotationVector(this.theSaddle));
        map.put("bCoverts", this.getRotationVector(this.theTailCoverts));
        map.put("bTail", this.getRotationVector(this.theTail));
    }

    private void readInitialAnimationValues(ChickenModelData data, ChickenPhenotype chicken) {
        Map<String, Vector3f> map = data.offsets;
        if (map.isEmpty()) {
            this.theChicken.setY(chicken.height);
            switch (chicken.bodyType) {
                case -1:
                    this.theWingLeft.setX(2.5F);
                    this.theWingRight.setX(-2.5F);
                    break;
                case 0:
                    this.theWingLeft.setX(3.0F);
                    this.theWingRight.setX(-3.0F);
                    break;
                case 1:
                    this.theWingLeft.setX(3.5F);
                    this.theWingRight.setX(-3.5F);
                    break;
            }
            this.theNeck.setXRot(-chicken.bodyAngle);
            this.theHead.setXRot(chicken.bodyAngle);
            this.theSaddle.setXRot(Mth.HALF_PI*-0.675F);
            this.theTailCoverts.setXRot(Mth.HALF_PI*0.15F);
            this.theTail.setXRot(Mth.HALF_PI*0.1F);

            this.theWingLeft.setY(chicken.wingPlacement);
            this.theWingRight.setY(chicken.wingPlacement);

            this.theWingLeft.setXRot(chicken.wingAngle);
            this.theWingRight.setXRot(chicken.wingAngle);

            this.theFootLeft.setY((15.5F-chicken.height) + 10.0F);
            this.theFootRight.setY(this.theFootLeft.getY());
        } else {
            this.theChicken.setRotation(map.get("bChicken"));
            this.theChicken.setPos(map.get("bChickenPos"));
            this.theBody.setRotation(map.get("bBody"));
            this.theBody.setPos(map.get("bBodyPos"));
            this.theLegLeft.setRotation(map.get("bLegL"));
            this.theFootLeft.setRotation(map.get("bFootL"));
            this.theFootRight.setRotation(map.get("bFootR"));
            this.theFootLeft.setPos(map.get("bFootLPos"));
            this.theFootRight.setPos(map.get("bFootRPos"));
            this.theLegLeft.setPos(map.get("bLegLPos"));
            this.theLegRight.setRotation(map.get("bLegR"));
            this.theLegRight.setPos(map.get("bLegRPos"));
            this.theWingLeft.setRotation(map.get("bWingL"));
            this.theWingLeft.setPos(map.get("bWingLPos"));
            this.theWingRight.setRotation(map.get("bWingR"));
            this.theWingRight.setPos(map.get("bWingRPos"));
            this.theHead.setRotation(map.get("bHead"));
            this.theHead.setPos(map.get("bHeadPos"));
            this.theNeck.setRotation(map.get("bNeck"));
            this.theNeck.setPos(map.get("bNeckPos"));
            this.theSaddle.setRotation(map.get("bSaddle"));
            this.theTailCoverts.setRotation(map.get("bCoverts"));
            this.theTail.setRotation(map.get("bTail"));
        }
    }
    @Override
    public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.chickenModelData = getCreateChickenModelData(entityIn);
        if (this.chickenModelData!= null) {
            ChickenPhenotype chicken = this.chickenModelData.getPhenotype();
            readInitialAnimationValues(this.chickenModelData, chicken);
            boolean isMoving = entityIn.getDeltaMovement().horizontalDistanceSqr() > 1.0E-7D || entityIn.xOld != entityIn.getX() || entityIn.zOld != entityIn.getZ();

            if (this.chickenModelData.sleeping && !isMoving) {
                if (this.chickenModelData.sleepDelay == -1) {
                    this.chickenModelData.sleepDelay = (int) ageInTicks + ((entityIn.getRandom().nextInt(10)) * 20) + 10;
                } else if (this.chickenModelData.sleepDelay <= ageInTicks+50) {
                    if (this.chickenModelData.sleepDelay <= ageInTicks) {
                        this.chickenModelData.sleepDelay = 0;
                        layDownAnimation(9.0F + (15.5F-chicken.height),true);
                    } else {
                        layDownAnimation(9.0F + (15.5F-chicken.height),false);
                        headLookingAnimation(netHeadYaw, headPitch, chicken.bodyAngle);
                    }
                }
            } else {
                if (this.chickenModelData.sleepDelay != -1) {
                    this.chickenModelData.sleepDelay = -1;
                }

                boolean flag = true;
                if (this.chickenModelData.isEating != 0) {
                    if (this.chickenModelData.isEating == -1) {
                        this.chickenModelData.isEating = (int)ageInTicks + 140;
                    } else if (this.chickenModelData.isEating < ageInTicks) {
                        this.chickenModelData.isEating = 0;
                    }
                    flag = grazingAnimation(this.chickenModelData.isEating - (int)ageInTicks);
                }

                if (flag) {
                    if (this.theBody.getY() != 2.0F || this.theBody.getXRot() != chicken.bodyAngle) {
                        standUpAnimation(chicken.bodyAngle);
                    }

                    float h = this.head.getY();
                    if (h != -5.0F || (netHeadYaw == 0 && headPitch == 0)) {
                        moveHeadAnimation(h, chicken.bodyAngle);
                    } else {
                        headLookingAnimation(netHeadYaw, headPitch, chicken.bodyAngle);
                    }
                }

                if (isMoving) {
                    walkingLegsAnimation(limbSwing, limbSwingAmount);
                } else if (this.theBody.getY() <= 3.0F){
                    standingLegsAnimation();
                }
            }

            if (!entityIn.isOnGround()) {
                animateFalling(ageInTicks);
            } else {
                if (this.theWingLeft.getZRot() > 0.001F) {
                    float flap = 1.2F * Mth.cos(ageInTicks);
                    this.theWingLeft.setZRot(-flap);
                    this.theWingRight.setZRot(flap);
                    if (this.theWingLeft.getXRot() < 0.0F) {
                        this.theWingLeft.setXRot(-flap);
                        this.theWingRight.setXRot(-flap);
                    } else {
                        this.theWingLeft.setXRot(0.0F);
                        this.theWingRight.setXRot(0.0F);
                    }
                } else {
                    this.theWingLeft.setZRot(this.lerpTo(this.theWingLeft.getZRot(), 0.0F));
                    this.theWingRight.setZRot(this.lerpTo(this.theWingRight.getZRot(), 0.0F));
                    this.theWingLeft.setXRot(this.lerpTo(this.theWingLeft.getXRot(), 0.0F));
                    this.theWingRight.setXRot(this.lerpTo(this.theWingRight.getXRot(), 0.0F));
                }
            }

            saveAnimationValues(this.chickenModelData, chicken);
        }
    }

    private void standingLegsAnimation() {
        this.theLegRight.setXRot(this.lerpTo(this.theLegRight.getXRot(), 0.0F));
        this.theLegLeft.setXRot(this.lerpTo(this.theLegLeft.getXRot(), 0.0F));
    }

    private void walkingLegsAnimation(float limbSwing, float limbSwingAmount) {
        this.theLegRight.setXRot(Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount);
        this.theLegLeft.setXRot(Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount);
    }

    private void headLookingAnimation(float netHeadYaw, float headPitch, float bodyAngle) {
        this.theNeck.setXRot((headPitch * 0.017453292F) - bodyAngle);
        this.theNeck.setYRot(netHeadYaw * 0.017453292F);
//        this.theNeck.setY(this.lerpTo(this.theNeck.getY(), -5.0F));
//        this.theNeck.setZ(this.lerpTo(this.theNeck.getZ(), -2.75F));
    }

    private void moveHeadAnimation(float h, float bodyAngle) {
        this.theNeck.setXRot(this.lerpTo(this.theNeck.getXRot(), -bodyAngle));
        if (h > -5.0F) {
            //corrects sleeping head pos/rot
            h = Math.abs(this.theNeck.getXRot());
            if (h+bodyAngle < Mth.HALF_PI * 0.5F) {
                this.theNeck.setYRot(this.lerpTo(this.theNeck.getYRot(), 0.0F));
            }
//            this.theNeck.setY(this.lerpTo(this.theNeck.getY(), -5.0F));
//            this.theNeck.setZ(this.lerpTo(this.theNeck.getZ(), -2.75F));
        } else {
            this.theNeck.setYRot(this.lerpTo(this.theNeck.getYRot(), 0.0F));
        }
    }

    private boolean grazingAnimation(float ticks) {
        this.theNeck.setYRot(this.lerpTo(this.theNeck.getYRot(), 0.0F));
        if (ticks < 50) {
            this.theLegLeft.setXRot(this.lerpTo(this.theLegLeft.getXRot(), 0.0F));
            this.theLegRight.setXRot(this.lerpTo(this.theLegRight.getXRot(), 0.0F));
            float headRot = this.theNeck.getXRot();
            if (headRot < Mth.HALF_PI*0.75F) {
                this.theNeck.setXRot(this.lerpTo(this.theNeck.getXRot(), Mth.HALF_PI));
//                this.theNeck.setY(this.lerpTo(this.theNeck.getY(), -3.0F));
            } else {
                float loop = (float) Math.cos(ticks*0.75F);
                if (loop > 0) {
                    this.theNeck.setXRot(this.lerpTo(this.theNeck.getXRot(), Mth.HALF_PI * 0.8F));
//                    this.theNeck.setY(this.lerpTo(this.theNeck.getY(), -2.0F));
                } else {
                    this.theNeck.setXRot(this.lerpTo(0.08F, this.theNeck.getXRot(), Mth.HALF_PI * 1.15F));
//                    this.theNeck.setY(this.lerpTo(0.1F, this.theNeck.getY(), 0.0F));
                }
            }
        } else if (ticks < 100) {
            this.theBody.setXRot(this.lerpTo(this.theBody.getXRot(), 0.0F));
            this.theNeck.setXRot(this.lerpTo(this.theNeck.getXRot(), 0.0F));
            float loop = (float) Math.cos(ticks*0.5F);
            if (loop > 0) {
                if (this.theLegLeft.getXRot() <= 0.0F) {
                    this.theLegLeft.setXRot(0.0F);
                    this.theLegRight.setXRot(this.lerpTo(0.1F,this.theLegRight.getXRot(), Mth.HALF_PI));
                } else {
                    this.theLegLeft.setXRot(this.lerpTo(0.1F, this.theLegLeft.getXRot(), -0.314159F));
                }
            } else {
                if (this.theLegRight.getXRot() <= 0.0F) {
                    this.theLegRight.setXRot(0.0F);
                    this.theLegLeft.setXRot(this.lerpTo(0.1F, this.theLegLeft.getXRot(), Mth.HALF_PI));
                } else {
                    this.theLegRight.setXRot(this.lerpTo(0.1F,this.theLegRight.getXRot(), -0.314159F));
                }
            }
        } else {
            return true;
        }
        return false;
    }

    private void standUpAnimation(float bodyAngle) {
        this.theBody.setY(this.lerpTo(this.theBody.getY(),2.0F));
        this.theBody.setXRot(this.lerpTo(this.theBody.getXRot(), bodyAngle));
    }

    private void layDownAnimation(float height, boolean asleep) {
        if (asleep) {
            float h = this.theNeck.getYRot();
            if (h == 0.0F) {
                h = ThreadLocalRandom.current().nextBoolean() ? 0.0001F : -0.0001F;
            }
            if (h > 0.0F) {
                this.theNeck.setYRot(this.lerpTo(h, Mth.HALF_PI * 1.85F));
            } else {
                this.theNeck.setYRot(this.lerpTo(h, Mth.HALF_PI * -1.85F));
            }
            if (Math.abs(this.theNeck.getYRot()) > Mth.HALF_PI) {
                this.theNeck.setXRot(this.lerpTo(this.theNeck.getXRot(), Mth.HALF_PI));
//                this.theNeck.setZ(this.lerpTo(this.theNeck.getZ(), -3.75F));
            }
//            this.theNeck.setY(this.lerpTo(this.theNeck.getY(), -7.01F));
        }
        this.theBody.setY(this.lerpTo(this.theBody.getY(), height));
        this.theBody.setXRot(this.lerpTo(this.theBody.getXRot(), 0.0F));
        standingLegsAnimation();
    }

    public void animateFalling( float ageInTicks) {
        float flap = 1.2F * Mth.cos(ageInTicks);
        if (this.theWingLeft.getXRot() > (Math.PI * -0.25F)) {
            this.theWingLeft.setXRot(-flap);
            this.theWingRight.setXRot(-flap);
        } else {
            this.theWingLeft.setXRot((float) Math.PI * -0.25F);
            this.theWingRight.setXRot((float) Math.PI * -0.25F);
        }
        this.theWingLeft.setZRot(-flap);
        this.theWingRight.setZRot(flap);
    }

    private ChickenModelData getCreateChickenModelData(T enhancedChicken) {
        return (ChickenModelData) getCreateAnimalModelData(enhancedChicken);
    }

    @Override
    protected void setInitialModelData(T enhancedChicken) {
        ChickenModelData chickenModelData = new ChickenModelData();
        chickenModelData.isFemale = enhancedChicken.getOrSetIsFemale();
        setBaseInitialModelData(chickenModelData, enhancedChicken);
    }

    @Override
    protected ChickenPhenotype createPhenotype(T enhancedAnimal) {
        return new ChickenPhenotype(enhancedAnimal.getSharedGenes());
    }

}
