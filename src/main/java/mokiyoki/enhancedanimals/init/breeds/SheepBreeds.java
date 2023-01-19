package mokiyoki.enhancedanimals.init.breeds;

import mokiyoki.enhancedanimals.util.Breed;
import mokiyoki.enhancedanimals.util.GeneSketch;
import net.minecraft.world.level.biome.Biomes;

public class SheepBreeds {
    public static final Breed DORSET = new Breed(new Breed.Properties().setData("Dorset", Biomes.OLD_GROWTH_BIRCH_FOREST, Breed.Rarity.ORDINARY)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(0, "1").add(4, "2").add(20, "5%1","2","5%1","2","5%1","2").add(36, "2").add(36, "2","3","1","1")));
    public static final Breed DORSETHORNED = new Breed(DORSET, new Breed.Properties().setData("DorsetHorned", Biomes.OLD_GROWTH_BIRCH_FOREST, Breed.Rarity.ORDINARY)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(4,"2","2")));
    public static final Breed DORSETPOLLED = new Breed(DORSET, new Breed.Properties().setData("DorsetPolled", Biomes.OLD_GROWTH_BIRCH_FOREST, Breed.Rarity.ORDINARY)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(4,"2","1")));
    public static final Breed JACOB = new Breed(new Breed.Properties().setData("Jacob", Biomes.OLD_GROWTH_BIRCH_FOREST, Breed.Rarity.ORDINARY)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(0, "6").add(6, "2","2").add(20, "1","1","1","5%1","2","1","5%1","2","5%1","2","1","5%1","2","5%1","2").add(36, "1")));
    public static final Breed ICELANDIC = new Breed(new Breed.Properties().setData("Icelandic", Biomes.SNOWY_PLAINS, Breed.Rarity.ORDINARY)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(0, "1-6","25%2","1").add(6, "2|3","30%2","1").add(20, "5%1","2","5%1","2","5%1","2","5%1","2","5%1","2","5%1","2","5%1","2","5%1","2","5%1","2","5%1","2").add(36, "2").add(38,"3","2","2","3","1")));
    public static final Breed BLACKBELLY = new Breed(new Breed.Properties().setData("BarbadosBlackBelly", Biomes.SAVANNA, Breed.Rarity.ORDINARY)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(0, "3").add(4, "2").add(20, "1%2","1","1","1%2","1","1","1%2","1","1","1%2","1","1","1%2","1","1").add(36, "2")));
    public static final Breed BABYDOLL = new Breed(new Breed.Properties().setData("Babydoll", Biomes.OLD_GROWTH_BIRCH_FOREST, Breed.Rarity.ORDINARY)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(0, "1").add(4, "2","1").add(20, "5%1","2","5%1","2","5%1","2").add(36, "2").add(38,"1","1","1")));
    public static final Breed DORPER = new Breed(new Breed.Properties().setData("Dorper", Biomes.SAVANNA, Breed.Rarity.ORDINARY)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(4, "1","1").add(20, "1%2","1","1","1%2","1","1","1%2","1","1","1%2","1","1","1%2","1","1").add(36, "2","3").add(46,"1").add(54, "1","1","1","8-14").add(66, "6").add(68, "4","1")));
    public static final Breed TICKED_DORPER = new Breed(DORPER, new Breed.Properties().setData("tickeddorp", Biomes.DESERT, Breed.Rarity.EXOTIC)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(70, "2")));
    public static final Breed ENGLISH_BLUE = new Breed(new Breed.Properties().setData("englishblue", Biomes.PLAINS, Breed.Rarity.RARE)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(0, "5")));
    public static final Breed GERMAN_BLUE = new Breed(new Breed.Properties().setData("germanblue", Biomes.PLAINS, Breed.Rarity.RARE)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(0, "14")));
    public static final Breed PADDINGTON_BLUE = new Breed(new Breed.Properties().setData("paddingtonblue", Biomes.PLAINS, Breed.Rarity.RARE)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(0, "16")));
    public static final Breed GREY = new Breed(new Breed.Properties().setData("grey", Biomes.PLAINS, Breed.Rarity.RARE)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(0, "2")));
    public static final Breed RECESSIVE_BLACK = new Breed(new Breed.Properties().setData("recessiveblack", Biomes.PLAINS, Breed.Rarity.RARE)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(0, "6")));
    public static final Breed MOORIT = new Breed(new Breed.Properties().setData("moorit", Biomes.PLAINS, Breed.Rarity.RARE)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(2, "2")));
    public static final Breed RED = new Breed(new Breed.Properties().setData("red", Biomes.PLAINS, Breed.Rarity.RARE)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(4, "3")));

    public static final Breed RANDOMRUFOUS = new Breed(new Breed.Properties().setData("randomrufous", Biomes.PLAINS, Breed.Rarity.RARE)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(74, "1|2,1|2","1|2,1|2","1|2,1|2","1|2,1|2","1|2,1|2","1|2,1|2","1|2,1|2","1|2,1|2")));
}
