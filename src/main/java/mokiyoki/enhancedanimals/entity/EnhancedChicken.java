package mokiyoki.enhancedanimals.entity;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Dynamic;
import mokiyoki.enhancedanimals.ai.brain.chicken.ChickenBrain;
import mokiyoki.enhancedanimals.ai.general.*;
import mokiyoki.enhancedanimals.capability.egg.EggCapabilityProvider;
import mokiyoki.enhancedanimals.config.EanimodCommonConfig;
import mokiyoki.enhancedanimals.entity.genetics.ChickenGeneticsInitialiser;
import mokiyoki.enhancedanimals.init.*;
import mokiyoki.enhancedanimals.items.EnhancedEgg;
import mokiyoki.enhancedanimals.model.modeldata.AnimalModelData;
import mokiyoki.enhancedanimals.model.modeldata.ChickenModelData;
import mokiyoki.enhancedanimals.tileentity.ChickenNestTileEntity;
import mokiyoki.enhancedanimals.util.Genes;
import mokiyoki.enhancedanimals.util.Reference;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

import static mokiyoki.enhancedanimals.init.FoodSerialiser.chickenFoodMap;
import static mokiyoki.enhancedanimals.renderer.textures.ChickenTexture.calculateChickenTextures;
import static mokiyoki.enhancedanimals.init.ModEntities.ENHANCED_CHICKEN;
import static mokiyoki.enhancedanimals.util.scheduling.Schedules.*;



import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by saemon and moki on 30/08/2018.
 */
public class EnhancedChicken extends EnhancedAnimalAbstract {

    //avalible UUID spaces : [ S 1 2 3 4 5 6 7 - 8 9 10 11 - 12 13 14 15 - 16 17 18 19 - 20 21 22 23 24 25 26 27 28 29 30 31 ]

    private static final EntityDataAccessor<Boolean> ROOSTING = SynchedEntityData.<Boolean>defineId(EnhancedChicken.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> BROODING = SynchedEntityData.<Boolean>defineId(EnhancedChicken.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> BROODY = SynchedEntityData.<Boolean>defineId(EnhancedChicken.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<BlockPos> NEST_POS = SynchedEntityData.defineId(EnhancedChicken.class, EntityDataSerializers.BLOCK_POS);


    //Brain Modules

    protected static final ImmutableList<? extends SensorType<? extends Sensor<? super EnhancedChicken>>> SENSOR_TYPES = ImmutableList.of(SensorType.NEAREST_LIVING_ENTITIES, SensorType.NEAREST_ADULT, SensorType.HURT_BY, ModSensorTypes.CHICKEN_HOSTILES_SENSOR.get(), ModSensorTypes.CHICKEN_FOOD_TEMPTATIONS.get());
    protected static final ImmutableList<? extends MemoryModuleType<?>> MEMORY_TYPES = ImmutableList.of(MemoryModuleType.BREED_TARGET, ModMemoryModuleTypes.SLEEPING.get(), ModMemoryModuleTypes.BROODING.get(), ModMemoryModuleTypes.ROOSTING.get(), ModMemoryModuleTypes.PAUSE_BRAIN.get(), ModMemoryModuleTypes.FOCUS_BRAIN.get(), ModMemoryModuleTypes.PAUSE_BETWEEN_EATING.get(), ModMemoryModuleTypes.HUNGRY.get(), ModMemoryModuleTypes.SEEKING_SHELTER.get(), MemoryModuleType.NEAREST_LIVING_ENTITIES, MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES, MemoryModuleType.NEAREST_VISIBLE_PLAYER, MemoryModuleType.NEAREST_VISIBLE_ATTACKABLE_PLAYER, MemoryModuleType.LOOK_TARGET, MemoryModuleType.WALK_TARGET, MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE, MemoryModuleType.PATH, MemoryModuleType.ATTACK_TARGET, MemoryModuleType.ATTACK_COOLING_DOWN, MemoryModuleType.NEAREST_VISIBLE_ADULT, MemoryModuleType.HURT_BY, MemoryModuleType.HURT_BY_ENTITY, MemoryModuleType.NEAREST_HOSTILE, MemoryModuleType.NEAREST_ATTACKABLE, MemoryModuleType.TEMPTING_PLAYER, MemoryModuleType.TEMPTATION_COOLDOWN_TICKS, MemoryModuleType.IS_TEMPTED);

    //--------------

    public float wingRotation;
    public float destPos;
    public float oFlapSpeed;
    public float oFlap;
    private float wingRotDelta = 1.0F;
    public int timeUntilNextEgg;
    public float currentNestScore;

    public EnhancedFollowParentGoal followParentGoal;
    public boolean chickenJockey;

    public int crowTick = 0;
    public boolean preening = false;

    @OnlyIn(Dist.CLIENT)
    private ChickenModelData chickenModelData;

    public EnhancedChicken(EntityType<? extends EnhancedChicken> entityType, Level worldIn) {
        super(entityType, worldIn, Reference.CHICKEN_SEXLINKED_GENES_LENGTH, Reference.CHICKEN_AUTOSOMAL_GENES_LENGTH, false);
//        this.setSize(0.4F, 0.7F); //I think its the height and width of a chicken
        this.timeUntilNextEgg = (int) (this.random.nextInt(this.random.nextInt(6000) + 6000)/EanimodCommonConfig.COMMON.eggMultiplier.get()); //TODO make some genes to alter these numbers
        this.setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
        this.createNewHungerLimit();
    }
    protected void customServerAiStep() {
        this.getBrain().tick((ServerLevel)this.level, this);
//        ChickenBrain.updateActivity(this);
        if (!this.isNoAi()) {
            if (this.isAnimalSleeping()) {
                this.getBrain().setMemory(ModMemoryModuleTypes.SLEEPING.get(), true);
            }
            if (this.isBrooding()) {
                this.getBrain().setMemory(ModMemoryModuleTypes.BROODING.get(), true);
            } else {
                this.getBrain().eraseMemory(ModMemoryModuleTypes.BROODING.get());
            }
            if (this.isAnimalSleeping()) {
                this.getBrain().setMemory(ModMemoryModuleTypes.PAUSE_BRAIN.get(), true);
            }
            if (this.isInWaterOrRain() && !this.isInWater()) {
                this.getBrain().setMemory(ModMemoryModuleTypes.SEEKING_SHELTER.get(), true);
                this.scheduledToRun.put(CHECK_RAIN_STOPPED_SCHEDULE.funcName, CHECK_RAIN_STOPPED_SCHEDULE.function.apply(300));
            }
            if (this.getHunger() > hungerLimit) {
                this.getBrain().setMemory(ModMemoryModuleTypes.HUNGRY.get(), true);
            }
            if (this.level.isDay() && this.isRoosting()) {
                this.getBrain().eraseMemory(ModMemoryModuleTypes.ROOSTING.get());
                this.setRoosting(false);
            }

        }

        super.customServerAiStep();
    }

    protected Brain.Provider<EnhancedChicken> brainProvider() {
        return Brain.provider(MEMORY_TYPES, SENSOR_TYPES);
    }

    protected Brain<?> makeBrain(Dynamic<?> p_149138_) {
        return ChickenBrain.makeBrain(this.brainProvider().makeBrain(p_149138_));
    }

    public Brain<EnhancedChicken> getBrain() {
        return (Brain<EnhancedChicken>)super.getBrain();
    }
    

    @Override
    protected FoodSerialiser.AnimalFoodMap getAnimalFoodType() {
        return chickenFoodMap();
    }

    @Override
    public EntityDimensions getDimensions(Pose poseIn) {
        return EntityDimensions.scalable(0.4F, 0.7F).scale(this.getScale());
    }

    @Override
    public float getScale() {
        float size = this.getAnimalSize() > 0.0F ? this.getAnimalSize() : 1.0F;
        float nbSize = 0.2F;
        return this.isGrowing() ? (nbSize + ((size-nbSize) * (this.growthAmount()))) : size;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(ROOSTING, Boolean.FALSE);
        this.entityData.define(BROODING, Boolean.FALSE);
        this.entityData.define(BROODY, Boolean.FALSE);
        this.entityData.define(NEST_POS, BlockPos.ZERO);
    }

    public void scheduleDespawn(int ticksToWait) {
        this.scheduledToRun.put(DESPAWN_NO_PASSENGER_SCHEDULE.funcName, DESPAWN_NO_PASSENGER_SCHEDULE.function.apply(ticksToWait));
    }

    public void scheduleLookForNest(int ticksToWait) {
        this.scheduledToRun.put(LOOK_FOR_NEST_SCHEDULE.funcName, LOOK_FOR_NEST_SCHEDULE.function.apply(ticksToWait));
    }

    @Override
    public void despawn() {
        if (this.getPassengers().isEmpty()) {
            if (this.isChickenJockey()) {
                if (!(this.hasCustomName() || this.isLeashed() || this.isTame())) {
                    this.discard();
                }
            } else {
                super.despawn();
            }
        }
    }

    @Override
    protected String getSpecies() {
        return "entity.eanimod.enhanced_chicken";
    }

    @Override
    protected int getAdultAge() { return EanimodCommonConfig.COMMON.adultAgeChicken.get();}

    @Override
    protected int gestationConfig() {
        return EanimodCommonConfig.COMMON.incubationDaysChicken.get();
    }

    protected int eggLayingTime() { return (int)(6000/EanimodCommonConfig.COMMON.eggMultiplier.get());}

    @Override
    public InteractionResult mobInteract(Player entityPlayer, InteractionHand hand) {
        ItemStack itemStack = entityPlayer.getItemInHand(hand);
        Item item = itemStack.getItem();

        if (item == ModItems.ENHANCED_CHICKEN_EGG.get()) {
            return InteractionResult.SUCCESS;
        }

        if (this.isChickenJockey() && this.getPassengers().isEmpty()) {
            if (hunger >= 6000 && (isFoodItem(itemStack) || item instanceof EnhancedEgg)) {
                this.setChickenJockey(false);
                this.setTame(true);
            }
        }

        if (item instanceof EnhancedEgg && hunger >= 6000) {
            //enhancedegg egg eating
            decreaseHunger(200);
            if (!entityPlayer.getAbilities().instabuild) {
                itemStack.shrink(1);
            } else {
                if (itemStack.getCount() > 1) {
                    itemStack.shrink(1);
                }
            }
        }

        return super.mobInteract(entityPlayer, hand);
    }

    public void setSharedGenesFromEntityEgg(String genes) {
        this.entityData.set(SHARED_GENES, genes);
    }

    public boolean isRoosting() {
        return this.entityData.get(ROOSTING);
    }

    public void setRoosting(boolean isRoosting) {
        this.entityData.set(ROOSTING, isRoosting);
    }

    public boolean isBrooding() {
        return this.entityData.get(BROODING);
    }

    public void setBrooding(boolean isBrooding) {
        this.entityData.set(BROODING, isBrooding);
    }

    public boolean isBroody() {
        return this.entityData.get(BROODY);
    }

    public void setBroody(boolean isBroody) {
        this.entityData.set(BROODY, isBroody);
    }

    public void setNest(BlockPos position) {
        this.entityData.set(NEST_POS, position);
    }

    public void setNest(int x, int y, int z) {
        setNest(new BlockPos(x, y, z));
    }

    public BlockPos getNest() {
        return this.entityData.get(NEST_POS);
    }

    protected float getStandingEyeHeight(Pose poseIn, EntityDimensions sizeIn) {
        return this.isBaby() ? sizeIn.height * 0.85F : sizeIn.height * 0.92F;
    }

    public static AttributeSupplier.Builder prepareAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 3.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.25D);
    }

    public void positionRider(Entity passenger) {
        super.positionRider(passenger);
        float f = Mth.sin(this.yBodyRot * ((float)Math.PI / 180F));
        float f1 = Mth.cos(this.yBodyRot * ((float)Math.PI / 180F));
        float f2 = 0.1F;
        float f3 = 0.0F;
        passenger.setPos(this.getX() + (double)(0.1F * f), this.getY(0.5D) + passenger.getMyRidingOffset() + 0.0D, this.getZ() - (double)(0.1F * f1));
        if (passenger instanceof LivingEntity) {
            ((LivingEntity)passenger).yBodyRot = this.yBodyRot;
        }

    }

    /**
     * Determines if this chicken is a jokey with a zombie riding it.
     */
    public boolean isChickenJockey() {
        return this.chickenJockey;
    }

    /**
     * Sets whether this chicken is a jockey or not.
     */
    public void setChickenJockey(boolean jockey) {
        this.chickenJockey = jockey;
    }

    public void handleEntityEvent(byte p_29814_) {
        if (p_29814_ == 11) { //crow event broadcasted
            this.crowTick = 120;
        } else if (p_29814_ == 12) { //preening start event broadcasted
            this.preening = true;
        } else if (p_29814_ == 13) { //preening stop event broadcasted
            this.preening = false;
        } else {
            super.handleEntityEvent(p_29814_);
        }
    }

    @Override
    public void aiStep() {
        super.aiStep();
        this.oFlap = this.wingRotation;
        this.oFlapSpeed = this.destPos;
        this.destPos = (float)((double)this.destPos + (double)(this.onGround ? -1 : 4) * 0.3D);
        this.destPos = Mth.clamp(this.destPos, 0.0F, 1.0F);

        if (!this.onGround && this.wingRotDelta < 1.0F) {
            this.wingRotDelta = 1.0F;
        }

        this.wingRotDelta = (float)((double)this.wingRotDelta * 0.9D);
        Vec3 vec3d = this.getDeltaMovement();
        if (!this.onGround && vec3d.y < 0.0D) {
            this.setDeltaMovement(vec3d.multiply(1.0D, this.genetics.isHomozygousFor(106,   2) ? 1.0D : 0.6D, 1.0D));
        }

        this.wingRotation += this.wingRotDelta * 2.0F;
       //TODO if "is child" and parent is sitting go under parent, possibly turn off ability to collide.

        //TODO if "is child" and parent is 1 block over or less and doesn't have a passenger ride on parent's back

        if (!this.getOrSetIsFemale()) {
            if (this.crowTick > 0) {
                this.crowTick = Math.max(0, this.crowTick - 1);
                if (!this.level.isClientSide) {
                    if (this.crowTick == 60) { //TODO this is the start of the crow, change to whatever is needed to match the animation
                        this.level.playSound(null, this, ModSounds.ROOSTER_CROW.get(), this.getSoundSource(), 1.0F, 1.0F);
                    }
                    if (this.crowTick <= 5) {
                        this.currentAIStatus = AIStatus.NONE;
                    }
                }
            } else {
                if (!this.level.isClientSide && !this.scheduledToRun.containsKey("CrowSchedule")) {
                    //TODO the lower and upper bounds of the random int, can be used to create a wait period of when to crow
                    //we can add extra code here that has a different value if say we have detected another rooster crow or maybe early mornings ect
                    this.scheduledToRun.put(CROW_SCHEDULE.funcName, CROW_SCHEDULE.function.apply(this.random.nextInt(100, 6000)));
                }
            }
        }

        if (!this.level.isClientSide && !this.sleeping && !this.scheduledToRun.containsKey("StopPreenSchedule")) {
            int startPreenTime = this.random.nextInt(1000, 6000);
            int preenDuration = this.random.nextInt(100, 500);
            this.scheduledToRun.put(START_PREEN_SCHEDULE.funcName, START_PREEN_SCHEDULE.function.apply(startPreenTime));
            this.scheduledToRun.put(STOP_PREEN_SCHEDULE.funcName, STOP_PREEN_SCHEDULE.function.apply(startPreenTime+preenDuration));
        }
    }

    protected void incrementHunger() {
        if (this.sleeping) {
            hunger = hunger + (0.25F*getHungerModifier());
        } else {
            hunger = hunger + (0.5F*getHungerModifier());
        }
    }

//    @Override
//    public boolean sleepingConditional() {
//        return (((this.level.getDayTime()%24000 >= 11000 && this.level.getDayTime()%24000 <= 22000) || this.level.isThundering()) && this.awokenTimer == 0 && !this.sleeping);
//    }

    @Override
    protected void runExtraIdleTimeTick() {
        if (EanimodCommonConfig.COMMON.omnigenders.get() || this.getOrSetIsFemale()) {
            if (this.gestationTimer > 0) {
                --this.gestationTimer;
            }

            if (hunger <= 24000 && !isAnimalSleeping() && !this.isBroody()) {
                --this.timeUntilNextEgg;
            } else if (hunger >= 48000) {
                this.timeUntilNextEgg = eggLayingTime();
            }
        }
    }

    @Override
    protected void runPregnancyTick() {
        if (!this.isBaby() && this.timeUntilNextEgg <= 0 && !this.isAnimalSleeping()) {
            this.playSound(SoundEvents.CHICKEN_EGG, 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
            ItemStack eggItem = new ItemStack(getEggColour(resolveEggColour()), 1, null);
            ((EnhancedEgg) eggItem.getItem()).setHasParents(eggItem, true);
            if (this.gestationTimer > 0) {
                String damName = "???";
                if (this.getCustomName()!=null) {
                    damName = this.getCustomName().getString();
                }
                eggItem.getCapability(EggCapabilityProvider.EGG_CAP, null).orElse(new EggCapabilityProvider()).setEggData(new Genes(this.mateGenetics).makeChild(!this.mateGender, this.genetics, !this.getOrSetIsFemale(), Genes.Species.CHICKEN), this.mateName, damName);
                CompoundTag nbtTagCompound = eggItem.serializeNBT();
                eggItem.deserializeNBT(nbtTagCompound);
                if (this.level.getGameRules().getBoolean(GameRules.RULE_DOMOBLOOT)) {
                    int i = 1;
                    while (i > 0) {
                        int j = ExperienceOrb.getExperienceValue(i);
                        i -= j;
                        this.level.addFreshEntity(new ExperienceOrb(this.level, this.getX(), this.getY(), this.getZ(), j));
                    }
                }
            }
            if (this.level.getBlockEntity(this.blockPosition()) instanceof ChickenNestTileEntity nestEntity ) {
                if (this.blockPosition()!=this.getNest()) {
                    this.currentNestScore = this.rateNest(this.blockPosition());
                    this.setNest(this.blockPosition());
                } else if (this.currentNestScore<0.0F) {
                    this.currentNestScore = -(this.currentNestScore+0.1F);
                }
                nestEntity.addEggToNest(eggItem);
                if (nestEntity.isFull() || (nestEntity.getEggCount()>=3 && ThreadLocalRandom.current().nextInt(5)==0)) {
                    this.setBroody(true);
                }
            } else {
                this.spawnAtLocation(eggItem, 1);
                if (this.getNest()!=BlockPos.ZERO && this.random.nextInt(3)==0) {
                    this.setNest(0, 0, 0);
                }
            }
            if (this.isBrooding() && !this.isBroody()) {
                this.setBrooding(false);
            }
            float eggTimeVariance = 0.1F; //TODO egg time variation genetics?
            this.timeUntilNextEgg = (int) (eggLayingTime()*(1.0F-eggTimeVariance) + this.random.nextInt((int) (eggLayingTime()*(eggTimeVariance*2))));
        }

        if (this.isBroody()) {
            if (this.level.getBlockEntity(this.blockPosition()) instanceof ChickenNestTileEntity nestEntity) {
                if (nestEntity.incubate()) {
                    nestEntity.hatchEggs(this.level, this.getNest(), this.getRandom());
                    this.setBroody(false);
                    this.setBrooding(false);
                }
            }
        }
    }

    @Override
    public boolean causeFallDamage(float distance, float damageMultiplier, DamageSource damageSource) {
        if (this.genetics.isHomozygousFor(106, 2)) {
            return super.causeFallDamage(distance, damageMultiplier, damageSource);
        }
            return false;
    }

    @Override
    protected void lethalGenes() {
        int[] genes = this.genetics.getAutosomalGenes();
        if(genes[70] == 2 && genes[71] == 2) {
                this.remove(RemovalReason.DISCARDED);
        } else if(genes[72] == 2 && genes[73] == 2){
                this.remove(RemovalReason.DISCARDED);
        } else if(genes[104] == 2 && genes[105] == 2){
                this.remove(RemovalReason.DISCARDED);
        } else if (genes[150] == 2 && genes[151] == 2){
                this.remove(RemovalReason.DISCARDED);
        }
    }

    @Override
    public void playAmbientSound() {
//        if (!this.getOrSetIsFemale() && this.growthAmount()==1.0F && this.soundOffset <= 0) {
//            this.playSound(ModSounds.ROOSTER_CROW.get(), 3.0F, 1.5F - (((this.getAnimalSize() - 0.5076F) / 0.4924F) * 0.85F));
//            this.soundOffset = this.random.nextInt(10);
//        } else {
//            if (this.soundOffset>0 && !this.sleeping) this.soundOffset--;
            super.playAmbientSound();
//        }
    }

    @Override
    public float getVoicePitch() {
        float pitch = 1.0F + (1.0F-this.getAnimalSize());
        if (this.isGrowing()) pitch*=(1.0F+((1.0F-this.growthAmount())*0.5F));
        return this.getOrSetIsFemale()?pitch*0.95F:pitch*1.05F;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        if (isAnimalSleeping()) {
            return null;
        }
        return SoundEvents.CHICKEN_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.CHICKEN_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.CHICKEN_DEATH;
    }

    protected void playStepSound(BlockPos pos, BlockState blockIn) {
        if (!this.isSilent() && this.getBells()) {
            this.playSound(SoundEvents.NOTE_BLOCK_CHIME, 1.5F, 2.0F);
        }
    }

    @OnlyIn(Dist.CLIENT)
    public void crow() {
        this.playSound(ModSounds.ROOSTER_CROW.get(), 3.0F, 1.5F - (((this.getAnimalSize() - 0.5076F) / 0.4924F) * 0.85F));
    }

    /**
     * Chicken grass eating and sand bathing
    */
    //TODO make the sand bathing actions

    //also provides sand bath bonus
    public void ate() {
//        if (this.isChild())
//        {
//            this.addGrowth(60);
//        }
    }

    @Override
    protected void handlePartnerBreeding(AgeableMob ageable) {
        if (EanimodCommonConfig.COMMON.omnigenders.get()) {
            this.mateGenetics = ((EnhancedChicken)ageable).getGenes();
            this.setFertile();
            this.setMateGender(((EnhancedChicken)ageable).getOrSetIsFemale());
            if (((EnhancedChicken)ageable).hasCustomName()) {
                this.setMateName(((EnhancedChicken) ageable).getCustomName().getString());
            }
            ((EnhancedChicken)ageable).setMateGenes(this.genetics);
            ((EnhancedChicken)ageable).setFertile();
            ((EnhancedChicken)ageable).setMateGender(this.getOrSetIsFemale());
            if (this.hasCustomName()) {
                ((EnhancedChicken)ageable).setMateName(this.getCustomName().getString());
            }
        } else if (this.getOrSetIsFemale()) {
            this.mateGenetics = ((EnhancedChicken)ageable).getGenes();
            this.setFertile();
            this.setMateGender(false);
            if (((EnhancedChicken)ageable).hasCustomName()) {
                this.setMateName(((EnhancedChicken) ageable).getCustomName().getString());
            }
        } else {
            ((EnhancedChicken)ageable).setMateGenes(this.genetics);
            ((EnhancedChicken)ageable).setFertile();
            ((EnhancedChicken)ageable).setMateGender(false);
            if (this.hasCustomName()) {
                ((EnhancedAnimalAbstract)ageable).setMateName(this.getCustomName().getString());
            }
        }
    }


    private int resolveEggColour(){
        int eggColour = 0;

        if (this.genetics!=null) {
            int[] sexlinkedGenes = this.genetics.getSexlinkedGenes();
            int[] genes = this.genetics.getAutosomalGenes();

        if(sexlinkedGenes[10] == 1 || (!this.getOrSetIsFemale() && sexlinkedGenes[11] == 1)) {

            if (genes[64] == 1 || genes[65] == 1 || genes[66] == 1 || genes[67] == 1) {
                //egg is brown
                eggColour = 13;
            } else if ((genes[64] == 2 || genes[65] == 2) && (genes[66] == 2 || genes[67] == 2)) {
                //egg is brown
                eggColour = 13;
            } else if (genes[66] == 2 || genes[67] == 2) {
                //egg is cream
                eggColour = 1;
            } else if (genes[64] == 2 || genes[65] == 2) {
                //egg is pink
                eggColour = 7;
            } else if (genes[64] == 3 || genes[65] == 3 || genes[66] == 3 || genes[67] == 3) {
                //egg is white
                eggColour = 0;
            }

            int shade = 0;
            int markings = 0;

            //darkens egg if already brown shade
            if (genes[68] == 1 || genes[69] == 1) {
                if (eggColour != 0) {
                    shade +=1;
                }
            }

            if (genes[172] == 2 || genes[173] == 2) {
                //darkens egg by 1
                shade +=1;
            }

            if ((eggColour != 3 && eggColour != 7 && eggColour != 11) && genes[174] == 2 || genes[175] == 2) {
                //darkens egg by 1
                shade +=1;
            }

            if (genes[176] == 3 || genes[177] == 3) {
                //darkens egg by 1
                shade += 1;
            } else if ((eggColour != 3 && eggColour != 7 && eggColour != 11) && genes[176] == 2 || genes[177] == 2) {
                //darkens egg by 1
                shade += 1;
            }

            if (genes[178] == 2 || genes[179] == 2) {
                //has speckles
                shade = -1;
                markings = 1;
            } else if (genes[178] == 3 || genes[179] == 3) {
                markings = 1;
            }

            if (genes[180] == 2 && genes[181] == 2) {
                if (markings == 1) {
                    markings = 2;
                } else {
                    shade += 1;
                }
            }

            if (genes[182] == 2 || genes[183] == 2) {
                if (markings == 1) {
                    markings = 3;
                } else if (markings == 2) {
                    shade += 1;
                }
            }

            if (shade > 6) {
                shade = 6;
            } else if (shade < 0) {
                shade = 0;
            }

            if (eggColour == 0 && shade != 0) {
                //gives egg a brown tint if its white
                eggColour = shade + 13 - 1;
            } else {
                eggColour = eggColour + shade;
            }

            if (markings == 1) {
                eggColour = eggColour + 76;
            } else if (markings == 2) {
                eggColour = eggColour + 152;
            } else if (markings == 3) {
                eggColour = eggColour + 228;
            }

        }

        //toggles blue egg version
        if(genes[62] == 4 || genes[63] == 4){
            //paleblue range
            eggColour = eggColour + 57;
        } else if (genes[62] == 3 || genes[63] == 3) {
            //powderblue range
            eggColour = eggColour + 38;
        } else if(genes[62] == 1 || genes[63] == 1){
            //blue range
            eggColour = eggColour + 19;
        }

        }

        return eggColour;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public ChickenModelData getModelData() {
        return this.chickenModelData;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void setModelData(AnimalModelData animalModelData) {
        this.chickenModelData = (ChickenModelData) animalModelData;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public String getTexture() {
        if (this.enhancedAnimalTextureGrouping == null) {
            this.setTexturePaths();
        } else if (this.reload && this.getEnhancedAnimalAge() >= (int)(this.getFullSizeAge()*0.25F)) {
            this.reload = false;
            this.reloadTextures();
        }

        return getCompiledTextures("enhanced_chicken");
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    protected void reloadTextures() {
        this.texturesIndexes.clear();
        this.enhancedAnimalTextures.clear();
        this.enhancedAnimalTextureGrouping = null;
        this.compiledTexture = null;
        this.colouration.setMelaninColour(-1);
        this.colouration.setPheomelaninColour(-1);
        this.setTexturePaths();
    }

    @OnlyIn(Dist.CLIENT)
    protected void setTexturePaths() {
        calculateChickenTextures(this);
    }

    @Override
    protected void setAlphaTexturePaths() {
    }

    @Override
    protected boolean shouldDropExperience() {
        if (this.getEnhancedAnimalAge() > 10000) {
            return true;
        } else {
            return false;
        }
    }

    protected void dropCustomDeathLoot(DamageSource source, int looting, boolean recentlyHitIn) {
        super.dropCustomDeathLoot(source, looting, recentlyHitIn);
        int[] genes = this.genetics.getAutosomalGenes();
        int age = this.getEnhancedAnimalAge();
        int bodyType = 0;
        int meatSize;
        int featherCount = random.nextInt(4+looting)-1;

        if (genes[146] == 2 && genes[147] == 2) {
            if (genes[148] != 2 || genes[149] != 2) {
                //big body
                bodyType = 1;
            }
        } else if (genes[148] == 2 && genes[149] == 2) {
            if (genes[146] != 2 && genes[147] != 2) {
                //small body
                bodyType = -1;
            }
        }

        if (age < 60000) {
            if (age > 40000) {
                bodyType = bodyType - 1;
                featherCount = featherCount - 1;
            } else if (age > 20000){
                bodyType = bodyType - (random.nextInt(2)+1);
                featherCount = featherCount - 2;
            } else {
                bodyType = bodyType - 2;
                featherCount = featherCount - 3;
            }
        }

        // size is [ 0.5076 - 1.0F]
        if (this.getAnimalSize() < 0.67) {
            meatSize = bodyType + 1;
        } else if (this.getAnimalSize() < 0.89) {
            meatSize = bodyType + 2;
        } else {
            meatSize = bodyType + 3;
        }

        if (meatSize > 0) {
            ItemStack meatStack = new ItemStack(Items.CHICKEN, 1 + looting);

            if (genes[4] == 1 && genes[20] != 3 && genes[21] != 3 && (genes[42] == 1 || genes[43] == 1)) {
                if (meatSize == 1) {
                    if (this.isOnFire()) {
                        meatStack = new ItemStack(ModItems.COOKEDCHICKEN_DARKSMALL.get(), 1 + looting);
                    } else {
                        meatStack = new ItemStack(ModItems.RAWCHICKEN_DARKSMALL.get(), 1 + looting);
                    }
                } else if (meatSize == 2) {
                    if (this.isOnFire()) {
                        meatStack = new ItemStack(ModItems.COOKEDCHICKEN_DARKBIG.get(), 1 + looting);
                    } else {
                        meatStack = new ItemStack(ModItems.RAWCHICKEN_DARKBIG.get(), 1 + looting);
                    }
                } else {
                    if (this.isOnFire()) {
                        meatStack = new ItemStack(ModItems.COOKEDCHICKEN_DARK.get(), 1 + looting);
                    } else {
                        meatStack = new ItemStack(ModItems.RAWCHICKEN_DARK.get(), 1 + looting);
                    }
                }
            } else {
                if (meatSize == 1) {
                    if (this.isOnFire()) {
                        meatStack = new ItemStack(ModItems.COOKEDCHICKEN_PALESMALL.get(), 1 + looting);
                    } else {
                        meatStack = new ItemStack(ModItems.RAWCHICKEN_PALESMALL.get(), 1 + looting);
                    }
                } else if (meatSize == 2) {
                    if (this.isOnFire()) {
                        meatStack = new ItemStack(ModItems.COOKEDCHICKEN_PALE.get(), 1 + looting);
                    } else {
                        meatStack = new ItemStack(ModItems.RAWCHICKEN_PALE.get(), 1 + looting);
                    }
                } else {
                    if (this.isOnFire()) {
                        meatStack = new ItemStack(Items.COOKED_CHICKEN, 1 + looting);
                    }
                }
            }

            this.spawnAtLocation(meatStack);
        }

        if (featherCount > 0) {
            ItemStack featherStack = new ItemStack(Items.FEATHER, featherCount);
            this.spawnAtLocation(featherStack);
        }
    }

    @Override
    protected void readLegacyGenes(ListTag geneList, Genes genetics) {
        if (geneList.getCompound(0).contains("Sgene")) {
            super.readLegacyGenes(geneList, genetics);
        } else {
            for (int i = 0; i < 10; ++i) {
                int gene = geneList.getCompound(i).getInt("Gene");
                genetics.setSexlinkedGene(i * 2, gene);
                genetics.setSexlinkedGene((i * 2) + 1, gene);
            }

            for (int i = 0; i < geneList.size(); ++i) {
                if (i < 20) {
                    genetics.setAutosomalGene(i, 1);
                } else {
                    genetics.setAutosomalGene(i, geneList.getCompound(i).getInt("Gene"));
                }
            }
        }
    }

    @Override
    protected EnhancedAnimalAbstract createEnhancedChild(Level level, EnhancedAnimalAbstract otherParent) {
        EnhancedChicken enhancedchicken = ENHANCED_CHICKEN.get().create(this.level);
        if (enhancedchicken != null) {
            Genes genes = new Genes(this.genetics).makeChild(!this.getOrSetIsFemale(), otherParent.getSharedGenes(), !otherParent.getOrSetIsFemale(), Genes.Species.CHICKEN);
            enhancedchicken.setGenes(genes);
            enhancedchicken.setSharedGenes(genes);
            enhancedchicken.setSireName(otherParent.getCustomName()==null ? "???" : otherParent.getCustomName().getString());
            enhancedchicken.setDamName(this.getCustomName()==null ? "???" : this.getCustomName().getString());
            enhancedchicken.setParent(this.getUUID().toString());
            enhancedchicken.setGrowingAge();
            enhancedchicken.setBirthTime();
            enhancedchicken.initilizeAnimalSize();
            enhancedchicken.setEntityStatus(EntityState.CHILD_STAGE_ONE.toString());
            enhancedchicken.moveTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), 0.0F);
            }
        return enhancedchicken;
    }

    @Override
    protected void createAndSpawnEnhancedChild(Level level) {}

    @Override
    protected boolean canBePregnant() {
        return false;
    }

    @Override
    protected boolean canLactate() {
        return false;
    }

    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);

        compound.putBoolean("IsChickenJockey", this.isChickenJockey());
        compound.putFloat("NestQuality", this.currentNestScore);
        compound.putInt("NestPosX", this.getNest().getX());
        compound.putInt("NestPosY", this.getNest().getY());
        compound.putInt("NestPosZ", this.getNest().getZ());
        compound.putBoolean("Broody", this.isBroody());

    }

    /**
     * (abstract) Protected helper method to read subclass entity assets from NBT.
     */
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);

        setChickenJockey(compound.getBoolean("IsChickenJockey"));
        this.currentNestScore = compound.getFloat("NestQuality");
        this.setNest(compound.getInt("NestPosX"), compound.getInt("NestPosY"), compound.getInt("NestPosZ"));
        this.setBroody(compound.getBoolean("Broody"));
        if (this.isBroody()) this.setBrooding(true); else this.getBrain().eraseMemory(ModMemoryModuleTypes.BROODING.get());
        this.getBrain().eraseMemory(ModMemoryModuleTypes.PAUSE_BRAIN.get());
        this.getBrain().eraseMemory(ModMemoryModuleTypes.FOCUS_BRAIN.get());


    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor inWorld, DifficultyInstance difficulty, MobSpawnType spawnReason, @Nullable SpawnGroupData livingdata, @Nullable CompoundTag itemNbt) {
        livingdata = commonInitialSpawnSetup(inWorld, livingdata, getAdultAge(), 10000, 120000, spawnReason);
        if (this.mateGenetics != null && this.mateGenetics.getSexlinkedGene(0) != 0) {
            this.setFertile();
        }
        return livingdata;
    }

    @Override
    protected Genes createInitialGenes(LevelAccessor world, BlockPos pos, boolean isDomestic) {
        return new ChickenGeneticsInitialiser().generateNewGenetics(world, pos, isDomestic);
    }

    @Override
    public Genes createInitialBreedGenes(LevelAccessor world, BlockPos pos, String breed) {
        return new ChickenGeneticsInitialiser().generateWithBreed(world, pos, breed);
    }

    @Override
    protected void initializeHealth(EnhancedAnimalAbstract animal, float health) {
        int[] genes = animal.genetics.getAutosomalGenes();
        if (genes[54] != 3 && genes[55] != 3 && genes[184] == 2 && genes[185] == 2) {
            health = 0.5F;
        } else {
            health = (2.0F * animal.getAnimalSize()) + 2.0F;
        }

        super.initializeHealth(animal, health);
    }

    @Override
    public void initilizeAnimalSize(){
        float size = 1.0F;

        int[] genes = this.genetics.getAutosomalGenes();
        if(genes[74] == 1){
            size = size - 0.05F;
        }else if(genes[74] == 2){
            size = size - 0.025F;
        }
        if(genes[75] == 1){
            size = size - 0.05F;
        }else if(genes[75] == 2){
            size = size - 0.025F;
        }

        if(genes[76] == 1 || genes[77] == 1){
            size = size - 0.05F;
        }else if(genes[76] == 3 && genes[77] == 3){
            size = size - 0.1F;
        }

        if(genes[78] == 1 || genes[79] == 1){
            size = size * 0.94F;
        }


        genes = this.genetics.getSexlinkedGenes();
        if (this.getOrSetIsFemale()) {
            if(genes[14] == 2){
                size = size * 0.9F;
            }

            if(genes[16] == 2){
                size = size * 0.75F;
            }
        } else {
            if (genes[14] == 2 || genes[15] == 2) {
                if (genes[14] == 2 && genes[15] == 2) {
                    size = size * 0.9F;
                } else {
                    size = size * 0.99F;
                }
            }

            if (genes[16] == 2 || genes[17] == 2) {
                if (genes[16] == 2 && genes[17] == 2) {
                    size = size * 0.75F;
                } else {
                    size = size * 0.99F;
                }
            }
        }


        // size is [ 0.5076 - 1.0F]
        this.setAnimalSize(size);
    }

    private Item getEggColour(int eggColourGene){

        return switch (eggColourGene) {
            case 0 -> ModItems.EGG_WHITE.get();
            case 1 -> ModItems.EGG_CREAMLIGHT.get();
            case 2 -> ModItems.EGG_CREAM.get();
            case 3 -> ModItems.EGG_CREAMDARK.get();
            case 4 -> ModItems.EGG_CREAMDARKEST.get();
            case 5 -> ModItems.EGG_CARMELDARK.get();
            case 6 -> ModItems.EGG_GARNET.get();
            case 7 -> ModItems.EGG_PINKLIGHT.get();
            case 8 -> ModItems.EGG_PINK.get();
            case 9 -> ModItems.EGG_PINKDARK.get();
            case 10 -> ModItems.EGG_PINKDARKEST.get();
            case 11 -> ModItems.EGG_CHERRYDARK.get();
            case 12 -> ModItems.EGG_PLUM.get();
            case 13 -> ModItems.EGG_BROWNLIGHT.get();
            case 14 -> ModItems.EGG_BROWN.get();
            case 15 -> ModItems.EGG_BROWNDARK.get();
            case 16 -> ModItems.EGG_CHOCOLATE.get();
            case 17 -> ModItems.EGG_CHOCOLATEDARK.get();
            case 18 -> ModItems.EGG_CHOCOLATECOSMOS.get();
            case 19 -> ModItems.EGG_BLUE.get();
            case 20 -> ModItems.EGG_GREENLIGHT.get();
            case 21 -> ModItems.EGG_GREENYELLOW.get();
            case 22 -> ModItems.EGG_OLIVELIGHT.get();
            case 23 -> ModItems.EGG_OLIVE.get();
            case 24 -> ModItems.EGG_OLIVEDARK.get();
            case 25 -> ModItems.EGG_ARMY.get();
            case 26 -> ModItems.EGG_BLUEGREY.get();
            case 27 -> ModItems.EGG_GREY.get();
            case 28 -> ModItems.EGG_GREYGREEN.get();
            case 29 -> ModItems.EGG_AVOCADO.get();
            case 30 -> ModItems.EGG_AVOCADODARK.get();
            case 31 -> ModItems.EGG_FELDGRAU.get();
            case 32 -> ModItems.EGG_MINT.get();
            case 33 -> ModItems.EGG_GREEN.get();
            case 34 -> ModItems.EGG_GREENDARK.get();
            case 35 -> ModItems.EGG_PINE.get();
            case 36 -> ModItems.EGG_PINEDARK.get();
            case 37 -> ModItems.EGG_PINEBLACK.get();
            case 38 -> ModItems.EGG_POWDERBLUE.get();
            case 39 -> ModItems.EGG_TEA.get();
            case 40 -> ModItems.EGG_MATCHA.get();
            case 41 -> ModItems.EGG_MATCHADARK.get();
            case 42 -> ModItems.EGG_MOSS.get();
            case 43 -> ModItems.EGG_MOSSDARK.get();
            case 44 -> ModItems.EGG_GREENUMBER.get();
            case 45 -> ModItems.EGG_GREYBLUE.get();
            case 46 -> ModItems.EGG_GREYNEUTRAL.get();
            case 47 -> ModItems.EGG_LAUREL.get();
            case 48 -> ModItems.EGG_RESEDA.get();
            case 49 -> ModItems.EGG_GREENPEWTER.get();
            case 50 -> ModItems.EGG_GREYDARK.get();
            case 51 -> ModItems.EGG_CELADON.get();
            case 52 -> ModItems.EGG_FERN.get();
            case 53 -> ModItems.EGG_ASPARAGUS.get();
            case 54 -> ModItems.EGG_HUNTER.get();
            case 55 -> ModItems.EGG_HUNTERDARK.get();
            case 56 -> ModItems.EGG_TREEDARK.get();
            case 57 -> ModItems.EGG_PALEBLUE.get();
            case 58 -> ModItems.EGG_HONEYDEW.get();
            case 59 -> ModItems.EGG_EARTH.get();
            case 60 -> ModItems.EGG_KHAKI.get();
            case 61 -> ModItems.EGG_GRULLO.get();
            case 62 -> ModItems.EGG_KHAKIDARK.get();
            case 63 -> ModItems.EGG_CAROB.get();
            case 64 -> ModItems.EGG_COOLGREY.get();
            case 65 -> ModItems.EGG_PINKGREY.get();
            case 66 -> ModItems.EGG_WARMGREY.get();
            case 67 -> ModItems.EGG_ARTICHOKE.get();
            case 68 -> ModItems.EGG_MYRTLEGREY.get();
            case 69 -> ModItems.EGG_RIFLE.get();
            case 70 -> ModItems.EGG_JADE.get();
            case 71 -> ModItems.EGG_PISTACHIO.get();
            case 72 -> ModItems.EGG_SAGE.get();
            case 73 -> ModItems.EGG_ROSEMARY.get();
            case 74 -> ModItems.EGG_GREENBROWN.get();
            case 75 -> ModItems.EGG_UMBER.get();
            case 76 -> ModItems.EGG_WHITE.get();
            case 77 -> ModItems.EGG_CREAMLIGHT.get();
            case 78 -> ModItems.EGG_CREAM_SPECKLE.get();
            case 79 -> ModItems.EGG_CREAMDARK_SPECKLE.get();
            case 80 -> ModItems.EGG_CARMEL_SPECKLE.get();
            case 81 -> ModItems.EGG_CARMELDARK_SPECKLE.get();
            case 82 -> ModItems.EGG_GARNET_SPECKLE.get();
            case 83 -> ModItems.EGG_PINKLIGHT.get();
            case 84 -> ModItems.EGG_PINK_SPECKLE.get();
            case 85 -> ModItems.EGG_PINKDARK_SPECKLE.get();
            case 86 -> ModItems.EGG_CHERRY_SPECKLE.get();
            case 87 -> ModItems.EGG_CHERRYDARK_SPECKLE.get();
            case 88 -> ModItems.EGG_PLUM_SPECKLE.get();
            case 89 -> ModItems.EGG_BROWNLIGHT_SPECKLE.get();
            case 90 -> ModItems.EGG_BROWN_SPECKLE.get();
            case 91 -> ModItems.EGG_BROWNDARK_SPECKLE.get();
            case 92 -> ModItems.EGG_CHOCOLATE_SPECKLE.get();
            case 93 -> ModItems.EGG_CHOCOLATEDARK_SPECKLE.get();
            case 94 -> ModItems.EGG_CHOCOLATECOSMOS.get();
            case 95 -> ModItems.EGG_BLUE.get();
            case 96 -> ModItems.EGG_GREENLIGHT.get();
            case 97 -> ModItems.EGG_GREENYELLOW_SPECKLE.get();
            case 98 -> ModItems.EGG_OLIVELIGHT_SPECKLE.get();
            case 99 -> ModItems.EGG_OLIVE_SPECKLE.get();
            case 100 -> ModItems.EGG_OLIVEDARK_SPECKLE.get();
            case 101 -> ModItems.EGG_ARMY_SPECKLE.get();
            case 102 -> ModItems.EGG_BLUEGREY.get();
            case 103 -> ModItems.EGG_GREY_SPECKLE.get();
            case 104 -> ModItems.EGG_GREYGREEN_SPECKLE.get();
            case 105 -> ModItems.EGG_AVOCADO_SPECKLE.get();
            case 106 -> ModItems.EGG_AVOCADODARK_SPECKLE.get();
            case 107 -> ModItems.EGG_FELDGRAU_SPECKLE.get();
            case 108 -> ModItems.EGG_MINT_SPECKLE.get();
            case 109 -> ModItems.EGG_GREEN_SPECKLE.get();
            case 110 -> ModItems.EGG_GREENDARK_SPECKLE.get();
            case 111 -> ModItems.EGG_PINE_SPECKLE.get();
            case 112 -> ModItems.EGG_PINEDARK_SPECKLE.get();
            case 113 -> ModItems.EGG_PINEBLACK_SPECKLE.get();
            case 114 -> ModItems.EGG_POWDERBLUE.get();
            case 115 -> ModItems.EGG_TEA.get();
            case 116 -> ModItems.EGG_MATCHA_SPECKLE.get();
            case 117 -> ModItems.EGG_MATCHADARK_SPECKLE.get();
            case 118 -> ModItems.EGG_MOSS_SPECKLE.get();
            case 119 -> ModItems.EGG_MOSSDARK_SPECKLE.get();
            case 120 -> ModItems.EGG_GREENUMBER_SPECKLE.get();
            case 121 -> ModItems.EGG_GREYBLUE.get();
            case 122 -> ModItems.EGG_GREYNEUTRAL_SPECKLE.get();
            case 123 -> ModItems.EGG_LAUREL_SPECKLE.get();
            case 124 -> ModItems.EGG_RESEDA_SPECKLE.get();
            case 125 -> ModItems.EGG_GREENPEWTER_SPECKLE.get();
            case 126 -> ModItems.EGG_GREYDARK_SPECKLE.get();
            case 127 -> ModItems.EGG_CELADON_SPECKLE.get();
            case 128 -> ModItems.EGG_FERN_SPECKLE.get();
            case 129 -> ModItems.EGG_ASPARAGUS_SPECKLE.get();
            case 130 -> ModItems.EGG_HUNTER_SPECKLE.get();
            case 131 -> ModItems.EGG_HUNTERDARK_SPECKLE.get();
            case 132 -> ModItems.EGG_TREEDARK_SPECKLE.get();
            case 133 -> ModItems.EGG_PALEBLUE.get();
            case 134 -> ModItems.EGG_HONEYDEW.get();
            case 135 -> ModItems.EGG_EARTH_SPECKLE.get();
            case 136 -> ModItems.EGG_KHAKI_SPECKLE.get();
            case 137 -> ModItems.EGG_GRULLO_SPECKLE.get();
            case 138 -> ModItems.EGG_KHAKIDARK_SPECKLE.get();
            case 139 -> ModItems.EGG_CAROB_SPECKLE.get();
            case 140 -> ModItems.EGG_COOLGREY.get();
            case 141 -> ModItems.EGG_PINKGREY_SPECKLE.get();
            case 142 -> ModItems.EGG_WARMGREY_SPECKLE.get();
            case 143 -> ModItems.EGG_ARTICHOKE_SPECKLE.get();
            case 144 -> ModItems.EGG_MYRTLEGREY_SPECKLE.get();
            case 145 -> ModItems.EGG_RIFLE_SPECKLE.get();
            case 146 -> ModItems.EGG_JADE_SPECKLE.get();
            case 147 -> ModItems.EGG_PISTACHIO_SPECKLE.get();
            case 148 -> ModItems.EGG_SAGE_SPECKLE.get();
            case 149 -> ModItems.EGG_ROSEMARY_SPECKLE.get();
            case 150 -> ModItems.EGG_GREENBROWN_SPECKLE.get();
            case 151 -> ModItems.EGG_UMBER_SPECKLE.get();
            case 152 -> ModItems.EGG_WHITE.get();
            case 153 -> ModItems.EGG_CREAMLIGHT.get();
            case 154 -> ModItems.EGG_CREAM_SPATTER.get();
            case 155 -> ModItems.EGG_CREAMDARK_SPATTER.get();
            case 156 -> ModItems.EGG_CARMEL_SPATTER.get();
            case 157 -> ModItems.EGG_CARMELDARK_SPATTER.get();
            case 158 -> ModItems.EGG_GARNET_SPATTER.get();
            case 159 -> ModItems.EGG_PINKLIGHT.get();
            case 160 -> ModItems.EGG_PINK_SPATTER.get();
            case 161 -> ModItems.EGG_PINKDARK_SPATTER.get();
            case 162 -> ModItems.EGG_CHERRY_SPATTER.get();
            case 163 -> ModItems.EGG_CHERRYDARK_SPATTER.get();
            case 164 -> ModItems.EGG_PLUM_SPATTER.get();
            case 165 -> ModItems.EGG_BROWNLIGHT_SPATTER.get();
            case 166 -> ModItems.EGG_BROWN_SPATTER.get();
            case 167 -> ModItems.EGG_BROWNDARK_SPATTER.get();
            case 168 -> ModItems.EGG_CHOCOLATE_SPATTER.get();
            case 169 -> ModItems.EGG_CHOCOLATEDARK_SPATTER.get();
            case 170 -> ModItems.EGG_CHOCOLATECOSMOS.get();
            case 171 -> ModItems.EGG_BLUE.get();
            case 172 -> ModItems.EGG_GREENLIGHT.get();
            case 173 -> ModItems.EGG_GREENYELLOW_SPATTER.get();
            case 174 -> ModItems.EGG_OLIVELIGHT_SPATTER.get();
            case 175 -> ModItems.EGG_OLIVE_SPATTER.get();
            case 176 -> ModItems.EGG_OLIVEDARK_SPATTER.get();
            case 177 -> ModItems.EGG_ARMY_SPATTER.get();
            case 178 -> ModItems.EGG_BLUEGREY.get();
            case 179 -> ModItems.EGG_GREY_SPATTER.get();
            case 180 -> ModItems.EGG_GREYGREEN_SPATTER.get();
            case 181 -> ModItems.EGG_AVOCADO_SPATTER.get();
            case 182 -> ModItems.EGG_AVOCADODARK_SPATTER.get();
            case 183 -> ModItems.EGG_FELDGRAU_SPATTER.get();
            case 184 -> ModItems.EGG_MINT_SPATTER.get();
            case 185 -> ModItems.EGG_GREEN_SPATTER.get();
            case 186 -> ModItems.EGG_GREENDARK_SPATTER.get();
            case 187 -> ModItems.EGG_PINE_SPATTER.get();
            case 188 -> ModItems.EGG_PINEDARK_SPATTER.get();
            case 189 -> ModItems.EGG_PINEBLACK_SPATTER.get();
            case 190 -> ModItems.EGG_POWDERBLUE.get();
            case 191 -> ModItems.EGG_TEA.get();
            case 192 -> ModItems.EGG_MATCHA_SPATTER.get();
            case 193 -> ModItems.EGG_MATCHADARK_SPATTER.get();
            case 194 -> ModItems.EGG_MOSS_SPATTER.get();
            case 195 -> ModItems.EGG_MOSSDARK_SPATTER.get();
            case 196 -> ModItems.EGG_GREENUMBER_SPATTER.get();
            case 197 -> ModItems.EGG_GREYBLUE.get();
            case 198 -> ModItems.EGG_GREYNEUTRAL_SPATTER.get();
            case 199 -> ModItems.EGG_LAUREL_SPATTER.get();
            case 200 -> ModItems.EGG_RESEDA_SPATTER.get();
            case 201 -> ModItems.EGG_GREENPEWTER_SPATTER.get();
            case 202 -> ModItems.EGG_GREYDARK_SPATTER.get();
            case 203 -> ModItems.EGG_CELADON_SPATTER.get();
            case 204 -> ModItems.EGG_FERN_SPATTER.get();
            case 205 -> ModItems.EGG_ASPARAGUS_SPATTER.get();
            case 206 -> ModItems.EGG_HUNTER_SPATTER.get();
            case 207 -> ModItems.EGG_HUNTERDARK_SPATTER.get();
            case 208 -> ModItems.EGG_TREEDARK_SPATTER.get();
            case 209 -> ModItems.EGG_PALEBLUE.get();
            case 210 -> ModItems.EGG_HONEYDEW.get();
            case 211 -> ModItems.EGG_EARTH_SPATTER.get();
            case 212 -> ModItems.EGG_KHAKI_SPATTER.get();
            case 213 -> ModItems.EGG_GRULLO_SPATTER.get();
            case 214 -> ModItems.EGG_KHAKIDARK_SPATTER.get();
            case 215 -> ModItems.EGG_CAROB_SPATTER.get();
            case 216 -> ModItems.EGG_COOLGREY.get();
            case 217 -> ModItems.EGG_PINKGREY_SPATTER.get();
            case 218 -> ModItems.EGG_WARMGREY_SPATTER.get();
            case 219 -> ModItems.EGG_ARTICHOKE_SPATTER.get();
            case 220 -> ModItems.EGG_MYRTLEGREY_SPATTER.get();
            case 221 -> ModItems.EGG_RIFLE_SPATTER.get();
            case 222 -> ModItems.EGG_JADE_SPATTER.get();
            case 223 -> ModItems.EGG_PISTACHIO_SPATTER.get();
            case 224 -> ModItems.EGG_SAGE_SPATTER.get();
            case 225 -> ModItems.EGG_ROSEMARY_SPATTER.get();
            case 226 -> ModItems.EGG_GREENBROWN_SPATTER.get();
            case 227 -> ModItems.EGG_UMBER_SPATTER.get();
            case 228 -> ModItems.EGG_WHITE.get();
            case 229 -> ModItems.EGG_CREAMLIGHT.get();
            case 230 -> ModItems.EGG_CREAM_SPOT.get();
            case 231 -> ModItems.EGG_CREAMDARK_SPOT.get();
            case 232 -> ModItems.EGG_CARMEL_SPOT.get();
            case 233 -> ModItems.EGG_CARMELDARK_SPOT.get();
            case 234 -> ModItems.EGG_GARNET_SPOT.get();
            case 235 -> ModItems.EGG_PINKLIGHT.get();
            case 236 -> ModItems.EGG_PINK_SPOT.get();
            case 237 -> ModItems.EGG_PINKDARK_SPOT.get();
            case 238 -> ModItems.EGG_CHERRY_SPOT.get();
            case 239 -> ModItems.EGG_CHERRYDARK_SPOT.get();
            case 240 -> ModItems.EGG_PLUM_SPOT.get();
            case 241 -> ModItems.EGG_BROWNLIGHT_SPOT.get();
            case 242 -> ModItems.EGG_BROWN_SPOT.get();
            case 243 -> ModItems.EGG_BROWNDARK_SPOT.get();
            case 244 -> ModItems.EGG_CHOCOLATE_SPOT.get();
            case 245 -> ModItems.EGG_CHOCOLATEDARK_SPOT.get();
            case 246 -> ModItems.EGG_CHOCOLATECOSMOS.get();
            case 247 -> ModItems.EGG_BLUE.get();
            case 248 -> ModItems.EGG_GREENLIGHT.get();
            case 249 -> ModItems.EGG_GREENYELLOW_SPOT.get();
            case 250 -> ModItems.EGG_OLIVELIGHT_SPOT.get();
            case 251 -> ModItems.EGG_OLIVE_SPOT.get();
            case 252 -> ModItems.EGG_OLIVEDARK_SPOT.get();
            case 253 -> ModItems.EGG_ARMY_SPOT.get();
            case 254 -> ModItems.EGG_BLUEGREY.get();
            case 255 -> ModItems.EGG_GREY_SPOT.get();
            case 256 -> ModItems.EGG_GREYGREEN_SPOT.get();
            case 257 -> ModItems.EGG_AVOCADO_SPOT.get();
            case 258 -> ModItems.EGG_AVOCADODARK_SPOT.get();
            case 259 -> ModItems.EGG_FELDGRAU_SPOT.get();
            case 260 -> ModItems.EGG_MINT_SPOT.get();
            case 261 -> ModItems.EGG_GREEN_SPOT.get();
            case 262 -> ModItems.EGG_GREENDARK_SPOT.get();
            case 263 -> ModItems.EGG_PINE_SPOT.get();
            case 264 -> ModItems.EGG_PINEDARK_SPOT.get();
            case 265 -> ModItems.EGG_PINEBLACK_SPOT.get();
            case 266 -> ModItems.EGG_POWDERBLUE.get();
            case 267 -> ModItems.EGG_TEA.get();
            case 268 -> ModItems.Egg_Matcha_Spot.get();
            case 269 -> ModItems.Egg_MatchaDark_Spot.get();
            case 270 -> ModItems.EGG_MOSS_SPOT.get();
            case 271 -> ModItems.EGG_MOSSDARK_SPOT.get();
            case 272 -> ModItems.EGG_GREENUMBER_SPOT.get();
            case 273 -> ModItems.EGG_GREYBLUE.get();
            case 274 -> ModItems.EGG_GREYNEUTRAL_SPOT.get();
            case 275 -> ModItems.EGG_LAUREL_SPOT.get();
            case 276 -> ModItems.EGG_RESEDA_SPOT.get();
            case 277 -> ModItems.EGG_GREENPEWTER_SPOT.get();
            case 278 -> ModItems.EGG_GREYDARK_SPOT.get();
            case 279 -> ModItems.EGG_CELADON_SPOT.get();
            case 280 -> ModItems.EGG_FERN_SPOT.get();
            case 281 -> ModItems.EGG_ASPARAGUS_SPOT.get();
            case 282 -> ModItems.EGG_HUNTER_SPOT.get();
            case 283 -> ModItems.EGG_HUNTERDARK_SPOT.get();
            case 284 -> ModItems.EGG_TREEDARK_SPOT.get();
            case 285 -> ModItems.EGG_PALEBLUE.get();
            case 286 -> ModItems.EGG_HONEYDEW.get();
            case 287 -> ModItems.EGG_EARTH_SPOT.get();
            case 288 -> ModItems.EGG_KHAKI_SPOT.get();
            case 289 -> ModItems.EGG_GRULLO_SPOT.get();
            case 290 -> ModItems.EGG_KHAKIDARK_SPOT.get();
            case 291 -> ModItems.EGG_CAROB_SPOT.get();
            case 292 -> ModItems.EGG_COOLGREY.get();
            case 293 -> ModItems.EGG_PINKGREY_SPOT.get();
            case 294 -> ModItems.EGG_WARMGREY_SPOT.get();
            case 295 -> ModItems.EGG_ARTICHOKE_SPOT.get();
            case 296 -> ModItems.EGG_MYRTLEGREY_SPOT.get();
            case 297 -> ModItems.EGG_RIFLE_SPOT.get();
            case 298 -> ModItems.EGG_JADE_SPOT.get();
            case 299 -> ModItems.EGG_PISTACHIO_SPOT.get();
            case 300 -> ModItems.EGG_SAGE_SPOT.get();
            case 301 -> ModItems.EGG_ROSEMARY_SPOT.get();
            case 302 -> ModItems.EGG_GREENBROWN_SPOT.get();
            case 303 -> ModItems.EGG_UMBER_SPOT.get();
            default ->

                //TODO set up exception handling and put an exception here we should NEVER generate here.
                    null;
        };

    }

    public void setFertile(){
        this.gestationTimer = 96000;
    }

    public boolean isGoodNestSite(BlockPos pos) {
        ChickenNestTileEntity nestTileEntity = (ChickenNestTileEntity) this.level.getBlockEntity(pos);
        if (nestTileEntity!=null) {
            return !nestTileEntity.isFull();
        } else {
            if (this.level.isEmptyBlock(pos.below())) return false;
            if (this.level.isWaterAt(pos.below())) return false;
            if (!(this.level.getBlockState(pos.below()).isFaceSturdy(this.level.getChunkForCollisions(this.chunkPosition().x, this.chunkPosition().z), pos.below(), Direction.UP))) return false;
            if (this.level.isEmptyBlock(pos)) {
                int blocked = 0;
                int notblocked = 0;
                if (this.level.isEmptyBlock(pos.north())) {
                    notblocked++;
                } else {
                    blocked++;
                }
                if (this.level.isEmptyBlock(pos.east())) {
                    notblocked++;
                } else {
                    blocked++;
                }
                if (this.level.isEmptyBlock(pos.south())) {
                    notblocked++;
                } else {
                    blocked++;
                }
                if (this.level.isEmptyBlock(pos.west())) {
                    notblocked++;
                } else {
                    blocked++;
                }
                if (this.level.isEmptyBlock(pos.above())) {
                    return blocked >= 2;
                } else {
                    return notblocked >= 1 && blocked >= 1;
                }
            }
        }
        return false;
    }

    public float rateNest(BlockPos pos) {
        float score = 0.0F;

        if (this.level.getBlockEntity(pos) instanceof ChickenNestTileEntity nestTileEntity) {
            if (nestTileEntity.isFull()) return 0.0F;
            score += 0.1F;
            if (!nestTileEntity.isEmpty()) score += 0.1F;
            if (!this.level.canSeeSky(pos)) score += 0.1F;
        }

        score+=rateWall(pos, Direction.NORTH);
        score+=rateWall(pos, Direction.SOUTH);
        score+=rateWall(pos, Direction.EAST);
        score+=rateWall(pos, Direction.WEST);
        score+=rateWall(pos, Direction.UP);

        if (this.currentNestScore <= 0.0F) {
            if (-score < this.currentNestScore) {
                this.currentNestScore = -score;
                this.setNest(pos);
            }
        } else if (this.currentNestScore < score) {
            this.currentNestScore = -score;
            this.setNest(pos);
        }
        return score;
    }

    private float rateWall(BlockPos pos, Direction direction) {
        BlockPos directionPos = pos.relative(direction);
        if (this.level.isEmptyBlock(directionPos)) return 0.0F;
        int x = directionPos.getX()/16;
        int z = directionPos.getZ()/16;
        return this.level.getBlockState(directionPos).isSolidRender(this.level.getChunkForCollisions(x, z), directionPos) ? 0.1F : 0.05F;
    }

    static class GoToNestGoal extends Goal {
        private final EnhancedChicken chicken;
        private final double speed;
        private boolean stuck;
        private int closeToNestTryTicks;

        private boolean onNest = false;

        GoToNestGoal(EnhancedChicken chicken, double speedIn) {
            this.chicken = chicken;
            this.speed = speedIn;
        }

        public boolean canUse() {
            if (this.chicken.getNest() == BlockPos.ZERO) {
                return false;
            } else if (this.chicken.isBroody()) {
                return true;
            } else if (this.chicken.isAnimalSleeping()) {
                return false;
            } else if (this.chicken.timeUntilNextEgg<800 && (this.chicken.getOrSetIsFemale() || EanimodCommonConfig.COMMON.omnigenders.get())) {
                return true;
            } else {
                return !this.chicken.getNest().closerToCenterThan(this.chicken.position(), 64.0D);
            }
        }

        public void start() {
            this.chicken.setAIStatus(AIStatus.FOCUSED);
            this.stuck = false;
            this.closeToNestTryTicks = 0;
        }

        public void stop() {
            this.chicken.setAIStatus(AIStatus.NONE);
        }

        public boolean canContinueToUse() {
            boolean canContinue = this.chicken.isBroody() ||
                    ((!chicken.isAnimalSleeping() || this.onNest)
                    && this.chicken.getNest() != BlockPos.ZERO
                    && (this.chicken.timeUntilNextEgg<800 && (this.chicken.getOrSetIsFemale() || EanimodCommonConfig.COMMON.omnigenders.get()))
                    && !this.stuck
                    && (this.closeToNestTryTicks <= 600 || this.onNest));

            if (!canContinue) {
                this.chicken.setAIStatus(AIStatus.NONE);
            }

            return canContinue;
        }

        public void tick() {
            BlockPos blockPos = this.chicken.getNest();
            if (!this.chicken.isBrooding()) {
                boolean flag = blockPos.closerToCenterThan(this.chicken.position(), 16.0D);
                if (flag) {
                    ++this.closeToNestTryTicks;
                }

                if (blockPos.closerToCenterThan(this.chicken.position(), 1.0D)) {
                    this.chicken.moveTo(blockPos.getX()+0.5D, blockPos.getY()+0.0625D, blockPos.getZ()+0.5D);
//                    this.chicken.setPos(blockPos.getX()+0.5D, blockPos.getY()+0.0625D, blockPos.getZ()+0.5D);

                    if (chicken.isGoodNestSite(blockPos)) {
                        if (!this.chicken.isBrooding()) chicken.setBrooding(true);
                        this.onNest = true;
                        Level world = chicken.level;
                        if (world.isEmptyBlock(blockPos)) {
                            List<BlockPos> nestList = new ArrayList<>();
                            if (world.getBlockEntity(blockPos.north()) instanceof ChickenNestTileEntity) nestList.add(blockPos.north());
                            if (world.getBlockEntity(blockPos.south()) instanceof ChickenNestTileEntity) nestList.add(blockPos.south());
                            if (world.getBlockEntity(blockPos.east()) instanceof ChickenNestTileEntity) nestList.add(blockPos.east());
                            if (world.getBlockEntity(blockPos.west()) instanceof ChickenNestTileEntity) nestList.add(blockPos.west());
                            if (nestList.isEmpty()) {
                                if (chicken.currentNestScore < 0.0F) chicken.currentNestScore *= 0.75F;
                                world.setBlock(blockPos, ModBlocks.CHICKEN_NEST.get().defaultBlockState(), 3);
                            } else {
                                BlockPos pos = nestList.get(chicken.random.nextInt(nestList.size()));
                                chicken.rateNest(pos);
                                chicken.setNest(pos);
                            }
                        }
                    } else {
                        chicken.setNest(BlockPos.ZERO);
                    }

                } else if (this.chicken.getNavigation().isDone()) {
                    Vec3 vec3 = new Vec3(blockPos.getX() + 0.5D, blockPos.getY() + 0.0625D, blockPos.getZ() + 0.5D);
                    Vec3 vec31 = DefaultRandomPos.getPosTowards(this.chicken, 16, 3, vec3, (double)((float)Math.PI / 10F));
                    if (vec31 == null) {
                        vec31 = DefaultRandomPos.getPosTowards(this.chicken, 8, 7, vec3, (double)((float)Math.PI / 2F));
                    }

                    if (vec31 != null && !flag && !this.chicken.level.getBlockState(new BlockPos(vec31)).is(Blocks.WATER)) {
                        vec31 = DefaultRandomPos.getPosTowards(this.chicken, 16, 5, vec3, (double)((float)Math.PI / 2F));
                    }

                    if (vec31 == null) {
                        this.stuck = true;
                        return;
                    }

                    this.chicken.getNavigation().moveTo(vec31.x, vec31.y, vec31.z, this.speed);
                }
            } else {
                if (blockPos.closerToCenterThan(this.chicken.position(), 1.0D)) {
                    this.chicken.moveTo(blockPos.getX()+0.5D, blockPos.getY()+0.0625D, blockPos.getZ()+0.5D);
                }
                if (chicken.isBroody()) {
                    if (chicken.level.getBlockEntity(chicken.blockPosition()) instanceof ChickenNestTileEntity nestEntity) {
                        if (nestEntity.incubate()) {
                            nestEntity.hatchEggs(chicken.level, blockPos, chicken.random);
                            chicken.setBroody(false);
                            chicken.setBrooding(false);
                        }
                    }
                }
            }
        }
    }
/*
    static class InteractWithParentGoal extends EnhancedLookAtGoal {
        InteractWithParentGoal(Mob entityIn, Class<? extends LivingEntity> parentClass, float distance) {
            super(entityIn, parentClass, distance);
        }
    }*/
}

