package mokiyoki.enhancedanimals.entity.genetics;

import mokiyoki.enhancedanimals.init.breeds.AxolotlBreeds;
import mokiyoki.enhancedanimals.util.Breed;
import mokiyoki.enhancedanimals.util.Genes;
import mokiyoki.enhancedanimals.util.Reference;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.biome.Biome;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class AxolotlGeneticsInitialiser extends AbstractGeneticsInitialiser {
    List<Breed> breeds = new ArrayList<>();

    public AxolotlGeneticsInitialiser() {
        this.breeds.add(AxolotlBreeds.RIBBON);
        this.breeds.add(AxolotlBreeds.DARK_EYES);
        this.breeds.add(AxolotlBreeds.LIGHT_EYES);
        this.breeds.add(AxolotlBreeds.PIGMENTED_EYES);
        this.breeds.add(AxolotlBreeds.PASTEL_EYES);
        this.breeds.add(AxolotlBreeds.GLOW_EYES);
        this.breeds.add(AxolotlBreeds.ALBINO);
        this.breeds.add(AxolotlBreeds.GOLDEN);
        this.breeds.add(AxolotlBreeds.AXANTHIC);
        this.breeds.add(AxolotlBreeds.LEUCISTIC);
        this.breeds.add(AxolotlBreeds.COPPER);
        this.breeds.add(AxolotlBreeds.GREATGILLS);
        this.breeds.add(AxolotlBreeds.GFP);
    }

    public Genes generateNewGenetics(LevelAccessor world, BlockPos pos, boolean generateBreed) {
        return super.generateNewGenetics(world, pos, generateBreed, this.breeds);
    }

    public Genes generateWithBreed(LevelAccessor world, BlockPos pos, String breed) {
        return super.generateWithBreed(world, pos, this.breeds, breed);
    }

    @Override
    public Genes generateLocalWildGenetics(Biome biome, boolean isFlat) {
        int[] autosomalGenes = new int[Reference.AXOLOTL_AUTOSOMAL_GENES_LENGTH];

        /**
         *      [0,1]   - Albino
         *      [2,3]   - Axanthic
         *      [4,5]   - Melano
         *      [6,7]   - Copper
         *      [8,9]   - Leucistic
         */

        for (int i = 0; i < 10; i++) {
            autosomalGenes[i] = getChance() ? 2 : 1;
        }

        /**
         *      [10,11] - EP/GFP - WildType+ > EP(ExtraPigment) > GFP(FlorecentProtein)
         */
        autosomalGenes[10] = getChance() ? (getChance() ? 3 : 2) : 1;
        autosomalGenes[11] = getChance() ? (getChance() ? 3 : 2) : 1;

        /**
         *      [12,13] - WhiteBelly, Piebald - WildType+ > WhiteBelly > Piebald >
         */
        autosomalGenes[12] = getChance() ? (ThreadLocalRandom.current().nextInt(2)+2) : 1;
        autosomalGenes[13] = getChance() ? (ThreadLocalRandom.current().nextInt(2)+2) : 1;

        /**
         *      [14,15] - Piebald Strength - (weak piebald > medium spotted > high white)
         */
        autosomalGenes[14] = ThreadLocalRandom.current().nextInt(10)+1;
        autosomalGenes[15] = ThreadLocalRandom.current().nextInt(10)+1;

        /**
         *      [16,17] - Splotchy - speckles >< splotches
         */
        autosomalGenes[16] = ThreadLocalRandom.current().nextInt(2)+1;
        autosomalGenes[17] = ThreadLocalRandom.current().nextInt(2)+1;

        /**
         *      [18,19] - Berkshire, Blaze - Berkshire > Blaze > Wildtype
         */
        autosomalGenes[18] = getChance() ? (ThreadLocalRandom.current().nextInt(2)+2) : 1;
        autosomalGenes[19] = getChance() ? (ThreadLocalRandom.current().nextInt(2)+2) : 1;

        /**
         *      [20,21] - ColouredEyes - DarkEyes >< PigmentedEyes >< LightEyes >< PastelEyes >< GlowEyes >< NormalEyes
         */
        autosomalGenes[20] = getChance() ? (ThreadLocalRandom.current().nextInt(5)+2) : 1;
        autosomalGenes[21] = getChance() ? (ThreadLocalRandom.current().nextInt(5)+2) : 1;

        /**
         *      [22,23] - EyeRGB
         */
        autosomalGenes[22] = ThreadLocalRandom.current().nextInt(255)+1;
        autosomalGenes[23] = ThreadLocalRandom.current().nextInt(255)+1;

        /**
         *      [24,25] - FP RGB
         */
        autosomalGenes[24] = ThreadLocalRandom.current().nextInt(255)+1;
        autosomalGenes[25] = ThreadLocalRandom.current().nextInt(255)+1;

        /**
         *      [26,27] - LongTail
         */
        autosomalGenes[26] = getChance() ? 2 : 1;
        autosomalGenes[27] = getChance() ? 2 : 1;

        /**
         *      [28,29] - Size - (Small, Large)
         */
        autosomalGenes[28] = ThreadLocalRandom.current().nextInt(5)+1;
        autosomalGenes[29] = ThreadLocalRandom.current().nextInt(5)+1;

        /**
         *      [30,31] - Minature - (Normal, Small)
         */
        autosomalGenes[30] = ThreadLocalRandom.current().nextInt(10)+1;
        autosomalGenes[31] = ThreadLocalRandom.current().nextInt(10)+1;

        /**
         *      [32,33] - Long
         */
        autosomalGenes[32] = getChance() ? 2 : 1;
        autosomalGenes[33] = getChance() ? 2 : 1;

        /**
         *      [34,35] - Greater gills 1
         */
        autosomalGenes[34] = getChance() ? 2 : 1;
        autosomalGenes[35] = getChance() ? 2 : 1;

        /**
         *      [36,37] - Greater gills 2
         */
        autosomalGenes[36] = getChance() ? 2 : 1;
        autosomalGenes[37] = getChance() ? 2 : 1;

        return new Genes(autosomalGenes);
    }
}
