package mokiyoki.enhancedanimals.ai.brain;

import com.google.common.collect.ImmutableMap;
import mokiyoki.enhancedanimals.entity.EnhancedChicken;
import mokiyoki.enhancedanimals.init.ModMemoryModuleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;

import java.util.Optional;
import java.util.function.Function;

import static mokiyoki.enhancedanimals.util.scheduling.Schedules.DISMOUNT_SCHEDULE;
import static mokiyoki.enhancedanimals.util.scheduling.Schedules.RIDE_MOTHER_HEN_SCHEDULE;

public class BabyFollowParent<E extends AgeableMob> extends Behavior<E> {
   private final UniformInt followRange;
   private final Function<LivingEntity, Float> speedModifier;

   public BabyFollowParent(UniformInt followRange, float speedModifier) {
      this(followRange, (p_147421_) -> {
         return speedModifier;
      });
   }

   public BabyFollowParent(UniformInt followRange, Function<LivingEntity, Float> speedModifier) {
      super(ImmutableMap.of(ModMemoryModuleTypes.MOTHER_UUID.get(), MemoryStatus.VALUE_PRESENT, MemoryModuleType.WALK_TARGET, MemoryStatus.VALUE_ABSENT));
      this.followRange = followRange;
      this.speedModifier = speedModifier;
   }

   protected boolean checkExtraStartConditions(ServerLevel server, E eanimal) {
      if (!eanimal.isBaby()) {
         return false;
      } else {
         Optional<LivingEntity> optionalParent = eanimal.getBrain().getMemory(ModMemoryModuleTypes.MOTHER.get());
         if (optionalParent.isPresent()) {
            return eanimal.closerThan(optionalParent.get(), (double)(this.followRange.getMaxValue() + 1)) && !eanimal.closerThan(optionalParent.get(), (double)this.followRange.getMinValue());
         }

         optionalParent = BehaviorUtils.getLivingEntityFromUUIDMemory(eanimal, ModMemoryModuleTypes.MOTHER_UUID.get());
         if (optionalParent.isPresent()) {
            eanimal.getBrain().setMemory(ModMemoryModuleTypes.MOTHER.get(), optionalParent.get());
            return eanimal.closerThan(optionalParent.get(), (double)(this.followRange.getMaxValue() + 1)) && !eanimal.closerThan(optionalParent.get(), (double)this.followRange.getMinValue());
         }

         return false;
      }
   }

   protected void start(ServerLevel serverLevel, E child, long p_147428_) {
      Optional<LivingEntity> optionalParent = child.getBrain().getMemory(ModMemoryModuleTypes.MOTHER.get());
         if (optionalParent.isPresent()) {
            runFollowParent(serverLevel, child, optionalParent.get(), p_147428_);
         } else {
            optionalParent = BehaviorUtils.getLivingEntityFromUUIDMemory(child, ModMemoryModuleTypes.MOTHER_UUID.get());
            if (optionalParent.isPresent()) {
               child.getBrain().setMemory(ModMemoryModuleTypes.MOTHER.get(), optionalParent.get());
               runFollowParent(serverLevel, child, optionalParent.get(), p_147428_);
            }
         }
   }

   protected void runFollowParent(ServerLevel serverLevel, E child, LivingEntity parent, long p_147429_) {
      BehaviorUtils.setWalkAndLookTargetMemories(child, parent, this.speedModifier.apply(child), this.followRange.getMinValue() - 1);

      if (child instanceof EnhancedChicken enhancedChicken) {
         if (enhancedChicken.growthAmount()<0.25F && enhancedChicken.getRandom().nextInt(0, 100) > 95 && !enhancedChicken.isAnimalSleeping() && parent.getPassengers().isEmpty() && !enhancedChicken.scheduledToRun.containsKey(RIDE_MOTHER_HEN_SCHEDULE.funcName)) {
                int mountInTicks = enhancedChicken.getRandom().nextInt(100, 1000);
                int dismountInTicks = enhancedChicken.getRandom().nextInt(mountInTicks+100, mountInTicks+1000);
                enhancedChicken.scheduledToRun.put(RIDE_MOTHER_HEN_SCHEDULE.funcName, RIDE_MOTHER_HEN_SCHEDULE.function.apply(mountInTicks));
                enhancedChicken.scheduledToRun.put(DISMOUNT_SCHEDULE.funcName, DISMOUNT_SCHEDULE.function.apply(dismountInTicks));
            } else {
                enhancedChicken.getNavigation().moveTo(parent, this.speedModifier.apply(child));
            }
      }
   }

   private AgeableMob getNearestAdult(E p_147430_) {
      return p_147430_.getBrain().getMemory(MemoryModuleType.NEAREST_VISIBLE_ADULT).get();
   }
}