package mokiyoki.enhancedanimals.init;

import javafx.util.Pair;
import mokiyoki.enhancedanimals.util.Breed;
import mokiyoki.enhancedanimals.util.GeneSketch;
import net.minecraft.world.biome.Biomes;

import static mokiyoki.enhancedanimals.util.Breed.createVarientList;

public final class ChickenBreeds {
    public static final Breed LEGHORN = new Breed(new Breed.Properties().setData("Leghorn", Biomes.PLAINS, Breed.Rarity.ORDINARY)
        .setVarieties(new Breed.VarientHolder(
                createVarientList(GeneSet.GOLD.with(GeneSet.DUCKWING).get())
        ))
        .setGeneSketch(new GeneSketch().add(4, "6").add(8, "2", "2", "6").add(18, "1"),
                       new GeneSketch().add(20, "1", "1").add(38, "2").add(44, "2-3", "3", "2", "1").add(52, "2","3","2","3","2").add(70, "1", "1").add(80, "2","1","2","2").add(146, "1", "2").add(152, "10|12","10|12","6|8|10|12","4|6","16|18|20|22|24","16|18|20|22|24","5")));

    public static final Breed ROSECOMBLEGHORN = new Breed(LEGHORN, new Breed.Properties().setName("Rosecomb Leghorn").setRarity(Breed.Rarity.RARE)
            .setGeneSketch(new GeneSketch(), new GeneSketch().add(46, "1", "2", "1")));
    public static final Breed LEGBAR = new Breed(LEGHORN, new Breed.Properties().setName("Legbar").setRarity(Breed.Rarity.RARE)
            .setGeneSketch(new GeneSketch().add(6, "2"), new GeneSketch()));

    public static final Breed WYANDOTTE = new Breed(new Breed.Properties().setData("Wyandotte", Biomes.BIRCH_FOREST, Breed.Rarity.ORDINARY)
            .setVarieties(new Breed.VarientHolder(
                    createVarientList(GeneSet.FLATROSE.get()),
                    createVarientList(GeneSet.GOLD.get(),GeneSet.GOLD.with(GeneSet.MAHOGANY).get()),
                    createVarientList(GeneSet.DOMWHITE.get(),GeneSet.BLUE.get(),GeneSet.LAVENDER.get(),GeneSet.SILVER.with(GeneSet.CHOCOLATE).get(),GeneSet.NONE.get()),
                    createVarientList(GeneSet.LACED.get(), GeneSet.LACED.with(GeneSet.MOTTLED).get(), GeneSet.PENCILED.get())
            ))
            .setGeneSketch(new GeneSketch().add(10, "1", "1"), new GeneSketch().add(20, "1", "1").add(44, "1", "3", "2", "1", "2", "3", "2", "3", "2", "2", "1-3", "1|2", "2", "1", "1", "3").add(78, "2", "1", "2").add(86, "2", "1", "2", "2").add(146, "2", "1", "1", "11", "7", "9", "2", "9", "7", "1", "2", "1")));

    public static final Breed RHODEISLANDRED = new Breed(new Breed.Properties().setData("RhodeIslandRed", Biomes.FOREST, Breed.Rarity.ORDINARY)
            .setGeneSketch(new GeneSketch().add(0, "1").add(4, "4-6").add(8, "2", "2", "4-6").add(18, "1"),
                    new GeneSketch().add(20, "1", "1", "3", "2", "2", "2", "3", "1").add(38, "2").add(44, "2-3", "3", "2", "1").add(52, "2","3","2","3","2","2","1|2","1|3","1","1","1").add(80, "2","2","2","2").add(98, "1", "1").add(146, "1","1").add(152, "9|11","9|11","7|9|11","4","15|17|19|21|23","15|17|19|21|23","5").add(168,"1","1")));

    public static final Breed PLYMOUTHROCK = new Breed(new Breed.Properties().setData("PlymouthRock", Biomes.TAIGA, Breed.Rarity.ORDINARY)
            .setVarieties(new Breed.VarientHolder(
                    createVarientList(GeneSet.SINGLE.get())
            ))
            .setGeneSketch(new GeneSketch().add(6, "2", "1", "1", "1", "1"),
                    new GeneSketch().add(20, "1", "1", "5").add(38, "2").add(42, "2").add(44, "2", "3", "2", "1", "2", "3", "2", "3", "2", "2", "1|2", "1-3", "2", "1", "1", "3").add(78, "2", "1", "2").add(86, "2", "1", "2", "2").add(146, "2", "1", "1", "11", "7", "9", "2", "9", "7", "1", "1", "1")));

    public static final Breed ORPINGTON = new Breed(new Breed.Properties().setData("Orpington", Biomes.SWAMP, Breed.Rarity.ORDINARY)
            .setVarieties(new Breed.VarientHolder(
                    createVarientList(GeneSet.CHOCOLATE.with(GeneSet.SOLIDBLACK).get(),
                            GeneSet.SOLIDBLACK.get(),
                            GeneSet.BUFF.get(),
                            GeneSet.SOLIDBLACK.with(GeneSet.BLUE).get(),
                            GeneSet.CUCKOO.get())
            ))
            .setGeneSketch(new GeneSketch().add(10, "1", "1"), new GeneSketch().add(20, "1", "1").add(44, "1", "3", "2", "1", "2", "3", "2", "3", "2", "2", "1-3", "1|2", "2", "1", "1", "3").add(78, "2", "1", "2").add(86, "2", "1", "2", "2").add(146, "2", "1", "1", "11", "7", "9", "2", "9", "7", "1", "2", "1")));

    public static final Breed BELGIUMBANTAM = new Breed(new Breed.Properties().setData("Belgian", Biomes.SNOWY_TAIGA_MOUNTAINS, Breed.Rarity.UNCOMMON)
            .setVarieties(new Breed.VarientHolder(
                    createVarientList(GeneSet.FLATROSE.with(GeneSet.BEARDED).with(GeneSet.CRESTLESS).with(GeneSet.CLEANLEGS).get(),
                            GeneSet.FLUFFYLEGS.with(GeneSet.SINGLE).with(GeneSet.CRESTLESS).with(GeneSet.BEARDED).get(),
                            GeneSet.FLUFFYLEGS.with(GeneSet.SINGLE).with(GeneSet.CRESTLESS).with(GeneSet.BEARDED).with(GeneSet.RUMPLESS).get(),
                            GeneSet.RUMPLESS.with(GeneSet.CRESTLESS).with(GeneSet.BEARDED).with(GeneSet.FLATROSE).with(GeneSet.CLEANLEGS).get(),
                            GeneSet.TRIFIDROSE.with(GeneSet.BEARDED).with(GeneSet.CLEANLEGS).get()),
                    createVarientList(GeneSet.GOLD.with(GeneSet.SPANGLED).with(GeneSet.MOTTLED).get(),
                            GeneSet.GOLD.with(GeneSet.SPANGLED).with(GeneSet.MOTTLED).with(GeneSet.LAVENDER).get(),
                            GeneSet.CUCKOO.get(),
                            GeneSet.SOLIDBLACK.with(GeneSet.MOTTLED).get(),
                            GeneSet.SOLIDBLACK.with(GeneSet.MOTTLED).with(GeneSet.LAVENDER).get(),
                            GeneSet.SOLIDBLACK.get(),
                            GeneSet.SOLIDBLACK.with(GeneSet.BLUE).get(),
                            GeneSet.SOLIDBLACK.with(GeneSet.LAVENDER).get(),
                            GeneSet.QUAIL.get(),
                            GeneSet.QUAIL.with(GeneSet.LAVENDER).get(),
                            GeneSet.QUAIL.with(GeneSet.BLUE).get()
                            )))
            .setGeneSketch(new GeneSketch().add(14, "2"), new GeneSketch().add(20, "1").add(52, "2").add(68, "2","1","1","1","3","1","1","2","1","1","2|3").add(94, "1-3", "1-3").add(172, "1", "1", "1", "1", "1", "1", "1")));

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
        CHOCOLATE(new GeneSketch().add(22, "2"), "empty"),
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

        public GeneSet with(GeneSet set) {
            Pair<GeneSketch, GeneSketch> geneset = set.get();
            this.sexlinked.addLayer(geneset.getKey());
            this.autosomal.addLayer(geneset.getValue());
            return this;
        }
    }
}
