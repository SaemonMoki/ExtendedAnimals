package mokiyoki.enhancedanimals.ai.general;

import mokiyoki.enhancedanimals.entity.EnhancedAnimalAbstract;
import net.minecraft.entity.ai.goal.FollowParentGoal;

public class EnhancedFollowParentGoal extends FollowParentGoal {

    private final EnhancedAnimalAbstract enhanchedChild;

    public EnhancedFollowParentGoal(EnhancedAnimalAbstract animal, double speed) {
        super(animal, speed);
        this.enhanchedChild = animal;
    }

    public boolean shouldExecute() {
        if (this.enhanchedChild.isBeingRidden() || this.enhanchedChild.isAnimalSleeping()) {
            return false;
        }

        return super.shouldExecute();
    }

}
