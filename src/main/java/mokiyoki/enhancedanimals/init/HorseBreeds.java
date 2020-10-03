package mokiyoki.enhancedanimals.init;

import mokiyoki.enhancedanimals.util.Breed;
import mokiyoki.enhancedanimals.util.GeneSketch;
import net.minecraft.world.biome.Biomes;

public class HorseBreeds {
    public static final Breed WHITE = new Breed(new Breed.Properties().setData("SnowyWhite", Biomes.SNOWY_TUNDRA, Breed.Rarity.COMMON).setGeneSketch(new GeneSketch(), new GeneSketch()));

}
