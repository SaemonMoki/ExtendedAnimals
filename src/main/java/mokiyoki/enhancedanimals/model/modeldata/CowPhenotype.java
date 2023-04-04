package mokiyoki.enhancedanimals.model.modeldata;
public class CowPhenotype implements Phenotype {
    public boolean isFemale;
    public float hornScale;
    public HornType hornType;
    private float hornBaseHeight;
    public int leftHornLength = 9;
    public int rightHornLength = 9;
    public float[] hornGeneticsX;
    public float[] hornGeneticsY;
    public float[] hornGeneticsZ;
    public boolean dwarf;
    public float bodyWidth;
    public float bodyLength;
    public int hump;
    public float humpPlacement;
    public int hornNubLength;
    public boolean averageEars;
    public float earSize;
    public float earFloppiness;
    public boolean hairy = false;
    public int[] mushrooms;

    public CowPhenotype(int[] gene, char[] uuidArray, boolean isFemale, boolean isMooshroom) {
        super();
        this.isFemale = isFemale;
        this.hornType = calculateHornType(gene, isFemale);
        this.hornScale = getHornScale(gene, this.hornType);
        this.setHornLengths(gene, uuidArray[4], this.hornType);
        this.setHornGenetics(gene, this.hornType);
        this.dwarf = gene[26] == 1 || gene[27] == 1;

        float bodyWidth = isFemale ? -0.175F : 0.0F;
        float bodyLength = 0.0F;
        int hump = 0;

        float genderModifier = isFemale ? 0.045F : 0.0825F;
        for (int i = 1; i < gene[54]; i++) {
            bodyWidth = bodyWidth + genderModifier;
        }
        for (int i = 1; i < gene[55]; i++) {
            bodyWidth = bodyWidth + genderModifier;
        }

        if (bodyWidth >= 0.0F) {
            bodyLength = bodyWidth;
        }

        for (int i = 1; i < gene[38]; i++) {
            hump++;
        }

        for (int i = 1; i < gene[39]; i++) {
            hump++;
        }

        if (!(gene[48] == 1 || gene[49] == 1)){
            this.hairy = gene[50] + gene[51] + gene[52] + gene[53] > 5;
        }

        this.bodyWidth = bodyWidth;
        this.bodyLength = bodyLength;
        this.hump = (int) (hump * (isFemale ? 0.6 : 1));
        this.humpPlacement = setHumpPlacement(gene);
        this.hornBaseHeight = setHornBaseHeight(gene);
        this.hornNubLength = setHornNubLength(gene);
        this.calculateEarRotations(gene);

        if (isMooshroom) {
            this.mushrooms = new int[]{
                    mushroomX(uuidArray[20]),mushroomZ(uuidArray[20]),
                    mushroomX(uuidArray[21]),mushroomZ(uuidArray[21]),
                    mushroomX(uuidArray[22]),mushroomZ(uuidArray[22]),
                    mushroomX(uuidArray[23]),mushroomZ(uuidArray[23]),
                    mushroomX(uuidArray[24]),mushroomZ(uuidArray[24]),
                    mushroomX(uuidArray[25]),mushroomZ(uuidArray[25]),
                    mushroomX(uuidArray[26]),mushroomZ(uuidArray[26]),
                    mushroomX(uuidArray[27]),mushroomZ(uuidArray[27]),
                    mushroomX(uuidArray[28]),mushroomZ(uuidArray[28]),
                    mushroomX(uuidArray[29]),mushroomZ(uuidArray[29]),
                    mushroomX(uuidArray[30]),mushroomZ(uuidArray[30]),
                    mushroomX(uuidArray[31]),mushroomZ(uuidArray[31]),
                    mushroomX(uuidArray[32]),mushroomZ(uuidArray[32]),
                    mushroomX(uuidArray[33]),mushroomZ(uuidArray[33]),
                    mushroomX(uuidArray[34]),mushroomZ(uuidArray[34]),
                    mushroomX(uuidArray[35]),mushroomZ(uuidArray[35])
            };
        }
    }

    private int mushroomX(char val) {
        return switch (val) {
            case '0','4','8' -> 0;
            case '1','5','9' -> 1;
            case '2','6','a' -> 2;
            default -> -1;
        };
    }

    private int mushroomZ(char val) {
        return switch (val) {
            case '0','1','2' -> 0;
            case '4','5','6' -> 1;
            case '8','9','a' -> 2;
            default -> -1;
        };
    }

    private void setHornGenetics(int[] genes, HornType horns) {
        Float[] hornGenetics = {1.5F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F};

        if (horns != HornType.POLLED) {
            int a = 100;
            for (int i = 1; i <= 9; i++) {
                hornGenetics[i] = (float) (((genes[a] % 10) + (genes[a + 1] % 10)) - 3) / -30;
                hornGenetics[10 + i] = (float) ((((genes[a] / 10) % 10) + ((genes[a + 1] / 10) % 10)) - 6) / 30;
                a = a + 2;
            }

            hornGenetics[29] = (float) ((genes[94] % 10) + ((genes[95]) % 10)) / 18.0F;
            hornGenetics[28] = (float) (((genes[94] / 10) % 10) + ((genes[95] / 10) % 10)) / 18.0F;
            hornGenetics[27] = (float) (((genes[94] / 100) % 10) + ((genes[95] / 100) % 10)) / 18.0F;
            hornGenetics[26] = (float) (((genes[94] / 1000) % 10) + ((genes[95] / 1000) % 10)) / 18.0F;
            hornGenetics[25] = (float) (((genes[94] / 10000) % 10) + ((genes[95] / 10000) % 10)) / 18.0F;
            hornGenetics[24] = (float) ((genes[94] / 100000) + (genes[95] / 100000)) / 18.0F;
            hornGenetics[23] = (float) ((genes[96] % 10) + ((genes[97]) % 10)) / 18.0F;
            hornGenetics[22] = (float) (((genes[96] / 10) % 10) + ((genes[97] / 10) % 10)) / 18.0F;
            hornGenetics[21] = (float) (((genes[96] / 100) % 10) + ((genes[97] / 100) % 10)) / 18.0F;


            float averageMod = 1 - ((hornGenetics[29] + hornGenetics[28] + hornGenetics[27] + hornGenetics[26] + hornGenetics[25] + hornGenetics[24] + hornGenetics[23] + hornGenetics[22] + hornGenetics[21]) / 9);
            for (int i = 21; i <= 29; i++) {
                hornGenetics[i] = hornGenetics[i] * (((float) ((genes[96] / 1000) + (genes[97] / 1000) - (averageMod * 5))) / 8.0F);
            }

            // if b is lower horns curl more near ends
            int b = ((genes[84] - 1) + (genes[85] - 1)) / 6;

//             [straight] , [ 1 2 3 ] , [ 4 5 6 ], [ 7 8 9 ]
            float f = (1 + (float) (Math.abs((genes[92] % 10) - 3) + Math.abs((genes[92] % 10) - 3)) / 36);
            for (int i = 1; i <= 29; i++) {
                if (i % 10 <= 3) {
                    // horn 1, 2, 3 grab allele section [ O x x x ]
                    int c = (genes[92] / 1000) + (genes[93] / 1000);
                    hornGenetics[i] = hornGenetics[i] * ((((c) * ((1 + (float) (Math.abs((genes[92] % 10) - 3) + Math.abs((genes[92] % 10) - 3)) / 36)))) / 24.0F);
                } else if (i % 10 <= 6) {
                    // horn 4, 5, 6 grab allele section [ x O x x ]
                    int d = ((genes[92] / 100) % 10) + ((genes[93] / 100) % 10);
                    hornGenetics[i] = (1.3F - (b / 3.0F)) * hornGenetics[i] * ((d * f) / 24.0F);
                } else {
                    // horn 7, 8, 9 grab allele section [ x x O x ]
                    int e = ((genes[92] / 10) % 10) + ((genes[93] / 10) % 10);
                    hornGenetics[i] = (1.5F - (b / 2.0F)) * hornGenetics[i] * ((e * f) / 24.0F);
                }
            }

            // if b is lower horns stick more out the side of the head
            hornGenetics[10] = (b * (((genes[92] % 10) - 3.0F) + ((genes[92] % 10) - 3.0F)) / -9.0F) * 0.5F;
            hornGenetics[20] = hornGenetics[20] * (1 - b);
        }

        this.hornGeneticsX = new float[]{-hornGenetics[10], hornGenetics[11], hornGenetics[12], hornGenetics[13], hornGenetics[14], hornGenetics[15], hornGenetics[16], hornGenetics[17], hornGenetics[18], hornGenetics[19]};
        this.hornGeneticsY = new float[]{-hornGenetics[20], hornGenetics[21], hornGenetics[22], hornGenetics[23], hornGenetics[24], hornGenetics[25], hornGenetics[26], hornGenetics[27], hornGenetics[28], hornGenetics[29]};
        this.hornGeneticsZ = new float[]{hornGenetics[0], hornGenetics[1], hornGenetics[2], hornGenetics[3], hornGenetics[4], hornGenetics[5], hornGenetics[6], hornGenetics[7], hornGenetics[8], hornGenetics[9]};

    }

    private HornType calculateHornType(int[] gene, boolean isFemale) {
        if (gene != null) {
            if (gene[12] == 1 || gene[13] == 1) {
                //should be polled unless...
                //african horn genes
                if (gene[76] == 2 && gene[77] == 2) {
                    //polled
                    if (gene[78] == 1 && gene[79] == 1) {
                        //scured
                        return HornType.SCURRED;
                    } else if (gene[78] == 1 || gene[79] == 1) {
                        //sex determined scured
                        return isFemale ? HornType.POLLED : HornType.SCURRED;
                    } else {
                        //polled
                        return HornType.POLLED;
                    }
                } else if (isFemale && gene[76] != gene[77]) {
                    return (gene[78] == 1 && gene[79] == 1) ? HornType.SCURRED : HornType.POLLED;
                }
            }
        }

        return HornType.HORNED;
    }

    private float getHornScale(int[] genes, HornType horns) {
        //size [0.75 to 2.5]
        float hornScaleCalc = 1.0F;
        if (horns != HornType.POLLED) {
            if (horns == HornType.HORNED) {
                //normal horns
                for (int i = 86; i <= 89; i++) {
                    if (genes[i] == 2) {
                        hornScaleCalc = hornScaleCalc * 1.15F;
                    }
                }
                if (genes[90] == 2) {
                    hornScaleCalc = hornScaleCalc * 1.25F;
                }
                if (genes[91] == 2) {
                    hornScaleCalc = hornScaleCalc * 1.25F;
                }
                if (genes[80] >= 3) {
                    hornScaleCalc = hornScaleCalc * 0.95F;
                }
                if (genes[81] >= 3) {
                    hornScaleCalc = hornScaleCalc * 0.95F;
                }
            } else {
                //scurs
                hornScaleCalc = (hornScaleCalc + 0.75F) * 0.5F;
            }
            hornScaleCalc = hornScaleCalc * (((2.0F / (float) (genes[122] + genes[123])) + 1.5F) / 2.5F);
        }

        return hornScaleCalc;
    }

    private float setHornBaseHeight(int[] gene) {
        float hbh = -1.0F;

        if (gene[70] == 2) {
            hbh = hbh + 0.05F;
        }
        if (gene[71] == 2) {
            hbh = hbh + 0.05F;
        }
        if (gene[72] != 1) {
            hbh = hbh + 0.1F;
        }
        if (gene[73] != 1) {
            hbh = hbh + 0.1F;
        }
        if (gene[72] == 3 && gene[73] == 3) {
            hbh = hbh + 0.1F;
        }
        if (gene[74] == 1) {
            hbh = hbh * 0.75F;
        }
        if (gene[75] == 1) {
            hbh = hbh * 0.75F;
        }

        return hbh;
    }

    private void setHornLengths(int[] gene, char uuid, HornType horns) {
        if (horns != HornType.POLLED) {
            int lengthL = 2;

            if (gene[80] != 2 || gene[81] != 2) {
                if (gene[80] >= 3 || gene[81] >= 3) {
                    if (gene[80] == 4 && gene[81] == 4) {
                        lengthL = -1;
                    } else {
                        lengthL = 1;
                    }
                } else {
                    lengthL = 0;
                }
            }

            int lengthR = lengthL;

            if (horns == HornType.SCURRED) {
                lengthL = lengthL + 5;
                lengthR = lengthR + 5;

                if (Character.isDigit(uuid)) {
                    if ((uuid - 48) <= 3) {
                        //shorten left horn
                        lengthL = lengthL + (uuid - 48);
                    } else if ((uuid - 48) <= 7) {
                        //shorten right horn
                        lengthR = lengthR + (uuid - 52);
                    } else {
                        // shorten evenly
                        lengthL = lengthL + (uuid - 55);
                        lengthR = lengthL;
                    }
                } else {
                    char a = uuid;
                    switch (a) {
                        case 'a':
                            lengthL = lengthL + 1;
                            lengthR = lengthR + 2;
                        case 'b':
                            lengthL = lengthL + 2;
                            lengthR = lengthR + 1;
                        case 'c':
                            lengthL = lengthL + 1;
                            lengthR = lengthR + 3;
                        case 'd':
                            lengthL = lengthL + 3;
                            lengthR = lengthR + 1;
                        case 'e':
                            lengthL = lengthL + 2;
                            lengthR = lengthR + 3;
                        case 'f':
                            lengthL = lengthL + 3;
                            lengthR = lengthR + 2;
                    }
                }
            } else {
                //if horns are not scurred shorten horns if they are extra thicc
                if (gene[82] == 2) {
                    lengthL = lengthL + 1;
                }
                if (gene[83] == 2) {
                    lengthL = lengthL + 1;
                }

                if (gene[88] == 2) {
                    lengthL = lengthL + 1;
                }
                if (gene[89] == 2) {
                    lengthL = lengthL + 1;
                }
                if (gene[90] == 2 || gene[91] == 2) {
                    lengthL = lengthL + 2;
                }
                lengthR = lengthL;
            }

            if (lengthL > 9) {
                lengthL = 9;
            }
            if (lengthR > 9) {
                lengthR = 9;
            }

            this.leftHornLength = lengthL;
            this.rightHornLength = lengthR;
        }
    }

    private int setHornNubLength(int[] gene) {
        int hornNubLength = 4;
        if (gene[70] == 1 || gene[71] == 1){
            hornNubLength--;
        }

        if (gene[72] == 1 || gene[73] == 1){
            hornNubLength = hornNubLength - 2;
        }else if (gene[72] == 2 || gene[73] == 2){
            hornNubLength--;
        }

        if (gene[74] == 1 || gene[75] == 1) {
            hornNubLength++;
        }
        return hornNubLength;
    }

    private float setHumpPlacement(int[] gene) {
        float humpCalc = 0.0F;
        if (gene[40] != 1 && gene[41] != 1) {
            for (int i = 1; i < gene[40]; i++) {
                humpCalc = humpCalc - 0.5F;
            }
            for (int i = 1; i < gene[41]; i++) {
                humpCalc = humpCalc - 0.5F;
            }
        }
        return humpCalc;
    }

    private void calculateEarRotations(int[] gene) {
        if (gene[46] == 2 && gene[47] == 2) {
            float earSize = 0;
            for (int i = 1; i < gene[42]; i++) {
                earSize = earSize + 1.0F;
            }

            for (int i = 1; i < gene[43]; i++) {
                earSize = earSize + 1.0F;
            }

            if (gene[44] == 1 || gene[45] == 1) {
                earSize = earSize - earSize / 3.0F;
            }

            float floppiness = (earSize / 6.25F) + 0.2F;

            this.averageEars = true;
            this.earFloppiness = floppiness;
            this.earSize = earSize;

        } else {
            float floppiness = 0.9F;
            float earSize = 0;

            for (int i = 1; i < gene[42]; i++) {
                floppiness = floppiness + 0.16F;
                earSize = earSize + 1.0F;
            }

            for (int i = 1; i < gene[43]; i++) {
                floppiness = floppiness + 0.16F;
                earSize = earSize + 1.0F;
            }

            if (gene[44] == 1 || gene[45] == 1) {
                floppiness = floppiness - floppiness / 3.0F;
                earSize = earSize - earSize / 3.0F;
            }

            for (int i = 1; i < gene[46]; i++) {
                floppiness = floppiness + 0.1F;
            }
            for (int i = 1; i < gene[47]; i++) {
                floppiness = floppiness + 0.1F;
            }

            this.averageEars = false;
            this.earFloppiness = floppiness;
            this.earSize = earSize;
        }
    }

}
