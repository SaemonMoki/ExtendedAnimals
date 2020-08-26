package mokiyoki.enhancedanimals.entity.Genetics;

import javafx.util.Pair;
import mokiyoki.enhancedanimals.config.EanimodCommonConfig;
import mokiyoki.enhancedanimals.util.Breed;
import mokiyoki.enhancedanimals.util.Genes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public abstract class AbstractGeneticsInitialiser {
    int WTC = EanimodCommonConfig.COMMON.wildTypeChance.get();

    protected Genes generateNewGenetics(IWorld world, BlockPos pos, boolean generateBreed, List<Breed> breeds) {
        Biome biome = world.getBiome(pos);
        Genes localWildType = generateLocalWildGenetics(biome, world.getWorldInfo().getGenerator() == WorldType.FLAT || biome == Biomes.THE_VOID);

        if (generateBreed) {
            int areaSize = 1; // stand-in for config option 1 gives 1 breed per chunk has to be at least 1
            int posX = (pos.getX()>>4)/areaSize;
            int posZ = (pos.getZ()>>4)/areaSize;
            Random random = new Random(posX+world.getSeed()*(posZ-posX));
            Breed breed = selectBreed(breeds, biome, random);
            return breed.generateGenes(localWildType);
        }

        return localWildType;
    }

    protected Genes generateWithBreed(IWorld world, BlockPos pos, List<Breed> breeds, String breedAsString) {
        Biome biome = world.getBiome(pos);
        Genes localWildType = generateLocalWildGenetics(biome, world.getWorldInfo().getGenerator() == WorldType.FLAT);

        if (hasBreed(breeds, breedAsString)) {
            Breed breed = getBreedFromString(breeds, breedAsString);
            return breed.generateGenes(localWildType);
        }

        return localWildType;
    }

    protected abstract Genes generateLocalWildGenetics(Biome biome, boolean isFlat);

    public Breed selectBreed(List<Breed> breeds, Biome biome, Random random, boolean forTrader) {
        LinkedList<Pair<Float, Breed>> breedsByChance = new LinkedList();
        int listSize = breeds.size();

        breedsByChance.add(new Pair<>(breeds.get(listSize-1).likelyhood(biome, forTrader), breeds.get(listSize-1)));
        for (Breed breed : breeds) {
            if (breedsByChance.size() >= listSize) {
                break;
            }
            float comparison = breedsByChance.getFirst().getKey();
            float breedLikelyHood = breed.likelyhood(biome, forTrader);
            if (breedLikelyHood < comparison) {
                breedsByChance.addFirst(new Pair<>(breedLikelyHood, breed));
            } else {
                breedsByChance.addLast(new Pair<>(breedLikelyHood, breed));
            }
        }

        for (Pair<Float, Breed> breed : breedsByChance) {
            if (random.nextFloat() <= breed.getKey()) {
                return breed.getValue();
            }
        }

        return breedsByChance.getLast().getValue();

    }

    public Breed selectBreed(List<Breed> selection, Biome biome, Random random) {
        return selectBreed(selection, biome, random, false);
    }

    public Breed selectBreed(List<Breed> selection, boolean forTrader) {
        return selectBreed(selection, Biomes.THE_VOID, new Random(), forTrader);
    }

    public Boolean hasBreed(List<Breed> listOfBreeds, String selectedBreed) {
        for (Breed breed : listOfBreeds) {
            if (breed.getBreedName().toLowerCase().contains(selectedBreed.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    private Breed getBreedFromString(List<Breed> listOfBreeds, String selectedBreed) {
        Collections.shuffle(listOfBreeds);
        for (Breed breed : listOfBreeds) {
            if (breed.getBreedName().toLowerCase().contains(selectedBreed.toLowerCase())) {
                return breed;
            }
        }
        return null;
    }
}
