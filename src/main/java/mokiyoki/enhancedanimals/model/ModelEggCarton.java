package mokiyoki.enhancedanimals.model;

import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelBox;

public class ModelEggCarton extends Model {
    private final RendererModel base;
    private final RendererModel lid;

    public ModelEggCarton() {
        textureWidth = 64;
        textureHeight = 64;

        base = new RendererModel(this);
        base.setRotationPoint(0.0F, 0.0F, 0.0F);
        base.cubeList.add(new ModelBox(base, 0, 0, 1.0F, 11.0F, 2.0F, 14, 5, 12, 0.0F, false));
        base.cubeList.add(new ModelBox(base, 0, 35, 0.5F, 10.0F, 2.0F, 15, 3, 12, -2.0F, false));

        lid = new RendererModel(this);
        lid.setRotationPoint(0.0F, 12.0F, 2.0F);
        lid.cubeList.add(new ModelBox(lid, 0, 19, 1.0F, 0.0F, 0.0F, 14, 12, 4, 0.0F, false));
        this.setRotationAngle(this.lid, 3.2F, 0.0F, 0.0F);
    }

    public void renderAll() {
        this.base.render(0.0625F);
        this.lid.render(0.0625F);
    }

    public void setRotationAngle(RendererModel rendererModel, float x, float y, float z) {
        rendererModel.rotateAngleX = rendererModel.rotateAngleX + x;
        rendererModel.rotateAngleY = rendererModel.rotateAngleY + y;
        rendererModel.rotateAngleZ = rendererModel.rotateAngleZ + z;
    }

    public RendererModel getLid() {
        return this.lid;
    }

}
