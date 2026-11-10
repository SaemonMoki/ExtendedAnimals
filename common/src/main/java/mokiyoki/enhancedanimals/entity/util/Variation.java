package mokiyoki.enhancedanimals.entity.util;

import org.jetbrains.annotations.Nullable;
import java.util.UUID;

//seeded per-animal randomness for things genes don't decide (spot placement, horn lengths, etc), same on client and server.
//legacy animals keep reading their old UUID characters for any key with a legacy index so their looks don't change
public final class Variation {
    private final int seed;
    @Nullable
    private final String legacyUUID;

    public Variation(int seed) {
        this(seed, null);
    }

    public Variation(int seed, @Nullable String legacyUUID) {
        this.seed = seed;
        this.legacyUUID = legacyUUID == null || legacyUUID.isEmpty() ? null : legacyUUID;
    }

    public boolean isLegacy() {
        return this.legacyUUID != null;
    }

    public int getSeed() {
        return this.seed;
    }

    //0 to bound-1
    public int get(VariationKey key, int bound) {
        if (readsLegacy(key)) {
            return legacyChar(key) % bound;
        }
        return (int) Long.remainderUnsigned(raw(key), bound);
    }

    //0-15, same as reading one old UUID character
    public int hex(VariationKey key) {
        if (readsLegacy(key)) {
            return Character.digit(legacyChar(key), 16);
        }
        return get(key, 16);
    }

    //hex as a character, for textures named 0-f
    public char hexChar(VariationKey key) {
        if (readsLegacy(key)) {
            return legacyChar(key);
        }
        return Character.forDigit(hex(key), 16);
    }

    //0 to 1, for sizes and angles
    public float fraction(VariationKey key) {
        return (raw(key) >>> 40) * 0x1.0p-24F;
    }

    private boolean readsLegacy(VariationKey key) {
        return this.legacyUUID != null && key.hasLegacyIndex();
    }

    private char legacyChar(VariationKey key) {
        return this.legacyUUID.charAt(key.getLegacyIndex());
    }

    private long raw(VariationKey key) {
        return mix(this.seed ^ key.getSalt());
    }

    //for animals that don't have a seed yet
    public static int seedFrom(UUID uuid) {
        long folded = mix(uuid.getMostSignificantBits() ^ Long.rotateLeft(uuid.getLeastSignificantBits(), 32));
        int seed = (int) (folded ^ (folded >>> 32));
        return seed == 0 ? 1 : seed;
    }

    static long mix(long z) {
        z = (z ^ (z >>> 30)) * 0xbf58476d1ce4e5b9L;
        z = (z ^ (z >>> 27)) * 0x94d049bb133111ebL;
        return z ^ (z >>> 31);
    }
}
