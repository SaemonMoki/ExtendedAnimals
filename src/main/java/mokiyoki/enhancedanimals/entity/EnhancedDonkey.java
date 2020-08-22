package mokiyoki.enhancedanimals.entity;

import mokiyoki.enhancedanimals.config.EanimodCommonConfig;
import net.minecraft.entity.EntityType;
import net.minecraft.world.World;

import java.util.concurrent.ThreadLocalRandom;

public class EnhancedDonkey extends EnhancedHorse{

    //TODO add texture layers

    private static final int WTC = EanimodCommonConfig.COMMON.wildTypeChance.get();
    private static final int GENES_LENGTH = 32;
    private int[] genes = new int[GENES_LENGTH];
    private int[] mateGenes = new int[GENES_LENGTH];
    private int[] mitosisGenes = new int[GENES_LENGTH];
    private int[] mateMitosisGenes = new int[GENES_LENGTH];


    public EnhancedDonkey(EntityType<? extends EnhancedHorse> entityType, World worldIn) {
        super(entityType, worldIn);
    }
    //Health 15-30

    //Speed 0.1125–0.3375

    //Jump 0.4–1.0


    //TODO draw the rest of the horse


    private int[] createInitialGenes() {
        int[] initialGenes = new int[GENES_LENGTH];


        //Health Base genes [ weaker, stronger1, wildtype, stronger2 ]
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

        //Health Modifier genes [ weaker, stronger1, wildtype, stronger2 ]
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

        //Speed Base genes [ weaker, stronger1, wildtype, stronger2 ]
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

        //Speed Modifier genes [ weaker, stronger1, wildtype, stronger2 ]
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

        //Jump Base genes [ weaker, stronger1, wildtype, stronger2 ]
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

        //Jump Modifier genes [ weaker, stronger1, wildtype, stronger2 ]
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

        //Extention [ Wildtype, red ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[12] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[12] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[13] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[13] = (1);
        }

        //Agouti [ Wildtype, red ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[14] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[14] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[15] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[15] = (1);
        }

        //Dun [ Dun+, not dun ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[16] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[16] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[17] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[17] = (1);
        }

        //Dominant White Spotted [ White, Spotted, wildtype ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[18] = (ThreadLocalRandom.current().nextInt(3) + 1);
            initialGenes[19] = (3);

        } else {
            initialGenes[18] = (3);
            initialGenes[19] = (3);
        }

        //Roan [ Roan, wildtype ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[20] = (ThreadLocalRandom.current().nextInt(2) + 1);
            initialGenes[21] = (2);

        } else {
            initialGenes[20] = (2);
            initialGenes[21] = (2);
        }

        //Star [ Star, wildtype ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[22] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[22] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[23] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[23] = (1);
        }

        //No Light Points [ Wildtype, nlp ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[24] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[24] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[25] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[25] = (1);
        }

        //Ivory [ Wildtype, ivory ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[26] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[26] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[27] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[27] = (1);
        }

        //Long Hair 1 [ Wildtype, long hair ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[28] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[28] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[29] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[29] = (1);
        }

        //Long Hair 2 [ Wildtype, long hair ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[30] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[30] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[31] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[31] = (1);
        }

            return initialGenes;

    }
}
