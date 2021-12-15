package mokiyoki.enhancedanimals.model.util;

import net.minecraft.client.renderer.model.ModelRenderer;

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

    public static void copyModelRotations(ModelRenderer source, ModelRenderer dest) {
        dest.rotateAngleX = source.rotateAngleX;
        dest.rotateAngleY = source.rotateAngleY;
        dest.rotateAngleZ = source.rotateAngleZ;
    }

    public static void copyModelRotations(ModelRenderer source, ModelRenderer ... dest) {
        for (ModelRenderer destination : dest) {
            destination.rotateAngleX = source.rotateAngleX;
            destination.rotateAngleY = source.rotateAngleY;
            destination.rotateAngleZ = source.rotateAngleZ;
        }
    }

    public static void copyModelRotations(ModelRenderer source, ModelRenderer dest, float percent) {
        dest.rotateAngleX = source.rotateAngleX * percent;
        dest.rotateAngleY = source.rotateAngleY * percent;
        dest.rotateAngleZ = source.rotateAngleZ * percent;
    }

    public static void copyModelPositioning(ModelRenderer source, ModelRenderer dest) {
        dest.rotateAngleX = source.rotateAngleX;
        dest.rotateAngleY = source.rotateAngleY;
        dest.rotateAngleZ = source.rotateAngleZ;
        dest.rotationPointX = source.rotationPointX;
        dest.rotationPointY = source.rotationPointY;
        dest.rotationPointZ = source.rotationPointZ;
    }

    public static void copyModelPositioning(ModelRenderer source, ModelRenderer ... dest) {
        for (ModelRenderer destination : dest) {
            destination.rotateAngleX = source.rotateAngleX;
            destination.rotateAngleY = source.rotateAngleY;
            destination.rotateAngleZ = source.rotateAngleZ;
            destination.rotationPointX = source.rotationPointX;
            destination.rotationPointY = source.rotationPointY;
            destination.rotationPointZ = source.rotationPointZ;
        }
    }

    public static void copyModelPositioning(ModelRenderer source, ModelRenderer dest, float percent) {
        dest.rotateAngleX = source.rotateAngleX * percent;
        dest.rotateAngleY = source.rotateAngleY * percent;
        dest.rotateAngleZ = source.rotateAngleZ * percent;
        dest.rotationPointX = source.rotationPointX;
        dest.rotationPointY = source.rotationPointY;
        dest.rotationPointZ = source.rotationPointZ;
    }

}
