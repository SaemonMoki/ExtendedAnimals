package mokiyoki.enhancedanimals.entity.Genetics;

import mokiyoki.enhancedanimals.init.breeds.SheepBreeds;
import mokiyoki.enhancedanimals.init.breeds.TurtleBreeds;
import mokiyoki.enhancedanimals.util.Breed;
import mokiyoki.enhancedanimals.util.Genes;
import mokiyoki.enhancedanimals.util.Reference;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;

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

    public Genes generateNewGenetics(IWorld world, BlockPos pos, boolean generateBreed) {
        return super.generateNewGenetics(world, pos, generateBreed, this.breeds);
    }

    public Genes generateWithBreed(IWorld world, BlockPos pos, String breed) {
        return super.generateWithBreed(world, pos, this.breeds, breed);
    }

    @Override
    public Genes generateLocalWildGenetics(Biome biome, boolean isFlat) {
        int[] autosomalGenes = new int[Reference.TURTLE_AUTOSOMAL_GENES_LENGTH];

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

        return new Genes(autosomalGenes);
    }
}
