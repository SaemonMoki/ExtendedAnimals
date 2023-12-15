package mokiyoki.enhancedanimals.ai.general;

import mokiyoki.enhancedanimals.entity.EnhancedAnimalAbstract;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.FleeSunGoal;
import net.minecraft.world.entity.decoration.LeashFenceKnotEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;

import javax.annotation.Nullable;
import java.util.Random;

public class SeekShelterGoal extends FleeSunGoal {
    protected final EnhancedAnimalAbstract enhancedAnimal;
    private final Level world;
    private final int start;
    private final int end;
    private boolean isHungry = false;
    private boolean isBeingRainedOn = false;
    private boolean isHot = false;
    private boolean isLeashedToEntity = false;

    public SeekShelterGoal(EnhancedAnimalAbstract enhancedAnimal, double movementSpeedIn, int start, int end, int modifier) {
        super(enhancedAnimal, movementSpeedIn);
        this.world = enhancedAnimal.level;
        this.enhancedAnimal = enhancedAnimal;
        this.start = start + modifier;
        this.end = end + modifier;
    }

    @Override
    public boolean canUse() {
        this.getData(this.enhancedAnimal);
        if ((this.isBeingRainedOn || this.isHot) && !this.isLeashedToEntity && this.enhancedAnimal.getAIStatus() != AIStatus.FOCUSED) {
            if (this.enhancedAnimal.getTarget() != null) {
                return false;
            } else if (this.isHungry) {
                return false;
            } else if (this.enhancedAnimal.isOnFire()) {
                return false;
            } else if (!this.world.canSeeSky(this.enhancedAnimal.blockPosition())) {
                return false;
            } else {
                return this.setWantedPos();
            }
        } else {
            return false;
        }
    }

    private void getData(PathfinderMob animal) {
        Biome biome = this.world.getBiome(animal.blockPosition()).value();
        this.isLeashedToEntity = !(animal.getLeashHolder() instanceof LeashFenceKnotEntity) && animal.getLeashHolder() != null;
        if (!this.isLeashedToEntity) {
            this.isBeingRainedOn = animal.isInWaterOrRain() && !animal.isInWater();
            if (this.world.isDay() && !this.isBeingRainedOn) {
                this.isHungry = ((EnhancedAnimalAbstract) animal).getHunger() > 6000;
                // TODO 18: Biome.getTemperature() is now private and deprecated
                float temperature = 0.3f;
                //float temperature = this.world.getBiome(animal.blockPosition()).getTemperature(animal.blockPosition());
                this.isHot = temperature > 0.4F && this.world.getDayTime() >= this.start - (1500 * (temperature - 0.7F)) && this.world.getDayTime() <= this.end + (1500 * (temperature - 0.8F));
            } else {
                this.isHungry = ((EnhancedAnimalAbstract) animal).getHunger() > 12000;
            }
        }
    }

    @Override
    @Nullable
    protected Vec3 getHidePos() {
        Random random = this.enhancedAnimal.getRandom();
        BlockPos blockpos = this.enhancedAnimal.blockPosition();

        for(int i = 0; i < 10; ++i) {
            BlockPos blockpos1 = blockpos.offset(random.nextInt(20) - 10, random.nextInt(6) - 3, random.nextInt(20) - 10);
            if (!this.world.canSeeSkyFromBelowWater(blockpos1) && (this.isHot || !this.world.isWaterAt(blockpos)) && this.enhancedAnimal.getWalkTargetValue(blockpos1) < 0.0F) {
                return Vec3.atBottomCenterOf(blockpos1);
            }
        }

        return null;
    }
}
