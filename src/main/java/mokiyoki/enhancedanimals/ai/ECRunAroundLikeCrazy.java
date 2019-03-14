package mokiyoki.enhancedanimals.ai;

import mokiyoki.enhancedanimals.entity.EnhancedLlama;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.Vec3d;

public class ECRunAroundLikeCrazy extends EntityAIBase {
    private final EnhancedLlama llama;
    private final double speed;
    private double targetX;
    private double targetY;
    private double targetZ;

    public ECRunAroundLikeCrazy(EnhancedLlama horse, double speedIn) {
        this.llama = horse;
        this.speed = speedIn;
        this.setMutexBits(1);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute() {
        if (!this.llama.isTame() && this.llama.isBeingRidden()) {
            Vec3d vec3d = RandomPositionGenerator.findRandomTarget(this.llama, 5, 4);
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
        this.llama.getNavigator().tryMoveToXYZ(this.targetX, this.targetY, this.targetZ, this.speed);
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean shouldContinueExecuting() {
        return !this.llama.isTame() && !this.llama.getNavigator().noPath() && this.llama.isBeingRidden();
    }

    /**
     * Keep ticking a continuous task that has already been started
     */
    public void tick() {
        if (!this.llama.isTame() && this.llama.getRNG().nextInt(50) == 0) {
            Entity entity = this.llama.getPassengers().get(0);
            if (entity == null) {
                return;
            }

            if (entity instanceof EntityPlayer) {
                int i = this.llama.getTemper();
                int j = this.llama.getMaxTemper();
                if (j > 0 && this.llama.getRNG().nextInt(j) < i && !net.minecraftforge.event.ForgeEventFactory.onAnimalTame(llama, (EntityPlayer)entity)) {
                    this.llama.setTamedBy((EntityPlayer)entity);
                    return;
                }

                this.llama.increaseTemper(5);
            }

            this.llama.removePassengers();
            this.llama.makeMad();
            this.llama.world.setEntityState(this.llama, (byte)6);
        }

    }
}