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
//    HashMap<Integer, Pair<Float, String>> rarityGeneSketches = new HashMap<>();

    public GeneSketch (Pair<Integer, String>... geneSketch ) {
        for(Pair<Integer, String> pair : geneSketch) {
            this.geneSketch.put(pair.getKey(), pair.getValue());
        }
    }

    public GeneSketch(int gene, String string) {
        this.geneSketch.put(gene, string);
    }

    public GeneSketch () {
    }

    public GeneSketch add(int gene, String sketch) {
        this.geneSketch.put(gene, sketch);
        return this;
    }

    public GeneSketch add(int gene, String... sketch) {
        for (String string : sketch) {
            this.geneSketch.put(gene, string);
            gene += 2;
        }
        return this;
    }

//    public GeneSketch add(float rarity, int gene, String sketch) {
//        this.rarityGeneSketches.put(gene, new Pair<>(rarity, sketch));
//        return this;
//    }

//    public GeneSketch add(float rarity, int gene, String... sketch) {
//        for (String string : sketch) {
//            if (ThreadLocalRandom.current().nextFloat() < rarity) {
//                this.rarityGeneSketches.put(gene, new Pair<>(rarity, string));
//            }
//            gene += 2;
//        }
//        return this;
//    }

//    public GeneSketch add(Breed.Rarity rarity, int gene, String... sketch) {
//        float rare = 1.0F;
//        switch (rarity) {
//            case COMMON: rare = 0.8F;
//                break;
//            case UNCOMMON: rare = 0.6F;
//                break;
//            case RARE: rare = 0.2F;
//                break;
//            case EXOTIC: rare = 0.05F;
//        }
//
//        add(rare, gene, sketch);
//        return this;
//    }

//    public void addLayer(GeneSketch layer, float rarity) {
//        if (layer.hasSketch()) {
//            HashMap<Integer, String> overlaySketch = layer.getRawSketch();
//            for (Integer gene : overlaySketch.keySet()) {
//                this.rarityGeneSketches.put(gene, new Pair<>(rarity,overlaySketch.get(gene)));
//            }
//        }
//    }

//    public void addLayer(GeneSketch layer, Breed.Rarity rarity) {
//        Float rare = 1.0F;
//        switch (rarity) {
//            case COMMON: rare = 0.8F;
//                break;
//            case UNCOMMON: rare = 0.6F;
//                break;
//            case RARE: rare = 0.2F;
//                break;
//            case EXOTIC: rare = 0.05F;
//        }
//
//        addLayer(layer, rare);
//    }

    public void addLayer(GeneSketch layer) {
        if (layer != null) {
            if (layer.hasSketch()) {
                HashMap<Integer, String> overlaySketch = layer.getRawSketch();
                for (Integer gene : overlaySketch.keySet()) {
                    this.geneSketch.put(gene, overlaySketch.get(gene));
                }
            }
//        if (layer.hasRaritySketch()) {
//            HashMap<Integer,Pair<Float, String>> overlaySketch = layer.getRawRaritySketch();
//            for (Integer gene : overlaySketch.keySet()) {
//                this.rarityGeneSketches.put(gene, overlaySketch.get(gene));
//            }
//        }
        }
    }

    public int[] getGeneArray(Float accuracy, int[] genes) {
        if (!this.geneSketch.isEmpty() ) {
            int length = genes.length;
            for (int i = 0; i < length; i += 2) {
                String gene = "";
//                if (this.rarityGeneSketches.containsKey(i)) {
//                    Pair<Float, String> raregene = this.rarityGeneSketches.get(i);
//                    if (ThreadLocalRandom.current().nextFloat() < raregene.getKey()) {
//                        gene = raregene.getValue();
//                    } else if (this.geneSketch.containsKey(i)){
//                        gene = this.geneSketch.get(i);
//                    }
//                } else
                    if (this.geneSketch.containsKey(i)){
                    gene = this.geneSketch.get(i);
                }
                if (!gene.isEmpty()) {
                    String allele_A;
                    String allele_B;
                    if (gene.contains(",")) {
                        String[] splitGene = gene.split(",");
                        if (ThreadLocalRandom.current().nextBoolean()) {
                            allele_A = splitGene[0];
                            allele_B = splitGene[1];
                        } else {
                            allele_A = splitGene[1];
                            allele_B = splitGene[0];
                        }
                    } else {
                        allele_A = gene;
                        allele_B = gene;
                    }

                    if (ThreadLocalRandom.current().nextFloat() < accuracy) {
                        genes[i] = getAllele(allele_A);
                    }
                    if (ThreadLocalRandom.current().nextFloat() < accuracy) {
                        genes[i+1] = getAllele(allele_B);
                    }
                }
            }
        }

        return genes;
    }

    public int[] getGeneArray(int[] genes) {
        if (!this.geneSketch.isEmpty() ) {
            int length = genes.length;
            for (int i = 0; i < length; i += 2) {
                if (this.geneSketch.containsKey(i)) {
                    String gene = this.geneSketch.get(i);
                    String allele_A;
                    String allele_B;
                    if (gene.contains(",")) {
                        String[] splitGene = gene.split(",");
                        if (ThreadLocalRandom.current().nextBoolean()) {
                            allele_A = splitGene[0];
                            allele_B = splitGene[1];
                        } else {
                            allele_A = splitGene[1];
                            allele_B = splitGene[0];
                        }
                    } else {
                        allele_A = gene;
                        allele_B = gene;
                    }

                    genes[i] = getAllele(allele_A);
                    genes[i+1] = getAllele(allele_B);
                }
            }
        }

        return genes;
    }

    public Boolean hasSketch() {
        return !this.geneSketch.isEmpty();
    }

//    public Boolean hasRaritySketch() {
//        return !this.rarityGeneSketches.isEmpty();
//    }

    public String getSketchAt(int index) {
        return geneSketch.get(index);
    }

    public boolean hasGene(int gene) {
        return this.geneSketch.containsKey(gene);
    }

    private HashMap<Integer, String> getRawSketch() {
        return this.geneSketch;
    }

//    private HashMap<Integer, Pair<Float, String>> getRawRaritySketch() {
//        return this.rarityGeneSketches;
//    }

    private static int getAllele(String alleleData) {
        if (alleleData.contains("-")) {
            String[] splitGene = alleleData.split("-");
            if (Integer.valueOf(splitGene[0]) > Integer.valueOf(splitGene[1])) {
                return ThreadLocalRandom.current().nextInt(Integer.valueOf(splitGene[1]), (Integer.valueOf(splitGene[0])));
            } else {
                return ThreadLocalRandom.current().nextInt(Integer.valueOf(splitGene[0]), (Integer.valueOf(splitGene[1])));
            }
        } else if (alleleData.contains("|")) {
            String[] splitGene = alleleData.split("\\|");
            return Integer.valueOf(splitGene[ThreadLocalRandom.current().nextInt((splitGene.length))]);
        } else {
            return Integer.valueOf(alleleData);
        }
    }
}