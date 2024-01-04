package mokiyoki.enhancedanimals.model.modeldata;

public class CatModelData extends AnimalModelData {
    public float light = 0;
    public CatPhenotype getPhenotype() {
        return (CatPhenotype) this.phenotype;
    }
}
