package mokiyoki.enhancedanimals.entity;

import mokiyoki.enhancedanimals.ai.ECWanderAvoidWater;
import mokiyoki.enhancedanimals.util.handlers.ConfigHandler;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.BreedGoal;
import net.minecraft.entity.ai.goal.EatGrassGoal;
import net.minecraft.entity.ai.goal.FollowParentGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.PanicGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.TemptGoal;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
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
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import static mokiyoki.enhancedanimals.util.handlers.RegistryHandler.ENHANCED_PIG;

public class EnhancedPig extends AnimalEntity {
    private static final DataParameter<String> SHARED_GENES = EntityDataManager.<String>createKey(EnhancedPig.class, DataSerializers.STRING);
    private static final DataParameter<Float> PIG_SIZE = EntityDataManager.createKey(EnhancedPig.class, DataSerializers.FLOAT);

    private static final String[] PIG_TEXTURES_COATRED = new String[] {
            "pigbase.png", "solid_white.png", "solid_milk.png", "solid_cream.png", "solid_carmel.png", "solid_orange.png", "solid_ginger.png", "solid_red.png", "solid_brown.png"
    };
    private static final String[] PIG_TEXTURES_COATBLACK = new String[] {
            "red", "black_solid.png", "black_wildtype.png", "black_brindle.png", "choc_wildtype.png"
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

    private static final Ingredient TEMPTATION_ITEMS = Ingredient.fromItems(Blocks.MELON, Blocks.PUMPKIN, Blocks.GRASS, Blocks.HAY_BLOCK, Items.CARROT, Items.WHEAT, Items.BEETROOT, Items.ROTTEN_FLESH, Items.APPLE, Items.COOKED_CHICKEN, Items.COOKED_BEEF, Items.COOKED_MUTTON, Items.COOKED_RABBIT, Items.COOKED_SALMON, Items.COOKED_COD, Blocks.BROWN_MUSHROOM, Blocks.DARK_OAK_SAPLING, Blocks.OAK_SAPLING, Items.MILK_BUCKET, Items.BREAD);

    private static final int WTC = 90;
    private final List<String> pigTextures = new ArrayList<>();
    private static final int GENES_LENGTH = 80;
    private int[] genes = new int[GENES_LENGTH];
    private int[] mateGenes = new int[GENES_LENGTH];
    private int[] mitosisGenes = new int[GENES_LENGTH];
    private int[] mateMitosisGenes = new int[GENES_LENGTH];

    private float pigSize;

    public EnhancedPig(EntityType<? extends EnhancedPig> entityType, World worldIn) {
        super(entityType, worldIn);
        //TODO why doesnt this change from pig to pig
        this.setPigSize();
//        this.setSize(0.9F, pigSize*0.9F);
    }

    private int pigTimer;
    private EatGrassGoal entityAIEatGrass;

    private int gestationTimer = 0;
    private boolean pregnant = false;

    protected void initEntityAI() {
        this.entityAIEatGrass = new EatGrassGoal(this);
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 1.25D));
        this.goalSelector.addGoal(3, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(4, new TemptGoal(this, 1.2D, Ingredient.fromItems(Items.CARROT_ON_A_STICK), false));
        this.goalSelector.addGoal(4, new TemptGoal(this, 1.2D, false, TEMPTATION_ITEMS));
        this.goalSelector.addGoal(5, new FollowParentGoal(this, 1.1D));
        this.goalSelector.addGoal(5, this.entityAIEatGrass);
        this.goalSelector.addGoal(6, new ECWanderAvoidWater(this, 1.0D));
        this.goalSelector.addGoal(7, new LookAtGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.addGoal(8, new LookRandomlyGoal(this));
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

    protected void playStepSound(BlockPos pos, BlockState blockIn) {
        this.playSound(SoundEvents.ENTITY_PIG_STEP, 0.15F, 1.0F);
    }

    protected float getSoundVolume() {
        return 0.4F;
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
                        EnhancedPig enhancedpig = ENHANCED_PIG.create(this.world);
                        enhancedpig.setGrowingAge(0);
                        int[] babyGenes = getPigletGenes();
                        enhancedpig.setGenes(babyGenes);
                        enhancedpig.setSharedGenes(babyGenes);
                        enhancedpig.setPigSize();
                        enhancedpig.setGrowingAge(-24000);
                        enhancedpig.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0F);
                        this.world.addEntity(enhancedpig);
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

    public AgeableEntity createChild(AgeableEntity ageable) {
        this.mateGenes = ((EnhancedPig) ageable).getGenes();
        mixMateMitosisGenes();
        mixMitosisGenes();

        pregnant = true;

        this.setGrowingAge(10);
        this.resetInLove();
        ageable.setGrowingAge(10);
        ((EnhancedPig)ageable).resetInLove();

        ServerPlayerEntity entityplayermp = this.getLoveCause();
        if (entityplayermp == null && ((EnhancedPig)ageable).getLoveCause() != null) {
            entityplayermp = ((EnhancedPig)ageable).getLoveCause();
        }

        if (entityplayermp != null) {
            entityplayermp.addStat(Stats.ANIMALS_BRED);
            CriteriaTriggers.BRED_ANIMALS.trigger(entityplayermp, this, ((EnhancedPig)ageable), (AgeableEntity)null);
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
            
            if (genesForText[0] == 1 || genesForText[1] == 1){
                //solid black
            }else if (genesForText[0] == 2 || genesForText[1] == 2){
                agouti = true;
            }else if (genesForText[0] == 3 || genesForText[1] == 3){
                //red with spots
                if (genesForText[0] == 3 && genesForText[1] == 3){
                    //big spots
                }else{
                    //small spots
                }
            }else{
                //red
            }

            if (agouti){
                if (genesForText[2] == 1 || genesForText[3] == 1){
                    //shaded black
                }else if (genesForText[2] == 2 || genesForText[3] == 2){
                    //shaded brown
                }else if (genesForText[2] == 3 || genesForText[3] == 3){
                    //agouti wild type
                }else{
                    //recessive black
                }
            }

            if (genesForText[4] == 1 || genesForText[5] == 1){
                // reduce red in coat colour
            }

            if (genesForText[6] == 2 && genesForText[7] == 2){
                //black turns to dark chocolate
                //reduce red in coat slightly
            }

            if (genesForText[8] == 1 || genesForText[9] == 1){
                //turns black to blue/roan blue
            }

            if (genesForText[10] == 2 && genesForText[11] == 2){
                //white spotted
            }

            if (genesForText[12] == 1 || genesForText[13] == 1){
                //dominant white
            }else if (genesForText[12] == 2 || genesForText[13] == 2){
                //belted
            }

            if (genesForText[14] <= 2 || genesForText[15] <= 2){
                if (genesForText[14] == 2 || genesForText[15] == 2){
                    //tuxedo
                }else{
                    //berkshire
                }
            }

            if (genesForText[16] == 1 || genesForText[17] == 1){
                spotMod = 2;
            }else if (genesForText[16] == 2 || genesForText[17] == 2){
                spotMod = 1;
            }


            
        this.pigTextures.add(PIG_TEXTURES_COATRED[0]);
        this.pigTextures.add(PIG_TEXTURES_COATBLACK[0]);
        this.pigTextures.add(PIG_TEXTURES_SKINBASE[0]);

        }
    }
    
    //TODO put item interactable stuff here like saddling pigs


    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);

        //store this pigs's genes
        ListNBT geneList = new ListNBT();
        for (int i = 0; i < genes.length; i++) {
            CompoundNBT nbttagcompound = new CompoundNBT();
            nbttagcompound.putInt("Gene", genes[i]);
            geneList.add(nbttagcompound);
        }
        compound.put("Genes", geneList);

        //store this pigs's mate's genes
        ListNBT mateGeneList = new ListNBT();
        for (int i = 0; i < mateGenes.length; i++) {
            CompoundNBT nbttagcompound = new CompoundNBT();
            nbttagcompound.putInt("Gene", mateGenes[i]);
            mateGeneList.add(nbttagcompound);
        }
        compound.put("FatherGenes", mateGeneList);

        compound.putBoolean("Pregnant", this.pregnant);
        compound.putInt("Gestation", this.gestationTimer);

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
    public ILivingEntityData onInitialSpawn(IWorld world, DifficultyInstance difficulty, SpawnReason spawnReason, @Nullable ILivingEntityData livingdata, @Nullable CompoundNBT itemNbt) {
        livingdata = super.onInitialSpawn(world, difficulty, spawnReason, livingdata, itemNbt);
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

        //face squash gene 2 [ shorter, normal, larger, largest ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[18] = (ThreadLocalRandom.current().nextInt(4) + 1);

        } else {
            initialGenes[18] = (2);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[19] = (ThreadLocalRandom.current().nextInt(4) + 1);

        } else {
            initialGenes[19] = (2);
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

        return initialGenes;
    }


    public void setGenes(int[] genes) {
        this.genes = genes;
    }

    public int[] getGenes() {
        return this.genes;
    }

    public static class GroupData implements ILivingEntityData {

        public int[] groupGenes;

        public GroupData(int[] groupGenes) {
            this.groupGenes = groupGenes;
        }

    }

}
