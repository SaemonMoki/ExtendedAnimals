package mokiyoki.enhancedanimals.ai.brain.axolotl;

import com.google.common.collect.ImmutableMap;
import mokiyoki.enhancedanimals.entity.EnhancedAxolotl;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;

public class ValidatePlayDead extends Behavior<EnhancedAxolotl> {
    public ValidatePlayDead() {
        super(ImmutableMap.of(MemoryModuleType.PLAY_DEAD_TICKS, MemoryStatus.VALUE_PRESENT));
    }

    protected void start(ServerLevel p_149339_, EnhancedAxolotl p_149340_, long p_149341_) {
        Brain<EnhancedAxolotl> brain = p_149340_.getBrain();
        int i = brain.getMemory(MemoryModuleType.PLAY_DEAD_TICKS).get();
        if (i <= 0) {
            brain.eraseMemory(MemoryModuleType.PLAY_DEAD_TICKS);
            brain.eraseMemory(MemoryModuleType.HURT_BY_ENTITY);
            brain.useDefaultActivity();
        } else {
            brain.setMemory(MemoryModuleType.PLAY_DEAD_TICKS, i - 1);
        }

    }
}
