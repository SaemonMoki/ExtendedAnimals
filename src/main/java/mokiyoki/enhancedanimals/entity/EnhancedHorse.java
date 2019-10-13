package mokiyoki.enhancedanimals.entity;

import mokiyoki.enhancedanimals.ai.general.EnhancedLookAtGoal;
import mokiyoki.enhancedanimals.ai.general.EnhancedLookRandomlyGoal;
import mokiyoki.enhancedanimals.ai.general.EnhancedWaterAvoidingRandomWalkingEatingGoal;
import mokiyoki.enhancedanimals.ai.general.cow.EnhancedAINurseFromMotherGoal;
import mokiyoki.enhancedanimals.init.ModBlocks;
import mokiyoki.enhancedanimals.init.ModItems;
import mokiyoki.enhancedanimals.items.DebugGenesBook;
import mokiyoki.enhancedanimals.util.handlers.ConfigHandler;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.BreedGoal;
import net.minecraft.entity.ai.goal.FollowParentGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.PanicGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.TemptGoal;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.horse.AbstractChestedHorseEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
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
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
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

import static mokiyoki.enhancedanimals.util.handlers.EventRegistry.ENHANCED_HORSE;

public class EnhancedHorse extends AbstractChestedHorseEntity implements EnhancedAnimal {
    private static final DataParameter<String> SHARED_GENES = EntityDataManager.<String>createKey(EnhancedHorse.class, DataSerializers.STRING);
    private static final DataParameter<Float> HORSE_SIZE = EntityDataManager.createKey(EnhancedHorse.class, DataSerializers.FLOAT);
    protected static final DataParameter<String> HORSE_STATUS = EntityDataManager.createKey(EnhancedHorse.class, DataSerializers.STRING);
    protected static final DataParameter<Boolean> SLEEPING = EntityDataManager.createKey(EnhancedCow.class, DataSerializers.BOOLEAN);

    //TODO add texture layers
    private static final String[] HORSE_TEXTURES_BASE = new String[] {
            "solid_white.png"
    };
    private static final String[] HORSE_TEXTURES_RED = new String[] {
            "", "solid_red.png"
    };
    private static final String[] HORSE_TEXTURES_BLACK = new String[] {
            "", "solid_black.png"
    };
    private static final String[] HORSE_TEXTURES_SPOT_TOBIANO = new String[] {
            "", "solid_black.png"
    };
    private static final String[] HORSE_TEXTURES_SPOT_DOMINANTWHITE1 = new String[] {
            "", "solid_black.png"
    };
    private static final String[] HORSE_TEXTURES_SPOT_DOMINANTWHITE2 = new String[] {
            "", "solid_black.png"
    };

    private final List<String> horseTextures = new ArrayList<>();

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

    private static final int WTC = ConfigHandler.COMMON.wildTypeChance.get();
    private static final int GENES_LENGTH = 56;
    private int[] genes = new int[GENES_LENGTH];
    private int[] mateGenes = new int[GENES_LENGTH];
    private int[] mitosisGenes = new int[GENES_LENGTH];
    private int[] mateMitosisGenes = new int[GENES_LENGTH];

    private float horseSize;
    protected int hunger = 0;
    protected int horseTimer;
    protected boolean aiConfigured = false;
    protected String motherUUID = "";
    protected EnhancedWaterAvoidingRandomWalkingEatingGoal wanderEatingGoal;
    protected int gestationTimer = 0;
    protected boolean pregnant = false;

    protected Boolean sleeping;
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

    public int getHunger(){ return hunger; }

    public void decreaseHunger(int decrease) {
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
            float f2 = 0.3F;
            passenger.setPosition(this.posX + (double)(0.3F * f1), this.posY + this.getMountedYOffset() + passenger.getYOffset(), this.posZ - (double)(0.3F * f));
        }
    }

    /**
     * Returns the Y offset from the entity's position for any entity riding this one.
     */
    public double getMountedYOffset() {
        return (double)this.getHeight() * 0.67D;
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

    protected void registerAttributes() {
        super.registerAttributes();
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(8.0D);
        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.23000000417232513D);
    }

    public void livingTick() {
        super.livingTick();

        if (this.world.isRemote) {
            this.horseTimer = Math.max(0, this.horseTimer - 1);
        } else {

//            if (!this.world.isDaytime() && awokenTimer == 0 && (sleeping == null || !sleeping)) {
//                setSleeping(true);
//            } else if (awokenTimer > 0) {
//                awokenTimer--;
//            }

            if(pregnant) {
                gestationTimer++;
                int days = ConfigHandler.COMMON.gestationDaysHorse.get();
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

                    createAndSpawnEnhancedChild();

                }
            }
            if (this.getIdleTime() < 100) {
                if (hunger <= 72000) {

                    hunger = hunger + 2;

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
            lethalGenes();
        }
    }

    protected void createAndSpawnEnhancedChild() {
        EnhancedHorse enhancedhorse = ENHANCED_HORSE.create(this.world);
        int[] babyGenes = getFoalGenes(this.mitosisGenes, this.mateMitosisGenes);
        enhancedhorse.setGenes(babyGenes);
        enhancedhorse.setSharedGenes(babyGenes);
        enhancedhorse.setHorseSize();
        enhancedhorse.setGrowingAge(-84000);
        enhancedhorse.setHorseStatus(EntityState.CHILD_STAGE_ONE.toString());
        enhancedhorse.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0F);
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
        } else if (genes[32] == 1 && genes[33] == 1) {
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

    public AgeableEntity createChild(AgeableEntity ageable) {
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



            this.horseTextures.add(HORSE_TEXTURES_BASE[0]);
        }
    }

//
//    @OnlyIn(Dist.CLIENT)
//    public float[] getRgb() {
//        if (horseColouration == null) {
//            horseColouration = new float[6];
//            int[] genesForText = getSharedGenes();
//
//            float blackR = 15.0F;
//            float blackG = 7.0F;
//            float blackB = 7.0F;
//
//            float redR = 134.0F;
//            float redG = 79.0F;
//            float redB = 41.0F;
//
//            int tint;
//
//            if ((genesForText[6] == 1 || genesForText[7] == 1) || (genesForText[0] == 3 && genesForText[1] == 3)){
//                //red
//                tint = 4;
//            }else {
//                if (genesForText[0] == 1 || genesForText[1] == 1) {
//                    //black
//                    tint = 2;
//                } else {
//                    //wildtype
//                    tint = 3;
//                }
//            }
//
//            //standard dilution
//            if (genesForText[2] == 2 || genesForText[3] == 2) {
////            if (true) {
//                if (genesForText[2] == 2 && genesForText[3] == 2) {
////                if (false) {
//
//                    blackR = (blackR + (255F * tint)) / (tint+1);
//                    blackG = (blackG + (245F * tint)) / (tint+1);
//                    blackB = (blackB + (235F * tint)) / (tint+1);
//
//                    if (tint != 2) {
//                        redR = (redR + (255F * tint)) / (tint + 1);
//                        redG = (redG + (255F * tint)) / (tint + 1);
//                        redB = (redB + (255F * tint)) / (tint + 1);
//                    }
//                }else{
//                    if (tint == 3) {
//                        //wildtype
//                        redR = 160.5F;
//                        redG = 119.0F;
//                        redB = 67.0F;
//
//                        if (genesForText[4] == 1 || genesForText[5] == 1) {
//                            if (genesForText[4] == 1 && genesForText[5] == 1) {
//                                blackR = 81.0F;
//                                blackG = 71.0F;
//                                blackB = 65.0F;
//                            } else {
//                                blackR = 40.0F;
//                                blackG = 35.0F;
//                                blackB = 32.0F;
//                            }
//                        } else if (genesForText[4] == 4 && genesForText[5] == 4) {
//                            blackR = 81.0F;
//                            blackG = 71.0F;
//                            blackB = 65.0F;
//                        }
//
//                    } else if (tint == 4){
//                        //red
//                        redR = (redR*0.5F) + (187.0F*0.5F);
//                        redG = (redG*0.5F) + (180.0F*0.5F);
//                        redB = (redB*0.5F) + (166.0F*0.5F);
//                    }else {
//                        //black
//                        blackR = 81.0F;
//                        blackG = 71.0F;
//                        blackB = 65.0F;
//                    }
//                }
//            }
//
//            if (genesForText[4] == 3 || genesForText[5] == 3) {
//                redR = (redR + 245F) / 2;
//                redG = (redG + 237F) / 2;
//                redB = (redB + 222F) / 2;
//            }
//
//            //chocolate
//            if (genesForText[10] == 2 && genesForText[11] == 2){
//                blackR = blackR + 25F;
//                blackG = blackG + 15F;
//                blackB = blackB + 9F;
//
//                redR = redR + 25F;
//                redG = redG + 15F;
//                redB = redB + 9F;
//            }
//
//            if (this.isChild()) {
//                if (getHorseStatus().equals(EntityState.CHILD_STAGE_ONE.toString())) {
//                    blackR = redR;
//                    blackG = redG;
//                    blackB = redB;
//                }else if (getHorseStatus().equals(EntityState.CHILD_STAGE_TWO.toString())) {
//                    blackR = (blackR + redR)/2F;
//                    blackG = (blackG + redG)/2F;
//                    blackB = (blackB + redB)/2F;
//                } else {
//                    blackR = blackR*0.75F + redR*0.25F;
//                    blackG = blackG*0.75F + redG*0.25F;
//                    blackB = blackB*0.75F + redB*0.25F;
//                }
//            }
//
//            //TODO TEMP AF
//            //black
//            horseColouration[0] = blackR;
//            horseColouration[1] = blackG;
//            horseColouration[2] = blackB;
//
//            //red
//            horseColouration[3] = redR;
//            horseColouration[4] = redG;
//            horseColouration[5] = redB;
//
//            for (int i = 0; i <= 5; i++) {
//                if (horseColouration[i] > 255.0F) {
//                    horseColouration[i] = 255.0F;
//                }
//                horseColouration[i] = horseColouration[i] / 255.0F;
//            }
//
//        }
//        return horseColouration;
//    }

    @Override
    public boolean processInteract(PlayerEntity entityPlayer, Hand hand) {
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
                }

            }
        }
        return super.processInteract(entityPlayer, hand);
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
        compound.putInt("Hunger", hunger);

        compound.putString("MotherUUID", this.motherUUID);

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
        hunger = compound.getInt("Hunger");

        this.motherUUID = compound.getString("MotherUUID");

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
    public ILivingEntityData onInitialSpawn(IWorld inWorld, DifficultyInstance difficulty, SpawnReason spawnReason, @Nullable ILivingEntityData livingdata, @Nullable CompoundNBT itemNbt) {
        livingdata = super.onInitialSpawn(inWorld, difficulty, spawnReason, livingdata, itemNbt);
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
        return livingdata;
    }

    private int[] createInitialGenes(IWorld inWorld) {
        int[] initialGenes = new int[GENES_LENGTH];

        //Health Base gene [ weaker, stronger1, wildtype+, stronger2 ]
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

        //Health Modifier gene [ weaker, stronger1, wildtype+, stronger2 ]
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

        //Speed Base gene [ weaker, stronger1, wildtype+, stronger2 ]
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

        //Speed Modifier gene [ weaker, stronger1, wildtype+, stronger2 ]
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

        //Jump Base gene [ weaker, stronger1, wildtype+, stronger2 ]
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

        //Jump Modifier gene [ weaker, stronger1, wildtype+, stronger2 ]
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

        //Extension [ wildtype+, Black ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[12] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[12] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[13] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[13] = (1);
        }

        //Agouti [ Wildtype/light bay+, bay, brown/tan, solid/black ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[14] = (ThreadLocalRandom.current().nextInt(3) + 1);

        } else {
            initialGenes[14] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[15] = (ThreadLocalRandom.current().nextInt(3) + 1);

        } else {
            initialGenes[15] = (1);
        }

        //Dun [ Dun+, saturated dun, saturated coat ]
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

        //Mushroom [ wildtype+, Champagne ]
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

        //Pattern 1 [ wildtype+, Modified ] modifier increases coverage to 6 or 10 incomplete dominant
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

        //Pattern 2 [ wildtype+, Modified ] modifier increases by approximately 1
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

        //Pattern 3 [ wildtype+, Modified ] modifier increases by approximately 1
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


        //Pattern 4 [ wildtype+, Modified ] modifier increases by approximately 1
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

        //Pattern 5 [ wildtype+, Modified ] modifier increases by approximately 1
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

        //Varnish Roan [ wildtype+, Varnished ] modifier adds varnishing to appoloosa spots
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

        //Splash White [ wildtype+, Classic splash white1, splash white2, splash white3-  ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[50] = (ThreadLocalRandom.current().nextInt(4) + 1);

        } else {
            initialGenes[50] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[51] = (ThreadLocalRandom.current().nextInt(4) + 1);

        } else {
            initialGenes[51] = (1);
        }

        //Tiger Eye [ Wildtype+, te1, te2 ] te1 = lightens eyes to yellow/amber/orange, te2 = lightens eyes to blue in cream horses
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[52] = (ThreadLocalRandom.current().nextInt(3) + 1);

        } else {
            initialGenes[52] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[53] = (ThreadLocalRandom.current().nextInt(3) + 1);

        } else {
            initialGenes[53] = (1);
        }




        return initialGenes;
    }

    protected void configureAI() {
        if (!aiConfigured) {

            this.goalSelector.addGoal(1, new PanicGoal(this, 1.5D));
            this.goalSelector.addGoal(2, new BreedGoal(this, 1.0D));
            this.goalSelector.addGoal(3, new TemptGoal(this, 1.25D, TEMPTATION_ITEMS, false));
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
