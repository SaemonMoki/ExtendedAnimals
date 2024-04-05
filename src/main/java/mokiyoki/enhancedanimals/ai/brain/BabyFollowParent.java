package mokiyoki.enhancedanimals.ai.brain;

import com.google.common.collect.ImmutableMap;
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

   protected void start(ServerLevel p_147426_, E p_147427_, long p_147428_) {
      BehaviorUtils.setWalkAndLookTargetMemories(p_147427_, this.getNearestAdult(p_147427_), this.speedModifier.apply(p_147427_), this.followRange.getMinValue() - 1);
   }

   private AgeableMob getNearestAdult(E p_147430_) {
      return p_147430_.getBrain().getMemory(MemoryModuleType.NEAREST_VISIBLE_ADULT).get();
   }
}