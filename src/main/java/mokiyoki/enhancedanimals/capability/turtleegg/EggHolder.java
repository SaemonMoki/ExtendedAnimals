package mokiyoki.enhancedanimals.capability.turtleegg;

import mokiyoki.enhancedanimals.util.Genes;

public class EggHolder {
    private final String sire;
    private final String dam;
    private final Genes genes;
    private boolean hasParents;

    public EggHolder (String sire, String dam, Genes genes, boolean hasParents) {
        this.sire = sire!=null && !sire.equals("") ? sire : "???";
        this.dam = dam!=null && !dam.equals("") ? dam : "???";
        this.genes = genes;
        this.hasParents = hasParents;
    }

    public String getSire() {
        return this.sire;
    }

    public String getDam() {
        return this.dam;
    }

    public Genes getGenes() {
        return this.genes;
    }

    public boolean hasParents() {
        return this.hasParents;
    }
}
