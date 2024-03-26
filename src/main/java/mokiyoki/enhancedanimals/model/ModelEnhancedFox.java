package mokiyoki.enhancedanimals.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import mokiyoki.enhancedanimals.entity.EnhancedFox;
import mokiyoki.enhancedanimals.model.modeldata.AnimalModelData;
import mokiyoki.enhancedanimals.model.modeldata.Phenotype;
import mokiyoki.enhancedanimals.model.modeldata.FoxModelData;
import mokiyoki.enhancedanimals.model.modeldata.FoxPhenotype;
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

@OnlyIn(Dist.CLIENT)
public class ModelEnhancedFox<T extends EnhancedFox> extends EnhancedAnimalModel<T> {

    private static WrappedModelPart theFox;

    private static WrappedModelPart theHead;
    private static WrappedModelPart theSnout;
    private static WrappedModelPart theMouth;
    private static WrappedModelPart theEarLeft;
    private static WrappedModelPart theEarRight;
    private static WrappedModelPart theNeck;
    private static WrappedModelPart theBody;
    private static WrappedModelPart theLegFrontLeft;
    private static WrappedModelPart theLegFrontRight;
    private static WrappedModelPart theLegBackLeft;
    private static WrappedModelPart theLegBackRight;
    private static WrappedModelPart theTail;
    private static WrappedModelPart skull;
    private static WrappedModelPart nose;
    private static WrappedModelPart jaw;
    private static WrappedModelPart earL;
    private static WrappedModelPart earR;
    private static WrappedModelPart neck;
    private static WrappedModelPart body;
    private static WrappedModelPart legFrontLeft;
    private static WrappedModelPart legFrontRight;
    private static WrappedModelPart legBackLeft;
    private static WrappedModelPart legBackRight;
    private static WrappedModelPart tail0;
    private static WrappedModelPart tail1;
    private static WrappedModelPart tail2;


    private FoxModelData foxModelData;

    // this part creates the bone groups
    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition base = meshdefinition.getRoot().addOrReplaceChild("base", CubeListBuilder.create(), PartPose.ZERO);
        base.addOrReplaceChild("bFox", CubeListBuilder.create(), PartPose.offset(0.0F, 13.5F, 0.0F));
        base.addOrReplaceChild("bBody", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        base.addOrReplaceChild("bNeck", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 3.0F, -7.25F, Mth.HALF_PI*0.55F, 0.0F, 0.0F));
        base.addOrReplaceChild("bHead", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -6.0F, 3.5F, Mth.HALF_PI*-0.55F, 0.0F, 0.0F));
        base.addOrReplaceChild("bSnout", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, -6.0F));
        base.addOrReplaceChild("bMouth", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        base.addOrReplaceChild("bEarL", CubeListBuilder.create(), PartPose.offsetAndRotation(1.5F, -1.3F, -0.8F, 0.0F, 0.0F, Mth.HALF_PI*0.5F));  //
        base.addOrReplaceChild("bEarR", CubeListBuilder.create(), PartPose.offsetAndRotation(-1.5F, -1.3F, -0.8F, 0.0F, 0.0F, Mth.HALF_PI*-0.5F));
        base.addOrReplaceChild("bLegFL", CubeListBuilder.create(), PartPose.offset(2.25F, 3.0F, -7.25F));
        base.addOrReplaceChild("bLegFR", CubeListBuilder.create(), PartPose.offset(-2.25F, 3.0F, -7.25F));
        base.addOrReplaceChild("bLegBL", CubeListBuilder.create(), PartPose.offset(-2.25F, 3.0F, 5.0F));
        base.addOrReplaceChild("bLegBR", CubeListBuilder.create(), PartPose.offset(2.25F, 3.0F, 5.0F));
        base.addOrReplaceChild("bTail", CubeListBuilder.create(), PartPose.offset(0.0F, -0.5F, 6.5F));

// THE EYES
        base.addOrReplaceChild("eyes", CubeListBuilder.create()
                        .texOffs(58, 55)
                        .addBox(1.5F, 0.0F, 0.0F, 2, 1, 1, new CubeDeformation(0.01F))  // left
                        .texOffs(57, 58)
                        .addBox(-3.5F, 0.0F, 0.0F, 2, 1, 1, new CubeDeformation(0.01F))  // right
                        .texOffs(59, 50)
                        .addBox(1.5F, 0.0F, -0.05F, 1, 1, 0, new CubeDeformation(0.01F))  // PUPIL left
                        .texOffs(59, 52)
                        .addBox(-2.5F, 0.0F, -0.05F, 1, 1, 0, new CubeDeformation(0.01F)),  // PUPIL right
                PartPose.offset(0.0F, 0.0F, -6.0F)
        );

//HEAD
        base.addOrReplaceChild("skull", CubeListBuilder.create()
                        .texOffs(24, 49)
                        .addBox(-3.5F, -2.0F, -6.0F, 7, 6, 6), // first three = block offset or pivot point, second set = box dimensions
                PartPose.ZERO  // x, y, z
        );

        base.addOrReplaceChild("nose", CubeListBuilder.create()
                        .texOffs(12, 45)
                        .addBox(-1.5F, 0.5F, -4.0F, 3, 3, 4),
                PartPose.offset(0.0F, 0.0F, 0.0F)
        );

        base.addOrReplaceChild("jaw", CubeListBuilder.create()
                        .texOffs(42, 54)
                        .addBox(-1.5F, 2.5F, -7.0F, 3, 2, 8, new CubeDeformation(-0.25F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 3.5F, -Mth.HALF_PI*0.05F, 0.0F, 0.0F)
        );

// EARS
        base.addOrReplaceChild("earL", CubeListBuilder.create()
                        .texOffs(13, 1)
                        .addBox(-1.0F, -4.0F, -1.0F, 3, 4, 2)
                        .texOffs(1, 57)
                        .addBox(2.0F, -2.0F, -1.0F, 1, 2, 2), // lower
                PartPose.ZERO
        );

        base.addOrReplaceChild("earR", CubeListBuilder.create()
                        .texOffs(1, 1)
                        .addBox(-2.0F, -4.0F, -1.0F, 3, 4, 2)
                        .texOffs(1, 52)
                        .addBox(-3.0F, -2.0F, -1.0F, 1, 2, 2), // lower - move
                PartPose.ZERO
        );


// NECK
        base.addOrReplaceChild("neck", CubeListBuilder.create()
                        .texOffs(2, 10)
                        .addBox(-3.5F, -7.0F, -1.0F, 7, 9, 7, new CubeDeformation(0.1F)), //0.01F
                PartPose.ZERO
        );


// BODY
        base.addOrReplaceChild("body1", CubeListBuilder.create()
                        .texOffs(36, 27)
                        .addBox(-3.0F, -6.5F, -3.5F, 6, 13, 7), // first three are pivot point, second set is box dimensions
                PartPose.rotation(Mth.HALF_PI, 0.0F, 0.0F) // prev .offset(-3.0F, -6.0F, 3.0F)   // x , y , z
        );


// LEGS
        base.addOrReplaceChild("legFL", CubeListBuilder.create()  //switch with FR?
                        .texOffs(46, 0)
                        .addBox(-1.0F, 0.0F, 0.0F, 2, 8, 2),
                PartPose.ZERO
        );
        base.addOrReplaceChild("legFR", CubeListBuilder.create()  //switch with FL?
                        .texOffs(55, 0)
                        .addBox(-1.0F, 0.0F, 0.0F, 2, 8, 2),
                PartPose.ZERO
        );
        base.addOrReplaceChild("legBL", CubeListBuilder.create()
                        .texOffs(27, 0)
                        .addBox(-1.0F, 0.0F, 0.0F, 2, 8, 2),
                PartPose.offset(0.0F, -0.5F, 0.0F)
        );
        base.addOrReplaceChild("legBR", CubeListBuilder.create()
                        .texOffs(36, 0)
                        .addBox(-1.0F, 0.0F, 0.0F, 2, 8, 2),
                PartPose.offset(0.0F, -0.5F, 0.0F)
        );

// TAIL
        base.addOrReplaceChild("tail0", CubeListBuilder.create()
                        .texOffs(3, 29)
                        .addBox(-2.5F, -6.0F, -2.5F, 5, 6, 5),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F,-Mth.HALF_PI, 0.0F, 0.0F)
        );
        base.addOrReplaceChild("tail1", CubeListBuilder.create()
                        .texOffs(41, 13)
                        .addBox(-2.5F, -6.0F, -2.5F, 5, 6, 5),
                PartPose.offset(0.0F, -6.0F, 0.0F)
        );
        base.addOrReplaceChild("tail2", CubeListBuilder.create()
                        .texOffs(32, 27)  // TEXTURE OFFSET
                        .addBox(-1.5F, -2.0F, -0.0F, 3, 4, 2), //addbox = PIVOT or BLOCK OFFSET F, SIZE XYZ
                PartPose.offsetAndRotation(0.0F, -6.0F, 0.0F, Mth.HALF_PI, 0.0F, 0.0F)
        );

        // PartPose.offset for just position offset
        // PartPose.rotation if you need rotation and not position offset
        // PartPose.offsetAndRotation for both rotation and position offset


// Equipment stuff

        base.addOrReplaceChild("collar", CubeListBuilder.create()  // need to fix texture offset or dimensions
                        .texOffs(0, 54)  // main
                        .addBox(-4.0F, -5.5F, 0.0F, 8, 2, 8, new CubeDeformation(-0.3F))
                        .texOffs(24, 38)  // ring
                        .addBox(0.0F, -4.5F, -2.0F, 0, 3, 3, new CubeDeformation(-0.4F))
                        .texOffs(1, 42)  // bell
                        .addBox(-1.5F, -4.5F, -3.25F, 3, 3, 3, new CubeDeformation(-0.6F)),
                PartPose.offset(0.0F, 1.0F, -1.5F)
        );

        return LayerDefinition.create(meshdefinition, 64, 64);    // texture size 64x64
    }


    // PARENTING

    public ModelEnhancedFox(ModelPart modelPart) {
        super(modelPart);
        ModelPart base = modelPart.getChild("base");

        theFox = new WrappedModelPart("bFox", base);
        theBody = new WrappedModelPart("bBody", base);
        theNeck = new WrappedModelPart("bNeck", base);
        theHead = new WrappedModelPart("bHead", base);
        theSnout = new WrappedModelPart("bSnout", base);
        theMouth = new WrappedModelPart("bMouth", base);
        theEarLeft = new WrappedModelPart("bEarL", base);
        theEarRight = new WrappedModelPart("bEarR", base);
        theLegFrontLeft = new WrappedModelPart("bLegFL", base);
        theLegFrontRight = new WrappedModelPart("bLegFR", base);
        theLegBackLeft = new WrappedModelPart("bLegBL", base);
        theLegBackRight = new WrappedModelPart("bLegBR", base);
        theTail = new WrappedModelPart("bTail", base);

        /**
         *
         */

        this.eyes = new WrappedModelPart("eyes", base);

        skull = new WrappedModelPart("skull", base);
        nose = new WrappedModelPart("nose", base);
        jaw = new WrappedModelPart("jaw", base);

        earL = new WrappedModelPart("earL", base);
        earR = new WrappedModelPart("earR", base);

        neck = new WrappedModelPart("neck", base);

        body = new WrappedModelPart("body1", base);

        legFrontLeft = new WrappedModelPart("legFL", base);
        legFrontRight = new WrappedModelPart("legFR", base);
        legBackLeft = new WrappedModelPart("legBL", base);
        legBackRight = new WrappedModelPart("legBR", base);

        tail0 = new WrappedModelPart("tail0", base);
        tail1 = new WrappedModelPart("tail1", base);
        tail2 = new WrappedModelPart("tail2", base);

        theFox.addChild(theBody);
        theBody.addChild(theNeck);
        theBody.addChild(theTail);
        theNeck.addChild(theHead);
        theHead.addChild(theSnout);
        theSnout.addChild(theMouth);
        theHead.addChild(theEarLeft);
        theHead.addChild(theEarRight);
        theFox.addChild(theLegFrontLeft);
        theFox.addChild(theLegFrontRight);
        theFox.addChild(theLegBackLeft);
        theFox.addChild(theLegBackRight);

        theHead.addChild(skull);
        theHead.addChild(eyes);

        theEarLeft.addChild(earL);

        theEarRight.addChild(earR);

        theSnout.addChild(nose);
        theMouth.addChild(jaw);

        theNeck.addChild(neck);

        theBody.addChild(body);

        theLegFrontLeft.addChild(legFrontLeft);

        theLegFrontRight.addChild(legFrontRight);

        theLegBackLeft.addChild(legBackLeft);

        theLegBackRight.addChild(legBackRight);

        theTail.addChild(tail0);
        tail0.addChild(tail1);
        tail1.addChild(tail2);

        /**
         *      Equipment
         */

        this.collar = new WrappedModelPart("collar", base);

        theNeck.addChild(this.collar);

    }

    private void resetCubes() {

    }


    // GENES


    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        if (this.foxModelData!=null && this.foxModelData.getPhenotype() != null) {
            FoxPhenotype fox = foxModelData.getPhenotype();

            resetCubes();
            super.renderToBuffer(this.foxModelData, poseStack, vertexConsumer, packedLightIn, packedOverlayIn, red, green, blue, alpha);

            Map<String, List<Float>> mapOfScale = new HashMap<>();

//            this.neck.show(fox.shape == 2);
//
//            this.body.show(fox.shape == 0);


            float finalFoxSize = ((3.0F * foxModelData.size * foxModelData.growthAmount) + foxModelData.size) / 4.0F;  // body part scaling

            poseStack.pushPose();
            poseStack.scale(finalFoxSize, finalFoxSize, finalFoxSize);
            poseStack.translate(0.0F, -1.5F + 1.5F / finalFoxSize, 0.0F);

            gaRender(theFox, mapOfScale, poseStack, vertexConsumer, packedLightIn, packedOverlayIn, red, green, blue, alpha);  //error here?

            poseStack.popPose();
        }
    }

// ANIMATION

    protected void saveAnimationValues(FoxModelData data, FoxPhenotype fox) {
        Map<String, Vector3f> map = data.offsets;
        map.put("bFox", this.getRotationVector(theFox));
        map.put("bFoxPos", this.getPosVector(theFox));
        map.put("nose", this.getRotationVector(nose));
        map.put("jaw", this.getRotationVector(jaw));
        map.put("bNeck", this.getRotationVector(theNeck));
        map.put("bHead", this.getRotationVector(theHead));
        if (data.growthAmount == 1.0F && !map.containsKey("bMouthPos")) {
            map.put("bMouthPos",this.getPosVector(theMouth));
        }
        map.put("bEarL", this.getRotationVector(theEarLeft));
        map.put("bEarR", this.getRotationVector(theEarRight));
        map.put("bEarLPos", this.getPosVector(theEarLeft));
        map.put("bEarRPos", this.getPosVector(theEarRight));
        map.put("bLegFL", this.getRotationVector(theLegFrontLeft));
        map.put("bLegFR", this.getRotationVector(theLegFrontRight));
        map.put("bLegBL", this.getRotationVector(theLegBackLeft));
        map.put("bLegBR", this.getRotationVector(theLegBackRight));
        map.put("tail0", this.getRotationVector(tail0));
        map.put("tail1", this.getRotationVector(tail1));
        map.put("tail2", this.getRotationVector(tail2));
    }

    private void readInitialAnimationValues(FoxModelData data, FoxPhenotype fox) {
        Map<String, Vector3f> map = data.offsets;
        if (map.isEmpty()) {
//            this.theMouth.setY((-4.0F-data.growthAmount) - ((2.5F + (2.5F * data.growthAmount)) ));
        } else {
//            this.theFox.setRotation(map.get("bFox"));
//            this.theFox.setPos(map.get("bFoxPos"));
//            this.theNeck.setRotation(map.get("bNeck"));
//            this.theHead.setRotation(map.get("bHead"));
//            if (map.containsKey("bMouthPos")) {
//                this.theMouth.setPos(map.get("bMouthPos"));
//            } else {
//                this.theMouth.setY((-4.0F-data.growthAmount) - ((2.5F + (2.5F * data.growthAmount)) ));
//            }
//            this.nose.setRotation(map.get("nose"));
//            this.jaw.setRotation(map.get("jaw"));
//            this.theEarLeft.setRotation(map.get("bEarL"));
//            this.theEarRight.setRotation(map.get("bEarR"));
//            this.theEarLeft.setPos(map.get("bEarLPos"));
//            this.theEarRight.setPos(map.get("bEarRPos"));
//            this.theLegFrontLeft.setRotation(map.get("bLegFL"));
//            this.theLegFrontRight.setRotation(map.get("bLegFR"));
//            this.theLegBackLeft.setRotation(map.get("bLegBL"));
//            this.theLegBackRight.setRotation(map.get("bLegBR"));
//            this.tail0.setRotation(map.get("tail0"));
//            this.tail1.setRotation(map.get("tail1"));
//            this.tail2.setRotation(map.get("tail2"));
        }
    }



    /**
     *      Animations
     */

    @Override
    public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.foxModelData = getCreateFoxModelData(entityIn);

        if (this.foxModelData!=null) {
            FoxPhenotype fox = this.foxModelData.getPhenotype();
            readInitialAnimationValues(this.foxModelData, fox);
            boolean isMoving = entityIn.getDeltaMovement().horizontalDistanceSqr() > 1.0E-7D || entityIn.xOld != entityIn.getX() || entityIn.zOld != entityIn.getZ();

            if (this.foxModelData.earTwitchTimer <= ageInTicks) {
                if (theEarLeft.getXRot() != 0F || theEarRight.getXRot() != 0F) {
                    theEarLeft.setXRot(lerpTo(0.1F, theEarLeft.getXRot(), 0.0F));
                    theEarRight.setXRot(lerpTo(0.1F,theEarRight.getXRot(), 0.0F));
                    theEarLeft.setYRot(lerpTo(0.15F, theEarLeft.getYRot(), 0.0F));
                    theEarRight.setYRot(lerpTo(0.15F, theEarRight.getYRot(), 0.0F));
                } else {
                    this.foxModelData.earTwitchSide = entityIn.getRandom().nextBoolean();
                    this.foxModelData.earTwitchTimer = (int) ageInTicks + (entityIn.getRandom().nextInt(this.foxModelData.sleeping ? 60 : 30) * 20) + 30;
                }
            } else if (this.foxModelData.earTwitchTimer <= ageInTicks + 30) {
                twitchEarAnimation(this.foxModelData.earTwitchSide, (int)ageInTicks);
            }

            if (this.foxModelData.sleeping && !isMoving) {
                layDownAnimation();
            } else {
                standUpAnimation();

                if (netHeadYaw == 0 && headPitch == 0) {
                    headDefault();
                } else {
                    headLookingAnimation(netHeadYaw, headPitch);
                }

                if (isMoving) {
                    walkingLegsAnimation(limbSwing, limbSwingAmount);
                } else {
                    standingLegsAnimation();
                }
            }

            saveAnimationValues(this.foxModelData, fox);
        }

    }

    private void layDownAnimation() {
    }

    private void standUpAnimation() {
    }

    private void headLookingAnimation(float netHeadYaw, float headPitch) {
        netHeadYaw = (netHeadYaw * 0.017453292F);
        headPitch = ((headPitch * 0.017453292F));
        float lookRotX = Math.min((headPitch * 0.65F), 0.0F);
        float lookRotZ = netHeadYaw * 0.2F;

        theNeck.lerpXRot((Mth.HALF_PI*0.55F) + (headPitch - lookRotX));
        theNeck.lerpYRot(limit(netHeadYaw - lookRotZ, Mth.HALF_PI * 0.75F));
        theHead.lerpXRot((-Mth.HALF_PI*0.55F) + lookRotX);
        theHead.lerpYRot(lookRotZ);
    }

    private void headDefault() {
        theNeck.lerpXRot(Mth.HALF_PI*0.55F);
        theNeck.lerpYRot(0.0F);
        theHead.lerpXRot(-Mth.HALF_PI*0.55F);
        theHead.lerpYRot(0.0F);
        theHead.lerpZRot(0.0F);
    }

    private void walkingLegsAnimation(float limbSwing, float limbSwingAmount) {
        float f = (Mth.cos(limbSwing * 0.6662F)) * 1.4F * limbSwingAmount;
        float f1 = Mth.cos(limbSwing * 0.6662F + Mth.PI) * 1.4F * limbSwingAmount;

        theLegFrontLeft.setXRot(f);
        theLegFrontRight.setXRot(f1);
        theLegBackLeft.setXRot(f);
        theLegBackRight.setXRot(f1);
    }

    private void standingLegsAnimation() {
        theLegFrontLeft.lerpXRot(0.0F);
        theLegFrontRight.lerpXRot(0.0F);
        theLegBackLeft.lerpXRot(0.0F);
        theLegBackRight.lerpXRot(0.0F);
    }

    private void twitchEarAnimation(boolean side, float ticks) {
        boolean direction = Math.cos(ticks*0.8F) > 0;
        //TODO this animation needs to be redone for foxes, but demonstrates both how it is done and also that the rotation points are in the correct place
        if (side) {
            theEarLeft.lerpXRot(Mth.HALF_PI * 0.5F * (direction?-1F:0.5F));
            theEarLeft.lerpYRot(Mth.HALF_PI * 0.25F * (direction?-1F:0.5F));
            theEarRight.lerpXRot(0.0F);
            theEarRight.lerpYRot(0.0F);
            theEarRight.setYRot(lerpTo(0.15F, theEarRight.getYRot(), 0.0F));
        } else {
            theEarRight.setXRot(lerpTo(0.15F, theEarRight.getXRot(), Mth.HALF_PI * 0.5F * (direction?-1F:0.5F)));
            theEarRight.setYRot(lerpTo(0.15F, theEarRight.getYRot(), Mth.HALF_PI * 0.25F * (direction?1F:-0.5F)));
            theEarLeft.setXRot(lerpTo(0.15F, theEarLeft.getXRot(), 0.0F));
            theEarLeft.setYRot(lerpTo(0.15F, theEarLeft.getYRot(), 0.0F));
        }
    }

    private FoxModelData getCreateFoxModelData(T enhancedFox) {
        return (FoxModelData) getCreateAnimalModelData(enhancedFox);
    }

    @Override
    protected void setInitialModelData(T enhancedFox) {
        FoxModelData foxModelData = new FoxModelData();
        setBaseInitialModelData(foxModelData, enhancedFox);
    }

    @Override
    protected void additionalUpdateModelDataInfo(AnimalModelData animalModelData, T enhancedAnimal) {
        animalModelData.saddle = getSaddle(enhancedAnimal.getEnhancedInventory());
    }

    @Override
    protected Phenotype createPhenotype(T enhancedAnimal) {
        return new FoxPhenotype(enhancedAnimal.getSharedGenes().getAutosomalGenes(), enhancedAnimal.getStringUUID().charAt(1));
    }


}