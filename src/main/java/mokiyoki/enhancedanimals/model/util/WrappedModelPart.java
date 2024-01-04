package mokiyoki.enhancedanimals.model.util;

import com.mojang.math.Vector3f;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;

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

    public WrappedModelPart(String boxName, ModelPart parentModelPart, WrappedModelPart child) {
        this.modelPart = parentModelPart.getChild(boxName);
        this.boxName = boxName;
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

    public boolean lerpXRot(float xRot) {
        if (this.modelPart.xRot != xRot) {
            if (Mth.abs(this.modelPart.xRot - xRot) < 0.0001F) {
                this.modelPart.xRot = xRot;
            } else {
                this.modelPart.xRot = Mth.lerp(0.05F, this.modelPart.xRot, xRot);
                return false;
            }
        }
        return true;
    }

    public boolean lerpYRot(float yRot) {
        if (this.modelPart.yRot != yRot) {
            if (Mth.abs(this.modelPart.yRot - yRot) < 0.0001F) {
                this.modelPart.yRot = yRot;
            } else {
                this.modelPart.yRot = Mth.lerp(0.05F, this.modelPart.yRot, yRot);
                return false;
            }
        }
        return true;
    }

    public boolean lerpZRot(float zRot) {
        if (this.modelPart.zRot != zRot) {
            if (Mth.abs(this.modelPart.zRot - zRot) < 0.0001F) {
                this.modelPart.zRot = zRot;
            } else {
                this.modelPart.zRot = Mth.lerp(0.05F, this.modelPart.zRot, zRot);
                return false;
            }
        }
        return true;
    }

    public boolean lerpX(float x) {
        if (this.modelPart.x != x) {
            if (Mth.abs(this.modelPart.x - x) < 0.0001F) {
                this.modelPart.x = x;
            } else {
                this.modelPart.x = Mth.lerp(0.05F, this.modelPart.x, x);
                return false;
            }
        }
        return true;
    }

    public boolean lerpY(float y) {
        if (this.modelPart.y != y) {
            if (Mth.abs(this.modelPart.y - y) < 0.0001F) {
                this.modelPart.y = y;
            } else {
                this.modelPart.y = Mth.lerp(0.05F, this.modelPart.y, y);
                return false;
            }
        }
        return true;
    }

    public boolean lerpZ(float z) {
        if (this.modelPart.z != z) {
            if (Mth.abs(this.modelPart.z - z) < 0.0001F) {
                this.modelPart.z = z;
            } else {
                this.modelPart.z = Mth.lerp(0.05F, this.modelPart.z, z);
                return false;
            }
        }
        return true;
    }

    public void setPos(float xPos, float yPos, float zPos) {
        this.modelPart.setPos(xPos, yPos, zPos);
    }

    public void setRotation(float xRot, float yRot, float zRot) {
        this.modelPart.setRotation(xRot, yRot, zRot);
    }

    public void show() {
        this.modelPart.visible = true;
    }

    public void hide() {
        this.modelPart.visible = false;
    }

    public boolean show(boolean show) {
        this.modelPart.visible = show;
        return show;
    }

    public void setRotation(Vector3f vector3f) {
        if (vector3f != null) {
            this.modelPart.setRotation(vector3f.x(), vector3f.y(), vector3f.z());
        } else {
            this.modelPart.setRotation(0.0F, 0.0F, 0.0F);
        }
    }

    public void setPos(Vector3f vector3f) {
        if (vector3f != null) {
            this.modelPart.setPos(vector3f.x(), vector3f.y(), vector3f.z());
        } else {
            this.modelPart.setPos(0.0F, 0.0F, 0.0F);
        }
    }

    public void setPosYAndRot(Vector3f pos, Vector3f rot) {
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
