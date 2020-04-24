package mokiyoki.enhancedanimals.entity;

import mokiyoki.enhancedanimals.init.ModItems;
import mokiyoki.enhancedanimals.util.handlers.ConfigHandler;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Blocks;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.stats.Stats;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import static mokiyoki.enhancedanimals.util.handlers.EventRegistry.ENHANCED_BEE;

public class EnhancedBee extends AnimalEntity implements EnhancedAnimal {

    private static final DataParameter<String> SHARED_GENES = EntityDataManager.<String>createKey(EnhancedBee.class, DataSerializers.STRING);
    private static final DataParameter<Float> BEE_SIZE = EntityDataManager.createKey(EnhancedBee.class, DataSerializers.FLOAT);
    private static final DataParameter<String> BEE_STATUS = EntityDataManager.createKey(EnhancedBee.class, DataSerializers.STRING);
    protected static final DataParameter<Boolean> SLEEPING = EntityDataManager.createKey(EnhancedBee.class, DataSerializers.BOOLEAN);
    private static final DataParameter<String> BIRTH_TIME = EntityDataManager.<String>createKey(EnhancedBee.class, DataSerializers.STRING);

    private static final String[] BEE_TEXTURES_BLUE = new String[] {
            "white base.png"
    };
    private static final String[] BEE_TEXTURES_YELLOW = new String[] {
            "yellow area patterns.png"
    };
    private static final String[] BEE_TEXTURES_BLACK = new String[] {
            "solid_black.png","black stripe patterns.png"
    };
    private static final String[] BEE_TEXTURES_BELLY = new String[] {
            "black belly.png", "yellow belly.png", "blue belly.png"
    };
    private static final String[] BEE_TEXTURES_MASK = new String[] {
            "black face patterns.png"
    };
    private static final String[] BEE_TEXTURES_EYES = new String[] {
            "pale_eyes.png", "gray_eyes.png", "dark_eyes.png", "black_eyes.png"
    };
    private static final String[] BEE_TEXTURES_CROWN = new String[] {
            "", "crown_center1.png", "crown_center2.png", "crown_center3.png", "crown_center4.png",
    };

    private static final Ingredient TEMPTATION_ITEMS = Ingredient.fromItems(Items.DANDELION, Items.POPPY, Items.BLUE_ORCHID, Items.ALLIUM, Items.AZURE_BLUET, Items.ORANGE_TULIP, Items.RED_TULIP, Items.PINK_TULIP, Items.WHITE_TULIP, Items.OXEYE_DAISY, Items.CORNFLOWER, Items.LILY_OF_THE_VALLEY, Items.WITHER_ROSE, Items.SUNFLOWER, Items.LILAC, Items.ROSE_BUSH, Items.PEONY, Items.HONEY_BOTTLE, Items.HONEY_BLOCK, Items.SUGAR, Items.MELON_SLICE, Items.GLISTERING_MELON_SLICE, Items.WET_SPONGE);
    private static final Ingredient MILK_ITEMS = Ingredient.fromItems(Items.HONEY_BOTTLE);
    private static final Ingredient BREED_ITEMS = Ingredient.fromItems(Items.DANDELION, Items.POPPY, Items.BLUE_ORCHID, Items.ALLIUM, Items.AZURE_BLUET, Items.ORANGE_TULIP, Items.RED_TULIP, Items.PINK_TULIP, Items.WHITE_TULIP, Items.OXEYE_DAISY, Items.CORNFLOWER, Items.LILY_OF_THE_VALLEY, Items.WITHER_ROSE, Items.SUNFLOWER, Items.LILAC, Items.ROSE_BUSH, Items.PEONY);

    private boolean fertilized = false;
    private boolean female = true;
    private static final int WTC = ConfigHandler.COMMON.wildTypeChance.get();
    private final List<String> beeTextures = new ArrayList<>();
    private final List<String> beeAlphaTextures = new ArrayList<>();
    private static final int GENES_LENGTH = 10;
    private int[] genes = new int[GENES_LENGTH];
    private int[] mateGenes = new int[GENES_LENGTH];
    private int[] gameteGenes = new int[GENES_LENGTH];
    private int[] mateGameteGenes = new int[GENES_LENGTH];

    private int hunger = 0;
    protected int healTicks = 0;
    protected Boolean sleeping = false;
    protected int awokenTimer = 0;

    public EnhancedBee(EntityType<? extends EnhancedBee> entityType, World worldIn) {
        super(entityType, worldIn);
//        this.setBeeSize();
    }

    protected void registerData() {
        super.registerData();
        this.dataManager.register(SHARED_GENES, new String());
        this.dataManager.register(BEE_SIZE, 0.0F);
        this.dataManager.register(BEE_STATUS, new String());
        this.dataManager.register(SLEEPING, false);
        this.dataManager.register(BIRTH_TIME, "0");
    }

    private void setBeeStatus(String status) {
        this.dataManager.set(BEE_STATUS, status);
    }

    public String getBeeStatus() {
        return this.dataManager.get(BEE_STATUS);
    }

    private void setBeeSize(float size) {
        this.dataManager.set(BEE_SIZE, size);
    }

    public float getSize() { return this.dataManager.get(BEE_SIZE); }

    protected void setBirthTime(String birthTime) {
        this.dataManager.set(BIRTH_TIME, birthTime);
    }

    public String getBirthTime() { return this.dataManager.get(BIRTH_TIME); }

    private int getAge() {
        if (!(getBirthTime() == null) && !getBirthTime().equals("") && !getBirthTime().equals(0)) {
            return (int)(this.world.getWorldInfo().getGameTime() - Long.parseLong(getBirthTime()));
        } else {
            return 500000;
        }
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

    public int getHunger(){
        return hunger;
    }

    public void decreaseHunger(int decrease) {
        if (this.hunger - decrease < 0) {
            this.hunger = 0;
        } else {
            this.hunger = this.hunger - decrease;
        }
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

    private void setBeeSize() {
        float size = 1.0F;
        this.setBeeSize(size);
    }

    /**
     * yellow hue and saturation gene. 20+ that give exact hue is averaged to pick final hue.
     * yellow brightness gene. ~10 that are averaged to make final brightness of yellow. this can include white
     * blue colour gene. ~20 alleles that determine hue saturation and brightness of blue. this can include white
     * saturation modifier gene. changes saturation of both yellow and blue
     * recessive black abdomen gene. 2 allels. 1 weak, 1 strong. changes abdomen darker if weak, black if strong. strong is most recessive.
     * stripe number gene. determines how many stripes a bee has
     * stripe thickener gene. can make stripes thicker or thinner
     * chocolate stripe gene. reccessive allele makes stripes a dark brown.
     * cinnamon stripe gene. recessive gene that makes stripes a lighter colour if already brown.
     * black opacity gene. multipule allels that average to make the black pattern more opaque.
     * back stripe gene 1. incomplete dominant gene that only works with the second back stripe gene.
     * back stripe gene 2. incomplete dominant works with the first.
     *
     */

    /**
     *
     */

    protected void createAndSpawnEnhancedChild(World inWorld) {
        EnhancedBee enhancedbee = ENHANCED_BEE.create(this.world);
        enhancedbee.setGrowingAge(0);
        int[] babyGenes = getLarvaGenes(this.gameteGenes, this.mateGenes);
        enhancedbee.setGenes(babyGenes);
        enhancedbee.setSharedGenes(babyGenes);
        enhancedbee.setBeeSize();
        enhancedbee.setGrowingAge(-60000);
        enhancedbee.setBirthTime(String.valueOf(inWorld.getGameTime()));
        enhancedbee.setBeeStatus(EntityState.CHILD_STAGE_ONE.toString());
        enhancedbee.setLocationAndAngles(this.getPosX(), this.getPosY(), this.getPosZ(), this.rotationYaw, 0.0F);
//                        enhancedbee.setMotherUUID(this.getUniqueID().toString());
        this.world.addEntity(enhancedbee);
    }

    public boolean isBreedingItem(ItemStack stack) {
        //TODO set this to a separate item or type of item for force breeding
        return BREED_ITEMS.test(stack);
    }

    public AgeableEntity createChild(AgeableEntity ageable) {
        if(fertilized) {
            ((EnhancedBee)ageable).fertilized = true;
            ((EnhancedBee)ageable).setMateGenes(this.genes);
            ((EnhancedBee)ageable).mixMeiosisGenes();
        } else {
            fertilized = true;
            this.mateGenes = ((EnhancedBee) ageable).getGenes();
            mixMeiosisGenes();
        }

        this.setGrowingAge(10);
        this.resetInLove();
        ageable.setGrowingAge(10);
        ((EnhancedBee)ageable).resetInLove();

        ServerPlayerEntity entityplayermp = this.getLoveCause();
        if (entityplayermp == null && ((EnhancedBee)ageable).getLoveCause() != null) {
            entityplayermp = ((EnhancedBee)ageable).getLoveCause();
        }

        if (entityplayermp != null) {
            entityplayermp.addStat(Stats.ANIMALS_BRED);
            CriteriaTriggers.BRED_ANIMALS.trigger(entityplayermp, this, ((EnhancedBee)ageable), (AgeableEntity)null);
        }

        return null;
    }

    public void mixMeiosisGenes() { meiosis(gameteGenes, genes); }

//    public void mixMateMeiosisGenes() { meiosis(mateGameteGenes, mateGenes); } //not needed since males are haploid

    public void meiosis(int[] parent, int[] gamete) {
        Random rand = new Random();
        for (int i = 0; i < genes.length; i = (i + 2)) {
            boolean mix = rand.nextBoolean();
            if (mix) {
                parent[i] = gamete[i + 1];
                parent[i + 1] = gamete[i];
            } else {
                parent[i] = gamete[i];
                parent[i + 1] = gamete[i + 1];
            }
        }
    }

    public int[] getLarvaGenes(int[] gameteGenes, int[] mateGameteGenes) {
        Random rand = new Random();
        int[] larvaGenes = new int[GENES_LENGTH];
        boolean female = rand.nextBoolean();

        if (female) {
            for (int i = 0; i < genes.length; i = (i + 2)) {
                boolean mix = rand.nextBoolean();
                if (mix) {
                    larvaGenes[i] = gameteGenes[i];
                    larvaGenes[i+1] = mateGameteGenes[i];
                } else {
                    larvaGenes[i] = mateGameteGenes[i];
                    larvaGenes[i+1] = gameteGenes[i+1];
                }
            }
        } else {
            for (int i = 0; i < genes.length; i = (i + 2)) {
                boolean mix = rand.nextBoolean();
                if (mix) {
                    larvaGenes[i] = gameteGenes[i];
                    larvaGenes[i+1] = gameteGenes[i];
                } else {
                    larvaGenes[i] = gameteGenes[i+1];
                    larvaGenes[i+1] = gameteGenes[i+1];
                }
            }
        }

        return larvaGenes;
    }

//    public int[] getBreedGenes () {
//
//        return
//    }

    //                this.beeTextures.add(BEE_TEXTURES_BLUE[blue]);
//                this.beeTextures.add(BEE_TEXTURES_YELLOW[yellow]);
//                this.beeTextures.add(BEE_TEXTURES_CROWN[crowncenter1]);
//                this.beeTextures.add(BEE_TEXTURES_CROWN[crowncenter2]);
//                this.beeTextures.add(BEE_TEXTURES_CROWN[crownouter3]);

    @Nullable
    @Override
    public ILivingEntityData onInitialSpawn(IWorld inWorld, DifficultyInstance difficulty, SpawnReason spawnReason, @Nullable ILivingEntityData livingdata, @Nullable CompoundNBT itemNbt) {
//        livingdata = super.onInitialSpawn(inWorld, difficulty, spawnReason, livingdata, itemNbt);
        int[] spawnGenes;

//        if (livingdata instanceof GroupData) {
//            int[] spawnGenes1 = ((GroupData) livingdata).groupGenes;
//            int[] mitosis = new int[GENES_LENGTH];
//             meiosis(mitosis, spawnGenes1);
//
//            int[] spawnGenes2 = ((GroupData) livingdata).groupGenes;
//            int[] mateMitosis = new int[GENES_LENGTH];
//             meiosis(mateMitosis, spawnGenes2);
//        } else {
            spawnGenes = createInitialGenes(inWorld);
            livingdata = new GroupData(spawnGenes);
//        }

        this.genes = spawnGenes;
        this.setSharedGenes(genes);
        this.setBeeSize();

        int birthMod = ThreadLocalRandom.current().nextInt(30000, 80000);
        this.setBirthTime(String.valueOf(inWorld.getWorld().getGameTime() - birthMod));
        if (birthMod < 60000) {
            this.setGrowingAge(birthMod - 60000);
        }

        return livingdata;
    }

    private int[] createInitialGenes(IWorld inWorld) {
        int[] initialGenes = new int[GENES_LENGTH];



        return initialGenes;
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
