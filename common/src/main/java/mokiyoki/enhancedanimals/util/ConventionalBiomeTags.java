package mokiyoki.enhancedanimals.util;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;

public final class ConventionalBiomeTags {

    public static final TagKey<Biome> IS_PLAINS = tag("is_plains");
    public static final TagKey<Biome> IS_ICY = tag("is_icy");
    public static final TagKey<Biome> IS_DESERT = tag("is_desert");
    public static final TagKey<Biome> IS_SAVANNA = tag("is_savanna");
    public static final TagKey<Biome> IS_MOUNTAIN = tag("is_mountain");
    public static final TagKey<Biome> IS_WINDSWEPT = tag("is_windswept");

    private ConventionalBiomeTags() {
    }

    private static TagKey<Biome> tag(String name) {
        return TagKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath("c", name));
    }
}
