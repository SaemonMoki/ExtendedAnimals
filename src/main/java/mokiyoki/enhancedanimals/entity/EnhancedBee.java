package mokiyoki.enhancedanimals.entity;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import mokiyoki.enhancedanimals.EnhancedAnimals;
import mokiyoki.enhancedanimals.capability.nestegg.INestEggCapability;
import mokiyoki.enhancedanimals.capability.nestegg.NestCapabilityProvider;
import mokiyoki.enhancedanimals.config.GeneticAnimalsConfig;
import mokiyoki.enhancedanimals.entity.genetics.BeeGeneticsInitialiser;
import mokiyoki.enhancedanimals.init.FoodSerialiser;
import mokiyoki.enhancedanimals.init.ModBlocks;
import mokiyoki.enhancedanimals.init.ModItems;
import mokiyoki.enhancedanimals.init.ModTags;
import mokiyoki.enhancedanimals.model.modeldata.AnimalModelData;
import mokiyoki.enhancedanimals.model.modeldata.BeeModelData;
import mokiyoki.enhancedanimals.network.EAPPHappy;
import mokiyoki.enhancedanimals.network.EAPPSolid;
import mokiyoki.enhancedanimals.network.EAPPAir;
import mokiyoki.enhancedanimals.network.EAParticlePacket;
import mokiyoki.enhancedanimals.util.Genes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.control.LookControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.ResetUniversalAngerTargetGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.util.AirAndWaterRandomPos;
import net.minecraft.world.entity.ai.util.AirRandomPos;
import net.minecraft.world.entity.ai.util.HoverRandomPos;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.entity.ai.village.poi.PoiRecord;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.animal.FlyingAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BeehiveBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.PacketDistributor;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static mokiyoki.enhancedanimals.init.ModEntities.ENHANCED_BEE;
import static mokiyoki.enhancedanimals.renderer.textures.BeeTexture.calculateBeeTexture;
import static mokiyoki.enhancedanimals.util.Reference.BEE_SEXLINKED_GENES_LENGTH;

public class EnhancedBee extends EnhancedAnimalAbstract implements NeutralMob, FlyingAnimal {
    private static final EntityDataAccessor<Integer> STINGER = SynchedEntityData.defineId(EnhancedBee.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> DATA_REMAINING_ANGER_TIME = SynchedEntityData.defineId(EnhancedBee.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Byte> DATA_FLAGS_ID = SynchedEntityData.defineId(EnhancedBee.class, EntityDataSerializers.BYTE);
    private static final UniformInt PERSISTENT_ANGER_TIME = TimeUtil.rangeOfSeconds(20, 39);
    public static final float FLAP_DEGREES_PER_TICK = 120.32113F;
    public static final int TICKS_PER_FLAP = Mth.ceil(1.4959966F);

    @Nullable
    private UUID persistentAngerTarget;
    private float rollAmount;
    private float rollAmountO;
    private int timeSinceSting;
    int ticksWithoutPollenSinceExitingNest;
    private int stayOutOfNestCountdown;
    private int numCropsGrownSincePollination;
    private static final int COOLDOWN_BEFORE_LOCATING_NEW_NEST = 200;
    int remainingCooldownBeforeLocatingNewNest;
    private static final int COOLDOWN_BEFORE_LOCATING_NEW_FLOWER = 200;
    int remainingCooldownBeforeLocatingNewFlower;
    @Nullable
    BlockPos savedFlowerPos;
    @Nullable
    BlockPos nestPos;
    PollinateGoal pollinateGoal;
    GoToNestGoal goToNestGoal;
    private BeeGoToKnownFlowerGoal goToKnownFlowerGoal;
    private int underWaterTicks;

    @OnlyIn(Dist.CLIENT)
    private BeeModelData beeModelData;

    private int stingerCountdown;
    private Gender gender;
    private boolean wantsSons = false;

    public EnhancedBee(EntityType<? extends EnhancedBee> entityType, Level worldIn) {
        super(entityType, worldIn, BEE_SEXLINKED_GENES_LENGTH, 2, false);
        this.initilizeAnimalSize();
        this.remainingCooldownBeforeLocatingNewFlower = Mth.nextInt(this.random, 20, 60);
        this.moveControl = new FlyingMoveControl(this, 20, true);
        this.lookControl = new BeeLookControl(this);
        this.setPathfindingMalus(BlockPathTypes.DANGER_FIRE, -1.0F);
        this.setPathfindingMalus(BlockPathTypes.WATER, -1.0F);
        this.setPathfindingMalus(BlockPathTypes.WATER_BORDER, 16.0F);
        this.setPathfindingMalus(BlockPathTypes.COCOA, -1.0F);
        this.setPathfindingMalus(BlockPathTypes.FENCE, -1.0F);
    }

    public static AttributeSupplier.Builder prepareAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.FLYING_SPEED, 0.6D)
                .add(Attributes.MAX_HEALTH, 10.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.3F)
                .add(Attributes.ATTACK_DAMAGE, 2.0D);
    }

    @Override
    public EntityDimensions getDimensions(Pose poseIn) {
        return EntityDimensions.scalable(0.75F, 0.42F).scale(this.getScale());
    }

    @Override
    public float getScale() {
        float size = this.getAnimalSize() > 0.0F ? this.getAnimalSize() : 1.0F;
        float nbSize = 0.25F;
        return this.isGrowing() ? (nbSize + ((size-nbSize) * (this.growthAmount()))) : size;
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(STINGER, -2);
        this.entityData.define(DATA_REMAINING_ANGER_TIME, 0);
        this.entityData.define(DATA_FLAGS_ID, (byte)0);
    }

    public float getWalkTargetValue(BlockPos p_27788_, LevelReader p_27789_) {
        return p_27789_.getBlockState(p_27788_).isAir() ? 10.0F : 0.0F;
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(0, new BeeAttackGoal(this, (double)1.4F, true));
        this.goalSelector.addGoal(1, new BeeEnterHiveGoal());
        this.goalSelector.addGoal(2, new BreedGoal(this, (double)1.0F));
        this.goalSelector.addGoal(3, new TemptGoal(this, (double)1.25F, Ingredient.of(ItemTags.FLOWERS), false));
        this.pollinateGoal = new PollinateGoal();
        this.goalSelector.addGoal(4, this.pollinateGoal);
        this.goalSelector.addGoal(5, new FollowParentGoal(this, (double)1.25F));
        this.goalSelector.addGoal(5, new BeeLocateNestGoal());
        this.goToNestGoal = new GoToNestGoal();
        this.goalSelector.addGoal(5, this.goToNestGoal);
        this.goToKnownFlowerGoal = new BeeGoToKnownFlowerGoal();
        this.goalSelector.addGoal(6, this.goToKnownFlowerGoal);
        this.goalSelector.addGoal(7, new BeeGrowCropGoal());
        this.goalSelector.addGoal(8, new BeeWanderGoal());
        this.goalSelector.addGoal(9, new FloatGoal(this));
        this.targetSelector.addGoal(1, (new BeeHurtByOtherGoal(this)).setAlertOthers(new Class[0]));
        this.targetSelector.addGoal(2, new BeeBecomeAngryTargetGoal(this));
        this.targetSelector.addGoal(3, new ResetUniversalAngerTargetGoal(this, true));
    }

    private boolean getFlag(int flag) {
        return ((Byte)this.entityData.get(DATA_FLAGS_ID) & flag) != 0;
    }

    private void setFlag(int flagID, boolean flagState) {
        // this is some wizard shit but its something to the effect of flipping the byte the flag is at to toggle the boolean
        if (flagState) {
            this.entityData.set(DATA_FLAGS_ID, (byte)((Byte)this.entityData.get(DATA_FLAGS_ID) | flagID));
        } else {
            this.entityData.set(DATA_FLAGS_ID, (byte)((Byte)this.entityData.get(DATA_FLAGS_ID) & ~flagID));
        }

    }

    public int getStingerData() {
        return this.entityData.get(STINGER);
    }

    public void setStingerData(int counter) {
        this.entityData.set(STINGER, counter);
    }

    private void setHasStung(boolean p_27926_) {
        this.setFlag(4, p_27926_);
    }

    public boolean hasStung() {
        return this.getFlag(4);
    }

    public boolean doHurtTarget(Entity p_27722_) {
        if (this.getStingerData() != -1) return false;

        boolean flag = p_27722_.hurt(DamageSource.sting(this), (float)((int)this.getAttributeValue(Attributes.ATTACK_DAMAGE)));
        if (flag) {
            this.doEnchantDamageEffects(this, p_27722_);
            if (p_27722_ instanceof LivingEntity) {
                ((LivingEntity)p_27722_).setStingerCount(((LivingEntity)p_27722_).getStingerCount() + 1);
                int i = 0;
                if (this.level.getDifficulty() == Difficulty.NORMAL) {
                    i = 10;
                } else if (this.level.getDifficulty() == Difficulty.HARD) {
                    i = 18;
                }

                if (i > 0) {
                    ((LivingEntity)p_27722_).addEffect(new MobEffectInstance(MobEffects.POISON, i * 20, 0), this);
                }
            }

            if (this.gender == Gender.SACRIFICIAL_WORKER) {
                this.setStingerData(1);
            }
            this.stopBeingAngry();
            this.playSound(SoundEvents.BEE_STING, 1.0F, 1.0F);
        }

        return flag;
    }

    @Override
    public int getRemainingPersistentAngerTime() {
        return this.entityData.get(DATA_REMAINING_ANGER_TIME);
    }

    @Override
    public void setRemainingPersistentAngerTime(int time) {
        this.entityData.set(DATA_REMAINING_ANGER_TIME, time);
    }

    @Override
    public @Nullable UUID getPersistentAngerTarget() {
        return this.persistentAngerTarget;
    }

    @Override
    public void setPersistentAngerTarget(@Nullable UUID target) {
        this.persistentAngerTarget = target;
    }

    @Override
    public void startPersistentAngerTimer() {
        this.setRemainingPersistentAngerTime(PERSISTENT_ANGER_TIME.sample(this.random));
    }

    @Override
    public boolean getOrSetIsFemale() {
        if (this.isFemale == null) {
            return this.isFemale = this.getGenes().isSexlinkedHeterozygous(0);
        }
        return this.isFemale;
    }

    @Override
    protected void setIsFemale(CompoundTag compound) {
        if (compound.contains("IsFemale")) {
            this.isFemale = compound.getBoolean("IsFemale");
        } else {
            int[] sGenes = compound.getCompound("Genetics").getIntArray("SGenes");
            this.isFemale = sGenes[0] != sGenes[1];
        }
    }
    
    @Override
    protected String getSpecies() {
        return "entity.eanimod.enhanced_bee";
    }

    @Override
    protected int getAdultAge() {
        if (this.adultAge != null) return this.adultAge;
        this.adultAge = GeneticAnimalsConfig.COMMON.adultAgeBee.get();
        return this.adultAge;
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
        /**
         *      if (this.isAngry) {
         *          do angry bee stuff.
         *          if (this.stingerCountdown == -1) {
         *              sting stuff
         *          }
         *      }
         */
        if (this.gender == Gender.SACRIFICIAL_WORKER && this.stingerCountdown > 0) {
            this.stingerCountdown++;
            if (this.stingerCountdown % 5 == 0 && this.random.nextInt(Mth.clamp(1200 - this.stingerCountdown, 1, 1200)) == 0) {
                this.hurt(DamageSource.GENERIC, this.getHealth());
            }
        }

        if (this.hasPollen() && this.getCropsGrownSincePollination() < 10 && this.random.nextFloat() < 0.05F) {
            for(int i = 0; i < this.random.nextInt(2) + 1; ++i) {
                this.spawnFluidParticle(this.level, this.getX() - (double)0.3F, this.getX() + (double)0.3F, this.getZ() - (double)0.3F, this.getZ() + (double)0.3F, this.getY((double)0.5F), ParticleTypes.FALLING_NECTAR);
            }
        }

        this.updateRollAmount();
    }

    private void spawnFluidParticle(Level p_27780_, double p_27781_, double p_27782_, double p_27783_, double p_27784_, double p_27785_, ParticleOptions p_27786_) {
        p_27780_.addParticle(p_27786_, Mth.lerp(p_27780_.random.nextDouble(), p_27781_, p_27782_), p_27785_, Mth.lerp(p_27780_.random.nextDouble(), p_27783_, p_27784_), (double)0.0F, (double)0.0F, (double)0.0F);
    }

    @Override
    public InteractionResult mobInteract(Player entityPlayer, InteractionHand hand) {
        ItemStack itemStack = entityPlayer.getItemInHand(hand);
        Item item = itemStack.getItem();

        if (item == ModItems.ENHANCED_BEE_EGG.get()) {
            return InteractionResult.SUCCESS;
        }

        return super.mobInteract(entityPlayer, hand);
    }

    @Override
    protected void lethalGenes() {

    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public BeeModelData getModelData() {
        return this.beeModelData;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void setModelData(AnimalModelData animalModelData) {
        this.beeModelData = (BeeModelData) animalModelData;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public String getTexture() {
        if (this.enhancedAnimalTextureGrouping == null) {
            this.setTexturePaths();
        } else if (this.reload) {
            this.reload = false;
            this.reloadTextures();
        }

        return getCompiledTextures("enhanced_bee");
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    protected void reloadTextures() {
        this.texturesIndexes.clear();
        this.enhancedAnimalTextures.clear();
        this.enhancedAnimalTextureGrouping = null;
        this.compiledTexture = null;
        this.setTexturePaths();
    }

    @Override
    protected void setTexturePaths() {
        if (this.getGenes() != null) {
            calculateBeeTexture(this, this.getGenes().getSexlinkedGenes(), this.isAngry(), this.hasPollen());
        }
    }

    @Override
    protected void setAlphaTexturePaths() {}

    @Override
    public void initilizeAnimalSize() {
        int[] genes = this.genetics.getSexlinkedGenes();
        float size = 1.0F;
        float px = 1.0F/16.0F;

        int thoraxSize = 0;
        int abdomenSize = 7;

        if (genes[0] != 1 || genes[1] != 1) {
            thoraxSize = 3;
            if (genes[0] >= 3) {
                thoraxSize+= genes[0]==4 ? 2 : 1;
            }

            if (genes[1] >= 3) {
                thoraxSize+= genes[1]==4 ? 2 : 1;
            }

            if (thoraxSize > 3 && (genes[2]!=1 && genes[3]!=1)) thoraxSize--;
        }

        if (genes[4] != 1 || genes[5] != 1) {
            abdomenSize++;
            if (genes[4] >= 3 || genes[5] >= 3) {
                abdomenSize++;
                if (genes[4] == 4 || genes[5] == 4) {
                    abdomenSize++;
                }
            }
        }
        if (genes[2]==3|| genes[3]==3) abdomenSize--;
        if (genes[6]==2 || genes[7]==2) {
            abdomenSize--;
        }
        if (genes[8]==2 || genes[9]==2) {
            abdomenSize--;
        }
        if (genes[10]==2 || genes[11]==2) {
            abdomenSize--;
        }

        if (abdomenSize < 4) abdomenSize = 4;

        int oversize = thoraxSize + abdomenSize;
        if (oversize > 7) {
            size = 7.0F / oversize;
        }

        this.setAnimalSize(size);
    }

    @Override
    protected EnhancedAnimalAbstract createEnhancedChild(Level world, EnhancedAnimalAbstract otherParent) {
        EnhancedBee enhancedBee = ENHANCED_BEE.get().create(this.level);

        this.wantsSons = this.random.nextBoolean(); //TODO we are probably going to want something different here

        if (enhancedBee != null) {
            Genes genes;
            if (this.wantsSons) {
                genes = new Genes(this.genetics.getGamite(true)).getGamite(false);
                enhancedBee.gender = Gender.DRONE;
                enhancedBee.setSireName("");
            } else {
                genes = new Genes(this.genetics).makeChild(this.getOrSetIsFemale(), this.mateGender, otherParent.getGenes());
                if (!genes.isSexlinkedHeterozygous(0)) {
                    enhancedBee.unbreedable = true;
                    enhancedBee.gender = Gender.DIPLOID_DRONE;
                }
                enhancedBee.setSireName(otherParent.getCustomName()==null ? "???" : otherParent.getCustomName().getString());
            }

            enhancedBee.setGenes(genes);
            enhancedBee.setSharedGenes(genes);
            enhancedBee.setDamName(this.getCustomName()==null ? "???" : this.getCustomName().getString());
            enhancedBee.setGrowingAge();
            enhancedBee.setBirthTime();
            enhancedBee.initilizeAnimalSize();
            enhancedBee.setEntityStatus(EntityState.CHILD_STAGE_ONE.toString());
            enhancedBee.moveTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), 0.0F);
        }

        return enhancedBee;
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

    @Override
    protected void fixGeneLengths() {
        if (this.genetics.getNumberOfSexlinkedGenes() < BEE_SEXLINKED_GENES_LENGTH) {
            this.genetics.setSexlinkedGene(Arrays.copyOf(this.genetics.getSexlinkedGenes(), BEE_SEXLINKED_GENES_LENGTH));
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);

        compound.putInt("StingerTimer", this.stingerCountdown);

        if (this.hasNest()) {
            compound.put("HivePos", NbtUtils.writeBlockPos(this.getNestPos()));
        }

        if (this.hasSavedFlowerPos()) {
            compound.put("FlowerPos", NbtUtils.writeBlockPos(this.getSavedFlowerPos()));
        }

        compound.putBoolean("HasPollen", this.hasPollen());
        compound.putBoolean("HasStung", this.hasStung());
        compound.putInt("TicksSincePollination", this.ticksWithoutPollenSinceExitingNest);
        compound.putInt("CannotEnterHiveTicks", this.stayOutOfNestCountdown);
        compound.putInt("CropsGrownSincePollination", this.numCropsGrownSincePollination);
        this.addPersistentAngerSaveData(compound);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);

        this.setStingerData(compound.getInt("StingerTimer"));

        this.nestPos = null;
        if (compound.contains("HivePos")) {
            this.nestPos = NbtUtils.readBlockPos(compound.getCompound("HivePos"));
        }

        this.savedFlowerPos = null;
        if (compound.contains("FlowerPos")) {
            this.savedFlowerPos = NbtUtils.readBlockPos(compound.getCompound("FlowerPos"));
        }

        super.readAdditionalSaveData(compound);
        this.setHasPollen(compound.getBoolean("HasPollen"));
        this.setHasStung(compound.getBoolean("HasStung"));
        this.ticksWithoutPollenSinceExitingNest = compound.getInt("TicksSincePollination");
        this.stayOutOfNestCountdown = compound.getInt("CannotEnterHiveTicks");
        this.numCropsGrownSincePollination = compound.getInt("CropsGrownSincePollination");
        this.readPersistentAngerSaveData(this.level, compound);
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor inWorld, DifficultyInstance difficulty, MobSpawnType spawnReason, @Nullable SpawnGroupData livingdata, @Nullable CompoundTag itemNbt) {
        livingdata = commonInitialSpawnSetup(inWorld, livingdata, getAdultAge(), 30000, 80000, spawnReason);



        return livingdata;
    }

    @Override
    protected Genes createInitialGenes(LevelAccessor world, BlockPos pos, boolean isDomestic) {

        return new BeeGeneticsInitialiser().generateNewGenetics(world, pos, isDomestic);
    }

    @Override
    public Genes createInitialBreedGenes(LevelAccessor world, BlockPos pos, String breed) {
        return new BeeGeneticsInitialiser().generateWithBreed(world, pos, breed);
    }

    @Override
    public void setInitialDefaults() {
        super.setInitialDefaults();
        this.setStingerData(this.getOrSetIsFemale() ? -1 : 0);
    }

    @Override
    public boolean causeFallDamage(float p_148750_, float p_148751_, DamageSource p_148752_) {
        return false;
    }

    @Override
    protected void checkFallDamage(double p_27754_, boolean p_27755_, BlockState p_27756_, BlockPos p_27757_) {}

    @Override
    public boolean isFlapping() {
        return this.isFlying() && this.tickCount % TICKS_PER_FLAP == 0;
    }

    @Override
    public boolean isFlying() {
        return false;
    }

    private enum Gender {
        QUEEN,
        QUEEN_S,
        SOCIAL_QUEEN,
        SOCIAL_QUEEN_S,
        FEMALE,
        SOCIAL_FEMALE,
        WORKER,
        SACRIFICIAL_WORKER,
        DRONE,
        DIPLOID_DRONE
    }

    /**
     *      Thoughts
     */

    public boolean isAngry() {
        return this.getRemainingPersistentAngerTime() > 0;
    }

    /**
     *      Animation Triggers
     */

    private boolean isRolling() {
        return this.getFlag(2);
    }

    private void setRolling(boolean p_27930_) {
        this.setFlag(2, p_27930_);
    }

    public float getRollAmount(float p_27936_) {
        return Mth.lerp(p_27936_, this.rollAmountO, this.rollAmount);
    }

    private void updateRollAmount() {
        this.rollAmountO = this.rollAmount;
        if (this.isRolling()) {
            this.rollAmount = Math.min(1.0F, this.rollAmount + 0.2F);
        } else {
            this.rollAmount = Math.max(0.0F, this.rollAmount - 0.24F);
        }

    }

    @Override
    protected void runLivingTickClient() {
        super.runLivingTickClient();

        if (this.hasPollen() && this.getCropsGrownSincePollination() < 10 && this.random.nextFloat() < 0.05F) {
            for(int i = 0; i < this.random.nextInt(2) + 1; ++i) {
                this.spawnFluidParticle(this.level, this.getX() - (double)0.3F, this.getX() + (double)0.3F, this.getZ() - (double)0.3F, this.getZ() + (double)0.3F, this.getY((double)0.5F), ParticleTypes.FALLING_NECTAR);
            }
        }
    }

    /**
     *      Navigation Util
     */
    public void aiStep() {
        super.aiStep();
        if (!this.level.isClientSide) {
            if (this.stayOutOfNestCountdown > 0) {
                --this.stayOutOfNestCountdown;
            }

            if (this.remainingCooldownBeforeLocatingNewNest > 0) {
                --this.remainingCooldownBeforeLocatingNewNest;
            }

            if (this.remainingCooldownBeforeLocatingNewFlower > 0) {
                --this.remainingCooldownBeforeLocatingNewFlower;
            }

            boolean flag = this.isAngry() && !this.hasStung() && this.getTarget() != null && this.getTarget().distanceToSqr(this) < (double)4.0F;
            this.setRolling(flag);
            if (this.tickCount % 20 == 0 && !this.isNestValid()) {
                this.nestPos = null;
            }
        }
    }

    protected PathNavigation createNavigation(Level p_27815_) {
        FlyingPathNavigation flyingpathnavigation = new FlyingPathNavigation(this, p_27815_) {
            public boolean isStableDestination(BlockPos p_27947_) {
                return !this.level.getBlockState(p_27947_.below()).isAir();
            }

            public void tick() {
                if (!EnhancedBee.this.pollinateGoal.isPollinating()) {
                    super.tick();
                }

            }
        };
        flyingpathnavigation.setCanOpenDoors(false);
        flyingpathnavigation.setCanFloat(false);
        flyingpathnavigation.setCanPassDoors(true);
        return flyingpathnavigation;
    }

    int getCropsGrownSincePollination() {
        return this.numCropsGrownSincePollination;
    }

    private void resetNumCropsGrownSincePollination() {
        this.numCropsGrownSincePollination = 0;
    }

    void incrementNumCropsGrownSincePollination() {
        ++this.numCropsGrownSincePollination;
    }

    boolean isNestValid() {
        if (!this.hasNest()) {
            return false;
        } else {
            BlockEntity blockentity = this.level.getBlockEntity(this.nestPos);
            return blockentity instanceof BeehiveBlockEntity;
        }
    }

    public void resetTicksWithoutPollenSinceExitingNest() {
        this.ticksWithoutPollenSinceExitingNest = 0;
    }

    public boolean hasPollen() {
        return this.getFlag(8);
    }

    void setHasPollen(boolean p_27920_) {
        if (p_27920_) {
            this.resetTicksWithoutPollenSinceExitingNest();
        }
        reloadTextures();

        this.setFlag(8, p_27920_);
    }

    private boolean isHiveNearFire() {
        if (this.nestPos == null) {
            return false;
        } else {
            BlockEntity blockentity = this.level.getBlockEntity(this.nestPos);
            return blockentity instanceof BeehiveBlockEntity && ((BeehiveBlockEntity)blockentity).isFireNearby();
        }
    }

    public boolean hasNest() {
        return this.nestPos != null;
    }

    public BlockPos getNestPos() {
        return this.nestPos;
    }

    private boolean isTiredOfLookingForPollen() {
        return this.ticksWithoutPollenSinceExitingNest > 3600;
    }

    boolean wantsToEnterNest() {
        if (this.stayOutOfNestCountdown <= 0 && !this.pollinateGoal.isPollinating() && !this.hasStung() && this.getTarget() == null) {
            boolean flag = this.isTiredOfLookingForPollen() || this.level.isRaining() || this.level.isNight() || this.hasPollen();
            return flag && !this.isHiveNearFire();
        } else {
            return false;
        }
    }

    private boolean doesHiveHaveSpace(BlockPos p_27885_) {
        BlockEntity blockentity = this.level.getBlockEntity(p_27885_);
        return true;
        //TODO Add logic for if a bee can use a hive
//        if (blockentity instanceof BeehiveBlockEntity) {
//            return !((BeehiveBlockEntity)blockentity).isFull();
//        } else {
//            return false;
//        }
    }

    boolean closerThan(BlockPos p_27817_, int p_27818_) {
        return p_27817_.closerThan(this.blockPosition(), (double)p_27818_);
    }

    boolean isTooFarAway(BlockPos p_27890_) {
        return !this.closerThan(p_27890_, 32);
    }

    void pathfindRandomlyTowards(BlockPos p_27881_) {
        Vec3 vec3 = Vec3.atBottomCenterOf(p_27881_);
        int i = 0;
        BlockPos blockpos = this.blockPosition();
        int j = (int)vec3.y - blockpos.getY();
        if (j > 2) {
            i = 4;
        } else if (j < -2) {
            i = -4;
        }

        int k = 6;
        int l = 8;
        int i1 = blockpos.distManhattan(p_27881_);
        if (i1 < 15) {
            k = i1 / 2;
            l = i1 / 2;
        }

        Vec3 vec31 = AirRandomPos.getPosTowards(this, k, l, i, vec3, (double)((float)Math.PI / 10F));
        if (vec31 != null) {
            this.navigation.setMaxVisitedNodesMultiplier(0.5F);
            this.navigation.moveTo(vec31.x, vec31.y, vec31.z, (double)1.0F);
        }

    }

    boolean isFlowerValid(BlockPos p_27897_) {
        return this.level.isLoaded(p_27897_) && this.level.getBlockState(p_27897_).is(BlockTags.FLOWERS);
    }

    @Nullable
    public BlockPos getSavedFlowerPos() {
        return this.savedFlowerPos;
    }

    public boolean hasSavedFlowerPos() {
        return this.savedFlowerPos != null;
    }

    public void setSavedFlowerPos(BlockPos p_27877_) {
        this.savedFlowerPos = p_27877_;
    }


    /**
     *      Navigation
     */

    abstract class BaseBeeGoal extends Goal {
        BaseBeeGoal() {
        }

        public abstract boolean canBeeUse();

        public abstract boolean canBeeContinueToUse();

        public boolean canUse() {
            return this.canBeeUse() && !EnhancedBee.this.isAngry();
        }

        public boolean canContinueToUse() {
            return this.canBeeContinueToUse() && !EnhancedBee.this.isAngry();
        }
    }

    class BeeAttackGoal extends MeleeAttackGoal {
        BeeAttackGoal(PathfinderMob p_27960_, double p_27961_, boolean p_27962_) {
            super(p_27960_, p_27961_, p_27962_);
        }

        public boolean canUse() {
            return super.canUse() && EnhancedBee.this.isAngry() && !EnhancedBee.this.hasStung();
        }

        public boolean canContinueToUse() {
            return super.canContinueToUse() && EnhancedBee.this.isAngry() && !EnhancedBee.this.hasStung();
        }
    }

    static class BeeBecomeAngryTargetGoal extends NearestAttackableTargetGoal<Player> {
        BeeBecomeAngryTargetGoal(EnhancedBee p_27966_) {
            super(p_27966_, Player.class, 10, true, false, p_27966_::isAngryAt);
        }

        public boolean canUse() {
            return this.beeCanTarget() && super.canUse();
        }

        public boolean canContinueToUse() {
            boolean flag = this.beeCanTarget();
            if (flag && this.mob.getTarget() != null) {
                return super.canContinueToUse();
            } else {
                this.targetMob = null;
                return false;
            }
        }

        private boolean beeCanTarget() {
            EnhancedBee bee = (EnhancedBee)this.mob;
            return bee.isAngry() && !bee.hasStung();
        }
    }

    class BeeEnterHiveGoal extends BaseBeeGoal {
        BeeEnterHiveGoal() {
        }

        public boolean canBeeUse() {
            if (EnhancedBee.this.hasNest() && EnhancedBee.this.wantsToEnterNest() && EnhancedBee.this.nestPos.closerToCenterThan(EnhancedBee.this.position(), (double)2.0F)) {
                BlockEntity blockentity = EnhancedBee.this.level.getBlockEntity(EnhancedBee.this.nestPos);
                return true; //TODO this needs some checks for if the nest is full or whatever
            }

            return false;
        }

        public boolean canBeeContinueToUse() {
            return false;
        }

        public void start() {
            BlockEntity blockentity = EnhancedBee.this.level.getBlockEntity(EnhancedBee.this.nestPos);
            if (blockentity instanceof BeehiveBlockEntity beehiveblockentity) {
                beehiveblockentity.addOccupant(EnhancedBee.this, EnhancedBee.this.hasPollen());
            }

        }
    }

    public class GoToNestGoal extends BaseBeeGoal {
        public static final int MAX_TRAVELLING_TICKS = 600;
        int travellingTicks;
        private static final int MAX_BLACKLISTED_TARGETS = 3;
        final List<BlockPos> blacklistedTargets;
        @Nullable
        private Path lastPath;
        private static final int TICKS_BEFORE_HIVE_DROP = 60;
        private int ticksStuck;

        GoToNestGoal() {
            this.travellingTicks = EnhancedBee.this.level.random.nextInt(10);
            this.blacklistedTargets = Lists.newArrayList();
            this.setFlags(EnumSet.of(Flag.MOVE));
        }

        public boolean canBeeUse() {
            return EnhancedBee.this.nestPos != null && !EnhancedBee.this.hasRestriction() && EnhancedBee.this.wantsToEnterNest() && !this.hasReachedTarget(EnhancedBee.this.nestPos) && EnhancedBee.this.level.getBlockState(EnhancedBee.this.nestPos).is(BlockTags.BEEHIVES);
        }

        public boolean canBeeContinueToUse() {
            return this.canBeeUse();
        }

        public void start() {
            this.travellingTicks = 0;
            this.ticksStuck = 0;
            super.start();
        }

        public void stop() {
            this.travellingTicks = 0;
            this.ticksStuck = 0;
            EnhancedBee.this.navigation.stop();
            EnhancedBee.this.navigation.resetMaxVisitedNodesMultiplier();
        }

        public void tick() {
            if (EnhancedBee.this.nestPos != null) {
                ++this.travellingTicks;
                if (this.travellingTicks > this.adjustedTickDelay(600)) {
                    this.dropAndBlacklistHive();
                } else if (!EnhancedBee.this.navigation.isInProgress()) {
                    if (!EnhancedBee.this.closerThan(EnhancedBee.this.nestPos, 16)) {
                        if (EnhancedBee.this.isTooFarAway(EnhancedBee.this.nestPos)) {
                            this.dropHive();
                        } else {
                            EnhancedBee.this.pathfindRandomlyTowards(EnhancedBee.this.nestPos);
                        }
                    } else {
                        boolean flag = this.pathfindDirectlyTowards(EnhancedBee.this.nestPos);
                        if (!flag) {
                            this.dropAndBlacklistHive();
                        } else if (this.lastPath != null && EnhancedBee.this.navigation.getPath().sameAs(this.lastPath)) {
                            ++this.ticksStuck;
                            if (this.ticksStuck > 60) {
                                this.dropHive();
                                this.ticksStuck = 0;
                            }
                        } else {
                            this.lastPath = EnhancedBee.this.navigation.getPath();
                        }
                    }
                }
            }

        }

        private boolean pathfindDirectlyTowards(BlockPos p_27991_) {
            EnhancedBee.this.navigation.setMaxVisitedNodesMultiplier(10.0F);
            EnhancedBee.this.navigation.moveTo((double)p_27991_.getX(), (double)p_27991_.getY(), (double)p_27991_.getZ(), (double)1.0F);
            return EnhancedBee.this.navigation.getPath() != null && EnhancedBee.this.navigation.getPath().canReach();
        }

        boolean isTargetBlacklisted(BlockPos p_27994_) {
            return this.blacklistedTargets.contains(p_27994_);
        }

        private void blacklistTarget(BlockPos p_27999_) {
            this.blacklistedTargets.add(p_27999_);

            while(this.blacklistedTargets.size() > 3) {
                this.blacklistedTargets.remove(0);
            }

        }

        void clearBlacklist() {
            this.blacklistedTargets.clear();
        }

        private void dropAndBlacklistHive() {
            if (EnhancedBee.this.nestPos != null) {
                this.blacklistTarget(EnhancedBee.this.nestPos);
            }

            this.dropHive();
        }

        private void dropHive() {
            EnhancedBee.this.nestPos = null;
            EnhancedBee.this.remainingCooldownBeforeLocatingNewNest = 200;
        }

        private boolean hasReachedTarget(BlockPos p_28002_) {
            if (EnhancedBee.this.closerThan(p_28002_, 2)) {
                return true;
            } else {
                Path path = EnhancedBee.this.navigation.getPath();
                return path != null && path.getTarget().equals(p_28002_) && path.canReach() && path.isDone();
            }
        }
    }

    public class BeeGoToKnownFlowerGoal extends BaseBeeGoal {
        private static final int MAX_TRAVELLING_TICKS = 600;
        int travellingTicks;

        BeeGoToKnownFlowerGoal() {
            this.travellingTicks = EnhancedBee.this.level.random.nextInt(10);
            this.setFlags(EnumSet.of(Flag.MOVE));
        }

        public boolean canBeeUse() {
            return EnhancedBee.this.savedFlowerPos != null && !EnhancedBee.this.hasRestriction() && this.wantsToGoToKnownFlower() && EnhancedBee.this.isFlowerValid(EnhancedBee.this.savedFlowerPos) && !EnhancedBee.this.closerThan(EnhancedBee.this.savedFlowerPos, 2);
        }

        public boolean canBeeContinueToUse() {
            return this.canBeeUse();
        }

        public void start() {
            this.travellingTicks = 0;
            super.start();
        }

        public void stop() {
            this.travellingTicks = 0;
            EnhancedBee.this.navigation.stop();
            EnhancedBee.this.navigation.resetMaxVisitedNodesMultiplier();
        }

        public void tick() {
            if (EnhancedBee.this.savedFlowerPos != null) {
                ++this.travellingTicks;
                if (this.travellingTicks > this.adjustedTickDelay(600)) {
                    EnhancedBee.this.savedFlowerPos = null;
                } else if (!EnhancedBee.this.navigation.isInProgress()) {
                    if (EnhancedBee.this.isTooFarAway(EnhancedBee.this.savedFlowerPos)) {
                        EnhancedBee.this.savedFlowerPos = null;
                    } else {
                        EnhancedBee.this.pathfindRandomlyTowards(EnhancedBee.this.savedFlowerPos);
                    }
                }
            }

        }

        private boolean wantsToGoToKnownFlower() {
            return EnhancedBee.this.ticksWithoutPollenSinceExitingNest > 2400;
        }
    }

    class BeeGrowCropGoal extends BaseBeeGoal {
        static final int GROW_CHANCE = 30;

        BeeGrowCropGoal() {
        }

        public boolean canBeeUse() {
            if (EnhancedBee.this.getCropsGrownSincePollination() >= 10) {
                return false;
            } else if (EnhancedBee.this.random.nextFloat() < 0.3F) {
                return false;
            } else {
                return EnhancedBee.this.hasPollen() && EnhancedBee.this.isNestValid();
            }
        }

        public boolean canBeeContinueToUse() {
            return this.canBeeUse();
        }

        public void tick() {
            if (EnhancedBee.this.random.nextInt(this.adjustedTickDelay(30)) == 0) {
                for(int i = 1; i <= 2; ++i) {
                    BlockPos blockpos = EnhancedBee.this.blockPosition().below(i);
                    BlockState blockstate = EnhancedBee.this.level.getBlockState(blockpos);
                    Block block = blockstate.getBlock();
                    boolean flag = false;
                    IntegerProperty integerproperty = null;
                    if (blockstate.is(BlockTags.BEE_GROWABLES)) {
                        if (block instanceof CropBlock) {
                            CropBlock cropblock = (CropBlock)block;
                            if (!cropblock.isMaxAge(blockstate)) {
                                flag = true;
                                integerproperty = cropblock.getAgeProperty();
                            }
                        } else if (block instanceof StemBlock) {
                            int j = (Integer)blockstate.getValue(StemBlock.AGE);
                            if (j < 7) {
                                flag = true;
                                integerproperty = StemBlock.AGE;
                            }
                        } else if (blockstate.is(Blocks.SWEET_BERRY_BUSH)) {
                            int k = (Integer)blockstate.getValue(SweetBerryBushBlock.AGE);
                            if (k < 3) {
                                flag = true;
                                integerproperty = SweetBerryBushBlock.AGE;
                            }
                        } else if (blockstate.is(Blocks.CAVE_VINES) || blockstate.is(Blocks.CAVE_VINES_PLANT)) {
                            ((BonemealableBlock)blockstate.getBlock()).performBonemeal((ServerLevel)EnhancedBee.this.level, EnhancedBee.this.random, blockpos, blockstate);
                        }

                        if (flag) {
                            EnhancedBee.this.level.levelEvent(2005, blockpos, 0);
                            EnhancedBee.this.level.setBlockAndUpdate(blockpos, (BlockState)blockstate.setValue(integerproperty, (Integer)blockstate.getValue(integerproperty) + 1));
                            EnhancedBee.this.incrementNumCropsGrownSincePollination();
                        }
                    }
                }
            }

        }
    }

    class BeeHurtByOtherGoal extends HurtByTargetGoal {
        BeeHurtByOtherGoal(EnhancedBee p_28033_) {
            super(p_28033_, new Class[0]);
        }

        public boolean canContinueToUse() {
            return EnhancedBee.this.isAngry() && super.canContinueToUse();
        }

        protected void alertOther(Mob p_28035_, LivingEntity p_28036_) {
            if (p_28035_ instanceof EnhancedBee && this.mob.hasLineOfSight(p_28036_)) {
                p_28035_.setTarget(p_28036_);
            }

        }
    }

    class BeeLocateNestGoal extends BaseBeeGoal {
        BeeLocateNestGoal() {
        }

        public boolean canBeeUse() {
            return EnhancedBee.this.remainingCooldownBeforeLocatingNewNest == 0 && !EnhancedBee.this.hasNest() && EnhancedBee.this.wantsToEnterNest();
        }

        public boolean canBeeContinueToUse() {
            return false;
        }

        public void start() {
            EnhancedBee.this.remainingCooldownBeforeLocatingNewNest = 200;
            List<BlockPos> list = this.findNearbyNestableLocations(ModTags.Blocks.BURROW_NEST);
            if (!list.isEmpty()) {
                Collections.shuffle(list);
                for(BlockPos blockpos : list) {
                    if (!EnhancedBee.this.goToNestGoal.isTargetBlacklisted(blockpos)) {
                        EnhancedBee.this.nestPos = blockpos;
                        return;
                    }
                }

                EnhancedBee.this.goToNestGoal.clearBlacklist();
                EnhancedBee.this.nestPos = (BlockPos)list.get(0);
            }

        }

        private List<BlockPos> findNearbyHivesWithSpace() {
            BlockPos blockpos = EnhancedBee.this.blockPosition();
            PoiManager poimanager = ((ServerLevel)EnhancedBee.this.level).getPoiManager();
            Stream<PoiRecord> stream = poimanager.getInRange((p_28045_) -> p_28045_ == PoiType.BEEHIVE || p_28045_ == PoiType.BEE_NEST, blockpos, 20, PoiManager.Occupancy.ANY);
            return (List)stream.map(PoiRecord::getPos).filter(EnhancedBee.this::doesHiveHaveSpace).sorted(Comparator.comparingDouble((p_148811_) -> p_148811_.distSqr(blockpos))).collect(Collectors.toList());
        }

        private List<BlockPos> findNearbyNestableLocations(TagKey<Block> nestMaterial) {
            HashMap<BlockPos, Integer> nestSites = new HashMap<>();
            BlockPos blockpos = EnhancedBee.this.blockPosition();
            INestEggCapability nestCapability = EnhancedBee.this.level.getCapability(NestCapabilityProvider.NEST_CAP, null).orElse(new NestCapabilityProvider()); //TODO move this up the chain more?

            /**
             *      North   : -Z    LD : 180
             *      South   : +Z    LD : 0/360
             *      West    : -X    LD : 90
             *      East    : +X    LD : 270
             *
             */

            float lookdirection = EnhancedBee.this.getYRot() % 360;

            if (lookdirection > 45 && lookdirection < 135) {
                System.out.println("I'm looking WEST!");
            } else if (lookdirection > 135 && lookdirection < 225) {
                System.out.println("I'm looking NORTH!");
            } else if (lookdirection > 225 && lookdirection < 315) {
                System.out.println("I'm looking EAST!");
            } else {
                System.out.println("I'm looking SOUTH!");
            }

            List<BlockPos> nestList = new ArrayList<>();

            lookFor(blockpos, (int) EnhancedBee.this.getYRot(), 10, EnhancedBee.this.level, nestMaterial, nestList);

            return nestList;
        }
    }

    private final static int[][] riserun = new int[][]{
            {10    },
            {11,   5},
            {12,   2,7},
            {13,   2,5,8},
            {13,   1,3,6,8},
            {14,   1,3,4,6,8},
            {14,   1,2,3,5,6,8},
            {15,   1,2,3,4,5,6,7,8}
    };

    private void lookFor(BlockPos center, int looking, int range, Level level, TagKey<Block> nestMaterial, List<BlockPos> found) {
        List<BlockPos> air = new ArrayList<>();
        List<BlockPos> solid = new ArrayList<>();

        boolean[] rays = new boolean[31];

        int sectionX = ((looking + 675) % 360) / 90; // which diagonally drawn quadrant to start looking
        looking = (looking+630) % 360;
        int sectionT = looking / 90;
        looking /= 6;

        int r = looking % 15;
        if (r > 7) r = 15 - r;

        boolean xFirst = sectionX == 1 || sectionX == 3;

        int xB = xFirst ? 2 - sectionX : 0; // blockpos X we are on rn //these are not intuitive since the quad doesn't exactly start on the axis
        int zB = xFirst ? 0 : 1 - sectionX; // blockpos Z we are on rn

        int xStartDirection = looking > 0 && looking <= 30 ? 1 : -1;
        int zStartDirection = looking <= 15 || looking > 30 ? 1 : -1;

        int riserunInx = 1;

        sayToChat("starting scan at " + (looking) + " with angle value of " + r + ", riserun size is " + riserun[r][0]);
        sayToChat("    < quad = " + sectionT + " & " + sectionX + " || (" + xB + "," + zB + ")>");

        for (int i = 1; i < riserun[r][0]; i++) {

            int xPos = xB;
            int zPos = zB;

            sayToChat("start loop   -->   i="+i+"  ("+xPos+","+zPos+")");

            for (int c = 0; c <= i*2; c++) {

//                if (rays[c*((15/i))]) {
//                    if ((c == 0 || rays[(c+1)*(15/(i-1))]) || (rays[(c-1)*(15/(i-1))] || c == i*2)) {
                        BlockPos blockPos = center.offset(xPos, 0, zPos);
                        BlockState blockState = level.getBlockState(blockPos);
                        if (!blockState.isAir()) {
                            if (isNestableBlock(blockState, nestMaterial)) {
                                found.add(blockPos);
                            } else {
                                solid.add(blockPos);
                            }

                            rays[c*(15/i)] = false;
                        } else {
                            solid.add(blockPos);
                        }
//                    }
//                }
                if (c==0 || c == i*2) air.add(blockPos);

                sayToChat("c = " +c+ "   ("+xPos+","+zPos+")");

                /**
                 *      North   : -Z    LD : 180
                 *      South   : +Z    LD : 0/360
                 *      West    : -X    LD : 90
                 *      East    : +X    LD : 270
                 *
                 */

                if (zPos >= 0 && xPos < 0) {
                    xPos++;
                    zPos++;
                } else if (zPos > 0) {
                    xPos++;
                    zPos--;
                } else if (xPos > 0) {
                    xPos--;
                    zPos--;
                } else {
                    xPos--;
                    zPos++;
                }
            }

            if (xFirst) {
                if ((riserunInx < riserun[r].length) && (riserun[r][riserunInx] == Mth.abs(xB))) {
                    sayToChat("z instead at index " + riserunInx +"  : " + zB+" + "+zStartDirection + " = " + (zB+zStartDirection));
                    zB += zStartDirection;
                    riserunInx++;
                } else {
                    sayToChat("added to x first : " + xB+" + "+xStartDirection+" = " + (xB+xStartDirection));
                    xB += xStartDirection;
                }
            } else {
                if ((riserunInx < riserun[r].length) && (riserun[r][riserunInx] == Mth.abs(zB))) {
                    sayToChat("x instead at index " + riserunInx +"  : " + xB+" + "+xStartDirection+" = " + (xB+xStartDirection));
                    xB += xStartDirection;
                    riserunInx++;
                } else {
                    sayToChat("added to z first : " + zB+" + "+zStartDirection + " = " + (zB+zStartDirection));
                    zB += zStartDirection;
                }
            }
        }



        EAParticlePacket airParticlePacket = new EAPPAir(getPacketPos(air));
        EnhancedAnimals.channel.send(PacketDistributor.TRACKING_ENTITY.with(() -> this), airParticlePacket);
        EAParticlePacket solidParticlePacket = new EAPPSolid(getPacketPos(solid));
        EnhancedAnimals.channel.send(PacketDistributor.TRACKING_ENTITY.with(() -> this), solidParticlePacket);
        EAParticlePacket nestParticlePacket = new EAPPHappy(getPacketPos(found));
        EnhancedAnimals.channel.send(PacketDistributor.TRACKING_ENTITY.with(() -> this), nestParticlePacket);

    }

    private static int[] getPacketPos(List<BlockPos> blockPos) {
        int[] packedPos = new int[blockPos.size()*3];
        int i = 0;
        for (BlockPos pos : blockPos) {
            packedPos[i] = pos.getX();
            packedPos[i+1] = pos.getY()+1;
            packedPos[i+2] = pos.getZ();
            i += 3;
        }

        return packedPos;
    }

    private void lookForOld(BlockPos center, int looking, int range, Level level, TagKey<Block> nestMaterial, List<BlockPos> found) {
        boolean[] rays = new boolean[31];
        int sectionX = ((looking - 45) % 360) / 90; // which diagonally drawn quadrant to start looking
        looking = (looking + 90) % 360; // where the scan starts
        int sectionT = looking / 90;  // which quadrant to start looking
        looking /= 6; // cut to 6 degrees
        int r = looking % 15;
        if (r > 7) r = 15 - r;

        int zD = sectionT == 0 || sectionT == 3 ? 1 : -1; // sets the direction increment
        int xD = sectionT == 2 || sectionT == 3 ? 1 : -1;
        boolean xFirst = sectionX == 1 || sectionX == 3;

        int xB = 0; // blockpos X we are on rn
        int zB = 0; // blockpos Z we are on rn

        int n = riserun[r].length > 1 ? riserun[r][1] : 0;

        for (int dist = 1; dist <= range; dist++) {
            int scanSize = (dist*2);
            int rayScale = 30/scanSize;

            for (int a = 0; a <= scanSize; a++) {
                if (xFirst) {
                    xB += xD;
                } else {
                    zB += zD;
                }

                int rayInx = a * rayScale;

                if (rays[rayInx]) continue; // I need this to check if the rays that would contain blocks that would block this ray is blocked and if it is then block it also

                BlockPos blockPos = center.offset(xB, 0, zB);
                BlockState blockState = level.getBlockState(blockPos);

                if (!blockState.isAir()) {
                    rays[rayInx] = true;

                    // solid was found
                    if (isNestableBlock(blockState, nestMaterial)) {
                        // the solid looks like a nest!
                        sayToChat("I saw a nestable " + blockState.getBlock() + " block at " + blockPos);
                        found.add(blockPos);
                    } else sayToChat("I saw a solid " + blockState.getBlock() + " block at " + blockPos); //TODO remove this
                }

                // this swaps the direction of the diagonal scan when it goes over an axis
                if (xB == 0 || zB == 0) {
                    switch (sectionT) {
                        case 0 -> {
                            xD = 1;
                            zD = 1;
                        }
                        case 1 -> {
                            xD = -1;
                            zD = 1;
                        }
                        case 2 -> {
                            xD = -1;
                            zD = -1;
                        }
                        case 3 -> {
                            xD = 1;
                            zD = -1;
                        }
                    }
                }

            }

            // use riserun to reset the diamond scan

        }

//
//        for (int a = 0; a <= 30; a++) {
//            // ray level
//            sayToChat("scanning ray " + looking + "  ( " + a + " out of 30 ) ...");
//
//            int rayLength = rays[r][0];
//
//            for (int i = 1; i <= rayLength ; i++) {
//                // block on the ray level
//                if (xFirst) {
//                    xB += xD;
//                } else {
//                    zB += zD;
//                }
//
//                BlockPos blockPos = center.offset(xB, 0, zB);
//                BlockState blockState = level.getBlockState(blockPos);
//                if (!blockState.isAir()) {
//                    // solid was found
//                    if (isNestableBlock(blockState, nestMaterial)) {
//                        // the solid looks like a nest!
//                        sayToChat("I saw a nestable " + blockState.getBlock() + " block at " + blockPos);
//                        found.add(blockPos);
//                    }
//                    sayToChat("I saw a solid " + blockState.getBlock() + " block at " + blockPos);
//
//                    if (i != rayLength) {
//                        resetBlock = false;
//                    }
//
//                    break;
//
//                } else {
//                    sayToChat(i + " block is air at " + blockPos);
//                    if (n > 0) {
//                        if (n == Mth.abs(xFirst ? xD : zD)) {
//                            n++;
//                            if (xFirst) {
//                                xB--;
//                                zB += zD;
//                            } else {
//                                xB += zB;
//                                zB--;
//                            }
//                        }
//                    }
//                }
//            }
//
//            looking--; // next ray
//            if (looking < 0) {
//                looking += 60;
//            }
//
//            r = looking % 15;
//            if (r > 7) r = 15 - r;
//
//            if (looking % 15 == 0) {
//                sectionT--;
//                zD = sectionT == 0 || sectionT == 3 ? 1 : -1;
//                xD = sectionT == 2 || sectionT == 3 ? 1 : -1;
//            } else if (looking % 15 == 7) {
//                sectionX--;
//                xFirst = !xFirst;
//            }
//
//            n = rays[r].length > 1 ? rays[r][1] : 0;
//
//            if (resetBlock) {
//                xB = 0;
//                zB = 0;
//            } else {
//
//                resetBlock = true;
//            }
//
//        }

    }

    private static void sayToChat(String txt) {
        System.out.println(txt);
    }

    @Contract(value = "_, _, _ -> new", pure = true)
    private int @NotNull [] offsets(int x, int y, int z) {
        return new int[] {x, y, z};
    }

    class BeeLookControl extends LookControl {
        BeeLookControl(Mob p_28059_) {
            super(p_28059_);
        }

        public void tick() {
            if (!EnhancedBee.this.isAngry()) {
                super.tick();
            }

        }

        protected boolean resetXRotOnTick() {
            return !EnhancedBee.this.pollinateGoal.isPollinating();
        }
    }

    class PollinateGoal extends BaseBeeGoal {
        private final Predicate<BlockState> VALID_POLLINATION_BLOCKS = (p_28074_) -> {
            if (p_28074_.is(BlockTags.FLOWERS)) {
                if (p_28074_.is(Blocks.SUNFLOWER)) {
                    return p_28074_.getValue(DoublePlantBlock.HALF) == DoubleBlockHalf.UPPER;
                } else {
                    return true;
                }
            } else {
                return false;
            }
        };
        private int successfulPollinatingTicks;
        private int lastSoundPlayedTick;
        private boolean pollinating;
        @Nullable
        private Vec3 hoverPos;
        private int pollinatingTicks;

        PollinateGoal() {
            this.setFlags(EnumSet.of(Flag.MOVE));
        }

        public boolean canBeeUse() {
            if (EnhancedBee.this.remainingCooldownBeforeLocatingNewFlower > 0) {
                return false;
            } else if (EnhancedBee.this.hasPollen()) {
                return false;
            } else if (EnhancedBee.this.level.isRaining()) {
                return false;
            } else {
                Optional<BlockPos> optional = this.findNearbyFlower();
                if (optional.isPresent()) {
                    EnhancedBee.this.savedFlowerPos = (BlockPos)optional.get();
                    EnhancedBee.this.navigation.moveTo((double)EnhancedBee.this.savedFlowerPos.getX() + (double)0.5F, (double)EnhancedBee.this.savedFlowerPos.getY() + (double)0.5F, (double)EnhancedBee.this.savedFlowerPos.getZ() + (double)0.5F, (double)1.2F);
                    return true;
                } else {
                    EnhancedBee.this.remainingCooldownBeforeLocatingNewFlower = Mth.nextInt(EnhancedBee.this.random, 20, 60);
                    return false;
                }
            }
        }

        public boolean canBeeContinueToUse() {
            if (!this.pollinating) {
                return false;
            } else if (!EnhancedBee.this.hasSavedFlowerPos()) {
                return false;
            } else if (EnhancedBee.this.level.isRaining()) {
                return false;
            } else if (this.hasPollinatedLongEnough()) {
                return EnhancedBee.this.random.nextFloat() < 0.2F;
            } else if (EnhancedBee.this.tickCount % 20 == 0 && !EnhancedBee.this.isFlowerValid(EnhancedBee.this.savedFlowerPos)) {
                EnhancedBee.this.savedFlowerPos = null;
                return false;
            } else {
                return true;
            }
        }

        private boolean hasPollinatedLongEnough() {
            return this.successfulPollinatingTicks > 400;
        }

        boolean isPollinating() {
            return this.pollinating;
        }

        void stopPollinating() {
            this.pollinating = false;
        }

        public void start() {
            this.successfulPollinatingTicks = 0;
            this.pollinatingTicks = 0;
            this.lastSoundPlayedTick = 0;
            this.pollinating = true;
            EnhancedBee.this.resetTicksWithoutPollenSinceExitingNest();
        }

        public void stop() {
            if (this.hasPollinatedLongEnough()) {
                EnhancedBee.this.setHasPollen(true);
            }

            this.pollinating = false;
            EnhancedBee.this.navigation.stop();
            EnhancedBee.this.remainingCooldownBeforeLocatingNewFlower = 200;
        }

        public boolean requiresUpdateEveryTick() {
            return true;
        }

        public void tick() {
            ++this.pollinatingTicks;
            if (this.pollinatingTicks > 600) {
                EnhancedBee.this.savedFlowerPos = null;
            } else {
                Vec3 vec3 = Vec3.atBottomCenterOf(EnhancedBee.this.savedFlowerPos).add((double)0.0F, (double)0.6F, (double)0.0F);
                if (vec3.distanceTo(EnhancedBee.this.position()) > (double)1.0F) {
                    this.hoverPos = vec3;
                    this.setWantedPos();
                } else {
                    if (this.hoverPos == null) {
                        this.hoverPos = vec3;
                    }

                    boolean flag = EnhancedBee.this.position().distanceTo(this.hoverPos) <= 0.1;
                    boolean flag1 = true;
                    if (!flag && this.pollinatingTicks > 600) {
                        EnhancedBee.this.savedFlowerPos = null;
                    } else {
                        if (flag) {
                            boolean flag2 = EnhancedBee.this.random.nextInt(25) == 0;
                            if (flag2) {
                                this.hoverPos = new Vec3(vec3.x() + (double)this.getOffset(), vec3.y(), vec3.z() + (double)this.getOffset());
                                EnhancedBee.this.navigation.stop();
                            } else {
                                flag1 = false;
                            }

                            EnhancedBee.this.getLookControl().setLookAt(vec3.x(), vec3.y(), vec3.z());
                        }

                        if (flag1) {
                            this.setWantedPos();
                        }

                        ++this.successfulPollinatingTicks;
                        if (EnhancedBee.this.random.nextFloat() < 0.05F && this.successfulPollinatingTicks > this.lastSoundPlayedTick + 60) {
                            this.lastSoundPlayedTick = this.successfulPollinatingTicks;
                            EnhancedBee.this.playSound(SoundEvents.BEE_POLLINATE, 1.0F, 1.0F);
                        }
                    }
                }
            }

        }

        private void setWantedPos() {
            EnhancedBee.this.getMoveControl().setWantedPosition(this.hoverPos.x(), this.hoverPos.y(), this.hoverPos.z(), (double)0.35F);
        }

        private float getOffset() {
            return (EnhancedBee.this.random.nextFloat() * 2.0F - 1.0F) * 0.33333334F;
        }

        private Optional<BlockPos> findNearbyFlower() {
            return this.findNearestBlock(this.VALID_POLLINATION_BLOCKS, (double)5.0F);
        }

        private Optional<BlockPos> findNearestBlock(Predicate<BlockState> p_28076_, double p_28077_) {
            BlockPos blockpos = EnhancedBee.this.blockPosition();
            BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

            for(int i = 0; (double)i <= p_28077_; i = i > 0 ? -i : 1 - i) {
                for(int j = 0; (double)j < p_28077_; ++j) {
                    for(int k = 0; k <= j; k = k > 0 ? -k : 1 - k) {
                        for(int l = k < j && k > -j ? j : 0; l <= j; l = l > 0 ? -l : 1 - l) {
                            blockpos$mutableblockpos.setWithOffset(blockpos, k, i - 1, l);
                            if (blockpos.closerThan(blockpos$mutableblockpos, p_28077_) && p_28076_.test(EnhancedBee.this.level.getBlockState(blockpos$mutableblockpos))) {
                                return Optional.of(blockpos$mutableblockpos);
                            }
                        }
                    }
                }
            }

            return Optional.empty();
        }
    }

    class BeeWanderGoal extends Goal {
        private static final int WANDER_THRESHOLD = 22;

        BeeWanderGoal() {
            this.setFlags(EnumSet.of(Flag.MOVE));
        }

        public boolean canUse() {
            return EnhancedBee.this.navigation.isDone() && EnhancedBee.this.random.nextInt(10) == 0;
        }

        public boolean canContinueToUse() {
            return EnhancedBee.this.navigation.isInProgress();
        }

        public void start() {
            Vec3 vec3 = this.findPos();
            if (vec3 != null) {
                EnhancedBee.this.navigation.moveTo(EnhancedBee.this.navigation.createPath(new BlockPos(vec3), 1), (double)1.0F);
            }

        }

        @Nullable
        private Vec3 findPos() {
            Vec3 vec3;
            if (EnhancedBee.this.isNestValid() && !EnhancedBee.this.closerThan(EnhancedBee.this.nestPos, 22)) {
                Vec3 vec31 = Vec3.atCenterOf(EnhancedBee.this.nestPos);
                vec3 = vec31.subtract(EnhancedBee.this.position()).normalize();
            } else {
                vec3 = EnhancedBee.this.getViewVector(0.0F);
            }

            int i = 8;
            Vec3 vec32 = HoverRandomPos.getPos(EnhancedBee.this, 8, 7, vec3.x, vec3.z, ((float)Math.PI / 2F), 3, 1);
            return vec32 != null ? vec32 : AirAndWaterRandomPos.getPos(EnhancedBee.this, 8, 4, -2, vec3.x, vec3.z, (double)((float)Math.PI / 2F));
        }
    }

    /**
     *  Nest
     */

    private static boolean isNestableBlock(BlockState state, TagKey<Block> tagKey) {
        return state.is(tagKey);
    }


    private static final ImmutableList<Block> BURROW_NEST = ImmutableList.of(Blocks.DIRT, Blocks.COARSE_DIRT, Blocks.GRASS_BLOCK, ModBlocks.SPARSEGRASS_BLOCK.get(), Blocks.MYCELIUM, ModBlocks.PATCHYMYCELIUM_BLOCK.get());
    private static final ImmutableList<Block> TOUGH_NEST = ImmutableList.of(Blocks.COARSE_DIRT, Blocks.GRAVEL);
    private static final ImmutableList<Block> MUD_NEST = ImmutableList.of(Blocks.CLAY);
    private static final ImmutableList<Block> SOFT_NEST = ImmutableList.of(Blocks.HAY_BLOCK, Blocks.BLACK_WOOL, Blocks.BLUE_WOOL, Blocks.BROWN_WOOL, Blocks.CYAN_WOOL, Blocks.GRAY_WOOL, Blocks.GREEN_WOOL, Blocks.LIGHT_BLUE_WOOL, Blocks.LIGHT_GRAY_WOOL, Blocks.LIME_WOOL, Blocks.MAGENTA_WOOL, Blocks.ORANGE_WOOL, Blocks.PINK_WOOL, Blocks.PURPLE_WOOL, Blocks.RED_WOOL, Blocks.YELLOW_WOOL);
    private static final ImmutableList<Block> SAND_NEST = ImmutableList.of(Blocks.SAND, Blocks.RED_SAND, Blocks.BLACK_CONCRETE_POWDER, Blocks.BLUE_CONCRETE_POWDER, Blocks.BROWN_CONCRETE_POWDER, Blocks.CYAN_CONCRETE_POWDER, Blocks.GRAY_CONCRETE_POWDER, Blocks.GREEN_CONCRETE_POWDER, Blocks.LIGHT_BLUE_CONCRETE_POWDER, Blocks.LIGHT_GRAY_CONCRETE_POWDER, Blocks.LIME_CONCRETE_POWDER, Blocks.MAGENTA_CONCRETE_POWDER, Blocks.ORANGE_CONCRETE_POWDER, Blocks.PINK_CONCRETE_POWDER, Blocks.PURPLE_CONCRETE_POWDER, Blocks.RED_CONCRETE_POWDER, Blocks.YELLOW_CONCRETE_POWDER);
    private static final ImmutableList<Block> CARVED_NEST = ImmutableList.of(Blocks.ACACIA_LOG, Blocks.STRIPPED_ACACIA_LOG, Blocks.ACACIA_PLANKS, Blocks.ACACIA_WOOD, Blocks.BIRCH_LOG, Blocks.STRIPPED_BIRCH_LOG, Blocks.BIRCH_PLANKS, Blocks.BIRCH_WOOD, Blocks.DARK_OAK_LOG, Blocks.STRIPPED_DARK_OAK_LOG, Blocks.DARK_OAK_PLANKS, Blocks.DARK_OAK_WOOD, Blocks.JUNGLE_LOG, Blocks.STRIPPED_JUNGLE_LOG, Blocks.JUNGLE_PLANKS, Blocks.JUNGLE_WOOD, Blocks.OAK_LOG, Blocks.STRIPPED_OAK_LOG, Blocks.OAK_PLANKS, Blocks.OAK_WOOD, Blocks.SPRUCE_LOG, Blocks.STRIPPED_OAK_LOG, Blocks.OAK_PLANKS, Blocks.OAK_WOOD);

    private static boolean checkWalls(Level level, BlockPos pos, INestEggCapability nestCapability, HashMap<BlockPos, Integer> nestSites, TagKey<Block> nestMaterial) {
        if (level.getBlockState(pos).isAir()) {
            int matchingWalls = 0;

            if (nestable(level, nestCapability, pos.north(), nestSites, nestMaterial)) matchingWalls++;
            if (nestable(level, nestCapability, pos.south(), nestSites, nestMaterial)) matchingWalls++;
            if (nestable(level, nestCapability, pos.east(), nestSites, nestMaterial)) matchingWalls++;
            if (nestable(level, nestCapability, pos.west(), nestSites, nestMaterial)) matchingWalls++;

            return matchingWalls > 0;
        }

        return false;
    }

    private static boolean checkUnderside(Level level, BlockPos pos, INestEggCapability nestCapability, HashMap<BlockPos, Integer> nestSites, TagKey<Block> nestMaterial) {
        if (level.getBlockState(pos).isAir()) {

            return (nestable(level, nestCapability, pos.above(), nestSites, nestMaterial));
        }

        return false;
    }

    private static boolean checkGround(Level level, BlockPos pos, INestEggCapability nestCapability, HashMap<BlockPos, Integer> nestSites, TagKey<Block> nestMaterial) {
        if (level.getBlockState(pos).isAir()) {
            nestable(level, nestCapability, pos.below(), nestSites, nestMaterial);
            return true;
        }

        return false;
    }

    private static boolean checkSouthEast(Level level, BlockPos pos, INestEggCapability nestCapability, HashMap<BlockPos, Integer> nestSites, TagKey<Block> nestMaterial) {
        if (level.getBlockState(pos).isAir()) {
            int matchingWalls = 0;

            /**
             *  the block north and west of the air block are checked because the bee wants the hive to face south or east
             */

            if (nestable(level, nestCapability, pos.north(), nestSites, nestMaterial)) matchingWalls++;
            if (nestable(level, nestCapability, pos.west(), nestSites, nestMaterial)) matchingWalls++;

            return matchingWalls > 0;
        }

        return false;
    }

    private static boolean nestable(Level level, INestEggCapability nestCapability, BlockPos pos, HashMap<BlockPos, Integer> nestSites, TagKey<Block> nestMaterial) {
        if (nestCapability.getAllNestEggPos().containsKey(pos)) return false;

        BlockState state = level.getBlockState(pos);

        if (state.isAir()) {
            nestSites.put(pos, 0);
            return false;
        }

        if (isNestableBlock(state, nestMaterial)) {
            nestSites.put(pos, 2);
            System.out.println(nestSites.size() + " nestable locations detected");
            return true;
        } else {
            nestSites.put(pos, 1);
        }

        return false;
    }
}