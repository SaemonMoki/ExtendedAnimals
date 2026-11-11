package mokiyoki.enhancedanimals.renderer.texture;

import java.util.function.IntPredicate;
import java.util.function.Supplier;

// Adds a texture layer to a group and writes its cache key field. Always finish with add().
public final class TextureSlotBuilder {

    private final TextureCacheKeyWriter key;
    private final TextureGrouping group;

    private String path = "";
    private Supplier<String> deferredPath;
    private TexturingType texturingType = TexturingType.NONE;
    private Integer rgb;
    private int[] cubes;
    private boolean flip = false;

    private String keyFragment = "0";
    private String skipFragment;
    private Integer variantIndex;
    private boolean included = true;
    private boolean writesKeyField = true;

    public TextureSlotBuilder(TextureCacheKeyWriter key, TextureGrouping group) {
        this.key = key;
        this.group = group;
    }

    // Key is "0" unless keyedAs is set
    public TextureSlotBuilder texture(String path) {
        this.path = path;
        return this;
    }

    // Key is the index
    public TextureSlotBuilder textureTableSelector(String[] table, int index) {
        this.deferredPath = () -> table[index];
        this.variantIndex = index;
        this.keyFragment = String.valueOf(index);
        return this;
    }

    public TextureSlotBuilder textureTableSelector(String[][] table, int first, int second) {
        this.deferredPath = () -> table[first][second];
        this.keyFragment = String.valueOf(first) + second;
        return this;
    }

    public TextureSlotBuilder textureTableSelector(String[][][] table, int first, int second, int third) {
        this.deferredPath = () -> table[first][second][third];
        this.keyFragment = String.valueOf(first) + second + third;
        return this;
    }

    public TextureSlotBuilder asType(TexturingType texturingType) {
        this.texturingType = texturingType;
        return this;
    }

    public TextureSlotBuilder tinted(TexturingType texturingType, int argb) {
        this.texturingType = texturingType;
        this.rgb = argb;
        return this;
    }

    public TextureSlotBuilder flipped(boolean flip, int[] cubes) {
        if (flip) {
            this.texturingType = TexturingType.APPLY_FLIP;
            this.flip = true;
        } else {
            this.texturingType = TexturingType.MERGE_GROUP;
        }
        this.cubes = cubes;
        return this;
    }

    public TextureSlotBuilder flipped(boolean flip, int[]... cubes) {
        if (flip) {
            int l = 0;
            for (int[] a : cubes) {
                l += a.length;
            }
            this.cubes = new int[l];
            l = 0;
            for (int[] a : cubes) {
                for (int b : a) {
                    this.cubes[l] = b;
                    l++;
                }
            }

            this.texturingType = TexturingType.APPLY_FLIP;
            this.flip = true;
        } else {
            this.cubes = cubes[0];
            this.texturingType = TexturingType.MERGE_GROUP;
        }
        return this;
    }

    public TextureSlotBuilder keyedAs(String fragment) {
        if (fragment == null || fragment.isEmpty()) {
            this.included = false;
            this.skipFragment = "0";
        } else {
            this.keyFragment = fragment;
        }
        return this;
    }

    public TextureSlotBuilder onlyIf(boolean condition) {
        this.included = condition;
        return this;
    }

    public TextureSlotBuilder onlyIf(IntPredicate test) {
        if (this.variantIndex == null) {
            throw new IllegalStateException("onlyIf(IntPredicate) needs variant(String[], int); use onlyIf(boolean)");
        }
        this.included = test.test(this.variantIndex);
        return this;
    }

    // Key to write when the layer is skipped, otherwise the field is left empty
    public TextureSlotBuilder orKey(String fragmentWhenSkipped) {
        this.skipFragment = fragmentWhenSkipped;
        return this;
    }

    // For loops with a gene dependent count, the caller writes one key field for the whole loop
    public TextureSlotBuilder noKey() {
        this.writesKeyField = false;
        return this;
    }

    public void add() {
        if (this.included) {
            String resolved = this.deferredPath != null ? this.deferredPath.get() : this.path;
            if (!resolved.isEmpty()) {
                TextureLayer layer = new TextureLayer(this.texturingType, resolved);
                if (this.rgb != null) layer.setRGB(this.rgb);
                if (this.cubes != null) layer.setCubes(this.cubes);
                this.group.addTextureLayers(layer);
            }
        }

        if (!this.writesKeyField) return;

        if (!this.included) {
            if (this.skipFragment != null) {
                this.key.writeKeyField(this.skipFragment);
            } else {
                this.key.addSkippedSlot();
            }
        } else if (this.rgb != null) {
            this.key.writeKeyField(this.keyFragment, String.valueOf(this.rgb));
        } else if (this.cubes != null) {
            this.key.writeKeyField(this.keyFragment, String.valueOf(this.flip));
        } else {
            this.key.writeKeyField(this.keyFragment);
        }
    }
}
