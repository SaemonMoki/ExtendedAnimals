package mokiyoki.enhancedanimals.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import mokiyoki.enhancedanimals.config.EanimodCommonConfig;
import mokiyoki.enhancedanimals.entity.EnhancedChicken;
import mokiyoki.enhancedanimals.model.modeldata.AnimalModelData;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by saemon on 8/09/2018.
 */
@OnlyIn(Dist.CLIENT)
public class ModelEnhancedChicken<T extends EnhancedChicken> extends EnhancedAnimalModel<T> {
    private static WrappedModelPart theChicken;

    private static WrappedModelPart theHead;
    private static WrappedModelPart theEars;
    private static WrappedModelPart theComb;
    private static WrappedModelPart theWaddles;
    private static WrappedModelPart theCrest;
    private static WrappedModelPart theBeard;
    private static WrappedModelPart theNeck;
    private static WrappedModelPart theBody;
    private static WrappedModelPart theWingLeft;
    private static WrappedModelPart theWingRight;
    private static WrappedModelPart theLegLeft;
    private static WrappedModelPart theLegRight;
    private static WrappedModelPart theFootLeft;
    private static WrappedModelPart theFootRight;
    private static WrappedModelPart theSaddle;
    private static WrappedModelPart theTailCoverts;
    private static WrappedModelPart theTail;

    private static WrappedModelPart head;
    private static WrappedModelPart headFeathers;

    private static WrappedModelPart beak;
    private static WrappedModelPart jaw;
    private static WrappedModelPart earTiny;
    private static WrappedModelPart earSmall;
    private static WrappedModelPart earMedium;
    private static WrappedModelPart earLarge;
    private static WrappedModelPart earXLarge;
    private static WrappedModelPart waddlesSmall;
    private static WrappedModelPart waddlesMedium;
    private static WrappedModelPart waddlesLarge;
    private static WrappedModelPart waddlesPea;
    private static WrappedModelPart waddlesBearded;

    private static WrappedModelPart combDuplex;
    private static WrappedModelPart combSingleXs;
    private static WrappedModelPart combSingleS;
    private static WrappedModelPart combSingleM;
    private static WrappedModelPart combSingleL;
    private static WrappedModelPart combSingleXl;
    private static WrappedModelPart combRoseTallS;
    private static WrappedModelPart combRoseTallM;
    private static WrappedModelPart combRoseTallL;
    private static WrappedModelPart combRoseFlatS;
    private static WrappedModelPart combRoseFlatM;
    private static WrappedModelPart combRoseFlatL;
    private static WrappedModelPart combPeaS;
    private static WrappedModelPart combPeaM;
    private static WrappedModelPart combPeaL;
    private static WrappedModelPart combWalnutS;
    private static WrappedModelPart combWalnutM;
    private static WrappedModelPart combWalnutL;
    private static WrappedModelPart combV;

    private static WrappedModelPart crestSmall;
    private static WrappedModelPart crestMedium;
    private static WrappedModelPart crestLarge;

    private static WrappedModelPart beardNakedNeck;
    private static WrappedModelPart beardLarge;

    private static WrappedModelPart earTuftLeft;
    private static WrappedModelPart earTuftRight;

    private static WrappedModelPart neck;
    private static WrappedModelPart hackle;

    private static WrappedModelPart bodyNaked;
    private static WrappedModelPart bodyFeathers;

    private static WrappedModelPart wingLeftNaked;
    private static WrappedModelPart wingRightNaked;
    private static WrappedModelPart wingLeftSmall;
    private static WrappedModelPart wingRightSmall;
    private static WrappedModelPart wingLeftMedium;
    private static WrappedModelPart wingRightMedium;

    private static WrappedModelPart thighLeft;
    private static WrappedModelPart thighRight;
    private static WrappedModelPart bloomersLeft;
    private static WrappedModelPart bloomersRight;

    private static WrappedModelPart legLeftShort;
    private static WrappedModelPart legLeftMedium;
    private static WrappedModelPart legLeftLong;
    private static WrappedModelPart legRightShort;
    private static WrappedModelPart legRightMedium;
    private static WrappedModelPart legRightLong;

    private static WrappedModelPart footLeft;
    private static WrappedModelPart footRight;

    private static WrappedModelPart pantsLeft;
    private static WrappedModelPart pantsLeftLong;
    private static WrappedModelPart pantsRight;
    private static WrappedModelPart pantsRightLong;
    private static WrappedModelPart bootsLeft;
    private static WrappedModelPart bootsRight;
    private static WrappedModelPart toeFeathersLeft;
    private static WrappedModelPart toeFeathersRight;
    private static WrappedModelPart vultureHockLeft;
    private static WrappedModelPart vultureHockRight;
    private static WrappedModelPart tailNub;
    private static WrappedModelPart cushion;
    private static WrappedModelPart tailCover;
    private static WrappedModelPart tailFeathers;

    private ChickenModelData chickenModelData;
    private static Map<String, WrappedModelPart> chickenModelParts = new HashMap<>();
    private static final float earMaxGrowth = EanimodCommonConfig.COMMON.adultAgeChicken.get();

    private static Map<String, WrappedModelPart> getModelParts() {
        Map<String, WrappedModelPart> map = new HashMap<>();
        map.put("bChicken", theChicken);
        map.put("bHead", theHead);
        map.put("bEars",  theEars);
        map.put("bComb",  theComb);
        map.put("bWaddles",  theWaddles);
        map.put("bCrest",  theCrest);
        map.put("bBeard",  theBeard);
        map.put("bNeck",  theNeck);
        map.put("bBody",  theBody);
        map.put("bWingL",  theWingLeft);
        map.put("bWingR",  theWingRight);
        map.put("bLegL",  theLegLeft);
        map.put("bLegR",  theLegRight);
        map.put("bFootL",  theFootLeft);
        map.put("bFootR",  theFootRight);
        map.put("bSaddle",  theSaddle);
        map.put("bCoverts",  theTailCoverts);
        map.put("bTail",  theTail);

        map.put("head",  head);
        map.put("headF",  headFeathers);

        map.put("beak",  beak);
        map.put("jaw", jaw);
        map.put("earT",  earTiny);
        map.put("earS",  earSmall);
        map.put("earM",  earMedium);
        map.put("earL",  earLarge);
        map.put("earXl",  earXLarge);
        map.put("waddlesS",  waddlesSmall);
        map.put("waddlesM",  waddlesMedium);
        map.put("waddlesL",  waddlesLarge);
        map.put("waddlesP",  waddlesPea);
        map.put("waddlesB",  waddlesBearded);
        map.put("combDuplex",  combDuplex);
        map.put("combSingleXs",  combSingleXs);
        map.put("combSingleS",  combSingleS);
        map.put("combSingleM",  combSingleM);
        map.put("combSingleL",  combSingleL);
        map.put("combSingleXl",  combSingleXl);
        map.put("combRoseTallS",  combRoseTallS);
        map.put("combRoseTallM",  combRoseTallM);
        map.put("combRoseTallL",  combRoseTallL);
        map.put("combRoseFlatS",  combRoseFlatS);
        map.put("combRoseFlatM",  combRoseFlatM);
        map.put("combRoseFlatL",  combRoseFlatL);
        map.put("combPeaS",  combPeaS);
        map.put("combPeaM",  combPeaM);
        map.put("combPeaL",  combPeaL);
        map.put("combWalnutS",  combWalnutS);
        map.put("combWalnutM",  combWalnutM);
        map.put("combWalnutL",  combWalnutL);
        map.put("combV",  combV);
        map.put("crestS",  crestSmall);
        map.put("crestM",  crestMedium);
        map.put("crestL",  crestLarge);
        map.put("beardN",  beardNakedNeck);
        map.put("beardL",  beardLarge);
        map.put("earTuftL",  earTuftLeft);
        map.put("earTuftR",  earTuftRight);
        map.put("neck",  neck);
        map.put("hackle",  hackle);
        map.put("bodyN",  bodyNaked);
        map.put("bodyF",  bodyFeathers);
        map.put("wingLN",  wingLeftNaked);
        map.put("wingRN",  wingRightNaked);
        map.put("wingLS",  wingLeftSmall);
        map.put("wingRS",  wingRightSmall);
        map.put("wingLM",  wingLeftMedium);
        map.put("wingRM",  wingRightMedium);
        map.put("thighL",  thighLeft);
        map.put("thighR",  thighRight);
        map.put("bloomersL",  bloomersLeft);
        map.put("bloomersR",  bloomersRight);
        map.put("legLS",  legLeftShort);
        map.put("legLM",  legLeftMedium);
        map.put("legLL",  legLeftLong);
        map.put("legRS",  legRightShort);
        map.put("legRM",  legRightMedium);
        map.put("legRL",  legRightLong);
        map.put("footL",  footLeft);
        map.put("footR",  footRight);
        map.put("pantsL",  pantsLeft);
        map.put("pantsLL",  pantsLeftLong);
        map.put("pantsR",  pantsRight);
        map.put("pantsRL",  pantsRightLong);
        map.put("bootsL",  bootsLeft);
        map.put("bootsR",  bootsRight);
        map.put("toeFeathersL",  toeFeathersLeft);
        map.put("toeFeathersR",  toeFeathersRight);
        map.put("vultureHocksL",  vultureHockLeft);
        map.put("vultureHocksR",  vultureHockRight);
        map.put("tailN",  tailNub);
        map.put("cushion",  cushion);
        map.put("tailC",  tailCover);
        map.put("tail",  tailFeathers);
        return map;
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition base = meshdefinition.getRoot().addOrReplaceChild("base", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        base.addOrReplaceChild("bChicken", CubeListBuilder.create(), PartPose.offset(0.0F, 17.5F, 0.0F));
        base.addOrReplaceChild("bBody", CubeListBuilder.create(), PartPose.ZERO);
        base.addOrReplaceChild("bNeck", CubeListBuilder.create(), PartPose.offset(0.0F, -2.5F, -3.5F));
        base.addOrReplaceChild("bHead", CubeListBuilder.create(), PartPose.offset(0.0F, -4.625F, 0.3F));
        base.addOrReplaceChild("bEars", CubeListBuilder.create(), PartPose.offset(0.0F, -1.0F, 0.0F));
        base.addOrReplaceChild("bComb", CubeListBuilder.create(), PartPose.offset(0.0F, -1.0F, 0.0F));
        base.addOrReplaceChild("bWaddles", CubeListBuilder.create(), PartPose.offset(0.0F, -0.5F, -1.0F));
        base.addOrReplaceChild("bCrest", CubeListBuilder.create(), PartPose.offset(0.0F, -2.0F,-1.0F));
        base.addOrReplaceChild("bBeard", CubeListBuilder.create(), PartPose.offset(0.0F, 2.5F, 0.0F));
        base.addOrReplaceChild("bWingL", CubeListBuilder.create(), PartPose.offset(3.0F, -5.0F, -3.0F));
        base.addOrReplaceChild("bWingR", CubeListBuilder.create(), PartPose.offset(-3.0F, -5.0F, -3.0F));
        base.addOrReplaceChild("bLegL", CubeListBuilder.create(), PartPose.offset(0.0F, 0.5F, -0.5F));
        base.addOrReplaceChild("bLegR", CubeListBuilder.create(), PartPose.offset(0.0F, 0.5F, -0.5F));
        base.addOrReplaceChild("bFootL", CubeListBuilder.create(), PartPose.ZERO);
        base.addOrReplaceChild("bFootR", CubeListBuilder.create(), PartPose.ZERO);
        base.addOrReplaceChild("bSaddle", CubeListBuilder.create(), PartPose.offset(0.0F, -6.0F, 2.5F));
        base.addOrReplaceChild("bCoverts", CubeListBuilder.create(), PartPose.offset(0.0F,2.0F, 2.0F));
        base.addOrReplaceChild("bTail", CubeListBuilder.create(), PartPose.offset(0.0F, -4.0F, 0.0F));

        /**
         *      - Heads and HeadParts
         */
        base.addOrReplaceChild("headF",
                CubeListBuilder.create()
                        .texOffs(12, 0)
                        .addBox(-2.0F, -2.5F, -3.0F, 4, 3, 4, new CubeDeformation(-0.5F)),
                PartPose.ZERO
        );
        base.addOrReplaceChild("head",
                CubeListBuilder.create()
                        .texOffs(0, 5)
                        .addBox(-2.0F, -2.5F, -3.0F, 4, 3, 4, new CubeDeformation(-0.51F)),
                PartPose.ZERO
        );
        base.addOrReplaceChild("beak",
                CubeListBuilder.create()
                        .texOffs(0, 21)
                        .addBox(-0.5F, 0.0F, -2.0F, 1, 1, 2, new CubeDeformation(-0.05F)),
                PartPose.offsetAndRotation(0.0F,  -1.25F, -2.0F, 0.08F, 0.0F, 0.0F)
        );
        base.addOrReplaceChild("jaw",
                CubeListBuilder.create()
                        .texOffs(0, 22)
                        .addBox(-0.5F, 0.0F, -2.0F, 1, 1, 2, new CubeDeformation(-0.06F, -0.4F, -0.15F)),
                PartPose.offset(0.0F,  -0.65F, -2.0F)
        );

        /**
         *  Ears
         */
        base.addOrReplaceChild("earT",
                CubeListBuilder.create()
                        .texOffs(0, 49)
                        .mirror(true)
                        .addBox(-2.0F, 0.0F, -1.0F, 1, 2, 2, new CubeDeformation(-0.25F, -0.5F, -0.5F))
                        .mirror(false)
                        .addBox(1.0F, 0.0F, -1.0F, 1, 2, 2, new CubeDeformation(-0.25F, -0.5F, -0.5F)),
                PartPose.offset(0.0F, -0.75F, -0.625F)
        );
        base.addOrReplaceChild("earS",
                CubeListBuilder.create()
                        .texOffs(0, 49)
                        .mirror(true)
                        .addBox(-2.0F, 0.0F, -1.0F, 1, 2, 2, new CubeDeformation(-0.25F))
                        .mirror(false)
                        .addBox(1.0F, 0.0F, -1.0F, 1, 2, 2, new CubeDeformation(-0.25F)),
                PartPose.offset(0.0F, 0.5F, -0.625F)
        );
        base.addOrReplaceChild("earM",
                CubeListBuilder.create()
                        .texOffs(0, 49)
                        .mirror(true)
                        .addBox(-2.0F, 0.0F, -1.0F, 1, 3, 3, new CubeDeformation(-0.25F, -0.5F, -0.5F))
                        .mirror(false)
                        .addBox(1.0F, 0.0F, -1.0F, 1, 3, 3, new CubeDeformation(-0.25F, -0.5F, -0.5F)),
                PartPose.offset(0.0F, -0.75F, -1.125F)
        );
        base.addOrReplaceChild("earL",
                CubeListBuilder.create()
                        .texOffs(0, 49)
                        .mirror(true)
                        .addBox(-2.0F, 0.0F, -1.0F, 1, 4, 3, new CubeDeformation(-0.25F, -0.5F, -0.5F))
                        .mirror(false)
                        .addBox(1.0F, 0.0F, -1.0F, 1, 4, 3, new CubeDeformation(-0.25F, -0.5F, -0.5F)),
                PartPose.offset(0.0F, -0.75F, -1.125F)
        );
        base.addOrReplaceChild("earXl",
                CubeListBuilder.create()
                        .texOffs(0, 49)
                        .mirror(true)
                        .addBox(-2.0F, 0.0F, -1.0F, 1, 5, 4, new CubeDeformation(-0.25F, -0.5F, -0.5F))
                        .mirror(false)
                        .addBox(1.0F, 0.0F, -1.0F, 1, 5, 4, new CubeDeformation(-0.25F, -0.5F, -0.5F)),
                PartPose.offset(0.0F, -0.75F, -2.125F)
        );

        /**
         *  Eyes
         */
        base.addOrReplaceChild("eyes",
                CubeListBuilder.create()
                        .texOffs(24, 0)
                        .addBox(0.61F, 0.05F, -0.201F, 1, 1, 1, new CubeDeformation(-0.1F))
                        .texOffs(12, 0)
                        .addBox(-1.61F, 0.05F, -0.201F, 1, 1, 1, new CubeDeformation(-0.1F)),
                PartPose.offset(0.0F, -1.6F, -2.41F)
        );

        /**
         *  Waddles
         */
        base.addOrReplaceChild("waddlesS",
                CubeListBuilder.create()
                        .texOffs(7, -2)
                        .addBox(-0.491F, 0.0F, -2.0F, 0, 1, 2)
                        .addBox(0.491F, 0.0F, -2.0F, 0, 1, 2),
                PartPose.ZERO
        );
        base.addOrReplaceChild("waddlesM",
                CubeListBuilder.create()
                        .texOffs(7, -2)
                        .addBox(-0.491F, 0.0F, -2.0F, 0, 2, 2)
                        .addBox(0.491F, 0.0F, -2.0F, 0, 2, 2),
                PartPose.ZERO
        );
        base.addOrReplaceChild("waddlesL",
                    CubeListBuilder.create()
                            .texOffs(7, -2)
                            .addBox(-0.491F, 0.0F, -2.0F, 0, 3, 2)
                            .addBox(0.491F, 0.0F, -2.0F, 0, 3, 2),
                    PartPose.ZERO
        );
        base.addOrReplaceChild("waddlesP",
                    CubeListBuilder.create()
                            .texOffs(7, -2)
                            .addBox(0.0F, 0.0F, -2.0F, 0, 2, 2),
                    PartPose.ZERO
        );
        base.addOrReplaceChild("waddlesB",
                    CubeListBuilder.create()
                            .texOffs(0, 1)
                            .addBox(-1.5F, 0.0F, -1.0F, 3, 1, 1),
                    PartPose.ZERO
        );

        /**
         *     Beard
         */
        base.addOrReplaceChild("beardL",
            CubeListBuilder.create()
                .texOffs(0, 30)
                .addBox(-2.5F, -4.0F, -2.0F, 2, 2, 2)
                .texOffs(0, 34)
                .addBox(-2.0F, -3.5F, -2.75F, 4, 2, 2)
                .texOffs(10, 32)
                .addBox(-1.0F, -2.75F, -3.25F, 2, 2, 2)
                .texOffs(6, 28)
                .addBox(0.5F, -4.0F, -2.0F, 2, 2, 2),
            PartPose.ZERO
        );
        base.addOrReplaceChild("beardN", CubeListBuilder.create()
                .texOffs(0, 30)
                .addBox(-3F, -4F, -2F, 1, 2, 2)
                .texOffs(6, 28)
                .addBox(2F, -4F, -2F, 1, 2, 2)
                .texOffs(0, 34)
                .addBox(-2F, -3.0F, -2.75F, 2, 2, 2)
                .texOffs(4, 34)
                .addBox(0F, -3F, -2.75F, 2, 2, 2)
                .texOffs(10, 32)
                .addBox(-0.5F, -2.0F, -3.5F, 1, 1, 1),
            PartPose.ZERO
        );

        /**
         *      EarTufts
         */
        base.addOrReplaceChild("earTuftL", CubeListBuilder.create()
                .texOffs(19, 8)
                .addBox(-3.0F, 0.0F, 0.0F, 3, 2, 0),
            PartPose.offsetAndRotation(-1.5F, -1.5F, -1.0F, 0.0F, Mth.HALF_PI*0.5F, 0.0F)
        );
        base.addOrReplaceChild("earTuftR", CubeListBuilder.create()
                .mirror(true)
                .texOffs(22, 8)
                .addBox(0.0F, 0.0F, 0.0F, 3, 2, 0),
            PartPose.offsetAndRotation(1.5F, -1.5F, -1.0F, 0.0F, -Mth.HALF_PI*0.5F, 0.0F)
        );

        /**
         *      Crests
         */
        base.addOrReplaceChild("crestS", CubeListBuilder.create()
                        .texOffs(1, 39)
                        .addBox(-1.5F, -3.0F, -1.5F, 3, 3, 3, new CubeDeformation(0.1F)),
                PartPose.ZERO
        );
        base.addOrReplaceChild("crestM", CubeListBuilder.create()
                        .texOffs(1, 39)
                        .addBox(-1.5F, -3.0F, -1.5F, 3, 3, 3, new CubeDeformation(0.6F)),
                PartPose.ZERO
        );
        base.addOrReplaceChild("crestL", CubeListBuilder.create()
                        .texOffs(0, 38)
                        .addBox(-2.0F, -4.0F, -2.0F, 4, 4, 4, new CubeDeformation(0.5F)),
                PartPose.ZERO
        );

        /**
         *      Combs
         */
        base.addOrReplaceChild("combSingleXs",
                CubeListBuilder.create()
                    .texOffs(0, 0)
                    .addBox(-0.5F, -1.5F, -3.0F, 1, 2, 1, new CubeDeformation(-0.25F))
                    .addBox(-0.5F, -2.0F, -2.5F, 1, 2, 1, new CubeDeformation(-0.25F))
                    .addBox(-0.5F, -1.75F, -2.0F, 1, 2, 1, new CubeDeformation(-0.25F))
                    .addBox(-0.5F, -2.25F, -1.5F, 1, 2, 1, new CubeDeformation(-0.25F))
                    .addBox(-0.5F, -1.75F, -1.0F, 1, 1, 1, new CubeDeformation(-0.25F))
                    .addBox(-0.5F, -2.0F, -0.5F, 1, 1, 1, new CubeDeformation(-0.25F)),
                PartPose.ZERO
            );
        base.addOrReplaceChild("combSingleS",
                    CubeListBuilder.create()
                        .texOffs(0, 0)
                            .texOffs(0, 0)
                            .addBox(-0.5F, -1.75F, -3.0F, 1, 2, 1, new CubeDeformation(-0.125F))
                            .addBox(-0.5F, -2.5F, -2.5F, 1, 2, 1, new CubeDeformation(-0.125F))
                            .addBox(-0.5F, -1.75F, -2.25F, 1, 2, 2, new CubeDeformation(-0.125F))
                            .addBox(-0.5F, -2.5F, -1.25F, 1, 1, 1, new CubeDeformation(-0.125F))
                            .addBox(-0.5F, -1.75F, -0.75F, 1, 1, 1, new CubeDeformation(-0.125F))
                            .addBox(-0.5F, -2.0F, 0.0F, 1, 1, 1, new CubeDeformation(-0.125F)),
                    PartPose.ZERO
            );
        CubeDeformation cubeDeformation = new CubeDeformation(-0.125F, 0.0F, 0.0F);
        base.addOrReplaceChild("combSingleM",
                CubeListBuilder.create()
                        .texOffs(0, 0)
                        .addBox(-0.5F, -1.0F, -3.0F, 1, 1, 1, cubeDeformation)
                        .addBox(-0.5F, -1.5F, -3.0F, 1, 1, 1, cubeDeformation)
                        .addBox(-0.5F, -2.5F, -2.0F, 1, 3, 1, cubeDeformation)
                        .addBox(-0.5F, -2.0F, -1.0F, 1, 2, 1, cubeDeformation)
                        .addBox(-0.5F, -2.5F, 0.0F, 1, 2, 1, cubeDeformation)
                        .addBox(-0.5F, -1.5F, 1.0F, 1, 1, 1, cubeDeformation),
                PartPose.ZERO
            );
        base.addOrReplaceChild("combSingleL",
                CubeListBuilder.create()
                    .texOffs(0, 0)
                    .addBox(-0.5F, -2.5F, -3.5F, 1, 1, 1, cubeDeformation)
                    .addBox(-0.5F, -1.5F, -3.0F, 1, 2, 1, cubeDeformation)
                    .addBox(-0.5F, -3.5F, -2.0F, 1, 4, 1, cubeDeformation)
                    .addBox(-0.5F, -2.5F, -1.0F, 1, 3, 1, cubeDeformation)
                    .addBox(-0.5F, -4.0F, 0.0F, 1, 4, 1, cubeDeformation)
                    .addBox(-0.5F, -2.5F, 1.0F, 1, 2, 1, cubeDeformation)
                    .addBox(-0.5F, -3.5F, 2.0F, 1, 3, 1, cubeDeformation)
                    .addBox(-0.5F, -2.0F, 3.0F, 1, 1, 1, cubeDeformation),
                PartPose.ZERO
            );
        base.addOrReplaceChild("combSingleXl",
                CubeListBuilder.create()
                    .texOffs(0, 0)
                    .addBox(-0.5F, -3.5F, -3.5F, 1, 2, 1, cubeDeformation)
                    .addBox(-0.5F, -2.5F, -3.0F, 1, 3, 1, cubeDeformation)
                    .addBox(-0.5F, -5.0F, -2.0F, 1, 5, 1, cubeDeformation)
                    .addBox(-0.5F, -3.5F, -1.0F, 1, 4, 1, cubeDeformation)
                    .addBox(-0.5F, -4.5F, 0.0F, 1, 5, 1, cubeDeformation)
                    .addBox(-0.5F, -5.5F, 0.5F, 1, 1, 1, cubeDeformation)
                    .addBox(-0.5F, -3.5F, 1.0F, 1, 3, 1, cubeDeformation)
                    .addBox(-0.5F, -4.5F, 2.0F, 1, 4, 1, cubeDeformation)
                    .addBox(-0.5F, -5.0F, 2.5F, 1, 1, 1, cubeDeformation)
                    .addBox(-0.5F, -3.0F, 3.0F, 1, 2, 1, cubeDeformation)
                    .addBox(-0.5F, -3.5F, 4.0F, 1, 2, 1, cubeDeformation),
                PartPose.ZERO
            );
        base.addOrReplaceChild("combRoseTallS",
                CubeListBuilder.create()
                    .texOffs(0, 0)
                    .addBox(-1F, -1.5F, -3.125F, 2, 2, 1, new CubeDeformation(-0.25F))
                    .addBox(-0.5F, -2.25F, -2.625F, 1, 2, 1, new CubeDeformation(-0.1F))
                    .addBox(-0.5F, -2.65F, -2.125F, 1, 1, 1, new CubeDeformation(-0.25F)),
                PartPose.ZERO
            );
        base.addOrReplaceChild("combRoseTallM",
                CubeListBuilder.create()
                    .texOffs(0, 0)
                    .addBox(-0.5F, -1.25F, -3.0F, 1, 1, 1, new CubeDeformation(0.5F, 0.5F, 0.0F))
                    .addBox(-0.5F, -2.25F, -2.0F, 1, 2, 1, new CubeDeformation(0.25F))
                    .addBox(-0.5F, -3.25F, -1.0F, 1, 1, 1),
                PartPose.ZERO
            );
        base.addOrReplaceChild("combRoseTallL",
                CubeListBuilder.create()
                    .texOffs(0, 0)
                        .addBox(-1.0F, -1.25F, -3.25F, 2, 2, 1)
                        .addBox(-0.5F, -2.25F, -2.75F, 1, 2, 2, new CubeDeformation(0.2F))
                        .addBox(-0.5F, -3.25F, -0.75F, 1, 2, 1)
                        .addBox(-0.5F, -4.25F, -0.25F, 1, 1, 1),
                PartPose.ZERO
            );
        base.addOrReplaceChild("combRoseFlatS",
                CubeListBuilder.create()
                        .texOffs(0, 0)
                        .addBox(-0.5F, -1.0F, -2.5F, 1, 1, 1, new CubeDeformation(0.1F))
                        .addBox(-0.5F, -1.5F, -2.0F, 1, 1, 1)
                        .addBox(-0.5F, -1.4F, -1.0F, 1, 1, 1),
                PartPose.ZERO
            );
        base.addOrReplaceChild("combRoseFlatM",
                CubeListBuilder.create()
                        .texOffs(0, 0)
                        .addBox(-1.0F, -1.5F, -3.0F, 2, 2, 1)
                        .addBox(-1.0F, -1.5F, -2.0F, 2, 1, 1)
                        .addBox(-0.5F, -2.0F, -2.0F, 1, 1, 1, new CubeDeformation(0.25F))
                        .addBox(-0.5F, -2.0F, -1.0F, 1, 1, 1),
                PartPose.ZERO
            );
        base.addOrReplaceChild("combRoseFlatL",
                CubeListBuilder.create()
                        .texOffs(0, 0)
                        .addBox(-1.0F, -1.0F, -3.0F, 2, 2, 1)
                        .addBox(-1.0F, -1.75F, -2.5F, 2, 1, 2)
                        .addBox(-0.5F, -1.5F, -0.5F, 1, 1, 1, new CubeDeformation(0.2F)),
                PartPose.ZERO
            );
        base.addOrReplaceChild("combPeaS",
                CubeListBuilder.create()
                        .texOffs(0, 0)
                        .addBox(-0.5F, -1.0F, -3.0F, 1, 1, 1, new CubeDeformation(-0.25F))
                        .addBox(-0.5F, -1.5F, -2.75F, 1, 1, 1, new CubeDeformation(-0.15F))
                        .addBox(-0.5F, -1.5F, -2.4F, 1, 1, 1, new CubeDeformation(-0.25F)),
                PartPose.ZERO
            );
        base.addOrReplaceChild("combPeaM",
                CubeListBuilder.create()
                        .texOffs(0, 0)
                        .addBox(-0.5F, -1.0F, -3.125F, 1, 1, 2, new CubeDeformation(-0.2F))
                        .addBox(-0.5F, -1.5F, -2.75F, 1, 1, 1)
                        .addBox(-0.5F, -2.0F, -2.25F, 1, 2, 1, new CubeDeformation(-0.2F)),
                PartPose.ZERO
            );
        base.addOrReplaceChild("combPeaL",
                    CubeListBuilder.create()
                            .texOffs(0, 0)
                            .addBox(-0.5F, -1.0F, -3.125F, 1, 1, 2, new CubeDeformation(-0.1F))
                            .addBox(-0.5F, -1.5F, -2.75F, 1, 1, 1, new CubeDeformation(0.0F, 0.1F, 0.1F))
                            .addBox(-0.5F, -2.0F, -2.25F, 1, 2, 1, new CubeDeformation(-0.1F)),
                    PartPose.ZERO
            );
        base.addOrReplaceChild("combWalnutS",
                    CubeListBuilder.create()
                            .texOffs(0, 0)
                            .addBox(-0.5F, -0.9F, -3.0F, 1, 1, 1, new CubeDeformation(-0.25F)),
                    PartPose.ZERO
            );
        base.addOrReplaceChild("combWalnutM",
                    CubeListBuilder.create()
                            .texOffs(0, 0)
                            .addBox(-0.5F, -1.1F, -3.0F, 1, 1, 1),
                    PartPose.ZERO
            );
        base.addOrReplaceChild("combWalnutL",
                    CubeListBuilder.create()
                            .texOffs(0, 0)
                            .addBox(-1.0F, -1.5F, -3.25F, 2, 2, 1, new CubeDeformation(-0.125F))
                            .addBox(-0.5F, -1.5F, -2.75F, 1, 1, 1),
                    PartPose.ZERO
            );
        base.addOrReplaceChild("combV",
                    CubeListBuilder.create()
                        .texOffs(0, 0)
                        .addBox(-0.5F, -1.0F, -3.0F, 1, 1, 1, new CubeDeformation(0.0F, 0.1F, 0.0F))
                        .addBox(-0.1F, -1.75F, -2.75F, 1, 1, 1, new CubeDeformation(-0.2F))
                        .addBox(-0.9F, -1.75F, -2.75F, 1, 1, 1, new CubeDeformation(-0.2F))
                        .addBox(0.1F, -2.1F, -2.5F, 1, 1, 1, new CubeDeformation(-0.3F))
                        .addBox(-1.1F, -2.1F, -2.5F, 1, 1, 1, new CubeDeformation(-0.3F)),
                    PartPose.ZERO
            );
        base.addOrReplaceChild("combDuplex", CubeListBuilder.create(), PartPose.rotation(0.0F, 0.0F, 0.0F));

        /**
         *      Necks
         */
        base.addOrReplaceChild("neck", CubeListBuilder.create()
                        .texOffs(0, 15)
                        .addBox(-0.5F, -5.0F, -1.0F, 1, 5, 1)
                        .texOffs(0, 19)
                        .addBox(-0.5F,-1.0F,0.0F, 1, 1, 1),
                PartPose.rotation(Mth.HALF_PI*-0.1F, 0.0F, 0.0F)
        );
        base.addOrReplaceChild("hackle", CubeListBuilder.create()
                        .texOffs(48, 13)
                        .addBox(-2.0F, -3.0F, -2.0F, 4, 6, 4, new CubeDeformation(-0.4F)),
                PartPose.offset(0.0F, -2.0F, 0.0F)
        );

        /**
         *      Bodies
         */
        base.addOrReplaceChild("bodyN", CubeListBuilder.create()
                        .texOffs(40, 52)
                        .addBox(-2.5F, -2.5F, -3.5F, 5, 5, 7, new CubeDeformation(-0.51F)),
                PartPose.offset(0.0F, -3.0F, 0.0F)
        );
        base.addOrReplaceChild("bodyF", CubeListBuilder.create()
                        .texOffs(12, 10)
                        .addBox(-3.0F, -3.0F, -4.0F, 6, 6, 8),
                PartPose.offset(0.0F, -3.0F, 0.0F)
        );

        /**
         *      Wings
         */
        base.addOrReplaceChild("wingLM", CubeListBuilder.create()
                        .texOffs(30, 24)
                        .addBox(0.0F, 0.0F, 0.0F, 1, 4, 6),
                PartPose.ZERO
        );
        base.addOrReplaceChild("wingLS", CubeListBuilder.create()
                        .texOffs(31, 25)
                        .addBox(0.0F, 0.0F, 0.0F, 1, 3, 5),
                PartPose.ZERO
        );
        base.addOrReplaceChild("wingLN", CubeListBuilder.create()
                        .texOffs(47, 58)
                        .addBox(-0.5F, 0.0F, 0.0F, 1, 2, 3),
                PartPose.offsetAndRotation(-0.5F, 0.0F, 0.0F, 0.0F, 0.4F, 0.0F)
        );

        base.addOrReplaceChild("wingRM", CubeListBuilder.create()
                        .texOffs(16, 24)
                        .addBox(-1.0F, 0.0F, 0.0F, 1, 4, 6),
                PartPose.ZERO
        );
        base.addOrReplaceChild("wingRS", CubeListBuilder.create()
                        .texOffs(17, 25)
                        .addBox(-1.0F, 0.0F, 0.0F, 1, 3, 5),
                PartPose.ZERO
        );
        base.addOrReplaceChild("wingRN", CubeListBuilder.create()
                        .texOffs(47, 58)
                        .addBox(-0.5F, 0.0F, 0.0F, 1, 2, 3),
                PartPose.offsetAndRotation(0.5F, 0.0F, 0.0F, 0.0F, -0.4F, 0.0F)
        );

        /**
         *      Legs
         */
        base.addOrReplaceChild("thighL", CubeListBuilder.create()
                        .texOffs(40, 59)
                        .addBox(-1.0F, -1.0F, 0.5F, 2, 2, 3),
                PartPose.offset(-1.5F, -1.0F, 0.0F)
        );
        base.addOrReplaceChild("thighR", CubeListBuilder.create()
                        .texOffs(54, 59)
                        .addBox(-1.0F, -1.0F, 0.5F, 2, 2, 3),
                PartPose.offset(1.5F, -1.0F, 0.0F)
        );

        base.addOrReplaceChild("legLS", CubeListBuilder.create()
                        .texOffs(8, 21)
                        .addBox(-0.5F, 0.5F, 1.0F, 1, 3, 1),
                PartPose.offset(-1.5F, 0.0F, 0.0F)
        );
        base.addOrReplaceChild("legLM", CubeListBuilder.create()
                        .texOffs(8, 21)
                        .addBox(-0.5F, 0.5F, 1.0F, 1, 5, 1),
                PartPose.offset(-1.5F, 0.0F, 0.0F)
        );
        base.addOrReplaceChild("legLL", CubeListBuilder.create()
                        .texOffs(8, 21)
                        .addBox(-0.5F, 0.5F, 1.0F, 1, 2, 1)
                        .addBox(-0.5F, 2.5F, 1.0F, 1, 5, 1),
                PartPose.offset(-1.5F, 0.0F, 0.0F)
        );
        base.addOrReplaceChild("legRS", CubeListBuilder.create()
                        .texOffs(8, 21)
                        .addBox(-0.5F, 0.5F, 1.0F, 1, 3, 1),
                PartPose.offset(1.5F, 0.0F, 0.0F)
        );
        base.addOrReplaceChild("legRM", CubeListBuilder.create()
                        .texOffs(8, 21)
                        .addBox(-0.5F, 0.5F, 1.0F, 1, 5, 1),
                PartPose.offset(1.5F, 0.0F, 0.0F)
        );
        base.addOrReplaceChild("legRL", CubeListBuilder.create()
                        .texOffs(8, 21)
                        .addBox(-0.5F, 0.5F, 1.0F, 1, 2, 1)
                        .addBox(-0.5F, 2.5F, 1.0F, 1, 5, 1),
                PartPose.offset(1.5F, 0.0F, 0.0F)
        );

        base.addOrReplaceChild("footL", CubeListBuilder.create()
                        .texOffs(1, 26)
                        .addBox(-3.0F, 0.0F, 0.0F, 3, 1, 1)
                        .texOffs(3, 26)
                        .addBox(-2.0F, 0.0F, -1.0F, 1, 1, 1),
                PartPose.ZERO
        );
        base.addOrReplaceChild("footR", CubeListBuilder.create()
                        .texOffs(1, 26)
                        .addBox(0.0F, 0.0F, 0.0F, 3, 1, 1)
                        .texOffs(3, 26)
                        .addBox(1.0F, 0.0F, -1.0F, 1, 1, 1),
                PartPose.ZERO
        );

        /**
         *      Leg Feathers
         */
        base.addOrReplaceChild("bloomersL", CubeListBuilder.create()
                        .texOffs(38, 20)
                        .addBox(-1.5F, -1.5F, 0.0F, 3, 3, 4, new CubeDeformation(0.5F)),
                PartPose.ZERO
        );
        base.addOrReplaceChild("bloomersR", CubeListBuilder.create()
                        .texOffs(50, 23)
                        .addBox(-1.5F, -1.5F, 0.0F, 3, 3, 4, new CubeDeformation(0.5F)),
                PartPose.ZERO
        );

        base.addOrReplaceChild("pantsL", CubeListBuilder.create()
                        .texOffs(40, 11)
                        .addBox(-3.5F, 2.901F, 0.5F, 2, 3, 2),
                PartPose.ZERO
        );
        base.addOrReplaceChild("pantsLL", CubeListBuilder.create()
                        .texOffs(40, 11)
                        .addBox(-3.5F, -2.0F, 0.5F, 2, 2, 2),
                PartPose.offset(0.0F, 2.901F, 0.0F)
        );
        base.addOrReplaceChild("pantsR", CubeListBuilder.create()
                        .texOffs(32, 11)
                        .addBox(1.5F, 2.901F, 0.5F, 2, 3, 2),
                PartPose.ZERO
        );
        base.addOrReplaceChild("pantsRL", CubeListBuilder.create()
                        .texOffs(32, 11)
                        .addBox(1.5F, -2.0F, 0.5F, 2, 2, 2),
                PartPose.offset(0.0F, 2.901F, 0.0F)
        );

        base.addOrReplaceChild("bootsL", CubeListBuilder.create()
                        .texOffs(38, 0)
                        .addBox(0.0F, 0.9F, 0.0F, 5, 5, 0),
                PartPose.offsetAndRotation(-2.25F, 0.0F, 4.0F, 0.0F, Mth.HALF_PI, 0.0F)
        );
        base.addOrReplaceChild("bootsR", CubeListBuilder.create()
                        .texOffs(28, 0)
                        .addBox(0.0F, 0.9F, 0.0F, 5, 5, 0),
                PartPose.offsetAndRotation(2.25F, 0.0F, 4.0F, 0.0F, Mth.HALF_PI, 0.0F)
        );

        base.addOrReplaceChild("toeFeathersL", CubeListBuilder.create()
                        .texOffs(4, 12)
                        .addBox(-6.0F, 0.9F, -2.0F, 5, 0, 6),
                PartPose.ZERO
        );
        base.addOrReplaceChild("toeFeathersR", CubeListBuilder.create()
                        .texOffs(6, 24)
                        .addBox(1.0F, 0.9F, -2.0F, 5, 0, 6),
                PartPose.ZERO
        );

        base.addOrReplaceChild("vultureHocksL", CubeListBuilder.create()
                        .texOffs(28, 23)
                        .addBox(-3.5F, 0.0F, 2.5F, 0, 3, 4, new CubeDeformation(0.0F, -0.2F, -0.2F)),
                PartPose.ZERO
        );
        base.addOrReplaceChild("vultureHocksR", CubeListBuilder.create()
                        .texOffs(24, 23)
                        .addBox(3.5F, 0.0F, 2.5F, 0, 3, 4, new CubeDeformation(0.0F, -0.2F, -0.2F)),
                PartPose.ZERO
        );

        /**
         *      Saddles
         */
        base.addOrReplaceChild("cushion", CubeListBuilder.create()
                        .texOffs(28, 0)
                        .addBox(-2.5F, -3.5F, 0.0F, 5, 5, 5, new CubeDeformation(-0.001F)),
                PartPose.ZERO
        );
        base.addOrReplaceChild("tailN", CubeListBuilder.create()
                        .texOffs(46, 58)
                        .addBox(-0.5F, -2.0F, 0.0F, 1,2,1),
                PartPose.ZERO
        );

        /**
         *      Tail Coverts
         */
        base.addOrReplaceChild("tailC", CubeListBuilder.create()
                        .texOffs(48, 0)
                        .addBox(-1.0F, -7.25F, -1.5F, 2, 7, 6),
                PartPose.ZERO
        );

        /**
         *      Tails
         */
        base.addOrReplaceChild("tail", CubeListBuilder.create()
                        .texOffs(24, 14)
                        .addBox(0.0F, -9.5F, -3.0F, 0, 16, 20),
                PartPose.ZERO
        );

        /**
         *      Equipment
         */
        base.addOrReplaceChild("collar", CubeListBuilder.create()
                        .texOffs(0, 54)
                        .addBox(-5.0F, -2.0F, -3.0F, 10,  2, 8)
                        .texOffs(28, 54)
                        .addBox(0.0F, -2.6666F, -5.0F, 0,  4, 4),
                PartPose.offsetAndRotation(0.0F, 0.0F, -0.25F, Mth.HALF_PI * 0.5F, 0.0F, 0.0F)
        );
        base.addOrReplaceChild("collarH", CubeListBuilder.create()
                        .texOffs(30, 52)
                        .addBox(-1.5F, 0.0F, -1.5F, 3, 3, 3, new CubeDeformation(-1.0F)),
                PartPose.offsetAndRotation(0.0F, -1.5F, -3.0F, Mth.HALF_PI * -0.5F, 0.0F, 0.0F)
        );

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    public ModelEnhancedChicken(ModelPart modelPart) {
        super(modelPart);
        ModelPart base = modelPart.getChild("base");

/*        chickenModelParts.forEach( (k,v) -> chickenModelParts.put(k, new WrappedModelPart(k, base)) ); */
/*        for (Map.Entry<String, WrappedModelPart> entry : chickenModelParts.entrySet()) {
            String k = entry.getKey();
            entry.setValue(new WrappedModelPart(k, base));}*/
/*        theEars.pushPopChildren = false;
        combDuplex.pushPopChildren = false;*/

        theChicken = new WrappedModelPart("bChicken", base);
        theBody = new WrappedModelPart("bBody", base);
        theLegLeft = new WrappedModelPart("bLegL", base);
        theLegRight = new WrappedModelPart("bLegR", base);
        theFootLeft = new WrappedModelPart("bFootL", base);
        theFootRight = new WrappedModelPart("bFootR", base);
        theNeck = new WrappedModelPart("bNeck", base);
        theHead = new WrappedModelPart("bHead", base);
        theEars = new WrappedModelPart("bEars", base, false);
        theWingLeft = new WrappedModelPart("bWingL", base);
        theWingRight = new WrappedModelPart("bWingR", base);
        theSaddle = new WrappedModelPart("bSaddle", base);
        theTailCoverts = new WrappedModelPart("bCoverts", base);
        theTail = new WrappedModelPart("bTail", base);
        theComb = new WrappedModelPart("bComb", base);
        theWaddles = new WrappedModelPart("bWaddles", base);
        theCrest = new WrappedModelPart("bCrest", base);
        theBeard = new WrappedModelPart("bBeard", base);


        head = new WrappedModelPart("head", base);
        headFeathers = new WrappedModelPart("headF", base);

        beak = new WrappedModelPart("beak", base);
        jaw = new WrappedModelPart("jaw", base);
        eyes = new WrappedModelPart("eyes", base);

        earTiny = new WrappedModelPart("earT", base);
        earSmall = new WrappedModelPart("earS", base);
        earMedium = new WrappedModelPart("earM", base);
        earLarge = new WrappedModelPart("earL", base);
        earXLarge = new WrappedModelPart("earXl", base);

        hackle = new WrappedModelPart("hackle", base);
        neck = new WrappedModelPart("neck", base);

        combSingleXs = new WrappedModelPart("combSingleXs", base);
        combSingleS = new WrappedModelPart("combSingleS", base);
        combSingleM = new WrappedModelPart("combSingleM", base);
        combSingleL = new WrappedModelPart("combSingleL", base);
        combSingleXl = new WrappedModelPart("combSingleXl", base);
        combRoseTallS = new WrappedModelPart("combRoseTallS", base);
        combRoseTallM = new WrappedModelPart("combRoseTallM", base);
        combRoseTallL = new WrappedModelPart("combRoseTallL", base);
        combRoseFlatS = new WrappedModelPart("combRoseFlatS", base);
        combRoseFlatM = new WrappedModelPart("combRoseFlatM", base);
        combRoseFlatL = new WrappedModelPart("combRoseFlatL", base);
        combPeaS = new WrappedModelPart("combPeaS", base);
        combPeaM = new WrappedModelPart("combPeaM", base);
        combPeaL = new WrappedModelPart("combPeaL", base);
        combWalnutS = new WrappedModelPart("combWalnutS", base);
        combWalnutM = new WrappedModelPart("combWalnutM", base);
        combWalnutL = new WrappedModelPart("combWalnutL", base);
        combV = new WrappedModelPart("combV", base);
        combDuplex = new WrappedModelPart("combDuplex", base, false);

        waddlesSmall = new WrappedModelPart("waddlesS", base);
        waddlesMedium = new WrappedModelPart("waddlesM", base);
        waddlesLarge = new WrappedModelPart("waddlesL", base);
        waddlesPea = new WrappedModelPart("waddlesP", base);
        waddlesBearded = new WrappedModelPart("waddlesB", base);
        crestSmall = new WrappedModelPart("crestS", base);
        crestMedium = new WrappedModelPart("crestM", base);
        crestLarge = new WrappedModelPart("crestL", base);
        earTuftLeft = new WrappedModelPart("earTuftL", base);
        earTuftRight = new WrappedModelPart("earTuftR", base);
        beardNakedNeck = new WrappedModelPart("beardN", base);
        beardLarge = new WrappedModelPart("beardL", base);
        bodyNaked = new WrappedModelPart("bodyN", base);
        bodyFeathers = new WrappedModelPart("bodyF", base);
        wingLeftNaked = new WrappedModelPart("wingLN", base);
        wingRightNaked = new WrappedModelPart("wingRN", base);
        wingLeftSmall = new WrappedModelPart("wingLS", base);
        wingLeftMedium = new WrappedModelPart("wingLM", base);
        wingRightSmall = new WrappedModelPart("wingRS", base);
        wingRightMedium = new WrappedModelPart("wingRM", base);
        thighLeft = new WrappedModelPart("thighL", base);
        thighRight = new WrappedModelPart("thighR", base);
        legLeftShort = new WrappedModelPart("legLS", base);
        legLeftMedium = new WrappedModelPart("legLM", base);
        legLeftLong = new WrappedModelPart("legLL", base);
        legRightShort = new WrappedModelPart("legRS", base);
        legRightMedium = new WrappedModelPart("legRM", base);
        legRightLong = new WrappedModelPart("legRL", base);
        footLeft = new WrappedModelPart("footL", base);
        footRight = new WrappedModelPart("footR", base);
        bloomersLeft = new WrappedModelPart("bloomersL", base);
        bloomersRight = new WrappedModelPart("bloomersR", base);
        pantsLeft = new WrappedModelPart("pantsL", base);
        pantsLeftLong = new WrappedModelPart("pantsLL", base);
        pantsRight = new WrappedModelPart("pantsR", base);
        pantsRightLong = new WrappedModelPart("pantsRL", base);
        bootsLeft = new WrappedModelPart("bootsL", base);
        bootsRight = new WrappedModelPart("bootsR", base);
        toeFeathersLeft = new WrappedModelPart("toeFeathersL", base);
        toeFeathersRight = new WrappedModelPart("toeFeathersR", base);
        vultureHockLeft = new WrappedModelPart("vultureHocksL", base);
        vultureHockRight = new WrappedModelPart("vultureHocksR", base);
        tailNub = new WrappedModelPart("tailN", base);
        cushion = new WrappedModelPart("cushion", base);
        tailCover = new WrappedModelPart("tailC", base);
        tailFeathers = new WrappedModelPart("tail", base);

        theChicken.addChild(theBody);
        theChicken.addChild(theLegLeft);
        theChicken.addChild(theLegRight);
        theBody.addChild(theNeck);
        theBody.addChild(theWingLeft);
        theBody.addChild(theWingRight);
        theBody.addChild(theSaddle);
        theNeck.addChild(theHead);
        theHead.addChild(theComb);
        theHead.addChild(theEars);
        theHead.addChild(theWaddles);
        theHead.addChild(theCrest);
        theHead.addChild(theBeard);
        theLegLeft.addChild(theFootLeft);
        theLegRight.addChild(theFootRight);

        theNeck.addChild(hackle);
        theNeck.addChild(neck);

        theHead.addChild(head);
        theHead.addChild(headFeathers);
        theHead.addChild(beak);
        theHead.addChild(jaw);
        theHead.addChild(eyes);

        theEars.addChild(earTiny);
        theEars.addChild(earSmall);
        theEars.addChild(earMedium);
        theEars.addChild(earLarge);
        theEars.addChild(earXLarge);
        theEars.addChild(earTuftLeft);
        theEars.addChild(earTuftRight);

        theComb.addChild(combSingleXs);
        theComb.addChild(combSingleS);
        theComb.addChild(combSingleM);
        theComb.addChild(combSingleL);
        theComb.addChild(combSingleXl);
        theComb.addChild(combRoseTallS);
        theComb.addChild(combRoseTallM);
        theComb.addChild(combRoseTallL);
        theComb.addChild(combRoseFlatS);
        theComb.addChild(combRoseFlatM);
        theComb.addChild(combRoseFlatL);
        theComb.addChild(combPeaS);
        theComb.addChild(combPeaM);
        theComb.addChild(combPeaL);
        theComb.addChild(combWalnutS);
        theComb.addChild(combWalnutM);
        theComb.addChild(combWalnutL);
        theComb.addChild(combV);
        theComb.addChild(combDuplex);

        for (WrappedModelPart part : theComb.children) {
            if (part != combDuplex) {
                combDuplex.addChild(part);
            }
        }

        theWaddles.addChild(waddlesSmall);
        theWaddles.addChild(waddlesMedium);
        theWaddles.addChild(waddlesLarge);
        theWaddles.addChild(waddlesPea);
        theWaddles.addChild(waddlesBearded);

        theCrest.addChild(crestSmall);
        theCrest.addChild(crestMedium);
        theCrest.addChild(crestLarge);

        theBeard.addChild(beardLarge);
        theBeard.addChild(beardNakedNeck);

        theBody.addChild(bodyNaked);
        theBody.addChild(bodyFeathers);
        theBody.addChild(thighLeft);
        theBody.addChild(thighRight);
        thighLeft.addChild(bloomersLeft);
        thighRight.addChild(bloomersRight);

        theWingLeft.addChild(wingLeftSmall);
        theWingLeft.addChild(wingLeftMedium);
        theWingLeft.addChild(wingLeftNaked);

        theWingRight.addChild(wingRightSmall);
        theWingRight.addChild(wingRightMedium);
        theWingRight.addChild(wingRightNaked);

        theLegLeft.addChild(legLeftShort);
        theLegLeft.addChild(legLeftMedium);
        theLegLeft.addChild(legLeftLong);
        theLegLeft.addChild(pantsLeft);
        pantsLeft.addChild(pantsLeftLong);
        theLegLeft.addChild(bootsLeft);
        theLegLeft.addChild(vultureHockLeft);
        theLegRight.addChild(legRightShort);
        theLegRight.addChild(legRightMedium);
        theLegRight.addChild(legRightLong);
        theLegRight.addChild(pantsRight);
        pantsRight.addChild(pantsRightLong);
        theLegRight.addChild(bootsRight);
        theLegRight.addChild(vultureHockRight);

        theFootLeft.addChild(footLeft);
        theFootLeft.addChild(toeFeathersLeft);
        theFootRight.addChild(footRight);
        theFootRight.addChild(toeFeathersRight);

        theSaddle.addChild(theTailCoverts);
        theSaddle.addChild(cushion);
        theSaddle.addChild(tailNub);

        theTailCoverts.addChild(theTail);
        theTailCoverts.addChild(tailCover);

        theTail.addChild(tailFeathers);

        /**
         *      Equipment
         */
        this.collar = new WrappedModelPart("collar", base);
        this.collarHardware = new WrappedModelPart("collarH", base);
        theNeck.addChild(this.collar);

        this.collar.addChild(this.collarHardware);

        chickenModelParts = getModelParts();
    }

    private void resetCubes() {

        for (WrappedModelPart comb : theComb.children) {
            comb.hide();
        }

        for (WrappedModelPart waddles : theWaddles.children) {
            waddles.hide();
        }

        crestSmall.hide();
        crestMedium.hide();
        crestLarge.hide();

        beardLarge.hide();
        beardNakedNeck.hide();

        wingLeftSmall.hide();
        wingLeftMedium.hide();
        wingLeftNaked.hide();
        wingRightSmall.hide();
        wingRightMedium.hide();
        wingRightNaked.hide();

        for (WrappedModelPart legPart : theLegLeft.children) {
            legPart.hide();
        }
        for (WrappedModelPart legPart : theLegRight.children) {
            legPart.hide();
        }

        pantsLeftLong.hide();
        pantsRightLong.hide();

        toeFeathersLeft.hide();
        toeFeathersRight.hide();

        thighLeft.show();
        thighRight.show();

        theFootLeft.show();
        theFootRight.show();

        for (WrappedModelPart earPart : theEars.children) {
            earPart.hide();
        }
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha)  {
        if (this.chickenModelData != null && this.chickenModelData.getPhenotype() != null) {
            ChickenPhenotype chicken = (ChickenPhenotype) this.chickenModelData.phenotype;

            resetCubes();
            super.renderToBuffer(this.chickenModelData, poseStack, vertexConsumer, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            Map<String, List<Float>> mapOfScale = new HashMap<>();
            float size = this.chickenModelData.size;
            float finalChickenSize = ((3.0F * size * this.chickenModelData.growthAmount) + size) / 4.0F;

            /**
             *      Ears
             */


            if (chickenModelData.earGrowth > 0.0F) {
                List<Float> earScale = new ArrayList<>();
                switch (chicken.earSize) {
                    case 2 -> {
                        earTiny.show();
                        earScale = ModelHelper.createScalings(1.0F, 0.75F * chickenModelData.earGrowth, 0.75F * chickenModelData.earGrowth, 0.0F, -0.03F, -0.01F);
                    }
                    case 3 -> {
                        earTiny.show();
                        earScale = ModelHelper.createScalings(1.0F, 0.85F * chickenModelData.earGrowth, 0.85F * chickenModelData.earGrowth, 0.0F, -0.015F, -0.005F);
                    }
                    case 4 -> {
                        earTiny.show();
                    }
                    case 5 -> {
                        earSmall.show();
                        earScale = ModelHelper.createScalings(1.0F, 0.8F * chickenModelData.earGrowth, 0.8F * chickenModelData.earGrowth, 0.0F, -0.03F, -0.01F);
                    }
                    case 6 -> {
                        earSmall.show();
                    }
                    case 7 -> {
                        earMedium.show();
                        earScale = ModelHelper.createScalings(1.0F, 0.8F * chickenModelData.earGrowth, 0.8F * chickenModelData.earGrowth, 0.0F, -0.03F, -0.01F);
                    }
                    case 8 -> {
                        earMedium.show();
                        earScale = ModelHelper.createScalings(1.0F, 0.9F * chickenModelData.earGrowth, 0.9F * chickenModelData.earGrowth, 0.0F, -0.015F, -0.005F);
                    }
                    case 9 -> {
                        earMedium.show();
                    }
                    case 10 -> {
                        earLarge.show();
                        earScale = ModelHelper.createScalings(1.0F, 0.75F * chickenModelData.earGrowth, chickenModelData.earGrowth, 0.0F, -0.03F, 0.0F);
                    }
                    case 11 -> {
                        earLarge.show();
                        earScale = ModelHelper.createScalings(1.0F, 0.85F * chickenModelData.earGrowth, chickenModelData.earGrowth, 0.0F, -0.015F, 0.0F);
                    }
                    case 12 -> {
                        earLarge.show();
                    }
                    case 13 -> {
                        earXLarge.show();
                        earScale = ModelHelper.createScalings(1.0F, 0.75F * chickenModelData.earGrowth, 0.75F * chickenModelData.earGrowth, 0.0F, -0.03F, -0.01F);
                    }
                    case 14 -> {
                        earXLarge.show();
                        earScale = ModelHelper.createScalings(1.0F, 0.85F * chickenModelData.earGrowth, 0.85F * chickenModelData.earGrowth, 0.0F, -0.015F, -0.005F);
                    }
                    case 15 -> {
                        earXLarge.show();
                    }
                    default -> chickenModelData.earGrowth = -1.0F;
                }
                if (chickenModelData.earGrowth != -1.0F) {
                    mapOfScale.put("bEars", earScale.isEmpty() ? ModelHelper.createScalings(1.0F, chickenModelData.earGrowth, chickenModelData.earGrowth, 0.0F, 0.0F, 0.0F) : earScale);
                }
            }

            /**
             *      Comb
             */
            if (chicken.isCombed() && chickenModelData.growthAmount>0.25F){
                theComb.show();

                if (chicken.waddleSize >= 2) {
                    if (chicken.isBearded() && (!chicken.comb.hasPeaComb())) {
                        waddlesBearded.show();
                    }
                    if (!chicken.isBearded()) {
                        if (chicken.comb.hasPeaComb()) {
                            waddlesPea.show();
                        }
                    }
                }
                if (!chicken.isBearded() && (!chicken.comb.hasPeaComb())) {
                    if (chicken.waddleSize >= 3) {
                        waddlesLarge.show();
                    } else if (chicken.waddleSize >= 1) {
                        waddlesMedium.show();
                    } else {
                        waddlesLarge.show();
                    }
                }

                if (chicken.comb == Comb.SINGLE && (chicken.crestType == Crested.NONE || chicken.combSize >= 3)) {
                    switch (chicken.combSize) {
                        case 0:
                            combSingleXs.show();
                            break;
                        case 1:
                            combSingleS.show();
                            break;
                        case 2:
                            combSingleM.show();
                            break;
                        case 3:
                            combSingleL.show();
                            break;
                        case 4:
                        default:
                            combSingleXl.show();
                            break;

                    }
                } else if (chicken.comb == Comb.ROSE_ONE && chicken.crestType == Crested.NONE) {
                    switch (chicken.combSize) {
                        case 0:
                            combRoseTallS.show();
                            break;
                        case 1:
                        case 2:
                        case 3:
                            combRoseTallM.show();
                            break;
                        case 4:
                        default:
                            combRoseTallL.show();
                            break;
                    }
                } else if (chicken.comb == Comb.ROSE_TWO) {
                    switch (chicken.combSize) {
                        case 0:
                            combRoseFlatS.show();
                            break;
                        case 1:
                        case 2:
                        case 3:
                            combRoseFlatM.show();
                            break;
                        case 4:
                        default:
                            combRoseFlatL.show();
                            break;
                    }
                } else if (chicken.comb == Comb.PEA || (chicken.comb == Comb.SINGLE && chicken.crestType != Crested.NONE)) {
                    switch (chicken.combSize) {
                        case 1:
                        case 2:
                            combPeaS.show();
                            break;
                        case 3:
                            combPeaM.show();
                            break;
                        case 4:
                        default:
                            combPeaL.show();
                            break;
                    }
                } else if (chicken.comb == Comb.WALNUT || ((chicken.comb == Comb.ROSE_ONE || chicken.comb == Comb.ROSE_TWO) && chicken.crestType != Crested.NONE)) {
                    switch (chicken.combSize) {
                        case 1:
                        case 2:
                            combWalnutS.show();
                            break;
                        case 3:
                            combWalnutM.show();
                            break;
                        case 4:
                        default:
                            combWalnutL.show();
                            break;
                    }
                } else if (chicken.comb == Comb.V) {
                    combV.show();
                }

                if (chicken.butterCup) {
                    combDuplex.show();
                }
            }

            /**
             *      Legs
             */
            if (this.chickenModelData.offsets.containsKey("bBodyPos")) {
                if (this.chickenModelData.offsets.get("bBodyPos").y() < 8.0F + (15.5F - chicken.height)) {
                    if (chicken.creeper) {
                        if (chicken.hasLongLegs()) {
                            legLeftMedium.show();
                            legRightMedium.show();
                        } else {
                            legLeftShort.show();
                            legRightShort.show();
                        }
                    } else {
                        if (chicken.hasLongLegs()) {
                            legLeftLong.show();
                            legRightLong.show();
                        } else {
                            legLeftMedium.show();
                            legRightMedium.show();
                        }
                    }
                }
            }

            if (!chicken.rumpless) {
                theSaddle.show();
                theTailCoverts.show(this.chickenModelData.growthAmount>0.25F);
                theTail.show(this.chickenModelData.growthAmount>0.4F);
            } else {
                theSaddle.hide();
            }

            if (chicken.isScaleless) {
                headFeathers.hide();
                bodyFeathers.hide();
                neck.show();
                hackle.hide();
                thighRight.show();
                thighLeft.show();
                wingLeftNaked.show();
                wingRightNaked.show();
                theTailCoverts.hide();
                cushion.hide();
                tailNub.show();
            } else {

                /**
                 *      Head Feathers
                 */
                headFeathers.show();

                /**
                 *      Neck Feathers
                 */
                hackle.show(chicken.nakedNeckType!=NakedNeckType.NAKED_NECK);
                neck.show(chicken.nakedNeckType!=NakedNeckType.NONE);

                /**
                 *      Beard
                 */
                switch (chicken.beard) {
                    case BIG_BEARD -> {
                        beardLarge.show();
                    }
                    case SMALL_BEARD, NN_BEARD -> {
                        beardNakedNeck.show();
                    }
                }

                /**
                 *      EarTufts
                 */
                earTuftLeft.show(chicken.earTufts);
                earTuftRight.show(chicken.earTufts);


                /**
                 *      Crest
                 */
                if ((chicken.crestType == Crested.SMALL_CREST || chicken.crestType == Crested.SMALL_FORWARDCREST) || (chicken.crestType != Crested.NONE && this.chickenModelData.growthAmount > 0.5F)) {
                    crestSmall.show();
                } else if (chicken.crestType == Crested.BIG_FORWARDCREST) {
                    crestMedium.show();
                } else if (chicken.crestType == Crested.BIG_CREST) {
                    crestLarge.show();
                }

                /**
                 *      Wing Feathers
                 */
                if (chicken.wingSize == 2) {
                    wingLeftMedium.show();
                    wingRightMedium.show();
                } else {
                    wingLeftSmall.show();
                    wingRightSmall.show();
                }

                /**
                 *      Leg Feathers
                 */
                bloomersLeft.show();
                bloomersRight.show();

                switch (chicken.footFeatherType) {
                    case BIG_TOEFEATHERS:
                    case TOEFEATHERS:
                        toeFeathersLeft.show();
                        toeFeathersRight.show();
                    case FOOTFEATHERS:
                        bootsLeft.show();
                        bootsRight.show();
                    case LEGFEATHERS:
                        if (!this.chickenModelData.sleeping) {
                            pantsLeft.show();
                            pantsRight.show();
                            if (chicken.longHockFeathers || chicken.hasLongLegs()) {
                                pantsLeftLong.show();
                                pantsRightLong.show();
                            }
                        }
                        if (chicken.isVultureHocked) {
                            vultureHockLeft.show();
                            vultureHockRight.show();
                        }
                        break;
                }


                /**
                 *      Body Feathers
                 */
                bodyFeathers.show();

                /**
                 *      Tail Feathers
                 */

                cushion.show();
                theTailCoverts.show();
                tailNub.hide();

                /**
                 *      feather shape variation
                 */

                float FeatherFluff = chicken.fluffiness;

                if (chickenModelData.isBrooding()) {
                    FeatherFluff += (2.0F-FeatherFluff)*0.75F;
                    mapOfScale.put("bBody", ModelHelper.createScalings(1.1F, 0.8F, 0.95F, 0.0F, 0.0F, 0.0F));
                }

                float scale = 0.62F + (FeatherFluff*0.38F);
                scale = scale + ((1.505F-scale)*(chicken.neckPoof*1.35F));
                if (chickenModelData.isFemale) scale*=0.8F;
                mapOfScale.put("hackle", ModelHelper.createScalings(chickenModelData.isBrooding()?scale+0.15F:scale, 1.0F + (chicken.neckPoof), chickenModelData.isBrooding()?scale+0.15F:scale, 0.0F, 0.0F, 0.0F));
                scale = 0.75F + (FeatherFluff*0.25F);
                mapOfScale.put("bodyF", ModelHelper.createScalings(chickenModelData.isBrooding()?scale+0.15F:scale, 0.0F, 0.0F, 0.0F));
                scale = 0.62F + (chicken.fluffiness*0.38F);
                List<Float> scalings = ModelHelper.createScalings(scale, 0.0F, 0.0F, scale<1.0F?(1.0F-scale)*0.21F:0.0F);
                mapOfScale.put("bloomersL", scalings);
                mapOfScale.put("bloomersR", scalings);
                scale = 0.05F - FeatherFluff*0.05F;
                mapOfScale.put("bWingL", ModelHelper.createScalings(1.0F, -scale, 0.0F, 0.0F));
                mapOfScale.put("bWingR", ModelHelper.createScalings(1.0F, scale, 0.0F, 0.0F));
                scale = 0.4F + (FeatherFluff*0.6F);
                mapOfScale.put("bSaddle", ModelHelper.createScalings(scale, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F));
            }

            /**
             *      shape variation
             */

            if (chicken.meatiness!=1.0F) {
                float scale = 0.65F + (chicken.meatiness*0.35F);
                List<Float> scalings = ModelHelper.createScalings(scale, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F);
                mapOfScale.put("head", scalings);
                mapOfScale.put("headF", scalings);
                mapOfScale.put("eyes", scalings);
                mapOfScale.put("bBeard", scalings);
                if (chickenModelData.earGrowth > 0.0F) {
                    scalings = mapOfScale.getOrDefault("bEars", ModelHelper.createDefaultScalings());
                    scalings.set(0, scalings.get(0) * scale);
                    mapOfScale.put("bEars", scalings);
                }
                scale = 1.0F + (chicken.meatiness*0.75F);
                mapOfScale.put("neck", ModelHelper.createScalings(scale, 1.0F, scale, 0.0F, 0.0F, 0.0F));

                mapOfScale.put("bodyN", ModelHelper.createScalings(0.8F + (chicken.meatiness*0.2F), 1.0F,1.0F, 0.0F, 0.0F, 0.0F));

                if (mapOfScale.containsKey("bodyF")) {
                    scalings = mapOfScale.get("bodyF");
                    mapOfScale.put("bodyF", ModelHelper.createScalings(scalings.get(0) * (0.8F + (chicken.meatiness*0.2F)), scalings.get(1),scalings.get(2), 0.0F, 0.0F /*scalings.get(4)*/, 0.0F));
                } else {
                    mapOfScale.put("bodyF", ModelHelper.createScalings(0.8F + (chicken.meatiness*0.2F), 1.0F,1.0F, 0.0F, 0.0F, 0.0F));
                }

                if (mapOfScale.containsKey("bWingR")) {
                    scale = mapOfScale.get("bWingR").get(3) + (0.025F - chicken.meatiness*0.025F);
                    mapOfScale.put("bWingL", ModelHelper.createScalings(1.0F, -scale, 0.0F, 0.0F));
                    mapOfScale.put("bWingR", ModelHelper.createScalings(1.0F, scale, 0.0F, 0.0F));
                }

                scale = 0.65F + (chicken.meatiness*0.35F);
                scalings = ModelHelper.createScalings(scale, 1.0F + (1.0F-scale)*0.01F, scale, /*(1.0F-scale)*-0.16F*/0.0F, 0.0F, 0.0F);
                mapOfScale.put("legLS", scalings);
                mapOfScale.put("legLM", scalings);
                mapOfScale.put("legLL", scalings);
                mapOfScale.put("legRS", scalings);
                mapOfScale.put("legRM", scalings);
                mapOfScale.put("legRL", scalings);
                scalings = ModelHelper.createScalings(1.0F, scale, 1.0F, 0.0F, 0.0F, 0.0F);
                mapOfScale.put("footL", scalings);
                mapOfScale.put("footR", scalings);
            }

            /**
             *      Child variation
             */

            if (this.chickenModelData.growthAmount != 1.0F) {
                mapOfScale.put("bNeck", ModelHelper.createScalings(1.0F + ((1.0F-this.chickenModelData.growthAmount)*0.3F), 0.0F, (1.0F-this.chickenModelData.growthAmount)*0.1F, 0.0F));
                List<Float> beakScaling = ModelHelper.createScalings(1.0F, 1.0F, 0.7F + (chickenModelData.growthAmount*0.3F), 0.0F, 0.0F, 0.0F);
                mapOfScale.put("beak", beakScaling);
                mapOfScale.put("jaw", beakScaling);
                mapOfScale.put("bWaddles", ModelHelper.createScalings(1.01F, this.chickenModelData.growthAmount, 1.0F, 0.0F, 0.0F, 0.0F));
            }
            mapOfScale.put("collar", ModelHelper.createScalings(chicken.isScaleless||chicken.nakedNeckType==NakedNeckType.NAKED_NECK?0.25F:0.5F, 0.0F, 0.0F, 0.0F));
            mapOfScale.put("collarH", ModelHelper.createScalings(2.0F, 0.0F, 0.0F, 0.0F));

            poseStack.pushPose();
            poseStack.scale(finalChickenSize, finalChickenSize, finalChickenSize);
            poseStack.translate(0.0F, -1.5F + 1.5F / finalChickenSize, 0.0F);

            gaRender(theChicken, mapOfScale, poseStack, vertexConsumer, packedLightIn, packedOverlayIn, red, green, blue, alpha);

            poseStack.popPose();
        }
  }

    /**
     *      Animations
     */
    protected void saveAnimationValues(ChickenModelData data) {
        Map<String, Vector3f> map = data.offsets;
        map.put("bChicken", this.getRotationVector(theChicken));
        map.put("bChickenPos", this.getPosVector(theChicken));
        map.put("bBody", this.getRotationVector(theBody));
        map.put("bBodyPos", this.getPosVector(theBody));
        map.put("bHead", this.getRotationVector(theHead));
        map.put("bHeadPos", this.getPosVector(theHead));
        map.put("bLegL", this.getRotationVector(theLegLeft));
        map.put("bLegR", this.getRotationVector(theLegRight));
        map.put("bLegLPos", this.getPosVector(theLegLeft));
        map.put("bLegRPos", this.getPosVector(theLegRight));
        map.put("bWingL", this.getRotationVector(theWingLeft));
        map.put("bWingLPos", this.getPosVector(theWingLeft));
        map.put("bWingR", this.getRotationVector(theWingRight));
        map.put("bWingRPos", this.getPosVector(theWingRight));
        map.put("bFootL", this.getRotationVector(theFootLeft));
        map.put("bFootR", this.getRotationVector(theFootRight));
        map.put("bFootLPos", this.getPosVector(theFootLeft));
        map.put("bFootRPos", this.getPosVector(theFootRight));
        map.put("bNeck", this.getRotationVector(theNeck));
        map.put("bNeckPos", this.getPosVector(theNeck));
        map.put("bSaddle", this.getRotationVector(theSaddle));
        map.put("bSaddlePos", this.getPosVector(theSaddle));
        map.put("bCoverts", this.getRotationVector(theTailCoverts));
        map.put("bCovertsPos", this.getPosVector(theTailCoverts));
        map.put("bTail", this.getRotationVector(theTail));
        map.put("bTailPos", this.getPosVector(theTail));
        map.put("pants", this.getPosVector(pantsLeft));
        map.put("bComb", this.getRotationVector(theComb));
        map.put("bCombPos", this.getPosVector(theComb));
        map.put("bEars", this.getPosVector(theEars));
        map.put("thighPos", this.getPosVector(thighLeft));
        map.put("beak", this.getRotationVector(beak));
        map.put("jaw", this.getRotationVector(jaw));
    }

    private void readInitialAnimationValues(ChickenModelData data, ChickenPhenotype chicken) {
        Map<String, Vector3f> map = data.offsets;
        if (map.isEmpty()) {
            theChicken.setY(chicken.height);
            theBody.setXRot(chicken.bodyAngle);
            theBody.setY(2.0F + chicken.bodyY);
            theBody.setZ(chicken.bodyZ);
            theNeck.setXRot(-chicken.bodyAngle*chicken.neckAngle);
            theHead.setXRot(chicken.bodyAngle);

            theSaddle.setXRot(-0.8F - (chicken.tailAngle*0.7F));
            theSaddle.setZ(1.0F + (chicken.tailAngle*3.5F));

            theTailCoverts.setXRot(0.5F - (chicken.tailAngle*0.6F));
            theTailCoverts.setY(2.0F - (chicken.tailAngle*2.5F));

            theTail.setXRot(0.6F - (chicken.tailAngle*0.8F));
            theTail.setY(-4.0F - (chicken.tailAngle));


            theWingLeft.setY(chicken.wingPlacement);
            theWingRight.setY(chicken.wingPlacement);

            theWingLeft.setXRot(chicken.wingAngle);
            theWingRight.setXRot(chicken.wingAngle);

            theFootLeft.setY((15.5F-chicken.height) + 7.0F);
            theFootRight.setY(theFootLeft.getY());
            pantsLeft.setY(17.5F-chicken.height);
            if (chicken.butterCup) {
                switch (chicken.comb) {
                    case SINGLE -> {
                        theComb.setY(chicken.combSize<2?-1.7F:-1.5F);
                        theComb.setZRot(Mth.HALF_PI*-0.5F);
                        combDuplex.setZRot(Mth.HALF_PI);
                    }
                    case ROSE_ONE -> {
                        theComb.setY(-1.1F);
                        theComb.setZRot(Mth.HALF_PI*-0.25F);
                        combDuplex.setZRot(Mth.HALF_PI*0.5F);
                    }
                    case ROSE_TWO -> {
                        theComb.setY(chicken.combSize<2?-1.5F:-2.0F);
                        theComb.setZRot(Mth.HALF_PI*-0.25F);
                        combDuplex.setZRot(Mth.HALF_PI*0.5F);
                    }
                    default -> {
                        theComb.setY(-1.0F);
                        theComb.setZRot(Mth.HALF_PI*-0.125F);
                        combDuplex.setZRot(Mth.HALF_PI*0.25F);
                    }
                }
            } else {
                theComb.setY(-1.0F);
                theComb.setZRot(0.0F);
                combDuplex.setZRot(0.0F);
            }

            if (chicken.ear != EarType.NONE) {
                if (chicken.earSize == 13 && chicken.ear == EarType.ROUND) {
                    theEars.setZ(0.2F);
                } else {
                    theEars.setZ(0.0F);
                }
            }
        } else {
            theChicken.setRotation(map.get("bChicken"));
            theChicken.setPos(map.get("bChickenPos"));
            theBody.setRotation(map.get("bBody"));
            theBody.setPos(map.get("bBodyPos"));
            theLegLeft.setRotation(map.get("bLegL"));
            theFootLeft.setRotation(map.get("bFootL"));
            theFootRight.setRotation(map.get("bFootR"));
            theFootLeft.setPos(map.get("bFootLPos"));
            theFootRight.setPos(map.get("bFootRPos"));
            theLegLeft.setPos(map.get("bLegLPos"));
            theLegRight.setRotation(map.get("bLegR"));
            theLegRight.setPos(map.get("bLegRPos"));
            theWingLeft.setRotation(map.get("bWingL"));
            theWingLeft.setPos(map.get("bWingLPos"));
            theWingRight.setRotation(map.get("bWingR"));
            theWingRight.setPos(map.get("bWingRPos"));
            theHead.setRotation(map.get("bHead"));
            theHead.setPos(map.get("bHeadPos"));
            theNeck.setRotation(map.get("bNeck"));
            theNeck.setPos(map.get("bNeckPos"));
            theSaddle.setRotation(map.get("bSaddle"));
            theSaddle.setPos(map.get("bSaddlePos"));
            theTailCoverts.setRotation(map.get("bCoverts"));
            theTailCoverts.setPos(map.get("bCovertsPos"));
            theTail.setRotation(map.get("bTail"));
            theTail.setPos(map.get("bTailPos"));
            pantsLeft.setY(map.get("pants").y());
            theComb.setRotation(map.get("bComb"));
            combDuplex.setZRot(theComb.getZRot()*-2);
            theComb.setPos(map.get("bCombPos"));
            theEars.setPos(map.get("bEars"));
            thighLeft.setPos(map.get("thighPos"));
            beak.setXRot(map.get("beak").x());
            jaw.setXRot(map.get("jaw").x());
        }
        pantsRight.setY(pantsLeft.getY());
        bootsLeft.setY(pantsLeft.getY());
        bootsRight.setY(pantsLeft.getY());
        thighRight.setPos(-thighLeft.getX(),thighLeft.getY(), thighLeft.getZ());
        tailNub.setY((theSaddle.getZ()-1.0F)*0.4F);
    }

    @Override
    public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.chickenModelData = getCreateChickenModelData(entityIn);
        if (this.chickenModelData != null) {
            ChickenPhenotype chicken = this.chickenModelData.getPhenotype();
            readInitialAnimationValues(this.chickenModelData, chicken);
            boolean isMoving = entityIn.getDeltaMovement().horizontalDistanceSqr() > 1.0E-7D || entityIn.xOld != entityIn.getX() || entityIn.zOld != entityIn.getZ();

            if (chickenModelData.lookType <= ageInTicks) {
                if (entityIn.getRandom().nextBoolean()) {
                    chickenModelData.lookType = (int) (ageInTicks) + entityIn.getRandom().nextInt(300) + 60;
                } else {
                    chickenModelData.lookType = (int) (ageInTicks) + entityIn.getRandom().nextInt(60);
                }
            } else {
                chickenModelData.lookType--;
            }

            float height = 24.5F - chicken.height;
            float currentTailAngle = chicken.tailAngle;
            boolean awake = true;
            boolean usingBeak = false;
            boolean usingNeck = false;
            boolean usingRWing = false;
            boolean usingLWing = false;
            boolean usingTail = false;
            boolean usingBody = false;

            if (this.chickenModelData.sleeping && !isMoving) {
                if (this.chickenModelData.sleepDelay == -1) {
                    this.chickenModelData.sleepDelay = (int) ageInTicks + ((entityIn.getRandom().nextInt(10)) * 20) + 10;
                } else if (this.chickenModelData.sleepDelay <= ageInTicks + 50) {
                    usingBody = true;
                    if (this.chickenModelData.sleepDelay <= ageInTicks && sitDownAnimation(height)) {
                        headSleeping();
                        currentTailAngle = 1.0F;
                        this.chickenModelData.sleepDelay = 0;
                        awake = false;
                        usingNeck = true;
                    }
                }
            } else if (this.chickenModelData.sleepDelay != -1) {
                this.chickenModelData.sleepDelay = -1;
            }

            if (chickenModelData.isBrooding()) {
                if (!isMoving) {
                    if (chickenModelData.brooding == 1 && sitDownAnimation(height)) {
                        chickenModelData.brooding = (int) ageInTicks + ThreadLocalRandom.current().nextInt(40) + 20;
                    } else if (chickenModelData.brooding != 1) {
                        broodyAnimation(height, chickenModelData.brooding - ageInTicks);
                        usingBody = true;
                        usingRWing = true;
                        usingLWing = true;
                    }
                }
            } else if (bodyNaked.getX() != 0.0F) {
                bodyFeathers.setX(0.0F);
                bodyNaked.setX(0.0F);
            }

            if (awake) {
                if (this.chickenModelData.isEating != 0) {
                    if (this.chickenModelData.isEating == -1) {
                        this.chickenModelData.isEating = (int) ageInTicks + 140;
                    } else if (this.chickenModelData.isEating < ageInTicks) {
                        this.chickenModelData.isEating = 0;
                    }
                    grazingAnimation(this.chickenModelData.isEating - (int) ageInTicks, chicken.bodyY);
                    usingBody = true;
                    usingNeck = true;
                    } else {
                        if (isMoving) {
                            legsWalking(limbSwing, limbSwingAmount);
                        } else if (theBody.getY() <= 3.0F) {
                            legsDefault();
                        }

                        if (chickenModelData.idleType != -1) {
                            if (chickenModelData.idleTimer < ageInTicks) {
                                if (chickenModelData.idleType == 1) {
                                    chickenModelData.idleType = ThreadLocalRandom.current().nextInt(3) + 2;
                                } else {
                                    chickenModelData.idleType = ThreadLocalRandom.current().nextInt(4) + 1;
                                }
                                if (chickenModelData.idleType == 4 && (chicken.rumpless || chicken.isScaleless)) {
                                    chickenModelData.idleType = 3 - ThreadLocalRandom.current().nextInt(2);
                                }
                                chickenModelData.idleTimer = (int) ageInTicks + (ThreadLocalRandom.current().nextInt(chickenModelData.idleType <= 1 ? 5 : 10) * 10) + 10;
                            } else {
                                usingNeck = true;
                                switch (chickenModelData.idleType) {
                                    case 1 -> {
                                        usingBeak = preenOilGland(ageInTicks);
                                    }
                                    case 2 -> {
                                        usingBeak = preenBelly(ageInTicks);
                                    }
                                    case 3 -> {
                                        if (theNeck.getYRot() != 0.0F) {
                                            if (theNeck.getYRot() > 0.0F) {
                                                usingLWing = true;
                                            } else {
                                                usingRWing = true;
                                            }
                                        }
                                        usingBeak = preenWing(ageInTicks);
                                    }
                                    case 4 -> {
                                        usingBeak = preenTail(ageInTicks);
                                        usingTail = true;
                                    }
                                }
                            }
                        }

                        if (chickenModelData.idleType <= 0 && !usingNeck) {
                            int crowTick = entityIn.crowTick;
                            if (crowTick > 0) {
                                if (crow(crowTick, ageInTicks, chicken.wingAngle, chicken.bodyAngle, chicken.neckAngle)) {
                                    usingBeak = true;
                                    usingNeck = true;
                                    usingBody = true;
                                }
                            } else {
                                if ((netHeadYaw != 0 && headPitch != 0)) {
                                    if (chickenModelData.sleepDelay == -1) {
                                        usingNeck = true;

                                        if (chickenModelData.lookType + ageInTicks > 60) {
                                            headBinocularLookingAnimation(netHeadYaw, headPitch, chicken.bodyAngle, chicken.neckAngle);
                                        } else {
                                            headMonocularLookingAnimation(netHeadYaw, headPitch, chicken.bodyAngle, chicken.neckAngle);
                                        }
                                    }
                                }
                            }
                        }
                    }

                    if (!usingBody && (theBody.getY() != 2.0F + chicken.bodyY || theBody.getXRot() != chicken.bodyAngle)) {
                        standUpAnimation(chicken.bodyAngle, chicken.bodyY, chicken.bodyZ);
                    }
                }

                if (!usingTail && !chicken.isScaleless && !chicken.rumpless) {
                    float health = entityIn.getHealth() / entityIn.getMaxHealth();
                    if (health == 1.0F) {
                        if (this.chickenModelData.tailSwishTimer <= ageInTicks) {
                            this.chickenModelData.tailSwishSide = entityIn.getRandom().nextBoolean();
                            this.chickenModelData.tailSwishTimer = (int) ageInTicks + (entityIn.getRandom().nextInt(100) * 20) + 1000;
                        } else if (this.chickenModelData.tailSwishTimer <= ageInTicks + 40) {
                            wiggleTailAnimation(this.chickenModelData.tailSwishSide, ageInTicks);
                        } else {
                            tailAngleAnimation(currentTailAngle);
                        }
                    } else {
                        tailAngleAnimation(1.5F);
                    }
                    tailDefault();
                }

            if (wingsFlapping(entityIn.getDeltaMovement().horizontalDistanceSqr() < 0.05F && entityIn.isOnGround(), ageInTicks)) {
                usingLWing = true;
                usingRWing = true;
            }

            if (!usingLWing) {
                wingLeftDefault(chicken.wingAngle);
            }
            if (!usingRWing) {
                wingRightDefault(chicken.wingAngle);
            }

            if (!usingBeak) {
                mouth(0.0F);
            }

            if (!usingNeck) {
                headDefault(chicken.bodyAngle, chicken.neckAngle);
            }

            articulate(1.0F - chicken.neckAngle, height, chicken.bodyY+2.0F);

            saveAnimationValues(this.chickenModelData);
        }
    }

    private boolean crow(int crowTimer, float ticks, float wingAngle, float bodyAngle, float neckAngle) {
        theBody.setXRot(this.lerpTo(crowTimer<30?0.01F:0.05F, theBody.getXRot(), crowTimer<30?bodyAngle:Math.max(bodyAngle-0.6F,-1.57079F)));
        theBody.setY((theBody.getXRot() * 2.9F)+2.0F);
        if (crowTimer<20) {
            mouth(0.0F);
        } else if (crowTimer<40) {
            mouth((crowTimer-20F)/20F);
        } else {
            mouth(crowTimer>60?0.0F:1.0F);
        }

        if (!wingsFlapping(crowTimer < 100, ticks*0.8F)) {
            wingsDefault(wingAngle);
            theNeck.lerpYRot(0.0F);
            theHead.lerpYRot(0.0F);
            if (crowTimer < 80) {
                float bodyMod = (-bodyAngle / 1.5F) * 1.5708F;
                float neckMod = neckAngle > 1.0F ? 0.0F : 1.0F - neckAngle;
                if (crowTimer > 20) {
                    float neck = 0.5F;
                    float head = -0.22F;
                    float speed = 0.025F;
                    if (crowTimer < 70) {
                        if (crowTimer > 50) {
                            neck = -0.3F;
                            head = 0.2F;
                            speed = 0.05F;
                        } else {
                            neck = 2.0F;
                            head = -2.0F;
                            speed = 0.01F;
                        }
                    }
                    theNeck.setXRot(this.lerpTo(speed, theNeck.getXRot(), (bodyMod * neckAngle) + neck));
                    theHead.setXRot(this.lerpTo(speed, theHead.getXRot(), (bodyMod * neckMod) + head));
                    theNeck.lerpY(-4.5F);
                } else {
                    theNeck.lerpXRot(bodyMod * neckAngle);
                    theHead.lerpXRot(bodyMod * neckMod);
                    wingsDefault(wingAngle);
                }
            }
        }
        return !(crowTimer>60||crowTimer<20);
    }

    private boolean mouth(float openness) {
        if (jaw.getXRot()==openness*0.4F) return true;
        beak.setXRot(this.lerpTo(beak.getXRot(), 0.08F - (openness*0.08F)));
        jaw.setXRot(this.lerpTo(jaw.getXRot(), openness*0.4F));
        return false;
    }
    private void mouth(float openness, float ticks) {
        if (openness==0.0F) {
            beak.setXRot(0.08F);
            jaw.setXRot(0.0F);
        } else {
            ticks = openness * Mth.HALF_PI * -0.5F * (Mth.cos(ticks * Mth.PI) - 1.0F);
            beak.setXRot((ticks * -0.08F) + 0.08F);
            jaw.setXRot(ticks * 0.4F);
        }
    }

    /**
     *      Wings
     */
    private void wingsDefault(float wingAngle) {
        wingLeftDefault(wingAngle);
        wingRightDefault(wingAngle);
    }

    private void wingLeftDefault(float wingAngle) {
        theWingLeft.lerpZRot(0.0F);
        theWingLeft.lerpYRot(0.0F);
        theWingLeft.lerpXRot(wingAngle);
    }

    private void wingRightDefault(float wingAngle) {
        theWingRight.lerpZRot(0.0F);
        theWingRight.lerpYRot(0.0F);
        theWingRight.lerpXRot(wingAngle);
    }
    public boolean wingsFlapping(boolean stopFlap, float ticks) {
        if (stopFlap && theWingRight.getZRot() <= 0.001F) {
            return false;
        } else {
            float flap = 1.2F*(Mth.cos(ticks) + 1.0F);
            theWingLeft.setZRot(-flap);
            theWingRight.setZRot(flap);
            if (stopFlap) {
                if (theWingLeft.getXRot() < 0.0F) {
                    theWingLeft.setXRot(-flap);
                    theWingRight.setXRot(-flap);
                } else {
                    theWingLeft.setXRot(0.0F);
                    theWingRight.setXRot(0.0F);
                }
            } else {
                if (theWingLeft.getXRot() > (Math.PI * -0.25F)) {
                    theWingLeft.setXRot(-flap);
                    theWingRight.setXRot(-flap);
                } else {
                    theWingLeft.setXRot((float) Math.PI * -0.25F);
                    theWingRight.setXRot((float) Math.PI * -0.25F);
                }
            }
        }

        return true;
    }

    /**
     *      Legs
     *
     *      TODO legs can be articulated better to feel more realistic. Do better pls
     */
    private boolean legsDefault() {
        if (theLegRight.getXRot() == 0.0F && theLegLeft.getXRot() == 0.0F) return true;
        if (Mth.abs(theLegLeft.getXRot())<0.001F) {
            theLegLeft.setXRot(0.0F);
            theLegRight.setXRot(0.0F);
            return true;
        } else {
            theLegLeft.setXRot(this.lerpTo(theLegLeft.getXRot(), 0.0F));
            theLegRight.setXRot(this.lerpTo(theLegRight.getXRot(), 0.0F));
        }
        return false;
    }
    private void legsWalking(float limbSwing, float limbSwingAmount) {
        theLegRight.setXRot(Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount);
        theLegLeft.setXRot(Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount);
    }
    private void legsScratchGround(float ticks) {
        float loop = (float) Math.cos(ticks *0.5F);
        if (loop > 0) {
            if (theLegLeft.getXRot() <= 0.0F) {
                theLegLeft.setXRot(0.0F);
                theLegRight.setXRot(this.lerpTo(0.1F,theLegRight.getXRot(), Mth.HALF_PI));
            } else {
                theLegLeft.setXRot(this.lerpTo(0.1F, theLegLeft.getXRot(), -0.314159F));
            }
        } else {
            if (theLegRight.getXRot() <= 0.0F) {
                theLegRight.setXRot(0.0F);
                theLegLeft.setXRot(this.lerpTo(0.1F, theLegLeft.getXRot(), Mth.HALF_PI));
            } else {
                theLegRight.setXRot(this.lerpTo(0.1F,theLegRight.getXRot(), -0.314159F));
            }
        }
    }

    /**
     *      Head/Neck
     */
    private void headDefault(float bodyAngle, float neckAngle) {
        float bodyMod = -bodyAngle/1.5F;
        float neckMod = 1.0F-neckAngle;

        theNeck.lerpXRot(1.5708F*bodyMod*neckAngle);
        theNeck.lerpYRot(0.0F);
        if (neckMod<0.0F) neckMod = 0.0F;
        theHead.lerpXRot(1.5708F*bodyMod*neckMod);
        theNeck.lerpY(-2.5F);
    }

    /**
     *      fix broken sleeping head
     *      while you are at it fill in the other methods
     */
    private void headSleeping() {
        float h = theNeck.getYRot();
        if (h == 0.0F) {
            h = ThreadLocalRandom.current().nextBoolean() ? 0.0001F : -0.0001F;
        }
        if (h > 0.0F) {
            theNeck.setYRot(this.lerpTo(h, Mth.HALF_PI * 1.85F));
        } else {
            theNeck.setYRot(this.lerpTo(h, Mth.HALF_PI * -1.85F));
        }
        if (Math.abs(theNeck.getYRot()) > Mth.HALF_PI) {
            theNeck.setXRot(this.lerpTo(theNeck.getXRot(), Mth.HALF_PI));
            theNeck.setZ(this.lerpTo(theNeck.getZ(), -3.75F));
        }
        theNeck.lerpY(-6.01F);
    }
    private void headPeck(float ticks) {
        legsDefault();
        theNeck.lerpY(-4.5F);
        float headRot = theNeck.getXRot();
        if (headRot < Mth.HALF_PI*0.75F) {
            theNeck.lerpXRot(Mth.HALF_PI);
        } else {
            float loop = (float) Math.cos(ticks *0.75F);
            if (loop > 0) {
                theNeck.setXRot(this.lerpTo(0.3F, theNeck.getXRot(), Mth.HALF_PI * 0.8F));
            } else {
                theNeck.setXRot(this.lerpTo(0.3F, theNeck.getXRot(), Mth.HALF_PI * 1.15F));
            }
            theHead.setXRot(-(theNeck.getXRot()-1F));
        }
    }
    private void tuckEgg() {

    }

    /**
     * TODO
     *  ask about testing out my map animations on a branch
     */
    private boolean preenWing(float ticks) {
        theNeck.setY(this.lerpTo(theNeck.getY(), -4.01F));
        float neckYRot = theNeck.getYRot();
        if (neckYRot == 0.0F) {
            neckYRot = ThreadLocalRandom.current().nextBoolean() ? 0.0001F : -0.0001F;
        }
        if (neckYRot > 0.0F) {
            theNeck.setYRot(this.lerpTo(neckYRot, Mth.HALF_PI * 1.4F));
        } else {
            theNeck.setYRot(this.lerpTo(neckYRot, Mth.HALF_PI * -1.4F));
        }
        theNeck.setXRot(this.lerpTo(theNeck.getXRot(), Mth.HALF_PI*0.6F));
        theNeck.setZ(this.lerpTo(theNeck.getZ(), -3.75F));
        theHead.setXRot(this.lerpTo(theHead.getXRot(), Mth.HALF_PI*0.3F));
        if (Math.abs(neckYRot) > Mth.HALF_PI) {
            if (neckYRot > 0.0F) {
                theWingRight.setYRot(this.lerpTo(theWingRight.getYRot(), -0.5F));
            } else {
                theWingLeft.setYRot(this.lerpTo(theWingLeft.getYRot(), 0.5F));
            }
            mouth(0.25F, ticks*0.5F);
            float loop = (float) Math.cos(ticks * 0.75F);
            if (loop > 0) {
                theHead.setYRot(this.lerpTo(0.3F, theHead.getYRot(), Mth.HALF_PI * (neckYRot>0?0.3F:-0.3F)));
            } else {
                theHead.setYRot(this.lerpTo(0.3F, theHead.getYRot(), Mth.HALF_PI * (neckYRot>0?0.4F:-0.4F)));
            }
            return true;
        }
        return false;
    }
    private boolean preenBelly(float ticks) {
        theNeck.setY(this.lerpTo(theNeck.getY(), -6.01F));
        theNeck.setYRot(this.lerpTo(theNeck.getYRot(), 0.0F));
        theNeck.setXRot(this.lerpTo(theNeck.getXRot(), Mth.HALF_PI*1.7F));
        theHead.setYRot(this.lerpTo(theHead.getYRot(), 0.0F));
        theHead.setXRot(this.lerpTo(theHead.getXRot(), 0.3F));
        if (Math.abs(theNeck.getXRot()) > Mth.HALF_PI*1.5F) {
            mouth(0.25F, ticks*0.5F);
            float loop = (float) Math.cos(ticks * 0.75F);
            if (loop > 0) {
                theHead.setYRot(this.lerpTo(0.3F, theHead.getYRot(), Mth.HALF_PI * 0.15F));
            } else {
                theHead.setYRot(this.lerpTo(0.3F, theHead.getYRot(), Mth.HALF_PI * -0.15F));
            }
            return true;
        }
        return false;
    }
    private boolean preenTail(float ticks) {
        theNeck.setY(this.lerpTo(theNeck.getY(), -5.01F));
        float h = theNeck.getYRot();
        if (h == 0.0F) {
            h = ThreadLocalRandom.current().nextBoolean() ? 0.0001F : -0.0001F;
        }
        if (h > 0.0F) {
            theNeck.setYRot(this.lerpTo(h, Mth.HALF_PI * 1.75F));
        } else {
            theNeck.setYRot(this.lerpTo(h, Mth.HALF_PI * -1.75F));
        }
        if (Math.abs(theNeck.getYRot()) > (Mth.HALF_PI)*0.75F) {
            theTail.setYRot(this.lerpTo(theTail.getYRot(), h>0.0F?-0.6F:0.6F));
            theTailCoverts.setZRot(this.lerpTo(theTailCoverts.getZRot(), h>0.0F?-0.4F:0.4F));
            theSaddle.setYRot(this.lerpTo(theSaddle.getYRot(), h>0.0F?-1.0F:1.0F));
            theNeck.setXRot(this.lerpTo(theNeck.getXRot(), Mth.HALF_PI*0.75F));
            theNeck.setZ(this.lerpTo(theNeck.getZ(), -3.75F));
            theHead.setXRot(this.lerpTo(theHead.getXRot(), -Mth.HALF_PI*0.9F));
            if (Math.abs(theNeck.getYRot()) > Mth.HALF_PI) {
                mouth(0.25F, ticks*0.5F);
                float loop = (float) Math.cos(ticks * 0.75F);
                if (loop > 0) {
                    theHead.setYRot(this.lerpTo(0.3F, theHead.getYRot(), Mth.HALF_PI * (h>0?0.3F:-0.3F)));
                } else {
                    theHead.setYRot(this.lerpTo(0.3F, theHead.getYRot(), Mth.HALF_PI * (h>0?0.55F:-0.55F)));
                }
                return true;
            }
        }
        return false;
    }

    private boolean preenOilGland(float ticks) {
        theNeck.setY(this.lerpTo(theNeck.getY(), -6.01F));
        float h = theNeck.getYRot();
        if (h == 0.0F) {
            h = ThreadLocalRandom.current().nextBoolean() ? 0.0001F : -0.0001F;
        }
        if (h > 0.0F) {
            theNeck.setYRot(this.lerpTo(h, Mth.HALF_PI * 1.75F));
        } else {
            theNeck.setYRot(this.lerpTo(h, Mth.HALF_PI * -1.75F));
        }
        if (Math.abs(theNeck.getYRot()) > (Mth.HALF_PI)*0.75F) {
            //TODO saddle should be lifted with the rest of the tail staying in place.
//            this.theSaddle.setXRot(Mth.HALF_PI*0.1F);
//            this.theSaddle.setZ(1.0F);
//            this.theTailCoverts.setXRot(-Mth.HALF_PI*1.3F);
//            this.theTailCoverts.setY(2.5F);
            theNeck.setXRot(this.lerpTo(theNeck.getXRot(), Mth.HALF_PI*0.75F));
            theNeck.setZ(this.lerpTo(theNeck.getZ(), -3.75F));
            theHead.setXRot(this.lerpTo(theHead.getXRot(), 0.0F));
            if (Math.abs(theNeck.getYRot()) > Mth.HALF_PI) {
                mouth(0.25F, ticks*0.5F);
                float loop = (float) Math.cos(ticks * 0.75F);
                if (loop > 0) {
                    theHead.setYRot(this.lerpTo(0.3F, theHead.getYRot(), Mth.HALF_PI * (h>0?0.3F:-0.3F)));
                } else {
                    theHead.setYRot(this.lerpTo(0.3F, theHead.getYRot(), Mth.HALF_PI * (h>0?0.55F:-0.55F)));
                }
                return true;
            }
        }
        return false;
    }

    private void neckPosturing() {
        //the trying to look scary one
    }
    private void headBinocularLookingAnimation(float netHeadYaw, float headPitch, float bodyAngle, float neckAngle) {
        float xRot = (headPitch * 0.017453292F)*0.5F;
        float yRot = (netHeadYaw * 0.017453292F)*0.5F;
        float bodyMod = -bodyAngle/1.5F;
        float neckMod = 1.0F-neckAngle;

        theNeck.setXRot(this.lerpTo(0.05F, theNeck.getXRot(), (neckAngle*xRot) + (1.5708F*bodyMod*neckAngle)));
        if (neckMod<0.0F) neckMod = 0.0F;
        theHead.setXRot(this.lerpTo(0.05F, theHead.getXRot(), (xRot+(xRot*neckMod)) + (1.5708F*bodyMod*(neckMod))));

        bodyMod = theNeck.getXRot()/1.5708F;

        theNeck.setYRot(this.lerpTo(0.05F, theNeck.getYRot(), yRot*(1.0F-bodyMod)));
        theNeck.setZRot(this.lerpTo(0.05F, theNeck.getZRot(), yRot*bodyMod));

        bodyMod = theHead.getXRot()/1.5708F;
        theHead.setYRot(this.lerpTo(0.05F, theHead.getYRot(), yRot*(1.0F-bodyMod)));
        theHead.setZRot(this.lerpTo(0.05F, theHead.getZRot(), yRot*bodyMod*bodyMod));
        theNeck.setY(this.lerpTo(theNeck.getY(), -2.5F));
    }

    private void headMonocularLookingAnimation(float netHeadYaw, float headPitch, float bodyAngle, float neckAngle) {
        float xRot = (headPitch * 0.017453292F);
        float yRot = (netHeadYaw * 0.017453292F);
        float bodyMod = -bodyAngle/1.5F;
        float neckMod;

        theNeck.setXRot(this.lerpTo(0.05F, theNeck.getXRot(), (-neckAngle*xRot) + (1.5708F*bodyMod*neckAngle)));
        bodyMod = theNeck.getXRot()/1.5708F;
        theNeck.setYRot(this.lerpTo(0.05F, theNeck.getYRot(),yRot*(1.0F-bodyMod)));
        theNeck.setZRot(this.lerpTo(0.05F, theNeck.getZRot(),yRot*bodyMod));

        neckMod = Math.abs(theNeck.getYRot()/1.5708F)-1.5708F;
        theHead.setZRot(this.lerpTo(0.05F, theHead.getZRot(),(yRot>0.0F?0.5F:-0.5F)*neckMod));
        theHead.setXRot(-theNeck.getXRot());
        theNeck.setY(this.lerpTo(theNeck.getY(), -2.5F));
    }

    /**
     *      Body
     */
    private boolean grazingAnimation(float ticks, float bodyY) {
        theNeck.setYRot(this.lerpTo(0.45F, theNeck.getYRot(), 0.0F));
        theHead.setYRot(this.lerpTo(0.45F, theHead.getYRot(), 0.0F));
        if (ticks < 50) {
            theBody.lerpXRot(0.5F);
            headPeck(ticks);
            legsDefault();
        } else if (ticks < 100) {
            bodyScratchGround(ticks, bodyY);
        } else {
            legsDefault();
            return false;
        }
        return true;
    }



    private void bodyScratchGround(float ticks, float bodyY) {
        theHead.setZRot(this.lerpTo(theHead.getZRot(), 0.0F));
        theBody.setXRot(this.lerpTo(theBody.getXRot(), 0.0F));
        theBody.setY(2.0F + (theBody.getXRot()*0.64F* bodyY));
        theNeck.setXRot(this.lerpTo(0.3F, theNeck.getXRot(), 0.0F));
        legsScratchGround(ticks);
    }


    private boolean standUpAnimation(float bodyAngle, float bodyY, float bodyZ) {
        if (theBody.getY() == (bodyY+=2.0F) && theBody.getXRot() == bodyAngle) return true;
        theBody.lerpY(bodyY);
        theBody.lerpXRot(bodyAngle);
        return false;
    }

    private boolean sitDownAnimation(float height) {
        return theBody.lerpY(height) && theBody.lerpXRot(0.0F);
    }

    private void sittingDown(float height) {
        theBody.setY(height+3.0F);
        theBody.setXRot(0.0F);
    }

    private void broodyAnimation(float height, float brooding) {
        sitDownAnimation(height);
        float shuffle = 1.0F;
        if (brooding>0) {
            shuffle = Mth.sin(brooding*1.1F);
            theBody.setX(shuffle*0.5F);
            theNeck.setX(theBody.getX()*-0.5F);
            theHead.setX(theBody.getX()*-0.5F);
        } else {
            theBody.setX(0.0F);
            theNeck.setX(0.0F);
            theHead.setX(0.0F);
        }

        if (shuffle==1.0F) {
            theWingLeft.setZRot(this.lerpTo(theWingLeft.getZRot(), -1.0F));
            theWingRight.setZRot(this.lerpTo(theWingRight.getZRot(), 1.0F));
        } else {
            shuffle = Mth.sin(brooding*1.65F) + 1.0F;
            shuffle += 1.0F;
            theWingLeft.setZRot(shuffle);
            theWingRight.setZRot(shuffle);
        }
        theWingLeft.setXRot(this.lerpTo(theWingLeft.getXRot(), shuffle*0.5F));
        theWingRight.setXRot(this.lerpTo(theWingRight.getXRot(), shuffle*0.5F));
    }

    private void sandBathingAnimation(float height, float bathing) {
        float shuffle = 1.0F;
        if (bathing>0) {
            shuffle = Mth.sin(bathing*1.1F);
            sitDownAnimation(height+shuffle);
        } else {
            sittingDown(height);
        }

        if (shuffle==1.0F) {
            theWingLeft.setZRot(this.lerpTo(theWingLeft.getZRot(), -1.0F));
            theWingRight.setZRot(this.lerpTo(theWingRight.getZRot(), 1.0F));
        } else {
            shuffle = Mth.sin(bathing*1.65F) + 1.0F;
            shuffle += 1.0F;
            theWingLeft.setZRot(shuffle);
            theWingRight.setZRot(shuffle);
        }
        theWingLeft.setXRot(this.lerpTo(theWingLeft.getXRot(), shuffle*0.5F));
        theWingRight.setXRot(this.lerpTo(theWingRight.getXRot(), shuffle*0.5F));
    }

    private void tailDefault() {
        theTail.lerpYRot(0.0F);
        theTailCoverts.lerpZRot(0.0F);
        theSaddle.lerpYRot(0.0F);
        theSaddle.lerpZRot(0.0F);
    }
    private void wiggleTailAnimation(boolean side, float ticks) {
        float loop = (side ? (float)Math.cos(ticks) : -(float)Math.cos(ticks));
        tailAngleAnimation(1.0F);
        theSaddle.setZRot(Mth.HALF_PI * 0.2F * loop);
        theSaddle.setYRot(Mth.HALF_PI * 0.6F * loop);
    }

    private boolean tailAngleAnimation(float angle) {
        float val1 = -0.8F - (angle*0.7F);
        float val2 = 1.0F + (angle*3.5F);
        if (theSaddle.getXRot()==val1 && theSaddle.getZ()==val2 && theSaddle.getYRot()==0.0F) return true;
        theSaddle.setXRot(this.lerpTo(theSaddle.getXRot(),val1));
        theSaddle.setZ(this.lerpTo(theSaddle.getZ(),val2));
        return false;
    }

    private void articulate(float neckAngle, float height, float bodyY) {
        if (theNeck.getY() != -2.5F || neck.getYRot() != 0.0F) {
            if (theNeck.getXRot()<0.0F) neck.setYRot(-theNeck.getYRot());
        }

        if (theBody.getXRot()<0.0F) {
            theHead.setZ(0.3F + (((theBody.getXRot()*neckAngle)/1.5708F)*1.1F));
        } else {
            theHead.setZ(0.3F);
        }

        if (theBody.getZ()!=theBody.getXRot()) {
            theBody.setZ(theBody.getXRot());
        }

        thighLeft.setXRot(theLegLeft.getXRot()*0.4F);
        thighRight.setXRot(theLegRight.getXRot()*0.4F);
        if (theBody.getY()<=bodyY) {
            if (thighLeft.getY()!=-1.0F) thighLeft.setY(this.lerpTo(thighLeft.getY(), -1.0F));
        } else {
            thighLeft.setY(-1.0F-((theBody.getY()-bodyY)*0.25F));
        }

        if (thighRight.getY()!=thighLeft.getY()) {
            thighRight.setY(thighLeft.getY());
        }
    }

    private ChickenModelData getCreateChickenModelData(T enhancedChicken) {
        return (ChickenModelData) getCreateAnimalModelData(enhancedChicken);
    }

    @Override
    protected void setInitialModelData(T enhancedChicken) {
        ChickenModelData chickenModelData = new ChickenModelData();
        chickenModelData.isFemale = enhancedChicken.getOrSetIsFemale();
        chickenModelData.brooding = enhancedChicken.isBrooding() ? 1 : 0;
        chickenModelData.earGrowth = getEarGrowth(enhancedChicken.getEnhancedAnimalAge());
        setBaseInitialModelData(chickenModelData, enhancedChicken);
    }

    @Override
    protected void additionalUpdateModelDataInfo(AnimalModelData animalModelData, T enhancedAnimal) {
        if (enhancedAnimal.isBrooding()) {
            if (((ChickenModelData) animalModelData).brooding==0) ((ChickenModelData) animalModelData).brooding = 1;
        } else {
            ((ChickenModelData) animalModelData).brooding = 0;
        }
        if (((ChickenModelData) animalModelData).earGrowth != -1.0F) {
            ((ChickenModelData) animalModelData).earGrowth = getEarGrowth(enhancedAnimal.getEnhancedAnimalAge());
        }
        if (((ChickenModelData) animalModelData).idleType == -1) {
            if (enhancedAnimal.preening) ((ChickenModelData) animalModelData).idleType = 0;
        } else if (!enhancedAnimal.preening) {
            ((ChickenModelData) animalModelData).idleType = -1;
        }
    }

    @Override
    protected ChickenPhenotype createPhenotype(T enhancedAnimal) {
        return new ChickenPhenotype(enhancedAnimal.getSharedGenes(), enhancedAnimal.getOrSetIsFemale());
    }

    protected float getEarGrowth(float age) {
        if (age>(0.5F*earMaxGrowth)) {
            return age > 4.5F*earMaxGrowth ? 1.0F : (age-(0.5F*earMaxGrowth)) / (4.0F*earMaxGrowth);
        }
        return 0.0F;
    }
}
