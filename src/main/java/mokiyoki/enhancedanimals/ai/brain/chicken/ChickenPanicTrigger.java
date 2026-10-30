package mokiyoki.enhancedanimals.ai.brain.chicken;

import com.google.common.collect.ImmutableMap;
import mokiyoki.enhancedanimals.entity.EnhancedChicken;
import mokiyoki.enhancedanimals.init.ModParticles;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.schedule.Activity;

public class ChickenPanicTrigger extends Behavior<EnhancedChicken> {
   public ChickenPanicTrigger() {
      super(ImmutableMap.of());
   }

   protected boolean canStillUse(ServerLevel server, EnhancedChicken chicken, long p_24686_) {
      return isHurt(chicken);
   }

   protected void start(ServerLevel p_24694_, EnhancedChicken chicken, long p_24696_) {
      if (isHurt(chicken)) {
         Brain<?> brain = chicken.getBrain();
         if (!brain.isActive(Activity.PANIC)) {
            brain.eraseMemory(MemoryModuleType.PATH);
            brain.eraseMemory(MemoryModuleType.WALK_TARGET);
            brain.eraseMemory(MemoryModuleType.LOOK_TARGET);
            brain.eraseMemory(MemoryModuleType.BREED_TARGET);
            brain.eraseMemory(MemoryModuleType.INTERACTION_TARGET);
         }

         brain.setActiveActivityIfPossible(Activity.PANIC);
      }

   }

   protected void tick(ServerLevel server, EnhancedChicken chicken, long p_24702_) {
      int[] gene = chicken.getGenes().getAutosomalGenes();

      //random chance to space the tick and gene check to ensure not scaleless
      if (chicken.invulnerable <= 0 && p_24702_ % 3L == 0L && !(gene[108] == 2 && gene[109] == 2)) {
         server.sendParticles(ModParticles.FEATHER.get(),
                 chicken.getX(), chicken.getY(), chicken.getZ(), 0,
                 (chicken.getRandom().nextDouble() - 0.5D) * 0.2D, 0.2D, (chicken.getRandom().nextDouble() - 0.5D) * 0.2D, 1.0D);
      }
   }

   public static boolean hasHostile(LivingEntity livingEntity) {
      return livingEntity.getBrain().hasMemoryValue(MemoryModuleType.NEAREST_HOSTILE);
   }

   public static boolean isHurt(LivingEntity livingEntity) {
      return livingEntity.getBrain().hasMemoryValue(MemoryModuleType.HURT_BY);
   }
}