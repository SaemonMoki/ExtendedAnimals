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
        base.setRotationPoint(-8.0F, 20.0F, 15.0F);
        base.cubeList.add(new ModelBox(base, 0, 0, -7.0F, -1.0F, -13.0F, 14, 4, 12, 0.0F, false));
        base.cubeList.add(new ModelBox(base, 3, 0, -6.0F, -1.25F, -11.0F, 12, 4, 8, 0.0F, false));
        base.cubeList.add(new ModelBox(base, 1, 0, -7.0F, 3.0F, -12.0F, 14, 1, 4, 0.0F, true));
        base.cubeList.add(new ModelBox(base, 0, 0, -7.0F, 3.0F, -6.0F, 14, 1, 4, 0.0F, false));

        lid = new RendererModel(this);
        lid.setRotationPoint(0.0F, 24.0F, 0.0F);
        lid.cubeList.add(new ModelBox(lid, 0, 8, -15.0F, -8.0F, 2.0F, 14, 4, 12, 0.0F, false));
    }

    public void renderAll() {
        this.base.render(0.0625F);
        this.lid.render(0.0625F);
    }

//    @Override
//    public void setRotationAngle(RendererModel rendererModel, float x, float y, float z) {
//        rendererModel.rotateAngleX = x;
//        rendererModel.rotateAngleY = y;
//        rendererModel.rotateAngleZ = z;
//    }

    public RendererModel getLid() {
        return this.lid;
    }

}
