package mokiyoki.enhancedanimals.ai.general.pig;

import mokiyoki.enhancedanimals.ai.general.GrazingGoal;
import mokiyoki.enhancedanimals.entity.EnhancedAnimalAbstract;
import mokiyoki.enhancedanimals.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.pattern.BlockStateMatcher;
import net.minecraft.util.math.BlockPos;

import java.util.function.Predicate;

public class GrazingGoalPig extends GrazingGoal {

    protected static final Predicate<BlockState> IS_CARROT = BlockStateMatcher.forBlock(Blocks.CARROTS);
    protected static final Predicate<BlockState> IS_BEETROOT = BlockStateMatcher.forBlock(Blocks.BEETROOTS);
    protected static final Predicate<BlockState> IS_POTATO = BlockStateMatcher.forBlock(Blocks.POTATOES);
    protected static final Predicate<BlockState> IS_WHEAT = BlockStateMatcher.forBlock(Blocks.WHEAT);
    protected static final Predicate<BlockState> IS_MELON = BlockStateMatcher.forBlock(Blocks.MELON);
    protected static final Predicate<BlockState> IS_PUMPKIN = BlockStateMatcher.forBlock(Blocks.PUMPKIN);
    protected static final Predicate<BlockState> IS_HONEY = BlockStateMatcher.forBlock(Blocks.HONEY_BLOCK);

    protected static final Predicate<BlockState> IS_BROWN_MUSHROOM = BlockStateMatcher.forBlock(Blocks.BROWN_MUSHROOM);
    protected static final Predicate<BlockState> IS_RED_MUSHROOM = BlockStateMatcher.forBlock(Blocks.RED_MUSHROOM);

    public GrazingGoalPig(EnhancedAnimalAbstract eanimal, double movementSpeed) {
        super(eanimal, movementSpeed);
    }

    @Override
    protected void eatBlocks() {
        BlockPos blockpos = new BlockPos(this.eanimal);
        BlockState blockType = this.eanimal.world.getBlockState(blockpos);
        if (IS_CARROT.test(this.eanimal.world.getBlockState(blockpos)) || IS_BEETROOT.test(blockType) || IS_POTATO.test(blockType) || IS_WHEAT.test(blockType) || IS_MELON.test(blockType) || IS_PUMPKIN.test(blockType) || IS_HONEY.test(blockType)) {
            if (IS_MELON.test(blockType) || IS_PUMPKIN.test(blockType) || IS_HONEY.test(blockType)) {
                this.eanimal.eatGrassBonus();
            }
            if (net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.eanimal.world, this.eanimal)) {
                this.eanimal.world.destroyBlock(blockpos, false);
            }
            this.eanimal.eatGrassBonus();
        } else if (IS_MELON.test(blockType) || IS_PUMPKIN.test(blockType) || IS_HONEY.test(blockType)) {
            if (net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.eanimal.world, this.eanimal)) {
                this.eanimal.world.destroyBlock(blockpos, false);
            }
        } else {
            int root = this.eanimal.world.rand.nextInt(3);
            if (root == 2) {
                //just nibbling
                if (!IS_RED_MUSHROOM.test(blockType)) {
                    super.eatBlocks();
                }
            } else {
                if (IS_GRASS.test(blockType) || IS_TALL_GRASS_BLOCK.test(blockType) || IS_BROWN_MUSHROOM.test(blockType)) {
                    if (net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.eanimal.world, this.eanimal)) {
                        this.eanimal.world.destroyBlock(blockpos, false);
                    }
                    this.eanimal.eatGrassBonus();
                } else {
                    BlockPos blockpos1 = blockpos.down();
                    Block blockDown = this.eanimal.world.getBlockState(blockpos1).getBlock();
                    if (blockDown == Blocks.GRASS_BLOCK) {
                        if (net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.eanimal.world, this.eanimal)) {
                            this.eanimal.world.playEvent(2001, blockpos1, Block.getStateId(Blocks.GRASS_BLOCK.getDefaultState()));
                            this.eanimal.world.setBlockState(blockpos1, Blocks.FARMLAND.getDefaultState(), 2);
                        }
                        this.eanimal.eatGrassBonus();
                    } else if (blockDown == ModBlocks.SPARSEGRASS_BLOCK) {
                        if (net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.eanimal.world, this.eanimal)) {
                            this.eanimal.world.playEvent(2001, blockpos1, Block.getStateId(Blocks.GRASS_BLOCK.getDefaultState()));
                            this.eanimal.world.setBlockState(blockpos1, Blocks.FARMLAND.getDefaultState(), 2);
                        }
                        this.eanimal.eatGrassBonus();
                    } else if (blockDown == Blocks.DIRT) {
                        if (net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.eanimal.world, this.eanimal)) {
                            this.eanimal.world.playEvent(2001, blockpos1, Block.getStateId(Blocks.GRASS_BLOCK.getDefaultState()));
                            this.eanimal.world.setBlockState(blockpos1, Blocks.FARMLAND.getDefaultState(), 2);
                        }
                    }
                }

            }
        }
    }
}
