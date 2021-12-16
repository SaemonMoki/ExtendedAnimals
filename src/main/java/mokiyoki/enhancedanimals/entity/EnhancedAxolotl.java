package mokiyoki.enhancedanimals.entity;

import mokiyoki.enhancedanimals.blocks.EnhancedAxolotlEggBlock;
import mokiyoki.enhancedanimals.capability.turtleegg.NestCapabilityProvider;
import mokiyoki.enhancedanimals.entity.Genetics.AxolotlGeneticsInitialiser;
import mokiyoki.enhancedanimals.entity.util.Colouration;
import mokiyoki.enhancedanimals.init.FoodSerialiser;
import mokiyoki.enhancedanimals.init.ModBlocks;
import mokiyoki.enhancedanimals.util.Genes;
import mokiyoki.enhancedanimals.util.Reference;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.decoration.LeashFenceKnotEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SeagrassBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.AmphibiousNodeEvaluator;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.PathFinder;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.Random;

public class EnhancedAxolotl extends EnhancedAnimalAbstract {
    private static final EntityDataAccessor<Boolean> HAS_EGG = SynchedEntityData.defineId(EnhancedAxolotl.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<BlockPos> TRAVEL_POS = SynchedEntityData.defineId(EnhancedAxolotl.class, EntityDataSerializers.BLOCK_POS);
    private static final EntityDataAccessor<Boolean> TRAVELLING = SynchedEntityData.defineId(EnhancedAxolotl.class, EntityDataSerializers.BOOLEAN);

    private int airTimer;
    private int sleepTimer;
    private boolean isTempted = false;

    private static final String[] AXOLOTL_TEXTURES_BASE = new String[] {
            "natural.png", "c_extrapigment.png"
    };


    public EnhancedAxolotl(EntityType<? extends EnhancedAxolotl> type, Level worldIn) {
        super(type, worldIn, 2, Reference.AXOLOTL_AUTOSOMAL_GENES_LENGTH, false);
        this.setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
        this.moveControl = new EnhancedAxolotl.MoveHelperController(this);
        this.maxUpStep = 1.0F;
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(0, new EnhancedAxolotl.PanicGoal(this, 1.2D));
        this.goalSelector.addGoal(1, new EnhancedAxolotl.MateGoal(this, 1.0D));
        this.goalSelector.addGoal(1, new EnhancedAxolotl.LayEggGoal(this, 1.0D));
        this.goalSelector.addGoal(2, new EnhancedAxolotl.PlayerTemptGoal(this, 1.1D));
        this.goalSelector.addGoal(3, new EnhancedAxolotl.GoToWaterGoal(this, 1.0D));
        this.goalSelector.addGoal(5, new EnhancedAxolotl.WanderGoal(this, 1.0D, 100));
        this.goalSelector.addGoal(6, new EnhancedAxolotl.TravelGoal(this, 1.0D));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
    }

    @Override
    public EntityDimensions getDimensions(Pose poseIn) {
        return EntityDimensions.scalable(1.2F, 0.4F).scale(this.getScale());
    }

    public float getRenderScale() {
        return this.isGrowing() ? (0.2F + (0.8F * (this.growthAmount()))) : 1.0F;
    }

    public static AttributeSupplier.Builder prepareAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 30.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.25D);
    }

    @Override
    protected PathNavigation createNavigation(Level worldIn) {
        return new EnhancedAxolotl.Navigator(this, worldIn);
    }

    public boolean isTempted(boolean tempted) {
        this.isTempted = tempted;
        return tempted;
    }

    public boolean getIsTempted() {
        return this.isTempted;
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

    private boolean isTravelling() {
        return this.entityData.get(TRAVELLING);
    }

    private void setTravelling(boolean isTravelling) {
        this.entityData.set(TRAVELLING, isTravelling);
    }

    @Override
    public Boolean isAnimalSleeping() {
        if (!this.isInWater() || this.hasEgg()) {
            return false;
        } else if (!(this.getLeashHolder() instanceof LeashFenceKnotEntity) && this.getLeashHolder() != null) {
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
        } else if (this.sleepTimer==-1 && !(this.hasEgg())) {
            //is tired
            this.sleepTimer = this.random.nextInt(6000)+1200;
        }
        return (this.sleepTimer > 0) && this.awokenTimer == 0 && !this.sleeping;
    }

    @Override
    protected String getSpecies() {
        return "entity.eanimod.enhanced_axolotl";
    }

    @Override
    protected int getAdultAge() { return 24000;}

    @Override
    protected int gestationConfig() {
        return 24000;
    }

    @Override
    protected void incrementHunger() {

    }

    @Override
    protected void runExtraIdleTimeTick() {

    }

    @Override
    protected void lethalGenes() {

    }

    @Override
    public void initilizeAnimalSize() {

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

    @Override
    protected FoodSerialiser.AnimalFoodMap getAnimalFoodType() {
        return null;
    }

    public boolean isPushedByWater() {
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
        return !this.isInWater() && this.onGround && !this.isBaby() ? SoundEvents.AXOLOTL_IDLE_AIR : super.getAmbientSound();
    }

    protected void playSwimSound(float volume) {
        super.playSwimSound(volume * 1.5F);
    }

    protected SoundEvent getSwimSound() {
        return SoundEvents.AXOLOTL_SWIM;
    }

    @Nullable
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.AXOLOTL_HURT;
    }

    @Nullable
    protected SoundEvent getDeathSound() {
        return SoundEvents.AXOLOTL_DEATH;
    }

    protected void playStepSound(BlockPos pos, BlockState blockIn) {
        SoundEvent soundevent = this.isBaby() ? SoundEvents.TURTLE_SHAMBLE_BABY : SoundEvents.TURTLE_SHAMBLE;
        this.playSound(soundevent, 0.15F, 1.0F);
        if (!this.isSilent() && this.getBells() && this.random.nextBoolean()) {
            this.playSound(SoundEvents.NOTE_BLOCK_IRON_XYLOPHONE, 0.5F, 0.1F);
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
        } else if (this.getReloadTexture() ^ this.reload) {
            this.reload=!this.reload;
            this.reloadTextures();
        }

        return getCompiledTextures("enhanced_axolotl");

    }

    @OnlyIn(Dist.CLIENT)
    protected void setTexturePaths() {
        if (this.getSharedGenes() != null) {
            int[] gene = getSharedGenes().getAutosomalGenes();
//            char[] uuidArry = getCachedUniqueIdString().toCharArray();
            int base = gene[10] == 1 || gene[11] == 1 ? 0 : 1;

            addTextureToAnimal(AXOLOTL_TEXTURES_BASE, 1, null);
            addTextureToAnimal("eyel_.png");
            addTextureToAnimal("eyer_.png");
        }
    }

    @Override
    protected void setAlphaTexturePaths() {

    }

    @OnlyIn(Dist.CLIENT)
    public Colouration getRgb() {
        this.colouration = super.getRgb();
        Genes genes = getSharedGenes();
        if (genes != null) {
            if (this.colouration.getDyeColour() == -1 || this.colouration.getLeftEyeColour() == -1 || this.colouration.getRightEyeColour() == -1) {
                int[] gene = genes.getAutosomalGenes();

                float specialHue = Colouration.mixHueComponent((float) gene[24] / 255, (float) gene[25] / 255, 0.5F);
                this.colouration.setDyeColour(Colouration.HSBtoABGR(specialHue, 0.5F, 0.75F));

                float eyeHue = Colouration.mixHueComponent((float) gene[22] / 255, (float) gene[23] / 255, 0.5F);
                float eyeSaturation = 0.3F;
                float eyeBrightness = 0.25F;

                if (gene[22] == 2) {
                    if (gene[23] == 1) {
                        eyeSaturation = 0.6F;
                        eyeBrightness = 0.125F;
                    } else if (gene[23] == 2) {
                        eyeSaturation = 1.0F;
                        eyeBrightness = 0.06F;
                    } else if (gene[23] == 3) {
                        eyeSaturation = 1.0F;
                        eyeBrightness = 0.25F;
                    }
                }

                this.colouration.setLeftEyeColour(Colouration.HSBtoABGR(eyeHue, eyeSaturation, eyeBrightness));
                this.colouration.setRightEyeColour(Colouration.HSBtoABGR(eyeHue, eyeSaturation, eyeBrightness));
            }
        }

        return this.colouration;
    }

    @Override
    protected int getPregnancyProgression() {
        return this.hasEgg() ? 10 : 0;
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(HAS_EGG, false);
        this.entityData.define(TRAVEL_POS, BlockPos.ZERO);
        this.entityData.define(TRAVELLING, false);
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor worldIn, DifficultyInstance difficultyIn, MobSpawnType reason, @Nullable SpawnGroupData livingdata, @Nullable CompoundTag dataTag) {
        return commonInitialSpawnSetup(worldIn, livingdata, getAdultAge(), 48000, 800000, reason);
    }

    @Override
    public void setInitialDefaults() {
        super.setInitialDefaults();
    }

    @Override
    protected Genes createInitialGenes(LevelAccessor inWorld, BlockPos pos, boolean isDomestic) {
        return new AxolotlGeneticsInitialiser().generateNewGenetics(this.level, pos, isDomestic);
    }

    @Override
    public Genes createInitialBreedGenes(LevelAccessor inWorld, BlockPos pos, String breed) {
        return new AxolotlGeneticsInitialiser().generateWithBreed(this.level, pos, breed);
    }

    public void travel(Vec3 travelVector) {
        if (this.isEffectiveAi() && this.isInWater()) {
            this.moveRelative(0.1F, travelVector);
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.setDeltaMovement(this.getDeltaMovement().scale(0.9D));
            if (this.getTarget() == null) {
                this.setDeltaMovement(this.getDeltaMovement().add(0.0D, -0.005D, 0.0D));
            }
        } else {
            super.travel(travelVector);
        }

    }

    public void thunderHit(ServerLevel p_241841_1_, LightningBolt p_241841_2_) {
        this.hurt(DamageSource.LIGHTNING_BOLT, Float.MAX_VALUE);
    }

    static class GoToWaterGoal extends MoveToBlockGoal {
        private final EnhancedAxolotl axolotl;

        private GoToWaterGoal(EnhancedAxolotl axolotl, double speedIn) {
            super(axolotl, speedIn, 24);
            this.axolotl = axolotl;
            this.verticalSearchStart = -1;
        }

        /**
         * Returns whether an in-progress EntityAIBase should continue executing
         */
        public boolean canContinueToUse() {
            return !this.axolotl.isInWater() && this.tryTicks <= 1200 && this.isValidTarget(this.axolotl.level, this.getMoveToTarget());
        }

        /**
         * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
         * method as well.
         */
        public boolean canUse() {
            return !this.axolotl.isInWater();
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
        private final EnhancedAxolotl axolotl;

        LayEggGoal(EnhancedAxolotl axolotl, double speedIn) {
            super(axolotl, speedIn, 16);
            this.axolotl = axolotl;
        }

        /**
         * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
         * method as well.
         */
        public boolean canUse() {
            if (this.axolotl.mateGenetics==null) {
                this.axolotl.setHasEgg(false);
                return false;
            }
            return this.axolotl.hasEgg() && super.canUse();
        }

        /**
         * Returns whether an in-progress EntityAIBase should continue executing
         */
        public boolean canContinueToUse() {
            if (this.axolotl.mateGenetics==null) {
                this.axolotl.setHasEgg(false);
                return false;
            }
            return super.canContinueToUse() && this.axolotl.hasEgg();
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void tick() {
            super.tick();
            BlockPos blockpos = this.axolotl.blockPosition();
            if (!this.axolotl.isInWater() && this.isReachedTarget()) {
                    Level world = this.axolotl.level;
                    world.playSound((Player)null, blockpos, SoundEvents.TURTLE_LAY_EGG, SoundSource.BLOCKS, 0.3F, 0.9F + world.random.nextFloat() * 0.2F);
                    int numberOfEggs = this.axolotl.random.nextInt(4) + 1;
                    BlockPos pos = this.blockPos.below();
                    String mateName = this.axolotl.mateName.isEmpty() ? "???" : this.axolotl.mateName;
                    String name = this.axolotl.hasCustomName() ? this.axolotl.getName().getString() : "???";
                    for (int i = 0; i < numberOfEggs;i++) {
                        Genes eggGenes = new Genes(this.axolotl.getGenes()).makeChild(true, true, this.axolotl.mateGenetics);
                        world.getCapability(NestCapabilityProvider.NEST_CAP, null).orElse(new NestCapabilityProvider()).addNestEggPos(pos, mateName,name, eggGenes, true);
                    }
                    world.setBlock(pos, ModBlocks.TURTLE_EGG.defaultBlockState().setValue(EnhancedAxolotlEggBlock.EGGS, Integer.valueOf(numberOfEggs)), 3);
                    this.axolotl.setHasEgg(false);
                }
            }

        /**
         * Return true to set given position as destination
         */
        protected boolean isValidTarget(LevelReader worldIn, BlockPos pos) {
            if (worldIn.isWaterAt(pos)) {
                if (worldIn.getBlockState(pos.below()).getBlock() instanceof SeagrassBlock) {
                    return true;
                }
                if (worldIn.getBlockState(pos.north()).getBlock() instanceof SeagrassBlock) {
                    return true;
                }
                if (worldIn.getBlockState(pos.east()).getBlock() instanceof SeagrassBlock) {
                    return true;
                }
                if (worldIn.getBlockState(pos.south()).getBlock() instanceof SeagrassBlock) {
                    return true;
                }
                return worldIn.getBlockState(pos.west()).getBlock() instanceof SeagrassBlock;
            }

            return false;

        }

    }

    static class MateGoal extends BreedGoal {
        private final EnhancedAxolotl axolotl;

        MateGoal(EnhancedAxolotl axolotl, double speedIn) {
            super(axolotl, speedIn);
            this.axolotl = axolotl;
        }

        /**
         * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
         * method as well.
         */
        public boolean canUse() {
            return super.canUse() && !this.axolotl.hasEgg();
        }

        /**
         * Spawns a baby animal of the same type.
         */
        protected void breed() {
            this.axolotl.handlePartnerBreeding(this.partner);
            if (this.axolotl.pregnant) {
                this.axolotl.setHasEgg(true);
                this.axolotl.pregnant = false;
            }
            if (((EnhancedAxolotl) this.partner).pregnant) {
                ((EnhancedAxolotl) this.partner).setHasEgg(true);
                ((EnhancedAxolotl) this.partner).pregnant = false;
            }
            this.axolotl.setAge(10);
            this.axolotl.resetLove();
            this.partner.setAge(10);
            this.partner.resetLove();

            ServerPlayer entityplayermp = this.axolotl.getLoveCause();
            if (entityplayermp == null && this.partner.getLoveCause() != null) {
                entityplayermp = this.partner.getLoveCause();
            }

            if (entityplayermp != null) {
                entityplayermp.awardStat(Stats.ANIMALS_BRED);
                CriteriaTriggers.BRED_ANIMALS.trigger(entityplayermp, this.axolotl, ((EnhancedAnimalAbstract) this.partner), (AgeableMob) null);
            }

            Random random = this.animal.getRandom();
            if (this.level.getGameRules().getBoolean(GameRules.RULE_DOMOBLOOT)) {
                this.level.addFreshEntity(new ExperienceOrb(this.level, this.animal.getX(), this.animal.getY(), this.animal.getZ(), random.nextInt(7) + 1));
            }
        }
    }

    static class MoveHelperController extends MoveControl {
        private final EnhancedAxolotl axolotl;

        MoveHelperController(EnhancedAxolotl axolotl) {
            super(axolotl);
            this.axolotl = axolotl;
        }

        private void updateSpeed() {
            if (this.axolotl.isInWater()) {
                this.axolotl.setDeltaMovement(this.axolotl.getDeltaMovement().add(0.0D, 0.005D, 0.0D));

                if (this.axolotl.isBaby()) {
                    this.axolotl.setSpeed(Math.max(this.axolotl.getSpeed() / 3.0F, 0.06F));
                }
            } else if (this.axolotl.isOnGround()) {
                this.axolotl.setSpeed(Math.max(this.axolotl.getSpeed() / 2.0F, 0.06F));
            }

        }

        public void tick() {
            this.updateSpeed();
            if (this.operation == MoveControl.Operation.MOVE_TO && !this.axolotl.getNavigation().isDone()) {
                double d0 = this.wantedX - this.axolotl.getX();
                double d1 = this.wantedY - this.axolotl.getY();
                double d2 = this.wantedZ - this.axolotl.getZ();
                double d3 = (double) Mth.sqrt((float) (d0 * d0 + d1 * d1 + d2 * d2));
                d1 = d1 / d3;
                float f = (float)(Mth.atan2(d2, d0) * (double)(180F / (float)Math.PI)) - 90.0F;
                this.axolotl.setYRot(this.rotlerp(this.axolotl.getYRot(), f, 90.0F));
                this.axolotl.setYBodyRot(this.axolotl.getYRot());
                float f1 = (float)(this.speedModifier * this.axolotl.getAttributeValue(Attributes.MOVEMENT_SPEED));
                this.axolotl.setSpeed(Mth.lerp(0.125F, this.axolotl.getSpeed(), f1));
                this.axolotl.setDeltaMovement(this.axolotl.getDeltaMovement().add(0.0D, (double)this.axolotl.getSpeed() * d1 * 0.1D, 0.0D));
            } else {
                this.axolotl.setSpeed(0.0F);
            }
        }
    }

    static class Navigator extends WaterBoundPathNavigation {
        Navigator(EnhancedAxolotl axolotl, Level worldIn) {
            super(axolotl, worldIn);
        }

        /**
         * If on ground or swimming and can swim
         */
        protected boolean canUpdatePath() {
            return true;
        }

        protected PathFinder createPathFinder(int p_179679_1_) {
            this.nodeEvaluator = new AmphibiousNodeEvaluator(false);
            return new PathFinder(this.nodeEvaluator, p_179679_1_);
        }

        public boolean isStableDestination(BlockPos pos) {
            if (this.mob instanceof EnhancedAxolotl) {
                EnhancedAxolotl axolotl = (EnhancedAxolotl)this.mob;
                if (axolotl.isTravelling()) {
                    return this.level.getBlockState(pos).is(Blocks.WATER);
                }
            }

            return !this.level.getBlockState(pos.below()).isAir();
        }
    }

    static class PanicGoal extends net.minecraft.world.entity.ai.goal.PanicGoal {
        PanicGoal(EnhancedAxolotl axolotl, double speedIn) {
            super(axolotl, speedIn);
        }

        /**
         * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
         * method as well.
         */
        public boolean shouldExecute() {
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
        private final EnhancedAxolotl axolotl;
        private final double speed;
        private Player tempter;
        private int cooldown;

        PlayerTemptGoal(EnhancedAxolotl axolotl, double speedIn) {
            this.axolotl = axolotl;
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
                return this.axolotl.getHunger() < 3000;
            } else {
                this.tempter = this.axolotl.level.getNearestPlayer(TEMPT_TARGETING, this.axolotl);
                if (this.tempter == null) {
                    return false;
                } else {
                    return this.axolotl.isTempted(this.isTemptedBy(this.tempter.getMainHandItem()) || this.isTemptedBy(this.tempter.getOffhandItem()));
                }
            }
        }

        private boolean isTemptedBy(ItemStack itemStack) {
            return this.axolotl.isFoodItem(itemStack);
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
            this.axolotl.getNavigation().stop();
            this.axolotl.isTempted(false);
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void tick() {
            this.axolotl.getLookControl().setLookAt(this.tempter, (float)(this.axolotl.getMaxHeadYRot() + 20), (float)this.axolotl.getMaxHeadXRot());
            if (this.axolotl.distanceToSqr(this.tempter) < 6.25D) {
                this.axolotl.getNavigation().stop();
            } else {
                this.axolotl.getNavigation().moveTo(this.tempter, this.speed);
            }

        }
    }

    static class TravelGoal extends Goal {
        private final EnhancedAxolotl axolotl;
        private final double speed;
        private boolean stuck;

        TravelGoal(EnhancedAxolotl axolotl, double speedIn) {
            this.axolotl = axolotl;
            this.speed = speedIn;
        }

        /**
         * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
         * method as well.
         */
        public boolean canUse() {
            return this.axolotl.getTarget() == null && !this.axolotl.hasEgg() && !this.axolotl.isAnimalSleeping() && this.axolotl.isInWater() && !this.axolotl.getIsTempted();
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void start() {
            int i = 512;
            int j = 4;
            Random random = this.axolotl.random;
            int k = random.nextInt(1025) - 512;
            int l = random.nextInt(9) - 4;
            int i1 = random.nextInt(1025) - 512;
            if ((double)l + this.axolotl.getY() > (double)(this.axolotl.level.getSeaLevel() - 1)) {
                l = 0;
            }

            BlockPos blockpos = new BlockPos((double)k + this.axolotl.getX(), (double)l + this.axolotl.getY(), (double)i1 + this.axolotl.getZ());
            this.axolotl.setTravelPos(blockpos);
            this.axolotl.setTravelling(true);
            this.stuck = false;
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void tick() {
            if (this.axolotl.getNavigation().isDone()) {
                Vec3 vec3 = Vec3.atBottomCenterOf(this.axolotl.getTravelPos());
                Vec3 vec31 = DefaultRandomPos.getPosTowards(this.axolotl, 16, 3, vec3, (double)((float)Math.PI / 10F));
                if (vec31 == null) {
                    vec31 = DefaultRandomPos.getPosTowards(this.axolotl, 8, 7, vec3, (double)((float)Math.PI / 2F));
                }

                if (vec31 != null) {
                    int i = Mth.floor(vec31.x);
                    int j = Mth.floor(vec31.z);
                    int k = 34;
                    if (!this.axolotl.level.hasChunksAt(i - 34, j - 34, i + 34, j + 34)) {
                        vec31 = null;
                    }
                }

                if (vec31 == null) {
                    this.stuck = true;
                    return;
                }

                this.axolotl.getNavigation().moveTo(vec31.x, vec31.y, vec31.z, this.speed);
            }

        }

        /**
         * Returns whether an in-progress EntityAIBase should continue executing
         */
        public boolean canContinueToUse() {
            return !this.axolotl.getNavigation().isDone() && !this.stuck && !this.axolotl.isAnimalSleeping() && !this.axolotl.isInLove() && !this.axolotl.hasEgg() && !this.axolotl.getIsTempted();
        }

        /**
         * Reset the task's internal state. Called when this task is interrupted by another one
         */
        public void stop() {
            this.axolotl.setTravelling(false);
            super.stop();
        }
    }

    static class WanderGoal extends RandomStrollGoal {
        private final EnhancedAxolotl axolotl;

        private WanderGoal(EnhancedAxolotl axolotl, double speedIn, int chance) {
            super(axolotl, speedIn, chance);
            this.axolotl = axolotl;
        }

        /**
         * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
         * method as well.
         */
        public boolean canUse() {
            return !this.axolotl.hasEgg() && !this.axolotl.isAnimalSleeping() && super.canUse();
        }
    }
}
