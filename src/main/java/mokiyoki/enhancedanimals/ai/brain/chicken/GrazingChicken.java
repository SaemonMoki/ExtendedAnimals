package mokiyoki.enhancedanimals.ai.brain.chicken;

import mokiyoki.enhancedanimals.ai.brain.Grazing;
import mokiyoki.enhancedanimals.entity.EnhancedAnimalAbstract;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class GrazingChicken extends Grazing {

    public GrazingChicken() {
        super();
    }

    @Override
    protected void eatBlock(EnhancedAnimalAbstract geneticAnimal, BlockPos currentDestination, int eatenBlock, BlockState newBlock) {
        int root = geneticAnimal.level.random.nextInt(2);
        if (root == 0) {
            super.eatBlock(geneticAnimal, currentDestination, eatenBlock, newBlock);
        }
    }
}
