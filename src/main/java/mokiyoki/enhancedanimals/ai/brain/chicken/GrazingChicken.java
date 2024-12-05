package mokiyoki.enhancedanimals.ai.brain.chicken;

import mokiyoki.enhancedanimals.ai.brain.Grazing;
import mokiyoki.enhancedanimals.entity.EnhancedAnimalAbstract;
import mokiyoki.enhancedanimals.entity.EnhancedChicken;
import mokiyoki.enhancedanimals.init.ModMemoryModuleTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.state.BlockState;

public class GrazingChicken extends Grazing {

    public GrazingChicken() {
        super();
    }

    @Override
    protected void start(ServerLevel serverLevel, EnhancedAnimalAbstract geneticAnimal, long gameTime) {
        super.start(serverLevel, geneticAnimal, gameTime);
        ((EnhancedChicken)geneticAnimal).setBrooding(false);
    }

    @Override
    protected void eatBlock(EnhancedAnimalAbstract geneticAnimal, BlockPos currentDestination, int eatenBlock, BlockState newBlock) {
        int root = geneticAnimal.level.random.nextInt(2);
        if (root == 0) {
            super.eatBlock(geneticAnimal, currentDestination, eatenBlock, newBlock);
        }
    }
}
