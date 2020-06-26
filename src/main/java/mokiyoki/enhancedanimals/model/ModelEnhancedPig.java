package mokiyoki.enhancedanimals.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import mokiyoki.enhancedanimals.entity.EnhancedPig;
import mokiyoki.enhancedanimals.model.util.ModelHelper;
import mokiyoki.enhancedanimals.renderer.EnhancedRendererModelNew;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.storage.WorldInfo;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@OnlyIn(Dist.CLIENT)
public class ModelEnhancedPig <T extends EnhancedPig> extends EntityModel<T> {

    String saddleType = "vanilla";

    private Map<Integer, PigModelData> pigModelDataCache = new HashMap<>();
    private int clearCacheTimer = 0;
    private float headRotationAngleX;

    private final EnhancedRendererModelNew pig;
    private final EnhancedRendererModelNew head;
    private final EnhancedRendererModelNew cheeks;
    private final EnhancedRendererModelNew snout;
    private final EnhancedRendererModelNew mouth;
    private final EnhancedRendererModelNew tuskTL;
    private final EnhancedRendererModelNew tuskTR;
    private final EnhancedRendererModelNew tuskBL;
    private final EnhancedRendererModelNew tuskBR;
    private final EnhancedRendererModelNew earSmallL;
    private final EnhancedRendererModelNew earSmallR;
    private final EnhancedRendererModelNew earMediumL;
    private final EnhancedRendererModelNew earMediumR;
    private final EnhancedRendererModelNew neck;
    private final EnhancedRendererModelNew neckBigger;
    private final EnhancedRendererModelNew body;
    private final EnhancedRendererModelNew butt;
    private final EnhancedRendererModelNew tail0;
    private final EnhancedRendererModelNew tail1;
    private final EnhancedRendererModelNew tail2;
    private final EnhancedRendererModelNew tail3;
    private final EnhancedRendererModelNew tail4;
    private final EnhancedRendererModelNew tail5;
    private final EnhancedRendererModelNew leg1;
    private final EnhancedRendererModelNew leg2;
    private final EnhancedRendererModelNew leg3;
    private final EnhancedRendererModelNew leg4;
    private final EnhancedRendererModelNew eyeLeft;
    private final EnhancedRendererModelNew eyeRight;

    private final EnhancedRendererModelNew saddle;
    private final EnhancedRendererModelNew saddleWestern;
    private final EnhancedRendererModelNew saddleEnglish;
    private final EnhancedRendererModelNew saddleHorn;
    private final EnhancedRendererModelNew saddlePomel;
    private final EnhancedRendererModelNew saddleSideL;
    private final EnhancedRendererModelNew stirrup2DWideL;
    private final EnhancedRendererModelNew stirrup2DWideR;
    private final EnhancedRendererModelNew stirrup3DNarrowL;
    private final EnhancedRendererModelNew stirrup3DNarrowR;
    private final EnhancedRendererModelNew stirrup;
    private final EnhancedRendererModelNew saddleSideR;
    private final EnhancedRendererModelNew saddlePad;

    private final List<EnhancedRendererModelNew> saddles = new ArrayList<>();

    private Integer currentPig = null;

    public ModelEnhancedPig() {

        this.textureWidth = 160;
        this.textureHeight = 80;

        this.pig = new EnhancedRendererModelNew(this, 49, 0);
        this.pig.setRotationPoint(0.0F, 0.0F, 0.0F);

        this.head = new EnhancedRendererModelNew(this, 49, 0);
        this.head.addBox(-3.5F, -5.0F, -4.0F, 7, 6, 7);
        this.head.setRotationPoint(0.0F, -5.0F, -4.0F);

        this.cheeks = new EnhancedRendererModelNew(this, 49, 13);
        this.cheeks.addBox(-4.0F, 0.0F, 0.0F, 8, 5, 4, 0.25F);
        this.cheeks.setRotationPoint(0.0F, -5.5F, -4.0F);

        this.snout = new EnhancedRendererModelNew(this, 49, 22);
        this.snout.addBox(-2.0F, -5.0F, -3.0F, 4, 7, 3);
        this.snout.setRotationPoint(0.0F, -5.0F, 0.0F);

        this.mouth = new EnhancedRendererModelNew(this, 63, 22);
        this.mouth.addBox(-1.0F, -5.0F, 0.0F, 2, 6, 1);
        this.mouth.setRotationPoint(0.0F, 1.0F, -4.0F);

        this.tuskTL = new EnhancedRendererModelNew(this, 69, 22);
        this.tuskTL.addBox(-0.5F, -2.0F, -0.5F, 1, 2, 1, -0.1F);
        this.tuskTL.setRotationPoint(0.5F, 0.0F, -2.0F);

        this.tuskTR = new EnhancedRendererModelNew(this, 69, 22);
        this.tuskTR.addBox(-0.5F, -2.0F, -0.5F, 1, 2, 1, -0.1F);
        this.tuskTR.setRotationPoint(-0.5F, 0.0F, -2.0F);

        this.tuskBL = new EnhancedRendererModelNew(this, 69, 22);
        this.tuskBL.addBox(-0.5F, -2.0F, -0.5F, 1, 2, 1, -0.1F);
        this.tuskBL.addBox(-0.5F, -2.8F, -0.5F, 1, 1, 2, -0.1F);
        this.tuskBL.setRotationPoint(0.5F, 0.0F, 1F);

        this.tuskBR = new EnhancedRendererModelNew(this, 69, 22);
        this.tuskBR.addBox(-0.5F, -2.0F, -0.5F, 1, 2, 1, -0.1F);
        this.tuskBR.addBox(-0.5F, -2.8F, -0.5F, 1, 1, 2, -0.1F);
        this.tuskBR.setRotationPoint(-0.5F, 0.0F, 1F);

        this.earSmallL = new EnhancedRendererModelNew(this, 46, 0);
        this.earSmallL.addBox(-1.0F, -2.0F, 0.0F, 3, 4, 1);
        this.earSmallL.setRotationPoint(3.5F, -4.0F, 3.0F);

        this.earSmallR = new EnhancedRendererModelNew(this, 70, 0);
        this.earSmallR.addBox(-2.0F, -2.0F, 0.0F, 3, 4, 1);
        this.earSmallR.setRotationPoint(-3.5F, -4.0F, 3.0F);

        this.earMediumL = new EnhancedRendererModelNew(this, 46, 0);
        this.earMediumL.addBox(-1.0F, -4.0F, 0.0F, 4, 6, 1);
        this.earMediumL.setRotationPoint(3.5F, -4.0F, 3.0F);

        this.earMediumR = new EnhancedRendererModelNew(this, 70, 0);
        this.earMediumR.addBox(-3.0F, -4.0F, 0.0F, 4, 6, 1);
        this.earMediumR.setRotationPoint(-3.0F, -4.0F, 3.0F);

        this.neck = new EnhancedRendererModelNew(this, 0, 0);
        this.neck.addBox(-4.5F, -6.75F, -9.0F, 9, 7, 9);
        this.neck.setRotationPoint(0.0F, 0.0F, 9.1F);

        this.neckBigger = new EnhancedRendererModelNew(this, 0, 0);
        this.neckBigger.addBox(-4.5F, -5.75F, -9.0F, 9, 7, 9, 1.0F);
        this.neckBigger.setRotationPoint(0.0F, 0.0F, 9.1F);

        this.body = new EnhancedRendererModelNew(this, 0, 23);
        this.body.addBox(-5.0F, 0.0F, 0.0F, 10, 11, 10);
        this.body.setRotationPoint(0.0F, 18.1F, -4.0F);

        this.butt = new EnhancedRendererModelNew(this, 0, 53);
        this.butt.addBox(-4.5F, 0.0F, 0.0F, 9, 5, 9);
        this.butt.setRotationPoint(0.0F, 18.0F, 5.5F);

        this.tail0 = new EnhancedRendererModelNew(this, 36, 0);
        this.tail0.addBox(-0.5F, 0.0F, 0.0F, 1, 2, 1, -0.05F);
        this.tail0.setRotationPoint(0.0F, 5.0F, 7.5F);

        this.tail1 = new EnhancedRendererModelNew(this, 36, 0);
        this.tail1.addBox(-0.5F, 0.0F, -0.5F, 1, 2, 1, -0.1F);
        this.tail1.setRotationPoint(0.0F, 1.9F, 0.5F);

        this.tail2 = new EnhancedRendererModelNew(this, 36, 3);
        this.tail2.addBox(-0.5F, 0.0F, -0.5F, 1, 2, 1, -0.15F);
        this.tail2.setRotationPoint(0.0F, 1.8F, 0.0F);

        this.tail3 = new EnhancedRendererModelNew(this, 36, 6);
        this.tail3.addBox(-0.5F, 0.0F, -0.5F, 1, 2, 1, -0.2F);
        this.tail3.setRotationPoint(0.0F, 1.7F, 0.0F);

        this.tail4 = new EnhancedRendererModelNew(this, 36, 9);
        this.tail4.addBox(-0.5F, 0.0F, -0.5F, 1, 2, 1, -0.25F);
        this.tail4.setRotationPoint(0.0F, 1.6F, 0.0F);

        this.tail5 = new EnhancedRendererModelNew(this, 36, 12);
        this.tail5.addBox(-0.5F, 0.0F, -0.5F, 1, 2, 1, -0.3F);
        this.tail5.setRotationPoint(0.0F, 1.5F, 0.0F);

        this.leg1 = new EnhancedRendererModelNew(this, 49, 32);
        this.leg1.addBox(-3.0F, 0.0F, 0.0F, 3, 8, 3);
        this.leg1.setRotationPoint(-2.0F, 16.0F, -7.0F);

        this.leg2 = new EnhancedRendererModelNew(this, 61, 32);
        this.leg2.addBox(0.0F, 0.0F, 0.0F, 3, 8, 3);
        this.leg2.setRotationPoint(2.0F, 16.0F, -7.0F);

        this.leg3 = new EnhancedRendererModelNew(this, 49, 44);
        this.leg3.addBox(-3.0F, 0.0F, 0.0F, 3, 8, 3);
        this.leg3.setRotationPoint(-2.0F, 16.0F, 7.0F);

        this.leg4 = new EnhancedRendererModelNew(this, 61, 44);
        this.leg4.addBox(0.0F, 0.0F, 0.0F, 3, 8, 3);
        this.leg4.setRotationPoint(2.0F, 16.0F, 7.0F);

        this.eyeLeft = new EnhancedRendererModelNew(this, 69, 15);
        this.eyeLeft.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1, 0.01F);
        this.eyeLeft.setRotationPoint(2.5F, -5.0F, 0.0F);

        this.eyeRight = new EnhancedRendererModelNew(this, 49, 15);
        this.eyeRight.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1, 0.01F);
        this.eyeRight.setRotationPoint(-3.5F, -5.0F, 0.0F);

        this.body.addChild(this.neckBigger);
//        this.neck.addChild(this.head);
        this.neckBigger.addChild(this.head);
        this.head.addChild(this.cheeks);
        this.head.addChild(this.snout);
        this.snout.addChild(this.tuskTL);
        this.snout.addChild(this.tuskTR);
        this.snout.addChild(this.mouth);
        this.mouth.addChild(this.tuskBL);
        this.mouth.addChild(this.tuskBR);
//        this.head.addChild(this.earSmallL);
//        this.head.addChild(this.earSmallR);
        this.head.addChild(this.earMediumL);
        this.head.addChild(this.earMediumR);
        this.head.addChild(this.eyeLeft);
        this.head.addChild(this.eyeRight);
        this.butt.addChild(this.tail0);
        this.tail0.addChild(this.tail1);
        this.tail1.addChild(this.tail2);
        this.tail2.addChild(this.tail3);
        this.tail3.addChild(this.tail4);
        this.tail4.addChild(this.tail5);

        this.pig.addChild(this.body);
        this.pig.addChild(this.butt);
        this.pig.addChild(this.leg1);
        this.pig.addChild(this.leg2);
        this.pig.addChild(this.leg3);
        this.pig.addChild(this.leg4);

        /**
         * Equipment stuff
         */

        this.saddle = new EnhancedRendererModelNew(this, 98, 24, "Saddle");
        this.saddle.addBox(-8.0F, -1.0F, -6.0F, 16, 10, 15, -1.0F);

        this.saddleWestern = new EnhancedRendererModelNew(this, 114, 0, "WesternSaddle");
        this.saddleWestern.addBox(-5.0F, -2.0F, -5.0F, 10, 2, 13, 0.0F);
        this.saddleWestern.setTextureOffset(114, 15);
        this.saddleWestern.addBox(-4.0F, -3.0F, 5.0F, 8, 2, 4, 0.0F);
        this.saddleWestern.setTextureOffset(134, 15);
        this.saddleWestern.addBox(-3.5F, -4.0F, 8.0F, 7, 2, 2, 0.0F);

        this.saddleEnglish = new EnhancedRendererModelNew(this, 115, 1, "EnglishSaddle");
        this.saddleEnglish.addBox(-5.0F, -1.0F, -4.0F, 10, 2, 12, 0.0F);
        this.saddleEnglish.setTextureOffset(114, 15);
        this.saddleEnglish.addBox(-4.0F, -1.5F, 5.0F, 8, 2, 4, 0.0F);
        this.saddleEnglish.setTextureOffset(134, 15);
        this.saddleEnglish.addBox(-3.5F, -2.0F, 7.5F, 7, 2, 2, 0.0F);

        this.saddleHorn = new EnhancedRendererModelNew(this, 138, 19, "SaddleHorn");
        this.saddleHorn.addBox(-4.0F, -2.0F, -3.0F, 8, 2, 3, 0.0F);

        this.saddlePomel = new EnhancedRendererModelNew(this, 147, 0, "SaddlePomel");
        this.saddlePomel.addBox(-1.0F, -3.0F, -2.0F, 2, 4, 2, -0.25F);
        this.saddlePomel.setRotationPoint(0.0F, -2.0F, -2.0F);

        this.saddleSideL = new EnhancedRendererModelNew(this, 138, 49, "SaddleLeft");
        this.saddleSideL.addBox(0.0F, 0.0F, 0.0F, 3, 4, 8);

        this.saddleSideR = new EnhancedRendererModelNew(this, 138, 61, "SaddleRight");
        this.saddleSideR.addBox(-3.0F, 0.0F, 0.0F, 3, 4, 8);

        this.stirrup2DWideL = new EnhancedRendererModelNew(this, 152, 24, "2DStirrupL");
        this.stirrup2DWideL.addBox(0.0F, 0.0F, 0.0F, 0, 10, 4); // strap

        this.stirrup2DWideR = new EnhancedRendererModelNew(this, 152, 24, "2DStirrupR");
        this.stirrup2DWideR.addBox(0.0F, 0.0F, 0.0F, 0, 10, 4); // strap

        this.stirrup3DNarrowL = new EnhancedRendererModelNew(this, 153, 27, "3DStirrupL");
        this.stirrup3DNarrowL.addBox(-1.0F, 0.0F, 0.0F, 1, 10, 1); // strap

        this.stirrup3DNarrowR = new EnhancedRendererModelNew(this, 155, 27, "3DStirrupR");
        this.stirrup3DNarrowR.addBox(0.0F, 0.0F, 0.0F, 1, 10, 1);

        this.stirrup = new EnhancedRendererModelNew(this, 114, 0, "Stirrup");
        this.stirrup.addBox(-0.5F, 9.5F, -1.0F, 1, 1, 1);
        this.stirrup.setTextureOffset(118, 0);
        this.stirrup.addBox(-0.5F, 9.5F, 1.0F, 1, 1, 1);
        this.stirrup.setTextureOffset(114, 2);
        this.stirrup.addBox(-0.5F, 10.5F, -1.5F, 1, 3, 1);
        this.stirrup.setTextureOffset(118, 2);
        this.stirrup.addBox(-0.5F, 10.5F, 1.5F, 1, 3, 1);
        this.stirrup.setTextureOffset(115, 7);
        this.stirrup.addBox(-0.5F, 12.5F, -0.5F, 1, 1, 2);

        this.saddlePad = new EnhancedRendererModelNew(this, 98, 24, "SaddlePad");
        this.saddlePad.addBox(-8.0F, -1.0F, -6.0F, 16, 10, 15, -1.0F);

        this.saddleHorn.addChild(this.saddlePomel);

        //western
        this.body.addChild(this.saddleWestern);
        this.saddleWestern.addChild(this.saddleHorn);
        this.saddleWestern.addChild(this.saddleSideL);
        this.saddleWestern.addChild(this.saddleSideR);
        this.saddleWestern.addChild(this.saddlePad);
        this.saddleWestern.addChild(this.stirrup2DWideL);
        this.saddleWestern.addChild(this.stirrup2DWideR);
        this.stirrup2DWideL.addChild(this.stirrup);
        this.stirrup2DWideR.addChild(this.stirrup);
        //english
        this.body.addChild(this.saddleEnglish);
        this.saddleEnglish.addChild(this.saddleHorn);
        this.saddleEnglish.addChild(this.saddleSideL);
        this.saddleEnglish.addChild(this.saddleSideR);
        this.saddleEnglish.addChild(this.saddlePad);
        this.saddleEnglish.addChild(this.stirrup3DNarrowL);
        this.saddleEnglish.addChild(this.stirrup3DNarrowR);
        this.stirrup3DNarrowL.addChild(this.stirrup);
        this.stirrup3DNarrowR.addChild(this.stirrup);
        //vanilla
        this.body.addChild(this.saddle);
        this.saddle.addChild(this.stirrup3DNarrowL);
        this.saddle.addChild(this.stirrup3DNarrowR);
    }

    @Override
    public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {

        PigModelData pigModelData = getPigModelData();
        float size = pigModelData.size;
        int blink = pigModelData.blink;

        float age = 1.0F;
        if (!(pigModelData.birthTime == null) && !pigModelData.birthTime.equals("") && !pigModelData.birthTime.equals("0")) {
            int ageTime = (int)(pigModelData.clientGameTime - Long.parseLong(pigModelData.birthTime));
            if (ageTime <= 108000) {
                age = ageTime/108000.0F;
            }
        }

        float finalPigSize = (( 3.0F * size * age) + size) / 4.0F;
        matrixStackIn.push();
        matrixStackIn.scale(finalPigSize, finalPigSize, finalPigSize);
        matrixStackIn.translate(0.0F, -1.5F + 1.5F/finalPigSize, 0.0F);

        if (true) {
            renderPigandSaddle(pigModelData.unrenderedModels, matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        } else {
            this.pig.render(matrixStackIn, bufferIn, null, new ArrayList<>(), false, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        }

        if (blink >= 6) {
            this.eyeLeft.showModel = true;
            this.eyeRight.showModel = true;
        } else {
            this.eyeLeft.showModel = false;
            this.eyeRight.showModel = false;
        }

        matrixStackIn.pop();
    }

    private void renderPigandSaddle(List<String> unrenderedModels, MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        if (true) {
            Map<String, List<Float>> mapOfScale = new HashMap<>();
            float saddleScale = 0.75F;

            this.saddleWestern.showModel = false;
            this.saddleEnglish.showModel = false;
            this.saddle.showModel = false;
            this.saddlePomel.showModel = false;

//            float antiScale = 1.25F;
            List<Float> scalingsForSaddle = ModelHelper.createScalings(saddleScale, saddleScale, saddleScale, 0.0F, -saddleScale*0.01F, (saddleScale - 1.0F)*0.04F);
//            List<Float> scalingsForPad = createScalings(antiScale, 0.0F, -antiScale*0.01F, (antiScale - 1.0F)*0.04F);
//            mapOfScale.put("SaddlePad", scalingsForPad);

            if (saddleType.equals("western")) {
                this.saddleWestern.showModel = true;
                this.saddlePomel.showModel = true;
                mapOfScale.put("WesternSaddle", scalingsForSaddle);
            } else if (saddleType.equals("english")) {
                this.saddleEnglish.showModel = true;
                saddleScale = 1.125F;
                List<Float> scalingsForSaddlePad = ModelHelper.createScalings(saddleScale, saddleScale, saddleScale, 0.0F, -saddleScale*0.01F, (saddleScale - 1.0F)*0.04F);
                mapOfScale.put("SaddlePad", scalingsForSaddlePad);
                mapOfScale.put("EnglishSaddle", scalingsForSaddle);
            } else {
                this.saddle.showModel = true;
                mapOfScale.put("Saddle", scalingsForSaddle);
            }
            this.pig.render(matrixStackIn, bufferIn , mapOfScale, unrenderedModels, false, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        }
    }

    @Override
    public void setLivingAnimations(T entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTickTime) {
        super.setLivingAnimations(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTickTime);

        PigModelData pigModelData = getCreatePigModelData(entitylivingbaseIn);
        this.currentPig = entitylivingbaseIn.getEntityId();

        int[] sharedGenes = pigModelData.pigGenes;
        char[] uuidArry = pigModelData.uuidArray;
        float onGround;

        boolean sleeping = pigModelData.sleeping;

        if (sleeping) {
            onGround = sleepingAnimation();
        } else {
            onGround = standingAnimation();
        }

        this.neck.rotationPointZ = onGround - (entitylivingbaseIn).getHeadRotationPointY(partialTickTime) * 4.5F;
        this.headRotationAngleX = (entitylivingbaseIn).getHeadRotationAngleX(partialTickTime);

        //snoutLength
        float snoutLength1 = 1.0F;
        float snoutLength2 = 1.0F;
        float snoutLength = 0.0F;

        if(sharedGenes != null) {
            // 1 - 5, longest - shortest
            for (int i = 1; i < sharedGenes[18];i++){
                snoutLength1 = snoutLength1 - 0.1F;
            }

            for (int i = 1; i < sharedGenes[19];i++){
                snoutLength2 = snoutLength2 - 0.1F;
            }

            //causes partial dominance of longer nose over shorter nose.
            if (snoutLength1 > snoutLength2){
                snoutLength = (snoutLength1*0.75F) + (snoutLength2*0.25F);
            }else if (snoutLength1 < snoutLength2){
                snoutLength = (snoutLength1*0.25F) + (snoutLength2*0.75F);
            }else{
                snoutLength = snoutLength1;
            }

            // 1 - 4, longest - shortest
            if (sharedGenes[42] >= sharedGenes[43]) {
                snoutLength = snoutLength + ((4 - sharedGenes[42])/8.0F);
            } else {
                snoutLength = snoutLength + ((4 - sharedGenes[43])/8.0F);
            }

            if (sharedGenes[44] >= 2 && sharedGenes[45] >= 2) {
                if (sharedGenes[44] == 2 || sharedGenes[45] == 2) {
                    snoutLength = snoutLength * 0.9F;
                } else {
                    snoutLength = snoutLength * 0.75F;
                }
            }

            if (sharedGenes[46] == 2 && sharedGenes[47] == 2) {
                snoutLength = snoutLength * 0.75F;
            }

        }

        if (snoutLength > 1.0F) {
            snoutLength = 1.0F;
        } else if (snoutLength < 0.0F) {
            snoutLength = 0.0F;
        }


        if (!(pigModelData.birthTime == null) && !pigModelData.birthTime.equals("") && !pigModelData.birthTime.equals("0")) {
            int ageTime = (int) (pigModelData.clientGameTime - Long.parseLong(pigModelData.birthTime));
            if (ageTime < 70000) {
                float age = ageTime/70000.F;
                snoutLength = snoutLength * age;
            }
        }

        /*
            shortest nose Y rotation point:
                snout = -2.0F
                topTusks = -4.5F
                bottomTusks = -5.0F

            longest nose Y rotation point:
                snout = -7.0F
                topTusks = -1.0F
                bottomTusks = -2.0F
         */

        //TODO check that baby pig snoots are short and cute
//        this.snout.rotateAngleX = -snoutLength;
        this.snout.rotationPointY = -(2.0F + (5.0F*snoutLength));
        this.tuskTL.rotationPointY = -(4.5F - (3.5F*snoutLength));
        this.tuskTR.rotationPointY = this.tuskTL.rotationPointY;
        this.tuskBL.rotationPointY = -(5.0F - (3.0F*snoutLength));
        this.tuskBR.rotationPointY = this.tuskBL.rotationPointY;





        float inbreedingFactor = 0.0F;

        if(sharedGenes != null) {
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
        }

        this.tail0.rotateAngleX = 1.5F * inbreedingFactor;
        this.tail1.rotateAngleX = 1.1111F * inbreedingFactor;
        this.tail2.rotateAngleX = 1.2222F * inbreedingFactor;
        this.tail3.rotateAngleX = 1.3333F * inbreedingFactor;
        this.tail4.rotateAngleX = 1.5F * inbreedingFactor;
        this.tail5.rotateAngleX = 0.1F * inbreedingFactor;

        if(uuidArry != null) {
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

    @Override
    public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        PigModelData pigModelData = getPigModelData();
        int ticks = entityIn.ticksExisted;
//        List<String> unrenderedModels = new ArrayList<>();

        this.neck.rotateAngleX = ((float)Math.PI * 2.0F);
        this.body.rotateAngleX = ((float)Math.PI / 2F);
        this.butt.rotateAngleX = ((float)Math.PI / 2F);

        /**
         * change only earFlopMod for ear effects. If anything is tweaked it needs to be through that.
         */
        float earFlopMod = -1.0F; //[-1.0 = full droop, -0.65 = half droop, -0.25 = over eyes flop, 0 = stiff flop, 0.5F is half flop, 1 is no flop]

        float earFlopContinuationMod = 1.0F;

        if (earFlopMod < -0.25) {
            earFlopContinuationMod = (1 + earFlopMod)/0.75F;
            earFlopMod = -0.25F;
        } else {
            earFlopContinuationMod = 1.0F;
        }

        this.earSmallL.rotateAngleX = ((-(float)Math.PI / 2.0F) * earFlopMod * earFlopContinuationMod) + ((((float)Math.PI / 2.2F)) * (1.0F - earFlopContinuationMod));
        this.earSmallR.rotateAngleX = ((-(float)Math.PI / 2.0F) * earFlopMod *earFlopContinuationMod) + ((((float)Math.PI / 2.2F)) * (1.0F - earFlopContinuationMod));

        this.earSmallL.rotateAngleY = ((float)Math.PI / 3F) * earFlopContinuationMod;
        this.earSmallR.rotateAngleY = -((float)Math.PI / 3F) * earFlopContinuationMod;

        this.earSmallL.rotateAngleZ = (-((float)Math.PI / 10F) * earFlopContinuationMod) + (((float)Math.PI / 2.2F) * (1.0F-earFlopContinuationMod));
        this.earSmallR.rotateAngleZ = (((float)Math.PI / 10F) * earFlopContinuationMod) - (((float)Math.PI / 2.2F) * (1.0F-earFlopContinuationMod));

        if (earFlopMod == -0.25F) {
            earSmallL.rotationPointZ = 3.0F * earFlopContinuationMod;
            earSmallR.rotationPointZ = 3.0F * earFlopContinuationMod;
        }

//
//        this.earSmallL.rotateAngleX = -((float)Math.PI / 2.0F) * earFlopMod * earFlopContinuationMod;
//        this.earSmallR.rotateAngleX = -((float)Math.PI / 2.0F) * earFlopMod;
//
//        this.earSmallL.rotateAngleY = ((float)Math.PI / 3F) * 1.0F * earFlopContinuationMod;
//        this.earSmallR.rotateAngleY = -((float)Math.PI / 3F);
//
//        this.earSmallL.rotateAngleZ = -((float)Math.PI / 10F) * 1.0F * earFlopContinuationMod;
//        this.earSmallR.rotateAngleZ = ((float)Math.PI / 10F);



//        this.earSmallR.rotateAngleX = ((float)Math.PI / 2.2F);
//        this.earSmallR.rotateAngleY = 0.0F;
//        this.earSmallR.rotateAngleZ = ((float)Math.PI / -2F);

        ModelHelper.copyModelAngles(earSmallL, earMediumL);
        ModelHelper.copyModelAngles(earSmallR, earMediumR);


        this.neck.rotateAngleX = this.neck.rotateAngleX + ((headPitch * 0.017453292F) / 5.0F) + (this.headRotationAngleX / 2.0F);
        this.head.rotateAngleX = ((headPitch * 0.017453292F)/5.0F)  + (this.headRotationAngleX / 2.0F);

        this.head.rotateAngleZ = -(netHeadYaw * 0.017453292F)/2.0F;

        if (!pigModelData.sleeping) {
            this.neck.rotateAngleZ = -(netHeadYaw * 0.017453292F)/2.0F;
            this.leg1.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
            this.leg2.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
            this.leg3.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
            this.leg4.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        }

        this.mouth.rotateAngleX = -0.12F + this.headRotationAngleX;

        this.tuskTL.rotateAngleZ = 1.3F;
        this.tuskTR.rotateAngleZ = -1.3F;

        this.tuskBL.rotateAngleZ = 1.5F;
        this.tuskBR.rotateAngleZ = -1.5F;

        ModelHelper.copyModelAngles(neck, neckBigger);

        this.tail0.rotateAngleX = -((float)Math.PI / 2F);

        if (saddleType.equals("western")) {
            this.saddleWestern.rotateAngleX = -((float)Math.PI / 2F);
            this.saddleWestern.setRotationPoint(0.0F, 4.0F, 10.5F);
            this.saddleSideL.setRotationPoint(5.0F, -1.0F, -5.25F);
            this.saddleSideR.setRotationPoint(-5.0F, -1.0F, -5.25F);
            this.saddleHorn.setRotationPoint(0.0F, -2.0F, -2.0F);
            this.saddleHorn.rotateAngleX = (float)Math.PI / 8.0F;
            this.saddlePomel.setRotationPoint(0.0F, -1.5F, -0.5F);
            this.saddlePomel.rotateAngleX = -0.2F;
            this.stirrup2DWideL.setRotationPoint(7.5F, 0.0F, -3.5F);
            this.stirrup2DWideR.setRotationPoint(-7.5F, 0.0F, -3.5F);
        } else if (saddleType.equals("english")) {
            this.saddleEnglish.rotateAngleX = -((float)Math.PI / 2F);
            this.saddleEnglish.setRotationPoint(0.0F, 4.0F, 10.5F);
            this.saddleSideL.setRotationPoint(3.25F, -0.5F, -4.0F);
            this.saddleSideR.setRotationPoint(-3.25F, -0.5F, -4.0F);
            this.saddleHorn.setRotationPoint(0.0F, -1.0F, -1.0F);
            this.saddleHorn.rotateAngleX = (float)Math.PI / 4.5F;
            this.stirrup3DNarrowL.setRotationPoint(8.0F, -0.5F, -1.5F);
            this.stirrup3DNarrowR.setRotationPoint(-8.0F, -0.5F, -1.5F);
        } else {
            this.saddle.rotateAngleX = -((float)Math.PI / 2F);
            this.saddle.setRotationPoint(0.0F, 4.0F, 10.5F);
            this.stirrup3DNarrowL.setRotationPoint(8.0F, 0.0F, 0.0F);
            this.stirrup3DNarrowR.setRotationPoint(-8.0F, 0.0F, 0.0F);
        }

    }

    private float sleepingAnimation() {
        float onGround;

        onGround = 9.80F;

        this.pig.rotateAngleZ = (float)Math.PI / 2.0F;
        this.pig.setRotationPoint(15.0F, 19.0F, 0.0F);
        this.leg1.rotateAngleZ = -0.8F;
        this.leg1.rotateAngleX = 0.3F;
        this.leg3.rotateAngleZ = -0.8F;
        this.leg3.rotateAngleX = -0.3F;
        this.neck.rotateAngleZ = 0.2F;

        return onGround;
    }

    private float standingAnimation() {
        float onGround;
        onGround = 8.75F;

        this.pig.rotateAngleZ = 0.0F;
        this.pig.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.leg1.rotateAngleZ = 0.0F;
        this.leg3.rotateAngleZ = 0.0F;

        return onGround;
    }

    private class PigModelData {
        int[] pigGenes;
        char[] uuidArray;
        String birthTime;
        float size = 1.0F;
        boolean sleeping = false;
        int blink = 0;
        int lastAccessed = 0;
        long clientGameTime = 0;
//        int dataReset = 0;
        List<String> unrenderedModels = new ArrayList<>();
    }

    private PigModelData getPigModelData() {
        if (this.currentPig == null || !pigModelDataCache.containsKey(this.currentPig)) {
            return new PigModelData();
        }
        return pigModelDataCache.get(this.currentPig);
    }

    private PigModelData getCreatePigModelData(T enhancedPig) {
        clearCacheTimer++;
        if(clearCacheTimer > 50000) {
            pigModelDataCache.values().removeIf(value -> value.lastAccessed==1);
            for (PigModelData pigModelData : pigModelDataCache.values()){
                pigModelData.lastAccessed = 1;
            }
            clearCacheTimer = 0;
        }

        if (pigModelDataCache.containsKey(enhancedPig.getEntityId())) {
            PigModelData pigModelData = pigModelDataCache.get(enhancedPig.getEntityId());
            pigModelData.lastAccessed = 0;
//            pigModelData.dataReset++;
//            if (pigModelData.dataReset > 5000) {

//                pigModelData.dataReset = 0;
//            }
            pigModelData.sleeping = enhancedPig.isAnimalSleeping();
            pigModelData.blink = enhancedPig.getBlink();
            pigModelData.clientGameTime = (((WorldInfo)((ClientWorld)enhancedPig.world).getWorldInfo()).getGameTime());

            return pigModelData;
        } else {
            PigModelData pigModelData = new PigModelData();
            pigModelData.pigGenes = enhancedPig.getSharedGenes();
            pigModelData.size = enhancedPig.getAnimalSize();
            pigModelData.sleeping = enhancedPig.isAnimalSleeping();
            pigModelData.blink = enhancedPig.getBlink();
            pigModelData.uuidArray = enhancedPig.getCachedUniqueIdString().toCharArray();
            pigModelData.birthTime = enhancedPig.getBirthTime();
            pigModelData.clientGameTime = (((WorldInfo)((ClientWorld)enhancedPig.world).getWorldInfo()).getGameTime());

            if(pigModelData.pigGenes != null) {
                pigModelDataCache.put(enhancedPig.getEntityId(), pigModelData);
            }

            return pigModelData;
        }
    }

}
