package mokiyoki.enhancedanimals.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import mokiyoki.enhancedanimals.entity.EnhancedAxolotl;
import mokiyoki.enhancedanimals.model.util.WrappedModelPart;
import mokiyoki.enhancedanimals.util.Genes;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Collections;
import java.util.Map;
import java.util.function.Function;

@OnlyIn(Dist.CLIENT)
public class ModelEnhancedAxolotl<T extends EnhancedAxolotl> extends EnhancedAnimalModel<T> {
    protected WrappedModelPart theAxolotl;

    protected WrappedModelPart theHead;
    protected WrappedModelPart theGills;
    protected WrappedModelPart theBody;
    protected WrappedModelPart theTail;

    protected WrappedModelPart head;
    protected WrappedModelPart headLong;
    protected WrappedModelPart gillsTop;
    protected WrappedModelPart gillsLeft;
    protected WrappedModelPart gillsRight;
    protected WrappedModelPart body;
    protected WrappedModelPart bodyLong;
    protected WrappedModelPart bodyFin;
    protected WrappedModelPart bodyFinLong;
    protected WrappedModelPart tail12;
    protected WrappedModelPart tail13;
    protected WrappedModelPart tail14;
    protected WrappedModelPart tail15;
    protected WrappedModelPart tail16;
    protected WrappedModelPart legFrontRight;
    protected WrappedModelPart legFrontLeft;
    protected WrappedModelPart legBackLeft;
    protected WrappedModelPart legBackRight;
    protected WrappedModelPart collar;

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition base = meshdefinition.getRoot().addOrReplaceChild("base", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 22.0F, -8.0F, -(float)Math.PI * 0.5F, 0.0F, 0.0F));
        PartDefinition bHead = base.addOrReplaceChild("bHead", CubeListBuilder.create(), PartPose.offset(0.0F, -1.0F, -4.0F));
        PartDefinition bGills = bHead.addOrReplaceChild("bGills", CubeListBuilder.create(), PartPose.ZERO);
        PartDefinition bBody = base.addOrReplaceChild("bBody", CubeListBuilder.create(), PartPose.ZERO);
        PartDefinition bTail = base.addOrReplaceChild("bTail", CubeListBuilder.create(), PartPose.offset(0.0F, -12.0F, .0F));

        bBody.addOrReplaceChild("body", CubeListBuilder.create()
                .texOffs(0, 12)
                .addBox(-4.0F, -6.0F, -4.0F, 8.0F, 6.0F, 4.0F)
                .texOffs(0, 22)
                .addBox(-4.0F, -12.0F, -4.0F, 8.0F, 6.0F, 4.0F),
                PartPose.ZERO
        );
        bBody.addOrReplaceChild("bodyLong", CubeListBuilder.create()
                .texOffs(0, 12)
                .addBox(-4.0F, -6.0F, -4.0F, 8.0F, 6.0F, 4.0F)
                .texOffs(0, 22)
                .addBox(-4.0F, -14.0F, -4.0F, 8.0F, 8.0F, 4.0F),
                PartPose.ZERO
        );

        bHead.addOrReplaceChild("head", CubeListBuilder.create()
                .texOffs(0, 0)
                .addBox(-4.0F, -5.0F, -4.0F, 8.0F, 5.0F, 5.0F, new CubeDeformation(0.01F)),
                PartPose.ZERO
        );
        bHead.addOrReplaceChild("headLong", CubeListBuilder.create()
                .texOffs(0, 0)
                .addBox(-4.0F, -6.0F, -4.0F, 8.0F, 6.0F, 5.0F, new CubeDeformation(0.01F)),
                PartPose.ZERO
        );

        bGills.addOrReplaceChild("gillsTop", CubeListBuilder.create()
                .texOffs(39, 0)
                .addBox(-5.0F, -5.0F, 0.0F, 10.0F, 5.0F, 0.0F),
                PartPose.offset(0.0F, -1.0F, 1.0F)
        );

        bGills.addOrReplaceChild("gillsLeft", CubeListBuilder.create()
                .texOffs(36, 0)
                .addBox(0.0F, -8.0F, 0.0F, 0.0F, 8.0F, 5.0F),
                PartPose.offset(-4.0F, -1.0F, -4.0F)
        );

        bGills.addOrReplaceChild("gillsRight", CubeListBuilder.create()
                .texOffs(47, 0)
                .addBox(0.0F, -8.0F, -5.0F, 0.0F, 8.0F, 5.0F),
                PartPose.offset(4.0F, -1.0F, -4.0F)
        );

        bBody.addOrReplaceChild("legFrontLeft", CubeListBuilder.create()
                        .texOffs(58, 2)
                        .addBox(-1.5F, 0.0F, 0.0F, 3.0F, 5.0F, 0.0F),
                PartPose.offset(-3.0F, -2.5F, -1.0F)
        );
        bBody.addOrReplaceChild("legFrontRight", CubeListBuilder.create()
                        .texOffs(58, 9)
                        .addBox(-1.5F, 0.0F, 0.0F, 3.0F, 5.0F, 0.0F),
                PartPose.offset(3.0F, -2.5F, -1.0F)
        );
        bBody.addOrReplaceChild("legBackLeft", CubeListBuilder.create()
                        .texOffs(58, 16)
                        .addBox(-1.5F, 0.0F, 0.0F, 3.0F, 5.0F, 0.0F),
                PartPose.offset(-3.0F, -9.5F, -1.0F)
        );
        bBody.addOrReplaceChild("legBackRight", CubeListBuilder.create()
                        .texOffs(58, 23)
                        .addBox(-1.5F, 0.0F, 0.0F, 3.0F, 5.0F, 0.0F),
                PartPose.offset(3.0F, -9.5F, -1.0F)
        );

        bBody.addOrReplaceChild("bodyFin", CubeListBuilder.create()
                        .mirror(true)
                        .texOffs(26, 9)
                        .addBox(0.01F, 0.0F, -5.0F, 0.0F, 9.0F, 5.0F)
                        .mirror(false)
                        .texOffs(26, 36)
                        .addBox(-0.01F, 0.0F, -5.0F, 0.0F, 9.0F, 5.0F),
                PartPose.offset(0.0F, -12.0F, 0.0F)
        );

        bBody.addOrReplaceChild("bodyFinLong", CubeListBuilder.create()
                        .mirror(true)
                        .texOffs(26, 9)
                        .addBox(0.01F, 0.0F, -5.0F, 0.0F, 11.0F, 5.0F)
                        .mirror(false)
                        .texOffs(26, 36)
                        .addBox(-0.01F, 0.0F, -5.0F, 0.0F, 11.0F, 5.0F),
                PartPose.offset(0.0F, -14.0F, 0.0F)
        );

        bTail.addOrReplaceChild("tail12", CubeListBuilder.create()
                        .mirror(true)
                        .texOffs(26, -5)
                        .addBox(0.01F, -12.0F, -5.0F, 0.0F, 12.0F, 5.0F)
                        .mirror(false)
                        .texOffs(26, 20)
                        .addBox(-0.01F, -12.0F, -5.0F, 0.0F, 12.0F, 5.0F),
                PartPose.ZERO
        );
        bTail.addOrReplaceChild("tail13", CubeListBuilder.create()
                        .mirror(true)
                        .texOffs(26, -5)
                        .addBox(0.01F, -13.0F, -5.0F, 0.0F, 13.0F, 5.0F)
                        .mirror(false)
                        .texOffs(26, 20)
                        .addBox(-0.01F, -13.0F, -5.0F, 0.0F, 13.0F, 5.0F),
                PartPose.ZERO
        );
        bTail.addOrReplaceChild("tail14", CubeListBuilder.create()
                        .mirror(true)
                        .texOffs(26, -5)
                        .addBox(0.01F, -14.0F, -5.0F, 0.0F, 14.0F, 5.0F)
                        .mirror(false)
                        .texOffs(26, 20)
                        .addBox(-0.01F, -14.0F, -5.0F, 0.0F, 14.0F, 5.0F),
                PartPose.ZERO
        );
        bTail.addOrReplaceChild("tail15", CubeListBuilder.create()
                        .mirror(true)
                        .texOffs(26, -5)
                        .addBox(0.01F, -15.0F, -5.0F, 0.0F, 15.0F, 5.0F)
                        .mirror(false)
                        .texOffs(26, 20)
                        .addBox(-0.01F, -15.0F, -5.0F, 0.0F, 15.0F, 5.0F),
                PartPose.ZERO
        );
        bTail.addOrReplaceChild("tail16", CubeListBuilder.create()
                        .mirror(true)
                        .texOffs(26, -5)
                        .addBox(0.01F, -16.0F, -5.0F, 0.0F, 16.0F, 5.0F)
                        .mirror(false)
                        .texOffs(26, 20)
                        .addBox(-0.01F, -16.0F, -5.0F, 0.0F, 16.0F, 5.0F),
                PartPose.ZERO
        );

        base.addOrReplaceChild("collar", CubeListBuilder.create()
                        .texOffs(36, 57)
                        .addBox(-4.5F, -1.0F, -0.5F, 9, 2, 5)
                        .texOffs(35, 51)
                        .addBox(0.0F, -1.5F, 3.5F, 0,  3, 3)
                        .texOffs(12, 37)
                        .addBox(-1.5F, -1.5F, 5.0F, 3, 3, 3, new CubeDeformation(-0.5F)),
                PartPose.offset(0.0F, -2.0F, -4.0F)
        );

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    protected Function<ResourceLocation, RenderType> renderTypeFunction() {
        return RenderType::entityCutoutNoCull;
    }

    public ModelEnhancedAxolotl(ModelPart modelPart, Function<ResourceLocation, RenderType> renderType) {
        super(modelPart, renderType);
        ModelPart base = modelPart.getChild("base");
        this.theAxolotl = new WrappedModelPart(base, "base");
        ModelPart bHead = base.getChild("bHead");
        ModelPart bGills = bHead.getChild("bGills");
        ModelPart bBody = base.getChild("bBody");
        ModelPart bTail = base.getChild("bTail");

        this.theHead = new WrappedModelPart(bHead, "bHead");
        this.theGills = new WrappedModelPart(bGills, "bGills");
        this.theBody = new WrappedModelPart(bBody, "bBody");
        this.theTail = new WrappedModelPart(bTail, "bTail");

        this.head = new WrappedModelPart("head", bHead);
        this.headLong = new WrappedModelPart("headLong", bHead);
        this.gillsTop = new WrappedModelPart("gillsTop", bGills);
        this.gillsLeft = new WrappedModelPart(bGills.getChild("gillsLeft"), "gillsLeft");
        this.gillsRight = new WrappedModelPart(bGills.getChild("gillsRight"), "gillsRight");
        this.body = new WrappedModelPart(bBody.getChild("body"), "body");
        this.bodyLong = new WrappedModelPart(bBody.getChild("bodyLong"), "bodyLong");
        this.bodyFin = new WrappedModelPart(bBody.getChild("bodyFin"), "bodyFin");
        this.bodyFinLong = new WrappedModelPart(bBody.getChild("bodyFinLong"), "bodyFinLong");
        this.tail12 = new WrappedModelPart(bTail.getChild("tail12"), "tail12");
        this.tail13 = new WrappedModelPart(bTail.getChild("tail13"), "tail13");
        this.tail14 = new WrappedModelPart(bTail.getChild("tail14"), "tail14");
        this.tail15 = new WrappedModelPart(bTail.getChild("tail15"), "tail15");
        this.tail16 = new WrappedModelPart(bTail.getChild("tail16"), "tail16");
        this.legFrontLeft = new WrappedModelPart(bBody.getChild("legFrontLeft"), "legFrontLeft");
        this.legFrontRight = new WrappedModelPart(bBody.getChild("legFrontRight"), "legFrontRight");
        this.legBackLeft = new WrappedModelPart(bBody.getChild("legBackLeft"), "legBackLeft");
        this.legBackRight = new WrappedModelPart(bBody.getChild("legBackRight"), "legBackRight");
        this.collar = new WrappedModelPart(base.getChild("collar"), "collar");

        this.theAxolotl.addChild(this.theHead);
        this.theAxolotl.addChild(this.theBody);

        this.theHead.addChild(this.head);
        this.theHead.addChild(this.headLong);
        this.theHead.addChild(this.theGills);

        this.theGills.addChild(this.gillsTop);
        this.theGills.addChild(this.gillsLeft);
        this.theGills.addChild(this.gillsRight);

        this.theBody.addChild(this.body);
        this.theBody.addChild(this.bodyLong);
        this.theBody.addChild(this.bodyFin);
        this.theBody.addChild(this.bodyFinLong);
        this.theBody.addChild(this.legFrontLeft);
        this.theBody.addChild(this.legFrontRight);
        this.theBody.addChild(this.legBackLeft);
        this.theBody.addChild(this.legBackRight);
        this.theBody.addChild(this.theTail);

        this.theTail.addChild(this.tail12);
        this.theTail.addChild(this.tail13);
        this.theTail.addChild(this.tail14);
        this.theTail.addChild(this.tail15);
        this.theTail.addChild(this.tail16);

        this.theBody.addChild(this.collar);
    }

    @Override
    public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.currentAnimal = entityIn.getId();
        AxolotlModelData axolotlModelData = getCreateAxolotlModelData(entityIn);
        AxolotlPhenotype axolotl = (AxolotlPhenotype) axolotlModelData.phenotype;
        int randomAnimationOffset = (int)(2000 * axolotlModelData.random);
        if (axolotl!=null) {

            this.setRotationPointOffsets(axolotl);

            this.setupInitialAnimationValues(entityIn, netHeadYaw, headPitch);

            this.setupWaterHoveringAnimation(ageInTicks+randomAnimationOffset);

//            if (/*entityIn.isPlayingDead()*/ false) {
//                this.setupPlayDeadAnimation(netHeadYaw);
//                this.saveAnimationValues(entityIn);
//            } else {
//                boolean flag = entityIn.getDeltaMovement().horizontalDistanceSqr() > 1.0E-7D || entityIn.getXRot() != entityIn.xRotO || entityIn.getYRot() != entityIn.yRotO || entityIn.xOld != entityIn.getX() || entityIn.zOld != entityIn.getZ();
//                if (entityIn.isInWaterOrBubble()) {
//                    if (flag) {
//                        this.setupSwimmingAnimation(ageInTicks, headPitch);
//                    } else {
//                        this.setupWaterHoveringAnimation(ageInTicks);
//                    }
//
//                    this.saveAnimationValues(entityIn);
//                } else {
//                    if (entityIn.isOnGround()) {
//                        if (flag) {
//                            this.setupGroundCrawlingAnimation(ageInTicks, netHeadYaw);
//                        } else {
//                            this.setupLayStillOnGroundAnimation(ageInTicks, netHeadYaw);
//                        }
//                    }
//
//                    this.saveAnimationValues(entityIn);
//                }
//            }

//            float pi = (float) Math.PI;
//
//            this.theHead.modelPart.setRotation(pi + (headPitch * (pi / 180F)), 0.0F, netHeadYaw * (pi / 180F));
//
//            float gillRotation = 0.4F * Mth.sin((ageInTicks+randomAnimationOffset) * 0.1F);
//            this.gillsTop.modelPart.xRot = ((float) Math.PI * 1.5F) + gillRotation;
//            this.gillsLeft.modelPart.zRot = ((float) Math.PI * 0.4F) + gillRotation;
//            this.gillsRight.modelPart.zRot = ((float) Math.PI * 0.4F) - gillRotation;
//
//            limbSwing = limbSwing * 0.6666F;
//
//            this.legFrontLeft.modelPart.zRot = pi * 0.5F + (Mth.cos(limbSwing + pi) * 1.4F * limbSwingAmount);
//            this.legFrontRight.modelPart.zRot = -pi * 0.5F + (Mth.cos(limbSwing) * 1.4F * limbSwingAmount);
//            this.legBackLeft.modelPart.zRot = pi * 0.5F + (Mth.cos(limbSwing + pi) * 1.4F * limbSwingAmount);
//            this.legBackRight.modelPart.zRot = -pi * 0.5F + (Mth.cos(limbSwing) * 1.4F * limbSwingAmount);
//            this.legFrontRight.modelPart.xRot = pi * 0.2F;
//            this.legFrontLeft.modelPart.xRot = pi * 0.2F;
//            this.legBackRight.modelPart.xRot = pi * 0.2F;
//            this.legBackLeft.modelPart.xRot = pi * 0.2F;
//
//            this.legBackLeft.modelPart.y = axolotl.isLong ? -11.5F : -9.5F;
//            this.legBackRight.modelPart.y = axolotl.isLong ? -11.5F : -9.5F;
//
//            this.theTail.modelPart.zRot = Mth.sin((ageInTicks+randomAnimationOffset) * 0.1F);

            if (axolotlModelData.collar) {
                this.collar.setRotation(this.theHead.getXRot()+Mth.PI, this.theHead.getYRot(), this.theHead.getZRot());
//                this.collar.modelPart.xRot = (float) Math.PI * -0.1F + (headPitch * (pi / 180F) * 0.5F);
//                this.collar.modelPart.zRot = netHeadYaw * (pi / 180F) * 0.5F;
            }
        }

    }

    private void setRotationPointOffsets(AxolotlPhenotype axolotl) {
        this.legBackLeft.modelPart.y = axolotl.isLong ? -11.5F : -9.5F;
        this.legBackRight.modelPart.y = axolotl.isLong ? -11.5F : -9.5F;
    }

    private void setupInitialAnimationValues(T entityIn, float netHeadYaw, float headPitch) {
        float pi = (float)Math.PI;
        float halfPi = pi * 0.5F;
        this.theAxolotl.modelPart.x = 0.0F;
        this.theAxolotl.modelPart.y = 20.0F;
        this.theHead.modelPart.z = -4.0F;
        this.theBody.modelPart.y = 0.0F;
        Map<String, Vector3f> map = entityIn.getModelRotationValues();
        if (map.isEmpty()) {
            this.theBody.setRotation(headPitch * (-pi / 180F), netHeadYaw * (pi / 180F), 0.0F);
            this.theHead.setRotation(pi, 0.0F, 0.0F);
            this.legFrontLeft.setRotation(pi * 0.2F, 0.0F, halfPi);
            this.legFrontRight.setRotation(pi * 0.2F, 0.0F, -halfPi);
            this.legBackLeft.setRotation(pi * 0.2F, 0.0F, halfPi);
            this.legBackRight.setRotation(pi * 0.2F, 0.0F, -halfPi);
            this.gillsLeft.setRotation(-halfPi, 0.0F, halfPi);
            this.gillsRight.setRotation(-halfPi, 0.0F, halfPi);
            this.gillsTop.setRotation(pi * 1.5F, 0.0F, 0.0F);
            this.theTail.setRotation(0.0F, 0.0F, 0.0F);
        } else {
            this.setRotationFromVector(this.theBody, map.get("body"));
            this.setRotationFromVector(this.theHead, map.get("head"));
            this.setRotationFromVector(this.legFrontLeft, map.get("left_front_leg"));
            this.setRotationFromVector(this.legFrontRight, map.get("right_front_leg"));
            this.setRotationFromVector(this.legBackLeft, map.get("left_hind_leg"));
            this.setRotationFromVector(this.legBackRight, map.get("right_hind_leg"));
            this.setRotationFromVector(this.gillsTop, map.get("top_gills"));
            this.setRotationFromVector(this.gillsLeft, map.get("left_gills"));
            this.setRotationFromVector(this.gillsRight, map.get("right_gills"));
            this.setRotationFromVector(this.theTail, map.get("tail"));
        }
    }

    private void setupLayStillOnGroundAnimation(float p_170415_, float p_170416_) {
        float f = p_170415_ * 0.09F;
        float f1 = Mth.sin(f);
        float f2 = Mth.cos(f);
        float f3 = f1 * f1 - 2.0F * f1;
        float f4 = f2 * f2 - 3.0F * f1;
        this.theHead.setXRot(this.lerpTo(this.theHead.getXRot(), -0.09F * f3));
        this.theHead.setYRot(this.lerpTo(this.theHead.getYRot(), 0.0F));
        this.theHead.setZRot(this.lerpTo(this.theHead.getZRot(), -0.2F));
        this.theTail.setYRot(this.lerpTo(this.theTail.getYRot(), -0.1F + 0.1F * f3));
        this.gillsTop.setXRot(this.lerpTo(this.gillsTop.getXRot(), 0.6F + 0.05F * f4));
        this.gillsLeft.setYRot(this.lerpTo(this.gillsLeft.getYRot(), -this.gillsTop.getXRot()));
        this.gillsRight.setYRot(this.lerpTo(this.gillsRight.getYRot(), -this.gillsLeft.getYRot()));
        this.lerpPart(this.legBackLeft, 1.1F, 0.0F, 1.0F);
        this.lerpPart(this.legFrontLeft, 0.8F, -0.5F, 2.3F);
        this.applyMirrorLegRotations();
        this.theBody.setXRot(this.lerpTo(0.2F, this.theBody.getXRot(), 0.0F));
        this.theBody.setYRot(this.lerpTo(this.theBody.getYRot(), p_170416_ * ((float)Math.PI / 180F)));
        this.theBody.setZRot(this.lerpTo(this.theBody.getZRot(), 0.0F));
    }

    private void setupGroundCrawlingAnimation(float p_170419_, float p_170420_) {
        float f = p_170419_ * 0.11F;
        float f1 = Mth.cos(f);
        float f2 = (f1 * f1 - 2.0F * f1) / 5.0F;
        float f3 = 0.7F * f1;
        this.theHead.setXRot(this.lerpTo(this.theHead.getXRot(), 0.0F));
        this.theHead.setYRot(this.lerpTo(this.theHead.getYRot(), 0.09F * f1));
        this.theHead.setZRot(this.lerpTo(this.theHead.getZRot(), 0.0F));
        this.theTail.setYRot(this.lerpTo(this.theTail.getYRot(), this.theHead.getYRot()));
        this.gillsTop.setXRot(this.lerpTo(this.gillsTop.getXRot(), 0.6F - 0.08F * (f1 * f1 + 2.0F * Mth.sin(f))));
        this.gillsLeft.setYRot(this.lerpTo(this.gillsLeft.getYRot(), -this.gillsTop.getXRot()));
        this.gillsRight.setYRot(this.lerpTo(this.gillsRight.getYRot(), -this.gillsLeft.getYRot()));
        this.lerpPart(this.legBackLeft, 0.9424779F, -0.1F, 1.5F - f2);
        this.lerpPart(this.legFrontLeft, 1.0995574F, 0.0F, ((float)Math.PI / 2F) - f3);
        this.lerpPart(this.legBackRight, this.legBackLeft.getXRot(), 0.0F, -1.0F - f2);
        this.lerpPart(this.legFrontRight, this.legFrontLeft.getXRot(), 0.0F, (-(float)Math.PI / 2F) - f3);
        this.theBody.setXRot(this.lerpTo(0.2F, this.theBody.getXRot(), 0.0F));
        this.theBody.setYRot(this.lerpTo(this.theBody.getYRot(), p_170420_ * ((float)Math.PI / 180F)));
        this.theBody.setZRot(this.lerpTo(this.theBody.getZRot(), 0.0F));
    }

    private void setupWaterHoveringAnimation(float p_170373_) {
        float f = p_170373_ * 0.075F;
        float f1 = Mth.cos(f);
        float f2 = Mth.sin(f) * 0.015F;
        this.theAxolotl.modelPart.z -= f2;
        this.theBody.setXRot(this.lerpTo(this.theBody.getXRot(), -0.15F + 0.075F * f1));
        this.theHead.setXRot(this.lerpTo(this.theHead.getXRot(), this.theBody.getXRot()));
        this.theHead.setYRot(this.lerpTo(this.theHead.getYRot(), 0.0F));
        this.theHead.setZRot(this.lerpTo(this.theHead.getZRot(), 0.0F));
        this.gillsTop.setXRot(this.lerpTo(this.gillsTop.getXRot(), (-0.2F * f1)-((float) Math.PI * 0.5F) ));
        this.gillsLeft.setYRot(this.lerpTo(this.gillsLeft.getYRot(), -0.3F * f1 - 0.19F));
        this.gillsRight.setYRot(this.lerpTo(this.gillsRight.getYRot(), -this.gillsLeft.getYRot()));
        this.lerpPart(this.legBackLeft, 2.3561945F - f1 * 0.11F,1.7278761F, 0.47123894F);
        this.lerpPart(this.legFrontLeft, ((float)Math.PI / 4F) - f1 * 0.2F, 0.0F, 2.042035F);
        this.applyMirrorLegRotations();
        this.theTail.setZRot(this.lerpTo(this.theTail.getZRot(), 0.5F * f1));
    }

    private void setupSwimmingAnimation(float p_170423_, float p_170424_) {
        float f = p_170423_ * 0.33F;
        float f1 = Mth.sin(f);
        float f2 = Mth.cos(f);
        float f3 = 0.13F * f1;
        this.theBody.setXRot(this.lerpTo(0.1F, this.theBody.getXRot(), p_170424_ * ((float)Math.PI / 180F) + f3));
        this.theHead.setXRot(-f3 * 1.8F);
        this.theBody.modelPart.y -= 0.45F * f2;
        this.gillsTop.setXRot(this.lerpTo(this.gillsTop.getXRot(), -0.5F * f1 - 0.8F));
        this.gillsLeft.setYRot(this.lerpTo(this.gillsLeft.getYRot(), 0.3F * f1 + 0.9F));
        this.gillsRight.setYRot(this.lerpTo(this.gillsRight.getYRot(), -this.gillsLeft.getYRot()));
        this.theTail.setYRot(this.lerpTo(this.theTail.getYRot(), 0.3F * Mth.cos(f * 0.9F)));
        this.lerpPart(this.legBackLeft, 1.8849558F, ((float)Math.PI / 2F), -0.4F * f1);
        this.lerpPart(this.legFrontLeft, 1.8849558F, ((float)Math.PI / 2F), -0.2F * f2 - 0.1F);
        this.applyMirrorLegRotations();
        this.theHead.setYRot(this.lerpTo(this.theHead.getYRot(), 0.0F));
        this.theHead.setZRot(this.lerpTo(this.theHead.getZRot(), 0.0F));
    }

    private void setupPlayDeadAnimation(float p_170413_) {
        this.lerpPart(this.legBackLeft, 1.4137167F, 1.0995574F, ((float)Math.PI / 4F));
        this.lerpPart(this.legFrontLeft, ((float)Math.PI / 4F), 2.042035F, 0.0F);
        this.theBody.setXRot(this.lerpTo(this.theBody.getXRot(), -0.15F));
        this.theBody.setZRot(this.lerpTo(this.theBody.getZRot(), 0.35F));
        this.applyMirrorLegRotations();
        this.theBody.setYRot(this.lerpTo(this.theBody.getYRot(), p_170413_ * ((float)Math.PI / 180F)));
        this.theHead.setRotation(this.lerpTo(this.theHead.getXRot(), 0.0F), this.lerpTo(this.theHead.getYRot(), 0.0F), this.lerpTo(this.theHead.getZRot(), 0.0F));
        this.theTail.setYRot(this.lerpTo(this.theTail.modelPart.yRot, 0.0F));
        this.lerpPart(this.gillsTop, 0.0F, 0.0F, 0.0F);
        this.lerpPart(this.gillsLeft, 0.0F, 0.0F, 0.0F);
        this.lerpPart(this.gillsRight, 0.0F, 0.0F, 0.0F);
    }

    protected void saveAnimationValues(T p_170389_, Phenotype axolotl) {
        Map<String, Vector3f> map = p_170389_.getModelRotationValues();
//        map.put("axolotl", this.getRotationVector(this.theAxolotl));
        map.put("head", this.getRotationVector(this.theHead));
        map.put("body", this.getRotationVector(this.theBody));
        map.put("left_front_leg", this.getRotationVector(this.legFrontLeft));
        map.put("right_front_leg", this.getRotationVector(this.legFrontRight));
        map.put("left_hind_leg", this.getRotationVector(this.legBackLeft));
        map.put("right_hind_leg", this.getRotationVector(this.legBackRight));
        map.put("top_gills", this.getRotationVector(this.gillsTop));
        map.put("left_gills", this.getRotationVector(this.gillsLeft));
        map.put("right_gills", this.getRotationVector(this.gillsRight));
        map.put("tail", this.getRotationVector(this.theTail));
    }

    private void applyMirrorLegRotations() {
        this.lerpPart(this.legBackRight, this.legBackLeft.modelPart.xRot, -this.legBackLeft.modelPart.yRot, -this.legBackLeft.modelPart.zRot);
        this.lerpPart(this.legFrontRight, this.legFrontLeft.modelPart.xRot, -this.legFrontLeft.modelPart.yRot, -this.legFrontLeft.modelPart.zRot);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        AxolotlModelData axolotlModelData = getAxolotlModelData();
        AxolotlPhenotype axolotl = (AxolotlPhenotype) axolotlModelData.phenotype;

        if (axolotl!=null) {

            for (WrappedModelPart part : this.theTail.children) {
                part.show(false);
            }

            if (axolotl.isLong) {
                this.head.modelPart.visible = false;
                this.body.modelPart.visible = false;
                this.bodyFin.modelPart.visible = false;
                this.headLong.modelPart.visible = true;
                this.bodyLong.modelPart.visible = true;
                this.bodyFinLong.modelPart.visible = true;
                switch (axolotl.tailLength) {
                    case EXTRALONG -> this.tail16.modelPart.visible = true;
                    case LONG -> this.tail15.modelPart.visible = true;
                    default -> this.tail14.modelPart.visible = true;
                }
            } else {
                this.head.modelPart.visible = true;
                this.body.modelPart.visible = true;
                this.bodyFin.modelPart.visible = true;
                this.headLong.modelPart.visible = false;
                this.bodyLong.modelPart.visible = false;
                this.bodyFinLong.modelPart.visible = false;
                switch (axolotl.tailLength) {
                    case EXTRALONG -> this.tail14.show(true);
                    case LONG -> this.tail13.show(true);
                    case NORMAL -> this.tail12.show(true);
                    default -> this.tail12.show(true);
                }
            }

            gaRender(this.theAxolotl, null, poseStack, vertexConsumer, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        }
    }

    private class AxolotlModelData extends AnimalModelData {
        boolean hasEggs = false;

        public AxolotlPhenotype getPhenotype() {
            return (AxolotlPhenotype) this.phenotype;
        }
    }

    private AxolotlModelData getAxolotlModelData() {
        return (AxolotlModelData) getAnimalModelData();
    }

    private AxolotlModelData getCreateAxolotlModelData(T enhancedAxolotl) {
        return (AxolotlModelData) getCreateAnimalModelData(enhancedAxolotl);
    }

    @Override
    protected void setInitialModelData(T enhancedAxolotl) {
        AxolotlModelData axolotlModelData = new AxolotlModelData();
        axolotlModelData.hasEggs = enhancedAxolotl.hasEgg();
        setBaseInitialModelData(axolotlModelData, enhancedAxolotl);
    }

    @Override
    protected void additionalModelDataInfo(AnimalModelData animalModelData, T enhancedAxolotl) {
        ((AxolotlModelData)animalModelData).hasEggs = enhancedAxolotl.hasEgg();
    }

    @Override
    protected Phenotype createPhenotype(T enhancedAxolotl) {
        return new AxolotlPhenotype(enhancedAxolotl.getSharedGenes());
    }

    protected class AxolotlPhenotype implements Phenotype {
        boolean glowingBody;
        boolean glowingEyes;
        boolean glowingGills;
        boolean isLong;
        TailLength tailLength;

        AxolotlPhenotype(Genes genes) {
            int[] gene = genes.getAutosomalGenes();
            this.isLong = gene[32] == 2 && gene[33] == 2;

            if (gene[26] == gene[27]) {
                this.tailLength = gene[32] == 1 ? TailLength.NORMAL : TailLength.EXTRALONG;
            } else {
                this.tailLength = TailLength.LONG;
            }

            this.glowingBody = genes.has(10, 3) && !genes.has(10, 2);
            this.glowingEyes = this.glowingBody || genes.has(20, 6);
            this.glowingGills = this.glowingBody || (genes.has(38, 3) && !genes.has(38, 2));
        }
    }

    private enum TailLength {
        NORMAL,
        LONG,
        EXTRALONG
    }
}
