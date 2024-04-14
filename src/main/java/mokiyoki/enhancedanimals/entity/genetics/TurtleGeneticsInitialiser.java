package mokiyoki.enhancedanimals.entity.genetics;

import mokiyoki.enhancedanimals.init.breeds.TurtleBreeds;
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

public class TurtleGeneticsInitialiser extends AbstractGeneticsInitialiser {
    List<Breed> breeds = new ArrayList<>();

    public TurtleGeneticsInitialiser() {
        this.breeds.add(TurtleBreeds.ALBINO);
        this.breeds.add(TurtleBreeds.AXANTHIC);
        this.breeds.add(TurtleBreeds.MELANIZED);
        this.breeds.add(TurtleBreeds.PIBALD);
        this.breeds.add(TurtleBreeds.SNOW);
    }

    public Genes generateNewGenetics(LevelAccessor world, BlockPos pos, boolean generateBreed) {
        return super.generateNewGenetics(world, pos, generateBreed, this.breeds);
    }

    public Genes generateWithBreed(LevelAccessor world, BlockPos pos, String breed) {
        return super.generateWithBreed(world, pos, this.breeds, breed);
    }

    @Override
    public Genes generateLocalWildGenetics(Holder<Biome> biomeHolder, boolean isFlat) {
        int[] autosomalGenes = new int[Reference.TURTLE_AUTOSOMAL_GENES_LENGTH];
        Biome biome = biomeHolder.value();

        /**
         *      [0,1]   - Albino
         *      [2,3]   - Axanthic
         *      [4,5]   - Melanized
         *      [6,7]   - Piebald
         *      [8,9]   - speckle to spot piebald modifier
         *      [10,11] - pattern
         */

        for (int i = 0; i < 12; i++) {
            autosomalGenes[i] = ThreadLocalRandom.current().nextInt(100) > WTC ? 2 : 1;
        }

        /**
         *      [12,13] - major miniature
         *      [14,15] - miniature
         *      [16,17] - miniature
         *      [18,19] - miniature
         */

        for (int i = 12; i < 20; i++) {
            autosomalGenes[i] = ThreadLocalRandom.current().nextInt(100) > WTC ? 2 : 1;
        }

        /**
         *      [20,21] - larger
         *      [22,23] - larger
         *      [24,25] - larger
         *      [26,27] - larger
         *      [28,29] - larger
         */

        for (int i = 20; i < 30; i++) {
            autosomalGenes[i] = ThreadLocalRandom.current().nextInt(100) > WTC ? 2 : 1;
        }

        return new Genes(autosomalGenes);
    }
}
