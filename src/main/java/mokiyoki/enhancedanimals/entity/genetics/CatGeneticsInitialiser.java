package mokiyoki.enhancedanimals.entity.genetics;

//import mokiyoki.enhancedanimals.init.breeds.CatBreeds;
import mokiyoki.enhancedanimals.init.breeds.CatBreeds;
import mokiyoki.enhancedanimals.util.Breed;
import mokiyoki.enhancedanimals.util.Genes;
import mokiyoki.enhancedanimals.util.Reference;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.biome.Biome;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class CatGeneticsInitialiser extends AbstractGeneticsInitialiser{
    List<Breed> breeds = new ArrayList<>();

    public CatGeneticsInitialiser() {
        this.breeds.add(CatBreeds.BLACK);
        this.breeds.add(CatBreeds.BRITISHSHORTHAIR);
        //this.breeds.add(CatBreeds.Calico);
        //this.breeds.add(CatBreeds.Jellie);
        //this.breeds.add(CatBreeds.Persian);
        //this.breeds.add(CatBreeds.Ragdoll);
        //this.breeds.add(CatBreeds.Red);
        //this.breeds.add(CatBreeds.Siamese);
        //this.breeds.add(CatBreeds.BrownTabby);
        //this.breeds.add(CatBreeds.Tuxedo);
        //this.breeds.add(CatBreeds.White);
        this.breeds.add(CatBreeds.ORIENTALSHORTHAIR);
    }

    public Genes generateNewGenetics(LevelAccessor world, BlockPos pos, boolean generateBreed) {
        return super.generateNewGenetics(world, pos, generateBreed, this.breeds);
    }

    public Genes generateWithBreed(LevelAccessor world, BlockPos pos, String breed) {
        return super.generateWithBreed(world, pos, this.breeds, breed);
    }

    @Override
    public Genes generateLocalWildGenetics(Holder<Biome> biomeHolder, boolean isFlat) {
        int[] sexlinkedGenes = new int[Reference.CAT_SEXLINKED_GENES_LENGTH];
        int[] autosomalGenes = new int[Reference.CAT_AUTOSOMAL_GENES_LENGTH];
        Biome biome = biomeHolder.value();

        /**
         *  SEX LINKED GENES
         */

        // sex linked red [wildtype, red]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            sexlinkedGenes[0] = (ThreadLocalRandom.current().nextInt(2) + 1);
        } else {
            sexlinkedGenes[0] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            sexlinkedGenes[1] = (ThreadLocalRandom.current().nextInt(2) + 1);
        } else {
            sexlinkedGenes[1] = (1);
        }

        /**
         *  AUTOSOMAL GENES
         */

        // Agouti [Tabby+, nonagouti, ALC/charcoal]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[0] = (ThreadLocalRandom.current().nextInt(3) + 1);
        } else {
            autosomalGenes[0] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[1] = (ThreadLocalRandom.current().nextInt(3) + 1);
        } else {
            autosomalGenes[1] = (1);
        }

        // Tabby [Mackerel, classic]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[2] = (ThreadLocalRandom.current().nextInt(2) + 1);
        } else {
            autosomalGenes[2] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[3] = (ThreadLocalRandom.current().nextInt(2) + 1);
        } else {
            autosomalGenes[3] = (1);
        }

        // Eumelanin [Wildtype+, chocolate, cinnamon]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[4] = (ThreadLocalRandom.current().nextInt(3) + 1);
        } else {
            autosomalGenes[4] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[5] = (ThreadLocalRandom.current().nextInt(3) + 1);
        } else {
            autosomalGenes[5] = (1);
        }

        // Extension [Wildtype+, amber, carnelian, russet]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[6] = (ThreadLocalRandom.current().nextInt(4) + 1);
        } else {
            autosomalGenes[6] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[7] = (ThreadLocalRandom.current().nextInt(4) + 1);
        } else {
            autosomalGenes[7] = (1);
        }

        // Ticked [Wildtype+, Ticked]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[8] = (ThreadLocalRandom.current().nextInt(2) + 1);
        } else {
            autosomalGenes[8] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[9] = (ThreadLocalRandom.current().nextInt(2) + 1);
        } else {
            autosomalGenes[9] = (1);
        }

        // Spotted [Wildtype+, Spotted]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[10] = (ThreadLocalRandom.current().nextInt(2) + 1);
        } else {
            autosomalGenes[10] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[11] = (ThreadLocalRandom.current().nextInt(2) + 1);
        } else {
            autosomalGenes[11] = (1);
        }

        //White Markings (KIT?) [wildtype+, White Spotting]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[12] = (ThreadLocalRandom.current().nextInt(2) + 1);
        } else {
            autosomalGenes[12] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[13] = (ThreadLocalRandom.current().nextInt(2) + 1);
        } else {
            autosomalGenes[13] = (1);
        }

        //White Extension [normal, extended]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[14] = (ThreadLocalRandom.current().nextInt(2) + 1);
        } else {
            autosomalGenes[14] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[15] = (ThreadLocalRandom.current().nextInt(2) + 1);
        } else {
            autosomalGenes[15] = (1);
        }

        //White Extension 2 [normal, extended]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[16] = (ThreadLocalRandom.current().nextInt(2) + 1);
        } else {
            autosomalGenes[16] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[17] = (ThreadLocalRandom.current().nextInt(2) + 1);
        } else {
            autosomalGenes[17] = (1);
        }

        //Color Restriction [Wildtype+, colorpoint, sepia, mocha]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[18] = (ThreadLocalRandom.current().nextInt(4) + 1);
        } else {
            autosomalGenes[18] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[19] = (ThreadLocalRandom.current().nextInt(4) + 1);
        } else {
            autosomalGenes[19] = (1);
        }

        // Bengal Modifier
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

        // Roan/Karpati (dominant)
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[22] = (ThreadLocalRandom.current().nextInt(2) + 1);
        } else {
            autosomalGenes[22] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[23] = (ThreadLocalRandom.current().nextInt(2) + 1);
        } else {
            autosomalGenes[23] = (1);
        }

        // Inhibitor/Silver [dominant]
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

        // Manx Bobtail [homo lethal]
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

        // Dilute/Blue [Wildtype, dilute]
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
        // CORIN [Wildtype, sunshine]
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

        // Hairless [Wildtype, sphynx]
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

        // Bengal Modifier Quality [wildtype, Quality+]
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
        // Glitter [wildtype, glitter]
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
        // leaving a gap here for more basic stuff.
        // because i like the basic color genes being towards the beginning

        // M1-5 are recessive and are all indistinguishable phenotypically
        // Long Hair [Shorthair+, M1, M2, M3, M4, M5]
//        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
//            autosomalGenes[32] = (ThreadLocalRandom.current().nextInt(5) + 1);
//        } else {
//            autosomalGenes[32] = (1);
//        }
//        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
//            autosomalGenes[33] = (ThreadLocalRandom.current().nextInt(5) + 1);
//        } else {
//            autosomalGenes[33] = (1);
//        }
//        // Fur Length [1-10] (34-41)
//        for (int i = 34; i < 42; i++) {
//            if (ThreadLocalRandom.current().nextInt(100) > WTC) {
//                autosomalGenes[i] = (ThreadLocalRandom.current().nextInt(10) + 1);
//            } else {
//                autosomalGenes[i] = (1);
//            }
//        }
        //Smaller Eyes [1-5]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[40] = (ThreadLocalRandom.current().nextInt(5) + 1);
        } else {
            autosomalGenes[40] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[41] = (ThreadLocalRandom.current().nextInt(5) + 1);
        } else {
            autosomalGenes[41] = (1);
        }

        //Eye Roundness Adder [1-3]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[42] = (ThreadLocalRandom.current().nextInt(3) + 1);
        } else {
            autosomalGenes[42] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[43] = (ThreadLocalRandom.current().nextInt(3) + 1);
        } else {
            autosomalGenes[43] = (1);
        }

        //Eye Roundness Adder [1-5]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[44] = (ThreadLocalRandom.current().nextInt(5) + 1);
        } else {
            autosomalGenes[44] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[45] = (ThreadLocalRandom.current().nextInt(5) + 1);
        } else {
            autosomalGenes[45] = (1);
        }

        //Eye Roundness Subtractor [1-3]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[46] = (ThreadLocalRandom.current().nextInt(3) + 1);
        } else {
            autosomalGenes[46] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[47] = (ThreadLocalRandom.current().nextInt(3) + 1);
        } else {
            autosomalGenes[47] = (1);
        }

        //Eye Roundness Subtractor [1-5]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[48] = (ThreadLocalRandom.current().nextInt(5) + 1);
        } else {
            autosomalGenes[48] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[49] = (ThreadLocalRandom.current().nextInt(5) + 1);
        } else {
            autosomalGenes[49] = (1);
        }

        //Eye Coloration [neutral/yellow, orange, green]
        for (int i = 50; i < 60; i++) {
            if (ThreadLocalRandom.current().nextInt(100) > WTC) {
                autosomalGenes[i] = (ThreadLocalRandom.current().nextInt(3) + 1);
            } else {
                autosomalGenes[i] = (1);
            }
        }
        //Eye Lightness (60-65 = lighter, 66-72 = darker)
        for (int i = 60; i < 72; i++) {
            if (ThreadLocalRandom.current().nextInt(100) > WTC) {
                autosomalGenes[i] = (ThreadLocalRandom.current().nextInt(2) + 1);
            } else {
                autosomalGenes[i] = (1);
            }
        }
        //Eye Vibrance  [wildtype, more saturated]
        for (int i = 72; i < 80; i++) {
            if (ThreadLocalRandom.current().nextInt(100) > WTC) {
                autosomalGenes[i] = (ThreadLocalRandom.current().nextInt(2) + 1);
            } else {
                autosomalGenes[i] = (1);
            }
        }
        //Head Size 1 [normal, smaller, bigger]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[90] = (ThreadLocalRandom.current().nextInt(3) + 1);
        } else {
            autosomalGenes[90] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[91] = (ThreadLocalRandom.current().nextInt(3) + 1);
        } else {
            autosomalGenes[91] = (1);
        }

        //Head Size 2 [normal, smaller, bigger]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[92] = (ThreadLocalRandom.current().nextInt(3) + 1);
        } else {
            autosomalGenes[92] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[93] = (ThreadLocalRandom.current().nextInt(3) + 1);
        } else {
            autosomalGenes[93] = (1);
        }

        //Head Width Adder
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[94] = (ThreadLocalRandom.current().nextInt(5) + 1);
        } else {
            autosomalGenes[94] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[95] = (ThreadLocalRandom.current().nextInt(5) + 1);
        } else {
            autosomalGenes[95] = (1);
        }

        //Body Length Subtractor
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[96] = (ThreadLocalRandom.current().nextInt(5) + 1);
        } else {
            autosomalGenes[96] = (2);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[97] = (ThreadLocalRandom.current().nextInt(5) + 1);
        } else {
            autosomalGenes[97] = (2);
        }

        //Body Length Subtractor
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[98] = (ThreadLocalRandom.current().nextInt(5) + 1);
        } else {
            autosomalGenes[98] = (2);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[99] = (ThreadLocalRandom.current().nextInt(5) + 1);
        } else {
            autosomalGenes[99] = (2);
        }

        //Muzzle Length [normal, longest, longer, shorter, shortest]
        for (int i = 100; i < 108; i++) {
            if (ThreadLocalRandom.current().nextInt(100) > WTC) {
                autosomalGenes[i] = (ThreadLocalRandom.current().nextInt(5) + 1);
            } else {
                autosomalGenes[i] = (1);
            }
        }
        //Muzzle Width [normal, thinnest, thinner, wider, widest]
        for (int i = 108; i < 116; i++) {
            if (ThreadLocalRandom.current().nextInt(100) > WTC) {
                autosomalGenes[i] = (ThreadLocalRandom.current().nextInt(5) + 1);
            } else {
                autosomalGenes[i] = (1);
            }
        }

        //Muzzle Size [normal, smaller, larger]
        for (int i = 116; i < 120; i++) {
            if (ThreadLocalRandom.current().nextInt(100) > WTC) {
                autosomalGenes[i] = (ThreadLocalRandom.current().nextInt(3) + 1);
            } else {
                autosomalGenes[i] = (1);
            }
        }

        //Body Type Adder [normal to cobby]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[120] = (ThreadLocalRandom.current().nextInt(5) + 1);
        } else {
            autosomalGenes[120] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[121] = (ThreadLocalRandom.current().nextInt(5) + 1);
        } else {
            autosomalGenes[121] = (1);
        }
        //Body Type Adder [normal to cobby]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[122] = (ThreadLocalRandom.current().nextInt(5) + 1);
        } else {
            autosomalGenes[122] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[123] = (ThreadLocalRandom.current().nextInt(5) + 1);
        } else {
            autosomalGenes[123] = (1);
        }
        //Body Type Subtractor [normal to lanky]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[124] = (ThreadLocalRandom.current().nextInt(5) + 1);
        } else {
            autosomalGenes[124] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[125] = (ThreadLocalRandom.current().nextInt(5) + 1);
        } else {
            autosomalGenes[125] = (1);
        }
        //Body Type Subtractor [normal to lanky]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[126] = (ThreadLocalRandom.current().nextInt(5) + 1);
        } else {
            autosomalGenes[126] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[127] = (ThreadLocalRandom.current().nextInt(5) + 1);
        } else {
            autosomalGenes[127] = (1);
        }

        //Ear Spacing 1
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[128] = (ThreadLocalRandom.current().nextInt(3) + 1);
        } else {
            autosomalGenes[128] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[129] = (ThreadLocalRandom.current().nextInt(3) + 1);
        } else {
            autosomalGenes[129] = (1);
        }

        //Ear Spacing 2
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[130] = (ThreadLocalRandom.current().nextInt(3) + 1);
        } else {
            autosomalGenes[130] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[131] = (ThreadLocalRandom.current().nextInt(3) + 1);
        } else {
            autosomalGenes[131] = (1);
        }

        //Ear Width 1
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[132] = (ThreadLocalRandom.current().nextInt(3) + 1);
        } else {
            autosomalGenes[132] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[133] = (ThreadLocalRandom.current().nextInt(3) + 1);
        } else {
            autosomalGenes[133] = (1);
        }
        //Ear Width 2
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[134] = (ThreadLocalRandom.current().nextInt(3) + 1);
        } else {
            autosomalGenes[134] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[135] = (ThreadLocalRandom.current().nextInt(3) + 1);
        } else {
            autosomalGenes[135] = (1);
        }

        //Ear Length 1
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[136] = (ThreadLocalRandom.current().nextInt(5) + 1);
        } else {
            autosomalGenes[136] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[137] = (ThreadLocalRandom.current().nextInt(5) + 1);
        } else {
            autosomalGenes[137] = (1);
        }

        //Ear Length 2
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[138] = (ThreadLocalRandom.current().nextInt(5) + 1);
        } else {
            autosomalGenes[138] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[139] = (ThreadLocalRandom.current().nextInt(5) + 1);
        } else {
            autosomalGenes[139] = (1);
        }

        //Ear Roundness 1
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[140] = (ThreadLocalRandom.current().nextInt(5) + 1);
        } else {
            autosomalGenes[140] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[141] = (ThreadLocalRandom.current().nextInt(5) + 1);
        } else {
            autosomalGenes[141] = (1);
        }

        //Ear Roundness 2
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[142] = (ThreadLocalRandom.current().nextInt(5) + 1);
        } else {
            autosomalGenes[142] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[143] = (ThreadLocalRandom.current().nextInt(5) + 1);
        } else {
            autosomalGenes[143] = (1);
        }

        //Ear Size+
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[144] = (ThreadLocalRandom.current().nextInt(5) + 1);
        } else {
            autosomalGenes[144] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[145] = (ThreadLocalRandom.current().nextInt(5) + 1);
        } else {
            autosomalGenes[145] = (1);
        }

        //Ear Size-
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[146] = (ThreadLocalRandom.current().nextInt(5) + 1);
        } else {
            autosomalGenes[146] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[147] = (ThreadLocalRandom.current().nextInt(5) + 1);
        } else {
            autosomalGenes[147] = (1);
        }

        return new Genes(sexlinkedGenes, autosomalGenes);
    }

}
