package mokiyoki.enhancedanimals.renderer.texture;

import java.util.function.IntPredicate;
import java.util.function.Supplier;

/**
 * Builds one texture slot: a layer added to a {@link TextureGrouping}, plus the cache key
 * field that records what the slot resolved to.
 *
 * Three independent choices, each named rather than encoded in the argument list:
 *
 *   where the path comes from -- {@link #texture} / {@link #variant}
 *   how it renders            -- {@link #as} / {@link #tinted}
 *   what the key records      -- {@link #keyedAs} / {@link #onlyIf} / {@link #orKey}
 *
 * {@link #add()} is the terminal operation and always closes the slot with exactly one
 * delimiter, so a slot that is built can never shift the fields after it. It cannot help
 * with a slot that is never built at all -- a branch that skips the builder entirely must
 * call {@link TextureCacheKeyWriter#addSkippedSlot()} to hold the position.
 */
public final class TextureSlot {

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

    public TextureSlot(TextureCacheKeyWriter key, TextureGrouping group) {
        this.key = key;
        this.group = group;
    }

    /* ---- where the path comes from ---- */

    /** A literal path. The key records "0" unless {@link #keyedAs} overrides it. */
    public TextureSlot texture(String path) {
        this.path = path;
        return this;
    }

    /*
     * The table is indexed in add(), not here. Callers routinely derive an index that is only
     * valid when the slot is included -- "pied - 1" is -1 for an unpied axolotl -- so resolving
     * eagerly would throw on exactly the animals that skip the texture.
     */

    /** {@code table[index]}. The key records the index. */
    public TextureSlot variant(String[] table, int index) {
        this.deferredPath = () -> table[index];
        this.variantIndex = index;
        this.keyFragment = String.valueOf(index);
        return this;
    }

    /** {@code table[first][second]}. The key records both indexes. */
    public TextureSlot variant(String[][] table, int first, int second) {
        this.deferredPath = () -> table[first][second];
        this.keyFragment = String.valueOf(first) + second;
        return this;
    }

    /** {@code table[first][second][third]}. The key records all three indexes. */
    public TextureSlot variant(String[][][] table, int first, int second, int third) {
        this.deferredPath = () -> table[first][second][third];
        this.keyFragment = String.valueOf(first) + second + third;
        return this;
    }

    /* ---- how it renders ---- */

    public TextureSlot as(TexturingType texturingType) {
        this.texturingType = texturingType;
        return this;
    }

    /** Applies a colour to the layer. The colour joins the key, since it varies per animal. */
    public TextureSlot tinted(TexturingType texturingType, int argb) {
        this.texturingType = texturingType;
        this.rgb = argb;
        return this;
    }

    public TextureSlot flipped(int... cubes) {
        this.texturingType = TexturingType.APPLY_FLIP;
        this.cubes = cubes;
        return this;
    }

    /* ---- what the key records ---- */

    /**
     * Overrides the key fragment. An empty fragment means the texture does not exist for
     * this animal: no layer is added and the key records "0", matching the long-standing
     * behaviour of the textureName overloads this replaces.
     */
    public TextureSlot keyedAs(String fragment) {
        if (fragment == null || fragment.isEmpty()) {
            this.included = false;
            this.skipFragment = "0";
        } else {
            this.keyFragment = fragment;
        }
        return this;
    }

    /** Adds the layer only if the condition holds. When skipped the key field is empty. */
    public TextureSlot onlyIf(boolean condition) {
        this.included = condition;
        return this;
    }

    /** Tests the index passed to {@link #variant(String[], int)}. */
    public TextureSlot onlyIf(IntPredicate test) {
        if (this.variantIndex == null) {
            //a multi index variant has no single index to test, and a literal texture has none
            //at all; silently including the layer would hide the mistake
            throw new IllegalStateException("onlyIf(IntPredicate) needs variant(String[], int); use onlyIf(boolean)");
        }
        this.included = test.test(this.variantIndex);
        return this;
    }

    /** What the key records when the slot is skipped. Without this the field is empty. */
    public TextureSlot orKey(String fragmentWhenSkipped) {
        this.skipFragment = fragmentWhenSkipped;
        return this;
    }

    /**
     * Adds the layer without writing a key field. For loops whose layers are covered by a
     * single field written by the caller -- the slot count must stay constant, so a loop
     * with a gene-dependent iteration count cannot write one field per iteration.
     */
    public TextureSlot noKey() {
        this.writesKeyField = false;
        return this;
    }

    /* ---- terminal ---- */

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
