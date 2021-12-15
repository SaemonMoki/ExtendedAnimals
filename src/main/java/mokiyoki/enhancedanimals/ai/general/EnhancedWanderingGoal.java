package mokiyoki.enhancedanimals.ai.general;

import mokiyoki.enhancedanimals.entity.EnhancedAnimalAbstract;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;

public class EnhancedWanderingGoal extends WaterAvoidingRandomWalkingGoal {

    EnhancedAnimalAbstract enhancedAnimal;

    public EnhancedWanderingGoal(EnhancedAnimalAbstract enhancedAnimal, double speedIn) {
        super(enhancedAnimal, speedIn);
        this.enhancedAnimal = enhancedAnimal;
    }

    @Override
    public boolean shouldExecute() {
        if (enhancedAnimal.isAnimalSleeping()) {
            return false;
        }
        boolean superShould = super.shouldExecute();
        return superShould && !enhancedAnimal.getAIStatus().equals(AIStatus.EATING);
    }

    @Override
    public boolean shouldContinueExecuting() {
        if (enhancedAnimal.isAnimalSleeping()) {
            return false;
        }
        return super.shouldExecute();
    }
}
