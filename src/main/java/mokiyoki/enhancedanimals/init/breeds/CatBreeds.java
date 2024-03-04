package mokiyoki.enhancedanimals.init.breeds;

import mokiyoki.enhancedanimals.util.Breed;
import mokiyoki.enhancedanimals.util.GeneSketch;
import net.minecraft.world.level.biome.Biomes;

public class CatBreeds {
    public static final Breed BLACK = new Breed(new Breed.Properties().setData("Black", Biomes.FOREST, Breed.Rarity.ORDINARY)
            .setGeneSketch(new GeneSketch().add(0, "1"), new GeneSketch().add(0, "2").add(12, "1", "1", "1").add(50,"2","2","1|2","1|2","1|2")));
    public static final Breed BRITISHSHORTHAIR = new Breed(new Breed.Properties().setData("BritishShorthair", Biomes.FOREST, Breed.Rarity.ORDINARY)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(0, "1|2").add(12, "1", "1", "1").add(28,"2").add(50,"2","2","1|2","1|2","1|2")));
    public static final Breed ORIENTALSHORTHAIR = new Breed(new Breed.Properties().setData("OrientalShortHair", Biomes.FOREST, Breed.Rarity.ORDINARY)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(40, "3|4").add(46, "2|3").add(48, "4|5").add(94,"1","1","1").add(100, "1-3", "1-3", "1-3", "1-3").add(108, "2|3", "2|3", "2|3", "2|3").add(120,"1", "1", "4|5", "4|5").add(128, "1-3", "1-3", "2|3", "2|3").add(144,"3-5", "1")));

//    public static final Breed BENGAL = new Breed(new Breed.Properties().setData("Bengal", Biomes.FOREST, Breed.Rarity.ORDINARY)
//            .setGeneSketch(new GeneSketch(), new GeneSketch().add(32, "2")));

}
