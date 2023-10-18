package mokiyoki.enhancedanimals.ai.general;

import mokiyoki.enhancedanimals.blocks.UnboundHayBlock;
import mokiyoki.enhancedanimals.capability.hay.HayCapabilityProvider;
import mokiyoki.enhancedanimals.entity.EnhancedAnimalAbstract;
import mokiyoki.enhancedanimals.init.ModBlocks;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.predicate.BlockStatePredicate;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

public class EnhancedWaterAvoidingRandomWalkingEatingGoal extends WaterAvoidingRandomStrollGoal {

    protected final PathfinderMob creature;
    public final double movementSpeed;
    protected int eatDelay;
    protected int timeoutCounter;
    private int maxTicks = 200;
    protected BlockPos destinationBlock = BlockPos.ZERO;
    private boolean isAtDestination = false;
    private final int searchLength;
    private final int verticalSearchRange;
    protected int verticalSearchStart;
    protected final float probability;
    protected final Level entityWorld;
    private int hungerModifier;

    private int hayHungerRestore = 6000;
    private int otherHungerRestore = 3000;

    private int eatingGrassTimer;

    protected double x;
    protected double y;
    protected double z;
    protected int wanderExecutionChance;
    protected boolean mustUpdate;

    private boolean eatingSearch;
    private boolean eating;
    private boolean searchHay;
    private boolean eatingHay;

    //cache to assist with valid pathing locations
    private Set<String> invalidBlockPosCache = new HashSet<String>();
    private int expireCacheTimer = 12000;


    protected static final Predicate<BlockState> IS_GRASS = BlockStatePredicate.forBlock(Blocks.GRASS);
    protected static final Predicate<BlockState> IS_GRASS_BLOCK = BlockStatePredicate.forBlock(Blocks.GRASS_BLOCK);
    protected static final Predicate<BlockState> IS_SPARSE_GRASS_BLOCK = BlockStatePredicate.forBlock(ModBlocks.SPARSEGRASS_BLOCK.get());
    protected static final Predicate<BlockState> IS_TALL_GRASS_BLOCK = BlockStatePredicate.forBlock(Blocks.TALL_GRASS);

    public EnhancedWaterAvoidingRandomWalkingEatingGoal(PathfinderMob creature, double speedIn, int length, float probabilityIn, int wanderExecutionChance, int depth, int hungerModifier) {
        super(creature, speedIn);
        this.creature = creature; //the entity
        this.movementSpeed = speedIn; //speed to move
        this.searchLength = length; //how long to search
        this.verticalSearchStart = 0;
        this.verticalSearchRange = depth;
        this.entityWorld = creature.level;
        this.probability = probabilityIn; //probably to pick block???
        this.wanderExecutionChance = wanderExecutionChance; //chance to wander
        this.hungerModifier = hungerModifier; //the amount to divide the hunger timer by to then take away from the 1/1000 chance to eat
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.JUMP));
    }

    @Override
    public boolean canUse() {
        if (this.creature.isVehicle() || ((EnhancedAnimalAbstract)this.creature).isAnimalSleeping() || ((EnhancedAnimalAbstract)this.creature).getAIStatus() == AIStatus.FOCUSED) {
            return false;
        } else {

            if (this.creature.getNoActionTime() >= 100) {
                return false;
            }

            expireCacheTimer--;
            if (expireCacheTimer <= 0) {
                // refresh the cache every half day
                expireCacheTimer = 12000;
                invalidBlockPosCache.clear();
            }

            if (eatDelay > 0) {
                --this.eatDelay;
            }
//            if (!this.mustUpdate) {
                //Todo make this use Temperaments
                if(((EnhancedAnimalAbstract)this.creature).getHunger() > 12000) {
                    return eatingRoute();
                }

                float eatingModifier = createEatingModifier(((EnhancedAnimalAbstract)creature).getHunger());

                float chanceToEat = 1000 - eatingModifier;

                if (chanceToEat < 1) {
                    chanceToEat = 1;
                }


                if (this.creature.getRandom().nextInt(Math.round(chanceToEat)) == 0) {
                    return eatingRoute();
                }


                //wander checks
                if (this.creature.getRandom().nextInt(this.wanderExecutionChance) != 0) {
                    return false;
                }
//            }

            Vec3 lvt_1_1_ = this.getPosition();
            if (lvt_1_1_ == null) {
                return false;
            } else {
                this.x = lvt_1_1_.x;
                this.y = lvt_1_1_.y;
                this.z = lvt_1_1_.z;
//                this.mustUpdate = false;
                eatingSearch = false;
                return true;
            }
        }
    }

    private float createEatingModifier(float hunger) {
        float modifier = hunger / this.hungerModifier;
        return modifier;
    }

    private boolean eatingRoute() {
        if (this.eatDelay > 0) {
            return false;
        } else {
            this.eatDelay = this.getEatDelay(this.creature);
            eatingSearch = true;
            return this.searchForDestination();
        }
    }

    @Nullable
    protected Vec3 getPosition() {
        if (this.creature.isInWaterOrBubble()) {
            Vec3 vec3d = LandRandomPos.getPos(this.creature, 15, 7);
            return vec3d == null ? super.getPosition() : vec3d;
        } else {
            return this.creature.getRandom().nextFloat() >= this.probability ? LandRandomPos.getPos(this.creature, 5, 5) : super.getPosition();
        }
    }

    public int getEatingGrassTimer() {
        return this.eatingGrassTimer;
    }

    public boolean canContinueToUse() {

        if (this.timeoutCounter > this.maxTicks) {
            this.invalidBlockPosCache.add(this.destinationBlock.toString());
            return false;
        }

        if (eatingSearch) {
            if (searchHay) {
                if (this.destinationBlock.closerToCenterThan(this.creature.position(), getHayBlockTargetDistanceSq())) {
                    eatingHay = true;
                    searchHay = false;
                    eatingSearch = false;
                    ((EnhancedAnimalAbstract)this.creature).decreaseHunger(hayHungerRestore);
                    this.eatingGrassTimer = 40;
                    this.entityWorld.broadcastEntityEvent(this.creature, (byte)10);
                    this.creature.getNavigation().stop();
                    return true;
                } else {
                    return true;
                }
            } else if (checkForFood()) {
                //stop looking for food and start eating
                eating = true;
                eatingSearch = false;
                ((EnhancedAnimalAbstract)this.creature).decreaseHunger(otherHungerRestore);
                this.eatingGrassTimer = 40;
                this.entityWorld.broadcastEntityEvent(this.creature, (byte)10);
                this.creature.getNavigation().stop();
                return true;
            } else {
                return true;
            }
        } else if (eating || eatingHay) {
            //keep eating till timer up
            return this.eatingGrassTimer > 0;
        } else {
            //keep wandering to wander location
            return !this.creature.getNavigation().isDone();
        }
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void start() {
        if (eatingSearch) {
            this.createNavigation();
            this.timeoutCounter = 0;
        } else {
            //wander
            this.creature.getNavigation().moveTo(this.x, this.y, this.z, this.speedModifier);
        }
    }

    /**
     * Makes task to bypass chance
     */
    public void trigger() {
        this.mustUpdate = true;
    }

    /**
     * Changes task random possibility for execution
     */
    public void setInterval(int newchance) {
        this.wanderExecutionChance = newchance;
    }

    protected boolean checkForFood() {
        BlockPos blockpos = new BlockPos(this.creature.blockPosition());

        //TODO add the predicate for different blocks to eat based on temperaments and animal type.
        BlockState blockState = this.entityWorld.getBlockState(blockpos);
        if (IS_GRASS.test(blockState) || IS_TALL_GRASS_BLOCK.test(blockState)) {
            return true;
        } else {
            BlockState blockStateDown = this.entityWorld.getBlockState(blockpos.below());
            return blockStateDown.getBlock() == Blocks.GRASS_BLOCK || blockStateDown.getBlock() == ModBlocks.SPARSEGRASS_BLOCK.get();
        }
    }


    protected int getEatDelay(PathfinderMob creatureIn) {
        int delayInt = 300 + creatureIn.getRandom().nextInt(300) - Math.round(((EnhancedAnimalAbstract)creatureIn).getHunger()/50);
        if (delayInt < 0) {
            delayInt = 0;
        }
        return delayInt;
    }

    protected void createNavigation() {
        int y = this.destinationBlock.getY();
        if (!searchHay) {
            y= y+1;
        }
        this.creature.getNavigation().moveTo((double)((float)this.destinationBlock.getX()), (double)(y), (double)((float)this.destinationBlock.getZ()), this.movementSpeed);
    }

    public double getGoundBlockTargetDistanceSq() {
        return 1.0D;
    }

    public double getHayBlockTargetDistanceSq() {
        return 2.0D;
    }

    /**
     * Keep ticking a continuous task that has already been started
     */
    public void tick() {
        if (eatingSearch) {
            ++this.timeoutCounter;
            if(searchHay) {
                if (!this.destinationBlock.closerToCenterThan(this.creature.position(), getHayBlockTargetDistanceSq())) {
                    if (this.shouldMove()) {
                        this.creature.getNavigation().moveTo((double)((float)this.destinationBlock.getX()), (double)(this.destinationBlock.getY()), (double)((float)this.destinationBlock.getZ()), this.movementSpeed);
                    }
                } else {
                    this.isAtDestination = true;
                }
            } else if (!this.destinationBlock.above().closerToCenterThan(this.creature.position(), this.getGoundBlockTargetDistanceSq())) {
                if (this.shouldMove()) {
                    this.creature.getNavigation().moveTo((double)((float)this.destinationBlock.getX()), (double)(this.destinationBlock.getY() + 1), (double)((float)this.destinationBlock.getZ()), this.movementSpeed);
                }
            } else {
                this.isAtDestination = true;
            }
        } else if (eating) {
            this.eatingGrassTimer = Math.max(0, this.eatingGrassTimer - 1);
            if (this.eatingGrassTimer == 4) {
                eatBlocks();
            }
        } else if (eatingHay) {
            this.eatingGrassTimer = Math.max(0, this.eatingGrassTimer - 1);
            if (this.eatingGrassTimer == 4) {
                BlockState blockState = this.entityWorld.getBlockState(this.destinationBlock);
                if (blockState.getBlock() instanceof UnboundHayBlock) {
                    ((UnboundHayBlock)blockState.getBlock()).eatFromBlock(this.entityWorld, blockState, this.destinationBlock);
                } else {
                    //clean up
                    entityWorld.getCapability(HayCapabilityProvider.HAY_CAP, null).orElse(new HayCapabilityProvider()).removeHayPos(this.destinationBlock);
                }
            }
        }
    }

    protected void eatBlocks() {
        BlockPos blockpos = new BlockPos(this.creature.blockPosition());
        if (IS_GRASS.test(this.entityWorld.getBlockState(blockpos)) || IS_TALL_GRASS_BLOCK.test(this.entityWorld.getBlockState(blockpos))) {
            if (net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.entityWorld, this.creature)) {
                this.entityWorld.destroyBlock(blockpos, false);
            }
            this.creature.ate();
        } else {
            BlockPos blockposDown = blockpos.below();
            if (this.entityWorld.getBlockState(blockposDown).getBlock() == Blocks.GRASS_BLOCK) {
                if (net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.entityWorld, this.creature)) {
                    this.entityWorld.levelEvent(2001, blockposDown, Block.getId(Blocks.GRASS_BLOCK.defaultBlockState()));
                    this.entityWorld.setBlock(blockposDown, ModBlocks.SPARSEGRASS_BLOCK.get().defaultBlockState(), 2);
                }

                this.creature.ate();
            } else if (this.entityWorld.getBlockState(blockposDown).getBlock() == ModBlocks.SPARSEGRASS_BLOCK.get()) {
                if (net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.entityWorld, this.creature)) {
                    this.entityWorld.levelEvent(2001, blockposDown, Block.getId(Blocks.GRASS_BLOCK.defaultBlockState()));
                    this.entityWorld.setBlock(blockposDown, Blocks.DIRT.defaultBlockState(), 2);
                }

                this.creature.ate();
            }
        }
    }

    public boolean shouldMove() {
        return this.timeoutCounter % 40 == 0;
    }

    protected boolean searchForDestination() {
        int i = this.searchLength;
        int j = this.verticalSearchRange;

        if (findIfNearbyHay()) {
            searchHay = true;
            this.eatDelay = this.eatDelay*3;
            return true;
        }

        BlockPos blockpos = new BlockPos(this.creature.blockPosition());
        BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

        for(int k = this.verticalSearchStart; k <= j; k = k > 0 ? -k : 1 - k) {
            for(int l = 0; l < i; ++l) {
                for(int i1 = 0; i1 <= l; i1 = i1 > 0 ? -i1 : 1 - i1) {
                    for(int j1 = i1 < l && i1 > -l ? l : 0; j1 <= l; j1 = j1 > 0 ? -j1 : 1 - j1) {
                        blockpos$mutableblockpos.set(blockpos).move(i1, k - 1, j1);
                        if (!invalidBlockPosCache.contains(blockpos$mutableblockpos) && this.creature.isWithinRestriction(blockpos$mutableblockpos) && this.shouldMoveTo(this.entityWorld, blockpos$mutableblockpos)) {
                            this.destinationBlock = blockpos$mutableblockpos;
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }

    private boolean findIfNearbyHay() {
        Set<BlockPos> hayList = entityWorld.getCapability(HayCapabilityProvider.HAY_CAP, null).orElse(new HayCapabilityProvider()).getAllHayPos();
        double closestDistance = 128;
        boolean found = false;
        for (BlockPos pos : hayList) {
            double distance = pos.distSqr(creature.blockPosition());
            if (distance < closestDistance) {
                closestDistance = distance;
                this.destinationBlock = pos;
                found = true;
            }
        }
        return found;
    }

    protected boolean shouldMoveTo(LevelReader worldIn, BlockPos pos) {
        BlockState blockstate = worldIn.getBlockState(pos);
        if (IS_GRASS_BLOCK.test(blockstate) || IS_GRASS.test(blockstate) || IS_SPARSE_GRASS_BLOCK.test(blockstate) || IS_TALL_GRASS_BLOCK.test(blockstate)) {
            return true;
        }
        return false;
    }

    public void stop() {
        eatingSearch = false;
        eating = false;
        searchHay = false;
        eatingHay = false;
        isAtDestination = false;
        timeoutCounter = 0;
    }

}
