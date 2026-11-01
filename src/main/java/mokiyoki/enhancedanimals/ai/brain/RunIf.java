package mokiyoki.enhancedanimals.ai.brain;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;

import java.util.function.Predicate;

public class RunIf<E extends LivingEntity> implements BehaviorControl<E> {
    private final Predicate<E> predicate;
    private final BehaviorControl<? super E> wrappedBehavior;
    private final boolean checkWhileRunningAlso;
    private Behavior.Status status = Behavior.Status.STOPPED;

    public RunIf(Predicate<E> predicate, BehaviorControl<? super E> wrappedBehavior, boolean checkWhileRunningAlso) {
        this.predicate = predicate;
        this.wrappedBehavior = wrappedBehavior;
        this.checkWhileRunningAlso = checkWhileRunningAlso;
    }

    public RunIf(Predicate<E> predicate, BehaviorControl<? super E> wrappedBehavior) {
        this(predicate, wrappedBehavior, false);
    }

    @Override
    public Behavior.Status getStatus() {
        return this.status;
    }

    @Override
    public boolean tryStart(ServerLevel level, E entity, long gameTime) {
        if (this.predicate.test(entity) && this.wrappedBehavior.tryStart(level, entity, gameTime)) {
            this.status = Behavior.Status.RUNNING;
            return true;
        }
        return false;
    }

    @Override
    public void tickOrStop(ServerLevel level, E entity, long gameTime) {
        if (this.checkWhileRunningAlso && this.predicate.test(entity)) {
            this.wrappedBehavior.tickOrStop(level, entity, gameTime);
            if (this.wrappedBehavior.getStatus() == Behavior.Status.STOPPED) {
                this.status = Behavior.Status.STOPPED;
            }
        } else {
            this.doStop(level, entity, gameTime);
        }
    }

    @Override
    public void doStop(ServerLevel level, E entity, long gameTime) {
        this.status = Behavior.Status.STOPPED;
        if (this.wrappedBehavior.getStatus() == Behavior.Status.RUNNING) {
            this.wrappedBehavior.doStop(level, entity, gameTime);
        }
    }

    @Override
    public String debugString() {
        return "RunIf: " + this.wrappedBehavior.debugString();
    }
}
