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
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class EnhancedAxolotlBucketModel extends Model {
    private final ModelPart bucket;

    public static LayerDefinition createLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        PartDefinition bucket = partdefinition.addOrReplaceChild("bucket", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 8.0F));
        bucket.addOrReplaceChild("bucket_front", CubeListBuilder.create()
                .texOffs(2, 4).addBox(2.0F, 4.0F, 0.0F, 12.0F, 8.0F, 0.0F)
                .texOffs(3, 12).addBox(3.0F, 12.0F, 0.0F, 10.0F, 2.0F, 0.0F)
                .texOffs(4, 14).addBox(4.0F, 14.0F, 0.0F, 8.0F, 1.0F, 0.0F)
                .texOffs(5, 15).addBox(5.0F, 15.0F, 0.0F, 6.0F, 1.0F, 0.0F)

                .texOffs(13, 3).addBox(14.0F, 4.0F, -1.0F, 0.0F, 8.0F, 1.0F)
                .texOffs(12, 11).addBox(13.0F, 12.0F, -1.0F, 0.0F, 2.0F, 1.0F)
                .texOffs(11, 13).addBox(12.0F, 14.0F, -1.0F, 0.0F, 1.0F, 1.0F)
                .texOffs(10, 14).addBox(11.0F, 15.0F, -1.0F, 0.0F, 1.0F, 1.0F)
                .mirror(true)
                .texOffs(2, 3).addBox(2.0F, 4.0F, -1.0F, 0.0F, 8.0F, 1.0F)
                .texOffs(3, 11).addBox(3.0F, 12.0F, -1.0F, 0.0F, 2.0F, 1.0F)
                .texOffs(4, 13).addBox(4.0F, 14.0F, -1.0F, 0.0F, 1.0F, 1.0F)
                .texOffs(5, 14).addBox(5.0F, 15.0F, -1.0F, 0.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 0.5F));
        bucket.addOrReplaceChild("bucket_back", CubeListBuilder.create()
                .texOffs(2, 4).addBox(2.0F, 4.0F, 0.0F, 12.0F, 8.0F, 0.0F)
                .texOffs(3, 12).addBox(3.0F, 12.0F, 0.0F, 10.0F, 2.0F, 0.0F)
                .texOffs(4, 14).addBox(4.0F, 14.0F, 0.0F, 8.0F, 1.0F, 0.0F)
                .texOffs(5, 15).addBox(5.0F, 15.0F, 0.0F, 6.0F, 1.0F, 0.0F),
                PartPose.offsetAndRotation(16.0F, 0.0F, -0.5F, 0.0F, (float)Mth.PI, 0.0F));
        bucket.addOrReplaceChild("head_front", CubeListBuilder.create()
                .texOffs(3, 2).addBox(3.0F, 2.0F, 0.0F, 10.0F, 2.0F, 0.0F)
                .texOffs(4, 0).addBox(4.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.0F)
                .texOffs(11, 0).addBox(11.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.0F)
                .texOffs(2, 1).addBox(2.0F, 1.0F, 0.0F, 1.0F, 2.0F, 0.0F)
                .texOffs(4, 1).addBox(4.0F, 1.0F, 0.0F, 2.0F, 1.0F, 0.0F)
                .texOffs(10, 1).addBox(10.0F, 1.0F, 0.0F, 2.0F, 1.0F, 0.0F)
                .texOffs(13, 1).addBox(13.0F, 1.0F, 0.0F, 1.0F, 2.0F, 0.0F)

                .texOffs(0, 3).addBox(3.0F, 2.0F, -1.0F, 10.0F, 0.0F, 1.0F)
                ,
                PartPose.offset(0.0F, 0.0F, 0.5F));
        bucket.addOrReplaceChild("head_back", CubeListBuilder.create()
                .texOffs(3, 2).addBox(3.0F, 2.0F, 0.0F, 10.0F, 2.0F, 0.0F)
                .texOffs(4, 0).addBox(4.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.0F)
                .texOffs(11, 0).addBox(11.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.0F)
                .texOffs(2, 1).addBox(2.0F, 1.0F, 0.0F, 1.0F, 2.0F, 0.0F)
                .texOffs(4, 1).addBox(4.0F, 1.0F, 0.0F, 2.0F, 1.0F, 0.0F)
                .texOffs(10, 1).addBox(10.0F, 1.0F, 0.0F, 2.0F, 1.0F, 0.0F)
                .texOffs(13, 1).addBox(13.0F, 1.0F, 0.0F, 1.0F, 2.0F, 0.0F),
                PartPose.offsetAndRotation(16.0F, 0.0F, -0.5F, 0.0F, (float)Mth.PI, 0.0F));

        return LayerDefinition.create(meshdefinition, 16, 16);
    }

    public EnhancedAxolotlBucketModel(ModelPart root) {
        super(RenderType::entityCutout);
        this.bucket = root;
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        this.bucket.render(poseStack, vertexConsumer, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    }
}
