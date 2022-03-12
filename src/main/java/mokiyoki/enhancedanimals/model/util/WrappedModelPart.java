package mokiyoki.enhancedanimals.model.util;

import net.minecraft.client.model.geom.ModelPart;

import java.util.ArrayList;
import java.util.List;

public class WrappedModelPart {

    public final ModelPart modelPart;
    public final String boxName;
    public boolean pushPopChildren = true;
    public List<WrappedModelPart> children = new ArrayList<>();

    public WrappedModelPart(String boxName, ModelPart parentModelPart) {
        this.modelPart = parentModelPart.getChild(boxName);
        this.boxName = boxName;
    }

    public WrappedModelPart(ModelPart modelPart, String boxName) {
        this.modelPart = modelPart;
        this.boxName = boxName;
    }

    public WrappedModelPart(ModelPart modelPart, String boxName, boolean pushPopChildren) {
        this.modelPart = modelPart;
        this.boxName = boxName;
        this.pushPopChildren = pushPopChildren;
    }

    public void addChild(WrappedModelPart wrappedModelPart) {
        this.children.add(wrappedModelPart);
    }

    public void setX(float point) {
        this.modelPart.x = point;
    }

    public float getX() {
        return this.modelPart.x;
    }

    public void setY(float point) {
        this.modelPart.y = point;
    }

    public float getY() {
        return this.modelPart.y;
    }

    public void setZ(float point) {
        this.modelPart.z = point;
    }

    public float getZ() {
        return this.modelPart.z;
    }

    public void setXRot(float point) {
        this.modelPart.xRot = point;
    }

    public float getXRot() {
        return this.modelPart.xRot;
    }

    public void setYRot(float point) {
        this.modelPart.yRot = point;
    }

    public float getYRot() {
        return this.modelPart.yRot;
    }

    public void setZRot(float point) {
        this.modelPart.zRot = point;
    }

    public float getZRot() {
        return this.modelPart.zRot;
    }

    public void setRotation(float xRot, float yRot, float zRot) {
        this.modelPart.setRotation(xRot, yRot, zRot);
    }

    public void setRotation(float xRot, float yRot, float zRot, float percent) {
        float negPercent = 1.0F - percent;
        xRot = (this.modelPart.xRot * negPercent) + (xRot * percent);
        yRot = (this.modelPart.yRot * negPercent) + (yRot * percent);
        zRot = (this.modelPart.zRot * negPercent) + (zRot * percent);

        this.modelPart.setRotation(xRot, yRot, zRot);
    }

    public void show() {
        this.modelPart.visible = true;
    }

    public void hide() {
        this.modelPart.visible = false;
    }

    public void show(boolean show) {
        this.modelPart.visible = show;
    }
}
