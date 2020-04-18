package mokiyoki.enhancedanimals.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import mokiyoki.enhancedanimals.entity.EnhancedRabbit;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.storage.WorldInfo;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.HashMap;
import java.util.Map;

@OnlyIn(Dist.CLIENT)
public class ModelEnhancedRabbit <T extends EnhancedRabbit> extends EntityModel<T> {

    private Map<Integer, RabbitModelData> rabbitModelDataCache = new HashMap<>();
    private int clearCacheTimer = 0;

    private float earX = 0;
    private float earY = 0.2617994F;
    private float earZ = 0;

    private final ModelRenderer rabbitLeftFoot;
    private final ModelRenderer rabbitRightFoot;
    private final ModelRenderer rabbitLeftCalf;
    private final ModelRenderer rabbitRightCalf;
    private final ModelRenderer rabbitLeftThigh;
    private final ModelRenderer rabbitRightThigh;
    private final ModelRenderer rabbitBody;
    private final ModelRenderer rabbitBodyAngora1;
    private final ModelRenderer rabbitBodyAngora2;
    private final ModelRenderer rabbitBodyAngora3;
    private final ModelRenderer rabbitBodyAngora4;
    private final ModelRenderer rabbitButtRound;
    private final ModelRenderer rabbitButt;
    private final ModelRenderer rabbitButtAngora1;
    private final ModelRenderer rabbitButtAngora2;
    private final ModelRenderer rabbitButtAngora3;
    private final ModelRenderer rabbitButtAngora4;
    private final ModelRenderer rabbitButtTube;
    private final ModelRenderer rabbitLeftArm;
    private final ModelRenderer rabbitRightArm;
    private final ModelRenderer rabbitHeadLeft;
    private final ModelRenderer rabbitHeadRight;
    private final ModelRenderer rabbitHeadMuzzle;
    private final ModelRenderer rabbitNose;
    private final ModelRenderer rabbitHeadMuzzleDwarf;
    private final ModelRenderer rabbitNoseDwarf;
    private final ModelRenderer rabbitLionHeadL;
    private final ModelRenderer rabbitLionHeadR;
    private final ModelRenderer rabbitLionHeadL1;
    private final ModelRenderer rabbitLionHeadR1;
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
    private final ModelRenderer rabbitTail;
    private float jumpRotation;
    private float testerFloat;

    private Integer currentRabbit = null;

    public ModelEnhancedRabbit()
    {
        this.textureWidth = 64;
        this.textureHeight = 64;

        float xMove = -2.0F;

        this.rabbitLeftFoot = new ModelRenderer(this, 0, 55);
        this.rabbitLeftFoot.addBox(0F, 0F, 0F, 3, 8, 1);
        this.rabbitLeftFoot.setRotationPoint(0.0F, 5.0F, 1.0F + xMove);
        this.setRotationOffset(this.rabbitLeftFoot, 3.0F, 0.0F, 0.0F);
        this.rabbitLeftFoot.mirror = true;

        this.rabbitRightFoot = new ModelRenderer(this, 8, 55);
        this.rabbitRightFoot.addBox(0F, 0F, 0F, 3, 8, 1);
        this.rabbitRightFoot.setRotationPoint(0F, 5.0F, 1.0F + xMove);
        this.setRotationOffset(this.rabbitRightFoot, 3.0F, 0.0F, 0.0F);
        this.rabbitRightFoot.mirror = true;

        this.rabbitLeftCalf = new ModelRenderer(this, 0, 49);
        this.rabbitLeftCalf.addBox(0F, 0F, 0F, 3, 5, 1);
        this.rabbitLeftCalf.setRotationPoint(0.0F, 6.5F, 2.7F + xMove);
        this.setRotationOffset(this.rabbitLeftCalf, 2.0F, 0.0F, 0.0F);
        this.rabbitLeftCalf.addChild(rabbitLeftFoot);

        this.rabbitRightCalf = new ModelRenderer(this, 18, 49);
        this.rabbitRightCalf.addBox(0F, 0F, 0F, 3, 5, 1);
        this.rabbitRightCalf.setRotationPoint(0, 6.5F, 2.7F + xMove);
        this.setRotationOffset(this.rabbitRightCalf, 2.0F, 0.0F, 0.0F);
        this.rabbitRightCalf.addChild(rabbitRightFoot);

        this.rabbitLeftThigh = new ModelRenderer(this, 0, 37);
        this.rabbitLeftThigh.addBox(0F, 0F, 0F, 3, 6, 6);
        this.rabbitLeftThigh.setRotationPoint(-4.5F, 1.0F, 2.5F);
        this.rabbitLeftThigh.addChild(rabbitLeftCalf);

        this.rabbitRightThigh = new ModelRenderer(this, 18, 37);
        this.rabbitRightThigh.addBox(0F, 0F, 0F, 3, 6, 6);
        this.rabbitRightThigh.setRotationPoint(1.5F, 1.0F, 2.5F);
        this.rabbitRightThigh.addChild(rabbitRightCalf);

        this.rabbitBody = new ModelRenderer(this, 7, 8);
        this.rabbitBody.addBox(-3.5F, 0.0F, 0.0F, 7, 7, 9,0.5F);
        this.rabbitBody.setRotationPoint(0.0F, 16.0F, -4.5F + xMove);

        this.rabbitBodyAngora1 = new ModelRenderer(this, 7, 8);
        this.rabbitBodyAngora1.addBox(-3.5F, 0F, 0F, 7, 7, 9, 1F);
        this.rabbitBodyAngora1.setRotationPoint(0.0F, 15.5F, -4.5F);

        this.rabbitBodyAngora2 = new ModelRenderer(this, 7, 8);
        this.rabbitBodyAngora2.addBox(-3.5F, 0F, 0F, 7, 7, 9, 2F);
        this.rabbitBodyAngora2.setRotationPoint(0.0F, 15.0F, -4.5F);

        this.rabbitBodyAngora3 = new ModelRenderer(this, 7, 8);
        this.rabbitBodyAngora3.addBox(-3.5F, 0F, 0F, 7, 7, 9, 3F);
        this.rabbitBodyAngora3.setRotationPoint(0.0F, 14.5F, -4.5F);

        this.rabbitBodyAngora4 = new ModelRenderer(this, 7, 8);
        this.rabbitBodyAngora4.addBox(-3.5F, 0F, 0F, 7, 7, 9, 4F);
        this.rabbitBodyAngora4.setRotationPoint(0.0F, 14.0F, -4.5F);

        this.rabbitButtRound = new ModelRenderer(this, 30, 0);
        this.rabbitButtRound.addBox(-4.0F, 0.0F, 0.0F, 8, 8, 8, 0.5F);
        this.rabbitButtRound.setRotationPoint(0.0F, 14.0F, 2.5F + xMove);
        this.rabbitButtRound.addChild(this.rabbitLeftThigh);
        this.rabbitButtRound.addChild(this.rabbitRightThigh);

        this.rabbitButt = new ModelRenderer(this, 30, 0);
        this.rabbitButt.addBox(-4.0F, 0.0F, 0.0F, 8, 8, 8);
        this.rabbitButt.setRotationPoint(0.0F, 15.0F, 2.5F + xMove);
        this.rabbitButt.addChild(this.rabbitLeftThigh);
        this.rabbitButt.addChild(this.rabbitRightThigh);

        this.rabbitButtTube = new ModelRenderer(this, 30, 0);
        this.rabbitButtTube.addBox(-4.0F, 0.0F, 0.0F, 8, 8, 8, -0.49F);
        this.rabbitButtTube.setRotationPoint(0.0F, 16.1F, 2.5F + xMove);
        this.rabbitButtTube.addChild(this.rabbitLeftThigh);
        this.rabbitButtTube.addChild(this.rabbitRightThigh);

        this.rabbitButtAngora1 = new ModelRenderer(this, 30, 0);
        this.rabbitButtAngora1.addBox(-4.0F, 0.0F, 0.0F, 8, 8, 8, 1F);
        this.rabbitButtAngora1.setRotationPoint(0.0F, 14.5F, 2.5F);
        this.rabbitButtAngora1.addChild(this.rabbitLeftThigh);
        this.rabbitButtAngora1.addChild(this.rabbitRightThigh);

        this.rabbitButtAngora2 = new ModelRenderer(this, 30, 0);
        this.rabbitButtAngora2.addBox(-4.0F, 0.0F, 0.0F, 8, 8, 8, 2F);
        this.rabbitButtAngora2.setRotationPoint(0.0F, 14.0F, 2.5F);
        this.rabbitButtAngora2.addChild(this.rabbitLeftThigh);
        this.rabbitButtAngora2.addChild(this.rabbitRightThigh);

        this.rabbitButtAngora3 = new ModelRenderer(this, 30, 0);
        this.rabbitButtAngora3.addBox(-4.0F, 0.0F, 0.0F, 8, 8, 8, 3F);
        this.rabbitButtAngora3.setRotationPoint(0.0F, 13.5F, 2.5F);
        this.rabbitButtAngora3.addChild(this.rabbitLeftThigh);
        this.rabbitButtAngora3.addChild(this.rabbitRightThigh);

        this.rabbitButtAngora4 = new ModelRenderer(this, 30, 0);
        this.rabbitButtAngora4.addBox(-4.0F, 0.0F, 0.0F, 8, 8, 8, 4F);
        this.rabbitButtAngora4.setRotationPoint(0.0F, 13.0F, 2.5F);
        this.rabbitButtAngora4.addChild(this.rabbitLeftThigh);
        this.rabbitButtAngora4.addChild(this.rabbitRightThigh);

        this.rabbitLeftArm = new ModelRenderer(this, 16, 56);
        this.rabbitLeftArm.addBox(0.0F, 0.0F, 0.0F, 3, 6, 2);
        this.rabbitLeftArm.setRotationPoint(-3.5F, 23.5F, -2.0F + xMove);
        this.setRotationOffset(this.rabbitLeftArm, -1.6F, 0.0F, 0.0F);

        this.rabbitRightArm = new ModelRenderer(this, 26, 56);
        this.rabbitRightArm.addBox(0.0F, 0F, 0F, 3, 6, 2);
        this.rabbitRightArm.setRotationPoint(0.5F, 23.5F, -2.0F + xMove);
        this.setRotationOffset(this.rabbitRightArm, -1.6F, 0.0F, 0.0F);

        this.rabbitHeadLeft = new ModelRenderer(this, 0, 24);
        this.rabbitHeadLeft.addBox(0.0F, 0.0F, 0.0F, 3, 6, 6);
        this.rabbitHeadLeft.setRotationPoint(0.0F, 14.0F, -11.0F);

        this.rabbitHeadRight = new ModelRenderer(this, 18, 24);
        this.rabbitHeadRight.addBox(-3.0F, 0F, 0F, 3, 6, 6);
        this.rabbitHeadRight.setRotationPoint(0.0F, 14.0F, -9.0F + xMove);

        this.rabbitHeadMuzzle = new ModelRenderer(this, 0, 8);
        this.rabbitHeadMuzzle.addBox(-2F, 1.5F, -2F, 4, 4, 4);
        this.rabbitHeadMuzzle.setRotationPoint(0.0F, 0.0F, 0.0F + xMove);

        this.rabbitNose = new ModelRenderer(this, 0, 8);
        this.rabbitNose.addBox(-0.5F, 1.6F, -2.1F, 1, 1, 1);

        this.rabbitHeadMuzzleDwarf = new ModelRenderer(this, 0, 8);
        this.rabbitHeadMuzzleDwarf.addBox(-2F, 1.5F, -1F, 4, 4, 4);
        this.rabbitHeadMuzzleDwarf.setRotationPoint(0.0F, 0.0F, 0.0F + xMove);

        this.rabbitNoseDwarf = new ModelRenderer(this, 0, 8);
        this.rabbitNoseDwarf.addBox(-0.5F, 1.6F, -1.1F, 1, 1, 1);

        this.rabbitLionHeadL = new ModelRenderer(this, 33, 30);
        this.rabbitLionHeadL.addBox(0.0F, 0.0F, 0.0F, 3, 6, 6, 0.5F);

        this.rabbitLionHeadR = new ModelRenderer(this, 33, 18);
        this.rabbitLionHeadR.addBox(-3.0F, 0.0F, 0.0F, 3, 6, 6, 0.5F);

        this.rabbitLionHeadL1 = new ModelRenderer(this, 33, 30);
        this.rabbitLionHeadL1.addBox(0.0F, 0.0F, 0.0F, 3, 6, 6, 1.1F);

        this.rabbitLionHeadR1 = new ModelRenderer(this, 33, 18);
        this.rabbitLionHeadR1.addBox(-3.0F, 0.0F, 0.0F, 3, 6, 6, 1.1F);

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

        this.rabbitTail = new ModelRenderer(this, 20, 0);
        this.rabbitTail.addBox(-1.5F, 2.0F, 8.0F, 3, 4, 2);
        this.rabbitTail.setRotationPoint(0.0F, 0.0F, 0.0F + xMove);
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
        RabbitModelData rabbitModelData = getRabbitModelData();

        int[] genes = rabbitModelData.rabbitGenes;
        int coatLength = rabbitModelData.coatlength;

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
            rabbitModelData.dwarf = true;
            size = 0.3F + ((size - 0.3F)/2F);
        }else{
            rabbitModelData.dwarf = false;
        }

        float age = 1.0F;
        if (!(rabbitModelData.birthTime == null) && !rabbitModelData.birthTime.equals("") && !rabbitModelData.birthTime.equals("0")) {
            int ageTime = (int)(rabbitModelData.clientGameTime - Long.parseLong(rabbitModelData.birthTime));
            if (ageTime <= 50000) {
                age = ageTime/50000.0F;
            }
        }

        float finalRabbitSize = (( 3.0F * size * age) + size) / 4.0F;

        matrixStackIn.push();
        matrixStackIn.scale(finalRabbitSize, finalRabbitSize, finalRabbitSize);
        matrixStackIn.translate(0.0F, -1.45F + 1.45F/finalRabbitSize, 0.0F);
            this.rabbitHeadLeft.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            this.rabbitHeadRight.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            if (genes[24] == 2 && genes[25] == 2){
                this.rabbitLionHeadL1.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                this.rabbitLionHeadR1.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                this.LionEarParent1.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            }else if (genes[24] == 2 || genes[25] == 2){
                this.rabbitLionHeadL.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                this.rabbitLionHeadR.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                this.LionEarParent.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            }
            if (rabbitModelData.dwarf){
                this.rabbitHeadMuzzleDwarf.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                this.rabbitNoseDwarf.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                this.DwarfParent.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            }else{
                this.rabbitHeadMuzzle.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                this.rabbitNose.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                this.EarParent.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            }

            if (coatLength == 0){
                this.rabbitBody.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                //this.rabbitButtRound.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                this.rabbitButt.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                //this.rabbitButtTube.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            }else{
                if(coatLength == 1){
                    this.rabbitBodyAngora1.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                    //this.rabbitButtRoundAngora1.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                    this.rabbitButtAngora1.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                    //this.rabbitButtTubeAngora1.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                }else if(coatLength == 2){
                    this.rabbitBodyAngora2.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                    //this.rabbitButtRoundAngora2.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                    this.rabbitButtAngora2.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                    //this.rabbitButtTubeAngora2.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                }else if(coatLength == 3){
                    this.rabbitBodyAngora3.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                    //this.rabbitButtRoundAngora3.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                    this.rabbitButtAngora3.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                    //this.rabbitButtTubeAngora3.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                }else if(coatLength == 4){
                    this.rabbitBodyAngora4.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                    //this.rabbitButtRoundAngora4.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                    this.rabbitButtAngora4.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                    //this.rabbitButtTubeAngora4.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                }
            }

            this.rabbitLeftArm.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            this.rabbitRightArm.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            this.rabbitTail.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
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
        this.jumpRotation = MathHelper.sin(((EnhancedRabbit)entitylivingbaseIn).getJumpCompletion(partialTickTime) * (float)Math.PI);
        RabbitModelData rabbitModelData = getCreateRabbitModelData(entitylivingbaseIn);
        this.currentRabbit = entitylivingbaseIn.getEntityId();
    }

    /**
     * Sets the model's various rotation angles. For bipeds, par1 and par2 are used for animating the movement of arms
     * and legs, where par1 represents the time(so that arms and legs swing back and forth) and par2 represents how
     * "far" arms and legs can swing at most.
     */
    @Override
    public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        float f = ageInTicks - (float)entityIn.ticksExisted;

        RabbitModelData rabbitModelData = getRabbitModelData();

        int[] genes = rabbitModelData.rabbitGenes;
        boolean dwarf = rabbitModelData.dwarf;

        this.rabbitHeadLeft.rotateAngleX = headPitch * 0.017453292F;
        this.rabbitHeadLeft.rotateAngleY = netHeadYaw * 0.017453292F;
        copyModelAngles(rabbitHeadLeft, rabbitHeadRight);
        copyModelAngles(rabbitHeadLeft, rabbitHeadMuzzle);
        copyModelAngles(rabbitHeadLeft, rabbitHeadMuzzleDwarf);
        copyModelAngles(rabbitHeadLeft, rabbitLionHeadL);
        copyModelAngles(rabbitHeadRight, rabbitLionHeadR);
        copyModelAngles(rabbitHeadLeft, rabbitLionHeadL1);
        copyModelAngles(rabbitHeadRight, rabbitLionHeadR1);
        copyModelAngles(rabbitHeadLeft, rabbitNose);

        copyModelAngles(rabbitHeadLeft, EarParent);
        copyModelAngles(rabbitHeadLeft, LionEarParent);
        copyModelAngles(rabbitHeadLeft, LionEarParent1);
        copyModelAngles(rabbitHeadLeft, DwarfParent);

//        this.rabbitNose.rotateAngleY = netHeadYaw * 0.017453292F;
//        this.EarL.rotateAngleY = Math.abs(this.rabbitNose.rotateAngleY) * 1.0F;
//        this.EarR.rotateAngleY = -Math.abs(this.rabbitNose.rotateAngleY) * 1.0F;

        float lop = 0;

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

        if (lop >= 0.75F) {
            lop = 1.0F;
        } else {
            lop = 0;
        }

        float lookMod = Math.abs(this.rabbitNose.rotateAngleY);

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
            this.rabbitLeftCalf.rotateAngleX = 2.1F;
            this.rabbitRightCalf.rotateAngleX = 2.1F;
            this.rabbitLeftArm.rotateAngleX = -1.6F;
            this.rabbitRightArm.rotateAngleX = -1.6F;
            this.rabbitBody.rotateAngleX = 0.0F;
            this.EarParent.rotateAngleX = this.rabbitHeadLeft.rotateAngleX + 0.4F;
            this.LionEarParent.rotateAngleX = this.rabbitHeadLeft.rotateAngleX + 0.4F;
            this.LionEarParent1.rotateAngleX = this.rabbitHeadLeft.rotateAngleX + 0.4F;
            this.DwarfParent.rotateAngleX = this.rabbitHeadLeft.rotateAngleX + 0.4F;
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
            this.rabbitLeftCalf.rotateAngleX = 2.1F - (this.jumpRotation * +50.0F * ((float)Math.PI / 180F));
            this.rabbitRightCalf.rotateAngleX = 2.1F - (this.jumpRotation * +50.0F * ((float)Math.PI / 180F));
            this.rabbitLeftArm.rotateAngleX = -1.6F - (this.jumpRotation * -40.0F - 11.0F) * ((float)Math.PI / 180F);
            this.rabbitRightArm.rotateAngleX = -1.6F - (this.jumpRotation * -40.0F - 11.0F) * ((float)Math.PI / 180F);
            this.rabbitButt.rotateAngleX = (this.jumpRotation * +30.0F) * ((float)Math.PI / 180F);
            this.rabbitButtAngora1.rotateAngleX = (this.jumpRotation * +30.0F) * ((float)Math.PI / 180F);
            this.rabbitButtAngora2.rotateAngleX = (this.jumpRotation * +30.0F) * ((float)Math.PI / 180F);
            this.rabbitButtAngora3.rotateAngleX = (this.jumpRotation * +30.0F) * ((float)Math.PI / 180F);
            this.rabbitButtAngora4.rotateAngleX = (this.jumpRotation * +30.0F) * ((float)Math.PI / 180F);
            this.rabbitBody.rotateAngleX = (this.jumpRotation * +15.0F) * ((float)Math.PI / 180F);
            this.EarParent.rotateAngleX = ((this.jumpRotation * +15.0F) * ((float)Math.PI / 180F)) + this.rabbitHeadLeft.rotateAngleX + 0.4F;
            this.LionEarParent.rotateAngleX = ((this.jumpRotation * +15.0F) * ((float)Math.PI / 180F)) + this.rabbitHeadLeft.rotateAngleX + 0.4F;
            this.LionEarParent.rotateAngleX = ((this.jumpRotation * +15.0F) * ((float)Math.PI / 180F)) + this.rabbitHeadLeft.rotateAngleX + 0.4F;
            this.DwarfParent.rotateAngleX = ((this.jumpRotation * +15.0F) * ((float)Math.PI / 180F)) + this.rabbitHeadLeft.rotateAngleX + 0.4F;
            this.rabbitBodyAngora1.rotateAngleX = (this.jumpRotation * +15.0F) * ((float)Math.PI / 180F);
            this.rabbitBodyAngora2.rotateAngleX = (this.jumpRotation * +15.0F) * ((float)Math.PI / 180F);
            this.rabbitBodyAngora3.rotateAngleX = (this.jumpRotation * +15.0F) * ((float)Math.PI / 180F);
            this.rabbitBodyAngora4.rotateAngleX = (this.jumpRotation * +15.0F) * ((float)Math.PI / 180F);
        }
        this.rabbitLeftThigh.rotateAngleX = (this.jumpRotation * 50.0F - 21.0F) * ((float)Math.PI / 180F);
        this.rabbitRightThigh.rotateAngleX = (this.jumpRotation * 50.0F - 21.0F) * ((float)Math.PI / 180F);

        copyModelAngles(rabbitButt, rabbitTail);

//        if (ageInTicks+(entityIn.getEntityId()*7)%20 == 0) {
//            if (entityIn.noseTwitch != 1) {
//                entityIn.noseTwitch = 1;
//            } else {
//                entityIn.noseTwitch = -1;
//            }
//        }

        this.rabbitNose.rotationPointY = 14.0F + entityIn.noseTwitch;
        copyModelAngles(rabbitNose, rabbitNoseDwarf);
    }

    public static void copyModelAngles(ModelRenderer source, ModelRenderer dest) {
        dest.rotateAngleX = source.rotateAngleX;
        dest.rotateAngleY = source.rotateAngleY;
        dest.rotateAngleZ = source.rotateAngleZ;
        dest.rotationPointX = source.rotationPointX;
        dest.rotationPointY = source.rotationPointY;
        dest.rotationPointZ = source.rotationPointZ;
    }

    private class RabbitModelData {
        float previousSwing;
        int[] rabbitGenes;
        String birthTime;
        int coatlength;
        boolean dwarf;
        boolean sleeping;
        int lastAccessed = 0;
        int dataReset = 0;
        long clientGameTime = 0;
    }

    private RabbitModelData getRabbitModelData() {
        if (this.currentRabbit == null) {
            return null;
        }
        RabbitModelData cowModelData = rabbitModelDataCache.get(this.currentRabbit);
        return cowModelData;
    }

    private RabbitModelData getCreateRabbitModelData(T enhancedRabbit) {
        clearCacheTimer++;
        if(clearCacheTimer > 100000) {
            rabbitModelDataCache.values().removeIf(value -> value.lastAccessed==1);
            for (RabbitModelData rabbitModelData : rabbitModelDataCache.values()){
                rabbitModelData.lastAccessed = 1;
            }
            clearCacheTimer = 0;
        }

        if (rabbitModelDataCache.containsKey(enhancedRabbit.getEntityId())) {
            RabbitModelData rabbitModelData = rabbitModelDataCache.get(enhancedRabbit.getEntityId());
            rabbitModelData.lastAccessed = 0;
            rabbitModelData.dataReset++;
            if (rabbitModelData.dataReset > 5000) {
                rabbitModelData.coatlength = enhancedRabbit.getCoatLength();
                rabbitModelData.dataReset = 0;
            }
            rabbitModelData.sleeping = enhancedRabbit.isAnimalSleeping();
            rabbitModelData.clientGameTime = (((WorldInfo)((ClientWorld)enhancedRabbit.world).getWorldInfo()).getGameTime());

            return rabbitModelData;
        } else {
            RabbitModelData rabbitModelData = new RabbitModelData();
            rabbitModelData.rabbitGenes = enhancedRabbit.getSharedGenes();
            rabbitModelData.coatlength = enhancedRabbit.getCoatLength();
            rabbitModelData.sleeping = enhancedRabbit.isAnimalSleeping();
            rabbitModelData.birthTime = enhancedRabbit.getBirthTime();
            rabbitModelData.clientGameTime = (((WorldInfo)((ClientWorld)enhancedRabbit.world).getWorldInfo()).getGameTime());

            rabbitModelDataCache.put(enhancedRabbit.getEntityId(), rabbitModelData);

            return rabbitModelData;
        }
    }

}
