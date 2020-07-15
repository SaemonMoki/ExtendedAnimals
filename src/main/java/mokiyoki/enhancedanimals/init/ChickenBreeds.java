package mokiyoki.enhancedanimals.init;

import javafx.util.Pair;
import mokiyoki.enhancedanimals.util.Breed;
import mokiyoki.enhancedanimals.util.GeneSketch;
import net.minecraft.world.biome.Biomes;

import java.util.ArrayList;

import static mokiyoki.enhancedanimals.util.Breed.createVarientList;

public class ChickenBreeds {
    public static final Breed PLAINSNATIVE = new Breed(new Breed.Properties().setData("wild plains chicken", Biomes.PLAINS, Breed.Rarity.ORDINARY)
    .setGeneSketch(new GeneSketch(),
            new GeneSketch()));

    public static final Breed LEGHORN = new Breed(new Breed.Properties().setData("Leghorn", Biomes.PLAINS, Breed.Rarity.ORDINARY)
        .setVarieties(new Breed.VarientHolder(
                createVarientList(GeneSet.LEGHORNWHITE.get())
        ))
        .setGeneSketch(new GeneSketch().add(4, "4-6").add(8, "2", "2", "4-6").add(18, "1").add(26, "3").add(48, "2","1"),
                       new GeneSketch().add(24, "3,2-3").add(32, "2","3","2","3","2").add(60, "2","1","2","2").add(146, "1").add(152, "9-12","9-12","6-12","4-6","13-24","13-24","5").add(168,"2")));
    public static final Breed ROSECOMBLEGHORN = new Breed(LEGHORN, new Breed.Properties().setRarity(Breed.Rarity.RARE)).editGenes(GeneSet.POINTEDROSE.get());
    public static final Breed WYANDOTTE = new Breed(new Breed.Properties().setData("Wyandotte", Biomes.BIRCH_FOREST, Breed.Rarity.ORDINARY)
            .setVarieties(new Breed.VarientHolder(
                    createVarientList(GeneSet.FLATROSE.get()),
                    createVarientList(GeneSet.GOLD.get(),GeneSet.GOLD.with(GeneSet.MAHOGANY).get()),
                    createVarientList(GeneSet.DOMWHITE.get(),GeneSet.BLUE.get(),GeneSet.LAVENDER.get(),GeneSet.SILVER.with(GeneSet.CHOCOLATE).get(),GeneSet.NONE.get()),
                    createVarientList(GeneSet.LACED.get(), GeneSet.LACED.with(GeneSet.MOTTLED).get(), GeneSet.PENCILED.get())
            ))
            .setGeneSketch(new GeneSketch(new Pair<>(1, "1")), new GeneSketch(new Pair<>(1, "1"))));
    public static final Breed RHODEISLANDRED = new Breed(new Breed.Properties().setData("RhodeIslandRed", Biomes.FOREST, Breed.Rarity.ORDINARY)
            .setGeneSketch(new GeneSketch(new Pair<>(1, "1")), new GeneSketch(new Pair<>(1, "1"))));
    public static final Breed PLYMOUTHROCK = new Breed(new Breed.Properties().setData("PlymouthRock", Biomes.TAIGA, Breed.Rarity.ORDINARY)
            .setGeneSketch(new GeneSketch(new Pair<>(1, "1")), new GeneSketch(new Pair<>(1, "1"))));
    public static final Breed ORPINGTON = new Breed(new Breed.Properties().setData("Orpington", Biomes.SWAMP, Breed.Rarity.ORDINARY)
//            .setVarieties("Chocolate,Blue,Buff,White,Lavender,Platinum,GoldLaced,Black")
            .setGeneSketch(new GeneSketch(new Pair<>(1, "1")), new GeneSketch(new Pair<>(1, "1"))));
    public static final Breed BELGIUMBANTAM = new Breed(new Breed.Properties().setData("Belgian", Biomes.SNOWY_TAIGA_MOUNTAINS, Breed.Rarity.UNCOMMON)
            .setVarieties(new Breed.VarientHolder(
                    createVarientList(GeneSet.SINGLE.get(), GeneSet.FLATROSE.get(), GeneSet.TRIFIDROSE.get()),
                    createVarientList(GeneSet.GOLD.with(GeneSet.LACED).with(GeneSet.MOTTLED).get())))
            .setGeneSketch(new GeneSketch().add(14, "2","2"), new GeneSketch(new Pair<>(1, "1"))));



    /**
     * animal species specific stuff that needs to be in a chicken breed class I think
     */

    public enum GeneSet {
        NONE("empty"),
        GOLD(new GeneSketch().add(0, "1"), new GeneSketch().add(170, "1")),
        SILVER(new GeneSketch().add(0, "2"), new GeneSketch().add(170, "2")),
        SINGLE("empty", new GeneSketch().add(26, "3").add(48, "2","1")),
        POINTEDROSE("empty", new GeneSketch().add(26, "1").add(48, "2", "1")),
        FLATROSE("empty", new GeneSketch().add(26, "2").add(48, "2","1")),
        PEA("empty", new GeneSketch().add(26, "3").add(48, "1", "1")),
        TRIFIDROSE("empty", new GeneSketch().add(26, "1").add(48, "2", "1").add(54, "1")),
        SPANGLED("empty", new GeneSketch().add(4, "4", "1", "2", "1").add(78, "1")),
        LACED("empty", new GeneSketch().add(4, "4", "1", "1", "1").add(78, "2")),
        DOUBLELACED("empty", new GeneSketch().add(4, "4", "1", "2", "1").add(78, "2")),
        PENCILED("empty", new GeneSketch().add(4, "4", "1", "2", "2").add(78, "2")),
        MOTTLED("empty", new GeneSketch().add(2, "2")),
        LEGHORNWHITE(new GeneSketch().add(6, "2"), new GeneSketch().add(4, "5").add(18, "1")),
        DOMWHITE("empty", new GeneSketch().add(18, "1")),
        CHOCOLATE(new GeneSketch().add(2, "2"), "empty"),
        BLUE("empty", new GeneSketch().add(20, "1-2")),
        LAVENDER("empty", new GeneSketch().add(16, "2")),
        EASTEREGGER(new GeneSketch().add(10, "1"), new GeneSketch().add(42, "1-2,1", "1|3", "1|3")),
        MAHOGANY("empty", new GeneSketch().add(14, "1").add(170, "1"))
        ;

        public final GeneSketch sexlinked;
        public final GeneSketch autosomal;

        GeneSet(GeneSketch sexlinked, GeneSketch autosomal) {
            this.sexlinked = sexlinked;
            this.autosomal = autosomal;
        }

        GeneSet(GeneSketch sexlinked, String empty) {
            this.sexlinked = sexlinked;
            this.autosomal = new GeneSketch(new Pair<>(-1, empty));
        }

        GeneSet(String empty, GeneSketch autosomal) {
            this.sexlinked = new GeneSketch(new Pair<>(-1, empty));
            this.autosomal = autosomal;
        }

        GeneSet(String empty) {
            this.sexlinked = new GeneSketch(new Pair<>(-1, empty));
            this.autosomal = new GeneSketch(new Pair<>(-1, empty));
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
