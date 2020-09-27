package mokiyoki.enhancedanimals.ai.general;

import mokiyoki.enhancedanimals.blocks.GrowableDoubleHigh;
import mokiyoki.enhancedanimals.blocks.GrowablePlant;
import mokiyoki.enhancedanimals.blocks.UnboundHayBlock;
import mokiyoki.enhancedanimals.capability.hay.HayCapabilityProvider;
import mokiyoki.enhancedanimals.entity.EnhancedAnimalAbstract;
import mokiyoki.enhancedanimals.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.pattern.BlockStateMatcher;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IWorldReader;

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

    protected static final Predicate<BlockState> IS_GRASS = BlockStateMatcher.forBlock(Blocks.GRASS);

    protected static final Predicate<BlockState> IS_GRASS_BLOCK = BlockStateMatcher.forBlock(Blocks.GRASS_BLOCK);
    protected static final Predicate<BlockState> IS_SPARSE_GRASS_BLOCK = BlockStateMatcher.forBlock(ModBlocks.SPARSEGRASS_BLOCK);
    protected static final Predicate<BlockState> IS_TALL_GRASS_BLOCK = BlockStateMatcher.forBlock(Blocks.TALL_GRASS);

    @Override
    public boolean shouldExecute() {
        if (this.eanimal.isBeingRidden() || this.eanimal.isAnimalSleeping()) {
            return false;
        } else {

            if (this.waitingTimer > 0) {
                waitingTimer--;
                return false;
            }

            if (this.eanimal.getIdleTime() >= 100) {
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

        BlockPos baseBlockPos = new BlockPos(this.eanimal);
        BlockPos.Mutable mutableblockpos = new BlockPos.Mutable();

        Vec3d directionVec = getDirectionVec();

        int horizontalRange = 10;
        int verticalRange = 3;
        Entity holder = this.eanimal.getLeashHolder();

        if (holder != null) {
            if (holder instanceof PlayerEntity) {
                this.destinationBlock = new BlockPos(this.eanimal.getPosition().getX(), this.eanimal.getPosition().getY()-1, this.eanimal.getPosition().getZ());
                return true;
            }

            horizontalRange = 4;
            verticalRange = 2;
            baseBlockPos = new BlockPos(this.eanimal.getLeashHolder());
            randomSelection = true;
        } else {
            allFoundPos.clear();
            randomSelection = false;
            if(checkSquaresInFront(baseBlockPos, mutableblockpos, directionVec)) {
                this.destinationBlock = mutableblockpos;
                return true;
            }
        }

        mutableblockpos.setPos(baseBlockPos);

        if (randomSelection && !allFoundPos.isEmpty()) {
            this.destinationBlock = allFoundPos.get(ThreadLocalRandom.current().nextInt(0, allFoundPos.size()));
            return true;
        }

        for(int k = 0; k <= verticalRange; k = k > 0 ? -k : 1 - k) {
            for(int l = 0; l < horizontalRange; ++l) {
                for(int i1 = 0; i1 <= l; i1 = i1 > 0 ? -i1 : 1 - i1) {
                    for(int j1 = i1 < l && i1 > -l ? l : 0; j1 <= l; j1 = j1 > 0 ? -j1 : 1 - j1) {
                        mutableblockpos.setPos(baseBlockPos).move(i1, k - 1, j1);
                        if (this.isEdibleBlock(this.eanimal.world, mutableblockpos)) {
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

    private boolean checkSquaresInFront(BlockPos eanimalBlockPos, BlockPos.Mutable mutableblockpos, Vec3d directionVec) {
        if (isEdibleBlock(this.eanimal.world, mutableblockpos.setPos(eanimalBlockPos).move((int)(directionVec.x*2)+1, -1, (int)(directionVec.z*2)+1))) {
            this.destinationBlock = mutableblockpos;
            return true;
        }
        if (isEdibleBlock(this.eanimal.world, mutableblockpos.setPos(eanimalBlockPos).move((int)(directionVec.x*3)+1, -1, (int)(directionVec.z*3)+1))) {
            this.destinationBlock = mutableblockpos;
            return true;
        }

        if (directionVec.x != 0) {
            if (isEdibleBlock(this.eanimal.world, mutableblockpos.setPos(eanimalBlockPos).move((int)directionVec.x+1, -1, -1))) {
                this.destinationBlock = mutableblockpos;
                return true;
            }
            if (isEdibleBlock(this.eanimal.world, mutableblockpos.setPos(eanimalBlockPos).move((int)directionVec.x+1, -1, 1))) {
                this.destinationBlock = mutableblockpos;
                return true;
            }
        } else {
            if (isEdibleBlock(this.eanimal.world, mutableblockpos.setPos(eanimalBlockPos).move(-1, -1, (int)directionVec.z+1))) {
                this.destinationBlock = mutableblockpos;
                return true;
            }
            if (isEdibleBlock(this.eanimal.world, mutableblockpos.setPos(eanimalBlockPos).move(1, -1, (int)directionVec.z+1))) {
                this.destinationBlock = mutableblockpos;
                return true;
            }
        }
        return false;
    }

    private Vec3d getDirectionVec() {
        Vec3d lockingVec = this.eanimal.getLookVec().align(EnumSet.of(Direction.Axis.X, Direction.Axis.Z));
        double x = Math.abs(lockingVec.x);
        double z = Math.abs(lockingVec.z);

        if (x > z) {
            return new Vec3d(lockingVec.x, 0, 0);
        } else {
            return new Vec3d(0, 0, lockingVec.z);
        }
    }

    protected boolean isEdibleBlock(IWorldReader worldIn, BlockPos pos) {
        BlockState blockstate = worldIn.getBlockState(pos);
        if (IS_GRASS_BLOCK.test(blockstate) || IS_GRASS.test(blockstate) || IS_SPARSE_GRASS_BLOCK.test(blockstate) || IS_TALL_GRASS_BLOCK.test(blockstate)) {
            return true;
        }
        return false;
    }

    private boolean findIfNearbyHay() {
        Set<BlockPos> hayList = eanimal.world.getCapability(HayCapabilityProvider.HAY_CAP, null).orElse(new HayCapabilityProvider()).getAllHayPos();
        double closestDistance = 128;
        boolean found = false;
        for (BlockPos pos : hayList) {
            double distance;
            if(this.eanimal.getLeashHolder()!= null) {
                distance = pos.distanceSq(this.eanimal.getLeashHolder().getPosition());
                if (distance < 12) {
                    closestDistance = distance;
                    this.destinationBlock = pos;
                    found = true;
                }
            } else {
                distance = pos.distanceSq(eanimal.getPosition());
                if (distance < closestDistance) {
                    closestDistance = distance;
                    this.destinationBlock = pos;
                    found = true;
                }
            }


        }
        return found;
    }


    public void startExecuting() {
        this.createNavigation();
        this.timeoutCounter = 0;
        this.amountToEat = ThreadLocalRandom.current().nextInt(1,4);
    }

    protected void createNavigation() {
        int y = this.destinationBlock.getY();
        if (!searchHay) {
            y= y+1;
        }
        this.eanimal.getNavigator().tryMoveToXYZ((double)((float)this.destinationBlock.getX()), (double)(y), (double)((float)this.destinationBlock.getZ()), this.movementSpeed);
    }

    @Override
    public boolean shouldContinueExecuting() {
        if (this.timeoutCounter > this.maxTicks) {
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
                if (!this.destinationBlock.withinDistance(this.eanimal.getPositionVec(), getHayBlockTargetDistanceSq())) {
                    if (this.shouldStillMove()) {
                        this.eanimal.getNavigator().tryMoveToXYZ((double)((float)this.destinationBlock.getX()), (double)(this.destinationBlock.getY()), (double)((float)this.destinationBlock.getZ()), this.movementSpeed);
                    }
                } else {
                    eatingHay = true;
                    searchHay = false;
                    eatingSearch = false;
                    this.eanimal.decreaseHunger(hayHungerRestore);
                    this.eatingGrassTimer = 40;
                    this.eanimal.world.setEntityState(this.eanimal, (byte)10);
                    this.eanimal.getNavigator().clearPath();
                }
            } else if (!this.destinationBlock.up().withinDistance(this.eanimal.getPositionVec(), this.getGoundBlockTargetDistanceSq())) {
                if (this.shouldStillMove()) {
                    this.eanimal.getNavigator().tryMoveToXYZ((double)((float)this.destinationBlock.getX()), (double)(this.destinationBlock.getY() + 1), (double)((float)this.destinationBlock.getZ()), this.movementSpeed);
                }
            } else {
                eating = true;
                eatingSearch = false;
                this.eanimal.decreaseHunger(otherHungerRestore);
                this.eatingGrassTimer = 40;
                this.eanimal.world.setEntityState(this.eanimal, (byte)10);
                this.eanimal.getNavigator().clearPath();
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
                BlockState blockState = this.eanimal.world.getBlockState(this.destinationBlock);
                if (blockState.getBlock() instanceof UnboundHayBlock) {
                    ((UnboundHayBlock)blockState.getBlock()).eatFromBlock(this.eanimal.world, blockState, this.destinationBlock);
                } else {
                    //clean up
                    this.eanimal.world.getWorld().getCapability(HayCapabilityProvider.HAY_CAP, null).orElse(new HayCapabilityProvider()).removeHayPos(this.destinationBlock);
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
        BlockPos blockpos = new BlockPos(this.eanimal);
        if (isInEdibleBlock(blockpos)) {
            if (net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.eanimal.world, this.eanimal)) {
                this.eanimal.world.destroyBlock(blockpos, false);
            }
            this.eanimal.eatGrassBonus();
        } else {
            BlockPos blockposDown = blockpos.down();
            if (this.eanimal.world.getBlockState(blockposDown).getBlock() == Blocks.GRASS_BLOCK) {
                if (net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.eanimal.world, this.eanimal)) {
                    this.eanimal.world.playEvent(2001, blockposDown, Block.getStateId(Blocks.GRASS_BLOCK.getDefaultState()));
                    this.eanimal.world.setBlockState(blockposDown, ModBlocks.SPARSEGRASS_BLOCK.getDefaultState(), 2);
                }

                this.eanimal.eatGrassBonus();
            } else if (this.eanimal.world.getBlockState(blockposDown).getBlock() == ModBlocks.SPARSEGRASS_BLOCK) {
                if (net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.eanimal.world, this.eanimal)) {
                    this.eanimal.world.playEvent(2001, blockposDown, Block.getStateId(Blocks.GRASS_BLOCK.getDefaultState()));
                    this.eanimal.world.setBlockState(blockposDown, Blocks.DIRT.getDefaultState(), 2);
                }

                this.eanimal.eatGrassBonus();
            }
        }
    }

    public double getHayBlockTargetDistanceSq() {
        return 2.0D;
    }

    public double getGoundBlockTargetDistanceSq() {
        if (this.eanimal.getLeashed()) {
            return 1.5D;
        }
        return 1.0D;
    }

    private boolean isInEdibleBlock(BlockPos blockpos) {
        BlockState blockState = this.eanimal.world.getBlockState(blockpos);
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

    public void resetTask() {
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

