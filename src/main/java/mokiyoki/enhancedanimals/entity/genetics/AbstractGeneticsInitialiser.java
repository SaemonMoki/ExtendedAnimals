package mokiyoki.enhancedanimals.entity.genetics;

import com.mojang.datafixers.util.Pair;
import mokiyoki.enhancedanimals.config.GeneticAnimalsConfig;
import mokiyoki.enhancedanimals.util.Breed;
import mokiyoki.enhancedanimals.util.Genes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public abstract class AbstractGeneticsInitialiser {
    int WTC = GeneticAnimalsConfig.COMMON.wildTypeChance.get();

    protected Genes generateNewGenetics(LevelAccessor world, BlockPos pos, boolean generateBreed, List<Breed> breeds) {
        Holder<Biome> holder = world.getBiome(pos);

        ((ServerLevel) world).registryAccess().registry(Registries.BIOME);

        Genes localWildType = generateLocalWildGenetics(holder,pos, holder.value() == ForgeRegistries.BIOMES.getValue(Biomes.THE_VOID.location()) || GeneticAnimalsConfig.COMMON.spawnWithRandomBiome.get());

        if (generateBreed) {
            int areaSize = 1; // stand-in for config option 1 gives 1 breed per chunk has to be at least 1
            int posX = (pos.getX()>>4)/areaSize;
            int posZ = (pos.getZ()>>4)/areaSize;
            Random random = new Random(posX+((ServerLevel)world).getSeed()*(posZ-posX));
            Breed breed = selectBreed(breeds, holder, random);
            return breed.generateGenes(localWildType);
        }

        return localWildType;
    }

    protected Genes generateWithBreed(LevelAccessor world, BlockPos pos, List<Breed> breeds, String breedAsString) {
        if (isBiome(breedAsString)) {
            return generateWithBiome(world, breedAsString, pos);
        }

        Holder<Biome> biome = world.getBiome(pos);

        if (breedAsString.equals("WanderingTrader")) {
            Collections.shuffle(breeds);
            return breeds.get(0).generateGenes(generateLocalWildGenetics(biome, pos, true));
        }

        breedAsString = breedAsString.toLowerCase();
        Genes localWildType = generateLocalWildGenetics(biome, pos, false/*world.getWorldInfo().getGenerator() == WorldType.FLAT*/);

        if (hasBreed(breeds, breedAsString)) {
            Breed breed = getBreedFromString(breeds, breedAsString);
            return breed.generateGenes(localWildType);
        }

        return localWildType;
    }

    protected Genes generateWithBiome(LevelAccessor world, String biome, BlockPos pos) {
        biome = biome.toLowerCase();

        Registry<Biome> biomeRegistry = ((ServerLevel) world).registryAccess().registry(Registries.BIOME).get();

        if (biome.contains("darkwoods") || biome.equals("darkforest")) {
            return generateLocalWildGenetics(biomeRegistry.getHolder(Biomes.DARK_FOREST).get(), pos, false);
        } else if (biome.contains("savanna")) {
            return generateLocalWildGenetics(biomeRegistry.getHolder(Biomes.SAVANNA).get(), pos, false);
        } else if (biome.contains("desert")) {
            return generateLocalWildGenetics(biomeRegistry.getHolder(Biomes.DESERT).get(), pos, false);
        } else if (biome.contains("tundra") || biome.contains("snow")) {
            return generateLocalWildGenetics(biomeRegistry.getHolder(Biomes.SNOWY_PLAINS).get(), pos, false);
        } else if (biome.contains("mountains")) {
            return generateLocalWildGenetics(biomeRegistry.getHolder(Biomes.WINDSWEPT_HILLS).get(), pos, false);
        } else if (biome.contains("sunflower")) {
            return generateLocalWildGenetics(biomeRegistry.getHolder(Biomes.SUNFLOWER_PLAINS).get(), pos, false);
        } else if (biome.contains("marsh") || biome.equals("swamp")) {
            return generateLocalWildGenetics(biomeRegistry.getHolder(Biomes.SWAMP).get(), pos, false);
        } else if (biome.contains("jungle")) {
            return generateLocalWildGenetics(biomeRegistry.getHolder(Biomes.JUNGLE).get(), pos, false);
        } else if (biome.contains("mushroom")) {
            return generateLocalWildGenetics(biomeRegistry.getHolder(Biomes.MUSHROOM_FIELDS).get(), pos, false);
        } else if (biome.contains("plains")) {
            return generateLocalWildGenetics(biomeRegistry.getHolder(Biomes.PLAINS).get(), pos, false);
        } else if (biome.contains("flower")) {
            return generateLocalWildGenetics(biomeRegistry.getHolder(Biomes.FLOWER_FOREST).get(), pos, false);
        } else if (biome.contains("woods") || biome.contains("forest")) {
            return generateLocalWildGenetics(biomeRegistry.getHolder(Biomes.FOREST).get(), pos,false);
        }

            return generateLocalWildGenetics(biomeRegistry.getHolder(Biomes.THE_VOID).get(), pos, true);
    }

    protected abstract Genes generateLocalWildGenetics(Holder<Biome> biome, BlockPos blockpos, boolean isFlat);

    public Breed selectBreed(List<Breed> breeds, Holder<Biome> biome, Random random, boolean forTrader) {
        LinkedList<Pair<Float, Breed>> breedsByChance = new LinkedList();
        int listSize = breeds.size();

        breedsByChance.add(new Pair<>(breeds.get(listSize-1).likelyhood(biome.value(), forTrader), breeds.get(listSize-1)));
        for (Breed breed : breeds) {
            if (breedsByChance.size() >= listSize) {
                break;
            }
            float comparison = breedsByChance.getFirst().getFirst();
            float breedLikelyHood = breed.likelyhood(biome.value(), forTrader);
            if (breedLikelyHood < comparison) {
                breedsByChance.addFirst(new Pair<>(breedLikelyHood, breed));
            } else {
                breedsByChance.addLast(new Pair<>(breedLikelyHood, breed));
            }
        }

        for (Pair<Float, Breed> breed : breedsByChance) {
            if (random.nextFloat() <= breed.getFirst()) {
                return breed.getSecond();
            }
        }

        return breedsByChance.getLast().getSecond();

    }

    public Breed selectBreed(List<Breed> selection, Holder<Biome> biome, Random random) {
        return selectBreed(selection, biome, random, false);
    }

    public Boolean hasBreed(List<Breed> listOfBreeds, String selectedBreed) {
        if (!listOfBreeds.isEmpty() && !selectedBreed.isEmpty()) {
            if (selectedBreed.equals("true")) {
                return true;
            }
            for (Breed breed : listOfBreeds) {
                if (breed.getBreedName().contains(selectedBreed)) {
                    return true;
                }
            }
        }
        return false;
    }

    public Boolean isBiome(String biome) {
        biome = biome.toLowerCase();
        if (biome.contains("plains")) {
            return true;
        } else if (biome.contains("darkwoods") || biome.equals("darkforest")) {
            return true;
        } else if (biome.contains("savanna")) {
            return true;
        } else if (biome.contains("desert")) {
            return true;
        } else if (biome.contains("tundra") || biome.contains("snowy")) {
            return true;
        } else if (biome.contains("mountains")) {
            return true;
        } else if (biome.contains("sunflower")) {
            return true;
        } else if (biome.contains("marsh") || biome.equals("swamp")) {
            return true;
        } else if (biome.contains("jungle")) {
            return true;
        } else if (biome.contains("mushroom")) {
            return true;
        } else if (biome.contains("flower")) {
            return true;
        }

        return false;
    }

    private Breed getBreedFromString(List<Breed> listOfBreeds, String selectedBreed) {
        Collections.shuffle(listOfBreeds);
        if (selectedBreed.equals("true")) {
            return listOfBreeds.get(0);
        }
        for (Breed breed : listOfBreeds) {
            if (breed.getBreedName().contains(selectedBreed)) {
                return breed;
            }
        }
        return null;
    }

    protected boolean getChance(int value) {
        return ThreadLocalRandom.current().nextInt(100) > value;
    }

    protected boolean getChance(float value) {
        return ThreadLocalRandom.current().nextFloat() > value;
    }

    protected boolean getChance() {
        return ThreadLocalRandom.current().nextInt(100) > WTC;
    }
}
