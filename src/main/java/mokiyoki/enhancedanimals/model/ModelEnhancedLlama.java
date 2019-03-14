package mokiyoki.enhancedanimals.model;

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

    private final ModelRenderer head;
    private final ModelRenderer earsR;
    private final ModelRenderer earsL;
    private final ModelRenderer earsTopR;
    private final ModelRenderer earsTopL;
    private final ModelRenderer body;
    private final ModelRenderer tail;
    public final ModelRenderer leg1;
    public final ModelRenderer leg2;
    public final ModelRenderer leg3;
    public final ModelRenderer leg4;
    public final ModelRenderer toeOuterFrontR;
    public final ModelRenderer toeInnerFrontR;
    public final ModelRenderer toeOuterFrontL;
    public final ModelRenderer toeInnerFrontL;
    public final ModelRenderer toeOuterBackR;
    public final ModelRenderer toeInnerBackR;
    public final ModelRenderer toeOuterBackL;
    public final ModelRenderer toeInnerBackL;

    public ModelEnhancedLlama()
    {
        this.textureWidth = 96;
        this.textureHeight = 96;

        this.head = new ModelRenderer(this, 0, 0);
        this.head.addBox(-4F, 0F, 0F, 8, 18, 6, 0.0F); //head and neck
        this.head.setTextureOffset(28, 0);
        this.head.addBox(-2F, 0F, 0F, 4, 4, 4, 0.0F); //nose
        this.head.setRotationPoint(0, 15, 0);

        this.earsR = new ModelRenderer(this, 50, 0);
        this.earsR.addBox(0F, 0F, 0F, 3, 3, 2, 0.0F); //ear right
        this.earsR.setRotationPoint(0, 15, 0);

        this.earsL = new ModelRenderer(this, 44, 0);
        this.earsL.addBox(0F, 0F, 0F, 3, 3, 2, 0.0F); //ear left
        this.earsL.setRotationPoint(0, 15, 0);

        this.earsTopR = new ModelRenderer(this, 54, 0);
        this.earsTopR.addBox(-0.5F, 0F, 0F, 3, 1, 2, 0.0F); //ear right
        this.earsTopR.setRotationPoint(0, 15, 0);

        this.earsTopL = new ModelRenderer(this, 64, 0);
        this.earsTopL.addBox(0.5F, 0F, 0F, 3, 1, 2, 0.0F); //ear left
        this.earsTopL.setRotationPoint(0, 15, 0);

        this.body = new ModelRenderer(this, 74, 39);
        this.body.addBox(-6F, 0F, 0F, 12, 10, 18, 0.0F);
        this.body.setRotationPoint(0F, 13F, 0);

        this.tail = new ModelRenderer(this, 0, 25);
        this.tail.addBox(-1.0F, 0.0F, 0.0F, 6, 6, 6);
        this.tail.setRotationPoint(0.0F, 9.0F, 14.0F);

        this.leg1 = new ModelRenderer(this, 0, 68);
        this.leg1.addBox(0.0F, 0.0F, 0.0F, 3, 10, 3);
        this.leg1.setRotationPoint(-4, 14,-2);

        this.leg2 = new ModelRenderer(this, 12, 68);
        this.leg2.addBox(0.0F, 0.0F, 0.0F, 3, 10, 3);
        this.leg2.setRotationPoint(1, 14,-2);

        this.leg3 = new ModelRenderer(this, 0, 82);
        this.leg3.addBox(0.0F, 0.0F, 0.0F, 3, 10, 3);
        this.leg3.setRotationPoint(-4, 14,11);

        this.leg4 = new ModelRenderer(this, 12, 82);
        this.leg4.addBox(0.0F, 0.0F, 0.0F, 3, 10, 3);
        this.leg4.setRotationPoint(1, 14,11);

        this.toeOuterFrontR = new ModelRenderer(this, 24, 68);
        this.toeOuterFrontR.addBox(0F, 0F, 0F, 3, 4, 6, 0.25F);
        this.toeInnerFrontR = new ModelRenderer(this, 0, 68);
        this.toeInnerFrontR.addBox(0F, 0F, 0F, 3, 4, 6, 0.25F);

        this.toeOuterFrontL = new ModelRenderer(this, 42, 68);
        this.toeOuterFrontL.addBox(0F, 0F, 0F, 3, 4, 6, 0.25F);
        this.toeInnerFrontL = new ModelRenderer(this, 0, 68);
        this.toeInnerFrontL.addBox(0F, 0F, 0F, 3, 4, 6, 0.25F);

        this.toeOuterBackR = new ModelRenderer(this, 24, 82);
        this.toeOuterBackR.addBox(0F, 0F, 0F, 3, 4, 6, 0.25F);
        this.toeInnerBackR = new ModelRenderer(this, 0, 0);
        this.toeInnerBackR.addBox(0F, 0F, 0F, 3, 4, 6, 0.25F);

        this.toeOuterBackL = new ModelRenderer(this, 42, 82);
        this.toeOuterBackL.addBox(0F, 0F, 0F, 3, 4, 6, 0.25F);
        this.toeInnerBackL = new ModelRenderer(this, 0, 0);
        this.toeInnerBackL.addBox(0F, 0F, 0F, 3, 4, 6, 0.25F);


        leg1.addChild(toeOuterFrontR);
        leg1.addChild(toeInnerFrontR);
        leg2.addChild(toeOuterFrontL);
        leg2.addChild(toeInnerFrontL);
        leg3.addChild(toeOuterBackR);
        leg3.addChild(toeInnerBackR);
        leg4.addChild(toeOuterBackL);
        leg4.addChild(toeInnerBackL);

    }

    public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        this.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entityIn);

        if (this.isChild) {
            GlStateManager.pushMatrix();
            GlStateManager.scalef(0.6F, 0.6F, 0.6F);
            GlStateManager.translatef(0.0F, 15.0F * scale, 0.0F);

            this.head.render(scale);
            this.earsR.render(scale);
            this.earsL.render(scale);
            this.earsTopR.render(scale);
            this.earsTopL.render(scale);

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

            GlStateManager.popMatrix();

        }else {

            this.head.render(scale);
            this.earsR.render(scale);
            this.earsL.render(scale);
            this.earsTopR.render(scale);
            this.earsTopL.render(scale);
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

        this.tail.rotateAngleZ = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;


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
//        super.setLivingAnimations(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTickTime);
//        this.head.rotationPointY = 9.0F + ((EnhancedLlama)entitylivingbaseIn).getHeadRotationPointY(partialTickTime) * 9.0F;
//        this.headRotationAngleX = ((EnhancedLlama)entitylivingbaseIn).getHeadRotationAngleX(partialTickTime);
    }
}
