//package mokiyoki.enhancedanimals.model;
//
//import com.mojang.blaze3d.matrix.MatrixStack;
//import com.mojang.blaze3d.vertex.IVertexBuilder;
//import mokiyoki.enhancedanimals.entity.EnhancedBee;
//import net.minecraft.client.renderer.entity.model.EntityModel;
//import net.minecraft.client.renderer.entity.model.ModelUtils;
//import net.minecraft.client.renderer.model.ModelRenderer;
//import net.minecraft.client.world.ClientWorld;
//import net.minecraft.util.math.MathHelper;
//import net.minecraft.world.storage.WorldInfo;
//import net.minecraftforge.api.distmarker.Dist;
//import net.minecraftforge.api.distmarker.OnlyIn;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@OnlyIn(Dist.CLIENT)
//public class ModelEnhancedBee<T extends EnhancedBee> extends EntityModel<T> {
//
//    private Map<Integer, BeeModelData> beeModelDataCache = new HashMap<>();
//    private int clearCacheTimer = 0;
//
//    private final ModelRenderer body;
//    private final ModelRenderer wingLeft;
//    private final ModelRenderer wingRight;
//    private final ModelRenderer antennaLeft;
//    private final ModelRenderer antennaRight;
//    private final ModelRenderer legs1;
//    private final ModelRenderer legs2;
//    private final ModelRenderer legs3;
//    private final ModelRenderer stinger;
//    private float bodyPitch;
//
//    private Integer currentBee = null;
//
//    public ModelEnhancedBee() {
//        this.textureWidth = 64;
//        this.textureHeight = 64;
//
//        this.body = new ModelRenderer(this, 0, 0);
//        this.body.addBox(-3.5F, 0.0F, 0.0F, 7, 7, 10);
//
//        this.wingLeft = new ModelRenderer(this, 0, 0);
//        this.wingLeft.addBox(-3.5F, 0.0F, 0.0F, 9, 9, 0, 0.001F);
//
//        this.wingRight = new ModelRenderer(this, 0, 0);
//        this.wingRight.addBox(-3.5F, 0.0F, 0.0F, 9, 9, 0, 0.001F);
//
//        this.antennaLeft = new ModelRenderer(this, 0, 0);
//        this.antennaLeft.addBox(-3.5F, 0.0F, 0.0F, 0, 5, 5);
//
//        this.antennaRight = new ModelRenderer(this, 0, 0);
//        this.antennaRight.addBox(-3.5F, 0.0F, 0.0F, 0, 5, 5);
//
//        this.legs1 = new ModelRenderer(this, 0, 0);
//        this.legs1.addBox(-3.5F, 0.0F, 0.0F, 5, 3, 0);
//
//        this.legs2 = new ModelRenderer(this, 0, 0);
//        this.legs2.addBox(-3.5F, 0.0F, 0.0F, 5, 3, 0);
//
//        this.legs3 = new ModelRenderer(this, 0, 0);
//        this.legs3.addBox(-3.5F, 0.0F, 0.0F, 5, 3, 0);
//
//        this.stinger = new ModelRenderer(this, 0, 0);
//        this.stinger.addBox(-3.5F, 0.0F, 0.0F, 0, 1, 3);
//
//        this.body.addChild(wingLeft);
//        this.body.addChild(wingRight);
//        this.body.addChild(antennaLeft);
//        this.body.addChild(antennaRight);
//        this.body.addChild(legs1);
//        this.body.addChild(legs2);
//        this.body.addChild(legs3);
//        this.body.addChild(stinger);
//
//    }
//
//
//    @Override
//    public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
//
//        this.body.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
//
//    }
//
//    @Override
//    public void setLivingAnimations(T entityIn, float limbSwing, float limbSwingAmount, float partialTick) {
//        BeeModelData beeModelData = getCreateBeeModelData(entityIn);
//        this.currentBee = entityIn.getEntityId();
//        this.bodyPitch = entityIn.getBodyPitch(partialTick);
//        boolean isFemale = true;
//        boolean hasStung = false;
//
//        if (isFemale && !hasStung) {
//            this.stinger.showModel = true;
//        } else {
//            this.stinger.showModel = false;
//        }
//
////        char[] uuidArry = beeModelData.uuidArray;
//    }
//
//    @Override
//    public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
//        this.wingRight.rotateAngleX = 0.0F;
//        this.antennaLeft.rotateAngleX = 0.0F;
//        this.antennaRight.rotateAngleX = 0.0F;
//        this.body.rotateAngleX = 0.0F;
//        this.body.rotationPointY = 19.0F;
//        boolean flag = entityIn.onGround && entityIn.getMotion().lengthSquared() < 1.0E-7D;
//        if (flag) {
//            this.wingRight.rotateAngleY = -0.2618F;
//            this.wingRight.rotateAngleZ = 0.0F;
//            this.wingLeft.rotateAngleX = 0.0F;
//            this.wingLeft.rotateAngleY = 0.2618F;
//            this.wingLeft.rotateAngleZ = 0.0F;
//            this.legs1.rotateAngleX = 0.0F;
//            this.legs2.rotateAngleX = 0.0F;
//            this.legs3.rotateAngleX = 0.0F;
//        } else {
//            float f = ageInTicks * 2.1F;
//            this.wingRight.rotateAngleY = 0.0F;
//            this.wingRight.rotateAngleZ = MathHelper.cos(f) * (float)Math.PI * 0.15F;
//            this.wingLeft.rotateAngleX = this.wingRight.rotateAngleX;
//            this.wingLeft.rotateAngleY = this.wingRight.rotateAngleY;
//            this.wingLeft.rotateAngleZ = -this.wingRight.rotateAngleZ;
//            this.legs1.rotateAngleX = ((float)Math.PI / 4F);
//            this.legs2.rotateAngleX = ((float)Math.PI / 4F);
//            this.legs3.rotateAngleX = ((float)Math.PI / 4F);
//            this.body.rotateAngleX = 0.0F;
//            this.body.rotateAngleY = 0.0F;
//            this.body.rotateAngleZ = 0.0F;
//        }
//
//        if (!entityIn.isAngry()) {
//            this.body.rotateAngleX = 0.0F;
//            this.body.rotateAngleY = 0.0F;
//            this.body.rotateAngleZ = 0.0F;
//            if (!flag) {
//                float f1 = MathHelper.cos(ageInTicks * 0.18F);
//                this.body.rotateAngleX = 0.1F + f1 * (float)Math.PI * 0.025F;
//                this.antennaLeft.rotateAngleX = f1 * (float)Math.PI * 0.03F;
//                this.antennaRight.rotateAngleX = f1 * (float)Math.PI * 0.03F;
//                this.legs1.rotateAngleX = -f1 * (float)Math.PI * 0.1F + ((float)Math.PI / 8F);
//                this.legs3.rotateAngleX = -f1 * (float)Math.PI * 0.05F + ((float)Math.PI / 4F);
//                this.body.rotationPointY = 19.0F - MathHelper.cos(ageInTicks * 0.18F) * 0.9F;
//            }
//        }
//
//        if (this.bodyPitch > 0.0F) {
//            this.body.rotateAngleX = ModelUtils.rotlerpRad(this.body.rotateAngleX, 3.0915928F, this.bodyPitch);
//        }
//    }
//
//    private class BeeModelData {
//        int[] beeGenes;
//        char[] uuidArray;
//        String birthTime;
//        float size = 1.0F;
//        boolean sleeping = false;
//        int lastAccessed = 0;
//        long clientGameTime = 0;
////        int dataReset = 0;
//    }
//
//    private BeeModelData getBeeModelData() {
//        if (this.currentBee == null || !beeModelDataCache.containsKey(this.currentBee)) {
//            return new BeeModelData();
//        }
//        return beeModelDataCache.get(this.currentBee);
//    }
//
//    private BeeModelData getCreateBeeModelData(T enhancedBee) {
//        clearCacheTimer++;
//        if(clearCacheTimer > 50000) {
//            beeModelDataCache.values().removeIf(value -> value.lastAccessed==1);
//            for (BeeModelData beeModelData : beeModelDataCache.values()){
//                beeModelData.lastAccessed = 1;
//            }
//            clearCacheTimer = 0;
//        }
//
//        if (beeModelDataCache.containsKey(enhancedBee.getEntityId())) {
//            BeeModelData beeModelData = beeModelDataCache.get(enhancedBee.getEntityId());
//            beeModelData.lastAccessed = 0;
////            pigModelData.dataReset++;
////            if (pigModelData.dataReset > 5000) {
//
////                pigModelData.dataReset = 0;
////            }
//            beeModelData.sleeping = enhancedBee.isAnimalSleeping();
//            beeModelData.clientGameTime = (((WorldInfo)((ClientWorld)enhancedBee.world).getWorldInfo()).getGameTime());
//
//            return beeModelData;
//        } else {
//            BeeModelData beeModelData = new BeeModelData();
//            beeModelData.beeGenes = enhancedBee.getSharedGenes();
//            beeModelData.size = enhancedBee.getSize();
//            beeModelData.sleeping = enhancedBee.isAnimalSleeping();
//            beeModelData.uuidArray = enhancedBee.getCachedUniqueIdString().toCharArray();
//            beeModelData.birthTime = enhancedBee.getBirthTime();
//            beeModelData.clientGameTime = (((WorldInfo)((ClientWorld)enhancedBee.world).getWorldInfo()).getGameTime());
//
//            if(beeModelData.beeGenes != null) {
//                beeModelDataCache.put(enhancedBee.getEntityId(), beeModelData);
//            }
//
//            return beeModelData;
//        }
//    }
//
//}
