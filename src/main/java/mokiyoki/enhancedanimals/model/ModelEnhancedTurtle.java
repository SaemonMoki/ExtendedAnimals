package mokiyoki.enhancedanimals.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import mokiyoki.enhancedanimals.config.EanimodCommonConfig;
import mokiyoki.enhancedanimals.entity.EnhancedTurtle;
import mokiyoki.enhancedanimals.items.CustomizableCollar;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.inventory.Inventory;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.HashMap;
import java.util.Map;

@OnlyIn(Dist.CLIENT)
public class ModelEnhancedTurtle<T extends EnhancedTurtle> extends EntityModel<T> {

    private Map<Integer, TurtleModelData> turtleModelDataCache = new HashMap<>();
    private int clearCacheTimer = 0;

    private final ModelRenderer pregnant;
    protected ModelRenderer headModel = new ModelRenderer(this, 0, 0);
    protected ModelRenderer body;
    protected ModelRenderer legBackRight;
    protected ModelRenderer legBackLeft;
    protected ModelRenderer legFrontRight;
    protected ModelRenderer legFrontLeft;
    protected ModelRenderer eyelids;
    protected ModelRenderer collar;

    private Integer currentTurtle = null;

    public ModelEnhancedTurtle(float scale) {
        this.textureWidth = 128;
        this.textureHeight = 64;
        this.headModel = new ModelRenderer(this, 3, 0);
        this.headModel.addBox(-3.0F, -1.0F, -3.0F, 6.0F, 5.0F, 6.0F, 0.0F);
        this.headModel.setRotationPoint(0.0F, 19.0F, -10.0F);
        this.eyelids = new ModelRenderer(this, 13, 6);
        this.eyelids.addBox(0.0F, 1.0F, 2.0F, 2.0F, 1.0F, 1.0F, 0.001F);
        this.eyelids.setTextureOffset(4, 6);
        this.eyelids.addBox(0.0F, 1.0F, -3.0F, 2.0F, 1.0F, 1.0F, 0.001F);
        this.eyelids.rotateAngleY = (float) Math.PI * 0.5F;
        this.headModel.addChild(this.eyelids);
        this.body = new ModelRenderer(this);
        this.body.setTextureOffset(7, 37).addBox(-9.5F, 3.0F, -10.0F, 19.0F, 20.0F, 6.0F, 0.0F);
        this.body.setTextureOffset(31, 1).addBox(-5.5F, 3.0F, -13.0F, 11.0F, 18.0F, 3.0F, 0.0F);
        this.body.setRotationPoint(0.0F, 11.0F, -10.0F);
        this.pregnant = new ModelRenderer(this);
        this.pregnant.setTextureOffset(70, 33).addBox(-4.5F, 3.0F, -14.0F, 9.0F, 18.0F, 1.0F, 0.0F);
        this.pregnant.setRotationPoint(0.0F, 11.0F, -10.0F);
        int i = 1;
        this.legBackRight = new ModelRenderer(this, 1, 23);
        this.legBackRight.addBox(-2.0F, 0.0F, 0.0F, 4.0F, 1.0F, 10.0F, 0.0F);
        this.legBackRight.setRotationPoint(-3.5F, 22.0F, 11.0F);
        this.legBackLeft = new ModelRenderer(this, 1, 12);
        this.legBackLeft.addBox(-2.0F, 0.0F, 0.0F, 4.0F, 1.0F, 10.0F, 0.0F);
        this.legBackLeft.setRotationPoint(3.5F, 22.0F, 11.0F);
        this.legFrontRight = new ModelRenderer(this, 27, 30);
        this.legFrontRight.addBox(-13.0F, 0.0F, -2.0F, 13.0F, 1.0F, 5.0F, 0.0F);
        this.legFrontRight.setRotationPoint(-5.0F, 21.0F, -4.0F);
        this.legFrontLeft = new ModelRenderer(this, 27, 24);
        this.legFrontLeft.addBox(0.0F, 0.0F, -2.0F, 13.0F, 1.0F, 5.0F, 0.0F);
        this.legFrontLeft.setRotationPoint(5.0F, 21.0F, -4.0F);

        this.collar = new ModelRenderer(this, 59, 54);
        this.collar.addBox(-3.5F, -1.0F, -0.5F, 7, 2, 7);
        this.collar.setTextureOffset(82, 14);
        this.collar.addBox(0.0F, -1.5F, 5.5F, 0,  3, 3);
        this.collar.setTextureOffset(59, 0);
        this.collar.addBox(-1.5F, -1.5F, 7.0F, 3, 3, 3, -0.5F);
        this.collar.setRotationPoint(0, -1.5F, 2F);
        this.collar.rotateAngleX = (float) Math.PI * -0.5F;

        this.headModel.addChild(this.collar);
    }

    /**
     * Sets this entity's model rotation angles
     */
    @Override
    public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.headModel.rotateAngleX = headPitch * ((float)Math.PI / 180F);
        this.headModel.rotateAngleY = netHeadYaw * ((float)Math.PI / 180F);
        this.body.rotateAngleX = ((float)Math.PI / 2F);
        this.legBackRight.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        this.legBackLeft.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
        this.legFrontRight.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
        this.legFrontLeft.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;

        this.legBackRight.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F * 0.6F) * 0.5F * limbSwingAmount;
        this.legBackLeft.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F * 0.6F + (float)Math.PI) * 0.5F * limbSwingAmount;
        this.legFrontRight.rotateAngleZ = MathHelper.cos(limbSwing * 0.6662F * 0.6F + (float)Math.PI) * 0.5F * limbSwingAmount;
        this.legFrontLeft.rotateAngleZ = MathHelper.cos(limbSwing * 0.6662F * 0.6F) * 0.5F * limbSwingAmount;
        this.legFrontRight.rotateAngleX = 0.0F;
        this.legFrontLeft.rotateAngleX = 0.0F;
        this.legFrontRight.rotateAngleY = 0.0F;
        this.legFrontLeft.rotateAngleY = 0.0F;
        this.legBackRight.rotateAngleY = 0.0F;
        this.legBackLeft.rotateAngleY = 0.0F;
        this.pregnant.rotateAngleX = ((float)Math.PI / 2F);
        if (!entityIn.isInWater() && entityIn.isOnGround()) {
            float f = entityIn.isDigging() ? 4.0F : 1.0F;
            float f1 = entityIn.isDigging() ? 2.0F : 1.0F;
            float f2 = 5.0F;
            this.legFrontRight.rotateAngleY = MathHelper.cos(f * limbSwing * 5.0F + (float)Math.PI) * 8.0F * limbSwingAmount * f1;
            this.legFrontRight.rotateAngleZ = 0.0F;
            this.legFrontLeft.rotateAngleY = MathHelper.cos(f * limbSwing * 5.0F) * 8.0F * limbSwingAmount * f1;
            this.legFrontLeft.rotateAngleZ = 0.0F;
            this.legBackRight.rotateAngleY = MathHelper.cos(limbSwing * 5.0F + (float)Math.PI) * 3.0F * limbSwingAmount;
            this.legBackRight.rotateAngleX = 0.0F;
            this.legBackLeft.rotateAngleY = MathHelper.cos(limbSwing * 5.0F) * 3.0F * limbSwingAmount;
            this.legBackLeft.rotateAngleX = 0.0F;
        }
    }

    @Override
    public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        TurtleModelData turtleModelData = getTurtleModelData();
        float size = 1.0F;

        this.eyelids.showModel = turtleModelData.blink <= 12;

        this.collar.showModel = turtleModelData.collar;

        if (!(turtleModelData.birthTime == null) && !turtleModelData.birthTime.equals("") && !turtleModelData.birthTime.equals("0")) {
            int ageTime = (int)(turtleModelData.clientGameTime - Long.parseLong(turtleModelData.birthTime));
            if (ageTime < turtleModelData.adultAge) {
                size = ageTime < 0 ? 0 : (float) ageTime/(float)turtleModelData.adultAge;
                size = (1.0F + (size * 11.0F))/12.0F;
                float babyHead = (1.0F + (size*10.0F))/11.0F;
                matrixStackIn.push();
                matrixStackIn.scale(babyHead, babyHead, babyHead);
                matrixStackIn.translate(0, -1.52F + 1.52F/(babyHead), -0.08F + 0.08F/(babyHead));

                    this.headModel.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);

                matrixStackIn.pop();

            }
        }

        matrixStackIn.push();
        matrixStackIn.scale(size, size, size);
        matrixStackIn.translate(0, -1.5F + 1.5F/(size), 0);

        if (size == 1.0F) {
            this.headModel.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        }

        this.body.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.legFrontLeft.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.legFrontRight.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.legBackLeft.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.legBackRight.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.pregnant.showModel = turtleModelData.hasEggs;
        if (turtleModelData.hasEggs) {
            this.pregnant.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        }

        matrixStackIn.pop();
    }

    @Override
    public void setLivingAnimations(T entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTickTime) {
        super.setLivingAnimations(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTickTime);
        TurtleModelData turtleModelData = getCreateTurtleModelData(entitylivingbaseIn);
        this.currentTurtle = entitylivingbaseIn.getEntityId();
    }

    private class TurtleModelData {
        String birthTime;
        boolean sleeping = false;
        boolean hasEggs = false;
        int blink = 0;
        boolean collar = false;
        int lastAccessed = 0;
        int dataReset = 0;
        long clientGameTime = 0;
        int adultAge = 0;
    }

    private TurtleModelData getTurtleModelData() {
        if (this.currentTurtle == null || !turtleModelDataCache.containsKey(this.currentTurtle)) {
            return new TurtleModelData();
        }
        return turtleModelDataCache.get(this.currentTurtle);
    }

    private TurtleModelData getCreateTurtleModelData(T enhancedTurtle) {
        clearCacheTimer++;
        if(clearCacheTimer > 50000) {
            turtleModelDataCache.values().removeIf(value -> value.lastAccessed==1);
            for (ModelEnhancedTurtle.TurtleModelData turtleModelData : turtleModelDataCache.values()){
                turtleModelData.lastAccessed = 1;
            }
            clearCacheTimer = 0;
        }

        if (turtleModelDataCache.containsKey(enhancedTurtle.getEntityId())) {
            TurtleModelData turtleModelData = turtleModelDataCache.get(enhancedTurtle.getEntityId());
            turtleModelData.lastAccessed = 0;
            turtleModelData.dataReset++;
//            turtleModelData.sleeping = enhancedTurtle.isAnimalSleeping();
//            turtleModelData.collar = hasCollar(enhancedTurtle.getEnhancedInventory());
            turtleModelData.blink = enhancedTurtle.getBlink();
            turtleModelData.hasEggs = enhancedTurtle.hasEgg();
            turtleModelData.birthTime = enhancedTurtle.getBirthTime();
            turtleModelData.clientGameTime = (((ClientWorld)enhancedTurtle.world).getWorldInfo()).getGameTime();

            return turtleModelData;
        } else {
            TurtleModelData turtleModelData = new TurtleModelData();
//            turtleModelData.sleeping = enhancedTurtle.isAnimalSleeping();
            turtleModelData.blink = enhancedTurtle.getBlink();
            turtleModelData.hasEggs = enhancedTurtle.hasEgg();
            turtleModelData.birthTime = enhancedTurtle.getBirthTime();
            turtleModelData.collar = hasCollar(enhancedTurtle.getEnhancedInventory());
            turtleModelData.clientGameTime = (((ClientWorld)enhancedTurtle.world).getWorldInfo()).getGameTime();
            turtleModelData.adultAge = EanimodCommonConfig.COMMON.adultAgeTurtle.get();

            turtleModelDataCache.put(enhancedTurtle.getEntityId(), turtleModelData);

            return turtleModelData;
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
}
