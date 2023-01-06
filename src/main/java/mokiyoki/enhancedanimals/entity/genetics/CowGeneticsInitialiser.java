package mokiyoki.enhancedanimals.entity.genetics;

import mokiyoki.enhancedanimals.init.breeds.CowBreeds;
import mokiyoki.enhancedanimals.util.Breed;
import mokiyoki.enhancedanimals.util.Genes;
import mokiyoki.enhancedanimals.util.Reference;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.biome.Biome;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class CowGeneticsInitialiser extends AbstractGeneticsInitialiser {
    List<Breed> breeds = new ArrayList<>();

    public CowGeneticsInitialiser() {
        this.breeds.add(CowBreeds.HEREFORD);
        this.breeds.add(CowBreeds.BLACK_ANGUS);
        this.breeds.add(CowBreeds.RED_ANGUS);
        this.breeds.add(CowBreeds.HOLSTEIN);
        this.breeds.add(CowBreeds.JERSEY);
        this.breeds.add(CowBreeds.TEXAS_LONGHORN);
        this.breeds.add(CowBreeds.WILD_HORNS);
        this.breeds.add(CowBreeds.DEXTER);
        this.breeds.add(CowBreeds.SPANISH);
        this.breeds.add(CowBreeds.MURREY_GREY);
        this.breeds.add(CowBreeds.GLOUCESTER);
        this.breeds.add(CowBreeds.HUNGARIAN_GREY);
        this.breeds.add(CowBreeds.BROWN_SWISS);
        this.breeds.add(CowBreeds.LIGHT_JERSEY);
        this.breeds.add(CowBreeds.DARK_JERSEY);
        this.breeds.add(CowBreeds.HIGHLAND);
        this.breeds.add(CowBreeds.ANKOL);
        this.breeds.add(CowBreeds.TEST);
    }

    public Genes generateNewGenetics(LevelAccessor world, BlockPos pos, boolean generateBreed) {
        return super.generateNewGenetics(world, pos, generateBreed, this.breeds);
    }

    public Genes generateWithBreed(LevelAccessor world, BlockPos pos, String breed) {
        return super.generateWithBreed(world, pos, this.breeds, breed);
    }

    @Override
    public Genes generateLocalWildGenetics(Biome biomeHolder, boolean isFlat) {
        int[] autosomalGenes = new int[Reference.COW_AUTOSOMAL_GENES_LENGTH];
        Biome biome = biomeHolder;

        int wildType = 2;
        if (biome.getBiomeCategory().equals(Biome.BiomeCategory.PLAINS)) {
            wildType = 3;
        } else if (biome.getBaseTemperature() >= 0.9F){
            wildType = 1;
        }

        if (isFlat) {
            int randomizeWT = ThreadLocalRandom.current().nextInt(9);
            if (randomizeWT <= 3) {
                wildType = randomizeWT;
            }
        }

        /**
         *  E^D     Dominant Black
         *  E^BR    Black-Red
         *  E^m     Masked
         *  E+      Wildtype
         *  e       Red
         *
         *      E^D > E^BR > E^m > E+ > e
         */
        //Extension [ Dom.Black, Wildtype+, red, black-red, masked]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[0] = (ThreadLocalRandom.current().nextInt(5) + 1);

        } else {
            autosomalGenes[0] = (2);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[1] = (ThreadLocalRandom.current().nextInt(5) + 1);

        } else {
            autosomalGenes[1] = (2);
        }

        //Dilute [ wildtype, simmental dilute, charolais dilute]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[2] = (ThreadLocalRandom.current().nextInt(2) + 1);
            autosomalGenes[3] = (1);

        } else {
            autosomalGenes[2] = (1);
            autosomalGenes[3] = (1);
        }

        /**
         *  A^BR    Brindle
         *  a^b     Dark Agouti
         *  A+      Agouti
         *  A^W     White-Belly Agouti (Reduced black patterning black is spread thinly, reduced red, enhanced white belly)
         *  A^F     Fawn (Reduced black patterning, removal of red from belly)
         *  a       Recessive Black
         */
        //Agouti [ Dark Agouti, Wildtype+, white-bellied agouti, brindle, fawn, recessive black ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[4] = (ThreadLocalRandom.current().nextInt(4) + 1);

        } else {
            if (wildType == 1){
                autosomalGenes[4] = (2);
            }else {
                autosomalGenes[4] = (2);
            }
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[5] = (ThreadLocalRandom.current().nextInt(4) + 1);

        } else {
            if (wildType == 1){
                autosomalGenes[5] = (3);
            }else {
                autosomalGenes[5] = (2);
            }
        }

        //Dominant Red *rare genes [ Dom.red, wildtype ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[6] = (ThreadLocalRandom.current().nextInt(2) + 1);
            autosomalGenes[7] = (2);

        } else {
            autosomalGenes[6] = (2);
            autosomalGenes[7] = (2);
        }

        //Roan [wildtype, white roan] random sterility in 'females' with 2 mutations
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

        //Chocolate [wildtype, chocolate] as in dexter cattle
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

        //Horns [polled, horned]
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


        //Speckled Spots [speckled, wildtype+]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[14] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            autosomalGenes[14] = (2);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[15] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            autosomalGenes[15] = (2);
        }

        //White face and other spots [hereford, pinzgauer, wildtype+, piebald]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[16] = (ThreadLocalRandom.current().nextInt(4) + 1);

        } else {
            if (wildType == 3) {
                autosomalGenes[16] = (4);
            } else {
                autosomalGenes[16] = (3);
            }
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[17] = (ThreadLocalRandom.current().nextInt(4) + 1);

        } else {
            if (wildType == 3) {
                autosomalGenes[17] = (4);
            } else {
                autosomalGenes[17] = (3);
            }
        }

        //Legacy belted [belted, blaze, brockling, wildtype]
        //Brockling [Brockling, wildtype]
        autosomalGenes[18] = (4);
        autosomalGenes[19] = (4);

        //colour sided [colour sided, wildtype]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[20] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            autosomalGenes[20] = (2);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[21] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            autosomalGenes[21] = (2);
        }

        //whiteface genes expression controller [+spots, normal, -spots, +backstripe]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[22] = (ThreadLocalRandom.current().nextInt(4) + 1);

        } else {
            autosomalGenes[22] = (2);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[23] = (ThreadLocalRandom.current().nextInt(4) + 1);

        } else {
            autosomalGenes[23] = (2);
        }

        //shading (white nose ring) [no nose ring, wildtype, extended mealy]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[24] = (ThreadLocalRandom.current().nextInt(3) + 1);

        } else {
            autosomalGenes[24] = (2);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[25] = (ThreadLocalRandom.current().nextInt(3) + 1);

        } else {
            autosomalGenes[25] = (2);
        }

        //bulldog dwarfism [dwarf, wildtype]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[26] = (ThreadLocalRandom.current().nextInt(2) + 1);
            autosomalGenes[27] = (2);

        } else {
            autosomalGenes[26] = (2);
            autosomalGenes[27] = (2);
        }

        //proportionate dwarfism [wildtype, dwarf]
        if (wildType == 1){
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
        }else {
            autosomalGenes[28] = (1);
            autosomalGenes[29] = (1);
        }

        //size genes reducer [wildtype, smaller smaller smallest...] adds milk fat [none to most]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[30] = (ThreadLocalRandom.current().nextInt(15) + 1);

        } else {
            autosomalGenes[30] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[31] = (ThreadLocalRandom.current().nextInt(15) + 1);

        } else {
            autosomalGenes[31] = (1);
        }

        //size genes adder [wildtype, bigger bigger biggest...]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[32] = (ThreadLocalRandom.current().nextInt(15) + 1);

        } else {
            autosomalGenes[32] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[33] = (ThreadLocalRandom.current().nextInt(15) + 1);

        } else {
            autosomalGenes[33] = (1);
        }

        //size genes varient1 [wildtype, smaller, smallest] suppresses milk fat [none to most]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[34] = (ThreadLocalRandom.current().nextInt(3) + 1);

        } else {
            autosomalGenes[34] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[35] = (ThreadLocalRandom.current().nextInt(3) + 1);

        } else {
            autosomalGenes[35] = (1);
        }

        //size genes varient2 [smallest, smaller, wildtype] suppresses milk fat [none to most]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[36] = (ThreadLocalRandom.current().nextInt(3) + 1);

        } else {
            autosomalGenes[36] = (3);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[37] = (ThreadLocalRandom.current().nextInt(3) + 1);

        } else {
            autosomalGenes[37] = (3);
        }

        //hump size [none, smallest, smaller, medium, bigger, biggest]  reduces milk production [ biggest sizes only]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            if (wildType == 1){
                autosomalGenes[38] = (ThreadLocalRandom.current().nextInt(3) + 4);
            }else{
                autosomalGenes[38] = (ThreadLocalRandom.current().nextInt(3) + 1);
            }
        } else {
            if (wildType == 1){
                autosomalGenes[38] = (4);
            }else {
                autosomalGenes[38] = (1);
            }
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            if (wildType == 1){
                autosomalGenes[39] = (ThreadLocalRandom.current().nextInt(4) + 3);
            }else{
                autosomalGenes[39] = (ThreadLocalRandom.current().nextInt(2) + 1);
            }
        } else {
            if (wildType == 1){
                autosomalGenes[39] = (5);
            }else{
                autosomalGenes[39] = (1);
            }
        }

        //hump height [tallest, tall, medium, short] reduces milk production [tall sizes only]
        if (wildType == 1) {
            if (ThreadLocalRandom.current().nextInt(100) > WTC) {
                autosomalGenes[40] = (ThreadLocalRandom.current().nextInt(4) + 1);

            } else {
                autosomalGenes[40] = (3);
            }
            if (ThreadLocalRandom.current().nextInt(100) > WTC) {
                autosomalGenes[41] = (ThreadLocalRandom.current().nextInt(4) + 1);

            } else {
                autosomalGenes[41] = (3);
            }
        }else{
            if (ThreadLocalRandom.current().nextInt(100) > WTC) {
                autosomalGenes[40] = (ThreadLocalRandom.current().nextInt(2) + 3);

            } else {
                autosomalGenes[40] = (4);
            }
            if (ThreadLocalRandom.current().nextInt(100) > WTC) {
                autosomalGenes[41] = (ThreadLocalRandom.current().nextInt(2) + 3);

            } else {
                autosomalGenes[41] = (4);
            }
        }

        //ear size [smallest, smaller, normal, long, longest]
        if (wildType == 1){
            if (ThreadLocalRandom.current().nextInt(100) > WTC) {
                autosomalGenes[42] = (ThreadLocalRandom.current().nextInt(3) + 3);

            } else {
                autosomalGenes[42] = (5);
            }
            if (ThreadLocalRandom.current().nextInt(100) > WTC) {
                autosomalGenes[43] = (ThreadLocalRandom.current().nextInt(3) + 3);

            } else {
                autosomalGenes[43] = (5);
            }
        }else{
            if (ThreadLocalRandom.current().nextInt(100) > WTC) {
                autosomalGenes[42] = (ThreadLocalRandom.current().nextInt(3) + 1);

            } else {
                autosomalGenes[42] = (2);
            }
            if (ThreadLocalRandom.current().nextInt(100) > WTC) {
                autosomalGenes[43] = (ThreadLocalRandom.current().nextInt(3) + 1);

            } else {
                autosomalGenes[43] = (2);
            }
        }

        //ear size suppressor [smaller ears, longer ears]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[44] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            if (wildType == 1){
                autosomalGenes[44] = (2);
            }else{
                autosomalGenes[44] = (1);
            }
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[45] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            if (wildType == 1){
                autosomalGenes[45] = (2);
            }else{
                autosomalGenes[45] = (1);
            }
        }

        //ear floppiness [stiffer, normal, floppier, floppiest]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[46] = (ThreadLocalRandom.current().nextInt(4) + 1);

        } else {
            if (wildType == 1){
                autosomalGenes[46] = (3);
            }else{
                autosomalGenes[46] = (2);
            }
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[47] = (ThreadLocalRandom.current().nextInt(4) + 1);

        } else {
            if (wildType == 1){
                autosomalGenes[47] = (3);
            }else{
                autosomalGenes[47] = (2);
            }
        }

        //coat smoothness [smooth, normal] (this gives a slick coat like brahma and suppresses furry coat genes)
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[48] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            if (wildType == 1){
                autosomalGenes[48] = (1);
            }else{
                autosomalGenes[48] = (2);
            }

        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[49] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            if (wildType == 1){
                autosomalGenes[49] = (1);
            }else{
                autosomalGenes[49] = (2);
            }
        }

        //Furry coat 1 [normal, furry] reduces milk production [least to most]
        if (wildType == 1){
            autosomalGenes[50] = (1);
            autosomalGenes[51] = (1);
            autosomalGenes[52] = (1);
            autosomalGenes[53] = (1);
        }else {
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

            //furry coat 2 [normal, furry] reduces milk production [least to most]
            if (ThreadLocalRandom.current().nextInt(100) > WTC) {
                autosomalGenes[52] = (ThreadLocalRandom.current().nextInt(2) + 1);

            } else {
                autosomalGenes[52] = (1);
            }
            if (ThreadLocalRandom.current().nextInt(100) > WTC) {
                autosomalGenes[53] = (ThreadLocalRandom.current().nextInt(2) + 1);

            } else {
                autosomalGenes[53] = (1);
            }
        }

        //body type [smallest to largest] reduces milk production and fat harshly [least to most] increases meat drops proportionally
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[54] = (ThreadLocalRandom.current().nextInt(3) + 1);

        } else {
            autosomalGenes[54] = (2);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[55] = (ThreadLocalRandom.current().nextInt(3) + 1);

        } else {
            autosomalGenes[55] = (2);
        }

        //A1 vs A2 milk cause why not [A1, A2]
            autosomalGenes[56] = (ThreadLocalRandom.current().nextInt(2) + 1);
            autosomalGenes[57] = (ThreadLocalRandom.current().nextInt(2) + 1);

        //Golden Milk 1[white, white, gold]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[58] = (ThreadLocalRandom.current().nextInt(3) + 1);

        } else {
            autosomalGenes[58] = (2);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[59] = (ThreadLocalRandom.current().nextInt(3) + 1);

        } else {
            autosomalGenes[59] = (2);
        }

        //Golden Milk 2[white, cream, gold]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[60] = (ThreadLocalRandom.current().nextInt(3) + 1);

        } else {
            autosomalGenes[60] = (2);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[61] = (ThreadLocalRandom.current().nextInt(3) + 1);

        } else {
            autosomalGenes[61] = (2);
        }

        //Milk Production Base 1[smallest to largest]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[62] = (ThreadLocalRandom.current().nextInt(10) + 1);

        } else {
            autosomalGenes[62] = (2);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[63] = (ThreadLocalRandom.current().nextInt(10) + 1);

        } else {
            autosomalGenes[63] = (2);
        }

        //Milk Production Base 2[smallest to largest] reduces milk fat [least to most]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[64] = (ThreadLocalRandom.current().nextInt(10) + 1);

        } else {
            autosomalGenes[64] = (2);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[65] = (ThreadLocalRandom.current().nextInt(10) + 1);

        } else {
            autosomalGenes[65] = (2);
        }

        //Milk Fat Base 1 [low fat to high fat] increases production inversely [high production to low production]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[66] = (ThreadLocalRandom.current().nextInt(5) + 1);

        } else {
            autosomalGenes[66] = (2);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[67] = (ThreadLocalRandom.current().nextInt(5) + 1);

        } else {
            autosomalGenes[67] = (2);
        }

        //Milk Fat Base 2
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[68] = (ThreadLocalRandom.current().nextInt(5) + 1);

        } else {
            autosomalGenes[68] = (2);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[69] = (ThreadLocalRandom.current().nextInt(5) + 1);

        } else {
            autosomalGenes[69] = (2);
        }

        //horn nub controller 1 [taurus, indus]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[70] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            if (wildType == 1){
                autosomalGenes[70] = (2);
            }else{
                autosomalGenes[70] = (1);
            }
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[71] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            if (wildType == 1){
                autosomalGenes[71] = (2);
            }else{
                autosomalGenes[71] = (1);
            }
        }

        //horn nub controller 2 [taurus, medium, indus]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[72] = (ThreadLocalRandom.current().nextInt(3) + 1);

        } else {
            if (wildType == 1){
                autosomalGenes[72] = (3);
            }else{
                autosomalGenes[72] = (1);
            }
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[73] = (ThreadLocalRandom.current().nextInt(3) + 1);

        } else {
            if (wildType == 1){
                autosomalGenes[73] = (3);
            }else{
                autosomalGenes[73] = (1);
            }
        }

        //horn nub controller 3 [indus, taurus]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[74] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            if (wildType == 1){
                autosomalGenes[74] = (1);
            }else{
                autosomalGenes[74] = (2);
            }
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[75] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            if (wildType == 1){
                autosomalGenes[75] = (1);
            }else{
                autosomalGenes[75] = (2);
            }
        }

        //african horn genes [african horned, wildtype]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[76] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            if (wildType == 1){
                autosomalGenes[76] = (1);
            }else {
                autosomalGenes[76] = (2);
            }
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[77] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            if (wildType == 1){
                autosomalGenes[77] = (1);
            }else {
                autosomalGenes[77] = (2);
            }
        }

        //scur genes [scurs, wildtype]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[78] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            autosomalGenes[78] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[79] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            autosomalGenes[79] = (1);
        }

        //cow horn length modifier [wildtype, longer horns 1, shorter horns 2, shorter horns 3]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[80] = (ThreadLocalRandom.current().nextInt(4) + 1);
        } else {
            autosomalGenes[80] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[81] = (ThreadLocalRandom.current().nextInt(4) + 1);

        } else {
            autosomalGenes[81] = (1);
        }

        //horn shortener [wildtype, shorter horns]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[82] = (ThreadLocalRandom.current().nextInt(2) + 1);
        } else {
            autosomalGenes[82] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[83] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            autosomalGenes[83] = (2);
        }

        //modifier [wildtype, ...]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[84] = (ThreadLocalRandom.current().nextInt(4) + 1);
        } else {
            autosomalGenes[84] = (2);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[85] = (ThreadLocalRandom.current().nextInt(4) + 1);
        } else {
            autosomalGenes[85] = (2);
        }

        //cow horn scale 1 [wildtype, 1.25]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[86] = (ThreadLocalRandom.current().nextInt(2) + 1);
        } else {
            autosomalGenes[86] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[87] = (ThreadLocalRandom.current().nextInt(2) + 1);
        } else {
            autosomalGenes[87] = (1);
        }

        //cow horn scale 2 [wildtype, 1.25]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[88] = (ThreadLocalRandom.current().nextInt(2) + 1);
        } else {
            autosomalGenes[88] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[89] = (ThreadLocalRandom.current().nextInt(2) + 1);
        } else {
            autosomalGenes[89] = (1);
        }

        // horn scale 3 [wildtype, 2.0]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[90] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            autosomalGenes[90] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[91] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            autosomalGenes[91] = (1);
        }

        //cow horn smoother
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[92] = (ThreadLocalRandom.current().nextInt(9999) + 1);

        } else {
            autosomalGenes[92] = (1988);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[93] = (ThreadLocalRandom.current().nextInt(9999) + 1);

        } else {
            autosomalGenes[93] = (1988);
        }

        //cow horn twist ... place matches following horn piece numbers { 4 5 6 7 8 9 }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[94] = (ThreadLocalRandom.current().nextInt(999999) + 1);

        } else {
            autosomalGenes[94] = (244349);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[95] = (ThreadLocalRandom.current().nextInt(999999) + 1);

        } else {
            autosomalGenes[95] = (244349);
        }

        //cow horn base twist  ... place matches following horn piece numbers { *total twist mod* 1 2 3 }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[96] = (ThreadLocalRandom.current().nextInt(9999) + 1);

        } else {
            autosomalGenes[96] = (1666);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[97] = (ThreadLocalRandom.current().nextInt(9999) + 1);

        } else {
            autosomalGenes[97] = (1666);
        }

        // cow horn root
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[98] = (ThreadLocalRandom.current().nextInt(999) + 1);

        } else {
            autosomalGenes[98] = (206);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[99] = (ThreadLocalRandom.current().nextInt(999) + 1);

        } else {
            autosomalGenes[99] = (206);
        }

        //cow horn1 X and Z
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[100] = (ThreadLocalRandom.current().nextInt(999) + 1);

        } else {
            autosomalGenes[100] = (158);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[101] = (ThreadLocalRandom.current().nextInt(999) + 1);

        } else {
            autosomalGenes[101] = (158);
        }

        //cow horn2 X and Z
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[102] = (ThreadLocalRandom.current().nextInt(999) + 1);

        } else {
            autosomalGenes[102] = (158);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[103] = (ThreadLocalRandom.current().nextInt(999) + 1);

        } else {
            autosomalGenes[103] = (158);
        }

        //cow horn3 X and Z
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[104] = (ThreadLocalRandom.current().nextInt(999) + 1);

        } else {
            autosomalGenes[104] = (165);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[105] = (ThreadLocalRandom.current().nextInt(999) + 1);

        } else {
            autosomalGenes[105] = (165);
        }

        //cow horn4 X and Z
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[106] = (ThreadLocalRandom.current().nextInt(999) + 1);

        } else {
            autosomalGenes[106] = (177);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[107] = (ThreadLocalRandom.current().nextInt(999) + 1);

        } else {
            autosomalGenes[107] = (177);
        }

        //cow horn5 X and Z
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[108] = (ThreadLocalRandom.current().nextInt(999) + 1);

        } else {
            autosomalGenes[108] = (138);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[109] = (ThreadLocalRandom.current().nextInt(999) + 1);

        } else {
            autosomalGenes[109] = (138);
        }

        //cow horn6 X and Z
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[110] = (ThreadLocalRandom.current().nextInt(999) + 1);

        } else {
            autosomalGenes[110] = (144);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[111] = (ThreadLocalRandom.current().nextInt(999) + 1);

        } else {
            autosomalGenes[111] = (144);
        }

        //cow horn7 X and Z
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[112] = (ThreadLocalRandom.current().nextInt(999) + 1);

        } else {
            autosomalGenes[112] = (195);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[113] = (ThreadLocalRandom.current().nextInt(999) + 1);

        } else {
            autosomalGenes[113] = (195);
        }

        //cow horn8 X and Z
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[114] = (ThreadLocalRandom.current().nextInt(999) + 1);

        } else {
            autosomalGenes[114] = (161);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[115] = (ThreadLocalRandom.current().nextInt(999) + 1);

        } else {
            autosomalGenes[115] = (161);
        }

        //cow horn9 X and Z
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[116] = (ThreadLocalRandom.current().nextInt(999) + 1);

        } else {
            autosomalGenes[116] = (190);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[117] = (ThreadLocalRandom.current().nextInt(999) + 1);

        } else {
            autosomalGenes[117] = (190);
        }

        //parasitic immunity gene
        autosomalGenes[118] = 1;
        autosomalGenes[119] = 1;

        // Eel Stripe
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[120] = ThreadLocalRandom.current().nextInt(2)+1;
        } else {
            autosomalGenes[120] = 1;
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[121] = ThreadLocalRandom.current().nextInt(2)+1;
        } else {
            autosomalGenes[121] = 1;
        }

        //horn modifier
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[122] = ThreadLocalRandom.current().nextInt(5)+1;
        } else {
            autosomalGenes[122] = 1;
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[123] = ThreadLocalRandom.current().nextInt(5)+1;
        } else {
            autosomalGenes[123] = 1;
        }

        // masculine coat modifier
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[124] = ThreadLocalRandom.current().nextInt(3)+1;
        } else {
            autosomalGenes[124] = 1;
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[125] = ThreadLocalRandom.current().nextInt(3)+1;
        } else {
            autosomalGenes[125] = 1;
        }

        // feminine coat modifier
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[126] = ThreadLocalRandom.current().nextInt(3)+1;
        } else {
            autosomalGenes[126] = 1;
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[127] = ThreadLocalRandom.current().nextInt(3)+1;
        } else {
            autosomalGenes[127] = 1;
        }

        // dun
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[128] = ThreadLocalRandom.current().nextInt(2)+1;
        } else {
            autosomalGenes[128] = 1;
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[129] = ThreadLocalRandom.current().nextInt(2)+1;
        } else {
            autosomalGenes[129] = 1;
        }

        // rufous genes
        for (int i = 130; i < 170; i++) {
            if (ThreadLocalRandom.current().nextInt(100) > WTC*0.6F) {
                autosomalGenes[i] = ThreadLocalRandom.current().nextInt(2)+1;
            } else {
                autosomalGenes[i] = 1;
            }
        }

        // darker/lighter genes
        for (int i = 170; i < 200; i++) {
            if (ThreadLocalRandom.current().nextInt(100) > WTC*0.6F) {
                autosomalGenes[i] = ThreadLocalRandom.current().nextInt(2)+1;
            } else {
                autosomalGenes[i] = 1;
            }
        }

        // darker/lighter pattern genes
        for (int i = 200; i < 250; i++) {
            if (ThreadLocalRandom.current().nextInt(100) > WTC) {
                autosomalGenes[i] = ThreadLocalRandom.current().nextInt(2)+1;
            } else {
                autosomalGenes[i] = 1;
            }
        }

        // Belted [wildtype, Belted]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[250] = ThreadLocalRandom.current().nextInt(2)+1;
        } else {
            autosomalGenes[250] = 1;
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[251] = ThreadLocalRandom.current().nextInt(2)+1;
        } else {
            autosomalGenes[251] = 1;
        }

        // Blaze [wildtype, Blaze]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[252] = ThreadLocalRandom.current().nextInt(2)+1;
        } else {
            autosomalGenes[252] = 1;
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[253] = ThreadLocalRandom.current().nextInt(2)+1;
        } else {
            autosomalGenes[253] = 1;
        }

        return new Genes(autosomalGenes);
    }
}
