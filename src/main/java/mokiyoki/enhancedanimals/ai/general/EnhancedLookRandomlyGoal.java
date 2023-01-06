package mokiyoki.enhancedanimals.ai.general;

import mokiyoki.enhancedanimals.entity.EnhancedAnimalAbstract;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;

public class EnhancedLookRandomlyGoal extends RandomLookAroundGoal {

    protected Mob entityIn;

    public EnhancedLookRandomlyGoal(Mob entitylivingIn) {
        super(entitylivingIn);
        entityIn = entitylivingIn;
    }

    @Override
    public boolean canUse() {
        if (((EnhancedAnimalAbstract)entityIn).isAnimalSleeping()) {
            return false;
        } else {
            return super.canUse();
        }
    }


}
