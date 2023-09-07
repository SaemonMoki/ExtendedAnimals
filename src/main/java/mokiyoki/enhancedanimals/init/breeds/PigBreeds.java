package mokiyoki.enhancedanimals.init.breeds;

import mokiyoki.enhancedanimals.util.Breed;
import mokiyoki.enhancedanimals.util.GeneSketch;
import net.minecraft.world.level.biome.Biomes;

public class PigBreeds {
    public static final Breed LARGEWHITE = new Breed(new Breed.Properties().setData("LargeWhite", Biomes.FOREST, Breed.Rarity.ORDINARY)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(0, "3","3").add(12, "1","1").add(18, "2").add(32, "2","2","2","2").add(68, "3","3","1","3","3","3")));
    public static final Breed DUROC = new Breed(new Breed.Properties().setData("Duroc", Biomes.FOREST, Breed.Rarity.ORDINARY)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(0, "4","4").add(10, "1","3","1").add(32, "2","2", "2","2").add(130, "1", "2", "2", "2", "2", "2").add(142, "2", "2").add(150, "2")));
    public static final Breed HAMPSHIRE = new Breed(new Breed.Properties().setData("Hampshire", Biomes.FOREST, Breed.Rarity.ORDINARY)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(0, "5","1").add(12, "2","1").add(32, "2","1","2","2").add(68, "3","3","1","3","3","3")));
    public static final Breed BERKSHIRE = new Breed(new Breed.Properties().setData("Berkshire", Biomes.FOREST, Breed.Rarity.ORDINARY)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(0, "3","1").add(12, "3","1").add(32, "2","1", "2","2").add(62, "1","2").add(68, "3","3","1","3","3","3")));
    public static final Breed LARGEBLACK = new Breed(new Breed.Properties().setData("LargeBlack", Biomes.FOREST, Breed.Rarity.ORDINARY)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(0, "1", "1").add(12, "3","1").add(32, "2","1","2","2").add(68, "2","2","1","2","1","1")));
    public static final Breed MULEFOOT = new Breed(new Breed.Properties().setData("Mulefoot", Biomes.FOREST, Breed.Rarity.ORDINARY)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(0, "5","1").add(12, "3","1").add(32, "2","1","2","2").add(60, "2")));

    public static final Breed GLOUCESTERSHIRE = new Breed(new Breed.Properties().setData("GloucestershireOldSpot", Biomes.FOREST, Breed.Rarity.ORDINARY)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(0, "3").add(12, "3","1").add(32, "2","1","2","2")));
    public static final Breed KUNEKUNE = new Breed(new Breed.Properties().setData("KuneKune", Biomes.FOREST, Breed.Rarity.RARE)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(0, "3","3").add(10, "1","3","1").add(18, "5").add(32, "1","1","2","2","1","4","3","2").add(50, "1","3").add(58, "2").add(66, "5").add(68, "1","1","1","1","1")));
    public static final Breed PIETRAIN = new Breed(new Breed.Properties().setData("Pietrain", Biomes.FOREST, Breed.Rarity.ORDINARY)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(0, "3").add(12, "4","1").add(32, "2","1", "2","2").add(68, "3","3","1","3","3","3")));
    public static final Breed PROTESTPIG = new Breed(new Breed.Properties().setData("DanishProtestPigHusumRedPied", Biomes.FOREST, Breed.Rarity.ORDINARY)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(0, "3").add(12, "2","1").add(32, "2","1","2","2").add(62, "2","1")));
    public static final Breed TAMWORTH = new Breed(new Breed.Properties().setData("Tamworth", Biomes.FOREST, Breed.Rarity.ORDINARY)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(0, "3").add(12, "3","1").add(32, "2","1","2","2").add(62, "2","1")));
    public static final Breed MANGALITSA = new Breed(new Breed.Properties().setData("Mangalitsa", Biomes.FOREST, Breed.Rarity.ORDINARY)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(0, "1", "4").add(12, "3","1").add(32, "2","1","2","3").add(62, "1","1")));
    public static final Breed HEREFORDPIG = new Breed(new Breed.Properties().setData("HerefordPig", Biomes.FOREST, Breed.Rarity.ORDINARY)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(0, "3").add(12, "3","4","3").add(32, "2","1","2","2").add(62, "2","1")));

}
