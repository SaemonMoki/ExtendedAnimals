package mokiyoki.enhancedanimals.model.util;

import com.mojang.math.Vector3f;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WrappedModelPart {

    public final ModelPart modelPart;
    public final String boxName;
    public boolean boxIsRendered = true;
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

    public WrappedModelPart(String boxName, ModelPart parentModelPart, boolean pushPopChildren) {
        this.modelPart = parentModelPart.getChild(boxName);
        this.boxName = boxName;
        this.pushPopChildren = pushPopChildren;
    }

    public WrappedModelPart(String boxName, ModelPart parentModelPart, boolean pushPopChildren, WrappedModelPart child) {
        this.modelPart = parentModelPart.getChild(boxName);
        this.boxName = boxName;
        this.pushPopChildren = pushPopChildren;
        addChild(child);
    }

    public void addChild(WrappedModelPart wrappedModelPart) {
        this.children.add(wrappedModelPart);
    }

    public void addChild(WrappedModelPart... wrappedModelParts) {
        this.children.addAll(Arrays.asList(wrappedModelParts));
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

    public void setYAndSeen(float point, boolean isSeen) {
        this.show(isSeen);
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

    public void setPos(float xPos, float yPos, float zPos) {
        this.modelPart.setPos(xPos, yPos, zPos);
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

    public void showAndPos(boolean show, float x, float y, float z) {
        this.modelPart.visible = show;
        if (show) {
            this.modelPart.x = x;
            this.modelPart.y = y;
            this.modelPart.z = z;
        }
    }

    public void setFromVector(Vector3f vector3f) {
        if (vector3f != null) {
            this.modelPart.setRotation(vector3f.x(), vector3f.y(), vector3f.z());
        } else {
            this.modelPart.setRotation(0.0F, 0.0F, 0.0F);
        }
    }

    public void setPosFromVector(Vector3f vector3f) {
        if (vector3f != null) {
            this.modelPart.setPos(vector3f.x(), vector3f.y(), vector3f.z());
        } else {
            this.modelPart.setPos(0.0F, 0.0F, 0.0F);
        }
    }

    public void setFromVector(Vector3f pos, Vector3f rot) {
        if (pos != null) {
            this.modelPart.y = pos.y();
        } else {
            this.modelPart.y = 0.0F;
        }
        if (rot != null) {
            this.modelPart.setRotation(rot.x(), rot.y(), rot.z());
        } else {
            this.modelPart.setRotation(0.0F, 0.0F, 0.0F);
        }
    }
}
