package mokiyoki.enhancedanimals.util.scheduling;

import mokiyoki.enhancedanimals.entity.EnhancedAnimalAbstract;

import java.util.function.Consumer;
import java.util.function.Predicate;

public class AnimalScheduledFunction {

    int initialTicks = 0;
    int ticksToWait;

    Consumer<EnhancedAnimalAbstract> functionToRun;
    Predicate<EnhancedAnimalAbstract> repeatCondition;

    public AnimalScheduledFunction(int ticksToWait, Consumer<EnhancedAnimalAbstract> functionToRun) {
        this.ticksToWait = ticksToWait;
        this.functionToRun = functionToRun;
    }

    public AnimalScheduledFunction(int ticksToWait, Consumer<EnhancedAnimalAbstract> functionToRun, Predicate<EnhancedAnimalAbstract> repeatCondition) {
        this.initialTicks = ticksToWait;
        this.ticksToWait = ticksToWait;
        this.functionToRun = functionToRun;
        this.repeatCondition = repeatCondition;
    }

    public void tick() {
        ticksToWait--;
    }

    public void runFunction(EnhancedAnimalAbstract eaa) {
        functionToRun.accept(eaa);
    }

    public void runRepeatCondition(EnhancedAnimalAbstract eaa) {
        if (repeatCondition != null) {
            if(repeatCondition.test(eaa)) {
                this.ticksToWait = initialTicks;
            };
        }
    }

    public int[] getTickState() {
        if (this.initialTicks != 0) {
            return new int[]{this.ticksToWait, this.initialTicks};
        }
        return new int[]{this.ticksToWait};
    }

    public int getTicksToWait() {
        return this.ticksToWait;
    }

    public void setInitialTicks(int initialTicks) {
        this.initialTicks = initialTicks;
    }
}
