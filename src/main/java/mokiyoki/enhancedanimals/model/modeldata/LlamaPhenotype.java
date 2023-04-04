package mokiyoki.enhancedanimals.model.modeldata;

import net.minecraft.util.Mth;
public class LlamaPhenotype implements Phenotype {
    public int maxCoat;
    public boolean banana;
    boolean suri;
    public float nosePlacement;

    public LlamaPhenotype(int[] gene) {
        this.banana = gene[18] != 1 && gene[19] != 1 && (gene[18] == 2 || gene[19] == 2);
        this.suri = gene[20] == 2 && gene[21] == 2;

        float maxCoatLength = 0.0F;

        if (gene[22] >= 2 || gene[23] >= 2){
            if (gene[22] == 3 && gene[23] == 3){
                maxCoatLength = 1.25F;
            }else if (gene[22] == 3 || gene[23] == 3) {
                maxCoatLength = 1F;
            }else if (gene[22] == 2 && gene[23] == 2) {
                maxCoatLength = 0.75F;
            }else {
                maxCoatLength = 0.5F;
            }

            if (gene[24] == 2){
                maxCoatLength = maxCoatLength - 0.25F;
            }
            if (gene[25] == 2){
                maxCoatLength = maxCoatLength - 0.25F;
            }

            if (gene[26] == 2 && gene[27] == 2){
                maxCoatLength = maxCoatLength + (0.75F * (maxCoatLength/1.75F));
            }

        }else{
            maxCoatLength = 0;
        }

        if (maxCoatLength < 0.5){
            maxCoatLength = 0;
        }else if (maxCoatLength < 1){
            maxCoatLength = 1;
        }else if (maxCoatLength < 1.5){
            maxCoatLength = 2;
        }else if (maxCoatLength < 2) {
            maxCoatLength = 3;
        }else{
            maxCoatLength = 4;
        }

        this.maxCoat = (int)maxCoatLength;

        float noseHeight = 0.0F;
        if (gene[28] != 2) {
            if (gene[28] == 1) {
                noseHeight = 0.15F;
            } else if (gene[28] == 3) {
                noseHeight = 0.1F;
            } else {
                noseHeight = -0.1F;
            }
        }

        if (gene[29] != 2) {
            if (gene[29] == 1) {
                noseHeight = noseHeight + 0.05F;
            } else if (gene[29] == 3) {
                noseHeight = noseHeight + 0.1F;
            } else {
                noseHeight = noseHeight - 0.1F;
            }
        }

        if (gene[30] != 1) {
            if (gene[30] == 2) {
                noseHeight = noseHeight + 0.15F;
            } else if (gene[30] == 3) {
                noseHeight = noseHeight + 0.1F;
            } else {
                noseHeight = noseHeight - 0.1F;
            }
        }

        if (gene[31] != 1) {
            if (gene[31] == 2) {
                noseHeight = noseHeight + 0.05F;
            } else if (gene[31] == 3) {
                noseHeight = noseHeight + 0.1F;
            } else {
                noseHeight = noseHeight - 0.1F;
            }
        }

        if (gene[32] != 1) {
            if (gene[32] == 2) {
                noseHeight = noseHeight + 0.15F;
            } else if (gene[32] == 3) {
                noseHeight = noseHeight + 0.2F;
            } else if (gene[32] == 4) {
                noseHeight = noseHeight - 0.15F;
            } else {
                noseHeight = noseHeight - 0.2F;
            }
        }

        if (gene[33] != 1) {
            if (gene[33] == 2) {
                noseHeight = noseHeight + 0.15F;
            } else if (gene[33] == 3) {
                noseHeight = noseHeight + 0.2F;
            } else if (gene[33] == 4) {
                noseHeight = noseHeight - 0.15F;
            } else {
                noseHeight = noseHeight - 0.2F;
            }
        }

        this.nosePlacement = noseHeight - ((Mth.abs(noseHeight)*1.25F) * 0.2F);
    }
}