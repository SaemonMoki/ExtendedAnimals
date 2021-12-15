package mokiyoki.enhancedanimals.ai.general.chicken;

import mokiyoki.enhancedanimals.ai.general.GrazingGoal;
import mokiyoki.enhancedanimals.entity.EnhancedAnimalAbstract;

public class GrazingGoalChicken extends GrazingGoal {

    public GrazingGoalChicken(EnhancedAnimalAbstract eanimal, double movementSpeed) {
        super(eanimal, movementSpeed);
    }

    @Override
    protected void eatBlocks() {
        int root = this.eanimal.level.random.nextInt(1);
        if (root == 1) {
            return;
        } else {
            super.eatBlocks();
        }

    }
}
