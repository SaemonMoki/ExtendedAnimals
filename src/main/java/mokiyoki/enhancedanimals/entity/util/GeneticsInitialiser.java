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
            if (gene.contains("-")) {
                String[] splitGene = gene.split("-");
                ThreadLocalRandom.current().nextInt(Integer.valueOf(splitGene[0]), (Integer.valueOf(splitGene[1])+1));
            } else {
                genes[index] = Integer.valueOf(gene);
            }
        }
    }

    public static class ChickenGeneticsInitialiser extends GeneticsInitialiser {
        Map<String, List<String[]>> breedsForBiomes = new HashMap<String, List<String[]>>();
        Map<String, List<String[]>> breedsForVillages = new HashMap<String, List<String[]>>();

        public int[] generateNewChickenGenetics(String biome) {
            int[] chickenGenes = new int[Reference.CHICKEN_GENES_LENGTH];

            List<String[]> allPossibleGeneticsForBiome = breedsForBiomes.get(biome);

            String[] selectedGeneticsBase = allPossibleGeneticsForBiome.get(ThreadLocalRandom.current().nextInt(allPossibleGeneticsForBiome.size()));

            runGeneticsScrambler(selectedGeneticsBase, chickenGenes);

            return chickenGenes;

        }

        public ChickenGeneticsInitialiser() {
            String[] plainsBreed = new String[]{"1-2","1","1","1","1-2","1-3","1-4","1-4"};
//            String[] taigaBreed = new String[]{0,0,0,0,0,0,0,0};
//            String[] leghorn = new String[]{0,0,0,0,0,0,0,0};
//            String[] wyandotte = new String[]{0,0,0,0,0,0,0,0};
//            String[] araucana = new String[]{0,0,0,0,0,0,0,0};

            List<String[]> breedsForPlains = new ArrayList<>();
            breedsForPlains.add(plainsBreed);

            breedsForBiomes.put("Plains", breedsForPlains);


            List<String[]> breedsForPlainsVillage = new ArrayList<>();
//            breedsForVillages.add(leghorn);
//            breedsForVillages.add(wyandotte);
//            breedsForVillages.add(araucana);

            breedsForVillages.put("PlainsVillage", breedsForPlainsVillage);
        }

    }

}
