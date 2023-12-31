package mokiyoki.enhancedanimals.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import mokiyoki.enhancedanimals.entity.EnhancedFox;
import mokiyoki.enhancedanimals.model.modeldata.AnimalModelData;
import mokiyoki.enhancedanimals.model.modeldata.Phenotype;
import mokiyoki.enhancedanimals.model.modeldata.FoxModelData;
import mokiyoki.enhancedanimals.model.modeldata.FoxPhenotype;
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
public class ModelEnhancedFox<T extends EnhancedFox> extends EnhancedAnimalModel<T> {

    protected WrappedModelPart theFox;

    protected WrappedModelPart theHead;
    protected WrappedModelPart theMouth;
    protected WrappedModelPart theEarLeft;
    protected WrappedModelPart theEarRight;
    protected WrappedModelPart theNeck;
    protected WrappedModelPart theBody;
    protected WrappedModelPart theLegFrontLeft;
    protected WrappedModelPart theLegFrontRight;
    protected WrappedModelPart theLegBackLeft;
    protected WrappedModelPart theLegBackRight;
    protected WrappedModelPart theTail;

    protected WrappedModelPart skull;
    protected WrappedModelPart nose;
    protected WrappedModelPart jaw;

    protected WrappedModelPart earL;

    protected WrappedModelPart earR;

    protected WrappedModelPart neck;

    protected WrappedModelPart body;

    private WrappedModelPart legFrontLeft;
    private WrappedModelPart legFrontRight;
    private WrappedModelPart legBackLeft;
    private WrappedModelPart legBackRight;

    private WrappedModelPart tail0;
    private WrappedModelPart tail1;
    private WrappedModelPart tail2;


    private FoxModelData foxModelData;

    // this part creates the bone groups
    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition base = meshdefinition.getRoot().addOrReplaceChild("base", CubeListBuilder.create(), PartPose.ZERO); // prev .offset(0.0F, 0.0F, 0.0F)
        base.addOrReplaceChild("bFox", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 10.5F, 16.0F, Mth.HALF_PI, 0.0F, 0.0F));  // 16.0,-4.0
        base.addOrReplaceChild("bBody", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, -2.0F));
        base.addOrReplaceChild("bNeck", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 9.5F));
        base.addOrReplaceChild("bHead", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F)); // prev 0, -7.0F, -4.0F
        base.addOrReplaceChild("bMouth", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        base.addOrReplaceChild("bEarL", CubeListBuilder.create(), PartPose.offset(4.0F, -3.0F, -3.0F));
        base.addOrReplaceChild("bEarR", CubeListBuilder.create(), PartPose.offset(-4.0F, -3.0F, -3.0F));
        base.addOrReplaceChild("bLegFL", CubeListBuilder.create(), PartPose.offset(4.0F, 0.0F, 0.0F));
        base.addOrReplaceChild("bLegFR", CubeListBuilder.create(), PartPose.offset(-4.0F, 0.0F, 0.0F));
        base.addOrReplaceChild("bLegBL", CubeListBuilder.create(), PartPose.offset(4.0F, 14.0F, -2.0F));
        base.addOrReplaceChild("bLegBR", CubeListBuilder.create(), PartPose.offset(-4.0F, 14.0F, -2.0F));
        base.addOrReplaceChild("bTail", CubeListBuilder.create(), PartPose.offset(0.0F, 6.0F, 8.0F));

// THE EYES I GUESS - doesnt have a separate texture yet
        base.addOrReplaceChild("eyes", CubeListBuilder.create()
                        .texOffs(69, 15)
                        .addBox(2.5F, 0.0F, 0.0F, 1, 1, 1, new CubeDeformation(0.01F))
                        .texOffs(49, 15)
                        .addBox(-3.5F, 0.0F, 0.0F, 1, 1, 1, new CubeDeformation(0.01F)),
                PartPose.offset(0.0F, -5.0F, 0.0F)
        );

//HEAD   - currently facing down, needs to be rotated?
        base.addOrReplaceChild("skull", CubeListBuilder.create()
                        .texOffs(26, 0)
                        .addBox(0.0F, 14.75F, -0.25F, 7, 6, 6), // first three = pivot point, second set = box dimensions
                PartPose.ZERO   // offset(-3.5F, 11.75F, -4.25F)
        );

        base.addOrReplaceChild("nose", CubeListBuilder.create()
                        .texOffs(33, 36)
                        .addBox(0.0F, 14.75F, -5.25F, 3, 3, 4),
                PartPose.offset(0.0F, 3.5F, -3.0F)
        );

        base.addOrReplaceChild("jaw", CubeListBuilder.create()
                        .texOffs(0, 21)
                        .addBox(0.0F, 11.75F, -1.25F, 3, 2, 8, new CubeDeformation(-0.25F)),
                PartPose.offset(-1.5F, 10.8848F, -9.0486F)
        );


// EARS   ear pieces located roughly correctly in relation to each other, but overall are not in the right position and rotation
        base.addOrReplaceChild("earL", CubeListBuilder.create()
                        .texOffs(44, 43)
                        .addBox(-6.75F, 14.0F, -1.25F, 3, 4, 2)
                        .texOffs(0, 20)
                .addBox(-8.25F, 13.5F, -1.25F, 1, 2, 2), // lower
                PartPose.offset(-4.1716F, 16.5784F, -2.25F)
        );

        base.addOrReplaceChild("earR", CubeListBuilder.create()
                        .texOffs(40, 12)
                        .addBox(3.0F, 14.0F, -1.25F, 3, 4, 2)
                        .texOffs(0, 0)
                .addBox(1.5F, 13.5F, -1.25F, 1, 2, 2), // lower
                PartPose.offset(1.4216F, 16.5784F, -2.25F)
        );


// NECK
        base.addOrReplaceChild("neck", CubeListBuilder.create()
                        .texOffs(19, 13)
                        .addBox(0.0F, 10.0F, 1.5F, 7, 9, 7),
                PartPose.offsetAndRotation(-3.5F, 8.0784F, -2.4216F, -90.0F, 0.0F, 0.0F)
        );


// BODY
        base.addOrReplaceChild("body1", CubeListBuilder.create()
                        .texOffs(0, 0)
                        .addBox(0.0F, 4.0F, 0.0F, 6, 13, 7), // first three are pivot point, second set is box dimensions
                PartPose.ZERO // prev .offset(-3.0F, -6.0F, 3.0F)   // x , y , z
        );


// LEGS
        base.addOrReplaceChild("legFL", CubeListBuilder.create()
                        .texOffs(36, 43)
                        .addBox(-2.0F, 7.0F, -3.0F, 2, 8, 2),
                PartPose.ZERO
        );
        base.addOrReplaceChild("legFR", CubeListBuilder.create()
                        .texOffs(43, 29)
                        .addBox(2.0F, 7.0F, -3.0F, 2, 8, 2),
                PartPose.ZERO
        );
        base.addOrReplaceChild("legBL", CubeListBuilder.create()
                        .texOffs(20, 40)
                        .addBox(-2.0F, 7.5F, 9.0F, 2, 8, 2),
                PartPose.ZERO
        );
        base.addOrReplaceChild("legBR", CubeListBuilder.create()
                        .texOffs(28, 43)
                        .addBox(2.0F, 7.5F, 9.0F, 2, 8, 2),
                PartPose.ZERO
        );

// TAIL
        base.addOrReplaceChild("tail0", CubeListBuilder.create()
                        .texOffs(0, 35)
                        .addBox(0.0F, 12.5F, 10.0F, 5, 6, 5, new CubeDeformation(-0.05F)),
                PartPose.offsetAndRotation(-2.5F, 12.5F, 9.0F, -90.0F, 0.0F, 0.0F)
        );
        base.addOrReplaceChild("tail1", CubeListBuilder.create()
                        .texOffs(17, 29)
                        .addBox(0.0F, 13.5F, 16.0F, 5, 6, 5, new CubeDeformation(-0.1F)),
                PartPose.offsetAndRotation(-2.5F, 13.5F, 16.0F, -1.5708F, 0.0F, 0.0F)
        );
        base.addOrReplaceChild("tail2", CubeListBuilder.create()
                        .texOffs(19, 0)  // TEXTURE OFFSET
                        .addBox(0F, 13.0F, 22.0F, 3, 4, 2, new CubeDeformation(-0.15F)), //addbox = PIVOT or BLOCK OFFSET F, SIZE XYZ
                PartPose.offset(-1.5F, 9.5F, 22.0F)  // partposeOFFSET = POSITION  cubeDEFORM = SCALE
        );

        // PartPose.offset for just position offset
        // PartPose.rotation if you need rotation and not position offset
        // PartPose.offsetAndRotation for both rotation and position offset


// Equipment stuff

        base.addOrReplaceChild("collar", CubeListBuilder.create()
                        .texOffs(80, 1)
                        .addBox(-5.5F, -5.5F, -3.0F, 11, 2, 11, new CubeDeformation(0.001F))
                        .texOffs(80, 4)
                        .addBox(0.0F, -5.0F, -5.0F, 0, 3, 3)
                        .texOffs(116, 6)
                        .addBox(-1.5F, -5.0F, -6.25F, 3, 3, 3),
                PartPose.offset(0.0F, -1.0F, -7.5F)
        );

        return LayerDefinition.create(meshdefinition, 64, 64);    // texture size 64x64
    }


    // PARENTING

    public ModelEnhancedFox(ModelPart modelPart) {
        super(modelPart);
        ModelPart base = modelPart.getChild("base");

        /**
         * "bFox",
         * "bBody",
         * "bNeck",
         * "bHead",
         * "bMouth"
         * "bEarL",
         * "bEarR",
         * "bLegFL"
         * "bLegFR"
         * "bLegBL"
         * "bLegBR"
         * "bTail",
         */

        this.theFox = new WrappedModelPart("bFox", base);
        this.theBody = new WrappedModelPart("bBody", base);
        this.theNeck = new WrappedModelPart("bNeck", base);
        this.theHead = new WrappedModelPart("bHead", base);
        this.theMouth = new WrappedModelPart("bMouth", base);
        this.theEarLeft = new WrappedModelPart("bEarL", base);
        this.theEarRight = new WrappedModelPart("bEarR", base);
        this.theLegFrontLeft = new WrappedModelPart("bLegFL", base);
        this.theLegFrontRight = new WrappedModelPart("bLegFR", base);
        this.theLegBackLeft = new WrappedModelPart("bLegBL", base);
        this.theLegBackRight = new WrappedModelPart("bLegBR", base);
        this.theTail = new WrappedModelPart("bTail", base);

        /**
         *
         */

        this.eyes = new WrappedModelPart("eyes", base);

        this.skull = new WrappedModelPart("skull", base);
        this.nose = new WrappedModelPart("nose", base);
        this.jaw = new WrappedModelPart("jaw", base);

        this.earL = new WrappedModelPart("earL", base);
        this.earR = new WrappedModelPart("earR", base);

        this.neck = new WrappedModelPart("neck", base);

        this.body = new WrappedModelPart("body1", base);

        this.legFrontLeft = new WrappedModelPart("legFL", base);
        this.legFrontRight = new WrappedModelPart("legFR", base);
        this.legBackLeft = new WrappedModelPart("legBL", base);
        this.legBackRight = new WrappedModelPart("legBR", base);

        this.tail0 = new WrappedModelPart("tail0", base);
        this.tail1 = new WrappedModelPart("tail1", base);
        this.tail2 = new WrappedModelPart("tail2", base);

        this.theFox.addChild(this.theBody);
        this.theBody.addChild(this.theNeck);
        this.theBody.addChild(this.theTail);
        this.theNeck.addChild(this.theHead);
        this.theHead.addChild(this.theMouth);
        this.theHead.addChild(this.theEarLeft);
        this.theHead.addChild(this.theEarRight);
        this.theFox.addChild(this.theLegFrontLeft);
        this.theFox.addChild(this.theLegFrontRight);
        this.theFox.addChild(this.theLegBackLeft);
        this.theFox.addChild(this.theLegBackRight);

        this.theHead.addChild(this.skull);
        this.theHead.addChild(this.eyes);

        this.theEarLeft.addChild(this.earL);

        this.theEarRight.addChild(this.earR);

        this.theMouth.addChild(this.nose);
        this.theMouth.addChild(this.jaw);

        this.theNeck.addChild(this.neck);

        this.theBody.addChild(this.body);

        this.theLegFrontLeft.addChild(this.legFrontLeft);

        this.theLegFrontRight.addChild(this.legFrontRight);

        this.theLegBackLeft.addChild(this.legBackLeft);

        this.theLegBackRight.addChild(this.legBackRight);

        this.theTail.addChild(this.tail0);
        this.tail0.addChild(this.tail1);
        this.tail1.addChild(this.tail2);

        /**
         *      Equipment
         */

        this.collar = new WrappedModelPart("collar", base);

        this.theNeck.addChild(this.collar);

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

            this.neck.show(fox.shape == 2);

            this.body.show(fox.shape == 0);


            float finalFoxSize = ((3.0F * foxModelData.size * foxModelData.growthAmount) + foxModelData.size) / 4.0F;

            poseStack.pushPose();
            poseStack.scale(finalFoxSize, finalFoxSize, finalFoxSize);
            poseStack.translate(0.0F, -1.5F + 1.5F / finalFoxSize, 0.0F);

            gaRender(this.theFox, mapOfScale, poseStack, vertexConsumer, packedLightIn, packedOverlayIn, red, green, blue, alpha);  //error here?

            poseStack.popPose();
        }
    }

// ANIMATION - currently uses pig animations i think, but the part names should all be changed

    protected void saveAnimationValues(FoxModelData data, FoxPhenotype fox) {
        Map<String, Vector3f> map = data.offsets;
        map.put("bFox", this.getRotationVector(this.theFox));
        map.put("bFoxPos", this.getPosVector(this.theFox));
        map.put("nose", this.getRotationVector(this.nose));
        map.put("jaw", this.getRotationVector(this.jaw));
        map.put("bNeck", this.getRotationVector(this.theNeck));
        map.put("bHead", this.getRotationVector(this.theHead));
        if (data.growthAmount == 1.0F && !map.containsKey("bMouthPos")) {
            map.put("bMouthPos",this.getPosVector(this.theMouth));
        }
        map.put("bEarL", this.getRotationVector(this.theEarLeft));
        map.put("bEarR", this.getRotationVector(this.theEarRight));
        map.put("bEarLPos", this.getPosVector(this.theEarLeft));
        map.put("bEarRPos", this.getPosVector(this.theEarRight));
        map.put("bLegFL", this.getRotationVector(this.theLegFrontLeft));
        map.put("bLegFR", this.getRotationVector(this.theLegFrontRight));
        map.put("bLegBL", this.getRotationVector(this.theLegBackLeft));
        map.put("bLegBR", this.getRotationVector(this.theLegBackRight));
        map.put("tail0", this.getRotationVector(this.tail0));
        map.put("tail1", this.getRotationVector(this.tail1));
        map.put("tail2", this.getRotationVector(this.tail2));
    }

    private void readInitialAnimationValues(FoxModelData data, FoxPhenotype fox) {
        Map<String, Vector3f> map = data.offsets;
        if (map.isEmpty()) {
            this.theMouth.setY((-4.0F-data.growthAmount) - ((2.5F + (2.5F * data.growthAmount)) ));
            this.theLegFrontLeft.setXRot(-Mth.HALF_PI);
            this.theLegFrontRight.setXRot(-Mth.HALF_PI);
            this.theLegBackLeft.setXRot(-Mth.HALF_PI);
            this.theLegBackRight.setXRot(-Mth.HALF_PI);
        } else {
            this.theFox.setRotation(map.get("bFox"));
            this.theFox.setPos(map.get("bFoxPos"));
            this.theNeck.setRotation(map.get("bNeck"));
            this.theHead.setRotation(map.get("bHead"));
            if (map.containsKey("bMouthPos")) {
                this.theMouth.setPos(map.get("bMouthPos"));
            } else {
                this.theMouth.setY((-4.0F-data.growthAmount) - ((2.5F + (2.5F * data.growthAmount)) ));
            }
            this.nose.setRotation(map.get("nose"));
            this.jaw.setRotation(map.get("jaw"));
            this.theEarLeft.setRotation(map.get("bEarL"));
            this.theEarRight.setRotation(map.get("bEarR"));
            this.theEarLeft.setPos(map.get("bEarLPos"));
            this.theEarRight.setPos(map.get("bEarRPos"));
            this.theLegFrontLeft.setRotation(map.get("bLegFL"));
            this.theLegFrontRight.setRotation(map.get("bLegFR"));
            this.theLegBackLeft.setRotation(map.get("bLegBL"));
            this.theLegBackRight.setRotation(map.get("bLegBR"));
            this.tail0.setRotation(map.get("tail0"));
            this.tail1.setRotation(map.get("tail1"));
            this.tail2.setRotation(map.get("tail2"));
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
                float[] oldRot;
                boolean flag;
                if (this.foxModelData.earTwitchSide) {
                    oldRot = new float[]{this.theEarLeft.getXRot(), this.theEarLeft.getYRot(), this.theEarLeft.getZRot()};
                    flag = oldRot[0] == this.theEarLeft.getXRot() && oldRot[1] == this.theEarLeft.getYRot() && oldRot[2] == this.theEarLeft.getZRot();
                } else {
                    oldRot = new float[]{this.theEarRight.getXRot(), this.theEarRight.getYRot(), this.theEarRight.getZRot()};
                    flag = oldRot[0] == this.theEarRight.getXRot() && oldRot[1] == this.theEarRight.getYRot() && oldRot[2] == this.theEarRight.getZRot();
                }

                if (flag) {
                    this.foxModelData.earTwitchSide = entityIn.getRandom().nextBoolean();
                    this.foxModelData.earTwitchTimer = (int) ageInTicks + (entityIn.getRandom().nextInt(this.foxModelData.sleeping ? 60 : 30) * 20) + 30;
                } else {
                    if (this.foxModelData.earTwitchSide) {
                        this.theEarLeft.setXRot(this.lerpTo(oldRot[0], this.theEarLeft.getXRot()));
                        this.theEarLeft.setYRot(this.lerpTo(oldRot[1], this.theEarLeft.getYRot()));
                        this.theEarLeft.setZRot(this.lerpTo(oldRot[2], this.theEarLeft.getZRot()));
                    } else {
                        this.theEarRight.setXRot(this.lerpTo(oldRot[0], this.theEarRight.getXRot()));
                        this.theEarRight.setYRot(this.lerpTo(oldRot[1], this.theEarRight.getYRot()));
                        this.theEarRight.setZRot(this.lerpTo(oldRot[2], this.theEarRight.getZRot()));
                    }
                }
            } else if (this.foxModelData.earTwitchTimer <= ageInTicks + 30) {
                twitchEarAnimation(this.foxModelData.earTwitchSide, (int)ageInTicks);
            }

            if (this.foxModelData.sleeping && !isMoving) {
                if (this.theFox.getY() < 22.0F && this.theFox.getZRot() == 0.0F) {
                    layDownAnimation();
                } else {
                    rollOnSideAnimation();
                }
            } else {

                if (this.theFox.getY() != 16.0F) {
                    if (this.theFox.getZRot() == 0.0F) {
                        standUpAnimation();
                    } else {
                        rightSelfAnimation();
                    }
                }

                boolean flag = true;
                if (this.foxModelData.isEating != 0) {
                    if (this.foxModelData.isEating == -1) {
                        this.foxModelData.isEating = (int)ageInTicks + 90;
                    } else if (this.foxModelData.isEating < ageInTicks) {
                        this.foxModelData.isEating = 0;
                    }
                    flag = grazingAnimation(this.foxModelData.isEating - (int)ageInTicks);
                }

                if (flag) {
                    if (netHeadYaw == 0 && headPitch == 0) {
                        moveHeadAnimation();
                    } else {
                        headLookingAnimation(netHeadYaw, headPitch);
                    }
                }

                if (isMoving) {
                    walkingLegsAnimation(limbSwing, limbSwingAmount);
                } else if (this.theFox.getY() == 16.0F ){
                    standingLegsAnimation();
                }
            }


            saveAnimationValues(this.foxModelData, fox);
        }

    }

    private boolean grazingAnimation(float ticks) {
        if (ticks < 50) {
            float neckRot = this.theNeck.getXRot();
            if (neckRot < Mth.HALF_PI*0.1F) {
                this.theNeck.setXRot(this.lerpTo(this.theNeck.getXRot(), Mth.HALF_PI*0.2F));
            } else if (neckRot < Mth.HALF_PI*0.17F){
                this.theNeck.setXRot(this.lerpTo(this.theNeck.getXRot(), Mth.HALF_PI*0.2F));
                this.theHead.setXRot(this.lerpTo(this.theHead.getXRot(), Mth.HALF_PI*0.1F));
                this.nose.setXRot(this.lerpTo(this.nose.getXRot(), Mth.HALF_PI*0.0F));
                this.jaw.setXRot(this.lerpTo(this.jaw.getXRot(), Mth.HALF_PI*-0.1F));
            } else {
                float loop = (float) Math.cos(ticks*0.5F);
                if (loop > 0) {
                    this.theNeck.setXRot(this.lerpTo(this.theNeck.getXRot(), Mth.HALF_PI*0.2F));
                    this.theHead.setXRot(this.lerpTo(this.theHead.getXRot(), Mth.HALF_PI*0.3F));
                    this.nose.setXRot(this.lerpTo(this.nose.getXRot(), Mth.HALF_PI*0.0F));
                    this.jaw.setXRot(this.lerpTo(this.jaw.getXRot(), Mth.HALF_PI*-0.2F));
                } else {
                    this.theNeck.setXRot(this.lerpTo(this.theNeck.getXRot(), Mth.HALF_PI*0.2F));
                    this.theHead.setXRot(this.lerpTo(this.theHead.getXRot(), Mth.HALF_PI*0.1F));
                    this.nose.setXRot(this.lerpTo(this.nose.getXRot(), Mth.HALF_PI*-0.2F));
                    this.jaw.setXRot(this.lerpTo(this.jaw.getXRot(), Mth.HALF_PI*-0.05F));
                }
            }
            return false;
        } else {
            return true;
        }
    }

    private void rightSelfAnimation() {
        float zRot = this.theFox.getZRot();
        this.theFox.setZRot(zRot > 0.0F ? Math.max(zRot - 0.01F, 0.0F) : Math.min(zRot + 0.01F, 0.0F));
        if (this.theFox.getZRot()!=0.0F) {
            this.theFox.setX(this.lerpTo(0.02F, this.theFox.getX(), 0.0F));
            this.theFox.setY(this.lerpTo(0.02F, this.theFox.getY(), 22.0F));
            this.theFox.setXRot(this.lerpTo(0.02F, this.theFox.getXRot(), Mth.HALF_PI));
            this.theLegFrontLeft.setXRot(this.lerpTo(0.02F, this.theLegFrontLeft.getXRot(), 0.0F));
            this.theLegFrontLeft.setYRot(this.lerpTo(0.02F, this.theLegFrontLeft.getYRot(), 0.0F));
            this.theLegFrontRight.setXRot(this.lerpTo(0.02F, this.theLegFrontRight.getXRot(), 0.0F));
            this.theLegFrontRight.setYRot(this.lerpTo(0.02F, this.theLegFrontRight.getYRot(), 0.0F));
            this.theLegBackLeft.setXRot(this.lerpTo(0.02F, this.theLegBackLeft.getXRot(), -Mth.PI));
            this.theLegBackLeft.setYRot(this.lerpTo(0.02F, this.theLegBackLeft.getYRot(), 0.0F));
            this.theLegBackRight.setXRot(this.lerpTo(0.02F, this.theLegBackRight.getXRot(), -Mth.PI));
            this.theLegBackRight.setYRot(this.lerpTo(0.02F, this.theLegBackRight.getYRot(), 0.0F));
        } else {
            this.theFox.setX(0.0F);
            this.theFox.setY(22.0F);
            this.theFox.setXRot(Mth.HALF_PI);
            this.theLegFrontLeft.setXRot(0.0F);
            this.theLegFrontLeft.setYRot(0.0F);
            this.theLegFrontRight.setXRot(0.0F);
            this.theLegFrontRight.setYRot(0.0F);
            this.theLegBackLeft.setXRot(-Mth.PI);
            this.theLegBackLeft.setYRot(0.0F);
            this.theLegBackRight.setXRot(-Mth.PI);
            this.theLegBackRight.setYRot(0.0F);
        }
    }

    private void standUpAnimation() {
        if (this.theFox.getY() < 20.0F) {
            this.theFox.setY(this.lerpTo(0.02F,this.theFox.getY(), 16.0F));
            this.theFox.setXRot(this.lerpTo(0.02F,this.theFox.getXRot(), Mth.HALF_PI));
            this.theLegFrontLeft.setXRot(this.lerpTo(this.theLegFrontLeft.getXRot(), -Mth.HALF_PI));
            this.theLegFrontRight.setXRot(this.lerpTo(this.theLegFrontRight.getXRot(), -Mth.HALF_PI));
            this.theLegBackLeft.setXRot(this.lerpTo(0.05F,this.theLegBackLeft.getXRot(), -Mth.HALF_PI));
            this.theLegBackRight.setXRot(this.lerpTo(0.05F,this.theLegBackRight.getXRot(), -Mth.HALF_PI));
        } else {
            this.theFox.setY(this.lerpTo(0.02F,this.theFox.getY(), 19.8F));
            this.theFox.setXRot(this.lerpTo(0.02F,this.theFox.getXRot(), Mth.HALF_PI*0.85F));
            this.theLegFrontLeft.setXRot(this.lerpTo(0.05F,this.theLegFrontLeft.getXRot(), -Mth.HALF_PI));
            this.theLegFrontRight.setXRot(this.lerpTo(0.05F,this.theLegFrontRight.getXRot(), -Mth.HALF_PI));
            this.theLegBackLeft.setXRot(this.lerpTo(0.05F, this.theLegBackLeft.getXRot(), -Mth.HALF_PI * 1.85F));
            this.theLegBackRight.setXRot(this.lerpTo(0.05F, this.theLegBackRight.getXRot(), -Mth.HALF_PI * 1.85F));
        }
    }

    private void layDownAnimation() {
        if (this.theFox.getY() >= 20.0F) {
            this.theFox.setY(this.lerpTo(0.02F, this.theFox.getY(), 22.2F));
            this.theFox.setXRot(this.lerpTo(0.02F, this.theFox.getXRot(), Mth.HALF_PI));
            this.theLegFrontLeft.setXRot(this.lerpTo(0.02F, this.theLegFrontLeft.getXRot(), 0.0F));
            this.theLegFrontRight.setXRot(this.lerpTo(0.02F, this.theLegFrontRight.getXRot(), 0.0F));
            this.theLegBackLeft.setXRot(this.lerpTo(0.02F, this.theLegBackLeft.getXRot(), -Mth.PI));
            this.theLegBackRight.setXRot(this.lerpTo(0.02F, this.theLegBackRight.getXRot(), -Mth.PI));
        } else {
            this.theFox.setY(this.lerpTo(0.02F, this.theFox.getY(), 20.2F));
            this.theFox.setXRot(this.lerpTo(0.02F, this.theFox.getXRot(), Mth.HALF_PI*1.15F));
            this.theLegFrontLeft.setXRot(this.lerpTo(0.02F, this.theLegFrontLeft.getXRot(), -Mth.HALF_PI * 0.4F));
            this.theLegFrontRight.setXRot(this.lerpTo(0.02F, this.theLegFrontRight.getXRot(), -Mth.HALF_PI * 0.4F));
            this.theLegBackLeft.setXRot(this.lerpTo(this.theLegBackLeft.getXRot(), -Mth.HALF_PI));
            this.theLegBackRight.setXRot(this.lerpTo(this.theLegBackRight.getXRot(), -Mth.HALF_PI));
        }
    }
    private void rollOnSideAnimation() {
        if (this.theFox.getZRot() == 0.0F) {
            this.theFox.setZRot(ThreadLocalRandom.current().nextBoolean() ? 0.0001F : -0.0001F);
        }
        if (this.theFox.getZRot() > 0.0F) {
            this.theFox.setZRot(this.lerpTo(this.theFox.getZRot(), Mth.HALF_PI));
            this.theNeck.setZRot(this.lerpTo(this.theNeck.getZRot(), Mth.HALF_PI * 0.05F));
            this.theHead.setZRot(this.lerpTo(this.theHead.getZRot(), Mth.HALF_PI * 0.1F));
            this.theLegFrontLeft.setXRot(this.lerpTo(this.theLegFrontLeft.getXRot(), -Mth.HALF_PI * 0.75F));
            this.theLegFrontRight.setXRot(this.lerpTo(this.theLegFrontRight.getXRot(), -Mth.HALF_PI));
            this.theLegFrontRight.setYRot(this.lerpTo(this.theLegFrontRight.getYRot(), -Mth.HALF_PI * 0.25F));
            this.theLegBackLeft.setXRot(this.lerpTo(this.theLegBackLeft.getXRot(), -Mth.HALF_PI * 1.25F));
            this.theLegBackRight.setXRot(this.lerpTo(this.theLegBackRight.getXRot(), -Mth.HALF_PI));
            this.theLegBackRight.setYRot(this.lerpTo(this.theLegBackRight.getYRot(), -Mth.HALF_PI * 0.25F));

        } else {
            this.theFox.setZRot(this.lerpTo(this.theFox.getZRot(), -Mth.HALF_PI));
            this.theNeck.setZRot(this.lerpTo(this.theNeck.getZRot(), -Mth.HALF_PI * 0.05F));
            this.theHead.setZRot(this.lerpTo(this.theHead.getZRot(), -Mth.HALF_PI * 0.1F));
            this.theLegFrontLeft.setXRot(this.lerpTo(this.theLegFrontLeft.getXRot(), -Mth.HALF_PI));
            this.theLegFrontLeft.setYRot(this.lerpTo(this.theLegFrontLeft.getYRot(), Mth.HALF_PI * 0.25F));
            this.theLegFrontRight.setXRot(this.lerpTo(this.theLegFrontRight.getXRot(), -Mth.HALF_PI * 0.75F));
            this.theLegBackLeft.setXRot(this.lerpTo(this.theLegBackLeft.getXRot(), -Mth.HALF_PI));
            this.theLegBackLeft.setYRot(this.lerpTo(this.theLegBackLeft.getYRot(), Mth.HALF_PI * 0.25F));
            this.theLegBackRight.setXRot(this.lerpTo(this.theLegBackRight.getXRot(), -Mth.HALF_PI * 1.25F));
        }
        this.theFox.setY(this.lerpTo(this.theFox.getY(), 20.0F));
        this.theNeck.setXRot(this.lerpTo(this.theNeck.getXRot(), 0.0F));
        this.theHead.setXRot(this.lerpTo(this.theHead.getXRot(), 0.0F));
    }

    private void headLookingAnimation(float netHeadYaw, float headPitch) {
        netHeadYaw = (netHeadYaw * 0.017453292F);
        headPitch = ((headPitch * 0.017453292F));
        float lookRotX = Math.min((headPitch * 0.65F), 0.0F);
        float lookRotZ = netHeadYaw * 0.2F;

        this.theNeck.setXRot(this.lerpTo(this.theNeck.getXRot(), headPitch - lookRotX));
        this.theNeck.setZRot(this.lerpTo(this.theNeck.getZRot(), -lookRotZ));
        this.theHead.setXRot(this.lerpTo(this.theHead.getXRot(), lookRotX));
        this.theHead.setZRot(this.lerpTo(this.theHead.getZRot(), -limit(netHeadYaw - lookRotZ, Mth.HALF_PI * 0.75F)));
        this.jaw.setXRot(this.lerpTo(this.jaw.getXRot(), 0.0F));
    }

    private void moveHeadAnimation() {
        this.lerpPart(this.theNeck, 0.0F, 0.0F, 0.0F);
        this.lerpPart(this.theHead, 0.0F, 0.0F, 0.0F);
        this.nose.setXRot(this.lerpTo(this.nose.getXRot(), 0.0F));
        this.jaw.setXRot(this.lerpTo(this.jaw.getXRot(), 0.0F));
    }

    private void walkingLegsAnimation(float limbSwing, float limbSwingAmount) {
        float f = -Mth.HALF_PI + ((Mth.cos(limbSwing * 0.6662F)) * 1.4F * limbSwingAmount);
        float f1 = -Mth.HALF_PI + (Mth.cos(limbSwing * 0.6662F + Mth.PI) * 1.4F * limbSwingAmount);

        this.theLegFrontLeft.setXRot(f);
        this.theLegFrontRight.setXRot(f1);
        this.theLegBackLeft.setXRot(f);
        this.theLegBackRight.setXRot(f1);
    }

    private void standingLegsAnimation() {
        this.theLegFrontLeft.setXRot(this.lerpTo(this.theLegFrontLeft.getXRot(), -Mth.HALF_PI));
        this.theLegFrontRight.setXRot(this.lerpTo(this.theLegFrontRight.getXRot(), -Mth.HALF_PI));
        this.theLegBackLeft.setXRot(this.lerpTo(this.theLegBackLeft.getXRot(), -Mth.HALF_PI));
        this.theLegBackRight.setXRot(this.lerpTo(this.theLegBackRight.getXRot(), -Mth.HALF_PI));
    }

    private void twitchEarAnimation(boolean side, float ticks) {
        boolean direction = Math.cos(ticks*0.8F) > 0;
        if (side) {
            this.theEarLeft.setXRot(this.theEarLeft.getXRot() + (direction ? 0.07F : -0.07F));
            this.theEarLeft.setZRot(this.theEarLeft.getZRot() + (direction ? 0.05F : -0.05F));
        } else {
            this.theEarRight.setXRot(this.theEarRight.getXRot() + (direction ? -0.07F : 0.07F));
            this.theEarRight.setZRot(this.theEarRight.getZRot() + (direction ? -0.05F : 0.05F));
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