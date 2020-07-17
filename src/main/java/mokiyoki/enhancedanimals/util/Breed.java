package mokiyoki.enhancedanimals.util;

import javafx.util.Pair;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Breed {
    /**
     *      EXAMPLE of how it should work in action
     *     breed("WYANDOTTE", COLD, DRY, COMMON)
     *         .sexlinkedgenes("1-2", "1-2", "1|3|5|6|9", "2-3", "5-6")
     *         breed("WYANDOTTE").autosomalgenes("1-2", "1-2","1|6|9","1-2","1-2","1-2","1-2","2-3","1-2","1-2","1-2","1-2", "5-6")
     *         breed("WYANDOTTE").addVariety()
     *
     *
     */

//    public final Map<String, BreedPropertyGetter> properties = Maps.newHashMap();
    private final String breedName;
    private final Float temperature;
    private final Float rain;
    private final Rarity rarity;
    private final GeneSketch sexlinkedGeneSketch;
    private final GeneSketch autosomalGeneSketch;
    private final VarientHolder varieties;

    public Breed(Breed.Properties properties) {
        this.breedName = properties.breedName;
        this.temperature = properties.temperature;
        this.rain = properties.rain;
        this.rarity = properties.rarity;
        this.sexlinkedGeneSketch = properties.sexlinkedGeneSketch;
        this.autosomalGeneSketch = properties.autosomalGeneSketch;
        this.varieties = properties.varieties;
    }

    public Breed(Breed breedbase, Breed.Properties properties) {
        this.breedName = properties.breedName == null ? breedbase.getBreedName() : properties.breedName;
        this.temperature = properties.temperature == null ? breedbase.getNativeTemperature() : properties.temperature;
        this.rain = properties.rain == null ? breedbase.getNativeHumidity() : properties.rain;
        this.rarity = properties.rarity == null ? breedbase.getRarity() : properties.rarity;
        this.sexlinkedGeneSketch = properties.sexlinkedGeneSketch == null ? breedbase.getSexlinkedGeneSketch() : properties.sexlinkedGeneSketch;
        this.autosomalGeneSketch = properties.autosomalGeneSketch == null ? breedbase.getAutosomalGeneSketch() : properties.autosomalGeneSketch;
        this.varieties = properties.varieties == null ? breedbase.getVarieties() : properties.varieties;
    }

    public final String getBreedName() {
        return this.breedName;
    }

    public final float getNativeTemperature() {
        return this.temperature;
    }

    public final float getNativeHumidity() {
        return this.rain;
    }

    public final Breed.Rarity getRarity() {
        return this.rarity;
    }

    public final Pair<GeneSketch, GeneSketch> getGeneSketches() {
        return new Pair<>(this.sexlinkedGeneSketch, this.autosomalGeneSketch);
    }

    public final GeneSketch getSexlinkedGeneSketch() {
        return this.sexlinkedGeneSketch;
    }

    public final GeneSketch getAutosomalGeneSketch() {
        return this.autosomalGeneSketch;
    }

    public final VarientHolder getVarieties() {
        return this.varieties;
    }

    public final Float likelyhood(Biome biome, boolean forTrader) {
        // temperature ranges from -0.5 to 2.0
        //downfall ranges from 0.0 to 0.9
        //5 levels of rarity
        float rarity = 1.0F;

        switch (this.rarity) {
            case COMMON: rarity = 0.8F;
                break;
            case UNCOMMON: rarity = 0.6F;
                break;
            case RARE: rarity = 0.2F;
                break;
            case EXOTIC: rarity = 0.05F;
        }

        if (forTrader) {
            return (rarity + 1.0F)/2.0F;
        }

        float temp = (0.5F + biome.getDefaultTemperature())*0.4F;
        float rain = biome.getDownfall()*1.1111F;

        temp = 1.0F - Math.abs(((0.5F + this.temperature)*0.4F)-temp);
        rain = 1.0F - Math.abs((this.rain*1.1111F) - rain);

        return temp*rain*rarity;
    }

    public final Float likelyhood(Biome biome) {
        return likelyhood(biome, false);
    }

    public final Float likelyhood(boolean forTrader) {
        return likelyhood(Biomes.THE_VOID, true);
    }

    public final Genes generateGenes(Genes genes) {
        Pair<GeneSketch, GeneSketch> sketch = this.varieties.getSketchWithVarients(new Pair<>(this.sexlinkedGeneSketch, this.autosomalGeneSketch));
        return new Genes().setGenes(sketch.getKey().getGeneArray(genes.getSexlinkedGenes()), sketch.getValue().getGeneArray(genes.getAutosomalGenes()));
    }


    /**
     * meant for small tweaks to a breed, should probably attach rarity to varients instead of this
     * this only works on the main gene blueprint, varients override main genes
     */
    public final Breed editGenes(Pair<GeneSketch, GeneSketch> sketch) {
        this.autosomalGeneSketch.addLayer(sketch.getKey());
        this.sexlinkedGeneSketch.addLayer(sketch.getValue());
        return this;
    }

    public final Breed editGenes(Pair<GeneSketch, GeneSketch> sketch, Float rarity) {
        this.autosomalGeneSketch.addLayer(sketch.getKey(), rarity);
        this.sexlinkedGeneSketch.addLayer(sketch.getValue(), rarity);
        return this;
    }

    public final Breed editGenes(Pair<GeneSketch, GeneSketch> sketch, Breed.Rarity rarity) {
        this.autosomalGeneSketch.addLayer(sketch.getKey(), rarity);
        this.sexlinkedGeneSketch.addLayer(sketch.getValue(), rarity);
        return this;
    }

    public static class Properties {
        private String breedName;
        private Float temperature;
        private Float rain;
        private Rarity rarity;
        private GeneSketch sexlinkedGeneSketch;
        private GeneSketch autosomalGeneSketch;
        private VarientHolder varieties;

        public Breed.Properties setData(String name, Biome biome, Breed.Rarity rarity) {
            this.breedName = name;
            this.temperature = biome.getDefaultTemperature();
            this.rain = biome.getDownfall();
            this.rarity = rarity;
            return this;
        }

        public Breed.Properties setName(String name) {
            this.breedName = name;
            return this;
        }

        public Breed.Properties setBiome(Biome biome) {
            this.temperature = biome.getDefaultTemperature();
            this.rain = biome.getDownfall();
            return this;
        }

        public Breed.Properties setRarity(Breed.Rarity rarity) {
            this.rarity = rarity;
            return this;
        }

        public Breed.Properties setGeneSketch(GeneSketch sexlinkedSketch, GeneSketch autosomalSketch) {
            this.sexlinkedGeneSketch = sexlinkedSketch;
            this.autosomalGeneSketch = autosomalSketch;
            return this;
        }

        public Breed.Properties setVarieties(VarientHolder varieties) {
            this.varieties = varieties;
            return this;
        }

    }

    public enum Rarity {
        ORDINARY,
        COMMON,
        UNCOMMON,
        RARE,
        EXOTIC
    }

    public static class VarientHolder {
        /*
        Pair<String[], String[]> is a single varient. ie: ROSE(new String[]{}, new String[]{})
        List<Pair<String[], String[]>> is ALL of the chosen varients from a single group. ie: Rose and Single from BodyVarients
        List<List<Pair<String[], String[]>>> is all of the different varients in total, ie: BodyVarients, Pattern, ect
         */
        List<List<Pair<GeneSketch, GeneSketch>>> varientsToGoThrough = new ArrayList<>();
        public VarientHolder(List<Pair<GeneSketch, GeneSketch>> ... varientsToGoThrough ) {
            this.varientsToGoThrough.addAll(Arrays.asList(varientsToGoThrough));
        }

        public Pair<GeneSketch, GeneSketch> getSketchWithVarients(Pair<GeneSketch, GeneSketch> genesketch) {
            List<Pair<GeneSketch, GeneSketch>> varients = new ArrayList<>();
            for (List<Pair<GeneSketch, GeneSketch>> var : this.varientsToGoThrough) {
                varients.add(var.get(ThreadLocalRandom.current().nextInt(var.size())));
            }

            for (Pair<GeneSketch, GeneSketch> sketch : varients) {
                genesketch.getKey().addLayer(sketch.getKey());
                genesketch.getValue().addLayer(sketch.getValue());
            }

            return genesketch;
        }
    }

    public static List<Pair<GeneSketch, GeneSketch>> createVarientList(Pair<GeneSketch, GeneSketch>... varients) {
        List<Pair<GeneSketch,GeneSketch>> varientList = new ArrayList<>();
        varientList.addAll(Arrays.asList(varients));
        return varientList;
    }

}