package mokiyoki.enhancedanimals.ai.brain.chicken;

import com.google.common.collect.ImmutableMap;
import mokiyoki.enhancedanimals.entity.EnhancedChicken;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;

public class CalmDown extends Behavior<EnhancedChicken> {
   private static final int SAFE_DISTANCE_FROM_DANGER = 36;

   public CalmDown() {
      super(ImmutableMap.of());
   }

   protected void start(ServerLevel server, EnhancedChicken chicken, long p_24576_) {
      boolean flag = ChickenPanicTrigger.isHurt(chicken) || ChickenPanicTrigger.hasHostile(chicken) || isCloseToEntityThatHurtMe(chicken);
      if (!flag) {
         chicken.getBrain().eraseMemory(MemoryModuleType.HURT_BY);
         chicken.getBrain().eraseMemory(MemoryModuleType.HURT_BY_ENTITY);
         chicken.getBrain().updateActivityFromSchedule(server.getDayTime(), server.getGameTime());
      }

   }

   private static boolean isCloseToEntityThatHurtMe(EnhancedChicken chicken) {
      return chicken.getBrain().getMemory(MemoryModuleType.HURT_BY_ENTITY).filter((p_24581_) -> {
         return p_24581_.distanceToSqr(chicken) <= 36.0D;
      }).isPresent();
   }
}