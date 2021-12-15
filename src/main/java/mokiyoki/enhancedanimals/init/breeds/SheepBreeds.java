package mokiyoki.enhancedanimals.init.breeds;

import mokiyoki.enhancedanimals.util.Breed;
import mokiyoki.enhancedanimals.util.GeneSketch;
import net.minecraft.world.level.biome.Biomes;

public class SheepBreeds {
    public static final Breed DORSET = new Breed(new Breed.Properties().setData("Dorset", Biomes.TALL_BIRCH_FOREST, Breed.Rarity.ORDINARY)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(0, "1").add(4, "2").add(20, "5%1","2","5%1","2","5%1","2").add(36, "2").add(36, "2","3","1","1")));
    public static final Breed DORSETHORNED = new Breed(DORSET, new Breed.Properties().setData("DorsetHorned", Biomes.TALL_BIRCH_FOREST, Breed.Rarity.ORDINARY)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(4,"2","2")));
    public static final Breed DORSETPOLLED = new Breed(DORSET, new Breed.Properties().setData("DorsetPolled", Biomes.TALL_BIRCH_FOREST, Breed.Rarity.ORDINARY)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(4,"2","1")));
    public static final Breed JACOB = new Breed(new Breed.Properties().setData("Jacob", Biomes.TALL_BIRCH_FOREST, Breed.Rarity.ORDINARY)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(0, "6").add(6, "2","2").add(20, "1","1","1","5%1","2","1","5%1","2","5%1","2","1","5%1","2","5%1","2").add(36, "1")));
    public static final Breed ICELANDIC = new Breed(new Breed.Properties().setData("Icelandic", Biomes.SNOWY_TUNDRA, Breed.Rarity.ORDINARY)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(0, "1-6","25%2","1").add(6, "2|3","30%2","1").add(20, "5%1","2","5%1","2","5%1","2","5%1","2","5%1","2","5%1","2","5%1","2","5%1","2","5%1","2","5%1","2").add(36, "2").add(38,"3","2","2","3","1")));
    public static final Breed BLACKBELLY = new Breed(new Breed.Properties().setData("BarbadosBlackBelly", Biomes.SAVANNA, Breed.Rarity.ORDINARY)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(0, "3").add(4, "2").add(20, "1%2","1","1","1%2","1","1","1%2","1","1","1%2","1","1","1%2","1","1").add(36, "2")));
    public static final Breed BABYDOLL = new Breed(new Breed.Properties().setData("Babydoll", Biomes.TALL_BIRCH_FOREST, Breed.Rarity.ORDINARY)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(0, "1").add(4, "2","1").add(20, "5%1","2","5%1","2","5%1","2").add(36, "2").add(38,"1","1","1")));
    public static final Breed DORPER = new Breed(new Breed.Properties().setData("Dorper", Biomes.SAVANNA, Breed.Rarity.ORDINARY)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(4, "1","1").add(20, "1%2","1","1","1%2","1","1","1%2","1","1","1%2","1","1","1%2","1","1").add(36, "2","3").add(46,"1").add(54, "1","1","1","8-14").add(66, "6").add(68, "4","1")));
}
