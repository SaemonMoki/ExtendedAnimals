package mokiyoki.enhancedanimals.entity;

import mokiyoki.enhancedanimals.items.DebugGenesBook;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.AirItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.stats.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public abstract class EnhancedAnimalAbstract extends AnimalEntity implements EnhancedAnimal {

    protected static final DataParameter<String> SHARED_GENES = EntityDataManager.<String>createKey(EnhancedAnimalAbstract.class, DataSerializers.STRING);
    protected static final DataParameter<Boolean> SLEEPING = EntityDataManager.createKey(EnhancedAnimalAbstract.class, DataSerializers.BOOLEAN);
    protected static final DataParameter<String> BIRTH_TIME = EntityDataManager.<String>createKey(EnhancedAnimalAbstract.class, DataSerializers.STRING);

    private static Ingredient TEMPTATION_ITEMS;
    private static Ingredient BREED_ITEMS;

    // Genetic Info
    protected int[] genes;
    protected int[] mateGenes;
    protected int[] mitosisGenes;
    protected int[] mateMitosisGenes;

    //Hunger
    Map<Item, Integer> foodWeightMap = new HashMap();
    protected float hunger = 0F;
    protected int healTicks = 0;

    //Sleeping
    protected Boolean sleeping = false;
    protected int awokenTimer = 0;

    //Pregnancy
    protected int gestationTimer = 0;
    protected boolean pregnant = false;

    protected EnhancedAnimalAbstract(EntityType<? extends EnhancedAnimalAbstract> type, World worldIn, int genesSize, Ingredient temptationItems, Ingredient breedItems, Map<Item, Integer> foodWeightMap) {
        super(type, worldIn);
        this.genes = new int[genesSize];
        this.TEMPTATION_ITEMS = temptationItems;
        this.BREED_ITEMS = breedItems;
        this.foodWeightMap = foodWeightMap;
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(SHARED_GENES, new String());
        this.dataManager.register(SLEEPING, false);
        this.dataManager.register(BIRTH_TIME, "0");
    }

    public void setBirthTime(String birthTime) {
        this.dataManager.set(BIRTH_TIME, birthTime);
    }

    public String getBirthTime() {
        return this.dataManager.get(BIRTH_TIME);
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

    protected int getAge() {
        if (!(getBirthTime() == null) && !getBirthTime().equals("") && !getBirthTime().equals(0)) {
            return (int)(this.world.getWorldInfo().getGameTime() - Long.parseLong(getBirthTime()));
        } else {
            return 500000;
        }
    }

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
            else if (TEMPTATION_ITEMS.test(itemStack) && hunger >= 6000) {
                if (this.foodWeightMap.containsKey(item)) {
                    decreaseHunger(this.foodWeightMap.get(item));
                } else {
                    decreaseHunger(6000);
                }
                if (!entityPlayer.abilities.isCreativeMode) {
                    itemStack.shrink(1);
                }
            }
        }

        return super.processInteract(entityPlayer, hand);
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

    public void setMateGenes(int[] mateGenes){
        this.mateGenes = mateGenes;
    }

    public void mixMateMitosisGenes() {
        punnetSquare(mateMitosisGenes, mateGenes);
    }

    public void mixMitosisGenes() {
        punnetSquare(mitosisGenes, genes);
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


}
