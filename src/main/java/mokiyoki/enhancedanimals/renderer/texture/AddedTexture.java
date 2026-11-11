package mokiyoki.enhancedanimals.renderer.texture;

public final class AddedTexture {

    static final AddedTexture NOTHING = new AddedTexture(null);

    private final TextureLayer layer;

    AddedTexture(TextureLayer layer) {
        this.layer = layer;
    }

    public AddedTexture applyEffect(TexturingType type) {
        return with(Effect.of(type));
    }

    public AddedTexture dye() {
        return applyEffect(TexturingType.APPLY_DYE);
    }

    public AddedTexture tintRed() {
        return applyEffect(TexturingType.APPLY_RED);
    }

    public AddedTexture tintBlack() {
        return applyEffect(TexturingType.APPLY_BLACK);
    }

    public AddedTexture shadeMelanin() {
        return applyEffect(TexturingType.APPLY_SHADE_MELANIN);
    }

    public AddedTexture leftEyeColour() {
        return applyEffect(TexturingType.APPLY_EYE_LEFT_COLOUR);
    }

    public AddedTexture rightEyeColour() {
        return applyEffect(TexturingType.APPLY_EYE_RIGHT_COLOUR);
    }

    public AddedTexture collarColour() {
        return applyEffect(TexturingType.APPLY_COLLAR_COLOUR);
    }

    public AddedTexture bridleColour() {
        return applyEffect(TexturingType.APPLY_BRIDLE_COLOUR);
    }

    public AddedTexture saddleColour() {
        return applyEffect(TexturingType.APPLY_SADDLE_COLOUR);
    }

    public AddedTexture rgb(int rgb) {
        return with(Effect.tint(TexturingType.APPLY_RGB, rgb));
    }

    public AddedTexture rgba(int rgba) {
        return with(Effect.tint(TexturingType.APPLY_RGBA, rgba));
    }

    public AddedTexture alpha(float alpha) {
        int a = (int) (alpha * 255.0F);
        return with(Effect.tint(TexturingType.APPLY_RGBA, a << 24 | 255 << 16 | 255 << 8 | 255));
    }

    public AddedTexture hueShift(int ahsb) {
        return with(Effect.tint(TexturingType.APPLY_SHIFT, ahsb));
    }

    public AddedTexture flipIf(boolean flip, int[]... cubes) {
        if (!flip) return this;
        int length = 0;
        for (int[] part : cubes) length += part.length;
        int[] joined = new int[length];
        int i = 0;
        for (int[] part : cubes) {
            System.arraycopy(part, 0, joined, i, part.length);
            i += part.length;
        }
        return with(Effect.flip(joined));
    }

    private AddedTexture with(Effect effect) {
        if (layer != null) layer.addEffect(effect);
        return this;
    }
}
