package mokiyoki.enhancedanimals.model.modeldata;

public class CatPhenotype implements Phenotype {
    public boolean longHaired;
    public float furLength;
    public float furDensity;

    public CatPhenotype(int[] gene,char uuid) {
        longHaired = (gene[32] > 1 && gene[33] > 1);

        furLength = 0F;
        if (longHaired) {
            for (int i = 34; i < 42; i++) {
                furLength += gene[i] / 80F;
            }
        }
    }
}
