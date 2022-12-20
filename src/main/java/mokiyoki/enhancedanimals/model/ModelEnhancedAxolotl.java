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
    protected WrappedModelPart theLegFrontLeft;
    protected WrappedModelPart theLegFrontRight;
    protected WrappedModelPart theLegBackLeft;
    protected WrappedModelPart theLegBackRight;
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
        PartDefinition base = meshdefinition.getRoot().addOrReplaceChild("base", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 22.0F, -8.0F, -Mth.PI * 0.5F, 0.0F, 0.0F));
        PartDefinition bHead = base.addOrReplaceChild("bHead", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, -9.0F));
        PartDefinition bGills = base.addOrReplaceChild("bGills", CubeListBuilder.create(), PartPose.offset(0.0F, 2.0F, 0.0F));
        PartDefinition bBody = base.addOrReplaceChild("bBody", CubeListBuilder.create(), PartPose.ZERO);
        PartDefinition bLegFrontLeft = base.addOrReplaceChild("bLegFL", CubeListBuilder.create(), PartPose.offset(-3.5F, -2.0F, -1.0F));
        PartDefinition bLegFrontRight = base.addOrReplaceChild("bLegFR", CubeListBuilder.create(), PartPose.offset(3.5F, -2.0F, -1.0F));
        PartDefinition bLegBackLeft = base.addOrReplaceChild("bLegBL", CubeListBuilder.create(), PartPose.offset(-3.5F, 1.0F, -1.0F));
        PartDefinition bLegBackRight = base.addOrReplaceChild("bLegBR", CubeListBuilder.create(), PartPose.offset(3.5F, 1.0F, -1.0F));
        PartDefinition bTail = base.addOrReplaceChild("bTail", CubeListBuilder.create(), PartPose.offset(0.0F, -12.0F, 0.0F));

        bBody.addOrReplaceChild("body", CubeListBuilder.create()
                .texOffs(0, 12)
                .addBox(-4.0F, -6.0F, -4.0F, 8.0F, 4.0F, 4.0F)
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
                .addBox(-4.0F, -3.0F, -4.0F, 8.0F, 5.0F, 5.0F, new CubeDeformation(0.01F)),
                PartPose.ZERO
        );
        bHead.addOrReplaceChild("headLong", CubeListBuilder.create()
                .texOffs(0, 0)
                .addBox(-4.0F, -3.0F, -4.0F, 8.0F, 6.0F, 5.0F, new CubeDeformation(0.01F)),
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

        bLegFrontLeft.addOrReplaceChild("legFrontLeft", CubeListBuilder.create()
                        .texOffs(55, 2)
                        .addBox(-1.0F, 0.0F, 0.0F, 3.0F, 5.0F, 0.0F),
                PartPose.ZERO
        );
        bLegFrontRight.addOrReplaceChild("legFrontRight", CubeListBuilder.create()
                        .texOffs(55, 9)
                        .addBox(-2.0F, 0.0F, 0.0F, 3.0F, 5.0F, 0.0F),
                PartPose.ZERO
        );
        bLegBackLeft.addOrReplaceChild("legBackLeft", CubeListBuilder.create()
                        .texOffs(55, 16)
                        .addBox(-1.0F, 0.0F, 0.0F, 3.0F, 5.0F, 0.0F),
                PartPose.ZERO
        );
        bLegBackRight.addOrReplaceChild("legBackRight", CubeListBuilder.create()
                        .texOffs(55, 23)
                        .addBox(-2.0F, 0.0F, 0.0F, 3.0F, 5.0F, 0.0F),
                PartPose.ZERO
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
        ModelPart bGills = base.getChild("bGills");
        ModelPart bBody = base.getChild("bBody");
        ModelPart bLegFL = base.getChild("bLegFL");
        ModelPart bLegFR = base.getChild("bLegFR");
        ModelPart bLegBL = base.getChild("bLegBL");
        ModelPart bLegBR = base.getChild("bLegBR");
        ModelPart bTail = base.getChild("bTail");

        this.theHead = new WrappedModelPart(bHead, "bHead");
        this.theGills = new WrappedModelPart(bGills, "bGills");
        this.theBody = new WrappedModelPart(bBody, "bBody");
        this.theLegFrontLeft = new WrappedModelPart(bLegFL, "bLegFL");
        this.theLegFrontRight = new WrappedModelPart(bLegFR, "bLegFR");
        this.theLegBackLeft = new WrappedModelPart(bLegBL, "bLegBL");
        this.theLegBackRight = new WrappedModelPart(bLegBR, "bLegBR");
        this.theTail = new WrappedModelPart(bTail, "bTail");

        this.head = new WrappedModelPart("head", bHead);
        this.headLong = new WrappedModelPart("headLong", bHead);
        this.gillsTop = new WrappedModelPart("gillsTop", bGills);
        this.gillsLeft = new WrappedModelPart("gillsLeft", bGills);
        this.gillsRight = new WrappedModelPart("gillsRight", bGills);
        this.body = new WrappedModelPart("body", bBody);
        this.bodyLong = new WrappedModelPart("bodyLong", bBody);
        this.bodyFin = new WrappedModelPart("bodyFin", bBody);
        this.bodyFinLong = new WrappedModelPart("bodyFinLong", bBody);
        this.tail12 = new WrappedModelPart("tail12", bTail);
        this.tail13 = new WrappedModelPart("tail13", bTail);
        this.tail14 = new WrappedModelPart("tail14", bTail);
        this.tail15 = new WrappedModelPart("tail15", bTail);
        this.tail16 = new WrappedModelPart("tail16", bTail);
        this.legFrontLeft = new WrappedModelPart("legFrontLeft", bLegFL);
        this.legFrontRight = new WrappedModelPart("legFrontRight", bLegFR);
        this.legBackLeft = new WrappedModelPart("legBackLeft", bLegBL);
        this.legBackRight = new WrappedModelPart("legBackRight", bLegBR);
        this.collar = new WrappedModelPart(base.getChild("collar"), "collar");

        this.theAxolotl.addChild(this.theBody);
        this.theBody.addChild(this.theHead);
        this.theBody.addChild(this.theLegFrontLeft);
        this.theBody.addChild(this.theLegFrontRight);
        this.theBody.addChild(this.theLegBackLeft);
        this.theBody.addChild(this.theLegBackRight);
        this.theBody.addChild(this.theTail);

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

        this.theLegFrontLeft.addChild(this.legFrontLeft);
        this.theLegFrontRight.addChild(this.legFrontRight);
        this.theLegBackLeft.addChild(this.legBackLeft);
        this.theLegBackRight.addChild(this.legBackRight);

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
        if (axolotl!=null) {
            this.setupInitialAnimationValues(axolotlModelData, netHeadYaw, headPitch, axolotl);

            if (entityIn.isPlayingDead()) {
                this.setupPlayDeadAnimation(ageInTicks);
            } else {
                boolean isMoving = entityIn.getDeltaMovement().horizontalDistanceSqr() > 1.0E-7D || entityIn.getXRot() != entityIn.xRotO || entityIn.getYRot() != entityIn.yRotO || entityIn.xOld != entityIn.getX() || entityIn.zOld != entityIn.getZ();
                if (entityIn.isInWaterOrBubble()) {
                    if (isMoving) {
                        this.setupSwimmingAnimation(ageInTicks, headPitch);
                    } else {
                        this.setupWaterHoveringAnimation(ageInTicks);
                    }
                } else {
                    if (entityIn.isOnGround()) {
                        if (isMoving) {
                            this.setupGroundCrawlingAnimation(ageInTicks, netHeadYaw);
                        } else {
                            this.setupLayStillOnGroundAnimation(ageInTicks, netHeadYaw);
                        }
                    }
                }
            }


            this.saveAnimationValues(axolotlModelData);

            if (axolotlModelData.collar) {
                this.collar.setRotation(this.theHead.getXRot()+Mth.PI, this.theHead.getYRot(), this.theHead.getZRot());
            }
        }

    }

    private void setupInitialAnimationValues(AnimalModelData data, float netHeadYaw, float headPitch, AxolotlPhenotype axolotl) {
        this.theAxolotl.setX(0.0F);
        this.theAxolotl.setY(20.0F);
        this.theAxolotl.setZ(0.0F);
        this.theHead.setZ(-4.0F);
        this.theHead.setY(axolotl.isLong ? 2.0F : 0.0F);
        Map<String, Vector3f> map = data.offsets;
        if (map.isEmpty()) {
            this.theAxolotl.setRotation((headPitch * (-Mth.PI / 180F))-Mth.HALF_PI, netHeadYaw * (Mth.PI / 180F), 0.0F);
            this.theHead.setRotation(Mth.PI, 0.0F, 0.0F);
            this.theLegFrontLeft.setRotation(Mth.HALF_PI, 0.0F, Mth.PI);
            this.theLegFrontRight.setRotation(Mth.HALF_PI, 0.0F, Mth.PI);
            this.theLegBackLeft.setRotation(Mth.HALF_PI, 0.0F, Mth.PI);
            this.theLegBackRight.setRotation(Mth.HALF_PI, 0.0F, Mth.PI);
            this.gillsLeft.setRotation(-Mth.HALF_PI, 0.0F, Mth.HALF_PI);
            this.gillsRight.setRotation(-Mth.HALF_PI, 0.0F, Mth.HALF_PI);
            this.gillsTop.setRotation(Mth.PI * 1.5F, 0.0F, 0.0F);
            this.theTail.setRotation(0.0F, 0.0F, 0.0F);
            this.theLegBackLeft.modelPart.y = axolotl.isLong ? -11.5F : -9.5F;
            this.theLegBackRight.modelPart.y = axolotl.isLong ? -11.5F : -9.5F;
        } else {
            this.setRotationFromVector(this.theAxolotl, map.get("bAxolotl"));
            this.setRotationFromVector(this.theHead, map.get("bHead"));
            this.setRotationFromVector(this.theLegFrontLeft, map.get("bLegFL"));
            this.setRotationFromVector(this.theLegFrontRight, map.get("bLegFR"));
            this.setRotationFromVector(this.theLegBackLeft, map.get("bLegBL"));
            this.setRotationFromVector(this.theLegBackRight, map.get("bLegBR"));
            this.setRotationFromVector(this.gillsTop, map.get("top_gills"));
            this.setRotationFromVector(this.gillsLeft, map.get("left_gills"));
            this.setRotationFromVector(this.gillsRight, map.get("right_gills"));
            this.setRotationFromVector(this.theTail, map.get("bTail"));
            this.theLegBackLeft.setY(map.get("backLegs").y());
            this.theLegBackRight.setY(map.get("backLegs").y());
        }
    }

    private void setupLayStillOnGroundAnimation(float ageInTicks, float netHeadYaw) {
        float f = ageInTicks * 0.09F;
        float f1 = Mth.sin(f);
        float f2 = Mth.cos(f);
        float f3 = f1 * f1 - 2.0F * f1;
        float f4 = f2 * f2 - 3.0F * f1;
        this.theHead.setXRot(this.lerpTo(this.theHead.getXRot(), (-0.09F * f3)+Mth.PI));
        this.theHead.setYRot(this.lerpTo(this.theHead.getYRot(), -0.2F));
        this.theHead.setZRot(this.lerpTo(this.theHead.getZRot(), 0.0F));
        this.theTail.setZRot(this.lerpTo(this.theTail.getZRot(), -0.1F + 0.1F * f3));
        this.gillsTop.setXRot(this.lerpTo(this.gillsTop.getXRot(), (0.6F + 0.05F * f4)-Mth.HALF_PI));
        this.gillsLeft.setZRot(this.lerpTo(this.gillsLeft.getZRot(), this.gillsTop.getXRot()-Mth.PI));
        this.gillsRight.setZRot(this.lerpTo(this.gillsRight.getZRot(), -this.gillsLeft.getZRot()-Mth.PI));
        this.lerpPart(this.theLegBackRight, 1.1F+Mth.HALF_PI, 0.0F, 1.0F);
        this.lerpPart(this.theLegFrontRight, 0.8F+Mth.HALF_PI, -0.5F, 2.3F);
        this.applyMirrorLegRotations();
        this.theAxolotl.setXRot(this.lerpTo(0.2F, this.theAxolotl.getXRot(), -Mth.HALF_PI));
        this.theAxolotl.setYRot(this.lerpTo(this.theAxolotl.getYRot(), netHeadYaw * (Mth.PI / 180F)));
        this.theAxolotl.setZRot(this.lerpTo(this.theAxolotl.getZRot(), 0.0F));
    }

    private void setupGroundCrawlingAnimation(float ageInTicks, float netHeadYaw) {
        float f = ageInTicks * 0.11F;
        float f1 = Mth.cos(f);
        float f2 = (f1 * f1 - 2.0F * f1) / 5.0F;
        float f3 = 0.7F * f1;
        this.theHead.setXRot(this.lerpTo(this.theHead.getXRot(), Mth.PI));
        this.theHead.setYRot(this.lerpTo(this.theHead.getYRot(), 0.0F));
        this.theHead.setZRot(this.lerpTo(this.theHead.getZRot(), 0.09F * f1));
        this.theTail.setYRot(this.lerpTo(this.theTail.getYRot(), this.theHead.getYRot()));
        this.gillsTop.setXRot(this.lerpTo(this.gillsTop.getXRot(), (0.6F - 0.08F * (f1 * f1 + 2.0F * Mth.sin(f)))-Mth.HALF_PI));
        this.gillsRight.setZRot(this.lerpTo(this.gillsRight.getZRot(), 0.0F - this.gillsTop.getXRot()));
        this.gillsLeft.setZRot(this.lerpTo(this.gillsLeft.getZRot(), -(this.gillsRight.getZRot()+Mth.PI)));
        this.lerpPart(this.theLegBackLeft, 0.9424779F+Mth.HALF_PI, -0.1F, (1.5F - f2)+(Mth.PI * 1.25F));
        this.lerpPart(this.theLegFrontLeft, 1.0995574F - Mth.HALF_PI, 0.0F+Mth.PI, ((Mth.PI / 2F) - f3));
        this.lerpPart(this.theLegBackRight, this.theLegBackLeft.getXRot(), -0.1F, (1.5F - f2));
        this.lerpPart(this.theLegFrontRight, this.theLegFrontLeft.getXRot(), 0.0F+Mth.PI, ((-Mth.PI / 2F) - f3));
        this.theAxolotl.setXRot(this.lerpTo(0.2F, this.theAxolotl.getXRot(), -Mth.HALF_PI));
        this.theAxolotl.setYRot(this.lerpTo(this.theAxolotl.getYRot(), netHeadYaw * (Mth.PI / 180F)));
        this.theAxolotl.setZRot(this.lerpTo(this.theAxolotl.getZRot(), 0.0F));
    }

    private void setupWaterHoveringAnimation(float ageInTicks) {
        float f = ageInTicks * 0.075F;
        float f1 = Mth.cos(f);
        float f2 = Mth.sin(f) * 0.015F;
        this.theAxolotl.modelPart.z -= f2;
        this.theAxolotl.setXRot(this.lerpTo(this.theAxolotl.getXRot(), (-0.15F + 0.075F * f1) - Mth.HALF_PI));
        this.theHead.setXRot(this.lerpTo(this.theHead.getXRot(), Mth.HALF_PI-this.theAxolotl.getXRot()));
        this.theHead.setYRot(this.lerpTo(this.theHead.getYRot(), 0.0F));
        this.theHead.setZRot(this.lerpTo(this.theHead.getZRot(), 0.0F));
        this.gillsTop.setXRot(this.lerpTo(this.gillsTop.getXRot(), Mth.PI - ((-0.2F * f1)-(Mth.PI * 0.5F))));
        this.gillsLeft.setZRot(this.lerpTo(this.gillsLeft.getZRot(), (0.3F * f1 + 0.19F) + Mth.HALF_PI));
        this.gillsRight.setZRot(this.lerpTo(this.gillsRight.getZRot(), -this.gillsLeft.getZRot() + Mth.PI));
        this.lerpPart(this.theLegBackRight, Mth.PI + (2.3561945F - f1 * 0.11F),1.7278761F, Mth.PI + 0.47123894F);
        this.lerpPart(this.theLegFrontRight, Mth.HALF_PI + ((Mth.PI / 4F) - f1 * 0.2F), 0.0F, 2.042035F);
        this.applyMirrorLegRotations();
        this.theTail.setZRot(this.lerpTo(this.theTail.getZRot(), 0.5F * f1));
    }

    private void setupSwimmingAnimation(float ageInTicks, float headPitch) {
        float f = ageInTicks * 0.33F;
        float f1 = Mth.sin(f);
        float f2 = Mth.cos(f);
        float f3 = 0.13F * f1;
        this.theAxolotl.setXRot(this.lerpTo(0.1F, this.theAxolotl.getXRot(), (headPitch * ((float)Math.PI / 180F) + f3) - Mth.HALF_PI));
        this.theHead.setXRot((-f3 * 1.8F) + Mth.PI);
        this.theAxolotl.modelPart.y -= 0.45F * f2;
        this.gillsTop.setXRot(this.lerpTo(this.gillsTop.getXRot(), Mth.PI - (-0.5F * f1 - 0.8F)));
        this.gillsLeft.setZRot(this.lerpTo(this.gillsLeft.getZRot(), 0.3F * f1 + 0.9F));
        this.gillsRight.setZRot(this.lerpTo(this.gillsRight.getZRot(), -(0.3F * f1 + 0.9F) + Mth.PI));
        this.theTail.setZRot(this.lerpTo(this.theTail.getZRot(), 0.3F * Mth.cos(f * 0.9F)));
        this.lerpPart(this.theLegFrontLeft, 1.8849558F, Mth.HALF_PI, -(Mth.HALF_PI - (-0.4F * f1)));
        this.lerpPart(this.theLegBackLeft, 1.8849558F,  Mth.HALF_PI, -(Mth.HALF_PI + (-0.2F * f2 - 0.1F)));
        this.lerpPart(this.theLegBackRight, 1.8849558F,  -Mth.HALF_PI, (Mth.HALF_PI + (-0.2F * f2 - 0.1F)));
        this.lerpPart(this.theLegFrontRight, 1.8849558F, -Mth.HALF_PI, (Mth.HALF_PI - (-0.4F * f1)));
        this.theHead.setYRot(this.lerpTo(this.theHead.getYRot(), 0.0F));
        this.theHead.setZRot(this.lerpTo(this.theHead.getZRot(), 0.0F));
    }

    private void setupPlayDeadAnimation(float ageInTicks) {
        this.lerpPart(this.theLegFrontLeft, (Mth.PI * 0.25F) - Mth.HALF_PI, Mth.PI, Mth.PI - 2.042035F);
        this.lerpPart(this.theLegFrontRight, (Mth.PI * 0.25F) - Mth.HALF_PI, Mth.PI, -(Mth.PI - 2.042035F));
        this.lerpPart(this.theLegBackLeft, 1.4137167F - Mth.HALF_PI, -(Mth.PI * 0.25F) + (Mth.PI * 1.3F), -(1.0995574F - Mth.PI));
        this.lerpPart(this.theLegBackRight, 1.4137167F - Mth.HALF_PI, (Mth.PI * 0.25F) - (Mth.PI * 1.3F), (1.0995574F - Mth.PI));
        this.theAxolotl.setXRot(this.lerpTo(this.theAxolotl.getXRot(), -0.15F - Mth.HALF_PI));
        this.theAxolotl.setZRot(this.lerpTo(this.theAxolotl.getZRot(), 0.35F));
        this.theAxolotl.setYRot(this.lerpTo(this.theAxolotl.getYRot(), (ageInTicks * (Mth.PI / 180F))));
        this.theHead.setXRot(this.lerpTo(this.theHead.getXRot(), Mth.PI));
        this.theHead.setYRot(this.lerpTo(this.theHead.getYRot(), 0.0F));
        this.theHead.setZRot(this.lerpTo(this.theHead.getZRot(), 0.0F));
        this.theTail.setYRot(this.lerpTo(this.theTail.modelPart.yRot, 0.0F));
        this.lerpPart(this.gillsTop, Mth.PI * 1.5F, 0.0F, 0.0F);
        this.lerpPart(this.gillsLeft, -Mth.HALF_PI, 0.0F, Mth.HALF_PI);
        this.lerpPart(this.gillsRight, -Mth.HALF_PI, 0.0F, Mth.HALF_PI);
    }

    protected void saveAnimationValues(AnimalModelData data) {
        Map<String, Vector3f> map = data.offsets;
        map.put("bHead", this.getRotationVector(this.theHead));
        map.put("bAxolotl", this.getRotationVector(this.theAxolotl));
        map.put("bLegFL", this.getRotationVector(this.theLegFrontLeft));
        map.put("bLegFR", this.getRotationVector(this.theLegFrontRight));
        map.put("bLegBL", this.getRotationVector(this.theLegBackLeft));
        map.put("bLegBR", this.getRotationVector(this.theLegBackRight));
        map.put("top_gills", this.getRotationVector(this.gillsTop));
        map.put("left_gills", this.getRotationVector(this.gillsLeft));
        map.put("right_gills", this.getRotationVector(this.gillsRight));
        map.put("bTail", this.getRotationVector(this.theTail));
        map.put("backLegs", new Vector3f(0.0F, this.theLegBackLeft.getY(), 0.0F));
    }

    private void applyMirrorLegRotations() {
        this.lerpPart(this.theLegBackLeft, this.theLegBackRight.getXRot(), -this.theLegBackRight.getYRot(), -this.theLegBackRight.getZRot());
        this.lerpPart(this.theLegFrontLeft, this.theLegFrontRight.getXRot(), -this.theLegFrontRight.getYRot(), -this.theLegFrontRight.getZRot());
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

            this.theLegFrontLeft.show();
            this.theLegFrontRight.show();
            this.theLegBackLeft.show();
            this.theLegBackRight.show();

            if (axolotlModelData.growthAmount < 1.0F) {
                if (axolotlModelData.growthAmount < 0.3F) {
                    if (axolotlModelData.growthAmount < 0.15F) {
                        this.theLegFrontLeft.hide();
                        this.theLegFrontRight.hide();
                        this.theLegBackLeft.hide();
                        this.theLegBackRight.hide();
                    } else {
                        this.theLegBackLeft.hide();
                        this.theLegBackRight.hide();
                    }
                }
            }

            float size = ((1.0F + (axolotlModelData.growthAmount * 11.0F))/12.0F) * axolotlModelData.size;

            poseStack.pushPose();
            poseStack.scale(size, size, size);
            poseStack.translate(0.0F, -1.5F + 1.5F/(size), 0.0F);

            gaRender(this.theAxolotl, null, poseStack, vertexConsumer, packedLightIn, packedOverlayIn, red, green, blue, alpha);

            poseStack.popPose();
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
