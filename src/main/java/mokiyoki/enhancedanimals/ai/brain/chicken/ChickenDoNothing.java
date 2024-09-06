package mokiyoki.enhancedanimals.ai.brain.chicken;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;

public class ChickenDoNothing implements BehaviorControl<LivingEntity> {
    private final int minDuration;
    private final int maxDuration;
    private Behavior.Status status;
    private long endTimestamp;


    public ChickenDoNothing(int minDuration, int maxDuration) {
        this.status = Behavior.Status.STOPPED;
        this.minDuration = minDuration;
        this.maxDuration = maxDuration;
    }

    public Behavior.Status getStatus() {
        return this.status;
    }

    public final boolean tryStart(ServerLevel serverLevel, LivingEntity livingEntity, long timestamp) {
        if (livingEntity.onGround()) {
            this.status = Behavior.Status.RUNNING;
            int $$3 = this.minDuration + serverLevel.getRandom().nextInt(this.maxDuration + 1 - this.minDuration);
            this.endTimestamp = timestamp + (long)$$3;
            return true;
        }
        return false;
    }

    public final void tickOrStop(ServerLevel serverLevel, LivingEntity livingEntity, long timestamp) {
        if (timestamp > this.endTimestamp) {
            this.doStop(serverLevel, livingEntity, timestamp);
        }

    }

    public final void doStop(ServerLevel serverLevel, LivingEntity livingEntity, long p_259826_) {
        this.status = Behavior.Status.STOPPED;
    }

    public String debugString() {
        return this.getClass().getSimpleName();
    }

}
