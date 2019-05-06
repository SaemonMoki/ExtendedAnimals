package mokiyoki.enhancedanimals.model;

import mokiyoki.enhancedanimals.entity.EnhancedCow;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.model.ModelBase;
import net.minecraft.client.renderer.entity.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.MathHelper;

public class ModelEnhancedCow extends ModelBase {

    private float headRotationAngleX;

    private final ModelRenderer head;
    private final ModelRenderer neck;
    private final ModelRenderer body;
    private final ModelRenderer leg1;
    private final ModelRenderer leg2;
    private final ModelRenderer leg3;
    private final ModelRenderer leg4;


    public ModelEnhancedCow() {

        this.textureWidth = 128;
        this.textureHeight = 128;

        float xMove = 0.0F;

        this.head = new ModelRenderer(this, 61, 23);
        this.head.addBox(0.0F, 0.0F, 0.0F, 8, 7, 7, 0.0F);
        this.head.setRotationPoint(-4.0F, 0.0F, -10.0F + xMove);

        this.neck = new ModelRenderer(this, 61, 0);
        this.neck.addBox(0.0F, 0.0F, 0.0F, 6, 8, 10, 0.0F);
        this.neck.setRotationPoint(0.0F, 0.0F, 0.0F + xMove);

        this.body = new ModelRenderer(this, 0, 0);
        this.body.addBox(0.0F, 0.0F, 0.0F, 12, 10, 18, 0.0F);
        this.body.setRotationPoint(-6.0F, 1.0F, 0.0F + xMove);

        this.leg1 = new ModelRenderer(this, 0, 28);
        this.leg1.addBox(0.0F, 0.0F, 0.0F, 3, 13, 3, 0.0F);
        this.leg1.setRotationPoint(-6.0F, 11.0F, 0.0F + xMove);

        this.leg2 = new ModelRenderer(this, 12, 28);
        this.leg2.addBox(0.0F, 0.0F, 0.0F, 3, 13, 3, 0.0F);
        this.leg2.setRotationPoint(3.0F, 11.0F, 0.0F + xMove);

        this.leg3 = new ModelRenderer(this, 24, 28);
        this.leg3.addBox(0.0F, 0.0F, 0.0F, 3, 13, 3, 0.0F);
        this.leg3.setRotationPoint(-6.0F, 11.0F, 15.0F + xMove);

        this.leg4 = new ModelRenderer(this, 36, 28);
        this.leg4.addBox(0.0F, 0.0F, 0.0F, 3, 13, 3, 0.0F);
        this.leg4.setRotationPoint(3.0F, 11.0F, 15.0F + xMove);

//        this.neck.addChild(head);

    }


    public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        this.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entityIn);



        if (this.isChild) {
            GlStateManager.pushMatrix();
            GlStateManager.scalef(0.6F, 0.6F, 0.6F);
            GlStateManager.translatef(0.0F, 15.0F * scale, 0.0F);

            this.head.render(scale);

            GlStateManager.popMatrix();
            GlStateManager.pushMatrix();
            GlStateManager.scalef(0.5F, 0.5F, 0.5F);
            GlStateManager.translatef(0.0F, 20.0F * scale, 0.0F);

            this.body.render(scale);

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
            this.body.render(scale);
            this.leg1.render(scale);
            this.leg2.render(scale);
            this.leg3.render(scale);
            this.leg4.render(scale);
            
        }
    }

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
        
//        this.head.rotateAngleX = 1F + this.headRotationAngleX;   //might need to merge this with another line


    }

    public void setLivingAnimations(EntityLivingBase entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTickTime)
    {
        super.setLivingAnimations(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTickTime);
        this.head.rotationPointY = 9.0F + ((EnhancedCow)entitylivingbaseIn).getHeadRotationPointY(partialTickTime) * 9.0F;
        this.headRotationAngleX = ((EnhancedCow)entitylivingbaseIn).getHeadRotationAngleX(partialTickTime);


    }
    
}
