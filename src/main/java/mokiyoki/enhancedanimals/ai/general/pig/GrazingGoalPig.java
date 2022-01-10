//package mokiyoki.enhancedanimals.ai.general.pig;
//
//import mokiyoki.enhancedanimals.ai.general.GrazingGoal;
//import mokiyoki.enhancedanimals.entity.EnhancedAnimalAbstract;
//import mokiyoki.enhancedanimals.init.ModBlocks;
//import net.minecraft.world.level.block.Block;
//import net.minecraft.world.level.block.state.BlockState;
//import net.minecraft.world.level.block.Blocks;
//import net.minecraft.world.level.block.state.predicate.BlockStatePredicate;
//import net.minecraft.core.BlockPos;
//
//import java.util.function.Predicate;
//
//public class GrazingGoalPig extends GrazingGoal {
//
//    protected static final Predicate<BlockState> IS_CARROT = BlockStatePredicate.forBlock(Blocks.CARROTS);
//    protected static final Predicate<BlockState> IS_BEETROOT = BlockStatePredicate.forBlock(Blocks.BEETROOTS);
//    protected static final Predicate<BlockState> IS_POTATO = BlockStatePredicate.forBlock(Blocks.POTATOES);
//    protected static final Predicate<BlockState> IS_WHEAT = BlockStatePredicate.forBlock(Blocks.WHEAT);
//    protected static final Predicate<BlockState> IS_MELON = BlockStatePredicate.forBlock(Blocks.MELON);
//    protected static final Predicate<BlockState> IS_PUMPKIN = BlockStatePredicate.forBlock(Blocks.PUMPKIN);
//    protected static final Predicate<BlockState> IS_HONEY = BlockStatePredicate.forBlock(Blocks.HONEY_BLOCK);
//
//    protected static final Predicate<BlockState> IS_BROWN_MUSHROOM = BlockStatePredicate.forBlock(Blocks.BROWN_MUSHROOM);
//    protected static final Predicate<BlockState> IS_RED_MUSHROOM = BlockStatePredicate.forBlock(Blocks.RED_MUSHROOM);
//
//    public GrazingGoalPig(EnhancedAnimalAbstract eanimal, double movementSpeed) {
//        super(eanimal, movementSpeed);
//    }
//
//    @Override
//    protected void eatBlocks() {
//        BlockPos blockpos = new BlockPos(this.eanimal.blockPosition());
//        BlockState blockType = this.eanimal.level.getBlockState(blockpos);
//        if (IS_CARROT.test(this.eanimal.level.getBlockState(blockpos)) || IS_BEETROOT.test(blockType) || IS_POTATO.test(blockType) || IS_WHEAT.test(blockType) || IS_MELON.test(blockType) || IS_PUMPKIN.test(blockType) || IS_HONEY.test(blockType)) {
//            if (IS_MELON.test(blockType) || IS_PUMPKIN.test(blockType) || IS_HONEY.test(blockType)) {
//                this.eanimal.ate();
//            }
//            if (net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.eanimal.level, this.eanimal)) {
//                this.eanimal.level.destroyBlock(blockpos, false);
//            }
//            this.eanimal.ate();
//        } else if (IS_MELON.test(blockType) || IS_PUMPKIN.test(blockType) || IS_HONEY.test(blockType)) {
//            if (net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.eanimal.level, this.eanimal)) {
//                this.eanimal.level.destroyBlock(blockpos, false);
//            }
//        } else {
//            int root = this.eanimal.level.random.nextInt(3);
//            if (root == 2) {
//                //just nibbling
//                if (!IS_RED_MUSHROOM.test(blockType)) {
//                    super.eatBlocks();
//                }
//            } else {
//                if (IS_GRASS.test(blockType) || IS_TALL_GRASS_BLOCK.test(blockType) || IS_BROWN_MUSHROOM.test(blockType)) {
//                    if (net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.eanimal.level, this.eanimal)) {
//                        this.eanimal.level.destroyBlock(blockpos, false);
//                    }
//                    this.eanimal.ate();
//                } else {
//                    BlockPos blockpos1 = blockpos.below();
//                    Block blockDown = this.eanimal.level.getBlockState(blockpos1).getBlock();
//                    if (blockDown == Blocks.GRASS_BLOCK) {
//                        if (net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.eanimal.level, this.eanimal)) {
//                            this.eanimal.level.levelEvent(2001, blockpos1, Block.getId(Blocks.GRASS_BLOCK.defaultBlockState()));
//                            this.eanimal.level.setBlock(blockpos1, Blocks.FARMLAND.defaultBlockState(), 2);
//                        }
//                        this.eanimal.ate();
//                    } else if (blockDown == ModBlocks.SPARSEGRASS_BLOCK) {
//                        if (net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.eanimal.level, this.eanimal)) {
//                            this.eanimal.level.levelEvent(2001, blockpos1, Block.getId(Blocks.GRASS_BLOCK.defaultBlockState()));
//                            this.eanimal.level.setBlock(blockpos1, Blocks.FARMLAND.defaultBlockState(), 2);
//                        }
//                        this.eanimal.ate();
//                    } else if (blockDown == Blocks.DIRT) {
//                        if (net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.eanimal.level, this.eanimal)) {
//                            this.eanimal.level.levelEvent(2001, blockpos1, Block.getId(Blocks.GRASS_BLOCK.defaultBlockState()));
//                            this.eanimal.level.setBlock(blockpos1, Blocks.FARMLAND.defaultBlockState(), 2);
//                        }
//                    }
//                }
//
//            }
//        }
//    }
//}
