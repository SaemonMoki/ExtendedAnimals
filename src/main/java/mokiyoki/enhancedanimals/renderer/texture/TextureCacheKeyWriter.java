package mokiyoki.enhancedanimals.renderer.texture;

// Every texture slot must write exactly one key field, even if it adds no texture,
// otherwise later fields shift and different animals can end up with the same key.
public interface TextureCacheKeyWriter {

    void writeKeyField(String... fragments);

    void addSkippedSlot();

    void addSkippedSlots(int count);
}
