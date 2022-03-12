package mokiyoki.enhancedanimals.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import mokiyoki.enhancedanimals.entity.EnhancedChicken;
import mokiyoki.enhancedanimals.model.util.WrappedModelPart;
import mokiyoki.enhancedanimals.util.Genes;
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

/**
 * Created by saemon on 8/09/2018.
 */
@OnlyIn(Dist.CLIENT)
public class ModelEnhancedChicken<T extends EnhancedChicken> extends EnhancedAnimalModel<T> {
//    private int clearCacheTimer = 0;
//
//    private float headRotationAngleX;
//    private boolean nesting = false;
//    private float wingAngle = 0; //[between 0 - -1.5]

    //the below is all chicken parts
    private WrappedModelPart theChicken;
    private WrappedModelPart theHead;
    private WrappedModelPart theComb;
    private WrappedModelPart theWaddles;
    private WrappedModelPart theCrest;
    private WrappedModelPart theBeard;
    private WrappedModelPart theNeck;
    private WrappedModelPart theBody;
    private WrappedModelPart theLeftWing;
    private WrappedModelPart theRightWing;
    private WrappedModelPart theLeftLeg;
    private WrappedModelPart theRightLeg;
    private WrappedModelPart theSaddle;
    private WrappedModelPart theTail;

    private WrappedModelPart head;
    private WrappedModelPart headNakedNeck;

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

//    private WrappedModelPart neck;
//    private WrappedModelPart hackle;
//    private WrappedModelPart bowtie;

    private WrappedModelPart bodySmall;
    private WrappedModelPart bodyMedium;
    private WrappedModelPart bodyLarge;

    private WrappedModelPart wingLeftSmall;
    private WrappedModelPart wingRightSmall;
    private WrappedModelPart wingLeftMedium;
    private WrappedModelPart wingRightMedium;

    private WrappedModelPart legLeftShort;
    private WrappedModelPart legLeftMedium;
    private WrappedModelPart legLeftLong;
    private WrappedModelPart legRightShort;
    private WrappedModelPart legRightMedium;
    private WrappedModelPart legRightLong;

    private WrappedModelPart cushion;
    private WrappedModelPart saddle;
    private WrappedModelPart shortTailCover;
    private WrappedModelPart shortTail;
    private WrappedModelPart mediumTailCover;
    private WrappedModelPart mediumTail;
    private WrappedModelPart longTailCover;
    private WrappedModelPart longTail;

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition base = meshdefinition.getRoot().addOrReplaceChild("base", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition bChicken = base.addOrReplaceChild("bChicken", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition bBody = bChicken.addOrReplaceChild("bBody", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
//        PartDefinition bNeck = bBody.addOrReplaceChild("bNeck", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition bHead = bBody.addOrReplaceChild("bHead", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, -5.0F));
        PartDefinition bComb = bHead.addOrReplaceChild("bComb", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition bWaddles = bHead.addOrReplaceChild("bWaddles", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition bCrest = bHead.addOrReplaceChild("bCrest", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition bBeard = bHead.addOrReplaceChild("bBeard", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition bLeftWing = bBody.addOrReplaceChild("bLeftWing", CubeListBuilder.create(), PartPose.offset(3.0F, 0.0F, 0.0F));
        PartDefinition bRightWing = bBody.addOrReplaceChild("bRightWing", CubeListBuilder.create(), PartPose.offset(-3.0F, 0.0F, 0.0F));
        PartDefinition bLeftLeg = bChicken.addOrReplaceChild("bLeftLeg", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition bRightLeg = bChicken.addOrReplaceChild("bRightLeg", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition bSaddle = bBody.addOrReplaceChild("bSaddle", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition bTail = bSaddle.addOrReplaceChild("bTail", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        /**
         *      - Heads and HeadParts
         */
        bHead.addOrReplaceChild("head",
                CubeListBuilder.create()
                        .texOffs(12, 0)
                        .addBox(-2.0F, -6.0F, -2.0F, 4, 6, 3),
                PartPose.ZERO
        );
        bHead.addOrReplaceChild("headNN",
                CubeListBuilder.create()
                        .texOffs(12, 0)
                        .addBox(-2.0F, -6.0F, -2.0F, 4, 4, 3)
                        .texOffs(0, 6)
                        .addBox(-1F, -2.0F, -1F, 2, 3, 2),
                PartPose.ZERO
            );
        bHead.addOrReplaceChild("beak",
                CubeListBuilder.create()
                        .texOffs(0, 18)
                        .addBox(-0.5F, 0.0F, -1.5F, 1, 1, 2)
                        .texOffs(0, 19)
                        .addBox(-0.5F, 0.25F, -1.5F, 1, 1, 2, new CubeDeformation(-0.01F)),
                PartPose.offset(0.0F,  -4.0F, -2.0F)
        );
        bHead.addOrReplaceChild("ears",
                CubeListBuilder.create()
                        .texOffs(1, 52)
                        .addBox(-2.2F, -6.0F, -2.2F, 1, 6, 3, false)
                        .addBox(2.2F, -6.0F, -2.4F, 1, 6, 3, true),
                PartPose.ZERO
        );
        bHead.addOrReplaceChild("eyes",
                CubeListBuilder.create()
                        .texOffs(23, 0)
                        .addBox(1.0F, -5.0F, -2.0F, 1, 1, 1, new CubeDeformation(0.01F))
                        .texOffs(11, 0)
                        .addBox(1.0F, -5.0F, -2.0F, 1, 1, 1, new CubeDeformation(0.01F)),
                PartPose.ZERO
        );
        bHead.addOrReplaceChild("earTuft",
                CubeListBuilder.create()
                        .texOffs(12, 47)
                        .addBox(-2.0F, 0.0F, 0.0F, 2, 1, 3F),
                PartPose.ZERO
        );

            bWaddles.addOrReplaceChild("waddlesS",
                    CubeListBuilder.create()
                        .texOffs(21, 10)
                        .addBox(-0.505F, -1.0F, -2.0F, 0, 1, 2)
                        .addBox(0.505F, -1.0F, -2.0F, 0, 1, 2),
                    PartPose.ZERO
            );
        bWaddles.addOrReplaceChild("waddlesM",
                    CubeListBuilder.create()
                            .texOffs(21, 10)
                            .addBox(-0.505F, -2.0F, -2.0F, 0, 2, 2)
                            .addBox(0.505F, -2.0F, -2.0F, 0, 2, 2),
                    PartPose.ZERO
            );
        bWaddles.addOrReplaceChild("waddlesL",
                    CubeListBuilder.create()
                            .texOffs(21, 10)
                            .addBox(-0.505F, -3.0F, -2.0F, 0, 3, 2)
                            .addBox(0.505F, -3.0F, -2.0F, 0, 3, 2),
                    PartPose.ZERO
            );
        bWaddles.addOrReplaceChild("waddlesP",
                    CubeListBuilder.create()
                            .texOffs(21, 10)
                            .addBox(0.0F, -2.0F, -2.0F, 0, 2, 2),
                    PartPose.ZERO
            );
        bWaddles.addOrReplaceChild("waddlesB",
                    CubeListBuilder.create()
                            .texOffs(14, 10)
                            .addBox(-1.5F, -1.0F, -1.0F, 3, 1, 1),
                    PartPose.ZERO
            );

            bBeard.addOrReplaceChild("beardL",
                CubeListBuilder.create()
                    .texOffs(2, 29)
                    .addBox(-3.0F, -4.0F, -2.0F, 1, 2, 2)
                    .addBox(-2.5F, -3.0F, -2.75F, 2, 2, 2)
                    .texOffs(3, 30)
                    .addBox(-1.0F, -2.25F, -2.9F, 2, 2, 2)
                    .addBox(-0.5F, -2F, -3.75F, 1, 1, 1)
                    .texOffs(2, 29)
                    .addBox(2F, -4F, -2F, 1, 2, 2)
                    .addBox(0.5F, -3F, -2.75F, 2, 2, 2),
                PartPose.ZERO
            );
        bBeard.addOrReplaceChild("beardNN", CubeListBuilder.create()
                        .texOffs(2, 29)
                        .addBox(-3F, -4F, -2F, 1, 2, 2)
                        .addBox(-2F, -3F, -2.75F, 2, 2, 2)
                        .texOffs(3, 30)
                        .addBox(-0.5F, -2F, -3.5F, 1, 1, 1)
                        .texOffs(2, 29)
                        .addBox(2F, -4F, -2F, 1, 2, 2)
                        .addBox(0F, -3F, -2.75F, 2, 2, 2),
                PartPose.ZERO
        );
//        this.earTuftL = new EnhancedRendererModelNew(this, 12, 47, "LeftEarTuft");
//        this.earTuftL.addBox(-2.0F, 0.0F, 0.0F, 2, 1, 3F);
//        this.earTuftL.setPos(-1.5F, -4.5F, -1.0F);
//
//        this.earTuftR = new EnhancedRendererModelNew(this, 12, 47, "RightEarTuft");
//        this.earTuftR.addBox(0.0F, 0.0F, 0.0F, 2, 1, 3F);
//        this.earTuftR.setPos(1.5F, -4.5F, -1.0F);
//        this.earR.mirror = true;

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
                        .addBox(-1.5F, -3.0F, -1.5F, 3, 3, 3, new CubeDeformation(0.4F)),
                PartPose.ZERO
        );
        bCrest.addOrReplaceChild("crestLF", CubeListBuilder.create()
                        .texOffs(1, 42)
                        .addBox(-2.0F, -4.0F, -2.0F, 4, 4, 4, new CubeDeformation(0.4F)),
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
                    .addBox(-0.5F, -3.5F, -1.0F, 1, 3, 1)
                    .addBox(-0.5F, -4.5F, -2.0F, 1, 4, 1)
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
                    .addBox(-0.5F, -1.0F, -3F, 1, 1, 1, new CubeDeformation(0.5F))
                    .addBox(-0.5F, -2.0F, -2F, 1, 1, 1, new CubeDeformation(0.25F))
                    .addBox(-0.5F, -3.0F, -1F, 1, 1, 1),
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
                            .addBox(-1F, -2.0F, -2.5F, 2, 2, 1, new CubeDeformation(-0.125F))
                            .addBox(-0.5F, -1.5F, -2.0F, 1, 1, 1),
                    PartPose.ZERO
            );
        bComb.addOrReplaceChild("combV",
                    CubeListBuilder.create()
                        .texOffs(0, 0)
                        .addBox(-0.5F, -1.5F, -2.5F, 1, 1, 1)
                        .addBox(0F, -2.0F, -2.25F, 1, 1, 1, new CubeDeformation(-0.2F))
                        .addBox(-1F, -2.0F, -2.25F, 1, 1, 1, new CubeDeformation(-0.2F))
                        .addBox(.1F, -2.3F, -2F, 1, 1, 1, new CubeDeformation(-0.3F))
                        .addBox(-1.1F, -2.3F, -2F, 1, 1, 1, new CubeDeformation(-0.3F)),
                    PartPose.ZERO
            );
        bComb.addOrReplaceChild("combDuplex", CubeListBuilder.create(), PartPose.ZERO);

        /**
         *      Necks
         */
//        bBody.addOrReplaceChild("hackle", CubeListBuilder.create()
//                        .texOffs(12, 0)
//                        .addBox(-2.0F, -6.0F, -4.0F, 4, 4, 3, new CubeDeformation(-0.5F)),
//                PartPose.offset(0.0F, 0.0F, 1.0F)
//        );

        /**
         *      Bodies
         */
        bBody.addOrReplaceChild("bodyS", CubeListBuilder.create()
                        .texOffs(22, 10)
                        .addBox(-3.0F, -6.0F, -4.0F, 6, 6, 8, new CubeDeformation(-0.5F)),
                PartPose.offset(0.0F, 0.0F, 0.0F)
        );

        bBody.addOrReplaceChild("bodyM", CubeListBuilder.create()
                        .texOffs(22, 10)
                        .addBox(-3.0F, -6.0F, -4.0F, 6, 6, 8),
                PartPose.offset(0.0F, 0.0F, 0.0F)
        );

        bBody.addOrReplaceChild("bodyL", CubeListBuilder.create()
                        .texOffs(22, 10)
                        .addBox(-3.0F, -6.0F, -4.0F, 6, 6, 8, new CubeDeformation(0.5F)),
                PartPose.offset(0.0F, 0.0F, 0.0F)
        );

        /**
         *      Wings
         */
        bLeftWing.addOrReplaceChild("wingLM", CubeListBuilder.create()
                        .texOffs(45, 19)
                        .addBox(0.0F, 0.0F, -3.0F, 1, 4, 6),
                PartPose.ZERO
        );
        bLeftWing.addOrReplaceChild("wingLS", CubeListBuilder.create()
                        .texOffs(46, 20)
                        .addBox(-1.0F, 0F, -3.0F, 1, 3, 5),
                PartPose.ZERO
        );

        bRightWing.addOrReplaceChild("wingRM", CubeListBuilder.create()
                        .texOffs(45, 19)
                        .addBox(0.0F, 0.0F, -3.0F, 1, 4, 6),
                PartPose.ZERO
        );
        bRightWing.addOrReplaceChild("wingRS", CubeListBuilder.create()
                        .texOffs(46, 20)
                        .addBox(-1.0F, 0F, -3.0F, 1, 3, 5),
                PartPose.ZERO
        );

        /**
         *      Legs
         */
        bLeftLeg.addOrReplaceChild("legLS", CubeListBuilder.create()
                        .texOffs(8, 18)
                        .addBox(-2.0F, 3.5F, 1.0F, 1, 3, 1)
                        .texOffs(0, 22)
                        .addBox(-3.0F, 6.0F, -1.0F, 3, 1, 2)
                        .texOffs(2, 23)
                        .addBox(-2.0F, 6.0F, -2.0F, 1, 1, 1),
                PartPose.ZERO
        );
        bLeftLeg.addOrReplaceChild("legLM", CubeListBuilder.create()
                        .texOffs(8, 18)
                        .addBox(-2.0F, 3.5F, 1.0F, 1, 5, 1)
                        .texOffs(0, 22)
                        .addBox(-3.0F, 8.0F, -1.0F, 3, 1, 2)
                        .texOffs(2, 23)
                        .addBox(-2.0F, 8.0F, -2.0F, 1, 1, 1),
                PartPose.ZERO
        );
        bLeftLeg.addOrReplaceChild("legLL", CubeListBuilder.create()
                        .texOffs(8, 18)
                        .addBox(-2.0F, 3.5F, 1.0F, 1, 2, 1)
                        .addBox(-2.0F, 5.5F, 1.0F, 1, 5, 1)
                        .texOffs(0, 22)
                        .addBox(-3.0F, 10.0F, -1.0F, 3, 1, 2)
                        .texOffs(2, 23)
                        .addBox(-2.0F, 10.0F, -2.0F, 1, 1, 1),
                PartPose.ZERO
        );

        bRightLeg.addOrReplaceChild("legRS", CubeListBuilder.create()
                        .texOffs(8, 18)
                        .addBox(1.0F, 3.5F, 1.0F, 1, 3, 1)
                        .texOffs(0, 22)
                        .addBox(0.0F, 6.0F, -1.0F, 3, 1, 2)
                        .texOffs(3, 23)
                        .addBox(1.0F, 6.0F, -2.0F, 1, 1, 1),
                PartPose.ZERO
        );
        bRightLeg.addOrReplaceChild("legRM", CubeListBuilder.create()
                        .texOffs(8, 18)
                        .addBox(1.0F, 3.5F, 1.0F, 1, 5, 1)
                        .texOffs(0, 22)
                        .addBox(0.0F, 8.0F, -1.0F, 3, 1, 2)
                        .texOffs(3, 23)
                        .addBox(1.0F, 8.0F, -2.0F, 1, 1, 1),
                PartPose.ZERO
        );
        bRightLeg.addOrReplaceChild("legRL", CubeListBuilder.create()
                        .texOffs(8, 18)
                        .addBox(1.0F, 3.5F, 1.0F, 1, 2, 1)
                        .addBox(1.0F, 5.5F, 1.0F, 1, 5, 1)
                        .texOffs(0, 22)
                        .addBox(0.0F, 10.0F, -1.0F, 3, 1, 2)
                        .texOffs(3, 23)
                        .addBox(1.0F, 10.0F, -2.0F, 1, 1, 1),
                PartPose.ZERO
        );

        /**
         *      Saddles
         */

        /**
         *      Tails
         */

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

//    public static LayerDefinition createEquipmentLayer() {
//        MeshDefinition meshdefinition = new MeshDefinition();
//        PartDefinition eBase = meshdefinition.getRoot().addOrReplaceChild("equipment", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 22.0F, -8.0F, 0.5F, 0.0F, 0.0F));
//        PartDefinition eCollar = eBase.addOrReplaceChild("eCollar", CubeListBuilder.create(), PartPose.offset(0.0F, -1.0F, -4.0F));
//
//        eCollar.addOrReplaceChild("collar", CubeListBuilder.create()
//                        .texOffs(0, 155)
//                        .addBox(-2.5F, -1.0F, -1.5F, 5,  1, 4, new CubeDeformation(0.001F))
//                        .texOffs(30, 156)
//                        .addBox(0.0F, -1.3333F, -2.5F, 0,  2, 2),
//                PartPose.rotation((float)Math.PI/4.0F, 0.0F, 0.0F)
//        );
//
//        eCollar.addOrReplaceChild("bell", CubeListBuilder.create()
//                        .texOffs(18, 154)
//                        .addBox(-1.5F, 0.0F, -1.5F, 3, 3, 3, new CubeDeformation(-1.0F))
//                        .texOffs(0, 22)
//                        .addBox(-4.0F, -12.0F, -4.0F, 8.0F, 6.0F, 4.0F),
//                PartPose.ZERO
//        );
//
//        return LayerDefinition.create(meshdefinition, 160, 160);
//    }

    public ModelEnhancedChicken(ModelPart modelPart) {
        super(modelPart);
        ModelPart base = modelPart.getChild("base");
        this.theChicken = new WrappedModelPart(base, "bChicken");
        ModelPart bChicken = base.getChild("bChicken");
        this.theBody = new WrappedModelPart(bChicken, "bBody");
        this.theLeftLeg = new WrappedModelPart(bChicken, "bLeftLeg", false);
        this.theRightLeg = new WrappedModelPart(bChicken, "bRightLeg", false);
        ModelPart bLeftLeg = bChicken.getChild("bLeftLeg");
        ModelPart bRightLeg = bChicken.getChild("bRightLeg");
        ModelPart bBody = bChicken.getChild("bBody");
//        this.theNeck = new WrappedModelPart(bBody, "bNeck");
        this.theHead = new WrappedModelPart(bBody, "bHead");
        this.theLeftWing = new WrappedModelPart(bBody, "bLeftWing");
        this.theRightWing = new WrappedModelPart(bBody, "bLeftWing");
        this.theSaddle = new WrappedModelPart(bBody, "bSaddle");
        ModelPart bSaddle = bBody.getChild("bSaddle");
        this.theTail = new WrappedModelPart(bSaddle, "bTail");
//        ModelPart bNeck = bBody.getChild("bNeck");
        ModelPart bHead = bBody.getChild("bHead");
        this.theComb = new WrappedModelPart(bHead, "bComb");
        this.theWaddles = new WrappedModelPart(bHead, "bWaddles");
        this.theCrest = new WrappedModelPart(bHead, "bCrest");
        this.theBeard = new WrappedModelPart(bHead, "bBeard");
        ModelPart bComb = bHead.getChild("bComb");
        ModelPart bWaddles = bHead.getChild("bWaddles");
        ModelPart bCrest = bHead.getChild("bCrest");
        ModelPart bBeard = bHead.getChild("bBeard");
        ModelPart bLeftWing = bBody.getChild("bLeftWing");
        ModelPart bRightWing = bBody.getChild("bRightWing");
        ModelPart bTail = bSaddle.getChild("bTail");


        this.head = new WrappedModelPart("head", bHead);
        this.headNakedNeck = new WrappedModelPart("headNN", bHead);

        this.beak = new WrappedModelPart("beak", bHead);
        this.eyes = new WrappedModelPart("eyes", bHead);
        this.ears = new WrappedModelPart("ears", bHead);

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

        this.beardNakedNeck = new WrappedModelPart("beardNN", bBeard);
        this.beardLarge = new WrappedModelPart("beardL", bBeard);

        this.bodySmall = new WrappedModelPart("bodyS", bBody);
        this.bodyMedium = new WrappedModelPart("bodyM", bBody);
        this.bodyLarge = new WrappedModelPart("bodyL", bBody);


        this.wingLeftSmall = new WrappedModelPart("wingLS", bLeftWing);
        this.wingLeftMedium = new WrappedModelPart("wingLM", bLeftWing);
        this.wingRightSmall = new WrappedModelPart("wingRS", bRightWing);
        this.wingRightMedium = new WrappedModelPart("wingRM", bRightWing);

        this.legLeftShort = new WrappedModelPart("legLS", bLeftLeg);
        this.legLeftMedium = new WrappedModelPart("legLM", bLeftLeg);
        this.legLeftLong = new WrappedModelPart("legLL", bLeftLeg);
        this.legRightShort = new WrappedModelPart("legRS", bRightLeg);
        this.legRightMedium = new WrappedModelPart("legRM", bRightLeg);
        this.legRightLong = new WrappedModelPart("legRL", bRightLeg);

        this.theChicken.addChild(this.theBody);
        this.theChicken.addChild(this.theLeftLeg);
        this.theChicken.addChild(this.theRightLeg);
        this.theBody.addChild(this.theHead);
        this.theBody.addChild(this.theLeftWing);
        this.theBody.addChild(this.theRightWing);
//        this.theNeck.addChild(this.theHead);
        this.theHead.addChild(this.theComb);
        this.theHead.addChild(this.theWaddles);
        this.theHead.addChild(this.theCrest);
        this.theHead.addChild(this.theBeard);

        this.theHead.addChild(this.head);
        this.theHead.addChild(this.headNakedNeck);
        this.theHead.addChild(this.beak);
//        this.theHead.addChild(this.eyes);
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

        this.theBody.addChild(this.bodySmall);
        this.theBody.addChild(this.bodyMedium);
        this.theBody.addChild(this.bodyLarge);
        this.theBody.addChild(this.theSaddle);
        
        this.theLeftWing.addChild(this.wingLeftSmall);
        this.theLeftWing.addChild(this.wingLeftMedium);

        this.theRightWing.addChild(this.wingRightSmall);
        this.theRightWing.addChild(this.wingRightMedium);
        
        this.theLeftLeg.addChild(this.legLeftShort);
        this.theLeftLeg.addChild(this.legLeftMedium);
        this.theLeftLeg.addChild(this.legLeftLong);
        this.theRightLeg.addChild(this.legRightShort);
        this.theRightLeg.addChild(this.legRightMedium);
        this.theRightLeg.addChild(this.legRightLong);
        
        this.theSaddle.addChild(this.theTail);


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

//        this.rightFeather1 = new ModelPart(this, 1, 35);
//        this.rightFeather1.addBox(1.1F, 4F, 0F, 2, 3, 3F);
//
//        this.rightFeather1Extend = new ModelPart(this, 1, 35);
//        this.rightFeather1Extend.addBox(1.1F, 2F, 0F, 2, 2, 3F);
//
//        this.leftFeather1 = new ModelPart(this, 1, 35);
//        this.leftFeather1.mirror = true;
//        this.leftFeather1.addBox(-3.1F, 4F, 0F, 2, 3, 3F);
//
//        this.leftFeather1Extend = new ModelPart(this, 1, 35);
//        this.leftFeather1Extend.mirror = true;
//        this.leftFeather1Extend.addBox(-3.1F, 2F, 0F, 2, 2, 3F);

//        this.rightFeatherTall1 = new ModelRenderer(this,1,35);
//        this.rightFeatherTall1.addBox(1.1F, 3F, 0F, 2, 1, 3);
//        this.rightFeatherTall1.addBox(1.1F, 4F, 0F, 2, 3, 3);
//
//        this.leftFeatherTall1 = new ModelRenderer(this,1,35);
//        this.leftFeatherTall1.mirror = true;
//        this.leftFeatherTall1.addBox(-3.1F, 3F, 0F, 2, 1, 3);
//        this.leftFeatherTall1.addBox(-3.1F, 4F, 0F, 2, 3, 3);

//        this.rightFeather2 = new ModelPart(this, 12, 34);
//        this.rightFeather2.addBox(1.5F, 7F, -2.5F, 2, 2, 5F);
//
//        this.leftFeather2 = new ModelPart(this, 12, 34);
//        this.leftFeather2.mirror = true;
//        this.leftFeather2.addBox(-3.5F, 7F, -2.5F, 2, 2, 5F);
//
//        this.rightFeather3 = new ModelPart(this, 28, 36);
//        this.rightFeather3.addBox(3.5F, 8.9F, -2.5F, 4, 1, 5F);
//
//        this.leftFeather3 = new ModelPart(this, 28, 36);
//        this.leftFeather3.mirror = true;
//        this.leftFeather3.addBox(-7.5F, 8.9F, -2.5F, 4, 1, 5F);
//
//        this.rightVultureHock = new ModelPart(this, 33, 32);
//        this.rightVultureHock.mirror = true;
//        this.rightVultureHock.addBox(2.5F, 3.0F, 2.5F, 1, 3, 4, -0.2F);
//
//        this.leftVultureHock = new ModelPart(this, 33, 32);
//        this.leftVultureHock.addBox(-3.5F, 3.0F, 2.5F, 1, 3, 4, -0.2F);

    }

    private void resetCubes() {
        this.ears.show();

        this.combDuplex.hide();
        this.combSingleXs.hide();
        this.combSingleS.hide();
        this.combSingleM.hide();
        this.combSingleL.hide();
        this.combSingleXl.hide();
        this.combRoseTallS.hide();
        this.combRoseTallM.hide();
        this.combRoseTallL.hide();
        this.combRoseFlatS.hide();
        this.combRoseFlatM.hide();
        this.combRoseFlatL.hide();
        this.combPeaS.hide();
        this.combPeaM.hide();
        this.combPeaL.hide();
        this.combWalnutS.hide();
        this.combWalnutM.hide();
        this.combWalnutL.hide();
        this.combV.hide();

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

        this.legLeftShort.hide();
        this.legLeftMedium.hide();
        this.legLeftLong.hide();
        this.legRightShort.hide();
        this.legRightMedium.hide();
        this.legRightLong.hide();
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha)  {
        ChickenModelData chickenModelData = getChickenModelData();
        ChickenPhenotype chicken = (ChickenPhenotype) chickenModelData.phenotype;

        resetCubes();

        if (chicken != null) {

            if (this.eyes != null) {
                this.eyes.show(chickenModelData.blink);
            }
            if (this.collar != null) {
                this.collar.show(chickenModelData.collar);
            }
//            this.resetAllCubes();

//            boolean longLegs = false;

//            if (!chickenModelData.sleeping) {
//                //long legs
//                longLegs = chicken.hasLongLegs();
//            }


//            wingAngle = 0;   //do not use this to debug wingAngle scroll down
    //
    //        if(genes[88] == 2){
    //            this.wingAngle = this.wingAngle + 0.1F;
    //        }else if(genes[88] == 3) {
    //            this.wingAngle = this.wingAngle + 0.15F;
    //        }else{
    //
    //        }
    //        if(genes[89] == 2){
    //            this.wingAngle = this.wingAngle + 0.1F;
    //        }else if(genes[89] == 3) {
    //            this.wingAngle = this.wingAngle + 0.15F;
    //        }
    //        if(genes[86] == 2){
    //            this.wingAngle = this.wingAngle + 0.1F;
    //        }else if(genes[86] == 3) {
    //            this.wingAngle = this.wingAngle + 0.15F;
    //        }
    //        if(genes[87] == 2){
    //            this.wingAngle = this.wingAngle + 0.1F;
    //        }else if(genes[87] == 3) {
    //            this.wingAngle = this.wingAngle + 0.15F;
    //        }
    //
    //        if(genes[94] == 2 && genes[95] == 2){
    //            this.wingAngle = this.wingAngle * 1.2F;
    //        }else if(genes[94] == 3 && genes[95] == 3) {
    //            this.wingAngle = this.wingAngle * 1.5F;
    //        }else if(genes[94] != 1 || genes[95] != 1) {
    //            this.wingAngle = this.wingAngle * 1.1F;
    //        }
    //
    //        if(genes[96] == 2 && genes[97] == 2){
    //            this.wingAngle = this.wingAngle * 1.2F;
    //        }else if(genes[96] == 3 && genes[97] == 3) {
    //            wingAngle = this.wingAngle * 1.5F;
    //        }else if(genes[96] != 1 || genes[97] != 1) {
    //            this.wingAngle = this.wingAngle * 1.1F;
    //        }

    //        wingAngle = 1.5F;   // used for debugging wingAngle  [ 0 to 1.5 ]
            float size = chickenModelData.size;
            float finalChickenSize = ((3.0F * size * chickenModelData.growthAmount) + size) / 4.0F;

            /**
             *      Head
             */
            this.head.show(!chicken.isNakedNeck);
            this.headNakedNeck.show(chicken.isNakedNeck);

            /**
             *      Neck
             */
            /**
             *      Beard
             */
            switch (chicken.beard) {
                case BIG_BEARD -> {
                    this.beardLarge.show();
                }
                case SMALL_BEARD -> {
                    this.beardNakedNeck.show();
                }
                case NN_BEARD -> {
                    this.beardNakedNeck.show();
                }
            }

            /**
             *      Comb
             */
            if (chickenModelData.growthAmount <= 0.3F) {
                this.ears.hide();
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

            if ((chicken.crestType == Crested.SMALL_CREST || chicken.crestType == Crested.SMALL_FORWARDCREST) || (chicken.crestType != Crested.NONE && chickenModelData.growthAmount > 0.5F)) {
                this.crestSmallF.show();
            } else if (chicken.crestType == Crested.BIG_FORWARDCREST) {
                this.crestMediumF.show();
            } else if (chicken.crestType == Crested.BIG_CREST) {
                this.crestLargeF.show();
            }

            this.theSaddle.show(chicken.rumpless || chickenModelData.growthAmount < 0.15F);

            /**
             *      Wings
             */
            if (chicken.wingSize == 2) {
                this.wingLeftMedium.show();
                this.wingRightMedium.show();
            } else {
                this.wingLeftSmall.show(chicken.wingSize != 2);
                this.wingRightSmall.show(chicken.wingSize != 2);
            }

            /**
             *      Legs
             */
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

            poseStack.pushPose();
            poseStack.scale(finalChickenSize, finalChickenSize, finalChickenSize);
            poseStack.translate(0.0F, -1.5F + 1.5F / finalChickenSize, 0.0F);

//            switch (chicken.footFeatherType) {
//                case BIG_TOEFEATHERS:
//                case TOEFEATHERS:
//                    this.leftFeather3.visible = true;
//                    this.rightFeather3.visible = true;
//                    this.leftFeather3.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
//                    this.rightFeather3.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
//                case FOOTFEATHERS:
//                    this.leftFeather2.visible = true;
//                    this.rightFeather2.visible = true;
//                    this.leftFeather2.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
//                    this.rightFeather2.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
//                case LEGFEATHERS:
//                    if (!chickenModelData.sleeping && !nesting) {
//                        this.leftFeather1.visible = true;
//                        this.rightFeather1.visible = true;
//                        this.leftFeather1.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
//                        this.rightFeather1.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
//                        if (chicken.longHockFeathers || longLegs) {
//                            this.leftFeather1Extend.visible = true;
//                            this.rightFeather1Extend.visible = true;
//                            this.leftFeather1Extend.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
//                            this.rightFeather1Extend.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
//                        }
//                    }
//
//                    if (chicken.isVultureHocked) {
//                        this.leftVultureHock.visible = true;
//                        this.rightVultureHock.visible = true;
//                        this.leftVultureHock.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
//                        this.rightVultureHock.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
//                    }
//                    break;
//            }
//
//
//                if (chicken.earTufts) {
//                    this.earTuftL.visible = true;
//                    this.earTuftR.visible = true;
//                }

            gaRender(this.theChicken, null, Collections.emptyList(), poseStack, vertexConsumer, packedLightIn, packedOverlayIn, red, green, blue, alpha, false);

            poseStack.popPose();
        }
  }

    /**
     * Sets the model's various rotation angles. For bipeds, par1 and par2 are used for animating the movement of arms
     * and legs, where par1 represents the time(so that arms and legs swing back and forth) and par2 represents how
     * "far" arms and legs can swing at most.
     */
    @Override
    public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.currentAnimal = entityIn.getId();
        ChickenModelData chickenModelData = getCreateChickenModelData(entityIn);

        this.theRightLeg.modelPart.y = -5.0F;

        this.legLeftShort.modelPart.y = -5.0F;
        this.legLeftMedium.modelPart.y = -5.0F;
        this.legLeftLong.modelPart.y = -5.0F;

//        ChickenPhenotype chicken = (ChickenPhenotype) chickenModelData.phenotype;
//
//        if (chicken != null) {
//            this.theComb.setZRot(chicken.butterCup ? -(float) Math.PI * 0.15F : 0F);
//
////        float bodyangle = 0.5F;
////        super.setRotationAngles(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
//
//            this.theHead.setY(-3.5F);
//            this.theHead.setZ(-3.5F);
//            this.theHead.setXRot(headPitch * 0.017453292F);
//            this.theHead.setYRot(netHeadYaw * 0.017453292F);
//
////            this.earTuftL.xRot = 1.4F;
////            this.earTuftL.zRot = -1.4F;
////            this.earTuftR.xRot = 1.4F;
////            this.earTuftR.zRot = 1.4F;
//
//            //leg stuff
//            this.theRightLeg.setY(15F);
//            this.theLeftLeg.setY(15F);
//            this.theRightLeg.setXRot(Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount);
//            this.theLeftLeg.setXRot(Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount);
//
//            //body angle
//
//            //tail stuff
//
//            //wing stuff
//
////            int rand = (int)chickenModelData.random * 2000;
////            this.theRightWing.setZRot(ageInTicks + rand);
////            this.theLeftWing.setZRot(-(ageInTicks + rand));
//        }
    }

    private class ChickenModelData extends AnimalModelData {
        boolean isFemale = true;
    }

    private ChickenModelData getChickenModelData() {
        return (ChickenModelData) getAnimalModelData();
    }

    private ChickenModelData getCreateChickenModelData(T enhancedChicken) {
        return (ChickenModelData) getCreateAnimalModelData(enhancedChicken);
    }

    @Override
    protected AnimalModelData setInitialData(T enhancedChicken) {
        ChickenModelData chickenModelData = new ChickenModelData();
        setBaseInitialData(chickenModelData, enhancedChicken);
        chickenModelData.isFemale = enhancedChicken.getOrSetIsFemale();
        return chickenModelData;
    }

    @Override
    protected AnimalModelData updateData(T enhancedChicken) {
        ChickenModelData chickenModelData = (ChickenModelData) super.updateData(enhancedChicken);
        return chickenModelData;
    }

    @Override
    protected Phenotype getPhenotype(T enhancedAnimal) {
        return new ChickenPhenotype(enhancedAnimal.getSharedGenes());
    }

    protected class ChickenPhenotype extends Phenotype {
        private Crested crestType = Crested.NONE;
        private FootFeathers footFeatherType = FootFeathers.NONE;
        private Comb comb = Comb.SINGLE;
        private Beard beard;
        private boolean butterCup = false;
        private boolean isVultureHocked;
        private boolean isNakedNeck;
        private boolean earTufts;
        private boolean rumpless;
        private boolean longHockFeathers;
        private boolean creeper;
        private int longLegs = 0;
        private int combSize = 0;
        private int waddleSize;
        private int bodyType;
        private int wingSize;
        private int wingPlacement = 0;

        ChickenPhenotype(Genes genes) {
            super(genes);
            int[] gene = genes.getAutosomalGenes();
            this.isNakedNeck = gene[52] == 1 || gene[53] == 1;
            this.rumpless = gene[72] == 2 || gene[73] == 2;
            this.earTufts = gene[150] == 2 || gene[151] == 2;
            this.isVultureHocked = gene[102] == 2 && gene[103] == 2;
            this.creeper = gene[70] == 2 || gene[71] == 2;

            if (gene[56] == 1 || gene[57] == 1) {
                this.beard = this.isNakedNeck ? Beard.NN_BEARD : Beard.BIG_BEARD;
            } else {
                this.beard = Beard.NONE;
            }

            //Crest type
            if (!(gene[54] == 3 && gene[55] == 3)) {
                if (gene[54] == 3 || gene[55] == 3) {
                    if (gene[54] == 1 || gene[55] == 1) {
                        this.crestType = Crested.SMALL_CREST;
                    } else {
                        this.crestType = Crested.SMALL_FORWARDCREST;
                    }
                } else if (gene[54] != gene[55] || (gene[54] == 1 && gene[55] == 1)) {
                    this.crestType = Crested.BIG_CREST;
                } else {
                    this.crestType = Crested.BIG_FORWARDCREST;
                }
            }

            //Foot Feather Type
            if (!(gene[60] == 3 && gene[61] == 3)) {
                if (gene[60] == 1 || gene[61] == 1) {
                    if (gene[58] == 2 && gene[59] == 2) {
                        this.footFeatherType = FootFeathers.BIG_TOEFEATHERS;
                    } else if (gene[58] == 2 || gene[59] == 2 || (gene[58] == 1 && gene[58] == 1)) {
                        this.footFeatherType = FootFeathers.TOEFEATHERS;
                    } else if (gene[58] == 1 || gene[59] == 1) {
                        this.footFeatherType = FootFeathers.FOOTFEATHERS;
                    }
                } else {
                    if (gene[58] == 2 && gene[59] == 2) {
                        this.footFeatherType = FootFeathers.TOEFEATHERS;
                    } else if (gene[58] == 2 || gene[59] == 2 || (gene[58] == 1 && gene[58] == 1)) {
                        this.footFeatherType = FootFeathers.FOOTFEATHERS;
                    } else if (gene[58] == 1 || gene[59] == 1) {
                        this.footFeatherType = FootFeathers.LEGFEATHERS;
                    }
                }
            }

            if ((gene[50] == 2 || gene[51] == 2) && !(gene[50] == 1 || gene[51] == 1)) {
                if (gene[46] == 3 && gene[47] == 3 && gene[48] == 2 && gene[49] == 2) {
                    //v comb
                    this.comb = Comb.V;
                } else {
                    if (gene[48] == 2 && gene[49] == 2) {
                        //only waddles
                        this.comb = Comb.NONE;
                    }
                }
            } else {
                if (gene[48] == 1 || gene[49] == 1) {
                    if (gene[46] == 3 && gene[47] == 3) {
                        //peacomb
                        this.comb = Comb.PEA;
                    } else {
                        //walnut
                        this.comb = Comb.WALNUT;
                    }
                } else {
                    if (gene[46] == 3 && gene[47] == 3) {
                        //single comb
                        this.comb = Comb.SINGLE;
                    } else if (gene[46] == 1 || gene[47] == 1) {
                        //rose comb
//                        if (gene[46] == 3 || gene[47] == 3) {
//                            this.comb = Comb.HET_ROSE_ONE;
//                        } else {
                            this.comb = Comb.ROSE_ONE;
//                        }
                    } else {
                        //rose comb2
//                        if (gene[46] == 3 || gene[47] == 3) {
//                            this.comb = Comb.HET_ROSE_TWO;
//                        } else {
                            this.comb = Comb.ROSE_TWO;
//                        }
                    }
                }

                if (gene[50] == 2 || gene[51] == 2) {
                    this.butterCup = true;
                    this.combSize = -1;
                } else {
                    this.butterCup = gene[50] == 3 || gene[51] == 3;
                }

            }

            if ((gene[86] == 1 && gene[87] == 1) || (gene[86] == 3 && gene[87] == 3)){
                wingPlacement = 2;
            } else if (gene[86] == 1 || gene[87] == 1){
                wingPlacement = 1;
            }

            //comb size [80 and 81 small / 82 and 83 large]
            if (gene[80] == 2) {
                this.combSize = this.combSize + 1;
            }
            if (gene[81] == 2) {
                this.combSize = this.combSize + 1;
            }
            if (gene[82] == 1) {
                this.combSize = this.combSize + 1;
            }
            if (gene[83] == 1) {
                this.combSize = this.combSize + 1;
            }

            if (this.combSize < 0) {
                this.combSize = 0;
            }

            this.waddleSize = this.combSize;

            if (gene[84] == 1 && gene[85] == 1 && this.waddleSize > 0) {
                this.waddleSize = this.waddleSize - 1;
            }

            if (gene[146] == 2 && gene[147] == 2) {
                if (gene[148] == 2 && gene[149] == 2) {
                    //normal body
                    this.bodyType = 0;
                } else {
                    //big body
                    this.bodyType = 1;
                }
            } else if (gene[148] == 2 && gene[149] == 2) {
                if (gene[146] == 2 || gene[147] == 2) {
                    //normal body
                    this.bodyType = 0;
                } else {
                    //small body
                    this.longHockFeathers = true;
                    this.bodyType = -1;
                }
            } else {
                //normal body
                this.bodyType = 0;
            }

            if (gene[90] == 1 || gene[91] == 1){
                this.wingSize = 2;
            } else if (gene[92] == 1 || gene[93] == 1){
                this.wingSize = 1;
            }

            if (gene[168] == 2) {
                this.longLegs++;
            }
            if (gene[169] == 2) {
                this.longLegs++;
            }
        }

        private boolean isBearded() {
            return this.beard != Beard.NONE;
        }

        private boolean isCombed() { return this.comb != Comb.NONE && this.comb != Comb.BREDA_COMBLESS; }

        private boolean hasLongLegs() { return this.longLegs != 0; }

    }

    private enum Crested {
        BIG_CREST,
        BIG_FORWARDCREST,
        SMALL_CREST,
        SMALL_FORWARDCREST,
        NONE
    }

    private enum FootFeathers {
        BIG_TOEFEATHERS,
        TOEFEATHERS,
        FOOTFEATHERS,
        LEGFEATHERS,
        NONE
    }

    private enum Comb {
        SINGLE (false),
        ROSE_ONE (false),
        ROSE_TWO (false),
        PEA (true),
        WALNUT (true),
        V (false),
        HET_ROSE_ONE (false),
        HET_ROSE_TWO (false),
        BREDA_COMBLESS (false),
        NONE (true);

        boolean containsPeaComb;

        Comb(boolean effectsWaddleSize) {
            this.containsPeaComb = effectsWaddleSize;
        }

        public boolean hasPeaComb() {
            return this.containsPeaComb;
        }
    }

    private enum Beard {
        BIG_BEARD,
        SMALL_BEARD,
        NN_BEARD,
        NONE
    }

}
