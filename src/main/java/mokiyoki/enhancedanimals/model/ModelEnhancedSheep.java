package mokiyoki.enhancedanimals.model;

import mokiyoki.enhancedanimals.entity.EnhancedSheep;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.model.ModelBase;
import net.minecraft.client.renderer.entity.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ModelEnhancedSheep extends ModelBase {

    private float headRotationAngleX;
    private float f12 = 0F;
    private int coatlength = 0;

    private final ModelRenderer head;
    private final ModelRenderer earsR;
    private final ModelRenderer earsL;
    private final ModelRenderer body;
    private final ModelRenderer wool1;
    private final ModelRenderer wool2;
    private final ModelRenderer wool3;
    private final ModelRenderer wool4;
    private final ModelRenderer wool5;
    private final ModelRenderer wool6;
    private final ModelRenderer wool7;
    private final ModelRenderer wool8;
    private final ModelRenderer wool9;
    private final ModelRenderer wool10;
    private final ModelRenderer wool11;
    private final ModelRenderer wool12;
    private final ModelRenderer wool13;
    private final ModelRenderer wool14;
    private final ModelRenderer wool15;
    private final ModelRenderer tailBase;
    private final ModelRenderer tailMiddle;
    private final ModelRenderer tailTip;
    public final ModelRenderer leg1;
    public final ModelRenderer leg2;
    public final ModelRenderer leg3;
    public final ModelRenderer leg4;

    public ModelEnhancedSheep()
    {
        this.textureWidth = 64;
        this.textureHeight = 64;

        this.head = new ModelRenderer(this, 0, 0);
        this.head.addBox(-2.5F, -8.0F, -5.0F, 5, 4, 4, 0.0F); //skull
        this.head.setTextureOffset(0, 8);
        this.head.addBox(-2.0F, -8.0F, -8.0F, 4, 3, 3, 0.0F); //nose
//        this.head.addBox(-3.0F, 0.0F, -6.0F, 1, 2, 1, 0.0F); //horns duplicate it

        this.head.setTextureOffset(34, 0);
        this.head.addBox(-2.0F, -7.0F, -4.0F, 4, 9, 4, 0.0F); //neck

        this.earsR = new ModelRenderer(this, 50, 0);
        this.earsR.addBox(-5.0F, -8.5F, -2.0F, 3, 2, 1, 0.0F); //ear right
        this.earsL = new ModelRenderer(this, 50, 3);
        this.earsL.addBox(2.0F, -8.5F, -2.0F, 3, 2, 1, 0.0F); //ear left

//        float bodyY = 15;
        this.body = new ModelRenderer(this, 2, 0);
        this.body.addBox(-4.0F, 8.0F, -2.0F, 8, 6, 16, 0.0F);

        this.wool1 = new ModelRenderer(this, 2, 0);
        this.wool1.addBox(-4.0F, 8.0F, -2.0F, 8, 6, 16, 0.2F);

        this.wool2 = new ModelRenderer(this, 2, 0);
        this.wool2.addBox(-4.0F, 8.0F, -2.0F, 8, 6, 16, 0.4F);

        this.wool3 = new ModelRenderer(this, 2, 0);
        this.wool3.addBox(-4.0F, 8.0F, -2.0F, 8, 6, 16, 0.6F);

        this.wool4 = new ModelRenderer(this, 2, 0);
        this.wool4.addBox(-4.0F, 8.0F, -2.0F, 8, 6, 16, 0.8F);

        this.wool5 = new ModelRenderer(this, 2, 0);
        this.wool5.addBox(-4.0F, 8.0F, -2.0F, 8, 6, 16, 1.0F);

        this.wool6 = new ModelRenderer(this, 2, 0);
        this.wool6.addBox(-4.0F, 8.0F, -2.0F, 8, 6, 16, 1.2F);

        this.wool7 = new ModelRenderer(this, 2, 0);
        this.wool7.addBox(-4.0F, 8.0F, -2.0F, 8, 6, 16, 1.4F);

        this.wool8 = new ModelRenderer(this, 2, 0);
        this.wool8.addBox(-4.0F, 8.0F, -2.0F, 8, 6, 16, 1.6F);

        this.wool9 = new ModelRenderer(this, 2, 0);
        this.wool9.addBox(-4.0F, 8.0F, -2.0F, 8, 6, 16, 1.8F);

        this.wool10 = new ModelRenderer(this, 2, 0);
        this.wool10.addBox(-4.0F, 8.0F, -2.0F, 8, 6, 16, 2.0F);

        this.wool11 = new ModelRenderer(this, 2, 0);
        this.wool11.addBox(-4.0F, 8.0F, -2.0F, 8, 6, 16, 2.2F);

        this.wool12 = new ModelRenderer(this, 2, 0);
        this.wool12.addBox(-4.0F, 8.0F, -2.0F, 8, 6, 16, 2.4F);

        this.wool13 = new ModelRenderer(this, 2, 0);
        this.wool13.addBox(-4.0F, 8.0F, -2.0F, 8, 6, 16, 2.6F);

        this.wool14 = new ModelRenderer(this, 2, 0);
        this.wool14.addBox(-4.0F, 8.0F, -2.0F, 8, 6, 16, 2.8F);

        this.wool15 = new ModelRenderer(this, 2, 0);
        this.wool15.addBox(-4.0F, 8.0F, -2.0F, 8, 6, 16, 3.0F);

        this.tailBase = new ModelRenderer(this, 50, 6);
        this.tailBase.addBox(-1.0F, 0.0F, 0.0F, 2, 3, 1);
        this.tailBase.setRotationPoint(0.0F, 9.0F, 14.0F);

        this.tailMiddle = new ModelRenderer(this, 56, 6);
        this.tailMiddle.addBox(-0.5F, 0.0F, 0.0F, 1, 3, 1);
        this.tailMiddle.setRotationPoint(0.0F, 3.0F, 0.0F);

        this.tailTip = new ModelRenderer(this, 60, 6);
        this.tailTip.addBox(-0.5F, 0.0F, 0.0F, 1, 3, 1);
        this.tailTip.setRotationPoint(0.0F, 3.0F, 0.0F);

        this.leg1 = new ModelRenderer(this, 0, 22);
        this.leg1.addBox(0.0F, 0.0F, 0.0F, 3, 10, 3);
        this.leg1.setRotationPoint(-4, 14,-2);
        this.leg2 = new ModelRenderer(this, 12, 22);
        this.leg2.addBox(0.0F, 0.0F, 0.0F, 3, 10, 3);
        this.leg2.setRotationPoint(1, 14,-2);
        this.leg3 = new ModelRenderer(this, 24, 22);
        this.leg3.addBox(0.0F, 0.0F, 0.0F, 3, 10, 3);
        this.leg3.setRotationPoint(-4, 14,11);
        this.leg4 = new ModelRenderer(this, 36, 22);
        this.leg4.addBox(0.0F, 0.0F, 0.0F, 3, 10, 3);
        this.leg4.setRotationPoint(1, 14,11);

        this.tailBase.addChild(tailMiddle);
        this.tailMiddle.addChild(tailTip);
    }

    public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        this.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entityIn);

        EnhancedSheep enhancedSheep = (EnhancedSheep) entityIn;

        this.coatlength = enhancedSheep.getCoatLength();

        if (this.isChild) {
            GlStateManager.pushMatrix();
            GlStateManager.scalef(0.6F, 0.6F, 0.6F);
            GlStateManager.translatef(0.0F, 15.0F * scale, 0.0F);

            this.head.render(scale);
            this.earsR.render(scale);
            this.earsL.render(scale);

            GlStateManager.popMatrix();
            GlStateManager.pushMatrix();
            GlStateManager.scalef(0.5F, 0.5F, 0.5F);
            GlStateManager.translatef(0.0F, 20.0F * scale, 0.0F);

            this.body.render(scale);
            this.tailBase.render(scale);

            GlStateManager.popMatrix();
            GlStateManager.pushMatrix();
            GlStateManager.scalef(0.5F, 0.7F, 0.5F);
            GlStateManager.translatef(0.0F, 10.0F * scale, 0.0F);

            this.leg1.render(scale);
            this.leg2.render(scale);
            this.leg3.render(scale);
            this.leg4.render(scale);

            GlStateManager.popMatrix();

        }else {

            this.head.render(scale);
            this.earsR.render(scale);
            this.earsL.render(scale);
                this.body.render(scale);
             if (coatlength == 1){
                this.wool1.render(scale);
            }else if (coatlength == 2){
                this.wool2.render(scale);
            }else if (coatlength == 3){
                this.wool3.render(scale);
            }else if (coatlength == 4){
                this.wool4.render(scale);
            }else if (coatlength == 5){
                this.wool5.render(scale);
            }else if (coatlength == 6){
                this.wool6.render(scale);
            }else if (coatlength == 7){
                this.wool7.render(scale);
            }else if (coatlength == 8){
                this.wool8.render(scale);
            }else if (coatlength == 9){
                this.wool9.render(scale);
            }else if (coatlength == 10){
                this.wool10.render(scale);
            }else if (coatlength == 11){
                this.wool11.render(scale);
            }else if (coatlength == 12){
                this.wool12.render(scale);
            }else if (coatlength == 13){
                this.wool13.render(scale);
            }else if (coatlength == 14){
                this.wool14.render(scale);
            }else if (coatlength == 15){
                this.wool15.render(scale);
            }
            this.tailBase.render(scale);
            this.leg1.render(scale);
            this.leg2.render(scale);
            this.leg3.render(scale);
            this.leg4.render(scale);

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
        this.leg2.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
        this.leg3.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
        this.leg4.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;

        if (isChild) {
            this.tailBase.rotateAngleZ = MathHelper.cos(limbSwing * 0.6662F) * 1.3F * limbSwingAmount;
            this.tailMiddle.rotateAngleZ = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
            this.tailTip.rotateAngleZ = MathHelper.cos(limbSwing * 0.6662F) * 1.5F * limbSwingAmount;
        }else{
            this.tailBase.rotateAngleZ = MathHelper.cos(limbSwing * 0.6662F) * 0.4F * limbSwingAmount;
            this.tailMiddle.rotateAngleZ = MathHelper.cos(limbSwing * 0.6662F) * 0.5F * limbSwingAmount;
            this.tailTip.rotateAngleZ = MathHelper.cos(limbSwing * 0.6662F) * 0.6F * limbSwingAmount;
        }

        this.head.rotateAngleX = 1F + this.headRotationAngleX;   //might need to merge this with another line

        copyModelAngles(head, earsR);
        copyModelAngles(head, earsL);
        this.earsR.rotateAngleY = this.earsR.rotateAngleY - 0.15F;
        this.earsL.rotateAngleY = this.earsL.rotateAngleY + 0.15F;

    }

    /**
     * Used for easily adding entity-dependent animations. The second and third float params here are the same second
     * and third as in the setRotationAngles method.
     */
    public void setLivingAnimations(EntityLivingBase entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTickTime)
    {
        super.setLivingAnimations(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTickTime);
        this.head.rotationPointY = 9.0F + ((EnhancedSheep)entitylivingbaseIn).getHeadRotationPointY(partialTickTime) * 9.0F;
        this.headRotationAngleX = ((EnhancedSheep)entitylivingbaseIn).getHeadRotationAngleX(partialTickTime);
    }
}
