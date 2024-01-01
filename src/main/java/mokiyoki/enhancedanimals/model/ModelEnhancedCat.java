package mokiyoki.enhancedanimals.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import mokiyoki.enhancedanimals.entity.EnhancedCat;
import mokiyoki.enhancedanimals.model.modeldata.*;
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
import java.util.concurrent.ThreadLocalRandom;

@OnlyIn(Dist.CLIENT)
public class ModelEnhancedCat<T extends EnhancedCat> extends EnhancedAnimalModel<T> {

    public ModelEnhancedCat(ModelPart modelPart) {
        super(modelPart);
        ModelPart base = modelPart.getChild("base");
        ModelPart bCat = base.getChild("bCat");

        this.theCat = new WrappedModelPart(bCat, "bCat");
        this.cube = new WrappedModelPart("cube", bCat);

        this.theCat.addChild(this.cube);
    }
    private CatModelData catModelData;
    protected WrappedModelPart theCat;
    protected WrappedModelPart cube;

    private void resetCubes() {

    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition base = meshdefinition.getRoot().addOrReplaceChild("base", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition bCat = base.addOrReplaceChild("bCat", CubeListBuilder.create()
                .texOffs(0, 0)
                .addBox(0.0F, 0.0F, 0.0F, 16, 16, 16),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));

        bCat.addOrReplaceChild("cube", CubeListBuilder.create()
                        .texOffs(0, 0)
                        .addBox(0.0F, 0.0F, 0.0F, 16, 16, 16),
                PartPose.ZERO
        );

        return LayerDefinition.create(meshdefinition, 256, 256);
    }
    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        if (this.catModelData!=null && this.catModelData.getPhenotype() != null) {
            CatPhenotype cat = catModelData.getPhenotype();

            resetCubes();
            super.renderToBuffer(this.catModelData, poseStack, vertexConsumer, packedLightIn, packedOverlayIn, red, green, blue, alpha);

            Map<String, List<Float>> mapOfScale = new HashMap<>();

            poseStack.pushPose();
            poseStack.scale(1.0F, 1.0F, 1.0F);

            gaRender(this.theCat, mapOfScale, poseStack, vertexConsumer, packedLightIn, packedOverlayIn, red, green, blue, alpha);

            poseStack.popPose();
        }
    }

    @Override
    public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }

    private CatModelData getCreateCatModelData(T enhancedCat) {
        return (CatModelData) getCreateAnimalModelData(enhancedCat);
    }

    @Override
    protected void setInitialModelData(T enhancedCat) {
        CatModelData catModelData = new CatModelData();
        setBaseInitialModelData(catModelData, enhancedCat);
    }

    @Override
    protected void additionalUpdateModelDataInfo(AnimalModelData animalModelData, T enhancedAnimal) {
        animalModelData.saddle = getSaddle(enhancedAnimal.getEnhancedInventory());
    }

    @Override
    protected Phenotype createPhenotype(T enhancedAnimal) {
        return new CatPhenotype(enhancedAnimal.getSharedGenes().getAutosomalGenes(), enhancedAnimal.getStringUUID().charAt(1));
    }


}
