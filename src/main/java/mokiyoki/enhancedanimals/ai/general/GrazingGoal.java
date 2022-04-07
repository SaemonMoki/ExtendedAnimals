package mokiyoki.enhancedanimals.ai.general;

import mokiyoki.enhancedanimals.blocks.GrowableDoubleHigh;
import mokiyoki.enhancedanimals.blocks.GrowablePlant;
import mokiyoki.enhancedanimals.blocks.UnboundHayBlock;
import mokiyoki.enhancedanimals.capability.hay.HayCapabilityProvider;
import mokiyoki.enhancedanimals.entity.EnhancedAnimalAbstract;
import mokiyoki.enhancedanimals.init.ModBlocks;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.predicate.BlockStatePredicate;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.LevelReader;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Predicate;

public class GrazingGoal extends Goal {

    protected final EnhancedAnimalAbstract eanimal;
    public final double movementSpeed;
    private int hungerLimit;
    private int amountToEat = 0;

    private boolean eatingSearch;
    private boolean eating;
    private boolean searchHay;
    private boolean eatingHay;

    private int eatingGrassTimer;
    protected int timeoutCounter;
    private int maxTicks = 200;
    private int waitingTimer;

    private int hayHungerRestore = 8000;
    private int otherHungerRestore = 3000;

    protected BlockPos destinationBlock = BlockPos.ZERO;
    boolean randomSelection = false;

    List<BlockPos> allFoundPos = new ArrayList<>();

    public GrazingGoal(EnhancedAnimalAbstract eanimal, double movementSpeed) {
        this.eanimal = eanimal;
        this.movementSpeed = movementSpeed;
        this.hungerLimit = getNewHungerLimit();
    }

    protected static final Predicate<BlockState> IS_GRASS = BlockStatePredicate.forBlock(Blocks.GRASS);
    protected static final Predicate<BlockState> IS_GRASS_BLOCK = BlockStatePredicate.forBlock(Blocks.GRASS_BLOCK);
    protected static final Predicate<BlockState> IS_SPARSE_GRASS_BLOCK = BlockStatePredicate.forBlock(ModBlocks.SPARSEGRASS_BLOCK.get());
    protected static final Predicate<BlockState> IS_TALL_GRASS_BLOCK = BlockStatePredicate.forBlock(Blocks.TALL_GRASS);

    @Override
    public boolean canUse() {
        if (this.eanimal.isVehicle() || this.eanimal.isAnimalSleeping()) {
            return false;
        } else {

            if (this.waitingTimer > 0) {
                waitingTimer--;
                return false;
            }

            if (this.eanimal.getNoActionTime() >= 100) {
                return false;
            }

            if (this.eanimal.getHunger() < hungerLimit) {
                return false;
            }

            return eatingRoute();
        }
    }

    private boolean eatingRoute() {
        boolean foundFood = this.searchForDestination();
        if (foundFood) {
            this.eanimal.setAIStatus(AIStatus.EATING);
            this.eatingSearch = true;
            return true;
        }

        return false;
    }

    protected boolean searchForDestination() {
        if (findIfNearbyHay()) {
            searchHay = true;
            return true;
        }

        BlockPos baseBlockPos = new BlockPos(this.eanimal.blockPosition());
        BlockPos.MutableBlockPos mutableblockpos = new BlockPos.MutableBlockPos();

        Vec3 directionVec = getDirectionVec();

        int horizontalRange = 10;
        int verticalRange = 3;
        Entity holder = this.eanimal.getLeashHolder();

        if (holder != null) {
            if (holder instanceof Player) {
                this.destinationBlock = new BlockPos(this.eanimal.blockPosition().getX(), this.eanimal.blockPosition().getY()-1, this.eanimal.blockPosition().getZ());
                return true;
            }

            horizontalRange = 4;
            verticalRange = 2;
            baseBlockPos = new BlockPos(this.eanimal.getLeashHolder().blockPosition());
            randomSelection = true;
        } else {
            allFoundPos.clear();
            randomSelection = false;
            if(checkSquaresInFront(baseBlockPos, mutableblockpos, directionVec)) {
                this.destinationBlock = mutableblockpos;
                return true;
            }
        }

        mutableblockpos.set(baseBlockPos);

        if (randomSelection && !allFoundPos.isEmpty()) {
            this.destinationBlock = allFoundPos.get(ThreadLocalRandom.current().nextInt(0, allFoundPos.size()));
            return true;
        }

        for(int k = 0; k <= verticalRange; k = k > 0 ? -k : 1 - k) {
            for(int l = 0; l < horizontalRange; ++l) {
                for(int i1 = 0; i1 <= l; i1 = i1 > 0 ? -i1 : 1 - i1) {
                    for(int j1 = i1 < l && i1 > -l ? l : 0; j1 <= l; j1 = j1 > 0 ? -j1 : 1 - j1) {
                        mutableblockpos.set(baseBlockPos).move(i1, k - 1, j1);
                        if (this.isEdibleBlock(this.eanimal.level, mutableblockpos)) {
                            if(randomSelection) {
                                allFoundPos.add(new BlockPos(mutableblockpos));
                            } else {
                                this.destinationBlock = mutableblockpos;
                                return true;
                            }
                        }
                    }
                }
            }
        }

        if (randomSelection) {
            int range = allFoundPos.size();
            if (range > 0) {
                this.destinationBlock = allFoundPos.get(ThreadLocalRandom.current().nextInt(0, range));
                return true;
            }
        }

        this.waitingTimer = 500;
        return false;
    }

    private boolean checkSquaresInFront(BlockPos eanimalBlockPos, BlockPos.MutableBlockPos mutableblockpos, Vec3 directionVec) {
        if (isEdibleBlock(this.eanimal.level, mutableblockpos.set(eanimalBlockPos).move((int)(directionVec.x*2)+1, -1, (int)(directionVec.z*2)+1))) {
            this.destinationBlock = mutableblockpos;
            return true;
        }
        if (isEdibleBlock(this.eanimal.level, mutableblockpos.set(eanimalBlockPos).move((int)(directionVec.x*3)+1, -1, (int)(directionVec.z*3)+1))) {
            this.destinationBlock = mutableblockpos;
            return true;
        }

        if (directionVec.x != 0) {
            if (isEdibleBlock(this.eanimal.level, mutableblockpos.set(eanimalBlockPos).move((int)directionVec.x+1, -1, -1))) {
                this.destinationBlock = mutableblockpos;
                return true;
            }
            if (isEdibleBlock(this.eanimal.level, mutableblockpos.set(eanimalBlockPos).move((int)directionVec.x+1, -1, 1))) {
                this.destinationBlock = mutableblockpos;
                return true;
            }
        } else {
            if (isEdibleBlock(this.eanimal.level, mutableblockpos.set(eanimalBlockPos).move(-1, -1, (int)directionVec.z+1))) {
                this.destinationBlock = mutableblockpos;
                return true;
            }
            if (isEdibleBlock(this.eanimal.level, mutableblockpos.set(eanimalBlockPos).move(1, -1, (int)directionVec.z+1))) {
                this.destinationBlock = mutableblockpos;
                return true;
            }
        }
        return false;
    }

    private Vec3 getDirectionVec() {
        Vec3 lockingVec = this.eanimal.getLookAngle().align(EnumSet.of(Direction.Axis.X, Direction.Axis.Z));
        double x = Math.abs(lockingVec.x);
        double z = Math.abs(lockingVec.z);

        if (x > z) {
            return new Vec3(lockingVec.x, 0, 0);
        } else {
            return new Vec3(0, 0, lockingVec.z);
        }
    }

    protected boolean isEdibleBlock(LevelReader worldIn, BlockPos pos) {
        BlockState blockstate = worldIn.getBlockState(pos);
        if (IS_GRASS_BLOCK.test(blockstate) /*|| IS_GRASS.test(blockstate) || IS_SPARSE_GRASS_BLOCK.test(blockstate) || IS_TALL_GRASS_BLOCK.test(blockstate)*/) {
            return true;
        }
        return false;
    }

    private boolean findIfNearbyHay() {
        Set<BlockPos> hayList = eanimal.level.getCapability(HayCapabilityProvider.HAY_CAP, null).orElse(new HayCapabilityProvider()).getAllHayPos();
        double closestDistance = 128;
        boolean found = false;
        for (BlockPos pos : hayList) {
            double distance;
            if(this.eanimal.getLeashHolder()!= null) {
                distance = pos.distSqr(this.eanimal.getLeashHolder().blockPosition());
                if (distance < 12) {
                    closestDistance = distance;
                    this.destinationBlock = pos;
                    found = true;
                }
            } else {
                distance = pos.distSqr(eanimal.blockPosition());
                if (distance < closestDistance) {
                    closestDistance = distance;
                    this.destinationBlock = pos;
                    found = true;
                }
            }


        }
        return found;
    }


    public void start() {
        this.createNavigation();
        this.timeoutCounter = 0;
        this.amountToEat = ThreadLocalRandom.current().nextInt(1,4);
    }

    protected void createNavigation() {
        int y = this.destinationBlock.getY();
        if (!searchHay) {
            y= y+1;
        }
        this.eanimal.getNavigation().moveTo((double)((float)this.destinationBlock.getX()), (double)(y), (double)((float)this.destinationBlock.getZ()), this.movementSpeed);
    }

    @Override
    public boolean canContinueToUse() {
        if (this.timeoutCounter > this.maxTicks || this.eanimal.isAnimalSleeping()) {
            return false;
        }

        if (isSearching()) {
            return true;
        }

        return false;

    }

    public void tick() {
        if (eatingSearch) {
            ++this.timeoutCounter;
            if(searchHay) {
                if (!this.destinationBlock.closerToCenterThan(this.eanimal.position(), getHayBlockTargetDistanceSq())) {
                    if (this.shouldStillMove()) {
                        this.eanimal.getNavigation().moveTo((double)((float)this.destinationBlock.getX()), (double)(this.destinationBlock.getY()), (double)((float)this.destinationBlock.getZ()), this.movementSpeed);
                    }
                } else {
                    eatingHay = true;
                    searchHay = false;
                    eatingSearch = false;
                    this.eanimal.decreaseHunger(hayHungerRestore);
                    this.eatingGrassTimer = 40;
                    this.eanimal.level.broadcastEntityEvent(this.eanimal, (byte)10);
                    this.eanimal.getNavigation().stop();
                }
            } else if (!this.destinationBlock.above().closerToCenterThan(this.eanimal.position(), this.getGoundBlockTargetDistanceSq())) {
                if (this.shouldStillMove()) {
                    this.eanimal.getNavigation().moveTo((double)((float)this.destinationBlock.getX()), (double)(this.destinationBlock.getY() + 1), (double)((float)this.destinationBlock.getZ()), this.movementSpeed);
                }
            } else {
                eating = true;
                eatingSearch = false;
                this.eanimal.decreaseHunger(otherHungerRestore);
                this.eatingGrassTimer = 40;
                this.eanimal.level.broadcastEntityEvent(this.eanimal, (byte)10);
                this.eanimal.getNavigation().stop();
            }
        } else if (eating) {
            this.eatingGrassTimer = Math.max(0, this.eatingGrassTimer - 1);
            if (this.eatingGrassTimer == 4) {
                eatBlocks();
            } else if (this.eatingGrassTimer == 0) {
                eating = false;
                this.amountToEat--;
                shouldContinueEating();
            }
        } else if (eatingHay) {
            this.eatingGrassTimer = Math.max(0, this.eatingGrassTimer - 1);
            if (this.eatingGrassTimer == 4) {
                BlockState blockState = this.eanimal.level.getBlockState(this.destinationBlock);
                if (blockState.getBlock() instanceof UnboundHayBlock) {
                    ((UnboundHayBlock)blockState.getBlock()).eatFromBlock(this.eanimal.level, blockState, this.destinationBlock);
                } else {
                    //clean up
                    this.eanimal.level.getCapability(HayCapabilityProvider.HAY_CAP, null).orElse(new HayCapabilityProvider()).removeHayPos(this.destinationBlock);
                }
            } else if (this.eatingGrassTimer == 0) {
                eatingHay = false;
            }
        }
    }

    private void shouldContinueEating() {
        if (amountToEat > 0) {
            eatingRoute();
        }
    }

    public int getEatingGrassTimer() {
        return this.eatingGrassTimer;
    }

    public boolean isSearching() {
        return (eatingSearch || searchHay || eating || eatingHay);
    }

    protected void eatBlocks() {
        BlockPos blockpos = new BlockPos(this.eanimal.blockPosition());
        if (isInEdibleBlock(blockpos)) {
            if (net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.eanimal.level, this.eanimal)) {
                this.eanimal.level.destroyBlock(blockpos, false);
            }
            this.eanimal.ate();
        } else {
            BlockPos blockposDown = blockpos.below();
            if (this.eanimal.level.getBlockState(blockposDown).getBlock() == Blocks.GRASS_BLOCK) {
                if (net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.eanimal.level, this.eanimal)) {
                    this.eanimal.level.levelEvent(2001, blockposDown, Block.getId(Blocks.GRASS_BLOCK.defaultBlockState()));
                    this.eanimal.level.setBlock(blockposDown, ModBlocks.SPARSEGRASS_BLOCK.get().defaultBlockState(), 2);
                }

                this.eanimal.ate();
            } else if (this.eanimal.level.getBlockState(blockposDown).getBlock() == ModBlocks.SPARSEGRASS_BLOCK.get()) {
                if (net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.eanimal.level, this.eanimal)) {
                    this.eanimal.level.levelEvent(2001, blockposDown, Block.getId(Blocks.GRASS_BLOCK.defaultBlockState()));
                    this.eanimal.level.setBlock(blockposDown, Blocks.DIRT.defaultBlockState(), 2);
                }

                this.eanimal.ate();
            }
        }
    }

    public double getHayBlockTargetDistanceSq() {
        return 2.0D;
    }

    public double getGoundBlockTargetDistanceSq() {
        if (this.eanimal.isLeashed()) {
            return 1.5D;
        }
        return 1.0D;
    }

    private boolean isInEdibleBlock(BlockPos blockpos) {
        BlockState blockState = this.eanimal.level.getBlockState(blockpos);
        return (IS_GRASS.test(blockState)
                || IS_TALL_GRASS_BLOCK.test(blockState)
                || blockState.getBlock() instanceof GrowablePlant
                || blockState.getBlock() instanceof GrowableDoubleHigh);
    }

    public boolean shouldStillMove() {
        return this.timeoutCounter % 40 == 0;
    }

    private int getNewHungerLimit() {
        return eanimal.getHungerLimit() + ThreadLocalRandom.current().nextInt(1000);
    }

    public void stop() {
        eatingSearch = false;
        eating = false;
        searchHay = false;
        eatingHay = false;
        timeoutCounter = 0;
        amountToEat = 0;
        this.eanimal.setAIStatus(AIStatus.NONE);
        randomSelection = false;
    }

}

