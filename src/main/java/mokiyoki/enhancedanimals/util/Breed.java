package mokiyoki.enhancedanimals.util;

import net.minecraft.world.biome.Biome;

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
                this.genes = new Genes().setGenes("", "");
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

        public Breed.Properties setVarieties(PatternColour[] patternColours, GroundColour[] groundColours) {

            return this;
        }

    }

    public static class Genes {
        private String sexlinked;
        private String autosomal;

        public Genes setGenes(String sexlinked, String autosomal) {
            this.sexlinked = sexlinked;
            this.autosomal = autosomal;
            return this;
        }

        public Genes setGenes(String autosomal) {
            this.sexlinked = "";
            this.autosomal = autosomal;
            return this;
        }

        public String getSexlinkedGenes() {
            return sexlinked;
        }

        public String getAutosomalGenes() {
            return autosomal;
        }

//        public Genes overlayGenes(String sexlinked, String autosomal) {
//            if (sexlinked != "") {
//
//            }
//            if (autosomal != "") {
//
//            }
//        }

//        public int[][] getGeneArray() {
//            converts the sexlinked and autosomal string to an int array
//        }
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
        NONE("",""),
        DOM_WHITE("",""),
        BLACK("",""),
        CHOCOLATE("",""),
        BLUE("","");

        public final String sexlinked;
        public final String autosomal;
        
        private PatternColour(String sexlinked, String autosomal) {
            this.sexlinked = sexlinked;
            this.autosomal = autosomal;
            
        }
    }

    public enum GroundColour {
        NONE("",""),
        SILVER("",""),
        MAHOGANY("", ""),
        GOLD("","");

        public final String sexlinked;
        public final String autosomal;

         GroundColour(String sexlinked, String autosomal) {
            this.sexlinked = sexlinked;
            this.autosomal = autosomal;

        }

        public Genes getGenes() {
            return new Genes().setGenes(this.sexlinked, this.autosomal);
        }
    }

    public enum Pattern {
        NONE("",""),
        LACED("",""),
        DOUBLELACED("",""),
        DUCKWING("",""),
        SPANGLED("","");

        public final String sexlinked;
        public final String autosomal;

        private Pattern(String sexlinked, String autosomal) {
            this.sexlinked = sexlinked;
            this.autosomal = autosomal;

        }
    }

    public enum BodyVarients {
        NONE("",""),
        ROSE("",""),
        SINGLE("",""),
        FOOTFEATHERS("",""),
        BEARDED("","");

        public final String sexlinked;
        public final String autosomal;

        private BodyVarients(String sexlinked, String autosomal) {
            this.sexlinked = sexlinked;
            this.autosomal = autosomal;

        }
    }

    public class VarientHolder {
        PatternColour[] patternColours;
        GroundColour[] groundColours;
        Pattern[] patterns;
        BodyVarients[] bodyVarients;

        private VarientHolder(PatternColour[] patternColours, GroundColour[] groundColours, Pattern[] patterns, BodyVarients[] bodyVarients) {
            this.patternColours = patternColours;
            this.groundColours = groundColours;
            this.patterns = patterns;
            this.bodyVarients = bodyVarients;
        }

        private VarientHolder(PatternColour[] patternColours) {
            this.patternColours = patternColours;
            this.groundColours = new GroundColour[]{GroundColour.NONE};
            this.patterns = new Pattern[]{Pattern.NONE};
            this.bodyVarients = new BodyVarients[]{BodyVarients.NONE};
        }

        private VarientHolder(GroundColour[] groundColours) {
            this.patternColours = new PatternColour[]{PatternColour.NONE};
            this.groundColours = new GroundColour[]{GroundColour.NONE};
            this.patterns = new Pattern[]{Pattern.NONE};
            this.bodyVarients = new BodyVarients[]{BodyVarients.NONE};
        }

        private VarientHolder(Pattern[] patterns) {
            this.patternColours = new PatternColour[]{PatternColour.NONE};
            this.groundColours = new GroundColour[]{GroundColour.NONE};
            this.patterns = patterns;
            this.bodyVarients = new BodyVarients[]{BodyVarients.NONE};
        }

        private VarientHolder(BodyVarients[] bodyVarients) {
            this.patternColours = new PatternColour[]{PatternColour.NONE};
            this.groundColours = new GroundColour[]{GroundColour.NONE};
            this.patterns = new Pattern[]{Pattern.NONE};
            this.bodyVarients = bodyVarients;
        }

        private VarientHolder() {
            this.patternColours = new PatternColour[]{PatternColour.NONE};
            this.groundColours = new GroundColour[]{GroundColour.NONE};
            this.patterns = new Pattern[]{Pattern.NONE};
            this.bodyVarients = new BodyVarients[]{BodyVarients.NONE};
        }

    }

}
