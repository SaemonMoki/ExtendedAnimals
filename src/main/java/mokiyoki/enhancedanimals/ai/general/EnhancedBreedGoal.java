package mokiyoki.enhancedanimals.ai.general;

import mokiyoki.enhancedanimals.entity.EnhancedAnimalAbstract;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.server.level.ServerLevel;

public class EnhancedBreedGoal extends BreedGoal {

    public EnhancedBreedGoal(Animal animal, double speedIn) {
        super(animal, speedIn);
    }

    public boolean canUse() {
        if (this.animal.isVehicle() || ((EnhancedAnimalAbstract)this.animal).isAnimalSleeping()) {
            return false;
        }
        return super.canUse();
    }

    /**
     * This is so the Growing Age is set lower than vanilla resets it to after Cancelling the event
     */
    protected void breed() {
        this.animal.spawnChildFromBreeding((ServerLevel)this.level, this.partner);
        this.animal.setAge(10);
        this.partner.setAge(10);
    }

}
