package mokiyoki.enhancedanimals.entity;

import mokiyoki.enhancedanimals.items.DebugGenesBook;
import mokiyoki.enhancedanimals.util.handlers.ConfigHandler;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
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

import static mokiyoki.enhancedanimals.util.handlers.RegistryHandler.ENHANCED_COW;

public class EnhancedCow extends EntityAnimal {

    private static final DataParameter<String> SHARED_GENES = EntityDataManager.<String>createKey(EnhancedCow.class, DataSerializers.STRING);
    
    //TODO put texture variations here
    private static final String[] COW_TEXTURES_TEST = new String[] {
            "cowbase.png"
    };

    private static final Ingredient TEMPTATION_ITEMS = Ingredient.fromItems(Blocks.MELON, Blocks.PUMPKIN, Blocks.GRASS, Blocks.HAY_BLOCK, Items.CARROT, Items.WHEAT);

    private static final int WTC = 90;
    private final List<String> cowTextures = new ArrayList<>();
    private static final int GENES_LENGTH = 36;
    private int[] genes = new int[GENES_LENGTH];
    private int[] mateGenes = new int[GENES_LENGTH];
    private int[] mitosisGenes = new int[GENES_LENGTH];
    private int[] mateMitosisGenes = new int[GENES_LENGTH];

    public EnhancedCow(World worldIn) {
        super(ENHANCED_COW, worldIn);
        this.setSize(0.4F, 1F);

    }

    private int cowTimer;
    private EntityAIEatGrass entityAIEatGrass;

    private int gestationTimer = 0;
    private boolean pregnant = false;

    protected void initEntityAI() {
        this.entityAIEatGrass = new EntityAIEatGrass(this);
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIPanic(this, 1.25D));
        this.tasks.addTask(2, new EntityAIMate(this, 1.0D));
        this.tasks.addTask(3, new EntityAITempt(this, 1.1D, TEMPTATION_ITEMS, false));
        this.tasks.addTask(4, new EntityAIFollowParent(this, 1.1D));
        this.tasks.addTask(5, this.entityAIEatGrass);
        this.tasks.addTask(6, new EntityAIWanderAvoidWater(this, 1.0D));
        this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
    }

    protected void updateAITasks()
    {
        this.cowTimer = this.entityAIEatGrass.getEatingGrassTimer();
        super.updateAITasks();
    }

    protected void registerData() {
        super.registerData();
        this.dataManager.register(SHARED_GENES, new String());
    }

    @Nullable
    protected ResourceLocation getLootTable() { return LootTableList.ENTITIES_COW; }

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

                    EnhancedCow enhancedcow = new EnhancedCow(this.world);
                    enhancedcow.setGrowingAge(0);
                    int[] babyGenes = getCalfGenes();
                    enhancedcow.setGenes(babyGenes);
                    enhancedcow.setSharedGenes(babyGenes);
                    enhancedcow.setGrowingAge(-24000);
                    enhancedcow.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0F);
                    this.world.spawnEntity(enhancedcow);

                }
            }
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

    public EntityAgeable createChild(EntityAgeable ageable) {
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
    public String[] getVariantTexturePaths()
    {
        if (this.cowTextures.isEmpty()) {
            this.setTexturePaths();
        }

        return this.cowTextures.stream().toArray(String[]::new);
    }

    @OnlyIn(Dist.CLIENT)
    private void setTexturePaths() {

        this.cowTextures.add(COW_TEXTURES_TEST[0]);

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
    public boolean processInteract(EntityPlayer entityPlayer, EnumHand hand) {
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

    public void writeAdditional(NBTTagCompound compound) {
        super.writeAdditional(compound);

        //store this cows's genes
        NBTTagList geneList = new NBTTagList();
        for (int i = 0; i < genes.length; i++) {
            NBTTagCompound nbttagcompound = new NBTTagCompound();
            nbttagcompound.setInt("Gene", genes[i]);
            geneList.add(nbttagcompound);
        }
        compound.setTag("Genes", geneList);

        //store this cows's mate's genes
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
        //TODO create biome WTC variable [hot and dry biomes, hot and wet biomes, cold biomes] WTC is all others


        //[ 0=minecraft wildtype, 1=jungle wildtype, 2=savanna wildtype, 3=cold wildtype, 4=swamp wildtype ]
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

        //Agouti [ Black enhancer, Wildtype+, lavender dilution, white ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[2] = (ThreadLocalRandom.current().nextInt(4) + 1);

        } else {
            initialGenes[2] = (2);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[3] = (ThreadLocalRandom.current().nextInt(4) + 1);

        } else {
            initialGenes[3] = (2);
        }

        //Dilution [ Blue dilution, Dun, wildtype]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[4] = (ThreadLocalRandom.current().nextInt(3) + 1);

        } else {
            initialGenes[4] = (3);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[5] = (ThreadLocalRandom.current().nextInt(4) + 1);

        } else {
            initialGenes[5] = (3);
        }

        //Brindle [ Brindle, wildtype+ ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC || true) {
            initialGenes[6] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[6] = (2);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[7] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[7] = (2);
        }

        //Charolais Dilution [ dilute, wildtype ] incomplete dominant
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[8] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[8] = (2);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[9] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[9] = (2);
        }

        //Spots
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[10] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[10] = (2);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[11] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[11] = (2);
        }

        //Horns [normal, polled, scured, africanhorn]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[12] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[12] = (2);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[13] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[13] = (2);
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
