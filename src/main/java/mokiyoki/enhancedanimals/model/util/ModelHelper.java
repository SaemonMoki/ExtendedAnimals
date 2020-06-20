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

    public static void copyModelAngles(ModelRenderer source, ModelRenderer dest) {
        dest.rotateAngleX = source.rotateAngleX;
        dest.rotateAngleY = source.rotateAngleY;
        dest.rotateAngleZ = source.rotateAngleZ;
        dest.rotationPointX = source.rotationPointX;
        dest.rotationPointY = source.rotationPointY;
        dest.rotationPointZ = source.rotationPointZ;
    }

}
