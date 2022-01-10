//package mokiyoki.enhancedanimals.model;
//
//import com.mojang.blaze3d.vertex.PoseStack;
//import com.mojang.blaze3d.vertex.VertexConsumer;
//import mokiyoki.enhancedanimals.config.EanimodCommonConfig;
//import mokiyoki.enhancedanimals.entity.EnhancedTurtle;
//import net.minecraft.client.model.EntityModel;
//import net.minecraft.client.model.geom.ModelPart;
//import net.minecraft.client.multiplayer.ClientLevel;
//import net.minecraft.util.Mth;
//import net.minecraftforge.api.distmarker.Dist;
//import net.minecraftforge.api.distmarker.OnlyIn;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@OnlyIn(Dist.CLIENT)
//public class ModelEnhancedTurtle<T extends EnhancedTurtle> extends EntityModel<T> {
//
//    private Map<Integer, TurtleModelData> turtleModelDataCache = new HashMap<>();
//    private int clearCacheTimer = 0;
//
//    private final ModelPart pregnant;
//    protected ModelPart headModel = new ModelPart(this, 0, 0);
//    protected ModelPart body;
//    protected ModelPart legBackRight;
//    protected ModelPart legBackLeft;
//    protected ModelPart legFrontRight;
//    protected ModelPart legFrontLeft;
//    protected ModelPart eyelids;
//    protected ModelPart collar;
//
//    private Integer currentTurtle = null;
//
//    public ModelEnhancedTurtle(float scale) {
//        this.texWidth = 128;
//        this.texHeight = 64;
//
//        this.headModel = new ModelPart(this, 3, 0);
//        this.headModel.addBox(-3.0F, -1.0F, -3.0F, 6.0F, 5.0F, 6.0F, 0.0F);
//        this.headModel.setPos(0.0F, 19.0F, -10.0F);
//        this.eyelids = new ModelPart(this, 13, 6);
//        this.eyelids.addBox(0.0F, 1.0F, 2.0F, 2.0F, 1.0F, 1.0F, 0.001F);
//        this.eyelids.texOffs(4, 6);
//        this.eyelids.addBox(0.0F, 1.0F, -3.0F, 2.0F, 1.0F, 1.0F, 0.001F);
//        this.eyelids.yRot = (float) Math.PI * 0.5F;
//        this.headModel.addChild(this.eyelids);
//
//        this.body = new ModelPart(this);
//        this.body.texOffs(7, 37).addBox(-9.5F, 3.0F, -10.0F, 19.0F, 20.0F, 6.0F, 0.0F);
//        this.body.texOffs(31, 1).addBox(-5.5F, 3.0F, -13.0F, 11.0F, 18.0F, 3.0F, 0.0F);
//        this.body.setPos(0.0F, 11.0F, -10.0F);
//
//        this.pregnant = new ModelPart(this);
//        this.pregnant.texOffs(70, 33).addBox(-4.5F, 3.0F, -14.0F, 9.0F, 18.0F, 1.0F, 0.0F);
//        this.pregnant.setPos(0.0F, 11.0F, -10.0F);
//
//        this.legBackRight = new ModelPart(this, 1, 23);
//        this.legBackRight.addBox(-2.0F, 0.0F, 0.0F, 4.0F, 1.0F, 10.0F, 0.0F);
//        this.legBackRight.setPos(-3.5F, 22.0F, 11.0F);
//        this.legBackLeft = new ModelPart(this, 1, 12);
//        this.legBackLeft.addBox(-2.0F, 0.0F, 0.0F, 4.0F, 1.0F, 10.0F, 0.0F);
//        this.legBackLeft.setPos(3.5F, 22.0F, 11.0F);
//        this.legFrontRight = new ModelPart(this, 27, 30);
//        this.legFrontRight.addBox(-13.0F, 0.0F, -2.0F, 13.0F, 1.0F, 5.0F, 0.0F);
//        this.legFrontRight.setPos(-5.0F, 21.0F, -4.0F);
//        this.legFrontLeft = new ModelPart(this, 27, 24);
//        this.legFrontLeft.addBox(0.0F, 0.0F, -2.0F, 13.0F, 1.0F, 5.0F, 0.0F);
//        this.legFrontLeft.setPos(5.0F, 21.0F, -4.0F);
//
//        this.collar = new ModelPart(this, 59, 54);
//        this.collar.addBox(-3.5F, -1.0F, -0.5F, 7, 2, 7);
//        this.collar.texOffs(82, 14);
//        this.collar.addBox(0.0F, -1.5F, 5.5F, 0,  3, 3);
//        this.collar.texOffs(59, 0);
//        this.collar.addBox(-1.5F, -1.5F, 7.0F, 3, 3, 3, -0.5F);
//        this.collar.setPos(0, -1.5F, 2F);
//        this.collar.xRot = (float) Math.PI * -0.5F;
//
//        this.headModel.addChild(this.collar);
//    }
//
//    /**
//     * Sets this entity's model rotation angles
//     */
//    @Override
//    public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
//        this.headModel.xRot = headPitch * ((float)Math.PI / 180F);
//        this.headModel.yRot = netHeadYaw * ((float)Math.PI / 180F);
//        this.body.xRot = ((float)Math.PI / 2F);
//
//        this.legFrontRight.xRot = 0.0F;
//        this.legFrontLeft.xRot = 0.0F;
//
//        this.pregnant.xRot = ((float)Math.PI / 2F);
//
//        if (!entityIn.isInWater() && entityIn.isOnGround()) {
//            float f = entityIn.isDigging() ? 4.0F : 1.0F;
//            float f1 = entityIn.isDigging() ? 2.0F : 1.0F;
//            float f2 = 5.0F;
//            this.legFrontLeft.yRot = Mth.cos(f * limbSwing * 5.0F) * 8.0F * limbSwingAmount * f1;
//            this.legFrontLeft.zRot = 0.0F;
//            this.legFrontRight.yRot = Mth.cos(f * limbSwing * 5.0F + (float)Math.PI) * 8.0F * limbSwingAmount * f1;
//            this.legFrontRight.zRot = 0.0F;
//            this.legBackLeft.xRot = 0.0F;
//            this.legBackLeft.yRot = Mth.cos(limbSwing * 5.0F) * 3.0F * limbSwingAmount;
//            this.legBackRight.xRot = 0.0F;
//            this.legBackRight.yRot = Mth.cos(limbSwing * 5.0F + (float)Math.PI) * 3.0F * limbSwingAmount;
//        } else {
//            this.legFrontLeft.yRot = 0.0F;
//            this.legFrontLeft.zRot = Mth.cos(limbSwing * 0.6662F * 0.6F) * 0.5F * limbSwingAmount;
//            this.legFrontRight.yRot = 0.0F;
//            this.legFrontRight.zRot = Mth.cos(limbSwing * 0.6662F * 0.6F + (float)Math.PI) * 0.5F * limbSwingAmount;
//            this.legBackLeft.xRot = Mth.cos(limbSwing * 0.6662F * 0.6F + (float)Math.PI) * 0.5F * limbSwingAmount;
//            this.legBackLeft.yRot = 0.0F;
//            this.legBackRight.xRot = Mth.cos(limbSwing * 0.6662F * 0.6F) * 0.5F * limbSwingAmount;
//            this.legBackRight.yRot = 0.0F;
//        }
//    }
//
//    @Override
//    public void renderToBuffer(PoseStack matrixStackIn, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
//        TurtleModelData turtleModelData = getTurtleModelData();
//        float size = 1.0F;
//
//        this.eyelids.visible = turtleModelData.blink <= 12;
//
//        this.collar.showModel = turtleModelData.collar;
//
//        if (!(turtleModelData.birthTime == null) && !turtleModelData.birthTime.equals("") && !turtleModelData.birthTime.equals("0")) {
//            int ageTime = (int)(turtleModelData.clientGameTime - Long.parseLong(turtleModelData.birthTime));
//            if (ageTime < turtleModelData.adultAge) {
//                size = ageTime < 0 ? 0 : (float) ageTime/(float)turtleModelData.adultAge;
//                size = (1.0F + (size * 11.0F))/12.0F;
//                float babyHead = (1.0F + (size*10.0F))/11.0F;
//                matrixStackIn.pushPose();
//                matrixStackIn.scale(babyHead, babyHead, babyHead);
//                matrixStackIn.translate(0, -1.52F + 1.52F/(babyHead), -0.08F + 0.08F/(babyHead));
//
//                    this.headModel.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
//
//                matrixStackIn.popPose();
//
//            }
//        }
//
//        matrixStackIn.pushPose();
//        matrixStackIn.scale(size, size, size);
//        matrixStackIn.translate(0, -1.5F + 1.5F/(size), 0);
//
//        if (size == 1.0F) {
//            this.headModel.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
//        }
//
//        this.body.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
//        this.legFrontLeft.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
//        this.legFrontRight.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
//        this.legBackLeft.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
//        this.legBackRight.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
//        this.pregnant.visible = turtleModelData.hasEggs;
//        if (turtleModelData.hasEggs) {
//            this.pregnant.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
//        }
//
//        matrixStackIn.popPose();
//    }
//
//    @Override
//    public void prepareMobModel(T entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTickTime) {
//        super.prepareMobModel(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTickTime);
//        TurtleModelData turtleModelData = getCreateTurtleModelData(entitylivingbaseIn);
//        this.currentTurtle = entitylivingbaseIn.getId();
//    }
//
//    private class TurtleModelData {
//        String birthTime;
//        boolean sleeping = false;
//        boolean hasEggs = false;
//        int blink = 0;
//        boolean collar = false;
//        int lastAccessed = 0;
//        int dataReset = 0;
//        long clientGameTime = 0;
//        int adultAge = 0;
//    }
//
//    private TurtleModelData getTurtleModelData() {
//        if (this.currentTurtle == null || !turtleModelDataCache.containsKey(this.currentTurtle)) {
//            return new TurtleModelData();
//        }
//        return turtleModelDataCache.get(this.currentTurtle);
//    }
//
//    private TurtleModelData getCreateTurtleModelData(T enhancedTurtle) {
//        clearCacheTimer++;
//        if(clearCacheTimer > 50000) {
//            turtleModelDataCache.values().removeIf(value -> value.lastAccessed==1);
//            for (ModelEnhancedTurtle.TurtleModelData turtleModelData : turtleModelDataCache.values()){
//                turtleModelData.lastAccessed = 1;
//            }
//            clearCacheTimer = 0;
//        }
//
//        if (turtleModelDataCache.containsKey(enhancedTurtle.getId())) {
//            TurtleModelData turtleModelData = turtleModelDataCache.get(enhancedTurtle.getId());
//            turtleModelData.lastAccessed = 0;
//            turtleModelData.dataReset++;
////            turtleModelData.sleeping = enhancedTurtle.isAnimalSleeping();
////            turtleModelData.collar = hasCollar(enhancedTurtle.getEnhancedInventory());
//            turtleModelData.blink = enhancedTurtle.getBlink();
//            turtleModelData.hasEggs = enhancedTurtle.hasEgg();
//            turtleModelData.birthTime = enhancedTurtle.getBirthTime();
//            turtleModelData.clientGameTime = (((ClientLevel)enhancedTurtle.level).getLevelData()).getGameTime();
//
//            return turtleModelData;
//        } else {
//            TurtleModelData turtleModelData = new TurtleModelData();
////            turtleModelData.sleeping = enhancedTurtle.isAnimalSleeping();
//            turtleModelData.blink = enhancedTurtle.getBlink();
//            turtleModelData.hasEggs = enhancedTurtle.hasEgg();
//            turtleModelData.birthTime = enhancedTurtle.getBirthTime();
//            turtleModelData.collar = hasCollar(enhancedTurtle.getEnhancedInventory());
//            turtleModelData.clientGameTime = (((ClientLevel)enhancedTurtle.level).getLevelData()).getGameTime();
//            turtleModelData.adultAge = EanimodCommonConfig.COMMON.adultAgeTurtle.get();
//
//            turtleModelDataCache.put(enhancedTurtle.getId(), turtleModelData);
//
//            return turtleModelData;
//        }
//    }
//
//    private boolean hasCollar(Inventory inventory) {
//        for (int i = 1; i < 6; i++) {
//            if (inventory.getStackInSlot(i).getItem() instanceof CustomizableCollar) {
//                return true;
//            }
//        }
//        return false;
//    }
//}
