package mokiyoki.enhancedanimals.entity.genetics;

import mokiyoki.enhancedanimals.init.breeds.LlamaBreeds;
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

public class LlamaGeneticsInitialiser extends AbstractGeneticsInitialiser{
    List<Breed> breeds = new ArrayList<>();

    public LlamaGeneticsInitialiser() {
        this.breeds.add(LlamaBreeds.SURI);
        this.breeds.add(LlamaBreeds.BLUEEYEWHITE);
        this.breeds.add(LlamaBreeds.WHITE);
        this.breeds.add(LlamaBreeds.GREY);
        this.breeds.add(LlamaBreeds.BROWN);
        this.breeds.add(LlamaBreeds.SANDY);
        this.breeds.add(LlamaBreeds.TUXEDO);
        this.breeds.add(LlamaBreeds.WOOLLY);
    }

    public Genes generateNewGenetics(LevelAccessor world, BlockPos pos, boolean generateBreed) {
        return super.generateNewGenetics(world, pos, generateBreed, this.breeds);
    }

    public Genes generateWithBreed(LevelAccessor world, BlockPos pos, String breed) {
        return super.generateWithBreed(world, pos, this.breeds, breed);
    }

    @Override
    public Genes generateLocalWildGenetics(Holder<Biome> biomeHolder, boolean isFlat) {
        int[] autosomalGenes = new int[Reference.LLAMA_AUTOSOMAL_GENES_LENGTH];
        Biome biome = biomeHolder.value();
        //TODO create biome WTC variable [hot and dry biomes, cold biomes ] WTC is neutral biomes "all others"

        //Endurance genes [ wildtype, stronger1, stronger2]
        if(ThreadLocalRandom.current().nextInt(100)>WTC){
            autosomalGenes[0] = (ThreadLocalRandom.current().nextInt(3)+1);

        } else {
            autosomalGenes[0] = (1);
        }
        if(ThreadLocalRandom.current().nextInt(100)>WTC){
            autosomalGenes[1] = (ThreadLocalRandom.current().nextInt(3)+1);

        } else {
            autosomalGenes[1] = (1);
        }

        //Strength genes [ wildtype, stronger1, stronger2]
        if(ThreadLocalRandom.current().nextInt(100)>WTC){
            autosomalGenes[2] = (ThreadLocalRandom.current().nextInt(3)+1);

        } else {
            autosomalGenes[2] = (1);
        }
        if(ThreadLocalRandom.current().nextInt(100)>WTC){
            autosomalGenes[3] = (ThreadLocalRandom.current().nextInt(3)+1);

        } else {
            autosomalGenes[3] = (1);
        }

        //Attack genes [ wildtype, stronger1, stronger2]
        if(ThreadLocalRandom.current().nextInt(100)>WTC){
            autosomalGenes[4] = (ThreadLocalRandom.current().nextInt(3)+1);

        } else {
            autosomalGenes[4] = (1);
        }
        if(ThreadLocalRandom.current().nextInt(100)>WTC){
            autosomalGenes[5] = (ThreadLocalRandom.current().nextInt(3)+1);

        } else {
            autosomalGenes[5] = (1);
        }

        /**
         * Colour Genes
         */

        //Dominant White [ dominant white, wildtype ]
        if(ThreadLocalRandom.current().nextInt(100)>WTC){
            autosomalGenes[6] = (ThreadLocalRandom.current().nextInt(2)+1);

        } else {
            autosomalGenes[6] = (2);
        }
        if(ThreadLocalRandom.current().nextInt(100)>WTC){
            autosomalGenes[7] = (ThreadLocalRandom.current().nextInt(2)+1);

        } else {
            autosomalGenes[7] = (2);
        }

        //Roan [ roan, wildtype ]
        if(ThreadLocalRandom.current().nextInt(100)>WTC){
            autosomalGenes[8] = (ThreadLocalRandom.current().nextInt(2)+1);

        } else {
            autosomalGenes[8] = (2);
        }
        if(ThreadLocalRandom.current().nextInt(100)>WTC){
            autosomalGenes[9] = (ThreadLocalRandom.current().nextInt(2)+1);

        } else {
            autosomalGenes[9] = (2);
        }

        //Piebald [ wildtype, piebald ]
        if(ThreadLocalRandom.current().nextInt(100)>WTC){
            autosomalGenes[10] = (ThreadLocalRandom.current().nextInt(2)+1);

        } else {
            autosomalGenes[10] = (1);
        }
        if(ThreadLocalRandom.current().nextInt(100)>WTC){
            autosomalGenes[11] = (ThreadLocalRandom.current().nextInt(2)+1);

        } else {
            autosomalGenes[11] = (1);
        }

        //Tuxedo [ tuxedo, wildtype ]
        if(ThreadLocalRandom.current().nextInt(100)>WTC){
            autosomalGenes[12] = (ThreadLocalRandom.current().nextInt(2)+1);
        } else {
            autosomalGenes[12] = (2);
        }
        if(ThreadLocalRandom.current().nextInt(100)>WTC){
            autosomalGenes[13] = (ThreadLocalRandom.current().nextInt(2)+1);

        } else {
            autosomalGenes[13] = (2);
        }

        //Extention [ black, wildtype, self ]
        if(ThreadLocalRandom.current().nextInt(100)>WTC){
            autosomalGenes[14] = (ThreadLocalRandom.current().nextInt(3)+1);

        } else {
            autosomalGenes[14] = (2);
        }
        if(ThreadLocalRandom.current().nextInt(100)>WTC){
            autosomalGenes[15] = (ThreadLocalRandom.current().nextInt(3)+1);

        } else {
            autosomalGenes[15] = (2);
        }

        //Agouti [ PaleShaded, Shaded, RedTrimmedBlack, Bay, Mahogany, BlackTan, rBlack]
        if(ThreadLocalRandom.current().nextInt(100)>WTC){
            autosomalGenes[16] = (ThreadLocalRandom.current().nextInt(7)+1);

        } else {
            autosomalGenes[16] = (2);
        }
        if(ThreadLocalRandom.current().nextInt(100)>WTC){
            autosomalGenes[17] = (ThreadLocalRandom.current().nextInt(7)+1);

        } else {
            autosomalGenes[17] = (2);
        }

        //Banana Ears genes [ no banana, banana, bananaless ]
        if(ThreadLocalRandom.current().nextInt(100)>WTC){
            autosomalGenes[18] = (ThreadLocalRandom.current().nextInt(3)+1);

        } else {
            autosomalGenes[18] = (2);
        }
        if(ThreadLocalRandom.current().nextInt(100)>WTC){
            autosomalGenes[19] = (ThreadLocalRandom.current().nextInt(3)+1);

        } else {
            autosomalGenes[19] = (2);
        }

        //Suri coat genes [ normal, suri ]
        if(ThreadLocalRandom.current().nextInt(100)>WTC){
            autosomalGenes[20] = (ThreadLocalRandom.current().nextInt(2)+1);

        } else {
            autosomalGenes[20] = (1);
        }
        if(ThreadLocalRandom.current().nextInt(100)>WTC){
            autosomalGenes[21] = (ThreadLocalRandom.current().nextInt(2)+1);

        } else {
            autosomalGenes[21] = (1);
        }

        //Coat Length genes [ normal, Longer, Longest ]
        if(ThreadLocalRandom.current().nextInt(100)>WTC){
            autosomalGenes[22] = (ThreadLocalRandom.current().nextInt(3)+1);

        } else {
            autosomalGenes[22] = (1);
        }
        if(ThreadLocalRandom.current().nextInt(100)>WTC){
            autosomalGenes[23] = (ThreadLocalRandom.current().nextInt(3)+1);

        } else {
            autosomalGenes[23] = (1);
        }

        //Coat Length suppressor [ normal, shorter ]
        if(ThreadLocalRandom.current().nextInt(100)>WTC){
            autosomalGenes[24] = (ThreadLocalRandom.current().nextInt(3)+1);

        } else {
            autosomalGenes[24] = (1);
        }
        if(ThreadLocalRandom.current().nextInt(100)>WTC){
            autosomalGenes[25] = (ThreadLocalRandom.current().nextInt(3)+1);

        } else {
            autosomalGenes[25] = (1);
        }

        //Coat Length amplifier [ normal, double ]
        if(ThreadLocalRandom.current().nextInt(100)>WTC){
            autosomalGenes[26] = (ThreadLocalRandom.current().nextInt(3)+1);

        } else {
            autosomalGenes[26] = (1);
        }
        if(ThreadLocalRandom.current().nextInt(100)>WTC){
            autosomalGenes[27] = (ThreadLocalRandom.current().nextInt(3)+1);

        } else {
            autosomalGenes[27] = (1);
        }

        //nose placement genes [ +0.1, +0.15/+0.05, 0, -0.1 ]
        if(ThreadLocalRandom.current().nextInt(100)>WTC){
            autosomalGenes[28] = (ThreadLocalRandom.current().nextInt(4)+1);

        } else {
            autosomalGenes[28] = (2);
        }
        if(ThreadLocalRandom.current().nextInt(100)>WTC){
            autosomalGenes[29] = (ThreadLocalRandom.current().nextInt(4)+1);

        } else {
            autosomalGenes[29] = (2);
        }

        //nose placement genes [ +0.1, +0.15/0.05, 0, -0.1 ]
        if(ThreadLocalRandom.current().nextInt(100)>WTC){
            autosomalGenes[30] = (ThreadLocalRandom.current().nextInt(4)+1);

        } else {
            autosomalGenes[30] = (1);
        }
        if(ThreadLocalRandom.current().nextInt(100)>WTC){
            autosomalGenes[31] = (ThreadLocalRandom.current().nextInt(4)+1);

        } else {
            autosomalGenes[31] = (1);
        }

        //nose placement genes [ +0.2, +0.15, 0, -0.15, -0.2 ]
        if(ThreadLocalRandom.current().nextInt(100)>WTC){
            autosomalGenes[32] = (ThreadLocalRandom.current().nextInt(5)+1);

        } else {
            autosomalGenes[32] = (1);
        }
        if(ThreadLocalRandom.current().nextInt(100)>WTC){
            autosomalGenes[33] = (ThreadLocalRandom.current().nextInt(5)+1);

        } else {
            autosomalGenes[33] = (1);
        }

        //Health genes A [ wildtype, healthier1, healthier2, unhealthy]
        if(ThreadLocalRandom.current().nextInt(100)>WTC){
            autosomalGenes[34] = (ThreadLocalRandom.current().nextInt(4)+1);

        } else {
            autosomalGenes[34] = (1);
        }
        if(ThreadLocalRandom.current().nextInt(100)>WTC){
            autosomalGenes[35] = (ThreadLocalRandom.current().nextInt(4)+1);

        } else {
            autosomalGenes[35] = (1);
        }

        //Health genes B [ wildtype, healthier1, healthier2, unhealthy]
        if(ThreadLocalRandom.current().nextInt(100)>WTC){
            autosomalGenes[36] = (ThreadLocalRandom.current().nextInt(4)+1);

        } else {
            autosomalGenes[36] = (1);
        }
        if(ThreadLocalRandom.current().nextInt(100)>WTC){
            autosomalGenes[37] = (ThreadLocalRandom.current().nextInt(4)+1);

        } else {
            autosomalGenes[37] = (1);
        }

        //Health genes C [ wildtype, healthier1, healthier2, unhealthy]
        if(ThreadLocalRandom.current().nextInt(100)>WTC){
            autosomalGenes[38] = (ThreadLocalRandom.current().nextInt(4)+1);

        } else {
            autosomalGenes[38] = (1);
        }
        if(ThreadLocalRandom.current().nextInt(100)>WTC){
            autosomalGenes[39] = (ThreadLocalRandom.current().nextInt(4)+1);

        } else {
            autosomalGenes[39] = (1);
        }

        return new Genes(autosomalGenes);
    }

}
