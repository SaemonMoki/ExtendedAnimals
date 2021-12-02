package mokiyoki.enhancedanimals.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import mokiyoki.enhancedanimals.config.EanimodCommonConfig;
import mokiyoki.enhancedanimals.entity.EnhancedChicken;
import mokiyoki.enhancedanimals.items.CustomizableCollar;
import mokiyoki.enhancedanimals.model.util.ModelHelper;
import mokiyoki.enhancedanimals.renderer.EnhancedRendererModelNew;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.inventory.Inventory;
import net.minecraft.util.math.MathHelper;
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
    private float wingAngle = 0; //[between 0 - -1.5]

    //the below is all chicken parts
    private final EnhancedRendererModelNew theChicken;
    private final EnhancedRendererModelNew head;
    private final EnhancedRendererModelNew headNakedNeck;
    private final EnhancedRendererModelNew bigCrest;
    private final EnhancedRendererModelNew smallCrest;
    private final EnhancedRendererModelNew forwardCrest;
    private final EnhancedRendererModelNew combXtraSmallSingle;
    private final EnhancedRendererModelNew combSmallSingle;
    private final EnhancedRendererModelNew combSingle;
    private final EnhancedRendererModelNew combLargeSingle;
    private final EnhancedRendererModelNew combXtraLargeSingle;
    private final EnhancedRendererModelNew combSmallRose;
    private final EnhancedRendererModelNew combRose;
    private final EnhancedRendererModelNew combLargeRose;
    private final EnhancedRendererModelNew combSmallRose2;
    private final EnhancedRendererModelNew combRose2;
    private final EnhancedRendererModelNew combLargeRose2;
    private final EnhancedRendererModelNew combSmallPea;
    private final EnhancedRendererModelNew combPea;
    private final EnhancedRendererModelNew combLargePea;
    private final EnhancedRendererModelNew combSmallWalnut;
    private final EnhancedRendererModelNew combWalnut;
    private final EnhancedRendererModelNew combLargeWalnut;
    private final EnhancedRendererModelNew combV;
    private final EnhancedRendererModelNew comb;
    private final EnhancedRendererModelNew butterCup;
    private final EnhancedRendererModelNew body;
    private final EnhancedRendererModelNew bodyBig;
    private final EnhancedRendererModelNew bodySmall;
    private final ModelRenderer xtraShortTail;
    private final ModelRenderer shortTail;
    private final EnhancedRendererModelNew tail;
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
    private final EnhancedRendererModelNew beak;
    private final EnhancedRendererModelNew smallChin;
    private final EnhancedRendererModelNew chin;
    private final EnhancedRendererModelNew bigChin;
    private final EnhancedRendererModelNew beardChin;
    private final EnhancedRendererModelNew peaChin;
    private final EnhancedRendererModelNew beard;
    private final EnhancedRendererModelNew beardNN;
    private final EnhancedRendererModelNew earL;
    private final EnhancedRendererModelNew earR;
    private final EnhancedRendererModelNew earTuftL;
    private final EnhancedRendererModelNew earTuftR;
    private final EnhancedRendererModelNew eyeLeft;
    private final EnhancedRendererModelNew eyeRight;
    private final EnhancedRendererModelNew collar;
    private final EnhancedRendererModelNew bell;

    private Integer currentChicken = null;

    public ModelEnhancedChicken() {
        this.textureWidth = 64;
        this.textureHeight = 64;

        int combRz = 3;

        this.theChicken = new EnhancedRendererModelNew(this, 0, 0);

        this.head = new EnhancedRendererModelNew(this, 12, 0, "Head");
        this.head.addBox(-2.0F, -6.0F, -2.0F, 4, 6, 3, 0.0F);

        this.headNakedNeck = new EnhancedRendererModelNew(this, 12, 0, "NNHead");
        this.headNakedNeck.addBox(-2.0F, -6.0F, -2.0F, 4, 4, 3, 0.0F);
        this.headNakedNeck.setTextureOffset(0, 6);
        this.headNakedNeck.addBox(-1F, -2.0F, -4F + combRz, 2, 3, 2);

        this.bigCrest = new EnhancedRendererModelNew(this, 1, 42, "BigCrest");
        this.bigCrest.addBox(-2F, -9.0F, -5.5F + combRz, 4, 4, 4, 0.4F);

        this.smallCrest = new EnhancedRendererModelNew(this, 2, 43, "SmallCrest");
        this.smallCrest.addBox(-1.5F, -8.5F, -5F + combRz, 3, 3, 3, 0.1F);

        this.forwardCrest = new EnhancedRendererModelNew(this, 2, 43, "ForwardCrest");
        this.forwardCrest.addBox(-1.5F, -8.0F, -6F + combRz, 3, 3, 3, 0.2F);

        this.earL = new EnhancedRendererModelNew(this, 1, 52, "LeftEar");
        this.earL.addBox(-2.2F, -6.0F, -2.2F, 1, 6, 3);
        this.earR = new EnhancedRendererModelNew(this, 1, 52, "RightEar");
        this.earR.addBox(2.2F, -6.0F, -2.4F, 1, 6, 3);
        this.earR.mirror = true;

        this.eyeLeft = new EnhancedRendererModelNew(this, 23, 0, "EyeLeft");
        this.eyeLeft.addBox(1.0F, -5.0F, -2.0F, 1, 1, 1, 0.01F);
        this.eyeRight = new EnhancedRendererModelNew(this, 11, 0, "EyeRight");
        this.eyeRight.addBox(-2.0F, -5.0F, -2.0F, 1, 1, 1, 0.01F);

        this.earTuftL = new EnhancedRendererModelNew(this, 12, 47, "LeftEarTuft");
        this.earTuftL.addBox(-2.0F, 0.0F, 0.0F, 2, 1, 3F);
        this.earTuftL.setRotationPoint(-1.5F, -4.5F, -1.0F);

        this.earTuftR = new EnhancedRendererModelNew(this, 12, 47, "RightEarTuft");
        this.earTuftR.addBox(0.0F, 0.0F, 0.0F, 2, 1, 3F);
        this.earTuftR.setRotationPoint(1.5F, -4.5F, -1.0F);
        this.earR.mirror = true;

        int combSz = 3;

        this.combXtraSmallSingle = new EnhancedRendererModelNew(this, 0, 0, "XSSingleComb");
        this.combXtraSmallSingle.addBox(-0.5F, -1.5F, -2.5F, 1, 2, 1, -0.25F);
        this.combXtraSmallSingle.addBox(-0.5F, -2.0F, -2.0F, 1, 2, 1, -0.25F);
        this.combXtraSmallSingle.addBox(-0.5F, -1.75F, -1.5F, 1, 1, 1, -0.25F);
        this.combXtraSmallSingle.addBox(-0.5F, -2.25F, -1.0F, 1, 2, 1, -0.25F);
        this.combXtraSmallSingle.addBox(-0.5F, -1.75F, -0.5F, 1, 1, 1, -0.25F);
        this.combXtraSmallSingle.addBox(-0.5F, -2.0F, 0.0F, 1, 1, 1, -0.25F);

        this.combSmallSingle = new EnhancedRendererModelNew(this, 0, 0, "SSingleComb");
        this.combSmallSingle.addBox(-0.5F, -1.75F, -2.25F, 1, 2, 1, -0.125F);
        this.combSmallSingle.addBox(-0.5F, -2.5F, -1.75F, 1, 1, 1, -0.125F);
        this.combSmallSingle.addBox(-0.5F, -1.75F, -1.5F, 1, 1, 2, -0.125F);
        this.combSmallSingle.addBox(-0.5F, -2.5F, -0.5F, 1, 1, 1, -0.125F);
        this.combSmallSingle.addBox(-0.5F, -1.75F, 0F, 1, 1, 1, -0.125F);
        this.combSmallSingle.addBox(-0.5F, -2.0F, 0.75F, 1, 1, 1, -0.125F);

        this.combSingle = new EnhancedRendererModelNew(this, 0, 0, "SingleComb");
        this.combSingle.addBox(-0.5F, -1.0F, (-6F + combSz), 1, 1, 1);
        this.combSingle.addBox(-0.5F, -1.5F, (-6F + combSz), 1, 1, 1);
        this.combSingle.addBox(-0.5F, -2.5F, (-5F + combSz), 1, 2, 1);
        this.combSingle.addBox(-0.5F, -2.0F, (-4F + combSz), 1, 1, 1);
        this.combSingle.addBox(-0.5F, -2.5F, (-3F + combSz), 1, 2, 1);
        this.combSingle.addBox(-0.5F, -1.5F, (-2F + combSz), 1, 1, 1);

        this.combLargeSingle = new EnhancedRendererModelNew(this, 0, 0, "LSingleComb");
        this.combLargeSingle.addBox(-0.5F, -2.5F, (-6.5F + combSz), 1, 1, 1);
        this.combLargeSingle.addBox(-0.5F, -1.5F, (-6F + combSz), 1, 2, 1);
        this.combLargeSingle.addBox(-0.5F, -3.5F, (-5F + combSz), 1, 3, 1);
        this.combLargeSingle.addBox(-0.5F, -2.5F, (-4F + combSz), 1, 2, 1);
        this.combLargeSingle.addBox(-0.5F, -4.0F, (-3F + combSz), 1, 3, 1);
        this.combLargeSingle.addBox(-0.5F, -2.5F, (-2F + combSz), 1, 2, 1);
        this.combLargeSingle.addBox(-0.5F, -3.5F, (-1F + combSz), 1, 3, 1);
        this.combLargeSingle.addBox(-0.5F, -2.0F, (0F + combSz), 1, 1, 1);

        this.combXtraLargeSingle = new EnhancedRendererModelNew(this, 0, 0, "XLSingleComb");
        this.combXtraLargeSingle.addBox(-0.5F, -3.5F, (-6.5F + combSz), 1, 2, 1);
        this.combXtraLargeSingle.addBox(-0.5F, -2.5F, (-6F + combSz), 1, 3, 1);
        this.combXtraLargeSingle.addBox(-0.5F, -5.0F, (-5F + combSz), 1, 4, 1);
        this.combXtraLargeSingle.addBox(-0.5F, -3.5F, (-4F + combSz), 1, 3, 1);
        this.combXtraLargeSingle.addBox(-0.5F, -4.5F, (-3F + combSz), 1, 4, 1);
        this.combXtraLargeSingle.addBox(-0.5F, -5.5F, (-2.5F + combSz), 1, 1, 1);
        this.combXtraLargeSingle.addBox(-0.5F, -3.5F, (-2F + combSz), 1, 3, 1);
        this.combXtraLargeSingle.addBox(-0.5F, -4.5F, (-1F + combSz), 1, 4, 1);
        this.combXtraLargeSingle.addBox(-0.5F, -5.0F, (-0.5F + combSz), 1, 1, 1);
        this.combXtraLargeSingle.addBox(-0.5F, -3.0F, (0F + combSz), 1, 2, 1);
        this.combXtraLargeSingle.addBox(-0.5F, -3.5F, (1F + combSz), 1, 2, 1);

        this.combSmallRose = new EnhancedRendererModelNew(this, 0, 0, "SRoseComb");
        this.combSmallRose.addBox(-1F, -1.25F, (-5.75F + combRz), 2, 2, 1, -0.25F);
        this.combSmallRose.addBox(-0.5F, -2.0F, (-5.25F + combRz), 1, 2, 1, -0.1F);
        this.combSmallRose.addBox(-0.5F, -2.4F, (-4.75F + combRz), 1, 1, 1, -0.25F);

        this.combRose = new EnhancedRendererModelNew(this, 0, 0, "RoseComb");
        this.combRose.addBox(-0.5F, -1.0F, (-6F + combRz), 1, 1, 1, 0.5F);
        this.combRose.addBox(-0.5F, -2.0F, (-5F + combRz), 1, 1, 1, 0.25F);
        this.combRose.addBox(-0.5F, -3.0F, (-4F + combRz), 1, 1, 1);

        this.combLargeRose = new EnhancedRendererModelNew(this, 0, 0, "LRoseComb");
        this.combLargeRose.addBox(-1.0F, -1.0F, -3.0F, 2, 2, 1);
        this.combLargeRose.addBox(-0.5F, -2.0F, -2.5F, 1, 1, 2, 0.2F);
        this.combLargeRose.addBox(-0.5F, -3.0F, -0.5F, 1, 2, 1);
        this.combLargeRose.addBox(-0.5F, -4.0F, -0.0F, 1, 1, 1);

        this.combSmallRose2 = new EnhancedRendererModelNew(this, 0, 0, "SRoseTwo");
        this.combSmallRose2.addBox(-0.5F, -1.0F, -2.5F, 1, 1, 1, 0.1F);
        this.combSmallRose2.addBox(-0.5F, -1.5F, -2.0F, 1, 1, 1);
        this.combSmallRose2.addBox(-0.5F, -1.4F, -1F, 1, 1, 1);

        this.combRose2 = new EnhancedRendererModelNew(this, 0, 0, "RoseTwo");
        this.combRose2.addBox(-0.5F, -1.0F, (-6F + combRz), 1, 1, 1, 0.5F);
        this.combRose2.addBox(-0.5F, -2.0F, (-5F + combRz), 1, 1, 1, 0.25F);
        this.combRose2.addBox(-0.5F, -2.0F, (-4F + combRz), 1, 1, 1);

        this.combLargeRose2 = new EnhancedRendererModelNew(this, 0, 0, "LRoseTwo");
        this.combLargeRose2.addBox(-1.0F, -1.0F, -3.0F, 2, 2, 1);
        this.combLargeRose2.addBox(-1.0F, -1.75F, -2.5F, 2, 1, 2);
        this.combLargeRose2.addBox(-0.5F, -1.5F, -0.5F, 1, 1, 1, 0.2F);

        this.combSmallPea = new EnhancedRendererModelNew(this, 0, 0, "SPeaComb");
        this.combSmallPea.addBox(-0.5F, -1.0F, -2.5F, 1, 1, 1, -0.25F);
        this.combSmallPea.addBox(-0.5F, -1.5F, -2.5F, 1, 1, 1, -0.125F);
        this.combSmallPea.addBox(-0.5F, -1.5F, -1.9F, 1, 1, 1, -0.25F);

        this.combPea = new EnhancedRendererModelNew(this, 0, 0, "PeaComb");
        this.combPea.addBox(-0.5F, -1.0F, -3.0F, 1, 1, 2, -0.2F);
        this.combPea.addBox(-0.5F, -1.5F, -2.5F, 1, 1, 1);
        this.combPea.addBox(-0.5F, -2.0F, -2.0F, 1, 2, 1, -0.2F);

        this.combLargePea = new EnhancedRendererModelNew(this, 0, 0, "LPeaComb");
        this.combLargePea.addBox(-1.0F, -0.75F, -2.25F, 2, 1, 1);
        this.combLargePea.addBox(-0.5F, -1.75F, -2.4F, 1, 2, 2);
        this.combLargePea.setTextureOffset(0, 14);
        this.combLargePea.addBox(0.5F, -1.75F, -2.4F, 1, 2, 2);
        this.combLargePea.addBox(-0.5F, -2.0F, -1.1F, 1, 1, 1);

        this.combSmallWalnut = new EnhancedRendererModelNew(this, 0, 0, "SWalnutComb");
        this.combSmallWalnut.addBox(-0.5F, -1.5F, -2.5F, 1, 1, 1, -0.25F);

        this.combWalnut = new EnhancedRendererModelNew(this, 0, 0, "WalnutComb");
        this.combWalnut.addBox(-0.5F, -1.5F, -2.5F, 1, 1, 1);

        this.combLargeWalnut = new EnhancedRendererModelNew(this, 0, 0, "LWalnut");
        this.combLargeWalnut.addBox(-1F, -2.0F, -2.5F, 2, 2, 1, -0.125F);
        this.combLargeWalnut.addBox(-0.5F, -1.5F, -2.0F, 1, 1, 1);

        this.combV = new EnhancedRendererModelNew(this, 0, 0, "VComb");
        this.combV.addBox(-0.5F, -1.5F, (-5.5F + combRz), 1, 1, 1);
        this.combV.addBox(0F, -2.0F, (-5.25F + combRz), 1, 1, 1, -0.2F);
        this.combV.addBox(-1F, -2.0F, (-5.25F + combRz), 1, 1, 1, -0.2F);
        this.combV.addBox(.1F, -2.3F, (-5F + combRz), 1, 1, 1, -0.3F);
        this.combV.addBox(-1.1F, -2.3F, (-5F + combRz), 1, 1, 1, -0.3F);

        this.comb = new EnhancedRendererModelNew(this, 0, 0, "Comb", false);
        this.comb.setRotationPoint(0.0F, -5.0F, 0.0F);

        this.butterCup = new EnhancedRendererModelNew(this, 0, 0, "ButterCup", false);
        this.butterCup.setRotationPoint(0.0F, -5.0F, 0.0F);
        this.butterCup.rotateAngleZ = (float)Math.PI * 0.15F;

        this.body = new EnhancedRendererModelNew(this, 22, 10, "Body");
        this.body.addBox(-3F, -6F, -4F, 6, 6, 8F);
        this.body.setRotationPoint(0F, 0F, 1F);

        this.bodyBig = new EnhancedRendererModelNew(this, 22, 10, "BigBody");
        this.bodyBig.addBox(-3F, -6F, -4F, 6, 6, 8, 0.5F);
        this.bodyBig.setRotationPoint(0F, 0F, 1F);

        this.bodySmall = new EnhancedRendererModelNew(this, 22, 10, "SmallBody");
        this.bodySmall.addBox(-3F, -6F, -4F, 6, 6, 8, -0.5F);
        this.bodySmall.setRotationPoint(0F, 0F, 1F);

        this.xtraShortTail = new ModelRenderer(this, 36, 10);
        this.xtraShortTail.addBox(-0.5F, 12F, 3F, 1, 4, 3F);
        this.xtraShortTail.setTextureOffset(37, 11);
        this.xtraShortTail.addBox(-0.5F, 11F, 4F, 1, 1, 2F);

        this.shortTail = new ModelRenderer(this, 34, 11);
        this.shortTail.addBox(-0.5F, 12F, 3F, 1, 4, 4F);
        this.shortTail.setTextureOffset(36, 11);
        this.shortTail.addBox(-0.5F, 11F, 4F, 1, 1, 3F);

        this.tail = new EnhancedRendererModelNew(this, 30, 0, "NormalTail");
        this.tail.addBox(-0.5F, 0.0F, 3F, 1, 4, 5F);
        this.tail.setTextureOffset(44, 3);
        this.tail.addBox(-0.5F, -1.0F, 4F, 1, 1, 4F);
        this.tail.setRotationPoint(0.0F, -6.5F, -0.5F);

        this.longTail = new ModelRenderer(this, 34, 10);
        this.longTail.addBox(-0.5F, 12F, 3F, 1, 4, 5F);
        this.longTail.setTextureOffset(35, 11);
        this.longTail.addBox(-0.5F, 11F, 4F, 1, 1, 4F);
        this.longTail.setTextureOffset(38, 13);
        this.longTail.addBox(-0.5F, 12F, 8F, 1, 3, 2F);
        this.longTail.setTextureOffset(39, 15);
        this.longTail.addBox(-0.5F, 13F, 10F, 1, 1, 1F);

        this.xtraLongTail = new ModelRenderer(this, 34, 10);
        this.xtraLongTail.addBox(-0.5F, 12F, 4F, 1, 4, 5F);
        this.xtraLongTail.setTextureOffset(35, 11);
        this.xtraLongTail.addBox(-0.5F, 11F, 5F, 1, 1, 4F);
        this.xtraLongTail.setTextureOffset(38, 13);
        this.xtraLongTail.addBox(-0.5F, 12F, 9F, 1, 3, 2F);
        this.xtraLongTail.addBox(-0.5F, 15F, 9F, 1, 3, 3F);
        this.xtraLongTail.setTextureOffset(39, 15);
        this.xtraLongTail.addBox(-0.5F, 18F, 11F, 1, 1, 1F);
        this.xtraLongTail.addBox(-0.5F, 16F, 5F, 1, 1, 1F);

        this.leftLeg = new ModelRenderer(this, 8, 18);
        this.leftLegExtend = new ModelRenderer(this, 8, 18);
        this.rightLeg = new ModelRenderer(this, 8, 18);
        this.rightLegExtend = new ModelRenderer(this, 8, 18);
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

        this.rightFeather1 = new ModelRenderer(this, 1, 35);
        this.rightFeather1.addBox(1.1F, 4F, 0F, 2, 3, 3F);

        this.rightFeather1Extend = new ModelRenderer(this, 1, 35);
        this.rightFeather1Extend.addBox(1.1F, 2F, 0F, 2, 2, 3F);

        this.leftFeather1 = new ModelRenderer(this, 1, 35);
        this.leftFeather1.mirror = true;
        this.leftFeather1.addBox(-3.1F, 4F, 0F, 2, 3, 3F);

        this.leftFeather1Extend = new ModelRenderer(this, 1, 35);
        this.leftFeather1Extend.mirror = true;
        this.leftFeather1Extend.addBox(-3.1F, 2F, 0F, 2, 2, 3F);

//        this.rightFeatherTall1 = new ModelRenderer(this,1,35);
//        this.rightFeatherTall1.addBox(1.1F, 3F, 0F, 2, 1, 3);
//        this.rightFeatherTall1.addBox(1.1F, 4F, 0F, 2, 3, 3);
//
//        this.leftFeatherTall1 = new ModelRenderer(this,1,35);
//        this.leftFeatherTall1.mirror = true;
//        this.leftFeatherTall1.addBox(-3.1F, 3F, 0F, 2, 1, 3);
//        this.leftFeatherTall1.addBox(-3.1F, 4F, 0F, 2, 3, 3);

        this.rightFeather2 = new ModelRenderer(this, 12, 34);
        this.rightFeather2.addBox(1.5F, 7F, -2.5F, 2, 2, 5F);

        this.leftFeather2 = new ModelRenderer(this, 12, 34);
        this.leftFeather2.mirror = true;
        this.leftFeather2.addBox(-3.5F, 7F, -2.5F, 2, 2, 5F);

        this.rightFeather3 = new ModelRenderer(this, 28, 36);
        this.rightFeather3.addBox(3.5F, 8.9F, -2.5F, 4, 1, 5F);

        this.leftFeather3 = new ModelRenderer(this, 28, 36);
        this.leftFeather3.mirror = true;
        this.leftFeather3.addBox(-7.5F, 8.9F, -2.5F, 4, 1, 5F);

        this.rightVultureHock = new ModelRenderer(this, 33, 32);
        this.rightVultureHock.mirror = true;
        this.rightVultureHock.addBox(2.5F, 3.0F, 2.5F, 1, 3, 4, -0.2F);

        this.leftVultureHock = new ModelRenderer(this, 33, 32);
        this.leftVultureHock.addBox(-3.5F, 3.0F, 2.5F, 1, 3, 4, -0.2F);

        this.rightWing = new ModelRenderer(this, 13, 19);
        this.rightWing.addBox(0F, 0F, -3.0F, 1, 4, 6F);
        this.rightWing.setRotationPoint(-4.0F, 13.0F, 1.0F);

        this.leftWing = new ModelRenderer(this, 45, 19);
        this.leftWing.addBox(-1.0F, 0F, -3.0F, 1, 4, 6F);
        this.leftWing.setRotationPoint(4.0F, 13.0F, 1.0F);

        this.rightWingSmall = new ModelRenderer(this, 14, 20);
        this.rightWingSmall.addBox(0F, 0F, -3.0F, 1, 3, 5F);
        this.rightWingSmall.setRotationPoint(-4.0F, 13.0F, 1.0F);

        this.leftWingSmall = new ModelRenderer(this, 46, 20);
        this.leftWingSmall.addBox(-1.0F, 0F, -3.0F, 1, 3, 5F);
        this.leftWingSmall.setRotationPoint(4.0F, 13.0F, 1.0F);

        this.beak = new EnhancedRendererModelNew(this, 0, 18, "Beak");
        this.beak.addBox(-1.0F, 0.0F, -2.0F, 2, 2, 2, 0.0F);
        this.beak.setRotationPoint(0.0F, -4.0F, -2.0F);

        this.chin = new EnhancedRendererModelNew(this, 14, 10, "Waddles");
        this.chin.addBox(-1.0F, -2.0F, -3.0F, 2, 2, 1, 0.0F);

        this.bigChin = new EnhancedRendererModelNew(this, 14, 10, "BigWaddles");
        this.bigChin.addBox(-1.0F, -2.0F, -3.0F, 2, 3, 1, 0.0F);

        this.smallChin = new EnhancedRendererModelNew(this, 14, 10, "SmallWaddles");
        this.smallChin.addBox(-1.0F, -2.0F, -3.0F, 2, 1, 2, 0.0F);

        this.peaChin = new EnhancedRendererModelNew(this, 14, 10, "PeaCombWaddles");
        this.peaChin.addBox(-0.5F, -2.0F, -3.0F, 1, 1, 2, 0.0F);

        this.beardChin = new EnhancedRendererModelNew(this, 14, 10, "BeardWaddles");
        this.beardChin.addBox(-1.5F, -2.5F, -3.0F, 3, 1, 1, 0.0F);

        this.beard = new EnhancedRendererModelNew(this,2,29, "Beard");
        this.beard.addBox(-3F, -4F, -2F, 1, 2, 2F);
        this.beard.addBox(-2.5F, -3F, -2.75F, 2, 2, 2F);
        this.beard.setTextureOffset(3,30);
        this.beard.addBox(-1F, -2.25F, -2.9F, 2, 2, 2F);
        this.beard.addBox(-0.5F, -2F, -3.75F, 1, 1, 1F);
        this.beard.setTextureOffset(2,29);
        this.beard.addBox(2F, -4F, -2F, 1, 2, 2F);
        this.beard.addBox(0.5F, -3F, -2.75F, 2, 2, 2F);

        this.beardNN = new EnhancedRendererModelNew(this,2,29, "NNBeard");
        this.beardNN.addBox(-3F, -4F, -2F, 1, 2, 2F);
        this.beardNN.addBox(-2F, -3F, -2.75F, 2, 2, 2F);
        this.beardNN.setTextureOffset(3,30);
        this.beardNN.addBox(-0.5F, -2F, -3.5F, 1, 1, 1F);
        this.beardNN.setTextureOffset(2,29);
        this.beardNN.addBox(2F, -4F, -2F, 1, 2, 2F);
        this.beardNN.addBox(0F, -3F, -2.75F, 2, 2, 2F);

        theChicken.addChild(this.body);
        theChicken.addChild(this.bodyBig);
        theChicken.addChild(this.bodySmall);

        this.body.addChild(this.head);
        this.body.addChild(this.headNakedNeck);
        this.body.addChild(this.tail);

        this.head.addChild(this.beak);
        this.head.addChild(this.eyeLeft);
        this.head.addChild(this.eyeRight);
        this.head.addChild(this.earL);
        this.head.addChild(this.earR);

        this.head.addChild(this.bigChin);
        this.head.addChild(this.chin);
        this.head.addChild(this.smallChin);
        this.head.addChild(this.beardChin);
        this.head.addChild(this.peaChin);
        this.head.addChild(this.comb);
        this.head.addChild(this.butterCup);

        this.head.addChild(this.smallCrest);
        this.head.addChild(this.forwardCrest);
        this.head.addChild(this.bigCrest);

        this.head.addChild(this.earTuftL);
        this.head.addChild(this.earTuftR);

        this.head.addChild(this.beard);
        this.head.addChild(this.beardNN);

        this.comb.addChild(this.combXtraSmallSingle);
        this.comb.addChild(this.combSmallSingle);
        this.comb.addChild(this.combSingle);
        this.comb.addChild(this.combLargeSingle);
        this.comb.addChild(this.combXtraLargeSingle);
        this.comb.addChild(this.combSmallRose);
        this.comb.addChild(this.combRose);
        this.comb.addChild(this.combLargeRose);
        this.comb.addChild(this.combSmallRose2);
        this.comb.addChild(this.combRose2);
        this.comb.addChild(this.combLargeRose2);
        this.comb.addChild(this.combSmallPea);
        this.comb.addChild(this.combPea);
        this.comb.addChild(this.combLargePea);
        this.comb.addChild(this.combSmallWalnut);
        this.comb.addChild(this.combWalnut);
        this.comb.addChild(this.combLargeWalnut);
        this.comb.addChild(this.combV);
        this.butterCup.addChild(this.combXtraSmallSingle);
        this.butterCup.addChild(this.combSmallSingle);
        this.butterCup.addChild(this.combSingle);
        this.butterCup.addChild(this.combLargeSingle);
        this.butterCup.addChild(this.combXtraLargeSingle);
        this.butterCup.addChild(this.combSmallRose);
        this.butterCup.addChild(this.combRose);
        this.butterCup.addChild(this.combLargeRose);
        this.butterCup.addChild(this.combSmallRose2);
        this.butterCup.addChild(this.combRose2);
        this.butterCup.addChild(this.combLargeRose2);
        this.butterCup.addChild(this.combSmallPea);
        this.butterCup.addChild(this.combPea);
        this.butterCup.addChild(this.combLargePea);
        this.butterCup.addChild(this.combSmallWalnut);
        this.butterCup.addChild(this.combWalnut);
        this.butterCup.addChild(this.combLargeWalnut);

        /**
         *  Equipment Stuff
         */
        this.textureWidth=160;
        this.textureHeight=160;

        this.collar = new EnhancedRendererModelNew(this, 0, 155, "Collar");
        this.collar.addBox(-2.5F, -1.0F, -1.5F, 5,  1, 4, 0.001F);
        this.collar.setTextureOffset(30, 156);
        this.collar.addBox(0.0F, -1.3333F, -2.5F, 0,  2, 2);
        this.collar.rotateAngleX = (float)Math.PI/4.0F;

        this.bell = new EnhancedRendererModelNew(this, 18, 154, "Bell");
        this.bell.addBox(-1.5F, 0.0F, -1.5F, 3, 3, 3, -1.0F);
        this.bell.setRotationPoint(0.0F, -1.0F, -1.25F);
        this.bell.rotateAngleX = -(float)Math.PI/4.0F;

        this.collar.addChild(this.bell);
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
        Phenotype chicken = chickenModelData.phenotype;

        if (chicken != null) {
            this.resetAllCubes();
            int blink = chickenModelData.blink;
            List<String> unrenderedModels = chickenModelData.unrenderedModels;

            boolean longLegs = false;

            if (!chickenModelData.sleeping) {
                //long legs
                longLegs = chicken.hasLongLegs();
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

    //        wingAngle = 1.5F;   // used for debugging wingAngle  [ 0 to 1.5 ]

            float age = 1.0F;
            if (!(chickenModelData.birthTime == null) && !chickenModelData.birthTime.equals("") && !chickenModelData.birthTime.equals("0")) {
                int ageTime = (int) (chickenModelData.clientGameTime - Long.parseLong(chickenModelData.birthTime));
                if (ageTime <= chickenModelData.adultAge) {
                    age = ageTime < 0 ? 0 : ageTime / (float)chickenModelData.adultAge;
                }
            }

            float size = chickenModelData.size;

            float finalChickenSize = ((3.0F * size * age) + size) / 4.0F;

            matrixStackIn.push();
            matrixStackIn.scale(finalChickenSize, finalChickenSize, finalChickenSize);
            matrixStackIn.translate(0.0F, -1.5F + 1.5F / finalChickenSize, 0.0F);
            if (blink == 0 || blink >= 6) {
                this.eyeLeft.showModel = true;
                this.eyeRight.showModel = true;
            } else {
                this.eyeLeft.showModel = false;
                this.eyeRight.showModel = false;
            }

            if (chickenModelData.collar) {
                this.collar.showModel = true;
            }

            unrenderedModels.add(chicken.isNakedNeck ? "Head" : "NNHead");

            switch (chicken.beard) {
                case BIG_BEARD:
                    this.beard.showModel = true;
                    break;
                case NN_BEARD:
                    this.beardNN.showModel = true;
                    break;
            }

            if (age <= 0.3F) {
                this.earL.showModel = false;
                this.earR.showModel = false;
            } else if (chicken.isCombed()){
                this.comb.showModel = true;

                if (chicken.waddleSize >= 2) {
                    if (chicken.isBearded() && (!chicken.comb.hasPeaComb())) {
                        this.beardChin.showModel = true;
                    }
                    if (!chicken.isBearded()) {
                        if (chicken.comb.hasPeaComb()) {
                            this.peaChin.showModel = true;
                        }
                    }
                }
                if (!chicken.isBearded() && (!chicken.comb.hasPeaComb())) {
                    if (chicken.waddleSize >= 3) {
                        this.bigChin.showModel = true;
                    } else if (chicken.waddleSize >= 1) {
                        this.chin.showModel = true;
                    } else {
                        this.smallChin.showModel = true;
                    }
                }
                if (chicken.comb == Comb.SINGLE && (chicken.crestType == Crested.NONE || chicken.combSize >= 3)) {
                    switch (chicken.combSize) {
                        case 0:
                            this.combXtraSmallSingle.showModel = true;
                            break;
                        case 1:
                            this.combSmallSingle.showModel = true;
                            break;
                        case 2:
                            this.combSingle.showModel = true;
                            break;
                        case 3:
                            this.combLargeSingle.showModel = true;
                            break;
                        case 4:
                        default:
                            this.combXtraLargeSingle.showModel = true;
                            break;

                    }
                } else if (chicken.comb == Comb.ROSE_ONE && chicken.crestType == Crested.NONE) {
                    switch (chicken.combSize) {
                        case 0:
                            this.combSmallRose.showModel = true;
                            break;
                        case 1:
                        case 2:
                        case 3:
                            this.combRose.showModel = true;
                            break;
                        case 4:
                        default:
                            this.combLargeRose.showModel = true;
                            break;
                    }
                } else if (chicken.comb == Comb.ROSE_TWO) {
                    switch (chicken.combSize) {
                        case 0:
                            this.combSmallRose2.showModel = true;
                            break;
                        case 1:
                        case 2:
                        case 3:
                            this.combRose2.showModel = true;
                            break;
                        case 4:
                        default:
                            this.combLargeRose2.showModel = true;
                            break;
                    }
                } else if (chicken.comb == Comb.PEA || (chicken.comb == Comb.SINGLE && chicken.crestType != Crested.NONE)) {
                    switch (chicken.combSize) {
                        case 1:
                        case 2:
                            this.combSmallPea.showModel = true;
                            break;
                        case 3:
                            this.combPea.showModel = true;
                            break;
                        case 4:
                        default:
                            this.combLargePea.showModel = true;
                            break;
                    }
                } else if (chicken.comb == Comb.WALNUT || ((chicken.comb == Comb.ROSE_ONE || chicken.comb == Comb.ROSE_TWO) && chicken.crestType != Crested.NONE)) {
                    switch (chicken.combSize) {
                        case 1:
                        case 2:
                            this.combSmallWalnut.showModel = true;
                            break;
                        case 3:
                            this.combWalnut.showModel = true;
                            break;
                        case 4:
                        default:
                            this.combLargeWalnut.showModel = true;
                            break;
                    }
                } else if (chicken.comb == Comb.V) {
                    this.combV.showModel = true;
                }

                if (chicken.butterCup) {
                    this.butterCup.showModel = true;
                }
            }

            if (chicken.crestType == Crested.SMALL_CREST || (chicken.crestType != Crested.NONE && age > 0.5F)) {
                this.smallCrest.showModel = true;
            } else if (chicken.crestType == Crested.SMALL_FORWARDCREST || chicken.crestType == Crested.BIG_FORWARDCREST) {
                this.forwardCrest.showModel = true;
            } else if (chicken.crestType == Crested.BIG_CREST) {
                this.bigCrest.showModel = true;
            }

            if (chicken.rumpless || age < 0.15F) {
                this.tail.showModel = false;
            }

            if (chicken.wingSize == 2) {
                this.rightWing.showModel = true;
                this.leftWing.showModel = true;
                this.rightWing.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                this.leftWing.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            } else {
                this.rightWingSmall.showModel = true;
                this.leftWingSmall.showModel = true;
                this.rightWingSmall.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                this.leftWingSmall.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            }

            this.rightLeg.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            this.leftLeg.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);

            if (longLegs) {
                this.leftLegExtend.showModel = true;
                this.rightLegExtend.showModel = true;
                this.leftLegExtend.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                this.rightLegExtend.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            }

            switch (chicken.footFeatherType) {
                case BIG_TOEFEATHERS:
                case TOEFEATHERS:
                    this.leftFeather3.showModel = true;
                    this.rightFeather3.showModel = true;
                    this.leftFeather3.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                    this.rightFeather3.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                case FOOTFEATHERS:
                    this.leftFeather2.showModel = true;
                    this.rightFeather2.showModel = true;
                    this.leftFeather2.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                    this.rightFeather2.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                case LEGFEATHERS:
                    if (!chickenModelData.sleeping && !nesting) {
                        this.leftFeather1.showModel = true;
                        this.rightFeather1.showModel = true;
                        this.leftFeather1.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                        this.rightFeather1.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                        if (chicken.longHockFeathers || longLegs) {
                            this.leftFeather1Extend.showModel = true;
                            this.rightFeather1Extend.showModel = true;
                            this.leftFeather1Extend.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                            this.rightFeather1Extend.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                        }
                    }

                    if (chicken.isVultureHocked) {
                        this.leftVultureHock.showModel = true;
                        this.rightVultureHock.showModel = true;
                        this.leftVultureHock.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                        this.rightVultureHock.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                    }
                    break;
            }


                if (chicken.earTufts) {
                    this.earTuftL.showModel = true;
                    this.earTuftR.showModel = true;
                }

                switch (chicken.bodyType) {
                    case -1:
                        unrenderedModels.add("Body");
                        unrenderedModels.add("BigBody");
                        break;
                    case 0:
                        unrenderedModels.add("SmallBody");
                        unrenderedModels.add("BigBody");
                        break;
                    default:
                        unrenderedModels.add("SmallBody");
                        unrenderedModels.add("Body");
                        break;
                }

                this.theChicken.render(matrixStackIn, bufferIn, null, unrenderedModels, false, packedLightIn, packedOverlayIn, red, green, blue, alpha);

            matrixStackIn.pop();
        }
  }

    private void resetAllCubes() {
        this.bigCrest.showModel = false;
        this.smallCrest.showModel = false;
        this.forwardCrest.showModel = false;
        this.combXtraSmallSingle.showModel = false;
        this.combSmallSingle.showModel = false;
        this.combSingle.showModel = false;
        this.combLargeSingle.showModel = false;
        this.combXtraLargeSingle.showModel = false;
        this.combSmallRose.showModel = false;
        this.combRose.showModel = false;
        this.combLargeRose.showModel = false;
        this.combSmallRose2.showModel = false;
        this.combRose2.showModel = false;
        this.combLargeRose2.showModel = false;
        this.combSmallPea.showModel = false;
        this.combPea.showModel = false;
        this.combLargePea.showModel = false;
        this.combSmallWalnut.showModel = false;
        this.combWalnut.showModel = false;
        this.combLargeWalnut.showModel = false;
        this.combV.showModel = false;
        this.comb.showModel = false;
        this.butterCup.showModel = false;
        this.xtraShortTail.showModel = false;
        this.shortTail.showModel = false;
        this.tail.showModel = true;
        this.longTail.showModel = false;
        this.xtraLongTail.showModel = false;
        this.rightLegExtend.showModel = false;
        this.rightFeather1.showModel = false;
        this.rightFeather1Extend.showModel = false;
//     rightFeatherTall1;
        this.rightFeather2.showModel = false;
        this.rightFeather3.showModel = false;
        this.leftLegExtend.showModel = false;
        this.leftFeather1.showModel = false;
        this.leftFeather1Extend.showModel = false;
//     leftFeatherTall1;
        this.leftFeather2.showModel = false;
        this.leftFeather3.showModel = false;
        this.leftVultureHock.showModel = false;
        this.rightVultureHock.showModel = false;
        this.rightWing.showModel = false;
        this.rightWingSmall.showModel = false;
        this.leftWing.showModel = false;
        this.leftWingSmall.showModel = false;
        this.smallChin.showModel = false;
        this.chin.showModel = false;
        this.bigChin.showModel = false;
        this.beardChin.showModel = false;
        this.peaChin.showModel = false;
        this.beard.showModel = false;
        this.beardNN.showModel = false;
        this.earL.showModel = true;
        this.earR.showModel = true;
        this.earTuftL.showModel = false;
        this.earTuftR.showModel = false;
        this.eyeLeft.showModel = false;
        this.eyeRight.showModel = false;
        this.collar.showModel = false;
    }


    /**
     * Sets the model's various rotation angles. For bipeds, par1 and par2 are used for animating the movement of arms
     * and legs, where par1 represents the time(so that arms and legs swing back and forth) and par2 represents how
     * "far" arms and legs can swing at most.
     */
    @Override
    public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        ChickenModelData chickenModelData = getChickenModelData();

        Phenotype chicken = chickenModelData.phenotype;

        if (chicken != null) {
            this.comb.rotateAngleZ = chicken.butterCup ? -(float) Math.PI * 0.15F : 0F;

//        float bodyangle = 0.5F;
//        super.setRotationAngles(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);

            this.head.rotationPointY = -3.5F;
            this.head.rotationPointZ = -3.5F;
            this.head.rotateAngleX = headPitch * 0.017453292F;
            this.head.rotateAngleY = netHeadYaw * 0.017453292F;

            ModelHelper.copyModelPositioning(head, headNakedNeck);

            this.earTuftL.rotateAngleX = 1.4F;
            this.earTuftL.rotateAngleZ = -1.4F;
            this.earTuftR.rotateAngleX = 1.4F;
            this.earTuftR.rotateAngleZ = 1.4F;

            //leg stuff
            this.rightLeg.rotationPointY = 15F;
            this.leftLeg.rotationPointY = 15F;
            this.rightLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
            this.leftLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
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
//        ModelHelper.copyModelPositioning(body, tail);
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
    }

    @Override
    public void setLivingAnimations(T entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTickTime) {
        super.setLivingAnimations(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTickTime);
        ChickenModelData chickenModelData = getCreateChickenModelData(entitylivingbaseIn);
        this.currentChicken = entitylivingbaseIn.getEntityId();

        boolean roosting = chickenModelData.sleeping;

        Phenotype chicken = chickenModelData.phenotype;

        this.rightLeg.rotationPointX = 0F;
        this.leftLeg.rotationPointX = 0F;
        this.rightLeg.rotationPointY = 0F;
        this.leftLeg.rotationPointY = 0F;

        float point = 19F;

        float wingMod = 0.0F;

        if (chicken != null) {
            if (chicken.bodyType == -1) {
                wingMod = 0.5F;
            }

            //genes variations
            if ((chicken.creeper) && (!nesting && !roosting)) {
                switch (chicken.longLegs) {
                    case 0:
                    default:
                        this.body.rotationPointY = 3F + point;
                        this.rightWing.rotationPointY = 16.0F + wingMod;
                        this.leftWing.rotationPointY = 16.0F + wingMod;
                        this.rightWingSmall.rotationPointY = 16.0F + wingMod;
                        this.leftWingSmall.rotationPointY = 16.0F + wingMod;
                        break;
                    case 1:
                        this.body.rotationPointY = 2.5F + point;
                        this.rightWing.rotationPointY = 15.5F + wingMod;
                        this.leftWing.rotationPointY = 15.5F + wingMod;
                        this.rightWingSmall.rotationPointY = 15.5F + wingMod;
                        this.leftWingSmall.rotationPointY = 15.5F + wingMod;
                        break;
                    case 2:
                        this.body.rotationPointY = 2F + point;
                        this.rightWing.rotationPointY = 15.0F + wingMod;
                        this.leftWing.rotationPointY = 15.0F + wingMod;
                        this.rightWingSmall.rotationPointY = 15.0F + wingMod;
                        this.leftWingSmall.rotationPointY = 15.0F + wingMod;
                        break;
                    }
                } else {
                    switch (chicken.longLegs) {
                        case 0:
                        default:
                            this.body.rotationPointY = 0F + point;
                            this.rightWing.rotationPointY = 13.0F + wingMod;
                            this.leftWing.rotationPointY = 13.0F + wingMod;
                            this.rightWingSmall.rotationPointY = 13.0F + wingMod;
                            this.leftWingSmall.rotationPointY = 13.0F + wingMod;
                            break;
                        case 1:
                            this.body.rotationPointY = -1F + point;
                            this.rightWing.rotationPointY = 12.0F + wingMod;
                            this.leftWing.rotationPointY = 12.0F + wingMod;
                            this.rightWingSmall.rotationPointY = 12.0F + wingMod;
                            this.leftWingSmall.rotationPointY = 12.0F + wingMod;
                            break;
                        case 2:
                            this.body.rotationPointY = -2F + point;
                            this.rightWing.rotationPointY = 11.0F + wingMod;
                            this.leftWing.rotationPointY = 11.0F + wingMod;
                            this.rightWingSmall.rotationPointY = 11.0F + wingMod;
                            this.leftWingSmall.rotationPointY = 11.0F + wingMod;
                            break;
                    }
                }


                //behaviour animations
                //nesting "moves legs together to remove clipping"
                if (nesting) {
                    this.body.rotationPointY = 4.9F + point;
                    this.rightLeg.rotationPointX = this.rightLeg.rotationPointX - 0.1F;
                    this.leftLeg.rotationPointX = this.leftLeg.rotationPointX + 0.1F;

                    this.rightWing.rotationPointY = 19F + wingMod;
                    this.leftWing.rotationPointY = 19F + wingMod;
                    this.rightWingSmall.rotationPointY = 19F + wingMod;
                    this.leftWingSmall.rotationPointY = 19F + wingMod;
                } else {
                    if (roosting) {
                        this.body.rotationPointY = 4.9F + point;
                        this.rightLeg.rotationPointY = 1F;
                        this.leftLeg.rotationPointY = 1F;
                        this.rightLeg.rotationPointX = this.rightLeg.rotationPointX - 0.1F;
                        this.leftLeg.rotationPointX = this.leftLeg.rotationPointX + 0.1F;

                        this.rightWing.rotationPointY = 18F + wingMod;
                        this.leftWing.rotationPointY = 18F + wingMod;
                        this.rightWingSmall.rotationPointY = 18F + wingMod;
                        this.leftWingSmall.rotationPointY = 18F + wingMod;
                    }
                }
                //pecking ground

                //scratching (eating grass)

                //crowing

                /** wing position variants */
                switch (chicken.wingPlacement) {
                    case 0:
                    default:
                        break;
                    case 1:
                        this.rightWing.rotationPointY = this.rightWing.rotationPointY + 0.5F - (wingMod / 2);
                        this.leftWing.rotationPointY = this.leftWing.rotationPointY + 0.5F - (wingMod / 2);
                        this.rightWingSmall.rotationPointY = this.rightWingSmall.rotationPointY - 0.5F - (wingMod / 2);
                        this.leftWingSmall.rotationPointY = this.leftWingSmall.rotationPointY - 0.5F - (wingMod / 2);
                        break;
                    case 2:
                        this.rightWing.rotationPointY = this.rightWing.rotationPointY + 1F - wingMod;
                        this.leftWing.rotationPointY = this.leftWing.rotationPointY + 1F - wingMod;
                        this.rightWingSmall.rotationPointY = this.rightWingSmall.rotationPointY + 1F - wingMod;
                        this.leftWingSmall.rotationPointY = this.leftWingSmall.rotationPointY + 1F - wingMod;
                        break;
                }

    //            this.rightWing.rotationPointY = this.rightWing.rotationPointY + wingAngle* 2.2F;
    //            this.leftWing.rotationPointY = this.leftWing.rotationPointY + wingAngle * 2.2F;
    //            this.rightWingSmall.rotationPointY = this.rightWingSmall.rotationPointY + wingAngle * 2.2F;
    //            this.leftWingSmall.rotationPointY = this.leftWingSmall.rotationPointY + wingAngle * 2.2F;

                switch (chicken.bodyType) {
                    case -1:
                        this.rightWing.rotationPointX = -3.5F;
                        this.leftWing.rotationPointX = 3.5F;
                        break;
                    case 0:
                    default:
                        this.rightWing.rotationPointX = -4.0F;
                        this.leftWing.rotationPointX = 4.0F;
                        break;
                    case 2:
                        this.rightWing.rotationPointX = -4.5F;
                        this.leftWing.rotationPointX = 4.5F;
                        break;
                }

            this.head.rotationPointY = (9.0F + ((EnhancedChicken) entitylivingbaseIn).getHeadRotationPointY(partialTickTime) * 9.0F);
            this.headRotationAngleX = ((EnhancedChicken) entitylivingbaseIn).getHeadRotationAngleX(partialTickTime);
        }

    }

    private class ChickenModelData {
        Phenotype phenotype;
        String birthTime;
        boolean sleeping = false;
        int blink = 0;
        int lastAccessed = 0;
        long clientGameTime = 0;
        float size = 1.0F;
        boolean isFemale = true;
        List<String> unrenderedModels = new ArrayList<>();
        boolean collar = false;
        int adultAge;
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
            chickenModelData.sleeping = enhancedChicken.isAnimalSleeping();
            chickenModelData.blink = enhancedChicken.getBlink();
            chickenModelData.birthTime = enhancedChicken.getBirthTime();
            chickenModelData.collar = hasCollar(enhancedChicken.getEnhancedInventory());
            chickenModelData.clientGameTime = ((((ClientWorld)enhancedChicken.world).getWorldInfo()).getGameTime());
            chickenModelData.unrenderedModels = new ArrayList<>();

            return chickenModelData;
        } else {
            ChickenModelData chickenModelData = new ChickenModelData();
            if (enhancedChicken.getSharedGenes()!=null) {
                chickenModelData.phenotype = new Phenotype(enhancedChicken.getSharedGenes().getAutosomalGenes());
            }
            chickenModelData.sleeping = enhancedChicken.isAnimalSleeping();
            chickenModelData.blink = enhancedChicken.getBlink();
            chickenModelData.birthTime = enhancedChicken.getBirthTime();
            chickenModelData.collar = hasCollar(enhancedChicken.getEnhancedInventory());
            chickenModelData.size = enhancedChicken.getAnimalSize();
            chickenModelData.isFemale = enhancedChicken.isFemale();
            chickenModelData.clientGameTime = (((ClientWorld)enhancedChicken.world).getWorldInfo().getGameTime());
            chickenModelData.adultAge = EanimodCommonConfig.COMMON.adultAgeChicken.get();

            if(chickenModelData.phenotype != null) {
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

    private class Phenotype{
        private Crested crestType = Crested.NONE;
        private FootFeathers footFeatherType = FootFeathers.NONE;
        private Comb comb = Comb.SINGLE;
        private Beard beard;
        private boolean butterCup = false;
        private boolean isVultureHocked;
        private boolean isNakedNeck;
        private boolean earTufts;
        private boolean rumpless;
        private boolean longHockFeathers;
        private boolean creeper;
        private int longLegs = 0;
        private int combSize = 0;
        private int waddleSize;
        private int bodyType;
        private int wingSize;
        private int wingPlacement = 0;

        Phenotype(int[] gene) {
            this.isNakedNeck = gene[52] == 1 || gene[53] == 1;
            this.rumpless = gene[72] == 2 || gene[73] == 2;
            this.earTufts = gene[150] == 2 || gene[151] == 2;
            this.isVultureHocked = gene[102] == 2 && gene[103] == 2;
            this.creeper = gene[70] == 2 || gene[71] == 2;

            if (gene[56] == 1 || gene[57] == 1) {
                this.beard = this.isNakedNeck ? Beard.NN_BEARD : Beard.BIG_BEARD;
            } else {
                this.beard = Beard.NONE;
            }

            //Crest type
            if (!(gene[54] == 3 && gene[55] == 3)) {
                if (gene[54] == 3 || gene[55] == 3) {
                    if (gene[54] == 1 || gene[55] == 1) {
                        this.crestType = Crested.SMALL_CREST;
                    } else {
                        this.crestType = Crested.SMALL_FORWARDCREST;
                    }
                } else if (gene[54] != gene[55] || (gene[54] == 1 && gene[55] == 1)) {
                    this.crestType = Crested.BIG_CREST;
                } else {
                    this.crestType = Crested.BIG_FORWARDCREST;
                }
            }

            //Foot Feather Type
            if (!(gene[60] == 3 && gene[61] == 3)) {
                if (gene[60] == 1 || gene[61] == 1) {
                    if (gene[58] == 2 && gene[59] == 2) {
                        this.footFeatherType = FootFeathers.BIG_TOEFEATHERS;
                    } else if (gene[58] == 2 || gene[59] == 2 || (gene[58] == 1 && gene[58] == 1)) {
                        this.footFeatherType = FootFeathers.TOEFEATHERS;
                    } else if (gene[58] == 1 || gene[59] == 1) {
                        this.footFeatherType = FootFeathers.FOOTFEATHERS;
                    }
                } else {
                    if (gene[58] == 2 && gene[59] == 2) {
                        this.footFeatherType = FootFeathers.TOEFEATHERS;
                    } else if (gene[58] == 2 || gene[59] == 2 || (gene[58] == 1 && gene[58] == 1)) {
                        this.footFeatherType = FootFeathers.FOOTFEATHERS;
                    } else if (gene[58] == 1 || gene[59] == 1) {
                        this.footFeatherType = FootFeathers.LEGFEATHERS;
                    }
                }
            }

            if ((gene[50] == 2 || gene[51] == 2) && !(gene[50] == 1 || gene[51] == 1)) {
                if (gene[46] == 3 && gene[47] == 3 && gene[48] == 2 && gene[49] == 2) {
                    //v comb
                    this.comb = Comb.V;
                } else {
                    if (gene[48] == 2 && gene[49] == 2) {
                        //only waddles
                        this.comb = Comb.NONE;
                    }
                }
            } else {
                if (gene[48] == 1 || gene[49] == 1) {
                    if (gene[46] == 3 && gene[47] == 3) {
                        //peacomb
                        this.comb = Comb.PEA;
                    } else {
                        //walnut
                        this.comb = Comb.WALNUT;
                    }
                } else {
                    if (gene[46] == 3 && gene[47] == 3) {
                        //single comb
                        this.comb = Comb.SINGLE;
                    } else if (gene[46] == 1 || gene[47] == 1) {
                        //rose comb
//                        if (gene[46] == 3 || gene[47] == 3) {
//                            this.comb = Comb.HET_ROSE_ONE;
//                        } else {
                            this.comb = Comb.ROSE_ONE;
//                        }
                    } else {
                        //rose comb2
//                        if (gene[46] == 3 || gene[47] == 3) {
//                            this.comb = Comb.HET_ROSE_TWO;
//                        } else {
                            this.comb = Comb.ROSE_TWO;
//                        }
                    }
                }

                if (gene[50] == 2 || gene[51] == 2) {
                    this.butterCup = true;
                    this.combSize = -1;
                } else {
                    this.butterCup = gene[50] == 3 || gene[51] == 3;
                }

            }

            if ((gene[86] == 1 && gene[87] == 1) || (gene[86] == 3 && gene[87] == 3)){
                wingPlacement = 2;
            } else if (gene[86] == 1 || gene[87] == 1){
                wingPlacement = 1;
            }

            //comb size [80 and 81 small / 82 and 83 large]
            if (gene[80] == 2) {
                this.combSize = this.combSize + 1;
            }
            if (gene[81] == 2) {
                this.combSize = this.combSize + 1;
            }
            if (gene[82] == 1) {
                this.combSize = this.combSize + 1;
            }
            if (gene[83] == 1) {
                this.combSize = this.combSize + 1;
            }

            if (this.combSize < 0) {
                this.combSize = 0;
            }

            this.waddleSize = this.combSize;

            if (gene[84] == 1 && gene[85] == 1 && this.waddleSize > 0) {
                this.waddleSize = this.waddleSize - 1;
            }

            if (gene[146] == 2 && gene[147] == 2) {
                if (gene[148] == 2 && gene[149] == 2) {
                    //normal body
                    this.bodyType = 0;
                } else {
                    //big body
                    this.bodyType = 1;
                }
            } else if (gene[148] == 2 && gene[149] == 2) {
                if (gene[146] == 2 || gene[147] == 2) {
                    //normal body
                    this.bodyType = 0;
                } else {
                    //small body
                    this.longHockFeathers = true;
                    this.bodyType = -1;
                }
            } else {
                //normal body
                this.bodyType = 0;
            }

            if (gene[90] == 1 || gene[91] == 1){
                this.wingSize = 2;
            } else if (gene[92] == 1 || gene[93] == 1){
                this.wingSize = 1;
            }

            if (gene[168] == 2) {
                this.longLegs++;
            }
            if (gene[169] == 2) {
                this.longLegs++;
            }
        }

        private boolean isBearded() {
            return this.beard != Beard.NONE;
        }

        private boolean isCombed() { return this.comb != Comb.NONE && this.comb != Comb.BREDA_COMBLESS; }

        private boolean hasLongLegs() { return this.longLegs != 0; }

    }

    private enum Crested {
        BIG_CREST,
        BIG_FORWARDCREST,
        SMALL_CREST,
        SMALL_FORWARDCREST,
        NONE
    }

    private enum FootFeathers {
        BIG_TOEFEATHERS,
        TOEFEATHERS,
        FOOTFEATHERS,
        LEGFEATHERS,
        NONE
    }

    private enum Comb {
        SINGLE (false),
        ROSE_ONE (false),
        ROSE_TWO (false),
        PEA (true),
        WALNUT (true),
        V (false),
        HET_ROSE_ONE (false),
        HET_ROSE_TWO (false),
        BREDA_COMBLESS (false),
        NONE (true);

        boolean containsPeaComb;

        Comb(boolean effectsWaddleSize) {
            this.containsPeaComb = effectsWaddleSize;
        }

        public boolean hasPeaComb() {
            return this.containsPeaComb;
        }
    }

    private enum Beard {
        BIG_BEARD,
        SMALL_BEARD,
        NN_BEARD,
        NONE
    }

}
