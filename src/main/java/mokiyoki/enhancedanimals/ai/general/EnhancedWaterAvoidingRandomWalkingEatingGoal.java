package mokiyoki.enhancedanimals.ai.general;

import mokiyoki.enhancedanimals.blocks.UnboundHayBlock;
import mokiyoki.enhancedanimals.capability.hay.HayCapabilityProvider;
import mokiyoki.enhancedanimals.entity.EnhancedAnimal;
import mokiyoki.enhancedanimals.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.pattern.BlockStateMatcher;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.pathfinding.PathType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.function.Predicate;

public class EnhancedWaterAvoidingRandomWalkingEatingGoal extends WaterAvoidingRandomWalkingGoal {

    protected final CreatureEntity creature;
    public final double movementSpeed;
    protected int eatDelay;
    protected int timeoutCounter;
    private int maxStayTicks;
    protected BlockPos destinationBlock = BlockPos.ZERO;
    private boolean isAboveDestination;
    private final int searchLength;
    private final int field_203113_j;
    protected int field_203112_e;
    protected final float probability;
    private final World entityWorld;

    private int eatingGrassTimer;

    protected double x;
    protected double y;
    protected double z;
    protected int executionChance;
    protected boolean mustUpdate;

    private boolean eatingSearch;
    private boolean eating;
    private boolean searchHay;
    private boolean eatingHay;

    //cache to assist with valid pathing locations
    private List<String> invalidBlockPosCache = new ArrayList<>();
    private int expireCacheTimer = 12000;


    private static final Predicate<BlockState> IS_GRASS = BlockStateMatcher.forBlock(Blocks.GRASS);
    private static final Predicate<BlockState> IS_GRASS_BLOCK = BlockStateMatcher.forBlock(Blocks.GRASS_BLOCK);

    public EnhancedWaterAvoidingRandomWalkingEatingGoal(CreatureEntity creature, double speedIn, int length, float probabilityIn, int executionChance, int depth) {
        super(creature, speedIn);
        this.creature = creature; //the entity
        this.movementSpeed = speedIn; //speed to move
        this.searchLength = length; //how long to search
        this.field_203112_e = 0;
        this.field_203113_j = depth;
        this.entityWorld = creature.world;
        this.probability = probabilityIn; //probably to pick block???
        this.executionChance = executionChance; //chance to wander
        this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.JUMP));
    }

    @Override
    public boolean shouldExecute() {
        if (this.creature.isBeingRidden()) {
            return false;
        } else {

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
                if(((EnhancedAnimal)this.creature).getHunger() > 12000) {
                    return eatingRoute();
                }

                int eatingModifier = createEatingModifier(((EnhancedAnimal)creature).getHunger());

                int chanceToEat = (this.creature.isChild() ? 50 : 1000) - eatingModifier;

                if (chanceToEat < 1) {
                    chanceToEat = 1;
                }


                if (this.creature.getRNG().nextInt(chanceToEat) == 0) {
                    return eatingRoute();
                }


                //wander checks
                if (this.creature.getIdleTime() >= 100) {
                    return false;
                }
                if (this.creature.getRNG().nextInt(this.executionChance) != 0) {
                    return false;
                }
//            }

            Vec3d lvt_1_1_ = this.getPosition();
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

    private int createEatingModifier(int hunger) {
        int modifier = hunger / 50;
        if (creature.isChild()) {
            modifier =  modifier / 10;
        }

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
    protected Vec3d getPosition() {
        if (this.creature.isInWaterOrBubbleColumn()) {
            Vec3d vec3d = RandomPositionGenerator.getLandPos(this.creature, 15, 7);
            return vec3d == null ? super.getPosition() : vec3d;
        } else {
            return this.creature.getRNG().nextFloat() >= this.probability ? RandomPositionGenerator.getLandPos(this.creature, 10, 7) : super.getPosition();
        }
    }

    public int getEatingGrassTimer() {
        return this.eatingGrassTimer;
    }

    public boolean shouldContinueExecuting() {
        if (eatingSearch) {
            if (searchHay) {
                if (this.destinationBlock.withinDistance(this.creature.getPositionVec(), 1.0D)) {
                    eatingHay = true;
                    searchHay = false;
                    eatingSearch = false;
                    ((EnhancedAnimal)this.creature).decreaseHunger();
                    this.eatingGrassTimer = 40;
                    this.entityWorld.setEntityState(this.creature, (byte)10);
                    this.creature.getNavigator().clearPath();
                    return true;
                } else {
                    return this.timeoutCounter >= -this.maxStayTicks && this.timeoutCounter <= 300;
                }
            } else if (checkForFood()) {
                //stop looking for food and start eating
                eating = true;
                eatingSearch = false;
                ((EnhancedAnimal)this.creature).decreaseHunger();
                this.eatingGrassTimer = 40;
                this.entityWorld.setEntityState(this.creature, (byte)10);
                this.creature.getNavigator().clearPath();
                return true;
            } else {
                //keep looking for food
                return this.timeoutCounter >= -this.maxStayTicks && this.timeoutCounter <= 300 && this.shouldMoveTo(this.entityWorld, this.destinationBlock);
            }
        } else if (eating || eatingHay) {
            //keep eating till timer up
            return this.eatingGrassTimer > 0;
        } else {
            //keep wandering to wander location
            return !this.creature.getNavigator().noPath();
        }
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting() {
        if (eatingSearch) {
            this.createNavigation();
            this.timeoutCounter = 0;
            this.maxStayTicks = this.creature.getRNG().nextInt(this.creature.getRNG().nextInt(200) + 200) + 200;
        } else {
            //wander
            this.creature.getNavigator().tryMoveToXYZ(this.x, this.y, this.z, this.speed);
        }
    }

    /**
     * Makes task to bypass chance
     */
    public void makeUpdate() {
        this.mustUpdate = true;
    }

    /**
     * Changes task random possibility for execution
     */
    public void setExecutionChance(int newchance) {
        this.executionChance = newchance;
    }

    private boolean checkForFood() {
        BlockPos blockpos = new BlockPos(this.creature);

        //TODO add the predicate for different blocks to eat based on temperaments and animal type.
        if (IS_GRASS.test(this.entityWorld.getBlockState(blockpos))) {
            return true;
        } else {
            return this.entityWorld.getBlockState(blockpos.down()).getBlock() == Blocks.GRASS_BLOCK || this.entityWorld.getBlockState(blockpos.down()).getBlock() == ModBlocks.SparseGrass_Block;
        }
    }


    protected int getEatDelay(CreatureEntity creatureIn) {
        return 200 + creatureIn.getRNG().nextInt(200) - ((EnhancedAnimal)creatureIn).getHunger()/50;
    }

    protected void createNavigation() {
        int y = this.destinationBlock.getY();
        if (!searchHay) {
            y= y+1;
        }
        this.creature.getNavigator().tryMoveToXYZ((double)((float)this.destinationBlock.getX()), (double)(y), (double)((float)this.destinationBlock.getZ()), this.movementSpeed);
    }

    public double getTargetDistanceSq() {
        return 0.0D;
    }

    /**
     * Keep ticking a continuous task that has already been started
     */
    public void tick() {
        if (eatingSearch) {
            if (!this.destinationBlock.up().withinDistance(this.creature.getPositionVec(), this.getTargetDistanceSq())) {
                this.isAboveDestination = false;
                ++this.timeoutCounter;
                if (this.shouldMove()) {
                    this.creature.getNavigator().tryMoveToXYZ((double)((float)this.destinationBlock.getX()), (double)(this.destinationBlock.getY() + 1), (double)((float)this.destinationBlock.getZ()), this.movementSpeed);
                }
            } else {
                this.isAboveDestination = true;
                --this.timeoutCounter;
            }
        } else if (eating) {
            this.eatingGrassTimer = Math.max(0, this.eatingGrassTimer - 1);
            if (this.eatingGrassTimer == 4) {
                BlockPos blockpos = new BlockPos(this.creature);
                if (IS_GRASS.test(this.entityWorld.getBlockState(blockpos))) {
                    if (net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.entityWorld, this.creature)) {
                        this.entityWorld.destroyBlock(blockpos, false);
                    }

                    this.creature.eatGrassBonus();
                } else {
                    BlockPos blockpos1 = blockpos.down();
                    if (this.entityWorld.getBlockState(blockpos1).getBlock() == Blocks.GRASS_BLOCK) {
                        if (net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.entityWorld, this.creature)) {
                            this.entityWorld.playEvent(2001, blockpos1, Block.getStateId(Blocks.GRASS_BLOCK.getDefaultState()));
                            this.entityWorld.setBlockState(blockpos1, ModBlocks.SparseGrass_Block.getDefaultState(), 2);
                        }

                        this.creature.eatGrassBonus();
                    } else if (this.entityWorld.getBlockState(blockpos1).getBlock() == ModBlocks.SparseGrass_Block) {
                        if (net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.entityWorld, this.creature)) {
                            this.entityWorld.playEvent(2001, blockpos1, Block.getStateId(Blocks.GRASS_BLOCK.getDefaultState()));
                            this.entityWorld.setBlockState(blockpos1, Blocks.DIRT.getDefaultState(), 2);
                        }
                    }
                }

            }
        } else if (eatingHay) {
            this.eatingGrassTimer = Math.max(0, this.eatingGrassTimer - 1);
            if (this.eatingGrassTimer == 4) {
                BlockState blockState = this.entityWorld.getBlockState(this.destinationBlock);
                ((UnboundHayBlock)blockState.getBlock()).eatFromBlock(this.entityWorld, blockState, this.destinationBlock);

            }
        }
    }

    public boolean shouldMove() {
        return this.timeoutCounter % 40 == 0;
    }

    protected boolean getIsAboveDestination() {
        return this.isAboveDestination;
    }

    protected boolean searchForDestination() {
        int i = this.searchLength;
        int j = this.field_203113_j;



        if (findIfNearbyHay()) {
            searchHay = true;
            return true;
        }

        BlockPos blockpos = new BlockPos(this.creature);
        BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

        for(int k = this.field_203112_e; k <= j; k = k > 0 ? -k : 1 - k) {
            for(int l = 0; l < i; ++l) {
                for(int i1 = 0; i1 <= l; i1 = i1 > 0 ? -i1 : 1 - i1) {
                    for(int j1 = i1 < l && i1 > -l ? l : 0; j1 <= l; j1 = j1 > 0 ? -j1 : 1 - j1) {
                        blockpos$mutableblockpos.setPos(blockpos).move(i1, k - 1, j1);
                        if (this.creature.isWithinHomeDistanceFromPosition(blockpos$mutableblockpos) && this.shouldMoveTo(this.entityWorld, blockpos$mutableblockpos)) {
                            if (!this.invalidBlockPosCache.contains(blockpos$mutableblockpos.toString()) && isDirectPathBetweenPoints(this.creature.getPositionVec(), new Vec3d(blockpos$mutableblockpos.getX(), blockpos$mutableblockpos.getY(), blockpos$mutableblockpos.getZ()), 0, 1, 0)) {
                                this.destinationBlock = blockpos$mutableblockpos;
                                return true;
                            } else {
                                invalidBlockPosCache.add(blockpos$mutableblockpos.toString());
                            }
                        }
                    }
                }
            }
        }

        return false;
    }

    private boolean findIfNearbyHay() {
        List<BlockPos> hayList = entityWorld.getCapability(HayCapabilityProvider.HAY_CAP, null).orElse(new HayCapabilityProvider()).getAllHayPos();
        double closestDistance = 128;
        boolean found = false;
        for (BlockPos pos : hayList) {
            double distance = pos.distanceSq(creature.getPosition());
            if (distance < closestDistance) {
                if (isDirectPathBetweenPoints(this.creature.getPositionVec(), new Vec3d(pos.getX(), pos.getY(), pos.getZ()), 0, 1, 0)) {
                    closestDistance = distance;
                    this.destinationBlock = pos;
                    found = true;
                }
            }
        }
        return found;
    }

    protected boolean shouldMoveTo(IWorldReader worldIn, BlockPos pos) {
        BlockState blockstate = worldIn.getBlockState(pos);
        return IS_GRASS_BLOCK.test(blockstate);
    }

    public void resetTask() {
        eatingSearch = false;
        eating = false;
        searchHay = false;
        eatingHay = false;
    }

    protected boolean isDirectPathBetweenPoints(Vec3d posVec31, Vec3d posVec32, int sizeX, int sizeY, int sizeZ) {
        int i = MathHelper.floor(posVec31.x);
        int j = MathHelper.floor(posVec31.z);
        double d0 = posVec32.x - posVec31.x;
        double d1 = posVec32.z - posVec31.z;
        double d2 = d0 * d0 + d1 * d1;
        if (d2 < 1.0E-8D) {
            return false;
        } else {
            double d3 = 1.0D / Math.sqrt(d2);
            d0 = d0 * d3;
            d1 = d1 * d3;
//            sizeX = sizeX + 2;
//            sizeZ = sizeZ + 2;
            if (!this.isSafeToStandAt(i, MathHelper.floor(posVec31.y), j, sizeX, sizeY, sizeZ, posVec31, d0, d1)) {
                return false;
            } else {
//                sizeX = sizeX - 2;
                sizeX = sizeX + 1;
//                sizeZ = sizeZ - 2;
                sizeZ = sizeZ + 1;
                double d4 = 1.0D / Math.abs(d0);
                double d5 = 1.0D / Math.abs(d1);
                double d6 = (double)i - posVec31.x;
                double d7 = (double)j - posVec31.z;
                if (d0 >= 0.0D) {
                    ++d6;
                }

                if (d1 >= 0.0D) {
                    ++d7;
                }

                d6 = d6 / d0;
                d7 = d7 / d1;
                int k = d0 < 0.0D ? -1 : 1;
                int l = d1 < 0.0D ? -1 : 1;
                int i1 = MathHelper.floor(posVec32.x);
                int j1 = MathHelper.floor(posVec32.z);
                int k1 = i1 - i;
                int l1 = j1 - j;

                while(k1 * k > 0 || l1 * l > 0) {
                    if (d6 < d7) {
                        d6 += d4;
                        i += k;
                        k1 = i1 - i;
                    } else {
                        d7 += d5;
                        j += l;
                        l1 = j1 - j;
                    }

                    if (!this.isSafeToStandAt(i, MathHelper.floor(posVec31.y), j, sizeX, sizeY, sizeZ, posVec31, d0, d1)) {
                        return false;
                    }
                }

                return true;
            }
        }
    }

    /**
     * Returns true when an entity could stand at a position, including solid blocks under the entire entity.
     */
    private boolean isSafeToStandAt(int x, int y, int z, int sizeX, int sizeY, int sizeZ, Vec3d vec31, double p_179683_8_, double p_179683_10_) {
        int i = x - sizeX / 2;
        int j = z - sizeZ / 2;
        if (!this.isPositionClear(i, y, j, sizeX, sizeY, sizeZ, vec31, p_179683_8_, p_179683_10_)) {
            return false;
        } else {
            for(int k = i; k < i + sizeX; ++k) {
                for(int l = j; l < j + sizeZ; ++l) {
                    double d0 = (double)k + 0.5D - vec31.x;
                    double d1 = (double)l + 0.5D - vec31.z;
                    if (!(d0 * p_179683_8_ + d1 * p_179683_10_ < 0.0D)) {
                        PathNodeType pathnodetype = this.creature.getNavigator().getNodeProcessor().getPathNodeType(this.entityWorld, k, y - 1, l, this.creature, sizeX, sizeY, sizeZ, true, true);
                        if (pathnodetype == PathNodeType.WATER) {
                            return false;
                        }

                        if (pathnodetype == PathNodeType.LAVA) {
                            return false;
                        }

                        if (pathnodetype == PathNodeType.OPEN) {
                            return false;
                        }

                        pathnodetype = this.creature.getNavigator().getNodeProcessor().getPathNodeType(this.entityWorld, k, y, l, this.creature, sizeX, sizeY, sizeZ, true, true);
                        float f = this.creature.getPathPriority(pathnodetype);
                        if (f < 0.0F || f >= 8.0F) {
                            return false;
                        }

                        if (pathnodetype == PathNodeType.DAMAGE_FIRE || pathnodetype == PathNodeType.DANGER_FIRE || pathnodetype == PathNodeType.DAMAGE_OTHER) {
                            return false;
                        }
                    }
                }
            }

            return true;
        }
    }

    /**
     * Returns true if an entity does not collide with any solid blocks at the position.
     */
    private boolean isPositionClear(int x, int y, int z, int sizeX, int sizeY, int sizeZ, Vec3d p_179692_7_, double p_179692_8_, double p_179692_10_) {
        for(BlockPos blockpos : BlockPos.getAllInBoxMutable(new BlockPos(x, y, z), new BlockPos(x + sizeX - 1, y + sizeY - 1, z + sizeZ - 1))) {
            double d0 = (double)blockpos.getX() + 0.5D - p_179692_7_.x;
            double d1 = (double)blockpos.getZ() + 0.5D - p_179692_7_.z;
            if (!(d0 * p_179692_8_ + d1 * p_179692_10_ < 0.0D) && !this.entityWorld.getBlockState(blockpos).allowsMovement(this.entityWorld, blockpos, PathType.LAND)) {
                return false;
            }
        }

        return true;
    }


}
