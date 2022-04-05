package mokiyoki.enhancedanimals.util;

import com.mojang.datafixers.util.Pair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class GeneSketch {
    /**
     * geneSketch keys should only be even numbers to match up with
     *  the int[] they are eventually converted to.
     */
    HashMap<Integer, String> geneSketch = new HashMap<>();
    HashMap<Integer, List<Pair<Float, String>>> weightedGeneSketches = new HashMap<>();

    public GeneSketch (Pair<Integer, String>... geneSketch ) {
        for(Pair<Integer, String> pair : geneSketch) {
            if (pair.getSecond().contains("%")) {
                setWeightedGeneSketch(pair.getFirst(), pair.getSecond());
            } else if (!pair.getSecond().startsWith("_")){
                this.geneSketch.put(pair.getFirst(), pair.getSecond());
            }
        }
    }

    public GeneSketch(int gene, String sketch) {
        if (sketch.contains("%")) {
            setWeightedGeneSketch(gene, sketch);
        } else {
            this.geneSketch.put(gene, sketch);
        }
    }

    public GeneSketch(GeneSketch sketch) {
        this.geneSketch.putAll(sketch.getRawSketch());
        this.weightedGeneSketches.putAll(sketch.getRawWeightedSketch());
    }

    public GeneSketch () {
    }

    public GeneSketch add(int gene, String sketch) {
        if (sketch.contains("%")) {
            setWeightedGeneSketch(gene, sketch);
        } else {
            this.geneSketch.put(gene, sketch);
        }
        return this;
    }

    public GeneSketch add(int gene, String... sketch) {
        for (String string : sketch) {
            if (string.contains("%")) {
                setWeightedGeneSketch(gene, string);
            } else if (string.startsWith("_")) {
                gene += 2;
            } else {
                this.geneSketch.put(gene, string);
                gene += 2;
            }
        }
        return this;
    }

    private void setGeneSketch(int gene, String sketch) {
        this.geneSketch.put(gene, sketch);
    }

    private void setWeightedGeneSketch(int gene, String sketch) {
        String[] weightedsketch = sketch.split("%");
        setWeightedGeneSketches(gene, Float.valueOf(weightedsketch[0])*0.01F, weightedsketch[1]);
    }

    private void setWeightedGeneSketches(int gene, float weight, String sketch) {
        List<Pair<Float, String>> weightedListEntry = new ArrayList<>();
        List<Pair<Float, String>> removePair = new ArrayList<>();
        if (this.weightedGeneSketches.containsKey(gene)) {
            for (Pair<Float, String> alleleSketch : this.weightedGeneSketches.get(gene)) {
                if (alleleSketch.getSecond().equals(sketch)) {
                    removePair.add(alleleSketch);
                }
            }
        }
        weightedListEntry.add(new Pair<>(weight, sketch));
        this.weightedGeneSketches.replace(gene, removePair, weightedListEntry);
    }

    public void addLayer(GeneSketch layer) {
        if (layer != null) {
            if (layer.hasSketch()) {
                HashMap<Integer, String> overlaySketch = layer.getRawSketch();
                for (Integer gene : overlaySketch.keySet()) {
                    this.setGeneSketch(gene, overlaySketch.get(gene));
                }
            }
            if (layer.hasWeightedSketch()) {
                HashMap<Integer,List<Pair<Float, String>>> overlaySketch = layer.getRawWeightedSketch();
                for (Integer gene : overlaySketch.keySet()) {
                    for (Pair<Float, String> sketchPairs : overlaySketch.get(gene)) {
                        this.setWeightedGeneSketches(gene, sketchPairs.getFirst(), sketchPairs.getSecond());
                    }
                }
            }
        }
    }

    public int[] getGeneArray(Float accuracy, int[] genes) {
        if (!this.geneSketch.isEmpty() || !this.weightedGeneSketches.isEmpty()) {
            int length = genes.length;
            for (int i = 0; i < length; i += 2) {
                String gene = "";
                if (this.weightedGeneSketches.containsKey(i)) {
                    List<Pair<Float, String>> geneOptionsList = this.weightedGeneSketches.get(i);
                    for (Pair<Float, String> geneOptions : geneOptionsList) {
                        if (ThreadLocalRandom.current().nextFloat() < geneOptions.getFirst()) {
                            gene = geneOptions.getSecond();
                            break;
                        }
                    }
                }
                if (gene.isEmpty() && this.geneSketch.containsKey(i)){
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
        if (!this.geneSketch.isEmpty() || !this.weightedGeneSketches.isEmpty()) {
            int length = genes.length;
            for (int i = 0; i < length; i += 2) {
                String gene = "";
                if (this.weightedGeneSketches.containsKey(i)) {
                    List<Pair<Float, String>> geneOptionsList = this.weightedGeneSketches.get(i);
                    Collections.shuffle(geneOptionsList);
                    for (Pair<Float, String> geneOptions : geneOptionsList) {
                        if (ThreadLocalRandom.current().nextFloat() < geneOptions.getFirst()) {
                            gene = geneOptions.getSecond();
                            break;
                        }
                    }
                }
                if (gene.isEmpty() && this.geneSketch.containsKey(i)){
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

    public Boolean hasWeightedSketch() {
        return !this.weightedGeneSketches.isEmpty();
    }

    public String getSketchAt(int index) {
        return geneSketch.get(index);
    }

    public boolean hasGene(int gene) {
        return this.geneSketch.containsKey(gene);
    }

    private HashMap<Integer, String> getRawSketch() {
        return this.geneSketch;
    }

    private HashMap<Integer, List<Pair<Float, String>>> getRawWeightedSketch() {
        return this.weightedGeneSketches;
    }

    private static int getAllele(String alleleData) {
        if (alleleData.contains("-")) {
            String[] splitGene = alleleData.split("-");
            int start = Integer.valueOf(splitGene[0]);
            int end = Integer.valueOf(splitGene[1]);
            return ThreadLocalRandom.current().nextInt(Math.max(start, end)- Math.min(start, end))+Math.min(start, end);
//            if (Integer.valueOf(splitGene[0]) > Integer.valueOf(splitGene[1])) {
//                return ThreadLocalRandom.current().nextInt(Integer.valueOf(splitGene[1]), (Integer.valueOf(splitGene[0])));
//            } else {
//                return ThreadLocalRandom.current().nextInt(Integer.valueOf(splitGene[0]), (Integer.valueOf(splitGene[1])));
//            }
        } else if (alleleData.contains("|")) {
            String[] splitGene = alleleData.split("\\|");
            return Integer.valueOf(splitGene[ThreadLocalRandom.current().nextInt((splitGene.length))]);
        } else {
            return Integer.valueOf(alleleData);
        }
    }
}