package mokiyoki.enhancedanimals.ai.general;

import mokiyoki.enhancedanimals.entity.EnhancedAnimalAbstract;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.core.BlockPos;

public class EnhancedPanicGoal extends PanicGoal {

    public EnhancedPanicGoal(PathfinderMob creature, double speedIn) {
        super(creature, speedIn);
    }

    public boolean canUse() {
        if (this.mob.getLastHurtByMob() == null && !this.mob.isOnFire()) {
            return false;
        } else {
            ((EnhancedAnimalAbstract)this.mob).awaken();
            if (this.mob.isOnFire()) {
                BlockPos blockpos = this.lookForWater(this.mob.level(), this.mob, 5);
                if (blockpos != null) {
                    this.posX = (double)blockpos.getX();
                    this.posY = (double)blockpos.getY();
                    this.posZ = (double)blockpos.getZ();
                    return true;
                }
            }

            return this.findRandomPosition();
        }
    }
}
