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

    private final ModelRenderer actualHead;
    private final ModelRenderer mouth;
    private final ModelRenderer earL;
    private final ModelRenderer earR;
    private final ModelRenderer hornNub;
    private final ModelRenderer head; //this is the neck not the head
    private final ModelRenderer body;
    private final ModelRenderer hump;
    private final ModelRenderer leg1;
    private final ModelRenderer leg2;
    private final ModelRenderer leg3;
    private final ModelRenderer leg4;


    public ModelEnhancedCow() {

        this.textureWidth = 80;
        this.textureHeight = 80;

        float xMove = 0.0F;

        this.actualHead = new ModelRenderer(this, 0, 33);
        this.actualHead.addBox(-4.0F, 0.0F, -7.0F, 8, 7, 6, 0.0F);
        this.actualHead.setTextureOffset(28, 33);
        this.actualHead.addBox(-2.0F, 0.1F, -11.0F, 4, 5, 4, 0.0F);
        this.actualHead.setTextureOffset(44, 33);
        this.actualHead.addBox(-2.5F, 0.2F, -13.0F, 5, 4, 3, 0.0F);
        this.actualHead.setRotationPoint(0.0F, 0.0F, -7.0F + xMove);

        this.mouth = new ModelRenderer(this, 21, 46);
        this.mouth.addBox(-1.5F, 1.0F, -10.0F, 3, 3, 7, 0.1F);
        this.mouth.setTextureOffset(37, 46);
        this.mouth.addBox(-1.5F, 3.0F, -10.0F, 3, 1, 6, -0.1F);
        this.mouth.setRotationPoint(0.0F, 4.0F, -2.6F + xMove);

        this.earL = new ModelRenderer(this, 8, 46);
        this.earL.addBox(0.0F, 0.0F, 0.0F, 3, 5, 1);
        this.earL.setRotationPoint(0.0F, 0.0F, 0.0F + xMove);

        this.earR = new ModelRenderer(this, 0, 46);
        this.earR.addBox(0.0F, 0.0F, 0.0F, 3, 5, 1);
        this.earR.setRotationPoint(0.0F, 0.0F, 0.0F + xMove);

        this.hornNub = new ModelRenderer(this, 16, 46);
        this.hornNub.addBox(-2.0F, 0.0F, 0.0F, 4, 2, 2);
        this.hornNub.setRotationPoint(0.0F, 0.0F, 0.0F + xMove);

        // head is the neck cause thats how this works
        this.head = new ModelRenderer(this, 46, 0);
        this.head.addBox(-3.0F, 0.0F, -8.0F, 6, 8, 11, 0.0F);
        this.head.setRotationPoint(0.0F, 0.0F, 0.0F + xMove);

        this.body = new ModelRenderer(this, 0, 0);
        this.body.addBox(-6.0F, 0.0F, 0.0F, 12, 11, 22, 0.0F);
        this.body.setRotationPoint(0.0F, 1.0F, 0.0F + xMove);

        this.hump = new ModelRenderer(this, 0, 8);
        this.hump.addBox(-2.0F, 0.0F, 0.0F, 4, 8, 6, 0.0F);
        this.hump.setRotationPoint(0.0F, 0.0F, 0.0F + xMove);

        this.leg1 = new ModelRenderer(this, 0, 54);
        this.leg1.addBox(0.0F, 0.0F, 0.0F, 3, 10, 3, 0.0F);
        this.leg1.setRotationPoint(-6.0F, 11.0F, 0.0F + xMove);

        this.leg2 = new ModelRenderer(this, 12, 54);
        this.leg2.addBox(0.0F, 0.0F, 0.0F, 3, 10, 3, 0.0F);
        this.leg2.setRotationPoint(3.0F, 11.0F, 0.0F + xMove);

        this.leg3 = new ModelRenderer(this, 0, 67);
        this.leg3.addBox(0.0F, 0.0F, 0.0F, 3, 10, 3, 0.0F);
        this.leg3.setRotationPoint(-6.0F, 11.0F, 19.0F + xMove);

        this.leg4 = new ModelRenderer(this, 12, 67);
        this.leg4.addBox(0.0F, 0.0F, 0.0F, 3, 10, 3, 0.0F);
        this.leg4.setRotationPoint(3.0F, 11.0F, 19.0F + xMove);

        this.head.addChild(this.actualHead);
        this.actualHead.addChild(this.earL);
        this.actualHead.addChild(this.earR);
        this.actualHead.addChild(this.mouth);

    }


    public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        this.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entityIn);

        EnhancedCow enhancedCow = (EnhancedCow) entityIn;

        int[] genes = enhancedCow.getSharedGenes();

//        if(genes[6] == 2){
//            this.head.addChild(this.actualHead);
//        }
//        else {
//            if(this.head.childModels != null && this.head.childModels.contains(this.actualHead)) {
//                this.head.childModels.remove(this.actualHead);
//            }
//        }


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
        this.head.rotateAngleX = (headPitch * 0.017453292F);
        this.head.rotateAngleY = netHeadYaw * 0.017453292F;

        //        this.body.rotateAngleX = ((float)Math.PI / 2F);
        this.leg1.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        this.leg2.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
        this.leg3.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
        this.leg4.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        
        this.head.rotateAngleX = this.headRotationAngleX;   //might need to merge this with another line
        this.actualHead.rotateAngleX = 0.5F;   //might need to merge this with another line
        this.mouth.rotateAngleX = -0.3F;   //might need to merge this with another line
//        this.actualHead.rotateAngleX = 0.05F + this.actualHead.rotateAngleX;   //might need to merge this with another line


    }

    public void setLivingAnimations(EntityLivingBase entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTickTime)
    {
        super.setLivingAnimations(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTickTime);
        this.head.rotationPointY = 1.25F + ((EnhancedCow)entitylivingbaseIn).getHeadRotationPointY(partialTickTime) * 9.0F;
        this.headRotationAngleX = ((EnhancedCow)entitylivingbaseIn).getHeadRotationAngleX(partialTickTime);


    }
    
}
