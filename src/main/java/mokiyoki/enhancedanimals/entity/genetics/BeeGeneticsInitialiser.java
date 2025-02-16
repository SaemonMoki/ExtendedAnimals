package mokiyoki.enhancedanimals.entity.genetics;

import mokiyoki.enhancedanimals.util.Genes;
import mokiyoki.enhancedanimals.util.Reference;
import net.minecraft.core.Holder;
import net.minecraft.world.level.biome.Biome;

import java.util.concurrent.ThreadLocalRandom;

public class BeeGeneticsInitialiser extends AbstractGeneticsInitialiser {

    @Override
    public Genes generateLocalWildGenetics(Holder<Biome> biome, boolean isFlat) {
        int[] sexlinkedGenes = new int[2];

        /**
         *      brown
         */
        sexlinkedGenes[0] = getChance() ? ThreadLocalRandom.current().nextInt(2) + 1 : 1;
        sexlinkedGenes[1] = getChance() ? ThreadLocalRandom.current().nextInt(2) + 1 : 1;
//
//        /**
//         *      base colour [mostly 1 = yellow, half-half = white, mostly 2 = blue]
//         */
//        for (int i = 0; i < 16; i++) {
//            sexlinkedGenes[i] = getChance() ? 2 : 1;
//        }
//
//        /**
//         *      yellow enhancer
//         */
//        for (int i = 16; i < 24; i++) {
//            sexlinkedGenes[i] = getChance() ? 2 : 1;
//        }
//
//        /**
//         *      blue enhancer
//         */
//        for (int i = 24; i < 32; i++) {
//            sexlinkedGenes[i] = getChance() ? 2 : 1;
//        }
//
//        /**
//         *      mild non pattern dilution
//         */
//        for (int i = 32; i < 40; i++) {
//            sexlinkedGenes[i] = getChance() ? 2 : 1;
//        }
//
//        sexlinkedGenes[40] = getChance() ? ThreadLocalRandom.current().nextInt(2) + 1 : 1;
//        sexlinkedGenes[41] = getChance() ? ThreadLocalRandom.current().nextInt(2) + 1 : 1;

        return new Genes(sexlinkedGenes, new int[]{1,1});
    }
}
