package mokiyoki.enhancedanimals.model;

import com.mojang.blaze3d.platform.GlStateManager;
import mokiyoki.enhancedanimals.entity.EnhancedCow;
import mokiyoki.enhancedanimals.entity.EntityState;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.util.math.MathHelper;

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
    private final RendererModel hornGranparent;
    private final RendererModel hornParent;
    private final RendererModel hornL0;
    private final RendererModel hornL1;
    private final RendererModel hornL2;
    private final RendererModel hornL3;
    private final RendererModel hornL4;
    private final RendererModel hornL5;
    private final RendererModel hornL6;
    private final RendererModel hornL7;
    private final RendererModel hornL8;
    private final RendererModel hornL9;
    private final RendererModel hornR0;
    private final RendererModel hornR1;
    private final RendererModel hornR2;
    private final RendererModel hornR3;
    private final RendererModel hornR4;
    private final RendererModel hornR5;
    private final RendererModel hornR6;
    private final RendererModel hornR7;
    private final RendererModel hornR8;
    private final RendererModel hornR9;
    private final RendererModel headModel; //this is the neck not the head
    private final RendererModel bodyChonk;
    private final RendererModel bodyBig;
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
    private final RendererModel chonkLeg1;
    private final RendererModel chonkLeg2;
    private final RendererModel chonkLeg3;
    private final RendererModel chonkLeg4;
    private final RendererModel shortLeg1;
    private final RendererModel shortLeg2;
    private final RendererModel shortLeg3;
    private final RendererModel shortLeg4;
    private final RendererModel miniChonkLeg1;
    private final RendererModel miniChonkLeg2;
    private final RendererModel miniChonkLeg3;
    private final RendererModel miniChonkLeg4;
    private final RendererModel legExtender1;
    private final RendererModel legExtender2;
    private final RendererModel legExtender3;
    private final RendererModel legExtender4;


    public ModelEnhancedCow() {

        this.textureWidth = 80;
        this.textureHeight = 80;

        float xMove = -10.0F;

        this.actualHead = new RendererModel(this, 0, 33);
        this.actualHead.addBox(-4.0F, 0.0F, -7.0F, 8, 7, 6, 0.0F);
        this.actualHead.setTextureOffset(28, 33);
        this.actualHead.addBox(-2.0F, 0.1F, -11.0F, 4, 5, 4, 0.0F);
        this.actualHead.setTextureOffset(16, 46);
        this.actualHead.addBox(-2.5F, 0.2F, -13.0F, 5, 4, 3, 0.0F);
        this.actualHead.setRotationPoint(0.0F, 0.0F, -7.0F);

        this.mouth = new RendererModel(this, 25, 46);
        this.mouth.addBox(-1.5F, 1.0F, -10.0F, 3, 3, 7, 0.1F);
        this.mouth.setTextureOffset(37, 46);
        this.mouth.addBox(-1.5F, 3.0F, -10.0F, 3, 1, 6, -0.1F);
        this.mouth.setRotationPoint(0.0F, 4.0F, -2.0F);

        this.earSmallestL = new RendererModel(this, 8, 46);
        this.earSmallestL.addBox(0.0F, -3.0F, -0.5F, 3, 3, 1);
        
        this.earSmallL = new RendererModel(this, 8, 46);
        this.earSmallL.addBox(0.0F, -4.0F, -0.5F, 3, 4, 1);

        this.earMediumL = new RendererModel(this, 8, 46);
        this.earMediumL.addBox(0.0F, -5.0F, -0.5F, 3, 5, 1);

        this.earLongL = new RendererModel(this, 8, 46);
        this.earLongL.addBox(0.0F, -6.0F, -0.5F, 3, 6, 1, 0.15F);

        this.earLongestL = new RendererModel(this, 8, 46);
        this.earLongestL.addBox(0.0F, -7.0F, -0.5F, 3, 7, 1, 0.3F);

        this.earSmallestR = new RendererModel(this, 0, 46);
        this.earSmallestR.addBox(-3.0F, -3.0F, -0.5F, 3, 3, 1);

        this.earSmallR = new RendererModel(this, 0, 46);
        this.earSmallR.addBox(-3.0F, -4.0F, -0.5F, 3, 4, 1);

        this.earMediumR = new RendererModel(this, 0, 46);
        this.earMediumR.addBox(-3.0F, -5.0F, -0.5F, 3, 5, 1);

        this.earLongR = new RendererModel(this, 0, 46);
        this.earLongR.addBox(-3.0F, -6.0F, -0.5F, 3, 6, 1, 0.15F);

        this.earLongestR = new RendererModel(this, 0, 46);
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

        this.hornGranparent = new RendererModel(this, 0, 0);
        this.hornParent = new RendererModel(this, 0, 0);
        this.hornGranparent.addChild(hornParent);

        this.hornL0 = new RendererModel(this, 64, 34);
        this.hornL0.addBox(-2.0F, -4.0F, -2.0F, 4, 4, 4, -1.0F);

        this.hornL1 = new RendererModel(this, 64, 42);
        this.hornL1.addBox(-2.0F, -4.0F, -2.0F, 4, 4, 4, -1.0F);

        this.hornL2 = new RendererModel(this, 64, 50);
        this.hornL2.addBox(-2.0F, -4.0F, -2.0F, 4, 4, 4, -1.0F);

        this.hornL3 = new RendererModel(this, 64, 58);
        this.hornL3.addBox(-2.0F, -4.0F, -2.0F, 4, 4, 4, -1.0F);

        this.hornL4 = new RendererModel(this, 64, 66);
        this.hornL4.addBox(-2.0F, -4.0F, -2.0F, 4, 4, 4, -1.0F);

        this.hornL5 = new RendererModel(this, 64, 66);
        this.hornL5.addBox(-2.0F, -4.0F, -2.0F, 4, 4, 4, -1.1F);

        this.hornL6 = new RendererModel(this, 64, 66);
        this.hornL6.addBox(-2.0F, -4.0F, -2.0F, 4, 4, 4, -1.2F);

        this.hornL7 = new RendererModel(this, 64, 66);
        this.hornL7.addBox(-2.0F, -4.0F, -2.0F, 4, 4, 4, -1.3F);

        this.hornL8 = new RendererModel(this, 64, 66);
        this.hornL8.addBox(-2.0F, -4.0F, -2.0F, 4, 4, 4, -1.4F);

        this.hornL9 = new RendererModel(this, 64, 66);
        this.hornL9.addBox(-2.0F, -4.0F, -2.0F, 4, 4, 4, -1.5F);

        this.hornR0 = new RendererModel(this, 64, 34);
        this.hornR0.addBox(-2.0F, -4.0F, -2.0F, 4, 4, 4, -1.0F);

        this.hornR1 = new RendererModel(this, 64, 42);
        this.hornR1.addBox(-2.0F, -4.0F, -2.0F, 4, 4, 4, -1.0F);

        this.hornR2 = new RendererModel(this, 64, 50);
        this.hornR2.addBox(-2.0F, -4.0F, -2.0F, 4, 4, 4, -1.0F);

        this.hornR3 = new RendererModel(this, 64, 58);
        this.hornR3.addBox(-2.0F, -4.0F, -2.0F, 4, 4, 4, -1.0F);

        this.hornR4 = new RendererModel(this, 64, 66);
        this.hornR4.addBox(-2.0F, -4.0F, -2.0F, 4, 4, 4, -1.0F);

        this.hornR5 = new RendererModel(this, 64, 66);
        this.hornR5.addBox(-2.0F, -4.0F, -2.0F, 4, 4, 4, -1.1F);

        this.hornR6 = new RendererModel(this, 64, 66);
        this.hornR6.addBox(-2.0F, -4.0F, -2.0F, 4, 4, 4, -1.2F);

        this.hornR7 = new RendererModel(this, 64, 66);
        this.hornR7.addBox(-2.0F, -4.0F, -2.0F, 4, 4, 4, -1.3F);

        this.hornR8 = new RendererModel(this, 64, 66);
        this.hornR8.addBox(-2.0F, -4.0F, -2.0F, 4, 4, 4, -1.4F);

        this.hornR9 = new RendererModel(this, 64, 66);
        this.hornR9.addBox(-2.0F, -4.0F, -2.0F, 4, 4, 4, -1.5F);

        // head is the neck cause thats how this works
        this.headModel = new RendererModel(this, 46, 0);
        this.headModel.addBox(-3.0F, 0.0F, -8.0F, 6, 8, 11, 0.0F);
        this.headModel.setRotationPoint(0.0F, 0.0F, 0.0F + xMove);

        this.bodyChonk = new RendererModel(this, 0, 0);
        this.bodyChonk.addBox(-6.0F, 0.0F, 0.0F, 12, 11, 22, 1.0F);
        this.bodyChonk.setRotationPoint(0.0F, 2.5F, 0.0F + xMove);

        this.bodyBig = new RendererModel(this, 0, 0);
        this.bodyBig.addBox(-6.0F, 0.0F, 0.0F, 12, 11, 22, 0.5F);
        this.bodyBig.setRotationPoint(0.0F, 2.5F, 0.0F + xMove);

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

        this.chonkLeg1 = new RendererModel(this, 0, 54);
        this.chonkLeg1.addBox(0.0F, 0.0F, 0.0F, 3, 10, 3, 0.5F);

        this.chonkLeg2 = new RendererModel(this, 12, 54);
        this.chonkLeg2.addBox(0.0F, 0.0F, 0.0F, 3, 10, 3, 0.5F);

        this.chonkLeg3 = new RendererModel(this, 0, 67);
        this.chonkLeg3.addBox(0.0F, 0.0F, 0.0F, 3, 10, 3, 0.5F);

        this.chonkLeg4 = new RendererModel(this, 12, 67);
        this.chonkLeg4.addBox(0.0F, 0.0F, 0.0F, 3, 10, 3, 0.5F);

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

        this.miniChonkLeg1 = new RendererModel(this, 0, 54);
        this.miniChonkLeg1.addBox(0.0F, 0.0F, 0.0F, 3, 7, 3, 0.5F);

        this.miniChonkLeg2 = new RendererModel(this, 12, 54);
        this.miniChonkLeg2.addBox(0.0F, 0.0F, 0.0F, 3, 7, 3, 0.5F);

        this.miniChonkLeg3 = new RendererModel(this, 0, 67);
        this.miniChonkLeg3.addBox(0.0F, 0.0F, 0.0F, 3, 7, 3, 0.5F);

        this.miniChonkLeg4 = new RendererModel(this, 12, 67);
        this.miniChonkLeg4.addBox(0.0F, 0.0F, 0.0F, 3, 7, 3, 0.5F);

        this.legExtender1 = new RendererModel(this, 0, 54);
        this.legExtender1.addBox(0.0F, -0.5F, 0.0F, 3, 1, 3, 0.0F);
        this.legExtender1.setRotationPoint(-6.0F, 13.5F, 0.0F + xMove);

        this.legExtender2 = new RendererModel(this, 12, 54);
        this.legExtender2.addBox(0.0F, -0.5F, 0.0F, 3, 1, 3, 0.0F);
        this.legExtender2.setRotationPoint(3.0F, 13.5F, 0.0F + xMove);

        this.legExtender3 = new RendererModel(this, 0, 67);
        this.legExtender3.addBox(0.0F, -0.5F, 0.0F, 3, 1, 3, 0.0F);
        this.legExtender3.setRotationPoint(-6.0F, 13.5F, 19.0F + xMove);

        this.legExtender4 = new RendererModel(this, 12, 67);
        this.legExtender4.addBox(0.0F, -0.5F, 0.0F, 3, 1, 3, 0.0F);
        this.legExtender4.setRotationPoint(3.0F, 13.5F, 19.0F + xMove);

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
        this.setRotationAngles(enhancedCow, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);

        int[] genes = enhancedCow.getSharedGenes();
        this.size = enhancedCow.getSize();
        this.bagSize = enhancedCow.getBagSize();
        String cowStatus = enhancedCow.getCowStatus();
        float dwarf = 0.0F;
        int bodyShape = 0;
        int hump = 0;

        if (genes[26] == 1 || genes[27] == 1){
            //dwarf
            dwarf = 1.0F;
        }

        for (int i = 1; i < genes[54]; i++){
            bodyShape++;
        }
        for (int i = 1; i < genes[55]; i++){
            bodyShape++;
        }

        for (int i = 1; i < genes[38]; i++){
            hump++;
        }

        for (int i = 1; i < genes[39]; i++){
            hump++;
        }

//        0.6F <= size <= 1.5F

        if (this.isChild) {
//            if (cowStatus.contains(EntityState.CHILD_STAGE_ONE.toString())) {
                GlStateManager.pushMatrix();
                GlStateManager.scalef(0.4F, 0.4F, 0.4F);
                GlStateManager.translatef(0.0F, (-1.45F + 1.45F / 0.46F) + ((0.23F - (0.46F-0.7F)*0.0375F)*dwarf), 0.0F);

                this.headModel.render(scale);

                if (bodyShape >= 3) {
                    this.bodySlim.render(scale);
                }else{
                    this.bodyThin.render(scale);
                }

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
                this.tail0.render(scale);

                GlStateManager.popMatrix();
                GlStateManager.pushMatrix();
                GlStateManager.scalef(0.4F, 0.7F, 0.4F);
                GlStateManager.translatef(0.0F, (-1.45F + 1.45F / 0.7F) + ((0.23F - (0.7F-0.7F)*0.0375F)*dwarf), 0.0F);
                //TODO change y translation when calf is dwarf

                if (dwarf != 0.0F){
                    this.shortLeg1.render(scale);
                    this.shortLeg2.render(scale);
                    this.shortLeg3.render(scale);
                    this.shortLeg4.render(scale);
                }else {
                    this.leg1.render(scale);
                    this.leg2.render(scale);
                    this.leg3.render(scale);
                    this.leg4.render(scale);
                }

                GlStateManager.popMatrix();

//            }else if (cowStatus.contains(EntityState.CHILD_STAGE_TWO.toString())) {
//                GlStateManager.pushMatrix();
//                GlStateManager.scalef(0.6F, 0.6F, 0.6F);
//                GlStateManager.translatef(0.0F, -1.5F + 1.5F/0.675F, 0.0F);
//                GlStateManager.translatef(0.0F, (-1.45F + 1.45F / 0.6F) + ((0.23F - (0.6F-0.7F)*0.0375F)*dwarf), 0.0F);
//
//                this.headModel.render(scale);
//
//                if (bodyShape >= 3) {
//                    this.bodySlim.render(scale);
//                }else{
//                    this.bodyThin.render(scale);
//                }
//
//                if (hump >= 12){
//                    this.humpLargeish.render(scale);
//                }else if (hump >= 10){
//                    this.humpMedium.render(scale);
//                }else if (hump >= 8){
//                    this.humpSmallish.render(scale);
//                }else if (hump >= 6){
//                    this.humpSmall.render(scale);
//                }else if (hump >= 4){
//                    this.humpXSmall.render(scale);
//                }
//                this.tail0.render(scale);
//
//                GlStateManager.popMatrix();
//                GlStateManager.pushMatrix();
//                GlStateManager.scalef(0.6F, 0.75F, 0.6F);
//                GlStateManager.translatef(0.0F, -1.5F + 1.5F/0.75F, 0.0F);
//                GlStateManager.translatef(0.0F, (-1.45F + 1.45F / 0.75F) + ((0.23F - (0.75F-0.7F)*0.0375F)*dwarf), 0.0F);
//                //TODO change y translation when calf is dwarf
//
//                if (dwarf != 0.0F){
//                    this.shortLeg1.render(scale);
//                    this.shortLeg2.render(scale);
//                    this.shortLeg3.render(scale);
//                    this.shortLeg4.render(scale);
//                }else {
//                    this.leg1.render(scale);
//                    this.leg2.render(scale);
//                    this.leg3.render(scale);
//                    this.leg4.render(scale);
//                }
//
//                GlStateManager.popMatrix();
//            }else if (cowStatus.contains(EntityState.CHILD_STAGE_THREE.toString())) {
//                GlStateManager.pushMatrix();
//                GlStateManager.scalef(0.7F, 0.7F, 0.7F);
//                GlStateManager.translatef(0.0F, -1.5F + 1.5F/0.732F, 0.0F);
//                GlStateManager.translatef(0.0F, (-1.45F + 1.45F / 0.7F) + ((0.23F - (0.7F-0.7F)*0.0375F)*dwarf), 0.0F);
//
//                this.headModel.render(scale);
//
//                if (bodyShape >= 3) {
//                    this.bodySlim.render(scale);
//                }else{
//                    this.bodyThin.render(scale);
//                }
//
//                if (hump >= 12){
//                    this.humpLargeish.render(scale);
//                }else if (hump >= 10){
//                    this.humpMedium.render(scale);
//                }else if (hump >= 8){
//                    this.humpSmallish.render(scale);
//                }else if (hump >= 6){
//                    this.humpSmall.render(scale);
//                }else if (hump >= 4){
//                    this.humpXSmall.render(scale);
//                }
//                this.tail0.render(scale);
//
//                GlStateManager.popMatrix();
//                GlStateManager.pushMatrix();
//                GlStateManager.scalef(0.7F, 0.8F, 0.7F);
//                GlStateManager.translatef(0.0F, -1.5F + 1.5F/0.8F, 0.0F);
//                GlStateManager.translatef(0.0F, (-1.45F + 1.45F / 0.8F) + ((0.23F - (0.8F-0.7F)*0.0375F)*dwarf), 0.0F);
//                //TODO change y translation when calf is dwarf
//
//                if (dwarf != 0.0F){
//                    this.shortLeg1.render(scale);
//                    this.shortLeg2.render(scale);
//                    this.shortLeg3.render(scale);
//                    this.shortLeg4.render(scale);
//                }else {
//                    this.leg1.render(scale);
//                    this.leg2.render(scale);
//                    this.leg3.render(scale);
//                    this.leg4.render(scale);
//                }
//
//                GlStateManager.popMatrix();
//            } else {
//                GlStateManager.popMatrix();
//                GlStateManager.pushMatrix();
//                GlStateManager.scalef(0.7F, 0.8F, 0.7F);
//                GlStateManager.translatef(0.0F, -1.5F + 1.5F/0.8F, 0.0F);
//                GlStateManager.translatef(0.0F, (-1.45F + 1.45F / 0.8F) + ((0.23F - (0.8F-0.7F)*0.0375F)*dwarf), 0.0F);
//                //TODO change y translation when calf is dwarf
//
//                if (dwarf != 0.0F){
//                    this.shortLeg1.render(scale);
//                    this.shortLeg2.render(scale);
//                    this.shortLeg3.render(scale);
//                    this.shortLeg4.render(scale);
//                }else {
//                    this.leg1.render(scale);
//                    this.leg2.render(scale);
//                    this.leg3.render(scale);
//                    this.leg4.render(scale);
//                }
//
//                GlStateManager.popMatrix();
//            }

        } else {

            GlStateManager.pushMatrix();
            GlStateManager.scalef(size, size, size);
            GlStateManager.translatef(0.0F, (-1.45F + 1.45F / size) + ((0.23F - (size-0.7F)*0.0375F)*dwarf), 0.0F);

            this.headModel.render(scale);

            char[] uuidArry;
//        int age = enhancedCow.;

            if (enhancedCow.getMooshroomUUID().isEmpty()) {
                uuidArry = enhancedCow.getCachedUniqueIdString().toCharArray();
            } else {
                uuidArry = enhancedCow.getMooshroomUUID().toCharArray();
            }

            if (calculateHorns(genes, uuidArry) != 0.0F) {
                this.hornGranparent.render(scale);
            }

            if (bodyShape == 4){
                this.bodyChonk.render(scale);
            }else if (bodyShape == 3){
                this.bodyBig.render(scale);
            }else if (bodyShape == 2){
                this.bodyMedium.render(scale);
            }else if (bodyShape == 1){
                this.bodySlim.render(scale);
                this.legExtender1.render(scale);
                this.legExtender2.render(scale);
                this.legExtender3.render(scale);
                this.legExtender4.render(scale);
            }else{
                this.bodyThin.render(scale);
            }

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

            if (dwarf != 0.0F){
                if (bodyShape >= 3){
                    this.miniChonkLeg1.render(scale);
                    this.miniChonkLeg2.render(scale);
                    this.miniChonkLeg3.render(scale);
                    this.miniChonkLeg4.render(scale);
                }else {
                    this.shortLeg1.render(scale);
                    this.shortLeg2.render(scale);
                    this.shortLeg3.render(scale);
                    this.shortLeg4.render(scale);
                }
            }else {
                if (bodyShape >= 3){
                    this.chonkLeg1.render(scale);
                    this.chonkLeg2.render(scale);
                    this.chonkLeg3.render(scale);
                    this.chonkLeg4.render(scale);
                }else {
                    this.leg1.render(scale);
                    this.leg2.render(scale);
                    this.leg3.render(scale);
                    this.leg4.render(scale);
                }
            }

            this.tail0.render(scale);

            GlStateManager.popMatrix();

            if (cowStatus.equals(EntityState.PREGNANT.toString()) || cowStatus.equals(EntityState.MOTHER.toString())) {
//            if (true) {
            GlStateManager.pushMatrix();
            GlStateManager.scalef(bagSize * size, bagSize * size, bagSize * size);
            GlStateManager.translatef(0.0F, (-1.45F + 1.45F / size) + ((-0.6F*((1.5F-size)*2.0F)) + (0.6F*((1.5F-size)*2.0F))/bagSize) + ((0.23F - (size-0.7F)*0.0375F)*dwarf), ((1.0F-bagSize)*0.35F)); // if biggest 0, -0.16, 0 : if smallest 0, -0.66, 0
            GlStateManager.translatef(0.0F, 0.0F, 0.0F);
                this.udder.render(scale);
                GlStateManager.popMatrix();
            }

//            if (true) {
//                GlStateManager.pushMatrix();
//                GlStateManager.scalef(size/2F, size/2F, size/2F);
//                GlStateManager.translatef(0.0F, (-1.45F + 1.45F / size) + ((0.23F - (size-0.7F)*0.0375F)*dwarf), 0.0F);

//            this.hornGranparent.render(scale);

//                GlStateManager.popMatrix();
//            }

        }
    }

    @Override
    public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {

        super.setRotationAngles(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);

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

        this.leg1.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        this.leg2.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
        this.leg3.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
        this.leg4.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;

        this.chonkLeg1.rotateAngleX = this.leg1.rotateAngleX;
        this.chonkLeg2.rotateAngleX = this.leg2.rotateAngleX;
        this.chonkLeg3.rotateAngleX = this.leg3.rotateAngleX;
        this.chonkLeg4.rotateAngleX = this.leg4.rotateAngleX;

        this.miniChonkLeg1.rotateAngleX = this.leg1.rotateAngleX;
        this.miniChonkLeg2.rotateAngleX = this.leg2.rotateAngleX;
        this.miniChonkLeg3.rotateAngleX = this.leg3.rotateAngleX;
        this.miniChonkLeg4.rotateAngleX = this.leg4.rotateAngleX;

        copyModelAngles(leg1, shortLeg1);
        copyModelAngles(leg2, shortLeg2);
        copyModelAngles(leg3, shortLeg3);
        copyModelAngles(leg4, shortLeg4);

        copyModelAngles(leg1, legExtender1);

        copyModelAngles(leg2, legExtender2);
        copyModelAngles(leg3, legExtender3);
        copyModelAngles(leg4, legExtender4);

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

        this.hornNub1.rotateAngleX = ((float)Math.PI / -2F);
        this.hornNub2.rotateAngleX = ((float)Math.PI / -2F);
        this.hornNub3.rotateAngleX = ((float)Math.PI / -2F);
        this.hornNub4.rotateAngleX = ((float)Math.PI / -2F);
        this.hornNub5.rotateAngleX = ((float)Math.PI / -2F);

        copyModelAngles(headModel, hornGranparent);
        copyModelAngles(actualHead, hornParent);

    }

    @Override
    public void setLivingAnimations(T entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTickTime) {
        super.setLivingAnimations(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTickTime);

        this.headModel.rotationPointY = 2.75F + (entitylivingbaseIn).getHeadRotationPointY(partialTickTime) * 9.0F;
        this.headRotationAngleX = (entitylivingbaseIn).getHeadRotationAngleX(partialTickTime);

        EnhancedCow enhancedCow = entitylivingbaseIn;

        int[] sharedGenes = (entitylivingbaseIn).getSharedGenes();
        char[] uuidArry;
//        int age = enhancedCow.;

        if (enhancedCow.getMooshroomUUID().isEmpty()) {
            uuidArry = enhancedCow.getCachedUniqueIdString().toCharArray();
        } else {
            uuidArry = enhancedCow.getMooshroomUUID().toCharArray();
        }


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

            this.humpXLarge.rotationPointY = hump / child;
            this.humpLarge.rotationPointY = hump / child;
            this.humpLargeish.rotationPointY = hump / child;
            this.humpMedium.rotationPointY = hump / child;
            this.humpSmallish.rotationPointY = hump / child;
            this.humpSmall.rotationPointY = hump / child;
            this.humpXSmall.rotationPointY = hump / child;

        } else {
            int child = 1;
            if (this.isChild) {
                child = 2;
            }

            this.humpXLarge.rotationPointY = 2.0F / child;
            this.humpLarge.rotationPointY = 2.0F / child;
            this.humpLargeish.rotationPointY = 2.0F / child;
            this.humpMedium.rotationPointY = 2.0F / child;
            this.humpSmallish.rotationPointY = 2.0F / child;
            this.humpSmall.rotationPointY = 2.0F / child;
            this.humpXSmall.rotationPointY = 2.0F / child;
        }

        int bodyShape = 0;
        for (int i = 1; i < sharedGenes[54]; i++) {
            bodyShape++;
        }
        for (int i = 1; i < sharedGenes[55]; i++) {
            bodyShape++;
        }

        if (isChild && bodyShape != 0) {
            bodyShape = 1;
        }


        //default leg position
        this.leg1.setRotationPoint(-6.0F, 13.5F, -10.0F);
        this.leg2.setRotationPoint(3.0F, 13.5F, -10.0F);
        this.leg3.setRotationPoint(-6.0F, 13.5F, 9.0F);
        this.leg4.setRotationPoint(3.0F, 13.5F, 9.0F);


        if (bodyShape == 4) {
            this.leg1.rotationPointX = -7.0F;
            this.leg3.rotationPointX = -7.0F;
            this.leg2.rotationPointX = 4.0F;
            this.leg4.rotationPointX = 4.0F;
            this.chonkLeg1.setRotationPoint(-6.7F, 13.5F, -10.7F);
            this.chonkLeg2.setRotationPoint(3.7F, 13.5F, -10.7F);
            this.chonkLeg3.setRotationPoint(-6.7F, 13.5F, 9.7F);
            this.chonkLeg4.setRotationPoint(3.7F, 13.5F, 9.7F);
            this.miniChonkLeg1.setRotationPoint(-6.7F, 13.5F, -10.7F);
            this.miniChonkLeg2.setRotationPoint(3.7F, 13.5F, -10.7F);
            this.miniChonkLeg3.setRotationPoint(-6.7F, 13.5F, 9.7F);
            this.miniChonkLeg4.setRotationPoint(3.7F, 13.5F, 9.7F);
            this.tail0.rotationPointZ = 13.0F;

        } else if (bodyShape == 3) {
            this.leg1.rotationPointX = -6.5F;
            this.leg3.rotationPointX = -6.5F;
            this.leg2.rotationPointX = 3.5F;
            this.leg4.rotationPointX = 3.5F;
            this.tail0.rotationPointZ = 12.5F;
            this.chonkLeg1.setRotationPoint(-6.1F, 13.5F, -10.1F);
            this.chonkLeg2.setRotationPoint(3.1F, 13.5F, -10.1F);
            this.chonkLeg3.setRotationPoint(-6.1F, 13.5F, 9.1F);
            this.chonkLeg4.setRotationPoint(3.1F, 13.5F, 9.1F);
            this.miniChonkLeg1.setRotationPoint(-6.1F, 13.5F, -10.1F);
            this.miniChonkLeg2.setRotationPoint(3.1F, 13.5F, -10.1F);
            this.miniChonkLeg3.setRotationPoint(-6.1F, 13.5F, 9.1F);
            this.miniChonkLeg4.setRotationPoint(3.1F, 13.5F, 9.1F);
        } else if (bodyShape == 1) {
            this.leg1.rotationPointX = -5.5F;
            this.leg3.rotationPointX = -5.5F;
            this.leg2.rotationPointX = 2.5F;
            this.leg4.rotationPointX = 2.5F;
            this.leg1.rotationPointZ = -9.6F;
            this.leg3.rotationPointZ = 8.5F;
            this.leg2.rotationPointZ = -9.5F;
            this.leg4.rotationPointZ = 8.5F;
            this.tail0.rotationPointZ = 11.5F;
        } else if (bodyShape == 0) {
            this.leg1.rotationPointX = -5.0F;
            this.leg3.rotationPointX = -5.0F;
            this.leg2.rotationPointX = 2.0F;
            this.leg4.rotationPointX = 2.0F;
            this.tail0.rotationPointZ = 12.0F;
        } else {
            this.leg1.rotationPointX = -6.0F;
            this.leg3.rotationPointX = -6.0F;
            this.leg2.rotationPointX = 3.0F;
            this.leg4.rotationPointX = 3.0F;
            this.tail0.rotationPointZ = 12.0F;
        }

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

        if (this.isChild) {
            hornL0.setRotationPoint(0.0F, 2.75F, -2.25F);
            hornR0.setRotationPoint(0.0F, 2.75F, -2.25F);
        } else {
            hornL0.setRotationPoint(0.0F, 0.0F, -2.25F);
            hornR0.setRotationPoint(0.0F, 0.0F, -2.25F);
        }

        float X = 1.0F;
        float Y = -2.0F;
        float horns = -1.0F;

        horns = calculateHorns(sharedGenes, uuidArry);

        if (horns != 0.0F && !this.isChild) {
            this.hornL0.rotationPointX = X;
            this.hornR0.rotationPointX = -X;

            this.hornL0.rotationPointY = Y + 1.0F;
            this.hornR0.rotationPointY = Y + 1.0F;

            this.hornL1.rotationPointY = Y;
            this.hornR1.rotationPointY = Y;
            this.hornL2.rotationPointY = Y;
            this.hornR2.rotationPointY = Y;
            this.hornL3.rotationPointY = Y;
            this.hornR3.rotationPointY = Y;
            this.hornL4.rotationPointY = Y;
            this.hornR4.rotationPointY = Y;
            this.hornL5.rotationPointY = Y + 0.2F;
            this.hornR5.rotationPointY = Y + 0.2F;
            this.hornL6.rotationPointY = Y + 0.4F;
            this.hornR6.rotationPointY = Y + 0.4F;
            this.hornL7.rotationPointY = Y + 0.6F;
            this.hornR7.rotationPointY = Y + 0.6F;
            this.hornL8.rotationPointY = Y + 0.8F;
            this.hornR8.rotationPointY = Y + 0.8F;
            this.hornL9.rotationPointY = Y + 1.0F;
            this.hornR9.rotationPointY = Y + 1.0F;

        } else if (!this.isChild) {
            this.hornL0.rotationPointX = 0.0F;
            this.hornR0.rotationPointX = 0.0F;
            this.hornL0.rotationPointY = 0.0F;
            this.hornR0.rotationPointY = 0.0F;
            this.hornL1.rotationPointY = 0.0F;
            this.hornR1.rotationPointY = 0.0F;
            this.hornL2.rotationPointY = 0.0F;
            this.hornR2.rotationPointY = 0.0F;
            this.hornL3.rotationPointY = 0.0F;
            this.hornR3.rotationPointY = 0.0F;
            this.hornL4.rotationPointY = 0.0F;
            this.hornR4.rotationPointY = 0.0F;
            this.hornL5.rotationPointY = 0.0F;
            this.hornR5.rotationPointY = 0.0F;
            this.hornL6.rotationPointY = 0.0F;
            this.hornR6.rotationPointY = 0.0F;
            this.hornL7.rotationPointY = 0.0F;
            this.hornR7.rotationPointY = 0.0F;
            this.hornL8.rotationPointY = 0.0F;
            this.hornR8.rotationPointY = 0.0F;
            this.hornL9.rotationPointY = 0.0F;
            this.hornR9.rotationPointY = 0.0F;
        } else {
            this.hornL0.rotationPointX = 0.0F;
            this.hornR0.rotationPointX = 0.0F;
            this.hornL1.rotationPointY = 0.0F;
            this.hornR1.rotationPointY = 0.0F;
            this.hornL2.rotationPointY = 0.0F;
            this.hornR2.rotationPointY = 0.0F;
            this.hornL3.rotationPointY = 0.0F;
            this.hornR3.rotationPointY = 0.0F;
            this.hornL4.rotationPointY = 0.0F;
            this.hornR4.rotationPointY = 0.0F;
            this.hornL5.rotationPointY = 0.0F;
            this.hornR5.rotationPointY = 0.0F;
            this.hornL6.rotationPointY = 0.0F;
            this.hornR6.rotationPointY = 0.0F;
            this.hornL7.rotationPointY = 0.0F;
            this.hornR7.rotationPointY = 0.0F;
            this.hornL8.rotationPointY = 0.0F;
            this.hornR8.rotationPointY = 0.0F;
            this.hornL9.rotationPointY = 0.0F;
            this.hornR9.rotationPointY = 0.0F;
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
            float hornTest = 1.4F;
            if (hornNubLength == 1){
                this.hornNub1.rotationPointY = -1.0F + horns;
                this.hornL0.rotateAngleZ = hornTest;
                this.hornL0.rotateAngleX = 0.25F;
                this.hornR0.rotateAngleZ = -hornTest;
                this.hornR0.rotateAngleX = 0.25F;
            }else if (hornNubLength == 2){
                this.hornNub2.rotationPointY = -1.0F + horns;
                this.hornL0.rotateAngleZ = hornTest;
                this.hornL0.rotateAngleX = 0.15F;
                this.hornR0.rotateAngleZ = -hornTest;
                this.hornR0.rotateAngleX = 0.15F;
            }else if (hornNubLength == 3){
                this.hornNub3.rotationPointY = -1.0F + horns;
                this.hornL0.rotateAngleZ = hornTest;
                this.hornL0.rotateAngleX = 0.05F;
                this.hornR0.rotateAngleZ = -hornTest;
                this.hornR0.rotateAngleX = 0.05F;
            }else if (hornNubLength == 4){
                this.hornNub4.rotationPointY = -1.0F + horns;
                this.hornL0.rotateAngleZ = hornTest;
                this.hornL0.rotateAngleX = -0.15F;
                this.hornR0.rotateAngleZ = -hornTest;
                this.hornR0.rotateAngleX = -0.15F;
            }else if (hornNubLength == 5){
                this.hornNub5.rotationPointY = -1.0F + horns;
                this.hornL0.rotateAngleZ = hornTest;
                this.hornL0.rotateAngleX = -0.25F;
                this.hornR0.rotateAngleZ = -hornTest;
                this.hornR0.rotateAngleX = -0.25F;
            }
}

        //horn shape controller
        if (horns != 0) {
            this.hornL9.rotateAngleZ = -0.14F;
            this.hornL8.rotateAngleZ = -0.27F;
            this.hornL7.rotateAngleZ = -0.57F;
            this.hornL6.rotateAngleZ = -0.37F;
            this.hornL5.rotateAngleZ = -0.2F;
            this.hornL4.rotateAngleZ = 0.0F;
            this.hornL3.rotateAngleZ = 0.2F;
            this.hornL2.rotateAngleZ = 0.1F;
            this.hornL1.rotateAngleZ = -0.17F;

            this.hornL9.rotateAngleX = 0.1F;
            this.hornL8.rotateAngleX = 0.1F;
            this.hornL7.rotateAngleX = 0.1F;
            this.hornL6.rotateAngleX = 0.1F;
            this.hornL5.rotateAngleX = 0.1F;
            this.hornL4.rotateAngleX = 0.1F;
            this.hornL3.rotateAngleX = 0.1F;
            this.hornL2.rotateAngleX = 0.1F;
            this.hornL1.rotateAngleX = 0.1F;

            this.hornR9.rotateAngleZ = 0.14F;
            this.hornR8.rotateAngleZ = 0.27F;
            this.hornR7.rotateAngleZ = 0.57F;
            this.hornR6.rotateAngleZ = 0.37F;
            this.hornR5.rotateAngleZ = 0.2F;
            this.hornR4.rotateAngleZ = -0.0F;
            this.hornR3.rotateAngleZ = -0.2F;
            this.hornR2.rotateAngleZ = -0.1F;
            this.hornR1.rotateAngleZ = 0.17F;

            this.hornR9.rotateAngleX = 0.1F;
            this.hornR8.rotateAngleX = 0.1F;
            this.hornR7.rotateAngleX = 0.1F;
            this.hornR6.rotateAngleX = 0.1F;
            this.hornR5.rotateAngleX = 0.1F;
            this.hornR4.rotateAngleX = 0.1F;
            this.hornR3.rotateAngleX = 0.1F;
            this.hornR2.rotateAngleX = 0.1F;
            this.hornR1.rotateAngleX = 0.1F;

        }
            this.udder.rotationPointY = 10.5F;
        if (size < 1) {
            this.udder.rotationPointY = this.udder.rotationPointY - (bagSize - 1.0F)*3.0F;
        }

    }

    private float calculateHorns(int[] sharedGenes, char[] uuidArry) {
        float horns = -1.0F;
        if (!this.isChild) {

            if (sharedGenes[13] == 1 || sharedGenes[14] == 1) {
                //should be polled unless...
                //african horn gene
                if (sharedGenes[76] == 1 && sharedGenes[77] == 1) {
                    //horned
                } else if (sharedGenes[76] == 1 || sharedGenes[77] == 1) {
                    //sex determined horned
                    if (Character.isLetter(uuidArry[0]) || uuidArry[0] - 48 >= 8) {
                        //horned if male
                    } else {
                        //polled if female unless
                        if (sharedGenes[78] == 1 && sharedGenes[79] == 1) {
                            //she is scured
                        } else {
                            //polled
                            horns = 0.0F;
                        }
                    }
                } else {
                    //polled
                    if (sharedGenes[78] == 1 && sharedGenes[79] == 1) {
                        //scured
                    } else if (sharedGenes[78] == 1 || sharedGenes[79] == 1) {
                        //sex determined scured
                        if (Character.isLetter(uuidArry[0]) || uuidArry[0] - 48 >= 8) {
                            //scurred
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
