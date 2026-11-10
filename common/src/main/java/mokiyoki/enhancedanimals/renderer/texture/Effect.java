package mokiyoki.enhancedanimals.renderer.texture;

import java.util.Arrays;

public final class Effect {

    private final TexturingType type;
    private final int rgb;
    private final int[] cubes;

    private Effect(TexturingType type, int rgb, int[] cubes) {
        this.type = type;
        this.rgb = rgb;
        this.cubes = cubes;
    }

    public static Effect of(TexturingType type) {
        return new Effect(type, -1, null);
    }

    public static Effect tint(TexturingType type, int rgb) {
        return new Effect(type, rgb, null);
    }

    public static Effect flip(int[] cubes) {
        return new Effect(TexturingType.APPLY_FLIP, -1, cubes);
    }

    public TexturingType getType() {
        return type;
    }

    public int getRGB() {
        return rgb;
    }

    public int[] getCubes() {
        return cubes;
    }

    void describe(StringBuilder sb) {
        sb.append('+').append(type.name());
        if (rgb != -1) sb.append(':').append(rgb);
        if (cubes != null) sb.append(Arrays.toString(cubes));
    }
}
