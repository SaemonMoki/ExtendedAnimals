package mokiyoki.enhancedanimals.entity;

import mokiyoki.enhancedanimals.EnhancedAnimals;
import mokiyoki.enhancedanimals.ai.general.AIStatus;
import mokiyoki.enhancedanimals.ai.general.EnhancedLookAtGoal;
import mokiyoki.enhancedanimals.ai.general.EnhancedLookRandomlyGoal;
import mokiyoki.enhancedanimals.config.EanimodCommonConfig;
import mokiyoki.enhancedanimals.entity.util.Colouration;
import mokiyoki.enhancedanimals.entity.util.Equipment;
import mokiyoki.enhancedanimals.gui.EnhancedAnimalContainer;
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
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.ai.goal.BreedGoal;
import net.minecraft.entity.ai.goal.FollowParentGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.item.LeashKnotEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.IInventoryChangedListener;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.stats.Stats;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.items.CapabilityItemHandler;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public abstract class EnhancedAnimalAbstract extends AnimalEntity implements EnhancedAnimal, IInventoryChangedListener {

    protected static final DataParameter<String> SHARED_GENES = EntityDataManager.<String>createKey(EnhancedAnimalAbstract.class, DataSerializers.STRING);
    protected static final DataParameter<Boolean> SLEEPING = EntityDataManager.createKey(EnhancedAnimalAbstract.class, DataSerializers.BOOLEAN);
    protected static final DataParameter<Boolean> TAMED = EntityDataManager.createKey(EnhancedAnimalAbstract.class, DataSerializers.BOOLEAN);
    protected static final DataParameter<String> BIRTH_TIME = EntityDataManager.<String>createKey(EnhancedAnimalAbstract.class, DataSerializers.STRING);
    private static final DataParameter<Float> ANIMAL_SIZE = EntityDataManager.createKey(EnhancedAnimalAbstract.class, DataSerializers.FLOAT);
    private static final DataParameter<String> ENTITY_STATUS = EntityDataManager.createKey(EnhancedAnimalAbstract.class, DataSerializers.STRING);
    private static final DataParameter<Float> BAG_SIZE = EntityDataManager.createKey(EnhancedAnimalAbstract.class, DataSerializers.FLOAT);
    private static final DataParameter<Integer> MILK_AMOUNT = EntityDataManager.createKey(EnhancedAnimalAbstract.class, DataSerializers.VARINT);
    private static final DataParameter<Boolean> HAS_COLLAR = EntityDataManager.createKey(EnhancedAnimalAbstract.class, DataSerializers.BOOLEAN);
    protected static final DataParameter<Boolean> RESET_TEXTURE = EntityDataManager.createKey(EnhancedAnimalAbstract.class, DataSerializers.BOOLEAN);

    private final NonNullList<ItemStack> equipmentArray = NonNullList.withSize(7, ItemStack.EMPTY);

    private static final String COLLAR = "d_collar.png";
    private static final String COLLAR_TEXTURE = "collar_leather.png";
    private static final String[] COLLAR_HARDWARE = new String[] {
            "collar_ringiron.png", "collar_ringgold.png", "collar_ringdiamond.png",
            "collar_belliron.png", "collar_bellgold.png", "collar_belldiamond.png"
    };

    protected Ingredient TEMPTATION_ITEMS;
    protected Ingredient BREED_ITEMS;
    private static final Ingredient MILK_ITEMS = Ingredient.fromItems(ModItems.MILK_BOTTLE, ModItems.HALF_MILK_BOTTLE);

    //demo mode
    public boolean runDemoMode = false;
    protected int demoTimerMax = 60;
    private int demoTimer = 0;


    // Genetic Info
    protected Genes genetics;
    protected Genes mateGenetics;
    protected Boolean mateGender;
    protected Genes genesSplitForClient;
    protected static final int WTC = EanimodCommonConfig.COMMON.wildTypeChance.get();
    public String breed = "";

    //Hunger
    Map<Item, Integer> foodWeightMap = new HashMap();
    protected float hunger = 0F;
    protected int healTicks = 0;
    protected boolean bottleFeedable = false;
    protected int animalEatingTimer;
    protected AIStatus currentAIStatus = AIStatus.NONE;

    //Sleeping
    protected Boolean sleeping = false;
    protected int awokenTimer = 0;

    //Animations
    private int blink = 0;
    private int earTwitchTicker = 0;
    private int earTwitch = 0;

    //Pregnancy
    protected int gestationTimer = 0;
    protected boolean pregnant = false;

    //Lactation
    protected int lactationTimer = 0;
    protected float maxBagSize;
    protected int timeUntilNextMilk;

    //Texture
    protected final List<String> enhancedAnimalTextures = new ArrayList<>();
    protected final List<String> texturesIndexes = new ArrayList<>();
    protected final List<String> enhancedAnimalAlphaTextures = new ArrayList<>();
    protected final Map<Equipment, List<String>> equipmentTextures = new HashMap<>();
    public Colouration colouration = new Colouration();
    protected boolean bells;
    protected Boolean reload = false; //used in a toggle manner

    //Inventory
    protected Inventory animalInventory;

    //Overrides
    @Nullable
    private CompoundNBT leashNBTTag;

    /*
    Entity Construction
    */

    protected EnhancedAnimalAbstract(EntityType<? extends EnhancedAnimalAbstract> type, World worldIn, int SgenesSize, int AgenesSize, Ingredient temptationItems, Ingredient breedItems, Map<Item, Integer> foodWeightMap, boolean bottleFeedable) {
        super(type, worldIn);
        this.genetics = new Genes(new int[SgenesSize], new int[AgenesSize]);
        this.mateGenetics = new Genes(new int[SgenesSize], new int[AgenesSize]);
        this.mateGender = false;
        this.TEMPTATION_ITEMS = temptationItems;
        this.BREED_ITEMS = breedItems;
        this.foodWeightMap = foodWeightMap;
        this.bottleFeedable = bottleFeedable;

        initInventory();
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(SHARED_GENES, new String());
        this.dataManager.register(SLEEPING, false);
        this.dataManager.register(TAMED, false);
        this.dataManager.register(ENTITY_STATUS, new String());
        this.dataManager.register(BIRTH_TIME, "0");
        this.dataManager.register(ANIMAL_SIZE, 0.0F);
        if (canLactate()) {
            this.dataManager.register(BAG_SIZE, 0.0F);
            this.dataManager.register(MILK_AMOUNT, 0);
        }
        this.dataManager.register(HAS_COLLAR, false);
        this.dataManager.register(RESET_TEXTURE, false);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(3, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.1D));
        this.goalSelector.addGoal(7, new EnhancedLookAtGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.addGoal(8, new EnhancedLookRandomlyGoal(this));
    }

    protected abstract String getSpecies();

    protected void setBreed(String breed) {
        this.breed = breed;
    }

    protected abstract int getAdultAge();

    /*
    Animal expected abstracts and overrides
    */

    //returns the config for the animals gestation or any overrides
    protected abstract int gestationConfig();

    //the amount hunger is incremented
    protected abstract void incrementHunger();

    //anything extra to run during not idling
    protected abstract void runExtraIdleTimeTick();

    //any lethal genes checks the animal has
    protected abstract void lethalGenes();

    //when the animal wakes up
    protected boolean sleepingConditional() {
        return (!this.world.isDaytime() && awokenTimer == 0 && !sleeping);
    }

    //    public void setReloadTexture(Boolean resetTexture) {
//        this.dataManager.set(RESET_TEXTURE, resetTexture);
//    }

    //toggles the reloading
    protected void toggleReloadTexture() {
        this.dataManager.set(RESET_TEXTURE, this.getReloadTexture() == true ? false : true);
    }

    public boolean getReloadTexture() {
        return this.dataManager.get(RESET_TEXTURE);
    }

    //for setting the textures
    @OnlyIn(Dist.CLIENT)
    protected abstract void setTexturePaths();

    //for setting the alpha textures
    @OnlyIn(Dist.CLIENT)
    protected abstract void setAlphaTexturePaths();

    //called during construction to set up the animal size
    protected abstract void initilizeAnimalSize();

    //method to create the new child
    protected abstract void createAndSpawnEnhancedChild(World world);

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

    public void setBirthTime(String birthTime) {
        this.dataManager.set(BIRTH_TIME, birthTime);
    }

    public String getBirthTime() {
        return this.dataManager.get(BIRTH_TIME);
    }

    protected void setEntityStatus(String status) {
        this.dataManager.set(ENTITY_STATUS, status);
    }

    public String getEntityStatus() {
        return this.dataManager.get(ENTITY_STATUS);
    }

    public void setSleeping(Boolean sleeping) {
        this.sleeping = sleeping;
        this.dataManager.set(SLEEPING, sleeping); }

    @Override
    public Boolean isAnimalSleeping() {
        if (this.sleeping == null) {
            return false;
        } else {
            sleeping = this.dataManager.get(SLEEPING);
            return sleeping;
        }
    }

    @Override
    public void awaken() {
        this.awokenTimer = 200;
        setSleeping(false);
    }

    protected void setAnimalSize(float size) {
        this.dataManager.set(ANIMAL_SIZE, size);
    }

    public float getAnimalSize() {
        return this.dataManager.get(ANIMAL_SIZE);
    }

    @Override
    public float getHunger(){
        return hunger;
    }

    public void decreaseHunger(float decrease) {
        if (this.hunger - decrease < 0) {
            this.hunger = 0;
        } else {
            this.hunger = this.hunger - decrease;
        }
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
        return this.dataManager.get(TAMED);
    }

    public void setTame(boolean tame) {
        this.dataManager.set(TAMED, tame);
    }

    protected int getAge() {
        if (!(getBirthTime() == null) && !getBirthTime().equals("") && !getBirthTime().equals(0)) {
            return (int)(this.world.getWorldInfo().getGameTime() - Long.parseLong(getBirthTime()));
        } else {
            return 500000;
        }
    }

    protected void setBagSize(float size) { this.dataManager.set(BAG_SIZE, size); }

    public float getBagSize() {
        return this.dataManager.get(BAG_SIZE);
    }

    protected void setMilkAmount(Integer milkAmount) {
        this.dataManager.set(MILK_AMOUNT, milkAmount);
    }

    public Integer getMilkAmount() { return this.dataManager.get(MILK_AMOUNT); }

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
        return this.dataManager.get(HAS_COLLAR);
    }

    public void setCollar(boolean collared) {
        this.dataManager.set(HAS_COLLAR, collared);
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
    }

    //used to set if an animal is wearing bells
    protected boolean getBells() {
        return this.bells;
    }

    /*
    General Info
    */
    public boolean getIsFemale() {
        char[] uuidArray = getCachedUniqueIdString().toCharArray();
        return !Character.isLetter(uuidArray[0]) && uuidArray[0] - 48 < 8;
    }

    /*
    Tick
    */

    @Override
    public void tick() {
        super.tick();
        if (!this.world.isRemote) {
            for(int invSlot = 0; invSlot < 7; invSlot++) {
                ItemStack inventoryItemStack = this.animalInventory.getStackInSlot(invSlot);
                ItemStack equipmentItemStack = equipmentArray.get(invSlot);

                if (!ItemStack.areItemStacksEqual(inventoryItemStack, equipmentItemStack)) {
                    if (!inventoryItemStack.equals(equipmentItemStack, true)) {
                        EAEquipmentPacket equipmentPacket = new EAEquipmentPacket(this.getEntityId(), invSlot, inventoryItemStack);
                        EnhancedAnimals.channel.send(PacketDistributor.TRACKING_ENTITY.with(() -> this), equipmentPacket);
                    }
                    this.equipmentArray.set(invSlot, inventoryItemStack.copy());
                }
            }
        }
        if (this.runDemoMode) {
            if (this.demoTimer >= this.demoTimerMax) {
                this.demoTimer = 0;
                this.genetics = createInitialGenes(this.world, new BlockPos(this), false);
                setInitialDefaults();
                this.toggleReloadTexture();
            } else {
                this.demoTimer++;
            }
        }
    }

    @Override
    protected void updateLeashedState() {
        if (this.leashNBTTag != null) {
            super.updateLeashedState();
            leashNBTTag = null;
        }

        if (this.getLeashHolder() != null) {
            if (!this.isAlive() || !this.getLeashHolder().isAlive()) {
                this.clearLeashed(true, true);
            }
        }

        Entity entity = this.getLeashHolder();
        if (entity != null && entity.world == this.world) {
            this.setHomePosAndDistance(new BlockPos(entity), 5);
            float f = this.getDistance(entity);

            this.onLeashDistance(f);
            if (f > 10.0F) {
                this.clearLeashed(true, true);
                this.goalSelector.disableFlag(Goal.Flag.MOVE);
            } else if (f > 6.0F) {
                double d0 = (entity.getPosX() - this.getPosX()) / (double)f;
                double d1 = (entity.getPosY() - this.getPosY()) / (double)f;
                double d2 = (entity.getPosZ() - this.getPosZ()) / (double)f;
                this.setMotion(this.getMotion().add(Math.copySign(d0 * d0 * 0.4D, d0), Math.copySign(d1 * d1 * 0.4D, d1), Math.copySign(d2 * d2 * 0.4D, d2)));
            } else if (!ableToMoveWhileLeashed()){
                this.goalSelector.enableFlag(Goal.Flag.MOVE);
                Vec3d vec3d = (new Vec3d(entity.getPosX() - this.getPosX(), entity.getPosY() - this.getPosY(), entity.getPosZ() - this.getPosZ())).normalize().scale((double)Math.max(f - 2.0F, 0.0F));
                this.getNavigator().tryMoveToXYZ(this.getPosX() + vec3d.x, this.getPosY() + vec3d.y, this.getPosZ() + vec3d.z, this.followLeashSpeed());
            }
        }
    }

    protected boolean ableToMoveWhileLeashed() {
        return false;
    }

    /*
    Living Tick
    */

    @Override
    public void livingTick() {
        super.livingTick();
        //run client-sided tick stuff
        if (this.world.isRemote) {
            runLivingTickClient();
        } else {
            //run server-sided tick stuff
            if (sleepingConditional()) {
                setSleeping(true);
                healTicks = 0;
            } else if (awokenTimer > 0) {
                awokenTimer--;
            } else if (this.world.isDaytime() && sleeping) {
                setSleeping(false);
            }

            //not outside AI processing distance
            if (this.getIdleTime() < 100) {
                if (hunger <= 72000) {
                    if (sleeping) {
                        if ((hunger <= (gestationConfig()/2))) {
                            incrementHunger();
                        }
                        healTicks++;
                        if (healTicks > 100 && hunger < 6000 && this.getMaxHealth() > this.getHealth()) {
                            this.heal(2.0F);
                            hunger = hunger + 1000;
                            healTicks = 0;
                        }
                    } else {
                        incrementHunger();
                    }
                }
                runExtraIdleTimeTick();
            }

            runPregnancyTick();

            updateStatusTick();

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
                this.blink = rand.nextInt(400);
            } else {
                this.blink--;
            }
        }

        //ear twitch timer
        if (this.earTwitchTicker == 0) {
            if (asleep) {
                this.earTwitchTicker = rand.nextInt(2400);
            } else {
                this.earTwitchTicker = rand.nextInt(1200);
            }
            if (rand.nextBoolean()) {
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
                this.setGrowingAge(600);
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

                for (int i = 0; i <= numberOfChildren; i++) {
//                    mixMateMitosisGenes();
//                    mixMitosisGenes();
                    createAndSpawnEnhancedChild(this.world);
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

        float milkBagSize = 4 / (6*maxBagSize);

        this.setBagSize((milkBagSize*(maxBagSize/3.0F))+(maxBagSize*2.0F/3.0F));
    }

    protected int getNumberOfChildren() {
        return 1;
    }

    protected void updateStatusTick() {
        String entityState = getEntityStatus();
        if (this.isChild()) {
            if (entityState.equals(EntityState.CHILD_STAGE_ONE.toString()) && this.getGrowingAge() < -16000) {
                if(hunger < 5000) {
                    setEntityStatus(EntityState.CHILD_STAGE_TWO.toString());
                } else {
                    this.setGrowingAge(-16500);
                }
            } else if (entityState.equals(EntityState.CHILD_STAGE_TWO.toString()) && this.getGrowingAge() < -8000) {
                if(hunger < 5000) {
                    setEntityStatus(EntityState.CHILD_STAGE_THREE.toString());
                } else {
                    this.setGrowingAge(-8500);
                }
            } else if (entityState.equals(EntityState.CHILD_STAGE_THREE.toString())) {
                setEntityStatus(EntityState.ADULT.toString());
                //TODO remove the child follow mother ai
            }
        } else if (entityState.equals(EntityState.CHILD_STAGE_THREE.toString())) {
            setEntityStatus(EntityState.ADULT.toString());
            //TODO remove the child follow mother ai
        }
    }

    /*
    Animal interaction from player and other entities
    */

    @Override
    public boolean processInteract(PlayerEntity entityPlayer, Hand hand) {
        ItemStack itemStack = entityPlayer.getHeldItem(hand);
        Item item = itemStack.getItem();

        if (entityPlayer.isSecondaryUseActive()) {
            this.openGUI(entityPlayer);
            return true;
        }

        if (!this.world.isRemote && !hand.equals(Hand.OFF_HAND)) {
            if (item instanceof DebugGenesBook) {
                Minecraft.getInstance().keyboardListener.setClipboardString(this.dataManager.get(SHARED_GENES));
            }
            else if ((!this.isChild() || !bottleFeedable) && TEMPTATION_ITEMS.test(itemStack) && hunger >= 6000) {
                if (this.foodWeightMap.containsKey(item)) {
                    decreaseHunger(this.foodWeightMap.get(item));
                } else {
                    decreaseHunger(6000);
                }
                if (!entityPlayer.abilities.isCreativeMode) {
                    itemStack.shrink(1);
                } else {
                    if (itemStack.getCount() > 1) {
                        itemStack.shrink(1);
                    }
                }
            }  else if (this.isChild() && MILK_ITEMS.test(itemStack) && bottleFeedable && hunger >= 6000) {
                if (item == ModItems.HALF_MILK_BOTTLE) {
                    decreaseHunger(6000);
                    if (!entityPlayer.abilities.isCreativeMode) {
                        if (itemStack.isEmpty()) {
                            entityPlayer.setHeldItem(hand, new ItemStack(Items.GLASS_BOTTLE));
                        } else if (!entityPlayer.inventory.addItemStackToInventory(new ItemStack(Items.GLASS_BOTTLE))) {
                            entityPlayer.dropItem(new ItemStack(Items.GLASS_BOTTLE), false);
                        }
                    }
                } else if (item == ModItems.MILK_BOTTLE) {
                    if (hunger >= 12000) {
                        decreaseHunger(12000);
                        if (!entityPlayer.abilities.isCreativeMode) {
                            if (itemStack.isEmpty()) {
                                entityPlayer.setHeldItem(hand, new ItemStack(Items.GLASS_BOTTLE));
                            } else if (!entityPlayer.inventory.addItemStackToInventory(new ItemStack(Items.GLASS_BOTTLE))) {
                                entityPlayer.dropItem(new ItemStack(Items.GLASS_BOTTLE), false);
                            }
                        }
                    } else {
                        decreaseHunger(6000);
                        if (!entityPlayer.abilities.isCreativeMode) {
                            if (itemStack.isEmpty()) {
                                entityPlayer.setHeldItem(hand, new ItemStack(ModItems.HALF_MILK_BOTTLE));
                            } else if (!entityPlayer.inventory.addItemStackToInventory(new ItemStack(ModItems.HALF_MILK_BOTTLE))) {
                                entityPlayer.dropItem(new ItemStack(ModItems.HALF_MILK_BOTTLE), false);
                            }
                        }
                    }
                }
            }
        }

        return super.processInteract(entityPlayer, hand);
    }

    @OnlyIn(Dist.CLIENT)
    public Colouration getRgb() {
        for (int i = 1; i <= 6;i++) {
            Item item = this.getEnhancedInventory().getStackInSlot(i).getItem();
            if (item instanceof CustomizableAnimalEquipment) {
                setColourToSlot(item, i);
            }
        }

        return this.colouration;
    }

    protected void setColourToSlot(Item item, int i) {
        if (item instanceof CustomizableSaddleEnglish || item instanceof CustomizableSaddleWestern || item instanceof CustomizableSaddleVanilla) {
            this.colouration.setSaddleColour(Colouration.getEquipmentColor(this.getEnhancedInventory().getStackInSlot(i)));
        } else if (item instanceof CustomizableBridle) {
            this.colouration.setBridleColour(Colouration.getEquipmentColor(this.getEnhancedInventory().getStackInSlot(i)));
        } else if (item instanceof CustomizableCollar) {
            this.colouration.setCollarColour(Colouration.getEquipmentColor(this.getEnhancedInventory().getStackInSlot(i)));
        } else if (item instanceof CustomizableAnimalEquipment) {
            this.colouration.setHarnessColour(Colouration.getEquipmentColor(this.getEnhancedInventory().getStackInSlot(i)));
        }
    }

    /*
    NBT read/write
    */
    @Override
    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);

        ListNBT geneList = new ListNBT();
        int length = this.genetics.getNumberOfSexlinkedGenes();
        int[] sexlinked = this.genetics.getSexlinkedGenes();
        int[] autosomal = this.genetics.getAutosomalGenes();
        for(int i = 0; i< length; i++){
            CompoundNBT nbttagcompound = new CompoundNBT();
            nbttagcompound.putInt("Sgene", sexlinked[i]);
            geneList.add(nbttagcompound);
        }
        length = this.genetics.getNumberOfAutosomalGenes();
        for(int i = 0; i< length; i++){
            CompoundNBT nbttagcompound = new CompoundNBT();
            nbttagcompound.putInt("Agene", autosomal[i]);
            geneList.add(nbttagcompound);
        }
        compound.put("Genes", geneList);

        ListNBT mateGeneList = new ListNBT();
            length = this.genetics.getNumberOfSexlinkedGenes();
            sexlinked = this.mateGenetics.getSexlinkedGenes();
            autosomal = this.mateGenetics.getAutosomalGenes();
        for(int i = 0; i< length; i++){
            CompoundNBT nbttagcompound = new CompoundNBT();
            nbttagcompound.putInt("Sgene", sexlinked[i]);
            mateGeneList.add(nbttagcompound);
        }
            length = this.genetics.getNumberOfAutosomalGenes();
        for(int i = 0; i< length; i++){
            CompoundNBT nbttagcompound = new CompoundNBT();
            nbttagcompound.putInt("Agene", autosomal[i]);
            mateGeneList.add(nbttagcompound);
        }
        compound.put("FatherGenes", mateGeneList);

        compound.putFloat("Hunger", hunger);

        compound.putString("Status", getEntityStatus());

        compound.putString("BirthTime", this.getBirthTime());

        compound.putBoolean("Tamed", this.isTame());

        if (canBePregnant()) {
            compound.putBoolean("Pregnant", this.pregnant);
            compound.putInt("Gestation", this.gestationTimer);
        }

        if (canLactate()) {
            compound.putInt("Lactation", this.lactationTimer);
            compound.putInt("milk", getMilkAmount());
        }

        compound.putBoolean("Collared", this.hasCollar());

        writeInventory(compound);
    }

    protected void writeInventory(CompoundNBT compound) {
        ListNBT listnbt = new ListNBT();

        if (!this.animalInventory.getStackInSlot(0).isEmpty()) {
            compound.put("Chest", this.animalInventory.getStackInSlot(0).write(new CompoundNBT()));
        }

        if (!this.animalInventory.getStackInSlot(1).isEmpty()) {
            compound.put("Saddle", this.animalInventory.getStackInSlot(1).write(new CompoundNBT()));
        }

        if (!this.animalInventory.getStackInSlot(2).isEmpty()) {
            compound.put("Armour", this.animalInventory.getStackInSlot(2).write(new CompoundNBT()));
        }

        if (!this.animalInventory.getStackInSlot(3).isEmpty()) {
            compound.put("Bridle", this.animalInventory.getStackInSlot(3).write(new CompoundNBT()));
        }

        if (!this.animalInventory.getStackInSlot(4).isEmpty()) {
            compound.put("Blanket", this.animalInventory.getStackInSlot(4).write(new CompoundNBT()));
        }

        if (!this.animalInventory.getStackInSlot(5).isEmpty()) {
            compound.put("Harness", this.animalInventory.getStackInSlot(5).write(new CompoundNBT()));
        }

        if (!this.animalInventory.getStackInSlot(6).isEmpty()) {
            compound.put("Banner", this.animalInventory.getStackInSlot(6).write(new CompoundNBT()));
        }

        for(int i = 7; i < this.animalInventory.getSizeInventory(); ++i) {
            ItemStack itemstack = this.animalInventory.getStackInSlot(i);
            if (!itemstack.isEmpty()) {
                CompoundNBT compoundnbt = new CompoundNBT();
                compoundnbt.putByte("Slot", (byte)i);
                itemstack.write(compoundnbt);
                listnbt.add(compoundnbt);
            }
        }

        compound.put("Items", listnbt);
    }

    @Override
    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);

        ListNBT geneList = compound.getList("Genes", 10);
        if (geneList.getCompound(0).contains("Sgene")) {
            int sexlinkedlength = this.genetics.getNumberOfSexlinkedGenes();
            for (int i = 0; i < sexlinkedlength; i++) {
                this.genetics.setSexlinkedGene(i, geneList.getCompound(i).getInt("Sgene"));
            }

            int length = this.genetics.getNumberOfAutosomalGenes();
            for (int i = 0; i < length; i++) {
                this.genetics.setAutosomalGene(i, geneList.getCompound(i+sexlinkedlength).getInt("Agene"));
            }
        } else {
            if (this instanceof EnhancedChicken) {
                for (int i = 0; i < 10; ++i) {
                    int gene = geneList.getCompound(i).getInt("Gene");
                    this.genetics.setSexlinkedGene(i*2, gene);
                    this.genetics.setSexlinkedGene((i*2)+1, gene);
                }
            } else {
                int length = this.genetics.getNumberOfSexlinkedGenes();
                for (int i = 0; i < length; i++) {
                    this.genetics.setSexlinkedGene(i, 1);
                }
            }

            for (int i = 0; i < geneList.size(); ++i) {
                if (i < 20 && this instanceof EnhancedChicken) {
                    this.genetics.setAutosomalGene(i, 1);
                } else {
                    this.genetics.setAutosomalGene(i, geneList.getCompound(i).getInt("Gene"));
                }
            }
        }

            geneList = compound.getList("FatherGenes", 10);
        if (geneList.getCompound(0).contains("Sgene")) {
            int sexlinkedlength = this.genetics.getNumberOfSexlinkedGenes();
            for (int i = 0; i < sexlinkedlength; i++) {
                this.mateGenetics.setSexlinkedGene(i, geneList.getCompound(i).getInt("Sgene"));
            }

            int length = this.genetics.getNumberOfAutosomalGenes();
            for (int i = 0; i < length; i++) {
                this.mateGenetics.setAutosomalGene(i, geneList.getCompound(i+sexlinkedlength).getInt("Agene"));
            }
        } else {
            if (this instanceof EnhancedChicken) {
                for (int i = 0; i < 9; ++i) {
                    int gene = geneList.getCompound(i).getInt("Gene");
                    if (gene == 10) {
                        break;
                    }
                    this.mateGenetics.setSexlinkedGene(i*2, gene);
                    this.mateGenetics.setSexlinkedGene((i*2)+1, gene);
                }
            } else {
                int length = this.genetics.getNumberOfSexlinkedGenes();
                for (int i = 0; i < length; i++) {
                    this.mateGenetics.setSexlinkedGene(i, 1);
                }
            }

            for (int i = 0; i < geneList.size(); ++i) {
                if (i < 20 && this instanceof EnhancedChicken) {
                    this.mateGenetics.setAutosomalGene(i, 1);
                }
                this.mateGenetics.setAutosomalGene(i, geneList.getCompound(i).getInt("Gene"));
            }
        }

        this.hunger = compound.getFloat("Hunger");

        this.setEntityStatus(compound.getString("Status"));

        this.setBirthTime(compound.getString("BirthTime"));

        this.setTame(compound.getBoolean("Tamed"));

        if (canBePregnant()) {
            this.pregnant = compound.getBoolean("Pregnant");
            this.gestationTimer = compound.getInt("Gestation");
        }

        if (canLactate()) {
            this.lactationTimer = compound.getInt("Lactation");
            setMilkAmount(compound.getInt("milk"));
            setMaxBagSize();
        }

        this.setBreed(compound.getString("breed"));

        this.runDemoMode = (compound.getBoolean("demo"));
        if (this.runDemoMode) {
            this.demoTimerMax = (compound.getInt("demoTimer"));
            this.setBirthTime(String.valueOf(-100000));
        }

        //from MobEntity parent
        if (compound.contains("Leash", 10)) {
            this.leashNBTTag = compound.getCompound("Leash");
        }

        geneFixer();

        setSharedGenes(this.genetics);
        initilizeAnimalSize();

        this.setCollar(compound.getBoolean("Collared"));


        readInventory(compound);
    }

    private void readInventory(CompoundNBT compound) {
        this.initInventory();

        if (compound.contains("Chest", 10)) {
            ItemStack itemstack = ItemStack.read(compound.getCompound("Chest"));
            this.animalInventory.setInventorySlotContents(0, itemstack);
        }

        if (compound.contains("Saddle", 10)) {
            ItemStack itemstack = ItemStack.read(compound.getCompound("Saddle"));
            this.animalInventory.setInventorySlotContents(1, itemstack);
        }

        if (compound.contains("Armour", 10)) {
            ItemStack itemstack = ItemStack.read(compound.getCompound("Armour"));
            this.animalInventory.setInventorySlotContents(2, itemstack);
        }

        if (compound.contains("Bridle", 10)) {
            ItemStack itemstack = ItemStack.read(compound.getCompound("Bridle"));
            this.animalInventory.setInventorySlotContents(3, itemstack);
        }

        if (compound.contains("Blanket", 10)) {
            ItemStack itemstack = ItemStack.read(compound.getCompound("Blanket"));
            this.animalInventory.setInventorySlotContents(4, itemstack);
        }

        if (compound.contains("Harness", 10)) {
            ItemStack itemstack = ItemStack.read(compound.getCompound("Harness"));
            this.animalInventory.setInventorySlotContents(5, itemstack);
        }

        if (compound.contains("Banner", 10)) {
            ItemStack itemstack = ItemStack.read(compound.getCompound("Banner"));
            this.animalInventory.setInventorySlotContents(6, itemstack);
        }

        ListNBT listnbt = compound.getList("Items", 10);

        for(int i = 0; i < listnbt.size(); ++i) {
            CompoundNBT compoundnbt = listnbt.getCompound(i);
            int j = compoundnbt.getByte("Slot") & 255;
            if (j >= 0 && j < this.animalInventory.getSizeInventory()) {
                this.animalInventory.setInventorySlotContents(j, ItemStack.read(compoundnbt));
            }
        }

        this.updateInventorySlots();
    }

    /*
    Entity Creation
    */

    protected void defaultCreateAndSpawn(EnhancedAnimalAbstract enhancedAnimalChild, World inWorld, Genes babyGenes, int childAge) {
        enhancedAnimalChild.setGenes(babyGenes);
        enhancedAnimalChild.setSharedGenes(babyGenes);
        enhancedAnimalChild.initilizeAnimalSize();
        enhancedAnimalChild.setGrowingAge(childAge); // 3 days
        enhancedAnimalChild.setBirthTime(String.valueOf(inWorld.getGameTime()));
        enhancedAnimalChild.setEntityStatus(EntityState.CHILD_STAGE_ONE.toString());
        enhancedAnimalChild.setLocationAndAngles(this.getPosX(), this.getPosY(), this.getPosZ(), this.rotationYaw, 0.0F);
    }

    @Override
    public AgeableEntity createChild(AgeableEntity ageable) {
        handlePartnerBreeding(ageable);
        this.setGrowingAge(10);
        this.resetInLove();
        ageable.setGrowingAge(10);
        ((EnhancedAnimalAbstract)ageable).resetInLove();

        ServerPlayerEntity entityplayermp = this.getLoveCause();
        if (entityplayermp == null && ((EnhancedAnimalAbstract)ageable).getLoveCause() != null) {
            entityplayermp = ((EnhancedAnimalAbstract)ageable).getLoveCause();
        }

        if (entityplayermp != null) {
            entityplayermp.addStat(Stats.ANIMALS_BRED);
            CriteriaTriggers.BRED_ANIMALS.trigger(entityplayermp, this, ((EnhancedAnimalAbstract)ageable), (AgeableEntity)null);
        }

        return null;
    }

    protected void handlePartnerBreeding(AgeableEntity ageable) {
        if (EanimodCommonConfig.COMMON.omnigenders.get()) {
            if(pregnant) {
                ((EnhancedAnimalAbstract)ageable).pregnant = true;
                ((EnhancedAnimalAbstract)ageable).setMateGenes(this.genetics);
                ((EnhancedAnimalAbstract)ageable).setMateGender(this.getIsFemale());
            } else {
                this.pregnant = true;
                this.mateGenetics = ((EnhancedAnimalAbstract)ageable).getGenes();
                this.mateGender = ((EnhancedAnimalAbstract)ageable).getIsFemale();
            }
        } else if (this.getIsFemale()) {
           //is female
           this.pregnant = true;
           this.mateGenetics = ((EnhancedAnimalAbstract)ageable).getGenes();
           this.mateGender = false;
        } else {
            //is male
            ((EnhancedAnimalAbstract)ageable).pregnant = true;
            ((EnhancedAnimalAbstract)ageable).setMateGenes(this.genetics);
            ((EnhancedAnimalAbstract)ageable).setMateGender(false);
        }
    }

    /**
     * Returns true if the mob is currently able to mate with the specified mob.
     */
    @Override
    public boolean canMateWith(AnimalEntity otherAnimal) {
        if (otherAnimal == this) {
            return false;
        } else if (otherAnimal.getClass() != this.getClass()) {
            return false;
        } else {
            if (EanimodCommonConfig.COMMON.omnigenders.get() || (this.getIsFemale() ^ ((EnhancedAnimalAbstract)otherAnimal).getIsFemale())) {
                return this.isInLove() && otherAnimal.isInLove();
            }
            return false;
        }
    }

    /*
    Inventory
    */

    @Override
    public Inventory getEnhancedInventory() {
        return this.animalInventory;
    }

    @Override
    public void onInventoryChanged(IInventory invBasic) {
        boolean flag = this.dataManager.get(HAS_COLLAR);
        boolean flag2 = this.bells;
        this.updateInventorySlots();
        if (this.ticksExisted > 20 && !flag && this.dataManager.get(HAS_COLLAR)) {
            this.playSound(SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.5F, 1.0F);
            if (!flag2 && this.bells) {
                this.playSound(SoundEvents.BLOCK_NOTE_BLOCK_CHIME, 0.5F, 1.0F);
            }
        }
        this.updateInventorySlots();
    }

    protected void updateInventorySlots() {
        boolean hasCollar = false;
        this.bells = false;
        for (int i = 1; i <= 6;i++) {
            if (this.animalInventory.getStackInSlot(i).getItem() instanceof CustomizableCollar) {
                hasCollar = true;
                if (((CustomizableCollar) this.animalInventory.getStackInSlot(i).getItem()).getHasBells()) {
                    this.bells = true;
                }
                break;
            }
        }
        this.setCollar(hasCollar);
        if (!this.world.isRemote) {
        }
    }

    protected int getInventorySize() {
        return 22;
    }

    protected void initInventory() {
        Inventory inventory = this.animalInventory;
        this.animalInventory = new Inventory(this.getInventorySize());
        if (inventory != null) {
            inventory.removeListener(this);
            int i = Math.min(inventory.getSizeInventory(), this.animalInventory.getSizeInventory());

            for(int j = 0; j < i; ++j) {
                ItemStack itemstack = inventory.getStackInSlot(j);
                if (!itemstack.isEmpty()) {
                    this.animalInventory.setInventorySlotContents(j, itemstack.copy());
                }
            }
        }

        this.animalInventory.addListener(this);
        this.updateInventorySlots();
        this.itemHandler = net.minecraftforge.common.util.LazyOptional.of(() -> new net.minecraftforge.items.wrapper.InvWrapper(this.animalInventory));
    }

    public boolean replaceItemInInventory(int inventorySlot, ItemStack itemStackIn) {
        int i = inventorySlot - 400;
        if (i >= 0 && i < 2 && i < this.animalInventory.getSizeInventory()) {
            if (i == 0 && itemStackIn.getItem() != Items.SADDLE) {
                return false;
            } else {
                return false;
            }
        } else {
            int j = inventorySlot - 500 + 2;
            if (j >= 7 && j < this.animalInventory.getSizeInventory()) {
                this.animalInventory.setInventorySlotContents(j, itemStackIn);
                return true;
            } else {
                return false;
            }
        }
    }

    protected void dropInventory() {
        super.dropInventory();
        if (this.animalInventory != null) {
            for(int i = 0; i < this.animalInventory.getSizeInventory(); ++i) {
                ItemStack itemstack = this.animalInventory.getStackInSlot(i);
                if (!itemstack.isEmpty() && !EnchantmentHelper.hasVanishingCurse(itemstack)) {
                    this.entityDropItem(itemstack);
                }
            }

        }
    }

    protected ItemStack getReplacementItemWithColour(ItemStack itemStack) {
        ItemStack replacementItem = new ItemStack(itemStack.getItem(), 1);
        ((CustomizableAnimalEquipment)replacementItem.getItem()).setColor(replacementItem, ((CustomizableAnimalEquipment)itemStack.getItem()).getColor(itemStack));
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
    public void remove(boolean keepData) {
        super.remove(keepData);
        if (!keepData && itemHandler != null) {
            itemHandler.invalidate();
            itemHandler = null;
        }
    }

    /*
    GUI
    */

    public void openGUI(PlayerEntity playerEntity) {
        if (!this.world.isRemote && (!this.isBeingRidden() || this.isPassenger(playerEntity))) {
            this.openInfoInventory(this, this.animalInventory, playerEntity);
        }
    }

    public void openInfoInventory(EnhancedAnimalAbstract enhancedAnimal, IInventory inventoryIn, PlayerEntity playerEntity) {
        if(!playerEntity.world.isRemote) {

            EnhancedAnimalInfo animalInfo = new EnhancedAnimalInfo();
            animalInfo.health = (int)(10 * (this.getHealth() / this.getMaxHealth()));
            animalInfo.hunger = (int)(this.getHunger() / 7200);
            animalInfo.isFemale = this.getIsFemale();
            animalInfo.pregnant = (10 * this.gestationTimer)/gestationConfig();
            animalInfo.name = this.getAnimalsName(getSpecies());
            animalInfo.agePrefix = this.getAnimalsAgeString();

            if(playerEntity instanceof ServerPlayerEntity) {
                ServerPlayerEntity entityPlayerMP = (ServerPlayerEntity)playerEntity;
                NetworkHooks.openGui(entityPlayerMP, new INamedContainerProvider() {
                    @Override
                    public Container createMenu(int windowId, PlayerInventory inventory, PlayerEntity player) {
                        return new EnhancedAnimalContainer(windowId, inventory, enhancedAnimal, animalInfo);
                    }

                    @Override
                    public ITextComponent getDisplayName() {
                        return new TranslationTextComponent("eanimod.animalinfocontainer");
                    }
                }, buf -> {
                    buf.writeInt(enhancedAnimal.getEntityId());
                    buf.writeString(animalInfo.serialiseToString());
                });
            }
        }
    }

    protected String getAnimalsName(String species) {
        String name = species;
        if (this.getCustomName() != null) {
            name = this.getCustomName().getUnformattedComponentText();
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
                return "Young ";
            } else if (age > adultAge/3) {
                return "Baby ";
            } else {
                return "Newborn ";
            }
        }
        return "ADULT";
    }

    /*
    Client Sided Work
    */

    @OnlyIn(Dist.CLIENT)
    public void handleStatusUpdate(byte id) {
        if (id == 10) {
            this.animalEatingTimer = 40;
        } else {
            super.handleStatusUpdate(id);
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
            return ((float)Math.PI / 5F) + ((float)Math.PI * 7F / 100F) * MathHelper.sin(f * 28.7F);
        } else {
            return this.animalEatingTimer > 0 ? ((float)Math.PI / 5F) : this.rotationPitch * 0.017453292F;
        }
    }

    //------------

    public boolean setTamedBy(PlayerEntity player) {
        //TODO save player's name to be displayed in GUI
//        this.setOwnerUniqueId(player.getUniqueID());
        this.setTame(true);
        if (player instanceof ServerPlayerEntity) {
            CriteriaTriggers.TAME_ANIMAL.trigger((ServerPlayerEntity)player, this);
        }

        this.world.setEntityState(this, (byte)7);
        return true;
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        //TODO set this to a separate item or type of item for force breeding
        return this.BREED_ITEMS.test(stack);
    }

    public void setSharedGenes(Genes genes) {
        this.dataManager.set(SHARED_GENES, genes.getGenesAsString());
    }

    @OnlyIn(Dist.CLIENT)
    public Genes getSharedGenes() {
        if(this.genesSplitForClient==null) {
            String sharedGenes = this.dataManager.get(SHARED_GENES);
            if (sharedGenes.isEmpty()) {
                return null;
            }

            Genes genes = new Genes(sharedGenes);

            this.genesSplitForClient = genes;
            return genes;
        }
        return this.genesSplitForClient;
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

        //todo this is only temporarity until we have alpha textures
        if (this.enhancedAnimalAlphaTextures.isEmpty()) {
            return null;
        }

        return this.enhancedAnimalAlphaTextures.stream().toArray(String[]::new);
    }

    protected String getCompiledTextures(String eanimal) {
        String compiledTextures = this.texturesIndexes.stream().collect(Collectors.joining("/",eanimal+"/",""));
        compiledTextures = compiledTextures + this.equipmentTextures.values().stream().flatMap(Collection::stream).collect(Collectors.joining("/"));
        return compiledTextures;
    }

        protected void geneFixer() {
        if (!this.breed.isEmpty()) {
            this.genetics = createInitialBreedGenes(this.world, new BlockPos(this), this.breed);
            setInitialDefaults();
//            this.setBirthTime(String.valueOf(this.world.getWorld().getGameTime() - (rand.nextInt(180000-24000) + 24000)));
            this.setBirthTime(String.valueOf(this.world.getWorld().getGameTime() - (5000000)));
        } else if (this.genetics.getAutosomalGene(0) == 0) {
            this.genetics = createInitialGenes(this.world, new BlockPos(this), true);
            setInitialDefaults();
            this.setBirthTime(String.valueOf(this.world.getWorld().getGameTime() - (rand.nextInt(180000-24000) + 24000)));
        }
    }

    public void setMateGenes(Genes genes){
        this.mateGenetics = genes;
    }

    public void setMateGender(Boolean gender){
        this.mateGender = gender;
    }

    //overriden to prevent aging up when fed
    @Override
    public void ageUp(int growthSeconds, boolean updateForcedAge) {
        if (!updateForcedAge && EanimodCommonConfig.COMMON.feedGrowth.get()) {
            super.ageUp(growthSeconds, updateForcedAge);
        }
    }

    protected ILivingEntityData commonInitialSpawnSetup(IWorld inWorld, @Nullable ILivingEntityData livingdata, int childAge, int ageMinimum, int ageMaximum) {
        Genes spawnGenes;

        if (livingdata instanceof GroupData) {
            spawnGenes = new Genes(((GroupData)livingdata).groupGenes).makeChild(true, false, ((GroupData)livingdata).groupGenes);
        } else {
            spawnGenes = createInitialGenes(this.world, new BlockPos(this), false);
            livingdata = new GroupData(spawnGenes);
        }

        this.genetics = spawnGenes;
        setInitialDefaults();

        int birthMod = ThreadLocalRandom.current().nextInt(ageMinimum, ageMaximum);
        this.setBirthTime(String.valueOf(inWorld.getWorld().getGameTime() - birthMod));
        if (birthMod < childAge) {
            this.setGrowingAge(birthMod - childAge);
        }

        return livingdata;
    }

    protected abstract Genes createInitialGenes(IWorld inWorld, BlockPos pos, boolean isDomestic);

    protected abstract Genes createInitialBreedGenes(IWorld inWorld, BlockPos pos, String breed);

    protected void setInitialDefaults() {
        setSharedGenes(this.genetics);
        initilizeAnimalSize();
    }

    public static class GroupData implements ILivingEntityData {
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
                if (this.animalInventory.getStackInSlot(i).getItem() instanceof CustomizableCollar) {
                    collarSlot = this.animalInventory.getStackInSlot(i);
                    break;
                }
            }
            if (collarSlot != ItemStack.EMPTY) {
                Item collar = collarSlot.getItem();
                collarTextures.add(COLLAR);
                if (collar == ModItems.COLLAR_CLOTH_RING) {
                    collarTextures.add(COLLAR_HARDWARE[0]);
                } else if (collar == ModItems.COLLAR_CLOTH_G) {
                    collarTextures.add(COLLAR_HARDWARE[1]);
                } else if (collar == ModItems.COLLAR_CLOTH_D) {
                    collarTextures.add(COLLAR_HARDWARE[2]);
                } else if (collar == ModItems.COLLAR_CLOTH_BELL) {
                    collarTextures.add(COLLAR_HARDWARE[3]);
                } else if (collar == ModItems.COLLAR_CLOTH_GBELL) {
                    collarTextures.add(COLLAR_HARDWARE[4]);
                } else if (collar == ModItems.COLLAR_CLOTH_DBELL) {
                    collarTextures.add(COLLAR_HARDWARE[5]);
                } else if (collar == ModItems.COLLAR_LEATHER) {
                    collarTextures.add(COLLAR_TEXTURE);
                } else if (collar == ModItems.COLLAR_LEATHER_RING) {
                    collarTextures.add(COLLAR_TEXTURE);
                    collarTextures.add(COLLAR_HARDWARE[0]);
                } else if (collar == ModItems.COLLAR_LEATHER_G) {
                    collarTextures.add(COLLAR_TEXTURE);
                    collarTextures.add(COLLAR_HARDWARE[1]);
                } else if (collar == ModItems.COLLAR_LEATHER_D) {
                    collarTextures.add(COLLAR_TEXTURE);
                    collarTextures.add(COLLAR_HARDWARE[2]);
                } else if (collar == ModItems.COLLAR_LEATHER_BELL) {
                    collarTextures.add(COLLAR_TEXTURE);
                    collarTextures.add(COLLAR_HARDWARE[3]);
                } else if (collar == ModItems.COLLAR_LEATHER_GBELL) {
                    collarTextures.add(COLLAR_TEXTURE);
                    collarTextures.add(COLLAR_HARDWARE[4]);
                } else if (collar == ModItems.COLLAR_LEATHER_DBELL) {
                    collarTextures.add(COLLAR_TEXTURE);
                    collarTextures.add(COLLAR_HARDWARE[5]);
                }
            }
        }

        return collarTextures;
    }
}
