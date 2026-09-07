package mokiyoki.enhancedanimals.renderer.texture;

import java.util.function.IntPredicate;
import java.util.function.Supplier;

/**
 * Builds one texture slot: a layer added to a TextureGrouping, plus the cache key
 * field that records what the slot resolved to.
 * Three independent choices:
 *   where the path comes from -- texture / variant
 *   how it renders            -- as / tinted / flipped
 *   what the key records      -- keyedAs / onlyIf / orKey
 * add() creatures the slot and key with one delimiter is the terminal operation and
 * always closes the slot with exactly one delimiter
 * a branch that skips the builder entirely must
 * call TextureCacheKeyWriter#addSkippedSlot() to hold the position.
 */
public final class TextureSlotBuilder {

    private final TextureCacheKeyWriter key;
    private final TextureGrouping group;

    private String path = "";
    private Supplier<String> deferredPath;
    private TexturingType texturingType = TexturingType.NONE;
    private Integer rgb;
    private int[] cubes;

    private String keyFragment = "0";
    private String skipFragment;
    private Integer variantIndex;
    private boolean included = true;
    private boolean writesKeyField = true;

    public TextureSlotBuilder(TextureCacheKeyWriter key, TextureGrouping group) {
        this.key = key;
        this.group = group;
    }

    // The texture path. The key records "0" unless #keyedAs overrides it.
    public TextureSlotBuilder texture(String path) {
        this.path = path;
        return this;
    }

    // Pulls a texture from a table index, the key records the index.
    public TextureSlotBuilder textureTableSelector(String[] table, int index) {
        this.deferredPath = () -> table[index];
        this.variantIndex = index;
        this.keyFragment = String.valueOf(index);
        return this;
    }

    // Pulls a texture from a table [] [] index, the key records the indexes.
    public TextureSlotBuilder textureTableSelector(String[][] table, int first, int second) {
        this.deferredPath = () -> table[first][second];
        this.keyFragment = String.valueOf(first) + second;
        return this;
    }

    // Pulls a texture from a table [] [] [] index, the key records the index.
    public TextureSlotBuilder textureTableSelector(String[][][] table, int first, int second, int third) {
        this.deferredPath = () -> table[first][second][third];
        this.keyFragment = String.valueOf(first) + second + third;
        return this;
    }

    // The texture render type used
    public TextureSlotBuilder asType(TexturingType texturingType) {
        this.texturingType = texturingType;
        return this;
    }

    // Applies an rgb value tint to the texture
    public TextureSlotBuilder tinted(TexturingType texturingType, int argb) {
        this.texturingType = texturingType;
        this.rgb = argb;
        return this;
    }

    public TextureSlotBuilder flipped(int... cubes) {
        this.texturingType = TexturingType.APPLY_FLIP;
        this.cubes = cubes;
        return this;
    }


    // What the texture is keyed as in the cache key
    public TextureSlotBuilder keyedAs(String fragment) {
        if (fragment == null || fragment.isEmpty()) {
            this.included = false;
            this.skipFragment = "0";
        } else {
            this.keyFragment = fragment;
        }
        return this;
    }

    // Adds the layer only if the condition is true.
    public TextureSlotBuilder onlyIf(boolean condition) {
        this.included = condition;
        return this;
    }

    public TextureSlotBuilder onlyIf(IntPredicate test) {
        if (this.variantIndex == null) {
            //a multi index variant has no single index to test, and a literal texture has none
            //at all; silently including the layer would hide the mistake
            throw new IllegalStateException("onlyIf(IntPredicate) needs variant(String[], int); use onlyIf(boolean)");
        }
        this.included = test.test(this.variantIndex);
        return this;
    }

    // What the key records when the slot is skipped. Without this the field is empty.
    public TextureSlotBuilder orKey(String fragmentWhenSkipped) {
        this.skipFragment = fragmentWhenSkipped;
        return this;
    }

    /**
     * Adds the layer without writing a key field. For loops whose layers are covered by a
     * single field written by the caller, the texture slot count must stay constant, so a loop
     * with a gene-dependent iteration count cannot write one field per iteration.
     */
    public TextureSlotBuilder noKey() {
        this.writesKeyField = false;
        return this;
    }

    // Build the key and texture slot
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
        } else {
            this.key.writeKeyField(this.keyFragment);
        }
    }
}
