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
//        Biome biome = biomeHolder.value();

        // note here with values

//        int wildType = 2;
//        if (Biome.getBiomeCategory(Holder.direct(biome)).equals(Biome.BiomeCategory.PLAINS)) {
//            wildType = 1;
//        }
//        if (isFlat) {
//            int randomizeWT = ThreadLocalRandom.current().nextInt(4);
//            if (randomizeWT <= 2) {
//                wildType = randomizeWT;
//            }
//        }


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
        autosomalGenes[0] = ThreadLocalRandom.current().nextInt(100) > WTC ? ThreadLocalRandom.current().nextInt(2) + 1 : 1;
        autosomalGenes[1] = ThreadLocalRandom.current().nextInt(100) > WTC ? ThreadLocalRandom.current().nextInt(2) + 1 : 1;

        /**
         * ASIP - Agouti
         *    1 : A^r dominant wildtype: red
         *    2 : a recessive - more silver
         */
        //Agouti [ a=2, A=1 ] - 2 mutations, default to 1
       // autosomalGenes[1] = ThreadLocalRandom.current().nextInt(100) > WTC ? (ThreadLocalRandom.current().nextInt(2) + 1) : 1;

        //Agouti test 2 [ Wildtype, silver ]
        autosomalGenes[2] = ThreadLocalRandom.current().nextInt(100) > WTC ? ThreadLocalRandom.current().nextInt(2) + 1 : 1;
        autosomalGenes[3] = ThreadLocalRandom.current().nextInt(100) > WTC ? ThreadLocalRandom.current().nextInt(2) + 1 : 1;

        /**
         * SILVER GUARD HAIRS
         *    4 : S  - wildtype: no silvering
         *    5 : s - more silvering
         */
       // SILVERING [ Wildtype, silvering ]
        // [1:wildtype, 2:faded silver, 3:full silver
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[4] = (ThreadLocalRandom.current().nextInt(2) + 1);
        } else {
            autosomalGenes[4] = switch (Biome.getBiomeCategory(biomeHolder)) {
                case ICY -> 2;
                case MOUNTAIN, EXTREME_HILLS -> 3;
                default -> 1;
            };
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[5] = (ThreadLocalRandom.current().nextInt(2) + 1);
        } else {
            autosomalGenes[5] = switch (Biome.getBiomeCategory(biomeHolder)) {
                case ICY -> 2;
                case MOUNTAIN, EXTREME_HILLS -> 3;
                default -> 1;
            };
        }


        // Marble [ Wildtype, marble ]
        autosomalGenes[6] = ThreadLocalRandom.current().nextInt(100) > WTC ? ThreadLocalRandom.current().nextInt(2) + 1 : 1;
        autosomalGenes[7] = ThreadLocalRandom.current().nextInt(100) > WTC ? ThreadLocalRandom.current().nextInt(2) + 1 : 1;


        // Whitemark [ Wildtype, wm ]
        autosomalGenes[8] = ThreadLocalRandom.current().nextInt(100) > WTC ? ThreadLocalRandom.current().nextInt(2) + 1 : 1;
        autosomalGenes[9] = ThreadLocalRandom.current().nextInt(100) > WTC ? ThreadLocalRandom.current().nextInt(2) + 1 : 1;


        // Georgian White [ Wildtype, GW ]
        autosomalGenes[10] = ThreadLocalRandom.current().nextInt(100) > WTC ? ThreadLocalRandom.current().nextInt(2) + 1 : 1;
        autosomalGenes[11] = ThreadLocalRandom.current().nextInt(100) > WTC ? ThreadLocalRandom.current().nextInt(2) + 1 : 1;

        // Platinum [ Wildtype, plat ]
        autosomalGenes[12] = ThreadLocalRandom.current().nextInt(100) > WTC ? ThreadLocalRandom.current().nextInt(2) + 1 : 1;
        autosomalGenes[13] = ThreadLocalRandom.current().nextInt(100) > WTC ? ThreadLocalRandom.current().nextInt(2) + 1 : 1;

        // Ringneck [ Wildtype, ringneck ]
        autosomalGenes[14] = ThreadLocalRandom.current().nextInt(100) > WTC ? ThreadLocalRandom.current().nextInt(2) + 1 : 1;
        autosomalGenes[15] = ThreadLocalRandom.current().nextInt(100) > WTC ? ThreadLocalRandom.current().nextInt(2) + 1 : 1;

        // black socks [ Wildtype, no socks ]
        autosomalGenes[16] = ThreadLocalRandom.current().nextInt(100) > WTC ? ThreadLocalRandom.current().nextInt(2) + 1 : 1;
        autosomalGenes[17] = ThreadLocalRandom.current().nextInt(100) > WTC ? ThreadLocalRandom.current().nextInt(2) + 1 : 1;

        // Dilute/Blue [Wildtype, dilute(2)]
        autosomalGenes[18] = ThreadLocalRandom.current().nextInt(100) > WTC ? ThreadLocalRandom.current().nextInt(2) + 1 : 1;
        autosomalGenes[19] = ThreadLocalRandom.current().nextInt(100) > WTC ? ThreadLocalRandom.current().nextInt(2) + 1 : 1;

        //wideband [normal, wideband]
        autosomalGenes[20] = ThreadLocalRandom.current().nextInt(100) > WTC ? ThreadLocalRandom.current().nextInt(2) + 1 : 1;
        autosomalGenes[21] = ThreadLocalRandom.current().nextInt(100) > WTC ? ThreadLocalRandom.current().nextInt(2) + 1 : 1;


        //hair density [ wildtype, furry, sparse ]
        autosomalGenes[22] = ThreadLocalRandom.current().nextInt(100) > WTC ? ThreadLocalRandom.current().nextInt(3) + 1 : 1;
        autosomalGenes[23] = ThreadLocalRandom.current().nextInt(100) > WTC ? ThreadLocalRandom.current().nextInt(3) + 1 : 1;

        //desaturation [wildtype, duller, duller, ...]
        autosomalGenes[24] = /*ThreadLocalRandom.current().nextInt(100) > WTC ? ThreadLocalRandom.current().nextInt(10) + 1 :*/ 1;
        autosomalGenes[25] = /*ThreadLocalRandom.current().nextInt(100) > WTC ? ThreadLocalRandom.current().nextInt(10) + 1 :*/ 1;

        //Eye Coloration [neutral/yellow, orange, green]
        for (int i = 26; i < 36; i++) {
            if (ThreadLocalRandom.current().nextInt(100) > WTC) {
                autosomalGenes[i] = (ThreadLocalRandom.current().nextInt(3) + 1);
            } else {
                autosomalGenes[i] = (1);
            }
        }

        //Eye Lightness (36-41 = lighter, 42-47 = darker)
        for (int i = 36; i < 48; i++) {
            if (ThreadLocalRandom.current().nextInt(100) > WTC) {
                autosomalGenes[i] = (ThreadLocalRandom.current().nextInt(2) + 1);
            } else {
                autosomalGenes[i] = (1);
            }
        }

        //Eye Vibrance  [wildtype, more saturated]
        for (int i = 48; i < 56; i++) {
            if (ThreadLocalRandom.current().nextInt(100) > WTC) {
                autosomalGenes[i] = (ThreadLocalRandom.current().nextInt(2) + 1);
            } else {
                autosomalGenes[i] = (1);
            }
        }

        //rufousing [58-71: -red; 72-85: +red]
        for (int i = 58; i < 86; i++) {
            if (ThreadLocalRandom.current().nextInt(100) > WTC*0.6F) {
                autosomalGenes[i] = ThreadLocalRandom.current().nextInt(2)+1;
            } else {
                autosomalGenes[i] = 1;
            }
        }

        //darkness
        for (int i = 86; i < 94; i++) {
            if (ThreadLocalRandom.current().nextInt(100) > WTC*0.6F) {
                autosomalGenes[i] = ThreadLocalRandom.current().nextInt(2)+1;
            } else {
                autosomalGenes[i] = 1;
            }
        }

        //desaturation genes [normal, duller]
        for (int i = 94; i < 104; i++) {
            if (ThreadLocalRandom.current().nextInt(100) > WTC*0.8F) {
                autosomalGenes[i] = ThreadLocalRandom.current().nextInt(2)+1;
            } else {
                autosomalGenes[i] = 1;
            }
        }

        //burgandy [normal, wideband]
        autosomalGenes[104] = ThreadLocalRandom.current().nextInt(100) > WTC ? ThreadLocalRandom.current().nextInt(2) + 1 : 1;
        autosomalGenes[105] = ThreadLocalRandom.current().nextInt(100) > WTC ? ThreadLocalRandom.current().nextInt(2) + 1 : 1;



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
