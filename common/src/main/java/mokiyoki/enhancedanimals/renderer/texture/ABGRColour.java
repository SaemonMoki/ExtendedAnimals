package mokiyoki.enhancedanimals.renderer.texture;

public class ABGRColour {

    public static int getA(int abgr) {
        return abgr >> 24 & 255;
    }

    public static int getR(int abgr) {
        return abgr & 255;
    }

    public static int getG(int abgr) {
        return abgr >> 8 & 255;
    }

    public static int getB(int abgr) {
        return abgr >> 16 & 255;
    }

    public static int combine(int alpha, int blue, int green, int red) {
        return (alpha & 255) << 24 | (blue & 255) << 16 | (green & 255) << 8 | (red & 255);
    }
}
