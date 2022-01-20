package mokiyoki.enhancedanimals.ai.rabbit;

import mokiyoki.enhancedanimals.ai.general.EnhancedPanicGoal;
import mokiyoki.enhancedanimals.entity.EnhancedRabbit;

public class EnhancedRabbitPanicGoal extends EnhancedPanicGoal {

    private final EnhancedRabbit rabbit;

    public EnhancedRabbitPanicGoal(EnhancedRabbit rabbit, double speedIn) {
        super(rabbit, speedIn);
        this.rabbit = rabbit;
    }

    /**
     * Keep ticking a continuous task that has already been started
     */
    public void tick() {
        super.tick();
        this.rabbit.setMovementSpeed(this.speedModifier);
    }

}
