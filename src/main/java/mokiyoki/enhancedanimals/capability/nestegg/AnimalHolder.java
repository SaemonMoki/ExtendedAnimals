package mokiyoki.enhancedanimals.capability.nestegg;

import mokiyoki.enhancedanimals.util.Genes;

public class AnimalHolder extends EntityHolder {
    private final String mateName;
    private final Genes mateGenes;

    public AnimalHolder(String name, Genes genes, String mateName, Genes mateGenes) {
        super(name, genes);
        this.mateName = mateName;
        this.mateGenes = mateGenes;
    }
}
