package mokiyoki.enhancedanimals.model;

import mokiyoki.enhancedanimals.entity.EnhancedLlama;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.model.ModelBase;
import net.minecraft.client.renderer.entity.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ModelEnhancedLlama extends ModelBase {

    private final ModelRenderer chest1;
    private final ModelRenderer chest2;

    private boolean banana = false;
    private boolean suri = false;
    private float coatlength = 0.0F;

    private final ModelRenderer head;
    private final ModelRenderer headShaved;
    private final ModelRenderer neckWool1;
    private final ModelRenderer neckWool2;
    private final ModelRenderer neckWool3;
    private final ModelRenderer neckWool4;
    private final ModelRenderer earsR;
    private final ModelRenderer earsL;
    private final ModelRenderer earsTopR;
    private final ModelRenderer earsTopL;
    private final ModelRenderer earsTopBananaR;
    private final ModelRenderer earsTopBananaL;
    private final ModelRenderer body;
    private final ModelRenderer bodyShaved;
    private final ModelRenderer body1;
    private final ModelRenderer body2;
    private final ModelRenderer body3;
    private final ModelRenderer body4;
    private final ModelRenderer tail;
    private final ModelRenderer tail1;
    private final ModelRenderer tail2;
    private final ModelRenderer tail3;
    private final ModelRenderer tail4;
    private final ModelRenderer leg1;
    private final ModelRenderer leg1Wool1;
    private final ModelRenderer leg1Wool2;
    private final ModelRenderer leg1Wool3;
    private final ModelRenderer leg1Wool4;
    private final ModelRenderer leg2;
    private final ModelRenderer leg2Wool1;
    private final ModelRenderer leg2Wool2;
    private final ModelRenderer leg2Wool3;
    private final ModelRenderer leg2Wool4;
    private final ModelRenderer leg3;
    private final ModelRenderer leg3Wool1;
    private final ModelRenderer leg3Wool2;
    private final ModelRenderer leg3Wool3;
    private final ModelRenderer leg3Wool4;
    private final ModelRenderer leg4;
    private final ModelRenderer leg4Wool1;
    private final ModelRenderer leg4Wool2;
    private final ModelRenderer leg4Wool3;
    private final ModelRenderer leg4Wool4;
    private final ModelRenderer toeOuterFrontR;
    private final ModelRenderer toeInnerFrontR;
    private final ModelRenderer toeOuterFrontL;
    private final ModelRenderer toeInnerFrontL;
    private final ModelRenderer toeOuterBackR;
    private final ModelRenderer toeInnerBackR;
    private final ModelRenderer toeOuterBackL;
    private final ModelRenderer toeInnerBackL;

    public ModelEnhancedLlama()
    {
        this.textureWidth = 96;
        this.textureHeight = 96;

        this.head = new ModelRenderer(this, 0, 0);
        this.head.addBox(-4.0F, -14.0F, 0.0F, 8, 18, 6, 0.0F); //head and neck
        this.head.setTextureOffset(28, 0);
        this.head.addBox(-2.0F, -12.0F, -4.0F, 4, 4, 4, 0.0F); //nose
        this.head.setRotationPoint(0, 5, -6);

        this.headShaved = new ModelRenderer(this, 0, 0);
        this.headShaved.addBox(-4.0F, -14.0F, 0.0F, 8, 6, 6, 0.0F); //head and neck
        this.headShaved.setTextureOffset(28, 0);
        this.headShaved.addBox(-2.0F, -12.0F, -4.0F, 4, 4, 4, 0.0F); //nose
        this.headShaved.setRotationPoint(0, 5, -6);
        this.headShaved.setTextureOffset(0, 5);
        this.headShaved.addBox(-4.0F, -9.0F, 1.0F, 8, 8, 6, -1.0F); //head and neck
        this.headShaved.setTextureOffset(0, 10);
        this.headShaved.addBox(-4.0F, -3.0F, 1.0F, 8, 8, 6, -1.0F); //head and neck

        this.neckWool1 = new ModelRenderer(this, 0, 10);
        this.neckWool1.addBox(-4.0F, -8.0F, 0.0F, 8, 8, 6, 0.5F); //head and neck
        this.neckWool1.setTextureOffset(0, 13);
        this.neckWool1.addBox(-4.0F, 1.0F, 0.0F, 8, 4, 6, 0.5F); //head and neck

        this.neckWool2 = new ModelRenderer(this, 0, 10);
        this.neckWool2.addBox(-4.0F, -8.0F, 0.0F, 8, 8, 6, 1.0F); //head and neck
        this.neckWool2.setTextureOffset(0, 13);
        this.neckWool2.addBox(-4.0F, 2.0F, 0.0F, 8, 4, 6, 1.0F); //head and neck

        this.neckWool3 = new ModelRenderer(this, 0, 10);
        this.neckWool3.addBox(-4.0F, -8.0F, 0.0F, 8, 8, 6, 1.5F); //head and neck
        this.neckWool3.setTextureOffset(0, 13);
        this.neckWool3.addBox(-4.0F, 3.0F, 0.0F, 8, 4, 6, 1.5F); //head and neck

        this.neckWool4 = new ModelRenderer(this, 0, 10);
        this.neckWool4.addBox(-4.0F, -8.0F, 0.0F, 8, 8, 6, 2.0F); //head and neck
        this.neckWool4.setTextureOffset(0, 13);
        this.neckWool4.addBox(-4.0F, 4.0F, 0.0F, 8, 4, 6, 2.0F); //head and neck

        this.earsR = new ModelRenderer(this, 44, 0);
        this.earsR.addBox(-4.0F, -17.0F, 2.0F, 3, 3, 2, 0.0F); //ear right

        this.earsL = new ModelRenderer(this, 54, 0);
        this.earsL.addBox(1.0F, -17.0F, 2.0F, 3, 3, 2, 0.0F); //ear left

        this.earsTopR = new ModelRenderer(this, 64, 0);
        this.earsTopR.addBox(-4.0F, -18.0F, 2.0F, 3, 1, 2, 0.0F); //ear right

        this.earsTopL = new ModelRenderer(this, 74, 0);
        this.earsTopL.addBox(1.0F, -18.0F, 2.0F, 3, 1, 2, 0.0F); //ear left

        this.earsTopBananaR = new ModelRenderer(this, 64, 0);
        this.earsTopBananaR.addBox(-3.5F, -18.0F, 2.0F, 3, 1, 2, 0.0F); //ear right
        this.earsTopBananaL = new ModelRenderer(this, 74, 0);
        this.earsTopBananaL.addBox(0.5F, -18.0F, 2.0F, 3, 1, 2, 0.0F); //ear left

        this.body = new ModelRenderer(this, 0, 39);
        this.body.addBox(-6.0F, 0.0F, 0.0F, 12, 10, 18);
        this.body.setRotationPoint(0.0F, 2.0F, -2.0F);

        this.bodyShaved = new ModelRenderer(this, 0, 39);
        this.bodyShaved.addBox(-6.0F, 1.0F, 0.0F, 12, 10, 18, -1.0F);
        this.bodyShaved.setRotationPoint(0.0F, 2.0F, -2.0F);

        this.body1 = new ModelRenderer(this, 0, 39);
        this.body1.addBox(-6.0F, 0.0F, 0.0F, 12, 10, 18, 0.5F);
        this.body1.setRotationPoint(0.0F, 2.0F, -2.0F);

        this.body2 = new ModelRenderer(this, 0, 39);
        this.body2.addBox(-6.0F, 0.0F, 0.0F, 12, 10, 18, 1.0F);
        this.body2.setRotationPoint(0.0F, 2.0F, -2.0F);

        this.body3 = new ModelRenderer(this, 0, 39);
        this.body3.addBox(-6.0F, 0.0F, 0.0F, 12, 10, 18, 1.5F);
        this.body3.setRotationPoint(0.0F, 2.0F, -2.0F);

        this.body4 = new ModelRenderer(this, 0, 39);
        this.body4.addBox(-6.0F, 0.0F, 0.0F, 12, 10, 18, 2.0F);
        this.body4.setRotationPoint(0.0F, 2.0F, -2.0F);

        this.chest1 = new ModelRenderer(this, 74, 44);
        this.chest1.addBox(-3.0F, 0.0F, 0.0F, 8, 8, 3);
        this.chest1.setRotationPoint(-8.5F, 3.0F, 8.0F);
        this.chest1.rotateAngleY = ((float)Math.PI / 2F);
        this.chest2 = new ModelRenderer(this, 74, 57);
        this.chest2.addBox(-3.0F, 0.0F, 0.0F, 8, 8, 3);
        this.chest2.setRotationPoint(5.5F, 3.0F, 8.0F);
        this.chest2.rotateAngleY = ((float)Math.PI / 2F);

        this.tail = new ModelRenderer(this, 0, 25);
        this.tail.addBox(-3.0F, -2.0F, 15.0F, 6, 6, 6);

        this.tail1 = new ModelRenderer(this, 0, 25);
        this.tail1.addBox(-3.0F, -2.0F, 15.0F, 6, 6, 6, 0.25F);

        this.tail2 = new ModelRenderer(this, 0, 25);
        this.tail2.addBox(-3.0F, -2.0F, 15.0F, 6, 6, 6, 0.5F);

        this.tail3 = new ModelRenderer(this, 0, 25);
        this.tail3.addBox(-3.0F, -2.0F, 15.0F, 6, 6, 6, 0.75F);

        this.tail4 = new ModelRenderer(this, 0, 25);
        this.tail4.addBox(-3.0F, -2.0F, 15.0F, 6, 6, 6, 1.0F);

        this.leg1 = new ModelRenderer(this, 0, 68);
        this.leg1.addBox(0.0F, 0.0F, 0.0F, 3, 11, 3);
        this.leg1.setRotationPoint(-5.0F, 12.0F,-1.0F);

        this.leg1Wool1 = new ModelRenderer(this, 0, 68);
        this.leg1Wool1.addBox(0.0F, 0.0F, 0.0F, 3, 7, 3, 0.5F);
        this.leg1Wool1.setRotationPoint(-5.0F, 12.0F,-1.0F);

        this.leg1Wool2 = new ModelRenderer(this, 0, 68);
        this.leg1Wool2.addBox(0.0F, 0.0F, 0.0F, 3, 7, 3, 1.0F);
        this.leg1Wool2.setRotationPoint(-5.0F, 12.0F,-1.0F);

        this.leg1Wool3 = new ModelRenderer(this, 0, 68);
        this.leg1Wool3.addBox(0.0F, 0.0F, 0.0F, 3, 7, 3, 1.5F);
        this.leg1Wool3.setRotationPoint(-5.0F, 12.0F,-1.0F);

        this.leg1Wool4 = new ModelRenderer(this, 0, 68);
        this.leg1Wool4.addBox(0.0F, 0.0F, 0.0F, 3, 7, 3, 2.0F);
        this.leg1Wool4.setRotationPoint(-5.0F, 12.0F,-1.0F);

        this.leg2 = new ModelRenderer(this, 12, 68);
        this.leg2.addBox(0.0F, 0.0F, 0.0F, 3, 11, 3);
        this.leg2.setRotationPoint(2.0F, 12.0F,-1.0F);

        this.leg2Wool1 = new ModelRenderer(this, 12, 68);
        this.leg2Wool1.addBox(0.0F, 0.0F, 0.0F, 3, 7, 3, 0.5F);
        this.leg2Wool1.setRotationPoint(2.0F, 12.0F,-1.0F);

        this.leg2Wool2 = new ModelRenderer(this, 12, 68);
        this.leg2Wool2.addBox(0.0F, 0.0F, 0.0F, 3, 7, 3, 1.0F);
        this.leg2Wool2.setRotationPoint(2.0F, 12.0F,-1.0F);

        this.leg2Wool3 = new ModelRenderer(this, 12, 68);
        this.leg2Wool3.addBox(0.0F, 0.0F, 0.0F, 3, 7, 3, 1.5F);
        this.leg2Wool3.setRotationPoint(2.0F, 12.0F,-1.0F);

        this.leg2Wool4 = new ModelRenderer(this, 12, 68);
        this.leg2Wool4.addBox(0.0F, 0.0F, 0.0F, 3, 7, 3, 2.0F);
        this.leg2Wool4.setRotationPoint(2.0F, 12.0F,-1.0F);

        this.leg3 = new ModelRenderer(this, 0, 82);
        this.leg3.addBox(0.0F, 0F, 0.0F, 3, 11, 3);
        this.leg3.setRotationPoint(-5.0F, 12.0F,12.0F);

        this.leg3Wool1 = new ModelRenderer(this, 0, 82);
        this.leg3Wool1.addBox(0.0F, 0.0F, 0.0F, 3, 7, 3, 0.5F);
        this.leg3Wool1.setRotationPoint(-5.0F, 12.0F,12.0F);

        this.leg3Wool2 = new ModelRenderer(this, 0, 82);
        this.leg3Wool2.addBox(0.0F, 0.0F, 0.0F, 3, 7, 3, 1.0F);
        this.leg3Wool2.setRotationPoint(-5.0F, 12.0F,12.0F);

        this.leg3Wool3 = new ModelRenderer(this, 0, 82);
        this.leg3Wool3.addBox(0.0F, 0.0F, 0.0F, 3, 7, 3, 1.5F);
        this.leg3Wool3.setRotationPoint(-5.0F, 12.0F,12.0F);

        this.leg3Wool4 = new ModelRenderer(this, 0, 82);
        this.leg3Wool4.addBox(0.0F, 0.0F, 0.0F, 3, 7, 3, 2.0F);
        this.leg3Wool4.setRotationPoint(-5.0F, 12.0F,12.0F);

        this.leg4 = new ModelRenderer(this, 12, 82);
        this.leg4.addBox(0.0F, 0.0F, 0.0F, 3, 11, 3);
        this.leg4.setRotationPoint(2.0F, 12.0F,12.0F);

        this.leg4Wool1 = new ModelRenderer(this, 12, 82);
        this.leg4Wool1.addBox(0.0F, 0.0F, 0.0F, 3, 7, 3, 0.5F);
        this.leg4Wool1.setRotationPoint(2.0F, 12.0F,12.0F);

        this.leg4Wool2 = new ModelRenderer(this, 12, 82);
        this.leg4Wool2.addBox(0.0F, 0.0F, 0.0F, 3, 7, 3, 1.0F);
        this.leg4Wool2.setRotationPoint(2.0F, 12.0F,12.0F);

        this.leg4Wool3 = new ModelRenderer(this, 12, 82);
        this.leg4Wool3.addBox(0.0F, 0.0F, 0.0F, 3, 7, 3, 1.5F);
        this.leg4Wool3.setRotationPoint(2.0F, 12.0F,12.0F);

        this.leg4Wool4 = new ModelRenderer(this, 12, 82);
        this.leg4Wool4.addBox(0.0F, 0.0F, 0.0F, 3, 7, 3, 2.0F);
        this.leg4Wool4.setRotationPoint(2.0F, 12.0F,12.0F);

        this.toeOuterFrontR = new ModelRenderer(this, 26, 70);
        this.toeOuterFrontR.addBox(-0.85F, 10.0F, -2.5F, 3, 3, 4, -0.75F);
        this.toeInnerFrontR = new ModelRenderer(this, 44, 70);
        this.toeInnerFrontR.addBox(0.75F, 10.0F, -2.5F, 3, 3, 4, -0.75F);

        this.toeOuterFrontL = new ModelRenderer(this, 62, 70);
        this.toeOuterFrontL.addBox(0.85F, 10.0F, -2.5F, 3, 3, 4, -0.75F);
        this.toeInnerFrontL = new ModelRenderer(this, 80, 70);
        this.toeInnerFrontL.addBox(-0.75F, 10.0F, -2.5F, 3, 3, 4, -0.75F);

        this.toeOuterBackR = new ModelRenderer(this, 26, 84);
        this.toeOuterBackR.addBox(-0.85F, 10.0F, -2.5F, 3, 3, 4, -0.75F);
        this.toeInnerBackR = new ModelRenderer(this, 44, 84);
        this.toeInnerBackR.addBox(0.75F, 10.0F, -2.5F, 3, 3, 4, -0.75F);

        this.toeOuterBackL = new ModelRenderer(this, 62, 84);
        this.toeOuterBackL.addBox(0.85F, 10.0F, -2.5F, 3, 3, 4, -0.75F);
        this.toeInnerBackL = new ModelRenderer(this, 80, 84);
        this.toeInnerBackL.addBox(-0.75F, 10.0F, -2.5F, 3, 3, 4, -0.75F);


//        leg1.addChild(toeOuterFrontR);
//        leg1.addChild(toeInnerFrontR);
//        leg2.addChild(toeOuterFrontL);
//        leg2.addChild(toeInnerFrontL);
//        leg3.addChild(toeOuterBackR);
//        leg3.addChild(toeInnerBackR);
//        leg4.addChild(toeOuterBackL);
//        leg4.addChild(toeInnerBackL);

    }

    private void setRotationOffset(ModelRenderer renderer, float x, float y, float z) {
        renderer.rotateAngleX = x;
        renderer.rotateAngleY = y;
        renderer.rotateAngleZ = z;
    }

    public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        this.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entityIn);
        EnhancedLlama enhancedLlama = (EnhancedLlama) entityIn;

        this.coatlength = enhancedLlama.getCoatLength();

        int[] genes = enhancedLlama.getSharedGenes();

        float size = 1;

        if (genes[0] < 3){
            size = size - 0.025F;
            if (genes[0] < 2){
                size = size - 0.025F;
            }
        }
        if (genes[1] < 3){
            size = size - 0.025F;
            if (genes[1] < 2){
                size = size - 0.025F;
            }
        }
        if (genes[2] < 3){
            size = size - 0.025F;
            if (genes[2] < 2){
                size = size - 0.025F;
            }
        }
        if (genes[3] < 3){
            size = size - 0.025F;
            if (genes[3] < 2){
                size = size - 0.025F;
            }
        }

        if (this.isChild) {
            GlStateManager.pushMatrix();
            GlStateManager.scalef(0.6F, 0.6F, 0.6F);
            GlStateManager.translatef(0.0F, 15.0F * scale, 0.0F);

            this.head.render(scale);
            this.earsR.render(scale);
            this.earsL.render(scale);
            this.earsTopR.render(scale);
            this.earsTopL.render(scale);

            if (banana){
                this.earsTopBananaR.render(scale);
                this.earsTopBananaL.render(scale);
            }else {
                this.earsTopR.render(scale);
                this.earsTopL.render(scale);
            }

            GlStateManager.popMatrix();
            GlStateManager.pushMatrix();
            GlStateManager.scalef(0.5F, 0.5F, 0.5F);
            GlStateManager.translatef(0.0F, 20.0F * scale, 0.0F);

            this.body.render(scale);
            this.tail.render(scale);

            GlStateManager.popMatrix();
            GlStateManager.pushMatrix();
            GlStateManager.scalef(0.5F, 0.7F, 0.5F);
            GlStateManager.translatef(0.0F, 10.0F * scale, 0.0F);

            this.leg1.render(scale);
            this.leg2.render(scale);
            this.leg3.render(scale);
            this.leg4.render(scale);
            this.toeOuterFrontR.render(scale);
            this.toeInnerFrontR.render(scale);
            this.toeOuterFrontL.render(scale);
            this.toeInnerFrontL.render(scale);
            this.toeOuterBackR.render(scale);
            this.toeInnerBackR.render(scale);
            this.toeOuterBackL.render(scale);
            this.toeInnerBackL.render(scale);

            GlStateManager.popMatrix();

        }else {

            GlStateManager.pushMatrix();
            GlStateManager.scalef(size, size, size);
            GlStateManager.translatef(0.0F, -1.5F + 1.5F / size, 0.0F);
            this.earsR.render(scale);
            this.earsL.render(scale);

            if (banana) {
                this.earsTopBananaR.render(scale);
                this.earsTopBananaL.render(scale);
            } else {
                this.earsTopR.render(scale);
                this.earsTopL.render(scale);
            }

            this.leg1.render(scale);
            this.leg2.render(scale);
            this.leg3.render(scale);
            this.leg4.render(scale);
            this.toeOuterFrontR.render(scale);
            this.toeInnerFrontR.render(scale);
            this.toeOuterFrontL.render(scale);
            this.toeInnerFrontL.render(scale);
            this.toeOuterBackR.render(scale);
            this.toeInnerBackR.render(scale);
            this.toeOuterBackL.render(scale);
            this.toeInnerBackL.render(scale);

            if (enhancedLlama.hasChest()) {
                this.chest1.render(scale);
                this.chest2.render(scale);
            }

            if (coatlength == -1 ) {
                this.headShaved.render(scale);
                this.bodyShaved.render(scale);
                this.tail.render(scale);
            } else {

                this.head.render(scale);

                if (coatlength == 0 ) {
                    this.body.render(scale);
                    this.tail.render(scale);
                } else if (coatlength == 1 ) {
                    this.neckWool1.render(scale);
                    this.body1.render(scale);
                    this.tail1.render(scale);
                    this.leg1Wool1.render(scale);
                    this.leg2Wool1.render(scale);
                    this.leg3Wool1.render(scale);
                    this.leg4Wool1.render(scale);
                } else if (coatlength == 2 ) {
                    this.neckWool1.render(scale);
                    this.body2.render(scale);
                    this.tail2.render(scale);
                    this.leg1Wool2.render(scale);
                    this.leg2Wool2.render(scale);
                    this.leg3Wool2.render(scale);
                    this.leg4Wool2.render(scale);
                } else if (coatlength == 3 ) {
                    this.neckWool3.render(scale);
                    this.body3.render(scale);
                    this.tail3.render(scale);
                    this.leg1Wool3.render(scale);
                    this.leg2Wool3.render(scale);
                    this.leg3Wool3.render(scale);
                    this.leg4Wool3.render(scale);
                } else if (coatlength == 4 ) {
                    this.neckWool4.render(scale);
                    this.body4.render(scale);
                    this.tail4.render(scale);
                    this.leg1Wool4.render(scale);
                    this.leg2Wool4.render(scale);
                    this.leg3Wool4.render(scale);
                    this.leg4Wool4.render(scale);
                }
            }
            GlStateManager.popMatrix();


        }
    }

    /**
     * Sets the model's various rotation angles. For bipeds, par1 and par2 are used for animating the movement of arms
     * and legs, where par1 represents the time(so that arms and legs swing back and forth) and par2 represents how
     * "far" arms and legs can swing at most.
     */
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn)
    {
        super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);
        this.head.rotateAngleX = headPitch * 0.017453292F;
        this.head.rotateAngleY = netHeadYaw * 0.017453292F;

//        this.body.rotateAngleX = ((float)Math.PI / 2F);
        this.leg1.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        this.leg2.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
        this.leg3.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
        this.leg4.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;

        copyModelAngles(head, headShaved);
        copyModelAngles(head, neckWool1);

        copyModelAngles(body, bodyShaved);
        copyModelAngles(body, body1);

        copyModelAngles(leg1, leg1Wool1);
        copyModelAngles(leg2, leg2Wool1);
        copyModelAngles(leg3, leg3Wool1);
        copyModelAngles(leg4, leg4Wool1);

        copyModelAngles(body, tail);
        copyModelAngles(body, tail1);

        if ( suri && coatlength >= 0 ) {
            if (coatlength >= 1) {
                this.neckWool1.rotationPointY = this.head.rotationPointY + (coatlength/2);
                this.body1.rotationPointY = this.body.rotationPointY + (coatlength/2);
                this.tail1.rotationPointY = this.body.rotationPointY + (coatlength/3);
                this.leg1Wool1.rotationPointY = this.leg1.rotationPointY + (coatlength/2);
                this.leg2Wool1.rotationPointY = this.leg2.rotationPointY + (coatlength/2);
                this.leg3Wool1.rotationPointY = this.leg3.rotationPointY + (coatlength/2);
                this.leg4Wool1.rotationPointY = this.leg4.rotationPointY + (coatlength/2);
            }
        }
        copyModelAngles(neckWool1, neckWool2);
        copyModelAngles(neckWool1, neckWool3);
        copyModelAngles(neckWool1, neckWool4);
        copyModelAngles(head, earsR);
        copyModelAngles(head, earsL);
        copyModelAngles(head, earsTopR);
        copyModelAngles(head, earsTopL);
        copyModelAngles(head, earsTopBananaR);
        copyModelAngles(head, earsTopBananaL);

        copyModelAngles(body, body2);
        copyModelAngles(body, body3);
        copyModelAngles(body, body4);

        copyModelAngles(tail1, tail2);
        copyModelAngles(tail1, tail3);
        copyModelAngles(tail1, tail4);

        //TODO fix the toes so they angle properly and maintain the angle while they walk

        copyModelAngles(leg1Wool1, leg1Wool2);
        copyModelAngles(leg1Wool1, leg1Wool3);
        copyModelAngles(leg1Wool1, leg1Wool4);
        copyModelAngles(leg1, toeOuterFrontR);
        copyModelAngles(leg1, toeInnerFrontR);


        copyModelAngles(leg2Wool1, leg2Wool2);
        copyModelAngles(leg2Wool1, leg2Wool3);
        copyModelAngles(leg2Wool1, leg2Wool4);
        copyModelAngles(leg2, toeOuterFrontL);
        copyModelAngles(leg2, toeInnerFrontL);

        copyModelAngles(leg3Wool1, leg3Wool2);
        copyModelAngles(leg3Wool1, leg3Wool3);
        copyModelAngles(leg3Wool1, leg3Wool4);
        copyModelAngles(leg3, toeOuterBackR);
        copyModelAngles(leg3, toeInnerBackR);

        copyModelAngles(leg4Wool1, leg4Wool2);
        copyModelAngles(leg4Wool1, leg4Wool3);
        copyModelAngles(leg4Wool1, leg4Wool4);
        copyModelAngles(leg4, toeOuterBackL);
        copyModelAngles(leg4, toeInnerBackL);

    }

    /**
     * Used for easily adding entity-dependent animations. The second and third float params here are the same second
     * and third as in the setRotationAngles method.
     */
    public void setLivingAnimations(EntityLivingBase entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTickTime)
    {
        int[] sharedGenes = ((EnhancedLlama)entitylivingbaseIn).getSharedGenes();

        this.body.rotationPointY = 2.0F;

        // banana ears
        if ( sharedGenes[18] != 1 && sharedGenes[19] != 1){
            if (sharedGenes[18] == 2 || sharedGenes[19] == 2){
                banana = true;
            }
        }

        //TODO make coatlength calculations less dumb

        if (sharedGenes[20] == 2 && sharedGenes[21] == 2){
            suri = true;
        }

    }
}
