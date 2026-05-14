package mokiyoki.enhancedanimals.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import mokiyoki.enhancedanimals.entity.EnhancedAxolotlEgg;
import mokiyoki.enhancedanimals.model.modeldata.AxolotlEggModelData;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;

public class ModelEnhancedAxolotlEgg<T extends EnhancedAxolotlEgg> extends EntityModel<T> {
    private final ModelPart root;
    private final ModelPart egg;
    private final ModelPart embryo;
    private static final int wigglePeriod = 100;

    private AxolotlEggModelData eggModelData;

    public ModelEnhancedAxolotlEgg(ModelPart modelPart, Function<ResourceLocation, RenderType> renderType) {
        super(renderType);
        this.root = modelPart;
        this.egg = modelPart.getChild("egg");
        this.embryo = modelPart.getChild("embryo");
        this.root.children.put("egg", this.egg);
        this.egg.children.put("embryo", this.embryo);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        partdefinition.addOrReplaceChild("egg",
            CubeListBuilder.create()
                    .texOffs(0, 0).addBox(-3.0F, -3.0F, -3.0F, 6, 6, 6)
            , PartPose.ZERO
        );
        partdefinition.addOrReplaceChild("embryo",
                CubeListBuilder.create()
                        .texOffs(0, 12).addBox(-2.0F, -2.0F, -2.0F, 4, 4, 4)
                , PartPose.ZERO
        );
        return LayerDefinition.create(meshdefinition, 32, 32);
    }

    protected void saveAnimationValues(AxolotlEggModelData data) {
        Map<String, Vector3f> map = data.offsets;
        map.put("egg", new Vector3f(this.egg.x, this.egg.y, this.egg.z));
        map.put("embryo", new Vector3f(this.embryo.xRot, this.embryo.yRot, this.embryo.zRot));
    }

    private void readInitialAnimationValues(AxolotlEggModelData data) {
        Map<String, Vector3f> map = data.offsets;
        if (map.isEmpty()) {
            this.egg.setPos(0.0F, 3.0F, 0.0F);
            this.embryo.setRotation(0.0F, 0.0F, 0.0F);

        } else {
            setPosition(this.egg, map.get("egg"));
            setRotaion(this.embryo, map.get("embryo"));
        }
    }

    private static void setPosition(ModelPart part, Vector3f vector3f) {
        part.setPos(vector3f.x(), vector3f.y(), vector3f.z());
    }
    private static void setRotaion(ModelPart part, Vector3f vector3f) {
        part.setRotation(vector3f.x(), vector3f.y(), vector3f.z());
    }

    @Override
    public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.eggModelData = getCreateAnimalModelData(entityIn);
        if (this.eggModelData != null) {
            readInitialAnimationValues(this.eggModelData);

            float driftTimer = ageInTicks + eggModelData.random;
            this.egg.x = 0.5F * ((float) Math.sin((0.05F * driftTimer + 1.0F)));
            this.egg.y =(0.5F * ((float) Math.sin(0.03F * driftTimer))) + 3.0F;
            this.egg.z = 0.5F * ((float) Math.sin((0.05F * driftTimer + 2.0F)));

            int timeTillHatch = (int)(eggModelData.hatchTime-ageInTicks);

            if (eggModelData.wiggleTimer == 0) {
                if (timeTillHatch < 6000) {
                    if (timeTillHatch < 5600) {
                        eggModelData.wiggleTimer = (int)(ageInTicks + wigglePeriod + ThreadLocalRandom.current().nextInt(300));
                    } else {
                        eggModelData.wiggleTimer = (int)(ageInTicks + wigglePeriod + ThreadLocalRandom.current().nextInt(timeTillHatch/3));
                    }
                }
            } else if (eggModelData.wiggleTimer <= ageInTicks+wigglePeriod) {
                if (eggModelData.wiggleTimer >= ageInTicks || timeTillHatch < 100) {
                    this.embryo.xRot = eggModelData.wiggleRate * 0.3F * Mth.sin(ageInTicks * 0.3F);
                    this.embryo.yRot = eggModelData.wiggleRate * 0.2F * Mth.sin(ageInTicks * 0.25F);
                    this.embryo.zRot = eggModelData.wiggleRate * 0.3F * Mth.sin(ageInTicks * 0.2F);

                    if (eggModelData.wiggleTimer >= ageInTicks+70) {
                        eggModelData.wiggleRate = Mth.lerp(0.02F, eggModelData.wiggleRate, 1.25F);
                    } else {
                        eggModelData.wiggleRate = Mth.lerp(0.02F, eggModelData.wiggleRate, 1.0F);
                    }

                } else {
                    this.embryo.xRot = eggModelData.wiggleRate * 0.3F * Mth.sin(ageInTicks * 0.3F);
                    this.embryo.yRot = eggModelData.wiggleRate * 0.2F * Mth.sin(ageInTicks * 0.25F);
                    this.embryo.zRot = eggModelData.wiggleRate * 0.3F * Mth.sin(ageInTicks * 0.2F);
                    eggModelData.wiggleRate = Mth.lerp(0.03F, eggModelData.wiggleRate, 0.0F);
                    if (Mth.abs(this.embryo.xRot) < 0.0001F && Mth.abs(this.embryo.yRot) < 0.0001F && Mth.abs(this.embryo.zRot) < 0.0001F) {
                        this.embryo.setRotation(0.0F, 0.0F, 0.0F);
                        eggModelData.wiggleTimer = 0;
                    }
                }
            }

            saveAnimationValues(this.eggModelData);

            this.egg.yRot = this.eggModelData.rotationY;
            this.egg.zRot = this.eggModelData.rotationZ;
        }
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        poseStack.pushPose();
        poseStack.translate(0.0D, 0.0F, 0.0D);
        poseStack.scale(1.0F, 1.0F, 1.0F);
        this.egg.render(poseStack, vertexConsumer, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        poseStack.popPose();
    }

    private AxolotlEggModelData getCreateAnimalModelData(T egg) {
        if (egg.getModelData() == null) {
            setInitialModelData(egg);
        }

        return egg.getModelData();
    }

    private void setInitialModelData(T egg) {
        AxolotlEggModelData eggModelData = new AxolotlEggModelData();
        eggModelData.random = ThreadLocalRandom.current().nextFloat() * 2.0F;
        eggModelData.hatchTime = egg.getHatchTime();
        eggModelData.rotationY = ThreadLocalRandom.current().nextInt(4) * Mth.HALF_PI;
        eggModelData.rotationZ = ThreadLocalRandom.current().nextInt(4) * Mth.HALF_PI;
        egg.setModelData(eggModelData);
    }


}
