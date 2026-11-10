package mokiyoki.enhancedanimals.renderer.texture;

public final class Cutout implements TextureNode {

    private final TextureGrouping grouping;
    private final TextureGroup cutoutTexture;
    private final TextureGroup content;

    Cutout(TextureBuilder builder, TextureGrouping grouping) {
        this.grouping = grouping;
        this.cutoutTexture = new TextureGroup(builder, grouping.part(0));
        this.content = new TextureGroup(builder, grouping.part(1));
    }

    @Override
    public TextureGrouping grouping() {
        return grouping;
    }

    public TextureGroup cutoutTexture() {
        return cutoutTexture;
    }

    public TextureGroup content() {
        return content;
    }
}
