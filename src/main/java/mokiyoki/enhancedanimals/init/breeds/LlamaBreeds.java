package mokiyoki.enhancedanimals.init.breeds;

import mokiyoki.enhancedanimals.util.Breed;
import mokiyoki.enhancedanimals.util.GeneSketch;
import net.minecraft.world.level.biome.Biomes;

public class LlamaBreeds {
    public static final Breed SURI = new Breed(new Breed.Properties().setData("Suri", 0.2F, 0.3F, Breed.Rarity.UNCOMMON).setGeneSketch(new GeneSketch(), new GeneSketch().add(20,"2","3","1","2")));
    public static final Breed BLUEEYEWHITE = new Breed(new Breed.Properties().setData("BlueEyes", 0.2F, 0.3F, Breed.Rarity.EXOTIC).setGeneSketch(new GeneSketch(), new GeneSketch().add(6, "1").add(10, "2")));
    public static final Breed TUXEDO = new Breed(new Breed.Properties().setData("Tuxedo", 0.2F, 0.3F, Breed.Rarity.UNCOMMON).setGeneSketch(new GeneSketch(), new GeneSketch().add(6, "2").add(12, "1").add(16, "5%6","7")));
    public static final Breed SANDY = new Breed(new Breed.Properties().setData("SandyCreamFawn", 2.0F, 0.0F, Breed.Rarity.COMMON).setGeneSketch(new GeneSketch(), new GeneSketch().add(6, "2").add(14, "2|3","1|2")));
    public static final Breed WHITE = new Breed(new Breed.Properties().setData("SnowyWhite", 0.2F, 0.3F, Breed.Rarity.COMMON).setGeneSketch(new GeneSketch(), new GeneSketch().add(6, "1")));
    public static final Breed BROWN = new Breed(new Breed.Properties().setData("Brown", 2.0F, 0.0F, Breed.Rarity.COMMON).setGeneSketch(new GeneSketch(), new GeneSketch().add(6, "2").add(14, "2","3-6")));
    public static final Breed GREY = new Breed(new Breed.Properties().setData("GreyGray", 0.2F, 0.3F, Breed.Rarity.COMMON).setGeneSketch(new GeneSketch(), new GeneSketch().add(6, "2").add(8, "1").add(14, "1")));
    public static final Breed WOOLY = new Breed(new Breed.Properties().setData("WoolyWoolie", 0.2F, 0.3F, Breed.Rarity.UNCOMMON).setGeneSketch(new GeneSketch(), new GeneSketch().add(20,"1","3","1","2")));
}
