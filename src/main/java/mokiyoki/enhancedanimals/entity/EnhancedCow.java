package mokiyoki.enhancedanimals.entity;

import mokiyoki.enhancedanimals.ai.general.EnhancedLookAtGoal;
import mokiyoki.enhancedanimals.ai.general.EnhancedLookRandomlyGoal;
import mokiyoki.enhancedanimals.ai.general.EnhancedPanicGoal;
import mokiyoki.enhancedanimals.ai.general.EnhancedTemptGoal;
import mokiyoki.enhancedanimals.ai.general.EnhancedWaterAvoidingRandomWalkingEatingGoal;
import mokiyoki.enhancedanimals.ai.general.cow.EnhancedAINurseFromMotherGoal;
import mokiyoki.enhancedanimals.init.ModBlocks;
import mokiyoki.enhancedanimals.init.ModItems;
import mokiyoki.enhancedanimals.items.DebugGenesBook;
import mokiyoki.enhancedanimals.config.EanimodCommonConfig;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.BreedGoal;
import net.minecraft.entity.ai.goal.FollowParentGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.AirItem;
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
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import static mokiyoki.enhancedanimals.util.handlers.EventRegistry.ENHANCED_COW;

public class EnhancedCow extends AnimalEntity implements EnhancedAnimal {

    //avalible UUID spaces : [ S X X X X 5 6 7 - 8 9 10 11 - 12 13 14 15 - 16 17 18 19 - 20 21 22 23 24 25 26 27 28 29 30 31 ]

    private static final DataParameter<Boolean> SADDLED = EntityDataManager.createKey(EnhancedCow.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Integer> BOOST_TIME = EntityDataManager.createKey(EnhancedCow.class, DataSerializers.VARINT);
    private static final DataParameter<String> SHARED_GENES = EntityDataManager.<String>createKey(EnhancedCow.class, DataSerializers.STRING);
    private static final DataParameter<Float> COW_SIZE = EntityDataManager.createKey(EnhancedCow.class, DataSerializers.FLOAT);
    private static final DataParameter<Float> BAG_SIZE = EntityDataManager.createKey(EnhancedCow.class, DataSerializers.FLOAT);
    protected static final DataParameter<String> COW_STATUS = EntityDataManager.createKey(EnhancedCow.class, DataSerializers.STRING);
    protected static final DataParameter<Boolean> SLEEPING = EntityDataManager.createKey(EnhancedCow.class, DataSerializers.BOOLEAN);
    protected static final DataParameter<Boolean> RESET_TEXTURE = EntityDataManager.createKey(EnhancedCow.class, DataSerializers.BOOLEAN);
    private static final DataParameter<String> MOOSHROOM_UUID = EntityDataManager.createKey(EnhancedCow.class, DataSerializers.STRING);
    private static final DataParameter<Integer> MILK_AMOUNT = EntityDataManager.createKey(EnhancedCow.class, DataSerializers.VARINT);
    private static final DataParameter<String> HORN_ALTERATION = EntityDataManager.<String>createKey(EnhancedCow.class, DataSerializers.STRING);
    private static final DataParameter<String> BIRTH_TIME = EntityDataManager.<String>createKey(EnhancedCow.class, DataSerializers.STRING);

    private static final String[] COW_TEXTURES_BASE = new String[] {
            "solid_white.png", "solid_lightcream.png", "solid_cream.png", "solid_silver.png"
    };

    private static final String[] COW_TEXTURES_UDDER = new String[] {
            "udder_black.png", "udder_brown.png", "udder_pink.png"
    };

    private static final String[] COW_TEXTURES_RED = new String[] {
            "", "r_solid.png", "r_shaded.png"
    };

    private static final String[] COW_TEXTURES_BLACK = new String[] {
            "", "b_shoulders.png", "b_wildtype.png", "b_wildtype_darker1.png", "b_wildtype_dark.png", "b_solid.png", "b_brindle.png"
    };

    private static final String[] COW_TEXTURES_SKIN = new String[] {
            "skin_black.png", "skin_brown.png", "skin_pink.png"
    };

    private static final String[] COW_TEXTURES_ROAN = new String[] {
            "", "spot_roan0.png",
                "solid_white.png"
    };

    private static final String[] COW_TEXTURES_SPECKLED = new String[] {
            "", "spot_speckled0.png",
                "spot_whitespeckled0.png"
    };

    private static final String[] COW_TEXTURES_WHITEFACE = new String[] {
            "", "spot_whiteface0.png",
                "spot_wfcoloursided0.png",
                "spot_coloursided0.png",
                "spot_pibald0.png", "spot_pibald1.png", "spot_pibald2.png", "spot_pibald3.png", "spot_pibald4.png", "spot_pibald5.png", "spot_pibald6.png", "spot_pibald7.png", "spot_pibald8.png", "spot_pibald9.png","spot_pibalda.png", "spot_pibaldb.png", "spot_pibaldc.png", "spot_pibaldd.png", "spot_pibalde.png", "spot_pibaldf.png",
    };

    private static final String[] COW_TEXTURES_WHITEFACEHEAD = new String[] {
            "", "",
                "",
                "",
                "spot_pibald_head0.png", "spot_pibald_head1.png", "spot_pibald_head2.png", "spot_pibald_head3.png", "spot_pibald_head4.png","spot_pibald_head5.png", "spot_pibald_head6.png", "spot_pibald_head7.png", "spot_pibald_head8.png", "spot_pibald_head9.png","spot_pibald_heada.png", "spot_pibald_headb.png", "spot_pibald_headc.png", "spot_pibald_headd.png", "spot_pibald_heade.png", "spot_pibald_headf.png",
    };

    private static final String[] COW_TEXTURES_BELTED = new String[] {
            "", "spot_belt0.png", "spot_belt1.png", "spot_belt2.png", "spot_belt3.png", "spot_belt4.png", "spot_belt5.png", "spot_belt6.png", "spot_belt7.png", "spot_belt8.png", "spot_belt9.png", "spot_belta.png", "spot_beltb.png", "spot_beltc.png", "spot_beltd.png", "spot_belte.png", "spot_beltf.png",
                "spot_blaze0.png",
                "b_spot_brockling0.png",
                "r_spot_brockling0.png"
    };

    private static final String[] COW_TEXTURES_COLOURSIDED = new String[] {
            "", "spot_coloursided0.png"
    };

    private static final String[] COW_TEXTURES_HOOVES = new String[] {
            "hooves_black.png", "hooves_black_dwarf.png"
    };

    private static final String[] COW_TEXTURES_EYES = new String[] {
            "eyes_black.png"
    };

    private static final String[] COW_TEXTURES_HORNS = new String[] {
            "", "horns_black.png"
    };

    private static final String[] COW_TEXTURES_COAT = new String[] {
            "coat_normal.png", "coat_smooth.png", "coat_furry.png"
    };

    private static final String[] COW_TEXTURES_TEST = new String[] {
            "cowbase.png", "cowtest.png"
    };


    protected static final Ingredient TEMPTATION_ITEMS = Ingredient.fromItems(Blocks.MELON, Blocks.PUMPKIN, Blocks.GRASS, Blocks.HAY_BLOCK, Blocks.VINE, Blocks.TALL_GRASS, Blocks.OAK_LEAVES, Blocks.DARK_OAK_LEAVES, Items.CARROT, Items.WHEAT, Items.SUGAR, Items.APPLE, ModBlocks.UnboundHay_Block);
    private static final Ingredient MILK_ITEMS = Ingredient.fromItems(ModItems.Milk_Bottle, ModItems.Half_Milk_Bottle);
    private static final Ingredient BREED_ITEMS = Ingredient.fromItems(Blocks.HAY_BLOCK, Items.WHEAT);

    Map<Item, Integer> foodWeightMap = new HashMap() {{
        put(new ItemStack(Blocks.MELON).getItem(), 10000);
        put(new ItemStack(Blocks.PUMPKIN).getItem(), 10000);
        put(new ItemStack(Items.TALL_GRASS).getItem(), 6000);
        put(new ItemStack(Items.GRASS).getItem(), 3000);
        put(new ItemStack(Items.VINE).getItem(), 3000);
        put(new ItemStack(Blocks.HAY_BLOCK).getItem(), 54000);
        put(new ItemStack(Blocks.OAK_LEAVES).getItem(), 1000);
        put(new ItemStack(Blocks.DARK_OAK_LEAVES).getItem(), 1000);
        put(new ItemStack(Items.CARROT).getItem(), 1500);
        put(new ItemStack(Items.WHEAT).getItem(), 6000);
        put(new ItemStack(Items.SUGAR).getItem(), 1500);
        put(new ItemStack(Items.APPLE).getItem(), 1500);
        put(new ItemStack(ModBlocks.UnboundHay_Block).getItem(), 54000);
    }};

    protected boolean resetTexture = true;

    private static final int WTC = EanimodCommonConfig.COMMON.wildTypeChance.get();
    protected List<String> cowTextures = new ArrayList<>();
    private static final int GENES_LENGTH = 120;
    private int[] genes = new int[GENES_LENGTH];
    private int[] mateGenes = new int[GENES_LENGTH];
    protected int[] mitosisGenes = new int[GENES_LENGTH];
    protected int[] mateMitosisGenes = new int[GENES_LENGTH];

    protected float maxBagSize;
    protected float cowSize;
    protected int hunger = 0;
    protected int healTicks = 0;
    protected boolean aiConfigured = false;

    private String mooshroomUUID = "0";

    private float[] cowColouration = null;
    protected String motherUUID = "";

    protected int timeUntilNextMilk;
    protected int cowTimer;
    protected int ageTimer;
    protected EnhancedWaterAvoidingRandomWalkingEatingGoal wanderEatingGoal;
    protected int gestationTimer = 0;
    protected int lactationTimer = 0;
    protected boolean pregnant = false;
    protected Boolean sleeping = false;
    protected int awokenTimer = 0;
    protected int recalculateSizeTimer = 1000;

    private boolean boosting;
    private int boostTime;
    private int totalBoostTime;

    protected Boolean reload = false; //used in a toggle manner

    public EnhancedCow(EntityType<? extends EnhancedCow> entityType, World worldIn) {
        super(entityType, worldIn);
        // cowsize from .7 to 1.5 max bag size is 1 to 1.5
        //large cows make from 30 to 12 milk points per day, small cows make up to 1/4
        this.timeUntilNextMilk = this.rand.nextInt(600) + Math.round((800 + ((1.5F - maxBagSize)*2400)) * (cowSize/1.5F)) - 300;
    }

    @Override
    protected void registerGoals() {
        //Todo add the temperamants
//        this.eatGrassGoal = new EnhancedGrassGoal(this, null);
        this.goalSelector.addGoal(0, new SwimGoal(this));
//        this.goalSelector.addGoal(5, this.eatGrassGoal);
        this.goalSelector.addGoal(7, new EnhancedLookAtGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.addGoal(8, new EnhancedLookRandomlyGoal(this));
    }

    public void notifyDataManagerChange(DataParameter<?> key) {
        if (BOOST_TIME.equals(key) && this.world.isRemote) {
            this.boosting = true;
            this.boostTime = 0;
            this.totalBoostTime = this.dataManager.get(BOOST_TIME);
        }

        super.notifyDataManagerChange(key);
    }

    protected void updateAITasks()
    {
        this.cowTimer = this.wanderEatingGoal.getEatingGrassTimer();
        super.updateAITasks();
    }

    protected void registerData() {
        super.registerData();
        this.dataManager.register(SHARED_GENES, new String());
        this.dataManager.register(HORN_ALTERATION, "0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0");
        this.dataManager.register(COW_SIZE, 0.0F);
        this.dataManager.register(BAG_SIZE, 0.0F);
        this.dataManager.register(COW_STATUS, new String());
        this.dataManager.register(SLEEPING, false);
        this.dataManager.register(RESET_TEXTURE, false);
        this.dataManager.register(MOOSHROOM_UUID, "0");
        this.dataManager.register(MILK_AMOUNT, 0);
        this.dataManager.register(BIRTH_TIME, "0");
        this.dataManager.register(SADDLED, false);
        this.dataManager.register(BOOST_TIME, 0);
    }

    @Nullable
    public Entity getControllingPassenger() {
        return this.getPassengers().isEmpty() ? null : this.getPassengers().get(0);
    }

    public boolean canBeSteered() {
        Entity entity = this.getControllingPassenger();
        if (!(entity instanceof PlayerEntity)) {
            return false;
        } else {
            PlayerEntity playerentity = (PlayerEntity)entity;
            return playerentity.getHeldItemMainhand().getItem() == Items.CARROT_ON_A_STICK || playerentity.getHeldItemOffhand().getItem() == Items.CARROT_ON_A_STICK;
        }
    }

    protected void setCowSize(float size) {
        this.dataManager.set(COW_SIZE, size);
    }

    public float getSize() {
        return this.dataManager.get(COW_SIZE);
    }

    private void setBagSize(float size) { this.dataManager.set(BAG_SIZE, size); }

    public float getBagSize() {
        return this.dataManager.get(BAG_SIZE);
    }

    protected void setCowStatus(String status) { this.dataManager.set(COW_STATUS, status); }

    public String getCowStatus() { return this.dataManager.get(COW_STATUS); }

    protected void setMooshroomUUID(String status) {
        if (!status.equals("")) {
            this.dataManager.set(MOOSHROOM_UUID, status);
            this.mooshroomUUID = status;
        }
    }

    public String getMooshroomUUID() { return mooshroomUUID; }

    protected void setMilkAmount(Integer milkAmount) {
        this.dataManager.set(MILK_AMOUNT, milkAmount);
    }

    public Integer getMilkAmount() { return this.dataManager.get(MILK_AMOUNT); }

    protected void setBirthTime(String birthTime) {
        this.dataManager.set(BIRTH_TIME, birthTime);
    }

    public String getBirthTime() { return this.dataManager.get(BIRTH_TIME); }

    private int getAge() {
        if (!(getBirthTime() == null) && !getBirthTime().equals("") && !getBirthTime().equals(0)) {
            return (int)(this.world.getWorldInfo().getGameTime() - Long.parseLong(getBirthTime()));
        } else {
            return 108000;
        }
    }

    public void setSleeping(Boolean sleeping) {
        this.sleeping = sleeping;
        this.dataManager.set(SLEEPING, sleeping);
    }

    @Override
    public Boolean isAnimalSleeping() {
        if (this.sleeping == null) {
            return false;
        } else {
            sleeping = this.dataManager.get(SLEEPING);
            return sleeping;
        }
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

    @Override
    public void awaken() {
        this.awokenTimer = 200;
        setSleeping(false);
    }

    @Override
    public Inventory getEnhancedInventory() {
        return null;
    }

    public int getHunger(){ return hunger; }

    public void decreaseHunger(int decrease) {
        if (this.hunger - decrease < 0) {
            this.hunger = 0;
        } else {
            this.hunger = this.hunger - decrease;
        }
    }

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

    protected SoundEvent getAmbientSound() { return SoundEvents.ENTITY_COW_AMBIENT; }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) { return SoundEvents.ENTITY_COW_HURT; }

    protected SoundEvent getDeathSound() { return SoundEvents.ENTITY_COW_DEATH; }

    protected void playStepSound(BlockPos pos, BlockState blockIn) { this.playSound(SoundEvents.ENTITY_COW_STEP, 0.15F, 1.0F); }

    /**
     * Returns the volume for the sounds this mob makes.
     */
    protected float getSoundVolume() {
        return 0.4F;
    }

    protected void registerAttributes() {
        super.registerAttributes();
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(8.0D);
        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.23000000417232513D);
    }

//    @Override
//    public float getRenderScale() {
//        int age = getAge();
//        float size = getSize();
//        if (age < 108000) {
//            float ageResult = age/108000.0F;
//            float finalCowSize = ((( 1.5F * ageResult) + 1.5F) / 3.0F) * size;
//            return finalCowSize;
//        } else {
//            return size;
//        }
//        return getSize();
//    }

    public void livingTick() {
        super.livingTick();

        if (this.world.isRemote) {
            this.cowTimer = Math.max(0, this.cowTimer - 1);
//            recalculateSizeTimer--;
//            if (recalculateSizeTimer >= 0) {
//                recalculateSize();
//                recalculateSizeTimer = 1000;
//            }
        } else {

            if (((this.world.getDayTime()%24000 >= 12600 && this.world.getDayTime()%24000 <= 22000) || this.world.isThundering()) && awokenTimer == 0 && !sleeping) {
                setSleeping(true);
                healTicks = 0;
            } else if (awokenTimer > 0) {
                awokenTimer--;
            } else if (this.world.isDaytime() && sleeping) {
                setSleeping(false);
            }

            if(pregnant) {
                gestationTimer++;
                int days = EanimodCommonConfig.COMMON.gestationDaysCow.get();
                if (days/2 < gestationTimer) {
                    setCowStatus(EntityState.PREGNANT.toString());
                }
                if (hunger > 12000 && days !=0) {
                    pregnant = false;
                    setCowStatus(EntityState.ADULT.toString());
                }
                if (gestationTimer >= days) {
                    pregnant = false;
                    gestationTimer = 0;
                    setCowStatus(EntityState.MOTHER.toString());
                    //sets milk amount at first milk
                    Integer milk = Math.round((30*(cowSize/1.5F)*(maxBagSize/1.5F)) * 0.75F);
                    setMilkAmount(milk);

                    float milkBagSize = milk / (30*(cowSize/1.5F)*(maxBagSize/1.5F));
                    this.setBagSize((1.1F*milkBagSize*(maxBagSize-1.0F))+1.0F);

                    lactationTimer = -48000;

                    mixMateMitosisGenes();
                    mixMitosisGenes();

                    createAndSpawnEnhancedChild(this.world);

                }
            }
            if (this.getIdleTime() < 100) {
                if (hunger <= 72000) {

                    if (sleeping) {
                        int days = EanimodCommonConfig.COMMON.gestationDaysCow.get();
                        if (hunger <= days*(0.50)) {
                            hunger = hunger++;
                        }
                        healTicks++;
                        if (healTicks > 100 && hunger < 6000 && this.getMaxHealth() > this.getHealth()) {
                            this.heal(2.0F);
                            hunger = hunger + 1000;
                            healTicks = 0;
                        }
                    } else {
                        hunger = hunger + 2;
                    }
//                if (cowSize >= 1.3) {
//                    hunger++;
//                    if (ticksExisted % 2 == 0) {
//                        hunger++;
//                    }
//                } else if (cowSize >= 1.1) {
//                    hunger++;
//                    if (ticksExisted % 4 == 0) {
//                        hunger++;
//                    }
//                } else if (cowSize >= 0.9) {
//                    hunger++;
//                } else if (cowSize >= 0.8) {
//                    if (ticksExisted % 2 == 0 || ticksExisted % 3 == 0) {
//                        hunger++;
//                    }
//                } else {
//                    if (ticksExisted % 2 == 0) {
//                        hunger++;
//                    }
//                }

//                //cowSize 0.7 to 1.5 >> range is 0.0 to 0.8  turn to 3 to 1
//                if (ticksExisted % Math.round(1 + (1.5F - cowSize)*2.5F) == 0) {
//                    hunger++;
//                }
                }

                if (getCowStatus().equals(EntityState.MOTHER.toString())) {
                    if (hunger <= 24000) {
                        if (--this.timeUntilNextMilk <= 0) {
                            int milk = getMilkAmount();
                            if (milk < (30*(cowSize/1.5F))*(maxBagSize/1.5F)) {
                                milk++;
                                setMilkAmount(milk);
                                this.timeUntilNextMilk = this.rand.nextInt(600) + Math.round((800 + ((1.5F - maxBagSize)*1200)) * (cowSize/1.5F)) - 300;

                                //this takes the number of milk points a cow has over the number possible to make a number between 0 and 1.
                                float milkBagSize = milk / (30*(cowSize/1.5F)*(maxBagSize/1.5F));

                                this.setBagSize((1.1F*milkBagSize*(maxBagSize-1.0F))+1.0F);

                            }
                        }
                    }

                    if (timeUntilNextMilk <= 0) {
                        lactationTimer++;
                    } else if (getMilkAmount() <= 5 && lactationTimer >= -36000) {
                        lactationTimer--;
                    }

                    if (lactationTimer == 0) {
                        setCowStatus(EntityState.ADULT.toString());
                    }
                }
                if (this.isChild()) {
                    if (getCowStatus().equals(EntityState.CHILD_STAGE_ONE.toString()) && this.getGrowingAge() < -16000) {
                        if(hunger < 5000) {
                            setCowStatus(EntityState.CHILD_STAGE_TWO.toString());
                        } else {
                            this.setGrowingAge(-16500);
                        }
                    } else if (getCowStatus().equals(EntityState.CHILD_STAGE_TWO.toString()) && this.getGrowingAge() < -8000) {
                        if(hunger < 5000) {
                            setCowStatus(EntityState.CHILD_STAGE_THREE.toString());
                        } else {
                            this.setGrowingAge(-8500);
                        }
                    }
                } else if (getCowStatus().equals(EntityState.CHILD_STAGE_THREE.toString())) {
                    setCowStatus(EntityState.ADULT.toString());

                    //TODO remove the child follow mother ai

                }
            }
            lethalGenes();
        }
    }

    protected void createAndSpawnEnhancedChild(World inWorld) {
        EnhancedCow enhancedcow = ENHANCED_COW.create(this.world);
        int[] babyGenes = getCalfGenes(this.mitosisGenes, this.mateMitosisGenes);
        enhancedcow.setGenes(babyGenes);
        enhancedcow.setSharedGenes(babyGenes);
        enhancedcow.setCowSize();
        enhancedcow.setGrowingAge(-84000);

        enhancedcow.setBirthTime(String.valueOf(inWorld.getGameTime()));
        enhancedcow.setCowStatus(EntityState.CHILD_STAGE_ONE.toString());
        enhancedcow.setLocationAndAngles(this.getPosX(), this.getPosY(), this.getPosZ(), this.rotationYaw, 0.0F);
        enhancedcow.setMotherUUID(this.getUniqueID().toString());
        enhancedcow.configureAI();
        this.world.addEntity(enhancedcow);
    }

    protected void setCowSize(){
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

//        size = 0.7F;

        //        0.6F <= size <= 1.5F
        this.cowSize = size;
        this.setCowSize(size);
    }

    public void lethalGenes(){

        if(genes[26] == 1 && genes[27] == 1) {
            this.remove();
        }
    }

    public void setMotherUUID(String motherUUID) {
        this.motherUUID = motherUUID;
    }

    public String getMotherUUID() {
        return this.motherUUID;
    }

    public void setSharedGenes(int[] genes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < genes.length; i++) {
            sb.append(genes[i]);
            if (i != genes.length - 1) {
                sb.append(",");
            }
        }
        this.dataManager.set(SHARED_GENES, sb.toString());
    }

    public int[] getSharedGenes() {
        String sharedGenes = ((String) this.dataManager.get(SHARED_GENES)).toString();
        if (sharedGenes.isEmpty()) {
            return null;
        }
        String[] genesToSplit = sharedGenes.split(",");
        int[] sharedGenesArray = new int[genesToSplit.length];

        for (int i = 0; i < sharedGenesArray.length; i++) {
            //parse and store each value into int[] to be returned
            sharedGenesArray[i] = Integer.parseInt(genesToSplit[i]);
        }
        return sharedGenesArray;
    }

//    public void setHornAlteration(int index, String value) {
//        String hornsAlterationsString = ((String) this.dataManager.get(HORN_ALTERATION)).toString();
//
//        String[] hornAlterations = hornsAlterationsString.split(",");
//        if (value.equals("reset")) {
//            hornAlterations[index] = "0";
//
//        } else {
////            if (index >= 30) {
//                hornAlterations[index]= Float.toString(Float.valueOf(hornAlterations[index])+Float.valueOf(value));
//                if (hornAlterations[index].startsWith("-") || hornAlterations[index].startsWith("10")) {
//                    hornAlterations[index] = "9";
//                }
////            }
////            else {
////                hornAlterations[index]= Float.toString(Float.valueOf(hornAlterations[index])+Float.valueOf(value));
////                if (hornAlterations[index].startsWith("1.6")) {
////                    hornAlterations[index] = "-1.6";
////                }
////            }
//        }
//
//        StringBuilder sb = new StringBuilder();
//        for (int i = 0; i < hornAlterations.length; i++) {
//            sb.append(hornAlterations[i]);
//            if (i != hornAlterations.length - 1) {
//                sb.append(",");
//            }
//        }
//
//        this.dataManager.set(HORN_ALTERATION, sb.toString());
//    }

//    @OnlyIn(Dist.CLIENT)
//    public float[] getHornAlteration() {
//        String hornsAlterations = ((String) this.dataManager.get(HORN_ALTERATION)).toString();
//
//        String[] hornAlterations = hornsAlterations.split(",");
//
//        float[] hornsArray = new float[hornAlterations.length];
//
//        for (int i = 0; i < hornsArray.length; i++) {
//            //parse and store each value into int[] to be returned
//            hornsArray[i] = Float.valueOf(hornAlterations[i]);
//        }
//        return hornsArray;
//    }

    @Override
    protected boolean canDropLoot() { return true; }

    protected void dropSpecialItems(DamageSource source, int looting, boolean recentlyHitIn) {
        super.dropSpecialItems(source, looting, recentlyHitIn);
        this.isBurning();
        float cowSize = this.getSize();
        float age = this.getAge();
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
            int i = this.rand.nextInt(100);
            if (meatChanceMod > i) {
                meatDrop++;
            }
            i = this.rand.nextInt(100);
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
            int i = this.rand.nextInt(4);
            if (i>=1) {
                meatDrop++;
            }
            //25% chance leather
            i = this.rand.nextInt(4);
            if (i==0) {
                meatDrop++;
            }

        } else if (cowThickness == 4){

            //50% chance meat
            int i = this.rand.nextInt(2);
            if (i==1) {
                meatDrop++;
            }
            //50% chance leather
            i = this.rand.nextInt(2);
            if (i==1) {
                leatherDrop++;
            }

        } else if (cowThickness == 3){

            //25% chance meat
            int i = this.rand.nextInt(4);
            if (i==0) {
                meatDrop++;
            }
            //75% chance leather
            i = this.rand.nextInt(4);
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

            int i = this.rand.nextInt(100);
            if (meatChanceMod > i) {
                meatDrop++;
            }
        }

        if (leatherDrop < 0) {
            leatherDrop = 0;
        }
        if (meatDrop < 0) {
            meatDrop = 0;
        }

        if (this.isBurning()){
            ItemStack cookedBeefStack = new ItemStack(Items.COOKED_BEEF, meatDrop);
            this.entityDropItem(cookedBeefStack);
        }else {
            ItemStack beefStack = new ItemStack(Items.BEEF, meatDrop);
            ItemStack leatherStack = new ItemStack(Items.LEATHER, leatherDrop);
            this.entityDropItem(beefStack);
            this.entityDropItem(leatherStack);
        }
    }

    public boolean isBreedingItem(ItemStack stack) {
        return BREED_ITEMS.test(stack);
    }

    public AgeableEntity createChild(AgeableEntity ageable) {
        if(pregnant) {
            ((EnhancedCow)ageable).pregnant = true;
            ((EnhancedCow)ageable).setMateGenes(this.genes);
            ((EnhancedCow)ageable).mixMateMitosisGenes();
            ((EnhancedCow)ageable).mixMitosisGenes();
        } else {
            pregnant = true;
            this.mateGenes = ((EnhancedCow) ageable).getGenes();
            mixMateMitosisGenes();
            mixMitosisGenes();
        }


        this.setGrowingAge(10);
        this.resetInLove();
        ageable.setGrowingAge(10);
        ((EnhancedCow)ageable).resetInLove();

        ServerPlayerEntity entityplayermp = this.getLoveCause();
        if (entityplayermp == null && ((EnhancedCow)ageable).getLoveCause() != null) {
            entityplayermp = ((EnhancedCow)ageable).getLoveCause();
        }

        if (entityplayermp != null) {
            entityplayermp.addStat(Stats.ANIMALS_BRED);
            CriteriaTriggers.BRED_ANIMALS.trigger(entityplayermp, this, ((EnhancedCow)ageable), (AgeableEntity)null);
        }

        return null;
    }

    @OnlyIn(Dist.CLIENT)
    public String getCowTexture() {
        if (this.cowTextures.isEmpty()) {
            this.setTexturePaths();
        } else if (!this.reload && getReloadTexture() || this.reload && !getReloadTexture()) {
            this.cowTextures.removeAll(this.cowTextures);
            this.setTexturePaths();
            this.reload = (this.reload == true ? false : true);
        }
        return this.cowTextures.stream().collect(Collectors.joining("/","enhanced_cow/",""));

    }

    @OnlyIn(Dist.CLIENT)
    public String[] getVariantTexturePaths() {
        if (this.cowTextures.isEmpty()) {
            this.setTexturePaths();
        }

        return this.cowTextures.stream().toArray(String[]::new);
    }

    @OnlyIn(Dist.CLIENT)
    protected void setTexturePaths() {
        int[] genesForText = getSharedGenes();
        if (genesForText != null) {

            int base = 0;
            int red = 0;
            int black = 0;
            int roan = 0;
            int speckled = 0;
            int whiteface = 0;
            int whitefacehead = 0;
            int belted = 0;
            int coloursided = 0;
            int skin = 0;
            int hooves = 0;
            int horn = 1;
            int coat = 0;
            char[] uuidArry;

            String mooshroomUUIDForTexture = this.dataManager.get(MOOSHROOM_UUID);

            if (mooshroomUUIDForTexture.equals("0")) {
                uuidArry = getCachedUniqueIdString().toCharArray();
            } else {
                uuidArry = mooshroomUUIDForTexture.toCharArray();
            }

            //dominant red
            if (genesForText[6] == 1 || genesForText[7] == 1){
                //make red instead maybe flip dominant red toggle?
                red = 1;
                skin = 1;
            }else {
                if (genesForText[0] == 1 || genesForText[1] == 1) {
                    black = 5;
                } else if (genesForText[0] == 2 || genesForText[1] == 2) {
                    red = 2;

                    //Agouti
                    if (genesForText[4] == 1 || genesForText[5] == 1) {
                        if (genesForText[4] == 2 || genesForText[5] == 2) {
                            //darker wildtype
                            //or other incomplete dominance
                            black = 3;
                        } else {
                            //complete dominance of black enhancer
                            black = 4;
                        }
                    } else if (genesForText[4] == 2 || genesForText[5] == 2) {
                        //wildtype
                        black = 2;
                    } else if (genesForText[4] == 3 || genesForText[5] == 3) {
                        //white bellied fawn (i believe this is like silver)
                        black = 2;
                        red = 0;
                        base = 2;
                        //TODO set up something here to dilute the colour of red to a cream or white
                    } else {
                        //brindle (there might be a recessive black but no one seems to know lol)
                        black = 6;
                    }

                } else {
                    red = 1;
                    skin = 1;
                }
            }

            //standard dilution
            if (genesForText[2] == 2 || genesForText[3] == 2){
                if (genesForText[2] == 2 && genesForText[3] == 2){
                    //full dilute
                    skin = 2;
                }else{
                    //semi dilute
                    if (black != 0 && black <= 3) {
                        black--;
                    }
                }
            } //not dilute

            //roan
            if (genesForText[8] == 2 || genesForText[9] == 2){
                //is roan
                if (genesForText[8] == 2 && genesForText[9] == 2) {
                    //white roan
                    roan = 2;
//                if ( uuidArry[0]-48 == 0){
//                    //makes all cows with roan and uuid of 0 infertile
//                }
                }else{
                    roan = 1;
                }
            }

            ///speckled
            if (genesForText[14] == 1 || genesForText[15] == 1){
                if (genesForText[14] == 1 && genesForText[15] == 1){
                    speckled = 2;
                    //pointed white
                }else{
                    speckled = 1;
                    //speckled
                }
            } //not speckled

            //colour sided
            if (genesForText[20] == 1 || genesForText[21] == 1){
                //coloursided
                coloursided = 1;
            }

            if (genesForText[16] == 1 || genesForText[17] == 1){
                if (genesForText[16] == 2 || genesForText[17] == 2){
                    //white face with border spots(Pinzgauer)
                    whiteface = 2;
                }else{
                    //whiteface
                    whiteface = 1;
                }
            }else if (genesForText[16] == 2 || genesForText[17] == 2){
                //border spots (Pinzgauer) this gene might be incomplete dominant with wildtype but I dont see it
                    whiteface = 3;
            }else if (genesForText[16] == 4 && genesForText[17] == 4){
                //piebald
                    whiteface = 4;

            }

            //TODO figure out how these work when heterozygous
            if (genesForText[18] == 1 || genesForText[19] == 1){
                //belted
                belted = 1;
            }else if (genesForText[18] == 2 || genesForText[19] == 2){
                //blaze
                belted = 17;
            }else if (genesForText[18] == 3 || genesForText[19] == 3){
                if (whiteface != 0 || coloursided != 0) {
                    if (black == 4 || black == 5 || black == 6 || black == 10 || black == 11 || black == 12){
                        //brockling
                        belted = 18;
                    } else {
                        belted = 19;
                    }

                }

            }

            //TODO make randomizers for the textures
            if (whiteface == 4){
                //selects body piebalding texture
                if (Character.isDigit(uuidArry[1])) {
                    whiteface = whiteface + (1 + (uuidArry[1] - 48));
                } else {
                    char d = uuidArry[1];

                    switch (d) {
                        case 'a':
                            whiteface = whiteface + 10;
                            break;
                        case 'b':
                            whiteface = whiteface + 11;
                            break;
                        case 'c':
                            whiteface = whiteface + 12;
                            break;
                        case 'd':
                            whiteface = whiteface + 13;
                            break;
                        case 'e':
                            whiteface = whiteface + 14;
                            break;
                        case 'f':
                            whiteface = whiteface + 15;
                            break;
                        default:
                            whiteface = 1;
                    }
                }
                //selects face piebalding texture
                if (uuidArry[0] != uuidArry[1]) {
                    whitefacehead = 4;
                    if (Character.isDigit(uuidArry[2])) {
                        whitefacehead = whitefacehead + (1 + uuidArry[2] - 48);
                    } else {
                        char d = uuidArry[2];

                        switch (d) {
                            case 'a':
                                whitefacehead = whitefacehead + 10;
                                break;
                            case 'b':
                                whitefacehead = whitefacehead + 11;
                                break;
                            case 'c':
                                whitefacehead = whitefacehead + 12;
                                break;
                            case 'd':
                                whitefacehead = whitefacehead + 13;
                                break;
                            case 'e':
                                whitefacehead = whitefacehead + 14;
                                break;
                            case 'f':
                                whitefacehead = whitefacehead + 15;
                                break;
                            default:
                                whitefacehead = 0;
                        }
                    }
                }
                
            }

            if (belted == 1) {
                if (Character.isDigit(uuidArry[3])) {
                    belted = belted + (1 + (uuidArry[3] - 48));
                } else {
                    char d = uuidArry[3];

                    switch (d) {
                        case 'a':
                            belted = belted + 10;
                            break;
                        case 'b':
                            belted = belted + 11;
                            break;
                        case 'c':
                            belted = belted + 12;
                            break;
                        case 'd':
                            belted = belted + 13;
                            break;
                        case 'e':
                            belted = belted + 14;
                            break;
                        case 'f':
                            belted = belted + 15;
                            break;
                        default:
                            belted = 0;
                    }
                }
            }

            //these alter texture to fit model changes
            if(genesForText[26] == 1 || genesForText[27] == 1) {
                hooves = 1;
            }

//            if (genesForText[12] == 1 || genesForText[13] == 1) {
//                //should be polled unless...
//                //african horn gene
//                if (genesForText[76] == 1 && genesForText[77] == 1) {
//                    //horned
//                } else if (genesForText[76] == 1 || genesForText[77] == 1) {
//                    //sex determined horned
//                    if (Character.isLetter(uuidArry[0]) || uuidArry[0] - 48 >= 8) {
//                        //horned if male
//                    } else {
//                        //polled if female unless
//                        if (genesForText[78] == 1 && genesForText[79] == 1) {
//                            //she is scured
//                        } else {
//                            //polled
//                            horn = 0;
//                        }
//                    }
//                } else {
//                    //polled
//                    if (genesForText[78] == 1 && genesForText[79] == 1) {
//                        //scured
//                    } else if (genesForText[78] == 1 || genesForText[79] == 1) {
//                        //sex determined scured
//                        if (Character.isLetter(uuidArry[0]) || uuidArry[0] - 48 >= 8) {
//                            //scurred
//                        } else {
//                            //polled
//                            horn = 0;
//                        }
//                    } else {
//                        //polled
//                        horn = 0;
//                    }
//                }
//            }

            if (genesForText[48] == 1 || genesForText[49] == 1){
                coat = 1;
            }else{
                if (genesForText[50] == 2 && genesForText[51] == 2) {
                    coat = 2;
                } else if (genesForText[52] == 2 && genesForText[53] == 2) {
                    coat = 2;
                }else if ((genesForText[50] == 2 || genesForText[51] == 2) && (genesForText[52] == 2 || genesForText[53] == 2)){
                    coat = 2;
                }
            }



            //TODO change white spots to add whitening together
            //TODO add shading under correct conditions

            this.cowTextures.add(COW_TEXTURES_BASE[0]);
            this.cowTextures.add(COW_TEXTURES_UDDER[skin]);
            if (red != 0){
                this.cowTextures.add(COW_TEXTURES_RED[red]);
            }
            if (black != 0){
                this.cowTextures.add(COW_TEXTURES_BLACK[black]);
            }
            this.cowTextures.add(COW_TEXTURES_SKIN[skin]);
            if (whiteface != 0){
                this.cowTextures.add(COW_TEXTURES_WHITEFACE[whiteface]);
                if (whitefacehead >= 4) {
                    this.cowTextures.add(COW_TEXTURES_WHITEFACEHEAD[whitefacehead]);
                }
            }
            if (coloursided != 0){
                this.cowTextures.add(COW_TEXTURES_COLOURSIDED[coloursided]);
            }
            if (belted != 0){
                this.cowTextures.add(COW_TEXTURES_BELTED[belted]);
            }
            if (roan != 0){
                this.cowTextures.add(COW_TEXTURES_ROAN[roan]);
            }
            if (speckled != 0){
                this.cowTextures.add(COW_TEXTURES_SPECKLED[speckled]);
            }
            //TODO add hoof colour genetics
            this.cowTextures.add(COW_TEXTURES_HOOVES[hooves]);
            //TODO add eye colour genetics
            this.cowTextures.add(COW_TEXTURES_EYES[0]);
            //TODO add hoof colour genetics
            this.cowTextures.add(COW_TEXTURES_HORNS[horn]);
            this.cowTextures.add(COW_TEXTURES_COAT[coat]);
//              testing textures
//              this.cowTextures.add(COW_TEXTURES_TEST[1]);

        }
    }

    @OnlyIn(Dist.CLIENT)
    public float getHeadRotationPointY(float partialTickTime) {
        if (this.cowTimer <= 0)
        {
            return 0.0F;
        }
        else if (this.cowTimer >= 4 && this.cowTimer <= 36)
        {
            return 1.0F;
        }
        else
        {
            return this.cowTimer < 4 ? ((float)this.cowTimer - partialTickTime) / 4.0F : -((float)(this.cowTimer - 40) - partialTickTime) / 4.0F;
        }
    }
    //EATING???
    @OnlyIn(Dist.CLIENT)
    public float getHeadRotationAngleX(float partialTickTime) {
        if (this.cowTimer > 4 && this.cowTimer <= 36)
        {
            float f = ((float)(this.cowTimer - 4) - partialTickTime) / 32.0F;
            return ((float)Math.PI / 5F) + ((float)Math.PI * 7F / 100F) * MathHelper.sin(f * 28.7F);
        }
        else
        {
            return this.cowTimer > 0 ? ((float)Math.PI / 5F) : this.rotationPitch * 0.017453292F;
        }
    }

    @OnlyIn(Dist.CLIENT)
    public float[] getRgb() {
        if (cowColouration == null) {
            cowColouration = new float[6];
            int[] genesForText = getSharedGenes();

            float blackR = 15.0F;
            float blackG = 7.0F;
            float blackB = 7.0F;

            float redR = 134.0F;
            float redG = 79.0F;
            float redB = 41.0F;

            int tint;

            if (!(genesForText == null || genesForText.length == 0)) {
                if ((genesForText[6] == 1 || genesForText[7] == 1) || (genesForText[0] == 3 && genesForText[1] == 3)){
                    //red
                    tint = 4;
                }else {
                    if (genesForText[0] == 1 || genesForText[1] == 1) {
                        //black
                        tint = 2;
                    } else {
                        //wildtype
                        tint = 3;
                    }
                }

                //standard dilution
                if (genesForText[2] == 2 || genesForText[3] == 2) {
//            if (true) {
                    if (genesForText[2] == 2 && genesForText[3] == 2) {
//                if (false) {

                        blackR = (blackR + (255F * tint)) / (tint+1);
                        blackG = (blackG + (245F * tint)) / (tint+1);
                        blackB = (blackB + (235F * tint)) / (tint+1);

                        if (tint != 2) {
                            redR = (redR + (255F * tint)) / (tint + 1);
                            redG = (redG + (255F * tint)) / (tint + 1);
                            redB = (redB + (255F * tint)) / (tint + 1);
                        }
                    }else{
                        if (tint == 3) {
                            //wildtype
                            redR = 160.5F;
                            redG = 119.0F;
                            redB = 67.0F;

                            if (genesForText[4] == 1 || genesForText[5] == 1) {
                                if (genesForText[4] == 1 && genesForText[5] == 1) {
                                    blackR = 81.0F;
                                    blackG = 71.0F;
                                    blackB = 65.0F;
                                } else {
                                    blackR = 40.0F;
                                    blackG = 35.0F;
                                    blackB = 32.0F;
                                }
                            } else if (genesForText[4] == 4 && genesForText[5] == 4) {
                                blackR = 81.0F;
                                blackG = 71.0F;
                                blackB = 65.0F;
                            }

                        } else if (tint == 4){
                            //red
                            redR = (redR*0.5F) + (187.0F*0.5F);
                            redG = (redG*0.5F) + (180.0F*0.5F);
                            redB = (redB*0.5F) + (166.0F*0.5F);
                        }else {
                            //black
                            blackR = 81.0F;
                            blackG = 71.0F;
                            blackB = 65.0F;
                        }
                    }
                }

                if (genesForText[4] == 3 || genesForText[5] == 3) {
                    redR = (redR + 245F) / 2;
                    redG = (redG + 237F) / 2;
                    redB = (redB + 222F) / 2;
                }

                //chocolate
                if (genesForText[10] == 2 && genesForText[11] == 2){
                    blackR = blackR + 25F;
                    blackG = blackG + 15F;
                    blackB = blackB + 9F;

                    redR = redR + 25F;
                    redG = redG + 15F;
                    redB = redB + 9F;
                }

                if (this.isChild()) {
                    if (getCowStatus().equals(EntityState.CHILD_STAGE_ONE.toString())) {
                        blackR = redR;
                        blackG = redG;
                        blackB = redB;
                    }else if (getCowStatus().equals(EntityState.CHILD_STAGE_TWO.toString())) {
                        blackR = (blackR + redR)/2F;
                        blackG = (blackG + redG)/2F;
                        blackB = (blackB + redB)/2F;
                    } else {
                        blackR = blackR*0.75F + redR*0.25F;
                        blackG = blackG*0.75F + redG*0.25F;
                        blackB = blackB*0.75F + redB*0.25F;
                    }
                }
            }

            //TODO TEMP AF
            //black
            cowColouration[0] = blackR;
            cowColouration[1] = blackG;
            cowColouration[2] = blackB;

            //red
            cowColouration[3] = redR;
            cowColouration[4] = redG;
            cowColouration[5] = redB;

            for (int i = 0; i <= 5; i++) {
                if (cowColouration[i] > 255.0F) {
                    cowColouration[i] = 255.0F;
                }
                cowColouration[i] = cowColouration[i] / 255.0F;
            }

        }
        return cowColouration;
    }

    private void setMaxBagSize(){
        float maxBagSize = 0.0F;

        if (!this.isChild() && getCowStatus().equals(EntityState.MOTHER.toString())){
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
    public boolean processInteract(PlayerEntity entityPlayer, Hand hand) {
        ItemStack itemStack = entityPlayer.getHeldItem(hand);
        Item item = itemStack.getItem();

        if (item == Items.NAME_TAG) {
            itemStack.interactWithEntity(entityPlayer, this, hand);
            return true;
        } else if (this.getSaddled() && !this.isBeingRidden()) {
            if (!this.world.isRemote) {
                entityPlayer.startRiding(this);
            }

            return true;
        } else if (item == Items.SADDLE){
            return this.saddleCow(itemStack, entityPlayer, this);
        }

//        if (item == Items.BLACK_WOOL) {
//            setHornAlteration(0, "1.0");
//        } else if (item == Items.GRAY_WOOL) {
//            setHornAlteration(1, "1.0");
//        } else if (item == Items.WHITE_WOOL) {
//            setHornAlteration(2, "1.0");
//        } else if (item == Items.PINK_WOOL) {
//            setHornAlteration(3, "1.0");
//        } else if (item == Items.RED_WOOL) {
//            setHornAlteration(4, "1.0");
//        } else if (item == Items.ORANGE_WOOL) {
//            setHornAlteration(5, "1.0");
//        } else if (item == Items.YELLOW_WOOL) {
//            setHornAlteration(6, "1.0");
//        } else if (item == Items.LIME_WOOL) {
//            setHornAlteration(7, "1.0");
//        } else if (item == Items.CYAN_WOOL) {
//            setHornAlteration(8, "0.01");
//        } else if (item == Items.BLUE_WOOL) {
//            setHornAlteration(9, "0.01");
//        } else if (item == Items.BLACK_CONCRETE) {
//            setHornAlteration(10, "0.01");
//        } else if (item == Items.GRAY_CONCRETE) {
//            setHornAlteration(11, "0.01");
//        } else if (item == Items.WHITE_CONCRETE) {
//            setHornAlteration(12, "0.01");
//        } else if (item == Items.PINK_CONCRETE) {
//            setHornAlteration(13, "0.01");
//        } else if (item == Items.RED_CONCRETE) {
//            setHornAlteration(14, "0.01");
//        } else if (item == Items.ORANGE_CONCRETE) {
//            setHornAlteration(15, "0.01");
//        } else if (item == Items.YELLOW_CONCRETE) {
//            setHornAlteration(16, "0.01");
//        } else if (item == Items.LIME_CONCRETE) {
//            setHornAlteration(17, "0.01");
//        } else if (item == Items.CYAN_CONCRETE) {
//            setHornAlteration(18, "0.01");
//        } else if (item == Items.BLUE_CONCRETE) {
//            setHornAlteration(19, "0.01");
//        } else if (item == Items.BLACK_TERRACOTTA) {
//            setHornAlteration(20, "0.01");
//        } else if (item == Items.WHITE_TERRACOTTA) {
//            setHornAlteration(21, "0.01");
//        } else if (item == Items.GRAY_TERRACOTTA) {
//            setHornAlteration(22, "0.01");
//        } else if (item == Items.PINK_TERRACOTTA) {
//            setHornAlteration(23, "0.01");
//        } else if (item == Items.RED_TERRACOTTA) {
//            setHornAlteration(24, "0.01");
//        } else if (item == Items.ORANGE_TERRACOTTA) {
//            setHornAlteration(25, "0.01");
//        }else if (item == Items.YELLOW_TERRACOTTA) {
//            setHornAlteration(26, "0.01");
//        }else if (item == Items.LIME_TERRACOTTA) {
//            setHornAlteration(27, "0.01");
//        }else if (item == Items.CYAN_TERRACOTTA) {
//            setHornAlteration(28, "0.01");
//        }else if (item == Items.BLUE_TERRACOTTA) {
//            setHornAlteration(29, "0.01");
//        } else if (item == Items.BLACK_STAINED_GLASS) {
//            setHornAlteration(0, "reset");
//            setHornAlteration(10, "reset");
//            setHornAlteration(20, "reset");
//        } else if (item == Items.GRAY_STAINED_GLASS) {
//            setHornAlteration(1, "reset");
//            setHornAlteration(11, "reset");
//            setHornAlteration(21, "reset");
//        } else if (item == Items.WHITE_STAINED_GLASS) {
//            setHornAlteration(2, "reset");
//            setHornAlteration(12, "reset");
//            setHornAlteration(22, "reset");
//        } else if (item == Items.PINK_STAINED_GLASS) {
//            setHornAlteration(3, "reset");
//            setHornAlteration(13, "reset");
//            setHornAlteration(23, "reset");
//        } else if (item == Items.RED_STAINED_GLASS) {
//            setHornAlteration(4, "reset");
//            setHornAlteration(14, "reset");
//            setHornAlteration(24, "reset");
//        } else if (item == Items.ORANGE_STAINED_GLASS) {
//            setHornAlteration(5, "reset");
//            setHornAlteration(15, "reset");
//            setHornAlteration(25, "reset");
//        }else if (item == Items.YELLOW_STAINED_GLASS) {
//            setHornAlteration(6, "reset");
//            setHornAlteration(16, "reset");
//            setHornAlteration(26, "reset");
//        }else if (item == Items.LIME_STAINED_GLASS) {
//            setHornAlteration(7, "reset");
//            setHornAlteration(17, "reset");
//            setHornAlteration(27, "reset");
//        }else if (item == Items.CYAN_STAINED_GLASS) {
//            setHornAlteration(8, "reset");
//            setHornAlteration(18, "reset");
//            setHornAlteration(28, "reset");
//        }else if (item == Items.BLUE_STAINED_GLASS) {
//            setHornAlteration(9, "reset");
//            setHornAlteration(19, "reset");
//            setHornAlteration(29, "reset");
//        }else if (item == Items.BONE_MEAL) {
//            setHornAlteration(30, "-1");
//        }else if (item == Items.STICK) {
//            setHornAlteration(30, "1");
//        }

        if ((item == Items.BUCKET || item == ModItems.OneSixth_Milk_Bucket || item == ModItems.OneThird_Milk_Bucket || item == ModItems.Half_Milk_Bucket || item == ModItems.TwoThirds_Milk_Bucket || item == ModItems.FiveSixths_Milk_Bucket || item == ModItems.Half_Milk_Bottle || item == Items.GLASS_BOTTLE) && !this.isChild() && getCowStatus().equals(EntityState.MOTHER.toString())) {
            int maxRefill = 0;
            int bucketSize = 6;
            int currentMilk = getMilkAmount();
            int refillAmount = 0;
            boolean isBottle = false;
            if (item == Items.BUCKET) {
                maxRefill = 6;
            } else if (item == ModItems.OneSixth_Milk_Bucket) {
                maxRefill = 5;
            } else if (item == ModItems.OneThird_Milk_Bucket) {
                maxRefill = 4;
            } else if (item == ModItems.Half_Milk_Bucket) {
                maxRefill = 3;
            } else if (item == ModItems.TwoThirds_Milk_Bucket) {
                maxRefill = 2;
            } else if (item == ModItems.FiveSixths_Milk_Bucket) {
                maxRefill = 1;
            } else if (item == ModItems.Half_Milk_Bottle) {
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

            if (!this.world.isRemote) {
                int resultingMilkAmount = currentMilk - refillAmount;
                this.setMilkAmount(resultingMilkAmount);

                float milkBagSize = resultingMilkAmount / (30*(cowSize/1.5F)*(maxBagSize/1.5F));

                this.setBagSize((1.1F*milkBagSize*(maxBagSize-1.0F))+1.0F);
            }

            int resultAmount = bucketSize - maxRefill + refillAmount;

            ItemStack resultItem = new ItemStack(Items.BUCKET);

            switch (resultAmount) {
                case 0:
                    entityPlayer.playSound(SoundEvents.ENTITY_COW_HURT, 1.0F, 1.0F);
                    return true;
                case 1:
                    if (isBottle) {
                        resultItem = new ItemStack(ModItems.Half_Milk_Bottle);
                    } else {
                        resultItem = new ItemStack(ModItems.OneSixth_Milk_Bucket);
                    }
                    break;
                case 2:
                    if (isBottle) {
                        resultItem = new ItemStack(ModItems.Milk_Bottle);
                    } else {
                        resultItem = new ItemStack(ModItems.OneThird_Milk_Bucket);
                    }
                    break;
                case 3:
                    resultItem = new ItemStack(ModItems.Half_Milk_Bucket);
                    break;
                case 4:
                    resultItem = new ItemStack(ModItems.TwoThirds_Milk_Bucket);
                    break;
                case 5:
                    resultItem = new ItemStack(ModItems.FiveSixths_Milk_Bucket);
                    break;
                case 6:
                    resultItem = new ItemStack(Items.MILK_BUCKET);
                    break;
            }

            entityPlayer.playSound(SoundEvents.ENTITY_COW_MILK, 1.0F, 1.0F);
            itemStack.shrink(1);
            if (itemStack.isEmpty()) {
                entityPlayer.setHeldItem(hand, resultItem);
            } else if (!entityPlayer.inventory.addItemStackToInventory(resultItem)) {
                entityPlayer.dropItem(resultItem, false);
            }

        } else if (this.isChild() && MILK_ITEMS.test(itemStack) && hunger >= 6000) {

//            if (!entityPlayer.abilities.isCreativeMode) {
                if (item == ModItems.Half_Milk_Bottle) {
                    decreaseHunger(6000);
                    if (itemStack.isEmpty()) {
                        entityPlayer.setHeldItem(hand, new ItemStack(Items.GLASS_BOTTLE));
                    } else if (!entityPlayer.inventory.addItemStackToInventory(new ItemStack(Items.GLASS_BOTTLE))) {
                        entityPlayer.dropItem(new ItemStack(Items.GLASS_BOTTLE), false);
                    }
                } else if (item == ModItems.Milk_Bottle) {
                    if (hunger >= 12000) {
                        decreaseHunger(12000);
                        if (itemStack.isEmpty()) {
                            entityPlayer.setHeldItem(hand, new ItemStack(Items.GLASS_BOTTLE));
                        } else if (!entityPlayer.inventory.addItemStackToInventory(new ItemStack(Items.GLASS_BOTTLE))) {
                            entityPlayer.dropItem(new ItemStack(Items.GLASS_BOTTLE), false);
                        }
                    } else {
                        decreaseHunger(6000);
                        if (itemStack.isEmpty()) {
                            entityPlayer.setHeldItem(hand, new ItemStack(ModItems.Half_Milk_Bottle));
                        } else if (!entityPlayer.inventory.addItemStackToInventory(new ItemStack(ModItems.Half_Milk_Bottle))) {
                            entityPlayer.dropItem(new ItemStack(ModItems.Half_Milk_Bottle), false);
                        }
                    }
                }
//            }
        }

        if (!this.world.isRemote && !hand.equals(Hand.OFF_HAND)) {
            if (item instanceof AirItem) {
//                float[] hornAlterations = getHornAlteration();
//                String hornAltString = hornAlterations[0] + ",";
//                for (int a = 1; a < 29; a++) {
//                    hornAltString = hornAltString + hornAlterations[a] + ",";
//                }
//                hornAltString = hornAltString + hornAlterations[29];
//                ITextComponent message = new TranslationTextComponent(hornAltString);
//                Minecraft.getInstance().keyboardListener.setClipboardString(hornAltString);
                ITextComponent message = getHungerText();
                entityPlayer.sendMessage(message);
                if (pregnant) {
                    message = getPregnantText();
                    entityPlayer.sendMessage(message);
                }
            } else if (item instanceof DebugGenesBook) {
                Minecraft.getInstance().keyboardListener.setClipboardString(this.dataManager.get(SHARED_GENES));
            } else if (!getCowStatus().equals(EntityState.CHILD_STAGE_ONE.toString()) && TEMPTATION_ITEMS.test(itemStack) && hunger >= 6000) {
                if (this.foodWeightMap.containsKey(item)) {
                    decreaseHunger(this.foodWeightMap.get(item));
                } else {
                    decreaseHunger(6000);
                }
                if (!entityPlayer.abilities.isCreativeMode) {
                    itemStack.shrink(1);
                }
                return true;
            }
        }
        /* else if (item == Items.GLASS_BOTTLE && !entityPlayer.abilities.isCreativeMode && !this.isChild() && getCowStatus().equals(EntityState.MOTHER.toString())) {
                if (milk == 0) {
                    entityPlayer.playSound(SoundEvents.ENTITY_COW_HURT, 1.0F, 1.0F);
                } else {
                    entityPlayer.playSound(SoundEvents.ENTITY_COW_MILK, 1.0F, 1.0F);
                    itemStack.shrink(1);
                    milk = milk - 1;
                    if (itemStack.isEmpty()) {
                        entityPlayer.setHeldItem(hand, new ItemStack(ModItems.Half_Milk_Bottle));
                    } else if (!entityPlayer.inventory.addItemStackToInventory(new ItemStack(ModItems.Half_Milk_Bottle))) {
                        entityPlayer.dropItem(new ItemStack(ModItems.Half_Milk_Bottle), false);
                    }
                }
            }*/
//        }

        return super.processInteract(entityPlayer, hand);
    }


    public boolean saddleCow(ItemStack stack, PlayerEntity playerIn, LivingEntity target) {
        EnhancedCow entity = (EnhancedCow)target;
        if (entity.isAlive() && !entity.getSaddled() && !entity.isChild()) {
            entity.setSaddled(true);
            entity.world.playSound(playerIn, entity.getPosX(), entity.getPosY(), entity.getPosZ(), SoundEvents.ENTITY_PIG_SADDLE, SoundCategory.NEUTRAL, 0.5F, 1.0F);
            stack.shrink(1);
            return true;
        }

        return false;
    }

    protected void dropInventory() {
        super.dropInventory();
        if (this.getSaddled()) {
            this.entityDropItem(Items.SADDLE);
        }
    }

    public boolean getSaddled() {
        return this.dataManager.get(SADDLED);
    }

    public void setSaddled(boolean saddled) {
        if (saddled) {
            this.dataManager.set(SADDLED, true);
        } else {
            this.dataManager.set(SADDLED, false);
        }
    }

    public void travel(Vec3d p_213352_1_) {
        if (this.isAlive()) {
            Entity entity = this.getPassengers().isEmpty() ? null : this.getPassengers().get(0);
            if (this.isBeingRidden() && this.canBeSteered()) {
                this.rotationYaw = entity.rotationYaw;
                this.prevRotationYaw = this.rotationYaw;
                this.rotationPitch = entity.rotationPitch * 0.5F;
                this.setRotation(this.rotationYaw, this.rotationPitch);
                this.renderYawOffset = this.rotationYaw;
                this.rotationYawHead = this.rotationYaw;
                this.stepHeight = 1.0F;
                this.jumpMovementFactor = this.getAIMoveSpeed() * 0.1F;
                if (this.boosting && this.boostTime++ > this.totalBoostTime) {
                    this.boosting = false;
                }

                if (this.canPassengerSteer()) {
                    float f = (float)this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getValue() * 0.225F;
                    if (this.boosting) {
                        f += f * 1.15F * MathHelper.sin((float)this.boostTime / (float)this.totalBoostTime * (float)Math.PI);
                    }

                    this.setAIMoveSpeed(f);
                    super.travel(new Vec3d(0.0D, 0.0D, 1.0D));
                    this.newPosRotationIncrements = 0;
                } else {
                    this.setMotion(Vec3d.ZERO);
                }

                this.prevLimbSwingAmount = this.limbSwingAmount;
                double d1 = this.getPosX() - this.prevPosX;
                double d0 = this.getPosZ() - this.prevPosZ;
                float f1 = MathHelper.sqrt(d1 * d1 + d0 * d0) * 4.0F;
                if (f1 > 1.0F) {
                    f1 = 1.0F;
                }

                this.limbSwingAmount += (f1 - this.limbSwingAmount) * 0.4F;
                this.limbSwing += this.limbSwingAmount;
            } else {
                this.stepHeight = 0.5F;
                this.jumpMovementFactor = 0.02F;
                super.travel(p_213352_1_);
            }
        }
    }

    public boolean boost() {
        if (this.boosting) {
            return false;
        } else {
            this.boosting = true;
            this.boostTime = 0;
            this.totalBoostTime = this.getRNG().nextInt(841) + 140;
            this.getDataManager().set(BOOST_TIME, this.totalBoostTime);
            return true;
        }
    }

    private ITextComponent getHungerText() {
        String hungerText = "";
        if (this.hunger < 1000) {
            hungerText = "eanimod.hunger.not_hungry";
        } else if (this.hunger < 4000) {
            hungerText = "eanimod.hunger.hungry";
        } else if (this.hunger < 9000) {
            hungerText = "eanimod.hunger.very_hunger";
        } else if (this.hunger < 16000) {
            hungerText = "eanimod.hunger.starving";
        } else if (this.hunger > 24000) {
            hungerText = "eanimod.hunger.dying";
        }
        return new TranslationTextComponent(hungerText);
    }

    private ITextComponent getPregnantText() {
        String pregnancyText;
        int days = EanimodCommonConfig.COMMON.gestationDaysCow.get();
        if (gestationTimer > (days/5 * 4)) {
            pregnancyText = "eanimod.pregnancy.near_birth";
        } else if (gestationTimer > days/2 ) {
            pregnancyText = "eanimod.pregnancy.obviously_pregnant";
        } else if (gestationTimer > days/3) {
            pregnancyText = "eanimod.pregnancy.pregnant";
        } else if (gestationTimer > days/5) {
            pregnancyText = "eanimod.pregnancy.only_slightly_showing";
        } else {
            pregnancyText = "eanimod.pregnancy.not_showing";
        }
        return new TranslationTextComponent(pregnancyText);
    }

    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);

        //store this cows's genes
        ListNBT geneList = new ListNBT();
        for (int i = 0; i < genes.length; i++) {
            CompoundNBT nbttagcompound = new CompoundNBT();
            nbttagcompound.putInt("Gene", genes[i]);
            geneList.add(nbttagcompound);
        }
        compound.put("Genes", geneList);

        //store this cows's mate's genes
        ListNBT mateGeneList = new ListNBT();
        for (int i = 0; i < mateGenes.length; i++) {
            CompoundNBT nbttagcompound = new CompoundNBT();
            nbttagcompound.putInt("Gene", mateGenes[i]);
            mateGeneList.add(nbttagcompound);
        }
        compound.put("FatherGenes", mateGeneList);

        compound.putBoolean("Pregnant", this.pregnant);
        compound.putInt("Gestation", this.gestationTimer);
        compound.putInt("Lactation", this.lactationTimer);

        compound.putString("Status", getCowStatus());
        compound.putInt("Hunger", hunger);

        compound.putString("MooshroomID", getMooshroomUUID());

        compound.putString("MotherUUID", this.motherUUID);

        compound.putInt("milk", getMilkAmount());

        compound.putString("BirthTime", this.getBirthTime());

        compound.putBoolean("Saddle", this.getSaddled());

    }

    /**
     * (abstract) Protected helper method to read subclass entity assets from NBT.
     */
    public void readAdditional(CompoundNBT compound) {
        
        super.readAdditional(compound);

        ListNBT geneList = compound.getList("Genes", 10);
        for (int i = 0; i < geneList.size(); ++i) {
            CompoundNBT nbttagcompound = geneList.getCompound(i);
            int gene = nbttagcompound.getInt("Gene");
            genes[i] = gene;
        }

        ListNBT mateGeneList = compound.getList("FatherGenes", 10);
        for (int i = 0; i < mateGeneList.size(); ++i) {
            CompoundNBT nbttagcompound = mateGeneList.getCompound(i);
            int gene = nbttagcompound.getInt("Gene");
            mateGenes[i] = gene;
        }

        this.pregnant = compound.getBoolean("Pregnant");
        this.gestationTimer = compound.getInt("Gestation");
        this.lactationTimer = compound.getInt("Lactation");

        setCowStatus(compound.getString("Status"));
        hunger = compound.getInt("Hunger");

        setMooshroomUUID(compound.getString("MooshroomID"));

        this.motherUUID = compound.getString("MotherUUID");

        setMilkAmount(compound.getInt("milk"));

        this.setBirthTime(compound.getString("BirthTime"));

        if (genes[82] == 0) {
            generateHornGenes(genes);
        }

        this.setSaddled(compound.getBoolean("Saddle"));

        setSharedGenes(genes);
        setCowSize();
        setMaxBagSize();
        configureAI();
    }

    public void mixMitosisGenes() {
        punnetSquare(mitosisGenes, genes);
    }

    public void mixMateMitosisGenes() {
        punnetSquare(mateMitosisGenes, mateGenes);
    }

    public void punnetSquare(int[] mitosis, int[] parentGenes) {
        Random rand = new Random();
        for (int i = 0; i < genes.length; i = (i + 2)) {
            boolean mateOddOrEven = rand.nextBoolean();
            if (mateOddOrEven) {
                mitosis[i] = parentGenes[i + 1];
                mitosis[i + 1] = parentGenes[i];
            } else {
                mitosis[i] = parentGenes[i];
                mitosis[i + 1] = parentGenes[i + 1];
            }
        }
    }


    public int[] getCalfGenes(int[] mitosis, int[] mateMitosis) {
        Random rand = new Random();
        int[] calfGenes = new int[GENES_LENGTH];

        for (int i = 0; i < genes.length; i = (i + 2)) {
            boolean thisOrMate = rand.nextBoolean();
            if (thisOrMate) {
                calfGenes[i] = mitosis[i];
                calfGenes[i+1] = mateMitosis[i+1];
            } else {
                calfGenes[i] = mateMitosis[i];
                calfGenes[i+1] = mitosis[i+1];
            }
        }

        return calfGenes;
    }


    @Nullable
    @Override
    public ILivingEntityData onInitialSpawn(IWorld inWorld, DifficultyInstance difficulty, SpawnReason spawnReason, @Nullable ILivingEntityData livingdata, @Nullable CompoundNBT itemNbt) {
//        livingdata = super.onInitialSpawn(inWorld, difficulty, spawnReason, livingdata, itemNbt);
        int[] spawnGenes;

        if (livingdata instanceof GroupData) {
            int[] spawnGenes1 = ((GroupData) livingdata).groupGenes;
            int[] mitosis = new int[GENES_LENGTH];
            punnetSquare(mitosis, spawnGenes1);

            int[] spawnGenes2 = ((GroupData) livingdata).groupGenes;
            int[] mateMitosis = new int[GENES_LENGTH];
            punnetSquare(mateMitosis, spawnGenes2);
            spawnGenes = getCalfGenes(mitosis, mateMitosis);
        } else {
            spawnGenes = createInitialGenes(inWorld);
            livingdata = new GroupData(spawnGenes);
        }

        this.genes = spawnGenes;
        setSharedGenes(genes);
        setCowSize();
        setMaxBagSize();

        int birthMod = ThreadLocalRandom.current().nextInt(64800, 108000);
        setBirthTime(String.valueOf(inWorld.getWorld().getGameTime() - birthMod));
        if (birthMod < 84000) {
            this.setGrowingAge(birthMod - 84000);
        }

        configureAI();
//        for (int i = 0; i <= 30; i++) {
//            setHornAlteration(i , String.valueOf(0));
//        }
        return livingdata;
    }

    private int[] createInitialGenes(IWorld inWorld) {
        int[] initialGenes = new int[GENES_LENGTH];
        //TODO create biome WTC variable [hot and dry biomes, hot and wet biomes, cold biomes] WTC is all others
        int wildType = 2;
        Biome biome = inWorld.getBiome(new BlockPos(this));

        if (biome.getDefaultTemperature() >= 0.9F) // hot and wet (jungle)
        {
            wildType = 1;
        }else if (biome.getCategory().equals(Biome.Category.PLAINS)) {
            wildType = 3;
        } else if (biome.getCategory().equals(Biome.Category.MUSHROOM)) {
            wildType = 4;
        }


        //Extension [ Dom.Black, Wildtype+, red ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[0] = (ThreadLocalRandom.current().nextInt(3) + 1);

        } else {
            initialGenes[0] = (2);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[1] = (ThreadLocalRandom.current().nextInt(3) + 1);

        } else {
            initialGenes[1] = (2);
        }

        //Dilution [ wildtype, dilute]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[2] = (ThreadLocalRandom.current().nextInt(2) + 1);
            initialGenes[3] = (1);

        } else {
            initialGenes[2] = (1);
            initialGenes[3] = (1);
        }

        //Agouti [ Black enhancer, Wildtype+, white-bellied fawn, brindle ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[4] = (ThreadLocalRandom.current().nextInt(4) + 1);

        } else {
            if (wildType == 1){
                initialGenes[4] = (2);
            }else {
                initialGenes[4] = (2);
            }
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[5] = (ThreadLocalRandom.current().nextInt(4) + 1);

        } else {
            if (wildType == 1){
                initialGenes[5] = (3);
            }else {
                initialGenes[5] = (2);
            }
        }

        //Dominant Red *rare gene [ Dom.red, wildtype ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[6] = (ThreadLocalRandom.current().nextInt(2) + 1);
            initialGenes[7] = (2);

        } else {
            initialGenes[6] = (2);
            initialGenes[7] = (2);
        }

        //Roan [wildtype, white roan] random sterility in 'females' with 2 mutations
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[8] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[8] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[9] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[9] = (1);
        }

        //Chocoalte [wildtype, chocolate] as in dexter cattle
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[10] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[10] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[11] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[11] = (1);
        }

        //Horns [polled, horned]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[12] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[12] = (2);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[13] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[13] = (2);
        }


        //Speckled Spots [speckled, wildtype+]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[14] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[14] = (2);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[15] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[15] = (2);
        }

        //White face and other spots [whiteface1, border spotted, wildtype+, piebald]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[16] = (ThreadLocalRandom.current().nextInt(4) + 1);

        } else {
            if (wildType == 3) {
                initialGenes[16] = (4);
            } else {
                initialGenes[16] = (3);
            }
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[17] = (ThreadLocalRandom.current().nextInt(4) + 1);

        } else {
            if (wildType == 3) {
                initialGenes[17] = (4);
            } else {
                initialGenes[17] = (3);
            }
        }

        //belted [belted, blaze, brockling, wildtype]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[18] = (ThreadLocalRandom.current().nextInt(4) + 1);

        } else {
            initialGenes[18] = (4);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[19] = (ThreadLocalRandom.current().nextInt(4) + 1);

        } else {
            initialGenes[19] = (4);
        }

        //colour sided [colour sided, wildtype]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[20] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[20] = (2);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[21] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[21] = (2);
        }

        //whiteface gene expression controller [+spots, normal, -spots, +backstripe]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[22] = (ThreadLocalRandom.current().nextInt(4) + 1);

        } else {
            initialGenes[22] = (2);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[23] = (ThreadLocalRandom.current().nextInt(4) + 1);

        } else {
            initialGenes[23] = (2);
        }

        //shading (white nose ring) [no nose ring, wildtype]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[24] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[24] = (2);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[25] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[25] = (2);
        }

        //bulldog dwarfism [dwarf, wildtype]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[26] = (ThreadLocalRandom.current().nextInt(2) + 1);
            initialGenes[27] = (2);

        } else {
            initialGenes[26] = (2);
            initialGenes[27] = (2);
        }

        //proportionate dwarfism [wildtype, dwarf]
        if (wildType == 1){
            if (ThreadLocalRandom.current().nextInt(100) > WTC) {
                initialGenes[28] = (ThreadLocalRandom.current().nextInt(2) + 1);

            } else {
                initialGenes[28] = (1);
            }
            if (ThreadLocalRandom.current().nextInt(100) > WTC) {
                initialGenes[29] = (ThreadLocalRandom.current().nextInt(2) + 1);

            } else {
                initialGenes[29] = (1);
            }
        }else {
            initialGenes[28] = (1);
            initialGenes[29] = (1);
        }

        //size genes reducer [wildtype, smaller smaller smallest...] adds milk fat [none to most]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[30] = (ThreadLocalRandom.current().nextInt(15) + 1);

        } else {
            initialGenes[30] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[31] = (ThreadLocalRandom.current().nextInt(15) + 1);

        } else {
            initialGenes[31] = (1);
        }

        //size genes adder [wildtype, bigger bigger biggest...]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[32] = (ThreadLocalRandom.current().nextInt(15) + 1);

        } else {
            initialGenes[32] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[33] = (ThreadLocalRandom.current().nextInt(15) + 1);

        } else {
            initialGenes[33] = (1);
        }

        //size genes varient1 [wildtype, smaller, smallest] suppresses milk fat [none to most]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[34] = (ThreadLocalRandom.current().nextInt(3) + 1);

        } else {
            initialGenes[34] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[35] = (ThreadLocalRandom.current().nextInt(3) + 1);

        } else {
            initialGenes[35] = (1);
        }

        //size genes varient2 [smallest, smaller, wildtype] suppresses milk fat [none to most]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[36] = (ThreadLocalRandom.current().nextInt(3) + 1);

        } else {
            initialGenes[36] = (3);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[37] = (ThreadLocalRandom.current().nextInt(3) + 1);

        } else {
            initialGenes[37] = (3);
        }

        //hump size [none, smallest, smaller, medium, bigger, biggest]  reduces milk production [ biggest sizes only]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            if (wildType == 1){
                initialGenes[38] = (ThreadLocalRandom.current().nextInt(3) + 4);
            }else{
                initialGenes[38] = (ThreadLocalRandom.current().nextInt(3) + 1);
            }
        } else {
            if (wildType == 1){
                initialGenes[38] = (4);
            }else {
                initialGenes[38] = (1);
            }
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            if (wildType == 1){
                initialGenes[39] = (ThreadLocalRandom.current().nextInt(4) + 3);
            }else{
                initialGenes[39] = (ThreadLocalRandom.current().nextInt(2) + 1);
            }
        } else {
            if (wildType == 1){
                initialGenes[39] = (5);
            }else{
                initialGenes[39] = (1);
            }
        }

        //hump height [tallest, tall, medium, short] reduces milk production [tall sizes only]
        if (wildType == 1) {
            if (ThreadLocalRandom.current().nextInt(100) > WTC) {
                initialGenes[40] = (ThreadLocalRandom.current().nextInt(4) + 1);

            } else {
                initialGenes[40] = (3);
            }
            if (ThreadLocalRandom.current().nextInt(100) > WTC) {
                initialGenes[41] = (ThreadLocalRandom.current().nextInt(4) + 1);

            } else {
                initialGenes[41] = (3);
            }
        }else{
            if (ThreadLocalRandom.current().nextInt(100) > WTC) {
                initialGenes[40] = (ThreadLocalRandom.current().nextInt(2) + 3);

            } else {
                initialGenes[40] = (4);
            }
            if (ThreadLocalRandom.current().nextInt(100) > WTC) {
                initialGenes[41] = (ThreadLocalRandom.current().nextInt(2) + 3);

            } else {
                initialGenes[41] = (4);
            }
        }

        //ear size [smallest, smaller, normal, long, longest]
        if (wildType == 1){
            if (ThreadLocalRandom.current().nextInt(100) > WTC) {
                initialGenes[42] = (ThreadLocalRandom.current().nextInt(3) + 3);

            } else {
                initialGenes[42] = (5);
            }
            if (ThreadLocalRandom.current().nextInt(100) > WTC) {
                initialGenes[43] = (ThreadLocalRandom.current().nextInt(3) + 3);

            } else {
                initialGenes[43] = (5);
            }
        }else{
            if (ThreadLocalRandom.current().nextInt(100) > WTC) {
                initialGenes[42] = (ThreadLocalRandom.current().nextInt(3) + 1);

            } else {
                initialGenes[42] = (2);
            }
            if (ThreadLocalRandom.current().nextInt(100) > WTC) {
                initialGenes[43] = (ThreadLocalRandom.current().nextInt(3) + 1);

            } else {
                initialGenes[43] = (2);
            }
        }

        //ear size suppressor [smaller ears, longer ears]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[44] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            if (wildType == 1){
                initialGenes[44] = (2);
            }else{
                initialGenes[44] = (1);
            }
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[45] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            if (wildType == 1){
                initialGenes[45] = (2);
            }else{
                initialGenes[45] = (1);
            }
        }

        //ear floppiness [stiffer, normal, floppier, floppiest]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[46] = (ThreadLocalRandom.current().nextInt(4) + 1);

        } else {
            if (wildType == 1){
                initialGenes[46] = (3);
            }else{
                initialGenes[46] = (2);
            }
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[47] = (ThreadLocalRandom.current().nextInt(4) + 1);

        } else {
            if (wildType == 1){
                initialGenes[47] = (3);
            }else{
                initialGenes[47] = (2);
            }
        }

        //coat smoothness [smooth, normal] (this gives a slick coat like brahma and suppresses furry coat genes)
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[48] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            if (wildType == 1){
                initialGenes[48] = (1);
            }else{
                initialGenes[48] = (2);
            }

        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[49] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            if (wildType == 1){
                initialGenes[49] = (1);
            }else{
                initialGenes[49] = (2);
            }
        }

        //Furry coat 1 [normal, furry] reduces milk production [least to most]
        if (wildType == 1){
            initialGenes[50] = (1);
            initialGenes[51] = (1);
            initialGenes[52] = (1);
            initialGenes[53] = (1);
        }else {
            if (ThreadLocalRandom.current().nextInt(100) > WTC) {
                initialGenes[50] = (ThreadLocalRandom.current().nextInt(2) + 1);

            } else {
                initialGenes[50] = (1);
            }
            if (ThreadLocalRandom.current().nextInt(100) > WTC) {
                initialGenes[51] = (ThreadLocalRandom.current().nextInt(2) + 1);

            } else {
                initialGenes[51] = (1);
            }

            //furry coat 2 [normal, furry] reduces milk production [least to most]
            if (ThreadLocalRandom.current().nextInt(100) > WTC) {
                initialGenes[52] = (ThreadLocalRandom.current().nextInt(2) + 1);

            } else {
                initialGenes[52] = (1);
            }
            if (ThreadLocalRandom.current().nextInt(100) > WTC) {
                initialGenes[53] = (ThreadLocalRandom.current().nextInt(2) + 1);

            } else {
                initialGenes[53] = (1);
            }
        }

        //body type [smallest to largest] reduces milk production and fat harshly [least to most] increases meat drops proportionally
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[54] = (ThreadLocalRandom.current().nextInt(3) + 1);

        } else {
            initialGenes[54] = (2);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[55] = (ThreadLocalRandom.current().nextInt(3) + 1);

        } else {
            initialGenes[55] = (2);
        }

        //A1 vs A2 milk cause why not [A1, A2]
            initialGenes[56] = (ThreadLocalRandom.current().nextInt(2) + 1);
            initialGenes[57] = (ThreadLocalRandom.current().nextInt(2) + 1);

        //Golden Milk 1[white, white, gold]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[58] = (ThreadLocalRandom.current().nextInt(3) + 1);

        } else {
            initialGenes[58] = (2);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[59] = (ThreadLocalRandom.current().nextInt(3) + 1);

        } else {
            initialGenes[59] = (2);
        }

        //Golden Milk 2[white, cream, gold]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[60] = (ThreadLocalRandom.current().nextInt(3) + 1);

        } else {
            initialGenes[60] = (2);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[61] = (ThreadLocalRandom.current().nextInt(3) + 1);

        } else {
            initialGenes[61] = (2);
        }

        //Milk Production Base 1[smallest to largest]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[62] = (ThreadLocalRandom.current().nextInt(10) + 1);

        } else {
            initialGenes[62] = (2);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[63] = (ThreadLocalRandom.current().nextInt(10) + 1);

        } else {
            initialGenes[63] = (2);
        }

        //Milk Production Base 2[smallest to largest] reduces milk fat [least to most]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[64] = (ThreadLocalRandom.current().nextInt(10) + 1);

        } else {
            initialGenes[64] = (2);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[65] = (ThreadLocalRandom.current().nextInt(10) + 1);

        } else {
            initialGenes[65] = (2);
        }

        //Milk Fat Base 1 [low fat to high fat] increases production inversely [high production to low production]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[66] = (ThreadLocalRandom.current().nextInt(5) + 1);

        } else {
            initialGenes[66] = (2);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[67] = (ThreadLocalRandom.current().nextInt(5) + 1);

        } else {
            initialGenes[67] = (2);
        }

        //Milk Fat Base 2
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[68] = (ThreadLocalRandom.current().nextInt(5) + 1);

        } else {
            initialGenes[68] = (2);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[69] = (ThreadLocalRandom.current().nextInt(5) + 1);

        } else {
            initialGenes[69] = (2);
        }

        //horn nub controller 1 [taurus, indus]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[70] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            if (wildType == 1){
                initialGenes[70] = (2);
            }else{
                initialGenes[70] = (1);
            }
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[71] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            if (wildType == 1){
                initialGenes[71] = (2);
            }else{
                initialGenes[71] = (1);
            }
        }

        //horn nub controller 2 [taurus, medium, indus]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[72] = (ThreadLocalRandom.current().nextInt(3) + 1);

        } else {
            if (wildType == 1){
                initialGenes[72] = (3);
            }else{
                initialGenes[72] = (1);
            }
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[73] = (ThreadLocalRandom.current().nextInt(3) + 1);

        } else {
            if (wildType == 1){
                initialGenes[73] = (3);
            }else{
                initialGenes[73] = (1);
            }
        }

        //horn nub controller 3 [indus, taurus]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[74] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            if (wildType == 1){
                initialGenes[74] = (1);
            }else{
                initialGenes[74] = (2);
            }
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[75] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            if (wildType == 1){
                initialGenes[75] = (1);
            }else{
                initialGenes[75] = (2);
            }
        }

        //african horn gene [african horned, wildtype]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[76] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            if (wildType == 1){
                initialGenes[76] = (1);
            }else {
                initialGenes[76] = (2);
            }
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[77] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            if (wildType == 1){
                initialGenes[77] = (1);
            }else {
                initialGenes[77] = (2);
            }
        }

        //scur gene [scurs, wildtype]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[78] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[78] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[79] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[79] = (1);
        }

        //cow horn length modifier [wildtype, longer horns 1, shorter horns 2, shorter horns 3]
            initialGenes[80] = (ThreadLocalRandom.current().nextInt(4) + 1);

            initialGenes[81] = (ThreadLocalRandom.current().nextInt(4) + 1);

        generateHornGenes(initialGenes);

        //parasitic immunity gene
        if (wildType != 4) {
            initialGenes[118] = 1;
            initialGenes[119] = 1;
        } else {
            initialGenes[118] = 2;
            initialGenes[119] = 2;
        }

        return initialGenes;
    }

    private void generateHornGenes(int[] genesArray) {
        //horn shortener [wildtype, shorter horns]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            genesArray[82] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            genesArray[82] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            genesArray[83] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            genesArray[83] = (2);
        }

        //modifier [wildtype, ...]
        genesArray[84] = (ThreadLocalRandom.current().nextInt(4) + 1);
        genesArray[85] = (ThreadLocalRandom.current().nextInt(4) + 1);

        //cow horn scale 1 [wildtype, 1.25]
        genesArray[86] = (ThreadLocalRandom.current().nextInt(2) + 1);
        genesArray[87] = (ThreadLocalRandom.current().nextInt(2) + 1);
        //cow horn scale 2 [wildtype, 1.25]
        genesArray[88] = (ThreadLocalRandom.current().nextInt(2) + 1);
        genesArray[89] = (ThreadLocalRandom.current().nextInt(2) + 1);

        // horn scale 3 [wildtype, 2.0]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            genesArray[90] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            genesArray[90] = (2);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            genesArray[91] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            genesArray[91] = (2);
        }

        //cow horn smoother
        genesArray[92] = ThreadLocalRandom.current().nextInt(9999) + 1;
        genesArray[93] = ThreadLocalRandom.current().nextInt(9999) + 1;

        //cow horn twist ... place matches following horn piece numbers { 4 5 6 7 8 9 }
        genesArray[94] = ThreadLocalRandom.current().nextInt(999999) + 1;
        genesArray[95] = ThreadLocalRandom.current().nextInt(999999) + 1;

        //cow horn base twist  ... place matches following horn piece numbers { *total twist mod* 1 2 3 }
        genesArray[96] = ThreadLocalRandom.current().nextInt(9999) + 1;
        genesArray[97] = ThreadLocalRandom.current().nextInt(9999) + 1;

        // cow horn root
        genesArray[98] = ThreadLocalRandom.current().nextInt(999) + 1;
        genesArray[99] = ThreadLocalRandom.current().nextInt(999) + 1;

        //cow horn1 X and Z
        genesArray[100] = ThreadLocalRandom.current().nextInt(999) + 1;
        genesArray[101] = ThreadLocalRandom.current().nextInt(999) + 1;

        //cow horn2 X and Z
        genesArray[102] = ThreadLocalRandom.current().nextInt(999) + 1;
        genesArray[103] = ThreadLocalRandom.current().nextInt(999) + 1;

        //cow horn3 X and Z
        genesArray[104] = ThreadLocalRandom.current().nextInt(999) + 1;
        genesArray[105] = ThreadLocalRandom.current().nextInt(999) + 1;

        //cow horn4 X and Z
        genesArray[106] = ThreadLocalRandom.current().nextInt(999) + 1;
        genesArray[107] = ThreadLocalRandom.current().nextInt(999) + 1;

        //cow horn5 X and Z
        genesArray[108] = ThreadLocalRandom.current().nextInt(999) + 1;
        genesArray[109] = ThreadLocalRandom.current().nextInt(999) + 1;

        //cow horn6 X and Z
        genesArray[110] = ThreadLocalRandom.current().nextInt(999) + 1;
        genesArray[111] = ThreadLocalRandom.current().nextInt(999) + 1;

        //cow horn7 X and Z
        genesArray[112] = ThreadLocalRandom.current().nextInt(999) + 1;
        genesArray[113] = ThreadLocalRandom.current().nextInt(999) + 1;

        //cow horn8 X and Z
        genesArray[114] = ThreadLocalRandom.current().nextInt(999) + 1;
        genesArray[115] = ThreadLocalRandom.current().nextInt(999) + 1;

        //cow horn9 X and Z
        genesArray[116] = ThreadLocalRandom.current().nextInt(999) + 1;
        genesArray[117] = ThreadLocalRandom.current().nextInt(999) + 1;
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

            this.goalSelector.addGoal(1, new EnhancedPanicGoal(this, speed*1.5D));
            this.goalSelector.addGoal(2, new BreedGoal(this, speed));
            this.goalSelector.addGoal(3, new EnhancedTemptGoal(this, speed*1.25D, false, Ingredient.fromItems(Items.CARROT_ON_A_STICK)));
            this.goalSelector.addGoal(3, new EnhancedTemptGoal(this, speed*1.25D, false, TEMPTATION_ITEMS));
            this.goalSelector.addGoal(4, new FollowParentGoal(this, speed*1.25D));
            this.goalSelector.addGoal(4, new EnhancedAINurseFromMotherGoal(this, motherUUID, speed*1.25D));
            wanderEatingGoal = new EnhancedWaterAvoidingRandomWalkingEatingGoal(this, speed, 7, 0.001F, 120, 2, 20);
            this.goalSelector.addGoal(6, wanderEatingGoal);
        }
        aiConfigured = true;
    }

    public void setGenes(int[] genes) {
        this.genes = genes;
    }

    public int[] getGenes() {
        return this.genes;
    }

    public void setMateGenes(int[] mateGenes){
        this.mateGenes = mateGenes;
    }

    public static class GroupData implements ILivingEntityData {

        public int[] groupGenes;

        public GroupData(int[] groupGenes) {
            this.groupGenes = groupGenes;
        }

    }

}
