package mokiyoki.enhancedanimals.entity.genetics;

import mokiyoki.enhancedanimals.init.breeds.BeeBreeds;
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

public class BeeGeneticsInitialiser extends AbstractGeneticsInitialiser {

    List<Breed> breeds = new ArrayList<>();

    public BeeGeneticsInitialiser() {
        this.breeds.add(BeeBreeds.CHOCOLATE);
        this.breeds.add(BeeBreeds.YELLOW);
        this.breeds.add(BeeBreeds.BLUE);
        this.breeds.add(BeeBreeds.LONG_GUY);
        this.breeds.add(BeeBreeds.STUBBEE);
    }

    public Genes generateNewGenetics(LevelAccessor world, BlockPos pos, boolean generateBreed) {
        return super.generateNewGenetics(world, pos, generateBreed, this.breeds);
    }

    public Genes generateWithBreed(LevelAccessor world, BlockPos pos, String breed) {
        return super.generateWithBreed(world, pos, this.breeds, breed);
    }

    @Override
    public Genes generateLocalWildGenetics(Holder<Biome> biome, boolean isFlat) {
        int[] sexlinkedGenes = new int[Reference.BEE_SEXLINKED_GENES_LENGTH];

        /**
         *      SDL
         */
        sexlinkedGenes[0] = ThreadLocalRandom.current().nextInt(90) + 1;
        sexlinkedGenes[1] = ThreadLocalRandom.current().nextInt(90) + 1;

        /**
         *      thorax reducer [wildtype, Smaller, Smaller+abdomenReduction]
         */
        sexlinkedGenes[2] = getChance() ? ThreadLocalRandom.current().nextInt(3) + 1 : 1;
        sexlinkedGenes[3] = getChance() ? ThreadLocalRandom.current().nextInt(3) + 1 : 1;

        /**
         *      abdomen increase
         *      1 -> +0
         *      2 -> +1
         *      3 -> +2
         *      4 -> +3
         */
        sexlinkedGenes[4] = getChance() ? ThreadLocalRandom.current().nextInt(4) + 1 : 1;
        sexlinkedGenes[5] = getChance() ? ThreadLocalRandom.current().nextInt(4) + 1 : 1;

        /**
         *      abdomen decrease 1
         *      1 -> -0
         *      2 -> -1
         */
        sexlinkedGenes[6] = getChance() ? ThreadLocalRandom.current().nextInt(2) + 1 : 1;
        sexlinkedGenes[7] = getChance() ? ThreadLocalRandom.current().nextInt(2) + 1 : 1;

        /**
         *      abdomen decrease 2
         *      1 -> -0
         *      2 -> -1
         */
        sexlinkedGenes[8] = getChance() ? ThreadLocalRandom.current().nextInt(2) + 1 : 1;
        sexlinkedGenes[9] = getChance() ? ThreadLocalRandom.current().nextInt(2) + 1 : 1;

        /**
         *      abdomen decrease 3
         *      1 -> -0
         *      2 -> -1
         */
        sexlinkedGenes[10] = getChance() ? ThreadLocalRandom.current().nextInt(2) + 1 : 1;
        sexlinkedGenes[11] = getChance() ? ThreadLocalRandom.current().nextInt(2) + 1 : 1;

        /**
         *      blue eyes [blue, nonblue]
         */
        sexlinkedGenes[12] = getChance() ? ThreadLocalRandom.current().nextInt(2) + 1 : 1;
        sexlinkedGenes[13] = getChance() ? ThreadLocalRandom.current().nextInt(2) + 1 : 1;

        /**
         *      yellow eyes [nonyellow, yellow]
         */
        sexlinkedGenes[14] = getChance() ? ThreadLocalRandom.current().nextInt(2) + 1 : 1;
        sexlinkedGenes[15] = getChance() ? ThreadLocalRandom.current().nextInt(2) + 1 : 1;


        /**
         *      darken eyes [darken, nondark]
         */
        sexlinkedGenes[16] = getChance() ? ThreadLocalRandom.current().nextInt(2) + 1 : 1;
        sexlinkedGenes[17] = getChance() ? ThreadLocalRandom.current().nextInt(2) + 1 : 1;

        /**
         *      lighten eyes [nonlight, lighten]
         */
        sexlinkedGenes[18] = getChance() ? ThreadLocalRandom.current().nextInt(2) + 1 : 1;
        sexlinkedGenes[19] = getChance() ? ThreadLocalRandom.current().nextInt(2) + 1 : 1;

        /**
         *      long legs [medium, long]
         */
        sexlinkedGenes[20] = getChance() ? ThreadLocalRandom.current().nextInt(2) + 1 : 1;
        sexlinkedGenes[21] = getChance() ? ThreadLocalRandom.current().nextInt(2) + 1 : 1;

        /**
         *      short legs [medium, short]
         */
        sexlinkedGenes[22] = getChance() ? ThreadLocalRandom.current().nextInt(2) + 1 : 1;
        sexlinkedGenes[23] = getChance() ? ThreadLocalRandom.current().nextInt(2) + 1 : 1;

        /**
         *      chocolate [chocolate, non chocolate]
         */
        sexlinkedGenes[24] = getChance() ? ThreadLocalRandom.current().nextInt(2) + 1 : 1;
        sexlinkedGenes[25] = getChance() ? ThreadLocalRandom.current().nextInt(2) + 1 : 1;

        /**
         *      thorax size [1 = 0, 2 = 3, 3 = 5, 4 = 7]
         */
        sexlinkedGenes[26] = getChance() ? ThreadLocalRandom.current().nextInt(4) + 1 : 1;
        sexlinkedGenes[27] = getChance() ? ThreadLocalRandom.current().nextInt(4) + 1 : 1;


        return new Genes(sexlinkedGenes, new int[]{1,1});
    }
}
