package mokiyoki.enhancedanimals.entity.genetics;

import mokiyoki.enhancedanimals.init.breeds.ChickenBreeds;
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

public class ChickenGeneticsInitialiser extends AbstractGeneticsInitialiser {
    List<Breed> breeds = new ArrayList<>();

    public ChickenGeneticsInitialiser() {
//        this.breeds.add(ChickenBreeds.LEGHORN);
        this.breeds.add(ChickenBreeds.GOLD_DUCKWING_LEGHORN);
        this.breeds.add(ChickenBreeds.SILVER_DUCKWING_LEGHORN);
        this.breeds.add(ChickenBreeds.WHITE_LEGHORN);
        this.breeds.add(ChickenBreeds.BLACK_LEGHORN);
        this.breeds.add(ChickenBreeds.BLUE_LEGHORN);
        this.breeds.add(ChickenBreeds.BUFF_LEGHORN);
        this.breeds.add(ChickenBreeds.PILE_LEGHORN);
        this.breeds.add(ChickenBreeds.GOLD_LEGBAR);
        this.breeds.add(ChickenBreeds.CRESTED_CREAM_LEGBAR);
        this.breeds.add(ChickenBreeds.WHITE_WYANDOTTE);
        this.breeds.add(ChickenBreeds.MILLEFLEUR_WYANDOTTE);
        this.breeds.add(ChickenBreeds.BLUE_LACED_WYANDOTTE);
        this.breeds.add(ChickenBreeds.BUFF_LACED_WYANDOTTE);
        this.breeds.add(ChickenBreeds.GOLD_LACED_WYANDOTTE);
        this.breeds.add(ChickenBreeds.GOLD_PENCILED_WYANDOTTE);
        this.breeds.add(ChickenBreeds.SILVER_LACED_WYANDOTTE);
        this.breeds.add(ChickenBreeds.SILVER_PENCILED_WYANDOTTE);
        this.breeds.add(ChickenBreeds.BUFF_ORPINGTON);
        this.breeds.add(ChickenBreeds.BLACK_ORPINGTON);
        this.breeds.add(ChickenBreeds.BLUE_ORPINGTON);
        this.breeds.add(ChickenBreeds.CHOCOLATE_ORPINGTON);
        this.breeds.add(ChickenBreeds.GOLD_LACED_ORPINGTON);
        this.breeds.add(ChickenBreeds.GOLD_PENCILED_ORPINGTON);
        this.breeds.add(ChickenBreeds.MOTTLED_ORPINGTON);
        this.breeds.add(ChickenBreeds.LAVENDER_ORPINGTON);
        this.breeds.add(ChickenBreeds.SILVER_CHOCOLATE_LACED_ORPINGTON);
        this.breeds.add(ChickenBreeds.RHODEISLANDRED);
        this.breeds.add(ChickenBreeds.PLYMOUTHROCK);
        this.breeds.add(ChickenBreeds.BLACK_D_UCCLE);
        this.breeds.add(ChickenBreeds.MOTTLED_D_UCCLE);
        this.breeds.add(ChickenBreeds.MILLEFLEUR_D_UCCLE);
        this.breeds.add(ChickenBreeds.BLUE_MOTTLED_D_UCCLE);
        this.breeds.add(ChickenBreeds.LAVENDER_MOTTLED_D_UCCLE);
        this.breeds.add(ChickenBreeds.LEMON_MILLEFLEUR_D_UCCLE);
        this.breeds.add(ChickenBreeds.SILVER_MILLEFLEUR_D_UCCLE);
        this.breeds.add(ChickenBreeds.PORCELAINE_D_UCCLE);
        this.breeds.add(ChickenBreeds.GOLDNECK_D_UCCLE);
        this.breeds.add(ChickenBreeds.GOLD_QUAIL_WATERMEAL);
        this.breeds.add(ChickenBreeds.SILVER_QUAIL_WATERMEAL);
        this.breeds.add(ChickenBreeds.BELGIUM_DANVERS);
        this.breeds.add(ChickenBreeds.BELGIUM_D_EVERBERG);
        this.breeds.add(ChickenBreeds.BELGIUM_DE_BOITSFORT);
        this.breeds.add(ChickenBreeds.BELGIUM_DE_GRUBBE);
        this.breeds.add(ChickenBreeds.ARAUCANA);
        this.breeds.add(ChickenBreeds.EASTEREGGER);
        this.breeds.add(ChickenBreeds.SPANISH);
        this.breeds.add(ChickenBreeds.SILKIE);
        this.breeds.add(ChickenBreeds.SCOTS_DUMPY);
        this.breeds.add(ChickenBreeds.LA_FLECHE);
        this.breeds.add(ChickenBreeds.BLUE_SALMON_FAVEROLLE);
        this.breeds.add(ChickenBreeds.BUTTERCUP);
//        this.breeds.add(ChickenBreeds.CUTIEPIE);
    }

    public Genes generateNewGenetics(LevelAccessor world, BlockPos pos, boolean generateBreed) {
        return super.generateNewGenetics(world, pos, generateBreed, this.breeds);
    }

    public Genes generateWithBreed(LevelAccessor world, BlockPos pos, String breed) {
        return super.generateWithBreed(world, pos, this.breeds, breed);
    }

    @Override
    public Genes generateLocalWildGenetics(Holder<Biome> biomeHolder, boolean isFlat) {
        Biome biome = biomeHolder.value();
        int[] sexlinkedGenes = new int[Reference.CHICKEN_SEXLINKED_GENES_LENGTH];
        int[] autosomalGenes = new int[Reference.CHICKEN_AUTOSOMAL_GENES_LENGTH];

        //[ 0=minecraft wildtype, 1=jungle wildtype, 2=savanna wildtype, 3=cold wildtype, 4=swamp wildtype ]
        int wildType = 0;

        if (biome.getBaseTemperature() >= 0.9F && biome.getDownfall() > 0.8F) // hot and wet (jungle)
        {
            wildType = 1;
        } else if (biome.getBaseTemperature() >= 0.9F && biome.getDownfall() < 0.3F) // hot and dry (savanna)
        {
            wildType = 2;
        } else if (biome.getBaseTemperature() < 0.3F) // cold (mountains)
        {
            wildType = 3;
        } else if (biome.getBaseTemperature() >= 0.8F && biome.getDownfall() > 0.8F) {
            wildType = 4;
        }

        if (isFlat) {
            int randomizeWT = ThreadLocalRandom.current().nextInt(10);
            if (randomizeWT <= 4) {
                wildType = randomizeWT;
            }
        }

//             TODO here: genes for egg hatch chance when thrown, egg laying rate, and chicken ai modifiers
        /**
         * parent linked genes
         */
        //Gold [ gold, silver ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            sexlinkedGenes[0] = (ThreadLocalRandom.current().nextInt(2) + 1);
        } else {
            if (wildType == 3) {
                //cold biome silver variation
                sexlinkedGenes[0] = 2;
            } else {
                sexlinkedGenes[0] = 1;
            }
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            sexlinkedGenes[1] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            if (wildType == 3) {
                //cold biome silver variation
                sexlinkedGenes[1] = 2;
            } else {
                sexlinkedGenes[1] = 1;
            }
        }

        //Chocolate [ wildtype, chocolate ]
        if (ThreadLocalRandom.current().nextInt(100) > (WTC + ((100 - WTC) / 1.2))) {
            sexlinkedGenes[2] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            sexlinkedGenes[2] = 1;
        }
        if (ThreadLocalRandom.current().nextInt(100) > (WTC + ((100 - WTC) / 1.2))) {
            sexlinkedGenes[3] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            sexlinkedGenes[3] = 1;
        }

        //ear size setting genes
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            sexlinkedGenes[4] = (ThreadLocalRandom.current().nextInt(6) + 1);
        } else {
            if (wildType == 1) {
                sexlinkedGenes[4] = (6);
            } else {
                sexlinkedGenes[4] = (1);
            }
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            sexlinkedGenes[5] = (ThreadLocalRandom.current().nextInt(6) + 1);
        } else {
            if (wildType == 1) {
                sexlinkedGenes[5] = (6);
            } else {
                sexlinkedGenes[5] = (1);
            }
        }

        //Barred [ wildtype, barred ] //exclusive to savanna
        if (ThreadLocalRandom.current().nextInt(100) > WTC && wildType == 2) {
            sexlinkedGenes[6] = (ThreadLocalRandom.current().nextInt(2) + 1);
        } else {
            sexlinkedGenes[6] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC && wildType == 2) {
            sexlinkedGenes[7] = (ThreadLocalRandom.current().nextInt(2) + 1);
        } else {
            sexlinkedGenes[7] = (1);
        }

        //Fibromelanin Suppressor [ wildtype, suppressor ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            sexlinkedGenes[8] = (ThreadLocalRandom.current().nextInt(2) + 1);
        } else {
            sexlinkedGenes[8] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            sexlinkedGenes[9] = (ThreadLocalRandom.current().nextInt(2) + 1);
        } else {
            sexlinkedGenes[9] = (1);
        }

        //Brown egg genes suppressor [ wildtype, suppressor ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            sexlinkedGenes[10] = (ThreadLocalRandom.current().nextInt(2) + 1);
        } else {
            sexlinkedGenes[10] = 1;
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            sexlinkedGenes[11] = (ThreadLocalRandom.current().nextInt(2) + 1);
        } else {
            sexlinkedGenes[11] = 1;
        }

        //white face
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            sexlinkedGenes[12] = (ThreadLocalRandom.current().nextInt(6) + 1);
        } else {
            sexlinkedGenes[12] = 6;
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            sexlinkedGenes[13] = (ThreadLocalRandom.current().nextInt(6) + 1);
        } else {
            sexlinkedGenes[13] = 6;
        }

        //dwarf [ normal, slight dwarf ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            sexlinkedGenes[14] = (ThreadLocalRandom.current().nextInt(3) + 1);
        } else {
            sexlinkedGenes[14] = 1;
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            sexlinkedGenes[15] = (ThreadLocalRandom.current().nextInt(3) + 1);
        } else {
            sexlinkedGenes[15] = 1;
        }

        //dwarf 2 [ normal, very dwarf ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            sexlinkedGenes[16] = (ThreadLocalRandom.current().nextInt(3) + 1);
        } else {
            sexlinkedGenes[16] = 1;
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            sexlinkedGenes[17] = (ThreadLocalRandom.current().nextInt(3) + 1);
        } else {
            sexlinkedGenes[17] = 1;
        }

        //large ear inhibitor [ no inhibitor, halving inhibitor ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            sexlinkedGenes[18] = (ThreadLocalRandom.current().nextInt(3) + 1);
        } else {
            sexlinkedGenes[18] = 1;
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            sexlinkedGenes[19] = (ThreadLocalRandom.current().nextInt(3) + 1);
        } else {
            sexlinkedGenes[19] = 1;
        }

        /**
         * unused autosomal genes
         */

        for (int i = 0; i < 20; i++) {
            autosomalGenes[i] = 1;
        }

        /**
         * autosomal genes
         */

        //Recessive white [ wild, recessive white, albino ]  //mutation common in temperate areas and swamps
        if (ThreadLocalRandom.current().nextInt(100) > WTC || wildType == 4) {
            if (ThreadLocalRandom.current().nextInt(200) == 199) {
                autosomalGenes[20] = (3);
            } else {
                autosomalGenes[20] = (ThreadLocalRandom.current().nextInt(2) + 1);
            }
        } else {
            if (Biome.getBiomeCategory(Holder.direct(biome)) == Biome.BiomeCategory.PLAINS) {
                autosomalGenes[20] = (2);
            } else {
                autosomalGenes[20] = (1);
            }
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC || wildType == 4) {
            autosomalGenes[21] = (ThreadLocalRandom.current().nextInt(2) + 1);
        } else {
            if (Biome.getBiomeCategory(Holder.direct(biome)) == Biome.BiomeCategory.PLAINS) {
                autosomalGenes[21] = (2);
            } else {
                autosomalGenes[21] = (1);
            }
        }

        //Mottled [ wildtype, mottled, white crested ]  // cold biome exclusive
        if (ThreadLocalRandom.current().nextInt(100) > WTC && wildType == 3) {
            autosomalGenes[22] = (ThreadLocalRandom.current().nextInt(3) + 1);
        } else {
            autosomalGenes[22] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC && wildType == 3) {
            autosomalGenes[23] = (ThreadLocalRandom.current().nextInt(3) + 1);
        } else {
            autosomalGenes[23] = (1);
        }

        //Dlocus [ birchen, duckwing, wheaten, partridge, extended black ]
        //swamps have random Dlocus genes
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[24] = (ThreadLocalRandom.current().nextInt(5) + 1);
        } else {
            // swamps have a mixture but no black
            if (wildType == 4) {
                autosomalGenes[24] = (ThreadLocalRandom.current().nextInt(3) + 2);
            }
            // partridge is savanna wild type
            else if (wildType == 2) {
                autosomalGenes[24] = (4);
                // birchen and extended black is cold biome wildtype
            } else if (wildType == 3) {
                if (ThreadLocalRandom.current().nextInt(3) == 0) {
                    autosomalGenes[24] = (5);
                } else {
                    autosomalGenes[24] = (1);
                }
                // duckwing is jungle "true" wildtype
            } else {
                autosomalGenes[24] = (2);
            }
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[25] = (ThreadLocalRandom.current().nextInt(4) + 1);
        } else {
            // swamps have a mixture but no black
            if (wildType == 4) {
                autosomalGenes[25] = (ThreadLocalRandom.current().nextInt(3) + 2);
            }
            // partridge is savanna wild type
            else if (wildType == 2) {
                autosomalGenes[25] = (4);
                // birchen is cold biome wildtype
            } else if (wildType == 3) {
                if (ThreadLocalRandom.current().nextInt(3) == 0) {
                    autosomalGenes[25] = (5);
                } else {
                    autosomalGenes[25] = (1);
                }
                // duckwing is jungle "true" wildtype
            } else {
                autosomalGenes[25] = (2);
            }
        }

        //Pattern Gene [ pattern, wildtype ] pattern genes is common in savannas
        if (wildType == 2) {
            if (ThreadLocalRandom.current().nextInt(100) > (WTC * 0.5)) {
                autosomalGenes[26] = (ThreadLocalRandom.current().nextInt(2) + 1);
            } else {
                autosomalGenes[26] = (2);
            }
        } else {
            if (ThreadLocalRandom.current().nextInt(100) > WTC) {
                autosomalGenes[26] = (ThreadLocalRandom.current().nextInt(2) + 1);

            } else {
                autosomalGenes[26] = (2);
            }
        }
        if (wildType == 2) {
            if (ThreadLocalRandom.current().nextInt(100) > (WTC / 2)) {
                autosomalGenes[27] = (ThreadLocalRandom.current().nextInt(2) + 1);
            } else {
                autosomalGenes[27] = (2);
            }
        } else {
            if (ThreadLocalRandom.current().nextInt(100) > WTC) {
                autosomalGenes[27] = (ThreadLocalRandom.current().nextInt(2) + 1);
            } else {
                autosomalGenes[27] = (2);
            }
        }


        //Colombian [ colombian, wildtype ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC || wildType == 3) {
            autosomalGenes[28] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            autosomalGenes[28] = (2);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[29] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            autosomalGenes[29] = (2);
        }

        //Melanized [melanized, wildtype ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[30] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            autosomalGenes[30] = (2);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[31] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            autosomalGenes[31] = (2);
        }

        //Dilute [ dilute, cream, wildtype ] // more common in swamps
        if (ThreadLocalRandom.current().nextInt(100) > WTC || wildType == 4) {
            autosomalGenes[32] = (ThreadLocalRandom.current().nextInt(3) + 1);

        } else {
            autosomalGenes[32] = (3);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC || wildType == 4) {
            autosomalGenes[33] = (ThreadLocalRandom.current().nextInt(3) + 1);

        } else {
            autosomalGenes[33] = (3);
        }

        //Mahogany [ mahogany, wildtype ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[34] = (ThreadLocalRandom.current().nextInt(2) + 1);
        } else {
            if (wildType == 2) {
                autosomalGenes[34] = (1);
            } else {
                autosomalGenes[34] = (2);
            }
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[35] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            autosomalGenes[35] = (2);
        }

        //Lavender [ wildtype, lavender ]
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

        //Dominant White [ dominant white, wildtype, Dun, Smokey ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC || (wildType == 3)) {
            autosomalGenes[38] = (ThreadLocalRandom.current().nextInt(4) + 1);
        } else {
            autosomalGenes[38] = (2);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC || (wildType == 3)) {
            autosomalGenes[39] = (ThreadLocalRandom.current().nextInt(4) + 1);
        } else {
            autosomalGenes[39] = (2);
        }

        //Splash [ black, splash ]
        if (ThreadLocalRandom.current().nextInt(100) > (WTC + ((100 - WTC) / 2))) {
            autosomalGenes[40] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            autosomalGenes[40] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > (WTC + ((100 - WTC) / 2))) {
            autosomalGenes[41] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            autosomalGenes[41] = (1);
        }

        //Fibromelanin [ fibromelanin, wildtype ] // fibro is more common in savannas but still rare
        if (wildType == 2) {
            if (ThreadLocalRandom.current().nextInt(100) > WTC) {
                autosomalGenes[42] = (ThreadLocalRandom.current().nextInt(2) + 1);
            } else {
                autosomalGenes[42] = (2);
            }
            if (ThreadLocalRandom.current().nextInt(100) > WTC) {
                autosomalGenes[43] = (ThreadLocalRandom.current().nextInt(2) + 1);
            } else {
                autosomalGenes[43] = (2);
            }
        } else {
            if (ThreadLocalRandom.current().nextInt(100) > (WTC + ((100 - WTC) / 1.1))) {
                autosomalGenes[42] = (ThreadLocalRandom.current().nextInt(2) + 1);
            } else {
                autosomalGenes[42] = (2);
            }
            if (ThreadLocalRandom.current().nextInt(100) > (WTC + ((100 - WTC) / 1.1))) {
                autosomalGenes[43] = (ThreadLocalRandom.current().nextInt(2) + 1);
            } else {
                autosomalGenes[43] = (2);
            }
        }

        //yellow shanks [ white, yellow, superyellow ]
        if ((ThreadLocalRandom.current().nextInt(100) > (WTC + ((100 - WTC) / 2)) && wildType != 0) || wildType == 4) {
            autosomalGenes[44] = (ThreadLocalRandom.current().nextInt(3) + 1);

        } else {
            if (wildType == 1) {
                autosomalGenes[44] = (1);
            } else {
                autosomalGenes[44] = (2);
            }
        }       //homozygous white legs only in jungle
        if (ThreadLocalRandom.current().nextInt(100) > (WTC + ((100 - WTC) / 2))) {
            if (wildType == 1) {
                autosomalGenes[45] = (ThreadLocalRandom.current().nextInt(3) + 1);
            } else {
                autosomalGenes[45] = (ThreadLocalRandom.current().nextInt(2) + 2);
            }
        } else {
            if (wildType == 1) {
                autosomalGenes[45] = (1);
            } else {
                autosomalGenes[45] = (2);
            }
        }

        //Rose [ rose, rose2, wildtype ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[46] = (ThreadLocalRandom.current().nextInt(3) + 1);

        } else {
            autosomalGenes[46] = (3);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[47] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            autosomalGenes[47] = (3);
        }

        //Pea [ pea, wildtype ]
        if ((ThreadLocalRandom.current().nextInt(100) > WTC && (wildType == 0 || wildType == 3))) {
            autosomalGenes[48] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            if (wildType == 3) {
                autosomalGenes[48] = (1);
            } else {
                autosomalGenes[48] = (2);
            }
        }
        if ((ThreadLocalRandom.current().nextInt(100) > WTC && (wildType == 0 || wildType == 3))) {
            autosomalGenes[49] = (ThreadLocalRandom.current().nextInt(2) + 1);
        } else {
            if (wildType == 3) {
                autosomalGenes[49] = (1);
            } else {
                autosomalGenes[49] = (2);
            }
        }

        //Duplex comb or v comb [ wildtype, duplexV, duplexC ]   // cold biome exclusive
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            int i = ThreadLocalRandom.current().nextInt(2) + 1;
            if (i == 2) {
                autosomalGenes[50] = wildType == 3 ? 2 : 3;
            } else {
                autosomalGenes[50] = 1;
            }
        } else {
            autosomalGenes[50] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            int i = ThreadLocalRandom.current().nextInt(2) + 1;
            if (i == 2) {
                autosomalGenes[51] = wildType == 3 ? 2 : 3;
            } else {
                autosomalGenes[51] = 1;
            }
        } else {
            autosomalGenes[51] = (1);
        }

        //Naked neck [ naked neck, wildtype ] // savanna exclusive
        if (ThreadLocalRandom.current().nextInt(100) > WTC && wildType == 2) {
            autosomalGenes[52] = (ThreadLocalRandom.current().nextInt(2) + 1);
        } else {
            autosomalGenes[52] = (2);
        }
        //no wild homozygous naked neck
        autosomalGenes[53] = (2);


        //Crest [ normal crest, forward crest, wildtype ]
        if (wildType == 3 && ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[54] = (ThreadLocalRandom.current().nextInt(3) + 1);
        } else {
            autosomalGenes[54] = (3);
        }
        if (wildType == 3 && ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[55] = (ThreadLocalRandom.current().nextInt(3) + 1);
        } else {
            autosomalGenes[55] = (3);
        }

        //beard [ beard, wildtype ]
        if (wildType == 3 && ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[56] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            autosomalGenes[56] = (2);
        }
        if (wildType == 3 && ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[57] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            autosomalGenes[57] = (2);
        }

        //Foot feather 1 [ small foot feather, big foot feather, wildtype ]
        if (wildType == 3 && ThreadLocalRandom.current().nextInt(100) > (WTC / 2)) {
            autosomalGenes[58] = (ThreadLocalRandom.current().nextInt(3) + 1);
        } else {
            autosomalGenes[58] = (3);
        }
        if (wildType == 3 && ThreadLocalRandom.current().nextInt(100) > (WTC / 2)) {
            autosomalGenes[59] = (ThreadLocalRandom.current().nextInt(3) + 1);
        } else {
            autosomalGenes[59] = (3);
        }

        //Foot feather enhancer [ enhancer, wildtype ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC || wildType == 3) {
            autosomalGenes[60] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            autosomalGenes[60] = (2);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC || wildType == 3) {
            autosomalGenes[61] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            autosomalGenes[61] = (2);
        }

        //Blue eggs [ blueSaturated, wildtype, blueMedium, blueLight ] // swamp exclusive
        if (wildType == 4) {
            if (ThreadLocalRandom.current().nextBoolean()) {
                autosomalGenes[62] = (2);
            } else if (ThreadLocalRandom.current().nextBoolean()) {
                autosomalGenes[62] = (4);
            } else  if (ThreadLocalRandom.current().nextBoolean()) {
                autosomalGenes[62] = (3);
            } else {
                autosomalGenes[62] = (1);
            }

        } else {
            autosomalGenes[62] = (2);
        }
        if (wildType == 4) {
            if (ThreadLocalRandom.current().nextBoolean()) {
                autosomalGenes[63] = (2);
            } else if (ThreadLocalRandom.current().nextBoolean()) {
                autosomalGenes[63] = (4);
            } else  if (ThreadLocalRandom.current().nextBoolean()) {
                autosomalGenes[63] = (3);
            } else {
                autosomalGenes[63] = (1);
            }

        } else {
            autosomalGenes[63] = (2);
        }

        //Brown Pink eggs [ brown, pink, wildtype ] //pink more likely in savanna
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[64] = (ThreadLocalRandom.current().nextInt(3) + 1);

        } else {
            if (wildType == 2) {
                autosomalGenes[64] = (2);
            } else {
                autosomalGenes[64] = (3);
            }
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[65] = (ThreadLocalRandom.current().nextInt(3) + 1);

        } else {
            if (wildType == 2) {
                autosomalGenes[65] = (2);
            } else {
                autosomalGenes[65] = (3);
            }
        }

        //Brown Cream eggs [ brown, cream, wildtype ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC || wildType == 4) {
            autosomalGenes[66] = (ThreadLocalRandom.current().nextInt(3) + 1);

        } else {
            if (wildType == 1 || wildType == 2) {
                autosomalGenes[66] = (3);
            } else {
                autosomalGenes[66] = (2);
            }
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[67] = (ThreadLocalRandom.current().nextInt(3) + 1);

        } else {
            if (wildType == 1) {
                autosomalGenes[67] = (3);
            } else {
                autosomalGenes[67] = (2);
            }
        }

        //Darker eggs [ darker, wildtype ] // darker is more probable in swamps but still rare
        if (wildType == 4) {
            if (ThreadLocalRandom.current().nextInt(100) > WTC) {
                autosomalGenes[68] = (ThreadLocalRandom.current().nextInt(2) + 1);
            } else {
                autosomalGenes[68] = (2);
            }
            if (ThreadLocalRandom.current().nextInt(100) > WTC) {
                autosomalGenes[69] = (ThreadLocalRandom.current().nextInt(2) + 1);
            } else {
                autosomalGenes[69] = (2);
            }
        } else {
            if (ThreadLocalRandom.current().nextInt(100) > (WTC + ((100 - WTC) / 2))) {
                autosomalGenes[68] = (ThreadLocalRandom.current().nextInt(2) + 1);

            } else {
                autosomalGenes[68] = (2);
            }
            if (ThreadLocalRandom.current().nextInt(100) > (WTC + ((100 - WTC) / 2))) {
                autosomalGenes[69] = (ThreadLocalRandom.current().nextInt(2) + 1);

            } else {
                autosomalGenes[69] = (2);
            }
        }

        //creeper genes [ wildtype, creeper ] (short legs not exploding bushes)
        if (ThreadLocalRandom.current().nextInt(100) > (WTC + ((100 - WTC) / 2))) {
            autosomalGenes[70] = (ThreadLocalRandom.current().nextInt(2) + 1);
            autosomalGenes[71] = (1);
        } else {
            autosomalGenes[70] = (1);
            autosomalGenes[71] = (1);
        }

        //rumpless [ wildtype, rumpless ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[72] = (ThreadLocalRandom.current().nextInt(2) + 1);
            autosomalGenes[73] = (1);
        } else {
            autosomalGenes[72] = (1);
            autosomalGenes[73] = (1);
        }

        //base size [ smaller, wildtype, larger ] incomplete dominant
        if (ThreadLocalRandom.current().nextInt(100) > WTC / 4) {
            autosomalGenes[74] = (ThreadLocalRandom.current().nextInt(3) + 1);
        } else {
            autosomalGenes[74] = (2);
        }

        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[75] = (ThreadLocalRandom.current().nextInt(3) + 1);
        } else {
            autosomalGenes[75] = (2);
        }

        //Size subtraction [ smaller, normal+, smallest ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[76] = (ThreadLocalRandom.current().nextInt(3) + 1);
        } else {
            autosomalGenes[76] = (2);
        }

        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[77] = (ThreadLocalRandom.current().nextInt(3) + 1);
        } else {
            autosomalGenes[77] = (2);
        }

        //Size multiplier [ normal+, larger ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[78] = (ThreadLocalRandom.current().nextInt(2) + 1);
            autosomalGenes[79] = (1);
        } else {
            autosomalGenes[78] = (1);
            autosomalGenes[79] = (1);
        }

        //small comb [ small, normal+ ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[80] = (ThreadLocalRandom.current().nextInt(2) + 1);
            autosomalGenes[81] = (2);
        } else {
            autosomalGenes[80] = (2);
            autosomalGenes[81] = (2);
        }

        //large comb [ large, normal+ ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[82] = (ThreadLocalRandom.current().nextInt(2) + 1);
            autosomalGenes[83] = (2);
        } else {
            autosomalGenes[82] = (2);
            autosomalGenes[83] = (2);
        }

        //waddle reducer [ small, normal+ ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[84] = (ThreadLocalRandom.current().nextInt(2) + 1);
            autosomalGenes[85] = (2);
        } else {
            autosomalGenes[84] = (2);
            autosomalGenes[85] = (2);
        }

        //wing placement near back [ centered+, up on back, centered2 ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[86] = (ThreadLocalRandom.current().nextInt(3) + 1);
        } else {
            if (wildType == 1) {
                autosomalGenes[86] = (1);
            } else if (wildType == 2) {
                autosomalGenes[86] = (3);
            } else {
                autosomalGenes[86] = (2);
            }
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[87] = (ThreadLocalRandom.current().nextInt(3) + 1);
        } else {
            if (wildType == 1) {
                autosomalGenes[87] = (1);
            } else if (wildType == 2) {
                autosomalGenes[87] = (3);
            } else {
                autosomalGenes[87] = (2);
            }
        }

        //wings down [ centered+, tilted down, pointed down ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[88] = (ThreadLocalRandom.current().nextInt(3) + 1);
            autosomalGenes[89] = (1);
        } else {
            autosomalGenes[88] = (1);
            autosomalGenes[89] = (1);
        }

        //wing length [ normal+, 5 short ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[90] = (ThreadLocalRandom.current().nextInt(2) + 1);
            autosomalGenes[91] = (1);
        } else {
            autosomalGenes[90] = (1);
            autosomalGenes[91] = (1);
        }

        //wing thickness [ normal+, 3 wide ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[92] = (ThreadLocalRandom.current().nextInt(2) + 1);
            autosomalGenes[93] = (1);
        } else {
            autosomalGenes[92] = (1);
            autosomalGenes[93] = (1);
        }

        //wing angle multiplier [none+, 1.1, 1.5]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[94] = (ThreadLocalRandom.current().nextInt(3) + 1);
        } else {
            autosomalGenes[94] = (1);
        }

        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[95] = (ThreadLocalRandom.current().nextInt(3) + 1);
        } else {
            autosomalGenes[95] = (1);
        }

        //wing angle multiplier 2 [none+, 1.1, 1.5]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[96] = (ThreadLocalRandom.current().nextInt(3) + 1);
        } else {
            autosomalGenes[96] = (1);
        }

        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[97] = (ThreadLocalRandom.current().nextInt(3) + 1);
        } else {
            autosomalGenes[97] = (1);
        }

        // Darkbrown [ darkbrown, wildtype ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[98] = (ThreadLocalRandom.current().nextInt(2) + 1);
        } else {
            autosomalGenes[98] = (2);
        }

        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[99] = (ThreadLocalRandom.current().nextInt(2) + 1);
        } else {
            autosomalGenes[99] = (2);
        }

        // Charcoal [ wildtype, charcoal ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[100] = (ThreadLocalRandom.current().nextInt(2) + 1);
        } else {
            autosomalGenes[100] = (1);
        }

        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[101] = (ThreadLocalRandom.current().nextInt(2) + 1);
        } else {
            autosomalGenes[101] = (1);
        }

        // Vulture Hocks [ wildtype, vulture hocks ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[102] = (ThreadLocalRandom.current().nextInt(2) + 1);
        } else {
            autosomalGenes[102] = (1);
        }

        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[103] = (ThreadLocalRandom.current().nextInt(2) + 1);
        } else {
            autosomalGenes[103] = (1);
        }

        // Frizzle [ wildtype, frizzle ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[104] = (ThreadLocalRandom.current().nextInt(2) + 1);
            autosomalGenes[105] = (1);
        } else {
            autosomalGenes[104] = (1);
            autosomalGenes[105] = (1);
        }

        // Silkie [ wildtype, silkie ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[106] = (ThreadLocalRandom.current().nextInt(2) + 1);
        } else {
            autosomalGenes[106] = (1);
        }

        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[107] = (ThreadLocalRandom.current().nextInt(2) + 1);
        } else {
            autosomalGenes[107] = (1);
        }

        // Scaless [ wildtype, scaleless ]
        autosomalGenes[108] = ThreadLocalRandom.current().nextInt(200) > 198 ? 2 : 1;
        autosomalGenes[109] = (1);

        // Adrenaline A [ more alert, moderate alertness ,less alert ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[110] = (ThreadLocalRandom.current().nextInt(3) + 1);
        } else {
            autosomalGenes[110] = (2);
        }

        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[111] = (ThreadLocalRandom.current().nextInt(3) + 1);
        } else {
            autosomalGenes[111] = (2);
        }

        // Adrenaline B [ more alert, moderate alertness ,less alert ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[112] = (ThreadLocalRandom.current().nextInt(3) + 1);
        } else {
            autosomalGenes[112] = (2);
        }

        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[113] = (ThreadLocalRandom.current().nextInt(3) + 1);
        } else {
            autosomalGenes[113] = (2);
        }

        // Adrenaline C [ more alert, moderate alertness ,less alert ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[114] = (ThreadLocalRandom.current().nextInt(3) + 1);
        } else {
            autosomalGenes[114] = (2);
        }

        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[115] = (ThreadLocalRandom.current().nextInt(3) + 1);
        } else {
            autosomalGenes[115] = (2);
        }

        // The Dumb [ Dom.Dumb, dumb, normal ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[116] = (ThreadLocalRandom.current().nextInt(3) + 1);
        } else {
            autosomalGenes[116] = (3);
        }

        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[117] = (ThreadLocalRandom.current().nextInt(3) + 1);
        } else {
            autosomalGenes[117] = (3);
        }

        // The Clever [ normal, clever, rec. clever ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[118] = (ThreadLocalRandom.current().nextInt(3) + 1);
        } else {
            autosomalGenes[118] = (1);
        }

        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[119] = (ThreadLocalRandom.current().nextInt(3) + 1);
        } else {
            autosomalGenes[119] = (1);
        }

        // Anger A [ neutral, grouchy, aggressive ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[120] = (ThreadLocalRandom.current().nextInt(3) + 1);
        } else {
            autosomalGenes[120] = (1);
        }

        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[121] = (ThreadLocalRandom.current().nextInt(3) + 1);
        } else {
            autosomalGenes[121] = (1);
        }

        // Anger B [ flighty, neutral, aggressive ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[122] = (ThreadLocalRandom.current().nextInt(3) + 1);
        } else {
            autosomalGenes[122] = (1);
        }

        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[123] = (ThreadLocalRandom.current().nextInt(3) + 1);
        } else {
            autosomalGenes[123] = (1);
        }

        // Flightiness A [ flighty, neutral, shit scared(erratic booster) ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[124] = (ThreadLocalRandom.current().nextInt(3) + 1);
        } else {
            autosomalGenes[124] = (1);
        }

        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[125] = (ThreadLocalRandom.current().nextInt(3) + 1);
        } else {
            autosomalGenes[125] = (1);
        }

        // Flightiness B [ very flighty, nervous, neutral ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[126] = (ThreadLocalRandom.current().nextInt(3) + 1);
        } else {
            autosomalGenes[126] = (1);
        }

        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[127] = (ThreadLocalRandom.current().nextInt(3) + 1);
        } else {
            autosomalGenes[127] = (1);
        }

        // Wildness [ chaotic, wild, moderate, semi-predictable, predictable ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[128] = (ThreadLocalRandom.current().nextInt(5) + 1);
        } else {
            autosomalGenes[128] = (2);
        }

        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[129] = (ThreadLocalRandom.current().nextInt(5) + 1);
        } else {
            autosomalGenes[129] = (2);
        }

        // selfishness [ selfish, selfless, normal ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[130] = (ThreadLocalRandom.current().nextInt(3) + 1);
        } else {
            autosomalGenes[130] = (3);
        }

        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[131] = (ThreadLocalRandom.current().nextInt(3) + 1);
        } else {
            autosomalGenes[131] = (3);
        }

        // protectiveness [ protective - neutral ] //ups the fear/aggression if self,herd or baby is attacked
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[132] = (ThreadLocalRandom.current().nextInt(7) + 1);
        } else {
            autosomalGenes[132] = (3);
        }

        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[133] = (ThreadLocalRandom.current().nextInt(7) + 1);
        } else {
            autosomalGenes[133] = (3);
        }

        // curiosity [ curious - neutral ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[132] = (ThreadLocalRandom.current().nextInt(7) + 1);
        } else {
            autosomalGenes[132] = (3);
        }

        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[133] = (ThreadLocalRandom.current().nextInt(7) + 1);
        } else {
            autosomalGenes[133] = (3);
        }

        // sociable [ sociable - neutral ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[132] = (ThreadLocalRandom.current().nextInt(7) + 1);
        } else {
            autosomalGenes[132] = (3);
        }

        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[133] = (ThreadLocalRandom.current().nextInt(7) + 1);
        } else {
            autosomalGenes[133] = (3);
        }

        // empathetic/mothering [ mothering - neutral ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[134] = (ThreadLocalRandom.current().nextInt(7) + 1);
        } else {
            autosomalGenes[134] = (3);
        }

        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[135] = (ThreadLocalRandom.current().nextInt(7) + 1);
        } else {
            autosomalGenes[135] = (3);
        }

        // confidence [ neutral - confidence] (in the logic it should be partially negated by fearful attributes for some behaviours)
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[136] = (ThreadLocalRandom.current().nextInt(7) + 1);
        } else {
            autosomalGenes[136] = (3);
        }

        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[137] = (ThreadLocalRandom.current().nextInt(7) + 1);
        } else {
            autosomalGenes[137] = (3);
        }

        // playfulness [ playful - neutral ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[138] = (ThreadLocalRandom.current().nextInt(7) + 1);
        } else {
            autosomalGenes[138] = (3);
        }

        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[139] = (ThreadLocalRandom.current().nextInt(7) + 1);
        } else {
            autosomalGenes[139] = (3);
        }

        // food drive A [ loves food - neutral ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[140] = (ThreadLocalRandom.current().nextInt(6) + 1);
        } else {
            autosomalGenes[140] = (3);
        }

        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[141] = (ThreadLocalRandom.current().nextInt(6) + 1);
        } else {
            autosomalGenes[141] = (3);
        }

        // food drive B [ eats to live - neutral ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[142] = (ThreadLocalRandom.current().nextInt(6) + 1);
        } else {
            autosomalGenes[142] = (3);
        }

        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[143] = (ThreadLocalRandom.current().nextInt(6) + 1);
        } else {
            autosomalGenes[143] = (3);
        }

        // food drive C [ Always hungry, neutral, under eats ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[144] = (ThreadLocalRandom.current().nextInt(3) + 1);
        } else {
            autosomalGenes[144] = (2);
        }

        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[145] = (ThreadLocalRandom.current().nextInt(3) + 1);
        } else {
            autosomalGenes[145] = (2);
        }

        //BodyBig [normal, big]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[146] = (ThreadLocalRandom.current().nextInt(2) + 1);
        } else {
            autosomalGenes[146] = (1);
        }

        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[147] = (ThreadLocalRandom.current().nextInt(2) + 1);
        } else {
            autosomalGenes[147] = (1);
        }

        //BodySmall [normal, small]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[148] = (ThreadLocalRandom.current().nextInt(2) + 1);
        } else {
            autosomalGenes[148] = (2);
        }

        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[149] = (ThreadLocalRandom.current().nextInt(2) + 1);
        } else {
            autosomalGenes[149] = (2);
        }

        //EarTuft [normal, Eartuft]
        if (ThreadLocalRandom.current().nextInt(100) > WTC + ((100-WTC)/2)) {
            autosomalGenes[150] = (ThreadLocalRandom.current().nextInt(2) + 1);
            autosomalGenes[151] = (1);
        } else {
            autosomalGenes[150] = (1);
            autosomalGenes[151] = (1);
        }

        //ear size 1 [1-4 = -1 , 5-8 = 0, 9-12 = +1]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[152] = (ThreadLocalRandom.current().nextInt(6) + 1);
        } else {
            if (wildType == 1) {
                autosomalGenes[152] = (10);
            } else {
                autosomalGenes[152] = (1);
            }
        }

        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[153] = (ThreadLocalRandom.current().nextInt(6) + 7);
        } else {
            if (wildType == 1) {
                autosomalGenes[153] = (10);
            } else {
                autosomalGenes[153] = (7);
            }
        }

        //ear size 2 [1-4 = -1 , 5-8 = 0, 9-12 = +1]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[154] = (ThreadLocalRandom.current().nextInt(6) + 1);
        } else {
            autosomalGenes[154] = (1);
        }

        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[155] = (ThreadLocalRandom.current().nextInt(6) + 7);
        } else {
            autosomalGenes[155] = (7);
        }

        //ear size 3 [1-4 = -1 , 5 = 0, 6-10 = +1 , 10-12 = +1 || +2]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[156] = (ThreadLocalRandom.current().nextInt(6) + 1);
        } else {
            if (wildType == 1) {
                autosomalGenes[156] = (4);
            } else {
                autosomalGenes[156] = (1);
            }
        }

        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[157] = (ThreadLocalRandom.current().nextInt(6) + 7);
        } else {
            if (wildType == 1) {
                autosomalGenes[157] = (4);
            } else {
                autosomalGenes[157] = (7);
            }
        }

        //ear size 4 and whitener [reducer, neutral, adds size, adds white, adds white+ and size]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[158] = (ThreadLocalRandom.current().nextInt(5) + 1);
        } else {
            if (wildType == 1) {
                autosomalGenes[158] = (5);
            } else {
                autosomalGenes[158] = (1);
            }
        }

        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[159] = (ThreadLocalRandom.current().nextInt(5) + 1);
        } else {
            if (wildType == 1) {
                autosomalGenes[159] = (5);
            } else {
                autosomalGenes[159] = (1);
            }
        }

        //ear size 5 [1-4 = -1 , 5 = 0, 6-10 = +1 , 10-12 = +1 || +2]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[160] = (ThreadLocalRandom.current().nextInt(12) + 1);
        } else {
            if (wildType == 1) {
                autosomalGenes[160] = (6);
            } else {
                autosomalGenes[160] = (1);
            }
        }

        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[161] = (ThreadLocalRandom.current().nextInt(12) + 13);
        } else {
            if (wildType == 1) {
                autosomalGenes[161] = (6);
            } else {
                autosomalGenes[161] = (7);
            }
        }

        //ear size 6 [1-4 = -1 , 5 = 0, 6-10 = +1 , 10-12 = +1 || +2]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[162] = (ThreadLocalRandom.current().nextInt(12) + 1);
        } else {
            if (wildType == 1) {
                autosomalGenes[162] = (6);
            } else {
                autosomalGenes[162] = (1);
            }
        }

        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[163] = (ThreadLocalRandom.current().nextInt(12) + 13);
        } else {
            if (wildType == 1) {
                autosomalGenes[163] = (4);
            } else {
                autosomalGenes[163] = (7);
            }
        }

        //ear redness
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[164] = (ThreadLocalRandom.current().nextInt(5) + 1);
        } else {
            if (wildType == 1) {
                autosomalGenes[164] = (5);
            } else {
                autosomalGenes[164] = (1);
            }
        }

        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[165] = (ThreadLocalRandom.current().nextInt(5) + 1);
        } else {
            if (wildType == 1) {
                autosomalGenes[165] = (5);
            } else {
                autosomalGenes[165] = (1);
            }
        }

        //recessive black shanks [normal, blacker shanks]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[166] = (ThreadLocalRandom.current().nextInt(2) + 1);
        } else {
            if (wildType == 1) {
                autosomalGenes[166] = (2);
            } else {
                autosomalGenes[166] = (1);
            }
        }

        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[167] = (ThreadLocalRandom.current().nextInt(2) + 1);
        } else {
            if (wildType == 1) {
                autosomalGenes[167] = (2);
            } else {
                autosomalGenes[167] = (1);
            }
        }

        //long legs [normal, long legs]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[168] = (ThreadLocalRandom.current().nextInt(2) + 1);
        } else {
            autosomalGenes[168] = (1);
        }

        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[169] = (ThreadLocalRandom.current().nextInt(2) + 1);
        } else {
            autosomalGenes[169] = (1);
        }

        //Autosomal Red [Red+, red inhibitor]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[170] = (ThreadLocalRandom.current().nextInt(2) + 1);
        } else {
            autosomalGenes[170] = (1);
        }

        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[171] = (ThreadLocalRandom.current().nextInt(2) + 1);
        } else {
            autosomalGenes[171] = (1);
        }

        //Egg Tinter [wildtype, browner egg]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[172] = (ThreadLocalRandom.current().nextInt(2) + 1);
        } else {
            autosomalGenes[172] = (1);
        }

        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[173] = (ThreadLocalRandom.current().nextInt(2) + 1);
        } else {
            autosomalGenes[173] = (1);
        }

        //Egg Tinter 2 [wildtype, browner egg]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[174] = (ThreadLocalRandom.current().nextInt(2) + 1);
        } else {
            autosomalGenes[174] = (1);
        }

        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[175] = (ThreadLocalRandom.current().nextInt(2) + 1);
        } else {
            autosomalGenes[175] = (1);
        }

        //Egg Tinter 3 [wildtype, browner egg, brownest egg]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[176] = (ThreadLocalRandom.current().nextInt(3) + 1);
        } else {
            autosomalGenes[176] = (1);
        }

        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[177] = (ThreadLocalRandom.current().nextInt(3) + 1);
        } else {
            autosomalGenes[177] = (1);
        }

        //Egg Speckle [wildtype, speckle lighter, speckle ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[178] = (ThreadLocalRandom.current().nextInt(3) + 1);
        } else {
            autosomalGenes[178] = (1);
        }

        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[179] = (ThreadLocalRandom.current().nextInt(3) + 1);
        } else {
            autosomalGenes[179] = (1);
        }

        //Egg marking darkener [wildtype, darker spots ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[180] = (ThreadLocalRandom.current().nextInt(2) + 1);
        } else {
            autosomalGenes[180] = (1);
        }

        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[181] = (ThreadLocalRandom.current().nextInt(2) + 1);
        } else {
            autosomalGenes[181] = (1);
        }

        //Egg marking smudger [wildtype, smudger ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[182] = (ThreadLocalRandom.current().nextInt(2) + 1);
        } else {
            autosomalGenes[182] = (1);
        }

        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[183] = (ThreadLocalRandom.current().nextInt(2) + 1);
        } else {
            autosomalGenes[183] = (1);
        }

        //Vault [wildtype, vault ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[184] = (ThreadLocalRandom.current().nextInt(2) + 1);
        } else {
            autosomalGenes[184] = (1);
        }

        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[185] = (ThreadLocalRandom.current().nextInt(2) + 1);
        } else {
            autosomalGenes[185] = (1);
        }

        //Body Angle1 [wildtype, angled ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[186] = (ThreadLocalRandom.current().nextInt(2) + 1);
        } else {
            autosomalGenes[186] = (1);
        }

        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[187] = (ThreadLocalRandom.current().nextInt(2) + 1);
        } else {
            autosomalGenes[187] = (1);
        }

        //Body Angle2 [wildtype, angled ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[188] = (ThreadLocalRandom.current().nextInt(2) + 1);
        } else {
            autosomalGenes[188] = (1);
        }

        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[189] = (ThreadLocalRandom.current().nextInt(2) + 1);
        } else {
            autosomalGenes[189] = (1);
        }

        //Body Angle3 [wildtype, angled ]
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

        //Body Angle4 [wildtype, angled ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[192] = (ThreadLocalRandom.current().nextInt(2) + 1);
        } else {
            autosomalGenes[192] = (1);
        }

        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[193] = (ThreadLocalRandom.current().nextInt(2) + 1);
        } else {
            autosomalGenes[193] = (1);
        }

        //Body Angle5 [ wildtype, angled ]
        autosomalGenes[194] = ThreadLocalRandom.current().nextInt(100) > WTC ? (ThreadLocalRandom.current().nextInt(2) + 1) : 1;
        autosomalGenes[195] = ThreadLocalRandom.current().nextInt(100) > WTC ? (ThreadLocalRandom.current().nextInt(2) + 1) : 1;

        //Hen Feathering [ wildtype, hen feathering ]
        autosomalGenes[196] = ThreadLocalRandom.current().nextInt(100) > WTC ? (ThreadLocalRandom.current().nextInt(2) + 1) : 1;
        autosomalGenes[197] = ThreadLocalRandom.current().nextInt(100) > WTC ? (ThreadLocalRandom.current().nextInt(2) + 1) : 1;

        //Feather Growth Gene [ wildtype, feather growth ]
        autosomalGenes[198] = ThreadLocalRandom.current().nextInt(100) > WTC ? (ThreadLocalRandom.current().nextInt(2) + 1) : 1;
        autosomalGenes[199] = ThreadLocalRandom.current().nextInt(100) > WTC ? (ThreadLocalRandom.current().nextInt(2) + 1) : 1;

        //paint spot quantity modifier 1
        autosomalGenes[200] = ThreadLocalRandom.current().nextInt(100) > WTC ? (ThreadLocalRandom.current().nextInt(4) + 1) : 1;
        autosomalGenes[201] = ThreadLocalRandom.current().nextInt(100) > WTC ? (ThreadLocalRandom.current().nextInt(4) + 1) : 1;

        //paint spot quantity modifier 2
        autosomalGenes[202] = ThreadLocalRandom.current().nextInt(100) > WTC ? (ThreadLocalRandom.current().nextInt(4) + 1) : 1;
        autosomalGenes[203] = ThreadLocalRandom.current().nextInt(100) > WTC ? (ThreadLocalRandom.current().nextInt(4) + 1) : 1;

        //paint spot size modifier 1
        autosomalGenes[204] = ThreadLocalRandom.current().nextInt(100) > WTC ? (ThreadLocalRandom.current().nextInt(4) + 1) : 1;
        autosomalGenes[205] = ThreadLocalRandom.current().nextInt(100) > WTC ? (ThreadLocalRandom.current().nextInt(4) + 1) : 1;

        //paint spot size modifier 2
        autosomalGenes[206] = ThreadLocalRandom.current().nextInt(100) > WTC ? (ThreadLocalRandom.current().nextInt(4) + 1) : 1;
        autosomalGenes[207] = ThreadLocalRandom.current().nextInt(100) > WTC ? (ThreadLocalRandom.current().nextInt(4) + 1) : 1;

        //paint spot distribution modifier 2
        autosomalGenes[208] = ThreadLocalRandom.current().nextInt(100) > WTC ? (ThreadLocalRandom.current().nextInt(4) + 1) : 1;
        autosomalGenes[209] = ThreadLocalRandom.current().nextInt(100) > WTC ? (ThreadLocalRandom.current().nextInt(4) + 1) : 1;

        //paint spot head restriction modifier
        autosomalGenes[210] = ThreadLocalRandom.current().nextInt(100) > WTC ? (ThreadLocalRandom.current().nextInt(2) + 1) : 1;
        autosomalGenes[211] = ThreadLocalRandom.current().nextInt(100) > WTC ? (ThreadLocalRandom.current().nextInt(2) + 1) : 1;

        //paint spot tail restriction modifier
        autosomalGenes[212] = ThreadLocalRandom.current().nextInt(100) > WTC ? (ThreadLocalRandom.current().nextInt(2) + 1) : 1;
        autosomalGenes[213] = ThreadLocalRandom.current().nextInt(100) > WTC ? (ThreadLocalRandom.current().nextInt(2) + 1) : 1;

        //paint spot body restriction modifier
        autosomalGenes[214] = ThreadLocalRandom.current().nextInt(100) > WTC ? (ThreadLocalRandom.current().nextInt(2) + 1) : 1;
        autosomalGenes[215] = ThreadLocalRandom.current().nextInt(100) > WTC ? (ThreadLocalRandom.current().nextInt(2) + 1) : 1;

        //paint spot limbs restriction modifier
        autosomalGenes[216] = ThreadLocalRandom.current().nextInt(100) > WTC ? (ThreadLocalRandom.current().nextInt(2) + 1) : 1;
        autosomalGenes[217] = ThreadLocalRandom.current().nextInt(100) > WTC ? (ThreadLocalRandom.current().nextInt(2) + 1) : 1;

        autosomalGenes[218] = ThreadLocalRandom.current().nextInt(100) > WTC ? (ThreadLocalRandom.current().nextInt(3) + 1) : 1;
        autosomalGenes[219] = ThreadLocalRandom.current().nextInt(100) > WTC ? (ThreadLocalRandom.current().nextInt(3) + 1) : 1;

        autosomalGenes[220] = ThreadLocalRandom.current().nextInt(100) > WTC ? (ThreadLocalRandom.current().nextInt(3) + 1) : 1;
        autosomalGenes[221] = ThreadLocalRandom.current().nextInt(100) > WTC ? (ThreadLocalRandom.current().nextInt(3) + 1) : 1;

        autosomalGenes[222] = ThreadLocalRandom.current().nextInt(100) > WTC ? (ThreadLocalRandom.current().nextInt(2) + 1) : 1;
        autosomalGenes[223] = ThreadLocalRandom.current().nextInt(100) > WTC ? (ThreadLocalRandom.current().nextInt(2) + 1) : 1;

        autosomalGenes[224] = ThreadLocalRandom.current().nextInt(100) > WTC ? (ThreadLocalRandom.current().nextInt(2) + 1) : 1;
        autosomalGenes[225] = ThreadLocalRandom.current().nextInt(100) > WTC ? (ThreadLocalRandom.current().nextInt(2) + 1) : 1;

        autosomalGenes[226] = ThreadLocalRandom.current().nextInt(100) > WTC ? (ThreadLocalRandom.current().nextInt(2) + 1) : 1;
        autosomalGenes[227] = ThreadLocalRandom.current().nextInt(100) > WTC ? (ThreadLocalRandom.current().nextInt(2) + 1) : 1;

        return new Genes(sexlinkedGenes, autosomalGenes);

    }
}
