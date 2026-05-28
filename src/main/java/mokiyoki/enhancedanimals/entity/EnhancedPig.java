package mokiyoki.enhancedanimals.entity;

import mokiyoki.enhancedanimals.ai.EnhancedEatPlantsGoal;
import mokiyoki.enhancedanimals.ai.general.EnhancedBreedGoal;
import mokiyoki.enhancedanimals.ai.general.EnhancedLookAtGoal;
import mokiyoki.enhancedanimals.ai.general.EnhancedLookRandomlyGoal;
import mokiyoki.enhancedanimals.ai.general.EnhancedTemptGoal;
import mokiyoki.enhancedanimals.ai.general.EnhancedWaterAvoidingRandomWalkingEatingGoal;
import mokiyoki.enhancedanimals.ai.general.SeekShelterGoal;
import mokiyoki.enhancedanimals.ai.general.StayShelteredGoal;
import mokiyoki.enhancedanimals.entity.genetics.PigGeneticsInitialiser;
import mokiyoki.enhancedanimals.entity.util.Colouration;
import mokiyoki.enhancedanimals.ai.general.EnhancedWanderingGoal;
import mokiyoki.enhancedanimals.ai.general.GrazingGoal;
import mokiyoki.enhancedanimals.ai.general.pig.GrazingGoalPig;
import mokiyoki.enhancedanimals.init.FoodSerialiser;
import mokiyoki.enhancedanimals.init.ModBlocks;
import mokiyoki.enhancedanimals.config.GeneticAnimalsConfig;
import mokiyoki.enhancedanimals.init.ModItems;
import mokiyoki.enhancedanimals.items.CustomizableSaddleEnglish;
import mokiyoki.enhancedanimals.items.CustomizableSaddleWestern;
import mokiyoki.enhancedanimals.items.EnhancedEgg;
import mokiyoki.enhancedanimals.model.modeldata.AnimalModelData;
import mokiyoki.enhancedanimals.model.modeldata.PigModelData;
import mokiyoki.enhancedanimals.renderer.texture.TextureGrouping;
import mokiyoki.enhancedanimals.renderer.texture.TexturingType;
import mokiyoki.enhancedanimals.util.Genes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
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
import net.minecraft.world.entity.ai.goal.FollowParentGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import static mokiyoki.enhancedanimals.init.FoodSerialiser.pigFoodMap;
import static mokiyoki.enhancedanimals.init.ModEntities.ENHANCED_PIG;
import static mokiyoki.enhancedanimals.renderer.textures.PigTexture.calculatePigTexture;
import static mokiyoki.enhancedanimals.util.Reference.PIG_AUTOSOMAL_GENES_LENGTH;

public class EnhancedPig extends EnhancedAnimalRideableAbstract {

    //avalible UUID spaces : [ S X 6 7 - 8 9 10 11 - 12 13 14 15 - 16 17 18 19 - 20 21 22 23 24 25 26 27 28 29 30 31 ]

    private boolean resetTexture = true;

    private static final int SEXLINKED_GENES_LENGTH = 2;

    private UUID angerTargetUUID;
    private int angerLevel;

    private GrazingGoal grazingGoal;

    @OnlyIn(Dist.CLIENT)
    private PigModelData pigModelData;
    private float pigFat = -1.0F;
    private float pigMuscle = 1.0F;

//    private boolean boosting;
//    private int boostTime;
//    private int totalBoostTime;

    public EnhancedPig(EntityType<? extends EnhancedPig> entityType, Level worldIn) {
        super(entityType, worldIn, SEXLINKED_GENES_LENGTH, PIG_AUTOSOMAL_GENES_LENGTH, true);
        this.initilizeAnimalSize();
    }

    private Map<Block, EnhancedEatPlantsGoal.EatValues> createGrazingMap() {
        Map<Block, EnhancedEatPlantsGoal.EatValues> ediblePlants = new HashMap<>();
        ediblePlants.put(Blocks.WHEAT, new EnhancedEatPlantsGoal.EatValues(3, 7, 750));
        ediblePlants.put(Blocks.AZURE_BLUET, new EnhancedEatPlantsGoal.EatValues(3, 7, 750));
        ediblePlants.put(ModBlocks.GROWABLE_AZURE_BLUET.get(), new EnhancedEatPlantsGoal.EatValues(7, 2, 750));
        ediblePlants.put(Blocks.BLUE_ORCHID, new EnhancedEatPlantsGoal.EatValues(3, 7, 375));
        ediblePlants.put(ModBlocks.GROWABLE_BLUE_ORCHID.get(), new EnhancedEatPlantsGoal.EatValues(3, 7, 375));
        ediblePlants.put(Blocks.CORNFLOWER, new EnhancedEatPlantsGoal.EatValues(3, 7, 375));
        ediblePlants.put(ModBlocks.GROWABLE_CORNFLOWER.get(), new EnhancedEatPlantsGoal.EatValues(7, 7, 375));
        ediblePlants.put(Blocks.DANDELION, new EnhancedEatPlantsGoal.EatValues(3, 7, 750));
        ediblePlants.put(ModBlocks.GROWABLE_DANDELION.get(), new EnhancedEatPlantsGoal.EatValues(3, 7, 750));
        ediblePlants.put(Blocks.SUNFLOWER, new EnhancedEatPlantsGoal.EatValues(3, 7, 375));
        ediblePlants.put(ModBlocks.GROWABLE_SUNFLOWER.get(), new EnhancedEatPlantsGoal.EatValues(3, 7, 375));
        ediblePlants.put(Blocks.GRASS, new EnhancedEatPlantsGoal.EatValues(3, 7, 750));
        ediblePlants.put(ModBlocks.GROWABLE_GRASS.get(), new EnhancedEatPlantsGoal.EatValues(3, 7, 750));
        ediblePlants.put(Blocks.TALL_GRASS, new EnhancedEatPlantsGoal.EatValues(3, 7, 750));
        ediblePlants.put(ModBlocks.GROWABLE_TALL_GRASS.get(), new EnhancedEatPlantsGoal.EatValues(3, 7, 750));
        ediblePlants.put(Blocks.FERN, new EnhancedEatPlantsGoal.EatValues(3, 7, 750));
        ediblePlants.put(ModBlocks.GROWABLE_FERN.get(), new EnhancedEatPlantsGoal.EatValues(3, 7, 750));
        ediblePlants.put(Blocks.LARGE_FERN, new EnhancedEatPlantsGoal.EatValues(3, 7, 750));
        ediblePlants.put(ModBlocks.GROWABLE_LARGE_FERN.get(), new EnhancedEatPlantsGoal.EatValues(3, 7, 750));
        ediblePlants.put(Blocks.SWEET_BERRY_BUSH, new EnhancedEatPlantsGoal.EatValues(1, 1, 500));
        ediblePlants.put(Blocks.PUMPKIN, new EnhancedEatPlantsGoal.EatValues(1, 1, 10000));
        ediblePlants.put(Blocks.MELON, new EnhancedEatPlantsGoal.EatValues(1, 1, 10000));

        return ediblePlants;
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        int napmod = this.random.nextInt(1200);
        this.grazingGoal = new GrazingGoalPig(this, 1.0D);
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new EnhancedBreedGoal(this, 1.0D));
        this.targetSelector.addGoal(2, new EnhancedPig.HurtByAggressorGoal(this));
        this.targetSelector.addGoal(3, new EnhancedPig.TargetAggressorGoal(this));
        this.goalSelector.addGoal(4, new EnhancedTemptGoal(this, 1.0D, 1.2D, false, Items.CARROT_ON_A_STICK));
        this.goalSelector.addGoal(4, new EnhancedTemptGoal(this, 1.0D, 1.2D, false, Items.AIR));
        this.goalSelector.addGoal(5, new MeleeAttackGoal(this, 1.0D, true));
        this.goalSelector.addGoal(6, new FollowParentGoal(this, 1.1D));
        this.goalSelector.addGoal(7, new StayShelteredGoal(this, 6000, 7500, napmod));
        this.goalSelector.addGoal(8, new SeekShelterGoal(this, 1.0D, 6000, 7500, napmod));
        this.goalSelector.addGoal(9, new EnhancedEatPlantsGoal(this, createGrazingMap()));
        this.goalSelector.addGoal(10, this.grazingGoal);
        this.goalSelector.addGoal(9, new EnhancedWaterAvoidingRandomWalkingEatingGoal(this, 1.0D, 7, 0.001F, 120, 2, 50));
        this.goalSelector.addGoal(11, new EnhancedWanderingGoal(this, 1.0D));
        this.goalSelector.addGoal(12, new EnhancedLookAtGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(13, new EnhancedLookAtGoal(this, EnhancedAnimalAbstract.class, 6.0F));
        this.goalSelector.addGoal(14, new EnhancedLookRandomlyGoal(this));
    }

    @Override
    protected FoodSerialiser.AnimalFoodMap getAnimalFoodType() {
        return pigFoodMap();
    }

    @Override
    public boolean canHaveBridle() {
        return false;
    }

    @Override
    protected void customServerAiStep() {

        LivingEntity livingentity = this.getLastHurtByMob();
        if (this.isAngry()) {

            --this.angerLevel;
            LivingEntity livingentity1 = livingentity != null ? livingentity : this.getTarget();
            if (!this.isAngry() && livingentity1 != null) {
                if (!this.hasLineOfSight(livingentity1)) {
                    this.setLastHurtByMob((LivingEntity)null);
                    this.setTarget((LivingEntity)null);
                } else {
                    this.angerLevel = this.angerAmount();
                }
            }
            this.awaken();
        }

        if (this.isAngry() && this.angerTargetUUID != null && livingentity == null) {
            Player playerentity = this.level.getPlayerByUUID(this.angerTargetUUID);
            this.setLastHurtByMob(playerentity);
            this.lastHurtByPlayer = playerentity;
            this.lastHurtByPlayerTime = this.getLastHurtByMobTimestamp();
        }
        this.animalEatingTimer = this.grazingGoal.getEatingGrassTimer();
        super.customServerAiStep();
    }

    @Override
    public double getPassengersRidingOffset() {
        ItemStack saddleSlot = this.getEnhancedInventory().getItem(1);
        double yPos;
        if (saddleSlot.getItem() instanceof CustomizableSaddleWestern) {
            yPos = 0.87D;
        } else if (saddleSlot.getItem() instanceof CustomizableSaddleEnglish) {
            yPos = 0.81D;
        } else {
            yPos = 0.75D;
        }

        float size = this.getAnimalSize();
        size = (( 3.0F * size * this.growthAmount()) + size) / 4.0F;

        return yPos*(Math.pow(size, 1.2F));
    }

    @Override
    public EntityDimensions getDimensions(Pose poseIn) {
        return EntityDimensions.scalable(0.9F, 1.0F).scale(this.getScale());
    }

    @Override
    public float getScale() {
        float size = this.getAnimalSize() > 0.0F ? this.getAnimalSize() : 1.0F;
        float newbornSize = 0.4F;
        return this.isGrowing() ? (newbornSize + ((size-newbornSize) * (this.growthAmount()))) : size;
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
    }

    @Override
    protected String getSpecies() {
        return "entity.eanimod.enhanced_pig";
    }

    @Override
    protected int getAdultAge() {
        if (this.adultAge != null) return this.adultAge;
        this.adultAge = GeneticAnimalsConfig.COMMON.adultAgePig.get();
        return this.adultAge;
    }

    @Override
    protected int getFullSizeAge() {
        return (int)(getAdultAge() * 1.25);
    }

    @Override
    public InteractionResult mobInteract(Player entityPlayer, InteractionHand hand) {
        ItemStack itemStack = entityPlayer.getItemInHand(hand);
        Item item = itemStack.getItem();

        if (item == ModItems.ENHANCED_PIG_EGG.get()) {
            return InteractionResult.SUCCESS;
        }

        if (item == Items.NAME_TAG) {
            itemStack.interactLivingEntity(entityPlayer, this, hand);
            return InteractionResult.SUCCESS;
        }else if ((!this.isBaby() || !bottleFeedable) && item instanceof EnhancedEgg && hunger >= 6000) {
            //enhancedegg egg eating
            decreaseHunger(100);
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

    protected SoundEvent getAmbientSound() {
        if (isAnimalSleeping()) {
            return null;
        }
        return SoundEvents.PIG_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.PIG_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.PIG_DEATH;
    }

    protected void playStepSound(BlockPos pos, BlockState blockIn) {
        super.playStepSound(pos, blockIn);
        this.playSound(SoundEvents.PIG_STEP, 0.15F, 1.0F);
        if (!this.isSilent() && this.getBells()) {
            this.playSound(SoundEvents.NOTE_BLOCK_CHIME, 1.5F, 0.5F);
        }
    }

    protected float getSoundVolume() {
        return 0.4F;
    }

    public static AttributeSupplier.Builder prepareAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 10.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.25D)
                .add(Attributes.ATTACK_DAMAGE, 2.0D)
                .add(JUMP_STRENGTH);
    }

    public void aiStep() {
        super.aiStep();
    }

    @Override
    protected void runExtraIdleTimeTick() {
    }

    @Override
    protected int gestationConfig() {
        return GeneticAnimalsConfig.COMMON.gestationDaysPig.get();
    }

    protected  void incrementHunger() {
        if(this.sleeping) {
            hunger = hunger + (1.0F*getHungerModifier());
        } else {
            hunger = hunger + (2.0F*getHungerModifier());
        }
    }

    @Override
    protected int getNumberOfChildren() {
        int[] genes = this.genetics.getAutosomalGenes();
        int pigletAverage = 11;
        int pigletRange = 4;
        int age = this.getEnhancedAnimalAge();

        if (genes[58] == 1 || genes[59] == 1) {
            pigletAverage = 4;
            pigletRange = 3;
        } else if (genes[58] == 2 && genes[59] == 2) {
            pigletAverage = 8;
            pigletRange = 3;
        } else if (genes[58] == 2 || genes[59] == 2) {
            pigletAverage = 8;
        }

        //hypertrophy reduces fertility
        if (genes[172] == 2 || genes[173] == 2) {
            pigletAverage = (pigletAverage*2)/3;
        }

        if (age < 108000) {
            if (age > 100000) {
                pigletAverage = (pigletAverage*5)/6;
            } else if (age > 92000) {
                pigletAverage = (pigletAverage*4)/6;
            } else if (age > 84000) {
                pigletAverage = (pigletAverage*3)/6;
            } else if (age > 76000) {
                pigletAverage = (pigletAverage*2)/6;
            } else if (age > 68000) {
                pigletAverage = pigletAverage/6;
            } else {
                pigletAverage = pigletAverage/12;
            }
        }

        return Math.max(1, (ThreadLocalRandom.current().nextInt(pigletRange*2) - (pigletRange)) + pigletAverage);
    }

    @Override
    protected EnhancedAnimalAbstract createEnhancedChild(Level level, EnhancedAnimalAbstract otherParent) {
        EnhancedPig enhancedpig = ENHANCED_PIG.get().create(this.level);
        Genes babyGenes = new Genes(this.genetics).makeChild(this.getOrSetIsFemale(), otherParent.getOrSetIsFemale(), otherParent.getGenes());
        enhancedpig.setGenes(babyGenes);
        enhancedpig.setSharedGenes(babyGenes);
        enhancedpig.setSireName(otherParent.getCustomName()==null ? "???" : otherParent.getCustomName().getString());
        enhancedpig.setDamName(this.getCustomName()==null ? "???" : this.getCustomName().getString());
        enhancedpig.setParent(this.getUUID().toString());
        enhancedpig.setGrowingAge();
        enhancedpig.setBirthTime();
        enhancedpig.initilizeAnimalSize();
        enhancedpig.setEntityStatus(EntityState.CHILD_STAGE_ONE.toString());
        enhancedpig.moveTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), 0.0F);
        return enhancedpig;
    }

    protected void createAndSpawnEnhancedChild(Level level) {
        EnhancedPig enhancedpig = ENHANCED_PIG.get().create(this.level);
        Genes babyGenes = new Genes(this.genetics).makeChild(this.getOrSetIsFemale(), this.mateGender, this.mateGenetics);
        defaultCreateAndSpawn(enhancedpig, level, babyGenes, -this.getAdultAge());

        this.level.addFreshEntity(enhancedpig);
    }

    @Override
    protected boolean canBePregnant() {
        return true;
    }

    @Override
    protected boolean canLactate() {
        return false;
    }


    static class HurtByAggressorGoal extends HurtByTargetGoal {
        public HurtByAggressorGoal(EnhancedPig entity) {
            super(entity);
            this.setAlertOthers(new Class[]{EnhancedPig.class});
        }

        protected void alertOther(Mob mobIn, LivingEntity targetIn) {
            if (mobIn instanceof EnhancedPig && this.mob.hasLineOfSight(targetIn) && ((EnhancedPig)mobIn).becomeAngryAt(targetIn)) {
                mobIn.setTarget(targetIn);
            }

        }
    }

    static class TargetAggressorGoal extends NearestAttackableTargetGoal<Player> {
        public TargetAggressorGoal(EnhancedPig entity) {
            super(entity, Player.class, true);
        }

        /**
         * Returns whether the EntityAIBase should begin execution.
         */
        public boolean canUse() {
            return ((EnhancedPig)this.mob).isAngry() && super.canUse();
        }
    }

    @Override
    public boolean doHurtTarget(Entity entityIn) {
        boolean flag = entityIn.hurt(DamageSource.mobAttack(this), (float)((int)this.getAttribute(Attributes.ATTACK_DAMAGE).getValue()));
        if (flag) {
            this.doEnchantDamageEffects(this, entityIn);
        }

        return flag;
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (this.isInvulnerableTo(source)) {
            return false;
        } else {
            Entity entity = source.getEntity();
            if (entity instanceof Player && !((Player)entity).isCreative() && this.hasLineOfSight(entity)) {
                this.becomeAngryAt(entity);
            }

            return super.hurt(source, amount);
        }
    }

    private boolean becomeAngryAt(Entity entity) {
        this.angerLevel = this.angerAmount();
//        this.randomSoundDelay = this.rand.nextInt(40);
        if (entity instanceof LivingEntity) {
            this.setLastHurtByMob((LivingEntity)entity);
        }

        return true;
    }

    @Override
    public void setLastHurtByMob(@Nullable LivingEntity livingBase) {
        super.setLastHurtByMob(livingBase);
        if (livingBase != null) {
            this.angerTargetUUID = livingBase.getUUID();
        }

    }

    private int angerAmount() {
        return 400 + this.random.nextInt(400);
    }

    private boolean isAngry() {
        return this.angerLevel > 0;
    }

    @Override
    protected float getJumpHeight() {
        return 0.4F;
    }

    protected float getJumpFactorModifier() {
        return 0.05F;
    }

    @Override
    protected float getMovementFactorModifier() {
        float speedMod = 1.0F;
        int[] genes = this.getGenes().getAutosomalGenes();

        float muscle = getPigMuscle();

        float fat = getPigFat();

        float size = this.getAnimalSize();

        if (fat > 0.525F) {
            speedMod -= fat * 0.5F;
        }

        speedMod += Math.min(muscle, 0.6F)*0.5F;

        float chestMod = 0.0F;
        ItemStack chestSlot = this.getEnhancedInventory().getItem(0);
        if (chestSlot.getItem() == Items.CHEST) {
            chestMod = (1.0F-((size-0.7F)*1.25F)) * 0.4F;
        }

        return 0.4F + (speedMod * 0.6F) - chestMod;
    }

    @Override
    protected boolean shouldDropExperience() { return true; }

    protected void dropCustomDeathLoot(DamageSource source, int looting, boolean recentlyHitIn) {
        super.dropCustomDeathLoot(source, looting, recentlyHitIn);
        int[] genes = this.getGenes().getAutosomalGenes();
        float size = (this.getAnimalSize()-0.7F)*1.25F; // 0 to 1
        int age = this.getEnhancedAnimalAge();
        int meatDrop;
        int meatChanceMod;
        float muscle = 0.0F; // 0 to 1
        float fat = 0.0F; // 0 to 1
        float length = 0.0F; // 0 to 1
        for (int i = 166; i < 172; i++) {
            muscle += (genes[i] / 80.0F);
        }
        if (genes[172] == 2 || genes[173] == 2) {
            muscle += 0.25F;
        }
        for (int i = 174; i < 182; i++) {
            fat += (genes[i] / 80.0F);
        }

        for (int i = 182; i < 190; i++) {
            length += (genes[i] / 80.0F);
        }

        // max of 4 meat from size
        meatDrop = (int)((size*3.1)) + 1;

        if (genes[44] != 3 && genes[45] != 3) {
            meatChanceMod = (int)((length*4.1)+(fat*4.1)+(muscle*1.1));
            if (meatChanceMod >= 4) {
                if (meatChanceMod >= 9) {
                    //100% chance to +2
                    meatDrop += 2;
                } else if (meatChanceMod == 8) {
                    //100% chance to + 1
                    if (this.random.nextInt(4) == 0) {
                        // 25% chance to +1 additionally
                        meatDrop = meatDrop + 2;
                    } else {
                        meatDrop++;
                    }
                } else if (meatChanceMod == 7) {
                    if (this.random.nextInt(4) != 0) {
                        //75% chance to + 1
                        meatDrop++;
                    }
                    if (this.random.nextInt(4) == 0) {
                        //25% chance to + 1
                        meatDrop++;
                    }
                } else if (meatChanceMod == 6) {
                    // 50% chance to + 1
                    if (this.random.nextInt(2) == 0) {
                        meatDrop++;
                    }
                } else if (meatChanceMod == 5) {
                    // 25% chance to + 1
                    if (this.random.nextInt(4) == 0) {
                        meatDrop++;
                    }
                }
            } else {
                if (meatChanceMod == 3) {
                    // 25% chance to - 1
                    if (this.random.nextInt(4) == 0) {
                        meatDrop--;
                    }
                } else if (meatChanceMod == 2) {
                    // 50% chance to - 1
                    if (this.random.nextInt(2) == 0) {
                        meatDrop--;
                    }
                } else {
                    if (this.random.nextInt(4) != 0) {
                        //75% chance to - 1
                        meatDrop--;
                    }
                }
            }
        }

        if (meatDrop <= 0) {
            meatDrop = 1;
        }

        int adultAge = this.getAdultAge();
        int tier = adultAge * 3 / 10;
        if (age < tier * 6) {
            if (age >= tier * 5) {
                meatDrop = meatDrop - 1;
                meatChanceMod = (age - tier * 5) * 100 / tier;
            } else if (age >= tier * 4) {
                meatDrop = meatDrop - 2;
                meatChanceMod = (age - tier * 4) * 100 / tier;
            } else if (age >= tier * 3) {
                meatDrop = meatDrop - 3;
                meatChanceMod = (age - tier * 3) * 100 / tier;
            } else if (age >= tier * 2) {
                meatDrop = meatDrop - 4;
                meatChanceMod = (age - tier * 2) * 100 / tier;
            } else if (age >= tier) {
                meatDrop = meatDrop - 5;
                meatChanceMod = (age - tier) * 100 / tier;
            } else {
                meatDrop = meatDrop - 6;
                meatChanceMod = age * 100 / tier;
            }

            int i = this.random.nextInt(100);
            if (meatChanceMod > i) {
                meatDrop++;
            }
        }

        if (meatDrop > 6) {
            meatDrop = 6;
        } else if (meatDrop < 0) {
            meatDrop = 0;
        }

        if (this.isOnFire()){
            ItemStack cookedPorkStack = new ItemStack(Items.COOKED_PORKCHOP, meatDrop);
            this.spawnAtLocation(cookedPorkStack);
        }else {
            ItemStack porkStack = new ItemStack(Items.PORKCHOP, meatDrop);
            this.spawnAtLocation(porkStack);
        }
    }

    public void lethalGenes(){
        int[] genes = this.getGenes().getAutosomalGenes();
        if(genes[12] == 12 && genes[13] == 12) {
            this.remove(RemovalReason.KILLED);
        }
    }

    public void ate() {
//        if (this.isChild()) {
//            this.addGrowth(60);
//        }
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public PigModelData getModelData() {
        return this.pigModelData;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void setModelData(AnimalModelData animalModelData) {
        this.pigModelData = (PigModelData) animalModelData;
    }

    @OnlyIn(Dist.CLIENT)
    public String getTexture() {
        if (this.enhancedAnimalTextureGrouping == null) {
            this.setTexturePaths();
        } else if (this.resetTexture && !this.isBaby()) {
            this.resetTexture = false;
            this.reloadTextures();
        }

        return getCompiledTextures("enhanced_pig");
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    protected void setTexturePaths() {
        if (this.getGenes() != null) {
            calculatePigTexture(this, this.getGenes().getAutosomalGenes(), getStringUUID().toCharArray());
        }
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    protected void setAlphaTexturePaths() {
    }

    //TODO put item interactable stuff here like saddling pigs


    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);

        compound.putShort("Anger", (short)this.angerLevel);
        if (this.angerTargetUUID != null) {
            compound.putString("HurtBy", this.angerTargetUUID.toString());
        } else {
            compound.putString("HurtBy", "");
        }

//        compound.putBoolean("Saddle", this.getSaddled());
    }

    /**
     * (abstract) Protected helper method to read subclass entity assets from NBT.
     */
    public void readAdditionalSaveData(CompoundTag compound) {

        super.readAdditionalSaveData(compound);

        this.angerLevel = compound.getShort("Anger");
        String s = compound.getString("HurtBy");
        if (!s.isEmpty()) {
            this.angerTargetUUID = UUID.fromString(s);
            Player playerentity = this.level.getPlayerByUUID(this.angerTargetUUID);
            this.setLastHurtByMob(playerentity);
            if (playerentity != null) {
                this.lastHurtByPlayer = playerentity;
                this.lastHurtByPlayerTime = this.getLastHurtByMobTimestamp();
            }
        }
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor inWorld, DifficultyInstance difficulty, MobSpawnType spawnReason, @Nullable SpawnGroupData livingdata, @Nullable CompoundTag itemNbt) {
        registerAnimalAttributes();
        return commonInitialSpawnSetup(inWorld, livingdata, 60000, 30000, 80000, spawnReason);
    }


//    @Override
//    protected int[] createInitialSpawnChildGenes(int[] spawnGenes1, int[] spawnGenes2, int[] mitosis, int[] mateMitosis) {
//        return replaceGenes(getPigletGenes(mitosis, mateMitosis), spawnGenes1);
//    }
    private int[] replaceGenes(int[] resultGenes, int[] groupGenes) {
        resultGenes[20] = groupGenes[20];
        resultGenes[21] = groupGenes[21];
        resultGenes[22] = groupGenes[22];
        resultGenes[23] = groupGenes[23];
        resultGenes[24] = groupGenes[24];
        resultGenes[25] = groupGenes[25];
        resultGenes[26] = groupGenes[26];
        resultGenes[27] = groupGenes[27];
        resultGenes[28] = groupGenes[28];
        resultGenes[29] = groupGenes[29];
        resultGenes[30] = groupGenes[30];
        resultGenes[31] = groupGenes[31];

        return resultGenes;
    }

    @Override
    protected Genes createInitialGenes(LevelAccessor world, BlockPos pos, boolean isDomestic) {
        return new PigGeneticsInitialiser().generateNewGenetics(world, pos, isDomestic);
    }

    @Override
    public Genes createInitialBreedGenes(LevelAccessor world, BlockPos pos, String breed) {
        return new PigGeneticsInitialiser().generateWithBreed(world, pos, breed);
    }

    @Override
    protected void initializeHealth(EnhancedAnimalAbstract animal, float health) {
//        int[] genes = animal.genetics.getAutosomalGenes();

        health = 5.0F + (5.0F * (this.getAnimalSize()));
        if (health > 10.0F) {
            health = 10.0F;
        }

        super.initializeHealth(animal, health);
    }

    public void initilizeAnimalSize() {
        int[] genes = this.genetics.getAutosomalGenes();
        float size = 0.4F;

        // [44/45] (1-3) potbelly dwarfism [wildtype, dwarfStrong, dwarfWeak]
        // [46/47] (1-2) potbelly dwarfism2 [wildtype, dwarf]
        // [48/49] (1-15) size genes reducer [wildtype, smaller smaller smallest...]
        // [50/51] (1-15) size genes adder [wildtype, bigger bigger biggest...]
        // [52/53] (1-3) size genes varient1 [wildtype, smaller, smallest]
        // [54/55] (1-3) size genes varient2 [wildtype, larger, largest]

        size = size - (genes[48] - 1)*0.0125F;
        size = size - (genes[49] - 1)*0.0125F;
        size = size + (genes[50] - 1)*0.0125F;
        size = size + (genes[51] - 1)*0.0125F;

        if (genes[44] != 1 && genes[45] != 1) {
            if (genes[44] == 2 || genes[45] == 2) {
                //smaller rounder
                size = size * 0.9F;
            } else {
                //smaller roundest
                size = size * 0.8F;
            }
        }

        if (genes[46] == 2 && genes[47] == 2) {
            //smaller rounder
            size = size * 0.9F;
        }

        if (genes[52] == 2 || genes[53] == 2) {
            size = size * 0.975F;
        } else if (genes[52] == 3 || genes[53] == 3) {
            size = size * 0.925F;
        }

        if (genes[54] == 2 || genes[55] == 2) {
            size = size * 1.025F;
        } else if (genes[54] == 3 || genes[55] == 3) {
            size = size * 1.075F;
        }


        if (size > 0.8F) {
            size = 0.8F;
        }

        size = size + 0.7F;

        // 0.7F <= size <= 1.5F
        this.setAnimalSize(size);
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

    @Override
    protected void geneFixer() {
        int[] genes = this.genetics.getAutosomalGenes();
        for (int i = 0; i < 206; i++) {
            if (genes[i] == 0) {
                if (i >= 174 && i < 190) {
                    genes[i] = 4;
                }
                else if (i >= 202 && i < 206) {
                    genes[i] = 2;
                }
                else {
                    genes[i] = 1;
                }
            }
        }
        super.geneFixer();
    }

    @Override
    protected void fixGeneLengths() {
        if (this.genetics.getNumberOfAutosomalGenes() < PIG_AUTOSOMAL_GENES_LENGTH) {
            this.genetics.setAutosomalGenes(Arrays.copyOf(this.genetics.getAutosomalGenes(), PIG_AUTOSOMAL_GENES_LENGTH));
        }
    }

/*    @Override
    public Colouration getRgb() {
        boolean flag = (this.colouration.getMelaninColour() == -1 || this.colouration.getPheomelaninColour() == -1) && getSharedGenes()!=null;
        this.colouration = super.getRgb();

        if(this.colouration == null) {
            return null;
        }

        if (flag) {
            float[] melanin = Colouration.getHSBFromABGR(this.colouration.getMelaninColour());
            float[] pheomelanin = Colouration.getHSBFromABGR(this.colouration.getPheomelaninColour());

            if (this.isBaby()) {
                //melanin[1] -= 0.15F;
                //pheomelanin[1] -= 0.15F;
                melanin[2] = 0.5F;
                //pheomelanin[2] += 0.1F;
                //checks that numbers are within the valid range
                for (int i = 0; i <= 2; i++) {
                    if (melanin[i] > 1.0F) {
                        melanin[i] = 1.0F;
                    } else if (melanin[i] < 0.0F) {
                        melanin[i] = 0.0F;
                    }
                    if (pheomelanin[i] > 1.0F) {
                        pheomelanin[i] = 1.0F;
                    } else if (pheomelanin[i] < 0.0F) {
                        pheomelanin[i] = 0.0F;
                    }
                }
            }

            this.colouration.setMelaninColour(Colouration.HSBAtoABGR(melanin[0], melanin[1], melanin[2], 1.0F));
            this.colouration.setPheomelaninColour(Colouration.HSBAtoABGR(pheomelanin[0], pheomelanin[1], pheomelanin[2], 1.0F));
        }
        return this.colouration;
    }*/

    private float getPigMuscle() {
        if (this.pigMuscle < 0.0F) {
            int[] genes = this.getGenes().getAutosomalGenes();
            this.pigMuscle = 0.0F;
            for (int i = 166; i < 172; i++) {
                this.pigMuscle += (genes[i] / 80.0F);
            }
            if (genes[172] == 2 || genes[173] == 2) {
                this.pigMuscle += 0.25F;
            }
        }
        return this.pigMuscle;
    };


    private float getPigFat() {
        if (this.pigFat < 0.0F) {
            int[] genes = this.getGenes().getAutosomalGenes();
            this.pigFat = 0.0F;
            for (int i = 174; i < 182; i++) {
                this.pigFat += (genes[i] / 80.0F);
            }
        }
        return this.pigFat;
    };

}
