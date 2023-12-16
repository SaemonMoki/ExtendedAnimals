package mokiyoki.enhancedanimals.init.breeds;

import mokiyoki.enhancedanimals.util.Breed;
import mokiyoki.enhancedanimals.util.GeneSketch;
import net.minecraft.world.level.biome.Biomes;

public class LlamaBreeds {
    public static final Breed SURI = new Breed(new Breed.Properties().setData("Suri", Biomes.WINDSWEPT_HILLS, Breed.Rarity.UNCOMMON).setGeneSketch(new GeneSketch(), new GeneSketch().add(20,"2","3","1","2")));
    public static final Breed BLUEEYEWHITE = new Breed(new Breed.Properties().setData("BlueEyes", Biomes.WINDSWEPT_FOREST, Breed.Rarity.EXOTIC).setGeneSketch(new GeneSketch(), new GeneSketch().add(6, "1").add(10, "2")));
    public static final Breed TUXEDO = new Breed(new Breed.Properties().setData("Tuxedo", Biomes.WINDSWEPT_HILLS, Breed.Rarity.UNCOMMON).setGeneSketch(new GeneSketch(), new GeneSketch().add(6, "2").add(12, "1").add(16, "5%6","7")));
    public static final Breed SANDY = new Breed(new Breed.Properties().setData("SandyCreamFawn", Biomes.DESERT, Breed.Rarity.COMMON).setGeneSketch(new GeneSketch(), new GeneSketch().add(6, "2").add(14, "2|3","1|2")));
    public static final Breed WHITE = new Breed(new Breed.Properties().setData("SnowyWhite", Biomes.WINDSWEPT_FOREST, Breed.Rarity.COMMON).setGeneSketch(new GeneSketch(), new GeneSketch().add(6, "1")));
    public static final Breed BROWN = new Breed(new Breed.Properties().setData("Brown", Biomes.SAVANNA, Breed.Rarity.COMMON).setGeneSketch(new GeneSketch(), new GeneSketch().add(6, "2").add(14, "2","3-6")));
    public static final Breed GREY = new Breed(new Breed.Properties().setData("GreyGray", Biomes.WINDSWEPT_HILLS, Breed.Rarity.COMMON).setGeneSketch(new GeneSketch(), new GeneSketch().add(6, "2").add(8, "1").add(14, "1")));
    public static final Breed WOOLLY = new Breed(new Breed.Properties().setData("WoolyWoolie", Biomes.WINDSWEPT_HILLS, Breed.Rarity.UNCOMMON).setGeneSketch(new GeneSketch(), new GeneSketch().add(20,"1","3","1","2")));
}
