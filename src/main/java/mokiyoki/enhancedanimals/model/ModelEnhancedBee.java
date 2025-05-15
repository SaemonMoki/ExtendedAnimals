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
    protected WrappedModelPart theTail;

    protected WrappedModelPart head;
    protected WrappedModelPart thorax;
    protected WrappedModelPart abdomin;

    protected WrappedModelPart collar;

    private BeeModelData beeModelData;

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition base = meshdefinition.getRoot().addOrReplaceChild("base", CubeListBuilder.create(), PartPose.ZERO);
        PartDefinition bHead = base.addOrReplaceChild("bHead", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition bBody = base.addOrReplaceChild("bBody", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, -8.0F));
        PartDefinition bTail = base.addOrReplaceChild("bTail", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, -8.0F));

        bHead.addOrReplaceChild("head", CubeListBuilder.create()
                        .texOffs(0, 0)
                        .addBox(-3.5F, 0.0F, 0.0F, 7, 7, 3, new CubeDeformation(0.01F)),
                PartPose.ZERO
        );

        bBody.addOrReplaceChild("thorax", CubeListBuilder.create()
                        .texOffs(0, 10)
                        .addBox(-3.5F, 0.0F, 0.0F, 7, 7, 7),
                PartPose.rotation(-Mth.HALF_PI, 0.0F, 0.0F)
        );

        bTail.addOrReplaceChild("abdomen", CubeListBuilder.create()
                .texOffs(21, 0)
                .addBox(-3.5F, 0.0F, 0.0F, 7, 7, 7),
                PartPose.rotation(-Mth.HALF_PI, 0.0F, 0.0F)
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
        ModelPart bTail = base.getChild("bTail");

        this.theHead = new WrappedModelPart(bHead, "bHead");
        this.theBody = new WrappedModelPart(bBody, "bBody");
        this.theTail = new WrappedModelPart(bTail, "bTail");

        this.head = new WrappedModelPart("head", bHead);
        this.thorax = new WrappedModelPart("thorax", bBody);
        this.abdomin = new WrappedModelPart("abdomen", bTail);
        this.collar = new WrappedModelPart(base.getChild("collar"), "collar");

        this.theBee.addChild(this.theBody);
        this.theBody.addChild(this.theHead);
        this.theBody.addChild(this.theTail);

        this.theHead.addChild(this.head);
        this.theBody.addChild(this.thorax);
        this.theTail.addChild(this.abdomin);

        this.theBody.addChild(this.collar);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        if (this.beeModelData != null && this.beeModelData.getPhenotype()!=null) {
            BeePhenotype bee = this.beeModelData.getPhenotype();

            for (WrappedModelPart part : this.theTail.children) {
                part.show(false);
            }
            
            float size = ((1.0F + (beeModelData.growthAmount * 3.0F))/4.0F) * beeModelData.size;

            poseStack.pushPose();
            poseStack.scale(size, size, size);
            poseStack.translate(0.0F, -1.5F + 1.5F/(size), 0.0F);

            gaRender(this.theBee, null, poseStack, vertexConsumer, packedLightIn, packedOverlayIn, red, green, blue, alpha);

            poseStack.popPose();
        }
    }

    @Override
    public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.beeModelData = getCreateBeeModelData(entityIn);
        if (this.beeModelData != null && this.beeModelData.getPhenotype() != null) {
            BeePhenotype bee = this.beeModelData.getPhenotype();
            this.setupInitialAnimationValues(this.beeModelData, netHeadYaw, headPitch, bee);

            this.saveAnimationValues(this.beeModelData);
        }

    }

    protected void saveAnimationValues(AnimalModelData data) {
        Map<String, Vector3f> map = data.offsets;
    }

    private void setupInitialAnimationValues(AnimalModelData data, float netHeadYaw, float headPitch, BeePhenotype bee) {
        Map<String, Vector3f> map = data.offsets;
        if (map.isEmpty()) {

        } else {

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
        return new BeePhenotype(enhancedBee.getGenes().getAutosomalGenes(), enhancedBee.getOrSetIsFemale());
    }
}