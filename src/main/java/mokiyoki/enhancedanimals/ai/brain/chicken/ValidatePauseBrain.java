package mokiyoki.enhancedanimals.ai.brain.chicken;

import com.google.common.collect.ImmutableMap;
import mokiyoki.enhancedanimals.entity.EnhancedChicken;
import mokiyoki.enhancedanimals.init.ModMemoryModuleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryStatus;

public class ValidatePauseBrain extends Behavior<EnhancedChicken> {
    public ValidatePauseBrain() {
        super(ImmutableMap.of(
                ModMemoryModuleTypes.PAUSE_BRAIN.get(), MemoryStatus.VALUE_PRESENT
            )
        );
    }

    protected void start(ServerLevel serverLevel, EnhancedChicken eanimal, long p_149341_) {
        Brain<EnhancedChicken> brain = eanimal.getBrain();
        if (!eanimal.isBrooding()) {
            brain.eraseMemory(ModMemoryModuleTypes.BROODING.get());
        }

        if (!eanimal.isAnimalSleeping()) {
            brain.eraseMemory(ModMemoryModuleTypes.SLEEPING.get());

        }

        if (!eanimal.isBrooding() && !eanimal.isAnimalSleeping()) {
            brain.eraseMemory(ModMemoryModuleTypes.PAUSE_BRAIN.get());
            brain.useDefaultActivity();
        }

    }
}
