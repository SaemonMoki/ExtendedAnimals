package mokiyoki.enhancedanimals.model;

import com.mojang.blaze3d.platform.GlStateManager;
import mokiyoki.enhancedanimals.entity.EnhancedSheep;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.concurrent.ThreadLocalRandom;

@OnlyIn(Dist.CLIENT)
public class ModelEnhancedSheep  <T extends EnhancedSheep> extends EntityModel<T> {

    private float headRotationAngleX;
    private float f12 = 0F;
    private int coatlength = 0;

    private final RendererModel head;
    private final RendererModel hornBase;
    private final RendererModel polyHornBase;
    private final RendererModel polyHornL0;
    private final RendererModel hornL0;
    private final RendererModel hornL01;
    private final RendererModel hornL02;
    private final RendererModel hornL03;
    private final RendererModel hornL04;
    private final RendererModel hornL05;
    private final RendererModel hornL06;
    private final RendererModel hornL07;
    private final RendererModel hornL08;
    private final RendererModel hornL09;
    private final RendererModel hornL1;
    private final RendererModel hornL2;
    private final RendererModel hornL3;
    private final RendererModel hornL4;
    private final RendererModel hornL5;
    private final RendererModel hornL6;
    private final RendererModel hornL7;
    private final RendererModel hornL8;
    private final RendererModel hornL9;
    private final RendererModel polyHornR0;
    private final RendererModel hornR0;
    private final RendererModel hornR01;
    private final RendererModel hornR02;
    private final RendererModel hornR03;
    private final RendererModel hornR04;
    private final RendererModel hornR05;
    private final RendererModel hornR06;
    private final RendererModel hornR07;
    private final RendererModel hornR08;
    private final RendererModel hornR09;
    private final RendererModel hornR1;
    private final RendererModel hornR2;
    private final RendererModel hornR3;
    private final RendererModel hornR4;
    private final RendererModel hornR5;
    private final RendererModel hornR6;
    private final RendererModel hornR7;
    private final RendererModel hornR8;
    private final RendererModel hornR9;
    private final RendererModel earsR;
    private final RendererModel earsL;
    private final RendererModel body;
    private final RendererModel wool1;
    private final RendererModel wool2;
    private final RendererModel wool3;
    private final RendererModel wool4;
    private final RendererModel wool5;
    private final RendererModel wool6;
    private final RendererModel wool7;
    private final RendererModel wool8;
    private final RendererModel wool9;
    private final RendererModel wool10;
    private final RendererModel wool11;
    private final RendererModel wool12;
    private final RendererModel wool13;
    private final RendererModel wool14;
    private final RendererModel wool15;
    private final RendererModel neckWool1;
    private final RendererModel neckWool2;
    private final RendererModel neckWool3;
    private final RendererModel neckWool4;
    private final RendererModel neckWool5;
    private final RendererModel neckWool6;
    private final RendererModel neckWool7;
//    private final RendererModel headWool1;
//    private final RendererModel headWool2;
//    private final RendererModel headWool3;
//    private final RendererModel headWool4;
//    private final RendererModel headWool5;
//    private final RendererModel headWool6;
//    private final RendererModel headWool7;
//    private final RendererModel faceWool1;
//    private final RendererModel faceWool2;
//    private final RendererModel faceWool3;
//    private final RendererModel faceWool4;
//    private final RendererModel faceWool5;
//    private final RendererModel faceWool6;
//    private final RendererModel faceWool7;
    private final RendererModel tailBase;
    private final RendererModel tailMiddle;
    private final RendererModel tailTip;
    public final RendererModel leg1;
    public final RendererModel leg2;
    public final RendererModel leg3;
    public final RendererModel leg4;

    public ModelEnhancedSheep()
    {
        this.textureWidth = 64;
        this.textureHeight = 64;

        float xMove = -6.0F;

        this.head = new RendererModel(this, 0, 0);
        this.head.addBox(-2.5F, -8.0F, -5.0F, 5, 4, 4, 0.0F); //skull
        this.head.setTextureOffset(0, 8);
        this.head.addBox(-2.0F, -8.0F, -8.0F, 4, 3, 3, 0.0F); //nose

        this.head.setTextureOffset(34, 0);
        this.head.addBox(-2.0F, -7.0F, -4.0F, 4, 9, 4, 0.0F); //neck
        this.head.setRotationPoint(0.0F, 0.0F, -6.0F);

        this.hornBase = new RendererModel(this, 0, 36);
        this.hornBase.addBox(-1.5F, -8.9F, -4.1F, 3, 3, 3, -1.0F);
        this.hornBase.setRotationPoint(0.0F, 0.0F, -6.0F);

        this.polyHornBase = new RendererModel(this, 0, 36);
        this.polyHornBase.addBox(-1.5F, -8.9F, -4.1F, 3, 3, 3, -1.0F);
        this.polyHornBase.setRotationPoint(0.0F, 0.0F, -6.0F);

        this.polyHornL0 = new RendererModel(this, 0, 36);
        this.polyHornL0.addBox(0.0F, -3.0F, -1.5F, 3, 3, 3, -0.6F);
        this.polyHornL0.setRotationPoint(3.0F, -5.0F, -1.0F);

        this.polyHornR0 = new RendererModel(this, 0, 36);
        this.polyHornR0.addBox(-3.0F, -3.0F, -1.5F, 3, 3, 3, -0.6F);
        this.polyHornR0.setRotationPoint(-3.0F, -5.0F, -1.0F);

        // scale down by 0.1 for each
        this.hornL0 = new RendererModel(this, 0, 36);
        this.hornL0.addBox(0.0F, -3.0F, -1.5F, 3, 3, 3, -0.5F);
        this.hornL0.setRotationPoint(0.0F, -7.4F, -3.0F);

        this.hornL01 = new RendererModel(this, 0, 36);
        this.hornL01.addBox(0.0F, -3.0F, -1.5F, 3, 3, 3, -0.5F);
        this.hornL01.setRotationPoint(0.0F, 0.0F, 0.0F);

        this.hornL02 = new RendererModel(this, 0, 36);
        this.hornL02.addBox(0.0F, -3.0F, -1.5F, 3, 3, 3, -0.5F);
        this.hornL02.setRotationPoint(0.0F, 0.0F, 0.0F);

        this.hornL03 = new RendererModel(this, 0, 36);
        this.hornL03.addBox(0.0F, -3.0F, -1.5F, 3, 3, 3, -0.5F);
        this.hornL03.setRotationPoint(0.0F, 0.0F, 0.0F);

        this.hornL04 = new RendererModel(this, 0, 36);
        this.hornL04.addBox(0.0F, -3.0F, -1.5F, 3, 3, 3, -0.5F);
        this.hornL04.setRotationPoint(0.0F, 0.0F, 0.0F);

        this.hornL05 = new RendererModel(this, 0, 36);
        this.hornL05.addBox(0.0F, -3.0F, -1.5F, 3, 3, 3, -0.5F);
        this.hornL05.setRotationPoint(0.0F, 0.0F, 0.0F);

        this.hornL06 = new RendererModel(this, 0, 36);
        this.hornL06.addBox(0.0F, -3.0F, -1.5F, 3, 3, 3, -0.5F);
        this.hornL06.setRotationPoint(0.0F, 0.0F, 0.0F);

        this.hornL07 = new RendererModel(this, 0, 36);
        this.hornL07.addBox(0.0F, -3.0F, -1.5F, 3, 3, 3, -0.5F);
        this.hornL07.setRotationPoint(0.0F, 0.0F, 0.0F);

        this.hornL08 = new RendererModel(this, 0, 36);
        this.hornL08.addBox(0.0F, -3.0F, -1.5F, 3, 3, 3, -0.5F);
        this.hornL08.setRotationPoint(0.0F, 0.0F, 0.0F);

        this.hornL09 = new RendererModel(this, 0, 36);
        this.hornL09.addBox(0.0F, -3.0F, -1.5F, 3, 3, 3, -0.5F);
        this.hornL09.setRotationPoint(0.0F, 0.0F, 0.0F);

        this.hornL1 = new RendererModel(this, 0, 36);
        this.hornL1.addBox(0.0F, -3.0F, -1.5F, 3, 3, 3, -0.6F);
        this.hornL1.setRotationPoint(0.0F, 0.0F, 0.0F);

        this.hornL2 = new RendererModel(this, 0, 36);
        this.hornL2.addBox(0.0F, -3.0F, -1.5F, 3, 3, 3, -0.7F);
        this.hornL2.setRotationPoint(0.0F, 0.0F, 0.0F);

        this.hornL3 = new RendererModel(this, 0, 36);
        this.hornL3.addBox(0.0F, -3.0F, -1.5F, 3, 3, 3, -0.8F);
        this.hornL3.setRotationPoint(0.0F, 0.0F, 0.0F);

        this.hornL4 = new RendererModel(this, 0, 36);
        this.hornL4.addBox(0.0F, -3.0F, -1.5F, 3, 3, 3, -0.9F);
        this.hornL4.setRotationPoint(0.0F, 0.0F, 0.0F);

        this.hornL5 = new RendererModel(this, 0, 36);
        this.hornL5.addBox(0.0F, -3.0F, -1.5F, 3, 3, 3, -1.0F);
        this.hornL5.setRotationPoint(0.0F, 0.0F, 0.0F);

        this.hornL6 = new RendererModel(this, 0, 36);
        this.hornL6.addBox(0.0F, -3.0F, -1.5F, 3, 3, 3, -1.0562F);
        this.hornL6.setRotationPoint(0.0F, 0.0F, 0.0F);

        this.hornL7 = new RendererModel(this, 0, 36);
        this.hornL7.addBox(0.0F, -3.0F, -1.5F, 3, 3, 3, -1.1125F);
        this.hornL7.setRotationPoint(0.0F, 0.0F, 0.0F);

        this.hornL8 = new RendererModel(this, 0, 36);
        this.hornL8.addBox(0.0F, -3.0F, -1.5F, 3, 3, 3, -1.1812F);
        this.hornL8.setRotationPoint(0.0F, 0.0F, 0.0F);

        this.hornL9 = new RendererModel(this, 0, 36);
        this.hornL9.addBox(0.0F, -3.0F, -1.5F, 3, 3, 3, -1.25F);
        this.hornL9.setRotationPoint(0.0F, 0.0F, 0.0F);

        this.hornR0 = new RendererModel(this, 0, 36);
        this.hornR0.addBox(-3.0F, -3.0F, -1.5F, 3, 3, 3, -0.5F);
        this.hornR0.setRotationPoint(0.0F, -7.4F, -3.0F);

        this.hornR01 = new RendererModel(this, 0, 36);
        this.hornR01.addBox(-3.0F, -3.0F, -1.5F, 3, 3, 3, -0.5F);
        this.hornR01.setRotationPoint(0.0F, 0.0F, 0.0F);

        this.hornR02 = new RendererModel(this, 0, 36);
        this.hornR02.addBox(-3.0F, -3.0F, -1.5F, 3, 3, 3, -0.5F);
        this.hornR02.setRotationPoint(0.0F, 0.0F, 0.0F);

        this.hornR03 = new RendererModel(this, 0, 36);
        this.hornR03.addBox(-3.0F, -3.0F, -1.5F, 3, 3, 3, -0.5F);
        this.hornR03.setRotationPoint(0.0F, 0.0F, 0.0F);

        this.hornR04 = new RendererModel(this, 0, 36);
        this.hornR04.addBox(-3.0F, -3.0F, -1.5F, 3, 3, 3, -0.5F);
        this.hornR04.setRotationPoint(0.0F, 0.0F, 0.0F);

        this.hornR05 = new RendererModel(this, 0, 36);
        this.hornR05.addBox(-3.0F, -3.0F, -1.5F, 3, 3, 3, -0.5F);
        this.hornR05.setRotationPoint(0.0F, 0.0F, 0.0F);

        this.hornR06 = new RendererModel(this, 0, 36);
        this.hornR06.addBox(-3.0F, -3.0F, -1.5F, 3, 3, 3, -0.5F);
        this.hornR06.setRotationPoint(0.0F, 0.0F, 0.0F);

        this.hornR07 = new RendererModel(this, 0, 36);
        this.hornR07.addBox(-3.0F, -3.0F, -1.5F, 3, 3, 3, -0.5F);
        this.hornR07.setRotationPoint(0.0F, 0.0F, 0.0F);

        this.hornR08 = new RendererModel(this, 0, 36);
        this.hornR08.addBox(-3.0F, -3.0F, -1.5F, 3, 3, 3, -0.5F);
        this.hornR08.setRotationPoint(0.0F, 0.0F, 0.0F);

        this.hornR09 = new RendererModel(this, 0, 36);
        this.hornR09.addBox(-3.0F, -3.0F, -1.5F, 3, 3, 3, -0.5F);
        this.hornR09.setRotationPoint(0.0F, 0.0F, 0.0F);

        this.hornR1 = new RendererModel(this, 0, 36);
        this.hornR1.addBox(-3.0F, -3.0F, -1.5F, 3, 3, 3, -0.6F);
        this.hornR1.setRotationPoint(0.0F, 0.0F, 0.0F);

        this.hornR2 = new RendererModel(this, 0, 36);
        this.hornR2.addBox(-3.0F, -3.0F, -1.5F, 3, 3, 3, -0.7F);
        this.hornR2.setRotationPoint(0.0F, 0.0F, 0.0F);

        this.hornR3 = new RendererModel(this, 0, 36);
        this.hornR3.addBox(-3.0F, -3.0F, -1.5F, 3, 3, 3, -0.8F);
        this.hornR3.setRotationPoint(0.0F, 0.0F, 0.0F);

        this.hornR4 = new RendererModel(this, 0, 36);
        this.hornR4.addBox(-3.0F, -3.0F, -1.5F, 3, 3, 3, -0.9F);
        this.hornR4.setRotationPoint(0.0F, 0.0F, 0.0F);

        this.hornR5 = new RendererModel(this, 0, 36);
        this.hornR5.addBox(-3.0F, -3.0F, -1.5F, 3, 3, 3, -1.0F);
        this.hornR5.setRotationPoint(0.0F, 0.0F, 0.0F);

        this.hornR6 = new RendererModel(this, 0, 36);
        this.hornR6.addBox(-3.0F, -3.0F, -1.5F, 3, 3, 3, -1.0562F);
        this.hornR6.setRotationPoint(0.0F, 0.0F, 0.0F);

        this.hornR7 = new RendererModel(this, 0, 36);
        this.hornR7.addBox(-3.0F, -3.0F, -1.5F, 3, 3, 3, -1.1125F);
        this.hornR7.setRotationPoint(0.0F, 0.0F, 0.0F);

        this.hornR8 = new RendererModel(this, 0, 36);
        this.hornR8.addBox(-3.0F, -3.0F, -1.5F, 3, 3, 3, -1.1812F);
        this.hornR8.setRotationPoint(0.0F, 0.0F, 0.0F);

        this.hornR9 = new RendererModel(this, 0, 36);
        this.hornR9.addBox(-3.0F, -3.0F, -1.5F, 3, 3, 3, -1.25F);
        this.hornR9.setRotationPoint(0.0F, 0.0F, 0.0F);

        this.earsR = new RendererModel(this, 50, 0);
        this.earsR.addBox(-5.0F, -8.5F, -2.0F, 3, 2, 1, 0.0F); //ear right
        this.earsL = new RendererModel(this, 50, 3);
        this.earsL.addBox(2.0F, -8.5F, -2.0F, 3, 2, 1, 0.0F); //ear left

        this.body = new RendererModel(this, 2, 0);
        this.body.addBox(-4.0F, 8.0F, -2.0F, 8, 6, 16, 0.0F);
        this.body.setRotationPoint(0.0F, 0.0F, -6.0F);

        this.wool1 = new RendererModel(this, 2, 0);
        this.wool1.addBox(-4.0F, 8.0F, -2.0F, 8, 6, 16, 0.2F);

        this.wool2 = new RendererModel(this, 2, 0);
        this.wool2.addBox(-4.0F, 8.0F, -2.0F, 8, 6, 16, 0.4F);

        this.wool3 = new RendererModel(this, 2, 0);
        this.wool3.addBox(-4.0F, 8.0F, -2.0F, 8, 6, 16, 0.6F);

        this.wool4 = new RendererModel(this, 2, 0);
        this.wool4.addBox(-4.0F, 8.0F, -2.0F, 8, 6, 16, 0.8F);

        this.wool5 = new RendererModel(this, 2, 0);
        this.wool5.addBox(-4.0F, 8.0F, -2.0F, 8, 6, 16, 1.0F);

        this.wool6 = new RendererModel(this, 2, 0);
        this.wool6.addBox(-4.0F, 8.0F, -2.0F, 8, 6, 16, 1.2F);

        this.wool7 = new RendererModel(this, 2, 0);
        this.wool7.addBox(-4.0F, 8.0F, -2.0F, 8, 6, 16, 1.4F);

        this.wool8 = new RendererModel(this, 2, 0);
        this.wool8.addBox(-4.0F, 8.0F, -2.0F, 8, 6, 16, 1.6F);

        this.wool9 = new RendererModel(this, 2, 0);
        this.wool9.addBox(-4.0F, 8.0F, -2.0F, 8, 6, 16, 1.8F);

        this.wool10 = new RendererModel(this, 2, 0);
        this.wool10.addBox(-4.0F, 8.0F, -2.0F, 8, 6, 16, 2.0F);

        this.wool11 = new RendererModel(this, 2, 0);
        this.wool11.addBox(-4.0F, 8.0F, -2.0F, 8, 6, 16, 2.2F);

        this.wool12 = new RendererModel(this, 2, 0);
        this.wool12.addBox(-4.0F, 8.0F, -2.0F, 8, 6, 16, 2.4F);

        this.wool13 = new RendererModel(this, 2, 0);
        this.wool13.addBox(-4.0F, 8.0F, -2.0F, 8, 6, 16, 2.6F);

        this.wool14 = new RendererModel(this, 2, 0);
        this.wool14.addBox(-4.0F, 8.0F, -2.0F, 8, 6, 16, 2.8F);

        this.wool15 = new RendererModel(this, 2, 0);
        this.wool15.addBox(-4.0F, 8.0F, -2.0F, 8, 6, 16, 3.0F);

        this.neckWool1 = new RendererModel(this, 34, 0);
        this.neckWool1.addBox(-2.0F, -5.6F, -4.0F, 4, 9, 4, 0.4F);
        this.neckWool1.setRotationPoint(0.0F, 0.0F, -6.0F);

        this.neckWool2 = new RendererModel(this, 34, 0);
        this.neckWool2.addBox(-2.0F, -5.25F, -4.0F, 4, 8, 4, 0.75F);
        this.neckWool2.setRotationPoint(0.0F, 0.0F, -6.0F);

        this.neckWool3 = new RendererModel(this, 34, 0);
        this.neckWool3.addBox(-2.0F, -4.9F, -4.0F, 4, 7, 4, 1.1F);
        this.neckWool3.setRotationPoint(0.0F, 0.0F, -6.0F);

        this.neckWool4 = new RendererModel(this, 34, 0);
        this.neckWool4.addBox(-2.0F, -4.5F, -4.0F, 4, 6, 4, 1.5F);
        this.neckWool4.setRotationPoint(0.0F, 0.0F, -6.0F);

        this.neckWool5 = new RendererModel(this, 34, 0);
        this.neckWool5.addBox(-2.0F, -4.0F, -4.0F, 4, 6, 4, 2.0F);
        this.neckWool5.setRotationPoint(0.0F, 0.0F, -6.0F);

        this.neckWool6 = new RendererModel(this, 34, 0);
        this.neckWool6.addBox(-2.0F, -3.5F, -4.0F, 4, 5, 4, 2.5F);
        this.neckWool6.setRotationPoint(0.0F, 0.0F, -6.0F);

        this.neckWool7 = new RendererModel(this, 34, 0);
        this.neckWool7.addBox(-2.0F, -3.0F, -4.0F, 4, 5, 4, 3.0F);
        this.neckWool7.setRotationPoint(0.0F, 0.0F, -6.0F);

        this.tailBase = new RendererModel(this, 50, 6);
        this.tailBase.addBox(-1.0F, 0.0F, 0.0F, 2, 3, 1);
        this.tailBase.setRotationPoint(0.0F, 9.0F, 14.0F + xMove);

        this.tailMiddle = new RendererModel(this, 56, 6);
        this.tailMiddle.addBox(-0.5F, 0.0F, 0.0F, 1, 3, 1);
        this.tailMiddle.setRotationPoint(0.0F, 3.0F, 0.0F);

        this.tailTip = new RendererModel(this, 60, 6);
        this.tailTip.addBox(-0.5F, 0.0F, 0.0F, 1, 3, 1);
        this.tailTip.setRotationPoint(0.0F, 3.0F, 0.0F);

        this.leg1 = new RendererModel(this, 0, 22);
        this.leg1.addBox(0.0F, 0.0F, 0.0F, 3, 10, 3);
        this.leg1.setRotationPoint(-4, 14,-2 + xMove);
        this.leg2 = new RendererModel(this, 12, 22);
        this.leg2.addBox(0.0F, 0.0F, 0.0F, 3, 10, 3);
        this.leg2.setRotationPoint(1, 14,-2 + xMove);
        this.leg3 = new RendererModel(this, 24, 22);
        this.leg3.addBox(0.0F, 0.0F, 0.0F, 3, 10, 3);
        this.leg3.setRotationPoint(-4, 14,11 + xMove);
        this.leg4 = new RendererModel(this, 36, 22);
        this.leg4.addBox(0.0F, 0.0F, 0.0F, 3, 10, 3);
        this.leg4.setRotationPoint(1, 14,11 + xMove);

        this.tailBase.addChild(tailMiddle);
        this.tailMiddle.addChild(tailTip);

        this.hornBase.addChild(hornL0);
        this.hornL0.addChild(hornL09);
//        this.hornL01.addChild(hornL02);
//        this.hornL02.addChild(hornL03);
//        this.hornL03.addChild(hornL04);
//        this.hornL04.addChild(hornL05);
//        this.hornL05.addChild(hornL06);
//        this.hornL06.addChild(hornL07);
//        this.hornL07.addChild(hornL08);
//        this.hornL08.addChild(hornL09);
        this.hornL09.addChild(hornL1);
        this.hornL1.addChild(hornL2);
        this.hornL2.addChild(hornL3);
        this.hornL3.addChild(hornL4);
        this.hornL4.addChild(hornL5);
        this.hornL5.addChild(hornL6);
        this.hornL6.addChild(hornL7);
        this.hornL7.addChild(hornL8);
        this.hornL8.addChild(hornL9);

        this.hornBase.addChild(hornR0);
        this.hornR0.addChild(hornR09);
//        this.hornR01.addChild(hornR02);
//        this.hornR02.addChild(hornR03);
//        this.hornR03.addChild(hornR04);
//        this.hornR04.addChild(hornR05);
//        this.hornR05.addChild(hornR06);
//        this.hornR06.addChild(hornR07);
//        this.hornR07.addChild(hornR08);
//        this.hornR08.addChild(hornR09);
        this.hornR09.addChild(hornR1);
        this.hornR1.addChild(hornR2);
        this.hornR2.addChild(hornR3);
        this.hornR3.addChild(hornR4);
        this.hornR4.addChild(hornR5);
        this.hornR5.addChild(hornR6);
        this.hornR6.addChild(hornR7);
        this.hornR7.addChild(hornR8);
        this.hornR8.addChild(hornR9);

        this.polyHornBase.addChild(polyHornL0);
        this.polyHornL0.addChild(hornL2);
        this.polyHornBase.addChild(polyHornR0);
        this.polyHornR0.addChild(hornR2);

    }

    @Override
    public void render(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        this.setRotationAngles(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);

        EnhancedSheep enhancedSheep = (EnhancedSheep) entityIn;

        this.coatlength = enhancedSheep.getCoatLength();

        int[] genes = enhancedSheep.getSharedGenes();

        char[] uuidArry = enhancedSheep.getCachedUniqueIdString().toCharArray();

        boolean horns = false;
        boolean scurs = false;
        boolean polycerate = false;

        if (genes[6] == 2 || genes[7] == 2) {
            horns = true;
            if (genes[6] == 1 || genes[7] == 1) {
                //scurs, small horns ect
                //TODO make scurred horn variation
                scurs = true;
                horns = false;
            }
        }else if (genes[6] != 1 && genes[7] != 1){
            // genderfied horns
            if ( Character.isLetter(uuidArry[0]) || uuidArry[0]-48 >= 8 ){
                //horns if "male"
                horns = true;
            }
        }

        if (horns) {
            if (genes[36] == 1 || genes[37] == 1) {
                if (Character.isLetter(uuidArry[0]) || uuidArry[0] - 48 >= 8) {
                    //horns if "male"
                    if (Character.isLetter(uuidArry[2]) || uuidArry[2] - 48 >= 3) {
                        polycerate = true;
                    }
                } else {
                    if (uuidArry[2] - 48 <= 3) {
                        polycerate = true;
                    }
                }
            }
        }

        if (this.isChild) {
            GlStateManager.pushMatrix();
            GlStateManager.scalef(0.6F, 0.6F, 0.6F);
            GlStateManager.translatef(0.0F, 15.0F * scale, 0.0F);

            this.head.render(scale);
            this.earsR.render(scale);
            this.earsL.render(scale);

            GlStateManager.popMatrix();
            GlStateManager.pushMatrix();
            GlStateManager.scalef(0.5F, 0.5F, 0.5F);
            GlStateManager.translatef(0.0F, 20.0F * scale, 0.0F);

            this.body.render(scale);
            this.tailBase.render(scale);

            GlStateManager.popMatrix();
            GlStateManager.pushMatrix();
            GlStateManager.scalef(0.5F, 0.7F, 0.5F);
            GlStateManager.translatef(0.0F, 10.0F * scale, 0.0F);

            this.leg1.render(scale);
            this.leg2.render(scale);
            this.leg3.render(scale);
            this.leg4.render(scale);

            GlStateManager.popMatrix();

        }else {

            this.head.render(scale);
            this.earsR.render(scale);
            this.earsL.render(scale);
                this.body.render(scale);
//                coatlength = 12;
             if (coatlength == 1){
                this.wool1.render(scale);
                if (true) {
                    this.neckWool1.render(scale);
                }
            }else if (coatlength == 2){
                this.wool2.render(scale);
                 if (true) {
                     this.neckWool1.render(scale);
                 }
            }else if (coatlength == 3){
                this.wool3.render(scale);
                 if (true) {
                     this.neckWool2.render(scale);
                 }
            }else if (coatlength == 4){
                this.wool4.render(scale);
                 if (true) {
                     this.neckWool2.render(scale);
                 }
            }else if (coatlength == 5){
                this.wool5.render(scale);
                 if (true) {
                     this.neckWool3.render(scale);
                 }
            }else if (coatlength == 6){
                this.wool6.render(scale);
                 if (true) {
                     this.neckWool3.render(scale);
                 }
            }else if (coatlength == 7){
                this.wool7.render(scale);
                 if (true) {
                     this.neckWool4.render(scale);
                 }
            }else if (coatlength == 8){
                this.wool8.render(scale);
                 if (true) {
                     this.neckWool4.render(scale);
                 }
            }else if (coatlength == 9){
                this.wool9.render(scale);
                 if (true) {
                     this.neckWool5.render(scale);
                 }
            }else if (coatlength == 10){
                this.wool10.render(scale);
                 if (true) {
                     this.neckWool5.render(scale);
                 }
            }else if (coatlength == 11){
                this.wool11.render(scale);
                 if (true) {
                     this.neckWool6.render(scale);
                 }
            }else if (coatlength == 12){
                this.wool12.render(scale);
                 if (true) {
                     this.neckWool6.render(scale);
                 }
            }else if (coatlength == 13){
                this.wool13.render(scale);
                 if (true) {
                     this.neckWool7.render(scale);
                 }
            }else if (coatlength == 14){
                this.wool14.render(scale);
                 if (true) {
                     this.neckWool7.render(scale);
                 }
            }else if (coatlength == 15){
                this.wool15.render(scale);
                 if (true) {
                     this.neckWool7.render(scale);
                 }
            }
            this.tailBase.render(scale);
            this.leg1.render(scale);
            this.leg2.render(scale);
            this.leg3.render(scale);
            this.leg4.render(scale);

            if (horns){
                this.hornBase.render(scale);
                if (polycerate) {
                    this.polyHornBase.render(scale);
                }
            }

        }
    }

    /**
     * Sets the model's various rotation angles. For bipeds, par1 and par2 are used for animating the movement of arms
     * and legs, where par1 represents the time(so that arms and legs swing back and forth) and par2 represents how
     * "far" arms and legs can swing at most.
     */
    @Override
    public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor)
    {
        super.setRotationAngles(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
        this.head.rotateAngleX = headPitch * 0.017453292F;
        this.head.rotateAngleY = netHeadYaw * 0.017453292F;

        //        this.body.rotateAngleX = ((float)Math.PI / 2F);
        this.leg1.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        this.leg2.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
        this.leg3.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
        this.leg4.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;

        if (isChild) {
            this.tailBase.rotateAngleZ = MathHelper.cos(limbSwing * 0.6662F) * 1.3F * limbSwingAmount;
            this.tailMiddle.rotateAngleZ = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
            this.tailTip.rotateAngleZ = MathHelper.cos(limbSwing * 0.6662F) * 1.5F * limbSwingAmount;
        }else{
            this.tailBase.rotateAngleZ = MathHelper.cos(limbSwing * 0.6662F) * 0.4F * limbSwingAmount;
            this.tailMiddle.rotateAngleZ = MathHelper.cos(limbSwing * 0.6662F) * 0.5F * limbSwingAmount;
            this.tailTip.rotateAngleZ = MathHelper.cos(limbSwing * 0.6662F) * 0.6F * limbSwingAmount;
        }

        this.head.rotateAngleX = 1F + this.headRotationAngleX;   //might need to merge this with another line

        copyModelAngles(head, earsL);
        copyModelAngles(head, earsR);
        this.earsL.rotateAngleY = this.earsL.rotateAngleY + 0.15F;
        this.earsR.rotateAngleY = this.earsR.rotateAngleY - 0.15F;

        copyModelAngles(body, wool1);
        copyModelAngles(body, wool2);
        copyModelAngles(body, wool3);
        copyModelAngles(body, wool4);
        copyModelAngles(body, wool5);
        copyModelAngles(body, wool6);
        copyModelAngles(body, wool7);
        copyModelAngles(body, wool8);
        copyModelAngles(body, wool9);
        copyModelAngles(body, wool10);
        copyModelAngles(body, wool11);
        copyModelAngles(body, wool12);
        copyModelAngles(body, wool13);
        copyModelAngles(body, wool14);
        copyModelAngles(body, wool15);

        copyModelAngles(head, neckWool1);
        copyModelAngles(head, neckWool2);
        copyModelAngles(head, neckWool3);
        copyModelAngles(head, neckWool4);
        copyModelAngles(head, neckWool5);
        copyModelAngles(head, neckWool6);
        copyModelAngles(head, neckWool7);

        copyModelAngles(head, hornBase);
        copyModelAngles(head, polyHornBase);

        EnhancedSheep enhancedSheep = (EnhancedSheep) entityIn;
        int[] genes = enhancedSheep.getSharedGenes();
        char[] uuidArry = enhancedSheep.getCachedUniqueIdString().toCharArray();

        // values from: [ 0.1F to 0.4F]
        float hornSpacing = 0.4F;
//        float hornGrowth = (entityIn.ticksExisted/24000.0F);

            this.hornL9.rotationPointY = -0.4F;
            this.hornR9.rotationPointY = -0.4F;
            this.hornL8.rotationPointY = -0.5376F;
            this.hornR8.rotationPointY = -0.5376F;
            this.hornL7.rotationPointY = -0.675F;
            this.hornR7.rotationPointY = -0.675F;
            this.hornL6.rotationPointY = -0.7876F;
            this.hornR6.rotationPointY = -0.7876F;
            this.hornL5.rotationPointY = -0.9F;
            this.hornR5.rotationPointY = -0.9F;
            this.hornL4.rotationPointY = -1.1F;
            this.hornR4.rotationPointY = -1.1F;
            this.hornL3.rotationPointY = -1.4F;
            this.hornR3.rotationPointY = -1.4F;
            this.hornL2.rotationPointY = -1.7F;
            this.hornR2.rotationPointY = -1.7F;
            this.hornL1.rotationPointY = -1.9F;
            this.hornR1.rotationPointY = -1.9F;
            this.hornL09.rotationPointY = -2.0F;
            this.hornR09.rotationPointY = -2.0F;
//            this.hornL08.rotationPointY = -2.0F;
//            this.hornR08.rotationPointY = -2.0F;
//            this.hornL07.rotationPointY = -2.0F;
//            this.hornR07.rotationPointY = -2.0F;
//            this.hornL06.rotationPointY = -2.0F;
//            this.hornR06.rotationPointY = -2.0F;
//            this.hornL05.rotationPointY = -2.0F;
//            this.hornR05.rotationPointY = -2.0F;
//            this.hornL04.rotationPointY = -2.0F;
//            this.hornR04.rotationPointY = -2.0F;
//            this.hornL03.rotationPointY = -2.0F;
//            this.hornR03.rotationPointY = -2.0F;
//            this.hornL02.rotationPointY = -2.0F;
//            this.hornR02.rotationPointY = -2.0F;
//            this.hornL01.rotationPointY = -2.0F;
//            this.hornR01.rotationPointY = -2.0F;

            this.hornL0.rotationPointX = -hornSpacing;
            this.hornR0.rotationPointX = hornSpacing;

        //the curve overback
        // [ -0.degreesF * goldenRatio ];
//        float hornCurveX = -0.15F * 1.6180F;

        float polycerate = 1F;

        if (genes[36] == 1 || genes[37] == 1) {
            if (Character.isLetter(uuidArry[0]) || uuidArry[0] - 48 >= 8) {
                //horns if "male"
                if (Character.isLetter(uuidArry[2]) || uuidArry[2] - 48 >= 3) {
                    polycerate = 0.5F;
                }
            } else {
                if (uuidArry[2] - 48 <= 3) {
                    polycerate = 0.5F;
                }
            }
        }

        float hornRootAngleX = 0.25F;
        float hornRootAngleY = 0.25F;
        float hornRootAngleZ = 0.25F;
        //gradient should be 0 to 1
        float hornCurveGradientX = 0.50F * polycerate;
        float hornCurve = 0.20F * polycerate;
        float hornCurveX = -0.50F;
        float hornCurveY = 0.00F;
        float hornCurveZ = 0.00F;

//        this.polyHornL0.rotateAngleX = this.polyHornL0.rotateAngleX + 0.001F;
//        this.polyHornR0.rotateAngleX = this.polyHornR0.rotateAngleX - 0.001F;
        this.polyHornL0.rotateAngleX = -1.0F;
        this.polyHornR0.rotateAngleX = -1.0F;
        this.polyHornL0.rotateAngleY = 2.6F;
        this.polyHornR0.rotateAngleY = -2.6F;
        this.polyHornL0.rotateAngleZ = 1.6F;
        this.polyHornR0.rotateAngleZ = -1.6F;

        this.hornL0.rotateAngleX = (-hornCurve * ( 1.0F + (hornCurveGradientX * 1.5F))) - hornRootAngleX;
//        this.hornL01.rotateAngleX = -hornCurve * ( 1.0F + (hornCurveGradientX * 1.5F));
//        this.hornL02.rotateAngleX = -hornCurve * ( 1.0F + (hornCurveGradientX * 1.5F));
//        this.hornL03.rotateAngleX = -hornCurve * ( 1.0F + (hornCurveGradientX * 1.5F));
//        this.hornL04.rotateAngleX = -hornCurve * ( 1.0F + (hornCurveGradientX * 1.5F));
//        this.hornL05.rotateAngleX = -hornCurve * ( 1.0F + (hornCurveGradientX * 1.5F));
//        this.hornL06.rotateAngleX = -hornCurve * ( 1.0F + (hornCurveGradientX * 1.5F));
//        this.hornL07.rotateAngleX = -hornCurve * ( 1.0F + (hornCurveGradientX * 1.5F));
//        this.hornL08.rotateAngleX = -hornCurve * ( 1.0F + (hornCurveGradientX * 1.5F));
        this.hornL09.rotateAngleX = -hornCurve * ( 1.0F + (hornCurveGradientX * 1.5F));
        this.hornL1.rotateAngleX = -hornCurve * ( 1.0F + (hornCurveGradientX * 1.4F));
        this.hornL2.rotateAngleX = -hornCurve * ( 1.0F + (hornCurveGradientX * 1.3F));
        this.hornL3.rotateAngleX = -hornCurve * ( 1.0F + (hornCurveGradientX * 1.2F));
        this.hornL4.rotateAngleX = -hornCurve * ( 1.0F + (hornCurveGradientX * 1.1F));
        this.hornL5.rotateAngleX = -hornCurve * ( 1.0F + (hornCurveGradientX * 1.0F));
        this.hornL6.rotateAngleX = -hornCurve * ( 1.0F + (hornCurveGradientX * 0.9F));
        this.hornL7.rotateAngleX = -hornCurve * ( 1.0F + (hornCurveGradientX * 0.8F));
        this.hornL8.rotateAngleX = -hornCurve * ( 1.0F + (hornCurveGradientX * 0.7F));
        this.hornL9.rotateAngleX = -hornCurve * ( 1.0F + (hornCurveGradientX * 0.6F));

        this.hornR0.rotateAngleX = (-hornCurve * ( 1.0F + (hornCurveGradientX * 1.5F))) - hornRootAngleX;
//        this.hornR01.rotateAngleX = -hornCurve * ( 1.0F + (hornCurveGradientX * 1.5F));
//        this.hornR02.rotateAngleX = -hornCurve * ( 1.0F + (hornCurveGradientX * 1.5F));
//        this.hornR03.rotateAngleX = -hornCurve * ( 1.0F + (hornCurveGradientX * 1.5F));
//        this.hornR04.rotateAngleX = -hornCurve * ( 1.0F + (hornCurveGradientX * 1.5F));
//        this.hornR05.rotateAngleX = -hornCurve * ( 1.0F + (hornCurveGradientX * 1.5F));
//        this.hornR06.rotateAngleX = -hornCurve * ( 1.0F + (hornCurveGradientX * 1.5F));
//        this.hornR07.rotateAngleX = -hornCurve * ( 1.0F + (hornCurveGradientX * 1.5F));
//        this.hornR08.rotateAngleX = -hornCurve * ( 1.0F + (hornCurveGradientX * 1.5F));
        this.hornR09.rotateAngleX = -hornCurve * ( 1.0F + (hornCurveGradientX * 1.5F));
        this.hornR1.rotateAngleX = -hornCurve * ( 1.0F + (hornCurveGradientX * 1.4F));
        this.hornR2.rotateAngleX = -hornCurve * ( 1.0F + (hornCurveGradientX * 1.3F));
        this.hornR3.rotateAngleX = -hornCurve * ( 1.0F + (hornCurveGradientX * 1.2F));
        this.hornR4.rotateAngleX = -hornCurve * ( 1.0F + (hornCurveGradientX * 1.1F));
        this.hornR5.rotateAngleX = -hornCurve * ( 1.0F + (hornCurveGradientX * 1.0F));
        this.hornR6.rotateAngleX = -hornCurve * ( 1.0F + (hornCurveGradientX * 0.9F));
        this.hornR7.rotateAngleX = -hornCurve * ( 1.0F + (hornCurveGradientX * 0.8F));
        this.hornR8.rotateAngleX = -hornCurve * ( 1.0F + (hornCurveGradientX * 0.7F));
        this.hornR9.rotateAngleX = -hornCurve * ( 1.0F + (hornCurveGradientX * 0.6F));


        this.hornL0.rotateAngleY = hornCurve + hornRootAngleY;
//        this.hornL01.rotateAngleY = hornCurve;
//        this.hornL02.rotateAngleY = hornCurve;
//        this.hornL03.rotateAngleY = hornCurve;
//        this.hornL04.rotateAngleY = hornCurve;
//        this.hornL05.rotateAngleY = hornCurve;
//        this.hornL06.rotateAngleY = hornCurve;
//        this.hornL07.rotateAngleY = hornCurve;
//        this.hornL08.rotateAngleY = hornCurve;
        this.hornL09.rotateAngleY = hornCurve;
        this.hornL1.rotateAngleY = hornCurve;
        this.hornL2.rotateAngleY = hornCurve;
        this.hornL3.rotateAngleY = hornCurve;
        this.hornL4.rotateAngleY = hornCurve;
        this.hornL5.rotateAngleY = hornCurve;
        this.hornL6.rotateAngleY = hornCurve;
        this.hornL7.rotateAngleY = hornCurve;
        this.hornL8.rotateAngleY = hornCurve;
        this.hornL9.rotateAngleY = hornCurve;

        this.hornR0.rotateAngleY = -hornCurve - hornRootAngleY;
//        this.hornR01.rotateAngleY = -hornCurve;
//        this.hornR02.rotateAngleY = -hornCurve;
//        this.hornR03.rotateAngleY = -hornCurve;
//        this.hornR04.rotateAngleY = -hornCurve;
//        this.hornR05.rotateAngleY = -hornCurve;
//        this.hornR06.rotateAngleY = -hornCurve;
//        this.hornR07.rotateAngleY = -hornCurve;
//        this.hornR08.rotateAngleY = -hornCurve;
        this.hornR09.rotateAngleY = -hornCurve;
        this.hornR1.rotateAngleY = -hornCurve;
        this.hornR2.rotateAngleY = -hornCurve;
        this.hornR3.rotateAngleY = -hornCurve;
        this.hornR4.rotateAngleY = -hornCurve;
        this.hornR5.rotateAngleY = -hornCurve;
        this.hornR6.rotateAngleY = -hornCurve;
        this.hornR7.rotateAngleY = -hornCurve;
        this.hornR8.rotateAngleY = -hornCurve;
        this.hornR9.rotateAngleY = -hornCurve;


        this.hornL0.rotateAngleZ = hornCurve + hornRootAngleZ;
//        this.hornL01.rotateAngleZ = hornCurve;
//        this.hornL02.rotateAngleZ = hornCurve;
//        this.hornL03.rotateAngleZ = hornCurve;
//        this.hornL04.rotateAngleZ = hornCurve;
//        this.hornL05.rotateAngleZ = hornCurve;
//        this.hornL06.rotateAngleZ = hornCurve;
//        this.hornL07.rotateAngleZ = hornCurve;
//        this.hornL08.rotateAngleZ = hornCurve;
        this.hornL09.rotateAngleZ = hornCurve;
        this.hornL1.rotateAngleZ = hornCurve;
        this.hornL2.rotateAngleZ = hornCurve;
        this.hornL3.rotateAngleZ = hornCurve;
        this.hornL4.rotateAngleZ = hornCurve;
        this.hornL5.rotateAngleZ = hornCurve;
        this.hornL6.rotateAngleZ = hornCurve;
        this.hornL7.rotateAngleZ = hornCurve;
        this.hornL8.rotateAngleZ = hornCurve;
        this.hornL9.rotateAngleZ = hornCurve;

        this.hornR0.rotateAngleZ = -hornCurve - hornRootAngleZ;
//        this.hornR01.rotateAngleZ = -hornCurve;
//        this.hornR02.rotateAngleZ = -hornCurve;
//        this.hornR03.rotateAngleZ = -hornCurve;
//        this.hornR04.rotateAngleZ = -hornCurve;
//        this.hornR05.rotateAngleZ = -hornCurve;
//        this.hornR06.rotateAngleZ = -hornCurve;
//        this.hornR07.rotateAngleZ = -hornCurve;
//        this.hornR08.rotateAngleZ = -hornCurve;
        this.hornR09.rotateAngleZ = -hornCurve;
        this.hornR1.rotateAngleZ = -hornCurve;
        this.hornR2.rotateAngleZ = -hornCurve;
        this.hornR3.rotateAngleZ = -hornCurve;
        this.hornR4.rotateAngleZ = -hornCurve;
        this.hornR5.rotateAngleZ = -hornCurve;
        this.hornR6.rotateAngleZ = -hornCurve;
        this.hornR7.rotateAngleZ = -hornCurve;
        this.hornR8.rotateAngleZ = -hornCurve;
        this.hornR9.rotateAngleZ = -hornCurve;



    }

    /**
     * Used for easily adding entity-dependent animations. The second and third float params here are the same second
     * and third as in the setRotationAngles method.
     */
    @Override
    public void setLivingAnimations(T entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTickTime)
    {
        super.setLivingAnimations(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTickTime);
        this.head.rotationPointY = 9.0F + ((EnhancedSheep)entitylivingbaseIn).getHeadRotationPointY(partialTickTime) * 9.0F;
        this.headRotationAngleX = ((EnhancedSheep)entitylivingbaseIn).getHeadRotationAngleX(partialTickTime);


    }

    public static void copyModelAngles(RendererModel source, RendererModel dest) {
        dest.rotateAngleX = source.rotateAngleX;
        dest.rotateAngleY = source.rotateAngleY;
        dest.rotateAngleZ = source.rotateAngleZ;
        dest.rotationPointX = source.rotationPointX;
        dest.rotationPointY = source.rotationPointY;
        dest.rotationPointZ = source.rotationPointZ;
    }
}
