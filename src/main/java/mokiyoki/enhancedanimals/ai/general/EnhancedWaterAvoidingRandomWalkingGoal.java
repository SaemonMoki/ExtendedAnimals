package mokiyoki.enhancedanimals.ai.general;

import mokiyoki.enhancedanimals.entity.EnhancedAnimal;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.util.math.Vec3d;

public class EnhancedWaterAvoidingRandomWalkingGoal extends WaterAvoidingRandomWalkingGoal {

    public EnhancedWaterAvoidingRandomWalkingGoal(CreatureEntity p_i47301_1_, double p_i47301_2_) {
        super(p_i47301_1_, p_i47301_2_);
    }

    public EnhancedWaterAvoidingRandomWalkingGoal(CreatureEntity p_i47302_1_, double p_i47302_2_, float p_i47302_4_) {
        super(p_i47302_1_, p_i47302_2_, p_i47302_4_);
    }

    @Override
    public boolean shouldExecute() {
        if (this.creature.isBeingRidden()) {
            return false;
        } else {
            if (!this.mustUpdate) {
                //Todo make this use Temperaments\
                int hungerModifier = ((EnhancedAnimal)this.creature).getHunger()/50;
                if (hungerModifier >= this.executionChance) {
                    hungerModifier = this.executionChance - 1;
                }


                if (this.creature.getIdleTime() >= 100) {
                    return false;
                }

                if (this.creature.getRNG().nextInt(this.executionChance - hungerModifier) != 0) {
                    return false;
                }
            }

            Vec3d lvt_1_1_ = this.getPosition();
            if (lvt_1_1_ == null) {
                return false;
            } else {
                this.x = lvt_1_1_.x;
                this.y = lvt_1_1_.y;
                this.z = lvt_1_1_.z;
                this.mustUpdate = false;
                return true;
            }
        }
    }



}
