package mokiyoki.enhancedanimals.ai.brain.chicken;

import com.google.common.collect.ImmutableMap;
import mokiyoki.enhancedanimals.init.ModEntities;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.NearestVisibleLivingEntitySensor;

public class ChickenHostilesSensor extends NearestVisibleLivingEntitySensor {
   private static final ImmutableMap<EntityType<?>, Float> ACCEPTABLE_DISTANCE_FROM_HOSTILES = ImmutableMap.<EntityType<?>, Float>builder()
           .put(EntityType.WOLF, 10.0F)
           .put(EntityType.FOX, 10.0F)
           .put(ModEntities.ENHANCED_PIG.get(), 10.0F)
           .build();

   protected boolean isMatchingEntity(LivingEntity runningEntity, LivingEntity hostileEntity) {
      return this.isHostile(hostileEntity) && this.isClose(runningEntity, hostileEntity);
   }

   private boolean isClose(LivingEntity runningEntity, LivingEntity hostileEntity) {
      float f = ACCEPTABLE_DISTANCE_FROM_HOSTILES.get(hostileEntity.getType());
      return hostileEntity.distanceToSqr(runningEntity) <= (double)(f * f);
   }

   protected MemoryModuleType<LivingEntity> getMemory() {
      return MemoryModuleType.NEAREST_HOSTILE;
   }

   private boolean isHostile(LivingEntity hostileEntity) {
      return ACCEPTABLE_DISTANCE_FROM_HOSTILES.containsKey(hostileEntity.getType());
   }
}