package mokiyoki.enhancedanimals.ai.rabbit;

import mokiyoki.enhancedanimals.ai.EnhancedEatPlantsGoal;
import mokiyoki.enhancedanimals.entity.EnhancedAnimalAbstract;
import net.minecraft.block.Blocks;

public class EnhancedRabbitEatPlantsGoal extends EnhancedEatPlantsGoal {

    public EnhancedRabbitEatPlantsGoal(EnhancedAnimalAbstract animalIn) {
        super(animalIn);
        this.eaterIsShort = true;
        this.setEdiblePlants();
    }
}
