package mokiyoki.enhancedanimals.entity;

import mokiyoki.enhancedanimals.ai.general.EnhancedLookAtGoal;
import mokiyoki.enhancedanimals.ai.general.EnhancedLookRandomlyGoal;
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
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.BreedGoal;
import net.minecraft.entity.ai.goal.FollowParentGoal;
import net.minecraft.entity.ai.goal.PanicGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.passive.horse.AbstractChestedHorseEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.Inventory;
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
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import static mokiyoki.enhancedanimals.util.handlers.EventRegistry.ENHANCED_HORSE;

public class EnhancedHorse extends AbstractChestedHorseEntity {
    private static final DataParameter<String> SHARED_GENES = EntityDataManager.<String>createKey(EnhancedHorse.class, DataSerializers.STRING);
    private static final DataParameter<Float> HORSE_SIZE = EntityDataManager.createKey(EnhancedHorse.class, DataSerializers.FLOAT);
    protected static final DataParameter<String> HORSE_STATUS = EntityDataManager.createKey(EnhancedHorse.class, DataSerializers.STRING);
    protected static final DataParameter<Boolean> SLEEPING = EntityDataManager.createKey(EnhancedHorse.class, DataSerializers.BOOLEAN);
    private static final DataParameter<String> BIRTH_TIME = EntityDataManager.<String>createKey(EnhancedHorse.class, DataSerializers.STRING);

    private static final String[] HORSE_TEXTURES_TESTNUMBER = new String[] {
            "0.png", "1.png", "2.png", "3.png", "4.png", "5.png", "6.png", "7.png", "8.png"
    };

    private static final String[] HORSE_TEXTURES_TESTLETTER = new String[] {
            "a.png", "b.png", "c.png", "d.png", "e.png", "f.png", "g.png", "h.png", "i.png"
    };

    private static final String[] HORSE_TEXTURES_SKIN = new String[] {
            "skin_black.png", "skin_freckled.png", "skin_rosy.png", "skin_pink.png"
    };

    private static final String[] HORSE_TEXTURES_SKINSPOT_TOBIANO = new String[] {
            "", 
            "skinspot_tobiano_0.png", "skinspot_tobiano_1.png", "skinspot_tobiano_2.png", "skinspot_tobiano_3.png", "skinspot_tobiano_4.png", "skinspot_tobiano_5.png", "skinspot_tobiano_6.png", "skinspot_tobiano_7.png", "skinspot_tobiano_8.png", "skinspot_tobiano_9.png", "skinspot_tobiano_a.png", "skinspot_tobiano_b.png", "skinspot_tobiano_c.png", "skinspot_tobiano_d.png", "skinspot_tobiano_e.png", "skinspot_tobiano_f.png"
    };
    private static final String[] HORSE_TEXTURES_SKINSPOT_DOMINANTWHITE1 = new String[] {
            "",
            "skinspot_domwhite1_0.png", "skinspot_domwhite1_1.png", "skinspot_domwhite1_2.png", "skinspot_domwhite1_3.png", "skinspot_domwhite1_4.png", "skinspot_domwhite1_5.png", "skinspot_domwhite1_6.png", "skinspot_domwhite1_7.png", "skinspot_domwhite1_8.png", "skinspot_domwhite1_9.png", "skinspot_domwhite1_a.png", "skinspot_domwhite1_b.png", "skinspot_domwhite1_c.png", "skinspot_domwhite1_d.png", "skinspot_domwhite1_e.png", "skinspot_domwhite1_f.png"
    };
    private static final String[] HORSE_TEXTURES_SKINSPOT_DOMINANTWHITE2 = new String[] {
            "",
            "skinspot_domwhite2_0.png", "skinspot_domwhite2_1.png", "skinspot_domwhite2_2.png", "skinspot_domwhite2_3.png", "skinspot_domwhite2_4.png", "skinspot_domwhite2_5.png", "skinspot_domwhite2_6.png", "skinspot_domwhite2_7.png", "skinspot_domwhite2_8.png", "skinspot_domwhite2_9.png", "skinspot_domwhite2_a.png", "skinspot_domwhite2_b.png", "skinspot_domwhite2_c.png", "skinspot_domwhite2_d.png", "skinspot_domwhite2_e.png", "skinspot_domwhite2_f.png"
    };

    private static final String[] HORSE_TEXTURES_SKINSPOT_APPALOOSA = new String[] {
            "",
            "skinspot_appaloosa_0.png", "skinspot_appaloosa_1.png", "skinspot_appaloosa_2.png", "skinspot_appaloosa_3.png", "skinspot_appaloosa_4.png", "skinspot_appaloosa_5.png", "skinspot_appaloosa_6.png", "skinspot_appaloosa_7.png", "skinspot_appaloosa_8.png", "skinspot_appaloosa_9.png", "skinspot_appaloosa_a.png", "skinspot_appaloosa_b.png", "skinspot_appaloosa_c.png", "skinspot_appaloosa_d.png", "skinspot_appaloosa_e.png", "skinspot_appaloosa_f.png"
    };

    private static final String[] HORSE_TEXTURES_BASE = new String[] {
            "r_solid_white.png"
    };
//    private static final String[] HORSE_TEXTURES_RED = new String[] {
//            "", "solid_red.png"
//    };
    private static final String[] HORSE_TEXTURES_DUN = new String[] {
            "", "dun_smooth_strong.png", "dun_smooth_medium.png", "dun_smooth_weak.png",
                "dun_wild_strong.png", "dun_wild_medium.png", "dun_wild_weak.png"
    };
    private static final String[] HORSE_TEXTURES_BLACKPATTERN = new String[] {
            "", "b_bay_smooth_strong.png", "b_sealbrown.png"
    };
    private static final String[] HORSE_TEXTURES_SPOT_TOBIANO = new String[] {
            "",
            "spot_tobiano_0.png", "spot_tobiano_1.png", "spot_tobiano_2.png", "spot_tobiano_3.png", "spot_tobiano_4.png", "spot_tobiano_5.png", "spot_tobiano_6.png", "spot_tobiano_7.png", "spot_tobiano_8.png", "spot_tobiano_9.png", "spot_tobiano_a.png", "spot_tobiano_b.png", "spot_tobiano_c.png", "spot_tobiano_d.png", "spot_tobiano_e.png", "spot_tobiano_f.png"
    };
    private static final String[] HORSE_TEXTURES_SPOT_DOMINANTWHITE = new String[] {
            "", "spot_domwhite_solid.png", "spot_domwhite_1.png", "spot_domwhite1_2.png", "spot_domwhite1_3.png", "spot_domwhite1_4.png", "spot_domwhite1_5.png", "spot_domwhite1_6.png", "spot_domwhite1_7.png", "spot_domwhite1_8.png", "spot_domwhite1_9.png", "spot_domwhite1_a.png", "spot_domwhite1_b.png", "spot_domwhite1_c.png", "spot_domwhite1_d.png", "spot_domwhite1_e.png", "spot_domwhite1_f.png"
    };

    private static final String[] HORSE_TEXTURES_SPOT_APPALOOSA = new String[] {
            "",
            "spot_appaloosa_0.png", "spot_appaloosa_1.png", "spot_appaloosa_2.png", "spot_appaloosa_3.png", "spot_appaloosa_4.png", "spot_appaloosa_5.png", "spot_appaloosa_6.png", "spot_appaloosa_7.png", "spot_appaloosa_8.png", "spot_appaloosa_9.png", "spot_appaloosa_a.png", "spot_appaloosa_b.png", "spot_appaloosa_c.png", "spot_appaloosa_d.png", "spot_appaloosa_e.png", "spot_appaloosa_f.png"
    };

    private static final String[] HORSE_TEXTURES_SILVER = new String[]  {
            "silver_mask.png"
    };

    private static final String[] HORSE_TEXTURES_SCLERA = new String[]  {
            "sclera_black.png", "sclera_white.png"
    };

    private static final String[] HORSE_TEXTURES_EYES = new String[]  {
            "eyel_black.png", "eyel_brown.png", "eyel_hazel.png", "eyel_yellow.png", "eyel_blue.png", "eyel_white.png"
    };

    private static final String[] HORSE_TEXTURES_HOOVES = new String[]  {
            "hooves_black.png", "hooves_brown.png"
    };

    private static final String[] HORSE_TEXTURES_BLANKETS = new String[]  {
            "blanket_trader.png", "blanket_black.png", "blanket_blue.png", "blanket_brown.png", "blanket_cyan.png", "blanket_grey.png", "blanket_green.png", "blanket_lightblue.png", "blanket_lightgrey.png", "blanket_lime.png", "blanket_magenta.png", "blanket_orange.png", "blanket_pink.png", "blanket_purple.png", "blanket_red.png", "blanket_white.png", "blanket_yellow.png"
    };

    private static final String[] HORSE_TEXTURES_SADDLE = new String[]  {
            "", "saddle_vanilla.png", "saddle_western_dyeable.png", "saddle_english.png"
    };

    private static final String[] HORSE_TEXTURES_SADDLE_DECO = new String[]  {
            "", "c_saddleseat.png"
    };

    private final List<String> horseTextures = new ArrayList<>();

    protected static final Ingredient TEMPTATION_ITEMS = Ingredient.fromItems(Blocks.MELON, Blocks.PUMPKIN, Blocks.GRASS, Blocks.HAY_BLOCK, Blocks.VINE, Blocks.TALL_GRASS, Blocks.OAK_LEAVES, Blocks.DARK_OAK_LEAVES, Items.CARROT, Items.WHEAT, Items.SUGAR, Items.APPLE, ModBlocks.UNBOUNDHAY_BLOCK);
    private static final Ingredient MILK_ITEMS = Ingredient.fromItems(ModItems.MILK_BOTTLE, ModItems.HALF_MILK_BOTTLE);
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
        put(new ItemStack(ModBlocks.UNBOUNDHAY_BLOCK).getItem(), 54000);
    }};

    public boolean isFemale = true;
    private static final int WTC = EanimodCommonConfig.COMMON.wildTypeChance.get();
    private static final int GENES_LENGTH = 72;
    private int[] genes = new int[GENES_LENGTH];
    private int[] mateGenes = new int[GENES_LENGTH];
    private int[] mitosisGenes = new int[GENES_LENGTH];
    private int[] mateMitosisGenes = new int[GENES_LENGTH];

    private float[] horseColouration = null;
    private float horseSize;
    protected float hunger = 0;
    private int healTicks = 0;
    protected int horseTimer;
    protected boolean aiConfigured = false;
    protected String motherUUID = "";
    protected EnhancedWaterAvoidingRandomWalkingEatingGoal wanderEatingGoal;
    protected int gestationTimer = 0;
    protected boolean pregnant = false;

    protected Boolean sleeping = false;
    protected int awokenTimer = 0;

    public EnhancedHorse(EntityType<? extends EnhancedHorse> entityType, World worldIn) {
        super(entityType, worldIn);
    }
    
    @Override
    protected void registerGoals() {
        //Todo add the temperamants
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(7, new EnhancedLookAtGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.addGoal(8, new EnhancedLookRandomlyGoal(this));
    }

    protected void updateAITasks()
    {
        this.horseTimer = this.wanderEatingGoal.getEatingGrassTimer();
        super.updateAITasks();
    }

    protected void registerData() {
        super.registerData();
        this.dataManager.register(SHARED_GENES, new String());
        this.dataManager.register(HORSE_SIZE, 0.0F);
        this.dataManager.register(HORSE_STATUS, new String());
        this.dataManager.register(SLEEPING, false);
        this.dataManager.register(BIRTH_TIME, "0");
    }

    protected void setHorseSize(float size) {
        this.dataManager.set(HORSE_SIZE, size);
    }

    public float getSize() {
        return this.dataManager.get(HORSE_SIZE);
    }

    protected void setHorseStatus(String status) { this.dataManager.set(HORSE_STATUS, status); }

    public String getHorseStatus() { return this.dataManager.get(HORSE_STATUS); }

    public void setSleeping(Boolean sleeping) {
        this.sleeping = sleeping;
        this.dataManager.set(SLEEPING, sleeping); }

    public Boolean isAnimalSleeping() {
        if (this.sleeping == null) {
            return false;
        } else {
            sleeping = this.dataManager.get(SLEEPING);
            return sleeping;
        }
    }

    public void awaken() {
        this.awokenTimer = 200;
        setSleeping(false);
    }


    public Inventory getEnhancedInventory() {
        return null;
    }

    public float getHunger(){ return hunger; }

    public void decreaseHunger(float decrease) {
        if (this.hunger - decrease < 0) {
            this.hunger = 0;
        } else {
            this.hunger = this.hunger - decrease;
        }
    }

    public void updatePassenger(Entity passenger) {
        if (this.isPassenger(passenger)) {
            float f = MathHelper.cos(this.renderYawOffset * ((float)Math.PI / 180F));
            float f1 = MathHelper.sin(this.renderYawOffset * ((float)Math.PI / 180F));
            float f2 = 0.15F;
            passenger.setPosition(this.getPosX() + (double)(f2 * f1), this.getPosY() + this.getMountedYOffset() + passenger.getYOffset(), this.getPosZ() - (double)(f2 * f));
        }
    }

    /**
     * Returns the Y offset from the entity's position for any entity riding this one.
     */
    public double getMountedYOffset() {
        return (double)this.getHeight() * 0.725D;
    }

    /**
     * returns true if all the conditions for steering the entity are met. For pigs, this is true if it is being ridden
     * by a player and the player is holding a carrot-on-a-stick
     */
    public boolean canBeSteered() {
        return true;
    }


    protected SoundEvent getAmbientSound() { return SoundEvents.ENTITY_HORSE_AMBIENT; }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) { return SoundEvents.ENTITY_HORSE_HURT; }

    protected SoundEvent getDeathSound() { return SoundEvents.ENTITY_HORSE_DEATH; }

    protected void playStepSound(BlockPos pos, BlockState blockIn) { this.playSound(SoundEvents.ENTITY_HORSE_STEP, 0.15F, 1.0F); }

    /**
     * Returns the volume for the sounds this mob makes.
     */
    protected float getSoundVolume() {
        return 0.4F;
    }

//    protected void registerAttributes() {
//        super.registerAttributes();
//        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(8.0D);
//        this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.23000000417232513D);
//    }

    public void livingTick() {
        super.livingTick();

        if (this.world.isRemote) {
            this.horseTimer = Math.max(0, this.horseTimer - 1);
        } else {

            if (!this.world.isDaytime() && awokenTimer == 0 && !sleeping) {
                setSleeping(true);
                healTicks = 0;
            } else if (awokenTimer > 0) {
                awokenTimer--;
            } else if (this.world.isDaytime() && sleeping) {
                setSleeping(false);
            }

            if(pregnant) {
                gestationTimer++;
//                int days = EanimodCommonConfig.COMMON.gestationDaysHorse.get();
                int days = 2;
                if (days/2 < gestationTimer) {
                    setHorseStatus(EntityState.PREGNANT.toString());
                }
                if (hunger > days*(0.75) && days !=0) {
                    pregnant = false;
                    setHorseStatus(EntityState.ADULT.toString());
                }
                if (gestationTimer >= days) {
                    pregnant = false;
                    setHorseStatus(EntityState.MOTHER.toString());
                    gestationTimer = -48000;

                    mixMateMitosisGenes();
                    mixMitosisGenes();

                    createAndSpawnEnhancedChild(this.world);

                }
            }
            if (this.getIdleTime() < 100) {
                if (hunger <= 72000) {
                    if (sleeping) {
//                        int days = EanimodCommonConfig.COMMON.gestationDaysHorse.get();
                        int days = 2;
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
                }

                if (getHorseStatus().equals(EntityState.MOTHER.toString())) {
                    if (gestationTimer == 0) {
                        setHorseStatus(EntityState.ADULT.toString());
                    }
                }
                if (this.isChild()) {
                    if (getHorseStatus().equals(EntityState.CHILD_STAGE_ONE.toString()) && this.getGrowingAge() < -16000) {
                        if(hunger < 5000) {
                            setHorseStatus(EntityState.CHILD_STAGE_TWO.toString());
                        } else {
                            this.setGrowingAge(-16500);
                        }
                    } else if (getHorseStatus().equals(EntityState.CHILD_STAGE_TWO.toString()) && this.getGrowingAge() < -8000) {
                        if(hunger < 5000) {
                            setHorseStatus(EntityState.CHILD_STAGE_THREE.toString());
                        } else {
                            this.setGrowingAge(-8500);
                        }
                    }
                } else if (getHorseStatus().equals(EntityState.CHILD_STAGE_THREE.toString())) {
                    setHorseStatus(EntityState.ADULT.toString());

                    //TODO remove the child follow mother ai

                }
            }
//            lethalGenes();
        }
    }

    protected void createAndSpawnEnhancedChild(World inWorld) {
        EnhancedHorse enhancedhorse = ENHANCED_HORSE.create(this.world);
        int[] babyGenes = getFoalGenes(this.mitosisGenes, this.mateMitosisGenes);
        enhancedhorse.setGenes(babyGenes);
        enhancedhorse.setSharedGenes(babyGenes);
        enhancedhorse.setHorseSize();
        enhancedhorse.setGrowingAge(-84000);
        enhancedhorse.setHorseStatus(EntityState.CHILD_STAGE_ONE.toString());
        enhancedhorse.setBirthTime(String.valueOf(inWorld.getGameTime()));
        enhancedhorse.setLocationAndAngles(this.getPosX(), this.getPosY(), this.getPosZ(), this.rotationYaw, 0.0F);
        enhancedhorse.setMotherUUID(this.getUniqueID().toString());
        enhancedhorse.configureAI();
        this.world.addEntity(enhancedhorse);
    }

    protected void setHorseSize() {
        float size = 1.0F;

        this.horseSize = size;
        this.setHorseSize(size);
    }

    public void lethalGenes(){
        //put in the lethal combinations of dominant white
        if((genes[18] != 20 && genes[19] != 20 && genes[18] != 28 && genes[19] != 28 && genes[18] != 29 && genes[19] != 29) || (genes[18] == 12 || genes[19] == 12)) {
            this.remove();
        } else if (genes[32] == 2 && genes[33] == 2) {
            //TODO change the foal to a skeleton horse that attacks
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

    protected void setBirthTime(String birthTime) {
        this.dataManager.set(BIRTH_TIME, birthTime);
    }

    public String getBirthTime() { return this.dataManager.get(BIRTH_TIME); }

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

    protected void dropSpecialItems(DamageSource source, int looting, boolean recentlyHitIn) {
        super.dropSpecialItems(source, looting, recentlyHitIn);

        int leatherDrop = this.rand.nextInt(3);

        if (!this.isBurning()){
            ItemStack leatherStack = new ItemStack(Items.LEATHER, leatherDrop);
            this.entityDropItem(leatherStack);
        }
    }

    public boolean isBreedingItem(ItemStack stack) {
        return BREED_ITEMS.test(stack);
    }

    @Override
    public AgeableEntity func_241840_a(ServerWorld serverWorld, AgeableEntity ageable) {
        if(pregnant) {
            ((EnhancedHorse)ageable).pregnant = true;
            ((EnhancedHorse)ageable).setMateGenes(this.genes);
            ((EnhancedHorse)ageable).mixMateMitosisGenes();
            ((EnhancedHorse)ageable).mixMitosisGenes();
        } else {
            pregnant = true;
            this.mateGenes = ((EnhancedHorse) ageable).getGenes();
            mixMateMitosisGenes();
            mixMitosisGenes();
        }


        this.setGrowingAge(10);
        this.resetInLove();
        ageable.setGrowingAge(10);
        ((EnhancedHorse)ageable).resetInLove();

        ServerPlayerEntity entityplayermp = this.getLoveCause();
        if (entityplayermp == null && ((EnhancedHorse)ageable).getLoveCause() != null) {
            entityplayermp = ((EnhancedHorse)ageable).getLoveCause();
        }

        if (entityplayermp != null) {
            entityplayermp.addStat(Stats.ANIMALS_BRED);
            CriteriaTriggers.BRED_ANIMALS.trigger(entityplayermp, this, ((EnhancedHorse)ageable), (AgeableEntity)null);
        }

        return null;
    }

    @OnlyIn(Dist.CLIENT)
    public String getHorseTexture() {
        if (this.horseTextures.isEmpty()) {
            this.setTexturePaths();
        }
        return this.horseTextures.stream().collect(Collectors.joining("/","enhanced_horse/",""));

    }

    @OnlyIn(Dist.CLIENT)
    public String[] getVariantTexturePaths() {
        if (this.horseTextures.isEmpty()) {
            this.setTexturePaths();
        }

        return this.horseTextures.stream().toArray(String[]::new);
    }

    @OnlyIn(Dist.CLIENT)
    private void setTexturePaths() {
        int[] genesForText = getSharedGenes();
        if (genesForText != null) {
            int dun = 0;
            int pattern = 0;
            int number = 0;
            int letter = 0;
            int sclera = 0;
            boolean silver = false;
            char[] uuidArry = getCachedUniqueIdString().toCharArray();

            if ((genesForText[18] == 20 || genesForText[18] == 28 || genesForText[18] == 29) && (genesForText[19] == 20 || genesForText[19] == 28 || genesForText[19] == 29)) {

                if (genesForText[12] == 2 && genesForText[13] == 2) {
                    //horse is red based
                } else {
                    if (genesForText[14] == 4 && genesForText[15] == 4) {
                        //horse is black based
                    } else if (genesForText[14] == 1 || genesForText[15] == 1) {
                        //wildtype bay
                        pattern = 1;
                    } else if (genesForText[14] == 2 || genesForText[15] == 2) {
                        //heavy marked bay
                        pattern = 1;
                    } else {
                        //seal brown
                        pattern = 2;
                    }
                }

                //TODO mushroom

                if (genesForText[16] == 1 && genesForText[17] == 1) {
                    //dun
                    dun = 1;
                }

                if (genesForText[60] == 1 || genesForText[61] == 1) {
                    //mealy markings
                }

                //TODO sooty

                //TODO liver

                if (Character.isDigit(uuidArry[16])) {
                    number = uuidArry[16] - 48;
                    if (number >= 8) {
                        number = number - 8;
                    }
                } else {
                    char test = uuidArry[16];
                    switch (test) {
                        case 'a':
                            number = 3;
                            break;
                        case 'b':
                            number = 4;
                            break;
                        case 'c':
                            number = 5;
                            break;
                        case 'd':
                            number = 6;
                            break;
                        case 'e':
                            number = 7;
                            break;
                        case 'f':
                            number = 8;
                            break;
                    }
                }

                if (Character.isDigit(uuidArry[17])) {
                    letter = uuidArry[17] - 48;
                    if (letter >= 8) {
                        letter = letter - 8;
                    }
                } else {
                    char test = uuidArry[17];
                    switch (test) {
                        case 'a':
                            letter = 3;
                            break;
                        case 'b':
                            letter = 4;
                            break;
                        case 'c':
                            letter = 5;
                            break;
                        case 'd':
                            letter = 6;
                            break;
                        case 'e':
                            letter = 7;
                            break;
                        case 'f':
                            letter = 8;
                            break;
                    }
                }
            }

            if (genesForText[26] == 2 && genesForText[27] ==2) {
                silver = true;
            }

            if (genesForText[36] == 2 || genesForText[37] == 2) {
                sclera = 1;
            }

            this.horseTextures.add(HORSE_TEXTURES_SKIN[0]);
            this.horseTextures.add(HORSE_TEXTURES_BASE[0]);
            if (dun != 0) {
                this.horseTextures.add(HORSE_TEXTURES_DUN[dun]);
            }
            if (pattern != 0) {
                this.horseTextures.add(HORSE_TEXTURES_BLACKPATTERN[pattern]);
            }
            if (silver) {
                this.horseTextures.add(HORSE_TEXTURES_SILVER[0]);
            }
            this.horseTextures.add(HORSE_TEXTURES_TESTNUMBER[number]);
            this.horseTextures.add(HORSE_TEXTURES_TESTLETTER[letter]);
            this.horseTextures.add(HORSE_TEXTURES_EYES[1]);
            this.horseTextures.add(HORSE_TEXTURES_SCLERA[sclera]);
            this.horseTextures.add(HORSE_TEXTURES_HOOVES[0]);
            this.horseTextures.add(HORSE_TEXTURES_BLANKETS[14]);
            this.horseTextures.add(HORSE_TEXTURES_SADDLE[1]);
        }
    }


    @OnlyIn(Dist.CLIENT)
    public float[] getRgb() {
        if (horseColouration == null) {
            horseColouration = new float[6];
            int[] genesForText = getSharedGenes();
            //Dominant White Spotting [ W1-, W2-, W3-, W4-, W5, W6, W7, W8, W9-, W10-, W11-, W12, W13-, W14-, W15, W16, W17-, W18, W19, W20(Sabino2), W21, W22, W23-, W24-, W25-, W26, W27, Sabino1, wildtype+ ]
            if ((genesForText[18] == 20 || genesForText[18] == 28 || genesForText[18] == 29) && (genesForText[19] == 20 || genesForText[19] == 28 || genesForText[19] == 29)) {

                horseColouration[0] = 0.047F;
                horseColouration[1] = 0.20F;
                horseColouration[2] = 0.07F;

                horseColouration[3] = 0.047F;
                horseColouration[4] = 0.75F;
                horseColouration[5] = 0.5F;

                if (genesForText[12] == 2 && genesForText[13] == 2) {
                    //red
                    horseColouration[0] = horseColouration[3];
                    horseColouration[1] = horseColouration[4];
                    horseColouration[2] = horseColouration[5];
                } else if (genesForText[14] == 4 && genesForText[15] == 4) {
                    //black
                    horseColouration[3] = horseColouration[0];
                    horseColouration[4] = horseColouration[1];
                    horseColouration[5] = horseColouration[2];
                }


                if (genesForText[22] != 1 || genesForText[23] != 1) {
                    if (genesForText[22] == 2 || genesForText[23] == 2) {
                        if (genesForText[22] == 1 || genesForText[23] == 1) {
                            //cream
                            horseColouration[1] = horseColouration[1] * 0.75F;
                            horseColouration[2] = horseColouration[2] * 1.2F;
                            horseColouration[3] = horseColouration[3] * 2.0F;
                            horseColouration[4] = horseColouration[4] * 0.75F;
                            horseColouration[5] = horseColouration[5] * 1.45F;
                        } else {
                            //double dilute
                            horseColouration[1] = horseColouration[1] * 0.75F;
                            horseColouration[2] = horseColouration[2] * 1.2F;
                            horseColouration[3] = horseColouration[3] * 2.0F;
                            horseColouration[4] = (horseColouration[4] + 0.4F) / 4.0F;
                            horseColouration[5] = (horseColouration[5] + 4.0F) / 5.0F;

                            if (genesForText[22] == 3 || genesForText[23] == 3) {
                                //pseudo double dilute

                            }
                        }
                    } else {
                        //pearl
                        horseColouration[3] = horseColouration[3] * 1.55F;
                        horseColouration[4] = horseColouration[4] * 0.75F;
                        horseColouration[5] = horseColouration[5] * 1.5F;
                    }
                }


                //checks that numbers are within the valid range
                for (int i = 0; i <= 5; i++) {
                    if (horseColouration[i] > 1.0F) {
                        horseColouration[i] = 1.0F;
                    } else if (horseColouration[i] < 0.0F) {
                        horseColouration[i] = 0.0F;
                    }
                }

                //changes horse melanin from HSB to RGB
                int rgb = Color.HSBtoRGB(horseColouration[0], horseColouration[1], horseColouration[2]);
                horseColouration[0] = (rgb >> 16) & 0xFF;
                horseColouration[1] = (rgb >> 8) & 0xFF;
                horseColouration[2] = rgb & 0xFF;

                //changes horse pheomelanin from HSB to RGB
                rgb = Color.HSBtoRGB(horseColouration[3], horseColouration[4], horseColouration[5]);
                horseColouration[3] = (rgb >> 16) & 0xFF;
                horseColouration[4] = (rgb >> 8) & 0xFF;
                horseColouration[5] = rgb & 0xFF;

                for (int i = 0; i <= 5; i++) {
                    horseColouration[i] = horseColouration[i] / 255.0F;
                }

            } else {
                for (int i = 0; i <= 5; i++) {
                    horseColouration[i] = 1.0F;
                }
            }
        }

        return horseColouration;
    }

    @Override
    public ActionResultType func_230254_b_(PlayerEntity entityPlayer, Hand hand) {
        ItemStack itemStack = entityPlayer.getHeldItem(hand);
        Item item = itemStack.getItem();
        if (!this.world.isRemote) {

            if (item instanceof DebugGenesBook) {
                Minecraft.getInstance().keyboardListener.setClipboardString(this.dataManager.get(SHARED_GENES));
            } else if (!getHorseStatus().equals(EntityState.CHILD_STAGE_ONE.toString()) && TEMPTATION_ITEMS.test(itemStack) && hunger >= 6000) {
                if (this.foodWeightMap.containsKey(item)) {
                    decreaseHunger(this.foodWeightMap.get(item));
                } else {
                    decreaseHunger(6000);
                }
                if (!entityPlayer.abilities.isCreativeMode) {
                    itemStack.shrink(1);
                }
            } else if (this.isChild() && MILK_ITEMS.test(itemStack) && hunger >= 6000) {

                if (!entityPlayer.abilities.isCreativeMode) {
                    if (item == ModItems.HALF_MILK_BOTTLE) {
                        decreaseHunger(6000);
                        if (itemStack.isEmpty()) {
                            entityPlayer.setHeldItem(hand, new ItemStack(Items.GLASS_BOTTLE));
                        } else if (!entityPlayer.inventory.addItemStackToInventory(new ItemStack(Items.GLASS_BOTTLE))) {
                            entityPlayer.dropItem(new ItemStack(Items.GLASS_BOTTLE), false);
                        }
                    } else if (item == ModItems.MILK_BOTTLE) {
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
                                entityPlayer.setHeldItem(hand, new ItemStack(ModItems.HALF_MILK_BOTTLE));
                            } else if (!entityPlayer.inventory.addItemStackToInventory(new ItemStack(ModItems.HALF_MILK_BOTTLE))) {
                                entityPlayer.dropItem(new ItemStack(ModItems.HALF_MILK_BOTTLE), false);
                            }
                        }
                    }
                }

            }
        }
        return super.func_230254_b_(entityPlayer, hand);
    }


    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);

        //store this horses's genes
        ListNBT geneList = new ListNBT();
        for (int i = 0; i < genes.length; i++) {
            CompoundNBT nbttagcompound = new CompoundNBT();
            nbttagcompound.putInt("Gene", genes[i]);
            geneList.add(nbttagcompound);
        }
        compound.put("Genes", geneList);

        //store this horses's mate's genes
        ListNBT mateGeneList = new ListNBT();
        for (int i = 0; i < mateGenes.length; i++) {
            CompoundNBT nbttagcompound = new CompoundNBT();
            nbttagcompound.putInt("Gene", mateGenes[i]);
            mateGeneList.add(nbttagcompound);
        }
        compound.put("FatherGenes", mateGeneList);

        compound.putBoolean("Pregnant", this.pregnant);
        compound.putInt("Gestation", this.gestationTimer);

        compound.putString("Status", getHorseStatus());
        compound.putFloat("Hunger", hunger);

        compound.putString("MotherUUID", this.motherUUID);

        compound.putString("BirthTime", this.getBirthTime());

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

        setHorseStatus(compound.getString("Status"));
        hunger = compound.getFloat("Hunger");

        this.motherUUID = compound.getString("MotherUUID");

        this.setBirthTime(compound.getString("BirthTime"));

        setSharedGenes(genes);
        setHorseSize();
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


    public int[] getFoalGenes(int[] mitosis, int[] mateMitosis) {
        Random rand = new Random();
        int[] foalGenes = new int[GENES_LENGTH];

        for (int i = 0; i < genes.length; i = (i + 2)) {
            boolean thisOrMate = rand.nextBoolean();
            if (thisOrMate) {
                foalGenes[i] = mitosis[i];
                foalGenes[i+1] = mateMitosis[i+1];
            } else {
                foalGenes[i] = mateMitosis[i];
                foalGenes[i+1] = mitosis[i+1];
            }
        }

        return foalGenes;
    }




    //Health 15-30

    //Speed 0.1125–0.3375

    //Jump 0.4–1.0


    //TODO draw the rest of the horse
    @Nullable
    @Override
    public ILivingEntityData onInitialSpawn(IServerWorld inWorld, DifficultyInstance difficulty, SpawnReason spawnReason, @Nullable ILivingEntityData livingdata, @Nullable CompoundNBT itemNbt) {
//        livingdata = super.onInitialSpawn(inWorld, difficulty, spawnReason, livingdata, itemNbt);
        int[] spawnGenes;

        if (livingdata instanceof GroupData) {
            int[] spawnGenes1 = ((GroupData) livingdata).groupGenes;
            int[] mitosis = new int[GENES_LENGTH];
            punnetSquare(mitosis, spawnGenes1);

            int[] spawnGenes2 = ((GroupData) livingdata).groupGenes;
            int[] mateMitosis = new int[GENES_LENGTH];
            punnetSquare(mateMitosis, spawnGenes2);
            spawnGenes = getFoalGenes(mitosis, mateMitosis);
        } else {
            spawnGenes = createInitialGenes(inWorld);
            livingdata = new GroupData(spawnGenes);
        }

        this.genes = spawnGenes;
        setSharedGenes(genes);
        setHorseSize();

        configureAI();

        int birthMod = ThreadLocalRandom.current().nextInt(30000, 80000);
        this.setBirthTime(String.valueOf(inWorld.getWorld().getGameTime() - birthMod));
        if (birthMod < 48000) {
            this.setGrowingAge(birthMod - 48000);
        }

        return livingdata;
    }

    private int[] createInitialGenes(IWorld inWorld) {
        int[] initialGenes = new int[GENES_LENGTH];

        //Health Base genes [ weaker, stronger1, wildtype+, stronger2 ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[0] = (ThreadLocalRandom.current().nextInt(4) + 1);

        } else {
            initialGenes[0] = (3);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[1] = (ThreadLocalRandom.current().nextInt(4) + 1);

        } else {
            initialGenes[1] = (3);
        }

        //Health Modifier genes [ weaker, stronger1, wildtype+, stronger2 ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[2] = (ThreadLocalRandom.current().nextInt(4) + 1);

        } else {
            initialGenes[2] = (3);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[3] = (ThreadLocalRandom.current().nextInt(4) + 1);

        } else {
            initialGenes[3] = (3);
        }

        //Speed Base genes [ weaker, stronger1, wildtype+, stronger2 ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[4] = (ThreadLocalRandom.current().nextInt(4) + 1);

        } else {
            initialGenes[4] = (3);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[5] = (ThreadLocalRandom.current().nextInt(4) + 1);

        } else {
            initialGenes[5] = (3);
        }

        //Speed Modifier genes [ weaker, stronger1, wildtype+, stronger2 ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[6] = (ThreadLocalRandom.current().nextInt(4) + 1);

        } else {
            initialGenes[6] = (3);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[7] = (ThreadLocalRandom.current().nextInt(4) + 1);

        } else {
            initialGenes[7] = (3);
        }

        //Jump Base genes [ weaker, stronger1, wildtype+, stronger2 ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[8] = (ThreadLocalRandom.current().nextInt(4) + 1);

        } else {
            initialGenes[8] = (3);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[9] = (ThreadLocalRandom.current().nextInt(4) + 1);

        } else {
            initialGenes[9] = (3);
        }

        //Jump Modifier genes [ weaker, stronger1, wildtype+, stronger2 ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[10] = (ThreadLocalRandom.current().nextInt(4) + 1);

        } else {
            initialGenes[10] = (3);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[11] = (ThreadLocalRandom.current().nextInt(4) + 1);

        } else {
            initialGenes[11] = (3);
        }

        //Extension [ wildtype+, red ]
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

        //Agouti [ Wildtype+, Bay2, sealbrown, solid/black ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[14] = (ThreadLocalRandom.current().nextInt(4) + 1);

        } else {
            initialGenes[14] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[15] = (ThreadLocalRandom.current().nextInt(4) + 1);

        } else {
            initialGenes[15] = (1);
        }

        //Dun [ Dun+, primitive saturated, saturated ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[16] = (ThreadLocalRandom.current().nextInt(3) + 1);

        } else {
            initialGenes[16] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[17] = (ThreadLocalRandom.current().nextInt(3) + 1);

        } else {
            initialGenes[17] = (1);
        }

        //Dominant White Spotting [ W1-, W2-, W3-, W4-, W5, W6, W7, W8, W9-, W10-, W11-, W12, W13-, W14-, W15, W16, W17-, W18, W19, W20(Sabino2), W21, W22, W23-, W24-, W25-, W26, W27, Sabino1, wildtype+ ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            if (ThreadLocalRandom.current().nextInt(100) > 99) {
                initialGenes[18] = (ThreadLocalRandom.current().nextInt(29) + 1);
            }else{
                initialGenes[18] = (ThreadLocalRandom.current().nextInt(10) + 19);
            }
            initialGenes[19] = (29);

        } else {
            initialGenes[18] = (29);
            initialGenes[19] = (29);
        }

        //Roan [ wildtype+, Roan ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[20] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[20] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[21] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[21] = (1);
        }

        //Cream [ Wildtype+, Cream, pearl ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[22] = (ThreadLocalRandom.current().nextInt(3) + 1);

        } else {
            initialGenes[22] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[23] = (ThreadLocalRandom.current().nextInt(3) + 1);

        } else {
            initialGenes[23] = (1);
        }

        //Champagne [ wildtype+, Champagne ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[24] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[24] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[25] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[25] = (1);
        }

        //Silver [ wildtype+, Silver ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[26] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[26] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[27] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[27] = (1);
        }

        //Mushroom [ wildtype+, Mushroom ]
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

        //Grey [ wildtype+, Grey ]  this one turns the coat white over a few years
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[30] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[30] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[31] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[31] = (1);
        }

        //White Frame Overo [ wildtype+, white frame overo- ]  this is lethal in double dose
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[32] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[32] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[33] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[33] = (1);
        }

        //Tobiano [ wildtype+, Tobiano ] pinto paint like spots
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[34] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[34] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[35] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[35] = (1);
        }

        //Leopard Spotting [ wildtype+, Leopard] appaloosa
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[36] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[36] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[37] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[37] = (1);
        }

        //Appaloosa Pattern 1 [ wildtype+, Modified ] modifier increases coverage to 6 or 10 incomplete dominant
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[38] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[38] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[39] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[39] = (1);
        }

        //Appaloosa Pattern 2 [ wildtype+, Modified ] modifier increases by approximately 1
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[40] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[40] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[41] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[41] = (1);
        }

        //Appaloosa Pattern 3 [ wildtype+, Modified ] modifier increases by approximately 1
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[42] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[42] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[43] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[43] = (1);
        }


        //Appaloosa Pattern 4 [ wildtype+, Modified ] modifier increases by approximately 1
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[44] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[44] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[45] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[45] = (1);
        }

        //Appaloosa Pattern 5 [ wildtype+, Modified ] modifier increases by approximately 1
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[46] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[46] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[47] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[47] = (1);
        }

        //Appaloosa Varnish Roan [ wildtype+, Varnished ] modifier adds varnishing to appoloosa spots
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[48] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[48] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[49] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[49] = (1);
        }

        //Splash White 1 [ wildtype+, Classic splash white1, splash white3-  ]
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

        //Splash White 2 [ wildtype+, splash white2, splash white4  ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[52] = (ThreadLocalRandom.current().nextInt(4) + 1);

        } else {
            initialGenes[52] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[53] = (ThreadLocalRandom.current().nextInt(4) + 1);

        } else {
            initialGenes[53] = (1);
        }

        //Tiger Eye [ Wildtype+, te1, te2 ] te1 = lightens eyes to yellow/amber/orange, te2 = lightens eyes to blue in cream horses
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[54] = (ThreadLocalRandom.current().nextInt(3) + 1);

        } else {
            initialGenes[54] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[55] = (ThreadLocalRandom.current().nextInt(3) + 1);

        } else {
            initialGenes[55] = (1);
        }

        //Flaxen [ Wildtype+, flaxen ] causes flaxen mane and tail in chestnut horses
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[56] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[56] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[57] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[57] = (1);
        }

        //Flaxen Inhibitor [ darker mane and tail+, Non Inhibitor ] non inhibitor causes slightly lighter mane and tail in chestnut horses
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[58] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[58] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[59] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[59] = (1);
        }

        //Mealy or Pangaré [ Mealy+, non mealy ] near dominant
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[60] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[60] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[61] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[61] = (1);
        }

        //Sooty 1 [ wildtype+, Sooty ] incomplete dominant
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[62] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[62] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[63] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[63] = (1);
        }

        //Sooty 2 [ wildtype+, Sooty2 ] incomplete dominant
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[64] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[64] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[65] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[65] = (1);
        }

        //Sooty 3 [ wildtype+, sooty3 ] recessive
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[66] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[66] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[67] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[67] = (1);
        }

        //Sooty 4 [ wildtype+, sooty4 ] recessive
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[68] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[68] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[69] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[69] = (1);
        }

        //Liver or Dark Chestnut [ wildtype+, dark ] recessive
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[70] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[70] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[71] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[71] = (1);
        }

        return initialGenes;
    }

    protected void configureAI() {
        if (!aiConfigured) {

            this.goalSelector.addGoal(1, new PanicGoal(this, 1.5D));
            this.goalSelector.addGoal(2, new BreedGoal(this, 1.0D));
//            this.goalSelector.addGoal(3, new EnhancedTemptGoal(this, 1.25D, false, TEMPTATION_ITEMS));
            this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.25D));
            this.goalSelector.addGoal(4, new EnhancedAINurseFromMotherGoal(this, motherUUID, 1.25D));
            wanderEatingGoal = new EnhancedWaterAvoidingRandomWalkingEatingGoal(this, 1.0D, 7, 0.001F, 120, 2, 20);
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
