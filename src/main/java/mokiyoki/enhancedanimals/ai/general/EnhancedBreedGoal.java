package mokiyoki.enhancedanimals.ai.general;

import mokiyoki.enhancedanimals.entity.EnhancedAnimalAbstract;
import net.minecraft.entity.ai.goal.BreedGoal;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.world.server.ServerWorld;

public class EnhancedBreedGoal extends BreedGoal {

    public EnhancedBreedGoal(AnimalEntity animal, double speedIn) {
        super(animal, speedIn);
    }

    public boolean shouldExecute() {
        if (this.animal.isBeingRidden() || ((EnhancedAnimalAbstract)this.animal).isAnimalSleeping()) {
            return false;
        }
        return super.shouldExecute();
    }

    /**
     * This is so the Growing Age is set lower than vanilla resets it to after Cancelling the event
     */
    protected void spawnBaby() {
        this.animal.func_234177_a_((ServerWorld)this.world, this.targetMate);
        this.animal.setGrowingAge(10);
        this.targetMate.setGrowingAge(10);
    }

}
