package mokiyoki.enhancedanimals.ai;

import mokiyoki.enhancedanimals.entity.EnhancedChicken;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.util.math.Vec3d;

import javax.annotation.Nullable;

public class ECWanderAvoidWater extends EntityAIWander {

    protected final float probability;
    private final EnhancedChicken enhancedChicken;

    public ECWanderAvoidWater(EntityCreature entity, double speedIn) {
        this(entity, speedIn, 0.001F);
    }

    public ECWanderAvoidWater(EntityCreature entity, double speedIn, float probability) {
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
        if (this.entity.isInWater())
        {
            Vec3d vec3d = RandomPositionGenerator.getLandPos(this.entity, 15, 7);
            return vec3d == null ? super.getPosition() : vec3d;
        }
        else
        {
            return this.entity.getRNG().nextFloat() >= this.probability ? RandomPositionGenerator.getLandPos(this.entity, 10, 7) : super.getPosition();
        }
    }
}
