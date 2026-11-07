package mokiyoki.enhancedanimals.ai.brain.axolotl;

import com.google.common.collect.ImmutableMap;
import mokiyoki.enhancedanimals.entity.EnhancedAxolotl;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;

public class PlayDead extends Behavior<EnhancedAxolotl> {
    public PlayDead() {
        super(ImmutableMap.of(MemoryModuleType.PLAY_DEAD_TICKS, MemoryStatus.VALUE_PRESENT, MemoryModuleType.HURT_BY_ENTITY, MemoryStatus.VALUE_PRESENT), 200);
    }

    protected boolean checkExtraStartConditions(ServerLevel serverLevel, EnhancedAxolotl axolotl) {
        return axolotl.isInWaterOrBubble();
    }

    protected boolean canStillUse(ServerLevel serverLevel, EnhancedAxolotl axolotl, long p_149324_) {
        return axolotl.isInWaterOrBubble() && axolotl.getBrain().hasMemoryValue(MemoryModuleType.PLAY_DEAD_TICKS);
    }

    protected void start(ServerLevel serverLevel, EnhancedAxolotl p_149331_, long p_149332_) {
        Brain<EnhancedAxolotl> brain = p_149331_.getBrain();
        brain.eraseMemory(MemoryModuleType.WALK_TARGET);
        brain.eraseMemory(MemoryModuleType.LOOK_TARGET);
        p_149331_.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 200, 0));
    }
}
