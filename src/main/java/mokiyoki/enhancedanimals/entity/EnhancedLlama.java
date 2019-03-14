package mokiyoki.enhancedanimals.entity;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
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
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import static mokiyoki.enhancedanimals.util.handlers.RegistryHandler.ENHANCED_LLAMA;

public class EnhancedLlama extends EntityAnimal {

    private static final DataParameter<String> SHARED_GENES = EntityDataManager.<String>createKey(EnhancedLlama.class, DataSerializers.STRING);

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

    private static final Ingredient TEMPTATION_ITEMS = Ingredient.fromItems(Items.DANDELION_YELLOW, Items.CARROT, Items.GOLDEN_CARROT);

    private final List<String> llamaTextures = new ArrayList<>();

    public float destPos;

    private static final int WTC = 90;
    private static final int GENES_LENGTH = 18;
    private int[] genes = new int[GENES_LENGTH];
    private int[] mateGenes = new int[GENES_LENGTH];
    private int[] mitosisGenes = new int[GENES_LENGTH];
    private int[] mateMitosisGenes = new int[GENES_LENGTH];

    public EnhancedLlama(World worldIn) {
        super(ENHANCED_LLAMA, worldIn);
        this.setSize(0.4F, 0.5F);
        this.setPathPriority(PathNodeType.WATER, 0.0F);
        //TODO Add the jumping stuff
    }

    protected void initEntityAI() {
        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAIMate(this, 0.8D));
        this.tasks.addTask(3, new EntityAITempt(this, 1.0D, TEMPTATION_ITEMS, false));
        this.tasks.addTask(6, new EntityAIWanderAvoidWater(this, 0.6D));
        this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 10.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
    }

    //TODO put the rest of the jumping stuff here

    protected void registerData() {
        super.registerData();
        this.dataManager.register(SHARED_GENES, new String());
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

    public void fall(float distance, float damageMultiplier)
    {

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

    public boolean isBreedingItem(ItemStack stack) {
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
        return this.llamaTextures.stream().collect(Collectors.joining(", ","[","]"));

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
            String uuid = getCachedUniqueIdString();


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
                }else if ( genesForText[16] == 1 || genesForText[17] == 1 ){
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

                if ( Character.isDigit(uuid.charAt(1)) ){
                    if (uuid.charAt(1) < 5 ){
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

                if ( Character.isDigit(uuid.charAt(2)) ){
                    roan = 1 + uuid.charAt(2);
                } else {
                    char d = uuid.charAt(2);

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

                if ( Character.isDigit(uuid.charAt(4)) ){
                    piebald = 1 + uuid.charAt(4);
                } else {
                    char d = uuid.charAt(4);

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

                if ( Character.isDigit(uuid.charAt(6)) ){
                    tux = 1 + uuid.charAt(6);
                } else {
                    char d = uuid.charAt(6);

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
            initialGenes[10] = (2);
        }
        if(ThreadLocalRandom.current().nextInt(100)>WTC){
            initialGenes[11] = (ThreadLocalRandom.current().nextInt(2)+1);

        } else {
            initialGenes[11] = (2);
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
