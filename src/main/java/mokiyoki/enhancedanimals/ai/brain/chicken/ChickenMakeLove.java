package mokiyoki.enhancedanimals.ai.brain.chicken;

import mokiyoki.enhancedanimals.entity.EnhancedChicken;
import mokiyoki.enhancedanimals.init.ModEntities;
import mokiyoki.enhancedanimals.init.ModMemoryModuleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.behavior.AnimalMakeLove;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.animal.Animal;

public class ChickenMakeLove extends AnimalMakeLove {

    EnhancedChicken breedTarget;

    public ChickenMakeLove(float speed) {
        super(ModEntities.ENHANCED_CHICKEN.get(), speed);
    }

    @Override
    protected void start(ServerLevel serverLevel, Animal animal, long in) {
        super.start(serverLevel, animal, in);
        if (animal.getBrain().getMemory(MemoryModuleType.BREED_TARGET).get() instanceof EnhancedChicken enhancedChicken) {
            this.breedTarget = enhancedChicken;
        }
    }

    @Override
    protected void stop(ServerLevel serverLevel, Animal animal, long in) {
        super.stop(serverLevel, animal, in);
        if (((EnhancedChicken)animal).getOrSetIsFemale()) {
            ((EnhancedChicken)animal).getBrain().setMemory(ModMemoryModuleTypes.SEEKING_NEST.get(), true);
        } else if (breedTarget != null && breedTarget.isAlive() && breedTarget.getOrSetIsFemale()) {
            breedTarget.getBrain().setMemory(ModMemoryModuleTypes.SEEKING_NEST.get(), true);
        }
    }
}
