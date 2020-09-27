package mokiyoki.enhancedanimals.ai.general;

import mokiyoki.enhancedanimals.entity.EnhancedAnimalAbstract;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.goal.LookAtGoal;

public class EnhancedLookAtGoal extends LookAtGoal {

    public EnhancedLookAtGoal(MobEntity entityIn, Class<? extends LivingEntity> watchTargetClass, float maxDistance) {
        super(entityIn, watchTargetClass, maxDistance);
    }

    public boolean shouldExecute() {
        if(((EnhancedAnimalAbstract)entity).isAnimalSleeping()) {
            return false;
        }

        return super.shouldExecute();
    }
}
