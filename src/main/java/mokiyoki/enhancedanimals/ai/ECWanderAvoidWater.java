package mokiyoki.enhancedanimals.ai;

import mokiyoki.enhancedanimals.entity.EnhancedChicken;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.ai.goal.RandomWalkingGoal;
import net.minecraft.util.math.Vec3d;

import javax.annotation.Nullable;

public class ECWanderAvoidWater extends RandomWalkingGoal {

    protected final float probability;
    private final EnhancedChicken enhancedChicken;

    public ECWanderAvoidWater(CreatureEntity entity, double speedIn) {
        this(entity, speedIn, 0.001F);
    }

    public ECWanderAvoidWater(CreatureEntity entity, double speedIn, float probability) {
        super(entity, speedIn);
        this.probability = probability;
        this.enhancedChicken = (EnhancedChicken) entity;
    }

    public boolean shouldExecute() {
        if (!enhancedChicken.isRoosting()) {
            return super.shouldExecute();
        }
        return false;
    }

    @Nullable
    protected Vec3d getPosition() {
        if (this.creature.isInWater())
        {
            Vec3d vec3d = RandomPositionGenerator.getLandPos(this.creature, 15, 7);
            return vec3d == null ? super.getPosition() : vec3d;
        }
        else
        {
            return this.creature.getRNG().nextFloat() >= this.probability ? RandomPositionGenerator.getLandPos(this.creature, 10, 7) : super.getPosition();
        }
    }
}
