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
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(0, "2","1","2","2","1","1").add(14, "2","3","4","2").add(24,"2").add(30, "15","10","3","1","1","4","1-3").add(48, "2","1","1","1","1|2","1|2","1|2","10","1","5","5","1","1","2","2","2").add(62, "10")
                    .add(84, "2-4")
                    .add(92,"1988")/*.add(92,"1-9999")*/  //170-199, 1091, 2091,
                    .add(94, "244349")/*.add(94, "1-999999")*/
                    .add(96,"1557")/*.add(96,"1-9999")*/
                    .add(98,"205-207")/*.add(98,"1-999")*/
                    .add(100, "150-222")/*.add(100, "1-999")*/
                    .add(102, "150-160")/*.add(102, "1-999")*/
                    .add(104, "165")/*.add(104, "1-999")*/
                    .add(106, "177")/*.add(106, "1-999")*/
                    .add(108, "117-169")/*.add(108, "1-999")*/
                    .add(110, "125-150")/*.add(110, "1-999")*/
                    .add(112, "145-199")/*.add(112, "1-999")*/
                    .add(114, "161-169")/*.add(114, "1-999")*/
                    .add(116, "185-199")/*.add(116, "1-999")*/
                    .add(122, "5")
            ));

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

    public static final Breed WILD_HORNS = new Breed(new Breed.Properties().setData("truewildtype", Biomes.DARK_FOREST, Breed.Rarity.EXOTIC)
            .setGeneSketch(new GeneSketch() ,new GeneSketch().add(12, "2").add(80, "3").add(86, "1","1").add(90, "1")
                    .add(84, "1-4")
                    .add(92,"2222-3333")/*.add(92,"1-9999")*/  //170-199, 1091, 2091,
                    .add(94, "222222-333333")/*.add(94, "1-999999")*/
                    .add(96,"2222-3333")/*.add(96,"1-9999")*/
                    .add(98,"222-333")/*.add(98,"1-999")*/
                    .add(100, "222-333")/*.add(100, "1-999")*/
                    .add(102, "222-333")/*.add(102, "1-999")*/
                    .add(104, "222-333")/*.add(104, "1-999")*/
                    .add(106, "222-333")/*.add(106, "1-999")*/
                    .add(108, "222-333")/*.add(108, "1-999")*/
                    .add(110, "222-333")/*.add(110, "1-999")*/
                    .add(112, "222-333")/*.add(112, "1-999")*/
                    .add(114, "222-333")/*.add(114, "1-999")*/
                    .add(116, "222-333")/*.add(116, "1-999")*/
            ));

    public static final Breed DEXTER = new Breed(BLACK_ANGUS, new Breed.Properties().setName("Dexter")
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(10, "2").add(26, "1,2")));

}
