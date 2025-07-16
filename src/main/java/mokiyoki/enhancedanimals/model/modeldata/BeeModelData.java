package mokiyoki.enhancedanimals.model.modeldata;

public class BeeModelData extends AnimalModelData {
    public boolean hasOvipositor = true;

    public BeePhenotype getPhenotype() {
        return (BeePhenotype) this.phenotype;
    }
}
