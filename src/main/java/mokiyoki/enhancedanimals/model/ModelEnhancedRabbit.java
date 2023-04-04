package mokiyoki.enhancedanimals.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import mokiyoki.enhancedanimals.entity.EnhancedRabbit;
import mokiyoki.enhancedanimals.model.modeldata.AnimalModelData;
import mokiyoki.enhancedanimals.model.modeldata.Phenotype;
import mokiyoki.enhancedanimals.model.modeldata.RabbitModelData;
import mokiyoki.enhancedanimals.model.modeldata.RabbitPhenotype;
import mokiyoki.enhancedanimals.model.util.ModelHelper;
import mokiyoki.enhancedanimals.model.util.WrappedModelPart;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@OnlyIn(Dist.CLIENT)
public class ModelEnhancedRabbit<T extends EnhancedRabbit> extends EnhancedAnimalModel<T> {
    protected WrappedModelPart theRabbit;

    protected WrappedModelPart theHead;
    protected WrappedModelPart theNose;
    protected WrappedModelPart theEarLeft;
    protected WrappedModelPart theEarRight;
    protected WrappedModelPart theNeck;
    protected WrappedModelPart theBody;
    protected WrappedModelPart theButt;
    protected WrappedModelPart theLegFrontLeft;
    protected WrappedModelPart theLegFrontRight;
    protected WrappedModelPart theLegBackLeft;
    protected WrappedModelPart theTibiaBackLeft;
    protected WrappedModelPart theFootBackLeft;
    protected WrappedModelPart theLegBackRight;
    protected WrappedModelPart theTibiaBackRight;
    protected WrappedModelPart theFootBackRight;
    protected WrappedModelPart theTail;

    protected WrappedModelPart lionHeadManeDouble;
    protected WrappedModelPart lionHeadManeSingle;

    protected WrappedModelPart headLeft;
    protected WrappedModelPart headRight;
    protected WrappedModelPart lionHeadDouble;
    protected WrappedModelPart lionHeadSingle;
    protected WrappedModelPart lionHeadCheeks;
    protected WrappedModelPart muzzle;
    protected WrappedModelPart nose;
    protected WrappedModelPart jaw;

    protected WrappedModelPart earLeft;
    protected WrappedModelPart earDwarfLeft;
    protected WrappedModelPart earTopLeft;

    protected WrappedModelPart earRight;
    protected WrappedModelPart earDwarfRight;
    protected WrappedModelPart earTopRight;

    protected WrappedModelPart body;
    protected WrappedModelPart butt;
    protected WrappedModelPart bodyAngora[] = new WrappedModelPart[4];
    protected WrappedModelPart buttAngora[] = new WrappedModelPart[4];

    private WrappedModelPart legFrontLeft;
    private WrappedModelPart legFrontRight;
    private WrappedModelPart legBackLeft;
    private WrappedModelPart tibiaBackLeft;
    private WrappedModelPart footBackLeft;
    private WrappedModelPart legBackRight;
    private WrappedModelPart tibiaBackRight;
    private WrappedModelPart footBackRight;

    private WrappedModelPart tail;

    private RabbitModelData rabbitModelData;

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition base = meshdefinition.getRoot().addOrReplaceChild("base", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition bRabbit = base.addOrReplaceChild("bRabbit", CubeListBuilder.create(), PartPose.offset(0.0F, 16.5F, -6.0F));
        PartDefinition bBody = bRabbit.addOrReplaceChild("bBody", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition bButt = bRabbit.addOrReplaceChild("bButt", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 9.0F));
        PartDefinition bNeck = bBody.addOrReplaceChild("bNeck", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition bHead = bNeck.addOrReplaceChild("bHead", CubeListBuilder.create(), PartPose.offset(0.0F, 0.5F, 0.0F));
        PartDefinition bNose = bHead.addOrReplaceChild("bNose", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, -8.0F));
        PartDefinition bEarLeft = bHead.addOrReplaceChild("bEarL", CubeListBuilder.create(), PartPose.offset(1.0F, 0.0F, -2.5F));
        PartDefinition bEarRight = bHead.addOrReplaceChild("bEarR", CubeListBuilder.create(), PartPose.offset(-1.0F, 0.0F, -2.5F));
        PartDefinition bLegFrontLeft = bRabbit.addOrReplaceChild("bLegFL", CubeListBuilder.create(), PartPose.offset(-5.1F, 6.0F, 3.0F));
        PartDefinition bLegFrontRight = bRabbit.addOrReplaceChild("bLegFR", CubeListBuilder.create(), PartPose.offset(2.1F, 6.0F, 3.0F));
        PartDefinition bLegBackLeft = bRabbit.addOrReplaceChild("bLegBL", CubeListBuilder.create(), PartPose.offset(-4.0F, 2.0F, 5.89F));
        PartDefinition bLegBackRight = bRabbit.addOrReplaceChild("bLegBR", CubeListBuilder.create(), PartPose.offset(4.0F, 2.0F, 5.89F));
        PartDefinition bTibiaBackLeft = bRabbit.addOrReplaceChild("bTibiaBL", CubeListBuilder.create(), PartPose.offset(0.0F, 4.0F, -3.0F));
        PartDefinition bTibiaBackRight = bRabbit.addOrReplaceChild("bTibiaBR", CubeListBuilder.create(), PartPose.offset(0.0F, 4.0F, -3.0F));
        PartDefinition bFootBackLeft = bRabbit.addOrReplaceChild("bFootBL", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 5.0F));
        PartDefinition bFootBackRight = bRabbit.addOrReplaceChild("bFootBR", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 5.0F));
        PartDefinition bTail = bBody.addOrReplaceChild("bTail", CubeListBuilder.create(), PartPose.offset(0.0F, 6.0F, 8.5F));

        bNeck.addOrReplaceChild("lionHMD", CubeListBuilder.create()
                        .texOffs(98, 24)
                        .addBox(-7.5F, -5.5F, 0.0F, 15, 15, 0),
                PartPose.ZERO
        );
        bNeck.addOrReplaceChild("lionHMS", CubeListBuilder.create()
                        .texOffs(98, 23)
                        .addBox(-7.5F, -3.5F, 0.0F, 15, 13, 0),
                PartPose.ZERO
        );

        bHead.addOrReplaceChild("eyes", CubeListBuilder.create()
                        .texOffs(0, 16)
                        .addBox(2.5F, 0.0F, 0.0F, 1, 2, 2, new CubeDeformation(0.01F))
                        .texOffs(0, 20)
                        .addBox(-3.5F, 0.0F, 0.0F, 1, 2, 2, new CubeDeformation(0.01F)),
                PartPose.offset(0.0F, 2.0F, -5.0F)
        );

        bHead.addOrReplaceChild("headL", CubeListBuilder.create()
                        .texOffs(0, 24)
                        .addBox(0.0F, 0.0F, -6.0F, 3, 6, 6),
                PartPose.ZERO
        );
        bHead.addOrReplaceChild("headR", CubeListBuilder.create()
                        .texOffs(18, 24)
                        .addBox(-3.0F, 0F, -6.0F, 3, 6, 6),
                PartPose.ZERO
        );
        bHead.addOrReplaceChild("lionHD", CubeListBuilder.create()
                        .texOffs(106, 10)
                        .addBox(-5.5F, -4.0F, -2.0F, 11, 13, 0)
                        .texOffs(112, 31)
                        .addBox(0.0F, -3.0F, -6.0F, 0, 7, 8)
                        .texOffs(106, 0)
                        .addBox(-5.5F, -1.0F, -3.0F, 11, 9, 0),
                PartPose.ZERO
        );
        bHead.addOrReplaceChild("lionHS", CubeListBuilder.create()
                        .texOffs(106, 12)
                        .addBox(-5.5F, -2.0F, -2.0F, 11, 11, 0)
                        .texOffs(113, 32)
                        .addBox(0.0F, -2.0F, -6.0F, 0, 6, 7),
                PartPose.ZERO
        );
        bHead.addOrReplaceChild("lionHC", CubeListBuilder.create()
                        .texOffs(100, 35)
                        .addBox(-5.5F, 4.0F, -4.0F, 11, 4, 0),
                PartPose.ZERO
        );

        bNose.addOrReplaceChild("muzzle", CubeListBuilder.create()
                        .texOffs(0, 8)
                        .addBox(-2.0F, 1.5F, 0.0F, 4, 4, 4),
                PartPose.ZERO
        );
        bNose.addOrReplaceChild("nose", CubeListBuilder.create()
                        .texOffs(0, 8)
                        .addBox(-0.5F, 0.0F, 0.0F, 1, 1, 1),
                PartPose.offset(0.0F, 1.6F, -0.1F)
        );

        bEarLeft.addOrReplaceChild("earL", CubeListBuilder.create()
                        .texOffs(0, 0)
                        .addBox(-1.0F, -7.0F, -1.0F, 4, 7, 1),
                PartPose.ZERO
        );
        bEarLeft.addOrReplaceChild("earDL", CubeListBuilder.create()
                        .texOffs(0, 0)
                        .addBox(-1.0F, -5.0F, -1.0F, 4, 5, 1),
                PartPose.ZERO
        );
//        bEarLeft.addOrReplaceChild("earTL", CubeListBuilder.create()
//                        .texOffs(46, 0)
//                        .addBox(-1.0F, -2.0F, 0.0F, 4, 7, 1),
//                PartPose.ZERO
//        );

        bEarRight.addOrReplaceChild("earR", CubeListBuilder.create()
                        .texOffs(10, 0)
                        .addBox(-3.0F, -7.0F, -1.0F, 4, 7, 1),
                PartPose.ZERO
        );
        bEarRight.addOrReplaceChild("earDR", CubeListBuilder.create()
                        .texOffs(10, 0)
                        .addBox(-3.0F, -5.0F, -1.0F, 4, 5, 1),
                PartPose.ZERO
        );
//        bEarRight.addOrReplaceChild("earTR", CubeListBuilder.create()
//                        .texOffs(70, 0)
//                        .addBox(-2.0F, -2.0F, 0.0F, 3, 4, 1),
//                PartPose.ZERO
//        );

        bBody.addOrReplaceChild("body", CubeListBuilder.create()
                        .texOffs(7, 8)
                        .addBox(-3.5F, 0.0F, 0.0F, 7, 7, 9, new CubeDeformation(0.5F)),
                PartPose.ZERO
        );

        bButt.addOrReplaceChild("butt", CubeListBuilder.create()
                        .texOffs(30, 0)
                        .addBox(-4.0F, -1.0F, 0.0F, 8, 8, 8, new CubeDeformation(0.5F)),
                PartPose.ZERO
        );

        for (int i = 0; i < 4; i++) {
            bBody.addOrReplaceChild("bodyA"+(i+1), CubeListBuilder.create()
                            .texOffs(7, 8)
                            .addBox(-3.5F, 0.0F, 0.0F, 7, 7, 9, new CubeDeformation((i+1.0F))),
                    PartPose.ZERO
            );
            bButt.addOrReplaceChild("buttA"+(i+1), CubeListBuilder.create()
                            .texOffs(30, 0)
                            .addBox(-4.0F, -1.0F, 0.0F, 8, 8, 8, new CubeDeformation((i+1.0F))),
                    PartPose.ZERO
            );
        }

        bLegFrontLeft.addOrReplaceChild("legFL", CubeListBuilder.create()
                        .texOffs(16, 56)
                        .addBox(0.0F, 0.0F, 0.0F, 3, 6, 2, new CubeDeformation(0.0F, 0.0F, -0.5F)),
                PartPose.ZERO
        );
        bLegFrontRight.addOrReplaceChild("legFR", CubeListBuilder.create()
                        .texOffs(26, 56)
                        .addBox(0.0F, 0.0F, 0.0F, 3, 6, 2, new CubeDeformation(0.0F, 0.0F, -0.5F)),
                PartPose.ZERO
        );
        bLegBackLeft.addOrReplaceChild("legBL", CubeListBuilder.create()
                        .texOffs(0, 37)
                        .addBox(-2.0F, -2.0F, -4.0F, 3, 6, 6, new CubeDeformation(0.01F)),
                PartPose.ZERO
        );
        bLegBackRight.addOrReplaceChild("legBR", CubeListBuilder.create()
                        .texOffs(18, 37)
                        .addBox(-1.0F, -2.0F, -4.0F, 3, 6, 6, new CubeDeformation(0.01F)),
                PartPose.ZERO
        );
        bTibiaBackLeft.addOrReplaceChild("tibiaBL", CubeListBuilder.create()
                        .texOffs(0, 49)
                        .addBox(-1.5F, 0.0F, 0.0F, 3, 1, 5),
                PartPose.ZERO
        );
        bTibiaBackRight.addOrReplaceChild("tibiaBR", CubeListBuilder.create()
                        .texOffs(18, 49)
                        .addBox(-1.5F, 0.0F, 0.0F, 3, 1, 5),
                PartPose.ZERO
        );
        bFootBackLeft.addOrReplaceChild("footBL", CubeListBuilder.create()
                        .texOffs( 0, 55)
                        .addBox(-2.0F, 0.0F, 0.0F, 3, 8, 1),
                PartPose.ZERO
        );
        bFootBackRight.addOrReplaceChild("footBR", CubeListBuilder.create()
                        .texOffs(8, 55)
                        .addBox(-1.0F, 0.0F, 0.0F, 3, 8, 1),
                PartPose.ZERO
        );

        bTail.addOrReplaceChild("tail", CubeListBuilder.create()
                        .texOffs(20, 0)
                        .addBox(-1.5F, -4.0F, 0.0F, 3, 4, 2),
                PartPose.ZERO
        );

        base.addOrReplaceChild("collar", CubeListBuilder.create()
                        .texOffs(36, 55)
                        .addBox(-3.5F, -1.0F, -0.5F, 7, 2, 7)
                        .texOffs(35, 51)
                        .addBox(0.0F, -1.5F, 5.5F, 0,  3, 3)
                        .texOffs(12, 37)
                        .addBox(-1.5F, -1.5F, 7.0F, 3, 3, 3, new CubeDeformation(-0.5F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, -1.5F, -Mth.HALF_PI, 0.0F, 0.0F)
        );

        return LayerDefinition.create(meshdefinition, 128, 64);
    }

    public ModelEnhancedRabbit(ModelPart modelPart) {
        super(modelPart);
        ModelPart base = modelPart.getChild("base");
        ModelPart bRabbit = base.getChild("bRabbit");
        ModelPart bBody = bRabbit.getChild("bBody");
        ModelPart bButt = bRabbit.getChild("bButt");
        ModelPart bNeck = bBody.getChild("bNeck");
        ModelPart bHead = bNeck.getChild("bHead");
        ModelPart bNose = bHead.getChild("bNose");
        ModelPart bEarLeft = bHead.getChild("bEarL");
        ModelPart bEarRight = bHead.getChild("bEarR");
        ModelPart bLegFL = bRabbit.getChild("bLegFL");
        ModelPart bLegFR = bRabbit.getChild("bLegFR");
        ModelPart bLegBL = bRabbit.getChild("bLegBL");
        ModelPart bTibiaBL = bRabbit.getChild("bTibiaBL");
        ModelPart bFootBL = bRabbit.getChild("bFootBL");
        ModelPart bLegBR = bRabbit.getChild("bLegBR");
        ModelPart bTibiaBR = bRabbit.getChild("bTibiaBR");
        ModelPart bFootBR = bRabbit.getChild("bFootBR");
        ModelPart bTail = bBody.getChild("bTail");

        this.theRabbit = new WrappedModelPart(bRabbit, "bRabbit");
        this.theBody = new WrappedModelPart(bBody, "bBody");
        this.theButt = new WrappedModelPart(bButt, "bButt");
        this.theNeck = new WrappedModelPart(bNeck, "bNeck");
        this.theHead = new WrappedModelPart(bHead, "bHead");
        this.theNose = new WrappedModelPart(bNose, "bNose");
        this.theEarLeft = new WrappedModelPart(bEarLeft, "bEarL");
        this.theEarRight = new WrappedModelPart(bEarRight, "bEarR");
        this.theLegFrontLeft = new WrappedModelPart(bLegFL, "bLegFL");
        this.theLegFrontRight = new WrappedModelPart(bLegFR, "bLegFR");
        this.theLegBackLeft = new WrappedModelPart(bLegBL, "bLegBL");
        this.theLegBackRight = new WrappedModelPart(bLegBR, "bLegBR");
        this.theTibiaBackLeft = new WrappedModelPart(bTibiaBL, "bTibiaBL");
        this.theTibiaBackRight = new WrappedModelPart(bTibiaBR, "bTibiaBR");
        this.theFootBackLeft = new WrappedModelPart(bFootBL, "bFootBL");
        this.theFootBackRight = new WrappedModelPart(bFootBR, "bFootBR");
        this.theTail = new WrappedModelPart(bTail, "bTail");
        this.collar = new WrappedModelPart(base.getChild("collar"), "collar");

        this.lionHeadManeDouble = new WrappedModelPart("lionHMD", bNeck);
        this.lionHeadManeSingle = new WrappedModelPart("lionHMS", bNeck);

        this.headLeft = new WrappedModelPart("headL", bHead);
        this.headRight = new WrappedModelPart("headR", bHead);
        this.lionHeadDouble = new WrappedModelPart("lionHD", bHead);
        this.lionHeadSingle = new WrappedModelPart("lionHS", bHead);
        this.lionHeadCheeks = new WrappedModelPart("lionHC", bHead);

        this.eyes = new WrappedModelPart("eyes", bHead);

        this.muzzle = new WrappedModelPart("muzzle", bNose);

        this.nose = new WrappedModelPart("nose", bNose);

        this.earLeft = new WrappedModelPart("earL", bEarLeft);
        this.earDwarfLeft = new WrappedModelPart("earDL", bEarLeft);
//        this.earTopLeft = new WrappedModelPart("earTL", bEarLeft);

        this.earRight = new WrappedModelPart("earR", bEarRight);
        this.earDwarfRight = new WrappedModelPart("earDR", bEarRight);
//        this.earTopRight = new WrappedModelPart("earTR", bEarRight);

        this.body = new WrappedModelPart("body", bBody);

        this.butt = new WrappedModelPart("butt", bButt);

        for (int i=0; i < 4; i++) {
            this.bodyAngora[i] = new WrappedModelPart("bodyA" + (i+1), bBody);
            this.buttAngora[i] = new WrappedModelPart("buttA" + (i+1), bButt);
        }

        this.legFrontLeft = new WrappedModelPart("legFL", bLegFL);
        this.legFrontRight = new WrappedModelPart("legFR", bLegFR);
        this.legBackLeft = new WrappedModelPart("legBL", bLegBL);
        this.legBackRight = new WrappedModelPart("legBR", bLegBR);
        this.tibiaBackLeft = new WrappedModelPart("tibiaBL", bTibiaBL);
        this.tibiaBackRight = new WrappedModelPart("tibiaBR", bTibiaBR);
        this.footBackLeft = new WrappedModelPart("footBL", bFootBL);
        this.footBackRight = new WrappedModelPart("footBR", bFootBR);

        this.tail = new WrappedModelPart("tail", bTail);

        this.theRabbit.addChild(this.theBody);
        this.theBody.addChild(this.theNeck);
        this.theBody.addChild(this.theButt);
        this.theNeck.addChild(this.theHead);
        this.theHead.addChild(this.theEarLeft);
        this.theHead.addChild(this.theEarRight);
        this.theHead.addChild(this.theNose);
        this.theBody.addChild(this.theLegFrontLeft);
        this.theBody.addChild(this.theLegFrontRight);
        this.theButt.addChild(this.theLegBackLeft);
        this.theLegBackLeft.addChild(this.theTibiaBackLeft);
        this.theTibiaBackLeft.addChild(this.theFootBackLeft);
        this.theButt.addChild(this.theLegBackRight);
        this.theLegBackRight.addChild(this.theTibiaBackRight);
        this.theTibiaBackRight.addChild(this.theFootBackRight);
        this.theButt.addChild(this.theTail);

        this.theNeck.addChild(this.lionHeadManeDouble);
        this.theNeck.addChild(this.lionHeadManeSingle);
        this.theNeck.addChild(this.collar);

        this.theHead.addChild(this.headLeft);
        this.theHead.addChild(this.headRight);
        this.theHead.addChild(this.lionHeadDouble);
        this.theHead.addChild(this.lionHeadSingle);
        this.theHead.addChild(this.lionHeadCheeks);
        this.theHead.addChild(this.eyes);

        this.theEarLeft.addChild(earLeft);
        this.theEarLeft.addChild(earDwarfLeft);
        this.theEarRight.addChild(earRight);
        this.theEarRight.addChild(earDwarfRight);

        this.theNose.addChild(this.muzzle);
        this.theNose.addChild(this.nose);

        this.theBody.addChild(this.body);

        this.theButt.addChild(this.butt);

        for (int i = 0; i < 4; i++) {
            this.theBody.addChild(this.bodyAngora[i]);
            this.theButt.addChild(this.buttAngora[i]);
        }

        this.theLegFrontLeft.addChild(this.legFrontLeft);
        this.theLegFrontRight.addChild(this.legFrontRight);
        this.theLegBackLeft.addChild(this.legBackLeft);
        this.theLegBackRight.addChild(this.legBackRight);
        this.theTibiaBackLeft.addChild(this.tibiaBackLeft);
        this.theTibiaBackRight.addChild(this.tibiaBackRight);
        this.theFootBackRight.addChild(this.footBackRight);
        this.theFootBackLeft.addChild(this.footBackLeft);

        this.theTail.addChild(this.tail);
    }

    private void resetCubes() {

    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        if (this.rabbitModelData !=null && this.rabbitModelData.getPhenotype() != null) {
            RabbitPhenotype rabbit = this.rabbitModelData.getPhenotype();

            resetCubes();

            super.renderToBuffer(this.rabbitModelData, poseStack, vertexConsumer, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            Map<String, List<Float>> mapOfScale = new HashMap<>();


            float finalRabbitSize = ((3.0F * rabbitModelData.size * rabbitModelData.growthAmount) + rabbitModelData.size) / 4.0F;

            this.lionHeadManeDouble.hide();
            this.lionHeadManeSingle.hide();
            this.lionHeadDouble.hide();
            this.lionHeadSingle.hide();
            this.lionHeadCheeks.show();
            switch (rabbit.lionsMane) {
                case NONE -> this.lionHeadCheeks.hide();
                case DOUBLE -> {
                    this.lionHeadManeDouble.show();
                    this.lionHeadDouble.show();
                }
                case SINGLE -> {
                    this.lionHeadManeSingle.show();
                    this.lionHeadSingle.show();
                }
            }

            if (rabbit.dwarf) {
                this.earLeft.hide();
                this.earRight.hide();
                this.earDwarfLeft.show();
                this.earDwarfRight.show();
            } else {
                this.earLeft.show();
                this.earRight.show();
                this.earDwarfLeft.hide();
                this.earDwarfRight.hide();
            }

            if (rabbitModelData.coatLength != 0) {
                this.body.hide();
                this.butt.hide();
                for (int i = 0; i < 4; i++) {
                    this.bodyAngora[i].show(rabbitModelData.coatLength == i+1);
                    this.buttAngora[i].show(rabbitModelData.coatLength == i+1);
                }
            } else {
                this.body.show();
                this.butt.show();
                for (int i = 0; i < 4; i++) {
                    this.bodyAngora[i].hide();
                    this.buttAngora[i].hide();
                }
            }

            if (rabbitModelData.growthAmount != 1.0F) {
                float headScale = 1.4F - (rabbitModelData.growthAmount * 0.4F);
                float earScale = 0.6F + (rabbitModelData.growthAmount*0.4F);
                float noseScale = 0.8F + (rabbitModelData.growthAmount*0.2F);
                float move = 1.0F - rabbitModelData.growthAmount;
                mapOfScale.put("bHead", ModelHelper.createScalings(headScale, 0.0F, 0.0F, 0.0F));
                mapOfScale.put("bNose", ModelHelper.createScalings(noseScale, 0.0F, move * 0.1F, 0.0F));
                mapOfScale.put("bEarL", ModelHelper.createScalings(earScale, 0.0F, 0.0F, 0.0F));
                mapOfScale.put("bEarR", ModelHelper.createScalings(earScale, 0.0F, 0.0F, 0.0F));
            }

            poseStack.pushPose();
            poseStack.scale(finalRabbitSize, finalRabbitSize, finalRabbitSize);
            poseStack.translate(0.0F, -1.45F + 1.45F / finalRabbitSize, 0.0F);

            gaRender(this.theRabbit, mapOfScale, poseStack, vertexConsumer, packedLightIn, packedOverlayIn, red, green, blue, alpha);

            poseStack.popPose();
        }
    }

    protected void saveAnimationValues(RabbitModelData data) {
        Map<String, Vector3f> map = data.offsets;
        map.put("bBody", this.getRotationVector(this.theBody));
        map.put("bButt", this.getRotationVector(this.theButt));
        map.put("bLegFL", this.getRotationVector(this.theLegFrontLeft));
        map.put("bLegFR", this.getRotationVector(this.theLegFrontRight));
        map.put("bLegBL", this.getRotationVector(this.theLegBackLeft));
        map.put("bLegBR", this.getRotationVector(this.theLegBackRight));
        map.put("bTibiaBL", this.getRotationVector(this.theTibiaBackLeft));
        map.put("bTibiaBR", this.getRotationVector(this.theTibiaBackRight));
        map.put("bFootBL", this.getRotationVector(this.theFootBackLeft));
        map.put("bFootBR", this.getRotationVector(this.theFootBackRight));
        map.put("bEarLPos", this.getPosVector(this.theEarLeft));
        map.put("bEarL", this.getRotationVector(this.theEarLeft));
        map.put("bEarRPos", this.getPosVector(this.theEarRight));
        map.put("bEarR", this.getRotationVector(this.theEarRight));
        map.put("bNose", this.getPosVector(this.theNose));
    }



    private void readInitialAnimationValues(RabbitModelData data, RabbitPhenotype rabbit) {
        Map<String, Vector3f> map = data.offsets;
        if (map.isEmpty()) {
            this.theEarLeft.setPos(1.0F + (rabbit.lopL*3.0F), rabbit.lopL, -2.5F);
            this.theEarLeft.setRotation(rabbit.lop[0] ? -Mth.HALF_PI*(2.1F-rabbit.lopL) : 0.0F, rabbit.lop[0] ? -Mth.HALF_PI : 0.0F, rabbit.lop[0] ? -Mth.HALF_PI : 0.0F);
            this.theEarRight.setPos(-(1.0F + (rabbit.lopR*3.0F)), rabbit.lopR, -2.5F);
            this.theEarRight.setRotation(rabbit.lop[1] ? -Mth.HALF_PI*(2.1F-rabbit.lopR) : 0.0F, rabbit.lop[1] ? Mth.HALF_PI : 0.0F, rabbit.lop[1] ? Mth.HALF_PI : 0.0F);
            this.theNose.setZ(rabbit.dwarf ? -8.0F : -9.0F);
        } else {
            this.theBody.setRotation(map.get("bBody"));
            this.theButt.setRotation(map.get("bButt"));
            this.theLegFrontLeft.setRotation(map.get("bLegFL"));
            this.theLegFrontRight.setRotation(map.get("bLegFR"));
            this.theLegBackLeft.setRotation(map.get("bLegBL"));
            this.theLegBackRight.setRotation(map.get("bLegBR"));
            this.theTibiaBackLeft.setRotation(map.get("bTibiaBL"));
            this.theTibiaBackRight.setRotation(map.get("bTibiaBR"));
            this.theFootBackLeft.setRotation(map.get("bFootBL"));
            this.theFootBackRight.setRotation(map.get("bFootBR"));
            this.theEarLeft.setPos(map.get("bEarLPos"));
            this.theEarLeft.setRotation(map.get("bEarL"));
            this.theEarRight.setPos(map.get("bEarRPos"));
            this.theEarRight.setRotation(map.get("bEarR"));
            this.theNose.setPos(map.get("bNose"));
        }
    }

    @Override
    public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.rabbitModelData = getCreateRabbitModelData(entityIn);

        if (this.rabbitModelData != null) {
            RabbitPhenotype rabbit =this. rabbitModelData.getPhenotype();
            readInitialAnimationValues(this.rabbitModelData, rabbit);

            this.theNeck.setXRot(headPitch * 0.017453292F * 0.5F);
            this.theNeck.setYRot(netHeadYaw * 0.017453292F * 0.5F);
            this.theHead.setXRot(headPitch * 0.017453292F * 0.5F);
            this.theHead.setYRot(netHeadYaw * 0.017453292F * 0.5F);
            this.nose.setY(1.6F+ this.rabbitModelData.noseTwitch);

            boolean fallState = entityIn.getY() < entityIn.yOld;
            float health = entityIn.getHealth()/entityIn.getMaxHealth();
            animateEars(Math.abs(this.theHead.getYRot()), rabbit.lop, rabbit.lopL, rabbit.lopR, fallState, health);

            animateHop((float) Math.sin(entityIn.getJumpCompletion(ageInTicks-(int)ageInTicks) * Mth.PI));

            saveAnimationValues(this.rabbitModelData);
        }

    }

    private void animateHop(float hop) {
        if (hop == 0.0F) {
            this.theBody.setXRot(0.0F);
            this.theButt.setXRot(0.0F);
            this.theLegFrontLeft.setXRot(-Mth.HALF_PI);
            this.theLegFrontRight.setXRot(this.theLegFrontLeft.getXRot());
            this.theLegBackLeft.setXRot(Mth.HALF_PI * -0.2F);
            this.theLegBackRight.setXRot(this.theLegBackLeft.getXRot());
            this.theTibiaBackLeft.setXRot(0.0F);
            this.theTibiaBackRight.setXRot(0.0F);
            this.theFootBackLeft.setXRot(Mth.HALF_PI * -0.8F);
            this.theFootBackRight.setXRot(this.theFootBackLeft.getXRot());
        } else {
            this.theBody.setXRot(hop * 30.0F * Mth.DEG_TO_RAD);
            this.theButt.setXRot(hop * -15.0F * Mth.DEG_TO_RAD);
            this.theLegFrontLeft.setXRot(-1.6F - (hop * -40.0F - 11.0F) * Mth.DEG_TO_RAD);
            this.theLegFrontRight.setXRot(this.theLegFrontLeft.getXRot());
            this.theLegBackLeft.setXRot(Mth.HALF_PI * (-0.2F + (0.6F * hop)));
            this.theLegBackRight.setXRot(this.theLegBackLeft.getXRot());
            this.theTibiaBackLeft.setXRot(-0.25F * hop);
            this.theTibiaBackLeft.setXRot(this.theTibiaBackLeft.getXRot());
            this.theFootBackLeft.setXRot(Mth.HALF_PI * (-0.8F + (0.8F * hop)));
            this.theFootBackRight.setXRot(this.theFootBackLeft.getXRot());
        }
    }

    private void animateEars(float yaw, boolean[] lop, float lopL, float lopR, boolean fallState, float health) {
        if (yaw!=0) {
            this.animateListening(Math.min(yaw, Mth.HALF_PI*0.25F), lop, lopL, lopR, fallState);
        } else {
            if (health != 1.0F) {
                this.pinnedBackEars(lop, health);
            } else {
                this.neutralEars(lop, lopL, lopR, fallState);
            }
        }
    }

    private void animateListening(float yaw, boolean[] lop, float lopL, float lopR, boolean fallState) {
        if (lop[0]) {
            this.theEarLeft.setY(this.lerpTo(this.theEarLeft.getY(), 0.0F));
            float xRot = this.theEarLeft.getXRot();
            float goal = -Mth.HALF_PI*(2.1F-lopL);
            if (!fallState && xRot != goal) {
                if (xRot < goal) {
                    this.theEarLeft.setXRot(xRot + 0.025F);
                } else {
                    this.theEarLeft.setXRot(goal);
                }
            } else if (fallState) {
                this.theEarLeft.setXRot(xRot > -Mth.PI ? xRot - 0.025F : -Mth.PI);
            }
        } else {
            this.theEarLeft.setXRot(this.lerpTo(this.theEarLeft.getXRot(), 0.0F));
            this.theEarLeft.setYRot(this.lerpTo(this.theEarLeft.getYRot(), yaw));
            this.theEarLeft.setZRot(this.lerpTo(this.theEarLeft.getZRot(), 0.0F));
        }

        if (lop[1]) {
            this.theEarRight.setY(this.lerpTo(this.theEarRight.getY(), 0.0F));
            float xRot = this.theEarRight.getXRot();
            float goal = -Mth.HALF_PI*(2.1F-lopR);
            if (!fallState && xRot != goal) {
                if (xRot < goal) {
                    this.theEarRight.setXRot(xRot + 0.025F);
                } else {
                    this.theEarRight.setXRot(goal);
                }
            } else if (fallState) {
                this.theEarRight.setXRot(xRot > -Mth.PI ? xRot - 0.025F : -Mth.PI);
            }
        } else {
            this.theEarRight.setXRot(this.lerpTo(this.theEarRight.getXRot(), 0.0F));
            this.theEarRight.setYRot(this.lerpTo(this.theEarRight.getYRot(), -yaw));
            this.theEarRight.setZRot(this.lerpTo(this.theEarRight.getZRot(), 0.0F));
        }
    }

    private void neutralEars(boolean[] lop, float lopL, float lopR, boolean fallState) {
        if (lop[0]) {
            this.theEarLeft.setY(this.lerpTo(this.theEarLeft.getY(), 1.0F));
            float xRot = this.theEarLeft.getXRot();
            float goal = -Mth.HALF_PI*(2.1F-lopL);
            if (!fallState && xRot != goal) {
                if (xRot < goal) {
                    this.theEarLeft.setXRot(this.theEarLeft.getXRot() + 0.025F);
                } else {
                    this.theEarLeft.setXRot(goal);
                }
            } else if (fallState) {
                this.theEarLeft.setXRot(xRot > -Mth.PI ? xRot - 0.025F : -Mth.PI);
//                this.theEarLeft.setXRot(xRot > -Mth.PI ? this.theEarLeft.getXRot()-0.01F : -Mth.PI);
            }
//            this.theEarLeft.setXRot(hop == 0.0F ? -Mth.HALF_PI*(2.1F-lopL) : (this.theEarLeft.getXRot() > -Mth.PI ? this.theEarLeft.getXRot()-0.01F : -Mth.PI));
        } else {
            this.theEarLeft.setXRot(this.lerpTo(this.theEarLeft.getXRot(), -0.675F));
            this.theEarLeft.setYRot(this.lerpTo(this.theEarLeft.getYRot(), -0.9F));
            this.theEarLeft.setZRot(this.lerpTo(this.theEarLeft.getZRot(), 0.7F));
        }

        if (lop[1]) {
            this.theEarRight.setY(this.lerpTo(this.theEarRight.getY(), 1.0F));
            float xRot = this.theEarRight.getXRot();
            float goal = -Mth.HALF_PI*(2.1F-lopR);
            if (!fallState && xRot != goal) {
                if (xRot < goal) {
                    this.theEarRight.setXRot(xRot + 0.025F);
                } else {
                    this.theEarRight.setXRot(goal);
                }
            } else if (fallState) {
                this.theEarRight.setXRot(xRot > -Mth.PI ? xRot - 0.025F : -Mth.PI);
            }
        } else {
            this.theEarRight.setXRot(this.lerpTo(this.theEarRight.getXRot(), -0.675F));
            this.theEarRight.setYRot(this.lerpTo(this.theEarRight.getYRot(), 0.9F));
            this.theEarRight.setZRot(this.lerpTo(this.theEarRight.getZRot(), -0.7F));
        }
    }

    private void pinnedBackEars(boolean[] lop, float health) {
        if (!lop[0]) {
            this.theEarLeft.setXRot(this.lerpTo(this.theEarLeft.getXRot(), -1.35F));
            this.theEarLeft.setYRot(this.lerpTo(this.theEarLeft.getYRot(), -0.4F));
            this.theEarLeft.setZRot(this.lerpTo(this.theEarLeft.getZRot(), Mth.HALF_PI));

//            this.theEarLeft.setXRot(this.lerpTo(this.theEarLeft.getXRot(), health <= 0.5F ? -1.35F : -(0.675F + (0.675F*health*2.0F))));
//            this.theEarLeft.setYRot(this.lerpTo(this.theEarLeft.getYRot(), health <= 0.5F ? -0.4F : -(0.4F + (0.5F*health*2.0F))));
//            this.theEarLeft.setZRot(this.lerpTo(this.theEarLeft.getZRot(), health <= 0.5F ? Mth.HALF_PI : 1.4F - (0.7F*health*2.0F)));
        }
        if (!lop[1]) {
            this.theEarRight.setXRot(this.lerpTo(this.theEarRight.getXRot(), -1.35F));
            this.theEarRight.setYRot(this.lerpTo(this.theEarRight.getYRot(), 0.4F));
            this.theEarRight.setZRot(this.lerpTo(this.theEarRight.getZRot(), -Mth.HALF_PI));
        }
    }

    private RabbitModelData getCreateRabbitModelData(T enhancedRabbit) {
        return (RabbitModelData) getCreateAnimalModelData(enhancedRabbit);
    }

    @Override
    protected void setInitialModelData(T enhancedRabbit) {
        RabbitModelData rabbitModelData = new RabbitModelData();
        setBaseInitialModelData(rabbitModelData, enhancedRabbit);
    }

    @Override
    protected void additionalUpdateModelDataInfo(AnimalModelData animalModelData, T enhancedAnimal) {
        ((RabbitModelData)animalModelData).noseTwitch = enhancedAnimal.getNoseTwitch()/14F;
        ((RabbitModelData)animalModelData).coatLength = enhancedAnimal.getCoatLength();
    }

    @Override
    protected Phenotype createPhenotype(T enhancedAnimal) {
        return new RabbitPhenotype(enhancedAnimal.getSharedGenes().getAutosomalGenes(), enhancedAnimal.getStringUUID().charAt(6));
    }


}
