package mokiyoki.enhancedanimals.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import mokiyoki.enhancedanimals.entity.EnhancedChicken;
import mokiyoki.enhancedanimals.items.CustomizableCollar;
import mokiyoki.enhancedanimals.model.util.ModelHelper;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.inventory.Inventory;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.storage.WorldInfo;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by saemon on 8/09/2018.
 */
@OnlyIn(Dist.CLIENT)
public class ModelEnhancedChicken<T extends EnhancedChicken> extends EntityModel<T> {

    private Map<Integer, ChickenModelData> chickenModelDataCache = new HashMap<>();
    private int clearCacheTimer = 0;

    private float headRotationAngleX;
    private boolean nesting = false; //TODO actually make some nesting ai
    private int pose = 0;
    private int mutation = 0;
    private float wingAngle = 0; //[between 0 - -1.5]

    //the below is all chicken parts
    private final ModelRenderer head;
    private final ModelRenderer headNakedNeck;
    private final ModelRenderer bigCrest;
    private final ModelRenderer smallCrest;
    private final ModelRenderer forwardCrest;
    private final ModelRenderer combXtraSmallSingle;
    private final ModelRenderer combSmallSingle;
    private final ModelRenderer combSingle;
    private final ModelRenderer combLargeSingle;
    private final ModelRenderer combXtraLargeSingle;
    private final ModelRenderer combSmallRose;
    private final ModelRenderer combRose;
    private final ModelRenderer combLargeRose;
    private final ModelRenderer combSmallRose2;
    private final ModelRenderer combRose2;
    private final ModelRenderer combLargeRose2;
    private final ModelRenderer combSmallPea;
    private final ModelRenderer combPea;
    private final ModelRenderer combLargePea;
    private final ModelRenderer combSmallWalnut;
    private final ModelRenderer combWalnut;
    private final ModelRenderer combLargeWalnut;
    private final ModelRenderer combV;
    private final ModelRenderer body;
    private final ModelRenderer bodyBig;
    private final ModelRenderer bodySmall;
    private final ModelRenderer xtraShortTail;
    private final ModelRenderer shortTail;
    private final ModelRenderer tail;
    private final ModelRenderer longTail;
    private final ModelRenderer xtraLongTail;
    private final ModelRenderer rightLeg;
    private final ModelRenderer rightLegExtend;
    private final ModelRenderer rightFeather1;
    private final ModelRenderer rightFeather1Extend;
//    private final ModelRenderer rightFeatherTall1;
    private final ModelRenderer rightFeather2;
    private final ModelRenderer rightFeather3;
    private final ModelRenderer leftLeg;
    private final ModelRenderer leftLegExtend;
    private final ModelRenderer leftFeather1;
    private final ModelRenderer leftFeather1Extend;
//    private final ModelRenderer leftFeatherTall1;
    private final ModelRenderer leftFeather2;
    private final ModelRenderer leftFeather3;
    private final ModelRenderer leftVultureHock;
    private final ModelRenderer rightVultureHock;
    private final ModelRenderer rightWing;
    private final ModelRenderer rightWingSmall;
    private final ModelRenderer leftWing;
    private final ModelRenderer leftWingSmall;
    private final ModelRenderer bill;
    private final ModelRenderer billChild;
    private final ModelRenderer smallChin;
    private final ModelRenderer chin;
    private final ModelRenderer bigChin;
    private final ModelRenderer beardChin;
    private final ModelRenderer peaChin;
    private final ModelRenderer beard;
    private final ModelRenderer beardNN;
    private final ModelRenderer earL;
    private final ModelRenderer earR;
    private final ModelRenderer earTuftL;
    private final ModelRenderer earTuftR;
    private final ModelRenderer earTuftHelper;
    private final ModelRenderer eyeLeft;
    private final ModelRenderer eyeRight;
    private final ModelRenderer collar;

    private Integer currentChicken = null;

    public ModelEnhancedChicken(float fluffyscale, boolean silkie) {
        this.textureWidth = 64;
        this.textureHeight = 64;

        int combRz = 3;

        this.head = new ModelRenderer(this, 12, 0);
        this.head.addBox(-2.0F, -6.0F, -2.0F, 4, 6, 3, 0.0F + fluffyscale);

        this.headNakedNeck = new ModelRenderer(this, 12, 0);
        this.headNakedNeck.addBox(-2.0F, -6.0F, -2.0F, 4, 4, 3, 0.0F + fluffyscale);
        this.headNakedNeck.setTextureOffset(0, 6);
        this.headNakedNeck.addBox(-1F, -2.0F, -4F + combRz, 2, 3, 2);

        this.bigCrest = new ModelRenderer(this, 1, 42);
        this.bigCrest.addBox(-2F, -9.0F, -5.5F + combRz, 4, 4, 4, 0.4F + fluffyscale);

        this.smallCrest = new ModelRenderer(this, 2, 43);
        this.smallCrest.addBox(-1.5F, -8.5F, -5F + combRz, 3, 3, 3, 0.1F + fluffyscale);

        this.forwardCrest = new ModelRenderer(this, 2, 43);
        this.forwardCrest.addBox(-1.5F, -8.0F, -6F + combRz, 3, 3, 3, 0.2F + fluffyscale);

        this.earL = new ModelRenderer(this, 1, 52);
        this.earR = new ModelRenderer(this, 1, 52);
        this.eyeLeft = new ModelRenderer(this, 23, 0);
        this.eyeRight = new ModelRenderer(this, 11, 0);
        if (!silkie) {
            this.earL.addBox(-2.2F, -6.0F, -2.2F, 1, 6, 3);
            this.earR.addBox(2.2F, -6.0F, -2.4F, 1, 6, 3);
            this.earR.mirror = true;

            this.eyeLeft.addBox(1.0F, -5.0F, -2.0F, 1, 1, 1, 0.01F);
            this.eyeRight.addBox(-2.0F, -5.0F, -2.0F, 1, 1, 1, 0.01F);
        }

        this.earTuftL = new ModelRenderer(this, 12, 47);
        this.earTuftL.addBox(-2.0F, 0.0F, 0.0F, 2, 1, 3 + fluffyscale);
        this.earTuftL.setRotationPoint(-1.5F, -4.5F, -1.0F);

        this.earTuftR = new ModelRenderer(this, 12, 47);
        this.earTuftR.addBox(0.0F, 0.0F, 0.0F, 2, 1, 3 + fluffyscale);
        this.earTuftR.setRotationPoint(1.5F, -4.5F, -1.0F);
        this.earR.mirror = true;

        int combSz = 3;


        this.combXtraSmallSingle = new ModelRenderer(this, 0, 0);
        this.combSmallSingle = new ModelRenderer(this, 0, 0);
        this.combSingle = new ModelRenderer(this, 0, 0);
        this.combLargeSingle = new ModelRenderer(this, 0, 0);
        this.combXtraLargeSingle = new ModelRenderer(this, 0, 0);
        this.combSmallRose = new ModelRenderer(this, 0, 0);
        this.combRose = new ModelRenderer(this, 0, 0);
        this.combLargeRose = new ModelRenderer(this, 0, 0);
        this.combSmallRose2 = new ModelRenderer(this, 0, 0);
        this.combRose2 = new ModelRenderer(this, 0, 0);
        this.combLargeRose2 = new ModelRenderer(this, 0, 0);
        this.combSmallPea = new ModelRenderer(this, 0, 0);
        this.combV = new ModelRenderer(this, 0, 0);
        this.combPea = new ModelRenderer(this, 0, 0);
        this.combLargePea = new ModelRenderer(this, 0, 0);
        this.combSmallWalnut = new ModelRenderer(this, 0, 0);
        this.combWalnut = new ModelRenderer(this, 0, 0);
        this.combLargeWalnut = new ModelRenderer(this, 0, 0);

        if (!silkie) {
            this.combXtraSmallSingle.addBox(-0.5F, -6.5F, (-5.5F + combSz), 1, 2, 1, -0.25F);
            this.combXtraSmallSingle.addBox(-0.5F, -7.0F, (-5F + combSz), 1, 2, 1, -0.25F);
            this.combXtraSmallSingle.addBox(-0.5F, -6.75F, (-4.5F + combSz), 1, 1, 1, -0.25F);
            this.combXtraSmallSingle.addBox(-0.5F, -7.25F, (-4F + combSz), 1, 2, 1, -0.25F);
            this.combXtraSmallSingle.addBox(-0.5F, -6.75F, (-3.5F + combSz), 1, 1, 1, -0.25F);
            this.combXtraSmallSingle.addBox(-0.5F, -7.0F, (-3.25F + combSz), 1, 1, 1, -0.25F);

            this.combSmallSingle.addBox(-0.5F, -6.75F, -2.25F, 1, 2, 1, -0.125F);
            this.combSmallSingle.addBox(-0.5F, -7.5F, -1.75F, 1, 1, 1, -0.125F);
            this.combSmallSingle.addBox(-0.5F, -6.75F, -1.5F, 1, 1, 2, -0.125F);
            this.combSmallSingle.addBox(-0.5F, -7.5F, -0.5F, 1, 1, 1, -0.125F);
            this.combSmallSingle.addBox(-0.5F, -6.75F, 0F, 1, 1, 1, -0.125F);
            this.combSmallSingle.addBox(-0.5F, -7F, 0.75F, 1, 1, 1, -0.125F);

            this.combSingle.addBox(-0.5F, -6.0F, (-6F + combSz), 1, 1, 1);
            this.combSingle.addBox(-0.5F, -6.5F, (-6F + combSz), 1, 1, 1);
            this.combSingle.addBox(-0.5F, -7.5F, (-5F + combSz), 1, 2, 1);
            this.combSingle.addBox(-0.5F, -7.0F, (-4F + combSz), 1, 1, 1);
            this.combSingle.addBox(-0.5F, -7.5F, (-3F + combSz), 1, 2, 1);
            this.combSingle.addBox(-0.5F, -6.5F, (-2F + combSz), 1, 1, 1);

            this.combLargeSingle.addBox(-0.5F, -7.5F, (-6.5F + combSz), 1, 1, 1);
            this.combLargeSingle.addBox(-0.5F, -6.5F, (-6F + combSz), 1, 2, 1);
            this.combLargeSingle.addBox(-0.5F, -8.5F, (-5F + combSz), 1, 3, 1);
            this.combLargeSingle.addBox(-0.5F, -7.5F, (-4F + combSz), 1, 2, 1);
            this.combLargeSingle.addBox(-0.5F, -9.0F, (-3F + combSz), 1, 3, 1);
            this.combLargeSingle.addBox(-0.5F, -7.5F, (-2F + combSz), 1, 2, 1);
            this.combLargeSingle.addBox(-0.5F, -8.5F, (-1F + combSz), 1, 3, 1);
            this.combLargeSingle.addBox(-0.5F, -7.0F, (0F + combSz), 1, 1, 1);

            this.combXtraLargeSingle.addBox(-0.5F, -8.5F, (-6.5F + combSz), 1, 2, 1);
            this.combXtraLargeSingle.addBox(-0.5F, -7.5F, (-6F + combSz), 1, 3, 1);
            this.combXtraLargeSingle.addBox(-0.5F, -10.0F, (-5F + combSz), 1, 4, 1);
            this.combXtraLargeSingle.addBox(-0.5F, -8.5F, (-4F + combSz), 1, 3, 1);
            this.combXtraLargeSingle.addBox(-0.5F, -9.5F, (-3F + combSz), 1, 4, 1);
            this.combXtraLargeSingle.addBox(-0.5F, -10.5F, (-2.5F + combSz), 1, 1, 1);
            this.combXtraLargeSingle.addBox(-0.5F, -8.5F, (-2F + combSz), 1, 3, 1);
            this.combXtraLargeSingle.addBox(-0.5F, -9.5F, (-1F + combSz), 1, 4, 1);
            this.combXtraLargeSingle.addBox(-0.5F, -10.0F, (-0.5F + combSz), 1, 1, 1);
            this.combXtraLargeSingle.addBox(-0.5F, -8.0F, (0F + combSz), 1, 2, 1);
            this.combXtraLargeSingle.addBox(-0.5F, -8.5F, (1F + combSz), 1, 2, 1);

            this.combSmallRose.addBox(-1F, -6.25F, (-5.75F + combRz), 2, 2, 1, -0.25F);
            this.combSmallRose.addBox(-0.5F, -7F, (-5.25F + combRz), 1, 2, 1, -0.1F);
            this.combSmallRose.addBox(-0.5F, -7.4F, (-4.75F + combRz), 1, 1, 1, -0.25F);

            this.combRose.addBox(-0.5F, -6.0F, (-6F + combRz), 1, 1, 1, 0.5F);
            this.combRose.addBox(-0.5F, -7.0F, (-5F + combRz), 1, 1, 1, 0.25F);
            this.combRose.addBox(-0.5F, -8.0F, (-4F + combRz), 1, 1, 1);

            this.combLargeRose.addBox(-1.0F, -6.0F, -3.0F, 2, 2, 1);
            this.combLargeRose.addBox(-0.5F, -7.0F, -2.5F, 1, 1, 2, 0.2F);
            this.combLargeRose.addBox(-0.5F, -8.0F, -0.5F, 1, 2, 1);
            this.combLargeRose.addBox(-0.5F, -9.0F, -0.0F, 1, 1, 1);

            this.combSmallRose2.addBox(-0.5F, -6.0F, -2.5F, 1, 1, 1, 0.1F);
            this.combSmallRose2.addBox(-0.5F, -6.5F, -2.0F, 1, 1, 1);
            this.combSmallRose2.addBox(-0.5F, -6.4F, -1F, 1, 1, 1);

            this.combRose2.addBox(-0.5F, -6.0F, (-6F + combRz), 1, 1, 1, 0.5F);
            this.combRose2.addBox(-0.5F, -7.0F, (-5F + combRz), 1, 1, 1, 0.25F);
            this.combRose2.addBox(-0.5F, -7.0F, (-4F + combRz), 1, 1, 1);

            this.combLargeRose2.addBox(-1.0F, -6.0F, -3.0F, 2, 2, 1);
            this.combLargeRose2.addBox(-1.0F, -6.75F, -2.5F, 2, 1, 2);
            this.combLargeRose2.addBox(-0.5F, -6.5F, -0.5F, 1, 1, 1, 0.2F);

            int combPy = -15;
            int combPz = 3;
            this.combSmallPea.addBox(-0.5F, -6F, -2.5F, 1, 1, 1, -0.25F);
            this.combSmallPea.addBox(-0.5F, -6.5F, -2.5F, 1, 1, 1, -0.125F);
            this.combSmallPea.addBox(-0.5F, -6.5F, -1.9F, 1, 1, 1, -0.25F);

            this.combPea.addBox(-0.5F, (9F + combPy), (-6F + combPz), 1, 1, 2, -0.2F);
            this.combPea.addBox(-0.5F, (8.5F + combPy), (-5.5F + combPz), 1, 1, 1);
            this.combPea.addBox(-0.5F, (8F + combPy), (-5F + combPz), 1, 2, 1, -0.2F);

            this.combLargePea.addBox(-1.0F, -5.75F, -2.25F, 2, 1, 1);
            this.combLargePea.addBox(-0.5F, -6.75F, -2.4F, 1, 2, 2);
            this.combLargePea.setTextureOffset(0, 14);
            this.combLargePea.addBox(0.5F, -6.75F, -2.4F, 1, 2, 2);
            this.combLargePea.addBox(-0.5F, -7F, -1.1F, 1, 1, 1);

            this.combSmallWalnut.addBox(-0.5F, (8.5F + combPy), (-5.5F + combPz), 1, 1, 1, -0.25F);

            this.combWalnut.addBox(-0.5F, (8.5F + combPy), (-5.5F + combPz), 1, 1, 1);

            this.combLargeWalnut.addBox(-1F, (8F + combPy), (-5.5F + combPz), 2, 2, 1, -0.125F);
            this.combLargeWalnut.addBox(-0.5F, (8.5F + combPy), (-5F + combPz), 1, 1, 1);

            this.combV.addBox(-0.5F, (8.5F + -15), (-5.5F + combRz), 1, 1, 1);
            this.combV.addBox(0F, -7.0F, (-5.25F + combRz), 1, 1, 1, -0.2F);
            this.combV.addBox(-1F, -7.0F, (-5.25F + combRz), 1, 1, 1, -0.2F);
            this.combV.addBox(.1F, (7.7F + -15), (-5F + combRz), 1, 1, 1, -0.3F);
            this.combV.addBox(-1.1F, (7.7F + -15), (-5F + combRz), 1, 1, 1, -0.3F);
        }

        this.body = new ModelRenderer(this, 22, 10);
        this.body.addBox(-3F, -6F, -4F, 6, 6, 8 + fluffyscale);
        this.body.setRotationPoint(0F, 0F, 1F);

        this.bodyBig = new ModelRenderer(this, 22, 10);
        this.bodyBig.addBox(-3F, -6F, -4F, 6, 6, 8, 0.5F + fluffyscale);
        this.bodyBig.setRotationPoint(0F, 0F, 1F);

        this.bodySmall = new ModelRenderer(this, 22, 10);
        this.bodySmall.addBox(-3F, -6F, -4F, 6, 6, 8, -0.5F + fluffyscale);
        this.bodySmall.setRotationPoint(0F, 0F, 1F);

        this.xtraShortTail = new ModelRenderer(this, 36, 10);
        this.xtraShortTail.addBox(-0.5F, 12F, 3F, 1, 4, 3 + fluffyscale);
        this.xtraShortTail.setTextureOffset(37, 11);
        this.xtraShortTail.addBox(-0.5F, 11F, 4F, 1, 1, 2 + fluffyscale);

        this.shortTail = new ModelRenderer(this, 34, 11);
        this.shortTail.addBox(-0.5F, 12F, 3F, 1, 4, 4 + fluffyscale);
        this.shortTail.setTextureOffset(36, 11);
        this.shortTail.addBox(-0.5F, 11F, 4F, 1, 1, 3 + fluffyscale);

        this.tail = new ModelRenderer(this, 30, 0);
        this.tail.addBox(-0.5F, -7F, 3F, 1, 4, 5 + fluffyscale);
        this.tail.setTextureOffset(44, 3);
        this.tail.addBox(-0.5F, -8F, 4F, 1, 1, 4 + fluffyscale);

        this.longTail = new ModelRenderer(this, 34, 10);
        this.longTail.addBox(-0.5F, 12F, 3F, 1, 4, 5 + fluffyscale);
        this.longTail.setTextureOffset(35, 11);
        this.longTail.addBox(-0.5F, 11F, 4F, 1, 1, 4 + fluffyscale);
        this.longTail.setTextureOffset(38, 13);
        this.longTail.addBox(-0.5F, 12F, 8F, 1, 3, 2 + fluffyscale);
        this.longTail.setTextureOffset(39, 15);
        this.longTail.addBox(-0.5F, 13F, 10F, 1, 1, 1 + fluffyscale);

        this.xtraLongTail = new ModelRenderer(this, 34, 10);
        this.xtraLongTail.addBox(-0.5F, 12F, 4F, 1, 4, 5 + fluffyscale);
        this.xtraLongTail.setTextureOffset(35, 11);
        this.xtraLongTail.addBox(-0.5F, 11F, 5F, 1, 1, 4 + fluffyscale);
        this.xtraLongTail.setTextureOffset(38, 13);
        this.xtraLongTail.addBox(-0.5F, 12F, 9F, 1, 3, 2 + fluffyscale);
        this.xtraLongTail.addBox(-0.5F, 15F, 9F, 1, 3, 3 + fluffyscale);
        this.xtraLongTail.setTextureOffset(39, 15);
        this.xtraLongTail.addBox(-0.5F, 18F, 11F, 1, 1, 1 + fluffyscale);
        this.xtraLongTail.addBox(-0.5F, 16F, 5F, 1, 1, 1 + fluffyscale);

        this.leftLeg = new ModelRenderer(this, 8, 18);
        this.leftLegExtend = new ModelRenderer(this, 8, 18);
        this.rightLeg = new ModelRenderer(this, 8, 18);
        this.rightLegExtend = new ModelRenderer(this, 8, 18);
        if (!silkie) {
            this.leftLeg.addBox(-2F, 3.5F, 1F, 1, 5, 1);
            this.leftLeg.setTextureOffset(0, 22);
            this.leftLeg.addBox(-3F, 8F, -1F, 3, 1, 2);
            this.leftLeg.setTextureOffset(2, 23);
            this.leftLeg.addBox(-2F, 8F, -2F, 1, 1, 1);

            this.leftLegExtend.addBox(-2F, 1.5F, 1F, 1, 2, 1);

            this.rightLeg.addBox(1F, 3.5F, 1F, 1, 5, 1);
            this.rightLeg.setTextureOffset(0, 22);
            this.rightLeg.addBox(0F, 8F, -1F, 3, 1, 2);
            this.rightLeg.setTextureOffset(3, 23);
            this.rightLeg.addBox(1F, 8F, -2F, 1, 1, 1);

            this.rightLegExtend.addBox(1F, 1.5F, 1F, 1, 2, 1);
        }

        this.rightFeather1 = new ModelRenderer(this, 1, 35);
        this.rightFeather1.addBox(1.1F, 4F, 0F, 2, 3, 3 + fluffyscale);

        this.rightFeather1Extend = new ModelRenderer(this, 1, 35);
        this.rightFeather1Extend.addBox(1.1F, 2F, 0F, 2, 2, 3 + fluffyscale);

        this.leftFeather1 = new ModelRenderer(this, 1, 35);
        this.leftFeather1.mirror = true;
        this.leftFeather1.addBox(-3.1F, 4F, 0F, 2, 3, 3 + fluffyscale);

        this.leftFeather1Extend = new ModelRenderer(this, 1, 35);
        this.leftFeather1Extend.mirror = true;
        this.leftFeather1Extend.addBox(-3.1F, 2F, 0F, 2, 2, 3 + fluffyscale);

//        this.rightFeatherTall1 = new ModelRenderer(this,1,35);
//        this.rightFeatherTall1.addBox(1.1F, 3F, 0F, 2, 1, 3);
//        this.rightFeatherTall1.addBox(1.1F, 4F, 0F, 2, 3, 3);
//
//        this.leftFeatherTall1 = new ModelRenderer(this,1,35);
//        this.leftFeatherTall1.mirror = true;
//        this.leftFeatherTall1.addBox(-3.1F, 3F, 0F, 2, 1, 3);
//        this.leftFeatherTall1.addBox(-3.1F, 4F, 0F, 2, 3, 3);

        this.rightFeather2 = new ModelRenderer(this, 12, 34);
        this.rightFeather2.addBox(1.5F, 7F, -2.5F, 2, 2, 5 + fluffyscale);

        this.leftFeather2 = new ModelRenderer(this, 12, 34);
        this.leftFeather2.mirror = true;
        this.leftFeather2.addBox(-3.5F, 7F, -2.5F, 2, 2, 5 + fluffyscale);

        this.rightFeather3 = new ModelRenderer(this, 28, 36);
        this.rightFeather3.addBox(3.5F, 8.9F, -2.5F, 4, 1, 5 + fluffyscale);

        this.leftFeather3 = new ModelRenderer(this, 28, 36);
        this.leftFeather3.mirror = true;
        this.leftFeather3.addBox(-7.5F, 8.9F, -2.5F, 4, 1, 5 + fluffyscale);

        this.rightVultureHock = new ModelRenderer(this, 33, 32);
        this.rightVultureHock.mirror = true;
        this.rightVultureHock.addBox(2.5F, 3.0F, 2.5F, 1, 3, 4, -0.2F + fluffyscale);

        this.leftVultureHock = new ModelRenderer(this, 33, 32);
        this.leftVultureHock.addBox(-3.5F, 3.0F, 2.5F, 1, 3, 4, -0.2F + fluffyscale);

        this.rightWing = new ModelRenderer(this, 13, 19);
        this.rightWing.addBox(0F, 0F, -3.0F, 1, 4, 6 + fluffyscale);
        this.rightWing.setRotationPoint(-4.0F, 13.0F, 1.0F);

        this.leftWing = new ModelRenderer(this, 45, 19);
        this.leftWing.addBox(-1.0F, 0F, -3.0F, 1, 4, 6 + fluffyscale);
        this.leftWing.setRotationPoint(4.0F, 13.0F, 1.0F);

        this.rightWingSmall = new ModelRenderer(this, 14, 20);
        this.rightWingSmall.addBox(0F, 0F, -3.0F, 1, 3, 5 + fluffyscale);
        this.rightWingSmall.setRotationPoint(-4.0F, 13.0F, 1.0F);

        this.leftWingSmall = new ModelRenderer(this, 46, 20);
        this.leftWingSmall.addBox(-1.0F, 0F, -3.0F, 1, 3, 5 + fluffyscale);
        this.leftWingSmall.setRotationPoint(4.0F, 13.0F, 1.0F);

        this.bill = new ModelRenderer(this, 0, 18);
        this.billChild = new ModelRenderer(this, 0, 18);
        this.smallChin = new ModelRenderer(this, 14, 10);
        this.chin = new ModelRenderer(this, 14, 10);
        this.bigChin = new ModelRenderer(this, 14, 10);
        this.peaChin = new ModelRenderer(this, 14, 10);
        this.beardChin = new ModelRenderer(this, 14, 10);
        if (!silkie) {
            this.bill.addBox(-1.0F, -4.0F, -4.0F, 2, 2, 2, 0.0F);
            this.bill.setRotationPoint(0.0F, 15.0F, -4.0F);
            this.billChild.addBox(-1.0F, -4.0F, -3F, 2, 2, 2, 0.0F);
            this.billChild.setRotationPoint(0.0F, 15.0F, -4.0F);
            this.smallChin.addBox(-1.0F, -2.0F, -3.0F, 2, 1, 2, 0.0F);
            this.chin.addBox(-1.0F, -2.0F, -3.0F, 2, 2, 1, 0.0F);
            this.bigChin.addBox(-1.0F, -2.0F, -3.0F, 2, 3, 1, 0.0F);
            this.peaChin.addBox(-0.5F, -2.0F, -3.0F, 1, 1, 2, 0.0F);
            this.beardChin.addBox(-1.5F, -2.5F, -3.0F, 3, 1, 1, 0.0F);
        }

        this.beard = new ModelRenderer(this,2,29);
        this.beard.addBox(-3F, -4F, -2F, 1, 2, 2 + fluffyscale);
        this.beard.addBox(-2.5F, -3F, -2.75F, 2, 2, 2 + fluffyscale);
        this.beard.setTextureOffset(3,30);
        this.beard.addBox(-1F, -2.25F, -2.9F, 2, 2, 2 + fluffyscale);
        this.beard.addBox(-0.5F, -2F, -3.75F, 1, 1, 1 + fluffyscale);
        this.beard.setTextureOffset(2,29);
        this.beard.addBox(2F, -4F, -2F, 1, 2, 2 + fluffyscale);
        this.beard.addBox(0.5F, -3F, -2.75F, 2, 2, 2 + fluffyscale);

        this.beardNN = new ModelRenderer(this,2,29);
        this.beardNN.addBox(-3F, -4F, -2F, 1, 2, 2 + fluffyscale);
        this.beardNN.addBox(-2F, -3F, -2.75F, 2, 2, 2 + fluffyscale);
        this.beardNN.setTextureOffset(3,30);
        this.beardNN.addBox(-0.5F, -2F, -3.5F, 1, 1, 1 + fluffyscale);
        this.beardNN.setTextureOffset(2,29);
        this.beardNN.addBox(2F, -4F, -2F, 1, 2, 2 + fluffyscale);
        this.beardNN.addBox(0F, -3F, -2.75F, 2, 2, 2 + fluffyscale);

        this.earTuftHelper = new ModelRenderer(this, 47, 44);

        this.earTuftHelper.addChild(this.earTuftL);
        this.earTuftHelper.addChild(this.earTuftR);

        this.headNakedNeck.addChild(this.eyeLeft);
        this.headNakedNeck.addChild(this.eyeRight);
        this.head.addChild(this.eyeLeft);
        this.head.addChild(this.eyeRight);

        this.textureWidth=160;
        this.textureHeight=160;
        this.collar = new ModelRenderer(this, 0, 155);
        this.collar.addBox(-2.5F, -1.0F, -3.0F, 5,  1, 4, 0.001F);
        this.collar.setTextureOffset(30, 156);
        this.collar.addBox(0.0F, -1.3333F, -4.0F, 0,  2, 2);
        this.collar.setTextureOffset(18, 154);
        this.collar.addBox(-1.5F, -1.0F, -5.0F, 3, 3, 3, -1.0F);
        this.head.addChild(this.collar);
        this.headNakedNeck.addChild(this.collar);
    }

    private void setRotationOffset(ModelRenderer renderer, float x, float y, float z) {
        renderer.rotateAngleX = x;
        renderer.rotateAngleY = y;
        renderer.rotateAngleZ = z;
    }

    @Override
    public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {

        ChickenModelData chickenModelData = getChickenModelData();

        Boolean roosting = chickenModelData.sleeping;
        int blink = chickenModelData.blink;

        int[] genes = chickenModelData.chickenGenes;
        boolean nakedNeck = false;
        boolean longHockFeathers = false;
        int crest = 0; // [0, 1, 2, 3]          [none, small, forward, big]
        int fFeet = 0; // [0, 1, 2, 3]          [none, 1, 1&2, 1&2&3]
        int comb = 0;  // [0, 1, 2, 3, 4, 5, 6] [none, single, rose, rose2, pea, walnut, v]
//        int chin = 0;  // [0, 1, 2, 3]    [none, waddles, peacomb waddles, beard waddles]
        int beard = 0; // [0, 1, 2 ]            [none, beard, naked neck beard]
        float size = 1;  // [] [
        int cSize = 0; // [0, 1, 2, 3, 4]
        int wSize = 0; // [0, 1, 2, 3, 4]
        int wingSize = 0; // [0, 1, 2]
        int bodyType = 0; //[-1, 0, 1]  [small, normal, big]
        boolean earTuft = false;
        boolean longLegs = false;

        if (genes != null) {

            if (genes[52] == 1 || genes[53] == 1) {
                nakedNeck = true;
            }

            if (genes[50] == 1 && genes[51] == 1) {
                if (genes[48] == 1 || genes[49] == 1) {
                    if (genes[46] == 3 && genes[47] == 3) {
                        //peacomb
                        comb = 4;
                    } else {
                        //walnut
                        comb = 5;
                    }
                } else {
                    if (genes[46] == 3 && genes[47] == 3) {
                        //single comb
                        comb = 1;
                    } else if (genes[46] == 1 || genes[47] == 1) {
                        //rose comb
                        comb = 2;
                    } else {
                        //rose comb2
                        comb = 3;
                    }
                }
            } else {
                if (genes[46] == 3 && genes[47] == 3 && genes[48] == 2 && genes[49] == 2) {
                    //v comb
                    comb = 6;
                } else {
                    if (genes[48] == 2 && genes[49] == 2) {
                        //only waddles
                    }
                }
            }

            //bearded
            if (genes[56] == 1 || genes[57] == 1) {
                if (genes[52] == 1 || genes[53] == 1) {
                    beard = 2;
                } else {
                    beard = 1;
                }
            }

            //crestedness
            if ((genes[54] == 2 || genes[55] == 2) && (genes[54] != 1 && genes[55] != 1)) {
                crest = 2;
            } else if (genes[54] == 1 || genes[55] == 1) {
                if (genes[54] == genes[55]) {
                    if (genes[82] == 1 && genes[83] == 1) {
                        crest = 1;
                    } else {
                        crest = 3;
                    }

                } else {
                    crest = 1;
                }
            }

            //feather feets
            if (genes[58] == 1 || genes[59] == 1) {
                fFeet = fFeet + 1;
            } else if (genes[58] == 2 || genes[59] == 2) {
                fFeet = fFeet + 2;
            }
            if (genes[60] == 1 || genes[61] == 1) {
                fFeet = fFeet + 1;
            }

            //chicken size [ 0.5 to 1 ] genes 74-79 & 7 & 8
            if(genes[74] == 1){
                size = size - 0.05F;
            }else if(genes[74] == 2){
                size = size - 0.025F;
            }
            if(genes[75] == 1){
                size = size - 0.05F;
            }else if(genes[75] == 2){
                size = size - 0.025F;
            }

            if(genes[76] == 1 || genes[77] == 1){
                size = size - 0.05F;
            }else if(genes[76] == 3 && genes[77] == 3){
                size = size - 0.1F;
            }

            if(genes[78] == 1 || genes[79] == 1){
                size = size * 0.94F;
            }

            if(genes[7] == 2){
                size = size * 0.9F;
            }

            if(genes[8] == 2){
                size = size * 0.75F;
            }

            //comb size [80 and 81 small / 82 and 83 large]
            if (genes[80] == 2) {
                cSize = cSize + 1;
            }
            if (genes[81] == 2) {
                cSize = cSize + 1;
            }
            if (genes[82] == 1) {
                cSize = cSize + 1;
            }
            if (genes[83] == 1) {
                cSize = cSize + 1;
            }

            wSize = cSize;

            if (genes[84] == 1 && genes[85] == 1 && wSize > 0) {
                wSize = wSize - 1;
            }

            if (genes[90] == 1 || genes[91] == 1){
                wingSize = 2;
            } else if (genes[92] == 1 || genes[93] == 1){
                wingSize = 1;
            }


            if (genes[146] == 2 && genes[147] == 2) {
                if (genes[148] == 2 && genes[149] == 2) {
                    //normal body
                    bodyType = 0;
                } else {
                    //big body
                    bodyType = 1;
                }
            } else if (genes[148] == 2 && genes[149] == 2) {
                if (genes[146] == 2 || genes[147] == 2) {
                    //normal body
                    bodyType = 0;
                } else {
                    //small body
                    longHockFeathers = true;
                    bodyType = -1;
                }
            } else {
                //normal body
                bodyType = 0;
            }

            //ear tufts
            if (genes[150] == 2 || genes[151] == 2) {
                earTuft = true;
            } else {
                earTuft = false;
            }

            if (!roosting) {
                //long legs
                if (genes[168] == 2 || genes[169] == 2) {
                    longLegs = true;
                } else {
                    longLegs = false;
                }
            }

        }


        wingAngle = 0;   //do not use this to debug wingAngle scroll down
//
//        if(genes[88] == 2){
//            this.wingAngle = this.wingAngle + 0.1F;
//        }else if(genes[88] == 3) {
//            this.wingAngle = this.wingAngle + 0.15F;
//        }else{
//
//        }
//        if(genes[89] == 2){
//            this.wingAngle = this.wingAngle + 0.1F;
//        }else if(genes[89] == 3) {
//            this.wingAngle = this.wingAngle + 0.15F;
//        }
//        if(genes[86] == 2){
//            this.wingAngle = this.wingAngle + 0.1F;
//        }else if(genes[86] == 3) {
//            this.wingAngle = this.wingAngle + 0.15F;
//        }
//        if(genes[87] == 2){
//            this.wingAngle = this.wingAngle + 0.1F;
//        }else if(genes[87] == 3) {
//            this.wingAngle = this.wingAngle + 0.15F;
//        }
//
//        if(genes[94] == 2 && genes[95] == 2){
//            this.wingAngle = this.wingAngle * 1.2F;
//        }else if(genes[94] == 3 && genes[95] == 3) {
//            this.wingAngle = this.wingAngle * 1.5F;
//        }else if(genes[94] != 1 || genes[95] != 1) {
//            this.wingAngle = this.wingAngle * 1.1F;
//        }
//
//        if(genes[96] == 2 && genes[97] == 2){
//            this.wingAngle = this.wingAngle * 1.2F;
//        }else if(genes[96] == 3 && genes[97] == 3) {
//            wingAngle = this.wingAngle * 1.5F;
//        }else if(genes[96] != 1 || genes[97] != 1) {
//            this.wingAngle = this.wingAngle * 1.1F;
//        }



//        fFeet = 3; // these are to set everything to true
//        beard = 1;

//        wingAngle = 1.5F;   // used for debugging wingAngle  [ 0 to 1.5 ]

        float age = 1.0F;
        if (!(chickenModelData.birthTime == null) && !chickenModelData.birthTime.equals("") && !chickenModelData.birthTime.equals("0")) {
            int ageTime = (int)(chickenModelData.clientGameTime - Long.parseLong(chickenModelData.birthTime));
            if (ageTime <= 70000) {
                age = ageTime < 0 ? 0 : ageTime/70000.0F;
            }
        }

        float finalChickenSize = (( 3.0F * size * age) + size) / 4.0F;

        matrixStackIn.push();
        matrixStackIn.scale(finalChickenSize, finalChickenSize, finalChickenSize);
        matrixStackIn.translate(0.0F, -1.5F + 1.5F/finalChickenSize, 0.0F);
        if (blink == 0 || blink >= 6) {
            this.eyeLeft.showModel = true;
            this.eyeRight.showModel = true;
        } else {
            this.eyeLeft.showModel = false;
            this.eyeRight.showModel = false;
        }

        if (chickenModelData.collar) {
            this.collar.showModel = true;
        } else {
            this.collar.showModel = false;
        }

        if (nakedNeck) {
            this.headNakedNeck.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        } else {
            this.head.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        }

        if (beard == 1) {
            this.beard.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        } else if (beard == 2) {
            this.beardNN.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        }

        if (age > 0.3F) {

            if ((wSize == 2 || wSize == 3 || wSize == 4)) {

                if (beard != 0 && (comb == 1 || comb == 2 || comb == 3)) {
                    this.beardChin.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                }

                if (beard == 0) {
                    if (comb == 4 || comb == 5) {
                        this.peaChin.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                    }
                }
            }
            if (beard == 0 && (comb != 4 && comb != 5)){
                if (wSize == 4 || wSize == 3){
                    this.bigChin.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                } else if (wSize == 2 || wSize == 1){
                    this.chin.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                }else{
                    this.smallChin.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                }
            }

                if ((comb == 1 && crest == 0) || (comb == 1 && cSize >= 3)) {
                    if (cSize == 0) {
                        this.combXtraSmallSingle.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                    } else if (cSize == 1) {
                        this.combSmallSingle.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                    } else if (cSize == 2) {
                        //TODO change this back to combSingle
                        this.combSingle.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                    } else if (cSize == 3) {
                        this.combLargeSingle.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                    } else {
                        this.combXtraLargeSingle.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                    }
                } else if (comb == 2 && crest == 0) {
                    if (cSize == 0) {
                        this.combSmallRose.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                    } else if (cSize == 1 || cSize == 2 || cSize == 3) {
                        this.combRose.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                    } else {
                        this.combLargeRose.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                    }
                } else if (comb == 3) {
                    if (cSize == 0) {
                        this.combSmallRose2.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                    } else if (cSize == 1 || cSize == 2 || cSize == 3) {
                        this.combRose2.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                    } else {
                        this.combLargeRose2.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                    }
                } else if (comb == 4 || (comb == 1 && crest != 0)) {
                    if (cSize == 1 || cSize == 2) {
                        this.combSmallPea.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                    } else if (cSize == 3) {
                        this.combPea.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                    } else if (cSize == 4) {
                        this.combLargePea.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                    }
                } else if (comb == 5 || ((comb == 2 || comb == 3) && crest != 0)) {
                    if (cSize == 1 || cSize == 2) {
                        this.combSmallWalnut.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                    } else if (cSize == 3) {
                        this.combWalnut.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                    } else if (cSize == 4) {
                        this.combLargeWalnut.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                    }
                } else if (comb == 6) {
                    this.combV.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                }

                this.earL.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                this.earR.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);

            }

            if(crest == 1 || (crest != 0 && age > 0.5F)){
                this.smallCrest.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            }else if(crest == 2){
                this.forwardCrest.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            }else if(crest == 3){
                this.bigCrest.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            }

            if (genes != null) {
                if ((age > 0.15F || age >= 0.3333F) && (genes[72] == 1 && genes[73] == 1)) {
                    this.tail.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                }
            }

            if (wingSize == 2) {
                this.rightWing.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                this.leftWing.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            }else{
                this.rightWingSmall.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                this.leftWingSmall.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            }


            if (bodyType == -1) {
                this.bodySmall.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            } else if (bodyType == 0) {
                this.body.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            } else {
                this.bodyBig.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            }

            this.rightLeg.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            this.leftLeg.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            if (longLegs) {
                this.leftLegExtend.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                this.rightLegExtend.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            }

            if (fFeet >= 1) {
                if (!roosting && !nesting) {
                    this.leftFeather1.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                    this.rightFeather1.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                    if (longHockFeathers || longLegs) {
                        this.leftFeather1Extend.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                        this.rightFeather1Extend.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                    }
                }

                if (genes != null) {
                    if (genes[102] == 2 && genes[103] == 2){
                        this.leftVultureHock.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                        this.rightVultureHock.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                    }
                }

                if (fFeet >= 2) {
                    this.leftFeather2.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                    this.rightFeather2.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                    if (fFeet >= 3) {
                        this.leftFeather3.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                        this.rightFeather3.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                    }}}

            this.bill.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);

            if (earTuft) {
                this.earTuftHelper.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            }

        matrixStackIn.pop();
  }


    /**
     * Sets the model's various rotation angles. For bipeds, par1 and par2 are used for animating the movement of arms
     * and legs, where par1 represents the time(so that arms and legs swing back and forth) and par2 represents how
     * "far" arms and legs can swing at most.
     */
    @Override
    public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        ChickenModelData chickenModelData = getChickenModelData();
//        List<String> unrenderedModels = new ArrayList<>();

//        float bodyangle = 0.5F;
//        super.setRotationAngles(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
        if(this.pose == 1){
            this.head.rotationPointY = 22F;
        } else if (this.pose == 2){
            this.head.rotationPointY = 21F;
        } else{
            if(this.mutation == 1){
                this.head.rotationPointY = 18F;
            } else if(this.mutation == 2){
                this.head.rotationPointY = 17.5F;
            } else if(this.mutation == 3){
                this.head.rotationPointY = 17F;
            } else if(this.mutation == 4){
                this.head.rotationPointY = 14F;
            } else if(this.mutation == 5){
                this.head.rotationPointY = 13F;
            } else{
                this.head.rotationPointY = 15F;
            }
        }
        this.head.rotationPointZ = -3F;
        this.head.rotateAngleX = headPitch * 0.017453292F;
        this.head.rotateAngleY = netHeadYaw * 0.017453292F;

        ModelHelper.copyModelPositioning(head, headNakedNeck);

        ModelHelper.copyModelPositioning(head, bill);
        ModelHelper.copyModelPositioning(head, billChild);
        ModelHelper.copyModelPositioning(head, smallChin);
        ModelHelper.copyModelPositioning(head, chin);
        ModelHelper.copyModelPositioning(head, bigChin);
        ModelHelper.copyModelPositioning(head, beardChin);
        ModelHelper.copyModelPositioning(head, peaChin);

        ModelHelper.copyModelPositioning(head, smallCrest);
        ModelHelper.copyModelPositioning(head, bigCrest);
        ModelHelper.copyModelPositioning(head, forwardCrest);

        ModelHelper.copyModelPositioning(head, combXtraSmallSingle);
        ModelHelper.copyModelPositioning(head, combSmallSingle);
        ModelHelper.copyModelPositioning(head, combSingle);
        ModelHelper.copyModelPositioning(head, combLargeSingle);
        ModelHelper.copyModelPositioning(head, combXtraLargeSingle);
        ModelHelper.copyModelPositioning(head, combSmallRose);
        ModelHelper.copyModelPositioning(head, combRose);
        ModelHelper.copyModelPositioning(head, combLargeRose);
        ModelHelper.copyModelPositioning(head, combSmallRose2);
        ModelHelper.copyModelPositioning(head, combRose2);
        ModelHelper.copyModelPositioning(head, combLargeRose2);
        ModelHelper.copyModelPositioning(head, combSmallPea);
        ModelHelper.copyModelPositioning(head, combPea);
        ModelHelper.copyModelPositioning(head, combLargePea);
        ModelHelper.copyModelPositioning(head, combSmallWalnut);
        ModelHelper.copyModelPositioning(head, combWalnut);
        ModelHelper.copyModelPositioning(head, combLargeWalnut);
        ModelHelper.copyModelPositioning(head, combV);

        ModelHelper.copyModelPositioning(head, beard);
        ModelHelper.copyModelPositioning(head, beardNN);

        ModelHelper.copyModelPositioning(head, earL);
        ModelHelper.copyModelPositioning(head, earR);

        ModelHelper.copyModelPositioning(head, earTuftHelper);

        this.earTuftL.rotateAngleX = 1.4F;
        this.earTuftL.rotateAngleZ = -1.4F;
        this.earTuftR.rotateAngleX = 1.4F;
        this.earTuftR.rotateAngleZ = 1.4F;

        //leg stuff
        this.rightLeg.rotationPointY = 15F;
        this.leftLeg.rotationPointY = 15F;
        this.rightLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        this.leftLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
        ModelHelper.copyModelPositioning(rightLeg, rightFeather1);
        ModelHelper.copyModelPositioning(rightLeg, rightLegExtend);
        ModelHelper.copyModelPositioning(rightLeg, rightFeather1Extend);
        ModelHelper.copyModelPositioning(rightLeg, rightFeather2);
        ModelHelper.copyModelPositioning(rightLeg, rightFeather3);
        ModelHelper.copyModelPositioning(rightLeg, rightVultureHock);
        ModelHelper.copyModelPositioning(leftLeg, leftLegExtend);
        ModelHelper.copyModelPositioning(leftLeg, leftFeather1Extend);
        ModelHelper.copyModelPositioning(leftLeg, leftFeather1);
        ModelHelper.copyModelPositioning(leftLeg, leftFeather2);
        ModelHelper.copyModelPositioning(leftLeg, leftFeather3);
        ModelHelper.copyModelPositioning(leftLeg, leftVultureHock);

        //body angle
//        this.body.rotateAngleX = -bodyangle;

        //tail stuff
        ModelHelper.copyModelPositioning(body, bodyBig);
        ModelHelper.copyModelPositioning(body, bodySmall);
        ModelHelper.copyModelPositioning(body, tail);
        ModelHelper.copyModelPositioning(body, longTail);
        ModelHelper.copyModelPositioning(body, shortTail);
        ModelHelper.copyModelPositioning(body, xtraLongTail);
        ModelHelper.copyModelPositioning(body, xtraShortTail);

        //wing stuff

//        this.rightWing.rotationPointZ = -1;
//        this.leftWing.rotationPointZ = -1;
//        this.rightWing.rotationPointY = 13;
//        this.leftWing.rotationPointY = 13;

        this.rightWing.rotateAngleZ = ageInTicks;
        this.leftWing.rotateAngleZ = -ageInTicks;
        this.rightWingSmall.rotateAngleZ = ageInTicks;
        this.leftWingSmall.rotateAngleZ = -ageInTicks;

//        this.rightWing.rotateAngleX = -wingAngle - bodyangle ;
//        this.leftWing.rotateAngleX = -wingAngle - bodyangle;
//        this.rightWingSmall.rotateAngleX = -wingAngle - bodyangle;
//        this.leftWingSmall.rotateAngleX = -wingAngle - bodyangle;
    }

    @Override
    public void setLivingAnimations(T entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTickTime) {
        super.setLivingAnimations(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTickTime);
        ChickenModelData chickenModelData = getCreateChickenModelData(entitylivingbaseIn);
        this.currentChicken = entitylivingbaseIn.getEntityId();

        boolean roosting = chickenModelData.sleeping;

        int[] sharedGenes = chickenModelData.chickenGenes;

        this.rightLeg.rotationPointX = 0F;
        this.leftLeg.rotationPointX = 0F;
        this.rightLeg.rotationPointY = 0F;
        this.leftLeg.rotationPointY = 0F;

        float point = 19F;

        float wingMod = 0.0F;

        if (sharedGenes != null) {
            if (sharedGenes[148] == 2 && sharedGenes[149] == 2 && sharedGenes[146] != 2 && sharedGenes[147] != 2) {
                wingMod = 0.5F;
            }

            //genes variations
            if ((sharedGenes[70] == 2 || sharedGenes [71] == 2) && (!nesting && !roosting)) {

                if (sharedGenes[168] == 1 && sharedGenes[169] == 1) {
                    this.body.rotationPointY = 3F + point;
//            this.tail.rotationPointY = 3F;
                    this.rightWing.rotationPointY = 16.0F + wingMod;
                    this.leftWing.rotationPointY = 16.0F + wingMod;
                    this.rightWingSmall.rotationPointY = 16.0F + wingMod;
                    this.leftWingSmall.rotationPointY = 16.0F + wingMod;
                    this.mutation = 1;
                } else if (sharedGenes[168] == 2 && sharedGenes[169] == 2) {
                    this.body.rotationPointY = 2F + point;
//            this.tail.rotationPointY = 3F;
                    this.rightWing.rotationPointY = 15.0F + wingMod;
                    this.leftWing.rotationPointY = 15.0F + wingMod;
                    this.rightWingSmall.rotationPointY = 15.0F + wingMod;
                    this.leftWingSmall.rotationPointY = 15.0F + wingMod;
                    this.mutation = 3;
                } else {
                    this.body.rotationPointY = 2.5F + point;
//            this.tail.rotationPointY = 3F;
                    this.rightWing.rotationPointY = 15.5F + wingMod;
                    this.leftWing.rotationPointY = 15.5F + wingMod;
                    this.rightWingSmall.rotationPointY = 15.5F + wingMod;
                    this.leftWingSmall.rotationPointY = 15.5F + wingMod;
                    this.mutation = 2;
                }

            } else {
                if (sharedGenes[168] == 1 && sharedGenes[169] == 1) {
                    this.body.rotationPointY = 0F + point;
//            this.tail.rotationPointY = 0F;
                    this.rightWing.rotationPointY = 13.0F + wingMod;
                    this.leftWing.rotationPointY = 13.0F + wingMod;
                    this.rightWingSmall.rotationPointY = 13.0F + wingMod;
                    this.leftWingSmall.rotationPointY = 13.0F + wingMod;
                    this.mutation = 0;
                } else if (sharedGenes[168] == 2 && sharedGenes[169] == 2) {
                    this.body.rotationPointY = -2F + point;
//            this.tail.rotationPointY = 0F;
                    this.rightWing.rotationPointY = 11.0F + wingMod;
                    this.leftWing.rotationPointY = 11.0F + wingMod;
                    this.rightWingSmall.rotationPointY = 11.0F + wingMod;
                    this.leftWingSmall.rotationPointY = 11.0F + wingMod;
                    this.mutation = 5;
                } else {
                    this.body.rotationPointY = -1F + point;
//            this.tail.rotationPointY = -1F;
                    this.rightWing.rotationPointY = 12.0F + wingMod;
                    this.leftWing.rotationPointY = 12.0F + wingMod;
                    this.rightWingSmall.rotationPointY = 12.0F + wingMod;
                    this.leftWingSmall.rotationPointY = 12.0F + wingMod;
                    this.mutation = 4;
                }
            }
        }


        //behaviour animations
        //nesting "moves legs together to remove clipping"
        if(nesting){
            this.body.rotationPointY = 4.9F + point;
//            this.tail.rotationPointY = 5F;
            this.rightLeg.rotationPointX = this.rightLeg.rotationPointX - 0.1F;
            this.leftLeg.rotationPointX = this.leftLeg.rotationPointX + 0.1F;

            this.rightWing.rotationPointY = 19F + wingMod;
            this.leftWing.rotationPointY = 19F + wingMod;
            this.rightWingSmall.rotationPointY = 19F + wingMod;
            this.leftWingSmall.rotationPointY = 19F + wingMod;

            this.pose = 1;
        } else {
            if(roosting){
                this.body.rotationPointY = 4.9F + point;
//                this.tail.rotationPointY = 5F;
                this.rightLeg.rotationPointY = 1F;
                this.leftLeg.rotationPointY = 1F;
                this.rightLeg.rotationPointX = this.rightLeg.rotationPointX - 0.1F;
                this.leftLeg.rotationPointX = this.leftLeg.rotationPointX + 0.1F;

                this.rightWing.rotationPointY = 18F + wingMod;
                this.leftWing.rotationPointY = 18F + wingMod;
                this.rightWingSmall.rotationPointY = 18F + wingMod;
                this.leftWingSmall.rotationPointY = 18F + wingMod;

                this.pose = 2;
            } else {
                this.pose = 0;
            }
        }
        //pecking ground

        //scratching (eating grass)

        //crowing

        /** wing position variants */
        if (sharedGenes != null) {
            if ((sharedGenes[86] == 1 && sharedGenes[87] == 1) || (sharedGenes[86] == 3 && sharedGenes[87] == 3)){
                this.rightWing.rotationPointY = this.rightWing.rotationPointY + 1F - wingMod;
                this.leftWing.rotationPointY = this.leftWing.rotationPointY + 1F - wingMod;
                this.rightWingSmall.rotationPointY = this.rightWingSmall.rotationPointY + 1F - wingMod;
                this.leftWingSmall.rotationPointY = this.leftWingSmall.rotationPointY + 1F - wingMod;
            } else if (sharedGenes[86] == 1 || sharedGenes[87] == 1){
                this.rightWing.rotationPointY = this.rightWing.rotationPointY + 0.5F - (wingMod/2);
                this.leftWing.rotationPointY = this.leftWing.rotationPointY + 0.5F - (wingMod/2);
                this.rightWingSmall.rotationPointY = this.rightWingSmall.rotationPointY - 0.5F - (wingMod/2);
                this.leftWingSmall.rotationPointY = this.leftWingSmall.rotationPointY - 0.5F - (wingMod/2);
            }

//            this.rightWing.rotationPointY = this.rightWing.rotationPointY + wingAngle* 2.2F;
//            this.leftWing.rotationPointY = this.leftWing.rotationPointY + wingAngle * 2.2F;
//            this.rightWingSmall.rotationPointY = this.rightWingSmall.rotationPointY + wingAngle * 2.2F;
//            this.leftWingSmall.rotationPointY = this.leftWingSmall.rotationPointY + wingAngle * 2.2F;

            if (sharedGenes[146] == 2 && sharedGenes[147] == 2) {
                if (sharedGenes[148] == 2 && sharedGenes[149] == 2) {
                    //normal body
                    this.rightWing.rotationPointX = -4.0F;
                    this.leftWing.rotationPointX = 4.0F;
                } else {
                    //big body
                    this.rightWing.rotationPointX = -4.5F;
                    this.leftWing.rotationPointX = 4.5F;
                }
            } else if (sharedGenes[148] == 2 && sharedGenes[149] == 2) {
                if (sharedGenes[146] == 2 || sharedGenes[147] == 2) {
                    //normal body
                    this.rightWing.rotationPointX = -4.0F;
                    this.leftWing.rotationPointX = 4.0F;
                } else {
                    //small body
                    this.rightWing.rotationPointX = -3.5F;
                    this.leftWing.rotationPointX = 3.5F;
                }
            } else {
                //normal body
                this.rightWing.rotationPointX = -4.0F;
                this.leftWing.rotationPointX = 4.0F;
            }
        }

        this.head.rotationPointY = 9.0F + ((EnhancedChicken)entitylivingbaseIn).getHeadRotationPointY(partialTickTime) * 9.0F;
        this.headRotationAngleX = ((EnhancedChicken)entitylivingbaseIn).getHeadRotationAngleX(partialTickTime);



    }

    private class ChickenModelData {
        int[] chickenGenes;
        String birthTime;
        boolean sleeping = false;
        int blink = 0;
        int lastAccessed = 0;
        long clientGameTime = 0;
        List<String> unrenderedModels = new ArrayList<>();
//        int dataReset = 0;
        boolean collar;
    }

    private ChickenModelData getChickenModelData() {
        if (this.currentChicken == null || !chickenModelDataCache.containsKey(this.currentChicken)) {
            return new ChickenModelData();
        }
        return chickenModelDataCache.get(this.currentChicken);
    }

    private ChickenModelData getCreateChickenModelData(T enhancedChicken) {
        clearCacheTimer++;
        if(clearCacheTimer > 50000) {
            chickenModelDataCache.values().removeIf(value -> value.lastAccessed==1);
            for (ChickenModelData cowModelData : chickenModelDataCache.values()){
                cowModelData.lastAccessed = 1;
            }
            clearCacheTimer = 0;
        }

        if (chickenModelDataCache.containsKey(enhancedChicken.getEntityId())) {
            ChickenModelData chickenModelData = chickenModelDataCache.get(enhancedChicken.getEntityId());
            chickenModelData.lastAccessed = 0;
            chickenModelData.sleeping = enhancedChicken.isRoosting();
            chickenModelData.blink = enhancedChicken.getBlink();
            chickenModelData.birthTime = enhancedChicken.getBirthTime();
            chickenModelData.collar = hasCollar(enhancedChicken.getEnhancedInventory());
            chickenModelData.clientGameTime = (((WorldInfo)((ClientWorld)enhancedChicken.world).getWorldInfo()).getGameTime());
            chickenModelData.unrenderedModels = new ArrayList<>();

            return chickenModelData;
        } else {
            ChickenModelData chickenModelData = new ChickenModelData();
            if (enhancedChicken.getSharedGenes()!=null) {
                chickenModelData.chickenGenes = enhancedChicken.getSharedGenes().getAutosomalGenes();
            }
            chickenModelData.sleeping = enhancedChicken.isRoosting();
            chickenModelData.blink = enhancedChicken.getBlink();
            chickenModelData.birthTime = enhancedChicken.getBirthTime();
            chickenModelData.collar = hasCollar(enhancedChicken.getEnhancedInventory());
            chickenModelData.clientGameTime = (((WorldInfo)((ClientWorld)enhancedChicken.world).getWorldInfo()).getGameTime());

            if(chickenModelData.chickenGenes != null) {
                chickenModelDataCache.put(enhancedChicken.getEntityId(), chickenModelData);
            }


            return chickenModelData;
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
