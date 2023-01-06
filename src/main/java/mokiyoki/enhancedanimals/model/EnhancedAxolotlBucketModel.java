package mokiyoki.enhancedanimals.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class EnhancedAxolotlBucketModel extends Model {
    private final ModelPart bucket;
    public boolean greaterGills = false;
    public boolean longGills = false;

    public static LayerDefinition createLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        PartDefinition bucket = partdefinition.addOrReplaceChild("bucket", CubeListBuilder.create(), PartPose.offsetAndRotation(-16.0F, 16.0F, 8.0F, Mth.PI, 0.0F, 0.0F));
        bucket.addOrReplaceChild("front", CubeListBuilder.create()
                        .texOffs(0,0).addBox(0.0F, 0.0F, 0.0F, 16, 16, 0),
                PartPose.offset(0.0F, 0.0F, 0.5F));
        bucket.addOrReplaceChild("back", CubeListBuilder.create()
                        .mirror(true)
                        .texOffs(0,0).addBox(0.0F, 0.0F, 0.0F, 16, 16, 0),
                PartPose.offsetAndRotation(16.0F, 0.0F, -0.5F, 0.0F, Mth.PI, 0.0F));
        bucket.addOrReplaceChild("side1", CubeListBuilder.create()
                        .texOffs(3, -1).addBox(12, 0, 0, 0, 2, 1)
                        .texOffs(1, 0).addBox(14, 1, 0, 0, 2, 1)
                        .texOffs(2, 2).addBox(13, 3, 0, 0, 1, 1)

                        .texOffs(9, 0).addBox(6, 1, 0, 0, 1, 1)
                        .texOffs(10, -1).addBox(5, 0, 0, 0, 1, 1)
                        .texOffs(12, 0).addBox(3, 1, 0, 0, 1, 1)

                        .texOffs(1, 3).addBox(14, 4, 0, 0, 8, 1)
                        .texOffs(2, 11).addBox(13, 12, 0, 0, 2, 1)
                        .texOffs(3, 13).addBox(12, 14, 0, 0, 1, 1)
                        .texOffs(4, 14).addBox(11, 15, 0, 0, 1, 1)

                , PartPose.offset(0, 0, -0.5F));
        bucket.addOrReplaceChild("side2", CubeListBuilder.create()
                        .texOffs(11, -1).addBox(4, 0, 0, 0, 2, 1)
                        .texOffs(13, 0).addBox(2, 1, 0, 0, 2, 1)
                        .texOffs(12, 2).addBox(3, 3, 0, 0, 1, 1)

                        .texOffs(5, 0).addBox(10, 1, 0, 0, 1, 1)
                        .texOffs(4, -1).addBox(11, 0, 0, 0, 1, 1)
                        .texOffs(2, 0).addBox(13, 1, 0, 0, 1, 1)

                        .texOffs(13, 3).addBox(2, 4, 0, 0, 8, 1)
                        .texOffs(12, 11).addBox(3, 12, 0, 0, 2, 1)
                        .texOffs(11, 13).addBox(4, 14, 0, 0, 1, 1)
                        .texOffs(10, 14).addBox(5, 15, 0, 0, 1, 1),
                PartPose.offset(0, 0, -0.5F));
        bucket.addOrReplaceChild("top", CubeListBuilder.create()
                        .texOffs(10, 0).addBox(4, 0, 0, 1, 0, 1)
                        .texOffs(3, 0).addBox(11, 0, 0, 1, 0, 1)
                        .texOffs(4, 1).addBox(10, 1, 0, 1, 0, 1)
                        .texOffs(9, 1).addBox(5, 1, 0, 1, 0, 1)
                        .texOffs(1, 1).addBox(13, 1, 0, 1, 0, 1)
                        .texOffs(12, 1).addBox(2, 1, 0, 1, 0, 1)
                        .texOffs(11, 2).addBox(3, 2, 0, 1, 0, 1)
                        .texOffs(2, 2).addBox(12, 2, 0, 1, 0, 1)
                        .texOffs(12, 4).addBox(2, 4, 0, 1, 0, 1)
                        .texOffs(1, 4).addBox(13, 4, 0, 1, 0, 1)
                        .mirror(true)
                        .texOffs(5, 2).addBox(6, 2, 0, 4, 0, 1),
        PartPose.offset(0, 0, -0.5F));
        bucket.addOrReplaceChild("bottom", CubeListBuilder.create()
                        .texOffs(11, 2).addBox(2, 3, 0, 1, 0, 1)
                        .texOffs(0, 2).addBox(13, 3, 0, 1, 0, 1)
                        .texOffs(11, 11).addBox(2, 12, 0, 1, 0, 1)
                        .texOffs(0, 11).addBox(13, 12, 0, 1, 0, 1)
                        .texOffs(10, 13).addBox(3, 14, 0, 1, 0, 1)
                        .texOffs(1, 13).addBox(12, 14, 0, 1, 0, 1)
                        .texOffs(9, 14).addBox(4, 15, 0, 1, 0, 1)
                        .texOffs(2, 14).addBox(11, 15, 0, 1, 0, 1)
                        .texOffs(0, 15).addBox(5, 16, 0, 4, 0, 1)
                        .texOffs(6, 15).addBox(9, 16, 0, 2, 0, 1),
                PartPose.offset(0, 0, -0.5F));
        bucket.addOrReplaceChild("longgills", CubeListBuilder.create()
                        //top
                        .texOffs(2, 0).addBox(3, 0, 0, 1, 0, 1)
                        .texOffs(5, 0).addBox(6, 0, 0, 1, 0, 1)
                        .texOffs(8, 0).addBox(9, 0, 0, 1, 0, 1)
                        .texOffs(11, 0).addBox(12, 0, 0, 1, 0, 1)

                        .texOffs(0, 1).addBox(1, 1, 0, 1, 0, 1)
                        .texOffs(13, 1).addBox(14, 1, 0, 1, 0, 1)

                        .texOffs(0, 3).addBox(1, 3, 0, 1, 0, 1)
                        .texOffs(13, 3).addBox(14, 3, 0, 1, 0, 1)

                        .texOffs(0, 5).addBox(1, 5, 0, 1, 0, 1)
                        .texOffs(13, 5).addBox(14, 5, 0, 1, 0, 1)

                        //side 1
                        .texOffs(2, -1).addBox(5, 0, 0, 0, 1, 1)
                        .texOffs(5, -1).addBox(7, 0, 0, 0, 1, 1)
                        .texOffs(8, -1).addBox(10, 0, 0, 0, 1, 1)
                        .texOffs(11, -1).addBox(13, 0, 0, 0, 1, 1)

                        .texOffs(13, 0).addBox(15, 1, 0, 0, 1, 1)
                        .texOffs(0, 2).addBox(2, 3, 0, 0, 1, 1)
                        .texOffs(13, 2).addBox(15, 3, 0, 0, 1, 1)
                        .texOffs(13, 4).addBox(15, 5, 0, 0, 1, 1),
                PartPose.offset(0, 0, -0.5F));
        bucket.addOrReplaceChild("greatergills", CubeListBuilder.create()
                        //top
                        .texOffs(2, 0).addBox(3, 0, 0, 1, 0, 1)
                        .texOffs(5, 0).addBox(6, 0, 0, 1, 0, 1)
                        .texOffs(8, 0).addBox(9, 0, 0, 1, 0, 1)
                        .texOffs(11, 0).addBox(12, 0, 0, 1, 0, 1)

                        .texOffs(0, 1).addBox(1, 1, 0, 1, 0, 1)
                        .texOffs(13, 1).addBox(14, 1, 0, 1, 0, 1)

                        .texOffs(0, 3).addBox(1, 3, 0, 1, 0, 1)
                        .texOffs(13, 3).addBox(14, 3, 0, 1, 0, 1)

                        .texOffs(0, 5).addBox(1, 5, 0, 1, 0, 1)
                        .texOffs(13, 5).addBox(14, 5, 0, 1, 0, 1)

                        //side 1
                        .texOffs(2, -1).addBox(5, 0, 0, 0, 1, 1)
                        .texOffs(5, -1).addBox(7, 0, 0, 0, 1, 1)
                        .texOffs(8, -1).addBox(10, 0, 0, 0, 1, 1)
                        .texOffs(11, -1).addBox(13, 0, 0, 0, 1, 1)

                        .texOffs(13, 0).addBox(15, 1, 0, 0, 1, 1)
                        .texOffs(0, 2).addBox(2, 3, 0, 0, 1, 1)
                        .texOffs(13, 2).addBox(15, 3, 0, 0, 1, 1)
                        .texOffs(13, 4).addBox(15, 5, 0, 0, 1, 1),
                PartPose.offset(0, 0, -0.5F));
        return LayerDefinition.create(meshdefinition, 16, 16);
    }

    public EnhancedAxolotlBucketModel(ModelPart root) {
        super(RenderType::entityCutout);
        this.bucket = root;
    }

//    public void renderMinorGillsToBuffer (PoseStack poseStack, VertexConsumer vertexConsumer, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
//        this.bucket.getChild("bucket").getChild("greatergills").visible = false;
//        this.bucket.getChild("bucket").getChild("longgills").visible = false;
//        renderToBuffer(poseStack, vertexConsumer, packedLightIn, packedOverlayIn, 1, 1, 1, 1);
//    }
//
//    public void renderLongGillsToBuffer (PoseStack poseStack, VertexConsumer vertexConsumer, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
//        this.bucket.getChild("bucket").getChild("greatergills").visible = false;
//        this.bucket.getChild("bucket").getChild("longgills").visible = true;
//        renderToBuffer(poseStack, vertexConsumer, packedLightIn, packedOverlayIn, 1, 1, 1, 1);
//    }
//
//    public void renderGreaterGillsToBuffer (PoseStack poseStack, VertexConsumer vertexConsumer, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
//        this.bucket.getChild("bucket").getChild("greatergills").visible = true;
//        this.bucket.getChild("bucket").getChild("longgills").visible = true;
//        renderToBuffer(poseStack, vertexConsumer, packedLightIn, packedOverlayIn, 1, 1, 1, 1);
//    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        this.bucket.getChild("bucket").getChild("greatergills").visible = this.greaterGills;
        this.bucket.getChild("bucket").getChild("longgills").visible = this.longGills;
        this.bucket.render(poseStack, vertexConsumer, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    }
}
