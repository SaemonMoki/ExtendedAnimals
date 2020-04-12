package mokiyoki.enhancedanimals.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.ResourceLocation;

import java.util.function.Function;

public class ModelEggCarton extends Model {
    private final ModelRenderer base;
    private final ModelRenderer lid;

    public ModelEggCarton(Function<ResourceLocation, RenderType> renderTypeIn) {
        super(renderTypeIn);
        textureWidth = 64;
        textureHeight = 64;

//        private void addBox(int texOffX, int texOffY, float x, float y, float z, float width, float height, float depth, float deltaX, float deltaY, float deltaZ, boolean mirorIn, boolean p_228305_13_) {
//            this.cubeList.add(new ModelRenderer.ModelBox(texOffX, texOffY, x, y, z, width, height, depth, deltaX, deltaY, deltaZ, mirorIn, this.textureWidth, this.textureHeight));
//        }

        base = new ModelRenderer(this);
        base.setRotationPoint(0.0F, 0.0F, 0.0F);
//        base.addBox(new ModelRenderer.ModelBox(0, 0, 1.0F, 11.0F, 2.0F, 14, 5, 12, 0.0F, false));
//        base.addBox(new ModelBox(base, 0, 35, 0.5F, 10.0F, 2.0F, 15, 3, 12, -2.0F, false));

        lid = new ModelRenderer(this);
        lid.setRotationPoint(0.0F, 12.0F, 14.0F);
//        lid.addBox(new ModelBox(lid, 0, 19, 1.0F, -12.0F, 0.0F, 14, 12, 4, 0.0F, false));
        this.setRotationAngle(this.lid, 0.0F, 0.0F, 0.0F);
    }

    @Override
    public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        this.base.render(matrixStackIn, bufferIn,packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.lid.render(matrixStackIn, bufferIn,packedLightIn, packedOverlayIn, red, green, blue, alpha);
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = modelRenderer.rotateAngleX + x;
        modelRenderer.rotateAngleY = modelRenderer.rotateAngleY + y;
        modelRenderer.rotateAngleZ = modelRenderer.rotateAngleZ + z;
    }

    public ModelRenderer getLid() {
        return this.lid;
    }

}
