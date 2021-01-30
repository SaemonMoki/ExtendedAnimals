package mokiyoki.enhancedanimals.ai.general;

import mokiyoki.enhancedanimals.ai.pathfinding.EnhancedGroundPathNavigator;
import mokiyoki.enhancedanimals.entity.EnhancedAnimalAbstract;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

public class StayShelteredGoal extends Goal {
    protected final CreatureEntity creature;
    private final World world;
    private final int start;
    private final int end;

    private boolean isHungry = false;
    private boolean isRaining = false;
    private boolean isHot = false;

    public StayShelteredGoal(CreatureEntity creature, int siestaStart, int siestaEnd, int napMod) {
        this.creature = creature;
        this.world = creature.world;
        this.start = siestaStart + napMod;
        this.end = siestaEnd + napMod;
    }

    @Override
    public boolean shouldExecute() {
        this.getData(this.creature);
        return !this.isHungry && (this.isRaining || this.isHot) && this.creature.getNavigator() instanceof EnhancedGroundPathNavigator;
    }

    @Override
    public void startExecuting() {
        ((EnhancedGroundPathNavigator)this.creature.getNavigator()).setSeekShelter(true);
    }

    @Override
    public void resetTask() {
        if (this.creature.getNavigator() instanceof EnhancedGroundPathNavigator) {
            ((EnhancedGroundPathNavigator)this.creature.getNavigator()).setSeekShelter(true);
        }
    }

    private void getData(CreatureEntity animal) {
        Biome biome = this.world.getBiome(animal.getPosition());
        this.isRaining = this.world.isRaining() && biome.getPrecipitation() == Biome.RainType.RAIN && biome.getTemperature(animal.getPosition()) >= 0.15F;
        if (this.world.isDaytime()) {
            this.isHungry = ((EnhancedAnimalAbstract)animal).getHunger() > 4000;
        } else {
            this.isHungry = ((EnhancedAnimalAbstract)animal).getHunger() > 12000;
        }
        float temperature = this.world.getBiome(animal.getPosition()).getTemperature(animal.getPosition());
        this.isHot = temperature > 0.4F && this.world.getDayTime() >= this.start - (1500 * (temperature - 0.7F)) && this.world.getDayTime() <= this.end + (1500 * (temperature - 0.8F));
    }
}
