package mokiyoki.enhancedanimals.util;

import mokiyoki.enhancedanimals.entity.EnhancedAnimalAbstract;

import java.util.function.Consumer;

public class AnimalScheduledFunction {

    int ticksToWait;

    Consumer<EnhancedAnimalAbstract> functionToRun;

    public AnimalScheduledFunction(int ticksToWait, Consumer<EnhancedAnimalAbstract> functionToRun) {
        this.ticksToWait = ticksToWait;
        this.functionToRun = functionToRun;
    }

    public void tick() {
        ticksToWait--;
    }

    public void runFunction(EnhancedAnimalAbstract eaa) {
        functionToRun.accept(eaa);
    }

    public int getTicksToWait() {
        return this.ticksToWait;
    }
}
