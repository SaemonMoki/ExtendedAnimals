package mokiyoki.enhancedanimals.entity;

import mokiyoki.enhancedanimals.ai.general.EnhancedPanicGoal;
import mokiyoki.enhancedanimals.ai.general.EnhancedTemptGoal;
import mokiyoki.enhancedanimals.ai.general.EnhancedWanderingGoal;
import mokiyoki.enhancedanimals.ai.general.GrazingGoal;
import mokiyoki.enhancedanimals.ai.general.cow.EnhancedAINurseFromMotherGoal;
import mokiyoki.enhancedanimals.entity.Genetics.CowGeneticsInitialiser;
import mokiyoki.enhancedanimals.entity.util.Colouration;
import mokiyoki.enhancedanimals.init.ModBlocks;
import mokiyoki.enhancedanimals.init.ModItems;
import mokiyoki.enhancedanimals.config.EanimodCommonConfig;
import mokiyoki.enhancedanimals.util.Genes;
import mokiyoki.enhancedanimals.util.Reference;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.BreedGoal;
import net.minecraft.entity.ai.goal.FollowParentGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import static mokiyoki.enhancedanimals.util.handlers.EventRegistry.ENHANCED_COW;

public class EnhancedCow extends EnhancedAnimalRideableAbstract implements EnhancedAnimal {

    //avalible UUID spaces : [ S X X X X 5 6 7 - 8 9 10 11 - 12 13 14 15 - 16 17 18 19 - 20 21 22 23 24 25 26 27 28 29 30 31 ]

//    private static final DataParameter<Boolean> SADDLED = EntityDataManager.createKey(EnhancedCow.class, DataSerializers.BOOLEAN);
//    private static final DataParameter<Integer> BOOST_TIME = EntityDataManager.createKey(EnhancedCow.class, DataSerializers.VARINT);
    protected static final DataParameter<Boolean> RESET_TEXTURE = EntityDataManager.createKey(EnhancedCow.class, DataSerializers.BOOLEAN);
    private static final DataParameter<String> MOOSHROOM_UUID = EntityDataManager.createKey(EnhancedCow.class, DataSerializers.STRING);
//    private static final DataParameter<String> HORN_ALTERATION = EntityDataManager.<String>createKey(EnhancedCow.class, DataSerializers.STRING);

    private static final String[] COW_TEXTURES_BASE = new String[] {
            "solid_white.png", "solid_lightcream.png", "solid_cream.png", "solid_silver.png"
    };

    private static final String[] COW_TEXTURES_UDDER = new String[] {
            "udder_black.png", "udder_brown.png", "udder_pink.png"
    };

    private static final String[] COW_TEXTURES_RED = new String[] {
            "", "r_solid.png", "r_shaded.png", "r_shaded_indus.png"
    };

    private static final String[] COW_TEXTURES_BLACK = new String[] {
            "", "b_shoulders.png", "b_wildtype.png", "b_wildtype_darker1.png", "b_wildtype_dark.png", "b_solid.png", "b_brindle.png"
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
                "spot_pibald0.png", "spot_pibald1.png", "spot_pibald2.png", "spot_pibald3.png", "spot_pibald4.png", "spot_pibald5.png", "spot_pibald6.png", "spot_pibald7.png", "spot_pibald8.png", "spot_pibald9.png","spot_pibalda.png", "spot_pibaldb.png", "spot_pibaldc.png", "spot_pibaldd.png", "spot_pibalde.png", "spot_pibaldf.png",
    };

    private static final String[] COW_TEXTURES_WHITEFACEHEAD = new String[] {
            "", "",
                "",
                "",
                "spot_pibald_head0.png", "spot_pibald_head1.png", "spot_pibald_head2.png", "spot_pibald_head3.png", "spot_pibald_head4.png","spot_pibald_head5.png", "spot_pibald_head6.png", "spot_pibald_head7.png", "spot_pibald_head8.png", "spot_pibald_head9.png","spot_pibald_heada.png", "spot_pibald_headb.png", "spot_pibald_headc.png", "spot_pibald_headd.png", "spot_pibald_heade.png", "spot_pibald_headf.png",
    };

    private static final String[] COW_TEXTURES_BELTED = new String[] {
            "", "spot_belt0.png", "spot_belt1.png", "spot_belt2.png", "spot_belt3.png", "spot_belt4.png", "spot_belt5.png", "spot_belt6.png", "spot_belt7.png", "spot_belt8.png", "spot_belt9.png", "spot_belta.png", "spot_beltb.png", "spot_beltc.png", "spot_beltd.png", "spot_belte.png", "spot_beltf.png",
                "spot_blaze0.png",
                "b_spot_brockling0.png",
                "r_spot_brockling0.png"
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

    private static final String[] COW_TEXTURES_HORNS = new String[] {
            "", "horns_black.png"
    };

    private static final String[] COW_TEXTURES_COAT = new String[] {
            "coat_normal.png", "coat_smooth.png", "coat_furry.png"
    };

    protected static final Ingredient TEMPTATION_ITEMS = Ingredient.fromItems(Blocks.MELON, Blocks.PUMPKIN, Blocks.GRASS, Blocks.HAY_BLOCK, Blocks.VINE, Blocks.TALL_GRASS, Blocks.OAK_LEAVES, Blocks.DARK_OAK_LEAVES, Items.CARROT, Items.WHEAT, Items.SUGAR, Items.APPLE, ModBlocks.UNBOUNDHAY_BLOCK);
    private static final Ingredient BREED_ITEMS = Ingredient.fromItems(Blocks.HAY_BLOCK, Items.WHEAT);

    protected boolean resetTexture = true;

    private static final int SEXLINKED_GENES_LENGTH = 2;

    protected boolean aiConfigured = false;

    private String mooshroomUUID = "0";

    protected String motherUUID = "";

    protected GrazingGoal grazingGoal;

//    private boolean boosting;
//    private int boostTime;
//    private int totalBoostTime;

    protected Boolean reload = false; //used in a toggle manner

    public EnhancedCow(EntityType<? extends EnhancedCow> entityType, World worldIn) {
        super(entityType, worldIn, SEXLINKED_GENES_LENGTH, Reference.COW_AUTOSOMAL_GENES_LENGTH, TEMPTATION_ITEMS, BREED_ITEMS, createFoodMap(), true);
        // cowsize from .7 to 1.5 max bag size is 1 to 1.5
        //large cows make from 30 to 12 milk points per day, small cows make up to 1/4
        this.timeUntilNextMilk = this.rand.nextInt(600) + Math.round((800 + ((1.5F - maxBagSize)*2400)) * (getAnimalSize()/1.5F)) - 300;
    }

    private static Map<Item, Integer> createFoodMap() {
        return new HashMap() {{
            put(new ItemStack(Blocks.MELON).getItem(), 10000);
            put(new ItemStack(Blocks.PUMPKIN).getItem(), 10000);
            put(new ItemStack(Items.TALL_GRASS).getItem(), 6000);
            put(new ItemStack(Items.GRASS).getItem(), 3000);
            put(new ItemStack(Items.VINE).getItem(), 3000);
            put(new ItemStack(Blocks.HAY_BLOCK).getItem(), 54000);
            put(new ItemStack(Blocks.OAK_LEAVES).getItem(), 1000);
            put(new ItemStack(Blocks.DARK_OAK_LEAVES).getItem(), 1000);
            put(new ItemStack(Items.CARROT).getItem(), 1500);
            put(new ItemStack(Items.WHEAT).getItem(), 6000);
            put(new ItemStack(Items.SUGAR).getItem(), 1500);
            put(new ItemStack(Items.APPLE).getItem(), 1500);
            put(new ItemStack(ModBlocks.UNBOUNDHAY_BLOCK).getItem(), 54000);
        }};
    }

//    @Override
//    protected void registerGoals() {
//        //Todo add the temperamants
////        this.eatGrassGoal = new EnhancedGrassGoal(this, null);
//        this.goalSelector.addGoal(0, new SwimGoal(this));
////        this.goalSelector.addGoal(5, this.eatGrassGoal);
//        this.goalSelector.addGoal(7, new EnhancedLookAtGoal(this, PlayerEntity.class, 6.0F));
//        this.goalSelector.addGoal(8, new EnhancedLookRandomlyGoal(this));
//    }

    protected void updateAITasks() {
        this.animalEatingTimer = this.grazingGoal.getEatingGrassTimer();
        super.updateAITasks();
    }

    protected void registerData() {
        super.registerData();
//        this.dataManager.register(HORN_ALTERATION, "0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0");
        this.dataManager.register(RESET_TEXTURE, false);
        this.dataManager.register(MOOSHROOM_UUID, "0");
    }

    protected String getSpecies() {
        return I18n.format("entity.eanimod.enhanced_cow");
    }

    protected int getAdultAge() { return 84000;}

    @Override
    protected int gestationConfig() {
        return EanimodCommonConfig.COMMON.gestationDaysCow.get();
    }

    protected void setMooshroomUUID(String status) {
        if (!status.equals("")) {
            this.dataManager.set(MOOSHROOM_UUID, status);
            this.mooshroomUUID = status;
        }
    }

    public String getMooshroomUUID() { return mooshroomUUID; }

//    public void setReloadTexture(Boolean resetTexture) {
//        this.dataManager.set(RESET_TEXTURE, resetTexture);
//    }

    //toggles the reloading
    protected void toggleReloadTexture() {
        this.dataManager.set(RESET_TEXTURE, this.getReloadTexture() == true ? false : true);
    }

    public boolean getReloadTexture() {
            return this.dataManager.get(RESET_TEXTURE);
    }

    @Override
    protected boolean canBePregnant() {
        return true;
    }

    @Override
    protected boolean canLactate() {
        return true;
    }

    @Override
    protected boolean ableToMoveWhileLeashed() {
        return this.grazingGoal.isSearching();
    }

    protected SoundEvent getAmbientSound() { return SoundEvents.ENTITY_COW_AMBIENT; }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) { return SoundEvents.ENTITY_COW_HURT; }

    protected SoundEvent getDeathSound() { return SoundEvents.ENTITY_COW_DEATH; }

    protected void playStepSound(BlockPos pos, BlockState blockIn) {
        this.playSound(SoundEvents.ENTITY_COW_STEP, 0.15F, 1.0F);
        if (!this.isSilent() && this.getBells()) {
            this.playSound(SoundEvents.BLOCK_NOTE_BLOCK_CHIME, 1.5F, 0.1F);
            this.playSound(SoundEvents.BLOCK_NOTE_BLOCK_BELL, 1.0F, 0.1F);
        }
    }

    /**
     * Returns the volume for the sounds this mob makes.
     */
    protected float getSoundVolume() {
        return 0.4F;
    }

    protected void registerAttributes() {
        super.registerAttributes();
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(8.0D);
        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.23000000417232513D);
    }

//    @Override
//    public float getRenderScale() {
//        int age = getAge();
//        float size = getSize();
//        if (age < 108000) {
//            float ageResult = age/108000.0F;
//            float finalCowSize = ((( 1.5F * ageResult) + 1.5F) / 3.0F) * size;
//            return finalCowSize;
//        } else {
//            return size;
//        }
//        return getSize();
//    }

    public void livingTick() {
        super.livingTick();
        if (!this.world.isRemote) {
            if (getEntityStatus().equals(EntityState.MOTHER.toString())) {
                if (hunger <= 24000) {
                    if (--this.timeUntilNextMilk <= 0) {
                        int milk = getMilkAmount();
                        if (milk < (30*(getAnimalSize()/1.5F))*(maxBagSize/1.5F)) {
                            milk++;
                            setMilkAmount(milk);
                            this.timeUntilNextMilk = this.rand.nextInt(600) + Math.round((800 + ((1.5F - maxBagSize)*1200)) * (getAnimalSize()/1.5F)) - 300;

                            //this takes the number of milk points a cow has over the number possible to make a number between 0 and 1.
                            float milkBagSize = milk / (30*(getAnimalSize()/1.5F)*(maxBagSize/1.5F));

                            this.setBagSize((1.1F*milkBagSize*(maxBagSize-1.0F))+1.0F);

                        }
                    }
                }

                if (timeUntilNextMilk <= 0) {
                    lactationTimer++;
                } else if (getMilkAmount() <= 5 && lactationTimer >= -36000) {
                    lactationTimer--;
                }

                if (lactationTimer == 0) {
                    setEntityStatus(EntityState.ADULT.toString());
                }
            }
        }
    }

    @Override
    protected boolean sleepingConditional() {
        return (((this.world.getDayTime()%24000 >= 12600 && this.world.getDayTime()%24000 <= 22000) || this.world.isThundering()) && awokenTimer == 0 && !sleeping);
    }

    protected void initialMilk() {
        lactationTimer = -48000;
        //sets milk amount at first milk
        Integer milk = Math.round((30*(getAnimalSize()/1.5F)*(maxBagSize/1.5F)) * 0.75F);
        setMilkAmount(milk);

        float milkBagSize = milk / (30*(getAnimalSize()/1.5F)*(maxBagSize/1.5F));
        this.setBagSize((1.1F*milkBagSize*(maxBagSize-1.0F))+1.0F);
    }

    @Override
    protected void incrementHunger() {
        if(sleeping) {
            hunger = hunger + (1.0F*getHungerModifier());
        } else {
            hunger = hunger + (2.0F*getHungerModifier());
        }
    }

    @Override
    protected void runExtraIdleTimeTick() {
    }


    protected void createAndSpawnEnhancedChild(World inWorld) {
        EnhancedCow enhancedcow = ENHANCED_COW.create(this.world);
        Genes babyGenes = new Genes(this.genetics).makeChild(this.getIsFemale(), this.mateGender, this.mateGenetics);
//        int[] babyGenes = getCalfGenes(this.mitosisGenes, this.mateMitosisGenes);

        defaultCreateAndSpawn(enhancedcow, inWorld, babyGenes, -84000);

        enhancedcow.setMotherUUID(this.getUniqueID().toString());
        enhancedcow.configureAI();

        this.world.addEntity(enhancedcow);
    }

    protected void setCowSize(){
        int[] genes = this.genetics.getAutosomalGenes();
        float size = 1.0F;

        //[ 1 to 15 ] max - is 0.3F
        for (int i = 1; i < genes[30]; i++){
            size = size - 0.01F;
        }
        //[ 1 to 15 ]
        for (int i = 1; i < genes[31]; i++){
            //this variation is here on purpose
            size = size - 0.012F;
        }

        //[ 1 to 15 ]  max + is 0.3F
        for (int i = 1; i < genes[32]; i++){
            size = size + 0.016F;
        }
        //[ 1 to 15 ]
        for (int i = 1; i < genes[33]; i++){
            size = size + 0.016F;
        }

        //[ 1 to 3 ] max - is 0.04F
        if (genes[34] >= 2 || genes[35] >= 2){
            if (genes[34] == 3 && genes[35] == 3){
                size = size - 0.02F;
            }else{
                size = size - 0.01F;
            }
        }
        //[ 1 to 3 ]
        if (genes[34] == 1 || genes[35] == 1){
            size = size - 0.02F;
        }else if (genes[34] == 2 || genes[35] == 2){
            size = size - 0.01F;
        }

        if (genes[26] == 1 || genes[27] == 1){
            //dwarf
            size = size/1.05F;
        }
        if (genes[28] == 2 && genes[29] == 2){
            //miniature
            size = size/1.1F;
        }

        if (size < 0.7F){
            size = 0.7F;
        }

        this.setAnimalSize(size);
    }

    public void lethalGenes(){
        int[] genes = this.genetics.getAutosomalGenes();
        if(genes[26] == 1 && genes[27] == 1) {
            this.remove();
        }
    }

    public void setMotherUUID(String motherUUID) {
        this.motherUUID = motherUUID;
    }

    public String getMotherUUID() {
        return this.motherUUID;
    }

    @Override
    protected boolean canDropLoot() { return true; }

    protected void dropSpecialItems(DamageSource source, int looting, boolean recentlyHitIn) {
        super.dropSpecialItems(source, looting, recentlyHitIn);
        int[] genes = this.genetics.getAutosomalGenes();
        this.isBurning();
        float cowSize = this.getAnimalSize();
        float age = this.getAge();
        int cowThickness = (genes[54] + genes[55]);

        int meatDrop;
        float meatChanceMod;
        int leatherDrop;

        //cowsize from .7 to 1.5
        meatChanceMod = ((cowSize - 0.6F) * 4.445F) + 1;
        meatDrop = Math.round(meatChanceMod);
        if (meatDrop >= 5) {
            meatChanceMod = 0;
        } else {
            meatChanceMod = (meatChanceMod - meatDrop) * 100;
        }

        leatherDrop = meatDrop - 2;

        if (meatChanceMod  != 0) {
            int i = this.rand.nextInt(100);
            if (meatChanceMod > i) {
                meatDrop++;
            }
            i = this.rand.nextInt(100);
            if (meatChanceMod > i) {
                leatherDrop++;
            }
        }

        if (cowThickness == 6){

            //100% chance meat
            //0% chance leather
            meatDrop++;

        } else if (cowThickness == 5){

            //75% chance meat
            int i = this.rand.nextInt(4);
            if (i>=1) {
                meatDrop++;
            }
            //25% chance leather
            i = this.rand.nextInt(4);
            if (i==0) {
                meatDrop++;
            }

        } else if (cowThickness == 4){

            //50% chance meat
            int i = this.rand.nextInt(2);
            if (i==1) {
                meatDrop++;
            }
            //50% chance leather
            i = this.rand.nextInt(2);
            if (i==1) {
                leatherDrop++;
            }

        } else if (cowThickness == 3){

            //25% chance meat
            int i = this.rand.nextInt(4);
            if (i==0) {
                meatDrop++;
            }
            //75% chance leather
            i = this.rand.nextInt(4);
            if (i>=1) {
                leatherDrop++;
            }

        } else {
            leatherDrop++;
        }

        if (age < 84000) {
            if (age > 70000) {
                leatherDrop = leatherDrop - 1;
                meatDrop = meatDrop - 1;
                meatChanceMod = (age-70000)/140;
            } else if (age > 56000) {
                leatherDrop = leatherDrop - 2;
                meatDrop = meatDrop - 2;
                meatChanceMod = (age-56000)/140;
            } else if (age > 42000) {
                leatherDrop = leatherDrop - 3;
                meatDrop = meatDrop - 3;
                meatChanceMod = (age-42000)/140;
            } else if (age > 28000) {
                leatherDrop = 0;
                meatDrop = meatDrop - 4;
                meatChanceMod = (age-28000)/140;
            } else if (age > 14000) {
                leatherDrop = 0;
                meatDrop = meatDrop - 5;
                meatChanceMod = (age-14000)/140;
            } else {
                leatherDrop = 0;
                meatDrop = meatDrop - 6;
                meatChanceMod = age/140;
            }

            int i = this.rand.nextInt(100);
            if (meatChanceMod > i) {
                meatDrop++;
            }
        }

        if (leatherDrop < 0) {
            leatherDrop = 0;
        }
        if (meatDrop < 0) {
            meatDrop = 0;
        }

        if (this.isBurning()){
            ItemStack cookedBeefStack = new ItemStack(Items.COOKED_BEEF, meatDrop);
            this.entityDropItem(cookedBeefStack);
        }else {
            ItemStack beefStack = new ItemStack(Items.BEEF, meatDrop);
            ItemStack leatherStack = new ItemStack(Items.LEATHER, leatherDrop);
            this.entityDropItem(beefStack);
            this.entityDropItem(leatherStack);
        }
    }

    @OnlyIn(Dist.CLIENT)
    public String getCowTexture() {
        if (this.enhancedAnimalTextures.isEmpty()) {
            this.setTexturePaths();
        } else if (!this.reload && getReloadTexture() || this.reload && !getReloadTexture()) {
            this.texturesIndexes.clear();
            this.enhancedAnimalTextures.clear();
            this.setTexturePaths();
            this.reload = (this.reload == true ? false : true);
        }

        return getCompiledTextures("enhanced_cow");

    }

    @OnlyIn(Dist.CLIENT)
    protected void setTexturePaths() {
        if (this.getSharedGenes() != null) {
            int[] genesForText = getSharedGenes().getAutosomalGenes();

            int base = 0;
            int red = 1;
            int black = 0;
            int roan = 0;
            int speckled = 0;
            int whiteface = 0;
            int whitefacehead = 0;
            int belted = 0;
            int coloursided = 0;
            int skin = 0;
            int hooves = 0;
            int horn = 1;
            int coat = 0;
            char[] uuidArry;

            String mooshroomUUIDForTexture = this.dataManager.get(MOOSHROOM_UUID);

            if (mooshroomUUIDForTexture.equals("0")) {
                uuidArry = getCachedUniqueIdString().toCharArray();
            } else {
                uuidArry = mooshroomUUIDForTexture.toCharArray();
            }

            //dominant red
            if (genesForText[6] == 1 || genesForText[7] == 1){
                //make red instead maybe flip dominant red toggle?
//                red = 1;
                skin = 1;
            }else {
                if (genesForText[0] == 1 || genesForText[1] == 1) {
                    black = 5;
                } else if (genesForText[0] == 2 || genesForText[1] == 2) {
//                    red = 2;

                    //Agouti
                    if (genesForText[4] == 1 || genesForText[5] == 1) {
                        if (genesForText[4] == 2 || genesForText[5] == 2) {
                            //darker wildtype
                            //or other incomplete dominance
                            black = 3;
                        } else {
                            //complete dominance of black enhancer
                            black = 4;
                        }
                    } else if (genesForText[4] == 2 || genesForText[5] == 2) {
                        //wildtype
                        black = 2;
                    } else if (genesForText[4] == 3 || genesForText[5] == 3) {
                        //white bellied fawn (i believe this is like silver)
                        black = 2;
//                        red = 0;
                        base = 2;
                        //TODO set up something here to dilute the colour of red to a cream or white
                    } else {
                        //brindle (there might be a recessive black but no one seems to know lol)
                        black = 6;
                    }

                } else {
//                    red = 1;
                    skin = 1;
                }
            }

            //mealy
            if (genesForText[24] !=1 && genesForText[25] != 1) {
                if (genesForText[24] == 2 || genesForText[25] == 2) {
                    red = 2;
                } else {
                    red = 3;
                }
            }

            //standard dilution
            if (genesForText[2] == 2 || genesForText[3] == 2){
                if (genesForText[2] == 2 && genesForText[3] == 2){
                    //full dilute
                    skin = 2;
                }else{
                    //semi dilute
                    if (black != 0 && black <= 3) {
                        black--;
                    }
                }
            } //not dilute

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
                //border spots (Pinzgauer) this genes might be incomplete dominant with wildtype but I dont see it
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
                belted = 17;
            }else if (genesForText[18] == 3 || genesForText[19] == 3){
                if (whiteface != 0 || coloursided != 0) {
                    if (black == 4 || black == 5 || black == 6 || black == 10 || black == 11 || black == 12){
                        //brockling
                        belted = 18;
                    } else {
                        belted = 19;
                    }

                }

            }

            //TODO make randomizers for the textures
            if (whiteface == 4){
                //selects body piebalding texture
                if (Character.isDigit(uuidArry[1])) {
                    whiteface = whiteface + (1 + (uuidArry[1] - 48));
                } else {
                    char d = uuidArry[1];

                    switch (d) {
                        case 'a':
                            whiteface = whiteface + 10;
                            break;
                        case 'b':
                            whiteface = whiteface + 11;
                            break;
                        case 'c':
                            whiteface = whiteface + 12;
                            break;
                        case 'd':
                            whiteface = whiteface + 13;
                            break;
                        case 'e':
                            whiteface = whiteface + 14;
                            break;
                        case 'f':
                            whiteface = whiteface + 15;
                            break;
                        default:
                            whiteface = 1;
                    }
                }
                //selects face piebalding texture
                if (uuidArry[0] != uuidArry[1]) {
                    whitefacehead = 4;
                    if (Character.isDigit(uuidArry[2])) {
                        whitefacehead = whitefacehead + (1 + uuidArry[2] - 48);
                    } else {
                        char d = uuidArry[2];

                        switch (d) {
                            case 'a':
                                whitefacehead = whitefacehead + 10;
                                break;
                            case 'b':
                                whitefacehead = whitefacehead + 11;
                                break;
                            case 'c':
                                whitefacehead = whitefacehead + 12;
                                break;
                            case 'd':
                                whitefacehead = whitefacehead + 13;
                                break;
                            case 'e':
                                whitefacehead = whitefacehead + 14;
                                break;
                            case 'f':
                                whitefacehead = whitefacehead + 15;
                                break;
                            default:
                                whitefacehead = 0;
                        }
                    }
                }
                
            }

            if (belted == 1) {
                if (Character.isDigit(uuidArry[3])) {
                    belted = belted + (1 + (uuidArry[3] - 48));
                } else {
                    char d = uuidArry[3];

                    switch (d) {
                        case 'a':
                            belted = belted + 10;
                            break;
                        case 'b':
                            belted = belted + 11;
                            break;
                        case 'c':
                            belted = belted + 12;
                            break;
                        case 'd':
                            belted = belted + 13;
                            break;
                        case 'e':
                            belted = belted + 14;
                            break;
                        case 'f':
                            belted = belted + 15;
                            break;
                        default:
                            belted = 0;
                    }
                }
            }

            //these alter texture to fit model changes
            if(genesForText[26] == 1 || genesForText[27] == 1) {
                hooves = 1;
            }

//            if (genesForText[12] == 1 || genesForText[13] == 1) {
//                //should be polled unless...
//                //african horn genes
//                if (genesForText[76] == 1 && genesForText[77] == 1) {
//                    //horned
//                } else if (genesForText[76] == 1 || genesForText[77] == 1) {
//                    //sex determined horned
//                    if (Character.isLetter(uuidArry[0]) || uuidArry[0] - 48 >= 8) {
//                        //horned if male
//                    } else {
//                        //polled if female unless
//                        if (genesForText[78] == 1 && genesForText[79] == 1) {
//                            //she is scured
//                        } else {
//                            //polled
//                            horn = 0;
//                        }
//                    }
//                } else {
//                    //polled
//                    if (genesForText[78] == 1 && genesForText[79] == 1) {
//                        //scured
//                    } else if (genesForText[78] == 1 || genesForText[79] == 1) {
//                        //sex determined scured
//                        if (Character.isLetter(uuidArry[0]) || uuidArry[0] - 48 >= 8) {
//                            //scurred
//                        } else {
//                            //polled
//                            horn = 0;
//                        }
//                    } else {
//                        //polled
//                        horn = 0;
//                    }
//                }
//            }

            if (genesForText[48] == 1 || genesForText[49] == 1){
                coat = 1;
            }else{
                if (genesForText[50] == 2 && genesForText[51] == 2) {
                    coat = 2;
                } else if (genesForText[52] == 2 && genesForText[53] == 2) {
                    coat = 2;
                }else if ((genesForText[50] == 2 || genesForText[51] == 2) && (genesForText[52] == 2 || genesForText[53] == 2)){
                    coat = 2;
                }
            }



            //TODO change white spots to add whitening together
            //TODO add shading under correct conditions

            this.enhancedAnimalTextures.add(COW_TEXTURES_BASE[0]);
            this.texturesIndexes.add(String.valueOf(0));
            this.enhancedAnimalTextures.add(COW_TEXTURES_UDDER[skin]);
            this.texturesIndexes.add(String.valueOf(skin));
            if (red != 0){
                this.enhancedAnimalTextures.add(COW_TEXTURES_RED[red]);
                this.texturesIndexes.add(String.valueOf(red));
            }
            if (black != 0){
                this.enhancedAnimalTextures.add(COW_TEXTURES_BLACK[black]);
                this.texturesIndexes.add(String.valueOf(black));
            }
            this.enhancedAnimalTextures.add(COW_TEXTURES_SKIN[skin]);
            this.texturesIndexes.add(String.valueOf(skin));
            if (whiteface != 0){
                this.enhancedAnimalTextures.add(COW_TEXTURES_WHITEFACE[whiteface]);
                this.texturesIndexes.add(String.valueOf(whiteface));
                if (whitefacehead >= 4) {
                    this.enhancedAnimalTextures.add(COW_TEXTURES_WHITEFACEHEAD[whitefacehead]);
                    this.texturesIndexes.add(String.valueOf(whitefacehead));
                }
            }
            if (coloursided != 0){
                this.enhancedAnimalTextures.add(COW_TEXTURES_COLOURSIDED[coloursided]);
                this.texturesIndexes.add(String.valueOf(coloursided));
            }
            if (belted != 0){
                this.enhancedAnimalTextures.add(COW_TEXTURES_BELTED[belted]);
                this.texturesIndexes.add(String.valueOf(belted));
            }
            if (roan != 0){
                this.enhancedAnimalTextures.add(COW_TEXTURES_ROAN[roan]);
                this.texturesIndexes.add(String.valueOf(roan));
            }
            if (speckled != 0){
                this.enhancedAnimalTextures.add(COW_TEXTURES_SPECKLED[speckled]);
                this.texturesIndexes.add(String.valueOf(speckled));
            }
            //TODO add hoof colour genetics
            this.enhancedAnimalTextures.add(COW_TEXTURES_HOOVES[hooves]);
            this.texturesIndexes.add(String.valueOf(hooves));
            //TODO add eye colour genetics
            this.enhancedAnimalTextures.add(COW_TEXTURES_EYES[0]);
            this.texturesIndexes.add(String.valueOf(0));
            //TODO add hoof colour genetics
            this.enhancedAnimalTextures.add(COW_TEXTURES_HORNS[horn]);
            this.texturesIndexes.add(String.valueOf(horn));
            this.enhancedAnimalTextures.add(COW_TEXTURES_COAT[coat]);
            this.texturesIndexes.add(String.valueOf(coat));
            this.enhancedAnimalTextures.add("d_collar.png");
            this.texturesIndexes.add(String.valueOf(0));
        }
    }

    @Override
    protected void setAlphaTexturePaths() {
    }

    @Override
    protected void initilizeAnimalSize() {
        setCowSize();
    }

    @OnlyIn(Dist.CLIENT)
    public Colouration getRgb() {
        this.colouration = super.getRgb();
        if (this.colouration.getPheomelaninColour() == -1 || this.colouration.getMelaninColour() == -1) {
            int[] genesForText = getSharedGenes().getAutosomalGenes();
            String extention = "wildtype";

            if (genesForText == null || genesForText[0] == 0) {
                return null;
            }

            float blackHue = 0.0F;
            float blackSaturation = 0.05F;
            float blackBrightness = 0.05F;

            float redHue = 0.05F;
            float redSaturation = 0.57F;
            float redBrightness = 0.55F;

            if (genesForText[0] == 1 || genesForText[1] == 1) {
                //black cow
                redHue = blackHue;
                redSaturation = blackSaturation;
                redBrightness = blackBrightness;
                extention = "black";
            } else if (genesForText[0] != 2 && genesForText[1] != 2){
                if (genesForText[0] == 3 || genesForText[1] == 3) {
                    if (genesForText[0] != 4 && genesForText[1] != 4) {
                        //red cow as in red angus, red hereford
                        blackHue = Colouration.mixColours(redHue, 0.0F, 0.5F);
                        blackSaturation = Colouration.mixColours(redSaturation, 1.0F, 0.5F);
                        blackBrightness = Colouration.mixColours(redBrightness, blackBrightness, 0.75F);
                        extention = "red";
                    } //else red and black wildtype colouration
                } else if (genesForText[0] == 4 || genesForText[1] == 4) {
                    //cow is grey as in brahman, guzerat, and probably hungarian grey
                    redHue = 0.1F;
                    redSaturation = 0.075F;
                    redBrightness = 0.9F;
//                    if (genesForText[0] == 4 && genesForText[1] == 4) {
//                        //cow is indus white
//                        blackSaturation = redSaturation;
//                        blackBrightness = mixColours(redBrightness, blackBrightness, 0.25F);
//                        //TODO homozygous indus white needs to restrict the spread of black pigment to tips.
//                    }
                    //else its "blue" possibly carrot top..
                    //TODO do something about carrot top
                } else if (genesForText[0] == 5 && genesForText[1] == 5) {
                    //red cow as in red brahman and red gyr, indistinguishable from taros red
                    blackHue = Colouration.mixColours(redHue, 0.0F, 0.5F);
                    blackSaturation = Colouration.mixColours(redSaturation, 1.0F, 0.5F);
                    blackBrightness = Colouration.mixColours(redBrightness, blackBrightness, 0.75F);
                    extention = "red";
                }
            } //else red and black wildtype colouration

            if (genesForText[120] == 2 || genesForText[121] == 2) {
                //indus dilution
                blackHue = redHue;
                blackSaturation = Colouration.mixColours(blackSaturation, redSaturation, 0.5F);
                redHue = redHue + 0.01F;
                redSaturation = Colouration.mixColours(redSaturation, 0.0F, 0.48F);
                redBrightness = Colouration.mixColours(redBrightness, 1.0F, 0.55F);
                blackBrightness = Colouration.mixColours(blackBrightness, redBrightness, 0.25F);
            }

            if (genesForText[2] == 2 && genesForText[3] == 2) {
                //typical bos taros dilution in murray grey and highland cattle

            } else if (genesForText[2] == 2 || genesForText[3] == 2) {
                //typical bos taros dilution in murray grey and highland cattle
                if (extention.equals("black")) {
                    redHue = Colouration.mixColours(redHue, 0.1F, 0.75F);
                    redSaturation = Colouration.mixColours(redSaturation, 0.0F, 0.1F);
                    redBrightness = Colouration.mixColours(redBrightness, 1.0F, 0.4F);
                    blackHue = Colouration.mixColours(blackHue, redHue, 0.5F);
                    blackSaturation = Colouration.mixColours(blackSaturation, redSaturation, 0.5F);
                    blackBrightness = Colouration.mixColours(blackBrightness, redBrightness, 0.45F);
                } else if (!extention.equals("red")) {
                    redSaturation = Colouration.mixColours(redSaturation, 0.0F, 0.1F);
                    redBrightness = Colouration.mixColours(redBrightness, 1.0F, 0.4F);
                    blackHue = redHue;
                    redHue = Colouration.mixColours(redHue, 0.1F, 0.75F);
                    blackSaturation = redSaturation;
                    blackBrightness = Colouration.mixColours(blackBrightness, redBrightness, 0.25F);
                } else {
//                    blackHue = mixColours(blackHue, redHue, 0.5F);
//                    blackSaturation = mixColours(blackSaturation, redSaturation, 0.5F);
                    redHue = Colouration.mixColours(redHue, 0.1F, 0.80F);
                    redSaturation = Colouration.mixColours(redSaturation, 0.0F, 0.1F);
                    redBrightness = Colouration.mixColours(redBrightness, 1.0F, 0.4F);
                    blackHue = Colouration.mixColours(blackHue, redHue, 0.6F);
                    blackSaturation = Colouration.mixColours(blackSaturation, redSaturation, 0.5F);
                    blackBrightness = Colouration.mixColours(blackBrightness, redBrightness, 0.4F);
                }
            }


            //puts final values into array for processing
            float[] melanin = {blackHue, blackSaturation, blackBrightness};
            float[] pheomelanin = {redHue, redSaturation, redBrightness};

            //checks that numbers are within the valid range
            for (int i = 0; i <= 2; i++) {
                if (melanin[i] > 1.0F) {
                    melanin[i] = 1.0F;
                } else if (melanin[i] < 0.0F) {
                    melanin[i] = 0.0F;
                }
                if (pheomelanin[i] > 1.0F) {
                    pheomelanin[i] = 1.0F;
                } else if (pheomelanin[i] < 0.0F) {
                    pheomelanin[i] = 0.0F;
                }
            }

            this.colouration.setMelaninColour(Colouration.HSBtoABGR(melanin[0], melanin[1], melanin[2]));
            this.colouration.setPheomelaninColour(Colouration.HSBtoABGR(pheomelanin[0], pheomelanin[1], pheomelanin[2]));

        }

        return this.colouration;
    }

    @Override
    protected void setMaxBagSize(){
        int[] genes = this.genetics.getAutosomalGenes();
        float maxBagSize = 0.0F;

        if (!this.isChild() && getEntityStatus().equals(EntityState.MOTHER.toString())){
            for (int i = 1; i < genes[62]; i++){
                maxBagSize = maxBagSize + 0.01F;
            }
            for (int i = 1; i < genes[63]; i++){
                maxBagSize = maxBagSize + 0.01F;
            }
            for (int i = 1; i < genes[64]; i++){
                maxBagSize = maxBagSize + 0.01F;
            }
            for (int i = 1; i < genes[65]; i++){
                maxBagSize = maxBagSize + 0.01F;
            }


            if (genes[38] == 6){
                maxBagSize = maxBagSize - 0.01F;
            }
            if (genes[39] == 6){
                maxBagSize = maxBagSize - 0.01F;
            }

            if (genes[40] == 1){
                maxBagSize = maxBagSize - 0.01F;
            }
            if (genes[41] == 1){
                maxBagSize = maxBagSize - 0.01F;
            }

            if (genes[50] == 2){
                maxBagSize = maxBagSize - 0.01F;
            }
            if (genes[51] == 2){
                maxBagSize = maxBagSize - 0.01F;
            }

            if (genes[52] == 2 && genes[53] == 2){
                maxBagSize = maxBagSize - 0.02F;
            }

            for (int i = 5; i > genes[66]; i--){
                maxBagSize = maxBagSize + 0.01F;
            }
            for (int i = 5; i > genes[67]; i--){
                maxBagSize = maxBagSize + 0.01F;
            }

            if (genes[54] == 3 && genes[55] == 3){
                maxBagSize = maxBagSize/2.5F;
            }else if (genes[54] == 3 || genes[55] == 3){
                if (genes[54] == 2 || genes[55] == 2){
                    maxBagSize = maxBagSize/2.0F;
                }else{
                    maxBagSize = maxBagSize/1.5F;
                }
            } else if (genes[54] == 2 || genes[55] == 2) {
                if (genes[54] == 2 && genes[55] == 2){
                    maxBagSize = maxBagSize/1.5F;
                }
            } else {
                maxBagSize = maxBagSize * 1.5F;
            }
        }

        // [ 1 to 1.5 ]
        maxBagSize = maxBagSize + 1.0F;
        if (maxBagSize > 1.5F) {
            maxBagSize = 1.5F;
        } else if (maxBagSize < 1.0F) {
            maxBagSize = 1.0F;
        }

        this.maxBagSize = maxBagSize;
    }

    @Override
    public boolean processInteract(PlayerEntity entityPlayer, Hand hand) {
        ItemStack itemStack = entityPlayer.getHeldItem(hand);
        Item item = itemStack.getItem();

        if (item == Items.NAME_TAG) {
            itemStack.interactWithEntity(entityPlayer, this, hand);
            return true;
        }
//        else if (this.getSaddled() && !this.isBeingRidden()) {
//            if (!this.world.isRemote) {
//                entityPlayer.startRiding(this);
//            }
//
//            return true;
//        } else if (item == Items.SADDLE){
//            return this.saddleCow(itemStack, entityPlayer, this);
//        }

        if ((item == Items.BUCKET || item == ModItems.ONESIXTH_MILK_BUCKET || item == ModItems.ONETHIRD_MILK_BUCKET || item == ModItems.HALF_MILK_BUCKET || item == ModItems.TWOTHIRDS_MILK_BUCKET || item == ModItems.FIVESIXTHS_MILK_BUCKET || item == ModItems.HALF_MILK_BOTTLE || item == Items.GLASS_BOTTLE) && !this.isChild() && getEntityStatus().equals(EntityState.MOTHER.toString())) {
            int maxRefill = 0;
            int bucketSize = 6;
            int currentMilk = getMilkAmount();
            int refillAmount = 0;
            boolean isBottle = false;
            if (item == Items.BUCKET) {
                maxRefill = 6;
            } else if (item == ModItems.ONESIXTH_MILK_BUCKET) {
                maxRefill = 5;
            } else if (item == ModItems.ONETHIRD_MILK_BUCKET) {
                maxRefill = 4;
            } else if (item == ModItems.HALF_MILK_BUCKET) {
                maxRefill = 3;
            } else if (item == ModItems.TWOTHIRDS_MILK_BUCKET) {
                maxRefill = 2;
            } else if (item == ModItems.FIVESIXTHS_MILK_BUCKET) {
                maxRefill = 1;
            } else if (item == ModItems.HALF_MILK_BOTTLE) {
                maxRefill = 1;
                isBottle = true;
                bucketSize = 2;
            } else if (item == Items.GLASS_BOTTLE) {
                maxRefill = 2;
                isBottle = true;
                bucketSize = 2;
            }

            if ( currentMilk >= maxRefill) {
                    refillAmount = maxRefill;
            } else if (currentMilk < maxRefill) {
                   refillAmount = currentMilk;
            }

            if (!this.world.isRemote) {
                int resultingMilkAmount = currentMilk - refillAmount;
                this.setMilkAmount(resultingMilkAmount);

                float milkBagSize = resultingMilkAmount / (30*(getAnimalSize()/1.5F)*(maxBagSize/1.5F));

                this.setBagSize((1.1F*milkBagSize*(maxBagSize-1.0F))+1.0F);
            }

            int resultAmount = bucketSize - maxRefill + refillAmount;

            ItemStack resultItem = new ItemStack(Items.BUCKET);

            switch (resultAmount) {
                case 0:
                    entityPlayer.playSound(SoundEvents.ENTITY_COW_HURT, 1.0F, 1.0F);
                    return true;
                case 1:
                    if (isBottle) {
                        resultItem = new ItemStack(ModItems.HALF_MILK_BOTTLE);
                    } else {
                        resultItem = new ItemStack(ModItems.ONESIXTH_MILK_BUCKET);
                    }
                    break;
                case 2:
                    if (isBottle) {
                        resultItem = new ItemStack(ModItems.MILK_BOTTLE);
                    } else {
                        resultItem = new ItemStack(ModItems.ONETHIRD_MILK_BUCKET);
                    }
                    break;
                case 3:
                    resultItem = new ItemStack(ModItems.HALF_MILK_BUCKET);
                    break;
                case 4:
                    resultItem = new ItemStack(ModItems.TWOTHIRDS_MILK_BUCKET);
                    break;
                case 5:
                    resultItem = new ItemStack(ModItems.FIVESIXTHS_MILK_BUCKET);
                    break;
                case 6:
                    resultItem = new ItemStack(Items.MILK_BUCKET);
                    break;
            }

            entityPlayer.playSound(SoundEvents.ENTITY_COW_MILK, 1.0F, 1.0F);
            itemStack.shrink(1);
            if (itemStack.isEmpty()) {
                entityPlayer.setHeldItem(hand, resultItem);
            } else if (!entityPlayer.inventory.addItemStackToInventory(resultItem)) {
                entityPlayer.dropItem(resultItem, false);
            }

            return true;
        }

        return super.processInteract(entityPlayer, hand);
    }

    protected void dropInventory() {
        super.dropInventory();
    }

    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);

        compound.putString("MooshroomID", getMooshroomUUID());

        compound.putString("MotherUUID", this.motherUUID);

//        compound.putBoolean("Saddle", this.getSaddled());

    }

    /**
     * (abstract) Protected helper method to read subclass entity assets from NBT.
     */
    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);


        setMooshroomUUID(compound.getString("MooshroomID"));

        this.motherUUID = compound.getString("MotherUUID");

//        this.setSaddled(compound.getBoolean("Saddle"));

        configureAI();
    }

    @Nullable
    @Override
    public ILivingEntityData onInitialSpawn(IWorld inWorld, DifficultyInstance difficulty, SpawnReason spawnReason, @Nullable ILivingEntityData livingdata, @Nullable CompoundNBT itemNbt) {
        return commonInitialSpawnSetup(inWorld, livingdata, getAdultAge(), 64800, 108000);
    }

    @Override
    protected void setInitialDefaults() {
        super.setInitialDefaults();
        configureAI();
    }

    @Override
    protected Genes createInitialGenes(IWorld world, BlockPos pos, boolean isDomestic) {
        return new CowGeneticsInitialiser().generateNewGenetics(world, pos, isDomestic);
    }

    @Override
    protected Genes createInitialBreedGenes(IWorld world, BlockPos pos, String breed) {
        return new CowGeneticsInitialiser().generateWithBreed(world, pos, breed);
    }

    protected void configureAI() {
        if (!aiConfigured) {
            Double speed = 1.0D;

//            if (this.cowSize > 1.2F) {
//                speed++;
//                speed = speed + 0.1;
//            }
//
//            if (this.cowSize < 0.8F) {
//                speed = speed - 0.1;
//            }
//
//            int bodyShape = 0;
//
//            for (int i = 1; i < genes[54]; i++){
//                bodyShape++;
//            }
//            for (int i = 1; i < genes[55]; i++){
//                bodyShape++;
//            }
//
//            if (genes[26] == 1 || genes[27] == 1) {
//                speed = speed *0.9;
//            }
//
//            if (bodyShape == 4) {
//                speed = speed - 0.1;
//            }

            this.goalSelector.addGoal(1, new EnhancedPanicGoal(this, speed*1.5D));
            this.goalSelector.addGoal(2, new BreedGoal(this, speed));
            this.goalSelector.addGoal(3, new EnhancedTemptGoal(this, speed*1.25D, false, Ingredient.fromItems(Items.CARROT_ON_A_STICK)));
            this.goalSelector.addGoal(3, new EnhancedTemptGoal(this, speed*1.25D, false, TEMPTATION_ITEMS));
            this.goalSelector.addGoal(4, new FollowParentGoal(this, speed*1.25D));
            this.goalSelector.addGoal(4, new EnhancedAINurseFromMotherGoal(this, motherUUID, speed*1.25D));
//            grazingGoal = new EnhancedWaterAvoidingRandomWalkingEatingGoal(this, speed, 7, 0.001F, 120, 2, 20);
            grazingGoal = new GrazingGoal(this, speed);
            this.goalSelector.addGoal(6, grazingGoal);
            this.goalSelector.addGoal(7, new EnhancedWanderingGoal(this, speed));
        }
        aiConfigured = true;
    }

}
