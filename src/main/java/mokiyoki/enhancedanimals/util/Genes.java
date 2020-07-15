package mokiyoki.enhancedanimals.util;

import javafx.util.Pair;

import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

public class Genes {
    private int[] sexlinked;
    private int[] autosomal;

    public Genes setGenes(int[] sexlinked, int[] autosomal) {
        this.sexlinked = sexlinked;
        this.autosomal = autosomal;
        return this;
    }

    public Genes setGenes(int[] autosomal) {
        this.sexlinked = new int[]{1, 1};
        this.autosomal = autosomal;
        return this;
    }

    public void setSexlinkedGenes(int[] sexlinkedGenes) {
        this.sexlinked = sexlinkedGenes;
    }

    public void setAutosomalGenes(int[] autosomalGenes) {
        this.autosomal = autosomalGenes;
    }

    public void setSexlinkedGene(int gene, int allel) {
        sexlinked[gene] = allel;
    }

    public void setAutosomalGene(int gene, int allel) {
        autosomal[gene] = allel;
    }

    public int[] getSexlinkedGenes() {
        return this.sexlinked;
    }

    public int[] getSexlinkedGenes(boolean isDiploid) {
        if (isDiploid) {
            return this.sexlinked;
        } else {
            int[] haploid = new int[this.sexlinked.length];
            for (int i = 0; i < haploid.length;i+=2){
                haploid[i] = this.sexlinked[i];
                haploid[i+1] = -1;
            }
            return haploid;
        }
    }

    public int[] getAutosomalGenes() {
        return this.autosomal;
    }

    public int getNumberOfSexlinkedGenes() {
        return this.sexlinked.length;
    }

    public int getNumberOfAutosomalGenes() {
        return this.sexlinked.length;
    }

    public Genes getGamite(boolean isDiploid) {
        /**
         * this does not make a half get of genes like you may expect but instead scrambles the genes
         * this is to allow male/female or omnisexual breedings to work on 1 system
         */
        Genes gamiteGenes = new Genes();
        int length = this.sexlinked.length;
        if (length >= 2) {
            int[] gamiteSexChromosome = new int[length];
            if (isDiploid) {
                for (int i = 0; i <= length;i+=2) {
                    if (ThreadLocalRandom.current().nextBoolean()) {
                        gamiteSexChromosome[i] = this.sexlinked[i];
                        gamiteSexChromosome[i+1] = this.sexlinked[i+1];
                    } else {
                        gamiteSexChromosome[i] = this.sexlinked[i+1];
                        gamiteSexChromosome[i+1] = this.sexlinked[i];
                    }
                }
            } else {
                for (int i = 0; i <= length;i+=2) {
                    gamiteSexChromosome[i] = this.sexlinked[i];
                    gamiteSexChromosome[i+1] = this.sexlinked[i];
                }
            }
            gamiteGenes.setSexlinkedGenes(gamiteSexChromosome);
        } else {
            gamiteGenes.setSexlinkedGenes(new int[]{-1, -1});
        }
        length = this.autosomal.length;
        if (length >= 2) {
            int[] gamiteAutosomalChromosomes = new int[length];
            for (int i = 0; i <= length;i+=2) {
                if (ThreadLocalRandom.current().nextBoolean()) {
                    gamiteAutosomalChromosomes[i] = this.autosomal[i];
                    gamiteAutosomalChromosomes[i+1] = this.autosomal[i+1];
                } else {
                    gamiteAutosomalChromosomes[i] = this.autosomal[i+1];
                    gamiteAutosomalChromosomes[i+1] = this.autosomal[i];
                }
            }
            gamiteGenes.setSexlinkedGenes(gamiteAutosomalChromosomes);
        } else {
            gamiteGenes.setAutosomalGenes(new int[]{-1, -1});
        }

        return gamiteGenes;
    }

    public Genes makeChild(Genes dipolidParent, Genes donorParent) {
        Genes childGenes = dipolidParent.getGamite(true);

        if (donorParent != null) {
            int sexlinkedGenes = childGenes.getNumberOfSexlinkedGenes();
            int autosomalGenes = childGenes.getNumberOfAutosomalGenes();
            Genes donor = donorParent.getGamite(false);
            int[] donorSexChromosome = donor.getSexlinkedGenes();
            int[] donorAutosomes = donor.getAutosomalGenes();

            for (int i = 0; i < sexlinkedGenes;i+=2) {
                childGenes.setSexlinkedGene(i+1, donorSexChromosome[i]);
            }
            for (int i = 0; i < autosomalGenes;i+=2) {
                childGenes.setAutosomalGene(i+1, donorAutosomes[i]);
            }
        }

        return childGenes;
    }

    public Genes makeChild(Genes queen) {
        return makeChild(queen, new Genes());
    }

    //TODO add linkage map thing

}