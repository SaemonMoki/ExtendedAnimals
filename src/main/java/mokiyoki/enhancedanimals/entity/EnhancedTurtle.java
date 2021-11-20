package mokiyoki.enhancedanimals.entity;

import com.google.common.collect.Sets;
import mokiyoki.enhancedanimals.blocks.EnhancedTurtleEggBlock;
import mokiyoki.enhancedanimals.capability.turtleegg.NestCapabilityProvider;
import mokiyoki.enhancedanimals.entity.Genetics.TurtleGeneticsInitialiser;
import mokiyoki.enhancedanimals.init.ModBlocks;
import mokiyoki.enhancedanimals.util.Genes;
import mokiyoki.enhancedanimals.util.Reference;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.Pose;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.entity.ai.goal.BreedGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.entity.ai.goal.RandomWalkingGoal;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.item.ExperienceOrbEntity;
import net.minecraft.entity.item.LeashKnotEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathFinder;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.pathfinding.SwimmerPathNavigator;
import net.minecraft.pathfinding.WalkAndSwimNodeProcessor;
import net.minecraft.stats.Stats;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.GameRules;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.function.Predicate;

public class EnhancedTurtle  extends EnhancedAnimalAbstract {

    private static final DataParameter<BlockPos> HOME_POS = EntityDataManager.createKey(EnhancedTurtle.class, DataSerializers.BLOCK_POS);
    private static final DataParameter<Boolean> HAS_EGG = EntityDataManager.createKey(EnhancedTurtle.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> IS_DIGGING = EntityDataManager.createKey(EnhancedTurtle.class, DataSerializers.BOOLEAN);
    private static final DataParameter<BlockPos> TRAVEL_POS = EntityDataManager.createKey(EnhancedTurtle.class, DataSerializers.BLOCK_POS);
    private static final DataParameter<Boolean> GOING_HOME = EntityDataManager.createKey(EnhancedTurtle.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> TRAVELLING = EntityDataManager.createKey(EnhancedTurtle.class, DataSerializers.BOOLEAN);

    private int isDigging;
    private int sleepTimer;
    private boolean isTempted = false;

    public static final Predicate<LivingEntity> TARGET_DRY_BABY = (p_213616_0_) -> {
        return p_213616_0_.isChild() && !p_213616_0_.isInWater();
    };

    private static final String[] TURTLE_TEXTURES_BASE = new String[] {
            "normal_turtle.png", "albino_turtle.png", "axanthic_turtle.png", "axanthic_albino_turtle.png", "black_turtle.png", "het_melanised_normal.png", "axanthic_black_turtle.png", "het_melanised_axanthic.png"
    };

    private static final String[] TURTLE_TEXTURES_PIBALD = new String[] {
            "","pibald_turtle.png"
    };

    protected static final Ingredient TEMPTATION_ITEMS = Ingredient.fromItems(Blocks.SEAGRASS.asItem());
    private static final Ingredient BREED_ITEMS = Ingredient.fromItems(Blocks.SEAGRASS.asItem());

    private boolean homePosFixer = false;

    public EnhancedTurtle(EntityType<? extends EnhancedTurtle> type, World worldIn) {
        super(type, worldIn, 2, Reference.TURTLE_AUTOSOMAL_GENES_LENGTH, TEMPTATION_ITEMS, BREED_ITEMS, createFoodMap(), false);
        this.setPathPriority(PathNodeType.WATER, 0.0F);
        this.moveController = new EnhancedTurtle.MoveHelperController(this);
        this.stepHeight = 1.0F;
    }

    private static Map<Item, Integer> createFoodMap() {
        return new HashMap() {{
            put(new ItemStack(Blocks.SEAGRASS).getItem(), 6000);
        }};
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(0, new EnhancedTurtle.PanicGoal(this, 1.2D));
        this.goalSelector.addGoal(1, new EnhancedTurtle.MateGoal(this, 1.0D));
        this.goalSelector.addGoal(1, new EnhancedTurtle.LayEggGoal(this, 1.0D));
        this.goalSelector.addGoal(2, new EnhancedTurtle.PlayerTemptGoal(this, 1.1D, Blocks.SEAGRASS.asItem()));
        this.goalSelector.addGoal(3, new EnhancedTurtle.GoToWaterGoal(this, 1.0D));
        this.goalSelector.addGoal(4, new EnhancedTurtle.GoHomeGoal(this, 1.0D));
        this.goalSelector.addGoal(7, new EnhancedTurtle.TravelGoal(this, 1.0D));
        this.goalSelector.addGoal(8, new LookAtGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.addGoal(9, new EnhancedTurtle.WanderGoal(this, 1.0D, 100));
    }

    @Override
    public EntitySize getSize(Pose poseIn) {
        return EntitySize.flexible(1.2F, 0.4F).scale(this.getRenderScale());
    }

    public float getRenderScale() {
        return this.isChild() ? 0.3F : 1.0F;
    }

    public static AttributeModifierMap.MutableAttribute prepareAttributes() {
        return MobEntity.func_233666_p_()
                .createMutableAttribute(Attributes.MAX_HEALTH, 30.0D)
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.25D);
    }

    @Override
    protected PathNavigator createNavigator(World worldIn) {
        return new EnhancedTurtle.Navigator(this, worldIn);
    }

    public boolean isTempted(boolean tempted) {
        this.isTempted = tempted;
        return tempted;
    }

    public boolean getIsTempted() {
        return this.isTempted;
    }

    public void setHome(BlockPos position) {
        this.dataManager.set(HOME_POS, position);
    }

    private BlockPos getHome() {
        return this.dataManager.get(HOME_POS);
    }

    private void setTravelPos(BlockPos position) {
        this.dataManager.set(TRAVEL_POS, position);
    }

    private BlockPos getTravelPos() {
        return this.dataManager.get(TRAVEL_POS);
    }

    public boolean hasEgg() {
        return this.dataManager.get(HAS_EGG) || this.pregnant;
    }

    private void setHasEgg(boolean hasEgg) {
        this.dataManager.set(HAS_EGG, hasEgg);
    }

    public boolean isDigging() {
        return this.dataManager.get(IS_DIGGING);
    }

    private void setDigging(boolean isDigging) {
        this.isDigging = isDigging ? 1 : 0;
        this.dataManager.set(IS_DIGGING, isDigging);
    }

    private boolean isGoingHome() {
        return this.dataManager.get(GOING_HOME);
    }

    private void setGoingHome(boolean isGoingHome) {
        this.dataManager.set(GOING_HOME, isGoingHome);
    }

    private boolean isTravelling() {
        return this.dataManager.get(TRAVELLING);
    }

    private void setTravelling(boolean isTravelling) {
        this.dataManager.set(TRAVELLING, isTravelling);
    }

    public boolean canBeLeashedTo(PlayerEntity player) {
        return false;
    }

    @Override
    public Boolean isAnimalSleeping() {
        if (this.sleeping == null) {
            return false;
        } else if (!this.isInWater() || this.hasEgg() || this.isGoingHome()) {
            return false;
        } else {
            sleeping = this.dataManager.get(SLEEPING);
            return sleeping;
        }
    }

    @Override
    public boolean sleepingConditional() {
        this.sleepTimer = this.sleepTimer > 0 ? this.sleepTimer-- : this.sleepTimer++;
        if (this.sleepTimer == 0) {
            //finished sleeping
            this.sleepTimer = -(this.rand.nextInt(6000)+8000);
        } else if (this.sleepTimer==-1 && !(this.hasEgg() || this.isGoingHome())) {
            //is tired
            this.sleepTimer = this.rand.nextInt(6000)+1200;
        }
        return (this.sleepTimer > 0) && this.awokenTimer == 0 && !this.sleeping;
    }

    @Override
    protected String getSpecies() {
        return "entity.eanimod.enhanced_turtle";
    }

    @Override
    protected int getAdultAge() {
        return 24000;
    }

    @Override
    protected int gestationConfig() {
        return 24000;
    }

    @Override
    protected void incrementHunger() {

    }

    @Override
    protected void runExtraIdleTimeTick() {
        if (this.homePosFixer && this.isAddedToWorld()) {
            this.homePosFixer = false;
            this.setHome(this.getPosition());
        }
    }

    @Override
    protected void lethalGenes() {

    }

    @Override
    public void initilizeAnimalSize() {

    }

    @Override
    protected void createAndSpawnEnhancedChild(World world) {

    }

    @Override
    protected boolean canBePregnant() {
        return false;
    }

    @Override
    protected boolean canLactate() {
        return false;
    }

    public boolean isPushedByWater() {
        return false;
    }

    public boolean canBreatheUnderwater() {
        return true;
    }

    public CreatureAttribute getCreatureAttribute() {
        return CreatureAttribute.WATER;
    }

    /**
     * Get number of ticks, at least during which the living entity will be silent.
     */
    public int getTalkInterval() {
        return 200;
    }

    @Nullable
    protected SoundEvent getAmbientSound() {
        return !this.isInWater() && this.onGround && !this.isChild() ? SoundEvents.ENTITY_TURTLE_AMBIENT_LAND : super.getAmbientSound();
    }

    protected void playSwimSound(float volume) {
        super.playSwimSound(volume * 1.5F);
    }

    protected SoundEvent getSwimSound() {
        return SoundEvents.ENTITY_TURTLE_SWIM;
    }

    @Nullable
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return this.isChild() ? SoundEvents.ENTITY_TURTLE_HURT_BABY : SoundEvents.ENTITY_TURTLE_HURT;
    }

    @Nullable
    protected SoundEvent getDeathSound() {
        return this.isChild() ? SoundEvents.ENTITY_TURTLE_DEATH_BABY : SoundEvents.ENTITY_TURTLE_DEATH;
    }

    protected void playStepSound(BlockPos pos, BlockState blockIn) {
        SoundEvent soundevent = this.isChild() ? SoundEvents.ENTITY_TURTLE_SHAMBLE_BABY : SoundEvents.ENTITY_TURTLE_SHAMBLE;
        this.playSound(soundevent, 0.15F, 1.0F);
    }

    protected float determineNextStepDistance() {
        return this.distanceWalkedOnStepModified + 0.15F;
    }

//    public float getRenderScale() {
//        return this.isChild() ? 0.3F : 1.0F;
//    }

    @OnlyIn(Dist.CLIENT)
    public String getTurtleTexture() {
        if (this.enhancedAnimalTextures.isEmpty()) {
            this.setTexturePaths();
        }

        return getCompiledTextures("enhanced_turtle");

    }

    @OnlyIn(Dist.CLIENT)
    protected void setTexturePaths() {
        if (this.getSharedGenes() != null) {
            int[] gene = getSharedGenes().getAutosomalGenes();
            int base = 0;
            int pibald = 0;

            //"normal_turtle.png", "albino_turtle.png", "axanthic_turtle.png", "xanthic_albino_turtle.png", "black_turtle.png", "het_melanised_normal.png", "het_melanised_xanthic.png"

            if (gene[0] == 1 || gene[1] == 1) {
                //non-albino
                if (gene[2] == 1 || gene[3] == 1 ) {
                    //non-axanthic
                    if (gene[4] == 2 || gene[5] == 2) {
                        //melanized
                        base = gene[4] == gene[5] ? 4 : 5;
                    }
                } else {
                    //axanthic
                    if (gene[4] == 2 || gene[5] == 2) {
                        //melanized
                        base = gene[4] == gene[5] ? 6 : 7;
                    } else {
                        base = 2;
                    }
                }
            } else {
                //albino
                if (gene[2] == 1 || gene[3] == 1 ) {
                    //non-axanthic
                    base = 1;
                } else {
                    //axanthic
                    base = 3;
                }
            }

            if (gene[6] == 2 && gene[7] == 2) {
                pibald = 1;
            }

            addTextureToAnimal(TURTLE_TEXTURES_BASE, base, null);
            addTextureToAnimal(TURTLE_TEXTURES_PIBALD, pibald, i -> i != 0);
        }
    }

    @Override
    protected void setAlphaTexturePaths() {

    }

    @Override
    protected int getPregnancyProgression() {
        return this.hasEgg() ? 10 : 0;
    }

    protected void registerData() {
        super.registerData();
        this.dataManager.register(HOME_POS, BlockPos.ZERO);
        this.dataManager.register(HAS_EGG, false);
        this.dataManager.register(TRAVEL_POS, BlockPos.ZERO);
        this.dataManager.register(GOING_HOME, false);
        this.dataManager.register(TRAVELLING, false);
        this.dataManager.register(IS_DIGGING, false);
    }

    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        compound.putInt("HomePosX", this.getHome().getX());
        compound.putInt("HomePosY", this.getHome().getY());
        compound.putInt("HomePosZ", this.getHome().getZ());
        compound.putBoolean("HasEgg", this.hasEgg() || this.pregnant);
        compound.putInt("TravelPosX", this.getTravelPos().getX());
        compound.putInt("TravelPosY", this.getTravelPos().getY());
        compound.putInt("TravelPosZ", this.getTravelPos().getZ());
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
        int i = compound.getInt("HomePosX");
        int j = compound.getInt("HomePosY");
        int k = compound.getInt("HomePosZ");
        this.setHome(new BlockPos(i, j, k));
        this.setHasEgg(compound.getBoolean("HasEgg"));
        i = compound.getInt("TravelPosX");
        j = compound.getInt("TravelPosY");
        k = compound.getInt("TravelPosZ");
        this.setTravelPos(new BlockPos(i, j, k));
    }

    @Override
    protected void geneFixer() {
        super.geneFixer();
        this.homePosFixer = !this.breed.isEmpty();
    }

    @Nullable
    @Override
    public ILivingEntityData onInitialSpawn(IServerWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason, @Nullable ILivingEntityData livingdata, @Nullable CompoundNBT dataTag) {
        return commonInitialSpawnSetup(worldIn, livingdata, getAdultAge(), 48000, 800000, reason);
    }

    @Override
    protected void setInitialDefaults() {
        super.setInitialDefaults();
        this.setHome(new BlockPos(this.getPosition()));
        this.setTravelPos(BlockPos.ZERO);
    }

    public static boolean canTurtleSpawn(EntityType<EnhancedTurtle> p_223322_0_, IWorld p_223322_1_, SpawnReason reason, BlockPos p_223322_3_, Random p_223322_4_) {
        return p_223322_3_.getY() < p_223322_1_.getSeaLevel() + 4 && EnhancedTurtleEggBlock.hasProperHabitat(p_223322_1_, p_223322_3_) && p_223322_1_.getLightSubtracted(p_223322_3_, 0) > 8;
    }

    @Override
    protected Genes createInitialGenes(IWorld inWorld, BlockPos pos, boolean isDomestic) {
        return new TurtleGeneticsInitialiser().generateNewGenetics(this.world, pos, isDomestic);
    }

    @Override
    protected Genes createInitialBreedGenes(IWorld inWorld, BlockPos pos, String breed) {
        return new TurtleGeneticsInitialiser().generateWithBreed(this.world, pos, breed);
    }

    public void travel(Vector3d travelVector) {
        if (this.isServerWorld() && this.isInWater()) {
            this.moveRelative(0.1F, travelVector);
            this.move(MoverType.SELF, this.getMotion());
            this.setMotion(this.getMotion().scale(0.9D));
            if (this.getAttackTarget() == null && (!this.isGoingHome() || !this.getHome().withinDistance(this.getPositionVec(), 20.0D))) {
                this.setMotion(this.getMotion().add(0.0D, -0.005D, 0.0D));
            }
        } else {
            super.travel(travelVector);
        }

    }

    public void func_241841_a(ServerWorld p_241841_1_, LightningBoltEntity p_241841_2_) {
        this.attackEntityFrom(DamageSource.LIGHTNING_BOLT, Float.MAX_VALUE);
    }

    static class GoHomeGoal extends Goal {
        private final EnhancedTurtle turtle;
        private final double speed;
        private boolean field_203129_c;
        private int field_203130_d;

        GoHomeGoal(EnhancedTurtle turtle, double speedIn) {
            this.turtle = turtle;
            this.speed = speedIn;
        }

        /**
         * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
         * method as well.
         */
        public boolean shouldExecute() {
            if (this.turtle.isChild()) {
                return false;
            } else if (this.turtle.hasEgg()) {
                return true;
            } else if (this.turtle.getRNG().nextInt(700) != 0) {
                return false;
            } else {
                return !this.turtle.getHome().withinDistance(this.turtle.getPositionVec(), 64.0D);
            }
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void startExecuting() {
            this.turtle.setGoingHome(true);
            this.field_203129_c = false;
            this.field_203130_d = 0;
        }

        /**
         * Reset the task's internal state. Called when this task is interrupted by another one
         */
        public void resetTask() {
            this.turtle.setGoingHome(false);
        }

        /**
         * Returns whether an in-progress EntityAIBase should continue executing
         */
        public boolean shouldContinueExecuting() {
            return !this.turtle.getHome().withinDistance(this.turtle.getPositionVec(), 7.0D) && !this.field_203129_c && this.field_203130_d <= 600;
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void tick() {
            BlockPos blockpos = this.turtle.getHome();
            boolean flag = blockpos.withinDistance(this.turtle.getPositionVec(), 16.0D);
            if (flag) {
                ++this.field_203130_d;
            }

            if (this.turtle.getNavigator().noPath()) {
                Vector3d vector3d = Vector3d.copyCenteredHorizontally(blockpos);
                Vector3d vector3d1 = RandomPositionGenerator.findRandomTargetTowardsScaled(this.turtle, 16, 3, vector3d, (double)((float)Math.PI / 10F));
                if (vector3d1 == null) {
                    vector3d1 = RandomPositionGenerator.findRandomTargetBlockTowards(this.turtle, 8, 7, vector3d);
                }

                if (vector3d1 != null && !flag && !this.turtle.world.getBlockState(new BlockPos(vector3d1)).isIn(Blocks.WATER)) {
                    vector3d1 = RandomPositionGenerator.findRandomTargetBlockTowards(this.turtle, 16, 5, vector3d);
                }

                if (vector3d1 == null) {
                    this.field_203129_c = true;
                    return;
                }

                this.turtle.getNavigator().tryMoveToXYZ(vector3d1.x, vector3d1.y, vector3d1.z, this.speed);
            }

        }
    }

    static class GoToWaterGoal extends MoveToBlockGoal {
        private final EnhancedTurtle turtle;

        private GoToWaterGoal(EnhancedTurtle turtle, double speedIn) {
            super(turtle, turtle.isChild() ? 2.0D : speedIn, 24);
            this.turtle = turtle;
            this.field_203112_e = -1;
        }

        /**
         * Returns whether an in-progress EntityAIBase should continue executing
         */
        public boolean shouldContinueExecuting() {
            return !this.turtle.isInWater() && this.timeoutCounter <= 1200 && this.shouldMoveTo(this.turtle.world, this.destinationBlock);
        }

        /**
         * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
         * method as well.
         */
        public boolean shouldExecute() {
            if (this.turtle.isChild() && !this.turtle.isInWater()) {
                return super.shouldExecute();
            } else {
                return !this.turtle.isGoingHome() && !this.turtle.isInWater() && !this.turtle.hasEgg() ? super.shouldExecute() : false;
            }
        }

        public boolean shouldMove() {
            return this.timeoutCounter % 160 == 0;
        }

        /**
         * Return true to set given position as destination
         */
        protected boolean shouldMoveTo(IWorldReader worldIn, BlockPos pos) {
            return worldIn.getBlockState(pos).isIn(Blocks.WATER);
        }
    }

    static class LayEggGoal extends MoveToBlockGoal {
        private final EnhancedTurtle turtle;

        LayEggGoal(EnhancedTurtle turtle, double speedIn) {
            super(turtle, speedIn, 16);
            this.turtle = turtle;
        }

        /**
         * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
         * method as well.
         */
        public boolean shouldExecute() {
            if (this.turtle.mateGenetics==null) {
                this.turtle.setHasEgg(false);
                return false;
            }
            return (this.turtle.hasEgg()) && this.turtle.getHome().withinDistance(this.turtle.getPositionVec(), 9.0D) ? super.shouldExecute() : false;
        }

        /**
         * Returns whether an in-progress EntityAIBase should continue executing
         */
        public boolean shouldContinueExecuting() {
            if (this.turtle.mateGenetics==null) {
                this.turtle.setHasEgg(false);
                return false;
            }
            return super.shouldContinueExecuting() && this.turtle.hasEgg() && this.turtle.getHome().withinDistance(this.turtle.getPositionVec(), 9.0D);
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void tick() {
            super.tick();
            BlockPos blockpos = this.turtle.getPosition();
            if (!this.turtle.isInWater() && this.getIsAboveDestination()) {
                if (this.turtle.isDigging < 1) {
                    this.turtle.setDigging(true);
                } else if (this.turtle.isDigging > 200) {
                    World world = this.turtle.world;
                    world.playSound((PlayerEntity)null, blockpos, SoundEvents.ENTITY_TURTLE_LAY_EGG, SoundCategory.BLOCKS, 0.3F, 0.9F + world.rand.nextFloat() * 0.2F);
                    int numberOfEggs = this.turtle.rand.nextInt(4) + 1;
                    BlockPos pos = this.destinationBlock.up();
                    String mateName = this.turtle.mateName.isEmpty() ? "???" : this.turtle.mateName;
                    String name = this.turtle.hasCustomName() ? this.turtle.getName().getString() : "???";
                    for (int i = 0; i < numberOfEggs;i++) {
                        Genes eggGenes = new Genes(this.turtle.getGenes()).makeChild(true, true, this.turtle.mateGenetics);
                        world.getCapability(NestCapabilityProvider.NEST_CAP, null).orElse(new NestCapabilityProvider()).addNestEggPos(pos, mateName,name, eggGenes);
                    }
                    world.setBlockState(pos, ModBlocks.TURTLE_EGG.getDefaultState().with(EnhancedTurtleEggBlock.EGGS, Integer.valueOf(numberOfEggs)), 3);
                    this.turtle.setHasEgg(false);
                    this.turtle.setDigging(false);
                    this.turtle.setInLove(600);
                }

                if (this.turtle.isDigging()) {
                    this.turtle.isDigging++;
                }
            }

        }

        /**
         * Return true to set given position as destination
         */
        protected boolean shouldMoveTo(IWorldReader worldIn, BlockPos pos) {
            return !worldIn.isAirBlock(pos.up()) ? false : EnhancedTurtleEggBlock.isProperHabitat(worldIn, pos);
        }
    }

    static class MateGoal extends BreedGoal {
        private final EnhancedTurtle turtle;

        MateGoal(EnhancedTurtle turtle, double speedIn) {
            super(turtle, speedIn);
            this.turtle = turtle;
        }

        /**
         * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
         * method as well.
         */
        public boolean shouldExecute() {
            return super.shouldExecute() && !this.turtle.hasEgg();
        }

        /**
         * Spawns a baby animal of the same type.
         */
        protected void spawnBaby() {
            this.turtle.handlePartnerBreeding(this.targetMate);
            if (this.turtle.pregnant) {
                this.turtle.setHasEgg(true);
                this.turtle.pregnant = false;
            }
            if (((EnhancedTurtle) this.targetMate).pregnant) {
                ((EnhancedTurtle) this.targetMate).setHasEgg(true);
                ((EnhancedTurtle) this.targetMate).pregnant = false;
            }
            this.turtle.setGrowingAge(10);
            this.turtle.resetInLove();
            this.targetMate.setGrowingAge(10);
            ((EnhancedAnimalAbstract) this.targetMate).resetInLove();

            ServerPlayerEntity entityplayermp = this.turtle.getLoveCause();
            if (entityplayermp == null && ((EnhancedAnimalAbstract) this.targetMate).getLoveCause() != null) {
                entityplayermp = ((EnhancedAnimalAbstract) this.targetMate).getLoveCause();
            }

            if (entityplayermp != null) {
                entityplayermp.addStat(Stats.ANIMALS_BRED);
                CriteriaTriggers.BRED_ANIMALS.trigger(entityplayermp, this.turtle, ((EnhancedAnimalAbstract) this.targetMate), (AgeableEntity) null);
            }

            Random random = this.animal.getRNG();
            if (this.world.getGameRules().getBoolean(GameRules.DO_MOB_LOOT)) {
                this.world.addEntity(new ExperienceOrbEntity(this.world, this.animal.getPosX(), this.animal.getPosY(), this.animal.getPosZ(), random.nextInt(7) + 1));
            }
        }
    }

    static class MoveHelperController extends MovementController {
        private final EnhancedTurtle turtle;

        MoveHelperController(EnhancedTurtle turtleIn) {
            super(turtleIn);
            this.turtle = turtleIn;
        }

        private void updateSpeed() {
            if (this.turtle.isInWater()) {
                this.turtle.setMotion(this.turtle.getMotion().add(0.0D, 0.005D, 0.0D));
                if (!this.turtle.getHome().withinDistance(this.turtle.getPositionVec(), 16.0D)) {
                    this.turtle.setAIMoveSpeed(Math.max(this.turtle.getAIMoveSpeed() / 2.0F, 0.08F));
                }

                if (this.turtle.isChild()) {
                    this.turtle.setAIMoveSpeed(Math.max(this.turtle.getAIMoveSpeed() / 3.0F, 0.06F));
                }
            } else if (this.turtle.onGround) {
                this.turtle.setAIMoveSpeed(Math.max(this.turtle.getAIMoveSpeed() / 2.0F, 0.06F));
            }

        }

        public void tick() {
            this.updateSpeed();
            if (this.action == MovementController.Action.MOVE_TO && !this.turtle.getNavigator().noPath()) {
                double d0 = this.posX - this.turtle.getPosX();
                double d1 = this.posY - this.turtle.getPosY();
                double d2 = this.posZ - this.turtle.getPosZ();
                double d3 = (double)MathHelper.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
                d1 = d1 / d3;
                float f = (float)(MathHelper.atan2(d2, d0) * (double)(180F / (float)Math.PI)) - 90.0F;
                this.turtle.rotationYaw = this.limitAngle(this.turtle.rotationYaw, f, 90.0F);
                this.turtle.renderYawOffset = this.turtle.rotationYaw;
                float f1 = (float)(this.speed * this.turtle.getAttributeValue(Attributes.MOVEMENT_SPEED));
                this.turtle.setAIMoveSpeed(MathHelper.lerp(0.125F, this.turtle.getAIMoveSpeed(), f1));
                this.turtle.setMotion(this.turtle.getMotion().add(0.0D, (double)this.turtle.getAIMoveSpeed() * d1 * 0.1D, 0.0D));
            } else {
                this.turtle.setAIMoveSpeed(0.0F);
            }
        }
    }

    static class Navigator extends SwimmerPathNavigator {
        Navigator(EnhancedTurtle turtle, World worldIn) {
            super(turtle, worldIn);
        }

        /**
         * If on ground or swimming and can swim
         */
        protected boolean canNavigate() {
            return true;
        }

        protected PathFinder getPathFinder(int p_179679_1_) {
            this.nodeProcessor = new WalkAndSwimNodeProcessor();
            return new PathFinder(this.nodeProcessor, p_179679_1_);
        }

        public boolean canEntityStandOnPos(BlockPos pos) {
            if (this.entity instanceof EnhancedTurtle) {
                EnhancedTurtle turtleentity = (EnhancedTurtle)this.entity;
                if (turtleentity.isTravelling()) {
                    return this.world.getBlockState(pos).isIn(Blocks.WATER);
                }
            }

            return !this.world.getBlockState(pos.down()).isAir();
        }
    }

    static class PanicGoal extends net.minecraft.entity.ai.goal.PanicGoal {
        PanicGoal(EnhancedTurtle turtle, double speedIn) {
            super(turtle, speedIn);
        }

        /**
         * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
         * method as well.
         */
        public boolean shouldExecute() {
            if (this.creature.getRevengeTarget() == null && !this.creature.isBurning()) {
                return false;
            } else {
                BlockPos blockpos = this.getRandPos(this.creature.world, this.creature, 7, 4);
                if (blockpos != null) {
                    this.randPosX = (double)blockpos.getX();
                    this.randPosY = (double)blockpos.getY();
                    this.randPosZ = (double)blockpos.getZ();
                    return true;
                } else {
                    return this.findRandomPosition();
                }
            }
        }
    }

    static class PlayerTemptGoal extends Goal {
        private static final EntityPredicate field_220834_a = (new EntityPredicate()).setDistance(10.0D).allowFriendlyFire().allowInvulnerable();
        private final EnhancedTurtle turtle;
        private final double speed;
        private PlayerEntity tempter;
        private int cooldown;
        private final Set<Item> temptItems;

        PlayerTemptGoal(EnhancedTurtle turtle, double speedIn, Item temptItem) {
            this.turtle = turtle;
            this.speed = speedIn;
            this.temptItems = Sets.newHashSet(temptItem);
            this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        }

        /**
         * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
         * method as well.
         */
        public boolean shouldExecute() {
            if (this.cooldown > 0) {
                --this.cooldown;
                return this.turtle.getHunger() < 3000;
            } else {
                this.tempter = this.turtle.world.getClosestPlayer(field_220834_a, this.turtle);
                if (this.tempter == null) {
                    return false;
                } else {
                    return this.turtle.isTempted(this.isTemptedBy(this.tempter.getHeldItemMainhand()) || this.isTemptedBy(this.tempter.getHeldItemOffhand()));
                }
            }
        }

        private boolean isTemptedBy(ItemStack itemStack) {
            return this.temptItems.contains(itemStack.getItem());
        }

        /**
         * Returns whether an in-progress EntityAIBase should continue executing
         */
        public boolean shouldContinueExecuting() {
            return this.shouldExecute();
        }

        /**
         * Reset the task's internal state. Called when this task is interrupted by another one
         */
        public void resetTask() {
            this.tempter = null;
            this.turtle.getNavigator().clearPath();
            this.turtle.isTempted(false);
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void tick() {
            this.turtle.getLookController().setLookPositionWithEntity(this.tempter, (float)(this.turtle.getHorizontalFaceSpeed() + 20), (float)this.turtle.getVerticalFaceSpeed());
            if (this.turtle.getDistanceSq(this.tempter) < 6.25D) {
                this.turtle.getNavigator().clearPath();
            } else {
                this.turtle.getNavigator().tryMoveToEntityLiving(this.tempter, this.speed);
            }

        }
    }

    static class TravelGoal extends Goal {
        private final EnhancedTurtle turtle;
        private final double speed;
        private boolean field_203139_c;

        TravelGoal(EnhancedTurtle turtle, double speedIn) {
            this.turtle = turtle;
            this.speed = speedIn;
        }

        /**
         * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
         * method as well.
         */
        public boolean shouldExecute() {
            return !this.turtle.isGoingHome() && !this.turtle.hasEgg() && !this.turtle.isAnimalSleeping() && this.turtle.isInWater() && !this.turtle.getIsTempted();
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void startExecuting() {
            int i = 512;
            int j = 4;
            Random random = this.turtle.rand;
            int k = random.nextInt(1025) - 512;
            int l = random.nextInt(9) - 4;
            int i1 = random.nextInt(1025) - 512;
            if ((double)l + this.turtle.getPosY() > (double)(this.turtle.world.getSeaLevel() - 1)) {
                l = 0;
            }

            BlockPos blockpos = new BlockPos((double)k + this.turtle.getPosX(), (double)l + this.turtle.getPosY(), (double)i1 + this.turtle.getPosZ());
            this.turtle.setTravelPos(blockpos);
            this.turtle.setTravelling(true);
            this.field_203139_c = false;
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void tick() {
            if (this.turtle.getNavigator().noPath()) {
                Vector3d vector3d = Vector3d.copyCenteredHorizontally(this.turtle.getTravelPos());
                Vector3d vector3d1 = RandomPositionGenerator.findRandomTargetTowardsScaled(this.turtle, 16, 3, vector3d, (double)((float)Math.PI / 10F));
                if (vector3d1 == null) {
                    vector3d1 = RandomPositionGenerator.findRandomTargetBlockTowards(this.turtle, 8, 7, vector3d);
                }

                if (vector3d1 != null) {
                    int i = MathHelper.floor(vector3d1.x);
                    int j = MathHelper.floor(vector3d1.z);
                    int k = 34;
                    if (!this.turtle.world.isAreaLoaded(i - 34, 0, j - 34, i + 34, 0, j + 34)) {
                        vector3d1 = null;
                    }
                }

                if (vector3d1 == null) {
                    this.field_203139_c = true;
                    return;
                }

                this.turtle.getNavigator().tryMoveToXYZ(vector3d1.x, vector3d1.y, vector3d1.z, this.speed);
            }

        }

        /**
         * Returns whether an in-progress EntityAIBase should continue executing
         */
        public boolean shouldContinueExecuting() {
            return !this.turtle.getNavigator().noPath() && !this.field_203139_c && !this.turtle.isGoingHome() && !this.turtle.isAnimalSleeping() && !this.turtle.isInLove() && !this.turtle.hasEgg() && !this.turtle.getIsTempted();
        }

        /**
         * Reset the task's internal state. Called when this task is interrupted by another one
         */
        public void resetTask() {
            this.turtle.setTravelling(false);
            super.resetTask();
        }
    }

    static class WanderGoal extends RandomWalkingGoal {
        private final EnhancedTurtle turtle;

        private WanderGoal(EnhancedTurtle turtle, double speedIn, int chance) {
            super(turtle, speedIn, chance);
            this.turtle = turtle;
        }

        /**
         * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
         * method as well.
         */
        public boolean shouldExecute() {
            return !this.creature.isInWater() && !this.turtle.isGoingHome() && !this.turtle.hasEgg() && !this.turtle.isAnimalSleeping() && super.shouldExecute();
        }
    }
}
