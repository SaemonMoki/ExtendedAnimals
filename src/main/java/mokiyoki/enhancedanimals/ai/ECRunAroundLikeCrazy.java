package mokiyoki.enhancedanimals.ai;

import mokiyoki.enhancedanimals.entity.EnhancedAnimalRideableAbstract;
import mokiyoki.enhancedanimals.entity.EnhancedLlama;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;

import java.util.EnumSet;

public class ECRunAroundLikeCrazy extends Goal {
    private final EnhancedAnimalRideableAbstract ridable;
    private final double speed;
    private double targetX;
    private double targetY;
    private double targetZ;

    public ECRunAroundLikeCrazy(EnhancedLlama horse, double speedIn) {
        this.ridable = horse;
        this.speed = speedIn;
        this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE));
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute() {
        if (!this.ridable.isTame() && this.ridable.isBeingRidden()) {
            Vec3d vec3d = RandomPositionGenerator.findRandomTarget(this.ridable, 5, 4);
            if (vec3d == null) {
                return false;
            } else {
                this.targetX = vec3d.x;
                this.targetY = vec3d.y;
                this.targetZ = vec3d.z;
                return true;
            }
        } else {
            return false;
        }
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting() {
        this.ridable.getNavigator().tryMoveToXYZ(this.targetX, this.targetY, this.targetZ, this.speed);
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean shouldContinueExecuting() {
        return !this.ridable.isTame() && !this.ridable.getNavigator().noPath() && this.ridable.isBeingRidden();
    }

    /**
     * Keep ticking a continuous task that has already been started
     */
    public void tick() {
        if (!this.ridable.isTame() && this.ridable.getRNG().nextInt(50) == 0) {
            Entity entity = this.ridable.getPassengers().get(0);
            if (entity == null) {
                return;
            }

            if (entity instanceof PlayerEntity) {
                int i = this.ridable.getTemper();
                int j = this.ridable.getMaxTemper();
                if (j > 0 && this.ridable.getRNG().nextInt(j) < i && !net.minecraftforge.event.ForgeEventFactory.onAnimalTame(ridable, (PlayerEntity)entity)) {
                    //TODO ADD OWNERSHIP
                    this.ridable.setTamedBy((PlayerEntity)entity);
                    return;
                }

                this.ridable.increaseTemper(5);
            }

            this.ridable.removePassengers();
            this.ridable.makeMad();
            this.ridable.world.setEntityState(this.ridable, (byte)6);
        }

    }
}