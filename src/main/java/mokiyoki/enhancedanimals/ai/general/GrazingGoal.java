package mokiyoki.enhancedanimals.ai.general;

import mokiyoki.enhancedanimals.blocks.GrowableDoubleHigh;
import mokiyoki.enhancedanimals.blocks.GrowablePlant;
import mokiyoki.enhancedanimals.blocks.UnboundHayBlock;
import mokiyoki.enhancedanimals.capability.hay.HayCapabilityProvider;
import mokiyoki.enhancedanimals.entity.EnhancedAnimal;
import mokiyoki.enhancedanimals.entity.EnhancedAnimalAbstract;
import mokiyoki.enhancedanimals.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.pattern.BlockStateMatcher;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IWorldReader;

import java.util.EnumSet;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Predicate;

public class GrazingGoal extends Goal {

    protected final EnhancedAnimalAbstract eanimal;
    public final double movementSpeed;
    private int hungerLimit;
    private boolean finishedGrazing;
    private int amountEating = 0;

    private boolean eatingSearch;
    private boolean eating;
    private boolean searchHay;
    private boolean eatingHay;

    private int eatingGrassTimer;
    protected int timeoutCounter;
    private int maxTicks = 200;

    private int hayHungerRestore = 6000;
    private int otherHungerRestore = 3000;

    protected BlockPos destinationBlock = BlockPos.ZERO;

    public GrazingGoal(EnhancedAnimalAbstract eanimal, double movementSpeed) {
        this.eanimal = eanimal;
        this.movementSpeed = movementSpeed;
        this.hungerLimit = getNewHungerLimit();
    }

    protected static final Predicate<BlockState> IS_GRASS = BlockStateMatcher.forBlock(Blocks.GRASS);

    protected static final Predicate<BlockState> IS_GRASS_BLOCK = BlockStateMatcher.forBlock(Blocks.GRASS_BLOCK);
    protected static final Predicate<BlockState> IS_SPARSE_GRASS_BLOCK = BlockStateMatcher.forBlock(ModBlocks.SPARSE_GRASS_BLOCK);
    protected static final Predicate<BlockState> IS_TALL_GRASS_BLOCK = BlockStateMatcher.forBlock(Blocks.TALL_GRASS);

    @Override
    public boolean shouldExecute() {
        if (this.eanimal.isBeingRidden() || this.eanimal.isAnimalSleeping()) {
            return false;
        } else {

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
        this.eatingSearch = true;
        return this.searchForDestination();
    }

    protected boolean searchForDestination() {
        if (findIfNearbyHay()) {
            searchHay = true;
            return true;
        }

        BlockPos eanimalBlockPos = new BlockPos(this.eanimal);
        BlockPos.Mutable mutableblockpos = new BlockPos.Mutable();

        Vec3d directionVec = getDirectionVec();

        if (isEdibleBlock(this.eanimal.world, mutableblockpos.setPos(eanimalBlockPos).move((int)directionVec.x, -1, (int)directionVec.z))) {
            this.destinationBlock = mutableblockpos;
            return true;
        }

        mutableblockpos.setPos(eanimalBlockPos);
        if(checkSquaresInFront(eanimalBlockPos, mutableblockpos, directionVec)) {
            this.destinationBlock = mutableblockpos;
            return true;
        }

        for(int k = 0; k <= 3; k = k > 0 ? -k : 1 - k) {
            for(int l = 0; l < 10; ++l) {
                for(int i1 = 0; i1 <= l; i1 = i1 > 0 ? -i1 : 1 - i1) {
                    for(int j1 = i1 < l && i1 > -l ? l : 0; j1 <= l; j1 = j1 > 0 ? -j1 : 1 - j1) {
                        mutableblockpos.setPos(eanimalBlockPos).move(i1, k - 1, j1);
                        if (this.isEdibleBlock(this.eanimal.world, mutableblockpos)) {
                            this.destinationBlock = mutableblockpos;
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }

    private boolean checkSquaresInFront(BlockPos eanimalBlockPos, BlockPos.Mutable mutableblockpos, Vec3d directionVec) {
        if (isEdibleBlock(this.eanimal.world, mutableblockpos.setPos(eanimalBlockPos).move((int)directionVec.x*2, -1, (int)directionVec.z*2))) {
            this.destinationBlock = mutableblockpos;
            return true;
        }
        if (isEdibleBlock(this.eanimal.world, mutableblockpos.setPos(eanimalBlockPos).move((int)directionVec.x*3, -1, (int)directionVec.z*3))) {
            this.destinationBlock = mutableblockpos;
            return true;
        }

        if (directionVec.x != 0) {
            if (isEdibleBlock(this.eanimal.world, mutableblockpos.setPos(eanimalBlockPos).move((int)directionVec.x, -1, -1))) {
                this.destinationBlock = mutableblockpos;
                return true;
            }
            if (isEdibleBlock(this.eanimal.world, mutableblockpos.setPos(eanimalBlockPos).move((int)directionVec.x, -1, 1))) {
                this.destinationBlock = mutableblockpos;
                return true;
            }
        } else {
            if (isEdibleBlock(this.eanimal.world, mutableblockpos.setPos(eanimalBlockPos).move(-1, -1, (int)directionVec.z))) {
                this.destinationBlock = mutableblockpos;
                return true;
            }
            if (isEdibleBlock(this.eanimal.world, mutableblockpos.setPos(eanimalBlockPos).move(1, -1, (int)directionVec.z))) {
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
        double closestDistance = 32;
        boolean found = false;
        for (BlockPos pos : hayList) {
            double distance = pos.distanceSq(eanimal.getPosition());
            if (distance < closestDistance) {
                closestDistance = distance;
                this.destinationBlock = pos;
                found = true;
            }
        }
        return found;
    }


    public void startExecuting() {
        this.createNavigation();
        this.timeoutCounter = 0;
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

        if (eatingSearch || searchHay || eating || eatingHay) {
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
            }else if (this.eatingGrassTimer == 0) {
                eatingHay = false;
            }
        }
    }

    private void shouldContinueEating() {


    }

    public int getEatingGrassTimer() {
        return this.eatingGrassTimer;
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
                    this.eanimal.world.setBlockState(blockposDown, ModBlocks.SPARSE_GRASS_BLOCK.getDefaultState(), 2);
                }

                this.eanimal.eatGrassBonus();
            } else if (this.eanimal.world.getBlockState(blockposDown).getBlock() == ModBlocks.SPARSE_GRASS_BLOCK) {
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
        amountEating = 0;-
    }

}

