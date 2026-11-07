package mokiyoki.enhancedanimals.entity.util;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public final class VariationKey {
    private static final Set<String> NAMES = ConcurrentHashMap.newKeySet();

    private static final int NO_LEGACY_INDEX = -1;

    private final String name;
    private final long salt;
    private final int legacyIndex;

    private VariationKey(String name, int legacyIndex) {
        this.name = name;
        this.salt = Variation.mix(fnv1a64(name));
        this.legacyIndex = legacyIndex;
    }

    public static VariationKey of(String name) {
        return create(name, NO_LEGACY_INDEX);
    }

    public static VariationKey[] series(String name, int count) {
        VariationKey[] keys = new VariationKey[count];
        for (int i = 0; i < count; i++) {
            keys[i] = of(name + "#" + i);
        }
        return keys;
    }

    // Only for features that existed before variation seeds
    static VariationKey legacy(String name, int legacyIndex) {
        if (legacyIndex < 0 || legacyIndex >= 36) {
            throw new IllegalArgumentException("Legacy index must be a UUID string position: " + legacyIndex);
        }
        return create(name, legacyIndex);
    }

    // for legacy features, reading UUID characters from firstLegacyIndex onwards.
    static VariationKey[] legacySeries(String name, int count, int firstLegacyIndex) {
        VariationKey[] keys = new VariationKey[count];
        for (int i = 0; i < count; i++) {
            keys[i] = legacy(name + "#" + i, firstLegacyIndex + i);
        }
        return keys;
    }

    private static VariationKey create(String name, int legacyIndex) {
        if (!NAMES.add(name)) {
            throw new IllegalStateException("Duplicate VariationKey name: " + name);
        }
        return new VariationKey(name, legacyIndex);
    }

    public String getName() {
        return this.name;
    }

    long getSalt() {
        return this.salt;
    }

    boolean hasLegacyIndex() {
        return this.legacyIndex != NO_LEGACY_INDEX;
    }

    int getLegacyIndex() {
        return this.legacyIndex;
    }

    //this is for stable jvm version seeding don't fucking touch this number or I'll kill you
    private static long fnv1a64(String s) {
        long hash = 0xcbf29ce484222325L;
        for (int i = 0; i < s.length(); i++) {
            hash ^= s.charAt(i);
            hash *= 0x100000001b3L;
        }
        return hash;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
