package mokiyoki.enhancedanimals.ai.brain;

import com.google.common.collect.ImmutableMap;
import mokiyoki.enhancedanimals.entity.EnhancedAnimalAbstract;
import mokiyoki.enhancedanimals.init.ModMemoryModuleTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.decoration.LeashFenceKnotEntity;

import java.util.Random;

public class SeekShelter extends Behavior<EnhancedAnimalAbstract> {

    private BlockPos hidePos;

    public SeekShelter() {
        super(ImmutableMap.of(
                ModMemoryModuleTypes.SEEKING_SHELTER.get(), MemoryStatus.VALUE_PRESENT,
                ModMemoryModuleTypes.PAUSE_BRAIN.get(), MemoryStatus.VALUE_ABSENT,
                ModMemoryModuleTypes.BROODING.get(), MemoryStatus.VALUE_ABSENT
            )
        );
    }

    protected boolean checkExtraStartConditions(ServerLevel serverLevel, EnhancedAnimalAbstract geneticAnimal) {
        if ((isBeingRainedOn(geneticAnimal) || isHot(serverLevel, geneticAnimal))) {
            if (geneticAnimal.getBrain().hasMemoryValue(MemoryModuleType.ATTACK_TARGET)) {
                return false;
            } else if (isHungry(serverLevel, geneticAnimal) || geneticAnimal.isOnFire() || !serverLevel.canSeeSky(geneticAnimal.blockPosition()) || isLeashedToEntity(geneticAnimal)) {
                return false;
            }

            return this.setWantedPos(serverLevel, geneticAnimal);
        } else {
            return false;
        }
    }

    protected void start(ServerLevel serverLevel, EnhancedAnimalAbstract geneticAnimal, long p_24696_) {
        if(hidePos!=null) {
            BehaviorUtils.setWalkAndLookTargetMemories(geneticAnimal, hidePos, 1F, 0);
        }

    }

    protected boolean canStillUse(ServerLevel serverLevel, EnhancedAnimalAbstract geneticAnimal, long p_22547_) {
        if (geneticAnimal.getBrain().getMemory(MemoryModuleType.WALK_TARGET).isEmpty() ||
                (!isHot(serverLevel, geneticAnimal) &&
                !serverLevel.isRaining())
        ) {
            return false;
        }
        return true;
    }

    private boolean isHot(ServerLevel serverLevel, EnhancedAnimalAbstract geneticAnimal) {
        // TODO 18: Biome.getTemperature() is now private and deprecated
        float temperature = 0.3f;
        int start = 5723; //Start and end of the hot period
        int end = 7000;

        //float temperature = this.world.getBiome(animal.blockPosition()).getTemperature(animal.blockPosition());
        return temperature > 0.4F && serverLevel.getDayTime() >= start - (1500 * (temperature - 0.7F)) && serverLevel.getDayTime() <= end + (1500 * (temperature - 0.8F));
    }

    private boolean isHungry(ServerLevel serverLevel, EnhancedAnimalAbstract geneticAnimal) {
        if (serverLevel.isDay() && !isBeingRainedOn(geneticAnimal)) {
            return geneticAnimal.getHunger() > 6000;
        } else {
            return geneticAnimal.getHunger() > 12000;
        }
    }

    private boolean isLeashedToEntity(EnhancedAnimalAbstract geneticAnimal) {
        return !(geneticAnimal.getLeashHolder() instanceof LeashFenceKnotEntity) && geneticAnimal.getLeashHolder() != null;
    }

    private boolean isBeingRainedOn(EnhancedAnimalAbstract geneticAnimal) {
        return geneticAnimal.isInWaterOrRain() && !geneticAnimal.isInWater();
    }

    protected boolean setWantedPos(ServerLevel serverLevel, EnhancedAnimalAbstract geneticAnimal) {
        BlockPos hidePos = this.getHidePos(serverLevel, geneticAnimal);
        if (hidePos == null) {
            return false;
        } else {
            this.hidePos = hidePos;
            return true;
        }
    }

    protected BlockPos getHidePos(ServerLevel serverLevel, EnhancedAnimalAbstract geneticAnimal) {
        Random random = geneticAnimal.getRandom();
        BlockPos blockpos = geneticAnimal.blockPosition();

        for(int i = 0; i < 10; ++i) {
            BlockPos blockpos1 = blockpos.offset(random.nextInt(20) - 10, random.nextInt(6) - 3, random.nextInt(20) - 10);
            if (!serverLevel.canSeeSkyFromBelowWater(blockpos1) && (isHot(serverLevel, geneticAnimal) || !serverLevel.isWaterAt(blockpos)) && geneticAnimal.getWalkTargetValue(blockpos1) < 0.0F) {
                return blockpos1;
            }
        }

        return null;
    }
}
