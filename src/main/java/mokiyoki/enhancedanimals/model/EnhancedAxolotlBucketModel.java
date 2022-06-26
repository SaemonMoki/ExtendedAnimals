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
        PartDefinition bucket = partdefinition.addOrReplaceChild("bucket", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 8.0F, 0.0F, Mth.PI, 0.0F));
        bucket.addOrReplaceChild("front", CubeListBuilder.create()
                .texOffs(0,0).addBox(0.0F, 0.0F, 0.0F, 16, 16, 0),
                PartPose.offset(0.0F, 0.0F, 0.5F));
        bucket.addOrReplaceChild("back", CubeListBuilder.create()
                .mirror(true)
                .texOffs(0,0).addBox(0.0F, 0.0F, 0.0F, 16, 16, 0),
                PartPose.offsetAndRotation(16.0F, 0.0F, -0.5F, 0.0F, (float)Mth.PI, 0.0F));
        bucket.addOrReplaceChild("side1", CubeListBuilder.create()
                .texOffs(3, -1).addBox(4, 0, 0, 0, 2, 1)
                .texOffs(9, 0).addBox(4, 0, 0, 0, 2, 1)
                .texOffs(1, 0).addBox(2, 1, 0, 0, 2, 1)
                .texOffs(2, 2).addBox(3, 3, 0, 0, 1, 1)
                .texOffs(1, 3).addBox(2, 4, 0, 0, 8, 1)
                .texOffs(2, 11).addBox(3, 12, 0, 0, 2, 1)
                .texOffs(4, 13).addBox(4, 13, 0, 0, 1, 1)
                .texOffs(5, 14).addBox(5, 14, 0, 0, 1, 1),
                PartPose.offset(0, 0, -0.5F));
        bucket.addOrReplaceChild("top", CubeListBuilder.create()
                .texOffs(0, 1).addBox(2, 1, 0, 1, 0, 1)
                .texOffs(1, 2).addBox(3, 2, 0, 1, 0, 1)
                .texOffs(2, 0).addBox(4, 0, 0, 1, 0, 1)
                .texOffs(3, 1).addBox(5, 1, 0, 1, 0, 1)
                .texOffs(1, 2).addBox(6, 2, 0, 4, 0, 1),
                PartPose.offset(0, 0, -0.5F));
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
