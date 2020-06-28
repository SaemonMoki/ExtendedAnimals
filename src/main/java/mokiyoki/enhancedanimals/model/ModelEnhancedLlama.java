package mokiyoki.enhancedanimals.model;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import mokiyoki.enhancedanimals.entity.EnhancedLlama;
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
public class ModelEnhancedLlama <T extends EnhancedLlama> extends EntityModel<T> {

    private Map<Integer, LlamaModelData> llamaModelDataCache = new HashMap<>();
    private int clearCacheTimer = 0;

    private final ModelRenderer chest1;
    private final ModelRenderer chest2;

    String saddleType = "western";

    private boolean banana = false;
    private boolean suri = false;

    private final ModelRenderer nose;
    private final ModelRenderer head;
    private final ModelRenderer neckBone;
    private final ModelRenderer neck;
    private final ModelRenderer neckWool0;
    private final ModelRenderer neckWool1;
    private final ModelRenderer neckWool2;
    private final ModelRenderer neckWool3;
    private final ModelRenderer neckWool4;
    private final ModelRenderer earsR;
    private final ModelRenderer earsL;
    private final ModelRenderer earsTopR;
    private final ModelRenderer earsTopL;
    private final ModelRenderer earsTopBananaR;
    private final ModelRenderer earsTopBananaL;
    private final ModelRenderer body;
    private final ModelRenderer bodyShaved;
    private final ModelRenderer body1;
    private final ModelRenderer body2;
    private final ModelRenderer body3;
    private final ModelRenderer body4;
    private final ModelRenderer tail;
    private final ModelRenderer tail1;
    private final ModelRenderer tail2;
    private final ModelRenderer tail3;
    private final ModelRenderer tail4;
    private final ModelRenderer leg1;
    private final ModelRenderer leg1Wool1;
    private final ModelRenderer leg1Wool2;
    private final ModelRenderer leg1Wool3;
    private final ModelRenderer leg1Wool4;
    private final ModelRenderer leg2;
    private final ModelRenderer leg2Wool1;
    private final ModelRenderer leg2Wool2;
    private final ModelRenderer leg2Wool3;
    private final ModelRenderer leg2Wool4;
    private final ModelRenderer leg3;
    private final ModelRenderer leg3Wool1;
    private final ModelRenderer leg3Wool2;
    private final ModelRenderer leg3Wool3;
    private final ModelRenderer leg3Wool4;
    private final ModelRenderer leg4;
    private final ModelRenderer leg4Wool1;
    private final ModelRenderer leg4Wool2;
    private final ModelRenderer leg4Wool3;
    private final ModelRenderer leg4Wool4;
    private final ModelRenderer toeOuterFrontR;
    private final ModelRenderer toeInnerFrontR;
    private final ModelRenderer toeOuterFrontL;
    private final ModelRenderer toeInnerFrontL;
    private final ModelRenderer toeOuterBackR;
    private final ModelRenderer toeInnerBackR;
    private final ModelRenderer toeOuterBackL;
    private final ModelRenderer toeInnerBackL;
    private final ModelRenderer eyeLeft;
    private final ModelRenderer eyeRight;
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

    private Integer currentLlama = null;

    public ModelEnhancedLlama(float scale)
    {
        this.textureWidth = 256;
        this.textureHeight = 256;

        float xMove = -6.0F;
        float headAdjust = -2.0F;

//        this.head.setTextureOffset(28, 0);
//        this.head.addBox(-2.0F, -12.0F, -4.0F, 4, 4, 4, 0.0F); //nose
//        this.head.setRotationPoint(0, 5, -12.0F);

        this.nose = new ModelRenderer(this,28, 0);
        this.nose.addBox(-2.0F, 0.0F, -7.0F, 4, 4, 4, scale); //nose
        this.nose.setRotationPoint(0, 0, 0);

        this.head = new ModelRenderer(this, 0, 0);
        this.head.addBox(-4.0F, -3.0F, -3.0F, 8, 6, 6, scale); //head
        this.head.setTextureOffset(28,8);
        this.head.addBox(-4.0F, -3.0F, -3.0F, 8, 10, 6, scale + 0.505F); //deco
        this.head.setRotationPoint(0.0F, -11.0F, scale + 1.0F);
//        this.head.setTextureOffset(28, 0);
//        this.head.addBox(-2.0F, -12.0F, -4.0F, 4, 4, 4, 0.0F); //nose

        this.neckBone = new ModelRenderer(this);
        this.neckBone.setRotationPoint(0, 5, -6);

        this.neck = new ModelRenderer(this,0, 12);
        this.neck.addBox(-4.0F, -9.0F, -1.1F, 8, 12, 6, scale - 1.0F); //neck
        this.neck.setTextureOffset(28,23);
        this.neck.addBox(-4.0F, -2.0F, -1.1F, 8, 10, 6, scale + 0.01F); //deco
//        this.neck.addBox(-4.0F, -2.0F, -1.1F, 8, 6, 6, -1.0F); //head and neck
        this.neck.setRotationPoint(0, 5, -6);

        this.neckWool0 = new ModelRenderer(this, 0, 12);
        this.neckWool0.addBox(-4.0F, -8.0F, headAdjust, 8, 12, 6, scale + 0.0F); //neck
        this.neckWool0.setTextureOffset(28,23);
        this.neckWool0.addBox(-4.0F, -3.75F, headAdjust, 8, 10, 6, scale + 0.51F); //neck

        this.neckWool1 = new ModelRenderer(this, 0, 12);
        this.neckWool1.addBox(-4.0F, -8.5F, headAdjust, 8, 12, 6, scale + 0.5F); //neck
        this.neckWool1.setTextureOffset(28,23);
        this.neckWool1.addBox(-4.0F, -2.25F, headAdjust, 8, 10, 6, scale + 0.75F); //neck

        this.neckWool2 = new ModelRenderer(this, 0, 12);
        this.neckWool2.addBox(-4.0F, -7.5F, headAdjust, 8, 12, 6, scale + 1.0F); //neck
        this.neckWool2.setTextureOffset(28,23);
        this.neckWool2.addBox(-4.0F, -3.7F, headAdjust, 8, 10, 6, scale + 1.2F); //neck

        this.neckWool3 = new ModelRenderer(this, 0, 12);
        this.neckWool3.addBox(-4.0F, -7.0F, headAdjust, 8, 12, 6, scale + 1.5F); //neck
        this.neckWool3.setTextureOffset(28,23);
        this.neckWool3.addBox(-4.0F, -3.1F, headAdjust, 8, 10, 6, scale + 1.6F); //neck

        this.neckWool4 = new ModelRenderer(this, 0, 12);
        this.neckWool4.addBox(-4.0F, -6.5F, headAdjust, 8, 12, 6, scale + 2.0F); //neck
        this.neckWool4.setTextureOffset(28,23);
        this.neckWool4.addBox(-4.0F, -2.55F, headAdjust, 8, 10, 6, scale + 2.05F); //neck

        this.earsR = new ModelRenderer(this, 44, 0);
        this.earsR.addBox(-4.0F, -6.0F, 0.0F, 3, 3, 2, scale + 0.0F); //ear right

        this.earsL = new ModelRenderer(this, 54, 0);
        this.earsL.addBox(1.0F, -6.0F, 0.0F, 3, 3, 2, scale + 0.0F); //ear left

        this.earsTopR = new ModelRenderer(this, 64, 0);
        this.earsTopR.addBox(-4.0F, -7.0F, 0.0F, 3, 1, 2, scale + 0.0F); //ear right

        this.earsTopL = new ModelRenderer(this, 74, 0);
        this.earsTopL.addBox(1.0F, -7.0F, 0.0F, 3, 1, 2, scale + 0.0F); //ear left

        this.earsTopBananaR = new ModelRenderer(this, 64, 0);
        this.earsTopBananaR.addBox(-3.5F, -7.0F, 0.0F, 3, 1, 2, scale + 0.0F); //ear right
        this.earsTopBananaL = new ModelRenderer(this, 74, 0);
        this.earsTopBananaL.addBox(0.5F, -7.0F, 0.0F, 3, 1, 2, scale + 0.0F); //ear left

        this.body = new ModelRenderer(this, 0, 39);
        this.body.addBox(-6.0F, 0.0F, 0.0F, 12, 10, 18, scale);
        this.body.setRotationPoint(0.0F, 2.0F, -8.0F);

        this.bodyShaved = new ModelRenderer(this, 0, 39);
        this.bodyShaved.addBox(-6.0F, 1.0F, 0.0F, 12, 10, 18, scale - 1.0F);
        this.bodyShaved.setRotationPoint(0.0F, 2.0F, -2.0F);

        this.body1 = new ModelRenderer(this, 0, 39);
        this.body1.addBox(-6.0F, 0.0F, 0.0F, 12, 10, 18, scale + 0.5F);
        this.body1.setRotationPoint(0.0F, 2.0F, -2.0F);

        this.body2 = new ModelRenderer(this, 0, 39);
        this.body2.addBox(-6.0F, 0.0F, 0.0F, 12, 10, 18, scale + 1.0F);
        this.body2.setRotationPoint(0.0F, 2.0F, -2.0F);

        this.body3 = new ModelRenderer(this, 0, 39);
        this.body3.addBox(-6.0F, 0.0F, 0.0F, 12, 10, 18, scale + 1.5F);
        this.body3.setRotationPoint(0.0F, 2.0F, -2.0F);

        this.body4 = new ModelRenderer(this, 0, 39);
        this.body4.addBox(-6.0F, 0.0F, 0.0F, 12, 10, 18, scale + 2.0F);
        this.body4.setRotationPoint(0.0F, 2.0F, -2.0F);

        this.chest1 = new ModelRenderer(this, 74, 44);
        this.chest1.addBox(-3.0F, 0.0F, 0.0F, 8, 8, 3, scale);
        this.chest1.setRotationPoint(-8.5F, 3.0F, 4.0F);
        this.chest1.rotateAngleY = ((float)Math.PI / 2F);
        this.chest2 = new ModelRenderer(this, 74, 57);
        this.chest2.addBox(-3.0F, 0.0F, 0.0F, 8, 8, 3, scale);
        this.chest2.setRotationPoint(5.5F, 3.0F, 4.0F);
        this.chest2.rotateAngleY = ((float)Math.PI / 2F);

        this.tail = new ModelRenderer(this, 42, 39);
        this.tail.addBox(-3.0F, -2.0F, 15.0F, 6, 6, 6, scale);

        this.tail1 = new ModelRenderer(this, 42, 39);
        this.tail1.addBox(-3.0F, -2.0F, 15.0F, 6, 6, 6, scale + 0.25F);

        this.tail2 = new ModelRenderer(this, 42, 39);
        this.tail2.addBox(-3.0F, -2.0F, 15.0F, 6, 6, 6, scale + 0.5F);

        this.tail3 = new ModelRenderer(this, 42, 39);
        this.tail3.addBox(-3.0F, -2.0F, 15.0F, 6, 6, 6, scale + 0.75F);

        this.tail4 = new ModelRenderer(this, 42, 39);
        this.tail4.addBox(-3.0F, -2.0F, 15.0F, 6, 6, 6, scale + 1.0F);

        this.leg1 = new ModelRenderer(this, 0, 68);
        this.leg1.addBox(0.0F, 0.0F, 0.0F, 3, 11, 3, scale);
        this.leg1.setRotationPoint(-5.0F, 12.0F,-7.0F);

        this.leg1Wool1 = new ModelRenderer(this, 0, 68);
        this.leg1Wool1.addBox(0.0F, 0.0F, 0.0F, 3, 7, 3, scale + 0.5F);
        this.leg1Wool1.setRotationPoint(-5.0F, 12.0F,-1.0F);

        this.leg1Wool2 = new ModelRenderer(this, 0, 68);
        this.leg1Wool2.addBox(0.0F, 0.0F, 0.0F, 3, 7, 3, scale + 1.0F);
        this.leg1Wool2.setRotationPoint(-5.0F, 12.0F,-1.0F);

        this.leg1Wool3 = new ModelRenderer(this, 0, 68);
        this.leg1Wool3.addBox(0.0F, 0.0F, 0.0F, 3, 7, 3, scale + 1.5F);
        this.leg1Wool3.setRotationPoint(-5.0F, 12.0F,-1.0F);

        this.leg1Wool4 = new ModelRenderer(this, 0, 68);
        this.leg1Wool4.addBox(0.0F, 0.0F, 0.0F, 3, 7, 3, scale + 2.0F);
        this.leg1Wool4.setRotationPoint(-5.0F, 12.0F,-1.0F);

        this.leg2 = new ModelRenderer(this, 12, 68);
        this.leg2.addBox(0.0F, 0.0F, 0.0F, 3, 11, 3, scale);
        this.leg2.setRotationPoint(2.0F, 12.0F,-1.0F + xMove);

        this.leg2Wool1 = new ModelRenderer(this, 12, 68);
        this.leg2Wool1.addBox(0.0F, 0.0F, 0.0F, 3, 7, 3, scale + 0.5F);
        this.leg2Wool1.setRotationPoint(2.0F, 12.0F,-1.0F);

        this.leg2Wool2 = new ModelRenderer(this, 12, 68);
        this.leg2Wool2.addBox(0.0F, 0.0F, 0.0F, 3, 7, 3, scale + 1.0F);
        this.leg2Wool2.setRotationPoint(2.0F, 12.0F,-1.0F);

        this.leg2Wool3 = new ModelRenderer(this, 12, 68);
        this.leg2Wool3.addBox(0.0F, 0.0F, 0.0F, 3, 7, 3, scale + 1.5F);
        this.leg2Wool3.setRotationPoint(2.0F, 12.0F,-1.0F);

        this.leg2Wool4 = new ModelRenderer(this, 12, 68);
        this.leg2Wool4.addBox(0.0F, 0.0F, 0.0F, 3, 7, 3, scale + 2.0F);
        this.leg2Wool4.setRotationPoint(2.0F, 12.0F,-1.0F);

        this.leg3 = new ModelRenderer(this, 0, 82);
        this.leg3.addBox(0.0F, 0F, 0.0F, 3, 11, 3, scale);
        this.leg3.setRotationPoint(-5.0F, 12.0F,12.0F + xMove);

        this.leg3Wool1 = new ModelRenderer(this, 0, 82);
        this.leg3Wool1.addBox(0.0F, 0.0F, 0.0F, 3, 7, 3, scale + 0.5F);
        this.leg3Wool1.setRotationPoint(-5.0F, 12.0F,12.0F);

        this.leg3Wool2 = new ModelRenderer(this, 0, 82);
        this.leg3Wool2.addBox(0.0F, 0.0F, 0.0F, 3, 7, 3, scale + 1.0F);
        this.leg3Wool2.setRotationPoint(-5.0F, 12.0F,12.0F);

        this.leg3Wool3 = new ModelRenderer(this, 0, 82);
        this.leg3Wool3.addBox(0.0F, 0.0F, 0.0F, 3, 7, 3, scale + 1.5F);
        this.leg3Wool3.setRotationPoint(-5.0F, 12.0F,12.0F);

        this.leg3Wool4 = new ModelRenderer(this, 0, 82);
        this.leg3Wool4.addBox(0.0F, 0.0F, 0.0F, 3, 7, 3, scale + 2.0F);
        this.leg3Wool4.setRotationPoint(-5.0F, 12.0F,12.0F);

        this.leg4 = new ModelRenderer(this, 12, 82);
        this.leg4.addBox(0.0F, 0.0F, 0.0F, 3, 11, 3, scale);
        this.leg4.setRotationPoint(2.0F, 12.0F,12.0F + xMove);

        this.leg4Wool1 = new ModelRenderer(this, 12, 82);
        this.leg4Wool1.addBox(0.0F, 0.0F, 0.0F, 3, 7, 3, scale + 0.5F);
        this.leg4Wool1.setRotationPoint(2.0F, 12.0F,12.0F);

        this.leg4Wool2 = new ModelRenderer(this, 12, 82);
        this.leg4Wool2.addBox(0.0F, 0.0F, 0.0F, 3, 7, 3, scale + 1.0F);
        this.leg4Wool2.setRotationPoint(2.0F, 12.0F,12.0F);

        this.leg4Wool3 = new ModelRenderer(this, 12, 82);
        this.leg4Wool3.addBox(0.0F, 0.0F, 0.0F, 3, 7, 3, scale + 1.5F);
        this.leg4Wool3.setRotationPoint(2.0F, 12.0F,12.0F);

        this.leg4Wool4 = new ModelRenderer(this, 12, 82);
        this.leg4Wool4.addBox(0.0F, 0.0F, 0.0F, 3, 7, 3, scale + 2.0F);
        this.leg4Wool4.setRotationPoint(2.0F, 12.0F,12.0F);

        this.toeOuterFrontR = new ModelRenderer(this, 26, 70);
        this.toeOuterFrontR.addBox(-0.85F, 10.0F, -2.5F, 3, 3, 4, scale - 0.75F);
        this.toeInnerFrontR = new ModelRenderer(this, 44, 70);
        this.toeInnerFrontR.addBox(0.75F, 10.0F, -2.5F, 3, 3, 4, scale - 0.75F);

        this.toeOuterFrontL = new ModelRenderer(this, 62, 70);
        this.toeOuterFrontL.addBox(0.85F, 10.0F, -2.5F, 3, 3, 4, scale - 0.75F);
        this.toeInnerFrontL = new ModelRenderer(this, 80, 70);
        this.toeInnerFrontL.addBox(-0.75F, 10.0F, -2.5F, 3, 3, 4, scale - 0.75F);

        this.toeOuterBackR = new ModelRenderer(this, 26, 84);
        this.toeOuterBackR.addBox(-0.85F, 10.0F, -2.5F, 3, 3, 4, scale - 0.75F);
        this.toeInnerBackR = new ModelRenderer(this, 44, 84);
        this.toeInnerBackR.addBox(0.75F, 10.0F, -2.5F, 3, 3, 4, scale - 0.75F);

        this.toeOuterBackL = new ModelRenderer(this, 62, 84);
        this.toeOuterBackL.addBox(0.85F, 10.0F, -2.5F, 3, 3, 4, scale - 0.75F);
        this.toeInnerBackL = new ModelRenderer(this, 80, 84);
        this.toeInnerBackL.addBox(-0.75F, 10.0F, -2.5F, 3, 3, 4, scale - 0.75F);

        this.eyeLeft = new ModelRenderer(this, 22, 3);
        this.eyeLeft.addBox(0.0F, 0.0F, 0.0F, 2, 2, 1, 0.01F);
        this.eyeLeft.setRotationPoint(2.0F, -1.0F, -3.0F);

        this.eyeRight = new ModelRenderer(this, 0, 3);
        this.eyeRight.addBox(0.0F, 0.0F, 0.0F, 2, 2, 1, 0.01F);
        this.eyeRight.setRotationPoint(-4.0F, -1.0F, -3.0F);

//        leg1.addChild(toeOuterFrontR);
//        leg1.addChild(toeInnerFrontR);
//        leg2.addChild(toeOuterFrontL);
//        leg2.addChild(toeInnerFrontL);
//        leg3.addChild(toeOuterBackR);
//        leg3.addChild(toeInnerBackR);
//        leg4.addChild(toeOuterBackL);
//        leg4.addChild(toeInnerBackL);

//        head.addChild(nose);
        this.neckBone.addChild(head);
        this.head.addChild(nose);
        this.head.addChild(earsL);
        this.head.addChild(earsR);
        this.head.addChild(earsTopL);
        this.head.addChild(earsTopR);
        this.head.addChild(earsTopBananaL);
        this.head.addChild(earsTopBananaR);
        this.head.addChild(eyeLeft);
        this.head.addChild(eyeRight);

        /**
         * Equipment stuff
         */

        this.saddle = new EnhancedRendererModelNew(this, 0, 0, "Saddle");

        this.saddleWestern = new EnhancedRendererModelNew(this, 146, 0, "WesternSaddle");
        this.saddleWestern.addBox(-5.0F, -2.0F, -5.0F, 10, 2, 13, 0.0F);
        this.saddleWestern.setTextureOffset(146, 15);
        this.saddleWestern.addBox(-4.0F, -3.0F, 5.0F, 8, 2, 4, 0.0F);
        this.saddleWestern.setTextureOffset(222, 15);
        this.saddleWestern.addBox(-3.5F, -4.0F, 8.0F, 7, 2, 2, 0.0F);

        this.saddleEnglish = new EnhancedRendererModelNew(this, 147, 1, "EnglishSaddle");
        this.saddleEnglish.addBox(-5.0F, -1.0F, -4.0F, 10, 2, 12, 0.0F);
        this.saddleEnglish.setTextureOffset(146, 15);
        this.saddleEnglish.addBox(-4.0F, -1.5F, 5.0F, 8, 2, 4, 0.0F);
        this.saddleEnglish.setTextureOffset(222, 15);
        this.saddleEnglish.addBox(-3.5F, -2.0F, 7.5F, 7, 2, 2, 0.0F);

        this.saddleHorn = new EnhancedRendererModelNew(this, 170, 19, "SaddleHorn");
        this.saddleHorn.addBox(-4.0F, -2.0F, -3.0F, 8, 2, 3, 0.0F);

        this.saddlePomel = new EnhancedRendererModelNew(this, 179, 0, "SaddlePomel");
        this.saddlePomel.addBox(-1.0F, -3.0F, -2.0F, 2, 4, 2, -0.25F);
        this.saddlePomel.setRotationPoint(0.0F, -2.0F, -2.0F);

        this.saddleSideL = new EnhancedRendererModelNew(this, 170, 49, "SaddleLeft");
        this.saddleSideL.addBox(0.0F, 0.0F, 0.0F, 3, 4, 8);

        this.saddleSideR = new EnhancedRendererModelNew(this, 170, 61, "SaddleRight");
        this.saddleSideR.addBox(-3.0F, 0.0F, 0.0F, 3, 4, 8);

        this.stirrup2DWideL = new EnhancedRendererModelNew(this, 184, 24, "2DStirrupL");
        this.stirrup2DWideL.addBox(0.0F, 0.0F, 0.0F, 0, 10, 4); // strap

        this.stirrup2DWideR = new EnhancedRendererModelNew(this, 184, 24, "2DStirrupR");
        this.stirrup2DWideR.addBox(0.0F, 0.0F, 0.0F, 0, 10, 4); // strap

        this.stirrup3DNarrowL = new EnhancedRendererModelNew(this, 185, 27, "3DStirrupL");
        this.stirrup3DNarrowL.addBox(-1.0F, 0.0F, 0.0F, 1, 10, 1); // strap

        this.stirrup3DNarrowR = new EnhancedRendererModelNew(this, 187, 27, "3DStirrupR");
        this.stirrup3DNarrowR.addBox(0.0F, 0.0F, 0.0F, 1, 10, 1);

        this.stirrup = new EnhancedRendererModelNew(this, 146, 0, "Stirrup");
        this.stirrup.addBox(-0.5F, 9.5F, -1.0F, 1, 1, 1);
        this.stirrup.setTextureOffset(150, 0);
        this.stirrup.addBox(-0.5F, 9.5F, 1.0F, 1, 1, 1);
        this.stirrup.setTextureOffset(146, 2);
        this.stirrup.addBox(-0.5F, 10.5F, -1.5F, 1, 3, 1);
        this.stirrup.setTextureOffset(150, 2);
        this.stirrup.addBox(-0.5F, 10.5F, 1.5F, 1, 3, 1);
        this.stirrup.setTextureOffset(147, 7);
        this.stirrup.addBox(-0.5F, 12.5F, -0.5F, 1, 1, 2);

        this.saddlePad = new EnhancedRendererModelNew(this, 130, 24, "SaddlePad");
        this.saddlePad.addBox(-8.0F, -1.0F, -6.0F, 16, 10, 15, -1.0F);

        this.body.addChild(saddle);
        this.saddle.addChild(saddlePad);
        this.saddle.addChild(stirrup3DNarrowL);
        this.saddle.addChild(stirrup3DNarrowR);
        this.saddleHorn.addChild(saddlePomel);
        this.saddlePad.addChild(saddleSideL);
        this.saddlePad.addChild(saddleSideR);

        //western
        this.body.addChild(saddleWestern);
        this.saddleWestern.addChild(saddleHorn);
        this.saddleWestern.addChild(saddlePad);
        this.saddleWestern.addChild(stirrup2DWideL);
        this.saddleWestern.addChild(stirrup2DWideR);
        this.stirrup2DWideL.addChild(stirrup);
        this.stirrup2DWideR.addChild(stirrup);
        //english
        this.body.addChild(saddleEnglish);
        this.saddleEnglish.addChild(saddleHorn);
        this.saddleEnglish.addChild(saddlePad);
        this.saddleEnglish.addChild(stirrup3DNarrowL);
        this.saddleEnglish.addChild(stirrup3DNarrowR);
        this.stirrup3DNarrowL.addChild(stirrup);
        this.stirrup3DNarrowR.addChild(stirrup);
    }

    @Override
    public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        LlamaModelData llamaModelData = getLlamaModelData();

        int[] genes = llamaModelData.llamaGenes;
        int coatlength = llamaModelData.coatlength;
        int maxCoatlength = llamaModelData.maxCoatlength;
        boolean sleeping = llamaModelData.sleeping;

        float size = 1;

        if (genes != null) {
            if (genes[0] < 3) {
                size = size - 0.025F;
                if (genes[0] < 2) {
                    size = size - 0.025F;
                }
            }
            if (genes[1] < 3) {
                size = size - 0.025F;
                if (genes[1] < 2) {
                    size = size - 0.025F;
                }
            }
            if (genes[2] < 3) {
                size = size - 0.025F;
                if (genes[2] < 2) {
                    size = size - 0.025F;
                }
            }
            if (genes[3] < 3) {
                size = size - 0.025F;
                if (genes[3] < 2) {
                    size = size - 0.025F;
                }
            }

            // banana ears
            if (genes[18] != 1 && genes[19] != 1) {
                if (genes[18] == 2 || genes[19] == 2) {
                    banana = true;
                }
            }

            if (genes[20] == 2 && genes[21] == 2) {
                suri = true;
            }
        }

        float age = 1.0F;
        if (!(llamaModelData.birthTime == null) && !llamaModelData.birthTime.equals("") && !llamaModelData.birthTime.equals("0")) {
            int ageTime = (int)(llamaModelData.clientGameTime - Long.parseLong(llamaModelData.birthTime));
            if (ageTime <= 80000) {
                age = ageTime/80000.0F;
            }
        }

        float finalLlamaSize = (( 2.0F * size * age) + size) / 3.0F;
        float babyScale = 1.0F;
        float d = 0.0F;
        if (!sleeping) {
            babyScale = (2.0F - age)/1;
            d = 0.5F * (1.0F-age);
        }

        matrixStackIn.push();
        matrixStackIn.scale(finalLlamaSize, finalLlamaSize, finalLlamaSize);
        matrixStackIn.translate(0.0F, (-1.5F + 1.5F / finalLlamaSize) - d, 0.0F);

            this.neckBone.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);

            if (banana){
                this.earsTopR.showModel = false;
                this.earsTopL.showModel = false;
            }else {
                this.earsTopBananaR.showModel = false;
                this.earsTopBananaL.showModel = false;
            }

            //controlled by flag in rotationAngles
            this.chest1.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            this.chest2.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);

                if (coatlength == 0) {
                    ImmutableList.of(this.neckWool0, this.body).forEach((renderer) -> {
                        renderer.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                    });
                } else if (coatlength == 1 ) {
                    ImmutableList.of(this.neckWool1, this.body1).forEach((renderer) -> {
                        renderer.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                    });
                } else if (coatlength == 2 ) {
                    ImmutableList.of(this.neckWool2, this.body2).forEach((renderer) -> {
                        renderer.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                    });
                } else if (coatlength == 3 ) {
                    ImmutableList.of(this.neckWool3, this.body3).forEach((renderer) -> {
                        renderer.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                    });
                } else if (coatlength == 4 ) {
                    ImmutableList.of(this.neckWool4, this.body4).forEach((renderer) -> {
                        renderer.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                    });
                } else {
                    ImmutableList.of(this.neck, this.bodyShaved).forEach((renderer) -> {
                        renderer.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                    });
                }

                if (maxCoatlength <= 0 || this.isChild) {
                    this.tail.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                } else if (maxCoatlength == 1 ) {
                    ImmutableList.of(this.tail1, leg1Wool1, leg2Wool1, leg3Wool1, leg4Wool1).forEach((renderer) -> {
                        renderer.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                    });
                } else if (maxCoatlength == 2 ) {
                    ImmutableList.of(this.tail2, leg1Wool2, leg2Wool2, leg3Wool2, leg4Wool2).forEach((renderer) -> {
                        renderer.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                    });
                } else if (maxCoatlength == 3 ) {
                    ImmutableList.of(this.tail3, leg1Wool3, leg2Wool3, leg3Wool3, leg4Wool3).forEach((renderer) -> {
                        renderer.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                    });
                } else {
                    ImmutableList.of(this.tail4, leg1Wool4, leg2Wool4, leg3Wool4, leg4Wool4).forEach((renderer) -> {
                        renderer.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                    });
                }

                renderSaddle(coatlength, llamaModelData.unrenderedModels, matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);

        matrixStackIn.pop();

        matrixStackIn.push();
        matrixStackIn.scale(finalLlamaSize, finalLlamaSize * babyScale, finalLlamaSize);
        matrixStackIn.translate(0.0F, -1.5F + 1.5F / (finalLlamaSize * babyScale), 0.0F);

        ImmutableList.of(this.leg1, this.leg2, this.leg3, this.leg4).forEach((renderer) -> {
            renderer.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        });
            if (!sleeping) {
                ImmutableList.of(this.toeOuterFrontR, this.toeInnerFrontR, this.toeOuterFrontL, this.toeInnerFrontL, this.toeOuterBackR, this.toeInnerBackR, this.toeOuterBackL, this.toeInnerBackL).forEach((renderer) -> {
                    renderer.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                });
            }

        matrixStackIn.pop();

            if (sleeping) {
                this.eyeLeft.showModel = false;
                this.eyeRight.showModel = false;
            } else {
                this.eyeLeft.showModel = true;
                this.eyeRight.showModel = true;
            }
    }

    private void renderSaddle(int coatLength, List<String> unrenderedModels, MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        Map<String, List<Float>> mapOfScale = new HashMap<>();

        this.saddleWestern.showModel = false;
        this.saddleEnglish.showModel = false;
        this.saddle.showModel = false;
        this.saddlePomel.showModel = false;
        this.saddleSideL.showModel = true;
        this.saddleSideR.showModel = true;

        float coatMod = 1.0F;

        if (coatLength != 0) {
            if (coatLength == -1) {
                coatMod = 0.875F;
            } else if (coatLength == 1) {
                coatMod = 1.0625F;
            } else if (coatLength == 2) {
                coatMod = 1.125F;
            } else if (coatLength == 3) {
                coatMod = 1.25F;
            } else {
                coatMod = 1.375F;
            }
        }

        if (saddleType != "") {
            float saddleScale = 0.875F;
            List<Float> scalingsForSaddle = ModelHelper.createScalings(saddleScale, saddleScale, saddleScale, 0.0F, -saddleScale*0.01F, (saddleScale - 1.0F)*0.04F);
            List<Float> scalingsForPad = ModelHelper.createScalings(coatMod, saddleScale, saddleScale, 0.0F, -saddleScale*0.01F, (saddleScale - 1.0F)*0.04F);

            if (saddleType.equals("western")) {
                this.saddleWestern.showModel = true;
                this.saddlePomel.showModel = true;
                mapOfScale.put("WesternSaddle", scalingsForSaddle);
                mapOfScale.put("SaddlePad", scalingsForPad);
                this.saddleWestern.render(matrixStackIn, bufferIn , mapOfScale, unrenderedModels, false, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            } else if (saddleType.equals("english")) {
                this.saddleEnglish.showModel = true;
                mapOfScale.put("EnglishSaddle", scalingsForSaddle);
                mapOfScale.put("SaddlePad", scalingsForPad);
                this.saddleEnglish.render(matrixStackIn, bufferIn , mapOfScale, unrenderedModels, false, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            } else {
                this.saddle.showModel = true;
                mapOfScale.put("Saddle", scalingsForSaddle);
                mapOfScale.put("SaddlePad", scalingsForPad);
                    this.saddleSideL.showModel = false;
                    this.saddleSideR.showModel = false;
                this.saddle.render(matrixStackIn, bufferIn , mapOfScale, unrenderedModels, false, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            }
        }
    }

        @Override
    public void setLivingAnimations(T entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTickTime) {
        LlamaModelData llamaModelData = getCreateLlamaModelData(entitylivingbaseIn);
        this.currentLlama = entitylivingbaseIn.getEntityId();

        boolean sleeping = llamaModelData.sleeping;
        float onGround;

        this.body.rotationPointY = 2.0F;

        if (sleeping) {
            onGround = sleepingAnimation(isChild);
        } else {
            onGround = standingAnimation();
        }

        this.neck.rotationPointY = onGround;
    }

    @Override
    public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.neck.rotateAngleY = netHeadYaw * 0.017453292F;

        //TODO if llama is angry turn ears back
        //TODO stop llama legs from being sheared

        LlamaModelData llamaModelData = getLlamaModelData();

        int[] sharedGenes = llamaModelData.llamaGenes;

        boolean flag = !entityIn.isChild() && entityIn.hasChest();
        this.chest1.showModel = flag;
        this.chest2.showModel = flag;

        if (!llamaModelData.sleeping) {
            this.neck.rotateAngleX = headPitch * 0.017453292F;
            this.leg1.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
            this.leg2.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
            this.leg3.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
            this.leg4.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        }

        ModelHelper.copyModelAngles(neck, neckWool0);
        ModelHelper.copyModelAngles(neck, neckWool1);

        ModelHelper.copyModelAngles(body, bodyShaved);
        ModelHelper.copyModelAngles(body, body1);

        ModelHelper.copyModelAngles(leg1, leg1Wool1);
        ModelHelper.copyModelAngles(leg2, leg2Wool1);
        ModelHelper.copyModelAngles(leg3, leg3Wool1);
        ModelHelper.copyModelAngles(leg4, leg4Wool1);

        ModelHelper.copyModelAngles(body, tail);
        ModelHelper.copyModelAngles(body, tail1);

        if ( suri && llamaModelData.coatlength >= 0 ) {
            if (llamaModelData.coatlength >= 1) {
                this.neckWool1.rotationPointY = this.neck.rotationPointY + (llamaModelData.coatlength/2.0F);
                this.body1.rotationPointY = this.body.rotationPointY + (llamaModelData.coatlength/2.0F);
                this.tail1.rotationPointY = this.body.rotationPointY + (llamaModelData.coatlength/3.0F);
                this.leg1Wool1.rotationPointY = this.leg1.rotationPointY + (llamaModelData.coatlength/2.0F);
                this.leg2Wool1.rotationPointY = this.leg2.rotationPointY + (llamaModelData.coatlength/2.0F);
                this.leg3Wool1.rotationPointY = this.leg3.rotationPointY + (llamaModelData.coatlength/2.0F);
                this.leg4Wool1.rotationPointY = this.leg4.rotationPointY + (llamaModelData.coatlength/2.0F);
            }
        }
        ModelHelper.copyModelAngles(neck, neckBone);
        ModelHelper.copyModelAngles(neck, neckWool0);
        ModelHelper.copyModelAngles(neck, neckWool1);
        ModelHelper.copyModelAngles(neckWool1, neckWool2);
        ModelHelper.copyModelAngles(neckWool1, neckWool3);
        ModelHelper.copyModelAngles(neckWool1, neckWool4);

//        copyModelAngles(head, earsR);
//        copyModelAngles(head, earsL);
//        copyModelAngles(head, earsTopR);
//        copyModelAngles(head, earsTopL);
//        copyModelAngles(head, earsTopBananaR);
//        copyModelAngles(head, earsTopBananaL);

        ModelHelper.copyModelAngles(body, body2);
        ModelHelper.copyModelAngles(body, body3);
        ModelHelper.copyModelAngles(body, body4);

        ModelHelper.copyModelAngles(tail1, tail2);
        ModelHelper.copyModelAngles(tail1, tail3);
        ModelHelper.copyModelAngles(tail1, tail4);

        //TODO fix the toes so they angle properly and maintain the angle while they walk

        ModelHelper.copyModelAngles(leg1Wool1, leg1Wool2);
        ModelHelper.copyModelAngles(leg1Wool1, leg1Wool3);
        ModelHelper.copyModelAngles(leg1Wool1, leg1Wool4);
        ModelHelper.copyModelAngles(leg1, toeOuterFrontR);
        ModelHelper.copyModelAngles(leg1, toeInnerFrontR);


        ModelHelper.copyModelAngles(leg2Wool1, leg2Wool2);
        ModelHelper.copyModelAngles(leg2Wool1, leg2Wool3);
        ModelHelper.copyModelAngles(leg2Wool1, leg2Wool4);
        ModelHelper.copyModelAngles(leg2, toeOuterFrontL);
        ModelHelper.copyModelAngles(leg2, toeInnerFrontL);

        ModelHelper.copyModelAngles(leg3Wool1, leg3Wool2);
        ModelHelper.copyModelAngles(leg3Wool1, leg3Wool3);
        ModelHelper.copyModelAngles(leg3Wool1, leg3Wool4);
        ModelHelper.copyModelAngles(leg3, toeOuterBackR);
        ModelHelper.copyModelAngles(leg3, toeInnerBackR);

        ModelHelper.copyModelAngles(leg4Wool1, leg4Wool2);
        ModelHelper.copyModelAngles(leg4Wool1, leg4Wool3);
        ModelHelper.copyModelAngles(leg4Wool1, leg4Wool4);
        ModelHelper.copyModelAngles(leg4, toeOuterBackL);
        ModelHelper.copyModelAngles(leg4, toeInnerBackL);

        setNoseRotations(sharedGenes, llamaModelData);

        float coatLength = (float)llamaModelData.coatlength;
        if (coatLength >= 1) {
            coatLength = coatLength/1.825F;
        } else if (coatLength == -1) {
            coatLength = -1.25F;
        }
            if (saddleType != "") {
                if (saddleType.equals("western")) {
                    this.saddleWestern.rotationPointY = 2.0F - coatLength;
                    this.saddleSideL.setRotationPoint(4.75F, -1.0F, -5.25F);
                    this.saddleSideR.setRotationPoint(-4.75F, -1.0F, -5.25F);
                    this.saddleHorn.setRotationPoint(0.0F, -2.0F, -2.0F);
                    this.saddleHorn.rotateAngleX = (float) Math.PI / 8.0F;
                    this.saddlePomel.setRotationPoint(0.0F, -1.5F, -0.5F);
                    this.saddlePomel.rotateAngleX = -0.2F;
                    this.stirrup2DWideL.setRotationPoint(7.5F + coatLength, 0.0F, -3.5F);
                    this.stirrup2DWideR.setRotationPoint(-7.5F - coatLength, 0.0F, -3.5F);
                } else if (saddleType.equals("english")) {
                    this.saddleEnglish.rotationPointY = 2.0F - coatLength;
                    this.saddleSideL.setRotationPoint(3.25F, -0.5F, -4.0F);
                    this.saddleSideR.setRotationPoint(-3.25F, -0.5F, -4.0F);
                    this.saddleHorn.setRotationPoint(0.0F, -1.0F, -1.0F);
                    this.saddleHorn.rotateAngleX = (float) Math.PI / 4.5F;
                    this.stirrup3DNarrowL.setRotationPoint(7.25F + coatLength, -0.25F, -1.5F);
                    this.stirrup3DNarrowR.setRotationPoint(-7.25F - coatLength, -0.25F, -1.5F);
                } else {
                    this.saddle.rotationPointY = 2.0F - coatLength;
                    this.stirrup3DNarrowL.setRotationPoint(7.5F + coatLength, 0.0F, 0.0F);
                    this.stirrup3DNarrowR.setRotationPoint(-7.5F - coatLength, 0.0F, 0.0F);
                }
            }
    }

    private void setNoseRotations(int[] sharedGenes, LlamaModelData llamaModelData) {

        float age = 1.0F;
        if (!(llamaModelData.birthTime == null) && !llamaModelData.birthTime.equals("") && !llamaModelData.birthTime.equals("0")) {
            int ageTime = (int)(llamaModelData.clientGameTime - Long.parseLong(llamaModelData.birthTime));
            if (ageTime <= 80000) {
                age = ageTime/80000.0F;
            }
        }

        this.nose.rotationPointZ = 1.5F - (age*1.5F);

        //range from -12.1 to 10.5
        float noseHeight = 0F;
        if (sharedGenes != null) {
            if (sharedGenes[28] == 1) {
                noseHeight = 0.1F;
            } else if (sharedGenes[28] == 2) {
                noseHeight = 0.15F;
            } else if (sharedGenes[28] == 3) {
                noseHeight = 0.0F;
            } else {
                noseHeight = -0.1F;
            }

            if (sharedGenes[29] == 1) {
                noseHeight = noseHeight + 0.1F;
            } else if (sharedGenes[28] == 2) {
                noseHeight = noseHeight + 0.05F;
            } else if (sharedGenes[28] == 3) {
                noseHeight = noseHeight + 0.0F;
            } else {
                noseHeight = noseHeight - 0.1F;
            }

            if (sharedGenes[30] == 1) {
                noseHeight = noseHeight + 0.1F;
            } else if (sharedGenes[30] == 2) {
                noseHeight = noseHeight + 0.15F;
            } else if (sharedGenes[30] == 3) {
                noseHeight = noseHeight + 0.0F;
            } else {
                noseHeight = noseHeight - 0.1F;
            }

            if (sharedGenes[31] == 1) {
                noseHeight = noseHeight + 0.1F;
            } else if (sharedGenes[31] == 2) {
                noseHeight = noseHeight + 0.05F;
            } else if (sharedGenes[31] == 3) {
                noseHeight = noseHeight + 0.0F;
            } else {
                noseHeight = noseHeight - 0.1F;
            }

            if (sharedGenes[32] == 1) {
                noseHeight = noseHeight + 0.2F;
            } else if (sharedGenes[32] == 2) {
                noseHeight = noseHeight + 0.15F;
            } else if (sharedGenes[32] == 3) {
                noseHeight = noseHeight + 0.0F;
            } else if (sharedGenes[32] == 4) {
                noseHeight = noseHeight - 0.15F;
            } else {
                noseHeight = noseHeight - 0.2F;
            }

            if (sharedGenes[33] == 1) {
                noseHeight = noseHeight + 0.2F;
            } else if (sharedGenes[33] == 2) {
                noseHeight = noseHeight + 0.15F;
            } else if (sharedGenes[33] == 3) {
                noseHeight = noseHeight + 0.0F;
            } else if (sharedGenes[33] == 4) {
                noseHeight = noseHeight - 0.15F;
            } else {
                noseHeight = noseHeight - 0.2F;
            }
        }

        this.nose.rotationPointY = -0.3F - noseHeight;
    }

    private float sleepingAnimation(boolean isChild) {
        float onGround;

        if (isChild) {
            onGround = 20.0F;
            this.body.rotationPointY = 14.0F;
        } else {
            onGround = 20.0F;
            this.body.rotationPointY = 11.0F;
        }

        this.neck.rotateAngleX = 1.6F;
        this.neck.rotationPointZ = -9.0F;

        this.head.rotateAngleX = -1.6F;

        this.leg1.rotateAngleX = 1.575F;
        this.leg1.rotateAngleY = 0.2F;
        this.leg2.rotateAngleX = 1.575F;
        this.leg2.rotateAngleY = -0.2F;
        this.leg3.rotateAngleX = -1.575F;
        this.leg4.rotateAngleX = -1.575F;

        this.chest1.rotationPointY = 12.0F;
        this.chest2.rotationPointY = 12.0F;

        this.leg1.setRotationPoint(-5.0F, 24.0F, -10.0F);
        this.leg2.setRotationPoint(2.0F, 24.0F, -10.0F);
        this.leg3.setRotationPoint(-5.0F, 21.0F, 10.0F);
        this.leg4.setRotationPoint(2.0F, 21.0F, 10.0F);
        return onGround;
    }

    private float standingAnimation() {
        float onGround;
        onGround = 5.0F;

        this.body.rotationPointY = 2.0F;
        this.leg1.rotateAngleY = 0.0F;
        this.leg3.rotateAngleY = 0.0F;

        this.neck.rotationPointZ = -10.0F;

        this.head.rotateAngleX = 0.0F;

        this.chest1.rotationPointY = 3.0F;
        this.chest2.rotationPointY = 3.0F;

        if (this.getLlamaModelData().angry) {
            this.earsR.rotateAngleY = (float)Math.PI/4.0F;
        }

        this.leg1.setRotationPoint(-5.0F, 12.0F,-7.0F);
        this.leg2.setRotationPoint(2.0F, 12.0F,-7.0F);
        this.leg3.setRotationPoint(-5.0F, 12.0F,6.0F);
        this.leg4.setRotationPoint(2.0F, 12.0F,6.0F);

        return onGround;
    }

    private class LlamaModelData {
        int[] llamaGenes;
        int coatlength = 0;
        int maxCoatlength = 0;
        String birthTime;
        boolean sleeping = false;
        int lastAccessed = 0;
        boolean angry = false;
        long clientGameTime = 0;
//        int dataReset = 0;
        List<String> unrenderedModels = new ArrayList<>();
    }

    private LlamaModelData getLlamaModelData() {
        if (this.currentLlama == null || !llamaModelDataCache.containsKey(this.currentLlama)) {
            return new LlamaModelData();
        }
        return llamaModelDataCache.get(this.currentLlama);
    }

    private LlamaModelData getCreateLlamaModelData(T enhancedLlama) {
        clearCacheTimer++;
        if(clearCacheTimer > 50000) {
            llamaModelDataCache.values().removeIf(value -> value.lastAccessed==1);
            for (LlamaModelData llamaModelData : llamaModelDataCache.values()){
                llamaModelData.lastAccessed = 1;
            }
            clearCacheTimer = 0;
        }

        if (llamaModelDataCache.containsKey(enhancedLlama.getEntityId())) {
            LlamaModelData llamaModelData = llamaModelDataCache.get(enhancedLlama.getEntityId());
            llamaModelData.lastAccessed = 0;
//            llamaModelData.dataReset++;
//            if (llamaModelData.dataReset > 5000) {
//                llamaModelData.dataReset = 0;
//            }
            llamaModelData.coatlength = enhancedLlama.getCoatLength();
            llamaModelData.sleeping = enhancedLlama.isAnimalSleeping();
            llamaModelData.angry = (enhancedLlama.isAggressive());
            llamaModelData.clientGameTime = (((WorldInfo)((ClientWorld)enhancedLlama.world).getWorldInfo()).getGameTime());
            llamaModelData.unrenderedModels = new ArrayList<>();

            return llamaModelData;
        } else {
            LlamaModelData llamaModelData = new LlamaModelData();
            llamaModelData.llamaGenes = enhancedLlama.getSharedGenes();
            llamaModelData.coatlength = enhancedLlama.getCoatLength();
            llamaModelData.maxCoatlength = enhancedLlama.getCoatLength();
            llamaModelData.sleeping = enhancedLlama.isAnimalSleeping();
            llamaModelData.birthTime = enhancedLlama.getBirthTime();
            llamaModelData.angry = (!(enhancedLlama.getRevengeTarget() == null));
            llamaModelData.clientGameTime = (((WorldInfo)((ClientWorld)enhancedLlama.world).getWorldInfo()).getGameTime());

            if(llamaModelData.llamaGenes != null) {
                llamaModelDataCache.put(enhancedLlama.getEntityId(), llamaModelData);
            }

            return llamaModelData;
        }
    }

}
