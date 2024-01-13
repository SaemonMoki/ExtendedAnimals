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

    private static final String[] CAT_TEXTURES_FUR = new String[] {
            "", "solid_base.png"
    };

    private static final String[] CAT_TEXTURES_UNDERBELLY = new String[] {
            "", "underbelly.png"
    };

    private final int IDX_BLACK_SOLID = 1;
    private final int IDX_BLACK_TORTIE_1 = 2;
    private final int IDX_BLACK_TORTIE_5 = 3;
    private final int IDX_BLACK_TORTIE_6 = 4;
    private static final String[] CAT_TEXTURES_BLACK = new String[] {
            "", "solid_base.png", "tortie_grade1.png", "tortie_grade5.png", "tortie_grade6.png"
    };

    private final int IDX_WHITE_2 = 1;
    private final int IDX_WHITE_3 = 2;
    private final int IDX_WHITE_4 = 3;
    private final int IDX_WHITE_5 = 4;
    private final int IDX_WHITE_6 = 5;
    private final int IDX_WHITE_7 = 6;
    private final int IDX_WHITE_8 = 7;
    private final int IDX_WHITE_9 = 8;
    private final int IDX_WHITE_10 = 9;
    private static final String[] CAT_TEXTURES_WHITE = new String[] {
            "", "white_grade2.png", "white_grade3.png", "white_grade4.png", "white_grade5.png", "white_grade6.png", "white_grade7.png", "white_grade8.png", "white_grade9.png", "solid_base.png"
    };

    private static final String[] CAT_TEXTURES_NOSE = new String[] {
            "nose_brick.png", "nose_black.png", "nose_pink.png"
    };

    private static final String[] CAT_TEXTURES_PAWS = new String[] {
            "paws_pink.png", "paws_black.png"
    };

    private static final String[] CAT_TEXTURES_EARS = new String[] {
            "ears.png"
    };

    private static final String[] CAT_TEXTURES_EYES = new String[] {
            "eyes.png"
    };

    private static final String[] CAT_TEXTURES_FUR_OVERLAY = new String[] {
            "", "hair_short_overlay.png",
    };

    private static final String[] CAT_TEXTURES_PUPILS = new String[] {
            "pupils.png"
    };
    private final int IDX_MACKEREL_TABBY = 1;
    private final int IDX_CLASSIC_TABBY = 2;
    private final int IDX_SPOTTED_TABBY = 3;
    private final int IDX_HET_TICKED_TABBY = 4;
    private final int IDX_HOMO_TICKED_TABBY = 5;
    private static final String[] CAT_TEXTURES_TABBY = new String[] {
            "", "mackerel_tabby1.png", "classic_tabby1.png", "spotted_tabby1.png", "het_ticked_tabby1.png", "homo_ticked_tabby1.png"
    };

    private static final String[] CAT_TEXTURES_TICKED = new String[] {
            "", "base_ticked_tabby1.png"
    };

    private static final int SEXLINKED_GENES_LENGTH = 2;

    @OnlyIn(Dist.CLIENT)
    private CatModelData catModelData;

    public EnhancedCat(EntityType<? extends EnhancedCat> entityType, Level worldIn) {
        super(entityType, worldIn, SEXLINKED_GENES_LENGTH, Reference.PIG_AUTOSOMAL_GENES_LENGTH, true);
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

        //TODO lethal genes go here

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

    @Override
    @OnlyIn(Dist.CLIENT)
    protected void setTexturePaths() {
        if (this.getSharedGenes() != null) {
            int[] aGenes = getSharedGenes().getAutosomalGenes();
            int[] sGenes = getSharedGenes().getSexlinkedGenes();
            //COLORATION
            float[] melanin = { 0.075F, 0.325F, 0.316F };
            float[] blackTabbyColor = { 0.072F, 0.22F, 0.080F };

            float[] pheomelanin = { 0.078F, 0.623F, 0.798F };
            float[] redTabbyColor = {  0.048F, 0.623F, 0.628F };

            //GENES
            int tabby = IDX_MACKEREL_TABBY;
            int ticked = 0;
            int nose = 0;
            int paws = 1;
            int black = IDX_BLACK_SOLID;
            int white = 0;
            int whiteExtension = 1; // starting at 1 to be consistent with grades of white.
            boolean agouti = true;

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

            // Tabby Types
            if (aGenes[2] == 2 && aGenes[3] == 2) {
                //classic tabby
                tabby = IDX_CLASSIC_TABBY;
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
                }
            }

            if (aGenes[0] == 2 && aGenes[1] == 2) {
                //non agouti
                agouti = false;
                melanin[2] = 0.071F;
                if (black != 0)
                    nose = 1;
            }

            if (aGenes[8] == 2 || aGenes[9] == 2) {
                ticked = 1;
                if (aGenes[8] == aGenes[9]) {
                    tabby = IDX_HOMO_TICKED_TABBY;
                }
                else {
                    tabby = IDX_HET_TICKED_TABBY;
                }
            }


            float[] blackUnderbelly = { melanin[0], melanin[1]+0.03F, melanin[2]+0.1F };
            float[] redUnderbelly = { pheomelanin[0], pheomelanin[1]-0.1F, pheomelanin[2]+0.1F };

            int melaninRGB = Colouration.HSBtoABGR(melanin[0], melanin[1], melanin[2]);

            int blackTabbyRGB = Colouration.HSBtoARGB(blackTabbyColor[0], blackTabbyColor[1], blackTabbyColor[2]);
            int blackUnderbellyRGB = Colouration.HSBtoARGB(blackUnderbelly[0], blackUnderbelly[1], blackUnderbelly[2]);

            int pheomelaninRGB = Colouration.HSBtoABGR(pheomelanin[0], pheomelanin[1], pheomelanin[2]);

            int redTabbyRGB = Colouration.HSBtoARGB(redTabbyColor[0], redTabbyColor[1], redTabbyColor[2]);
            int redUnderbellyRGB = Colouration.HSBtoARGB(redUnderbelly[0], redUnderbelly[1], redUnderbelly[2]);

            this.colouration.setMelaninColour(melaninRGB);
            this.colouration.setPheomelaninColour(pheomelaninRGB);

            //TEXTURES
            TextureGrouping parentGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
            //addTextureToAnimalTextureGrouping(parentGroup, CAT_TEXTURES_SKINBASE, 0, true);
            TextureGrouping hairGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
            TextureGrouping hairTexGroup = new TextureGrouping(TexturingType.MERGE_GROUP);

            //RED LAYER
            if (black != IDX_BLACK_SOLID) {
                TextureGrouping hairRedGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
                addTextureToAnimalTextureGrouping(hairRedGroup, TexturingType.APPLY_RED, CAT_TEXTURES_FUR, 1, l -> l !=0);
                addTextureToAnimalTextureGrouping(hairRedGroup, TexturingType.APPLY_RGB, CAT_TEXTURES_UNDERBELLY[1], "b-ub", redUnderbellyRGB);
                addTextureToAnimalTextureGrouping(hairRedGroup, TexturingType.APPLY_RGB, CAT_TEXTURES_TABBY[tabby], "r-tb"+tabby, redTabbyRGB);
                if (ticked != 0) {
                    addTextureToAnimalTextureGrouping(hairRedGroup, TexturingType.APPLY_RGB, CAT_TEXTURES_TICKED[ticked], "r-ttb", redTabbyRGB);
                }
                hairTexGroup.addGrouping(hairRedGroup);
            }

            //BLACK LAYER
            if (black != 0) {
                TextureGrouping hairBlackGroup = new TextureGrouping(TexturingType.MASK_GROUP);
                addTextureToAnimalTextureGrouping(hairBlackGroup, CAT_TEXTURES_BLACK, black, true);
                addTextureToAnimalTextureGrouping(hairBlackGroup, TexturingType.APPLY_BLACK, CAT_TEXTURES_FUR, 1, l -> l !=0);
                if (agouti) {
                    addTextureToAnimalTextureGrouping(hairBlackGroup, TexturingType.APPLY_RGB, CAT_TEXTURES_UNDERBELLY[1], "b-ub", blackUnderbellyRGB);
                    addTextureToAnimalTextureGrouping(hairBlackGroup, TexturingType.APPLY_RGB, CAT_TEXTURES_TABBY[tabby], "b-tb"+tabby, blackTabbyRGB);
                    if (ticked != 0) {
                        addTextureToAnimalTextureGrouping(hairBlackGroup, TexturingType.APPLY_RGB, CAT_TEXTURES_TICKED[ticked], "b-ttb", blackTabbyRGB);
                    }
                }
                hairTexGroup.addGrouping(hairBlackGroup);
            }

            //WHITE LAYER
            if (white != 0) {
                TextureGrouping hairWhiteGroup = new TextureGrouping(TexturingType.MASK_GROUP);
                addTextureToAnimalTextureGrouping(hairWhiteGroup, CAT_TEXTURES_WHITE, white, true);
                addTextureToAnimalTextureGrouping(hairWhiteGroup, CAT_TEXTURES_FUR, 1, true);
                hairTexGroup.addGrouping(hairWhiteGroup);
            }


            TextureGrouping hairOverlayGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
            addTextureToAnimalTextureGrouping(hairOverlayGroup, CAT_TEXTURES_FUR_OVERLAY, 1, true);
            hairTexGroup.addGrouping(hairOverlayGroup);


            hairGroup.addGrouping(hairTexGroup);
            parentGroup.addGrouping(hairGroup);

            TextureGrouping detailGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
            addTextureToAnimalTextureGrouping(detailGroup, CAT_TEXTURES_NOSE, nose, true);
            addTextureToAnimalTextureGrouping(detailGroup, CAT_TEXTURES_EARS, 0, true);
            addTextureToAnimalTextureGrouping(detailGroup, CAT_TEXTURES_PAWS, paws, true);
            addTextureToAnimalTextureGrouping(detailGroup, CAT_TEXTURES_EYES, 0, true);
            addTextureToAnimalTextureGrouping(detailGroup, CAT_TEXTURES_PUPILS, 0, true);
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
