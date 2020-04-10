package mokiyoki.enhancedanimals.model;

import com.mojang.blaze3d.platform.GlStateManager;
import mokiyoki.enhancedanimals.entity.EnhancedSheep;
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
public class ModelEnhancedSheep  <T extends EnhancedSheep> extends EntityModel<T> {

    private Map<Integer, SheepModelData> sheepModelDataCache = new HashMap<>();
    private int clearCacheTimer = 0;

    private float headRotationAngleX;
    private float f12 = 0F;

    private final RendererModel head;
    private final RendererModel neck;
    private final EnhancedRendererModel hornBase;
    private final EnhancedRendererModel polyHornBase;
    private final EnhancedRendererModel polyHornL0;
    private final EnhancedRendererModel hornL0;
    private final EnhancedRendererModel hornL01;
    private final EnhancedRendererModel hornL02;
    private final EnhancedRendererModel hornL03;
    private final EnhancedRendererModel hornL04;
    private final EnhancedRendererModel hornL05;
    private final EnhancedRendererModel hornL06;
    private final EnhancedRendererModel hornL07;
    private final EnhancedRendererModel hornL08;
    private final EnhancedRendererModel hornL09;
    private final EnhancedRendererModel hornL1;
    private final EnhancedRendererModel hornL2;
    private final EnhancedRendererModel hornL3;
    private final EnhancedRendererModel hornL4;
    private final EnhancedRendererModel hornL5;
    private final EnhancedRendererModel hornL6;
    private final EnhancedRendererModel hornL7;
    private final EnhancedRendererModel hornL8;
    private final EnhancedRendererModel hornL9;
    private final EnhancedRendererModel polyHornR0;
    private final EnhancedRendererModel hornR0;
    private final EnhancedRendererModel hornR01;
    private final EnhancedRendererModel hornR02;
    private final EnhancedRendererModel hornR03;
    private final EnhancedRendererModel hornR04;
    private final EnhancedRendererModel hornR05;
    private final EnhancedRendererModel hornR06;
    private final EnhancedRendererModel hornR07;
    private final EnhancedRendererModel hornR08;
    private final EnhancedRendererModel hornR09;
    private final EnhancedRendererModel hornR1;
    private final EnhancedRendererModel hornR2;
    private final EnhancedRendererModel hornR3;
    private final EnhancedRendererModel hornR4;
    private final EnhancedRendererModel hornR5;
    private final EnhancedRendererModel hornR6;
    private final EnhancedRendererModel hornR7;
    private final EnhancedRendererModel hornR8;
    private final EnhancedRendererModel hornR9;
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
    private final RendererModel headWool1;
    private final RendererModel headWool1Child;
//    private final RendererModel headWool3;
//    private final RendererModel headWool4;
//    private final RendererModel headWool5;
//    private final RendererModel headWool6;
//    private final RendererModel headWool7;
    private final RendererModel cheekWool1;
    private final RendererModel cheekWool1Child;
    private final RendererModel noseWool1;
    private final RendererModel noseWool1Child;
//    private final RendererModel faceWool2;
//    private final RendererModel faceWool3;
//    private final RendererModel faceWool4;
//    private final RendererModel faceWool5;
//    private final RendererModel faceWool6;
//    private final RendererModel faceWool7;
    private final RendererModel udder;
    private final RendererModel nippleL;
    private final RendererModel nippleR;
    private final RendererModel tailBase;
    private final RendererModel tailMiddle;
    private final RendererModel tailTip;
    public final RendererModel leg1;
    public final RendererModel leg2;
    public final RendererModel leg3;
    public final RendererModel leg4;

    private final List<RendererModel> sheepLeftHorns = new ArrayList<>();
    private final List<RendererModel> sheepRightHorns = new ArrayList<>();

    public ModelEnhancedSheep()
    {
        this.textureWidth = 64;
        this.textureHeight = 64;

        float xMove = -6.0F;

        this.head = new RendererModel(this, 0, 0);
        this.head.addBox(-2.5F, 0.0F, -5.0F, 5, 4, 4, 0.0F); //skull
        this.head.setTextureOffset(0, 8);
        this.head.addBox(-2.0F, 0.0F, -8.0F, 4, 3, 3, 0.0F); //nose
        this.head.setRotationPoint(0.0F, -8.0F, 0.0F);

        this.neck = new RendererModel(this, 34, 0);
        this.neck.addBox(-2.0F, -7.0F, -4.0F, 4, 9, 4, 0.0F); //neck
        this.neck.setRotationPoint(0.0F, 0.0F, -6.0F);

        this.hornBase = new EnhancedRendererModel(this, 0, 36);
        this.hornBase.addBox(-1.5F, -8.9F, -4.1F, 3, 3, 3, -1.0F);
        this.hornBase.setRotationPoint(0.0F, 0.0F, -6.0F);

        this.polyHornBase = new EnhancedRendererModel(this, 0, 36);
        this.polyHornBase.addBox(-1.5F, -8.9F, -4.1F, 3, 3, 3, -1.0F);
        this.polyHornBase.setRotationPoint(0.0F, 0.0F, -6.0F);

        this.polyHornL0 = new EnhancedRendererModel(this, 0, 36);
        this.polyHornL0.addBox(0.0F, -3.0F, -1.5F, 3, 3, 3, -0.6F);
        this.polyHornL0.setRotationPoint(3.0F, -5.0F, -1.0F);

        this.polyHornR0 = new EnhancedRendererModel(this, 0, 36);
        this.polyHornR0.addBox(-3.0F, -3.0F, -1.5F, 3, 3, 3, -0.6F);
        this.polyHornR0.setRotationPoint(-3.0F, -5.0F, -1.0F);

        // scale down by 0.1 for each
        this.hornL0 = new EnhancedRendererModel(this, 0, 36, "HornL0");
        this.hornL0.addBox(0.0F, -3.0F, -1.5F, 3, 3, 3, -0.5F);
        this.hornL0.setRotationPoint(0.0F, -7.4F, -3.0F);
        sheepLeftHorns.add(hornL0);

        this.hornL01 = new EnhancedRendererModel(this, 0, 36, "HornL01");
        this.hornL01.addBox(0.0F, -3.0F, -1.5F, 3, 3, 3, -0.5F);
        this.hornL01.setRotationPoint(0.0F, 0.0F, 0.0F);
        sheepLeftHorns.add(hornL01);

        this.hornL02 = new EnhancedRendererModel(this, 0, 36, "HornL02");
        this.hornL02.addBox(0.0F, -3.0F, -1.5F, 3, 3, 3, -0.5F);
        this.hornL02.setRotationPoint(0.0F, 0.0F, 0.0F);
        sheepLeftHorns.add(hornL02);

        this.hornL03 = new EnhancedRendererModel(this, 0, 36, "HornL03");
        this.hornL03.addBox(0.0F, -3.0F, -1.5F, 3, 3, 3, -0.5F);
        this.hornL03.setRotationPoint(0.0F, 0.0F, 0.0F);
        sheepLeftHorns.add(hornL03);

        this.hornL04 = new EnhancedRendererModel(this, 0, 36, "HornL04");
        this.hornL04.addBox(0.0F, -3.0F, -1.5F, 3, 3, 3, -0.5F);
        this.hornL04.setRotationPoint(0.0F, 0.0F, 0.0F);
        sheepLeftHorns.add(hornL04);

        this.hornL05 = new EnhancedRendererModel(this, 0, 36, "HornL05");
        this.hornL05.addBox(0.0F, -3.0F, -1.5F, 3, 3, 3, -0.5F);
        this.hornL05.setRotationPoint(0.0F, 0.0F, 0.0F);
        sheepLeftHorns.add(hornL05);

        this.hornL06 = new EnhancedRendererModel(this, 0, 36, "HornL06");
        this.hornL06.addBox(0.0F, -3.0F, -1.5F, 3, 3, 3, -0.5F);
        this.hornL06.setRotationPoint(0.0F, 0.0F, 0.0F);
        sheepLeftHorns.add(hornL06);

        this.hornL07 = new EnhancedRendererModel(this, 0, 36, "HornL07");
        this.hornL07.addBox(0.0F, -3.0F, -1.5F, 3, 3, 3, -0.5F);
        this.hornL07.setRotationPoint(0.0F, 0.0F, 0.0F);
        sheepLeftHorns.add(hornL07);

        this.hornL08 = new EnhancedRendererModel(this, 0, 36, "HornL08");
        this.hornL08.addBox(0.0F, -3.0F, -1.5F, 3, 3, 3, -0.5F);
        this.hornL08.setRotationPoint(0.0F, 0.0F, 0.0F);
        sheepLeftHorns.add(hornL08);

        this.hornL09 = new EnhancedRendererModel(this, 0, 36, "HornL09");
        this.hornL09.addBox(0.0F, -3.0F, -1.5F, 3, 3, 3, -0.5F);
        this.hornL09.setRotationPoint(0.0F, 0.0F, 0.0F);
        sheepLeftHorns.add(hornL09);

        this.hornL1 = new EnhancedRendererModel(this, 0, 36, "HornL1");
        this.hornL1.addBox(0.0F, -3.0F, -1.5F, 3, 3, 3, -0.6F);
        this.hornL1.setRotationPoint(0.0F, 0.0F, 0.0F);
        sheepLeftHorns.add(hornL1);

        this.hornL2 = new EnhancedRendererModel(this, 0, 36, "HornL2");
        this.hornL2.addBox(0.0F, -3.0F, -1.5F, 3, 3, 3, -0.7F);
        this.hornL2.setRotationPoint(0.0F, 0.0F, 0.0F);
        sheepLeftHorns.add(hornL2);

        this.hornL3 = new EnhancedRendererModel(this, 0, 36, "HornL3");
        this.hornL3.addBox(0.0F, -3.0F, -1.5F, 3, 3, 3, -0.8F);
        this.hornL3.setRotationPoint(0.0F, 0.0F, 0.0F);
        sheepLeftHorns.add(hornL3);

        this.hornL4 = new EnhancedRendererModel(this, 0, 36, "HornL4");
        this.hornL4.addBox(0.0F, -3.0F, -1.5F, 3, 3, 3, -0.9F);
        this.hornL4.setRotationPoint(0.0F, 0.0F, 0.0F);
        sheepLeftHorns.add(hornL4);

        this.hornL5 = new EnhancedRendererModel(this, 0, 36, "HornL5");
        this.hornL5.addBox(0.0F, -3.0F, -1.5F, 3, 3, 3, -1.0F);
        this.hornL5.setRotationPoint(0.0F, 0.0F, 0.0F);
        sheepLeftHorns.add(hornL5);

        this.hornL6 = new EnhancedRendererModel(this, 0, 36, "HornL6");
        this.hornL6.addBox(0.0F, -3.0F, -1.5F, 3, 3, 3, -1.0562F);
        this.hornL6.setRotationPoint(0.0F, 0.0F, 0.0F);
        sheepLeftHorns.add(hornL6);

        this.hornL7 = new EnhancedRendererModel(this, 0, 36, "HornL7");
        this.hornL7.addBox(0.0F, -3.0F, -1.5F, 3, 3, 3, -1.1125F);
        this.hornL7.setRotationPoint(0.0F, 0.0F, 0.0F);
        sheepLeftHorns.add(hornL7);

        this.hornL8 = new EnhancedRendererModel(this, 0, 36, "HornL8");
        this.hornL8.addBox(0.0F, -3.0F, -1.5F, 3, 3, 3, -1.1812F);
        this.hornL8.setRotationPoint(0.0F, 0.0F, 0.0F);
        sheepLeftHorns.add(hornL8);

        this.hornL9 = new EnhancedRendererModel(this, 0, 36, "HornL9");
        this.hornL9.addBox(0.0F, -3.0F, -1.5F, 3, 3, 3, -1.25F);
        this.hornL9.setRotationPoint(0.0F, 0.0F, 0.0F);
        sheepLeftHorns.add(hornL9);

        this.hornR0 = new EnhancedRendererModel(this, 0, 36);
        this.hornR0.addBox(-3.0F, -3.0F, -1.5F, 3, 3, 3, -0.5F);
        this.hornR0.setRotationPoint(0.0F, -7.4F, -3.0F);
        sheepRightHorns.add(hornR0);

        this.hornR01 = new EnhancedRendererModel(this, 0, 36);
        this.hornR01.addBox(-3.0F, -3.0F, -1.5F, 3, 3, 3, -0.5F);
        this.hornR01.setRotationPoint(0.0F, 0.0F, 0.0F);
        sheepRightHorns.add(hornR01);

        this.hornR02 = new EnhancedRendererModel(this, 0, 36);
        this.hornR02.addBox(-3.0F, -3.0F, -1.5F, 3, 3, 3, -0.5F);
        this.hornR02.setRotationPoint(0.0F, 0.0F, 0.0F);
        sheepRightHorns.add(hornR02);

        this.hornR03 = new EnhancedRendererModel(this, 0, 36);
        this.hornR03.addBox(-3.0F, -3.0F, -1.5F, 3, 3, 3, -0.5F);
        this.hornR03.setRotationPoint(0.0F, 0.0F, 0.0F);
        sheepRightHorns.add(hornR03);

        this.hornR04 = new EnhancedRendererModel(this, 0, 36);
        this.hornR04.addBox(-3.0F, -3.0F, -1.5F, 3, 3, 3, -0.5F);
        this.hornR04.setRotationPoint(0.0F, 0.0F, 0.0F);
        sheepRightHorns.add(hornR04);

        this.hornR05 = new EnhancedRendererModel(this, 0, 36);
        this.hornR05.addBox(-3.0F, -3.0F, -1.5F, 3, 3, 3, -0.5F);
        this.hornR05.setRotationPoint(0.0F, 0.0F, 0.0F);
        sheepRightHorns.add(hornR05);

        this.hornR06 = new EnhancedRendererModel(this, 0, 36);
        this.hornR06.addBox(-3.0F, -3.0F, -1.5F, 3, 3, 3, -0.5F);
        this.hornR06.setRotationPoint(0.0F, 0.0F, 0.0F);
        sheepRightHorns.add(hornR06);

        this.hornR07 = new EnhancedRendererModel(this, 0, 36);
        this.hornR07.addBox(-3.0F, -3.0F, -1.5F, 3, 3, 3, -0.5F);
        this.hornR07.setRotationPoint(0.0F, 0.0F, 0.0F);
        sheepRightHorns.add(hornR07);

        this.hornR08 = new EnhancedRendererModel(this, 0, 36);
        this.hornR08.addBox(-3.0F, -3.0F, -1.5F, 3, 3, 3, -0.5F);
        this.hornR08.setRotationPoint(0.0F, 0.0F, 0.0F);
        sheepRightHorns.add(hornR08);

        this.hornR09 = new EnhancedRendererModel(this, 0, 36);
        this.hornR09.addBox(-3.0F, -3.0F, -1.5F, 3, 3, 3, -0.5F);
        this.hornR09.setRotationPoint(0.0F, 0.0F, 0.0F);
        sheepRightHorns.add(hornR09);

        this.hornR1 = new EnhancedRendererModel(this, 0, 36);
        this.hornR1.addBox(-3.0F, -3.0F, -1.5F, 3, 3, 3, -0.6F);
        this.hornR1.setRotationPoint(0.0F, 0.0F, 0.0F);
        sheepRightHorns.add(hornR1);

        this.hornR2 = new EnhancedRendererModel(this, 0, 36);
        this.hornR2.addBox(-3.0F, -3.0F, -1.5F, 3, 3, 3, -0.7F);
        this.hornR2.setRotationPoint(0.0F, 0.0F, 0.0F);
        sheepRightHorns.add(hornR2);

        this.hornR3 = new EnhancedRendererModel(this, 0, 36);
        this.hornR3.addBox(-3.0F, -3.0F, -1.5F, 3, 3, 3, -0.8F);
        this.hornR3.setRotationPoint(0.0F, 0.0F, 0.0F);
        sheepRightHorns.add(hornR3);

        this.hornR4 = new EnhancedRendererModel(this, 0, 36);
        this.hornR4.addBox(-3.0F, -3.0F, -1.5F, 3, 3, 3, -0.9F);
        this.hornR4.setRotationPoint(0.0F, 0.0F, 0.0F);
        sheepRightHorns.add(hornR4);

        this.hornR5 = new EnhancedRendererModel(this, 0, 36);
        this.hornR5.addBox(-3.0F, -3.0F, -1.5F, 3, 3, 3, -1.0F);
        this.hornR5.setRotationPoint(0.0F, 0.0F, 0.0F);
        sheepRightHorns.add(hornR5);

        this.hornR6 = new EnhancedRendererModel(this, 0, 36);
        this.hornR6.addBox(-3.0F, -3.0F, -1.5F, 3, 3, 3, -1.0562F);
        this.hornR6.setRotationPoint(0.0F, 0.0F, 0.0F);
        sheepRightHorns.add(hornR6);

        this.hornR7 = new EnhancedRendererModel(this, 0, 36);
        this.hornR7.addBox(-3.0F, -3.0F, -1.5F, 3, 3, 3, -1.1125F);
        this.hornR7.setRotationPoint(0.0F, 0.0F, 0.0F);
        sheepRightHorns.add(hornR7);

        this.hornR8 = new EnhancedRendererModel(this, 0, 36);
        this.hornR8.addBox(-3.0F, -3.0F, -1.5F, 3, 3, 3, -1.1812F);
        this.hornR8.setRotationPoint(0.0F, 0.0F, 0.0F);
        sheepRightHorns.add(hornR8);

        this.hornR9 = new EnhancedRendererModel(this, 0, 36);
        this.hornR9.addBox(-3.0F, -3.0F, -1.5F, 3, 3, 3, -1.25F);
        this.hornR9.setRotationPoint(0.0F, 0.0F, 0.0F);
        sheepRightHorns.add(hornR9);

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

        this.headWool1 = new RendererModel(this,0,0);
        this.headWool1.setRotationPoint(0.0F, 0.0F, -6.0F);
        this.headWool1Child = new RendererModel(this, 0, 55);
        this.headWool1Child.addBox(-2.5F, 0.0F, 0.0F, 5, 3, 4, 0.4F); //neck fluff
        this.headWool1Child.setRotationPoint(0.0F, -4.0F, -3.0F);

        this.cheekWool1 = new RendererModel(this,0,0);
        this.cheekWool1.setRotationPoint(0.0F, 0.0F, -6.0F);
        this.cheekWool1Child = new RendererModel(this, 14, 47);
        this.cheekWool1Child.addBox(-2.5F, 0.0F, 0.0F, 5, 2, 2, 0.5F); //cheek fluff
        this.cheekWool1Child.setRotationPoint(0.0F, -3.5F, -6.0F);

        this.noseWool1 = new RendererModel(this,0,0);
        this.noseWool1.setRotationPoint(0.0F, 0.0F, -6.0F);
        this.noseWool1Child = new RendererModel(this, 0, 47);
        this.noseWool1Child.addBox(-2.0F, 0.0F, 0.0F, 4, 2, 3, 0.4F); //cheek fluff
        this.noseWool1Child.setRotationPoint(0.0F, -5.0F, -7.0F);

        this.udder = new RendererModel(this, 46, 55);
        this.udder.addBox(-2.0F, 0.0F, 0.0F, 4, 4, 5);
        this.nippleL = new RendererModel(this, 46, 55);
        this.nippleL.addBox(-0.5F, 0.0F, -0.5F, 1, 3, 1);
        this.nippleR = new RendererModel(this, 60, 55);
        this.nippleR.addBox(-0.5F, 0.0F, -0.5F, 1, 3, 1);
        this.udder.setRotationPoint(0.0F, 12.0F, 3.25F);
        this.nippleL.setRotationPoint(-1.0F, 2.5F, 1.0F);
        this.nippleR.setRotationPoint(1.0F, 2.5F, 1.0F);

        this.tailBase = new RendererModel(this, 50, 6);
        this.tailBase.addBox(-1.0F, 0.0F, 0.0F, 2, 3, 1);
        this.tailBase.setRotationPoint(0.0F, 9.0F, 8.0F);

        this.tailMiddle = new RendererModel(this, 56, 6);
        this.tailMiddle.addBox(-0.5F, 0.0F, 0.0F, 1, 3, 1);
        this.tailMiddle.setRotationPoint(0.0F, 3.0F, 0.0F);

        this.tailTip = new RendererModel(this, 60, 6);
        this.tailTip.addBox(-0.5F, 0.0F, 0.0F, 1, 3, 1);
        this.tailTip.setRotationPoint(0.0F, 3.0F, 0.0F);

        this.leg1 = new RendererModel(this, 0, 22);
        this.leg1.addBox(0.0F, 0.0F, 0.0F, 3, 10, 3);
        this.leg1.setRotationPoint(-4.0F, 14.0F,-8.0F);
        this.leg2 = new RendererModel(this, 12, 22);
        this.leg2.addBox(0.0F, 0.0F, 0.0F, 3, 10, 3);
        this.leg2.setRotationPoint(1.0F, 14.0F,-8.0F);
        this.leg3 = new RendererModel(this, 24, 22);
        this.leg3.addBox(0.0F, 0.0F, 0.0F, 3, 10, 3);
        this.leg3.setRotationPoint(-4.0F, 14.0F,7.0F);
        this.leg4 = new RendererModel(this, 36, 22);
        this.leg4.addBox(0.0F, 0.0F, 0.0F, 3, 10, 3);
        this.leg4.setRotationPoint(1.0F, 14.0F,7.0F);

        this.tailBase.addChild(tailMiddle);
        this.tailMiddle.addChild(tailTip);

        this.hornBase.addChild(hornL0);
        this.hornL0.addChild(hornL01);
        this.hornL01.addChild(hornL02);
        this.hornL02.addChild(hornL03);
        this.hornL03.addChild(hornL04);
        this.hornL04.addChild(hornL05);
        this.hornL05.addChild(hornL06);
        this.hornL06.addChild(hornL07);
        this.hornL07.addChild(hornL08);
        this.hornL08.addChild(hornL09);
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
        this.hornR0.addChild(hornR01);
        this.hornR01.addChild(hornR02);
        this.hornR02.addChild(hornR03);
        this.hornR03.addChild(hornR04);
        this.hornR04.addChild(hornR05);
        this.hornR05.addChild(hornR06);
        this.hornR06.addChild(hornR07);
        this.hornR07.addChild(hornR08);
        this.hornR08.addChild(hornR09);
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

        this.neck.addChild(head);

        this.headWool1.addChild(headWool1Child);
        this.cheekWool1.addChild(cheekWool1Child);
        this.noseWool1.addChild(noseWool1Child);

        this.udder.addChild(nippleL);
        this.udder.addChild(nippleR);

    }

    @Override
    public void render(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        SheepModelData sheepModelData = getSheepModelData(entityIn);

        int[] genes = sheepModelData.sheepGenes;
        int coatLength = sheepModelData.coatlength;
        String sheepStatus = sheepModelData.sheepStatus;
        char[] uuidArry = sheepModelData.uuidArray;
        boolean sleeping = sheepModelData.sleeping;
        float size = 1.0F;

        List<String> unrenderedModels = this.setRotationAngles(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, genes, uuidArry, sleeping);


        boolean horns = false;
        boolean scurs = false;
        boolean polycerate = false;
        int facewool = 0;

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

        if (genes[42] == 1 || genes[43] == 1) {
            if (genes[40] == 1) {
                facewool++;
            }
            if (genes[41] == 1) {
                facewool++;
            }
            if (genes[38] == 1 || genes[39] == 1) {
                facewool++;
            } else if (genes[38] == 3 && genes[39] == 3) {
                facewool--;
            }
        }

        float hornScale = 1.0F;

        //TODO horn scale logic

        float age = 1.0F;
        if (!(sheepModelData.birthTime == null) && !sheepModelData.birthTime.equals("") && !sheepModelData.birthTime.equals("0")) {
            int ageTime = (int)(((WorldInfo)((ClientWorld)entityIn.world).getWorldInfo()).getGameTime() - Long.parseLong(sheepModelData.birthTime));
            if (ageTime <= 75000) {
                age = ageTime/75000.0F;
            }
        }

        float finalSheepSize = (( 2.0F * size * age) + size) / 3.0F;
        float babyScale = 1.0F;
        float d = 0.0F;
        if (!sleeping) {
            babyScale = (3.0F - age)/2;
            d = 0.3F * (1.0F-age);
        }

        GlStateManager.pushMatrix();
        GlStateManager.scalef(finalSheepSize, finalSheepSize, finalSheepSize);
        GlStateManager.translatef(0.0F, (-1.5F + 1.5F/finalSheepSize) - d, 0.0F);

            renderAdult(scale, sheepStatus, horns, hornScale, facewool, polycerate, unrenderedModels, coatLength);

        GlStateManager.popMatrix();

        GlStateManager.pushMatrix();
        GlStateManager.scalef(finalSheepSize, finalSheepSize * babyScale, finalSheepSize);
        GlStateManager.translatef(0.0F, -1.5F + 1.5F/(finalSheepSize * babyScale), 0.0F);

            renderLegs(scale);

        GlStateManager.popMatrix();
//        }
    }

    private void renderAdult(float scale, String sheepStatus, boolean horns, float hornScale, int facewool, boolean polycerate, List<String> unrenderedModels, int coatLength) {
        this.neck.render(scale);
        this.earsR.render(scale);
        this.earsL.render(scale);
        this.body.render(scale);

        renderWool(scale, facewool, coatLength);

        if (horns){
            renderHorns(scale, polycerate, hornScale, unrenderedModels);
        }

        float woolLength = ((coatLength-1)*0.025F) + 1.0F;

//            GlStateManager.pushMatrix();
//            GlStateManager.scalef(woolLength, woolLength, woolLength);
//            GlStateManager.translatef(0.0F, -1.5F + 1.5F/(woolLength/1.5F), 0.0F);
//            this.body.render(scale);
//            GlStateManager.popMatrix();

        GlStateManager.pushMatrix();
        GlStateManager.scalef(woolLength, 1.0F, woolLength);
        GlStateManager.translatef(0.0F, 0.0F, 0.0F);
        this.tailBase.render(scale);
        GlStateManager.popMatrix();

        if (sheepStatus.equals(EntityState.PREGNANT.toString()) || sheepStatus.equals(EntityState.MOTHER.toString())) {
//            if (true) {

//                GlStateManager.pushMatrix();
//                GlStateManager.scalef(0.5F, 0.7F, 0.5F);
//                GlStateManager.translatef(0.0F, 10.0F * scale, 0.0F);

            this.udder.render(scale);

//                GlStateManager.popMatrix();
        }
    }

    private void renderLegs(float scale) {
        this.leg1.render(scale);
        this.leg2.render(scale);
        this.leg3.render(scale);
        this.leg4.render(scale);
    }

    private void renderHorns(float scale, boolean polycerate, float hornScale, List<String> unrenderedModels) {

//        this.hornBase.render(scale, null, unrenderedModels, false);
        if (polycerate) {
            this.polyHornBase.render(scale,null, unrenderedModels, false);
        }

        Map<String, List<Float>> mapOfScale = new HashMap<>();

        List<Float> scalingsForHorn = createScalings(hornScale, 0.0F, 0.0F, 0.0F);
        mapOfScale.put("HornL0", scalingsForHorn);
        mapOfScale.put("HornR0", mirrorX(scalingsForHorn, false));
        this.hornBase.render(scale, mapOfScale, unrenderedModels, false);
    }

    private List<Float> mirrorX(List<Float> scalings, boolean includeScaling) {
        List<Float> reversedNegative = new ArrayList<>();

        if (includeScaling) {
            reversedNegative.add(scalings.get(0));
        } else {
            reversedNegative.add(null);
        }
        reversedNegative.add(scalings.get(1) * -2.0F);
        reversedNegative.add(0F);
        reversedNegative.add(0F);

        return reversedNegative;
    }

    private void renderWool(float scale, int facewool, int coatLength)  {
        if (coatLength == 1){
            this.wool1.render(scale);
            if (true) {
                this.neckWool1.render(scale);
            }
        }else if (coatLength == 2){
            this.wool2.render(scale);
            if (true) {
                this.neckWool1.render(scale);
            }
        }else if (coatLength == 3){
            this.wool3.render(scale);
            if (true) {
                this.neckWool2.render(scale);
            }
        }else if (coatLength == 4){
            this.wool4.render(scale);
            if (true) {
                this.neckWool2.render(scale);
            }
        }else if (coatLength == 5){
            this.wool5.render(scale);
            if (true) {
                this.neckWool3.render(scale);
            }
        }else if (coatLength == 6){
            this.wool6.render(scale);
            if (true) {
                this.neckWool3.render(scale);
            }
        }else if (coatLength == 7){
            this.wool7.render(scale);
            if (true) {
                this.neckWool4.render(scale);
            }
        }else if (coatLength == 8){
            this.wool8.render(scale);
            if (true) {
                this.neckWool4.render(scale);
            }
        }else if (coatLength == 9){
            this.wool9.render(scale);
            if (true) {
                this.neckWool5.render(scale);
            }
        }else if (coatLength == 10){
            this.wool10.render(scale);
            if (true) {
                this.neckWool5.render(scale);
            }
        }else if (coatLength == 11){
            this.wool11.render(scale);
            if (true) {
                this.neckWool6.render(scale);
            }
        }else if (coatLength == 12){
            this.wool12.render(scale);
            if (true) {
                this.neckWool6.render(scale);
            }
        }else if (coatLength == 13){
            this.wool13.render(scale);
            if (true) {
                this.neckWool7.render(scale);
            }
        }else if (coatLength == 14){
            this.wool14.render(scale);
            if (true) {
                this.neckWool7.render(scale);
            }
        }else if (coatLength == 15){
            this.wool15.render(scale);
            if (true) {
                this.neckWool7.render(scale);
            }
        }

        if (facewool >= 1) {
            this.headWool1.render(scale);
            if (facewool >= 2) {
                this.cheekWool1.render(scale);
                if (facewool == 3) {
                    this.noseWool1.render(scale);
                }
            }
        }
    }

    /**
     * Sets the model's various rotation angles. For bipeds, par1 and par2 are used for animating the movement of arms
     * and legs, where par1 represents the time(so that arms and legs swing back and forth) and par2 represents how
     * "far" arms and legs can swing at most.
     */

    public List<String> setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, int[] sharedGenes, char[] uuidArry, boolean sleeping) {
        super.setRotationAngles(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
        SheepModelData sheepModelData = getSheepModelData(entityIn);
        List<String> unrenderedModels = new ArrayList<>();

        float age = 1.0F;
        if (!(sheepModelData.birthTime == null) && !sheepModelData.birthTime.equals("") && !sheepModelData.birthTime.equals("0")) {
            int ageTime = (int)(((WorldInfo)((ClientWorld)entityIn.world).getWorldInfo()).getGameTime() - Long.parseLong(sheepModelData.birthTime));
            if (ageTime <= 75000) {
                age = ageTime/75000.0F;
            }
        }

        if (!sleeping) {
            this.neck.rotateAngleX = headPitch * 0.017453292F;
            this.neck.rotateAngleY = netHeadYaw * 0.017453292F;
        }

        if (!sleeping) {
            this.leg1.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
            this.leg2.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
            this.leg3.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
            this.leg4.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        }

        this.tailBase.rotateAngleZ = MathHelper.cos(limbSwing * 0.6662F) * (1.3F - (0.9F * age)) * limbSwingAmount;
        this.tailMiddle.rotateAngleZ = MathHelper.cos(limbSwing * 0.6662F) * (1.4F - (0.9F * age)) * limbSwingAmount;
        this.tailTip.rotateAngleZ = MathHelper.cos(limbSwing * 0.6662F) * (1.5F - (0.9F * age)) * limbSwingAmount;


        this.neck.rotateAngleX = 1F + this.headRotationAngleX;   //might need to merge this with another line

        copyModelAngles(neck, earsL);
        copyModelAngles(neck, earsR);
        this.earsL.rotateAngleY = this.earsL.rotateAngleY + 0.15F;
        this.earsR.rotateAngleY = this.earsR.rotateAngleY - 0.15F;
        copyModelAngles(body, wool1);
            this.wool1.rotateAngleZ = MathHelper.cos(limbSwing * 0.6662F) * 0.0133F * limbSwingAmount;
        copyModelAngles(body, wool2);
            this.wool2.rotateAngleZ = MathHelper.cos(limbSwing * 0.6662F) * 0.0266F * limbSwingAmount;
        copyModelAngles(body, wool3);
            this.wool3.rotateAngleZ = MathHelper.cos(limbSwing * 0.6662F) * 0.0399F * limbSwingAmount;
        copyModelAngles(body, wool4);
            this.wool4.rotateAngleZ = MathHelper.cos(limbSwing * 0.6662F) * 0.0533F * limbSwingAmount;
        copyModelAngles(body, wool5);
            this.wool5.rotateAngleZ = MathHelper.cos(limbSwing * 0.6662F) * 0.0666F * limbSwingAmount;
        copyModelAngles(body, wool6);
            this.wool6.rotateAngleZ = MathHelper.cos(limbSwing * 0.6662F) * 0.0799F * limbSwingAmount;
        copyModelAngles(body, wool7);
            this.wool7.rotateAngleZ = MathHelper.cos(limbSwing * 0.6662F) * 0.0933F * limbSwingAmount;
        copyModelAngles(body, wool8);
            this.wool8.rotateAngleZ = MathHelper.cos(limbSwing * 0.6662F) * 0.1066F * limbSwingAmount;
        copyModelAngles(body, wool9);
            this.wool9.rotateAngleZ = MathHelper.cos(limbSwing * 0.6662F) * 0.1199F * limbSwingAmount;
        copyModelAngles(body, wool10);
            this.wool10.rotateAngleZ = MathHelper.cos(limbSwing * 0.6662F) * 0.1333F * limbSwingAmount;
        copyModelAngles(body, wool11);
            this.wool11.rotateAngleZ = MathHelper.cos(limbSwing * 0.6662F) * 0.1466F * limbSwingAmount;
        copyModelAngles(body, wool12);
            this.wool12.rotateAngleZ = MathHelper.cos(limbSwing * 0.6662F) * 0.1599F * limbSwingAmount;
        copyModelAngles(body, wool13);
         this.wool13.rotateAngleZ = MathHelper.cos(limbSwing * 0.6662F) * 0.1733F * limbSwingAmount;
        copyModelAngles(body, wool14);
            this.wool14.rotateAngleZ = MathHelper.cos(limbSwing * 0.6662F) * 0.1866F * limbSwingAmount;
        copyModelAngles(body, wool15);
            this.wool15.rotateAngleZ = MathHelper.cos(limbSwing * 0.6662F) * 0.2F * limbSwingAmount;

        copyModelAngles(neck, neckWool1);
        copyModelAngles(neck, neckWool2);
        copyModelAngles(neck, neckWool3);
        copyModelAngles(neck, neckWool4);
        copyModelAngles(neck, neckWool5);
        copyModelAngles(neck, neckWool6);
        copyModelAngles(neck, neckWool7);

        copyModelAngles(neck, headWool1);
        copyModelAngles(neck, cheekWool1);
        copyModelAngles(neck, noseWool1);
        this.headWool1Child.rotateAngleX = 1.6F;
        this.cheekWool1Child.rotateAngleX = 1.6F;
        this.noseWool1Child.rotateAngleX = 1.6F;

        copyModelAngles(neck, hornBase);
        copyModelAngles(neck, polyHornBase);


        setHornRotations(sharedGenes, uuidArry, unrenderedModels, entityIn);

        return unrenderedModels;

    }

    private void setHornRotations(int[] sharedGenes, char[] uuidArray, List<String> unrenderedModels, T entityIn) {

        SheepModelData sheepModelData = getSheepModelData(entityIn);
        EnhancedSheep enhancedSheep = (EnhancedSheep) entityIn;



        //TODO add size variation
        int lengthL = 0;
        int lengthR = 0;

        Float[] hornGrowthL = {0.0F, -1.95F, -1.95F, -1.95F, -1.95F, -1.95F, -1.95F, -1.95F, -1.95F, -1.95F, -1.95F, -1.8F, -1.4F, -1.1F, -0.9F, -0.7876F, -0.675F, -0.5376F, -0.4F};
        Float[] hornGrowthR = hornGrowthL;

        int hornGrowth = 0;
        if (!(sheepModelData.birthTime == null) && !sheepModelData.birthTime.equals("") && !sheepModelData.birthTime.equals("0")) {
            int ageTime = (int)(((WorldInfo)((ClientWorld)enhancedSheep.world).getWorldInfo()).getGameTime() - Long.parseLong(sheepModelData.birthTime));
            if (ageTime > 744000) {
                hornGrowth = 0;
            } else if (ageTime > 552000) {
                hornGrowth = 1;
            } else if (ageTime > 360000) {
                hornGrowth = 2;
            } else if (ageTime > 276000) {
                hornGrowth = 3;
            } else if (ageTime > 234000) {
                hornGrowth = 4;
            } else if (ageTime > 192000) {
                hornGrowth = 5;
            } else if (ageTime > 168000) {
                hornGrowth = 6;
            } else if (ageTime > 144000) {
                hornGrowth = 7;
            } else if (ageTime > 120000) {
                hornGrowth = 8;
            } else if (ageTime > 96000) {
                hornGrowth = 9;
            } else if (ageTime > 72000) {
                hornGrowth = 10;
            } else if (ageTime > 60000) {
                hornGrowth = 11;
            } else if (ageTime > 48000) {
                hornGrowth = 12;
            } else if (ageTime > 36000) {
                hornGrowth = 13;
            } else if (ageTime > 30000) {
                hornGrowth = 14;
            } else if (ageTime > 27000) {
                hornGrowth = 15;
            } else if (ageTime > 24000) {
                hornGrowth = 16;
            } else if (ageTime > 21000) {
                hornGrowth = 17;
            } else {
                hornGrowth = 18;
            }
        }

        lengthL = lengthL + hornGrowth;
        lengthR = lengthR + hornGrowth;

        if (lengthL != 0) {
            for (int i = 0; i <= 18; i++) {
                if (i <= lengthL) {
                    hornGrowthL[i] = 0.0F;
                    unrenderedModels.add(this.sheepLeftHorns.get(i).boxName);
                }
                if (i <= lengthR) {
                    hornGrowthR[i] = 0.0F;
                    unrenderedModels.add(this.sheepRightHorns.get(i).boxName);
                }
                if (i == lengthL) {
                    unrenderedModels.remove(this.sheepLeftHorns.get(i).boxName);
                }
                if (i == lengthR) {
                    unrenderedModels.remove(this.sheepRightHorns.get(i).boxName);
                }
            }
        }

        this.hornL0.rotationPointX = -0.75F;
        this.hornR0.rotationPointX = -this.hornL0.rotationPointX;

        for (int i = 1; i <= 18; i++) {
            this.sheepLeftHorns.get(i).rotationPointY = hornGrowthL[i];
            this.sheepRightHorns.get(i).rotationPointY = hornGrowthR[i];
        }

        float polycerate = 1.0F;

        if (sharedGenes[36] == 1 || sharedGenes[37] == 1) {
            if (Character.isLetter(uuidArray[0]) || uuidArray[0] - 48 >= 8) {
                //horns if "male"
                if (Character.isLetter(uuidArray[2]) || uuidArray[2] - 48 >= 3) {
                    polycerate = -0.001F;
                }
            } else {
                if (uuidArray[2] - 48 <= 3) {
                    polycerate = -0.001F;
                }
            }
        }

        this.polyHornL0.rotateAngleX = this.polyHornL0.rotateAngleX + 0.001F;
        this.polyHornR0.rotateAngleX = this.polyHornR0.rotateAngleX - 0.001F;

        this.polyHornL0.rotateAngleX = -1.0F;
        this.polyHornR0.rotateAngleX = -1.0F;
        this.polyHornL0.rotateAngleY = 2.6F;
        this.polyHornR0.rotateAngleY = -2.6F;
        this.polyHornL0.rotateAngleZ = 1.6F;
        this.polyHornR0.rotateAngleZ = -1.6F;

        //horn shape controllers
//        if (horns != 0) {
            float a = 0.2F + ((1.0F - polycerate)* -0.05F);
            float b = 0.3F + ((1.0F - polycerate)* 0.05F);
            float x = a * ( 1.0F + (b * 1.5F));
                    
            Float[] hornCalculationsZ = {(a) + 0.25F, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a};
            Float[] hornCalculationsX = {(x) + 0.25F, x, x, x, x, x, x, x, x, x, a*(1.0F+(b*1.4F)), a*(1.0F+(b*1.3F)), a*(1.0F+(b*1.2F)), a*(1.0F+(b*1.1F)), a*(1.0F+(b*1.0F)), a*(1.0F+(b*0.9F)), a*(1.0F+(b*0.8F)), a*(1.0F+(b*0.7F)), a*(1.0F+(b*0.6F))};
            Float[] hornCalculationsY = {(a) + 0.25F, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a};

            for (int z = 1; z <= 18; z++) {
                if (hornGrowthL[z] != 0.0F) {
                    hornGrowthL[z] = 1.0F;
                }
                if (hornGrowthR[z] != 0.0F) {
                    hornGrowthR[z] = 1.0F;
                }
            }

            this.hornL0.rotateAngleZ = hornCalculationsZ[0];
            this.hornR0.rotateAngleZ = -hornCalculationsZ[0];
            this.hornL0.rotateAngleX = -hornCalculationsX[0];
            this.hornR0.rotateAngleX = -hornCalculationsX[0];
            this.hornL0.rotateAngleY = hornCalculationsY[0];
            this.hornR0.rotateAngleY = -hornCalculationsY[0];

            for (int r = 1; r <= 18; r++) {
                this.sheepRightHorns.get(r).rotateAngleZ = (hornCalculationsZ[r] - (0.3F*polycerate)) * hornGrowthR[r];
                this.sheepRightHorns.get(r).rotateAngleX = -(hornCalculationsX[r] + (0.2F*polycerate)) * hornGrowthR[r];
                this.sheepRightHorns.get(r).rotateAngleY = -hornCalculationsY[r] * hornGrowthR[r];
                this.sheepLeftHorns.get(r).rotateAngleZ = -(hornCalculationsZ[r] - (0.3F*polycerate)) * hornGrowthL[r];
                this.sheepLeftHorns.get(r).rotateAngleX = -(hornCalculationsX[r] + (0.2F*polycerate)) * hornGrowthL[r];
                this.sheepLeftHorns.get(r).rotateAngleY = hornCalculationsZ[r] * hornGrowthL[r];
            }
//        }
    }

    /**
     * Used for easily adding entity-dependent animations. The second and third float params here are the same second
     * and third as in the setRotationAngles method.
     */
    @Override
    public void setLivingAnimations(T entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTickTime)
    {
        super.setLivingAnimations(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTickTime);
        boolean sleeping = getSheepModelData(entitylivingbaseIn).sleeping;
        float onGround;
        float sleepyHead = 0.0F;

        if (sleeping) {
            onGround = sleepingAnimation();
            sleepyHead = 0.7F;
        } else {
            onGround = standingAnimation();
        }

        this.neck.rotationPointY = onGround + ((EnhancedSheep)entitylivingbaseIn).getHeadRotationPointY(partialTickTime) * 9.0F;
        this.headRotationAngleX = sleepyHead + ((EnhancedSheep)entitylivingbaseIn).getHeadRotationAngleX(partialTickTime);
    }

    private float sleepingAnimation() {
        float onGround;
        onGround = 15.75F;
        this.body.rotationPointY = 6.75F;
        this.udder.rotationPointY = 18.75F;
        this.tailBase.rotationPointY = 15.75F;

        this.leg1.setRotationPoint(-4.0F, 20.75F,-5.0F);
        this.leg2.setRotationPoint(1.0F, 23.75F,-10.0F);
        this.leg3.setRotationPoint(-4.0F, 20.75F,8.0F);
        this.leg4.setRotationPoint(1.0F, 20.75F,8.0F);

        this.neck.rotateAngleY = 0.8F;

        this.leg1.rotateAngleX = -1.575F;
        this.leg2.rotateAngleX = 1.575F;
        this.leg3.rotateAngleX = -1.575F;
        this.leg4.rotateAngleX = -1.575F;

        this.leg1.rotateAngleY = -0.1F;
        this.leg2.rotateAngleY = -0.1F;

        return onGround;
    }

    private float standingAnimation() {
        float onGround;
        onGround = 9.0F;
        this.body.rotationPointY = 0.0F;
        this.udder.rotationPointY = 12.0F;
        this.tailBase.rotationPointY = 9.0F;

        this.leg1.setRotationPoint(-4.0F, 14.0F,-8.0F);
        this.leg2.setRotationPoint(1.0F, 14.0F,-8.0F);
        this.leg3.setRotationPoint(-4.0F, 14.0F,5.0F);
        this.leg4.setRotationPoint(1.0F, 14.0F,5.0F);

        return onGround;
    }

    public static void copyModelAngles(RendererModel source, RendererModel dest) {
        dest.rotateAngleX = source.rotateAngleX;
        dest.rotateAngleY = source.rotateAngleY;
        dest.rotateAngleZ = source.rotateAngleZ;
        dest.rotationPointX = source.rotationPointX;
        dest.rotationPointY = source.rotationPointY;
        dest.rotationPointZ = source.rotationPointZ;
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

    private class SheepModelData {
        float previousSwing;
        int[] sheepGenes;
        String birthTime;
        String sheepStatus;
        int coatlength;
        char[] uuidArray;
        boolean sleeping;
        int lastAccessed = 0;
        int dataReset = 0;
    }

    private SheepModelData getSheepModelData(T enhancedSheep) {
        clearCacheTimer++;
        if(clearCacheTimer > 100000) {
            sheepModelDataCache.values().removeIf(value -> value.lastAccessed==1);
            for (SheepModelData llamaModelData : sheepModelDataCache.values()){
                llamaModelData.lastAccessed = 1;
            }
            clearCacheTimer = 0;
        }

        if (sheepModelDataCache.containsKey(enhancedSheep.getEntityId())) {
            SheepModelData sheepModelData = sheepModelDataCache.get(enhancedSheep.getEntityId());
            sheepModelData.lastAccessed = 0;
            sheepModelData.dataReset++;
            if (sheepModelData.dataReset > 5000) {
                sheepModelData.sheepStatus = enhancedSheep.getSheepStatus();
                sheepModelData.dataReset = 0;
            }
            sheepModelData.coatlength = enhancedSheep.getCoatLength();
            sheepModelData.sleeping = enhancedSheep.isAnimalSleeping();

            return sheepModelData;
        } else {
            SheepModelData sheepModelData = new SheepModelData();
            sheepModelData.sheepGenes = enhancedSheep.getSharedGenes();
            sheepModelData.coatlength = enhancedSheep.getCoatLength();
            sheepModelData.sleeping = enhancedSheep.isAnimalSleeping();
            sheepModelData.sheepStatus = enhancedSheep.getSheepStatus();
            sheepModelData.uuidArray = enhancedSheep.getCachedUniqueIdString().toCharArray();
            sheepModelData.birthTime = enhancedSheep.getBirthTime();

            sheepModelDataCache.put(enhancedSheep.getEntityId(), sheepModelData);

            return sheepModelData;
        }
    }

}
