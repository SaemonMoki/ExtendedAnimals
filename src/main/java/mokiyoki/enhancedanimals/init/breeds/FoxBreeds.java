package mokiyoki.enhancedanimals.init.breeds;

import mokiyoki.enhancedanimals.util.Breed;
import mokiyoki.enhancedanimals.util.GeneSketch;
import net.minecraft.world.level.biome.Biomes;

public class FoxBreeds {
    public static final Breed RED = new Breed(new Breed.Properties().setData("Red", Biomes.FOREST, Breed.Rarity.ORDINARY)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(0, "1","1").add(2, "1","1")));
    public static final Breed BLACK = new Breed(new Breed.Properties().setData("Black", Biomes.FOREST, Breed.Rarity.ORDINARY)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(0, "2","2").add(2, "2","2")));

    public static final Breed CROSS1 = new Breed(new Breed.Properties().setData("Cross1", Biomes.FOREST, Breed.Rarity.UNCOMMON)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(0, "1","1").add(2, "2","2")));


}
