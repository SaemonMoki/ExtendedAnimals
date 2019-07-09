package mokiyoki.enhancedanimals.entity;

import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;

import java.util.concurrent.ThreadLocalRandom;

public class EnhancedHorse {
//    private static final DataParameter<String> SHARED_GENES = EntityDataManager.<String>createKey(EnhancedHorse.class, DataSerializers.STRING);

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

        //Health Base gene [ weaker, stronger1, wildtype, stronger2 ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[0] = (ThreadLocalRandom.current().nextInt(4) + 1);

        } else {
            initialGenes[0] = (3);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[1] = (ThreadLocalRandom.current().nextInt(4) + 1);

        } else {
            initialGenes[1] = (3);
        }

        //Health Modifier gene [ weaker, stronger1, wildtype, stronger2 ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[2] = (ThreadLocalRandom.current().nextInt(4) + 1);

        } else {
            initialGenes[2] = (3);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[3] = (ThreadLocalRandom.current().nextInt(4) + 1);

        } else {
            initialGenes[3] = (3);
        }

        //Speed Base gene [ weaker, stronger1, wildtype, stronger2 ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[4] = (ThreadLocalRandom.current().nextInt(4) + 1);

        } else {
            initialGenes[4] = (3);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[5] = (ThreadLocalRandom.current().nextInt(4) + 1);

        } else {
            initialGenes[5] = (3);
        }

        //Speed Modifier gene [ weaker, stronger1, wildtype, stronger2 ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[6] = (ThreadLocalRandom.current().nextInt(4) + 1);

        } else {
            initialGenes[6] = (3);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[7] = (ThreadLocalRandom.current().nextInt(4) + 1);

        } else {
            initialGenes[7] = (3);
        }

        //Jump Base gene [ weaker, stronger1, wildtype, stronger2 ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[8] = (ThreadLocalRandom.current().nextInt(4) + 1);

        } else {
            initialGenes[8] = (3);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[9] = (ThreadLocalRandom.current().nextInt(4) + 1);

        } else {
            initialGenes[9] = (3);
        }

        //Jump Modifier gene [ weaker, stronger1, wildtype, stronger2 ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[10] = (ThreadLocalRandom.current().nextInt(4) + 1);

        } else {
            initialGenes[10] = (3);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[11] = (ThreadLocalRandom.current().nextInt(4) + 1);

        } else {
            initialGenes[11] = (3);
        }

        //Extension [ black, wildtype ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[12] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[12] = (2);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[13] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[13] = (2);
        }

        //Agouti [ Wildtype/light bay, bay, brown/tan, solid/black ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[14] = (ThreadLocalRandom.current().nextInt(3) + 1);

        } else {
            initialGenes[14] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[15] = (ThreadLocalRandom.current().nextInt(3) + 1);

        } else {
            initialGenes[15] = (1);
        }

        //Dun [ Dun+, saturated dun, saturated coat ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[16] = (ThreadLocalRandom.current().nextInt(3) + 1);

        } else {
            initialGenes[16] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[17] = (ThreadLocalRandom.current().nextInt(3) + 1);

        } else {
            initialGenes[17] = (1);
        }

        //Dominant White Spotting [ W1-, W2-, W3-, W4-, W5, W6, W7, W8, W9-, W10-, W11-, W12, W13-, W14-, W15, W16, W17-, W18, W19, W20, W21, W22, W23-, W24-, W25-, W26, W27, wildtype ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            if (ThreadLocalRandom.current().nextInt(100) > 99) {
                initialGenes[18] = (ThreadLocalRandom.current().nextInt(28) + 1);
            }else{
                initialGenes[18] = (ThreadLocalRandom.current().nextInt(10) + 18);
            }
            initialGenes[19] = (28);

        } else {
            initialGenes[18] = (28);
            initialGenes[19] = (28);
        }

        //Roan [ Roan, wildtype ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[20] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[20] = (2);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[21] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[21] = (2);
        }

        //Cream [ Wildtype, Cream, pearl ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[10] = (ThreadLocalRandom.current().nextInt(3) + 1);

        } else {
            initialGenes[10] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[11] = (ThreadLocalRandom.current().nextInt(3) + 1);

        } else {
            initialGenes[11] = (1);
        }

        //Champagne [ Champagne, wildtype ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[10] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[10] = (2);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[11] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[11] = (2);
        }

        //Silver [ Silver, wildtype ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[10] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[10] = (2);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[11] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[11] = (2);
        }

        //Mushroom [ Champagne, wildtype ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[10] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[10] = (2);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[11] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[11] = (2);
        }

        //Grey [ Grey , wildtype ]  this one turns the coat white over a few years
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[10] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[10] = (2);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[11] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[11] = (2);
        }

        //White Frame Overo [ white spots- , wildtype ]  this is lethal in double dose
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[10] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[10] = (2);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[11] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[11] = (2);
        }

        //Tobiano [ Tobiano, wildtype ] pinto spots
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[10] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[10] = (2);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[11] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[11] = (2);
        }

        //Leopard Spotting [ Leopard, wildtype ] appaloosa
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[10] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[10] = (2);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[11] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[11] = (2);
        }

        //Leopard Spot Modifier [ Modified, wildtype ] wildtype makes varnish appaloosa
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[10] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[10] = (2);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[11] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[11] = (2);
        }

        //Splash White Locus 1 [ classic splash white1, splash white3-, macchiato, wildtype ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[10] = (ThreadLocalRandom.current().nextInt(4) + 1);

        } else {
            initialGenes[10] = (4);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[11] = (ThreadLocalRandom.current().nextInt(4) + 1);

        } else {
            initialGenes[11] = (4);
        }

        //Splash White Locus 2 [ splash white2, splash white4-, wildtype ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[10] = (ThreadLocalRandom.current().nextInt(3) + 1);

        } else {
            initialGenes[10] = (3);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[11] = (ThreadLocalRandom.current().nextInt(3) + 1);

        } else {
            initialGenes[11] = (3);
        }

        //Tiger Eye [ Wildtype, te1, te2 ] te1 = lightens eyes to yellow/amber/orange, te2 = lightens eyes to blue in cream horses
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[10] = (ThreadLocalRandom.current().nextInt(3) + 1);

        } else {
            initialGenes[10] = (3);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[11] = (ThreadLocalRandom.current().nextInt(3) + 1);

        } else {
            initialGenes[11] = (3);
        }



        return initialGenes;
    }

}
