package mokiyoki.enhancedanimals.init;

import mokiyoki.enhancedanimals.util.Breed;
import mokiyoki.enhancedanimals.util.GeneSketch;
import net.minecraft.world.biome.Biomes;

public class CowBreeds {
    public static final Breed HEREFORD = new Breed(new Breed.Properties().setData("Hereford", Biomes.BIRCH_FOREST_HILLS, Breed.Rarity.ORDINARY)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(0, "3", "1", "4").add(8, "1").add(12, "1", "2", "1", "4", "2").add(24, "1", "2", "1", "1-5", "5-10", "1", "3", "1", "4", "1", "1").add(54, "3").add(70, "1", "1", "2", "2", "2", "3", "2").add(86, "1", "1", "1").add(120, "1")));

    public static final Breed ANGUS = new Breed(new Breed.Properties().setData("Angus", Biomes.JUNGLE, Breed.Rarity.ORDINARY)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(0, "1", "1", "4").add(8, "1").add(12, "1", "2", "3", "4", "2").add(24, "1", "2", "1", "1-5", "5-10", "1", "3", "1", "4", "1", "1").add(54, "3").add(70, "1", "1", "2", "2", "2", "3", "2").add(86, "1", "1", "1").add(120, "1")));

    public static final Breed BLACK_ANGUS = new Breed(ANGUS, new Breed.Properties().setName("BlackAngus"));

    public static final Breed RED_ANGUS = new Breed(ANGUS, new Breed.Properties().setName("RedAngus")
            .setGeneSketch(new GeneSketch(), new GeneSketch(0, "3")));

    public static final Breed HOLSTEIN = new Breed(new Breed.Properties().setData("HolsteinFresian", Biomes.FOREST, Breed.Rarity.ORDINARY)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(0, "1","1","2").add(6, "5%1","2").add(8, "1","1","1","2","4").add(18,"15%2","4","2").add(24,"1","2","1","1-5","10-5","1","3","1","4","2|3").add(50, "1","1","1").add(62, "10", "10", "1").add(70, "1","1","2","2","2").add(120, "1")));

    public static final Breed JERSEY = new Breed(new Breed.Properties().setData("Jersey", Biomes.DARK_FOREST, Breed.Rarity.COMMON)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(0, "2","1","2","2","1","1").add(14, "2","3","4","2").add(24,"2").add(30, "15","10","3","1","1","4","1-3").add(48, "2","1","1","1","1|2","1|2","1|2","10","1","5","5","1","1","2","2","2")));

    public static final Breed TEXAS_LONGHORN = new Breed(new Breed.Properties().setData("texaslonghorn", Biomes.SAVANNA, Breed.Rarity.COMMON)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(0, "10%1","2|3").add(12, "2","2","2|3|4","2|3|4","1","_","25%2","1").add(38, "1").add(44, "1").add(54, "2").add(70, "1","1","2").add(80, "4","1").add(88, "1","1")
                .add(84, "1-4")
                .add(86, "1-2")
                .add(90, "1-2")
                .add(92,"500")/*.add(92,"1-9999")*/  //170-199, 1091, 2091,
                .add(94, "1-999999")/*.add(94, "1-999999")*/
                .add(96,"1-9999")/*.add(96,"1-9999")*/
                .add(98,"1-999")/*.add(98,"1-999")*/
                .add(100, "1-999")/*.add(100, "1-999")*/
                .add(102, "1-999")/*.add(102, "1-999")*/
                .add(104, "1-999")/*.add(104, "1-999")*/
                .add(106, "1-999")/*.add(106, "1-999")*/
                .add(108, "1-999")/*.add(108, "1-999")*/
                .add(110, "1-999")/*.add(110, "1-999")*/
                .add(112, "1-999")/*.add(112, "1-999")*/
                .add(114, "1-999")/*.add(114, "1-999")*/
                .add(116, "1-999")/*.add(116, "1-999")*/
            ));


}
