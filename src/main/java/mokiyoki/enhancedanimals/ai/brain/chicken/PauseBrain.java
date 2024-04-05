package mokiyoki.enhancedanimals.ai.brain.chicken;

import com.google.common.collect.ImmutableMap;
import mokiyoki.enhancedanimals.entity.EnhancedChicken;
import mokiyoki.enhancedanimals.init.ModMemoryModuleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;

public class PauseBrain extends Behavior<EnhancedChicken> {
    public PauseBrain() {
        super(ImmutableMap.of(
                ModMemoryModuleTypes.PAUSE_BRAIN.get(), MemoryStatus.VALUE_PRESENT
            )
        );
    }

    protected boolean canStillUse(ServerLevel serverLevel, EnhancedChicken eanimal, long p_149324_) {
        return eanimal.getBrain().hasMemoryValue(ModMemoryModuleTypes.PAUSE_BRAIN.get());
    }

    protected void start(ServerLevel serverLevel, EnhancedChicken eanimal, long p_149332_) {
        Brain<EnhancedChicken> brain = eanimal.getBrain();
        brain.eraseMemory(MemoryModuleType.WALK_TARGET);
        brain.eraseMemory(MemoryModuleType.LOOK_TARGET);
    }
}
