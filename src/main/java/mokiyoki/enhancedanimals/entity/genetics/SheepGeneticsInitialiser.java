package mokiyoki.enhancedanimals.entity.genetics;

import mokiyoki.enhancedanimals.init.breeds.SheepBreeds;
import mokiyoki.enhancedanimals.util.Breed;
import mokiyoki.enhancedanimals.util.Genes;
import mokiyoki.enhancedanimals.util.Reference;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraftforge.common.Tags;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class SheepGeneticsInitialiser extends AbstractGeneticsInitialiser {
    List<Breed> breeds = new ArrayList<>();

    public SheepGeneticsInitialiser() {
        this.breeds.add(SheepBreeds.DORSETHORNED);
        this.breeds.add(SheepBreeds.DORSETPOLLED);
        this.breeds.add(SheepBreeds.JACOB);
        this.breeds.add(SheepBreeds.ICELANDIC);
        this.breeds.add(SheepBreeds.BLACKBELLY);
        this.breeds.add(SheepBreeds.BABYDOLL);
        this.breeds.add(SheepBreeds.DORPER);
        this.breeds.add(SheepBreeds.TICKED_DORPER);
        this.breeds.add(SheepBreeds.ENGLISH_BLUE);
        this.breeds.add(SheepBreeds.GERMAN_BLUE);
        this.breeds.add(SheepBreeds.PADDINGTON_BLUE);
        this.breeds.add(SheepBreeds.GREY);
        this.breeds.add(SheepBreeds.RECESSIVE_BLACK);
        this.breeds.add(SheepBreeds.MOORIT);
        this.breeds.add(SheepBreeds.RED);
        this.breeds.add(SheepBreeds.RANDOMRUFOUS);
    }

    public Genes generateNewGenetics(LevelAccessor world, BlockPos pos, boolean generateBreed) {
        return super.generateNewGenetics(world, pos, generateBreed, this.breeds);
    }

    public Genes generateWithBreed(LevelAccessor world, BlockPos pos, String breed) {
        return super.generateWithBreed(world, pos, this.breeds, breed);
    }

    @Override
    public Genes generateLocalWildGenetics(Holder<Biome> biomeHolder, BlockPos blockpos, boolean isFlat) {
        int[] autosomalGenes = new int[Reference.SHEEP_AUTOSOMAL_GENES_LENGTH];

//        if (true) {
//            return new Genes(new int[]{4, 6, 1, 2, 3, 3, 2, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 1, 1, 1, 1, 1, 1, 1, 2, 2, 1, 1, 1, 1, 1, 2, 2, 2, 3, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1});
//        }

        //Agouti? [ Dom.White, Grey, Blackbelly_0, Mouflon, EnglishBlue, Rec.Black, Blackbelly_1, Blackbelly_2, Blackbelly_3, Blackbelly_4, Blackbelly_5, light_mouflon, WildMouflon, Blue_German, Light_Blue, Paddington_Blue ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC * 0.9F) {
            autosomalGenes[0] = (ThreadLocalRandom.current().nextInt(16) + 1);
        } else {
            autosomalGenes[0] = (biomeHolder.containsTag(Tags.Biomes.IS_PLAINS) ? 1 : 13);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC * 0.9F) {
            autosomalGenes[1] = (ThreadLocalRandom.current().nextInt(16) + 1);
        } else {
            autosomalGenes[1] = (biomeHolder.containsTag(Tags.Biomes.IS_PLAINS) ? 1 : 13);
        }

        //Chocolate [ Wildtype+, chocolate ]
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

        //Extension [ Dom.Black, wildtype+, Rec.Red ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[4] = (ThreadLocalRandom.current().nextInt(3) + 1);
        } else {
            autosomalGenes[4] = (2);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[5] = (ThreadLocalRandom.current().nextInt(3) + 1);
        } else {
            autosomalGenes[5] = (2);
        }

        /**
         * Horns
         */


        //Polled [ no horns, horns, males have horns]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[6] = (ThreadLocalRandom.current().nextInt(3) + 1);
        } else {
            autosomalGenes[6] = (2);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[7] = (ThreadLocalRandom.current().nextInt(3) + 1);
        } else {
            autosomalGenes[7] = (2);
        }

        /**
         * Spot Genes
         */

        //spots1 [ wildtype, spots1 ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[8] = (ThreadLocalRandom.current().nextInt(2) + 1);
        } else {
            autosomalGenes[8] = 1;
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[9] = (ThreadLocalRandom.current().nextInt(2) + 1);
        } else {
            autosomalGenes[9] = 1;
        }

        //appaloosa spots [ wildtype, appaloosa ]
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

        //irregular spots [ wildtype, irregular ]
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

        //blaze [ wildtype, blaze ]
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

        //white nose [ wildtype, whitenose ]
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

        //white spot expansion [ wildtype, white extension ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[18] = (ThreadLocalRandom.current().nextInt(8) + 1);
        } else {
            autosomalGenes[18] = 1;
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[19] = (ThreadLocalRandom.current().nextInt(8) + 1);
        } else {
            autosomalGenes[19] = 1;
        }

        Integer[] woolmod = {1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 3, 3, 3, 4, 4, 4, 4, 4, 4, 4, 5, 5, 5, 7, 7, 8, 10, 10};
        List<Integer> woolShuffle = Arrays.asList(woolmod);
        Collections.shuffle(woolShuffle);
        woolShuffle.toArray(woolmod);

        if (biomeHolder.containsTag(Tags.Biomes.IS_PLAINS) || biomeHolder.get().coldEnoughToSnow(blockpos) ) {
            for (int i = 0; i < woolmod.length; i++) {
                woolmod[i] = woolmod[i] * 2;
            }
        } else if (biomeHolder.get().getPrecipitationAt(blockpos) == Biome.Precipitation.NONE) {
            for (int i = 0; i < woolmod.length; i++) {
                woolmod[i] = woolmod[i] != 1 ? (int)(woolmod[i] * 0.5) : 1;
            }
        }

        //added wool length 1 [ wildtype, wool1 ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC / woolmod[0]) {
            autosomalGenes[20] = (ThreadLocalRandom.current().nextInt(2) + 1);
        } else {
            autosomalGenes[20] = 1;
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC / woolmod[0]) {
            autosomalGenes[21] = (ThreadLocalRandom.current().nextInt(2) + 1);
        } else {
            autosomalGenes[21] = 1;
        }

        //added wool length 2 [ wildtype, wool2 ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC / woolmod[1]) {
            autosomalGenes[22] = (ThreadLocalRandom.current().nextInt(2) + 1);
        } else {
            autosomalGenes[22] = 1;
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC / woolmod[1]) {
            autosomalGenes[23] = (ThreadLocalRandom.current().nextInt(2) + 1);
        } else {
            autosomalGenes[23] = 1;
        }

        //added wool length 3 [ wildtype, wool3 ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC / woolmod[2]) {
            autosomalGenes[24] = (ThreadLocalRandom.current().nextInt(2) + 1);
        } else {
            autosomalGenes[24] = 1;
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC / woolmod[2]) {
            autosomalGenes[25] = (ThreadLocalRandom.current().nextInt(2) + 1);
        } else {
            autosomalGenes[25] = 1;
        }

        //added wool length 4 [ wildtype, wool3 ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC / woolmod[3]) {
            autosomalGenes[26] = (ThreadLocalRandom.current().nextInt(2) + 1);
        } else {
            autosomalGenes[26] = 1;
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC / woolmod[3]) {
            autosomalGenes[27] = (ThreadLocalRandom.current().nextInt(2) + 1);
        } else {
            autosomalGenes[27] = 1;
        }

        //added wool length 5 [ wildtype, wool3 ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC / woolmod[4]) {
            autosomalGenes[28] = (ThreadLocalRandom.current().nextInt(2) + 1);
        } else {
            autosomalGenes[28] = 1;
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC / woolmod[4]) {
            autosomalGenes[29] = (ThreadLocalRandom.current().nextInt(2) + 1);
        } else {
            autosomalGenes[29] = 1;
        }

        //added wool length 6 [ wildtype, wool3 ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC / woolmod[5]) {
            autosomalGenes[30] = (ThreadLocalRandom.current().nextInt(2) + 1);
        } else {
            autosomalGenes[30] = 1;
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC / woolmod[5]) {
            autosomalGenes[31] = (ThreadLocalRandom.current().nextInt(2) + 1);
        } else {
            autosomalGenes[31] = 1;
        }

        //added wool length 7 [ wildtype, wool3 ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC / woolmod[6]) {
            autosomalGenes[32] = (ThreadLocalRandom.current().nextInt(2) + 1);
        } else {
            autosomalGenes[32] = 1;
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC / woolmod[6]) {
            autosomalGenes[33] = (ThreadLocalRandom.current().nextInt(2) + 1);
        } else {
            autosomalGenes[33] = 1;
        }

        //added wool length 8 [ wildtype, wool3 ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC / woolmod[7]) {
            autosomalGenes[34] = (ThreadLocalRandom.current().nextInt(2) + 1);
        } else {
            autosomalGenes[34] = 1;
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC / woolmod[7]) {
            autosomalGenes[35] = (ThreadLocalRandom.current().nextInt(2) + 1);
        } else {
            autosomalGenes[35] = 1;
        }

        //multi-horned genes [multi-horn, wildtype+]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[36] = (ThreadLocalRandom.current().nextInt(2) + 1);
        } else {
            autosomalGenes[36] = (2);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[37] = (ThreadLocalRandom.current().nextInt(2) + 1);
        } else {
            autosomalGenes[37] = (2);
        }

        //wool growth area extension [extended, wildtype+, limiter]
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

        //wool growth area extension [extended, wildtype+]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[40] = (ThreadLocalRandom.current().nextInt(3) + 1);
        } else {
            autosomalGenes[40] = (2);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[41] = (ThreadLocalRandom.current().nextInt(3) + 1);
        } else {
            autosomalGenes[41] = (2);
        }

        //allows wool surrounding face [face wool, wildtype+]
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

        //fertility modifier [ -1, 0, +1]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[44] = (ThreadLocalRandom.current().nextInt(3) + 1);
        } else {
            autosomalGenes[44] = (2);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[45] = (ThreadLocalRandom.current().nextInt(3) + 1);
        } else {
            autosomalGenes[45] = (2);
        }

        //Shedding/Rooing Sheep[Shedding, non shedding]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[46] = (ThreadLocalRandom.current().nextInt(2) + 1);
        } else {
            if (biomeHolder.get().getPrecipitationAt(blockpos) == Biome.Precipitation.NONE) {
                autosomalGenes[46] = 1;
            } else {
                autosomalGenes[46] = 2;
            }
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[47] = (ThreadLocalRandom.current().nextInt(2) + 1);
        } else {
            if (biomeHolder.get().getPrecipitationAt(blockpos) == Biome.Precipitation.NONE) {
                autosomalGenes[47] = 1;
            } else {
                autosomalGenes[47] = 2;
            }
        }

        //White Shading 1 [ Shaded+, non shaded]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[48] = (ThreadLocalRandom.current().nextInt(2) + 1);
        } else {
            autosomalGenes[48] = (2);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[49] = (ThreadLocalRandom.current().nextInt(2) + 1);
        } else {
            autosomalGenes[49] = (2);
        }

        //White Shading 2 [ Shaded+, non shaded]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[50] = (ThreadLocalRandom.current().nextInt(2) + 1);
        } else {
            autosomalGenes[50] = (2);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[51] = (ThreadLocalRandom.current().nextInt(2) + 1);
        } else {
            autosomalGenes[51] = (2);
        }

        //White Shading Enhancer [ Shaded+, non shaded]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[52] = (ThreadLocalRandom.current().nextInt(2) + 1);
        } else {
            autosomalGenes[52] = (2);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[53] = (ThreadLocalRandom.current().nextInt(2) + 1);
        } else {
            autosomalGenes[53] = (2);
        }

        //shortleg dwarfism [wildtype, dwarfStrong, dwarfWeak]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[54] = (ThreadLocalRandom.current().nextInt(3) + 1);
            autosomalGenes[55] = 1;
        } else {
            autosomalGenes[54] = 1;
            autosomalGenes[55] = 1;
        }

        //minature [wildtype, minature]
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

        //size genes reducer [wildtype, smaller smaller smallest...] adds milk fat [none to most]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[58] = (ThreadLocalRandom.current().nextInt(16) + 1);
        } else {
            autosomalGenes[58] = 1;
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[59] = (ThreadLocalRandom.current().nextInt(16) + 1);
        } else {
            autosomalGenes[59] = 1;
        }

        //size genes adder [wildtype, bigger bigger biggest...]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[60] = (ThreadLocalRandom.current().nextInt(16) + 1);
        } else {
            autosomalGenes[60] = 1;
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[61] = (ThreadLocalRandom.current().nextInt(16) + 1);
        } else {
            autosomalGenes[61] = 1;
        }

        //size genes varient1 [wildtype, smaller, smallest]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[62] = (ThreadLocalRandom.current().nextInt(3) + 1);
        } else {
            autosomalGenes[62] = 1;
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[63] = (ThreadLocalRandom.current().nextInt(3) + 1);
        } else {
            autosomalGenes[63] = 1;
        }

        //size genes varient2 [wildtype, smaller, smallest]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[64] = (ThreadLocalRandom.current().nextInt(3) + 1);
        } else {
            autosomalGenes[64] = 1;
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[65] = (ThreadLocalRandom.current().nextInt(3) + 1);
        } else {
            autosomalGenes[65] = 1;
        }

        //body type [wildtype, smallest to largest] if mod with lard/fat smallest size has the least fat, largest has most fat
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[66] = (ThreadLocalRandom.current().nextInt(6) + 1);
        } else {
            autosomalGenes[66] = 1;
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[67] = (ThreadLocalRandom.current().nextInt(6) + 1);
        } else {
            autosomalGenes[67] = 1;
        }

        /**
         *  Afghan lethal - seems similar to lethal white in horses. het have pigment on parts of head and legs
         *  Turkish       - homozygotes have pigment on nose, eyes, ears, and lower legs. het are speckled
         *  Persian       - homozygotes have a white body and pigmented head
         *  wildtype      - no white spots
         */
        //Pigmented Head [wildtype, afghan lethal, turkish, persian]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[68] = (ThreadLocalRandom.current().nextInt(4) + 1);
        } else {
            autosomalGenes[68] = 1;
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[69] = (ThreadLocalRandom.current().nextInt(4) + 1);
        } else {
            autosomalGenes[69] = 1;
        }

        //ticking
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[70] = (ThreadLocalRandom.current().nextInt(2) + 1);
        } else {
            autosomalGenes[70] = 1;
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[71] = (ThreadLocalRandom.current().nextInt(2) + 1);
        } else {
            autosomalGenes[71] = 1;
        }

        // [1:wildtype, 2:darker(dominant), 3:tan, 4:cream, 5:offwhite, 6:white
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[72] = (ThreadLocalRandom.current().nextInt(5) + 1);
        } else {
            autosomalGenes[72] = 1;
            if (biomeHolder.containsTag(Tags.Biomes.IS_PLAINS) ||
                    biomeHolder.containsTag(Tags.Biomes.IS_SNOWY)) {
                autosomalGenes[72] = 6;
            }

            if (biomeHolder.containsTag(Tags.Biomes.IS_DESERT)) {
                autosomalGenes[72] = 3;
            }

            if (biomeHolder.containsTag(BiomeTags.IS_SAVANNA)) {
                autosomalGenes[72] = 2;
            }

            if (biomeHolder.containsTag(Tags.Biomes.IS_MOUNTAIN)) {
                autosomalGenes[72] = 5;
            }
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[73] = (ThreadLocalRandom.current().nextInt(5) + 1);
        } else {
            autosomalGenes[73] = 1;

            if (biomeHolder.containsTag(Tags.Biomes.IS_PLAINS) ||
                    biomeHolder.containsTag(Tags.Biomes.IS_SNOWY)) {
                autosomalGenes[73] = 5;
            }

            if (biomeHolder.containsTag(Tags.Biomes.IS_DESERT)) {
                autosomalGenes[73] = 3;
            }

            if (biomeHolder.containsTag(BiomeTags.IS_SAVANNA)) {
                autosomalGenes[73] = 2;
            }

            if (biomeHolder.containsTag(Tags.Biomes.IS_MOUNTAIN)) {
                autosomalGenes[73] = 4;
            }
        }

        // fine detail rufous genes
        for (int i = 74; i < 90; i++) {
            if (ThreadLocalRandom.current().nextInt(100) > WTC*0.6F) {
                autosomalGenes[i] = ThreadLocalRandom.current().nextInt(2)+1;
            } else {
                autosomalGenes[i] = 1;
            }
        }

        // [1 wild mealy, no mealy]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[90] = (ThreadLocalRandom.current().nextInt(2) + 1);
        } else {
            autosomalGenes[90] = 1;
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[91] = (ThreadLocalRandom.current().nextInt(2) + 1);
        } else {
            autosomalGenes[91] = 1;
        }

        //HOXB13
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[92] = (ThreadLocalRandom.current().nextInt(2) + 1);
        } else {
            autosomalGenes[92] = 1;
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[93] = (ThreadLocalRandom.current().nextInt(2) + 1);
        } else {
            autosomalGenes[93] = 1;
        }

        //TBXT
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[94] = (ThreadLocalRandom.current().nextInt(2) + 1);
        } else {
            autosomalGenes[94] = 1;
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[95] = (ThreadLocalRandom.current().nextInt(2) + 1);
        } else {
            autosomalGenes[95] = 1;
        }

        //PDGFD
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[96] = (ThreadLocalRandom.current().nextInt(2) + 1);
        } else {
            autosomalGenes[96] = 1;
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[97] = (ThreadLocalRandom.current().nextInt(2) + 1);
        } else {
            autosomalGenes[97] = 1;
        }

        //IBH
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[98] = (ThreadLocalRandom.current().nextInt(2) + 1);
        } else {
            autosomalGenes[98] = 1;
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[99] = (ThreadLocalRandom.current().nextInt(2) + 1);
        } else {
            autosomalGenes[99] = 1;
        }

        //Roan
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[100] = (ThreadLocalRandom.current().nextInt(2) + 1);
        } else {
            autosomalGenes[100] = 1;
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[101] = (ThreadLocalRandom.current().nextInt(2) + 1);
        } else {
            autosomalGenes[101] = 1;
        }

        //Blaze - nadji, white extremities, blaze
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[102] = (ThreadLocalRandom.current().nextInt(4) + 1);
        } else {
            autosomalGenes[102] = 1;
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[103] = (ThreadLocalRandom.current().nextInt(2) + 1);
        } else {
            autosomalGenes[103] = 1;
        }

        return new Genes(autosomalGenes);
    }
}
