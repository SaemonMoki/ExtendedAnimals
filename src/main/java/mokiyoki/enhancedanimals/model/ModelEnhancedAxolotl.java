package mokiyoki.enhancedanimals.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import mokiyoki.enhancedanimals.entity.EnhancedAxolotl;
import mokiyoki.enhancedanimals.items.CustomizableCollar;
import mokiyoki.enhancedanimals.model.util.ModelHelper;
import net.minecraft.client.model.EntityModel;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

@OnlyIn(Dist.CLIENT)
public class ModelEnhancedAxolotl<T extends EnhancedAxolotl> extends EntityModel<T> {

    private Map<Integer, AxolotlModelData> axolotlModelDataCache = new HashMap<>();
    private int clearCacheTimer = 0;

    protected ModelRenderer theAxolotl;
    protected ModelRenderer head;
    protected ModelRenderer headLong;
    protected ModelRenderer gillsTop;
    protected ModelRenderer gillsLeft;
    protected ModelRenderer gillsRight;
    protected ModelRenderer body;
    protected ModelRenderer bodyLong;
    protected ModelRenderer bodyFin;
    protected ModelRenderer bodyFinLong;
    protected ModelRenderer tail12;
    protected ModelRenderer tail13;
    protected ModelRenderer tail14;
    protected ModelRenderer tail15;
    protected ModelRenderer tail16;
    protected ModelRenderer legFrontRight;
    protected ModelRenderer legFrontLeft;
    protected ModelRenderer legBackLeft;
    protected ModelRenderer legBackRight;
    protected ModelRenderer collar;

    private Integer currentAxolotl = null;

    public ModelEnhancedAxolotl( ) {
        this.textureWidth = 64;
        this.textureHeight = 64;

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
    public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        AxolotlModelData axolotlModelData = getAxolotlModelData();
        Phenotype axolotl = axolotlModelData.phenotype;

        if (axolotl!=null) {
            float size = 1.0F;

//        if (!(axolotlModelData.birthTime == null) && !axolotlModelData.birthTime.equals("") && !axolotlModelData.birthTime.equals("0")) {
//            int ageTime = (int)(axolotlModelData.clientGameTime - Long.parseLong(axolotlModelData.birthTime));
//            if (ageTime < axolotlModelData.adultAge) {
//                size = ageTime < 0 ? 0 : (float) ageTime/(float)axolotlModelData.adultAge;
//                size = (1.0F + (size * 11.0F))/12.0F;
//                float babyHead = (1.0F + (size*10.0F))/11.0F;
//                matrixStackIn.push();
//                matrixStackIn.scale(babyHead, babyHead, babyHead);
//                matrixStackIn.translate(0, -1.52F + 1.52F/(babyHead), -0.08F + 0.08F/(babyHead));
//
//                    this.head.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
//
//                matrixStackIn.pop();
//
//            }
//        }

//        matrixStackIn.push();
//        matrixStackIn.scale(size, size, size);
//        matrixStackIn.translate(0, -1.5F + 1.5F/(size), 0);

//        if (size == 1.0F) {
//            this.head.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
//        }

            this.tail12.showModel = false;
            this.tail13.showModel = false;
            this.tail14.showModel = false;
            this.tail15.showModel = false;
            this.tail16.showModel = false;

            if (axolotl.isLong) {
                this.head.showModel = false;
                this.headLong.showModel = true;
                this.body.showModel = false;
                this.bodyLong.showModel = true;
                switch (axolotl.tailLength) {
                    case EXTRALONG:
                        this.tail16.showModel = true;
                        break;
                    case LONG:
                        this.tail15.showModel = true;
                        break;
                    case NORMAL:
                    default:
                        this.tail14.showModel = true;
                        break;
                }
            } else {
                this.head.showModel = true;
                this.headLong.showModel = false;
                this.body.showModel = true;
                this.bodyLong.showModel = false;
                switch (axolotl.tailLength) {
                    case EXTRALONG:
                        this.tail14.showModel = true;
                        break;
                    case LONG:
                        this.tail13.showModel = true;
                        break;
                    case NORMAL:
                    default:
                        this.tail12.showModel = true;
                        break;
                }
            }

            this.collar.showModel = axolotlModelData.collar;

            this.theAxolotl.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
//        this.body.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
//        this.legFrontLeft.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
//        this.legFrontRight.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
//        this.legBackLeft.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
//        this.legBackRight.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
//        if (axolotlModelData.hasEggs) {
//            make fat?
//        }

//        matrixStackIn.pop();
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

    @Override
    public void renderToBuffer(PoseStack p_103111_, VertexConsumer p_103112_, int p_103113_, int p_103114_, float p_103115_, float p_103116_, float p_103117_, float p_103118_) {

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

        if (axolotlModelDataCache.containsKey(enhancedAxolotl.getEntityId())) {
            AxolotlModelData axolotlModelData = axolotlModelDataCache.get(enhancedAxolotl.getEntityId());
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
                axolotlModelDataCache.put(enhancedAxolotl.getEntityId(), axolotlModelData);
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
