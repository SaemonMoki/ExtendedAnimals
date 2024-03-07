package mokiyoki.enhancedanimals.entity;

import mokiyoki.enhancedanimals.ai.general.*;
import mokiyoki.enhancedanimals.entity.genetics.CatGeneticsInitialiser;
import mokiyoki.enhancedanimals.init.FoodSerialiser;
import mokiyoki.enhancedanimals.config.EanimodCommonConfig;
import mokiyoki.enhancedanimals.init.ModItems;
import mokiyoki.enhancedanimals.model.modeldata.AnimalModelData;
import mokiyoki.enhancedanimals.model.modeldata.CatModelData;
import mokiyoki.enhancedanimals.renderer.texture.TextureGrouping;
import mokiyoki.enhancedanimals.renderer.texture.TexturingType;
import mokiyoki.enhancedanimals.util.Genes;
import mokiyoki.enhancedanimals.util.Reference;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.entity.animal.Fox;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FollowParentGoal;
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
import mokiyoki.enhancedanimals.entity.util.Colouration;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import static mokiyoki.enhancedanimals.init.FoodSerialiser.catFoodMap;
import static mokiyoki.enhancedanimals.init.ModEntities.ENHANCED_CAT;

public class EnhancedCat extends EnhancedAnimalAbstract implements EnhancedAnimalTamableInterface {
    //avalible UUID spaces : [ S X 1 2 3 4 5 6 7 - 8 9 10 11 - 12 13 14 15 - 16 17 18 19 - 20 21 22 23 24 25 26 27 28 29 30 31 ]
    protected static final EntityDataAccessor<Optional<UUID>> DATA_OWNERUUID_ID = SynchedEntityData.defineId(EnhancedCat.class, EntityDataSerializers.OPTIONAL_UUID);
    protected static final EntityDataAccessor<Byte> DATA_FLAGS_ID = SynchedEntityData.defineId(EnhancedCat.class, EntityDataSerializers.BYTE);

    private boolean orderedToSit;

    public Animal getAnimal() {
        return this;
    }
    public boolean isTame() {
        return (this.entityData.get(DATA_FLAGS_ID) & 4) != 0;
    }
    public boolean isOrderedToSit() {
        return this.orderedToSit;
    }

    public void setTame(boolean tameness) {
        byte b0 = this.entityData.get(DATA_FLAGS_ID);
        if (tameness) {
            this.entityData.set(DATA_FLAGS_ID, (byte)(b0 | 4));
        } else {
            this.entityData.set(DATA_FLAGS_ID, (byte)(b0 & -5));
        }

        //this.reassessTameGoals();
    }

    public void setOrderedToSit(boolean sitting) {
        this.orderedToSit = sitting;
    }

    @Nullable
    public UUID getOwnerUUID() {
        return this.entityData.get(DATA_OWNERUUID_ID).orElse((UUID)null);
    }

    public void setOwnerUUID(@Nullable UUID uuid) {
        this.entityData.set(DATA_OWNERUUID_ID, Optional.ofNullable(uuid));
    }

    private static final String[] CAT_TEXTURES_SKINBASE = new String[] {
            "teststripes.png"
    };

    private static final String[] CAT_TEXTURES_BASE = new String[] {
            "solid_base.png"
    };

    private static final String[] CAT_TEXTURES_SKIN = new String[] {
            "skin_pink.png", "skin_orange.png", "skin_black.png", "skin_blue.png"
    };
    private static final String[] CAT_TEXTURES_FUR = new String[] {
            "solid_base.png", "hairless_nose_patch.png"
    };

    private static final String[] CAT_TEXTURES_UNDERBELLY = new String[] {
            "underbelly_skin.png", "underbelly.png", "underbelly_sunshine.png"
    };

    private final int IDX_BLACK_SOLID = 1;
    private final int IDX_BLACK_TORTIE_1 = 2;
    private final int IDX_BLACK_TORTIE_5 = 3;
    private final int IDX_BLACK_TORTIE_6 = 4;
    private static final String[] CAT_TEXTURES_BLACK = new String[] {
            "", "solid_base.png", "tortie_grade1.png", "tortie_grade5.png", "tortie_grade6.png"
    };

    private final int IDX_WHITE_2 = 1;
    private final int IDX_WHITE_3 = IDX_WHITE_2+1;
    private final int IDX_WHITE_4 = IDX_WHITE_3+4;
    private final int IDX_WHITE_5 = IDX_WHITE_4+3;
    private final int IDX_WHITE_6 = IDX_WHITE_5+7;
    private final int IDX_WHITE_7 = IDX_WHITE_6+9;
    private final int IDX_WHITE_8 = IDX_WHITE_7+8;
    private final int IDX_WHITE_9 = IDX_WHITE_8+9;
    private final int IDX_WHITE_10 = IDX_WHITE_9+1;
    private static final String[] CAT_TEXTURES_WHITE = new String[] {
            "", "whitegrade2/white_grade2.png",
            "whitegrade3/grade_3_body_1.png", "whitegrade3/grade_3_body_2.png", "whitegrade3/grade_3_body_3.png", "whitegrade3/grade_3_body_4.png",
            "whitegrade4/grade_4_body_1.png", "whitegrade4/grade_4_body_2.png", "whitegrade4/grade_4_body_3.png",
            "whitegrade5/grade_5_body_1.png", "whitegrade5/grade_5_body_2.png", "whitegrade5/grade_5_body_3.png", "whitegrade5/grade_5_body_4.png", "whitegrade5/grade_5_body_5.png", "whitegrade5/grade_5_body_6.png", "whitegrade5/grade_5_body_7.png",
            "whitegrade6/grade_6_body_1.png", "whitegrade6/grade_6_body_2.png", "whitegrade6/grade_6_body_3.png", "whitegrade6/grade_6_body_4.png", "whitegrade6/grade_6_body_5.png", "whitegrade6/grade_6_body_6.png", "whitegrade6/grade_6_body_7.png", "whitegrade6/grade_6_body_8.png", "whitegrade6/grade_6_body_9.png",
            "whitegrade7/grade_7_body_1.png", "whitegrade7/grade_7_body_2.png", "whitegrade7/grade_7_body_3.png", "whitegrade7/grade_7_body_4.png", "whitegrade7/grade_7_body_5.png", "whitegrade7/grade_7_body_6.png", "whitegrade7/grade_7_body_7.png", "whitegrade7/grade_7_body_8.png",
            "whitegrade8/grade_8_body_1.png", "whitegrade8/grade_8_body_2.png", "whitegrade8/grade_8_body_3.png", "whitegrade8/grade_8_body_4.png", "whitegrade8/grade_8_body_5.png", "whitegrade8/grade_8_body_6.png", "whitegrade8/grade_8_body_7.png", "whitegrade8/grade_8_body_8.png", "whitegrade8/grade_8_body_9.png",
            "whitegrade9/grade_9_body_1.png",
            "solid_base.png"
    };
    private final int IDX_HEADWHITE_2 = 1;
    private final int IDX_HEADWHITE_3 = IDX_HEADWHITE_2 + 1;
    private final int IDX_HEADWHITE_4 = IDX_HEADWHITE_3 + 11;
    private final int IDX_HEADWHITE_5 = IDX_HEADWHITE_4 + 1;
    private final int IDX_HEADWHITE_6 = IDX_HEADWHITE_5 + 5;
    private final int IDX_HEADWHITE_7 = IDX_HEADWHITE_6 + 5;
    private final int IDX_HEADWHITE_8  = IDX_HEADWHITE_7 + 5;
    private final int IDX_HEADWHITE_9  = IDX_HEADWHITE_8;

    private static final String[] CAT_TEXTURES_HEADWHITE = new String[] {
            "", "",
            "whitegrade3/grade_3_head_1.png", "whitegrade3/grade_3_head_2.png", "whitegrade3/grade_3_head_3.png", "whitegrade3/grade_3_head_4.png", "whitegrade3/grade_3_head_5.png", "whitegrade3/grade_3_head_6.png", "whitegrade3/grade_3_head_7.png", "whitegrade3/grade_3_head_8.png", "whitegrade3/grade_3_head_8.png", "whitegrade3/grade_3_head_9.png", "whitegrade3/grade_3_head_10.png", "whitegrade3/grade_3_head_11.png",
            "whitegrade4/grade_4_head_1.png",
            "whitegrade5/grade_5_head_1.png", "whitegrade5/grade_5_head_2.png", "whitegrade5/grade_5_head_3.png", "whitegrade5/grade_5_head_4.png", "whitegrade5/grade_5_head_5.png",
            "whitegrade6/grade_6_head_1.png", "whitegrade6/grade_6_head_2.png", "whitegrade6/grade_6_head_3.png", "whitegrade6/grade_6_head_4.png", "whitegrade6/grade_6_head_5.png",
            "whitegrade7/grade_7_head_1.png", "whitegrade7/grade_7_head_2.png", "whitegrade7/grade_7_head_3.png", "whitegrade7/grade_7_head_4.png", "whitegrade7/grade_7_head_5.png",
            //"whitegrade8/grade_8_head_1.png",
            "whitegrade9/grade_9_head_1.png", "whitegrade9/grade_9_head_2.png", "whitegrade9/grade_9_head_3.png", "whitegrade9/grade_9_head_4.png", "whitegrade9/grade_9_head_5.png", "whitegrade9/grade_9_head_6.png", "whitegrade9/grade_9_head_7.png", "whitegrade9/grade_9_head_8.png", "whitegrade9/grade_9_head_9.png", "whitegrade9/grade_9_head_10.png", "whitegrade9/grade_9_head_11.png",
            "solid_base.png"
    };
    private static final String[] CAT_TEXTURES_NOSE = new String[] {
            "nose.png"
    };

    private static final String[] CAT_TEXTURES_PAWS = new String[] {
            "paws_pink.png", "paws_black.png"
    };

    private static final String[] CAT_TEXTURES_EARS = new String[] {
            "ears.png"
    };

    private static final String[] CAT_TEXTURES_EYE_L = new String[] {
            "eye_left.png"
    };

    private static final String[] CAT_TEXTURES_EYE_R = new String[] {
            "eye_right.png"
    };

    private static final String[] CAT_TEXTURES_FUR_OVERLAY = new String[] {
            "", "hair_short_overlay.png", "coat_sphinx.png"
    };

    private static final String[] CAT_TEXTURES_GLITTER = new String[] {
            "", "glitter2.png"
    };

    private static final String[] CAT_TEXTURES_COLORPOINT_BLACK = new String[] {
            "", "colorpoint.png", "sepia.png", "mocha.png", "colorpoint_sepia.png", "colorpoint_mocha.png", "sepia_mocha.png"
    };
    private static final String[] CAT_TEXTURES_COLORPOINT_RED = new String[] {
            "", "flamepoint.png", "sepia.png", "mocha.png", "colorpoint_sepia.png", "colorpoint_mocha.png", "sepia_mocha.png"
    };
    private static final String[] CAT_TEXTURES_PUPILS = new String[] {
            "pupils.png"
    };
    private static final String[] CAT_TEXTURES_MEALY = new String[] {
            "", "mealy/mealy_1.png", "mealy/mealy_2.png", "mealy/mealy_3.png", "mealy/mealy_4.png", "mealy/mealy_5.png"
    };

    private final int IDX_MACKEREL_TABBY = 1;
    private final int IDX_CLASSIC_TABBY = IDX_MACKEREL_TABBY + 1;
    private final int IDX_HET_SPOTTED_TABBY = IDX_CLASSIC_TABBY + 1;
    private final int IDX_HOMO_SPOTTED_TABBY = IDX_HET_SPOTTED_TABBY + 1;
    private final int IDX_HET_TICKED_TABBY = IDX_HOMO_SPOTTED_TABBY + 1;
    private final int IDX_HOMO_TICKED_TABBY = IDX_HET_TICKED_TABBY + 1;
    private final int IDX_MACKEREL_BM = IDX_HOMO_TICKED_TABBY + 1;
    private final int IDX_CLASSIC_BM = IDX_MACKEREL_BM + 4;
    private final int IDX_HET_SPOTTED_BM = IDX_CLASSIC_BM + 4;
    private final int IDX_SPOTTED_BM = IDX_HET_SPOTTED_BM + 4;
    private final int IDX_HET_TICKED_BM = IDX_SPOTTED_BM + 4;
    private static final String[] CAT_TEXTURES_TABBY = new String[] {
            "", "tabby/mackerel_tabby1.png", "tabby/classic_tabby1.png", "tabby/het_spotted_tabby1.png", "tabby/homo_spotted_tabby1.png",
            "tabby/het_ticked_tabby1.png", "tabby/homo_ticked_tabby1.png",
            "tabby/bm_mackerel_grade1.png", "tabby/bm_mackerel_grade2.png", "tabby/bm_mackerel_grade3.png", "tabby/bm_mackerel_grade4.png",
            "tabby/bm_marble_grade1.png", "tabby/bm_mackerel_grade2.png", "tabby/bm_mackerel_grade3.png", "tabby/bm_mackerel_grade4.png",
            "tabby/bm_broken_grade1.png", "tabby/bm_broken_grade2.png", "tabby/bm_broken_grade3.png", "tabby/bm_broken_grade4.png",
            "tabby/bm_spotted_grade1_1.png", "tabby/bm_spotted_grade2_1.png", "tabby/bm_spotted_grade3_1.png", "tabby/bm_spotted_grade4_1.png",
            "tabby/bm_het_ticked.png"

    };
    private static final String[] CAT_TEXTURES_RED_TABBY_MASK = new String[] {
            "solid_base.png", "flamepoint_tabbymask.png"
    };

    private static final String[] CAT_TEXTURES_TABBY_BASE = new String[] {
            "", "base_agouti.png", "base_agouti_ticked.png"
    };

    private static final String[] CAT_TEXTURES_INHIBITOR_SHADING = new String[] {
            "", "base_inhibitor_shading1.png"
    };
    private static final String[] CAT_TEXTURES_SMOKE_HIGHLIGHTS = new String[] {
            "", "base_smoke1.png"
    };

    private static final String[] CAT_TEXTURES_CHARCOAL = new String[] {
            "", "charcoal.png"
    };
    private static final String[] CAT_TEXTURES_KARPATI = new String[] {
            "", "karpati.png"
    };

    private static final String[] CAT_TEXTURES_CORIN = new String[] {
            "solid_base.png", "sunshine_mask.png", "bimetallic_mask.png"
    };
    private static final int SEXLINKED_GENES_LENGTH = 2;

    @OnlyIn(Dist.CLIENT)
    private CatModelData catModelData;

    public EnhancedCat(EntityType<? extends EnhancedCat> entityType, Level worldIn) {
        super(entityType, worldIn, SEXLINKED_GENES_LENGTH, Reference.CAT_AUTOSOMAL_GENES_LENGTH, true);
        this.initilizeAnimalSize();
    }

    @Override
    protected void registerGoals() {
        int napmod = this.random.nextInt(1200);
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(3, new EnhancedAvoidEntityGoal<>(this, Wolf.class, 10.0F, 2.2D, 2.2D, null));
        this.goalSelector.addGoal(3, new EnhancedAvoidEntityGoal<>(this, Cat.class, 10.0F, 2.2D, 2.2D, null));
        this.goalSelector.addGoal(3, new EnhancedAvoidEntityGoal<>(this, Fox.class, 10.0F, 2.2D, 2.2D, null));
        this.goalSelector.addGoal(3, new EnhancedAvoidEntityGoal<>(this, Monster.class, 4.0F, 2.2D, 2.2D, null));
        this.goalSelector.addGoal(4, new EnhancedBreedGoal(this, 0.8D));
        this.goalSelector.addGoal(5, new EnhancedTemptGoal(this, 1.0D, 1.2D, false, Items.AIR));
        this.goalSelector.addGoal(7, new FollowParentGoal(this, 1.1D));
        this.goalSelector.addGoal(8, new StayShelteredGoal(this, 4000, 7500, napmod));
        this.goalSelector.addGoal(9, new SeekShelterGoal(this, 1.0D, 4000, 7500, napmod));
//        this.goalSelector.addGoal(9, new EnhancedRabbitRaidFarmGoal(this));
//        this.goalSelector.addGoal(5, new EnhancedRabbitEatPlantsGoal(this));
//        this.goalSelector.addGoal(6, new EnhancedWaterAvoidingRandomWalkingGoal(this, 0.6D));
        this.goalSelector.addGoal(12, new EnhancedWanderingGoal(this, 1.0D));
        this.goalSelector.addGoal(13, new EnhancedLookAtGoal(this, Player.class, 10.0F));
        this.goalSelector.addGoal(13, new EnhancedLookAtGoal(this, Monster.class, 10.0F));
        this.goalSelector.addGoal(14, new EnhancedLookRandomlyGoal(this));
    }

    @Override
    protected FoodSerialiser.AnimalFoodMap getAnimalFoodType() {
        return catFoodMap();
    }

    @Override
    public boolean canHaveBridle() {
        return false;
    }

    @Override
    protected void customServerAiStep() {

        LivingEntity livingentity = this.getLastHurtByMob();

        super.customServerAiStep();
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
        this.entityData.define(DATA_FLAGS_ID, (byte)0);
        this.entityData.define(DATA_OWNERUUID_ID, Optional.empty());
    }

    @Override
    protected String getSpecies() {
        return "entity.eanimod.enhanced_cat";
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

        if (item == ModItems.ENHANCED_CAT_EGG.get()) {
            return InteractionResult.SUCCESS;
        }

        if (item == Items.NAME_TAG) {
            itemStack.interactLivingEntity(entityPlayer, this, hand);
            return InteractionResult.SUCCESS;
        }

        if ( (isBreedingItem(itemStack)) && !isTame()) {
            tame(entityPlayer);
        }

        if (isTame() && itemStack.isEmpty()) {
            this.setOrderedToSit(!this.isOrderedToSit());
//            InteractionResult interactionresult = super.mobInteract(entityPlayer, hand);
//            if (!interactionresult.consumesAction()) {
//                this.setOrderedToSit(!this.isOrderedToSit());
//            }
        }

        return super.mobInteract(entityPlayer, hand);
    }

    protected SoundEvent getAmbientSound() {
        if (isAnimalSleeping()) {
            return null;
        }
        return SoundEvents.CAT_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.CAT_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.CAT_DEATH;
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
                .add(Attributes.MOVEMENT_SPEED, 0.15D)
                .add(Attributes.ATTACK_DAMAGE, 2.0D);
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
//        int[] genes = this.genetics.getAutosomalGenes();
        return (ThreadLocalRandom.current().nextInt(3)) + 3;
    }

    protected void createAndSpawnEnhancedChild(Level inWorld) {
        EnhancedCat enhancedcat = ENHANCED_CAT.get().create(this.level);
        Genes babyGenes = new Genes(this.genetics).makeChild(this.getOrSetIsFemale(), this.mateGender, this.mateGenetics);
        defaultCreateAndSpawn(enhancedcat, inWorld, babyGenes, -this.getAdultAge());

        this.level.addFreshEntity(enhancedcat);
    }

    @Override
    protected boolean canBePregnant() {
        return true;
    }

    @Override
    protected boolean canLactate() {
        return false;
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

            return super.hurt(source, amount);
        }
    }


    private int angerAmount() {
        return 400 + this.random.nextInt(400);
    }

    protected float getJumpFactorModifier() {
        return 0.05F;
    }

    @Override
    protected boolean shouldDropExperience() { return true; }

    protected void dropCustomDeathLoot(DamageSource source, int looting, boolean recentlyHitIn) {
        super.dropCustomDeathLoot(source, looting, recentlyHitIn);
    }

    public void lethalGenes(){
        int[] gene = this.genetics.getAutosomalGenes();
        if(gene[26] == 2 && gene[27] == 2) {
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
    public CatModelData getModelData() {
        return this.catModelData;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void setModelData(AnimalModelData animalModelData) {
        this.catModelData = (CatModelData) animalModelData;
    }

    @OnlyIn(Dist.CLIENT)
    public String getTexture() {
        if (this.enhancedAnimalTextures.isEmpty()) {
            this.setTexturePaths();
        } else if (this.reload) {
            this.reload = false;
            this.reloadTextures();
        }

        return getCompiledTextures("enhanced_cat");
    }

    private void clampRGB(float[] color, boolean clampHue) {
        if (clampHue) {
            float minHue = 0.020F;
            float maxHue = 0.101F;
            if (color[0] < minHue) {
                color[0] = minHue;
            }
            else if (color[0] > maxHue) {
                color[0] = maxHue;
            }
        }
        for (int i = 0; i <= 2; i++) {
            if (color[i] > 1.0F) {
                color[i] = 1.0F;
            } else if (color[i] < 0.0F) {
                color[i] = 0.0F;
            }
        }
    }
    private void clampRGB(float[] color) {
        clampRGB(color, true);
    }


    @Override
    @OnlyIn(Dist.CLIENT)
    protected void setTexturePaths() {
        if (this.getSharedGenes() != null) {
            int[] aGenes = getSharedGenes().getAutosomalGenes();
            int[] sGenes = getSharedGenes().getSexlinkedGenes();
            //COLORATION
            float[] melanin = { 0.075F, 0.325F, 0.26F };
            float[] blackTabbyColor = { 0.072F, 0.22F, 0.071F };

            float[] pheomelanin = { 0.078F, 0.623F, 0.798F };
            float[] redTabbyColor = {  0.048F, 0.623F, 0.628F };

            float[] corinTabbyColor = { redTabbyColor[0], redTabbyColor[1], redTabbyColor[2] };

            float[] colorpointBlack = { 0.085F, 0.19F, 0.89F };
            float[] colorpointTabby = { 0.085F, 0.41F, 0.63F };

            float[] colorpointRed = { 0.085F, 0.14F, 0.945F };

            float[] eyeColor = { 0.205F, 0.61F, 0.675F };

            //GENES
            int hairless = 0;
            int tabby = IDX_MACKEREL_TABBY;
            int agoutiBase = 1;
            int noseRGB = 0;
            int paws = 1;
            int black = IDX_BLACK_SOLID;
            int colorpoint = 0;
            int white = 0;
            int headWhite = 0;
            int karpati = 0;
            int whiteExtension = 1; // starting at 1 to be consistent with grades of white.
            boolean agouti = true;
            boolean charcoal = false;
            int inhibitor = 0;
            int corin = 0;
            int underbelly = 1;
            int mealy = 0;
            int coatType = 1;
            int glitter = 1;

            if (aGenes[12] == 2 || aGenes[13] == 2) {
                whiteExtension+=2;
                if (aGenes[12] == aGenes[13]) {
                    whiteExtension+=3;
                }
            }
            for (int i = 14; i < 17; i++) {
                if (aGenes[i] == 2) {
                    whiteExtension++;
                }
            }
            switch (whiteExtension) {
                case 2:
                    white = IDX_WHITE_2;
                    break;
                case 3:
                    white = IDX_WHITE_3;
                    break;
                case 4:
                    white = IDX_WHITE_4;
                    break;
                case 5:
                    white = IDX_WHITE_5;
                    break;
                case 6:
                    white = IDX_WHITE_6;
                    break;
                case 7:
                    white = IDX_WHITE_7;
                    break;
                case 8:
                    white = IDX_WHITE_8;
                    break;
                case 9:
                    white = IDX_WHITE_9;
                    break;
                case 10:
                    white = IDX_WHITE_10;
                    break;
            }
            if (aGenes[22] == 2 || aGenes[23] == 2) {
                //karpati
                karpati = 1;
            }
            // Tabby Types
            if (aGenes[2] == 2 && aGenes[3] == 2) {
                //classic tabby
                tabby = IDX_CLASSIC_TABBY;
            }
            else if (aGenes[10] == 2 || aGenes[11] == 2) {
                // Spotted Tabby
                if (aGenes[10] == aGenes[11]) {
                    //homo spotted
                    tabby = IDX_HOMO_SPOTTED_TABBY;
                }
                else {
                    //het spotted
                    tabby = IDX_HET_SPOTTED_TABBY;
                }
            }
            // Sex Linked Red
            if (sGenes[0] == 2) {
                if (this.getOrSetIsFemale() && sGenes[1] == 1) {
                    //tortie
                    if (whiteExtension < 5) {
                        black = IDX_BLACK_TORTIE_1;
                    }
                    else if (whiteExtension == 5) {
                        black = IDX_BLACK_TORTIE_5;
                    }
                    else {
                        black = IDX_BLACK_TORTIE_6;
                    }
                }
                else {
                    //red
                    black = 0;
                    noseRGB = 2;
                }
            }

            if (aGenes[0] == 2 && aGenes[1] == 2) {
                //non agouti
                agouti = false;
                melanin[2] = 0.071F;
                if (black != 0) {
                    noseRGB = 1;
                }
            }
            else if ((aGenes[0] == 3 || aGenes[1] == 3) && (aGenes[0] == 2 || aGenes[1] == 2)) {
                //het non agouti, het ALC produces charcoal phenotype
                charcoal = true;
                if (black != 0) {
                    noseRGB = 1;
                }
                melanin[1] -= 0.1F;
                blackTabbyColor[1] -= 0.1F;
            }

            if (aGenes[8] == 2 || aGenes[9] == 2) {
                agoutiBase = 2;
                if (aGenes[8] == aGenes[9]) {
                    tabby = IDX_HOMO_TICKED_TABBY;
                }
                else {
                    tabby = IDX_HET_TICKED_TABBY;
                }
            }

            if (agouti) {
                if (aGenes[30] == 2 && aGenes[31] == 2) {
                    // Sunshine
                    corin = 1;
                    underbelly = 2;

                    if (aGenes[0] == 2 || aGenes[1] == 2) {
                        //Dark Sunshine (one copy non-agouti)
                        melanin[0] = pheomelanin[0];
                        melanin[1] = pheomelanin[1] - 0.05F;
                        melanin[2] = pheomelanin[2] - 0.1F;
                        blackTabbyColor[0] = redTabbyColor[0];
                        noseRGB = 0;
                        if (aGenes[24] == 2 || aGenes[25] == 2) {
                            //Bimetallic
                            corin = 2;
                        }
                    }
                    else {
                        //Normal Sunshine
                        melanin[0] = pheomelanin[0];
                        melanin[1] = pheomelanin[1] - 0.1F;
                        melanin[2] = pheomelanin[2] + 0.15F;
                        blackTabbyColor[0] = redTabbyColor[0];
                        noseRGB = 3;
                    }
                    //blackTabbyColor[1] = redTabbyColor[1];
                    //                blackTabbyColor[2] = (blackTabbyColor[2]+redTabbyColor[2])/2F;
                }
            }

            // Inhibitor
            if (aGenes[24] == 2 || aGenes[25] == 2) {
                melanin[1] *= 0.8F;
                melanin[1] -= 0.3F;
                pheomelanin[1] *= 0.8F;
                pheomelanin[1] -= 0.1F;
                if (corin != 0) {
                    corinTabbyColor[0] = pheomelanin[0];
                    corinTabbyColor[1] -= 0.35F;
                    corinTabbyColor[2] += 0.425F;
                    blackTabbyColor[2] += 0.2F;
                }
                if (agouti) {
                    // Silver
                    melanin[2] += 0.475F;
                    //pheomelanin[2] += 0.15F;
                    inhibitor = 1;
                }
                else {
                    // Smoke
                    inhibitor = 1;
                }
            }

            // Colorpoint
            if (aGenes[18] > 1 && aGenes[19] > 1) {
                if (aGenes[18] == 2 && aGenes[19] == 2) {
                    colorpoint = 1;
                }
                else if (aGenes[18] == 3 && aGenes[19] == 3) {
                    colorpoint = 2;
//                    melanin[1] += 0.05F;
                    pheomelanin[2] += 0.15F;
                    pheomelanin[1] -= 0.15F;
                }
                else if (aGenes[18] == 4 && aGenes[19] == 4) {
                    colorpoint = 3;
                }
                else if ((aGenes[18] == 2 && aGenes[19] == 3) || (aGenes[18] == 3 && aGenes[19] == 2)) {
                    colorpoint = 4;
                    if (agouti) {
                        melanin[1] += 0.1F;
                    }
                    pheomelanin[2] += 0.15F;
                    pheomelanin[1] -= 0.15F;
                }
                else if ((aGenes[18] == 2 && aGenes[19] == 4) || (aGenes[18] == 4 && aGenes[19] == 2)) {
                    colorpoint = 5;
                }
                else if ((aGenes[18] == 3 && aGenes[19] == 4) || (aGenes[18] == 4 && aGenes[19] == 3)) {
                    colorpoint = 6;
                    melanin[1] += 0.05F;
                    pheomelanin[2] += 0.15F;
                    pheomelanin[1] -= 0.15F;
                }
                noseRGB = 4;
                if (aGenes[18] == 4 && aGenes[19] == 4) {
                    // Homozygous Mocha
                    noseRGB = 3;
                    melanin[1] += agouti ? 0.03F : 0.15F;
                }

                melanin[0] -= 0.02F;
                melanin[1] += agouti ? -0.1F : 0.4F;
                melanin[2] += 0.05F;
                blackTabbyColor[0] = 0.073F;
                blackTabbyColor[1] += 0.4F;
                blackTabbyColor[2] += 0.05F;
//                redTabbyColor[0] = (redTabbyColor[0] + pheomelanin[0])*0.5F;
//                redTabbyColor[1] = (redTabbyColor[1] + pheomelanin[1])*0.5F;
//                redTabbyColor[2] = (redTabbyColor[2] + pheomelanin[2])*0.5F;
                eyeColor[0] = 0.55F;
                eyeColor[1] -= 0.215F;
                eyeColor[2] += 0.22F;
            }

            //Bengal Modifier
            if (aGenes[20] == 2 || aGenes[21] == 2) {
                int bmQuality = aGenes[20] == aGenes[21] ? 1 : 0;
                bmQuality += aGenes[34] == 2 ? 1 : 0;
                bmQuality += aGenes[35] == 2 ? 1 : 0;
                if (aGenes[20] == aGenes[21]) {
                    switch (tabby) {
                        case IDX_MACKEREL_TABBY:
                            tabby = IDX_MACKEREL_BM + bmQuality;
                            break;
                        case IDX_CLASSIC_TABBY:
                            tabby = IDX_CLASSIC_BM + bmQuality;
                            break;
                        case IDX_HET_SPOTTED_TABBY:
                            tabby = IDX_HET_SPOTTED_BM + bmQuality;
                            break;
                        case IDX_HOMO_SPOTTED_TABBY:
                            tabby = IDX_SPOTTED_BM + bmQuality;
                            break;
                        case IDX_HET_TICKED_TABBY:
                            tabby = IDX_HET_TICKED_BM;
                            break;
                    }
                }
            }

            char[] uuidArry = getStringUUID().toCharArray();
            /*
             * RANDOM TEXTURES
             *
             * UUID Spaces used:
             * [0] - Gender
             * [1] - Body White
             * [2] - Head White
             *
             *
             * [5] - Bobtail
             */

            int randBodyWhite = uuidArry[1];
            int randHeadWhite = uuidArry[2];

            switch (white) {
                case IDX_WHITE_2:
                    break;
                case IDX_WHITE_3:
                    white = IDX_WHITE_3 + (randBodyWhite % 4);
                    headWhite = IDX_HEADWHITE_3 + (randHeadWhite % 11);
                    break;
                case IDX_WHITE_4:
                    white = IDX_WHITE_4 + (randBodyWhite % 3);
                    //headWhite = IDX_HEADWHITE_4 + (randHeadWhite % 1);
                    break;
                case IDX_WHITE_5:
                    white = IDX_WHITE_5 + (randBodyWhite % 7);
                    headWhite = IDX_HEADWHITE_5 + (randHeadWhite % 5);
                    break;
                case IDX_WHITE_6:
                    white = IDX_WHITE_6 + (randBodyWhite % 9);
                    headWhite = IDX_HEADWHITE_6 + (randHeadWhite % 5);
                    break;
                case IDX_WHITE_7:
                    white = IDX_WHITE_7 + (randBodyWhite % 8);
                    headWhite = IDX_HEADWHITE_7 + (randHeadWhite % 5);
                    break;
                case IDX_WHITE_8:
                    white = IDX_WHITE_8 + (randBodyWhite % 9);
                    headWhite = IDX_HEADWHITE_8 + (randHeadWhite % 11);
                    break;
                case IDX_WHITE_9:
                    headWhite = IDX_HEADWHITE_9 + (randHeadWhite % 11);
                    break;
            }

            // Chocolate
            if (aGenes[4]==2 && aGenes[5]==2) {
                melanin[0] -= agouti ? 0.01F : 0.02F;
                melanin[1] += agouti ? 0.1F : 0.25F;
                melanin[2] += 0.12F;
                blackTabbyColor[0] -= 0.02F;
                blackTabbyColor[1] += 0.25F;
                blackTabbyColor[2] += 0.12F;
                if (!agouti) {
                    noseRGB = 4;
                }
            }

            //Dilute
            if (aGenes[28]==2 && aGenes[29]==2) {
                melanin[1] -= 0.35F;
                melanin[2] += 0.16F;
//                colorpointBlack[1] -= 0.35F;
                colorpointBlack[2] += 0.05F;
                blackTabbyColor[1] -= 0.35F;
                blackTabbyColor[2] += 0.16F;
                colorpointTabby[1] -= 0.25F;
                colorpointTabby[2] += 0.12F;
                pheomelanin[0] += 0.010F;
                pheomelanin[1] -= 0.25F;
                pheomelanin[2] += 0.12F;
//                colorpointRed[1] -= 0.25F;
//                colorpointRed[2] += 0.12F;
                redTabbyColor[0] += 0.010F;
                redTabbyColor[1] -= 0.25F;
                redTabbyColor[2] += 0.15F;
//                corinTabbyColor[0] += 0.010F;
                corinTabbyColor[1] -= 0.40F;
                corinTabbyColor[2] += 0.2F;
            }

            if (aGenes[36] == 2 && aGenes[37] == 2) {
                glitter = 1;
                if (agouti) {
                    melanin[2] += 0.25F;
                }
                pheomelanin[2] += 0.25F;
            }

            if (aGenes[32] == 2 && aGenes[33] == 2) {
                //Sphynx
                hairless = 1;
                coatType = 2;
            }

            int eyeHue = 0; // negative = orange, positive = green
            for (int i = 50; i < 60; i++) {
                if (aGenes[i] == 2) {
                    eyeHue--;
                }
                else if (aGenes[i] == 3) {
                    eyeHue++;
                }
            }

            int eyeLightness = 0;
            for (int i = 60; i < 72; i++) {
                if (aGenes[i] == 2) {
                    eyeLightness += i < 66 ? 1 : -1;
                }
            }

            int eyeSaturation = 0;
            for (int i = 72; i < 80; i++) {
                if (aGenes[i] == 2) {
                    eyeSaturation += 1;
                }
            }

            eyeColor[0] += eyeHue*0.0130F;

            eyeColor[1] += eyeSaturation*0.025F;

            eyeColor[1] -= eyeLightness*0.005F;
            eyeColor[2] += eyeLightness*0.0225F;


            float[] blackUnderbelly = { melanin[0], melanin[1]+0.03F, melanin[2]+0.1F };
            if (corin > 0) {
                blackUnderbelly[1] -= 0.25F;
                blackUnderbelly[2] += 0.35F;
            }
            float[] redUnderbelly = { pheomelanin[0], pheomelanin[1]-0.1F, pheomelanin[2]+0.1F };

            clampRGB(blackUnderbelly);
            clampRGB(redUnderbelly);
            clampRGB(melanin);
            clampRGB(pheomelanin);
            clampRGB(eyeColor, false);
            clampRGB(blackTabbyColor);
            clampRGB(redTabbyColor);
            clampRGB(corinTabbyColor);
            clampRGB(colorpointBlack);
            clampRGB(colorpointRed);
            clampRGB(colorpointTabby);

            int melaninRGB = Colouration.HSBtoABGR(melanin[0], melanin[1], melanin[2]);
            int pheomelaninRGB = Colouration.HSBtoABGR(pheomelanin[0], pheomelanin[1], pheomelanin[2]);

            int leftEyeRGB = Colouration.HSBtoARGB(eyeColor[0], eyeColor[1], eyeColor[2]);
            int rightEyeRGB = Colouration.HSBtoARGB(eyeColor[0], eyeColor[1], eyeColor[2]);

            int blackTabbyRGB = Colouration.HSBtoARGB(blackTabbyColor[0], blackTabbyColor[1], blackTabbyColor[2]);
            int blackUnderbellyRGB = Colouration.HSBtoARGB(blackUnderbelly[0], blackUnderbelly[1], blackUnderbelly[2]);

            int redTabbyRGB = Colouration.HSBtoARGB(redTabbyColor[0], redTabbyColor[1], redTabbyColor[2]);
            int redUnderbellyRGB = Colouration.HSBtoARGB(redUnderbelly[0], redUnderbelly[1], redUnderbelly[2]);

            int corinTabbyRGB = Colouration.HSBtoARGB(corinTabbyColor[0], corinTabbyColor[1], corinTabbyColor[2]);

            int colorpointBlackRGB = Colouration.HSBtoARGB(colorpointBlack[0], colorpointBlack[1], colorpointBlack[2]);
            int colorpointRedRGB = Colouration.HSBtoARGB(colorpointRed[0], colorpointRed[1], colorpointRed[2]);
            int colorpointTabbyRGB = Colouration.HSBtoARGB(colorpointTabby[0], colorpointTabby[1], colorpointTabby[2]);

            int whiteRGB = Colouration.HSBtoARGB(0.13F, 0.02F, 0.96F);
            int[] noseColors = {
                    //Brick 0
                    Colouration.HSBtoARGB(0.0F, 0.429F, 0.494F),
                    //Black 1
                    Colouration.HSBtoARGB(0.0F, 0.051F, 0.231F),
                    //Light Brick 2
                    Colouration.HSBtoARGB(0.0F, 0.439F, 0.594F),
                    //Pink 3
                    Colouration.HSBtoARGB(0.987F, 0.404F, 0.861F),
                    //Brown 4
                    Colouration.HSBtoARGB(0.02F, 0.321F, 0.231F),
            };


            this.colouration.setMelaninColour(melaninRGB);
            this.colouration.setLeftEyeColour(leftEyeRGB);
            this.colouration.setRightEyeColour(rightEyeRGB);
            this.colouration.setPheomelaninColour(pheomelaninRGB);

            //TEXTURES
            TextureGrouping parentGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
            TextureGrouping skinGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
            if (black != IDX_BLACK_SOLID) {
                TextureGrouping skinRedGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
                    addTextureToAnimalTextureGrouping(skinRedGroup, CAT_TEXTURES_SKIN, 1, l -> true);
                skinGroup.addGrouping(skinRedGroup);
            }
            if (black != 0) {
                TextureGrouping skinBlackGroup = new TextureGrouping(TexturingType.MASK_GROUP);
                    TextureGrouping skinBlackMaskGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
                        addTextureToAnimalTextureGrouping(skinBlackMaskGroup, CAT_TEXTURES_BLACK, black, true);
                    skinBlackGroup.addGrouping(skinBlackMaskGroup);
                    TextureGrouping skinBlackBaseGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
                        addTextureToAnimalTextureGrouping(skinBlackBaseGroup, CAT_TEXTURES_SKIN, 2, l -> true);
                    skinBlackGroup.addGrouping(skinBlackBaseGroup);
                    if (agouti) {
                        TextureGrouping skinBlackUnderbellyGroup = new TextureGrouping(TexturingType.MASK_GROUP);
                            addTextureToAnimalTextureGrouping(skinBlackUnderbellyGroup, CAT_TEXTURES_UNDERBELLY, 0, l -> true);
                            addTextureToAnimalTextureGrouping(skinBlackUnderbellyGroup, CAT_TEXTURES_SKIN, 0, l -> true);
                        skinBlackGroup.addGrouping(skinBlackUnderbellyGroup);
//                        TextureGrouping skinBlackAgoutiGroup = new TextureGrouping(TexturingType.MASK_GROUP);
//                            addTextureToAnimalTextureGrouping(skinBlackAgoutiGroup, CAT_TEXTURES_TABBY, tabby, l -> true);
//                            addTextureToAnimalTextureGrouping(skinBlackAgoutiGroup, CAT_TEXTURES_SKIN, 2, l -> true);
//                        skinBlackGroup.addGrouping(skinBlackAgoutiGroup);
                    }
                skinGroup.addGrouping(skinBlackGroup);
            }
            if (white != 0) {
                TextureGrouping skinWhiteGroup = new TextureGrouping(TexturingType.MASK_GROUP);
                    addTextureToAnimalTextureGrouping(skinWhiteGroup, CAT_TEXTURES_WHITE, white, l -> l != 0);
                    addTextureToAnimalTextureGrouping(skinWhiteGroup, CAT_TEXTURES_SKIN, 0, l -> true);
                skinGroup.addGrouping(skinWhiteGroup);
            }
            //addTextureToAnimalTextureGrouping(skinGroup, CAT_TEXTURES_SKIN, 0, l -> true);
            parentGroup.addGrouping(skinGroup);

            //addTextureToAnimalTextureGrouping(parentGroup, CAT_TEXTURES_SKINBASE, 0, true);
            TextureGrouping hairGroup = new TextureGrouping(TexturingType.MASK_GROUP);
            TextureGrouping hairAlphaGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
            addTextureToAnimalTextureGrouping(hairAlphaGroup, CAT_TEXTURES_FUR, hairless, l -> true);
            hairGroup.addGrouping(hairAlphaGroup);
//            TextureGrouping hairGlitterGroup = new TextureGrouping(TexturingType.OVERLAY_GROUP);
            TextureGrouping hairTexGroup = new TextureGrouping(TexturingType.MERGE_GROUP);

            //RED LAYER
            if (black != IDX_BLACK_SOLID) {
                TextureGrouping redGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
                TextureGrouping redGlitterGroup = new TextureGrouping(TexturingType.OVERLAY_GROUP);
                TextureGrouping redBaseGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
                addTextureToAnimalTextureGrouping(redBaseGroup, TexturingType.APPLY_RED, CAT_TEXTURES_BASE, 0, l -> true);
                addTextureToAnimalTextureGrouping(redBaseGroup, TexturingType.APPLY_RGB, CAT_TEXTURES_UNDERBELLY[underbelly], "r-ub"+underbelly, redUnderbellyRGB);
                addTextureToAnimalTextureGrouping(redBaseGroup, CAT_TEXTURES_MEALY, mealy, l -> l !=0);
//                addTextureToAnimalTextureGrouping(redBaseGroup, CAT_TEXTURES_TABBY_BASE, agoutiBase, l->l!=0);
                addTextureToAnimalTextureGrouping(redBaseGroup, TexturingType.APPLY_RGB, CAT_TEXTURES_TABBY_BASE[agoutiBase], "r-agb"+agoutiBase, redTabbyRGB);

                if (inhibitor == 1) {
                    addTextureToAnimalTextureGrouping(redBaseGroup, TexturingType.APPLY_RGB, CAT_TEXTURES_INHIBITOR_SHADING[inhibitor], "r-inh", redTabbyRGB);
                }
                redGlitterGroup.addGrouping(redBaseGroup);
                TextureGrouping glitterShineGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
//                addTextureToAnimalTextureGrouping(glitterShineGroup, CAT_TEXTURES_GLITTER, glitter, l -> l != 0);
                redGlitterGroup.addGrouping(glitterShineGroup);
                redGroup.addGrouping(redGlitterGroup);
                TextureGrouping redTabbyGroup = new TextureGrouping(TexturingType.MASK_GROUP);
                addTextureToAnimalTextureGrouping(redTabbyGroup, CAT_TEXTURES_RED_TABBY_MASK, colorpoint != 0 ? 1 : 0, l -> true);
                addTextureToAnimalTextureGrouping(redTabbyGroup, TexturingType.APPLY_RGB, CAT_TEXTURES_TABBY[tabby], "r-tb"+tabby, redTabbyRGB);
                redGroup.addGrouping(redTabbyGroup);
                if (colorpoint != 0) {
                    TextureGrouping colorpointGroup = new TextureGrouping(TexturingType.MASK_GROUP);

                    TextureGrouping colorpointMaskGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
                    addTextureToAnimalTextureGrouping(colorpointMaskGroup, CAT_TEXTURES_COLORPOINT_RED, colorpoint, true);
                    colorpointGroup.addGrouping(colorpointMaskGroup);

                    TextureGrouping colorpointTintGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
                    addTextureToAnimalTextureGrouping(colorpointTintGroup, TexturingType.APPLY_RGB, CAT_TEXTURES_BASE[0], "cp", colorpointRedRGB);
                    colorpointGroup.addGrouping(colorpointTintGroup);

                    redGroup.addGrouping(colorpointGroup);
                }
                hairTexGroup.addGrouping(redGroup);
            }

            //BLACK LAYER
            if (black != 0) {
                TextureGrouping blackGroup = new TextureGrouping(TexturingType.MASK_GROUP);
                TextureGrouping blackMaskGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
                    addTextureToAnimalTextureGrouping(blackMaskGroup, CAT_TEXTURES_BLACK, black, true);
                blackGroup.addGrouping(blackMaskGroup);
                TextureGrouping blackTexGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
                TextureGrouping blackGlitterGroup = new TextureGrouping(TexturingType.OVERLAY_GROUP);
                TextureGrouping blackBaseGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
                addTextureToAnimalTextureGrouping(blackBaseGroup, TexturingType.APPLY_BLACK, CAT_TEXTURES_BASE, 0, l -> true);
                if (agouti) {
                    addTextureToAnimalTextureGrouping(blackBaseGroup, TexturingType.APPLY_RGB, CAT_TEXTURES_UNDERBELLY[underbelly], "b-ub" + underbelly, blackUnderbellyRGB);
                    addTextureToAnimalTextureGrouping(blackBaseGroup, CAT_TEXTURES_MEALY, mealy, l -> l != 0);
                    if (inhibitor == 1) {
                        addTextureToAnimalTextureGrouping(blackBaseGroup, TexturingType.APPLY_RGB, CAT_TEXTURES_INHIBITOR_SHADING[inhibitor], "b-inh", blackTabbyRGB);
                    }
                }
                else {
                    if (inhibitor == 1) {
                        addTextureToAnimalTextureGrouping(blackBaseGroup, CAT_TEXTURES_SMOKE_HIGHLIGHTS, inhibitor, l -> l !=0);
                    }
                }
                blackGlitterGroup.addGrouping(blackBaseGroup);
                TextureGrouping glitterShineGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
//                addTextureToAnimalTextureGrouping(glitterShineGroup, CAT_TEXTURES_GLITTER, glitter, l -> l != 0);
                blackGlitterGroup.addGrouping(glitterShineGroup);
                blackTexGroup.addGrouping(blackGlitterGroup);
                if (agouti) {
                    TextureGrouping agoutiGroup = new TextureGrouping(TexturingType.MASK_GROUP);
                    TextureGrouping agoutiMaskGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
                    addTextureToAnimalTextureGrouping(agoutiMaskGroup, CAT_TEXTURES_TABBY_BASE, agoutiBase, l->l!=0);
                    addTextureToAnimalTextureGrouping(agoutiMaskGroup, CAT_TEXTURES_TABBY, tabby, l->l !=0);

                    addTextureToAnimalTextureGrouping(agoutiMaskGroup, CAT_TEXTURES_CHARCOAL, 1, charcoal);

                    agoutiGroup.addGrouping(agoutiMaskGroup);
                    TextureGrouping agoutiBaseGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
                    if (corin > 0) {
                        addTextureToAnimalTextureGrouping(agoutiBaseGroup, TexturingType.APPLY_RGB, CAT_TEXTURES_BASE[0], "b-tbc", corinTabbyRGB);
                    }
                    if (inhibitor == 1) {
                        addTextureToAnimalTextureGrouping(agoutiBaseGroup, TexturingType.APPLY_RGB, CAT_TEXTURES_INHIBITOR_SHADING[inhibitor], "b-inh", blackTabbyRGB);
                    }
                    addTextureToAnimalTextureGrouping(agoutiBaseGroup, TexturingType.APPLY_RGB, CAT_TEXTURES_CORIN[corin], "b-tb"+corin, blackTabbyRGB);

                    agoutiGroup.addGrouping(agoutiBaseGroup);
                    blackTexGroup.addGrouping(agoutiGroup);
                }

                if (colorpoint != 0) {
                    TextureGrouping colorpointGroup = new TextureGrouping(TexturingType.MASK_GROUP);

                    TextureGrouping colorpointMaskGroup = new TextureGrouping(TexturingType.AVERAGE_GROUP);
                    addTextureToAnimalTextureGrouping(colorpointMaskGroup, CAT_TEXTURES_COLORPOINT_BLACK, colorpoint, true);
                    colorpointGroup.addGrouping(colorpointMaskGroup);

                    TextureGrouping colorpointTintGroup = new TextureGrouping(TexturingType.MERGE_GROUP);

                    addTextureToAnimalTextureGrouping(colorpointTintGroup, TexturingType.APPLY_RGB, CAT_TEXTURES_BASE[0], "cp", colorpointBlackRGB);
                    if (agouti) {
                        addTextureToAnimalTextureGrouping(colorpointTintGroup, TexturingType.APPLY_RGB, CAT_TEXTURES_TABBY[tabby], "cp-tb"+tabby, colorpointTabbyRGB);
                    }
                    colorpointGroup.addGrouping(colorpointTintGroup);
                    blackTexGroup.addGrouping(colorpointGroup);
                }
                blackGroup.addGrouping(blackTexGroup);
                hairTexGroup.addGrouping(blackGroup);
            }

            //WHITE LAYER
            if (white != 0 || karpati != 0) {
                TextureGrouping hairWhiteGroup = new TextureGrouping(TexturingType.MASK_GROUP);
                TextureGrouping whiteMaskGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
                addTextureToAnimalTextureGrouping(whiteMaskGroup, CAT_TEXTURES_WHITE, white, l -> l != 0);
                addTextureToAnimalTextureGrouping(whiteMaskGroup, CAT_TEXTURES_HEADWHITE, headWhite, l -> l != 0);
                addTextureToAnimalTextureGrouping(whiteMaskGroup, CAT_TEXTURES_KARPATI, karpati, l -> l != 0);
                TextureGrouping whiteTextureGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
                addTextureToAnimalTextureGrouping(whiteTextureGroup, TexturingType.APPLY_RGB, CAT_TEXTURES_BASE[0], "w", whiteRGB);
                hairWhiteGroup.addGrouping(whiteMaskGroup);
                hairWhiteGroup.addGrouping(whiteTextureGroup);
                hairTexGroup.addGrouping(hairWhiteGroup);
            }


            TextureGrouping hairOverlayGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
            addTextureToAnimalTextureGrouping(hairOverlayGroup, CAT_TEXTURES_FUR_OVERLAY, coatType, true);
            hairTexGroup.addGrouping(hairOverlayGroup);


            hairGroup.addGrouping(hairTexGroup);
//            hairGlitterGroup.addGrouping(hairTexGroup);
//            TextureGrouping glitterShineGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
//            addTextureToAnimalTextureGrouping(glitterShineGroup, CAT_TEXTURES_GLITTER, glitter, l -> l != 0);
//            hairGlitterGroup.addGrouping(glitterShineGroup);

//            hairGroup.addGrouping(hairGlitterGroup);
            parentGroup.addGrouping(hairGroup);

            TextureGrouping detailGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
            TextureGrouping detailGroupBase = new TextureGrouping(TexturingType.MERGE_GROUP);
            addTextureToAnimalTextureGrouping(detailGroupBase, TexturingType.APPLY_RGB, CAT_TEXTURES_NOSE[0], "nose", noseColors[noseRGB]);
            addTextureToAnimalTextureGrouping(detailGroupBase, CAT_TEXTURES_EARS, 0, true);
            addTextureToAnimalTextureGrouping(detailGroupBase, CAT_TEXTURES_PAWS, paws, true);
            addTextureToAnimalTextureGrouping(detailGroupBase, TexturingType.APPLY_EYE_LEFT_COLOUR, CAT_TEXTURES_EYE_L, 0, l-> true);
            addTextureToAnimalTextureGrouping(detailGroupBase, TexturingType.APPLY_EYE_RIGHT_COLOUR, CAT_TEXTURES_EYE_R, 0, l -> true);
            addTextureToAnimalTextureGrouping(detailGroupBase, CAT_TEXTURES_PUPILS, 0, true);
            addTextureToAnimalTextureGrouping(detailGroupBase, TexturingType.APPLY_RGB, CAT_TEXTURES_NOSE[0], "nose", noseColors[noseRGB]);
            detailGroup.addGrouping(detailGroupBase);
            if (headWhite != 0) {
                TextureGrouping detailGroupWhite = new TextureGrouping(TexturingType.MASK_GROUP);
                addTextureToAnimalTextureGrouping(detailGroupWhite, CAT_TEXTURES_HEADWHITE, headWhite, true);
                addTextureToAnimalTextureGrouping(detailGroupWhite, TexturingType.APPLY_RGB, CAT_TEXTURES_NOSE[0], "nose", noseColors[3]);
                detailGroup.addGrouping(detailGroupWhite);
            }
            parentGroup.addGrouping(detailGroup);

            this.setTextureGrouping(parentGroup);
        }
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    protected void setAlphaTexturePaths() {
    }

    /**
     * (abstract) Protected helper method to read subclass entity assets from NBT.
     */
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        addTamableSaveData(compound);
        compound.putBoolean("Pregnant", this.pregnant);
        compound.putInt("Gestation", this.gestationTimer);
    }

    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        readTamableSaveData(compound);
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor inWorld, DifficultyInstance difficulty, MobSpawnType spawnReason, @Nullable SpawnGroupData livingdata, @Nullable CompoundTag itemNbt) {
        //registerAnimalAttributes();
        return commonInitialSpawnSetup(inWorld, livingdata, 60000, 30000, 80000, spawnReason);
    }


    //    @Override
//    protected int[] createInitialSpawnChildGenes(int[] spawnGenes1, int[] spawnGenes2, int[] mitosis, int[] mateMitosis) {
//        return replaceGenes(getPigletGenes(mitosis, mateMitosis), spawnGenes1);
//    }
    private int[] replaceGenes(int[] resultGenes, int[] groupGenes) {


        return resultGenes;
    }

    @Override
    protected Genes createInitialGenes(LevelAccessor world, BlockPos pos, boolean isDomestic) {
        return new CatGeneticsInitialiser().generateNewGenetics(world, pos, isDomestic);
    }

    @Override
    public Genes createInitialBreedGenes(LevelAccessor world, BlockPos pos, String breed) {
        return new CatGeneticsInitialiser().generateWithBreed(world, pos, breed);
    }

    @Override
    protected void initializeHealth(EnhancedAnimalAbstract animal, float health) {
//        int[] genes = animal.genetics.getAutosomalGenes();

        health = 10.0F;
        super.initializeHealth(animal, health);
    }

    public void initilizeAnimalSize() {
        int[] genes = this.genetics.getAutosomalGenes();
        float size = 1.0F;

        // 0.7F <= size <= 1.5F
        this.setAnimalSize(size);
    }
}
