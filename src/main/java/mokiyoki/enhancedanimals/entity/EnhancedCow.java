package mokiyoki.enhancedanimals.entity;

import mokiyoki.enhancedanimals.ai.EnhancedEatPlantsGoal;
import mokiyoki.enhancedanimals.ai.general.EnhancedBreedGoal;
import mokiyoki.enhancedanimals.ai.general.EnhancedLookAtGoal;
import mokiyoki.enhancedanimals.ai.general.EnhancedLookRandomlyGoal;
import mokiyoki.enhancedanimals.ai.general.EnhancedPanicGoal;
import mokiyoki.enhancedanimals.ai.general.EnhancedTemptGoal;
import mokiyoki.enhancedanimals.ai.general.EnhancedWanderingGoal;
import mokiyoki.enhancedanimals.ai.general.EnhancedWaterAvoidingRandomWalkingEatingGoal;
import mokiyoki.enhancedanimals.ai.general.GrazingGoal;
import mokiyoki.enhancedanimals.ai.general.SeekShelterGoal;
import mokiyoki.enhancedanimals.ai.general.StayShelteredGoal;
import mokiyoki.enhancedanimals.entity.genetics.CowGeneticsInitialiser;
import mokiyoki.enhancedanimals.entity.util.Colouration;
import mokiyoki.enhancedanimals.init.FoodSerialiser;
import mokiyoki.enhancedanimals.init.ModBlocks;
import mokiyoki.enhancedanimals.init.ModItems;
import mokiyoki.enhancedanimals.config.GeneticAnimalsConfig;
import mokiyoki.enhancedanimals.items.CustomizableSaddleEnglish;
import mokiyoki.enhancedanimals.items.CustomizableSaddleWestern;
import mokiyoki.enhancedanimals.model.modeldata.AnimalModelData;
import mokiyoki.enhancedanimals.model.modeldata.CowModelData;
import mokiyoki.enhancedanimals.util.Genes;
import mokiyoki.enhancedanimals.util.Reference;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.biome.BiomeData;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
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
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
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

import static mokiyoki.enhancedanimals.entity.textures.CowTextureHelper.calculateCowRGB;
import static mokiyoki.enhancedanimals.entity.textures.CowTextureHelper.calculateCowTextures;
import static mokiyoki.enhancedanimals.init.FoodSerialiser.cowFoodMap;
import static mokiyoki.enhancedanimals.init.ModEntities.ENHANCED_COW;

public class EnhancedCow extends EnhancedAnimalRideableAbstract {

    ///avalible UUID spaces : [ S X X X X X 6 7 - 8 9 10 11 - 12 13 14 15 - 16 17 18 19 - 20 21 22 23 24 25 26 27 28 29 30 31 ]
    protected static final EntityDataAccessor<Boolean> RESET_TEXTURE = SynchedEntityData.defineId(EnhancedCow.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<String> MOOSHROOM_UUID = SynchedEntityData.defineId(EnhancedCow.class, EntityDataSerializers.STRING);

    protected boolean resetTexture = true;
    protected String cacheTexture;

    private static final int SEXLINKED_GENES_LENGTH = 2;

    protected boolean aiConfigured = false;

    private String mooshroomUUID = "0";

    protected GrazingGoal grazingGoal;
    private EnhancedWaterAvoidingRandomWalkingEatingGoal wanderEatingGoal;

    @OnlyIn(Dist.CLIENT)
    private CowModelData cowModelData;

    public EnhancedCow(EntityType<? extends EnhancedCow> entityType, Level worldIn) {
        super(entityType, worldIn, SEXLINKED_GENES_LENGTH, Reference.COW_AUTOSOMAL_GENES_LENGTH, true);
        // cowsize from .7 to 1.5 max bag size is 1 to 1.5
        //large cows make from 30 to 12 milk points per day, small cows make up to 1/4
        this.timeUntilNextMilk = this.random.nextInt(600) + Math.round((800 + ((1.5F - this.maxBagSize)*2400)) * (getAnimalSize()/1.5F)) - 300;
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
        ediblePlants.put(Blocks.PUMPKIN, new EnhancedEatPlantsGoal.EatValues(1, 1, 10000));
        ediblePlants.put(Blocks.MELON, new EnhancedEatPlantsGoal.EatValues(1, 1, 10000));

        return ediblePlants;
    }

//    @Override
//    protected void registerGoals() {
//        //Todo add the temperamants
////        this.eatGrassGoal = new EnhancedGrassGoal(this, null);
//        this.goalSelector.addGoal(0, new SwimGoal(this));
////        this.goalSelector.addGoal(5, this.eatGrassGoal);
//        this.goalSelector.addGoal(7, new EnhancedLookAtGoal(this, PlayerEntity.class, 6.0F));
//        this.goalSelector.addGoal(8, new EnhancedLookRandomlyGoal(this));
//    }

    protected void customServerAiStep() {
        this.animalEatingTimer = this.grazingGoal.getEatingGrassTimer();
        super.customServerAiStep();
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(RESET_TEXTURE, false);
        this.entityData.define(MOOSHROOM_UUID, "0");
    }

    @Override
    protected FoodSerialiser.AnimalFoodMap getAnimalFoodType() {
        return cowFoodMap();
    }

    protected String getSpecies() {
        return "entity.eanimod.enhanced_cow";
    }

    @Override
    protected int getAdultAge() { return GeneticAnimalsConfig.COMMON.adultAgeCow.get();}

    //returns how grown the horns are
    public float hornGrowthAmount() {
        int age = this.getEnhancedAnimalAge();
        int hornFullSizedAge = this.getFullSizeAge() * 2;
        return age > hornFullSizedAge ? 1.0F : age/(float)hornFullSizedAge;
    }

    @Override
    protected int gestationConfig() {
        return GeneticAnimalsConfig.COMMON.gestationDaysCow.get();
    }

    protected void setMooshroomUUID(String uuid) {
        if (!uuid.equals("")) {
            this.entityData.set(MOOSHROOM_UUID, uuid);
            this.mooshroomUUID = uuid;
        }
    }

    public String getMooshroomUUID() { return this.mooshroomUUID; }

    public EntityDataAccessor<String> getMooshroomEntityData() {
        return MOOSHROOM_UUID;
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
        return SoundEvents.COW_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) { return SoundEvents.COW_HURT; }

    protected SoundEvent getDeathSound() { return SoundEvents.COW_DEATH; }

    protected void playStepSound(BlockPos pos, BlockState blockIn) {
        super.playStepSound(pos, blockIn);
        this.playSound(SoundEvents.COW_STEP, 0.15F, 1.0F);
        if (!this.isSilent() && this.getBells()) {
            this.playSound(SoundEvents.NOTE_BLOCK_CHIME.get(), 1.5F, 0.1F);
            this.playSound(SoundEvents.NOTE_BLOCK_BELL.get(), 1.0F, 0.1F);
        }
    }

    /**
     * Returns the volume for the sounds this mob makes.
     */
    protected float getSoundVolume() {
        return 0.4F;
    }

    @Override
    public double getPassengersRidingOffset() {
        ItemStack saddleSlot = this.getEnhancedInventory().getItem(1);
        double yPos;
        if (this.getOrSetIsFemale()) {
            //female height
                yPos = 1.0D;
        } else {
            //male height
                yPos = 1.1D;
        }

        float size = this.getAnimalSize();

        if (this.dwarf == -1.0F) {
            Genes sharedGenetics = new Genes(this.entityData.get(SHARED_GENES));
            this.dwarf = sharedGenetics.isHomozygousFor(26, 2) ? 0.0F : 0.2F;
        }

        if (this.dwarf>0) {
            yPos = yPos - this.dwarf;
        }

        if (saddleSlot.getItem() instanceof CustomizableSaddleWestern) {
            yPos = yPos + 0.12D;
        } else if (saddleSlot.getItem() instanceof CustomizableSaddleEnglish) {
            yPos = yPos + 0.06D;
        }

        size = (( 2.0F * size * this.growthAmount()) + size) / 3.0F;

        return yPos*(Math.pow(size, 1.2F));
    }

    @Override
    protected float getJumpHeight() {
        if (this.dwarf > 0 || this.getEnhancedInventory().getItem(0).getItem() == Items.CHEST) {
            return 0.45F;
        } else {
            float jump = 0.48F;
            float size = this.getAnimalSize();
            if (size < 0.9F) {
                return jump + (((size - 0.9F) / 0.2F) * 0.1F);
            }
            return jump;
        }
    }

    @Override
    protected float getJumpFactorModifier() {
        return 0.25F;
    }

    @Override
    protected float getMovementFactorModifier() {
        float speedMod = 1.0F;
        float size = this.getAnimalSize();
        if (size > 1.05F) {
            speedMod = 1.05F/size;
        } else if (size < 1.0F) {
            speedMod = size/1.0F;
        }

        if (this.dwarf > 0) {
            speedMod = speedMod * 0.25F;
        }

        float chestMod = 0.0F;
        ItemStack chestSlot = this.getEnhancedInventory().getItem(0);
        if (chestSlot.getItem() == Items.CHEST) {
            chestMod = (1.0F-((size-0.7F)*1.25F)) * 0.4F;
        }

        return 0.4F + (speedMod * 0.4F) - chestMod;
    }

    public static AttributeSupplier.Builder prepareAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 8.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.23D)
                .add(JUMP_STRENGTH);
    }

    @Override
    public EntityDimensions getDimensions(Pose poseIn) {
        return EntityDimensions.scalable(1.0F, 1.25F).scale(this.getScale());
    }

    @Override
    public float getScale() {
        float size = this.getAnimalSize() > 0.0F ? this.getAnimalSize() : 1.0F;
        size = this.getOrSetIsFemale() ? size : size * 1.1F;
        float newbornSize = 0.65F;
        return this.isGrowing() ? (newbornSize + ((size-newbornSize) * (this.growthAmount()))) : size;
    }

    @Override
    protected void setIsFemale(CompoundTag compound) {
        if (compound.contains("IsFemale")) {
            this.isFemale = compound.getBoolean("IsFemale");
        } else {
            this.isFemale = (this.mooshroomUUID.equals("0") ? this.getStringUUID() : this.mooshroomUUID).toCharArray()[0] - 48 < 8;
        }
    }

    public void aiStep() {
        super.aiStep();

        if (!this.level().isClientSide) {
            if (getEntityStatus().equals(EntityState.MOTHER.toString())) {
                if (hunger <= 24000) {
                    if (--this.timeUntilNextMilk <= 0) {
                        int milk = getMilkAmount();
                        if (milk < (30*(getAnimalSize()/1.5F))*(this.maxBagSize/1.5F)) {
                            milk++;
                            setMilkAmount(milk);
                            this.timeUntilNextMilk = this.random.nextInt(600) + Math.round((800 + ((1.5F - this.maxBagSize)*1200)) * (getAnimalSize()/1.5F)) - 300;

                            //this takes the number of milk points a cow has over the number possible to make a number between 0 and 1.
                            float milkBagSize = milk / (30*(getAnimalSize()/1.5F)*(this.maxBagSize/1.5F));

                            this.setBagSize((1.1F*milkBagSize*(this.maxBagSize-1.0F))+1.0F);

                        }
                    }
                }

                if (this.timeUntilNextMilk <= 0) {
                    this.lactationTimer++;
                } else if (getMilkAmount() <= 5 && this.lactationTimer >= -36000) {
                    this.lactationTimer--;
                }

                if (this.lactationTimer == 0) {
                    this.setMilkAmount(-1);
                    setEntityStatus(EntityState.ADULT.toString());
                }
            }
        }
    }

    @Override
    public boolean sleepingConditional() {
        return (((this.level().getDayTime()%24000 >= 12600 && this.level().getDayTime()%24000 <= 22000) || this.level().isThundering()) && this.awokenTimer == 0 && !this.sleeping);
    }

    protected void initialMilk() {
        this.lactationTimer = -48000;
        //sets milk amount at first milk
        Integer milk = Math.round((30*(getAnimalSize()/1.5F)*(this.maxBagSize/1.5F)) * 0.75F);
        setMilkAmount(milk);

        float milkBagSize = milk / (30*(getAnimalSize()/1.5F)*(this.maxBagSize/1.5F));
        this.setBagSize((1.1F*milkBagSize*(this.maxBagSize-1.0F))+1.0F);
    }

    @Override
    protected void incrementHunger() {
        if(this.sleeping) {
            this.hunger = this.hunger + (1.0F*getHungerModifier());
        } else {
            this.hunger = this.hunger + (2.0F*getHungerModifier());
        }
    }

    @Override
    protected void runExtraIdleTimeTick() {
    }


    protected void createAndSpawnEnhancedChild(Level inWorld) {
        EnhancedCow enhancedcow = ENHANCED_COW.get().create(this.level());
        Genes babyGenes = new Genes(this.genetics).makeChild(this.getOrSetIsFemale(), this.mateGender, this.mateGenetics);
        defaultCreateAndSpawn(enhancedcow, inWorld, babyGenes, -this.getAdultAge());
        enhancedcow.configureAI();

        this.level().addFreshEntity(enhancedcow);
    }

    public void lethalGenes(){
        int[] genes = this.genetics.getAutosomalGenes();
        if(genes[26] == 1 && genes[27] == 1) {
            this.remove(RemovalReason.KILLED);
        }
    }

    @Override
    public boolean shouldDropExperience() { return true; }

    protected void dropCustomDeathLoot(DamageSource source, int looting, boolean recentlyHitIn) {
        super.dropCustomDeathLoot(source, looting, recentlyHitIn);
        int[] genes = this.genetics.getAutosomalGenes();
        this.isOnFire();
        float cowSize = this.getAnimalSize();
        float age = this.getEnhancedAnimalAge(); //overloaded version of getAge
        int cowThickness = (genes[54] + genes[55]);

        int meatDrop;
        float meatChanceMod;
        int leatherDrop;

        //cowsize from .7 to 1.5
        meatChanceMod = ((cowSize - 0.6F) * 4.445F) + 1;
        meatDrop = Math.round(meatChanceMod);
        if (meatDrop >= 5) {
            meatChanceMod = 0;
        } else {
            meatChanceMod = (meatChanceMod - meatDrop) * 100;
        }

        leatherDrop = meatDrop - 2;

        if (meatChanceMod  != 0) {
            int i = this.random.nextInt(100);
            if (meatChanceMod > i) {
                meatDrop++;
            }
            i = this.random.nextInt(100);
            if (meatChanceMod > i) {
                leatherDrop++;
            }
        }

        if (cowThickness == 6){

            //100% chance meat
            //0% chance leather
            meatDrop++;

        } else if (cowThickness == 5){

            //75% chance meat
            int i = this.random.nextInt(4);
            if (i>=1) {
                meatDrop++;
            }
            //25% chance leather
            i = this.random.nextInt(4);
            if (i==0) {
                meatDrop++;
            }

        } else if (cowThickness == 4){

            //50% chance meat
            int i = this.random.nextInt(2);
            if (i==1) {
                meatDrop++;
            }
            //50% chance leather
            i = this.random.nextInt(2);
            if (i==1) {
                leatherDrop++;
            }

        } else if (cowThickness == 3){

            //25% chance meat
            int i = this.random.nextInt(4);
            if (i==0) {
                meatDrop++;
            }
            //75% chance leather
            i = this.random.nextInt(4);
            if (i>=1) {
                leatherDrop++;
            }

        } else {
            leatherDrop++;
        }

        if (age < 84000) {
            if (age > 70000) {
                leatherDrop = leatherDrop - 1;
                meatDrop = meatDrop - 1;
                meatChanceMod = (age-70000)/140;
            } else if (age > 56000) {
                leatherDrop = leatherDrop - 2;
                meatDrop = meatDrop - 2;
                meatChanceMod = (age-56000)/140;
            } else if (age > 42000) {
                leatherDrop = leatherDrop - 3;
                meatDrop = meatDrop - 3;
                meatChanceMod = (age-42000)/140;
            } else if (age > 28000) {
                leatherDrop = 0;
                meatDrop = meatDrop - 4;
                meatChanceMod = (age-28000)/140;
            } else if (age > 14000) {
                leatherDrop = 0;
                meatDrop = meatDrop - 5;
                meatChanceMod = (age-14000)/140;
            } else {
                leatherDrop = 0;
                meatDrop = meatDrop - 6;
                meatChanceMod = age/140;
            }

            int i = this.random.nextInt(100);
            if (meatChanceMod > i) {
                meatDrop++;
            }
        }

        leatherDrop = leatherDrop < 0 ? 0 : leatherDrop;

        meatDrop = meatDrop < 0 ? 0 : meatDrop;

        leatherDrop = (leatherDrop > 5) ? 5 : leatherDrop;

        if (this.isOnFire()){
            ItemStack cookedBeefStack = new ItemStack(Items.COOKED_BEEF, meatDrop);
            this.spawnAtLocation(cookedBeefStack);
        }else {
            ItemStack beefStack = new ItemStack(Items.BEEF, meatDrop);
            ItemStack leatherStack = new ItemStack(Items.LEATHER, leatherDrop);
            this.spawnAtLocation(beefStack);
            this.spawnAtLocation(leatherStack);
        }
    }

    @OnlyIn(Dist.CLIENT)
    public String updateCashe(String current) {
        if (!current.equals(this.cacheTexture)) {
            String old = this.cacheTexture;
            this.cacheTexture = current;
            return old;
        }
        return "";
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public CowModelData getModelData() {
        return this.cowModelData;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void setModelData(AnimalModelData animalModelData) {
        this.cowModelData = (CowModelData) animalModelData;
    }

    @OnlyIn(Dist.CLIENT)
    public String getTexture() {
        if (this.enhancedAnimalTextures.isEmpty()) {
            this.setTexturePaths();
        } else if (this.getReloadTexture() ^ this.reload) {
            this.reload = !this.reload;
            this.reloadTextures();
        }

        return getCompiledTextures("enhanced_cow");

    }

    @Override
    @OnlyIn(Dist.CLIENT)
    protected void reloadTextures() {
        this.texturesIndexes.clear();
        this.enhancedAnimalTextures.clear();
        this.setTexturePaths();
        this.colouration.setMelaninColour(-1);
        this.colouration.setPheomelaninColour(-1);
    }

    @OnlyIn(Dist.CLIENT)
    protected void setTexturePaths() {
        calculateCowTextures(this);
    }

    @Override
    protected void setAlphaTexturePaths() {
    }

    @OnlyIn(Dist.CLIENT)
    public Colouration getRgb() {
        this.colouration = super.getRgb();
        Genes genes = getSharedGenes();

        if (genes != null) {
            calculateCowRGB(this.colouration, genes, this.getOrSetIsFemale());

            if (this.isBaby() && !genes.has(0,0) && this.updateColouration) {
                colouration.setBabyAlpha(this.growthAmount());
                this.updateColouration = false;
            }
        }

        return this.colouration;
    }

    @Override
    protected void setMaxBagSize(){
        int[] genes = this.genetics.getAutosomalGenes();
        float maxBagSize = 0.0F;

        if (this.getOrSetIsFemale() || GeneticAnimalsConfig.COMMON.omnigenders.get()){
            for (int i = 1; i < genes[62]; i++){
                maxBagSize = maxBagSize + 0.01F;
            }
            for (int i = 1; i < genes[63]; i++){
                maxBagSize = maxBagSize + 0.01F;
            }
            for (int i = 1; i < genes[64]; i++){
                maxBagSize = maxBagSize + 0.01F;
            }
            for (int i = 1; i < genes[65]; i++){
                maxBagSize = maxBagSize + 0.01F;
            }


            if (genes[38] == 6){
                maxBagSize = maxBagSize - 0.01F;
            }
            if (genes[39] == 6){
                maxBagSize = maxBagSize - 0.01F;
            }

            if (genes[40] == 1){
                maxBagSize = maxBagSize - 0.01F;
            }
            if (genes[41] == 1){
                maxBagSize = maxBagSize - 0.01F;
            }

            if (genes[50] == 2){
                maxBagSize = maxBagSize - 0.01F;
            }
            if (genes[51] == 2){
                maxBagSize = maxBagSize - 0.01F;
            }

            if (genes[52] == 2 && genes[53] == 2){
                maxBagSize = maxBagSize - 0.02F;
            }

            for (int i = 5; i > genes[66]; i--){
                maxBagSize = maxBagSize + 0.01F;
            }
            for (int i = 5; i > genes[67]; i--){
                maxBagSize = maxBagSize + 0.01F;
            }

            if (genes[54] == 3 && genes[55] == 3){
                maxBagSize = maxBagSize/2.5F;
            }else if (genes[54] == 3 || genes[55] == 3){
                if (genes[54] == 2 || genes[55] == 2){
                    maxBagSize = maxBagSize/2.0F;
                }else{
                    maxBagSize = maxBagSize/1.5F;
                }
            } else if (genes[54] == 2 || genes[55] == 2) {
                if (genes[54] == 2 && genes[55] == 2){
                    maxBagSize = maxBagSize/1.5F;
                }
            } else {
                maxBagSize = maxBagSize * 1.5F;
            }
        }

        // [ 1 to 1.5 ]
        maxBagSize = maxBagSize + 1.0F;
        if (maxBagSize > 1.5F) {
            maxBagSize = 1.5F;
        } else if (maxBagSize < 1.0F) {
            maxBagSize = 1.0F;
        }

        this.maxBagSize = maxBagSize;
    }

    @Override
    public InteractionResult mobInteract(Player entityPlayer, InteractionHand hand) {
        ItemStack itemStack = entityPlayer.getItemInHand(hand);
        Item item = itemStack.getItem();

        if (item == Items.NAME_TAG) {
            itemStack.interactLivingEntity(entityPlayer, this, hand);
            return InteractionResult.SUCCESS;
        }

        if (item == ModItems.ENHANCED_COW_EGG.get()) {
            return InteractionResult.SUCCESS;
        }

        /**
        if (item == Items.BUCKET || item instanceof MixableMilkBucket && !this.isChild() && getEntityStatus().equals(EntityState.MOTHER.toString())) {
            int currentMilk = getMilkAmount();
            int milkColour = 16777215;
            float milkFat = 0.037F;
            ItemStack milkbucket = new ItemStack(ModItems.MIXABLE_MILK);
            int fillamount;
            if (item instanceof MixableMilkBucket) {
                fillamount = ((MixableMilkBucket) milkbucket.getItem()).getSpaceLeft();
                fillamount = currentMilk >= fillamount ? fillamount : currentMilk;
            } else {
                fillamount = currentMilk;
            }

            ((MixableMilkBucket)milkbucket.getItem()).mix(milkbucket, fillamount >= 6 ? 6 : fillamount, milkColour, milkFat, MixableMilkBucket.MilkType.COW, MixableMilkBucket.InoculationType.NONE);
            setMilkAmount(currentMilk - ((MixableMilkBucket)milkbucket.getItem()).getSpaceLeft());

            entityPlayer.playSound(SoundEvents.ENTITY_COW_MILK, 1.0F, 1.0F);
            itemStack.shrink(1);
            if (itemStack.isEmpty()) {
                entityPlayer.setHeldItem(hand, milkbucket);
            } else if (!entityPlayer.inventory.addItemStackToInventory(milkbucket)) {
                entityPlayer.dropItem(milkbucket, false);
            }
        }
         */

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

                float milkBagSize = resultingMilkAmount / (30*(getAnimalSize()/1.5F)*(this.maxBagSize/1.5F));

                this.setBagSize((1.1F*milkBagSize*(this.maxBagSize-1.0F))+1.0F);
            }

            int resultAmount = bucketSize - maxRefill + refillAmount;

            ItemStack resultItem = new ItemStack(Items.BUCKET);

            switch (resultAmount) {
                case 0:
                    entityPlayer.playSound(SoundEvents.COW_HURT, 1.0F, 1.0F);
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

            return InteractionResult.SUCCESS;
        }

        return super.mobInteract(entityPlayer, hand);
    }

    protected void dropEquipment() {
        super.dropEquipment();
    }

    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);

        compound.putString("MooshroomID", getMooshroomUUID());

//        compound.putBoolean("Saddle", this.getSaddled());

    }

    /**
     * (abstract) Protected helper method to read subclass entity assets from NBT.
     */
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);

        setMooshroomUUID(compound.getString("MooshroomID"));

        //this takes the number of milk points a cow has over the number possible to make a number between 0 and 1.
        float milkBagSize = this.getMilkAmount() / (30*(getAnimalSize()/1.5F)*(this.maxBagSize/1.5F));
        this.setBagSize((1.1F*milkBagSize*(this.maxBagSize-1.0F))+1.0F);

        this.dwarf = this.genetics.isHomozygousFor(26, 2) ? 0.0F : 0.2F;

        configureAI();
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor inWorld, DifficultyInstance difficulty, MobSpawnType spawnReason, @Nullable SpawnGroupData livingdata, @Nullable CompoundTag itemNbt) {
        return commonInitialSpawnSetup(inWorld, livingdata, getAdultAge(), 64800, 108000, spawnReason);
    }

    @Override
    public void setInitialDefaults() {
        super.setInitialDefaults();
        configureAI();
    }

    @Override
    protected Genes createInitialGenes(LevelAccessor world, BlockPos pos, boolean isDomestic) {
        return new CowGeneticsInitialiser().generateNewGenetics(world, pos, isDomestic);
    }

    @Override
    public Genes createInitialBreedGenes(LevelAccessor world, BlockPos pos, String breed) {
        return new CowGeneticsInitialiser().generateWithBreed(world, pos, breed);
    }

    @Override
    protected void initializeHealth(EnhancedAnimalAbstract animal, float health) {
        float sizeVar = 1.0F - (Math.abs(1F - animal.getAnimalSize()));
        health = 10 * sizeVar;
        super.initializeHealth(animal, health);
    }

    @Override
    public void initilizeAnimalSize() {
        int[] genes = this.genetics.getAutosomalGenes();
        float size = 1.0F;

        //[ 1 to 15 ] max - is 0.3F
        for (int i = 1; i < genes[30]; i++){
            size = size - 0.01F;
        }
        //[ 1 to 15 ]
        for (int i = 1; i < genes[31]; i++){
            //this variation is here on purpose
            size = size - 0.012F;
        }

        //[ 1 to 15 ]  max + is 0.3F
        for (int i = 1; i < genes[32]; i++){
            size = size + 0.016F;
        }
        //[ 1 to 15 ]
        for (int i = 1; i < genes[33]; i++){
            size = size + 0.016F;
        }

        //[ 1 to 3 ] max - is 0.04F
        if (genes[34] >= 2 || genes[35] >= 2){
            if (genes[34] == 3 && genes[35] == 3){
                size = size - 0.02F;
            }else{
                size = size - 0.01F;
            }
        }
        //[ 1 to 3 ]
        if (genes[34] == 1 || genes[35] == 1){
            size = size - 0.02F;
        }else if (genes[34] == 2 || genes[35] == 2){
            size = size - 0.01F;
        }

        if (genes[26] == 1 || genes[27] == 1){
            //dwarf
            size = size/1.05F;
        }
        if (genes[28] == 2 && genes[29] == 2){
            //miniature
            size = size/1.1F;
        }

        if (size < 0.7F){
            size = 0.7F;
        }

        this.setAnimalSize(size);
    }

    protected void configureAI() {
        if (!aiConfigured) {
            Double speed = 1.0D;

//            if (this.cowSize > 1.2F) {
//                speed++;
//                speed = speed + 0.1;
//            }
//
//            if (this.cowSize < 0.8F) {
//                speed = speed - 0.1;
//            }
//
//            int bodyShape = 0;
//
//            for (int i = 1; i < genes[54]; i++){
//                bodyShape++;
//            }
//            for (int i = 1; i < genes[55]; i++){
//                bodyShape++;
//            }
//
//            if (genes[26] == 1 || genes[27] == 1) {
//                speed = speed *0.9;
//            }
//
//            if (bodyShape == 4) {
//                speed = speed - 0.1;
//            }
            this.wanderEatingGoal = new EnhancedWaterAvoidingRandomWalkingEatingGoal(this, 1.0D, 7, 0.001F, 120, 2, 50);
            this.goalSelector.addGoal(0, new FloatGoal(this));
            this.goalSelector.addGoal(1, new EnhancedPanicGoal(this, speed*1.5D));
            this.goalSelector.addGoal(2, new EnhancedBreedGoal(this, speed));
            this.goalSelector.addGoal(3, new EnhancedTemptGoal(this, speed, speed*1.25D, false, Items.CARROT_ON_A_STICK));
            this.goalSelector.addGoal(3, new EnhancedTemptGoal(this, speed,speed*1.25D, false, Items.AIR));
//            this.goalSelector.addGoal(4, new EnhancedFollowParentGoal(this, this.parent,speed*1.25D));
//            this.goalSelector.addGoal(4, new EnhancedAINurseFromMotherGoal(this, this.parent, speed*1.25D));
            this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.1D));
            this.goalSelector.addGoal(5, new StayShelteredGoal(this, 5723, 7000, 0));
            this.goalSelector.addGoal(6, new SeekShelterGoal(this, 1.0D, 5723, 7000, 0));
//            grazingGoal = new EnhancedWaterAvoidingRandomWalkingEatingGoal(this, speed, 7, 0.001F, 120, 2, 20);
            this.goalSelector.addGoal(7, new EnhancedEatPlantsGoal(this, createGrazingMap()));
            this.grazingGoal = new GrazingGoal(this, speed);
            this.goalSelector.addGoal(8, grazingGoal);
            this.goalSelector.addGoal(9, this.wanderEatingGoal);
            this.goalSelector.addGoal(10, new EnhancedWanderingGoal(this, speed));
            this.goalSelector.addGoal(11, new EnhancedLookAtGoal(this, Player.class, 6.0F));
            this.goalSelector.addGoal(12, new EnhancedLookAtGoal(this, EnhancedAnimalAbstract.class, 6.0F));
            this.goalSelector.addGoal(13, new EnhancedLookRandomlyGoal(this));
        }
        aiConfigured = true;
    }

}
