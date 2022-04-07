package mokiyoki.enhancedanimals.entity.genetics;

import mokiyoki.enhancedanimals.init.breeds.RabbitBreeds;
import mokiyoki.enhancedanimals.util.Breed;
import mokiyoki.enhancedanimals.util.Genes;
import mokiyoki.enhancedanimals.util.Reference;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.biome.Biome;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class RabbitGeneticsInitialiser extends AbstractGeneticsInitialiser {
    List<Breed> breeds = new ArrayList<>();

    public RabbitGeneticsInitialiser() {
        this.breeds.add(RabbitBreeds.ENGLISH_ANGORA);
        this.breeds.add(RabbitBreeds.FRENCH_ANGORA);
        this.breeds.add(RabbitBreeds.LOP);
        this.breeds.add(RabbitBreeds.MINI_LOP);
        this.breeds.add(RabbitBreeds.LIONHEAD);
        this.breeds.add(RabbitBreeds.DWARF_LIONHEAD);
        this.breeds.add(RabbitBreeds.VIENNA);
        this.breeds.add(RabbitBreeds.NEWZEALAND_WHITE);
        this.breeds.add(RabbitBreeds.FLEMISH_GIANT);
        this.breeds.add(RabbitBreeds.HOTOT);
        this.breeds.add(RabbitBreeds.DUTCH);
        this.breeds.add(RabbitBreeds.HIMALAYAN);
        this.breeds.add(RabbitBreeds.NETHERLAND_DWARF);
        this.breeds.add(RabbitBreeds.RHINELANDER);
        this.breeds.add(RabbitBreeds.HARLEQUIN);
    }

    public Genes generateNewGenetics(LevelAccessor world, BlockPos pos, boolean generateBreed) {
        return super.generateNewGenetics(world, pos, generateBreed, this.breeds);
    }

    public Genes generateWithBreed(LevelAccessor world, BlockPos pos, String breed) {
        return super.generateWithBreed(world, pos, this.breeds, breed);
    }

    @Override
    public Genes generateLocalWildGenetics(Biome biome, boolean isFlat) {
        int[] autosomalGenes = new int[Reference.RABBIT_AUTOSOMAL_GENES_LENGTH];

        //[ 0=forest wildtype, 1=cold wildtype, 2=desert wildtype, 3=extreme cold ]
        int wildType = 0;
        if (biome.getBaseTemperature() < 0.5F){
            if (biome.getBaseTemperature() <= 0.05F){
                wildType  = 3;
            } else {
                wildType = 1;
            }
        } else if (biome.getBaseTemperature() > 0.8F) {
            wildType = 2;
        }
        if (isFlat) {
            int randomizeWT = ThreadLocalRandom.current().nextInt(9);
            if (randomizeWT <= 3) {
                wildType = randomizeWT;
            }
        }

        /**
         * Colour Genes
         */

        //Agouti [ Agouti+, Tan, Self ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[0] = (ThreadLocalRandom.current().nextInt(3) + 1);
        } else {
            autosomalGenes[0] = 1;
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[1] = (ThreadLocalRandom.current().nextInt(3) + 1);
        } else {
            autosomalGenes[1] = 1;
        }

        //Brown/Chocolate [ wildtype, brown ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[2] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            autosomalGenes[2] = 1;
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[3] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            autosomalGenes[3] = 1;
        }

        //Colour Completion [ Wildtype+, Dark Chinchilla, Light Chinchilla, Himalayan, Albino ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            if (wildType == 0){
                autosomalGenes[4] = (ThreadLocalRandom.current().nextInt(5) + 1);
                autosomalGenes[5] = (ThreadLocalRandom.current().nextInt(5) + 1);
            }else if (wildType == 1 || wildType == 3){
                autosomalGenes[4] = (ThreadLocalRandom.current().nextInt(3) + 3);
                autosomalGenes[5] = (ThreadLocalRandom.current().nextInt(3) + 2);
            }else{
                autosomalGenes[4] = (ThreadLocalRandom.current().nextInt(5) + 1);
            }
        } else {
            if (wildType == 0){
                autosomalGenes[4] = 1;
                autosomalGenes[5] = 1;
            }else if (wildType == 1 || wildType == 3) {
                autosomalGenes[4] = 2;
                autosomalGenes[5] = 2;
            }else{
                autosomalGenes[4] = 1;
            }
        }
        if (wildType == 2) {
            if (ThreadLocalRandom.current().nextInt(100) > WTC) {
                autosomalGenes[5] = (ThreadLocalRandom.current().nextInt(5) + 1);
            } else {
                autosomalGenes[5] = 1;
            }
        }

        //Dilute [ wildtype, dilute ]
        if (wildType == 1) {
            if (ThreadLocalRandom.current().nextInt(100) > WTC/2) {
                autosomalGenes[6] = (ThreadLocalRandom.current().nextInt(2) + 1);

            } else {
                autosomalGenes[6] = 1;
            }
            if (ThreadLocalRandom.current().nextInt(100) > WTC/2) {
                autosomalGenes[7] = (ThreadLocalRandom.current().nextInt(2) + 1);

            } else {
                autosomalGenes[7] = 1;
            }
        }else {
            if (ThreadLocalRandom.current().nextInt(100) > WTC) {
                autosomalGenes[6] = (ThreadLocalRandom.current().nextInt(2) + 1);

            } else {
                autosomalGenes[6] = 1;
            }
            if (ThreadLocalRandom.current().nextInt(100) > WTC) {
                autosomalGenes[7] = (ThreadLocalRandom.current().nextInt(2) + 1);

            } else {
                autosomalGenes[7] = 1;
            }
        }

        //E Locus [ Steel, Wildtype, Brindle, Non Extension ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[8] = (ThreadLocalRandom.current().nextInt(4) + 1);

        } else {
            if (wildType == 0){
                autosomalGenes[8] = 3;
            }else if(wildType == 3) {
                autosomalGenes[8] = 4;
            }else {
                autosomalGenes[8] = 2;
            }

        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[9] = (ThreadLocalRandom.current().nextInt(4) + 1);

        } else {
            if (wildType == 3){
                autosomalGenes[9] = 4;
            }else{
                autosomalGenes[9] = 2;
            }

        }

        //Spotted [ wildtype, spotted ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[10] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            autosomalGenes[10] = 1;
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[11] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            autosomalGenes[11] = 1;
        }

        //Dutch [ wildtype, dutch]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[12] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            autosomalGenes[12] = 1;
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[13] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            autosomalGenes[13] = 1;
        }

        //Vienna [ wildtype, vienna]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[14] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            autosomalGenes[14] = 1;
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[15] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            autosomalGenes[15] = 1;
        }

        //Wideband [ wildtype, wideband]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[16] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            autosomalGenes[16] = 1;
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[17] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            autosomalGenes[17] = 1;
        }

        //Silver [ wildtype, silver]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[18] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            autosomalGenes[18] = 1;
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[19] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            autosomalGenes[19] = 1;
        }

        //Lutino [ wildtype, lutino]
        if (wildType == 2){
            if (ThreadLocalRandom.current().nextInt(100) > WTC/3) {
                autosomalGenes[20] = (ThreadLocalRandom.current().nextInt(2) + 1);

            } else {
                autosomalGenes[20] = 2;
            }
            if (ThreadLocalRandom.current().nextInt(100) > WTC) {
                autosomalGenes[21] = (ThreadLocalRandom.current().nextInt(2) + 1);

            } else {
                autosomalGenes[21] = 2;
            }
        }else {
            if (ThreadLocalRandom.current().nextInt(100) > WTC) {
                autosomalGenes[20] = (ThreadLocalRandom.current().nextInt(2) + 1);

            } else {
                autosomalGenes[20] = 1;
            }
            if (ThreadLocalRandom.current().nextInt(100) > WTC) {
                autosomalGenes[21] = (ThreadLocalRandom.current().nextInt(2) + 1);

            } else {
                autosomalGenes[21] = 1;
            }
        }

        /**
         * Coat Genes
         */

        //Furless [ wildtype, furless]
//        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
//            autosomalGenes[22] = (ThreadLocalRandom.current().nextInt(2) + 1);
//
//        } else {
            autosomalGenes[22] = 1;
//        }
//        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
//            autosomalGenes[23] = (ThreadLocalRandom.current().nextInt(2) + 1);
//
//        } else {
            autosomalGenes[23] = 1;
//        }

        //Lion Mane [ wildtype, lion mane]
        if (wildType == 1 || wildType == 3 || ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[24] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            autosomalGenes[24] = 1;
        }
        if (wildType == 3 || ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[25] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            autosomalGenes[25] = 1;
        }

        //Angora [ wildtype, angora]
        if (ThreadLocalRandom.current().nextInt(100) > WTC || wildType == 3) {
            autosomalGenes[26] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
                autosomalGenes[26] = 1;
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC || wildType == 3) {
            autosomalGenes[27] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
                autosomalGenes[27] = 1;
        }

        //Rex [ wildtype, rex]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[28] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            autosomalGenes[28] = 1;
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[29] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            autosomalGenes[29] = 1;
        }

        //Satin [ wildtype, satin]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[30] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            autosomalGenes[30] = 1;
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[31] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            autosomalGenes[31] = 1;
        }

        //Waved [ wildtype, waved]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[32] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            autosomalGenes[32] = 1;
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[33] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            autosomalGenes[33] = 1;
        }

        /**
         * Shape and Size Genes
         */

        //Dwarf [ wildtype, dwarf]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[34] = (ThreadLocalRandom.current().nextInt(2) + 1);
            autosomalGenes[35] = 1;
        } else {
            autosomalGenes[34] = 1;
            autosomalGenes[35] = 1;
        }

        //Lop1 [ wildtype, halflop, lop1]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[36] = (ThreadLocalRandom.current().nextInt(3) + 1);

        } else {
            autosomalGenes[36] = 1;
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[37] = (ThreadLocalRandom.current().nextInt(3) + 1);

        } else {
            autosomalGenes[37] = 1;
        }

        //Lop2 [ wildtype, lop2]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[38] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            autosomalGenes[38] = 1;
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[39] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            autosomalGenes[39] = 1;
        }

        //long ears [ wildtype, longer ears, longest ears]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[40] = (ThreadLocalRandom.current().nextInt(3) + 1);

        } else {
            autosomalGenes[40] = 1;
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[41] = (ThreadLocalRandom.current().nextInt(3) + 1);

        } else {
            autosomalGenes[41] = 1;
        }

        //ear length bias [ normal ears, shorter ears, longest ears ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[42] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            autosomalGenes[42] = 1;
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[43] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            autosomalGenes[43] = 1;
        }

        //longer body [ normal length, longer body ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[44] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            autosomalGenes[44] = 1;
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[45] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            autosomalGenes[45] = 1;
        }

        //Size tendency [ small, normal, small2, big, extra large ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[46] = (ThreadLocalRandom.current().nextInt(5) + 1);

        } else {
            autosomalGenes[46] = 2;
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[47] = (ThreadLocalRandom.current().nextInt(5) + 1);

        } else {
            autosomalGenes[47] = 2;
        }

        //Size Enhancer [ big, normal, small ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[48] = (ThreadLocalRandom.current().nextInt(3) + 1);

        } else {
            autosomalGenes[48] = 2;
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[49] = (ThreadLocalRandom.current().nextInt(3) + 1);

        } else {
            autosomalGenes[49] = 2;
        }

        //Fur Length Enhancer 1 [ normal, longer, normal]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[50] = (ThreadLocalRandom.current().nextInt(3) + 1);

        } else {
            autosomalGenes[50] = 1;
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[51] = (ThreadLocalRandom.current().nextInt(3) + 1);

        } else {
            autosomalGenes[51] = 1;
        }

        //Fur Length Enhancer 2 [ normal, longer, longest]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[52] = (ThreadLocalRandom.current().nextInt(3) + 1);

        } else {
            autosomalGenes[52] = 1;
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[53] = (ThreadLocalRandom.current().nextInt(3) + 1);

        } else {
            autosomalGenes[53] = 1;
        }

        //Fur Length Enhancer 3 [ shorter, normal]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[54] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            if (wildType == 1 || wildType == 3){
                autosomalGenes[54] = 2;
            }else {
                autosomalGenes[54] = 1;
            }
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[55] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            if (wildType == 3){
                autosomalGenes[55] = 2;
            }else {
                autosomalGenes[55] = 1;
            }
        }

        //Fertility++
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[56] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            autosomalGenes[56] = 1;
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[57] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            autosomalGenes[57] = 1;
        }

        //Fertility--
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[58] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            autosomalGenes[58] = 1;
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[59] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            autosomalGenes[59] = 1;
        }

        return new Genes(autosomalGenes);
    }
}
