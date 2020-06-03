package mokiyoki.enhancedanimals.entity;

import mokiyoki.enhancedanimals.config.EanimodCommonConfig;
import mokiyoki.enhancedanimals.init.ModItems;
import mokiyoki.enhancedanimals.items.DebugGenesBook;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
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
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public abstract class EnhancedAnimalAbstract extends AnimalEntity implements EnhancedAnimal {

    protected static final DataParameter<String> SHARED_GENES = EntityDataManager.<String>createKey(EnhancedAnimalAbstract.class, DataSerializers.STRING);
    protected static final DataParameter<Boolean> SLEEPING = EntityDataManager.createKey(EnhancedAnimalAbstract.class, DataSerializers.BOOLEAN);
    protected static final DataParameter<String> BIRTH_TIME = EntityDataManager.<String>createKey(EnhancedAnimalAbstract.class, DataSerializers.STRING);
    private static final DataParameter<Float> ANIMAL_SIZE = EntityDataManager.createKey(EnhancedAnimalAbstract.class, DataSerializers.FLOAT);
    private static final DataParameter<String> ENTITY_STATUS = EntityDataManager.createKey(EnhancedAnimalAbstract.class, DataSerializers.STRING);

    private static Ingredient TEMPTATION_ITEMS;
    private static Ingredient BREED_ITEMS;
    private static final Ingredient MILK_ITEMS = Ingredient.fromItems(ModItems.Milk_Bottle, ModItems.Half_Milk_Bottle);

    // Genetic Info
    protected int[] genes;
    protected int[] mateGenes;
    protected int[] mitosisGenes;
    protected int[] mateMitosisGenes;
    protected static final int WTC = EanimodCommonConfig.COMMON.wildTypeChance.get();

    //Hunger
    Map<Item, Integer> foodWeightMap = new HashMap();
    protected float hunger = 0F;
    protected int healTicks = 0;
    protected boolean bottleFeedable = false;
    protected int animalEatingTimer;

    //Sleeping
    protected Boolean sleeping = false;
    protected int awokenTimer = 0;

    //Pregnancy
    protected int gestationTimer = 0;
    protected boolean pregnant = false;

    //Texture
    protected final List<String> enhancedAnimalTextures = new ArrayList<>();
    protected final List<String> enhancedAnimalAlphaTextures = new ArrayList<>();

    /*
    Entity Construction
    */

    protected EnhancedAnimalAbstract(EntityType<? extends EnhancedAnimalAbstract> type, World worldIn, int genesSize, Ingredient temptationItems, Ingredient breedItems, Map<Item, Integer> foodWeightMap, boolean bottleFeedable) {
        super(type, worldIn);
        this.genes = new int[genesSize];
        this.mateGenes = new int[genesSize];
        this.mitosisGenes = new int[genesSize];
        this.mateMitosisGenes = new int[genesSize];
        this.TEMPTATION_ITEMS = temptationItems;
        this.BREED_ITEMS = breedItems;
        this.foodWeightMap = foodWeightMap;
        this.bottleFeedable = bottleFeedable;
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(SHARED_GENES, new String());
        this.dataManager.register(SLEEPING, false);
        this.dataManager.register(ENTITY_STATUS, new String());
        this.dataManager.register(BIRTH_TIME, "0");
        this.dataManager.register(ANIMAL_SIZE, 0.0F);
    }

    /*
    Animal expected abstracts and overrides
    */

    //returns the config for the animals gestation or any overrides
    protected abstract int gestationConfig();

    //the amount hunger is incremented
    protected abstract void incrementHunger();

    //anything extra to run during not idling
    protected abstract void runExtraIdleTimeTick();

    //any lethal gene checks the animal has
    protected abstract void lethalGenes();

    //all pregnancy work during living tick goes here
    protected abstract void runPregnancyTick();

    //when the animal wakes up
    protected boolean sleepingConditional() {
        return (!this.world.isDaytime() && awokenTimer == 0 && !sleeping);
    }

    //for setting the textures
    @OnlyIn(Dist.CLIENT)
    protected abstract void setTexturePaths();

    //for setting the alpha textures
    @OnlyIn(Dist.CLIENT)
    protected abstract void setAlphaTexturePaths();

    //called during construction to set up the animal size
    protected abstract void initilizeAnimalSize();


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

    protected int getAge() {
        if (!(getBirthTime() == null) && !getBirthTime().equals("") && !getBirthTime().equals(0)) {
            return (int)(this.world.getWorldInfo().getGameTime() - Long.parseLong(getBirthTime()));
        } else {
            return 500000;
        }
    }

    public void setGenes(int[] genes) {
        this.genes = genes;
    }

    public int[] getGenes(){
        return this.genes;
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

        if (!this.world.isRemote && !hand.equals(Hand.OFF_HAND)) {
            if (item instanceof AirItem) {
                ITextComponent message = getHungerText();
                entityPlayer.sendMessage(message);

            } else if (item instanceof DebugGenesBook) {
                Minecraft.getInstance().keyboardListener.setClipboardString(this.dataManager.get(SHARED_GENES));
            }
            else if (!this.isChild() && TEMPTATION_ITEMS.test(itemStack) && hunger >= 6000) {
                if (this.foodWeightMap.containsKey(item)) {
                    decreaseHunger(this.foodWeightMap.get(item));
                } else {
                    decreaseHunger(6000);
                }
                if (!entityPlayer.abilities.isCreativeMode) {
                    itemStack.shrink(1);
                }
            }  else if (this.isChild() && MILK_ITEMS.test(itemStack) && bottleFeedable && hunger >= 6000) {
                if (item == ModItems.Half_Milk_Bottle) {
                    decreaseHunger(6000);
                    if (!entityPlayer.abilities.isCreativeMode) {
                        if (itemStack.isEmpty()) {
                            entityPlayer.setHeldItem(hand, new ItemStack(Items.GLASS_BOTTLE));
                        } else if (!entityPlayer.inventory.addItemStackToInventory(new ItemStack(Items.GLASS_BOTTLE))) {
                            entityPlayer.dropItem(new ItemStack(Items.GLASS_BOTTLE), false);
                        }
                    }
                } else if (item == ModItems.Milk_Bottle) {
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

    /*
    NBT read/write
    */
    @Override
    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);

        //store this animal's genes
        ListNBT geneList = new ListNBT();
        for(int i = 0; i< genes.length; i++){
            CompoundNBT nbttagcompound = new CompoundNBT();
            nbttagcompound.putInt("Gene", genes[i]);
            geneList.add(nbttagcompound);
        }
        compound.put("Genes", geneList);

        //store this animal's mate's genes
        ListNBT mateGeneList = new ListNBT();
        for(int i = 0; i< mateGenes.length; i++){
            CompoundNBT nbttagcompound = new CompoundNBT();
            nbttagcompound.putInt("Gene", mateGenes[i]);
            mateGeneList.add(nbttagcompound);
        }
        compound.put("FatherGenes", mateGeneList);

        compound.putFloat("Hunger", hunger);

        compound.putString("Status", getEntityStatus());

        compound.putString("BirthTime", this.getBirthTime());
    }

    @Override
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

        hunger = compound.getFloat("Hunger");

        setEntityStatus(compound.getString("Status"));

        this.setBirthTime(compound.getString("BirthTime"));

        geneFixer();

        setSharedGenes(genes);
        initilizeAnimalSize();
    }

    /*
    Entity Creation
    */

    protected void defaultCreateAndSpawn(EnhancedAnimalAbstract enhancedAnimalChild, World inWorld, int[] babyGenes, int childAge) {
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
        if(pregnant) {
            ((EnhancedAnimalAbstract)ageable).pregnant = true;
            ((EnhancedAnimalAbstract)ageable).setMateGenes(this.genes);
            ((EnhancedAnimalAbstract)ageable).mixMateMitosisGenes();
            ((EnhancedAnimalAbstract)ageable).mixMitosisGenes();
        } else {
            pregnant = true;
            this.mateGenes = ((EnhancedCow) ageable).getGenes();
            mixMateMitosisGenes();
            mixMitosisGenes();
        }
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

    protected ITextComponent getHungerText() {
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

    protected ITextComponent getPregnantText() {
        String pregnancyText;
        int days = gestationConfig();
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

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        //TODO set this to a separate item or type of item for force breeding
        return BREED_ITEMS.test(stack);
    }

    public void setSharedGenes(int[] genes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < genes.length; i++){
            sb.append(genes[i]);
            if (i != genes.length -1){
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

    @OnlyIn(Dist.CLIENT)
    public String[] getVariantTexturePaths() {
        if (this.enhancedAnimalTextures.isEmpty()) {
            this.setTexturePaths();
        }

        return this.enhancedAnimalTextures.stream().toArray(String[]::new);
    }

    @OnlyIn(Dist.CLIENT)
    public String[] getVariantAlphaTexturePaths()
    {
        if (this.enhancedAnimalAlphaTextures.isEmpty()) {
            this.setAlphaTexturePaths();
        }

        //todo this is only temporarity until we have alpha textures
        if (this.enhancedAnimalAlphaTextures.isEmpty()) {
            return null;
        }

        return this.enhancedAnimalAlphaTextures.stream().toArray(String[]::new);
    }

    protected void geneFixer() {
        for (int i = 0; i < genes.length; i++) {
            if (genes[i] == 0) {
                genes[i] = 1;
            }
        }
        if (mateGenes[0] != 0) {
            for (int i = 0; i < mateGenes.length; i++) {
                if (mateGenes[i] == 0) {
                    mateGenes[i] = 1;
                }
            }
        }
    }

    public void setMateGenes(int[] mateGenes){
        this.mateGenes = mateGenes;
    }

    public void mixMateMitosisGenes() {
        punnetSquare(0, mateMitosisGenes, mateGenes);
    }

    public void mixMitosisGenes() {
        punnetSquare(0, mitosisGenes, genes);
    }

    public void punnetSquare(int startIndex, int[] mitosis, int[] parentGenes) {
        mixGenes(startIndex, mitosis, parentGenes, 2);

        extraMixingOverrides(mitosis, parentGenes);
    }

    protected void mixGenes(int startIndex, int[] mitosis, int[] parentGenes, int increaseAmount) {
        for (int i = startIndex; i < genes.length; i = (i + increaseAmount)) {
            boolean mateOddOrEven = this.rand.nextBoolean();
            if (mateOddOrEven) {
                mitosis[i] = parentGenes[i + 1];
                mitosis[i + 1] = parentGenes[i];
            } else {
                mitosis[i] = parentGenes[i];
                mitosis[i + 1] = parentGenes[i + 1];
            }
        }
    }

    protected void extraMixingOverrides(int[] mitosis, int[] parentGenes){}

    protected ILivingEntityData commonInitialSpawnSetup(IWorld inWorld, @Nullable ILivingEntityData livingdata, int geneStartIndex, int geneLength, int childAge, int ageMinimum, int ageMaximum) {
        int[] spawnGenes;

        if (livingdata instanceof GroupData) {
            int[] spawnGenes1 = ((GroupData) livingdata).groupGenes;
            int[] mitosis = new int[geneLength];
            punnetSquare(geneStartIndex, mitosis, spawnGenes1);

            int[] spawnGenes2 = ((GroupData) livingdata).groupGenes;
            int[] mateMitosis = new int[geneLength];
            punnetSquare(geneStartIndex, mateMitosis, spawnGenes2);
            spawnGenes = createInitialSpawnChildGenes(spawnGenes1, spawnGenes2, mitosis, mateMitosis);
        } else {
            spawnGenes = createInitialGenes(inWorld);
            livingdata = new GroupData(spawnGenes);
        }

        this.genes = spawnGenes;
        setSharedGenes(genes);
        initilizeAnimalSize();
        setInitialDefaults();

        int birthMod = ThreadLocalRandom.current().nextInt(ageMinimum, ageMaximum);
        this.setBirthTime(String.valueOf(inWorld.getWorld().getGameTime() - birthMod));
        if (birthMod < childAge) {
            this.setGrowingAge(birthMod - childAge);
        }

        return livingdata;
    }

    protected abstract int[] createInitialSpawnChildGenes(int[] spawnGenes1, int[] spawnGenes2, int[] mitosis, int[] mateMitosis);

    protected abstract int[] createInitialGenes(IWorld inWorld);

    protected abstract void setInitialDefaults();

    public static class GroupData implements ILivingEntityData {
        public int[] groupGenes;

        public GroupData(int[] groupGenes) {
            this.groupGenes = groupGenes;
        }
    }


}
