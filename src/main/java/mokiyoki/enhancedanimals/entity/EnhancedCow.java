package mokiyoki.enhancedanimals.entity;

import mokiyoki.enhancedanimals.items.DebugGenesBook;
import mokiyoki.enhancedanimals.util.handlers.ConfigHandler;
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
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
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

import static mokiyoki.enhancedanimals.util.handlers.RegistryHandler.ENHANCED_COW;

public class EnhancedCow extends AnimalEntity {

    private static final DataParameter<String> SHARED_GENES = EntityDataManager.<String>createKey(EnhancedCow.class, DataSerializers.STRING);
    
    private static final String[] COW_TEXTURES_BASE = new String[] {
            "solid_white.png", "solid_lightcream.png", "solid_cream.png", "solid_silver.png"
    };

    private static final String[] COW_TEXTURES_RED = new String[] {
            "", "red_solid.png", "red_shaded.png"
              , "red_solid.png", "red_shaded_thin.png"
    };

    private static final String[] COW_TEXTURES_BLACK = new String[] {
            "", "black_wildtype.png", "black_wildtype_darker1.png", "black_wildtype_dark.png", "black_solid.png", "black_brindle.png"
              , "black_wildtype_thin.png", "black_wildtype_darker1_thin.png", "black_wildtype_dark_thin.png", "black_solid.png", "black_brindle.png"
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
                "spot_pibald0.png"
    };

    private static final String[] COW_TEXTURES_BELTED = new String[] {
            "", "spot_belted0.png",
                "spot_blaze0.png",
                "spot_brockling0.png"
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

    private static final String[] COW_TEXTURES_TEST = new String[] {
            "cowbase.png", "cowtest.png"
    };


    private static final Ingredient TEMPTATION_ITEMS = Ingredient.fromItems(Blocks.MELON, Blocks.PUMPKIN, Blocks.GRASS, Blocks.HAY_BLOCK, Items.CARROT, Items.WHEAT);

    private static final int WTC = 90;
    private final List<String> cowTextures = new ArrayList<>();
    private static final int GENES_LENGTH = 56;
    private int[] genes = new int[GENES_LENGTH];
    private int[] mateGenes = new int[GENES_LENGTH];
    private int[] mitosisGenes = new int[GENES_LENGTH];
    private int[] mateMitosisGenes = new int[GENES_LENGTH];

    public EnhancedCow(EntityType<? extends EnhancedCow> entityType, World worldIn) {
        super(entityType, worldIn);
//        this.setSize(0.4F, 1F);

    }

    private int cowTimer;
    private EatGrassGoal eatGrassGoal;

    private int gestationTimer = 0;
    private boolean pregnant = false;

    protected void registerGoals() {
        this.eatGrassGoal = new EatGrassGoal(this);
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 2.0D));
        this.goalSelector.addGoal(2, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.25D, TEMPTATION_ITEMS, false));
        this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.25D));
        this.goalSelector.addGoal(5, this.eatGrassGoal);
        this.goalSelector.addGoal(6, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
        this.goalSelector.addGoal(7, new LookAtGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.addGoal(8, new LookRandomlyGoal(this));
    }

    protected void updateAITasks()
    {
        this.cowTimer = this.eatGrassGoal.getEatingGrassTimer();
        super.updateAITasks();
    }

    protected void registerData() {
        super.registerData();
        this.dataManager.register(SHARED_GENES, new String());
    }
//
//    @Nullable
//    protected ResourceLocation getLootTable() { return LootTableList.ENTITIES_COW; }

    protected void registerAttributes() {
        super.registerAttributes();
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(8.0D);
        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.23000000417232513D);
    }

    public void livingTick() {
        super.livingTick();

        if (this.world.isRemote) {
            this.cowTimer = Math.max(0, this.cowTimer - 1);
        }

        if (!this.world.isRemote) {
            if(pregnant) {
                gestationTimer++;
                int days = ConfigHandler.COMMON.gestationDays.get();
                if (gestationTimer >= days) {
                    pregnant = false;
                    gestationTimer = 0;

                    EnhancedCow enhancedcow = ENHANCED_COW.create(this.world);
                    enhancedcow.setGrowingAge(0);
                    int[] babyGenes = getCalfGenes();
                    enhancedcow.setGenes(babyGenes);
                    enhancedcow.setSharedGenes(babyGenes);
                    enhancedcow.setGrowingAge(-24000);
                    enhancedcow.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0F);
                    this.world.addEntity(enhancedcow);

                }
            }
        }

        if (!this.world.isRemote){
            lethalGenes();
        }

    }

    public void lethalGenes(){

        if(genes[26] == 1 && genes[27] == 1) {
            this.remove();
        }
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

    public AgeableEntity createChild(AgeableEntity ageable) {
        this.mateGenes = ((EnhancedCow) ageable).getGenes();
        mixMateMitosisGenes();
        mixMitosisGenes();

        pregnant = true;

        this.setGrowingAge(10);
        this.resetInLove();
        ageable.setGrowingAge(10);
        ((EnhancedCow)ageable).resetInLove();

        return null;
    }

    @OnlyIn(Dist.CLIENT)
    public String getCowTexture() {
        if (this.cowTextures.isEmpty()) {
            this.setTexturePaths();
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
    private void setTexturePaths() {
        int[] genesForText = getSharedGenes();
        if (genesForText != null) {

            int base = 0;
            int red = 0;
            int black = 0;
            int roan = 0;
            int speckled = 0;
            int whiteface = 0;
            int belted = 0;
            int coloursided = 0;
            int skin = 0;
            int hooves = 0;

            char[] uuidArry = getCachedUniqueIdString().toCharArray();

            //dominant red
            if (genesForText[6] == 1 || genesForText[7] == 1){
                //make red instead maybe flip dominant red toggle?
                red = 1;
                skin = 1;
            }else {
                if (genesForText[0] == 1 || genesForText[1] == 1) {
                    black = 4;
                } else if (genesForText[0] == 2 || genesForText[1] == 2) {
                    red = 2;

                    //Agouti
                    if (genesForText[4] == 1 || genesForText[5] == 1) {
                        if (genesForText[4] == 2 || genesForText[5] == 2) {
                            //darker wildtype
                            //or other incomplete dominance
                            black = 2;
                        } else {
                            //complete dominance of black enhancer
                            black = 3;
                        }
                    } else if (genesForText[4] == 2 || genesForText[5] == 2) {
                        //wildtype
                        black = 1;
                    } else if (genesForText[4] == 3 || genesForText[5] == 3) {
                        //white bellied fawn (i believe this is like silver)
                        black = 1;
                        red = 0;
                        base = 2;
                        //TODO set up something here to dilute the colour of red to a cream or white
                    } else {
                        //brindle (there might be a recessive black but no one seems to know lol)
                        black = 5;
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
                }
            } //not dilute

            //chocolate
            if (genesForText[10] == 2 && genesForText[11] == 2){
                //make chocolate version possibly add variations to exact colour
            }

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
                belted = 2;
            }else if (genesForText[18] == 3 || genesForText[19] == 3){
                //brockling
                belted = 3;
            }

            //TODO make randomizers for the textures
//            if (spots != 0){
//                if (Character.isDigit(uuidArry[1])) {
//                    spots = 1 + (uuidArry[1] - 48);
//                } else {
//                    char d = uuidArry[1];
//
//                    switch (d) {
//                        case 'a':
//                            spots = 11;
//                            break;
//                        case 'b':
//                            spots = 12;
//                            break;
//                        case 'c':
//                            spots = 13;
//                            break;
//                        case 'd':
//                            spots = 14;
//                            break;
//                        case 'e':
//                            spots = 15;
//                            break;
//                        case 'f':
//                            spots = 16;
//                            break;
//                        default:
//                            spots = 0;
//                    }
//                }
//            }

            //these alter texture to fit model changes
            if(genesForText[26] == 1 || genesForText[27] == 1) {
                hooves = 1;
            }
            if(genesForText[54] == 1 && genesForText[55] == 1){
                if (red != 0){
                    red = red*2;
                }
                if (black != 0){
                    black = black*2;
                }
            }

            
            //TODO change white spots to add whitening together
            //TODO add shading under correct conditions

            this.cowTextures.add(COW_TEXTURES_BASE[0]);
            if (red != 0){
                this.cowTextures.add(COW_TEXTURES_RED[red]);
            }
            if (black != 0){
                this.cowTextures.add(COW_TEXTURES_BLACK[black]);
            }
            this.cowTextures.add(COW_TEXTURES_SKIN[skin]);
            if (roan != 0){
                this.cowTextures.add(COW_TEXTURES_ROAN[roan]);
            }
            if (speckled != 0){
                this.cowTextures.add(COW_TEXTURES_SPECKLED[speckled]);
            }
            if (whiteface != 0){
                this.cowTextures.add(COW_TEXTURES_WHITEFACE[whiteface]);
            }
            if (belted != 0){
                this.cowTextures.add(COW_TEXTURES_BELTED[belted]);
            }
            if (coloursided != 0){
                this.cowTextures.add(COW_TEXTURES_COLOURSIDED[coloursided]);
            }
            //TODO add hoof colour genetics
            this.cowTextures.add(COW_TEXTURES_HOOVES[hooves]);
            //TODO add eye colour genetics
            this.cowTextures.add(COW_TEXTURES_EYES[0]);

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


    @Override
    public boolean processInteract(PlayerEntity entityPlayer, Hand hand) {
        ItemStack itemStack = entityPlayer.getHeldItem(hand);
        Item item = itemStack.getItem();
        if (!this.world.isRemote) {
            if (item == Items.BUCKET && !entityPlayer.abilities.isCreativeMode && !this.isChild()) {
                entityPlayer.playSound(SoundEvents.ENTITY_COW_MILK, 1.0F, 1.0F);
                itemStack.shrink(1);
                if (itemStack.isEmpty()) {
                    entityPlayer.setHeldItem(hand, new ItemStack(Items.MILK_BUCKET));
                } else if (!entityPlayer.inventory.addItemStackToInventory(new ItemStack(Items.MILK_BUCKET))) {
                    entityPlayer.dropItem(new ItemStack(Items.MILK_BUCKET), false);
                }
            } else if (item instanceof DebugGenesBook) {
                ((DebugGenesBook)item).displayGenes(this.dataManager.get(SHARED_GENES));
            }
        }
        return super.processInteract(entityPlayer, hand);
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


    public int[] getCalfGenes() {
        Random rand = new Random();
        int[] calfGenes = new int[GENES_LENGTH];

        for (int i = 0; i < genes.length; i = (i + 2)) {
            boolean thisOrMate = rand.nextBoolean();
            if (thisOrMate) {
                calfGenes[i] = mitosisGenes[i];
                calfGenes[i+1] = mateMitosisGenes[i+1];
            } else {
                calfGenes[i] = mateMitosisGenes[i];
                calfGenes[i+1] = mitosisGenes[i+1];
            }
        }

        return calfGenes;
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

        return livingdata;
    }

    private int[] createInitialGenes() {
        int[] initialGenes = new int[GENES_LENGTH];
        //TODO create biome WTC variable [hot and dry biomes, hot and wet biomes, cold biomes] WTC is all others
        int wildType = 0;
        Biome biome = this.world.getBiome(new BlockPos(this));

        if (biome.getDefaultTemperature() >= 0.9F && biome.getDownfall() > 0.8F) // hot and wet (jungle)
        {
            wildType = 1;
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

        } else {
            initialGenes[2] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[3] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[3] = (1);
        }

        //Agouti [ Black enhancer, Wildtype+, white-bellied fawn, brindle ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[4] = (ThreadLocalRandom.current().nextInt(4) + 1);

        } else {
            initialGenes[4] = (2);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[5] = (ThreadLocalRandom.current().nextInt(4) + 1);

        } else {
            initialGenes[5] = (2);
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

        //Horns [normal, polled, scured, africanhorn]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[12] = (ThreadLocalRandom.current().nextInt(4) + 1);

        } else {
            initialGenes[12] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[13] = (ThreadLocalRandom.current().nextInt(4) + 1);

        } else {
            initialGenes[13] = (1);
        }

        //Speckled Spots
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

        //White face and other spots [whiteface1, border spotted, wildtype, piebald]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[16] = (ThreadLocalRandom.current().nextInt(4) + 1);

        } else {
            initialGenes[16] = (3);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[17] = (ThreadLocalRandom.current().nextInt(4) + 1);

        } else {
            initialGenes[17] = (3);
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

        //size genes reducer [wildtype, smaller smaller smallest...]
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
            initialGenes[32] = (ThreadLocalRandom.current().nextInt(10) + 1);

        } else {
            initialGenes[32] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[33] = (ThreadLocalRandom.current().nextInt(10) + 1);

        } else {
            initialGenes[33] = (1);
        }

        //size genes varient1 [wildtype, smaller, smallest]
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

        //size genes varient2 [smallest, smaller, wildtype]
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

        //hump size [none, smallest, smaller, medium, bigger, biggest]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[38] = (ThreadLocalRandom.current().nextInt(6) + 1);

        } else {
            initialGenes[38] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[39] = (ThreadLocalRandom.current().nextInt(6) + 1);

        } else {
            initialGenes[39] = (1);
        }

        //hump size [tallest, tall, medium, short]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[40] = (ThreadLocalRandom.current().nextInt(4) + 1);

        } else {
            initialGenes[40] = (4);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[41] = (ThreadLocalRandom.current().nextInt(4) + 1);

        } else {
            initialGenes[41] = (4);
        }

        //ear size [smallest, smaller, normal, long, longest]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[42] = (ThreadLocalRandom.current().nextInt(5) + 1);

        } else {
            initialGenes[42] = (2);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[43] = (ThreadLocalRandom.current().nextInt(5) + 1);

        } else {
            initialGenes[43] = (2);
        }

        //ear size suppressor [smaller ears, longer ears]
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

        //ear floppiness [stiffer, normal, floppier, floppiest]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[46] = (ThreadLocalRandom.current().nextInt(4) + 1);

        } else {
            initialGenes[46] = (2);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[47] = (ThreadLocalRandom.current().nextInt(4) + 1);

        } else {
            initialGenes[47] = (2);
        }

        //coat smoothness [smooth, normal] (this gives a slick coat like brahma and suppresses furry coat genes)
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[48] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[48] = (2);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[49] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[49] = (2);
        }

        //Furry coat 1 [normal, furry]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[50] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[50] = (2);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[51] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[51] = (2);
        }

        //furry coat 2 [normal, furry]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[52] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[52] = (2);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[53] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[53] = (2);
        }

        //body type [smallest to largest]
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
