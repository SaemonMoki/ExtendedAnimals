package mokiyoki.enhancedanimals.entity;

import mokiyoki.enhancedanimals.ai.EnhancedEatPlantsGoal;
import mokiyoki.enhancedanimals.ai.general.EnhancedBreedGoal;
import mokiyoki.enhancedanimals.ai.general.EnhancedFollowParentGoal;
import mokiyoki.enhancedanimals.ai.general.EnhancedPanicGoal;
import mokiyoki.enhancedanimals.ai.general.EnhancedTemptGoal;
import mokiyoki.enhancedanimals.ai.general.EnhancedWanderingGoal;
import mokiyoki.enhancedanimals.ai.general.GrazingGoal;
import mokiyoki.enhancedanimals.ai.general.SeekShelterGoal;
import mokiyoki.enhancedanimals.ai.general.StayShelteredGoal;
import mokiyoki.enhancedanimals.ai.general.cow.EnhancedAINurseFromMotherGoal;
import mokiyoki.enhancedanimals.entity.Genetics.CowGeneticsInitialiser;
import mokiyoki.enhancedanimals.entity.util.Colouration;
import mokiyoki.enhancedanimals.init.ModBlocks;
import mokiyoki.enhancedanimals.init.ModItems;
import mokiyoki.enhancedanimals.config.EanimodCommonConfig;
import mokiyoki.enhancedanimals.items.CustomizableSaddleEnglish;
import mokiyoki.enhancedanimals.items.CustomizableSaddleWestern;
import mokiyoki.enhancedanimals.util.Genes;
import mokiyoki.enhancedanimals.util.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.Pose;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
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
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

import static mokiyoki.enhancedanimals.util.handlers.EventRegistry.ENHANCED_COW;

public class EnhancedCow extends EnhancedAnimalRideableAbstract {

    //avalible UUID spaces : [ S X X X X X 6 7 - 8 9 10 11 - 12 13 14 15 - 16 17 18 19 - 20 21 22 23 24 25 26 27 28 29 30 31 ]

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
            "", "b_shoulders.png", "b_agoutiwildtype.png", "b_wildtype_darker1.png", "b_wildtype_dark.png", "b_solid.png", "b_brindle0.png", "b_whitebelly.png", "b_fawn.png", "b_gloucester1.png", "b_mask.png"
    };

    private static final String[] COW_TEXTURES_EELSTRIPE = new String[] {
            "", "r_eelstripe.png"
    };

    private static final String[] COW_TEXTURES_MEALY = new String[] {
            "", "mealy0.png", "mealy1.png", "mealy2.png"
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

    private static final String[] COW_TEXTURES_BROCKLING = new String[] {
            "", "b_spot_brockling0.png", "r_spot_brockling0.png"
    };

    private static final String[] COW_TEXTURES_BELTED = new String[] {
            "", "spot_belt0.png", "spot_belt1.png", "spot_belt2.png", "spot_belt3.png", "spot_belt4.png", "spot_belt5.png", "spot_belt6.png", "spot_belt7.png", "spot_belt8.png", "spot_belt9.png", "spot_belta.png", "spot_beltb.png", "spot_beltc.png", "spot_beltd.png", "spot_belte.png", "spot_beltf.png"
    };

    private static final String[] COW_TEXTURES_BLAZE = new String[] {
            "", "spot_doubleblaze0.png", "spot_doubleblaze1.png", "spot_doubleblaze2.png", "spot_doubleblaze0.png", "spot_doubleblaze1.png", "spot_doubleblaze2.png", "spot_doubleblaze0.png", "spot_doubleblaze1.png", "spot_doubleblaze2.png", "spot_doubleblaze0.png", "spot_doubleblaze1.png", "spot_doubleblaze2.png", "spot_doubleblaze0.png", "spot_doubleblaze1.png", "spot_doubleblaze2.png", "spot_doubleblaze2.png",
                "spot_blaze0.png", "spot_blaze1.png", "spot_blaze2.png", "spot_blaze3.png", "spot_blaze4.png", "spot_blaze5.png", "spot_blaze6.png", "spot_blaze0.png", "spot_blaze1.png", "spot_blaze2.png", "spot_blaze3.png", "spot_blaze4.png", "spot_blaze5.png", "spot_blaze6.png", "spot_blaze5.png", "spot_blaze6.png"
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

    protected static final Ingredient TEMPTATION_ITEMS = Ingredient.fromItems(Blocks.MELON, Blocks.PUMPKIN, Blocks.GRASS, Blocks.HAY_BLOCK, Blocks.VINE, Blocks.TALL_GRASS, Blocks.OAK_LEAVES, Blocks.DARK_OAK_LEAVES, Items.CARROT, Items.WHEAT, Items.SUGAR, Items.APPLE, ModBlocks.UNBOUNDHAY_BLOCK, Items.MELON_SLICE);
    private static final Ingredient BREED_ITEMS = Ingredient.fromItems(Blocks.HAY_BLOCK, Items.WHEAT);

    protected boolean resetTexture = true;
    protected String cacheTexture;

    private static final int SEXLINKED_GENES_LENGTH = 2;

    protected boolean aiConfigured = false;

    private String mooshroomUUID = "0";

    protected String motherUUID = "";

    protected GrazingGoal grazingGoal;

//    private boolean boosting;
//    private int boostTime;
//    private int totalBoostTime;

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
            put(new ItemStack(Items.SUGAR).getItem(), 500);
            put(new ItemStack(Items.APPLE).getItem(), 1500);
            put(new ItemStack(Items.MELON_SLICE).getItem(), 1100);
            put(new ItemStack(ModBlocks.UNBOUNDHAY_BLOCK).getItem(), 54000);
        }};
    }

    private Map<Block, EnhancedEatPlantsGoal.EatValues> createGrazingMap() {
        Map<Block, EnhancedEatPlantsGoal.EatValues> ediblePlants = new HashMap<>();
        ediblePlants.put(Blocks.WHEAT, new EnhancedEatPlantsGoal.EatValues(3, 7, 750));
        ediblePlants.put(Blocks.AZURE_BLUET, new EnhancedEatPlantsGoal.EatValues(3, 7, 750));
        ediblePlants.put(ModBlocks.GROWABLE_AZURE_BLUET, new EnhancedEatPlantsGoal.EatValues(7, 2, 750));
        ediblePlants.put(Blocks.BLUE_ORCHID, new EnhancedEatPlantsGoal.EatValues(3, 7, 375));
        ediblePlants.put(ModBlocks.GROWABLE_BLUE_ORCHID, new EnhancedEatPlantsGoal.EatValues(3, 7, 375));
        ediblePlants.put(Blocks.CORNFLOWER, new EnhancedEatPlantsGoal.EatValues(3, 7, 375));
        ediblePlants.put(ModBlocks.GROWABLE_CORNFLOWER, new EnhancedEatPlantsGoal.EatValues(7, 7, 375));
        ediblePlants.put(Blocks.DANDELION, new EnhancedEatPlantsGoal.EatValues(3, 7, 750));
        ediblePlants.put(ModBlocks.GROWABLE_DANDELION, new EnhancedEatPlantsGoal.EatValues(3, 7, 750));
        ediblePlants.put(Blocks.SUNFLOWER, new EnhancedEatPlantsGoal.EatValues(3, 7, 375));
        ediblePlants.put(ModBlocks.GROWABLE_SUNFLOWER, new EnhancedEatPlantsGoal.EatValues(3, 7, 375));
        ediblePlants.put(Blocks.GRASS, new EnhancedEatPlantsGoal.EatValues(3, 7, 750));
        ediblePlants.put(ModBlocks.GROWABLE_GRASS, new EnhancedEatPlantsGoal.EatValues(3, 7, 750));
        ediblePlants.put(Blocks.TALL_GRASS, new EnhancedEatPlantsGoal.EatValues(3, 7, 750));
        ediblePlants.put(ModBlocks.GROWABLE_TALL_GRASS, new EnhancedEatPlantsGoal.EatValues(3, 7, 750));
        ediblePlants.put(Blocks.FERN, new EnhancedEatPlantsGoal.EatValues(3, 7, 750));
        ediblePlants.put(ModBlocks.GROWABLE_FERN, new EnhancedEatPlantsGoal.EatValues(3, 7, 750));
        ediblePlants.put(Blocks.LARGE_FERN, new EnhancedEatPlantsGoal.EatValues(3, 7, 750));
        ediblePlants.put(ModBlocks.GROWABLE_LARGE_FERN, new EnhancedEatPlantsGoal.EatValues(3, 7, 750));
        ediblePlants.put(Blocks.PUMPKIN, new EnhancedEatPlantsGoal.EatValues(1, 1, 10000));
        ediblePlants.put(Blocks.MELON, new EnhancedEatPlantsGoal.EatValues(1, 1, 10000));

        return ediblePlants;
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
        return "entity.eanimod.enhanced_cow";
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

    public String getMooshroomUUID() { return this.mooshroomUUID; }

    @Override
    protected boolean canBePregnant() {
        return true;
    }

    @Override
    protected boolean canLactate() {
        return true;
    }

    protected SoundEvent getAmbientSound() {
        if (isAnimalSleeping()) {
            return null;
        }
        return SoundEvents.ENTITY_COW_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) { return SoundEvents.ENTITY_COW_HURT; }

    protected SoundEvent getDeathSound() { return SoundEvents.ENTITY_COW_DEATH; }

    protected void playStepSound(BlockPos pos, BlockState blockIn) {
        super.playStepSound(pos, blockIn);
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

    @Override
    public double getMountedYOffset() {
        ItemStack saddleSlot = this.getEnhancedInventory().getStackInSlot(1);
        double yPos;
        if (this.isFemale()) {
            //female height
                yPos = 1.0D;
        } else {
            //male height
                yPos = 1.1D;
        }

        float size = this.getAnimalSize();

        if (this.dwarf == -1.0F) {
            Genes sharedGenetics = new Genes(this.dataManager.get(SHARED_GENES));
            this.dwarf = (sharedGenetics.getAutosomalGene(26) == 1 || sharedGenetics.getAutosomalGene(27) == 1) ? 0.2F : 0.0F;
        }

        if (this.dwarf>0) {
            yPos = yPos - this.dwarf;
        }

        if (saddleSlot.getItem() instanceof CustomizableSaddleWestern) {
            yPos = yPos + 0.12D;
        } else if (saddleSlot.getItem() instanceof CustomizableSaddleEnglish) {
            yPos = yPos + 0.06D;
        }

        int age = this.getAge() < 108000 ? this.getAge() : 108000;
        size = (( 2.0F * size * ((float) age/108000.0F)) + size) / 3.0F;

        return yPos*(Math.pow(size, 1.2F));
    }

    @Override
    protected float getJumpHeight() {
        if (this.dwarf > 0 || this.getEnhancedInventory().getStackInSlot(0).getItem() == Items.CHEST) {
            return 0.45F;
        } else {
            float jump = 0.48F;
            float size = this.getAnimalSize();
            if (size < 0.9F) {
                return jump + (((size - 0.9F) / 0.2F) * 0.1F);
            }
            return jump;
        }
    }

    @Override
    protected float getJumpFactorModifier() {
        return 0.25F;
    }

    @Override
    protected float getMovementFactorModifier() {
        float speedMod = 1.0F;
        float size = this.getAnimalSize();
        if (size > 1.05F) {
            speedMod = 1.05F/size;
        } else if (size < 1.0F) {
            speedMod = size/1.0F;
        }

        if (this.dwarf > 0) {
            speedMod = speedMod * 0.25F;
        }

        float chestMod = 0.0F;
        ItemStack chestSlot = this.getEnhancedInventory().getStackInSlot(0);
        if (chestSlot.getItem() == Items.CHEST) {
            chestMod = (1.0F-((size-0.7F)*1.25F)) * 0.4F;
        }

        return 0.4F + (speedMod * 0.4F) - chestMod;
    }

    public static AttributeModifierMap.MutableAttribute prepareAttributes() {
        return MobEntity.func_233666_p_()
                .createMutableAttribute(Attributes.MAX_HEALTH, 8.0D)
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.23D)
                .createMutableAttribute(JUMP_STRENGTH);
    }

    @Override
    public EntitySize getSize(Pose poseIn) {
        return EntitySize.flexible(1.0F, 1.25F);
    }

    @Override
    public boolean isFemale() {
        char[] uuidArray = (this.mooshroomUUID.equals("0") ? getCachedUniqueIdString() : this.mooshroomUUID).toCharArray();
        return !Character.isLetter(uuidArray[0]) && uuidArray[0] - 48 < 8;
    }

    public void livingTick() {
        super.livingTick();
        if (!this.world.isRemote) {
            if (getEntityStatus().equals(EntityState.MOTHER.toString())) {
                if (hunger <= 24000) {
                    if (--this.timeUntilNextMilk <= 0) {
                        int milk = getMilkAmount();
                        if (milk < (30*(getAnimalSize()/1.5F))*(this.maxBagSize/1.5F)) {
                            milk++;
                            setMilkAmount(milk);
                            this.timeUntilNextMilk = this.rand.nextInt(600) + Math.round((800 + ((1.5F - this.maxBagSize)*1200)) * (getAnimalSize()/1.5F)) - 300;

                            //this takes the number of milk points a cow has over the number possible to make a number between 0 and 1.
                            float milkBagSize = milk / (30*(getAnimalSize()/1.5F)*(this.maxBagSize/1.5F));

                            this.setBagSize((1.1F*milkBagSize*(this.maxBagSize-1.0F))+1.0F);

                        }
                    }
                }

                if (this.timeUntilNextMilk <= 0) {
                    this.lactationTimer++;
                } else if (getMilkAmount() <= 5 && this.lactationTimer >= -36000) {
                    this.lactationTimer--;
                }

                if (lactationTimer == 0) {
                    setEntityStatus(EntityState.ADULT.toString());
                }
            }
        }
    }

    @Override
    public boolean sleepingConditional() {
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
        Genes babyGenes = new Genes(this.genetics).makeChild(this.isFemale(), this.mateGender, this.mateGenetics);
        defaultCreateAndSpawn(enhancedcow, inWorld, babyGenes, -84000);
        enhancedcow.setMotherUUID(this.getUniqueID().toString());
        enhancedcow.configureAI();

        this.world.addEntity(enhancedcow);
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

        leatherDrop = leatherDrop < 0 ? 0 : leatherDrop;

        meatDrop = meatDrop < 0 ? 0 : meatDrop;

        leatherDrop = (leatherDrop > 5) ? 5 : leatherDrop;

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
    public String updateCashe(String current) {
        if (!current.equals(this.cacheTexture)) {
            String old = this.cacheTexture;
            this.cacheTexture = current;
            return old;
        }
        return "";
    }

    @OnlyIn(Dist.CLIENT)
    public String getCowTexture() {
        if (this.enhancedAnimalTextures.isEmpty()) {
            this.setTexturePaths();
        } else if (this.resetTexture) {
            this.resetTexture = false;
            this.texturesIndexes.clear();
            this.enhancedAnimalTextures.clear();
            this.setTexturePaths();
            this.colouration.setMelaninColour(-1);
            this.colouration.setPheomelaninColour(-1);
        }

        return getCompiledTextures("enhanced_cow");

    }

    @OnlyIn(Dist.CLIENT)
    protected void setTexturePaths() {
        if (this.getSharedGenes() != null) {
            int[] gene = getSharedGenes().getAutosomalGenes();

            int base = 0;
            int red = 1;
            int black = 0;
            int roan = 0;
            int speckled = 0;
            int whiteface = 0;
            int whitefacehead = 0;
            int brockling = 0;
            int belted = 0;
            int blaze = 0;
            int coloursided = 0;
            int skin = 0;
            int hooves = 0;
            int horn = 1;
            int coat = 0;
            int eelstripe = 0;
            int mealy = 0;
            char[] uuidArry;

            String mooshroomUUIDForTexture = this.dataManager.get(MOOSHROOM_UUID);

            if (mooshroomUUIDForTexture.equals("0")) {
                uuidArry = getCachedUniqueIdString().toCharArray();
            } else {
                uuidArry = mooshroomUUIDForTexture.toCharArray();
            }

            //dominant red
            if (gene[6] == 1 || gene[7] == 1){
                //make red instead maybe flip dominant red toggle?
//                red = 1;
                skin = 1;
            }else {
                if (gene[0] == 1 || gene[1] == 1) {
                    //dominant black
                    black = 5;
                } else if (gene[0] == 4 || gene[1] == 4) {
                    if ((gene[4] == 3 || gene[4] == 5) && (gene[5] == 3 || gene[5] == 5)) {
                        black = 9;
                    } else {
                        black = 5;
                    }
                } else if (gene[0] == 3 && gene[1] == 3) {
                    // red
                    black = 0;
                } else {
                    //Agouti
                    if (gene[4] == 4 || gene[5] == 4) {
                        //brindle
                        black = 6;
                    } else if (gene[4] == 1 || gene[5] == 1) {
                        if (gene[4] == 2 || gene[5] == 2) {
                            //darker wildtype
                            //or other incomplete dominance
                            black = 4;
                        } else {
                            //complete dominance of black enhancer
                            black = 4;
                        }
                    } else if (gene[4] == 2 || gene[5] == 2) {
                        //wildtype
                        if (this.isFemale()) {
                            black = 2;
                        } else {
                            black = 4;
                        }
                    } else if (gene[4] == 3 || gene[5] == 3) {
                        //white bellied fawn more blured markings?
                        if (gene[0] == 5 || gene[1] == 5) {
                            black = 10;
                        } else {
                            black = 7;
                        }
                        red = 2;
                        //TODO set up something here to dilute the colour of red to a cream or white
                    } else if (gene[4] == 5 || gene[5] == 5){
                        //fawn
                        red = 3;
                        if (gene[0] == 5 || gene[1] == 5) {
                            black = 10;
                        } else {
                            black = 8;
                        }
                    } else {
                        //recessive black
                        black = 5;
                    }
                }
            }

            //mealy
            if (gene[0] != 1 && gene[1] != 1) {
                if (gene[24] != 1 && gene[25] != 1) {
                    if (!this.isFemale() || gene[24] == 2 || gene[25] == 2) {
                        mealy = 2;
                        if (red < 3) {
                            red++;
                        }
                    } else {
                        mealy = 1;
                        if (red < 3) {
                            red++;
                        }
                    }
                    if (gene[120] == 1 && gene[121] == 1) {
                        eelstripe = 1;
                    }
                }
            }

            //standard dilution
            if (gene[2] == 2 || gene[3] == 2){
                if (gene[2] == 2 && gene[3] == 2){
                    //full dilute
                    skin = 2;
                }
            } //not dilute

            //roan
            if (gene[8] == 2 || gene[9] == 2){
                //is roan
                if (gene[8] == 2 && gene[9] == 2) {
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
            if (gene[14] == 1 || gene[15] == 1){
                if (gene[14] == 1 && gene[15] == 1){
                    speckled = 2;
                    //pointed white
                }else{
                    speckled = 1;
                    //speckled
                }
            } //not speckled

            //colour sided
            if (gene[20] == 1 || gene[21] == 1){
                //coloursided
                coloursided = 1;
            }

            if (gene[16] == 1 || gene[17] == 1){
                if (gene[16] == 2 || gene[17] == 2){
                    //white face with border spots(Pinzgauer)
                    whiteface = 2;
                }else{
                    //whiteface
                    whiteface = 1;
                }
            }else if (gene[16] == 2 || gene[17] == 2){
                //border spots (Pinzgauer) this genes might be incomplete dominant with wildtype but I dont see it
                    whiteface = 3;
            }else if (gene[16] == 4 && gene[17] == 4){
                //piebald
                    whiteface = 4;

            }

            // Legacy belt/blaze
            // Brockling gene
            if (gene[18] == 1 || gene[19] == 1){
                //belted
                brockling = -1;
            }else if (gene[18] == 2 || gene[19] == 2){
                //blaze
                brockling = -2;
            }

            //Belted
            if (brockling == -1) {
                belted = -1;
                brockling = 0;
            } else if (gene[250] == 2 || gene[251] == 2) {
                belted = 1;
            }

            //Blaze
            if (brockling == -2) {
                blaze = -1;
                brockling = 0;
            } else if (gene[252] == 2 || gene[253] == 2) {
                if (gene[252] == 2 && gene[253] == 2) {
                    blaze = 1;
                } else {
                    blaze = 2;
                }
            }

            if (gene[18] == 3 || gene[19] == 3){
                if (whiteface != 0 || coloursided != 0 || blaze != 0) {
                    if (black == 4 || black == 5 || black == 6 || black == 10 || black == 11 || black == 12) {
                        //brockling
                        brockling = 1;
                    } else {
                        brockling = 2;
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

            //belt picker belt==-1 is legacy belt==1 is updated
            if (belted != 0) {
                belted = 1;
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

            //blaze variations
            if (blaze != 0) {
                if (blaze != 1) {
                    blaze = 17;
                }
                if (Character.isDigit(uuidArry[3])) {
                    blaze = blaze + (1 + (uuidArry[3] - 48));
                } else {
                    char d = uuidArry[3];

                    switch (d) {
                        case 'a':
                            blaze = blaze + 0;
                            break;
                        case 'b':
                            blaze = blaze + 1;
                            break;
                        case 'c':
                            blaze = blaze + 2;
                            break;
                        case 'd':
                            blaze = blaze + 3;
                            break;
                        case 'e':
                            blaze = blaze + 4;
                            break;
                        case 'f':
                            blaze = blaze + 5;
                            break;
                        default:
                            belted = 0;
                    }
                }
            }

            //these alter texture to fit model changes
            if(gene[26] == 1 || gene[27] == 1) {
                hooves = 1;
            }

            if (gene[48] == 1 || gene[49] == 1){
                coat = 1;
            }else{
                if (gene[50] == 2 && gene[51] == 2) {
                    coat = 2;
                } else if (gene[52] == 2 && gene[53] == 2) {
                    coat = 2;
                }else if ((gene[50] == 2 || gene[51] == 2) && (gene[52] == 2 || gene[53] == 2)){
                    coat = 2;
                }
            }

            //TODO change white spots to add whitening together
            //TODO add shading under correct conditions

            addTextureToAnimal(COW_TEXTURES_BASE, 0, null);
            addTextureToAnimal(COW_TEXTURES_UDDER, skin, null);
            addTextureToAnimal(COW_TEXTURES_RED, red, r -> r != 0);
            addTextureToAnimal(COW_TEXTURES_BLACK, black, b -> b != 0);
            addTextureToAnimal(COW_TEXTURES_MEALY, mealy, m -> m != 0);
            addTextureToAnimal(COW_TEXTURES_EELSTRIPE, eelstripe, e -> e != 0);
            addTextureToAnimal(COW_TEXTURES_SKIN, skin, null);
            addTextureToAnimal(COW_TEXTURES_WHITEFACE, whiteface, w -> w != 0);
            addTextureToAnimal(COW_TEXTURES_WHITEFACEHEAD, whitefacehead, w -> w >= 4);
            addTextureToAnimal(COW_TEXTURES_COLOURSIDED, coloursided, c -> c != 0);
            addTextureToAnimal(COW_TEXTURES_BROCKLING, brockling, b -> b != 0);
            addTextureToAnimal(COW_TEXTURES_BELTED, belted, b -> b != 0);
            addTextureToAnimal(COW_TEXTURES_BLAZE, blaze, b -> b != 0);
            addTextureToAnimal(COW_TEXTURES_ROAN, roan, r -> r != 0);
            addTextureToAnimal(COW_TEXTURES_SPECKLED, speckled, r -> r != 0);
            addTextureToAnimal(COW_TEXTURES_HOOVES, hooves, null);
            addTextureToAnimal(COW_TEXTURES_EYES, 0, null);
            addTextureToAnimal(COW_TEXTURES_HORNS, horn, null);
            addTextureToAnimal(COW_TEXTURES_COAT, coat, null);
        }
    }

    @Override
    protected void setAlphaTexturePaths() {
    }

    @OnlyIn(Dist.CLIENT)
    public Colouration getRgb() {
        this.colouration = super.getRgb();
        Genes genes = getSharedGenes();
        if (genes != null) {
            if (this.colouration.getPheomelaninColour() == -1 || this.colouration.getMelaninColour() == -1) {
                int[] gene = genes.getAutosomalGenes();

                float blackHue = 0.0F;
                float blackSaturation = 0.05F;
                float blackBrightness = 0.05F;

                float redHue = 0.05F;
                float redSaturation = 0.57F;
                float redBrightness = 0.55F;

                if (gene[0] == 3 && gene[1] == 3) {
                    //cow is red
                    blackHue = redHue;
                    blackSaturation = redSaturation;
                    blackBrightness = (redBrightness + blackBrightness) * 0.5F;
                }

                for (int i = 130; i < 150; i++) {
                    if (gene[i] == 2) {
                        redHue = redHue + ((0.1F - redHue) * 0.05F);
                        redBrightness = redBrightness + 0.01F;
                    }
                }

                for (int i = 150; i < 170; i++) {
                    if (gene[i] == 2) {
                        redHue = redHue - 0.00225F;
                        if (redHue <= 0.0F) {
                            redHue = 1.0F + redHue;
                        }
                        redSaturation = redSaturation + 0.00175F;
                        redBrightness = redBrightness - 0.0155F;

                        blackHue = blackHue - 0.00225F;
                        if (blackHue <= 0.0F) {
                            blackHue = 1.0F + blackHue;
                        }
                        blackHue = blackHue + 0.002F;
                    }
                }

                float shadeIntensity = 0.5F;
                for (int i = 170; i < 185; i++) {
                    if (gene[i] == 2) {
                        redBrightness = redBrightness + 0.005F;
                        shadeIntensity = shadeIntensity - 0.005F;
                    }
                }

                for (int i = 185; i < 200; i++) {
                    if (gene[i] == 2) {
                        redBrightness = redBrightness - 0.005F;
                        shadeIntensity = shadeIntensity + 0.005F;
                    }
                }

                for (int i = 200; i < 225; i++) {
                    if (gene[i] == 2) {
                        shadeIntensity = shadeIntensity - 0.015F;
                    }
                }

                for (int i = 225; i < 250; i++) {
                    if (gene[i] == 2) {
                        shadeIntensity = shadeIntensity + 0.02F;
                    }
                }

                /**
                 *  Dun - Mostly effects red pigment. Looks like what I would call silver. Responsible for grey brahmans, guzerat, and hungarian grey.
                 */
                if (gene[128] == 2 || gene[129] == 2) {
                    if (gene[128] == 2 && gene[129] == 2) {
                        //homo dun
                        redSaturation = 0.0F;
                        redBrightness = Colouration.mixColourComponent(redBrightness, 1.0F, 0.9F);
                        blackSaturation = Colouration.mixColourComponent(blackBrightness, 1.0F, 0.2F);
                        blackBrightness = Colouration.mixColourComponent(blackBrightness, 1.0F, 0.05F);
                    } else {
                        //het dun
                        redHue = Colouration.mixHueComponent(redHue, 0.1F, 0.1F);
                        redSaturation = Colouration.mixColourComponent(redSaturation, 0.0F, 0.45F);
                        redBrightness = Colouration.mixColourComponent(redBrightness, 1.0F, 0.35F);
                    }
                }

                /**
                 *  Dexter Dun - chocolate
                 */
                if (gene[10] == 2 && gene[11] == 2) {
                    blackHue = Colouration.mixHueComponent(blackHue, 0.1F, 0.3F);
                    blackSaturation = blackSaturation + ((1.0F - blackSaturation) * 0.45F);
                    blackBrightness = blackBrightness + ((1.0F - blackBrightness) * 0.25F);
                }

                /**
                 *  Dilution. What you usually see in highlands, murrey grey, simmental and other such dun/yellow/grey cattle
                 */
                if (gene[2] == 2 || gene[3] == 2) {
                    // simmental or highland dilution
                    if (gene[2] == 2 && gene[3] == 2) {
                        //homo highland dun
                        redHue = Colouration.mixHueComponent(redHue, 0.1F, 0.85F);
                        redSaturation = Colouration.mixColourComponent(redSaturation, 0.0F, 0.9F);
                        redBrightness = Colouration.mixColourComponent(redBrightness, 1.0F, 0.6F);
                        blackHue = Colouration.mixHueComponent(redHue, 0.1F, 0.9F);
                        blackSaturation = Colouration.mixColourComponent(redSaturation, 0.0F, 0.9F);
                        blackBrightness = Colouration.mixColourComponent(redBrightness * blackBrightness, 1.0F, 0.6F);
                    } else if (gene[2] == 1 || gene[3] == 1) {
                        //het dun
                        if (gene[0] == 1 || gene[1] == 1) {
                            redHue = Colouration.mixHueComponent(redHue, 0.1F, 0.75F);
                            redSaturation = Colouration.mixColourComponent(redSaturation, 0.0F, 0.1F);
                            redBrightness = Colouration.mixColourComponent(redBrightness, 1.0F, 0.4F);
                            blackHue = Colouration.mixHueComponent(blackHue, redHue, 0.5F);
                            blackSaturation = Colouration.mixColourComponent(blackSaturation, redSaturation, 0.5F);
                            blackBrightness = Colouration.mixColourComponent(blackBrightness, redBrightness, 0.45F);
                        } else if (gene[0] == 3 || gene[1] == 3) {
                            redSaturation = Colouration.mixColourComponent(redSaturation, 0.0F, 0.1F);
                            redBrightness = Colouration.mixColourComponent(redBrightness, 1.0F, 0.4F);
                            blackHue = redHue;
                            redHue = Colouration.mixHueComponent(redHue, 0.1F, 0.75F);
                            blackSaturation = redSaturation;
                            blackBrightness = Colouration.mixColourComponent(blackBrightness, redBrightness, 0.25F);
                        } else {
//                    blackHue = mixColourComponent(blackHue, redHue, 0.5F);
//                    blackSaturation = mixColourComponent(blackSaturation, redSaturation, 0.5F);
                            redHue = Colouration.mixHueComponent(redHue, 0.1F, 0.8F);
                            redSaturation = Colouration.mixColourComponent(redSaturation, 0.0F, 0.1F);
                            redBrightness = Colouration.mixColourComponent(redBrightness, 1.0F, 0.4F);
                            blackHue = Colouration.mixHueComponent(blackHue, redHue, 0.6F);
                            blackSaturation = Colouration.mixColourComponent(blackSaturation, redSaturation, 0.5F);
                            blackBrightness = Colouration.mixColourComponent(blackBrightness, redBrightness, 0.4F);
                        }
                    } else {
                        redHue = Colouration.mixHueComponent(redHue, 0.1F, 0.85F);
                        redSaturation = Colouration.mixColourComponent(redSaturation, 0.0F, 0.85F);
                        redBrightness = Colouration.mixColourComponent(redBrightness, 1.0F, 0.84F);
//                        blackSaturation = Colouration.mixColourComponent(blackBrightness, 1.0F, 0.1F);
                        blackBrightness = Colouration.mixColourComponent(blackBrightness, 1.0F, 0.75F);
                    }
                } else if (gene[2] == 3 || gene[3] == 3) {
                    //charolais dilution very harsh
                    if (gene[2] == 3 && gene[3] == 3) {
                        redHue = 0.1F;
                        redSaturation = 0.0F;
                        redBrightness = 1.0F;
                        blackHue = 0.1F;
                        blackSaturation = 0.0F;
                        blackBrightness = 1.0F;
                    } else {
                        redHue = Colouration.mixHueComponent(redHue, 0.1F, 0.80F);
                        redSaturation = Colouration.mixColourComponent(redSaturation, 0.0F, 0.25F);
                        redBrightness = Colouration.mixColourComponent(redBrightness, 1.0F, 0.8F);
                        blackHue = Colouration.mixHueComponent(blackHue, 0.1F, 0.80F);
                        blackSaturation = Colouration.mixColourComponent(blackBrightness, 0.0F, 0.2F);
                        blackBrightness = Colouration.mixColourComponent(blackBrightness, 1.0F, 0.8F);
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

                if (!this.isFemale()) {
                    shadeIntensity = 0.5F * (shadeIntensity + 1.0F);
                }

                if (shadeIntensity <= 0.0F) {
                    shadeIntensity = 0.00001F;
                } else if (shadeIntensity >= 1.0F) {
                    shadeIntensity = 0.99999F;
                }

                this.colouration.setMelaninColour(Colouration.HSBAtoABGR(melanin[0], melanin[1], melanin[2], shadeIntensity));
                this.colouration.setPheomelaninColour(Colouration.HSBtoABGR(pheomelanin[0], pheomelanin[1], pheomelanin[2]));

            } else if (this.isChild() && !(genes.testGenes(0, 1, 0))) {
                this.colouration.setBabyAlpha((float) this.getAge() / (float) this.getAdultAge());
            }
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
    public ActionResultType func_230254_b_(PlayerEntity entityPlayer, Hand hand) {
        ItemStack itemStack = entityPlayer.getHeldItem(hand);
        Item item = itemStack.getItem();

        if (item == Items.NAME_TAG) {
            itemStack.interactWithEntity(entityPlayer, this, hand);
            return ActionResultType.SUCCESS;
        }

        /**
        if (item == Items.BUCKET || item instanceof MixableMilkBucket && !this.isChild() && getEntityStatus().equals(EntityState.MOTHER.toString())) {
            int currentMilk = getMilkAmount();
            int milkColour = 16777215;
            float milkFat = 0.037F;
            ItemStack milkbucket = new ItemStack(ModItems.MIXABLE_MILK);
            int fillamount;
            if (item instanceof MixableMilkBucket) {
                fillamount = ((MixableMilkBucket) milkbucket.getItem()).getSpaceLeft();
                fillamount = currentMilk >= fillamount ? fillamount : currentMilk;
            } else {
                fillamount = currentMilk;
            }

            ((MixableMilkBucket)milkbucket.getItem()).mix(milkbucket, fillamount >= 6 ? 6 : fillamount, milkColour, milkFat, MixableMilkBucket.MilkType.COW, MixableMilkBucket.InoculationType.NONE);
            setMilkAmount(currentMilk - ((MixableMilkBucket)milkbucket.getItem()).getSpaceLeft());

            entityPlayer.playSound(SoundEvents.ENTITY_COW_MILK, 1.0F, 1.0F);
            itemStack.shrink(1);
            if (itemStack.isEmpty()) {
                entityPlayer.setHeldItem(hand, milkbucket);
            } else if (!entityPlayer.inventory.addItemStackToInventory(milkbucket)) {
                entityPlayer.dropItem(milkbucket, false);
            }
        }
         */

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

                float milkBagSize = resultingMilkAmount / (30*(getAnimalSize()/1.5F)*(this.maxBagSize/1.5F));

                this.setBagSize((1.1F*milkBagSize*(this.maxBagSize-1.0F))+1.0F);
            }

            int resultAmount = bucketSize - maxRefill + refillAmount;

            ItemStack resultItem = new ItemStack(Items.BUCKET);

            switch (resultAmount) {
                case 0:
                    entityPlayer.playSound(SoundEvents.ENTITY_COW_HURT, 1.0F, 1.0F);
                    return ActionResultType.SUCCESS;
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

            return ActionResultType.SUCCESS;
        }

        return super.func_230254_b_(entityPlayer, hand);
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

        //this takes the number of milk points a cow has over the number possible to make a number between 0 and 1.
        float milkBagSize = this.getMilkAmount() / (30*(getAnimalSize()/1.5F)*(this.maxBagSize/1.5F));
        this.setBagSize((1.1F*milkBagSize*(this.maxBagSize-1.0F))+1.0F);

        ListNBT geneList = compound.getList("Genes", 10);

        this.dwarf = (geneList.getCompound(2+26).getInt("Agene") == 1 || geneList.getCompound(2+27).getInt("Agene") == 1) ? 0.2F : 0.0F;

        configureAI();
    }

    @Nullable
    @Override
    public ILivingEntityData onInitialSpawn(IServerWorld inWorld, DifficultyInstance difficulty, SpawnReason spawnReason, @Nullable ILivingEntityData livingdata, @Nullable CompoundNBT itemNbt) {
        return commonInitialSpawnSetup(inWorld, livingdata, getAdultAge(), 64800, 108000, spawnReason);
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
    public Genes createInitialBreedGenes(IWorld world, BlockPos pos, String breed) {
        return new CowGeneticsInitialiser().generateWithBreed(world, pos, breed);
    }

    @Override
    protected void initializeHealth(EnhancedAnimalAbstract animal, float health) {
        float sizeVar = 1.0F - (Math.abs(1F - animal.getAnimalSize()));
        health = 10 * sizeVar;
        super.initializeHealth(animal, health);
    }

    @Override
    public void initilizeAnimalSize() {
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
            this.goalSelector.addGoal(2, new EnhancedBreedGoal(this, speed));
            this.goalSelector.addGoal(3, new EnhancedTemptGoal(this, speed, speed*1.25D, false, Ingredient.fromItems(Items.CARROT_ON_A_STICK)));
            this.goalSelector.addGoal(3, new EnhancedTemptGoal(this, speed,speed*1.25D, false, TEMPTATION_ITEMS));
            this.goalSelector.addGoal(4, new EnhancedFollowParentGoal(this, speed*1.25D));
            this.goalSelector.addGoal(4, new EnhancedAINurseFromMotherGoal(this, motherUUID, speed*1.25D));
            this.goalSelector.addGoal(5, new StayShelteredGoal(this, 5723, 7000, 0));
            this.goalSelector.addGoal(6, new SeekShelterGoal(this, 1.0D, 5723, 7000, 0));
//            grazingGoal = new EnhancedWaterAvoidingRandomWalkingEatingGoal(this, speed, 7, 0.001F, 120, 2, 20);
            this.goalSelector.addGoal(7, new EnhancedEatPlantsGoal(this, createGrazingMap()));
            grazingGoal = new GrazingGoal(this, speed);
            this.goalSelector.addGoal(8, grazingGoal);
            this.goalSelector.addGoal(9, new EnhancedWanderingGoal(this, speed));
        }
        aiConfigured = true;
    }

}
