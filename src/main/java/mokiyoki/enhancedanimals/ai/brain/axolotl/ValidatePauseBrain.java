package mokiyoki.enhancedanimals.ai.brain.axolotl;

import com.google.common.collect.ImmutableMap;
import mokiyoki.enhancedanimals.entity.EnhancedAxolotl;
import mokiyoki.enhancedanimals.init.ModMemoryModuleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryStatus;

public class ValidatePauseBrain extends Behavior<EnhancedAxolotl> {
    public ValidatePauseBrain() {
        super(ImmutableMap.of(ModMemoryModuleTypes.HAS_EGG.get(), MemoryStatus.VALUE_PRESENT));
    }

    protected void start(ServerLevel serverLevel, EnhancedAxolotl axolotl, long p_149341_) {
        Brain<EnhancedAxolotl> brain = axolotl.getBrain();
        if (!axolotl.hasEgg()) {
            brain.eraseMemory(ModMemoryModuleTypes.HAS_EGG.get());
            brain.useDefaultActivity();
        }

    }
}
