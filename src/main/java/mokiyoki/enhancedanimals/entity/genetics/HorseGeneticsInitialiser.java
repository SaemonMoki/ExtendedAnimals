package mokiyoki.enhancedanimals.entity.genetics;

import mokiyoki.enhancedanimals.init.breeds.HorseBreeds;
import mokiyoki.enhancedanimals.util.Breed;
import mokiyoki.enhancedanimals.util.Genes;
import mokiyoki.enhancedanimals.util.Reference;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.biome.Biome;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class HorseGeneticsInitialiser extends AbstractGeneticsInitialiser{
    List<Breed> breeds = new ArrayList<>();

    public HorseGeneticsInitialiser() {
        this.breeds.add(HorseBreeds.WHITE);
    }

    public Genes generateNewGenetics(LevelAccessor world, BlockPos pos, boolean generateBreed) {
        return super.generateNewGenetics(world, pos, generateBreed, this.breeds);
    }

    public Genes generateWithBreed(LevelAccessor world, BlockPos pos, String breed) {
        return super.generateWithBreed(world, pos, this.breeds, breed);
    }

    @Override
    public Genes generateLocalWildGenetics(Biome biomeHolder, boolean isFlat) {
        int[] autosomalGenes = new int[Reference.HORSE_AUTOSOMAL_GENES_LENGTH];
        Biome biome = biomeHolder;
        //TODO create biome WTC variable [hot and dry biomes, cold biomes ] WTC is neutral biomes "all others"

        /**
         * Colour Genes
         */

        //Health Base genes [ weaker, stronger1, wildtype+, stronger2 ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[0] = (ThreadLocalRandom.current().nextInt(4) + 1);

        } else {
            autosomalGenes[0] = (3);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[1] = (ThreadLocalRandom.current().nextInt(4) + 1);

        } else {
            autosomalGenes[1] = (3);
        }

        //Health Modifier genes [ weaker, stronger1, wildtype+, stronger2 ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[2] = (ThreadLocalRandom.current().nextInt(4) + 1);

        } else {
            autosomalGenes[2] = (3);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[3] = (ThreadLocalRandom.current().nextInt(4) + 1);

        } else {
            autosomalGenes[3] = (3);
        }

        //Speed Base genes [ weaker, stronger1, wildtype+, stronger2 ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[4] = (ThreadLocalRandom.current().nextInt(4) + 1);

        } else {
            autosomalGenes[4] = (3);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[5] = (ThreadLocalRandom.current().nextInt(4) + 1);

        } else {
            autosomalGenes[5] = (3);
        }

        //Speed Modifier genes [ weaker, stronger1, wildtype+, stronger2 ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[6] = (ThreadLocalRandom.current().nextInt(4) + 1);

        } else {
            autosomalGenes[6] = (3);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[7] = (ThreadLocalRandom.current().nextInt(4) + 1);

        } else {
            autosomalGenes[7] = (3);
        }

        //Jump Base genes [ weaker, stronger1, wildtype+, stronger2 ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[8] = (ThreadLocalRandom.current().nextInt(4) + 1);

        } else {
            autosomalGenes[8] = (3);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[9] = (ThreadLocalRandom.current().nextInt(4) + 1);

        } else {
            autosomalGenes[9] = (3);
        }

        //Jump Modifier genes [ weaker, stronger1, wildtype+, stronger2 ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[10] = (ThreadLocalRandom.current().nextInt(4) + 1);

        } else {
            autosomalGenes[10] = (3);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[11] = (ThreadLocalRandom.current().nextInt(4) + 1);

        } else {
            autosomalGenes[11] = (3);
        }

        //Extension [ wildtype+, red ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[12] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            autosomalGenes[12] = (2);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[13] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            autosomalGenes[13] = (2);
        }

        //Agouti [ Wildtype+, Bay2, sealbrown, solid/black ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[14] = (ThreadLocalRandom.current().nextInt(4) + 1);

        } else {
            autosomalGenes[14] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[15] = (ThreadLocalRandom.current().nextInt(4) + 1);

        } else {
            autosomalGenes[15] = (1);
        }

        //Dun [ Dun+, primitive saturated, saturated ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[16] = (ThreadLocalRandom.current().nextInt(3) + 1);

        } else {
            autosomalGenes[16] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[17] = (ThreadLocalRandom.current().nextInt(3) + 1);

        } else {
            autosomalGenes[17] = (1);
        }

        //Dominant White Spotting [ W1-, W2-, W3-, W4-, W5, W6, W7, W8, W9-, W10-, W11-, W12, W13-, W14-, W15, W16, W17-, W18, W19, W20(Sabino2), W21, W22, W23-, W24-, W25-, W26, W27, Sabino1, wildtype+ ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            if (ThreadLocalRandom.current().nextInt(100) > 99) {
                autosomalGenes[18] = (ThreadLocalRandom.current().nextInt(29) + 1);
            }else{
                autosomalGenes[18] = (ThreadLocalRandom.current().nextInt(10) + 19);
            }
            autosomalGenes[19] = (29);

        } else {
            autosomalGenes[18] = (29);
            autosomalGenes[19] = (29);
        }

        //Roan [ wildtype+, Roan ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[20] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            autosomalGenes[20] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[21] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            autosomalGenes[21] = (1);
        }

        //Cream [ Wildtype+, Cream, pearl ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[22] = (ThreadLocalRandom.current().nextInt(3) + 1);

        } else {
            autosomalGenes[22] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[23] = (ThreadLocalRandom.current().nextInt(3) + 1);

        } else {
            autosomalGenes[23] = (1);
        }

        //Champagne [ wildtype+, Champagne ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[24] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            autosomalGenes[24] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[25] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            autosomalGenes[25] = (1);
        }

        //Silver [ wildtype+, Silver ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[26] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            autosomalGenes[26] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[27] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            autosomalGenes[27] = (1);
        }

        //Mushroom [ wildtype+, Mushroom ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[28] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            autosomalGenes[28] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[29] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            autosomalGenes[29] = (1);
        }

        //Grey [ wildtype+, Grey ]  this one turns the coat white over a few years
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[30] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            autosomalGenes[30] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[31] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            autosomalGenes[31] = (1);
        }

        //White Frame Overo [ wildtype+, white frame overo- ]  this is lethal in double dose
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[32] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            autosomalGenes[32] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[33] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            autosomalGenes[33] = (1);
        }

        //Tobiano [ wildtype+, Tobiano ] pinto paint like spots
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[34] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            autosomalGenes[34] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[35] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            autosomalGenes[35] = (1);
        }

        //Leopard Spotting [ wildtype+, Leopard] appaloosa
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[36] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            autosomalGenes[36] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[37] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            autosomalGenes[37] = (1);
        }

        //Appaloosa Pattern 1 [ wildtype+, Modified ] modifier increases coverage to 6 or 10 incomplete dominant
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[38] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            autosomalGenes[38] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[39] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            autosomalGenes[39] = (1);
        }

        //Appaloosa Pattern 2 [ wildtype+, Modified ] modifier increases by approximately 1
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[40] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            autosomalGenes[40] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[41] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            autosomalGenes[41] = (1);
        }

        //Appaloosa Pattern 3 [ wildtype+, Modified ] modifier increases by approximately 1
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[42] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            autosomalGenes[42] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[43] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            autosomalGenes[43] = (1);
        }


        //Appaloosa Pattern 4 [ wildtype+, Modified ] modifier increases by approximately 1
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[44] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            autosomalGenes[44] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[45] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            autosomalGenes[45] = (1);
        }

        //Appaloosa Pattern 5 [ wildtype+, Modified ] modifier increases by approximately 1
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[46] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            autosomalGenes[46] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[47] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            autosomalGenes[47] = (1);
        }

        //Appaloosa Varnish Roan [ wildtype+, Varnished ] modifier adds varnishing to appoloosa spots
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[48] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            autosomalGenes[48] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[49] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            autosomalGenes[49] = (1);
        }

        //Splash White 1 [ wildtype+, Classic splash white1, splash white3-  ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[50] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            autosomalGenes[50] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[51] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            autosomalGenes[51] = (1);
        }

        //Splash White 2 [ wildtype+, splash white2, splash white4  ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[52] = (ThreadLocalRandom.current().nextInt(4) + 1);

        } else {
            autosomalGenes[52] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[53] = (ThreadLocalRandom.current().nextInt(4) + 1);

        } else {
            autosomalGenes[53] = (1);
        }

        //Tiger Eye [ Wildtype+, te1, te2 ] te1 = lightens eyes to yellow/amber/orange, te2 = lightens eyes to blue in cream horses
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[54] = (ThreadLocalRandom.current().nextInt(3) + 1);

        } else {
            autosomalGenes[54] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[55] = (ThreadLocalRandom.current().nextInt(3) + 1);

        } else {
            autosomalGenes[55] = (1);
        }

        //Flaxen [ Wildtype+, flaxen ] causes flaxen mane and tail in chestnut horses
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[56] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            autosomalGenes[56] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[57] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            autosomalGenes[57] = (1);
        }

        //Flaxen Inhibitor [ darker mane and tail+, Non Inhibitor ] non inhibitor causes slightly lighter mane and tail in chestnut horses
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[58] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            autosomalGenes[58] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[59] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            autosomalGenes[59] = (1);
        }

        //Mealy or Pangaré [ Mealy+, non mealy ] near dominant
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[60] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            autosomalGenes[60] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[61] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            autosomalGenes[61] = (1);
        }

        //Sooty 1 [ wildtype+, Sooty ] incomplete dominant
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[62] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            autosomalGenes[62] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[63] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            autosomalGenes[63] = (1);
        }

        //Sooty 2 [ wildtype+, Sooty2 ] incomplete dominant
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[64] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            autosomalGenes[64] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[65] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            autosomalGenes[65] = (1);
        }

        //Sooty 3 [ wildtype+, sooty3 ] recessive
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[66] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            autosomalGenes[66] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[67] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            autosomalGenes[67] = (1);
        }

        //Sooty 4 [ wildtype+, sooty4 ] recessive
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[68] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            autosomalGenes[68] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[69] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            autosomalGenes[69] = (1);
        }

        //Liver or Dark Chestnut [ wildtype+, dark ] recessive
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[70] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            autosomalGenes[70] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[71] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            autosomalGenes[71] = (1);
        }

        return new Genes(autosomalGenes);
    }
}
