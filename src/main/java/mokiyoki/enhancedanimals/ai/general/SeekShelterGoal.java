package mokiyoki.enhancedanimals.ai.general;

import mokiyoki.enhancedanimals.entity.EnhancedAnimalAbstract;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.ai.goal.FleeSunGoal;
import net.minecraft.entity.item.LeashKnotEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

import javax.annotation.Nullable;
import java.util.Random;

public class SeekShelterGoal extends FleeSunGoal {
    protected final CreatureEntity creature;
    private final World world;
    private final int start;
    private final int end;
    private boolean isHungry = false;
    private boolean isBeingRainedOn = false;
    private boolean isHot = false;
    private boolean isLeashedToEntity = false;

    public SeekShelterGoal(CreatureEntity theCreatureIn, double movementSpeedIn, int start, int end, int modifier) {
        super(theCreatureIn, movementSpeedIn);
        this.world = theCreatureIn.world;
        this.creature = theCreatureIn;
        this.start = start + modifier;
        this.end = end + modifier;
    }

    @Override
    public boolean shouldExecute() {
        this.getData(this.creature);
        if ((this.isBeingRainedOn || this.isHot) && !this.isLeashedToEntity) {
            if (this.creature.getAttackTarget() != null) {
                return false;
            } else if (this.isHungry) {
                return false;
            } else if (this.creature.isBurning()) {
                return false;
            } else if (!this.world.canSeeSky(this.creature.getPosition())) {
                return false;
            } else {
                return this.isPossibleShelter();
            }
        } else {
            return false;
        }
    }

    private void getData(CreatureEntity animal) {
        Biome biome = this.world.getBiome(animal.getPosition());
        this.isLeashedToEntity = !(animal.getLeashHolder() instanceof LeashKnotEntity) && animal.getLeashHolder() != null;
        if (!this.isLeashedToEntity) {
            this.isBeingRainedOn = this.world.isRaining() && biome.getPrecipitation() == Biome.RainType.RAIN && biome.getTemperature(animal.getPosition()) >= 0.15F;
            if (this.world.isDaytime() && !this.isBeingRainedOn) {
                this.isHungry = ((EnhancedAnimalAbstract) animal).getHunger() > 6000;
                float temperature = this.world.getBiome(animal.getPosition()).getTemperature(animal.getPosition());
                this.isHot = temperature > 0.4F && this.world.getDayTime() >= this.start - (1500 * (temperature - 0.7F)) && this.world.getDayTime() <= this.end + (1500 * (temperature - 0.8F));
            } else {
                this.isHungry = ((EnhancedAnimalAbstract) animal).getHunger() > 12000;
            }
        }
    }

    @Override
    @Nullable
    protected Vector3d findPossibleShelter() {
        Random random = this.creature.getRNG();
        BlockPos blockpos = this.creature.getPosition();

        for(int i = 0; i < 10; ++i) {
            BlockPos blockpos1 = blockpos.add(random.nextInt(20) - 10, random.nextInt(6) - 3, random.nextInt(20) - 10);
            if (!this.world.canBlockSeeSky(blockpos1) && (this.isHot || !this.world.hasWater(blockpos)) && this.creature.getBlockPathWeight(blockpos1) < 0.0F) {
                return Vector3d.copyCenteredHorizontally(blockpos1);
            }
        }

        return null;
    }
}
