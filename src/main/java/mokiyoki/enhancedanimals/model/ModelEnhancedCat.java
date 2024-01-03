package mokiyoki.enhancedanimals.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import mokiyoki.enhancedanimals.entity.EnhancedCat;
import mokiyoki.enhancedanimals.model.modeldata.*;
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
public class ModelEnhancedCat<T extends EnhancedCat> extends EnhancedAnimalModel<T> {
    private CatModelData catModelData;
    private static WrappedModelPart theCat;
    private static WrappedModelPart theHead;
    private static WrappedModelPart theEarL;
    private static WrappedModelPart theEarR;
    private static WrappedModelPart theNeck;
    private static WrappedModelPart theSnout;
    private static WrappedModelPart theMouth;
    private static WrappedModelPart theBodyFront;
    private static WrappedModelPart theBodyBack;
    private static WrappedModelPart theLegFrontLeft;
    private static WrappedModelPart theLegFrontRight;
    private static WrappedModelPart theLegBackLeft;
    private static WrappedModelPart theLegBackRight;
    private static WrappedModelPart theLegBottomFrontLeft;
    private static WrappedModelPart theLegBottomFrontRight;
    private static WrappedModelPart theLegBottomBackLeft;
    private static WrappedModelPart theLegBottomBackRight;
    private static WrappedModelPart thePawFrontLeft;
    private static WrappedModelPart thePawFrontRight;
    private static WrappedModelPart thePawBackLeft;
    private static WrappedModelPart thePawBackRight;
    private static WrappedModelPart theTail;

    private static WrappedModelPart head;
    private static WrappedModelPart neck;
    private static WrappedModelPart snout;
    private static WrappedModelPart nose;
    private static WrappedModelPart earL[] = new WrappedModelPart[3];
    private static WrappedModelPart earR[] = new WrappedModelPart[3];
    private static WrappedModelPart mouth;
    private static WrappedModelPart bodyFront;
    private static WrappedModelPart bodyBack;
    private static WrappedModelPart legFrontLeft;
    private static WrappedModelPart legFrontRight;
    private static WrappedModelPart legBackLeft;
    private static WrappedModelPart legBackRight;

    private static WrappedModelPart legFrontLeftBottom;
    private static WrappedModelPart legFrontRightBottom;
    private static WrappedModelPart legBackLeftBottom;
    private static WrappedModelPart legBackRightBottom;

    private static WrappedModelPart pawFrontLeft;
    private static WrappedModelPart pawFrontRight;
    private static WrappedModelPart pawBackLeft;
    private static WrappedModelPart pawBackRight;

    private static WrappedModelPart toesFrontLeft;
    private static WrappedModelPart toesFrontRight;
    private static WrappedModelPart toesBackLeft;
    private static WrappedModelPart toesBackRight;
    private static WrappedModelPart tail[] = new WrappedModelPart[7];

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition base = meshdefinition.getRoot().addOrReplaceChild("base", CubeListBuilder.create(), PartPose.offset(0.0F, 8.0F, 0.0F));
        base.addOrReplaceChild("bMouth", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 1.5F, -2.25F, -Mth.PI*0.027F, 0.0F, 0.0F));
        base.addOrReplaceChild("bSnout", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 1.0F, -4.5F, Mth.PI*0.527F, 0.0F, 0.0F));
        base.addOrReplaceChild("bHead", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -1.0F, -7.0F, Mth.PI*0.15F, 0.0F, 0.0F));
        base.addOrReplaceChild("bNeck", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 3.0F, -7.0F, -Mth.PI*0.15F, 0.0F, 0.0F));
        base.addOrReplaceChild("bEarL", CubeListBuilder.create(), PartPose.offsetAndRotation(1.25F, -1.5F, 0.5F, 0.0F, 0.0F, 0.0F));
        base.addOrReplaceChild("bEarR", CubeListBuilder.create(), PartPose.offsetAndRotation(-1.25F, -1.5F, 0.5F, 0.0F, 0.0F, 0.0F));
        base.addOrReplaceChild("bBodyF", CubeListBuilder.create(), PartPose.ZERO);
        base.addOrReplaceChild("bBodyB", CubeListBuilder.create(), PartPose.ZERO);
        base.addOrReplaceChild("bLegFL", CubeListBuilder.create(), PartPose.offset(-1.5F, 7.0F, -7.0F));
        base.addOrReplaceChild("bLegFR", CubeListBuilder.create(), PartPose.offset(1.5F, 7.0F, -7.0F));
        base.addOrReplaceChild("bLegBFL", CubeListBuilder.create(), PartPose.offset(0.0F, 4.0F, 0.0F));
        base.addOrReplaceChild("bLegBFR", CubeListBuilder.create(), PartPose.offset(0.0F, 4.0F, 0.0F));
        base.addOrReplaceChild("bPawFL", CubeListBuilder.create(), PartPose.offset(0.0F, 4.0F, 0.0F));
        base.addOrReplaceChild("bPawFR", CubeListBuilder.create(), PartPose.offset(0.0F, 4.0F, 0.0F));
        base.addOrReplaceChild("bLegBL", CubeListBuilder.create(), PartPose.offset(-1.5F, 0.0F, 6.0F));
        base.addOrReplaceChild("bLegBR", CubeListBuilder.create(), PartPose.offset(1.5F, 0.0F, 6.0F));
        base.addOrReplaceChild("bLegBBL", CubeListBuilder.create(), PartPose.offset(0.0F, 8.0F, 4.0F));
        base.addOrReplaceChild("bLegBBR", CubeListBuilder.create(), PartPose.offset(0.0F, 8.0F, 4.0F));
        base.addOrReplaceChild("bPawBL", CubeListBuilder.create(), PartPose.offset(0.0F, 7.0F, 0.0F));
        base.addOrReplaceChild("bPawBR", CubeListBuilder.create(), PartPose.offset(0.0F, 7.0F, 0.0F));
        base.addOrReplaceChild("bTail", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 8.0F));

        CubeDeformation deformation = new CubeDeformation(-0.5F);

        /**
         *      Snout
         */
        base.addOrReplaceChild("snout", CubeListBuilder.create()
                        .texOffs(6, 7)
                        .addBox(-1.5F, -3.0F, -2.0F, 3, 3, 2, new CubeDeformation(0.0F, 0.0F, 0.0F)),
                PartPose.ZERO
        );
        base.addOrReplaceChild("nose", CubeListBuilder.create()
                        .texOffs(2, 7)
                        .addBox(-0.5F, -3.1F, -0.55F, 1, 4, 1, new CubeDeformation(0.0F, 0.0F, 0.0F)),
                PartPose.ZERO
        );
        base.addOrReplaceChild("mouth", CubeListBuilder.create()
                        .texOffs(0, 12)
                        .addBox(-1.5F, -4.5F, -1.0F, 3, 5, 2, deformation),
                PartPose.ZERO
        );

        /**
         *      Ears
         */
        base.addOrReplaceChild("earL0", CubeListBuilder.create()
                        .texOffs(21, 4)
                        .addBox(-1.0F, -1.0F, -1.025F, 1,2,1, new CubeDeformation(0.0F, 0.0F, 0.0F))
                        .texOffs(24, 1)
                        .addBox(-1.0F, -2.0F, -0.525F, 1,3,1, new CubeDeformation(0.0F, 0.0F, -0.45F)),
                PartPose.rotation(0.0F, -Mth.PI*0.15F, 0.0F)
        );
        base.addOrReplaceChild("earL1", CubeListBuilder.create()
                        .texOffs(28, 0)
                        .addBox(0.0F, -3.0F, -0.525F, 1,4,1, new CubeDeformation(0.0F, 0.0F, -0.45F)),
                PartPose.ZERO
        );
        base.addOrReplaceChild("earL2", CubeListBuilder.create()
                        .texOffs(32, 1)
                        .addBox(0.0F, -2.0F, -0.525F, 1,3,1, new CubeDeformation(0.0F, 0.0F, -0.45F))
                        .texOffs(35, 4)
                        .addBox(0.0F, 0.0F, -1.025F, 1,1,1, new CubeDeformation(0.0F, 0.0F, 0.0F)),
                PartPose.offsetAndRotation(1.0F, 0.0F, 0.0F, 0.0F, Mth.PI*0.15F, 0.0F)
        );

        base.addOrReplaceChild("earR0", CubeListBuilder.create()
                        .texOffs(16, 4)
                        .addBox(0.0F, -1.0F, -1.025F, 1,2,1, new CubeDeformation(0.0F, 0.0F, 0.0F))
                        .texOffs(13, 1)
                        .addBox(0.0F, -2.0F, -0.525F, 1,3,1, new CubeDeformation(0.0F, 0.0F, -0.45F)),
                PartPose.rotation(0.0F, Mth.PI*0.15F, 0.0F)
        );
        base.addOrReplaceChild("earR1", CubeListBuilder.create()
                        .texOffs(9, 0)
                        .addBox(-1.0F, -3.0F, -0.525F, 1,4,1, new CubeDeformation(0.0F, 0.0F, -0.45F)),
                PartPose.ZERO
        );
        base.addOrReplaceChild("earR2", CubeListBuilder.create()
                        .texOffs(5, 1)
                        .addBox(-1.0F, -2.0F, -0.525F, 1,3,1, new CubeDeformation(0.0F, 0.0F, -0.45F))
                        .texOffs(2, 4)
                        .addBox(-1.0F, 0.0F, -1.025F, 1,1,1, new CubeDeformation(0.0F, 0.0F, 0.0F)),
                PartPose.offsetAndRotation(-1.0F, 0.0F, 0.0F, 0.0F, -Mth.PI*0.15F, 0.0F)
        );

        /**
         *      Head
         */
        base.addOrReplaceChild("head", CubeListBuilder.create()
                        .texOffs(10, 7)
                        .addBox(-3.0F, -2.0F, -5.0F, 6, 6, 6, deformation),
                PartPose.ZERO
        );

        /**
         *      Neck
         */
        base.addOrReplaceChild("neck", CubeListBuilder.create()
                        .texOffs(8, 19)
                        .addBox(-3.0F, -3.0F, -7.0F, 6, 6, 8, deformation),
                PartPose.ZERO
        );

        /**
         *      Body
         */
        base.addOrReplaceChild("bodyF", CubeListBuilder.create()
                        .texOffs(8, 33)
                        .addBox(-3.0F, -9.5F, -7.5F, 6, 10, 8, deformation)
                        .texOffs(5, 115)
                        .addBox(-3.0F, -9.5F, -10.0F, 6, 10, 3, new CubeDeformation(-0.499F, -0.5F, 0.0F)),
                PartPose.rotation(Mth.HALF_PI, 0.0F, 0.0F)
        );
        base.addOrReplaceChild("bodyB", CubeListBuilder.create()
                        .texOffs(36, 0)
                        .addBox(-3.0F, -0.5F, -7.5F, 6, 7, 8, deformation)
                        .texOffs(29, 15)
                        .addBox(-3.0F, 5.5F, -4.5F, 6, 4, 5, deformation)
                        .texOffs(33, 118)
                        .addBox(-3.0F, -0.5F, -10.0F, 6, 7, 3, new CubeDeformation(-0.499F, -0.5F, 0.0F)),
                PartPose.rotation(Mth.HALF_PI, 0.0F, 0.0F)
        );

        /**
         *      Legs
         */
            /** Front */
                /** Top */
        base.addOrReplaceChild("legFL", CubeListBuilder.create()
                        .texOffs(36, 30)
                        .addBox(-1.0F, -4.0F, -1.0F, 2, 4, 2),
                PartPose.rotation(Mth.PI, 0.0F, 0.0F)
        );
        base.addOrReplaceChild("legFR", CubeListBuilder.create()
                        .texOffs(44, 30)
                        .addBox(-1.0F, -4.0F, -1.0F, 2, 4, 2),
                PartPose.rotation(Mth.PI, 0.0F, 0.0F)
        );
                /** Bottom */
        base.addOrReplaceChild("legBFL", CubeListBuilder.create()
                        .texOffs(36, 36)
                        .addBox(-1.0F, -4.0F, -1.0F, 2, 4, 2),
                PartPose.rotation(Mth.PI, 0.0F, 0.0F)
        );
        base.addOrReplaceChild("legBFR", CubeListBuilder.create()
                        .texOffs(44, 36)
                        .addBox(-1.0F, -4.0F, -1.0F, 2, 4, 2),
                PartPose.rotation(Mth.PI, 0.0F, 0.0F)
        );

            /** Back */
                /** Top */
        base.addOrReplaceChild("legBL", CubeListBuilder.create()
                        .texOffs(36, 42)
                        .addBox(-1.5F, 0.0F, 0.0F, 3, 8, 4)
                        .texOffs(104, 122)
                        .addBox(-1.5F, 8.0F, 0.0F, 0, 2, 4),
                PartPose.ZERO
        );
        base.addOrReplaceChild("legBR", CubeListBuilder.create()
                        .texOffs(50, 42)
                        .addBox(-1.5F, 0.0F, 0.0F, 3, 8, 4)
                        .texOffs(108, 122)
                        .addBox(1.5F, 8.0F, 0.0F, 0, 2, 4),
                PartPose.ZERO
        );
                /** Bottom */
        base.addOrReplaceChild("legBBL", CubeListBuilder.create()
                        .texOffs(52, 32)
                        .addBox(-1.0F, -7.0F, -1.0F, 2, 8, 2),
                PartPose.rotation(Mth.PI, 0.0F, 0.0F)
        );
        base.addOrReplaceChild("legBBR", CubeListBuilder.create()
                        .texOffs(56, 54)
                        .addBox(-1.0F, -7.0F, -1.0F, 2, 8, 2),
                PartPose.rotation(Mth.PI, 0.0F, 0.0F)
        );

        /**
         *      Paws
         */
        base.addOrReplaceChild("pawFL", CubeListBuilder.create()
                        .texOffs(28, 33)
                        .addBox(-1.0F, -1.0F, -1.0F, 2, 1, 2),
                PartPose.rotation(Mth.PI, 0.0F, 0.0F)
        );
        base.addOrReplaceChild("pawFR", CubeListBuilder.create()
                        .texOffs(28,36)
                        .addBox(-1.0F, -1.0F, -1.0F, 2, 1, 2),
                PartPose.rotation(Mth.PI, 0.0F, 0.0F)
        );
        base.addOrReplaceChild("pawBL", CubeListBuilder.create()
                        .texOffs(28, 24)
                        .addBox(-1.0F, -1.0F, -1.0F, 2, 1, 2),
                PartPose.rotation(Mth.PI, 0.0F, 0.0F)
        );
        base.addOrReplaceChild("pawBR", CubeListBuilder.create()
                        .texOffs(36, 24)
                        .addBox(-1.0F, -1.0F, -1.0F, 2, 1, 2),
                PartPose.rotation(Mth.PI, 0.0F, 0.0F)
        );

        /**
         *      Toes
         */
        deformation = new CubeDeformation(-1.5F, 0.0F, -1.5F);
        base.addOrReplaceChild("toesFL", CubeListBuilder.create()
                        .texOffs(31, 54)
                        .addBox(-2.5F, 0.05F, -2.5F, 5, 0, 5, deformation),
                PartPose.offset(0.0F, 1.0F, 0.0F)
        );
        base.addOrReplaceChild("toesFR", CubeListBuilder.create()
                        .texOffs(41, 54)
                        .addBox(-2.5F, 0.05F, -2.5F, 5, 0, 5, deformation),
                PartPose.offset(0.0F, 1.0F, 0.0F)
        );
        base.addOrReplaceChild("toesBL", CubeListBuilder.create()
                        .texOffs(31, 59)
                        .addBox(-2.5F, 0.05F, -2.5F, 5, 0, 5, deformation),
                PartPose.offset(0.0F, 1.0F, 0.0F)
        );
        base.addOrReplaceChild("toesBR", CubeListBuilder.create()
                        .texOffs(41, 59)
                        .addBox(-2.5F, 0.05F, -2.5F, 5, 0, 5, deformation),
                PartPose.offset(0.0F, 1.0F, 0.0F)
        );

        /**
         *      Tail
         */
        for (int i = 0; i < 7; i++) {
            base.addOrReplaceChild("tail"+i, CubeListBuilder.create()
                    .texOffs(0, 60-(i*4))
                    .addBox(-1.0F, -2.0F, -1.0F, 2, 2, 2)
                    .texOffs(119, 124-(i*4))
                    .addBox(-1.0F, -2.25F, 1.0F, 2, 2, 2, new CubeDeformation(i%2==0?0.0F:0.01F, 0.0F, 0.0F)),
                    PartPose.offset(0.0F, i == 0 ? 0.0F : -2.0F, i == 0 ? 1.0F : 0.0F)
            );
        }

        return LayerDefinition.create(meshdefinition, 128, 128);
    }

    public ModelEnhancedCat(ModelPart modelPart) {
        super(modelPart);
        ModelPart base = modelPart.getChild("base");

        theCat = new WrappedModelPart(base, "base");
        theMouth = new WrappedModelPart("bMouth", base);
        theSnout = new WrappedModelPart("bSnout", base);
        theEarL = new WrappedModelPart("bEarL", base);
        theEarR = new WrappedModelPart("bEarR", base);
        theHead = new WrappedModelPart("bHead", base);
        theNeck = new WrappedModelPart("bNeck", base);
        theBodyFront = new WrappedModelPart("bBodyF", base);
        theBodyBack = new WrappedModelPart("bBodyB", base);
        theLegFrontLeft = new WrappedModelPart("bLegFL", base);
        theLegFrontRight = new WrappedModelPart("bLegFR", base);
        theLegBottomFrontLeft = new WrappedModelPart("bLegBFL", base);
        theLegBottomFrontRight = new WrappedModelPart("bLegBFR", base);
        thePawFrontLeft = new WrappedModelPart("bPawFL", base);
        thePawFrontRight = new WrappedModelPart("bPawFR", base);
        theLegBackLeft = new WrappedModelPart("bLegBL", base);
        theLegBackRight = new WrappedModelPart("bLegBR", base);
        theLegBottomBackLeft = new WrappedModelPart("bLegBBL", base);
        theLegBottomBackRight = new WrappedModelPart("bLegBBR", base);
        thePawBackLeft = new WrappedModelPart("bPawBL", base);
        thePawBackRight = new WrappedModelPart("bPawBR", base);
        theTail = new WrappedModelPart("bTail", base);

        mouth = new WrappedModelPart("mouth", base);
        snout = new WrappedModelPart("snout", base);
        for (int i = 0; i < 3; i++) {
            earL[i] = new WrappedModelPart("earL"+i, base);
            theEarL.addChild(earL[i]);
        }
        for (int i = 0; i < 3; i++) {
            earR[i] = new WrappedModelPart("earR"+i, base);
            theEarR.addChild(earR[i]);
        }
        head = new WrappedModelPart("head", base);
        neck = new WrappedModelPart("neck", base);
        nose = new WrappedModelPart("nose", base);

        bodyFront = new WrappedModelPart("bodyF", base);
        bodyBack = new WrappedModelPart("bodyB", base);

        legFrontLeft = new WrappedModelPart("legFL", base);
        legFrontRight = new WrappedModelPart("legFR", base);
        legFrontLeftBottom = new WrappedModelPart("legBFL", base);
        legFrontRightBottom = new WrappedModelPart("legBFR", base);
        pawFrontLeft = new WrappedModelPart("pawFL", base);
        pawFrontRight = new WrappedModelPart("pawFR", base);

        toesFrontLeft = new WrappedModelPart("toesFL", base);
        toesFrontRight = new WrappedModelPart("toesFR", base);

        legBackLeft = new WrappedModelPart("legBL", base);
        legBackRight = new WrappedModelPart("legBR", base);
        legBackLeftBottom = new WrappedModelPart("legBBL", base);
        legBackRightBottom = new WrappedModelPart("legBBR", base);
        pawBackLeft = new WrappedModelPart("pawBL", base);
        pawBackRight = new WrappedModelPart("pawBR", base);

        toesBackLeft = new WrappedModelPart("toesBL", base);
        toesBackRight = new WrappedModelPart("toesBR", base);

        for (int i = 0; i < 7; i++) {
            tail[i] = new WrappedModelPart("tail"+i, base);
            if (i>0) {
                tail[i-1].addChild(tail[i]);
            }
        }

        theCat.addChild(theBodyBack);
        theHead.addChild(theSnout);
        theSnout.addChild(theMouth);
        theHead.addChild(theEarL);
        theHead.addChild(theEarR);
        theNeck.addChild(theHead);
        theBodyBack.addChild(theBodyFront);
        theBodyFront.addChild(theNeck);
        theBodyFront.addChild(theLegFrontLeft);
        theBodyFront.addChild(theLegFrontRight);
        theBodyBack.addChild(theLegBackLeft);
        theBodyBack.addChild(theLegBackRight);
        theBodyBack.addChild(theTail);
        theLegFrontLeft.addChild(theLegBottomFrontLeft);
        theLegFrontRight.addChild(theLegBottomFrontRight);
        theLegBottomFrontLeft.addChild(thePawFrontLeft);
        theLegBottomFrontRight.addChild(thePawFrontRight);
        
        theLegBackLeft.addChild(theLegBottomBackLeft);
        theLegBackRight.addChild(theLegBottomBackRight);
        theLegBottomBackLeft.addChild(thePawBackLeft);
        theLegBottomBackRight.addChild(thePawBackRight);

        theMouth.addChild(mouth);

        theSnout.addChild(snout);
        theSnout.addChild(nose);

        theHead.addChild(head);

        theNeck.addChild(neck);

        theBodyFront.addChild(bodyFront);
        theBodyBack.addChild(bodyBack);

        theLegFrontLeft.addChild(legFrontLeft);
        theLegFrontRight.addChild(legFrontRight);

        theLegBottomFrontLeft.addChild(legFrontLeftBottom);
        theLegBottomFrontRight.addChild(legFrontRightBottom);

        thePawFrontLeft.addChild(pawFrontLeft);
        thePawFrontLeft.addChild(toesFrontLeft);

        thePawFrontRight.addChild(pawFrontRight);
        thePawFrontRight.addChild(toesFrontRight);

        theLegBackLeft.addChild(legBackLeft);
        theLegBackRight.addChild(legBackRight);

        theLegBottomBackLeft.addChild(legBackLeftBottom);
        theLegBottomBackRight.addChild(legBackRightBottom);

        thePawBackLeft.addChild(pawBackLeft);
        thePawBackLeft.addChild(toesBackLeft);

        thePawBackRight.addChild(pawBackRight);
        thePawBackRight.addChild(toesBackRight);

        theTail.addChild(tail[0]);

    }

    private void resetCubes() {

    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        if (this.catModelData!=null && this.catModelData.getPhenotype() != null) {
            CatPhenotype cat = catModelData.getPhenotype();

            resetCubes();
            super.renderToBuffer(this.catModelData, poseStack, vertexConsumer, packedLightIn, packedOverlayIn, red, green, blue, alpha);

            Map<String, List<Float>> mapOfScale = new HashMap<>();

            float scale = 0.75F;
            mapOfScale.put("base", ModelHelper.createScalings(scale, 0.0F, 1.0F, 0.0F));
            mapOfScale.put("bNeck", ModelHelper.createScalings(1.01F, 0.0F, 0.0F, 0.0F));

            poseStack.pushPose();
            poseStack.scale(scale, scale, scale);
            poseStack.translate(0.0F, 0.0F, 0.0F);

            gaRender(theCat, mapOfScale, poseStack, vertexConsumer, packedLightIn, packedOverlayIn, red, green, blue, alpha);

            poseStack.popPose();
        }
    }

    protected void saveAnimationValues(CatModelData data) {
        Map<String, Vector3f> map = data.offsets;
        map.put("bLegBL", this.getRotationVector(theLegBackLeft));
        map.put("bLegBR", this.getRotationVector(theLegBackRight));
        map.put("bLegBBL", this.getRotationVector(theLegBottomBackLeft));
        map.put("bLegBBR", this.getRotationVector(theLegBottomBackRight));
        map.put("bTail", this.getRotationVector(theTail));
        for (int i = 0; i < 7; i++) {
            map.put("tail"+i, this.getRotationVector(tail[i]));
        }
    }
    private void setupInitialAnimationValues(CatModelData data) {
        Map<String, Vector3f> map = data.offsets;
        if (map.isEmpty()) {
            theLegBackLeft.setXRot(Mth.PI*-0.05F);
            theLegBackRight.setXRot(Mth.PI*-0.05F);
            theTail.setXRot(Mth.HALF_PI*-0.5F);
            tail[3].setXRot(Mth.HALF_PI*0.5F);
            tail[6].setXRot(Mth.HALF_PI*-0.5F);
        } else {
            theLegBackLeft.setRotation(map.get("bLegBL"));
            theLegBackRight.setRotation(map.get("bLegBR"));
            theLegBottomBackLeft.setRotation(map.get("bLegBBL"));
            theLegBottomBackRight.setRotation(map.get("bLegBBR"));
            theTail.setRotation(map.get("bTail"));
            for (int i = 0; i < 7; i++) {
                tail[i].setRotation(map.get("tail"+i));
            }
        }
    }

    @Override
    public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.catModelData = getCreateCatModelData(entityIn);

        if (this.catModelData != null) {
            setupInitialAnimationValues(this.catModelData);
            boolean isMoving = entityIn.getDeltaMovement().horizontalDistanceSqr() > 1.0E-7D || entityIn.xOld != entityIn.getX() || entityIn.zOld != entityIn.getZ();
            boolean awake = true;

            if (this.catModelData.sleeping && !isMoving) {
                if (this.catModelData.sleepDelay == -1) {
                    this.catModelData.sleepDelay = (int) ageInTicks + ((entityIn.getRandom().nextInt(10)) * 20) + 10;
                } else if (this.catModelData.sleepDelay <= ageInTicks && layDownAnimation()) {
                    this.catModelData.sleepDelay = 0;
                    awake = false;
                }
            }  else if (this.catModelData.sleepDelay != -1) {
                this.catModelData.sleepDelay = -1;
            }

            if (awake) {
                if (isMoving) {
                    standing();
                    legsWalking(limbSwing, limbSwingAmount);
                } else {
                    if (false/*TODO - entityIn.isInSittingPose()*/) {
                        sitting();
                    } else {
                        standing();
                        legsDefault();
                    }
                }
                headLookingAnimation(netHeadYaw, headPitch);
            }

            articulate();

            saveAnimationValues(this.catModelData);
        }
    }

    private void legsDefault() {
        theLegFrontLeft.setXRot(this.lerpTo(theLegFrontLeft.getXRot(), 0.0F));
        theLegFrontRight.setXRot(this.lerpTo(theLegFrontRight.getXRot(), 0.0F));

        theLegBackLeft.setXRot(this.lerpTo(theLegBackLeft.getXRot(), 0.0F));
        theLegBackRight.setXRot(this.lerpTo(theLegBackRight.getXRot(), 0.0F));

        theLegBottomFrontLeft.setXRot(this.lerpTo(theLegBottomFrontLeft.getXRot(), 0.0F));
        theLegBottomFrontRight.setXRot(this.lerpTo(theLegBottomFrontRight.getXRot(), 0.0F));

        theLegBottomBackLeft.setXRot(this.lerpTo(theLegBottomBackLeft.getXRot(), 0.0F));
        theLegBottomBackRight.setXRot(this.lerpTo(theLegBottomBackRight.getXRot(), 0.0F));
    }
    private void legsWalking(float limbSwing, float limbSwingAmount) {
        float f = (Mth.cos(limbSwing * 0.6662F)) * 1.4F * limbSwingAmount;
        float f1 = (Mth.cos(limbSwing * 0.6662F + Mth.PI) * 1.4F * limbSwingAmount);

        theLegFrontLeft.setXRot(f);
        theLegFrontRight.setXRot(f1);
        theLegBackLeft.setXRot(f);
        theLegBackRight.setXRot(f1);

        theLegBottomFrontLeft.setXRot(this.lerpTo(theLegBottomFrontLeft.getXRot(), 0.0F));
        theLegBottomFrontRight.setXRot(this.lerpTo(theLegBottomFrontRight.getXRot(), 0.0F));

        theLegBottomBackLeft.setXRot(this.lerpTo(theLegBottomBackLeft.getXRot(), 0.0F));
        theLegBottomBackRight.setXRot(this.lerpTo(theLegBottomBackRight.getXRot(), 0.0F));
    }

    private void standing() {
        theCat.lerpZ(0.0F);
        theCat.lerpY(8.0F);
        theBodyBack.lerpXRot(0.0F);
        theBodyFront.lerpXRot(0.0F);
        theTail.setXRot(Mth.HALF_PI*-0.5F);
    }

    /**
     *  TODO
     *  should return true if they have sat down,
     *  sit the cat down and return false otherwise
     */
    private boolean sitting() {
        theCat.lerpZ(5.0F);
        theCat.lerpY(15.0F);
        theBodyBack.lerpXRot(-Mth.HALF_PI*0.9F);
        theBodyFront.lerpXRot(Mth.HALF_PI*0.65F);
        theLegFrontLeft.lerpXRot(Mth.HALF_PI*0.25F);
        theLegFrontRight.lerpXRot(Mth.HALF_PI*0.25F);
        theLegBackLeft.lerpXRot(Mth.HALF_PI*-0.5F);
        theLegBackRight.lerpXRot(Mth.HALF_PI*-0.5F);
        theTail.lerpXRot(Mth.HALF_PI*0.25F);
        return false;
    }

    /**
     *  TODO
     *  should return true if they have laid down,
     *  lay the cat down and return false otherwise
     */
    private boolean layDownAnimation() {
        return true;
    }

    private void headLookingAnimation(float netHeadYaw, float headPitch) {
        netHeadYaw = netHeadYaw * 0.017453292F * 0.5F;
        headPitch = headPitch * 0.017453292F * 0.5F;

        float posture = 1.0F;
        if (theBodyBack.getXRot() != 0.0F || theBodyFront.getXRot() != 0.0F) {
            theNeck.lerpXRot(-posture + (headPitch - (theBodyBack.getXRot() + theBodyFront.getXRot())));
        } else {
            theNeck.lerpXRot(-posture + headPitch);
        }
        theNeck.lerpYRot(netHeadYaw);
        theHead.lerpXRot(posture + headPitch);
        theHead.lerpYRot(this.lerpTo(theHead.getYRot(),netHeadYaw));
    }

    private void articulate() {
        if (theLegBackLeft.getXRot()>=0.0F) {
            theLegBackLeft.setY(theLegBackLeft.getXRot() == 0.0F ? 0.0F : 2.5465F * theLegBackLeft.getXRot());
            theLegBackLeft.setZ(6.0F);
        } else {
            theLegBackLeft.setZ(6.0F - (theLegBackLeft.getXRot() * 0.3183F));

        }
        theLegBackRight.setY(theLegBackRight.getXRot()<=0.0F?0.0F:2.5465F*theLegBackRight.getXRot());
    }

    private CatModelData getCreateCatModelData(T enhancedCat) {
        return (CatModelData) getCreateAnimalModelData(enhancedCat);
    }

    @Override
    protected void setInitialModelData(T enhancedCat) {
        CatModelData catModelData = new CatModelData();
        setBaseInitialModelData(catModelData, enhancedCat);
    }

    @Override
    protected void additionalUpdateModelDataInfo(AnimalModelData animalModelData, T enhancedAnimal) {
        animalModelData.saddle = getSaddle(enhancedAnimal.getEnhancedInventory());
    }

    @Override
    protected Phenotype createPhenotype(T enhancedAnimal) {
        return new CatPhenotype(enhancedAnimal.getSharedGenes().getAutosomalGenes(), enhancedAnimal.getStringUUID().charAt(1));
    }


}
