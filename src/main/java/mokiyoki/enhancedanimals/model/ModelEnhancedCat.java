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
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private static WrappedModelPart cheekFluffLeft;
    private static WrappedModelPart cheekFluffRight;
    private static WrappedModelPart eyeL[] = new WrappedModelPart[3];
    private static WrappedModelPart eyeR[] = new WrappedModelPart[3];
    private static WrappedModelPart earL[] = new WrappedModelPart[4];
    private static WrappedModelPart earFluffL;
    private static WrappedModelPart earR[] = new WrappedModelPart[4];
    private static WrappedModelPart earFluffR;
    private static WrappedModelPart mouth;
    private static WrappedModelPart bodyFront;
    private static WrappedModelPart bodyFurFront;
    private static WrappedModelPart neckRuff;
    private static WrappedModelPart bodyBack;
    private static WrappedModelPart bodyFurBack;
    private static WrappedModelPart legFrontLeft;
    private static WrappedModelPart legFrontRight;
    private static WrappedModelPart legBackLeft;
    private static WrappedModelPart legFurBackLeft;
    private static WrappedModelPart legBackRight;
    private static WrappedModelPart legFurBackRight;

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
    private static WrappedModelPart theTailBones[] = new WrappedModelPart[7];
    private static WrappedModelPart tailFur[] = new WrappedModelPart[7];

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition base = meshdefinition.getRoot().addOrReplaceChild("base", CubeListBuilder.create(), PartPose.offset(0.0F, 8.0F, 0.0F));
        base.addOrReplaceChild("bMouth", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 1.5F, -2.25F, -Mth.PI*0.027F, 0.0F, 0.0F));
        base.addOrReplaceChild("bSnout", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 1.0F, -4.5F, Mth.PI*0.527F, 0.0F, 0.0F));
        base.addOrReplaceChild("bHead", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -1.0F, -6.0F, Mth.PI*0.15F, 0.0F, 0.0F));
        base.addOrReplaceChild("bNeck", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 3.0F, -7.0F, -Mth.PI*0.15F, 0.0F, 0.0F));
        base.addOrReplaceChild("bEarL", CubeListBuilder.create(), PartPose.offsetAndRotation(1.25F, -1.5F, 0.5F, 0.0F, 0.0F, 0.0F));
        base.addOrReplaceChild("bEarR", CubeListBuilder.create(), PartPose.offsetAndRotation(-1.25F, -1.5F, 0.5F, 0.0F, 0.0F, 0.0F));
        base.addOrReplaceChild("bBodyF", CubeListBuilder.create(), PartPose.ZERO);
        base.addOrReplaceChild("bBodyFurF", CubeListBuilder.create(), PartPose.ZERO);
        base.addOrReplaceChild("bBodyB", CubeListBuilder.create(), PartPose.ZERO);
        base.addOrReplaceChild("bBodyFurB", CubeListBuilder.create(), PartPose.ZERO);
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
         *      Eyes
         */
        base.addOrReplaceChild("eyes", CubeListBuilder.create(), PartPose.offset(0.0F, 0.75F, -4.501F));
        base.addOrReplaceChild("eyeL0", CubeListBuilder.create()
                        .texOffs(4, 22)
                        .addBox(-2.0F, -2.0F, 0.0F, 4, 4, 0, new CubeDeformation(-1.0F, -1.0F, 0.0F)),
                PartPose.offset(1.5F, -0.25F, 0.0F)
        );
        base.addOrReplaceChild("eyeR0", CubeListBuilder.create()
                        .texOffs(0, 28)
                        .addBox(-2.0F, -2.0F, 0.0F, 4, 4, 0, new CubeDeformation(-1.0F, -1.0F, 0.0F)),
                PartPose.offset(-1.5F, -0.25F, 0.0F)
        );
        base.addOrReplaceChild("eyeL1", CubeListBuilder.create()
                        .texOffs(4, 19)
                        .addBox(-2.0F, -2.0F, -0.001F, 4, 4, 0, new CubeDeformation(-1.0F, -1.0F, 0.0F)),
                PartPose.ZERO
        );
        base.addOrReplaceChild("eyeR1", CubeListBuilder.create()
                        .texOffs(0, 25)
                        .addBox(-2.0F, -2.0F, -0.001F, 4, 4, 0, new CubeDeformation(-1.0F, -1.0F, 0.0F)),
                PartPose.ZERO
        );
        base.addOrReplaceChild("eyeL2", CubeListBuilder.create()
                        .texOffs(12, 19)
                        .addBox(-1.0F, -1.0F, -0.002F, 2, 2, 0, new CubeDeformation(-0.5F, -0.5F, 0.0F)),
                PartPose.ZERO
        );
        base.addOrReplaceChild("eyeR2", CubeListBuilder.create()
                        .texOffs(12, 21)
                        .addBox(-1.0F, -1.0F, -0.002F, 2, 2, 0, new CubeDeformation(-0.5F, -0.5F, 0.0F)),
                PartPose.ZERO
        );

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
                        .addBox(-1.0F, -2.0F, -1.025F, 1,2,1, new CubeDeformation(0.0F, 0.0F, 0.0F))
                        .texOffs(24, 1)
                        .addBox(-1.0F, -3.0F, -0.525F, 1,3,1, new CubeDeformation(0.0F, 0.0F, -0.45F)),
                PartPose.offsetAndRotation(0F,1F,0F, 0.0F, -Mth.PI*0.15F, 0.0F)
        );
        base.addOrReplaceChild("earL1", CubeListBuilder.create()
                        .texOffs(28, 0)
                        .addBox(0.0F, -3.0F, -0.525F, 1,4,1, new CubeDeformation(0.0F, 0.0F, -0.45F)),
                PartPose.ZERO
        );
        base.addOrReplaceChild("earL2", CubeListBuilder.create()
                        .texOffs(32, 1)
                        .addBox(0.0F, -3.0F, -0.5F, 1,3,1, new CubeDeformation(0.0F, 0.0F, -0.45F)),
                PartPose.offsetAndRotation(1.0F, 1.0F, 0.0F, 0.0F, Mth.PI*0.15F, 0.0F)
        );
        base.addOrReplaceChild("earL3", CubeListBuilder.create()
                        .texOffs(35, 4)
                        .addBox(0.0F, -0.005F, -1.025F, 1,1,1, new CubeDeformation(0.0F, 0.0F, 0.0F)),
                PartPose.offsetAndRotation(1.0F, 0.0F, 0.0F, 0.0F, Mth.PI*0.15F, 0.0F)
        );
        base.addOrReplaceChild("earFluffL", CubeListBuilder.create()
                        .texOffs(78, 125)
                        .addBox(0.0F, 0, 0, 3,3,0, new CubeDeformation(-0.25F, -0.25F, 0.0F)),
                PartPose.offsetAndRotation(0.0F, -3.0F, -0.25F, 0.0F, Mth.PI*0.15F, 0.0F)
        );

        base.addOrReplaceChild("earR0", CubeListBuilder.create()
                        .texOffs(16, 4)
                        .addBox(0.0F, -2.0F, -1.025F, 1,2,1, new CubeDeformation(0.0F, 0.0F, 0.0F))
                        .texOffs(13, 1)
                        .addBox(0.0F, -3.0F, -0.525F, 1,3,1, new CubeDeformation(0.0F, 0.0F, -0.45F)),
                PartPose.offsetAndRotation(0F,1F,0F, 0.0F, Mth.PI*0.15F, 0.0F)
        );
        base.addOrReplaceChild("earR1", CubeListBuilder.create()
                        .texOffs(9, 0)
                        .addBox(-1.0F, -3.0F, -0.525F, 1,4,1, new CubeDeformation(0.0F, 0.0F, -0.45F)),
                PartPose.ZERO
        );
        base.addOrReplaceChild("earR2", CubeListBuilder.create()
                        .texOffs(5, 1)
                        .addBox(-1.0F, -3F, -0.525F, 1,3,1, new CubeDeformation(0.0F, 0.0F, -0.45F)),
                     PartPose.offsetAndRotation(-1.0F, 1.0F, 0.0F, 0.0F, -Mth.PI*0.15F, 0.0F)
        );
        base.addOrReplaceChild("earR3", CubeListBuilder.create()
                        .texOffs(2, 4)
                        .addBox(-1.0F, -0.005F, -1.025F, 1,1,1, new CubeDeformation(0.0F, 0.0F, 0.0F)),
                PartPose.offsetAndRotation(-1.0F, 0.0F, 0.0F, 0.0F, -Mth.PI*0.15F, 0.0F)
        );
        base.addOrReplaceChild("earFluffR", CubeListBuilder.create()
                        .texOffs(72, 125)
                        .addBox(-3.0F, 0, 0, 3,3,0, new CubeDeformation(-0.25F, -0.25F, 0.0F)),
                PartPose.offsetAndRotation(0.0F, -3.0F, -0.25F, 0.0F, -Mth.PI*0.15F, 0.0F)
        );

        /**
         *      Head
         */
        base.addOrReplaceChild("head", CubeListBuilder.create()
                        .texOffs(10, 7)
                        .addBox(-3.0F, -2.0F, -5.0F, 6, 6, 6, deformation),
                PartPose.ZERO
        );

        base.addOrReplaceChild("cheekFluffL", CubeListBuilder.create()
                        .texOffs(64, 122)
                        .addBox(-0.5F, -2F, 0F,3, 6, 0, new CubeDeformation(-0.5F, -0.5F, 0F)),
                PartPose.offsetAndRotation(2.5F, 0.0F, (-5F/6F * 5)+0.5F, 0.0F, -Mth.PI*0.25F, 0.0F)
        );

        base.addOrReplaceChild("cheekFluffR", CubeListBuilder.create()
                        .texOffs(57, 122)
                        .addBox(-2.5F, -2F, 0F,3, 6, 0, new CubeDeformation(-0.5F, -0.5F, 0F)),
                PartPose.offsetAndRotation(-2.5F, 0.0F, (-5F/6F * 5)+0.5F, 0.0F, Mth.PI*0.25F, 0.0F)
        );

        /**
         *      Neck
         */
        base.addOrReplaceChild("neck", CubeListBuilder.create()
                        .texOffs(9, 20)
                        .addBox(-3.0F, -3.0F, -7.0F, 6, 6, 7, new CubeDeformation(-0.75F, -0.5F, -0.5F)),
                PartPose.ZERO
        );

        /**
         *      Body
         */
        base.addOrReplaceChild("bodyF", CubeListBuilder.create()
                        .texOffs(8, 33)
                        .addBox(-3.0F, 0F, -7.5F, 6, 10, 8, deformation),
                PartPose.offsetAndRotation(0F, 0F, -9.5F, Mth.HALF_PI, 0.0F, 0.0F)
        );
        base.addOrReplaceChild("bodyFurF", CubeListBuilder.create()
                        .texOffs(5, 115)
                        .addBox(-3.0F, 0F, -10F, 6, 10, 3,  new CubeDeformation(-0.5F, -0.5F, 0.0F)),
                PartPose.ZERO
        );
        base.addOrReplaceChild("neckRuff", CubeListBuilder.create()
                        .texOffs(56, 117)
                        .addBox(-4.0F, 0.0F, -5.0F, 8, 0, 5, new CubeDeformation(-0.5F, 0F, -0.5F)),
                PartPose.offsetAndRotation(0F, 0.5F, -3.5F, Mth.HALF_PI*-0.15F, 0.0F, 0.0F)
        );
        base.addOrReplaceChild("bodyB", CubeListBuilder.create()
                        .texOffs(36, 0)
                        .addBox(-3.0F, -6.5F, -7.5F, 6, 7, 8, deformation)
                        .texOffs(29, 15)
                        .addBox(-3.0F, -0.5F, -4.55F, 6, 4, 5, deformation)
                ,
                PartPose.offsetAndRotation(0F,0F, 6.0F, Mth.HALF_PI, 0.0F, 0.0F)
        );
        base.addOrReplaceChild("bodyFurB", CubeListBuilder.create()
                        .texOffs(33, 118)
                        .addBox(-3.0F, -6.5F, -10F, 6, 7, 3,  new CubeDeformation(-0.5F, -0.5F, 0.0F)),
                PartPose.ZERO
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
                        .addBox(-1.5F, 0.0F, 0.0F, 3, 8, 4),
                PartPose.ZERO
        );
        base.addOrReplaceChild("legFurBL", CubeListBuilder.create()
                        .texOffs(105, 110)
                        .addBox(-1.5F, 8.0F, 0.0F, 0, 2, 4),
                PartPose.ZERO
        );
        base.addOrReplaceChild("legBR", CubeListBuilder.create()
                        .texOffs(50, 42)
                        .addBox(-1.5F, 0.0F, 0.0F, 3, 8, 4),
                PartPose.ZERO
        );
        base.addOrReplaceChild("legFurBR", CubeListBuilder.create()
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
            base.addOrReplaceChild("bTail"+i, CubeListBuilder.create(),
                    PartPose.offset(0.0F, i == 0 ? 0.0F : -2.0F, i == 0 ? 1.0F : 0.0F)
            );
        }
        for (int i = 0; i < 7; i++) {
            base.addOrReplaceChild("tail"+i, CubeListBuilder.create()
                    .texOffs(0, 60-(i*4))
                    .addBox(-1.0F, -2.0F, -1.0F, 2, 2, 2),
                    //PartPose.offset(0.0F, i == 0 ? 0.0F : -2.0F, i == 0 ? 1.0F : 0.0F)
                    PartPose.ZERO
            );
        }
        for (int i = 0; i < 7; i++) {
            base.addOrReplaceChild("tailFur"+i, CubeListBuilder.create()
                            .texOffs(119, 124-(i*4))
                            .addBox(-1.0F, -2F, 1.0F, 2, 2, 2),
//                    PartPose.offset(0.0F, 0.0F, i == 0 ? 1.0F : 0.0F)
                    PartPose.ZERO
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

        eyes = new WrappedModelPart("eyes", base);
        for (int i = 0; i < eyeL.length; i++) {
            eyeL[i] = new WrappedModelPart("eyeL"+i, base);
            eyeR[i] = new WrappedModelPart("eyeR"+i, base);
            if (i == 0) {
                eyes.addChild(eyeL[0]);
                eyes.addChild(eyeR[0]);
            } else {
                eyeL[i-1].addChild(eyeL[i]);
                eyeR[i-1].addChild(eyeR[i]);
            }
        }

        mouth = new WrappedModelPart("mouth", base);
        snout = new WrappedModelPart("snout", base);
        for (int i = 0; i < 4; i++) {
            earL[i] = new WrappedModelPart("earL"+i, base);
            theEarL.addChild(earL[i]);
        }
        earFluffL = new WrappedModelPart("earFluffL", base);
        theEarL.addChild(earFluffL);

        for (int i = 0; i < 4; i++) {
            earR[i] = new WrappedModelPart("earR"+i, base);
            theEarR.addChild(earR[i]);
        }
        earFluffR = new WrappedModelPart("earFluffR", base);
        theEarR.addChild(earFluffR);

        head = new WrappedModelPart("head", base);
        neck = new WrappedModelPart("neck", base);
        nose = new WrappedModelPart("nose", base);

        cheekFluffLeft = new WrappedModelPart("cheekFluffL", base);
        cheekFluffRight = new WrappedModelPart("cheekFluffR", base);

        bodyFront = new WrappedModelPart("bodyF", base);
        bodyFurFront = new WrappedModelPart("bodyFurF", base);
        neckRuff = new WrappedModelPart("neckRuff", base);
        bodyBack = new WrappedModelPart("bodyB", base);
        bodyFurBack = new WrappedModelPart("bodyFurB", base);

        legFrontLeft = new WrappedModelPart("legFL", base);
        legFrontRight = new WrappedModelPart("legFR", base);
        legFrontLeftBottom = new WrappedModelPart("legBFL", base);
        legFrontRightBottom = new WrappedModelPart("legBFR", base);
        pawFrontLeft = new WrappedModelPart("pawFL", base);
        pawFrontRight = new WrappedModelPart("pawFR", base);

        toesFrontLeft = new WrappedModelPart("toesFL", base);
        toesFrontRight = new WrappedModelPart("toesFR", base);

        legBackLeft = new WrappedModelPart("legBL", base);
        legFurBackLeft = new WrappedModelPart("legFurBL", base);
        legBackRight = new WrappedModelPart("legBR", base);
        legFurBackRight = new WrappedModelPart("legFurBR", base);
        legBackLeftBottom = new WrappedModelPart("legBBL", base);
        legBackRightBottom = new WrappedModelPart("legBBR", base);
        pawBackLeft = new WrappedModelPart("pawBL", base);
        pawBackRight = new WrappedModelPart("pawBR", base);

        toesBackLeft = new WrappedModelPart("toesBL", base);
        toesBackRight = new WrappedModelPart("toesBR", base);

        for (int i = 0; i < 7; i++) {
            theTailBones[i] = new WrappedModelPart("bTail"+i, base);
            tail[i] = new WrappedModelPart("tail"+i, base);
            tailFur[i] = new WrappedModelPart("tailFur"+i, base);
            if (i>0) {
                theTailBones[i-1].addChild(theTailBones[i]);
            }
            theTailBones[i].addChild(tail[i]);
            tail[i].addChild(tailFur[i]);
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
        theHead.addChild(eyes);

        theHead.addChild(cheekFluffLeft);
        theHead.addChild(cheekFluffRight);

        theNeck.addChild(neck);

        theBodyFront.addChild(bodyFront);
        bodyFront.addChild(bodyFurFront);
        bodyFront.addChild(neckRuff);
        theBodyBack.addChild(bodyBack);
        bodyBack.addChild(bodyFurBack);

        theLegFrontLeft.addChild(legFrontLeft);
        theLegFrontRight.addChild(legFrontRight);

        theLegBottomFrontLeft.addChild(legFrontLeftBottom);
        theLegBottomFrontRight.addChild(legFrontRightBottom);

        thePawFrontLeft.addChild(pawFrontLeft);
        thePawFrontLeft.addChild(toesFrontLeft);

        thePawFrontRight.addChild(pawFrontRight);
        thePawFrontRight.addChild(toesFrontRight);

        theLegBackLeft.addChild(legBackLeft);
        legBackLeft.addChild(legFurBackLeft);
        theLegBackRight.addChild(legBackRight);
        legBackRight.addChild(legFurBackRight);

        theLegBottomBackLeft.addChild(legBackLeftBottom);
        theLegBottomBackRight.addChild(legBackRightBottom);

        thePawBackLeft.addChild(pawBackLeft);
        thePawBackLeft.addChild(toesBackLeft);

        thePawBackRight.addChild(pawBackRight);
        thePawBackRight.addChild(toesBackRight);

        theTail.addChild(theTailBones[0]);

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

//            System.out.println((packedLightIn & 0xFFFF) >> 4); //block light
//            System.out.println(packedLightIn >> 20 & '\uffff'); //sky light

            float dilation = 1.0F-(Math.max(((packedLightIn & 0xFFFF) >> 4)/15F, ((packedLightIn >> 20 & '\uffff')/15F) * (catModelData.light))*0.8F);

            float scale = 0.75F;
            mapOfScale.put("base", ModelHelper.createScalings(scale, 0.0F, 1.0F, 0.0F));
            mapOfScale.put("bNeck", ModelHelper.createScalings(1.01F, 0.0F, 0.0F, 0.0F));
            if (false /*TODO this makes the cat partially close its eyes for stuff like slow blink*/) {
                mapOfScale.put("eyes", ModelHelper.createScalings(1.0F, 0.5F/*TODO eye openness percentage*/, 1.0F, 0.0F, 0.0F, 0.0F));
            }
            if (dilation!=1.0F /*TODO this controls pupil dilation*/) {
                mapOfScale.put("eyeL2", ModelHelper.createScalings(2f*dilation, 1.0F, 1.0F, (1.0F-dilation)*-0.065F, 0.0F, 0.0F));
                mapOfScale.put("eyeR2", ModelHelper.createScalings(2f*dilation, 1.0F, 1.0F, (1.0F-dilation)*0.065F, 0.0F, 0.0F));
            }

            this.bodyFurFront.show(cat.furnishings);
            this.neckRuff.show(cat.furnishings);
            this.bodyFurBack.show(cat.furnishings);
            this.cheekFluffLeft.show(cat.furnishings);
            this.legFurBackLeft.show(cat.furnishings);
            this.legFurBackRight.show(cat.furnishings);
            this.earFluffL.show(cat.furnishings);
            this.earFluffR.show(cat.furnishings);
            this.cheekFluffRight.show(cat.furnishings);
            for (int i = 0; i < 7; i++) {
                this.tailFur[i].show(cat.furnishings);
            }

            this.tail[6].show(cat.bobtail == 0);
            this.tail[5].show(cat.bobtail < 2);
            this.tail[4].show(cat.bobtail < 2);
            this.tail[3].show(cat.bobtail < 3);
            this.tail[2].show(cat.bobtail < 3);
            this.tail[1].show(cat.bobtail < 3);
            this.tail[0].show(cat.bobtail < 4);

            boolean isCobby = cat.isCobby;
            float eyesWidth = Math.min(cat.headWidth, 1F);
            float upperLegScale = 1F + cat.furLength*0.35F;
            float upperLegHeight = 1F + cat.furLength*0.1F;
            float eyeWidth = (1F - (cat.eyeRoundness > 0F ? cat.eyeRoundness * 0.2F : 0F)) * (cat.eyeSize);
            float eyeThickness = (1F + (cat.eyeRoundness * 0.275F)) * (cat.eyeSize);
            float earLength = 1.0F;
            float ear1Scale = -(cat.earRoundness*0.2F); //min 0.8
            float earSize = 1+(cat.earSize*0.25F);
            float eyeSize = 1F;
            float earSpacing = 1F;
            float[] tailFluffScales = {0F, 0.05F, 0.05F, 0.1F, 0.1F, 0.02F, 0.0F};
            mapOfScale.put("bHead", ModelHelper.createScalings(cat.headSize, 0F,0F,0F));
            mapOfScale.put("head", ModelHelper.createScalings(cat.headWidth, 1F, 1F, 0F,0F,0F));
            mapOfScale.put("eyes", ModelHelper.createScalings(eyesWidth, 1F, 1F, 0F,0F,0F));
            mapOfScale.put("bSnout", ModelHelper.createScalings(cat.snoutWidth*cat.snoutScale, cat.snoutLength*cat.snoutScale, cat.snoutScale, 0F,0F,0F));
            //mapOfScale.put("mouth", ModelHelper.createScalings(cat.jawScale, 1F, cat.jawScale, 0F,0F,0F));
            mapOfScale.put("neck", ModelHelper.createScalings(cat.neckWidth, cat.neckHeight, 1.0F, 0F,0F,0F));
            List<Float> legScalings = ModelHelper.createScalings(cat.lowerLegScale, 1F, cat.lowerLegScale, 0F,0F,0F);
            mapOfScale.put("legBFL", legScalings);
            mapOfScale.put("legBBL", legScalings);
            mapOfScale.put("bPawFL", legScalings);
            mapOfScale.put("bPawBL", legScalings);
            mapOfScale.put("legBFR", legScalings);
            mapOfScale.put("legBBR", legScalings);
            mapOfScale.put("bPawFR", legScalings);
            mapOfScale.put("bPawBR", legScalings);
            mapOfScale.put("legFL", legScalings);
            mapOfScale.put("legFR", legScalings);
            List<Float> thighScalings = ModelHelper.createScalings(upperLegScale+(cat.bodyWidth*0.75F), upperLegHeight, upperLegScale+(cat.bodyWidth*0.1F), 0F, 0F, 0F);
            mapOfScale.put("legBL", thighScalings);
            mapOfScale.put("legBR", thighScalings);
            List<Float> bodyScalingF = ModelHelper.createScalings(1F+cat.bodyWidth, cat.bodyLength, 1F, 0F, 0F, 0F);
            List<Float> bodyScalingB = ModelHelper.createScalings(1F+cat.bodyWidth, cat.bodyLength, 1F, 0F, 0F, 0F);
            mapOfScale.put("bodyF", bodyScalingF);
            //mapOfScale.put("bodyFurF", bodyScalingF);
            mapOfScale.put("bodyB", bodyScalingB);
            //mapOfScale.put("bodyFurB", bodyScalingB);
            List<Float> eye0Scaling = ModelHelper.createScalings(eyeWidth * eyeSize, eyeThickness, eyeSize, 0F, 0F, 0F);
            mapOfScale.put("eyeL0", eye0Scaling);
            mapOfScale.put("eyeR0", eye0Scaling);
            List<Float> bEarTransform = ModelHelper.createScalings(earSize, 0F, 0F,0F);
            List<Float> earTransform = ModelHelper.createScalings(1F, earLength, 1F, 0F, 0F,0F);
            List<Float> ear1Transform = ModelHelper.createScalings(1F, earLength+ear1Scale, 1F, 0F, 0F,0F);
            List<Float> ear2Transform = ModelHelper.createScalings(1F+cat.earFlare, earLength, 1F, 0F, 0F,0F);
            mapOfScale.put("bEarL", bEarTransform);
            mapOfScale.put("bEarR", bEarTransform);
            mapOfScale.put("earL0", earTransform);
            mapOfScale.put("earL1", ear1Transform);
            mapOfScale.put("earL2", ear2Transform);
            mapOfScale.put("earL3", ear2Transform);
            mapOfScale.put("earR0", earTransform);
            mapOfScale.put("earR1", ear1Transform);
            mapOfScale.put("earR2", ear2Transform);
            mapOfScale.put("earR3", ear2Transform);

            for (int i = 0; i < 7; i++) {
                mapOfScale.put("tail"+i, ModelHelper.createScalings(cat.tailThickness + (cat.furLength*tailFluffScales[i]), 1F + (cat.furLength*tailFluffScales[i]), cat.tailThickness + (cat.furLength*tailFluffScales[i]), 0F,0F,0F));
            }

            poseStack.pushPose();
            poseStack.scale(scale, scale, scale);
            poseStack.translate(0.0F, 0.0F, 0.0F);

            gaRender(theCat, mapOfScale, poseStack, vertexConsumer, packedLightIn, packedOverlayIn, red, green, blue, alpha);

            poseStack.popPose();
        }
    }

    protected void saveAnimationValues(CatModelData data) {
        Map<String, Vector3f> map = data.offsets;
        map.put("bSnoutPos", this.getPosVector(theSnout));
        map.put("bLegBL", this.getRotationVector(theLegBackLeft));
        map.put("bLegBLPos", this.getPosVector(theLegBackLeft));
        map.put("bLegBRPos", this.getPosVector(theLegBackRight));
        map.put("bLegFLPos", this.getPosVector(theLegFrontLeft));
        map.put("bLegFRPos", this.getPosVector(theLegFrontRight));
        map.put("bLegBR", this.getRotationVector(theLegBackRight));
        map.put("bLegBBL", this.getRotationVector(theLegBottomBackLeft));
        map.put("bLegBBR", this.getRotationVector(theLegBottomBackRight));
        map.put("bTail", this.getRotationVector(theTail));
        map.put("bEarLPos", this.getPosVector(theEarL));
        map.put("bEarRPos", this.getPosVector(theEarR));
        map.put("bBodyFPos", this.getPosVector(theBodyFront));
        map.put("cheekL", this.getPosVector(cheekFluffLeft));
        map.put("cheekR", this.getPosVector(cheekFluffRight));
        map.put("bEarLRot", this.getRotationVector(theEarL));
        map.put("bEarRRot", this.getRotationVector(theEarR));
        for (int i = 0; i < 7; i++) {
            map.put("bTail"+i, this.getRotationVector(theTailBones[i]));
        }
    }
    private void setupInitialAnimationValues(CatModelData data) {
        Map<String, Vector3f> map = data.offsets;
        if (map.isEmpty()) {
            CatPhenotype cat = catModelData.getPhenotype();
            float earAngle = (Mth.HALF_PI*1.1F*cat.earSpacing);
            theSnout.setZ(-3.5F);
            theLegBackLeft.setXRot(Mth.PI*-0.05F);
            theLegBackRight.setXRot(Mth.PI*-0.05F);
            theTail.setXRot(Mth.HALF_PI*-0.5F);
            theTailBones[3].setXRot(Mth.HALF_PI*0.5F);
            theTailBones[6].setXRot(Mth.HALF_PI*-0.5F);
            theBodyFront.setZ((9.25F*(1F-cat.bodyLength)) + (6.25F*(1F-cat.bodyLength)));
            theLegBackLeft.setX(-1.5F - (cat.bodyType*(cat.isCobby ? 0.375F : 0.075F)*1.5F));
            theLegBackRight.setX(1.5F + (cat.bodyType*(cat.isCobby ? 0.375F : 0.075F)*1.5F));
            theLegFrontLeft.setX(-1.5F - (cat.bodyType*(cat.isCobby ? 0.375F : 0.075F)*1.5F));
            theLegFrontRight.setX(1.5F + (cat.bodyType*(cat.isCobby ? 0.375F : 0.075F)*1.5F));
            theEarL.setX((cat.headWidth*1.25F)+(cat.earSpacing*1.25F*cat.headWidth)+(Mth.sin(earAngle)*0.5F));
            theEarR.setX((cat.headWidth*-1.25F)-(cat.earSpacing*1.25F*cat.headWidth)-(Mth.sin(earAngle)*0.5F));
            theEarL.setY(-1.5F+(1.5F*cat.earSpacing)-(Mth.sin(earAngle)*0.75F));
            theEarR.setY(-1.5F+(1.5F*cat.earSpacing)-(Mth.sin(earAngle)*0.75F));
            theEarL.setZRot(earAngle);
            theEarR.setZRot(-earAngle);
            cheekFluffLeft.setX(2.5F*cat.headWidth);
            cheekFluffRight.setX(-2.5F*cat.headWidth);
        } else {
            theSnout.setPos(map.get("bSnoutPos"));
            theLegBackLeft.setRotation(map.get("bLegBL"));
            theLegBackRight.setRotation(map.get("bLegBR"));
            theLegBackLeft.setPos(map.get("bLegBLPos"));
            theLegBackRight.setPos(map.get("bLegBRPos"));
            theLegFrontLeft.setPos(map.get("bLegFLPos"));
            theLegFrontRight.setPos(map.get("bLegFRPos"));
            theBodyFront.setPos(map.get("bBodyFPos"));
            theLegBottomBackLeft.setRotation(map.get("bLegBBL"));
            theLegBottomBackRight.setRotation(map.get("bLegBBR"));
            theTail.setRotation(map.get("bTail"));
            theEarL.setPos(map.get("bEarLPos"));
            theEarR.setPos(map.get("bEarRPos"));
            cheekFluffLeft.setPos(map.get("cheekL"));
            cheekFluffRight.setPos(map.get("cheekR"));
            theEarL.setRotation(map.get("bEarLRot"));
            theEarR.setRotation(map.get("bEarRRot"));
            for (int i = 1; i < 7; i++) {
                theTailBones[i].setRotation(map.get("bTail"+i));
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
                    if (entityIn.isOrderedToSit()) {
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
        theLegFrontLeft.lerpXRot(0.0F);
        theLegFrontRight.lerpXRot(0.0F);

        theLegBackLeft.lerpXRot(0.0F);
        theLegBackRight.lerpXRot(0.0F);

        theLegBottomFrontLeft.lerpXRot(0.0F);
        theLegBottomFrontRight.lerpXRot(0.0F);

        theLegBottomBackLeft.lerpXRot(0.0F);
        theLegBottomBackRight.lerpXRot(0.0F);
    }
    private void legsWalking(float limbSwing, float limbSwingAmount) {
        float f = (Mth.cos(limbSwing * 0.6662F)) * 1.4F * limbSwingAmount;
        float f1 = (Mth.cos(limbSwing * 0.6662F + Mth.PI) * 1.4F * limbSwingAmount);

        theLegFrontLeft.setXRot(f);
        theLegFrontRight.setXRot(f1);
        theLegBackLeft.setXRot(f);
        theLegBackRight.setXRot(f1);

        theLegBottomBackLeft.lerpXRot(0.0F);
        theLegBottomBackRight.lerpXRot(0.0F);
    }

    private void standing() {
        theCat.lerpZ(0.0F);
        theCat.lerpY(8.0F);
        theBodyBack.lerpXRot(0.0F);
        theBodyFront.lerpXRot(0.0F);
        theLegFrontLeft.lerpZ(-7.0F);
        theLegFrontRight.lerpZ(-7.0F);
        theLegBottomBackLeft.lerpXRot(Mth.HALF_PI*0.25F);
        theLegBottomBackRight.lerpXRot(Mth.HALF_PI*0.25F);
        theTail.setXRot(Mth.HALF_PI*-0.5F);
        theLegBottomBackLeft.lerpXRot(0.0F);
        theLegBottomBackRight.lerpXRot(0.0F);
        theLegBottomBackLeft.lerpZ(4.0F);
        theLegBottomBackRight.lerpZ(4.0F);
        theLegBottomBackLeft.lerpY(8.0F);
        theLegBottomBackRight.lerpY(8.0F);
    }

    /**
     *  TODO
     *  should return true if they have sat down,
     *  sit the cat down and return false otherwise
     */
    private boolean sitting() {
        theCat.lerpZ(5.0F);
        theCat.lerpY(12.0F);
        theBodyBack.lerpXRot(-Mth.HALF_PI*0.9F);
        theBodyFront.lerpXRot(Mth.HALF_PI*0.5F);
        theLegFrontLeft.lerpXRot(Mth.HALF_PI*0.25F);
        theLegFrontRight.lerpXRot(Mth.HALF_PI*0.25F);
        theLegFrontLeft.lerpZ(-6.0F);
        theLegFrontRight.lerpZ(-6.0F);
        theLegBackLeft.lerpXRot(Mth.HALF_PI*-0.5F);
        theLegBackRight.lerpXRot(Mth.HALF_PI*-0.5F);
        theLegBottomBackLeft.lerpXRot(Mth.HALF_PI*0.4F);
        theLegBottomBackRight.lerpXRot(Mth.HALF_PI*0.4F);
        theLegBottomBackLeft.lerpZ(4.0F);
        theLegBottomBackRight.lerpZ(4.0F);
        theLegBottomBackLeft.lerpY(-1.0F);
        theLegBottomBackRight.lerpY(-1.0F);
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
        netHeadYaw *= 0.017453292F;
        headPitch *= 0.017453292F;
        float yaw = netHeadYaw * 0.5F;
        float pitch = headPitch * 0.5F;

        float posture = 1.0F;
        if (theBodyBack.getXRot() != 0.0F || theBodyFront.getXRot() != 0.0F) {
            theNeck.lerpXRot(-posture + (pitch - (theBodyBack.getXRot() + theBodyFront.getXRot())));
        } else {
            theNeck.lerpXRot(-posture + pitch);
        }
        theNeck.lerpYRot(yaw);
        theHead.lerpXRot(posture + pitch);
        theHead.lerpYRot(this.lerpTo(theHead.getYRot(),yaw));

        if (yaw < 0.5F) {
            if (yaw > -0.5F) {
                eyeL[1].lerpX(0.0F);
                eyeL[2].lerpX(0.0F);
                eyeR[1].lerpX(0.0F);
                eyeR[2].lerpX(0.0F);
            } else {
                eyeL[1].lerpX(0.5F);
                eyeR[2].lerpX(0.5F);
            }
        } else {
            eyeR[1].lerpX(-0.5F);
            eyeL[2].lerpX(-0.5F);
        }
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
        if (animalModelData.sleepDelay!=0 && animalModelData.clientGameTime%200==0) {
            ((CatModelData) animalModelData).light = getSkyLight(enhancedAnimal.getLevel());
        }
    }

    private float getSkyLight(Level level) {
        float light = 0.0F;
        if (level.dimensionType().hasSkyLight()) {
            float time = level.dayTime();
            if (time > 23000) {
                light = (time-23000) * 0.0006F;
            } else if (time < 1000) {
                light = time * 0.0003F;
            } else if (time < 11500 ) {
                light = 1.0F;
            } else if (time < 13000) {
                light = (time-11500)/1500F;
            }
        }
        if (level.isThundering()) {
            light *= 0.3F;
        } else if (level.isRaining()) {
            light *= 0.6F;
        }
        return light;
    }


    @Override
    protected Phenotype createPhenotype(T enhancedAnimal) {
        return new CatPhenotype(enhancedAnimal.getSharedGenes().getAutosomalGenes(), enhancedAnimal.getStringUUID().charAt(5));
    }
}
