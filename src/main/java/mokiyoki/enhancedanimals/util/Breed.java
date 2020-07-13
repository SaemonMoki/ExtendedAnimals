package mokiyoki.enhancedanimals.util;

import javafx.util.Pair;
import net.minecraft.world.biome.Biome;

import java.util.ArrayList;
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
    private final Genes genes;
    private final String[] varieties;

    public Breed(Breed.Properties properties) {
        this.breedName = properties.breedName;
        this.temperature = properties.temperature;
        this.rain = properties.rain;
        this.rarity = properties.rarity;
        this.genes = properties.genes;
        this.varieties = properties.varieties;
    }

    public final String getBreedName() {
        return this.breedName;
    }

    public static class Properties {
        private String breedName;
        private Float temperature;
        private Float rain;
        private Rarity rarity;
        private Genes genes;
        private String[] varieties;

        public Breed.Properties setData(String name, Biome biome, Breed.Rarity rarity) {
            this.breedName = name;
            this.temperature = biome.getDefaultTemperature();
            this.rain = biome.getDownfall();
            this.rarity = rarity;
            return this;
        }

        public Breed.Properties setGenes(Genes geneBluePrint) {
            if (geneBluePrint == null) {
                this.genes = new Genes().setGenes(new String[]{}, new String[]{});
            } else {
                this.genes = geneBluePrint;
            }
            return this;
        }

//        public Breed.Properties setSexlinkedGenes(String geneBluePrint) {
//            if (geneBluePrint == null) {
//                this.sexlinked = "";
//            } else {
//                this.sexlinked = geneBluePrint;
//            }
//            return this;
//        }
//
//        public Breed.Properties setAutosomalGenes(String geneBluePrint) {
//            if (geneBluePrint == null) {
//                this.autosomal = "";
//            } else {
//                this.autosomal = geneBluePrint;
//            }
//            return this;
//        }

        public Breed.Properties setVarieties(VarientHolder varieties) {

            return this;
        }

    }

    public static class Genes {
        private String[] sexlinked;
        private String[] autosomal;

        public Genes setGenes(String[] sexlinked, String[] autosomal) {
            this.sexlinked = sexlinked;
            this.autosomal = autosomal;
            return this;
        }

        public Genes setGenes(String[] autosomal) {
            this.sexlinked = new String[]{""};
            this.autosomal = autosomal;
            return this;
        }

        public String[] getSexlinkedGenes() {
            return sexlinked;
        }

        public String[] getAutosomalGenes() {
            return autosomal;
        }

        public void overlayGenes(String[] sexlinked, String[] autosomal) {
            for (int i = 0; sexlinked.length >= i; i++) {
                if (sexlinked[i] != "") {
                    this.sexlinked[i] = sexlinked[i];
                }
            }
            for (int i = 0; autosomal.length >= i; i++) {
                if (autosomal[i] != "") {
                    this.autosomal[i] = autosomal[i];
                }
            }
        }

        public int[][] getGeneArray() {
            int[] sexlinkedGenes = new int[]{getSexlinkedGenes().length};
            int[] autosomalGenes = new int[]{getAutosomalGenes().length};
            int index = 0;
            for(String sexlinkedgene : getSexlinkedGenes()) {
                String allele_A;
                String allele_B;
                if (sexlinkedgene.contains(",")) {
                    String[] splitGene = sexlinkedgene.split(",");
                    if (ThreadLocalRandom.current().nextBoolean()) {
                        allele_A = splitGene[0];
                        allele_B = splitGene[1];
                    } else {
                        allele_A = splitGene[1];
                        allele_B = splitGene[0];
                    }
                } else {
                    allele_A = sexlinkedgene;
                    allele_B = sexlinkedgene;
                }

                sexlinkedGenes[index] = getAllele(allele_A);
                index++;
                sexlinkedGenes[index] = getAllele(allele_B);
                index++;
            }

            index = 0;
            for(String autosomalgene : getAutosomalGenes()) {
                String allele_A;
                String allele_B;
                if (autosomalgene.contains(",")) {
                    String[] splitGene = autosomalgene.split(",");
                    if (ThreadLocalRandom.current().nextBoolean()) {
                        allele_A = splitGene[0];
                        allele_B = splitGene[1];
                    } else {
                        allele_A = splitGene[1];
                        allele_B = splitGene[0];
                    }
                } else {
                    allele_A = autosomalgene;
                    allele_B = autosomalgene;
                }

                sexlinkedGenes[index] = getAllele(allele_A);
                index++;
                sexlinkedGenes[index] = getAllele(allele_B);
                index++;
            }

            return new int[][]{sexlinkedGenes,autosomalGenes};
        }
    }

    private static int getAllele(String alleleData) {
        if (alleleData.contains("-")) {
            String[] splitGene = alleleData.split("-");
            return ThreadLocalRandom.current().nextInt(Integer.valueOf(splitGene[0]), (Integer.valueOf(splitGene[1])));
        } else if (alleleData.contains("|")) {
            String[] splitGene = alleleData.split("\\|");
            return Integer.valueOf(splitGene[ThreadLocalRandom.current().nextInt((splitGene.length))]);
        } else {
            return Integer.valueOf(alleleData);
        }
    }

    public enum Rarity {
        ORDINARY,
        COMMON,
        UNCOMMON,
        RARE,
        EXOTIC
    }

    /**
     * animal species specific stuff that needs to be in a chicken breed class I think
     */

    public enum PatternColour {
        NONE(new String[]{}, new String[]{}),
        DOM_WHITE(new String[]{}, new String[]{}),
        BLACK(new String[]{}, new String[]{}),
        CHOCOLATE(new String[]{}, new String[]{}),
        BLUE(new String[]{}, new String[]{});

        public final String[] sexlinked;
        public final String[] autosomal;
        
        private PatternColour(String[] sexlinked, String[] autosomal) {
            this.sexlinked = sexlinked;
            this.autosomal = autosomal;
            
        }

        public Pair asPair() {
            return new Pair(this.sexlinked, this.autosomal);
        }

        public Genes getGenes(){
            return new Genes().setGenes(sexlinked, autosomal);
        }
    }

    public enum Pattern {
        NONE(new String[]{}, new String[]{}),
        LACED(new String[]{}, new String[]{}),
        DOUBLELACED(new String[]{}, new String[]{}),
        DUCKWING(new String[]{}, new String[]{}),
        SPANGLED(new String[]{}, new String[]{});

        public final String[] sexlinked;
        public final String[] autosomal;

        private Pattern(String[] sexlinked, String[] autosomal) {
            this.sexlinked = sexlinked;
            this.autosomal = autosomal;
        }

        public Pair asPair() {
            return new Pair(this.sexlinked, this.autosomal);
        }

        public Genes getGenes(){
            return new Genes().setGenes(sexlinked, autosomal);
        }
    }

    public enum BodyVarients {
        NONE(new String[]{}, new String[]{}),
        ROSE(new String[]{}, new String[]{}),
        SINGLE(new String[]{}, new String[]{}),
        FOOTFEATHERS(new String[]{}, new String[]{}),
        BEARDED(new String[]{}, new String[]{});

        public final String[] sexlinked;
        public final String[] autosomal;

        private BodyVarients(String[] sexlinked, String[] autosomal) {
            this.sexlinked = sexlinked;
            this.autosomal = autosomal;
        }

        public Pair asPair() {
            return new Pair(this.sexlinked, this.autosomal);
        }

        public Genes getGenes(){
            return new Genes().setGenes(sexlinked, autosomal);
        }
    }

    public static class VarientHolder {
        /*
        Pair<String[], String[]> is a single varient. ie: ROSE(new String[]{}, new String[]{})
        List<Pair<String[], String[]>> is ALL of the chosen varients from a single group. ie: Rose and Single from BodyVarients
        List<List<Pair<String[], String[]>>> is all of the different varients in total, ie: BodyVarients, Pattern, ect
         */
        List<List<Pair<String[], String[]>>> varientsToGoThrough = new ArrayList<>();

//        PatternColour[] patternColours;
//        GroundColour[] groundColours;
//        Pattern[] patterns;
//        BodyVarients[] bodyVarients;

        public VarientHolder(List<Pair<String[], String[]>> ... varientsToGoThrough ) {

        }
//
//        private VarientHolder(PatternColour[] patternColours, GroundColour[] groundColours, Pattern[] patterns, BodyVarients[] bodyVarients) {
//            this.patternColours = patternColours;
//            this.groundColours = groundColours;
//            this.patterns = patterns;
//            this.bodyVarients = bodyVarients;
//        }
//
//        private VarientHolder(PatternColour[] patternColours) {
//            this.patternColours = patternColours;
//            this.groundColours = new GroundColour[]{GroundColour.NONE};
//            this.patterns = new Pattern[]{Pattern.NONE};
//            this.bodyVarients = new BodyVarients[]{BodyVarients.NONE};
//        }
//
//        private VarientHolder(GroundColour[] groundColours) {
//            this.patternColours = new PatternColour[]{PatternColour.NONE};
//            this.groundColours = new GroundColour[]{GroundColour.NONE};
//            this.patterns = new Pattern[]{Pattern.NONE};
//            this.bodyVarients = new BodyVarients[]{BodyVarients.NONE};
//        }
//
//        private VarientHolder(Pattern[] patterns) {
//            this.patternColours = new PatternColour[]{PatternColour.NONE};
//            this.groundColours = new GroundColour[]{GroundColour.NONE};
//            this.patterns = patterns;
//            this.bodyVarients = new BodyVarients[]{BodyVarients.NONE};
//        }
//
//        private VarientHolder(BodyVarients[] bodyVarients) {
//            this.patternColours = new PatternColour[]{PatternColour.NONE};
//            this.groundColours = new GroundColour[]{GroundColour.NONE};
//            this.patterns = new Pattern[]{Pattern.NONE};
//            this.bodyVarients = bodyVarients;
//        }
    }

}
