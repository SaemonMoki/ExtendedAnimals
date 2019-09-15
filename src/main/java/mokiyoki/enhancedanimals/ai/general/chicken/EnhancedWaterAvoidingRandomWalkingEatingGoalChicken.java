package mokiyoki.enhancedanimals.ai.general.chicken;

import mokiyoki.enhancedanimals.ai.general.EnhancedWaterAvoidingRandomWalkingEatingGoal;
import mokiyoki.enhancedanimals.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.pattern.BlockStateMatcher;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.util.math.BlockPos;

import java.util.function.Predicate;

public class EnhancedWaterAvoidingRandomWalkingEatingGoalChicken extends EnhancedWaterAvoidingRandomWalkingEatingGoal {

    public EnhancedWaterAvoidingRandomWalkingEatingGoalChicken(CreatureEntity creature, double speedIn, int length, float probabilityIn, int wanderExecutionChance, int depth) {
        super(creature, speedIn, length, probabilityIn, wanderExecutionChance, depth);
    }

    @Override
    protected void eatBlocks() {
        int root = this.entityWorld.rand.nextInt(1);
        if (root == 1) {
            return;
        } else {
            super.eatBlocks();
        }

    }
}
