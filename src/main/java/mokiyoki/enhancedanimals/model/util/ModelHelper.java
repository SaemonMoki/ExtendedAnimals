package mokiyoki.enhancedanimals.model.util;

import net.minecraft.client.model.geom.ModelPart;

import java.util.ArrayList;
import java.util.List;

public class ModelHelper {

    public static List<Float> createScalings(Float scalingX, Float scalingY, Float scalingZ, Float translateX, Float translateY, Float translateZ) {
        List<Float> scalings = new ArrayList<>();
        //scaling
        scalings.add(scalingX);
        scalings.add(scalingY);
        scalings.add(scalingZ);

        //translations
        scalings.add(translateX);
        scalings.add(translateY);
        scalings.add(translateZ);
        return scalings;
    }

    public static List<Float> createScalings(Float scaling, Float translateX, Float translateY, Float translateZ) {
        List<Float> scalings = new ArrayList<>();
        //scaling
        scalings.add(scaling);
        scalings.add(scaling);
        scalings.add(scaling);

        //translations
        scalings.add(translateX);
        scalings.add(translateY);
        scalings.add(translateZ);
        return scalings;
    }

    public static List<Float> mirrorX(List<Float> scalings) {
        List<Float> reversedNegative = new ArrayList<>();
        reversedNegative.add(scalings.get(0));
        reversedNegative.add(scalings.get(1));
        reversedNegative.add(scalings.get(2));
        reversedNegative.add(scalings.get(3)*-1.0F);
        reversedNegative.add(scalings.get(4));
        reversedNegative.add(scalings.get(5));

        return reversedNegative;
    }

    public static void copyModelRotations(ModelPart source, ModelPart dest) {
        dest.xRot = source.xRot;
        dest.yRot = source.yRot;
        dest.zRot = source.zRot;
    }

    public static void copyModelRotations(ModelPart source, ModelPart ... dest) {
        for (ModelPart destination : dest) {
            destination.xRot = source.xRot;
            destination.yRot = source.yRot;
            destination.zRot = source.zRot;
        }
    }

    public static void copyModelRotations(ModelPart source, ModelPart dest, float percent) {
        dest.xRot = source.xRot * percent;
        dest.yRot = source.yRot * percent;
        dest.zRot = source.zRot * percent;
    }

    public static void copyModelPositioning(ModelPart source, ModelPart dest) {
        dest.xRot = source.xRot;
        dest.yRot = source.yRot;
        dest.zRot = source.zRot;
        dest.x = source.x;
        dest.y = source.y;
        dest.z = source.z;
    }

    public static void copyModelPositioning(ModelPart source, ModelPart ... dest) {
        for (ModelPart destination : dest) {
            destination.xRot = source.xRot;
            destination.yRot = source.yRot;
            destination.zRot = source.zRot;
            destination.x = source.x;
            destination.y = source.y;
            destination.z = source.z;
        }
    }

    public static void copyModelPositioning(ModelPart source, ModelPart dest, float percent) {
        dest.xRot = source.xRot * percent;
        dest.yRot = source.yRot * percent;
        dest.zRot = source.zRot * percent;
        dest.x = source.x;
        dest.y = source.y;
        dest.z = source.z;
    }

}
