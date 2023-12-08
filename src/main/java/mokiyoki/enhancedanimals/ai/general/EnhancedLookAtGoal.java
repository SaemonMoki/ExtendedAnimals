package mokiyoki.enhancedanimals.ai.general;

import mokiyoki.enhancedanimals.entity.EnhancedAnimalAbstract;
import mokiyoki.enhancedanimals.entity.EnhancedChicken;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;

public class EnhancedLookAtGoal extends LookAtPlayerGoal {

    public EnhancedLookAtGoal(Mob entityIn, Class<? extends LivingEntity> watchTargetClass, float maxDistance) {
        super(entityIn, watchTargetClass, maxDistance);
    }

    public boolean canUse() {
        if(((EnhancedAnimalAbstract)mob).isAnimalSleeping()) {
            return false;
        }

        return super.canUse();
    }

    @Override
    public boolean canContinueToUse() {
        if(!this.mob.getPassengers().isEmpty() && this.mob.getFirstPassenger() instanceof EnhancedChicken && this.mob.getNavigation().isInProgress()) {
            return false;
        }

        return super.canContinueToUse();
    }
}
