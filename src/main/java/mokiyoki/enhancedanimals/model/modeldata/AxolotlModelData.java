package mokiyoki.enhancedanimals.model.modeldata;

public class AxolotlModelData extends AnimalModelData {
    public boolean hasEggs = false;

    public AxolotlPhenotype getPhenotype() {
        return (AxolotlPhenotype) this.phenotype;
    }
}