package mokiyoki.enhancedanimals.model;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import mokiyoki.enhancedanimals.entity.EnhancedCow;
import mokiyoki.enhancedanimals.entity.EntityState;
import mokiyoki.enhancedanimals.items.CustomizableSaddleEnglish;
import mokiyoki.enhancedanimals.items.CustomizableSaddleWestern;
import mokiyoki.enhancedanimals.model.util.ModelHelper;
import mokiyoki.enhancedanimals.renderer.EnhancedRendererModelNew;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.world.ClientWorld;
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
public class ModelEnhancedCow <T extends EnhancedCow> extends EntityModel<T> {

//    private final Float hornScaleTest = 2.5F;
//    private final int hornLengthTest = 5;

    private Map<Integer, CowModelData> cowModelDataCache = new HashMap<>();
    private int clearCacheTimer = 0;
    private float headRotationAngleX;

    private final EnhancedRendererModelNew head;
    private final EnhancedRendererModelNew nose;
    private final EnhancedRendererModelNew eyeLeft;
    private final EnhancedRendererModelNew eyeRight;
    private final EnhancedRendererModelNew mouth;
    private final EnhancedRendererModelNew earSmallestL;
    private final EnhancedRendererModelNew earSmallL;
    private final EnhancedRendererModelNew earMediumL;
    private final EnhancedRendererModelNew earLongL;
    private final EnhancedRendererModelNew earLongestL;
    private final EnhancedRendererModelNew earSmallestR;
    private final EnhancedRendererModelNew earSmallR;
    private final EnhancedRendererModelNew earMediumR;
    private final EnhancedRendererModelNew earLongR;
    private final EnhancedRendererModelNew earLongestR;
    private final EnhancedRendererModelNew hornNub1;
    private final EnhancedRendererModelNew hornNub2;
    private final EnhancedRendererModelNew hornNub3;
    private final EnhancedRendererModelNew hornNub4;
    private final EnhancedRendererModelNew hornNub5;
    private final EnhancedRendererModelNew hornGranparent;
    private final EnhancedRendererModelNew hornParent;
    private final EnhancedRendererModelNew hornL0;
    private final EnhancedRendererModelNew hornL1;
    private final EnhancedRendererModelNew hornL2;
    private final EnhancedRendererModelNew hornL3;
    private final EnhancedRendererModelNew hornL4;
    private final EnhancedRendererModelNew hornL5;
    private final EnhancedRendererModelNew hornL6;
    private final EnhancedRendererModelNew hornL7;
    private final EnhancedRendererModelNew hornL8;
    private final EnhancedRendererModelNew hornL9;
    private final EnhancedRendererModelNew hornR0;
    private final EnhancedRendererModelNew hornR1;
    private final EnhancedRendererModelNew hornR2;
    private final EnhancedRendererModelNew hornR3;
    private final EnhancedRendererModelNew hornR4;
    private final EnhancedRendererModelNew hornR5;
    private final EnhancedRendererModelNew hornR6;
    private final EnhancedRendererModelNew hornR7;
    private final EnhancedRendererModelNew hornR8;
    private final EnhancedRendererModelNew hornR9;
    private final EnhancedRendererModelNew neck;
    private final EnhancedRendererModelNew body;
    private final EnhancedRendererModelNew udder;
    private final EnhancedRendererModelNew nipples;
    private final EnhancedRendererModelNew humpXSmall;
    private final EnhancedRendererModelNew humpSmall;
    private final EnhancedRendererModelNew humpSmallish;
    private final EnhancedRendererModelNew humpMedium;
    private final EnhancedRendererModelNew humpLargeish;
    private final EnhancedRendererModelNew humpLarge;
    private final EnhancedRendererModelNew humpXLarge;
    private final EnhancedRendererModelNew tail0;
    private final EnhancedRendererModelNew tail1;
    private final EnhancedRendererModelNew tail2;
    private final EnhancedRendererModelNew tailBrush;
    private final ModelRenderer leg1;
    private final ModelRenderer leg2;
    private final ModelRenderer leg3;
    private final ModelRenderer leg4;
    private final ModelRenderer shortLeg1;
    private final ModelRenderer shortLeg2;
    private final ModelRenderer shortLeg3;
    private final ModelRenderer shortLeg4;
    private final ModelRenderer mushroomBody1;
    private final ModelRenderer mushroomBody2;
    private final EnhancedRendererModelNew mushroomHead;
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
    private final EnhancedRendererModelNew headTassles;

    private final List<EnhancedRendererModelNew> leftHorns = new ArrayList<>();
    private final List<EnhancedRendererModelNew> rightHorns = new ArrayList<>();
    private final List<EnhancedRendererModelNew> saddles = new ArrayList<>();

    private Integer currentCow = null;

    public ModelEnhancedCow() {

        this.textureWidth = 256;
        this.textureHeight = 256;

        this.head = new EnhancedRendererModelNew(this, 0, 33);
        this.head.addBox(-4.0F, 0.0F, -7.0F, 8, 7, 6, 0.0F);
        this.head.setRotationPoint(0.0F, 0.0F, -7.0F);

        this.nose = new EnhancedRendererModelNew(this, 28, 33);
        this.nose.addBox(-2.0F, 0.1F, -11.0F, 4, 5, 4, 0.0F);
        this.nose.setTextureOffset(16, 46);
        this.nose.addBox(-2.5F, 0.2F, -13.0F, 5, 4, 3, 0.0F);


        this.eyeLeft = new EnhancedRendererModelNew(this, 22, 35);
        this.eyeLeft.addBox(0.0F, 0.0F, 0.0F, 1, 2, 2, 0.01F);
        this.eyeLeft.setRotationPoint(3.0F, 2.0F, -7.0F);

        this.eyeRight = new EnhancedRendererModelNew(this, 0, 35);
        this.eyeRight.addBox(0.0F, 0.0F, 0.0F, 1, 2, 2, 0.01F);
        this.eyeRight.setRotationPoint(-4.0F, 2.0F, -7.0F);

        this.mouth = new EnhancedRendererModelNew(this, 25, 46);
        this.mouth.addBox(-1.5F, 1.0F, -10.0F, 3, 3, 7, 0.1F);
        this.mouth.setTextureOffset(37, 46);
        this.mouth.addBox(-1.5F, 3.0F, -10.0F, 3, 1, 6, -0.1F);
        this.mouth.setRotationPoint(0.0F, 4.0F, -2.0F);

        this.earSmallestL = new EnhancedRendererModelNew(this, 8, 46, "SmallestL");
        this.earSmallestL.addBox(0.0F, -3.0F, -0.5F, 3, 3, 1);

        this.earSmallL = new EnhancedRendererModelNew(this, 8, 46, "SmallL");
        this.earSmallL.addBox(0.0F, -4.0F, -0.5F, 3, 4, 1);

        this.earMediumL = new EnhancedRendererModelNew(this, 8, 46, "MediumL");
        this.earMediumL.addBox(0.0F, -5.0F, -0.5F, 3, 5, 1);

        this.earLongL = new EnhancedRendererModelNew(this, 8, 46, "LongL");
        this.earLongL.addBox(0.0F, -6.0F, -0.5F, 3, 6, 1, 0.15F);

        this.earLongestL = new EnhancedRendererModelNew(this, 8, 46, "LongestL");
        this.earLongestL.addBox(0.0F, -7.0F, -0.5F, 3, 7, 1, 0.3F);

        this.earSmallestR = new EnhancedRendererModelNew(this, 0, 46, "SmallestR");
        this.earSmallestR.addBox(-3.0F, -3.0F, -0.5F, 3, 3, 1);

        this.earSmallR = new EnhancedRendererModelNew(this, 0, 46, "SmallR");
        this.earSmallR.addBox(-3.0F, -4.0F, -0.5F, 3, 4, 1);

        this.earMediumR = new EnhancedRendererModelNew(this, 0, 46, "MediumR");
        this.earMediumR.addBox(-3.0F, -5.0F, -0.5F, 3, 5, 1);

        this.earLongR = new EnhancedRendererModelNew(this, 0, 46, "LongR");
        this.earLongR.addBox(-3.0F, -6.0F, -0.5F, 3, 6, 1, 0.15F);

        this.earLongestR = new EnhancedRendererModelNew(this, 0, 46, "LongestR");
        this.earLongestR.addBox(-3.0F, -7.0F, -0.5F, 3, 7, 1, 0.3F);

        this.hornNub1 = new EnhancedRendererModelNew(this, 44, 33, "HornNub1");
        this.hornNub1.addBox(-2.0F, 0.0F, 0.0F, 4, 2, 2);
        this.hornNub1.setRotationPoint(0.0F, 1.0F, -1.0F);

        this.hornNub2 = new EnhancedRendererModelNew(this, 44, 33, "HornNub2");
        this.hornNub2.addBox(-2.0F, 0.0F, 0.0F, 4, 3, 2);
        this.hornNub2.setRotationPoint(0.0F, 1.0F, -1.0F);

        this.hornNub3 = new EnhancedRendererModelNew(this, 44, 33, "HornNub3");
        this.hornNub3.addBox(-2.0F, 0.0F, 0.0F, 4, 4, 2);
        this.hornNub3.setRotationPoint(0.0F, 1.0F, -1.0F);

        this.hornNub4 = new EnhancedRendererModelNew(this, 44, 33, "HornNub4");
        this.hornNub4.addBox(-2.0F, 0.0F, 0.0F, 4, 5, 2);
        this.hornNub4.setRotationPoint(0.0F, 1.0F, -1.0F);

        this.hornNub5 = new EnhancedRendererModelNew(this, 44, 33, "HornNub5");
        this.hornNub5.addBox(-2.0F, 0.0F, 0.0F, 4, 6, 2);
        this.hornNub5.setRotationPoint(0.0F, 1.0F, -1.0F);

        this.hornGranparent = new EnhancedRendererModelNew(this, 0, 0);
        this.hornParent = new EnhancedRendererModelNew(this, 0, 0, "hornParent");
        this.hornGranparent.addChild(hornParent);

        this.hornL0 = new EnhancedRendererModelNew(this, 64, 29, "HornL0");
        this.hornL0.addBox(-2.0F, -4.0F, -2.0F, 4, 4, 4, -1.0F);
        leftHorns.add(hornL0);

        this.hornL1 = new EnhancedRendererModelNew(this, 64, 37, "HornL1");
        this.hornL1.addBox(-2.0F, -4.0F, -2.0F, 4, 4, 4, -1.001F);
        leftHorns.add(hornL1);

        this.hornL2 = new EnhancedRendererModelNew(this, 64, 45, "HornL2");
        this.hornL2.addBox(-2.0F, -4.0F, -2.0F, 4, 4, 4, -1.002F);
        leftHorns.add(hornL2);

        this.hornL3 = new EnhancedRendererModelNew(this, 64, 53, "HornL3");
        this.hornL3.addBox(-2.0F, -4.0F, -2.0F, 4, 4, 4, -1.003F);
        leftHorns.add(hornL3);

        this.hornL4 = new EnhancedRendererModelNew(this, 64, 61, "HornL4");
        this.hornL4.addBox(-2.0F, -4.0F, -2.0F, 4, 4, 4, -1.004F);
        leftHorns.add(hornL4);

        this.hornL5 = new EnhancedRendererModelNew(this, 64, 61, "HornL5");
        this.hornL5.addBox(-2.0F, -4.0F, -2.0F, 4, 4, 4, -1.1F);
        leftHorns.add(hornL5);

        this.hornL6 = new EnhancedRendererModelNew(this, 64, 61, "HornL6");
        this.hornL6.addBox(-2.0F, -4.0F, -2.0F, 4, 4, 4, -1.2F);
        leftHorns.add(hornL6);

        this.hornL7 = new EnhancedRendererModelNew(this, 64, 61, "HornL7");
        this.hornL7.addBox(-2.0F, -4.0F, -2.0F, 4, 4, 4, -1.3F);
        leftHorns.add(hornL7);

        this.hornL8 = new EnhancedRendererModelNew(this, 64, 61, "HornL8");
        this.hornL8.addBox(-2.0F, -4.0F, -2.0F, 4, 4, 4, -1.4F);
        leftHorns.add(hornL8);

        this.hornL9 = new EnhancedRendererModelNew(this, 64, 61, "HornL9");
        this.hornL9.addBox(-2.0F, -4.0F, -2.0F, 4, 4, 4, -1.5F);
        leftHorns.add(hornL9);

        this.hornR0 = new EnhancedRendererModelNew(this, 64, 29, "HornR0");
        this.hornR0.addBox(-2.0F, -4.0F, -2.0F, 4, 4, 4, -1.0F);
        rightHorns.add(hornR0);

        this.hornR1 = new EnhancedRendererModelNew(this, 64, 37, "HornR1");
        this.hornR1.addBox(-2.0F, -4.0F, -2.0F, 4, 4, 4, -1.001F);
        rightHorns.add(hornR1);

        this.hornR2 = new EnhancedRendererModelNew(this, 64, 45, "HornR2");
        this.hornR2.addBox(-2.0F, -4.0F, -2.0F, 4, 4, 4, -1.002F);
        rightHorns.add(hornR2);

        this.hornR3 = new EnhancedRendererModelNew(this, 64, 53, "HornR3");
        this.hornR3.addBox(-2.0F, -4.0F, -2.0F, 4, 4, 4, -1.003F);
        rightHorns.add(hornR3);

        this.hornR4 = new EnhancedRendererModelNew(this, 64, 61, "HornR4");
        this.hornR4.addBox(-2.0F, -4.0F, -2.0F, 4, 4, 4, -1.004F);
        rightHorns.add(hornR4);

        this.hornR5 = new EnhancedRendererModelNew(this, 64, 61, "HornR5");
        this.hornR5.addBox(-2.0F, -4.0F, -2.0F, 4, 4, 4, -1.1F);
        rightHorns.add(hornR5);

        this.hornR6 = new EnhancedRendererModelNew(this, 64, 61, "HornR6");
        this.hornR6.addBox(-2.0F, -4.0F, -2.0F, 4, 4, 4, -1.2F);
        rightHorns.add(hornR6);

        this.hornR7 = new EnhancedRendererModelNew(this, 64, 61, "HornR7");
        this.hornR7.addBox(-2.0F, -4.0F, -2.0F, 4, 4, 4, -1.3F);
        rightHorns.add(hornR7);

        this.hornR8 = new EnhancedRendererModelNew(this, 64, 61, "HornR8");
        this.hornR8.addBox(-2.0F, -4.0F, -2.0F, 4, 4, 4, -1.4F);
        rightHorns.add(hornR8);

        this.hornR9 = new EnhancedRendererModelNew(this, 64, 61, "HornR9");
        this.hornR9.addBox(-2.0F, -4.0F, -2.0F, 4, 4, 4, -1.5F);
        rightHorns.add(hornR9);

        this.neck = new EnhancedRendererModelNew(this, 46, 0);
        this.neck.addBox(-3.0F, 0.0F, -8.0F, 6, 8, 11, 0.0F);
        this.neck.setRotationPoint(0.0F, 0.0F, -10.0F);

        this.body = new EnhancedRendererModelNew(this, 0, 0);
        this.body.addBox(-6.0F, 0.0F, 0.0F, 12, 11, 22, 0.0F);
        this.body.setRotationPoint(0.0F, 2.5F, -10.0F);

        this.udder = new EnhancedRendererModelNew(this, 24, 67, "Udder");
        this.udder.addBox(-2.0F, -2.0F, -5.0F, 4, 4, 6, 0.0F);
        this.udder.setRotationPoint(0.0F, 10.0F, 21.25F);

        this.nipples = new EnhancedRendererModelNew(this, 24, 77, "Nipples");
        this.nipples.addBox(-2.0F, 0.0F, -1.0F, 1, 2, 1, -0.15F);
        this.nipples.setTextureOffset(29, 77);
        this.nipples.addBox(1.0F, 0.0F, -1.0F, 1, 2, 1, -0.15F);
        this.nipples.setTextureOffset(35, 77);
        this.nipples.addBox(-2.0F, 0.0F, 2.0F, 1, 2, 1, -0.15F);
        this.nipples.setTextureOffset(40, 77);
        this.nipples.addBox(1.0F, 0.0F, 2.0F, 1, 2, 1, -0.15F);
        this.nipples.setRotationPoint(0.0F, 1.5F, -3.5F);

        this.humpXSmall = new EnhancedRendererModelNew(this, 0, 8, "HumpXXS");
        this.humpXSmall.addBox(-2.0F, 0.0F, 0.0F, 4, 8, 6, -1.0F);
        this.humpXSmall.setRotationPoint(0.0F, 0.0F, 1.0F);

        this.humpSmall = new EnhancedRendererModelNew(this, 0, 8, "HumpXS");
        this.humpSmall.addBox(-2.0F, 0.0F, 0.0F, 4, 8, 6, -0.5F);
        this.humpSmall.setRotationPoint(0.0F, 0.0F, 1.0F);

        this.humpSmallish = new EnhancedRendererModelNew(this, 0, 8, "HumpS");
        this.humpSmallish.addBox(-2.0F, 0.0F, 0.0F, 4, 8, 6, -0.25F);
        this.humpSmallish.setRotationPoint(0.0F, 0.0F, 1.0F);

        this.humpMedium = new EnhancedRendererModelNew(this, 0, 8, "Hump");
        this.humpMedium.addBox(-2.0F, 0.0F, 0.0F, 4, 8, 6, 0.0F);
        this.humpMedium.setRotationPoint(0.0F, 0.0F, 1.0F);

        this.humpLargeish = new EnhancedRendererModelNew(this, 0, 8, "HumpL");
        this.humpLargeish.addBox(-2.0F, 0.0F, 0.0F, 4, 8, 6, 0.5F);
        this.humpLargeish.setRotationPoint(0.0F, 0.0F, 1.0F);

        this.humpLarge = new EnhancedRendererModelNew(this, 0, 8, "HumpXL");
        this.humpLarge.addBox(-2.0F, 0.0F, 0.0F, 4, 8, 6, 1.0F);
        this.humpLarge.setRotationPoint(0.0F, 0.0F, 1.0F);

        this.humpXLarge = new EnhancedRendererModelNew(this, 0, 8, "HumpXXL");
        this.humpXLarge.addBox(-2.0F, 0.0F, 0.0F, 4, 8, 6, 1.5F);
        this.humpXLarge.setRotationPoint(0.0F, 0.0F, 1.0F);

        this.tail0 = new EnhancedRendererModelNew(this, 0,0, "Tail");
        this.tail0.addBox(-1.0F, 0.0F, 0.0F, 2, 4, 1);
        this.tail0.setRotationPoint(0.0F, 0.0F, 22.0F);

        this.tail1 = new EnhancedRendererModelNew(this, 6,0);
        this.tail1.addBox(-0.5F, 0.0F, 0.0F, 1, 4, 1);
        this.tail1.setRotationPoint(0.0F, 4.0F, 0.0F);

        this.tail2 = new EnhancedRendererModelNew(this, 10,0);
        this.tail2.addBox(-0.5F, 0.0F, 0.0F, 1, 3, 1, -0.01F);
        this.tail2.setRotationPoint(0.0F, 3.0F, 0.0F);

        this.tailBrush = new EnhancedRendererModelNew(this, 14,0);
        this.tailBrush.addBox(-1.0F, 0.0F, -0.5F, 2, 3, 2);
        this.tailBrush.setRotationPoint(0.0F, 3.0F, 0.0F);

        this.leg1 = new ModelRenderer(this, 0, 54);
        this.leg1.addBox(0.0F, 0.0F, 0.0F, 3, 10, 3, 0.0F);
        this.leg1.setRotationPoint(-6.0F, 13.5F, -10.0F);

        this.leg2 = new ModelRenderer(this, 12, 54);
        this.leg2.addBox(0.0F, 0.0F, 0.0F, 3, 10, 3, 0.0F);
        this.leg2.setRotationPoint(3.0F, 13.5F, -10.0F);

        this.leg3 = new ModelRenderer(this, 0, 67);
        this.leg3.addBox(0.0F, 0.0F, 0.0F, 3, 10, 3, 0.0F);
        this.leg3.setRotationPoint(-6.0F, 13.5F, 9.0F);

        this.leg4 = new ModelRenderer(this, 12, 67);
        this.leg4.addBox(0.0F, 0.0F, 0.0F, 3, 10, 3, 0.0F);
        this.leg4.setRotationPoint(3.0F, 13.5F, 9.0F);

        this.shortLeg1 = new ModelRenderer(this, 0, 54);
        this.shortLeg1.addBox(0.0F, 0.0F, 0.0F, 3, 7, 3, 0.0F);
        this.shortLeg1.setRotationPoint(-6.0F, 13.5F, -10.0F);

        this.shortLeg2 = new ModelRenderer(this, 12, 54);
        this.shortLeg2.addBox(0.0F, 0.0F, 0.0F, 3, 7, 3, 0.0F);
        this.shortLeg2.setRotationPoint(3.0F, 13.5F, -10.0F);

        this.shortLeg3 = new ModelRenderer(this, 0, 67);
        this.shortLeg3.addBox(0.0F, 0.0F, 0.0F, 3, 7, 3, 0.0F);
        this.shortLeg3.setRotationPoint(-6.0F, 13.5F, 9.0F);

        this.shortLeg4 = new ModelRenderer(this, 12, 67);
        this.shortLeg4.addBox(0.0F, 0.0F, 0.0F, 3, 7, 3, 0.0F);
        this.shortLeg4.setRotationPoint(3.0F, 13.5F, 9.0F);

        this.mushroomBody1 = new ModelRenderer(this, 54, 64);
        this.mushroomBody1.addBox(0.0F, -8.0F, 0.0F, 1, 8, 8);
        this.mushroomBody1.setTextureOffset(62, 71);
        this.mushroomBody1.addBox(-3.0F, -8.0F, 4.0F, 8, 8, 1);
        this.mushroomBody1.setRotationPoint(-3.0F, 0.0F, -5.0F);

        this.mushroomBody2 = new ModelRenderer(this, 54, 64);
        this.mushroomBody2.addBox(0.0F, -8.0F, 0.0F, 1, 8, 8);
        this.mushroomBody2.setTextureOffset(62, 71);
        this.mushroomBody2.addBox(-3.0F, -8.0F, 4.0F, 8, 8, 1);
        this.mushroomBody2.setRotationPoint(3.0F, 0.0F, 5.0F);

        this.mushroomHead = new EnhancedRendererModelNew(this, 54, 64);
        this.mushroomHead.addBox(0.0F, -8.0F, 0.0F, 1, 8, 8);
        this.mushroomHead.setTextureOffset(62, 71);
        this.mushroomHead.addBox(-3.0F, -8.0F, 4.0F, 8, 8, 1);
        this.mushroomHead.setRotationPoint(0.0F, -0.5F, -6.0F);

        this.neck.addChild(this.head);
        this.head.addChild(this.earLongestL);
        this.head.addChild(this.earLongL);
        this.head.addChild(this.earMediumL);
        this.head.addChild(this.earSmallL);
        this.head.addChild(this.earSmallestL);
        this.head.addChild(this.earLongestR);
        this.head.addChild(this.earLongR);
        this.head.addChild(this.earMediumR);
        this.head.addChild(this.earSmallR);
        this.head.addChild(this.earSmallestR);
        this.head.addChild(this.nose);
        this.head.addChild(this.mouth);
        this.head.addChild(this.eyeLeft);
        this.head.addChild(this.eyeRight);
        this.head.addChild(this.hornNub1);
        this.head.addChild(this.hornNub2);
        this.head.addChild(this.hornNub3);
        this.head.addChild(this.hornNub4);
        this.head.addChild(this.hornNub5);
        this.hornParent.addChild(this.hornL0);
        this.hornL0.addChild(this.hornL1);
        this.hornL1.addChild(this.hornL2);
        this.hornL2.addChild(this.hornL3);
        this.hornL3.addChild(this.hornL4);
        this.hornL4.addChild(this.hornL5);
        this.hornL5.addChild(this.hornL6);
        this.hornL6.addChild(this.hornL7);
        this.hornL7.addChild(this.hornL8);
        this.hornL8.addChild(this.hornL9);
        this.hornParent.addChild(this.hornR0);
        this.hornR0.addChild(this.hornR1);
        this.hornR1.addChild(this.hornR2);
        this.hornR2.addChild(this.hornR3);
        this.hornR3.addChild(this.hornR4);
        this.hornR4.addChild(this.hornR5);
        this.hornR5.addChild(this.hornR6);
        this.hornR6.addChild(this.hornR7);
        this.hornR7.addChild(this.hornR8);
        this.hornR8.addChild(this.hornR9);
        this.body.addChild(this.humpXSmall);
        this.body.addChild(this.humpSmall);
        this.body.addChild(this.humpSmallish);
        this.body.addChild(this.humpMedium);
        this.body.addChild(this.humpLargeish);
        this.body.addChild(this.humpLarge);
        this.body.addChild(this.humpXLarge);
        this.body.addChild(this.tail0);
        this.tail0.addChild(this.tail1);
        this.tail1.addChild(this.tail2);
        this.tail2.addChild(this.tailBrush);
        this.body.addChild(this.udder);
        this.udder.addChild(this.nipples);

        this.head.addChild(this.mushroomHead);

        /**
         * Equipment stuff
         */

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

        this.headTassles = new EnhancedRendererModelNew(this, 25, 56);
        this.headTassles.addBox(1.0F, 0.0F, -5.0F, 4, 9, 1);
        this.headTassles.setTextureOffset(34, 56);
        this.headTassles.addBox(1.0F, 0.0F, 4.0F, 4, 9, 1);


        this.body.addChild(this.saddle);
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
        this.saddle.addChild(saddlePad);
        this.saddle.addChild(this.stirrup3DNarrowL);
        this.saddle.addChild(this.stirrup3DNarrowR);

        //blanket deco
        this.head.addChild(this.headTassles);
    }

    public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        CowModelData cowModelData = getCowModelData();

        int[] genes = cowModelData.cowGenes;
        float horns = calculateHorns(genes, cowModelData.uuidArray);

        boolean dwarf = false;
        float bodyWidth = -0.165F;
        float bodyLength = 0.0F;
        int hump = 0;

        //size [0.75 to 2.5]
        float hornScale = 1.0F;
        if(genes != null) {
            if (genes[26] == 1 || genes[27] == 1){
                //dwarf
                dwarf = true;
            }

            for (int i = 1; i < genes[54]; i++){
                bodyWidth = bodyWidth + 0.0825F;
            }
            for (int i = 1; i < genes[55]; i++){
                bodyWidth = bodyWidth + 0.0825F;
            }

            if (bodyWidth >= 0.0F) {
                bodyLength = bodyWidth;
            }

            for (int i = 1; i < genes[38]; i++){
                hump++;
            }

            for (int i = 1; i < genes[39]; i++){
                hump++;
            }

            if (horns != 0.0F) {
                if (horns == -1.0F) {
                    //normal horns
                    for (int i = 86; i <= 89; i++){
                        if (genes[i] == 2) {
                            hornScale = hornScale * 1.15F;
                        }
                    }
                    if (genes[90] == 2) {
                        hornScale = hornScale * 1.25F;
                    }
                    if (genes[91] == 2) {
                        hornScale = hornScale * 1.25F;
                    }
                    if (genes[80] >= 3) {
                        hornScale = hornScale * 0.95F;
                    }
                    if (genes[81] >= 3) {
                        hornScale = hornScale * 0.95F;
                    }
                } else {
                    //scurs
                    hornScale = (hornScale + 0.75F) * 0.5F;
                }
            }
        }

        float age = 1.0F;
        float babyScale = 1.0F;
        if (!(cowModelData.birthTime == null) && !cowModelData.birthTime.equals("") && !cowModelData.birthTime.equals("0")) {
            int ageTime = (int)(cowModelData.clientGameTime - Long.parseLong(cowModelData.birthTime));
            if (ageTime <= 108000) {
                age = ageTime/108000.0F;
                babyScale = (3.0F - age)/2;
            }
        }

        float d = 0.0F;
        if (!cowModelData.sleeping) {
            if (dwarf) {
                d = 0.2F * (1.0F-age);
            } else {
                d = 0.3F * (1.0F-age);
            }
        } else {
            babyScale = 1.0F;
        }
        float finalCowSize = ((( 2.0F * age) + 1.0F) / 3.0F)*cowModelData.cowSize;
        bodyWidth = finalCowSize + (finalCowSize * bodyWidth);
        bodyLength = finalCowSize + (finalCowSize * bodyLength);

        matrixStackIn.push();
        matrixStackIn.scale(bodyWidth, finalCowSize, bodyLength);
        matrixStackIn.translate(0.0F, (-1.45F + 1.45F / (finalCowSize)) - d, 0.0F);

        renderHump(hump, cowModelData.unrenderedModels, matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);

        renderBodySaddleAndUdder(cowModelData.saddle, cowModelData.cowStatus, cowModelData.bagSize, cowModelData.unrenderedModels, matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);

        this.neck.render(matrixStackIn, bufferIn , null, cowModelData.unrenderedModels, true, packedLightIn, packedOverlayIn, red, green, blue, alpha);

        renderHorns(horns, hornScale, cowModelData.unrenderedModels, matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);

        if (!this.isChild) {
            this.mushroomBody1.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            this.mushroomBody2.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            this.mushroomHead.showModel = true;
        } else {
            this.mushroomHead.showModel = false;
        }

        matrixStackIn.pop();

        matrixStackIn.push();
        matrixStackIn.scale(bodyWidth, finalCowSize * babyScale, bodyLength);
        matrixStackIn.translate(0.0F, -1.45F + 1.45F / (finalCowSize * babyScale), 0.0F);

        renderLegs(dwarf, matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);

        matrixStackIn.pop();

//        matrixStackIn.push();
//        //TODO fix up cow necks/heads
////        Map<String, List<Float>> mapOfScale = new HashMap<>();
////
////        List<Float> scalings = new ArrayList<>();
////
////        //scaling
////        scalings.add(bodyWidth);
////        scalings.add(finalCowSize);
////        scalings.add(bodyLength);
////        //translations
////        scalings.add(0.0F);
////        scalings.add((-1.45F + 1.45F / (finalCowSize)) - d);
////        scalings.add(0.0F);
////        mapOfScale.put("Neck", scalings);
//
//        matrixStackIn.pop();

        if (cowModelData.blink >= 6) {
            this.eyeLeft.showModel = true;
            this.eyeRight.showModel = true;
        } else {
            this.eyeLeft.showModel = false;
            this.eyeRight.showModel = false;
        }

    }


    private void renderBodySaddleAndUdder(ItemStack saddleStack, String cowStatus, float bagSize, List<String> unrenderedModels, MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        float nipScale = 1.5F/(0.5F+bagSize);
        Map<String, List<Float>> mapOfScale = new HashMap<>();
        List<Float> scalingsForUdder = ModelHelper.createScalings(bagSize, bagSize, bagSize, 0.0F, -(bagSize-1.0F)*0.4F, -(bagSize-1.0F)*0.85F);
        List<Float> scalingsForNipples = ModelHelper.createScalings(nipScale, nipScale, nipScale, 0.0F, (bagSize-1.0F)*0.05F, 0.0F);
        mapOfScale.put("Udder", scalingsForUdder);
        mapOfScale.put("Nipples", scalingsForNipples);

        if ((!cowStatus.equals(EntityState.PREGNANT.toString()) && !cowStatus.equals(EntityState.MOTHER.toString()))) {
            unrenderedModels.add("Udder");
            unrenderedModels.add("Nipples");
        }

        this.saddleWestern.showModel = false;
        this.saddleEnglish.showModel = false;
        this.saddle.showModel = false;
        this.saddlePomel.showModel = false;

        if (!saddleStack.isEmpty()) {
            Item saddle = saddleStack.getItem();
            float saddleScale = 0.875F;
//            float antiScale = 1.25F;
            List<Float> scalingsForSaddle = ModelHelper.createScalings(saddleScale, saddleScale, saddleScale, 0.0F, -saddleScale*0.01F, (saddleScale - 1.0F)*0.04F);
//            List<Float> scalingsForPad = createScalings(antiScale, 0.0F, -antiScale*0.01F, (antiScale - 1.0F)*0.04F);
//            mapOfScale.put("SaddlePad", scalingsForPad);

            if (saddle instanceof CustomizableSaddleWestern) {
                this.saddleWestern.showModel = true;
                this.saddlePomel.showModel = true;
                mapOfScale.put("WesternSaddle", scalingsForSaddle);
            } else if (saddle instanceof CustomizableSaddleEnglish) {
                this.saddleEnglish.showModel = true;
                mapOfScale.put("EnglishSaddle", scalingsForSaddle);
            } else {
                this.saddle.showModel = true;
                mapOfScale.put("Saddle", scalingsForSaddle);
            }
        }

        this.body.render(matrixStackIn, bufferIn , mapOfScale, unrenderedModels, false, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    }

    private void renderHorns(float horns, float hornScale, List<String> unrenderedModels, MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        if (horns != 0.0F) {
            Map<String, List<Float>> mapOfScale = new HashMap<>();
//            hornScale = hornScaleTest;
//            List<Float> scalingsForHorn = ModelHelper.createScalings(hornScale, hornScale, hornScale,0.0F, -hornScale*0.01F, (hornScale - 1.0F)*0.04F);
            List<Float> scalingsForHorn = ModelHelper.createScalings(hornScale, hornScale, hornScale,0.0F, 0.0F, 0.0F);
            mapOfScale.put("HornL0", scalingsForHorn);
            mapOfScale.put("HornR0", ModelHelper.mirrorX(scalingsForHorn));
            this.hornGranparent.render(matrixStackIn, bufferIn , mapOfScale, unrenderedModels, false, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        }
    }

    private void renderLegs(boolean dwarf, MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        if (dwarf) {
            ImmutableList.of(this.shortLeg1, this.shortLeg2, this.shortLeg3, this.shortLeg4).forEach((renderer) -> {
                renderer.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            });
        } else {
            ImmutableList.of(this.leg1, this.leg2, this.leg3, this.leg4).forEach((renderer) -> {
                renderer.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            });
        }
    }

    private void renderHump(int hump, List<String> unrenderedModels, MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        unrenderedModels.add("HumpXXS");
        unrenderedModels.add("HumpXS");
        unrenderedModels.add("HumpS");
        unrenderedModels.add("Hump");
        unrenderedModels.add("HumpL");
        unrenderedModels.add("HumpXL");
        unrenderedModels.add("HumpXXL");

        if(hump == 12){
            unrenderedModels.remove("HumpXXL");
        }else if (hump >= 10){
            unrenderedModels.remove("HumpXL");
        }else if (hump >= 8){
            unrenderedModels.remove("HumpL");
        }else if (hump >= 6){
            unrenderedModels.remove("Hump");
        }else if (hump >= 4){
            unrenderedModels.remove("HumpS");
        }else if (hump >= 2){
            unrenderedModels.remove("HumpXS");
        }else if (hump == 1){
            unrenderedModels.remove("HumpXXS");
        }
    }

    private void renderTail(float scale, float cowSize, float babyScale, List<String> unrenderedModels) {
            Map<String, List<Float>> mapOfScale = new HashMap<>();

            //TODO update scalings to use X, Y, Z
            List<Float> scalingsForTail = ModelHelper.createScalings(cowSize, cowSize, cowSize, 0.0F, (-1.45F + 1.45F / (cowSize*babyScale)), 0.0F);
            mapOfScale.put("Tail", scalingsForTail);
    }

    @Override
    public void setLivingAnimations(T entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTickTime) {
        super.setLivingAnimations(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTickTime);
        CowModelData cowModelData = getCreateCowModelData(entitylivingbaseIn);
        this.currentCow = entitylivingbaseIn.getEntityId();

        int[] sharedGenes = cowModelData.cowGenes;
        float onGround = 2.75F;
        boolean dwarf = false;

        if(sharedGenes != null) {
             dwarf = (sharedGenes[26] == 1 || sharedGenes[27] == 1);
        }

        if (cowModelData.sleeping) {
            onGround = sleepingAnimation();
        } else {
            onGround = standingAnimation(dwarf);
        }
        this.neck.rotationPointY = onGround + (entitylivingbaseIn).getHeadRotationPointY(partialTickTime) * 9.0F;
        this.headRotationAngleX = (entitylivingbaseIn).getHeadRotationAngleX(partialTickTime);
    }

    @Override
    public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        CowModelData cowModelData = getCowModelData();
        ItemStack saddleStack = getCowModelData().saddle;
        List<String> unrenderedModels = new ArrayList<>();

        this.neck.rotateAngleX = (headPitch * 0.017453292F);
        this.neck.rotateAngleY = netHeadYaw * 0.017453292F;
//        this.neck.rotateAngleY = (float)Math.PI/2.0F;

        this.humpXLarge.rotateAngleX = ((headPitch * 0.017453292F)/2.5F) - 0.2F;
        this.humpXLarge.rotateAngleY = (netHeadYaw * 0.017453292F)/2.5F;

        ModelHelper.copyModelRotations(humpXLarge, humpLarge);
        ModelHelper.copyModelRotations(humpXLarge, humpLargeish);
        ModelHelper.copyModelRotations(humpXLarge, humpMedium);
        ModelHelper.copyModelRotations(humpXLarge, humpSmallish);
        ModelHelper.copyModelRotations(humpXLarge, humpSmall);
        ModelHelper.copyModelRotations(humpXLarge, humpXSmall);

        if (!cowModelData.sleeping) {
            this.leg1.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
            this.leg2.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
            this.leg3.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
            this.leg4.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        }

        ModelHelper.copyModelAngles(leg1, shortLeg1);
        ModelHelper.copyModelAngles(leg2, shortLeg2);
        ModelHelper.copyModelAngles(leg3, shortLeg3);
        ModelHelper.copyModelAngles(leg4, shortLeg4);

        this.neck.rotateAngleX = this.headRotationAngleX;
        this.head.rotateAngleX = 0.5F;
        this.mouth.rotateAngleX = -0.3F + (this.headRotationAngleX / 2.0F);

//        this.head.rotateAngleX = 0.05F + this.head.rotateAngleX;   //might need to merge this with another line

        this.tail0.rotateAngleZ = MathHelper.cos(limbSwing * 0.6662F) * 0.4F * limbSwingAmount;
        this.tail1.rotateAngleZ = MathHelper.cos(limbSwing * 0.6662F) * 0.4F * limbSwingAmount;
        this.tail2.rotateAngleZ = MathHelper.cos(limbSwing * 0.6662F) * 0.45F * limbSwingAmount;
        this.tailBrush.rotateAngleZ = MathHelper.cos(limbSwing * 0.6662F) * 0.6F * limbSwingAmount;

        this.tail0.rotateAngleX = 0.4F;
        this.tail1.rotateAngleX = -0.2F;
        this.tail2.rotateAngleX = -0.2F;
        this.tailBrush.rotateAngleX = 0F;

        setEarRotations(cowModelData.cowGenes, unrenderedModels);

        setHornRotations(cowModelData, unrenderedModels, entityIn);

        setHumpPlacement(cowModelData.cowGenes);

        cowModelData.unrenderedModels.addAll(unrenderedModels);

        if (!saddleStack.isEmpty()) {
            Item saddle = saddleStack.getItem();
            if (saddle instanceof CustomizableSaddleWestern) {
                this.saddleWestern.rotationPointZ = 9.0F;
                this.saddleSideL.setRotationPoint(5.0F, -1.0F, -5.25F);
                this.saddleSideR.setRotationPoint(-5.0F, -1.0F, -5.25F);
                this.saddleHorn.setRotationPoint(0.0F, -2.0F, -2.0F);
                this.saddleHorn.rotateAngleX = (float) Math.PI / 8.0F;
                this.saddlePomel.setRotationPoint(0.0F, -1.5F, -0.5F);
                this.saddlePomel.rotateAngleX = -0.2F;
                this.stirrup2DWideL.setRotationPoint(7.5F, 0.0F, -3.5F);
                this.stirrup2DWideR.setRotationPoint(-7.5F, 0.0F, -3.5F);
            } else if (saddle instanceof CustomizableSaddleEnglish) {
                this.saddleEnglish.rotationPointZ = 9.0F;
                this.saddleSideL.setRotationPoint(3.25F, -0.5F, -4.0F);
                this.saddleSideR.setRotationPoint(-3.25F, -0.5F, -4.0F);
                this.saddleHorn.setRotationPoint(0.0F, -1.0F, -1.0F);
                this.saddleHorn.rotateAngleX = (float) Math.PI / 4.5F;
                this.stirrup3DNarrowL.setRotationPoint(7.25F, -0.25F, -1.5F);
                this.stirrup3DNarrowR.setRotationPoint(-7.25F, -0.25F, -1.5F);
            } else {
                this.saddle.rotationPointZ = 9.0F;
                this.stirrup3DNarrowL.setRotationPoint(8.0F, 0.0F, 0.0F);
                this.stirrup3DNarrowR.setRotationPoint(-8.0F, 0.0F, 0.0F);
            }
        }

        this.headTassles.rotateAngleY = (float)Math.PI/2.0F;
    }

    private void setHornRotations(CowModelData cowModelData, List<String> unrenderedModels, T entityIn) {

        int[] genes = cowModelData.cowGenes;
        char[] uuidArray = cowModelData.uuidArray;


        this.hornNub1.rotateAngleX = ((float)Math.PI / -2F);
        this.hornNub2.rotateAngleX = ((float)Math.PI / -2F);
        this.hornNub3.rotateAngleX = ((float)Math.PI / -2F);
        this.hornNub4.rotateAngleX = ((float)Math.PI / -2F);
        this.hornNub5.rotateAngleX = ((float)Math.PI / -2F);

        ModelHelper.copyModelAngles(neck, hornGranparent);
        ModelHelper.copyModelAngles(head, hornParent);


        float hornBaseHight = -1.0F;

        if(genes != null) {
            if (genes[70] == 2) {
                hornBaseHight = hornBaseHight + 0.05F;
            }
            if (genes[71] == 2) {
                hornBaseHight = hornBaseHight + 0.05F;
            }
            if (genes[72] != 1) {
                hornBaseHight = hornBaseHight + 0.1F;
            }
            if (genes[73] != 1) {
                hornBaseHight = hornBaseHight + 0.1F;
            }
            if (genes[72] == 3 && genes[73] == 3) {
                hornBaseHight = hornBaseHight + 0.1F;
            }
            if (genes[74] == 1) {
                hornBaseHight = hornBaseHight * 0.75F;
            }
            if (genes[75] == 1) {
                hornBaseHight = hornBaseHight * 0.75F;
            }
        }

        hornParent.rotationPointY = hornBaseHight;

        hornL0.setRotationPoint(0.0F, 0.0F, -2.25F);
        hornR0.setRotationPoint(0.0F, 0.0F, -2.25F);

        float horns = calculateHorns(genes, uuidArray);

        float hornScale = 1.0F;
        if (horns != 0.0F) {
            if (horns == -1.0F) {
                //normal horns
                for (int i = 86; i <= 89; i++){
                    if (genes[i] == 2) {
                        hornScale = hornScale * 1.15F;
                    }
                }
                if (genes[90] == 2) {
                    hornScale = hornScale * 1.25F;
                }
                if (genes[91] == 2) {
                    hornScale = hornScale * 1.25F;
                }
                if (genes[80] >= 3) {
                    hornScale = hornScale * 0.95F;
                }
                if (genes[81] >= 3) {
                    hornScale = hornScale * 0.95F;
                }
            } else {
                //scurs
                hornScale = (hornScale + 0.75F) * 0.5F;
            }
        }

        Float[] hornGrowthL = {-1.0F, -2.0F, -2.0F, -2.0F, -2.0F, -1.8F, -1.6F, -1.4F, -1.2F, -1.0F};
        Float[] hornGrowthR = {-1.0F, -2.0F, -2.0F, -2.0F, -2.0F, -1.8F, -1.6F, -1.4F, -1.2F, -1.0F};

//        Float[] hornGrowthL = {-1.0F, -1.0F, -1.0F, -1.0F, -1.0F, -1.0F, -1.0F, -1.0F, -1.0F, -1.0F};
//        Float[] hornGrowthR = {-1.0F, -2.0F, -2.0F, -2.0F, -2.0F, -1.8F, -1.6F, -1.4F, -1.2F, -1.0F};

        if (horns != 0.0F) {

            int lengthL = 2;

            if(genes != null) {
                if (genes[80] != 2 || genes[81] != 2 ) {
                    if (genes[80] >= 3 || genes[81] >= 3 ) {
                        if (genes[80] == 4 && genes[81] == 4) {
                            lengthL = -1;
                        } else {
                            lengthL = 1;
                        }
                    } else {
                        lengthL = 0;
                    }
                }
            }

            int lengthR = lengthL;

            if (horns != -1.0){
                lengthL = lengthL + 5;
                lengthR = lengthR + 5;

                if (uuidArray != null) {
                    if (Character.isDigit(uuidArray[4])) {
                        if ((uuidArray[4] - 48) <= 3 ) {
                            //shorten left horn
                            lengthL = lengthL + (uuidArray[4] - 48);
                        } else if ((uuidArray[4] - 48) <= 7 ) {
                            //shorten right horn
                            lengthR = lengthR + (uuidArray[4] - 52);
                        } else {
                            // shorten evenly
                            lengthL = lengthL + (uuidArray[4] - 55);
                            lengthR = lengthL;
                        }
                    } else {
                        char a = uuidArray[4];
                        switch (a) {
                            case 'a':
                                lengthL = lengthL + 1;
                                lengthR = lengthR + 2;
                            case 'b':
                                lengthL = lengthL + 2;
                                lengthR = lengthR + 1;
                            case 'c':
                                lengthL = lengthL + 1;
                                lengthR = lengthR + 3;
                            case 'd':
                                lengthL = lengthL + 3;
                                lengthR = lengthR + 1;
                            case 'e':
                                lengthL = lengthL + 2;
                                lengthR = lengthR + 3;
                            case 'f':
                                lengthL = lengthL + 3;
                                lengthR = lengthR + 2;
                        }
                    }
                }

            } else {
                if(genes != null) {
                    //if horns are not scurred shorten horns if they are extra thicc
                    if (genes[82] == 2) {
                        lengthL = lengthL + 1;
                    }
                    if (genes[83] == 2 ){
                        lengthL = lengthL + 1 ;
                    }

                    if (genes[88] == 2) {
                        lengthL = lengthL + 1;
                    }
                    if (genes[89] == 2) {
                        lengthL = lengthL + 1;
                    }
                    if (genes[90] == 2 || genes[91] == 2) {
                        lengthL = lengthL + 2;
                    }
                }

                lengthR = lengthL;
            }

            if (lengthL > 9) {
                lengthL = 9;
            }
            if (lengthR > 9) {
                lengthR = 9;
            }

            if (cowModelData.birthTime != null && !cowModelData.birthTime.equals("") && !cowModelData.birthTime.equals("0")) {
                int ageTime = (int)(cowModelData.clientGameTime - Long.parseLong(cowModelData.birthTime));
                if (ageTime < 108000) {
                    if (ageTime > 97200) {
                        lengthL += 1;
                        lengthR += 1;
                    } else if (ageTime > 86400) {
                        lengthL += 2;
                        lengthL += 2;
                    } else if (ageTime > 75600) {
                        lengthL += 3;
                        lengthL += 3;
                    } else if (ageTime > 60800) {
                        lengthL += 4;
                        lengthR += 4;
                    } else if (ageTime > 40000) {
                        lengthL += 5;
                        lengthR += 5;
                    } else if (ageTime > 30200) {
                        lengthL += 6;
                        lengthR += 6;
                    } else if (ageTime > 12000) {
                        lengthL += 7;
                        lengthR += 7;
                    } else if (ageTime > 9000) {
                        lengthL += 8;
                        lengthR += 8;
                    } else if (ageTime > 6000) {
                        lengthL = 9;
                        lengthR = 9;
                    }
                }
            }

//            lengthL = hornLengthTest;
//            lengthR = hornLengthTest;

            if (lengthL != 0 && lengthR != 0) {

                for (int i = 0; i <= 9; i++) {
                    if (i <= lengthL) {
                        hornGrowthL[i] = 0.0F;
                        unrenderedModels.add(this.leftHorns.get(i).boxName);
                        if (i == lengthL) {
                            unrenderedModels.remove(this.leftHorns.get(i).boxName);
                        }
                    }
                    if (i <= lengthR) {
                        hornGrowthR[i] = 0.0F;
                        unrenderedModels.add(this.rightHorns.get(i).boxName);
                        if (i == lengthR) {
                            unrenderedModels.remove(this.rightHorns.get(i).boxName);
                        }
                    }
                }
            }

//            hornScale = hornScaleTest;
            float hornScaleR = hornScale;

            // hornlength 0 [ (scale=1.0F, 1.0F) (scale=2.5F, 5.5) ]
            // hornlength 1 [ (scale=1.0F, -1.0F) (scale=2.5F, 0.6) ]
            // hornlength 2 [ (scale=1.0F, -1.0F) (scale=2.5F, 0.6) ]
            // hornlength 3 [ (scale=1.0F, -1.0F) (scale=2.5F, ) ]
            // hornlength 4 [ (scale=1.0F, -1.0F) (scale=2.5F, ) ]
            // hornlength 5 [ (scale=1.0F, -1.0F) (scale=2.5F, 2.5) ]
            // hornlength 6 [ (scale=1.0F, -0.75F) (scale=2.5F, 1.2F) ]
            // hornlength 7 [ (scale=1.0F, -0.65F) (scale=2.5F, 1.25F) ]
            // hornlength 8 [ (scale=1.0F, -0.6) (scale=2.5F, 2.0) ]
            // hornlength 9 [ (scale=1.0F, -0.5) (scale=2.5F, 1.9) ]

            switch(lengthL) {
                case 0 : hornScale = 1.0F + ((hornScale-1.0F)*3.0F);
                    break;
                case 1 : hornScale = -1.0F + ((hornScale-1.0F)*1.0666F);
                    break;
                case 2 : hornScale = -1.0F + ((hornScale-1.0F)*1.0666F);
                    break;
                case 3 : hornScale = -1.0F + ((hornScale-1.0F)*1.0666F);
                    break;
                case 4 : hornScale = -1.0F + ((hornScale-1.0F)*1.0666F);
                    break;
                case 5 : hornScale = -1.0F + ((hornScale-1.0F)*1.25F);
                    break;
                case 6 : hornScale = -0.75F + ((hornScale-1.0F)*1.3F);
                    break;
                case 7 : hornScale = -0.65F + ((hornScale-1.0F)*1.2666F);
                    break;
                case 8 : hornScale = -0.6F +((hornScale-1.0F)*1.4666F);
                    break;
                case 9 : hornScale = -0.5F + ((hornScale-1.0F)*1.6F);
                    break;
            }

            switch(lengthR) {
                case 0 : hornScaleR = 1.0F + ((hornScaleR-1.0F)*3.0F);
                    break;
                case 1 : hornScaleR = -1.0F + ((hornScaleR-1.0F)*1.0666F);
                    break;
                case 2 : hornScaleR = -1.0F + ((hornScaleR-1.0F)*1.0666F);
                    break;
                case 3 : hornScaleR = -1.0F + ((hornScaleR-1.0F)*1.0666F);
                    break;
                case 4 : hornScaleR = -1.0F + ((hornScaleR-1.0F)*1.0666F);
                    break;
                case 5 : hornScaleR = -1.0F + ((hornScaleR-1.0F)*1.25F);
                    break;
                case 6 : hornScaleR = -0.75F + ((hornScaleR-1.0F)*1.3F);
                    break;
                case 7 : hornScaleR = -0.65F + ((hornScaleR-1.0F)*1.2666F);
                    break;
                case 8 : hornScaleR = -0.6F +((hornScaleR-1.0F)*1.4666F);
                    break;
                case 9 : hornScaleR = -0.5F + ((hornScaleR-1.0F)*1.6F);
                    break;
            }

            this.hornL0.rotationPointX = -hornScale;
            this.hornR0.rotationPointX = hornScaleR;

            for (int i = 1; i <= 9; i++) {
                this.leftHorns.get(i).rotationPointY = hornGrowthL[i];
                this.rightHorns.get(i).rotationPointY = hornGrowthR[i];
            }
        }

        unrenderedModels.add(hornL0.boxName);
        unrenderedModels.add(hornR0.boxName);

        int hornNubLength = 4;
        if(genes != null) {
            if (genes[70] == 1 || genes[71] == 1){
                hornNubLength--;
            }

            if (genes[72] == 1 || genes[73] == 1){
                hornNubLength = hornNubLength - 2;
            }else if (genes[72] == 2 || genes[73] == 2){
                hornNubLength--;
            }

            if (genes[74] == 1 || genes[75] == 1) {
                hornNubLength++;
            }
        }

        this.hornNub1.rotationPointY = -1.0F;
        this.hornNub2.rotationPointY = -1.0F;
        this.hornNub3.rotationPointY = -1.0F;
        this.hornNub4.rotationPointY = -1.0F;
        this.hornNub5.rotationPointY = -1.0F;
        unrenderedModels.add("HornNub1");
        unrenderedModels.add("HornNub2");
        unrenderedModels.add("HornNub3");
        unrenderedModels.add("HornNub4");
        unrenderedModels.add("HornNub5");

            if (hornNubLength == 1){
                this.hornNub1.rotationPointY = -1.0F + horns;
                unrenderedModels.remove("HornNub1");
            }else if (hornNubLength == 2){
                this.hornNub2.rotationPointY = -1.0F + horns;
                unrenderedModels.remove("HornNub2");
            }else if (hornNubLength == 3){
                this.hornNub3.rotationPointY = -1.0F + horns;
                unrenderedModels.remove("HornNub3");
            }else if (hornNubLength == 4){
                this.hornNub4.rotationPointY = -1.0F + horns;
                unrenderedModels.remove("HornNub4");
            }else if (hornNubLength == 5){
                this.hornNub5.rotationPointY = -1.0F + horns;
                unrenderedModels.remove("HornNub5");
            }
//        }

        //horn shape controllers
        if (horns != 0 && genes != null) {

            Float[] hornGenetics = {1.5F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F};

            int a = 100;
            for (int i = 1; i <= 9; i++) {
                hornGenetics[i] = (float)(((genes[a]%10) + (genes[a+1]%10)) - 3) / -30;
                hornGenetics[10 + i] = (float)((((genes[a]/10)%10) + ((genes[a+1]/10)%10)) - 6) / 30;
                a = a + 2;
            }

            hornGenetics[29] = (float)((genes[94]%10) + ((genes[95])%10)) / 18.0F;
            hornGenetics[28] = (float)(((genes[94]/10)%10) + ((genes[95]/10)%10)) / 18.0F;
            hornGenetics[27] = (float)(((genes[94]/100)%10) + ((genes[95]/100)%10)) / 18.0F;
            hornGenetics[26] = (float)(((genes[94]/1000)%10) + ((genes[95]/1000)%10)) / 18.0F;
            hornGenetics[25] = (float)(((genes[94]/10000)%10) + ((genes[95]/10000)%10)) / 18.0F;
            hornGenetics[24] = (float)((genes[94]/100000) + (genes[95]/100000)) / 18.0F;
            hornGenetics[23] = (float)((genes[96]%10) + ((genes[97])%10)) / 18.0F;
            hornGenetics[22] = (float)(((genes[96]/10)%10) + ((genes[97]/10)%10)) / 18.0F;
            hornGenetics[21] = (float)(((genes[96]/100)%10) + ((genes[97]/100)%10)) / 18.0F;


            float averageMod = 1-((hornGenetics[29] + hornGenetics[28] + hornGenetics[27] + hornGenetics[26] + hornGenetics[25] + hornGenetics[24] + hornGenetics[23] + hornGenetics[22] + hornGenetics[21]) / 9);
            for (int i = 21; i <= 29; i++) {
                hornGenetics[i] = hornGenetics[i] * (((float)((genes[96]/1000) + (genes[97]/1000) - (averageMod*5))) / 8.0F);
            }

            // if b is lower horns curl more near ends
            int b = ((genes[84]-1) + (genes[85]-1))/6;

//             [straight] , [ 1 2 3 ] , [ 4 5 6 ], [ 7 8 9 ]
            float f = (1 + (float)(Math.abs((genes[92]%10)-3) + Math.abs((genes[92]%10)-3)) / 36);
            for (int i = 1; i <= 29; i++) {
                if (i%10 <= 3) {
                    // horn 1, 2, 3 grab allele section [ O x x x ]
                    int c = (genes[92]/1000) + (genes[93]/1000);
//                    if (c >= 18) {
//                        c = 17;
//                    }
                    hornGenetics[i] = hornGenetics[i] * ((((c) * ((1 + (float)(Math.abs((genes[92]%10)-3) + Math.abs((genes[92]%10)-3)) / 36)) ))/24.0F);
                } else if (i%10 <= 6) {
                    // horn 4, 5, 6 grab allele section [ x O x x ]
                    int d = ((genes[92]/100)%10) + ((genes[93]/100)%10);
//                    if (d >= 18) {
//                        d = 17;
//                    }
                    hornGenetics[i] = (1.3F - (b/3.0F)) * hornGenetics[i] * ((d * f)/24.0F);
                } else {
                    // horn 7, 8, 9 grab allele section [ x x O x ]
                    int e = ((genes[92]/10)%10) + ((genes[93]/10)%10);
//                    if (e >= 18) {
//                        e = 17;
//                    }
                    hornGenetics[i] = (1.5F - (b/2.0F)) * hornGenetics[i] * ((e * f)/24.0F);
                }
            }

            //TODO improve impressve curls in horns

            // if b is lower horns stick more out the side of the head
            hornGenetics[10] = (b * (((genes[92]%10) - 3.0F) + ((genes[92]%10) - 3.0F))/-9.0F) * 0.5F;
            hornGenetics[20] = hornGenetics[20] * (1 - b);

            Float[] hornCalculationsZ = {hornGenetics[0], hornGenetics[1], hornGenetics[2], hornGenetics[3], hornGenetics[4], hornGenetics[5], hornGenetics[6], hornGenetics[7], hornGenetics[8], hornGenetics[9]};
            Float[] hornCalculationsX = {hornGenetics[10], hornGenetics[11], hornGenetics[12], hornGenetics[13], hornGenetics[14], hornGenetics[15], hornGenetics[16], hornGenetics[17], hornGenetics[18], hornGenetics[19]};
            Float[] hornCalculationsY = {hornGenetics[20], hornGenetics[21], hornGenetics[22], hornGenetics[23], hornGenetics[24], hornGenetics[25], hornGenetics[26], hornGenetics[27], hornGenetics[28], hornGenetics[29]};

                for (int z = 1; z <= 9; z++) {
                    if (hornGrowthL[z] != 0.0F) {
                        hornGrowthL[z] = 1.0F;
                    }
                    if (hornGrowthR[z] != 0.0F) {
                        hornGrowthR[z] = 1.0F;
                    }
                }

            this.hornL0.rotateAngleZ = hornCalculationsZ[0];
            this.hornR0.rotateAngleZ = -hornCalculationsZ[0];
            this.hornL0.rotateAngleX = hornCalculationsX[0];
            this.hornR0.rotateAngleX = hornCalculationsX[0];
            this.hornL0.rotateAngleY = -hornCalculationsY[0];
            this.hornR0.rotateAngleY = hornCalculationsY[0];

            this.hornL9.rotateAngleZ = hornCalculationsZ[9] * hornGrowthL[9];
            this.hornL8.rotateAngleZ = hornCalculationsZ[8] * hornGrowthL[8];
            this.hornL7.rotateAngleZ = hornCalculationsZ[7] * hornGrowthL[7];
            this.hornL6.rotateAngleZ = hornCalculationsZ[6] * hornGrowthL[6];
            this.hornL5.rotateAngleZ = hornCalculationsZ[5] * hornGrowthL[5];
            this.hornL4.rotateAngleZ = hornCalculationsZ[4] * hornGrowthL[4];
            this.hornL3.rotateAngleZ = hornCalculationsZ[3] * hornGrowthL[3];
            this.hornL2.rotateAngleZ = hornCalculationsZ[2] * hornGrowthL[2];
            this.hornL1.rotateAngleZ = (hornCalculationsZ[1] * hornGrowthL[1]);

            this.hornL9.rotateAngleX = -hornCalculationsX[9] * hornGrowthL[9];
            this.hornL8.rotateAngleX = -hornCalculationsX[8] * hornGrowthL[8];
            this.hornL7.rotateAngleX = -hornCalculationsX[7] * hornGrowthL[7];
            this.hornL6.rotateAngleX = -hornCalculationsX[6] * hornGrowthL[6];
            this.hornL5.rotateAngleX = -hornCalculationsX[5] * hornGrowthL[5];
            this.hornL4.rotateAngleX = -hornCalculationsX[4] * hornGrowthL[4];
            this.hornL3.rotateAngleX = -hornCalculationsX[3] * hornGrowthL[3];
            this.hornL2.rotateAngleX = -hornCalculationsX[2] * hornGrowthL[2];
            this.hornL1.rotateAngleX = (-hornCalculationsX[1] * hornGrowthL[1]);

            this.hornL9.rotateAngleY = hornCalculationsY[9] * hornGrowthL[9];
            this.hornL8.rotateAngleY = hornCalculationsY[8] * hornGrowthL[8];
            this.hornL7.rotateAngleY = hornCalculationsY[7] * hornGrowthL[7];
            this.hornL6.rotateAngleY = hornCalculationsY[6] * hornGrowthL[6];
            this.hornL5.rotateAngleY = hornCalculationsY[5] * hornGrowthL[5];
            this.hornL4.rotateAngleY = hornCalculationsY[4] * hornGrowthL[4];
            this.hornL3.rotateAngleY = hornCalculationsY[3] * hornGrowthL[3];
            this.hornL2.rotateAngleY = hornCalculationsY[2] * hornGrowthL[2];
            this.hornL1.rotateAngleY = (hornCalculationsY[1] * hornGrowthL[1]);

            this.hornR9.rotateAngleZ = -hornCalculationsZ[9] * hornGrowthR[9];
            this.hornR8.rotateAngleZ = -hornCalculationsZ[8] * hornGrowthR[8];
            this.hornR7.rotateAngleZ = -hornCalculationsZ[7] * hornGrowthR[7];
            this.hornR6.rotateAngleZ = -hornCalculationsZ[6] * hornGrowthR[6];
            this.hornR5.rotateAngleZ = -hornCalculationsZ[5] * hornGrowthR[5];
            this.hornR4.rotateAngleZ = -hornCalculationsZ[4] * hornGrowthR[4];
            this.hornR3.rotateAngleZ = -hornCalculationsZ[3] * hornGrowthR[3];
            this.hornR2.rotateAngleZ = -hornCalculationsZ[2] * hornGrowthR[2];
            this.hornR1.rotateAngleZ = (-hornCalculationsZ[1] * hornGrowthR[1]);

            this.hornR9.rotateAngleX = -hornCalculationsX[9] * hornGrowthR[9];
            this.hornR8.rotateAngleX = -hornCalculationsX[8] * hornGrowthR[8];
            this.hornR7.rotateAngleX = -hornCalculationsX[7] * hornGrowthR[7];
            this.hornR6.rotateAngleX = -hornCalculationsX[6] * hornGrowthR[6];
            this.hornR5.rotateAngleX = -hornCalculationsX[5] * hornGrowthR[5];
            this.hornR4.rotateAngleX = -hornCalculationsX[4] * hornGrowthR[4];
            this.hornR3.rotateAngleX = -hornCalculationsX[3] * hornGrowthR[3];
            this.hornR2.rotateAngleX = -hornCalculationsX[2] * hornGrowthR[2];
            this.hornR1.rotateAngleX = (-hornCalculationsX[1] * hornGrowthR[1]);

            this.hornR9.rotateAngleY = -hornCalculationsY[9] * hornGrowthR[9];
            this.hornR8.rotateAngleY = -hornCalculationsY[8] * hornGrowthR[8];
            this.hornR7.rotateAngleY = -hornCalculationsY[7] * hornGrowthR[7];
            this.hornR6.rotateAngleY = -hornCalculationsY[6] * hornGrowthR[6];
            this.hornR5.rotateAngleY = -hornCalculationsY[5] * hornGrowthR[5];
            this.hornR4.rotateAngleY = -hornCalculationsY[4] * hornGrowthR[4];
            this.hornR3.rotateAngleY = -hornCalculationsY[3] * hornGrowthR[3];
            this.hornR2.rotateAngleY = -hornCalculationsY[2] * hornGrowthR[2];
            this.hornR1.rotateAngleY = (-hornCalculationsY[1] * hornGrowthR[1]);



        }
    }

    private void setEarRotations(int[] sharedGenes, List<String> unrenderedModels) {
        this.earSmallestL.setRotationPoint(-3.9F, 4.0F, -2.5F);
        this.earSmallL.setRotationPoint(-3.9F, 4.0F, -2.5F);
        this.earMediumL.setRotationPoint(-3.9F, 4.0F, -2.5F);
        this.earLongL.setRotationPoint(-3.5F, 4.0F, -2.5F);
        this.earLongestL.setRotationPoint(-3.5F, 4.0F, -2.5F);
        this.earSmallestR.setRotationPoint(-3.9F, 4.0F, -2.5F);
        this.earSmallR.setRotationPoint(-3.9F, 4.0F, -2.5F);
        this.earMediumR.setRotationPoint(-3.9F, 4.0F, -2.5F);
        this.earLongR.setRotationPoint(-3.5F, 4.0F, -2.5F);
        this.earLongestR.setRotationPoint(-3.5F, 4.0F, -2.5F);

        this.earSmallestL.rotateAngleZ = ((float) Math.PI / 2F);
        this.earSmallL.rotateAngleZ = ((float) Math.PI / 2F);
        this.earMediumL.rotateAngleZ = ((float) Math.PI / 2F);
        this.earLongL.rotateAngleZ = ((float) Math.PI / 2F);
        this.earLongestL.rotateAngleZ = ((float) Math.PI / 2F);
        this.earSmallestR.rotateAngleZ = ((float) Math.PI / 2F);
        this.earSmallR.rotateAngleZ = ((float) Math.PI / 2F);
        this.earMediumR.rotateAngleZ = ((float) Math.PI / 2F);
        this.earLongR.rotateAngleZ = ((float) Math.PI / 2F);
        this.earLongestR.rotateAngleZ = ((float) Math.PI / 2F);

        unrenderedModels.add("SmallestL");
        unrenderedModels.add("SmallL");
        unrenderedModels.add("MediumL");
        unrenderedModels.add("LongL");
        unrenderedModels.add("LongestL");
        unrenderedModels.add("SmallestR");
        unrenderedModels.add("SmallR");
        unrenderedModels.add("MediumR");
        unrenderedModels.add("LongR");
        unrenderedModels.add("LongestR");

        if(sharedGenes != null) {
            if (sharedGenes[46] == 2 && sharedGenes[47] == 2) {
//        if (true){
                float earSize = 0;
                for (int i = 1; i < sharedGenes[42]; i++) {
                    earSize = earSize + 1.0F;
                }

                for (int i = 1; i < sharedGenes[43]; i++) {
                    earSize = earSize + 1.0F;
                }

                if (sharedGenes[44] == 1 || sharedGenes[45] == 1) {
                    earSize = earSize - earSize / 3.0F;
                }

                float floppiness = (earSize / 6.25F) + 0.2F;

                if (earSize <= 1) {
//            if (true){
                    this.earSmallestL.setRotationPoint(3.0F, 0.5F, -2.5F);
                    this.earSmallestL.rotateAngleZ = 1.1F;
                    this.earSmallestR.setRotationPoint(-3.0F, 0.5F, -2.5F);
                    this.earSmallestR.rotateAngleZ = -1.1F;
                    unrenderedModels.remove("SmallestL");
                    unrenderedModels.remove("SmallestR");
                } else if (earSize <= 3) {
                    this.earSmallL.setRotationPoint(3.0F, 0.75F, -2.5F);
                    this.earSmallL.rotateAngleZ = 1.1F + (earSize / 6.25F);
                    this.earSmallR.setRotationPoint(-3.0F, 0.75F, -2.5F);
                    this.earSmallR.rotateAngleZ = -(1.1F + (earSize / 6.25F));
                    unrenderedModels.remove("SmallL");
                    unrenderedModels.remove("SmallR");
                } else if (earSize <= 5) {
                    this.earMediumL.setRotationPoint(3.0F + (floppiness / 3), 1.0F, -2.5F);
                    this.earMediumL.rotateAngleZ = 1.1F + (earSize / 6.25F);
                    this.earMediumR.setRotationPoint(-(3.0F + (floppiness / 3)), 1.0F, -2.5F);
                    this.earMediumR.rotateAngleZ = -(1.1F + (earSize / 6.25F));
                    unrenderedModels.remove("MediumL");
                    unrenderedModels.remove("MediumR");
                } else if (earSize <= 7) {
                    this.earLongL.setRotationPoint(3.0F + (floppiness / 2), 1.0F, -2.5F);
                    this.earLongL.rotateAngleZ = 1.1F + (earSize / 6.25F);
                    this.earLongR.setRotationPoint(-(3.0F + (floppiness / 2)), 1.0F, -2.5F);
                    this.earLongR.rotateAngleZ = -(1.1F + (earSize / 6.25F));
                    unrenderedModels.remove("LongL");
                    unrenderedModels.remove("LongR");
                } else {
                    this.earLongestL.setRotationPoint(3.0F + floppiness, 1.0F, -2.5F);
                    this.earLongestL.rotateAngleZ = 1.1F + (earSize / 6.25F);
                    this.earLongestR.setRotationPoint(-(3.0F + floppiness), 1.0F, -2.5F);
                    this.earLongestR.rotateAngleZ = -(1.1F + (earSize / 6.25F));
                    unrenderedModels.remove("LongestL");
                    unrenderedModels.remove("LongestR");
                }

            } else {
                float floppiness = 0.9F;
                float earSize = 0;

                for (int i = 1; i < sharedGenes[42]; i++) {
                    floppiness = floppiness + 0.16F;
                    earSize = earSize + 1.0F;
                }

                for (int i = 1; i < sharedGenes[43]; i++) {
                    floppiness = floppiness + 0.16F;
                    earSize = earSize + 1.0F;
                }

                if (sharedGenes[44] == 1 || sharedGenes[45] == 1) {
                    floppiness = floppiness - floppiness / 3.0F;
                    earSize = earSize - earSize / 3.0F;
                }

                for (int i = 1; i < sharedGenes[46]; i++) {
                    floppiness = floppiness + 0.1F;
                }
                for (int i = 1; i < sharedGenes[47]; i++) {
                    floppiness = floppiness + 0.1F;
                }


                if (earSize <= 1) {
                    this.earSmallestL.setRotationPoint(3.0F, 0.5F, -2.5F);
                    this.earSmallestL.rotateAngleZ = floppiness;
                    this.earSmallestR.setRotationPoint(-3.0F, 0.5F, -2.5F);
                    this.earSmallestR.rotateAngleZ = -floppiness;
                    unrenderedModels.remove("SmallestL");
                    unrenderedModels.remove("SmallestR");
                } else if (earSize <= 3) {
                    this.earSmallL.setRotationPoint(3.0F, 0.75F, -2.5F);
                    this.earSmallL.rotateAngleZ = floppiness;
                    this.earSmallR.setRotationPoint(-3.0F, 0.75F, -2.5F);
                    this.earSmallR.rotateAngleZ = -floppiness;
                    unrenderedModels.remove("SmallL");
                    unrenderedModels.remove("SmallR");
                } else if (earSize <= 5) {
                    this.earMediumL.setRotationPoint(3.0F + (floppiness / 3), 1.0F, -2.5F);
                    this.earMediumL.rotateAngleZ = floppiness;
                    this.earMediumR.setRotationPoint(-(3.0F + (floppiness / 3)), 1.0F, -2.5F);
                    this.earMediumR.rotateAngleZ = -floppiness;
                    unrenderedModels.remove("MediumL");
                    unrenderedModels.remove("MediumR");
                } else if (earSize <= 7) {
                    this.earLongL.setRotationPoint(3.0F + (floppiness / 2), 1.0F, -2.5F);
                    this.earLongL.rotateAngleZ = floppiness;
                    this.earLongR.setRotationPoint(-(3.0F + (floppiness / 2)), 1.0F, -2.5F);
                    this.earLongR.rotateAngleZ = -floppiness;
                    unrenderedModels.remove("LongL");
                    unrenderedModels.remove("LongR");
                } else {
                    this.earLongestL.setRotationPoint(3.0F + floppiness, 1.0F, -2.5F);
                    this.earLongestL.rotateAngleZ = floppiness;
                    this.earLongestR.setRotationPoint(-(3.0F + floppiness), 1.0F, -2.5F);
                    this.earLongestR.rotateAngleZ = -floppiness;
                    unrenderedModels.remove("LongestL");
                    unrenderedModels.remove("LongestR");
                }

            }
        }
    }

    private float sleepingAnimation() {
        float onGround;

        onGround = 9.80F;
        this.body.rotationPointY = 9.75F;

        this.mushroomBody1.rotationPointY = 9.75F;
        this.mushroomBody2.rotationPointY = 9.75F;

        this.leg1.rotateAngleX = 1.575F;
        this.leg2.rotateAngleX = 1.575F;
        this.leg3.rotateAngleX = -1.575F;
        this.leg4.rotateAngleX = -1.575F;

        this.leg1.setRotationPoint(-6.0F, 14.0F+onGround, -10.0F);
        this.leg2.setRotationPoint(3.0F, 14.0F+onGround, -10.0F);
        this.leg3.setRotationPoint(-6.0F, 11.0F+onGround, 12.0F);
        this.leg4.setRotationPoint(3.0F, 11.0F+onGround, 12.0F);
        return onGround;
    }

    private float standingAnimation(boolean dwarf) {
        float onGround;
        if (dwarf){
            //dwarf
            onGround = 5.75F;
            this.body.rotationPointY = 5.5F;

            this.mushroomBody1.rotationPointY = 5.5F;
            this.mushroomBody2.rotationPointY = 5.5F;

            this.leg1.setRotationPoint(-6.0F, 16.5F, -10.0F);
            this.leg2.setRotationPoint(3.0F, 16.5F, -10.0F);
            this.leg3.setRotationPoint(-6.0F, 16.5F, 9.0F);
            this.leg4.setRotationPoint(3.0F, 16.5F, 9.0F);

        } else {
            onGround = 2.75F;
            this.body.rotationPointY = 2.5F;

            this.mushroomBody1.rotationPointY = 2.5F;
            this.mushroomBody2.rotationPointY = 2.5F;

            this.leg1.setRotationPoint(-6.0F, 13.5F, -10.0F);
            this.leg2.setRotationPoint(3.0F, 13.5F, -10.0F);
            this.leg3.setRotationPoint(-6.0F, 13.5F, 9.0F);
            this.leg4.setRotationPoint(3.0F, 13.5F, 9.0F);
        }

        return onGround;
    }

    private void setHumpPlacement(int[] sharedGenes) {
        if(sharedGenes != null) {
            if (sharedGenes[40] != 1 && sharedGenes[41] != 1) {
                float hump = 0.0F;
                for (int i = 1; i < sharedGenes[40]; i++) {
                    hump = hump - 0.5F;
                }
                for (int i = 1; i < sharedGenes[41]; i++) {
                    hump = hump - 0.5F;
                }

                this.humpXLarge.rotationPointY = hump;
                this.humpLarge.rotationPointY = hump;
                this.humpLargeish.rotationPointY = hump;
                this.humpMedium.rotationPointY = hump;
                this.humpSmallish.rotationPointY = hump;
                this.humpSmall.rotationPointY = hump;
                this.humpXSmall.rotationPointY = hump;

            }
        }
    }

    private float calculateHorns(int[] sharedGenes, char[] uuidArray) {
        float horns = -1.0F;

        if(sharedGenes != null) {
            if (sharedGenes[12] == 1 || sharedGenes[13] == 1) {
                //should be polled unless...
                //african horn gene
                if (sharedGenes[76] == 1 && sharedGenes[77] == 1) {
                    //horned
                } else if (sharedGenes[76] == 1 || sharedGenes[77] == 1) {
                    //sex determined horned
                    if (uuidArray != null) {
                        if (Character.isLetter(uuidArray[0]) || uuidArray[0] - 48 >= 8) {
                            //horned if male
                        } else {
                            //polled if female unless
                            if (sharedGenes[78] == 1 && sharedGenes[79] == 1) {
                                //she is scured
                                horns = -0.75F;
                            } else {
                                //polled
                                horns = 0.0F;
                            }
                        }
                    }
                } else {
                    //polled
                    if (sharedGenes[78] == 1 && sharedGenes[79] == 1) {
                        //scured
                        horns = -0.75F;
                    } else if (sharedGenes[78] == 1 || sharedGenes[79] == 1) {
                        //sex determined scured
                        if (uuidArray != null) {
                            if (Character.isLetter(uuidArray[0]) || uuidArray[0] - 48 >= 8) {
                                //scurred
                                horns = -0.75F;
                            } else {
                                //polled
                                horns = 0.0F;
                            }
                        }
                    } else {
                        //polled
                        horns = 0.0F;
                    }
                }
            }
        }

        return horns;
    }

    public ModelRenderer getHead() {
        return this.neck;
    }

    private class CowModelData {
        int[] cowGenes;
        String birthTime;
        float cowSize = 1.0F;
        char[] uuidArray;
        int lastAccessed = 0;
        float bagSize = 1.0F;
        String cowStatus = "";
        boolean sleeping = false;
        int blink = 0;
        int dataReset = 0;
        long clientGameTime = 0;
        List<String> unrenderedModels = new ArrayList<>();
        ItemStack saddle;
        ItemStack bridle;
        ItemStack harness;
    }

    private CowModelData getCowModelData() {
        if (this.currentCow == null || !cowModelDataCache.containsKey(this.currentCow)) {
            return new CowModelData();
        }
        return cowModelDataCache.get(this.currentCow);
    }

    private CowModelData getCreateCowModelData(T enhancedCow) {
        clearCacheTimer++;
        if(clearCacheTimer > 50000) {
            cowModelDataCache.values().removeIf(value -> value.lastAccessed==1);
            for (CowModelData cowModelData : cowModelDataCache.values()){
                cowModelData.lastAccessed = 1;
            }
            clearCacheTimer = 0;
        }

        if (cowModelDataCache.containsKey(enhancedCow.getEntityId())) {
            CowModelData cowModelData = cowModelDataCache.get(enhancedCow.getEntityId());
            cowModelData.lastAccessed = 0;
            cowModelData.dataReset++;
            if (cowModelData.dataReset > 5000) {
                cowModelData.cowStatus = enhancedCow.getEntityStatus();
                cowModelData.dataReset = 0;
            }
            cowModelData.bagSize = enhancedCow.getBagSize();
            cowModelData.sleeping = enhancedCow.isAnimalSleeping();
            cowModelData.blink = enhancedCow.getBlink();
            cowModelData.clientGameTime = (((WorldInfo)((ClientWorld)enhancedCow.world).getWorldInfo()).getGameTime());
            cowModelData.saddle = enhancedCow.getEnhancedInventory().getStackInSlot(1);
            cowModelData.bridle = enhancedCow.getEnhancedInventory().getStackInSlot(3);
            cowModelData.harness = enhancedCow.getEnhancedInventory().getStackInSlot(5);
            cowModelData.unrenderedModels = new ArrayList<>();

            return cowModelData;
        } else {
            //initial grab
            CowModelData cowModelData = new CowModelData();
            cowModelData.cowGenes = enhancedCow.getSharedGenes();
            cowModelData.cowSize = enhancedCow.getAnimalSize();
            cowModelData.bagSize = enhancedCow.getBagSize();
            cowModelData.cowStatus = enhancedCow.getEntityStatus();
            cowModelData.sleeping = enhancedCow.isAnimalSleeping();
            cowModelData.blink = enhancedCow.getBlink();
            cowModelData.birthTime = enhancedCow.getBirthTime();

            if (enhancedCow.getMooshroomUUID().isEmpty() || enhancedCow.getMooshroomUUID().equals("0")) {
                cowModelData.uuidArray = enhancedCow.getCachedUniqueIdString().toCharArray();
            } else {
                cowModelData.uuidArray = enhancedCow.getMooshroomUUID().toCharArray();
            }
            cowModelData.clientGameTime = (((WorldInfo)((ClientWorld)enhancedCow.world).getWorldInfo()).getGameTime());
            cowModelData.saddle = enhancedCow.getEnhancedInventory().getStackInSlot(1);
            cowModelData.bridle = enhancedCow.getEnhancedInventory().getStackInSlot(3);
            cowModelData.harness = enhancedCow.getEnhancedInventory().getStackInSlot(5);

            if(cowModelData.cowGenes != null) {
                cowModelDataCache.put(enhancedCow.getEntityId(), cowModelData);
            }

            return cowModelData;
        }
    }


}
