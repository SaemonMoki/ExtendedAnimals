package mokiyoki.enhancedanimals.model;

import com.mojang.blaze3d.platform.GlStateManager;
import mokiyoki.enhancedanimals.entity.EnhancedAnimal;
import mokiyoki.enhancedanimals.entity.EnhancedCow;
import mokiyoki.enhancedanimals.entity.EntityState;
import mokiyoki.enhancedanimals.renderer.EnhancedRendererModel;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.util.math.MathHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModelEnhancedCow <T extends EnhancedCow> extends EntityModel<T> {

    private float headRotationAngleX;
    private float size;
    private float bagSize;

    private final RendererModel actualHead;
    private final RendererModel mouth;
    private final RendererModel earSmallestL;
    private final RendererModel earSmallL;
    private final RendererModel earMediumL;
    private final RendererModel earLongL;
    private final RendererModel earLongestL;
    private final RendererModel earSmallestR;
    private final RendererModel earSmallR;
    private final RendererModel earMediumR;
    private final RendererModel earLongR;
    private final RendererModel earLongestR;
    private final RendererModel hornNub1;
    private final RendererModel hornNub2;
    private final RendererModel hornNub3;
    private final RendererModel hornNub4;
    private final RendererModel hornNub5;
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
    private final RendererModel headModel; //this is the neck not the head
    private final RendererModel bodyMedium;
    private final RendererModel bodySlim;
    private final RendererModel bodyThin;
    private final RendererModel udder;
    private final RendererModel nipples;
    private final RendererModel humpXSmall;
    private final RendererModel humpSmall;
    private final RendererModel humpSmallish;
    private final RendererModel humpMedium;
    private final RendererModel humpLargeish;
    private final RendererModel humpLarge;
    private final RendererModel humpXLarge;
    private final RendererModel tail0;
    private final RendererModel tail1;
    private final RendererModel tail2;
    private final RendererModel tailBrush;
    private final RendererModel leg1;
    private final RendererModel leg2;
    private final RendererModel leg3;
    private final RendererModel leg4;
    private final RendererModel shortLeg1;
    private final RendererModel shortLeg2;
    private final RendererModel shortLeg3;
    private final RendererModel shortLeg4;

    private final List<RendererModel> leftHorns = new ArrayList<>();
    private final List<RendererModel> rightHorns = new ArrayList<>();


    public ModelEnhancedCow() {

        this.textureWidth = 80;
        this.textureHeight = 80;

        float xMove = -10.0F;

        this.actualHead = new  RendererModel(this, 0, 33);
        this.actualHead.addBox(-4.0F, 0.0F, -7.0F, 8, 7, 6, 0.0F);
        this.actualHead.setTextureOffset(28, 33);
        this.actualHead.addBox(-2.0F, 0.1F, -11.0F, 4, 5, 4, 0.0F);
        this.actualHead.setTextureOffset(16, 46);
        this.actualHead.addBox(-2.5F, 0.2F, -13.0F, 5, 4, 3, 0.0F);
        this.actualHead.setRotationPoint(0.0F, 0.0F, -7.0F);

        this.mouth = new  RendererModel(this, 25, 46);
        this.mouth.addBox(-1.5F, 1.0F, -10.0F, 3, 3, 7, 0.1F);
        this.mouth.setTextureOffset(37, 46);
        this.mouth.addBox(-1.5F, 3.0F, -10.0F, 3, 1, 6, -0.1F);
        this.mouth.setRotationPoint(0.0F, 4.0F, -2.0F);

        this.earSmallestL = new  RendererModel(this, 8, 46);
        this.earSmallestL.addBox(0.0F, -3.0F, -0.5F, 3, 3, 1);
        
        this.earSmallL = new  RendererModel(this, 8, 46);
        this.earSmallL.addBox(0.0F, -4.0F, -0.5F, 3, 4, 1);

        this.earMediumL = new  RendererModel(this, 8, 46);
        this.earMediumL.addBox(0.0F, -5.0F, -0.5F, 3, 5, 1);

        this.earLongL = new  RendererModel(this, 8, 46);
        this.earLongL.addBox(0.0F, -6.0F, -0.5F, 3, 6, 1, 0.15F);

        this.earLongestL = new  RendererModel(this, 8, 46);
        this.earLongestL.addBox(0.0F, -7.0F, -0.5F, 3, 7, 1, 0.3F);

        this.earSmallestR = new  RendererModel(this, 0, 46);
        this.earSmallestR.addBox(-3.0F, -3.0F, -0.5F, 3, 3, 1);

        this.earSmallR = new  RendererModel(this, 0, 46);
        this.earSmallR.addBox(-3.0F, -4.0F, -0.5F, 3, 4, 1);

        this.earMediumR = new  RendererModel(this, 0, 46);
        this.earMediumR.addBox(-3.0F, -5.0F, -0.5F, 3, 5, 1);

        this.earLongR = new  RendererModel(this, 0, 46);
        this.earLongR.addBox(-3.0F, -6.0F, -0.5F, 3, 6, 1, 0.15F);

        this.earLongestR = new  RendererModel(this, 0, 46);
        this.earLongestR.addBox(-3.0F, -7.0F, -0.5F, 3, 7, 1, 0.3F);

        this.hornNub1 = new RendererModel(this, 44, 33);
        this.hornNub1.addBox(-2.0F, 0.0F, 0.0F, 4, 2, 2);
        this.hornNub1.setRotationPoint(0.0F, 1.0F, -1.0F);

        this.hornNub2 = new RendererModel(this, 44, 33);
        this.hornNub2.addBox(-2.0F, 0.0F, 0.0F, 4, 3, 2);
        this.hornNub2.setRotationPoint(0.0F, 1.0F, -1.0F);

        this.hornNub3 = new RendererModel(this, 44, 33);
        this.hornNub3.addBox(-2.0F, 0.0F, 0.0F, 4, 4, 2);
        this.hornNub3.setRotationPoint(0.0F, 1.0F, -1.0F);

        this.hornNub4 = new RendererModel(this, 44, 33);
        this.hornNub4.addBox(-2.0F, 0.0F, 0.0F, 4, 5, 2);
        this.hornNub4.setRotationPoint(0.0F, 1.0F, -1.0F);

        this.hornNub5 = new RendererModel(this, 44, 33);
        this.hornNub5.addBox(-2.0F, 0.0F, 0.0F, 4, 6, 2);
        this.hornNub5.setRotationPoint(0.0F, 1.0F, -1.0F);

        this.hornGranparent = new EnhancedRendererModel(this, 0, 0);
        this.hornParent = new EnhancedRendererModel(this, 0, 0);
        this.hornGranparent.addChild(hornParent);

        this.hornL0 = new EnhancedRendererModel(this, 64, 34, "HornL0");
        this.hornL0.addBox(-2.0F, -4.0F, -2.0F, 4, 4, 4, -1.0F);
        leftHorns.add(hornL0);

        this.hornL1 = new EnhancedRendererModel(this, 64, 42, "HornL1");
        this.hornL1.addBox(-2.0F, -4.0F, -2.0F, 4, 4, 4, -1.001F);
        leftHorns.add(hornL1);

        this.hornL2 = new EnhancedRendererModel(this, 64, 50, "HornL2");
        this.hornL2.addBox(-2.0F, -4.0F, -2.0F, 4, 4, 4, -1.002F);
        leftHorns.add(hornL2);

        this.hornL3 = new EnhancedRendererModel(this, 64, 58, "HornL3");
        this.hornL3.addBox(-2.0F, -4.0F, -2.0F, 4, 4, 4, -1.003F);
        leftHorns.add(hornL3);

        this.hornL4 = new EnhancedRendererModel(this, 64, 66, "HornL4");
        this.hornL4.addBox(-2.0F, -4.0F, -2.0F, 4, 4, 4, -1.004F);
        leftHorns.add(hornL4);

        this.hornL5 = new EnhancedRendererModel(this, 64, 66, "HornL5");
        this.hornL5.addBox(-2.0F, -4.0F, -2.0F, 4, 4, 4, -1.1F);
        leftHorns.add(hornL5);

        this.hornL6 = new EnhancedRendererModel(this, 64, 66, "HornL6");
        this.hornL6.addBox(-2.0F, -4.0F, -2.0F, 4, 4, 4, -1.2F);
        leftHorns.add(hornL6);

        this.hornL7 = new EnhancedRendererModel(this, 64, 66, "HornL7");
        this.hornL7.addBox(-2.0F, -4.0F, -2.0F, 4, 4, 4, -1.3F);
        leftHorns.add(hornL7);

        this.hornL8 = new EnhancedRendererModel(this, 64, 66, "HornL8");
        this.hornL8.addBox(-2.0F, -4.0F, -2.0F, 4, 4, 4, -1.4F);
        leftHorns.add(hornL8);

        this.hornL9 = new EnhancedRendererModel(this, 64, 66, "HornL9");
        this.hornL9.addBox(-2.0F, -4.0F, -2.0F, 4, 4, 4, -1.5F);
        leftHorns.add(hornL9);

        this.hornR0 = new EnhancedRendererModel(this, 64, 34, "HornR0");
        this.hornR0.addBox(-2.0F, -4.0F, -2.0F, 4, 4, 4, -1.0F);
        rightHorns.add(hornR0);

        this.hornR1 = new EnhancedRendererModel(this, 64, 42, "HornR1");
        this.hornR1.addBox(-2.0F, -4.0F, -2.0F, 4, 4, 4, -1.001F);
        rightHorns.add(hornR1);

        this.hornR2 = new EnhancedRendererModel(this, 64, 50, "HornR2");
        this.hornR2.addBox(-2.0F, -4.0F, -2.0F, 4, 4, 4, -1.002F);
        rightHorns.add(hornR2);

        this.hornR3 = new EnhancedRendererModel(this, 64, 58, "HornR3");
        this.hornR3.addBox(-2.0F, -4.0F, -2.0F, 4, 4, 4, -1.003F);
        rightHorns.add(hornR3);

        this.hornR4 = new EnhancedRendererModel(this, 64, 66, "HornR4");
        this.hornR4.addBox(-2.0F, -4.0F, -2.0F, 4, 4, 4, -1.004F);
        rightHorns.add(hornR4);

        this.hornR5 = new EnhancedRendererModel(this, 64, 66, "HornR5");
        this.hornR5.addBox(-2.0F, -4.0F, -2.0F, 4, 4, 4, -1.1F);
        rightHorns.add(hornR5);

        this.hornR6 = new EnhancedRendererModel(this, 64, 66, "HornR6");
        this.hornR6.addBox(-2.0F, -4.0F, -2.0F, 4, 4, 4, -1.2F);
        rightHorns.add(hornR6);

        this.hornR7 = new EnhancedRendererModel(this, 64, 66, "HornR7");
        this.hornR7.addBox(-2.0F, -4.0F, -2.0F, 4, 4, 4, -1.3F);
        rightHorns.add(hornR7);

        this.hornR8 = new EnhancedRendererModel(this, 64, 66, "HornR8");
        this.hornR8.addBox(-2.0F, -4.0F, -2.0F, 4, 4, 4, -1.4F);
        rightHorns.add(hornR8);

        this.hornR9 = new EnhancedRendererModel(this, 64, 66, "HornR9");
        this.hornR9.addBox(-2.0F, -4.0F, -2.0F, 4, 4, 4, -1.5F);
        rightHorns.add(hornR9);

        // head is the neck cause thats how this works
        this.headModel = new RendererModel(this, 46, 0);
        this.headModel.addBox(-3.0F, 0.0F, -8.0F, 6, 8, 11, 0.0F);
        this.headModel.setRotationPoint(0.0F, 0.0F, 0.0F + xMove);

        this.bodyMedium = new RendererModel(this, 0, 0);
        this.bodyMedium.addBox(-6.0F, 0.0F, 0.0F, 12, 11, 22, 0.0F);
        this.bodyMedium.setRotationPoint(0.0F, 2.5F, 0.0F + xMove);

        this.bodySlim = new RendererModel(this, 0, 0);
        this.bodySlim.addBox(-6.0F, 0.0F, 0.0F, 12, 11, 22, -0.5F);
        this.bodySlim.setRotationPoint(0.0F, 2.5F, 0.0F + xMove);

        this.bodyThin = new RendererModel(this, 0, 0);
        this.bodyThin.addBox(-5.0F, 0.0F, 0.0F, 10, 11, 22, 0.0F);
        this.bodyThin.setRotationPoint(0.0F, 2.5F, 0.0F + xMove);

        this.udder = new RendererModel(this, 24, 67);
        this.udder.addBox(-2.0F, 0.0F, 0.0F, 4, 4, 6, 0.0F);
        this.udder.setRotationPoint(0.0F, 10.5F, 15.75F + xMove);

        this.nipples = new RendererModel(this, 24, 77);
        this.nipples.addBox(-2.0F, 0.0F, -1.0F, 1, 2, 1, -0.15F);
        this.nipples.setTextureOffset(29, 77);
        this.nipples.addBox(1.0F, 0.0F, -1.0F, 1, 2, 1, -0.15F);
        this.nipples.setTextureOffset(35, 77);
        this.nipples.addBox(-2.0F, 0.0F, 2.0F, 1, 2, 1, -0.15F);
        this.nipples.setTextureOffset(40, 77);
        this.nipples.addBox(1.0F, 0.0F, 2.0F, 1, 2, 1, -0.15F);
        this.nipples.setRotationPoint(0.0F, 3.35F, 11.25F + xMove);

        this.humpXSmall = new RendererModel(this, 0, 8);
        this.humpXSmall.addBox(-2.0F, 0.0F, 0.0F, 4, 8, 6, -1.0F);
        this.humpXSmall.setRotationPoint(0.0F, 0.0F, 0.0F + xMove);
        
        this.humpSmall = new RendererModel(this, 0, 8);
        this.humpSmall.addBox(-2.0F, 0.0F, 0.0F, 4, 8, 6, -0.5F);
        this.humpSmall.setRotationPoint(0.0F, 0.0F, 0.0F + xMove);

        this.humpSmallish = new RendererModel(this, 0, 8);
        this.humpSmallish.addBox(-2.0F, 0.0F, 0.0F, 4, 8, 6, -0.25F);
        this.humpSmallish.setRotationPoint(0.0F, 0.0F, 0.0F + xMove);

        this.humpMedium = new RendererModel(this, 0, 8);
        this.humpMedium.addBox(-2.0F, 0.0F, 0.0F, 4, 8, 6, 0.0F);
        this.humpMedium.setRotationPoint(0.0F, 0.0F, 0.0F + xMove);

        this.humpLargeish = new RendererModel(this, 0, 8);
        this.humpLargeish.addBox(-2.0F, 0.0F, 0.0F, 4, 8, 6, 0.5F);
        this.humpLargeish.setRotationPoint(0.0F, 0.0F, 0.0F + xMove);

        this.humpLarge = new RendererModel(this, 0, 8);
        this.humpLarge.addBox(-2.0F, 0.0F, 0.0F, 4, 8, 6, 1.0F);
        this.humpLarge.setRotationPoint(0.0F, 0.0F, 0.0F + xMove);

        this.humpXLarge = new RendererModel(this, 0, 8);
        this.humpXLarge.addBox(-2.0F, 0.0F, 0.0F, 4, 8, 6, 1.5F);
        this.humpXLarge.setRotationPoint(0.0F, 0.0F, 0.0F + xMove);

        this.tail0 = new RendererModel(this, 0,0);
        this.tail0.addBox(-1.0F, 0.0F, 0.0F, 2, 4, 1);
        this.tail0.setRotationPoint(0.0F, 2.5F, 12.0F);

        this.tail1 = new RendererModel(this, 6,0);
        this.tail1.addBox(-0.5F, 0.0F, 0.0F, 1, 4, 1);
        this.tail1.setRotationPoint(0.0F, 4.0F, 0.0F);

        this.tail2 = new RendererModel(this, 10,0);
        this.tail2.addBox(-0.5F, 0.0F, 0.0F, 1, 3, 1);
        this.tail2.setRotationPoint(0.0F, 3.0F, 0.0F);

        this.tailBrush = new RendererModel(this, 14,0);
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
        this.shortLeg1.setRotationPoint(-6.0F, 13.5F, 0.0F + xMove);

        this.shortLeg2 = new RendererModel(this, 12, 54);
        this.shortLeg2.addBox(0.0F, 0.0F, 0.0F, 3, 7, 3, 0.0F);
        this.shortLeg2.setRotationPoint(3.0F, 13.5F, 0.0F + xMove);

        this.shortLeg3 = new RendererModel(this, 0, 67);
        this.shortLeg3.addBox(0.0F, 0.0F, 0.0F, 3, 7, 3, 0.0F);
        this.shortLeg3.setRotationPoint(-6.0F, 13.5F, 19.0F + xMove);

        this.shortLeg4 = new RendererModel(this, 12, 67);
        this.shortLeg4.addBox(0.0F, 0.0F, 0.0F, 3, 7, 3, 0.0F);
        this.shortLeg4.setRotationPoint(3.0F, 13.5F, 19.0F + xMove);

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
        this.tail0.addChild(this.tail1);
        this.tail1.addChild(this.tail2);
        this.tail2.addChild(this.tailBrush);
        this.udder.addChild(this.nipples);

    }

    @Override
    public void render(T enhancedCow, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        int[] genes = enhancedCow.getSharedGenes();
        List<String> unrenderedModels = this.setRotationAngles(enhancedCow, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, genes);

        this.size = enhancedCow.getSize();
        this.bagSize = enhancedCow.getBagSize();
        String cowStatus = enhancedCow.getCowStatus();
        float dwarf = 0.0F;
        float bodyWidth = -0.165F;
        float bodyLength = 0.0F;
        int hump = 0;
        char[] uuidArray;

        if (enhancedCow.getMooshroomUUID().isEmpty()) {
            uuidArray = enhancedCow.getCachedUniqueIdString().toCharArray();
        } else {
            uuidArray = enhancedCow.getMooshroomUUID().toCharArray();
        }

        float horns = calculateHorns(genes, uuidArray);
        //size [0.75 to 2.5]
        float hornScale = 1.0F;
        float hornShift = 0.0F;

        if (genes[26] == 1 || genes[27] == 1){
            //dwarf
            dwarf = 1.0F;
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
            } else {
                //scurs
                hornScale = (hornScale + 0.75F) * 0.5F;
            }
            //hornScale == [0.75 to 2.5]
            hornShift = (hornScale * 0.06F) - 0.06F;
        }

//        0.6F <= size <= 1.5F

        if (this.isChild) {
            renderChild(scale, dwarf, bodyWidth, bodyLength, hump);
        } else {
            renderAdult(scale, cowStatus, dwarf, bodyWidth, bodyLength, hump, horns, hornScale, hornShift, unrenderedModels);
        }
    }

    private void renderAdult(float scale, String cowStatus, float dwarf, float bodyWidth, float bodyLength, int hump, float horns, float hornScale, float hornShift, List<String> unrenderedModels) {
        GlStateManager.pushMatrix();
        GlStateManager.scalef(size + (size * bodyWidth), size, size + (size * bodyLength));
        GlStateManager.translatef(0.0F, (-1.45F + 1.45F / size) + ((0.23F - (size-0.7F)*0.0375F)*dwarf), 0.0F);

        renderHorns(scale, horns, hornScale, hornShift, unrenderedModels);

        this.bodyMedium.render(scale);

        renderLegs(scale, dwarf);

        renderHump(scale, hump, false);

        this.headModel.render(scale);

        GlStateManager.popMatrix();

        GlStateManager.pushMatrix();
        GlStateManager.scalef(size, size, size);
        GlStateManager.translatef(0.0F, (-1.45F + 1.45F / size) + ((0.23F - (size-0.7F)*0.0375F)*dwarf), 0.0F);

        this.tail0.render(scale);

        GlStateManager.popMatrix();

        if (cowStatus.equals(EntityState.PREGNANT.toString()) || cowStatus.equals(EntityState.MOTHER.toString())) {
            GlStateManager.pushMatrix();
            GlStateManager.scalef(bagSize * size, bagSize * size, bagSize * size);
            GlStateManager.translatef(0.0F, (-1.45F + 1.45F / size) + ((-0.6F*((1.5F-size)*2.0F)) + (0.6F*((1.5F-size)*2.0F))/bagSize) + ((0.23F - (size-0.7F)*0.0375F)*dwarf), ((1.0F-bagSize)*0.35F)); // if biggest 0, -0.16, 0 : if smallest 0, -0.66, 0
            GlStateManager.translatef(0.0F, 0.0F, 0.0F);
                this.udder.render(scale);
            GlStateManager.popMatrix();
        }
    }

    private void renderChild(float scale, float dwarf, float bodyWidth, float bodyLength, int hump) {
        float childSize = size / 3;
        //            if (cowStatus.contains(EntityState.CHILD_STAGE_ONE.toString())) {
        GlStateManager.pushMatrix();
        GlStateManager.scalef(childSize + (childSize * bodyWidth), childSize, childSize + (childSize * bodyLength));
        GlStateManager.translatef(0.0F, (-1.45F + 1.45F / childSize) + ((0.23F - ((childSize)-0.7F)*0.0375F)*dwarf)+0.05F, 0.0F);

        this.headModel.render(scale);

        if (bodyWidth >= 3) {
            this.bodySlim.render(scale);
        }else{
            this.bodyThin.render(scale);
        }

        renderHump(scale, hump, true);
        this.tail0.render(scale);

        GlStateManager.popMatrix();
        GlStateManager.pushMatrix();
        GlStateManager.scalef(childSize + (childSize * bodyWidth), (childSize*1.5F), childSize + (childSize * bodyLength));
        GlStateManager.translatef(0.0F, (-1.45F + 1.45F / (childSize*1.5F)) + ((0.23F - ((childSize*1.5F)-0.7F)*0.0375F)*dwarf), 0.0F);

        renderLegs(scale, dwarf);

        GlStateManager.popMatrix();
    }

    private void renderHorns(float scale, float horns, float hornScale, float hornShift, List<String> unrenderedModels) {
        if (horns != 0.0F) {
            Map<String, List<Float>> mapOfScale = new HashMap<>();

            List<Float> scalingsForHorn = createScalings(hornScale, -hornShift, hornShift, hornShift+0.025F);
            mapOfScale.put("HornL0", scalingsForHorn);
            mapOfScale.put("HornR0", mirrorX(scalingsForHorn, false));
            this.hornGranparent.render(scale, mapOfScale, unrenderedModels);
        }
    }

    private void renderLegs(float scale, float dwarf) {
        if (dwarf != 0.0F) {
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

    private void renderHump(float scale, int hump, boolean child) {
        if(child) {
            if (hump >= 12){
                this.humpLargeish.render(scale);
            }else if (hump >= 10){
                this.humpMedium.render(scale);
            }else if (hump >= 8){
                this.humpSmallish.render(scale);
            }else if (hump >= 6){
                this.humpSmall.render(scale);
            }else if (hump >= 4){
                this.humpXSmall.render(scale);
            }
        } else {
            if(hump == 12){
                this.humpXLarge.render(scale);
            }else if (hump >= 10){
                this.humpLarge.render(scale);
            }else if (hump >= 8){
                this.humpLargeish.render(scale);
            }else if (hump >= 6){
                this.humpMedium.render(scale);
            }else if (hump >= 4){
                this.humpSmallish.render(scale);
            }else if (hump >= 2){
                this.humpSmall.render(scale);
            }else if (hump == 1){
                this.humpXSmall.render(scale);
            }
        }
    }

    public List<String> setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, int[] sharedGenes) {
        super.setRotationAngles(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
        List<String> unrenderedModels = new ArrayList<>();

        boolean sleeping = ((EnhancedAnimal)entityIn).isAnimalSleeping();
        char[] uuidArray;

        if (entityIn.getMooshroomUUID().equals("0")) {
            uuidArray = entityIn.getCachedUniqueIdString().toCharArray();
        } else {
            uuidArray = entityIn.getMooshroomUUID().toCharArray();
        }

        this.headModel.rotateAngleX = (headPitch * 0.017453292F);
        this.headModel.rotateAngleY = netHeadYaw * 0.017453292F;

        this.humpXLarge.rotateAngleX = ((headPitch * 0.017453292F)/2.5F) - 0.2F;
        this.humpXLarge.rotateAngleY = (netHeadYaw * 0.017453292F)/2.5F;

        copyModelAngles(humpXLarge, humpLarge);
        copyModelAngles(humpXLarge, humpLargeish);
        copyModelAngles(humpXLarge, humpMedium);
        copyModelAngles(humpXLarge, humpSmallish);
        copyModelAngles(humpXLarge, humpSmall);
        copyModelAngles(humpXLarge, humpXSmall);

        if (sleeping) {
            this.leg1.rotateAngleX = 1.575F;
            this.leg2.rotateAngleX = 1.575F;
            this.leg3.rotateAngleX = -1.575F;
            this.leg4.rotateAngleX = -1.575F;
        } else {
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

        setEarRotations(sharedGenes);

        setHornRotations(sharedGenes, uuidArray, unrenderedModels, entityIn);

        return unrenderedModels;
    }

    @Override
    public void setLivingAnimations(T entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTickTime) {
        super.setLivingAnimations(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTickTime);
        EnhancedCow enhancedCow = entitylivingbaseIn;
        int[] sharedGenes = (entitylivingbaseIn).getSharedGenes();
        boolean sleeping = enhancedCow.isAnimalSleeping();
//        int age = enhancedCow.;
        float onGround;

        if (sleeping) {
            onGround = sleepingAnimation(sharedGenes);
        } else {
            onGround = standingAnimation();
        }

        this.bodyThin.rotationPointY = this.bodyMedium.rotationPointY;
        this.bodySlim.rotationPointY = this.bodyMedium.rotationPointY;


        this.headModel.rotationPointY = onGround + (entitylivingbaseIn).getHeadRotationPointY(partialTickTime) * 9.0F;
        this.headRotationAngleX = (entitylivingbaseIn).getHeadRotationAngleX(partialTickTime);


        setHumpAnimation(sharedGenes, onGround);

        int bodyShape = 0;
        for (int i = 1; i < sharedGenes[54]; i++) {
            bodyShape++;
        }
        for (int i = 1; i < sharedGenes[55]; i++) {
            bodyShape++;
        }

        if (this.isChild && bodyShape != 0) {
            bodyShape = 1;
        }

        if (bodyShape == 4) {
            this.tail0.rotationPointZ = 14.0F;
        } else if (bodyShape == 3) {
            this.tail0.rotationPointZ = 13.0F;
        } else {
            this.tail0.rotationPointZ = 12.0F;
        }
    }

    private void setHornRotations(int[] sharedGenes, char[] uuidArray, List<String> unrenderedModels, T entityIn) {

        this.hornNub1.rotateAngleX = ((float)Math.PI / -2F);
        this.hornNub2.rotateAngleX = ((float)Math.PI / -2F);
        this.hornNub3.rotateAngleX = ((float)Math.PI / -2F);
        this.hornNub4.rotateAngleX = ((float)Math.PI / -2F);
        this.hornNub5.rotateAngleX = ((float)Math.PI / -2F);

        copyModelAngles(headModel, hornGranparent);
        copyModelAngles(actualHead, hornParent);

        hornL0.setRotationPoint(0.0F, 0.0F, -2.25F);
        hornR0.setRotationPoint(0.0F, 0.0F, -2.25F);

        float horns = calculateHorns(sharedGenes, uuidArray);

        Float[] hornGrowthL = {-1.0F, -2.0F, -2.0F, -2.0F, -2.0F, -1.8F, -1.6F, -1.4F, -1.2F, -1.0F};
        Float[] hornGrowthR = {-1.0F, -2.0F, -2.0F, -2.0F, -2.0F, -1.8F, -1.6F, -1.4F, -1.2F, -1.0F};

        if (horns != 0.0F && !this.isChild) {

            Float[] hornHideL = {0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F};
            Float[] hornHideR = {0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F};


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
                    lengthL = lengthL + 2;
                }
                if (sharedGenes[89] == 2) {
                    lengthL = lengthL + 2;
                }
//                if (sharedGenes[90] == 2 || sharedGenes[91] == 2) {
//                    lengthL = lengthL + 2;
//                }

                lengthR = lengthL;
            }

            if (lengthL > 9) {
                lengthL = 9;
            }
            if (lengthR > 9) {
                lengthR = 9;
            }

            lengthL = 0;
            lengthR = 0;



            if (lengthL != 0) {
                for (int i = 0; i <= 9; i++) {
                    if (i <= lengthL) {
                        hornGrowthL[i] = 0.0F;
                        unrenderedModels.add(this.leftHorns.get(i).boxName);
                    }
                    if (i <= lengthR) {
                        hornGrowthR[i] = 0.0F;
                        unrenderedModels.add(this.rightHorns.get(i).boxName);
                    }
                    if (i == lengthL) {
                        unrenderedModels.remove(this.leftHorns.get(i).boxName);
                    }
                    if (i == lengthR) {
                        unrenderedModels.remove(this.rightHorns.get(i).boxName);
                    }
                }
            }

            this.hornL0.rotationPointY = hornGrowthL[0] + hornHideL[0];
            this.hornR0.rotationPointY = hornGrowthR[0] + hornHideR[0];

            this.hornL0.rotationPointX = 1.0F;
            this.hornR0.rotationPointX = -1.0F;

            for (int i = 1; i <= 9; i++) {
                this.leftHorns.get(i).rotationPointY = hornGrowthL[i];
                this.rightHorns.get(i).rotationPointY = hornGrowthR[i];

                this.leftHorns.get(i).rotationPointX = hornHideL[i];
                this.rightHorns.get(i).rotationPointX = -hornHideR[i];
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

        this.hornNub1.rotationPointY = 1.0F;
        this.hornNub2.rotationPointY = 1.0F;
        this.hornNub3.rotationPointY = 1.0F;
        this.hornNub4.rotationPointY = 1.0F;
        this.hornNub5.rotationPointY = 1.0F;

        if (this.isChild) {
            if (hornNubLength == 1){
                this.hornNub1.rotationPointY = -1.0F;
            }else if (hornNubLength == 2){
                this.hornNub2.rotationPointY = -1.0F;
            }else if (hornNubLength == 3){
                this.hornNub3.rotationPointY = -1.0F;
            }else if (hornNubLength == 4){
                this.hornNub4.rotationPointY = -1.0F;
            }else if (hornNubLength == 5){
                this.hornNub5.rotationPointY = -1.0F;
            }
        } else {
            if (hornNubLength == 1){
                this.hornNub1.rotationPointY = -1.0F + horns;
                this.hornL0.rotateAngleX = 0.25F;
                this.hornR0.rotateAngleX = 0.25F;
            }else if (hornNubLength == 2){
                this.hornNub2.rotationPointY = -1.0F + horns;
                this.hornL0.rotateAngleX = 0.15F;
                this.hornR0.rotateAngleX = 0.15F;
            }else if (hornNubLength == 3){
                this.hornNub3.rotationPointY = -1.0F + horns;
                this.hornL0.rotateAngleX = 0.05F;
                this.hornR0.rotateAngleX = 0.05F;
            }else if (hornNubLength == 4){
                this.hornNub4.rotationPointY = -1.0F + horns;
                this.hornL0.rotateAngleX = -0.15F;
                this.hornR0.rotateAngleX = -0.15F;
            }else if (hornNubLength == 5){
                this.hornNub5.rotationPointY = -1.0F + horns;
                this.hornL0.rotateAngleX = -0.25F;
                this.hornR0.rotateAngleX = -0.25F;
            }
        }

        //horn shape controllers
        if (horns != 0) {
            Float[] hornCalculationsZ = {0.0F, -0.17F, 0.1F, 0.2F, 0.01F, -0.2F, -0.37F, -0.57F, -0.27F, -0.14F};
            Float[] hornCalculationsX = {0.0F, -0.17F, 0.1F, 0.2F, 0.01F, -0.2F, -0.37F, -0.57F, -0.27F, -0.14F};
            Float[] hornCalculationsY = {0.0F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F};

            EnhancedCow enhancedCow = (EnhancedCow) entityIn;
            float[] hornAlterations = enhancedCow.getHornAlteration();

                float a = 0.0F + hornAlterations[0];    // period length       0.79
                float b = 0.0F + hornAlterations[1];   // period shift      -0.8
                float c = 1.0F / hornAlterations[2];     //wave height from d   2.3
                float d = 0.0F + hornAlterations[3];     // != 0, [

                float e = 0.0F + hornAlterations[4];         //makes turns in horn sharper
                float f = 0.0F + hornAlterations[5];
                float g = 1.0F / hornAlterations[6];
                float h = 0.0F + hornAlterations[7];     // != 0, [
            
                float i = 0.0F + hornAlterations[8];
                float j = 0.0F + hornAlterations[9];

                float rootRotationZ = hornAlterations[10] + 1.0F; //root rotation seems to go odd easily.
                float rootRotationX = hornAlterations[11] - 1.0F;
                float rootRotationY = hornAlterations[12] - 1.5F;

//                a = (float)((sharedGenes[84] >> 2) / 16);
//                e = ((float)(((sharedGenes[84] << 2) >> 2) / 16));

//                d = (float)((sharedGenes[96] + sharedGenes[97]) / 20);
//                h = (float)((sharedGenes[96] + sharedGenes[97]) / 20) - 1.0F;
//
//                d = d+((float)((sharedGenes[94] + sharedGenes[95])/40));

                if (horns != -1.0F) {
                    a = a/2;
                    e = e/3;
                    c = c*2;
                    g = g*3;
                }

                for (int z = 1; z <= 9; z++) {
                    hornCalculationsZ[z] = (float)((Math.cos((z * (a)) + (b)) + d) / c);
                    hornCalculationsX[z] = -(float)((Math.cos((z * (e)) + (f)) + h) / g);
                    hornCalculationsY[z] = i + (j * hornCalculationsY[z-1]);
                }
                for (int z = 1; z <= 9; z++) {
                    if (hornGrowthL[z] != 0.0F) {
                        hornGrowthL[z] = 1.0F;
                    }
                    if (hornGrowthR[z] != 0.0F) {
                        hornGrowthR[z] = 1.0F;
                    }
                }

            this.hornL0.rotateAngleZ = rootRotationZ;
            this.hornR0.rotateAngleZ = -rootRotationZ;
            this.hornL0.rotateAngleX = rootRotationX;
            this.hornR0.rotateAngleX = rootRotationX;
            this.hornL0.rotateAngleY = -rootRotationY;
            this.hornR0.rotateAngleY = rootRotationY;

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

            this.hornL9.rotateAngleY = -hornCalculationsY[9] * hornGrowthL[9];
            this.hornL8.rotateAngleY = -hornCalculationsY[8] * hornGrowthL[8];
            this.hornL7.rotateAngleY = -hornCalculationsY[7] * hornGrowthL[7];
            this.hornL6.rotateAngleY = -hornCalculationsY[6] * hornGrowthL[6];
            this.hornL5.rotateAngleY = -hornCalculationsY[5] * hornGrowthL[5];
            this.hornL4.rotateAngleY = -hornCalculationsY[4] * hornGrowthL[4];
            this.hornL3.rotateAngleY = -hornCalculationsY[3] * hornGrowthL[3];
            this.hornL2.rotateAngleY = -hornCalculationsY[2] * hornGrowthL[2];
            this.hornL1.rotateAngleY = -(hornCalculationsY[1] * hornGrowthL[1]);

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

            this.hornR9.rotateAngleY = hornCalculationsY[9] * hornGrowthR[9];
            this.hornR8.rotateAngleY = hornCalculationsY[8] * hornGrowthR[8];
            this.hornR7.rotateAngleY = hornCalculationsY[7] * hornGrowthR[7];
            this.hornR6.rotateAngleY = hornCalculationsY[6] * hornGrowthR[6];
            this.hornR5.rotateAngleY = hornCalculationsY[5] * hornGrowthR[5];
            this.hornR4.rotateAngleY = hornCalculationsY[4] * hornGrowthR[4];
            this.hornR3.rotateAngleY = hornCalculationsY[3] * hornGrowthR[3];
            this.hornR2.rotateAngleY = hornCalculationsY[2] * hornGrowthR[2];
            this.hornR1.rotateAngleY = (hornCalculationsY[1] * hornGrowthR[1]);
            
            

        }
    }

    private void setEarRotations(int[] sharedGenes) {
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
            } else if (earSize <= 3) {
                this.earSmallL.setRotationPoint(3.0F, 0.75F, -2.5F);
                this.earSmallL.rotateAngleZ = 1.1F + (earSize / 6.25F);
                this.earSmallR.setRotationPoint(-3.0F, 0.75F, -2.5F);
                this.earSmallR.rotateAngleZ = -(1.1F + (earSize / 6.25F));
            } else if (earSize <= 5) {
                this.earMediumL.setRotationPoint(3.0F + (floppiness / 3), 1.0F, -2.5F);
                this.earMediumL.rotateAngleZ = 1.1F + (earSize / 6.25F);
                this.earMediumR.setRotationPoint(-(3.0F + (floppiness / 3)), 1.0F, -2.5F);
                this.earMediumR.rotateAngleZ = -(1.1F + (earSize / 6.25F));
            } else if (earSize <= 7) {
                this.earLongL.setRotationPoint(3.0F + (floppiness / 2), 1.0F, -2.5F);
                this.earLongL.rotateAngleZ = 1.1F + (earSize / 6.25F);
                this.earLongR.setRotationPoint(-(3.0F + (floppiness / 2)), 1.0F, -2.5F);
                this.earLongR.rotateAngleZ = -(1.1F + (earSize / 6.25F));
            } else {
                this.earLongestL.setRotationPoint(3.0F + floppiness, 1.0F, -2.5F);
                this.earLongestL.rotateAngleZ = 1.1F + (earSize / 6.25F);
                this.earLongestR.setRotationPoint(-(3.0F + floppiness), 1.0F, -2.5F);
                this.earLongestR.rotateAngleZ = -(1.1F + (earSize / 6.25F));
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
            } else if (earSize <= 3) {
                this.earSmallL.setRotationPoint(3.0F, 0.75F, -2.5F);
                this.earSmallL.rotateAngleZ = floppiness;
                this.earSmallR.setRotationPoint(-3.0F, 0.75F, -2.5F);
                this.earSmallR.rotateAngleZ = -floppiness;
            } else if (earSize <= 5) {
                this.earMediumL.setRotationPoint(3.0F + (floppiness / 3), 1.0F, -2.5F);
                this.earMediumL.rotateAngleZ = floppiness;
                this.earMediumR.setRotationPoint(-(3.0F + (floppiness / 3)), 1.0F, -2.5F);
                this.earMediumR.rotateAngleZ = -floppiness;
            } else if (earSize <= 7) {
                this.earLongL.setRotationPoint(3.0F + (floppiness / 2), 1.0F, -2.5F);
                this.earLongL.rotateAngleZ = floppiness;
                this.earLongR.setRotationPoint(-(3.0F + (floppiness / 2)), 1.0F, -2.5F);
                this.earLongR.rotateAngleZ = -floppiness;
            } else {
                this.earLongestL.setRotationPoint(3.0F + floppiness, 1.0F, -2.5F);
                this.earLongestL.rotateAngleZ = floppiness;
                this.earLongestR.setRotationPoint(-(3.0F + floppiness), 1.0F, -2.5F);
                this.earLongestR.rotateAngleZ = -floppiness;
            }

        }
    }

    private float sleepingAnimation(int[] sharedGenes) {
        float onGround;
        if (this.isChild) {
            onGround = 13.85F;
            this.bodyMedium.rotationPointY = 13.65F;
            this.tail0.rotationPointY = 13.65F;
        } else {
            onGround = 9.80F;
            this.bodyMedium.rotationPointY = 9.75F;
            this.tail0.rotationPointY = 9.75F;

            this.udder.rotationPointY = 17.55F;
            if (size < 1) {
                this.udder.rotationPointY = this.udder.rotationPointY - (bagSize - 1.0F)*3.0F;
            }
        }
        if (sharedGenes[26] == 1 || sharedGenes[27] == 1){
            //dwarf
            onGround = onGround - 3.75F;
            this.bodyMedium.rotationPointY = this.bodyMedium.rotationPointY - 3.75F;
            this.tail0.rotationPointY = this.bodyMedium.rotationPointY;
            this.udder.rotationPointY = this.udder.rotationPointY - 3.75F;
        }

        this.leg1.setRotationPoint(-6.0F, 14.0F+onGround, -10.0F);
        this.leg2.setRotationPoint(3.0F, 14.0F+onGround, -10.0F);
        this.leg3.setRotationPoint(-6.0F, 11.0F+onGround, 12.0F);
        this.leg4.setRotationPoint(3.0F, 11.0F+onGround, 12.0F);
        return onGround;
    }

    private float standingAnimation() {
        float onGround;
        onGround = 2.75F;
        this.bodyMedium.rotationPointY = 2.5F;
        this.tail0.rotationPointY = 2.5F;

        this.udder.rotationPointY = 10.5F;
        if (size < 1) {
            this.udder.rotationPointY = this.udder.rotationPointY - (bagSize - 1.0F)*3.0F;
        }

        this.leg1.setRotationPoint(-6.0F, 13.5F, -10.0F);
        this.leg2.setRotationPoint(3.0F, 13.5F, -10.0F);
        this.leg3.setRotationPoint(-6.0F, 13.5F, 9.0F);
        this.leg4.setRotationPoint(3.0F, 13.5F, 9.0F);
        return onGround;
    }

    private void setHumpAnimation(int[] sharedGenes, float onGround) {
        if (sharedGenes[40] != 1 && sharedGenes[41] != 1) {
            int child = 1;
            if (this.isChild) {
                child = 2;
            }
            float hump = 2.0F;
            for (int i = 1; i < sharedGenes[40]; i++) {
                hump = hump - 0.5F;
            }
            for (int i = 1; i < sharedGenes[41]; i++) {
                hump = hump - 0.5F;
            }

            this.humpXLarge.rotationPointY = (hump / child) + (onGround - 2.75F);
            this.humpLarge.rotationPointY = (hump / child) + (onGround - 2.75F);
            this.humpLargeish.rotationPointY = (hump / child) + (onGround - 2.75F);
            this.humpMedium.rotationPointY = (hump / child) + (onGround - 2.75F);
            this.humpSmallish.rotationPointY = (hump / child) + (onGround - 2.75F);
            this.humpSmall.rotationPointY = (hump / child) + (onGround - 2.75F);
            this.humpXSmall.rotationPointY = (hump / child) + (onGround - 2.75F);

        } else {
            int child = 1;
            if (this.isChild) {
                child = 2;
            }

            this.humpXLarge.rotationPointY = (2.0F / child) + (onGround - 2.75F);
            this.humpLarge.rotationPointY = (2.0F / child) + (onGround - 2.75F);
            this.humpLargeish.rotationPointY = (2.0F / child) + (onGround - 2.75F);
            this.humpMedium.rotationPointY = (2.0F / child) + (onGround - 2.75F);
            this.humpSmallish.rotationPointY = (2.0F / child) + (onGround - 2.75F);
            this.humpSmall.rotationPointY = (2.0F / child) + (onGround - 2.75F);
            this.humpXSmall.rotationPointY = (2.0F / child) + (onGround - 2.75F);
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
        if (!this.isChild) {

            if (sharedGenes[13] == 1 || sharedGenes[14] == 1) {
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
            } else {
                //horned
            }
        } else {
            horns = 0.0F;
        }

        return horns;
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



}
