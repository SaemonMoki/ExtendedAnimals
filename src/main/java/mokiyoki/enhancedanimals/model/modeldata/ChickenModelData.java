package mokiyoki.enhancedanimals.model.modeldata;

public class ChickenModelData extends AnimalModelData {
    public boolean isFemale = true;
    public float extraGrowth = 0.0F;
    public int brooding = 0;
    public int lookType = 0;
    public int idleType = -1;
    public int idleTimer = 0;

    public ChickenPhenotype getPhenotype() {
        return (ChickenPhenotype) this.phenotype;
    }
    public boolean isBrooding() {
        return brooding != 0;
    }
}