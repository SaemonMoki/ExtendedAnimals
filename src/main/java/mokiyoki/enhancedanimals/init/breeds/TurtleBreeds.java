package mokiyoki.enhancedanimals.init.breeds;

import mokiyoki.enhancedanimals.util.Breed;
import mokiyoki.enhancedanimals.util.GeneSketch;
import net.minecraft.world.biome.Biomes;

public class TurtleBreeds {
    public static final Breed ALBINO = new Breed(new Breed.Properties().setData("albino", Biomes.BEACH, Breed.Rarity.RARE)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(0, "2")));
    public static final Breed AXANTHIC = new Breed(new Breed.Properties().setData("axanthic", Biomes.BEACH, Breed.Rarity.RARE)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(2, "2")));
    public static final Breed MELANIZED = new Breed(new Breed.Properties().setData("melanizedblack", Biomes.BEACH, Breed.Rarity.RARE)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(2, "2","2")));
    public static final Breed PIBALD = new Breed(new Breed.Properties().setData("pibaldspotted", Biomes.BEACH, Breed.Rarity.RARE)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(6, "2")));
    public static final Breed SNOW = new Breed(new Breed.Properties().setData("snow", Biomes.BEACH, Breed.Rarity.RARE)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(0, "2","2")));

}
