package mokiyoki.enhancedanimals.ai.general;

import mokiyoki.enhancedanimals.entity.EnhancedAnimalAbstract;
import net.minecraft.world.entity.ai.goal.FollowParentGoal;

public class EnhancedFollowParentGoal extends FollowParentGoal {

    private final EnhancedAnimalAbstract enhancedChild;

    public EnhancedFollowParentGoal(EnhancedAnimalAbstract animal, double speed) {
        super(animal, speed);
        this.enhancedChild = animal;
    }

    public boolean canUse() {
        if (this.enhancedChild.isVehicle() || this.enhancedChild.isAnimalSleeping()) {
            return false;
        }

        return super.canUse();
    }

}
