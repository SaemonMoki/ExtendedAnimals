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
import mokiyoki.enhancedanimals.entity.genetics.SheepGeneticsInitialiser;
import mokiyoki.enhancedanimals.entity.util.Colouration;
import mokiyoki.enhancedanimals.init.FoodSerialiser;
import mokiyoki.enhancedanimals.init.ModBlocks;
import mokiyoki.enhancedanimals.init.ModItems;
import mokiyoki.enhancedanimals.items.DebugGenesBook;
import mokiyoki.enhancedanimals.config.GeneticAnimalsConfig;
import mokiyoki.enhancedanimals.model.modeldata.AnimalModelData;
import mokiyoki.enhancedanimals.model.modeldata.SheepModelData;
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

import static mokiyoki.enhancedanimals.init.FoodSerialiser.sheepFoodMap;
import static mokiyoki.enhancedanimals.init.ModEntities.ENHANCED_SHEEP;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;


public class EnhancedSheep extends EnhancedAnimalChestedAbstract implements net.minecraftforge.common.IForgeShearable {

    //avalible UUID spaces : [ S X X 3 X 5 6 7 - 8 9 10 11 - 12 13 14 15 - 16 17 18 19 - 20 21 22 23 24 25 26 27 28 29 30 31 ]

    private static final EntityDataAccessor<Integer> COAT_LENGTH = SynchedEntityData.defineId(EnhancedSheep.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Float> BAG_SIZE = SynchedEntityData.defineId(EnhancedSheep.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Byte> DYE_COLOUR = SynchedEntityData.<Byte>defineId(EnhancedSheep.class, EntityDataSerializers.BYTE);
    private static final EntityDataAccessor<Integer> MILK_AMOUNT = SynchedEntityData.defineId(EnhancedSheep.class, EntityDataSerializers.INT);

    private static final String[] SHEEP_TEXTURES_MEALY = new String[] {
            "", "c_mealy.png", "c_mealy_male.png", "c_mealy_female.png"
    };
    private static final String[] SHEEP_TEXTURES_PATTERN = new String[] {
            "", "b_blackbelly_0.png", "b_blackandtan_0.png", "b_english_blue.png",
                "b_blackbelly_1.png", "b_blackbelly_2.png", "b_blackbelly_3.png", "b_blackbelly_4.png", "b_blackbelly_5.png",
                "b_blackandtan_1.png", "b_blackandtan_2.png",
                "b_blue_german.png", "b_light_blue.png", "b_paddington_blue.png", "b_solid.png"
    };

    private static final String[] SHEEP_TEXTURES_GREY = new String[] {
            "", "grey_0.png", "grey_1.png", "grey_2.png", "grey_3.png", "grey_4.png"
    };

    private static final String[] SHEEP_TEXTURES_SPOTS = new String[] {
            "", "spot_pied.png"
    };

    private static final String[] SHEEP_TEXTURES_BLAZE = new String[] {
            "", "c_najdi.png", "c_whiteextrems.png", "c_blaze.png"
    };

    private static final String[] SHEEP_TEXTURES_ROAN = new String[] {
            "", "c_roan.png"
    };
    private static final String[][] SHEEP_TEXTURES_PIGMENTEDHEAD = new String[][] {
            {""},
            {"c_solid_white.png"},
            {"c_afghanpied.png"},
            {"c_turkishpied.png"},
            {"c_turkishspeckled.png"},
            {"c_turkishpigmentedhead.png"},
            {"c_pigmentedhead_0.png", "c_pigmentedhead_1.png", "c_pigmentedhead_2.png", "c_pigmentedhead_3.png", "c_pigmentedhead_4.png", "c_pigmentedhead_5.png", "c_pigmentedhead_6.png", "c_pigmentedhead_7.png", "c_pigmentedhead_8.png"}
    };

    private static final String[] SHEEP_TEXTURES_TICKED = new String[] {
            "", "ticking.png"
    };

    private static final String[] SHEEP_TEXTURES_SKIN = new String[] {
            "skin_pink.png"
    };

    private static final String[] SHEEP_TEXTURES_HOOVES = new String[] {
            "hooves_black.png"
    };

    private static final String[] SHEEP_TEXTURES_FUR = new String[] {
            "c_fur_wooly.png"
    };

    private static final String[] SHEEP_TEXTURES_EYES = new String[] {
            "eyes_black.png"
    };

    private static final int SEXLINKED_GENES_LENGTH = 2;

    @OnlyIn(Dist.CLIENT)
    public SheepModelData sheepModelData;

    protected float maxBagSize;
    private int currentBagSize;
    private int maxCoatLength;
    private int currentCoatLength;
    private int timeForGrowth = 0;

    private boolean resetTexture = true;

    private String motherUUID = "";

    public EnhancedSheep(EntityType<? extends EnhancedSheep> entityType, Level worldIn) {
        super(entityType, worldIn, SEXLINKED_GENES_LENGTH, Reference.SHEEP_AUTOSOMAL_GENES_LENGTH, true);
        this.initilizeAnimalSize();
        this.timeUntilNextMilk = this.random.nextInt(this.random.nextInt(8000) + 4000);
    }

    private Map<Block, EnhancedEatPlantsGoal.EatValues> createGrazingMap() {
        Map<Block, EnhancedEatPlantsGoal.EatValues> ediblePlants = new HashMap<>();
        ediblePlants.put(Blocks.CARROTS, new EnhancedEatPlantsGoal.EatValues(4, 2, 750));
        ediblePlants.put(Blocks.WHEAT, new EnhancedEatPlantsGoal.EatValues(2, 2, 750));
        ediblePlants.put(Blocks.ALLIUM, new EnhancedEatPlantsGoal.EatValues(8, 3, 750));
        ediblePlants.put(Blocks.AZURE_BLUET, new EnhancedEatPlantsGoal.EatValues(3, 2, 750));
        ediblePlants.put(ModBlocks.GROWABLE_AZURE_BLUET.get(), new EnhancedEatPlantsGoal.EatValues(3, 2, 750));
        ediblePlants.put(Blocks.BLUE_ORCHID, new EnhancedEatPlantsGoal.EatValues(7, 2, 375));
        ediblePlants.put(ModBlocks.GROWABLE_BLUE_ORCHID.get(), new EnhancedEatPlantsGoal.EatValues(7, 2, 375));
        ediblePlants.put(Blocks.CORNFLOWER, new EnhancedEatPlantsGoal.EatValues(7, 2, 375));
        ediblePlants.put(ModBlocks.GROWABLE_CORNFLOWER.get(), new EnhancedEatPlantsGoal.EatValues(7, 2, 375));
        ediblePlants.put(Blocks.DANDELION, new EnhancedEatPlantsGoal.EatValues(3, 2, 750));
        ediblePlants.put(ModBlocks.GROWABLE_DANDELION.get(), new EnhancedEatPlantsGoal.EatValues(3, 2, 750));
        ediblePlants.put(Blocks.OXEYE_DAISY, new EnhancedEatPlantsGoal.EatValues(7, 3, 750));
        ediblePlants.put(ModBlocks.GROWABLE_OXEYE_DAISY.get(), new EnhancedEatPlantsGoal.EatValues(7, 2, 750));
        ediblePlants.put(Blocks.GRASS, new EnhancedEatPlantsGoal.EatValues(1, 1, 750));
        ediblePlants.put(ModBlocks.GROWABLE_GRASS.get(), new EnhancedEatPlantsGoal.EatValues(1, 1, 750));
        ediblePlants.put(Blocks.TALL_GRASS, new EnhancedEatPlantsGoal.EatValues(1, 1, 750));
        ediblePlants.put(ModBlocks.GROWABLE_TALL_GRASS.get(), new EnhancedEatPlantsGoal.EatValues(1, 1, 750));
        ediblePlants.put(Blocks.FERN, new EnhancedEatPlantsGoal.EatValues(1, 1, 750));
        ediblePlants.put(ModBlocks.GROWABLE_FERN.get(), new EnhancedEatPlantsGoal.EatValues(1, 1, 750));
        ediblePlants.put(Blocks.LARGE_FERN, new EnhancedEatPlantsGoal.EatValues(1, 1, 750));
        ediblePlants.put(ModBlocks.GROWABLE_LARGE_FERN.get(), new EnhancedEatPlantsGoal.EatValues(1, 1, 750));

        return ediblePlants;
    }

    @Override
    protected FoodSerialiser.AnimalFoodMap getAnimalFoodType() {
        return sheepFoodMap();
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

    protected float getStandingEyeHeight(Pose poseIn, EntityDimensions sizeIn) {
        return 0.95F * sizeIn.height;
    }

    private int timeUntilNextMilk;
    private EnhancedWaterAvoidingRandomWalkingEatingGoal wanderEatingGoal;

    @Override
    protected void registerGoals() {
        //Todo add the temperamants
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

    //TODO put new sheep behaviour here

    @Override
    public boolean canHaveBlanket() {
        return false;
    }

    protected void customServerAiStep()
    {
        this.animalEatingTimer = this.wanderEatingGoal.getEatingGrassTimer();
        super.customServerAiStep();
    }

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

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(COAT_LENGTH, 0);
        this.entityData.define(DYE_COLOUR, Byte.valueOf((byte)0));
        this.entityData.define(BAG_SIZE, 0.0F);
        this.entityData.define(MILK_AMOUNT, 0);
    }

    protected String getSpecies() {
        return "entity.eanimod.enhanced_sheep";
    }

    @Override
    protected int getAdultAge() { return GeneticAnimalsConfig.COMMON.adultAgeSheep.get();}

    //returns how grown the horns are
    public float hornGrowthAmount() {
        int age = this.getEnhancedAnimalAge();
        int hornFullSizedAge = this.getFullSizeAge() * 2;
        return age > hornFullSizedAge ? 1.0F : age/(float)hornFullSizedAge;
    }

    @Override
    protected int gestationConfig() {
        return GeneticAnimalsConfig.COMMON.gestationDaysSheep.get();
    }

    private void setCoatLength(int coatLength) {
        this.entityData.set(COAT_LENGTH, coatLength);
    }

    public int getCoatLength() {
        return this.entityData.get(COAT_LENGTH);
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

        if (!this.level().isClientSide) {
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

        int maxcoat = age >= this.getAdultAge() ? this.maxCoatLength : (int)(this.maxCoatLength*(((float)age/(float)this.getAdultAge())));

        if (maxcoat > 0) {
            int[] genes = this.genetics.getAutosomalGenes();
            //first check is for self shearing sheep
            if (this.currentCoatLength == maxcoat && (genes[46] == 1 || genes[47] == 1) && this.timeForGrowth >= 24000) {
                this.timeForGrowth = 0;
                this.currentCoatLength = maxcoat >= 2 ? this.random.nextInt(maxcoat/2) : 0;
            } else if (this.timeForGrowth >= (24000 / maxcoat)) {
                this.timeForGrowth = 0;
                if (maxcoat > this.currentCoatLength) {
                    this.currentCoatLength++;
                    setCoatLength(this.currentCoatLength);
                }
            }

        }
    }

    @Override
    protected void lethalGenes() {
        int[] gene = this.genetics.getAutosomalGenes();
        if(gene[68] == 2 && gene[69] == 2) {
            this.remove(RemovalReason.KILLED);
        }
        if (gene[100] == 2 && gene[101] == 2) {
            this.remove(RemovalReason.KILLED);
        }
    }

    @Override
    protected int getNumberOfChildren() {
        int[] genes = this.genetics.getAutosomalGenes();
        int lambRange;
        int lambAverage = 1;
        int numberOfLambs;

        if (genes[38] == 1 || genes[39] == 1) {
            //1 baby
            lambRange = 1;
        } else if (genes[38] == 3 && genes[39] == 3) {
            // 2-3 babies
            lambRange = 2;
            lambAverage = 2;
        } else if (genes[38] == 2 && genes[39] == 2) {
            //1 to 2 babies
            lambRange = 2;
        } else {
            // 1-3 babies
            lambRange = 3;
            lambAverage = 1;
        }

        if (lambRange != 1) {
            numberOfLambs = ThreadLocalRandom.current().nextInt(lambRange) + lambAverage;
        } else {
            numberOfLambs = 1;
        }

        return numberOfLambs;
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
        EnhancedSheep enhancedsheep = ENHANCED_SHEEP.get().create(this.level());
        Genes babyGenes = new Genes(this.genetics).makeChild(this.getOrSetIsFemale(), this.mateGender, this.mateGenetics);
        defaultCreateAndSpawn(enhancedsheep, inWorld, babyGenes, -this.getAdultAge());
        enhancedsheep.setMaxCoatLength();
        enhancedsheep.currentCoatLength = 0;
        enhancedsheep.setCoatLength(0);
        this.level().addFreshEntity(enhancedsheep);
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
        return SoundEvents.SHEEP_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.SHEEP_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.SHEEP_DEATH;
    }

    protected void playStepSound(BlockPos pos, BlockState blockIn) {
        this.playSound(SoundEvents.SHEEP_STEP, 0.15F, 1.0F);
        if (!this.isSilent() && this.getBells()) {
            this.playSound(SoundEvents.NOTE_BLOCK_CHIME.get(), 1.5F, 1.0F);
        }
    }

    public void ate() {
        this.setSheared(false);
        if (!this.isBaby() && (maxCoatLength > currentCoatLength)){
            this.currentCoatLength ++ ;
            setCoatLength(currentCoatLength);
        }

    }

    @Override
    public boolean isShearable(ItemStack item, Level world, BlockPos pos) {
        return !this.level().isClientSide && currentCoatLength >= 1;
    }

    @Override
    public java.util.List<ItemStack> onSheared(Player player, ItemStack item, Level world, BlockPos pos, int fortune) {
        java.util.List<ItemStack> ret = new java.util.ArrayList<>();
        if (!this.level().isClientSide) {
            int woolCount = (int)(this.currentCoatLength * 0.2F);
            float bonusWoolChance = (this.currentCoatLength - woolCount) * 0.2F;
            woolCount += bonusWoolChance >= this.random.nextFloat() ? 1 : 0;

            for (int i = 0; i < woolCount; i++){
                ret.add(new ItemStack(getWoolBlockFromColour(getWoolColour())));
            }
        }
        this.currentCoatLength = 0;
        setCoatLength(this.currentCoatLength);
        return ret;
    }

    private DyeColor getWoolColour() {
        DyeColor geneticColor = getGeneticWoolColour();
        if (geneticColor == DyeColor.BLACK) {
            return DyeColor.BLACK;
        } else if (geneticColor == DyeColor.WHITE) {
            return getFleeceDyeColour();
        }
        switch (getFleeceDyeColour()) {
            case WHITE -> {return getGeneticWoolColour();}
            case ORANGE -> {
                switch (geneticColor) {
                    case RED -> {return DyeColor.RED;}
                    case BROWN, GRAY -> {return DyeColor.BROWN;}
                }
                return DyeColor.ORANGE;
            }
            case MAGENTA -> {
                switch (geneticColor) {
                    case RED, ORANGE -> {return DyeColor.RED;}
                    case BROWN, GRAY -> {return DyeColor.BROWN;}
                }
                return DyeColor.MAGENTA;
            }
            case LIGHT_BLUE -> {
                switch (geneticColor) {
                    case YELLOW -> {return DyeColor.CYAN;}
                    case ORANGE -> {return DyeColor.LIGHT_GRAY;}
                    case BROWN, GRAY, RED -> {return DyeColor.GRAY;}
                }
                return DyeColor.LIGHT_BLUE;
            }
            case YELLOW -> {
                switch (geneticColor) {
                    case ORANGE, RED -> {return DyeColor.ORANGE;}
                    case BROWN, LIGHT_GRAY, GRAY -> {return DyeColor.BROWN;}
                }
                return DyeColor.YELLOW;
            }
            case LIME -> {
                switch (geneticColor) {
                    case ORANGE, RED -> {return DyeColor.ORANGE;}
                    case BROWN, LIGHT_GRAY, GRAY -> {return DyeColor.BROWN;}
                }
                return DyeColor.LIME;
            }
            case PINK -> {
                switch (geneticColor) {
                    case YELLOW -> {return DyeColor.ORANGE;}
                    case ORANGE, RED -> {return DyeColor.RED;}
                    case BROWN, LIGHT_GRAY, GRAY -> {return DyeColor.BROWN;}
                }
                return DyeColor.PINK;
            }
            case GRAY -> {
                switch (geneticColor) {
                    case YELLOW, ORANGE, RED, BROWN -> {return DyeColor.BROWN;}
                    case GRAY -> {return DyeColor.BLACK;}
                }
                return DyeColor.GRAY;
            }
            case LIGHT_GRAY -> {
                switch (geneticColor) {
                    case YELLOW, ORANGE, RED, BROWN -> {return DyeColor.BROWN;}
                    case GRAY -> {return DyeColor.GRAY;}
                }
                return DyeColor.LIGHT_GRAY;
            }
            case CYAN -> {
                switch (geneticColor) {
                    case YELLOW -> {return DyeColor.LIME;}
                    case ORANGE, RED, BROWN -> {return DyeColor.BROWN;}
                    case LIGHT_GRAY, GRAY -> {return DyeColor.BLUE;}
                }
                return DyeColor.CYAN;
            }
            case PURPLE -> {
                switch (geneticColor) {
                    case YELLOW -> {return DyeColor.GRAY;}
                    case ORANGE, RED, LIGHT_GRAY -> {return DyeColor.BROWN;}
                    case BROWN, GRAY -> {return DyeColor.BLACK;}
                }
                return DyeColor.PURPLE;
            }
            case BLUE -> {
                switch (geneticColor) {
                    case YELLOW, ORANGE, RED -> {return DyeColor.GRAY;}
                    case BROWN, GRAY -> {return DyeColor.BLACK;}
                }
                return DyeColor.BLUE;
            }
            case BROWN -> {return DyeColor.BROWN;}
            case GREEN -> {
                switch (geneticColor) {
                    case YELLOW -> {return DyeColor.CYAN;}
                    case ORANGE, RED -> {return DyeColor.GRAY;}
                    case BROWN, GRAY -> {return DyeColor.BLACK;}
                }
                return DyeColor.GREEN;
            }
            case RED -> {
                switch (geneticColor) {
                    case BROWN, GRAY -> {return DyeColor.BROWN;}
                }
                return DyeColor.RED;
            }
            case BLACK -> {return DyeColor.BLACK;}
        }
        return DyeColor.BLACK;
    }

    private DyeColor getGeneticWoolColour() {
        if (this.genetics==null) {
            return DyeColor.RED;
        }
        int[] gene = this.genetics.getAutosomalGenes();
        float spotChance = 0.0F;

        if (gene[68] != 1 || gene[69] != 1) {
            if (gene[68] == 2 || gene[69] == 2) {
                return DyeColor.WHITE;
            }
            if (gene[68] == 1 || gene[69] == 1) {
                if (gene[68] == 3 || gene[69] == 3) {
                    spotChance = 0.4F;
                }
            } else if (gene[68] == gene[69]) {
                return DyeColor.WHITE;
            }
        }

        if (gene[8] == 2 && gene[9] == 2) {
            spotChance = spotChance + ((1.0F-spotChance) * 0.4F);
        }

        if (spotChance > this.random.nextFloat()) {
            return DyeColor.WHITE;
        }

        boolean isSolidBlack = false;
        boolean red = gene[4] == 3 && gene[5] == 3;
        boolean roan = gene[100] == 2 || gene[101] == 2;
        boolean chocolate = !(gene[2] == 1 || gene[3] == 1);
        float agoutiShade = 0.0F;
        if (gene[4] == 1 || gene[5] == 1) {
            if (roan) {
                return DyeColor.LIGHT_GRAY;
            } else {
                return chocolate ? DyeColor.BROWN : DyeColor.BLACK;
            }
        } else {
            if (gene[0] == 6 && gene[1] == 6) {
                isSolidBlack = true;
            } else {
                if (gene[0] == 1 || gene[1] == 1) {
                    agoutiShade = 0.0F;
                } else {
                    if (gene[0] == 2 || gene[1] == 2) {
                        return chocolate || red || roan ? DyeColor.WHITE : DyeColor.LIGHT_GRAY;
                    }
                    if (gene[0] != 6) {
                        agoutiShade = gene[1] == 6 ? getAgoutiShade(gene[0]) : (getAgoutiShade(gene[0]) + getAgoutiShade(gene[1])) * 0.5F;
                    } else {
                        agoutiShade = getAgoutiShade(gene[1]);
                    }

                    if (agoutiShade == 1.0F) {
                        isSolidBlack = true;
                    }
                }
            }

            if (!isSolidBlack) {
                float[] redRGB = getBasePheomelanin(gene[72]);
                float[] redRGBAddition = getBasePheomelanin(gene[73]);

                redRGB[0] = (redRGB[0] + redRGBAddition[0])*0.5F;
                redRGB[1] = (redRGB[1] + redRGBAddition[1])*0.5F;
                redRGB[2] = (redRGB[2] + redRGBAddition[2])*0.5F;

                int r = 0;
                for (int i = 74; i < 90; i++) {
                    if (gene[i] == 2) {
                        r = i < 82 ? r-1 : r+1;
                    }
                }
                if (r != 0) {
                    if (r < 0) {
                        redRGB[0] = (((redRGB[0] * (10+r))) + (0.025F * -r)) * 0.1F;
                        if (redRGB[2] > 0.5F) {
                            redRGB[0] = redRGB[0] + (0.035F * (1.0F - redRGB[2]));
                        }
                    } else {
                        redRGB[0] = (((redRGB[0] * (10-r))) + (0.09F * r)) * 0.1F;
                    }
                }


                float darkness = agoutiShade + ((1.0F-agoutiShade) * redRGB[2]);
                if (darkness < 0.875F || redRGB[1] > 0.16F) {
                    if (redRGB[1] < 0.1F) {
                        if (darkness < 0.05F) {
                            return DyeColor.WHITE;
                        } else if (roan || darkness < 0.3F) {
                            return DyeColor.LIGHT_GRAY;
                        } else {
                            return chocolate ? DyeColor.BROWN : DyeColor.GRAY;
                        }
                    } else if (redRGB[1] < 0.5F) {
                        if (darkness < 0.05F) {
                            return DyeColor.WHITE;
                        } else if (roan || redRGB[1] < 0.2F) {
                            return DyeColor.LIGHT_GRAY;
                        } else {
                            if (darkness < 0.3F) {
                                return chocolate ? DyeColor.BROWN : DyeColor.LIGHT_GRAY;
                            } else {
                                return DyeColor.BROWN;
                            }
                        }
                    } else {
                        if (roan) {
                            return DyeColor.LIGHT_GRAY;
                        }
                        if (darkness < 0.1F) {
                            return DyeColor.ORANGE;
                        } else {
                            return DyeColor.BROWN;
                        }
                    }
                } else {
                    return DyeColor.WHITE;
                }
            }

            if (roan) {
                return DyeColor.LIGHT_GRAY;
            } else if (gene[4] == 3 && gene[5] == 3) {
                return chocolate ? DyeColor.ORANGE : DyeColor.BROWN;
            } else {
                return chocolate ? DyeColor.BROWN : DyeColor.BLACK;
            }
        }
    }

    private float getAgoutiShade(int gene) {
        switch (gene) {
            case 2 -> {return 0.25F;}
            case 3,7,8,9,10,11 -> {return 0.0F;}
            case 4,12,16 -> {return 1.0F;}
            case 5,14 -> {return 0.75F;}
            case 13,15 -> {return 0.5F;}
        }
        return -1.0F;
    }

    private Block getWoolBlockFromColour(DyeColor color) {
        switch (color) {
            case WHITE -> {return Blocks.WHITE_WOOL;}
            case ORANGE -> {return Blocks.ORANGE_WOOL;}
            case MAGENTA -> {return Blocks.MAGENTA_WOOL;}
            case LIGHT_BLUE -> {return Blocks.LIGHT_BLUE_WOOL;}
            case YELLOW -> {return Blocks.YELLOW_WOOL;}
            case LIME -> {return Blocks.LIME_WOOL;}
            case PINK -> {return Blocks.PINK_WOOL;}
            case GRAY -> {return Blocks.GRAY_WOOL;}
            case LIGHT_GRAY -> {return Blocks.LIGHT_GRAY_WOOL;}
            case CYAN -> {return Blocks.CYAN_WOOL;}
            case PURPLE -> {return Blocks.PURPLE_WOOL;}
            case BLUE -> {return Blocks.BLUE_WOOL;}
            case BROWN -> {return Blocks.BROWN_WOOL;}
            case GREEN -> {return Blocks.GREEN_WOOL;}
            case RED -> {return Blocks.RED_WOOL;}
            case BLACK -> {return Blocks.BLACK_WOOL;}
        }
        return Blocks.WHITE_WOOL;
    }

    @Override
    public boolean shouldDropExperience() { return true; }

    protected void dropCustomDeathLoot(DamageSource source, int looting, boolean recentlyHitIn) {
        super.dropCustomDeathLoot(source, looting, recentlyHitIn);
        float size = this.getAnimalSize();
        int age = this.getEnhancedAnimalAge();
        int meatDrop = this.random.nextInt(4)+1;
        boolean woolDrop = false;
        boolean leatherDrop = false;
        int meatChanceMod;

        if (this.currentCoatLength >= 5) {
            woolDrop = true;
        } else if (this.currentCoatLength == this.maxCoatLength && this.maxCoatLength >= 1) {
            if (this.currentCoatLength >= 5) {
                    woolDrop = true;
            } else if (this.currentCoatLength == 1) {
                int i = this.random.nextInt(5);
                if (i>3){
                    woolDrop = true;
                }
            } else if (this.currentCoatLength == 2) {
                int i = this.random.nextInt(5);
                if (i>2){
                    woolDrop = true;
                }
            } else if (this.currentCoatLength == 3) {
                int i = this.random.nextInt(5);
                if (i>1){
                    woolDrop = true;
                }
            } else {
                int i = this.random.nextInt(5);
                if (i>0) {
                    woolDrop = true;
                }
            }
        }

        if (this.maxCoatLength < 5 && !woolDrop) {
            int i = this.random.nextInt(3);
            // 2 out of 3 times
            if (i != 0) {
                if (this.currentCoatLength == 0) {
                    // 5 out of 5 times
                    leatherDrop = true;
                } else if (this.currentCoatLength == 1) {
                    // 4 out of 5 times
                    i = this.random.nextInt(5);
                    if (i != 0) {
                        leatherDrop = true;
                    }
                } else if (this.currentCoatLength == 2) {
                    // 3 out of 5 times
                    i = this.random.nextInt(5);
                    if (i <= 2) {
                        leatherDrop = true;
                    }
                } else if (this.currentCoatLength == 3) {
                    // 2 out of 5 times
                    i = this.random.nextInt(5);
                    if (i <= 1) {
                        leatherDrop = true;
                    }
                } else {
                    // 1 out of 5 times
                    i = this.random.nextInt(5);
                    if (i == 0) {
                        leatherDrop = true;
                    }
                }
            }

        }

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
            if (woolDrop) {
                ItemStack fleeceStack = new ItemStack(getWoolBlockFromColour(getWoolColour()), 1);
                this.spawnAtLocation(fleeceStack);
            } else if (leatherDrop) {
                ItemStack leatherStack = new ItemStack(Items.LEATHER, 1);
                this.spawnAtLocation(leatherStack);
            }
        }
    }

    public boolean getSheared() {
        return (this.entityData.get(DYE_COLOUR) & 16) != 0;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public SheepModelData getModelData() {
        return this.sheepModelData;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void setModelData(AnimalModelData animalModelData) {
        this.sheepModelData = (SheepModelData) animalModelData;
    }

    @OnlyIn(Dist.CLIENT)
    public String getTexture() {
        if (this.enhancedAnimalTextureGrouping == null) {
            this.setTexturePaths();
        } else if (this.resetTexture && !this.isBaby()) {
            this.resetTexture = false;
            this.reloadTextures();
        }

        return getCompiledTextures("enhanced_sheep");
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

            boolean mealy = false;
            int pattern1 = 0;
            int pattern2 = 0;
            int grey = 0;
            int spots = 0;
            int roan = 0;
            int blaze = 0;
            int pigmentedHeadCategory = 0;
            int pigmentedHead = 0;
            int skin = 0;
            int hooves = 0;
            int fur = 0;
            int eyes = 0;

            char[] uuidArry = getStringUUID().toCharArray();

            if (gene[4] == 1 || gene[5] == 1){
                //black sheep
                pattern1 = 14;
            } else if (gene[0] != 1 && gene[1] != 1) {
                if (gene[0] == 6 && gene[1] == 6) {
                    pattern1 = 14;
                } else {
                    if (gene[0] == 2 || gene[1] == 2) {
                        int maxCoatLength = 0;

                        if (gene[20] == 2) {
                            maxCoatLength = 1;
                        }
                        if (gene[21] == 2) {
                            maxCoatLength = maxCoatLength + 1;
                        }
                        if (gene[22] == 2) {
                            maxCoatLength = maxCoatLength + 1;
                        }
                        if (gene[23] == 2) {
                            maxCoatLength = maxCoatLength + 1;
                        }
                        if (gene[24] == 2) {
                            maxCoatLength = maxCoatLength + 1;
                        }
                        if (gene[25] == 2) {
                            maxCoatLength = maxCoatLength + 1;
                        }
                        if (gene[26] == 2) {
                            maxCoatLength = maxCoatLength + 1;
                        }
                        if (gene[27] == 2) {
                            maxCoatLength = maxCoatLength + 1;
                        }
                        if (gene[28] == 2) {
                            maxCoatLength = maxCoatLength + 1;
                        }
                        if (gene[29] == 2) {
                            maxCoatLength = maxCoatLength + 1;
                        }
                        if (gene[30] == 2) {
                            maxCoatLength = maxCoatLength + 1;
                        }
                        if (gene[31] == 2) {
                            maxCoatLength = maxCoatLength + 1;
                        }
                        if (gene[32] == 2) {
                            maxCoatLength = maxCoatLength + 1;
                        }
                        if (gene[33] == 2) {
                            maxCoatLength = maxCoatLength + 1;
                        }
                        if (gene[34] == 2 && gene[35] == 2) {
                            maxCoatLength = maxCoatLength + 1;
                        }

                        int faceWool = 0;
                        if (gene[42] == 1 || gene[43] == 1) {
                            if (gene[40] == 1) {
                                faceWool++;
                            }
                            if (gene[41] == 1) {
                                faceWool++;
                            }
                            if (gene[38] == 1 || gene[39] == 1) {
                                faceWool++;
                            } else if (gene[38] == 3 && gene[39] == 3) {
                                faceWool--;
                            }
                        }

                        grey = maxCoatLength < 3 ? 0 : 1+faceWool;
                        pattern1 = gene[0] == 2 ? 14 : 0;
                        pattern2 = gene[1] == 2 ? 14 : 0;
                    }

                    if (gene[0] == 6) {
                        pattern1 = 14;
                    } else if (gene[1] == 6) {
                        pattern2 = 14;
                    }

                    if (pattern1 == 0 || pattern2 == 0) {
                        if (pattern1 == 0) {
                            pattern1 = gene[0] <= 2 ? 0 : gene[0] - 2;
                            if (pattern1 > 3) {
                                pattern1 = pattern1 == 4 ? 14 : pattern1 - 1;
                            }
                        }
                        if (pattern2 == 0) {
                            pattern2 = gene[1] <= 2 ? 0 : gene[1] - 2;
                            if (pattern2 > 3) {
                                pattern2 = pattern2 == 4 ? 14 : pattern2 - 1;
                            }
                        }

                        if (gene[90] == 1 || gene[91] == 1) {
                            mealy = pattern1 == 3 || (pattern1 < 14 && pattern1 > 6);
                        }
                    }

                    if (gene[0] == 6) {
                        pattern1 = 0;
                    } else if (gene[1] == 6) {
                        pattern2 = 0;
                    }
                }
            }

            //basic spots
            if (gene[8] == 2 && gene[9] == 2){
//                if (Character.isDigit(uuidArry[1])){
//                    spots = 2;
//                }else {
                    spots = 1;
//                }
            }

            //pigmented head
            if (gene[68] == 2 || gene[69] == 2) {
                if (gene[68] == 1 || gene[69] == 1) {
                    //het afghan
                    pigmentedHeadCategory = 2;
                } else {
                    //white afghan
                    pigmentedHeadCategory = 1;
                }
            } else if (gene[68] == 3 || gene[69] == 3) {
                if (gene[68] == gene[69]) {
                    //homozygous turkish
                    pigmentedHeadCategory = 3;
                } else if (gene[68] == 4 || gene[69] == 4) {
                    //het turkish/pigmented head
                    pigmentedHeadCategory = 5;
                } else {
                    //het turkish (speckled)
                    pigmentedHeadCategory = 4;
                }
            } else if (gene[68] == 4 && gene[69] == 4) {
                // pigmented head
                pigmentedHeadCategory = 6;
            }

            if (pigmentedHeadCategory==6) {
                pigmentedHead = Math.max(gene[18]-1, gene[19]-1);
            }

            if (gene[100] == 2 || gene[101] == 2) {
                roan = 1;
            }

            if (gene[102] != 1 && gene[103] != 1) {
                if (gene[102] == 2 || gene[103] == 2) {
                    blaze = 1;
                } else {
                    blaze = gene[102] == 3 || gene[103] == 3 ? 2 : 3;
                }
            }

            TextureGrouping parentGroup = new TextureGrouping(TexturingType.MERGE_GROUP);

            TextureGrouping foundationGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
            addTextureToAnimalTextureGrouping(foundationGroup, TexturingType.APPLY_RED, "r_solid_white.png");
            addTextureToAnimalTextureGrouping(foundationGroup, TexturingType.APPLY_DYE, SHEEP_TEXTURES_MEALY, mealy ? 1 : 0, l -> l != 0);
            parentGroup.addGrouping(foundationGroup);

            if (gene[0] != 1 && gene[1] != 1 && (pattern1!=0 || pattern2!=0)) {
                TextureGrouping patternAverageGroup = new TextureGrouping(TexturingType.AVERAGE_GROUP);
                addTextureToAnimalTextureGrouping(patternAverageGroup, TexturingType.APPLY_BLACK, SHEEP_TEXTURES_PATTERN, pattern1, l -> l != 0);
                addTextureToAnimalTextureGrouping(patternAverageGroup, TexturingType.APPLY_BLACK, SHEEP_TEXTURES_PATTERN, pattern2, l -> l != 0);
                parentGroup.addGrouping(patternAverageGroup);
            }

            if (mealy || roan!=0 || blaze!=0 || pigmentedHeadCategory!=0 || spots!=0) {
                boolean ticked = !this.isBaby() && (gene[70] == 2 || gene[71] == 2) && (spots != 0 || pigmentedHeadCategory != 0);
                TextureGrouping whiteSpotGroup = new TextureGrouping(ticked ? TexturingType.ALPHA_GROUP : TexturingType.MERGE_GROUP);
                addTextureToAnimalTextureGrouping(whiteSpotGroup, SHEEP_TEXTURES_TICKED, ticked ? 1 : 0, l -> l != 0);
                addTextureToAnimalTextureGrouping(whiteSpotGroup, TexturingType.APPLY_DYE, SHEEP_TEXTURES_MEALY, mealy ? (this.getOrSetIsFemale() ? 3 : 2) : 0, l -> l != 0);
                addTextureToAnimalTextureGrouping(whiteSpotGroup, TexturingType.APPLY_DYE, SHEEP_TEXTURES_ROAN, roan, l -> l != 0);
                addTextureToAnimalTextureGrouping(whiteSpotGroup, TexturingType.APPLY_DYE, SHEEP_TEXTURES_BLAZE, blaze, l -> l != 0);
                addTextureToAnimalTextureGrouping(whiteSpotGroup, TexturingType.APPLY_DYE, SHEEP_TEXTURES_SPOTS, spots, l -> l != 0);
                addTextureToAnimalTextureGrouping(whiteSpotGroup, TexturingType.APPLY_DYE, SHEEP_TEXTURES_PIGMENTEDHEAD, pigmentedHeadCategory, pigmentedHead, pigmentedHeadCategory != 0);
                parentGroup.addGrouping(whiteSpotGroup);
            }

            TextureGrouping detailGroup = new TextureGrouping(TexturingType.MERGE_GROUP);
            addTextureToAnimalTextureGrouping(detailGroup, SHEEP_TEXTURES_GREY, grey, l -> l != 0);
            addTextureToAnimalTextureGrouping(detailGroup, SHEEP_TEXTURES_FUR, fur, null);
            addTextureToAnimalTextureGrouping(detailGroup, SHEEP_TEXTURES_SKIN, skin, null);
            addTextureToAnimalTextureGrouping(detailGroup, "hooves_black.png");
            addTextureToAnimalTextureGrouping(detailGroup, "eyes_black.png");
            addTextureToAnimalTextureGrouping(detailGroup,"chests.png");
            parentGroup.addGrouping(detailGroup);

            this.setTextureGrouping(parentGroup);
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

        if (flag) {
            int[] gene = getSharedGenes().getAutosomalGenes();
            float[] melanin = {0.02F, 0.5F, 0.02F};
            float[] pheomelanin = getBasePheomelanin(gene[72]);
            float[] f = getBasePheomelanin(gene[73]);

            pheomelanin[0] = (pheomelanin[0] + f[0]) * 0.5F;
            pheomelanin[1] = (pheomelanin[1] + f[1]) * 0.5F;
            pheomelanin[2] = (pheomelanin[2] + f[2]) * 0.5F;

            int r = 0;
            for (int i = 74; i < 90; i++) {
                if (gene[i] == 2) {
                    r = i < 82 ? r-1 : r+1;
                }
            }
            if (r != 0) {
                if (r < 0) {
                    pheomelanin[0] = (((pheomelanin[0] * (10+r))) + (0.025F * -r)) * 0.1F;
                    if (pheomelanin[2] > 0.5F) {
                        pheomelanin[0] = pheomelanin[0] + (0.035F * (1.0F - pheomelanin[2]));
                    }
                } else {
                    pheomelanin[0] = (((pheomelanin[0] * (10-r))) + (0.09F * r)) * 0.1F;
                }
            }

            if (gene[4] == 3 && gene[5] == 3) {
                //sheep is red
                melanin[0] = pheomelanin[0];
                melanin[1] = pheomelanin[1];
                melanin[2] = (pheomelanin[1] + melanin[2]) * 0.5F;
            }
            if (gene[2] == 2 && gene[3] == 2) {
                //chocolate variant
                melanin[0] = Colouration.mixHueComponent(melanin[0], 0.1F, 0.3F);
                melanin[1] = melanin[1] + ((1.0F - melanin[1]) * 0.45F);
                melanin[2] = melanin[2] + ((1.0F - melanin[2]) * 0.25F);
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
            }

            this.colouration.setMelaninColour(Colouration.HSBAtoABGR(melanin[0], melanin[1], melanin[2], 0.5F));
            this.colouration.setPheomelaninColour(Colouration.HSBAtoABGR(pheomelanin[0], pheomelanin[1], pheomelanin[2], 0.5F));
        }

        return this.colouration;
    }

    private float[] getBasePheomelanin(int gene) {
        float[] red = new float[3];
        switch (gene) {
            case 1 -> {
                red[0] = 0.07F;
                red[1] = 0.6F;
                red[2] = 0.5F;
            }
            case 2 -> {
                red[0] = 0.05F;
                red[1] = 0.61F;
                red[2] = 0.4F;
            }
            case 3 -> {
                red[0] = 0.075F;
                red[1] = 0.45F;
                red[2] = 0.6F;
            }
            case 4 -> {
                red[0] = 0.085F;
                red[1] = 0.35F;
                red[2] = 0.65F;
            }
            case 5 -> {
                red[0] = 0.085F;
                red[1] = 0.3F;
                red[2] = 0.87F;
            }
            case 6 -> {
                red[0] = 0.086F;
                red[1] = 0.0F;
                red[2] = 1.0F;
            }
        }
        return red;
    }

    @Override
    protected void setMaxBagSize(){
        float maxBagSize = 1.0F;

//        if (!this.isChild() && getSheepStatus().equals(EntityState.MOTHER.toString())){
//
//        }

//        // [ 0.25 to 1.0 ]
//        maxBagSize = maxBagSize + 1.0F;
//        if (maxBagSize > 1.5F) {
//            maxBagSize = 1.5F;
//        } else if (maxBagSize < 1.0F) {
//            maxBagSize = 1.0F;
//        }

        this.maxBagSize = maxBagSize;
    }

    @Override
    public InteractionResult mobInteract(Player entityPlayer, InteractionHand hand) {
        ItemStack itemStack = entityPlayer.getItemInHand(hand);
        Item item = itemStack.getItem();

        if (item == ModItems.ENHANCED_SHEEP_EGG.get()) {
            return InteractionResult.SUCCESS;
        }

        if ((item == Items.BUCKET || item == ModItems.ONESIXTH_MILK_BUCKET.get() || item == ModItems.ONETHIRD_MILK_BUCKET.get() || item == ModItems.HALF_MILK_BUCKET.get() || item == ModItems.TWOTHIRDS_MILK_BUCKET.get() || item == ModItems.FIVESIXTHS_MILK_BUCKET.get() || item == ModItems.HALF_MILK_BOTTLE.get() || item == Items.GLASS_BOTTLE) && !this.isBaby() && getEntityStatus().equals(EntityState.MOTHER.toString())) {
            int maxRefill = 0;
            int bucketSize = 6;
            int currentMilk = getMilkAmount();
            int refillAmount = 0;
            boolean isBottle = false;
            if (item == Items.BUCKET) {
                maxRefill = 6;
            } else if (item == ModItems.ONESIXTH_MILK_BUCKET.get()) {
                maxRefill = 5;
            } else if (item == ModItems.ONETHIRD_MILK_BUCKET.get()) {
                maxRefill = 4;
            } else if (item == ModItems.HALF_MILK_BUCKET.get()) {
                maxRefill = 3;
            } else if (item == ModItems.TWOTHIRDS_MILK_BUCKET.get()) {
                maxRefill = 2;
            } else if (item == ModItems.FIVESIXTHS_MILK_BUCKET.get()) {
                maxRefill = 1;
            } else if (item == ModItems.HALF_MILK_BOTTLE.get()) {
                maxRefill = 1;
                isBottle = true;
                bucketSize = 2;
            } else if (item == Items.GLASS_BOTTLE) {
                maxRefill = 2;
                isBottle = true;
                bucketSize = 2;
            }

            if ( currentMilk >= maxRefill) {
                refillAmount = maxRefill;
            } else if (currentMilk < maxRefill) {
                refillAmount = currentMilk;
            }

            if (!this.level().isClientSide) {
                int resultingMilkAmount = currentMilk - refillAmount;
                this.setMilkAmount(resultingMilkAmount);

                float milkBagSize = resultingMilkAmount / (6*maxBagSize);

                this.setBagSize((milkBagSize*(maxBagSize/3.0F))+(maxBagSize*2.0F/3.0F));
            }

            if (!this.level().isClientSide) {
                this.setMilkAmount(currentMilk - refillAmount);
            }

            int resultAmount = bucketSize - maxRefill + refillAmount;

            ItemStack resultItem = new ItemStack(Items.BUCKET);

            switch (resultAmount) {
                case 0:
                    entityPlayer.playSound(SoundEvents.SHEEP_HURT, 1.0F, 1.0F);
                    return InteractionResult.SUCCESS;
                case 1:
                    if (isBottle) {
                        resultItem = new ItemStack(ModItems.HALF_MILK_BOTTLE.get());
                    } else {
                        resultItem = new ItemStack(ModItems.ONESIXTH_MILK_BUCKET.get());
                    }
                    break;
                case 2:
                    if (isBottle) {
                        resultItem = new ItemStack(ModItems.MILK_BOTTLE.get());
                    } else {
                        resultItem = new ItemStack(ModItems.ONETHIRD_MILK_BUCKET.get());
                    }
                    break;
                case 3:
                    resultItem = new ItemStack(ModItems.HALF_MILK_BUCKET.get());
                    break;
                case 4:
                    resultItem = new ItemStack(ModItems.TWOTHIRDS_MILK_BUCKET.get());
                    break;
                case 5:
                    resultItem = new ItemStack(ModItems.FIVESIXTHS_MILK_BUCKET.get());
                    break;
                case 6:
                    resultItem = new ItemStack(Items.MILK_BUCKET);
                    break;
            }

            entityPlayer.playSound(SoundEvents.COW_MILK, 1.0F, 1.0F);
            itemStack.shrink(1);
            if (itemStack.isEmpty()) {
                entityPlayer.setItemInHand(hand, resultItem);
            } else if (!entityPlayer.getInventory().add(resultItem)) {
                entityPlayer.drop(resultItem, false);
            }

        }

        if (!this.level().isClientSide && !hand.equals(InteractionHand.OFF_HAND)) {
            if (item instanceof AirItem) {
                int[] genes = this.genetics.getAutosomalGenes();
                if (!this.isBaby() && (genes[46] == 1 || genes[47] == 1) && this.currentCoatLength == this.maxCoatLength) {
                        List<ItemStack> woolToDrop = onSheared(entityPlayer,null, this.level(), blockPosition(), 0);
                        woolToDrop.forEach(d -> {
                            net.minecraft.world.entity.item.ItemEntity ent = this.spawnAtLocation(d, 1.0F);
                            ent.setDeltaMovement(ent.getDeltaMovement().add((double)((random.nextFloat() - random.nextFloat()) * 0.1F), (double)(random.nextFloat() * 0.05F), (double)((random.nextFloat() - random.nextFloat()) * 0.1F)));
                        });
                        onSheared(entityPlayer, ItemStack.EMPTY, this.level(), blockPosition(), 0);
                }

            } else if (item == Items.WATER_BUCKET && this.getFleeceDyeColour() != DyeColor.WHITE) {
                this.setFleeceDyeColour(DyeColor.WHITE);
                entityPlayer.setItemInHand(hand, new ItemStack(Items.BUCKET));
            } else if (item instanceof DyeItem) {
                DyeColor enumdyecolor = ((DyeItem)item).getDyeColor();
                if (enumdyecolor != this.getFleeceDyeColour()) {
                    this.setFleeceDyeColour(enumdyecolor);
                    if (!entityPlayer.getAbilities().instabuild) {
                        itemStack.shrink(1);
                    }
                }
            }  else if (item instanceof DebugGenesBook) {
                Minecraft.getInstance().keyboardHandler.setClipboard(this.entityData.get(SHARED_GENES));
            }
        }
        return super.mobInteract(entityPlayer, hand);
    }

    /**
     * make a sheep sheared if set to true
     */
    public void setSheared(boolean sheared) {
        byte b0 = this.entityData.get(DYE_COLOUR);
        if (sheared) {
            this.entityData.set(DYE_COLOUR, (byte)(b0 | 16));
        } else {
            this.entityData.set(DYE_COLOUR, (byte)(b0 & -17));
        }
    }

    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putByte("Colour", (byte)this.getFleeceDyeColour().getId());

        compound.putFloat("CoatLength", this.getCoatLength());

        compound.putInt("Lactation", this.lactationTimer);

        compound.putInt("milk", getMilkAmount());

    }

    /**
     * (abstract) Protected helper method to read subclass entity assets from NBT.
     */
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);

        this.currentCoatLength = compound.getInt("CoatLength");
        this.setCoatLength(this.currentCoatLength);

        this.setFleeceDyeColour(DyeColor.byId(compound.getByte("Colour")));

        float milkBagSize = this.getMilkAmount() / (6*this.maxBagSize);
        this.setBagSize((milkBagSize*(this.maxBagSize/3.0F))+(this.maxBagSize*2.0F/3.0F));

        setMaxCoatLength();
//        configureAI();
        if (!compound.getString("breed").isEmpty()) {
            this.currentCoatLength = this.maxCoatLength;
            this.setCoatLength(this.currentCoatLength);
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
        setInitialCoat();

        //"White" is considered no dye
        this.setFleeceDyeColour(DyeColor.WHITE);
    }

    @Override
    protected Genes createInitialGenes(LevelAccessor world, BlockPos pos, boolean isDomestic) {
        return new SheepGeneticsInitialiser().generateNewGenetics(world, pos, isDomestic);
    }

    @Override
    public Genes createInitialBreedGenes(LevelAccessor world, BlockPos pos, String breed) {
        return new SheepGeneticsInitialiser().generateWithBreed(world, pos, breed);
    }

    public void setInitialCoat() {
        setMaxCoatLength();
        int age = this.getEnhancedAnimalAge();
        this.currentCoatLength = (int)(this.maxCoatLength*(age >= this.getAdultAge() ? 1 : ((float)age/(float)this.getAdultAge())));
        setCoatLength(this.currentCoatLength);
    }

    @Override
    protected void initializeHealth(EnhancedAnimalAbstract animal, float health) {
//        int[] genes = animal.genetics.getAutosomalGenes();

        health = 8.0F;

        super.initializeHealth(animal, health);
    }

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

    private void setMaxCoatLength() {
        int[] genes = this.genetics.getAutosomalGenes();
        int maxCoatLength = 0;

        if (genes[20] == 2){
            maxCoatLength = 1;
        }
        if (genes[21] == 2){
            maxCoatLength = maxCoatLength + 1;
        }
        if (genes[22] == 2){
            maxCoatLength = maxCoatLength + 1;
        }
        if (genes[23] == 2){
            maxCoatLength = maxCoatLength + 1;
        }
        if (genes[24] == 2){
            maxCoatLength = maxCoatLength + 1;
        }
        if (genes[25] == 2){
            maxCoatLength = maxCoatLength + 1;
        }
        if (genes[26] == 2){
            maxCoatLength = maxCoatLength + 1;
        }
        if (genes[27] == 2){
            maxCoatLength = maxCoatLength + 1;
        }
        if (genes[28] == 2){
            maxCoatLength = maxCoatLength + 1;
        }
        if (genes[29] == 2){
            maxCoatLength = maxCoatLength + 1;
        }
        if (genes[30] == 2){
            maxCoatLength = maxCoatLength + 1;
        }
        if (genes[31] == 2){
            maxCoatLength = maxCoatLength + 1;
        }
        if (genes[32] == 2){
            maxCoatLength = maxCoatLength + 1;
        }
        if (genes[33] == 2){
            maxCoatLength = maxCoatLength + 1;
        }
        if (genes[34] == 2 && genes[35] == 2){
            maxCoatLength = maxCoatLength + 1;
        }


        this.maxCoatLength = maxCoatLength > 15 ? 15 : maxCoatLength;

    }

//    private void configureAI() {
//        if (!aiConfigured) {
//            Double speed = 1.0D;
//
//            this.goalSelector.addGoal(1, new PanicGoal(this, speed*1.25D));
//            this.goalSelector.addGoal(2, new EnhancedBreedGoal(this, speed));
//            this.goalSelector.addGoal(3, new TemptGoal(this, speed*1.1D, TEMPTATION_ITEMS, false));
//            this.goalSelector.addGoal(4, new FollowParentGoal(this, speed*1.25D));
//            this.goalSelector.addGoal(4, new EnhancedAINurseFromMotherGoal(this, motherUUID, speed*1.1D));
//            grazingGoal = new EnhancedWaterAvoidingRandomWalkingEatingGoal(this, speed, 12, 0.001F, 120, 2);
//            this.goalSelector.addGoal(6, grazingGoal);
//        }
//        aiConfigured = true;
//    }
}
