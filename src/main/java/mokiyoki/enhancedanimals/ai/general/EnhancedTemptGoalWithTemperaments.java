package mokiyoki.enhancedanimals.ai.general;

import mokiyoki.enhancedanimals.entity.Temperament;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.Map;

public class EnhancedTemptGoalWithTemperaments extends TemptGoal {
    PathfinderMob entity;
    Map<Temperament, Integer> temperaments;

    public EnhancedTemptGoalWithTemperaments(PathfinderMob entity, double speedIn, Ingredient temptingItem, boolean scaredByPlayerMovement, Map<Temperament, Integer> temperaments) {
        super(entity, speedIn, temptingItem, scaredByPlayerMovement);
        this.entity = entity;
        this.temperaments = temperaments;
    }


    @Override
    public boolean canUse() {
        boolean superShould = super.canUse();
        if ((temperaments.containsKey(Temperament.AFRAID) && temperaments.get(Temperament.AFRAID) > 8) || !superShould) {
            return false;
        }
        return superShould;
    }

    public boolean canContinueToUse() {
        boolean shouldContinue = super.canContinueToUse();
        if (temperaments.containsKey(Temperament.AGGRESSIVE) && temperaments.get(Temperament.AGGRESSIVE) > 3 && tooCloseToPlayer()) {
            this.entity.setTarget(this.player);
            return false;
        }
        return shouldContinue;
    }

    private boolean tooCloseToPlayer() {
        double distanceToPlayer = this.player.distanceToSqr(this.entity);

        if (temperaments.get(Temperament.AGGRESSIVE) > 8 && distanceToPlayer < 8) {
            return true;
        } else if (temperaments.get(Temperament.AGGRESSIVE) > 6 && distanceToPlayer < 5) {
            return true;
        } else if (temperaments.get(Temperament.AGGRESSIVE) > 4 && distanceToPlayer < 3) {
            return true;
        } else if (distanceToPlayer < 1) {
            return true;
        }

        return false;

    }


    protected boolean canScare() {
        return super.canScare();
    }

}
