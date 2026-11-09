package mokiyoki.enhancedanimals.renderer.texture;

import mokiyoki.enhancedanimals.entity.util.VariationKey;

public class TextureGroup implements TextureNode {

    private final TextureBuilder builder;
    private final TextureGrouping grouping;

    TextureGroup(TextureBuilder builder, TextureGrouping grouping) {
        this.builder = builder;
        this.grouping = grouping;
    }

    @Override
    public TextureGrouping grouping() {
        return grouping;
    }

    public AddedTexture addTexture(String path) {
        if (path.isEmpty()) return AddedTexture.NOTHING;
        TextureLayer layer = new TextureLayer(path);
        grouping.addTextureLayers(layer);
        return new AddedTexture(layer);
    }

    public AddedTexture addTextureIf(boolean condition, String path) {
        return condition ? addTexture(path) : AddedTexture.NOTHING;
    }

    public AddedTexture pickRandomTexture(String format, VariationKey key, int bound) {
        return addTexture(String.format(format, builder.randomInt(key, bound)));
    }

    public AddedTexture pickRandomTexture(String[] table, VariationKey key) {
        return addTexture(table[builder.randomInt(key, table.length)]);
    }

    public AddedTexture addRandomHexTexture(String format, VariationKey key) {
        return addTexture(String.format(format, builder.randomHexChar(key)));
    }

    public TextureGroup addMergeGroup(String... paths) {
        return addGroup(TextureGrouping.merge(), paths);
    }

    public TextureGroup addAverageGroup(String... paths) {
        return addGroup(TextureGrouping.average(), paths);
    }

    public TextureGroup addDyeGroup(String... paths) {
        return addGroup(new TextureGrouping(TexturingType.DYE_GROUP), paths);
    }

    public TextureGroup addPheomelaninGroup(String... paths) {
        return addGroup(new TextureGrouping(TexturingType.APPLY_PHEOMELANIN), paths);
    }

    public Mask addMaskGroup() {
        TextureGrouping mask = TextureGrouping.withParts(TexturingType.MASK_GROUP);
        grouping.addGrouping(mask);
        return new Mask(builder, mask);
    }

    public Cutout addCutoutGroup() {
        TextureGrouping cutout = TextureGrouping.withParts(TexturingType.CUTOUT_GROUP);
        grouping.addGrouping(cutout);
        return new Cutout(builder, cutout);
    }

    private TextureGroup addGroup(TextureGrouping child, String... paths) {
        grouping.addGrouping(child);
        TextureGroup group = new TextureGroup(builder, child);
        for (String path : paths) group.addTexture(path);
        return group;
    }
}
