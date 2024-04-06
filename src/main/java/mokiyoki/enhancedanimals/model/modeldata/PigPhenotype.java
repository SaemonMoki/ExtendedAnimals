package mokiyoki.enhancedanimals.model.modeldata;

import mokiyoki.enhancedanimals.model.util.ModelHelper;
import net.minecraft.util.Mth;

import java.util.List;

public class PigPhenotype implements Phenotype {
    public float earFlopMod;
    public int earSizeMod = 0;
    public boolean tailCurl;
    public boolean hasWaddles;
    public float snoutLength;
    public float tailCurlAmount;
    public int shape;
    public float snoutAngle = 0F;

    public float muscle = 0.0F;
    public float fat = 0.0F;

    public int bodyLength; //in pixels
    public float lengthScaling;
    public float bodyScale;
    public float bodyWidth;
    public float bodyZ;
    public float buttScale;
    public float buttTranslation; //also used for legs
    public float wool = 0.0F;
    public List<Float> bodyScalings;
    public List<Float> neckScalings;
    public List<Float> buttScalings;
    public List<Float> cheekScalings;
    public List<Float> earScalings;
    public List<Float> headScalings;

    public PigPhenotype(int[] gene,char uuid) {
        this.tailCurl = Character.isLetter(uuid);

        float earSize = 0.0F;
        float earFlop = 1.0F;
        for (int i = 166; i < 172; i++) {
            if (gene[i] != 1) {
                muscle += (gene[i] / 80.0F);
            }
        }
        if (gene[172] == 2 || gene[173] == 2) {
            muscle += 0.25F;
        }

        for (int i = 174; i < 182; i++) {
            if (gene[i] != 1) {
                fat += gene[i];
            }
        }
        fat = (fat - 32.0F)/40.0F;

        int length = gene[182] + gene[183] + gene[184] + gene[185] + gene[186] + gene[187] + gene[188] + gene[189];

        this.bodyLength = 13;
        if (length > 60) {
            this.bodyLength = 14;
        }
        else if (length <= 20) {
            this.bodyLength = 12;
        }

        this.lengthScaling = ((length-32)/40.0F)*0.22F;
        this.buttTranslation = ((bodyLength)*this.lengthScaling);
        this.bodyScale = 1.0F + (this.fat*0.15F) + (this.wool*0.7F) - (this.muscle*0.02F);
        this.buttScale = 1.0F + (this.muscle*0.07F) + (this.fat*0.08F) + (this.wool*0.7F);


        if (gene[36] != 1 && gene[37] != 1) {
            if (gene[38] == 3 || gene[39] == 3) {
                if (gene[40] == 1 && gene[41] == 1) {
                    this.wool = 0.1F;
                }
                else if (gene[40] == 1 || gene[41] == 1) {
                    this.wool = 0.07F;
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

        for (int i = 202; i < 206; i++) {
            if (gene[i] == 1) {
                snoutAngle += Mth.HALF_PI*0.0125F;
            }
            else if (gene[i] == 3) {
                snoutAngle += Mth.HALF_PI*-0.0125F;
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

        float shoulderScale = 1.0F + (this.muscle*0.075F) + (this.fat*0.05F) + (this.wool*0.7F);
        float shoulderWidth = (this.muscle*0.035F) + (this.fat*0.055F);
        float shoulderHeight = (this.muscle*0.055F);
        float buttWidth = (this.muscle*0.050F) + (this.fat*0.025F);
        this.bodyWidth = (this.fat*0.025F) + (this.muscle*0.01F);
        float shoulderZ = (this.muscle*0.025F);
        bodyZ = (this.muscle * 0.015F);
        if (this.fat > 0) {
            bodyZ += -(this.fat * 0.08F);
        }
        else {
            shoulderHeight += (this.fat * 0.05F);
            shoulderZ += (this.fat * 0.04F);
        }
        float headWidth = (this.fat*0.055F);
        float headY = (this.muscle*0.040F);
        float headScaling = 1.0F;
        if (this.lengthScaling < 0) {
            headScaling += this.lengthScaling;
        }
        float cheekFat = (this.fat*0.012F);

        bodyScalings = ModelHelper.createScalings(this.bodyScale + this.bodyWidth, this.bodyScale+this.lengthScaling, this.bodyScale, 0.0F, 0.0F, bodyZ);
        neckScalings = ModelHelper.createScalings(shoulderScale + shoulderWidth, shoulderScale, shoulderScale+shoulderHeight, 0.0F, 0.0F, shoulderZ);
        buttScalings = ModelHelper.createScalings(this.buttScale+buttWidth, 1.0F, this.buttScale, 0.0F, 0.0F, 0.0F);
        cheekScalings = ModelHelper.createScalings(1+this.wool+(cheekFat*2.0F)-(this.snoutLength*0.125F), 1+this.wool+cheekFat, 1+this.wool+cheekFat, 0.0F, 0.0F, 0.0F);
        earScalings = ModelHelper.createScalings(1+this.wool, 1+this.wool, 1+this.wool, 0.0F, 0.0F, 0.0F);
        headScalings = ModelHelper.createScalings(headScaling+headWidth, headScaling, headScaling, 0.0F, 0.0F, headY);
        this.hasWaddles = gene[32] == 1 || gene[33] == 1;
    }
}
