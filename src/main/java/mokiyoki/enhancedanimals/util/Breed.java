package mokiyoki.enhancedanimals.util;

import net.minecraft.world.biome.Biome;

public class Breed {
    /**
     *      EXAMPLE of how it should work in action
     *     breed("Wyandotte", COLD, DRY, COMMON)
     *         .sexlinkedgenes("1-2", "1-2", "1|3|5|6|9", "2-3", "5-6")
     *         breed("Wyandotte").autosomalgenes("1-2", "1-2","1|6|9","1-2","1-2","1-2","1-2","2-3","1-2","1-2","1-2","1-2", "5-6")
     *         breed("Wyandotte").addVariety()
     *
     *
     */

//    public final Map<String, BreedPropertyGetter> properties = Maps.newHashMap();
    private final String breedName;
    private final Float temperature;
    private final Float rain;
    private final Rarity rarity;
    private final String sexlinked;
    private final String autosomal;
    private final String[] varieties;

    public Breed(Breed.Properties properties) {
        this.breedName = properties.breedName;
        this.temperature = properties.temperature;
        this.rain = properties.rain;
        this.rarity = properties.rarity;
        this.sexlinked = properties.sexlinked;
        this.autosomal = properties.autosomal;
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
        private String sexlinked;
        private String autosomal;
        private String[] varieties;

        public Breed.Properties setData(String name, Biome biome, Breed.Rarity rarity) {
            this.breedName = name;
            this.temperature = biome.getDefaultTemperature();
            this.rain = biome.getDownfall();
            this.rarity = rarity;
            return this;
        }

        public Breed.Properties setSexlinkedGenes(String geneBluePrint) {
            if (geneBluePrint == null) {
                this.sexlinked = "";
            } else {
                this.sexlinked = geneBluePrint;
            }
            return this;
        }

        public Breed.Properties setAutosomalGenes(String geneBluePrint) {
            if (geneBluePrint == null) {
                this.autosomal = "";
            } else {
                this.autosomal = geneBluePrint;
            }
            return this;
        }

        public Breed.Properties setVarieties(String varieties) {
            if (varieties == null) {
                this.varieties = new String[]{""};
            } else {
                String[] variants = new String[]{varieties};
                if (varieties.contains(",")) {
                    variants = varieties.split(",");
                }
                this.varieties = variants;
            }
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
}
