package mokiyoki.enhancedanimals.ai.general.chicken;

import mokiyoki.enhancedanimals.entity.EnhancedChicken;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;

public class ECWanderAvoidWater extends RandomStrollGoal {

    protected final float probability;
    private final EnhancedChicken enhancedChicken;

    public ECWanderAvoidWater(PathfinderMob entity, double speedIn) {
        this(entity, speedIn, 0.001F);
    }

    public ECWanderAvoidWater(PathfinderMob entity, double speedIn, float probability) {
        super(entity, speedIn);
        this.probability = probability;
        this.enhancedChicken = (EnhancedChicken) entity;
    }

    public boolean canUse() {
        if (!enhancedChicken.isRoosting()) {
            return super.canUse();
        }
        return false;
    }

    @Nullable
    protected Vec3 getPosition() {
        if (this.mob.isInWater())
        {
            Vec3 vec3d = LandRandomPos.getPos(this.mob, 15, 7);
            return vec3d == null ? super.getPosition() : vec3d;
        }
        else
        {
            return this.mob.getRandom().nextFloat() >= this.probability ? LandRandomPos.getPos(this.mob, 10, 7) : super.getPosition();
        }
    }
}
