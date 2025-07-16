package mokiyoki.enhancedanimals.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import mokiyoki.enhancedanimals.entity.EnhancedBee;
import mokiyoki.enhancedanimals.model.modeldata.AnimalModelData;
import mokiyoki.enhancedanimals.model.modeldata.BeeModelData;
import mokiyoki.enhancedanimals.model.modeldata.BeePhenotype;
import mokiyoki.enhancedanimals.model.modeldata.Phenotype;
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

import java.util.Map;

@OnlyIn(Dist.CLIENT)
public class ModelEnhancedBee<T extends EnhancedBee> extends EnhancedAnimalModel<T> {
    protected WrappedModelPart theBee;

    protected WrappedModelPart theHead;
    protected WrappedModelPart theBody;
    protected WrappedModelPart theLegs;
    protected WrappedModelPart theTail;

    protected WrappedModelPart head;
    protected WrappedModelPart antennaL;
    protected WrappedModelPart antennaR;

    protected WrappedModelPart[] thorax = new WrappedModelPart[5];

    protected WrappedModelPart wingL;
    protected WrappedModelPart wingR;

    protected WrappedModelPart[] abdomen = new WrappedModelPart[10];

    protected WrappedModelPart[] leg = new WrappedModelPart[6];
    
    protected WrappedModelPart ovipositor;

    private BeeModelData beeModelData;

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition base = meshdefinition.getRoot().addOrReplaceChild("base", CubeListBuilder.create(), PartPose.ZERO);
        PartDefinition bHead = base.addOrReplaceChild("bHead", CubeListBuilder.create(), PartPose.ZERO);
        PartDefinition bBody = base.addOrReplaceChild("bBody", CubeListBuilder.create(), PartPose.ZERO);
        PartDefinition bLegs = base.addOrReplaceChild("bLegs", CubeListBuilder.create(), PartPose.ZERO);
        PartDefinition bTail = base.addOrReplaceChild("bTail", CubeListBuilder.create(), PartPose.ZERO);

        bHead.addOrReplaceChild("head", CubeListBuilder.create()
                        .texOffs(0, 0)
                        .addBox(-3.5F, -3.5F, -3.0F, 7, 7, 3),
                PartPose.ZERO
        );
        bHead.addOrReplaceChild("antennaL", CubeListBuilder.create()
                        .texOffs(42, 12)
                        .addBox(0.0F, -5.0F, -5.0F, 0, 5, 5),
                PartPose.offset(-1.5F, -1.5F, -2.0F)
        );
        bHead.addOrReplaceChild("antennaR", CubeListBuilder.create()
                        .texOffs(28, 12)
                        .addBox(0.0F, -5.0F, -5.0F, 0, 5, 5),
                PartPose.offset(1.5F, -1.5F, -2.0F)
        );

        for (int i = 3; i <= 7; i++) {
            bBody.addOrReplaceChild("thorax"+i, CubeListBuilder.create()
                            .texOffs(0, 10)
                            .addBox(-3.5F, -i, -3.5F, 7, i, 7, new CubeDeformation(0.1F)),
                    PartPose.rotation(-Mth.HALF_PI, 0.0F, 0.0F)
            );
        }

        bBody.addOrReplaceChild("wingL", CubeListBuilder.create()
                        .texOffs(32, 48)
                        .addBox(0.0F, -16.0F, 0.0F, 16, 16, 0),
                PartPose.offsetAndRotation(1.5F, -3.61F, 0.0F, -Mth.HALF_PI, 0.0F, 0.0F)
        );
        bBody.addOrReplaceChild("wingR", CubeListBuilder.create()
                        .texOffs(0, 48)
                        .addBox(-16.0F, -16.0F, 0.0F, 16, 16, 0),
                PartPose.offsetAndRotation(-1.5F, -3.61F, 0.0F, -Mth.HALF_PI, 0.0F, 0.0F)
        );

        for (int i = 4; i <= 13; i++) {
            bTail.addOrReplaceChild("abdomen"+i, CubeListBuilder.create()
                            .texOffs(21, 0)
                            .addBox(-3.5F, -i, -3.5F, 7, i, 7),
                    PartPose.rotation(-Mth.HALF_PI, 0.0F, 0.0F)
            );
        }

        for (int i = 0; i < 6; i++) {
            boolean e = i%2==0;
            bLegs.addOrReplaceChild("leg"+i, CubeListBuilder.create()
                            .texOffs(e ? 17 + ((i/2)*3) : 7 - ((i/2)*3), 24)
                            .addBox(-0.5F, 0.0F, 0.0F, 1, 4, 0),
                    PartPose.offset((i < 2 ? 1.0F : 2.0F ) * (e ? -1.0F : 1.0F), 3.5F, 0.0F)
            );
        }

        bTail.addOrReplaceChild("tail", CubeListBuilder.create()
                        .texOffs(17, 0)
                        .addBox(0.0F, -0.5F, 0.0F, 0, 1, 2),
                PartPose.ZERO
        );

        base.addOrReplaceChild("collar", CubeListBuilder.create()
                        .texOffs(36, 57)
                        .addBox(-4.5F, -1.0F, -0.5F, 9, 2, 5)
                        .texOffs(35, 51)
                        .addBox(0.0F, -1.5F, 3.5F, 0,  3, 3)
                        .texOffs(12, 37)
                        .addBox(-1.5F, -1.5F, 5.0F, 3, 3, 3, new CubeDeformation(-0.5F)),
                PartPose.offsetAndRotation(0.0F, -2.0F, 0.0F, -Mth.HALF_PI, 0.0F, 0.0F)
        );

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    public ModelEnhancedBee(ModelPart modelPart) {
        super(modelPart);
        ModelPart base = modelPart.getChild("base");
        this.theBee = new WrappedModelPart(base, "base");
        ModelPart bHead = base.getChild("bHead");
        ModelPart bBody = base.getChild("bBody");
        ModelPart bLegs = base.getChild("bLegs");
        ModelPart bTail = base.getChild("bTail");

        this.theHead = new WrappedModelPart(bHead, "bHead");
        this.theBody = new WrappedModelPart(bBody, "bBody");
        this.theLegs = new WrappedModelPart(bBody, "bLegs");
        this.theTail = new WrappedModelPart(bTail, "bTail");

        this.head = new WrappedModelPart("head", bHead);

        this.antennaL = new WrappedModelPart("antennaL", bHead);
        this.antennaR = new WrappedModelPart("antennaR", bHead);

        for (int i = 3; i <= 7; i++) {
            this.thorax[i-3] = new WrappedModelPart("thorax"+i, bBody);
        }

        this.wingL = new WrappedModelPart("wingL", bBody);
        this.wingR = new WrappedModelPart("wingR", bBody);

        for (int i = 4; i <= 13; i++) {
            this.abdomen[i-4] = new WrappedModelPart("abdomen"+i, bTail);
        }

        for (int i = 0; i < 6; i++) {
            this.leg[i] = new WrappedModelPart("leg"+i, bLegs);
        }

        this.ovipositor = new WrappedModelPart("tail", bTail);

        this.collar = new WrappedModelPart(base.getChild("collar"), "collar");

        this.theBee.addChild(this.theBody);
        this.theBody.addChild(this.theHead);
        this.theBody.addChild(this.theLegs);
        this.theBody.addChild(this.theTail);

        this.theHead.addChild(this.head);
        this.theHead.addChild(this.antennaL);
        this.theHead.addChild(this.antennaR);

        this.theBody.addChild(this.thorax);
        this.theBody.addChild(this.wingL);
        this.theBody.addChild(this.wingR);

        this.theLegs.addChild(this.leg);

        this.theTail.addChild(this.abdomen);
        this.theTail.addChild(this.ovipositor);

        this.theBody.addChild(this.collar);
    }

    private void resetCubes() {
        for (WrappedModelPart part : this.thorax) {
            part.hide();
        }
        for (WrappedModelPart part : this.abdomen) {
            part.hide();
        }
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        if (this.beeModelData != null && this.beeModelData.getPhenotype()!=null) {
            BeePhenotype bee = this.beeModelData.getPhenotype();

            resetCubes();
            super.renderToBuffer(this.beeModelData, poseStack, vertexConsumer, packedLightIn, packedOverlayIn, red, green, blue, alpha);

            if (bee.hasThorax) {
                this.thorax[bee.thoraxSize-3].show();
            }
            this.abdomen[bee.abdomenSize-4].show();

            if (beeModelData.hasOvipositor) {
                this.ovipositor.show();
            } else {
                this.ovipositor.hide();
            }

            float size = beeModelData.size;

            poseStack.pushPose();
            poseStack.scale(size, size, size);
            poseStack.translate(0.0F, 0.0F, 0.0F);

            gaRender(this.theBee, null, poseStack, vertexConsumer, packedLightIn, packedOverlayIn, red, green, blue, alpha);

            poseStack.popPose();
        }
    }

    protected void saveAnimationValues(AnimalModelData data) {
        Map<String, Vector3f> map = data.offsets;
        map.put("bHead", this.getRotationVector(this.theHead));
        map.put("bTailPos", this.getPosVector(this.theTail));
        map.put("bTail", this.getRotationVector(this.theTail));
        map.put("bLegsPos", new Vector3f(0.0F, 0.0F, this.theLegs.getZ()));
        for (int i = 0; i < 6; i++) {
            map.put("legPos"+i, this.getPosVector(this.leg[i]));
            map.put("leg"+i, this.getRotationVector(this.leg[i]));
        }
    }

    private void setupInitialAnimationValues(BeeModelData data, BeePhenotype bee) {
        Map<String, Vector3f> map = data.offsets;

        if (data.hasOvipositor) this.ovipositor.setZ(bee.abdomenSize);

        if (map.isEmpty()) {
            if (bee.hasThorax) {
                float dangle = (bee.thoraxSize+bee.abdomenSize) * 0.01F;
                this.theHead.setXRot(dangle);
                this.theTail.setXRot(-dangle);
                this.theTail.setZ(bee.thoraxSize + (dangle * 0.5F));
                this.theTail.setY(dangle * -3.0F);
            } else {
                this.theHead.setXRot(0.0F);
                this.theTail.setXRot(0.0F);
                this.theTail.setZ(0.0F);
                this.theTail.setY(0.0F);
            }

            this.leg[0].setZ(0.0F);
            this.leg[1].setZ(0.0F);

            if ((bee.thoraxSize + bee.abdomenSize) < 7) {
                float cubeeMod = -0.3333F * (7 - (bee.thoraxSize + bee.abdomenSize));
                this.leg[2].setZ(cubeeMod);
                this.leg[3].setZ(this.leg[2].getZ());
                this.leg[4].setZ(this.leg[2].getZ() + 2.0F - cubeeMod);
                this.leg[5].setZ(this.leg[4].getZ());
                this.theLegs.setZ(cubeeMod + 1.0F);
            } else {
                this.theLegs.setZ(0.0F);
                if (bee.hasThorax) {
                    float legMod = 2.0F + ((bee.thoraxSize-3)*0.25F);
                    this.leg[2].setZ(legMod);
                    this.leg[3].setZ(this.leg[2].getZ());
                    this.leg[4].setZ(this.leg[2].getZ() + legMod);
                    this.leg[5].setZ(this.leg[4].getZ());
                } else {
                    this.leg[2].setZ(2.0F);
                    this.leg[3].setZ(2.0F);
                    this.leg[4].setZ(4.0F);
                    this.leg[5].setZ(4.0F);
                }
            }

        } else {
            this.theHead.setRotation(map.get("bHead"));
            this.theTail.setPos(map.get("bTailPos"));
            this.theTail.setRotation(map.get("bTail"));
            this.theLegs.setZ(map.get("bLegsPos").z());
            for (int i = 0; i < 6; i++) {
                this.leg[i].setPos(map.get("legPos"+i));
                this.leg[i].setRotation(map.get("leg"+i));
            }
        }
    }

    @Override
    public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.beeModelData = getCreateBeeModelData(entityIn);
        if (this.beeModelData != null && this.beeModelData.getPhenotype() != null) {
            BeePhenotype bee = this.beeModelData.getPhenotype();
            this.setupInitialAnimationValues(this.beeModelData, bee);

//            this.theHead.setXRot(this.theHead.getXRot() + 0.01F);

            this.saveAnimationValues(this.beeModelData);
        }

    }

    private BeeModelData getCreateBeeModelData(T enhancedBee) {
        return (BeeModelData) getCreateAnimalModelData(enhancedBee);
    }

    @Override
    protected void setInitialModelData(T enhancedBee) {
        BeeModelData beeModelData = new BeeModelData();
        setBaseInitialModelData(beeModelData, enhancedBee);
    }

    @Override
    protected void additionalModelDataInfo(AnimalModelData animalModelData, T enhancedBee) {
    }

    @Override
    protected Phenotype createPhenotype(T enhancedBee) {
        return new BeePhenotype(enhancedBee.getGenes().getSexlinkedGenes(), enhancedBee.getOrSetIsFemale(), 0);
    }
}