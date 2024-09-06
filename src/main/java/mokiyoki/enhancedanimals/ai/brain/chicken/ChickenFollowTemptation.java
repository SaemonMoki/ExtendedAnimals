package mokiyoki.enhancedanimals.ai.brain.chicken;

import mokiyoki.enhancedanimals.entity.EnhancedChicken;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.behavior.FollowTemptation;

import java.util.function.Function;

import static mokiyoki.enhancedanimals.ai.brain.chicken.ChickenBrain.canMoveOrLookAround;

public class ChickenFollowTemptation extends FollowTemptation {

    public ChickenFollowTemptation(Function<LivingEntity, Float> p_147486_) {
        super(p_147486_);
    }

    @Override
    protected boolean checkExtraStartConditions(ServerLevel serverLevel, PathfinderMob pathfinderMob) {
        if (pathfinderMob instanceof EnhancedChicken chicken) {
            return canMoveOrLookAround(chicken);
        }
        return false;
    }

}
