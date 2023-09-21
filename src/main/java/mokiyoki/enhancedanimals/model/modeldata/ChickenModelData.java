package mokiyoki.enhancedanimals.model.modeldata;

public class ChickenModelData extends AnimalModelData {
    public boolean isFemale = true;
    public boolean isBrooding = false;
    public int lookType = 0;
    public ChickenPhenotype getPhenotype() {
        return (ChickenPhenotype) this.phenotype;
    }
}