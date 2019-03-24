package mokiyoki.enhancedanimals.model;

import mokiyoki.enhancedanimals.entity.EnhancedRabbit;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.model.ModelBase;
import net.minecraft.client.renderer.entity.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ModelEnhancedRabbit extends ModelBase
{
    private final ModelRenderer rabbitLeftFoot;
    private final ModelRenderer rabbitRightFoot;
    private final ModelRenderer rabbitLeftCalf;
    private final ModelRenderer rabbitRightCalf;
    private final ModelRenderer rabbitLeftThigh;
    private final ModelRenderer rabbitRightThigh;
    private final ModelRenderer rabbitBody;
    private final ModelRenderer rabbitButtRound;
    private final ModelRenderer rabbitButt;
    private final ModelRenderer rabbitButtTube;
    private final ModelRenderer rabbitLeftArm;
    private final ModelRenderer rabbitRightArm;
    private final ModelRenderer rabbitHeadLeft;
    private final ModelRenderer rabbitHeadRight;
    private final ModelRenderer rabbitHeadMuzzle;
    private final ModelRenderer rabbitRightEar;
    private final ModelRenderer rabbitLeftEar;
    private final ModelRenderer rabbitTail;
    private final ModelRenderer rabbitNose;
    private float jumpRotation;

    public ModelEnhancedRabbit()
    {
        this.textureWidth = 64;
        this.textureHeight = 64;

        this.rabbitLeftFoot = new ModelRenderer(this, 0, 55);
        this.rabbitLeftFoot.addBox(0F, 0F, 0F, 3, 8, 1);
        this.rabbitLeftFoot.setRotationPoint(0.0F, 5.0F, 1.0F);
        this.setRotationOffset(this.rabbitLeftFoot, 3.0F, 0.0F, 0.0F);
        this.rabbitLeftFoot.mirror = true;

        this.rabbitRightFoot = new ModelRenderer(this, 8, 55);
        this.rabbitRightFoot.addBox(0F, 0F, 0F, 3, 8, 1);
        this.rabbitRightFoot.setRotationPoint(0F, 5.0F, 1.0F);
        this.setRotationOffset(this.rabbitRightFoot, 3.0F, 0.0F, 0.0F);
        this.rabbitRightFoot.mirror = true;

        this.rabbitLeftCalf = new ModelRenderer(this, 0, 49);
        this.rabbitLeftCalf.addBox(0F, 0F, 0F, 3, 5, 1);
        this.rabbitLeftCalf.setRotationPoint(0.0F, 6.5F, 2.7F);
        this.setRotationOffset(this.rabbitLeftCalf, 2.0F, 0.0F, 0.0F);
        this.rabbitLeftCalf.addChild(rabbitLeftFoot);

        this.rabbitRightCalf = new ModelRenderer(this, 18, 49);
        this.rabbitRightCalf.addBox(0F, 0F, 0F, 3, 5, 1);
        this.rabbitRightCalf.setRotationPoint(0, 6.5F, 2.7F);
        this.setRotationOffset(this.rabbitRightCalf, 2.0F, 0.0F, 0.0F);
        this.rabbitRightCalf.addChild(rabbitRightFoot);

        this.rabbitLeftThigh = new ModelRenderer(this, 0, 37);
        this.rabbitLeftThigh.addBox(0F, 0F, 0F, 3, 6, 6);
        this.rabbitLeftThigh.setRotationPoint(-4.5F, 17F, 4F);
        this.rabbitLeftThigh.addChild(rabbitLeftCalf);

        this.rabbitRightThigh = new ModelRenderer(this, 18, 37);
        this.rabbitRightThigh.addBox(0F, 0F, 0F, 3, 6, 6);
        this.rabbitRightThigh.setRotationPoint(1.5F, 17F, 4F);
        this.rabbitRightThigh.addChild(rabbitRightCalf);

        this.rabbitBody = new ModelRenderer(this, 7, 8);
        this.rabbitBody.addBox(-3.5F, 0F, 0F, 7, 7, 9);
        this.rabbitBody.setRotationPoint(0F, 16.0F, -4.5F);

        this.rabbitButtRound = new ModelRenderer(this, 30, 0);
        this.rabbitButtRound.addBox(-4.0F, 0.0F, 0.0F, 8, 8, 8, 0.5F);
        this.rabbitButtRound.setRotationPoint(0.0F, 14.0F, 2.5F);

        this.rabbitButt = new ModelRenderer(this, 30, 0);
        this.rabbitButt.addBox(-4.0F, 0.0F, 0.0F, 8, 8, 8);
        this.rabbitButt.setRotationPoint(0.0F, 15.0F, 2.5F);

        this.rabbitButtTube = new ModelRenderer(this, 30, 0);
        this.rabbitButtTube.addBox(-4.0F, 0.0F, 0.0F, 8, 8, 8, -0.49F);
        this.rabbitButtTube.setRotationPoint(0.0F, 16.1F, 2.5F);

        this.rabbitLeftArm = new ModelRenderer(this, 16, 56);
        this.rabbitLeftArm.addBox(0.0F, 0.0F, 0.0F, 3, 6, 2);
        this.rabbitLeftArm.setRotationPoint(-4.5F, 21.0F, -2.0F);

        this.rabbitRightArm = new ModelRenderer(this, 26, 56);
        this.rabbitRightArm.addBox(0.0F, 0F, 0F, 3, 6, 2);
        this.rabbitRightArm.setRotationPoint(1.5F, 21.0F, -2.0F);

        this.rabbitHeadLeft = new ModelRenderer(this, 0, 24);
        this.rabbitHeadLeft.addBox(0.0F, 0.0F, 0.0F, 3, 6, 6);
        this.rabbitHeadLeft.setRotationPoint(0.0F, 14.0F, -9.0F);

        this.rabbitHeadRight = new ModelRenderer(this, 18, 24);
        this.rabbitHeadRight.addBox(-3.0F, 0F, 0F, 3, 6, 6);
        this.rabbitHeadRight.setRotationPoint(0.0F, 14.0F, -9.0F);

        this.rabbitHeadMuzzle = new ModelRenderer(this, 0, 8);
        this.rabbitHeadMuzzle.addBox(-2F, 1.5F, -2F, 4, 4, 4);
        this.rabbitHeadMuzzle.setRotationPoint(0.0F, 0.0F, 0.0F);

        this.rabbitNose = new ModelRenderer(this, 0, 8);
        this.rabbitNose.addBox(-0.5F, 1.6F, -2.1F, 1, 1, 1);
//        this.setRotationOffset(this.rabbitNose, 0.0F, 0.0F, 0.0F);

        this.rabbitLeftEar = new ModelRenderer(this, 0, 0);
        this.rabbitLeftEar.addBox(0.0F, -7.0F, 2.0F, 4, 7, 1);
        this.rabbitLeftEar.setRotationPoint(1.0F, 14.0F, 0.0F);

        this.rabbitRightEar = new ModelRenderer(this, 10, 0);
        this.rabbitRightEar.addBox(-4.0F, -7.0F, 2.0F, 4, 7, 1);
        this.rabbitRightEar.setRotationPoint(-1.0F, 14.0F, 0.0F);

        this.rabbitTail = new ModelRenderer(this, 20, 0);
        this.rabbitTail.addBox(-1.5F, 2.0F, 8.0F, 3, 4, 2);
        this.rabbitTail.setRotationPoint(0.0F, 0.0F, 0.0F);
    }

    private void setRotationOffset(ModelRenderer renderer, float x, float y, float z) {
        renderer.rotateAngleX = x;
        renderer.rotateAngleY = y;
        renderer.rotateAngleZ = z;
    }

    /**
     * Sets the models various rotation angles then renders the model.
     */
    public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        this.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entityIn);

        if (this.isChild) {
//            float f = 1.5F;
            GlStateManager.pushMatrix();
            GlStateManager.scalef(0.56666666F, 0.56666666F, 0.56666666F);
            GlStateManager.translatef(0.0F, 22.0F * scale, 2.0F * scale);
            this.rabbitHeadLeft.render(scale);
            this.rabbitHeadRight.render(scale);
            this.rabbitHeadMuzzle.render(scale);
            this.rabbitNose.render(scale);
            this.rabbitLeftEar.render(scale);
            this.rabbitRightEar.render(scale);
            GlStateManager.popMatrix();
            GlStateManager.pushMatrix();
            GlStateManager.scalef(0.4F, 0.4F, 0.4F);
            GlStateManager.translatef(0.0F, 36.0F * scale, 0.0F);
//            this.rabbitLeftFoot.render(scale);
//            this.rabbitRightFoot.render(scale);
//            this.rabbitLeftCalf.render(scale);
//            this.rabbitRightCalf.render(scale);
            this.rabbitLeftThigh.render(scale);
            this.rabbitRightThigh.render(scale);
            this.rabbitBody.render(scale);
//            this.rabbitButtRound.render(scale);
            this.rabbitButt.render(scale);
//            this.rabbitButtTube.render(scale);
            this.rabbitLeftArm.render(scale);
            this.rabbitRightArm.render(scale);
            this.rabbitTail.render(scale);
            GlStateManager.popMatrix();
        } else {
//            GlStateManager.pushMatrix();
//            GlStateManager.scalef(0.6F, 0.6F, 0.6F);
//            GlStateManager.translatef(0.0F, 16.0F * scale, 0.0F);
            this.rabbitHeadLeft.render(scale);
            this.rabbitHeadRight.render(scale);
            this.rabbitHeadMuzzle.render(scale);
            this.rabbitNose.render(scale);
            this.rabbitLeftEar.render(scale);
            this.rabbitRightEar.render(scale);
//            this.rabbitLeftFoot.render(scale);
//            this.rabbitRightFoot.render(scale);
//            this.rabbitLeftCalf.render(scale);
//            this.rabbitRightCalf.render(scale);
            this.rabbitLeftThigh.render(scale);
            this.rabbitRightThigh.render(scale);
            this.rabbitBody.render(scale);
//            this.rabbitButtRound.render(scale);
            this.rabbitButt.render(scale);
////            this.rabbitButtTube.render(scale);
            this.rabbitLeftArm.render(scale);
            this.rabbitRightArm.render(scale);
            this.rabbitTail.render(scale);
//            GlStateManager.popMatrix();
        }
    }

    /**
     * Sets the model's various rotation angles. For bipeds, par1 and par2 are used for animating the movement of arms
     * and legs, where par1 represents the time(so that arms and legs swing back and forth) and par2 represents how
     * "far" arms and legs can swing at most.
     */
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
        float f = ageInTicks - (float)entityIn.ticksExisted;
        this.rabbitHeadLeft.rotateAngleX = headPitch * 0.017453292F;
        this.rabbitHeadLeft.rotateAngleY = netHeadYaw * 0.017453292F;
        copyModelAngles(rabbitHeadLeft, rabbitHeadRight);
        copyModelAngles(rabbitHeadLeft, rabbitHeadMuzzle);
        copyModelAngles(rabbitHeadLeft, rabbitNose);
        copyModelAngles(rabbitHeadLeft, rabbitLeftEar);
        copyModelAngles(rabbitHeadLeft, rabbitRightEar);
        this.rabbitNose.rotateAngleY = netHeadYaw * 0.017453292F;
        this.rabbitRightEar.rotateAngleY = this.rabbitNose.rotateAngleY - 0.2617994F;
        this.rabbitLeftEar.rotateAngleY = this.rabbitNose.rotateAngleY + 0.2617994F;

        this.jumpRotation = MathHelper.sin(((EnhancedRabbit)entityIn).getJumpCompletion(f) * (float)Math.PI);
        if (this.jumpRotation == 0.0F) {
            this.rabbitLeftFoot.rotateAngleX = 3.0F;
            this.rabbitRightFoot.rotateAngleX = 3.0F;
            this.rabbitLeftCalf.rotateAngleX = 2.0F;
            this.rabbitRightCalf.rotateAngleX = 2.0F;
        } else {
            this.rabbitLeftFoot.rotateAngleX = 3.0F + this.jumpRotation * 80.0F * ((float)Math.PI / 180F);
            this.rabbitRightFoot.rotateAngleX = 3.0F + this.jumpRotation * 80.0F * ((float)Math.PI / 180F);
            this.rabbitLeftCalf.rotateAngleX = 2.0F - (this.jumpRotation * +50.0F * ((float)Math.PI / 180F));
            this.rabbitRightCalf.rotateAngleX = 2.0F - (this.jumpRotation * +50.0F * ((float)Math.PI / 180F));
        }
        this.rabbitLeftThigh.rotateAngleX = (this.jumpRotation * 50.0F - 21.0F) * ((float)Math.PI / 180F);
        this.rabbitRightThigh.rotateAngleX = (this.jumpRotation * 50.0F - 21.0F) * ((float)Math.PI / 180F);
        this.rabbitLeftArm.rotateAngleX = (this.jumpRotation * -40.0F - 11.0F) * ((float)Math.PI / 180F);
        this.rabbitRightArm.rotateAngleX = (this.jumpRotation * -40.0F - 11.0F) * ((float)Math.PI / 180F);


        copyModelAngles(rabbitButt, rabbitTail);
    }

    /**
     * Used for easily adding entity-dependent animations. The second and third float params here are the same second
     * and third as in the setRotationAngles method.
     */
    public void setLivingAnimations(EntityLivingBase entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTickTime) {
        super.setLivingAnimations(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTickTime);
        this.jumpRotation = MathHelper.sin(((EnhancedRabbit)entitylivingbaseIn).getJumpCompletion(partialTickTime) * (float)Math.PI);
    }
}
