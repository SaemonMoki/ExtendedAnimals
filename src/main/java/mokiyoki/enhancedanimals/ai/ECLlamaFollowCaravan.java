package mokiyoki.enhancedanimals.ai;

import mokiyoki.enhancedanimals.entity.EnhancedLlama;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;
import java.util.List;

import net.minecraft.world.entity.ai.goal.Goal.Flag;

public class ECLlamaFollowCaravan extends Goal {
    public EnhancedLlama llama;
    private double speedModifier;
    private int distCheckCounter;

    public ECLlamaFollowCaravan(EnhancedLlama llamaIn, double speedModifierIn) {
        this.llama = llamaIn;
        this.speedModifier = speedModifierIn;
        this.setFlags(EnumSet.of(Flag.MOVE));
    }

    public boolean canUse() {
        if (!this.llama.isLeashed() && !this.llama.inCaravan()) {
            // TODO 18
            List<EnhancedLlama> list = null;
            //List<EnhancedLlama> list = this.llama.level.getEntities(this.llama.getClass(), this.llama.getBoundingBox().inflate(9.0D, 4.0D, 9.0D));
            EnhancedLlama entityllama = null;
            double d0 = Double.MAX_VALUE;

            for(EnhancedLlama entityllama1 : list) {
                if (entityllama1.inCaravan() && !entityllama1.hasCaravanTrail()) {
                    double d1 = this.llama.distanceToSqr(entityllama1);
                    if (!(d1 > d0)) {
                        d0 = d1;
                        entityllama = entityllama1;
                    }
                }
            }

            if (entityllama == null) {
                for(EnhancedLlama entityllama2 : list) {
                    if (entityllama2.isLeashed() && !entityllama2.hasCaravanTrail()) {
                        double d2 = this.llama.distanceToSqr(entityllama2);
                        if (!(d2 > d0)) {
                            d0 = d2;
                            entityllama = entityllama2;
                        }
                    }
                }
            }

            if (entityllama == null) {
                return false;
            } else if (d0 < 4.0D) {
                return false;
            } else if (!entityllama.isLeashed() && !this.firstIsLeashed(entityllama, 1)) {
                return false;
            } else {
                this.llama.joinCaravan(entityllama);
                return true;
            }
        } else {
            return false;
        }
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean canContinueToUse() {
        if (this.llama.inCaravan() && this.llama.getCaravanHead().isAlive() && this.firstIsLeashed(this.llama, 0)) {
            double d0 = this.llama.distanceToSqr(this.llama.getCaravanHead());
            if (d0 > 676.0D) {
                if (this.speedModifier <= 3.0D) {
                    this.speedModifier *= 1.2D;
                    this.distCheckCounter = 40;
                    return true;
                }

                if (this.distCheckCounter == 0) {
                    return false;
                }
            }

            if (this.distCheckCounter > 0) {
                --this.distCheckCounter;
            }

            return true;
        } else {
            return false;
        }
    }

    /**
     * Reset the task's internal state. Called when this task is interrupted by another one
     */
    public void stop() {
        this.llama.leaveCaravan();
        this.speedModifier = 2.1D;
    }

    /**
     * Keep ticking a continuous task that has already been started
     */
    public void tick() {
        if (this.llama.inCaravan()) {
            EnhancedLlama entityllama = this.llama.getCaravanHead();
            double d0 = (double)this.llama.distanceTo(entityllama);
            float f = 2.0F;
            Vec3 vec3d = (new Vec3(entityllama.getX() - this.llama.getX(), entityllama.getY() - this.llama.getY(), entityllama.getZ() - this.llama.getZ())).normalize().scale(Math.max(d0 - 2.0D, 0.0D));
            this.llama.getNavigation().moveTo(this.llama.getX() + vec3d.x, this.llama.getY() + vec3d.y, this.llama.getZ() + vec3d.z, this.speedModifier);
        }
    }

    private boolean firstIsLeashed(EnhancedLlama p_190858_1_, int p_190858_2_) {
        if (p_190858_2_ > 8) {
            return false;
        } else if (p_190858_1_.inCaravan()) {
            if (p_190858_1_.getCaravanHead().isLeashed()) {
                return true;
            } else {
                EnhancedLlama entityllama = p_190858_1_.getCaravanHead();
                ++p_190858_2_;
                return this.firstIsLeashed(entityllama, p_190858_2_);
            }
        } else {
            return false;
        }
    }


}
