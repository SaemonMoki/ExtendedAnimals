package mokiyoki.enhancedanimals.model.modeldata;

public class TurtleModelData extends AnimalModelData {
    public boolean hasEggs;
    public TurtlePhenotype getPhenotype() {
        return (TurtlePhenotype) this.phenotype;
    }
}
