package mokiyoki.enhancedanimals.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import mokiyoki.enhancedanimals.entity.EnhancedCat;
import mokiyoki.enhancedanimals.model.modeldata.AnimalModelData;
import mokiyoki.enhancedanimals.model.modeldata.Phenotype;
import mokiyoki.enhancedanimals.model.modeldata.PigModelData;
import mokiyoki.enhancedanimals.model.modeldata.PigPhenotype;
import mokiyoki.enhancedanimals.model.modeldata.SaddleType;
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
    }

    private void resetCubes() {

    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
    }

    @Override
    public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }

    private PigModelData getCreatePigModelData(T enhancedPig) {
        return (PigModelData) getCreateAnimalModelData(enhancedPig);
    }

    @Override
    protected void setInitialModelData(T enhancedPig) {
        PigModelData pigModelData = new PigModelData();
        setBaseInitialModelData(pigModelData, enhancedPig);
    }

    @Override
    protected void additionalUpdateModelDataInfo(AnimalModelData animalModelData, T enhancedAnimal) {
        animalModelData.saddle = getSaddle(enhancedAnimal.getEnhancedInventory());
    }

    @Override
    protected Phenotype createPhenotype(T enhancedAnimal) {
        return new PigPhenotype(enhancedAnimal.getSharedGenes().getAutosomalGenes(), enhancedAnimal.getStringUUID().charAt(1));
    }


}
