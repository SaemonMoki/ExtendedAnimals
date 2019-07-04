package mokiyoki.enhancedanimals.entity;

import mokiyoki.enhancedanimals.util.handlers.ConfigHandler;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.stats.StatList;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import static mokiyoki.enhancedanimals.util.handlers.RegistryHandler.ENHANCED_PIG;

public class EnhancedPig  extends EntityAnimal {
    private static final DataParameter<String> SHARED_GENES = EntityDataManager.<String>createKey(EnhancedPig.class, DataSerializers.STRING);
    private static final DataParameter<Float> PIG_SIZE = EntityDataManager.createKey(EnhancedPig.class, DataSerializers.FLOAT);

    private static final String[] PIG_TEXTURES_COATRED = new String[] {
            "pigbase.png", "solid_white.png", "solid_milk.png", "solid_cream.png", "solid_carmel.png", "solid_orange.png", "solid_ginger.png", "solid_red.png", "solid_brown.png"
    };
    private static final String[] PIG_TEXTURES_COATBLACK = new String[] {
            "red", "black_solid.png", "black_wildtype.png", "black_brindle.png", "black_brindlesmall.png",
                   "choc_solid.png", "choc_wildtype.png", "choc_brindle.png", "choc_brindlesmall.png",
                   "lightchoc_wildtype.png"
    };

    private static final String[] PIG_TEXTURES_SPOT_SPOTS = new String[] {
            "", "spot_spots.png", "spot_roanspots.png"
    };

    private static final String[] PIG_TEXTURES_SPOT_BELTED = new String[] {
            "", "spot_white.png", "spot_belt.png"
    };

    private static final String[] PIG_TEXTURES_SPOT_BERKSHIRE = new String[] {
            "", "spot_tux.png", "spot_berkshire.png"
    };

    private static final String[] PIG_TEXTURES_COAT = new String[] {
            "coat_normal.png", "coat_wooly.png"
    };

    private static final String[] PIG_TEXTURES_SKINBASE = new String[] {
            "", "skin_pink_bald.png", "skin_grey_bald.png", "skin_black_bald.png",
                "skin_pink_sparse.png", "skin_grey_sparse.png", "skin_black_sparse.png",
                "skin_pink_medium.png", "skin_grey_medium.png", "skin_black_medium.png",
                "skin_pink_furry.png", "skin_grey_furry.png", "skin_black_furry.png",
                "skin_pink_wooly.png", "skin_grey_wooly.png", "skin_black_wooly.png"
    };

    private static final String[] PIG_TEXTURES_SKINMARKINGS = new String[] {
            "", "spots"
    };

    private static final String[] PIG_TEXTURES_EYES = new String[] {
            "eyes_black.png", "eyes_brown.png", "eyes_blue.png"
    };

    private static final String[] PIG_TEXTURES_HOOVES = new String[] {
            "hooves_black.png", "hooves_brown.png", "hooves_pink.png"
    };

    private static final String[] PIG_TEXTURES_TUSKS = new String[] {
            "", "tusks.png"
    };

    private static final Ingredient TEMPTATION_ITEMS = Ingredient.fromItems(Blocks.MELON, Blocks.PUMPKIN, Blocks.GRASS, Blocks.HAY_BLOCK, Items.CARROT, Items.WHEAT, Items.BEETROOT, Items.ROTTEN_FLESH, Items.APPLE, Items.COOKED_CHICKEN, Items.COOKED_BEEF, Items.COOKED_MUTTON, Items.COOKED_RABBIT, Items.COOKED_SALMON, Items.COOKED_COD, Blocks.BROWN_MUSHROOM, Blocks.DARK_OAK_SAPLING, Blocks.OAK_SAPLING, Items.MILK_BUCKET, Items.BREAD);

    private static final int WTC = 90;
    private final List<String> pigTextures = new ArrayList<>();
    private static final int GENES_LENGTH = 44;
    private int[] genes = new int[GENES_LENGTH];
    private int[] mateGenes = new int[GENES_LENGTH];
    private int[] mitosisGenes = new int[GENES_LENGTH];
    private int[] mateMitosisGenes = new int[GENES_LENGTH];

    private float pigSize;

    public EnhancedPig(World worldIn) {
        super(ENHANCED_PIG, worldIn);
        //TODO why doesnt this change from pig to pig
        this.setPigSize();
        this.setSize(0.9F, pigSize*0.9F);
    }

    private int pigTimer;
    private EntityAIEatGrass entityAIEatGrass;

    private int gestationTimer = 0;
    private boolean pregnant = false;

    protected void initEntityAI() {
        this.entityAIEatGrass = new EntityAIEatGrass(this);
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIPanic(this, 1.25D));
        this.tasks.addTask(3, new EntityAIMate(this, 1.0D));
        this.tasks.addTask(4, new EntityAITempt(this, 1.2D, Ingredient.fromItems(Items.CARROT_ON_A_STICK), false));
        this.tasks.addTask(4, new EntityAITempt(this, 1.2D, false, TEMPTATION_ITEMS));
        this.tasks.addTask(5, new EntityAIFollowParent(this, 1.1D));
        this.tasks.addTask(5, this.entityAIEatGrass);
        this.tasks.addTask(6, new EntityAIWanderAvoidWater(this, 1.0D));
        this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
    }

    protected void updateAITasks()
    {
        this.pigTimer = this.entityAIEatGrass.getEatingGrassTimer();
        super.updateAITasks();
    }

    protected void registerData() {
        super.registerData();
        this.dataManager.register(SHARED_GENES, new String());
        this.dataManager.register(PIG_SIZE, 0.0F);
    }

    private void setPigSize(float size) {
        this.dataManager.set(PIG_SIZE, size);
    }

    public float getSize() {
        return this.dataManager.get(PIG_SIZE);
    }


    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_PIG_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.ENTITY_PIG_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_PIG_DEATH;
    }

    protected void playStepSound(BlockPos pos, IBlockState blockIn) {
        this.playSound(SoundEvents.ENTITY_PIG_STEP, 0.15F, 1.0F);
    }

    protected float getSoundVolume() {
        return 0.4F;
    }

    @Nullable
    protected ResourceLocation getLootTable() {
        return LootTableList.ENTITIES_PIG;
    }

    protected void registerAttributes() {
        super.registerAttributes();
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(10.0D);
        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
    }

    public void livingTick() {
        super.livingTick();

        if (this.world.isRemote) {
            this.pigTimer = Math.max(0, this.pigTimer - 1);
        }

        if (!this.world.isRemote) {
            if(pregnant) {
                gestationTimer++;
                int days = ConfigHandler.COMMON.gestationDays.get();
                if (gestationTimer >= days) {
                    pregnant = false;
                    gestationTimer = 0;

                    int pigletAverage = 8;
                    int pigletRange = 6;

                    int numberOfPiglets = ThreadLocalRandom.current().nextInt(pigletRange)+1+pigletAverage;
                    
                    for (int i = 0; i <= numberOfPiglets; i++) {
                        EnhancedPig enhancedpig = new EnhancedPig(this.world);
                        enhancedpig.setGrowingAge(0);
                        int[] babyGenes = getPigletGenes();
                        enhancedpig.setGenes(babyGenes);
                        enhancedpig.setSharedGenes(babyGenes);
                        enhancedpig.setPigSize();
                        enhancedpig.setGrowingAge(-24000);
                        enhancedpig.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0F);
                        this.world.spawnEntity(enhancedpig);
                    }

                }
            }
        }

        //TODO what lethal genes do pigs have?
        if (!this.world.isRemote){
            lethalGenes();
        }

    }

    private void setPigSize(){
        float size = 1.0F;

        //TODO size calculations go here

        //        0.6F <= size <= 1.5F
        this.pigSize = size;
        this.setPigSize(size);
    }

    public void lethalGenes(){

        //TODO lethal genes go here
        
    }

    public void eatGrassBonus() {
        if (this.isChild()) {
            this.addGrowth(60);
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

    public boolean isBreedingItem(ItemStack stack)
    {
        return TEMPTATION_ITEMS.test(stack);
    }

    public EntityAgeable createChild(EntityAgeable ageable) {
        this.mateGenes = ((EnhancedPig) ageable).getGenes();
        mixMateMitosisGenes();
        mixMitosisGenes();

        pregnant = true;

        this.setGrowingAge(10);
        this.resetInLove();
        ageable.setGrowingAge(10);
        ((EnhancedPig)ageable).resetInLove();

        EntityPlayerMP entityplayermp = this.getLoveCause();
        if (entityplayermp == null && ((EnhancedPig)ageable).getLoveCause() != null) {
            entityplayermp = ((EnhancedPig)ageable).getLoveCause();
        }

        if (entityplayermp != null) {
            entityplayermp.addStat(StatList.ANIMALS_BRED);
            CriteriaTriggers.BRED_ANIMALS.trigger(entityplayermp, this, ((EnhancedPig)ageable), (EntityAgeable)null);
        }

        return null;
    }

    @OnlyIn(Dist.CLIENT)
    public String getPigTexture() {
        if (this.pigTextures.isEmpty()) {
            this.setTexturePaths();
        }
        return this.pigTextures.stream().collect(Collectors.joining("/","enhanced_pig/",""));

    }

    @OnlyIn(Dist.CLIENT)
    public String[] getVariantTexturePaths()
    {
        if (this.pigTextures.isEmpty()) {
            this.setTexturePaths();
        }

        return this.pigTextures.stream().toArray(String[]::new);
    }

    @OnlyIn(Dist.CLIENT)
    private void setTexturePaths() {
        int[] genesForText = getSharedGenes();
        if (genesForText != null) {
            boolean agouti = false;
            int spotMod = 0;
            int red = 5;
            int black = 0;
            int spot = 0;
            int belt = 0;
            int berk = 0;
            int coat = 0;
            int skin = 0;
            boolean tusks = false;

            char[] uuidArry = getCachedUniqueIdString().toCharArray();

            if (genesForText[0] == 1 || genesForText[1] == 1){
                //solid black
                black = 1;
            }else if (genesForText[0] == 2 || genesForText[1] == 2){
                agouti = true;
                black = 2;
            }else if (genesForText[0] == 3 || genesForText[1] == 3){
                //red with spots
                if (genesForText[0] == 3 && genesForText[1] == 3){
                    //big spots
                    black = 3;
                }else{
                    //small spots
                    black = 4;
                }
            }

            if (agouti){
                if (genesForText[2] == 1 || genesForText[3] == 1){
                    //shaded black
                    red = 8;
                }else if (genesForText[2] == 2 || genesForText[3] == 2){
                    //shaded brown
                    black = 6;
                }else if (genesForText[2] == 4 && genesForText[3] == 4) {
                    //recessive black
                    black = 1;
                }
            }

            if (genesForText[4] == 1 || genesForText[5] == 1){
                // reduce red in coat colour
                red = red - 2;
            }

            if (genesForText[6] == 2 && genesForText[7] == 2){
                //black turns to dark chocolate
                //reduce red in coat slightly
                if (black != 6) {
                    black = black + 4;
                }else{
                    black = 9;
                }
                red = red - 1;

            }

//TODO change this one

//            if (genesForText[8] == 1 || genesForText[9] == 1){
//                //turns black to blue/roan blue
//
//            }

            if (genesForText[10] != 1 && genesForText[11] != 1) {
                if (genesForText[10] == 2 || genesForText[11] == 2){
                    //spotted
                    spot = 1;
                } else {
                    //roan spotted
                    spot = 2;
                }
            }


            if (genesForText[12] == 1 || genesForText[13] == 1){
                //dominant white
                belt = 1;
            }else if (genesForText[12] == 2 || genesForText[13] == 2){
                //belted
                belt = 2;
            }

            if (genesForText[14] <= 2 || genesForText[15] <= 2){
                if (genesForText[14] == 2 || genesForText[15] == 2){
                    //tuxedo
                    berk = 1;
                }else{
                    //berkshire
                    berk = 2;
                }
            }

            //TODO make textures for this
//            if (genesForText[16] == 1 || genesForText[17] == 1){
//                spotMod = 2;
//            }else if (genesForText[16] == 2 || genesForText[17] == 2){
//                spotMod = 1;
//            }

            if (black == 1 || black == 2) {
                skin = 3;
            } else if (belt == 1) {
                skin = 1;
            }else{
                skin = 2;
            }

            if (genesForText[36] != 1 && genesForText[37] != 1) {
                if ((genesForText[34] == 1 || genesForText[35] == 1) && (genesForText[34] != 3 && genesForText[35] != 3)) {
                    //furry
                    skin = skin + 9;
                }else if (genesForText[34] == 2 || genesForText[35] == 2) {
                    //normal
                    skin = skin + 6;
                }else{
                    //sparce
                    skin = skin + 3;
                }
            }

            if (genesForText[38] == 1 || genesForText[39] == 1) {
                skin = skin + 3;
            }

            if ((genesForText[38] == 1 || genesForText[39] == 1) && skin >= 10) {
                //wooly
                coat = 1;
            }

            if (!isChild()) {
                if ((Character.isLetter(uuidArry[0]) || uuidArry[0] - 48 >= 8)) {
                    //tusks if "male"
                    tusks = true;
                }
            }

            this.pigTextures.add(PIG_TEXTURES_COATRED[red]);
            if (black != 0) {
                this.pigTextures.add(PIG_TEXTURES_COATBLACK[black]);
            }
            if (spot != 0) {
                this.pigTextures.add(PIG_TEXTURES_SPOT_SPOTS[spot]);
            }
            if (belt != 0) {
                this.pigTextures.add(PIG_TEXTURES_SPOT_BELTED[belt]);
            }
            if (berk != 0) {
                this.pigTextures.add(PIG_TEXTURES_SPOT_BERKSHIRE[berk]);
            }
            this.pigTextures.add(PIG_TEXTURES_COAT[coat]);
            this.pigTextures.add(PIG_TEXTURES_SKINBASE[skin]);
            this.pigTextures.add(PIG_TEXTURES_EYES[0]);
            this.pigTextures.add(PIG_TEXTURES_HOOVES[0]);
            if (tusks){
                this.pigTextures.add(PIG_TEXTURES_TUSKS[1]);
            }

        }
    }
    
    //TODO put item interactable stuff here like saddling pigs


    public void writeAdditional(NBTTagCompound compound) {
        super.writeAdditional(compound);

        //store this pigs's genes
        NBTTagList geneList = new NBTTagList();
        for (int i = 0; i < genes.length; i++) {
            NBTTagCompound nbttagcompound = new NBTTagCompound();
            nbttagcompound.setInt("Gene", genes[i]);
            geneList.add(nbttagcompound);
        }
        compound.setTag("Genes", geneList);

        //store this pigs's mate's genes
        NBTTagList mateGeneList = new NBTTagList();
        for (int i = 0; i < mateGenes.length; i++) {
            NBTTagCompound nbttagcompound = new NBTTagCompound();
            nbttagcompound.setInt("Gene", mateGenes[i]);
            mateGeneList.add(nbttagcompound);
        }
        compound.setTag("FatherGenes", mateGeneList);

        compound.setBoolean("Pregnant", this.pregnant);
        compound.setInt("Gestation", this.gestationTimer);

    }

    /**
     * (abstract) Protected helper method to read subclass entity assets from NBT.
     */
    public void readAdditional(NBTTagCompound compound) {

        super.readAdditional(compound);

        NBTTagList geneList = compound.getList("Genes", 10);
        for (int i = 0; i < geneList.size(); ++i) {
            NBTTagCompound nbttagcompound = geneList.getCompound(i);
            int gene = nbttagcompound.getInt("Gene");
            genes[i] = gene;
        }

        NBTTagList mateGeneList = compound.getList("FatherGenes", 10);
        for (int i = 0; i < mateGeneList.size(); ++i) {
            NBTTagCompound nbttagcompound = mateGeneList.getCompound(i);
            int gene = nbttagcompound.getInt("Gene");
            mateGenes[i] = gene;
        }

        this.pregnant = compound.getBoolean("Pregnant");
        this.gestationTimer = compound.getInt("Gestation");

        setSharedGenes(genes);
        setPigSize();

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


    public int[] getPigletGenes() {
        Random rand = new Random();
        int[] pigletGenes = new int[GENES_LENGTH];

        for (int i = 0; i < genes.length; i = (i + 2)) {
            boolean thisOrMate = rand.nextBoolean();
            if (thisOrMate) {
                pigletGenes[i] = mitosisGenes[i];
                pigletGenes[i+1] = mateMitosisGenes[i+1];
            } else {
                pigletGenes[i] = mateMitosisGenes[i];
                pigletGenes[i+1] = mitosisGenes[i+1];
            }
        }

        return pigletGenes;
    }


    @Nullable
    @Override
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata, @Nullable NBTTagCompound itemNbt) {
        livingdata = super.onInitialSpawn(difficulty, livingdata, itemNbt);
        int[] spawnGenes;

        if (livingdata instanceof GroupData) {
            spawnGenes = ((GroupData) livingdata).groupGenes;
        } else {
            spawnGenes = createInitialGenes();
            livingdata = new GroupData(spawnGenes);
        }

        this.genes = spawnGenes;
        setSharedGenes(genes);
        setPigSize();

        return livingdata;
    }

    private int[] createInitialGenes() {
        int[] initialGenes = new int[GENES_LENGTH];
        //TODO create biome WTC variable [hot and dry biomes, hot and wet biomes, cold biomes] WTC is all others
        int wildType = 2;
        Biome biome = this.world.getBiome(new BlockPos(this));

        if (biome.getDefaultTemperature() >= 0.9F) // hot and wet (jungle)
        {
            wildType = 1;
        }


        //Extension [ Dom.Black, Wildtype+, brindle, red ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[0] = (ThreadLocalRandom.current().nextInt(4) + 1);

        } else {
            initialGenes[0] = (2);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[1] = (ThreadLocalRandom.current().nextInt(4) + 1);

        } else {
            initialGenes[1] = (2);
        }

        //Agouti [ Black Enhancer, brown, wildtype? ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[2] = (ThreadLocalRandom.current().nextInt(1) + 1);

        } else {
            initialGenes[2] = (3);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[3] = (ThreadLocalRandom.current().nextInt(1) + 1);

        } else {
            initialGenes[3] = (3);
        }

        //Chinchilla [ chinchilla, wildtype ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[4] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[4] = (2);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[5] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[5] = (2);
        }

        //Subtle Dilute [ Wildtype+, dilute ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[6] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[6] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[7] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[7] = (1);
        }

        //Blue Dilute [ Dilute, wildtype+ ]
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

        //White Spots [ Wildtype+, spotted, roanSpots ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[10] = (ThreadLocalRandom.current().nextInt(3) + 1);

        } else {
            initialGenes[10] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[11] = (ThreadLocalRandom.current().nextInt(3) + 1);

        } else {
            initialGenes[11] = (1);
        }

        //Dom.White and Belted [ Dom.White, Belted, Wildtype+ ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[12] = (ThreadLocalRandom.current().nextInt(3) + 1);

        } else {
            initialGenes[12] = (3);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[13] = (ThreadLocalRandom.current().nextInt(3) + 1);

        } else {
            initialGenes[13] = (3);
        }

        //Berkshire spots [ Wildtype+, tuxedo, berkshire ]
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

        //White Extension [ Over marked, Medium, undermarked+ ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[16] = (ThreadLocalRandom.current().nextInt(3) + 1);

        } else {
            initialGenes[16] = (3);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[17] = (ThreadLocalRandom.current().nextInt(3) + 1);

        } else {
            initialGenes[17] = (3);
        }

        //face squash gene 1 [ Wildtype+, long, medium, short, squashed ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[18] = (ThreadLocalRandom.current().nextInt(5) + 1);

        } else {
            initialGenes[18] = (5);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[19] = (ThreadLocalRandom.current().nextInt(5) + 1);

        } else {
            initialGenes[19] = (5);
        }

        //inbreeding detector A
            initialGenes[20] = (ThreadLocalRandom.current().nextInt(20) + 1);
            initialGenes[21] = (ThreadLocalRandom.current().nextInt(20) + 20);

        //inbreeding detector B
            initialGenes[22] = (ThreadLocalRandom.current().nextInt(20) + 1);
            initialGenes[23] = (ThreadLocalRandom.current().nextInt(20) + 20);

        //inbreeding detector C
            initialGenes[24] = (ThreadLocalRandom.current().nextInt(20) + 1);
            initialGenes[25] = (ThreadLocalRandom.current().nextInt(20) + 20);

        //inbreeding detector D
            initialGenes[26] = (ThreadLocalRandom.current().nextInt(20) + 1);
            initialGenes[27] = (ThreadLocalRandom.current().nextInt(20) + 20);

        //inbreeding detector E
            initialGenes[28] = (ThreadLocalRandom.current().nextInt(20) + 1);
            initialGenes[29] = (ThreadLocalRandom.current().nextInt(20) + 20);

        //inbreeding detector F
            initialGenes[30] = (ThreadLocalRandom.current().nextInt(20) + 1);
            initialGenes[31] = (ThreadLocalRandom.current().nextInt(20) + 20);


        //Waddles [ waddles, wildtype ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[32] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[32] = (2);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[33] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[33] = (2);
        }

        //hair density [ furry, wildtype, sparce ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[34] = (ThreadLocalRandom.current().nextInt(3) + 1);

        } else {
            initialGenes[34] = (2);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[35] = (ThreadLocalRandom.current().nextInt(3) + 1);

        } else {
            initialGenes[35] = (2);
        }

        //baldness [ Bald, wildtype ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[36] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[36] = (2);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[37] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[37] = (2);
        }

        //wooly [ wooly, wildtype ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[38] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[38] = (2);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[39] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[39] = (2);
        }

        //thick hair [ thick hair+, less hair ]
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

        //face squash gene 2 [ longest, normal, short, shortest]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[42] = (ThreadLocalRandom.current().nextInt(4) + 1);

        } else {
            initialGenes[42] = (4);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[43] = (ThreadLocalRandom.current().nextInt(4) + 1);

        } else {
            initialGenes[43] = (4);
        }



        return initialGenes;
    }


    public void setGenes(int[] genes) {
        this.genes = genes;
    }

    public int[] getGenes() {
        return this.genes;
    }

    public static class GroupData implements IEntityLivingData {

        public int[] groupGenes;

        public GroupData(int[] groupGenes) {
            this.groupGenes = groupGenes;
        }

    }

}
