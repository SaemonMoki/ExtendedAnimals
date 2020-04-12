package mokiyoki.enhancedanimals.model;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import mokiyoki.enhancedanimals.entity.EnhancedSheep;
import mokiyoki.enhancedanimals.entity.EntityState;
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
public class ModelEnhancedSheep  <T extends EnhancedSheep> extends EntityModel<T> {

    private Map<Integer, SheepModelData> sheepModelDataCache = new HashMap<>();
    private int clearCacheTimer = 0;

    private float headRotationAngleX;
    private float f12 = 0F;

    private final ModelRenderer head;
    private final ModelRenderer neck;
    private final EnhancedRendererModelNew hornBase;
    private final EnhancedRendererModelNew polyHornBase;
    private final EnhancedRendererModelNew polyHornL0;
    private final EnhancedRendererModelNew hornL0;
    private final EnhancedRendererModelNew hornL01;
    private final EnhancedRendererModelNew hornL02;
    private final EnhancedRendererModelNew hornL03;
    private final EnhancedRendererModelNew hornL04;
    private final EnhancedRendererModelNew hornL05;
    private final EnhancedRendererModelNew hornL06;
    private final EnhancedRendererModelNew hornL07;
    private final EnhancedRendererModelNew hornL08;
    private final EnhancedRendererModelNew hornL09;
    private final EnhancedRendererModelNew hornL1;
    private final EnhancedRendererModelNew hornL2;
    private final EnhancedRendererModelNew hornL3;
    private final EnhancedRendererModelNew hornL4;
    private final EnhancedRendererModelNew hornL5;
    private final EnhancedRendererModelNew hornL6;
    private final EnhancedRendererModelNew hornL7;
    private final EnhancedRendererModelNew hornL8;
    private final EnhancedRendererModelNew hornL9;
    private final EnhancedRendererModelNew polyHornR0;
    private final EnhancedRendererModelNew hornR0;
    private final EnhancedRendererModelNew hornR01;
    private final EnhancedRendererModelNew hornR02;
    private final EnhancedRendererModelNew hornR03;
    private final EnhancedRendererModelNew hornR04;
    private final EnhancedRendererModelNew hornR05;
    private final EnhancedRendererModelNew hornR06;
    private final EnhancedRendererModelNew hornR07;
    private final EnhancedRendererModelNew hornR08;
    private final EnhancedRendererModelNew hornR09;
    private final EnhancedRendererModelNew hornR1;
    private final EnhancedRendererModelNew hornR2;
    private final EnhancedRendererModelNew hornR3;
    private final EnhancedRendererModelNew hornR4;
    private final EnhancedRendererModelNew hornR5;
    private final EnhancedRendererModelNew hornR6;
    private final EnhancedRendererModelNew hornR7;
    private final EnhancedRendererModelNew hornR8;
    private final EnhancedRendererModelNew hornR9;
    private final ModelRenderer earsR;
    private final ModelRenderer earsL;
    private final ModelRenderer body;
    private final ModelRenderer wool1;
    private final ModelRenderer wool2;
    private final ModelRenderer wool3;
    private final ModelRenderer wool4;
    private final ModelRenderer wool5;
    private final ModelRenderer wool6;
    private final ModelRenderer wool7;
    private final ModelRenderer wool8;
    private final ModelRenderer wool9;
    private final ModelRenderer wool10;
    private final ModelRenderer wool11;
    private final ModelRenderer wool12;
    private final ModelRenderer wool13;
    private final ModelRenderer wool14;
    private final ModelRenderer wool15;
    private final ModelRenderer neckWool1;
    private final ModelRenderer neckWool2;
    private final ModelRenderer neckWool3;
    private final ModelRenderer neckWool4;
    private final ModelRenderer neckWool5;
    private final ModelRenderer neckWool6;
    private final ModelRenderer neckWool7;
    private final ModelRenderer headWool1;
    private final ModelRenderer headWool1Child;
//    private final ModelRenderer headWool3;
//    private final ModelRenderer headWool4;
//    private final ModelRenderer headWool5;
//    private final ModelRenderer headWool6;
//    private final ModelRenderer headWool7;
    private final ModelRenderer cheekWool1;
    private final ModelRenderer cheekWool1Child;
    private final ModelRenderer noseWool1;
    private final ModelRenderer noseWool1Child;
//    private final ModelRenderer faceWool2;
//    private final ModelRenderer faceWool3;
//    private final ModelRenderer faceWool4;
//    private final ModelRenderer faceWool5;
//    private final ModelRenderer faceWool6;
//    private final ModelRenderer faceWool7;
    private final ModelRenderer udder;
    private final ModelRenderer nippleL;
    private final ModelRenderer nippleR;
    private final ModelRenderer tailBase;
    private final ModelRenderer tailMiddle;
    private final ModelRenderer tailTip;
    public final ModelRenderer leg1;
    public final ModelRenderer leg2;
    public final ModelRenderer leg3;
    public final ModelRenderer leg4;

    private Integer currentSheep = null;

    private final List<EnhancedRendererModelNew> sheepLeftHorns = new ArrayList<>();
    private final List<EnhancedRendererModelNew> sheepRightHorns = new ArrayList<>();

    public ModelEnhancedSheep()
    {
        this.textureWidth = 64;
        this.textureHeight = 64;

        float xMove = -6.0F;

        this.head = new ModelRenderer(this, 0, 0);
        this.head.addBox(-2.5F, 0.0F, -5.0F, 5, 4, 4, 0.0F); //skull
        this.head.setTextureOffset(0, 8);
        this.head.addBox(-2.0F, 0.0F, -8.0F, 4, 3, 3, 0.0F); //nose
        this.head.setRotationPoint(0.0F, -8.0F, 0.0F);

        this.neck = new ModelRenderer(this, 34, 0);
        this.neck.addBox(-2.0F, -7.0F, -4.0F, 4, 9, 4, 0.0F); //neck
        this.neck.setRotationPoint(0.0F, 0.0F, -6.0F);

        this.hornBase = new EnhancedRendererModelNew(this, 0, 36);
        this.hornBase.addBox(-1.5F, -8.9F, -4.1F, 3, 3, 3, -1.0F);
        this.hornBase.setRotationPoint(0.0F, 0.0F, -6.0F);

        this.polyHornBase = new EnhancedRendererModelNew(this, 0, 36);
        this.polyHornBase.addBox(-1.5F, -8.9F, -4.1F, 3, 3, 3, -1.0F);
        this.polyHornBase.setRotationPoint(0.0F, 0.0F, -6.0F);

        this.polyHornL0 = new EnhancedRendererModelNew(this, 0, 36);
        this.polyHornL0.addBox(0.0F, -3.0F, -1.5F, 3, 3, 3, -0.6F);
        this.polyHornL0.setRotationPoint(3.0F, -5.0F, -1.0F);

        this.polyHornR0 = new EnhancedRendererModelNew(this, 0, 36);
        this.polyHornR0.addBox(-3.0F, -3.0F, -1.5F, 3, 3, 3, -0.6F);
        this.polyHornR0.setRotationPoint(-3.0F, -5.0F, -1.0F);

        // scale down by 0.1 for each
        this.hornL0 = new EnhancedRendererModelNew(this, 0, 36, "HornL0");
        this.hornL0.addBox(0.0F, -3.0F, -1.5F, 3, 3, 3, -0.5F);
        this.hornL0.setRotationPoint(0.0F, -7.4F, -3.0F);
        sheepLeftHorns.add(hornL0);

        this.hornL01 = new EnhancedRendererModelNew(this, 0, 36, "HornL01");
        this.hornL01.addBox(0.0F, -3.0F, -1.5F, 3, 3, 3, -0.5F);
        this.hornL01.setRotationPoint(0.0F, 0.0F, 0.0F);
        sheepLeftHorns.add(hornL01);

        this.hornL02 = new EnhancedRendererModelNew(this, 0, 36, "HornL02");
        this.hornL02.addBox(0.0F, -3.0F, -1.5F, 3, 3, 3, -0.5F);
        this.hornL02.setRotationPoint(0.0F, 0.0F, 0.0F);
        sheepLeftHorns.add(hornL02);

        this.hornL03 = new EnhancedRendererModelNew(this, 0, 36, "HornL03");
        this.hornL03.addBox(0.0F, -3.0F, -1.5F, 3, 3, 3, -0.5F);
        this.hornL03.setRotationPoint(0.0F, 0.0F, 0.0F);
        sheepLeftHorns.add(hornL03);

        this.hornL04 = new EnhancedRendererModelNew(this, 0, 36, "HornL04");
        this.hornL04.addBox(0.0F, -3.0F, -1.5F, 3, 3, 3, -0.5F);
        this.hornL04.setRotationPoint(0.0F, 0.0F, 0.0F);
        sheepLeftHorns.add(hornL04);

        this.hornL05 = new EnhancedRendererModelNew(this, 0, 36, "HornL05");
        this.hornL05.addBox(0.0F, -3.0F, -1.5F, 3, 3, 3, -0.5F);
        this.hornL05.setRotationPoint(0.0F, 0.0F, 0.0F);
        sheepLeftHorns.add(hornL05);

        this.hornL06 = new EnhancedRendererModelNew(this, 0, 36, "HornL06");
        this.hornL06.addBox(0.0F, -3.0F, -1.5F, 3, 3, 3, -0.5F);
        this.hornL06.setRotationPoint(0.0F, 0.0F, 0.0F);
        sheepLeftHorns.add(hornL06);

        this.hornL07 = new EnhancedRendererModelNew(this, 0, 36, "HornL07");
        this.hornL07.addBox(0.0F, -3.0F, -1.5F, 3, 3, 3, -0.5F);
        this.hornL07.setRotationPoint(0.0F, 0.0F, 0.0F);
        sheepLeftHorns.add(hornL07);

        this.hornL08 = new EnhancedRendererModelNew(this, 0, 36, "HornL08");
        this.hornL08.addBox(0.0F, -3.0F, -1.5F, 3, 3, 3, -0.5F);
        this.hornL08.setRotationPoint(0.0F, 0.0F, 0.0F);
        sheepLeftHorns.add(hornL08);

        this.hornL09 = new EnhancedRendererModelNew(this, 0, 36, "HornL09");
        this.hornL09.addBox(0.0F, -3.0F, -1.5F, 3, 3, 3, -0.5F);
        this.hornL09.setRotationPoint(0.0F, 0.0F, 0.0F);
        sheepLeftHorns.add(hornL09);

        this.hornL1 = new EnhancedRendererModelNew(this, 0, 36, "HornL1");
        this.hornL1.addBox(0.0F, -3.0F, -1.5F, 3, 3, 3, -0.6F);
        this.hornL1.setRotationPoint(0.0F, 0.0F, 0.0F);
        sheepLeftHorns.add(hornL1);

        this.hornL2 = new EnhancedRendererModelNew(this, 0, 36, "HornL2");
        this.hornL2.addBox(0.0F, -3.0F, -1.5F, 3, 3, 3, -0.7F);
        this.hornL2.setRotationPoint(0.0F, 0.0F, 0.0F);
        sheepLeftHorns.add(hornL2);

        this.hornL3 = new EnhancedRendererModelNew(this, 0, 36, "HornL3");
        this.hornL3.addBox(0.0F, -3.0F, -1.5F, 3, 3, 3, -0.8F);
        this.hornL3.setRotationPoint(0.0F, 0.0F, 0.0F);
        sheepLeftHorns.add(hornL3);

        this.hornL4 = new EnhancedRendererModelNew(this, 0, 36, "HornL4");
        this.hornL4.addBox(0.0F, -3.0F, -1.5F, 3, 3, 3, -0.9F);
        this.hornL4.setRotationPoint(0.0F, 0.0F, 0.0F);
        sheepLeftHorns.add(hornL4);

        this.hornL5 = new EnhancedRendererModelNew(this, 0, 36, "HornL5");
        this.hornL5.addBox(0.0F, -3.0F, -1.5F, 3, 3, 3, -1.0F);
        this.hornL5.setRotationPoint(0.0F, 0.0F, 0.0F);
        sheepLeftHorns.add(hornL5);

        this.hornL6 = new EnhancedRendererModelNew(this, 0, 36, "HornL6");
        this.hornL6.addBox(0.0F, -3.0F, -1.5F, 3, 3, 3, -1.0562F);
        this.hornL6.setRotationPoint(0.0F, 0.0F, 0.0F);
        sheepLeftHorns.add(hornL6);

        this.hornL7 = new EnhancedRendererModelNew(this, 0, 36, "HornL7");
        this.hornL7.addBox(0.0F, -3.0F, -1.5F, 3, 3, 3, -1.1125F);
        this.hornL7.setRotationPoint(0.0F, 0.0F, 0.0F);
        sheepLeftHorns.add(hornL7);

        this.hornL8 = new EnhancedRendererModelNew(this, 0, 36, "HornL8");
        this.hornL8.addBox(0.0F, -3.0F, -1.5F, 3, 3, 3, -1.1812F);
        this.hornL8.setRotationPoint(0.0F, 0.0F, 0.0F);
        sheepLeftHorns.add(hornL8);

        this.hornL9 = new EnhancedRendererModelNew(this, 0, 36, "HornL9");
        this.hornL9.addBox(0.0F, -3.0F, -1.5F, 3, 3, 3, -1.25F);
        this.hornL9.setRotationPoint(0.0F, 0.0F, 0.0F);
        sheepLeftHorns.add(hornL9);

        this.hornR0 = new EnhancedRendererModelNew(this, 0, 36);
        this.hornR0.addBox(-3.0F, -3.0F, -1.5F, 3, 3, 3, -0.5F);
        this.hornR0.setRotationPoint(0.0F, -7.4F, -3.0F);
        sheepRightHorns.add(hornR0);

        this.hornR01 = new EnhancedRendererModelNew(this, 0, 36);
        this.hornR01.addBox(-3.0F, -3.0F, -1.5F, 3, 3, 3, -0.5F);
        this.hornR01.setRotationPoint(0.0F, 0.0F, 0.0F);
        sheepRightHorns.add(hornR01);

        this.hornR02 = new EnhancedRendererModelNew(this, 0, 36);
        this.hornR02.addBox(-3.0F, -3.0F, -1.5F, 3, 3, 3, -0.5F);
        this.hornR02.setRotationPoint(0.0F, 0.0F, 0.0F);
        sheepRightHorns.add(hornR02);

        this.hornR03 = new EnhancedRendererModelNew(this, 0, 36);
        this.hornR03.addBox(-3.0F, -3.0F, -1.5F, 3, 3, 3, -0.5F);
        this.hornR03.setRotationPoint(0.0F, 0.0F, 0.0F);
        sheepRightHorns.add(hornR03);

        this.hornR04 = new EnhancedRendererModelNew(this, 0, 36);
        this.hornR04.addBox(-3.0F, -3.0F, -1.5F, 3, 3, 3, -0.5F);
        this.hornR04.setRotationPoint(0.0F, 0.0F, 0.0F);
        sheepRightHorns.add(hornR04);

        this.hornR05 = new EnhancedRendererModelNew(this, 0, 36);
        this.hornR05.addBox(-3.0F, -3.0F, -1.5F, 3, 3, 3, -0.5F);
        this.hornR05.setRotationPoint(0.0F, 0.0F, 0.0F);
        sheepRightHorns.add(hornR05);

        this.hornR06 = new EnhancedRendererModelNew(this, 0, 36);
        this.hornR06.addBox(-3.0F, -3.0F, -1.5F, 3, 3, 3, -0.5F);
        this.hornR06.setRotationPoint(0.0F, 0.0F, 0.0F);
        sheepRightHorns.add(hornR06);

        this.hornR07 = new EnhancedRendererModelNew(this, 0, 36);
        this.hornR07.addBox(-3.0F, -3.0F, -1.5F, 3, 3, 3, -0.5F);
        this.hornR07.setRotationPoint(0.0F, 0.0F, 0.0F);
        sheepRightHorns.add(hornR07);

        this.hornR08 = new EnhancedRendererModelNew(this, 0, 36);
        this.hornR08.addBox(-3.0F, -3.0F, -1.5F, 3, 3, 3, -0.5F);
        this.hornR08.setRotationPoint(0.0F, 0.0F, 0.0F);
        sheepRightHorns.add(hornR08);

        this.hornR09 = new EnhancedRendererModelNew(this, 0, 36);
        this.hornR09.addBox(-3.0F, -3.0F, -1.5F, 3, 3, 3, -0.5F);
        this.hornR09.setRotationPoint(0.0F, 0.0F, 0.0F);
        sheepRightHorns.add(hornR09);

        this.hornR1 = new EnhancedRendererModelNew(this, 0, 36);
        this.hornR1.addBox(-3.0F, -3.0F, -1.5F, 3, 3, 3, -0.6F);
        this.hornR1.setRotationPoint(0.0F, 0.0F, 0.0F);
        sheepRightHorns.add(hornR1);

        this.hornR2 = new EnhancedRendererModelNew(this, 0, 36);
        this.hornR2.addBox(-3.0F, -3.0F, -1.5F, 3, 3, 3, -0.7F);
        this.hornR2.setRotationPoint(0.0F, 0.0F, 0.0F);
        sheepRightHorns.add(hornR2);

        this.hornR3 = new EnhancedRendererModelNew(this, 0, 36);
        this.hornR3.addBox(-3.0F, -3.0F, -1.5F, 3, 3, 3, -0.8F);
        this.hornR3.setRotationPoint(0.0F, 0.0F, 0.0F);
        sheepRightHorns.add(hornR3);

        this.hornR4 = new EnhancedRendererModelNew(this, 0, 36);
        this.hornR4.addBox(-3.0F, -3.0F, -1.5F, 3, 3, 3, -0.9F);
        this.hornR4.setRotationPoint(0.0F, 0.0F, 0.0F);
        sheepRightHorns.add(hornR4);

        this.hornR5 = new EnhancedRendererModelNew(this, 0, 36);
        this.hornR5.addBox(-3.0F, -3.0F, -1.5F, 3, 3, 3, -1.0F);
        this.hornR5.setRotationPoint(0.0F, 0.0F, 0.0F);
        sheepRightHorns.add(hornR5);

        this.hornR6 = new EnhancedRendererModelNew(this, 0, 36);
        this.hornR6.addBox(-3.0F, -3.0F, -1.5F, 3, 3, 3, -1.0562F);
        this.hornR6.setRotationPoint(0.0F, 0.0F, 0.0F);
        sheepRightHorns.add(hornR6);

        this.hornR7 = new EnhancedRendererModelNew(this, 0, 36);
        this.hornR7.addBox(-3.0F, -3.0F, -1.5F, 3, 3, 3, -1.1125F);
        this.hornR7.setRotationPoint(0.0F, 0.0F, 0.0F);
        sheepRightHorns.add(hornR7);

        this.hornR8 = new EnhancedRendererModelNew(this, 0, 36);
        this.hornR8.addBox(-3.0F, -3.0F, -1.5F, 3, 3, 3, -1.1812F);
        this.hornR8.setRotationPoint(0.0F, 0.0F, 0.0F);
        sheepRightHorns.add(hornR8);

        this.hornR9 = new EnhancedRendererModelNew(this, 0, 36);
        this.hornR9.addBox(-3.0F, -3.0F, -1.5F, 3, 3, 3, -1.25F);
        this.hornR9.setRotationPoint(0.0F, 0.0F, 0.0F);
        sheepRightHorns.add(hornR9);

        this.earsR = new ModelRenderer(this, 50, 0);
        this.earsR.addBox(-5.0F, -8.5F, -2.0F, 3, 2, 1, 0.0F); //ear right
        this.earsL = new ModelRenderer(this, 50, 3);
        this.earsL.addBox(2.0F, -8.5F, -2.0F, 3, 2, 1, 0.0F); //ear left

        this.body = new ModelRenderer(this, 2, 0);
        this.body.addBox(-4.0F, 8.0F, -2.0F, 8, 6, 16, 0.0F);
        this.body.setRotationPoint(0.0F, 0.0F, -6.0F);

        this.wool1 = new ModelRenderer(this, 2, 0);
        this.wool1.addBox(-4.0F, 8.0F, -2.0F, 8, 6, 16, 0.2F);

        this.wool2 = new ModelRenderer(this, 2, 0);
        this.wool2.addBox(-4.0F, 8.0F, -2.0F, 8, 6, 16, 0.4F);

        this.wool3 = new ModelRenderer(this, 2, 0);
        this.wool3.addBox(-4.0F, 8.0F, -2.0F, 8, 6, 16, 0.6F);

        this.wool4 = new ModelRenderer(this, 2, 0);
        this.wool4.addBox(-4.0F, 8.0F, -2.0F, 8, 6, 16, 0.8F);

        this.wool5 = new ModelRenderer(this, 2, 0);
        this.wool5.addBox(-4.0F, 8.0F, -2.0F, 8, 6, 16, 1.0F);

        this.wool6 = new ModelRenderer(this, 2, 0);
        this.wool6.addBox(-4.0F, 8.0F, -2.0F, 8, 6, 16, 1.2F);

        this.wool7 = new ModelRenderer(this, 2, 0);
        this.wool7.addBox(-4.0F, 8.0F, -2.0F, 8, 6, 16, 1.4F);

        this.wool8 = new ModelRenderer(this, 2, 0);
        this.wool8.addBox(-4.0F, 8.0F, -2.0F, 8, 6, 16, 1.6F);

        this.wool9 = new ModelRenderer(this, 2, 0);
        this.wool9.addBox(-4.0F, 8.0F, -2.0F, 8, 6, 16, 1.8F);

        this.wool10 = new ModelRenderer(this, 2, 0);
        this.wool10.addBox(-4.0F, 8.0F, -2.0F, 8, 6, 16, 2.0F);

        this.wool11 = new ModelRenderer(this, 2, 0);
        this.wool11.addBox(-4.0F, 8.0F, -2.0F, 8, 6, 16, 2.2F);

        this.wool12 = new ModelRenderer(this, 2, 0);
        this.wool12.addBox(-4.0F, 8.0F, -2.0F, 8, 6, 16, 2.4F);

        this.wool13 = new ModelRenderer(this, 2, 0);
        this.wool13.addBox(-4.0F, 8.0F, -2.0F, 8, 6, 16, 2.6F);

        this.wool14 = new ModelRenderer(this, 2, 0);
        this.wool14.addBox(-4.0F, 8.0F, -2.0F, 8, 6, 16, 2.8F);

        this.wool15 = new ModelRenderer(this, 2, 0);
        this.wool15.addBox(-4.0F, 8.0F, -2.0F, 8, 6, 16, 3.0F);

        this.neckWool1 = new ModelRenderer(this, 34, 0);
        this.neckWool1.addBox(-2.0F, -5.6F, -4.0F, 4, 9, 4, 0.4F);
        this.neckWool1.setRotationPoint(0.0F, 0.0F, -6.0F);

        this.neckWool2 = new ModelRenderer(this, 34, 0);
        this.neckWool2.addBox(-2.0F, -5.25F, -4.0F, 4, 8, 4, 0.75F);
        this.neckWool2.setRotationPoint(0.0F, 0.0F, -6.0F);

        this.neckWool3 = new ModelRenderer(this, 34, 0);
        this.neckWool3.addBox(-2.0F, -4.9F, -4.0F, 4, 7, 4, 1.1F);
        this.neckWool3.setRotationPoint(0.0F, 0.0F, -6.0F);

        this.neckWool4 = new ModelRenderer(this, 34, 0);
        this.neckWool4.addBox(-2.0F, -4.5F, -4.0F, 4, 6, 4, 1.5F);
        this.neckWool4.setRotationPoint(0.0F, 0.0F, -6.0F);

        this.neckWool5 = new ModelRenderer(this, 34, 0);
        this.neckWool5.addBox(-2.0F, -4.0F, -4.0F, 4, 6, 4, 2.0F);
        this.neckWool5.setRotationPoint(0.0F, 0.0F, -6.0F);

        this.neckWool6 = new ModelRenderer(this, 34, 0);
        this.neckWool6.addBox(-2.0F, -3.5F, -4.0F, 4, 5, 4, 2.5F);
        this.neckWool6.setRotationPoint(0.0F, 0.0F, -6.0F);

        this.neckWool7 = new ModelRenderer(this, 34, 0);
        this.neckWool7.addBox(-2.0F, -3.0F, -4.0F, 4, 5, 4, 3.0F);
        this.neckWool7.setRotationPoint(0.0F, 0.0F, -6.0F);

        this.headWool1 = new ModelRenderer(this,0,0);
        this.headWool1.setRotationPoint(0.0F, 0.0F, -6.0F);
        this.headWool1Child = new ModelRenderer(this, 0, 55);
        this.headWool1Child.addBox(-2.5F, 0.0F, 0.0F, 5, 3, 4, 0.4F); //neck fluff
        this.headWool1Child.setRotationPoint(0.0F, -4.0F, -3.0F);

        this.cheekWool1 = new ModelRenderer(this,0,0);
        this.cheekWool1.setRotationPoint(0.0F, 0.0F, -6.0F);
        this.cheekWool1Child = new ModelRenderer(this, 14, 47);
        this.cheekWool1Child.addBox(-2.5F, 0.0F, 0.0F, 5, 2, 2, 0.5F); //cheek fluff
        this.cheekWool1Child.setRotationPoint(0.0F, -3.5F, -6.0F);

        this.noseWool1 = new ModelRenderer(this,0,0);
        this.noseWool1.setRotationPoint(0.0F, 0.0F, -6.0F);
        this.noseWool1Child = new ModelRenderer(this, 0, 47);
        this.noseWool1Child.addBox(-2.0F, 0.0F, 0.0F, 4, 2, 3, 0.4F); //cheek fluff
        this.noseWool1Child.setRotationPoint(0.0F, -5.0F, -7.0F);

        this.udder = new ModelRenderer(this, 46, 55);
        this.udder.addBox(-2.0F, 0.0F, 0.0F, 4, 4, 5);
        this.nippleL = new ModelRenderer(this, 46, 55);
        this.nippleL.addBox(-0.5F, 0.0F, -0.5F, 1, 3, 1);
        this.nippleR = new ModelRenderer(this, 60, 55);
        this.nippleR.addBox(-0.5F, 0.0F, -0.5F, 1, 3, 1);
        this.udder.setRotationPoint(0.0F, 12.0F, 3.25F);
        this.nippleL.setRotationPoint(-1.0F, 2.5F, 1.0F);
        this.nippleR.setRotationPoint(1.0F, 2.5F, 1.0F);

        this.tailBase = new ModelRenderer(this, 50, 6);
        this.tailBase.addBox(-1.0F, 0.0F, 0.0F, 2, 3, 1);
        this.tailBase.setRotationPoint(0.0F, 9.0F, 8.0F);

        this.tailMiddle = new ModelRenderer(this, 56, 6);
        this.tailMiddle.addBox(-0.5F, 0.0F, 0.0F, 1, 3, 1);
        this.tailMiddle.setRotationPoint(0.0F, 3.0F, 0.0F);

        this.tailTip = new ModelRenderer(this, 60, 6);
        this.tailTip.addBox(-0.5F, 0.0F, 0.0F, 1, 3, 1);
        this.tailTip.setRotationPoint(0.0F, 3.0F, 0.0F);

        this.leg1 = new ModelRenderer(this, 0, 22);
        this.leg1.addBox(0.0F, 0.0F, 0.0F, 3, 10, 3);
        this.leg1.setRotationPoint(-4.0F, 14.0F,-8.0F);
        this.leg2 = new ModelRenderer(this, 12, 22);
        this.leg2.addBox(0.0F, 0.0F, 0.0F, 3, 10, 3);
        this.leg2.setRotationPoint(1.0F, 14.0F,-8.0F);
        this.leg3 = new ModelRenderer(this, 24, 22);
        this.leg3.addBox(0.0F, 0.0F, 0.0F, 3, 10, 3);
        this.leg3.setRotationPoint(-4.0F, 14.0F,7.0F);
        this.leg4 = new ModelRenderer(this, 36, 22);
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
    public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        SheepModelData sheepModelData = getSheepModelData();

        int[] genes = sheepModelData.sheepGenes;
        int coatLength = sheepModelData.coatlength;
        String sheepStatus = sheepModelData.sheepStatus;
        char[] uuidArry = sheepModelData.uuidArray;
        boolean sleeping = sheepModelData.sleeping;
        float size = 1.0F;

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
            int ageTime = (int)(sheepModelData.clientGameTime - Long.parseLong(sheepModelData.birthTime));
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

        matrixStackIn.push();
        matrixStackIn.scale(finalSheepSize, finalSheepSize, finalSheepSize);
        matrixStackIn.translate(0.0F, (-1.5F + 1.5F/finalSheepSize) - d, 0.0F);

        renderAdult(sheepStatus, horns, hornScale, facewool, polycerate, sheepModelData.unrenderedModels, coatLength, matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);

        matrixStackIn.pop();

        matrixStackIn.push();
        matrixStackIn.scale(finalSheepSize, finalSheepSize * babyScale, finalSheepSize);
        matrixStackIn.translate(0.0F, -1.5F + 1.5F/(finalSheepSize * babyScale), 0.0F);

        renderLegs(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);

        matrixStackIn.pop();
//        }
    }

    private void renderAdult(String sheepStatus, boolean horns, float hornScale, int facewool, boolean polycerate, List<String> unrenderedModels, int coatLength, MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        this.neck.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.earsR.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.earsL.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.body.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);

        renderWool(facewool, coatLength, matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);

        if (horns){
            renderHorns(polycerate, hornScale, unrenderedModels, matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        }

        float woolLength = ((coatLength-1)*0.025F) + 1.0F;

//            GlStateManager.pushMatrix();
//            GlStateManager.scalef(woolLength, woolLength, woolLength);
//            GlStateManager.translatef(0.0F, -1.5F + 1.5F/(woolLength/1.5F), 0.0F);
//            this.body.render(scale);
//            GlStateManager.popMatrix();

        matrixStackIn.push();
        matrixStackIn.scale(woolLength, 1.0F, woolLength);
        matrixStackIn.translate(0.0F, 0.0F, 0.0F);
        this.tailBase.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        matrixStackIn.pop();

        if (sheepStatus.equals(EntityState.PREGNANT.toString()) || sheepStatus.equals(EntityState.MOTHER.toString())) {
            this.udder.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        }
    }

    private void renderLegs(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        ImmutableList.of(this.leg1, this.leg2, this.leg3, this.leg4).forEach((renderer) -> {
            renderer.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        });
    }

    private void renderHorns(boolean polycerate, float hornScale, List<String> unrenderedModels, MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        if (polycerate) {
            this.polyHornBase.render(matrixStackIn,bufferIn, null, unrenderedModels, false, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        }

        Map<String, List<Float>> mapOfScale = new HashMap<>();

        List<Float> scalingsForHorn = createScalings(hornScale, 0.0F, 0.0F, 0.0F);
        mapOfScale.put("HornL0", scalingsForHorn);
        mapOfScale.put("HornR0", mirrorX(scalingsForHorn, false));
        this.hornBase.render(matrixStackIn, bufferIn, mapOfScale, unrenderedModels, false, packedLightIn, packedOverlayIn, red, green, blue, alpha);
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

    private void renderWool(int facewool, int coatLength, MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha)  {
        if (coatLength == 1){
            this.wool1.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            if (true) {
                this.neckWool1.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            }
        }else if (coatLength == 2){
            this.wool2.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            if (true) {
                this.neckWool1.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            }
        }else if (coatLength == 3){
            this.wool3.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            if (true) {
                this.neckWool2.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            }
        }else if (coatLength == 4){
            this.wool4.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            if (true) {
                this.neckWool2.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            }
        }else if (coatLength == 5){
            this.wool5.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            if (true) {
                this.neckWool3.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            }
        }else if (coatLength == 6){
            this.wool6.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            if (true) {
                this.neckWool3.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            }
        }else if (coatLength == 7){
            this.wool7.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            if (true) {
                this.neckWool4.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            }
        }else if (coatLength == 8){
            this.wool8.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            if (true) {
                this.neckWool4.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            }
        }else if (coatLength == 9){
            this.wool9.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            if (true) {
                this.neckWool5.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            }
        }else if (coatLength == 10){
            this.wool10.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            if (true) {
                this.neckWool5.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            }
        }else if (coatLength == 11){
            this.wool11.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            if (true) {
                this.neckWool6.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            }
        }else if (coatLength == 12){
            this.wool12.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            if (true) {
                this.neckWool6.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            }
        }else if (coatLength == 13){
            this.wool13.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            if (true) {
                this.neckWool7.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            }
        }else if (coatLength == 14){
            this.wool14.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            if (true) {
                this.neckWool7.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            }
        }else if (coatLength == 15){
            this.wool15.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            if (true) {
                this.neckWool7.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            }
        }

        if (facewool >= 1) {
            this.headWool1.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            if (facewool >= 2) {
                this.cheekWool1.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                if (facewool == 3) {
                    this.noseWool1.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                }
            }
        }
    }

    @Override
    public void setLivingAnimations(T entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTickTime) {
        super.setLivingAnimations(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTickTime);

        SheepModelData sheepModelData = getCreateSheepModelData(entitylivingbaseIn);
        this.currentSheep = entitylivingbaseIn.getEntityId();

        float onGround;
        float sleepyHead = 0.0F;

        if (sheepModelData.sleeping) {
            onGround = sleepingAnimation();
            sleepyHead = 0.7F;
        } else {
            onGround = standingAnimation();
        }

        this.neck.rotationPointY = onGround + ((EnhancedSheep)entitylivingbaseIn).getHeadRotationPointY(partialTickTime) * 9.0F;
        this.headRotationAngleX = sleepyHead + ((EnhancedSheep)entitylivingbaseIn).getHeadRotationAngleX(partialTickTime);
    }


    @Override
    public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        SheepModelData sheepModelData = getSheepModelData();
        List<String> unrenderedModels = new ArrayList<>();

        float age = 1.0F;
        if (!(sheepModelData.birthTime == null) && !sheepModelData.birthTime.equals("") && !sheepModelData.birthTime.equals("0")) {
            int ageTime = (int)(((WorldInfo)((ClientWorld)entityIn.world).getWorldInfo()).getGameTime() - Long.parseLong(sheepModelData.birthTime));
            if (ageTime <= 75000) {
                age = ageTime/75000.0F;
            }
        }

        if (!sheepModelData.sleeping) {
            this.neck.rotateAngleX = headPitch * 0.017453292F;
            this.neck.rotateAngleY = netHeadYaw * 0.017453292F;

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


        setHornRotations(sheepModelData, unrenderedModels, entityIn);

        sheepModelData.unrenderedModels = unrenderedModels;

    }

    private void setHornRotations(SheepModelData sheepModelData, List<String> unrenderedModels, T entityIn) {

        //TODO add size variation
        int lengthL = 0;
        int lengthR = 0;

        Float[] hornGrowthL = {0.0F, -1.95F, -1.95F, -1.95F, -1.95F, -1.95F, -1.95F, -1.95F, -1.95F, -1.95F, -1.95F, -1.8F, -1.4F, -1.1F, -0.9F, -0.7876F, -0.675F, -0.5376F, -0.4F};
        Float[] hornGrowthR = hornGrowthL;

        int hornGrowth = 0;
        if (!(sheepModelData.birthTime == null) && !sheepModelData.birthTime.equals("") && !sheepModelData.birthTime.equals("0")) {
            int ageTime = (int)(sheepModelData.clientGameTime - Long.parseLong(sheepModelData.birthTime));
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

        if (sheepModelData.sheepGenes[36] == 1 || sheepModelData.sheepGenes[37] == 1) {
            if (Character.isLetter(sheepModelData.uuidArray[0]) || sheepModelData.uuidArray[0] - 48 >= 8) {
                //horns if "male"
                if (Character.isLetter(sheepModelData.uuidArray[2]) || sheepModelData.uuidArray[2] - 48 >= 3) {
                    polycerate = -0.001F;
                }
            } else {
                if (sheepModelData.uuidArray[2] - 48 <= 3) {
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

    public static void copyModelAngles(ModelRenderer source, ModelRenderer dest) {
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
        long clientGameTime = 0;
        List<String> unrenderedModels = new ArrayList<>();
    }

    private SheepModelData getSheepModelData() {
        if (this.currentSheep == null) {
            return null;
        }
        SheepModelData sheepModelData = sheepModelDataCache.get(this.currentSheep);
        return sheepModelData;
    }

    private SheepModelData getCreateSheepModelData(T enhancedSheep) {
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
            sheepModelData.clientGameTime = (((WorldInfo)((ClientWorld)enhancedSheep.world).getWorldInfo()).getGameTime());

            sheepModelData.unrenderedModels = new ArrayList<>();

            return sheepModelData;
        } else {
            SheepModelData sheepModelData = new SheepModelData();
            sheepModelData.sheepGenes = enhancedSheep.getSharedGenes();
            sheepModelData.coatlength = enhancedSheep.getCoatLength();
            sheepModelData.sleeping = enhancedSheep.isAnimalSleeping();
            sheepModelData.sheepStatus = enhancedSheep.getSheepStatus();
            sheepModelData.uuidArray = enhancedSheep.getCachedUniqueIdString().toCharArray();
            sheepModelData.birthTime = enhancedSheep.getBirthTime();
            sheepModelData.clientGameTime = (((WorldInfo)((ClientWorld)enhancedSheep.world).getWorldInfo()).getGameTime());


            sheepModelDataCache.put(enhancedSheep.getEntityId(), sheepModelData);

            return sheepModelData;
        }
    }

}
