//package mokiyoki.enhancedanimals.ai.general.mooshroom;
//
//import mokiyoki.enhancedanimals.ai.general.EnhancedWaterAvoidingRandomWalkingEatingGoal;
//import mokiyoki.enhancedanimals.init.ModBlocks;
//import net.minecraft.world.level.block.Block;
//import net.minecraft.world.level.block.state.BlockState;
//import net.minecraft.world.level.block.Blocks;
//import net.minecraft.world.level.block.state.predicate.BlockStatePredicate;
//import net.minecraft.world.entity.PathfinderMob;
//import net.minecraft.core.BlockPos;
//import net.minecraft.world.level.LevelReader;
//
//import java.util.function.Predicate;
//
//public class EnhancedWaterAvoidingRandomWalkingEatingGoalMooshroom extends EnhancedWaterAvoidingRandomWalkingEatingGoal {
//
//    protected static final Predicate<BlockState> IS_MYCELIUM = BlockStatePredicate.forBlock(Blocks.MYCELIUM);
//    protected static final Predicate<BlockState> IS_BROWN_MUSHROOM = BlockStatePredicate.forBlock(Blocks.BROWN_MUSHROOM);
//    protected static final Predicate<BlockState> IS_RED_MUSHROOM = BlockStatePredicate.forBlock(Blocks.RED_MUSHROOM);
//
//    public EnhancedWaterAvoidingRandomWalkingEatingGoalMooshroom(PathfinderMob creature, double speedIn, int length, float probabilityIn, int wanderExecutionChance, int depth, int hungerModifier) {
//        super(creature, speedIn, length, probabilityIn, wanderExecutionChance, depth, hungerModifier);
//    }
//
//    @Override
//    protected boolean checkForFood() {
//        BlockPos blockpos = new BlockPos(this.creature.blockPosition());
//
//        //TODO add the predicate for different blocks to eat based on temperaments and animal type.
//        BlockState blockState = this.entityWorld.getBlockState(blockpos);
//        if (IS_GRASS.test(this.entityWorld.getBlockState(blockpos)) || IS_TALL_GRASS_BLOCK.test(this.entityWorld.getBlockState(blockpos)) || IS_BROWN_MUSHROOM.test(this.entityWorld.getBlockState(blockpos)) || IS_RED_MUSHROOM.test(this.entityWorld.getBlockState(blockpos))) {
//            return true;
//        } else {
//            BlockState blockStateDown = this.entityWorld.getBlockState(blockpos.below());
//            return blockStateDown.getBlock() == Blocks.GRASS_BLOCK || blockStateDown.getBlock() == ModBlocks.SPARSEGRASS_BLOCK || blockStateDown.getBlock() == Blocks.MYCELIUM || blockStateDown.getBlock() == ModBlocks.PATCHYMYCELIUM_BLOCK;
//        }
//    }
//
//    @Override
//    protected void eatBlocks() {
//        BlockPos blockpos = new BlockPos(this.creature.blockPosition());
//        if (IS_GRASS.test(this.entityWorld.getBlockState(blockpos)) || IS_TALL_GRASS_BLOCK.test(this.entityWorld.getBlockState(blockpos)) || IS_BROWN_MUSHROOM.test(this.entityWorld.getBlockState(blockpos)) || IS_RED_MUSHROOM.test(this.entityWorld.getBlockState(blockpos))) {
//            if (net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.entityWorld, this.creature)) {
//                this.entityWorld.destroyBlock(blockpos, false);
//            }
//            this.creature.ate();
//        } else {
//            BlockPos blockposDown = blockpos.below();
//            if (this.entityWorld.getBlockState(blockposDown).getBlock() == Blocks.GRASS_BLOCK) {
//                if (net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.entityWorld, this.creature)) {
//                    this.entityWorld.levelEvent(2001, blockposDown, Block.getId(Blocks.GRASS_BLOCK.defaultBlockState()));
//                    this.entityWorld.setBlock(blockposDown, ModBlocks.SPARSEGRASS_BLOCK.defaultBlockState(), 2);
//                }
//                this.creature.ate();
//            }else if (this.entityWorld.getBlockState(blockposDown).getBlock() == Blocks.MYCELIUM) {
//                if (net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.entityWorld, this.creature)) {
//                    this.entityWorld.levelEvent(2001, blockposDown, Block.getId(Blocks.MYCELIUM.defaultBlockState()));
//                    this.entityWorld.setBlock(blockposDown, ModBlocks.PATCHYMYCELIUM_BLOCK.defaultBlockState(), 2);
//                }
//                this.creature.ate();
//            } else if (this.entityWorld.getBlockState(blockposDown).getBlock() == ModBlocks.SPARSEGRASS_BLOCK) {
//                if (net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.entityWorld, this.creature)) {
//                    this.entityWorld.levelEvent(2001, blockposDown, Block.getId(Blocks.GRASS_BLOCK.defaultBlockState()));
//                    this.entityWorld.setBlock(blockposDown, Blocks.DIRT.defaultBlockState(), 2);
//                }
//
//                this.creature.ate();
//            } else if (this.entityWorld.getBlockState(blockposDown).getBlock() == ModBlocks.PATCHYMYCELIUM_BLOCK) {
//                if (net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.entityWorld, this.creature)) {
//                    this.entityWorld.levelEvent(2001, blockposDown, Block.getId(Blocks.MYCELIUM.defaultBlockState()));
//                    this.entityWorld.setBlock(blockposDown, Blocks.DIRT.defaultBlockState(), 2);
//                }
//
//                this.creature.ate();
//            }
//        }
//
//    }
//
//    @Override
//    protected boolean shouldMoveTo(LevelReader worldIn, BlockPos pos) {
//        BlockState blockstate = worldIn.getBlockState(pos);
//        if (IS_GRASS_BLOCK.test(blockstate) || IS_GRASS.test(blockstate) || IS_SPARSE_GRASS_BLOCK.test(blockstate) || IS_TALL_GRASS_BLOCK.test(blockstate) || IS_MYCELIUM.test(blockstate) || IS_BROWN_MUSHROOM.test(blockstate) || IS_RED_MUSHROOM.test(blockstate)) {
//            return true;
//        }
//        return false;
//    }
//
//}
