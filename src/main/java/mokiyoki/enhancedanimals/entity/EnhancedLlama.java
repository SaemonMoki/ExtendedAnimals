package mokiyoki.enhancedanimals.entity;

import mokiyoki.enhancedanimals.ai.ECLlamaFollowCaravan;
import mokiyoki.enhancedanimals.ai.ECRunAroundLikeCrazy;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.Particles;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static mokiyoki.enhancedanimals.util.handlers.RegistryHandler.ENHANCED_LLAMA;

public class EnhancedLlama extends EntityAnimal implements IRangedAttackMob {

    private static final Predicate<Entity> IS_BREEDING = (entity) -> {
        return entity instanceof EnhancedLlama && ((EnhancedLlama)entity).isBreeding();
    };

    private static final DataParameter<Optional<UUID>> OWNER_UNIQUE_ID = EntityDataManager.createKey(EnhancedLlama.class, DataSerializers.OPTIONAL_UNIQUE_ID);

    private static final DataParameter<String> SHARED_GENES = EntityDataManager.<String>createKey(EnhancedLlama.class, DataSerializers.STRING);

    private static final DataParameter<Byte> STATUS = EntityDataManager.createKey(EnhancedLlama.class, DataSerializers.BYTE);

    private static final String[] LLAMA_TEXTURES_GROUND = new String[] {
            "brokenlogic.png", "ground_paleshaded.png", "ground_shaded.png", "ground_blackred.png", "ground_bay.png", "ground_mahogany.png", "ground_blacktan.png", "black.png", "fawn.png"
    };

    private static final String[] LLAMA_TEXTURES_PATTERN = new String[] {
            "", "pattern_paleshaded.png", "pattern_shaded.png", "pattern_blackred.png", "pattern_bay.png", "pattern_mahogany.png", "pattern_blacktan.png"
    };

    private static final String[] LLAMA_TEXTURES_ROAN = new String[] {
            "", "roan_0.png", "roan_1.png", "roan_2.png", "roan_3.png", "roan_4.png", "roan_5.png", "roan_6.png", "roan_7.png", "roan_8.png", "roan_9.png", "roan_a.png", "roan_b.png", "roan_c.png", "roan_d.png", "roan_e.png", "roan_f.png"
    };

    // higher numbers are more white
    private static final String[] LLAMA_TEXTURES_TUXEDO = new String[] {
            "", "tuxedo_0.png", "tuxedo_1.png", "tuxedo_2.png", "tuxedo_3.png", "tuxedo_4.png", "tuxedo_5.png", "tuxedo_6.png", "tuxedo_7.png", "tuxedo_8.png", "tuxedo_9.png", "tuxedo_a.png", "tuxedo_b.png", "tuxedo_c.png", "tuxedo_d.png", "tuxedo_e.png", "tuxedo_f.png"
    };

    // higher numbers are more white
    private static final String[] LLAMA_TEXTURES_PIEBALD = new String[] {
            "", "piebald_0.png", "piebald_1.png", "piebald_2.png", "piebald_3.png", "piebald_4.png", "piebald_5.png", "piebald_6.png", "piebald_7.png", "piebald_8.png", "piebald_9.png", "piebald_a.png", "piebald_b.png", "piebald_c.png", "piebald_d.png", "piebald_e.png", "piebald_f.png"
    };

    // higher numbers are more white
    private static final String[] LLAMA_TEXTURES_DOMWHITE = new String[] {
            "", "domwhite_leaky.png", "domwhite_tinted.png", "domwhite_solid.png"
    };

    private static final String[] LLAMA_TEXTURES_FUR = new String[] {
            "fur_short.png", "fur_medium.png", "fur_long.png", "fur_short_suri.png", "fur_medium_suri.png", "fur_long_suri.png"
    };

    private static final String[] LLAMA_TEXTURES_EYES = new String[] {
            "eyes_black.png", "eyes_blue", "eyes_iceblue"
    };

    private static final String[] LLAMA_TEXTURES_SKIN = new String[] {
            "skin_black.png", "skin_pink.png"
    };

    private final List<String> llamaTextures = new ArrayList<>();

    private static final Ingredient TEMPTATION_ITEMS = Ingredient.fromItems(Items.CARROT);

    public float destPos;

    private static final int WTC = 90;
    private static final int GENES_LENGTH = 20;
    private int[] genes = new int[GENES_LENGTH];
    private int[] mateGenes = new int[GENES_LENGTH];
    private int[] mitosisGenes = new int[GENES_LENGTH];
    private int[] mateMitosisGenes = new int[GENES_LENGTH];

    protected int temper;
    private int jumpRearingCounter;

    private boolean didSpit;
    @Nullable
    private EnhancedLlama caravanHead;
    @Nullable
    private EnhancedLlama caravanTail;

    public EnhancedLlama(World worldIn) {
        super(ENHANCED_LLAMA, worldIn);
        this.setSize(0.9F, 1.87F);
        this.setPathPriority(PathNodeType.WATER, 0.0F);
    }

    protected void initEntityAI() {
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new ECRunAroundLikeCrazy(this, 1.2D));
        this.tasks.addTask(2, new ECLlamaFollowCaravan(this, (double)2.1F));
        this.tasks.addTask(3, new EntityAIAttackRanged(this, 1.25D, 40, 20.0F));
        this.tasks.addTask(3, new EntityAIPanic(this, 1.2D));
        this.tasks.addTask(4, new EntityAIMate(this, 1.0D));
        this.tasks.addTask(5, new EntityAIFollowParent(this, 1.0D));
        this.tasks.addTask(6, new EntityAIWanderAvoidWater(this, 0.7D));
        this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EnhancedLlama.AIHurtByTarget(this));
        this.targetTasks.addTask(2, new EnhancedLlama.AIDefendTarget(this));
    }


    protected void registerData() {
        super.registerData();
        this.dataManager.register(SHARED_GENES, new String());
        this.dataManager.register(STATUS, (byte)0);
        this.dataManager.register(OWNER_UNIQUE_ID, Optional.empty());
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

    public float getEyeHeight()
    {
        return this.height;
    }

    protected void registerAttributes() {
        super.registerAttributes();
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(4.0D);
        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
    }

    public void livingTick()
    {
        super.livingTick();
        this.destPos = (float)((double)this.destPos + (double)(this.onGround ? -1 : 4) * 0.3D);
        this.destPos = MathHelper.clamp(this.destPos, 0.0F, 1.0F);
    }

    protected SoundEvent getAmbientSound()
    {
        return SoundEvents.ENTITY_LLAMA_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn)
    {
        return SoundEvents.ENTITY_LLAMA_HURT;
    }

    protected SoundEvent getDeathSound()
    {
        return SoundEvents.ENTITY_LLAMA_DEATH;
    }

    protected void playStepSound(BlockPos pos, IBlockState blockIn) {
        this.playSound(SoundEvents.ENTITY_LLAMA_STEP, 0.15F, 1.0F);
    }

    protected void playChestEquipSound() {
        this.playSound(SoundEvents.ENTITY_LLAMA_CHEST, 1.0F, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
    }

//    protected SoundEvent getAngrySound() { super.getAngrySound(); return SoundEvents.ENTITY_LLAMA_ANGRY; }

    public boolean isBreedingItem(ItemStack stack)
    {
        return TEMPTATION_ITEMS.test(stack);
    }

    public EntityAgeable createChild(EntityAgeable ageable) {
        this.mateGenes = ((EnhancedLlama) ageable).getGenes();
        mixMateMitosisGenes();
        mixMitosisGenes();
        EnhancedLlama enhancedllama = new EnhancedLlama(this.world);
        enhancedllama.setGrowingAge(0);
        int[] babyGenes = getCriaGenes();
        enhancedllama.setGenes(babyGenes);
        enhancedllama.setSharedGenes(babyGenes);
        return enhancedllama;
    }

    public void writeAdditional(NBTTagCompound compound) {
        super.writeAdditional(compound);

        //store this llamas's genes
        NBTTagList geneList = new NBTTagList();
        for (int i = 0; i < genes.length; i++) {
            NBTTagCompound nbttagcompound = new NBTTagCompound();
            nbttagcompound.setInt("Gene", genes[i]);
            geneList.add(nbttagcompound);
        }
        compound.setTag("Genes", geneList);

        //store this llamas's mate's genes
        NBTTagList mateGeneList = new NBTTagList();
        for (int i = 0; i < mateGenes.length; i++) {
            NBTTagCompound nbttagcompound = new NBTTagCompound();
            nbttagcompound.setInt("Gene", mateGenes[i]);
            mateGeneList.add(nbttagcompound);
        }
        compound.setTag("FatherGenes", mateGeneList);
    }

    @OnlyIn(Dist.CLIENT)
    public String getLlamaTexture() {
        if (this.llamaTextures.isEmpty()) {
            this.setTexturePaths();
        }
        return this.llamaTextures.stream().collect(Collectors.joining("/","enhanced_llama/",""));

    }

    @OnlyIn(Dist.CLIENT)
    public String[] getVariantTexturePaths()
    {
        if (this.llamaTextures.isEmpty()) {
            this.setTexturePaths();
        }

        return this.llamaTextures.stream().toArray(String[]::new);
    }

    @OnlyIn(Dist.CLIENT)
    private void setTexturePaths() {
        int[] genesForText = getSharedGenes();
        if (genesForText != null) {
            int ground = 0;
            int pattern = 0;
            int roan = 0;
            int tux = 0;
            int piebald = 0;
            int domwhite = 0;
            int fur = 0;
            int eyes = 0;
            int skin = 0;
            // i is a random modifier
            char[] uuidArry = getCachedUniqueIdString().toCharArray();

            if ( genesForText[14] == 1 || genesForText[15] == 1 ){
                //Dominant Black
                ground = 7;
            } else if ( genesForText[14] == 3 || genesForText[15] == 3 ){
                //fawn self
                ground = 8;
            }else{
                if ( genesForText[16] == 1 || genesForText[17] == 1 ){
                    //pale shaded fawn
                        ground = 1;
                        pattern = 1;
                } else if ( genesForText[16] == 2 || genesForText[17] == 2 ){
                    //shaded fawn
                        ground = 2;
                        pattern = 2;
                } else if ( genesForText[16] == 3 || genesForText[17] == 3 ){
                    //black trimmed red
                        ground = 3;
                        pattern = 3;
                } else if ( genesForText[16] == 4 || genesForText[17] == 4 ){
                    //bay
                        ground = 4;
                        pattern = 4;
                } else if ( genesForText[16] == 5 || genesForText[17] == 5 ){
                    //mahogany
                        ground = 5;
                        pattern = 5;
                }else if ( genesForText[16] == 6 || genesForText[17] == 6 ){
                    //black and tan
                        ground = 6;
                        pattern = 6;
                }else{
                    //black
                        ground = 7;
                }
            }

            if ( genesForText[6] == 1 || genesForText[7] == 1){
                //dominant white   0 1 2 3 4 5 6 7 8 9 a b c d e f

                if ( Character.isDigit(uuidArry[1]) ){
                    if ((uuidArry[1]-48) < 5 ){
                        domwhite = 1;
                    } else {
                        domwhite = 2;
                    }
                }else{
                        domwhite = 3;
                }

            }

            if ( genesForText[8] == 1 || genesForText[9] == 1){
                //roan

                if (Character.isDigit(uuidArry[2])){
                    roan = 1 + (uuidArry[2]-48);
                } else {
                    char d = uuidArry[2];

                    switch (d){
                        case 'a':
                            roan = 11;
                            break;
                        case 'b':
                            roan = 12;
                            break;
                        case 'c':
                            roan = 13;
                            break;
                        case 'd':
                            roan = 14;
                            break;
                        case 'e':
                            roan = 15;
                            break;
                        case 'f':
                            roan = 16;
                            break;
                        //TODO add debugging default option
                    }
                }
            }

            if ( genesForText[10] == 2 && genesForText[11] == 2){
                //piebald

                if ( Character.isDigit(uuidArry[4]) ){
                    piebald = 1 + (uuidArry[4] - 48);
                } else {
                    char d = uuidArry[4];

                    switch (d){
                        case 'a':
                            piebald = 11;
                            break;
                        case 'b':
                            piebald = 12;
                            break;
                        case 'c':
                            piebald = 13;
                            break;
                        case 'd':
                            piebald = 14;
                            break;
                        case 'e':
                            piebald = 15;
                            break;
                        case 'f':
                            piebald = 16;
                            break;
                        //TODO add debugging default option
                    }
                }
            }

            if ( genesForText[12] == 1 || genesForText[13] == 1){
                //tuxedo

                if ( Character.isDigit(uuidArry[6]) ){
                    tux = 1 + (uuidArry[6]-48);
                } else {
                    char d = uuidArry[6];

                    switch (d){
                        case 'a':
                            tux = 11;
                            break;
                        case 'b':
                            tux = 12;
                            break;
                        case 'c':
                            tux = 13;
                            break;
                        case 'd':
                            tux = 14;
                            break;
                        case 'e':
                            tux = 15;
                            break;
                        case 'f':
                            tux = 16;
                            break;
                        default:
                            ground = 0;
                            pattern = 0;
                            tux = 0;
                        //TODO add debugging default option
                    }
                }
            }

            if (domwhite > 0){
                skin = 1;
                if (piebald > 0){
                    eyes = 1;
                }
            }



            this.llamaTextures.add(LLAMA_TEXTURES_GROUND[ground]);

        if (pattern != 0) {
            this.llamaTextures.add(LLAMA_TEXTURES_PATTERN[pattern]);
        }

        if (roan != 0) {
            this.llamaTextures.add(LLAMA_TEXTURES_ROAN[roan]);
        }

        if (tux != 0) {
            this.llamaTextures.add(LLAMA_TEXTURES_TUXEDO[tux]);
        }

        if (piebald != 0) {
            this.llamaTextures.add(LLAMA_TEXTURES_PIEBALD[piebald]);
        }

        if (domwhite != 0) {
            this.llamaTextures.add(LLAMA_TEXTURES_DOMWHITE[domwhite]);
        }

        if (fur != 0) {
            this.llamaTextures.add(LLAMA_TEXTURES_FUR[fur]);
        }

            this.llamaTextures.add(LLAMA_TEXTURES_EYES[eyes]);

            this.llamaTextures.add(LLAMA_TEXTURES_SKIN[skin]);


        } //if genes are not null end bracket

    } // setTexturePaths end bracket

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

    public int[] getCriaGenes() {
        Random rand = new Random();
        int[] criaGenes = new int[GENES_LENGTH];

        for (int i = 0; i < genes.length; i++) {
            boolean thisOrMate = rand.nextBoolean();
            if (thisOrMate) {
                criaGenes[i] = mitosisGenes[i];
            } else {
                criaGenes[i] = mateMitosisGenes[i];
            }
        }

        return criaGenes;
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
        return livingdata;
    }

    private int[] createInitialGenes() {
        int[] initialGenes = new int[GENES_LENGTH];
        //TODO create biome WTC variable [hot and dry biomes, cold biomes ] WTC is neutral biomes "all others"


        //[ 0=minecraft wildtype, 1=jungle wildtype, 2=savanna wildtype, 3=cold wildtype, 4=swamp wildtype ]
//        int wildType = 0;
//        Biome biome = this.world.getBiome(new BlockPos(this));

//        if (biome.getDefaultTemperature() >= 0.9F && biome.getRainfall() > 0.8F) // hot and wet (jungle)
//        {
//            wildType = 1;
//        }


/**
 * Genes List
 */

        /**
         * Colour Genes
         */

        //Endurance gene [ wildtype, stronger1, stronger2]
        if(ThreadLocalRandom.current().nextInt(100)>WTC){
            initialGenes[0] = (ThreadLocalRandom.current().nextInt(3)+1);

        } else {
            initialGenes[0] = (1);
        }
        if(ThreadLocalRandom.current().nextInt(100)>WTC){
            initialGenes[1] = (ThreadLocalRandom.current().nextInt(3)+1);

        } else {
            initialGenes[1] = (1);
        }


        //Strength gene [ wildtype, stronger1, stronger2]
        if(ThreadLocalRandom.current().nextInt(100)>WTC){
            initialGenes[2] = (ThreadLocalRandom.current().nextInt(3)+1);

        } else {
            initialGenes[2] = (1);
        }
        if(ThreadLocalRandom.current().nextInt(100)>WTC){
            initialGenes[3] = (ThreadLocalRandom.current().nextInt(3)+1);

        } else {
            initialGenes[3] = (1);
        }

        //Attack gene [ wildtype, stronger1, stronger2]
        if(ThreadLocalRandom.current().nextInt(100)>WTC){
            initialGenes[4] = (ThreadLocalRandom.current().nextInt(3)+1);

        } else {
            initialGenes[4] = (1);
        }
        if(ThreadLocalRandom.current().nextInt(100)>WTC){
            initialGenes[5] = (ThreadLocalRandom.current().nextInt(3)+1);

        } else {
            initialGenes[5] = (1);
        }

        //Dominant White [ dominant white, wildtype ]
        if(ThreadLocalRandom.current().nextInt(100)>WTC){
            initialGenes[6] = (ThreadLocalRandom.current().nextInt(2)+1);

        } else {
            initialGenes[6] = (2);
        }
        if(ThreadLocalRandom.current().nextInt(100)>WTC){
            initialGenes[7] = (ThreadLocalRandom.current().nextInt(2)+1);

        } else {
            initialGenes[7] = (2);
        }

        //Roan [ roan, wildtype ]
        if(ThreadLocalRandom.current().nextInt(100)>WTC){
            initialGenes[8] = (ThreadLocalRandom.current().nextInt(2)+1);

        } else {
            initialGenes[8] = (2);
        }
        if(ThreadLocalRandom.current().nextInt(100)>WTC){
            initialGenes[9] = (ThreadLocalRandom.current().nextInt(2)+1);

        } else {
            initialGenes[9] = (2);
        }

        //Piebald [ piebald, wildtype ]
        if(ThreadLocalRandom.current().nextInt(100)>WTC){
            initialGenes[10] = (ThreadLocalRandom.current().nextInt(2)+1);

        } else {
            initialGenes[10] = (1);
        }
        if(ThreadLocalRandom.current().nextInt(100)>WTC){
            initialGenes[11] = (ThreadLocalRandom.current().nextInt(2)+1);

        } else {
            initialGenes[11] = (1);
        }

        //Tuxedo [ tuxedo, wildtype ]
        if(ThreadLocalRandom.current().nextInt(100)>WTC){
            initialGenes[12] = (ThreadLocalRandom.current().nextInt(2)+1);
        } else {
            initialGenes[12] = (2);
        }
        if(ThreadLocalRandom.current().nextInt(100)>WTC){
            initialGenes[13] = (ThreadLocalRandom.current().nextInt(2)+1);

        } else {
            initialGenes[13] = (2);
        }

        //Extention [ black, wildtype, self ]
        if(ThreadLocalRandom.current().nextInt(100)>WTC){
            initialGenes[14] = (ThreadLocalRandom.current().nextInt(3)+1);

        } else {
            initialGenes[14] = (2);
        }
        if(ThreadLocalRandom.current().nextInt(100)>WTC){
            initialGenes[15] = (ThreadLocalRandom.current().nextInt(3)+1);

        } else {
            initialGenes[15] = (2);
        }

        //Agouti [ PaleShaded, Shaded, RedTrimmedBlack, Bay, Mahogany, BlackTan, rBlack]
        if(ThreadLocalRandom.current().nextInt(100)>WTC){
            initialGenes[16] = (ThreadLocalRandom.current().nextInt(7)+1);

        } else {
            initialGenes[16] = (2);
        }
        if(ThreadLocalRandom.current().nextInt(100)>WTC){
            initialGenes[17] = (ThreadLocalRandom.current().nextInt(7)+1);

        } else {
            initialGenes[17] = (2);
        }

        //Banana Ears genes [ no banana, banana, bananaless ]
        if(ThreadLocalRandom.current().nextInt(100)>WTC){
            initialGenes[18] = (ThreadLocalRandom.current().nextInt(3)+1);

        } else {
            initialGenes[18] = (2);
        }
        if(ThreadLocalRandom.current().nextInt(100)>WTC){
            initialGenes[19] = (ThreadLocalRandom.current().nextInt(3)+1);

        } else {
            initialGenes[19] = (2);
        }


        return initialGenes;
    }

    public void setGenes(int[] genes) {
        this.genes = genes;
    }

    public int[] getGenes() {
        return this.genes;
    }


    private void spit(EntityLivingBase target) {
        EnhancedEntityLlamaSpit entityllamaspit = new EnhancedEntityLlamaSpit(this.world, this);
        double d0 = target.posX - this.posX;
        double d1 = target.getBoundingBox().minY + (double)(target.height / 3.0F) - entityllamaspit.posY;
        double d2 = target.posZ - this.posZ;
        float f = MathHelper.sqrt(d0 * d0 + d2 * d2) * 0.2F;
        entityllamaspit.shoot(d0, d1 + (double)f, d2, 1.5F, 10.0F);
        this.world.playSound((EntityPlayer)null, this.posX, this.posY, this.posZ, SoundEvents.ENTITY_LLAMA_SPIT, this.getSoundCategory(), 1.0F, 1.0F + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F);
        this.world.spawnEntity(entityllamaspit);
        this.didSpit = true;
    }

    private void setDidSpit(boolean didSpitIn) {
        this.didSpit = didSpitIn;
    }

    public void fall(float distance, float damageMultiplier) {
        int i = MathHelper.ceil((distance * 0.5F - 3.0F) * damageMultiplier);
        if (i > 0) {
            if (distance >= 6.0F) {
                this.attackEntityFrom(DamageSource.FALL, (float)i);
                if (this.isBeingRidden()) {
                    for(Entity entity : this.getRecursivePassengers()) {
                        entity.attackEntityFrom(DamageSource.FALL, (float)i);
                    }
                }
            }

            IBlockState iblockstate = this.world.getBlockState(new BlockPos(this.posX, this.posY - 0.2D - (double)this.prevRotationYaw, this.posZ));
            Block block = iblockstate.getBlock();
            if (!iblockstate.isAir() && !this.isSilent()) {
                SoundType soundtype = block.getSoundType();
                this.world.playSound((EntityPlayer)null, this.posX, this.posY, this.posZ, soundtype.getStepSound(), this.getSoundCategory(), soundtype.getVolume() * 0.5F, soundtype.getPitch() * 0.75F);
            }

        }
    }

    public void leaveCaravan() {
        if (this.caravanHead != null) {
            this.caravanHead.caravanTail = null;
        }

        this.caravanHead = null;
    }

    public void joinCaravan(EnhancedLlama caravanHeadIn) {
        this.caravanHead = caravanHeadIn;
        this.caravanHead.caravanTail = this;
    }

    public boolean hasCaravanTrail() {
        return this.caravanTail != null;
    }

    public boolean inCaravan() {
        return this.caravanHead != null;
    }

    @Nullable
    public EnhancedLlama getCaravanHead() {
        return this.caravanHead;
    }

    protected double followLeashSpeed() {
        return 2.0D;
    }

    protected void followMother() {
        if (!this.inCaravan() && this.isChild()) {
            if (this.isBreeding() && this.isChild() && !this.isEatingHaystack()) {
                EnhancedLlama llama = this.getClosestHorse(this, 16.0D);
                if (llama != null && this.getDistanceSq(llama) > 4.0D) {
                    this.navigator.getPathToEntityLiving(llama);
                }
            }
        }
    }
    public boolean isBreeding() {
        return this.getHorseWatchableBoolean(8);
    }

    public boolean isEatingHaystack() {
        return this.getHorseWatchableBoolean(16);
    }

    protected boolean getHorseWatchableBoolean(int p_110233_1_) {
        return (this.dataManager.get(STATUS) & p_110233_1_) != 0;
    }

    public boolean isTame() {
        return this.getHorseWatchableBoolean(2);
    }

    public void makeMad() {
        this.makeHorseRear();
        SoundEvent soundevent = this.getAngrySound();
        if (soundevent != null) {
            this.playSound(soundevent, this.getSoundVolume(), this.getSoundPitch());
        }

    }

    private void makeHorseRear() {
        if (this.canPassengerSteer() || this.isServerWorld()) {
            this.jumpRearingCounter = 1;
            this.setRearing(true);
        }

    }

    public void setRearing(boolean rearing) {
        if (rearing) {
            this.setEatingHaystack(false);
        }

        this.setHorseWatchableBoolean(32, rearing);
    }

    public void setEatingHaystack(boolean p_110227_1_) {
        this.setHorseWatchableBoolean(16, p_110227_1_);
    }

    protected void setHorseWatchableBoolean(int p_110208_1_, boolean p_110208_2_) {
        byte b0 = this.dataManager.get(STATUS);
        if (p_110208_2_) {
            this.dataManager.set(STATUS, (byte)(b0 | p_110208_1_));
        } else {
            this.dataManager.set(STATUS, (byte)(b0 & ~p_110208_1_));
        }

    }

    @Nullable
    protected SoundEvent getAngrySound() {
        this.makeHorseRear();
        return null;
    }

    public int increaseTemper(int p_110198_1_) {
        int i = MathHelper.clamp(this.getTemper() + p_110198_1_, 0, this.getMaxTemper());
        this.setTemper(i);
        return i;
    }

    public int getMaxTemper() {
        return 100;
    }

    public int getTemper() {
        return this.temper;
    }

    public void setTemper(int temperIn) {
        this.temper = temperIn;
    }

    public boolean setTamedBy(EntityPlayer player) {
        this.setOwnerUniqueId(player.getUniqueID());
        this.setHorseTamed(true);
        if (player instanceof EntityPlayerMP) {
            CriteriaTriggers.TAME_ANIMAL.trigger((EntityPlayerMP)player, this);
        }

        this.world.setEntityState(this, (byte)7);
        return true;
    }

    public void setOwnerUniqueId(@Nullable UUID uniqueId) {
        this.dataManager.set(OWNER_UNIQUE_ID, Optional.ofNullable(uniqueId));
    }

    public void setHorseTamed(boolean tamed) {
        this.setHorseWatchableBoolean(2, tamed);
    }

    @Nullable
    protected EnhancedLlama getClosestHorse(Entity entityIn, double distance) {
        double d0 = Double.MAX_VALUE;
        Entity entity = null;

        for(Entity entity1 : this.world.getEntitiesInAABBexcluding(entityIn, entityIn.getBoundingBox().expand(distance, distance, distance), IS_BREEDING)) {
            double d1 = entity1.getDistanceSq(entityIn.posX, entityIn.posY, entityIn.posZ);
            if (d1 < d0) {
                entity = entity1;
                d0 = d1;
            }
        }

        return (EnhancedLlama)entity;
    }

    @Nullable
    protected EnhancedLlama getClosestLlama(Entity entityIn, double distance) {
        double d0 = Double.MAX_VALUE;
        Entity entity = null;

        for(Entity entity1 : this.world.getEntitiesInAABBexcluding(entityIn, entityIn.getBoundingBox().expand(distance, distance, distance), IS_BREEDING)) {
            double d1 = entity1.getDistanceSq(entityIn.posX, entityIn.posY, entityIn.posZ);
            if (d1 < d0) {
                entity = entity1;
                d0 = d1;
            }
        }

        return (EnhancedLlama)entity;
    }

    public boolean canEatGrass() {
        return false;
    }

    /**
     * Attack the specified entity using a ranged attack.
     */
    public void attackEntityWithRangedAttack(EntityLivingBase target, float distanceFactor) {
        this.spit(target);
    }

    public void setSwingingArms(boolean swingingArms) {
    }

    static class AIDefendTarget extends EntityAINearestAttackableTarget<EntityWolf> {
        public AIDefendTarget(EnhancedLlama llama) {
            super(llama, EntityWolf.class, 16, false, true, (Predicate<EntityWolf>)null);
        }

        /**
         * Returns whether the EntityAIBase should begin execution.
         */
        public boolean shouldExecute() {
            if (super.shouldExecute() && this.targetEntity != null && !this.targetEntity.isTamed()) {
                return true;
            } else {
                this.taskOwner.setAttackTarget((EntityLivingBase)null);
                return false;
            }
        }

        protected double getTargetDistance() {
            return super.getTargetDistance() * 0.25D;
        }
    }

    static class AIHurtByTarget extends EntityAIHurtByTarget {
        public AIHurtByTarget(EnhancedLlama llama) {
            super(llama, false);
        }

        /**
         * Returns whether an in-progress EntityAIBase should continue executing
         */
        public boolean shouldContinueExecuting() {
            if (this.taskOwner instanceof EnhancedLlama) {
                EnhancedLlama entityllama = (EnhancedLlama)this.taskOwner;
                if (entityllama.didSpit) {
                    entityllama.setDidSpit(false);
                    return false;
                }
            }

            return super.shouldContinueExecuting();
        }
    }

    public static class GroupData implements IEntityLivingData {

        public int[] groupGenes;

        public GroupData(int[] groupGenes) {
            this.groupGenes = groupGenes;
        }

    }
}