package mokiyoki.enhancedanimals.model.modeldata;

import mokiyoki.enhancedanimals.util.Genes;

public class AxolotlPhenotype implements Phenotype {
    public boolean glowingBody;
    public boolean glowingEyes;
    public boolean glowingGills;
    public boolean isLong;
    public AxolotlTailLength tailLength;

    public AxolotlPhenotype(Genes genes) {
        int[] gene = genes.getAutosomalGenes();
        this.isLong = gene[32] == 2 && gene[33] == 2; //Long Body

        if (gene[26] == 2 || gene[27] == 2) {
            //Long Tail
            this.tailLength = gene[26] == gene[27] ? AxolotlTailLength.EXTRALONG : AxolotlTailLength.LONG;
        }
        else {
            this.tailLength = AxolotlTailLength.NORMAL;
        }

        this.glowingBody = genes.has(10, 3) && !genes.has(10, 2);
        this.glowingEyes = this.glowingBody || genes.has(20, 6);
        this.glowingGills = this.glowingBody || (genes.has(38, 3) && !genes.has(38, 2));
    }
}
