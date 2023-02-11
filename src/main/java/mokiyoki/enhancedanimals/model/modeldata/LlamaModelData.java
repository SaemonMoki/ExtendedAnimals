package mokiyoki.enhancedanimals.model.modeldata;

 public class LlamaModelData extends AnimalModelData {
    public int coatlength;
    public LlamaPhenotype getPhenotype() {
        return (LlamaPhenotype) this.phenotype;
    }
}
