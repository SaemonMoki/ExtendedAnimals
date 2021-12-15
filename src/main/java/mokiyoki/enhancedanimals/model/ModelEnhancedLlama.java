package mokiyoki.enhancedanimals.model;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import mokiyoki.enhancedanimals.config.EanimodCommonConfig;
import mokiyoki.enhancedanimals.entity.EnhancedLlama;
import mokiyoki.enhancedanimals.items.CustomizableBridle;
import mokiyoki.enhancedanimals.items.CustomizableCollar;
import mokiyoki.enhancedanimals.items.CustomizableSaddleEnglish;
import mokiyoki.enhancedanimals.items.CustomizableSaddleVanilla;
import mokiyoki.enhancedanimals.items.CustomizableSaddleWestern;
import mokiyoki.enhancedanimals.model.util.ModelHelper;
import mokiyoki.enhancedanimals.renderer.EnhancedRendererModelNew;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SaddleItem;
import net.minecraft.util.Mth;
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

    private final ModelPart chest1;
    private final ModelPart chest2;

    private boolean banana = false;
    private boolean suri = false;

    private final EnhancedRendererModelNew nose;
    private final EnhancedRendererModelNew head;
    private final EnhancedRendererModelNew neckBone;
    private final ModelPart neck;
    private final ModelPart neckWool0;
    private final ModelPart neckWool1;
    private final ModelPart neckWool2;
    private final ModelPart neckWool3;
    private final ModelPart neckWool4;
    private final EnhancedRendererModelNew earsR;
    private final EnhancedRendererModelNew earsL;
    private final EnhancedRendererModelNew earsTopR;
    private final EnhancedRendererModelNew earsTopL;
    private final EnhancedRendererModelNew earsTopBananaR;
    private final EnhancedRendererModelNew earsTopBananaL;
    private final ModelPart body;
    private final ModelPart bodyShaved;
    private final ModelPart body1;
    private final ModelPart body2;
    private final ModelPart body3;
    private final ModelPart body4;
    private final ModelPart tail;
    private final ModelPart tail1;
    private final ModelPart tail2;
    private final ModelPart tail3;
    private final ModelPart tail4;
    private final ModelPart leg1;
    private final ModelPart leg1Wool1;
    private final ModelPart leg1Wool2;
    private final ModelPart leg1Wool3;
    private final ModelPart leg1Wool4;
    private final ModelPart leg2;
    private final ModelPart leg2Wool1;
    private final ModelPart leg2Wool2;
    private final ModelPart leg2Wool3;
    private final ModelPart leg2Wool4;
    private final ModelPart leg3;
    private final ModelPart leg3Wool1;
    private final ModelPart leg3Wool2;
    private final ModelPart leg3Wool3;
    private final ModelPart leg3Wool4;
    private final ModelPart leg4;
    private final ModelPart leg4Wool1;
    private final ModelPart leg4Wool2;
    private final ModelPart leg4Wool3;
    private final ModelPart leg4Wool4;
    private final ModelPart toeOuterFrontR;
    private final ModelPart toeInnerFrontR;
    private final ModelPart toeOuterFrontL;
    private final ModelPart toeInnerFrontL;
    private final ModelPart toeOuterBackR;
    private final ModelPart toeInnerBackR;
    private final ModelPart toeOuterBackL;
    private final ModelPart toeInnerBackL;
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
    private final EnhancedRendererModelNew collar;
    private final EnhancedRendererModelNew collarHardware;
    private final EnhancedRendererModelNew bridle;
    private final EnhancedRendererModelNew bridleNose;

    private Integer currentLlama = null;

    public ModelEnhancedLlama(float scale)
    {
        this.texWidth = 256;
        this.texHeight = 256;

        float headAdjust = -2.0F;

        this.nose = new EnhancedRendererModelNew(this,28, 0);
        this.nose.addBox(-2.0F, 0.0F, -7.0F, 4, 4, 4, scale); //nose
        this.nose.setPos(0, 0, 0);

        this.head = new EnhancedRendererModelNew(this, 0, 0);
        this.head.addBox(-4.0F, -3.0F, -3.0F, 8, 6, 6, scale); //head
        this.head.texOffs(28,8);
        this.head.addBox(-4.0F, -3.0F, -3.0F, 8, 10, 6, scale + 0.505F); //deco
        this.head.setPos(0.0F, -11.0F, scale + 1.0F);
//        this.head.setTextureOffset(28, 0);
//        this.head.addBox(-2.0F, -12.0F, -4.0F, 4, 4, 4, 0.0F); //nose

        this.neckBone = new EnhancedRendererModelNew(this, 0, 0, "Neck");
        this.neckBone.setPos(0, 5, -6);

        this.neck = new ModelPart(this,0, 12);
        this.neck.addBox(-4.0F, -9.0F, -1.1F, 8, 12, 6, scale - 1.0F); //neck
        this.neck.texOffs(28,23);
        this.neck.addBox(-4.0F, -2.0F, -1.1F, 8, 10, 6, scale + 0.01F); //deco
        this.neck.setPos(0, 5, -6);

        this.neckWool0 = new ModelPart(this, 0, 12);
        this.neckWool0.addBox(-4.0F, -8.0F, headAdjust, 8, 12, 6, scale + 0.0F); //neck
        this.neckWool0.texOffs(28,23);
        this.neckWool0.addBox(-4.0F, -3.75F, headAdjust, 8, 10, 6, scale + 0.51F); //deco

        this.neckWool1 = new ModelPart(this, 0, 12);
        this.neckWool1.addBox(-4.0F, -8.5F, headAdjust, 8, 12, 6, scale + 0.5F); //neck
        this.neckWool1.texOffs(28,23);
        this.neckWool1.addBox(-4.0F, -2.25F, headAdjust, 8, 10, 6, scale + 0.75F); //deco

        this.neckWool2 = new ModelPart(this, 0, 12);
        this.neckWool2.addBox(-4.0F, -7.5F, headAdjust, 8, 12, 6, scale + 1.0F); //neck
        this.neckWool2.texOffs(28,23);
        this.neckWool2.addBox(-4.0F, -3.7F, headAdjust, 8, 10, 6, scale + 1.2F); //deco

        this.neckWool3 = new ModelPart(this, 0, 12);
        this.neckWool3.addBox(-4.0F, -7.0F, headAdjust, 8, 12, 6, scale + 1.5F); //neck
        this.neckWool3.texOffs(28,23);
        this.neckWool3.addBox(-4.0F, -3.1F, headAdjust, 8, 10, 6, scale + 1.6F); //deco

        this.neckWool4 = new ModelPart(this, 0, 12);
        this.neckWool4.addBox(-4.0F, -6.5F, headAdjust, 8, 12, 6, scale + 2.0F); //neck
        this.neckWool4.texOffs(28,23);
        this.neckWool4.addBox(-4.0F, -2.55F, -2.0F, 8, 10, 6, scale + 2.05F); //deco

        this.earsR = new EnhancedRendererModelNew(this, 44, 0);
        this.earsR.addBox(-4.0F, -6.0F, 0.0F, 3, 3, 2, scale + 0.0F); //ear right

        this.earsL = new EnhancedRendererModelNew(this, 54, 0);
        this.earsL.addBox(1.0F, -6.0F, 0.0F, 3, 3, 2, scale + 0.0F); //ear left

        this.earsTopR = new EnhancedRendererModelNew(this, 64, 0);
        this.earsTopR.addBox(-4.0F, -7.0F, 0.0F, 3, 1, 2, scale + 0.0F); //ear right

        this.earsTopL = new EnhancedRendererModelNew(this, 74, 0);
        this.earsTopL.addBox(1.0F, -7.0F, 0.0F, 3, 1, 2, scale + 0.0F); //ear left

        this.earsTopBananaR = new EnhancedRendererModelNew(this, 64, 0);
        this.earsTopBananaR.addBox(-3.5F, -7.0F, 0.0F, 3, 1, 2, scale + 0.0F); //ear right
        this.earsTopBananaL = new EnhancedRendererModelNew(this, 74, 0);
        this.earsTopBananaL.addBox(0.5F, -7.0F, 0.0F, 3, 1, 2, scale + 0.0F); //ear left

        this.body = new ModelPart(this, 0, 39);
        this.body.addBox(-6.0F, 0.0F, 0.0F, 12, 10, 18, scale);
        this.body.setPos(0.0F, 2.0F, -8.0F);

        this.bodyShaved = new ModelPart(this, 0, 39);
        this.bodyShaved.addBox(-6.0F, 1.0F, 0.0F, 12, 10, 18, scale - 1.0F);
        this.bodyShaved.setPos(0.0F, 2.0F, -2.0F);

        this.body1 = new ModelPart(this, 0, 39);
        this.body1.addBox(-6.0F, 0.0F, 0.0F, 12, 10, 18, scale + 0.5F);
        this.body1.setPos(0.0F, 2.0F, -2.0F);

        this.body2 = new ModelPart(this, 0, 39);
        this.body2.addBox(-6.0F, 0.0F, 0.0F, 12, 10, 18, scale + 1.0F);
        this.body2.setPos(0.0F, 2.0F, -2.0F);

        this.body3 = new ModelPart(this, 0, 39);
        this.body3.addBox(-6.0F, 0.0F, 0.0F, 12, 10, 18, scale + 1.5F);
        this.body3.setPos(0.0F, 2.0F, -2.0F);

        this.body4 = new ModelPart(this, 0, 39);
        this.body4.addBox(-6.0F, 0.0F, 0.0F, 12, 10, 18, scale + 2.0F);
        this.body4.setPos(0.0F, 2.0F, -2.0F);

        this.chest1 = new ModelPart(this, 74, 44);
        this.chest1.addBox(-3.0F, 0.0F, 0.0F, 8, 8, 3, scale);
        this.chest1.setPos(-8.5F, 3.0F, 4.0F);
        this.chest1.yRot = ((float)Math.PI / 2F);
        this.chest2 = new ModelPart(this, 74, 57);
        this.chest2.addBox(-3.0F, 0.0F, 0.0F, 8, 8, 3, scale);
        this.chest2.setPos(5.5F, 3.0F, 4.0F);
        this.chest2.yRot = ((float)Math.PI / 2F);

        this.tail = new ModelPart(this, 42, 39);
        this.tail.addBox(-3.0F, -2.0F, 15.0F, 6, 6, 6, scale);

        this.tail1 = new ModelPart(this, 42, 39);
        this.tail1.addBox(-3.0F, -1.75F, 15.25F, 6, 6, 6, scale + 0.25F);

        this.tail2 = new ModelPart(this, 42, 39);
        this.tail2.addBox(-3.0F, -1.5F, 15.5F, 6, 6, 6, scale + 0.5F);

        this.tail3 = new ModelPart(this, 42, 39);
        this.tail3.addBox(-3.0F, -1.25F, 15.75F, 6, 6, 6, scale + 0.75F);

        this.tail4 = new ModelPart(this, 42, 39);
        this.tail4.addBox(-3.0F, -0.75F, 16.25F, 6, 6, 6, scale + 1.25F);

        this.leg1 = new ModelPart(this, 0, 68);
        this.leg1.addBox(0.0F, 0.0F, 0.0F, 3, 11, 3, scale);
        this.leg1.setPos(-5.0F, 12.0F,-7.0F);

        this.leg1Wool1 = new ModelPart(this, 0, 68);
        this.leg1Wool1.addBox(0.0F, 0.0F, 0.0F, 3, 7, 3, scale + 0.5F);
        this.leg1Wool1.setPos(-5.0F, 12.0F,-1.0F);

        this.leg1Wool2 = new ModelPart(this, 0, 68);
        this.leg1Wool2.addBox(0.0F, 0.0F, 0.0F, 3, 7, 3, scale + 1.0F);
        this.leg1Wool2.setPos(-5.0F, 12.0F,-1.0F);

        this.leg1Wool3 = new ModelPart(this, 0, 68);
        this.leg1Wool3.addBox(0.0F, 0.0F, 0.0F, 3, 7, 3, scale + 1.5F);
        this.leg1Wool3.setPos(-5.0F, 12.0F,-1.0F);

        this.leg1Wool4 = new ModelPart(this, 0, 68);
        this.leg1Wool4.addBox(0.0F, 0.0F, 0.0F, 3, 7, 3, scale + 2.0F);
        this.leg1Wool4.setPos(-5.0F, 12.0F,-1.0F);

        this.leg2 = new ModelPart(this, 12, 68);
        this.leg2.addBox(0.0F, 0.0F, 0.0F, 3, 11, 3, scale);
        this.leg2.setPos(2.0F, 12.0F,-7.0F);

        this.leg2Wool1 = new ModelPart(this, 12, 68);
        this.leg2Wool1.addBox(0.0F, 0.0F, 0.0F, 3, 7, 3, scale + 0.5F);
        this.leg2Wool1.setPos(2.0F, 12.0F,-1.0F);

        this.leg2Wool2 = new ModelPart(this, 12, 68);
        this.leg2Wool2.addBox(0.0F, 0.0F, 0.0F, 3, 7, 3, scale + 1.0F);
        this.leg2Wool2.setPos(2.0F, 12.0F,-1.0F);

        this.leg2Wool3 = new ModelPart(this, 12, 68);
        this.leg2Wool3.addBox(0.0F, 0.0F, 0.0F, 3, 7, 3, scale + 1.5F);
        this.leg2Wool3.setPos(2.0F, 12.0F,-1.0F);

        this.leg2Wool4 = new ModelPart(this, 12, 68);
        this.leg2Wool4.addBox(0.0F, 0.0F, 0.0F, 3, 7, 3, scale + 2.0F);
        this.leg2Wool4.setPos(2.0F, 12.0F,-1.0F);

        this.leg3 = new ModelPart(this, 0, 82);
        this.leg3.addBox(0.0F, 0F, 0.0F, 3, 11, 3, scale);
        this.leg3.setPos(-5.0F, 12.0F,6.0F);

        this.leg3Wool1 = new ModelPart(this, 0, 82);
        this.leg3Wool1.addBox(0.0F, 0.0F, 0.0F, 3, 7, 3, scale + 0.5F);
        this.leg3Wool1.setPos(-5.0F, 12.0F,12.0F);

        this.leg3Wool2 = new ModelPart(this, 0, 82);
        this.leg3Wool2.addBox(0.0F, 0.0F, 0.0F, 3, 7, 3, scale + 1.0F);
        this.leg3Wool2.setPos(-5.0F, 12.0F,12.0F);

        this.leg3Wool3 = new ModelPart(this, 0, 82);
        this.leg3Wool3.addBox(0.0F, 0.0F, 0.0F, 3, 7, 3, scale + 1.5F);
        this.leg3Wool3.setPos(-5.0F, 12.0F,12.0F);

        this.leg3Wool4 = new ModelPart(this, 0, 82);
        this.leg3Wool4.addBox(0.0F, 0.0F, 0.0F, 3, 7, 3, scale + 2.0F);
        this.leg3Wool4.setPos(-5.0F, 12.0F,12.0F);

        this.leg4 = new ModelPart(this, 12, 82);
        this.leg4.addBox(0.0F, 0.0F, 0.0F, 3, 11, 3, scale);
        this.leg4.setPos(2.0F, 12.0F,6.0F);

        this.leg4Wool1 = new ModelPart(this, 12, 82);
        this.leg4Wool1.addBox(0.0F, 0.0F, 0.0F, 3, 7, 3, scale + 0.5F);
        this.leg4Wool1.setPos(2.0F, 12.0F,12.0F);

        this.leg4Wool2 = new ModelPart(this, 12, 82);
        this.leg4Wool2.addBox(0.0F, 0.0F, 0.0F, 3, 7, 3, scale + 1.0F);
        this.leg4Wool2.setPos(2.0F, 12.0F,12.0F);

        this.leg4Wool3 = new ModelPart(this, 12, 82);
        this.leg4Wool3.addBox(0.0F, 0.0F, 0.0F, 3, 7, 3, scale + 1.5F);
        this.leg4Wool3.setPos(2.0F, 12.0F,12.0F);

        this.leg4Wool4 = new ModelPart(this, 12, 82);
        this.leg4Wool4.addBox(0.0F, 0.0F, 0.0F, 3, 7, 3, scale + 2.0F);
        this.leg4Wool4.setPos(2.0F, 12.0F,12.0F);

        this.toeOuterFrontR = new ModelPart(this, 26, 70);
        this.toeOuterFrontR.addBox(-0.85F, 10.0F, -2.5F, 3, 3, 4, scale - 0.75F);
        this.toeInnerFrontR = new ModelPart(this, 44, 70);
        this.toeInnerFrontR.addBox(0.75F, 10.0F, -2.5F, 3, 3, 4, scale - 0.75F);

        this.toeOuterFrontL = new ModelPart(this, 62, 70);
        this.toeOuterFrontL.addBox(0.85F, 10.0F, -2.5F, 3, 3, 4, scale - 0.75F);
        this.toeInnerFrontL = new ModelPart(this, 80, 70);
        this.toeInnerFrontL.addBox(-0.75F, 10.0F, -2.5F, 3, 3, 4, scale - 0.75F);

        this.toeOuterBackR = new ModelPart(this, 26, 84);
        this.toeOuterBackR.addBox(-0.85F, 10.0F, -2.5F, 3, 3, 4, scale - 0.75F);
        this.toeInnerBackR = new ModelPart(this, 44, 84);
        this.toeInnerBackR.addBox(0.75F, 10.0F, -2.5F, 3, 3, 4, scale - 0.75F);

        this.toeOuterBackL = new ModelPart(this, 62, 84);
        this.toeOuterBackL.addBox(0.85F, 10.0F, -2.5F, 3, 3, 4, scale - 0.75F);
        this.toeInnerBackL = new ModelPart(this, 80, 84);
        this.toeInnerBackL.addBox(-0.75F, 10.0F, -2.5F, 3, 3, 4, scale - 0.75F);

        this.eyeLeft = new EnhancedRendererModelNew(this, 22, 3);
        this.eyeLeft.addBox(0.0F, 0.0F, 0.0F, 2, 2, 1, 0.01F);
        this.eyeLeft.setPos(2.0F, -1.0F, -3.0F);

        this.eyeRight = new EnhancedRendererModelNew(this, 0, 3);
        this.eyeRight.addBox(0.0F, 0.0F, 0.0F, 2, 2, 1, 0.01F);
        this.eyeRight.setPos(-4.0F, -1.0F, -3.0F);

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
        this.saddleWestern.texOffs(146, 15);
        this.saddleWestern.addBox(-4.0F, -3.0F, 5.0F, 8, 2, 4, 0.0F);
        this.saddleWestern.texOffs(222, 15);
        this.saddleWestern.addBox(-3.5F, -4.0F, 8.0F, 7, 2, 2, 0.0F);

        this.saddleEnglish = new EnhancedRendererModelNew(this, 147, 1, "EnglishSaddle");
        this.saddleEnglish.addBox(-5.0F, -1.0F, -4.0F, 10, 2, 12, 0.0F);
        this.saddleEnglish.texOffs(146, 15);
        this.saddleEnglish.addBox(-4.0F, -1.5F, 5.0F, 8, 2, 4, 0.0F);
        this.saddleEnglish.texOffs(222, 15);
        this.saddleEnglish.addBox(-3.5F, -2.0F, 7.5F, 7, 2, 2, 0.0F);

        this.saddleHorn = new EnhancedRendererModelNew(this, 170, 19, "SaddleHorn");
        this.saddleHorn.addBox(-4.0F, -2.0F, -3.0F, 8, 2, 3, 0.0F);

        this.saddlePomel = new EnhancedRendererModelNew(this, 179, 0, "SaddlePomel");
        this.saddlePomel.addBox(-1.0F, -3.0F, -2.0F, 2, 4, 2, -0.25F);
        this.saddlePomel.setPos(0.0F, -2.0F, -2.0F);

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
        this.stirrup.texOffs(150, 0);
        this.stirrup.addBox(-0.5F, 9.5F, 1.0F, 1, 1, 1);
        this.stirrup.texOffs(146, 2);
        this.stirrup.addBox(-0.5F, 10.5F, -1.5F, 1, 3, 1);
        this.stirrup.texOffs(150, 2);
        this.stirrup.addBox(-0.5F, 10.5F, 1.5F, 1, 3, 1);
        this.stirrup.texOffs(147, 7);
        this.stirrup.addBox(-0.5F, 12.5F, -0.5F, 1, 1, 2);

        this.saddlePad = new EnhancedRendererModelNew(this, 130, 24, "SaddlePad");
        this.saddlePad.addBox(-8.0F, -1.0F, -6.0F, 16, 10, 15, -1.0F);

        this.collar = new EnhancedRendererModelNew(this, 88, 84, "Collar");
        this.collar.addBox(-5.0F, -4.5F, -3.0F, 10, 2, 8, 0.0F);

        this.collarHardware = new EnhancedRendererModelNew(this, 127, 88, "CollarHardware");
        this.collarHardware.addBox(0.0F, -5.0F, -5.0F, 0, 3, 3, 0.0F);
        this.collarHardware.texOffs(116, 84);
        this.collarHardware.addBox(-1.5F, -2.5F, -5.75F, 3, 3, 3, 0.0F);
        this.neckBone.addChild(this.collar);
        this.collar.addChild(this.collarHardware);

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

        this.texHeight = 128;
        this.texWidth = 128;
        //bridle
        this.bridle = new EnhancedRendererModelNew(this, 0, 56);
        this.bridle.addBox(-4.0F, -3.0F, -3.0F, 8, 6, 6, scale+ 0.1F);

        this.bridleNose = new EnhancedRendererModelNew(this, 0, 48);
        this.bridleNose.addBox(-2.0F, 0.0F, -7.0F, 4, 4, 4, scale + 0.1F);
        this.bridle.addChild(this.bridleNose);

        this.head.addChild(this.bridle);
    }

    @Override
    public void renderToBuffer(PoseStack matrixStackIn, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        LlamaModelData llamaModelData = getLlamaModelData();

        int[] genes = llamaModelData.llamaGenes;
        int coatlength = llamaModelData.coatlength;
        int maxCoatlength = llamaModelData.maxCoatlength;
        boolean sleeping = llamaModelData.sleeping;

        if (genes != null) {
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
            if (ageTime <= llamaModelData.adultAge) {
                age = ageTime < 0 ? 0 : ageTime/(float) llamaModelData.adultAge;
            }
        }

        float size = llamaModelData.size;
        float finalLlamaSize = (( 2.0F * size * age) + size) / 3.0F;
        float babyScale = 1.0F;
        float d = 0.0F;
        if (!sleeping) {
            babyScale = (2.0F - age)/1;
            d = 0.5F * (1.0F-age);
        }

        matrixStackIn.pushPose();
        matrixStackIn.scale(finalLlamaSize, finalLlamaSize, finalLlamaSize);
        matrixStackIn.translate(0.0F, (-1.5F + 1.5F / finalLlamaSize) - d, 0.0F);

        if (llamaModelData.blink >= 6) {
            this.eyeLeft.visible = true;
            this.eyeRight.visible = true;
        } else {
            this.eyeLeft.visible = false;
            this.eyeRight.visible = false;
        }

        this.earsTopR.visible = false;
        this.earsTopL.visible = false;
        this.earsTopBananaR.visible = false;
        this.earsTopBananaL.visible = false;

        if (banana){
            this.earsTopBananaR.visible = true;
            this.earsTopBananaL.visible = true;
        }else {
            this.earsTopR.visible = true;
            this.earsTopL.visible = true;
        }

        if (llamaModelData.bridle!=null) {
            if (llamaModelData.bridle.getItem() instanceof CustomizableBridle) {
                this.bridle.visible = true;
            } else {
                this.bridle.visible = false;
            }
        }

        renderCollar(llamaModelData.collar, coatlength, llamaModelData.unrenderedModels, matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);

        this.chest1.visible = false;
        this.chest2.visible = false;
        if (llamaModelData.hasChest) {
            this.chest1.visible = true;
            this.chest2.visible = true;
            this.chest1.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            this.chest2.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        }

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

                if (maxCoatlength <= 0 || this.young) {
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

                renderSaddle(llamaModelData.saddle, coatlength, llamaModelData.unrenderedModels, matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);

        matrixStackIn.popPose();

        matrixStackIn.pushPose();
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

        matrixStackIn.popPose();

    }

    private void renderCollar(boolean hasCollar, int coatLength, List<String> unrenderedModels, PoseStack matrixStackIn, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        Map<String, List<Float>> mapOfScale = new HashMap<>();
        if (hasCollar) {
            this.collar.visible = true;
            List<Float> collarScale = ModelHelper.createScalings(0.9F, 1.0F, 0.75F, 0.0F, 0.0F, 0.080F);
            List<Float> hardwareScale = ModelHelper.createScalings(1.0F/0.9F, 1.0F, 1.0F/0.75F, 0.0F, 0.0F, 0.048F);
            if (coatLength == 0) {
                collarScale = ModelHelper.createScalings(1.0F, 1.0F, 0.9F, 0.0F, 0.0F, 0.005F);
                hardwareScale = ModelHelper.createScalings(1.0F, 1.0F, 1.0F/0.9F, 0.0F, 0.0F, 0.02F);
            } else if (coatLength == 1) {
                collarScale = ModelHelper.createScalings(1.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F);
                hardwareScale = ModelHelper.createScalings(1.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F);
            } else if (coatLength == 2) {
                collarScale = ModelHelper.createScalings(1.125F, 1.0F, 1.125F, 0.0F, 0.0F, -0.00625F);
                hardwareScale = ModelHelper.createScalings(1.0F/1.125F, 1.0F, 1.0F/1.125F, 0.0F, 0.0F, -0.02F);
            } else if (coatLength == 3) {
                collarScale = ModelHelper.createScalings(1.125F, 1.0F, 1.25F, 0.0F, 0.0F, -0.0125F);
                hardwareScale = ModelHelper.createScalings(1.0F/1.125F, 1.0F, 1.0F/1.15F, 0.0F, 0.0F, -0.029F);
            } else if (coatLength == 4) {
                collarScale = ModelHelper.createScalings(1.25F, 1.0F, 1.3F, 0.0F, 0.0F, -0.015F);
                hardwareScale = ModelHelper.createScalings(1.0F/1.25F, 1.0F, 1.0F/1.3F, 0.0F, 0.0F, -0.0565F);
            }
            mapOfScale.put("Collar", collarScale);
            mapOfScale.put("CollarHardware", hardwareScale);
        } else {
            this.collar.visible = false;
        }
        this.neckBone.render(matrixStackIn, bufferIn , mapOfScale, unrenderedModels, false, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    }

    private void renderSaddle(ItemStack saddleStack, int coatLength, List<String> unrenderedModels, PoseStack matrixStackIn, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        Map<String, List<Float>> mapOfScale = new HashMap<>();

        this.saddleWestern.visible = false;
        this.saddleEnglish.visible = false;
        this.saddle.visible = false;
        this.saddlePomel.visible = false;
        this.saddleSideL.visible = true;
        this.saddleSideR.visible = true;

        float coatMod = 1.0F;

        if (coatLength != 0) {
            if (coatLength == -1) {
                coatMod = 0.875F;
            } else if (coatLength == 1) {
                coatMod = 1.0625F;
            } else if (coatLength == 2) {
                coatMod = 1.15F;
            } else if (coatLength == 3) {
                coatMod = 1.25F;
            } else {
                coatMod = 1.375F;
            }
        }

        if (saddleStack!=null) {
            if (!saddleStack.isEmpty()) {
                Item saddle = saddleStack.getItem();
                float saddleScale = 0.875F;
                List<Float> scalingsForSaddle = ModelHelper.createScalings(saddleScale, saddleScale, saddleScale, 0.0F, -saddleScale * 0.01F, (saddleScale - 1.0F) * 0.04F);
                List<Float> scalingsForPad = ModelHelper.createScalings(coatMod, saddleScale, saddleScale, 0.0F, -saddleScale * 0.01F, (saddleScale - 1.0F) * 0.04F);

                if (saddle instanceof CustomizableSaddleWestern) {
                    this.saddleWestern.visible = true;
                    this.saddlePomel.visible = true;
                    mapOfScale.put("WesternSaddle", scalingsForSaddle);
                    mapOfScale.put("SaddlePad", scalingsForPad);
                    this.saddleWestern.render(matrixStackIn, bufferIn, mapOfScale, unrenderedModels, false, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                } else if (saddle instanceof CustomizableSaddleEnglish) {
                    this.saddleEnglish.visible = true;
                    mapOfScale.put("EnglishSaddle", scalingsForSaddle);
                    mapOfScale.put("SaddlePad", scalingsForPad);
                    this.saddleEnglish.render(matrixStackIn, bufferIn, mapOfScale, unrenderedModels, false, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                } else if (saddle instanceof CustomizableSaddleVanilla || saddle instanceof SaddleItem) {
                    this.saddle.visible = true;
                    mapOfScale.put("Saddle", scalingsForSaddle);
                    mapOfScale.put("SaddlePad", scalingsForPad);
                    this.saddleSideL.visible = false;
                    this.saddleSideR.visible = false;
                    this.saddle.render(matrixStackIn, bufferIn, mapOfScale, unrenderedModels, false, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                }
            }
        }
    }

        @Override
    public void prepareMobModel(T entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTickTime) {
        LlamaModelData llamaModelData = getCreateLlamaModelData(entitylivingbaseIn);
        this.currentLlama = entitylivingbaseIn.getId();

        boolean sleeping = llamaModelData.sleeping;
        float onGround;

        this.body.y = 2.0F;

        if (sleeping) {
            onGround = sleepingAnimation(young);
        } else {
            onGround = standingAnimation();
        }

        this.neck.y = onGround;
    }

    @Override
    public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        //TODO if llama is angry turn ears back

        LlamaModelData llamaModelData = getLlamaModelData();
        int[] sharedGenes = llamaModelData.llamaGenes;
        ItemStack saddleStack = llamaModelData.saddle;

        this.neck.yRot = netHeadYaw * 0.017453292F;

        if (!llamaModelData.sleeping) {
            this.neck.xRot = headPitch * 0.017453292F;
            this.leg1.xRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
            this.leg2.xRot = Mth.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
            this.leg3.xRot = Mth.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
            this.leg4.xRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        }

        ModelHelper.copyModelPositioning(neck, neckWool0);
        ModelHelper.copyModelPositioning(neck, neckWool1);

        ModelHelper.copyModelPositioning(body, bodyShaved);
        ModelHelper.copyModelPositioning(body, body1);

        ModelHelper.copyModelPositioning(leg1, leg1Wool1);
        ModelHelper.copyModelPositioning(leg2, leg2Wool1);
        ModelHelper.copyModelPositioning(leg3, leg3Wool1);
        ModelHelper.copyModelPositioning(leg4, leg4Wool1);

        ModelHelper.copyModelPositioning(body, tail);
        ModelHelper.copyModelPositioning(body, tail1);

        if ( suri && llamaModelData.coatlength >= 0 ) {
            if (llamaModelData.coatlength >= 1) {
                this.neckWool1.y = this.neck.y + (llamaModelData.coatlength/2.0F);
                this.body1.y = this.body.y + (llamaModelData.coatlength/2.0F);
                this.tail1.y = this.body.y + (llamaModelData.coatlength/3.0F);
                this.leg1Wool1.y = this.leg1.y + (llamaModelData.coatlength/2.0F);
                this.leg2Wool1.y = this.leg2.y + (llamaModelData.coatlength/2.0F);
                this.leg3Wool1.y = this.leg3.y + (llamaModelData.coatlength/2.0F);
                this.leg4Wool1.y = this.leg4.y + (llamaModelData.coatlength/2.0F);
            }
        }
        ModelHelper.copyModelPositioning(neck, neckBone);
        ModelHelper.copyModelPositioning(neck, neckWool0);
        ModelHelper.copyModelPositioning(neck, neckWool1);
        ModelHelper.copyModelPositioning(neckWool1, neckWool2);
        ModelHelper.copyModelPositioning(neckWool1, neckWool3);
        ModelHelper.copyModelPositioning(neckWool1, neckWool4);

//        copyModelPositioning(head, earsR);
//        copyModelPositioning(head, earsL);
//        copyModelPositioning(head, earsTopR);
//        copyModelPositioning(head, earsTopL);
//        copyModelPositioning(head, earsTopBananaR);
//        copyModelPositioning(head, earsTopBananaL);

        ModelHelper.copyModelPositioning(body, body2);
        ModelHelper.copyModelPositioning(body, body3);
        ModelHelper.copyModelPositioning(body, body4);

        ModelHelper.copyModelPositioning(tail1, tail2);
        ModelHelper.copyModelPositioning(tail1, tail3);
        ModelHelper.copyModelPositioning(tail1, tail4);

        //TODO fix the toes so they angle properly and maintain the angle while they walk

        ModelHelper.copyModelPositioning(leg1Wool1, leg1Wool2);
        ModelHelper.copyModelPositioning(leg1Wool1, leg1Wool3);
        ModelHelper.copyModelPositioning(leg1Wool1, leg1Wool4);
        ModelHelper.copyModelPositioning(leg1, toeOuterFrontR);
        ModelHelper.copyModelPositioning(leg1, toeInnerFrontR);


        ModelHelper.copyModelPositioning(leg2Wool1, leg2Wool2);
        ModelHelper.copyModelPositioning(leg2Wool1, leg2Wool3);
        ModelHelper.copyModelPositioning(leg2Wool1, leg2Wool4);
        ModelHelper.copyModelPositioning(leg2, toeOuterFrontL);
        ModelHelper.copyModelPositioning(leg2, toeInnerFrontL);

        ModelHelper.copyModelPositioning(leg3Wool1, leg3Wool2);
        ModelHelper.copyModelPositioning(leg3Wool1, leg3Wool3);
        ModelHelper.copyModelPositioning(leg3Wool1, leg3Wool4);
        ModelHelper.copyModelPositioning(leg3, toeOuterBackR);
        ModelHelper.copyModelPositioning(leg3, toeInnerBackR);

        ModelHelper.copyModelPositioning(leg4Wool1, leg4Wool2);
        ModelHelper.copyModelPositioning(leg4Wool1, leg4Wool3);
        ModelHelper.copyModelPositioning(leg4Wool1, leg4Wool4);
        ModelHelper.copyModelPositioning(leg4, toeOuterBackL);
        ModelHelper.copyModelPositioning(leg4, toeInnerBackL);

        setNoseRotations(sharedGenes, llamaModelData);

        float coatLength = (float)llamaModelData.coatlength;
        if (coatLength >= 1) {
            coatLength = coatLength/1.825F;
        } else if (coatLength == -1) {
            coatLength = -1.25F;
        }

        if (saddleStack!=null) {
            if (!saddleStack.isEmpty()) {
                Item saddle = saddleStack.getItem();
                if (saddle instanceof CustomizableSaddleWestern) {
                    this.saddleWestern.y = 2.0F - coatLength;
                    this.saddleSideL.setPos(4.75F, -1.0F, -5.25F);
                    this.saddleSideR.setPos(-4.75F, -1.0F, -5.25F);
                    this.saddleHorn.setPos(0.0F, -2.0F, -2.0F);
                    this.saddleHorn.xRot = (float) Math.PI / 8.0F;
                    this.saddlePomel.setPos(0.0F, -1.5F, -0.5F);
                    this.saddlePomel.xRot = -0.2F;
                    this.stirrup2DWideL.setPos(7.5F + coatLength, 0.0F, -3.5F);
                    this.stirrup2DWideR.setPos(-7.5F - coatLength, 0.0F, -3.5F);
                } else if (saddle instanceof CustomizableSaddleEnglish) {
                    this.saddleEnglish.y = 2.0F - coatLength;
                    this.saddleSideL.setPos(3.25F, -0.5F, -4.0F);
                    this.saddleSideR.setPos(-3.25F, -0.5F, -4.0F);
                    this.saddleHorn.setPos(0.0F, -1.0F, -1.0F);
                    this.saddleHorn.xRot = (float) Math.PI / 4.5F;
                    this.stirrup3DNarrowL.setPos(7.25F + coatLength, -0.25F, -1.5F);
                    this.stirrup3DNarrowR.setPos(-7.25F - coatLength, -0.25F, -1.5F);
                } else if (saddle instanceof CustomizableSaddleVanilla || saddle instanceof SaddleItem) {
                    this.saddle.y = 2.0F - coatLength;
                    this.stirrup3DNarrowL.setPos(7.5F + coatLength, 0.0F, 0.0F);
                    this.stirrup3DNarrowR.setPos(-7.5F - coatLength, 0.0F, 0.0F);
                }
            }
        }

            ModelHelper.copyModelPositioning(this.nose, this.bridleNose);
    }

    private void setNoseRotations(int[] sharedGenes, LlamaModelData llamaModelData) {

        float age = 1.0F;
        if (!(llamaModelData.birthTime == null) && !llamaModelData.birthTime.equals("") && !llamaModelData.birthTime.equals("0")) {
            int ageTime = (int)(llamaModelData.clientGameTime - Long.parseLong(llamaModelData.birthTime));
            if (ageTime <= llamaModelData.adultAge) {
                age = ageTime < 0 ? 0 : (float) ageTime/(float) llamaModelData.adultAge;
            }
        }

        this.nose.z = 1.5F - (age*1.5F);

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

        this.nose.y = -0.3F - noseHeight;
    }

    private float sleepingAnimation(boolean isChild) {
        float onGround;

        if (isChild) {
            onGround = 20.0F;
            this.body.y = 14.0F;
        } else {
            onGround = 20.0F;
            this.body.y = 11.0F;
        }

        this.neck.xRot = 1.6F;
        this.neck.z = -9.0F;

        this.head.xRot = -1.6F;

        this.leg1.xRot = 1.575F;
        this.leg1.yRot = 0.2F;
        this.leg2.xRot = 1.575F;
        this.leg2.yRot = -0.2F;
        this.leg3.xRot = -1.575F;
        this.leg4.xRot = -1.575F;

        this.chest1.y = 12.0F;
        this.chest2.y = 12.0F;

        this.leg1.setPos(-5.0F, 24.0F, -10.0F);
        this.leg2.setPos(2.0F, 24.0F, -10.0F);
        this.leg3.setPos(-5.0F, 21.0F, 10.0F);
        this.leg4.setPos(2.0F, 21.0F, 10.0F);
        return onGround;
    }

    private float standingAnimation() {
        float onGround;
        onGround = 5.0F;

        this.body.y = 2.0F;
        this.leg1.yRot = 0.0F;
        this.leg3.yRot = 0.0F;

        this.neck.z = -10.0F;

        this.head.xRot = 0.0F;

        this.chest1.y = 3.0F;
        this.chest2.y = 3.0F;

        if (this.getLlamaModelData().angry) {
            this.earsR.yRot = (float)Math.PI/4.0F;
        }

        this.leg1.setPos(-5.0F, 12.0F,-7.0F);
        this.leg2.setPos(2.0F, 12.0F,-7.0F);
        this.leg3.setPos(-5.0F, 12.0F,6.0F);
        this.leg4.setPos(2.0F, 12.0F,6.0F);

        return onGround;
    }

    private class LlamaModelData {
        int[] llamaGenes;
        int coatlength = 0;
        int maxCoatlength = 0;
        String birthTime;
        boolean sleeping = false;
        int blink = 0;
        int lastAccessed = 0;
        boolean angry = false;
        long clientGameTime = 0;
//        int dataReset = 0;
        List<String> unrenderedModels = new ArrayList<>();
        ItemStack saddle;
        ItemStack bridle;
        ItemStack harness;
        boolean collar = false;
        boolean hasChest = false;
        float size;
        int adultAge;
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

        if (llamaModelDataCache.containsKey(enhancedLlama.getId())) {
            LlamaModelData llamaModelData = llamaModelDataCache.get(enhancedLlama.getId());
            llamaModelData.lastAccessed = 0;
//            llamaModelData.dataReset++;
//            if (llamaModelData.dataReset > 5000) {
//                llamaModelData.dataReset = 0;
//            }
            llamaModelData.coatlength = enhancedLlama.getCoatLength();
            llamaModelData.sleeping = enhancedLlama.isAnimalSleeping();
            llamaModelData.blink = enhancedLlama.getBlink();
            llamaModelData.birthTime = enhancedLlama.getBirthTime();
            llamaModelData.angry = (enhancedLlama.isAggressive());
            llamaModelData.clientGameTime = (((ClientLevel)enhancedLlama.level).getLevelData()).getGameTime();
            llamaModelData.unrenderedModels = new ArrayList<>();
            llamaModelData.saddle = enhancedLlama.getEnhancedInventory().getItem(1);
            llamaModelData.bridle = enhancedLlama.getEnhancedInventory().getItem(3);
            llamaModelData.harness = enhancedLlama.getEnhancedInventory().getItem(5);
            llamaModelData.collar = hasCollar(enhancedLlama.getEnhancedInventory());
            llamaModelData.hasChest = !enhancedLlama.getEnhancedInventory().getItem(0).isEmpty();

            return llamaModelData;
        } else {
            LlamaModelData llamaModelData = new LlamaModelData();
            if (enhancedLlama.getSharedGenes()!=null) {
                llamaModelData.llamaGenes = enhancedLlama.getSharedGenes().getAutosomalGenes();
            }
            llamaModelData.coatlength = enhancedLlama.getCoatLength();
            llamaModelData.maxCoatlength = enhancedLlama.getCoatLength();
            llamaModelData.sleeping = enhancedLlama.isAnimalSleeping();
            llamaModelData.blink = enhancedLlama.getBlink();
            llamaModelData.birthTime = enhancedLlama.getBirthTime();
            llamaModelData.angry = (!(enhancedLlama.getLastHurtByMob() == null));
            llamaModelData.clientGameTime = (((ClientLevel)enhancedLlama.level).getLevelData()).getGameTime();
            llamaModelData.saddle = enhancedLlama.getEnhancedInventory().getItem(1);
            llamaModelData.bridle = enhancedLlama.getEnhancedInventory().getItem(3);
            llamaModelData.harness = enhancedLlama.getEnhancedInventory().getItem(5);
            llamaModelData.collar = hasCollar(enhancedLlama.getEnhancedInventory());
            llamaModelData.hasChest = !enhancedLlama.getEnhancedInventory().getItem(0).isEmpty();
            llamaModelData.size = enhancedLlama.getAnimalSize();
            llamaModelData.adultAge = EanimodCommonConfig.COMMON.adultAgeLlama.get();

            if(llamaModelData.llamaGenes != null) {
                llamaModelDataCache.put(enhancedLlama.getId(), llamaModelData);
            }

            return llamaModelData;
        }
    }

    private boolean hasCollar(SimpleContainer inventory) {
        for (int i = 1; i < 6; i++) {
            if (inventory.getItem(i).getItem() instanceof CustomizableCollar) {
                return true;
            }
        }
        return false;
    }

}
