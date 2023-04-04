package mokiyoki.enhancedanimals.model.modeldata;

import mokiyoki.enhancedanimals.util.Genes;

import mokiyoki.enhancedanimals.model.modeldata.ChickenPhenotypeEnums.Crested;
import mokiyoki.enhancedanimals.model.modeldata.ChickenPhenotypeEnums.FootFeathers;
import mokiyoki.enhancedanimals.model.modeldata.ChickenPhenotypeEnums.Comb;
import mokiyoki.enhancedanimals.model.modeldata.ChickenPhenotypeEnums.Beard;

public class ChickenPhenotype implements Phenotype {
    public Crested crestType = Crested.NONE;
    public FootFeathers footFeatherType = FootFeathers.NONE;
    public Comb comb = Comb.SINGLE;
    public Beard beard;
    public boolean butterCup = false;
    public boolean isVultureHocked;
    public boolean isNakedNeck;
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
    public float bodyAngle;
    public float tailAngle = 0;
    public float height;

    private boolean silkie;

    public ChickenPhenotype(Genes genes) {
        int[] gene = genes.getAutosomalGenes();
        this.isNakedNeck = gene[52] == 1 || gene[53] == 1;
        this.rumpless = gene[72] == 2 || gene[73] == 2;
        this.earTufts = gene[150] == 2 || gene[151] == 2;
        this.isVultureHocked = gene[102] == 2 && gene[103] == 2;
        this.creeper = gene[70] == 2 || gene[71] == 2;

        if (gene[56] == 1 || gene[57] == 1) {
            this.beard = this.isNakedNeck ? Beard.NN_BEARD : Beard.BIG_BEARD;
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
            this.combSize = this.combSize + 1;
        }
        if (gene[81] == 2) {
            this.combSize = this.combSize + 1;
        }
        if (gene[82] == 1) {
            this.combSize = this.combSize + 1;
        }
        if (gene[83] == 1) {
            this.combSize = this.combSize + 1;
        }

        if (this.combSize < 0) {
            this.combSize = 0;
        }

        this.waddleSize = this.combSize;

        if (gene[84] == 1 && gene[85] == 1 && this.waddleSize > 0) {
            this.waddleSize = this.waddleSize - 1;
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

        //      wingAngle  [ 0 to 1.5 ]
        if (gene[88] == 2) {
            this.wingAngle = this.wingAngle + 0.1F;
        } else if (gene[88] == 3) {
            this.wingAngle = this.wingAngle + 0.15F;
        }
        if (gene[89] == 2) {
            this.wingAngle = this.wingAngle + 0.1F;
        } else if (gene[89] == 3) {
            this.wingAngle = this.wingAngle + 0.15F;
        }

        if (gene[94] == 2 && gene[95] == 2) {
            this.wingAngle = this.wingAngle * 1.2F;
        } else if (gene[94] == 3 && gene[95] == 3) {
            this.wingAngle = this.wingAngle * 1.5F;
        } else if (gene[94] != 1 && gene[95] != 1) {
            this.wingAngle = this.wingAngle * 1.1F;
        }

        if (gene[96] == 2 && gene[97] == 2) {
            this.wingAngle = this.wingAngle * 1.2F;
        } else if(gene[96] == 3 && gene[97] == 3) {
            this.wingAngle = this.wingAngle * 1.5F;
        } else if(gene[96] != 1 || gene[97] != 1) {
            this.wingAngle = this.wingAngle * 1.1F;
        }

        this.wingAngle = -this.wingAngle;

        float bodyAngle = 0.0F;
        for (int i = 186; i < 196; i+=2) {
            if (gene[i] == 2 && gene[i+1] == 2) {
                bodyAngle -= 0.1F;
            }
        }
        this.bodyAngle = bodyAngle;

        if (this.creeper) {
            this.height = this.hasLongLegs() ? 18.5F : 19.5F;
        } else {
            this.height = this.hasLongLegs() ? 15.5F : 17.5F;
        }

        this.silkie = gene[106] == 1 || gene[107] == 1;
    }

    public boolean isBearded() {
        return this.beard != Beard.NONE;
    }

    public boolean isCombed() { return this.comb != Comb.NONE && this.comb != Comb.BREDA_COMBLESS; }

    public boolean hasLongLegs() { return this.longLegs != 0; }

}
