package mokiyoki.enhancedanimals.ai;

import mokiyoki.enhancedanimals.entity.EnhancedAnimalRideableAbstract;
import mokiyoki.enhancedanimals.entity.EnhancedLlama;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.util.RandomPos;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

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
        this.setFlags(EnumSet.of(Goal.Flag.MOVE));
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean canUse() {
        if (!this.ridable.isTame() && this.ridable.isVehicle()) {
            Vec3 vec3d = RandomPos.getPos(this.ridable, 5, 4);
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
    public void start() {
        this.ridable.getNavigation().moveTo(this.targetX, this.targetY, this.targetZ, this.speed);
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean canContinueToUse() {
        return !this.ridable.isTame() && !this.ridable.getNavigation().isDone() && this.ridable.isVehicle();
    }

    /**
     * Keep ticking a continuous task that has already been started
     */
    public void tick() {
        if (!this.ridable.isTame() && this.ridable.getRandom().nextInt(50) == 0) {
            Entity entity = this.ridable.getPassengers().get(0);
            if (entity == null) {
                return;
            }

            if (entity instanceof Player) {
                int i = this.ridable.getTemper();
                int j = this.ridable.getMaxTemper();
                if (j > 0 && this.ridable.getRandom().nextInt(j) < i && !net.minecraftforge.event.ForgeEventFactory.onAnimalTame(ridable, (Player)entity)) {
                    //TODO ADD OWNERSHIP
                    this.ridable.setTamedBy((Player)entity);
                    return;
                }

                this.ridable.increaseTemper(5);
            }

            this.ridable.ejectPassengers();
            this.ridable.makeMad();
            this.ridable.level.broadcastEntityEvent(this.ridable, (byte)6);
        }

    }
}