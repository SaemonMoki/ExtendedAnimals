package mokiyoki.enhancedanimals.model.modeldata;

public class CatPhenotype implements Phenotype {
    public boolean longHaired;
    public boolean furnishings;
    public float furLength = 0.0F;
    public float furDensity;
    public float headSize = 1.025F;
    public float headWidth = 1.0F;
    public float headHeight = 0.0F;
    public float bodyWidth = 1F;
    public float snoutLength = 1.0F;
    public float snoutWidth = 1.0F;
    public float snoutScale = 1.0F;
    public float eyeRoundness = 0.0F;
    public float eyeSize = 1.0F;
    public float earSpacing = 0.0F;
    public float earFlare = 0.0F;
    public float earSize = 0.0F;
    public float earLength = 0.0F;
    public float earRoundness = 1.0F;
    public float jawScale = 1.25F;
    public float bodyType = 0.0F; //Positive - shorter rounder; Negative - longer skinnier
    public boolean isCobby = false;
    public float bodyLength = 0.0F;
    public float neckWidth = 1.0F;
    public float neckHeight = 1.0F;

    public int bobtail = 0;

    public CatPhenotype(int[] gene, char uuid) {
        longHaired = (gene[32] > 1 && gene[33] > 1);

        furLength = 0.0F;
        if (longHaired) {
            for (int i = 34; i < 42; i++) {
                furLength += gene[i] / 80F;
            }
        }

        //furnishings
        furnishings = (gene[42] == 2 && gene[43] == 2);
        //headWidth = (gene[44] == 2 || gene[45] == 2) ? 1F : 0F;

        // snout length genes
        for (int i = 100; i < 108; i++) {
            switch (gene[i]) {
                case 2 -> //Longest
                        snoutLength += 0.037F;
                case 3 -> //Longer
                        snoutLength += 0.02F;
                case 4 -> //Shorter
                        snoutLength -= 0.025F;
                case 5 -> //Shortest
                        snoutLength -= 0.0525F;
            }
        }
        for (int i = 108; i < 116; i++) {
            switch (gene[i]) {
                case 2 -> //Thinnest
                        snoutWidth -= 0.025F;
                case 3 -> //Thinner
                        snoutWidth -= 0.015F;
                case 4 -> //Wider
                        snoutWidth += 0.02F;
                case 5 -> //Widest
                        snoutWidth += 0.035F;
            }
        }
        for (int i = 116; i < 120; i++) {
            switch (gene[i]) {
                case 2 -> { //Smaller
                    snoutScale -= 0.015F;
                }
                case 3 -> { //Larger
                    snoutScale += 0.015F;
                }
            }
        }

        // Eye Size Subtractors
        eyeSize -= ((gene[40] + gene[41]) - 2) * 0.0125F;
        if (eyeSize > 1F) {
            eyeSize = 1F;
        }

        // Eye Roundness Adders
        eyeRoundness += ((gene[42] + gene[43]) - 2) * 0.125F;
        eyeRoundness += ((gene[44] + gene[45]) - 2) * 0.0625F;

        // Eye Roundness Subtractors
        eyeRoundness -= ((gene[46] + gene[47]) - 2) * 0.125F;
        eyeRoundness -= ((gene[48] + gene[49]) - 2) * 0.0625F;

        //Body Type
        bodyType = ((gene[120] + gene[121] + gene[122] + gene[123]) - 4F) / 16F;
        bodyType -= ((gene[124] + gene[125] + gene[126] + gene[127]) - 4F) / 16F;

        isCobby = bodyType > 0F;

        headSize = 1.025F + (bodyType * (isCobby ? 0.05F : 0.025F));

        neckWidth = 1F + (furLength * 0.25F) + (bodyType * (isCobby ? 0.4F : 0.25F));
        neckHeight = 1F + (furLength * 0.25F) + (bodyType * (isCobby ? 0F : 0.15F));

        for (int i = 90; i < 94; i++) {
            switch (gene[i]) {
                case 2 -> { //Smaller
                    headSize -= 0.04F;
                    neckWidth -= 0.02F;
                }
                case 3 -> { //Bigger
                    headSize += 0.04F;
                    neckWidth += 0.02F;
                }
            }
        }

        bodyWidth = (bodyType * (isCobby ? 0.5F : 0.1F));

        headWidth = 1F + (bodyType * (isCobby ? 0.3F : 0.1F)) + ((gene[94] + gene[95] - 2) * 0.01F);

        bodyLength = (1F - (((gene[96] + gene[97] + gene[98] + gene[99] - 4) / 16F) * 0.20F));

        earSpacing = (gene[128]+gene[129]+gene[130]+gene[131]-4)/16F;
        earFlare = (gene[132]+gene[133]+gene[134]+gene[135]-4)/8F;
        earSpacing += earFlare/2F;

        earLength = (gene[136]+gene[137]+gene[138]+gene[139]-4)/16F;
        earRoundness = (gene[140]+gene[141]+gene[142]+gene[143]-4)/16F;
        earSize = ((gene[144]+gene[145]-(gene[146]+gene[147]))-4)/8F;

        this.bobtail = (gene[26]==2 || gene[27]==2) ? (uuid % 4) + 1 : 0;

    }
}
