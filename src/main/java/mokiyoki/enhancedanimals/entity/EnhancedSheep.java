//package mokiyoki.enhancedanimals.entity;
//
//import mokiyoki.enhancedanimals.ai.EnhancedEatPlantsGoal;
//import mokiyoki.enhancedanimals.ai.general.EnhancedAvoidEntityGoal;
//import mokiyoki.enhancedanimals.ai.general.EnhancedBreedGoal;
//import mokiyoki.enhancedanimals.ai.general.EnhancedLookAtGoal;
//import mokiyoki.enhancedanimals.ai.general.EnhancedLookRandomlyGoal;
//import mokiyoki.enhancedanimals.ai.general.EnhancedPanicGoal;
//import mokiyoki.enhancedanimals.ai.general.EnhancedTemptGoal;
//import mokiyoki.enhancedanimals.ai.general.EnhancedWaterAvoidingRandomWalkingEatingGoal;
//import mokiyoki.enhancedanimals.ai.general.SeekShelterGoal;
//import mokiyoki.enhancedanimals.ai.general.StayShelteredGoal;
//import mokiyoki.enhancedanimals.entity.Genetics.SheepGeneticsInitialiser;
//import mokiyoki.enhancedanimals.entity.util.Colouration;
//import mokiyoki.enhancedanimals.init.FoodSerialiser;
//import mokiyoki.enhancedanimals.init.ModBlocks;
//import mokiyoki.enhancedanimals.init.ModItems;
//import mokiyoki.enhancedanimals.items.DebugGenesBook;
//import mokiyoki.enhancedanimals.config.EanimodCommonConfig;
//import mokiyoki.enhancedanimals.util.Genes;
//import mokiyoki.enhancedanimals.util.Reference;
//import net.minecraft.world.entity.Entity;
//import net.minecraft.world.level.block.Block;
//import net.minecraft.world.level.block.state.BlockState;
//import net.minecraft.world.level.block.Blocks;
//import net.minecraft.client.Minecraft;
//import net.minecraft.world.entity.EntityDimensions;
//import net.minecraft.world.entity.EntityType;
//import net.minecraft.world.entity.SpawnGroupData;
//import net.minecraft.world.entity.Mob;
//import net.minecraft.world.entity.Pose;
//import net.minecraft.world.entity.MobSpawnType;
//import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
//import net.minecraft.world.entity.ai.attributes.Attributes;
//import net.minecraft.world.entity.ai.goal.FollowParentGoal;
//import net.minecraft.world.entity.ai.goal.FloatGoal;
//import net.minecraft.world.entity.animal.Wolf;
//import net.minecraft.world.entity.player.Player;
//import net.minecraft.world.item.AirItem;
//import net.minecraft.world.item.DyeColor;
//import net.minecraft.world.item.DyeItem;
//import net.minecraft.world.item.Item;
//import net.minecraft.world.item.ItemStack;
//import net.minecraft.world.item.Items;
//import net.minecraft.nbt.CompoundTag;
//import net.minecraft.network.syncher.EntityDataAccessor;
//import net.minecraft.network.syncher.EntityDataSerializers;
//import net.minecraft.network.syncher.SynchedEntityData;
//import net.minecraft.core.BlockPos;
//import net.minecraft.world.DifficultyInstance;
//import net.minecraft.world.level.ServerLevelAccessor;
//import net.minecraft.world.level.LevelAccessor;
//import net.minecraft.world.level.Level;
//import net.minecraftforge.api.distmarker.Dist;
//import net.minecraftforge.api.distmarker.OnlyIn;
//
//import javax.annotation.Nullable;
//import java.util.*;
//import java.util.concurrent.ThreadLocalRandom;
//
//import static mokiyoki.enhancedanimals.init.FoodSerialiser.sheepFoodMap;
//import static mokiyoki.enhancedanimals.util.handlers.EventRegistry.ENHANCED_SHEEP;
//
//import net.minecraft.sounds.SoundEvent;
//import net.minecraft.sounds.SoundEvents;
//import net.minecraft.world.InteractionHand;
//import net.minecraft.world.InteractionResult;
//import net.minecraft.world.damagesource.DamageSource;
//
//public class EnhancedSheep extends EnhancedAnimalChestedAbstract implements net.minecraftforge.common.IForgeShearable {
//
//    //avalible UUID spaces : [ S X X 3 X 5 6 7 - 8 9 10 11 - 12 13 14 15 - 16 17 18 19 - 20 21 22 23 24 25 26 27 28 29 30 31 ]
//
//    private static final EntityDataAccessor<Integer> COAT_LENGTH = SynchedEntityData.defineId(EnhancedSheep.class, EntityDataSerializers.INT);
//    private static final EntityDataAccessor<Float> BAG_SIZE = SynchedEntityData.defineId(EnhancedSheep.class, EntityDataSerializers.FLOAT);
//    private static final EntityDataAccessor<Byte> DYE_COLOUR = SynchedEntityData.<Byte>defineId(EnhancedSheep.class, EntityDataSerializers.BYTE);
//    private static final EntityDataAccessor<Integer> MILK_AMOUNT = SynchedEntityData.defineId(EnhancedSheep.class, EntityDataSerializers.INT);
//
//    private static final String[] SHEEP_TEXTURES_UNDER = new String[] {
//            "c_solid_tan.png", "c_solid_black.png", "c_solid_choc.png", "c_solid_lighttan.png",
//            "c_solid_tan_red.png", "c_solid_choc.png", "c_solid_tan", "c_solid_lighttan_red.png"
//    };
//
//    private static final String[] SHEEP_TEXTURES_PATTERN = new String[] {
//            "", "c_solid_white.png", "c_badger_black.png", "c_badger_choc.png", "c_mouflonbadger_black.png", "c_mouflonbadger_choc.png", "c_mouflon_black.png", "c_mouflon_choc.png", "c_blue_black.png", "c_blue_choc.png", "c_solid_black.png", "c_solid_choc.png",
//                "c_solid_white.png", "c_badger_black_red.png", "c_badger_choc_red.png", "c_mouflonbadger_black_red.png", "c_mouflonbadger_choc_red.png", "c_mouflon_black_red.png", "c_mouflon_choc_red.png", "c_blue_black_red.png", "c_blue_choc_red.png", "c_solid_black_red.png", "c_solid_choc_red.png"
//    };
//
//    private static final String[] SHEEP_TEXTURES_GREY = new String[] {
//            "", "c_grey.png"
//    };
//
//    private static final String[] SHEEP_TEXTURES_SPOTS = new String[] {
//            "", "c_spot0.png",  "c_spot1.png",  "c_spot2.png", "c_spot3.png",  "c_spot4.png",  "c_spot5.png", "c_spot6.png",  "c_spot7.png",  "c_spot8.png", "c_spot9.png",  "c_spota.png",  "c_spotb.png", "c_spotc.png",  "c_spotd.png",  "c_spote.png",  "c_spotf.png"
//    };
//
//    private static final String[] SHEEP_TEXTURES_PIGMENTEDHEAD = new String[] {
//            "", "c_solid_white.png", "c_afghan_pied.png", "c_turkish.png", "c_turkish_speckled.png", "c_turkish_pigmented_head.png", "c_pigmented_head.png"
//    };
//
//    private static final String[] SHEEP_TEXTURES_TICKED = new String[] {
//            "", "ticking.png"
//    };
//
//    private static final String[] SHEEP_TEXTURES_SKIN = new String[] {
//            "skin_pink.png"
//    };
//
//    private static final String[] SHEEP_TEXTURES_HOOVES = new String[] {
//            "hooves_black.png"
//    };
//
//    private static final String[] SHEEP_TEXTURES_FUR = new String[] {
//            "c_fur_wooly.png"
//    };
//
//    private static final String[] SHEEP_TEXTURES_EYES = new String[] {
//            "eyes_black.png"
//    };
//
//    private static final int SEXLINKED_GENES_LENGTH = 2;
//
//    protected float maxBagSize;
//    private int currentBagSize;
//    private int maxCoatLength;
//    private int currentCoatLength;
//    private int timeForGrowth = 0;
//
//    private boolean resetTexture = true;
//
//    private String motherUUID = "";
//
//    public EnhancedSheep(EntityType<? extends EnhancedSheep> entityType, Level worldIn) {
//        super(entityType, worldIn, SEXLINKED_GENES_LENGTH, Reference.SHEEP_AUTOSOMAL_GENES_LENGTH, true);
//        this.initilizeAnimalSize();
//        this.timeUntilNextMilk = this.random.nextInt(this.random.nextInt(8000) + 4000);
//    }
//
//    private Map<Block, EnhancedEatPlantsGoal.EatValues> createGrazingMap() {
//        Map<Block, EnhancedEatPlantsGoal.EatValues> ediblePlants = new HashMap<>();
//        ediblePlants.put(Blocks.CARROTS, new EnhancedEatPlantsGoal.EatValues(4, 2, 750));
//        ediblePlants.put(Blocks.WHEAT, new EnhancedEatPlantsGoal.EatValues(2, 2, 750));
//        ediblePlants.put(Blocks.ALLIUM, new EnhancedEatPlantsGoal.EatValues(8, 3, 750));
//        ediblePlants.put(Blocks.AZURE_BLUET, new EnhancedEatPlantsGoal.EatValues(3, 2, 750));
//        ediblePlants.put(ModBlocks.GROWABLE_AZURE_BLUET.get(), new EnhancedEatPlantsGoal.EatValues(3, 2, 750));
//        ediblePlants.put(Blocks.BLUE_ORCHID, new EnhancedEatPlantsGoal.EatValues(7, 2, 375));
//        ediblePlants.put(ModBlocks.GROWABLE_BLUE_ORCHID.get(), new EnhancedEatPlantsGoal.EatValues(7, 2, 375));
//        ediblePlants.put(Blocks.CORNFLOWER, new EnhancedEatPlantsGoal.EatValues(7, 2, 375));
//        ediblePlants.put(ModBlocks.GROWABLE_CORNFLOWER.get(), new EnhancedEatPlantsGoal.EatValues(7, 2, 375));
//        ediblePlants.put(Blocks.DANDELION, new EnhancedEatPlantsGoal.EatValues(3, 2, 750));
//        ediblePlants.put(ModBlocks.GROWABLE_DANDELION.get(), new EnhancedEatPlantsGoal.EatValues(3, 2, 750));
//        ediblePlants.put(Blocks.OXEYE_DAISY, new EnhancedEatPlantsGoal.EatValues(7, 3, 750));
//        ediblePlants.put(ModBlocks.GROWABLE_OXEYE_DAISY.get(), new EnhancedEatPlantsGoal.EatValues(7, 2, 750));
//        ediblePlants.put(Blocks.GRASS, new EnhancedEatPlantsGoal.EatValues(1, 1, 750));
//        ediblePlants.put(ModBlocks.GROWABLE_GRASS.get(), new EnhancedEatPlantsGoal.EatValues(1, 1, 750));
//        ediblePlants.put(Blocks.TALL_GRASS, new EnhancedEatPlantsGoal.EatValues(1, 1, 750));
//        ediblePlants.put(ModBlocks.GROWABLE_TALL_GRASS.get(), new EnhancedEatPlantsGoal.EatValues(1, 1, 750));
//        ediblePlants.put(Blocks.FERN, new EnhancedEatPlantsGoal.EatValues(1, 1, 750));
//        ediblePlants.put(ModBlocks.GROWABLE_FERN.get(), new EnhancedEatPlantsGoal.EatValues(1, 1, 750));
//        ediblePlants.put(Blocks.LARGE_FERN, new EnhancedEatPlantsGoal.EatValues(1, 1, 750));
//        ediblePlants.put(ModBlocks.GROWABLE_LARGE_FERN.get(), new EnhancedEatPlantsGoal.EatValues(1, 1, 750));
//
//        return ediblePlants;
//    }
//
//    @Override
//    protected FoodSerialiser.AnimalFoodMap getAnimalFoodType() {
//        return sheepFoodMap();
//    }
//
//    @OnlyIn(Dist.CLIENT)
//    public static int getDyeRgb(DyeColor dyeColour) {
//        return Colouration.getABGRFromBGR(dyeColour.getTextureDiffuseColors());
//    }
//
//    /**
//     * Gets the wool color of this sheep.
//     */
//    public DyeColor getFleeceDyeColour() {
//        return DyeColor.byId(this.entityData.get(DYE_COLOUR) & 15);
//    }
//
//    /**
//     * Sets the wool color of this sheep
//     */
//    public void setFleeceDyeColour(DyeColor colour) {
//        byte b0 = this.entityData.get(DYE_COLOUR);
//        this.entityData.set(DYE_COLOUR, (byte)(b0 & 240 | colour.getId() & 15));
//    }
//
//    protected float getStandingEyeHeight(Pose poseIn, EntityDimensions sizeIn) {
//        return 0.95F * sizeIn.height;
//    }
//
//    private int timeUntilNextMilk;
//    private EnhancedWaterAvoidingRandomWalkingEatingGoal wanderEatingGoal;
//
//    @Override
//    protected void registerGoals() {
//        //Todo add the temperamants
//        int napmod = this.random.nextInt(1000);
//        this.wanderEatingGoal = new EnhancedWaterAvoidingRandomWalkingEatingGoal(this, 1.0D, 7, 0.001F, 120, 2, 50);
//        this.goalSelector.addGoal(0, new FloatGoal(this));
//        this.goalSelector.addGoal(1, new EnhancedPanicGoal(this, 1.25D));
//        this.goalSelector.addGoal(2, new EnhancedAvoidEntityGoal<>(this, Wolf.class, 10.0F, 1.25D, 1.25D, null));
//        this.goalSelector.addGoal(3, new EnhancedBreedGoal(this, 1.0D));
//        this.goalSelector.addGoal(4, new EnhancedTemptGoal(this, 1.0D, 1.2D, false, Items.AIR));
//        this.goalSelector.addGoal(5, new FollowParentGoal(this, 1.1D));
//        this.goalSelector.addGoal(6, new StayShelteredGoal(this, 5723, 7000, napmod));
//        this.goalSelector.addGoal(7, new SeekShelterGoal(this, 1.0D, 5723, 7000, napmod));
//        this.goalSelector.addGoal(8, new EnhancedEatPlantsGoal(this, createGrazingMap()));
//        this.goalSelector.addGoal(9, this.wanderEatingGoal);
//        this.goalSelector.addGoal(10, new EnhancedLookAtGoal(this, Player.class, 6.0F));
//        this.goalSelector.addGoal(10, new EnhancedLookAtGoal(this, EnhancedSheep.class, 6.0F));
//        this.goalSelector.addGoal(11, new EnhancedLookRandomlyGoal(this));
//    }
//
//    //TODO put new sheep behaviour here
//
//    @Override
//    public boolean canHaveBlanket() {
//        return false;
//    }
//
//    protected void customServerAiStep()
//    {
//        this.animalEatingTimer = this.wanderEatingGoal.getEatingGrassTimer();
//        super.customServerAiStep();
//    }
//
//    @Override
//    public EntityDimensions getDimensions(Pose poseIn) {
//        return EntityDimensions.scalable(0.8F, 1.2F).scale(this.getScale());
//    }
//
//    @Override
//    public float getScale() {
//        float size = this.getAnimalSize() > 0.0F ? this.getAnimalSize() : 1.0F;
//        float newbornSize = 0.325F;
//        return this.isGrowing() ? (newbornSize + ((size-newbornSize) * (this.growthAmount()))) : size;
//    }
//
//    protected void defineSynchedData() {
//        super.defineSynchedData();
//        this.entityData.define(COAT_LENGTH, 0);
//        this.entityData.define(DYE_COLOUR, Byte.valueOf((byte)0));
//        this.entityData.define(BAG_SIZE, 0.0F);
//        this.entityData.define(MILK_AMOUNT, 0);
//    }
//
//    protected String getSpecies() {
//        return "entity.eanimod.enhanced_sheep";
//    }
//
//    @Override
//    protected int getAdultAge() { return EanimodCommonConfig.COMMON.adultAgeSheep.get();}
//
//    @Override
//    protected int gestationConfig() {
//        return EanimodCommonConfig.COMMON.gestationDaysSheep.get();
//    }
//
//    private void setCoatLength(int coatLength) {
//        this.entityData.set(COAT_LENGTH, coatLength);
//    }
//
//    public int getCoatLength() {
//        return this.entityData.get(COAT_LENGTH);
//    }
//
//    public static AttributeSupplier.Builder prepareAttributes() {
//        return Mob.createMobAttributes()
//                .add(Attributes.MAX_HEALTH, 8.0D)
//                .add(Attributes.MOVEMENT_SPEED, 0.23D);
//    }
//
//    protected void setMilkAmount(Integer milkAmount) {
//        this.entityData.set(MILK_AMOUNT, milkAmount);
//    }
//
//    public Integer getMilkAmount() { return this.entityData.get(MILK_AMOUNT); }
//
//    public boolean decreaseMilk(int decrease) {
//        int milk = getMilkAmount();
//        if (milk >= decrease) {
//            milk = milk - decrease;
//            setMilkAmount(milk);
//            return true;
//        } else {
//            this.playSound(SoundEvents.SHEEP_HURT, 1.0F, 1.0F);
//            return false;
//        }
//    }
//
//    @Override
//    public void aiStep() {
//        super.aiStep();
//
//        if (!this.level.isClientSide) {
//            if (getEntityStatus().equals(EntityState.MOTHER.toString())) {
//                if (hunger <= 24000) {
//                    if (--this.timeUntilNextMilk <= 0) {
//                        int milk = getMilkAmount();
//                        if (milk < (6*this.maxBagSize)) {
//                            milk++;
//                            setMilkAmount(milk);
//                            this.timeUntilNextMilk = this.random.nextInt(this.random.nextInt(8000) + 4000);
//
//                            float milkBagSize = milk / (6*this.maxBagSize);
//
//                            this.setBagSize((milkBagSize*(this.maxBagSize/3.0F))+(this.maxBagSize*2.0F/3.0F));
//                        }
//                    }
//                }
//
//                if (this.timeUntilNextMilk == 0) {
//                    this.lactationTimer++;
//                } else if (getMilkAmount() <= (6*this.maxBagSize) && this.lactationTimer >= -36000) {
//                    this.lactationTimer--;
//                }
//
//                if (this.lactationTimer >= 0) {
//                    setEntityStatus(EntityState.ADULT.toString());
//                }
//            }
//        }
//
//    }
//
//    @Override
//    protected void runExtraIdleTimeTick() {
//        if (this.hunger <= 36000) {
//            this.timeForGrowth++;
//        }
//
//        int maxcoat = this.getAge() >= this.getAdultAge() ? this.maxCoatLength : (int)(this.maxCoatLength*(((float)this.getAge()/(float)this.getAdultAge())));
//
//        if (maxcoat > 0) {
//            int[] genes = this.genetics.getAutosomalGenes();
//            //first check is for self shearing sheep
//            if (this.currentCoatLength == maxcoat && (genes[46] == 1 || genes[47] == 1) && this.timeForGrowth >= 24000) {
//                this.timeForGrowth = 0;
//                this.currentCoatLength = maxcoat >= 2 ? this.random.nextInt(maxcoat/2) : 0;
//            } else if (this.timeForGrowth >= (24000 / maxcoat)) {
//                this.timeForGrowth = 0;
//                if (maxcoat > this.currentCoatLength) {
//                    this.currentCoatLength++;
//                    setCoatLength(this.currentCoatLength);
//                }
//            }
//
//        }
//    }
//
//    @Override
//    protected void lethalGenes() {
//        int[] genes = this.genetics.getAutosomalGenes();
//        if(genes[68] == 2 && genes[69] == 2) {
//            this.remove(RemovalReason.KILLED);
//        }
//    }
//
//    @Override
//    protected int getNumberOfChildren() {
//        int[] genes = this.genetics.getAutosomalGenes();
//        int lambRange;
//        int lambAverage = 1;
//        int numberOfLambs;
//
//        if (genes[38] == 1 || genes[39] == 1) {
//            //1 baby
//            lambRange = 1;
//        } else if (genes[38] == 3 && genes[39] == 3) {
//            // 2-3 babies
//            lambRange = 2;
//            lambAverage = 2;
//        } else if (genes[38] == 2 && genes[39] == 2) {
//            //1 to 2 babies
//            lambRange = 2;
//        } else {
//            // 1-3 babies
//            lambRange = 3;
//            lambAverage = 1;
//        }
//
//        if (lambRange != 1) {
//            numberOfLambs = ThreadLocalRandom.current().nextInt(lambRange) + lambAverage;
//        } else {
//            numberOfLambs = 1;
//        }
//
//        return numberOfLambs;
//    }
//
//    @Override
//    protected void incrementHunger() {
//        if(this.sleeping) {
//            this.hunger = this.hunger + (0.5F*getHungerModifier());
//        } else {
//            this.hunger = this.hunger + (1.0F*getHungerModifier());
//        }
//    }
//
//    protected void createAndSpawnEnhancedChild(Level inWorld) {
//        EnhancedSheep enhancedsheep = ENHANCED_SHEEP.get().create(this.level);
//        Genes babyGenes = new Genes(this.genetics).makeChild(this.getOrSetIsFemale(), this.mateGender, this.mateGenetics);
//        defaultCreateAndSpawn(enhancedsheep, inWorld, babyGenes, -this.getAdultAge());
//        enhancedsheep.setMaxCoatLength();
//        enhancedsheep.currentCoatLength = 0;
//        enhancedsheep.setCoatLength(0);
//        this.level.addFreshEntity(enhancedsheep);
//    }
//
//    @Override
//    protected boolean canBePregnant() {
//        return true;
//    }
//
//    @Override
//    protected boolean canLactate() {
//        return true;
//    }
//
//    protected SoundEvent getAmbientSound() {
//        if (isAnimalSleeping()) {
//            return null;
//        }
//        return SoundEvents.SHEEP_AMBIENT;
//    }
//
//    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
//        return SoundEvents.SHEEP_HURT;
//    }
//
//    protected SoundEvent getDeathSound() {
//        return SoundEvents.SHEEP_DEATH;
//    }
//
//    protected void playStepSound(BlockPos pos, BlockState blockIn) {
//        this.playSound(SoundEvents.SHEEP_STEP, 0.15F, 1.0F);
//        if (!this.isSilent() && this.getBells()) {
//            this.playSound(SoundEvents.NOTE_BLOCK_CHIME, 1.5F, 1.0F);
//        }
//    }
//
//    public void ate() {
//        this.setSheared(false);
//        if (!this.isBaby() && (maxCoatLength > currentCoatLength)){
//            this.currentCoatLength ++ ;
//            setCoatLength(currentCoatLength);
//        }
//
//    }
//
//    @Override
//    public boolean isShearable(ItemStack item, Level world, BlockPos pos) {
//        return !this.level.isClientSide && currentCoatLength >= 1;
//    }
//
//    @Override
//    public java.util.List<ItemStack> onSheared(Player player, ItemStack item, Level world, BlockPos pos, int fortune) {
//        java.util.List<ItemStack> ret = new java.util.ArrayList<>();
//        if (!this.level.isClientSide) {
//            int woolCount = 0;
//            if (this.currentCoatLength == 1) {
//                int i = this.random.nextInt(5);
//                if (i>3){
//                    woolCount++;
//                }
//            } else if (this.currentCoatLength == 2) {
//                int i = this.random.nextInt(5);
//                if (i>2){
//                    woolCount++;
//                }
//            } else if (this.currentCoatLength == 3) {
//                int i = this.random.nextInt(5);
//                if (i>1){
//                    woolCount++;
//                }
//            } else if (this.currentCoatLength == 4) {
//                int i = this.random.nextInt(5);
//                if (i>0) {
//                    woolCount++;
//                }
//            } else if (this.currentCoatLength >= 5) {
//                woolCount++;
//
//                if (this.currentCoatLength == 6) {
//                    int i = this.random.nextInt(5);
//                    if (i>3){
//                        woolCount++;
//                    }
//                } else if (this.currentCoatLength == 7) {
//                    int i = this.random.nextInt(5);
//                    if (i>2){
//                        woolCount++;
//                    }
//                } else if (this.currentCoatLength == 8) {
//                    int i = this.random.nextInt(5);
//                    if (i>1){
//                        woolCount++;
//                    }
//                } else if (this.currentCoatLength == 9) {
//                    int i = this.random.nextInt(5);
//                    if (i>0) {
//                        woolCount++;
//                    }
//                } else if (this.currentCoatLength >= 10) {
//                    woolCount++;
//                    if (this.currentCoatLength == 11) {
//                        int i = this.random.nextInt(5);
//                        if (i>3){
//                            woolCount++;
//                        }
//                    } else if (this.currentCoatLength == 12) {
//                        int i = this.random.nextInt(5);
//                        if (i>2){
//                            woolCount++;
//                        }
//                    } else if (this.currentCoatLength == 13) {
//                        int i = this.random.nextInt(5);
//                        if (i>1){
//                            woolCount++;
//                        }
//                    } else if (this.currentCoatLength == 14) {
//                        int i = this.random.nextInt(5);
//                        if (i>0) {
//                            woolCount++;
//                        }
//                    } else if (this.currentCoatLength >= 15) {
//                        woolCount++;
//                    }
//                }
//            }
//
//            for (int c = 0-woolCount; c < 0; c++){
//
//                DyeColor woolColour = getWoolColour();
//
//                switch (woolColour) {
//                    case WHITE:
//                    default:
//                        ret.add(new ItemStack(Blocks.WHITE_WOOL));
//                        break;
//                    case ORANGE:
//                        ret.add(new ItemStack(Blocks.ORANGE_WOOL));
//                        break;
//                    case MAGENTA:
//                        ret.add(new ItemStack(Blocks.MAGENTA_WOOL));
//                        break;
//                    case LIGHT_BLUE:
//                        ret.add(new ItemStack(Blocks.LIGHT_BLUE_WOOL));
//                        break;
//                    case YELLOW:
//                        ret.add(new ItemStack(Blocks.YELLOW_WOOL));
//                        break;
//                    case LIME:
//                        ret.add(new ItemStack(Blocks.LIME_WOOL));
//                        break;
//                    case PINK:
//                        ret.add(new ItemStack(Blocks.PINK_WOOL));
//                        break;
//                    case GRAY:
//                        ret.add(new ItemStack(Blocks.GRAY_WOOL));
//                        break;
//                    case LIGHT_GRAY:
//                        ret.add(new ItemStack(Blocks.LIGHT_GRAY_WOOL));
//                        break;
//                    case CYAN:
//                        ret.add(new ItemStack(Blocks.CYAN_WOOL));
//                        break;
//                    case PURPLE:
//                        ret.add(new ItemStack(Blocks.PURPLE_WOOL));
//                        break;
//                    case BLUE:
//                        ret.add(new ItemStack(Blocks.BLUE_WOOL));
//                        break;
//                    case BROWN:
//                        ret.add(new ItemStack(Blocks.BROWN_WOOL));
//                        break;
//                    case GREEN:
//                        ret.add(new ItemStack(Blocks.GREEN_WOOL));
//                        break;
//                    case RED:
//                        ret.add(new ItemStack(Blocks.RED_WOOL));
//                        break;
//                    case BLACK:
//                        ret.add(new ItemStack(Blocks.BLACK_WOOL));
//                        break;
//                }
//            }
//        }
//        this.currentCoatLength = 0;
//        setCoatLength(this.currentCoatLength);
//        return ret;
//    }
//
//    private DyeColor getWoolColour() {
//        int[] gene = this.genetics.getAutosomalGenes();
//        int spots = 0;
//        DyeColor returnDye;
//
//        if (((gene[0] == 1 || gene[1] == 1) && !(gene[4] == 1 || gene[5] == 1)) || gene[68] == 2 || gene[69] == 2 || (gene[68] >= 3 && gene[68] == gene[69])) {
//            //sheep is white
//            spots = 2;
//        } else if (gene[8] == 2 && gene[9] == 2){
//            // 1 out of 3 chance to drop white wool instead
//            spots = this.random.nextInt(3) == 0 ? 1 : 0;
//            if (spots == 0 && (gene[68] == 3 || gene[69] == 3)) {
//                if (gene[68] == 4 || gene[69] == 4) {
//                    // 2 out of 3 chance to drop white wool
//                    spots = this.random.nextInt(3) == 0 ? 1 : 0;
//                } else if (gene[68] == 1 || gene[69] == 1) {
//                    // 1 out of 2 chance to drop white wool
//                    spots = this.random.nextInt(2);
//                }
//            }
//        } else if (gene[68] == 3 || gene[69] == 3) {
//            if (gene[68] == 4 || gene[69] == 4) {
//                // 2 out of 3 chance to drop white wool
//                spots = this.random.nextInt(3);
//            } else if (gene[68] == 1 || gene[69] == 1) {
//                // 1 out of 2 chance to drop white wool
//                spots = this.random.nextInt(2);
//            }
//        }
//
//        if (spots == 0) {
//            if (gene[4] == 1 || gene[5] == 1) {
//                //sheep is dominant black
//                if (gene[2] == 1 || gene[3] == 1){
//                    //is not chocolate
//                    returnDye = DyeColor.BLACK;
//                }else{
//                    //is chocolate
//                    returnDye = DyeColor.BROWN;
//                }
//            } else if (gene[4] == 3 && gene[5] == 3) {
//                //red sheep
//                if (gene[0] == 2 || gene[1] == 2 || gene[0] == 3 || gene[1] == 3) {
//                    returnDye = DyeColor.WHITE;
//                } else {
//                    if ((gene[0] >= 5 && gene[1] >= 5) && (gene[0] == 5 || gene[1] == 5)) {
//                        returnDye = DyeColor.LIGHT_GRAY;
//                    } else {
//                        returnDye = DyeColor.BROWN;
//                    }
//                }
//            } else if (gene[0] >= 3 && gene[1] >= 3){
//                if (gene[0] == 3 || gene[1] == 3) {
//                    //badgerface or badgerface mix brown sheep
//                    returnDye = DyeColor.BROWN;
//                } else if (gene[0] == 4 || gene[1] == 4) {
//                    //mouflon black or black mix with blue
//                    if (gene[2] == 1 || gene[3] == 1){
//                        //is not chocolate
//                        returnDye = DyeColor.BLACK;
//                    }else{
//                        //is chocolate
//                        returnDye = DyeColor.BROWN;
//                    }
//                } else if (gene[0] == 5 || gene[1] == 5) {
//                    //blue
//                    if (gene[2] == 1 || gene[3] == 1) {
//                        returnDye = DyeColor.GRAY;
//                    } else {
//                        returnDye = DyeColor.BROWN;
//                    }
//                } else {
//                    if (gene[2] == 1 || gene[3] == 1){
//                        //is not chocolate
//                        returnDye = DyeColor.BLACK;
//                    }else{
//                        //is chocolate
//                        returnDye = DyeColor.BROWN;
//                    }
//                }
//            }else if ((gene[0] == 2 || gene[1] == 2) && !(gene[0] == 3 || gene[1] == 3)) {
//                returnDye = DyeColor.LIGHT_GRAY;
//            } else {
//                returnDye = DyeColor.WHITE;
//            }
//        } else {
//            returnDye = DyeColor.WHITE;
//        }
//
//        if (returnDye == DyeColor.WHITE || returnDye == DyeColor.LIGHT_GRAY){
//            switch (this.getFleeceDyeColour()) {
//                case WHITE:
//                default:
//                    if (returnDye == DyeColor.LIGHT_GRAY) {
//                        returnDye = DyeColor.LIGHT_GRAY;
//                    }
//                    break;
//                case ORANGE:
//                    returnDye = DyeColor.ORANGE;
//                    break;
//                case MAGENTA:
//                    returnDye = DyeColor.MAGENTA;
//                    break;
//                case LIGHT_BLUE:
//                    returnDye = DyeColor.LIGHT_BLUE;
//                    break;
//                case YELLOW:
//                    returnDye = DyeColor.YELLOW;
//                    break;
//                case LIME:
//                    returnDye = DyeColor.LIME;
//                    break;
//                case PINK:
//                    returnDye = DyeColor.PINK;
//                    break;
//                case GRAY:
//                    returnDye = DyeColor.GRAY;
//                    break;
//                case LIGHT_GRAY:
//                    returnDye = DyeColor.LIGHT_GRAY;
//                    break;
//                case CYAN:
//                    returnDye = DyeColor.CYAN;
//                    break;
//                case PURPLE:
//                    returnDye = DyeColor.PURPLE;
//                    break;
//                case BLUE:
//                    returnDye = DyeColor.BLUE;
//                    break;
//                case BROWN:
//                    returnDye = DyeColor.BROWN;
//                    break;
//                case GREEN:
//                    returnDye = DyeColor.GREEN;
//                    break;
//                case RED:
//                    returnDye = DyeColor.RED;
//                    break;
//                case BLACK:
//                    returnDye = DyeColor.BLACK;
//                    break;
//            }
//        } else if (returnDye == DyeColor.BROWN || returnDye == DyeColor.GRAY) {
//            switch (this.getFleeceDyeColour()) {
//                case ORANGE:
//                    returnDye = DyeColor.BROWN;
//                    break;
//                case MAGENTA:
//                    returnDye = DyeColor.BROWN;
//                    break;
//                case LIGHT_BLUE:
//                    returnDye = DyeColor.BLACK;
//                    break;
//                case YELLOW:
//                    returnDye = DyeColor.BROWN;
//                    break;
//                case LIME:
//                    returnDye = DyeColor.BLACK;
//                    break;
//                case PINK:
//                    returnDye = DyeColor.BROWN;
//                    break;
//                case GRAY:
//                    returnDye = DyeColor.BLACK;
//                    break;
//                case LIGHT_GRAY:
//                    returnDye = DyeColor.GRAY;
//                    break;
//                case CYAN:
//                    returnDye = DyeColor.BLACK;
//                    break;
//                case PURPLE:
//                    returnDye = DyeColor.BLACK;
//                    break;
//                case BLUE:
//                    returnDye = DyeColor.BLACK;
//                    break;
//                case BROWN:
//                    returnDye = DyeColor.BROWN;
//                    break;
//                case GREEN:
//                    returnDye = DyeColor.BLACK;
//                    break;
//                case RED:
//                    returnDye = DyeColor.BLACK;
//                    break;
//                case BLACK:
//                    returnDye = DyeColor.BLACK;
//                    break;
//            }
//        }
//
//        if(this.getFleeceDyeColour() == DyeColor.BLACK) {
//            returnDye = DyeColor.BLACK;
//        }
//
//        return returnDye;
//    }
//
//    private Block getWoolBlocks () {
//        DyeColor woolColour = getWoolColour();
//        Block returnBlocks;
//
//        switch (woolColour) {
//            case WHITE:
//            default:
//                returnBlocks = Blocks.WHITE_WOOL;
//                break;
//            case ORANGE:
//                returnBlocks = Blocks.ORANGE_WOOL;
//                break;
//            case MAGENTA:
//                returnBlocks = Blocks.MAGENTA_WOOL;
//                break;
//            case LIGHT_BLUE:
//                returnBlocks = Blocks.LIGHT_BLUE_WOOL;
//                break;
//            case YELLOW:
//                returnBlocks = Blocks.YELLOW_WOOL;
//                break;
//            case LIME:
//                returnBlocks = Blocks.LIME_WOOL;
//                break;
//            case PINK:
//                returnBlocks = Blocks.PINK_WOOL;
//                break;
//            case GRAY:
//                returnBlocks = Blocks.GRAY_WOOL;
//                break;
//            case LIGHT_GRAY:
//                returnBlocks = Blocks.LIGHT_GRAY_WOOL;
//                break;
//            case CYAN:
//                returnBlocks = Blocks.CYAN_WOOL;
//                break;
//            case PURPLE:
//                returnBlocks = Blocks.PURPLE_WOOL;
//                break;
//            case BLUE:
//                returnBlocks = Blocks.BLUE_WOOL;
//                break;
//            case BROWN:
//                returnBlocks = Blocks.BROWN_WOOL;
//                break;
//            case GREEN:
//                returnBlocks = Blocks.GREEN_WOOL;
//                break;
//            case RED:
//                returnBlocks = Blocks.RED_WOOL;
//                break;
//            case BLACK:
//                returnBlocks = Blocks.BLACK_WOOL;
//                break;
//        }
//
//        return returnBlocks;
//    }
//
//    @Override
//    protected boolean shouldDropExperience() { return true; }
//
//    protected void dropCustomDeathLoot(DamageSource source, int looting, boolean recentlyHitIn) {
//        super.dropCustomDeathLoot(source, looting, recentlyHitIn);
//        float size = this.getAnimalSize();
//        int age = this.getAge();
//        int meatDrop = this.random.nextInt(4)+1;
//        boolean woolDrop = false;
//        boolean leatherDrop = false;
//        int meatChanceMod;
//
//        if (this.currentCoatLength >= 5) {
//            woolDrop = true;
//        } else if (this.currentCoatLength == this.maxCoatLength && this.maxCoatLength >= 1) {
//            if (this.currentCoatLength >= 5) {
//                    woolDrop = true;
//            } else if (this.currentCoatLength == 1) {
//                int i = this.random.nextInt(5);
//                if (i>3){
//                    woolDrop = true;
//                }
//            } else if (this.currentCoatLength == 2) {
//                int i = this.random.nextInt(5);
//                if (i>2){
//                    woolDrop = true;
//                }
//            } else if (this.currentCoatLength == 3) {
//                int i = this.random.nextInt(5);
//                if (i>1){
//                    woolDrop = true;
//                }
//            } else {
//                int i = this.random.nextInt(5);
//                if (i>0) {
//                    woolDrop = true;
//                }
//            }
//        }
//
//        if (this.maxCoatLength < 5 && !woolDrop) {
//            int i = this.random.nextInt(3);
//            // 2 out of 3 times
//            if (i != 0) {
//                if (this.currentCoatLength == 0) {
//                    // 5 out of 5 times
//                    leatherDrop = true;
//                } else if (this.currentCoatLength == 1) {
//                    // 4 out of 5 times
//                    i = this.random.nextInt(5);
//                    if (i != 0) {
//                        leatherDrop = true;
//                    }
//                } else if (this.currentCoatLength == 2) {
//                    // 3 out of 5 times
//                    i = this.random.nextInt(5);
//                    if (i <= 2) {
//                        leatherDrop = true;
//                    }
//                } else if (this.currentCoatLength == 3) {
//                    // 2 out of 5 times
//                    i = this.random.nextInt(5);
//                    if (i <= 1) {
//                        leatherDrop = true;
//                    }
//                } else {
//                    // 1 out of 5 times
//                    i = this.random.nextInt(5);
//                    if (i == 0) {
//                        leatherDrop = true;
//                    }
//                }
//            }
//
//        }
//
//        if (age < 72000) {
//            if (age >= 54000) {
//                meatDrop = meatDrop - 1;
//                meatChanceMod = (age-54000)/180;
//            } else if (age >= 36000) {
//                meatDrop = meatDrop - 2;
//                meatChanceMod = (age-36000)/180;
//            } else if (age >= 18000) {
//                meatDrop = meatDrop - 3;
//                meatChanceMod = (age-18000)/180;
//            } else {
//                meatDrop = meatDrop - 4;
//                meatChanceMod = age/180;
//            }
//
//            int i = this.random.nextInt(100);
//            if (meatChanceMod > i) {
//                meatDrop++;
//            }
//
//            if (woolDrop || leatherDrop) {
//                i = this.random.nextInt(100);
//                if (age/720 > i) {
//                    woolDrop = false;
//                    leatherDrop = false;
//                }
//            }
//        }
//
//        if (meatDrop < 0) {
//            meatDrop = 0;
//        }
//
//        if (this.isOnFire()){
//            ItemStack cookedMuttonStack = new ItemStack(Items.COOKED_MUTTON, meatDrop);
//            this.spawnAtLocation(cookedMuttonStack);
//        }else {
//            ItemStack muttonStack = new ItemStack(Items.MUTTON, meatDrop);
//            this.spawnAtLocation(muttonStack);
//            if (woolDrop) {
//                ItemStack fleeceStack = new ItemStack(getWoolBlocks(), 1);
//                this.spawnAtLocation(fleeceStack);
//            } else if (leatherDrop) {
//                ItemStack leatherStack = new ItemStack(Items.LEATHER, 1);
//                this.spawnAtLocation(leatherStack);
//            }
//        }
//    }
//
//    public boolean getSheared() {
//        return (this.entityData.get(DYE_COLOUR) & 16) != 0;
//    }
//
//    @OnlyIn(Dist.CLIENT)
//    public String getTexture() {
//        if (this.enhancedAnimalTextures.isEmpty()) {
//            this.setTexturePaths();
//            this.setAlphaTexturePaths();
//        } else if (this.resetTexture && !this.isBaby()) {
//            this.resetTexture = false;
//            this.reloadTextures();
//        }
//
//        return getCompiledTextures("enhanced_sheep");
//    }
//
//    @Override
//    @OnlyIn(Dist.CLIENT)
//    protected void reloadTextures() {
//        this.texturesIndexes.clear();
//        this.enhancedAnimalTextures.clear();
//        this.compiledTexture = null;
//        this.colouration.setMelaninColour(-1);
//        this.colouration.setPheomelaninColour(-1);
//        this.setTexturePaths();
//    }
//
//    @Override
//    @OnlyIn(Dist.CLIENT)
//    protected void setTexturePaths() {
//        if (this.getSharedGenes() != null) {
//            int[] gene = getSharedGenes().getAutosomalGenes();
//
//            int under = 0;
//            int pattern = 0;
//            int grey = 0;
//            int spots = 0;
//            int pigmentedHead = 0;
//            int skin = 0;
//            int hooves = 0;
//            int fur = 0;
//            int eyes = 0;
//
//            char[] uuidArry = getStringUUID().toCharArray();
//
//            if (gene[4] == 1 || gene[5] ==1){
//                //black sheep
//                under = 1;
//            }else {
//                if (gene[0] == 1 || gene[1] == 1) {
//                    //white sheep
//                    pattern = 1;
//                }else if (gene[0] == 2 || gene[1] == 2){
//                    grey = 1;
//                    if (gene[0] == 3 || gene[1] == 3){
//                        pattern = 2;
//                    }else if (gene[0] == 4 || gene[1] == 4){
//                        pattern = 6;
//                    }else{
//                        pattern = 10;
//                    }
//                }else if(gene[0] == 3 || gene[1] == 3){
//                    if(gene[0] == 4 || gene[1] == 4){
//                        pattern = 4;
//                    }else{
//                        pattern = 2;
//                    }
//                }else if (gene[0] == 4 || gene[1] == 4){
//                    pattern = 6;
//                }else if (gene[0] == 5 || gene[1] == 5){
//                    pattern = 8;
//                    under = 3;
//                }else{
//                    pattern = 10;
//                }
//
//                //red variant
//                if (gene[4] == 3 && gene[5] == 3){
//                    under = under + 4;
//                    pattern = pattern + 11;
//                }
//            }
//
//            //chocolate variant
//            if (gene[2] == 2 && gene[3] == 2){
//                if (pattern == 0) {
//                    under = 2;
//                } else if (pattern!=1 && pattern!=12){
//                    pattern = pattern + 1;
//                }
//            }
//
//            //basic spots
//            if (gene[8] == 2 && gene[9] == 2){
//                if (Character.isDigit(uuidArry[1])){
//                    spots = 2;
//                }else {
//                    spots = 1;
//                }
//            }
//
//            //pigmented head
//            if (gene[68] == 2 || gene[69] == 2) {
//                if (gene[68] == 1 || gene[69] == 1) {
//                    //het afghan
//                    pigmentedHead = 2;
//                } else {
//                    //white afghan
//                    pigmentedHead = 1;
//                }
//            } else if (gene[68] == 3 || gene[69] == 3) {
//                if (gene[68] == gene[69]) {
//                    //homozygous turkish
//                    pigmentedHead = 3;
//                } else if (gene[68] == 4 || gene[69] == 4) {
//                    //het turkish/pigmented head
//                    pigmentedHead = 5;
//                } else {
//                    //het turkish (speckled)
//                    pigmentedHead = 4;
//                }
//            } else if (gene[68] == 4 && gene[69] == 4) {
//                // pigmented head
//                pigmentedHead = 6;
//            }
//
//            boolean ticked = !this.isBaby() && (gene[70] == 2 || gene[71] == 2) && (spots != 0 || pigmentedHead != 0);
//
//            addTextureToAnimal(SHEEP_TEXTURES_UNDER, under, null);
//            addTextureToAnimal(SHEEP_TEXTURES_PATTERN, pattern, l -> l != 0);
//            addTextureToAnimal(SHEEP_TEXTURES_GREY, grey, l -> l != 0);
//            if (ticked) {
//                this.enhancedAnimalTextures.add("alpha_group_start");
//            }
//            addTextureToAnimal(SHEEP_TEXTURES_SPOTS, spots, l -> l != 0);
//            addTextureToAnimal(SHEEP_TEXTURES_PIGMENTEDHEAD, pigmentedHead, l -> l != 0);
//            if (ticked) {
//                this.enhancedAnimalTextures.add("alpha_group_end");
//            }
//            addTextureToAnimal(SHEEP_TEXTURES_SKIN, skin, null);
//            addTextureToAnimal(SHEEP_TEXTURES_HOOVES, hooves, null);
//            addTextureToAnimal(SHEEP_TEXTURES_FUR, fur, null);
//            addTextureToAnimal(SHEEP_TEXTURES_EYES, eyes, null);
//        }
//    }
//
//    @Override
//    protected void setAlphaTexturePaths() {
//        Genes genes = getSharedGenes();
//        if (genes != null) {
//            int[] gene = genes.getAutosomalGenes();
//            if (gene != null) {
//                if (!this.isBaby() && (gene[70] == 2 || gene[71] == 2)) {
//                    this.enhancedAnimalAlphaTextures.add(SHEEP_TEXTURES_TICKED[1]);
//                }
//            }
//        }
//    }
//
//    @Override
//    public Colouration getRgb() {
//        boolean flag = this.colouration.getMelaninColour() == -1;
//        this.colouration = super.getRgb();
//
//        if(this.colouration == null) {
//            return null;
//        }
//
//        if (flag) {
//            float[] melanin = Colouration.getHSBFromABGR(this.colouration.getMelaninColour());
//
//            melanin[0] = 0.02F;
//            melanin[1] = 0.5F;
//            melanin[2] = 0.02F;
//
//            this.colouration.setMelaninColour(Colouration.HSBAtoABGR(melanin[0], melanin[1], melanin[2], 1F));
//        }
//
//        return this.colouration;
//    }
//
//    @Override
//    protected void setMaxBagSize(){
//        float maxBagSize = 1.0F;
//
////        if (!this.isChild() && getSheepStatus().equals(EntityState.MOTHER.toString())){
////
////        }
//
////        // [ 0.25 to 1.0 ]
////        maxBagSize = maxBagSize + 1.0F;
////        if (maxBagSize > 1.5F) {
////            maxBagSize = 1.5F;
////        } else if (maxBagSize < 1.0F) {
////            maxBagSize = 1.0F;
////        }
//
//        this.maxBagSize = maxBagSize;
//    }
//
//    @Override
//    public InteractionResult mobInteract(Player entityPlayer, InteractionHand hand) {
//        ItemStack itemStack = entityPlayer.getItemInHand(hand);
//        Item item = itemStack.getItem();
//
//        if ((item == Items.BUCKET || item == ModItems.ONESIXTH_MILK_BUCKET.get() || item == ModItems.ONETHIRD_MILK_BUCKET.get() || item == ModItems.HALF_MILK_BUCKET.get() || item == ModItems.TWOTHIRDS_MILK_BUCKET.get() || item == ModItems.FIVESIXTHS_MILK_BUCKET.get() || item == ModItems.HALF_MILK_BOTTLE.get() || item == Items.GLASS_BOTTLE) && !this.isBaby() && getEntityStatus().equals(EntityState.MOTHER.toString())) {
//            int maxRefill = 0;
//            int bucketSize = 6;
//            int currentMilk = getMilkAmount();
//            int refillAmount = 0;
//            boolean isBottle = false;
//            if (item == Items.BUCKET) {
//                maxRefill = 6;
//            } else if (item == ModItems.ONESIXTH_MILK_BUCKET.get()) {
//                maxRefill = 5;
//            } else if (item == ModItems.ONETHIRD_MILK_BUCKET.get()) {
//                maxRefill = 4;
//            } else if (item == ModItems.HALF_MILK_BUCKET.get()) {
//                maxRefill = 3;
//            } else if (item == ModItems.TWOTHIRDS_MILK_BUCKET.get()) {
//                maxRefill = 2;
//            } else if (item == ModItems.FIVESIXTHS_MILK_BUCKET.get()) {
//                maxRefill = 1;
//            } else if (item == ModItems.HALF_MILK_BOTTLE.get()) {
//                maxRefill = 1;
//                isBottle = true;
//                bucketSize = 2;
//            } else if (item == Items.GLASS_BOTTLE) {
//                maxRefill = 2;
//                isBottle = true;
//                bucketSize = 2;
//            }
//
//            if ( currentMilk >= maxRefill) {
//                refillAmount = maxRefill;
//            } else if (currentMilk < maxRefill) {
//                refillAmount = currentMilk;
//            }
//
//            if (!this.level.isClientSide) {
//                int resultingMilkAmount = currentMilk - refillAmount;
//                this.setMilkAmount(resultingMilkAmount);
//
//                float milkBagSize = resultingMilkAmount / (6*maxBagSize);
//
//                this.setBagSize((milkBagSize*(maxBagSize/3.0F))+(maxBagSize*2.0F/3.0F));
//            }
//
//            if (!this.level.isClientSide) {
//                this.setMilkAmount(currentMilk - refillAmount);
//            }
//
//            int resultAmount = bucketSize - maxRefill + refillAmount;
//
//            ItemStack resultItem = new ItemStack(Items.BUCKET);
//
//            switch (resultAmount) {
//                case 0:
//                    entityPlayer.playSound(SoundEvents.SHEEP_HURT, 1.0F, 1.0F);
//                    return InteractionResult.SUCCESS;
//                case 1:
//                    if (isBottle) {
//                        resultItem = new ItemStack(ModItems.HALF_MILK_BOTTLE.get());
//                    } else {
//                        resultItem = new ItemStack(ModItems.ONESIXTH_MILK_BUCKET.get());
//                    }
//                    break;
//                case 2:
//                    if (isBottle) {
//                        resultItem = new ItemStack(ModItems.MILK_BOTTLE.get());
//                    } else {
//                        resultItem = new ItemStack(ModItems.ONETHIRD_MILK_BUCKET.get());
//                    }
//                    break;
//                case 3:
//                    resultItem = new ItemStack(ModItems.HALF_MILK_BUCKET.get());
//                    break;
//                case 4:
//                    resultItem = new ItemStack(ModItems.TWOTHIRDS_MILK_BUCKET.get());
//                    break;
//                case 5:
//                    resultItem = new ItemStack(ModItems.FIVESIXTHS_MILK_BUCKET.get());
//                    break;
//                case 6:
//                    resultItem = new ItemStack(Items.MILK_BUCKET);
//                    break;
//            }
//
//            entityPlayer.playSound(SoundEvents.COW_MILK, 1.0F, 1.0F);
//            itemStack.shrink(1);
//            if (itemStack.isEmpty()) {
//                entityPlayer.setItemInHand(hand, resultItem);
//            } else if (!entityPlayer.getInventory().add(resultItem)) {
//                entityPlayer.drop(resultItem, false);
//            }
//
//        }
//
//        if (!this.level.isClientSide && !hand.equals(InteractionHand.OFF_HAND)) {
//            if (item instanceof AirItem) {
//                int[] genes = this.genetics.getAutosomalGenes();
//                if (!this.isBaby() && (genes[46] == 1 || genes[47] == 1) && this.currentCoatLength == this.maxCoatLength) {
//                        List<ItemStack> woolToDrop = onSheared(entityPlayer,null, this.level, blockPosition(), 0);
//                        woolToDrop.forEach(d -> {
//                            net.minecraft.world.entity.item.ItemEntity ent = this.spawnAtLocation(d, 1.0F);
//                            ent.setDeltaMovement(ent.getDeltaMovement().add((double)((random.nextFloat() - random.nextFloat()) * 0.1F), (double)(random.nextFloat() * 0.05F), (double)((random.nextFloat() - random.nextFloat()) * 0.1F)));
//                        });
//                        onSheared(entityPlayer, ItemStack.EMPTY, this.level, blockPosition(), 0);
//                }
//
//            } else if (item == Items.WATER_BUCKET) {
//                this.setFleeceDyeColour(DyeColor.WHITE);
//            } else if (item instanceof DyeItem) {
//                DyeColor enumdyecolor = ((DyeItem)item).getDyeColor();
//                if (enumdyecolor != this.getFleeceDyeColour()) {
//                    this.setFleeceDyeColour(enumdyecolor);
//                    if (!entityPlayer.getAbilities().instabuild) {
//                        itemStack.shrink(1);
//                    }
//                }
//            }  else if (item instanceof DebugGenesBook) {
//                Minecraft.getInstance().keyboardHandler.setClipboard(this.entityData.get(SHARED_GENES));
//            }
//        }
//        return super.mobInteract(entityPlayer, hand);
//    }
//
//    /**
//     * make a sheep sheared if set to true
//     */
//    public void setSheared(boolean sheared) {
//        byte b0 = this.entityData.get(DYE_COLOUR);
//        if (sheared) {
//            this.entityData.set(DYE_COLOUR, (byte)(b0 | 16));
//        } else {
//            this.entityData.set(DYE_COLOUR, (byte)(b0 & -17));
//        }
//    }
//
//    public void addAdditionalSaveData(CompoundTag compound) {
//        super.addAdditionalSaveData(compound);
//        compound.putByte("Colour", (byte)this.getFleeceDyeColour().getId());
//        compound.putFloat("CoatLength", this.getCoatLength());
//
//        compound.putInt("Lactation", this.lactationTimer);
//
//        compound.putInt("milk", getMilkAmount());
//
//    }
//
//    /**
//     * (abstract) Protected helper method to read subclass entity assets from NBT.
//     */
//    public void readAdditionalSaveData(CompoundTag compound) {
//        super.readAdditionalSaveData(compound);
//
//        this.currentCoatLength = compound.getInt("CoatLength");
//        this.setCoatLength(this.currentCoatLength);
//
//        this.setFleeceDyeColour(DyeColor.byId(compound.getByte("Colour")));
//
//        float milkBagSize = this.getMilkAmount() / (6*this.maxBagSize);
//        this.setBagSize((milkBagSize*(this.maxBagSize/3.0F))+(this.maxBagSize*2.0F/3.0F));
//
//        setMaxCoatLength();
////        configureAI();
//        if (!compound.getString("breed").isEmpty()) {
//            this.currentCoatLength = this.maxCoatLength;
//            this.setCoatLength(this.currentCoatLength);
//        }
//    }
//
//    @Nullable
//    @Override
//    public SpawnGroupData finalizeSpawn(ServerLevelAccessor inWorld, DifficultyInstance difficulty, MobSpawnType spawnReason, @Nullable SpawnGroupData livingdata, @Nullable CompoundTag itemNbt) {
//        return commonInitialSpawnSetup(inWorld, livingdata, getAdultAge(), 60000, 80000, spawnReason);
//    }
//
//    @Override
//    public void setInitialDefaults() {
//        super.setInitialDefaults();
//        setInitialCoat();
//
//        //"White" is considered no dye
//        this.setFleeceDyeColour(DyeColor.WHITE);
//    }
//
//    @Override
//    protected Genes createInitialGenes(LevelAccessor world, BlockPos pos, boolean isDomestic) {
//        return new SheepGeneticsInitialiser().generateNewGenetics(world, pos, isDomestic);
//    }
//
//    @Override
//    public Genes createInitialBreedGenes(LevelAccessor world, BlockPos pos, String breed) {
//        return new SheepGeneticsInitialiser().generateWithBreed(world, pos, breed);
//    }
//
//    public void setInitialCoat() {
//        setMaxCoatLength();
//        this.currentCoatLength = (int)(this.maxCoatLength*(this.getAge() >= this.getAdultAge() ? 1 : ((float)this.getAge()/(float)this.getAdultAge())));
//        setCoatLength(this.currentCoatLength);
//    }
//
//    @Override
//    protected void initializeHealth(EnhancedAnimalAbstract animal, float health) {
////        int[] genes = animal.genetics.getAutosomalGenes();
//
//        health = 8.0F;
//
//        super.initializeHealth(animal, health);
//    }
//
//    @Override
//    public void initilizeAnimalSize() {
//        int[] genes = this.genetics.getAutosomalGenes();
//        float size = 0.4F;
//
//        //(56/57) 1-2 minature [wildtype, minature]
//        //(58/59) 1-16 size genes reducer [wildtype, smaller smaller smallest...] adds milk fat [none to most]
//        //(60/61) 1-16 size genes adder [wildtype, bigger bigger biggest...]
//        //(62/63) 1-3 size genes varient1 [wildtype, smaller, smallest]
//        //(64/65) 1-3 size genes varient2 [wildtype, smaller, smallest]
//
//        size = size - (genes[58] - 1)*0.01F;
//        size = size - (genes[59] - 1)*0.01F;
//        size = size + (genes[60] - 1)*0.0075F;
//        size = size + (genes[61] - 1)*0.0075F;
//
//        if (genes[56] == 2 || genes[57] == 2) {
//            if (genes[56] == 2 && genes[57] == 2) {
//                size = size * 0.8F;
//            } else {
//                size = size * 0.9F;
//            }
//        }
//
//        if (genes[62] == 2 || genes[63] == 2) {
//            size = size * 0.975F;
//        } else if (genes[62] == 3 || genes[63] == 3) {
//            size = size * 0.925F;
//        } else {
//            size = size * 1.025F;
//        }
//
//        if (genes[64] == 2 || genes[65] == 2) {
//            size = size * 1.05F;
//        } else if (genes[64] == 3 || genes[65] == 3) {
//            size = size * 1.1F;
//        }
//
//        if (size > 0.6F) {
//            size = 0.6F;
//        }
//
//        size = size + 0.43F;
//
//        // [ 0.52325 - 1.1 ]
//        this.setAnimalSize(size);
//    }
//
//    private void setMaxCoatLength() {
//        int[] genes = this.genetics.getAutosomalGenes();
//        int maxCoatLength = 0;
//
//        if (genes[20] == 2){
//            maxCoatLength = 1;
//        }
//        if (genes[21] == 2){
//            maxCoatLength = maxCoatLength + 1;
//        }
//        if (genes[22] == 2){
//            maxCoatLength = maxCoatLength + 1;
//        }
//        if (genes[23] == 2){
//            maxCoatLength = maxCoatLength + 1;
//        }
//        if (genes[24] == 2){
//            maxCoatLength = maxCoatLength + 1;
//        }
//        if (genes[25] == 2){
//            maxCoatLength = maxCoatLength + 1;
//        }
//        if (genes[26] == 2){
//            maxCoatLength = maxCoatLength + 1;
//        }
//        if (genes[27] == 2){
//            maxCoatLength = maxCoatLength + 1;
//        }
//        if (genes[28] == 2){
//            maxCoatLength = maxCoatLength + 1;
//        }
//        if (genes[29] == 2){
//            maxCoatLength = maxCoatLength + 1;
//        }
//        if (genes[30] == 2){
//            maxCoatLength = maxCoatLength + 1;
//        }
//        if (genes[31] == 2){
//            maxCoatLength = maxCoatLength + 1;
//        }
//        if (genes[32] == 2){
//            maxCoatLength = maxCoatLength + 1;
//        }
//        if (genes[33] == 2){
//            maxCoatLength = maxCoatLength + 1;
//        }
//        if (genes[34] == 2 && genes[35] == 2){
//            maxCoatLength = maxCoatLength + 1;
//        }
//
//
//        this.maxCoatLength = maxCoatLength > 15 ? 15 : maxCoatLength;
//
//    }
//
////    private void configureAI() {
////        if (!aiConfigured) {
////            Double speed = 1.0D;
////
////            this.goalSelector.addGoal(1, new PanicGoal(this, speed*1.25D));
////            this.goalSelector.addGoal(2, new EnhancedBreedGoal(this, speed));
////            this.goalSelector.addGoal(3, new TemptGoal(this, speed*1.1D, TEMPTATION_ITEMS, false));
////            this.goalSelector.addGoal(4, new FollowParentGoal(this, speed*1.25D));
////            this.goalSelector.addGoal(4, new EnhancedAINurseFromMotherGoal(this, motherUUID, speed*1.1D));
////            grazingGoal = new EnhancedWaterAvoidingRandomWalkingEatingGoal(this, speed, 12, 0.001F, 120, 2);
////            this.goalSelector.addGoal(6, grazingGoal);
////        }
////        aiConfigured = true;
////    }
//}
