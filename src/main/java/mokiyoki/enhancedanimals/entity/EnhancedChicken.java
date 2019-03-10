package mokiyoki.enhancedanimals.entity;

import mokiyoki.enhancedanimals.ai.ECRoost;
import mokiyoki.enhancedanimals.ai.ECSandBath;
import mokiyoki.enhancedanimals.ai.ECWanderAvoidWater;
import mokiyoki.enhancedanimals.init.ModItems;
import mokiyoki.enhancedanimals.util.Reference;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
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
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
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

import static mokiyoki.enhancedanimals.util.handlers.RegistryHandler.ENHANCED_CHICKEN;

/**
 * Created by saemon and moki on 30/08/2018.
 */
public class EnhancedChicken extends EntityAnimal {

    private static final DataParameter<String> SHARED_GENES = EntityDataManager.<String>createKey(EnhancedChicken.class, DataSerializers.STRING);
    private static final DataParameter<Boolean> ROOSTING = EntityDataManager.<Boolean>createKey(EnhancedChicken.class, DataSerializers.BOOLEAN);
    /** [4] duckwing, partridge, wheaten, solid
     [5] silver, salmon, lemon, gold, mahogany */
    private static final String[] CHICKEN_TEXTURES_GROUND = new String[] {
        "ground_duckwing_silver.png",   "ground_duckwing_salmon.png",   "ground_duckwing_lemon.png",
        "ground_duckwing_gold.png",     "ground_duckwing_mahogany.png", "ground_solid_silver.png",
        "ground_solid_silver.png",      "ground_partridge_lemon.png",   "ground_partridge_gold.png",
        "ground_partridge_mahogany.png","ground_wheaten_silver.png",    "ground_wheaten_salmon.png",
        "ground_wheaten_lemon.png",     "ground_wheaten_gold.png",      "ground_wheaten_mahogany.png",
        "ground_solid_silver.png",      "ground_solid_silver.png",      "ground_solid_cream.png",
        "ground_solid_buff.png",        "ground_solid_mahogany.png",    "pattern_solid_blue.png"
    };
    /** [10]  black,blue,splash,splashLav,splashDun,splashChoc,lavender,white,dun,chocolate
        [18] solid,birchen,duckwing,wheaten,quail,columbian,darkbrown,lakenvelder,moorhead,blacktail,penciled,bsinglelace,singlelace,doublelace,spangled,partridge-penciled, halfmoon spangle, bluelace/none */
    private static final String[] CHICKEN_TEXTURES_PATTERN = new String[] {
        "pattern_solid.png","pattern_solid_blue.png", "pattern_solid_splash.png","pattern_solid_splashlav.png","pattern_solid_splashdun.png",                                   // 0
        "pattern_solid_splashchoc.png", "pattern_solid_lav.png","ground_solid_silver.png", "pattern_solid_dun.png", "pattern_solid_choc.png",                                   // 5
        "pattern_birchen.png", "pattern_birchen_blue.png", "pattern_birchen_splash.png","pattern_birchen_splashlav.png","pattern_birchen_splashdun.png",                        //10
        "pattern_birchen_splashchoc.png","pattern_birchen_lav.png", "pattern_birchen_white.png", "pattern_birchen_dun.png", "pattern_birchen_choc.png",                         //15
        "pattern_duckwing.png","pattern_duckwing_blue.png", "pattern_duckwing_splash.png","pattern_duckwing_splashlav.png","pattern_duckwing_splashdun.png",                    //20
        "pattern_duckwing_splashchoc.png", "pattern_duckwing_lav.png","pattern_duckwing_white.png", "pattern_duckwing_dun.png", "pattern_duckwing_choc.png",                    //25
        "pattern_wheaten.png","pattern_wheaten_blue.png", "pattern_wheaten_splash.png","pattern_wheaten_splashlav.png","pattern_wheaten_splashdun.png",                         //30
        "pattern_wheaten_splashchoc.png","pattern_wheaten_lav.png","pattern_wheaten_white.png", "pattern_wheaten_dun.png", "pattern_wheaten_choc.png",                          //35
        "pattern_quail.png", "pattern_quail_blue.png","pattern_quail_splash.png","pattern_quail_splashlav.png","pattern_quail_splashdun.png",                                   //40
        "pattern_quail_splashchoc.png","pattern_quail_lav.png", "pattern_quail_white.png","pattern_quail_dun.png", "pattern_quail_choc.png",                                    //45
        "pattern_columbian.png","pattern_columbian_blue.png", "pattern_columbian_splash.png","pattern_columbian_splashlav.png","pattern_columbian_splashdun.png",               //50
        "pattern_columbian_splashchoc.png", "pattern_columbian_lav.png","pattern_columbian_white.png", "pattern_columbian_dun.png", "pattern_columbian_choc.png",               //55
        "pattern_darkbrown.png","pattern_darkbrown_blue.png", "pattern_darkbrown_splash.png","pattern_darkbrown_splashlav.png","pattern_darkbrown_splashdun.png",               //60
        "pattern_darkbrown_splashchoc.png","pattern_darkbrown_lav.png","pattern_darkbrown_white.png", "pattern_darkbrown_dun.png", "pattern_darkbrown_choc.png",                //65
        "pattern_lakenvelder.png", "pattern_lakenvelder_blue.png", "pattern_lakenvelder_splash.png","pattern_lakenvelder_splashlav.png","pattern_lakenvelder_splashdun.png",    //70
        "pattern_lakenvelder_splashchoc.png","pattern_lakenvelder_lav.png", "pattern_lakenvelder_white.png", "pattern_lakenvelder_dun.png","pattern_lakenvelder_choc.png",      //75
        "pattern_moorhead.png","pattern_moorhead_blue.png", "pattern_moorhead_splash.png","pattern_moorhead_splashlav.png","pattern_moorhead_splashdun.png",                    //80
        "pattern_moorhead_splashchoc.png", "pattern_moorhead_lav.png","pattern_moorhead_white.png", "pattern_moorhead_dun.png", "pattern_moorhead_choc.png",                    //85
        "pattern_blacktail.png","pattern_blacktail_blue.png", "pattern_blacktail_splash.png","pattern_blacktail_splashlav.png","pattern_blacktail_splashdun.png",               //90
        "pattern_blacktail_splashchoc.png","pattern_blacktail_lav.png","pattern_blacktail_white.png", "pattern_blacktail_dun.png", "pattern_blacktail_choc.png",                //95
        "pattern_penciled.png", "pattern_penciled_blue.png", "pattern_penciled_splash.png","pattern_penciled_splashlav.png","pattern_penciled_splashdun.png",                   //100
        "pattern_penciled_splashchoc.png","pattern_penciled_lav.png", "pattern_penciled_white.png","pattern_penciled_dun.png", "pattern_penciled_choc.png",                     //105
        "pattern_bsinglelace.png","pattern_bsinglelace_blue.png","pattern_bsinglelace_splash.png","pattern_bsinglelace_splashlav.png","pattern_bsinglelace_splashdun.png",      //110
        "pattern_bsinglelace_splashchoc.png", "pattern_bsinglelace_lav.png","pattern_bsinglelace_white.png", "pattern_bsinglelace_dun.png", "pattern_bsinglelace_choc.png",     //115
        "pattern_singlelace.png","pattern_singlelace_blue.png", "pattern_singlelace_splash.png","pattern_singlelace_splashlav.png","pattern_singlelace_splashdun.png",          //120
        "pattern_singlelace_splashchoc.png","pattern_singlelace_lav.png","pattern_singlelace_white.png", "pattern_singlelace_dun.png", "pattern_singlelace_choc.png",           //125
        "pattern_doublelace.png", "pattern_doublelace_blue.png", "pattern_doublelace_splash.png","pattern_doublelace_splashlav.png","pattern_doublelace_splashdun.png",         //130
        "pattern_doublelace_splashchoc.png","pattern_doublelace_lav.png", "pattern_doublelace_white.png","pattern_doublelace_dun.png", "pattern_doublelace_choc.png",           //135
        "pattern_spangled.png","pattern_spangled_blue.png","pattern_spangled_splash.png","pattern_spangled_splashlav.png","pattern_spangled_splashdun.png",                     //140
        "pattern_spangled_splashchoc.png", "pattern_spangled_lav.png","pattern_spangled_white.png", "pattern_spangled_dun.png", "pattern_spangled_choc.png",                    //145
        "pattern_prdgpenciled.png", "pattern_prdgpenciled_blue.png", "pattern_prdgpenciled_splash.png","pattern_prdgpenciled_splashlav.png","pattern_prdgpenciled_splashdun.png",//150
        "pattern_prdgpenciled_splashchoc.png","pattern_prdgpenciled_lav.png", "pattern_prdgpenciled_white.png", "pattern_prdgpenciled_dun.png", "pattern_prdgpenciled_choc.png", //155
        "pattern_spangledhm.png","pattern_spangledhm_blue.png","pattern_spangledhm_splash.png","pattern_spangledhm_splashlav.png","pattern_spangledhm_splashdun.png",            //160
        "pattern_spangledhm_splashchoc.png", "pattern_spangledhm_lav.png","pattern_spangledhm_white.png", "pattern_spangledhm_dun.png", "pattern_spangledhm_choc.png",           //165
        "pattern_bluelaced",    //special case number 170
        "",                     //special case patternless 171
        "48.png"                //test a texture 172
    };
    private static final String[] CHICKEN_TEXTURES_WHITE = new String[] {
        "","white_barred.png","white_mottles.png","white_crested.png"
    };
    private static final String[] CHICKEN_TEXTURES_SHANKS = new String[] {
        "shanks_horn.png","shanks_yellow.png","shanks_willow.png","shanks_black.png",
        "shanks_verywhite.png","shanks_white.png", "shanks_slate.png", "shanks_black.png",
    };
    private static final String[] CHICKEN_TEXTURES_COMB = new String[] {
        "comb_black.png","comb_mulberry.png","comb_red.png"
    };
    private static final String[] CHICKEN_TEXTURES_EYES = new String[] {
        "eyes_albino.png","eyes_black.png"
    };

    private static final Ingredient TEMPTATION_ITEMS = Ingredient.fromItems(Items.WHEAT_SEEDS, Items.MELON_SEEDS, Items.PUMPKIN_SEEDS, Items.BEETROOT_SEEDS);
    public float wingRotation;
    public float destPos;
    public float oFlapSpeed;
    public float oFlap;
    public float wingRotDelta = 1.0F;
    public int timeUntilNextEgg;
    private int grassTimer;
    private int sandBathTimer;
    private EntityAIEatGrass entityAIEatGrass;
    private ECSandBath ecSandBath;

    private static final int WTC = 90;
    private int broodingCount;
    private final List<String> chickenTextures = new ArrayList<>();
    //'father' gene variables list
    private int[] genes = new int[Reference.CHICKEN_GENES_LENGTH];
    private int[] mateGenes = new int[Reference.CHICKEN_GENES_LENGTH];
    private int[] mitosisGenes = new int[Reference.CHICKEN_GENES_LENGTH];
    private int[] mateMitosisGenes = new int[Reference.CHICKEN_GENES_LENGTH];

    public EnhancedChicken(World worldIn) {
        super(ENHANCED_CHICKEN, worldIn);
        this.setSize(0.4F, 0.7F); //I think its the height and width of a chicken
        this.timeUntilNextEgg = this.rand.nextInt(this.rand.nextInt(6000) + 6000); //TODO make some genes to alter these numbers
        this.setPathPriority(PathNodeType.WATER, 0.0F); //TODO investigate what this do and how/if needed
    }

    protected void initEntityAI()
    {
        this.entityAIEatGrass = new EntityAIEatGrass(this);
        this.ecSandBath = new ECSandBath(this);
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIPanic(this, 1.4D));
        this.tasks.addTask(2, new EntityAIMate(this, 1.0D));
        this.tasks.addTask(3, new EntityAITempt(this, 1.0D, false, TEMPTATION_ITEMS));
        this.tasks.addTask(4, new EntityAIFollowParent(this, 1.1D));
        this.tasks.addTask(5, new ECWanderAvoidWater(this, 1.0D));
        this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
        this.tasks.addTask(7, new EntityAIEatGrass(this));//TODO make an animation that suits chickens
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.tasks.addTask(9, new ECRoost(this));

    }

    protected void updateAITasks()
    {
        this.grassTimer = this.entityAIEatGrass.getEatingGrassTimer();
        this.sandBathTimer = this.ecSandBath.getSandBathTimer();
        super.updateAITasks();
    }

    protected void registerData() {
        super.registerData();
        this.dataManager.register(SHARED_GENES, new String());
        this.dataManager.register(ROOSTING, new Boolean(false));
    }

    public void setSharedGenes(int[] genes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < genes.length; i++){
            sb.append(genes[i]);
            if (i != genes.length -1){
                sb.append(",");
            }
        }
        this.dataManager.set(SHARED_GENES, sb.toString());
    }

    public int[] getSharedGenes() {
        String sharedGenes = ((String)this.dataManager.get(SHARED_GENES)).toString();
        int[] sharedGenesArray;
        if(sharedGenes.isEmpty()){
            return null;
        } else {
            String[] genesToSplit = sharedGenes.split(",");
            sharedGenesArray = new int[genesToSplit.length];
            for (int i = 0; i < sharedGenesArray.length; i++) {
                //parse and store each value into int[] to be returned
                sharedGenesArray[i] = Integer.parseInt(genesToSplit[i]);
            }
        }
        return sharedGenesArray;
    }

    public boolean isRoosting() {
        return this.dataManager.get(ROOSTING);
    }

    public void setRoosting(boolean isRoosting) {
        this.dataManager.set(ROOSTING, isRoosting);
    }

    public float getEyeHeight()
    {
        return this.height;
    }

    protected void registerAttributes() {
        super.registerAttributes();
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(3.0D);
        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
    }

    public void livingTick()
    {
        super.livingTick();
        this.oFlap = this.wingRotation;
        this.oFlapSpeed = this.destPos;
        this.destPos = (float)((double)this.destPos + (double)(this.onGround ? -1 : 4) * 0.3D);
        this.destPos = MathHelper.clamp(this.destPos, 0.0F, 1.0F);

        if (!this.onGround && this.wingRotDelta < 1.0F)
        {
            this.wingRotDelta = 1.0F;
        }

        this.wingRotDelta = (float)((double)this.wingRotDelta * 0.9D);

        if (!this.onGround && this.motionY < 0.0D)
        {
            this.motionY *= 0.6D;
        }

        this.wingRotation += this.wingRotDelta * 2.0F;


        if (!this.world.isRemote && !this.isChild() && --this.timeUntilNextEgg <= 0)
        {
            this.playSound(SoundEvents.ENTITY_CHICKEN_EGG, 1.0F, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
//            this.dropItem(Items.EGG, 1);
            this.entityDropItem(getEggColour(resolveEggColour()), 1);
//            this.dropItem(getEggColour(resolveEggColour()), 1); //TODO replace this with the hatching eggs
//            ItemStack eggItem = new ItemStack(getEggColour(resolveEggColour()), 1, 0);
//            eggItem.getCapability(EggCapabilityProvider.EGG_CAP, null).setGenes(getEggGenes());
//            NBTTagCompound nbtTagCompound = eggItem.serializeNBT();
//            eggItem.deserializeNBT(nbtTagCompound);
            this.timeUntilNextEgg = this.rand.nextInt(6000) + 6000;
        }

        if (!this.world.isRemote){
            lethalGenes();
        }

        if (this.world.isRemote) {
            this.grassTimer = Math.max(0, this.grassTimer - 1);
        }

        if(this.grassTimer == 4){
            this.wingRotDelta = 1.0F;
        }

        //TODO find roost and sit on it, after tick 22812 find a suitable roost (horizontal post) that can be reached. If chicken gets to the chosen spot sit, after tick 13000 sit anyways. after tick 22812 stop sitting

        //TODO if "is child" and parent is sitting go under parent, possibly turn off ability to collide.

        //TODO if "is child" and parent is 1 block over or less and doesn't have a passenger ride on parent's back

        //TODO if it is daytime and if this chicken can crow and (it randomly wants to crow OR another chicken near by is crowing) then crow.

    }

    public void fall(float distance, float damageMultiplier)
    {
    }

    public void lethalGenes(){

        if(genes[70] == 2 && genes[71] == 2) {
                this.remove();
        } else if(genes[72] == 2 && genes[73] == 2){
                this.remove();
        }
    }


    public void onDeath(DamageSource cause) {
        super.onDeath(cause);
        if (!this.world.isRemote) {

            float size = 1;

            if (genes[4] == 1 && genes[20] != 3 && genes[21] != 3 && (genes[42] == 1 || genes[43] == 1)) {
                //chicken size
                if(genes[74] == 1){
                    size = size - 0.05F;
                }else if(genes[74] == 2){
                    size = size - 0.025F;
                }
                if(genes[75] == 1){
                    size = size - 0.05F;
                }else if(genes[75] == 2){
                    size = size - 0.025F;
                }

                if(genes[76] == 1 || genes[77] == 1){
                    size = size - 0.05F;
                }else if(genes[76] == 3 && genes[77] == 3){
                    size = size - 0.1F;
                }

                if(genes[78] == 1 || genes[79] == 1){
                    size = size * 0.94F;
                }

                if(genes[7] == 2){
                    size = size * 0.9F;
                }

                if(genes[8] == 2){
                    size = size * 0.75F;
                }

                if (size <= 0.7F) {
                this.entityDropItem(ModItems.RawChicken_DarkSmall, 1);
                } else if (size >= 0.9F) {
                this.entityDropItem(ModItems.RawChicken_DarkBig, 1);
                } else {
                this.entityDropItem(ModItems.RawChicken_Dark, 1);
                }

            } else {

                //chicken size
                if(genes[74] == 1){
                    size = size - 0.05F;
                }else if(genes[74] == 2){
                    size = size - 0.025F;
                }
                if(genes[75] == 1){
                    size = size - 0.05F;
                }else if(genes[75] == 2){
                    size = size - 0.025F;
                }

                if(genes[76] == 1 || genes[77] == 1){
                    size = size - 0.05F;
                }else if(genes[76] == 3 && genes[77] == 3){
                    size = size - 0.1F;
                }

                if(genes[78] == 1 || genes[79] == 1){
                    size = size * 0.94F;
                }

                if(genes[7] == 2){
                    size = size * 0.9F;
                }

                if(genes[8] == 2){
                    size = size * 0.75F;
                }

                if (size <= 0.7F) {
                    this.entityDropItem(ModItems.RawChicken_PaleSmall, 1);
                } else if (size >= 0.9F) {
                    this.entityDropItem(Items.CHICKEN, 1);
                } else {
                    this.entityDropItem(ModItems.RawChicken_Pale, 1);
                }
            }
            this.entityDropItem(Items.FEATHER, 1);

        }
    }


    protected SoundEvent getAmbientSound()
    {
        return SoundEvents.ENTITY_CHICKEN_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn)
    {
        return SoundEvents.ENTITY_CHICKEN_HURT;
    }

    protected SoundEvent getDeathSound()
    {
        return SoundEvents.ENTITY_CHICKEN_DEATH;
    }

    protected void playStepSound(BlockPos pos, Block blockIn)
    {
        this.playSound(SoundEvents.ENTITY_CHICKEN_STEP, 0.15F, 1.0F);
    }

    /**
     * Chicken grass eating and sand bathing
    */

    //TODO make the grass eating actions
    //TODO make the sand bathing actions

    //also provides sand bath bonus
    public void eatGrassBonus()
    {
        if (this.isChild())
        {
            this.addGrowth(60);
        }
    }

    public boolean isBreedingItem(ItemStack stack)
    {
        return TEMPTATION_ITEMS.test(stack);
    }

    @Nullable
    @Override
    public EntityAgeable createChild(EntityAgeable ageable) {
//        if (!this.world.isRemote && !this.isChild() && --this.timeUntilNextEgg <= 0)
//        {
//            this.playSound(SoundEvents.ENTITY_CHICKEN_EGG, 1.0F, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
////            ItemStack itemStack = new ItemStack(getEggColour(resolveEggColour()), 1, 0);
////            this.dropItem(getEggColour(resolveEggColour()), 1);
////            entityDropItem(itemStack, 0.0F);
//            this.timeUntilNextEgg = this.rand.nextInt(6000) + 6000;
//        }
//        return null;
//
//    }
        this.mateGenes = ((EnhancedChicken)ageable).getGenes();
        mixMateMitosisGenes();
        mixMitosisGenes();
        EnhancedChicken enhancedchicken = new EnhancedChicken(this.world);
        enhancedchicken.setGrowingAge(0);
        int[] babyGenes = getEggGenes();
        enhancedchicken.setGenes(babyGenes);
        enhancedchicken.setSharedGenes(babyGenes);
        return enhancedchicken;
    }


    private Item getEggColour(int eggColourGene){

        switch (eggColourGene) {
            case 0:
//                Item item = Item.REGISTRY.getObject(new ResourceLocation("eanimod:egg_white"));
                return ModItems.Egg_White;
//                return new EnhancedEgg("eggWhite", "egg_white");
            case 1:
                return ModItems.Egg_Cream;
//                return new EnhancedEgg ("eggCream", "egg_cream");
            case 2:
                return ModItems.Egg_CreamDark;
//                return new EnhancedEgg ("eggCreamDark", "egg_creamdark");
            case 3:
                return ModItems.Egg_Pink;
//                return new EnhancedEgg ("eggPink", "egg_pink");
            case 4:
                return ModItems.Egg_PinkDark;
//                return new EnhancedEgg ("eggPinkDark", "egg_pinkdark");
            case 5:
                return ModItems.Egg_Brown;
//                return new EnhancedEgg ("eggBrown", "egg_brown");
            case 6:
                return ModItems.Egg_BrownDark;
//                return new EnhancedEgg ("eggBrownDark", "egg_browndark");
            case 7:
                return ModItems.Egg_Blue;
//                return new EnhancedEgg ("eggBlue", "egg_blue");
            case 8:
                return ModItems.Egg_GreenLight;
//                return new EnhancedEgg ("eggGreenLight", "egg_greenlight");
            case 9:
                return ModItems.Egg_Green;
//                return new EnhancedEgg ("eggGreen", "egg_green");
            case 10:
                return ModItems.Egg_Grey;
//                return new EnhancedEgg ("eggGrey", "egg_grey");
            case 11:
                return ModItems.Egg_GreyGreen;
//                return new EnhancedEgg ("eggGreyGreen", "egg_greygreen");
            case 12:
                return ModItems.Egg_Olive;
//                return new EnhancedEgg ("eggOlive", "egg_olive");
            case 13:
                return ModItems.Egg_GreenDark;
//                return new EnhancedEgg ("eggGreenDark", "egg_greendark");
        }

        //TODO set up exception handling and put an exception here we should NEVER get here.
        return null;
    }

    private int resolveEggColour(){
        int eggColour = 0;

        if(genes[5] == 1){

            if(genes[64] == 1 || genes[65] == 1 || genes[66] == 1 || genes[67] == 1){
                //egg is brown
                eggColour = 5;
            }else if((genes[64] == 2 || genes[65] == 2) && (genes[66] == 2 || genes[67] == 2)){
                //egg is brown
                eggColour = 5;
            }else if(genes[66] == 2 || genes[67] == 2){
                //egg is cream
                eggColour = 1;
            }else if(genes[64] == 2 || genes[65] == 2){
                //egg is pink
                eggColour = 3;
            }else if(genes[64] == 3 || genes[65] == 3 || genes[66] == 3 || genes[67] == 3){
                //egg is white
                eggColour = 0;
            }

        }

        //darkens egg if already brown shade
        if(genes[68] == 1 || genes[69] == 1){
            if(eggColour == 0){
                eggColour = 5;
            }else {
                eggColour = eggColour + 1;
            }
        }

        //toggles blue egg version
        if(genes[62] == 1 || genes[63] == 1){
            eggColour = eggColour +7;
        }

        return eggColour;
    }

    @OnlyIn(Dist.CLIENT)
    public String getChickenTexture() {
        if (this.chickenTextures.isEmpty()) {
            this.setTexturePaths();
        }
        return this.chickenTextures.stream().collect(Collectors.joining("/","enhanced_chicken/",""));

    }

    @OnlyIn(Dist.CLIENT)
    public String[] getVariantTexturePaths()
    {
        if (this.chickenTextures.isEmpty()) {
            this.setTexturePaths();
        }

        return this.chickenTextures.stream().toArray(String[]::new);
    }

    @OnlyIn(Dist.CLIENT)
    private void setTexturePaths() {
        int[] genesForText = getSharedGenes();
        if(genesForText!=null){
            int ground = 0;
            int pattern = 0;
            int white = 0;
            int shanks = 2;
            int comb = 2;
            int eyes = 1;
            int ptrncolours = 10; //number of pattern colours

            int Columbian= 3;
            int Melanin= 0;
            int PatternGene= 1;

            boolean isAlbino = false;

            if (genesForText[20] != 1 && genesForText[21] != 1) {                                                                       //checks if not wildtype
                if (genesForText[20] == 2 || genesForText[21] == 2) {                                                                   //sets recessive white or albino
                    //recessive white
                    ground = 5;
                    pattern = 171;
                } else {
                    //albino
                    ground = 5;
                    pattern = 171;
                    white = 0;
                    shanks = 4;
                    comb = 2;
                    eyes = 0;
                    isAlbino = true;
                }
            }else{
                if (genesForText[24] == 5 || genesForText[25] == 5){
                    //extended black tree
                    if (genesForText[24] == 5 && genesForText[25] == 5){
                        if(genesForText[28] == 1 && genesForText[29] == 1 && genesForText[98] == 1 && genesForText[99] == 1){
                            //xtradark birchen
                        }else{
                            //solid black
                        }
                    } else if (genesForText[24] == 1 || genesForText[25] == 1){
                            //xtradark birchen
                    } else {
                        if(genesForText[28] == 1 && genesForText[29] == 1 && genesForText[98] == 1 && genesForText[99] == 1){
                            //leaky black
                        }else{
                            //xtradark birchen
                        }
                    }
                } else if (genesForText[24] == 1 || genesForText[25] == 1){
                    //birchen tree
                    if (genesForText[24] == 1 || genesForText[25] == 1){
                        if (genesForText[28] == 1 && genesForText[29] == 1){
                            if (genesForText[98] == 1 && genesForText[99] == 1){
                                if (genesForText[30] == 1 && genesForText[31] == 1){
                                    if(genesForText[26] == 1 || genesForText[27] == 1){
                                        if(genesForText[100] == 2 && genesForText[101] == 2){
                                            //xtra dark birchen single lace
                                        }else{
                                            //birchen single laced
                                        }
                                    }else{
                                        //extended black patterned columbian
                                    }
                                } else if (genesForText[30] == 1 || genesForText[31] == 1){
                                    if(genesForText[26] == 1 || genesForText[27] == 1){
                                        if(genesForText[100] == 2 && genesForText[101] == 2){
                                            //moorhead doublehalfspangled
                                        }else{
                                            //doublehalfspangle
                                        }

                                    }else{
                                        if(genesForText[100] == 2 && genesForText[101] == 2){
                                            //overly dark columbian
                                        }else{
                                            //moorheaded columbian
                                        }
                                    }
                                }else{
                                    if(genesForText[26] == 1 || genesForText[27] == 1){
                                        if(genesForText[100] == 2 && genesForText[101] == 2){
                                            //moorhead doublehalfspangled
                                        }else{
                                            //doublehalfspangle
                                        }

                                    }else{
                                        if(genesForText[100] == 2 && genesForText[101] == 2){
                                            //moorhead blackpatterned transverse penciled
                                        }else{
                                            //blackpatterned transverse penciled
                                        }
                                    }
                                }
                            } else if (genesForText[98] == 1 || genesForText[99] == 1){
                                if (genesForText[30] == 1 || genesForText[31] == 1){
                                    if (genesForText[26] == 1 || genesForText[27] == 1) {
                                            //dark doublehalfspangle
                                    }else{
                                            //dark messy quail
                                    }
                                }else{
                                    if (genesForText[26] == 1 || genesForText[27] == 1){
                                        if (genesForText[100] == 2 && genesForText[101] == 2){
                                            //dark transverse penciled
                                        }else{
                                            //black patterned incomplete penciled
                                        }
                                    }else{
                                            //dark quail mealy
                                    }

                                }
                            }else{
                                if (genesForText[30] == 1 || genesForText[31] == 1){
                                    if (genesForText[100] == 2 || genesForText[101] == 2){
                                        //solid black
                                    }else {
                                        // leaking black
                                    }
                                }else{
                                    if (genesForText[100] == 1 && genesForText[101] == 1){
                                        //leaking black
                                    }else{
                                        //birchen
                                    }
                                }
                            }
                        }else if (genesForText[28] == 1 || genesForText[29] == 1){
                            if (genesForText[98] == 1 || genesForText[99] == 1){
                                if (genesForText[30] == 1 || genesForText[31] == 1){
                                    if (genesForText[26] == 1 || genesForText[27] == 1){
                                        if(genesForText[100] == 2 && genesForText[101] == 2){
                                            //extended black patterned halfspangle
                                        }else{
                                            //black patterned halfspangle
                                        }
                                    }else{
                                           //incomplete "black patterned" columbian
                                    }
                                }else{
                                    if (genesForText[26] == 1 || genesForText[27] == 1){
                                        if (genesForText[100] == 2 && genesForText[101] == 2){
                                            //extended black patterned transverse penciled
                                        }else{
                                            //black patterned transverse penciled
                                        }
                                    }else{
                                        //black patterned columbian
                                    }
                                }
                            }else{
                                if (genesForText[30] == 1 || genesForText[31] == 1) {
                                        if (genesForText[100] == 2 && genesForText[101] == 2) {
                                            //solid
                                        }else{
                                            //leaking black
                                        }
                                }else{
                                        if (genesForText[100] == 2 && genesForText[101] == 2) {
                                            //leaking black
                                        }else{
                                            //birchen
                                        }
                                }
                            }
                        } if (genesForText[98] == 1 || genesForText[99] == 1){
                            if(genesForText[30] == 1 || genesForText[31] == 1){
                                if(genesForText[26] == 1 || genesForText[27] == 1){
                                    if (genesForText[100] == 2 && genesForText[101] == 2){
                                        //extended black patterened spangled
                                    }else{
                                        //black patterened spangled
                                    }
                                }else{
                                    if (genesForText[100] == 2 && genesForText[101] == 2){
                                        //extended black patterened incomplete quail
                                    }else{
                                        //black patterened incomplete quail
                                    }
                                }
                            }else{
                                if(genesForText[26] == 1 || genesForText[27] == 1){
                                    if (genesForText[100] == 2 && genesForText[101] == 2){
                                        //extended black traverse penciled
                                    }else{
                                        //black traverse penciled
                                    }
                                }else{
                                    if (genesForText[100] == 2 && genesForText[101] == 2){
                                        //extended black patterened incomplete quail
                                    }else{
                                        //black patterened incomplete quail
                                    }
                                }
                            }
                        }else{
                            if(genesForText[30] == 1 || genesForText[31] == 1){
                                //solid black
                            }else{
                                    if (genesForText[100] == 2 && genesForText[101] == 2){
                                        //leaky black
                                    }else{
                                        //birchen
                                }
                            }
                        }
                    }



                }else if (genesForText[24] == 2 || genesForText[25] == 2){
                    //duckwing tree
                    if (genesForText[28] == 1 || genesForText[29] == 1){
                        if (genesForText[98] == 1 || genesForText[99] == 1){
                            if (genesForText[30] == 1 || genesForText[31] == 1){
                                if (genesForText[26] == 1 || genesForText[27] == 1){
                                    if (genesForText[100] == 2 && genesForText[101] == 2){
                                        // extended black patterned halfspangled
                                    }else{
                                        // black patterned halfspangled
                                    }
                                }else{
                                        // black patterned incomplete quail
                                }
                            }else{
                                if (genesForText[26] == 1 || genesForText[27] == 1){
                                    if (genesForText[100] == 2 && genesForText[101] == 2){
                                        // all mutations but melanotic
                                    }else{

                                    }
                                }else{
                                    if (genesForText[100] == 2 && genesForText[101] == 2){
                                        // all mutations but pattern gene and melanotic
                                    }else{

                                    }
                                }
                            }
                        }else{
                            if (genesForText[30] == 1 || genesForText[31] == 1){
                                if (genesForText[26] == 1 || genesForText[27] == 1){
                                    if (genesForText[100] == 2 && genesForText[101] == 2){
                                        // all mutations but darkbrown
                                    }else{

                                    }
                                }else{
                                    if (genesForText[100] == 2 && genesForText[101] == 2){
                                        // all mutations but pattern gene and darkbrown
                                    }else{

                                    }
                                }
                            }else{
                                if (genesForText[26] == 1 || genesForText[27] == 1){
                                    if (genesForText[100] == 2 && genesForText[101] == 2){
                                        // all mutations but melanotic and darkbrown
                                    }else{

                                    }
                                }else{
                                    if (genesForText[100] == 2 && genesForText[101] == 2){
                                        // all mutations but pattern gene and melanotic and darkbrown
                                    }else{

                                    }
                                }
                            }
                        }
                    }else{
                        if (genesForText[98] == 1 || genesForText[99] == 1){
                            if (genesForText[30] == 1 || genesForText[31] == 1){
                                if (genesForText[26] == 1 || genesForText[27] == 1){
                                    if (genesForText[100] == 2 && genesForText[101] == 2){
                                        // all mutations but columbian
                                    }else{

                                    }
                                }else{
                                    if (genesForText[100] == 2 && genesForText[101] == 2){
                                        // all mutations but pattern gene and columbian
                                    }else{

                                    }
                                }
                            }else{
                                if (genesForText[26] == 1 || genesForText[27] == 1){
                                    if (genesForText[100] == 2 && genesForText[101] == 2){
                                        // all mutations but melanotic and columbian
                                    }else{

                                    }
                                }else{
                                    if (genesForText[100] == 2 && genesForText[101] == 2){
                                        // all mutations but pattern gene and melanotic and columbian
                                    }else{

                                    }
                                }
                            }
                        }else{
                            if (genesForText[30] == 1 || genesForText[31] == 1){
                                if (genesForText[26] == 1 || genesForText[27] == 1){
                                    if (genesForText[100] == 2 && genesForText[101] == 2){
                                        // all mutations but darkbrown and columbian
                                    }else{

                                    }
                                }else{
                                    if (genesForText[100] == 2 && genesForText[101] == 2){
                                        // all mutations but pattern gene and darkbrown and columbian
                                    }else{

                                    }
                                }
                            }else{
                                if (genesForText[26] == 1 || genesForText[27] == 1){
                                    if (genesForText[100] == 2 && genesForText[101] == 2){
                                        // all mutations but melanotic and darkbrown and columbian
                                    }else{

                                    }
                                }else{
                                    if (genesForText[100] == 2 && genesForText[101] == 2){
                                        // all mutations but pattern gene and melanotic and darkbrown and columbian
                                    }else{
                                        //full wildtype
                                    }
                                }
                            }
                        }
                    }



                }else if (genesForText[24] == 3 || genesForText[25] == 3){
                    //wheaten tree
                    if (genesForText[24] == 3 && genesForText[25] == 3){

                    }



                }else if (genesForText[24] == 4 || genesForText[25] == 4){
                    //partidge tree
                    if (genesForText[24] == 4 && genesForText[25] == 4){

                    }



                }


                //ground colour tint
                if (genesForText[0] == 1) {
                    //gold
                    ground = ground + 2;
                }
                if (genesForText[0] == 1 && ((genesForText[32] == 3 && genesForText[33] == 3) || (genesForText[36] == 2 && genesForText[37] == 2))) {
                    //lemon or cream but backwards
                    ground = ground + 1;
                }
                if (genesForText[34] == 1 || genesForText[35] == 1) {
                    //mahogany or lemon cream counter
                    ground = ground + 1;
                }

                if (pattern < 170) {
                    //black pattern shade genes
                    //sets pattern to correct positioning pre:variation
                    pattern = (pattern * ptrncolours);
                    if (genesForText[38] == 1 && genesForText[39] == 1) {
                        //domwhite
                        pattern = pattern + 7;
                    } else if (genesForText[38] == 1 || genesForText[39] == 1) {
                        // spotted domwhite
                        pattern = pattern + 7;
                    } else {
                        //if chocolate
                        if (genesForText[1] == 2) {
                            //if lavender
                            if (genesForText[36] == 2 && genesForText[37] == 2) {
                                //is a dun variety
                                //if it is splash
                                if (genesForText[40] == 2 && genesForText[41] == 2) {
                                    //splash dun
                                    pattern = pattern + 4;
                                } else {
                                    //dun
                                    pattern = pattern + 8;
                                }
                            } else {
                                //is a chocolate variety
                                if (genesForText[40] == 2 && genesForText[41] == 2) {
                                    //splash choc
                                    pattern = pattern + 5;
                                } else if (genesForText[40] != 1 || genesForText[41] != 1) {
                                    //dun
                                    pattern = pattern + 8;
                                } else {
                                    //chocolate
                                    pattern = pattern + 9;
                                }
                            }
                        } else {
                            //if lavender
                            if (genesForText[36] == 2 && genesForText[37] == 2) {
                                //is a lavender variety
                                //if it is splash
                                if (genesForText[40] == 2 && genesForText[41] == 2) {
                                    //splash lavender
                                    pattern = pattern + 3;
                                } else {
                                    //lavender
                                    pattern = pattern + 6;
                                }
                            } else {
                                //is a black variety
                                if (genesForText[40] == 2 && genesForText[41] == 2) {
                                    //splash
                                    pattern = pattern + 2;
                                } else if (genesForText[40] == 2 || genesForText[41] == 2) {
                                    //blue
                                    if (genesForText[15] == 4 && genesForText[16] == 4 && (genesForText[30] == 1 && genesForText[31] == 1)) {
                                        //blue laced ... super special gene combo for blue andalusian type pattern
                                        pattern = 110;
                                        ground = 20;
                                    } else {
                                        //blue
                                        pattern = pattern + 1;
                                    }

                                }
                            }
                        }
                    }
                }
            }

                //white marking genesForText
                if (genesForText[3] == 2) {
                    //Barred
                    white = 1;
                } else {
                    if (genesForText[22] == 2 && genesForText[23] == 2) {
                        //mottled
                        white = 2;
                    } else {
                        if (pattern > 8 && Melanin != 2 && (genesForText[54] != 3 && genesForText[55] != 3) && genesForText[6] == 2) {
                            //white crest
                            white = 3;
                        }
                    }
                }

                // figures out the shank, comb, and skin colour if its not albino
                if (!isAlbino) {
                    //gets comb colour
                    if (genesForText[4] == 1 && (genesForText[42] == 1 || genesForText[43] == 1)) {
                        //comb and shanks are black
                        comb = 0;
                        shanks = 3;
                    }
                    if (genesForText[24] == 1 && genesForText[25] == 1) {
                        shanks = 3;
                        // makes mulbery comb
                        if (genesForText[30] == 2) {
                            comb = 1;
                        }
                    }
                    //shanks starts at 3 btw
                    // if Dilute is Dilute and the shanks arnt darkened by extened black lighten by 1 shade
                    if ((genesForText[24] != 1 && genesForText[25] != 1) && (genesForText[32] == 1 || genesForText[33] == 1)) {
                        shanks--;
                    }

                    //if barred or mottled lighten by 1 shade
                    if(genesForText[3] == 2 || (genesForText[22] == 2 && genesForText[23] == 2)){
                        shanks--;
                    }

                    // if dominant white or lavender lighten by 1 shade
                    if ((genesForText[38] == 1 && genesForText[39] == 1) || (genesForText[36] == 1 && genesForText[37] == 1)) {
                        shanks--;
                    }

                    // if splash or blue lighten by 1 shade
                    if (genesForText[40] == 2 || genesForText[41] == 2) {
                        shanks--;
                    }

                    //if its melanized
                    if (Melanin == 2) {
                        shanks++;
                    }

                    //TODO replace this with a new r.black shank gene
                    // if columbian toggle doesnt matter darken by 1
                    if ((genesForText[2] == 1 && genesForText[28] == 2 && genesForText[29] == 2) || (genesForText[2] == 2 && genesForText[28] == 1 && genesForText[29] == 1)) {
                        shanks++;
                    }

                    //makes sure its not off the chart
                    if (shanks < 0) {
                        shanks = 0;
                    } else if (shanks > 3) {
                        shanks = 3;
                    }

                    //lightens comb to mulberry if lightness is extreme enough
                    if (shanks < 2 && comb == 0) {
                        comb = 1;
                    }

                    //makes the shanks and beak their white or yellow varient
                    if (genesForText[44] == 1 || genesForText[45] == 1) {
                        shanks = shanks + 4;
                    }

        }



//            after finished genesForText
            this.chickenTextures.add(CHICKEN_TEXTURES_GROUND[11]);
            if (pattern != 171){
                this.chickenTextures.add(CHICKEN_TEXTURES_PATTERN[172]);
            }
            if (white!= 0){
                this.chickenTextures.add(CHICKEN_TEXTURES_WHITE[white]);
            }
            this.chickenTextures.add(CHICKEN_TEXTURES_SHANKS[shanks]);
            this.chickenTextures.add(CHICKEN_TEXTURES_COMB[comb]);
            this.chickenTextures.add(CHICKEN_TEXTURES_EYES[eyes]);
        }
    }




    public void writeAdditional(NBTTagCompound compound) {
        super.writeAdditional(compound);

        //store this chicken's genes
        NBTTagList geneList = new NBTTagList();
        for(int i = 0; i< genes.length; i++){
            NBTTagCompound nbttagcompound = new NBTTagCompound();
            nbttagcompound.setInt("Gene", genes[i]);
            geneList.add(nbttagcompound);
        }
        compound.setTag("Genes", geneList);

        //store this chicken's mate's genes
        NBTTagList mateGeneList = new NBTTagList();
        for(int i = 0; i< mateGenes.length; i++){
            NBTTagCompound nbttagcompound = new NBTTagCompound();
            nbttagcompound.setInt("Gene", mateGenes[i]);
            mateGeneList.add(nbttagcompound);
        }
        compound.setTag("FatherGenes", mateGeneList);
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
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

    public void mixMateMitosisGenes(){
        punnetSquare(mateMitosisGenes, mateGenes);
    }

    public void punnetSquare(int[] mitosis, int[] parentGenes) {
        Random rand = new Random();
        for (int i = 20; i < genes.length; i = (i+2) ) {
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


    public int[] getEggGenes() {
        Random rand = new Random();
        int[] eggGenes = new int[Reference.CHICKEN_GENES_LENGTH];

        for(int i =0; i< 20; i++) {
            boolean thisOrMate = rand.nextBoolean();
            if (thisOrMate){
                eggGenes[i] = genes[i];
            } else {
                eggGenes[i] = mateGenes[i];
            }
        }

        for(int i =20; i< genes.length; i++) {
            boolean thisOrMate = rand.nextBoolean();
            if (thisOrMate){
                eggGenes[i] = mitosisGenes[i];
            } else {
                eggGenes[i] = mateMitosisGenes[i];
            }
        }

        return eggGenes;
    }

    @Nullable
    @Override
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData entityLivingData, @Nullable NBTTagCompound itemNbt) {
        entityLivingData = super.onInitialSpawn(difficulty, entityLivingData, itemNbt);
        int[] spawnGenes;

        if (entityLivingData instanceof EnhancedChicken.GroupData) {
            spawnGenes = ((GroupData)entityLivingData).groupGenes;
        } else {
            spawnGenes = createInitialGenes();
            entityLivingData = new GroupData(spawnGenes);
        }

        this.genes = spawnGenes;
        setSharedGenes(genes);
        return entityLivingData;
    }

    private int[] createInitialGenes() {
        int[] initialGenes = new int[Reference.CHICKEN_GENES_LENGTH];
        //TODO create biome WTC variable [hot and dry biomes, hot and wet biomes, cold biomes] WTC is all others


            //[ 0=minecraft wildtype, 1=jungle wildtype, 2=savanna wildtype, 3=cold wildtype, 4=swamp wildtype ]
            int wildType = 0;
            Biome biome = this.world.getBiome(new BlockPos(this));

            if (biome.getDefaultTemperature() >= 0.9F && biome.getDownfall() > 0.8F) // hot and wet (jungle)
            {
                wildType  = 1;
            }
            else if (biome.getDefaultTemperature() >= 0.9F && biome.getDownfall() < 0.3F) // hot and dry (savanna)
            {
                wildType = 2;
            }
            else if (biome.getDefaultTemperature() < 0.3F ) // cold (mountains)
            {
                wildType = 3;
            }
            else if (biome.getDefaultTemperature() >= 0.8F && biome.getDownfall() > 0.8F)
            {
                wildType = 4;
            }

/**
 * parent linked genes
 */
        //Gold [ gold, silver ]
        if(ThreadLocalRandom.current().nextInt(100)>WTC){
            initialGenes[0] = (ThreadLocalRandom.current().nextInt(2)+1);

        } else {
            if(wildType == 3){
                //cold biome silver variation
                initialGenes[0] = (2);
            }else {
                initialGenes[0] = (1);
            }
        }

        //Chocolate [ wildtype, chocolate ]
        if(ThreadLocalRandom.current().nextInt(100)>(WTC+((100-WTC)/1.2))){
            initialGenes[1] = (ThreadLocalRandom.current().nextInt(2)+1);

        } else {
            initialGenes[1] = (1);
        }

        //TODO make this into ear whiteness gene [ large white, little white, no white ]
        if(ThreadLocalRandom.current().nextInt(100)>WTC){
            initialGenes[2] = (ThreadLocalRandom.current().nextInt(2)+1);
        } else {
            if(wildType == 1 || wildType == 2){
                initialGenes[2] = (1);
            }else{
                initialGenes[2] = (2);
            }
        }

        //Barred [ wildtype, barred ] //exclusive to savanna
        if(ThreadLocalRandom.current().nextInt(100)>WTC && wildType == 2){
            initialGenes[3] = (ThreadLocalRandom.current().nextInt(2)+1);
        } else {
            initialGenes[3] = (1);
        }

        //Fibromelanin Suppressor [ wildtype, suppressor ]
        if(ThreadLocalRandom.current().nextInt(100)>WTC){
            initialGenes[4] = (ThreadLocalRandom.current().nextInt(2)+1);
        } else {
            initialGenes[4] = (1);
        }

        //Brown egg gene suppressor [ wildtype, suppressor ]
        if(ThreadLocalRandom.current().nextInt(100)>WTC){
            initialGenes[5] = (ThreadLocalRandom.current().nextInt(2)+1);

        } else {
            initialGenes[5] = (1);
        }

        //white head
        if(ThreadLocalRandom.current().nextInt(100)>WTC){
            initialGenes[6] = (ThreadLocalRandom.current().nextInt(2)+1);

        } else {
            initialGenes[6] = (1);
        }

        //dwarf [ normal, slight dwarf ]
        if(ThreadLocalRandom.current().nextInt(100)>WTC){
            initialGenes[7] = (ThreadLocalRandom.current().nextInt(3)+1);

        } else {
            initialGenes[7] = (1);
        }

        //dwarf 2 [ normal, very dwarf ]
        if(ThreadLocalRandom.current().nextInt(100)>WTC){
            initialGenes[8] = (ThreadLocalRandom.current().nextInt(3)+1);

        } else {
            initialGenes[8] = (1);
        }

/**
* unused parent linked genes
*/

        for (int i = 9; i < 20; i++ ) {
            initialGenes[i] = (10);
        }

/**
* normal genes start with 20
*/

        //Recessive white [ wild, recessive white, albino ]  //mutation common in temperate areas and swamps
        if(ThreadLocalRandom.current().nextInt(100)>WTC || wildType == 4){
                if(ThreadLocalRandom.current().nextInt(200)>199){
                   initialGenes[20] = (3);
                }else {
                    initialGenes[20] = (ThreadLocalRandom.current().nextInt(2) + 1);
                }
            } else {
                if(wildType == 0){
                    initialGenes[20] = (2);
                }else {
                    initialGenes[20] = (1);
                }
        }
        if(ThreadLocalRandom.current().nextInt(100)>WTC || wildType == 4){
            if(ThreadLocalRandom.current().nextInt(200)>199){
                initialGenes[21] = (3);
            }else {
                initialGenes[21] = (ThreadLocalRandom.current().nextInt(2) + 1);
            }
        } else {
            if(wildType == 0){
                initialGenes[21] = (2);
            }else {
                initialGenes[21] = (1);
            }
        }

        //Mottled [ wildtype, mottled ]  // cold biome exclusive
        if(ThreadLocalRandom.current().nextInt(100)>WTC && wildType == 3){
            initialGenes[22] = (ThreadLocalRandom.current().nextInt(2)+1);
        }else{
            initialGenes[22] = (1);
        }
        if(ThreadLocalRandom.current().nextInt(100)>WTC && wildType == 3){
            initialGenes[23] = (ThreadLocalRandom.current().nextInt(2)+1);
        } else {
            initialGenes[23] = (1);
        }

        //Dlocus [ birchen, duckwing, wheaten, partridge, extended black ]
        //swamps have random Dlocus genes
        if(ThreadLocalRandom.current().nextInt(100)>WTC){
            initialGenes[24] = (ThreadLocalRandom.current().nextInt(5)+1);
        } else {
            // swamps have a mixture but no black
            if(wildType == 4){
                initialGenes[24] = (ThreadLocalRandom.current().nextInt(3)+2);
            }
            // partridge is savanna wild type
            else if (wildType == 2) {
                initialGenes[24] = (4);
            // birchen and extended black is cold biome wildtype
            }else if(wildType == 3){
                if(ThreadLocalRandom.current().nextInt(3) == 0){
                    initialGenes[24] = (5);
                }else{
                    initialGenes[24] = (1);
                }
            // duckwing is jungle "true" wildtype
            }else{
                initialGenes[24] = (2);
            }
        }
        if(ThreadLocalRandom.current().nextInt(100)>WTC){
        initialGenes[25] = (ThreadLocalRandom.current().nextInt(4)+1);
        } else {
            // swamps have a mixture but no black
            if(wildType == 4){
                initialGenes[25] = (ThreadLocalRandom.current().nextInt(3)+2);
            }
            // partridge is savanna wild type
            else if (wildType == 2) {
                initialGenes[25] = (4);
            // birchen is cold biome wildtype
            }else if(wildType == 3){
                if(ThreadLocalRandom.current().nextInt(3) == 0){
                    initialGenes[25] = (5);
                }else{
                    initialGenes[25] = (1);
                }
            // duckwing is jungle "true" wildtype
            }else{
                initialGenes[25] = (2);
            }
        }

        //Pattern Gene [ pattern, wildtype ] pattern gene is common in savannas
        if(wildType == 2){
            if(ThreadLocalRandom.current().nextInt(100)>(WTC/2)) {
                initialGenes[26] = (ThreadLocalRandom.current().nextInt(2) + 1);
            }else{
                initialGenes[26] = (2);
            }
        }else {
            if (ThreadLocalRandom.current().nextInt(100) > WTC) {
                initialGenes[26] = (ThreadLocalRandom.current().nextInt(2) + 1);

            } else {
                initialGenes[26] = (2);
            }
        }
        if(wildType == 2){
            if(ThreadLocalRandom.current().nextInt(100)>(WTC/2)) {
                initialGenes[27] = (ThreadLocalRandom.current().nextInt(2) + 1);
            }else{
                initialGenes[27] = (2);
            }
        }else {
            if (ThreadLocalRandom.current().nextInt(100) > WTC) {
                initialGenes[27] = (ThreadLocalRandom.current().nextInt(2) + 1);
            } else {
                initialGenes[27] = (2);
            }
        }


        //Colombian [ colombian, wildtype ]
        if(ThreadLocalRandom.current().nextInt(100)>WTC || wildType == 3){
            initialGenes[28] = (ThreadLocalRandom.current().nextInt(2)+1);

        } else {
            initialGenes[28] = (3);
        }
        if(ThreadLocalRandom.current().nextInt(100)>WTC){
            initialGenes[29] = (ThreadLocalRandom.current().nextInt(2)+1);

        } else {
            initialGenes[29] = (3);
        }

        //Melanized [melanized, wildtype ]
        if(ThreadLocalRandom.current().nextInt(100)>WTC){
            initialGenes[30] = (ThreadLocalRandom.current().nextInt(2)+1);

        } else {
            initialGenes[30] = (3);
        }
        if(ThreadLocalRandom.current().nextInt(100)>WTC){
            initialGenes[31] = (ThreadLocalRandom.current().nextInt(2)+1);

        } else {
            initialGenes[31] = (3);
        }

        //Dilute [ dilute, cream, wildtype ] // more common in swamps
        if(ThreadLocalRandom.current().nextInt(100)>WTC || wildType == 4){
            initialGenes[32] = (ThreadLocalRandom.current().nextInt(3)+1);

        } else {
            initialGenes[32] = (3);
        }
        if(ThreadLocalRandom.current().nextInt(100)>WTC || wildType == 4){
            initialGenes[33] = (ThreadLocalRandom.current().nextInt(3)+1);

        } else {
            initialGenes[33] = (3);
        }

        //Mahogany [ mahogany, wildtype ]
        if(ThreadLocalRandom.current().nextInt(100)>WTC){
            initialGenes[34] = (ThreadLocalRandom.current().nextInt(2)+1);

        } else {
            if(wildType == 2){
                initialGenes[34] = (1);
            }else {
                initialGenes[34] = (2);
            }
        }
        if(ThreadLocalRandom.current().nextInt(100)>WTC){
            initialGenes[35] = (ThreadLocalRandom.current().nextInt(2)+1);

        } else {
            initialGenes[35] = (2);
        }

        //Lavender [ wildtype, lavender ]
        if(ThreadLocalRandom.current().nextInt(100)>WTC){
            initialGenes[36] = (ThreadLocalRandom.current().nextInt(2)+1);

        } else {
            initialGenes[36] = (1);
        }
        if(ThreadLocalRandom.current().nextInt(100)>WTC){
            initialGenes[37] = (ThreadLocalRandom.current().nextInt(2)+1);

        } else {
            initialGenes[37] = (1);
        }

        //Dominant White [ dominant white, wildtype ]
        if(ThreadLocalRandom.current().nextInt(100)>WTC || (wildType == 3)){
            initialGenes[38] = (ThreadLocalRandom.current().nextInt(2)+1);
        } else {
            initialGenes[38] = (2);
        }
        if(ThreadLocalRandom.current().nextInt(100)>WTC || (wildType == 3)){
            initialGenes[39] = (ThreadLocalRandom.current().nextInt(2)+1);
        } else {
            initialGenes[39] = (2);
        }

        //Splash [ black, splash ]
        if(ThreadLocalRandom.current().nextInt(100)>(WTC+((100-WTC)/2))){
            initialGenes[40] = (ThreadLocalRandom.current().nextInt(2)+1);

        } else {
            initialGenes[40] = (1);
        }
        if(ThreadLocalRandom.current().nextInt(100)>(WTC+((100-WTC)/2))){
            initialGenes[41] = (ThreadLocalRandom.current().nextInt(2)+1);

        } else {
            initialGenes[41] = (1);
        }

        //Fibromelanin [ fibromelanin, wildtype ] // fibro is more common in savannas but still rare
        if(wildType == 2){
            if(ThreadLocalRandom.current().nextInt(100)>WTC){
                initialGenes[42] = (ThreadLocalRandom.current().nextInt(2)+1);
            } else {
                initialGenes[42] = (2);
            }
            if(ThreadLocalRandom.current().nextInt(100)>WTC){
                initialGenes[43] = (ThreadLocalRandom.current().nextInt(2)+1);
            } else {
                initialGenes[43] = (2);
            }
        }else{
            if(ThreadLocalRandom.current().nextInt(100)>(WTC+((100-WTC)/1.1))){
                initialGenes[42] = (ThreadLocalRandom.current().nextInt(2)+1);
            } else {
                initialGenes[42] = (2);
            }
            if(ThreadLocalRandom.current().nextInt(100)>(WTC+((100-WTC)/1.1))){
                initialGenes[43] = (ThreadLocalRandom.current().nextInt(2)+1);
            } else {
                initialGenes[43] = (2);
            }
        }

        //yellow shanks [ white, yellow ]
        if((ThreadLocalRandom.current().nextInt(100)>(WTC+((100-WTC)/2)) && wildType != 0) || wildType == 4){
            initialGenes[44] = (ThreadLocalRandom.current().nextInt(2)+1);

        } else {
            if(wildType == 1) {
                initialGenes[44] = (1);
            }else{
                initialGenes[44] = (2);
            }
        }       //homozygous white legs only in jungle
        if(ThreadLocalRandom.current().nextInt(100)>(WTC+((100-WTC)/2)) && wildType == 1){
            initialGenes[45] = (ThreadLocalRandom.current().nextInt(2)+1);
        } else {
            if(wildType == 1) {
                initialGenes[45] = (1);
            }else{
                initialGenes[45] = (2);
            }
        }

        //Rose [ rose, rose2, wildtype ]
        if(ThreadLocalRandom.current().nextInt(100)>WTC){
            initialGenes[46] = (ThreadLocalRandom.current().nextInt(3)+1);

        } else {
            initialGenes[46] = (3);
        }
        if(ThreadLocalRandom.current().nextInt(100)>WTC){
            initialGenes[47] = (ThreadLocalRandom.current().nextInt(2)+1);

        } else {
            initialGenes[47] = (3);
        }

        //Pea [ pea, wildtype ]
        if((ThreadLocalRandom.current().nextInt(100)>WTC && (wildType == 0 || wildType == 3))){
            initialGenes[48] = (ThreadLocalRandom.current().nextInt(2)+1);

        } else {
            if(wildType == 3){
                initialGenes[48] = (1);
            }else {
                initialGenes[48] = (2);
            }
        }
        if((ThreadLocalRandom.current().nextInt(100)>WTC && (wildType == 0 || wildType == 3))){
            initialGenes[49] = (ThreadLocalRandom.current().nextInt(2)+1);
        } else {
            if(wildType == 3){
                initialGenes[49] = (1);
            }else {
                initialGenes[49] = (2);
            }
        }

        //Duplex comb or v comb [ wildtype, duplex ]   // reversed dominance, cold biome exclusive
        if((ThreadLocalRandom.current().nextInt(100)>WTC) && wildType == 3){
            initialGenes[50] = (ThreadLocalRandom.current().nextInt(2)+1);
        } else {
            initialGenes[50] = (1);
        }
        if((ThreadLocalRandom.current().nextInt(100)>WTC) && wildType == 3){
            initialGenes[51] = (ThreadLocalRandom.current().nextInt(2)+1);
        } else {
                initialGenes[51] = (1);
        }

        //Naked neck [ naked neck, wildtype ] // savanna exclusive
        if(ThreadLocalRandom.current().nextInt(100)>WTC && wildType == 2){
            initialGenes[52] = (ThreadLocalRandom.current().nextInt(2)+1);
        } else {
            initialGenes[52] = (2);
        }
        //no wild homozygous naked neck
            initialGenes[53] = (2);


        //Crest [ normal crest, forward crest, wildtype ]
        if(wildType == 3 && ThreadLocalRandom.current().nextInt(100)>WTC){
            initialGenes[54] = (ThreadLocalRandom.current().nextInt(3)+1);
        } else {
            initialGenes[54] = (3);
        }
        if(wildType == 3 && ThreadLocalRandom.current().nextInt(100)>WTC){
            initialGenes[55] = (ThreadLocalRandom.current().nextInt(3)+1);
        } else {
            initialGenes[55] = (3);
        }

        //beard [ beard, wildtype ]
        if(wildType == 3 && ThreadLocalRandom.current().nextInt(100)>WTC){
            initialGenes[56] = (ThreadLocalRandom.current().nextInt(2)+1);

        } else {
            initialGenes[56] = (2);
        }
        if(wildType == 3 && ThreadLocalRandom.current().nextInt(100)>WTC){
            initialGenes[57] = (ThreadLocalRandom.current().nextInt(2)+1);

        } else {
            initialGenes[57] = (2);
        }

        //Foot feather 1 [ small foot feather, big foot feather, wildtype ]
        if(wildType == 3 && ThreadLocalRandom.current().nextInt(100)>(WTC/2)){
            initialGenes[58] = (ThreadLocalRandom.current().nextInt(3)+1);
        } else {
            initialGenes[58] = (3);
        }
        if(wildType == 3 && ThreadLocalRandom.current().nextInt(100)>(WTC/2)){
            initialGenes[59] = (ThreadLocalRandom.current().nextInt(3)+1);
        } else {
            initialGenes[59] = (3);
        }

        //Foot feather enhancer [ enhancer, wildtype ]
        if(ThreadLocalRandom.current().nextInt(100)>WTC || wildType == 3){
            initialGenes[60] = (ThreadLocalRandom.current().nextInt(2)+1);

        } else {
            initialGenes[60] = (2);
        }
        if(ThreadLocalRandom.current().nextInt(100)>WTC || wildType == 3){
            initialGenes[61] = (ThreadLocalRandom.current().nextInt(2)+1);

        } else {
            initialGenes[61] = (2);
        }

        //Blue eggs [ blue, wildtype ] // swamp exclusive
        if(wildType == 4){
            initialGenes[62] = (ThreadLocalRandom.current().nextInt(2)+1);

        } else {
            initialGenes[62] = (2);
        }
        if(wildType == 4){
            initialGenes[63] = (ThreadLocalRandom.current().nextInt(2)+1);

        } else {
            initialGenes[63] = (2);
        }

        //Brown Pink eggs [ brown, pink, wildtype ] //pink more likely in savanna
        if(ThreadLocalRandom.current().nextInt(100)>WTC){
            initialGenes[64] = (ThreadLocalRandom.current().nextInt(3)+1);

        } else {
            if(wildType == 2){
                initialGenes[64] = (2);
            }else {
                initialGenes[64] = (3);
            }
        }
        if(ThreadLocalRandom.current().nextInt(100)>WTC){
            initialGenes[65] = (ThreadLocalRandom.current().nextInt(3)+1);

        } else {
            if(wildType == 2){
                initialGenes[65] = (2);
            }else {
                initialGenes[65] = (3);
            }
        }

        //Brown Cream eggs [ brown, cream, wildtype ]
        if(ThreadLocalRandom.current().nextInt(100)>WTC || wildType == 4){
            initialGenes[66] = (ThreadLocalRandom.current().nextInt(3)+1);

        } else {
            if(wildType == 1 || wildType == 2){
                initialGenes[66] = (3);
            }else {
                initialGenes[66] = (2);
            }
        }
        if(ThreadLocalRandom.current().nextInt(100)>WTC){
            initialGenes[67] = (ThreadLocalRandom.current().nextInt(3)+1);

        } else {
            if(wildType == 1){
                initialGenes[67] = (3);
            }else {
                initialGenes[67] = (2);
            }
        }

        //Darker eggs [ darker, wildtype ] // darker is more probable in swamps but still rare
        if(wildType == 4){
            if (ThreadLocalRandom.current().nextInt(100) > WTC) {
                initialGenes[68] = (ThreadLocalRandom.current().nextInt(2) + 1);
            } else {
                initialGenes[68] = (2);
            }
            if (ThreadLocalRandom.current().nextInt(100) > WTC) {
                initialGenes[69] = (ThreadLocalRandom.current().nextInt(2) + 1);
            } else {
                initialGenes[69] = (2);
            }
        }else {
            if (ThreadLocalRandom.current().nextInt(100) > (WTC + ((100 - WTC) / 2)) ) {
                initialGenes[68] = (ThreadLocalRandom.current().nextInt(2) + 1);

            } else {
                initialGenes[68] = (2);
            }
            if (ThreadLocalRandom.current().nextInt(100) > (WTC + ( (100 - WTC) / 2)) ) {
                initialGenes[69] = (ThreadLocalRandom.current().nextInt(2) + 1);

            } else {
                initialGenes[69] = (2);
            }
        }

        //creeper gene [ wildtype, creeper ] (short legs not exploding bushes)
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[70] = (ThreadLocalRandom.current().nextInt(2) + 1);
            initialGenes[71] = (1);
        }else{
            initialGenes[70] = (1);
            initialGenes[71] = (1);
        }

        //rumpless [ wildtype, rumpless ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[72] = (ThreadLocalRandom.current().nextInt(2) + 1);
            initialGenes[73] = (1);
        }else{
            initialGenes[72] = (1);
            initialGenes[73] = (1);
        }

        //base size [ smaller, wildtype, larger ] incomplete dominant
        if (ThreadLocalRandom.current().nextInt(100) > WTC/4) {
            initialGenes[74] = (ThreadLocalRandom.current().nextInt(3) + 1);
        }else{
            initialGenes[74] = (2);
        }

        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[75] = (ThreadLocalRandom.current().nextInt(3) + 1);
        }else{
            initialGenes[75] = (2);
        }

        //Size subtraction [ smaller, normal+, smallest ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[76] = (ThreadLocalRandom.current().nextInt(3) + 1);
        }else{
            initialGenes[76] = (2);
        }

        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[77] = (ThreadLocalRandom.current().nextInt(3) + 1);
        }else{
            initialGenes[77] = (2);
        }

        //Size multiplier [ normal+, larger ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[78] = (ThreadLocalRandom.current().nextInt(2) + 1);
            initialGenes[79] = (1);
        }else{
            initialGenes[78] = (1);
            initialGenes[79] = (1);
        }

        //small comb [ small, normal+ ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[80] = (ThreadLocalRandom.current().nextInt(2) + 1);
            initialGenes[81] = (2);
        }else{
            initialGenes[80] = (2);
            initialGenes[81] = (2);
        }

        //large comb [ large, normal+ ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[82] = (ThreadLocalRandom.current().nextInt(2) + 1);
            initialGenes[83] = (2);
        }else{
            initialGenes[82] = (2);
            initialGenes[83] = (2);
        }

        //waddle reducer [ small, normal+ ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[84] = (ThreadLocalRandom.current().nextInt(2) + 1);
            initialGenes[85] = (2);
        }else{
            initialGenes[84] = (2);
            initialGenes[85] = (2);
        }

        //wing placement near back [ centered+, up on back, centered2 ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[86] = (ThreadLocalRandom.current().nextInt(3) + 1);
            initialGenes[87] = (1);
        }else{
            initialGenes[86] = (1);
            initialGenes[87] = (1);
        }

        //wings down [ centered+, tilted down, pointed down ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[88] = (ThreadLocalRandom.current().nextInt(3) + 1);
            initialGenes[89] = (1);
        }else{
            initialGenes[88] = (1);
            initialGenes[89] = (1);
        }

        //wing length [ normal+, 5 short ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[90] = (ThreadLocalRandom.current().nextInt(2) + 1);
            initialGenes[91] = (1);
        }else {
            initialGenes[90] = (1);
            initialGenes[91] = (1);
        }

        //wing thickness [ normal+, 3 wide ]
            if (ThreadLocalRandom.current().nextInt(100) > WTC) {
                initialGenes[92] = (ThreadLocalRandom.current().nextInt(2) + 1);
                initialGenes[93] = (1);
            }else {
                initialGenes[92] = (1);
                initialGenes[93] = (1);
        }

        //wing angle multiplier [none+, 1.1, 1.5]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[94] = (ThreadLocalRandom.current().nextInt(3) + 1);
        }else{
            initialGenes[94] = (1);
        }

        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[95] = (ThreadLocalRandom.current().nextInt(3) + 1);
        }else{
            initialGenes[95] = (1);
        }

        //wing angle multiplier 2 [none+, 1.1, 1.5]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[96] = (ThreadLocalRandom.current().nextInt(3) + 1);
        }else{
            initialGenes[96] = (1);
        }

        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[97] = (ThreadLocalRandom.current().nextInt(3) + 1);
        }else{
            initialGenes[97] = (1);
        }

        // Darkbrown [ darkbrown, wildtype ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[98] = (ThreadLocalRandom.current().nextInt(3) + 1);
        }else{
            initialGenes[98] = (2);
        }

        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[99] = (ThreadLocalRandom.current().nextInt(3) + 1);
        }else{
            initialGenes[99] = (2);
        }

        // Charcoal [ wildtype, charcoal ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[100] = (ThreadLocalRandom.current().nextInt(3) + 1);
        }else{
            initialGenes[100] = (1);
        }

        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[101] = (ThreadLocalRandom.current().nextInt(3) + 1);
        }else{
            initialGenes[101] = (1);
        }

    // TODO here: genes for egg hatch chance when thrown, egg laying rate, and chicken ai modifiers

        return initialGenes;
    }

    public void setGenes(int[] genes) {
        this.genes = genes;
    }

    public int[] getGenes(){
        return this.genes;
    }

    public static class GroupData implements IEntityLivingData
    {

        public int[] groupGenes;
        public GroupData(int[] groupGenes)
        {
            this.groupGenes = groupGenes;
        }

    }
}

