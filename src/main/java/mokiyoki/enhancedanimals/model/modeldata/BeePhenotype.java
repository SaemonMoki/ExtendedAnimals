package mokiyoki.enhancedanimals.model.modeldata;

public class BeePhenotype implements Phenotype {
    public boolean hasOvipositor = true;
    public boolean hasThorax = false;
    public int thoraxSize = 0;
    public int abdomenSize = 7;

    public BeePhenotype(int[] genes, boolean female, int queenMod) {

        if (!female) this.hasOvipositor = false;

        calculateThoraxSize(genes);
        calculateAbdomenSize(genes, queenMod);

    }

    private void calculateAbdomenSize(int[] genes, int queenMod) {
        if (genes[4] != 1 || genes[5] != 1) {
            abdomenSize++;
            if (genes[4] >= 3 || genes[5] >= 3) {
                abdomenSize++;
                if (genes[4] == 4 || genes[5] == 4) {
                    abdomenSize++;
                }
            }
        }
        if (genes[2]==3|| genes[3]==3) abdomenSize--;
        if (genes[6]==2 || genes[7]==2) {
            abdomenSize--;
        }
        if (genes[8]==2 || genes[9]==2) {
            abdomenSize--;
        }
        if (genes[10]==2 || genes[11]==2) {
            abdomenSize--;
        }
        abdomenSize += queenMod;

        if (abdomenSize < 4) abdomenSize = 4;
    }

    private void calculateThoraxSize(int[] genes) {
        if (genes[0] != 1 || genes[1] != 1) {
            thoraxSize = 3;
            if (genes[0] >= 3) {
                thoraxSize+= genes[0]==4 ? 2 : 1;
            }

            if (genes[1] >= 3) {
                thoraxSize+= genes[1]==4 ? 2 : 1;
            }

            if (thoraxSize > 3 && (genes[2]!=1 && genes[3]!=1)) thoraxSize--;

            hasThorax = true;
        }
    }
}
