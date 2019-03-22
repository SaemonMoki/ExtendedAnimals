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

    private final ModelRenderer head;
    private final ModelRenderer earsR;
    private final ModelRenderer earsL;
    private final ModelRenderer earsTopR;
    private final ModelRenderer earsTopL;
    private final ModelRenderer earsTopBananaR;
    private final ModelRenderer earsTopBananaL;
    private final ModelRenderer body;
    private final ModelRenderer tail;
    private final ModelRenderer leg1;
    private final ModelRenderer leg2;
    private final ModelRenderer leg3;
    private final ModelRenderer leg4;
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
        this.head.addBox(-4F, -14F, 0F, 8, 18, 6, 0.0F); //head and neck
        this.head.setTextureOffset(28, 0);
        this.head.addBox(-2F, -12F, -4F, 4, 4, 4, 0.0F); //nose
        this.head.setRotationPoint(0, 5, -6);

        this.earsR = new ModelRenderer(this, 44, 0);
        this.earsR.addBox(-4F, -17F, 2F, 3, 3, 2, 0.0F); //ear right

        this.earsL = new ModelRenderer(this, 54, 0);
        this.earsL.addBox(1F, -17F, 2F, 3, 3, 2, 0.0F); //ear left

        this.earsTopR = new ModelRenderer(this, 64, 0);
        this.earsTopR.addBox(-4F, -18F, 2F, 3, 1, 2, 0.0F); //ear right

        this.earsTopL = new ModelRenderer(this, 74, 0);
        this.earsTopL.addBox(1F, -18F, 2F, 3, 1, 2, 0.0F); //ear left

        this.earsTopBananaR = new ModelRenderer(this, 64, 0);
        this.earsTopBananaR.addBox(-3.5F, -18F, 2F, 3, 1, 2, 0.0F); //ear right
        this.earsTopBananaL = new ModelRenderer(this, 74, 0);
        this.earsTopBananaL.addBox(0.5F, -18F, 2F, 3, 1, 2, 0.0F); //ear left

        this.body = new ModelRenderer(this, 0, 39);
        this.body.addBox(-6F, 0F, 0F, 12, 10, 18, 0.0F);
        this.body.setRotationPoint(0F, 2F, -2F);

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

        this.leg1 = new ModelRenderer(this, 0, 68);
        this.leg1.addBox(0.0F, 0F, 0.0F, 3, 11, 3);
        this.leg1.setRotationPoint(-5, 12,-1);

        this.leg2 = new ModelRenderer(this, 12, 68);
        this.leg2.addBox(0.0F, 0F, 0.0F, 3, 11, 3);
        this.leg2.setRotationPoint(2, 12,-1);

        this.leg3 = new ModelRenderer(this, 0, 82);
        this.leg3.addBox(0.0F, 0F, 0.0F, 3, 11, 3);
        this.leg3.setRotationPoint(-5, 12,12);

        this.leg4 = new ModelRenderer(this, 12, 82);
        this.leg4.addBox(0.0F, 0F, 0.0F, 3, 11, 3);
        this.leg4.setRotationPoint(2, 12,12);

        this.toeOuterFrontR = new ModelRenderer(this, 26, 70);
        this.toeOuterFrontR.addBox(-0.85F, 10F, -2.5F, 3, 3, 4, -0.75F);
        this.toeInnerFrontR = new ModelRenderer(this, 44, 70);
        this.toeInnerFrontR.addBox(0.75F, 10F, -2.5F, 3, 3, 4, -0.75F);

        this.toeOuterFrontL = new ModelRenderer(this, 62, 70);
        this.toeOuterFrontL.addBox(0.85F, 10F, -2.5F, 3, 3, 4, -0.75F);
        this.toeInnerFrontL = new ModelRenderer(this, 80, 70);
        this.toeInnerFrontL.addBox(-0.75F, 10F, -2.5F, 3, 3, 4, -0.75F);

        this.toeOuterBackR = new ModelRenderer(this, 26, 84);
        this.toeOuterBackR.addBox(-0.85F, 10F, -2.5F, 3, 3, 4, -0.75F);
        this.toeInnerBackR = new ModelRenderer(this, 44, 84);
        this.toeInnerBackR.addBox(0.75F, 10F, -2.5F, 3, 3, 4, -0.75F);

        this.toeOuterBackL = new ModelRenderer(this, 62, 84);
        this.toeOuterBackL.addBox(0.85F, 10F, -2.5F, 3, 3, 4, -0.75F);
        this.toeInnerBackL = new ModelRenderer(this, 80, 84);
        this.toeInnerBackL.addBox(-0.75F, 10F, -2.5F, 3, 3, 4, -0.75F);


//        leg1.addChild(toeOuterFrontR);
//        leg1.addChild(toeInnerFrontR);
//        leg2.addChild(toeOuterFrontL);
//        leg2.addChild(toeInnerFrontL);
//        leg3.addChild(toeOuterBackR);
//        leg3.addChild(toeInnerBackR);
//        leg4.addChild(toeOuterBackL);
//        leg4.addChild(toeInnerBackL);

    }

    public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        this.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entityIn);
        EnhancedLlama enhancedLlama = (EnhancedLlama) entityIn;

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

            this.head.render(scale);
            this.earsR.render(scale);
            this.earsL.render(scale);
            if (banana){
                this.earsTopBananaR.render(scale);
                this.earsTopBananaL.render(scale);
            }else {
                this.earsTopR.render(scale);
                this.earsTopL.render(scale);
            }
            this.body.render(scale);
            this.tail.render(scale);
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

        copyModelAngles(head, earsR);
        copyModelAngles(head, earsL);
        copyModelAngles(head, earsTopR);
        copyModelAngles(head, earsTopL);
        copyModelAngles(head, earsTopBananaR);
        copyModelAngles(head, earsTopBananaL);

        copyModelAngles(body, tail);

        //TODO fix the toes so they angle properly and maintain the angle while they walk
        copyModelAngles(leg1, toeOuterFrontR);
        copyModelAngles(leg1, toeInnerFrontR);

        copyModelAngles(leg2, toeOuterFrontL);
        copyModelAngles(leg2, toeInnerFrontL);

        copyModelAngles(leg3, toeOuterBackR);
        copyModelAngles(leg3, toeInnerBackR);

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

        // banana ears
        if ( sharedGenes[18] != 1 && sharedGenes[19] != 1){
            if (sharedGenes[18] == 2 || sharedGenes[19] == 2){
                banana = true;
            }
        }

//        super.setLivingAnimations(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTickTime);
//        this.head.rotationPointY = 9.0F + ((EnhancedLlama)entitylivingbaseIn).getHeadRotationPointY(partialTickTime) * 9.0F;
//        this.headRotationAngleX = ((EnhancedLlama)entitylivingbaseIn).getHeadRotationAngleX(partialTickTime);
    }
}
