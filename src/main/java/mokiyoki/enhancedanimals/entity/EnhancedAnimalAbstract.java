package mokiyoki.enhancedanimals.entity;

import mokiyoki.enhancedanimals.EnhancedAnimals;
import mokiyoki.enhancedanimals.ai.general.AIStatus;
import mokiyoki.enhancedanimals.config.EanimodCommonConfig;
import mokiyoki.enhancedanimals.entity.util.Colouration;
import mokiyoki.enhancedanimals.entity.util.Equipment;
import mokiyoki.enhancedanimals.gui.EnhancedAnimalContainer;
import mokiyoki.enhancedanimals.init.FoodSerialiser;
import mokiyoki.enhancedanimals.init.ModItems;
import mokiyoki.enhancedanimals.items.CustomizableAnimalEquipment;
import mokiyoki.enhancedanimals.items.CustomizableBridle;
import mokiyoki.enhancedanimals.items.CustomizableCollar;
import mokiyoki.enhancedanimals.items.CustomizableSaddleEnglish;
import mokiyoki.enhancedanimals.items.CustomizableSaddleVanilla;
import mokiyoki.enhancedanimals.items.CustomizableSaddleWestern;
import mokiyoki.enhancedanimals.items.DebugGenesBook;
import mokiyoki.enhancedanimals.network.EAEquipmentPacket;
import mokiyoki.enhancedanimals.util.EnhancedAnimalInfo;
import mokiyoki.enhancedanimals.util.Genes;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.decoration.LeashFenceKnotEntity;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerListener;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionResult;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.core.NonNullList;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PacketDistributor;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public abstract class EnhancedAnimalAbstract extends Animal implements ContainerListener {

    protected static final EntityDataAccessor<String> SHARED_GENES = SynchedEntityData.defineId(EnhancedAnimalAbstract.class, EntityDataSerializers.STRING);
    protected static final EntityDataAccessor<Boolean> SLEEPING = SynchedEntityData.defineId(EnhancedAnimalAbstract.class, EntityDataSerializers.BOOLEAN);
    protected static final EntityDataAccessor<Boolean> TAMED = SynchedEntityData.defineId(EnhancedAnimalAbstract.class, EntityDataSerializers.BOOLEAN);
    protected static final EntityDataAccessor<String> BIRTH_TIME = SynchedEntityData.defineId(EnhancedAnimalAbstract.class, EntityDataSerializers.STRING);
    private static final EntityDataAccessor<Float> ANIMAL_SIZE = SynchedEntityData.defineId(EnhancedAnimalAbstract.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<String> ENTITY_STATUS = SynchedEntityData.defineId(EnhancedAnimalAbstract.class, EntityDataSerializers.STRING);
    private static final EntityDataAccessor<Float> BAG_SIZE = SynchedEntityData.defineId(EnhancedAnimalAbstract.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Integer> MILK_AMOUNT = SynchedEntityData.defineId(EnhancedAnimalAbstract.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> HAS_COLLAR = SynchedEntityData.defineId(EnhancedAnimalAbstract.class, EntityDataSerializers.BOOLEAN);
    protected static final EntityDataAccessor<Boolean> RESET_TEXTURE = SynchedEntityData.defineId(EnhancedAnimalAbstract.class, EntityDataSerializers.BOOLEAN);

    private final NonNullList<ItemStack> equipmentArray = NonNullList.withSize(7, ItemStack.EMPTY);

    protected static final String CACHE_DELIMITER = "-";

    private static final String COLLAR = "d_collar.png";
    private static final String COLLAR_TEXTURE = "collar_leather.png";
    private static final String[] COLLAR_HARDWARE = new String[] {
            "collar_ringiron.png", "collar_ringgold.png", "collar_ringdiamond.png",
            "collar_belliron.png", "collar_bellgold.png", "collar_belldiamond.png"
    };

    private static final Ingredient MILK_ITEMS = Ingredient.of(ModItems.MILK_BOTTLE, ModItems.HALF_MILK_BOTTLE);


    // Genetic Info
    protected Genes genetics;
    protected Genes mateGenetics;
    protected Boolean mateGender;
    protected Genes genesSplitForClient;
    protected static final int WTC = EanimodCommonConfig.COMMON.wildTypeChance.get();
    public String breed = "";
    protected String mateName = "???";
    protected String sireName = "???";
    protected String damName = "???";
    protected Boolean isFemale;

    //Hunger
    protected float hunger = 0F;
    protected int healTicks = 0;
    protected boolean bottleFeedable = false;
    protected int animalEatingTimer;
    public int eatingTicks;
    protected AIStatus currentAIStatus = AIStatus.NONE;

    //Sleeping
    protected boolean sleeping = false;
    protected int awokenTimer = 0;

    //Animations
    private int blink = 0;
    private int earTwitchTicker = 0;
    private int earTwitch = 0;

    //Parent
//    protected EnhancedAnimalAbstract parent = null;
    protected String motherUUID = "";

    //Pregnancy
    protected int gestationTimer = 0;
    protected boolean pregnant = false;

    //Lactation
    protected int lactationTimer = 0;
    protected float maxBagSize = 1;
    protected int timeUntilNextMilk;

    //Texture
    protected final List<String> enhancedAnimalTextures = new ArrayList<>();
    protected final List<String> texturesIndexes = new ArrayList<>();
    protected String compiledTexture;
    protected final List<String> enhancedAnimalAlphaTextures = new ArrayList<>();
    protected String compiledAlphaTexture;
    protected final Map<Equipment, List<String>> equipmentTextures = new HashMap<>();
    protected String compiledEquipmentTexture;
    public Colouration colouration = new Colouration();
    protected boolean bells;
    protected Boolean reload = true; //used in a toggle manner

    //Inventory
    protected SimpleContainer animalInventory;

    //Size
    private int reloadSizeTime = 0;

    //Overrides
    @Nullable
    private CompoundTag leashNBTTag;

    /*
    Entity Construction
    */

    protected EnhancedAnimalAbstract(EntityType<? extends EnhancedAnimalAbstract> type, Level worldIn, int SgenesSize, int AgenesSize, boolean bottleFeedable) {
        super(type, worldIn);
        this.genetics = new Genes(new int[SgenesSize], new int[AgenesSize]);
        this.mateGenetics = new Genes(new int[SgenesSize], new int[AgenesSize]);
        this.mateGender = false;
        this.bottleFeedable = bottleFeedable;

        initInventory();
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(SHARED_GENES, new String());
        this.entityData.define(SLEEPING, false);
        this.entityData.define(TAMED, false);
        this.entityData.define(ENTITY_STATUS, new String());
        this.entityData.define(BIRTH_TIME, "0");
        this.entityData.define(ANIMAL_SIZE, 0.0F);
        if (canLactate()) {
            this.entityData.define(BAG_SIZE, 0.0F);
            this.entityData.define(MILK_AMOUNT, 0);
        }
        this.entityData.define(HAS_COLLAR, false);
        this.entityData.define(RESET_TEXTURE, false);
    }

    @Override
    protected void registerGoals() { }

    protected void setMateName(String mateName) {
        if (mateName!=null && !mateName.equals("")) {
            this.mateName = mateName;
        } else {
            this.mateName = "???";
        }
    }

    public void setSireName(String sireName) {
        if (sireName!=null && !sireName.equals("")) {
            this.sireName = sireName;
        } else {
            this.sireName = "???";
        }
    }

    public void setDamName(String damName) {
        if (damName!=null && !damName.equals("")) {
            this.damName = damName;
        } else {
            this.damName = "???";
        }
    }

    protected void setBreed(String breed) {
        this.breed = breed;
    }

    /*
    Animal expected abstracts and overrides
    */

    //returns the species name in the selected language
    protected abstract String getSpecies();

    //returns the age an animal becomes an adult
    protected abstract int getAdultAge();

    //returns the age when the animal finishes growing
    protected int getFullSizeAge() {
        return getAdultAge();
    }

    //returns if the animal is still growing
    protected boolean isGrowing() {
        return this.getAge()<(float)this.getFullSizeAge();
    }

    //returns how grown the animal is
    public float growthAmount() {
        return this.getAge() > this.getFullSizeAge() ? 1.0F : this.getAge()/(float)this.getFullSizeAge();
    }

    //returns the config for the animals gestation or any overrides
    protected abstract int gestationConfig();

    //the amount hunger is incremented
    protected abstract void incrementHunger();

    //anything extra to run during not idling
    protected abstract void runExtraIdleTimeTick();

    //any lethal genes checks the animal has
    protected abstract void lethalGenes();

    //when the animal wakes up
    public boolean sleepingConditional() {
        return (!this.level.isDay() && this.awokenTimer == 0 && !this.sleeping);
    }

    //toggles the reloading
    protected void toggleReloadTexture() {
        this.entityData.set(RESET_TEXTURE, !this.getReloadTexture());
    }

    public boolean getReloadTexture() {
        return this.entityData.get(RESET_TEXTURE);
    }

    @OnlyIn(Dist.CLIENT)
    public abstract String getTexture();

    @OnlyIn(Dist.CLIENT)
    protected void reloadTextures() {
        this.texturesIndexes.clear();
        this.enhancedAnimalTextures.clear();
        this.compiledTexture = null;
        this.setTexturePaths();
    }

    //for setting the textures
    @OnlyIn(Dist.CLIENT)
    protected abstract void setTexturePaths();

    //for setting the alpha textures
    @OnlyIn(Dist.CLIENT)
    protected abstract void setAlphaTexturePaths();

    //called during construction to set up the animal size
    public abstract void initilizeAnimalSize();

    //method to create the new child
    protected abstract void createAndSpawnEnhancedChild(Level world);

    //used to set if an animal runs the pregnancy code
    protected abstract boolean canBePregnant();

    //used to set if an animal can producte milk
    protected abstract boolean canLactate();


    //used to set if an animal can have a saddle
    public boolean canHaveSaddle() {
        return false;
    }

    //used to set if an animal can have a bridle
    public boolean canHaveBridle() {
        return false;
    }

    //used to set if an animal can have armour
    public boolean canHaveArmour() {
        return false;
    }

    //used to set if an animal can have a blanket
    public boolean canHaveBlanket() {
        return false;
    }

    //used to set if an animal can have a banner
    public boolean canHaveBanner() {
        return false;
    }

    //used to set if an animal can have a harness
    public boolean canHaveHarness() {
        return false;
    }

    //used to set if an animal can have a chest
    public boolean canHaveChest() {
        return false;
    }

    /*
    Getters and Setters for variables and datamanagers
    */

    protected abstract FoodSerialiser.AnimalFoodMap getAnimalFoodType();

    public void setBirthTime() {
        this.setBirthTime(String.valueOf(this.level.getLevelData().getGameTime()));
    }

    public void setBirthTime(Level world, int age) {
        this.setBirthTime(String.valueOf(world.getLevelData().getGameTime() + age));
    }

    public void setBirthTime(String birthTime) {
        this.entityData.set(BIRTH_TIME, birthTime);
    }

    public String getBirthTime() {
        return this.entityData.get(BIRTH_TIME);
    }

//    private void setParent(String motherUUID) {
//        if (this.isChild()) {
//            List<EnhancedAnimalAbstract> list = this.world.getEntitiesWithinAABB(this.getClass(), this.getBoundingBox().grow(8.0D, 4.0D, 8.0D));
//            EnhancedAnimalAbstract animalentity = null;
//            double d0 = Double.MAX_VALUE;
//
//            for (EnhancedAnimalAbstract animalentity1 : list) {
//                if (animalentity1.getUniqueID().equals(motherUUID)) {
//                    double d1 = this.getDistanceSq(animalentity1);
//                    if (!(d1 > d0)) {
//                        d0 = d1;
//                        animalentity = animalentity1;
//                    }
//                }
//            }
//
//            if (!(animalentity == null || d0 < 2.0D)) {
//                if (animalentity instanceof EnhancedAnimalAbstract) {
//                    this.parent = (EnhancedAnimalAbstract) animalentity;
//                }
//            }
//        }
//    }

    protected void setParent(String parentUUID) {
//        this.parent = parent;
        this.motherUUID = parentUUID;
    }

//    private EnhancedAnimalAbstract getParent() {
//        return this.parent;
//    }

    protected void setEntityStatus(String status) {
        this.entityData.set(ENTITY_STATUS, status);
    }

    public String getEntityStatus() {
        return this.entityData.get(ENTITY_STATUS);
    }

    public void setSleeping(Boolean sleeping) {
        this.sleeping = sleeping;
        this.entityData.set(SLEEPING, sleeping); }

    public Boolean isAnimalSleeping() {
        if (this.isInWaterRainOrBubble()) {
            return false;
        } else if (!(this.getLeashHolder() instanceof LeashFenceKnotEntity) && this.getLeashHolder() != null) {
            return false;
        } else {
            return this.entityData.get(SLEEPING);
        }
    }

    public void awaken() {
        this.awokenTimer = 200;
        setSleeping(false);
    }

    protected void setAnimalSize(float size) {
        this.entityData.set(ANIMAL_SIZE, size);
    }

    public float getAnimalSize() {
        return this.entityData.get(ANIMAL_SIZE);
    }

    public float getHunger(){
        return this.hunger;
    }

    public void decreaseHunger(float decrease) {
        if (this.hunger - decrease < 0) {
            this.hunger = 0;
        } else {
            this.hunger = this.hunger - decrease;
        }
    }

    protected float getHungerModifier() {
        return EanimodCommonConfig.COMMON.hungerScaling.get().hungerScalingValue;
    }

    public AIStatus getAIStatus() {
        return this.currentAIStatus;
    }

    public void setAIStatus(AIStatus aiStatus) {
        this.currentAIStatus = aiStatus;
    }

    public int getHungerLimit() {
        return 2000;
    }

    public boolean isTame() {
        return this.entityData.get(TAMED);
    }

    public void setTame(boolean tame) {
        this.entityData.set(TAMED, tame);
    }

    public int getAge() {
        if (!(getBirthTime() == null) && !getBirthTime().equals("") && !getBirthTime().equals(0)) {
            return (int)(this.level.getLevelData().getGameTime() - Long.parseLong(getBirthTime()));
        } else {
            setBirthTime(String.valueOf(this.level.getLevelData().getGameTime() - this.getAdultAge()));
            return this.getAdultAge();
        }
    }

    protected void setBagSize(float size) { this.entityData.set(BAG_SIZE, size); }

    public float getBagSize() {
        return this.entityData.get(BAG_SIZE);
    }

    protected void setMilkAmount(Integer milkAmount) {
        this.entityData.set(MILK_AMOUNT, milkAmount);
    }

    public Integer getMilkAmount() { return this.entityData.get(MILK_AMOUNT); }

    public boolean decreaseMilk(int decrease) {
        int milk = getMilkAmount();
        if (milk >= decrease) {
            milk = milk - decrease;
            setMilkAmount(milk);
            return true;
        } else {
//            entityPlayer.playSound(SoundEvents.ENTITY_COW_HURT, 1.0F, 1.0F);
            return false;
        }
    }

    protected void setMaxBagSize(){ }

    public void setGenes(int[] sgenes, int[] agenes) {
        this.genetics.setGenes(sgenes, agenes);
    }

    public void setGenes(Genes genes) {
        this.genetics.setGenes(genes);
    }

    public Genes getGenes(){
        return this.genetics;
    }

    @OnlyIn(Dist.CLIENT)
    public int getBlink() {
        return this.blink;
    }

    @OnlyIn(Dist.CLIENT)
    public float getEarTwitch() {
        int timer = this.earTwitchTicker;
        boolean left = true;
        if (timer < 0) {
            timer = -timer;
            left = false;
        }
        if (timer < 40) {
           if (timer > 30) {
               this.earTwitch--;
           } else if (timer > 20) {
               this.earTwitch++;
           } else if (timer > 10) {
               this.earTwitch--;
           } else {
               this.earTwitch++;
           }
        }

        if (left) {
            return ((this.earTwitch*0.15F) + 10);
        } else {
            return ((this.earTwitch*0.15F) - 10);
        }

    }

    public boolean hasCollar() {
        return this.entityData.get(HAS_COLLAR);
    }

    public void setCollar(boolean collared) {
        this.entityData.set(HAS_COLLAR, collared);
        List<String> previousCollarTextures = this.equipmentTextures.get(Equipment.COLLAR);
        List<String> newCollarTextures = getCollarTextures();

        if(collared) {
            if(previousCollarTextures == null || !previousCollarTextures.containsAll(newCollarTextures)){
                this.equipmentTextures.put(Equipment.COLLAR, newCollarTextures);
            }
        } else {
            if(previousCollarTextures != null){
                this.equipmentTextures.remove(Equipment.COLLAR);
            }
        }
        this.compiledEquipmentTexture = null; //reset compiled string
    }
    //used to set if an animal is wearing bells

    protected boolean getBells() {
        return this.bells;
    }
    /*
    General Info
    */

    public boolean getOrSetIsFemale() {
        if (this.isFemale == null) {
            return this.isFemale = getStringUUID().toCharArray()[0] - 48 < 8;
        }
        return this.isFemale;
    }

    protected void setIsFemale(CompoundTag compound) {
        this.isFemale = compound.contains("IsFemale") ? compound.getBoolean("IsFemale") : getStringUUID().toCharArray()[0] - 48 < 8;
    }

    /*
    Tick
    */

    @Override
    public void tick() {
        super.tick();
        if (!this.level.isClientSide) {
            for(int invSlot = 0; invSlot < 7; invSlot++) {
                ItemStack inventoryItemStack = this.animalInventory.getItem(invSlot);
                ItemStack equipmentItemStack = equipmentArray.get(invSlot);

                if (!ItemStack.matches(inventoryItemStack, equipmentItemStack)) {
                    if (!inventoryItemStack.equals(equipmentItemStack, true)) {
                        EAEquipmentPacket equipmentPacket = new EAEquipmentPacket(this.getId(), invSlot, inventoryItemStack);
                        EnhancedAnimals.channel.send(PacketDistributor.TRACKING_ENTITY.with(() -> this), equipmentPacket);
                    }
                    this.equipmentArray.set(invSlot, inventoryItemStack.copy());
                }
            }
        }
    }

    @Override
    protected void tickLeash() {
        if (this.leashNBTTag != null) {
            super.tickLeash();
            leashNBTTag = null;
        }

        if (this.getLeashHolder() != null) {
            if (!this.isAlive() || !this.getLeashHolder().isAlive()) {
                this.dropLeash(true, true);
            }
        }

        Entity entity = this.getLeashHolder();
        if (entity != null && entity.level == this.level) {
            this.restrictTo(new BlockPos(entity.blockPosition()), 5);
            float f = this.distanceTo(entity);

            this.onLeashDistance(f);
            if (f > 10.0F) {
                this.dropLeash(true, true);
                this.goalSelector.disableControlFlag(Goal.Flag.MOVE);
            } else if (f > 6.0F) {
                double d0 = (entity.getX() - this.getX()) / (double)f;
                double d1 = (entity.getY() - this.getY()) / (double)f;
                double d2 = (entity.getZ() - this.getZ()) / (double)f;
                this.setDeltaMovement(this.getDeltaMovement().add(Math.copySign(d0 * d0 * 0.4D, d0), Math.copySign(d1 * d1 * 0.4D, d1), Math.copySign(d2 * d2 * 0.4D, d2)));
            } else if (!ableToMoveWhileLeashed()){
                this.goalSelector.enableControlFlag(Goal.Flag.MOVE);
                Vec3 vec3d = (new Vec3(entity.getX() - this.getX(), entity.getY() - this.getY(), entity.getZ() - this.getZ())).normalize().scale((double)Math.max(f - 2.0F, 0.0F));
                this.getNavigation().moveTo(this.getX() + vec3d.x, this.getY() + vec3d.y, this.getZ() + vec3d.z, this.followLeashSpeed());
            }
        }
    }

    protected boolean ableToMoveWhileLeashed() {
        return !(this.getLeashHolder() instanceof LivingEntity);
    }

    /*
    Living Tick
    */

    @Override
    public void aiStep() {
        super.aiStep();

        if (this.reloadSizeTime != 0) {
            if (this.isGrowing()) {
                if (this.getAge() % this.reloadSizeTime == 0) {
                    this.refreshDimensions();
                }
            } else {
                this.reloadSizeTime = 0;
            }
        }

        //run client-sided tick stuff
        if (this.level.isClientSide) {
            runLivingTickClient();

//            if (this.serializeNBT().contains("OpenEnhancedAnimalRidenGUI")) {
//                if (this.isBeingRidden()) {
//                    if (this.getRidingEntity() instanceof PlayerEntity) {
//                        this.openGUI((PlayerEntity)this.getRidingEntity());
//                        this.removeTag("OpenEnhancedAnimalRidenGUI");
//                    }
//                }
//            }
        } else {
//            //run server-sided tick stuff
//            if (this.serializeNBT().contains("OpenEnhancedAnimalRidenGUI")) {
//                if (this.isBeingRidden()) {
//                    if (this.getRidingEntity() instanceof PlayerEntity) {
//                        this.openGUI((PlayerEntity)this.getRidingEntity());
//                        this.removeTag("OpenEnhancedAnimalRidenGUI");
//                    }
//                }
//            }

            if (sleepingConditional()) {
                setSleeping(true);
                this.healTicks = 0;
            } else if (awokenTimer > 0) {
                this.awokenTimer--;
            } else if (this.level.isDay() && this.sleeping) {
                setSleeping(false);
            }

            //not outside AI processing distance
            if (this.getNoActionTime() < 100) {
                if (this.hunger <= 72000) {
                    if (this.sleeping) {
                        if ((this.hunger <= (gestationConfig()/2))) {
                            incrementHunger();
                        }
                        this.healTicks++;
                        if (this.healTicks > 100 && hunger < 6000 && this.getMaxHealth() > this.getHealth()) {
                            this.heal(2.0F);
                            this.hunger = this.hunger + 1000;
                            this.healTicks = 0;
                        }
                    } else {
                        incrementHunger();
                    }
                }
                runExtraIdleTimeTick();
            }

            runPregnancyTick();

            lethalGenes();
        }
    }

    protected void runLivingTickClient() {
        this.animalEatingTimer = Math.max(0, this.animalEatingTimer - 1);

        boolean asleep = isAnimalSleeping();
        //blinking animation timer
        if (asleep) {
            this.blink = 1;
        } else {
            if (this.blink == 0) {
                this.blink = random.nextInt(400);
            } else {
                this.blink--;
            }
        }

        //ear twitch timer
        if (this.earTwitchTicker == 0) {
            if (asleep) {
                this.earTwitchTicker = random.nextInt(2400);
            } else {
                this.earTwitchTicker = random.nextInt(1200);
            }
            if (random.nextBoolean()) {
                this.earTwitchTicker = -this.earTwitchTicker;
            }
        } else if (this.earTwitchTicker > 0) {
            this.earTwitchTicker--;
        } else {
            this.earTwitchTicker++;
        }

    }

    protected void runPregnancyTick() {
        if(this.pregnant) {
            this.gestationTimer++;
            int days = gestationConfig();
            if (this.gestationTimer > days + 1200) {
                this.setAge(600);
            }
            if (days/2 < this.gestationTimer) {
                setEntityStatus(EntityState.PREGNANT.toString());
            }
//            if (this.hunger > getPregnancyHungerLimit() && days !=0) {
//                this.pregnant = false;
//                this.gestationTimer = 0;
//                setEntityStatus(EntityState.ADULT.toString());
//            }
            if (this.gestationTimer >= days) {
                this.pregnant = false;
                this.gestationTimer = 0;
                setEntityStatus(EntityState.MOTHER.toString());

                if (canLactate()) {
                    initialMilk();
                }

                int numberOfChildren = getNumberOfChildren();

                for (int i = 0; i < numberOfChildren; i++) {
//                    mixMateMitosisGenes();
//                    mixMitosisGenes();
                    createAndSpawnEnhancedChild(this.level);
                }

                if (this.level.getGameRules().getBoolean(GameRules.RULE_DOMOBLOOT)) {
                    int i = 1;
                    while (i > 0) {
                        int j = ExperienceOrb.getExperienceValue(i);
                        i -= j;
                        this.level.addFreshEntity(new ExperienceOrb(this.level, this.getX(), this.getY(), this.getZ(), j));
                    }
                }
            }
        }
    }

    protected float getPregnancyHungerLimit() {
        return 12000F;
    }

    protected void initialMilk() {
        lactationTimer = -48000;
        setMilkAmount(4);

        float milkBagSize = 4 / (6*this.maxBagSize);

        this.setBagSize((milkBagSize*(this.maxBagSize/3.0F))+(this.maxBagSize*2.0F/3.0F));
    }

    protected int getNumberOfChildren() {
        return 1;
    }

    public boolean isPlantEaten() {
        return this.eatingTicks == 0;
    }

    /*
    Animal interaction from player and other entities
    */

    @Override
    public InteractionResult mobInteract(Player entityPlayer, InteractionHand hand) {
        ItemStack itemStack = entityPlayer.getItemInHand(hand);
        Item item = itemStack.getItem();
        if (entityPlayer.isSecondaryUseActive()) {
            this.openGUI(entityPlayer);
            return InteractionResult.sidedSuccess(this.level.isClientSide);
        }

        if (!this.level.isClientSide && !hand.equals(InteractionHand.OFF_HAND)) {
            boolean isChild = this.isBaby();
            if (item instanceof DebugGenesBook) {
                Minecraft.getInstance().keyboardHandler.setClipboard(this.entityData.get(SHARED_GENES));
            } else if (!isChild && isBreedingItem(itemStack)) {
                if (this.hunger >= 4000 || (!this.pregnant && !isChild && this.canFallInLove())) {
                    decreaseHunger(getHungerRestored(itemStack));
                    shrinkItemStack(entityPlayer, itemStack);
                } else {
                    return InteractionResult.PASS;
                }
                if (!this.level.isClientSide && !isChild && this.canFallInLove()) {
                    this.setInLove(entityPlayer);
                    return InteractionResult.SUCCESS;
                }
            } else if (isChild && (isBreedingItem(itemStack)) || (this.bottleFeedable && MILK_ITEMS.test(itemStack))) {
                if (this.hunger >= 4000 || EanimodCommonConfig.COMMON.feedGrowth.get()) {
                    boolean isHungry = this.hunger >= 4000;
                    if (EanimodCommonConfig.COMMON.feedGrowth.get()) {
                        this.ageUp((int) ((float) (-this.getAge() / 20) * 0.1F), true);
                    }
                    if (MILK_ITEMS.test(itemStack)) {
                        if (item == ModItems.HALF_MILK_BOTTLE) {
                            if (isHungry) {
                                decreaseHunger(6000);
                            }
                            swapItemStack(entityPlayer, hand, itemStack, Items.GLASS_BOTTLE);
                        } else if (item == ModItems.MILK_BOTTLE) {
                            if (hunger >= 12000) {
                                decreaseHunger(12000);
                                swapItemStack(entityPlayer, hand, itemStack, Items.GLASS_BOTTLE);
                            } else {
                                if (isHungry) {
                                    decreaseHunger(6000);
                                }
                                swapItemStack(entityPlayer, hand, itemStack, ModItems.HALF_MILK_BOTTLE);
                            }
                        }
                    } else {
                        if (isHungry) {
                            decreaseHunger(getHungerRestored(itemStack));
                        }
                        shrinkItemStack(entityPlayer, itemStack);
                    }
                } else {
                    return InteractionResult.PASS;
                }
            } else if (isFoodItem(itemStack)) {
                if (hunger >= 4000) {
                    decreaseHunger(getHungerRestored(itemStack));
                    shrinkItemStack(entityPlayer, itemStack);
                } else {
                    return InteractionResult.PASS;
                }
            }
        }

        return InteractionResult.PASS;
    }

    private void shrinkItemStack(Player entityPlayer, ItemStack itemStack) {
        if (!entityPlayer.getAbilities().instabuild) {
            itemStack.shrink(1);
        } else {
            if (itemStack.getCount() > 1) {
                itemStack.shrink(1);
            }
        }
    }

    private void swapItemStack(Player entityPlayer, InteractionHand hand, ItemStack itemStack, Item swapItem) {
        if (!entityPlayer.getAbilities().instabuild) {
            if (itemStack.isEmpty()) {
                entityPlayer.setItemInHand(hand, new ItemStack(swapItem));
            } else if (!entityPlayer.getInventory().add(new ItemStack(swapItem))) {
                entityPlayer.drop(new ItemStack(swapItem), false);
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    public Colouration getRgb() {
        for (int i = 1; i <= 6;i++) {
            Item item = this.getEnhancedInventory().getItem(i).getItem();
            if (item instanceof CustomizableAnimalEquipment) {
                setColourToSlot(item, i);
            }
        }

        return this.colouration;
    }

    protected void setColourToSlot(Item item, int i) {
        if (item instanceof CustomizableSaddleEnglish || item instanceof CustomizableSaddleWestern || item instanceof CustomizableSaddleVanilla) {
            this.colouration.setSaddleColour(Colouration.getEquipmentColor(this.getEnhancedInventory().getItem(i)));
        } else if (item instanceof CustomizableBridle) {
            this.colouration.setBridleColour(Colouration.getEquipmentColor(this.getEnhancedInventory().getItem(i)));
        } else if (item instanceof CustomizableCollar) {
            this.colouration.setCollarColour(Colouration.getEquipmentColor(this.getEnhancedInventory().getItem(i)));
        } else if (item instanceof CustomizableAnimalEquipment) {
            this.colouration.setHarnessColour(Colouration.getEquipmentColor(this.getEnhancedInventory().getItem(i)));
        }
    }

    /*
    NBT read/write
    */
    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);

        this.writeNBTGenes("Genetics", compound, this.genetics);
        this.writeNBTGenes("MateGenetics", compound, this.mateGenetics);

        compound.putFloat("Hunger", hunger);

        compound.putString("Status", getEntityStatus());

        compound.putString("BirthTime", this.getBirthTime());

        compound.putString("MateName", this.mateName);
        compound.putString("SireName", this.sireName);
        compound.putString("DamName", this.damName);

        compound.putBoolean("Tamed", this.isTame());

        compound.putString("MotherUUID", this.motherUUID);

        if (canBePregnant()) {
            compound.putBoolean("Pregnant", this.pregnant);
        }
        compound.putInt("Gestation", this.gestationTimer);

        if (canLactate()) {
            compound.putInt("Lactation", this.lactationTimer);
            compound.putInt("milk", getMilkAmount());
        }

        compound.putBoolean("Collared", this.hasCollar());

        compound.putBoolean("IsFemale", this.getOrSetIsFemale());

        writeInventory(compound);
    }

    protected void writeInventory(CompoundTag compound) {
        ListTag listnbt = new ListTag();

        if (!this.animalInventory.getItem(0).isEmpty()) {
            compound.put("Chest", this.animalInventory.getItem(0).save(new CompoundTag()));
        }

        if (!this.animalInventory.getItem(1).isEmpty()) {
            compound.put("Saddle", this.animalInventory.getItem(1).save(new CompoundTag()));
        }

        if (!this.animalInventory.getItem(2).isEmpty()) {
            compound.put("Armour", this.animalInventory.getItem(2).save(new CompoundTag()));
        }

        if (!this.animalInventory.getItem(3).isEmpty()) {
            compound.put("Bridle", this.animalInventory.getItem(3).save(new CompoundTag()));
        }

        if (!this.animalInventory.getItem(4).isEmpty()) {
            compound.put("Blanket", this.animalInventory.getItem(4).save(new CompoundTag()));
        }

        if (!this.animalInventory.getItem(5).isEmpty()) {
            compound.put("Harness", this.animalInventory.getItem(5).save(new CompoundTag()));
        }

        if (!this.animalInventory.getItem(6).isEmpty()) {
            compound.put("Banner", this.animalInventory.getItem(6).save(new CompoundTag()));
        }

        for(int i = 7; i < this.animalInventory.getContainerSize(); ++i) {
            ItemStack itemstack = this.animalInventory.getItem(i);
            if (!itemstack.isEmpty()) {
                CompoundTag compoundnbt = new CompoundTag();
                compoundnbt.putByte("Slot", (byte)i);
                itemstack.save(compoundnbt);
                listnbt.add(compoundnbt);
            }
        }

        compound.put("Items", listnbt);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);

        this.readNBTGenes(compound, "Genetics", this.genetics);
        this.readNBTGenes(compound, "MateGenetics", this.mateGenetics);

        this.hunger = compound.getFloat("Hunger");

        this.setEntityStatus(compound.getString("Status"));

        this.setBirthTime(compound.getString("BirthTime"));

        this.setTame(compound.getBoolean("Tamed"));

        this.motherUUID = compound.getString("MotherUUID");

//        this.setParent(this.motherUUID);

        if (canBePregnant()) {
            this.pregnant = compound.getBoolean("Pregnant");
        }
        this.gestationTimer = compound.getInt("Gestation");

        this.setBreed(compound.getString("breed"));

        //from MobEntity parent
        if (compound.contains("Leash", 10)) {
            this.leashNBTTag = compound.getCompound("Leash");
        }

        geneFixer();

        this.setIsFemale(compound);

        setSharedGenes(this.genetics);
        initilizeAnimalSize();

        resetGrowingAgeToAge();

        if (canLactate()) {
            this.lactationTimer = compound.getInt("Lactation");
            this.setMilkAmount(compound.getInt("milk"));
            this.setMaxBagSize();
        }

        this.setCollar(compound.getBoolean("Collared"));

        this.setMateName(compound.getString("MateName"));
        this.setSireName(compound.getString("SireName"));
        this.setDamName(compound.getString("DamName"));

        this.toggleReloadTexture();

        readInventory(compound);
    }

    protected void resetGrowingAgeToAge() {
        int resetAge = this.getAge() - this.getAdultAge();
        super.setAge(Math.min(resetAge, 0));
    }

    private void readInventory(CompoundTag compound) {
        this.initInventory();

        if (compound.contains("Chest", 10)) {
            ItemStack itemstack = ItemStack.of(compound.getCompound("Chest"));
            this.animalInventory.setItem(0, itemstack);
        }

        if (compound.contains("Saddle", 10)) {
            ItemStack itemstack = ItemStack.of(compound.getCompound("Saddle"));
            this.animalInventory.setItem(1, itemstack);
        }

        if (compound.contains("Armour", 10)) {
            ItemStack itemstack = ItemStack.of(compound.getCompound("Armour"));
            this.animalInventory.setItem(2, itemstack);
        }

        if (compound.contains("Bridle", 10)) {
            ItemStack itemstack = ItemStack.of(compound.getCompound("Bridle"));
            this.animalInventory.setItem(3, itemstack);
        }

        if (compound.contains("Blanket", 10)) {
            ItemStack itemstack = ItemStack.of(compound.getCompound("Blanket"));
            this.animalInventory.setItem(4, itemstack);
        }

        if (compound.contains("Harness", 10)) {
            ItemStack itemstack = ItemStack.of(compound.getCompound("Harness"));
            this.animalInventory.setItem(5, itemstack);
        }

        if (compound.contains("Banner", 10)) {
            ItemStack itemstack = ItemStack.of(compound.getCompound("Banner"));
            this.animalInventory.setItem(6, itemstack);
        }

        ListTag listnbt = compound.getList("Items", 10);

        for(int i = 0; i < listnbt.size(); ++i) {
            CompoundTag compoundnbt = listnbt.getCompound(i);
            int j = compoundnbt.getByte("Slot") & 255;
            if (j >= 0 && j < this.animalInventory.getContainerSize()) {
                this.animalInventory.setItem(j, ItemStack.of(compoundnbt));
            }
        }

        this.updateInventorySlots();
    }

    protected void writeNBTGenes(String name, CompoundTag compound, Genes genetics) {
        CompoundTag nbttagcompound = new CompoundTag();
        nbttagcompound.putIntArray("SGenes", genetics.getSexlinkedGenes());
        nbttagcompound.putIntArray("AGenes", genetics.getAutosomalGenes());
        compound.put(name, nbttagcompound);
    }

    protected void readNBTGenes(CompoundTag compoundNBT, String key, Genes genetics) {
        if (compoundNBT.contains(key)) {
            CompoundTag nbtGenetics = compoundNBT.getCompound(key);
            genetics.setGenes(nbtGenetics.getIntArray("SGenes"), nbtGenetics.getIntArray("AGenes"));
        } else {
            readLegacyGenes(compoundNBT.getList(key.equals("Genetics") ? "Genes" : "FatherGenes", 10), genetics);
        }
    }

    protected void readLegacyGenes(ListTag geneList, Genes genetics) {
        if (geneList.getCompound(0).contains("Sgene")) {
            int sexlinkedlength = genetics.getNumberOfSexlinkedGenes();
            for (int i = 0; i < sexlinkedlength; i++) {
                genetics.setSexlinkedGene(i, geneList.getCompound(i).getInt("Sgene"));
            }

            int length = genetics.getNumberOfAutosomalGenes();
            for (int i = 0; i < length; i++) {
                genetics.setAutosomalGene(i, geneList.getCompound(i+sexlinkedlength).getInt("Agene"));
            }
        } else {
            int length = genetics.getNumberOfSexlinkedGenes();
            for (int i = 0; i < length; i++) {
                genetics.setSexlinkedGene(i, 1);
            }

            for (int i = 0; i < geneList.size(); ++i) {
                genetics.setAutosomalGene(i, geneList.getCompound(i).getInt("Gene"));
            }
        }
    }

    /*
    Entity Creation
    */

    protected void defaultCreateAndSpawn(EnhancedAnimalAbstract enhancedAnimalChild, Level inWorld, Genes babyGenes, int childAge) {
        enhancedAnimalChild.setGenes(babyGenes);
        enhancedAnimalChild.setSharedGenes(babyGenes);
        enhancedAnimalChild.initilizeAnimalSize();
        enhancedAnimalChild.setAge(childAge); // 3 days
        enhancedAnimalChild.setBirthTime(String.valueOf(inWorld.getGameTime()));
        enhancedAnimalChild.setEntityStatus(EntityState.CHILD_STAGE_ONE.toString());
        enhancedAnimalChild.moveTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), 0.0F);
        enhancedAnimalChild.setParent(this.getUUID().toString());
        enhancedAnimalChild.setSireName(this.mateName);
        String name = "???";
        if (this.getCustomName()!=null) {
            name = this.getCustomName().getString();
        }
        enhancedAnimalChild.setDamName(name);
    }

    @Override
    public boolean canBreed() {
        if (this.getAge() < this.getAdultAge()) {
            return false;
        } else {
            return super.canBreed();
        }
    }

    @Override
    public AgeableMob getBreedOffspring(ServerLevel serverWorld, AgeableMob ageable) {
        if (this.getAdultAge() <= this.getAge()) {
            handlePartnerBreeding(ageable);
            this.setAge(10);
            this.resetLove();
            ageable.setAge(10);
            ((EnhancedAnimalAbstract) ageable).resetLove();

            ServerPlayer entityplayermp = this.getLoveCause();
            if (entityplayermp == null && ((EnhancedAnimalAbstract) ageable).getLoveCause() != null) {
                entityplayermp = ((EnhancedAnimalAbstract) ageable).getLoveCause();
            }

            if (entityplayermp != null) {
                entityplayermp.awardStat(Stats.ANIMALS_BRED);
                CriteriaTriggers.BRED_ANIMALS.trigger(entityplayermp, this, ((EnhancedAnimalAbstract) ageable), (AgeableMob) null);
            }
        }

        return null;
    }

    protected void handlePartnerBreeding(AgeableMob ageable) {
        if (EanimodCommonConfig.COMMON.omnigenders.get()) {
            if(this.pregnant) {
                ((EnhancedAnimalAbstract)ageable).pregnant = true;
                ((EnhancedAnimalAbstract)ageable).setMateGenes(this.genetics);
                ((EnhancedAnimalAbstract)ageable).setMateGender(this.getOrSetIsFemale());
                if (this.hasCustomName()) {
                    ((EnhancedAnimalAbstract)ageable).setMateName(this.getCustomName().getString());
                }
            } else {
                this.pregnant = true;
                this.mateGenetics = ((EnhancedAnimalAbstract)ageable).getGenes();
                this.mateGender = ((EnhancedAnimalAbstract)ageable).getOrSetIsFemale();
                if (ageable.hasCustomName()) {
                    this.setMateName(ageable.getCustomName().getString());
                }
            }
        } else if (this.getOrSetIsFemale()) {
           //is female
           this.pregnant = true;
           this.mateGenetics = ((EnhancedAnimalAbstract)ageable).getGenes();
           this.mateGender = false;
            if (ageable.hasCustomName()) {
                this.setMateName(ageable.getCustomName().getString());
            }
        } else {
            //is male
            ((EnhancedAnimalAbstract)ageable).pregnant = true;
            ((EnhancedAnimalAbstract)ageable).setMateGenes(this.genetics);
            ((EnhancedAnimalAbstract)ageable).setMateGender(false);
            if (this.hasCustomName()) {
                ((EnhancedAnimalAbstract)ageable).setMateName(this.getCustomName().getString());
            }
        }
    }

    /**
     * Returns true if the mob is currently able to mate with the specified mob.
     */
    @Override
    public boolean canMate(Animal otherAnimal) {
        if (otherAnimal == this) {
            return false;
        } else if (otherAnimal.getClass() != this.getClass()) {
            return false;
        } else {
            if (EanimodCommonConfig.COMMON.omnigenders.get() || (this.getOrSetIsFemale() ^ ((EnhancedAnimalAbstract)otherAnimal).getOrSetIsFemale())) {
                return this.isInLove() && otherAnimal.isInLove();
            }
            return false;
        }
    }

    /*
    Inventory
    */

    public SimpleContainer getEnhancedInventory() {
        return this.animalInventory;
    }

    @Override
    public void containerChanged(Container invBasic) {
        boolean flag = this.entityData.get(HAS_COLLAR);
        boolean flag2 = this.bells;
        this.updateInventorySlots();
        if (this.tickCount > 20 && !flag && this.entityData.get(HAS_COLLAR)) {
            this.playSound(SoundEvents.ARMOR_EQUIP_LEATHER, 0.5F, 1.0F);
            if (!flag2 && this.bells) {
                this.playSound(SoundEvents.NOTE_BLOCK_CHIME, 0.5F, 1.0F);
            }
        }
        this.updateInventorySlots();
    }

    protected void updateInventorySlots() {
        boolean hasCollar = false;
        this.bells = false;
        for (int i = 1; i <= 6;i++) {
            if (this.animalInventory.getItem(i).getItem() instanceof CustomizableCollar) {
                hasCollar = true;
                if (((CustomizableCollar) this.animalInventory.getItem(i).getItem()).getHasBells()) {
                    this.bells = true;
                }
                break;
            }
        }
        this.setCollar(hasCollar);
        if (!this.level.isClientSide) {
        }
    }

    public int getInventorySize() {
        return 22;
    }

    protected void initInventory() {
        SimpleContainer inventory = this.animalInventory;
        this.animalInventory = new SimpleContainer(this.getInventorySize());
        if (inventory != null) {
            inventory.removeListener(this);
            int i = Math.min(inventory.getContainerSize(), this.animalInventory.getContainerSize());

            for(int j = 0; j < i; ++j) {
                ItemStack itemstack = inventory.getItem(j);
                if (!itemstack.isEmpty()) {
                    this.animalInventory.setItem(j, itemstack.copy());
                }
            }
        }

        this.animalInventory.addListener(this);
        this.updateInventorySlots();
        this.itemHandler = net.minecraftforge.common.util.LazyOptional.of(() -> new net.minecraftforge.items.wrapper.InvWrapper(this.animalInventory));
    }

    public boolean setSlot(int inventorySlot, ItemStack itemStackIn) {
        int i = inventorySlot - 400;
        if (i >= 0 && i < 2 && i < this.animalInventory.getContainerSize()) {
            if (i == 0 && itemStackIn.getItem() != Items.SADDLE) {
                return false;
            } else {
                return false;
            }
        } else {
            int j = inventorySlot - 500 + 2;
            if (j >= 7 && j < this.animalInventory.getContainerSize()) {
                this.animalInventory.setItem(j, itemStackIn);
                return true;
            } else {
                return false;
            }
        }
    }

    protected void dropEquipment() {
        super.dropEquipment();
        if (this.animalInventory != null) {
            for(int i = 0; i < this.animalInventory.getContainerSize(); ++i) {
                ItemStack itemstack = this.animalInventory.getItem(i);
                if (!itemstack.isEmpty() && !EnchantmentHelper.hasVanishingCurse(itemstack)) {
                    this.spawnAtLocation(itemstack);
                }
            }

        }
    }

    protected ItemStack getReplacementItemWithColour(ItemStack itemStack) {
        ItemStack replacementItem = new ItemStack(itemStack.getItem(), 1);
        if (((CustomizableAnimalEquipment)itemStack.getItem()).hasCustomColor(itemStack)) {
            ((CustomizableAnimalEquipment)replacementItem.getItem()).setColor(replacementItem, ((CustomizableAnimalEquipment)itemStack.getItem()).getColor(itemStack));
        }
        return replacementItem;
    }

    protected net.minecraftforge.common.util.LazyOptional<?> itemHandler = null;

    @Override
    public <T> net.minecraftforge.common.util.LazyOptional<T> getCapability(Capability<T> capability, @Nullable Direction facing) {
        if (this.isAlive() && capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY && itemHandler != null)
            return itemHandler.cast();
        return super.getCapability(capability, facing);
    }

    @Override
    public void remove(Entity.RemovalReason removalReason) {
        super.remove(removalReason);
        if (removalReason.shouldDestroy() && itemHandler != null) {
            itemHandler.invalidate();
            itemHandler = null;
        }
    }

    /*
    GUI
    */

    public void openGUI(Player playerEntity) {
        if (!this.level.isClientSide && (!this.isVehicle() || this.hasPassenger(playerEntity))) {
            this.openInfoInventory(this, this.animalInventory, playerEntity);
        }
    }

    public void openInfoInventory(EnhancedAnimalAbstract enhancedAnimal, Container inventoryIn, Player playerEntity) {
        if(!playerEntity.level.isClientSide) {

            EnhancedAnimalInfo animalInfo = new EnhancedAnimalInfo();
            animalInfo.health = (int)(10 * (this.getHealth() / this.getMaxHealth()));
            animalInfo.hunger = (int)(this.getHunger() / 7200);
            animalInfo.isFemale = this.getOrSetIsFemale();
            animalInfo.pregnant = getPregnancyProgression();
            animalInfo.name = this.getAnimalsName(getSpecies());
            animalInfo.agePrefix = this.getAnimalsAgeString();
            animalInfo.age = this.getAge();
            animalInfo.sire = this.sireName;
            animalInfo.dam = this.damName;

            if(playerEntity instanceof ServerPlayer) {
                ServerPlayer entityPlayerMP = (ServerPlayer)playerEntity;
                NetworkHooks.openGui(entityPlayerMP, new MenuProvider() {
                    @Override
                    public AbstractContainerMenu createMenu(int windowId, Inventory inventory, Player player) {
                        return new EnhancedAnimalContainer(windowId, inventory, enhancedAnimal, animalInfo);
                    }

                    @Override
                    public Component getDisplayName() {
                        return new TranslatableComponent("eanimod.animalinfocontainer");
                    }
                }, buf -> {
                    buf.writeInt(enhancedAnimal.getId());
                    buf.writeUtf(animalInfo.serialiseToString());
                });
            }
        }
    }

    protected int getPregnancyProgression() {
        return this.canBePregnant() ? (10 * this.gestationTimer)/gestationConfig() : 0;
    }

    protected String getAnimalsName(String species) {
        String name = species;
        if (this.getCustomName() != null) {
            name = this.getCustomName().getContents();
            if (name.equals("")) {
                name = species;
            }
        }

        return name;
    }

    protected String getAnimalsAgeString() {
        int age = this.getAge();
        int adultAge = getAdultAge();
        if (age < adultAge) {
            if (age > (adultAge*3)/4) {
                return "YOUNG";
            } else if (age > adultAge/3) {
                return "BABY";
            } else {
                return "NEWBORN";
            }
        }
        return "ADULT";
    }

    @Override
    public boolean isBaby() {
        int age = this.getAge();
        int adultAge = getAdultAge();
        return age < adultAge;
    }

    public void onSyncedDataUpdated(EntityDataAccessor<?> key) {
        if (ANIMAL_SIZE.equals(key) && this.level.isClientSide) {
            if (this.isGrowing()) {
                this.reloadSizeTime = (int) (this.getFullSizeAge() * 0.05);
            } else {
                this.refreshDimensions();
            }
        }

        super.onSyncedDataUpdated(key);
    }


    /*
    Client Sided Work
    */

    @OnlyIn(Dist.CLIENT)
    public void handleEntityEvent(byte id) {
        if (id == 10) {
            this.animalEatingTimer = 40;
        } else {
            super.handleEntityEvent(id);
        }
    }

    //Eating Animation
    @OnlyIn(Dist.CLIENT)
    public float getHeadRotationPointY(float partialTickTime) {
        if (this.animalEatingTimer <= 0) {
            return 0.0F;
        } else if (this.animalEatingTimer >= 4 && this.animalEatingTimer <= 36) {
            return 0.2F;
        } else {
            return this.animalEatingTimer < 4 ? ((float)this.animalEatingTimer - partialTickTime) / 4.0F : -((float)(this.animalEatingTimer - 40) - partialTickTime) / 4.0F;
        }
    }
    //Eating Animation
    @OnlyIn(Dist.CLIENT)
    public float getHeadRotationAngleX(float partialTickTime) {
        if (this.animalEatingTimer > 4 && this.animalEatingTimer <= 36) {
            float f = ((float)(this.animalEatingTimer - 4) - partialTickTime) / 32.0F;
            return ((float)Math.PI / 5F) + ((float)Math.PI * 7F / 100F) * Mth.sin(f * 28.7F);
        } else {
            return this.animalEatingTimer > 0 ? ((float)Math.PI / 5F) : this.getXRot() * 0.017453292F;
        }
    }

    //------------

    public boolean setTamedBy(Player player) {
        //TODO save player's name to be displayed in GUI
//        this.setOwnerUniqueId(player.getUniqueID());
        this.setTame(true);
        if (player instanceof ServerPlayer) {
            CriteriaTriggers.TAME_ANIMAL.trigger((ServerPlayer)player, this);
        }

        this.level.broadcastEntityEvent(this, (byte)7);
        return true;
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return getAnimalFoodType().isBreedingItem(stack.getItem());
    }

    public boolean isFoodItem(ItemStack stack) {
        return getAnimalFoodType().isFoodItem(stack.getItem());
    }

    public boolean isBreedingItem(ItemStack stack) {
        return getAnimalFoodType().isBreedingItem(stack.getItem());
    }

    public int getHungerRestored(ItemStack stack) {
        return getAnimalFoodType().getHungerRestored(stack.getItem());
    }

    public void setSharedGenes(Genes genes) {
        this.entityData.set(SHARED_GENES, genes.getGenesAsString());
    }

    @OnlyIn(Dist.CLIENT)
    public Genes getSharedGenes() {
        if(this.genesSplitForClient==null) {
            String sharedGenes = this.entityData.get(SHARED_GENES);
            if (sharedGenes.isEmpty()) {
                return null;
            }

            Genes genes = new Genes(sharedGenes);

            this.genesSplitForClient = genes;
            return genes;
        }
        return this.genesSplitForClient;
    }

    protected void addTextureToAnimal(String[] texture, int geneValue, Predicate<Integer> check) {
        if(check == null || check.test(geneValue)) {
            this.enhancedAnimalTextures.add(texture[geneValue]);
            this.texturesIndexes.add(String.valueOf(geneValue));
        }
        this.texturesIndexes.add(CACHE_DELIMITER);
    }

    protected void addTextureToAnimal(String texture) {
        this.enhancedAnimalTextures.add(texture);
        this.texturesIndexes.add(String.valueOf(0));
        this.texturesIndexes.add(CACHE_DELIMITER);
    }

    @OnlyIn(Dist.CLIENT)
    public String[] getVariantTexturePaths() {
        if (this.enhancedAnimalTextures.isEmpty()) {
            this.setTexturePaths();
        }
        List<String> compiledTextures = new ArrayList<>();
        compiledTextures.addAll(this.enhancedAnimalTextures);
        compiledTextures.addAll(this.equipmentTextures.values().stream().flatMap(Collection::stream).collect(Collectors.toList()));

        return compiledTextures.stream().toArray(String[]::new);
    }

    @OnlyIn(Dist.CLIENT)
    public String[] getVariantAlphaTexturePaths() {
        if (this.enhancedAnimalAlphaTextures.isEmpty()) {
            this.setAlphaTexturePaths();
        }

        //todo this is only temporary until we have alpha textures
        if (this.enhancedAnimalAlphaTextures.isEmpty()) {
            return null;
        }

        return this.enhancedAnimalAlphaTextures.stream().toArray(String[]::new);
    }

    protected String getCompiledTextures(String eanimal) {
        if (this.compiledTexture == null) {
            this.compiledTexture = this.texturesIndexes.stream().collect(Collectors.joining("",eanimal+"/",""));
        }

        if (this.compiledAlphaTexture == null) {
            this.compiledAlphaTexture = this.enhancedAnimalAlphaTextures.stream().collect(Collectors.joining("/"));
        }

        if (this.compiledEquipmentTexture == null) {
            this.compiledEquipmentTexture = this.equipmentTextures.values().stream().flatMap(Collection::stream).collect(Collectors.joining("/"));
        }
        return this.compiledTexture + compiledAlphaTexture + compiledEquipmentTexture;
    }

    protected void geneFixer() {
        if (!this.breed.isEmpty()) {
            this.genetics = this.breed.equals("village") ? this.genetics = createInitialGenes(this.level, new BlockPos(this.blockPosition()), true) : createInitialBreedGenes(this.level, new BlockPos(this.blockPosition()), this.breed);
            setInitialDefaults();
            int childAge = this.getAdultAge();
            if (this.random.nextInt(20) == 0) {
                childAge = this.random.nextInt(childAge);
                this.setAge(childAge);
                this.setBirthTime(this.level, -childAge);
            } else {
                this.setAge(0);
                if (this.random.nextInt(20) == 0) {
                    this.setBirthTime(this.level, -((ThreadLocalRandom.current().nextInt(50) * 192000) + 1536000));
                } else {
                    this.setBirthTime(this.level, -((ThreadLocalRandom.current().nextInt(50) * 100000) + childAge));
                }
            }
        } else if (this.genetics.getAutosomalGene(0) == 0) {
            this.genetics = createInitialGenes(this.level, new BlockPos(this.blockPosition()), true);
            setInitialDefaults();
            int childAge = this.getAdultAge();
            if (this.random.nextInt(20) == 0) {
                childAge = this.random.nextInt(childAge);
                this.setAge(childAge);
                this.setBirthTime(this.level, -childAge);
            } else {
                this.setAge(0);
                if (this.random.nextInt(20) == 0) {
                    this.setBirthTime(this.level, -((ThreadLocalRandom.current().nextInt(50) * 192000) + 1536000));
                } else {
                    this.setBirthTime(this.level, -((ThreadLocalRandom.current().nextInt(50) * 100000) + childAge));
                }
            }
        } else {
            int[] sexlinkedGenes = this.genetics.getSexlinkedGenes();
            int[] autosomalGenes = this.genetics.getAutosomalGenes();
            int genelength = this.genetics.getNumberOfSexlinkedGenes() - 1;
            for (int i = genelength; sexlinkedGenes[i] == 0; i--) {
                this.genetics.setSexlinkedGene(i, 1);
                if (i <= 0) {
                    break;
                }
            }
            genelength = this.genetics.getNumberOfAutosomalGenes() - 1;
            for (int i = genelength; autosomalGenes[i] == 0; i--) {
                this.genetics.setAutosomalGene(i, 1);
                if (i <= 0) {
                    break;
                }
            }
        }
    }

    public void setMateGenes(Genes genes){
        this.mateGenetics = genes;
    }

    public void setMateGender(Boolean gender){
        this.mateGender = gender;
    }

    public void setGrowingAge() {
        super.setAge(-this.getAdultAge());
    }

    //overriden to prevent aging up when fed
    @Override
    public void ageUp(int growthSeconds, boolean updateForcedAge) {
        int newBirthTime = Integer.valueOf(getBirthTime()) - ((int)(getAdultAge()*0.1));
        this.setBirthTime(String.valueOf(newBirthTime));
        super.ageUp(growthSeconds, updateForcedAge);
        if (!this.isBaby() && getAge() <= -1) {
            this.setAge(0);
        }
    }

    protected SpawnGroupData commonInitialSpawnSetup(LevelAccessor inWorld, @Nullable SpawnGroupData livingdata, int childAge, int ageMinimum, int ageMaximum, MobSpawnType spawnReason) {
        Genes spawnGenes;

        if (spawnReason.equals(MobSpawnType.STRUCTURE)) {
            spawnGenes = createInitialGenes(this.level, new BlockPos(this.blockPosition()), true);
        } else if (livingdata instanceof GroupData) {
            spawnGenes = new Genes(((GroupData)livingdata).groupGenes).makeChild(true, false, ((GroupData)livingdata).groupGenes);
        } else {
            spawnGenes = createInitialGenes(this.level, new BlockPos(this.blockPosition()), false);
            livingdata = new GroupData(spawnGenes);
        }

        this.genetics = spawnGenes;

        boolean canBePregnant = false;
        if (this.random.nextInt(20) == 0) {
            int age = this.random.nextInt(childAge);
            this.setAge(age);
            this.setBirthTime(this.level, -age);
        } else {
            this.setAge(0);
            if (this.random.nextInt(20) == 0) {
                this.setBirthTime(this.level, -((ThreadLocalRandom.current().nextInt(50) * 192000) + 1536000));
            } else {
                this.setBirthTime(this.level, -((ThreadLocalRandom.current().nextInt(50) * 100000) + childAge));
            }

            if (spawnReason.equals(MobSpawnType.CHUNK_GENERATION)) {
                canBePregnant = EanimodCommonConfig.COMMON.omnigenders.get() ? this.random.nextInt(50) == 0 : this.getOrSetIsFemale() && this.random.nextInt(25) == 0;
            }
        }

        setInitialDefaults();

        if (canBePregnant) {
            if (this.bottleFeedable) {
                this.pregnant = true;
            }
            if (this.random.nextInt(5) == 0) {
                int x = this.random.nextBoolean() ? this.random.nextInt(600) : -this.random.nextInt(600);
                int z = this.random.nextBoolean() ? this.random.nextInt(600) : -this.random.nextInt(600);
                BlockPos matePos = new BlockPos(this.blockPosition().offset(x, 0, z));
                this.mateGenetics = createInitialGenes(this.level, matePos, false);
            } else {
                this.mateGenetics = ((GroupData)livingdata).groupGenes;
            }
            this.mateGender = false;
        }

        return livingdata;
    }

    protected abstract Genes createInitialGenes(LevelAccessor inWorld, BlockPos pos, boolean isDomestic);

    public abstract Genes createInitialBreedGenes(LevelAccessor inWorld, BlockPos pos, String breed);

    public void setInitialDefaults() {
        setSharedGenes(this.genetics);
        initilizeAnimalSize();
        initializeHealth(this, 1F);
    }

    protected void initializeHealth(EnhancedAnimalAbstract animal, float health) {
        animal.getAttribute(Attributes.MAX_HEALTH).setBaseValue((double)health);
        animal.heal(animal.getMaxHealth() - animal.getHealth());
    }

    public static class GroupData implements SpawnGroupData {
        public Genes groupGenes;

        public GroupData(Genes groupGenes) {
            this.groupGenes = groupGenes;
        }
    }

    private List<String> getCollarTextures() {
        List<String> collarTextures = new ArrayList<>();

        if (this.getEnhancedInventory() != null) {
            ItemStack collarSlot = ItemStack.EMPTY;
            for (int i = 1; i <= 6;i++) {
                if (this.animalInventory.getItem(i).getItem() instanceof CustomizableCollar) {
                    collarSlot = this.animalInventory.getItem(i);
                    break;
                }
            }
            if (collarSlot != ItemStack.EMPTY) {
                Item collar = collarSlot.getItem();
                collarTextures.add(COLLAR);
                if (collar == ModItems.COLLAR_BASIC_CLOTH_IRONRING) {
                    collarTextures.add(COLLAR_HARDWARE[0]);
                } else if (collar == ModItems.COLLAR_BASIC_CLOTH_GOLDRING) {
                    collarTextures.add(COLLAR_HARDWARE[1]);
                } else if (collar == ModItems.COLLAR_BASIC_CLOTH_DIAMONDRING) {
                    collarTextures.add(COLLAR_HARDWARE[2]);
                } else if (collar == ModItems.COLLAR_BASIC_CLOTH_IRONBELL) {
                    collarTextures.add(COLLAR_HARDWARE[3]);
                } else if (collar == ModItems.COLLAR_BASIC_CLOTH_GOLDBELL) {
                    collarTextures.add(COLLAR_HARDWARE[4]);
                } else if (collar == ModItems.COLLAR_BASIC_CLOTH_DIAMONDBELL) {
                    collarTextures.add(COLLAR_HARDWARE[5]);
                } else if (collar == ModItems.COLLAR_BASIC_LEATHER) {
                    collarTextures.add(COLLAR_TEXTURE);
                } else if (collar == ModItems.COLLAR_BASIC_LEATHER_IRONRING) {
                    collarTextures.add(COLLAR_TEXTURE);
                    collarTextures.add(COLLAR_HARDWARE[0]);
                } else if (collar == ModItems.COLLAR_BASIC_LEATHER_GOLDRING) {
                    collarTextures.add(COLLAR_TEXTURE);
                    collarTextures.add(COLLAR_HARDWARE[1]);
                } else if (collar == ModItems.COLLAR_BASIC_LEATHER_DIAMONDRING) {
                    collarTextures.add(COLLAR_TEXTURE);
                    collarTextures.add(COLLAR_HARDWARE[2]);
                } else if (collar == ModItems.COLLAR_BASIC_LEATHER_IRONBELL) {
                    collarTextures.add(COLLAR_TEXTURE);
                    collarTextures.add(COLLAR_HARDWARE[3]);
                } else if (collar == ModItems.COLLAR_BASIC_LEATHER_GOLDBELL) {
                    collarTextures.add(COLLAR_TEXTURE);
                    collarTextures.add(COLLAR_HARDWARE[4]);
                } else if (collar == ModItems.COLLAR_BASIC_LEATHER_DIAMONDBELL) {
                    collarTextures.add(COLLAR_TEXTURE);
                    collarTextures.add(COLLAR_HARDWARE[5]);
                }
            }
        }

        return collarTextures;
    }
}
