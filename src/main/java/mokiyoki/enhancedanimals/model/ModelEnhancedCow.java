package mokiyoki.enhancedanimals.model;

import com.mojang.blaze3d.platform.GlStateManager;
import mokiyoki.enhancedanimals.entity.EnhancedCow;
import mokiyoki.enhancedanimals.entity.EntityState;
import mokiyoki.enhancedanimals.renderer.EnhancedRendererModel;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.RendererModel;
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
public class ModelEnhancedCow <T extends EnhancedCow> extends EntityModel<T> {

    private Map<Integer, CowModelData> cowModelDataCache = new HashMap<>();
    private int clearCacheTimer = 0;
    private float headRotationAngleX;

    private final EnhancedRendererModel actualHead;
    private final EnhancedRendererModel mouth;
    private final EnhancedRendererModel earSmallestL;
    private final EnhancedRendererModel earSmallL;
    private final EnhancedRendererModel earMediumL;
    private final EnhancedRendererModel earLongL;
    private final EnhancedRendererModel earLongestL;
    private final EnhancedRendererModel earSmallestR;
    private final EnhancedRendererModel earSmallR;
    private final EnhancedRendererModel earMediumR;
    private final EnhancedRendererModel earLongR;
    private final EnhancedRendererModel earLongestR;
    private final EnhancedRendererModel hornNub1;
    private final EnhancedRendererModel hornNub2;
    private final EnhancedRendererModel hornNub3;
    private final EnhancedRendererModel hornNub4;
    private final EnhancedRendererModel hornNub5;
    private final EnhancedRendererModel hornGranparent;
    private final EnhancedRendererModel hornParent;
    private final EnhancedRendererModel hornL0;
    private final EnhancedRendererModel hornL1;
    private final EnhancedRendererModel hornL2;
    private final EnhancedRendererModel hornL3;
    private final EnhancedRendererModel hornL4;
    private final EnhancedRendererModel hornL5;
    private final EnhancedRendererModel hornL6;
    private final EnhancedRendererModel hornL7;
    private final EnhancedRendererModel hornL8;
    private final EnhancedRendererModel hornL9;
    private final EnhancedRendererModel hornR0;
    private final EnhancedRendererModel hornR1;
    private final EnhancedRendererModel hornR2;
    private final EnhancedRendererModel hornR3;
    private final EnhancedRendererModel hornR4;
    private final EnhancedRendererModel hornR5;
    private final EnhancedRendererModel hornR6;
    private final EnhancedRendererModel hornR7;
    private final EnhancedRendererModel hornR8;
    private final EnhancedRendererModel hornR9;
    private final EnhancedRendererModel headModel; //this is the neck not the head
    private final EnhancedRendererModel bodyMedium;
    private final RendererModel udder;
    private final RendererModel nipples;
    private final RendererModel humpXSmall;
    private final RendererModel humpSmall;
    private final RendererModel humpSmallish;
    private final RendererModel humpMedium;
    private final RendererModel humpLargeish;
    private final RendererModel humpLarge;
    private final RendererModel humpXLarge;
    private final EnhancedRendererModel tail0;
    private final EnhancedRendererModel tail1;
    private final EnhancedRendererModel tail2;
    private final EnhancedRendererModel tailBrush;
    private final RendererModel leg1;
    private final RendererModel leg2;
    private final RendererModel leg3;
    private final RendererModel leg4;
    private final RendererModel shortLeg1;
    private final RendererModel shortLeg2;
    private final RendererModel shortLeg3;
    private final RendererModel shortLeg4;
    private final RendererModel mushroomBody1;
    private final RendererModel mushroomBody2;
    private final EnhancedRendererModel mushroomHead;

    private final List<RendererModel> leftHorns = new ArrayList<>();
    private final List<RendererModel> rightHorns = new ArrayList<>();

    public ModelEnhancedCow() {

        this.textureWidth = 80;
        this.textureHeight = 80;

        this.actualHead = new  EnhancedRendererModel(this, 0, 33);
        this.actualHead.addBox(-4.0F, 0.0F, -7.0F, 8, 7, 6, 0.0F);
        this.actualHead.setTextureOffset(28, 33);
        this.actualHead.addBox(-2.0F, 0.1F, -11.0F, 4, 5, 4, 0.0F);
        this.actualHead.setTextureOffset(16, 46);
        this.actualHead.addBox(-2.5F, 0.2F, -13.0F, 5, 4, 3, 0.0F);
        this.actualHead.setRotationPoint(0.0F, 0.0F, -7.0F);

        this.mouth = new  EnhancedRendererModel(this, 25, 46);
        this.mouth.addBox(-1.5F, 1.0F, -10.0F, 3, 3, 7, 0.1F);
        this.mouth.setTextureOffset(37, 46);
        this.mouth.addBox(-1.5F, 3.0F, -10.0F, 3, 1, 6, -0.1F);
        this.mouth.setRotationPoint(0.0F, 4.0F, -2.0F);

        this.earSmallestL = new EnhancedRendererModel(this, 8, 46, "SmallestL");
        this.earSmallestL.addBox(0.0F, -3.0F, -0.5F, 3, 3, 1);

        this.earSmallL = new EnhancedRendererModel(this, 8, 46, "SmallL");
        this.earSmallL.addBox(0.0F, -4.0F, -0.5F, 3, 4, 1);

        this.earMediumL = new EnhancedRendererModel(this, 8, 46, "MediumL");
        this.earMediumL.addBox(0.0F, -5.0F, -0.5F, 3, 5, 1);

        this.earLongL = new EnhancedRendererModel(this, 8, 46, "LongL");
        this.earLongL.addBox(0.0F, -6.0F, -0.5F, 3, 6, 1, 0.15F);

        this.earLongestL = new EnhancedRendererModel(this, 8, 46, "LongestL");
        this.earLongestL.addBox(0.0F, -7.0F, -0.5F, 3, 7, 1, 0.3F);

        this.earSmallestR = new EnhancedRendererModel(this, 0, 46, "SmallestR");
        this.earSmallestR.addBox(-3.0F, -3.0F, -0.5F, 3, 3, 1);

        this.earSmallR = new EnhancedRendererModel(this, 0, 46, "SmallR");
        this.earSmallR.addBox(-3.0F, -4.0F, -0.5F, 3, 4, 1);

        this.earMediumR = new EnhancedRendererModel(this, 0, 46, "MediumR");
        this.earMediumR.addBox(-3.0F, -5.0F, -0.5F, 3, 5, 1);

        this.earLongR = new EnhancedRendererModel(this, 0, 46, "LongR");
        this.earLongR.addBox(-3.0F, -6.0F, -0.5F, 3, 6, 1, 0.15F);

        this.earLongestR = new EnhancedRendererModel(this, 0, 46, "LongestR");
        this.earLongestR.addBox(-3.0F, -7.0F, -0.5F, 3, 7, 1, 0.3F);

        this.hornNub1 = new EnhancedRendererModel(this, 44, 33, "HornNub1");
        this.hornNub1.addBox(-2.0F, 0.0F, 0.0F, 4, 2, 2);
        this.hornNub1.setRotationPoint(0.0F, 1.0F, -1.0F);

        this.hornNub2 = new EnhancedRendererModel(this, 44, 33, "HornNub2");
        this.hornNub2.addBox(-2.0F, 0.0F, 0.0F, 4, 3, 2);
        this.hornNub2.setRotationPoint(0.0F, 1.0F, -1.0F);

        this.hornNub3 = new EnhancedRendererModel(this, 44, 33, "HornNub3");
        this.hornNub3.addBox(-2.0F, 0.0F, 0.0F, 4, 4, 2);
        this.hornNub3.setRotationPoint(0.0F, 1.0F, -1.0F);

        this.hornNub4 = new EnhancedRendererModel(this, 44, 33, "HornNub4");
        this.hornNub4.addBox(-2.0F, 0.0F, 0.0F, 4, 5, 2);
        this.hornNub4.setRotationPoint(0.0F, 1.0F, -1.0F);

        this.hornNub5 = new EnhancedRendererModel(this, 44, 33, "HornNub5");
        this.hornNub5.addBox(-2.0F, 0.0F, 0.0F, 4, 6, 2);
        this.hornNub5.setRotationPoint(0.0F, 1.0F, -1.0F);

        this.hornGranparent = new EnhancedRendererModel(this, 0, 0);
        this.hornParent = new EnhancedRendererModel(this, 0, 0);
        this.hornGranparent.addChild(hornParent);

        this.hornL0 = new EnhancedRendererModel(this, 64, 29, "HornL0");
        this.hornL0.addBox(-2.0F, -4.0F, -2.0F, 4, 4, 4, -1.0F);
        leftHorns.add(hornL0);

        this.hornL1 = new EnhancedRendererModel(this, 64, 37, "HornL1");
        this.hornL1.addBox(-2.0F, -4.0F, -2.0F, 4, 4, 4, -1.001F);
        leftHorns.add(hornL1);

        this.hornL2 = new EnhancedRendererModel(this, 64, 45, "HornL2");
        this.hornL2.addBox(-2.0F, -4.0F, -2.0F, 4, 4, 4, -1.002F);
        leftHorns.add(hornL2);

        this.hornL3 = new EnhancedRendererModel(this, 64, 53, "HornL3");
        this.hornL3.addBox(-2.0F, -4.0F, -2.0F, 4, 4, 4, -1.003F);
        leftHorns.add(hornL3);

        this.hornL4 = new EnhancedRendererModel(this, 64, 61, "HornL4");
        this.hornL4.addBox(-2.0F, -4.0F, -2.0F, 4, 4, 4, -1.004F);
        leftHorns.add(hornL4);

        this.hornL5 = new EnhancedRendererModel(this, 64, 61, "HornL5");
        this.hornL5.addBox(-2.0F, -4.0F, -2.0F, 4, 4, 4, -1.1F);
        leftHorns.add(hornL5);

        this.hornL6 = new EnhancedRendererModel(this, 64, 61, "HornL6");
        this.hornL6.addBox(-2.0F, -4.0F, -2.0F, 4, 4, 4, -1.2F);
        leftHorns.add(hornL6);

        this.hornL7 = new EnhancedRendererModel(this, 64, 61, "HornL7");
        this.hornL7.addBox(-2.0F, -4.0F, -2.0F, 4, 4, 4, -1.3F);
        leftHorns.add(hornL7);

        this.hornL8 = new EnhancedRendererModel(this, 64, 61, "HornL8");
        this.hornL8.addBox(-2.0F, -4.0F, -2.0F, 4, 4, 4, -1.4F);
        leftHorns.add(hornL8);

        this.hornL9 = new EnhancedRendererModel(this, 64, 61, "HornL9");
        this.hornL9.addBox(-2.0F, -4.0F, -2.0F, 4, 4, 4, -1.5F);
        leftHorns.add(hornL9);

        this.hornR0 = new EnhancedRendererModel(this, 64, 29, "HornR0");
        this.hornR0.addBox(-2.0F, -4.0F, -2.0F, 4, 4, 4, -1.0F);
        rightHorns.add(hornR0);

        this.hornR1 = new EnhancedRendererModel(this, 64, 37, "HornR1");
        this.hornR1.addBox(-2.0F, -4.0F, -2.0F, 4, 4, 4, -1.001F);
        rightHorns.add(hornR1);

        this.hornR2 = new EnhancedRendererModel(this, 64, 45, "HornR2");
        this.hornR2.addBox(-2.0F, -4.0F, -2.0F, 4, 4, 4, -1.002F);
        rightHorns.add(hornR2);

        this.hornR3 = new EnhancedRendererModel(this, 64, 53, "HornR3");
        this.hornR3.addBox(-2.0F, -4.0F, -2.0F, 4, 4, 4, -1.003F);
        rightHorns.add(hornR3);

        this.hornR4 = new EnhancedRendererModel(this, 64, 61, "HornR4");
        this.hornR4.addBox(-2.0F, -4.0F, -2.0F, 4, 4, 4, -1.004F);
        rightHorns.add(hornR4);

        this.hornR5 = new EnhancedRendererModel(this, 64, 61, "HornR5");
        this.hornR5.addBox(-2.0F, -4.0F, -2.0F, 4, 4, 4, -1.1F);
        rightHorns.add(hornR5);

        this.hornR6 = new EnhancedRendererModel(this, 64, 61, "HornR6");
        this.hornR6.addBox(-2.0F, -4.0F, -2.0F, 4, 4, 4, -1.2F);
        rightHorns.add(hornR6);

        this.hornR7 = new EnhancedRendererModel(this, 64, 61, "HornR7");
        this.hornR7.addBox(-2.0F, -4.0F, -2.0F, 4, 4, 4, -1.3F);
        rightHorns.add(hornR7);

        this.hornR8 = new EnhancedRendererModel(this, 64, 61, "HornR8");
        this.hornR8.addBox(-2.0F, -4.0F, -2.0F, 4, 4, 4, -1.4F);
        rightHorns.add(hornR8);

        this.hornR9 = new EnhancedRendererModel(this, 64, 61, "HornR9");
        this.hornR9.addBox(-2.0F, -4.0F, -2.0F, 4, 4, 4, -1.5F);
        rightHorns.add(hornR9);

        // head is the neck cause thats how this works
        this.headModel = new EnhancedRendererModel(this, 46, 0);
        this.headModel.addBox(-3.0F, 0.0F, -8.0F, 6, 8, 11, 0.0F);
        this.headModel.setRotationPoint(0.0F, 0.0F, -10.0F);

        this.bodyMedium = new EnhancedRendererModel(this, 0, 0);
        this.bodyMedium.addBox(-6.0F, 0.0F, 0.0F, 12, 11, 22, 0.0F);
        this.bodyMedium.setRotationPoint(0.0F, 2.5F, -10.0F);

        this.udder = new EnhancedRendererModel(this, 24, 67, "Udder");
        this.udder.addBox(-2.0F, -2.0F, -5.0F, 4, 4, 6, 0.0F);
        this.udder.setRotationPoint(0.0F, 10.0F, 21.25F);

        this.nipples = new EnhancedRendererModel(this, 24, 77, "Nipples");
        this.nipples.addBox(-2.0F, 0.0F, -1.0F, 1, 2, 1, -0.15F);
        this.nipples.setTextureOffset(29, 77);
        this.nipples.addBox(1.0F, 0.0F, -1.0F, 1, 2, 1, -0.15F);
        this.nipples.setTextureOffset(35, 77);
        this.nipples.addBox(-2.0F, 0.0F, 2.0F, 1, 2, 1, -0.15F);
        this.nipples.setTextureOffset(40, 77);
        this.nipples.addBox(1.0F, 0.0F, 2.0F, 1, 2, 1, -0.15F);
        this.nipples.setRotationPoint(0.0F, 1.5F, -3.5F);

        this.humpXSmall = new EnhancedRendererModel(this, 0, 8, "HumpXXS");
        this.humpXSmall.addBox(-2.0F, 0.0F, 0.0F, 4, 8, 6, -1.0F);
        this.humpXSmall.setRotationPoint(0.0F, 0.0F, 1.0F);

        this.humpSmall = new EnhancedRendererModel(this, 0, 8, "HumpXS");
        this.humpSmall.addBox(-2.0F, 0.0F, 0.0F, 4, 8, 6, -0.5F);
        this.humpSmall.setRotationPoint(0.0F, 0.0F, 1.0F);

        this.humpSmallish = new EnhancedRendererModel(this, 0, 8, "HumpS");
        this.humpSmallish.addBox(-2.0F, 0.0F, 0.0F, 4, 8, 6, -0.25F);
        this.humpSmallish.setRotationPoint(0.0F, 0.0F, 1.0F);

        this.humpMedium = new EnhancedRendererModel(this, 0, 8, "Hump");
        this.humpMedium.addBox(-2.0F, 0.0F, 0.0F, 4, 8, 6, 0.0F);
        this.humpMedium.setRotationPoint(0.0F, 0.0F, 1.0F);

        this.humpLargeish = new EnhancedRendererModel(this, 0, 8, "HumpL");
        this.humpLargeish.addBox(-2.0F, 0.0F, 0.0F, 4, 8, 6, 0.5F);
        this.humpLargeish.setRotationPoint(0.0F, 0.0F, 1.0F);

        this.humpLarge = new EnhancedRendererModel(this, 0, 8, "HumpXL");
        this.humpLarge.addBox(-2.0F, 0.0F, 0.0F, 4, 8, 6, 1.0F);
        this.humpLarge.setRotationPoint(0.0F, 0.0F, 1.0F);

        this.humpXLarge = new EnhancedRendererModel(this, 0, 8, "HumpXXL");
        this.humpXLarge.addBox(-2.0F, 0.0F, 0.0F, 4, 8, 6, 1.5F);
        this.humpXLarge.setRotationPoint(0.0F, 0.0F, 1.0F);

        this.tail0 = new EnhancedRendererModel(this, 0,0, "Tail");
        this.tail0.addBox(-1.0F, 0.0F, 0.0F, 2, 4, 1);
        this.tail0.setRotationPoint(0.0F, 0.0F, 22.0F);

        this.tail1 = new EnhancedRendererModel(this, 6,0);
        this.tail1.addBox(-0.5F, 0.0F, 0.0F, 1, 4, 1);
        this.tail1.setRotationPoint(0.0F, 4.0F, 0.0F);

        this.tail2 = new EnhancedRendererModel(this, 10,0);
        this.tail2.addBox(-0.5F, 0.0F, 0.0F, 1, 3, 1);
        this.tail2.setRotationPoint(0.0F, 3.0F, 0.0F);

        this.tailBrush = new EnhancedRendererModel(this, 14,0);
        this.tailBrush.addBox(-1.0F, 0.0F, -0.5F, 2, 3, 2);
        this.tailBrush.setRotationPoint(0.0F, 3.0F, 0.0F);

        this.leg1 = new RendererModel(this, 0, 54);
        this.leg1.addBox(0.0F, 0.0F, 0.0F, 3, 10, 3, 0.0F);
        this.leg1.setRotationPoint(-6.0F, 13.5F, -10.0F);

        this.leg2 = new RendererModel(this, 12, 54);
        this.leg2.addBox(0.0F, 0.0F, 0.0F, 3, 10, 3, 0.0F);
        this.leg2.setRotationPoint(3.0F, 13.5F, -10.0F);

        this.leg3 = new RendererModel(this, 0, 67);
        this.leg3.addBox(0.0F, 0.0F, 0.0F, 3, 10, 3, 0.0F);
        this.leg3.setRotationPoint(-6.0F, 13.5F, 9.0F);

        this.leg4 = new RendererModel(this, 12, 67);
        this.leg4.addBox(0.0F, 0.0F, 0.0F, 3, 10, 3, 0.0F);
        this.leg4.setRotationPoint(3.0F, 13.5F, 9.0F);

        this.shortLeg1 = new RendererModel(this, 0, 54);
        this.shortLeg1.addBox(0.0F, 0.0F, 0.0F, 3, 7, 3, 0.0F);
        this.shortLeg1.setRotationPoint(-6.0F, 13.5F, -10.0F);

        this.shortLeg2 = new RendererModel(this, 12, 54);
        this.shortLeg2.addBox(0.0F, 0.0F, 0.0F, 3, 7, 3, 0.0F);
        this.shortLeg2.setRotationPoint(3.0F, 13.5F, -10.0F);

        this.shortLeg3 = new RendererModel(this, 0, 67);
        this.shortLeg3.addBox(0.0F, 0.0F, 0.0F, 3, 7, 3, 0.0F);
        this.shortLeg3.setRotationPoint(-6.0F, 13.5F, 9.0F);

        this.shortLeg4 = new RendererModel(this, 12, 67);
        this.shortLeg4.addBox(0.0F, 0.0F, 0.0F, 3, 7, 3, 0.0F);
        this.shortLeg4.setRotationPoint(3.0F, 13.5F, 9.0F);

        this.mushroomBody1 = new RendererModel(this, 54, 64);
        this.mushroomBody1.addBox(0.0F, -8.0F, 0.0F, 1, 8, 8);
        this.mushroomBody1.setTextureOffset(62, 71);
        this.mushroomBody1.addBox(-4.0F, -8.0F, 4.0F, 8, 8, 1);
        this.mushroomBody1.setRotationPoint(-3.0F, 0.0F, -5.0F);

        this.mushroomBody2 = new RendererModel(this, 54, 64);
        this.mushroomBody2.addBox(0.0F, -8.0F, 0.0F, 1, 8, 8);
        this.mushroomBody2.setTextureOffset(62, 71);
        this.mushroomBody2.addBox(-4.0F, -8.0F, 4.0F, 8, 8, 1);
        this.mushroomBody2.setRotationPoint(3.0F, 0.0F, 5.0F);

        this.mushroomHead = new EnhancedRendererModel(this, 54, 64);
        this.mushroomHead.addBox(0.0F, -8.0F, 0.0F, 1, 8, 8);
        this.mushroomHead.setTextureOffset(62, 71);
        this.mushroomHead.addBox(-4.0F, -8.0F, 4.0F, 8, 8, 1);
        this.mushroomHead.setRotationPoint(0.0F, -0.5F, -6.0F);

        this.headModel.addChild(this.actualHead);
        this.actualHead.addChild(this.earLongestL);
        this.actualHead.addChild(this.earLongL);
        this.actualHead.addChild(this.earMediumL);
        this.actualHead.addChild(this.earSmallL);
        this.actualHead.addChild(this.earSmallestL);
        this.actualHead.addChild(this.earLongestR);
        this.actualHead.addChild(this.earLongR);
        this.actualHead.addChild(this.earMediumR);
        this.actualHead.addChild(this.earSmallR);
        this.actualHead.addChild(this.earSmallestR);
        this.actualHead.addChild(this.hornNub1);
        this.actualHead.addChild(this.hornNub2);
        this.actualHead.addChild(this.hornNub3);
        this.actualHead.addChild(this.hornNub4);
        this.actualHead.addChild(this.hornNub5);
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
        this.actualHead.addChild(this.mouth);
        this.bodyMedium.addChild(this.humpXSmall);
        this.bodyMedium.addChild(this.humpSmall);
        this.bodyMedium.addChild(this.humpSmallish);
        this.bodyMedium.addChild(this.humpMedium);
        this.bodyMedium.addChild(this.humpLargeish);
        this.bodyMedium.addChild(this.humpLarge);
        this.bodyMedium.addChild(this.humpXLarge);
        this.bodyMedium.addChild(this.tail0);
        this.tail0.addChild(this.tail1);
        this.tail1.addChild(this.tail2);
        this.tail2.addChild(this.tailBrush);
        this.bodyMedium.addChild(this.udder);
        this.udder.addChild(this.nipples);

        this.actualHead.addChild(mushroomHead);

    }

    @Override
    public void render(T enhancedCow, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {

        CowModelData cowModelData = getCowModelData(enhancedCow);

        int[] genes = cowModelData.cowGenes;
        float horns = calculateHorns(genes, cowModelData.uuidArray);

        boolean dwarf = false;
        float bodyWidth = -0.165F;
        float bodyLength = 0.0F;
        int hump = 0;
        //TODO find out why humps are glitchy
        //TODO double check the widening/thinning genes and phenotype

        List<String> unrenderedModels = this.setRotationAngles(enhancedCow, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, genes, cowModelData.uuidArray, cowModelData.sleeping);

        //size [0.75 to 2.5]
        float hornScale = 1.0F;

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

        float age = 1.0F;
        float babyScale = 1.0F;
        if (!(cowModelData.birthTime == null) && !cowModelData.birthTime.equals("") && !cowModelData.birthTime.equals("0")) {
            int ageTime = (int)(((WorldInfo)((ClientWorld)enhancedCow.world).getWorldInfo()).getGameTime() - Long.parseLong(cowModelData.birthTime));
            if (ageTime <= 108000) {
                age = ageTime/108000.0F;
                babyScale = (3.0F - age)/2;
            }
        }

            renderAdult(scale, age, babyScale, cowModelData.cowStatus, dwarf, bodyWidth, bodyLength, hump, horns, hornScale, unrenderedModels, cowModelData.cowSize, cowModelData.bagSize, cowModelData.sleeping);

//        long renderTotalEndTime = System.nanoTime();
//        System.out.println("CowRenderTotal: " + (renderTotalEndTime - renderTotalStartTime));

    }

    private void renderAdult(float scale, float age, float babyScale, String cowStatus, boolean dwarf, float bodyWidth, float bodyLength, int hump, float horns, float hornShift, List<String> unrenderedModels, float cowSize, float bagSize, boolean sleeping) {
        float d = 0.0F;
        if (!sleeping) {
            if (dwarf) {
                d = 0.2F * (1.0F-age);
            } else {
                d = 0.3F * (1.0F-age);
            }
        } else {
            babyScale = 1.0F;
        }
        float finalCowSize = (( 2.0F * cowSize * age) + cowSize) / 3.0F;
        GlStateManager.pushMatrix();
        GlStateManager.scalef(finalCowSize + (finalCowSize * bodyWidth), finalCowSize, finalCowSize + (finalCowSize * bodyLength));
        GlStateManager.translatef(0.0F, (-1.45F + 1.45F / (finalCowSize)) - d, 0.0F);

        renderHump(scale, hump, unrenderedModels);

        renderBodyAndUdder(scale, finalCowSize, cowStatus, bodyLength, bagSize, unrenderedModels);

        renderHorns(scale, horns, hornShift, unrenderedModels);

        this.mushroomBody1.render(scale);
        this.mushroomBody2.render(scale);

        this.headModel.render(scale, null , unrenderedModels, true);

        GlStateManager.popMatrix();

        GlStateManager.pushMatrix();
        GlStateManager.scalef(finalCowSize + (finalCowSize * bodyWidth), finalCowSize * babyScale, finalCowSize + (finalCowSize * bodyLength));
        GlStateManager.translatef(0.0F, -1.45F + 1.45F / (finalCowSize * babyScale), 0.0F);

        renderLegs(scale, dwarf);

        GlStateManager.popMatrix();

    }

    private void renderBodyAndUdder(float scale, float cowSize, String cowStatus, float bodyLength, float bagSize, List<String> unrenderedModels) {
        Map<String, List<Float>> mapOfScale = new HashMap<>();
        List<Float> scalingsForUdder = createScalings(bagSize, 0.0F, -(bagSize-1.0F)*0.4F, -(bagSize-1.0F)*0.85F);
        List<Float> scalingsForNipples = createScalings(1.5F/(0.5F+bagSize), 0.0F, (bagSize-1.0F)*0.05F, 0.0F);
        mapOfScale.put("Udder", scalingsForUdder);
        mapOfScale.put("Nipples", scalingsForNipples);

        if ((!cowStatus.equals(EntityState.PREGNANT.toString()) && !cowStatus.equals(EntityState.MOTHER.toString()))) {
            unrenderedModels.add("Udder");
            unrenderedModels.add("Nipples");
        }

        this.bodyMedium.render(scale, mapOfScale, unrenderedModels, false);
    }

    private void renderHorns(float scale, float horns, float hornScale, List<String> unrenderedModels) {
        if (horns != 0.0F) {
            Map<String, List<Float>> mapOfScale = new HashMap<>();

            List<Float> scalingsForHorn = createScalings(hornScale, 0.0F, -hornScale*0.01F, (hornScale - 1.0F)*0.04F);
            mapOfScale.put("HornL0", scalingsForHorn);
            mapOfScale.put("HornR0", mirrorX(scalingsForHorn, false));
            this.hornGranparent.render(scale, mapOfScale, unrenderedModels, false);
        }
    }

    private void renderLegs(float scale, boolean dwarf) {
        if (dwarf) {
            this.shortLeg1.render(scale);
            this.shortLeg2.render(scale);
            this.shortLeg3.render(scale);
            this.shortLeg4.render(scale);
        } else {
            this.leg1.render(scale);
            this.leg2.render(scale);
            this.leg3.render(scale);
            this.leg4.render(scale);
        }
    }

    private void renderHump(float scale, int hump, List<String> unrenderedModels) {
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

            List<Float> scalingsForTail = createScalings(cowSize, 0.0F, (-1.45F + 1.45F / (cowSize*babyScale)), 0.0F);
            mapOfScale.put("Tail", scalingsForTail);
    }

    public List<String> setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, int[] sharedGenes, char[] uuidArray, boolean sleeping) {
        super.setRotationAngles(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
        List<String> unrenderedModels = new ArrayList<>();

        this.headModel.rotateAngleX = (headPitch * 0.017453292F);
        this.headModel.rotateAngleY = netHeadYaw * 0.017453292F;

        this.humpXLarge.rotateAngleX = ((headPitch * 0.017453292F)/2.5F) - 0.2F;
        this.humpXLarge.rotateAngleY = (netHeadYaw * 0.017453292F)/2.5F;

        copyModelRotations(humpXLarge, humpLarge);
        copyModelRotations(humpXLarge, humpLargeish);
        copyModelRotations(humpXLarge, humpMedium);
        copyModelRotations(humpXLarge, humpSmallish);
        copyModelRotations(humpXLarge, humpSmall);
        copyModelRotations(humpXLarge, humpXSmall);

        if (!sleeping) {
            this.leg1.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
            this.leg2.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
            this.leg3.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
            this.leg4.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        }

        copyModelAngles(leg1, shortLeg1);
        copyModelAngles(leg2, shortLeg2);
        copyModelAngles(leg3, shortLeg3);
        copyModelAngles(leg4, shortLeg4);

        this.headModel.rotateAngleX = this.headRotationAngleX;   //might need to merge this with another line
        this.actualHead.rotateAngleX = 0.5F;   //might need to merge this with another line
        this.mouth.rotateAngleX = -0.3F;   //might need to merge this with another line

//        this.actualHead.rotateAngleX = 0.05F + this.actualHead.rotateAngleX;   //might need to merge this with another line

        this.tail0.rotateAngleZ = MathHelper.cos(limbSwing * 0.6662F) * 0.4F * limbSwingAmount;
        this.tail1.rotateAngleZ = MathHelper.cos(limbSwing * 0.6662F) * 0.4F * limbSwingAmount;
        this.tail2.rotateAngleZ = MathHelper.cos(limbSwing * 0.6662F) * 0.45F * limbSwingAmount;
        this.tailBrush.rotateAngleZ = MathHelper.cos(limbSwing * 0.6662F) * 0.6F * limbSwingAmount;

        this.tail0.rotateAngleX = 0.4F;
        this.tail1.rotateAngleX = -0.2F;
        this.tail2.rotateAngleX = -0.2F;
        this.tailBrush.rotateAngleX = 0F;

        setEarRotations(sharedGenes, unrenderedModels);

        setHornRotations(sharedGenes, uuidArray, unrenderedModels, entityIn);

        setHumpPlacement(sharedGenes);

        return unrenderedModels;
    }

    @Override
    public void setLivingAnimations(T entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTickTime) {
        super.setLivingAnimations(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTickTime);
        CowModelData cowModelData = getCowModelData(entitylivingbaseIn);

        int[] sharedGenes = cowModelData.cowGenes;
        boolean sleeping = cowModelData.sleeping;
        float onGround;
        boolean dwarf = (sharedGenes[26] == 1 || sharedGenes[27] == 1);

        if (sleeping) {
            onGround = sleepingAnimation();
        } else {
            onGround = standingAnimation(dwarf);
        }
        this.headModel.rotationPointY = onGround + (entitylivingbaseIn).getHeadRotationPointY(partialTickTime) * 9.0F;
        this.headRotationAngleX = (entitylivingbaseIn).getHeadRotationAngleX(partialTickTime);
    }

    private void setHornRotations(int[] sharedGenes, char[] uuidArray, List<String> unrenderedModels, T entityIn) {

        CowModelData cowModelData = getCowModelData(entityIn);

        this.hornNub1.rotateAngleX = ((float)Math.PI / -2F);
        this.hornNub2.rotateAngleX = ((float)Math.PI / -2F);
        this.hornNub3.rotateAngleX = ((float)Math.PI / -2F);
        this.hornNub4.rotateAngleX = ((float)Math.PI / -2F);
        this.hornNub5.rotateAngleX = ((float)Math.PI / -2F);

        copyModelAngles(headModel, hornGranparent);
        copyModelAngles(actualHead, hornParent);

        float hornBaseHight = -1.0F;
        if (sharedGenes[70] == 2) {
            hornBaseHight = hornBaseHight + 0.05F;
        }
        if (sharedGenes[71] == 2) {
            hornBaseHight = hornBaseHight + 0.05F;
        }
        if (sharedGenes[72] != 1) {
            hornBaseHight = hornBaseHight + 0.1F;
        }
        if (sharedGenes[73] != 1) {
            hornBaseHight = hornBaseHight + 0.1F;
        }
        if (sharedGenes[72] == 3 && sharedGenes[73] == 3) {
            hornBaseHight = hornBaseHight + 0.1F;
        }
        if (sharedGenes[74] == 1) {
            hornBaseHight = hornBaseHight * 0.75F;
        }
        if (sharedGenes[75] == 1) {
            hornBaseHight = hornBaseHight * 0.75F;
        }
        hornParent.rotationPointY = hornBaseHight;

        hornL0.setRotationPoint(0.0F, 0.0F, -2.25F);
        hornR0.setRotationPoint(0.0F, 0.0F, -2.25F);

        float horns = calculateHorns(sharedGenes, uuidArray);

        Float[] hornGrowthL = {-1.0F, -2.0F, -2.0F, -2.0F, -2.0F, -1.8F, -1.6F, -1.4F, -1.2F, -1.0F};
        Float[] hornGrowthR = {-1.0F, -2.0F, -2.0F, -2.0F, -2.0F, -1.8F, -1.6F, -1.4F, -1.2F, -1.0F};

        EnhancedCow enhancedCow = (EnhancedCow) entityIn;

        if (horns != 0.0F) {

            int lengthL = 2;


            if (sharedGenes[80] != 2 || sharedGenes[81] != 2 ) {
                if (sharedGenes[80] >= 3 || sharedGenes[81] >= 3 ) {
                    if (sharedGenes[80] == 4 && sharedGenes[81] == 4) {
                        lengthL = -1;
                    } else {
                        lengthL = 1;
                    }
                } else {
                    lengthL = 0;
                }
            }

            int lengthR = lengthL;

            if (horns != -1.0){
                lengthL = lengthL + 5;
                lengthR = lengthR + 5;

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
            } else {
                //if horns are not scurred shorten horns if they are extra thicc
                if (sharedGenes[82] == 2) {
                    lengthL = lengthL + 1;
                }
                if (sharedGenes[83] == 2 ){
                    lengthL = lengthL + 1 ;
                }

                if (sharedGenes[88] == 2) {
                    lengthL = lengthL + 1;
                }
                if (sharedGenes[89] == 2) {
                    lengthL = lengthL + 1;
                }
                if (sharedGenes[90] == 2 || sharedGenes[91] == 2) {
                    lengthL = lengthL + 2;
                }

                lengthR = lengthL;
            }

            if (lengthL > 9) {
                lengthL = 9;
            }
            if (lengthR > 9) {
                lengthR = 9;
            }


            int hornGrowth = 0;
            if (!(cowModelData.birthTime == null) && !cowModelData.birthTime.equals("") && !cowModelData.birthTime.equals("0")) {
                int ageTime = (int)(((WorldInfo)((ClientWorld)enhancedCow.world).getWorldInfo()).getGameTime() - Long.parseLong(cowModelData.birthTime));
                if (ageTime > 108000) {
                    hornGrowth = 0;
                } else if (ageTime > 97200) {
                    hornGrowth = 1;
                } else if (ageTime > 86400) {
                    hornGrowth = 2;
                } else if (ageTime > 75600) {
                    hornGrowth = 3;
                } else if (ageTime > 64800) {
                    hornGrowth = 4;
                } else if (ageTime > 54000) {
                    hornGrowth = 5;
                } else if (ageTime > 43200) {
                    hornGrowth = 6;
                } else if (ageTime > 30000) {
                    hornGrowth = 7;
                } else if (ageTime > 12000) {
                    hornGrowth = 8;
                } else {
                    hornGrowth = 9;
                }
            }

            lengthL = lengthL + hornGrowth;
            lengthR = lengthR + hornGrowth;



            if (lengthL != 0 || lengthR != 0) {

                for (int i = 0; i <= 9; i++) {
                    if (i <= lengthL) {
                        hornGrowthL[i] = 0.0F;
                        unrenderedModels.add(this.leftHorns.get(i).boxName);
                        if (i != 9) {
                            hornGrowthL[i+1] = 0.0F;
                        }
                    }
                    if (i <= lengthR) {
                        hornGrowthR[i] = 0.0F;
                        unrenderedModels.add(this.rightHorns.get(i).boxName);
                        if (i != 9) {
                            hornGrowthR[i+1] = 0.0F;
                        }
                    }
                    if (i == lengthL) {
                        unrenderedModels.remove(this.leftHorns.get(i).boxName);
                    }
                    if (i == lengthR) {
                        unrenderedModels.remove(this.rightHorns.get(i).boxName);
                    }
                }



            }

            this.hornL0.rotationPointX = 0.0F;
            this.hornR0.rotationPointX = 0.0F;

            for (int i = 1; i <= 9; i++) {
                this.leftHorns.get(i).rotationPointY = hornGrowthL[i];
                this.rightHorns.get(i).rotationPointY = hornGrowthR[i];
            }
        }

        int hornNubLength = 4;
        if (sharedGenes[70] == 1 || sharedGenes[71] == 1){
            hornNubLength--;
        }

        if (sharedGenes[72] == 1 || sharedGenes[73] == 1){
            hornNubLength = hornNubLength - 2;
        }else if (sharedGenes[72] == 2 || sharedGenes[73] == 2){
            hornNubLength--;
        }

        if (sharedGenes[74] == 1 || sharedGenes[75] == 1) {
            hornNubLength++;
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
        if (horns != 0) {

            Float[] hornGenetics = {1.5F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F};

            int a = 100;
            for (int i = 1; i <= 9; i++) {
                hornGenetics[i] = (float)(((sharedGenes[a]%10) + (sharedGenes[a+1]%10)) - 3) / -30;
                hornGenetics[10 + i] = (float)((((sharedGenes[a]/10)%10) + ((sharedGenes[a+1]/10)%10)) - 6) / 30;
                a = a + 2;
            }

            hornGenetics[29] = (float)((sharedGenes[94]%10) + ((sharedGenes[95])%10)) / 18.0F;
            hornGenetics[28] = (float)(((sharedGenes[94]/10)%10) + ((sharedGenes[95]/10)%10)) / 18.0F;
            hornGenetics[27] = (float)(((sharedGenes[94]/100)%10) + ((sharedGenes[95]/100)%10)) / 18.0F;
            hornGenetics[26] = (float)(((sharedGenes[94]/1000)%10) + ((sharedGenes[95]/1000)%10)) / 18.0F;
            hornGenetics[25] = (float)(((sharedGenes[94]/10000)%10) + ((sharedGenes[95]/10000)%10)) / 18.0F;
            hornGenetics[24] = (float)((sharedGenes[94]/100000) + (sharedGenes[95]/100000)) / 18.0F;
            hornGenetics[23] = (float)((sharedGenes[96]%10) + ((sharedGenes[97])%10)) / 18.0F;
            hornGenetics[22] = (float)(((sharedGenes[96]/10)%10) + ((sharedGenes[97]/10)%10)) / 18.0F;
            hornGenetics[21] = (float)(((sharedGenes[96]/100)%10) + ((sharedGenes[97]/100)%10)) / 18.0F;


            float averageMod = 1-((hornGenetics[29] + hornGenetics[28] + hornGenetics[27] + hornGenetics[26] + hornGenetics[25] + hornGenetics[24] + hornGenetics[23] + hornGenetics[22] + hornGenetics[21]) / 9);
            for (int i = 21; i <= 29; i++) {
                hornGenetics[i] = hornGenetics[i] * (((float)((sharedGenes[96]/1000) + (sharedGenes[97]/1000) - (averageMod*5))) / 8.0F);
            }

            // if b is lower horns curl more near ends
            int b = ((sharedGenes[84]-1) + (sharedGenes[85]-1))/6;

//             [straight] , [ 1 2 3 ] , [ 4 5 6 ], [ 7 8 9 ]
            float f = (1 + (float)(Math.abs((sharedGenes[92]%10)-3) + Math.abs((sharedGenes[92]%10)-3)) / 36);
            for (int i = 1; i <= 29; i++) {
                if (i%10 <= 3) {
                    // horn 1, 2, 3 grab allele section [ O x x x ]
                    int c = (sharedGenes[92]/1000) + (sharedGenes[93]/1000);
//                    if (c >= 18) {
//                        c = 17;
//                    }
                    hornGenetics[i] = hornGenetics[i] * ((((c) * ((1 + (float)(Math.abs((sharedGenes[92]%10)-3) + Math.abs((sharedGenes[92]%10)-3)) / 36)) ))/24.0F);
                } else if (i%10 <= 6) {
                    // horn 4, 5, 6 grab allele section [ x O x x ]
                    int d = ((sharedGenes[92]/100)%10) + ((sharedGenes[93]/100)%10);
//                    if (d >= 18) {
//                        d = 17;
//                    }
                    hornGenetics[i] = (1.3F - (b/3.0F)) * hornGenetics[i] * ((d * f)/24.0F);
                } else {
                    // horn 7, 8, 9 grab allele section [ x x O x ]
                    int e = ((sharedGenes[92]/10)%10) + ((sharedGenes[93]/10)%10);
//                    if (e >= 18) {
//                        e = 17;
//                    }
                    hornGenetics[i] = (1.5F - (b/2.0F)) * hornGenetics[i] * ((e * f)/24.0F);
                }
            }

            //TODO improve impressve curls in horns

            // if b is lower horns stick more out the side of the head
            hornGenetics[10] = (b * (((sharedGenes[92]%10) - 3.0F) + ((sharedGenes[92]%10) - 3.0F))/-9.0F) * 0.5F;
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

    private float sleepingAnimation() {
        float onGround;

        onGround = 9.80F;
        this.bodyMedium.rotationPointY = 9.75F;

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

    private float standingAnimation( boolean dwarf) {
        float onGround;
        if (dwarf){
            //dwarf
            onGround = 5.75F;
            this.bodyMedium.rotationPointY = 5.5F;

            this.mushroomBody1.rotationPointY = 5.5F;
            this.mushroomBody2.rotationPointY = 5.5F;

            this.leg1.setRotationPoint(-6.0F, 16.5F, -10.0F);
            this.leg2.setRotationPoint(3.0F, 16.5F, -10.0F);
            this.leg3.setRotationPoint(-6.0F, 16.5F, 9.0F);
            this.leg4.setRotationPoint(3.0F, 16.5F, 9.0F);

        } else {
            onGround = 2.75F;
            this.bodyMedium.rotationPointY = 2.5F;

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

    private List<Float> createScalings(Float scaling, Float translateX, Float translateY, Float translateZ) {
        List<Float> scalings = new ArrayList<>();
        //scaling
        scalings.add(scaling);

        //translations
        scalings.add(translateX);
        scalings.add(translateY);
        scalings.add(translateZ);
        return scalings;
    }

    private List<Float> mirrorX(List<Float> scalings, boolean includeScaling) {
        List<Float> reversedNegative = new ArrayList<>();

        if (includeScaling) {
            reversedNegative.add(scalings.get(0));
        } else {
            reversedNegative.add(null);
        }
        reversedNegative.add(scalings.get(1)*-2.0F);
        reversedNegative.add(0F);
        reversedNegative.add(0F);

        return reversedNegative;
    }

    private float calculateHorns(int[] sharedGenes, char[] uuidArray) {
        float horns = -1.0F;

            if (sharedGenes[12] == 1 || sharedGenes[13] == 1) {
                //should be polled unless...
                //african horn gene
                if (sharedGenes[76] == 1 && sharedGenes[77] == 1) {
                    //horned
                } else if (sharedGenes[76] == 1 || sharedGenes[77] == 1) {
                    //sex determined horned
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
                } else {
                    //polled
                    if (sharedGenes[78] == 1 && sharedGenes[79] == 1) {
                        //scured
                        horns = -0.75F;
                    } else if (sharedGenes[78] == 1 || sharedGenes[79] == 1) {
                        //sex determined scured
                        if (Character.isLetter(uuidArray[0]) || uuidArray[0] - 48 >= 8) {
                            //scurred
                            horns = -0.75F;
                        } else {
                            //polled
                            horns = 0.0F;
                        }
                    } else {
                        //polled
                        horns = 0.0F;
                    }
                }
            }

        return horns;
    }

    public static void copyModelRotations(RendererModel source, RendererModel dest) {
        dest.rotateAngleX = source.rotateAngleX;
        dest.rotateAngleY = source.rotateAngleY;
        dest.rotateAngleZ = source.rotateAngleZ;
    }

    public static void copyModelAngles(RendererModel source, RendererModel dest) {
        dest.rotateAngleX = source.rotateAngleX;
        dest.rotateAngleY = source.rotateAngleY;
        dest.rotateAngleZ = source.rotateAngleZ;
        dest.rotationPointX = source.rotationPointX;
        dest.rotationPointY = source.rotationPointY;
        dest.rotationPointZ = source.rotationPointZ;
    }

    public RendererModel getHead() {
        return this.headModel;
    }

    private class CowModelData {
        int[] cowGenes;
        String birthTime;
        float cowSize;
        char[] uuidArray;
        int lastAccessed = 0;
        float bagSize;
        String cowStatus;
        boolean sleeping;
        int dataReset = 0;
    }

    private CowModelData getCowModelData(T enhancedCow) {
        clearCacheTimer++;
        if(clearCacheTimer > 100000) {
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
                cowModelData.cowStatus = enhancedCow.getCowStatus();
                cowModelData.dataReset = 0;
            }
            cowModelData.bagSize = enhancedCow.getBagSize();
            cowModelData.sleeping = enhancedCow.isAnimalSleeping();
            return cowModelData;
        } else {
            CowModelData cowModelData = new CowModelData();
            cowModelData.cowGenes = enhancedCow.getSharedGenes();
            cowModelData.cowSize = enhancedCow.getSize();
            cowModelData.bagSize = enhancedCow.getBagSize();
            cowModelData.cowStatus = enhancedCow.getCowStatus();
            cowModelData.sleeping = enhancedCow.isAnimalSleeping();
            cowModelData.birthTime = enhancedCow.getBirthTime();

            if (enhancedCow.getMooshroomUUID().isEmpty() || enhancedCow.getMooshroomUUID().equals("0")) {
                cowModelData.uuidArray = enhancedCow.getCachedUniqueIdString().toCharArray();
            } else {
                cowModelData.uuidArray = enhancedCow.getMooshroomUUID().toCharArray();
            }

            cowModelDataCache.put(enhancedCow.getEntityId(), cowModelData);

            return cowModelData;
        }
    }


}
