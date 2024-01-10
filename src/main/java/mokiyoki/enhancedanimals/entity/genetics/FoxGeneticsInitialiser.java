package mokiyoki.enhancedanimals.entity.genetics;

import mokiyoki.enhancedanimals.init.breeds.FoxBreeds;
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

public class FoxGeneticsInitialiser extends AbstractGeneticsInitialiser {
    List<Breed> breeds = new ArrayList<>();

    public FoxGeneticsInitialiser() {
        this.breeds.add(FoxBreeds.RED);
        this.breeds.add(FoxBreeds.BLACK);
        this.breeds.add(FoxBreeds.CROSS1);
    }


    public Genes generateNewGenetics(LevelAccessor world, BlockPos pos, boolean generateBreed) {
        return super.generateNewGenetics(world, pos, generateBreed, this.breeds);
    }

    public Genes generateWithBreed(LevelAccessor world, BlockPos pos, String breed) {
        return super.generateWithBreed(world, pos, this.breeds, breed);
    }

    @Override
    public Genes generateLocalWildGenetics(Holder<Biome> biomeHolder, boolean isFlat) {
        int[] autosomalGenes = new int[Reference.FOX_AUTOSOMAL_GENES_LENGTH];  // value is 4 rn
        Biome biome = biomeHolder.value();

        // note here with values

        /**
         * MC1R - Extension
         *  1 : E dominant wildtype
         *  2 : e recessive - more silver
         *
         *  example: a wildtype fox would have EE
         */
        // Extension test 1 - 2 mutations, value set to 1
       // autosomalGenes[0] = ThreadLocalRandom.current().nextInt(100) > WTC ? (ThreadLocalRandom.current().nextInt(2) + 1) : 1;

        //Extension test 2 [ Wildtype, silver ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[0] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            autosomalGenes[0] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            autosomalGenes[1] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            autosomalGenes[1] = (1);
        }

        /**
         * ASIP - Agouti
         *    1 : A^r dominant wildtype: red
         *    2 : a recessive - more silver
         */
        //Agouti [ a=2, A=1 ] - 2 mutations, default to 1
       // autosomalGenes[1] = ThreadLocalRandom.current().nextInt(100) > WTC ? (ThreadLocalRandom.current().nextInt(2) + 1) : 1;

        //Agouti test 2 [ Wildtype, silver ]
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




        //if (ThreadLocalRandom.current().nextInt(100) > WTC) {
        //    autosomalGenes[2] = (ThreadLocalRandom.current().nextInt(2) + 1);

        //} else {
        //    autosomalGenes[2] = (2);
        //}
        //if (ThreadLocalRandom.current().nextInt(100) > WTC) {
        //    autosomalGenes[3] = (ThreadLocalRandom.current().nextInt(2) + 1);

        //} else {
        //    autosomalGenes[3] = (2);
        //}

        // WTC = WildTypeChance
        // autosomalGenes[#] = the gene ID #; which gene it is
        // can include biome category




        return new Genes(autosomalGenes);
    }
}
