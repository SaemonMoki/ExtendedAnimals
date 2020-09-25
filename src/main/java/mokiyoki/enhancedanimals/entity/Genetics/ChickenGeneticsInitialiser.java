package mokiyoki.enhancedanimals.entity.Genetics;

import mokiyoki.enhancedanimals.init.ChickenBreeds;
import mokiyoki.enhancedanimals.util.Breed;
import mokiyoki.enhancedanimals.util.Genes;
import mokiyoki.enhancedanimals.util.Reference;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;

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
//        this.breeds.add(ChickenBreeds.CUTIEPIE);
    }

    public Genes generateNewGenetics(IWorld world, BlockPos pos, boolean generateBreed) {
        return super.generateNewGenetics(world, pos, generateBreed, this.breeds);
    }

    public Genes generateWithBreed(IWorld world, BlockPos pos, String breed) {
        return super.generateWithBreed(world, pos, this.breeds, breed);
    }

    @Override
    public Genes generateLocalWildGenetics(Biome biome, boolean isFlat) {
        int[] sexlinkedGenes = new int[Reference.CHICKEN_SEXLINKED_GENES_LENGTH];
        int[] autosomalGenes = new int[Reference.CHICKEN_AUTOSOMAL_GENES_LENGTH];

        //[ 0=minecraft wildtype, 1=jungle wildtype, 2=savanna wildtype, 3=cold wildtype, 4=swamp wildtype ]
        int wildType = 0;

        if (biome.getDefaultTemperature() >= 0.9F && biome.getDownfall() > 0.8F) // hot and wet (jungle)
        {
            wildType = 1;
        } else if (biome.getDefaultTemperature() >= 0.9F && biome.getDownfall() < 0.3F) // hot and dry (savanna)
        {
            wildType = 2;
        } else if (biome.getDefaultTemperature() < 0.3F) // cold (mountains)
        {
            wildType = 3;
        } else if (biome.getDefaultTemperature() >= 0.8F && biome.getDownfall() > 0.8F) {
            wildType = 4;
        }

        if (isFlat) {
            int randomizeWT = ThreadLocalRandom.current().nextInt(10);
            if (randomizeWT <= 4) {
                wildType = randomizeWT;
            }
        }

//
//            /**
//             * parent linked genes
//             */
//            autosomalGenes.sexlinkedGenes()
//            //Gold [ gold, silver ]
//            .genes(0).set(1)
//                    .setIf(Biome.RainType.SNOW, 2)
//                    .pickIfWTC(1, 2)
//
//            //Chocolate [ wildtype, chocolate ]
//            .genes(2).set(1).pickIf(0.8F, 1, 2)
//
//            //ear size setting genes
//            .genes(4).set(1).setIf(Biome.Category.JUNGLE, 6).pickIfWTC(1, 2, 3, 4, 5, 6)
//
//            //Barred [ wildtype, barred ] //exclusive to savanna
//            .genes(6).set(1).pickIfWithWTC(Biome.Category.SAVANNA, 1, 2)
//
//            //Fibromelanin Suppressor [ wildtype, suppressor ]
//            .genes(8).set(1).pickIfWTC(1, 2)
//
//            //Brown egg genes suppressor [ wildtype, suppressor ]
//            .genes(10).set(1).pickIfWTC(1, 2)
//
//            //white face
//            .genes(12).set(6).pickIfWTC(1, 2, 3, 4, 5, 6)
//
//            //dwarf [ normal, slight dwarf ]
//            .genes(14).set(1).pickIfWTC(1,2,3)
//
//            //dwarf 2 [ normal, very dwarf ]
//            .genes(16).set(1).pickIfWTC(1, 2, 3)
//
//            //large ear inhibitor [ no inhibitor, halving inhibitor ]
//            .genes(18).set(1).pickIfWTC(1, 2, 3)
//
//            /**
//             * Autosomal Genes
//             */
//
//            //these are open to be used for new autosomal genes
//            .autosomalGenes()
//                    .genes(0).set(1)
//                    .genes(2).set(1)
//                    .genes(4).set(1)
//                    .genes(6).set(1)
//                    .genes(8).set(1)
//                    .genes(10).set(1)
//                    .genes(12).set(1)
//                    .genes(14).set(1)
//                    .genes(16).set(1)
//                    .genes(18).set(1)
//
//            //Recessive white [ wild, recessive white, albino ]  //mutation common in temperate areas and swamps
//            .genes(20).set(1).setIf(Biome.Category.PLAINS, 2).pickIfOrWithWTC(Biome.Category.SWAMP, 1, 2).setIf(1F/8192F, 3)
//
//            //Mottled [ wildtype, mottled ]  // cold biome exclusive
//            .genes(22).set(1).pickIfOrWithWTC(Biome.RainType.SNOW, 1, 2)
//
//            //Dlocus [ birchen, duckwing, wheaten, partridge, extended black ]
//            .genes(24).set(2).setIf(Biome.Category.SAVANNA, 4).pickIf(Biome.RainType.SNOW, 1, 5).pickIf(Biome.Category.SWAMP, 2, 3, 4).pickIfWTC(1, 2, 3, 4, 5)
//
//            //Pattern Gene [ pattern, wildtype ] pattern genes is common in savannas
//            .genes(26).set(2).setIf(Biome.Category.SAVANNA, 0.25F, 1).pickIfWTC(1, 2)
//
//            //Colombian [ colombian, wildtype ]
//            .genes(28).set(2).pickIfOrWithWTC(Biome.TempCategory.COLD, 1, 2)
//
//            //Melanized [melanized, wildtype ]
//            .genes(30).set(2).pickIfWTC(1, 2)
//
//            //Dilute [ dilute, cream, wildtype ] // more common in swamps
//            .genes(32).set(3).pickIfOrWithWTC(Biome.Category.SWAMP, 1, 2, 3)
//
//            //Mahogany [ mahogany, wildtype ]
//            .genes(34).set(2).pickIfWTC(1, 2)
//                    .gene(35).pickIfOrWithWTC(Biome.Category.MESA, 0.30F, 1, 2)
//
//            //Lavender [ wildtype, lavender ]
//            .genes(36).set(1).pickIfWTC(1,2)
//
//            //Dominant White [ dominant white, wildtype ]
//            .genes(38).set(2).pickIf(Biome.RainType.SNOW, 1, 2)
//
//            //Splash [ black, splash ]
//            .genes(40).set(1).pickIfWithWTC(0.5F, 1, 2)
//
//            //Fibromelanin [ fibromelanin, wildtype ] // fibro is more common in savannas but still rare
//            .genes(42).set(2).pickIfWithWTC(0.1F, 1, 2).pickIfWithWTC(Biome.Category.SAVANNA, 1, 2)
//
//            //yellow shanks [ white, yellow, superyellow ]
//            .genes(44).set(2).setIf(Biome.TempCategory.WARM, 1).pickIfWithWTC(2.0F, 1, 2).pickIfWithWTC(Biomes.SUNFLOWER_PLAINS, 2, 3)
//
//            //Rose [ rose, rose2, wildtype ]
//            .genes(46).set(3)
//                    .gene(46).pickIfWTC(1, 2, 3)
//                    .gene(47).pickIfWTC(1, 1, 2)
//
//            //Pea [ pea, wildtype ]
//            .genes(48).set(2).setIfCold(1).pickIfWithWTC(Biome.Category.PLAINS, 1, 2).pickIfWithWTC(Biome.RainType.SNOW, 1, 2)
//
//            //Duplex comb or v comb [ wildtype, duplexV, duplexC ]   // reversed dominance, cold biome exclusive
//            .genes(50).set(1).pickIfWithWTC(Biome.RainType.SNOW, 1, 2)
//
//            //Naked neck [ naked neck, wildtype ] // savanna exclusive
//            .genes(52).set(2)
//                    .gene(53).pickIfWithWTC(Biome.Category.SAVANNA, 1, 2).pickIfWithWTC(Biome.Category.DESERT, 1, 2)
//
//            //Crest [ normal crest, forward crest, wildtype ]
//            .genes(54).set(3).pickIfWithWTC(Biome.TempCategory.COLD, 1, 2, 3)
//
//            //beard [ beard, wildtype ]
//            .genes(56).set(2).pickIfWithWTC(Biome.TempCategory.COLD, 1, 2)
//
//            //Foot feather 1 [ small foot feather, big foot feather, wildtype ]
//            .genes(58).set(3).pickIfWithWTC(Biome.TempCategory.COLD, 1.5F, 1, 2, 3)
//
//            //Foot feather enhancer [ enhancer, wildtype ]
//            .genes(60).set(2).pickIfWithWTC(Biome.TempCategory.COLD, 1, 2)
//
//            //Blue eggs [ blueSaturated, wildtype, blueMedium, blueLight ] // swamp exclusive
//            .genes(62).set(2).pickIf(Biome.Category.SWAMP, 2, 4, 4).setIf(Biome.Category.SWAMP, 0.01F, 3).setIf(Biome.Category.SWAMP, 0.001F, 1)
//
//            //Brown Pink eggs [ brown, pink, wildtype ] //pink more likely in savanna
//            .genes(64).set(3).setIf(Biome.TempCategory.WARM, 2).setIf(Biome.Category.JUNGLE, 3).pickIfWTC(1, 2, 3)
//
//            //Brown Cream eggs [ brown, cream, wildtype ]
//            .genes(66).set(2).setIf(Biome.TempCategory.WARM, 3).pickIfWTC(1, 2, 3)
//
//            //Darker eggs [ darker, wildtype ] // darker is more probable in swamps but still rare
//            .genes(68).set(2).pickIfWithWTC(Biome.Category.SWAMP, 1, 2).pickIfWithWTC(0.33F, 1, 2)
//
//            //creeper genes [ wildtype, creeper ] (short legs not exploding bushes)
//            .genes(70).set(1).gene(71).pickIfWithWTC(0.3F, 1, 2)
//
//            //rumpless [ wildtype, rumpless ]
//            .genes(72).set(1).gene(73).pickIfWithWTC(0.3F, 1, 2)
//
//            //base size [ smaller, wildtype, larger ] incomplete dominant
//            .genes(74).set(2).pickIfWithWTC(0.2F, 1, 2, 3)
//
//            //Size subtraction [ smaller, normal+, smallest ]
//            .genes(76).set(2).pickIfWTC(1, 2, 3)
//
//            //Size multiplier [ normal+, larger ]
//            .genes(78).set(1).gene(78).pickIfWTC(1, 2)
//
//            //small comb [ small, normal+ ]
//            .genes(80).set(2).gene(80).pickIfWTC(1, 2)
//
//            //large comb [ large, normal+ ]
//            .genes(82).set(2).gene(82).pickIfWTC(1, 2)
//
//            //waddle reducer [ small, normal+ ]
//            .genes(84).set(2).pickIfWTC(1, 2)
//
//            //wing placement near back [ centered+, up on back, centered2 ]
//            .genes(86).set(2).setIf(Biome.TempCategory.WARM, 3).setIf(Biome.Category.JUNGLE, 1).pickIfWTC(1, 2, 3)
//
//            //wings down [ centered+, tilted down, pointed down ]
//            .genes(88).set(1).pickIfWTC(1, 2, 3)
//
//            //wing length [ normal+, (5 pixel size) short ]
//            .genes(90).set(1).gene(80).pickIfWTC(1, 2)
//
//            //wing thickness [ normal+, 3 pixels wide ]
//            .genes(92).set(1).gene(92).pickIfWTC(1, 2)
//
//            //wing angle multiplier [none+, 1.1, 1.5]
//            .genes(94).set(1).pickIfWTC(1, 2, 3)
//
//            //wing angle multiplier 2 [none+, 1.1, 1.5]
//            .genes(96).set(1).pickIfWTC(1, 2, 3)
//
//            // Darkbrown [ darkbrown, wildtype ]
//            .genes(98).set(2).pickIfWTC(1, 2)
//
//            // Charcoal [ wildtype, charcoal ]
//            .genes(100).set(1).pickIfWTC(1, 2)
//
//            // Vulture Hocks [ wildtype, vulture hocks ]
//            .genes(102).set(1).pickIfWTC(1, 2)
//
//            // Frizzle [ wildtype, frizzle ]
//            .genes(104).set(1)
//
//            // Silkie [ wildtype, silkie ]
//            .genes(106).set(1)
//
//            // Scaless [ wildtype, scaleless ]
//            .genes(108).set(1)
//
//            // Adrenaline A [ moderate alertness, more alert ,less alert ]
//            .genes(110).set(1).pickIfWTC(1, 2, 3)
//
//            // Adrenaline B [ more alert, moderate alertness ,less alert ]
//            .genes(112).set(1).pickIfWTC(1, 2, 3)
//
//            // Adrenaline C [ more alert, moderate alertness ,less alert ]
//            .genes(114).set(1).pickIfWTC(1, 2, 3)
//
//            // The Dumb [ normal, Dom.Dumb, dumb ]
//            .genes(116).set(1).pickIfWTC(1, 2, 3)
//
//            // The Clever [ normal, clever, rec. clever ]
//            .genes(118).set(1).pickIfWTC(1, 2, 3)
//
//            // Anger A [ neutral, grouchy, aggressive ]
//            .genes(120).set(1).pickIfWTC(1, 2, 3)
//
//            // Anger B [ neutral, flighty, aggressive ]
//            .genes(122).set(1).pickIfWTC(1, 2, 3)
//
//            // Flightiness A [ flighty, neutral, shit scared(erratic booster) ]
//            .genes(124).set(1).pickIfWTC(1, 2, 3)
//
//            // Flightiness B [ very flighty, nervous, neutral ]
//            .genes(126).set(1).pickIfWTC(1, 2, 3)
//
//            // Wildness [ chaotic, wild, moderate, semi-predictable, predictable ]
//            .genes(128).set(2).pickIfWTC(1, 2, 3, 4, 5)
//
//            // selfishness [ normal, selfish, selfless ]
//            .genes(130).set(1).pickIfWTC(1, 2, 3)
//
//            // protectiveness [ protective - neutral ] //ups the fear/aggression if self,herd or baby is attacked
//            .genes(132).set(3).pickFromRangeWithWTC(1, 7)
//
//            // curiosity [ curious - neutral ]
//            // sociable [ sociable - neutral ]
//
//            // empathetic/mothering [ mothering - neutral ]
//            .genes(134).set(3).pickFromRangeWithWTC(1, 7)
//
//            // confidence [ neutral - confidence] (in the logic it should be partially negated by fearful attributes for some behaviours)
//            .genes(136).set(3).pickFromRangeWithWTC(1, 7)
//
//            // playfulness [ playful - neutral ]
//            .genes(138).set(3).pickFromRangeWithWTC(1, 7)
//
//            // food drive A [ loves food - neutral ]
//            .genes(140).set(3).pickFromRangeWithWTC(1, 6)
//
//            // food drive B [ eats to live - neutral ]
//            .genes(142).set(3).pickFromRangeWithWTC(1, 6)
//
//            // food drive C [ Always hungry, neutral, under eats ]
//            .genes(144).set(2).pickIfWTC(1, 2, 3)
//
//            //BodyBig [normal, big]
//            .genes(146).set(1).pickIfWTC(1, 2)
//
//            //BodySmall [normal, small]
//            .genes(148).set(2).pickIfWTC(1, 2)
//
//            //EarTuft [normal, Eartuft]
//            .genes(150).set(1).gene(150).pickIfWithWTC(0.3F, 1, 2)
//
//            //ear size 1 [1-4 = -1 , 5-8 = 0, 9-12 = +1]
//            .genes(152).set(1, 7).setIf(Biome.Category.JUNGLE, 10).gene(152).pickFromRangeWithWTC(1, 6)
//            .gene(153).pickFromRangeWithWTC(6, 12)
//
//            //ear size 2 [1-4 = -1 , 5-8 = 0, 9-12 = +1]
//            .gene(154).set(1).pickFromRangeWithWTC(1, 6)
//            .gene(155).set(7).pickFromRangeWithWTC(6, 12)
//
//            //ear size 3 [1-4 = -1 , 5 = 0, 6-10 = +1 , 10-12 = +1 || +2]
//            .genes(156).set(1, 7).setIf(Biome.Category.JUNGLE, 4).gene(156).pickFromRangeWithWTC(1, 6)
//            .gene(157).pickFromRangeWithWTC(6, 12)
//
//            //ear size 4 and whitener [reducer, nuetral, adds size, adds white, adds white+ and size]
//            .genes(158).set(1).setIf(Biome.Category.JUNGLE, 5).pickFromRangeWithWTC(1, 5)
//
//            //ear size 5 [1-4 = -1 , 5 = 0, 6-10 = +1 , 10-12 = +1 || +2]
//            .genes(160).set(1, 7).setIf(Biome.Category.JUNGLE, 6).gene(160).pickFromRangeWithWTC(1, 12)
//                    .gene(161).pickFromRangeWithWTC(12, 24)
//
//            //ear size 6 [1-4 = -1 , 5 = 0, 6-10 = +1 , 10-12 = +1 || +2]
//            .genes(162).set(1, 7).setIf(Biome.Category.JUNGLE, 6, 4).gene(162).pickFromRangeWithWTC(1, 12)
//                    .gene(163).pickFromRangeWithWTC(12, 24)
//
//            //ear redness
//            .genes(164).set(1).setIf(Biome.Category.JUNGLE, 5).pickFromRangeWithWTC(1, 5)
//
//            //recessive black shanks [normal, blacker shanks]
//            .genes(166).set(1).setIf(Biome.Category.JUNGLE, 2).pickIfWTC(1, 2)
//
//            //long legs [normal, long legs]
//            .genes(168).set(1).pickIfWTC(1, 2)
//
//            //Autosomal Red [Red+, red inhibitor]
//            .genes(170).set(1).pickIfWTC(1, 2)
//
//            //Egg Tinter [wildtype, browner egg]
//            .genes(172).set(1).pickIfWTC(1, 2)
//
//            //Egg Tinter 2 [wildtype, browner egg]
//            .genes(174).set(1).pickIfWTC(1, 2)
//
//            //Egg Tinter 3 [wildtype, browner egg, brownest egg]
//            .genes(176).set(1).pickIfWTC(1, 2, 3)
//
//            //Egg Speckle [wildtype, speckle lighter, speckle ]
//            .genes(178).set(1).pickIfWTC(1, 2, 3)
//
//            //Egg marking darkener [wildtype, darker spots ]
//            .genes(180).set(1).pickIfWTC(1, 2)
//
//            //Egg marking smudger [wildtype, smudger ]
//            .genes(182).set(1).pickIfWTC(1, 2);
//            //Quirk ideas:
//            //favourite flavours/foods
//            //phobias: heights, certain mobs, swords/sticks/axes in hand, fire/lava, things bigger than them running
//            //loves: heights, warm places, food,
//
//            // TODO here: genes for egg hatch chance when thrown, egg laying rate, and chicken ai modifiers
//
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
            if (wildType == 0) {
                autosomalGenes[20] = (2);
            } else {
                autosomalGenes[20] = (1);
            }
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC || wildType == 4) {
            autosomalGenes[21] = (ThreadLocalRandom.current().nextInt(2) + 1);
        } else {
            if (wildType == 0) {
                autosomalGenes[21] = (2);
            } else {
                autosomalGenes[21] = (1);
            }
        }

        //Mottled [ wildtype, mottled ]  // cold biome exclusive
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
            if (ThreadLocalRandom.current().nextInt(100) > (WTC / 2)) {
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

        //Dominant White [ dominant white, wildtype ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC || (wildType == 3)) {
            autosomalGenes[38] = (ThreadLocalRandom.current().nextInt(2) + 1);
        } else {
            autosomalGenes[38] = (2);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC || (wildType == 3)) {
            autosomalGenes[39] = (ThreadLocalRandom.current().nextInt(2) + 1);
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

        //Duplex comb or v comb [ wildtype, duplexV, duplexC ]   // reversed dominance, cold biome exclusive
        if ((ThreadLocalRandom.current().nextInt(100) > WTC) && wildType == 3) {
            autosomalGenes[50] = (ThreadLocalRandom.current().nextInt(2) + 1);
        } else {
            autosomalGenes[50] = (1);
        }
        if ((ThreadLocalRandom.current().nextInt(100) > WTC) && wildType == 3) {
            autosomalGenes[51] = (ThreadLocalRandom.current().nextInt(2) + 1);
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
        //    if (ThreadLocalRandom.current().nextInt(200) > 199) {
        //        autosomalGenes[108] = (ThreadLocalRandom.current().nextInt(10) + 1);
        //        if (autosomalGenes[108] != 2) {
        //            autosomalGenes[108] = 1;
        //        }
        //        autosomalGenes[109] = (1);
        //    } else {
        autosomalGenes[108] = (1);
        autosomalGenes[109] = (1);
        //    }

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

        //ear size 4 and whitener [reducer, nuetral, adds size, adds white, adds white+ and size]
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

        return new Genes(sexlinkedGenes, autosomalGenes);

    }
}
