package mokiyoki.enhancedanimals.model.modeldata;

public class SheepPhenotype implements Phenotype {
    public int faceWool;
    public int tailLength = 4;
    public float tailFat = 0.0F;
    float hornScale = 1.0F;
    public boolean polyHorns;
    public HornType hornType = HornType.POLLED;
    public int leftHornLength;
    public int rightHornLength;
    public float[] hornGeneticsX;
    public float[] hornGeneticsY;
    public float[] hornGeneticsZ;


    public SheepPhenotype(int[] gene, boolean isFemale, char[] uuid) {
        faceWool = 0;
        if (gene[42] == 1 || gene[43] == 1) {
            if (gene[40] == 1) {
                faceWool++;
            }
            if (gene[41] == 1) {
                faceWool++;
            }
            if (gene[38] == 1 || gene[39] == 1) {
                faceWool++;
            } else if (gene[38] == 3 && gene[39] == 3) {
                faceWool--;
            }
        }

        this.tailLength = gene[92] == 2 || gene[93] == 2 ? 3 : 2;
        if (gene[94] == 2 || gene[95] == 2) {
            this.tailLength-=1;
            this.tailFat += 0.25F;
        }
        this.tailFat += gene[96] == 2 || gene[97] == 2 ? 0.25F : 0.0F;
        this.tailFat += gene[98] == 2 || gene[99] == 2 ? 0.25F : 0.0F;

        if (gene[6] == 2 || gene[7] == 2) {
            this.hornType = gene[6] == 1 || gene[7] == 1 ? HornType.SCURRED : HornType.HORNED;
        } else if (gene[6] != 1 && gene[7] != 1){
            // genderified horns
            this.hornType = isFemale ? HornType.POLLED : HornType.HORNED;
        }

        this.polyHorns = (gene[36] == 1 || gene[37] == 1) && (isFemale ? uuid[2] - 48 <= 3 : Character.isLetter(uuid[2]) || uuid[2] - 48 >= 3);

        float a = 0.2F + ((1.0F - (this.polyHorns ? -0.001F : 1.0F))* -0.05F);
        float b = 0.3F + ((1.0F - (this.polyHorns ? -0.001F : 1.0F))* 0.05F);
        float x = a * ( 1.0F + (b * 1.5F)) + (this.polyHorns ? 0.0F : -0.4F);

        float m = a*(1.0F+(b*1.4F));

        this.hornGeneticsX = new float[]{x, (x*0.9F) + (m*0.1F), (x*0.8F) + (m*0.2F), (x*0.7F) + (m*0.3F), (x*0.6F) + (m*0.4F), (x*0.5F) + (m*0.5F), (x*0.4F) + (m*0.6F), (x*0.3F) + (m*0.7F), (x*0.2F) + (m*0.8F), (x*0.1F) + (m*0.9F), m, a*(1.0F+(b*1.3F)), a*(1.0F+(b*1.2F)), a*(1.0F+(b*1.1F)), a*(1.0F+b), a*(1.0F+(b*0.9F)), a*(1.0F+(b*0.8F)), a*(1.0F+(b*0.7F)), a*(1.0F+(b*0.6F))};
        this.hornGeneticsY = new float[]{a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a};
        if (!this.polyHorns) {
            for (int i = 0, l = this.hornGeneticsX.length; i < l; i++) {
                this.hornGeneticsX[i] = this.hornGeneticsX[i] + 1.0F - (1.1F*((float)i/(l))) ;
            }
            a = a - 0.2F;
        }
        this.hornGeneticsZ = new float[]{a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a, a};

        calculateMaxHornLength(gene, isFemale, uuid);
    }

    private void calculateMaxHornLength(int[] gene, boolean isFemale, char[] uuid) {
        if (this.hornType != HornType.POLLED) {
            if (this.hornType == HornType.HORNED) {
                this.leftHornLength = 5;
                this.rightHornLength = 5;
            } else if (this.hornType == HornType.SCURRED) {
                this.leftHornLength = this.leftHornLength + 8;
                this.rightHornLength = this.rightHornLength + 8;

                if (Character.isDigit(uuid[4])) {
                    if ((uuid[4] - 48) <= 3) {
                        //shorten left horn
                        this.leftHornLength = this.leftHornLength + (uuid[4] - 48);
                    } else if ((uuid[4] - 48) <= 7) {
                        //shorten right horn
                        this.rightHornLength = this.rightHornLength + (uuid[4] - 52);
                    } else {
                        // shorten evenly
                        this.leftHornLength = this.leftHornLength + (uuid[4] - 55);
                        this.rightHornLength = this.leftHornLength;
                    }
                } else {
                    char a = uuid[4];
                    switch (a) {
                        case 'a':
                            this.leftHornLength = this.leftHornLength + 1;
                            this.rightHornLength = this.rightHornLength + 2;
                        case 'b':
                            this.leftHornLength = this.leftHornLength + 2;
                            this.rightHornLength = this.rightHornLength + 1;
                        case 'c':
                            this.leftHornLength = this.leftHornLength + 1;
                            this.rightHornLength = this.rightHornLength + 3;
                        case 'd':
                            this.leftHornLength = this.leftHornLength + 3;
                            this.rightHornLength = this.rightHornLength + 1;
                        case 'e':
                            this.leftHornLength = this.leftHornLength + 2;
                            this.rightHornLength = this.rightHornLength + 3;
                        case 'f':
                            this.leftHornLength = this.leftHornLength + 3;
                            this.rightHornLength = this.rightHornLength + 2;
                    }
                }
            }

            if (isFemale) {
                leftHornLength = 18 - (int)((18 - leftHornLength) * 0.5F);
                rightHornLength = 18 - (int)((18 - rightHornLength) * 0.5F);
            }
        }
    }
}
