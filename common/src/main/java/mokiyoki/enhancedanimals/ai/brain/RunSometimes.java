package mokiyoki.enhancedanimals.ai.brain;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;

public class RunSometimes<E extends LivingEntity> implements BehaviorControl<E> {
    private boolean resetTicks;
    private boolean wasRunning;
    private final UniformInt interval;
    private final BehaviorControl<? super E> wrappedBehavior;
    private int ticksUntilNextStart;
    private Behavior.Status status = Behavior.Status.STOPPED;

    public RunSometimes(BehaviorControl<? super E> wrappedBehavior, UniformInt interval) {
        this(wrappedBehavior, false, interval);
    }

    public RunSometimes(BehaviorControl<? super E> wrappedBehavior, boolean resetTicks, UniformInt interval) {
        this.wrappedBehavior = wrappedBehavior;
        this.resetTicks = !resetTicks;
        this.interval = interval;
    }

    @Override
    public Behavior.Status getStatus() {
        return this.status;
    }

    @Override
    public boolean tryStart(ServerLevel level, E entity, long gameTime) {
        if (this.resetTicks) {
            this.resetTicksUntilNextStart(level);
            this.resetTicks = false;
        }

        if (this.ticksUntilNextStart > 0) {
            --this.ticksUntilNextStart;
        }

        if (!this.wasRunning && this.ticksUntilNextStart == 0 && this.wrappedBehavior.tryStart(level, entity, gameTime)) {
            this.status = Behavior.Status.RUNNING;
            return true;
        }
        return false;
    }

    @Override
    public void tickOrStop(ServerLevel level, E entity, long gameTime) {
        this.wrappedBehavior.tickOrStop(level, entity, gameTime);
        this.wasRunning = this.wrappedBehavior.getStatus() == Behavior.Status.RUNNING;
        if (!this.wasRunning) {
            this.doStop(level, entity, gameTime);
        }
    }

    @Override
    public void doStop(ServerLevel level, E entity, long gameTime) {
        this.status = Behavior.Status.STOPPED;
        this.resetTicksUntilNextStart(level);
        if (this.wrappedBehavior.getStatus() == Behavior.Status.RUNNING) {
            this.wrappedBehavior.doStop(level, entity, gameTime);
        }
    }

    private void resetTicksUntilNextStart(ServerLevel level) {
        this.ticksUntilNextStart = this.interval.sample(level.random);
    }

    @Override
    public String debugString() {
        return "RunSometimes: " + this.wrappedBehavior.debugString();
    }
}
