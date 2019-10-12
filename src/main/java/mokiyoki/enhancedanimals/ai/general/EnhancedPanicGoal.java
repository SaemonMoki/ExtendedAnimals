package mokiyoki.enhancedanimals.ai.general;

import mokiyoki.enhancedanimals.entity.EnhancedAnimal;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.ai.goal.PanicGoal;
import net.minecraft.util.math.BlockPos;

public class EnhancedPanicGoal extends PanicGoal {

    public EnhancedPanicGoal(CreatureEntity creature, double speedIn) {
        super(creature, speedIn);
    }

    public boolean shouldExecute() {
        if (this.creature.getRevengeTarget() == null && !this.creature.isBurning()) {
            return false;
        } else {
            ((EnhancedAnimal)this.creature).awaken();
            if (this.creature.isBurning()) {
                BlockPos blockpos = this.getRandPos(this.creature.world, this.creature, 5, 4);
                if (blockpos != null) {
                    this.randPosX = (double)blockpos.getX();
                    this.randPosY = (double)blockpos.getY();
                    this.randPosZ = (double)blockpos.getZ();
                    return true;
                }
            }

            return this.findRandomPosition();
        }
    }
}
