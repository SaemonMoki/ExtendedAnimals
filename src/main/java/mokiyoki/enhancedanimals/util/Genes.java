package mokiyoki.enhancedanimals.util;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Genes {
    private int[] sexlinked;
    private int[] autosomal;

    public Genes (int[] sexlinked, int[] autosomal) {
        this.sexlinked = sexlinked;
        this.autosomal = autosomal;
    }

    public Genes (int[] autosomal) {
        this.sexlinked = new int[]{1, 1};
        this.autosomal = autosomal;
    }

    public Genes (int sexlinked, int autosomal) {
        this.sexlinked = sexlinked > 2 ? new int[sexlinked] : new int[2];
        this.autosomal = autosomal > 2 ? new int[autosomal] : new int[2];
    }

    public Genes (int autosomal) {
        this.sexlinked = new int[2];
        this.autosomal = autosomal > 2 ? new int[autosomal] : new int[2];
    }

    public Genes (Genes genes) {
        this.sexlinked = genes.getSexlinkedGenes();
        this.autosomal = genes.getAutosomalGenes();
    }

    public Genes (String genesAsString) {
        if (genesAsString.contains("+")) {
            String[] genesToSplit = genesAsString.split("\\+");
            String[] sexlink = genesToSplit[0].split(",");
            String[] autosomal = genesToSplit[1].split(",");

            Genes genes = new Genes(sexlink.length, autosomal.length);
            int i = 0;
            for (String allele : sexlink) {
                genes.setSexlinkedGene(i, Integer.parseInt(allele));
                i++;
            }

            i = 0;
            for (String allele : autosomal) {
                genes.setAutosomalGene(i, Integer.parseInt(allele));
                i++;
            }

            setGenes(genes.getSexlinkedGenes(), genes.getAutosomalGenes());
            this.sexlinked = genes.getSexlinkedGenes();
            this.autosomal = genes.getAutosomalGenes();
        } else {
            //reading old genes
            String[] genesToSplit = genesAsString.split(",");

            if (genesToSplit[17].equals("10")) {
                Genes genes = new Genes(18, genesToSplit.length);
                //assume its a chicken
                for (int i = 0; i < 9; i++) {
                    genes.setSexlinkedGene(i*2, Integer.parseInt(genesToSplit[i]));
                    genes.setSexlinkedGene((i*2)+1, Integer.parseInt(genesToSplit[i]));
                }
                int i = 0;
                for (String allele : genesToSplit) {
                    if (i < 20) {
                        genes.setAutosomalGene(i, 1);
                    } else {
                        genes.setAutosomalGene(i, Integer.parseInt(allele));
                    }
                    i++;
                }
                this.sexlinked = genes.getSexlinkedGenes();
                this.autosomal = genes.getAutosomalGenes();
            } else {
                Genes genes = new Genes(genesToSplit.length);
                int i = 0;
                for (String allele : genesToSplit) {
                    genes.setSexlinkedGene(i, Integer.parseInt(allele));
                }
                this.sexlinked = new int[]{1, 1};
                this.autosomal = genes.getAutosomalGenes();
            }
        }


    }

    public Genes setGenes(int[] sexlinked, int[] autosomal) {
        this.sexlinked = sexlinked;
        this.autosomal = autosomal;
        return this;
    }

    public Genes setGenes(int[] autosomal) {
        //only to be used when there are no sexlinked genes
        this.sexlinked = new int[]{1, 1};
        this.autosomal = autosomal;
        return this;
    }

    public Genes setGenes(Genes genes) {
        this.sexlinked = genes.getSexlinkedGenes();
        this.autosomal = genes.getAutosomalGenes();
        return this;
    }

    public void setSexlinkedGenes(int[] sexlinkedGenes) {
        this.sexlinked = sexlinkedGenes;
    }

    public void setAutosomalGenes(int[] autosomalGenes) {
        this.autosomal = autosomalGenes;
    }

    public void setSexlinkedGene(int gene, int allel) {
        this.sexlinked[gene] = allel;
    }

    public void setAutosomalGene(int gene, int allel) {
        this.autosomal[gene] = allel;
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
                haploid[i+1] = this.sexlinked[i];
            }
            return haploid;
        }
    }

    public int[] getAutosomalGenes() {
        return this.autosomal;
    }

    public int getAutosomalGene(int gene) {
        return this.autosomal[gene];
    }

    public int getSexlinkedGene(int gene) {
        return this.sexlinked[gene];
    }

    public int getNumberOfSexlinkedGenes() {
        return this.sexlinked == null ? 0 : this.sexlinked.length;
    }

    public int getNumberOfAutosomalGenes() {
        return this.autosomal == null ? 0 : this.autosomal.length;
    }

    public String getGenesAsString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < this.sexlinked.length; i++){
            sb.append(sexlinked[i]);
            if (i != sexlinked.length - 1){
                sb.append(",");
            }
        }

        sb.append("+");
        for (int i = 0; i < this.autosomal.length; i++){
            sb.append(autosomal[i]);
            if (i != autosomal.length - 1){
                sb.append(",");
            }
        }

        return sb.toString();
    }

    public Genes getGamite(boolean isDiploid, Species species) {
        /**
         * this does not make a half get of genes like you may expect but instead scrambles the genes
         * this is to allow male/female or omnisexual breedings to work on 1 system
         */
        Genes gamiteGenes = new Genes(this.sexlinked.length, this.autosomal.length);
        int length = this.sexlinked.length;
            if (isDiploid) {
                for (int i = 0; i < length;i+=2) {
                    if (ThreadLocalRandom.current().nextBoolean()) {
                        gamiteGenes.setSexlinkedGene(i, this.sexlinked[i]);
                        gamiteGenes.setSexlinkedGene(i+1, this.sexlinked[i+1]);
                    } else {
                        gamiteGenes.setSexlinkedGene(i, this.sexlinked[i+1]);
                        gamiteGenes.setSexlinkedGene(i+1, this.sexlinked[i]);
                    }
                }
            } else {
                for (int i = 0; i < length;i+=2) {
                    gamiteGenes.setSexlinkedGene(i, this.sexlinked[i]);
                    gamiteGenes.setSexlinkedGene(i+1, this.sexlinked[i]);
                }
            }

        length = this.autosomal.length;
            for (int i = 0; i < length;i+=2) {
                if (ThreadLocalRandom.current().nextBoolean()) {
                    gamiteGenes.setAutosomalGene(i, this.autosomal[i]);
                    gamiteGenes.setAutosomalGene(i+1, this.autosomal[i+1]);
                } else {
                    gamiteGenes.setAutosomalGene(i, this.autosomal[i+1]);
                    gamiteGenes.setAutosomalGene(i+1, this.autosomal[i]);
                }
            }

            List<GeneLink> linkages = getLinkages(species);
            if (linkages.get(0).isNotBlank()) {
                for (GeneLink linkage : linkages) {
                    gamiteGenes = linkage.getLinkedGenes(this, gamiteGenes, isDiploid);
                }
            }

        return gamiteGenes;
    }

    public Genes getGamite(boolean isDiploid) {
        return getGamite(isDiploid, Species.UNIMPORTANT);
    }

    public Genes makeChild(boolean thisIsDiploid, Genes donorParent, boolean donorIsDiploid, Species species) {
        Genes childGenes;

        if (donorParent != null) {
            Genes donor;
            if (thisIsDiploid) {
                childGenes = this.getGamite(true, species);
                donor = donorParent.getGamite(false, species);
            } else if (donorIsDiploid) {
                childGenes = donorParent.getGamite(true, species);
                donor = this.getGamite(false, species);
            } else {
                childGenes = this.getGamite(false, species);
                donor = donorParent.getGamite(false, species);
            }

            int sexlinkedGenes = childGenes.getNumberOfSexlinkedGenes();
            int[] donorSexChromosome = donor.getSexlinkedGenes();
            for (int i = 0; i < sexlinkedGenes;i+=2) {
                childGenes.setSexlinkedGene(i+1, donorSexChromosome[i]);
            }

            int autosomalGenes = childGenes.getNumberOfAutosomalGenes();
            int[] donorAutosomes = donor.getAutosomalGenes();
            for (int i = 0; i < autosomalGenes;i+=2) {
                childGenes.setAutosomalGene(i+1, donorAutosomes[i]);
            }
        } else {
            childGenes = this.getGamite(thisIsDiploid, species);
        }

        return childGenes;
    }

    public Genes makeChild(Boolean isDiploid, Species species) {
        //this is for bees which can have just 1 female parent
        return this.getGamite(isDiploid, species);
    }

    public Genes makeChild(Genes donor) {
        return this.makeChild(true, donor, true, Species.UNIMPORTANT);
    }

    public List<GeneLink> getLinkages(Species species) {
        List<GeneLink> linkages = new ArrayList<>();
        if (species == Species.CHICKEN) {
            linkages.add(new GeneLink(48, 62, 0.97F));
        } else {
            linkages.add(new GeneLink(0, 0, 0.0F));
        }
        return linkages;
    }

    public class GeneLink {
        boolean isAutosomal;
        int gene1;
        int gene2;
        float link;

        public GeneLink(boolean isAutosomal, int gene1, int gene2, float link) {
            this.isAutosomal = isAutosomal;
            this.gene1 = gene1;
            this.gene2 = gene2;
            this.link = link;
        }

        public GeneLink(int gene1, int gene2, float link) {
            this.isAutosomal = true;
            this.gene1 = gene1;
            this.gene2 = gene2;
            this.link = link;
        }

        public Genes getLinkedGenes(Genes original, Genes gamite, boolean isDiploid) {
            if (ThreadLocalRandom.current().nextFloat() < this.link) {
                if (this.isAutosomal || isDiploid) {
                    if (ThreadLocalRandom.current().nextBoolean()) {
                        gamite.setAutosomalGene(this.gene1, original.getAutosomalGene(this.gene1));
                        gamite.setAutosomalGene(this.gene1+1, original.getAutosomalGene(this.gene1+1));
                        gamite.setAutosomalGene(this.gene2, original.getAutosomalGene(this.gene2));
                        gamite.setAutosomalGene(this.gene2+1, original.getAutosomalGene(this.gene2+1));
                    } else {
                        gamite.setAutosomalGene(this.gene1, original.getAutosomalGene(this.gene1+1));
                        gamite.setAutosomalGene(this.gene1+1, original.getAutosomalGene(this.gene1));
                        gamite.setAutosomalGene(this.gene2, original.getAutosomalGene(this.gene2+1));
                        gamite.setAutosomalGene(this.gene2+1, original.getAutosomalGene(this.gene2));
                    }
                }
            }

            return gamite;
        }

        public boolean isNotBlank() {
            return this.gene1 != this.gene2;
        }
    }

    public enum Species {
        CHICKEN,
        RABBIT,
        COW,
        PIG,
        SHEEP,
        LLAMA,
        HORSE,
        UNIMPORTANT

    }
}