//package mokiyoki.enhancedanimals.ai.general.chicken;
//
//import mokiyoki.enhancedanimals.ai.general.EnhancedWaterAvoidingRandomWalkingEatingGoal;
//import mokiyoki.enhancedanimals.init.ModBlocks;
//import net.minecraft.block.Block;
//import net.minecraft.block.BlockState;
//import net.minecraft.block.Blocks;
//import net.minecraft.block.pattern.BlockStateMatcher;
//import net.minecraft.world.entity.PathfinderMob;
//import net.minecraft.util.math.BlockPos;
//
//import java.util.function.Predicate;
//
//public class EnhancedWaterAvoidingRandomWalkingEatingGoalChicken extends EnhancedWaterAvoidingRandomWalkingEatingGoal {
//
//    public EnhancedWaterAvoidingRandomWalkingEatingGoalChicken(PathfinderMob creature, double speedIn, int length, float probabilityIn, int wanderExecutionChance, int depth, int hungerModifier) {
//        super(creature, speedIn, length, probabilityIn, wanderExecutionChance, depth, hungerModifier);
//    }
//
//    @Override
//    protected void eatBlocks() {
//        int root = this.entityWorld.random.nextInt(1);
//        if (root == 1) {
//            return;
//        } else {
//            super.eatBlocks();
//        }
//
//    }
//}
