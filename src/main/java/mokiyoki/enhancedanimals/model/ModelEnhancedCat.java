package mokiyoki.enhancedanimals.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import mokiyoki.enhancedanimals.entity.EnhancedCat;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.world.ClientWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.HashMap;
import java.util.Map;

@OnlyIn(Dist.CLIENT)
public class ModelEnhancedCat<T extends EnhancedCat> extends EntityModel<T> {

    private Map<Integer, CatModelData> catModelDataCache = new HashMap<>();
    private int clearCacheTimer = 0;

    private float earX = 0;
    private float earY = 0.2617994F;
    private float earZ = 0;

    private final ModelRenderer catLeftFoot;
    private final ModelRenderer catRightFoot;
    private final ModelRenderer catLeftCalf;
    private final ModelRenderer catRightCalf;
    private final ModelRenderer catLeftThigh;
    private final ModelRenderer catRightThigh;
    private final ModelRenderer catBody;
    private final ModelRenderer catBodyAngora1;
    private final ModelRenderer catBodyAngora2;
    private final ModelRenderer catBodyAngora3;
    private final ModelRenderer catBodyAngora4;
    private final ModelRenderer catButtRound;
    private final ModelRenderer catButt;
    private final ModelRenderer catButtAngora1;
    private final ModelRenderer catButtAngora2;
    private final ModelRenderer catButtAngora3;
    private final ModelRenderer catButtAngora4;
    private final ModelRenderer catButtTube;
    private final ModelRenderer catLeftArm;
    private final ModelRenderer catRightArm;
    private final ModelRenderer catHeadLeft;
    private final ModelRenderer catHeadRight;
    private final ModelRenderer catHeadMuzzle;
    private final ModelRenderer catNose;
    private final ModelRenderer catHeadMuzzleDwarf;
    private final ModelRenderer catNoseDwarf;
    private final ModelRenderer catLionHeadL;
    private final ModelRenderer catLionHeadR;
    private final ModelRenderer catLionHeadL1;
    private final ModelRenderer catLionHeadR1;
    private final ModelRenderer LionEarParent;
    private final ModelRenderer LionEarL;
    private final ModelRenderer LionEarR;
    private final ModelRenderer LionEarParent1;
    private final ModelRenderer LionEarL1;
    private final ModelRenderer LionEarR1;
    private final ModelRenderer EarParent;
    private final ModelRenderer EarL;
    private final ModelRenderer EarR;
    private final ModelRenderer DwarfParent;
    private final ModelRenderer DwarfEarL;
    private final ModelRenderer DwarfEarR;
    private final ModelRenderer catTail;

    private Integer currentCat = null;

    public ModelEnhancedCat()
    {
        this.textureWidth = 64;
        this.textureHeight = 64;

        float xMove = -2.0F;

        this.catLeftFoot = new ModelRenderer(this, 0, 55);
        this.catLeftFoot.addBox(0F, 0F, 0F, 3, 8, 1);
        this.catLeftFoot.setRotationPoint(0.0F, 5.0F, 1.0F + xMove);
        this.setRotationOffset(this.catLeftFoot, 3.0F, 0.0F, 0.0F);
        this.catLeftFoot.mirror = true;

        this.catRightFoot = new ModelRenderer(this, 8, 55);
        this.catRightFoot.addBox(0F, 0F, 0F, 3, 8, 1);
        this.catRightFoot.setRotationPoint(0F, 5.0F, 1.0F + xMove);
        this.setRotationOffset(this.catRightFoot, 3.0F, 0.0F, 0.0F);
        this.catRightFoot.mirror = true;

        this.catLeftCalf = new ModelRenderer(this, 0, 49);
        this.catLeftCalf.addBox(0F, 0F, 0F, 3, 5, 1);
        this.catLeftCalf.setRotationPoint(0.0F, 6.5F, 2.7F + xMove);
        this.setRotationOffset(this.catLeftCalf, 2.0F, 0.0F, 0.0F);
        this.catLeftCalf.addChild(catLeftFoot);

        this.catRightCalf = new ModelRenderer(this, 18, 49);
        this.catRightCalf.addBox(0F, 0F, 0F, 3, 5, 1);
        this.catRightCalf.setRotationPoint(0, 6.5F, 2.7F + xMove);
        this.setRotationOffset(this.catRightCalf, 2.0F, 0.0F, 0.0F);
        this.catRightCalf.addChild(catRightFoot);

        this.catLeftThigh = new ModelRenderer(this, 0, 37);
        this.catLeftThigh.addBox(0F, 0F, 0F, 3, 6, 6);
        this.catLeftThigh.setRotationPoint(-4.5F, 1.0F, 2.5F);
        this.catLeftThigh.addChild(catLeftCalf);

        this.catRightThigh = new ModelRenderer(this, 18, 37);
        this.catRightThigh.addBox(0F, 0F, 0F, 3, 6, 6);
        this.catRightThigh.setRotationPoint(1.5F, 1.0F, 2.5F);
        this.catRightThigh.addChild(catRightCalf);

        this.catBody = new ModelRenderer(this, 7, 8);
        this.catBody.addBox(-3.5F, 0.0F, 0.0F, 7, 7, 9,0.5F);
        this.catBody.setRotationPoint(0.0F, 16.0F, -4.5F + xMove);

        this.catBodyAngora1 = new ModelRenderer(this, 7, 8);
        this.catBodyAngora1.addBox(-3.5F, 0F, 0F, 7, 7, 9, 1F);
        this.catBodyAngora1.setRotationPoint(0.0F, 15.5F, -4.5F);

        this.catBodyAngora2 = new ModelRenderer(this, 7, 8);
        this.catBodyAngora2.addBox(-3.5F, 0F, 0F, 7, 7, 9, 2F);
        this.catBodyAngora2.setRotationPoint(0.0F, 15.0F, -4.5F);

        this.catBodyAngora3 = new ModelRenderer(this, 7, 8);
        this.catBodyAngora3.addBox(-3.5F, 0F, 0F, 7, 7, 9, 3F);
        this.catBodyAngora3.setRotationPoint(0.0F, 14.5F, -4.5F);

        this.catBodyAngora4 = new ModelRenderer(this, 7, 8);
        this.catBodyAngora4.addBox(-3.5F, 0F, 0F, 7, 7, 9, 4F);
        this.catBodyAngora4.setRotationPoint(0.0F, 14.0F, -4.5F);

        this.catButtRound = new ModelRenderer(this, 30, 0);
        this.catButtRound.addBox(-4.0F, 0.0F, 0.0F, 8, 8, 8, 0.5F);
        this.catButtRound.setRotationPoint(0.0F, 14.0F, 2.5F + xMove);
        this.catButtRound.addChild(this.catLeftThigh);
        this.catButtRound.addChild(this.catRightThigh);

        this.catButt = new ModelRenderer(this, 30, 0);
        this.catButt.addBox(-4.0F, 0.0F, 0.0F, 8, 8, 8);
        this.catButt.setRotationPoint(0.0F, 15.0F, 2.5F + xMove);
        this.catButt.addChild(this.catLeftThigh);
        this.catButt.addChild(this.catRightThigh);

        this.catButtTube = new ModelRenderer(this, 30, 0);
        this.catButtTube.addBox(-4.0F, 0.0F, 0.0F, 8, 8, 8, -0.49F);
        this.catButtTube.setRotationPoint(0.0F, 16.1F, 2.5F + xMove);
        this.catButtTube.addChild(this.catLeftThigh);
        this.catButtTube.addChild(this.catRightThigh);

        this.catButtAngora1 = new ModelRenderer(this, 30, 0);
        this.catButtAngora1.addBox(-4.0F, 0.0F, 0.0F, 8, 8, 8, 1F);
        this.catButtAngora1.setRotationPoint(0.0F, 14.5F, 2.5F);
        this.catButtAngora1.addChild(this.catLeftThigh);
        this.catButtAngora1.addChild(this.catRightThigh);

        this.catButtAngora2 = new ModelRenderer(this, 30, 0);
        this.catButtAngora2.addBox(-4.0F, 0.0F, 0.0F, 8, 8, 8, 2F);
        this.catButtAngora2.setRotationPoint(0.0F, 14.0F, 2.5F);
        this.catButtAngora2.addChild(this.catLeftThigh);
        this.catButtAngora2.addChild(this.catRightThigh);

        this.catButtAngora3 = new ModelRenderer(this, 30, 0);
        this.catButtAngora3.addBox(-4.0F, 0.0F, 0.0F, 8, 8, 8, 3F);
        this.catButtAngora3.setRotationPoint(0.0F, 13.5F, 2.5F);
        this.catButtAngora3.addChild(this.catLeftThigh);
        this.catButtAngora3.addChild(this.catRightThigh);

        this.catButtAngora4 = new ModelRenderer(this, 30, 0);
        this.catButtAngora4.addBox(-4.0F, 0.0F, 0.0F, 8, 8, 8, 4F);
        this.catButtAngora4.setRotationPoint(0.0F, 13.0F, 2.5F);
        this.catButtAngora4.addChild(this.catLeftThigh);
        this.catButtAngora4.addChild(this.catRightThigh);

        this.catLeftArm = new ModelRenderer(this, 16, 56);
        this.catLeftArm.addBox(0.0F, 0.0F, 0.0F, 3, 6, 2);
        this.catLeftArm.setRotationPoint(-3.5F, 23.5F, -2.0F + xMove);
        this.setRotationOffset(this.catLeftArm, -1.6F, 0.0F, 0.0F);

        this.catRightArm = new ModelRenderer(this, 26, 56);
        this.catRightArm.addBox(0.0F, 0F, 0F, 3, 6, 2);
        this.catRightArm.setRotationPoint(0.5F, 23.5F, -2.0F + xMove);
        this.setRotationOffset(this.catRightArm, -1.6F, 0.0F, 0.0F);

        this.catHeadLeft = new ModelRenderer(this, 0, 24);
        this.catHeadLeft.addBox(0.0F, 0.0F, 0.0F, 3, 6, 6);
        this.catHeadLeft.setRotationPoint(0.0F, 14.0F, -11.0F);

        this.catHeadRight = new ModelRenderer(this, 18, 24);
        this.catHeadRight.addBox(-3.0F, 0F, 0F, 3, 6, 6);
        this.catHeadRight.setRotationPoint(0.0F, 14.0F, -9.0F + xMove);

        this.catHeadMuzzle = new ModelRenderer(this, 0, 8);
        this.catHeadMuzzle.addBox(-2F, 1.5F, -2F, 4, 4, 4);
        this.catHeadMuzzle.setRotationPoint(0.0F, 0.0F, 0.0F + xMove);

        this.catNose = new ModelRenderer(this, 0, 8);
        this.catNose.addBox(-0.5F, 1.6F, -2.1F, 1, 1, 1);

        this.catHeadMuzzleDwarf = new ModelRenderer(this, 0, 8);
        this.catHeadMuzzleDwarf.addBox(-2F, 1.5F, -1F, 4, 4, 4);
        this.catHeadMuzzleDwarf.setRotationPoint(0.0F, 0.0F, 0.0F + xMove);

        this.catNoseDwarf = new ModelRenderer(this, 0, 8);
        this.catNoseDwarf.addBox(-0.5F, 1.6F, -1.1F, 1, 1, 1);

        this.catLionHeadL = new ModelRenderer(this, 33, 30);
        this.catLionHeadL.addBox(0.0F, 0.0F, 0.0F, 3, 6, 6, 0.5F);

        this.catLionHeadR = new ModelRenderer(this, 33, 18);
        this.catLionHeadR.addBox(-3.0F, 0.0F, 0.0F, 3, 6, 6, 0.5F);

        this.catLionHeadL1 = new ModelRenderer(this, 33, 30);
        this.catLionHeadL1.addBox(0.0F, 0.0F, 0.0F, 3, 6, 6, 1.1F);

        this.catLionHeadR1 = new ModelRenderer(this, 33, 18);
        this.catLionHeadR1.addBox(-3.0F, 0.0F, 0.0F, 3, 6, 6, 1.1F);

        this.LionEarParent = new ModelRenderer(this, 60, 62);
        this.LionEarL = new ModelRenderer(this, 50, 46);
        this.LionEarL.addBox(-3.0F, -7.0F, -0.5F, 4, 7, 1, 0.4F);
        this.LionEarR = new ModelRenderer(this, 40, 46);
        this.LionEarR.addBox(-1.0F, -7.0F, -0.5F, 4, 7, 1, 0.4F);
        this.LionEarParent.addChild(LionEarL);
        this.LionEarParent.addChild(LionEarR);

        this.LionEarParent1 = new ModelRenderer(this, 60, 62);
        this.LionEarL1 = new ModelRenderer(this, 50, 46);
        this.LionEarL1.addBox(-3.0F, -7.0F, -0.5F, 4, 7, 1, 0.5F);
        this.LionEarR1 = new ModelRenderer(this, 40, 46);
        this.LionEarR1.addBox(-1.0F, -7.0F, -0.5F, 4, 7, 1, 0.5F);
        this.LionEarParent1.addChild(LionEarL1);
        this.LionEarParent1.addChild(LionEarR1);

        this.EarParent = new ModelRenderer(this, 60, 62);
        this.EarL = new ModelRenderer(this, 10, 0);
        this.EarL.addBox(-3.0F, -7.0F, -0.5F, 4, 7, 1);
        this.EarR = new ModelRenderer(this, 0, 0);
        this.EarR.addBox(-1.0F, -7.0F, -0.5F, 4, 7, 1);
        this.EarParent.addChild(EarL);
        this.EarParent.addChild(EarR);

        this.DwarfParent = new ModelRenderer(this, 60, 62);
        this.DwarfEarL = new ModelRenderer(this, 10, 0);
        this.DwarfEarL.addBox(-3.0F, -5.0F, -0.5F, 4, 5, 1);
        this.DwarfEarR = new ModelRenderer(this, 0, 0);
        this.DwarfEarR.addBox(-1.0F, -5.0F, -0.5F, 4, 5, 1);
        this.DwarfParent.addChild(DwarfEarL);
        this.DwarfParent.addChild(DwarfEarR);

        this.catTail = new ModelRenderer(this, 20, 0);
        this.catTail.addBox(-1.5F, 2.0F, 8.0F, 3, 4, 2);
        this.catTail.setRotationPoint(0.0F, 0.0F, 0.0F + xMove);
    }

    private void setRotationOffset(ModelRenderer renderer, float x, float y, float z) {
        renderer.rotateAngleX = x;
        renderer.rotateAngleY = y;
        renderer.rotateAngleZ = z;
    }

    /**
     * Sets the models various rotation angles then renders the model.
     */
    @Override
    public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        CatModelData catModelData = getCatModelData();

        int[] genes = catModelData.catGenes;
        int coatLength = catModelData.coatlength;

        float size = 1F; // [minimum size = 0.3 maximum size = 1]

        if (genes != null) {
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
                catModelData.dwarf = true;
                size = 0.3F + ((size - 0.3F)/2F);
            }else{
                catModelData.dwarf = false;
            }
        } else {
            catModelData.dwarf = false;
        }


        float age = 1.0F;
        if (!(catModelData.birthTime == null) && !catModelData.birthTime.equals("") && !catModelData.birthTime.equals("0")) {
            int ageTime = (int)(catModelData.clientGameTime - Long.parseLong(catModelData.birthTime));
            if (ageTime <= 50000) {
                age = ageTime/50000.0F;
            }
        }

        float finalCatSize = (( 3.0F * size * age) + size) / 4.0F;

        matrixStackIn.push();
        matrixStackIn.scale(finalCatSize, finalCatSize, finalCatSize);
        matrixStackIn.translate(0.0F, -1.45F + 1.45F/finalCatSize, 0.0F);
            this.catHeadLeft.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            this.catHeadRight.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            if (genes != null) {
                if (genes[24] == 2 && genes[25] == 2){
                    this.catLionHeadL1.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                    this.catLionHeadR1.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                    this.LionEarParent1.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                }else if (genes[24] == 2 || genes[25] == 2){
                    this.catLionHeadL.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                    this.catLionHeadR.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                    this.LionEarParent.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                }
            }
            if (catModelData.dwarf){
                this.catHeadMuzzleDwarf.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                this.catNoseDwarf.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                this.DwarfParent.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            }else{
                this.catHeadMuzzle.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                this.catNose.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                this.EarParent.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            }

            if (coatLength == 0){
                this.catBody.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                //this.catButtRound.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                this.catButt.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                //this.catButtTube.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            }else{
                if(coatLength == 1){
                    this.catBodyAngora1.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                    //this.catButtRoundAngora1.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                    this.catButtAngora1.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                    //this.catButtTubeAngora1.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                }else if(coatLength == 2){
                    this.catBodyAngora2.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                    //this.catButtRoundAngora2.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                    this.catButtAngora2.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                    //this.catButtTubeAngora2.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                }else if(coatLength == 3){
                    this.catBodyAngora3.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                    //this.catButtRoundAngora3.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                    this.catButtAngora3.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                    //this.catButtTubeAngora3.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                }else if(coatLength == 4){
                    this.catBodyAngora4.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                    //this.catButtRoundAngora4.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                    this.catButtAngora4.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                    //this.catButtTubeAngora4.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                }
            }

            this.catLeftArm.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            this.catRightArm.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            this.catTail.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        matrixStackIn.pop();
//        }
    }

    /**
     * Used for easily adding entity-dependent animations. The second and third float params here are the same second
     * and third as in the setRotationAngles method.
     */
    @Override
    public void setLivingAnimations(T entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTickTime) {
        super.setLivingAnimations(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTickTime);
        CatModelData catModelData = getCreateCatModelData(entitylivingbaseIn);
        this.currentCat = entitylivingbaseIn.getEntityId();
    }

    /**
     * Sets the model's various rotation angles. For bipeds, par1 and par2 are used for animating the movement of arms
     * and legs, where par1 represents the time(so that arms and legs swing back and forth) and par2 represents how
     * "far" arms and legs can swing at most.
     */
    @Override
    public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        float f = ageInTicks - (float)entityIn.ticksExisted;

        CatModelData catModelData = getCatModelData();

        int[] genes = catModelData.catGenes;
        boolean dwarf = catModelData.dwarf;

        this.catHeadLeft.rotateAngleX = headPitch * 0.017453292F;
        this.catHeadLeft.rotateAngleY = netHeadYaw * 0.017453292F;
        copyModelAngles(catHeadLeft, catHeadRight);
        copyModelAngles(catHeadLeft, catHeadMuzzle);
        copyModelAngles(catHeadLeft, catHeadMuzzleDwarf);
        copyModelAngles(catHeadLeft, catLionHeadL);
        copyModelAngles(catHeadRight, catLionHeadR);
        copyModelAngles(catHeadLeft, catLionHeadL1);
        copyModelAngles(catHeadRight, catLionHeadR1);
        copyModelAngles(catHeadLeft, catNose);

        copyModelAngles(catHeadLeft, EarParent);
        copyModelAngles(catHeadLeft, LionEarParent);
        copyModelAngles(catHeadLeft, LionEarParent1);
        copyModelAngles(catHeadLeft, DwarfParent);

//        this.catNose.rotateAngleY = netHeadYaw * 0.017453292F;
//        this.EarL.rotateAngleY = Math.abs(this.catNose.rotateAngleY) * 1.0F;
//        this.EarR.rotateAngleY = -Math.abs(this.catNose.rotateAngleY) * 1.0F;

        float lop = 0;

        if (genes != null) {
            // genes 36 to 43
            if (genes[36] == 3 && genes[37] == 3) {
                lop = 0.25F;
            } else {
                if (genes[36] == 2) {
                    lop = 0.1F;
                }
                if (genes[37] == 2) {
                    lop = 0.1F;
                }
            }
            if (genes[38] == 2 && genes[39] == 2) {
                lop = lop * 4.0F;
            }

            lop = lop + ((genes[40]-1) * 0.1F);
            lop = lop + ((genes[41]-1) * 0.1F);

            if (genes[42] == 3 && genes[43] == 3) {
                lop = lop + 0.1F;
            }
        }

        if (lop >= 0.75F) {
            lop = 1.0F;
        } else {
            lop = 0;
        }

        float lookMod = Math.abs(this.catNose.rotateAngleY);

        float earPointX = 1.0F + (lookMod / 4.0F);
        float earPointY = 2.0F;
        float earPointZ = 4.5F;

        float earRotateX = (lookMod * 0.7F) - 1.35F;
        float earRotateY = 0.0F;
        float earRotateZ = (lookMod * 0.9F) - 1.4F;

        float floppyEarPointX = 3.5F;
        float floppyEarPointY = 2.0F;
        float floppyEarPointZ = 2.0F;

        float floppyEarRotateX = 0.3F;
        float floppyEarRotateY = 1.8F;
        float floppyEarRotateZ = 3.2F;

        earPointX = (earPointX*(1.0F-lop) + floppyEarPointX*(lop));
        earPointY = (earPointY*(1.0F-lop) + floppyEarPointY*(lop));
        earPointZ = (earPointZ*(1.0F-lop) + floppyEarPointZ*(lop));

        this.EarL.setRotationPoint(-earPointX, earPointY, earPointZ);
        this.EarR.setRotationPoint(earPointX, earPointY, earPointZ);

        this.EarL.rotateAngleX = (earRotateX*(1.0F-lop) + floppyEarRotateX*(-lop));
        this.EarR.rotateAngleX = (earRotateX*(1.0F-lop) + floppyEarRotateX*(-lop));
        this.EarL.rotateAngleY = (earRotateY*(1.0F-lop) + floppyEarRotateY*(lop));
        this.EarR.rotateAngleY = (earRotateY*(1.0F-lop) + (floppyEarRotateY)*(-lop));
        this.EarL.rotateAngleZ = (earRotateZ*(1.0F-lop) + floppyEarRotateZ*(lop));
        this.EarR.rotateAngleZ = (-earRotateZ*(1.0F-lop) + floppyEarRotateZ*(lop));

        copyModelAngles(EarL, LionEarL);
        copyModelAngles(EarR, LionEarR);
        copyModelAngles(EarL, LionEarL1);
        copyModelAngles(EarR, LionEarR1);
        copyModelAngles(EarL, DwarfEarL);
        copyModelAngles(EarR, DwarfEarR);

        //changes some rotation angles
        this.catButtRound.rotationPointZ = 2.5F;
        this.catButt.rotationPointZ = 2.5F;
        this.catButtTube.rotationPointZ = 2.5F;

        if(dwarf) {
            this.catButtRound.rotationPointZ = 0.5F;
            this.catButt.rotationPointZ = 0.5F;
            this.catButtTube.rotationPointZ = 0.5F;
        }

        copyModelAngles(catButt, catTail);

//        if (ageInTicks+(entityIn.getEntityId()*7)%20 == 0) {
//            if (entityIn.noseTwitch != 1) {
//                entityIn.noseTwitch = 1;
//            } else {
//                entityIn.noseTwitch = -1;
//            }
//        }

//        this.catNose.rotationPointY = 14.0F + entityIn.noseTwitch;
        copyModelAngles(catNose, catNoseDwarf);
    }

    public static void copyModelAngles(ModelRenderer source, ModelRenderer dest) {
        dest.rotateAngleX = source.rotateAngleX;
        dest.rotateAngleY = source.rotateAngleY;
        dest.rotateAngleZ = source.rotateAngleZ;
        dest.rotationPointX = source.rotationPointX;
        dest.rotationPointY = source.rotationPointY;
        dest.rotationPointZ = source.rotationPointZ;
    }

    private class CatModelData {
        int[] catGenes;
        String birthTime;
        int coatlength = 0;
        boolean dwarf = false;
        boolean sleeping = false;
        int lastAccessed = 0;
        int dataReset = 0;
        long clientGameTime = 0;
    }

    private CatModelData getCatModelData() {
        if (this.currentCat == null || !catModelDataCache.containsKey(this.currentCat)) {
            return new CatModelData();
        }
        return catModelDataCache.get(this.currentCat);
    }

    private CatModelData getCreateCatModelData(T enhancedCat) {
        clearCacheTimer++;
        if(clearCacheTimer > 50000) {
            catModelDataCache.values().removeIf(value -> value.lastAccessed==1);
            for (CatModelData catModelData : catModelDataCache.values()){
                catModelData.lastAccessed = 1;
            }
            clearCacheTimer = 0;
        }

        if (catModelDataCache.containsKey(enhancedCat.getEntityId())) {
            CatModelData catModelData = catModelDataCache.get(enhancedCat.getEntityId());
            catModelData.lastAccessed = 0;
            catModelData.dataReset++;
            if (catModelData.dataReset > 5000) {
//                catModelData.coatlength = enhancedCat.getCoatLength();
                catModelData.dataReset = 0;
            }
            catModelData.sleeping = enhancedCat.isAnimalSleeping();
            catModelData.clientGameTime = (((ClientWorld)enhancedCat.world).getWorldInfo()).getGameTime();

            return catModelData;
        } else {
            CatModelData catModelData = new CatModelData();
            catModelData.catGenes = enhancedCat.getSharedGenes();
//            catModelData.coatlength = enhancedCat.getCoatLength();
            catModelData.sleeping = enhancedCat.isAnimalSleeping();
//            catModelData.birthTime = enhancedCat.getBirthTime();
            catModelData.clientGameTime = (((ClientWorld)enhancedCat.world).getWorldInfo()).getGameTime();

            if(catModelData.catGenes != null) {
                catModelDataCache.put(enhancedCat.getEntityId(), catModelData);
            }

            return catModelData;
        }
    }

}
