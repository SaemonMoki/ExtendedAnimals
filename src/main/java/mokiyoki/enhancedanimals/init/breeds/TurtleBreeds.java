package mokiyoki.enhancedanimals.init.breeds;

import mokiyoki.enhancedanimals.util.Breed;
import mokiyoki.enhancedanimals.util.GeneSketch;
import net.minecraft.world.level.biome.Biomes;

public class TurtleBreeds {
    public static final Breed ALBINO = new Breed(new Breed.Properties().setData("albino", 0.8F, 0.4F, Breed.Rarity.RARE)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(0, "2")));
    public static final Breed AXANTHIC = new Breed(new Breed.Properties().setData("axanthic", 0.8F, 0.4F, Breed.Rarity.RARE)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(2, "2")));
    public static final Breed MELANIZED = new Breed(new Breed.Properties().setData("melanizedblack", 0.8F, 0.4F, Breed.Rarity.RARE)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(2, "2","2")));
    public static final Breed PIBALD = new Breed(new Breed.Properties().setData("pibaldspotted", 0.8F, 0.4F, Breed.Rarity.RARE)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(6, "2")));
    public static final Breed SNOW = new Breed(new Breed.Properties().setData("snow", 0.8F, 0.4F, Breed.Rarity.RARE)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(0, "2","2")));

}
