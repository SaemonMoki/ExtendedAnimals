package mokiyoki.enhancedanimals.util;

import javafx.util.Pair;
import mokiyoki.enhancedanimals.init.ChickenBreeds;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class GeneSketch {
    /**
     * geneSketch keys should only be even numbers to match up with
     *  the int[] they are eventually converted to.
     */
    HashMap<Integer, String> geneSketch = new HashMap<>();

    public GeneSketch (Pair<Integer, String>... geneSketch ) {
        Map<Integer, String> geneMap = new HashMap<>();

        for(Pair<Integer, String> pair : geneSketch) {
            geneMap.put(pair.getKey(), pair.getValue());
        }
    }

    public GeneSketch () {
    }

    public GeneSketch add(float rarity, int gene, String sketch) {
        if (ThreadLocalRandom.current().nextFloat() < rarity) {
            this.geneSketch.put(gene, sketch);
        }
        return this;
    }

    public GeneSketch add(Breed.Rarity rarity, int gene, String sketch) {
        float rare = 1.0F;
        switch (rarity) {
            case COMMON: rare = 0.8F;
                break;
            case UNCOMMON: rare = 0.6F;
                break;
            case RARE: rare = 0.2F;
                break;
            case EXOTIC: rare = 0.05F;
        }

        add(rare, gene, sketch);
        return this;
    }

    public GeneSketch add(int gene, String sketch) {
        this.geneSketch.put(gene, sketch);
        return this;
    }

    public GeneSketch add(int gene, String... sketch) {
        for (String string : sketch) {
            this.geneSketch.put(gene, string);
            gene+=2;
        }
        return this;
    }

    public GeneSketch add(float rarity, int gene, String... sketch) {
        for (String string : sketch) {
            if (ThreadLocalRandom.current().nextFloat() < rarity) {
                this.geneSketch.put(gene, string);
            }
            gene+=2;
        }
        return this;
    }

    public GeneSketch add(Breed.Rarity rarity, int gene, String... sketch) {
        float rare = 1.0F;
        switch (rarity) {
            case COMMON: rare = 0.8F;
                break;
            case UNCOMMON: rare = 0.6F;
                break;
            case RARE: rare = 0.2F;
                break;
            case EXOTIC: rare = 0.05F;
        }

        add(rare, gene, sketch);
        return this;
    }

    public String getSketchAt(int index) {
        return geneSketch.get(index);
    }

    public boolean hasGene(int gene) {
        return this.geneSketch.containsKey(gene);
    }

    public void addLayer(GeneSketch layer, float rarity) {
        HashMap<Integer, String> overlaySketch = layer.getRawSketch();
        for(Integer gene : overlaySketch.keySet()) {
            if (ThreadLocalRandom.current().nextFloat() <= rarity) {
                this.geneSketch.put(gene, overlaySketch.get(gene));
            }
        }
    }

    public void addLayer(GeneSketch layer, Breed.Rarity rarity) {
        Float rare = 1.0F;
        switch (rarity) {
            case COMMON: rare = 0.8F;
                break;
            case UNCOMMON: rare = 0.6F;
                break;
            case RARE: rare = 0.2F;
                break;
            case EXOTIC: rare = 0.05F;
        }

        addLayer(layer, rare);
    }

    public void addLayer(GeneSketch layer) {
        HashMap<Integer, String> overlaySketch = layer.getRawSketch();
        for(Integer gene : overlaySketch.keySet()) {
            this.geneSketch.put(gene, overlaySketch.get(gene));
        }
    }

    public int[] getGeneArray(int[] genes, boolean isDiploid) {
        int length = genes.length;
        if (!this.geneSketch.containsKey(-1)) {
            for (int i = 0; i <= length; i += 2) {
                if (this.geneSketch.containsKey(i)) {
                    String gene = this.geneSketch.get(i);
                    String allele_A;
                    String allele_B;
                    if (gene.contains(",")) {
                        String[] splitGene = gene.split(",");
                        allele_A = splitGene[0];
                        allele_B = splitGene[1];
                        if (!isDiploid && ThreadLocalRandom.current().nextBoolean()) {
                            allele_A = splitGene[1];
                        }
                    } else {
                        allele_A = gene;
                        allele_B = gene;
                    }

                    genes[i] = getAllele(allele_A);
                    if (isDiploid) {
                        genes[i + 1] = getAllele(allele_B);
                    } else {
                        genes[i + 1] = -1;
                    }
                }
            }
        }

        return genes;
    }

    public int[] getGeneArray(int[] genes) {
        return getGeneArray(genes, true);
    }

    private HashMap<Integer, String> getRawSketch() {
        return this.geneSketch;
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
}