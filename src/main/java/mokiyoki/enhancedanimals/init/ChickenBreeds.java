package mokiyoki.enhancedanimals.init;

import javafx.util.Pair;
import mokiyoki.enhancedanimals.util.Breed;
import mokiyoki.enhancedanimals.util.GeneSketch;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;

import java.util.ArrayList;
import java.util.List;

import static mokiyoki.enhancedanimals.util.Breed.createVarientList;

public final class ChickenBreeds {
    List<Breed> breeds = new ArrayList<>();

    public static final Breed LEGHORN = new Breed(new Breed.Properties().setData("Leghorn", Biomes.PLAINS, Breed.Rarity.ORDINARY)
            .setGeneSketch(new GeneSketch().add(4, "6", "1", "2", "2", "6").add(18, "1"),
                       new GeneSketch().add(20, "1", "1").add(38, "2").add(44, "2-3", "3", "2", "1").add(52, "2","3","2","3","2").add(70, "1", "1").add(80, "2","1","2","2").add(146, "1", "2").add(152, "10|12","10|12","6|8|10|12","4|6","16|18|20|22|24","16|18|20|22|24","5")));

    public static final Breed WHITE_LEGHORN = new Breed(LEGHORN, new Breed.Properties().setData("WhiteLeghorn", Breed.Rarity.ORDINARY)
            .setGeneSketch(GeneSet.LEGHORNWHITE.get()));

    public static final Breed BLACK_LEGHORN = new Breed(LEGHORN, new Breed.Properties().setData("BlackLeghorn", Breed.Rarity.COMMON)
            .setGeneSketch(GeneSet.SOLIDBLACK.get()));

    public static final Breed BLUE_LEGHORN = new Breed(LEGHORN, new Breed.Properties().setData("BlueLeghorn", Breed.Rarity.UNCOMMON)
            .setGeneSketch(GeneSet.SOLIDBLUE.get()));

    public static final Breed GOLD_DUCKWING_LEGHORN = new Breed(LEGHORN, new Breed.Properties().setData("GoldDuckwingLeghorn", Breed.Rarity.COMMON)
            .setGeneSketch(new NewGeneSet(GeneSet.GOLD,GeneSet.DUCKWING).get()));

    public static final Breed SILVER_DUCKWING_LEGHORN = new Breed(LEGHORN, new Breed.Properties().setData("SilverDuckwingLeghorn", Breed.Rarity.COMMON)
            .setGeneSketch(new NewGeneSet(GeneSet.SILVER,GeneSet.DUCKWING).get()));

    public static final Breed BUFF_LEGHORN = new Breed(LEGHORN, new Breed.Properties().setData("BuffLeghorn", Breed.Rarity.RARE)
            .setGeneSketch(GeneSet.BUFF.get()));

    public static final Breed PILE_LEGHORN = new Breed(GOLD_DUCKWING_LEGHORN, new Breed.Properties().setName("PileLeghorn")
            .setGeneSketch(GeneSet.DOMWHITE.get()));

    public static final Breed GOLD_LEGBAR = new Breed(GOLD_DUCKWING_LEGHORN, new Breed.Properties().setData("GoldLegbar", Breed.Rarity.RARE)
            .setGeneSketch(GeneSet.BARRED.get()));

    public static final Breed CRESTED_CREAM_LEGBAR = new Breed(GOLD_LEGBAR, new Breed.Properties().setData("CrestedCreamLegbar", Breed.Rarity.EXOTIC)
            .setGeneSketch(new GeneSketch(),
                    new GeneSketch().add(32, "2").add(54, "1").add(62, "1|3|4")));

    public static final Breed WYANDOTTE = new Breed(new Breed.Properties().setData("Wyandotte", Biomes.BIRCH_FOREST, Breed.Rarity.ORDINARY)
            .setGeneSketch(new NewGeneSet(GeneSet.FLATROSE,GeneSet.YELLOWLEGS).get(),new GeneSketch().add(6, "1").add(10, "1", "1"), new GeneSketch().add(20, "1", "1").add(44, "1", "3", "2", "1", "2", "3", "2", "3", "2", "2", "1-3", "1|2", "2", "1", "1", "3").add(78, "2", "1", "2").add(86, "2", "1", "2", "2").add(146, "2", "1", "1", "11", "7", "9", "2", "9", "7", "1", "2", "1")));

    public static final Breed SILVER_LACED_WYANDOTTE = new Breed(WYANDOTTE, new Breed.Properties().setData("SilverLacedWyandotte", Biomes.BIRCH_FOREST, Breed.Rarity.ORDINARY)
            .setGeneSketch(new NewGeneSet(GeneSet.SILVER,GeneSet.LACED).get()));

    public static final Breed GOLD_LACED_WYANDOTTE = new Breed(WYANDOTTE, new Breed.Properties().setData("GoldLacedWyandotte", Biomes.FOREST, Breed.Rarity.ORDINARY)
            .setGeneSketch(new NewGeneSet(GeneSet.GOLD,GeneSet.LACED).get()));

    public static final Breed WHITE_WYANDOTTE = new Breed(WYANDOTTE, new Breed.Properties().setData("WhiteWyandotte", Biomes.BIRCH_FOREST, Breed.Rarity.COMMON)
            .setGeneSketch(GeneSet.SILVER.get(), new GeneSketch(), new GeneSketch().add(20, "2")));

    public static final Breed BUFF_LACED_WYANDOTTE = new Breed(GOLD_LACED_WYANDOTTE, new Breed.Properties().setData("BuffLacedWyandotte", Biomes.FOREST, Breed.Rarity.UNCOMMON)
            .setGeneSketch(GeneSet.DOMWHITE.get()));

    public static final Breed BLUE_LACED_WYANDOTTE = new Breed(GOLD_LACED_WYANDOTTE, new Breed.Properties().setData("BlueLacedWyandotte", Biomes.FOREST, Breed.Rarity.UNCOMMON)
            .setGeneSketch(new NewGeneSet(GeneSet.BLUE,GeneSet.MAHOGANY).get()));

    public static final Breed GOLD_PENCILED_WYANDOTTE = new Breed(WYANDOTTE, new Breed.Properties().setData("GoldPenciledWyandotte", Biomes.FOREST, Breed.Rarity.COMMON)
            .setGeneSketch(new NewGeneSet(GeneSet.GOLD,GeneSet.PENCILED).get()));

    public static final Breed SILVER_PENCILED_WYANDOTTE = new Breed(WYANDOTTE, new Breed.Properties().setData("SilverPenciledWyandotte", Biomes.BIRCH_FOREST, Breed.Rarity.UNCOMMON)
            .setGeneSketch(new NewGeneSet(GeneSet.SILVER,GeneSet.PENCILED).get()));

    public static final Breed MILLEFLEUR_WYANDOTTE = new Breed(GOLD_LACED_WYANDOTTE, new Breed.Properties().setData("MillefluerWyandotte", Biomes.FLOWER_FOREST, Breed.Rarity.RARE)
            .setGeneSketch(GeneSet.MOTTLED.get()));

    public static final Breed RHODEISLANDRED = new Breed(new Breed.Properties().setData("RhodeIslandRed", Biomes.FOREST, Breed.Rarity.ORDINARY)
            .setGeneSketch(GeneSet.YELLOWLEGS.get(), new GeneSketch().add(0, "1").add(4, "4-6").add(8, "2", "2", "4-6").add(18, "1"),
                    new GeneSketch().add(20, "1", "1", "3", "2", "2", "2", "3", "1").add(38, "2").add(44, "2-3", "3", "2", "1").add(52, "2","3","2","3","2","2","1|2","1|3","1","1","1").add(80, "2","2","2","2").add(98, "1", "1").add(146, "1","1").add(152, "9|11","9|11","7|9|11","4","15|17|19|21|23","15|17|19|21|23","5").add(168,"1","1")));

    public static final Breed PLYMOUTHROCK = new Breed(new Breed.Properties().setData("PlymouthRock", Biomes.TAIGA, Breed.Rarity.ORDINARY)
            .setGeneSketch(new NewGeneSet(GeneSet.SINGLE,GeneSet.YELLOWLEGS).get(), new GeneSketch().add(6, "2", "1", "1", "1", "1"),
                    new GeneSketch().add(20, "1", "1", "5").add(38, "2").add(42, "2").add(44, "2", "3", "2", "1", "2", "3", "2", "3", "2", "2", "1|2", "1-3", "2", "1", "1", "3").add(78, "2", "1", "2").add(86, "2", "1", "2", "2").add(146, "2", "1", "1", "11", "7", "9", "2", "9", "7", "1", "1", "1")));

    public static final Breed ORPINGTON = new Breed(new Breed.Properties().setData("Orpington", Biomes.DESERT, Breed.Rarity.ORDINARY)
            .setGeneSketch(new GeneSketch(6, "1").add(10, "1", "1"), new GeneSketch().add(20, "1", "1").add(44, "1", "3", "2", "1", "2", "3", "2", "3", "2", "2", "1-3", "1|2", "2", "1", "1", "3").add(78, "2", "1", "2").add(86, "2", "1", "2", "2").add(146, "2", "1", "1", "11", "7", "9", "2", "9", "7", "1", "2", "1")));

    public static final Breed BUFF_ORPINGTON = new Breed(ORPINGTON, new Breed.Properties().setData("BuffOrpington", Breed.Rarity.ORDINARY)
            .setGeneSketch(GeneSet.BUFF.get()));

    public static final Breed BLACK_ORPINGTON = new Breed(ORPINGTON, new Breed.Properties().setData("BlackOrpington", Breed.Rarity.ORDINARY)
            .setGeneSketch(GeneSet.SOLIDBLACK.get()));

    public static final Breed BLUE_ORPINGTON = new Breed(ORPINGTON, new Breed.Properties().setData("BlueOrpington", Breed.Rarity.COMMON)
            .setGeneSketch(GeneSet.SOLIDBLUE.get()));

    public static final Breed GOLD_LACED_ORPINGTON = new Breed(ORPINGTON, new Breed.Properties().setData("GoldLacedOrpington", Breed.Rarity.UNCOMMON)
            .setGeneSketch(new NewGeneSet(GeneSet.GOLD,GeneSet.LACED).get()));

    public static final Breed GOLD_PENCILED_ORPINGTON = new Breed(ORPINGTON, new Breed.Properties().setData("GoldPenciledOrpington", Breed.Rarity.UNCOMMON)
            .setGeneSketch(new NewGeneSet(GeneSet.GOLD,GeneSet.PENCILED).get()));

    public static final Breed MOTTLED_ORPINGTON = new Breed(ORPINGTON, new Breed.Properties().setData("MottledOrpington", Breed.Rarity.UNCOMMON)
            .setGeneSketch(new NewGeneSet(GeneSet.SOLIDBLACK,GeneSet.MOTTLED).get()));

    public static final Breed LAVENDER_ORPINGTON = new Breed(ORPINGTON, new Breed.Properties().setData("LavenderOrpington", Breed.Rarity.RARE)
            .setGeneSketch(new NewGeneSet(GeneSet.SOLIDBLACK,GeneSet.LAVENDER).get()));

    public static final Breed CHOCOLATE_ORPINGTON = new Breed(ORPINGTON, new Breed.Properties().setData("ChocolateOrpington", Breed.Rarity.RARE)
            .setGeneSketch(new NewGeneSet(GeneSet.SOLIDBLACK,GeneSet.CHOCOLATE).get()));

    public static final Breed SILVER_CHOCOLATE_LACED_ORPINGTON = new Breed(ORPINGTON, new Breed.Properties().setData("SilverChocolateLacedOrpington", Breed.Rarity.EXOTIC)
            .setGeneSketch(new NewGeneSet(GeneSet.SILVER,GeneSet.LACED,GeneSet.CHOCOLATE).get()));

    public static final Breed BELGIUMBANTAM = new Breed(new Breed.Properties().setData("Belgian", Biomes.SNOWY_TUNDRA, Breed.Rarity.UNCOMMON)
            .setGeneSketch(new GeneSketch().add(14, "2"), new GeneSketch().add(20, "1").add(44, "1").add(52, "2").add(68, "2","1","1","1","3","1","1","2","1","1","2|3").add(94, "1-3", "1-3").add(172, "1", "1", "1", "1", "1", "1", "1")));

    public static final Breed BELGIUM_D_UCCLE = new Breed(BELGIUMBANTAM, new Breed.Properties().setName("BelgiumDUccle")
            .setGeneSketch(new NewGeneSet(GeneSet.FLUFFYLEGS,GeneSet.SINGLE,GeneSet.CRESTLESS,GeneSet.BEARDED).get()));

    public static final Breed MILLEFLEUR_D_UCCLE = new Breed(BELGIUM_D_UCCLE, new Breed.Properties().setName("MillefleurBelgiumDUccle")
            .setGeneSketch(new NewGeneSet(GeneSet.GOLD,GeneSet.SPANGLED,GeneSet.MOTTLED).get()));

    public static final Breed GOLDNECK_D_UCCLE = new Breed(BELGIUM_D_UCCLE, new Breed.Properties().setName("GoldneckBelgiumDUccle")
            .setGeneSketch(new NewGeneSet(GeneSet.GOLD,GeneSet.SPANGLED,GeneSet.DOMWHITE).get()));

    public static final Breed LEMON_MILLEFLEUR_D_UCCLE = new Breed(BELGIUM_D_UCCLE, new Breed.Properties().setName("LemonMillefleurBelgiumDUccle")
            .setGeneSketch(new NewGeneSet(GeneSet.GOLD,GeneSet.SPANGLED,GeneSet.MOTTLED).get(), new GeneSketch(), new GeneSketch(32, "1").add(34, "2")));

    public static final Breed SILVER_MILLEFLEUR_D_UCCLE = new Breed(BELGIUM_D_UCCLE, new Breed.Properties().setName("SilverMillefleurBelgiumDUccle")
            .setGeneSketch(new NewGeneSet(GeneSet.SILVER,GeneSet.SPANGLED,GeneSet.MOTTLED).get()));

    public static final Breed BLACK_D_UCCLE = new Breed(BELGIUM_D_UCCLE, new Breed.Properties().setName("BlackBelgiumDUccle")
            .setGeneSketch(GeneSet.SOLIDBLACK.get()));

    public static final Breed MOTTLED_D_UCCLE = new Breed(BELGIUM_D_UCCLE, new Breed.Properties().setName("MottledBelgiumDUccle")
            .setGeneSketch(new NewGeneSet(GeneSet.SOLIDBLACK,GeneSet.MOTTLED).get()));

    public static final Breed BLUE_MOTTLED_D_UCCLE = new Breed(BELGIUM_D_UCCLE, new Breed.Properties().setName("BlueMottledBelgiumDUccle")
            .setGeneSketch(new NewGeneSet(GeneSet.SOLIDBLUE,GeneSet.MOTTLED).get()));

    public static final Breed LAVENDER_MOTTLED_D_UCCLE = new Breed(BELGIUM_D_UCCLE, new Breed.Properties().setName("LavenderMottledBelgiumDUccle")
            .setGeneSketch(new NewGeneSet(GeneSet.SOLIDBLACK,GeneSet.LAVENDER,GeneSet.MOTTLED).get()));

    public static final Breed PORCELAINE_D_UCCLE = new Breed(BELGIUM_D_UCCLE, new Breed.Properties().setData("PorcelaineBelgiumDUccle", Breed.Rarity.EXOTIC)
            .setGeneSketch(new NewGeneSet(GeneSet.SILVER,GeneSet.SPANGLED,GeneSet.LAVENDER,GeneSet.MOTTLED).get()));

    public static final Breed BELGIUM_D_EVERBERG = new Breed(BELGIUMBANTAM, new Breed.Properties().setName("BelgiumDEverberg")
            .setGeneSketch(new NewGeneSet(GeneSet.FLUFFYLEGS,GeneSet.SINGLE,GeneSet.CRESTLESS,GeneSet.BEARDED,GeneSet.RUMPLESS).get()));

    public static final Breed BELGIUM_DANVERS = new Breed(BELGIUMBANTAM, new Breed.Properties().setName("BelgiumDAnvers")
            .setGeneSketch(new NewGeneSet(GeneSet.FLATROSE,GeneSet.CRESTLESS,GeneSet.BEARDED,GeneSet.CLEANLEGS).get()));

    public static final Breed BELGIUM_DE_WATERMEAL = new Breed(BELGIUMBANTAM, new Breed.Properties().setName("BelgiumDeWatermeal")
            .setGeneSketch(new NewGeneSet(GeneSet.FLATROSE,GeneSet.CRESTED,GeneSet.BEARDED,GeneSet.CLEANLEGS).get()));

    public static final Breed GOLD_QUAIL_WATERMEAL = new Breed(BELGIUM_DE_WATERMEAL, new Breed.Properties().setName("GoldQuailDeWatermeal")
            .setGeneSketch(new NewGeneSet(GeneSet.GOLD, GeneSet.QUAIL).get()));

    public static final Breed SILVER_QUAIL_WATERMEAL = new Breed(BELGIUM_DE_WATERMEAL, new Breed.Properties().setName("SilverQuailDeWatermeal")
            .setGeneSketch(new NewGeneSet(GeneSet.SILVER, GeneSet.QUAIL).get()));


    public static final Breed BELGIUM_DE_BOITSFORT = new Breed(BELGIUMBANTAM, new Breed.Properties().setName("BelgiumDeBoitsfort")
            .setGeneSketch(new NewGeneSet(GeneSet.FLATROSE,GeneSet.CRESTED,GeneSet.BEARDED,GeneSet.CLEANLEGS,GeneSet.RUMPLESS).get()));

    public static final Breed BELGIUM_DE_GRUBBE = new Breed(BELGIUMBANTAM, new Breed.Properties().setName("BelgiumDeGrubbe")
            .setGeneSketch(new NewGeneSet(GeneSet.FLATROSE, GeneSet.CRESTLESS, GeneSet.BEARDED, GeneSet.CLEANLEGS, GeneSet.RUMPLESS).get()));

    public static final Breed SPANISH = new Breed(new Breed.Properties().setData("Spanish", Biomes.SAVANNA, Breed.Rarity.RARE)
            .setGeneSketch(new GeneSketch().add(4, "6", "1", "2", "2", "6").add(18, "1"),
                    new GeneSketch().add(20, "1", "1","5").add(38, "2").add(44, "1", "3", "2", "1").add(52, "2","3","2","3","2").add(70, "1", "1").add(80, "2","1","2","2").add(146, "1", "2").add(152, "10|12","10|12","10|12","4|6","22|24","22|24","5")));

    public static final Breed CUTIEPIE = new Breed(new Breed.Properties().setData("Cutiepie", Biomes.SAVANNA, Breed.Rarity.EXOTIC)
            .setGeneSketch(new GeneSketch().add(0, "2","25%2","1", "6", "1", "1", "1", "6", "1-2", "1-2", "1"),
                    new GeneSketch().add(20, "1", "1","5").add(38, "2").add(42, "1").add(44, "1", "3", "2", "1").add(52, "2","3","1","2","1","1").add(70, "1", "1").add(80, "2","1","1","2").add(102, "2","1","2,1|2").add(146, "2", "1").add(152, "12","12","12","6","24","24","5","2","1","2")));


//    public static final Breed BELGIUMBANTAM = new Breed(new Breed.Properties().setData("Belgian", Biomes.SNOWY_TAIGA_MOUNTAINS, Breed.Rarity.UNCOMMON)
//            .setVarieties(new Breed.VarientHolder(
//                    createVarientList(GeneSet.FLATROSE.with(GeneSet.BEARDED).with(GeneSet.CRESTLESS).with(GeneSet.CLEANLEGS).get(),
//                            GeneSet.FLUFFYLEGS.with(GeneSet.SINGLE).with(GeneSet.CRESTLESS).with(GeneSet.BEARDED).get(),
//                            GeneSet.FLUFFYLEGS.with(GeneSet.SINGLE).with(GeneSet.CRESTLESS).with(GeneSet.BEARDED).with(GeneSet.RUMPLESS).get(),
//                            GeneSet.RUMPLESS.with(GeneSet.CRESTLESS).with(GeneSet.BEARDED).with(GeneSet.FLATROSE).with(GeneSet.CLEANLEGS).get(),
//                            GeneSet.TRIFIDROSE.with(GeneSet.BEARDED).with(GeneSet.CLEANLEGS).get()),
//                    createVarientList(GeneSet.GOLD.with(GeneSet.SPANGLED).with(GeneSet.MOTTLED).get(),
//                            GeneSet.GOLD.with(GeneSet.SPANGLED).with(GeneSet.MOTTLED).with(GeneSet.LAVENDER).get(),
//                            GeneSet.CUCKOO.get(),
//                            GeneSet.SOLIDBLACK.with(GeneSet.MOTTLED).get(),
//                            GeneSet.SOLIDBLACK.with(GeneSet.MOTTLED).with(GeneSet.LAVENDER).get(),
//                            GeneSet.SOLIDBLACK.get(),
//                            GeneSet.SOLIDBLACK.with(GeneSet.BLUE).get(),
//                            GeneSet.SOLIDBLACK.with(GeneSet.LAVENDER).get(),
//                            GeneSet.QUAIL.get(),
//                            GeneSet.QUAIL.with(GeneSet.LAVENDER).get(),
//                            GeneSet.QUAIL.with(GeneSet.BLUE).get()
//                            )))
//            .setGeneSketch(new GeneSketch().add(14, "2"), new GeneSketch().add(20, "1").add(52, "2").add(68, "2","1","1","1","3","1","1","2","1","1","2|3").add(94, "1-3", "1-3").add(172, "1", "1", "1", "1", "1", "1", "1")));

    public enum GeneSet {
        NONE(),
        GOLD(new GeneSketch().add(0, "1"), new GeneSketch().add(170, "1")),
        SILVER(new GeneSketch().add(0, "2"), new GeneSketch().add(170, "2")),
        BARRED(new GeneSketch().add(6, "2"), "empty"),
        SINGLE("empty", new GeneSketch().add(46, "3", "2", "1")),
        POINTEDROSE("empty", new GeneSketch().add(46, "1", "2", "1")),
        FLATROSE("empty", new GeneSketch().add(46, "2", "2", "1")),
        PEA("empty", new GeneSketch().add(46, "3", "1", "1")),
        TRIFIDROSE("empty", new GeneSketch().add(46, "1", "2", "1").add(54, "1")),
        CUCKOO(new GeneSketch().add(12, "2"), new GeneSketch().add(24, "5")),
        SOLIDBLACK("empty", new GeneSketch().add(24, "5")),
        SOLIDBLUE("empty", new GeneSketch().add(24, "5").add(40, "1|2")),
        DUCKWING("empty", new GeneSketch().add(24, "2", "2", "2", "2").add(98, "2", "1")),
        SPANGLED("empty", new GeneSketch().add(24, "4", "1", "2", "1").add(98, "1")),
        LACED("empty", new GeneSketch().add(24, "4", "1", "1", "1").add(98, "2")),
        DOUBLELACED("empty", new GeneSketch().add(24, "4", "1", "2", "1").add(98, "2")),
        PENCILED("empty", new GeneSketch().add(24, "4", "1", "2", "2").add(98, "2")),
        QUAIL("empty", new GeneSketch().add(24, "2", "2", "1", "1").add(98, "2")),
        BUFF(new GeneSketch().add(0, "1"), new GeneSketch().add(24, "3", "2", "1", "2").add(98, "1", "1")),
        BLACKTAIL("empty", new GeneSketch().add(24, "3", "2", "2", "2").add(98, "1", "1")),
        MOTTLED("empty", new GeneSketch().add(22, "2")),
        LEGHORNWHITE(new GeneSketch().add(6, "2"), new GeneSketch().add(24, "5").add(38, "1")),
        DOMWHITE("empty", new GeneSketch().add(38, "1")),
        CHOCOLATE(new GeneSketch().add(2, "2"), "empty"),
        BLUE("empty", new GeneSketch().add(40, "1|2")),
        LAVENDER("empty", new GeneSketch().add(36, "2")),
        EASTEREGGER(new GeneSketch().add(10, "1"), new GeneSketch().add(62, "1|2,1|2", "1|3", "1|3")),
        MAHOGANY("empty", new GeneSketch().add(34, "1").add(170, "1")),
        YELLOWLEGS("empty", new GeneSketch().add(44, "2").add(166, "1")),
        GOLDENLEGS("empty", new GeneSketch().add(44, "3").add(166, "1")),
        RUMPLESS("empty", new GeneSketch().add(72, "1,2")),
        BEARDED("empty", new GeneSketch().add(56, "1")),
        BEARDEDLESS("empty", new GeneSketch().add(56, "2")),
        CRESTED("empty", new GeneSketch().add(54, "1")),
        CRESTLESS("empty", new GeneSketch().add(54, "3")),
        CLEANLEGS("empty", new GeneSketch().add(58, "3", "2")),
        FLUFFYLEGS("empty", new GeneSketch().add(58, "2", "1").add(102, "2"))
        ;

        public final GeneSketch sexlinked;
        public final GeneSketch autosomal;

        GeneSet(GeneSketch sexlinked, GeneSketch autosomal) {
            this.sexlinked = sexlinked;
            this.autosomal = autosomal;
        }

        GeneSet(GeneSketch sexlinked, String empty) {
            this.sexlinked = sexlinked;
            this.autosomal = new GeneSketch();
        }

        GeneSet(String empty, GeneSketch autosomal) {
            this.sexlinked = new GeneSketch();
            this.autosomal = autosomal;
        }

        GeneSet() {
            this.sexlinked = new GeneSketch();
            this.autosomal = new GeneSketch();
        }

        public Pair<GeneSketch, GeneSketch> get(){
            return new Pair<>(this.sexlinked, this.autosomal);
        }

        public GeneSketch getSexlinked() {
            return new GeneSketch(this.sexlinked);
        }

        public GeneSketch getAutosomal() {
            return new GeneSketch(this.autosomal);
        }
    }

    public static class NewGeneSet {
        private GeneSketch sexlinked;
        private GeneSketch autosomal;

        public NewGeneSet(GeneSet firstSet, GeneSet... geneSets) {
            this.sexlinked = new GeneSketch(firstSet.getSexlinked());
            this.autosomal = new GeneSketch(firstSet.getAutosomal());
            for (GeneSet set : geneSets) {
                this.sexlinked.addLayer(set.getSexlinked());
                this.autosomal.addLayer(set.getAutosomal());
            }
        }

        public Pair<GeneSketch, GeneSketch> get() {
            return new Pair<>(this.sexlinked, this.autosomal);
        }
    }
}
