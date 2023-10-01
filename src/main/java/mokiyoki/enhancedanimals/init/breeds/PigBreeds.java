package mokiyoki.enhancedanimals.init.breeds;

import mokiyoki.enhancedanimals.util.Breed;
import mokiyoki.enhancedanimals.util.GeneSketch;
import net.minecraft.world.level.biome.Biomes;

public class PigBreeds {
    public static final Breed LARGEWHITE = new Breed(new Breed.Properties().setData("LargeWhite", Biomes.FOREST, Breed.Rarity.ORDINARY)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(0, "3","3").add(12, "6,6|5|8|10|11|12","1").add(18, "2").add(32, "2","2","2","2").add(68, "3","3","1","3","3","3").add(160, "1|2").add(174, "5","10")));
    public static final Breed DUROC = new Breed(new Breed.Properties().setData("Duroc", Biomes.FOREST, Breed.Rarity.ORDINARY)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(0, "4","4").add(10, "1","3","1").add(18, "4").add(32, "2","2", "2","2").add(42, "3").add(68, "2","2").add(134, "1", "2", "2", "2", "2", "2").add(142, "2", "2").add(150,"2","2","2").add(166, "2","2","1","2","2","2","1","1")));
    public static final Breed HAMPSHIRE = new Breed(new Breed.Properties().setData("Hampshire", Biomes.FOREST, Breed.Rarity.ORDINARY)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(0, "5","3").add(12, "2","1").add(32, "2","1","2","2").add(68, "3","3","1","3","3","3")));
    public static final Breed BERKSHIRE = new Breed(new Breed.Properties().setData("Berkshire", Biomes.FOREST, Breed.Rarity.ORDINARY)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(0, "3","3").add(12, "3","1").add(32, "2","1", "2","2").add(62, "1","2").add(68, "3","3","1","3","3","3")));
    public static final Breed LARGEBLACK = new Breed(new Breed.Properties().setData("LargeBlack", Biomes.FOREST, Breed.Rarity.ORDINARY)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(0, "1", "3").add(12, "3","1").add(32, "2","1","2","2").add(68, "2","2","1","2","1","1")));
    public static final Breed MULEFOOT = new Breed(new Breed.Properties().setData("Mulefoot", Biomes.FOREST, Breed.Rarity.RARE)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(0, "5").add(12, "3","1").add(32, "2","1","2","2").add(60, "2")));
    public static final Breed GLOUCESTERSHIRE = new Breed(new Breed.Properties().setData("GloucestershireOldSpot", Biomes.FOREST, Breed.Rarity.ORDINARY)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(0, "3", "3").add(12, "3","1").add(32, "2","1","2","2")));
    public static final Breed KUNEKUNE = new Breed(new Breed.Properties().setData("KuneKune", Biomes.FOREST, Breed.Rarity.RARE)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(0, "3","1", "1|2").add(10, "1","3","1").add(18, "5").add(32, "1","1","2","2","1","4","3","2").add(50, "1","3").add(58, "2").add(66, "5").add(68, "1","1","1","1","1")));
    public static final Breed PIETRAIN = new Breed(new Breed.Properties().setData("Pietrain", Biomes.FOREST, Breed.Rarity.RARE)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(0, "3","3").add(12, "11","1","2").add(32, "2","1", "2","2").add(68, "3","3","1","3","3","3").add(166, "10", "10", "10", "2")));
    public static final Breed PROTESTPIG = new Breed(new Breed.Properties().setData("DanishProtestPigHusumRedPied", Biomes.FOREST, Breed.Rarity.ORDINARY)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(0, "3", "3").add(12, "2","1").add(32, "2","1","2","2").add(62, "2","1")));
    public static final Breed TAMWORTH = new Breed(new Breed.Properties().setData("Tamworth", Biomes.FOREST, Breed.Rarity.ORDINARY)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(0, "3", "3").add(12, "3","1").add(32, "2","1","2","2").add(42, "1").add(62, "2","1").add(72, "2","1").add(166, "1","1","1","1","1","1","1","1").add(182, "5-7","5-7","6-10","8-10")));
    public static final Breed MANGALITSA = new Breed(new Breed.Properties().setData("Mangalitsa", Biomes.FOREST, Breed.Rarity.RARE)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(0, "2", "4").add(12, "3","1").add(32, "2","1","2","3").add(62, "1","1").add(68, "2","2").add(130, "1","1","2","2","2","2","2").add(150, "2","2","2","2").add(158, "2").add(164, "2")));
    public static final Breed HEREFORDPIG = new Breed(new Breed.Properties().setData("HerefordPig", Biomes.FOREST, Breed.Rarity.RARE)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(0, "3").add(12, "9","3","3").add(32, "2","1","2","2").add(62, "2","1").add(68, "2","2","2").add(148,"1")));

}
