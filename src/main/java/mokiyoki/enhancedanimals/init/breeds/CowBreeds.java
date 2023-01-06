package mokiyoki.enhancedanimals.init.breeds;

import mokiyoki.enhancedanimals.util.Breed;
import mokiyoki.enhancedanimals.util.GeneSketch;
import net.minecraft.world.level.biome.Biomes;

public class CowBreeds {
    public static final Breed HEREFORD = new Breed(new Breed.Properties().setData("Hereford", Biomes.TALL_BIRCH_FOREST, Breed.Rarity.ORDINARY)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(0, "3", "1", "4").add(8, "1").add(12, "1", "2", "1", "4", "2").add(24, "1", "2", "1", "1-5", "5-10", "1", "3", "1", "4", "1", "1").add(54, "3").add(70, "1", "1", "2", "2", "2", "3", "2").add(86, "1", "1", "1").add(120, "1")));

    public static final Breed ANGUS = new Breed(new Breed.Properties().setData("Angus", Biomes.JUNGLE, Breed.Rarity.ORDINARY)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(0, "1", "1", "4").add(8, "1").add(12, "1", "2", "3", "3", "2").add(24, "1", "2", "1", "1-5", "5-10", "1", "3", "1", "4", "1", "1").add(54, "3").add(70, "1", "1", "2", "2", "2", "3", "2").add(86, "1", "1", "1").add(120, "1")));

    public static final Breed BLACK_ANGUS = new Breed(ANGUS, new Breed.Properties().setName("BlackAngus"));

    public static final Breed RED_ANGUS = new Breed(ANGUS, new Breed.Properties().setName("RedAngus")
            .setGeneSketch(new GeneSketch(), new GeneSketch(0, "3")));

    public static final Breed HOLSTEIN = new Breed(new Breed.Properties().setData("HolsteinFresian", Biomes.FOREST, Breed.Rarity.ORDINARY)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(0, "1","1","2").add(6, "5%1","2").add(8, "1","1","1","2","4").add(24,"1","2","1","1-5","10-5","1","3","1","4","2|3").add(50, "1","1","1").add(62, "10", "10", "1").add(70, "1","1","2","2","2").add(120, "1").add(252,"15%2","1")));

    public static final Breed JERSEY = new Breed(new Breed.Properties().setData("Jersey", Biomes.DARK_FOREST, Breed.Rarity.COMMON)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(0, "2|5","1","10%2|1|3","5","2","1","1").add(14, "2","3","_","2").add(24,"2").add(30, "15","10","3","1","1","4","1-3").add(48, "2","1","1","1","1|2","1|2","1|2","10","1","5","5","1","1","2","2","2").add(62, "10","10")
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
                    .add(130, "2","2","2","2","2","2","2","1|2","1|2","1|2")
                    .add(150, "1","1","1","1","1","1","1","1|2","1|2","1|2")
                    .add(170, "2","2","2")
                    .add(200, "2","1|2","2","1|2","2","1|2","2","2","2","2","2","2")
            ));

    public static final Breed LIGHT_JERSEY = new Breed(JERSEY, new Breed.Properties().setData("Light", Breed.Rarity.COMMON)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(170, "2","2","2","2","2","2","2","1","1","1","1","1","1","1","2","2","2","2","2","2","2","2","2","2","2","2","1","1","1","1","1","1","1","1","1","1","1","1")));

    public static final Breed DARK_JERSEY = new Breed(JERSEY, new Breed.Properties().setData("Dark", Breed.Rarity.COMMON)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(170, "1","1","1","1","1","1","1","2","2","2","2","2","2","2","1","1","1","1","1","1","1","1","1","1","1","1","2","2","2","2","2","2","2","2","2","2","2","2")));
    
    public static final Breed TEXAS_LONGHORN = new Breed(new Breed.Properties().setData("texaslonghorn", Biomes.SAVANNA, Breed.Rarity.COMMON)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(0, "10%1","2|3").add(12, "2","2","2|3|4","1|4","1","_","25%2","1").add(38, "1").add(44, "1").add(54, "2").add(70, "1","1","2").add(80, "4","1").add(88, "1","1")
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
                .add(122, "1")
            ));

    public static final Breed WILD_HORNS = new Breed(new Breed.Properties().setData("truewildtype", Biomes.DARK_FOREST, Breed.Rarity.EXOTIC)
            .setGeneSketch(new GeneSketch() ,new GeneSketch().add(12, "2").add(80, "3").add(86, "1","1").add(90, "1")
                    .add(84, "1-4")
                    .add(92,"1111-9999")/*.add(92,"1-9999")*/  //170-199, 1091, 2091,
                    .add(94, "111111-999999")/*.add(94, "1-999999")*/
                    .add(96,"1111-9999")/*.add(96,"1-9999")*/
                    .add(98,"111-999")/*.add(98,"1-999")*/
                    .add(100, "111-999")/*.add(100, "1-999")*/
                    .add(102, "111-999")/*.add(102, "1-999")*/
                    .add(104, "111-999")/*.add(104, "1-999")*/
                    .add(106, "111-999")/*.add(106, "1-999")*/
                    .add(108, "111-999")/*.add(108, "1-999")*/
                    .add(110, "111-999")/*.add(110, "1-999")*/
                    .add(112, "111-999")/*.add(112, "1-999")*/
                    .add(114, "111-999")/*.add(114, "1-999")*/
                    .add(116, "111-999")/*.add(116, "1-999")*/
            ));

    public static final Breed DEXTER = new Breed(BLACK_ANGUS, new Breed.Properties().setName("Dexter")
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(10, "2").add(26, "10%1,2","2")));

    public static final Breed SPANISH = new Breed(new Breed.Properties().setData("spanish", Biomes.FOREST, Breed.Rarity.ORDINARY)
            .setGeneSketch(new GeneSketch(), new GeneSketch()
                    .add(84, "1")
                    .add(86, "1")
                    .add(90, "1-2")
                    .add(92,"1988")/*.add(92,"1-9999")*/  //170-199, 1091, 2091,
                    .add(94, "244349")/*.add(94, "1-999999")*/
                    .add(96,"1666")/*.add(96,"1-9999")*/
                    .add(98,"206")/*.add(98,"1-999")*/
                    .add(100, "158")/*.add(100, "1-999")*/
                    .add(102, "158")/*.add(102, "1-999")*/
                    .add(104, "165")/*.add(104, "1-999")*/
                    .add(106, "177")/*.add(106, "1-999")*/
                    .add(108, "138")/*.add(108, "1-999")*/
                    .add(110, "144")/*.add(110, "1-999")*/
                    .add(112, "195")/*.add(112, "1-999")*/
                    .add(114, "640-455")/*.add(114, "1-999")*/
                    .add(116, "190")/*.add(116, "1-999")*/
                    .add(122, "1")
            ));

    public static final Breed MURREY_GREY = new Breed(new Breed.Properties().setData("grey", Biomes.SAVANNA, Breed.Rarity.ORDINARY)
            .setGeneSketch(new GeneSketch(), new GeneSketch()
                    .add(0, "1")
                    .add(2, "1|2|3,1|2|3")
            ));

    public static final Breed GLOUCESTER = new Breed(new Breed.Properties().setData("gloucester", Biomes.PLAINS, Breed.Rarity.EXOTIC)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(0, "4","1","5","2","1").add(12, "2","2","2","4","2","3","1","2").add(38, "1").add(44, "1").add(54, "2").add(70, "1","1","2").add(80, "4","1").add(88, "1","1")
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
                    .add(130, "1","1","1","1","1","1","1","1","1","1")
                    .add(150, "2","2","2","2","2","2","2","2","2","2")
            ));

    public static final Breed HUNGARIAN_GREY = new Breed(new Breed.Properties().setData("hungariangrey", Biomes.MOUNTAINS, Breed.Rarity.EXOTIC)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(0, "2","1","3").add(12, "2","2","3","4","2","_","2","2").add(38, "1").add(44, "1").add(54, "2").add(70, "1","1","2").add(80, "4","1").add(88, "1","1")
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
                    .add(130, "1","1","1","1","1","1","1","1|2","1|2","1|2")
                    .add(150, "2","2","2","2","2","2","2","1|2","1|2","1|2")
                    .add(128, "2")
            ));

    public static final Breed ANKOL = new Breed(new Breed.Properties().setData("ankol", Biomes.SAVANNA, Breed.Rarity.COMMON)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(0, "10%1","2|3").add(12, "2","2","2|3|4","_","2","_","25%2","1").add(38, "1").add(44, "1").add(48, "1").add(54, "2").add(70, "1","1","2").add(80, "2","1","1","2","2","2")
                    .add(92,"9999")
                    .add(94, "56529-677711")
                    .add(96,"39-94")
                    .add(98,"84-600")
                    .add(100, "248-690")
                    .add(102, "690-999")
                    .add(104, "688-999")
                    .add(106, "888|999")
                    .add(108, "80-644")
                    .add(110, "212-986")
                    .add(112, "123-762")
                    .add(114, "596-982")
                    .add(116, "501-957")
                    .add(122, "1")
            ));

    public static final Breed BROWN_SWISS = new Breed(new Breed.Properties().setData("brownswiss", Biomes.DARK_FOREST, Breed.Rarity.COMMON)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(0, "2","2","10%2","1","2","1","1").add(14, "2","3","4","2").add(24,"2").add(30, "15","10","3","1","1","4","1-3").add(48, "2","1","1","1","1|2","1|2","1|2","10","1","5","5","1","1","2","2","2").add(62, "10")
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
                    .add(130, "2","2","2","2","2","2","2","1|2","1|2","1|2")
                    .add(150, "1","1","1","1","1","1","1","1|2","1|2","1|2")
                    .add(128,"3")
            ));

    public static final Breed HIGHLAND = new Breed(new Breed.Properties().setData("highlandcoo", Biomes.SNOWY_TUNDRA, Breed.Rarity.ORDINARY)
            .setGeneSketch(new GeneSketch(), new GeneSketch()
                    .add(0, "10%1|2","3")
                    .add(2, "12%1|2", "1")
                    .add(4, "5%2","4")
                    .add(12, "2")
                    .add(14, "2","3","_","2")
                    .add(24, "1")
                    .add(48, "2","2","2")
                    .add(86, "1", "1", "1")
                    .add(92,"1988")
                    .add(94, "111111")
                    .add(96,"1666")
                    .add(98,"1-999")
                    .add(100, "1-999")
                    .add(102, "1-999")
                    .add(104, "1-999")
                    .add(106, "1-999")
                    .add(108, "1-999")
                    .add(110, "1-999")
                    .add(112, "1-999")
                    .add(114, "1-999")
                    .add(116, "1-999")
                    .add(120, "2","5")
//                    .add(130, "2","2","2","2","2","2","2","2","2","2","2","2","2","2","2","2","2","2","2","2")
                    .add(170, "2","2","2","2","2","2","2","1","1","1","1","1","1","1","2","2","2","2","2","2","2","2","2","2","2","2","1","1","1","1","1","1","1","1","1","1","1","1")
                    .add(250,"1","1")
            ));

    public static final Breed TEST = new Breed(new Breed.Properties().setData("test", Biomes.THE_VOID, Breed.Rarity.ORDINARY)
            .setGeneSketch(new GeneSketch(), new GeneSketch()
                            .add(48, "2","2","2")
                            .add(86, "1", "1", "1")
                            .add(92,"1-9999")
                            .add(94, "1-999999")
                            .add(96,"1-999")
                            .add(98,"1-999")
                            .add(100, "1-999")
                            .add(102, "1-999")
                            .add(104, "1-999")
                            .add(106, "1-999")
                            .add(108, "1-999")
                            .add(110, "1-999")
                            .add(112, "1-999")
                            .add(114, "1-999")
                            .add(116, "1-999")
            ));

    //SPANISH HORNS 3,1,1,1,1,1,2,1988,1988,244349,244349,1666,1666,206,206,158,158,158,158,165,165,177,177,138,138,144,144,195,195,622,674,190,190,1,1,1,1,1,1
    //SPANISH HORNS 1,1+2,2,1,1,2,2,2,2,1,1,1,1,2,2,2,2,4,4,4,4,2,2,2,2,2,2,2,2,1,1,1,1,1,1,1,1,3,3,1,1,4,4,2,2,1,2,2,2,2,2,1,1,1,1,2,2,1,1,2,2,2,2,2,2,2,2,2,2,2,2,2,2,1,1,2,2,2,2,1,1,1,1,2,1,3,3,1,1,1,1,1,2,1988,1988,244349,244349,1666,1666,206,206,158,158,158,158,165,165,177,177,138,138,144,144,195,195,622,674,190,190,1,1,1,1,1,1
    //SPANISH HORNS 1,1+2,2,1,1,2,2,2,2,1,1,1,1,2,2,2,2,4,4,4,4,2,2,2,2,2,2,2,2,1,1,1,1,1,1,1,1,3,3,1,1,4,4,1,2,2,1,2,2,2,2,1,2,1,1,2,2,1,2,2,2,2,2,5,2,2,2,2,2,2,2,2,2,1,1,2,2,2,2,1,1,1,1,1,2,3,2,1,1,1,1,1,2,1988,1988,244349,244349,1666,1666,206,206,158,158,158,158,165,165,177,177,138,138,144,144,195,195,622,674,190,190,1,1,1,1,1,1

}
