package mokiyoki.enhancedanimals.model;

import mokiyoki.enhancedanimals.entity.EnhancedSheep;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.MathHelper;

public class ModelEnhancedSheepSheared extends ModelBase {

    private float headRotationAngleX;

    private final ModelRenderer head;
    private final ModelRenderer earsR;
    private final ModelRenderer earsL;
    private final ModelRenderer body;
    private final ModelRenderer tail;
    public final ModelRenderer leg1;
    public final ModelRenderer leg2;
    public final ModelRenderer leg3;
    public final ModelRenderer leg4;

    public ModelEnhancedSheepSheared()
    {
        this.textureWidth = 64;
        this.textureHeight = 64;

        this.head = new ModelRenderer(this, 0, 0);
        this.head.addBox(-2.5F, -8.0F, -5.0F, 5, 4, 4, 0.0F); //skull
        this.head.setTextureOffset(0, 8);
        this.head.addBox(-2.0F, -8.0F, -8.0F, 4, 3, 3, 0.0F); //nose
        this.head.setTextureOffset(34, 0);
        this.head.addBox(-2.0F, -7.0F, -4.0F, 4, 9, 4, 0.0F); //neck
//        this.head.addBox(-3.0F, 0.0F, -6.0F, 1, 2, 1, 0.0F); //horns duplicate it

        this.earsR = new ModelRenderer(this, 50, 0);
        this.earsR.addBox(-5.0F, -8.5F, -2.0F, 3, 2, 1, 0.0F); //ear right
        this.earsL = new ModelRenderer(this, 50, 3);
        this.earsL.addBox(2.0F, -8.5F, -2.0F, 3, 2, 1, 0.0F); //ear left

//        float bodyY = 15;
        this.body = new ModelRenderer(this, 2, 0);
        this.body.addBox(-4.0F, 8.0F, -2.0F, 8, 6, 16, 0.0F);

        this.tail = new ModelRenderer(this, 50, 6);
        this.tail.addBox(-1.0F, 9.0F, 14.0F, 2, 3, 1);
        this.tail.setTextureOffset( 56, 6);
        this.tail.addBox(-0.5F, 12.0F, 14.0F, 1, 3, 1);
        this.tail.setTextureOffset( 60, 6);
        this.tail.addBox(-0.5F, 15.0F, 14.0F, 1, 3, 1);

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
    }

    public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        this.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entityIn);

        if (this.isChild) {
            GlStateManager.pushMatrix();
            GlStateManager.scale(0.6F, 0.6F, 0.6F);
            GlStateManager.translate(0.0F, 15.0F * scale, 0.0F);

            this.head.render(scale);
            this.earsR.render(scale);
            this.earsL.render(scale);

            GlStateManager.popMatrix();
            GlStateManager.pushMatrix();
            GlStateManager.scale(0.5F, 0.5F, 0.5F);
            GlStateManager.translate(0.0F, 20.0F * scale, 0.0F);

            this.body.render(scale);
            this.tail.render(scale);

            GlStateManager.popMatrix();
            GlStateManager.pushMatrix();
            GlStateManager.scale(0.5F, 0.7F, 0.5F);
            GlStateManager.translate(0.0F, 10.0F * scale, 0.0F);

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
            this.tail.render(scale);
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
        this.leg2.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
        this.leg3.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
        this.leg4.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        this.head.rotateAngleX = 1.2F + this.headRotationAngleX;   //might need to merge this with another line

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
