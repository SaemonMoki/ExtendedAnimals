package mokiyoki.enhancedanimals.entity;

import mokiyoki.enhancedanimals.ai.ECLlamaFollowCaravan;
import mokiyoki.enhancedanimals.ai.ECRunAroundLikeCrazy;
import mokiyoki.enhancedanimals.util.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCarpet;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.passive.AbstractChestHorse;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.Particles;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumDyeColor;
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
import net.minecraft.util.ResourceLocation;
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

public class EnhancedLlama extends AbstractChestHorse implements IRangedAttackMob, net.minecraftforge.common.IShearable {

    private static final DataParameter<Integer> DATA_STRENGTH_ID = EntityDataManager.createKey(EnhancedLlama.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> DATA_INVENTORY_ID = EntityDataManager.createKey(EnhancedLlama.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> COAT_LENGTH = EntityDataManager.createKey(EnhancedLlama.class, DataSerializers.VARINT);
    private static final DataParameter<String> SHARED_GENES = EntityDataManager.<String>createKey(EnhancedLlama.class, DataSerializers.STRING);
    private static final DataParameter<Integer> DATA_COLOR_ID = EntityDataManager.createKey(EnhancedLlama.class, DataSerializers.VARINT);

    private static final String[] LLAMA_TEXTURES_GROUND = new String[] {
            "brokenlogic.png", "ground_paleshaded.png", "ground_shaded.png", "ground_blacktan.png", "ground_bay.png", "ground_mahogany.png", "ground_blacktan.png", "black.png", "fawn.png"
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
            "", "fur_suri.png"
    };

    private static final String[] LLAMA_TEXTURES_EYES = new String[] {
            "eyes_black.png", "eyes_blue.png", "eyes_iceblue.png"
    };

    private static final String[] LLAMA_TEXTURES_SKIN = new String[] {
            "skin_black.png", "skin_pink.png"
    };

    private static final String LLAMA_CHEST_TEXTURE = "chest.png";


    private final List<String> llamaTextures = new ArrayList<>();

    private static final Ingredient TEMPTATION_ITEMS = Ingredient.fromItems(Items.CARROT);

    public float destPos;
    private String dropMeatType;

    private static final int WTC = 90;
    private static final int GENES_LENGTH = 28;
    private int[] genes = new int[GENES_LENGTH];
    private int[] mateGenes = new int[GENES_LENGTH];
    private int[] mitosisGenes = new int[GENES_LENGTH];
    private int[] mateMitosisGenes = new int[GENES_LENGTH];

    protected int temper;

    private int maxCoatLength;
    private int currentCoatLength;
    private int timeForGrowth = 0;

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
        this.dataManager.register(DATA_STRENGTH_ID, 0);
        this.dataManager.register(DATA_INVENTORY_ID, 0);
        this.dataManager.register(COAT_LENGTH, -1);
        this.dataManager.register(DATA_COLOR_ID, -1);
    }

    private void setStrength(int strengthIn) {
        this.dataManager.set(DATA_STRENGTH_ID, strengthIn);
    }

    public int getStrength() {
        return this.dataManager.get(DATA_STRENGTH_ID);
    }

    private void setInventory(int inventoryIn) {
        this.dataManager.set(DATA_INVENTORY_ID, inventoryIn);
    }

    public int getInventory() {
        return this.dataManager.get(DATA_INVENTORY_ID);
    }

    private void setCoatLength(int coatLength) {
        this.dataManager.set(COAT_LENGTH, coatLength);
    }

    public int getCoatLength() {
        return this.dataManager.get(COAT_LENGTH);
    }


    protected int getInventorySize() {
        return this.hasChest() ? 2 + 3 * this.getInventoryColumns() : super.getInventorySize();
    }

    public int getInventoryColumns() {
        return this.getInventory();
    }

    public void updatePassenger(Entity passenger) {
        if (this.isPassenger(passenger)) {
            float f = MathHelper.cos(this.renderYawOffset * ((float)Math.PI / 180F));
            float f1 = MathHelper.sin(this.renderYawOffset * ((float)Math.PI / 180F));
            float f2 = 0.3F;
            passenger.setPosition(this.posX + (double)(0.3F * f1), this.posY + this.getMountedYOffset() + passenger.getYOffset(), this.posZ - (double)(0.3F * f));
        }
    }

    /**
     * Returns the Y offset from the entity's position for any entity riding this one.
     */
    public double getMountedYOffset() {
        return (double)this.height * 0.67D;
    }

    /**
     * returns true if all the conditions for steering the entity are met. For pigs, this is true if it is being ridden
     * by a player and the player is holding a carrot-on-a-stick
     */
    public boolean canBeSteered() {
        return false;
    }

    protected boolean handleEating(EntityPlayer player, ItemStack stack) {
        int i = 0;
        int j = 0;
        float f = 0.0F;
        boolean flag = false;
        Item item = stack.getItem();
        if (item == Items.WHEAT) {
            i = 10;
            j = 3;
            f = 2.0F;
        } else if (item == Blocks.HAY_BLOCK.asItem()) {
            i = 90;
            j = 6;
            f = 10.0F;
            if (this.isTame() && this.getGrowingAge() == 0 && this.canBreed()) {
                flag = true;
                this.setInLove(player);
            }
        }

        if (this.getHealth() < this.getMaxHealth() && f > 0.0F) {
            this.heal(f);
            flag = true;
        }

        if (this.isChild() && i > 0) {
            this.world.spawnParticle(Particles.HAPPY_VILLAGER, this.posX + (double)(this.rand.nextFloat() * this.width * 2.0F) - (double)this.width, this.posY + 0.5D + (double)(this.rand.nextFloat() * this.height), this.posZ + (double)(this.rand.nextFloat() * this.width * 2.0F) - (double)this.width, 0.0D, 0.0D, 0.0D);
            if (!this.world.isRemote) {
                this.addGrowth(i);
            }

            flag = true;
        }

        if (j > 0 && (flag || !this.isTame()) && this.getTemper() < this.getMaxTemper()) {
            flag = true;
            if (!this.world.isRemote) {
                this.increaseTemper(j);
            }
        }

        if (flag && !this.isSilent()) {
            this.world.playSound((EntityPlayer)null, this.posX, this.posY, this.posZ, SoundEvents.ENTITY_LLAMA_EAT, this.getSoundCategory(), 1.0F, 1.0F + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F);
        }

        return flag;
    }

    /**
     * Dead and sleeping entities cannot move
     */
    protected boolean isMovementBlocked() {
        return this.getHealth() <= 0.0F || this.isEatingHaystack();
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

    public void livingTick() {
        super.livingTick();
        this.destPos = (float)((double)this.destPos + (double)(this.onGround ? -1 : 4) * 0.3D);
        this.destPos = MathHelper.clamp(this.destPos, 0.0F, 1.0F);
        if (!this.world.isRemote) {
            timeForGrowth++;
            if (timeForGrowth >= 24000) {
                timeForGrowth = 0;
                if (maxCoatLength > currentCoatLength) {
                    currentCoatLength++;
                    setCoatLength(currentCoatLength);
                }
            }
        }


    }

    @OnlyIn(Dist.CLIENT)
    public boolean hasColor() {
        return this.getColor() != null;
    }

    @Override
    @Nullable
    protected ResourceLocation getLootTable() {

        if (genes[20] == 1 || genes[21] == 1){
            //drops leather
            dropMeatType = "leather";
        }else{
            //drops wool
            dropMeatType = "brown_wool";
        }

        return new ResourceLocation(Reference.MODID, "enhanced_llama");
    }

    public String getDropMeatType() {
        return dropMeatType;
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

    public boolean isBreedingItem(ItemStack stack)
    {
        return TEMPTATION_ITEMS.test(stack);
    }

    @Override
    public boolean isShearable(ItemStack item, net.minecraft.world.IWorldReader world, BlockPos pos) {
        if (!this.world.isRemote && currentCoatLength >=0 && !isChild()) {
            return true;
        }
        return false;
    }

    @Override
    public java.util.List<ItemStack> onSheared(ItemStack item, net.minecraft.world.IWorld world, BlockPos pos, int fortune) {
        java.util.List<ItemStack> ret = new java.util.ArrayList<>();
        if (!this.world.isRemote && !isChild()) {
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
        currentCoatLength = -1;
        setCoatLength(currentCoatLength);
        return ret;
    }

    @Override
    public void setChested(boolean chested) {
        super.setChested(chested);
        if (chested && !this.llamaTextures.contains(LLAMA_CHEST_TEXTURE)) {
            this.llamaTextures.add(LLAMA_CHEST_TEXTURE);
        } else if (!chested && this.llamaTextures.contains(LLAMA_CHEST_TEXTURE)) {
            this.llamaTextures.remove(LLAMA_CHEST_TEXTURE);
        }
    }

    public EntityAgeable createChild(EntityAgeable ageable) {
        this.mateGenes = ((EnhancedLlama) ageable).getGenes();
        mixMateMitosisGenes();
        mixMitosisGenes();
        int[] babyGenes = getCriaGenes();
        EnhancedLlama enhancedllama = new EnhancedLlama(this.world);
        enhancedllama.setGrowingAge(0);
        enhancedllama.setGenes(babyGenes);
        enhancedllama.setSharedGenes(babyGenes);
        enhancedllama.setStrengthAndInventory();
        enhancedllama.setMaxCoatLength();
        enhancedllama.currentCoatLength = enhancedllama.maxCoatLength;
        enhancedllama.setCoatLength(enhancedllama.currentCoatLength);
        return enhancedllama;
    }

    @OnlyIn(Dist.CLIENT)
    public String getLlamaTexture() {
        if (this.llamaTextures.isEmpty()) {
            this.setTexturePaths();
        }
        return this.llamaTextures.stream().collect(Collectors.joining("/","enhanced_llama/",""));

    }

    @OnlyIn(Dist.CLIENT)
    public String[] getVariantTexturePaths() {
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

            //suri coat texture
            if (genesForText[20] == 2 && genesForText[21] == 2){
                fur = 1;
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
        if (this.hasChest()) {
            this.llamaTextures.add(LLAMA_CHEST_TEXTURE);

        }

    } // setTexturePaths end bracket

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

        compound.setInt("Strength", this.getStrength());
        compound.setInt("Inventory", this.getInventory());
        compound.setFloat("CoatLength", this.getCoatLength());
        if (!this.horseChest.getStackInSlot(1).isEmpty()) {
            compound.setTag("DecorItem", this.horseChest.getStackInSlot(1).write(new NBTTagCompound()));
        }
    }

    public void readAdditional(NBTTagCompound compound) {
        this.setStrength(compound.getInt("Strength"));
        this.setInventory(compound.getInt("Inventory"));
        this.setCoatLength(compound.getInt("CoatLength"));
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

        //TODO add a proper calculation for this
        for (int i = 0; i < genes.length; i++) {
            if (genes[i] == 0) {
                genes[i] = 1;
            }
        }
        if (mateGenes[0] != 0) {
            for (int i = 0; i < mateGenes.length; i++) {
                if (mateGenes[i] == 0) {
                    mateGenes[i] = 1;
                }
            }
        }

        setSharedGenes(genes);

        if (compound.contains("DecorItem", 10)) {
            this.horseChest.setInventorySlotContents(1, ItemStack.read(compound.getCompound("DecorItem")));
        }

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

    public int[] getCriaGenes() {
        Random rand = new Random();
        int[] criaGenes = new int[GENES_LENGTH];

        for (int i = 0; i < genes.length; i = (i + 2)) {
            boolean thisOrMate = rand.nextBoolean();
            if (thisOrMate) {
                criaGenes[i] = mitosisGenes[i];
                criaGenes[i+1] = mateMitosisGenes[i+1];
            } else {
                criaGenes[i] = mateMitosisGenes[i];
                criaGenes[i+1] = mitosisGenes[i+1];
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
        setStrengthAndInventory();
        setMaxCoatLength();
        this.currentCoatLength = this.maxCoatLength;
        setCoatLength(this.currentCoatLength);
        return livingdata;
    }

    private void setStrengthAndInventory() {
        int inv = 1;
        int str = 1;
        if (genes[2] != 1 &&  genes[3] !=1) {
            if (genes[2] == 2 && genes[3] == 2) {
                inv = inv + 1;
            } else if (genes[2] == 3 && genes[3] == 3) {
                inv = inv + 1;
            } else {
                inv = inv + 2;
            }
        }

        if (genes[4] == 1 && genes[5] ==1) {
            str = inv;
        }else if (genes[4] == 2 && genes[5] == 2) {
            str = inv + 1;
        } else if (genes[4] == 3 && genes[5] == 3) {
            str = inv + 1;
        } else {
            str = inv + 2;
        }


        if (genes[0] != 1 && genes[1] !=1) {
            if (genes[0] == 2 && genes[1] == 2){
                inv = inv + 1;
            } else if (genes[0] == 3 && genes[1] == 3){
                inv = inv + 1;
            } else {
                inv = inv + 2;
            }
        }

        setStrength(str);
        setInventory(inv);
    }

    private void setMaxCoatLength() {
        float maxCoatLength = 0.0F;

        if ( !this.isChild() && (genes[22] >= 2 || genes[23] >= 2) ){
            if (genes[22] == 3 && genes[23] == 3){
                maxCoatLength = 1.25F;
            }else if (genes[22] == 3 || genes[23] == 3) {
                maxCoatLength = 1F;
            }else if (genes[22] == 2 && genes[23] == 2) {
                maxCoatLength = 0.75F;
            }else {
                maxCoatLength = 0.5F;
            }

            if (genes[24] == 2){
                maxCoatLength = maxCoatLength - 0.25F;
            }
            if (genes[25] == 2){
                maxCoatLength = maxCoatLength - 0.25F;
            }

            if (genes[26] == 2 && genes[27] == 2){
                maxCoatLength = maxCoatLength + (0.75F * (maxCoatLength/1.75F));
            }

        }else{
            maxCoatLength = 0;
        }

        if (maxCoatLength < 0.5){
            maxCoatLength = 0;
        }else if (maxCoatLength < 1){
            maxCoatLength = 1;
        }else if (maxCoatLength < 1.5){
            maxCoatLength = 2;
        }else if (maxCoatLength < 2) {
            maxCoatLength = 3;
        }else{
            maxCoatLength = 4;
        }

        this.maxCoatLength = (int)maxCoatLength;

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

        //Suri coat genes [ normal, suri ]
        if(ThreadLocalRandom.current().nextInt(100)>WTC){
            initialGenes[20] = (ThreadLocalRandom.current().nextInt(2)+1);

        } else {
            initialGenes[20] = (1);
        }
        if(ThreadLocalRandom.current().nextInt(100)>WTC){
            initialGenes[21] = (ThreadLocalRandom.current().nextInt(2)+1);

        } else {
            initialGenes[21] = (1);
        }

        //Coat Length genes [ normal, Longer, Longest ]
        if(ThreadLocalRandom.current().nextInt(100)>WTC){
            initialGenes[22] = (ThreadLocalRandom.current().nextInt(3)+1);

        } else {
            initialGenes[22] = (1);
        }
        if(ThreadLocalRandom.current().nextInt(100)>WTC){
            initialGenes[23] = (ThreadLocalRandom.current().nextInt(3)+1);

        } else {
            initialGenes[23] = (1);
        }

        //Coat Length suppressor [ normal, shorter ]
        if(ThreadLocalRandom.current().nextInt(100)>WTC){
            initialGenes[24] = (ThreadLocalRandom.current().nextInt(3)+1);

        } else {
            initialGenes[24] = (1);
        }
        if(ThreadLocalRandom.current().nextInt(100)>WTC){
            initialGenes[25] = (ThreadLocalRandom.current().nextInt(3)+1);

        } else {
            initialGenes[25] = (1);
        }

        //Coat Length amplifier [ normal, double ]
        if(ThreadLocalRandom.current().nextInt(100)>WTC){
            initialGenes[26] = (ThreadLocalRandom.current().nextInt(3)+1);

        } else {
            initialGenes[26] = (1);
        }
        if(ThreadLocalRandom.current().nextInt(100)>WTC){
            initialGenes[27] = (ThreadLocalRandom.current().nextInt(3)+1);

        } else {
            initialGenes[27] = (1);
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
            super.followMother();
        }
    }


    public void makeMad() {
        SoundEvent soundevent = this.getAngrySound();
        if (soundevent != null) {
            this.playSound(soundevent, this.getSoundVolume(), this.getSoundPitch());
        }

    }

    protected SoundEvent getAngrySound() {
        return SoundEvents.ENTITY_LLAMA_ANGRY;
    }

    public int increaseTemper(int p_110198_1_) {
        int i = MathHelper.clamp(this.getTemper() + p_110198_1_, 0, this.getMaxTemper());
        this.setTemper(i);
        return i;
    }

    /**
     * Updates the items in the saddle and armor slots of the horse's inventory.
     */
    protected void updateHorseSlots() {
        if (!this.world.isRemote) {
            super.updateHorseSlots();
            this.setColor(func_195403_g(this.horseChest.getStackInSlot(1)));
        }
    }

    private void setColor(@Nullable EnumDyeColor color) {
        this.dataManager.set(DATA_COLOR_ID, color == null ? -1 : color.getId());
    }

    @Nullable
    private static EnumDyeColor func_195403_g(ItemStack p_195403_0_) {
        Block block = Block.getBlockFromItem(p_195403_0_.getItem());
        return block instanceof BlockCarpet ? ((BlockCarpet)block).getColor() : null;
    }

    @Nullable
    public EnumDyeColor getColor() {
        int i = this.dataManager.get(DATA_COLOR_ID);
        return i == -1 ? null : EnumDyeColor.byId(i);
    }

    public boolean canMateWith(EntityAnimal otherAnimal) {
        return otherAnimal != this && otherAnimal instanceof EnhancedLlama && this.canMate() && ((EnhancedLlama)otherAnimal).canMate();
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
