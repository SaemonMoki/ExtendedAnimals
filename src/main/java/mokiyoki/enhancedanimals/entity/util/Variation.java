package mokiyoki.enhancedanimals.entity.util;

import javax.annotation.Nullable;
import java.util.UUID;

/**
 * Deterministic per-animal randomness for things genes don't decide: where spots land, which
 * horn is shorter, where mushrooms grow. Replaces reading characters out of the animal's UUID.
 *
 * Each animal has a variation seed (see {@code EnhancedAnimalAbstract#getVariation()}); each
 * {@link VariationKey} hashes with that seed into its own 64 bit value. The same seed and key
 * always give the same answer on client and server, there is no limit on the number of keys,
 * and every value is evenly distributed.
 *
 * Calls with the same key read the same underlying value, so for example
 * {@code get(key, 4)} is always {@code hex(key) % 4} (except on legacy animals, below).
 *
 * Legacy animals (ones that existed before variation seeds) also carry the UUID string their
 * patterns used to be read from. For them, keys with a legacy index reproduce the old reads
 * exactly, quirks included: {@link #hexChar} is the raw character, {@link #hex} its digit value
 * and {@link #get} the character code modulo bound. Keys without a legacy index always hash.
 */
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

    /** True if this animal predates variation seeds and keeps its old UUID based patterns. */
    public boolean isLegacy() {
        return this.legacyUUID != null;
    }

    public int getSeed() {
        return this.seed;
    }

    /** A value in {@code [0, bound)}. */
    public int get(VariationKey key, int bound) {
        if (readsLegacy(key)) {
            return legacyChar(key) % bound;
        }
        return (int) Long.remainderUnsigned(raw(key), bound);
    }

    /** A value in {@code [0, 16)}: the equivalent of one old UUID character. */
    public int hex(VariationKey key) {
        if (readsLegacy(key)) {
            return Character.digit(legacyChar(key), 16);
        }
        return get(key, 16);
    }

    /** {@link #hex} as '0'-'9' / 'a'-'f', for textures named by hex digit. */
    public char hexChar(VariationKey key) {
        if (readsLegacy(key)) {
            return legacyChar(key);
        }
        return Character.forDigit(hex(key), 16);
    }

    /** A value in {@code [0, 1)}, for continuous variation such as sizes or angles. */
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

    /** A non-zero seed taken from a UUID; used for animals that don't have a stored seed yet. */
    public static int seedFrom(UUID uuid) {
        long folded = mix(uuid.getMostSignificantBits() ^ Long.rotateLeft(uuid.getLeastSignificantBits(), 32));
        int seed = (int) (folded ^ (folded >>> 32));
        return seed == 0 ? 1 : seed;
    }

    //SplitMix64 finaliser
    static long mix(long z) {
        z = (z ^ (z >>> 30)) * 0xbf58476d1ce4e5b9L;
        z = (z ^ (z >>> 27)) * 0x94d049bb133111ebL;
        return z ^ (z >>> 31);
    }
}
