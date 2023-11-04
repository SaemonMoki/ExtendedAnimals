package mokiyoki.enhancedanimals.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import mokiyoki.enhancedanimals.entity.EnhancedHorse;
import mokiyoki.enhancedanimals.model.modeldata.HorseModelData;
import mokiyoki.enhancedanimals.model.modeldata.HorsePhenotype;
import mokiyoki.enhancedanimals.model.modeldata.Phenotype;
import mokiyoki.enhancedanimals.model.util.WrappedModelPart;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.joml.Vector3f;

import java.util.Map;

@OnlyIn(Dist.CLIENT)
public class ModelEnhancedHorse<T extends EnhancedHorse> extends EnhancedAnimalModel<T> {
    protected WrappedModelPart theHorse;

    protected WrappedModelPart theHead;
    protected WrappedModelPart theEarLeft;
    protected WrappedModelPart theEarRight;
    protected WrappedModelPart theNeck;
    protected WrappedModelPart theBody;
    protected WrappedModelPart theLegFrontLeft;
    protected WrappedModelPart theLegFrontRight;
    protected WrappedModelPart theLegBackLeft;
    protected WrappedModelPart theLegBackRight;
    protected WrappedModelPart theTail;

    protected WrappedModelPart head;
    protected WrappedModelPart nose;
    protected WrappedModelPart jaw;

    protected WrappedModelPart earLeft;
    protected WrappedModelPart earTopLeft;

    protected WrappedModelPart earRight;
    protected WrappedModelPart earTopRight;

    protected WrappedModelPart neck;
    protected WrappedModelPart body;

    private WrappedModelPart legFrontLeft;
    private WrappedModelPart legFrontRight;
    private WrappedModelPart legBackLeft;
    private WrappedModelPart legBackRight;

    private WrappedModelPart tail;

    private HorseModelData horseModelData;

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition base = meshdefinition.getRoot().addOrReplaceChild("base", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition bHorse = base.addOrReplaceChild("bHorse", CubeListBuilder.create(), PartPose.offset(0.0F, 14.0F, 0.0F));
        PartDefinition bBody = bHorse.addOrReplaceChild("bBody", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition bNeck = bBody.addOrReplaceChild("bNeck", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition bHead = bNeck.addOrReplaceChild("bHead", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition bEarLeft = bHead.addOrReplaceChild("bEarL", CubeListBuilder.create(), PartPose.offset(4.0F, 0.0F, -3.0F));
        PartDefinition bEarRight = bHead.addOrReplaceChild("bEarR", CubeListBuilder.create(), PartPose.offset(-4.0F, 0.0F, -3.0F));
        PartDefinition bLegFrontLeft = bHorse.addOrReplaceChild("bLegFL", CubeListBuilder.create(), PartPose.offset(3.0F, 0.0F, -10.0F));
        PartDefinition bLegFrontRight = bHorse.addOrReplaceChild("bLegFR", CubeListBuilder.create(), PartPose.offset(-3.0F, 0.0F, -10.0F));
        PartDefinition bLegBackLeft = bHorse.addOrReplaceChild("bLegBL", CubeListBuilder.create(), PartPose.offset(3.0F, 0.0F, 9.0F));
        PartDefinition bLegBackRight = bHorse.addOrReplaceChild("bLegBR", CubeListBuilder.create(), PartPose.offset(-3.0F, 0.0F, 9.0F));
        PartDefinition bTail = bBody.addOrReplaceChild("bTail", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 22.0F));

        bHead.addOrReplaceChild("eyes", CubeListBuilder.create()
                        .texOffs(69, 15)
                        .addBox(2.5F, 0.0F, 0.0F, 1, 1, 1, new CubeDeformation(0.01F))
                        .texOffs(0, 40)
                        .addBox(-3.5F, 0.0F, 0.0F, 1, 1, 1, new CubeDeformation(0.01F)),
                PartPose.ZERO
        );

        bHead.addOrReplaceChild("head", CubeListBuilder.create()
                        .texOffs(49, 0)
                        .addBox(-3.5F, -5.0F, -4.0F, 7, 6, 7),
                PartPose.ZERO
        );
        bHead.addOrReplaceChild("nose", CubeListBuilder.create()
                        .texOffs(49, 22)
                        .addBox(-2.0F, -5.0F, -3.0F, 4, 6, 3),
                PartPose.ZERO
        );
        bHead.addOrReplaceChild("jaw", CubeListBuilder.create()
                        .texOffs(49, 22)
                        .addBox(-2.0F, -5.0F, -3.0F, 4, 6, 3),
                PartPose.offset(0.0F, 1.0F, -4.0F)
        );

        bEarLeft.addOrReplaceChild("earL", CubeListBuilder.create()
                        .texOffs(46, 0)
                        .addBox(-1.0F, -2.0F, 0.0F, 3, 4, 1),
                PartPose.ZERO
        );
        bEarLeft.addOrReplaceChild("earTL", CubeListBuilder.create()
                        .texOffs(46, 0)
                        .addBox(-1.0F, -3.0F, 0.0F, 3.5F, 5, 1),
                PartPose.ZERO
        );

        bEarRight.addOrReplaceChild("earR", CubeListBuilder.create()
                        .texOffs(70, 0)
                        .addBox(-2.0F, -2.0F, 0.0F, 3, 4, 1),
                PartPose.ZERO
        );
        bEarRight.addOrReplaceChild("earTR", CubeListBuilder.create()
                        .texOffs(70, 0)
                        .addBox(-2.5F, -3.0F, 0.0F, 3.5F, 5, 1),
                PartPose.ZERO
        );

        bNeck.addOrReplaceChild("neck", CubeListBuilder.create()
                        .texOffs(0, 0)
                        .addBox(-4.5F, -6.75F, -9.0F, 9, 7, 9),
                PartPose.ZERO
        );

        bBody.addOrReplaceChild("body", CubeListBuilder.create()
                        .texOffs(0, 23)
                        .addBox(-5.0F, 0.0F, 0.0F, 10, 11, 10),
                PartPose.offset(0.0F, 18.1F, -4.0F)
        );

        bLegFrontLeft.addOrReplaceChild("legFL", CubeListBuilder.create()
                        .texOffs(49, 32)
                        .addBox(-3.0F, 0.0F, 0.0F, 3, 8, 3),
                PartPose.ZERO
        );
        bLegFrontRight.addOrReplaceChild("legFR", CubeListBuilder.create()
                        .texOffs(61, 32)
                        .addBox(0.0F, 0.0F, 0.0F, 3, 8, 3),
                PartPose.ZERO
        );
        bLegBackLeft.addOrReplaceChild("legBL", CubeListBuilder.create()
                        .texOffs(49, 44)
                        .addBox(-3.0F, 0.0F, 0.0F, 3, 8, 3),
                PartPose.ZERO
        );
        bLegBackRight.addOrReplaceChild("legBR", CubeListBuilder.create()
                        .texOffs(61, 44)
                        .addBox(0.0F, 0.0F, 0.0F, 3, 8, 3),
                PartPose.ZERO
        );

        bTail.addOrReplaceChild("tail", CubeListBuilder.create()
                        .texOffs(36, 0)
                        .addBox(-0.5F, 0.0F, 0.0F, 1, 2, 1, new CubeDeformation(-0.05F)),
                PartPose.ZERO
        );

        return LayerDefinition.create(meshdefinition, 256, 256);
    }

    public ModelEnhancedHorse(ModelPart modelPart) {
        super(modelPart);
        ModelPart base = modelPart.getChild("base");
        ModelPart bHorse = base.getChild("bHorse");
        ModelPart bBody = bHorse.getChild("bBody");
        ModelPart bNeck = bBody.getChild("bNeck");
        ModelPart bHead = bNeck.getChild("bHead");
        ModelPart bEarLeft = bHead.getChild("bEarL");
        ModelPart bEarRight = bHead.getChild("bEarR");
        ModelPart bLegFL = bHorse.getChild("bLegFL");
        ModelPart bLegFR = bHorse.getChild("bLegFR");
        ModelPart bLegBL = bHorse.getChild("bLegBL");
        ModelPart bLegBR = bHorse.getChild("bLegBR");
        ModelPart bTail = bBody.getChild("bTail");

        this.theHorse = new WrappedModelPart(bHorse, "bHorse");
        this.theBody = new WrappedModelPart(bBody, "bBody");
        this.theNeck = new WrappedModelPart(bNeck, "bNeck");
        this.theHead = new WrappedModelPart(bHead, "bHead");
        this.theEarLeft = new WrappedModelPart(bEarLeft, "bEarL");
        this.theEarRight = new WrappedModelPart(bEarRight, "bEarR");
        this.theLegFrontLeft = new WrappedModelPart(bLegFL, "bLegFL");
        this.theLegFrontRight = new WrappedModelPart(bLegFR, "bLegFR");
        this.theLegBackLeft = new WrappedModelPart(bLegBL, "bLegBL");
        this.theLegBackRight = new WrappedModelPart(bLegBR, "bLegBR");
        this.theTail = new WrappedModelPart(bTail, "bTail");

        this.eyes = new WrappedModelPart("eyes", bHead);

        this.head = new WrappedModelPart("head", bHead);

        this.jaw = new WrappedModelPart("jaw", bHead);

        this.earLeft = new WrappedModelPart("earL", bEarLeft);
        this.earTopLeft = new WrappedModelPart("earTL", bEarLeft);

        this.earRight = new WrappedModelPart("earR", bEarRight);
        this.earTopRight = new WrappedModelPart("earTR", bEarRight);

        this.neck = new WrappedModelPart("neck", bNeck);

        this.body = new WrappedModelPart("body", bBody);

        this.legFrontLeft = new WrappedModelPart("legFL", bLegFL);
        this.legFrontRight = new WrappedModelPart("legFR", bLegFR);
        this.legBackLeft = new WrappedModelPart("legBL", bLegBL);
        this.legBackRight = new WrappedModelPart("legBR", bLegBR);

        this.tail = new WrappedModelPart("tail", bTail);

        this.theHorse.addChild(this.theBody);
        this.theBody.addChild(this.theNeck);
        this.theNeck.addChild(this.theHead);
        this.theHead.addChild(this.theEarLeft);
        this.theHead.addChild(this.theEarRight);
        this.theHorse.addChild(this.legFrontLeft);
        this.theHorse.addChild(this.legFrontRight);
        this.theHorse.addChild(this.legBackLeft);
        this.theHorse.addChild(this.legBackRight);

        this.theHead.addChild(this.head);
        this.theHead.addChild(this.eyes);
        this.theHead.addChild(this.nose);
        this.theHead.addChild(this.jaw);

        this.theBody.addChild(this.body);

        this.theLegFrontLeft.addChild(this.legFrontLeft);

        this.theLegFrontRight.addChild(this.legFrontRight);

        this.theLegBackLeft.addChild(this.legBackLeft);

        this.theLegBackRight.addChild(this.legBackRight);
    }

    private void resetCubes() {

    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        if (this.horseModelData!=null && this.horseModelData.getPhenotype() != null) {
            HorsePhenotype horse = horseModelData.getPhenotype();

            resetCubes();

            poseStack.pushPose();
            poseStack.scale(1.0F, 1.0F, 1.0F);
            poseStack.translate(0.0F, 0.0F, 0.0F);

            gaRender(this.theHorse, null, poseStack, vertexConsumer, packedLightIn, packedOverlayIn, red, green, blue, alpha);

            poseStack.popPose();
        }
    }

    protected Map<String, Vector3f> saveAnimationValues(T animal, HorsePhenotype horse) {
        Map<String, Vector3f> map = animal.getModelRotationValues();
        return map;
    }

    private void setupInitialAnimationValues(T entityIn, HorseModelData modelData, HorsePhenotype horse) {

    }

    @Override
    public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.horseModelData = getCreateHorseModelData(entityIn);
        HorsePhenotype horse = this.horseModelData.getPhenotype();
        float drive = ageInTicks + (1000 * this.horseModelData.random);

        if (horse != null) {

        }

    }
    private HorseModelData getCreateHorseModelData(T enhancedHorse) {
        return (HorseModelData) getCreateAnimalModelData(enhancedHorse);
    }

    @Override
    protected void setInitialModelData(T enhancedHorse) {
        HorseModelData horseModelData = new HorseModelData();
        setBaseInitialModelData(horseModelData, enhancedHorse);
    }

    @Override
    protected Phenotype createPhenotype(T enhancedAnimal) {
        return new HorsePhenotype(enhancedAnimal.getSharedGenes().getAutosomalGenes());
    }
}

//package mokiyoki.enhancedanimals.model;
//
//import com.mojang.blaze3d.vertex.PoseStack;
//import com.mojang.blaze3d.vertex.VertexConsumer;
//import mokiyoki.enhancedanimals.config.EanimodCommonConfig;
//import mokiyoki.enhancedanimals.entity.EnhancedHorse;
//import mokiyoki.enhancedanimals.items.CustomizableCollar;
//import mokiyoki.enhancedanimals.items.CustomizableSaddleEnglish;
//import mokiyoki.enhancedanimals.items.CustomizableSaddleVanilla;
//import mokiyoki.enhancedanimals.items.CustomizableSaddleWestern;
//import mokiyoki.enhancedanimals.model.util.ModelHelper;
//import mokiyoki.enhancedanimals.renderer.EnhancedRendererModelNew;
//import net.minecraft.client.model.EntityModel;
//import net.minecraft.client.model.geom.ModelPart;
//import net.minecraft.client.multiplayer.ClientLevel;
//import net.minecraft.world.SimpleContainer;
//import net.minecraft.world.item.ItemStack;
//import net.minecraft.util.Mth;
//import net.minecraftforge.api.distmarker.Dist;
//import net.minecraftforge.api.distmarker.OnlyIn;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//@OnlyIn(Dist.CLIENT)
//public class ModelEnhancedHorse <T extends EnhancedHorse> extends EntityModel<T> {
//    private Map<Integer, HorseModelData> horseModelDataCache = new HashMap<>();
//
//    private int clearCacheTimer = 0;
//
//    private final ModelPart chest1;
//    private final ModelPart chest2;
//
//    private final ModelPart head;
//    private final ModelPart noseArch0;
//    private final ModelPart noseArch1;
//    private final ModelPart noseArch2;
//    private final ModelPart nose0;
//    private final ModelPart nose1;
//    private final ModelPart nose2;
//    private final ModelPart eyeLeft;
//    private final ModelPart eyeRight;
//    private final ModelPart earL;
//    private final ModelPart earR;
//    private final ModelPart jaw0;
//    private final ModelPart jaw1;
//    private final ModelPart jaw2;
//    private final ModelPart tongue;
//    private final ModelPart maneJoiner;
//    private final ModelPart neck;
//    private final EnhancedRendererModelNew body;
//    private final ModelPart tail;
//    private final ModelPart leg1A;
//    private final ModelPart leg1B;
//    private final ModelPart leg2;
//    private final ModelPart leg3;
//    private final ModelPart leg4;
//    private final ModelPart hock3;
//    private final ModelPart hock4;
//    private final ModelPart hoof1;
//    private final ModelPart hoof2;
//    private final ModelPart hoof3;
//    private final ModelPart hoof4;
//    private final EnhancedRendererModelNew saddle;
//    private final EnhancedRendererModelNew saddleWestern;
//    private final EnhancedRendererModelNew saddleEnglish;
//    private final EnhancedRendererModelNew saddleHorn;
//    private final EnhancedRendererModelNew saddlePomel;
//    private final EnhancedRendererModelNew saddleSideL;
//    private final EnhancedRendererModelNew stirrup2DWideL;
//    private final EnhancedRendererModelNew stirrup2DWideR;
//    private final EnhancedRendererModelNew stirrup3DNarrowL;
//    private final EnhancedRendererModelNew stirrup3DNarrowR;
//    private final EnhancedRendererModelNew stirrup;
//    private final EnhancedRendererModelNew saddleSideR;
//    private final EnhancedRendererModelNew saddlePad;
//    private final ModelPart bridle;
//
//    private final List<EnhancedRendererModelNew> saddles = new ArrayList<>();
//
//    private Integer currentHorse = null;
//
//    public ModelEnhancedHorse() {
//        HorseModelData horseModelData = getHorseModelData();
//        int heightMod = horseModelData.heightMod;
//        this.texWidth = 256;
//        this.texHeight = 256;
//
//        this.head = new ModelPart(this, 0, 35);
//        this.head.addBox(-3.5F, -0.5F, -6.51F, 7, 7, 7, -0.5F);
//
//        this.noseArch0 = new ModelPart(this, 45, 0);
//        this.noseArch0.addBox(-2.5F, -3.5F, -3.5F, 5, 4, 4, -0.5F);
//
//        this.noseArch1 = new ModelPart(this, 45, 0);
//        this.noseArch1.addBox(-2.5F, -4.5F, -3.5F, 5, 5, 4, -0.5F);
//
//        this.noseArch2 = new ModelPart(this, 45, 0);
//        this.noseArch2.addBox(-2.5F, -5.5F, -3.5F, 5, 6, 4, -0.5F);
//
//        this.nose0 = new ModelPart(this, 45, 10);
//        this.nose0.addBox(-2.5F, -3.6F, -3.6F, 5, 4, 4, -0.4F);
//
//        this.nose1 = new ModelPart(this, 45, 10);
//        this.nose1.addBox(-2.5F, -4.6F, -3.6F, 5, 5, 4, -0.4F);
//
//        this.nose2 = new ModelPart(this, 45, 10);
//        this.nose2.addBox(-2.5F, -5.6F, -3.6F, 5, 6, 4, -0.4F);
//
////        this.head.setTextureOffset(0, 0);
////        this.head.addBox(-4.01F, 0.0F, -6.0F, 3, 4, 4, -1.0F);
////        this.head.addBox(1.01F, 0.0F, -6.0F, 3, 4, 4, -1.0F);
//
//        this.head.texOffs(94, 0);
//        this.head.addBox(-1.5F, -1.5F, -4.5F, 3, 3, 6, -0.5F); //mane piece 1
//        this.head.setPos(0.0F, -14.0F, -1.0F);
//
//        this.eyeLeft = new ModelPart(this, 0, 0);
//        this.eyeLeft.addBox(2.01F, 0.0F, -7.01F, 0, 4, 4, -1.0F);
//
//        this.eyeRight = new ModelPart(this, 3, 0);
//        this.eyeRight.addBox(-2.01F, 0.0F, -7.01F, 0, 4, 4, -1.0F);
//
//        this.earL = new ModelPart(this, 6, 0);
//        this.earL.addBox(-2.0F, -3.0F, -0.5F, 2, 3, 1);
//        this.earL.setPos(-1.0F, 0.0F, -1.0F);
//
//        this.earR = new ModelPart(this, 0, 0);
//        this.earR.addBox(0.0F, -3.0F, -0.5F, 2, 3, 1);
//        this.earR.setPos(1.0F, 0.0F, -1.0F);
//
//        this.jaw0 = new ModelPart(this, 72,0);
//        this.jaw0.addBox(-2.0F, -9.0F, -6.0F, 4, 7, 4, -0.1F);
//
//        this.jaw1 = new ModelPart(this, 72,0);
//        this.jaw1.addBox(-2.0F, -10.0F, -6.0F, 4, 8, 4, -0.1F);
//
//        this.jaw2 = new ModelPart(this, 72,0);
//        this.jaw2.addBox(-2.0F, -11.0F, -6.0F, 4, 9, 4, -0.1F);
//
//        this.tongue = new ModelPart(this, 0, 15);
//        this.tongue.addBox(-2.0F, 2.25F, -9.0F, 4, 1, 7, -0.11F);
//        this.tongue.setPos(0.0F, 0.0F, -1.5F);
//
//        this.maneJoiner = new ModelPart(this, 98, 9);
//        this.maneJoiner.addBox(-1.5F, -1.49F, -0.49F, 3, 2, 2, -0.51F); //mane piece 2
//        this.maneJoiner.setPos(0.0F, -13.0F, 0.0F);
//
//        this.neck = new ModelPart(this, 69, 15);
//        this.neck.addBox(-2.5F, -14.0F, -7.0F, 5, 17, 8, -1.0F);
//        this.neck.texOffs(97, 13);
//        this.neck.addBox(-1.5F, -13.5F, -0.5F, 3, 18, 3, -0.5F); // mane piece 3
//        this.neck.setPos(0.0F, 1.0F, -5.0F);
//
//        this.body = new EnhancedRendererModelNew(this, 0, 0, "Body");
//        this.body.addBox(-5.5F, -0.5F, -10.5F, 11, 11, 23, -0.5F);
//        this.body.setPos(0.0F, 1.0F, 0.0F);
//
//        this.tail = new ModelPart(this, 29, 15);
//        this.tail.addBox(-0.5F, 0.0F, -1.0F, 1, 4, 1, 0.0F);
//        this.tail.setPos(0.0F, 0.0F, 12.0F);
//
//        this.leg1A = new ModelPart(this, 6, 53);
//        this.leg1A.addBox(0.0F, 0.5F, 0.0F, 5, 8, 5, -1.0F);  //13
//        this.leg1A.setPos(-6.0F, 9.5F, -9.0F);
//
//        this.leg1B = new ModelPart(this, 6, 53);
//        this.leg1B.addBox(0.0F, -1.0F, -2.0F, 5, 7, 5, -1.001F);  //13
//        this.leg1B.setPos(0.0F, 7.5F, 2.0F);
//
//        this.leg2 = new ModelPart(this, 26, 53);
//        this.leg2.addBox(0.0F, 0.5F, 0.0F, 5, 13 - heightMod, 5, -1.0F);
//        this.leg2.setPos(1.0F, 9.5F, -9.0F);
//
//        this.leg3 = new ModelPart(this, 46, 53);
//        this.leg3.addBox(0.0F, 2.0F, 0.0F, 5, 12 - heightMod, 5, -1.0F);  //12
//        this.leg3.setPos(-6.0F, 9.5F, 8.0F);
//
//        this.leg4 = new ModelPart(this, 66, 53);
//        this.leg4.addBox(0.0F, 2.0F, 0.0F, 5, 12 - heightMod, 5, -1.0F);
//        this.leg4.setPos(1.0F, 9.5F, 8.0F);
//
//        this.hock3 = new ModelPart(this, 47, 41);
//        this.hock3.addBox(0.75F, -4.0F, -0.75F, 4, 7, 5, 0.0F);
//        this.hock3.setPos(-6.0F, 9.5F, 8.0F);
//
//        this.hock4 = new ModelPart(this, 67, 41);
//        this.hock4.addBox(0.25F, -4.0F, -0.75F, 4, 7, 5, 0.0F);
//        this.hock4.setPos(1.0F, 9.5F, 8.0F);
//
//        this.hoof1 = new ModelPart(this, 6, 71);
//        this.hoof1.addBox(0.0F, -0.5F, -2.4F, 5, 3, 4, -0.5F);
//        this.hoof1.setPos(0.0F, 4.75F, 0.5F);
//
//        this.hoof2 = new ModelPart(this, 26, 71);
//        this.hoof2.addBox(0.0F, 11.75F, 0.1F, 5, 3, 4, -0.5F);
//
//        this.hoof3 = new ModelPart(this, 46, 71);
//        this.hoof3.addBox(0.0F, 11.75F, 0.1F, 5, 3, 4, -0.5F);
//
//        this.hoof4 = new ModelPart(this, 66, 71);
//        this.hoof4.addBox(0.0F, 11.75F, 0.1F, 5, 3, 4, -0.5F);
//
//        this.neck.addChild(head);
//        this.neck.addChild(maneJoiner);
//        this.head.addChild(earL);
//        this.head.addChild(earR);
//        this.head.addChild(jaw0);
//        this.head.addChild(jaw1);
//        this.head.addChild(jaw2);
//        this.jaw0.addChild(tongue);
//        this.jaw1.addChild(tongue);
//        this.jaw2.addChild(tongue);
//        this.head.addChild(eyeLeft);
//        this.head.addChild(eyeRight);
//        this.leg1A.addChild(this.leg1B);
//        this.leg1B.addChild(this.hoof1);
//
//            /**
//             * Equipment stuff
//             */
//
//        this.chest1 = new ModelPart(this, 188, 0);
//        this.chest1.addBox(-3.0F, 0.0F, 0.0F, 8, 8, 3);
//        this.chest1.setPos(-8.0F, 1.0F, 8.0F);
//        this.chest1.yRot = ((float)Math.PI / 2F);
//        this.chest2 = new ModelPart(this, 188, 11);
//        this.chest2.addBox(-3.0F, 0.0F, 0.0F, 8, 8, 3);
//        this.chest2.setPos(5.0F, 1.0F, 8.0F);
//        this.chest2.yRot = ((float)Math.PI / 2F);
//
//        this.saddle = new EnhancedRendererModelNew(this, 194, 24, "Saddle");
//        this.saddle.addBox(-8.0F, -1.0F, -6.0F, 16, 10, 15, -1.0F);
//
//        this.saddleWestern = new EnhancedRendererModelNew(this, 210, 0, "WesternSaddle");
//        this.saddleWestern.addBox(-5.0F, -2.0F, -5.0F, 10, 2, 13, 0.0F);
//        this.saddleWestern.texOffs(210, 15);
//        this.saddleWestern.addBox(-4.0F, -3.0F, 5.0F, 8, 2, 4, 0.0F);
//        this.saddleWestern.texOffs(230, 15);
//        this.saddleWestern.addBox(-3.5F, -4.0F, 8.0F, 7, 2, 2, 0.0F);
//
//        this.saddleEnglish = new EnhancedRendererModelNew(this, 211, 1, "EnglishSaddle");
//        this.saddleEnglish.addBox(-5.0F, -1.0F, -4.0F, 10, 2, 12, 0.0F);
//        this.saddleEnglish.texOffs(210, 15);
//        this.saddleEnglish.addBox(-4.0F, -1.5F, 5.0F, 8, 2, 4, 0.0F);
//        this.saddleEnglish.texOffs(230, 15);
//        this.saddleEnglish.addBox(-3.5F, -2.0F, 7.5F, 7, 2, 2, 0.0F);
//
//        this.saddleHorn = new EnhancedRendererModelNew(this, 234, 19, "SaddleHorn");
//        this.saddleHorn.addBox(-4.0F, -2.0F, -3.0F, 8, 2, 3, 0.0F);
//
//        this.saddlePomel = new EnhancedRendererModelNew(this, 243, 0, "SaddlePomel");
//        this.saddlePomel.addBox(-1.0F, -3.0F, -2.0F, 2, 4, 2, -0.25F);
//        this.saddlePomel.setPos(0.0F, -2.0F, -2.0F);
//
//        this.saddleSideL = new EnhancedRendererModelNew(this, 234, 49, "SaddleLeft");
//        this.saddleSideL.addBox(0.0F, 0.0F, 0.0F, 3, 4, 8);
//
//        this.saddleSideR = new EnhancedRendererModelNew(this, 234, 61, "SaddleRight");
//        this.saddleSideR.addBox(-3.0F, 0.0F, 0.0F, 3, 4, 8);
//
//        this.stirrup2DWideL = new EnhancedRendererModelNew(this, 248, 24, "2DStirrupL");
//        this.stirrup2DWideL.addBox(0.0F, 0.0F, 0.0F, 0, 10, 4); // strap
//
//        this.stirrup2DWideR = new EnhancedRendererModelNew(this, 248, 24, "2DStirrupR");
//        this.stirrup2DWideR.addBox(0.0F, 0.0F, 0.0F, 0, 10, 4); // strap
//
//        this.stirrup3DNarrowL = new EnhancedRendererModelNew(this, 249, 27, "3DStirrupL");
//        this.stirrup3DNarrowL.addBox(-1.0F, 0.0F, 0.0F, 1, 10, 1); // strap
//
//        this.stirrup3DNarrowR = new EnhancedRendererModelNew(this, 251, 27, "3DStirrupR");
//        this.stirrup3DNarrowR.addBox(0.0F, 0.0F, 0.0F, 1, 10, 1);
//
//        this.stirrup = new EnhancedRendererModelNew(this, 210, 0, "Stirrup");
//        this.stirrup.addBox(-0.5F, 9.5F, -1.0F, 1, 1, 1);
//        this.stirrup.texOffs(214, 0);
//        this.stirrup.addBox(-0.5F, 9.5F, 1.0F, 1, 1, 1);
//        this.stirrup.texOffs(210, 2);
//        this.stirrup.addBox(-0.5F, 10.5F, -1.5F, 1, 3, 1);
//        this.stirrup.texOffs(214, 2);
//        this.stirrup.addBox(-0.5F, 10.5F, 1.5F, 1, 3, 1);
//        this.stirrup.texOffs(211, 7);
//        this.stirrup.addBox(-0.5F, 12.5F, -0.5F, 1, 1, 2);
//
//        this.saddlePad = new EnhancedRendererModelNew(this, 194, 24, "SaddlePad");
//        this.saddlePad.addBox(-8.0F, -1.0F, -6.0F, 16, 10, 15, -1.0F);
//
//        this.body.addChild(saddle);
//        this.saddle.addChild(stirrup3DNarrowL);
//        this.saddle.addChild(stirrup3DNarrowR);
//        this.saddleHorn.addChild(saddlePomel);
//
//        //western
//        this.body.addChild(saddleWestern);
//        this.saddleWestern.addChild(saddleHorn);
//        this.saddleWestern.addChild(saddleSideL);
//        this.saddleWestern.addChild(saddleSideR);
//        this.saddleWestern.addChild(saddlePad);
//        this.saddleWestern.addChild(stirrup2DWideL);
//        this.saddleWestern.addChild(stirrup2DWideR);
//        this.stirrup2DWideL.addChild(stirrup);
//        this.stirrup2DWideR.addChild(stirrup);
//        //english
//        this.body.addChild(saddleEnglish);
//        this.saddleEnglish.addChild(saddleHorn);
//        this.saddleEnglish.addChild(saddleSideL);
//        this.saddleEnglish.addChild(saddleSideR);
//        this.saddleEnglish.addChild(saddlePad);
//        this.saddleEnglish.addChild(stirrup3DNarrowL);
//        this.saddleEnglish.addChild(stirrup3DNarrowR);
//        this.stirrup3DNarrowL.addChild(stirrup);
//        this.stirrup3DNarrowR.addChild(stirrup);
//
//        this.texWidth = 128;
//        this.texHeight = 128;
//
//        this.bridle = new ModelPart(this, 0, 53);
//        this.bridle.addBox(-3.5F, -0.0F, -6.01F, 7, 6, 6, 0.05F);
//        this.bridle.texOffs(0, 40);
//        this.bridle.addBox(-2.5F, -0.0F, -10.0F, 5, 6, 5, 0.05F);
//        this.head.addChild(this.bridle);
//        this.head.addChild(this.noseArch0);
//        this.head.addChild(this.noseArch1);
//        this.head.addChild(this.noseArch2);
//        this.noseArch0.addChild(this.nose0);
//        this.noseArch0.addChild(this.nose1);
//        this.noseArch0.addChild(this.nose2);
//        this.noseArch1.addChild(this.nose0);
//        this.noseArch1.addChild(this.nose1);
//        this.noseArch1.addChild(this.nose2);
//        this.noseArch2.addChild(this.nose0);
//        this.noseArch2.addChild(this.nose1);
//        this.noseArch2.addChild(this.nose2);
//    }
//
//    @Override
//    public void renderToBuffer(PoseStack matrixStackIn, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
//        HorseModelData horseModelData = getHorseModelData();
//
//        if (horseModelData.sleeping) {
//            this.eyeLeft.visible = false;
//            this.eyeRight.visible = false;
//        } else {
//            this.eyeLeft.visible = true;
//            this.eyeRight.visible = true;
//        }
//
//        this.noseArch0.visible = false;
//        this.noseArch1.visible = false;
//        this.noseArch2.visible = false;
//        this.nose0.visible = false;
//        this.nose1.visible = false;
//        this.nose2.visible = false;
//        this.jaw0.visible = false;
//        this.jaw1.visible = false;
//        this.jaw2.visible = false;
//        if (horseModelData.faceShape >= 0) {
//            switch (horseModelData.faceLength) {
//                case 0:
//                    this.noseArch0.visible = true;
//                    this.jaw0.visible = true;
//                    break;
//                case 1:
//                    this.noseArch1.visible = true;
//                    this.jaw1.visible = true;
//                    break;
//                default:
//                case 2:
//                    this.noseArch2.visible = true;
//                    this.jaw2.visible = true;
//                    break;
//            }
//            this.nose0.visible = true;
//        } else {
//            switch (horseModelData.faceLength) {
//                case 0:
//                    this.nose0.visible = true;
//                    this.jaw0.visible = true;
//                    break;
//                case 1:
//                    this.nose1.visible = true;
//                    this.jaw1.visible = true;
//                    break;
//                default:
//                case 2:
//                    this.nose2.visible = true;
//                    this.jaw2.visible = true;
//                    break;
//            }
//            this.noseArch0.visible = true;
//        }
//
//        this.chest1.visible = false;
//        this.chest2.visible = false;
//        if (horseModelData.hasChest) {
//            this.chest1.visible = true;
//            this.chest2.visible = true;
//            this.chest1.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
//            this.chest2.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
//        }
//
//        this.bridle.visible = true;
//
////        this.head.render(scale);
//        this.neck.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
//        renderBodyandSaddle(horseModelData.unrenderedModels, matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
//        this.tail.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
//        this.leg1A.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
//        this.leg2.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
//        this.leg3.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
//        this.leg4.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
//        this.hock3.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
//        this.hock4.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
////        this.hoof1.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
//        this.hoof2.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
//        this.hoof3.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
//        this.hoof4.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
//
//    }
//
//    private void renderBodyandSaddle(List<String> unrenderedModels, PoseStack matrixStackIn, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
//            Map<String, List<Float>> mapOfScale = new HashMap<>();
//            float saddleScale = 0.75F;
//
//            this.saddleWestern.visible = false;
//            this.saddleEnglish.visible = false;
//            this.saddle.visible = false;
//            this.saddlePomel.visible = false;
//
////            float antiScale = 1.25F;
//            List<Float> scalingsForSaddle = ModelHelper.createScalings(saddleScale,saddleScale,saddleScale, 0.0F, -saddleScale*0.01F, (saddleScale - 1.0F)*0.04F);
////            List<Float> scalingsForPad = createScalings(antiScale, 0.0F, -antiScale*0.01F, (antiScale - 1.0F)*0.04F);
////            mapOfScale.put("SaddlePad", scalingsForPad);sadd
//
//            if (getHorseModelData().saddle.getItem() instanceof CustomizableSaddleWestern) {
//                this.saddleWestern.visible = true;
//                this.saddlePomel.visible = true;
//                mapOfScale.put("WesternSaddle", scalingsForSaddle);
//            } else if (getHorseModelData().saddle.getItem() instanceof CustomizableSaddleEnglish) {
//                this.saddleEnglish.visible = true;
//                mapOfScale.put("EnglishSaddle", scalingsForSaddle);
//            } else if (getHorseModelData().saddle.getItem() instanceof CustomizableSaddleVanilla){
//                //vanilla saddle
//                this.saddle.visible = true;
//                mapOfScale.put("Saddle", scalingsForSaddle);
//            }
//            this.body.render(matrixStackIn, bufferIn , mapOfScale, unrenderedModels, false, packedLightIn, packedOverlayIn, red, green, blue, alpha);
//    }
//
//    @Override
//    public void prepareMobModel(T entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTickTime) {
//        HorseModelData horseModelData = getCreateHorseModelData(entitylivingbaseIn);
//        this.currentHorse = entitylivingbaseIn.getId();
//        int heightMod = horseModelData.heightMod;
//
//        char[] uuidArry = horseModelData.uuidArray;
//
//        if (uuidArry != null) {
//            if (Character.isDigit(uuidArry[16])){
//                if (uuidArry[16] - 48 == 0) {
//                    //0
//                    this.hock3.z = 8.5F;
//                    this.hock4.z = 8.5F;
//                } else if (uuidArry[16] - 48 == 1) {
//                    //1
//                    this.hock3.z = 8.4F;
//                    this.hock4.z = 8.4F;
//                } else if (uuidArry[16] - 48 == 2) {
//                    //2
//                    this.hock3.z = 8.3F;
//                    this.hock4.z = 8.3F;
//                } else if (uuidArry[16] - 48 == 3) {
//                    //3
//                    this.hock3.z = 8.2F;
//                    this.hock4.z = 8.2F;
//                } else if (uuidArry[16] - 48 == 4) {
//                    //4
//                    this.hock3.z = 8.1F;
//                    this.hock4.z = 8.1F;
//                } else if (uuidArry[16] - 48 == 5) {
//                    //5
//                    this.hock3.z = 8.0F;
//                    this.hock4.z = 8.0F;
//                } else if (uuidArry[16] - 48 == 6) {
//                    //6
//                    this.hock3.z = 7.9F;
//                    this.hock4.z = 7.9F;
//                } else if (uuidArry[16] - 48 == 7) {
//                    //7
//                    this.hock3.z = 7.8F;
//                    this.hock4.z = 7.8F;
//                } else if (uuidArry[16] - 48 == 8) {
//                    //0
//                    this.hock3.z = 8.5F;
//                    this.hock4.z = 8.5F;
//                } else {
//                    //1
//                    this.hock3.z = 8.4F;
//                    this.hock4.z = 8.4F;
//                }
//            } else {
//                char test = uuidArry[16];
//                switch (test){
//                    case 'a':
//                        //2
//                        this.hock3.z = 8.3F;
//                        this.hock4.z = 8.3F;
//                        break;
//                    case 'b':
//                        //3
//                        this.hock3.z = 8.2F;
//                        this.hock4.z = 8.2F;
//                        break;
//                    case 'c':
//                        //4
//                        this.hock3.z = 8.1F;
//                        this.hock4.z = 8.1F;
//                        break;
//                    case 'd':
//                        //5
//                        this.hock3.z = 8.0F;
//                        this.hock4.z = 8.0F;
//                        break;
//                    case 'e':
//                        //6
//                        this.hock3.z = 7.9F;
//                        this.hock4.z = 7.9F;
//                        break;
//                    case 'f':
//                        //7
//                        this.hock3.z = 7.8F;
//                        this.hock4.z = 7.8F;
//                        break;
//                }
//            }
//
//            if (Character.isDigit(uuidArry[17])){
//                if (uuidArry[17] - 48 == 0) {
//                    //a
//                    this.leg3.z = this.hock3.z + 1.5F;
//                    this.leg4.z = this.hock4.z + 1.5F;
//                } else if (uuidArry[17] - 48 == 1) {
//                    //b
//                    this.leg3.z = this.hock3.z + 1.35F;
//                    this.leg4.z = this.hock4.z + 1.35F;
//                } else if (uuidArry[17] - 48 == 2) {
//                    //c
//                    this.leg3.z = this.hock3.z + 1.25F;
//                    this.leg4.z = this.hock4.z + 1.25F;
//                } else if (uuidArry[17] - 48 == 3) {
//                    //d
//                    this.leg3.z = this.hock3.z + 1.15F;
//                    this.leg4.z = this.hock4.z + 1.15F;
//                } else if (uuidArry[17] - 48 == 4) {
//                    //e
//                    this.leg3.z = this.hock3.z + 1.05F;
//                    this.leg4.z = this.hock4.z + 1.05F;
//                } else if (uuidArry[17] - 48 == 5) {
//                    //f
//                    this.leg3.z = this.hock3.z + 0.95F;
//                    this.leg4.z = this.hock4.z + 0.95F;
//                } else if (uuidArry[17] - 48 == 6) {
//                    //g
//                    this.leg3.z = this.hock3.z + 0.85F;
//                    this.leg4.z = this.hock4.z + 0.85F;
//                } else if (uuidArry[17] - 48 == 7) {
//                    //h
//                    this.leg3.z = this.hock3.z + 0.75F;
//                    this.leg4.z = this.hock4.z + 0.75F;
//                } else if (uuidArry[17] - 48 == 8) {
//                    //a
//                    this.leg3.z = this.hock3.z + 1.5F;
//                    this.leg4.z = this.hock4.z + 1.5F;
//                } else {
//                    //b
//                    this.leg3.z = this.hock3.z + 1.35F;
//                    this.leg4.z = this.hock4.z + 1.35F;
//                }
//            } else {
//                char test = uuidArry[17];
//                switch (test){
//                    case 'a':
//                        //c
//                        this.leg3.z = this.hock3.z + 1.25F;
//                        this.leg4.z = this.hock4.z + 1.25F;
//                        break;
//                    case 'b':
//                        //d
//                        this.leg3.z = this.hock3.z + 1.15F;
//                        this.leg4.z = this.hock4.z + 1.15F;
//                        break;
//                    case 'c':
//                        //e
//                        this.leg3.z = this.hock3.z + 1.05F;
//                        this.leg4.z = this.hock4.z + 1.05F;
//                        break;
//                    case 'd':
//                        //f
//                        this.leg3.z = this.hock3.z + 0.95F;
//                        this.leg4.z = this.hock4.z + 0.95F;
//                        break;
//                    case 'e':
//                        //g
//                        this.leg3.z = this.hock3.z + 0.85F;
//                        this.leg4.z = this.hock4.z + 0.85F;
//                        break;
//                    case 'f':
//                        //h
//                        this.leg3.z = this.hock3.z + 0.75F;
//                        this.leg4.z = this.hock4.z + 0.75F;
//                        break;
//                }
//            }
//        }
//
//        this.tongue.xRot = (float)Math.PI * -0.5F;
//
//        /**
//         * other
//         */
//
//        this.leg1B.xRot = (float)Math.PI * 0.4F;
//        this.hoof1.xRot = (float)Math.PI * 0.2F;
//
//        /**
//         *  experimental head variations
//         */
//
////        float noseAngle = horseModelData.faceShape; // dish-roman [-0.175, 0.075]
//        float noseAngle = 0.0F; // dish-roman [-0.175, 0.075]
//
//
//        this.noseArch0.setPos(0,0, -6.0F);
////        this.nose0.rotationPointZ = noseAngle < 0 ? (-3.0F - (noseAngle * 5.0F)) : -3.0F;
//
//        this.noseArch0.xRot = (float)Math.PI/2.0F + (float)Math.PI * (Math.abs(noseAngle));
//        this.nose0.xRot = ((float)Math.PI * (noseAngle < 0 ? (3.0F*noseAngle/2.0F) : (2.0F*noseAngle/3.0F)));
////        this.jaw0.rotateAngleX = (-0.175F + (noseAngle < 0 ? noseAngle/2.0F : ((float)Math.PI * (2.0F*noseAngle/3.0F)))) + (float)Math.PI/2.0F;
//
//        ModelHelper.copyModelRotations(noseArch0, noseArch1, noseArch2);
//        ModelHelper.copyModelRotations(nose0, nose1, nose2);
//
//        this.noseArch1.z = this.noseArch0.z;
//        this.noseArch2.z = this.noseArch0.z;
//
//        if (noseAngle >= 0) {
//            this.nose0.y = -3.0F + -horseModelData.faceLength;
//        } else {
//            this.nose0.y = -3.0F + (-noseAngle * 5.0F);
//        }
//
//        this.nose1.setPos(0.0F, this.nose0.y, 0);
//        this.nose2.setPos(0.0F, this.nose0.y, 0);
//
//        this.jaw0.xRot = (float)Math.PI * 0.45F;
////        this.jaw0.rotateAngleX = (float)Math.PI * 0.41F;
////        this.jaw0.rotationPointZ = -1.25F;
//        this.jaw0.z = -2.0F;
//
//        this.jaw1.z = this.jaw0.z;
//        this.jaw2.z = this.jaw0.z;
//        this.jaw1.xRot = this.jaw0.xRot;
//        this.jaw2.xRot = this.jaw0.xRot;
//
////        int jawLengthMod = horseModelData.faceShape > 0 ? (int)(2.9999F*(-horseModelData.faceShape)/0.175F) : 0;
////
////        jawLengthMod = horseModelData.faceLength - jawLengthMod;
////
////        switch (jawLengthMod) {
////            default:
////            case 0 :
////                this.tongue.rotationPointZ = -2.0F - (noseAngle >= 0 ? noseAngle * -2.0F : noseAngle * 2.75F);
////                this.jaw0.setRotationPoint(0.0F, 0.70F - (noseAngle >= 0 ? 0.0F : noseAngle), this.tongue.rotationPointZ);
////                break;
////            case 1 :
////                this.tongue.rotationPointZ = -3.0F - (noseAngle >= 0 ? 0.0F : noseAngle * 2.75F);
////                this.jaw1.setRotationPoint(0.0F, 0.70F - (noseAngle >= 0 ? 0.0F : noseAngle), this.tongue.rotationPointZ);
////                break;
////            case 2 :
////                this.tongue.rotationPointZ = -4.0F - (noseAngle >= 0 ? 0.0F : noseAngle * 2.75F);
////                this.jaw2.setRotationPoint(0.0F, 0.70F - (noseAngle >= 0 ? 0.0F : noseAngle), this.tongue.rotationPointZ);
////                break;
////
////        }
//
//        /**
//         *  experimental size variations
//         */
//
//        this.body.y = 1.0F + heightMod;
//        this.neck.y = 1.0F + heightMod + (int)(heightMod/2);
//        this.neck.z = -5.0F  + (int)(heightMod/2);
//        this.hock3.y = 9.5F + heightMod;
//        this.hock4.y = 9.5F + heightMod;
//        this.tail.y = 0.0F + heightMod;
//        this.leg1A.y = 9.5F + heightMod;
//        this.leg2.y = 9.5F + heightMod;
//        this.leg3.y = 9.5F + heightMod;
//        this.leg4.y = 9.5F + heightMod;
//        this.chest1.y = 1.0F + heightMod;
//        this.chest2.y = 1.0F + heightMod;
//
//    }
//
//    @Override
//    public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
//        HorseModelData horseModelData = getHorseModelData();
//
//        if (horseModelData!=null) {
//            int heightMod = horseModelData.heightMod;
//            this.neck.xRot = headPitch * 0.017453292F + 0.8F;
//            this.neck.yRot = ((netHeadYaw * 0.017453292F) * 0.40F);
//            this.head.yRot = ((netHeadYaw * 0.017453292F) * 0.14F);
//            this.maneJoiner.yRot = ((netHeadYaw * 0.017453292F) * 0.07F);
//
//            this.tail.xRot = 0.6F;
//
//            this.leg1A.xRot = Mth.cos(limbSwing * 0.3332F) * 1.4F * limbSwingAmount;
//            this.leg2.xRot = Mth.cos(limbSwing * 0.3332F + (float) Math.PI) * 1.4F * limbSwingAmount;
//            this.hock3.xRot = Mth.cos(limbSwing * 0.3332F + (float) Math.PI) * 1.4F * limbSwingAmount;
//            this.leg3.xRot = Mth.cos(limbSwing * 0.3332F + (float) Math.PI) * 1.4F * limbSwingAmount;
//            this.hock4.xRot = Mth.cos(limbSwing * 0.3332F) * 1.4F * limbSwingAmount;
//            this.leg4.xRot = Mth.cos(limbSwing * 0.3332F) * 1.4F * limbSwingAmount;
//
//    //        ModelHelper.copyModelPositioning(leg1A, hoof1);
//            ModelHelper.copyModelPositioning(leg2, hoof2);
//            ModelHelper.copyModelPositioning(leg3, hoof3);
//            ModelHelper.copyModelPositioning(leg4, hoof4);
//
//            this.hoof1.y = this.hoof1.y - heightMod;
//            this.hoof2.y = this.hoof2.y - heightMod;
//            this.hoof3.y = this.hoof3.y - heightMod;
//            this.hoof4.y = this.hoof4.y - heightMod;
//
//            this.earL.zRot = -0.15F;
//            this.earR.zRot = 0.15F;
//
//            Item saddle = horseModelData.saddle.getItem();
//            if (saddle instanceof CustomizableSaddleWestern) {
//                this.saddleSideL.setPos(5.0F, -1.0F, -5.25F);
//                this.saddleSideR.setPos(-5.0F, -1.0F, -5.25F);
//                this.saddleHorn.setPos(0.0F, -2.0F, -2.0F);
//                this.saddleHorn.xRot = (float)Math.PI / 8.0F;
//                this.saddlePomel.setPos(0.0F, -1.5F, -0.5F);
//                this.saddlePomel.xRot = -0.2F;
//                this.stirrup2DWideL.setPos(7.5F, 0.0F, -3.5F);
//                this.stirrup2DWideR.setPos(-7.5F, 0.0F, -3.5F);
//            } else if (saddle instanceof CustomizableSaddleEnglish) {
//                this.saddleSideL.setPos(3.25F, -0.5F, -4.0F);
//                this.saddleSideR.setPos(-3.25F, -0.5F, -4.0F);
//                this.saddleHorn.setPos(0.0F, -1.0F, -1.0F);
//                this.saddleHorn.xRot = (float)Math.PI / 4.5F;
//                this.stirrup3DNarrowL.setPos(7.25F, -0.25F, -1.5F);
//                this.stirrup3DNarrowR.setPos(-7.25F, -0.25F, -1.5F);
//            } else if (saddle instanceof CustomizableSaddleVanilla){
//                this.stirrup3DNarrowL.setPos(8.0F, 0.0F, 0.0F);
//                this.stirrup3DNarrowR.setPos(-8.0F, 0.0F, 0.0F);
//            }
//        }
//    }
//
//    private class HorseModelData {
//        int[] horseGenes;
//        char[] uuidArray;
//        String birthTime;
//        float size = 1.0F;
//        boolean isFemale;
//        int lastAccessed = 0;
//        boolean sleeping = false;
//        int blink = 0;
//        long clientGameTime = 0;
//        List<String> unrenderedModels = new ArrayList<>();
//        ItemStack saddle;
//        boolean bridle = false;
//        boolean harness = false;
//        boolean collar = false;
//        boolean hasChest = false;
//        int heightMod = 0; //[-2 to 5]
//        float faceShape = 0.0F;
//        int faceLength = 0;
//        int adultAge;
//    }
//
//    private HorseModelData getHorseModelData() {
//        if (this.currentHorse == null || !horseModelDataCache.containsKey(this.currentHorse)) {
//            return new HorseModelData();
//        }
//        return horseModelDataCache.get(this.currentHorse);
//    }
//
//    private HorseModelData getCreateHorseModelData(T enhancedHorse) {
//        clearCacheTimer++;
//        if(clearCacheTimer > 50000) {
//            horseModelDataCache.values().removeIf(value -> value.lastAccessed==1);
//            for (HorseModelData horseModelData : horseModelDataCache.values()){
//                horseModelData.lastAccessed = 1;
//            }
//            clearCacheTimer = 0;
//        }
//
//        if (horseModelDataCache.containsKey(enhancedHorse.getId())) {
//            HorseModelData horseModelData = horseModelDataCache.get(enhancedHorse.getId());
//            horseModelData.lastAccessed = 0;
//
//            horseModelData.sleeping = enhancedHorse.isAnimalSleeping();
//            horseModelData.blink = enhancedHorse.getBlink();
//            horseModelData.birthTime = enhancedHorse.getBirthTime();
//            horseModelData.clientGameTime = (((ClientLevel)enhancedHorse.level).getLevelData()).getGameTime();
//            int collarSlot = hasCollar(enhancedHorse.getEnhancedInventory());
//            horseModelData.collar = collarSlot!=0;
//            horseModelData.saddle = collarSlot!=1 ? enhancedHorse.getEnhancedInventory().getItem(1) : ItemStack.EMPTY;
//            horseModelData.bridle = !enhancedHorse.getEnhancedInventory().getItem(3).isEmpty() && collarSlot!=3;
//            horseModelData.harness = !enhancedHorse.getEnhancedInventory().getItem(5).isEmpty() && collarSlot!=5;
//            horseModelData.hasChest = !enhancedHorse.getEnhancedInventory().getItem(0).isEmpty();
//            horseModelData.unrenderedModels = new ArrayList<>();
//
//            return horseModelData;
//        } else {
//            HorseModelData horseModelData = new HorseModelData();
//            if (enhancedHorse.getSharedGenes()!=null) {
//                horseModelData.horseGenes = enhancedHorse.getSharedGenes().getAutosomalGenes();
//            }
//            horseModelData.size = enhancedHorse.getAnimalSize();
//            horseModelData.sleeping = enhancedHorse.isAnimalSleeping();
//            horseModelData.blink = enhancedHorse.getBlink();
//            horseModelData.uuidArray = enhancedHorse.getStringUUID().toCharArray();
//            horseModelData.birthTime = enhancedHorse.getBirthTime();
//            horseModelData.clientGameTime = ((((ClientLevel)enhancedHorse.level).getLevelData()).getGameTime());
//
//            int collarSlot = hasCollar(enhancedHorse.getEnhancedInventory());
//            horseModelData.collar = collarSlot!=0;
//            horseModelData.saddle = collarSlot!=1 ? enhancedHorse.getEnhancedInventory().getItem(1) : ItemStack.EMPTY;
//            horseModelData.bridle = !enhancedHorse.getEnhancedInventory().getItem(3).isEmpty() && collarSlot!=3;
//            horseModelData.harness = !enhancedHorse.getEnhancedInventory().getItem(5).isEmpty() && collarSlot!=5;
//            horseModelData.hasChest = !enhancedHorse.getEnhancedInventory().getItem(0).isEmpty();
//            horseModelData.heightMod = enhancedHorse.getShape();
//            horseModelData.faceShape = enhancedHorse.getFaceShape();
//            horseModelData.faceLength = enhancedHorse.getFaceLength();
//            horseModelData.adultAge = EanimodCommonConfig.COMMON.adultAgeCow.get();
//
//            if(horseModelData.horseGenes != null) {
//                horseModelDataCache.put(enhancedHorse.getId(), horseModelData);
//            }
//
//            return horseModelData;
//        }
//    }
//
//    private int hasCollar(SimpleContainer inventory) {
//        for (int i = 1; i < 6; i++) {
//            if (inventory.getItem(i).getItem() instanceof CustomizableCollar) {
//                return i;
//            }
//        }
//        return 0;
//    }
//
//    private class Phenotype {
//        float faceShape;
//        float faceLength;
//
//    }
//
//}
