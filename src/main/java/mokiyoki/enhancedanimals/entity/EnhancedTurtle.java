package mokiyoki.enhancedanimals.entity;

import mokiyoki.enhancedanimals.blocks.EnhancedTurtleEggBlock;
import mokiyoki.enhancedanimals.capability.nestegg.NestCapabilityProvider;
import mokiyoki.enhancedanimals.config.EanimodCommonConfig;
import mokiyoki.enhancedanimals.entity.genetics.TurtleGeneticsInitialiser;
import mokiyoki.enhancedanimals.init.FoodSerialiser;
import mokiyoki.enhancedanimals.init.ModBlocks;
import mokiyoki.enhancedanimals.util.Genes;
import mokiyoki.enhancedanimals.util.Reference;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.level.pathfinder.AmphibiousNodeEvaluator;
import net.minecraft.world.level.pathfinder.PathFinder;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
import net.minecraft.stats.Stats;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.Random;
import java.util.function.Predicate;

import static mokiyoki.enhancedanimals.init.FoodSerialiser.turtleFoodMap;

public class EnhancedTurtle  extends EnhancedAnimalAbstract {

    private static final EntityDataAccessor<BlockPos> HOME_POS = SynchedEntityData.defineId(EnhancedTurtle.class, EntityDataSerializers.BLOCK_POS);
    private static final EntityDataAccessor<Boolean> HAS_EGG = SynchedEntityData.defineId(EnhancedTurtle.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> IS_DIGGING = SynchedEntityData.defineId(EnhancedTurtle.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<BlockPos> TRAVEL_POS = SynchedEntityData.defineId(EnhancedTurtle.class, EntityDataSerializers.BLOCK_POS);
    private static final EntityDataAccessor<Boolean> GOING_HOME = SynchedEntityData.defineId(EnhancedTurtle.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> TRAVELLING = SynchedEntityData.defineId(EnhancedTurtle.class, EntityDataSerializers.BOOLEAN);

    private int isDigging;
    private int sleepTimer;
    private boolean isTempted = false;
    private boolean homePosFixer = false;
    private boolean hasScute = false;

    public static final Predicate<LivingEntity> TARGET_DRY_BABY = (turtle) -> {
        return turtle.isBaby() && !turtle.isInWater();
    };

    private static final String[] TURTLE_TEXTURES_BASE = new String[] {
            "normal_turtle.png", "albino_turtle.png", "axanthic_turtle.png", "axanthic_albino_turtle.png", "black_turtle.png", "het_melanised_normal.png", "axanthic_black_turtle.png", "het_melanised_axanthic.png"
    };

    private static final String[] TURTLE_TEXTURES_PIBALD = new String[] {
            "","pibald_turtle.png", "pibald_turtle1.png", "pibald_turtle2.png", "pibald_turtle3.png",
            "pibald_turtle.png", "pibald_turtle1.png", "pibald_turtle2.png", "pibald_turtle3.png",
            "pibald_turtle.png", "pibald_turtle1.png", "pibald_turtle2.png", "pibald_turtle3.png",
            "pibald_turtle.png", "pibald_turtle1.png", "pibald_turtle2.png", "pibald_turtle3.png"
    };

    public EnhancedTurtle(EntityType<? extends EnhancedTurtle> type, Level worldIn) {
        super(type, worldIn, 2, Reference.TURTLE_AUTOSOMAL_GENES_LENGTH, false);
        this.setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
        this.moveControl = new EnhancedTurtle.MoveHelperController(this);
        this.maxUpStep = 1.0F;
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(0, new EnhancedTurtle.PanicGoal(this, 1.2D));
        this.goalSelector.addGoal(1, new EnhancedTurtle.MateGoal(this, 1.0D));
        this.goalSelector.addGoal(1, new EnhancedTurtle.LayEggGoal(this, 1.0D));
        this.goalSelector.addGoal(2, new EnhancedTurtle.PlayerTemptGoal(this, 1.1D, Blocks.SEAGRASS.asItem()));
        this.goalSelector.addGoal(3, new EnhancedTurtle.GoToWaterGoal(this, 1.0D));
        this.goalSelector.addGoal(4, new EnhancedTurtle.GoHomeGoal(this, 1.0D));
        this.goalSelector.addGoal(7, new EnhancedTurtle.TravelGoal(this, 1.0D));
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(9, new EnhancedTurtle.WanderGoal(this, 1.0D, 100));
    }

    @Override
    public EntityDimensions getDimensions(Pose poseIn) {
        return EntityDimensions.scalable(1.2F, 0.4F).scale(this.getScale());
    }

    public float getScale() {
        return this.isGrowing() ? (0.2F + (0.8F * (this.growthAmount()))) : 1.0F;
    }

    public static AttributeSupplier.Builder prepareAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 30.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.25D);
    }

    @Override
    protected PathNavigation createNavigation(Level worldIn) {
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
        this.entityData.set(HOME_POS, position);
    }

    private BlockPos getHome() {
        return this.entityData.get(HOME_POS);
    }

    private void setTravelPos(BlockPos position) {
        this.entityData.set(TRAVEL_POS, position);
    }

    private BlockPos getTravelPos() {
        return this.entityData.get(TRAVEL_POS);
    }

    public boolean hasEgg() {
        return this.entityData.get(HAS_EGG) || this.pregnant;
    }

    private void setHasEgg(boolean hasEgg) {
        this.entityData.set(HAS_EGG, hasEgg);
    }

    public boolean isDigging() {
        return this.entityData.get(IS_DIGGING);
    }

    private void setDigging(boolean isDigging) {
        this.isDigging = isDigging ? 1 : 0;
        this.entityData.set(IS_DIGGING, isDigging);
    }

    private boolean isGoingHome() {
        return this.entityData.get(GOING_HOME);
    }

    private void setGoingHome(boolean isGoingHome) {
        this.entityData.set(GOING_HOME, isGoingHome);
    }

    private boolean isTravelling() {
        return this.entityData.get(TRAVELLING);
    }

    private void setTravelling(boolean isTravelling) {
        this.entityData.set(TRAVELLING, isTravelling);
    }

    public boolean canBeLeashed(Player player) {
        return false;
    }

    @Override
    public Boolean isAnimalSleeping() {
        if (!this.isInWater() || this.hasEgg() || this.isGoingHome()) {
            return false;
        } else {
            this.sleeping = this.entityData.get(SLEEPING);
            return this.sleeping;
        }
    }

    @Override
    public boolean sleepingConditional() {
        this.sleepTimer = this.sleepTimer > 0 ? this.sleepTimer-- : this.sleepTimer++;
        if (this.sleepTimer == 0) {
            //finished sleeping
            this.sleepTimer = -(this.random.nextInt(6000)+8000);
        } else if (this.sleepTimer==-1 && !(this.hasEgg() || this.isGoingHome())) {
            //is tired
            this.sleepTimer = this.random.nextInt(6000)+1200;
        }
        return (this.sleepTimer > 0) && this.awokenTimer == 0 && !this.sleeping;
    }

    @Override
    protected FoodSerialiser.AnimalFoodMap getAnimalFoodType() {
        return turtleFoodMap();
    }

    @Override
    protected String getSpecies() {
        return "entity.eanimod.enhanced_turtle";
    }

    @Override
    protected int getAdultAge() { return EanimodCommonConfig.COMMON.adultAgeTurtle.get();}

    public void setHasScute() {
        this.hasScute = this.getEnhancedAnimalAge() < 24000;
    }

    public boolean canDropScute() {
        return this.hasScute && this.isAddedToWorld() && EanimodCommonConfig.COMMON.turtleScuteDropAge.get() <= this.getEnhancedAnimalAge();
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
            this.setHome(this.blockPosition());
        }
        if (this.canDropScute()) {
            if (this.level.getGameRules().getBoolean(GameRules.RULE_DOMOBLOOT)) {
                this.spawnAtLocation(Items.SCUTE, 1);
            }
            this.hasScute = false;
        }
    }

    @Override
    protected void lethalGenes() {

    }

    @Override
    public void initilizeAnimalSize() {
        this.setAnimalSize(1.0F);
    }

    @Override
    protected void createAndSpawnEnhancedChild(Level world) {

    }

    @Override
    protected boolean canBePregnant() {
        return false;
    }

    @Override
    protected boolean canLactate() {
        return false;
    }

    public boolean isPushedByFluid() {
        return false;
    }

    public boolean canBreatheUnderwater() {
        return true;
    }

    public MobType getMobType() {
        return MobType.WATER;
    }

    /**
     * Get number of ticks, at least during which the living entity will be silent.
     */
    public int getAmbientSoundInterval() {
        return 200;
    }

    @Nullable
    protected SoundEvent getAmbientSound() {
        return !this.isInWater() && this.onGround && !this.isBaby() ? SoundEvents.TURTLE_AMBIENT_LAND : super.getAmbientSound();
    }

    protected void playSwimSound(float volume) {
        super.playSwimSound(volume * 1.5F);
    }

    protected SoundEvent getSwimSound() {
        return SoundEvents.TURTLE_SWIM;
    }

    @Nullable
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return this.isBaby() ? SoundEvents.TURTLE_HURT_BABY : SoundEvents.TURTLE_HURT;
    }

    @Nullable
    protected SoundEvent getDeathSound() {
        return this.isBaby() ? SoundEvents.TURTLE_DEATH_BABY : SoundEvents.TURTLE_DEATH;
    }

    protected void playStepSound(BlockPos pos, BlockState blockIn) {
        SoundEvent soundevent = this.isBaby() ? SoundEvents.TURTLE_SHAMBLE_BABY : SoundEvents.TURTLE_SHAMBLE;
        this.playSound(soundevent, 0.15F, 1.0F);
        if (!this.isSilent() && this.getBells() && this.random.nextBoolean()) {
            this.playSound(SoundEvents.NOTE_BLOCK_IRON_XYLOPHONE, 0.5F, 0.2F);
            this.playSound(SoundEvents.NOTE_BLOCK_CHIME, 1.4F, 0.155F);
        }
    }

    protected float nextStep() {
        return this.moveDist + 0.15F;
    }

//    public float getRenderScale() {
//        return this.isChild() ? 0.3F : 1.0F;
//    }

    @OnlyIn(Dist.CLIENT)
    public String getTexture() {
        if (this.enhancedAnimalTextures.isEmpty()) {
            this.setTexturePaths();
        } else if (this.reload) {
            this.reload = false;
            this.reloadTextures();
        }

        return getCompiledTextures("enhanced_turtle");

    }

    @OnlyIn(Dist.CLIENT)
    protected void setTexturePaths() {
        if (this.getSharedGenes() != null) {
            int[] gene = getSharedGenes().getAutosomalGenes();
            int base = 0;
            int pibald = 0;

            char[] uuidArry = getStringUUID().toCharArray();

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
                if ( Character.isDigit(uuidArry[5]) ){
                    pibald = 1 + (uuidArry[5]-48);
                } else {
                    char d = uuidArry[5];

                    switch (d) {
                        case 'a':
                            pibald = 11;
                            break;
                        case 'b':
                            pibald = 12;
                            break;
                        case 'c':
                            pibald = 13;
                            break;
                        case 'd':
                            pibald = 14;
                            break;
                        case 'e':
                            pibald = 15;
                            break;
                        case 'f':
                            pibald = 16;
                            break;
                        default:
                            pibald = 0;
                    }
                }

                if (gene[8] == 2 && gene[9] == 2) {
                    pibald = 1;
                } else if ((pibald-1) % 4 == 0){
                    pibald = (int)(pibald + (pibald*0.25F));
                }
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

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(HOME_POS, BlockPos.ZERO);
        this.entityData.define(HAS_EGG, false);
        this.entityData.define(TRAVEL_POS, BlockPos.ZERO);
        this.entityData.define(GOING_HOME, false);
        this.entityData.define(TRAVELLING, false);
        this.entityData.define(IS_DIGGING, false);
    }

    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("HomePosX", this.getHome().getX());
        compound.putInt("HomePosY", this.getHome().getY());
        compound.putInt("HomePosZ", this.getHome().getZ());
        compound.putBoolean("HasEgg", this.hasEgg() || this.pregnant);
        compound.putInt("TravelPosX", this.getTravelPos().getX());
        compound.putInt("TravelPosY", this.getTravelPos().getY());
        compound.putInt("TravelPosZ", this.getTravelPos().getZ());
        compound.putBoolean("hasScute", this.hasScute);
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        int i = compound.getInt("HomePosX");
        int j = compound.getInt("HomePosY");
        int k = compound.getInt("HomePosZ");
        this.setHome(new BlockPos(i, j, k));
        this.setHasEgg(compound.getBoolean("HasEgg"));
        i = compound.getInt("TravelPosX");
        j = compound.getInt("TravelPosY");
        k = compound.getInt("TravelPosZ");
        this.setTravelPos(new BlockPos(i, j, k));
        this.hasScute = compound.getBoolean("hasScute");
    }

    @Override
    protected void geneFixer() {
        super.geneFixer();
        this.homePosFixer = !this.breed.isEmpty();
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor worldIn, DifficultyInstance difficultyIn, MobSpawnType reason, @Nullable SpawnGroupData livingdata, @Nullable CompoundTag dataTag) {
        return commonInitialSpawnSetup(worldIn, livingdata, getAdultAge(), 48000, 800000, reason);
    }

    @Override
    public void setInitialDefaults() {
        super.setInitialDefaults();
        this.setHome(new BlockPos(this.blockPosition()));
        this.setTravelPos(BlockPos.ZERO);
        this.setHasScute();
    }

    public static boolean canTurtleSpawn(EntityType<EnhancedTurtle> p_223322_0_, LevelAccessor p_223322_1_, MobSpawnType reason, BlockPos p_223322_3_, Random p_223322_4_) {
        return p_223322_3_.getY() < p_223322_1_.getSeaLevel() + 4 && EnhancedTurtleEggBlock.hasProperHabitat(p_223322_1_, p_223322_3_) && p_223322_1_.getRawBrightness(p_223322_3_, 0) > 8;
    }

    @Override
    protected Genes createInitialGenes(LevelAccessor inWorld, BlockPos pos, boolean isDomestic) {
        return new TurtleGeneticsInitialiser().generateNewGenetics(this.level, pos, isDomestic);
    }

    @Override
    public Genes createInitialBreedGenes(LevelAccessor inWorld, BlockPos pos, String breed) {
        return new TurtleGeneticsInitialiser().generateWithBreed(this.level, pos, breed);
    }

    public void travel(Vec3 travelVector) {
        if (this.isEffectiveAi() && this.isInWater()) {
            this.moveRelative(0.1F, travelVector);
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.setDeltaMovement(this.getDeltaMovement().scale(0.9D));
            if (this.getTarget() == null && (!this.isGoingHome() || !this.getHome().closerToCenterThan(this.position(), 20.0D))) {
                this.setDeltaMovement(this.getDeltaMovement().add(0.0D, -0.005D, 0.0D));
            }
        } else {
            super.travel(travelVector);
        }

    }

    public void thunderHit(ServerLevel p_241841_1_, LightningBolt p_241841_2_) {
        this.hurt(DamageSource.LIGHTNING_BOLT, Float.MAX_VALUE);
    }

    static class GoHomeGoal extends Goal {
        private final EnhancedTurtle turtle;
        private final double speed;
        private boolean stuck;
        private int closeToHomeTryTicks;

        GoHomeGoal(EnhancedTurtle turtle, double speedIn) {
            this.turtle = turtle;
            this.speed = speedIn;
        }

        /**
         * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
         * method as well.
         */
        public boolean canUse() {
            if (this.turtle.isBaby()) {
                return false;
            } else if (this.turtle.hasEgg()) {
                return true;
            } else if (this.turtle.getRandom().nextInt(700) != 0) {
                return false;
            } else {
                return !this.turtle.getHome().closerToCenterThan(this.turtle.position(), 64.0D);
            }
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void start() {
            this.turtle.setGoingHome(true);
            this.stuck = false;
            this.closeToHomeTryTicks = 0;
        }

        /**
         * Reset the task's internal state. Called when this task is interrupted by another one
         */
        public void stop() {
            this.turtle.setGoingHome(false);
        }

        /**
         * Returns whether an in-progress EntityAIBase should continue executing
         */
        public boolean canContinueToUse() {
            return !this.turtle.getHome().closerToCenterThan(this.turtle.position(), 7.0D) && !this.stuck && this.closeToHomeTryTicks <= 600;
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void tick() {
            BlockPos blockpos = this.turtle.getHome();
            boolean flag = blockpos.closerToCenterThan(this.turtle.position(), 16.0D);
            if (flag) {
                ++this.closeToHomeTryTicks;
            }

            if (this.turtle.getNavigation().isDone()) {
                Vec3 vec3 = Vec3.atBottomCenterOf(blockpos);
                Vec3 vec31 = DefaultRandomPos.getPosTowards(this.turtle, 16, 3, vec3, (double)((float)Math.PI / 10F));
                if (vec31 == null) {
                    vec31 = DefaultRandomPos.getPosTowards(this.turtle, 8, 7, vec3, (double)((float)Math.PI / 2F));
                }

                if (vec31 != null && !flag && !this.turtle.level.getBlockState(new BlockPos(vec31)).is(Blocks.WATER)) {
                    vec31 = DefaultRandomPos.getPosTowards(this.turtle, 16, 5, vec3, (double)((float)Math.PI / 2F));
                }

                if (vec31 == null) {
                    this.stuck = true;
                    return;
                }

                this.turtle.getNavigation().moveTo(vec31.x, vec31.y, vec31.z, this.speed);
            }

        }
    }

    static class GoToWaterGoal extends MoveToBlockGoal {
        private final EnhancedTurtle turtle;

        private GoToWaterGoal(EnhancedTurtle turtle, double speedIn) {
            super(turtle, turtle.isBaby() ? 2.0D : speedIn, 24);
            this.turtle = turtle;
            this.verticalSearchStart = -1;
        }

        /**
         * Returns whether an in-progress EntityAIBase should continue executing
         */
        public boolean canContinueToUse() {
            return !this.turtle.isInWater() && this.tryTicks <= 1200 && this.isValidTarget(this.turtle.level, this.getMoveToTarget());
        }

        /**
         * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
         * method as well.
         */
        public boolean canUse() {
            if (this.turtle.isBaby() && !this.turtle.isInWater()) {
                return super.canUse();
            } else {
                return !this.turtle.isGoingHome() && !this.turtle.isInWater() && !this.turtle.hasEgg() && super.canUse();
            }
        }

        public boolean shouldRecalculatePath() {
            return this.tryTicks % 160 == 0;
        }

        /**
         * Return true to set given position as destination
         */
        protected boolean isValidTarget(LevelReader worldIn, BlockPos pos) {
            return worldIn.getBlockState(pos).is(Blocks.WATER);
        }
    }

    static class LayEggGoal extends MoveToBlockGoal {
        private final EnhancedTurtle turtle;

        LayEggGoal(EnhancedTurtle turtle, double speedIn) {
            super(turtle, speedIn, 16);
            this.turtle = turtle;
        }

        public boolean canUse() {
            if (this.turtle.mateGenetics==null) {
                this.turtle.setHasEgg(false);
                return false;
            }
            return (this.turtle.hasEgg()) && this.turtle.getHome().closerToCenterThan(this.turtle.position(), 9.0D) ? super.canUse() : false;
        }

        public boolean canContinueToUse() {
            if (this.turtle.mateGenetics==null) {
                this.turtle.setHasEgg(false);
                return false;
            }
            return super.canContinueToUse() && this.turtle.hasEgg() && this.turtle.getHome().closerToCenterThan(this.turtle.position(), 9.0D);
        }

        public void tick() {
            super.tick();
            BlockPos blockpos = this.turtle.blockPosition();
            if (!this.turtle.isInWater() && this.isReachedTarget()) {
                if (this.turtle.isDigging < 1) {
                    this.turtle.setDigging(true);
                } else if (this.turtle.isDigging > 200) {
                    Level world = this.turtle.level;
                    world.playSound((Player)null, blockpos, SoundEvents.TURTLE_LAY_EGG, SoundSource.BLOCKS, 0.3F, 0.9F + world.random.nextFloat() * 0.2F);
                    int numberOfEggs = this.turtle.random.nextInt(4) + 1;
                    BlockPos pos = this.blockPos.above();
                    String mateName = this.turtle.mateName.isEmpty() ? "???" : this.turtle.mateName;
                    String name = this.turtle.hasCustomName() ? this.turtle.getName().getString() : "???";
                    for (int i = 0; i < numberOfEggs;i++) {
                        Genes eggGenes = new Genes(this.turtle.getGenes()).makeChild(true, true, this.turtle.mateGenetics);
                        world.getCapability(NestCapabilityProvider.NEST_CAP, null).orElse(new NestCapabilityProvider()).addNestEggPos(pos, mateName,name, eggGenes, true);
                    }
                    world.setBlock(pos, ModBlocks.TURTLE_EGG.get().defaultBlockState().setValue(EnhancedTurtleEggBlock.EGGS, Integer.valueOf(numberOfEggs)), 3);
                    this.turtle.setHasEgg(false);
                    this.turtle.setDigging(false);
//                    this.turtle.setInLove(600);
                }

                if (this.turtle.isDigging()) {
                    this.turtle.isDigging++;
                }
            }

        }

        /**
         * Return true to set given position as destination
         */
        protected boolean isValidTarget(LevelReader worldIn, BlockPos pos) {
            return !worldIn.isEmptyBlock(pos.above()) ? false : EnhancedTurtleEggBlock.isProperHabitat(worldIn, pos);
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
        public boolean canUse() {
            return super.canUse() && !this.turtle.hasEgg();
        }

        /**
         * Spawns a baby animal of the same type.
         */
        protected void breed() {
            if (this.turtle.getOrSetIsFemale()) {
                this.turtle.handlePartnerBreeding(this.partner);
            } else {
                ((EnhancedTurtle) this.partner).handlePartnerBreeding(this.turtle);
            }

            if (this.turtle.pregnant) {
                this.turtle.setHasEgg(true);
                this.turtle.pregnant = false;
            }
            if (((EnhancedTurtle) this.partner).pregnant) {
                ((EnhancedTurtle) this.partner).setHasEgg(true);
                ((EnhancedTurtle) this.partner).pregnant = false;
            }
            this.turtle.setAge(10);
            this.turtle.resetLove();
            this.partner.setAge(10);
            this.partner.resetLove();

            ServerPlayer entityplayermp = this.turtle.getLoveCause();
            if (entityplayermp == null && this.partner.getLoveCause() != null) {
                entityplayermp = this.partner.getLoveCause();
            }

            if (entityplayermp != null) {
                entityplayermp.awardStat(Stats.ANIMALS_BRED);
                CriteriaTriggers.BRED_ANIMALS.trigger(entityplayermp, this.turtle, ((EnhancedAnimalAbstract) this.partner), (AgeableMob) null);
            }

            Random random = this.animal.getRandom();
            if (this.level.getGameRules().getBoolean(GameRules.RULE_DOMOBLOOT)) {
                this.level.addFreshEntity(new ExperienceOrb(this.level, this.animal.getX(), this.animal.getY(), this.animal.getZ(), random.nextInt(7) + 1));
            }
        }
    }

    static class MoveHelperController extends MoveControl {
        private final EnhancedTurtle turtle;

        MoveHelperController(EnhancedTurtle turtleIn) {
            super(turtleIn);
            this.turtle = turtleIn;
        }

        private void updateSpeed() {
            if (this.turtle.isInWater()) {
                this.turtle.setDeltaMovement(this.turtle.getDeltaMovement().add(0.0D, 0.005D, 0.0D));
                if (!this.turtle.getHome().closerToCenterThan(this.turtle.position(), 16.0D)) {
                    this.turtle.setSpeed(Math.max(this.turtle.getSpeed() / 2.0F, 0.08F));
                }

                if (this.turtle.isBaby()) {
                    this.turtle.setSpeed(Math.max(this.turtle.getSpeed() / 3.0F, 0.06F));
                }
            } else if (this.turtle.onGround) {
                this.turtle.setSpeed(Math.max(this.turtle.getSpeed() / 2.0F, 0.06F));
            }

        }

        public void tick() {
            this.updateSpeed();
            if (this.operation == MoveControl.Operation.MOVE_TO && !this.turtle.getNavigation().isDone()) {
                double d0 = this.wantedX - this.turtle.getX();
                double d1 = this.wantedY - this.turtle.getY();
                double d2 = this.wantedZ - this.turtle.getZ();
                double d3 = (double)Mth.sqrt((float) (d0 * d0 + d1 * d1 + d2 * d2));
                d1 = d1 / d3;
                float f = (float)(Mth.atan2(d2, d0) * (double)(180F / (float)Math.PI)) - 90.0F;
                this.turtle.setYRot(this.rotlerp(this.turtle.getYRot(), f, 90.0F));
                this.turtle.setYBodyRot(this.turtle.getYRot());
                float f1 = (float)(this.speedModifier * this.turtle.getAttributeValue(Attributes.MOVEMENT_SPEED));
                this.turtle.setSpeed(Mth.lerp(0.125F, this.turtle.getSpeed(), f1));
                this.turtle.setDeltaMovement(this.turtle.getDeltaMovement().add(0.0D, (double)this.turtle.getSpeed() * d1 * 0.1D, 0.0D));
            } else {
                this.turtle.setSpeed(0.0F);
            }
        }
    }

    static class Navigator extends WaterBoundPathNavigation {
        Navigator(EnhancedTurtle turtle, Level worldIn) {
            super(turtle, worldIn);
        }

        /**
         * If on ground or swimming and can swim
         */
        protected boolean canUpdatePath() {
            return true;
        }

        protected PathFinder createPathFinder(int p_179679_1_) {
            this.nodeEvaluator = new AmphibiousNodeEvaluator(true);
            return new PathFinder(this.nodeEvaluator, p_179679_1_);
        }

        public boolean isStableDestination(BlockPos pos) {
            if (this.mob instanceof EnhancedTurtle) {
                EnhancedTurtle turtleentity = (EnhancedTurtle)this.mob;
                if (turtleentity.isTravelling()) {
                    return this.level.getBlockState(pos).is(Blocks.WATER);
                }
            }

            return !this.level.getBlockState(pos.below()).isAir();
        }
    }

    static class PanicGoal extends net.minecraft.world.entity.ai.goal.PanicGoal {
        PanicGoal(EnhancedTurtle turtle, double speedIn) {
            super(turtle, speedIn);
        }

        /**
         * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
         * method as well.
         */
        public boolean canUse() {
            if (this.mob.getLastHurtByMob() == null && !this.mob.isOnFire()) {
                return false;
            } else {
                BlockPos blockpos = this.lookForWater(this.mob.level, this.mob, 7);
                if (blockpos != null) {
                    this.posX = (double)blockpos.getX();
                    this.posY = (double)blockpos.getY();
                    this.posZ = (double)blockpos.getZ();
                    return true;
                } else {
                    return this.findRandomPosition();
                }
            }
        }
    }

    static class PlayerTemptGoal extends Goal {
        private static final TargetingConditions TEMPT_TARGETING = TargetingConditions.forNonCombat().range(10.0D).ignoreLineOfSight();
        private final EnhancedTurtle turtle;
        private final double speed;
        private Player tempter;
        private int cooldown;

        PlayerTemptGoal(EnhancedTurtle turtle, double speedIn, Item temptItem) {
            this.turtle = turtle;
            this.speed = speedIn;
            this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        }

        /**
         * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
         * method as well.
         */
        public boolean canUse() {
            if (this.cooldown > 0) {
                --this.cooldown;
                return this.turtle.getHunger() < 3000;
            } else {
                this.tempter = this.turtle.level.getNearestPlayer(TEMPT_TARGETING, this.turtle);
                if (this.tempter == null) {
                    return false;
                } else {
                    return this.turtle.isTempted(this.isTemptedBy(this.tempter.getMainHandItem()) || this.isTemptedBy(this.tempter.getOffhandItem()));
                }
            }
        }

        private boolean isTemptedBy(ItemStack itemStack) {
            return this.turtle.isFoodItem(itemStack);
        }

        /**
         * Returns whether an in-progress EntityAIBase should continue executing
         */
        public boolean canContinueToUse() {
            return this.canUse();
        }

        /**
         * Reset the task's internal state. Called when this task is interrupted by another one
         */
        public void stop() {
            this.tempter = null;
            this.turtle.getNavigation().stop();
            this.turtle.isTempted(false);
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void tick() {
            this.turtle.getLookControl().setLookAt(this.tempter, (float)(this.turtle.getMaxHeadYRot() + 20), (float)this.turtle.getMaxHeadXRot());
            if (this.turtle.distanceToSqr(this.tempter) < 6.25D) {
                this.turtle.getNavigation().stop();
            } else {
                this.turtle.getNavigation().moveTo(this.tempter, this.speed);
            }

        }
    }

    static class TravelGoal extends Goal {
        private final EnhancedTurtle turtle;
        private final double speed;
        private boolean stuck;

        TravelGoal(EnhancedTurtle turtle, double speedIn) {
            this.turtle = turtle;
            this.speed = speedIn;
        }

        /**
         * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
         * method as well.
         */
        public boolean canUse() {
            return !this.turtle.isGoingHome() && !this.turtle.hasEgg() && !this.turtle.isAnimalSleeping() && this.turtle.isInWater() && !this.turtle.getIsTempted();
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void start() {
            int i = 512;
            int j = 4;
            Random random = this.turtle.random;
            int k = random.nextInt(1025) - 512;
            int l = random.nextInt(9) - 4;
            int i1 = random.nextInt(1025) - 512;
            if ((double)l + this.turtle.getY() > (double)(this.turtle.level.getSeaLevel() - 1)) {
                l = 0;
            }

            BlockPos blockpos = new BlockPos((double)k + this.turtle.getX(), (double)l + this.turtle.getY(), (double)i1 + this.turtle.getZ());
            this.turtle.setTravelPos(blockpos);
            this.turtle.setTravelling(true);
            this.stuck = false;
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void tick() {
            if (this.turtle.getNavigation().isDone()) {
                Vec3 vec3 = Vec3.atBottomCenterOf(this.turtle.getTravelPos());
                Vec3 vec31 = DefaultRandomPos.getPosTowards(this.turtle, 16, 3, vec3, (double)((float)Math.PI / 10F));
                if (vec31 == null) {
                    vec31 = DefaultRandomPos.getPosTowards(this.turtle, 8, 7, vec3, (double)((float)Math.PI / 2F));
                }

                if (vec31 != null) {
                    int i = Mth.floor(vec31.x);
                    int j = Mth.floor(vec31.z);
                    int k = 34;
                    if (!this.turtle.level.hasChunksAt(i - 34, j - 34, i + 34, j + 34)) {
                        vec31 = null;
                    }
                }

                if (vec31 == null) {
                    this.stuck = true;
                    return;
                }

                this.turtle.getNavigation().moveTo(vec31.x, vec31.y, vec31.z, this.speed);
            }

        }

        /**
         * Returns whether an in-progress EntityAIBase should continue executing
         */
        public boolean canContinueToUse() {
            return !this.turtle.getNavigation().isDone() && !this.stuck && !this.turtle.isGoingHome() && !this.turtle.isAnimalSleeping() && !this.turtle.isInLove() && !this.turtle.hasEgg() && !this.turtle.getIsTempted();
        }

        /**
         * Reset the task's internal state. Called when this task is interrupted by another one
         */
        public void stop() {
            this.turtle.setTravelling(false);
            super.stop();
        }
    }

    static class WanderGoal extends RandomStrollGoal {
        private final EnhancedTurtle turtle;

        private WanderGoal(EnhancedTurtle turtle, double speedIn, int chance) {
            super(turtle, speedIn, chance);
            this.turtle = turtle;
        }

        /**
         * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
         * method as well.
         */
        public boolean canUse() {
            return !this.mob.isInWater() && !this.turtle.isGoingHome() && !this.turtle.hasEgg() && !this.turtle.isAnimalSleeping() && super.canUse();
        }
    }
}
