package mokiyoki.enhancedanimals.init.breeds;

import mokiyoki.enhancedanimals.util.Breed;
import mokiyoki.enhancedanimals.util.GeneSketch;
import net.minecraft.world.level.biome.Biomes;

public class RabbitBreeds {
    public static final Breed ANGORA = new Breed(new Breed.Properties().setData("Angora", Biomes.MOUNTAINS , Breed.Rarity.ORDINARY)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(26, "2").add(50, "2","3","2")));
    public static final Breed ENGLISH_ANGORA = new Breed(ANGORA, new Breed.Properties().setData("EnglishAngora", Biomes.MOUNTAINS, Breed.Rarity.ORDINARY)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(24, "2")));
    public static final Breed FRENCH_ANGORA = new Breed(ANGORA, new Breed.Properties().setData("FrenchAngora", Biomes.MOUNTAINS, Breed.Rarity.ORDINARY)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(24, "1")));
    public static final Breed LOP = new Breed(new Breed.Properties().setData("LargeLop", Biomes.MOUNTAINS, Breed.Rarity.ORDINARY)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(14, "1").add(24, "1").add(36, "3","2","3","3")));
    public static final Breed MINI_LOP = new Breed(LOP, new Breed.Properties().setData("DwarfLopMiniLop", Biomes.MOUNTAINS, Breed.Rarity.ORDINARY)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(34, "1,2")));
    public static final Breed LIONHEAD = new Breed(new Breed.Properties().setData("LargeLionhead", Biomes.MOUNTAINS, Breed.Rarity.ORDINARY)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(14, "1").add(24, "2")));
    public static final Breed DWARF_LIONHEAD = new Breed(LIONHEAD, new Breed.Properties().setData("DwarfLionheadMiniLionhead", Biomes.MOUNTAINS, Breed.Rarity.ORDINARY)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(14, "1").add(34, "1,2")));
    public static final Breed VIENNA = new Breed(new Breed.Properties().setData("Vienna", Biomes.MOUNTAINS, Breed.Rarity.ORDINARY)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(14, "2")));
    public static final Breed NEWZEALAND_WHITE = new Breed(new Breed.Properties().setData("NewZealandWhite", Biomes.MOUNTAINS, Breed.Rarity.ORDINARY)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(4, "5").add(24, "1")));
    public static final Breed FLEMISH_GIANT = new Breed(new Breed.Properties().setData("Flemishgiant", Biomes.MOUNTAINS, Breed.Rarity.ORDINARY)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(4, "1|2").add(14, "1").add(24, "1").add(46, "5","1")));
    public static final Breed HOTOT = new Breed(new Breed.Properties().setData("Hotot", Biomes.MOUNTAINS, Breed.Rarity.ORDINARY)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(0, "3").add(4, "1").add(8, "2","2","2").add(14, "1").add(24, "1")));
    public static final Breed DUTCH = new Breed(new Breed.Properties().setData("Dutch", Biomes.MOUNTAINS, Breed.Rarity.ORDINARY)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(0, "3").add(4, "1").add(8, "2","1","2").add(14, "1").add(24, "1")));
    public static final Breed HIMALAYAN = new Breed(new Breed.Properties().setData("Himalayan", Biomes.MOUNTAINS, Breed.Rarity.ORDINARY)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(0, "3","_","4","1","2","1","1","1","1","1").add(24, "1")));
    public static final Breed NETHERLAND_DWARF = new Breed(LOP, new Breed.Properties().setData("NetherlandDwarf", Biomes.MOUNTAINS, Breed.Rarity.ORDINARY)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(14, "1").add(34, "1,2","1","1","1","2","1","1|3","3").add(24, "1")));
    public static final Breed RHINELANDER = new Breed(new Breed.Properties().setData("Rhinelander", Biomes.MOUNTAINS, Breed.Rarity.ORDINARY)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(0,"1","1","1","1","3","2","1","1","1","1").add(24,"1","1").add(46, "3","1")));
    public static final Breed HARLEQUIN = new Breed(new Breed.Properties().setData("Harlequin", Biomes.MOUNTAINS, Breed.Rarity.ORDINARY)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(0,"1","1|2","1","1|2","3","1","1","1","1","1").add(24,"1","1")));
}
