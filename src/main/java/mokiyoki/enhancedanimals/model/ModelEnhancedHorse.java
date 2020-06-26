package mokiyoki.enhancedanimals.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import mokiyoki.enhancedanimals.entity.EnhancedHorse;
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
public class ModelEnhancedHorse <T extends EnhancedHorse> extends EntityModel<T> {

    String saddleType = "vanilla";

    private Map<Integer, HorseModelData> horseModelDataCache = new HashMap<>();
    private int clearCacheTimer = 0;

    private final ModelRenderer head;
    private final ModelRenderer eyeLeft;
    private final ModelRenderer eyeRight;
    private final ModelRenderer earL;
    private final ModelRenderer earR;
    private final ModelRenderer jaw;
    private final ModelRenderer maneJoiner;
    private final ModelRenderer neck;
    private final EnhancedRendererModelNew body;
    private final ModelRenderer tail;
    private final ModelRenderer leg1;
    private final ModelRenderer leg2;
    private final ModelRenderer leg3;
    private final ModelRenderer leg4;
    private final ModelRenderer hock3;
    private final ModelRenderer hock4;
    private final ModelRenderer hoof1;
    private final ModelRenderer hoof2;
    private final ModelRenderer hoof3;
    private final ModelRenderer hoof4;
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

    private Integer currentHorse = null;

    public ModelEnhancedHorse() {
        this.textureWidth = 248;
        this.textureHeight = 124;

        this.head = new ModelRenderer(this, 0, 35);
        this.head.addBox(-3.5F, -0.5F, -6.51F, 7, 7, 7, -0.5F);
        this.head.setTextureOffset(28, 35);
        this.head.addBox(-2.5F, -0.5F, -9.5F, 5, 4, 4, -0.5F);
        this.head.setTextureOffset(28, 43);
        this.head.addBox(-2.5F, -0.4F, -12.5F, 5, 4, 4, -0.4F);
//        this.head.setTextureOffset(0, 0);
//        this.head.addBox(-4.01F, 0.0F, -6.0F, 3, 4, 4, -1.0F);
//        this.head.addBox(1.01F, 0.0F, -6.0F, 3, 4, 4, -1.0F);

        this.head.setTextureOffset(94, 0);
        this.head.addBox(-1.5F, -1.5F, -4.5F, 3, 3, 6, -0.5F); //mane piece 1
        this.head.setRotationPoint(0.0F, -14.0F, -1.0F);

        this.eyeLeft = new ModelRenderer(this, 0, 0);
        this.eyeLeft.addBox(2.01F, 0.0F, -6.0F, 0, 4, 4, -1.0F);

        this.eyeRight = new ModelRenderer(this, 3, 0);
        this.eyeRight.addBox(-2.01F, 0.0F, -6.0F, 0, 4, 4, -1.0F);

        this.earL = new ModelRenderer(this, 6, 0);
        this.earL.addBox(-2.0F, -3.0F, -0.5F, 2, 3, 1);
        this.earL.setRotationPoint(-1.0F, 0.0F, -1.0F);

        this.earR = new ModelRenderer(this, 0, 0);
        this.earR.addBox(0.0F, -3.0F, -0.5F, 2, 3, 1);
        this.earR.setRotationPoint(1.0F, 0.0F, -1.0F);

        this.jaw = new ModelRenderer(this, 69,0);
        this.jaw.addBox(-2.0F, 0.0F, -9.0F, 4, 4, 7, -0.1F);
        this.jaw.setTextureOffset(0, 15);
        this.jaw.addBox(-2.0F, 2.25F, -9.0F, 4, 1, 7, -0.11F);
        this.jaw.setRotationPoint(0.0F, 2.5F, -2.0F);

        this.maneJoiner = new ModelRenderer(this, 98, 9);
        this.maneJoiner.addBox(-1.5F, -1.49F, -0.49F, 3, 2, 2, -0.51F); //mane piece 2
        this.maneJoiner.setRotationPoint(0.0F, -13.0F, 0.0F);

        this.neck = new ModelRenderer(this, 69, 15);
        this.neck.addBox(-2.5F, -14.0F, -7.0F, 5, 17, 8, -1.0F);
        this.neck.setTextureOffset(97, 13);
        this.neck.addBox(-1.5F, -13.5F, -0.5F, 3, 18, 3, -0.5F); // mane piece 3
        this.neck.setRotationPoint(0.0F, 1.0F, -5.0F);

        this.body = new EnhancedRendererModelNew(this, 0, 0, "Body");
        this.body.addBox(-5.5F, -0.5F, -10.5F, 11, 11, 23, -0.5F);
        this.body.setRotationPoint(0.0F, 1.0F, 0.0F);

        this.tail = new ModelRenderer(this, 97, 34);
        this.tail.addBox(-2.0F, 0.0F, -4.0F, 4, 15, 4, 0.0F);
        this.tail.setRotationPoint(0.0F, 0.0F, 12.0F);

        this.leg1 = new ModelRenderer(this, 6, 53);
        this.leg1.addBox(0.0F, 0.5F, 0.0F, 5, 13, 5, -1.0F);
        this.leg1.setRotationPoint(-6.0F, 9.5F, -9.0F);

        this.leg2 = new ModelRenderer(this, 26, 53);
        this.leg2.addBox(0.0F, 0.5F, 0.0F, 5, 13, 5, -1.0F);
        this.leg2.setRotationPoint(1.0F, 9.5F, -9.0F);

        this.hock3 = new ModelRenderer(this, 47, 41);
        this.hock3.addBox(0.75F, -4.0F, -0.75F, 4, 7, 5, 0.0F);
        this.hock3.setRotationPoint(-6.0F, 9.5F, 8.0F);

        this.leg3 = new ModelRenderer(this, 46, 53);
        this.leg3.addBox(0.0F, 2.0F, 0.0F, 5, 12, 5, -1.0F);
        this.leg3.setRotationPoint(-6.0F, 9.5F, 8.0F);

        this.hock4 = new ModelRenderer(this, 67, 41);
        this.hock4.addBox(0.25F, -4.0F, -0.75F, 4, 7, 5, 0.0F);
        this.hock4.setRotationPoint(1.0F, 9.5F, 8.0F);

        this.leg4 = new ModelRenderer(this, 66, 53);
        this.leg4.addBox(0.0F, 2.0F, 0.0F, 5, 12, 5, -1.0F);
        this.leg4.setRotationPoint(1.0F, 9.5F, 8.0F);

        this.hoof1 = new ModelRenderer(this, 6, 71);
        this.hoof1.addBox(0.0F, 11.75F, 0.1F, 5, 3, 4, -0.5F);

        this.hoof2 = new ModelRenderer(this, 26, 71);
        this.hoof2.addBox(0.0F, 11.75F, 0.1F, 5, 3, 4, -0.5F);

        this.hoof3 = new ModelRenderer(this, 46, 71);
        this.hoof3.addBox(0.0F, 11.75F, 0.1F, 5, 3, 4, -0.5F);

        this.hoof4 = new ModelRenderer(this, 66, 71);
        this.hoof4.addBox(0.0F, 11.75F, 0.1F, 5, 3, 4, -0.5F);

        this.neck.addChild(head);
        this.neck.addChild(maneJoiner);
        this.head.addChild(earL);
        this.head.addChild(earR);
        this.head.addChild(jaw);
        this.head.addChild(eyeLeft);
        this.head.addChild(eyeRight);

            /**
             * Equipment stuff
             */

        this.saddle = new EnhancedRendererModelNew(this, 186, 24, "Saddle");
        this.saddle.addBox(-8.0F, -1.0F, -6.0F, 16, 10, 15, -1.0F);

        this.saddleWestern = new EnhancedRendererModelNew(this, 202, 0, "WesternSaddle");
        this.saddleWestern.addBox(-5.0F, -2.0F, -5.0F, 10, 2, 13, 0.0F);
        this.saddleWestern.setTextureOffset(202, 15);
        this.saddleWestern.addBox(-4.0F, -3.0F, 5.0F, 8, 2, 4, 0.0F);
        this.saddleWestern.setTextureOffset(222, 15);
        this.saddleWestern.addBox(-3.5F, -4.0F, 8.0F, 7, 2, 2, 0.0F);

        this.saddleEnglish = new EnhancedRendererModelNew(this, 203, 1, "EnglishSaddle");
        this.saddleEnglish.addBox(-5.0F, -1.0F, -4.0F, 10, 2, 12, 0.0F);
        this.saddleEnglish.setTextureOffset(202, 15);
        this.saddleEnglish.addBox(-4.0F, -1.5F, 5.0F, 8, 2, 4, 0.0F);
        this.saddleEnglish.setTextureOffset(222, 15);
        this.saddleEnglish.addBox(-3.5F, -2.0F, 7.5F, 7, 2, 2, 0.0F);

        this.saddleHorn = new EnhancedRendererModelNew(this, 226, 19, "SaddleHorn");
        this.saddleHorn.addBox(-4.0F, -2.0F, -3.0F, 8, 2, 3, 0.0F);

        this.saddlePomel = new EnhancedRendererModelNew(this, 235, 0, "SaddlePomel");
        this.saddlePomel.addBox(-1.0F, -3.0F, -2.0F, 2, 4, 2, -0.25F);
        this.saddlePomel.setRotationPoint(0.0F, -2.0F, -2.0F);

        this.saddleSideL = new EnhancedRendererModelNew(this, 226, 49, "SaddleLeft");
        this.saddleSideL.addBox(0.0F, 0.0F, 0.0F, 3, 4, 8);

        this.saddleSideR = new EnhancedRendererModelNew(this, 226, 61, "SaddleRight");
        this.saddleSideR.addBox(-3.0F, 0.0F, 0.0F, 3, 4, 8);

        this.stirrup2DWideL = new EnhancedRendererModelNew(this, 240, 24, "2DStirrupL");
        this.stirrup2DWideL.addBox(0.0F, 0.0F, 0.0F, 0, 10, 4); // strap

        this.stirrup2DWideR = new EnhancedRendererModelNew(this, 240, 24, "2DStirrupR");
        this.stirrup2DWideR.addBox(0.0F, 0.0F, 0.0F, 0, 10, 4); // strap

        this.stirrup3DNarrowL = new EnhancedRendererModelNew(this, 241, 27, "3DStirrupL");
        this.stirrup3DNarrowL.addBox(-1.0F, 0.0F, 0.0F, 1, 10, 1); // strap

        this.stirrup3DNarrowR = new EnhancedRendererModelNew(this, 243, 27, "3DStirrupR");
        this.stirrup3DNarrowR.addBox(0.0F, 0.0F, 0.0F, 1, 10, 1);

        this.stirrup = new EnhancedRendererModelNew(this, 202, 0, "Stirrup");
        this.stirrup.addBox(-0.5F, 9.5F, -1.0F, 1, 1, 1);
        this.stirrup.setTextureOffset(206, 0);
        this.stirrup.addBox(-0.5F, 9.5F, 1.0F, 1, 1, 1);
        this.stirrup.setTextureOffset(202, 2);
        this.stirrup.addBox(-0.5F, 10.5F, -1.5F, 1, 3, 1);
        this.stirrup.setTextureOffset(206, 2);
        this.stirrup.addBox(-0.5F, 10.5F, 1.5F, 1, 3, 1);
        this.stirrup.setTextureOffset(203, 7);
        this.stirrup.addBox(-0.5F, 12.5F, -0.5F, 1, 1, 2);

        this.saddlePad = new EnhancedRendererModelNew(this, 186, 24, "SaddlePad");
        this.saddlePad.addBox(-8.0F, -1.0F, -6.0F, 16, 10, 15, -1.0F);

        this.body.addChild(saddle);
        this.saddle.addChild(stirrup3DNarrowL);
        this.saddle.addChild(stirrup3DNarrowR);
        this.saddleHorn.addChild(saddlePomel);

        //western
        this.body.addChild(saddleWestern);
        this.saddleWestern.addChild(saddleHorn);
        this.saddleWestern.addChild(saddleSideL);
        this.saddleWestern.addChild(saddleSideR);
        this.saddleWestern.addChild(saddlePad);
        this.saddleWestern.addChild(stirrup2DWideL);
        this.saddleWestern.addChild(stirrup2DWideR);
        this.stirrup2DWideL.addChild(stirrup);
        this.stirrup2DWideR.addChild(stirrup);
        //english
        this.body.addChild(saddleEnglish);
        this.saddleEnglish.addChild(saddleHorn);
        this.saddleEnglish.addChild(saddleSideL);
        this.saddleEnglish.addChild(saddleSideR);
        this.saddleEnglish.addChild(saddlePad);
        this.saddleEnglish.addChild(stirrup3DNarrowL);
        this.saddleEnglish.addChild(stirrup3DNarrowR);
        this.stirrup3DNarrowL.addChild(stirrup);
        this.stirrup3DNarrowR.addChild(stirrup);
    }


    @Override
    public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        HorseModelData horseModelData = getHorseModelData();

//        this.head.render(scale);
        this.neck.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        renderBodyandSaddle(horseModelData.unrenderedModels, matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.tail.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.leg1.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.leg2.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.leg3.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.leg4.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.hock3.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.hock4.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.hoof1.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.hoof2.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.hoof3.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.hoof4.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);

        if (horseModelData.sleeping) {
            this.eyeLeft.showModel = false;
            this.eyeRight.showModel = false;
        } else {
            this.eyeLeft.showModel = true;
            this.eyeRight.showModel = true;
        }

    }

    private void renderBodyandSaddle(List<String> unrenderedModels, MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        if (true) {
            Map<String, List<Float>> mapOfScale = new HashMap<>();
            float saddleScale = 0.75F;

            this.saddleWestern.showModel = false;
            this.saddleEnglish.showModel = false;
            this.saddle.showModel = false;
            this.saddlePomel.showModel = false;

//            float antiScale = 1.25F;
            List<Float> scalingsForSaddle = ModelHelper.createScalings(saddleScale,saddleScale,saddleScale, 0.0F, -saddleScale*0.01F, (saddleScale - 1.0F)*0.04F);
//            List<Float> scalingsForPad = createScalings(antiScale, 0.0F, -antiScale*0.01F, (antiScale - 1.0F)*0.04F);
//            mapOfScale.put("SaddlePad", scalingsForPad);

            if (saddleType.equals("western")) {
                this.saddleWestern.showModel = true;
                this.saddlePomel.showModel = true;
                mapOfScale.put("WesternSaddle", scalingsForSaddle);
            } else if (saddleType.equals("english")) {
                this.saddleEnglish.showModel = true;
                mapOfScale.put("EnglishSaddle", scalingsForSaddle);
            } else {
                //vanilla saddle
                this.saddle.showModel = true;
                mapOfScale.put("Saddle", scalingsForSaddle);
            }
            this.body.render(matrixStackIn, bufferIn , mapOfScale, unrenderedModels, false, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        }
    }

    @Override
    public void setLivingAnimations(T entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTickTime) {
        HorseModelData horseModelData = getCreateHorseModelData(entitylivingbaseIn);
        this.currentHorse = entitylivingbaseIn.getEntityId();

        char[] uuidArry = horseModelData.uuidArray;

        if (uuidArry != null) {
            if (Character.isDigit(uuidArry[16])){
                if (uuidArry[16] - 48 == 0) {
                    //0
                    this.hock3.rotationPointZ = 8.5F;
                    this.hock4.rotationPointZ = 8.5F;
                } else if (uuidArry[16] - 48 == 1) {
                    //1
                    this.hock3.rotationPointZ = 8.4F;
                    this.hock4.rotationPointZ = 8.4F;
                } else if (uuidArry[16] - 48 == 2) {
                    //2
                    this.hock3.rotationPointZ = 8.3F;
                    this.hock4.rotationPointZ = 8.3F;
                } else if (uuidArry[16] - 48 == 3) {
                    //3
                    this.hock3.rotationPointZ = 8.2F;
                    this.hock4.rotationPointZ = 8.2F;
                } else if (uuidArry[16] - 48 == 4) {
                    //4
                    this.hock3.rotationPointZ = 8.1F;
                    this.hock4.rotationPointZ = 8.1F;
                } else if (uuidArry[16] - 48 == 5) {
                    //5
                    this.hock3.rotationPointZ = 8.0F;
                    this.hock4.rotationPointZ = 8.0F;
                } else if (uuidArry[16] - 48 == 6) {
                    //6
                    this.hock3.rotationPointZ = 7.9F;
                    this.hock4.rotationPointZ = 7.9F;
                } else if (uuidArry[16] - 48 == 7) {
                    //7
                    this.hock3.rotationPointZ = 7.8F;
                    this.hock4.rotationPointZ = 7.8F;
                } else if (uuidArry[16] - 48 == 8) {
                    //0
                    this.hock3.rotationPointZ = 8.5F;
                    this.hock4.rotationPointZ = 8.5F;
                } else {
                    //1
                    this.hock3.rotationPointZ = 8.4F;
                    this.hock4.rotationPointZ = 8.4F;
                }
            } else {
                char test = uuidArry[16];
                switch (test){
                    case 'a':
                        //2
                        this.hock3.rotationPointZ = 8.3F;
                        this.hock4.rotationPointZ = 8.3F;
                        break;
                    case 'b':
                        //3
                        this.hock3.rotationPointZ = 8.2F;
                        this.hock4.rotationPointZ = 8.2F;
                        break;
                    case 'c':
                        //4
                        this.hock3.rotationPointZ = 8.1F;
                        this.hock4.rotationPointZ = 8.1F;
                        break;
                    case 'd':
                        //5
                        this.hock3.rotationPointZ = 8.0F;
                        this.hock4.rotationPointZ = 8.0F;
                        break;
                    case 'e':
                        //6
                        this.hock3.rotationPointZ = 7.9F;
                        this.hock4.rotationPointZ = 7.9F;
                        break;
                    case 'f':
                        //7
                        this.hock3.rotationPointZ = 7.8F;
                        this.hock4.rotationPointZ = 7.8F;
                        break;
                }
            }

            if (Character.isDigit(uuidArry[17])){
                if (uuidArry[17] - 48 == 0) {
                    //a
                    this.leg3.rotationPointZ = this.hock3.rotationPointZ + 1.5F;
                    this.leg4.rotationPointZ = this.hock4.rotationPointZ + 1.5F;
                } else if (uuidArry[17] - 48 == 1) {
                    //b
                    this.leg3.rotationPointZ = this.hock3.rotationPointZ + 1.35F;
                    this.leg4.rotationPointZ = this.hock4.rotationPointZ + 1.35F;
                } else if (uuidArry[17] - 48 == 2) {
                    //c
                    this.leg3.rotationPointZ = this.hock3.rotationPointZ + 1.25F;
                    this.leg4.rotationPointZ = this.hock4.rotationPointZ + 1.25F;
                } else if (uuidArry[17] - 48 == 3) {
                    //d
                    this.leg3.rotationPointZ = this.hock3.rotationPointZ + 1.15F;
                    this.leg4.rotationPointZ = this.hock4.rotationPointZ + 1.15F;
                } else if (uuidArry[17] - 48 == 4) {
                    //e
                    this.leg3.rotationPointZ = this.hock3.rotationPointZ + 1.05F;
                    this.leg4.rotationPointZ = this.hock4.rotationPointZ + 1.05F;
                } else if (uuidArry[17] - 48 == 5) {
                    //f
                    this.leg3.rotationPointZ = this.hock3.rotationPointZ + 0.95F;
                    this.leg4.rotationPointZ = this.hock4.rotationPointZ + 0.95F;
                } else if (uuidArry[17] - 48 == 6) {
                    //g
                    this.leg3.rotationPointZ = this.hock3.rotationPointZ + 0.85F;
                    this.leg4.rotationPointZ = this.hock4.rotationPointZ + 0.85F;
                } else if (uuidArry[17] - 48 == 7) {
                    //h
                    this.leg3.rotationPointZ = this.hock3.rotationPointZ + 0.75F;
                    this.leg4.rotationPointZ = this.hock4.rotationPointZ + 0.75F;
                } else if (uuidArry[17] - 48 == 8) {
                    //a
                    this.leg3.rotationPointZ = this.hock3.rotationPointZ + 1.5F;
                    this.leg4.rotationPointZ = this.hock4.rotationPointZ + 1.5F;
                } else {
                    //b
                    this.leg3.rotationPointZ = this.hock3.rotationPointZ + 1.35F;
                    this.leg4.rotationPointZ = this.hock4.rotationPointZ + 1.35F;
                }
            } else {
                char test = uuidArry[17];
                switch (test){
                    case 'a':
                        //c
                        this.leg3.rotationPointZ = this.hock3.rotationPointZ + 1.25F;
                        this.leg4.rotationPointZ = this.hock4.rotationPointZ + 1.25F;
                        break;
                    case 'b':
                        //d
                        this.leg3.rotationPointZ = this.hock3.rotationPointZ + 1.15F;
                        this.leg4.rotationPointZ = this.hock4.rotationPointZ + 1.15F;
                        break;
                    case 'c':
                        //e
                        this.leg3.rotationPointZ = this.hock3.rotationPointZ + 1.05F;
                        this.leg4.rotationPointZ = this.hock4.rotationPointZ + 1.05F;
                        break;
                    case 'd':
                        //f
                        this.leg3.rotationPointZ = this.hock3.rotationPointZ + 0.95F;
                        this.leg4.rotationPointZ = this.hock4.rotationPointZ + 0.95F;
                        break;
                    case 'e':
                        //g
                        this.leg3.rotationPointZ = this.hock3.rotationPointZ + 0.85F;
                        this.leg4.rotationPointZ = this.hock4.rotationPointZ + 0.85F;
                        break;
                    case 'f':
                        //h
                        this.leg3.rotationPointZ = this.hock3.rotationPointZ + 0.75F;
                        this.leg4.rotationPointZ = this.hock4.rotationPointZ + 0.75F;
                        break;
                }
            }
        }
    }

    @Override
    public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.neck.rotateAngleX = headPitch * 0.017453292F + 0.8F;
        this.neck.rotateAngleY = ((netHeadYaw * 0.017453292F) * 0.40F);
        this.head.rotateAngleY = ((netHeadYaw * 0.017453292F) * 0.14F);
        this.maneJoiner.rotateAngleY = ((netHeadYaw * 0.017453292F) * 0.07F);

        this.tail.rotateAngleX = 0.6F;

        this.leg1.rotateAngleX = MathHelper.cos(limbSwing * 0.3332F) * 1.4F * limbSwingAmount;
        this.leg2.rotateAngleX = MathHelper.cos(limbSwing * 0.3332F + (float) Math.PI) * 1.4F * limbSwingAmount;
        this.hock3.rotateAngleX = MathHelper.cos(limbSwing * 0.3332F + (float) Math.PI) * 1.4F * limbSwingAmount;
        this.leg3.rotateAngleX = MathHelper.cos(limbSwing * 0.3332F + (float) Math.PI) * 1.4F * limbSwingAmount;
        this.hock4.rotateAngleX = MathHelper.cos(limbSwing * 0.3332F) * 1.4F * limbSwingAmount;
        this.leg4.rotateAngleX = MathHelper.cos(limbSwing * 0.3332F) * 1.4F * limbSwingAmount;

        ModelHelper.copyModelAngles(leg1, hoof1);
        ModelHelper.copyModelAngles(leg2, hoof2);
        ModelHelper.copyModelAngles(leg3, hoof3);
        ModelHelper.copyModelAngles(leg4, hoof4);

        this.earL.rotateAngleZ = -0.15F;
        this.earR.rotateAngleZ = 0.15F;
        this.jaw.rotateAngleX = -0.15F;

        if (saddleType.equals("western")) {
            this.saddleSideL.setRotationPoint(5.0F, -1.0F, -5.25F);
            this.saddleSideR.setRotationPoint(-5.0F, -1.0F, -5.25F);
            this.saddleHorn.setRotationPoint(0.0F, -2.0F, -2.0F);
            this.saddleHorn.rotateAngleX = (float)Math.PI / 8.0F;
            this.saddlePomel.setRotationPoint(0.0F, -1.5F, -0.5F);
            this.saddlePomel.rotateAngleX = -0.2F;
            this.stirrup2DWideL.setRotationPoint(7.5F, 0.0F, -3.5F);
            this.stirrup2DWideR.setRotationPoint(-7.5F, 0.0F, -3.5F);
        } else if (saddleType.equals("english")) {
            this.saddleSideL.setRotationPoint(3.25F, -0.5F, -4.0F);
            this.saddleSideR.setRotationPoint(-3.25F, -0.5F, -4.0F);
            this.saddleHorn.setRotationPoint(0.0F, -1.0F, -1.0F);
            this.saddleHorn.rotateAngleX = (float)Math.PI / 4.5F;
            this.stirrup3DNarrowL.setRotationPoint(7.25F, -0.25F, -1.5F);
            this.stirrup3DNarrowR.setRotationPoint(-7.25F, -0.25F, -1.5F);
        } else {
            this.stirrup3DNarrowL.setRotationPoint(8.0F, 0.0F, 0.0F);
            this.stirrup3DNarrowR.setRotationPoint(-8.0F, 0.0F, 0.0F);
        }

    }

    private class HorseModelData {
        int[] horseGenes;
        char[] uuidArray;
        String birthTime;
        float size = 1.0F;
        boolean sleeping = false;
        int lastAccessed = 0;
        long clientGameTime = 0;
//        int dataReset = 0;
        List<String> unrenderedModels = new ArrayList<>();
    }

    private HorseModelData getHorseModelData() {
        if (this.currentHorse == null || !horseModelDataCache.containsKey(this.currentHorse)) {
            return new HorseModelData();
        }
        return horseModelDataCache.get(this.currentHorse);
    }

    private HorseModelData getCreateHorseModelData(T enhancedHorse) {
        clearCacheTimer++;
        if(clearCacheTimer > 50000) {
            horseModelDataCache.values().removeIf(value -> value.lastAccessed==1);
            for (HorseModelData horseModelData : horseModelDataCache.values()){
                horseModelData.lastAccessed = 1;
            }
            clearCacheTimer = 0;
        }

        if (horseModelDataCache.containsKey(enhancedHorse.getEntityId())) {
            HorseModelData horseModelData = horseModelDataCache.get(enhancedHorse.getEntityId());
            horseModelData.lastAccessed = 0;
//            pigModelData.dataReset++;
//            if (pigModelData.dataReset > 5000) {

//                pigModelData.dataReset = 0;
//            }
            horseModelData.sleeping = enhancedHorse.isAnimalSleeping();
            horseModelData.clientGameTime = (((WorldInfo)((ClientWorld)enhancedHorse.world).getWorldInfo()).getGameTime());

            return horseModelData;
        } else {
            HorseModelData horseModelData = new HorseModelData();
            horseModelData.horseGenes = enhancedHorse.getSharedGenes();
            horseModelData.size = enhancedHorse.getSize();
            horseModelData.sleeping = enhancedHorse.isAnimalSleeping();
            horseModelData.uuidArray = enhancedHorse.getCachedUniqueIdString().toCharArray();
            horseModelData.birthTime = enhancedHorse.getBirthTime();
            horseModelData.clientGameTime = (((WorldInfo)((ClientWorld)enhancedHorse.world).getWorldInfo()).getGameTime());

            if(horseModelData.horseGenes != null) {
                horseModelDataCache.put(enhancedHorse.getEntityId(), horseModelData);
            }

            return horseModelData;
        }
    }

}
