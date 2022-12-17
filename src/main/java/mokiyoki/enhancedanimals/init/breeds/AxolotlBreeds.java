package mokiyoki.enhancedanimals.init.breeds;

import mokiyoki.enhancedanimals.util.Breed;
import mokiyoki.enhancedanimals.util.GeneSketch;
import net.minecraft.world.level.biome.Biomes;

public class AxolotlBreeds {
    public static final Breed RIBBON = new Breed(new Breed.Properties().setData("ribbon", Biomes.LUSH_CAVES, Breed.Rarity.EXOTIC)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(26, "2").add(32, "2")));
    public static final Breed DARK_EYES = new Breed(new Breed.Properties().setData("darkeyes", Biomes.LUSH_CAVES, Breed.Rarity.UNCOMMON)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(20, "2")));
    public static final Breed PIGMENTED_EYES = new Breed(new Breed.Properties().setData("pigmentedeyes", Biomes.LUSH_CAVES, Breed.Rarity.RARE)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(20, "3")));
    public static final Breed LIGHT_EYES = new Breed(new Breed.Properties().setData("lighteyes", Biomes.LUSH_CAVES, Breed.Rarity.RARE)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(20, "4")));
    public static final Breed PASTEL_EYES = new Breed(new Breed.Properties().setData("pasteleyes", Biomes.LUSH_CAVES, Breed.Rarity.RARE)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(20, "5")));
    public static final Breed GLOW_EYES = new Breed(new Breed.Properties().setData("gloweyes", Biomes.LUSH_CAVES, Breed.Rarity.EXOTIC)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(20, "6")));
    public static final Breed GOLDEN = new Breed(new Breed.Properties().setData("golden", Biomes.LUSH_CAVES, Breed.Rarity.COMMON)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(0, "2").add(2, "1")));
    public static final Breed ALBINO = new Breed(new Breed.Properties().setData("albino", Biomes.LUSH_CAVES, Breed.Rarity.COMMON)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(0, "2").add(8, "2")));
    public static final Breed AXANTHIC = new Breed(new Breed.Properties().setData("axanthic", Biomes.LUSH_CAVES, Breed.Rarity.COMMON)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(2, "2")));
    public static final Breed LEUCISTIC = new Breed(new Breed.Properties().setData("leucistic", Biomes.LUSH_CAVES, Breed.Rarity.COMMON)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(8, "2")));
    public static final Breed COPPER = new Breed(new Breed.Properties().setData("copper", Biomes.LUSH_CAVES, Breed.Rarity.RARE)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(6, "2")));
    public static final Breed GREATGILLS = new Breed(new Breed.Properties().setData("greatgills", Biomes.LUSH_CAVES, Breed.Rarity.EXOTIC)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(34, "2","2")));
    public static final Breed GFP = new Breed(new Breed.Properties().setData("gfp", Biomes.LUSH_CAVES, Breed.Rarity.EXOTIC)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(10, "3")));
    public static final Breed GOLDEN_GFP = new Breed(GOLDEN, new Breed.Properties().setName("gol_gfp").setRarity(Breed.Rarity.EXOTIC)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(10, "3")));
    public static final Breed GOLDEN_GLOW_EYES = new Breed(GOLDEN, new Breed.Properties().setName("goldengloweyes").setRarity(Breed.Rarity.EXOTIC)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(20, "6")));
    public static final Breed ALBINO_GFP = new Breed(ALBINO, new Breed.Properties().setName("albi_gfp").setRarity(Breed.Rarity.EXOTIC)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(10, "3")));
    public static final Breed AXANTHIC_GFP = new Breed(AXANTHIC, new Breed.Properties().setName("axanthicgfp").setRarity(Breed.Rarity.EXOTIC)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(10, "3")));
    public static final Breed GFP_GILLS = new Breed(new Breed.Properties().setData("glowgills", Biomes.LUSH_CAVES, Breed.Rarity.EXOTIC)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(38, "3")));
    public static final Breed GOLDEN_DRAGON = new Breed(GOLDEN_GLOW_EYES, new Breed.Properties().setName("goldendragon").setRarity(Breed.Rarity.EXOTIC)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(34, "2","2").add(38, "3").add(26, "2").add(32, "2")));
    public static final Breed WHITE_BELLY = new Breed(new Breed.Properties().setData("whitebelly", Biomes.LUSH_CAVES, Breed.Rarity.EXOTIC)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(12, "2")));
    public static final Breed PIED = new Breed(new Breed.Properties().setData("pied", Biomes.LUSH_CAVES, Breed.Rarity.EXOTIC)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(12, "3")));
    public static final Breed HIGHWHITE_BELLY = new Breed(WHITE_BELLY, new Breed.Properties().setData("highwhitebelly", Biomes.LUSH_CAVES, Breed.Rarity.EXOTIC)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(14, "10")));
    public static final Breed HIGHWHITE_PIED = new Breed(PIED, new Breed.Properties().setData("highwhitepied", Biomes.LUSH_CAVES, Breed.Rarity.EXOTIC)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(14, "10")));
    public static final Breed KOI_SPOT = new Breed(new Breed.Properties().setData("koispotted", Biomes.LUSH_CAVES, Breed.Rarity.EXOTIC)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(12, "3", "10|8|9|7").add(16, "1|2").add(26, "2").add(34, "2","1")));
    public static final Breed GLOW_KOI = new Breed(KOI_SPOT, new Breed.Properties().setData("glowkoi", Biomes.LUSH_CAVES, Breed.Rarity.EXOTIC)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(8, "2").add(10, "3")));
    public static final Breed BLACK_KOI = new Breed(KOI_SPOT, new Breed.Properties().setData("blackkoi", Biomes.LUSH_CAVES, Breed.Rarity.EXOTIC)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(4, "2")));
    public static final Breed GOLD_KOI = new Breed(KOI_SPOT, new Breed.Properties().setData("goldkoi", Biomes.LUSH_CAVES, Breed.Rarity.EXOTIC)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(0, "2").add(2, "1")));
    public static final Breed SILVER_KOI = new Breed(KOI_SPOT, new Breed.Properties().setData("silverkoi", Biomes.LUSH_CAVES, Breed.Rarity.EXOTIC)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(0, "1").add(2, "2").add(4,"1")));
    public static final Breed COPPER_KOI = new Breed(KOI_SPOT, new Breed.Properties().setData("copperkoi", Biomes.LUSH_CAVES, Breed.Rarity.EXOTIC)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(0, "1").add(2, "1")));
    public static final Breed RED_KOI = new Breed(KOI_SPOT, new Breed.Properties().setData("redkoi", Biomes.LUSH_CAVES, Breed.Rarity.EXOTIC)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(2, "1").add(8, "2").add(10, "2").add(24,"1-12|243-256")));
    public static final Breed ORANGE_KOI = new Breed(KOI_SPOT, new Breed.Properties().setData("orangekoi", Biomes.LUSH_CAVES, Breed.Rarity.EXOTIC)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(2, "1").add(8, "2").add(10, "2").add(24,"13-31")));
    public static final Breed RAINBOW = new Breed(LEUCISTIC, new Breed.Properties().setData("rainbow", Biomes.LUSH_CAVES, Breed.Rarity.EXOTIC)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(10, "2")));
    public static final Breed RED = new Breed(RAINBOW, new Breed.Properties().setData("red", Biomes.LUSH_CAVES, Breed.Rarity.EXOTIC)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(24,"1")));
    public static final Breed ORANGE = new Breed(RAINBOW, new Breed.Properties().setData("orange", Biomes.LUSH_CAVES, Breed.Rarity.EXOTIC)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(24,"1,43")));
    public static final Breed YELLOW = new Breed(RAINBOW, new Breed.Properties().setData("yellow", Biomes.LUSH_CAVES, Breed.Rarity.EXOTIC)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(24,"50%43","1,85")));
    public static final Breed MELONGREEN = new Breed(RAINBOW, new Breed.Properties().setData("melongreen", Biomes.LUSH_CAVES, Breed.Rarity.EXOTIC)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(24,"43,85")));
    public static final Breed GREEN = new Breed(RAINBOW, new Breed.Properties().setData("green", Biomes.LUSH_CAVES, Breed.Rarity.EXOTIC)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(24,"85")));
    public static final Breed VERDEGRIS = new Breed(RAINBOW, new Breed.Properties().setData("verdegris", Biomes.LUSH_CAVES, Breed.Rarity.EXOTIC)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(24,"85,127")));
    public static final Breed CYAN = new Breed(RAINBOW, new Breed.Properties().setData("cyan", Biomes.LUSH_CAVES, Breed.Rarity.EXOTIC)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(24,"50%127","85,169")));
    public static final Breed AZURE = new Breed(RAINBOW, new Breed.Properties().setData("azure", Biomes.LUSH_CAVES, Breed.Rarity.EXOTIC)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(24,"127,169")));
    public static final Breed BLUE = new Breed(RAINBOW, new Breed.Properties().setData("blue", Biomes.LUSH_CAVES, Breed.Rarity.EXOTIC)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(24,"169")));
    public static final Breed VIOLET = new Breed(RAINBOW, new Breed.Properties().setData("violet", Biomes.LUSH_CAVES, Breed.Rarity.EXOTIC)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(24,"169,212")));
    public static final Breed MAGENTA = new Breed(RAINBOW, new Breed.Properties().setData("magentafushia", Biomes.LUSH_CAVES, Breed.Rarity.EXOTIC)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(24,"50%212","169,1")));
    public static final Breed HOT_PINK = new Breed(RAINBOW, new Breed.Properties().setData("hotpink", Biomes.LUSH_CAVES, Breed.Rarity.EXOTIC)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(24,"212,1")));
    public static final Breed LAVENDER = new Breed(RAINBOW, new Breed.Properties().setData("lavender", Biomes.LUSH_CAVES, Breed.Rarity.EXOTIC)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(24,"255,148")));
    public static final Breed WHITE = new Breed(RAINBOW, new Breed.Properties().setData("hetwhite", Biomes.LUSH_CAVES, Breed.Rarity.EXOTIC)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(24,"33%43,169","33%85,212","1,127")));
    public static final Breed MICRO = new Breed(new Breed.Properties().setData("micro", Biomes.LUSH_CAVES, Breed.Rarity.EXOTIC)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(28, "5","10")));
}
