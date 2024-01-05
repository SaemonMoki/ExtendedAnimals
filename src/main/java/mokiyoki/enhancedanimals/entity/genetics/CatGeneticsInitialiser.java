package mokiyoki.enhancedanimals.entity.genetics;

//import mokiyoki.enhancedanimals.init.breeds.CatBreeds;
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

public class CatGeneticsInitialiser extends AbstractGeneticsInitialiser{
    List<Breed> breeds = new ArrayList<>();

    public CatGeneticsInitialiser() {
        //this.breeds.add(CatBreeds.Black);
        //this.breeds.add(CatBreeds.BritishShorthair);
        //this.breeds.add(CatBreeds.Calico);
        //this.breeds.add(CatBreeds.Jellie);
        //this.breeds.add(CatBreeds.Persian);
        //this.breeds.add(CatBreeds.Ragdoll);
        //this.breeds.add(CatBreeds.Red);
        //this.breeds.add(CatBreeds.Siamese);
        //this.breeds.add(CatBreeds.BrownTabby);
        //this.breeds.add(CatBreeds.Tuxedo);
        //this.breeds.add(CatBreeds.White);
    }

    public Genes generateNewGenetics(LevelAccessor world, BlockPos pos, boolean generateBreed) {
        return super.generateNewGenetics(world, pos, generateBreed, this.breeds);
    }

    public Genes generateWithBreed(LevelAccessor world, BlockPos pos, String breed) {
        return super.generateWithBreed(world, pos, this.breeds, breed);
    }

    @Override
    public Genes generateLocalWildGenetics(Holder<Biome> biomeHolder, boolean isFlat) {
        int[] sexlinkedGenes = new int[Reference.CAT_SEXLINKED_GENES_LENGTH];
        int[] autosomalGenes = new int[Reference.CAT_AUTOSOMAL_GENES_LENGTH];
        Biome biome = biomeHolder.value();

        /**
         *  SEX LINKED GENES
         */

        // sex linked red [wildtype, red]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            sexlinkedGenes[0] = (ThreadLocalRandom.current().nextInt(2) + 1);
        } else {
            sexlinkedGenes[0] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            sexlinkedGenes[1] = (ThreadLocalRandom.current().nextInt(2) + 1);
        } else {
            sexlinkedGenes[1] = (1);
        }

        /**
         *  AUTOSOMAL GENES
         */

        // Agouti [Tabby+, nonagouti, ALC/charcoal]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[0] = (ThreadLocalRandom.current().nextInt(3) + 1);
        } else {
            autosomalGenes[0] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[1] = (ThreadLocalRandom.current().nextInt(3) + 1);
        } else {
            autosomalGenes[1] = (1);
        }

        // Tabby [Mackerel, classic]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[2] = (ThreadLocalRandom.current().nextInt(2) + 1);
        } else {
            autosomalGenes[2] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[3] = (ThreadLocalRandom.current().nextInt(2) + 1);
        } else {
            autosomalGenes[3] = (1);
        }

        // Eumelanin [Wildtype+, chocolate, cinnamon]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[4] = (ThreadLocalRandom.current().nextInt(3) + 1);
        } else {
            autosomalGenes[4] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[5] = (ThreadLocalRandom.current().nextInt(3) + 1);
        } else {
            autosomalGenes[5] = (1);
        }

        // Extension [Wildtype+, amber, carnelian, russet]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[6] = (ThreadLocalRandom.current().nextInt(4) + 1);
        } else {
            autosomalGenes[6] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[7] = (ThreadLocalRandom.current().nextInt(4) + 1);
        } else {
            autosomalGenes[7] = (1);
        }

        // Ticked [Wildtype+, Ticked]
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

        // Spotted [Wildtype+, Spotted]
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

        // leaving a gap here for more basic stuff.
        // because i like the basic color genes being towards the beginning

        // M1-5 are recessive and are all indistinguishable phenotypically
        // Long Hair [Shorthair+, M1, M2, M3, M4, M5]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[32] = (ThreadLocalRandom.current().nextInt(5) + 1);
        } else {
            autosomalGenes[32] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[33] = (ThreadLocalRandom.current().nextInt(5) + 1);
        } else {
            autosomalGenes[33] = (1);
        }
        // Fur Length [1-10] (34-41)
        for (int i = 34; i < 42; i++) {
            if (ThreadLocalRandom.current().nextInt(100) > WTC) {
                autosomalGenes[i] = (ThreadLocalRandom.current().nextInt(10) + 1);
            } else {
                autosomalGenes[i] = (1);
            }
        }

        // Fur Density [1-10]
        // Fur Fur-nishings [1-2]



        return new Genes(sexlinkedGenes, autosomalGenes);
    }

}
