package mokiyoki.enhancedanimals.ai.brain.axolotl;

import com.google.common.collect.ImmutableMap;
import mokiyoki.enhancedanimals.entity.EnhancedAxolotl;
import mokiyoki.enhancedanimals.init.ModMemoryModuleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;

public class PauseBrain extends Behavior<EnhancedAxolotl> {
    public PauseBrain() {
        super(ImmutableMap.of(ModMemoryModuleTypes.HAS_EGG.get(), MemoryStatus.VALUE_PRESENT), 200);
    }

    protected boolean checkExtraStartConditions(ServerLevel p_149319_, EnhancedAxolotl p_149320_) {
        return p_149320_.isInWaterOrBubble();
    }

    protected boolean canStillUse(ServerLevel serverLevel, EnhancedAxolotl p_149323_, long p_149324_) {
        return p_149323_.isInWaterOrBubble() && p_149323_.getBrain().hasMemoryValue(ModMemoryModuleTypes.HAS_EGG.get());
    }

    protected void start(ServerLevel serverLevel, EnhancedAxolotl p_149331_, long p_149332_) {
        Brain<EnhancedAxolotl> brain = p_149331_.getBrain();
        brain.eraseMemory(MemoryModuleType.WALK_TARGET);
        brain.eraseMemory(MemoryModuleType.LOOK_TARGET);
    }
}
