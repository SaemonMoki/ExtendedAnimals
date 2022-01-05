package mokiyoki.enhancedanimals.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import mokiyoki.enhancedanimals.entity.EnhancedAxolotl;
import mokiyoki.enhancedanimals.items.CustomizableCollar;
import mokiyoki.enhancedanimals.model.util.GAModel;
import mokiyoki.enhancedanimals.model.util.ModelHelper;
import mokiyoki.enhancedanimals.model.util.WrappedModelPart;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
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

    public ModelEnhancedAxolotl(ModelPart baseModel) {

        this.theAxolotl = new ModelRenderer(this);
        this.theAxolotl.setRotationPoint(0.0F, 22.0F, -8.0F);
        this.theAxolotl.rotateAngleX = -(float)Math.PI*0.5F;

        this.head = new ModelRenderer(this, 0, 0);
        this.head.addBox(-4.0F, -5.0F, -4.0F, 8.0F, 5.0F, 5.0F, 0.0F);
        this.head.setRotationPoint(0.0F, -1.0F, -4.0F);

        this.headLong = new ModelRenderer(this, 0, 0);
        this.headLong.addBox(-4.0F, -6.0F, -4.0F, 8.0F, 6.0F, 5.0F, 0.0F);
        this.headLong.setRotationPoint(0.0F, -1.0F, -4.0F);

        this.gillsTop = new ModelRenderer(this, 34, 0);
        this.gillsTop.addBox(-5.0F, 0.0F, 0.0F, 10.0F, 0.0F, 5.0F, 0.0F);
        this.gillsTop.setRotationPoint(0.0F, -1.0F, 1.0F);

        this.gillsLeft = new ModelRenderer(this, 36, 0);
        this.gillsLeft.addBox(0.0F, -8.0F, 0.0F, 0.0F, 8.0F, 5.0F, 0.0F);
        this.gillsLeft.setRotationPoint(-4.0F, -1.0F, -4.0F);
        this.gillsLeft.rotateAngleX = -(float)Math.PI * 0.5F;

        this.gillsRight = new ModelRenderer(this, 36, 0);
        this.gillsRight.addBox(0.0F, -8.0F, 0.0F, 0.0F, 8.0F, 5.0F, 0.0F);
        this.gillsRight.setRotationPoint(4.0F, -1.0F, -4.0F);
        this.gillsRight.rotateAngleX = -(float)Math.PI * 0.5F;

        this.body = new ModelRenderer(this, 0, 12);
        this.body.addBox(-4.0F, -6.0F, -4.0F, 8.0F, 5.0F, 4.0F, 0.0F);
        this.body.setTextureOffset(0, 22);
        this.body.addBox(-4.0F, -12.0F, -4.0F, 8.0F, 6.0F, 4.0F, 0.0F);

        this.bodyLong = new ModelRenderer(this, 0, 12);
        this.bodyLong.addBox(-4.0F, -6.0F, -4.0F, 8.0F, 5.0F, 4.0F, 0.0F);
        this.bodyLong.setTextureOffset(0, 22);
        this.bodyLong.addBox(-4.0F, -14.0F, -4.0F, 8.0F, 8.0F, 4.0F, 0.0F);


        this.bodyFin = new ModelRenderer(this, 26, 9);
        this.bodyFin.addBox(0.0F, 0.0F, -5.0F, 0.0F, 9.0F, 5.0F, 0.0F);
        this.bodyFin.setRotationPoint(0.0F, -12.0F, 0.0F);

        this.bodyFinLong = new ModelRenderer(this, 26, 9);
        this.bodyFinLong.addBox(0.0F, 0.0F, -5.0F, 0.0F, 11.0F, 5.0F, 0.0F);
        this.bodyFinLong.setRotationPoint(0.0F, -14.0F, 0.0F);

        this.tail12 = new ModelRenderer(this, 26, -5);
        this.tail12.addBox(0.0F, -12.0F, -5.0F, 0.0F, 12.0F, 5.0F, 0.0F);
        this.tail12.setRotationPoint(0.0F, 0.0F, 0.0F);

        this.tail13 = new ModelRenderer(this, 26, -5);
        this.tail13.addBox(0.0F, -13.0F, -5.0F, 0.0F, 13.0F, 5.0F, 0.0F);
        this.tail13.setRotationPoint(0.0F, 0.0F, 0.0F);

        this.tail14 = new ModelRenderer(this, 26, -5);
        this.tail14.addBox(0.0F, -14.0F, -5.0F, 0.0F, 14.0F, 5.0F, 0.0F);
        this.tail14.setRotationPoint(0.0F, 0.0F, 0.0F);

        this.tail15 = new ModelRenderer(this, 26, -5);
        this.tail15.addBox(0.0F, -15.0F, -5.0F, 0.0F, 15.0F, 5.0F, 0.0F);
        this.tail15.setRotationPoint(0.0F, 0.0F, 0.0F);

        this.tail16 = new ModelRenderer(this, 26, -5);
        this.tail16.addBox(0.0F, -16.0F, -5.0F, 0.0F, 16.0F, 5.0F, 0.0F);
        this.tail16.setRotationPoint(0.0F, 0.0F, 0.0F);

        this.legFrontLeft = new ModelRenderer(this, 58, 2);
        this.legFrontLeft.addBox(-1.5F, 0.0F, 0.0F, 3.0F, 5.0F, 0.0F, 0.0F);
        this.legFrontLeft.setRotationPoint(-3.0F, -2.5F, -1.0F);

        this.legFrontRight = new ModelRenderer(this, 58, 9);
        this.legFrontRight.addBox(-1.5F, 0.0F, 0.0F, 3.0F, 5.0F, 0.0F, 0.0F);
        this.legFrontRight.setRotationPoint(3.0F, -2.5F, -1.0F);

        this.legBackLeft = new ModelRenderer(this, 58, 16);
        this.legBackLeft.addBox(-1.5F, 0.0F, 0.0F, 3.0F, 5.0F, 0.0F, 0.0F);
        this.legBackLeft.setRotationPoint(-3.0F, -9.5F, -1.0F);

        this.legBackRight = new ModelRenderer(this, 58, 23);
        this.legBackRight.addBox(-1.5F, 0.0F, 0.0F, 3.0F, 5.0F, 0.0F, 0.0F);
        this.legBackRight.setRotationPoint(3.0F, -9.5F, -1.0F);

        this.collar = new ModelRenderer(this, 36, 57);
        this.collar.addBox(-4.5F, -1.0F, -0.5F, 9, 2, 5);
        this.collar.setTextureOffset(35, 51);
        this.collar.addBox(0.0F, -1.5F, 3.5F, 0,  3, 3);
        this.collar.setTextureOffset(12, 37);
        this.collar.addBox(-1.5F, -1.5F, 5.0F, 3, 3, 3, -0.5F);
        this.collar.setRotationPoint(0.0F, -2.0F, -4.0F);
    }

    public static LayerDefinition createBodyLayer(CubeDeformation p_170726_) {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

//        partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -14.0F, -10.0F, 4.0F, 4.0F, 9.0F, p_170726_).texOffs(0, 14).addBox("neck", -4.0F, -16.0F, -6.0F, 8.0F, 18.0F, 6.0F, p_170726_).texOffs(17, 0).addBox("ear", -4.0F, -19.0F, -4.0F, 3.0F, 3.0F, 2.0F, p_170726_).texOffs(17, 0).addBox("ear", 1.0F, -19.0F, -4.0F, 3.0F, 3.0F, 2.0F, p_170726_), PartPose.offset(0.0F, 7.0F, -6.0F));
//        partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(29, 0).addBox(-6.0F, -10.0F, -7.0F, 12.0F, 18.0F, 10.0F, p_170726_), PartPose.offsetAndRotation(0.0F, 5.0F, 2.0F, ((float)Math.PI / 2F), 0.0F, 0.0F));
//        partdefinition.addOrReplaceChild("right_chest", CubeListBuilder.create().texOffs(45, 28).addBox(-3.0F, 0.0F, 0.0F, 8.0F, 8.0F, 3.0F, p_170726_), PartPose.offsetAndRotation(-8.5F, 3.0F, 3.0F, 0.0F, ((float)Math.PI / 2F), 0.0F));
//        partdefinition.addOrReplaceChild("left_chest", CubeListBuilder.create().texOffs(45, 41).addBox(-3.0F, 0.0F, 0.0F, 8.0F, 8.0F, 3.0F, p_170726_), PartPose.offsetAndRotation(5.5F, 3.0F, 3.0F, 0.0F, ((float)Math.PI / 2F), 0.0F));
//        int i = 4;
//        int j = 14;
//        CubeListBuilder cubelistbuilder = CubeListBuilder.create().texOffs(29, 29).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 14.0F, 4.0F, p_170726_);
//        partdefinition.addOrReplaceChild("right_hind_leg", cubelistbuilder, PartPose.offset(-3.5F, 10.0F, 6.0F));
//        partdefinition.addOrReplaceChild("left_hind_leg", cubelistbuilder, PartPose.offset(3.5F, 10.0F, 6.0F));
//        partdefinition.addOrReplaceChild("right_front_leg", cubelistbuilder, PartPose.offset(-3.5F, 10.0F, -5.0F));
//        partdefinition.addOrReplaceChild("left_front_leg", cubelistbuilder, PartPose.offset(3.5F, 10.0F, -5.0F));
//        return LayerDefinition.create(meshdefinition, 128, 64);
    }

    public ModelEnhancedAxolotl(ModelPart modelPart) {
        this.theAxolotl = new WrappedModelPart(modelPart.getChild("base"), "base");
        this.head = new WrappedModelPart(modelPart.getChild("head"), "head");
        this.headLong = new WrappedModelPart(modelPart.getChild("headLong"), "headLong");
        this.body = new WrappedModelPart(modelPart.getChild("body"), "body");
        this.bodyLong = new WrappedModelPart(modelPart.getChild("bodyLong"), "bodyLong");
        this.gillsTop = new WrappedModelPart(modelPart.getChild("gillsTop"), "gillsTop");
        this.gillsLeft = new WrappedModelPart(modelPart.getChild("gillsLeft"), "gillsLeft");
        this.gillsRight = new WrappedModelPart(modelPart.getChild("gillsRight"), "gillsRight");
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
    public void setupAnim(T p_102618_, float p_102619_, float p_102620_, float p_102621_, float p_102622_, float p_102623_) {

    }

    /**
     * Sets this entity's model rotation angles
     */
    @Override
    public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        AxolotlModelData axolotlModelData = getAxolotlModelData();
        Phenotype axolotl = axolotlModelData.phenotype;

        if (axolotl!=null) {
            float pi = (float) Math.PI;

            this.head.rotateAngleX = pi + (headPitch * (pi / 180F));
            this.head.rotateAngleZ = netHeadYaw * (pi / 180F);
            ModelHelper.copyModelRotations(this.head, this.headLong);

            this.collar.rotateAngleX = (float) Math.PI * -0.1F + (headPitch * (pi / 180F) * 0.5F);
            this.collar.rotateAngleZ = netHeadYaw * (pi / 180F) * 0.5F;

            limbSwing = limbSwing * 0.6666F;

            this.legFrontLeft.rotateAngleZ = pi * 0.5F + (MathHelper.cos(limbSwing + pi) * 1.4F * limbSwingAmount);
            this.legFrontRight.rotateAngleZ = -pi * 0.5F + (MathHelper.cos(limbSwing) * 1.4F * limbSwingAmount);
            this.legBackLeft.rotateAngleZ = pi * 0.5F + (MathHelper.cos(limbSwing + pi) * 1.4F * limbSwingAmount);
            this.legBackRight.rotateAngleZ = -pi * 0.5F + (MathHelper.cos(limbSwing) * 1.4F * limbSwingAmount);
            this.legFrontRight.rotateAngleX = pi * 0.2F;
            this.legFrontLeft.rotateAngleX = pi * 0.2F;
            this.legBackRight.rotateAngleX = pi * 0.2F;
            this.legBackLeft.rotateAngleX = pi * 0.2F;

            this.legBackLeft.rotationPointY = axolotl.isLong ? -11.5F : -9.5F;
            this.legBackRight.rotationPointY = axolotl.isLong ? -11.5F : -9.5F;
//        if (!entityIn.isInWater() && entityIn.isOnGround()) {
//            this.legFrontRight.rotateAngleY = MathHelper.cos(limbSwing * 5.0F + (float)Math.PI) * 8.0F * limbSwingAmount;
//            this.legFrontRight.rotateAngleZ = 0.0F;
//            this.legFrontLeft.rotateAngleY = MathHelper.cos(limbSwing * 5.0F) * 8.0F * limbSwingAmount;
//            this.legFrontLeft.rotateAngleZ = 0.0F;
//            this.legBackRight.rotateAngleY = MathHelper.cos(limbSwing * 5.0F + (float)Math.PI) * 3.0F * limbSwingAmount;
//            this.legBackRight.rotateAngleX = (float) Math.PI * 0.5F;
//            this.legBackLeft.rotateAngleY = MathHelper.cos(limbSwing * 5.0F) * 3.0F * limbSwingAmount;
//            this.legBackLeft.rotateAngleX = (float) Math.PI * 0.5F;
//        }
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

    @Override
    public void setLivingAnimations(T entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTickTime) {
        super.setLivingAnimations(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTickTime);
        AxolotlModelData axolotlModelData = getCreateAxolotlModelData(entitylivingbaseIn);

        this.currentAxolotl = entitylivingbaseIn.getEntityId();
        int randomAnimationOffset = (int)(2000 * axolotlModelData.random);

        float gillRotation = 0.4F * MathHelper.sin((entitylivingbaseIn.ticksExisted+randomAnimationOffset) * 0.1F);

        this.gillsTop.rotateAngleX = ((float) Math.PI * 0.0F) + gillRotation;
        this.gillsLeft.rotateAngleZ = ((float) Math.PI * 0.4F) + gillRotation;
        this.gillsRight.rotateAngleZ = ((float) Math.PI * -0.4F) - gillRotation;

        this.tail12.rotateAngleZ = MathHelper.sin((entitylivingbaseIn.ticksExisted+randomAnimationOffset) * 0.1F);
        this.tail13.rotateAngleZ = this.tail12.rotateAngleZ;
        this.tail14.rotateAngleZ = this.tail12.rotateAngleZ;
        this.tail15.rotateAngleZ = this.tail12.rotateAngleZ;
        this.tail16.rotateAngleZ = this.tail12.rotateAngleZ;
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
            axolotlModelData.clientGameTime = (((ClientWorld)enhancedAxolotl.world).getWorldInfo()).getGameTime();

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
            axolotlModelData.clientGameTime = (((ClientWorld)enhancedAxolotl.world).getWorldInfo()).getGameTime();
            axolotlModelData.random = ThreadLocalRandom.current().nextFloat();

            if(axolotlModelData.phenotype != null) {
                axolotlModelDataCache.put(enhancedAxolotl.getId(), axolotlModelData);
            }

            return axolotlModelData;
        }
    }

    private boolean hasCollar(Inventory inventory) {
        for (int i = 1; i < 6; i++) {
            if (inventory.getStackInSlot(i).getItem() instanceof CustomizableCollar) {
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
