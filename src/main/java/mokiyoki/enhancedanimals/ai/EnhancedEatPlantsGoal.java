package mokiyoki.enhancedanimals.ai;

import mokiyoki.enhancedanimals.blocks.GrowableDoubleHigh;
import mokiyoki.enhancedanimals.blocks.GrowablePlant;
import mokiyoki.enhancedanimals.entity.EnhancedAnimalAbstract;
import mokiyoki.enhancedanimals.init.ModBlocks;
import net.minecraft.block.BeetrootBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.BushBlock;
import net.minecraft.block.CactusBlock;
import net.minecraft.block.CropsBlock;
import net.minecraft.block.DoublePlantBlock;
import net.minecraft.block.FlowerBlock;
import net.minecraft.block.MelonBlock;
import net.minecraft.block.PumpkinBlock;
import net.minecraft.block.SweetBerryBushBlock;
import net.minecraft.block.TallFlowerBlock;
import net.minecraft.block.TallGrassBlock;
import net.minecraft.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
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

    public boolean shouldExecute() {
        if (this.runDelay <= 0) {
            if (!net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.animal.world, this.animal)) {
                return false;
            }

            this.canEat = false;
            this.wantsToEat = true;
        }

        return animal.getHunger() > 3000 && !animal.isAnimalSleeping() && super.shouldExecute();
    }

    public boolean shouldContinueExecuting() {
        return this.canEat && super.shouldContinueExecuting();
    }

    public void tick() {
        super.tick();
        this.animal.getLookController().setLookPosition((double)this.destinationBlock.getX() + 0.5D, (double)(this.destinationBlock.getY() + 1), (double)this.destinationBlock.getZ() + 0.5D, 10.0F, (float)this.animal.getVerticalFaceSpeed());
        if (this.getIsAboveDestination()) {
            World world = this.animal.world;
            BlockPos blockpos = this.destinationBlock.up();
            if (this.canEat && isEdible(world, blockpos)) {
                BlockState blockState = world.getBlockState(blockpos);
                Block block = blockState.getBlock();
                EatValues blockValues = this.ediblePlants.get(block);
                if (blockValues != null) {
                    Integer integer = getPlantAge(blockState);
                    this.animal.decreaseHunger(blockValues.getHungerPoints(integer, getPlantMaxAge(block)));
                    if (block instanceof MelonBlock || block instanceof PumpkinBlock) {
                        world.setBlockState(blockpos, Blocks.AIR.getDefaultState(), 2);
                        world.destroyBlock(blockpos, false);
                    } else {
                        setNewPlantBlockState(world, blockpos, blockState, block, integer);
                        world.playEvent(2001, blockpos, Block.getStateId(blockState));
                    }
                    this.animal.eatingTicks = ThreadLocalRandom.current().nextInt(30) + 40;
                    this.canEat = false;
                    this.runDelay = 20;
                }
            }
        } else if (this.destinationBlock.withinDistance(this.animal.getPosition(), 2.0D)) {
            World world = this.animal.world;
            BlockPos blockpos = this.destinationBlock.up();
            BlockState blockState = world.getBlockState(blockpos);
            Block block = blockState.getBlock();
            int cactusSize = 0;
            while (world.getBlockState(blockpos.up()).getBlock() == Blocks.CACTUS) {
                blockpos = blockpos.up();
                cactusSize++;
            }
            if ((block instanceof CactusBlock && cactusSize != 0) || block instanceof MelonBlock || block instanceof PumpkinBlock) {
                EatValues blockValues = this.ediblePlants.get(block);
                if (blockValues != null) {
                    this.animal.decreaseHunger(blockValues.hungerPoints);
                    world.setBlockState(blockpos, Blocks.AIR.getDefaultState(), 2);
                    world.destroyBlock(blockpos, false);
                    this.animal.eatingTicks = ThreadLocalRandom.current().nextInt(30) + 40;
                    this.canEat = false;
                    this.runDelay = 20;
                }
            } else if (block instanceof SweetBerryBushBlock) {
                EatValues blockValues = this.ediblePlants.get(block);
                if (blockValues != null) {
                    Integer integer = getPlantAge(blockState);
                    this.animal.decreaseHunger(blockValues.getHungerPoints(integer, getPlantMaxAge(block)));
                    setNewPlantBlockState(world, blockpos, blockState, block, integer);
                    world.playEvent(2001, blockpos, Block.getStateId(blockState));
                    this.animal.eatingTicks = ThreadLocalRandom.current().nextInt(30) + 40;
                    this.canEat = false;
                    this.runDelay = 20;
                }
            }
        }

    }

    protected boolean shouldMoveTo(IWorldReader worldIn, BlockPos pos) {
        if (isEdible(worldIn, pos.up()) && this.wantsToEat && !this.canEat) {
            this.canEat = true;
            return true;
        }

        return false;
    }

    protected boolean isEdible(IWorldReader world, BlockPos pos){
        Block block = world.getBlockState(pos).getBlock();
        BlockState blockState = world.getBlockState(pos);
        if (this.ediblePlants.containsKey(block)) {
            if (block instanceof SweetBerryBushBlock) {
                return this.ediblePlants.get(block).isRipeEnough(blockState.get(SweetBerryBushBlock.AGE));
            } else if (block instanceof CropsBlock) {
                if (block instanceof GrowableDoubleHigh) {
                    if (blockState.get(HALF) == DoubleBlockHalf.UPPER) {
                        return blockState.get(CropsBlock.AGE) != 0;
                    }
                } else if (block instanceof BeetrootBlock) {
                    return this.ediblePlants.get(block).isRipeEnough(blockState.get(BeetrootBlock.BEETROOT_AGE));
                }
                return this.ediblePlants.get(block).isRipeEnough(blockState.get(CropsBlock.AGE));
            } else {
                return true;
            }
        }
        return false;
    }

    protected Integer getPlantAge(BlockState plant) {
        Block plantBlock = plant.getBlock();
        if (plantBlock instanceof BeetrootBlock) {
            return plant.get(BeetrootBlock.BEETROOT_AGE);
        } else if (plantBlock instanceof CropsBlock) {
            return plant.get(CropsBlock.AGE);
        } else if (plantBlock instanceof SweetBerryBushBlock) {
            return plant.get(SweetBerryBushBlock.AGE);
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

    protected void setNewPlantBlockState(World world, BlockPos blockpos, BlockState blockstate, Block block, Integer integer) {
        if (block instanceof CropsBlock) {
            if (block instanceof GrowableDoubleHigh) {
                BlockState topBlockstate = world.getBlockState(blockpos.up());
                if (topBlockstate.getBlock() instanceof GrowableDoubleHigh) {
                    int topAge = topBlockstate.get(CropsBlock.AGE);
                    int bottomAge = blockstate.get(CropsBlock.AGE);
                    if (topAge != 0) {
                        world.setBlockState(blockpos.up(), block.getDefaultState().with(GrowableDoubleHigh.AGE, this.ediblePlants.get(block).takeBite(topAge)).with(HALF, DoubleBlockHalf.UPPER), 2);
                    } else {
                        world.setBlockState(blockpos, block.getDefaultState().with(GrowableDoubleHigh.AGE, this.ediblePlants.get(block).takeBite(bottomAge)).with(HALF, DoubleBlockHalf.LOWER), 2);
                    }
                }
            } else {
                if (block instanceof BeetrootBlock) {
                    int beetrootAge = blockstate.get(BeetrootBlock.BEETROOT_AGE);
                    world.setBlockState(blockpos, blockstate.with(BeetrootBlock.BEETROOT_AGE, this.ediblePlants.get(block).takeBite(beetrootAge)), 2);
                } else {
                    int currentAge = blockstate.get(CropsBlock.AGE);
                    world.setBlockState(blockpos, blockstate.with(CropsBlock.AGE, this.ediblePlants.get(block).takeBite(currentAge)), 2);
                }
            }
        } else if (block instanceof SweetBerryBushBlock) {
            world.setBlockState(blockpos, blockstate.with(SweetBerryBushBlock.AGE, integer - 1), 2);
        } else if (block instanceof BushBlock) {
            if (block instanceof FlowerBlock) {
                if (block == Blocks.ALLIUM) {
                    world.setBlockState(blockpos, ModBlocks.GROWABLE_ALLIUM.getDefaultState().with(GrowablePlant.AGE, this.ediblePlants.get(block).takeBite(7)), 2);
                } else if (block == Blocks.AZURE_BLUET) {
                    world.setBlockState(blockpos, ModBlocks.GROWABLE_AZURE_BLUET.getDefaultState().with(GrowablePlant.AGE, this.ediblePlants.get(block).takeBite(7)), 2);
                } else if (block == Blocks.BLUE_ORCHID) {
                    world.setBlockState(blockpos, ModBlocks.GROWABLE_BLUE_ORCHID.getDefaultState().with(GrowablePlant.AGE, this.ediblePlants.get(block).takeBite(7)), 2);
                } else if (block == Blocks.CORNFLOWER) {
                    world.setBlockState(blockpos, ModBlocks.GROWABLE_CORNFLOWER.getDefaultState().with(GrowablePlant.AGE, this.ediblePlants.get(block).takeBite(7)), 2);
                } else if (block == Blocks.DANDELION) {
                    world.setBlockState(blockpos, ModBlocks.GROWABLE_DANDELION.getDefaultState().with(GrowablePlant.AGE, this.ediblePlants.get(block).takeBite(7)), 2);
                } else if (block == Blocks.OXEYE_DAISY) {
                    world.setBlockState(blockpos, ModBlocks.GROWABLE_OXEYE_DAISY.getDefaultState().with(GrowablePlant.AGE, this.ediblePlants.get(block).takeBite(7)), 2);
                }
            } else if (block instanceof TallFlowerBlock) {
                if (block == Blocks.ROSE_BUSH) {
                    world.setBlockState(blockpos, ModBlocks.GROWABLE_ROSE_BUSH.getDefaultState().with(GrowableDoubleHigh.AGE, 7).with(HALF, DoubleBlockHalf.LOWER), 2);
                    world.setBlockState(blockpos.up(), ModBlocks.GROWABLE_ROSE_BUSH.getDefaultState().with(GrowableDoubleHigh.AGE, this.ediblePlants.get(block).takeBite(7)).with(HALF, DoubleBlockHalf.UPPER), 2);
                } else if (block == Blocks.SUNFLOWER) {
                    world.setBlockState(blockpos, ModBlocks.GROWABLE_SUNFLOWER.getDefaultState().with(GrowableDoubleHigh.AGE, 7).with(HALF, DoubleBlockHalf.LOWER), 2);
                    world.setBlockState(blockpos.up(), ModBlocks.GROWABLE_SUNFLOWER.getDefaultState().with(GrowableDoubleHigh.AGE, this.ediblePlants.get(block).takeBite(7)).with(HALF, DoubleBlockHalf.UPPER), 2);
                }
            } else if (block instanceof TallGrassBlock) {
                if (block == Blocks.GRASS) {
                    world.setBlockState(blockpos, ModBlocks.GROWABLE_GRASS.getDefaultState().with(GrowablePlant.AGE, this.ediblePlants.get(block).takeBite(7)), 2);
                } else if (block == Blocks.FERN) {
                    world.setBlockState(blockpos, ModBlocks.GROWABLE_FERN.getDefaultState().with(GrowablePlant.AGE, this.ediblePlants.get(block).takeBite(7)), 2);
                }
            } else if (block instanceof DoublePlantBlock) {
                if (block == Blocks.TALL_GRASS) {
                    if (world.getBlockState(blockpos.up()).getBlock() == Blocks.TALL_GRASS) {
                        world.setBlockState(blockpos, ModBlocks.GROWABLE_TALL_GRASS.getDefaultState().with(GrowableDoubleHigh.AGE, this.ediblePlants.get(block).takeBite(7)).with(HALF, DoubleBlockHalf.LOWER), 2);
                        world.setBlockState(blockpos.up(), ModBlocks.GROWABLE_TALL_GRASS.getDefaultState().with(GrowableDoubleHigh.AGE, 7).with(HALF, DoubleBlockHalf.UPPER), 2);
                    }
                } else if (block == Blocks.LARGE_FERN) {
                    if (world.getBlockState(blockpos.up()).getBlock() == Blocks.LARGE_FERN) {
                        world.setBlockState(blockpos, ModBlocks.GROWABLE_LARGE_FERN.getDefaultState().with(GrowableDoubleHigh.AGE, this.ediblePlants.get(block).takeBite(7)).with(HALF, DoubleBlockHalf.LOWER), 2);
                        world.setBlockState(blockpos.up(), ModBlocks.GROWABLE_LARGE_FERN.getDefaultState().with(GrowableDoubleHigh.AGE, 7).with(HALF, DoubleBlockHalf.UPPER), 2);
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
