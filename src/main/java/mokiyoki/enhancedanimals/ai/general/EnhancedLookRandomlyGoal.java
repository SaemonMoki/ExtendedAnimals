package mokiyoki.enhancedanimals.ai.general;

import mokiyoki.enhancedanimals.entity.EnhancedAnimal;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;

public class EnhancedLookRandomlyGoal extends LookRandomlyGoal {

    protected MobEntity entityIn;

    public EnhancedLookRandomlyGoal(MobEntity entitylivingIn) {
        super(entitylivingIn);
        entityIn = entitylivingIn;
    }

    @Override
    public boolean shouldExecute() {
        if (((EnhancedAnimal)entityIn).isAnimalSleeping()) {
            return false;
        } else {
            return super.shouldExecute();
        }
    }


}
