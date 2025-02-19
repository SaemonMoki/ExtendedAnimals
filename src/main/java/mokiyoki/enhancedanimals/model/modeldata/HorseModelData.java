package mokiyoki.enhancedanimals.model.modeldata;

public class HorseModelData extends AnimalModelData {
    public boolean[] legMovingForward = new boolean[] {false, true, true, false};
    public HorsePhenotype getPhenotype() {
        return (HorsePhenotype) this.phenotype;
    }
}
