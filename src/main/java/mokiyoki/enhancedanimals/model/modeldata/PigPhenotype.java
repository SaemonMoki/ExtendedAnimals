package mokiyoki.enhancedanimals.model.modeldata;

public class PigPhenotype implements Phenotype {
    public float earFlopMod;
    public int earSizeMod = 0;
    public boolean tailCurl;
    public boolean hasWaddles;
    public float snoutLength;
    public float tailCurlAmount;
    public int shape;

    public float muscle = 0.0F;
    public float fat = 0.0F;
    public float wool = 0.0F;

    public PigPhenotype(int[] gene,char uuid) {
        this.tailCurl = Character.isLetter(uuid);

        float earSize = 0.0F;
        float earFlop = 1.0F;
        for (int i = 166; i < 172; i++) {
            if (gene[i] == 2) {
                muscle += 0.1F;
            }
            else if (gene[i] == 3) {
                muscle += 0.15F;
            }
        }
        if (gene[172] == 1 || gene[173] == 1) {
            muscle += 0.2F;
        }

        for (int i = 174; i < 178; i++) {
            if (gene[i] == 2) {
                fat += 0.1F;
            }
            else if (gene[i] == 3) {
                fat += 0.15F;
            }
        }


        /*
        //muscle test
        if (gene[154] == 2 || gene[155] == 2) {
            muscle = 0.5F;
        }
        else if (gene[154] == 3 || gene[155] == 3) {
            muscle = 1.0F;
        }
        //fat test
        if (gene[156] == 2 || gene[157] == 2) {
            fat = 1.0F;
        }*/
        if (gene[36] != 1 && gene[37] != 1) {
            if (gene[38] == 3 || gene[39] == 3) {
                if (gene[40] == 1 && gene[41] == 1) {
                    this.wool = 1.0F;
                }
                else if (gene[40] == 1 || gene[41] == 1) {
                    this.wool = 0.7F;
                }
            }
        }
        //SSC1
        if (gene[68] == 2 || gene[69] == 2) {
            earSize = earSize + 0.12F;
            earFlop = earFlop - 0.44F;
        } else if (gene[68] == 3 || gene[69] == 3) {
            earSize = earSize + 0.03F;
        }
        //SSC5
        if (gene[70] == 2 || gene[71] == 2) {
            earSize = earSize + 0.35F;
            earFlop = earFlop - 0.48F;
        } else if (gene[70] == 3 && gene[71] == 3) {
            earSize = earSize + 0.09F;
        }
        //SSC6
        if (gene[72] == 2 || gene[73] == 2) {
            earSize = earSize - 0.031F;
        }
        //SSC7
        if (gene[74] == 2 || gene[75] == 2) {
            earSize = earSize + 0.29F;
            earFlop = earFlop - 0.62F;
        } else if (gene[70] == 3 && gene[71] == 3) {
            earSize = earSize + 0.09F;
        }
        //SSC9
        if (gene[76] == 2 || gene[77] == 2) {
            earSize = earSize + 0.11F;
            earFlop = earFlop - 0.32F;
        } else if (gene[70] == 3 && gene[71] == 3) {
            earSize = earSize + 0.03F;
        }
        //SSC12
        if (gene[78] == 2 || gene[79] == 2) {
            earSize = earSize + 0.11F;
            earFlop = earFlop - 0.14F;
        } else if (gene[78] == 3 && gene[79] == 3) {
            earSize = earSize + 0.03F;
        }

        for (int i = 80; i < 100; i+= 2) {
            if (gene[i] != 1 && gene[i+1] != 1) {
                if (gene[i] == 2 || gene[i+1] == 2) {
                    earSize = earSize + 0.007F;
                } else if (gene[i] == 3 || gene[i+1] == 3){
                    earSize = earSize + 0.007F;
                    earFlop = earFlop - 0.004F;
                } else {
                    earSize = earSize + 0.007F;
                    earFlop = earFlop - 0.007F;
                }
            }
        }
        for (int i = 100; i < 120; i+= 2) {
            if (gene[i] != 1 && gene[i+1] != 1) {
                if (gene[i] == 2 || gene[i+1] == 2) {
                    earSize = earSize - 0.007F;
                } else if (gene[i] == 3 || gene[i+1] == 3){
                    earSize = earSize - 0.007F;
                    earFlop = earFlop + 0.004F;
                } else {
                    earSize = earSize - 0.007F;
                    earFlop = earFlop + 0.007F;
                }
            }
        }

        if (earFlop > 1.0F) {
            this.earFlopMod = 1.0F;
        } else {
            this.earFlopMod = earFlop < -1.0F ? -1.0F : earFlop;
        }

        if (earSize > 0.2F) {
            if (earSize > 0.5F) {
                this.earSizeMod = 2;
            } else {
                this.earSizeMod = 1;
            }
        }

        //snoutLength
        float snoutlength1 = 1.0F;
        float snoutlength2 = 1.0F;
        float snoutlength = 0.0F;

        // 1 - 5, longest - shortest
        for (int i = 1; i < gene[18];i++){
            snoutlength1 = snoutlength1 - 0.1F;
        }

        for (int i = 1; i < gene[19];i++){
            snoutlength2 = snoutlength2 - 0.1F;
        }

        for (int i = 1; i < gene[66];i++){
            snoutlength1 = snoutlength1 - 0.1F;
        }

        for (int i = 1; i < gene[67];i++){
            snoutlength2 = snoutlength2 - 0.1F;
        }

        //causes partial dominance of longer nose over shorter nose.
        if (snoutlength1 > snoutlength2){
            snoutlength = (snoutlength1*0.75F) + (snoutlength2*0.25F);
        }else if (snoutlength1 < snoutlength2){
            snoutlength = (snoutlength1*0.25F) + (snoutlength2*0.75F);
        }else{
            snoutlength = snoutlength1;
        }

        // 1 - 4, longest - shortest
        if (gene[42] >= gene[43]) {
            snoutlength = snoutlength + ((4 - gene[42])/8.0F);
        } else {
            snoutlength = snoutlength + ((4 - gene[43])/8.0F);
        }

        if (gene[44] >= 2 && gene[45] >= 2) {
            if (gene[44] == 2 || gene[45] == 2) {
                snoutlength = snoutlength * 0.9F;
            } else {
                snoutlength = snoutlength * 0.75F;
            }
        }

        if (gene[46] == 2 && gene[47] == 2) {
            snoutlength = snoutlength * 0.6F;
        }

        if (snoutlength > 1.0F) {
            this.snoutLength = 1.0F;
        } else if (snoutlength < 0.0F) {
            this.snoutLength = 0.0F;
        } else {
            this.snoutLength = snoutlength;
        }

        float inbreedingFactor = 0.0F;

        if (gene[20] == gene[21]) {
            inbreedingFactor = 0.1667F;
        }
        if (gene[22] == gene[23]) {
            inbreedingFactor = inbreedingFactor + 0.1667F;
        }
        if (gene[24] == gene[25]) {
            inbreedingFactor = inbreedingFactor + 0.1667F;
        }
        if (gene[26] == gene[27]) {
            inbreedingFactor = inbreedingFactor + 0.1667F;
        }
        if (gene[28] == gene[29]) {
            inbreedingFactor = inbreedingFactor + 0.1667F;
        }
        if (gene[30] == gene[31]) {
            inbreedingFactor = inbreedingFactor + 0.1667F;
        }

        this.tailCurlAmount = inbreedingFactor;

//            int shape = 1;
//
//            if (gene[56] != 1 && gene[57] != 1) {
//                if (gene[56] + gene[57] <= 8) {
//                    shape = 0;
//                } else if (gene[56] == 5 || gene[57] == 5) {
//                    shape = 2;
//                } else if (gene[56] == 6 || gene[57] == 6) {
//                    shape = 3;
//                } else if (gene[56] == 7 && gene[57] == 7) {
//                    shape = 4;
//                }
//            }
        this.shape = 2;

        this.hasWaddles = gene[32] == 1 || gene[33] == 1;
    }
}
