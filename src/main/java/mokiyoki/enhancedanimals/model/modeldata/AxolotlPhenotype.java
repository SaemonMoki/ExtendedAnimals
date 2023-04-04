package mokiyoki.enhancedanimals.model.modeldata;

import mokiyoki.enhancedanimals.util.Genes;

public class AxolotlPhenotype implements Phenotype {
    boolean glowingBody;
    boolean glowingEyes;
    boolean glowingGills;
    public boolean isLong;
    public AxolotlTailLength tailLength;

    public AxolotlPhenotype(Genes genes) {
        int[] gene = genes.getAutosomalGenes();
        this.isLong = gene[32] == 2 && gene[33] == 2;

        if (gene[26] == gene[27]) {
            this.tailLength = gene[32] == 1 ? AxolotlTailLength.NORMAL : AxolotlTailLength.EXTRALONG;
        } else {
            this.tailLength = AxolotlTailLength.LONG;
        }

        this.glowingBody = genes.has(10, 3) && !genes.has(10, 2);
        this.glowingEyes = this.glowingBody || genes.has(20, 6);
        this.glowingGills = this.glowingBody || (genes.has(38, 3) && !genes.has(38, 2));
    }
}
