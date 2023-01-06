package mokiyoki.enhancedanimals.ai.general;

import mokiyoki.enhancedanimals.ai.pathfinding.EnhancedGroundPathNavigator;
import mokiyoki.enhancedanimals.entity.EnhancedAnimalAbstract;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;

public class StayShelteredGoal extends Goal {
    protected final PathfinderMob creature;
    private final Level world;
    private final int start;
    private final int end;

    private boolean isHungry = false;
    private boolean isRaining = false;
    private boolean isHot = false;

    public StayShelteredGoal(PathfinderMob creature, int siestaStart, int siestaEnd, int napMod) {
        this.creature = creature;
        this.world = creature.level;
        this.start = siestaStart + napMod;
        this.end = siestaEnd + napMod;
    }

    @Override
    public boolean canUse() {
        this.getData(this.creature);
        return !this.isHungry && (this.isRaining || this.isHot) && this.creature.getNavigation() instanceof EnhancedGroundPathNavigator;
    }

    @Override
    public void start() {
        ((EnhancedGroundPathNavigator)this.creature.getNavigation()).setSeekShelter(true, this.isHot);
    }

    @Override
    public void stop() {
        if (this.creature.getNavigation() instanceof EnhancedGroundPathNavigator && !this.isHungry) {
            ((EnhancedGroundPathNavigator)this.creature.getNavigation()).setSeekShelter(true, this.isHot);
        }
    }

    private void getData(PathfinderMob animal) {
        Biome biome = this.world.getBiome(animal.blockPosition()).value();
        this.isRaining = this.world.isRaining() && biome.getPrecipitation() == Biome.Precipitation.RAIN && biome.warmEnoughToRain(animal.blockPosition());
        if (this.world.isDay()) {
            this.isHungry = ((EnhancedAnimalAbstract)animal).getHunger() > 6000;
            // TODO 18: Biome.getTemperature() is now private and deprecated
            float temperature = 0.3f;
            //float temperature = this.world.getBiome(animal.blockPosition()).getTemperature(animal.blockPosition());
            this.isHot = temperature > 0.4F && this.world.getDayTime() >= this.start - (1500 * (temperature - 0.7F)) && this.world.getDayTime() <= this.end + (1500 * (temperature - 0.8F));
        } else {
            this.isHungry = ((EnhancedAnimalAbstract)animal).getHunger() > 12000;
        }
    }
}
