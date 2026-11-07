package mokiyoki.enhancedanimals.model.modeldata;

import mokiyoki.enhancedanimals.entity.util.Variation;

import static mokiyoki.enhancedanimals.entity.util.VariationKeys.RABBIT_LOP;

public class RabbitPhenotype implements Phenotype {
    public float lopL = 0.0F;
    public float lopR;
    public boolean[] lop;
    public boolean dwarf;

    public LionsMane lionsMane = LionsMane.NONE;

    public RabbitPhenotype(int[] gene, Variation variation) {

        if (gene[36] == 3 && gene[37] == 3) {
            this.lopL = 0.25F;
        } else {
            if (gene[36] == 2) {
                this.lopL = 0.1F;
            }
            if (gene[37] == 2) {
                this.lopL = 0.1F;
            }
        }
        if (gene[38] == 2 && gene[39] == 2) {
            this.lopL *= 4.0F;
        }

        this.lopL += (gene[40] - 1) * 0.1F;
        this.lopL += (gene[41] - 1) * 0.1F;

        if (gene[42] == 3 && gene[43] == 3) {
            this.lopL += 0.1F;
        }

        if (lopL > 1.0F) {
            this.lopL = 1.0F;
        }

        this.lop = getLop(this.lopL, variation.hex(RABBIT_LOP));

        this.lopR = this.lop[1] ? this.lopL : this.lopL*0.75F;
        this.lopL = this.lop[0] ? this.lopL : this.lopL*0.75F;

        this.dwarf = gene[34] == 2 || gene[35] == 2;

        if (gene[24] == 2 || gene[25] == 2) {
            this.lionsMane = gene[24] == gene[25] ? LionsMane.DOUBLE : LionsMane.SINGLE;
        }
    }

    private boolean[] getLop(float val, int lop) {
        if (val > 0.5F) {
            if (val >= 0.75F) {
                return new boolean[] {true, true};
            } else {
                if (lop < 5) {
                    return new boolean[] {true, false};
                } else if (lop < 10) {
                    return new boolean[] {false, true};
                } else {
                    return new boolean[] {true, true};
                }
            }
        }

        return new boolean[] {false, false};
    }
}
