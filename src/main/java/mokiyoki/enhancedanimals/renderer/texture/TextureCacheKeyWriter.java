package mokiyoki.enhancedanimals.renderer.texture;

/**
 * Implemented by anything that accumulates a texture cache key.
 *
 * The key is a flat list of fields, one per texture slot, each closed by a delimiter.
 * A slot MUST write exactly one field every time the texture is calculated, whether or
 * not it contributed a layer -- otherwise every later slot shifts left and two different
 * genetics can compile to the same key.
 */
public interface TextureCacheKeyWriter {

    /** Writes one field made of the given fragments, then closes the slot. */
    void writeKeyField(String... fragments);

    /** Writes one empty field, reserving the position of a slot that added nothing. */
    void addSkippedSlot();

    /** Reserves {@code count} consecutive slot positions that added nothing. */
    void addSkippedSlots(int count);
}
