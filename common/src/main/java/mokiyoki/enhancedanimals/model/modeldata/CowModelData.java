package mokiyoki.enhancedanimals.model.modeldata;

public class CowModelData extends AnimalModelData {
    public float bagSize = 0.0F;
    public float hornGrowth = 0.0F;
    public CowPhenotype getPhenotype() {
        return (CowPhenotype) this.phenotype;
    }
}
