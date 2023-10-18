package mokiyoki.enhancedanimals.ai.general;

import mokiyoki.enhancedanimals.entity.EnhancedAnimalAbstract;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;

public class EnhancedWanderingGoal extends WaterAvoidingRandomStrollGoal {

    EnhancedAnimalAbstract enhancedAnimal;

    public EnhancedWanderingGoal(EnhancedAnimalAbstract enhancedAnimal, double speedIn) {
        super(enhancedAnimal, speedIn);
        this.enhancedAnimal = enhancedAnimal;
    }

    @Override
    public boolean canUse() {
        if (enhancedAnimal.isAnimalSleeping() || this.enhancedAnimal.getAIStatus() == AIStatus.FOCUSED) {
            return false;
        }
        boolean superShould = super.canUse();
        return superShould && !enhancedAnimal.getAIStatus().equals(AIStatus.EATING);
    }

    @Override
    public boolean canContinueToUse() {
        if (enhancedAnimal.isAnimalSleeping()) {
            return false;
        }
        return super.canUse();
    }
}
