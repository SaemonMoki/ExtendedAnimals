package mokiyoki.enhancedanimals.ai.general;

import mokiyoki.enhancedanimals.entity.EnhancedAnimal;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.goal.LookAtGoal;

public class EnhancedLookAtGoal extends LookAtGoal {

    public EnhancedLookAtGoal(MobEntity entityIn, Class<? extends LivingEntity> watchTargetClass, float maxDistance) {
        super(entityIn, watchTargetClass, maxDistance);
    }

    public boolean shouldExecute() {
        if(((EnhancedAnimal)entity).isAnimalSleeping()) {
            return false;
        }

        return super.shouldExecute();
    }
}
