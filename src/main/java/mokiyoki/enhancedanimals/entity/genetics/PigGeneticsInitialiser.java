package mokiyoki.enhancedanimals.entity.genetics;

import mokiyoki.enhancedanimals.init.breeds.PigBreeds;
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

public class PigGeneticsInitialiser extends AbstractGeneticsInitialiser {
    List<Breed> breeds = new ArrayList<>();

    public PigGeneticsInitialiser() {
        this.breeds.add(PigBreeds.LARGEWHITE);
        this.breeds.add(PigBreeds.KUNEKUNE);
        this.breeds.add(PigBreeds.PIETRAIN);
        this.breeds.add(PigBreeds.MULEFOOT);
        this.breeds.add(PigBreeds.HAMPSHIRE);
        this.breeds.add(PigBreeds.BERKSHIRE);
        this.breeds.add(PigBreeds.DUROC);
        this.breeds.add(PigBreeds.LARGEBLACK);
        this.breeds.add(PigBreeds.PROTESTPIG);
        this.breeds.add(PigBreeds.TAMWORTH);
        this.breeds.add(PigBreeds.MANGALITSA);
        this.breeds.add(PigBreeds.HEREFORDHOG);
        this.breeds.add(PigBreeds.GLOUCESTERSHIRE);
    }

    public Genes generateNewGenetics(LevelAccessor world, BlockPos pos, boolean generateBreed) {
        return super.generateNewGenetics(world, pos, generateBreed, this.breeds);
    }

    public Genes generateWithBreed(LevelAccessor world, BlockPos pos, String breed) {
        return super.generateWithBreed(world, pos, this.breeds, breed);
    }

    @Override
    public Genes generateLocalWildGenetics(Holder<Biome> biomeHolder, boolean isFlat) {
        int[] autosomalGenes = new int[Reference.PIG_AUTOSOMAL_GENES_LENGTH];
        Biome biome = biomeHolder.value();

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
         *  1 : E^D1 Dominant Black (European Large Black and Chinese Meishan)
         *  5 : E^D2 Dominant Black (Hampshire)
         *  2 : E+ WildType (Wild boar)
         *  3 : E^p Brindle (Large White, Pietrain, ?Oldspot?) Black spot pattern, Makes black spots on white or red ground colour
         *  4 : e Red (Duroc, all red breeds) Disables black pigment creation
         */

        //Extension [ Dom.Black(MCR1), Wildtype+, brindle(spots), red, (Dominant.Black(MCR2)) ]
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

        // Agouti > Brown > Whitebelly > nonagouti > swallowbelly
        //Agouti [ Agouti, Legacy-BrownAgouti, non-agouti, swallowbelly, Whitebelly ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[2] = (ThreadLocalRandom.current().nextInt(4) + 1);
            if (autosomalGenes[2]>=2) autosomalGenes[2]+=1;
        } else {
            autosomalGenes[2] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[3] = (ThreadLocalRandom.current().nextInt(3) + 1);
            if (autosomalGenes[3]>=2) autosomalGenes[3]+=1;
        } else {
            autosomalGenes[3] = (1);
        }

        //Chinchilla [ chinchilla, wildtype ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[4] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            autosomalGenes[4] = (2);
        }
        //if (ThreadLocalRandom.current().nextInt(100) > WTC) {
//            autosomalGenes[5] = (ThreadLocalRandom.current().nextInt(2) + 1);
//
//        } else {
            autosomalGenes[5] = (2);
//        }

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

        //TYRP1 [no dilute, silver-brown, chocolate]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[8] = ThreadLocalRandom.current().nextInt(3) + 1;
        }
        else {
            autosomalGenes[8] = 1;
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[9] = ThreadLocalRandom.current().nextInt(3) + 1;
        }
        else {
            autosomalGenes[9] = 1;
        }

        //White Spots [ Wildtype+, spotted, roanSpots ]
//        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
//            autosomalGenes[10] = (ThreadLocalRandom.current().nextInt(3) + 1);
//
//        } else {
            autosomalGenes[10] = (1);
//        }
//        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
//            autosomalGenes[11] = (ThreadLocalRandom.current().nextInt(3) + 1);

//        } else {
            autosomalGenes[11] = (1);
//        }

        /**
         *    1 :  I^1  Legacy Dominant White : Large White, Belgian Landrace, Landrace
         *    2 : I^Be Belted : white belt over front legs : Hampshire, Cinta Senese
         *    3 : i+   wildtype :
         *    4 :  I^P  Legacy Patch : erroneously functions as a dilute
         *    5 : I^Rn Roan/dilute : grey, white and coloured hairs mixed : Large White, Landrace, Belgian Landrace
         *    6 : I^1  Dominant White : Large White, Belgian Landrace, Landrace
         *    7 :  I^2  Dominant White : Large White, Belgian Landrace, Landrace
         *    8 :  I^3  Dominant White : Large White, Belgian Landrace, Landrace
         *    9 : I^Be2 Large Belt : Swabian Hall
         *    10: I^N2  Tuxedo
         *    11: I^P  Patch : black? spots on white background :  Pietrain, Large White, Landrace, Belgian Landrace
         *    12  I^L  Lethal : Homozygous Lethal
         */

        //KIT [ Legacy-Dom.White, Belted, wildtype+, Legacy-Patch, Roan, Dom.White, (Dom.White2), (Dom.White3), Belted2, Tuxedo, Patch, Lethal ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[12] = (ThreadLocalRandom.current().nextInt(8) + 2);
            if (autosomalGenes[12]>=4) autosomalGenes[12]+=1;
            if (autosomalGenes[12]>=7) autosomalGenes[12]+=2;
            if (wildType == 1) {
                autosomalGenes[13] = (6);
            } else {
                autosomalGenes[13] = (3);
            }

        } else {
            if (wildType == 1) {
                autosomalGenes[12] = (6);
                autosomalGenes[13] = (6);
            } else {
                autosomalGenes[12] = (3);
                autosomalGenes[13] = (3);
            }
        }

        //White points seems to function as a secondary white extension; likely adds additional copies of DUP2
        //White Points [ Wildtype+, legacy-tuxedo, white points ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[14] = (ThreadLocalRandom.current().nextInt(2) + 1);
            if (autosomalGenes[14]==2) autosomalGenes[14]+=1;
            autosomalGenes[15] = (1);

        } else {
            autosomalGenes[14] = (1);
            autosomalGenes[15] = (1);
        }

        //White Extension [ Undermarked+, medium, over marked ]
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


        //Wattles [ wattles, wildtype ]
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

        //baldness [ bald, Wildtype ]
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
            if (wildType == 1) {
                autosomalGenes[37] = (1);
            } else {
                autosomalGenes[37] = (2);
            }
        }

        //wooly [ thicker hair, wildtype, curly wool ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[38] = (ThreadLocalRandom.current().nextInt(3) + 1);

        } else {
            autosomalGenes[38] = (2);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[39] = (ThreadLocalRandom.current().nextInt(3) + 1);

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

        //size genes varient2 [wildtype, bigger, biggest]
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
//        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
//            autosomalGenes[56] = (ThreadLocalRandom.current().nextInt(7) + 1);
//        } else {
            autosomalGenes[56] = (1);
//        }
//        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
//            autosomalGenes[57] = (ThreadLocalRandom.current().nextInt(7) + 1);
//
//        } else {
            autosomalGenes[57] = (1);
//        }

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

        //mulefoot gene [wildtype, mulefoot]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[60] = ThreadLocalRandom.current().nextInt(2) + 1;
        } else {
            autosomalGenes[60] = 1;
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[61] = ThreadLocalRandom.current().nextInt(2) + 1;
        } else {
            autosomalGenes[61] = 1;
        }

        /**
         *  needs E^P to express
         *  dominant gene, negated by OopsAllSpots
         */

        //Tamsworth gene [wildtype, allred]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[62] = ThreadLocalRandom.current().nextInt(2) + 1;
        } else {
            autosomalGenes[62] = 1;
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[63] = ThreadLocalRandom.current().nextInt(2) + 1;
        } else {
            autosomalGenes[63] = 1;
        }

        /**
         *  needs homozygous E^P to express
         *  incomplete dominant at best if E^P homozygous, negated by tamsworth mutation
         *  makes fur white
         */
        //KITLG aka Oops All Spots gene [wildtype, allspots]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[64] = ThreadLocalRandom.current().nextInt(2) + 1;
        } else {
            autosomalGenes[64] = 1;
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[65] = ThreadLocalRandom.current().nextInt(2) + 1;
        } else {
            autosomalGenes[65] = 1;
        }

        //face squash genes 3 [ Wildtype+, long, medium, short, squashed ]
        if (wildType == 1 || ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[66] = (ThreadLocalRandom.current().nextInt(5) + 1);
        } else {
            autosomalGenes[66] = (1);
        }
        if (wildType == 1 || ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[67] = (ThreadLocalRandom.current().nextInt(5) + 1);
        } else {
            autosomalGenes[67] = (1);
        }

        //SSC1 some effect on both
        autosomalGenes[68] = (ThreadLocalRandom.current().nextInt(100) > WTC) ? ThreadLocalRandom.current().nextInt(3)+1 : 1;
        autosomalGenes[69] = (ThreadLocalRandom.current().nextInt(100) > WTC) ? ThreadLocalRandom.current().nextInt(3)+1 : 1;

        //SSC5 makes ears bigger but less effect on size
        autosomalGenes[70] = (ThreadLocalRandom.current().nextInt(100) > WTC) ? ThreadLocalRandom.current().nextInt(3)+1 : 1;
        autosomalGenes[71] = (ThreadLocalRandom.current().nextInt(100) > WTC) ? ThreadLocalRandom.current().nextInt(3)+1 : 1;

        //SSC6 only effects size
        autosomalGenes[72] = (ThreadLocalRandom.current().nextInt(100) > WTC) ? 2 : 1;
        autosomalGenes[73] = (ThreadLocalRandom.current().nextInt(100) > WTC) ? 2 : 1;

        //SSC7 makes ears floppier and bigger the most
        autosomalGenes[74] = (ThreadLocalRandom.current().nextInt(100) > WTC) ? ThreadLocalRandom.current().nextInt(3)+1 : 1;
        autosomalGenes[75] = (ThreadLocalRandom.current().nextInt(100) > WTC) ? ThreadLocalRandom.current().nextInt(3)+1 : 1;

        //SSC9 small change
        autosomalGenes[76] = (ThreadLocalRandom.current().nextInt(100) > WTC) ? ThreadLocalRandom.current().nextInt(3)+1 : 1;
        autosomalGenes[77] = (ThreadLocalRandom.current().nextInt(100) > WTC) ? ThreadLocalRandom.current().nextInt(3)+1 : 1;

        //SSC12 least change
        autosomalGenes[78] = (ThreadLocalRandom.current().nextInt(100) > WTC) ? ThreadLocalRandom.current().nextInt(3)+1 : 1;
        autosomalGenes[79] = (ThreadLocalRandom.current().nextInt(100) > WTC) ? ThreadLocalRandom.current().nextInt(3)+1 : 1;

        //ear size [xsmall, small, medium, large, xlarge]
        for (int i = 80; i < 120; i++) {
            autosomalGenes[i] = (ThreadLocalRandom.current().nextInt(100) > WTC) ? ThreadLocalRandom.current().nextInt(4)+1 : 1;
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
        
        //blonde dilute [normal, cream]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[158] = ThreadLocalRandom.current().nextInt(2) + 1;
        } 
        else {
            autosomalGenes[158] = 1;
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[159] = ThreadLocalRandom.current().nextInt(2) + 1;
        } 
        else {
            autosomalGenes[159] = 1;
        }

        //heterochromia [ Wildtype+, blue eye(s) ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[160] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            autosomalGenes[160] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[161] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            autosomalGenes[161] = (1);
        }
        //heterochromia enhancer [ Wildtype+, bilateral ]
        //if (ThreadLocalRandom.current().nextInt(100) > WTC) {
        //    autosomalGenes[162] = (ThreadLocalRandom.current().nextInt(2) + 1);
        //
        //} else {
            autosomalGenes[162] = (1);
        //}
        //if (ThreadLocalRandom.current().nextInt(100) > WTC) {
        //    autosomalGenes[163] = (ThreadLocalRandom.current().nextInt(2) + 1);
        //
        //} else {
            autosomalGenes[163] = (1);
        //}

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

        //muscle adder [ normal, more... ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[166] = (ThreadLocalRandom.current().nextInt(10) + 1);
        } else {
            autosomalGenes[166] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[167] = (ThreadLocalRandom.current().nextInt(10) + 1);
        } else {
            autosomalGenes[167] = (1);
        }

        //muscle adder [ normal, more... ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[168] = (ThreadLocalRandom.current().nextInt(10) + 1);
        } else {
            autosomalGenes[168] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[169] = (ThreadLocalRandom.current().nextInt(10) + 1);
        } else {
            autosomalGenes[169] = (1);
        }

        //muscle adder [ normal, more... ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[170] = (ThreadLocalRandom.current().nextInt(10) + 1);
        } else {
            autosomalGenes[170] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[171] = (ThreadLocalRandom.current().nextInt(10) + 1);
        } else {
            autosomalGenes[171] = (1);
        }

        //hypertrophy [wildtype+, Hypertrophy]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[172] = (ThreadLocalRandom.current().nextInt(2) + 1);
        } else {
            autosomalGenes[172] = (1);
        }
        autosomalGenes[173] = (1);

        //fat adder [ normal, more... ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[174] = (ThreadLocalRandom.current().nextInt(10) + 1);
        } else {
            autosomalGenes[174] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[175] = (ThreadLocalRandom.current().nextInt(10) + 1);
        } else {
            autosomalGenes[175] = (1);
        }

        //fat adder [ normal, more... ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[176] = (ThreadLocalRandom.current().nextInt(10) + 1);
        } else {
            autosomalGenes[176] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[177] = (ThreadLocalRandom.current().nextInt(10) + 1);
        } else {
            autosomalGenes[177] = (1);
        }
        //fat adder [ normal, more... ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[178] = (ThreadLocalRandom.current().nextInt(10) + 1);
        } else {
            autosomalGenes[178] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[179] = (ThreadLocalRandom.current().nextInt(10) + 1);
        } else {
            autosomalGenes[179] = (1);
        }
        //fat adder [ normal, more... ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[180] = (ThreadLocalRandom.current().nextInt(10) + 1);
        } else {
            autosomalGenes[180] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[181] = (ThreadLocalRandom.current().nextInt(10 ) + 1);
        } else {
            autosomalGenes[181] = (1);
        }

        //body length 1
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[182] = (ThreadLocalRandom.current().nextInt(10) + 1);
        } else {
            autosomalGenes[182] = (2);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[183] = (ThreadLocalRandom.current().nextInt(10) + 1);
        } else {
            autosomalGenes[183] = (2);
        }
        //body length 2
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[184] = (ThreadLocalRandom.current().nextInt(10) + 1);
        } else {
            autosomalGenes[184] = (2);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[185] = (ThreadLocalRandom.current().nextInt(10) + 1);
        } else {
            autosomalGenes[185] = (2);
        }
        //body length 3
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[186] = (ThreadLocalRandom.current().nextInt(10) + 1);
        } else {
            autosomalGenes[186] = (3);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[187] = (ThreadLocalRandom.current().nextInt(10) + 1);
        } else {
            autosomalGenes[187] = (3);
        }
        //body length 4
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[188] = (ThreadLocalRandom.current().nextInt(10) + 1);
        } else {
            autosomalGenes[188] = (3);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[189] = (ThreadLocalRandom.current().nextInt(10) + 1);
        } else {
            autosomalGenes[189] = (3);
        }

        //MITF [wildtype, splash?/hereford]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[190] = (ThreadLocalRandom.current().nextInt(2) + 1);
        } else {
            autosomalGenes[190] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[191] = (ThreadLocalRandom.current().nextInt(2) + 1);
        } else {
            autosomalGenes[191] = (1);
        }

        //desaturation genes [normal, duller]
        for (int i = 192; i < 202; i++) {
            if (ThreadLocalRandom.current().nextInt(100) > WTC*0.8F) {
                autosomalGenes[i] = ThreadLocalRandom.current().nextInt(2)+1;
            } else {
                autosomalGenes[i] = 1;
            }
        }

        return new Genes(autosomalGenes);
    }
}
