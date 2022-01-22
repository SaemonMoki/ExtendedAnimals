package mokiyoki.enhancedanimals.init.breeds;

import mokiyoki.enhancedanimals.util.Breed;
import mokiyoki.enhancedanimals.util.GeneSketch;
import net.minecraft.world.level.biome.Biomes;

public class AxolotlBreeds {
    public static final Breed RIBBON = new Breed(new Breed.Properties().setData("ribbon", Biomes.LUSH_CAVES, Breed.Rarity.RARE)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(26, "2").add(32, "2")));
    public static final Breed DARK_EYES = new Breed(new Breed.Properties().setData("darkeyes", Biomes.LUSH_CAVES, Breed.Rarity.RARE)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(20, "2")));
    public static final Breed PIGMENTED_EYES = new Breed(new Breed.Properties().setData("pigmentedeyes", Biomes.LUSH_CAVES, Breed.Rarity.RARE)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(20, "3")));
    public static final Breed LIGHT_EYES = new Breed(new Breed.Properties().setData("lighteyes", Biomes.LUSH_CAVES, Breed.Rarity.RARE)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(20, "4")));
    public static final Breed PASTEL_EYES = new Breed(new Breed.Properties().setData("pasteleyes", Biomes.LUSH_CAVES, Breed.Rarity.RARE)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(20, "5")));
    public static final Breed GLOW_EYES = new Breed(new Breed.Properties().setData("gloweyes", Biomes.LUSH_CAVES, Breed.Rarity.RARE)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(20, "6")));
    public static final Breed GOLDEN = new Breed(new Breed.Properties().setData("golden", Biomes.LUSH_CAVES, Breed.Rarity.RARE)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(0, "2")));
    public static final Breed ALBINO = new Breed(new Breed.Properties().setData("albino", Biomes.LUSH_CAVES, Breed.Rarity.RARE)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(0, "2").add(8, "2")));
    public static final Breed AXANTHIC = new Breed(new Breed.Properties().setData("axanthic", Biomes.LUSH_CAVES, Breed.Rarity.RARE)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(2, "2")));
    public static final Breed LEUCISTIC = new Breed(new Breed.Properties().setData("leucistic", Biomes.LUSH_CAVES, Breed.Rarity.RARE)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(8, "2")));
    public static final Breed COPPER = new Breed(new Breed.Properties().setData("copper", Biomes.LUSH_CAVES, Breed.Rarity.RARE)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(6, "2")));
    public static final Breed GREATGILLS = new Breed(new Breed.Properties().setData("greatgills", Biomes.LUSH_CAVES, Breed.Rarity.RARE)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(36, "2","2")));
    public static final Breed GFP = new Breed(new Breed.Properties().setData("gfp", Biomes.LUSH_CAVES, Breed.Rarity.RARE)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(10, "3")));
}
