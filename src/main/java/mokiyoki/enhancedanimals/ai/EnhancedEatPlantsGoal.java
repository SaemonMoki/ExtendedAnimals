package mokiyoki.enhancedanimals.ai;

import mokiyoki.enhancedanimals.blocks.GrowableDoubleHigh;
import mokiyoki.enhancedanimals.blocks.GrowablePlant;
import mokiyoki.enhancedanimals.entity.EnhancedAnimalAbstract;
import mokiyoki.enhancedanimals.init.ModBlocks;
import net.minecraft.world.level.block.BeetrootBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.CactusBlock;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.MelonBlock;
import net.minecraft.world.level.block.PumpkinBlock;
import net.minecraft.world.level.block.SweetBerryBushBlock;
import net.minecraft.world.level.block.TallFlowerBlock;
import net.minecraft.world.level.block.TallGrassBlock;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.Level;
import net.minecraftforge.fml.common.Mod;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import static mokiyoki.enhancedanimals.blocks.GrowableDoubleHigh.HALF;

public class EnhancedEatPlantsGoal extends MoveToBlockGoal {

    private final EnhancedAnimalAbstract animal;
    private boolean wantsToEat;
    private boolean canEat;
    protected Map<Block, EatValues> ediblePlants;

    public EnhancedEatPlantsGoal(EnhancedAnimalAbstract animalIn, Map<Block, EatValues> foods) {
        super(animalIn, (double)0.7F, 16);
        this.animal = animalIn;
        this.ediblePlants = foods;
    }

    public boolean canUse() {
        if (this.nextStartTick <= 0) {
            if (!net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.animal.level, this.animal)) {
                return false;
            }

            this.canEat = false;
            this.wantsToEat = true;
        }

        return animal.getHunger() > 3000 && !animal.isAnimalSleeping() && super.canUse();
    }

    public boolean canContinueToUse() {
        return this.canEat && super.canContinueToUse();
    }

    public void tick() {
        super.tick();
        this.animal.getLookControl().setLookAt((double)this.blockPos.getX() + 0.5D, (double)(this.blockPos.getY() + 1), (double)this.blockPos.getZ() + 0.5D, 10.0F, (float)this.animal.getMaxHeadXRot());
        if (this.isReachedTarget()) {
            Level world = this.animal.level;
            BlockPos blockpos = this.blockPos.above();
            if (this.canEat && isEdible(world, blockpos)) {
                BlockState blockState = world.getBlockState(blockpos);
                Block block = blockState.getBlock();
                EatValues blockValues = this.ediblePlants.get(block);
                if (blockValues != null) {
                    Integer integer = getPlantAge(blockState);
                    this.animal.decreaseHunger(blockValues.getHungerPoints(integer, getPlantMaxAge(block)));
                    if (block instanceof MelonBlock || block instanceof PumpkinBlock) {
                        world.setBlock(blockpos, Blocks.AIR.defaultBlockState(), 2);
                        world.destroyBlock(blockpos, false);
                    } else {
                        setNewPlantBlockState(world, blockpos, blockState, block, integer);
                        world.levelEvent(2001, blockpos, Block.getId(blockState));
                    }
                    this.animal.eatingTicks = ThreadLocalRandom.current().nextInt(30) + 40;
                    this.canEat = false;
                    this.nextStartTick = 20;
                }
            }
        } else if (this.blockPos.closerThan(this.animal.blockPosition(), 2.0D)) {
            Level world = this.animal.level;
            BlockPos blockpos = this.blockPos.above();
            BlockState blockState = world.getBlockState(blockpos);
            Block block = blockState.getBlock();
            int cactusSize = 0;
            while (world.getBlockState(blockpos.above()).getBlock() == Blocks.CACTUS) {
                blockpos = blockpos.above();
                cactusSize++;
            }
            if ((block instanceof CactusBlock && cactusSize != 0) || block instanceof MelonBlock || block instanceof PumpkinBlock) {
                EatValues blockValues = this.ediblePlants.get(block);
                if (blockValues != null) {
                    this.animal.decreaseHunger(blockValues.hungerPoints);
                    world.setBlock(blockpos, Blocks.AIR.defaultBlockState(), 2);
                    world.destroyBlock(blockpos, false);
                    this.animal.eatingTicks = ThreadLocalRandom.current().nextInt(30) + 40;
                    this.canEat = false;
                    this.nextStartTick = 20;
                }
            } else if (block instanceof SweetBerryBushBlock) {
                EatValues blockValues = this.ediblePlants.get(block);
                if (blockValues != null) {
                    Integer integer = getPlantAge(blockState);
                    this.animal.decreaseHunger(blockValues.getHungerPoints(integer, getPlantMaxAge(block)));
                    setNewPlantBlockState(world, blockpos, blockState, block, integer);
                    world.levelEvent(2001, blockpos, Block.getId(blockState));
                    this.animal.eatingTicks = ThreadLocalRandom.current().nextInt(30) + 40;
                    this.canEat = false;
                    this.nextStartTick = 20;
                }
            }
        }

    }

    protected boolean isValidTarget(LevelReader worldIn, BlockPos pos) {
        if (isEdible(worldIn, pos.above()) && this.wantsToEat && !this.canEat) {
            this.canEat = true;
            return true;
        }

        return false;
    }

    protected boolean isEdible(LevelReader world, BlockPos pos){
        Block block = world.getBlockState(pos).getBlock();
        BlockState blockState = world.getBlockState(pos);
        if (this.ediblePlants.containsKey(block)) {
            if (block instanceof SweetBerryBushBlock) {
                return this.ediblePlants.get(block).isRipeEnough(blockState.getValue(SweetBerryBushBlock.AGE));
            } else if (block instanceof CropBlock) {
                if (block instanceof GrowableDoubleHigh) {
                    if (blockState.getValue(HALF) == DoubleBlockHalf.UPPER) {
                        return blockState.getValue(CropBlock.AGE) != 0;
                    }
                } else if (block instanceof BeetrootBlock) {
                    return this.ediblePlants.get(block).isRipeEnough(blockState.getValue(BeetrootBlock.AGE));
                }
                return this.ediblePlants.get(block).isRipeEnough(blockState.getValue(CropBlock.AGE));
            } else {
                return true;
            }
        }
        return false;
    }

    protected Integer getPlantAge(BlockState plant) {
        Block plantBlock = plant.getBlock();
        if (plantBlock instanceof BeetrootBlock) {
            return plant.getValue(BeetrootBlock.AGE);
        } else if (plantBlock instanceof CropBlock) {
            return plant.getValue(CropBlock.AGE);
        } else if (plantBlock instanceof SweetBerryBushBlock) {
            return plant.getValue(SweetBerryBushBlock.AGE);
        } else if (plantBlock instanceof BushBlock) {
            return 7;
        } else {
            return 0;
        }
    }

    protected Integer getPlantMaxAge(Block plantBlock) {
        if (plantBlock instanceof SweetBerryBushBlock) {
            return 3;
        } else if (plantBlock instanceof BushBlock) {
            return 7;
        } else {
            return 0;
        }
    }

    protected void setNewPlantBlockState(Level world, BlockPos blockpos, BlockState blockstate, Block block, Integer integer) {
        if (block instanceof CropBlock) {
            if (block instanceof GrowableDoubleHigh) {
                BlockState topBlockstate = world.getBlockState(blockpos.above());
                if (topBlockstate.getBlock() instanceof GrowableDoubleHigh) {
                    int topAge = topBlockstate.getValue(CropBlock.AGE);
                    int bottomAge = blockstate.getValue(CropBlock.AGE);
                    if (topAge != 0) {
                        world.setBlock(blockpos.above(), block.defaultBlockState().setValue(GrowableDoubleHigh.AGE, this.ediblePlants.get(block).takeBite(topAge)).setValue(HALF, DoubleBlockHalf.UPPER), 2);
                    } else {
                        world.setBlock(blockpos, block.defaultBlockState().setValue(GrowableDoubleHigh.AGE, this.ediblePlants.get(block).takeBite(bottomAge)).setValue(HALF, DoubleBlockHalf.LOWER), 2);
                    }
                }
            } else {
                if (block instanceof BeetrootBlock) {
                    int beetrootAge = blockstate.getValue(BeetrootBlock.AGE);
                    world.setBlock(blockpos, blockstate.setValue(BeetrootBlock.AGE, this.ediblePlants.get(block).takeBite(beetrootAge)), 2);
                } else {
                    int currentAge = blockstate.getValue(CropBlock.AGE);
                    world.setBlock(blockpos, blockstate.setValue(CropBlock.AGE, this.ediblePlants.get(block).takeBite(currentAge)), 2);
                }
            }
        } else if (block instanceof SweetBerryBushBlock) {
            world.setBlock(blockpos, blockstate.setValue(SweetBerryBushBlock.AGE, integer - 1), 2);
        } else if (block instanceof BushBlock) {
            if (block instanceof FlowerBlock) {
                if (block == Blocks.ALLIUM) {
                    world.setBlock(blockpos, ModBlocks.GROWABLE_ALLIUM.defaultBlockState().setValue(GrowablePlant.AGE, this.ediblePlants.get(block).takeBite(7)), 2);
                } else if (block == Blocks.AZURE_BLUET) {
                    world.setBlock(blockpos, ModBlocks.GROWABLE_AZURE_BLUET.defaultBlockState().setValue(GrowablePlant.AGE, this.ediblePlants.get(block).takeBite(7)), 2);
                } else if (block == Blocks.BLUE_ORCHID) {
                    world.setBlock(blockpos, ModBlocks.GROWABLE_BLUE_ORCHID.defaultBlockState().setValue(GrowablePlant.AGE, this.ediblePlants.get(block).takeBite(7)), 2);
                } else if (block == Blocks.CORNFLOWER) {
                    world.setBlock(blockpos, ModBlocks.GROWABLE_CORNFLOWER.defaultBlockState().setValue(GrowablePlant.AGE, this.ediblePlants.get(block).takeBite(7)), 2);
                } else if (block == Blocks.DANDELION) {
                    world.setBlock(blockpos, ModBlocks.GROWABLE_DANDELION.defaultBlockState().setValue(GrowablePlant.AGE, this.ediblePlants.get(block).takeBite(7)), 2);
                } else if (block == Blocks.OXEYE_DAISY) {
                    world.setBlock(blockpos, ModBlocks.GROWABLE_OXEYE_DAISY.defaultBlockState().setValue(GrowablePlant.AGE, this.ediblePlants.get(block).takeBite(7)), 2);
                }
            } else if (block instanceof TallFlowerBlock) {
                if (block == Blocks.ROSE_BUSH) {
                    world.setBlock(blockpos, ModBlocks.GROWABLE_ROSE_BUSH.defaultBlockState().setValue(GrowableDoubleHigh.AGE, 7).setValue(HALF, DoubleBlockHalf.LOWER), 2);
                    world.setBlock(blockpos.above(), ModBlocks.GROWABLE_ROSE_BUSH.defaultBlockState().setValue(GrowableDoubleHigh.AGE, this.ediblePlants.get(block).takeBite(7)).setValue(HALF, DoubleBlockHalf.UPPER), 2);
                } else if (block == Blocks.SUNFLOWER) {
                    world.setBlock(blockpos, ModBlocks.GROWABLE_SUNFLOWER.defaultBlockState().setValue(GrowableDoubleHigh.AGE, 7).setValue(HALF, DoubleBlockHalf.LOWER), 2);
                    world.setBlock(blockpos.above(), ModBlocks.GROWABLE_SUNFLOWER.defaultBlockState().setValue(GrowableDoubleHigh.AGE, this.ediblePlants.get(block).takeBite(7)).setValue(HALF, DoubleBlockHalf.UPPER), 2);
                }
            } else if (block instanceof TallGrassBlock) {
                if (block == Blocks.GRASS) {
                    world.setBlock(blockpos, ModBlocks.GROWABLE_GRASS.defaultBlockState().setValue(GrowablePlant.AGE, this.ediblePlants.get(block).takeBite(7)), 2);
                } else if (block == Blocks.FERN) {
                    world.setBlock(blockpos, ModBlocks.GROWABLE_FERN.defaultBlockState().setValue(GrowablePlant.AGE, this.ediblePlants.get(block).takeBite(7)), 2);
                }
            } else if (block instanceof DoublePlantBlock) {
                if (block == Blocks.TALL_GRASS) {
                    if (world.getBlockState(blockpos.above()).getBlock() == Blocks.TALL_GRASS) {
                        world.setBlock(blockpos, ModBlocks.GROWABLE_TALL_GRASS.defaultBlockState().setValue(GrowableDoubleHigh.AGE, this.ediblePlants.get(block).takeBite(7)).setValue(HALF, DoubleBlockHalf.LOWER), 2);
                        world.setBlock(blockpos.above(), ModBlocks.GROWABLE_TALL_GRASS.defaultBlockState().setValue(GrowableDoubleHigh.AGE, 7).setValue(HALF, DoubleBlockHalf.UPPER), 2);
                    }
                } else if (block == Blocks.LARGE_FERN) {
                    if (world.getBlockState(blockpos.above()).getBlock() == Blocks.LARGE_FERN) {
                        world.setBlock(blockpos, ModBlocks.GROWABLE_LARGE_FERN.defaultBlockState().setValue(GrowableDoubleHigh.AGE, this.ediblePlants.get(block).takeBite(7)).setValue(HALF, DoubleBlockHalf.LOWER), 2);
                        world.setBlock(blockpos.above(), ModBlocks.GROWABLE_LARGE_FERN.defaultBlockState().setValue(GrowableDoubleHigh.AGE, 7).setValue(HALF, DoubleBlockHalf.UPPER), 2);
                    }
                }
            }
        }
    }

    public static class EatValues{
        private int edibleAt = 0;
        private int biteSize = 0;
        private int hungerPoints = 0;

        public EatValues(int edibleAt, int biteSize, int hungerPoints) {
            this.edibleAt = edibleAt;
            this.biteSize = biteSize;
            this.hungerPoints = hungerPoints;
        }

        public boolean isRipeEnough(int age) {
            return age >= this.edibleAt;
        }

        public int getRemainingAge(int plantAgeIn){
            int newAge = plantAgeIn-this.biteSize;
            return newAge < 0 ? 0 : newAge;
        }

        public int takeBite(int maxAge) {
            return maxAge-this.biteSize < 0 ? 0 : maxAge-this.biteSize;
        }

        public int getHungerPoints(int plantAgeIn, int maxAge) {
            if (plantAgeIn == maxAge) {
                return this.hungerPoints;
            } else {
                return this.biteSize <= plantAgeIn ? this.hungerPoints : ((this.biteSize - plantAgeIn) * this.hungerPoints) / this.biteSize;
            }
        }
    }

}
