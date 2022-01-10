//package mokiyoki.enhancedanimals.ai.general.pig;
//
//import mokiyoki.enhancedanimals.ai.general.mooshroom.EnhancedWaterAvoidingRandomWalkingEatingGoalMooshroom;
//import mokiyoki.enhancedanimals.init.ModBlocks;
//import net.minecraft.world.level.block.Block;
//import net.minecraft.world.level.block.state.BlockState;
//import net.minecraft.world.level.block.Blocks;
//import net.minecraft.world.level.block.state.predicate.BlockStatePredicate;
//import net.minecraft.world.entity.PathfinderMob;
//import net.minecraft.core.BlockPos;
//
//import java.util.function.Predicate;
//
//public class EnhancedWaterAvoidingRandomWalkingEatingGoalPig extends EnhancedWaterAvoidingRandomWalkingEatingGoalMooshroom {
//
//    protected static final Predicate<BlockState> IS_CARROT = BlockStatePredicate.forBlock(Blocks.CARROTS);
//    protected static final Predicate<BlockState> IS_BEETROOT = BlockStatePredicate.forBlock(Blocks.BEETROOTS);
//    protected static final Predicate<BlockState> IS_POTATO = BlockStatePredicate.forBlock(Blocks.POTATOES);
//    protected static final Predicate<BlockState> IS_WHEAT = BlockStatePredicate.forBlock(Blocks.WHEAT);
//    protected static final Predicate<BlockState> IS_MELON = BlockStatePredicate.forBlock(Blocks.MELON);
//    protected static final Predicate<BlockState> IS_PUMPKIN = BlockStatePredicate.forBlock(Blocks.PUMPKIN);
//    protected static final Predicate<BlockState> IS_HONEY = BlockStatePredicate.forBlock(Blocks.HONEY_BLOCK);
//
//    public EnhancedWaterAvoidingRandomWalkingEatingGoalPig(PathfinderMob creature, double speedIn, int length, float probabilityIn, int wanderExecutionChance, int depth, int hungerModifier) {
//        super(creature, speedIn, length, probabilityIn, wanderExecutionChance, depth, hungerModifier);
//    }
//
//    @Override
//    protected void eatBlocks() {
//        BlockPos blockpos = new BlockPos(this.creature.blockPosition());
//        BlockState blockType = this.entityWorld.getBlockState(blockpos);
//        if (IS_CARROT.test(this.entityWorld.getBlockState(blockpos)) || IS_BEETROOT.test(blockType) || IS_POTATO.test(blockType) || IS_WHEAT.test(blockType) || IS_MELON.test(blockType) || IS_PUMPKIN.test(blockType) || IS_HONEY.test(blockType)) {
//            if (IS_MELON.test(blockType) || IS_PUMPKIN.test(blockType) || IS_HONEY.test(blockType)) {
//                this.creature.ate();
//            }
//            if (net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.entityWorld, this.creature)) {
//                this.entityWorld.destroyBlock(blockpos, false);
//            }
//            this.creature.ate();
//        } else if (IS_MELON.test(blockType) || IS_PUMPKIN.test(blockType) || IS_HONEY.test(blockType)) {
//            if (net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.entityWorld, this.creature)) {
//                this.entityWorld.destroyBlock(blockpos, false);
//            }
//        } else {
//            int root = this.entityWorld.random.nextInt(3);
//            if (root == 2) {
//                //just nibbling
//                if (!IS_RED_MUSHROOM.test(blockType)) {
//                    super.eatBlocks();
//                }
//            } else {
//                if (IS_GRASS.test(blockType) || IS_TALL_GRASS_BLOCK.test(blockType) || IS_BROWN_MUSHROOM.test(blockType)) {
//                    if (net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.entityWorld, this.creature)) {
//                        this.entityWorld.destroyBlock(blockpos, false);
//                    }
//                    this.creature.ate();
//                } else {
//                    BlockPos blockpos1 = blockpos.below();
//                    Block blockDown = this.entityWorld.getBlockState(blockpos1).getBlock();
//                    if (blockDown == Blocks.GRASS_BLOCK) {
//                        if (net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.entityWorld, this.creature)) {
//                            this.entityWorld.levelEvent(2001, blockpos1, Block.getId(Blocks.GRASS_BLOCK.defaultBlockState()));
//                            this.entityWorld.setBlock(blockpos1, Blocks.FARMLAND.defaultBlockState(), 2);
//                        }
//                        this.creature.ate();
//                    } else if (blockDown == ModBlocks.SPARSEGRASS_BLOCK) {
//                        if (net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.entityWorld, this.creature)) {
//                            this.entityWorld.levelEvent(2001, blockpos1, Block.getId(Blocks.GRASS_BLOCK.defaultBlockState()));
//                            this.entityWorld.setBlock(blockpos1, Blocks.FARMLAND.defaultBlockState(), 2);
//                        }
//                        this.creature.ate();
//                    } else if (blockDown == Blocks.DIRT) {
//                        if (net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.entityWorld, this.creature)) {
//                            this.entityWorld.levelEvent(2001, blockpos1, Block.getId(Blocks.GRASS_BLOCK.defaultBlockState()));
//                            this.entityWorld.setBlock(blockpos1, Blocks.FARMLAND.defaultBlockState(), 2);
//                        }
//                    }
//                }
//
//            }
//        }
//    }
//}
