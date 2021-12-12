package mokiyoki.enhancedanimals.entity.Genetics;

import javafx.util.Pair;
import mokiyoki.enhancedanimals.config.EanimodCommonConfig;
import mokiyoki.enhancedanimals.util.Breed;
import mokiyoki.enhancedanimals.util.Genes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.gen.carver.WorldCarver;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public abstract class AbstractGeneticsInitialiser {
    int WTC = EanimodCommonConfig.COMMON.wildTypeChance.get();

    protected Genes generateNewGenetics(IWorld world, BlockPos pos, boolean generateBreed, List<Breed> breeds) {
        Biome biome = world.getBiome(pos);

        Genes localWildType = generateLocalWildGenetics(biome,biome == ForgeRegistries.BIOMES.getValue(Biomes.THE_VOID.getLocation()) || EanimodCommonConfig.COMMON.spawnWithRandomBiome.get());

        if (generateBreed) {
            int areaSize = 1; // stand-in for config option 1 gives 1 breed per chunk has to be at least 1
            int posX = (pos.getX()>>4)/areaSize;
            int posZ = (pos.getZ()>>4)/areaSize;
            Random random = new Random(posX+((ServerWorld)world).getSeed()*(posZ-posX));
            Breed breed = selectBreed(breeds, biome, random);
            return breed.generateGenes(localWildType);
        }

        return localWildType;
    }

    protected Genes generateWithBreed(IWorld world, BlockPos pos, List<Breed> breeds, String breedAsString) {
        if (isBiome(breedAsString)) {
            return generateWithBiome(breedAsString);
        }

        Biome biome = world.getBiome(pos);

        if (breedAsString.equals("WanderingTrader")) {
            Collections.shuffle(breeds);
            return breeds.get(0).generateGenes(generateLocalWildGenetics(biome, true));
        }

        breedAsString = breedAsString.toLowerCase();
        Genes localWildType = generateLocalWildGenetics(biome, false/*world.getWorldInfo().getGenerator() == WorldType.FLAT*/);

        if (hasBreed(breeds, breedAsString)) {
            Breed breed = getBreedFromString(breeds, breedAsString);
            return breed.generateGenes(localWildType);
        }

        return localWildType;
    }

    protected Genes generateWithBiome(String biome) {
        biome = biome.toLowerCase();

        if (biome.contains("darkwoods") || biome.equals("darkforest")) {
            return generateLocalWildGenetics(ForgeRegistries.BIOMES.getValue(Biomes.DARK_FOREST.getLocation()), false);
        } else if (biome.contains("savanna")) {
            return generateLocalWildGenetics(ForgeRegistries.BIOMES.getValue(Biomes.SAVANNA.getLocation()), false);
        } else if (biome.contains("desert")) {
            return generateLocalWildGenetics(ForgeRegistries.BIOMES.getValue(Biomes.DESERT.getLocation()), false);
        } else if (biome.contains("tundra") || biome.contains("snow")) {
            return generateLocalWildGenetics(ForgeRegistries.BIOMES.getValue(Biomes.SNOWY_TUNDRA.getLocation()), false);
        } else if (biome.contains("mountains")) {
            return generateLocalWildGenetics(ForgeRegistries.BIOMES.getValue(Biomes.MOUNTAINS.getLocation()), false);
        } else if (biome.contains("sunflower")) {
            return generateLocalWildGenetics(ForgeRegistries.BIOMES.getValue(Biomes.SUNFLOWER_PLAINS.getLocation()), false);
        } else if (biome.contains("marsh") || biome.equals("swamp")) {
            return generateLocalWildGenetics(ForgeRegistries.BIOMES.getValue(Biomes.SWAMP.getLocation()), false);
        } else if (biome.contains("jungle")) {
            return generateLocalWildGenetics(ForgeRegistries.BIOMES.getValue(Biomes.JUNGLE.getLocation()), false);
        } else if (biome.contains("mushroom")) {
            return generateLocalWildGenetics(ForgeRegistries.BIOMES.getValue(Biomes.MUSHROOM_FIELDS.getLocation()), false);
        } else if (biome.contains("plains")) {
            return generateLocalWildGenetics(ForgeRegistries.BIOMES.getValue(Biomes.PLAINS.getLocation()), false);
        } else if (biome.contains("flower")) {
            return generateLocalWildGenetics(ForgeRegistries.BIOMES.getValue(Biomes.FLOWER_FOREST.getLocation()), false);
        } else if (biome.contains("woods") || biome.contains("forest")) {
            return generateLocalWildGenetics(ForgeRegistries.BIOMES.getValue(Biomes.FOREST.getLocation()), false);
        }

            return generateLocalWildGenetics(ForgeRegistries.BIOMES.getValue(Biomes.THE_VOID.getLocation()), true);
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
        return selectBreed(selection, ForgeRegistries.BIOMES.getValue(Biomes.THE_VOID.getLocation()), new Random(), forTrader);
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
