package mokiyoki.enhancedanimals.ai.general.pig;

import mokiyoki.enhancedanimals.ai.general.mooshroom.EnhancedWaterAvoidingRandomWalkingEatingGoalMooshroom;
import mokiyoki.enhancedanimals.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.pattern.BlockStateMatcher;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.util.math.BlockPos;

import java.util.function.Predicate;

public class EnhancedWaterAvoidingRandomWalkingEatingGoalPig extends EnhancedWaterAvoidingRandomWalkingEatingGoalMooshroom {

    protected static final Predicate<BlockState> IS_CARROT = BlockStateMatcher.forBlock(Blocks.CARROTS);
    protected static final Predicate<BlockState> IS_BEETROOT = BlockStateMatcher.forBlock(Blocks.BEETROOTS);
    protected static final Predicate<BlockState> IS_POTATO = BlockStateMatcher.forBlock(Blocks.POTATOES);
    protected static final Predicate<BlockState> IS_WHEAT = BlockStateMatcher.forBlock(Blocks.WHEAT);
    protected static final Predicate<BlockState> IS_MELON = BlockStateMatcher.forBlock(Blocks.MELON);
    protected static final Predicate<BlockState> IS_PUMPKIN = BlockStateMatcher.forBlock(Blocks.PUMPKIN);
    protected static final Predicate<BlockState> IS_HONEY = BlockStateMatcher.forBlock(Blocks.HONEY_BLOCK);

    public EnhancedWaterAvoidingRandomWalkingEatingGoalPig(CreatureEntity creature, double speedIn, int length, float probabilityIn, int wanderExecutionChance, int depth, int hungerModifier) {
        super(creature, speedIn, length, probabilityIn, wanderExecutionChance, depth, hungerModifier);
    }

    @Override
    protected void eatBlocks() {
        BlockPos blockpos = new BlockPos(this.creature);
        BlockState blockType = this.entityWorld.getBlockState(blockpos);
        if (IS_CARROT.test(this.entityWorld.getBlockState(blockpos)) || IS_BEETROOT.test(blockType) || IS_POTATO.test(blockType) || IS_WHEAT.test(blockType) || IS_MELON.test(blockType) || IS_PUMPKIN.test(blockType) || IS_HONEY.test(blockType)) {
            if (IS_MELON.test(blockType) || IS_PUMPKIN.test(blockType) || IS_HONEY.test(blockType)) {
                this.creature.eatGrassBonus();
            }
            if (net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.entityWorld, this.creature)) {
                this.entityWorld.destroyBlock(blockpos, false);
            }
            this.creature.eatGrassBonus();
        } else if (IS_MELON.test(blockType) || IS_PUMPKIN.test(blockType) || IS_HONEY.test(blockType)) {
            if (net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.entityWorld, this.creature)) {
                this.entityWorld.destroyBlock(blockpos, false);
            }
        } else {
            int root = this.entityWorld.rand.nextInt(3);
            if (root == 2) {
                //just nibbling
                if (!IS_RED_MUSHROOM.test(blockType)) {
                    super.eatBlocks();
                }
            } else {
                if (IS_GRASS.test(blockType) || IS_TALL_GRASS_BLOCK.test(blockType) || IS_BROWN_MUSHROOM.test(blockType)) {
                    if (net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.entityWorld, this.creature)) {
                        this.entityWorld.destroyBlock(blockpos, false);
                    }
                    this.creature.eatGrassBonus();
                } else {
                    BlockPos blockpos1 = blockpos.down();
                    Block blockDown = this.entityWorld.getBlockState(blockpos1).getBlock();
                    if (blockDown == Blocks.GRASS_BLOCK) {
                        if (net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.entityWorld, this.creature)) {
                            this.entityWorld.playEvent(2001, blockpos1, Block.getStateId(Blocks.GRASS_BLOCK.getDefaultState()));
                            this.entityWorld.setBlockState(blockpos1, Blocks.FARMLAND.getDefaultState(), 2);
                        }
                        this.creature.eatGrassBonus();
                    } else if (blockDown == ModBlocks.SPARSE_GRASS_BLOCK) {
                        if (net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.entityWorld, this.creature)) {
                            this.entityWorld.playEvent(2001, blockpos1, Block.getStateId(Blocks.GRASS_BLOCK.getDefaultState()));
                            this.entityWorld.setBlockState(blockpos1, Blocks.FARMLAND.getDefaultState(), 2);
                        }
                        this.creature.eatGrassBonus();
                    } else if (blockDown == Blocks.DIRT) {
                        if (net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.entityWorld, this.creature)) {
                            this.entityWorld.playEvent(2001, blockpos1, Block.getStateId(Blocks.GRASS_BLOCK.getDefaultState()));
                            this.entityWorld.setBlockState(blockpos1, Blocks.FARMLAND.getDefaultState(), 2);
                        }
                    }
                }

            }
        }
    }
}
