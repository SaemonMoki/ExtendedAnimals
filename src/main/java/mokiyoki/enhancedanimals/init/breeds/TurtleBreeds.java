package mokiyoki.enhancedanimals.init.breeds;

import mokiyoki.enhancedanimals.util.Breed;
import mokiyoki.enhancedanimals.util.GeneSketch;

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
    public static final Breed NATURAL = new Breed(new Breed.Properties().setData("natural", Biomes.BEACH, Breed.Rarity.RARE)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(4, "1","1").add(10, "2").add(30, "1","2").add(50, "2","2","2","2").add(66, "2","2").add(72, "3|2")));

    public static final Breed SCALE = new Breed(new Breed.Properties().setData("scale", Biomes.BEACH, Breed.Rarity.RARE)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(30, "1","2")));

    public static final Breed CLOWN = new Breed(new Breed.Properties().setData("clown", Biomes.BEACH, Breed.Rarity.RARE)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(30, "1","3")));

    public static final Breed FLAME = new Breed(new Breed.Properties().setData("flame", Biomes.BEACH, Breed.Rarity.RARE)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(30, "3","1")));

    public static final Breed FLAME_SCALE = new Breed(new Breed.Properties().setData("flamescale", Biomes.BEACH, Breed.Rarity.RARE)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(30, "3","2")));

    public static final Breed FLAME_CLOWN = new Breed(new Breed.Properties().setData("flameclown", Biomes.BEACH, Breed.Rarity.RARE)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(30, "3","3")));

    public static final Breed PATTERNLESS = new Breed(new Breed.Properties().setData("patternless", Biomes.BEACH, Breed.Rarity.RARE)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(30, "4")));

    public static final Breed FLAME_SCALE_BLUE = new Breed(new Breed.Properties().setData("flamescaleblue", Biomes.BEACH, Breed.Rarity.RARE)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(2,"2").add(30, "3","2")));

    public static final Breed GOLDEN = new Breed(new Breed.Properties().setData("golden", Biomes.BEACH, Breed.Rarity.RARE)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(34, "2","2","2","2","2","2","2","2").add(74, "2","2")));

    public static final Breed GOLD_SCALED = new Breed(new Breed.Properties().setData("goldscaled", Biomes.BEACH, Breed.Rarity.RARE)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(30, "1","2").add(34, "2","2","2","2","2","2","2","2").add(72, "1")));

    public static final Breed LAVENDER = new Breed(new Breed.Properties().setData("lavender", Biomes.BEACH, Breed.Rarity.RARE)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(0, "1","1|2").add(70, "2")));

    public static final Breed TORTISHELL = new Breed(new Breed.Properties().setData("tortishellbrindle", Biomes.BEACH, Breed.Rarity.RARE)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(10, "2")));

    public static final Breed COLOURFUL_TORTISHELL = new Breed(new Breed.Properties().setData("colourfultortishell", Biomes.BEACH, Breed.Rarity.RARE)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(2,"1|2").add(10, "2").add(50, "1|2","1|2","1|2","1|2","1|2","1|2","1|2","1|2").add(66, "1|2", "1|2")));

    public static final Breed COLOURFUL_DARK = new Breed(new Breed.Properties().setData("darkcolours", Biomes.BEACH, Breed.Rarity.RARE)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(2,"1|2").add(10, "2|1").add(34, "1|2","1|2","1|2","1|2","1|2","1|2","1|2","1|2").add(50, "2","2","2","2","2","2","2","2").add(66, "1|2", "1|2").add(72, "1|2|3")));


}
