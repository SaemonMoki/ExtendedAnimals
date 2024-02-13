package mokiyoki.enhancedanimals.init.breeds;

import mokiyoki.enhancedanimals.util.Breed;
import mokiyoki.enhancedanimals.util.GeneSketch;
import net.minecraft.world.level.biome.Biomes;

public class CatBreeds {
    public static final Breed BLACK = new Breed(new Breed.Properties().setData("Black", Biomes.FOREST, Breed.Rarity.ORDINARY)
            .setGeneSketch(new GeneSketch().add(0, "1"), new GeneSketch().add(0, "2").add(12, "1", "1", "1").add(50,"2","2","1|2","1|2","1|2")));
    public static final Breed BRITISHSHORTHAIR = new Breed(new Breed.Properties().setData("BritishShorthair", Biomes.FOREST, Breed.Rarity.ORDINARY)
            .setGeneSketch(new GeneSketch().add(0, "1"), new GeneSketch().add(0, "1|2").add(12, "1", "1", "1").add(28,"2").add(50,"2","2","1|2","1|2","1|2")));
}
