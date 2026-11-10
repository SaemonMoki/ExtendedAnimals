package mokiyoki.enhancedanimals.model.modeldata;

public class RabbitModelData extends AnimalModelData {
    public float noseTwitch;
    public int coatLength;
    public RabbitPhenotype getPhenotype() {
        return (RabbitPhenotype) this.phenotype;
    }
}
