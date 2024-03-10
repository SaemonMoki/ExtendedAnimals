package mokiyoki.enhancedanimals.ai.brain.chicken;

import com.google.common.collect.ImmutableMap;
import mokiyoki.enhancedanimals.entity.EnhancedChicken;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

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
      if (p_24702_ % 3L == 0L) {
         ItemStack featherStack = new ItemStack(Items.FEATHER, 1);

         ItemEntity itementity = new ItemEntity(chicken.level, chicken.getX(), chicken.getY() + 0.0d, chicken.getZ(), featherStack);
         itementity.setDefaultPickUpDelay();
         itementity.setNeverPickUp();
         itementity.lifespan = 20;

         chicken.level.addFreshEntity(itementity);
      }
   }

   public static boolean hasHostile(LivingEntity livingEntity) {
      return livingEntity.getBrain().hasMemoryValue(MemoryModuleType.NEAREST_HOSTILE);
   }

   public static boolean isHurt(LivingEntity livingEntity) {
      return livingEntity.getBrain().hasMemoryValue(MemoryModuleType.HURT_BY);
   }
}