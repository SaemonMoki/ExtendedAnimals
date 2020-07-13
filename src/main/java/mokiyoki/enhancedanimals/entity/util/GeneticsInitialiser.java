package mokiyoki.enhancedanimals.entity.util;

import mokiyoki.enhancedanimals.util.Reference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class GeneticsInitialiser {

    private static void runGeneticsScrambler(String[] selectedGeneticsBase, int[] genes) {
        int index = 0;
        for(String gene : selectedGeneticsBase) {
            String allele_A;
            String allele_B;
            if (gene.contains(",")) {
                String[] splitGene = gene.split(",");
                allele_A = splitGene[0];
                allele_B = splitGene[1];
            } else {
                allele_A = gene;
                allele_B = gene;
            }

            genes[index] = getAllele(allele_A);
            index++;
            genes[index] = getAllele(allele_B);
            index++;

        }
    }

    private static int getAllele(String alleleData) {
        if (alleleData.contains("-")) {
            String[] splitGene = alleleData.split("-");
            return ThreadLocalRandom.current().nextInt(Integer.valueOf(splitGene[0]), (Integer.valueOf(splitGene[1])));
        } else if (alleleData.contains("|")) {
            String[] splitGene = alleleData.split("\\|");
            return Integer.valueOf(splitGene[ThreadLocalRandom.current().nextInt((splitGene.length))]);
        } else {
            return Integer.valueOf(alleleData);
        }
    }

    public static class ChickenGeneticsInitialiser extends GeneticsInitialiser {
        Map<String, List<String[]>> breedsForBiomes = new HashMap<String, List<String[]>>();
        Map<String, List<String[]>> breedsForVillages = new HashMap<String, List<String[]>>();
        Map<String, List<String[]>> exactBreeds = new HashMap<String, List<String[]>>();

        public int[] generateNewChickenGenetics(String biome) {
            int[] chickenGenes = new int[Reference.CHICKEN_GENES_LENGTH];

            List<String[]> allPossibleGeneticsForBiome = breedsForBiomes.get(biome);

            String[] selectedGeneticsBase = allPossibleGeneticsForBiome.get(ThreadLocalRandom.current().nextInt(allPossibleGeneticsForBiome.size()));

            runGeneticsScrambler(selectedGeneticsBase, chickenGenes);

            return chickenGenes;

        }

        public ChickenGeneticsInitialiser() {
//            if (breed != localwildtype) {
//                String[] plainsBreed = new String[]{"1-2", "1-2", "1|3|5,1|3", "7", "1-2", "1|3|6|9", "1-4", "1-4"};
//                String[] plainsBreed2 = new String[]{"1", "1", "5,1|3", "7", "1", "1|3|6|9", "1-3", "1-4"};
//                String[] leghorn = new String[]{"exact genes"};
//            }
//
//            String[] plainsBreed = new String[]{"1-2", "1-2", "1|3|5,1|3", "7", "1-2", "1|3|6|9", "1-4", "1-4"};
//
//
//
//            List<String[]> breedsForPlains = new ArrayList<>();
//            breedsForPlains.add(plainsBreed);
//
//            breedsForBiomes.put("Plains", breedsForPlains);
//
//
//            List<String[]> breedsForPlainsVillage = new ArrayList<>();
////            breedsForVillages.add(leghorn);
////            breedsForVillages.add(wyandotte);
////            breedsForVillages.add(araucana);
//
//            breedsForVillages.put("PlainsVillage", breedsForPlainsVillage);
//
//
//            breedsForVillages.put("Leghorn", new List<String>().add(leghorn));

        }

    }

}
