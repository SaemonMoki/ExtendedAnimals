package mokiyoki.enhancedanimals.entity;

import mokiyoki.enhancedanimals.ai.general.EnhancedLookAtGoal;
import mokiyoki.enhancedanimals.ai.general.EnhancedLookRandomlyGoal;
import mokiyoki.enhancedanimals.ai.general.EnhancedPanicGoal;
import mokiyoki.enhancedanimals.ai.general.EnhancedTemptGoal;
import mokiyoki.enhancedanimals.ai.general.EnhancedWaterAvoidingRandomWalkingEatingGoal;
import mokiyoki.enhancedanimals.entity.Genetics.SheepGeneticsInitialiser;
import mokiyoki.enhancedanimals.entity.util.Colouration;
import mokiyoki.enhancedanimals.init.ModBlocks;
import mokiyoki.enhancedanimals.init.ModItems;
import mokiyoki.enhancedanimals.items.DebugGenesBook;
import mokiyoki.enhancedanimals.config.EanimodCommonConfig;
import mokiyoki.enhancedanimals.util.Genes;
import mokiyoki.enhancedanimals.util.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.Pose;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.BreedGoal;
import net.minecraft.entity.ai.goal.FollowParentGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AirItem;
import net.minecraft.item.DyeColor;
import net.minecraft.item.DyeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

import static mokiyoki.enhancedanimals.util.handlers.EventRegistry.ENHANCED_SHEEP;

public class EnhancedSheep extends EnhancedAnimalChestedAbstract implements net.minecraftforge.common.IForgeShearable {

    //avalible UUID spaces : [ S X X 3 X 5 6 7 - 8 9 10 11 - 12 13 14 15 - 16 17 18 19 - 20 21 22 23 24 25 26 27 28 29 30 31 ]

    private static final DataParameter<Integer> COAT_LENGTH = EntityDataManager.createKey(EnhancedSheep.class, DataSerializers.VARINT);
    private static final DataParameter<Float> BAG_SIZE = EntityDataManager.createKey(EnhancedSheep.class, DataSerializers.FLOAT);
    private static final DataParameter<Byte> DYE_COLOUR = EntityDataManager.<Byte>createKey(EnhancedSheep.class, DataSerializers.BYTE);
    private static final DataParameter<Integer> MILK_AMOUNT = EntityDataManager.createKey(EnhancedSheep.class, DataSerializers.VARINT);

    private static final String[] SHEEP_TEXTURES_UNDER = new String[] {
            "c_solid_tan.png", "c_solid_black.png", "c_solid_choc.png", "c_solid_lighttan.png",
            "c_solid_tan_red.png", "c_solid_choc.png", "c_solid_tan", "c_solid_lighttan_red.png"
    };

    private static final String[] SHEEP_TEXTURES_PATTERN = new String[] {
            "", "c_solid_white.png", "c_badger_black.png", "c_badger_choc.png", "c_mouflonbadger_black.png", "c_mouflonbadger_choc.png", "c_mouflon_black.png", "c_mouflon_choc.png", "c_blue_black.png", "c_blue_choc.png", "c_solid_black.png", "c_solid_choc.png",
                "c_solid_white.png", "c_badger_black_red.png", "c_badger_choc_red.png", "c_mouflonbadger_black_red.png", "c_mouflonbadger_choc_red.png", "c_mouflon_black_red.png", "c_mouflon_choc_red.png", "c_blue_black_red.png", "c_blue_choc_red.png", "c_solid_black_red.png", "c_solid_choc_red.png"
    };

    private static final String[] SHEEP_TEXTURES_GREY = new String[] {
            "", "c_grey.png"
    };

    private static final String[] SHEEP_TEXTURES_SPOTS = new String[] {
            "", "c_spot0.png",  "c_spot1.png",  "c_spot2.png", "c_spot3.png",  "c_spot4.png",  "c_spot5.png", "c_spot6.png",  "c_spot7.png",  "c_spot8.png", "c_spot9.png",  "c_spota.png",  "c_spotb.png", "c_spotc.png",  "c_spotd.png",  "c_spote.png",  "c_spotf.png"
    };

    private static final String[] SHEEP_TEXTURES_SKIN = new String[] {
            "skin_pink.png"
    };

    private static final String[] SHEEP_TEXTURES_HOOVES = new String[] {
            "hooves_black.png"
    };

    private static final String[] SHEEP_TEXTURES_FUR = new String[] {
            "c_fur_wooly.png"
    };

    private static final String[] SHEEP_TEXTURES_EYES = new String[] {
            "eyes_black.png"
    };

    private static final Ingredient TEMPTATION_ITEMS = Ingredient.fromItems(Blocks.MELON, Blocks.PUMPKIN, Blocks.GRASS, Blocks.TALL_GRASS, Items.VINE, Blocks.HAY_BLOCK, Items.CARROT, Items.WHEAT, Items.ROSE_BUSH, Items.DANDELION, Items.SUGAR, Items.APPLE, ModBlocks.UNBOUNDHAY_BLOCK);
    private static final Ingredient BREED_ITEMS = Ingredient.fromItems(Blocks.HAY_BLOCK, Items.WHEAT);

    private final List<String> sheepFleeceTextures = new ArrayList<>();
    private static final int SEXLINKED_GENES_LENGTH = 2;

    protected float maxBagSize;
    private int currentBagSize;
    private int maxCoatLength;
    private int currentCoatLength;
    private int timeForGrowth = 0;

//    protected boolean aiConfigured = false;
    private String motherUUID = "";

    public EnhancedSheep(EntityType<? extends EnhancedSheep> entityType, World worldIn) {
        super(entityType, worldIn, SEXLINKED_GENES_LENGTH, Reference.SHEEP_AUTOSOMAL_GENES_LENGTH, TEMPTATION_ITEMS, BREED_ITEMS, createFoodMap(), true);
        this.setSheepSize();
        this.timeUntilNextMilk = this.rand.nextInt(this.rand.nextInt(8000) + 4000);
    }

    private static Map<Item, Integer> createFoodMap() {
        return new HashMap() {{
            put(new ItemStack(Blocks.MELON).getItem(), 10000);
            put(new ItemStack(Blocks.PUMPKIN).getItem(), 10000);
            put(new ItemStack(Items.TALL_GRASS).getItem(), 6000);
            put(new ItemStack(Items.GRASS).getItem(), 3000);
            put(new ItemStack(Items.VINE).getItem(), 3000);
            put(new ItemStack(Blocks.HAY_BLOCK).getItem(), 54000);
            put(new ItemStack(Items.WHEAT).getItem(), 6000);
            put(new ItemStack(Items.CARROT).getItem(), 3000);
            put(new ItemStack(Items.GOLDEN_CARROT).getItem(), 12000);
            put(new ItemStack(Items.SWEET_BERRIES).getItem(), 1500);
            put(new ItemStack(Items.DANDELION).getItem(), 1500);
            put(new ItemStack(Items.ROSE_BUSH).getItem(), 1500);
            put(new ItemStack(Items.SUGAR).getItem(), 1500);
            put(new ItemStack(Items.APPLE).getItem(), 1500);
            put(new ItemStack(ModBlocks.UNBOUNDHAY_BLOCK).getItem(), 54000);
        }};

    }

//    private int sheepTimer;
//    private EnhancedWaterAvoidingRandomWalkingEatingGoal grazingGoal;

//    /** Map from EnumDyeColor to RGB values for passage to GlStateManager.color() */
//    private static final Map<DyeColor, float[]> DYE_TO_RGB = Maps.newEnumMap(Arrays.stream(DyeColor.values()).collect(Collectors.toMap((DyeColor p_200204_0_) -> {
//        return p_200204_0_;
//    }, EnhancedSheep::createSheepDyeColor)));

    private static int createSheepDyeColor(DyeColor enumDyeColour) {
        return enumDyeColour.getColorValue();
    }

    @OnlyIn(Dist.CLIENT)
    public static int getDyeRgb(DyeColor dyeColour) {
        return Colouration.getABGRFromARGB(dyeColour.getColorValue());
    }

    /**
     * Gets the wool color of this sheep.
     */
    public DyeColor getFleeceDyeColour() {
        return DyeColor.byId(this.dataManager.get(DYE_COLOUR) & 15);
    }

    /**
     * Sets the wool color of this sheep
     */
    public void setFleeceDyeColour(DyeColor colour) {
        byte b0 = this.dataManager.get(DYE_COLOUR);
        this.dataManager.set(DYE_COLOUR, (byte)(b0 & 240 | colour.getId() & 15));
    }

    protected float getStandingEyeHeight(Pose poseIn, EntitySize sizeIn) {
        return 0.95F * sizeIn.height;
    }

    private int timeUntilNextMilk;
    private EnhancedWaterAvoidingRandomWalkingEatingGoal wanderEatingGoal;

    @Override
    protected void registerGoals() {
        //Todo add the temperamants
        this.wanderEatingGoal = new EnhancedWaterAvoidingRandomWalkingEatingGoal(this, 1.0D, 7, 0.001F, 120, 2, 50);
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new EnhancedPanicGoal(this, 1.25D));
        this.goalSelector.addGoal(2, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(3, new EnhancedTemptGoal(this, 1.0D, 1.2D, false, TEMPTATION_ITEMS));
        this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.1D));
        this.goalSelector.addGoal(5, this.wanderEatingGoal);
        this.goalSelector.addGoal(7, new EnhancedLookAtGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.addGoal(8, new EnhancedLookRandomlyGoal(this));
    }

    //TODO put new sheep behaviour here

    @Override
    public boolean canHaveBlanket() {
        return false;
    }

    protected void updateAITasks()
    {
        this.animalEatingTimer = this.wanderEatingGoal.getEatingGrassTimer();
        super.updateAITasks();
    }

    @Override
    public EntitySize getSize(Pose poseIn) {
        return EntitySize.flexible(0.8F, 1.2F);
    }

    protected void registerData() {
        super.registerData();
        this.dataManager.register(COAT_LENGTH, 0);
        this.dataManager.register(DYE_COLOUR, Byte.valueOf((byte)0));
        this.dataManager.register(BAG_SIZE, 0.0F);
        this.dataManager.register(MILK_AMOUNT, 0);
    }

    protected String getSpecies() {
        return "entity.eanimod.enhanced_sheep";
    }

    protected int getAdultAge() { return 72000;}

    private void setCoatLength(int coatLength) {
        this.dataManager.set(COAT_LENGTH, coatLength);
    }

    public int getCoatLength() {
        return this.dataManager.get(COAT_LENGTH);
    }

    public static AttributeModifierMap.MutableAttribute prepareAttributes() {
        return MobEntity.func_233666_p_()
                .createMutableAttribute(Attributes.MAX_HEALTH, 8.0D)
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.23D);
    }

    protected void setMilkAmount(Integer milkAmount) {
        this.dataManager.set(MILK_AMOUNT, milkAmount);
    }

    public Integer getMilkAmount() { return this.dataManager.get(MILK_AMOUNT); }

    public boolean decreaseMilk(int decrease) {
        int milk = getMilkAmount();
        if (milk >= decrease) {
            milk = milk - decrease;
            setMilkAmount(milk);
            return true;
        } else {
            this.playSound(SoundEvents.ENTITY_SHEEP_HURT, 1.0F, 1.0F);
            return false;
        }
    }

    @Override
    public void livingTick() {
        super.livingTick();

        if (!this.world.isRemote) {
            if (getEntityStatus().equals(EntityState.MOTHER.toString())) {
                if (hunger <= 24000) {
                    if (--this.timeUntilNextMilk <= 0) {
                        int milk = getMilkAmount();
                        if (milk < (6*this.maxBagSize)) {
                            milk++;
                            setMilkAmount(milk);
                            this.timeUntilNextMilk = this.rand.nextInt(this.rand.nextInt(8000) + 4000);

                            float milkBagSize = milk / (6*this.maxBagSize);

                            this.setBagSize((milkBagSize*(this.maxBagSize/3.0F))+(this.maxBagSize*2.0F/3.0F));
                        }
                    }
                }

                if (this.timeUntilNextMilk == 0) {
                    this.lactationTimer++;
                } else if (getMilkAmount() <= (6*this.maxBagSize) && this.lactationTimer >= -36000) {
                    this.lactationTimer--;
                }

                if (this.lactationTimer >= 0) {
                    setEntityStatus(EntityState.ADULT.toString());
                }
            }
        }

    }

    @Override
    protected void runExtraIdleTimeTick() {
        if (hunger <= 36000) {
            timeForGrowth++;
        }

        int maxcoat = (int)(this.maxCoatLength*(((float)this.getAge()/(float)this.getAdultAge())));

        if ((maxcoat) > 0) {
            int[] genes = this.genetics.getAutosomalGenes();
            //first check is for self shearing sheep
            if (currentCoatLength == maxcoat && (genes[46] == 1 || genes[47] == 1) && timeForGrowth >= 24000) {
                timeForGrowth = 0;
                currentCoatLength = rand.nextInt(maxcoat/2);
            } else if (timeForGrowth >= (24000 / maxcoat)) {
                timeForGrowth = 0;
                if (maxcoat > currentCoatLength) {
                    currentCoatLength++;
                    setCoatLength(currentCoatLength);
                }
            }

        }
    }

    @Override
    protected void lethalGenes() {
    }

    @Override
    protected int getNumberOfChildren() {
        int[] genes = this.genetics.getAutosomalGenes();
        int lambRange;
        int lambAverage = 1;
        int numberOfLambs;

        if (genes[38] == 1 || genes[39] == 1) {
            //1 baby
            lambRange = 1;
        } else if (genes[38] == 3 && genes[39] == 3) {
            // 2-3 babies
            lambRange = 2;
            lambAverage = 2;
        } else if (genes[38] == 2 && genes[39] == 2) {
            //1 to 2 babies
            lambRange = 2;
        } else {
            // 1-3 babies
            lambRange = 3;
            lambAverage = 1;
        }

        if (lambRange != 1) {
            numberOfLambs = ThreadLocalRandom.current().nextInt(lambRange) + lambAverage;
        } else {
            numberOfLambs = 1;
        }

        return numberOfLambs;
    }

    @Override
    protected void incrementHunger() {
        if(sleeping) {
            hunger = hunger + (0.5F*getHungerModifier());
        } else {
            hunger = hunger + (1.0F*getHungerModifier());
        }
    }

    protected void createAndSpawnEnhancedChild(World inWorld) {
        EnhancedSheep enhancedsheep = ENHANCED_SHEEP.create(this.world);
        Genes babyGenes = new Genes(this.genetics).makeChild(this.isFemale(), this.mateGender, this.mateGenetics);
        defaultCreateAndSpawn(enhancedsheep, inWorld, babyGenes, -72000);
        enhancedsheep.setMaxCoatLength();
        enhancedsheep.currentCoatLength = enhancedsheep.maxCoatLength;
        enhancedsheep.setCoatLength(enhancedsheep.currentCoatLength);
        this.world.addEntity(enhancedsheep);
    }

    @Override
    protected boolean canBePregnant() {
        return true;
    }

    @Override
    protected boolean canLactate() {
        return true;
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

    protected void playStepSound(BlockPos pos, BlockState blockIn) {
        this.playSound(SoundEvents.ENTITY_SHEEP_STEP, 0.15F, 1.0F);
        if (!this.isSilent() && this.getBells()) {
            this.playSound(SoundEvents.BLOCK_NOTE_BLOCK_CHIME, 1.5F, 1.0F);
        }
    }

    public void eatGrassBonus() {
        this.setSheared(false);
        if (!this.isChild() && (maxCoatLength > currentCoatLength)){
            this.currentCoatLength ++ ;
            setCoatLength(currentCoatLength);
        }

    }

    @Override
    public boolean isShearable(ItemStack item, World world, BlockPos pos) {
        if (!this.world.isRemote && currentCoatLength >=1) {
            return true;
        }
        return false;
    }

    @Override
    public java.util.List<ItemStack> onSheared(PlayerEntity player, ItemStack item, World world, BlockPos pos, int fortune) {
        java.util.List<ItemStack> ret = new java.util.ArrayList<>();
        if (!this.world.isRemote) {
            int woolCount = 0;
            if (currentCoatLength == 1) {
                int i = this.rand.nextInt(5);
                if (i>3){
                    woolCount++;
                }
            } else if (currentCoatLength == 2) {
                int i = this.rand.nextInt(5);
                if (i>2){
                    woolCount++;
                }
            } else if (currentCoatLength == 3) {
                int i = this.rand.nextInt(5);
                if (i>1){
                    woolCount++;
                }
            } else if (currentCoatLength == 4) {
                int i = this.rand.nextInt(5);
                if (i>0) {
                    woolCount++;
                }
            } else if (currentCoatLength >= 5) {
                woolCount++;

                if (currentCoatLength == 6) {
                    int i = this.rand.nextInt(5);
                    if (i>3){
                        woolCount++;
                    }
                } else if (currentCoatLength == 7) {
                    int i = this.rand.nextInt(5);
                    if (i>2){
                        woolCount++;
                    }
                } else if (currentCoatLength == 8) {
                    int i = this.rand.nextInt(5);
                    if (i>1){
                        woolCount++;
                    }
                } else if (currentCoatLength == 9) {
                    int i = this.rand.nextInt(5);
                    if (i>0) {
                        woolCount++;
                    }
                } else if (currentCoatLength >= 10) {
                    woolCount++;
                    if (currentCoatLength == 11) {
                        int i = this.rand.nextInt(5);
                        if (i>3){
                            woolCount++;
                        }
                    } else if (currentCoatLength == 12) {
                        int i = this.rand.nextInt(5);
                        if (i>2){
                            woolCount++;
                        }
                    } else if (currentCoatLength == 13) {
                        int i = this.rand.nextInt(5);
                        if (i>1){
                            woolCount++;
                        }
                    } else if (currentCoatLength == 14) {
                        int i = this.rand.nextInt(5);
                        if (i>0) {
                            woolCount++;
                        }
                    } else if (currentCoatLength >= 15) {
                        woolCount++;
                    }
                }
            }

            for (int c = 0-woolCount; c < 0; c++){

                DyeColor woolColour = getWoolColour();

                switch (woolColour) {
                    case WHITE:
                    default:
                        ret.add(new ItemStack(Blocks.WHITE_WOOL));
                        break;
                    case ORANGE:
                        ret.add(new ItemStack(Blocks.ORANGE_WOOL));
                        break;
                    case MAGENTA:
                        ret.add(new ItemStack(Blocks.MAGENTA_WOOL));
                        break;
                    case LIGHT_BLUE:
                        ret.add(new ItemStack(Blocks.LIGHT_BLUE_WOOL));
                        break;
                    case YELLOW:
                        ret.add(new ItemStack(Blocks.YELLOW_WOOL));
                        break;
                    case LIME:
                        ret.add(new ItemStack(Blocks.LIME_WOOL));
                        break;
                    case PINK:
                        ret.add(new ItemStack(Blocks.PINK_WOOL));
                        break;
                    case GRAY:
                        ret.add(new ItemStack(Blocks.GRAY_WOOL));
                        break;
                    case LIGHT_GRAY:
                        ret.add(new ItemStack(Blocks.LIGHT_GRAY_WOOL));
                        break;
                    case CYAN:
                        ret.add(new ItemStack(Blocks.CYAN_WOOL));
                        break;
                    case PURPLE:
                        ret.add(new ItemStack(Blocks.PURPLE_WOOL));
                        break;
                    case BLUE:
                        ret.add(new ItemStack(Blocks.BLUE_WOOL));
                        break;
                    case BROWN:
                        ret.add(new ItemStack(Blocks.BROWN_WOOL));
                        break;
                    case GREEN:
                        ret.add(new ItemStack(Blocks.GREEN_WOOL));
                        break;
                    case RED:
                        ret.add(new ItemStack(Blocks.RED_WOOL));
                        break;
                    case BLACK:
                        ret.add(new ItemStack(Blocks.BLACK_WOOL));
                        break;
                }
            }
        }
        currentCoatLength = 0;
        setCoatLength(currentCoatLength);
        return ret;
    }

    private DyeColor getWoolColour() {
        int[] genes = this.genetics.getAutosomalGenes();
        int spots = 0;
        DyeColor returnDye;

        if ((genes[0] == 1 || genes[1] == 1) && !(genes[4] == 1 || genes[5] == 1)) {
            //sheep is white
            spots = 2;
        } else if (genes[8] == 2 && genes[9] == 2){
            // 1 out of 3 chance to drop white wool instead
            spots = this.rand.nextInt(3);
        }

        if (spots != 2) {
            if (genes[4] == 1 || genes[5] == 1) {
                //sheep is dominant black
                if (genes[2] == 1 || genes[3] == 1){
                    //is not chocolate
                    returnDye = DyeColor.BLACK;
                }else{
                    //is chocolate
                    returnDye = DyeColor.BROWN;
                }
            } else if (genes[4] == 3 && genes[5] == 3) {
                //red sheep
                if (genes[0] == 2 || genes[1] == 2 || genes[0] == 3 || genes[1] == 3) {
                    returnDye = DyeColor.WHITE;
                } else {
                    if ((genes[0] >= 5 && genes[1] >= 5) && (genes[0] == 5 || genes[1] == 5)) {
                        returnDye = DyeColor.LIGHT_GRAY;
                    } else {
                        returnDye = DyeColor.BROWN;
                    }
                }
            } else if (genes[0] >= 3 && genes[1] >= 3){
                if (genes[0] == 3 || genes[1] == 3) {
                    //badgerface or badgerface mix brown sheep
                    returnDye = DyeColor.BROWN;
                } else if (genes[0] == 4 || genes[1] == 4) {
                    //mouflon black or black mix with blue
                    if (genes[2] == 1 || genes[3] == 1){
                        //is not chocolate
                        returnDye = DyeColor.BLACK;
                    }else{
                        //is chocolate
                        returnDye = DyeColor.BROWN;
                    }
                } else if (genes[0] == 5 || genes[1] == 5) {
                    //blue
                    if (genes[2] == 1 || genes[3] == 1) {
                        returnDye = DyeColor.GRAY;
                    } else {
                        returnDye = DyeColor.BROWN;
                    }
                } else {
                    if (genes[2] == 1 || genes[3] == 1){
                        //is not chocolate
                        returnDye = DyeColor.BLACK;
                    }else{
                        //is chocolate
                        returnDye = DyeColor.BROWN;
                    }
                }
            }else if ((genes[0] == 2 || genes[1] == 2) && !(genes[0] == 3 || genes[1] == 3)) {
                returnDye = DyeColor.LIGHT_GRAY;
            } else {
                returnDye = DyeColor.WHITE;
            }
        } else {
            returnDye = DyeColor.WHITE;
        }

        if (returnDye == DyeColor.WHITE || returnDye == DyeColor.LIGHT_GRAY){
            switch (this.getFleeceDyeColour()) {
                case WHITE:
                default:
                    if (returnDye == DyeColor.LIGHT_GRAY) {
                        returnDye = DyeColor.LIGHT_GRAY;
                    }
                    break;
                case ORANGE:
                    returnDye = DyeColor.ORANGE;
                    break;
                case MAGENTA:
                    returnDye = DyeColor.MAGENTA;
                    break;
                case LIGHT_BLUE:
                    returnDye = DyeColor.LIGHT_BLUE;
                    break;
                case YELLOW:
                    returnDye = DyeColor.YELLOW;
                    break;
                case LIME:
                    returnDye = DyeColor.LIME;
                    break;
                case PINK:
                    returnDye = DyeColor.PINK;
                    break;
                case GRAY:
                    returnDye = DyeColor.GRAY;
                    break;
                case LIGHT_GRAY:
                    returnDye = DyeColor.LIGHT_GRAY;
                    break;
                case CYAN:
                    returnDye = DyeColor.CYAN;
                    break;
                case PURPLE:
                    returnDye = DyeColor.PURPLE;
                    break;
                case BLUE:
                    returnDye = DyeColor.BLUE;
                    break;
                case BROWN:
                    returnDye = DyeColor.BROWN;
                    break;
                case GREEN:
                    returnDye = DyeColor.GREEN;
                    break;
                case RED:
                    returnDye = DyeColor.RED;
                    break;
                case BLACK:
                    returnDye = DyeColor.BLACK;
                    break;
            }
        } else if (returnDye == DyeColor.BROWN || returnDye == DyeColor.GRAY) {
            switch (this.getFleeceDyeColour()) {
                case ORANGE:
                    returnDye = DyeColor.BROWN;
                    break;
                case MAGENTA:
                    returnDye = DyeColor.BROWN;
                    break;
                case LIGHT_BLUE:
                    returnDye = DyeColor.BLACK;
                    break;
                case YELLOW:
                    returnDye = DyeColor.BROWN;
                    break;
                case LIME:
                    returnDye = DyeColor.BLACK;
                    break;
                case PINK:
                    returnDye = DyeColor.BROWN;
                    break;
                case GRAY:
                    returnDye = DyeColor.BLACK;
                    break;
                case LIGHT_GRAY:
                    returnDye = DyeColor.GRAY;
                    break;
                case CYAN:
                    returnDye = DyeColor.BLACK;
                    break;
                case PURPLE:
                    returnDye = DyeColor.BLACK;
                    break;
                case BLUE:
                    returnDye = DyeColor.BLACK;
                    break;
                case BROWN:
                    returnDye = DyeColor.BROWN;
                    break;
                case GREEN:
                    returnDye = DyeColor.BLACK;
                    break;
                case RED:
                    returnDye = DyeColor.BLACK;
                    break;
                case BLACK:
                    returnDye = DyeColor.BLACK;
                    break;
            }
        }

        if(this.getFleeceDyeColour() == DyeColor.BLACK) {
            returnDye = DyeColor.BLACK;
        }

        return returnDye;
    }

    private Block getWoolBlocks () {
        DyeColor woolColour = getWoolColour();
        Block returnBlocks;

        switch (woolColour) {
            case WHITE:
            default:
                returnBlocks = Blocks.WHITE_WOOL;
                break;
            case ORANGE:
                returnBlocks = Blocks.ORANGE_WOOL;
                break;
            case MAGENTA:
                returnBlocks = Blocks.MAGENTA_WOOL;
                break;
            case LIGHT_BLUE:
                returnBlocks = Blocks.LIGHT_BLUE_WOOL;
                break;
            case YELLOW:
                returnBlocks = Blocks.YELLOW_WOOL;
                break;
            case LIME:
                returnBlocks = Blocks.LIME_WOOL;
                break;
            case PINK:
                returnBlocks = Blocks.PINK_WOOL;
                break;
            case GRAY:
                returnBlocks = Blocks.GRAY_WOOL;
                break;
            case LIGHT_GRAY:
                returnBlocks = Blocks.LIGHT_GRAY_WOOL;
                break;
            case CYAN:
                returnBlocks = Blocks.CYAN_WOOL;
                break;
            case PURPLE:
                returnBlocks = Blocks.PURPLE_WOOL;
                break;
            case BLUE:
                returnBlocks = Blocks.BLUE_WOOL;
                break;
            case BROWN:
                returnBlocks = Blocks.BROWN_WOOL;
                break;
            case GREEN:
                returnBlocks = Blocks.GREEN_WOOL;
                break;
            case RED:
                returnBlocks = Blocks.RED_WOOL;
                break;
            case BLACK:
                returnBlocks = Blocks.BLACK_WOOL;
                break;
        }

        return returnBlocks;
    }

    private void setSheepSize() {
        int[] genes = this.genetics.getAutosomalGenes();
        float size = 0.4F;

        //(56/57) 1-2 minature [wildtype, minature]
        //(58/59) 1-16 size genes reducer [wildtype, smaller smaller smallest...] adds milk fat [none to most]
        //(60/61) 1-16 size genes adder [wildtype, bigger bigger biggest...]
        //(62/63) 1-3 size genes varient1 [wildtype, smaller, smallest]
        //(64/65) 1-3 size genes varient2 [wildtype, smaller, smallest]

        size = size - (genes[58] - 1)*0.01F;
        size = size - (genes[59] - 1)*0.01F;
        size = size + (genes[60] - 1)*0.0075F;
        size = size + (genes[61] - 1)*0.0075F;

        if (genes[56] == 2 || genes[57] == 2) {
            if (genes[56] == 2 && genes[57] == 2) {
                size = size * 0.8F;
            } else {
                size = size * 0.9F;
            }
        }

        if (genes[62] == 2 || genes[63] == 2) {
            size = size * 0.975F;
        } else if (genes[62] == 3 || genes[63] == 3) {
            size = size * 0.925F;
        } else {
            size = size * 1.025F;
        }

        if (genes[64] == 2 || genes[65] == 2) {
            size = size * 1.05F;
        } else if (genes[64] == 3 || genes[65] == 3) {
            size = size * 1.1F;
        }

        if (size > 0.6F) {
            size = 0.6F;
        }

        size = size + 0.43F;

        // [ 0.52325 - 1.1 ]
        this.setAnimalSize(size);
    }

    @Override
    protected boolean canDropLoot() { return true; }

    protected void dropSpecialItems(DamageSource source, int looting, boolean recentlyHitIn) {
        super.dropSpecialItems(source, looting, recentlyHitIn);
        float size = this.getAnimalSize();
        int age = this.getAge();
        int meatDrop = this.rand.nextInt(4)+1;
        boolean woolDrop = false;
        boolean leatherDrop = false;
        int meatChanceMod;

        if (currentCoatLength == maxCoatLength && maxCoatLength >= 1) {
            if (currentCoatLength >= 5) {
                    woolDrop = true;
            } else if (currentCoatLength == 1) {
                int i = this.rand.nextInt(5);
                if (i>3){
                    woolDrop = true;
                }
            } else if (currentCoatLength == 2) {
                int i = this.rand.nextInt(5);
                if (i>2){
                    woolDrop = true;
                }
            } else if (currentCoatLength == 3) {
                int i = this.rand.nextInt(5);
                if (i>1){
                    woolDrop = true;
                }
            } else {
                int i = this.rand.nextInt(5);
                if (i>0) {
                    woolDrop = true;
                }
            }
        }

        if (maxCoatLength < 5 && !woolDrop) {
            int i = this.rand.nextInt(3);
            // 2 out of 3 times
            if (i != 0) {
                if (currentCoatLength == 0) {
                    // 5 out of 5 times
                    leatherDrop = true;
                } else if (currentCoatLength == 1) {
                    // 4 out of 5 times
                    i = this.rand.nextInt(5);
                    if (i != 0) {
                        leatherDrop = true;
                    }
                } else if (currentCoatLength == 2) {
                    // 3 out of 5 times
                    i = this.rand.nextInt(5);
                    if (i <= 2) {
                        leatherDrop = true;
                    }
                } else if (currentCoatLength == 3) {
                    // 2 out of 5 times
                    i = this.rand.nextInt(5);
                    if (i <= 1) {
                        leatherDrop = true;
                    }
                } else {
                    // 1 out of 5 times
                    i = this.rand.nextInt(5);
                    if (i == 0) {
                        leatherDrop = true;
                    }
                }
            }

        }

        if (age < 72000) {
            if (age >= 54000) {
                meatDrop = meatDrop - 1;
                meatChanceMod = (age-54000)/180;
            } else if (age >= 36000) {
                meatDrop = meatDrop - 2;
                meatChanceMod = (age-36000)/180;
            } else if (age >= 18000) {
                meatDrop = meatDrop - 3;
                meatChanceMod = (age-18000)/180;
            } else {
                meatDrop = meatDrop - 4;
                meatChanceMod = age/180;
            }

            int i = this.rand.nextInt(100);
            if (meatChanceMod > i) {
                meatDrop++;
            }

            if (woolDrop || leatherDrop) {
                i = this.rand.nextInt(100);
                if (age/720 > i) {
                    woolDrop = false;
                    leatherDrop = false;
                }
            }
        }

        if (meatDrop < 0) {
            meatDrop = 0;
        }

        if (this.isBurning()){
            ItemStack cookedMuttonStack = new ItemStack(Items.COOKED_MUTTON, meatDrop);
            this.entityDropItem(cookedMuttonStack);
        }else {
            ItemStack muttonStack = new ItemStack(Items.MUTTON, meatDrop);
            this.entityDropItem(muttonStack);
            if (woolDrop) {
                ItemStack fleeceStack = new ItemStack(getWoolBlocks(), 1);
                this.entityDropItem(fleeceStack);
            } else if (leatherDrop) {
                ItemStack leatherStack = new ItemStack(Items.LEATHER, 1);
                this.entityDropItem(leatherStack);
            }
        }
    }

    public boolean getSheared() {
        return (this.dataManager.get(DYE_COLOUR) & 16) != 0;
    }

    @OnlyIn(Dist.CLIENT)
    public String getSheepTexture() {
        if (this.enhancedAnimalTextures.isEmpty()) {
            this.setTexturePaths();
        }

        return getCompiledTextures("enhanced_sheep");
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    protected void setTexturePaths() {
        if (this.getSharedGenes() != null) {
            int[] genesForText = getSharedGenes().getAutosomalGenes();

            int under = 0;
            int pattern = 0;
            int grey = 0;
            int spots = 0;
            int skin = 0;
            int hooves = 0;
            int fur = 0;
            int eyes = 0;

            char[] uuidArry = getCachedUniqueIdString().toCharArray();

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

                //red variant
                if (genesForText[4] == 3 && genesForText[5] == 3){
                    under = under + 4;
                    pattern = pattern + 11;
                }
            }

            //chocolate variant
            if (genesForText[2] == 2 && genesForText[3] == 2){
                if (pattern == 0) {
                    under = 2;
                } else if (pattern!=1 && pattern!=12){
                    pattern = pattern + 1;
                }
            }

            //basic spots
            if (genesForText[8] == 2 && genesForText[9] == 2){
                if (Character.isDigit(uuidArry[1])){
                    spots = 2;
                }else {
                    spots = 1;
                }
            }

            addTextureToAnimal(SHEEP_TEXTURES_UNDER, under, null);
            addTextureToAnimal(SHEEP_TEXTURES_PATTERN, pattern, l -> l != 0);
            addTextureToAnimal(SHEEP_TEXTURES_GREY, grey, l -> l != 0);
            addTextureToAnimal(SHEEP_TEXTURES_SPOTS, spots, l -> l != 0);
            addTextureToAnimal(SHEEP_TEXTURES_SKIN, skin, null);
            addTextureToAnimal(SHEEP_TEXTURES_HOOVES, hooves, null);
            addTextureToAnimal(SHEEP_TEXTURES_FUR, fur, null);
            addTextureToAnimal(SHEEP_TEXTURES_EYES, eyes, null);
        }
    }

    @Override
    protected void setAlphaTexturePaths() {
    }

    @Override
    protected void setMaxBagSize(){
        float maxBagSize = 1.0F;

//        if (!this.isChild() && getSheepStatus().equals(EntityState.MOTHER.toString())){
//
//        }

//        // [ 0.25 to 1.0 ]
//        maxBagSize = maxBagSize + 1.0F;
//        if (maxBagSize > 1.5F) {
//            maxBagSize = 1.5F;
//        } else if (maxBagSize < 1.0F) {
//            maxBagSize = 1.0F;
//        }

        this.maxBagSize = maxBagSize;
    }

    @Override
    public ActionResultType func_230254_b_(PlayerEntity entityPlayer, Hand hand) {
        ItemStack itemStack = entityPlayer.getHeldItem(hand);
        Item item = itemStack.getItem();

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

                float milkBagSize = resultingMilkAmount / (6*maxBagSize);

                this.setBagSize((milkBagSize*(maxBagSize/3.0F))+(maxBagSize*2.0F/3.0F));
            }

            if (!this.world.isRemote) {
                this.setMilkAmount(currentMilk - refillAmount);
            }

            int resultAmount = bucketSize - maxRefill + refillAmount;

            ItemStack resultItem = new ItemStack(Items.BUCKET);

            switch (resultAmount) {
                case 0:
                    entityPlayer.playSound(SoundEvents.ENTITY_SHEEP_HURT, 1.0F, 1.0F);
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

        }

        if (!this.world.isRemote && !hand.equals(Hand.OFF_HAND)) {
            if (item instanceof AirItem) {
                int[] genes = this.genetics.getAutosomalGenes();
                if (!this.isChild() && (genes[46] == 1 || genes[47] == 1) && currentCoatLength == maxCoatLength) {
                        List<ItemStack> woolToDrop = onSheared(entityPlayer,null, this.world, getPosition(), 0);
                        woolToDrop.forEach(d -> {
                            net.minecraft.entity.item.ItemEntity ent = this.entityDropItem(d, 1.0F);
                            ent.setMotion(ent.getMotion().add((double)((rand.nextFloat() - rand.nextFloat()) * 0.1F), (double)(rand.nextFloat() * 0.05F), (double)((rand.nextFloat() - rand.nextFloat()) * 0.1F)));
                        });
                        onSheared(entityPlayer, ItemStack.EMPTY, this.world, getPosition(), 0);
                }

            } else if (item == Items.WATER_BUCKET) {
                this.setFleeceDyeColour(DyeColor.WHITE);
            } else if (item instanceof DyeItem) {
                DyeColor enumdyecolor = ((DyeItem)item).getDyeColor();
                if (enumdyecolor != this.getFleeceDyeColour()) {
                    this.setFleeceDyeColour(enumdyecolor);
                    if (!entityPlayer.abilities.isCreativeMode) {
                        itemStack.shrink(1);
                    }
                }
            }  else if (item instanceof DebugGenesBook) {
                Minecraft.getInstance().keyboardListener.setClipboardString(this.dataManager.get(SHARED_GENES));
            }
        }
        return super.func_230254_b_(entityPlayer, hand);
    }

    @Override
    protected  int gestationConfig() {
        return EanimodCommonConfig.COMMON.gestationDaysSheep.get();
    }

    /**
     * make a sheep sheared if set to true
     */
    public void setSheared(boolean sheared) {
        byte b0 = this.dataManager.get(DYE_COLOUR);
        if (sheared) {
            this.dataManager.set(DYE_COLOUR, (byte)(b0 | 16));
        } else {
            this.dataManager.set(DYE_COLOUR, (byte)(b0 & -17));
        }
    }

    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        compound.putByte("Colour", (byte)this.getFleeceDyeColour().getId());
        compound.putFloat("CoatLength", this.getCoatLength());

        compound.putInt("Lactation", this.lactationTimer);

        compound.putInt("milk", getMilkAmount());

    }

    /**
     * (abstract) Protected helper method to read subclass entity assets from NBT.
     */
    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);

        this.currentCoatLength = compound.getInt("CoatLength");
        this.setCoatLength(this.currentCoatLength);

        this.setFleeceDyeColour(DyeColor.byId(compound.getByte("Colour")));

        float milkBagSize = this.getMilkAmount() / (6*this.maxBagSize);
        this.setBagSize((milkBagSize*(this.maxBagSize/3.0F))+(this.maxBagSize*2.0F/3.0F));

        setMaxCoatLength();
//        configureAI();
        if (!compound.getString("breed").isEmpty()) {
            this.currentCoatLength = this.maxCoatLength;
            this.setCoatLength(this.currentCoatLength);
        }
    }

    protected void initilizeAnimalSize() {
        setSheepSize();
    }

    @Nullable
    @Override
    public ILivingEntityData onInitialSpawn(IServerWorld inWorld, DifficultyInstance difficulty, SpawnReason spawnReason, @Nullable ILivingEntityData livingdata, @Nullable CompoundNBT itemNbt) {
        return commonInitialSpawnSetup(inWorld, livingdata, getAdultAge(), 60000, 80000);
    }

    @Override
    protected void setInitialDefaults() {
        super.setInitialDefaults();
        this.setMaxCoatLength();
        this.currentCoatLength = (int)(this.maxCoatLength*(((float)this.getAge()/(float)this.getAdultAge())));
        this.setCoatLength(this.currentCoatLength);

        //"White" is considered no dye
        this.setFleeceDyeColour(DyeColor.WHITE);
    }

    private void setMaxCoatLength() {
        int[] genes = this.genetics.getAutosomalGenes();
        int maxCoatLength = 0;

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

        this.maxCoatLength = maxCoatLength;

    }

    @Override
    protected Genes createInitialGenes(IWorld world, BlockPos pos, boolean isDomestic) {
        return new SheepGeneticsInitialiser().generateNewGenetics(world, pos, isDomestic);
    }

    @Override
    protected Genes createInitialBreedGenes(IWorld world, BlockPos pos, String breed) {
        return new SheepGeneticsInitialiser().generateWithBreed(world, pos, breed);
    }

//    private void configureAI() {
//        if (!aiConfigured) {
//            Double speed = 1.0D;
//
//            this.goalSelector.addGoal(1, new PanicGoal(this, speed*1.25D));
//            this.goalSelector.addGoal(2, new BreedGoal(this, speed));
//            this.goalSelector.addGoal(3, new TemptGoal(this, speed*1.1D, TEMPTATION_ITEMS, false));
//            this.goalSelector.addGoal(4, new FollowParentGoal(this, speed*1.25D));
//            this.goalSelector.addGoal(4, new EnhancedAINurseFromMotherGoal(this, motherUUID, speed*1.1D));
//            grazingGoal = new EnhancedWaterAvoidingRandomWalkingEatingGoal(this, speed, 12, 0.001F, 120, 2);
//            this.goalSelector.addGoal(6, grazingGoal);
//        }
//        aiConfigured = true;
//    }
}
