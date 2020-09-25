package mokiyoki.enhancedanimals.ai;

import mokiyoki.enhancedanimals.blocks.GrowableDoubleHigh;
import mokiyoki.enhancedanimals.blocks.GrowablePlant;
import mokiyoki.enhancedanimals.entity.EnhancedAnimalAbstract;
import mokiyoki.enhancedanimals.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.BushBlock;
import net.minecraft.block.CropsBlock;
import net.minecraft.block.FlowerBlock;
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
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class EnhancedEatPlantsGoal extends MoveToBlockGoal {

    private final EnhancedAnimalAbstract animal;
    private boolean wantsToEat;
    private boolean canEat;
    protected boolean eaterIsShort = false;
    protected Map<Block, EatValues> ediblePlants = new HashMap<>();

    public EnhancedEatPlantsGoal(EnhancedAnimalAbstract animalIn) {
        super(animalIn, (double)0.7F, 16);
        this.animal = animalIn;
        this.setEdiblePlants();
    }

    protected void setEdiblePlants(){
        this.ediblePlants.put(Blocks.CARROTS, new EatValues(4, 1, 750));
        this.ediblePlants.put(Blocks.BEETROOTS, new EatValues(2, 1, 750));
        this.ediblePlants.put(Blocks.WHEAT, new EatValues(2, 1, 750));
        this.ediblePlants.put(Blocks.AZURE_BLUET, new EatValues(3, 2, 750));
        this.ediblePlants.put(Blocks.BLUE_ORCHID, new EatValues(7, 2, 375));
        this.ediblePlants.put(Blocks.CORNFLOWER, new EatValues(7, 2, 375));
        this.ediblePlants.put(Blocks.DANDELION, new EatValues(3, 2, 750));
        this.ediblePlants.put(Blocks.OXEYE_DAISY, new EatValues(7, 2, 750));
        this.ediblePlants.put(Blocks.ROSE_BUSH, new EatValues(3, 2, 375));
        this.ediblePlants.put(Blocks.SWEET_BERRY_BUSH, new EatValues(1, 1, 1000));
        this.ediblePlants.put(Blocks.CACTUS, new EatValues(1, 1, 1000));
    }

    public boolean shouldExecute() {
        if (this.runDelay <= 0) {
            if (!net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.animal.world, this.animal)) {
                return false;
            }

            this.canEat = false;
            this.wantsToEat = this.animal.isPlantEaten();
            this.wantsToEat = true;
        }

        return super.shouldExecute();
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
                if (!this.eaterIsShort && isEdible(world, blockpos.up())) {
                    blockpos = blockpos.up();
                }
                BlockState iblockstate = world.getBlockState(blockpos);
                Block block = iblockstate.getBlock();
                Integer integer = getPlantAge(iblockstate);
                this.animal.decreaseHunger(this.ediblePlants.get(block).getHungerPoints(integer, getPlantMaxAge(block)));
                if (integer == 0) {
                    while (world.getBlockState(blockpos).getBlock() == Blocks.CACTUS) { blockpos = blockpos.up(); }
                    world.setBlockState(blockpos, Blocks.AIR.getDefaultState(), 2);
                    world.destroyBlock(blockpos, true);
                } else {
                    setNewPlantBlockState(world, blockpos, iblockstate, block, integer);
                    world.playEvent(2001, blockpos, Block.getStateId(iblockstate));
                }
                this.animal.eatingTicks = ThreadLocalRandom.current().nextInt(30)+40;
            }

            this.canEat = false;
            this.runDelay = 10;
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
                return this.ediblePlants.get(block).isRipeEnough(blockState.get(CropsBlock.AGE));
            } else {
                return true;
            }
        } else if (block instanceof GrowablePlant) {
            block = Block.getBlockFromItem(((GrowablePlant) block).getSeedsItem().asItem());
            return this.ediblePlants.get(block).isRipeEnough(blockState.get(GrowableDoubleHigh.AGE));
        } else if (block instanceof GrowableDoubleHigh) {
            block = Block.getBlockFromItem(((GrowableDoubleHigh)block).getSeedsItem().asItem());
            return this.ediblePlants.get(block).isRipeEnough(blockState.get(GrowableDoubleHigh.AGE));
        }
        return false;
    }

    protected Integer getPlantAge(BlockState plant) {
        Block plantBlock = plant.getBlock();
        if (plantBlock instanceof CropsBlock) {
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

    protected void setNewPlantBlockState(World world, BlockPos blockpos, BlockState blockstate,Block block, Integer integer) {
        if (block instanceof CropsBlock) {
            world.setBlockState(blockpos, blockstate.with(CropsBlock.AGE, this.ediblePlants.get(block).takeBite(getPlantMaxAge(block))), 2);
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
                    if (world.getBlockState(blockpos.up()).getBlock() == Blocks.ROSE_BUSH) {
                        world.setBlockState(blockpos, ModBlocks.GROWABLE_ROSE_BUSH.getDefaultState().with(GrowableDoubleHigh.AGE, this.ediblePlants.get(block).takeBite(7)).with(GrowableDoubleHigh.HALF, DoubleBlockHalf.LOWER), 2);
                        world.setBlockState(blockpos.up(), ModBlocks.GROWABLE_ROSE_BUSH.getDefaultState().with(GrowableDoubleHigh.AGE, 7).with(GrowableDoubleHigh.HALF, DoubleBlockHalf.UPPER), 2);
                    } else {
                        world.setBlockState(blockpos, ModBlocks.GROWABLE_ROSE_BUSH.getDefaultState().with(GrowableDoubleHigh.AGE, 7).with(GrowableDoubleHigh.HALF, DoubleBlockHalf.LOWER), 2);
                        world.setBlockState(blockpos.up(), ModBlocks.GROWABLE_ROSE_BUSH.getDefaultState().with(GrowableDoubleHigh.AGE, this.ediblePlants.get(block).takeBite(7)).with(GrowableDoubleHigh.HALF, DoubleBlockHalf.UPPER), 2);
                    }
                } else if (block == Blocks.SUNFLOWER) {
                    if (world.getBlockState(blockpos.up()).getBlock() == Blocks.SUNFLOWER) {
                        world.setBlockState(blockpos, ModBlocks.GROWABLE_SUNFLOWER.getDefaultState().with(GrowableDoubleHigh.AGE, this.ediblePlants.get(block).takeBite(7)).with(GrowableDoubleHigh.HALF, DoubleBlockHalf.LOWER), 2);
                        world.setBlockState(blockpos.up(), ModBlocks.GROWABLE_SUNFLOWER.getDefaultState().with(GrowableDoubleHigh.AGE, 7).with(GrowableDoubleHigh.HALF, DoubleBlockHalf.UPPER), 2);
                    } else {
                        world.setBlockState(blockpos, ModBlocks.GROWABLE_SUNFLOWER.getDefaultState().with(GrowableDoubleHigh.AGE, 7).with(GrowableDoubleHigh.HALF, DoubleBlockHalf.LOWER), 2);
                        world.setBlockState(blockpos.up(), ModBlocks.GROWABLE_SUNFLOWER.getDefaultState().with(GrowableDoubleHigh.AGE, this.ediblePlants.get(block).takeBite(7)).with(GrowableDoubleHigh.HALF, DoubleBlockHalf.UPPER), 2);
                    }
                }
            } else if (block instanceof TallGrassBlock) {
                if (block == Blocks.TALL_GRASS) {
                    if (world.getBlockState(blockpos.up()).getBlock() == Blocks.TALL_GRASS) {
                        world.setBlockState(blockpos, ModBlocks.GROWABLE_TALL_GRASS.getDefaultState().with(GrowableDoubleHigh.AGE, this.ediblePlants.get(block).takeBite(7)).with(GrowableDoubleHigh.HALF, DoubleBlockHalf.LOWER), 2);
                        world.setBlockState(blockpos.up(), ModBlocks.GROWABLE_TALL_GRASS.getDefaultState().with(GrowableDoubleHigh.AGE, 7).with(GrowableDoubleHigh.HALF, DoubleBlockHalf.UPPER), 2);
                    } else {
                        world.setBlockState(blockpos, ModBlocks.GROWABLE_TALL_GRASS.getDefaultState().with(GrowableDoubleHigh.AGE, 7).with(GrowableDoubleHigh.HALF, DoubleBlockHalf.LOWER), 2);
                        world.setBlockState(blockpos.up(), ModBlocks.GROWABLE_TALL_GRASS.getDefaultState().with(GrowableDoubleHigh.AGE, this.ediblePlants.get(block).takeBite(7)).with(GrowableDoubleHigh.HALF, DoubleBlockHalf.UPPER), 2);
                    }
                } else if (block == Blocks.LARGE_FERN) {
                    if (world.getBlockState(blockpos.up()).getBlock() == Blocks.LARGE_FERN) {
                        world.setBlockState(blockpos, ModBlocks.GROWABLE_LARGE_FERN.getDefaultState().with(GrowableDoubleHigh.AGE, this.ediblePlants.get(block).takeBite(7)).with(GrowableDoubleHigh.HALF, DoubleBlockHalf.LOWER), 2);
                        world.setBlockState(blockpos.up(), ModBlocks.GROWABLE_LARGE_FERN.getDefaultState().with(GrowableDoubleHigh.AGE, 7).with(GrowableDoubleHigh.HALF, DoubleBlockHalf.UPPER), 2);
                    } else {
                        world.setBlockState(blockpos, ModBlocks.GROWABLE_LARGE_FERN.getDefaultState().with(GrowableDoubleHigh.AGE, 7).with(GrowableDoubleHigh.HALF, DoubleBlockHalf.LOWER), 2);
                        world.setBlockState(blockpos.up(), ModBlocks.GROWABLE_LARGE_FERN.getDefaultState().with(GrowableDoubleHigh.AGE, this.ediblePlants.get(block).takeBite(7)).with(GrowableDoubleHigh.HALF, DoubleBlockHalf.UPPER), 2);
                    }
                }
            } else if (block == Blocks.GRASS) {
                world.setBlockState(blockpos, ModBlocks.GROWABLE_GRASS.getDefaultState().with(GrowablePlant.AGE, this.ediblePlants.get(block).takeBite(7)), 2);
            } else if (block == Blocks.FERN) {
                world.setBlockState(blockpos, ModBlocks.GROWABLE_FERN.getDefaultState().with(GrowablePlant.AGE, this.ediblePlants.get(block).takeBite(7)), 2);
            }
        }
    }

    protected class EatValues{
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
