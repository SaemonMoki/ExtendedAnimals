package mokiyoki.enhancedanimals.renderer.texture;

public final class Mask implements TextureNode {

    private final TextureGrouping grouping;
    private final TextureGroup maskTexture;
    private final TextureGroup content;

    Mask(TextureBuilder builder, TextureGrouping grouping) {
        this.grouping = grouping;
        this.maskTexture = new TextureGroup(builder, grouping.part(0));
        this.content = new TextureGroup(builder, grouping.part(1));
    }

    @Override
    public TextureGrouping grouping() {
        return grouping;
    }

    public TextureGroup maskTexture() {
        return maskTexture;
    }

    public TextureGroup content() {
        return content;
    }
}
