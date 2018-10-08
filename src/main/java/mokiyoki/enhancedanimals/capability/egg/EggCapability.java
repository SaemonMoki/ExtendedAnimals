package mokiyoki.enhancedanimals.capability.egg;

/**
 * Created by saemon on 30/09/2018.
 */
public class EggCapability implements IEggCapability {

    private int[] genes;
    @Override
    public int[] getGenes() {
        return this.genes;
    }

    @Override
    public void setGenes(int[] genes) {
        this.genes = genes;
    }
}
