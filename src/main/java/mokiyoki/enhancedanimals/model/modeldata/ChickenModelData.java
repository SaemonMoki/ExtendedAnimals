package mokiyoki.enhancedanimals.model.modeldata;

public class ChickenModelData extends AnimalModelData {
    public boolean isFemale = true;
    public ChickenPhenotype getPhenotype() {
        return (ChickenPhenotype) this.phenotype;
    }
}