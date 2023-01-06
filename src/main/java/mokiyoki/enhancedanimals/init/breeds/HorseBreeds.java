package mokiyoki.enhancedanimals.init.breeds;

import mokiyoki.enhancedanimals.util.Breed;
import mokiyoki.enhancedanimals.util.GeneSketch;
import net.minecraft.world.level.biome.Biomes;

public class HorseBreeds {
    public static final Breed WHITE = new Breed(new Breed.Properties().setData("SnowyWhite", Biomes.PLAINS, Breed.Rarity.COMMON).setGeneSketch(new GeneSketch(), new GeneSketch()));

}
