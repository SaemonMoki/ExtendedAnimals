package mokiyoki.enhancedanimals.ai.brain.chicken;

import mokiyoki.enhancedanimals.entity.EnhancedChicken;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.ai.behavior.EntityTracker;
import net.minecraft.world.entity.ai.behavior.SetEntityLookTargetSometimes;
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.NearestVisibleLivingEntities;

import java.util.Optional;
import java.util.function.Predicate;

import static mokiyoki.enhancedanimals.ai.brain.chicken.ChickenBrain.canMoveOrLookAround;

public class ChickenSetEntityLookTargetSometimes {

    public ChickenSetEntityLookTargetSometimes() {
        super();
    }

    public static BehaviorControl<LivingEntity> create(float p_259047_, UniformInt p_260065_) {
      return create(p_259047_, p_260065_, (p_259715_) -> {
         return true;
      });
    }

    public static BehaviorControl<LivingEntity> create(EntityType<?> entityTarget, float range, UniformInt interval) {
      return create(range, interval, (p_289379_) -> {
         return entityTarget.equals(p_289379_.getType());
      });
    }

    private static BehaviorControl<LivingEntity> create(float range, UniformInt interval, Predicate<LivingEntity> livingEntityPredicate) {
        float f = range * range;
        ChickenSetEntityLookTargetSometimes.Ticker setentitylooktargetsometimes$ticker = new ChickenSetEntityLookTargetSometimes.Ticker(interval);
        return BehaviorBuilder.create((entityInstance) -> {
            return entityInstance.group(entityInstance.absent(MemoryModuleType.LOOK_TARGET), entityInstance.present(MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES)).apply(entityInstance, (memoryAccessorPosition, memoryAccessorNearestEntities) -> {
                return (serverLevel, chicken, p_264954_) -> {
                    if (!checkExtraStartConditions(serverLevel, chicken)) {
                        return false;
                    }

                   Optional<LivingEntity> optional = entityInstance.<NearestVisibleLivingEntities>get(memoryAccessorNearestEntities).findClosest(livingEntityPredicate.and((targetEntity) -> {
                      return targetEntity.distanceToSqr(chicken) <= (double)f;
                   }));
                   if (optional.isEmpty()) {
                      return false;
                   } else if (!setentitylooktargetsometimes$ticker.tickDownAndCheck(serverLevel.random)) {
                      return false;
                   } else {
                       memoryAccessorPosition.set(new EntityTracker(optional.get(), true));
                      return true;
                   }
                };
            });
      });
   }

   public static final class Ticker {
      private final UniformInt interval;
      private int ticksUntilNextStart;

      public Ticker(UniformInt uniformInt) {
         if (uniformInt.getMinValue() <= 1) {
            throw new IllegalArgumentException();
         } else {
            this.interval = uniformInt;
         }
      }

      public boolean tickDownAndCheck(RandomSource randomSource) {
         if (this.ticksUntilNextStart == 0) {
            this.ticksUntilNextStart = this.interval.sample(randomSource) - 1;
            return false;
         } else {
            return --this.ticksUntilNextStart == 0;
         }
      }
   }

    protected static boolean checkExtraStartConditions(ServerLevel serverLevel, LivingEntity livingEntity) {
        if (livingEntity instanceof EnhancedChicken chicken) {
            return canMoveOrLookAround(chicken);
        }
        return false;
    }
}
