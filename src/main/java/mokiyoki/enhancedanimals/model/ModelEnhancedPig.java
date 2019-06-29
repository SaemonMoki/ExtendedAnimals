package mokiyoki.enhancedanimals.model;

import mokiyoki.enhancedanimals.entity.EnhancedCow;
import mokiyoki.enhancedanimals.entity.EnhancedPig;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.model.ModelBase;
import net.minecraft.client.renderer.entity.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.MathHelper;

public class ModelEnhancedPig extends ModelBase {

    private float size;

    private final ModelRenderer head;
    private final ModelRenderer cheeks;
    private final ModelRenderer snout;
    private final ModelRenderer mouth;
    private final ModelRenderer earL;
    private final ModelRenderer earR;
    private final ModelRenderer neck;
    private final ModelRenderer neckBigger;
    private final ModelRenderer body;
    private final ModelRenderer butt;
    private final ModelRenderer tail0;
    private final ModelRenderer tail1;
    private final ModelRenderer tail2;
    private final ModelRenderer tail3;
    private final ModelRenderer tail4;
    private final ModelRenderer tail5;
    private final ModelRenderer leg1;
    private final ModelRenderer leg2;
    private final ModelRenderer leg3;
    private final ModelRenderer leg4;

    public ModelEnhancedPig() {

        this.textureWidth = 80;
        this.textureHeight = 80;

        this.head = new ModelRenderer(this, 49, 0);
        this.head.addBox(-3.5F, -5.0F, -4.0F, 7, 6, 7);
        this.head.setRotationPoint(0.0F, -5.0F, -4.0F);

        this.cheeks = new ModelRenderer(this, 49, 13);
        this.cheeks.addBox(-4.0F, 0.0F, 0.0F, 8, 5, 4, 0.25F);
        this.cheeks.setRotationPoint(0.0F, -5.5F, -4.0F);

        this.snout = new ModelRenderer(this, 49, 22);
        this.snout.addBox(-2.0F, -5.0F, -3.0F, 4, 7, 3);
        this.snout.setRotationPoint(0.0F, -5.0F, 0.0F);

        this.mouth = new ModelRenderer(this, 63, 22);
        this.mouth.addBox(-1.0F, -5.0F, 0.0F, 2, 6, 1);
        this.mouth.setRotationPoint(0.0F, 1.0F, -4.0F);

        this.earL = new ModelRenderer(this, 46, 0);
        this.earL.addBox(0.0F, -3.0F, 0.0F, 4, 3, 1);
        this.earL.setRotationPoint(3.5F, -3.0F, 0.0F);

        this.earR = new ModelRenderer(this, 70, 0);
        this.earR.addBox(-4.0F, -3.0F, 0.0F, 4, 3, 1);
        this.earR.setRotationPoint(-3.5F, -3.0F, 0.0F);

        this.neck = new ModelRenderer(this, 0, 0);
        this.neck.addBox(-4.5F, -6.75F, -9.0F, 9, 7, 9);
        this.neck.setRotationPoint(0.0F, 9.0F, -2.0F);

        this.neckBigger = new ModelRenderer(this, 0, 0);
        this.neckBigger.addBox(-4.5F, -5.75F, -9.0F, 9, 7, 9, 1.0F);
        this.neckBigger.setRotationPoint(0.0F, 9.0F, -2.0F);

        this.body = new ModelRenderer(this, 0, 23);
        this.body.addBox(-5.0F, 0.0F, 0.0F, 10, 11, 10);
        this.body.setRotationPoint(0.0F, 18.1F, -4.0F);

        this.butt = new ModelRenderer(this, 0, 53);
        this.butt.addBox(-4.5F, 0.0F, 0.0F, 9, 5, 9);
        this.butt.setRotationPoint(0.0F, 18.0F, 5.5F);

        this.tail0 = new ModelRenderer(this, 36, 0);
        this.tail0.addBox(-0.5F, 0.0F, -0.5F, 1, 2, 1, -0.05F);
        this.tail0.setRotationPoint(0.0F, 10.0F, 11.0F);

        this.tail1 = new ModelRenderer(this, 36, 0);
        this.tail1.addBox(-0.5F, 0.0F, -0.5F, 1, 2, 1, -0.1F);
        this.tail1.setRotationPoint(0.0F, 1.9F, 0.0F);

        this.tail2 = new ModelRenderer(this, 36, 3);
        this.tail2.addBox(-0.5F, 0.0F, -0.5F, 1, 2, 1, -0.15F);
        this.tail2.setRotationPoint(0.0F, 1.8F, 0.0F);

        this.tail3 = new ModelRenderer(this, 36, 6);
        this.tail3.addBox(-0.5F, 0.0F, -0.5F, 1, 2, 1, -0.2F);
        this.tail3.setRotationPoint(0.0F, 1.7F, 0.0F);

        this.tail4 = new ModelRenderer(this, 36, 9);
        this.tail4.addBox(-0.5F, 0.0F, -0.5F, 1, 2, 1, -0.25F);
        this.tail4.setRotationPoint(0.0F, 1.6F, 0.0F);

        this.tail5 = new ModelRenderer(this, 36, 12);
        this.tail5.addBox(-0.5F, 0.0F, -0.5F, 1, 2, 1, -0.3F);
        this.tail5.setRotationPoint(0.0F, 1.5F, 0.0F);

        this.leg1 = new ModelRenderer(this, 49, 32);
        this.leg1.addBox(0.0F, 0.0F, 0.0F, 3, 8, 3);
        this.leg1.setRotationPoint(-5.0F, 16.0F, -7.0F);

        this.leg2 = new ModelRenderer(this, 61, 32);
        this.leg2.addBox(0.0F, 0.0F, 0.0F, 3, 8, 3);
        this.leg2.setRotationPoint(2.0F, 16.0F, -7.0F);

        this.leg3 = new ModelRenderer(this, 49, 44);
        this.leg3.addBox(0.0F, 0.0F, 0.0F, 3, 8, 3);
        this.leg3.setRotationPoint(-5.0F, 16.0F, 7.0F);

        this.leg4 = new ModelRenderer(this, 61, 44);
        this.leg4.addBox(0.0F, 0.0F, 0.0F, 3, 8, 3);
        this.leg4.setRotationPoint(2.0F, 16.0F, 7.0F);

        this.neck.addChild(this.head);
        this.head.addChild(this.cheeks);
        this.head.addChild(this.snout);
        this.snout.addChild(this.mouth);
        this.head.addChild(this.earL);
        this.head.addChild(this.earR);
        this.tail0.addChild(this.tail1);
        this.tail1.addChild(this.tail2);
        this.tail2.addChild(this.tail3);
        this.tail3.addChild(this.tail4);
        this.tail4.addChild(this.tail5);

    }

    private void setRotationOffset(ModelRenderer renderer, float x, float y, float z) {
        renderer.rotateAngleX = x;
        renderer.rotateAngleY = y;
        renderer.rotateAngleZ = z;
    }

    public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        this.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entityIn);

        EnhancedPig enhancedPig = (EnhancedPig) entityIn;
        int[] genes = enhancedPig.getSharedGenes();
        this.size = enhancedPig.getSize();
        float childSize = size/4.0F;

        if (isChild){

            GlStateManager.pushMatrix();
            GlStateManager.scalef(childSize, childSize, childSize);
            GlStateManager.translatef(0.0F, -1.5F + 1.5F/childSize, 0.0F);

            this.neck.render(scale);
            this.body.render(scale);
            this.butt.render(scale);
            this.tail0.render(scale);
            this.leg1.render(scale);
            this.leg2.render(scale);
            this.leg3.render(scale);
            this.leg4.render(scale);

            GlStateManager.popMatrix();


            GlStateManager.pushMatrix();
            GlStateManager.scalef(childSize, childSize, childSize);
            GlStateManager.translatef(0.0F, -1.5F + 1.5F/childSize, 0.0F);

            this.body.render(scale);
            this.butt.render(scale);
            this.tail0.render(scale);
            this.leg1.render(scale);
            this.leg2.render(scale);
            this.leg3.render(scale);
            this.leg4.render(scale);

            GlStateManager.popMatrix();

        }else{
            GlStateManager.pushMatrix();
            GlStateManager.scalef(size, size, size);
            GlStateManager.translatef(0.0F, -1.5F + 1.5F/size, 0.0F);

            this.neck.render(scale);
            this.neckBigger.render(scale);
            this.body.render(scale);
            this.butt.render(scale);
            this.tail0.render(scale);
            this.leg1.render(scale);
            this.leg2.render(scale);
            this.leg3.render(scale);
            this.leg4.render(scale);

            GlStateManager.popMatrix();
        }
    }

    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {

        super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);

        this.neck.rotateAngleX = ((float)Math.PI / 2F);
        this.neckBigger.rotateAngleX = ((float)Math.PI / 2F);
        this.body.rotateAngleX = ((float)Math.PI / 2F);
        this.butt.rotateAngleX = ((float)Math.PI / 2F);

        this.earL.rotateAngleX = -((float)Math.PI / 2F);
        this.earR.rotateAngleX = -((float)Math.PI / 2F);

        this.earL.rotateAngleY = -((float)Math.PI / 4F);
        this.earR.rotateAngleY = ((float)Math.PI / 4F);

        this.earL.rotateAngleZ = -((float)Math.PI / 16F);
        this.earR.rotateAngleZ = ((float)Math.PI / 16F);

        this.neck.rotateAngleX = this.neck.rotateAngleX + ((headPitch * 0.017453292F)/2.0F);
        this.head.rotateAngleX = (headPitch * 0.017453292F)/2.0F;

        this.neck.rotateAngleY = (netHeadYaw * 0.017453292F)/2.0F;
        this.head.rotateAngleZ = -(netHeadYaw * 0.017453292F)/2.0F;


        this.leg1.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        this.leg2.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
        this.leg3.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
        this.leg4.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;

        this.mouth.rotateAngleX = -0.12F;

        copyModelAngles(neck, neckBigger);

    }

    public void setLivingAnimations(EntityLivingBase entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTickTime) {
        super.setLivingAnimations(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTickTime);

        EnhancedPig enhancedPig = (EnhancedPig) entitylivingbaseIn;

        int[] sharedGenes = ((EnhancedPig) entitylivingbaseIn).getSharedGenes();
        char[] uuidArry = enhancedPig.getCachedUniqueIdString().toCharArray();

          float snoutLength1 = -0.065F;
          float snoutLength2 = -0.065F;
        float snoutLength;

        for (int i = 1; i < sharedGenes[18];i++){
            snoutLength1 = snoutLength1 + 0.03F;
        }

        for (int i = 1; i < sharedGenes[19];i++){
            snoutLength2 = snoutLength2 + 0.03F;
        }

        if (snoutLength1 > snoutLength2){
            snoutLength = (snoutLength1*0.75F) + (snoutLength2*0.25F);
        }else if (snoutLength1 < snoutLength2){
            snoutLength = (snoutLength1*0.25F) + (snoutLength2*0.75F);
        }else{
            snoutLength = snoutLength1 + snoutLength2;
        }

        if (sharedGenes[20] == 1 || sharedGenes[21] == 1){
            if (snoutLength >= -0.12F){
                snoutLength = snoutLength - 0.01F;
            }
        }else if (sharedGenes[20] != 2 && sharedGenes[21] != 2){
            if (sharedGenes[20] == 3 || sharedGenes[21] == 3){
                snoutLength = snoutLength + 0.01F;
            }else{
                snoutLength = snoutLength + 0.02F;
            }
        }

        if (isChild){
            snoutLength = (snoutLength + 0.11F) / 2.0F;
        }

        this.snout.offsetY = snoutLength;
        this.snout.rotateAngleX = -snoutLength;

        float inbreedingFactor = 0.0F;

        if (sharedGenes[20] == sharedGenes[21]){
            inbreedingFactor = 0.1667F;
        }
        if (sharedGenes[22] == sharedGenes[23]){
            inbreedingFactor = inbreedingFactor + 0.1667F;
        }
        if (sharedGenes[24] == sharedGenes[25]){
            inbreedingFactor = inbreedingFactor + 0.1667F;
        }
        if (sharedGenes[26] == sharedGenes[27]){
            inbreedingFactor = inbreedingFactor + 0.1667F;
        }
        if (sharedGenes[28] == sharedGenes[29]){
            inbreedingFactor = inbreedingFactor + 0.1667F;
        }
        if (sharedGenes[30] == sharedGenes[31]){
            inbreedingFactor = inbreedingFactor + 0.1667F;
        }

        this.tail0.rotateAngleX = 1.5F * inbreedingFactor;
        this.tail1.rotateAngleX = 1.1111F * inbreedingFactor;
        this.tail2.rotateAngleX = 1.2222F * inbreedingFactor;
        this.tail3.rotateAngleX = 1.3333F * inbreedingFactor;
        this.tail4.rotateAngleX = 1.5F * inbreedingFactor;
        this.tail5.rotateAngleX = 0.1F * inbreedingFactor;

        if (Character.isLetter(uuidArry[1])){
            this.tail0.rotateAngleY = 0.3555F * inbreedingFactor;
            this.tail1.rotateAngleY = 0.3555F * inbreedingFactor;
            this.tail2.rotateAngleY = 0.3555F * inbreedingFactor;
            this.tail3.rotateAngleY = 0.3555F * inbreedingFactor;
        }else{
            this.tail0.rotateAngleY = -0.3555F * inbreedingFactor;
            this.tail1.rotateAngleY = -0.3555F * inbreedingFactor;
            this.tail2.rotateAngleY = -0.3555F * inbreedingFactor;
            this.tail3.rotateAngleY = -0.3555F * inbreedingFactor;
        }



    }

}
