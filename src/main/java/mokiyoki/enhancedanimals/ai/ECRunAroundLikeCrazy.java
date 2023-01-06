package mokiyoki.enhancedanimals.ai;

import mokiyoki.enhancedanimals.entity.EnhancedAnimalRideableAbstract;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.ai.util.RandomPos;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.EnumSet;

public class ECRunAroundLikeCrazy extends Goal {
    private final EnhancedAnimalRideableAbstract rideable;
    private final double speedModifier;
    private double posX;
    private double posY;
    private double posZ;

    public ECRunAroundLikeCrazy(EnhancedAnimalRideableAbstract rideable, double speedIn) {
        this.rideable = rideable;
        this.speedModifier = speedIn;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE));
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean canUse() {
        if (this.rideable.isOnFire()) {
            BlockPos blockpos = this.lookForWater(this.rideable.level, this.rideable, 5);
            if (blockpos != null) {
                this.posX = (double)blockpos.getX();
                this.posY = (double)blockpos.getY();
                this.posZ = (double)blockpos.getZ();
                return true;
            }
            return this.findRandomPosition();
        }

        if (!this.rideable.isTame() && this.rideable.isVehicle()) {
            Vec3 vec3 = DefaultRandomPos.getPos(this.rideable, 5, 4);
            if (vec3 == null) {
                return false;
            } else {
                this.posX = vec3.x;
                this.posY = vec3.y;
                this.posZ = vec3.z;
                return true;
            }
        } else {
            return false;
        }
    }

    public void start() {
        this.rideable.getNavigation().moveTo(this.posX, this.posY, this.posZ, this.speedModifier);
    }

    public boolean canContinueToUse() {
        return !this.rideable.isTame() && !this.rideable.getNavigation().isDone() && this.rideable.isVehicle();
    }

    public void tick() {
        if (!this.rideable.isTame() && this.rideable.getRandom().nextInt(50) == 0) {
            Entity entity = this.rideable.getPassengers().get(0);
            if (entity == null) {
                return;
            }

            if (entity instanceof Player) {
                int i = this.rideable.getTemper();
                int j = this.rideable.getMaxTemper();
                if (j > 0 && this.rideable.getRandom().nextInt(j) < i && !net.minecraftforge.event.ForgeEventFactory.onAnimalTame(rideable, (Player)entity)) {
                    this.rideable.setTamedBy((Player)entity);
                    return;
                }

                this.rideable.increaseTemper(5);
            }

            this.rideable.ejectPassengers();
            this.rideable.makeMad();
            this.rideable.level.broadcastEntityEvent(this.rideable, (byte)6);
        }

    }

    protected boolean findRandomPosition() {
        Vec3 vec3 = DefaultRandomPos.getPos(this.rideable, 5, 4);
        if (vec3 == null) {
            return false;
        } else {
            this.posX = vec3.x;
            this.posY = vec3.y;
            this.posZ = vec3.z;
            return true;
        }
    }

    @Nullable
    protected BlockPos lookForWater(BlockGetter p_198173_, Entity p_198174_, int p_198175_) {
        BlockPos blockpos = p_198174_.blockPosition();
        return !p_198173_.getBlockState(blockpos).getCollisionShape(p_198173_, blockpos).isEmpty() ? null : BlockPos.findClosestMatch(p_198174_.blockPosition(), p_198175_, 1, (p_196649_) -> {
            return p_198173_.getFluidState(p_196649_).is(FluidTags.WATER);
        }).orElse((BlockPos)null);
    }
}
