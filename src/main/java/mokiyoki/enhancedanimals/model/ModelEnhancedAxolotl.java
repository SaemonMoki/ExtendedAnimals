package mokiyoki.enhancedanimals.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import mokiyoki.enhancedanimals.entity.EnhancedAxolotl;
import mokiyoki.enhancedanimals.items.CustomizableCollar;
import mokiyoki.enhancedanimals.model.util.GAModel;
import mokiyoki.enhancedanimals.model.util.ModelHelper;
import mokiyoki.enhancedanimals.model.util.WrappedModelPart;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

@OnlyIn(Dist.CLIENT)
public class ModelEnhancedAxolotl<T extends EnhancedAxolotl> extends GAModel<T> {

    private Map<Integer, AxolotlModelData> axolotlModelDataCache = new HashMap<>();
    private int clearCacheTimer = 0;

    protected WrappedModelPart theAxolotl;
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

    private Integer currentAxolotl = null;

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition root = meshdefinition.getRoot();
        PartDefinition base = root.addOrReplaceChild("base", CubeListBuilder.create(), PartPose.rotation(0.0F, 22.0F, -8.0F));

        PartDefinition body = base.addOrReplaceChild("body", CubeListBuilder.create()
                .texOffs(0, 12)
                .addBox(-4.0F, -6.0F, -4.0F, 8.0F, 5.0F, 4.0F)
                .texOffs(0, 22)
                .addBox(-4.0F, -12.0F, -4.0F, 8.0F, 6.0F, 4.0F),
                PartPose.ZERO
        );
        PartDefinition bodyLong = base.addOrReplaceChild("bodyLong", CubeListBuilder.create()
                .texOffs(0, 12)
                .addBox(-4.0F, -6.0F, -4.0F, 8.0F, 5.0F, 4.0F)
                .texOffs(0, 22)
                .addBox(-4.0F, -14.0F, -4.0F, 8.0F, 8.0F, 4.0F),
                PartPose.ZERO
        );
        PartDefinition head = base.addOrReplaceChild("head", CubeListBuilder.create()
                .texOffs(0, 1)
                .addBox(-4.0F, -5.0F, -4.0F, 8.0F, 5.0F, 5.0F),
                PartPose.rotation(0.0F, -1.0F, -4.0F)
        );
        PartDefinition headLong = base.addOrReplaceChild("headLong", CubeListBuilder.create()
                .texOffs(0, 1)
                .addBox(-4.0F, -6.0F, -4.0F, 8.0F, 6.0F, 5.0F),
                PartPose.rotation(0.0F, -1.0F, -4.0F)
        );

        CubeListBuilder topGillsCubes = CubeListBuilder.create().texOffs(34, 0).addBox(-5.0F, 0.0F, 0.0F, 10.0F, 0.0F, 5.0F);
        head.addOrReplaceChild("topGills", topGillsCubes, PartPose.rotation(0.0F, -1.0F, 1.0F));
        headLong.addOrReplaceChild("topGills", topGillsCubes, PartPose.rotation(0.0F, -1.0F, 1.0F));

        CubeListBuilder leftGillsCubes = CubeListBuilder.create().texOffs(36, 0).addBox(0.0F, -8.0F, 0.0F, 0.0F, 8.0F, 5.0F);
        CubeListBuilder rightGillsCubes = CubeListBuilder.create().texOffs(36, 0).addBox(0.0F, -8.0F, 0.0F, 0.0F, 8.0F, 5.0F);
        head.addOrReplaceChild("leftGills", leftGillsCubes, PartPose.offset(-4.0F, -1.0F, -4.0F));
        head.addOrReplaceChild("rightGills", rightGillsCubes, PartPose.offset(4.0F, -1.0F, -4.0F));
        headLong.addOrReplaceChild("leftGills", leftGillsCubes, PartPose.offset(-4.0F, -1.0F, -4.0F));
        headLong.addOrReplaceChild("rightGills", rightGillsCubes, PartPose.offset(4.0F, -1.0F, -4.0F));

        body.addOrReplaceChild("legFrontLeft", CubeListBuilder.create()
                        .texOffs(58, 2)
                        .addBox(-1.5F, 0.0F, 0.0F, 3.0F, 5.0F, 0.0F),
                PartPose.rotation(-3.0F, -2.5F, -1.0F)
        );
        body.addOrReplaceChild("legFrontRight", CubeListBuilder.create()
                        .texOffs(58, 9)
                        .addBox(-1.5F, 0.0F, 0.0F, 3.0F, 5.0F, 0.0F),
                PartPose.rotation(3.0F, -2.5F, -1.0F)
        );
        body.addOrReplaceChild("legBackLeft", CubeListBuilder.create()
                        .texOffs(58, 16)
                        .addBox(-1.5F, 0.0F, 0.0F, 3.0F, 5.0F, 0.0F),
                PartPose.rotation(-3.0F, -9.5F, -1.0F)
        );
        body.addOrReplaceChild("legBackRight", CubeListBuilder.create()
                        .texOffs(58, 23)
                        .addBox(-1.5F, 0.0F, 0.0F, 3.0F, 5.0F, 0.0F),
                PartPose.rotation(3.0F, -9.5F, -1.0F)
        );

        CubeDeformation collarDeformation = new CubeDeformation(-0.5F);
        body.addOrReplaceChild("collar", CubeListBuilder.create()
                        .texOffs(36, 57)
                        .addBox(-4.5F, -1.0F, -0.5F, 9, 2, 5)
                        .texOffs(35, 51)
                        .addBox(0.0F, -1.5F, 3.5F, 0,  3, 3)
                        .texOffs(12, 37)
                        .addBox(-1.5F, -1.5F, 5.0F, 3, 3, 3, collarDeformation),
                PartPose.rotation(0.0F, -2.0F, -4.0F)
        );

        PartDefinition bodyFin = body.addOrReplaceChild("bodyFin", CubeListBuilder.create()
                        .texOffs(26, 9)
                        .addBox(0.0F, 0.0F, -5.0F, 0.0F, 9.0F, 5.0F),
                PartPose.rotation(0.0F, -12.0F, 0.0F)
        );

        PartDefinition bodyFinLong = bodyLong.addOrReplaceChild("bodyFinLong", CubeListBuilder.create()
                        .texOffs(26, 9)
                        .addBox(0.0F, 0.0F, -5.0F, 0.0F, 11.0F, 5.0F),
                PartPose.rotation(0.0F, -14.0F, 0.0F)
        );

        bodyFin.addOrReplaceChild("tail12", CubeListBuilder.create()
                        .texOffs(26, -5)
                        .addBox(0.0F, -12.0F, -5.0F, 0.0F, 12.0F, 5.0F),
                PartPose.rotation(0.0F, 0.0F, 0.0F)
        );
        bodyFin.addOrReplaceChild("tail13", CubeListBuilder.create()
                        .texOffs(26, -5)
                        .addBox(0.0F, -13.0F, -5.0F, 0.0F, 13.0F, 5.0F),
                PartPose.rotation(0.0F, 0.0F, 0.0F)
        );
        bodyFin.addOrReplaceChild("tail14", CubeListBuilder.create()
                        .texOffs(26, -5)
                        .addBox(0.0F, -14.0F, -5.0F, 0.0F, 14.0F, 5.0F),
                PartPose.rotation(0.0F, 0.0F, 0.0F)
        );
        bodyFin.addOrReplaceChild("tail15", CubeListBuilder.create()
                        .texOffs(26, -5)
                        .addBox(0.0F, -15.0F, -5.0F, 0.0F, 15.0F, 5.0F),
                PartPose.rotation(0.0F, 0.0F, 0.0F)
        );
        bodyFin.addOrReplaceChild("tail16", CubeListBuilder.create()
                        .texOffs(26, -5)
                        .addBox(0.0F, -16.0F, -5.0F, 0.0F, 16.0F, 5.0F),
                PartPose.rotation(0.0F, 0.0F, 0.0F)
        );

        bodyFinLong.addOrReplaceChild("tail12", CubeListBuilder.create()
                        .texOffs(26, -5)
                        .addBox(0.0F, -12.0F, -5.0F, 0.0F, 12.0F, 5.0F),
                PartPose.rotation(0.0F, 0.0F, 0.0F)
        );
        bodyFinLong.addOrReplaceChild("tail13", CubeListBuilder.create()
                        .texOffs(26, -5)
                        .addBox(0.0F, -13.0F, -5.0F, 0.0F, 13.0F, 5.0F),
                PartPose.rotation(0.0F, 0.0F, 0.0F)
        );
        bodyFinLong.addOrReplaceChild("tail14", CubeListBuilder.create()
                        .texOffs(26, -5)
                        .addBox(0.0F, -14.0F, -5.0F, 0.0F, 14.0F, 5.0F),
                PartPose.rotation(0.0F, 0.0F, 0.0F)
        );
        bodyFinLong.addOrReplaceChild("tail15", CubeListBuilder.create()
                        .texOffs(26, -5)
                        .addBox(0.0F, -15.0F, -5.0F, 0.0F, 15.0F, 5.0F),
                PartPose.rotation(0.0F, 0.0F, 0.0F)
        );
        bodyFinLong.addOrReplaceChild("tail16", CubeListBuilder.create()
                        .texOffs(26, -5)
                        .addBox(0.0F, -16.0F, -5.0F, 0.0F, 16.0F, 5.0F),
                PartPose.rotation(0.0F, 0.0F, 0.0F)
        );

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    public ModelEnhancedAxolotl(ModelPart modelPart) {
        this.theAxolotl = new WrappedModelPart(modelPart.getChild("base"), "base");
        this.theAxolotl.modelPart.xRot = -(float)Math.PI * 0.5F;
        this.head = new WrappedModelPart(modelPart.getChild("head"), "head");
        this.headLong = new WrappedModelPart(modelPart.getChild("headLong"), "headLong");
        this.body = new WrappedModelPart(modelPart.getChild("body"), "body");
        this.bodyLong = new WrappedModelPart(modelPart.getChild("bodyLong"), "bodyLong");
        this.gillsTop = new WrappedModelPart(modelPart.getChild("gillsTop"), "gillsTop");
        this.gillsLeft = new WrappedModelPart(modelPart.getChild("gillsLeft"), "gillsLeft");
        this.gillsLeft.modelPart.xRot = -(float)Math.PI * 0.5F;
        this.gillsRight = new WrappedModelPart(modelPart.getChild("gillsRight"), "gillsRight");
        this.gillsRight.modelPart.xRot = -(float)Math.PI * 0.5F;
        this.bodyFin = new WrappedModelPart(modelPart.getChild("bodyFin"), "bodyFin");
        this.bodyFinLong = new WrappedModelPart(modelPart.getChild("bodyFinLong"), "bodyFinLong");
        this.tail12 = new WrappedModelPart(modelPart.getChild("tail12"), "tail12");
        this.tail13 = new WrappedModelPart(modelPart.getChild("tail13"), "tail13");
        this.tail14 = new WrappedModelPart(modelPart.getChild("tail14"), "tail14");
        this.tail15 = new WrappedModelPart(modelPart.getChild("tail15"), "tail15");
        this.tail16 = new WrappedModelPart(modelPart.getChild("tail16"), "tail16");
        this.legFrontLeft = new WrappedModelPart(modelPart.getChild("legFrontLeft"), "legFrontLeft");
        this.legFrontRight = new WrappedModelPart(modelPart.getChild("legFrontRight"), "legFrontRight");
        this.legBackLeft = new WrappedModelPart(modelPart.getChild("legBackLeft"), "legBackLeft");
        this.legBackRight = new WrappedModelPart(modelPart.getChild("legBackRight"), "legBackRight");
        this.collar = new WrappedModelPart(modelPart.getChild("collar"), "collar");

        this.theAxolotl.addChild(this.head);
        this.theAxolotl.addChild(this.headLong);
        this.theAxolotl.addChild(this.body);
        this.theAxolotl.addChild(this.bodyLong);
        this.head.addChild(this.gillsTop);
        this.head.addChild(this.gillsLeft);
        this.head.addChild(this.gillsRight);
        this.headLong.addChild(this.gillsTop);
        this.headLong.addChild(this.gillsLeft);
        this.headLong.addChild(this.gillsRight);
        this.body.addChild(this.legFrontLeft);
        this.body.addChild(this.legFrontRight);
        this.body.addChild(this.legBackLeft);
        this.body.addChild(this.legBackRight);
        this.body.addChild(this.bodyFin);
        this.body.addChild(this.collar);
        this.bodyLong.addChild(this.legFrontLeft);
        this.bodyLong.addChild(this.legFrontRight);
        this.bodyLong.addChild(this.legBackLeft);
        this.bodyLong.addChild(this.legBackRight);
        this.bodyLong.addChild(this.bodyFinLong);
        this.bodyLong.addChild(this.collar);
        this.bodyFin.addChild(this.tail12);
        this.bodyFin.addChild(this.tail13);
        this.bodyFin.addChild(this.tail14);
        this.bodyFin.addChild(this.tail15);
        this.bodyFin.addChild(this.tail16);
        this.bodyFinLong.addChild(this.tail12);
        this.bodyFinLong.addChild(this.tail13);
        this.bodyFinLong.addChild(this.tail14);
        this.bodyFinLong.addChild(this.tail15);
        this.bodyFinLong.addChild(this.tail16);
    }

    @Override
    public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        AxolotlModelData axolotlModelData = getCreateAxolotlModelData(entityIn);

        this.currentAxolotl = entityIn.getId();
        int randomAnimationOffset = (int)(2000 * axolotlModelData.random);

        float gillRotation = 0.4F * Mth.sin((ageInTicks+randomAnimationOffset) * 0.1F);

        this.gillsTop.modelPart.xRot = ((float) Math.PI * 0.0F) + gillRotation;
        this.gillsLeft.modelPart.zRot = ((float) Math.PI * 0.4F) + gillRotation;
        this.gillsRight.modelPart.zRot = ((float) Math.PI * -0.4F) - gillRotation;

        this.tail12.modelPart.zRot = Mth.sin((ageInTicks+randomAnimationOffset) * 0.1F);
        this.tail13.modelPart.zRot = this.tail12.modelPart.zRot;
        this.tail14.modelPart.zRot = this.tail12.modelPart.zRot;
        this.tail15.modelPart.zRot = this.tail12.modelPart.zRot;
        this.tail16.modelPart.zRot = this.tail12.modelPart.zRot;

        Phenotype axolotl = axolotlModelData.phenotype;

        if (axolotl!=null) {
            float pi = (float) Math.PI;

            this.head.modelPart.xRot = pi + (headPitch * (pi / 180F));
            this.head.modelPart.zRot = netHeadYaw * (pi / 180F);
            ModelHelper.copyModelRotations(this.head.modelPart, this.headLong.modelPart);

            this.collar.modelPart.xRot = (float) Math.PI * -0.1F + (headPitch * (pi / 180F) * 0.5F);
            this.collar.modelPart.zRot = netHeadYaw * (pi / 180F) * 0.5F;

            limbSwing = limbSwing * 0.6666F;

            this.legFrontLeft.modelPart.zRot = pi * 0.5F + (Mth.cos(limbSwing + pi) * 1.4F * limbSwingAmount);
            this.legFrontRight.modelPart.zRot = -pi * 0.5F + (Mth.cos(limbSwing) * 1.4F * limbSwingAmount);
            this.legBackLeft.modelPart.zRot = pi * 0.5F + (Mth.cos(limbSwing + pi) * 1.4F * limbSwingAmount);
            this.legBackRight.modelPart.zRot = -pi * 0.5F + (Mth.cos(limbSwing) * 1.4F * limbSwingAmount);
            this.legFrontRight.modelPart.xRot = pi * 0.2F;
            this.legFrontLeft.modelPart.xRot = pi * 0.2F;
            this.legBackRight.modelPart.xRot = pi * 0.2F;
            this.legBackLeft.modelPart.xRot = pi * 0.2F;

            this.legBackLeft.modelPart.y = axolotl.isLong ? -11.5F : -9.5F;
            this.legBackRight.modelPart.y = axolotl.isLong ? -11.5F : -9.5F;
        }
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        AxolotlModelData axolotlModelData = getAxolotlModelData();
        Phenotype axolotl = axolotlModelData.phenotype;

        if (axolotl!=null) {

            this.tail12.modelPart.visible = false;
            this.tail13.modelPart.visible = false;
            this.tail14.modelPart.visible = false;
            this.tail15.modelPart.visible = false;
            this.tail16.modelPart.visible = false;

            if (axolotl.isLong) {
                this.head.modelPart.visible = false;
                this.headLong.modelPart.visible = true;
                this.body.modelPart.visible = false;
                this.bodyLong.modelPart.visible = true;
                switch (axolotl.tailLength) {
                    case EXTRALONG:
                        this.tail16.modelPart.visible = true;
                        break;
                    case LONG:
                        this.tail15.modelPart.visible = true;
                        break;
                    case NORMAL:
                    default:
                        this.tail14.modelPart.visible = true;
                        break;
                }
            } else {
                this.head.modelPart.visible = true;
                this.headLong.modelPart.visible = false;
                this.body.modelPart.visible = true;
                this.bodyLong.modelPart.visible = false;
                switch (axolotl.tailLength) {
                    case EXTRALONG:
                        this.tail14.modelPart.visible = true;
                        break;
                    case LONG:
                        this.tail13.modelPart.visible = true;
                        break;
                    case NORMAL:
                    default:
                        this.tail12.modelPart.visible = true;
                        break;
                }
            }

            this.collar.modelPart.visible = axolotlModelData.collar;

            gaRender(this.theAxolotl, null, Collections.emptyList(), poseStack, vertexConsumer, packedLightIn, packedOverlayIn, red, green, blue, alpha, false);
        }
    }

    private class AxolotlModelData {
        Phenotype phenotype;
        boolean sleeping = false;
        boolean hasEggs = false;
        int blink = 0;
        boolean collar = false;
        int lastAccessed = 0;
        int dataReset = 0;
        long clientGameTime = 0;
        float age = 0.0F;
        float random;
    }

    private AxolotlModelData getAxolotlModelData() {
        if (this.currentAxolotl == null || !axolotlModelDataCache.containsKey(this.currentAxolotl)) {
            return new AxolotlModelData();
        }
        return axolotlModelDataCache.get(this.currentAxolotl);
    }

    private AxolotlModelData getCreateAxolotlModelData(T enhancedAxolotl) {
        clearCacheTimer++;
        if(clearCacheTimer > 50000) {
            axolotlModelDataCache.values().removeIf(value -> value.lastAccessed==1);
            for (AxolotlModelData turtleModelData : axolotlModelDataCache.values()){
                turtleModelData.lastAccessed = 1;
            }
            clearCacheTimer = 0;
        }

        if (axolotlModelDataCache.containsKey(enhancedAxolotl.getId())) {
            AxolotlModelData axolotlModelData = axolotlModelDataCache.get(enhancedAxolotl.getId());
            axolotlModelData.lastAccessed = 0;
            axolotlModelData.dataReset++;
//            turtleModelData.sleeping = enhancedTurtle.isAnimalSleeping();
            axolotlModelData.collar = hasCollar(enhancedAxolotl.getEnhancedInventory());
            axolotlModelData.blink = enhancedAxolotl.getBlink();
            axolotlModelData.hasEggs = enhancedAxolotl.hasEgg();
            if (axolotlModelData.age != 1.0F) {
                axolotlModelData.age = enhancedAxolotl.growthAmount();
            }
            axolotlModelData.clientGameTime = ((ClientLevel)enhancedAxolotl.level).getLevelData().getGameTime();

            return axolotlModelData;
        } else {
            AxolotlModelData axolotlModelData = new AxolotlModelData();
            if (enhancedAxolotl.getSharedGenes()!=null) {
                axolotlModelData.phenotype = new Phenotype(enhancedAxolotl.getSharedGenes().getAutosomalGenes());
            }
            axolotlModelData.sleeping = enhancedAxolotl.isAnimalSleeping();
            axolotlModelData.blink = enhancedAxolotl.getBlink();
            axolotlModelData.hasEggs = enhancedAxolotl.hasEgg();
            axolotlModelData.age = enhancedAxolotl.growthAmount();
            axolotlModelData.collar = hasCollar(enhancedAxolotl.getEnhancedInventory());
            axolotlModelData.clientGameTime = ((ClientLevel)enhancedAxolotl.level).getLevelData().getGameTime();
            axolotlModelData.random = ThreadLocalRandom.current().nextFloat();

            if(axolotlModelData.phenotype != null) {
                axolotlModelDataCache.put(enhancedAxolotl.getId(), axolotlModelData);
            }

            return axolotlModelData;
        }
    }

    private boolean hasCollar(SimpleContainer inventory) {
        for (int i = 1; i < 6; i++) {
            if (inventory.getItem(i).getItem() instanceof CustomizableCollar) {
                return true;
            }
        }
        return false;
    }

    private class Phenotype {
        boolean isLong;
        TailLength tailLength;

        Phenotype(int[] gene) {
            this.isLong = gene[32] == 2 && gene[33] == 2;

            if (gene[26] == gene[27]) {
                this.tailLength = gene[32] == 1 ? TailLength.NORMAL : TailLength.EXTRALONG;
            } else {
                this.tailLength = TailLength.LONG;
            }
        }
    }

    private enum TailLength {
        NORMAL,
        LONG,
        EXTRALONG
    }
}
