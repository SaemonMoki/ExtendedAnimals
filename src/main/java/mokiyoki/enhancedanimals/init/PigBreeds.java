package mokiyoki.enhancedanimals.init;

import mokiyoki.enhancedanimals.util.Breed;
import mokiyoki.enhancedanimals.util.GeneSketch;
import net.minecraft.world.biome.Biomes;

public class PigBreeds {
    public static final Breed LARGEWHITE = new Breed(new Breed.Properties().setData("LargeWhite", Biomes.BIRCH_FOREST_HILLS, Breed.Rarity.ORDINARY)
            .setGeneSketch(new GeneSketch(), new GeneSketch()));
}
