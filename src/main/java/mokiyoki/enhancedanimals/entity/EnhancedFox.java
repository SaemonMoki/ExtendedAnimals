package mokiyoki.enhancedanimals.entity;

import mokiyoki.enhancedanimals.ai.EnhancedEatPlantsGoal;
import mokiyoki.enhancedanimals.ai.general.EnhancedAvoidEntityGoal;
import mokiyoki.enhancedanimals.ai.general.EnhancedBreedGoal;
import mokiyoki.enhancedanimals.ai.general.EnhancedLookAtGoal;
import mokiyoki.enhancedanimals.ai.general.EnhancedLookRandomlyGoal;
import mokiyoki.enhancedanimals.ai.general.EnhancedPanicGoal;
import mokiyoki.enhancedanimals.ai.general.EnhancedTemptGoal;
import mokiyoki.enhancedanimals.ai.general.EnhancedWaterAvoidingRandomWalkingEatingGoal;
import mokiyoki.enhancedanimals.ai.general.SeekShelterGoal;
import mokiyoki.enhancedanimals.ai.general.StayShelteredGoal;
import mokiyoki.enhancedanimals.entity.genetics.FoxGeneticsInitialiser;
import mokiyoki.enhancedanimals.entity.util.Colouration;
import mokiyoki.enhancedanimals.init.FoodSerialiser;
import mokiyoki.enhancedanimals.init.ModBlocks;
import mokiyoki.enhancedanimals.init.ModItems;
import mokiyoki.enhancedanimals.items.DebugGenesBook;
import mokiyoki.enhancedanimals.config.EanimodCommonConfig;
import mokiyoki.enhancedanimals.model.modeldata.AnimalModelData;
import mokiyoki.enhancedanimals.model.modeldata.FoxModelData;
import mokiyoki.enhancedanimals.renderer.texture.TextureGrouping;
import mokiyoki.enhancedanimals.renderer.texture.TexturingType;
import mokiyoki.enhancedanimals.util.Genes;
import mokiyoki.enhancedanimals.util.Reference;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FollowParentGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AirItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.core.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

import static mokiyoki.enhancedanimals.init.FoodSerialiser.foxFoodMap;
import static mokiyoki.enhancedanimals.init.ModEntities.ENHANCED_FOX;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;

public class EnhancedFox extends EnhancedAnimalChestedAbstract {

    //avalible UUID spaces : [ S X X 3 X 5 6 7 - 8 9 10 11 - 12 13 14 15 - 16 17 18 19 - 20 21 22 23 24 25 26 27 28 29 30 31 ]

    // temporarily using sheep as a base to work off of


    private static final EntityDataAccessor<Byte> DYE_COLOUR = SynchedEntityData.<Byte>defineId(EnhancedFox.class, EntityDataSerializers.BYTE);

    // Texture Layers?
    // Base coat - red, gold, cross x2, silver x2
    // white spotting on different body parts? face, legs, body, ears etc.
    // coat texture - thick vs thin
    // Silvering - handle some with code?
    // eyes - blue, shades of orange / reddish, brown, possibly green?
    // paws - pads (pink or dark)

    private static final String[] FOX_TEXTURES_BASECOAT = new String[] {
            "", "foxred.png", "fox_gold1.png", "fox_silver1.png", "fox_cross1.png", "fox_silvercross1.png", "fox_silver2.png", "fox_silver3.png"
    };

 //   private static final String[] FOX_TEXTURES_PATTERN = new String[] {
 //           "", "b_blackbelly_0.png", "b_blackandtan_0.png", "b_english_blue.png",
  //          "b_blackbelly_1.png", "b_blackbelly_2.png", "b_blackbelly_3.png", "b_blackbelly_4.png", "b_blackbelly_5.png",
  //          "b_blackandtan_1.png", "b_blackandtan_2.png",
  //          "b_blue_german.png", "b_light_blue.png", "b_paddington_blue.png", "b_solid.png"
  //  };

  //  private static final String[] FOX_TEXTURES_SKIN = new String[] {
  //          "skin_pink.png"
  //  };

  //  private static final String[] FOX_TEXTURES_HOOVES = new String[] {
  //          "hooves_black.png"
  //  };

 //   private static final String[] FOX_TEXTURES_FUR = new String[] {
  //          "c_fur_wooly.png"
  //  };

 //   private static final String[] FOX_TEXTURES_EYES = new String[] {
 //           "eyes_black.png"
 //   };


    // will come back to this once i have more genetic info - sex linked genes?
    private static final int SEXLINKED_GENES_LENGTH = 2;

    @OnlyIn(Dist.CLIENT)
    public FoxModelData foxModelData;

    private int maxCoatLength;
    private int currentCoatLength;
    private int timeForGrowth = 0;

    private boolean resetTexture = true;

    private String motherUUID = "";

    public EnhancedFox(EntityType<? extends EnhancedFox> entityType, Level worldIn) {
        super(entityType, worldIn, SEXLINKED_GENES_LENGTH, Reference.FOX_AUTOSOMAL_GENES_LENGTH, true);
        this.initilizeAnimalSize();
    }

    private Map<Block, EnhancedEatPlantsGoal.EatValues> createGrazingMap() {
        Map<Block, EnhancedEatPlantsGoal.EatValues> ediblePlants = new HashMap<>();
        ediblePlants.put(Blocks.CARROTS, new EnhancedEatPlantsGoal.EatValues(4, 2, 750));
        ediblePlants.put(Blocks.WHEAT, new EnhancedEatPlantsGoal.EatValues(2, 2, 750));
        ediblePlants.put(Blocks.ALLIUM, new EnhancedEatPlantsGoal.EatValues(8, 3, 750));

        return ediblePlants;
    }

    @Override
    protected FoodSerialiser.AnimalFoodMap getAnimalFoodType() {
        return foxFoodMap();
    }

    @OnlyIn(Dist.CLIENT)
    public static int getDyeRgb(DyeColor dyeColour) {
        return Colouration.getABGRFromBGR(dyeColour.getTextureDiffuseColors());
    }

    /**
     * Gets the wool color of this sheep.
     */
    public DyeColor getFleeceDyeColour() {
        return DyeColor.byId(this.entityData.get(DYE_COLOUR) & 15);
    }

    /**
     * Sets the wool color of this sheep
     */
    public void setFleeceDyeColour(DyeColor colour) {
        byte b0 = this.entityData.get(DYE_COLOUR);
        this.entityData.set(DYE_COLOUR, (byte)(b0 & 240 | colour.getId() & 15));
    }

    // check once model rendering works
    protected float getStandingEyeHeight(Pose poseIn, EntityDimensions sizeIn) {
        return 0.95F * sizeIn.height;
    }


    private EnhancedWaterAvoidingRandomWalkingEatingGoal wanderEatingGoal;

    @Override
    protected void registerGoals() {
        int napmod = this.random.nextInt(1000);
        this.wanderEatingGoal = new EnhancedWaterAvoidingRandomWalkingEatingGoal(this, 1.0D, 7, 0.001F, 120, 2, 50);
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new EnhancedPanicGoal(this, 1.25D));
        this.goalSelector.addGoal(2, new EnhancedAvoidEntityGoal<>(this, Wolf.class, 10.0F, 1.25D, 1.25D, null));
        this.goalSelector.addGoal(3, new EnhancedBreedGoal(this, 1.0D));
        this.goalSelector.addGoal(4, new EnhancedTemptGoal(this, 1.0D, 1.2D, false, Items.AIR));
        this.goalSelector.addGoal(5, new FollowParentGoal(this, 1.1D));
        this.goalSelector.addGoal(6, new StayShelteredGoal(this, 5723, 7000, napmod));
        this.goalSelector.addGoal(7, new SeekShelterGoal(this, 1.0D, 5723, 7000, napmod));
        this.goalSelector.addGoal(8, new EnhancedEatPlantsGoal(this, createGrazingMap()));
        this.goalSelector.addGoal(9, this.wanderEatingGoal);
        this.goalSelector.addGoal(10, new EnhancedLookAtGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(10, new EnhancedLookAtGoal(this, EnhancedSheep.class, 6.0F));
        this.goalSelector.addGoal(11, new EnhancedLookRandomlyGoal(this));
    }

    // let foxes wear blankets? definitely collars, but not chests
    @Override
    public boolean canHaveBlanket() {
        return false;
    }

    protected void customServerAiStep()
    {
        this.animalEatingTimer = this.wanderEatingGoal.getEatingGrassTimer();
        super.customServerAiStep();
    }

 // check once model renders
    @Override
    public EntityDimensions getDimensions(Pose poseIn) {
        return EntityDimensions.scalable(0.8F, 1.2F).scale(this.getScale());
    }

    @Override
    public float getScale() {
        float size = this.getAnimalSize() > 0.0F ? this.getAnimalSize() : 1.0F;
        float newbornSize = 0.325F;
        return this.isGrowing() ? (newbornSize + ((size-newbornSize) * (this.growthAmount()))) : size;
    }

    // used for stuff like entity variations (shrooms and blooms), coat length, etc
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DYE_COLOUR, Byte.valueOf((byte)0));
    }

    protected String getSpecies() {
        return "entity.eanimod.enhanced_fox";
    }

    @Override
    protected int getAdultAge() { return EanimodCommonConfig.COMMON.adultAgeFox.get();}


    @Override
    protected int gestationConfig() {
        return EanimodCommonConfig.COMMON.gestationDaysFox.get();
    }


    public static AttributeSupplier.Builder prepareAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 8.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.23D);
    }

    @Override
    protected void usePlayerItem(Player player, InteractionHand hand, ItemStack itemStack) {
        if (itemStack.is(Items.WATER_BUCKET) && this.getFleeceDyeColour() != DyeColor.WHITE) {
            player.setItemInHand(hand, new ItemStack(Items.BUCKET));
        } else {
            super.usePlayerItem(player, hand, itemStack);
        }
    }

    @Override
    public void aiStep() {
        super.aiStep();

        if (!this.level.isClientSide) {
            if (getEntityStatus().equals(EntityState.MOTHER.toString())) {
                if (hunger <= 24000) {
                    if (--this.timeUntilNextMilk <= 0) {
                        int milk = getMilkAmount();
                        if (milk < (6*this.maxBagSize)) {
                            milk++;
                            setMilkAmount(milk);
                            this.timeUntilNextMilk = this.random.nextInt(this.random.nextInt(8000) + 4000);

                            float milkBagSize = milk / (6*this.maxBagSize);

                            this.setBagSize((milkBagSize*(this.maxBagSize/3.0F))+(this.maxBagSize*2.0F/3.0F));
                        }
                    }
                }

                if (this.timeUntilNextMilk == 0) {
                    this.lactationTimer++;
                } else if (getMilkAmount() <= (6*this.maxBagSize) && this.lactationTimer >= -36000) {
                    this.lactationTimer--;
                }

                if (this.lactationTimer >= 0) {
                    setMilkAmount(-1);
                    setEntityStatus(EntityState.ADULT.toString());
                }
            }
        }

    }

    @Override
    protected void runExtraIdleTimeTick() {
        if (this.hunger <= 36000) {
            this.timeForGrowth++;
        }

        int age = this.getEnhancedAnimalAge();


    }

    @Override
    protected void lethalGenes() {
        int[] gene = this.genetics.getAutosomalGenes();
    //    if(gene[68] == 2 && gene[69] == 2) {
    //        this.remove(RemovalReason.KILLED);
    //    }
    //    if (gene[100] == 2 && gene[101] == 2) {
    //        this.remove(RemovalReason.KILLED);
    //    }
    }

    @Override
    protected int getNumberOfChildren() {
        int[] genes = this.genetics.getAutosomalGenes();
        int kitRange;
        int kitAverage = 1;
        int numberOfKits;

        if (genes[38] == 1 || genes[39] == 1) {
            //1 baby
            kitRange = 1;
        } else if (genes[38] == 3 && genes[39] == 3) {
            // 2-3 babies
            kitRange = 2;
            kitAverage = 2;
        } else if (genes[38] == 2 && genes[39] == 2) {
            //1 to 2 babies
            kitRange = 2;
        } else {
            // 1-3 babies
            kitRange = 3;
            kitAverage = 1;
        }

        if (kitRange != 1) {
            numberOfKits = ThreadLocalRandom.current().nextInt(kitRange) + kitAverage;
        } else {
            numberOfKits = 1;
        }

        return numberOfKits;
    }

    @Override
    protected void incrementHunger() {
        if(this.sleeping) {
            this.hunger = this.hunger + (0.5F*getHungerModifier());
        } else {
            this.hunger = this.hunger + (1.0F*getHungerModifier());
        }
    }

    protected void createAndSpawnEnhancedChild(Level inWorld) {
        EnhancedFox enhancedfox = ENHANCED_FOX.get().create(this.level);
        Genes babyGenes = new Genes(this.genetics).makeChild(this.getOrSetIsFemale(), this.mateGender, this.mateGenetics);
        defaultCreateAndSpawn(enhancedfox, inWorld, babyGenes, -this.getAdultAge());
        this.level.addFreshEntity(enhancedfox);
    }

    @Override
    protected boolean canBePregnant() {
        return true;
    }

    @Override
    protected boolean canLactate() {
        return true;
    }

    protected SoundEvent getAmbientSound() {
        if (isAnimalSleeping()) {
            return null;
        }
        return SoundEvents.FOX_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.FOX_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.FOX_DEATH;
    }

    protected void playStepSound(BlockPos pos, BlockState blockIn) {
        this.playSound(SoundEvents.SHEEP_STEP, 0.15F, 1.0F);
        if (!this.isSilent() && this.getBells()) {
            this.playSound(SoundEvents.NOTE_BLOCK_CHIME, 1.5F, 1.0F);
        }
    }

 // dye and wool color was here


    @Override
    protected boolean shouldDropExperience() { return true; }

    protected void dropCustomDeathLoot(DamageSource source, int looting, boolean recentlyHitIn) {
        super.dropCustomDeathLoot(source, looting, recentlyHitIn);
        float size = this.getAnimalSize();
        int age = this.getEnhancedAnimalAge();
        int meatDrop = this.random.nextInt(4)+1;
        boolean woolDrop = false;
        boolean leatherDrop = false;
        int meatChanceMod;



        if (age < 72000) {
            if (age >= 54000) {
                meatDrop = meatDrop - 1;
                meatChanceMod = (age-54000)/180;
            } else if (age >= 36000) {
                meatDrop = meatDrop - 2;
                meatChanceMod = (age-36000)/180;
            } else if (age >= 18000) {
                meatDrop = meatDrop - 3;
                meatChanceMod = (age-18000)/180;
            } else {
                meatDrop = meatDrop - 4;
                meatChanceMod = age/180;
            }

            int i = this.random.nextInt(100);
            if (meatChanceMod > i) {
                meatDrop++;
            }

            if (woolDrop || leatherDrop) {
                i = this.random.nextInt(100);
                if (age/720 > i) {
                    woolDrop = false;
                    leatherDrop = false;
                }
            }
        }

        if (meatDrop < 0) {
            meatDrop = 0;
        }

        if (this.isOnFire()){
            ItemStack cookedMuttonStack = new ItemStack(Items.COOKED_MUTTON, meatDrop);
            this.spawnAtLocation(cookedMuttonStack);
        }else {
            ItemStack muttonStack = new ItemStack(Items.MUTTON, meatDrop);
            this.spawnAtLocation(muttonStack);

        }
    }



    @OnlyIn(Dist.CLIENT)
    @Override
    public FoxModelData getModelData() {
        return this.foxModelData;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void setModelData(AnimalModelData animalModelData) {
        this.foxModelData = (FoxModelData) animalModelData;
    }

    @OnlyIn(Dist.CLIENT)
    public String getTexture() {
        if (this.enhancedAnimalTextureGrouping == null) {
            this.setTexturePaths();
        } else if (this.resetTexture && !this.isBaby()) {
            this.resetTexture = false;
            this.reloadTextures();
        }

        return getCompiledTextures("enhanced_fox");
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
    @OnlyIn(Dist.CLIENT)
    protected void setTexturePaths() {
        if (this.getSharedGenes() != null) {
            int[] gene = getSharedGenes().getAutosomalGenes();

           // int skin = 0;
           // int fur = 0;
            //int eyes = 0;
            int extension = 0;
            int agouti = 0;
            int basecoat = 0;

            char[] uuidArry = getStringUUID().toCharArray();

            /**
             * MC1R - Extension
             *  1 : E dominant wildtype
             *  2 : e recessive - more silver
             *
             * if (allele1 && allele2) {
             *    //homozygous form
             * }
             * else if (allele1 || allele2) {
             *   //heterozygous form
             * }
             *
             *  gene 0, gene 1
             */

            //fox base coat - extension
            if (gene[0] == 1 && gene[1] == 1) {
                extension = 1;  // EE
            } else if (gene[0] == 1 || gene[1] == 1) {
                extension = 2;  // Ee
            } else {
                extension = 3;  // ee
            }

            /**
             * ASIP - Agouti
             *    1 : A dominant wildtype: red
             *    2 : a recessive - more silver
             *
             *    gene 2, gene 3
             */

            //fox base coat - agouti
            // if gene 2 = 1 (dom A) and gene 3 = 1 (dom A), then its homozygous
            if (gene[2] == 1 && gene[3] == 1 ) {
                agouti = 1;  // AA
            } else if (gene[2] == 2 || gene[3] == 2 ) {   // heterozygous
                agouti = 2;  // Aa
            } else {
                agouti = 3;  // aa
            }

            // 1 = homo Dom, 2 = het, 3 = homo Rec
            if (extension == 1 && agouti == 1 ) {
                basecoat = 1;  // AAEE - red wildtype          foxred.png
            } else if (extension == 1 && agouti == 2) {
                basecoat = 2;  // AAEe - gold                  gold1.png
            } else if (extension == 1 && agouti == 3) {
                basecoat = 3;  // AAee - standard silver       silver2.png
            } else if (extension == 2 && agouti == 1) {
                basecoat = 4;  // AaEE - alaskan cross         cross1.png
            } else if (extension == 2 && agouti == 2) {
                basecoat = 5;  // AaEe - blended cross         silvercross1.png
            } else if (extension == 2 && agouti == 3) {
                basecoat = 6;  // Aaee - sub-standard silver   silver2.png
            } else if (extension == 3 && agouti == 1) {
                basecoat = 7;  // aaEE - alaskan silver        silver1.png
            } else if (extension == 3 && agouti == 2) {
                basecoat = 8;  // aaEe - sub-alaskan silver    silver1.png
            } else {
                basecoat = 9;  // aaee - double silver         silver3.png
            }


            // if (whiteFace != 0 || white != 0 || berk != 0 || whiteSplash != 0) {
            //
            //
            // }


// Texture Grouping
            // apply basecoats as foundation
            // merge, alpha, mask
            // groups: parent (add everything at end), skin, hair, foundation, eyes and paws at end in parent
            // grouping for pigs: skin (merge), hair (merge and mask), white markings, overlay - coat texture,

            TextureGrouping parentGroup = new TextureGrouping(TexturingType.MERGE_GROUP);  // create parent group
            TextureGrouping hairGroup = new TextureGrouping(TexturingType.MASK_GROUP);     //MASK_GROUP  hair

            TextureGrouping foundationGroup = new TextureGrouping(TexturingType.MERGE_GROUP);  // create foundation group
            addTextureToAnimalTextureGrouping(foundationGroup, FOX_TEXTURES_BASECOAT, basecoat, l -> l != 0);  // create basecoat group
            hairGroup.addGrouping(foundationGroup); // foundation added to hair


            parentGroup.addGrouping(hairGroup);  // hair added to parent

            //TextureGrouping detailGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
            //addTextureToAnimalTextureGrouping(detailGroup, FOX_TEXTURES_FUR, fur, null);
            //addTextureToAnimalTextureGrouping(detailGroup, FOX_TEXTURES_SKIN, skin, null);
            //addTextureToAnimalTextureGrouping(detailGroup, "hooves_black.png");
            //addTextureToAnimalTextureGrouping(detailGroup, "eyes_black.png");
            //addTextureToAnimalTextureGrouping(detailGroup,"chests.png");
            //parentGroup.addGrouping(detailGroup);

            this.setTextureGrouping(parentGroup);  // finalizes texture grouping
        }
    }

    @Override
    protected void setAlphaTexturePaths() {}

    @Override
    public Colouration getRgb() {
        boolean flag = (this.colouration.getMelaninColour() == -1 || this.colouration.getPheomelaninColour() == -1) && getSharedGenes()!=null;
        this.colouration = super.getRgb();

        if(this.colouration == null) {
            return null;
        }

        return this.colouration;
    }



    @Override
    public InteractionResult mobInteract(Player entityPlayer, InteractionHand hand) {
        ItemStack itemStack = entityPlayer.getItemInHand(hand);
        Item item = itemStack.getItem();

        if (item == ModItems.ENHANCED_FOX_EGG.get()) {
            return InteractionResult.SUCCESS;
        }

        return super.mobInteract(entityPlayer, hand);
    }



    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putByte("Colour", (byte)this.getFleeceDyeColour().getId());

    }

    /**
     * (abstract) Protected helper method to read subclass entity assets from NBT.
     */
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);



        this.setFleeceDyeColour(DyeColor.byId(compound.getByte("Colour")));

//        configureAI();
        if (!compound.getString("breed").isEmpty()) {

        }
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor inWorld, DifficultyInstance difficulty, MobSpawnType spawnReason, @Nullable SpawnGroupData livingdata, @Nullable CompoundTag itemNbt) {
        return commonInitialSpawnSetup(inWorld, livingdata, getAdultAge(), 60000, 80000, spawnReason);
    }

    @Override
    public void setInitialDefaults() {
        super.setInitialDefaults();
    //    setInitialCoat();

    }

    @Override
    protected Genes createInitialGenes(LevelAccessor world, BlockPos pos, boolean isDomestic) {
        return new FoxGeneticsInitialiser().generateNewGenetics(world, pos, isDomestic);
    }

    @Override
    public Genes createInitialBreedGenes(LevelAccessor world, BlockPos pos, String breed) {
        return new FoxGeneticsInitialiser().generateWithBreed(world, pos, breed);
    }

   // public void setInitialCoat() {

    //}

    @Override
    protected void initializeHealth(EnhancedAnimalAbstract animal, float health) {
//        int[] genes = animal.genetics.getAutosomalGenes();

        health = 8.0F;

        super.initializeHealth(animal, health);
    }

    // edit later for foxes
    @Override
    public void initilizeAnimalSize() {
        int[] genes = this.genetics.getAutosomalGenes();
        float size = 0.4F;

        //(56/57) 1-2 minature [wildtype, minature]
        //(58/59) 1-16 size genes reducer [wildtype, smaller smaller smallest...] adds milk fat [none to most]
        //(60/61) 1-16 size genes adder [wildtype, bigger bigger biggest...]
        //(62/63) 1-3 size genes varient1 [wildtype, smaller, smallest]
        //(64/65) 1-3 size genes varient2 [wildtype, smaller, smallest]

        size = size - (genes[58] - 1)*0.01F;
        size = size - (genes[59] - 1)*0.01F;
        size = size + (genes[60] - 1)*0.0075F;
        size = size + (genes[61] - 1)*0.0075F;

        if (genes[56] == 2 || genes[57] == 2) {
            if (genes[56] == 2 && genes[57] == 2) {
                size = size * 0.8F;
            } else {
                size = size * 0.9F;
            }
        }

        if (genes[62] == 2 || genes[63] == 2) {
            size = size * 0.975F;
        } else if (genes[62] == 3 || genes[63] == 3) {
            size = size * 0.925F;
        } else {
            size = size * 1.025F;
        }

        if (genes[64] == 2 || genes[65] == 2) {
            size = size * 1.05F;
        } else if (genes[64] == 3 || genes[65] == 3) {
            size = size * 1.1F;
        }

        if (size > 0.6F) {
            size = 0.6F;
        }

        size = size + 0.43F;

        // [ 0.52325 - 1.1 ]
        this.setAnimalSize(size);
    }


}