package mokiyoki.enhancedanimals.entity;

import java.util.concurrent.ThreadLocalRandom;

public class EnhancedDonkey {

    //TODO add texture layers

    private static final int WTC = 90;
    private static final int GENES_LENGTH = 20;
    private int[] genes = new int[GENES_LENGTH];
    private int[] mateGenes = new int[GENES_LENGTH];
    private int[] mitosisGenes = new int[GENES_LENGTH];
    private int[] mateMitosisGenes = new int[GENES_LENGTH];

    //Health 15-30

    //Speed 0.1125–0.3375

    //Jump 0.4–1.0


    //TODO draw the rest of the horse


    private int[] createInitialGenes() {
        int[] initialGenes = new int[GENES_LENGTH];

        //Extention [ Wildtype, red ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[0] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[0] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[1] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[1] = (1);
        }

        //Agouti [ Wildtype, red ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[0] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[0] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[1] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[1] = (1);
        }

        //Dun [ Dun+, not dun ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[0] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[0] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[1] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[1] = (1);
        }

        //No Light Points [ Wildtype, nlp ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[0] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[0] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[1] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[1] = (1);
        }

        //Dominant White Spotted [ White, Spotted, wildtype ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[0] = (ThreadLocalRandom.current().nextInt(3) + 1);
            initialGenes[1] = (3);

        } else {
            initialGenes[0] = (3);
            initialGenes[1] = (3);
        }

        //Star [ Star, wildtype ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[0] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[0] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[1] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[1] = (1);
        }

        //Roan [ Roan, wildtype ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[0] = (ThreadLocalRandom.current().nextInt(2) + 1);
            initialGenes[1] = (2);

        } else {
            initialGenes[0] = (2);
            initialGenes[1] = (2);
        }

        //Ivory [ Wildtype, ivory ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[0] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[0] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[1] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[1] = (1);
        }

        //Long Hair 1 [ Wildtype, long hair ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[0] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[0] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[1] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[1] = (1);
        }

        //Long Hair 2 [ Wildtype, long hair ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[0] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[0] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[1] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[1] = (1);
        }

            return initialGenes;

    }
}
