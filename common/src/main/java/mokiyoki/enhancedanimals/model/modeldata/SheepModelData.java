package mokiyoki.enhancedanimals.model.modeldata;

public class SheepModelData extends AnimalModelData {
    public float bagSize = 0.0F;
    public float hornGrowth = 0.0F;
    public int coatLength = 0;
    public SheepPhenotype getPhenotype() {
        return (SheepPhenotype) this.phenotype;
    }
}
