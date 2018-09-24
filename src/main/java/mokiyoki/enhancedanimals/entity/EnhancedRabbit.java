package mokiyoki.enhancedanimals.entity;

import com.google.common.collect.Sets;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class EnhancedRabbit extends EntityAnimal {

    private static final DataParameter<String> SHARED_GENES = EntityDataManager.<String>createKey(EnhancedChicken.class, DataSerializers.STRING);

    private static final Set<Item> TEMPTATION_ITEMS = Sets.newHashSet(Item.getItemFromBlock(Blocks.YELLOW_FLOWER), Item.getItemFromBlock(Blocks.TALLGRASS), Items.CARROT);

    private static final int WTC = 90;
    private static final int GENES_LENGTH = 50;
    private int[] genes = new int[GENES_LENGTH];
    private int[] mateGenes = new int[GENES_LENGTH];
    private int[] mitosisGenes = new int[GENES_LENGTH];
    private int[] mateMitosisGenes = new int[GENES_LENGTH];

    public EnhancedRabbit(World worldIn) {
        super(worldIn);
        this.setSize(0.4F, 0.5F);
        //TODO Add the jumping stuff
    }

    protected void initEntityAI() {

    }

    //TODO put the rest of the jumping stuff here

    protected void entityInit() {
        super.entityInit();
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

    public EntityAgeable createChild(EntityAgeable ageable) {
//
//    }
        this.mateGenes = ((EnhancedChicken) ageable).getGenes();
        mixMateMitosisGenes();
        mixMitosisGenes();
        EnhancedRabbit enhancedrabbit = new EnhancedRabbit(this.world);
        enhancedrabbit.setGrowingAge(0);
        int[] babyGenes = getBunnyGenes();
        enhancedrabbit.setGenes(babyGenes);
        enhancedrabbit.setSharedGenes(babyGenes);
        return enhancedrabbit;
    }

    public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);

        //store this rabbits's genes
        NBTTagList geneList = new NBTTagList();
        for (int i = 0; i < genes.length; i++) {
            NBTTagCompound nbttagcompound = new NBTTagCompound();
            nbttagcompound.setInteger("Gene", genes[i]);
            geneList.appendTag(nbttagcompound);
        }
        compound.setTag("Genes", geneList);

        //store this rabbits's mate's genes
        NBTTagList mateGeneList = new NBTTagList();
        for (int i = 0; i < mateGenes.length; i++) {
            NBTTagCompound nbttagcompound = new NBTTagCompound();
            nbttagcompound.setInteger("Gene", mateGenes[i]);
            mateGeneList.appendTag(nbttagcompound);
        }
        compound.setTag("FatherGenes", mateGeneList);
    }

    @SideOnly(Side.CLIENT)
    private void setTexturePaths() {
        int[] genesForText = getSharedGenes();
        if (genesForText != null) {



        }
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);

        NBTTagList geneList = compound.getTagList("Genes", 10);
        for (int i = 0; i < geneList.tagCount(); ++i) {
            NBTTagCompound nbttagcompound = geneList.getCompoundTagAt(i);
            int gene = nbttagcompound.getInteger("Gene");
            genes[i] = gene;
        }

        NBTTagList mateGeneList = compound.getTagList("FatherGenes", 10);
        for (int i = 0; i < mateGeneList.tagCount(); ++i) {
            NBTTagCompound nbttagcompound = mateGeneList.getCompoundTagAt(i);
            int gene = nbttagcompound.getInteger("Gene");
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
        for (int i = 20; i < genes.length; i = (i + 2)) {
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


    public int[] getBunnyGenes() {
        Random rand = new Random();
        int[] bunnyGenes = new int[GENES_LENGTH];

        for (int i = 0; i < 20; i++) {
            boolean thisOrMate = rand.nextBoolean();
            if (thisOrMate) {
                bunnyGenes[i] = genes[i];
            } else {
                bunnyGenes[i] = mateGenes[i];
            }
        }

        for (int i = 20; i < genes.length; i++) {
            boolean thisOrMate = rand.nextBoolean();
            if (thisOrMate) {
                bunnyGenes[i] = mitosisGenes[i];
            } else {
                bunnyGenes[i] = mateMitosisGenes[i];
            }
        }

        return bunnyGenes;
    }

    @Nullable
    @Override
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata) {
        livingdata = super.onInitialSpawn(difficulty, livingdata);
        int[] spawnGenes;

        if (livingdata instanceof EnhancedChicken.GroupData) {
            spawnGenes = ((EnhancedChicken.GroupData) livingdata).groupGenes;
        } else {
            spawnGenes = createInitialGenes();
            livingdata = new EnhancedChicken.GroupData(spawnGenes);
        }

        this.genes = spawnGenes;
        setSharedGenes(genes);
        return livingdata;
    }

    private int[] createInitialGenes() {
        int[] initialGenes = new int[GENES_LENGTH];
        //TODO create biome WTC variable [hot and dry biomes, hot and wet biomes, cold biomes] WTC is all others


        //[ 0=minecraft wildtype, 1=jungle wildtype, 2=savanna wildtype, 3=cold wildtype, 4=swamp wildtype ]
        int wildType = 0;
        Biome biome = this.world.getBiome(new BlockPos(this));

        if (biome.getDefaultTemperature() >= 0.9F && biome.getRainfall() > 0.8F) // hot and wet (jungle)
        {
            wildType = 1;
        }


/**
 * Genes List
 */

        /**
         * Colour Genes
         */

        //Agouti [ Agouti, Tan, Self ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[0] = (ThreadLocalRandom.current().nextInt(3) + 1);

        } else {
            initialGenes[0] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[1] = (ThreadLocalRandom.current().nextInt(3) + 1);

        } else {
            initialGenes[1] = (1);
        }

        //Brown/Chocolate [ wildtype, brown ]
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

        //Colour Completion [ Wildtype+, Dark Chinchilla, Light Chinchilla, Pale Chinchilla, Himalayan, Albino ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[4] = (ThreadLocalRandom.current().nextInt(6) + 1);

        } else {
            initialGenes[4] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[5] = (ThreadLocalRandom.current().nextInt(6) + 1);

        } else {
            initialGenes[5] = (1);
        }

        //Dilute [ wildtype, dilute ]
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

        //E Locus [ Dominant Black, Steel, Wildtype, Japanese Brindle, Non Extension ]
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

        //Spotted [ wildtype, spotted ]
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

        //Dutch [ wildtype, dutch]
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

        //Vienna [ wildtype, vienna]
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

        //Wideband [ wildtype, wideband]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[14] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[14] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[15] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[15] = (1);
        }

        //Silver [ wildtype, silver]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[16] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[16] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[17] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[17] = (1);
        }

        //Lutino [ wildtype, lutino]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[18] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[18] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[19] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[19] = (1);
        }

        /**
         * Coat Genes
         */

        //Furless [ wildtype, furless]
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

        //Lion Mane [ wildtype, lion mane]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[22] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[22] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[23] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[23] = (1);
        }

        //Angora [ wildtype, dutch]
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

        //Rex [ wildtype, dutch]
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

        //Satin [ wildtype, dutch]
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

        //Waved [ wildtype, dutch]
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

        /**
         * Shape and Size Genes
         */

        //Dwarf [ wildtype, dwarf]
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

        //Lop1 [ wildtype, halflop, lop1]
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

        //Lop2 [ wildtype, lop2]
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

        //long ears [ wildtype, longer ears, longest ears]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[38] = (ThreadLocalRandom.current().nextInt(3) + 1);

        } else {
            initialGenes[38] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[39] = (ThreadLocalRandom.current().nextInt(3) + 1);

        } else {
            initialGenes[39] = (1);
        }

        //ear length bias [ normal ears, shorter ears, longest ears ]
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

        //longer body [ normal length, longer body ]
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

        //Size tendency [ small, normal, small2, big, extra large ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[44] = (ThreadLocalRandom.current().nextInt(5) + 1);

        } else {
            initialGenes[44] = (2);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[45] = (ThreadLocalRandom.current().nextInt(5) + 1);

        } else {
            initialGenes[45] = (2);
        }

        //Size Enhancer [ big, normal, small ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[46] = (ThreadLocalRandom.current().nextInt(3) + 1);

        } else {
            initialGenes[46] = (2);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[47] = (ThreadLocalRandom.current().nextInt(3) + 1);

        } else {
            initialGenes[47] = (2);
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
