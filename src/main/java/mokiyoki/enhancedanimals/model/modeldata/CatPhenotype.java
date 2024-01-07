package mokiyoki.enhancedanimals.model.modeldata;

public class CatPhenotype implements Phenotype {
    public boolean longHaired;
    public boolean furnishings;
    public float furLength = 0.0F;
    public float furDensity;

    public CatPhenotype(int[] gene,char uuid) {
        longHaired = (gene[32] > 1 && gene[33] > 1);

        furLength = 0.0F;
        if (longHaired) {
            for (int i = 34; i < 42; i++) {
                furLength += gene[i] / 80F;
            }
        }

        //furnishings
        furnishings = (gene[42] == 2 && gene[43] == 2);
    }
}
