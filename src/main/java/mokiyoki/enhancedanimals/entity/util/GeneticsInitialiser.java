package mokiyoki.enhancedanimals.entity.util;

import javafx.util.Pair;
import mokiyoki.enhancedanimals.init.ChickenBreeds;
import mokiyoki.enhancedanimals.util.Breed;
import mokiyoki.enhancedanimals.util.Genes;
import mokiyoki.enhancedanimals.util.Reference;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class GeneticsInitialiser {

    public static class ChickenGeneticsInitialiser extends GeneticsInitialiser {
        List<Breed> wildGenetics = new ArrayList<>();
        List<Breed> chickenbreeds = new ArrayList<>();

        public Genes generateNewChickenGenetics(IWorld world, BlockPos pos) {
            Biome biome = world.getBiome(pos);
            int areaSize = 16; // stand-in for config option 1 gives 1 breed per chunk has to be at least 1
            int posX = (pos.getX()>>4)/areaSize;
            int posZ = (pos.getZ()>>4)/areaSize;
            Random random = new Random(posX+world.getSeed()*posZ);
            Genes trueWildType = new Genes(new int[]{1, 1, 1, 1, 5, 6, 1, 1, 1, 1, 1, 1, 3, 6, 1, 1, 1, 1, 1, 1}, new int[]{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,2,2,2,2,2,2,2,2,3,3,2,2,1,1,2,2,1,1,2,2,1,1,3,3,2,2,1,1,2,2,3,3,2,2,3,3,2,2,2,2,3,3,3,3,2,2,1,1,1,1,2,2,2,2,1,1,2,2,2,2,2,2,1,1,1,1,1,1,1,1,1,1,1,1,2,2,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,10,10,6,6,5,5,5,5,6,6,4,6,5,5,2,2,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1});
            Breed breed = ChickenBreeds.LEGHORN.editGenes(selectBreed(chickenbreeds, biome, random).getGeneSketches());

            return trueWildType;

        }

        public ChickenGeneticsInitialiser() {
            wildGenetics.add(ChickenBreeds.PLAINSNATIVE);
            chickenbreeds.add(ChickenBreeds.LEGHORN);
            chickenbreeds.add(ChickenBreeds.ROSECOMBLEGHORN);
            chickenbreeds.add(ChickenBreeds.WYANDOTTE);
            chickenbreeds.add(ChickenBreeds.ORPINGTON);
            chickenbreeds.add(ChickenBreeds.RHODEISLANDRED);
            chickenbreeds.add(ChickenBreeds.PLYMOUTHROCK);
            chickenbreeds.add(ChickenBreeds.BELGIUMBANTAM);
        }

    }

    public static class CowGeneticsInitialiser extends GeneticsInitialiser {
        List<Breed> wildGenetics = new ArrayList<>();
        List<Breed> cowbreeds = new ArrayList<>();


        public Genes generateNewCowGenetics(IWorld world, BlockPos pos) {
            Biome biome = world.getBiome(pos);
            int areaSize = 16; // stand-in for config option 1 gives 1 breed per chunk has to be at least 1
            int posX = (pos.getX() >> 4) / areaSize;
            int posZ = (pos.getZ() >> 4) / areaSize;
            Random random = new Random(posX + world.getSeed() + posZ);
            Genes trueWildType = new Genes(new int[]{2,2}, new int[]{2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2});
            
            return trueWildType;

        }

        public CowGeneticsInitialiser() {

        }

    }

    public static class LlamaGeneticsInitialiser extends GeneticsInitialiser {
        List<Breed> wildGenetics = new ArrayList<>();
        List<Breed> llamabreeds = new ArrayList<>();


        public Genes generateNewLlamaGenetics(IWorld world, BlockPos pos) {
            Biome biome = world.getBiome(pos);
            int areaSize = 16; // stand-in for config option 1 gives 1 breed per chunk has to be at least 1
            int posX = (pos.getX() >> 4) / areaSize;
            int posZ = (pos.getZ() >> 4) / areaSize;
            Random random = new Random(posX + world.getSeed() - posZ);
            Genes trueWildType = new Genes(new int[]{2,2}, new int[]{2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2});

            return trueWildType;

        }

        public LlamaGeneticsInitialiser() {

        }

    }

    public static class PigGeneticsInitialiser extends GeneticsInitialiser {
        List<Breed> wildGenetics = new ArrayList<>();
        List<Breed> pigbreeds = new ArrayList<>();


        public Genes generateNewPigGenetics(IWorld world, BlockPos pos) {
            Biome biome = world.getBiome(pos);
            int areaSize = 16; // stand-in for config option 1 gives 1 breed per chunk has to be at least 1
            int posX = (pos.getX() >> 4) / areaSize;
            int posZ = (pos.getZ() >> 4) / areaSize;
            Random random = new Random(posX + world.getSeed() - posZ);
            Genes trueWildType = new Genes(new int[]{2,2}, new int[]{2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2});

            return trueWildType;

        }

        public PigGeneticsInitialiser() {

        }

    }

    public static class SheepGeneticsInitialiser extends GeneticsInitialiser {
        List<Breed> wildGenetics = new ArrayList<>();
        List<Breed> sheepbreeds = new ArrayList<>();


        public Genes generateNewSheepGenetics(IWorld world, BlockPos pos) {
            Biome biome = world.getBiome(pos);
            int areaSize = 16; // stand-in for config option 1 gives 1 breed per chunk has to be at least 1
            int posX = (pos.getX() >> 4) / areaSize;
            int posZ = (pos.getZ() >> 4) / areaSize;
            Random random = new Random(posX + world.getSeed() - posZ);
            Genes trueWildType = new Genes(new int[]{2,2}, new int[]{2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2});

            return trueWildType;

        }

        public SheepGeneticsInitialiser() {

        }

    }

    public static class RabbitGeneticsInitialiser extends GeneticsInitialiser {
        List<Breed> wildGenetics = new ArrayList<>();
        List<Breed> rabbitbreeds = new ArrayList<>();


        public Genes generateNewRabbitGenetics(IWorld world, BlockPos pos) {
            Biome biome = world.getBiome(pos);
            int areaSize = 16; // stand-in for config option 1 gives 1 breed per chunk has to be at least 1
            int posX = (pos.getX() >> 4) / areaSize;
            int posZ = (pos.getZ() >> 4) / areaSize;
            Random random = new Random(posX + world.getSeed() - posZ);
            Genes trueWildType = new Genes(new int[]{2,2}, new int[]{2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2});

            return trueWildType;

        }

        public RabbitGeneticsInitialiser() {

        }

    }
    
    public Breed selectBreed(List<Breed> selection, Biome biome, Random random, boolean forTrader) {
        LinkedList<Pair<Float, Breed>> breedsByChance = new LinkedList();

        breedsByChance.add(new Pair<>(selection.get(0).likelyhood(biome, forTrader), selection.get(0)));
        for (Breed breed : selection) {
            float comparison = breedsByChance.getFirst().getKey();
            float breedLikelyHood = breed.likelyhood(biome, forTrader);
            if (breedLikelyHood < comparison) {
                breedsByChance.addFirst(new Pair<>(breedLikelyHood, breed));
            } else {
                breedsByChance.addLast(new Pair<>(breedLikelyHood, breed));
            }
        }

        for (Pair<Float, Breed> breed : breedsByChance) {
            if (random.nextFloat() < breed.getKey()) {
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

}
