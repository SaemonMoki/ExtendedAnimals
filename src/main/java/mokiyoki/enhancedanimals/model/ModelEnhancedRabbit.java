package mokiyoki.enhancedanimals.model;

import com.mojang.blaze3d.platform.GlStateManager;
import mokiyoki.enhancedanimals.entity.EnhancedRabbit;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ModelEnhancedRabbit <T extends EnhancedRabbit> extends EntityModel<T> {

    private int coatlength = 0;
    private boolean dwarf = false;
    private float earX = 0;
    private float earY = 0.2617994F;
    private float earZ = 0;

    private final RendererModel rabbitLeftFoot;
    private final RendererModel rabbitRightFoot;
    private final RendererModel rabbitLeftCalf;
    private final RendererModel rabbitRightCalf;
    private final RendererModel rabbitLeftThigh;
    private final RendererModel rabbitRightThigh;
    private final RendererModel rabbitBody;
    private final RendererModel rabbitBodyAngora1;
    private final RendererModel rabbitBodyAngora2;
    private final RendererModel rabbitBodyAngora3;
    private final RendererModel rabbitBodyAngora4;
    private final RendererModel rabbitButtRound;
    private final RendererModel rabbitButt;
    private final RendererModel rabbitButtAngora1;
    private final RendererModel rabbitButtAngora2;
    private final RendererModel rabbitButtAngora3;
    private final RendererModel rabbitButtAngora4;
    private final RendererModel rabbitButtTube;
    private final RendererModel rabbitLeftArm;
    private final RendererModel rabbitRightArm;
    private final RendererModel rabbitHeadLeft;
    private final RendererModel rabbitHeadRight;
    private final RendererModel rabbitHeadMuzzle;
    private final RendererModel rabbitNose;
    private final RendererModel rabbitHeadMuzzleDwarf;
    private final RendererModel rabbitNoseDwarf;
    private final RendererModel rabbitLionHeadL;
    private final RendererModel rabbitLionHeadR;
    private final RendererModel rabbitLionEarL;
    private final RendererModel rabbitLionEarR;
    private final RendererModel rabbitLionHeadL1;
    private final RendererModel rabbitLionHeadR1;
    private final RendererModel rabbitLionEarL1;
    private final RendererModel rabbitLionEarR1;
    private final RendererModel rabbitRightEar;
    private final RendererModel rabbitLeftEar;
    private final RendererModel rabbitRightEarDwarf;
    private final RendererModel rabbitLeftEarDwarf;
    private final RendererModel rabbitTail;
    private float jumpRotation;
    private float testerFloat;

    public ModelEnhancedRabbit()
    {
        this.textureWidth = 64;
        this.textureHeight = 64;

        float xMove = -2.0F;

        this.rabbitLeftFoot = new RendererModel(this, 0, 55);
        this.rabbitLeftFoot.addBox(0F, 0F, 0F, 3, 8, 1);
        this.rabbitLeftFoot.setRotationPoint(0.0F, 5.0F, 1.0F + xMove);
        this.setRotationOffset(this.rabbitLeftFoot, 3.0F, 0.0F, 0.0F);
        this.rabbitLeftFoot.mirror = true;

        this.rabbitRightFoot = new RendererModel(this, 8, 55);
        this.rabbitRightFoot.addBox(0F, 0F, 0F, 3, 8, 1);
        this.rabbitRightFoot.setRotationPoint(0F, 5.0F, 1.0F + xMove);
        this.setRotationOffset(this.rabbitRightFoot, 3.0F, 0.0F, 0.0F);
        this.rabbitRightFoot.mirror = true;

        this.rabbitLeftCalf = new RendererModel(this, 0, 49);
        this.rabbitLeftCalf.addBox(0F, 0F, 0F, 3, 5, 1);
        this.rabbitLeftCalf.setRotationPoint(0.0F, 6.5F, 2.7F + xMove);
        this.setRotationOffset(this.rabbitLeftCalf, 2.0F, 0.0F, 0.0F);
        this.rabbitLeftCalf.addChild(rabbitLeftFoot);

        this.rabbitRightCalf = new RendererModel(this, 18, 49);
        this.rabbitRightCalf.addBox(0F, 0F, 0F, 3, 5, 1);
        this.rabbitRightCalf.setRotationPoint(0, 6.5F, 2.7F + xMove);
        this.setRotationOffset(this.rabbitRightCalf, 2.0F, 0.0F, 0.0F);
        this.rabbitRightCalf.addChild(rabbitRightFoot);

        this.rabbitLeftThigh = new RendererModel(this, 0, 37);
        this.rabbitLeftThigh.addBox(0F, 0F, 0F, 3, 6, 6);
        this.rabbitLeftThigh.setRotationPoint(-4.5F, 17F, 4F + xMove);
        this.rabbitLeftThigh.addChild(rabbitLeftCalf);

        this.rabbitRightThigh = new RendererModel(this, 18, 37);
        this.rabbitRightThigh.addBox(0F, 0F, 0F, 3, 6, 6);
        this.rabbitRightThigh.setRotationPoint(1.5F, 17F, 4F + xMove);
        this.rabbitRightThigh.addChild(rabbitRightCalf);

        this.rabbitBody = new RendererModel(this, 7, 8);
        this.rabbitBody.addBox(-3.5F, 0.0F, 0.0F, 7, 7, 9,0.5F);
        this.rabbitBody.setRotationPoint(0F, 16.0F, -4.5F + xMove);

        this.rabbitBodyAngora1 = new RendererModel(this, 7, 8);
        this.rabbitBodyAngora1.addBox(-3.5F, 0F, 0F, 7, 7, 9, 1F);
        this.rabbitBodyAngora1.setRotationPoint(0F, 15.5F, -4.5F);

        this.rabbitBodyAngora2 = new RendererModel(this, 7, 8);
        this.rabbitBodyAngora2.addBox(-3.5F, 0F, 0F, 7, 7, 9, 2F);
        this.rabbitBodyAngora2.setRotationPoint(0F, 15.0F, -4.5F);

        this.rabbitBodyAngora3 = new RendererModel(this, 7, 8);
        this.rabbitBodyAngora3.addBox(-3.5F, 0F, 0F, 7, 7, 9, 3F);
        this.rabbitBodyAngora3.setRotationPoint(0F, 14.5F, -4.5F);

        this.rabbitBodyAngora4 = new RendererModel(this, 7, 8);
        this.rabbitBodyAngora4.addBox(-3.5F, 0F, 0F, 7, 7, 9, 4F);
        this.rabbitBodyAngora4.setRotationPoint(0F, 14.0F, -4.5F);

        this.rabbitButtRound = new RendererModel(this, 30, 0);
        this.rabbitButtRound.addBox(-4.0F, 0.0F, 0.0F, 8, 8, 8, 0.5F);
        this.rabbitButtRound.setRotationPoint(0.0F, 14.0F, 2.5F + xMove);

        this.rabbitButt = new RendererModel(this, 30, 0);
        this.rabbitButt.addBox(-4.0F, 0.0F, 0.0F, 8, 8, 8);
        this.rabbitButt.setRotationPoint(0.0F, 15.0F, 2.5F + xMove);

        this.rabbitButtAngora1 = new RendererModel(this, 30, 0);
        this.rabbitButtAngora1.addBox(-4.0F, 0.0F, 0.0F, 8, 8, 8, 1F);
        this.rabbitButtAngora1.setRotationPoint(0.0F, 14.5F, 2.5F);

        this.rabbitButtAngora2 = new RendererModel(this, 30, 0);
        this.rabbitButtAngora2.addBox(-4.0F, 0.0F, 0.0F, 8, 8, 8, 2F);
        this.rabbitButtAngora2.setRotationPoint(0.0F, 14.0F, 2.5F);

        this.rabbitButtAngora3 = new RendererModel(this, 30, 0);
        this.rabbitButtAngora3.addBox(-4.0F, 0.0F, 0.0F, 8, 8, 8, 3F);
        this.rabbitButtAngora3.setRotationPoint(0.0F, 13.5F, 2.5F);

        this.rabbitButtAngora4 = new RendererModel(this, 30, 0);
        this.rabbitButtAngora4.addBox(-4.0F, 0.0F, 0.0F, 8, 8, 8, 4F);
        this.rabbitButtAngora4.setRotationPoint(0.0F, 13.0F, 2.5F);

        this.rabbitButtTube = new RendererModel(this, 30, 0);
        this.rabbitButtTube.addBox(-4.0F, 0.0F, 0.0F, 8, 8, 8, -0.49F);
        this.rabbitButtTube.setRotationPoint(0.0F, 16.1F, 2.5F + xMove);

        this.rabbitLeftArm = new RendererModel(this, 16, 56);
        this.rabbitLeftArm.addBox(0.0F, 0.0F, 0.0F, 3, 6, 2);
        this.rabbitLeftArm.setRotationPoint(-3.5F, 23.5F, -2.0F + xMove);
        this.setRotationOffset(this.rabbitLeftArm, -1.6F, 0.0F, 0.0F);

        this.rabbitRightArm = new RendererModel(this, 26, 56);
        this.rabbitRightArm.addBox(0.0F, 0F, 0F, 3, 6, 2);
        this.rabbitRightArm.setRotationPoint(0.5F, 23.5F, -2.0F + xMove);
        this.setRotationOffset(this.rabbitRightArm, -1.6F, 0.0F, 0.0F);

        this.rabbitHeadLeft = new RendererModel(this, 0, 24);
        this.rabbitHeadLeft.addBox(0.0F, 0.0F, 0.0F, 3, 6, 6);
        this.rabbitHeadLeft.setRotationPoint(0.0F, 14.0F, -9.0F + xMove);

        this.rabbitHeadRight = new RendererModel(this, 18, 24);
        this.rabbitHeadRight.addBox(-3.0F, 0F, 0F, 3, 6, 6);
        this.rabbitHeadRight.setRotationPoint(0.0F, 14.0F, -9.0F + xMove);

        this.rabbitHeadMuzzle = new RendererModel(this, 0, 8);
        this.rabbitHeadMuzzle.addBox(-2F, 1.5F, -2F, 4, 4, 4);
        this.rabbitHeadMuzzle.setRotationPoint(0.0F, 0.0F, 0.0F + xMove);

        this.rabbitNose = new RendererModel(this, 0, 8);
        this.rabbitNose.addBox(-0.5F, 1.6F, -2.1F, 1, 1, 1);

        this.rabbitHeadMuzzleDwarf = new RendererModel(this, 0, 8);
        this.rabbitHeadMuzzleDwarf.addBox(-2F, 1.5F, -1F, 4, 4, 4);
        this.rabbitHeadMuzzleDwarf.setRotationPoint(0.0F, 0.0F, 0.0F + xMove);

        this.rabbitNoseDwarf = new RendererModel(this, 0, 8);
        this.rabbitNoseDwarf.addBox(-0.5F, 1.6F, -1.1F, 1, 1, 1);

        this.rabbitLionHeadL = new RendererModel(this, 33, 30);
        this.rabbitLionHeadL.addBox(0.0F, 0.0F, 0.0F, 3, 6, 6, 0.5F);

        this.rabbitLionHeadR = new RendererModel(this, 33, 18);
        this.rabbitLionHeadR.addBox(-3.0F, 0.0F, 0.0F, 3, 6, 6, 0.5F);

        this.rabbitLionEarL = new RendererModel(this, 40, 46);
        this.rabbitLionEarL.addBox(0.0F, -7.0F, 2.0F, 4, 7, 1, 0.4F);

        this.rabbitLionEarR = new RendererModel(this, 50, 46);
        this.rabbitLionEarR.addBox(-4.0F, -7.0F, 2.0F, 4, 7, 1, 0.4F);

        this.rabbitLionHeadL1 = new RendererModel(this, 33, 30);
        this.rabbitLionHeadL1.addBox(0.0F, 0.0F, 0.0F, 3, 6, 6, 1.1F);

        this.rabbitLionHeadR1 = new RendererModel(this, 33, 18);
        this.rabbitLionHeadR1.addBox(-3.0F, 0.0F, 0.0F, 3, 6, 6, 1.1F);

        this.rabbitLionEarL1 = new RendererModel(this, 40, 46);
        this.rabbitLionEarL1.addBox(0.0F, -7.0F, 2.0F, 4, 7, 1, 0.5F);
        this.rabbitLionEarL1.setRotationPoint(1.0F, 14.0F, 0.0F);

        this.rabbitLionEarR1 = new RendererModel(this, 50, 46);
        this.rabbitLionEarR1.addBox(-4.0F, -7.0F, 2.0F, 4, 7, 1, 0.5F);
        this.rabbitLionEarR1.setRotationPoint(-1.0F, 14.0F, 0.0F);

        this.rabbitLeftEar = new RendererModel(this, 0, 0);
        this.rabbitLeftEar.addBox(0.0F, -7.0F, 2.0F, 4, 7, 1);
        this.rabbitLeftEar.setRotationPoint(1.0F, 14.0F, 0.0F + xMove);

        this.rabbitRightEar = new RendererModel(this, 10, 0);
        this.rabbitRightEar.addBox(-4.0F, -7.0F, 2.0F, 4, 7, 1);
        this.rabbitRightEar.setRotationPoint(-1.0F, 14.0F, 0.0F + xMove);

        this.rabbitLeftEarDwarf = new RendererModel(this, 0, 0);
        this.rabbitLeftEarDwarf.addBox(0.0F, -4.0F, 2.0F, 4, 4, 1);
        this.rabbitLeftEarDwarf.setRotationPoint(1.0F, 14.0F, 0.0F);

        this.rabbitRightEarDwarf = new RendererModel(this, 10, 0);
        this.rabbitRightEarDwarf.addBox(-4.0F, -4.0F, 2.0F, 4, 4, 1);
        this.rabbitRightEarDwarf.setRotationPoint(-1.0F, 14.0F, 0.0F);

        this.rabbitTail = new RendererModel(this, 20, 0);
        this.rabbitTail.addBox(-1.5F, 2.0F, 8.0F, 3, 4, 2);
        this.rabbitTail.setRotationPoint(0.0F, 0.0F, 0.0F + xMove);
    }

    private void setRotationOffset(RendererModel renderer, float x, float y, float z) {
        renderer.rotateAngleX = x;
        renderer.rotateAngleY = y;
        renderer.rotateAngleZ = z;
    }

    /**
     * Sets the models various rotation angles then renders the model.
     */
    @Override
    public void render(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        EnhancedRabbit enhancedRabbit = (EnhancedRabbit) entityIn;

        this.coatlength = enhancedRabbit.getCoatLength();

        int[] genes = enhancedRabbit.getSharedGenes();

        float size = 1F; // [minimum size = 0.3 maximum size = 1]

        if (genes[46] < 5){
            size = size - 0.07F;
            if (genes[46] < 4){
                size = size - 0.07F;
                if (genes[46] < 3){
                    size = size - 0.07F;
                    if (genes[46] < 2){
                        size = size - 0.03F;
                    }
                }
            }
        }
        if (genes[46] < 5){
            size = size - 0.07F;
            if (genes[46] < 4){
                size = size - 0.07F;
                if (genes[46] < 3){
                    size = size - 0.07F;
                    if (genes[46] < 2){
                        size = size - 0.03F;
                    }
                }
            }
        }
        if ( genes[48] == 3 && genes[49] == 3){
            size = size - 0.075F;
        }else if ( genes[48] == 2 && genes[49] == 2){
            size = size - 0.05F;
        }else if ( genes[48] == 2 || genes[49] == 2){
            size = size - 0.025F;
        }

        if ( genes[34] == 2 || genes[35] == 2){
            dwarf = true;
            size = 0.3F + ((size - 0.3F)/2F);
        }else{
            dwarf = false;
        }

        this.setRotationAngles(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);

        if (this.isChild) {
//            float f = 1.5F;
            GlStateManager.pushMatrix();
            GlStateManager.scalef(0.28F, 0.28F, 0.28F);
            GlStateManager.translatef(0.0F, -1.5F + 1.5F/0.28F, 2.0F * scale);
            this.rabbitHeadLeft.render(scale);
            this.rabbitHeadRight.render(scale);
            if (dwarf){
                this.rabbitHeadMuzzleDwarf.render(scale);
                this.rabbitNoseDwarf.render(scale);
                this.rabbitLeftEarDwarf.render(scale);
                this.rabbitRightEarDwarf.render(scale);
            }else{
                this.rabbitHeadMuzzle.render(scale);
                this.rabbitNose.render(scale);
                this.rabbitLeftEar.render(scale);
                this.rabbitRightEar.render(scale);
            }
            GlStateManager.popMatrix();
            GlStateManager.pushMatrix();
            GlStateManager.scalef(0.20F, 0.20F, 0.20F);
            GlStateManager.translatef(0.0F, -1.5F + 1.5F/0.2F, 0.0F);
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
            GlStateManager.pushMatrix();
            GlStateManager.scalef(size, size, size);
            GlStateManager.translatef(0.0F, -1.5F + 1.5F/size, 0.0F);
            this.rabbitHeadLeft.render(scale);
            this.rabbitHeadRight.render(scale);
            if (genes[24] == 2 && genes[25] == 2){
                this.rabbitLionHeadL1.render(scale);
                this.rabbitLionHeadR1.render(scale);
                this.rabbitLionEarL1.render(scale);
                this.rabbitLionEarR1.render(scale);
            }else if (genes[24] == 2 || genes[25] == 2){
                this.rabbitLionHeadL.render(scale);
                this.rabbitLionHeadR.render(scale);
                this.rabbitLionEarL.render(scale);
                this.rabbitLionEarR.render(scale);
            }
            if (genes[34] == 2 || genes[35] == 2){
                this.rabbitHeadMuzzleDwarf.render(scale);
                this.rabbitNoseDwarf.render(scale);
                this.rabbitLeftEarDwarf.render(scale);
                this.rabbitRightEarDwarf.render(scale);
            }else{
                this.rabbitHeadMuzzle.render(scale);
                this.rabbitNose.render(scale);
                this.rabbitLeftEar.render(scale);
                this.rabbitRightEar.render(scale);
            }
            this.rabbitLeftThigh.render(scale);
            this.rabbitRightThigh.render(scale);
            if (coatlength == 0){
                this.rabbitBody.render(scale);
                //this.rabbitButtRound.render(scale);
                this.rabbitButt.render(scale);
                //this.rabbitButtTube.render(scale);
            }else{
                if(coatlength == 1){
                    this.rabbitBodyAngora1.render(scale);
                    //this.rabbitButtRoundAngora1.render(scale);
                    this.rabbitButtAngora1.render(scale);
                    //this.rabbitButtTubeAngora1.render(scale);
                }else if(coatlength == 2){
                    this.rabbitBodyAngora2.render(scale);
                    //this.rabbitButtRoundAngora2.render(scale);
                    this.rabbitButtAngora2.render(scale);
                    //this.rabbitButtTubeAngora2.render(scale);
                }else if(coatlength == 3){
                    this.rabbitBodyAngora3.render(scale);
                    //this.rabbitButtRoundAngora3.render(scale);
                    this.rabbitButtAngora3.render(scale);
                    //this.rabbitButtTubeAngora3.render(scale);
                }else if(coatlength == 4){
                    this.rabbitBodyAngora4.render(scale);
                    //this.rabbitButtRoundAngora4.render(scale);
                    this.rabbitButtAngora4.render(scale);
                    //this.rabbitButtTubeAngora4.render(scale);
                }
            }

            this.rabbitLeftArm.render(scale);
            this.rabbitRightArm.render(scale);
            this.rabbitTail.render(scale);
            GlStateManager.popMatrix();
        }
    }

    /**
     * Sets the model's various rotation angles. For bipeds, par1 and par2 are used for animating the movement of arms
     * and legs, where par1 represents the time(so that arms and legs swing back and forth) and par2 represents how
     * "far" arms and legs can swing at most.
     */
    @Override
    public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {
        float f = ageInTicks - (float)entityIn.ticksExisted;

        this.rabbitHeadLeft.rotateAngleX = headPitch * 0.017453292F;
        this.rabbitHeadLeft.rotateAngleY = netHeadYaw * 0.017453292F;
        copyModelAngles(rabbitHeadLeft, rabbitHeadRight);
        copyModelAngles(rabbitHeadLeft, rabbitHeadMuzzle);
        copyModelAngles(rabbitHeadLeft, rabbitNose);
        copyModelAngles(rabbitHeadLeft, rabbitHeadMuzzleDwarf);
        copyModelAngles(rabbitHeadLeft, rabbitNoseDwarf);
        copyModelAngles(rabbitHeadLeft, rabbitLeftEar);
        copyModelAngles(rabbitHeadLeft, rabbitRightEar);
        copyModelAngles(rabbitHeadLeft, rabbitLionHeadL);
        copyModelAngles(rabbitHeadRight, rabbitLionHeadR);
        copyModelAngles(rabbitHeadLeft, rabbitLionHeadL1);
        copyModelAngles(rabbitHeadRight, rabbitLionHeadR1);
        this.rabbitNose.rotateAngleY = netHeadYaw * 0.017453292F;
//        this.rabbitLeftEar.rotateAngleY = this.rabbitNose.rotateAngleY + 0.2617994F;
//        this.rabbitRightEar.rotateAngleY = this.rabbitNose.rotateAngleY - 0.2617994F;

        this.rabbitLeftEar.rotateAngleX = this.rabbitLeftEar.rotateAngleX + earX;
        this.rabbitRightEar.rotateAngleX = this.rabbitRightEar.rotateAngleX + earX;

        this.rabbitLeftEar.rotateAngleY = this.rabbitNose.rotateAngleY + earY;
        this.rabbitRightEar.rotateAngleY = this.rabbitNose.rotateAngleY - earY;

        this.rabbitLeftEar.rotateAngleZ = this.rabbitLeftEar.rotateAngleZ + earZ;
        this.rabbitRightEar.rotateAngleZ = this.rabbitRightEar.rotateAngleZ - earZ;

        copyModelAngles(rabbitLeftEar, rabbitLeftEarDwarf);
        copyModelAngles(rabbitRightEar, rabbitRightEarDwarf);
        copyModelAngles(rabbitLeftEar, rabbitLionEarL);
        copyModelAngles(rabbitRightEar, rabbitLionEarR);
        copyModelAngles(rabbitLeftEar, rabbitLionEarL1);
        copyModelAngles(rabbitRightEar, rabbitLionEarR1);

        //changes some rotation angles
        this.rabbitButtRound.rotationPointZ = 2.5F;
        this.rabbitButt.rotationPointZ = 2.5F;
        this.rabbitButtTube.rotationPointZ = 2.5F;

        if(dwarf) {
            this.rabbitButtRound.rotationPointZ = 0.5F;
            this.rabbitButt.rotationPointZ = 0.5F;
            this.rabbitButtTube.rotationPointZ = 0.5F;
        }

        //TODO add twitching nose

        this.jumpRotation = MathHelper.sin(((EnhancedRabbit)entityIn).getJumpCompletion(f) * (float)Math.PI);
        if (this.jumpRotation == 0.0F) {
            this.rabbitLeftFoot.rotateAngleX = 3.0F;
            this.rabbitRightFoot.rotateAngleX = 3.0F;
            this.rabbitLeftCalf.rotateAngleX = 2.0F;
            this.rabbitRightCalf.rotateAngleX = 2.0F;
            this.rabbitLeftArm.rotateAngleX = -1.6F;
            this.rabbitRightArm.rotateAngleX = -1.6F;
            this.rabbitBody.rotateAngleX = 0.0F;
            this.rabbitBodyAngora1.rotateAngleX = 0.0F;
            this.rabbitBodyAngora2.rotateAngleX = 0.0F;
            this.rabbitBodyAngora3.rotateAngleX = 0.0F;
            this.rabbitBodyAngora4.rotateAngleX = 0.0F;
            this.rabbitButtRound.rotateAngleX = 0.0F;
            this.rabbitButt.rotateAngleX = 0.0F;
            this.rabbitButtAngora1.rotateAngleX = 0.0F;
            this.rabbitButtAngora2.rotateAngleX = 0.0F;
            this.rabbitButtAngora3.rotateAngleX = 0.0F;
            this.rabbitButtAngora4.rotateAngleX = 0.0F;
            this.rabbitButtTube.rotateAngleX = 0.0F;
        } else {
            this.rabbitLeftFoot.rotateAngleX = 3.0F + this.jumpRotation * 80.0F * ((float)Math.PI / 180F);
            this.rabbitRightFoot.rotateAngleX = 3.0F + this.jumpRotation * 80.0F * ((float)Math.PI / 180F);
            this.rabbitLeftCalf.rotateAngleX = 2.0F - (this.jumpRotation * +50.0F * ((float)Math.PI / 180F));
            this.rabbitRightCalf.rotateAngleX = 2.0F - (this.jumpRotation * +50.0F * ((float)Math.PI / 180F));
            this.rabbitLeftArm.rotateAngleX = -1.6F - (this.jumpRotation * -40.0F - 11.0F) * ((float)Math.PI / 180F);
            this.rabbitRightArm.rotateAngleX = -1.6F - (this.jumpRotation * -40.0F - 11.0F) * ((float)Math.PI / 180F);
            this.rabbitButt.rotateAngleX = (this.jumpRotation * +30.0F) * ((float)Math.PI / 180F);
            this.rabbitButtAngora1.rotateAngleX = (this.jumpRotation * +30.0F) * ((float)Math.PI / 180F);
            this.rabbitButtAngora2.rotateAngleX = (this.jumpRotation * +30.0F) * ((float)Math.PI / 180F);
            this.rabbitButtAngora3.rotateAngleX = (this.jumpRotation * +30.0F) * ((float)Math.PI / 180F);
            this.rabbitButtAngora4.rotateAngleX = (this.jumpRotation * +30.0F) * ((float)Math.PI / 180F);
            this.rabbitBody.rotateAngleX = (this.jumpRotation * +15.0F) * ((float)Math.PI / 180F);
            this.rabbitBodyAngora1.rotateAngleX = (this.jumpRotation * +15.0F) * ((float)Math.PI / 180F);
            this.rabbitBodyAngora2.rotateAngleX = (this.jumpRotation * +15.0F) * ((float)Math.PI / 180F);
            this.rabbitBodyAngora3.rotateAngleX = (this.jumpRotation * +15.0F) * ((float)Math.PI / 180F);
            this.rabbitBodyAngora4.rotateAngleX = (this.jumpRotation * +15.0F) * ((float)Math.PI / 180F);
        }
        this.rabbitLeftThigh.rotateAngleX = (this.jumpRotation * 50.0F - 21.0F) * ((float)Math.PI / 180F);
        this.rabbitRightThigh.rotateAngleX = (this.jumpRotation * 50.0F - 21.0F) * ((float)Math.PI / 180F);


        copyModelAngles(rabbitButt, rabbitTail);
    }

    /**
     * Used for easily adding entity-dependent animations. The second and third float params here are the same second
     * and third as in the setRotationAngles method.
     */
    @Override
    public void setLivingAnimations(T entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTickTime){
        super.setLivingAnimations(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTickTime);
        this.jumpRotation = MathHelper.sin(((EnhancedRabbit)entitylivingbaseIn).getJumpCompletion(partialTickTime) * (float)Math.PI);
    }

    public static void copyModelAngles(RendererModel source, RendererModel dest) {
        dest.rotateAngleX = source.rotateAngleX;
        dest.rotateAngleY = source.rotateAngleY;
        dest.rotateAngleZ = source.rotateAngleZ;
        dest.rotationPointX = source.rotationPointX;
        dest.rotationPointY = source.rotationPointY;
        dest.rotationPointZ = source.rotationPointZ;
    }
}
