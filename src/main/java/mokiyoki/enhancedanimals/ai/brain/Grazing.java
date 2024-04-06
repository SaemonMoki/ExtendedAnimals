package mokiyoki.enhancedanimals.ai.brain;

import com.google.common.collect.ImmutableMap;
import mokiyoki.enhancedanimals.blocks.GrowableDoubleHigh;
import mokiyoki.enhancedanimals.blocks.GrowablePlant;
import mokiyoki.enhancedanimals.blocks.UnboundHayBlock;
import mokiyoki.enhancedanimals.capability.hay.HayCapabilityProvider;
import mokiyoki.enhancedanimals.entity.EnhancedAnimalAbstract;
import mokiyoki.enhancedanimals.init.ModBlocks;
import mokiyoki.enhancedanimals.init.ModMemoryModuleTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.predicate.BlockStatePredicate;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

public class Grazing extends Behavior<EnhancedAnimalAbstract> {

    private List<BlockPos> eatingDestinations = new ArrayList<>();
    private boolean searchingForFood;
    private boolean eating;
    private boolean seekingHay = false;

    private int eatingGrassTimer;
    protected int pauseAfterEating;
    private long maxTicks = 200L;

    private int hayHungerRestore = 16000;
    private int otherHungerRestore = 3000;



    protected static final Predicate<BlockState> IS_GRASS = BlockStatePredicate.forBlock(Blocks.GRASS);
    protected static final Predicate<BlockState> IS_GRASS_BLOCK = BlockStatePredicate.forBlock(Blocks.GRASS_BLOCK);
    protected static final Predicate<BlockState> IS_SPARSE_GRASS_BLOCK = BlockStatePredicate.forBlock(ModBlocks.SPARSEGRASS_BLOCK.get());
    protected static final Predicate<BlockState> IS_TALL_GRASS_BLOCK = BlockStatePredicate.forBlock(Blocks.TALL_GRASS);

    public Grazing() {
        super(ImmutableMap.of(
                ModMemoryModuleTypes.HUNGRY.get(), MemoryStatus.VALUE_PRESENT,
                ModMemoryModuleTypes.PAUSE_BRAIN.get(), MemoryStatus.VALUE_ABSENT,
                ModMemoryModuleTypes.FOCUS_BRAIN.get(), MemoryStatus.VALUE_ABSENT,
                ModMemoryModuleTypes.SLEEPING.get(), MemoryStatus.VALUE_ABSENT,
                ModMemoryModuleTypes.ROOSTING.get(), MemoryStatus.VALUE_ABSENT,
                ModMemoryModuleTypes.PAUSE_BETWEEN_EATING.get(), MemoryStatus.VALUE_ABSENT
            ),
    200
        );
    }

    protected boolean checkExtraStartConditions(ServerLevel serverLevel, EnhancedAnimalAbstract geneticAnimal) {
        if (geneticAnimal.getNoActionTime() >= 100) {
            return false;
        }

        return foodPathFound(serverLevel, geneticAnimal);
    }

    private boolean foodPathFound(ServerLevel serverLevel, EnhancedAnimalAbstract geneticAnimal) {
        if (findIfNearbyHay(geneticAnimal)) {
            seekingHay = true;
            return true;
        }

        for (int i = 0; i < geneticAnimal.getRandom().nextInt(3)+1; i++) {
            Vec3 randomDirVec = LandRandomPos.getPosAway(geneticAnimal, 10, 7, getDirectionVec(geneticAnimal));
            if (randomDirVec != null && isEdibleBlock(serverLevel, new BlockPos(randomDirVec))) {
                eatingDestinations.add(new BlockPos(randomDirVec));
            }
        }

        return !eatingDestinations.isEmpty();
    }

    @Override
    protected void start(ServerLevel serverLevel, EnhancedAnimalAbstract geneticAnimal, long gameTime) {
        geneticAnimal.getBrain().setMemory(ModMemoryModuleTypes.FOCUS_BRAIN.get(), true);
        this.searchingForFood = true;
        this.pauseAfterEating = 0;
        this.maxTicks = 400 + gameTime;
    }

    @Override
    protected void stop(ServerLevel serverLevel, EnhancedAnimalAbstract geneticAnimal, long gameTime) {
        geneticAnimal.getBrain().eraseMemory(ModMemoryModuleTypes.FOCUS_BRAIN.get());
        geneticAnimal.getBrain().eraseMemory(ModMemoryModuleTypes.HUNGRY.get());
        geneticAnimal.getBrain().setMemory(ModMemoryModuleTypes.PAUSE_BETWEEN_EATING.get(), geneticAnimal.getRandom().nextInt(400, 800));
        this.searchingForFood = false;
        this.seekingHay = false;
        this.eating = false;
    }

    @Override
    protected boolean canStillUse(ServerLevel serverLevel, EnhancedAnimalAbstract geneticAnimal, long gameTime) {
        return pauseAfterEating > 0 || (!eatingDestinations.isEmpty() && maxTicks > gameTime);
    }

    @Override
    protected void tick(ServerLevel serverLevel, EnhancedAnimalAbstract geneticAnimal, long gameTime) {
        BlockPos currentDestination = eatingDestinations.get(0);
        if (searchingForFood) {

            if (!currentDestination.below().closerToCenterThan(geneticAnimal.position(), 1D)) {
                BehaviorUtils.setWalkAndLookTargetMemories(geneticAnimal, currentDestination.below(), 1.0F, getAcceptableDistance());
            } else {
                eating = true;
                searchingForFood = false;
                if (seekingHay) {
                    geneticAnimal.decreaseHunger(hayHungerRestore);
                } else {
                    geneticAnimal.decreaseHunger(otherHungerRestore);
                }
                this.eatingGrassTimer = 40;
                geneticAnimal.level.broadcastEntityEvent(geneticAnimal, (byte)10);
                geneticAnimal.getNavigation().stop();
            }
        } else if (eating) {
            this.eatingGrassTimer = Math.max(0, this.eatingGrassTimer - 1);
            if (this.eatingGrassTimer == 4) {
                if(seekingHay) {
                    BlockState blockState = geneticAnimal.level.getBlockState(currentDestination);
                    if (blockState.getBlock() instanceof UnboundHayBlock) {
                        ((UnboundHayBlock)blockState.getBlock()).eatFromBlock(geneticAnimal.level, blockState, currentDestination);
                    } else {
                        //clean up
                        geneticAnimal.level.getCapability(HayCapabilityProvider.HAY_CAP, null).orElse(new HayCapabilityProvider()).removeHayPos(currentDestination);
                    }
                } else {
                    eatBlocks(geneticAnimal, currentDestination);
                }
            } else if (this.eatingGrassTimer == 0) {
                eating = false;
                eatingDestinations.remove(0);
                if (!eatingDestinations.isEmpty()) {
                    searchingForFood = true;
                }
            }
        }
        super.tick(serverLevel, geneticAnimal, gameTime);
    }

    private boolean findIfNearbyHay(EnhancedAnimalAbstract geneticAnimal) {
        Set<BlockPos> hayList = geneticAnimal.level.getCapability(HayCapabilityProvider.HAY_CAP, null).orElse(new HayCapabilityProvider()).getAllHayPos();
        double closestDistance = 128;
        BlockPos destinationBlock = null;
        for (BlockPos pos : hayList) {
            double distance;
            if(geneticAnimal.getLeashHolder()!= null) {
                distance = pos.distSqr(geneticAnimal.getLeashHolder().blockPosition());
                if (distance < 12) {
                    closestDistance = distance;
                    destinationBlock = pos;
                }
            } else {
                distance = pos.distSqr(geneticAnimal.blockPosition());
                if (distance < closestDistance) {
                    closestDistance = distance;
                    destinationBlock = pos;
                }
            }
        }

        if (destinationBlock != null) {
            if (geneticAnimal.getNavigation().createPath(destinationBlock, 128) != null) {
                eatingDestinations.add(destinationBlock.atY(destinationBlock.getY()+1));
                return true;
            }
        }
        return false;
    }

    protected boolean isEdibleBlock(ServerLevel serverLevel, BlockPos pos) {
        /*|| IS_GRASS.test(blockstate) || IS_SPARSE_GRASS_BLOCK.test(blockstate) || IS_TALL_GRASS_BLOCK.test(blockstate)*/
        return IS_GRASS_BLOCK.test(serverLevel.getBlockState(pos.below()));
    }

    public int getAcceptableDistance() {
        if (seekingHay) {
            return 2;
        }
        return 1;
    }

    private Vec3 getDirectionVec(EnhancedAnimalAbstract geneticAnimal) {
        Vec3 lockingVec = geneticAnimal.getLookAngle().align(EnumSet.of(Direction.Axis.X, Direction.Axis.Z));
        double x = Math.abs(lockingVec.x);
        double z = Math.abs(lockingVec.z);

        if (x > z) {
            return new Vec3(lockingVec.x, 0, 0);
        } else {
            return new Vec3(0, 0, lockingVec.z);
        }
    }

    protected void eatBlocks(EnhancedAnimalAbstract geneticAnimal, BlockPos currentDestination) {
        BlockPos blockpos = new BlockPos(geneticAnimal.blockPosition());
        if (isInEdibleBlock(geneticAnimal, blockpos)) {
            if (net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(geneticAnimal.level, geneticAnimal)) {
                geneticAnimal.level.destroyBlock(blockpos, false);
            }
        } else if (net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(geneticAnimal.level, geneticAnimal)) {
            if (geneticAnimal.level.getBlockState(currentDestination.below()).getBlock() == Blocks.GRASS_BLOCK) {
                eatBlock(geneticAnimal, currentDestination.below(), Block.getId(Blocks.GRASS_BLOCK.defaultBlockState()), ModBlocks.SPARSEGRASS_BLOCK.get().defaultBlockState());
            } else if (geneticAnimal.level.getBlockState(currentDestination.below()).getBlock() == ModBlocks.SPARSEGRASS_BLOCK.get()) {

            }
        }
        geneticAnimal.ate();
    }

    protected void eatBlock(EnhancedAnimalAbstract geneticAnimal, BlockPos currentDestination, int eatenBlock, BlockState newBlock) {
        geneticAnimal.level.levelEvent(2001, currentDestination, eatenBlock);
        geneticAnimal.level.setBlock(currentDestination, newBlock, 2);
    }

    private boolean isInEdibleBlock(EnhancedAnimalAbstract geneticAnimal, BlockPos blockpos) {
        BlockState blockState = geneticAnimal.level.getBlockState(blockpos);
        return (IS_GRASS.test(blockState)
                || IS_TALL_GRASS_BLOCK.test(blockState)
                || blockState.getBlock() instanceof GrowablePlant
                || blockState.getBlock() instanceof GrowableDoubleHigh);
    }
}
