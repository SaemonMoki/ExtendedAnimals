package mokiyoki.enhancedanimals.init;

import javafx.util.Pair;
import mokiyoki.enhancedanimals.util.Breed;
import mokiyoki.enhancedanimals.util.GeneSketch;
import net.minecraft.world.biome.Biomes;

import java.security.PublicKey;
import java.util.ArrayList;

import static mokiyoki.enhancedanimals.util.Breed.createVarientList;

public final class ChickenBreeds {
    public static final Breed TEST = new Breed(new Breed.Properties()
        .setVarieties(new Breed.VarientHolder(
                createVarientList(GeneSet.LACED.with(GeneSet.MOTTLED).with(GeneSet.GOLD).get(), GeneSet.SPANGLED.with(GeneSet.MOTTLED).with(GeneSet.GOLD).get(), GeneSet.NONE.get()),
                createVarientList(GeneSet.BLUE.get(), GeneSet.CHOCOLATE.get())
        ))
        .setGeneSketch(new GeneSketch(),
                       new GeneSketch())
    );
    public static final Breed ALLJUNGLEMUTATIONS = new Breed(new Breed.Properties().setName("all jungle mutations").setBiome(Biomes.JUNGLE).setRarity(Breed.Rarity.ORDINARY)
            .setGeneSketch(new GeneSketch().add(0, "1|2","1|2","1-6","1|2","1|2","1|2","1|6","1|2","1|2","1|2"),
                    new GeneSketch().add(20, "1-3","1|2","1-5","1|2","1|2","1|2","1-3","1|2","1|2").add(38, "1-2").add(40, "1|2","1|2","1-3","1-3","1|2","1-3","1|2","1-3","1|2","1-3","1|2","1-4","1-3","1-3","1-2","1,1|2","1,1|2","1-3","1-3","1|2","1|2","1|2","1|2","1-3","1-3","1|2","1|2","1-3","1-3","1|2","1|2","1|2")
                            .add(104,"1,1|2","1|2","1")
                            .add(146,"1|2","1|2","1,1|2","1-12","1-12","1-12","1-5","1-12","1-12","1-5","1|2","1|2","1|2","1|2","1|2","1|2","1-3","1-3","1|2","1|2"))
    );
    public static final Breed ALLPLAINSMUTATIONS = new Breed(new Breed.Properties().setName("all mutations").setBiome(Biomes.PLAINS).setRarity(Breed.Rarity.ORDINARY)
            .setGeneSketch(new GeneSketch().add(0, "1|2","1|2","1-6","1|2","1|2","1|2","1|6","1|2","1|2","1|2"),
                    new GeneSketch().add(20, "1-3","1|2","1-5","1|2","1|2","1|2","1-3","1|2","1|2").add(38, "1-2").add(40, "1|2","1|2","1-3","1-3","1|2","1-3","1|2","1-3","1|2","1-3","1|2","1-4","1-3","1-3","1-2","1,1|2","1,1|2","1-3","1-3","1|2","1|2","1|2","1|2","1-3","1-3","1|2","1|2","1-3","1-3","1|2","1|2","1|2")
                            .add(104,"1,1|2","1|2","1")
                            .add(146,"1|2","1|2","1,1|2","1-12","1-12","1-12","1-5","1-12","1-12","1-5","1|2","1|2","1|2","1|2","1|2","1|2","1-3","1-3","1|2","1|2"))
    );
    public static final Breed ALLSWAMPMUTATIONS = new Breed(new Breed.Properties().setName("all plains mutations").setBiome(Biomes.SWAMP).setRarity(Breed.Rarity.ORDINARY)
            .setGeneSketch(new GeneSketch().add(0, "1|2","1|2","1-6","1|2","1|2","1|2","1|6","1|2","1|2","1|2"),
                    new GeneSketch().add(20, "1-3","1|2","1-5","1|2","1|2","1|2","1-3","1|2","1|2").add(38, "1-2").add(40, "1|2","1|2","1-3","1-3","1|2","1-3","1|2","1-3","1|2","1-3","1|2","1-4","1-3","1-3","1-2","1,1|2","1,1|2","1-3","1-3","1|2","1|2","1|2","1|2","1-3","1-3","1|2","1|2","1-3","1-3","1|2","1|2","1|2")
                            .add(104,"1,1|2","1|2","1")
                            .add(146,"1|2","1|2","1,1|2","1-12","1-12","1-12","1-5","1-12","1-12","1-5","1|2","1|2","1|2","1|2","1|2","1|2","1-3","1-3","1|2","1|2"))
    );
    public static final Breed ALLSAVANNAMUTATIONS = new Breed(new Breed.Properties().setName("all swamp mutations").setBiome(Biomes.SAVANNA).setRarity(Breed.Rarity.ORDINARY)
            .setGeneSketch(new GeneSketch().add(0, "1|2","1|2","1-6","1|2","1|2","1|2","1|6","1|2","1|2","1|2"),
                    new GeneSketch().add(20, "1-3","1|2","1-5","1|2","1|2","1|2","1-3","1|2","1|2").add(38, "1-2").add(40, "1|2","1|2","1-3","1-3","1|2","1-3","1|2","1-3","1|2","1-3","1|2","1-4","1-3","1-3","1-2","1,1|2","1,1|2","1-3","1-3","1|2","1|2","1|2","1|2","1-3","1-3","1|2","1|2","1-3","1-3","1|2","1|2","1|2")
                            .add(104,"1,1|2","1|2","1")
                            .add(146,"1|2","1|2","1,1|2","1-12","1-12","1-12","1-5","1-12","1-12","1-5","1|2","1|2","1|2","1|2","1|2","1|2","1-3","1-3","1|2","1|2"))
    );
    public static final Breed ALLSNOWMUTATIONS = new Breed(new Breed.Properties().setName("all snowy mutations").setBiome(Biomes.SNOWY_TUNDRA).setRarity(Breed.Rarity.ORDINARY)
            .setGeneSketch(new GeneSketch().add(0, "1|2","1|2","1-6","1|2","1|2","1|2","1|6","1|2","1|2","1|2"),
                    new GeneSketch().add(20, "1-3","1|2","1-5","1|2","1|2","1|2","1-3","1|2","1|2").add(38, "1-2").add(40, "1|2","1|2","1-3","1-3","1|2","1-3","1|2","1-3","1|2","1-3","1|2","1-4","1-3","1-3","1-2","1,1|2","1,1|2","1-3","1-3","1|2","1|2","1|2","1|2","1-3","1-3","1|2","1|2","1-3","1-3","1|2","1|2","1|2")
                            .add(104,"1,1|2","1|2","1")
                            .add(146,"1|2","1|2","1,1|2","1-12","1-12","1-12","1-5","1-12","1-12","1-5","1|2","1|2","1|2","1|2","1|2","1|2","1-3","1-3","1|2","1|2"))
    );
    public static final Breed ALLMUTATIONS = new Breed(new Breed.Properties().setName("all mutations").setRarity(Breed.Rarity.EXOTIC)
        .setGeneSketch(new GeneSketch().add(0, "1|2","1|2","1-6","1|2","1|2","1|2","1|6","1|2","1|2","1|2"),
                       new GeneSketch().add(20, "1-3","1|2","1-5","1|2","1|2","1|2","1-3","1|2","1|2").add(38, "1-2").add(40, "1|2","1|2","1-3","1-3","1|2","1-3","1|2","1-3","1|2","1-3","1|2","1-4","1-3","1-3","1-2","1,1|2","1,1|2","1-3","1-3","1|2","1|2","1|2","1|2","1-3","1-3","1|2","1|2","1-3","1-3","1|2","1|2","1|2")
                               .add(104,"1,1|2","1|2","1")
                               .add(146,"1|2","1|2","1,1|2","1-12","1-12","1-12","1-5","1-12","1-12","1-5","1|2","1|2","1|2","1|2","1|2","1|2","1-3","1-3","1|2","1|2"))
    );
    public static final Breed JUNGLENATIVE = new Breed(new Breed.Properties().setData("wild jungle chicken", Biomes.JUNGLE, Breed.Rarity.ORDINARY)
        .setGeneSketch(new GeneSketch().add(0, "1","1","5|6","1", "1", "1", "3-6", "1", "1", "1"),
                       new GeneSketch().add(20, "1","1","2","2","2","2","3","2","1","2","1","2","1","3","2","1","2","3","2","3","2","2","3","3","2","1","1","2","2","1","2","2","2","1","1","1","1","1","1","2","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","10","6","5","5","6","4-6","5","2","1","1","1","1","1","1","1","1")));

    public static final Breed PLAINSNATIVE = new Breed(JUNGLENATIVE, new Breed.Properties().setData("wild plains native", Biomes.PLAINS, Breed.Rarity.ORDINARY)
        .setGeneSketch(new GeneSketch().add(4, "1").add(12,"1").add(18, "2"),
                new GeneSketch().add(20, "2").add(44, "2")/*.add(0.2F, 44, "3")*/.add(166, "1")/*.add(0.2F, 72, "1,2")*/
        ));
    public static final Breed PLAINSSUNFLOWERNATIVE = new Breed(PLAINSNATIVE, new Breed.Properties().setData("wild sunflowerplains native", Biomes.SUNFLOWER_PLAINS, Breed.Rarity.UNCOMMON)
        .setGeneSketch(new GeneSketch(),
                       new GeneSketch().add(44, "3"))
    );
    public static final Breed SAVANNANATIVE = new Breed(JUNGLENATIVE, new Breed.Properties().setData("wild savanna native", Biomes.SAVANNA, Breed.Rarity.ORDINARY)
        .setGeneSketch(new GeneSketch()/*.add(0.1F, 6, "1|2")*/,
                       new GeneSketch().add(24, "4","1|2")/*.add(0.25F, 30, "1|2").add(0.25F, 34, "1|2")*/
    ));
    public static final Breed LEGHORN = new Breed(new Breed.Properties().setData("Leghorn", Biomes.PLAINS, Breed.Rarity.ORDINARY)
        .setVarieties(new Breed.VarientHolder(
                createVarientList(GeneSet.LEGHORNWHITE.get())
        ))
        .setGeneSketch(new GeneSketch().add(4, "4-6").add(8, "2", "2", "4-6").add(18, "1"),
                       new GeneSketch().add(24, "5").add(38, "1").add(44, "3,3").add(52, "2","3","2","3","2").add(80, "2","1","2","2").add(146, "1").add(152, "9-12","9-12","6-12","4-6","13-24","13-24","5").add(168,"2")));
    public static final Breed ROSECOMBLEGHORN = new Breed(LEGHORN, new Breed.Properties().setRarity(Breed.Rarity.RARE))/*.editGenes(GeneSet.POINTEDROSE.get())*/;
    public static final Breed WYANDOTTE = new Breed(new Breed.Properties().setData("Wyandotte", Biomes.BIRCH_FOREST, Breed.Rarity.ORDINARY)
            .setVarieties(new Breed.VarientHolder(
                    createVarientList(GeneSet.FLATROSE.get()),
                    createVarientList(GeneSet.GOLD.get(),GeneSet.GOLD.with(GeneSet.MAHOGANY).get()),
                    createVarientList(GeneSet.DOMWHITE.get(),GeneSet.BLUE.get(),GeneSet.LAVENDER.get(),GeneSet.SILVER.with(GeneSet.CHOCOLATE).get(),GeneSet.NONE.get()),
                    createVarientList(GeneSet.LACED.get(), GeneSet.LACED.with(GeneSet.MOTTLED).get(), GeneSet.PENCILED.get())
            ))
            .setGeneSketch(new GeneSketch(), new GeneSketch()));
    public static final Breed RHODEISLANDRED = new Breed(new Breed.Properties().setData("RhodeIslandRed", Biomes.FOREST, Breed.Rarity.ORDINARY)
            .setGeneSketch(new GeneSketch(), new GeneSketch()));
    public static final Breed PLYMOUTHROCK = new Breed(new Breed.Properties().setData("PlymouthRock", Biomes.TAIGA, Breed.Rarity.ORDINARY)
            .setGeneSketch(new GeneSketch(), new GeneSketch()));
    public static final Breed ORPINGTON = new Breed(new Breed.Properties().setData("Orpington", Biomes.SWAMP, Breed.Rarity.ORDINARY)
//            .setVarieties("Chocolate,Blue,Buff,White,Lavender,Platinum,GoldLaced,Black")
            .setGeneSketch(new GeneSketch(), new GeneSketch()));
    public static final Breed BELGIUMBANTAM = new Breed(new Breed.Properties().setData("Belgian", Biomes.SNOWY_TAIGA_MOUNTAINS, Breed.Rarity.UNCOMMON)
            .setVarieties(new Breed.VarientHolder(
                    createVarientList(GeneSet.SINGLE.get(), GeneSet.FLATROSE.get(), GeneSet.TRIFIDROSE.get()),
                    createVarientList(GeneSet.GOLD.with(GeneSet.LACED).with(GeneSet.MOTTLED).get())))
            .setGeneSketch(new GeneSketch().add(14, "2","2"), new GeneSketch()));



    /**
     * animal species specific stuff that needs to be in a chicken breed class I think
     */

    public enum GeneSet {
        NONE(),
        GOLD(new GeneSketch().add(0, "1"), new GeneSketch().add(170, "1")),
        SILVER(new GeneSketch().add(0, "2"), new GeneSketch().add(170, "2")),
        SINGLE("empty", new GeneSketch().add(46, "3").add(68, "2","1")),
        POINTEDROSE("empty", new GeneSketch().add(46, "1").add(68, "2", "1")),
        FLATROSE("empty", new GeneSketch().add(46, "2").add(68, "2","1")),
        PEA("empty", new GeneSketch().add(46, "3").add(68, "1", "1")),
        TRIFIDROSE("empty", new GeneSketch().add(46, "1").add(68, "2", "1").add(74, "1")),
        SPANGLED("empty", new GeneSketch().add(24, "4", "1", "2", "1").add(98, "1")),
        LACED("empty", new GeneSketch().add(24, "4", "1", "1", "1").add(98, "2")),
        DOUBLELACED("empty", new GeneSketch().add(24, "4", "1", "2", "1").add(98, "2")),
        PENCILED("empty", new GeneSketch().add(24, "4", "1", "2", "2").add(98, "2")),
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
        RUMPLESS("empty", new GeneSketch().add(72, "1,2"))
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
