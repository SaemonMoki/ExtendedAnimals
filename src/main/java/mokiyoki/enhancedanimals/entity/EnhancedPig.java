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
import mokiyoki.enhancedanimals.config.EanimodCommonConfig;
import mokiyoki.enhancedanimals.init.ModItems;
import mokiyoki.enhancedanimals.items.CustomizableSaddleEnglish;
import mokiyoki.enhancedanimals.items.CustomizableSaddleWestern;
import mokiyoki.enhancedanimals.items.EnhancedEgg;
import mokiyoki.enhancedanimals.model.modeldata.AnimalModelData;
import mokiyoki.enhancedanimals.model.modeldata.PigModelData;
import mokiyoki.enhancedanimals.renderer.texture.TextureGrouping;
import mokiyoki.enhancedanimals.renderer.texture.TexturingType;
import mokiyoki.enhancedanimals.util.Genes;
import mokiyoki.enhancedanimals.util.Reference;
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
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import static mokiyoki.enhancedanimals.init.FoodSerialiser.pigFoodMap;
import static mokiyoki.enhancedanimals.init.ModEntities.ENHANCED_PIG;

public class EnhancedPig extends EnhancedAnimalRideableAbstract {

    //avalible UUID spaces : [ S X 5 6 7 - 8 9 10 11 - 12 13 14 15 - 16 17 18 19 - 20 21 22 23 24 25 26 27 28 29 30 31 ]
    private boolean resetTexture = true;
    private static final String[] PIG_TEXTURES_SKINBASE = new String[] {
            "", "skin_pink.png", "skin_grey.png", "skin_black.png", "skin_brown.png", "skin_chocolate.png"
    };

    private static final String[] PIG_TEXTURES_SKINMARKINGS_SPOTS = new String[] {
    		//deprecated
            "", "skin_spots.png", ""
    };

    private static final String[] PIG_TEXTURES_SKINMARKINGS_WHITE = new String[] {
            "", "skin_pink.png", "skin_belt.png", "skin_patchy.png", "skin_roan.png",
            "skin_bluebuttbelt.png", "skin_bluebuttbelt", "skin_bluebuttbelt.png",
            "brindlepatch_skin_1.png", "brindlepatch_skin_2.png", "brindlepatch_skin_3.png", "brindlepatch_coat_4.png","brindlepatch_coat_5.png",
            "brindlepatch_med_skin_1.png", "brindlepatch_med_skin_2.png", "brindlepatch_med_skin_3.png", "brindlepatch_med_skin_4.png",
            "patch_coat_1.png", "patch_coat_2.png", "patch_coat_3.png", "patch_coat_4.png", "patch_coat_5.png", "patch_coat_6.png", "patch_coat_7.png", "patch_coat_8.png", "patch_coat_9.png", "patch_coat_10.png",
            "spotted_patch_coat_1.png", "spotted_patch_coat_2.png", "spotted_patch_coat_3.png", "spotted_patch_coat_4.png", "spotted_patch_coat_5.png",
            "whitehead_belly_large_1.png", "whitehead_belly_large_2.png", "whitehead_belly_large_3.png", "whitehead_belly_large_4.png", "whitehead_belly_large_5.png",
            "tux_min_1.png", "tux_min_2.png", "tux_min_3.png",
            "tux_med_1.png", "tux_med_2.png", "tux_med_3.png", "tux_med_4.png", "tux_med_5.png", "tux_med_6.png",
            "tux_high_1.png", "tux_high_2.png", "tux_high_3.png", "tux_high_4.png",
            "huge_belt_coat_1.png", "huge_belt_coat_2.png", "huge_belt_coat_3.png", "huge_belt_coat_4.png", "huge_belt_coat_5.png", "huge_belt_coat_6.png", "huge_belt_coat_7.png",
    };

    private final int idx_brindlepatch = 8;
    private final int idx_brindlepatch_med = 13;
    private final int idx_patch = 17;
    private final int idx_spottedpatch = 27;
    private final int idx_whiteheadbelly = 32;
    private final int idx_tuxmin = 37;
    private final int idx_tuxmed = 40;
    private final int idx_tuxhigh = 46;
    private final int idx_hugebelt = 50;
    private final int idx_belt = 57;
    private static final String[] PIG_TEXTURES_COATWHITE = new String[] {
            "pigbase.png", "solid_white.png", "spot_belt.png", "spot_patchy.png", "spot_roan.png", "spot_roanbelted.png", "spot_patchyhetred.png", "spot_patchyhetsilver.png",
            "brindlepatch_coat_1.png", "brindlepatch_coat_2.png", "brindlepatch_coat_3.png", "brindlepatch_coat_4.png","brindlepatch_coat_5.png",
            "brindlepatch_med_coat_1.png", "brindlepatch_med_coat_2.png", "brindlepatch_med_coat_3.png", "brindlepatch_med_coat_4.png",
            "patch_coat_1.png", "patch_coat_2.png", "patch_coat_3.png", "patch_coat_4.png", "patch_coat_5.png", "patch_coat_6.png", "patch_coat_7.png", "patch_coat_8.png", "patch_coat_9.png", "patch_coat_10.png",
            "spotted_patch_coat_1.png", "spotted_patch_coat_2.png", "spotted_patch_coat_3.png", "spotted_patch_coat_4.png", "spotted_patch_coat_5.png",
            "whitehead_belly_large_1.png", "whitehead_belly_large_2.png", "whitehead_belly_large_3.png", "whitehead_belly_large_4.png", "whitehead_belly_large_5.png",
            "tux_min_1.png", "tux_min_2.png", "tux_min_3.png",
            "tux_med_1.png", "tux_med_2.png", "tux_med_3.png", "tux_med_4.png", "tux_med_5.png", "tux_med_6.png",
            "tux_high_1.png", "tux_high_2.png", "tux_high_3.png", "tux_high_4.png",
            "huge_belt_coat_1.png", "huge_belt_coat_2.png", "huge_belt_coat_3.png", "huge_belt_coat_4.png", "huge_belt_coat_5.png", "huge_belt_coat_6.png", "huge_belt_coat_7.png",
            "spot_belt_1.png", "spot_belt_2.png", "spot_belt_3.png", "spot_belt_4.png","spot_belt_5.png", "spot_belt_6.png",
    };

    private static final String[] PIG_TEXTURES_SKINMARKINGS_BERKSHIRE = new String[] {
            "", "skin_tux.png", "skin_berkshire.png"
    };

    private static final String[] PIG_TEXTURES_COATRED = new String[] {
    		"pigbase.png", "coat_base.png", "agouti_base.png"
    };
    private static final String[] PIG_TEXTURES_SKINBRINDLE_SPOTS = new String[] {
            "", "black_berkshirebrindle.png", "skin_brindle.png",
            "brindle_coat_1.png", "brindle_coat_2.png", "brindle_coat_3.png", "brindle_coat_4.png", "brindle_coat_5.png",
            "brindle_med_coat_1.png", "brindle_med_coat_2.png", "brindle_med_coat_3.png", "brindle_med_coat_4.png",
            "brindle_het_med_1.png", "brindle_het_med_2.png", "brindle_het_med_3.png", "brindle_het_med_4.png", "brindle_het_med_5.png",
    };
    private static final String[] PIG_TEXTURES_COATBLACK = new String[] {
    		"pigbase.png", "solid_white.png", "agouti_base.png", "black_berkshirebrindle.png", "black_oopsallspots.png", "black_brindle.png", "black_brindlesmall.png",
    		"brindle_coat_1.png", "brindle_coat_2.png", "brindle_coat_3.png", "brindle_coat_4.png", "brindle_coat_5.png",
    		"brindle_med_coat_1.png", "brindle_med_coat_2.png", "brindle_med_coat_3.png", "brindle_med_coat_4.png",
            "brindle_het_med_1.png", "brindle_het_med_2.png", "brindle_het_med_3.png", "brindle_het_med_4.png", "brindle_het_med_5.png",
    };

    private static final String[] PIG_TEXTURES_SPOT_SPOTS = new String[] {
            //discontinued genes
            "", "spot_spots.png", "spot_roanspots.png"
    };

    private static final String[] PIG_TEXTURES_SPOT_BERKSHIRE = new String[] {
            "", "spot_tux.png", "spot_berkshire.png", "spot_extended_berkshire.png"
    };
    
    private static final String[] PIG_TEXTURES_WHITE_FACE = new String[] {
            "", "whitehead_face_small_1.png", "whitehead_face_small_2.png", "whitehead_face_small_3.png", "whitehead_face_small_4.png", "whitehead_face_small_5.png", "whitehead_face_small_6.png",
            "whitehead_face_med_1.png", "whitehead_face_med_2.png", "whitehead_face_med_3.png", "whitehead_face_med_4.png", "whitehead_face_med_5.png",
            "whitehead_face_large_1.png", "whitehead_face_large_2.png", "whitehead_face_large_3.png", "whitehead_face_large_4.png", "whitehead_face_large_5.png", "whitehead_face_large_6.png",
            "tux_face_min_1.png", "tux_face_min_2.png", "tux_face_min_3.png",
            "tux_face_med_1.png", "tux_face_med_2.png", "tux_face_med_3.png", "tux_face_med_4.png", "tux_face_med_5.png", "tux_face_med_6.png",
            "tux_face_high_1.png", "tux_face_high_2.png", "tux_face_high_3.png", "tux_face_high_4.png",
    };
    
    private static final String[] PIG_TEXTURES_WHITEHEAD_BELLY = new String[] {
            "", "whitehead_belly_small_1.png", "whitehead_belly_small_2.png", "whitehead_belly_small_3.png", "whitehead_belly_small_4.png",
            "whitehead_belly_med_1.png", "whitehead_belly_med_2.png", "whitehead_belly_med_3.png", 
            "whitehead_belly_large_1.png", "whitehead_belly_large_2.png", "whitehead_belly_large_3.png", "whitehead_belly_large_4.png", "whitehead_belly_large_5.png",
    };
    
    private static final String[] PIG_TEXTURES_WHITE_LEG = new String[] {
            "", "whitehead_leg_1.png", "whitehead_leg_2.png", "whitehead_leg_3.png", "whitehead_leg_4.png", "whitehead_leg_5.png"
    };
    
    private static final String[] PIG_TEXTURES_COAT = new String[] {
            "coat_normal.png", "coat_wooly.png", "coat_thick_wool.png"
    };

    private static final String[] PIG_TEXTURES_AGOUTI = new String[]{
            "coat_base.png", "agouti_base.png"
    };
    private static final String[] PIG_TEXTURES_AGOUTI_DARK = new String[]{
            "", "agouti_dark.png"
    };

    private static final String[] PIG_TEXTURES_AGOUTI_LIGHT = new String[]{
            "", "agouti_light.png"
    };
    private static final String[] PIG_TEXTURES_EYES = new String[] {
            "eyes_black.png", "eyes_brown.png", "eyes_blue.png", "eyes_blue_left.png", "eyes_blue_right.png"
    };

    private static final String[] PIG_TEXTURES_HOOVES = new String[] {
            "hooves_black.png", "hoovesmulefoot_black.png"
    };

    private static final String[] PIG_TEXTURES_TUSKS = new String[] {
            "", "tusks.png"
    };

    private static final String[] PIG_TEXTURES_ALPHA = new String[] {
            "bald", "sparse.png", "medium.png", "furry.png", "wooly.png"
    };

    private static final String[] PIG_TEXTURES_SWALLOWBELLY = new String[] {
            "", "pattern_swallowbelly.png"
    };

    private float[] swallowbellyColor = {0.0F, 0.0F, 0.0F};
    
    private static final int SEXLINKED_GENES_LENGTH = 2;

    private UUID angerTargetUUID;
    private int angerLevel;

    private GrazingGoal grazingGoal;

    @OnlyIn(Dist.CLIENT)
    private PigModelData pigModelData;

//    private boolean boosting;
//    private int boostTime;
//    private int totalBoostTime;

    public EnhancedPig(EntityType<? extends EnhancedPig> entityType, Level worldIn) {
        super(entityType, worldIn, SEXLINKED_GENES_LENGTH, Reference.PIG_AUTOSOMAL_GENES_LENGTH, true);
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
    protected int getAdultAge() { return EanimodCommonConfig.COMMON.adultAgePig.get();}

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
        return EanimodCommonConfig.COMMON.gestationDaysPig.get();
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

        return (ThreadLocalRandom.current().nextInt(pigletRange*2) - (pigletRange)) + pigletAverage;
    }

    protected void createAndSpawnEnhancedChild(Level inWorld) {
        EnhancedPig enhancedpig = ENHANCED_PIG.get().create(this.level);
        Genes babyGenes = new Genes(this.genetics).makeChild(this.getOrSetIsFemale(), this.mateGender, this.mateGenetics);
        defaultCreateAndSpawn(enhancedpig, inWorld, babyGenes, -this.getAdultAge());

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
        float size = this.getAnimalSize();
        if (size > 1.05F) {
            speedMod = speedMod/size;
        }

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
        int[] genes = this.genetics.getAutosomalGenes();
        int size = (int)((this.getAnimalSize()-0.7F)*10);
        int age = this.getEnhancedAnimalAge();
        int meatDrop;
        int meatChanceMod;

        // max of 4
        meatDrop = size/2;

        if (genes[44] != 3 && genes[45] != 3) {
            meatChanceMod = (genes[56] + genes[57]) - 6;
            if (meatChanceMod > 4) {
                if (meatChanceMod == 8) {
                    //100% chance to + 1
                    if (this.random.nextInt(4) == 0) {
                        // 25% chance to + 1
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
                } else {
                    // 25% chance to + 1
                    if (this.random.nextInt(4) == 0) {
                        meatDrop++;
                    }
                }
            } else if (meatChanceMod < 4){
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
                } else if (meatChanceMod == 1) {
                    if (this.random.nextInt(4) != 0) {
                        //75% chance to - 1
                        meatDrop--;
                    }
                } else {
                    //100% chance to - 1
                    meatDrop--;
                    if (this.random.nextInt(4) == 0) {
                        // 25% chance to - 1
                        meatDrop--;
                    }
                }
            }
        }

        if (meatDrop <= 0) {
            meatDrop = 1;
        }

        if (age < 108000) {
            if (age >= 90000) {
                meatDrop = meatDrop - 1;
                meatChanceMod = (age-90000)/180;
            } else if (age >= 72000) {
                meatDrop = meatDrop - 2;
                meatChanceMod = (age-72000)/180;
            } else if (age >= 54000) {
                meatDrop = meatDrop - 3;
                meatChanceMod = (age-54000)/180;
            } else if (age >= 36000) {
                meatDrop = meatDrop - 4;
                meatChanceMod = (age-36000)/180;
            } else if (age >= 18000) {
                meatDrop = meatDrop - 5;
                meatChanceMod = (age-18000)/180;
            } else {
                meatDrop = meatDrop - 6;
                meatChanceMod = age/180;
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

        //TODO lethal genes go here

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
        if (this.getSharedGenes() != null) {
            int[] genesForText = getSharedGenes().getAutosomalGenes();

            int brindleSkin = 0;
            int eyes = 0;
            int red = 2;
            int black = 0;
            int spot = 0;
            //int belt = 0;
            int berk = 0;
            int coat_alpha = 0;
            int coat_texture = 0;
            int skin = 0;
            int hooves = 0;
            int swallowbelly = 0;
            int whitePointsFace = 0;
            int whitePointsLeg = 0;
            int whiteExtension = 0;
            int white = 0;
            boolean tusks = false;
            boolean agouti = true;
            boolean agoutiBlack = false;
            boolean isSwallowbellyRed = false;

            char[] uuidArry = getStringUUID().toCharArray();

            // check white points locus first since it affects some white patterns
            if (genesForText[14] == 3 || genesForText[15] == 3) {
                //white points
                whitePointsFace = 1;
            }
            else if (genesForText[14] != 1 && genesForText[15] != 1){
                if (genesForText[14] == 2 || genesForText[15] == 2){
                    //tuxedo
                    berk = 1;
                } else {
                    //berkshire
                    //berk = 2;
                }
            }

            if (genesForText[0] == 1 || genesForText[1] == 1 || genesForText[0] == 5 || genesForText[1] == 5){
                //solid black
                black = 1;
                agouti = false;
            }else if (genesForText[0] == 2 || genesForText[1] == 2){
                //black = 2;
                agoutiBlack = true;
                if (genesForText[164] == 2 && genesForText[165] == 2 && agouti) {
                    //wideband
                }
                else {
                    black = 1;
                }
            } else if (genesForText[0] == 3 || genesForText[1] == 3) {
                //brindle
                isSwallowbellyRed = true;
                // if negative, tamworth; if positive, kitlg/allspots
                int spotPower = (genesForText[64] + genesForText[65]) - (genesForText[62] + genesForText[63]);
                if (genesForText[0] == 3 && genesForText[1] == 3) {
                    //homozygous brindle
                    black = 5;
                    if (spotPower == 2) {
                        //berkshire spots
                        black = 3;
                        brindleSkin = 1;
                    }
                    else if (spotPower == 1) {
                        // het allspots
                        black = 4;
                    }
                    else if (spotPower < 0) {
                        // tamworth
                        black = 0;
                    }
                }
                else {
                    //heterozygous brindle
                    //minimal spots
                    black = 6;
                    if (spotPower == 2) {
                        // berkshire spots
                        black = 16;
                    }
                    else if (spotPower == 1) {
                        // het allspots
                        black = 16;
                    }
                    else if (spotPower < 0) {
                        // tamworth
                        black = 0;
                    }
                }
            }
            else if (genesForText[0] == 4 && genesForText[1] == 4) {
                black = 0;
                isSwallowbellyRed = true;
            }

            //A locus
            if (genesForText[2] == 1 || genesForText[3] == 1) {
                // agouti
            }
            else if (genesForText[2] == 2 || genesForText[3] == 2) {
            }
            else if (genesForText[2] == 3 || genesForText[3] == 3) {
                // non-agouti
                red = 1;
                agouti = false;
            }
            else if (genesForText[2] == 4 && genesForText[3] == 4 && agouti) {
                //swallowbelly
                swallowbelly = 1;
            }


            if (genesForText[10] != 1 && genesForText[11] != 1) {
                if (genesForText[10] == 2 || genesForText[11] == 2){
                    //spotted
                    spot = 1;
                } else {
                    //roan spotted
                    spot = 2;
                }
            }

            if (genesForText[12] == 6 || genesForText[13] == 6) {
                //dom white
                white = 1;
            }
            else if (genesForText[12] == 1 || genesForText[13] == 1){
                //legacy dom white
                if (genesForText[12] != 3 && genesForText[13] != 3) {
                    white = 1;
                }
            } else if (genesForText[12] == 3 && genesForText[13] == 3) {
            	white = 0;
        	} 
            //patchy
            else if (genesForText[12] == 4 || genesForText[13] == 4) {
                if (genesForText[12] != 5 && genesForText[13] != 5) {
                	//patch seems to override the brindle spotting pattern

                    // i dont think allspots patch does anything different
                    if (black == 3) {
                    }
                	//het allspots patch
                	else if (black == 4) {
                		brindleSkin = 0;
                		black = 1;
                		white = idx_brindlepatch_med;
                	}
                	//plain brindle patch
                	else if (black == 5) {
                    	brindleSkin = 0;
                    	black = 1; 
                    	white = idx_brindlepatch;
                    }
                    else {
                        white = idx_patch;
                    }
                }
            }
            else if (genesForText[12] == 8 || genesForText[13] == 8) {
                //irregular belted
                white = idx_hugebelt;
            }
            else if (genesForText[12] == 2 || genesForText[13] == 2) {
                //belted
                white = idx_belt;
            }
            //roan
            else if (genesForText[12] == 5 || genesForText[13] == 5) {
            	white = 4;
            }
            //white spots 2 aka tux
            else if (genesForText[12] == 10 || genesForText[13] == 10) {
                //produce tuxedo when combined with white points
                white = idx_tuxmin;
                whitePointsFace = 18;
            }
            //white spots Nx aka hereford
            else if (genesForText[12] == 9 && genesForText[13] == 9) {
                //produce hereford when combined with white points
                if (whitePointsFace == 1) {
                    white = idx_whiteheadbelly;
                }
                else {
                    whitePointsFace = 1;
                }
            }

        	//white extension
            if  (genesForText[16] == 1 || genesForText[17] == 1) {
            	whiteExtension = 0;
        	} else if (genesForText[16] == 2 || genesForText[17] == 2) {
        		whiteExtension = 1;
        	} else if (genesForText[16] == 3 && genesForText[17] == 3) {
            	whiteExtension = 2;
        	}
            
            //random brindle
            if (black == 5) {
            	int d = uuidArry[3] % 5;
            	black = 7 + d;
            	brindleSkin = 3 + d;
            }
            //random big brindle
            else if (black == 4) {
            	int d = uuidArry[3] % 4;
            	black = 12 + d;
            	brindleSkin = 8 + d;
            }
            //random big het brindle
            else if (black == 16) {
                int d = uuidArry[3] % 5;
                black = 16 + d;
                brindleSkin = 12 + d;
            }
            //random patch + white points aka spotted
            else if (white == idx_patch && whitePointsFace == 1) {
                whitePointsFace = 0;
                int d = uuidArry[3] % 5;
                white = idx_spottedpatch + d;
            }
            //random patch
            else if (white == idx_patch) {
                int d = uuidArry[3] % 10;
                white = idx_patch + d;
            }
            //random patch brindle
            else if (white == idx_brindlepatch) {
            	int d = uuidArry[3] % 5;
            	white = idx_brindlepatch + d;
            }
            //random patch big brindle
            else if (white == idx_brindlepatch_med) {
            	int d = uuidArry[3] % 4;
            	white = idx_brindlepatch_med + d;
            }
            //random big/irregular belt
            else if (white == idx_hugebelt) {
                int d = uuidArry[3] % 7;
                white = idx_hugebelt + d;
            }
            //random tux
            else if (white == idx_tuxmin) {
                switch (whiteExtension) {
                    case 0 -> {
                        int d3 = uuidArry[3] % 3;
                        int d2 = uuidArry[2] % 3;
                        white = idx_tuxmin + d3;
                        whitePointsFace = 18 + d2;
                    }
                    case 1 -> {
                        int d3 = uuidArry[3] % 6;
                        int d2 = uuidArry[2] % 6;
                        white = idx_tuxmed + d3;
                        whitePointsFace = 21 + d2;
                    }
                    case 2 -> {
                        int d3 = uuidArry[3] % 4;
                        int d2 = uuidArry[2] % 4;
                        white = idx_tuxhigh + d3;
                        whitePointsFace = 27 + d2;
                    }
                }
            }
            else if (white == idx_belt) {
                int d = uuidArry[3] % 6;
                white = idx_belt + d;
            }

            //random hereford
            if (white == idx_whiteheadbelly) {
                int d3 = uuidArry[3] % 5;
                int d2 = uuidArry[2] % 5;
                whitePointsFace = 12 + d2;
                white = idx_whiteheadbelly + d3;
                whitePointsLeg = 5;
            }
            //random white points
            else if ( whitePointsFace == 1 ) {
            	int d2 = uuidArry[2];
           		//int d2 = uuidArry[3];
            	switch (whiteExtension) {
	                case 0 -> {
	                	whitePointsFace = 1 + (d2 % 4);
	                	whitePointsLeg = 1 + (d2 % 2);
	                }
	                case 1 -> {
	                	whitePointsFace = 5 + (d2 % 5);
	                	whitePointsLeg = 2 + (d2 % 2);
	                }
	                case 2 -> {
                        whitePointsFace = 7 + (d2 % 5);
                        whitePointsLeg = 2 + (d2 % 3);
                    }
                }
        	}
            
            if (white == 1) {
            	//pink
                skin = 1;
            } else if ( (genesForText[62] == 2 || genesForText[63] == 2) && (genesForText[0] == 3 && genesForText[1] == 3) ) {
            	//tamworth causes pink skin
            	skin = 1;
            } else if (genesForText[0] == 4  && genesForText[1] == 4) {
            	//rec.red causes brownish skin
                skin = 4;
            } else if (genesForText[12] == 5 && genesForText[13] == 5 ) {
            	//grey skin
                skin = 2;
            } else if (genesForText[4] == 1 || genesForText[5] == 1 || genesForText[8] == 2 || genesForText[9] == 2 || (genesForText[8] == 3 && genesForText[9] == 3)) {
            	//chinchilla, chocolate and silver-brown dilute skin
                skin = 5;
            } else if (black == 1 || black == 2) {
            	//black
                skin = 3;
            } else {
            	//grey
                skin = 2;
                if (brindleSkin == 1) {
                    skin = 1;
                }
            }

            //heterochromia
            int heterochromia = 0;
            if (genesForText[160] == 2 && genesForText[161] == 2) {
                heterochromia += 1;
                if (genesForText[162] == 2 && genesForText[163] == 2) {
                    heterochromia += 1;
                }
            }

            if (heterochromia == 2 || white == 1 || genesForText[12] == 1 || genesForText[13] == 1) {
                eyes = 2;
            }
            else if (heterochromia == 1 ) {
                int d4 = uuidArry[4] % 2;
                eyes = (d4 == 1) ? 3 : 4;
            }

            if (!isBaby()) {
                if ((Character.isLetter(uuidArry[0]) || uuidArry[0] - 48 >= 8)) {
                    //tusks if "male"
                    tusks = true;
                }
            }

            if (genesForText[60] == 2 && genesForText[61] == 2) {
                hooves = 1;
            }

            //black agouti and swallowbelly only show up if there's any agouti
            agoutiBlack = agoutiBlack && agouti;
            
            TextureGrouping parentGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
            TextureGrouping skinGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
            TextureGrouping hairGroup = new TextureGrouping(TexturingType.ALPHA_GROUP); //MASK_GROUP

            addTextureToAnimalTextureGrouping(skinGroup, PIG_TEXTURES_SKINBASE, skin, true);
            addTextureToAnimalTextureGrouping(skinGroup, PIG_TEXTURES_SKINMARKINGS_SPOTS, spot, (spot == 1));

        	//addTextureToAnimalTextureGrouping(skinGroup, PIG_TEXTURES_SKIN_BRINDLEPATCH, white, p -> p > 1);
        	
        	//addTextureToAnimalTextureGrouping(skinGroup, PIG_TEXTURES_SKINMARKINGS_WHITE, white, white != 0);


            addTextureToAnimalTextureGrouping(skinGroup, PIG_TEXTURES_SKINMARKINGS_BERKSHIRE, berk, b -> b != 0);


            parentGroup.addGrouping(skinGroup);

            if (brindleSkin != 0) {
                TextureGrouping blackSkinGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
                TextureGrouping blackSkinAlpha = new TextureGrouping(TexturingType.ALPHA_GROUP);
                addTextureToAnimalTextureGrouping(blackSkinAlpha, PIG_TEXTURES_SKINBRINDLE_SPOTS, brindleSkin, brindleSkin != 0);
                addTextureToAnimalTextureGrouping(blackSkinAlpha, PIG_TEXTURES_SKINBASE, 3, brindleSkin != 0);
                blackSkinGroup.addGrouping(blackSkinAlpha);
                parentGroup.addGrouping(blackSkinGroup);
            }

            if (whitePointsFace != 0 || white != 0) {
                TextureGrouping whiteSkinGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
                TextureGrouping whiteSkinAlpha = new TextureGrouping(TexturingType.ALPHA_GROUP); //MASK_GROUP
                TextureGrouping whiteSkinMask = new TextureGrouping(TexturingType.MERGE_GROUP);
		    	addTextureToAnimalTextureGrouping(whiteSkinMask, PIG_TEXTURES_WHITE_FACE, whitePointsFace, b -> b != 0);
		    	addTextureToAnimalTextureGrouping(whiteSkinMask, PIG_TEXTURES_WHITE_LEG, whitePointsLeg, b -> b != 0);
	        	addTextureToAnimalTextureGrouping(whiteSkinMask, PIG_TEXTURES_SKINMARKINGS_WHITE, white, white != 0);
                whiteSkinAlpha.addGrouping(whiteSkinMask);
		    	TextureGrouping whSkinTex = new TextureGrouping(TexturingType.MERGE_GROUP);
		    	addTextureToAnimalTextureGrouping(whSkinTex, PIG_TEXTURES_SKINBASE, 1, true);
                whiteSkinAlpha.addGrouping(whSkinTex);
                whiteSkinGroup.addGrouping(whiteSkinAlpha);
                parentGroup.addGrouping(whiteSkinGroup);
            }

            if (genesForText[36] != 1 && genesForText[37] != 1) {
                if ((genesForText[34] == 1 || genesForText[35] == 1) && (genesForText[34] != 3 && genesForText[35] != 3)) {
                    //furry
                    coat_alpha = 3;
                } else if (genesForText[34] == 2 || genesForText[35] == 2) {
                    //normal
                	coat_alpha = 2;
                } else {
                    //sparse
                	coat_alpha = 1;
                }

                if (genesForText[38] == 3 || genesForText[39] == 3) {
                    coat_alpha = 4;
                    coat_texture = 2;
                }
                else if (genesForText[38] == 1 || genesForText[39] == 1) {
                	coat_alpha = coat_alpha + 1;
                    coat_texture = 1;
                }
            }
            if (genesForText[36] != 1 && genesForText[37] != 1) {

                TextureGrouping hairAlphaGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
                addTextureToAnimalTextureGrouping(hairAlphaGroup, PIG_TEXTURES_ALPHA, coat_alpha, coat_alpha != 0);
                TextureGrouping hairTexGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
                TextureGrouping redGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
                addTextureToAnimalTextureGrouping(redGroup, TexturingType.APPLY_RED, PIG_TEXTURES_COATRED, red, l -> true);
                hairTexGroup.addGrouping(redGroup);


                if (swallowbelly != 0 && isSwallowbellyRed) {
                    TextureGrouping redSwallowbellyGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
                    //addTextureToAnimalTextureGrouping(hairGroup, TexturingType.APPLY_RGB, PIG_TEXTURES_SWALLOWBELLY[swallowbelly], "sb", Colouration.HSBtoARGB(this.swallowbellyColor[0], this.swallowbellyColor[1], this.swallowbellyColor[2]));
                    addTextureToAnimalTextureGrouping(redSwallowbellyGroup, TexturingType.APPLY_RED, PIG_TEXTURES_SWALLOWBELLY, swallowbelly, l -> l != 0);
                    hairTexGroup.addGrouping(redSwallowbellyGroup);
                }

                if (black != 0 || (swallowbelly != 0 && !isSwallowbellyRed)) {
                    TextureGrouping blackGroup = new TextureGrouping(TexturingType.ALPHA_GROUP);
                    TextureGrouping blackAlphaGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
                    if (swallowbelly != 0 && !isSwallowbellyRed) {
                        addTextureToAnimalTextureGrouping(blackAlphaGroup, PIG_TEXTURES_SWALLOWBELLY, swallowbelly, l -> l != 0);
                    }
                    else {
                        addTextureToAnimalTextureGrouping(blackAlphaGroup, PIG_TEXTURES_COATBLACK, black, l -> l != 0);
                    }
                    TextureGrouping agoutiGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
                    addTextureToAnimalTextureGrouping(agoutiGroup, TexturingType.APPLY_BLACK, PIG_TEXTURES_AGOUTI, agoutiBlack ? 1 : 0, l -> true);
                    addTextureToAnimalTextureGrouping(agoutiGroup, TexturingType.APPLY_BLACK, PIG_TEXTURES_AGOUTI_DARK, agoutiBlack ? 1 : 0, l -> l != 0);
                    addTextureToAnimalTextureGrouping(agoutiGroup, TexturingType.APPLY_BLACK, PIG_TEXTURES_AGOUTI_LIGHT, agoutiBlack ? 1 : 0, l -> l != 0);
                    blackGroup.addGrouping(blackAlphaGroup);
                    blackGroup.addGrouping(agoutiGroup);
                    hairTexGroup.addGrouping(blackGroup);
                }

                if (whitePointsFace != 0 || white != 0) {
                    TextureGrouping whiteGroup = new TextureGrouping(TexturingType.ALPHA_GROUP);
                    TextureGrouping whiteAlphaGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
                    addTextureToAnimalTextureGrouping(whiteAlphaGroup, PIG_TEXTURES_COATWHITE, white, p -> p != 0);
                    addTextureToAnimalTextureGrouping(whiteAlphaGroup, PIG_TEXTURES_WHITE_FACE, whitePointsFace, (whitePointsFace != 0));
                    addTextureToAnimalTextureGrouping(whiteAlphaGroup, PIG_TEXTURES_WHITE_LEG, whitePointsLeg, (whitePointsLeg != 0));
                    addTextureToAnimalTextureGrouping(whiteAlphaGroup, PIG_TEXTURES_SPOT_BERKSHIRE, berk, (berk != 0));
                    TextureGrouping whiteTextureGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
                    addTextureToAnimalTextureGrouping(whiteTextureGroup, PIG_TEXTURES_COATWHITE, 1, p -> p != 0);
                    whiteGroup.addGrouping(whiteAlphaGroup);
                    whiteGroup.addGrouping(whiteTextureGroup);
                    hairTexGroup.addGrouping(whiteGroup);
                }
                TextureGrouping overlayGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
                addTextureToAnimalTextureGrouping(overlayGroup, PIG_TEXTURES_COAT, coat_texture, true);

                hairTexGroup.addGrouping(overlayGroup);

                //addTextureToAnimalTextureGrouping(hairTexGroup, PIG_TEXTURES_SPOT_SPOTS, spot, (spot != 0));
                hairGroup.addGrouping(hairAlphaGroup);
                hairGroup.addGrouping(hairTexGroup);

                /*if (belt != 0) {
                    if (genesForText[12] == 1 || genesForText[13] == 1) {
                        belt = 1;
                    }
                    addTextureToAnimalTextureGrouping(hairGroup, PIG_TEXTURES_SPOT_BELTED, belt, true);
                }*/

                //addTextureToAnimalTextureGrouping(hairGroup, PIG_TEXTURES_WHITEHEAD_BELLY, whitePointsBelly, (whitePointsBelly != 0));

        		parentGroup.addGrouping(hairGroup);
            }

            addTextureToAnimalTextureGrouping(parentGroup, PIG_TEXTURES_EYES, eyes, true);
            addTextureToAnimalTextureGrouping(parentGroup, PIG_TEXTURES_HOOVES, hooves, true);
            if (tusks) {
            	addTextureToAnimalTextureGrouping(parentGroup, PIG_TEXTURES_TUSKS, tusks ? 1 : 0, tusks);	
            }
//            addTextureToAnimal("pigbase.png");
            this.setTextureGrouping(parentGroup);
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
    
    private int getSaturation(int gene) {
        int saturation = 0;
        switch (gene) {
        	// wildtype
            case 1 -> {
                saturation = 0;
            }
            case 2 -> {
            	saturation = 1;
            }
            case 3 -> {
            	saturation = -1;
            }
            case 4 -> {
            	saturation = -2;
            }
            case 5 -> {
            	saturation = -4;
            }
        }
        return saturation;
    }
    
    @Override
    public Colouration getRgb() {
    	boolean flag = (this.colouration.getMelaninColour() == -1 || this.colouration.getPheomelaninColour() == -1) && getSharedGenes()!=null;
        this.colouration = super.getRgb();

        if(this.colouration == null) {
            return null;
        }

        // black: {0.036F, 0.5F, 0.071F};
        // diluteblack: {0.033F, 0.51F, 0.114F};
        // diluteblack difference: {-0.003F, 0.01F, 0.043F};
        // dilutered: {0.086F, 0.534F, 0.69F}
        // dilutered difference: {0.037F, -0.149F, 0.132F}
        /*
        	pheomelanin[0] = 0.061F;
    		pheomelanin[1] = 0.627F;
    		pheomelanin[2] = 0.651F;
         */
        //rufousing caps in both directions. to make sure we dont get purple or green pigs
        float maxRed = 0.025F;
        float maxYellow = 0.102F;
        if (flag) {
        	int[] gene = getSharedGenes().getAutosomalGenes();
            float[] melanin = {0.036F, 0.5F, 0.071F};
            float[] pheomelanin = { 0.049F, 0.683F, 0.558F };
            //0.103F, 0.319F, 0.847F
            boolean agouti = true;

            //dom black
            if (gene[0] == 1 || gene[1] == 1 || gene[0] == 5 || gene[1] == 5) {
                agouti = false;
            }

            if (agouti && (gene[2] == 2 || gene[3] == 2)) {
                    //legacy brown agouti
                    melanin[0] = 0.033F;
                    melanin[1] = 0.517F;
                    melanin[2] = 0.114F;
            }

//            if (gene[12] == 1 && gene[13] == 1) {
//            	pheomelanin[0] = 0.10F;
//            	pheomelanin[1] = 0.02F;
//            	pheomelanin[2] = 0.96F;
//            }
            //brindle
            if (gene[0] == 3 && gene[1] == 3) {
	            // allspots
	            if (gene[64] == 2 && gene[65] == 2 && gene[62] != 2 && gene[63] != 2) {
	            	pheomelanin[0] = 0.086F;
	            	pheomelanin[1] = 0.100F;
	            	pheomelanin[2] = 0.90F;
	            }
	            else if ((gene[64] == 2 || gene[65] == 2) && gene[62] != 2 && gene[63] != 2) {
	            	pheomelanin[0] = 0.086F;
	            	pheomelanin[1] = 0.534F;
	            	pheomelanin[2] = 0.69F;
	            }
            }
            // patch
            /*if (gene[12] == 4 && gene[13] == 4) {
            	pheomelanin[0] = 0.133F;
            	pheomelanin[1] = 0.02F;
            	pheomelanin[2] = 0.961F;
            }*/
            // patch / wildtype
            /*else if ((gene[12] == 4 || gene[13] == 4) && (gene[12] == 3 || gene[13] == 3)) {
            	pheomelanin[0] = 0.086F;
            	pheomelanin[1] = 0.534F;
            	pheomelanin[2] = 0.69F;
            }*/
            
            // chinchilla dilute
            if (gene[4] == 1 || gene[5] == 1) {
            	melanin[0] += 0.003F;
            	melanin[1] += 0.034F;
            	melanin[2] += 0.092F;
            	pheomelanin[0] += 0.034F;
            	pheomelanin[1] += -0.585F;
            	pheomelanin[2] += 0.385F;
            	this.swallowbellyColor[0] += 0.034F;
            	this.swallowbellyColor[1] += -0.425F;
            	this.swallowbellyColor[2] += 0.330F;
            }

            //blonde
            if (gene[158] == 2 && gene[159] == 2) {
                pheomelanin[0] += 0.0548F;
                pheomelanin[1] += -0.364F;
                pheomelanin[2] += 0.289F;
            }
            else if (gene[158] == 2 || gene[159] == 2) {
                pheomelanin[0] += 0.029F;
                pheomelanin[1] += -0.109F;
                pheomelanin[2] += 0.120F;
            }

            // subtle dilute
            if (gene[6] == 2 && gene[7] == 2) {
            	melanin[0] += 0.003F;
            	melanin[1] += 0.015F;
            	melanin[2] += 0.045F;
            	pheomelanin[0] += 0.032F;
            	pheomelanin[1] += -0.129F;
            	pheomelanin[2] += 0.120F;
            	this.swallowbellyColor[0] += 0.037F;
            	this.swallowbellyColor[1] += -0.149F;
            	this.swallowbellyColor[2] += 0.132F;
            }
            
            // silver-brown
            if (gene[8] == 2 && gene[9] == 2) {
            	melanin[0] += 0.003F;
            	melanin[1] += -0.275F;
            	melanin[2] += 0.825F;
            } else if (gene[8] == 2 || gene[9] == 2) {
                melanin[0] += 0.002F;
                melanin[1] += -0.245F;
                melanin[2] += 0.555F;
            //chocolate
            } else if (gene[8] == 3 && gene[9] == 3) {
                melanin[0] += 0.002F;
                melanin[1] += 0.084F;
                melanin[2] += 0.245F;
            }
            
            float saturation = 1 - ((gene[148] + gene[149])/2.0F);

            //wide band increases saturation
            if (gene[164] == 2 && gene[165] == 2) {
                saturation += 1;
            }

            int r = 0;
            for (int i = 120; i < 148; i++) {
                if (gene[i] == 2) {
                    r = i < 134 ? r-1 : r+1;
                }
            }
            
            int darkness = 0;
            for (int i = 150; i < 158; i++) {
                if (gene[i] == 2) {
                	darkness += 1;
                }
            }
            
            if (saturation != 0) {
            	pheomelanin[1] += (0.02F * saturation);
            	this.swallowbellyColor[1] += (0.015F * saturation);
            }
            
            if (r != 0) {
            	pheomelanin[0] += (0.0027F * -r);
            	pheomelanin[2] += (0.008F * -r);
            	this.swallowbellyColor[0] += (0.001F * -r);
            	this.swallowbellyColor[2] += (0.005F * -r);
            }
            
            if (darkness != 0) {
            	melanin[2] -= (0.01F * darkness);
            	pheomelanin[0] -= (0.002F * darkness);
            	pheomelanin[1] += (0.005F * darkness);
            	pheomelanin[2] -= (0.03F * darkness);
            	//this.swallowbellyColor[2] -= (0.01F * darkness);
            }

            //check hue range
            if (pheomelanin[0] > maxYellow) {
                pheomelanin[0] = maxYellow;
            } else if (pheomelanin[0] < maxRed) {
                pheomelanin[0] = maxRed;
            }

            if (agouti) {
                // mix some red into the black for non-wideband agouti
                if (gene[164] != 2 || gene[165] != 2) {
                    for (int i = 0; i <= 2; i++) {
                        melanin[i] = (melanin[i] * 0.9F) + (pheomelanin[i] * 0.1F);
                    }
                }
            }

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
                if (this.swallowbellyColor[i] > 1.0F) {
                	this.swallowbellyColor[i] = 1.0F;
                } else if (this.swallowbellyColor[i] < 0.0F) {
                	this.swallowbellyColor[i] = 0.0F;
                }
            }
            
            this.colouration.setMelaninColour(Colouration.HSBAtoABGR(melanin[0], melanin[1], melanin[2], 0.5F));
            this.colouration.setPheomelaninColour(Colouration.HSBAtoABGR(pheomelanin[0], pheomelanin[1], pheomelanin[2], 0.5F));
        }
        
        return this.colouration;
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
}
