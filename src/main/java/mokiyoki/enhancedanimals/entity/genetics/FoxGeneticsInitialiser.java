package mokiyoki.enhancedanimals.entity.genetics;

import mokiyoki.enhancedanimals.init.breeds.FoxBreeds;
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

public class FoxGeneticsInitialiser extends AbstractGeneticsInitialiser {
    List<Breed> breeds = new ArrayList<>();

    public FoxGeneticsInitialiser() {
        this.breeds.add(FoxBreeds.RED);
        this.breeds.add(FoxBreeds.BLACK);
        this.breeds.add(FoxBreeds.CROSS1);
    }


    public Genes generateNewGenetics(LevelAccessor world, BlockPos pos, boolean generateBreed) {
        return super.generateNewGenetics(world, pos, generateBreed, this.breeds);
    }

    public Genes generateWithBreed(LevelAccessor world, BlockPos pos, String breed) {
        return super.generateWithBreed(world, pos, this.breeds, breed);
    }

    @Override
    public Genes generateLocalWildGenetics(Holder<Biome> biomeHolder, boolean isFlat) {
        int[] autosomalGenes = new int[Reference.FOX_AUTOSOMAL_GENES_LENGTH];  // value is 100 rn
        Biome biome = biomeHolder.value();

        // note here with values

        int wildType = 2;
        if (Biome.getBiomeCategory(Holder.direct(biome)).equals(Biome.BiomeCategory.PLAINS)) {
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
         *  1 : E dominant wildtype
         *  2 : e recessive - more silver
         *
         *  example: a wildtype fox would have EE
         */
        // Extension test 1 - 2 mutations, value set to 1
       // autosomalGenes[0] = ThreadLocalRandom.current().nextInt(100) > WTC ? (ThreadLocalRandom.current().nextInt(2) + 1) : 1;

        //Extension test 2 [ Wildtype, silver ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[0] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            autosomalGenes[0] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[1] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            autosomalGenes[1] = (1);
        }

        /**
         * ASIP - Agouti
         *    1 : A^r dominant wildtype: red
         *    2 : a recessive - more silver
         */
        //Agouti [ a=2, A=1 ] - 2 mutations, default to 1
       // autosomalGenes[1] = ThreadLocalRandom.current().nextInt(100) > WTC ? (ThreadLocalRandom.current().nextInt(2) + 1) : 1;

        //Agouti test 2 [ Wildtype, silver ]
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




        /**
         * SILVER GUARD HAIRS
         *    4 : S  - wildtype: no silvering
         *    5 : s - more silvering
         */
       // SILVERING [ Wildtype, silvering ]

//        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
//            autosomalGenes[4] = (ThreadLocalRandom.current().nextInt(2) + 1);

//        } else {
//            autosomalGenes[4] = (2);
//        }
//        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
//            autosomalGenes[5] = (ThreadLocalRandom.current().nextInt(2) + 1);

//        } else {
 //           autosomalGenes[5] = (2);
//        }

        // [1:wildtype, 2:faded silver, 3:full silver
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[4] = (ThreadLocalRandom.current().nextInt(2) + 1);
        } else {
            autosomalGenes[4] = 1;
            switch (Biome.getBiomeCategory(biomeHolder)) {
                case PLAINS, DESERT -> autosomalGenes[4] = 1;
                case ICY -> autosomalGenes[4] = 2;
                case MOUNTAIN, EXTREME_HILLS -> autosomalGenes[4] = 3;
            }
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[5] = (ThreadLocalRandom.current().nextInt(2) + 1);
        } else {
            autosomalGenes[5] = 1;
            switch (Biome.getBiomeCategory(biomeHolder)) {
                case PLAINS, DESERT -> autosomalGenes[5] = 1;
                case ICY -> autosomalGenes[5] = 2;
                case MOUNTAIN, EXTREME_HILLS -> autosomalGenes[5] = 3;
            }
        }


        // Marble [ Wildtype, marble ]
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

        // Whitemark [ Wildtype, wm ]
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

        // Georgian White [ Wildtype, GW ]
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

        // Platinum [ Wildtype, plat ]
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

        // Ringneck [ Wildtype, ringneck ]
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

        // black socks [ Wildtype, no socks ]
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


        // SMOKY FACTOR - increases levels of dark hairs scattered thru coat
        // rufousing?



        //eye size? 1-5
    //    if (ThreadLocalRandom.current().nextInt(100) > WTC) {
    //        autosomalGenes[6] = (ThreadLocalRandom.current().nextInt(5) + 1);
    //    } else {
    //        autosomalGenes[6] = (1);
    //    }
    //    if (ThreadLocalRandom.current().nextInt(100) > WTC) {
    //        autosomalGenes[7] = (ThreadLocalRandom.current().nextInt(5) + 1);
    //    } else {
    //        autosomalGenes[7] = (1);
    //    }


        // genes 6 and 7?

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



        // Dilute/Blue [Wildtype, dilute(2)]
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



        //rufousing [120-133: -red; 134-147: +red]
        for (int i = 120; i < 148; i++) {
            if (ThreadLocalRandom.current().nextInt(100) > WTC*0.6F) {
                autosomalGenes[i] = ThreadLocalRandom.current().nextInt(2)+1;
            } else {
                autosomalGenes[i] = 1;
            }
        }

        //desaturation [wildtype, duller, duller, ...]
//        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
//            autosomalGenes[148] = ThreadLocalRandom.current().nextInt(10) + 1;
//        }
//        else {
        autosomalGenes[148] = 1;
//        }
//        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
//            autosomalGenes[149] = ThreadLocalRandom.current().nextInt(10) + 1;
//        }
//        else {
        autosomalGenes[149] = 1;
//        }

        //darkness
        for (int i = 150; i < 158; i++) {
            if (ThreadLocalRandom.current().nextInt(100) > WTC*0.6F) {
                autosomalGenes[i] = ThreadLocalRandom.current().nextInt(2)+1;
            } else {
                autosomalGenes[i] = 1;
            }
        }


        //wideband [normal, wideband]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[164] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            autosomalGenes[164] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[165] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            autosomalGenes[165] = (1);
        }

        //desaturation genes [normal, duller]
        for (int i = 192; i < 202; i++) {
            if (ThreadLocalRandom.current().nextInt(100) > WTC*0.8F) {
                autosomalGenes[i] = ThreadLocalRandom.current().nextInt(2)+1;
            } else {
                autosomalGenes[i] = 1;
            }
        }







        // ear size, spacing, angle


        //if (ThreadLocalRandom.current().nextInt(100) > WTC) {
        //    autosomalGenes[2] = (ThreadLocalRandom.current().nextInt(2) + 1);

        //} else {
        //    autosomalGenes[2] = (2);
        //}
        //if (ThreadLocalRandom.current().nextInt(100) > WTC) {
        //    autosomalGenes[3] = (ThreadLocalRandom.current().nextInt(2) + 1);

        //} else {
        //    autosomalGenes[3] = (2);
        //}

        // WTC = WildTypeChance
        // autosomalGenes[#] = the gene ID #; which gene it is
        // can include biome category


        return new Genes(autosomalGenes);
    }
}
