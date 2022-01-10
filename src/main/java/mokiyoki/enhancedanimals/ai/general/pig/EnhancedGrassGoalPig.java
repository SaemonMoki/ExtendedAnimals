//package mokiyoki.enhancedanimals.ai.general.pig;
//
//import mokiyoki.enhancedanimals.ai.general.EnhancedGrassGoal;
//import mokiyoki.enhancedanimals.entity.Temperament;
//import mokiyoki.enhancedanimals.init.ModBlocks;
//import net.minecraft.world.level.block.Block;
//import net.minecraft.world.level.block.state.BlockState;
//import net.minecraft.world.level.block.Blocks;
//import net.minecraft.world.level.block.state.predicate.BlockStatePredicate;
//import net.minecraft.world.entity.Mob;
//import net.minecraft.core.BlockPos;
//
//import java.util.Map;
//import java.util.Random;
//import java.util.function.Predicate;
//
//public class EnhancedGrassGoalPig extends EnhancedGrassGoal {
//
//    protected static final Predicate<BlockState> IS_MUSHROOM = BlockStatePredicate.forBlock(Blocks.BROWN_MUSHROOM);
//
//    public EnhancedGrassGoalPig(Mob grassEaterEntityIn, Map<Temperament, Integer> temperaments) {
//        super(grassEaterEntityIn, temperaments);
//    }
//
//    @Override
//    public void tick() {
//        Random rand = new Random();
//        int root = rand.nextInt(3);
//        if (root == 2) {
//            //just nibbling
//            super.tick();
//        } else {
//            this.eatingGrassTimer = Math.max(0, this.eatingGrassTimer - 1);
//            if (this.eatingGrassTimer == 4) {
//                BlockPos blockpos = new BlockPos(this.grassEaterEntity.blockPosition());
//                if (IS_TALLGRASS.test(this.entityWorld.getBlockState(blockpos))) {
//                    if (net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.entityWorld, this.grassEaterEntity)) {
//                        this.entityWorld.destroyBlock(blockpos, false);
//                    }
//
//                    this.grassEaterEntity.ate();
//                    this.grassEaterEntity.ate();
//
//                } else if (IS_GRASSBLOCK.test(this.entityWorld.getBlockState(blockpos)) || IS_MUSHROOM.test(this.entityWorld.getBlockState(blockpos))) {
//                    if (net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.entityWorld, this.grassEaterEntity)) {
//                        this.entityWorld.destroyBlock(blockpos, false);
//                    }
//
//                    this.grassEaterEntity.ate();
//
//                }
//                BlockPos blockpos1 = blockpos.below();
//                if (this.entityWorld.getBlockState(blockpos1).getBlock() == Blocks.GRASS_BLOCK || this.entityWorld.getBlockState(blockpos1).getBlock() == Blocks.DIRT || this.entityWorld.getBlockState(blockpos1).getBlock() == Blocks.COARSE_DIRT || this.entityWorld.getBlockState(blockpos1).getBlock() == Blocks.PODZOL || this.entityWorld.getBlockState(blockpos1).getBlock() == Blocks.MYCELIUM || this.entityWorld.getBlockState(blockpos1).getBlock() == ModBlocks.SPARSEGRASS_BLOCK) {
//                    if (net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.entityWorld, this.grassEaterEntity)) {
//                        this.entityWorld.levelEvent(2001, blockpos1, Block.getId(Blocks.GRASS_BLOCK.defaultBlockState()));
////                        this.entityWorld.setBlockState(blockpos1, Blocks.DIRT.getDefaultState(), 2);
//                        this.entityWorld.setBlock(blockpos1, Blocks.FARMLAND.defaultBlockState(), 2);
//                    }
//
//                    this.grassEaterEntity.ate();
//                }
//            }
//
//        }
//    }
//}
