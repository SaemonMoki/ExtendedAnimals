package mokiyoki.enhancedanimals.init.breeds;

import mokiyoki.enhancedanimals.util.Breed;
import mokiyoki.enhancedanimals.util.GeneSketch;
import net.minecraft.world.level.biome.Biomes;

public class RabbitBreeds {
    public static final Breed ANGORA = new Breed(new Breed.Properties().setData("Angora", 0.2F, 0.3F, Breed.Rarity.ORDINARY)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(26, "2").add(50, "2","3","2")));
    public static final Breed ENGLISH_ANGORA = new Breed(ANGORA, new Breed.Properties().setData("EnglishAngora", 0.2F, 0.3F, Breed.Rarity.ORDINARY)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(24, "2")));
    public static final Breed FRENCH_ANGORA = new Breed(ANGORA, new Breed.Properties().setData("FrenchAngora", 0.2F, 0.3F, Breed.Rarity.ORDINARY)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(24, "1")));
    public static final Breed LOP = new Breed(new Breed.Properties().setData("LargeLop", 0.2F, 0.3F, Breed.Rarity.ORDINARY)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(14, "1").add(24, "1").add(36, "3","2","3","3")));
    public static final Breed MINI_LOP = new Breed(LOP, new Breed.Properties().setData("DwarfLopMiniLop", 0.2F, 0.3F, Breed.Rarity.ORDINARY)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(34, "1,2")));
    public static final Breed LIONHEAD = new Breed(new Breed.Properties().setData("LargeLionhead", 0.2F, 0.3F, Breed.Rarity.ORDINARY)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(14, "1").add(24, "2")));
    public static final Breed DWARF_LIONHEAD = new Breed(LIONHEAD, new Breed.Properties().setData("DwarfLionheadMiniLionhead", 0.2F, 0.3F, Breed.Rarity.ORDINARY)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(14, "1").add(34, "1,2")));
    public static final Breed VIENNA = new Breed(new Breed.Properties().setData("Vienna", 0.2F, 0.3F, Breed.Rarity.ORDINARY)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(14, "2")));
    public static final Breed NEWZEALAND_WHITE = new Breed(new Breed.Properties().setData("NewZealandWhite", 0.2F, 0.3F, Breed.Rarity.ORDINARY)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(4, "5").add(24, "1")));
    public static final Breed FLEMISH_GIANT = new Breed(new Breed.Properties().setData("Flemishgiant", 0.2F, 0.3F, Breed.Rarity.ORDINARY)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(4, "1|2").add(14, "1").add(24, "1").add(46, "5","1")));
    public static final Breed HOTOT = new Breed(new Breed.Properties().setData("Hotot", 0.2F, 0.3F, Breed.Rarity.ORDINARY)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(0, "3").add(4, "1").add(8, "2","2","2").add(14, "1").add(24, "1")));
    public static final Breed DUTCH = new Breed(new Breed.Properties().setData("Dutch", 0.2F, 0.3F, Breed.Rarity.ORDINARY)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(0, "3").add(4, "1").add(8, "2","1","2").add(14, "1").add(24, "1")));
    public static final Breed HIMALAYAN = new Breed(new Breed.Properties().setData("Himalayan", 0.2F, 0.3F, Breed.Rarity.ORDINARY)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(0, "3","_","4","1","2","1","1","1","1","1").add(24, "1")));
    public static final Breed NETHERLAND_DWARF = new Breed(LOP, new Breed.Properties().setData("NetherlandDwarf", 0.2F, 0.3F, Breed.Rarity.ORDINARY)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(14, "1").add(34, "1,2","1","1","1","2","1","1|3","3").add(24, "1")));
    public static final Breed RHINELANDER = new Breed(new Breed.Properties().setData("Rhinelander", 0.2F, 0.3F, Breed.Rarity.ORDINARY)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(0,"1","1","1","1","3","2","1","1","1","1").add(24,"1","1").add(46, "3","1")));
    public static final Breed HARLEQUIN = new Breed(new Breed.Properties().setData("Harlequin", 0.2F, 0.3F, Breed.Rarity.ORDINARY)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(0,"1","1|2","1","1|2","3","1","1","1","1","1").add(24,"1","1")));
}
