package mokiyoki.enhancedanimals.init;

public class Breed {
    /**
     *      EXAMPLE of how it should work in action
     *     breed("Wyandotte", COLD, DRY, COMMON)
     *         breed("Wyandotte").sexlinkedgenes("1-2", "1-2", "1|3|5|6|9", "2-3", "5-6")
     *         breed("Wyandotte").autosomalgenes("1-2", "1-2","1|6|9","1-2","1-2","1-2","1-2","2-3","1-2","1-2","1-2","1-2", "5-6")
     *         breed("Wyandotte").addVariety()
     */

    String breedName;
    Float temperature;
    Float rain;
    Rarity rarity;
    String sexlinked;
    String autosomal;
    String[] varieties;

    public Breed(String breedName, Float temperature, Float rain, Rarity rarity, String sexlinked, String autosomal, String[] varieties) {
        this.breedName = breedName;
        this.temperature = temperature;
        this.rain = rain;
        this.rarity = rarity;
        this.sexlinked = sexlinked;
        this.autosomal = autosomal;
        this.varieties = varieties;
    }

    public Breed(String breedName, Float temperature, Float rain, Rarity rarity) {
        new Breed(breedName, temperature, rain, rarity, "", "", new String[]{""});
    }

    public Breed getBreed(String breedName) {

    }

    public String setAutosomalGenes(String breedName, String genes) {

    }

    public enum Rarity {
        ORDINARY,
        COMMON,
        UNCOMMON,
        RARE,
        EXOTIC
    }
}
