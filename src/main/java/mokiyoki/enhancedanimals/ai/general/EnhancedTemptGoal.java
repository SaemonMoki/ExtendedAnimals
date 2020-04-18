package mokiyoki.enhancedanimals.ai.general;

import mokiyoki.enhancedanimals.entity.EnhancedAnimal;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.ai.goal.TemptGoal;
import net.minecraft.item.crafting.Ingredient;

public class EnhancedTemptGoal extends TemptGoal {

    protected final CreatureEntity temptedCreature;

    public EnhancedTemptGoal(CreatureEntity creatureIn, double speedIn, boolean scaredOfPlayerMovement, Ingredient temptItemsIn) {
        super(creatureIn, speedIn, scaredOfPlayerMovement, temptItemsIn);
        this.temptedCreature = creatureIn;
    }

    public boolean shouldExecute() {
        boolean superShould = super.shouldExecute();

        if (!superShould || isSleeping()) {
            return false;
        }

        return true;

    }

    private boolean isSleeping() {
        return ((EnhancedAnimal)temptedCreature).isAnimalSleeping();
    }

}
