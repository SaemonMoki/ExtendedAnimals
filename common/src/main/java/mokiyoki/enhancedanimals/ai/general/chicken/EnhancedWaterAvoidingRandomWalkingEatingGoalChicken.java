package mokiyoki.enhancedanimals.ai.general.chicken;

import mokiyoki.enhancedanimals.ai.general.EnhancedWaterAvoidingRandomWalkingEatingGoal;
import mokiyoki.enhancedanimals.init.ModBlocks;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.core.BlockPos;

import java.util.function.Predicate;

public class EnhancedWaterAvoidingRandomWalkingEatingGoalChicken extends EnhancedWaterAvoidingRandomWalkingEatingGoal {

    public EnhancedWaterAvoidingRandomWalkingEatingGoalChicken(PathfinderMob creature, double speedIn, int length, float probabilityIn, int wanderExecutionChance, int depth, int hungerModifier) {
        super(creature, speedIn, length, probabilityIn, wanderExecutionChance, depth, hungerModifier);
    }

    @Override
    protected void eatBlocks() {
        int root = this.entityWorld.random.nextInt(1);
        if (root == 1) {
            return;
        } else {
            super.eatBlocks();
        }

    }
}
