package mokiyoki.enhancedanimals.entity;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import static mokiyoki.enhancedanimals.util.handlers.RegistryHandler.ENHANCED_SHEEP;

public class EnhancedSheep extends EntityAnimal implements net.minecraftforge.common.IShearable{

    private static final DataParameter<String> SHARED_GENES = EntityDataManager.<String>createKey(EnhancedSheep.class, DataSerializers.STRING);
    private static final DataParameter<Byte> DYE_COLOR = EntityDataManager.<Byte>createKey(EnhancedSheep.class, DataSerializers.BYTE);

    /**
     * Internal crafting inventory used to check the result of mixing dyes corresponding to the fleece color when
     * breeding sheep.
     */
    private final InventoryCrafting inventoryCrafting = new InventoryCrafting(new Container() {
        /**
         * Determines whether supplied player can use this container
         */
        public boolean canInteractWith(EntityPlayer playerIn) {
            return false;
        }
    }, 2, 1);
    private static final Map<EnumDyeColor, IItemProvider> WOOL_BY_COLOR = Util.make(Maps.newEnumMap(EnumDyeColor.class), (woolMap) -> {
        woolMap.put(EnumDyeColor.WHITE, Blocks.WHITE_WOOL);
        woolMap.put(EnumDyeColor.ORANGE, Blocks.ORANGE_WOOL);
        woolMap.put(EnumDyeColor.MAGENTA, Blocks.MAGENTA_WOOL);
        woolMap.put(EnumDyeColor.LIGHT_BLUE, Blocks.LIGHT_BLUE_WOOL);
        woolMap.put(EnumDyeColor.YELLOW, Blocks.YELLOW_WOOL);
        woolMap.put(EnumDyeColor.LIME, Blocks.LIME_WOOL);
        woolMap.put(EnumDyeColor.PINK, Blocks.PINK_WOOL);
        woolMap.put(EnumDyeColor.GRAY, Blocks.GRAY_WOOL);
        woolMap.put(EnumDyeColor.LIGHT_GRAY, Blocks.LIGHT_GRAY_WOOL);
        woolMap.put(EnumDyeColor.CYAN, Blocks.CYAN_WOOL);
        woolMap.put(EnumDyeColor.PURPLE, Blocks.PURPLE_WOOL);
        woolMap.put(EnumDyeColor.BLUE, Blocks.BLUE_WOOL);
        woolMap.put(EnumDyeColor.BROWN, Blocks.BROWN_WOOL);
        woolMap.put(EnumDyeColor.GREEN, Blocks.GREEN_WOOL);
        woolMap.put(EnumDyeColor.RED, Blocks.RED_WOOL);
        woolMap.put(EnumDyeColor.BLACK, Blocks.BLACK_WOOL);
    });



    private static final Ingredient TEMPTATION_ITEMS = Ingredient.fromItems(Blocks.MELON, Blocks.PUMPKIN, Blocks.GRASS, Blocks.HAY_BLOCK, Items.CARROT, Items.WHEAT);

    /** Map from EnumDyeColor to RGB values for passage to GlStateManager.color() */
    private static final Map<EnumDyeColor, float[]> DYE_TO_RGB = Maps.newEnumMap(EnumDyeColor.class);

    private static final int WTC = 90;
    private final List<String> sheepTextures = new ArrayList<>();
    private final List<String> sheepFleeceTextures = new ArrayList<>();
    private static final int GENES_LENGTH = 50;
    private int[] genes = new int[GENES_LENGTH];
    private int[] mateGenes = new int[GENES_LENGTH];
    private int[] mitosisGenes = new int[GENES_LENGTH];
    private int[] mateMitosisGenes = new int[GENES_LENGTH];

    public EnhancedSheep(World worldIn) {
        super(ENHANCED_SHEEP, worldIn);
        this.setSize(0.4F, 1F);

    }

    public float getEyeHeight()
    {
        return this.height;
    }

    private int sheepTimer;
    private EntityAIEatGrass entityAIEatGrass;

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

    //TODO put new sheep behaviour here

    protected void updateAITasks()
    {
        this.sheepTimer = this.entityAIEatGrass.getEatingGrassTimer();
        super.updateAITasks();
    }

    protected void registerData() {
        super.registerData();
        this.dataManager.register(SHARED_GENES, new String());
        this.dataManager.register(DYE_COLOR, Byte.valueOf((byte)0));
    }

    protected void registerAttributes() {
        super.registerAttributes();
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(8.0D);
        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.23000000417232513D);
    }

    public void livingTick() {
        if (this.world.isRemote) {
            this.sheepTimer = Math.max(0, this.sheepTimer - 1);
        }

        super.livingTick();
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

    public EnumDyeColor getFleeceColor() {
        return EnumDyeColor.byId(this.dataManager.get(DYE_COLOR) & 15);
    }

    /**
     * Sets the wool color of this sheep
     */
    public void setFleeceColor(EnumDyeColor color) {
        byte b0 = this.dataManager.get(DYE_COLOR);
        this.dataManager.set(DYE_COLOR, (byte)(b0 & 240 | color.getId() & 15));
    }

    @OnlyIn(Dist.CLIENT)
    public static float[] getDyeRgb(EnumDyeColor dyeColor)
    {
        return DYE_TO_RGB.get(dyeColor);
    }

    public EntityAgeable createChild(EntityAgeable ageable) {
//
//    }
        this.mateGenes = ((EnhancedSheep) ageable).getGenes();
        mixMateMitosisGenes();
        mixMitosisGenes();
        EnhancedSheep enhancedsheep = new EnhancedSheep(this.world);
        enhancedsheep.setGrowingAge(0);
        int[] babyGenes = getLambGenes();
        enhancedsheep.setGenes(babyGenes);
        enhancedsheep.setSharedGenes(babyGenes);
        return enhancedsheep;
    }

    private static float[] createSheepColor(EnumDyeColor dyeColor)
    {
        float[] afloat = dyeColor.getColorComponentValues();
        float f = 0.75F;
        return new float[] {afloat[0] * 0.75F, afloat[1] * 0.75F, afloat[2] * 0.75F};
    }

    public boolean getSheared()
    {
        return (this.dataManager.get(DYE_COLOR) & 16) != 0;
    }

    @OnlyIn(Dist.CLIENT)
    public String getSheepTexture() {
        if (this.sheepTextures.isEmpty()) {
            this.setTexturePaths();
        }
        return this.sheepTextures.stream().collect(Collectors.joining(", ","[","]"));

    }

    @OnlyIn(Dist.CLIENT)
    public String[] getVariantTexturePaths()
    {
        if (this.sheepTextures.isEmpty()) {
            this.setTexturePaths();
        }

        return this.sheepTextures.stream().toArray(String[]::new);
    }

    @OnlyIn(Dist.CLIENT)
    private void setTexturePaths() {
        this.sheepTextures.add("sheep.png");
    }

    @OnlyIn(Dist.CLIENT)
    public String getSheepFleeceTexture() {
        if (this.sheepFleeceTextures.isEmpty()) {
            this.setFleeceTexturePaths();
        }
        return this.sheepFleeceTextures.stream().collect(Collectors.joining(", ","[","]"));

    }

    @OnlyIn(Dist.CLIENT)
    public String[] getVariantFleeceTexturePaths()
    {
        if (this.sheepFleeceTextures.isEmpty()) {
            this.setFleeceTexturePaths();
        }

        return this.sheepFleeceTextures.stream().toArray(String[]::new);
    }

    @OnlyIn(Dist.CLIENT)
    private void setFleeceTexturePaths() {
        this.sheepFleeceTextures.add("sheep_wool.png");
    }

    @OnlyIn(Dist.CLIENT)
    public void handleStatusUpdate(byte id)
    {
        if (id == 10)
        {
            this.sheepTimer = 40;
        }
        else
        {
            super.handleStatusUpdate(id);
        }
    }


    //EATING???

    @OnlyIn(Dist.CLIENT)
    public float getHeadRotationPointY(float partialTickTime)
    {
        if (this.sheepTimer <= 0)
        {
            return 0.0F;
        }
        else if (this.sheepTimer >= 4 && this.sheepTimer <= 36)
        {
            return 1.0F;
        }
        else
        {
            return this.sheepTimer < 4 ? ((float)this.sheepTimer - partialTickTime) / 4.0F : -((float)(this.sheepTimer - 40) - partialTickTime) / 4.0F;
        }
    }
    //EATING???
    @OnlyIn(Dist.CLIENT)
    public float getHeadRotationAngleX(float partialTickTime)
    {
        if (this.sheepTimer > 4 && this.sheepTimer <= 36)
        {
            float f = ((float)(this.sheepTimer - 4) - partialTickTime) / 32.0F;
            return ((float)Math.PI / 5F) + ((float)Math.PI * 7F / 100F) * MathHelper.sin(f * 28.7F);
        }
        else
        {
            return this.sheepTimer > 0 ? ((float)Math.PI / 5F) : this.rotationPitch * 0.017453292F;
        }
    }


    public boolean processInteract(EntityPlayer player, EnumHand hand) {
    ItemStack itemstack = player.getHeldItem(hand);
      if (false && itemstack.getItem() == Items.SHEARS && !this.getSheared() && !this.isChild()) { //Forge: Moved to onSheared
        if (!this.world.isRemote) {
            this.setSheared(true);
            int i = 1 + this.rand.nextInt(3);

            for(int j = 0; j < i; ++j) {
                EntityItem entityitem = this.entityDropItem(WOOL_BY_COLOR.get(this.getFleeceColor()), 1);
                if (entityitem != null) {
                    entityitem.motionY += (double)(this.rand.nextFloat() * 0.05F);
                    entityitem.motionX += (double)((this.rand.nextFloat() - this.rand.nextFloat()) * 0.1F);
                    entityitem.motionZ += (double)((this.rand.nextFloat() - this.rand.nextFloat()) * 0.1F);
                }
            }
        }

        itemstack.damageItem(1, player);
        this.playSound(SoundEvents.ENTITY_SHEEP_SHEAR, 1.0F, 1.0F);
    }

      return super.processInteract(player, hand);
    }

    /**
     * make a sheep sheared if set to true
     */
    public void setSheared(boolean sheared) {
        byte b0 = this.dataManager.get(DYE_COLOR);
        if (sheared) {
            this.dataManager.set(DYE_COLOR, (byte)(b0 | 16));
        } else {
            this.dataManager.set(DYE_COLOR, (byte)(b0 & -17));
        }
    }

    @Override
    public boolean isShearable(ItemStack item, net.minecraft.world.IWorldReader world, BlockPos pos) {
        return !this.getSheared() && !this.isChild();
    }

    @Override
    public java.util.List<ItemStack> onSheared(ItemStack item, net.minecraft.world.IWorld world, BlockPos pos, int fortune) {
        java.util.List<ItemStack> ret = new java.util.ArrayList<>();
        if (!this.world.isRemote) {
            this.setSheared(true);
            int i = 1 + this.rand.nextInt(3);

            for(int j = 0; j < i; ++j) {
                ret.add(new ItemStack(WOOL_BY_COLOR.get(this.getFleeceColor())));
            }
        }
        return ret;
    }


    public void writeAdditional(NBTTagCompound compound) {
        super.writeAdditional(compound);

        compound.setBoolean("Sheared", this.getSheared());
        compound.setByte("Color", (byte)this.getFleeceColor().getId());

        //store this sheeps's genes
        NBTTagList geneList = new NBTTagList();
        for (int i = 0; i < genes.length; i++) {
            NBTTagCompound nbttagcompound = new NBTTagCompound();
            nbttagcompound.setInt("Gene", genes[i]);
            geneList.add(nbttagcompound);
        }
        compound.setTag("Genes", geneList);

        //store this sheeps's mate's genes
        NBTTagList mateGeneList = new NBTTagList();
        for (int i = 0; i < mateGenes.length; i++) {
            NBTTagCompound nbttagcompound = new NBTTagCompound();
            nbttagcompound.setInt("Gene", mateGenes[i]);
            mateGeneList.add(nbttagcompound);
        }
        compound.setTag("FatherGenes", mateGeneList);
    }

    /**
     * (abstract) Protected helper method to read subclass entity assets from NBT.
     */
    public void readAdditional(NBTTagCompound compound) {
        super.readAdditional(compound);

        this.setSheared(compound.getBoolean("Sheared"));
        this.setFleeceColor(EnumDyeColor.byId(compound.getByte("Color")));

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


    public int[] getLambGenes() {
        Random rand = new Random();
        int[] lambGenes = new int[GENES_LENGTH];

        for (int i = 0; i < 20; i++) {
            boolean thisOrMate = rand.nextBoolean();
            if (thisOrMate) {
                lambGenes[i] = genes[i];
            } else {
                lambGenes[i] = mateGenes[i];
            }
        }

        for (int i = 20; i < genes.length; i++) {
            boolean thisOrMate = rand.nextBoolean();
            if (thisOrMate) {
                lambGenes[i] = mitosisGenes[i];
            } else {
                lambGenes[i] = mateMitosisGenes[i];
            }
        }

        return lambGenes;
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

        /**
         * Horns
         */


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
         * Shape Genes
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

        /**
         * Size Genes
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

        /**
         * Production Type Genes
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

        /**
         * Behaviour Type Genes
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

    static
    {
        for (EnumDyeColor enumdyecolor : EnumDyeColor.values())
        {
            DYE_TO_RGB.put(enumdyecolor, createSheepColor(enumdyecolor));
        }

        DYE_TO_RGB.put(EnumDyeColor.WHITE, new float[] {0.9019608F, 0.9019608F, 0.9019608F});
    }
}
