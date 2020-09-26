package mokiyoki.enhancedanimals.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import mokiyoki.enhancedanimals.entity.EnhancedRabbit;
import mokiyoki.enhancedanimals.items.CustomizableCollar;
import mokiyoki.enhancedanimals.model.util.ModelHelper;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.inventory.Inventory;
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
    private final ModelRenderer buttAngora4;
    private final ModelRenderer rabbitButtTube;
    private final ModelRenderer leftArm;
    private final ModelRenderer rightArm;
    private final ModelRenderer headLeft;
    private final ModelRenderer headRight;
    private final ModelRenderer eyeLeft;
    private final ModelRenderer eyeRight;
    private final ModelRenderer rabbitHeadMuzzle;
    private final ModelRenderer rabbitNose;
    private final ModelRenderer rabbitHeadMuzzleDwarf;
    private final ModelRenderer rabbitNoseDwarf;
    private final ModelRenderer rabbitLionHeadL;
    private final ModelRenderer rabbitLionHeadR;
    private final ModelRenderer rabbitLionHeadL1;
    private final ModelRenderer rabbitLionHeadR1;
    private final ModelRenderer earHelper;
    private final ModelRenderer lionEarL;
    private final ModelRenderer lionEarR;
    private final ModelRenderer lionEarL1;
    private final ModelRenderer lionEarR1;
    private final ModelRenderer earL;
    private final ModelRenderer earR;
    private final ModelRenderer dwarfEarL;
    private final ModelRenderer dwarfEarR;
    private final ModelRenderer rabbitTail;
    private final ModelRenderer collar;
    private float jumpRotation;

    private Integer currentRabbit = null;

    public ModelEnhancedRabbit()
    {
        this.textureWidth = 128;
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

        this.buttAngora4 = new ModelRenderer(this, 30, 0);
        this.buttAngora4.addBox(-4.0F, 0.0F, 0.0F, 8, 8, 8, 4F);
        this.buttAngora4.setRotationPoint(0.0F, 13.0F, 2.5F);
        this.buttAngora4.addChild(this.rabbitLeftThigh);
        this.buttAngora4.addChild(this.rabbitRightThigh);

        this.leftArm = new ModelRenderer(this, 16, 56);
        this.leftArm.addBox(0.0F, 0.0F, 0.0F, 3, 6, 2);
        this.leftArm.setRotationPoint(-3.5F, 23.5F, -2.0F + xMove);
        this.setRotationOffset(this.leftArm, -1.6F, 0.0F, 0.0F);

        this.rightArm = new ModelRenderer(this, 26, 56);
        this.rightArm.addBox(0.0F, 0F, 0F, 3, 6, 2);
        this.rightArm.setRotationPoint(0.5F, 23.5F, -2.0F + xMove);
        this.setRotationOffset(this.rightArm, -1.6F, 0.0F, 0.0F);

        this.headLeft = new ModelRenderer(this, 0, 24);
        this.headLeft.addBox(0.0F, 0.0F, -6.0F, 3, 6, 6);
        this.headLeft.setRotationPoint(0.0F, 14.0F, -7.0F);

        this.headRight = new ModelRenderer(this, 18, 24);
        this.headRight.addBox(-3.0F, 0F, -6.0F, 3, 6, 6);
        this.headRight.setRotationPoint(0.0F, 14.0F, -7.0F);

        this.eyeLeft = new ModelRenderer(this, 0, 16);
        this.eyeLeft.addBox(2.0F, 2.0F, -4.0F, 1, 2, 2, 0.01F);

        this.eyeRight = new ModelRenderer(this, 0, 20);
        this.eyeRight.addBox(-3.0F, 2.0F, -4.0F, 1, 2, 2, 0.01F);

        this.rabbitHeadMuzzle = new ModelRenderer(this, 0, 8);
        this.rabbitHeadMuzzle.addBox(-2F, 1.5F, -8F, 4, 4, 4);
        this.rabbitHeadMuzzle.setRotationPoint(0.0F, 0.0F, 0.0F + xMove);

        this.rabbitNose = new ModelRenderer(this, 0, 8);
        this.rabbitNose.addBox(-0.5F, 1.6F, -8.1F, 1, 1, 1);

        this.rabbitHeadMuzzleDwarf = new ModelRenderer(this, 0, 8);
        this.rabbitHeadMuzzleDwarf.addBox(-2F, 1.5F, -7F, 4, 4, 4);
        this.rabbitHeadMuzzleDwarf.setRotationPoint(0.0F, 0.0F, 0.0F + xMove);

        this.rabbitNoseDwarf = new ModelRenderer(this, 0, 8);
        this.rabbitNoseDwarf.addBox(-0.5F, 1.6F, -7.1F, 1, 1, 1);

        this.rabbitLionHeadL = new ModelRenderer(this, 33, 30);
        this.rabbitLionHeadL.addBox(0.0F, 0.0F, -6.0F, 3, 6, 6, 0.5F);

        this.rabbitLionHeadR = new ModelRenderer(this, 33, 18);
        this.rabbitLionHeadR.addBox(-3.0F, 0.0F, -6.0F, 3, 6, 6, 0.5F);

        this.rabbitLionHeadL1 = new ModelRenderer(this, 33, 30);
        this.rabbitLionHeadL1.addBox(0.0F, 0.0F, -6.0F, 3, 6, 6, 1.1F);

        this.rabbitLionHeadR1 = new ModelRenderer(this, 33, 18);
        this.rabbitLionHeadR1.addBox(-3.0F, 0.0F, -6.0F, 3, 6, 6, 1.1F);

        this.earHelper = new ModelRenderer(this, 0, 0);
        this.earHelper.setRotationPoint(0.0F, 0.0F, -6.0F);

        this.lionEarL = new ModelRenderer(this, 50, 46);
        this.lionEarL.addBox(-3.0F, -7.0F, -0.5F, 4, 7, 1, 0.4F);
        this.lionEarR = new ModelRenderer(this, 40, 46);
        this.lionEarR.addBox(-1.0F, -7.0F, -0.5F, 4, 7, 1, 0.4F);

        this.lionEarL1 = new ModelRenderer(this, 50, 46);
        this.lionEarL1.addBox(-3.0F, -7.0F, -0.5F, 4, 7, 1, 0.5F);
        this.lionEarR1 = new ModelRenderer(this, 40, 46);
        this.lionEarR1.addBox(-1.0F, -7.0F, -0.5F, 4, 7, 1, 0.5F);

        this.earL = new ModelRenderer(this, 10, 0);
        this.earL.addBox(-3.0F, -7.0F, -0.5F, 4, 7, 1);
        this.earR = new ModelRenderer(this, 0, 0);
        this.earR.addBox(-1.0F, -7.0F, -0.5F, 4, 7, 1);

        this.dwarfEarL = new ModelRenderer(this, 10, 0);
        this.dwarfEarL.addBox(-3.0F, -5.0F, -0.5F, 4, 5, 1);
        this.dwarfEarR = new ModelRenderer(this, 0, 0);
        this.dwarfEarR.addBox(-1.0F, -5.0F, -0.5F, 4, 5, 1);

        this.rabbitTail = new ModelRenderer(this, 20, 0);
        this.rabbitTail.addBox(-1.5F, 2.0F, 8.0F, 3, 4, 2);
        this.rabbitTail.setRotationPoint(0.0F, 0.0F, 0.0F + xMove);

        this.headLeft.addChild(this.eyeLeft);
        this.headRight.addChild(this.eyeRight);
        this.headLeft.addChild(this.earHelper);

        this.earHelper.addChild(this.earL);
        this.earHelper.addChild(this.earR);
        this.earHelper.addChild(this.lionEarL);
        this.earHelper.addChild(this.lionEarR);
        this.earHelper.addChild(this.lionEarL1);
        this.earHelper.addChild(this.lionEarR1);
        this.earHelper.addChild(this.dwarfEarL);
        this.earHelper.addChild(this.dwarfEarR);

        this.collar = new ModelRenderer(this, 36, 55);
        this.collar.addBox(-3.5F, -1.0F, -0.5F, 7, 2, 7);
        this.collar.setTextureOffset(35, 51);
        this.collar.addBox(0.0F, -1.5F, 5.5F, 0,  3, 3);
        this.collar.setTextureOffset(12, 37);
        this.collar.addBox(-1.5F, -1.5F, 7.0F, 3, 3, 3, -0.5F);
        this.headLeft.addChild(this.collar);
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
                rabbitModelData.dwarf = true;
                size = 0.3F + ((size - 0.3F)/2F);
            }else{
                rabbitModelData.dwarf = false;
            }
        } else {
            rabbitModelData.dwarf = false;
        }


        float age = 1.0F;
        if (!(rabbitModelData.birthTime == null) && !rabbitModelData.birthTime.equals("") && !rabbitModelData.birthTime.equals("0")) {
            int ageTime = (int)(rabbitModelData.clientGameTime - Long.parseLong(rabbitModelData.birthTime));
            if (ageTime <= 50000) {
                age = ageTime < 0 ? 0 : ageTime/50000.0F;
            }
        }

        float finalRabbitSize = (( 3.0F * size * age) + size) / 4.0F;

        matrixStackIn.push();
        matrixStackIn.scale(finalRabbitSize, finalRabbitSize, finalRabbitSize);
        matrixStackIn.translate(0.0F, -1.45F + 1.45F/finalRabbitSize, 0.0F);
        if (rabbitModelData.blink >= 6) {
            this.eyeLeft.showModel = true;
            this.eyeRight.showModel = true;
        } else {
            this.eyeLeft.showModel = false;
            this.eyeRight.showModel = false;
        }

        this.earL.showModel = false;
        this.earR.showModel = false;
        this.lionEarL1.showModel = false;
        this.lionEarR1.showModel = false;
        this.lionEarL.showModel = false;
        this.lionEarR.showModel = false;
        this.dwarfEarL.showModel = false;
        this.dwarfEarR.showModel = false;

        if (genes != null) {
//            if (false) {
                if (genes[24] == 2 && genes[25] == 2){
                    this.lionEarL1.showModel = true;
                    this.lionEarR1.showModel = true;
                    this.rabbitLionHeadL1.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                    this.rabbitLionHeadR1.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                }else if (genes[24] == 2 || genes[25] == 2){
                    this.lionEarL.showModel = true;
                    this.lionEarR.showModel = true;
                    this.rabbitLionHeadL.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                    this.rabbitLionHeadR.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                }
                this.earL.showModel = true;
                this.earR.showModel = true;
                this.headLeft.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                this.headRight.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            }
            if (rabbitModelData.dwarf){
//            if (false){
                this.rabbitHeadMuzzleDwarf.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                this.rabbitNoseDwarf.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                this.dwarfEarL.showModel = true;
                this.dwarfEarR.showModel = true;
            }else{
                this.rabbitHeadMuzzle.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                this.rabbitNose.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                this.earL.showModel = true;
                this.earR.showModel = true;
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
                    this.buttAngora4.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                    //this.rabbitButtTubeAngora4.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                }
            }

            this.leftArm.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            this.rightArm.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
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

        this.headLeft.rotateAngleX = headPitch * 0.017453292F;
        this.headLeft.rotateAngleY = netHeadYaw * 0.017453292F;
        ModelHelper.copyModelPositioning(headLeft, headRight);
        ModelHelper.copyModelPositioning(headLeft, rabbitHeadMuzzle);
        ModelHelper.copyModelPositioning(headLeft, rabbitHeadMuzzleDwarf);
        ModelHelper.copyModelPositioning(headLeft, rabbitLionHeadL);
        ModelHelper.copyModelPositioning(headRight, rabbitLionHeadR);
        ModelHelper.copyModelPositioning(headLeft, rabbitLionHeadL1);
        ModelHelper.copyModelPositioning(headRight, rabbitLionHeadR1);
        ModelHelper.copyModelPositioning(headLeft, rabbitNose);

        this.collar.rotateAngleX = -(this.headLeft.rotateAngleX/2.0F) - ((float)Math.PI/2.0F);
        this.collar.rotateAngleY = -(this.headLeft.rotateAngleY/2.0F);

//        this.rabbitNose.rotateAngleY = netHeadYaw * 0.017453292F;
//        this.earL.rotateAngleY = Math.abs(this.rabbitNose.rotateAngleY) * 1.0F;
//        this.earR.rotateAngleY = -Math.abs(this.rabbitNose.rotateAngleY) * 1.0F;

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

        this.earL.setRotationPoint(-earPointX, earPointY, earPointZ);
        this.earR.setRotationPoint(earPointX, earPointY, earPointZ);

        this.earL.rotateAngleX = (earRotateX*(1.0F-lop) + floppyEarRotateX*(-lop));
        this.earR.rotateAngleX = (earRotateX*(1.0F-lop) + floppyEarRotateX*(-lop));
        this.earL.rotateAngleY = (earRotateY*(1.0F-lop) + floppyEarRotateY*(lop));
        this.earR.rotateAngleY = (earRotateY*(1.0F-lop) + (floppyEarRotateY)*(-lop));
        this.earL.rotateAngleZ = (earRotateZ*(1.0F-lop) + floppyEarRotateZ*(lop));
        this.earR.rotateAngleZ = (-earRotateZ*(1.0F-lop) + floppyEarRotateZ*(lop));

        ModelHelper.copyModelPositioning(earL, lionEarL);
        ModelHelper.copyModelPositioning(earR, lionEarR);
        ModelHelper.copyModelPositioning(earL, lionEarL1);
        ModelHelper.copyModelPositioning(earR, lionEarR1);
        ModelHelper.copyModelPositioning(earL, dwarfEarL);
        ModelHelper.copyModelPositioning(earR, dwarfEarR);

        //changes some rotation angles
        this.rabbitButtRound.rotationPointZ = 2.5F;
        this.rabbitButt.rotationPointZ = 2.5F;
        this.rabbitButtTube.rotationPointZ = 2.5F;

        if(dwarf) {
            this.rabbitButtRound.rotationPointZ = 0.5F;
            this.rabbitButt.rotationPointZ = 0.5F;
            this.rabbitButtTube.rotationPointZ = 0.5F;
        }

        this.jumpRotation = MathHelper.sin(((EnhancedRabbit)entityIn).getJumpCompletion(f) * (float)Math.PI);
        if (this.jumpRotation == 0.0F) {
            this.rabbitLeftFoot.rotateAngleX = 3.0F;
            this.rabbitRightFoot.rotateAngleX = 3.0F;
            this.rabbitLeftCalf.rotateAngleX = 2.1F;
            this.rabbitRightCalf.rotateAngleX = 2.1F;
            this.leftArm.rotateAngleX = -1.6F;
            this.rightArm.rotateAngleX = -1.6F;
            this.rabbitBody.rotateAngleX = 0.0F;
            this.earHelper.rotateAngleX = this.headLeft.rotateAngleX + 0.4F;
//            this.LionEarParent.rotateAngleX = this.headLeft.rotateAngleX + 0.4F;
//            this.LionEarParent1.rotateAngleX = this.headLeft.rotateAngleX + 0.4F;
//            this.DwarfParent.rotateAngleX = this.headLeft.rotateAngleX + 0.4F;
            this.rabbitBodyAngora1.rotateAngleX = 0.0F;
            this.rabbitBodyAngora2.rotateAngleX = 0.0F;
            this.rabbitBodyAngora3.rotateAngleX = 0.0F;
            this.rabbitBodyAngora4.rotateAngleX = 0.0F;
            this.rabbitButtRound.rotateAngleX = 0.0F;
            this.rabbitButt.rotateAngleX = 0.0F;
            this.rabbitButtAngora1.rotateAngleX = 0.0F;
            this.rabbitButtAngora2.rotateAngleX = 0.0F;
            this.rabbitButtAngora3.rotateAngleX = 0.0F;
            this.buttAngora4.rotateAngleX = 0.0F;
            this.rabbitButtTube.rotateAngleX = 0.0F;
        } else {
            this.rabbitLeftFoot.rotateAngleX = 3.0F + this.jumpRotation * 80.0F * ((float)Math.PI / 180F);
            this.rabbitRightFoot.rotateAngleX = 3.0F + this.jumpRotation * 80.0F * ((float)Math.PI / 180F);
            this.rabbitLeftCalf.rotateAngleX = 2.1F - (this.jumpRotation * +50.0F * ((float)Math.PI / 180F));
            this.rabbitRightCalf.rotateAngleX = 2.1F - (this.jumpRotation * +50.0F * ((float)Math.PI / 180F));
            this.leftArm.rotateAngleX = -1.6F - (this.jumpRotation * -40.0F - 11.0F) * ((float)Math.PI / 180F);
            this.rightArm.rotateAngleX = -1.6F - (this.jumpRotation * -40.0F - 11.0F) * ((float)Math.PI / 180F);
            this.rabbitButt.rotateAngleX = (this.jumpRotation * +30.0F) * ((float)Math.PI / 180F);
            this.rabbitButtAngora1.rotateAngleX = (this.jumpRotation * +30.0F) * ((float)Math.PI / 180F);
            this.rabbitButtAngora2.rotateAngleX = (this.jumpRotation * +30.0F) * ((float)Math.PI / 180F);
            this.rabbitButtAngora3.rotateAngleX = (this.jumpRotation * +30.0F) * ((float)Math.PI / 180F);
            this.buttAngora4.rotateAngleX = (this.jumpRotation * +30.0F) * ((float)Math.PI / 180F);
            this.rabbitBody.rotateAngleX = (this.jumpRotation * +15.0F) * ((float)Math.PI / 180F);
            this.earHelper.rotateAngleX = ((this.jumpRotation * +15.0F) * ((float)Math.PI / 180F)) + this.headLeft.rotateAngleX + 0.4F;
//            this.LionEarParent.rotateAngleX = ((this.jumpRotation * +15.0F) * ((float)Math.PI / 180F)) + this.headLeft.rotateAngleX + 0.4F;
//            this.LionEarParent.rotateAngleX = ((this.jumpRotation * +15.0F) * ((float)Math.PI / 180F)) + this.headLeft.rotateAngleX + 0.4F;
//            this.DwarfParent.rotateAngleX = ((this.jumpRotation * +15.0F) * ((float)Math.PI / 180F)) + this.headLeft.rotateAngleX + 0.4F;
            this.rabbitBodyAngora1.rotateAngleX = (this.jumpRotation * +15.0F) * ((float)Math.PI / 180F);
            this.rabbitBodyAngora2.rotateAngleX = (this.jumpRotation * +15.0F) * ((float)Math.PI / 180F);
            this.rabbitBodyAngora3.rotateAngleX = (this.jumpRotation * +15.0F) * ((float)Math.PI / 180F);
            this.rabbitBodyAngora4.rotateAngleX = (this.jumpRotation * +15.0F) * ((float)Math.PI / 180F);
        }
        this.rabbitLeftThigh.rotateAngleX = (this.jumpRotation * 50.0F - 21.0F) * ((float)Math.PI / 180F);
        this.rabbitRightThigh.rotateAngleX = (this.jumpRotation * 50.0F - 21.0F) * ((float)Math.PI / 180F);

        ModelHelper.copyModelPositioning(rabbitButt, rabbitTail);

        this.rabbitNose.rotationPointY = 14.2F + (float)entityIn.getNoseTwitch()/4.0F;

        ModelHelper.copyModelPositioning(rabbitNose, rabbitNoseDwarf);
    }

    private class RabbitModelData {
        int[] rabbitGenes;
        String birthTime;
        int coatlength = 0;
        boolean dwarf = false;
        boolean sleeping = false;
        int blink = 0;
        boolean collar = false;
        int lastAccessed = 0;
        int dataReset = 0;
        long clientGameTime = 0;
    }

    private RabbitModelData getRabbitModelData() {
        if (this.currentRabbit == null || !rabbitModelDataCache.containsKey(this.currentRabbit)) {
            return new RabbitModelData();
        }
        return rabbitModelDataCache.get(this.currentRabbit);
    }

    private RabbitModelData getCreateRabbitModelData(T enhancedRabbit) {
        clearCacheTimer++;
        if(clearCacheTimer > 50000) {
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
            rabbitModelData.collar = hasCollar(enhancedRabbit.getEnhancedInventory());
            rabbitModelData.blink = enhancedRabbit.getBlink();
            rabbitModelData.birthTime = enhancedRabbit.getBirthTime();
            rabbitModelData.clientGameTime = (((WorldInfo)((ClientWorld)enhancedRabbit.world).getWorldInfo()).getGameTime());

            return rabbitModelData;
        } else {
            RabbitModelData rabbitModelData = new RabbitModelData();
            if (enhancedRabbit.getSharedGenes()!=null) {
                rabbitModelData.rabbitGenes = enhancedRabbit.getSharedGenes().getAutosomalGenes();
            }
            rabbitModelData.coatlength = enhancedRabbit.getCoatLength();
            rabbitModelData.sleeping = enhancedRabbit.isAnimalSleeping();
            rabbitModelData.blink = enhancedRabbit.getBlink();
            rabbitModelData.birthTime = enhancedRabbit.getBirthTime();
            rabbitModelData.collar = hasCollar(enhancedRabbit.getEnhancedInventory());
            rabbitModelData.clientGameTime = (((WorldInfo)((ClientWorld)enhancedRabbit.world).getWorldInfo()).getGameTime());

            if(rabbitModelData.rabbitGenes != null) {
                rabbitModelDataCache.put(enhancedRabbit.getEntityId(), rabbitModelData);
            }

            return rabbitModelData;
        }
    }

    private boolean hasCollar(Inventory inventory) {
        for (int i = 1; i < 6; i++) {
            if (inventory.getStackInSlot(i).getItem() instanceof CustomizableCollar) {
                return true;
            }
        }
        return false;
    }
}
