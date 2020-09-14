package mokiyoki.enhancedanimals.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import mokiyoki.enhancedanimals.entity.EnhancedPig;
import mokiyoki.enhancedanimals.items.CustomizableCollar;
import mokiyoki.enhancedanimals.items.CustomizableSaddleEnglish;
import mokiyoki.enhancedanimals.items.CustomizableSaddleWestern;
import mokiyoki.enhancedanimals.model.util.ModelHelper;
import mokiyoki.enhancedanimals.renderer.EnhancedRendererModelNew;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
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

    private Map<Integer, PigModelData> pigModelDataCache = new HashMap<>();
    private int clearCacheTimer = 0;
    private float headRotationAngleX;

    private final ModelRenderer chest1;
    private final ModelRenderer chest2;

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
    private final EnhancedRendererModelNew neckLong;
    private final EnhancedRendererModelNew neckBiggerLong;
    private final EnhancedRendererModelNew wattles;
    private final EnhancedRendererModelNew body11;
    private final EnhancedRendererModelNew body12;
    private final EnhancedRendererModelNew body13;
    private final EnhancedRendererModelNew body14;
    private final EnhancedRendererModelNew body15;
    private final EnhancedRendererModelNew butt5;
    private final EnhancedRendererModelNew butt6;
    private final EnhancedRendererModelNew butt7;
    private final EnhancedRendererModelNew butt8;
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
    private final EnhancedRendererModelNew collar;

    private final List<EnhancedRendererModelNew> saddles = new ArrayList<>();

    private Integer currentPig = null;

    public ModelEnhancedPig() {

        this.textureWidth = 256;
        this.textureHeight = 256;

        this.pig = new EnhancedRendererModelNew(this, 49, 0);
        this.pig.setRotationPoint(0.0F, 0.0F, 0.0F);

        this.head = new EnhancedRendererModelNew(this, 49, 0);
        this.head.addBox(-3.5F, -5.0F, -4.0F, 7, 6, 7);
        this.head.setRotationPoint(0.0F, -5.0F, -4.0F);

        this.cheeks = new EnhancedRendererModelNew(this, 49, 13);
        this.cheeks.addBox(-4.0F, 0.0F, 0.0F, 8, 5, 4, 0.25F);
        this.cheeks.setRotationPoint(0.0F, -5.5F, -4.0F);

        this.snout = new EnhancedRendererModelNew(this, 49, 22);
        this.snout.addBox(-2.0F, -5.0F, -3.0F, 4, 6, 3);
        this.snout.setRotationPoint(0.0F, -6.0F, 0.0F);

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

        this.neckLong = new EnhancedRendererModelNew(this, 0, 0);
        this.neckLong.addBox(-4.5F, -6.75F, -9.0F, 9, 8, 9, 1.0F);
        this.neckLong.setRotationPoint(0.0F, 0.0F, 9.1F);

        this.neckBigger = new EnhancedRendererModelNew(this, 0, 0);
        this.neckBigger.addBox(-4.5F, -5.75F, -9.0F, 9, 7, 9);
        this.neckBigger.setRotationPoint(0.0F, 0.0F, 9.1F);

        this.neckBiggerLong = new EnhancedRendererModelNew(this, 0, 0);
        this.neckBiggerLong.addBox(-4.5F, -6.75F, -9.0F, 9, 8, 9, 1.0F);
        this.neckBiggerLong.setRotationPoint(0.0F, 0.0F, 9.1F);

        this.wattles = new EnhancedRendererModelNew(this, 3, 9);
        this.wattles.addBox(2.5F, 0.0F, -9.0F, 2, 4, 2);
        this.wattles.setTextureOffset(25,9);
        this.wattles.addBox(-4.5F, 0.0F, -9.0F, 2, 4, 2);
        this.wattles.setRotationPoint(0.0F, 2.5F, -9.0F);
        this.wattles.rotateAngleX = (float)Math.PI/-2.0F;

        this.body11 = new EnhancedRendererModelNew(this, 0, 23);
        this.body11.addBox(-5.0F, 0.0F, 0.0F, 10, 11, 10);
        this.body11.setRotationPoint(0.0F, 18.1F, -4.0F);

        this.body12 = new EnhancedRendererModelNew(this, 0, 23);
        this.body12.addBox(-5.0F, 0.0F, 0.0F, 10, 12, 10);
        this.body12.setRotationPoint(0.0F, 18.1F, -5.0F);

        this.body13 = new EnhancedRendererModelNew(this, 0, 23);
        this.body13.addBox(-5.0F, 0.0F, 0.0F, 10, 13, 10);
        this.body13.setRotationPoint(0.0F, 18.1F, -6.0F);

        this.body14 = new EnhancedRendererModelNew(this, 0, 23);
        this.body14.addBox(-5.0F, 0.0F, 0.0F, 10, 14, 10);
        this.body14.setRotationPoint(0.0F, 18.1F, -7.0F);

        this.body15 = new EnhancedRendererModelNew(this, 0, 23);
        this.body15.addBox(-5.0F, 0.0F, 0.0F, 10, 15, 10);
        this.body15.setRotationPoint(0.0F, 18.1F, -8.0F);

        this.butt5 = new EnhancedRendererModelNew(this, 0, 53);
        this.butt5.addBox(-4.5F, 0.0F, 0.0F, 9, 5, 9);
        this.butt5.setRotationPoint(0.0F, 18.0F, 5.5F);

        this.butt6 = new EnhancedRendererModelNew(this, 0, 53);
        this.butt6.addBox(-4.5F, 0.0F, 0.0F, 9, 6, 9);
        this.butt6.setRotationPoint(0.0F, 18.0F, 6.5F);

        this.butt7 = new EnhancedRendererModelNew(this, 0, 53);
        this.butt7.addBox(-4.5F, 0.0F, 0.0F, 9, 7, 9);
        this.butt7.setRotationPoint(0.0F, 18.0F, 7.5F);

        this.butt8 = new EnhancedRendererModelNew(this, 0, 53);
        this.butt8.addBox(-4.5F, 0.0F, 0.0F, 9, 8, 9);
        this.butt8.setRotationPoint(0.0F, 18.0F, 8.5F);

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
        this.leg1.setRotationPoint(-2.001F, 16.0F, -7.0F);

        this.leg2 = new EnhancedRendererModelNew(this, 61, 32);
        this.leg2.addBox(0.0F, 0.0F, 0.0F, 3, 8, 3);
        this.leg2.setRotationPoint(2.001F, 16.0F, -7.0F);

        this.leg3 = new EnhancedRendererModelNew(this, 49, 44);
        this.leg3.addBox(-3.0F, 0.0F, 0.0F, 3, 8, 3);
        this.leg3.setRotationPoint(-2.001F, 16.0F, 7.0F);

        this.leg4 = new EnhancedRendererModelNew(this, 61, 44);
        this.leg4.addBox(0.0F, 0.0F, 0.0F, 3, 8, 3);
        this.leg4.setRotationPoint(2.001F, 16.0F, 7.0F);

        this.eyeLeft = new EnhancedRendererModelNew(this, 69, 15);
        this.eyeLeft.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1, 0.01F);
        this.eyeLeft.setRotationPoint(2.5F, -5.0F, 0.0F);

        this.eyeRight = new EnhancedRendererModelNew(this, 49, 15);
        this.eyeRight.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1, 0.01F);
        this.eyeRight.setRotationPoint(-3.5F, -5.0F, 0.0F);

        this.body11.addChild(this.neckLong);
        this.body12.addChild(this.neckLong);
        this.body13.addChild(this.neckLong);
        this.body14.addChild(this.neckLong);
        this.body15.addChild(this.neckLong);
        this.neck.addChild(this.head);
        this.neck.addChild(this.wattles);
        this.neckBigger.addChild(this.head);
        this.neckBigger.addChild(this.wattles);
        this.neckLong.addChild(this.head);
        this.neckLong.addChild(this.wattles);
        this.neckBiggerLong.addChild(this.head);
        this.neckBiggerLong.addChild(this.wattles);
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
        this.butt5.addChild(this.tail0);
        this.butt6.addChild(this.tail0);
        this.butt7.addChild(this.tail0);
        this.butt8.addChild(this.tail0);
        this.tail0.addChild(this.tail1);
        this.tail1.addChild(this.tail2);
        this.tail2.addChild(this.tail3);
        this.tail3.addChild(this.tail4);
        this.tail4.addChild(this.tail5);

        this.pig.addChild(this.body11);
        this.pig.addChild(this.body12);
        this.pig.addChild(this.body13);
        this.pig.addChild(this.body14);
        this.pig.addChild(this.body15);
        this.pig.addChild(this.butt5);
        this.pig.addChild(this.butt6);
        this.pig.addChild(this.butt7);
        this.pig.addChild(this.butt8);
        this.pig.addChild(this.leg1);
        this.pig.addChild(this.leg2);
        this.pig.addChild(this.leg3);
        this.pig.addChild(this.leg4);

        /**
         * Equipment stuff
         */

        this.chest1 = new ModelRenderer(this, 80, 0);
        this.chest1.addBox(-3.0F, 0.0F, 0.0F, 8, 8, 3);
        this.chest1.setRotationPoint(-8.0F, 8.0F, 3.0F);
        this.chest1.rotateAngleY = ((float)Math.PI / 2F);
        this.chest2 = new ModelRenderer(this, 80, 11);
        this.chest2.addBox(-3.0F, 0.0F, 0.0F, 8, 8, 3);
        this.chest2.setRotationPoint(5.0F, 8.0F, 3.0F);
        this.chest2.rotateAngleY = ((float)Math.PI / 2F);

        this.saddle = new EnhancedRendererModelNew(this, 0, 0, "Saddle");

        this.saddleWestern = new EnhancedRendererModelNew(this, 210, 0, "WesternSaddle");
        this.saddleWestern.addBox(-5.0F, -2.0F, -5.0F, 10, 2, 13, 0.0F);
        this.saddleWestern.setTextureOffset(210, 15);
        this.saddleWestern.addBox(-4.0F, -3.0F, 5.0F, 8, 2, 4, 0.0F);
        this.saddleWestern.setTextureOffset(230, 15);
        this.saddleWestern.addBox(-3.5F, -4.0F, 8.0F, 7, 2, 2, 0.0F);

        this.saddleEnglish = new EnhancedRendererModelNew(this, 211, 1, "EnglishSaddle");
        this.saddleEnglish.addBox(-5.0F, -1.0F, -4.0F, 10, 2, 12, 0.0F);
        this.saddleEnglish.setTextureOffset(210, 15);
        this.saddleEnglish.addBox(-4.0F, -1.5F, 5.0F, 8, 2, 4, 0.0F);
        this.saddleEnglish.setTextureOffset(230, 15);
        this.saddleEnglish.addBox(-3.5F, -2.0F, 7.5F, 7, 2, 2, 0.0F);

        this.saddleHorn = new EnhancedRendererModelNew(this, 234, 19, "SaddleHorn");
        this.saddleHorn.addBox(-4.0F, -2.0F, -3.0F, 8, 2, 3, 0.0F);

        this.saddlePomel = new EnhancedRendererModelNew(this, 243, 0, "SaddlePomel");
        this.saddlePomel.addBox(-1.0F, -3.0F, -2.0F, 2, 4, 2, -0.25F);
        this.saddlePomel.setRotationPoint(0.0F, -2.0F, -2.0F);

        this.saddleSideL = new EnhancedRendererModelNew(this, 234, 49, "SaddleLeft");
        this.saddleSideL.addBox(0.0F, 0.0F, 0.0F, 3, 4, 8);

        this.saddleSideR = new EnhancedRendererModelNew(this, 234, 61, "SaddleRight");
        this.saddleSideR.addBox(-3.0F, 0.0F, 0.0F, 3, 4, 8);

        this.stirrup2DWideL = new EnhancedRendererModelNew(this, 248, 24, "2DStirrupL");
        this.stirrup2DWideL.addBox(0.0F, 0.0F, 0.0F, 0, 10, 4); // strap

        this.stirrup2DWideR = new EnhancedRendererModelNew(this, 248, 24, "2DStirrupR");
        this.stirrup2DWideR.addBox(0.0F, 0.0F, 0.0F, 0, 10, 4); // strap

        this.stirrup3DNarrowL = new EnhancedRendererModelNew(this, 249, 27, "3DStirrupL");
        this.stirrup3DNarrowL.addBox(-1.0F, 0.0F, 0.0F, 1, 10, 1); // strap

        this.stirrup3DNarrowR = new EnhancedRendererModelNew(this, 251, 27, "3DStirrupR");
        this.stirrup3DNarrowR.addBox(0.0F, 0.0F, 0.0F, 1, 10, 1);

        this.stirrup = new EnhancedRendererModelNew(this, 210, 0, "Stirrup");
        this.stirrup.addBox(-0.5F, 9.5F, -1.0F, 1, 1, 1);
        this.stirrup.setTextureOffset(214, 0);
        this.stirrup.addBox(-0.5F, 9.5F, 1.0F, 1, 1, 1);
        this.stirrup.setTextureOffset(210, 2);
        this.stirrup.addBox(-0.5F, 10.5F, -1.5F, 1, 3, 1);
        this.stirrup.setTextureOffset(214, 2);
        this.stirrup.addBox(-0.5F, 10.5F, 1.5F, 1, 3, 1);
        this.stirrup.setTextureOffset(211, 7);
        this.stirrup.addBox(-0.5F, 12.5F, -0.5F, 1, 1, 2);

        this.saddlePad = new EnhancedRendererModelNew(this, 194, 24, "SaddlePad");
        this.saddlePad.addBox(-8.0F, -1.0F, -6.0F, 16, 10, 15, -1.0F);

        this.saddleHorn.addChild(this.saddlePomel);

        this.collar = new EnhancedRendererModelNew(this, 80, 0);
        this.collar.addBox(-6.0F, -4.5F, -3.0F, 12, 2, 12, 0.0F);
        this.collar.setTextureOffset(80, 4);
        this.collar.addBox(0.0F, -5.0F, -5.0F, 0, 3, 3, 0.0F);
        this.collar.setTextureOffset(116, 6);
        this.collar.addBox(-1.5F, -5.0F, -6.25F, 3, 3, 3, 0.0F);
        this.collar.setRotationPoint(0.0F, -1.0F, -7.5F);
        this.neckBigger.addChild(this.collar);

        //western
        this.body11.addChild(this.saddleWestern);
        this.body12.addChild(this.saddleWestern);
        this.body13.addChild(this.saddleWestern);
        this.body14.addChild(this.saddleWestern);
        this.body15.addChild(this.saddleWestern);
        this.saddleWestern.addChild(this.saddleHorn);
        this.saddleWestern.addChild(this.saddleSideL);
        this.saddleWestern.addChild(this.saddleSideR);
        this.saddleWestern.addChild(this.saddlePad);
        this.saddleWestern.addChild(this.stirrup2DWideL);
        this.saddleWestern.addChild(this.stirrup2DWideR);
        this.stirrup2DWideL.addChild(this.stirrup);
        this.stirrup2DWideR.addChild(this.stirrup);
        //english
        this.body11.addChild(this.saddleEnglish);
        this.body12.addChild(this.saddleEnglish);
        this.body13.addChild(this.saddleEnglish);
        this.body14.addChild(this.saddleEnglish);
        this.body15.addChild(this.saddleEnglish);
        this.saddleEnglish.addChild(this.saddleHorn);
        this.saddleEnglish.addChild(this.saddleSideL);
        this.saddleEnglish.addChild(this.saddleSideR);
        this.saddleEnglish.addChild(this.saddlePad);
        this.saddleEnglish.addChild(this.stirrup3DNarrowL);
        this.saddleEnglish.addChild(this.stirrup3DNarrowR);
        this.stirrup3DNarrowL.addChild(this.stirrup);
        this.stirrup3DNarrowR.addChild(this.stirrup);
        //vanilla
        this.body11.addChild(this.saddle);
        this.body12.addChild(this.saddle);
        this.body13.addChild(this.saddle);
        this.body14.addChild(this.saddle);
        this.body15.addChild(this.saddle);
        this.saddle.addChild(this.saddlePad);
        this.saddle.addChild(this.stirrup3DNarrowL);
        this.saddle.addChild(this.stirrup3DNarrowR);
    }

    @Override
    public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {

        PigModelData pigModelData = getPigModelData();
        float size = pigModelData.size;
        size = size < 0 ? 0 : size;
        int[] gene = pigModelData.pigGenes;
        int shape = 1;
//        boolean wattles = gene[32] == 1 || gene[33] == 1;

        if (gene[56] != 1 && gene[57] != 1) {
            if (gene[56] + gene[57] <= 8) {
                shape = 0;
            } else if (gene[56] == 5 || gene[57] == 5) {
                shape = 2;
            } else if (gene[56] == 6 || gene[57] == 6) {
                shape = 3;
            } else if (gene[56] == 7 && gene[57] == 7) {

            }
        }

        float age = 1.0F;
        if (!(pigModelData.birthTime == null) && !pigModelData.birthTime.equals("") && !pigModelData.birthTime.equals("0")) {
            int ageTime = (int)(pigModelData.clientGameTime - Long.parseLong(pigModelData.birthTime));
            if (ageTime <= 108000) {
                age = ageTime < 0 ? 0 :  ageTime/108000.0F;
            }
        }

        float finalPigSize = (( 3.0F * size * age) + size) / 4.0F;
        matrixStackIn.push();
        matrixStackIn.scale(finalPigSize, finalPigSize, finalPigSize);
        matrixStackIn.translate(0.0F, -1.5F + 1.5F/finalPigSize, 0.0F);

        this.wattles.showModel = gene[32] == 1 || gene[33] == 1;

        if (pigModelData.blink >= 6) {
            this.eyeLeft.showModel = true;
            this.eyeRight.showModel = true;
        } else {
            this.eyeLeft.showModel = false;
            this.eyeRight.showModel = false;
        }

        if (pigModelData.collar) {
            this.collar.showModel = true;
        } else {
            this.collar.showModel = false;
        }

        this.chest1.showModel = false;
        this.chest2.showModel = false;
        if (pigModelData.hasChest) {
            this.chest1.showModel = true;
            this.chest2.showModel = true;
            this.chest1.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            this.chest2.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        }

        this.body11.showModel = false;
        this.body12.showModel = false;
        this.body13.showModel = false;
        this.body14.showModel = false;
        this.body15.showModel = false;
        this.butt5.showModel = false;
        this.butt6.showModel = false;
        this.butt7.showModel = false;
        this.butt8.showModel = false;
        if (false) {
            this.body11.showModel = true;
        } else if (false) {
            this.body12.showModel = true;
        } else if (false) {
            this.body13.showModel = true;
        } else if (false) {
            this.body14.showModel = true;
        } else {
            this.body15.showModel = true;
        }

        this.butt5.showModel = true;

        if (!(pigModelData.saddle.isEmpty() || pigModelData.saddle.getItem() instanceof CustomizableCollar)) {
            renderPigandSaddle(pigModelData.saddle, pigModelData.unrenderedModels, matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        } else {
            this.pig.render(matrixStackIn, bufferIn, null, new ArrayList<>(), false, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        }

        matrixStackIn.pop();
    }

    private void renderPigandSaddle( ItemStack saddleStack,List<String> unrenderedModels, MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {

            Map<String, List<Float>> mapOfScale = new HashMap<>();
            float saddleScale = 0.75F;

            this.saddleWestern.showModel = false;
            this.saddleEnglish.showModel = false;
            this.saddle.showModel = false;
            this.saddlePomel.showModel = false;

        if (!saddleStack.isEmpty()) {
            List<Float> scalingsForSaddle = ModelHelper.createScalings(saddleScale, saddleScale, saddleScale, 0.0F, -saddleScale*0.01F, (saddleScale - 1.0F)*0.04F);
            Item saddle = saddleStack.getItem();
            if (saddle instanceof CustomizableSaddleWestern) {
                this.saddleWestern.showModel = true;
                this.saddlePomel.showModel = true;
                mapOfScale.put("WesternSaddle", scalingsForSaddle);
            } else if (saddle instanceof CustomizableSaddleEnglish) {
                this.saddleEnglish.showModel = true;
                saddleScale = 1.125F;
                List<Float> scalingsForSaddlePad = ModelHelper.createScalings(saddleScale, saddleScale, saddleScale, 0.0F, -saddleScale*0.01F, (saddleScale - 1.0F)*0.04F);
                mapOfScale.put("SaddlePad", scalingsForSaddlePad);
                mapOfScale.put("EnglishSaddle", scalingsForSaddle);
            } else if (!(saddle instanceof CustomizableCollar)) {
                this.saddle.showModel = true;
                mapOfScale.put("Saddle", scalingsForSaddle);
            }
        }
        this.pig.render(matrixStackIn, bufferIn , mapOfScale, unrenderedModels, false, packedLightIn, packedOverlayIn, red, green, blue, alpha);
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
        this.neckLong.rotationPointZ = (onGround - (entitylivingbaseIn).getHeadRotationPointY(partialTickTime) * 4.5F) + 1;
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
                float age = ageTime < 0 ? 0 : ageTime/70000.F;
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
        this.snout.rotationPointY = -(2.0F + (4.75F*snoutLength));
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

        /**
         * longer body11 adjustments prototype
         */

        /**
        this.body14.rotationPointZ = -6.0F; //-4.0F
        this.tail0.rotationPointY = 9.0F; //  5.0F
        this.leg1.rotationPointZ = -9.0F; // -7.0F
        this.leg2.rotationPointZ = -9.0F; // -7.0F
        this.leg3.rotationPointZ = 10.5F;  // 7.0F
        this.leg4.rotationPointZ = 10.5F; //  7.0F
        */
        if (this.body11.showModel) {
            this.leg1.rotationPointZ = -7.0F;
            this.leg2.rotationPointZ = -7.0F;
        } else if (this.body12.showModel) {
            this.leg1.rotationPointZ = -8.0F;
            this.leg2.rotationPointZ = -8.0F;
        } else if (this.body13.showModel) {
            this.leg1.rotationPointZ = -9.0F;
            this.leg2.rotationPointZ = -9.0F;
        } else if (this.body14.showModel) {
            this.leg1.rotationPointZ = -10.0F;
            this.leg2.rotationPointZ = -10.0F;
        } else if (this.body15.showModel) {
            this.leg1.rotationPointZ = -11.0F;
            this.leg2.rotationPointZ = -11.0F;
        }

        float movebutt = 1F;

        //butt selection probs should have mostly to do with shape?
        if (this.butt5.showModel) {
            this.tail0.rotationPointY = 5.0F;
            this.butt5.rotationPointZ = 6.0F + movebutt;
            this.leg3.rotationPointZ = 7.0F + movebutt;
            this.leg4.rotationPointZ = 7.0F + movebutt;
        } else if (this.butt6.showModel) {
            this.tail0.rotationPointY = 6.0F;
            this.butt6.rotationPointZ = 6.0F + movebutt;
            this.leg3.rotationPointZ = 8.0F + movebutt;
            this.leg4.rotationPointZ = 8.0F + movebutt;
        } else if (this.butt7.showModel) {
            this.tail0.rotationPointY = 7.0F;
            this.butt7.rotationPointZ = 6.0F + movebutt;
            this.leg3.rotationPointZ = 9.0F + movebutt;
            this.leg4.rotationPointZ = 9.0F + movebutt;
        } else if (this.butt8.showModel) {
            this.tail0.rotationPointY = 8.0F;

            /**
             * if body15 butt8 should be from -3 to 1
             *
             */

            this.butt8.rotationPointZ = 6.0F + movebutt;
            this.leg3.rotationPointZ = 10.0F + movebutt;
            this.leg4.rotationPointZ = 10.0F + movebutt;
        }

    }

    @Override
    public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        PigModelData pigModelData = getPigModelData();
        int ticks = entityIn.ticksExisted;
        ItemStack saddleStack = pigModelData.saddle;
//        List<String> unrenderedModels = new ArrayList<>();

        float twoPi = (float)Math.PI * 2.0F;
        float halfPi = (float)Math.PI / 2F;

//        this.neck.rotateAngleX = twoPi;
//        this.neckLong.rotateAngleX = twoPi;
        this.body11.rotateAngleX = halfPi;
        this.body12.rotateAngleX = halfPi;
        this.body13.rotateAngleX = halfPi;
        this.body14.rotateAngleX = halfPi;
        this.body15.rotateAngleX = halfPi;
        this.butt5.rotateAngleX = halfPi;
        this.butt6.rotateAngleX = halfPi;
        this.butt7.rotateAngleX = halfPi;
        this.butt8.rotateAngleX = halfPi;

        /**
         * change only earFlopMod for ear effects. If anything is tweaked it needs to be through that.
         */
        float earFlopMod = 1.0F; //[-1.0 = full droop, -0.65 = half droop, -0.25 = over eyes flop, 0 = stiff flop, 0.5F is half flop, 1 is no flop]

        float earFlopContinuationMod;

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

        ModelHelper.copyModelPositioning(earSmallL, earMediumL);
        ModelHelper.copyModelPositioning(earSmallR, earMediumR);

/**
 * these are the cause of the trouble with pigs
 * netHeadYaw must be getting some unusual numbers when in gui form
 * try to get a more reasonable set of numbers somehow
 */
        this.neck.rotateAngleX = twoPi + ((headPitch * 0.017453292F) / 5.0F) + (this.headRotationAngleX / 2.0F);
        this.head.rotateAngleX = ((headPitch * 0.017453292F)/5.0F)  + (this.headRotationAngleX / 2.0F);

        float headYaw = netHeadYaw - Math.round(netHeadYaw/180)*180;
        this.head.rotateAngleZ = headYaw * 0.017453292F * -0.5F;

//        System.out.print(headYaw);
//        System.out.println();

        if (!pigModelData.sleeping) {
            this.neck.rotateAngleZ = headYaw * 0.017453292F * -0.5F;
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

        ModelHelper.copyModelRotations(this.neck, this.neckLong);
        ModelHelper.copyModelPositioning(this.neck, neckBigger);

        this.tail0.rotateAngleX = -((float)Math.PI / 2F);

        if (saddleStack!= null && !saddleStack.isEmpty()) {
            Item saddle = saddleStack.getItem();
            if (saddle instanceof CustomizableSaddleWestern) {
                this.saddleWestern.rotateAngleX = -((float) Math.PI / 2F);
                this.saddleWestern.setRotationPoint(0.0F, 4.0F, 10.0F);
                this.saddleSideL.setRotationPoint(5.0F, -1.0F, -5.25F);
                this.saddleSideR.setRotationPoint(-5.0F, -1.0F, -5.25F);
                this.saddleHorn.setRotationPoint(0.0F, -2.0F, -2.0F);
                this.saddleHorn.rotateAngleX = (float) Math.PI / 8.0F;
                this.saddlePomel.setRotationPoint(0.0F, -1.5F, -0.5F);
                this.saddlePomel.rotateAngleX = -0.2F;
                this.stirrup2DWideL.setRotationPoint(7.5F, 0.0F, -3.5F);
                this.stirrup2DWideR.setRotationPoint(-7.5F, 0.0F, -3.5F);
            } else if (saddle instanceof CustomizableSaddleEnglish) {
                this.saddleEnglish.rotateAngleX = -((float) Math.PI / 2F);
                this.saddleEnglish.setRotationPoint(0.0F, 4.0F, 10.0F);
                this.saddleSideL.setRotationPoint(3.25F, -0.5F, -4.0F);
                this.saddleSideR.setRotationPoint(-3.25F, -0.5F, -4.0F);
                this.saddleHorn.setRotationPoint(0.0F, -1.0F, -1.0F);
                this.saddleHorn.rotateAngleX = (float) Math.PI / 4.5F;
                this.stirrup3DNarrowL.setRotationPoint(8.0F, -0.5F, -1.5F);
                this.stirrup3DNarrowR.setRotationPoint(-8.0F, -0.5F, -1.5F);
            } else if (!(saddle instanceof CustomizableCollar)) {
                this.saddle.rotateAngleX = -((float) Math.PI / 2F);
                this.saddle.setRotationPoint(0.0F, 4.0F, 10.0F);
                this.stirrup3DNarrowL.setRotationPoint(8.0F, 0.0F, 0.0F);
                this.stirrup3DNarrowR.setRotationPoint(-8.0F, 0.0F, 0.0F);
            }
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
        ItemStack saddle;
        ItemStack bridle;
        ItemStack harness;
        Boolean collar;
        Boolean hasChest;
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
            pigModelData.saddle = enhancedPig.getEnhancedInventory().getStackInSlot(1);
            pigModelData.bridle = enhancedPig.getEnhancedInventory().getStackInSlot(3);
            pigModelData.harness = enhancedPig.getEnhancedInventory().getStackInSlot(5);
            pigModelData.collar = hasCollar(enhancedPig.getEnhancedInventory());
            pigModelData.hasChest = !enhancedPig.getEnhancedInventory().getStackInSlot(0).isEmpty();


            return pigModelData;
        } else {
            PigModelData pigModelData = new PigModelData();
            if (enhancedPig.getSharedGenes()!=null) {
                pigModelData.pigGenes = enhancedPig.getSharedGenes().getAutosomalGenes();
            }
            pigModelData.size = enhancedPig.getAnimalSize();
            pigModelData.sleeping = enhancedPig.isAnimalSleeping();
            pigModelData.blink = enhancedPig.getBlink();
            pigModelData.uuidArray = enhancedPig.getCachedUniqueIdString().toCharArray();
            pigModelData.birthTime = enhancedPig.getBirthTime();
            pigModelData.clientGameTime = (((WorldInfo)((ClientWorld)enhancedPig.world).getWorldInfo()).getGameTime());
            pigModelData.saddle = enhancedPig.getEnhancedInventory().getStackInSlot(1);
            pigModelData.bridle = enhancedPig.getEnhancedInventory().getStackInSlot(3);
            pigModelData.harness = enhancedPig.getEnhancedInventory().getStackInSlot(5);
            pigModelData.collar = hasCollar(enhancedPig.getEnhancedInventory());
            pigModelData.hasChest = !enhancedPig.getEnhancedInventory().getStackInSlot(0).isEmpty();

            if(pigModelData.pigGenes != null) {
                pigModelDataCache.put(enhancedPig.getEntityId(), pigModelData);
            }

            return pigModelData;
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
