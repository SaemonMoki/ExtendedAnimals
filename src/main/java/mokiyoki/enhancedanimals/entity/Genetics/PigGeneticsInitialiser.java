package mokiyoki.enhancedanimals.entity.Genetics;

import mokiyoki.enhancedanimals.config.EanimodCommonConfig;
import mokiyoki.enhancedanimals.init.PigBreeds;
import mokiyoki.enhancedanimals.util.Breed;
import mokiyoki.enhancedanimals.util.Genes;
import mokiyoki.enhancedanimals.util.Reference;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class PigGeneticsInitialiser extends AbstractGeneticsInitialiser {
    List<Breed> breeds = new ArrayList<>();

    public PigGeneticsInitialiser() {
        this.breeds.add(PigBreeds.LARGEWHITE);
    }

    public Genes generateNewGenetics(IWorld world, BlockPos pos, boolean generateBreed) {
        return super.generateNewGenetics(world, pos, generateBreed, this.breeds);
    }

    public Genes generateWithBreed(IWorld world, BlockPos pos, String breed) {
        return super.generateWithBreed(world, pos, this.breeds, breed);
    }

    @Override
    public Genes generateLocalWildGenetics(Biome biome, boolean isFlat) {
        int[] autosomalGenes = new int[Reference.PIG_AUTOSOMAL_GENES_LENGTH];

        int wildType = 2;
        if (biome.getCategory().equals(Biome.Category.PLAINS)) {
            wildType = 1;
        }
        if (isFlat) {
            int randomizeWT = ThreadLocalRandom.current().nextInt(4);
            if (randomizeWT <= 2) {
                wildType = randomizeWT;
            }
        }

        /**
         * MC1R - Extension
         *  E^D1 Dominant Black (European Large Black and Chinese Meishan)
         *  E^D2 Dominant Black (Hampshire)
         *  E+ WildType (Wild boar)
         *  E^p Brindle (Large White, Pietrain, ?Oldspot?) Black spot pattern, Makes black spots on white or red ground colour
         *  e Red (Duroc, all red breeds) Disables black pigment creation
         */

        //Extension [ Dom.Black(MCR1), Dominant.Black(MCR2), Wildtype+, brindle(spots), red ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[0] = (ThreadLocalRandom.current().nextInt(4) + 1);

        } else {
            autosomalGenes[0] = (2);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[1] = (ThreadLocalRandom.current().nextInt(4) + 1);

        } else {
            autosomalGenes[1] = (2);
        }

        //Agouti [ Black Enhancer, brown, wildtype? ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[2] = (ThreadLocalRandom.current().nextInt(1) + 1);

        } else {
            autosomalGenes[2] = (3);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[3] = (ThreadLocalRandom.current().nextInt(1) + 1);

        } else {
            autosomalGenes[3] = (3);
        }

        //Chinchilla [ chinchilla, wildtype ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[4] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            autosomalGenes[4] = (2);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[5] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            autosomalGenes[5] = (2);
        }

        //Subtle Dilute [ Wildtype+, dilute ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[6] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            autosomalGenes[6] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[7] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            autosomalGenes[7] = (1);
        }

        //Blue Dilute [ Dilute, wildtype+ ]
//            if (ThreadLocalRandom.current().nextInt(100) > WTC) {
////                autosomalGenes[8] = (ThreadLocalRandom.current().nextInt(2) + 1);
//
//            } else {
        autosomalGenes[8] = (1);
//            }
//            if (ThreadLocalRandom.current().nextInt(100) > WTC) {
////                autosomalGenes[9] = (ThreadLocalRandom.current().nextInt(2) + 1);
//
//            } else {
        autosomalGenes[9] = (1);
//            }

        //this one will combo with agouti I guess
        //White Spots [ Wildtype+, spotted, roanSpots ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[10] = (ThreadLocalRandom.current().nextInt(3) + 1);

        } else {
            autosomalGenes[10] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[11] = (ThreadLocalRandom.current().nextInt(3) + 1);

        } else {
            autosomalGenes[11] = (1);
        }

        //Dom.White and Belted [ Dom.White, Belted, wildtype+, Patch, Roan]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[12] = (ThreadLocalRandom.current().nextInt(5) + 1);
            if (wildType == 1) {
                autosomalGenes[13] = (1);
            } else {
                autosomalGenes[13] = (3);
            }

        } else {
            if (wildType == 1) {
                autosomalGenes[12] = (1);
                autosomalGenes[13] = (1);
            } else {
                autosomalGenes[12] = (3);
                autosomalGenes[13] = (3);
            }
        }

        //Berkshire spots [ Wildtype+, tuxedo, berkshire ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[14] = (ThreadLocalRandom.current().nextInt(3) + 1);
            autosomalGenes[15] = (1);

        } else {
            autosomalGenes[14] = (1);
            autosomalGenes[15] = (1);
        }

        //White Extension [ Over marked, Medium, undermarked+ ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[16] = (ThreadLocalRandom.current().nextInt(3) + 1);

        } else {
            autosomalGenes[16] = (3);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[17] = (ThreadLocalRandom.current().nextInt(3) + 1);

        } else {
            autosomalGenes[17] = (3);
        }

        //face squash genes 1 [ Wildtype+, long, medium, short, squashed ]
        if (wildType == 1 || ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[18] = (ThreadLocalRandom.current().nextInt(5) + 1);

        } else {
            autosomalGenes[18] = (1);
        }
        if (wildType == 1 || ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[19] = (ThreadLocalRandom.current().nextInt(5) + 1);

        } else {
            autosomalGenes[19] = (1);
        }

        //inbreeding detector A
        autosomalGenes[20] = (ThreadLocalRandom.current().nextInt(20) + 1);
        autosomalGenes[21] = (ThreadLocalRandom.current().nextInt(20) + 20);

        //inbreeding detector B
        autosomalGenes[22] = (ThreadLocalRandom.current().nextInt(20) + 1);
        autosomalGenes[23] = (ThreadLocalRandom.current().nextInt(20) + 20);

        //inbreeding detector C
        autosomalGenes[24] = (ThreadLocalRandom.current().nextInt(20) + 1);
        autosomalGenes[25] = (ThreadLocalRandom.current().nextInt(20) + 20);

        //inbreeding detector D
        autosomalGenes[26] = (ThreadLocalRandom.current().nextInt(20) + 1);
        autosomalGenes[27] = (ThreadLocalRandom.current().nextInt(20) + 20);

        //inbreeding detector E
        autosomalGenes[28] = (ThreadLocalRandom.current().nextInt(20) + 1);
        autosomalGenes[29] = (ThreadLocalRandom.current().nextInt(20) + 20);

        //inbreeding detector F
        autosomalGenes[30] = (ThreadLocalRandom.current().nextInt(20) + 1);
        autosomalGenes[31] = (ThreadLocalRandom.current().nextInt(20) + 20);


        //Waddles [ waddles, wildtype ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[32] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            autosomalGenes[32] = (2);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[33] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            autosomalGenes[33] = (2);
        }

        //hair density [ furry, wildtype, sparse ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[34] = (ThreadLocalRandom.current().nextInt(3) + 1);

        } else {
            autosomalGenes[34] = (2);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[35] = (ThreadLocalRandom.current().nextInt(3) + 1);

        } else {
            autosomalGenes[35] = (2);
        }

        //baldness [ Bald, wildtype ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[36] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            if (wildType == 1) {
                autosomalGenes[36] = (1);
            } else {
                autosomalGenes[36] = (2);
            }
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[37] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            autosomalGenes[37] = (2);
        }

        //wooly [ wooly, wildtype ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[38] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            autosomalGenes[38] = (2);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[39] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            autosomalGenes[39] = (2);
        }

        //thick hair [ thick hair+, less hair ]
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

        //face squash genes 2 [ longest, normal, short, shortest]
        if (wildType == 1 || ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[42] = (ThreadLocalRandom.current().nextInt(4) + 1);

        } else {
            autosomalGenes[42] = (1);
        }
        if (wildType == 1 || ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[43] = (ThreadLocalRandom.current().nextInt(4) + 1);

        } else {
            autosomalGenes[43] = (1);
        }

        //potbelly dwarfism [wildtype, dwarfStrong, dwarfWeak]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[44] = (ThreadLocalRandom.current().nextInt(3) + 1);
            autosomalGenes[45] = (1);

        } else {
            autosomalGenes[44] = (1);
            autosomalGenes[45] = (1);
        }

        //potbelly dwarfism2 [wildtype, dwarf]
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

        //size genes reducer [wildtype, smaller smaller smallest...]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[48] = (ThreadLocalRandom.current().nextInt(16) + 1);

        } else {
            autosomalGenes[48] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[49] = (ThreadLocalRandom.current().nextInt(16) + 1);

        } else {
            autosomalGenes[49] = (1);
        }

        //size genes adder [wildtype, bigger bigger biggest...]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[50] = (ThreadLocalRandom.current().nextInt(16) + 1);

        } else {
            autosomalGenes[50] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[51] = (ThreadLocalRandom.current().nextInt(16) + 1);

        } else {
            autosomalGenes[51] = (1);
        }

        //size genes varient1 [wildtype, smaller, smallest]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[52] = (ThreadLocalRandom.current().nextInt(3) + 1);

        } else {
            autosomalGenes[52] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[53] = (ThreadLocalRandom.current().nextInt(3) + 1);

        } else {
            autosomalGenes[53] = (1);
        }

        //size genes varient2 [wildtype, smaller, smallest]
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

        //body type [wildtype(middle), smallest to largest]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[56] = (ThreadLocalRandom.current().nextInt(7) + 1);

        } else {
            autosomalGenes[56] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[57] = (ThreadLocalRandom.current().nextInt(7) + 1);

        } else {
            autosomalGenes[57] = (1);
        }

        //litter size reduction [wildtype (half), weak reduction (2/3), prolific]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[58] = (ThreadLocalRandom.current().nextInt(3) + 1);

        } else {
            autosomalGenes[58] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[59] = (ThreadLocalRandom.current().nextInt(3) + 1);

        } else {
            autosomalGenes[59] = (1);
        }

        //ear size [xsmall, small, medium, large, xlarge]

        //ear angle [up, medium, down

        return new Genes(autosomalGenes);
    }
}
