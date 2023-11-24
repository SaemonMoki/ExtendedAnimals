package mokiyoki.enhancedanimals.model.modeldata;

import mokiyoki.enhancedanimals.model.modeldata.ChickenPhenotypeEnums.EarType;
import mokiyoki.enhancedanimals.model.modeldata.ChickenPhenotypeEnums.NakedNeckType;
import mokiyoki.enhancedanimals.util.Genes;

import mokiyoki.enhancedanimals.model.modeldata.ChickenPhenotypeEnums.Crested;
import mokiyoki.enhancedanimals.model.modeldata.ChickenPhenotypeEnums.FootFeathers;
import mokiyoki.enhancedanimals.model.modeldata.ChickenPhenotypeEnums.Comb;
import mokiyoki.enhancedanimals.model.modeldata.ChickenPhenotypeEnums.Beard;

import java.util.concurrent.ThreadLocalRandom;

public class ChickenPhenotype implements Phenotype {
    public Crested crestType = Crested.NONE;
    public FootFeathers footFeatherType = FootFeathers.NONE;
    public EarType ear = EarType.NONE;
    public int earSize = 0;
    public Comb comb = Comb.SINGLE;
    public Beard beard;
    public boolean butterCup = false;
    public boolean isVultureHocked;
    public boolean isScaleless;
    public NakedNeckType nakedNeckType;
    public boolean earTufts;
    public boolean rumpless;
    public boolean longHockFeathers;
    public boolean creeper;
    private int longLegs = 0;
    public int combSize = 0;
    public int waddleSize;
    public int bodyType;
    public int wingSize;
    public float wingPlacement = -5.5F;
    public float wingAngle = 0;
    public float neckAngle;
    public float bodyAngle;
    public float bodyY;
    public float bodyZ;
    public float tailAngle;
    public float height;
    private boolean silkie;
    public float fluffiness;
    public final float neckPoof;
    public float meatiness;

    public ChickenPhenotype(Genes genes, boolean isFemale) {
        int[] gene = genes.getAutosomalGenes();
        this.isScaleless = gene[108] == 2 && gene[109] == 2;
        this.nakedNeckType = gene[52] == 1 || gene[53] == 1 ? (gene[52]==gene[53]?NakedNeckType.NAKED_NECK:NakedNeckType.BOWTIE_NECK):NakedNeckType.NONE;
        this.rumpless = gene[72] == 2 || gene[73] == 2;
        this.earTufts = gene[150] == 2 || gene[151] == 2;
        this.isVultureHocked = gene[102] == 2 && gene[103] == 2;
        this.creeper = gene[70] == 2 || gene[71] == 2;

        if (gene[56] == 1 || gene[57] == 1) {
            this.beard = this.nakedNeckType!=NakedNeckType.NONE ? Beard.NN_BEARD : Beard.BIG_BEARD;
        } else {
            this.beard = Beard.NONE;
        }

        //Crest type
        if (!(gene[54] == 3 && gene[55] == 3)) {
            if (gene[54] == 3 || gene[55] == 3) {
                if (gene[54] == 1 || gene[55] == 1) {
                    this.crestType = Crested.SMALL_CREST;
                } else {
                    this.crestType = Crested.SMALL_FORWARDCREST;
                }
            } else if (gene[54] != gene[55] || (gene[54] == 1 && gene[55] == 1)) {
                this.crestType = Crested.BIG_CREST;
            } else {
                this.crestType = Crested.BIG_FORWARDCREST;
            }
        }

        //Foot Feather Type
        if (!(gene[60] == 3 && gene[61] == 3)) {
            if (gene[60] == 1 || gene[61] == 1) {
                if (gene[58] == 2 && gene[59] == 2) {
                    this.footFeatherType = FootFeathers.BIG_TOEFEATHERS;
                } else if (gene[58] == 2 || gene[59] == 2 || (gene[58] == 1 && gene[59] == 1)) {
                    this.footFeatherType = FootFeathers.TOEFEATHERS;
                } else if (gene[58] == 1 || gene[59] == 1) {
                    this.footFeatherType = FootFeathers.FOOTFEATHERS;
                }
            } else {
                if (gene[58] == 2 && gene[59] == 2) {
                    this.footFeatherType = FootFeathers.TOEFEATHERS;
                } else if (gene[58] == 2 || gene[59] == 2 || (gene[58] == 1 && gene[59] == 1)) {
                    this.footFeatherType = FootFeathers.FOOTFEATHERS;
                } else if (gene[58] == 1 || gene[59] == 1) {
                    this.footFeatherType = FootFeathers.LEGFEATHERS;
                }
            }
        }

        if ((gene[50] == 2 || gene[51] == 2) && !(gene[50] == 1 || gene[51] == 1)) {
            if (gene[46] == 3 && gene[47] == 3 && gene[48] == 2 && gene[49] == 2) {
                //v comb
                this.comb = Comb.V;
            } else {
                if (gene[48] == 2 && gene[49] == 2) {
                    //only waddles
                    this.comb = Comb.NONE;
                }
            }
        } else {
            if (gene[48] == 1 || gene[49] == 1) {
                if (gene[46] == 3 && gene[47] == 3) {
                    //peacomb
                    this.comb = Comb.PEA;
                } else {
                    //walnut
                    this.comb = Comb.WALNUT;
                }
            } else {
                if (gene[46] == 3 && gene[47] == 3) {
                    //single comb
                    this.comb = Comb.SINGLE;
                } else if (gene[46] == 1 || gene[47] == 1) {
                    //rose comb
//                        if (gene[46] == 3 || gene[47] == 3) {
//                            this.comb = Comb.HET_ROSE_ONE;
//                        } else {
                    this.comb = Comb.ROSE_ONE;
//                        }
                } else {
                    //rose comb2
//                        if (gene[46] == 3 || gene[47] == 3) {
//                            this.comb = Comb.HET_ROSE_TWO;
//                        } else {
                    this.comb = Comb.ROSE_TWO;
//                        }
                }
            }

            if (gene[50] == 2 || gene[51] == 2) {
                this.butterCup = true;
                this.combSize = -1;
            } else {
                this.butterCup = gene[50] == 3 || gene[51] == 3;
            }

        }

        if ((gene[86] == 1 && gene[87] == 1) || (gene[86] == 3 && gene[87] == 3)){
            wingPlacement = -4.5F;
        } else if (gene[86] == 1 || gene[87] == 1){
            wingPlacement = -5.0F;
        }

        //comb size [80 and 81 small / 82 and 83 large]
        if (gene[80] == 2) {
            this.combSize++;
        }
        if (gene[81] == 2) {
            this.combSize++;
        }
        if (gene[82] == 1) {
            this.combSize++;
        }
        if (gene[83] == 1) {
            this.combSize++;
        }

        if (this.combSize < 0) this.combSize = 0;

        this.waddleSize = this.combSize;

        if (gene[84] == 1 && gene[85] == 1 && this.waddleSize > 0) {
            this.waddleSize = this.waddleSize - 1;
        }

        int[] sGene = genes.getSexlinkedGenes();
        int ear = this.combSize;

        ear+=isFemale?sGene[4]-1:Math.min(sGene[4],sGene[5]);

        if (gene[152] <= 4 || gene[153] <= 4) {
            ear--;
        } else if (gene[152] > 8 && gene[153] > 8) {
            ear++;
        }

        if (gene[154] <= 4 || gene[155] <= 4) {
            if (ear!=-1) ear--;
        } else if (gene[154] > 8 && gene[155] > 8) {
            ear++;
        }

        if (gene[156] <= 4 || gene[157] <= 4) {
            if (ear!=-1) ear--;
        } else if (gene[156] > 5 && gene[157] > 5) {
            ear++;
            if (gene[156]==gene[157] && gene[156] >= 10) {
                ear++;
            }
        }

        if (gene[158] == 1 || gene[159] == 1) {
            ear--;
        } else if (gene[158] > 2 && gene[159] > 2) {
            if (gene[158] == 3 || gene[159] == 3) {
                ear++;
            } else if (gene[158] == 5 && gene[159] == 5) {
                ear++;
            }
        }

        if (gene[160] <= 4 || gene[161] <= 4) {
            if (ear!=-1) ear--;
        } else if (gene[160] > 5 && gene[161] > 5) {
            ear++;
            if (gene[160]==gene[161] && gene[160] >= 10) {
                ear++;
            }
        }

        if (gene[162] <= 4 || gene[163] <= 4) {
            if (ear!=-1) ear--;
        } else if (gene[162] > 5 && gene[163] > 5) {
            ear++;
            if (gene[162]==gene[163] && gene[162] >= 10) {
                ear++;
            }
        }

        if (sGene[18]!=1 && (isFemale || sGene[19]!=1)) {
            earSize*= sGene[18] == 2 && (isFemale || sGene[19] == 2) ? 0.75 : 0.5F;
        }

        if (ear>=0) {
            if (ear>15) ear=15;
            this.earSize = ear;
            ear = 0;
            for (int i = 152; i < 163; i++) {
                if (i < 158 || i > 159) ear+= gene[i] % 2 == 0 ? 1 : -1;
            }
            this.ear = ear>0 ? EarType.ROUND : EarType.LONG;
        }

        if (gene[146] == 2 && gene[147] == 2) {
            if (gene[148] == 2 && gene[149] == 2) {
                //normal body
                this.bodyType = 0;
            } else {
                //big body
                this.bodyType = 1;
            }
        } else if (gene[148] == 2 && gene[149] == 2) {
            if (gene[146] == 2 || gene[147] == 2) {
                //normal body
                this.bodyType = 0;
            } else {
                //small body
                this.longHockFeathers = true;
                this.bodyType = -1;
            }
        } else {
            //normal body
            this.bodyType = 0;
        }

        if (gene[90] == 1 || gene[91] == 1) {
            this.wingSize = 2;
        } else if (gene[92] == 1 || gene[93] == 1) {
            this.wingSize = 1;
        }

        if (gene[168] == 2) {
            this.longLegs++;
        }
        if (gene[169] == 2) {
            this.longLegs++;
        }

        this.wingAngle = getWingAngle(gene);

        this.bodyAngle = getBodyAngle(gene);
        this.bodyY = this.bodyAngle * 2.9F;
        this.bodyZ = this.bodyAngle;

        this.neckAngle = getNeckAngle(gene);

        if (this.creeper) {
            this.height = this.hasLongLegs() ? 18.5F : 19.5F;
        } else {
            this.height = this.hasLongLegs() ? 15.5F : 17.5F;
        }

        this.silkie = gene[106] == 1 || gene[107] == 1;

        this.fluffiness = getFluffiness(gene);

        this.neckPoof = getNeckPoof(gene);

        this.meatiness = getMeatiness(gene);

        this.tailAngle = getTailAngle(gene);
    }

    private static float getBodyAngle(int[] gene) {
        float bodyAngle = 1.5F;
        for (int i = 186; i < 196; i+=2) {
            if (gene[i] == 2 && gene[i+1] == 2) {
                bodyAngle -= 0.3F;
            }
        }

        for (int i = 248; i<258; i++) {
            if (gene[i]==2) bodyAngle *= 0.9F;
        }

        return -1.5F+bodyAngle;
    }

    private static float getNeckAngle(int[] gene) {
        float neckAngle = 1.0F;

        for (int i = 10; i<20; i++) {
            if (gene[i]==2) neckAngle *= 0.8F;
        }
        return neckAngle;
    }

    private static float getWingAngle(int[] gene) {
        //      wingAngle  [ 0 to 1.5 ]
        float wingAngle = 0.0F;
        if (gene[88] == 2) {
            wingAngle = wingAngle + 0.1F;
        } else if (gene[88] == 3) {
            wingAngle = wingAngle + 0.15F;
        }
        if (gene[89] == 2) {
            wingAngle = wingAngle + 0.1F;
        } else if (gene[89] == 3) {
            wingAngle = wingAngle + 0.15F;
        }

        if (gene[94] == 2 && gene[95] == 2) {
            wingAngle = wingAngle * 1.2F;
        } else if (gene[94] == 3 && gene[95] == 3) {
            wingAngle = wingAngle * 1.5F;
        } else if (gene[94] != 1 && gene[95] != 1) {
            wingAngle = wingAngle * 1.1F;
        }

        if (gene[96] == 2 && gene[97] == 2) {
            wingAngle = wingAngle * 1.2F;
        } else if(gene[96] == 3 && gene[97] == 3) {
            wingAngle = wingAngle * 1.5F;
        } else if(gene[96] != 1 || gene[97] != 1) {
            wingAngle = wingAngle * 1.1F;
        }
        return -wingAngle;
    }

    private static float getTailAngle(int[] gene) {
        float tailAngle = 0.5F;
        for (int i = 258; i<268; i++) {
            if (gene[i]==2) tailAngle -= 0.05F;
        }

        for (int i = 268; i<278; i++) {
            if (gene[i]==2) tailAngle += 0.05F;
        }
        return tailAngle;
    }

    private static float getFluffiness(int[] gene) {
        float fluffiness = 0.0F;
        for (int i = 228; i<248; i++) {
            if (i<235) {
                if (gene[i]==1) fluffiness += 0.1F;
            } else {
                if (gene[i]==2) fluffiness += 0.1F;
            }
        }
        return fluffiness;
    }

    private static float getNeckPoof(int[] gene) {
        float poof = 0.0F;
        if (gene[286]==2 && gene[287]==2) {
            poof+=0.95F;
        }
        for (int i = 288; i<294; i++) {
            if (gene[i]==2) poof+=0.05F;
        }

        return poof;
    }

    private static float getMeatiness(int[] gene) {
        float meatiness = 0.5F;
        if (gene[146] == 2 && gene[147] == 2) {
            if (gene[148] != 2 || gene[149] != 2) {
                //big body
                meatiness = 1.0F;
            }
        } else if (gene[148] == 2 && gene[149] == 2) {
            if (gene[146] != 2 && gene[147] != 2) {
                //small body
                meatiness = 0.0F;
            }
        }

        for (int i = 0; i<10; i++) {
            if (i<5) {
                if (gene[i]==1) meatiness *= 0.93F;
            } else {
                if (gene[i]==2) meatiness *= 0.93F;
            }
        }

        return meatiness;
    }

    public boolean isBearded() {
        return this.beard != Beard.NONE;
    }

    public boolean isCombed() { return this.comb != Comb.NONE && this.comb != Comb.BREDA_COMBLESS; }

    public boolean hasLongLegs() { return this.longLegs != 0; }

}
