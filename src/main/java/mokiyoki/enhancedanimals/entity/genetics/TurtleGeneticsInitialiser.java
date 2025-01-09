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
    List<Breed> types = new ArrayList<>();

    public TurtleGeneticsInitialiser() {
        this.breeds.add(TurtleBreeds.ALBINO);
        this.breeds.add(TurtleBreeds.AXANTHIC);
        this.breeds.add(TurtleBreeds.MELANIZED);
        this.breeds.add(TurtleBreeds.PIBALD);
        this.breeds.add(TurtleBreeds.SNOW);
        this.breeds.add(TurtleBreeds.NATURAL);

        this.types.add(TurtleBreeds.SCALE);
        this.types.add(TurtleBreeds.CLOWN);
        this.types.add(TurtleBreeds.FLAME);
        this.types.add(TurtleBreeds.FLAME_SCALE);
        this.types.add(TurtleBreeds.FLAME_CLOWN);
        this.types.add(TurtleBreeds.PATTERNLESS);
        this.types.add(TurtleBreeds.FLAME_SCALE_BLUE);
        this.types.add(TurtleBreeds.GOLDEN);
        this.types.add(TurtleBreeds.GOLD_SCALED);
        this.types.add(TurtleBreeds.LAVENDER);
        this.types.add(TurtleBreeds.TORTISHELL);
        this.types.add(TurtleBreeds.COLOURFUL_TORTISHELL);
        this.types.add(TurtleBreeds.COLOURFUL_DARK);

        this.types.addAll(this.breeds);
    }

    public Genes generateNewGenetics(LevelAccessor world, BlockPos pos, boolean generateBreed) {
        return super.generateNewGenetics(world, pos, generateBreed, this.breeds);
    }

    public Genes generateWithBreed(LevelAccessor world, BlockPos pos, String breed) {
        return super.generateWithBreed(world, pos, breed.equals("WanderingTrader")? this.breeds : this.types, breed);
    }

    @Override
    public Genes generateLocalWildGenetics(Holder<Biome> biomeHolder, BlockPos blockpos, boolean isFlat) {
        int[] autosomalGenes = new int[Reference.TURTLE_AUTOSOMAL_GENES_LENGTH];
        Biome biome = biomeHolder.value();

        /**
         *      [0,1]   - Albino
         *      [2,3]   - Axanthic
         *      [4,5]   - Melanized
         *      [6,7]   - Piebald
         *      [8,9]   - speckle to spot piebald modifier
         */

        for (int i = 0; i < 10; i++) {
            autosomalGenes[i] = ThreadLocalRandom.current().nextInt(100) > WTC ? 2 : 1;
        }

        /**
         *      [10,11] - tortishell
         */
        autosomalGenes[10] = ThreadLocalRandom.current().nextInt(100) > WTC ? ThreadLocalRandom.current().nextInt(2)+1 : 1;
        autosomalGenes[11] = ThreadLocalRandom.current().nextInt(100) > WTC ? ThreadLocalRandom.current().nextInt(2)+1 : 1;

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

        /**
         *      [30,31] - pattern [1=wildtype, 2=solid(allpattern), 3=flame, 4=patternless]
         *      2 > 1 >= 3 >= 4
         */
        autosomalGenes[30] = ThreadLocalRandom.current().nextInt(100) > WTC ? ThreadLocalRandom.current().nextInt(4)+1 : 1;
        autosomalGenes[31] = ThreadLocalRandom.current().nextInt(100) > WTC ? ThreadLocalRandom.current().nextInt(4)+1 : 1;

        /**
         *      [32,33] - pattern [1=wildtype, 2=scale, 3=clown]
         *      epistatic to 30/31 = solid
         */
        autosomalGenes[32] = ThreadLocalRandom.current().nextInt(100) > WTC ? ThreadLocalRandom.current().nextInt(3)+1 : 1;
        autosomalGenes[33] = ThreadLocalRandom.current().nextInt(100) > WTC ? ThreadLocalRandom.current().nextInt(3)+1 : 1;

        /**
         *      [34,35] golden
         *      [36,37] golden
         *      [38,39] golden
         *      [40,41] golden
         *      [42,43] golden
         *      [44,45] golden
         *      [46,47] golden
         *      [48,49] golden
         */
        for (int i = 34; i < 50; i++) {
            autosomalGenes[i] = ThreadLocalRandom.current().nextInt(100) > WTC ? 2 : 1;
        }

        /**
         *
         *      G Axanthic        Shifts green to cyan to blue to black
         *      G Non Axanthic    Shifts green to olive to brown to black
         *      M Axanthic        Shifts (cyan)grey to (blue)dark grey to black
         *      M Non Axanthic    Shifts orange to red to black
         *
         *
         *      [50,51] darkenHue
         *      [52,53] darkenHue
         *      [54,55] darkenHue
         *      [56,57] darkenHue
         *      [58,59] darkenHue
         *      [60,61] darkenHue
         *      [62,63] darkenHue
         *      [64,65] darkenHue
         */
        for (int i = 50; i < 66; i++) {
            autosomalGenes[i] = ThreadLocalRandom.current().nextInt(100) > WTC ? 2 : 1;
        }

        /**
         *      [66,67] - green pigmentType [green, olive]
         */
        autosomalGenes[66] = ThreadLocalRandom.current().nextInt(100) > WTC ? ThreadLocalRandom.current().nextInt(2)+1 : 1;
        autosomalGenes[67] = ThreadLocalRandom.current().nextInt(100) > WTC ? ThreadLocalRandom.current().nextInt(2)+1 : 1;

        /**
         *      [68,69] - melanin pigmentType [wildtype, melanin]
         */
        autosomalGenes[68] = ThreadLocalRandom.current().nextInt(100) > WTC ? ThreadLocalRandom.current().nextInt(2)+1 : 1;
        autosomalGenes[69] = ThreadLocalRandom.current().nextInt(100) > WTC ? ThreadLocalRandom.current().nextInt(2)+1 : 1;

        /**
         *      [70,71] - lavender
         */
        autosomalGenes[70] = ThreadLocalRandom.current().nextInt(100) > WTC ? ThreadLocalRandom.current().nextInt(2)+1 : 1;
        autosomalGenes[71] = ThreadLocalRandom.current().nextInt(100) > WTC ? ThreadLocalRandom.current().nextInt(2)+1 : 1;


        /**
         *      [72,73] - countershaded [non-countershaded, crispcountershaded, countershaded, scale-countershaded]
         */
        autosomalGenes[72] = ThreadLocalRandom.current().nextInt(100) > WTC ? ThreadLocalRandom.current().nextInt(4)+1 : 1;
        autosomalGenes[73] = ThreadLocalRandom.current().nextInt(100) > WTC ? ThreadLocalRandom.current().nextInt(4)+1 : 1;

        /**
         *      [74,75] golden
         *      [76,77] golden
         */
        for (int i = 74; i < 78; i++) {
            autosomalGenes[i] = ThreadLocalRandom.current().nextInt(100) > WTC ? 2 : 1;
        }

        return new Genes(autosomalGenes);
    }
}
