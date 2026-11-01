package mokiyoki.enhancedanimals.util;

import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.neoforged.neoforge.common.Tags;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

public enum LegacyBiomeCategory {
    NONE,
    PLAINS,
    ICY,
    DESERT,
    SAVANNA,
    MOUNTAIN,
    EXTREME_HILLS;

    private static final Set<ResourceKey<Biome>> NEWER_VANILLA_BIOMES = Set.of(Biomes.CHERRY_GROVE, Biomes.MANGROVE_SWAMP, Biomes.DEEP_DARK);

    private static final Map<ResourceKey<Biome>, LegacyBiomeCategory> VANILLA_CATEGORIES = Map.ofEntries(
            Map.entry(Biomes.PLAINS, PLAINS),
            Map.entry(Biomes.SUNFLOWER_PLAINS, PLAINS),
            Map.entry(Biomes.SNOWY_PLAINS, ICY),
            Map.entry(Biomes.ICE_SPIKES, ICY),
            Map.entry(Biomes.DESERT, DESERT),
            Map.entry(Biomes.SAVANNA, SAVANNA),
            Map.entry(Biomes.SAVANNA_PLATEAU, SAVANNA),
            Map.entry(Biomes.WINDSWEPT_SAVANNA, SAVANNA),
            Map.entry(Biomes.MEADOW, MOUNTAIN),
            Map.entry(Biomes.SNOWY_SLOPES, MOUNTAIN),
            Map.entry(Biomes.FROZEN_PEAKS, MOUNTAIN),
            Map.entry(Biomes.JAGGED_PEAKS, MOUNTAIN),
            Map.entry(Biomes.STONY_PEAKS, MOUNTAIN),
            Map.entry(Biomes.WINDSWEPT_HILLS, EXTREME_HILLS),
            Map.entry(Biomes.WINDSWEPT_GRAVELLY_HILLS, EXTREME_HILLS),
            Map.entry(Biomes.WINDSWEPT_FOREST, EXTREME_HILLS)
    );

    public static LegacyBiomeCategory of(Holder<Biome> biome) {
        Optional<ResourceKey<Biome>> key = biome.unwrapKey();
        if (key.isPresent() && key.get().location().getNamespace().equals("minecraft") && !NEWER_VANILLA_BIOMES.contains(key.get())) {
            return VANILLA_CATEGORIES.getOrDefault(key.get(), NONE);
        }
        if (biome.is(Tags.Biomes.IS_PLAINS)) return PLAINS;
        if (biome.is(Tags.Biomes.IS_ICY)) return ICY;
        if (biome.is(Tags.Biomes.IS_DESERT)) return DESERT;
        if (biome.is(Tags.Biomes.IS_SAVANNA)) return SAVANNA;
        if (biome.is(Tags.Biomes.IS_MOUNTAIN)) return MOUNTAIN;
        if (biome.is(Tags.Biomes.IS_WINDSWEPT)) return EXTREME_HILLS;
        return NONE;
    }

    public static Biome.Precipitation precipitationOf(Biome biome) {
        if (!biome.hasPrecipitation()) {
            return Biome.Precipitation.NONE;
        }
        return biome.getBaseTemperature() < 0.15F ? Biome.Precipitation.SNOW : Biome.Precipitation.RAIN;
    }
}
