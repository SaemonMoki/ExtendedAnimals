package mokiyoki.enhancedanimals.entity;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import net.minecraft.block.state.IBlockState;
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
import net.minecraft.util.*;
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

    private static final DataParameter<Integer> COAT_LENGTH = EntityDataManager.createKey(EnhancedSheep.class, DataSerializers.VARINT);
    private static final DataParameter<String> SHARED_GENES = EntityDataManager.<String>createKey(EnhancedSheep.class, DataSerializers.STRING);
    private static final DataParameter<Byte> DYE_COLOR = EntityDataManager.<Byte>createKey(EnhancedSheep.class, DataSerializers.BYTE);

    private static final String[] SHEEP_TEXTURES_UNDER = new String[] {
            "solid_tan.png", "solid_black.png", "solid_choc", "solid_lighttan.png"
    };

    private static final String[] SHEEP_TEXTURES_PATTERN = new String[] {
            "", "solid_white.png", "badger_black.png", "badger_choc.png", "mouflonbadger_black.png", "mouflonbadger_choc.png", "mouflon_black.png", "mouflon_choc.png", "blue_black.png", "blue_choc.png", "solid_black.png", "solid_choc.png"
    };

    private static final String[] SHEEP_TEXTURES_GREY = new String[] {
            "", "grey.png"
    };

    private static final String[] SHEEP_TEXTURES_SKIN = new String[] {
            "skin_pink.png"
    };

    private static final String[] SHEEP_TEXTURES_HOOVES = new String[] {
            "hooves_black.png"
    };

    private static final String[] SHEEP_TEXTURES_FUR = new String[] {
            "fur_wooly.png"
    };

    private static final String[] SHEEP_TEXTURES_EYES = new String[] {
            "eyes_black.png"
    };

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
    private static final int GENES_LENGTH = 36;
    private int[] genes = new int[GENES_LENGTH];
    private int[] mateGenes = new int[GENES_LENGTH];
    private int[] mitosisGenes = new int[GENES_LENGTH];
    private int[] mateMitosisGenes = new int[GENES_LENGTH];

    private int maxCoatLength;
    private int currentCoatLength;
    private int timeForGrowth = 0;

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
        this.dataManager.register(COAT_LENGTH, 0);
        this.dataManager.register(DYE_COLOR, Byte.valueOf((byte)0));
    }

    private void setCoatLength(int coatLength) {
        this.dataManager.set(COAT_LENGTH, coatLength);
    }

    public int getCoatLength() {
        return this.dataManager.get(COAT_LENGTH);
    }

    protected void registerAttributes() {
        super.registerAttributes();
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(8.0D);
        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.23000000417232513D);
    }

    public void livingTick() {
        super.livingTick();

        if (this.world.isRemote) {
            this.sheepTimer = Math.max(0, this.sheepTimer - 1);
        }

        if (!this.world.isRemote) {
            timeForGrowth++;
            if (timeForGrowth >= (24000 / maxCoatLength)) {
                timeForGrowth = 0;
                if (maxCoatLength > currentCoatLength) {
                    currentCoatLength++;
                    setCoatLength(currentCoatLength);
                }
            }
        }

    }

    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_SHEEP_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.ENTITY_SHEEP_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_SHEEP_DEATH;
    }

    protected void playStepSound(BlockPos pos, IBlockState blockIn) {
        this.playSound(SoundEvents.ENTITY_SHEEP_STEP, 0.15F, 1.0F);
    }

    public void eatGrassBonus() {
        this.setSheared(false);
        if (this.isChild()) {
            this.addGrowth(60);
        }else if (currentCoatLength < maxCoatLength){
            this.currentCoatLength ++ ;
        }

    }

    @Override
    public boolean isShearable(ItemStack item, net.minecraft.world.IWorldReader world, BlockPos pos) {
        if (currentCoatLength >=1) {
            return true;
        }
        return false;
    }

    @Override
    public java.util.List<ItemStack> onSheared(ItemStack item, net.minecraft.world.IWorld world, BlockPos pos, int fortune) {
        java.util.List<ItemStack> ret = new java.util.ArrayList<>();
        if (!this.world.isRemote) {
            if (currentCoatLength == 1) {
                int i = this.rand.nextInt(4);
                if (i>3){
                    ret.add(new ItemStack(Blocks.BROWN_WOOL));
                }
            } else if (currentCoatLength == 2) {
                int i = this.rand.nextInt(2);
                if (i>0){
                    ret.add(new ItemStack(Blocks.BROWN_WOOL));
                }
            } else if (currentCoatLength == 3) {
                int i = this.rand.nextInt(4);
                if (i>0){
                    ret.add(new ItemStack(Blocks.BROWN_WOOL));
                }
            } else if (currentCoatLength == 4) {
                ret.add(new ItemStack(Blocks.BROWN_WOOL));
            }

        }
        currentCoatLength = 0;
        setCoatLength(currentCoatLength);
        return ret;
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
        enhancedsheep.setMaxCoatLength();
        enhancedsheep.currentCoatLength = enhancedsheep.maxCoatLength;
        enhancedsheep.setCoatLength(enhancedsheep.currentCoatLength);
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
        return this.sheepTextures.stream().collect(Collectors.joining("/","enhanced_sheep/",""));

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

        int[] genesForText = getSharedGenes();
        if (genesForText != null) {
            int under = 0;
            int pattern = 0;
            int grey = 0;
            int skin = 0;
            int hooves = 0;
            int fur = 0;
            int eyes = 0;

            if (genesForText[4] == 1 || genesForText[5] ==1){
                //black sheep
                under = 1;
            }else {
                if (genesForText[0] == 1 || genesForText[1] == 1) {
                    //white sheep
                    pattern = 1;
                }else if (genesForText[0] == 2 || genesForText[1] == 2){
                    grey = 1;
                    if (genesForText[0] == 3 || genesForText[1] == 3){
                        pattern = 2;
                    }else if (genesForText[0] == 4 || genesForText[1] == 4){
                        pattern = 6;
                    }else{
                        pattern = 10;
                    }
                }else if(genesForText[0] == 3 || genesForText[1] == 3){
                    if(genesForText[0] == 4 || genesForText[1] == 4){
                        pattern = 4;
                    }else{
                        pattern = 2;
                    }
                }else if (genesForText[0] == 4 || genesForText[1] == 4){
                    pattern = 6;
                }else if (genesForText[0] == 5 || genesForText[1] == 5){
                    pattern = 8;
                    under = 3;
                }else{
                    pattern = 10;
                }
            }

            if (genesForText[2] == 2 && genesForText[3] == 2){
                pattern = pattern + 1;
            }


         this.sheepTextures.add(SHEEP_TEXTURES_UNDER[under]);
         if (pattern != 0) {
             this.sheepTextures.add(SHEEP_TEXTURES_PATTERN[pattern]);
         }
         if (grey != 0) {
             this.sheepTextures.add(SHEEP_TEXTURES_GREY[grey]);
         }
         this.sheepTextures.add(SHEEP_TEXTURES_SKIN[skin]);
         this.sheepTextures.add(SHEEP_TEXTURES_HOOVES[hooves]);
         this.sheepTextures.add(SHEEP_TEXTURES_FUR[fur]);
         this.sheepTextures.add(SHEEP_TEXTURES_EYES[eyes]);


        }
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
        if (id == 10) {
            this.sheepTimer = 40;
        } else {
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


//    public boolean processInteract(EntityPlayer player, EnumHand hand) {
//    ItemStack itemstack = player.getHeldItem(hand);
//      if (false && itemstack.getItem() == Items.SHEARS && !this.getSheared() && !this.isChild()) { //Forge: Moved to onSheared
//        if (!this.world.isRemote) {
//            this.setSheared(true);
//            int i = 1 + this.rand.nextInt(3);
//
//            for(int j = 0; j < i; ++j) {
//                EntityItem entityitem = this.entityDropItem(WOOL_BY_COLOR.get(this.getFleeceColor()), 1);
//                if (entityitem != null) {
//                    entityitem.motionY += (double)(this.rand.nextFloat() * 0.05F);
//                    entityitem.motionX += (double)((this.rand.nextFloat() - this.rand.nextFloat()) * 0.1F);
//                    entityitem.motionZ += (double)((this.rand.nextFloat() - this.rand.nextFloat()) * 0.1F);
//                }
//            }
//        }
//
//        itemstack.damageItem(1, player);
//        this.playSound(SoundEvents.ENTITY_SHEEP_SHEAR, 1.0F, 1.0F);
//    }
//
//      return super.processInteract(player, hand);
//    }

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

    public void writeAdditional(NBTTagCompound compound) {
        super.writeAdditional(compound);

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
        compound.setFloat("CoatLength", this.getCoatLength());

    }

    /**
     * (abstract) Protected helper method to read subclass entity assets from NBT.
     */
    public void readAdditional(NBTTagCompound compound) {

        this.setCoatLength(compound.getInt("CoatLength"));

        super.readAdditional(compound);

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

        //resets the max so we don't have to store it
        setMaxCoatLength();

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


    public int[] getLambGenes() {
        Random rand = new Random();
        int[] lambGenes = new int[GENES_LENGTH];

        for (int i = 0; i < genes.length; i = (i + 2)) {
            boolean thisOrMate = rand.nextBoolean();
            if (thisOrMate) {
                lambGenes[i] = mitosisGenes[i];
                lambGenes[i+1] = mateMitosisGenes[i+1];
            } else {
                lambGenes[i] = mateMitosisGenes[i];
                lambGenes[i+1] = mitosisGenes[i+1];
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
        setMaxCoatLength();
        this.currentCoatLength = this.maxCoatLength;
        setCoatLength(this.currentCoatLength);
        return livingdata;
    }

    private void setMaxCoatLength() {
        float maxCoatLength = 0.0F;

        if ( !this.isChild() ) {
            if (genes[20] == 2){
                maxCoatLength = 1;
            }
            if (genes[21] == 2){
                maxCoatLength = maxCoatLength + 1;
            }
            if (genes[22] == 2){
                maxCoatLength = maxCoatLength + 1;
            }
            if (genes[23] == 2){
                maxCoatLength = maxCoatLength + 1;
            }
            if (genes[24] == 2){
                maxCoatLength = maxCoatLength + 1;
            }
            if (genes[25] == 2){
                maxCoatLength = maxCoatLength + 1;
            }
            if (genes[26] == 2){
                maxCoatLength = maxCoatLength + 1;
            }
            if (genes[27] == 2){
                maxCoatLength = maxCoatLength + 1;
            }
            if (genes[28] == 2){
                maxCoatLength = maxCoatLength + 1;
            }
            if (genes[29] == 2){
                maxCoatLength = maxCoatLength + 1;
            }
            if (genes[30] == 2){
                maxCoatLength = maxCoatLength + 1;
            }
            if (genes[31] == 2){
                maxCoatLength = maxCoatLength + 1;
            }
            if (genes[32] == 2){
                maxCoatLength = maxCoatLength + 1;
            }
            if (genes[33] == 2){
                maxCoatLength = maxCoatLength + 1;
            }
            if (genes[34] == 2 && genes[35] == 2){
                maxCoatLength = maxCoatLength + 1;
            }

        }

        this.maxCoatLength = (int)maxCoatLength;

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

        //Agouti? [ Dom.White, Grey, Badgerface, Mouflon+, EnglishBlue, Rec.Black ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[0] = (ThreadLocalRandom.current().nextInt(6) + 1);

        } else {
            initialGenes[0] = (4);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[1] = (ThreadLocalRandom.current().nextInt(6) + 1);

        } else {
            initialGenes[1] = (4);
        }

        //Chocolate [ Wildtype+, chocolate ]
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

        //Extention [ Dom.Black, wildtype+, Rec.Red ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[4] = (ThreadLocalRandom.current().nextInt(3) + 1);

        } else {
            initialGenes[4] = (2);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[5] = (ThreadLocalRandom.current().nextInt(3) + 1);

        } else {
            initialGenes[5] = (2);
        }

        /**
         * Horns
         */


        //Polled [ no horns, horns, 1/2 chance horns ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[6] = (ThreadLocalRandom.current().nextInt(3) + 1);

        } else {
            initialGenes[6] = (2);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[7] = (ThreadLocalRandom.current().nextInt(3) + 1);

        } else {
            initialGenes[7] = (2);
        }

        /**
         * Spot Genes
         */

        //spots1 [ wildtype, spots1 ]
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

        //appaloosa spots [ wildtype, appaloosa ]
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

        //irregular spots [ wildtype, irregular ]
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

        //blaze [ wildtype, blaze ]
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

        //white nose [ wildtype, whitenose ]
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

        //face white extension [ wildtype, white extension ]
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

        //added wool length 1 [ wildtype, wool1 ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC/4) {
            initialGenes[20] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[20] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC/4) {
            initialGenes[21] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[21] = (1);
        }

        //added wool length 2 [ wildtype, wool2 ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC/4) {
            initialGenes[22] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[22] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC/4) {
            initialGenes[23] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[23] = (1);
        }

        //added wool length 3 [ wildtype, wool3 ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC/4) {
            initialGenes[24] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[24] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC/4) {
            initialGenes[25] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[25] = (1);
        }

        //added wool length 4 [ wildtype, wool3 ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC/4) {
            initialGenes[26] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[26] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC/4) {
            initialGenes[27] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[27] = (1);
        }

        //added wool length 5 [ wildtype, wool3 ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC/4) {
            initialGenes[28] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[28] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC/4) {
            initialGenes[29] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[29] = (1);
        }

        //added wool length 6 [ wildtype, wool3 ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC/4) {
            initialGenes[30] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[30] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC/4) {
            initialGenes[31] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[31] = (1);
        }

        //added wool length 7 [ wildtype, wool3 ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC/4) {
            initialGenes[32] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[32] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC/4) {
            initialGenes[33] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[33] = (1);
        }

        //added wool length 8 [ wildtype, wool3 ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC/4) {
            initialGenes[34] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[34] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC/4) {
            initialGenes[35] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[35] = (1);
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
