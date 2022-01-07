package mokiyoki.enhancedanimals.model.util;

import net.minecraft.client.model.geom.ModelPart;

import java.util.ArrayList;
import java.util.List;

public class WrappedModelPart {

    public final ModelPart modelPart;
    public final String boxName;
    public boolean pushPopChildren = true;
    public List<WrappedModelPart> children = new ArrayList<>();

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
}
