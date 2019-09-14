package mokiyoki.enhancedanimals.entity;

import com.google.common.collect.Maps;
import mokiyoki.enhancedanimals.ai.general.EnhancedGrassGoal;
import mokiyoki.enhancedanimals.ai.general.EnhancedWaterAvoidingRandomWalkingEatingGoal;
import mokiyoki.enhancedanimals.ai.general.EnhancedWaterAvoidingRandomWalkingGoal;
import mokiyoki.enhancedanimals.ai.general.cow.EnhancedAINurseFromMotherGoal;
import mokiyoki.enhancedanimals.init.ModBlocks;
import mokiyoki.enhancedanimals.init.ModItems;
import mokiyoki.enhancedanimals.items.DebugGenesBook;
import mokiyoki.enhancedanimals.util.handlers.ConfigHandler;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.Pose;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.BreedGoal;
import net.minecraft.entity.ai.goal.FollowParentGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.PanicGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.TemptGoal;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.DyeColor;
import net.minecraft.item.DyeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.stats.Stats;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.storage.loot.LootTables;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import static mokiyoki.enhancedanimals.util.handlers.RegistryHandler.ENHANCED_SHEEP;

public class EnhancedSheep extends AnimalEntity implements net.minecraftforge.common.IShearable, EnhancedAnimal{

    //avalible UUID spaces : [ S X X 3 4 5 6 7 - 8 9 10 11 - 12 13 14 15 - 16 17 18 19 - 20 21 22 23 24 25 26 27 28 29 30 31 ]

    private static final DataParameter<Integer> COAT_LENGTH = EntityDataManager.createKey(EnhancedSheep.class, DataSerializers.VARINT);
    private static final DataParameter<String> SHARED_GENES = EntityDataManager.<String>createKey(EnhancedSheep.class, DataSerializers.STRING);
    private static final DataParameter<Byte> DYE_COLOUR = EntityDataManager.<Byte>createKey(EnhancedSheep.class, DataSerializers.BYTE);
    private static final DataParameter<String> SHEEP_STATUS = EntityDataManager.createKey(EnhancedSheep.class, DataSerializers.STRING);

    private static final String[] SHEEP_TEXTURES_UNDER = new String[] {
            "c_solid_tan.png", "c_solid_black.png", "c_solid_choc", "c_solid_lighttan.png",
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

    private static final Ingredient TEMPTATION_ITEMS = Ingredient.fromItems(Blocks.MELON, Blocks.PUMPKIN, Blocks.GRASS, Blocks.TALL_GRASS, Items.VINE, Blocks.HAY_BLOCK, Items.CARROT, Items.WHEAT, Items.ROSE_BUSH, Items.DANDELION, Items.SUGAR, Items.APPLE, ModBlocks.UnboundHay_Block);
    private static final Ingredient BREED_ITEMS = Ingredient.fromItems(Blocks.HAY_BLOCK, Items.WHEAT);

    private static final int WTC = 90;
    private final List<String> sheepTextures = new ArrayList<>();
    private final List<String> sheepFleeceTextures = new ArrayList<>();
    private static final int GENES_LENGTH = 54;
    private int[] genes = new int[GENES_LENGTH];
    private int[] mateGenes = new int[GENES_LENGTH];
    private int[] mitosisGenes = new int[GENES_LENGTH];
    private int[] mateMitosisGenes = new int[GENES_LENGTH];

    private int maxCoatLength;
    private int currentCoatLength;
    private int timeForGrowth = 0;

    private int hunger = 0;

//    protected boolean aiConfigured = false;
//    private String motherUUID = "";

    private int gestationTimer = 0;
    private boolean pregnant = false;

    public EnhancedSheep(EntityType<? extends EnhancedSheep> entityType, World worldIn) {
        super(entityType, worldIn);
//        this.setSize(0.4F, 1F);

    }

//    private int sheepTimer;
//    private EnhancedWaterAvoidingRandomWalkingEatingGoal wanderEatingGoal;

    /** Map from EnumDyeColor to RGB values for passage to GlStateManager.color() */
    private static final Map<DyeColor, float[]> DYE_TO_RGB = Maps.newEnumMap(Arrays.stream(DyeColor.values()).collect(Collectors.toMap((DyeColor p_200204_0_) -> {
        return p_200204_0_;
    }, EnhancedSheep::createSheepDyeColor)));

    private static float[] createSheepDyeColor(DyeColor enumDyeColour) {
        if (enumDyeColour == DyeColor.WHITE) {
//            return new float[]{0.9019608F, 0.9019608F, 0.9019608F};
            return new float[]{1.0F, 1.0F, 1.0F};
        } else {
            float[] afloat = enumDyeColour.getColorComponentValues();
            float f = 0.75F;
            return new float[]{afloat[0] * f, afloat[1] * f, afloat[2] * f};
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static float[] getDyeRgb(DyeColor dyeColour) {
        return DYE_TO_RGB.get(dyeColour);
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

    private int sheepTimer;
    private EnhancedGrassGoal eatGrassGoal;

    @Override
    protected void registerGoals() {
        //Todo add the temperamants
        this.eatGrassGoal = new EnhancedGrassGoal(this, null);
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 1.25D));
        this.goalSelector.addGoal(2, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.1D, TEMPTATION_ITEMS, false));
        this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.1D));
        this.goalSelector.addGoal(5, this.eatGrassGoal);
        this.goalSelector.addGoal(6, new EnhancedWaterAvoidingRandomWalkingGoal(this, 1.0D));
        this.goalSelector.addGoal(7, new LookAtGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.addGoal(8, new LookRandomlyGoal(this));
    }

    //TODO put new sheep behaviour here

    protected void updateAITasks()
    {
//        this.sheepTimer = this.wanderEatingGoal.getEatingGrassTimer();
        this.sheepTimer = this.eatGrassGoal.getEatingGrassTimer();
        super.updateAITasks();
    }

    protected void registerData() {
        super.registerData();
        this.dataManager.register(SHARED_GENES, new String());
        this.dataManager.register(COAT_LENGTH, 0);
        this.dataManager.register(DYE_COLOUR, Byte.valueOf((byte)0));
        this.dataManager.register(SHEEP_STATUS, new String());
    }

    protected void setSheepStatus(String status) {
        this.dataManager.set(SHEEP_STATUS, status);
    }

    public String getSheepStatus() {
        return this.dataManager.get(SHEEP_STATUS);
    }

    @Nullable
    protected ResourceLocation getLootTable() {
        if (this.getSheared()) {
            return this.getType().getLootTable();
        } else {
            if (genes[4] == 1 || genes[5] == 1 || ((genes[0] >= 3 && genes[1] >= 3) && (genes[0] != 5 && genes[1] != 5))){
                if ((genes[2] == 1 || genes[3] == 1) && (genes[0] != 3 && genes[1] != 3)){
                    return LootTables.ENTITIES_SHEEP_BLACK;
                }else{
                    return LootTables.ENTITIES_SHEEP_BROWN;
                }
            }else if ((genes[0] == 2 || genes[1] == 2) && getFleeceDyeColour() == DyeColor.WHITE) {
                return LootTables.ENTITIES_SHEEP_LIGHT_GRAY;
            }else {
                switch (this.getFleeceDyeColour()) {
                    case WHITE:
                    default:
                        return LootTables.ENTITIES_SHEEP_WHITE;
                    case ORANGE:
                        return LootTables.ENTITIES_SHEEP_ORANGE;
                    case MAGENTA:
                        return LootTables.ENTITIES_SHEEP_MAGENTA;
                    case LIGHT_BLUE:
                        return LootTables.ENTITIES_SHEEP_LIGHT_BLUE;
                    case YELLOW:
                        return LootTables.ENTITIES_SHEEP_YELLOW;
                    case LIME:
                        return LootTables.ENTITIES_SHEEP_LIME;
                    case PINK:
                        return LootTables.ENTITIES_SHEEP_PINK;
                    case GRAY:
                        return LootTables.ENTITIES_SHEEP_GRAY;
                    case LIGHT_GRAY:
                        return LootTables.ENTITIES_SHEEP_LIGHT_GRAY;
                    case CYAN:
                        return LootTables.ENTITIES_SHEEP_CYAN;
                    case PURPLE:
                        return LootTables.ENTITIES_SHEEP_PURPLE;
                    case BLUE:
                        return LootTables.ENTITIES_SHEEP_BLUE;
                    case BROWN:
                        return LootTables.ENTITIES_SHEEP_BROWN;
                    case GREEN:
                        return LootTables.ENTITIES_SHEEP_GREEN;
                    case RED:
                        return LootTables.ENTITIES_SHEEP_RED;
                    case BLACK:
                        return LootTables.ENTITIES_SHEEP_BLACK;
                }
            }
        }
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

    private void setEntityStatus(String status) {
        this.dataManager.set(SHEEP_STATUS, status);
    }

    public String getEntityStatus() {
        return this.dataManager.get(SHEEP_STATUS);
    }

    public int getHunger(){
        return hunger;
    }

    public void decreaseHunger(int decrease) {
        if (this.hunger - decrease < 0) {
            this.hunger = 0;
        } else {
            this.hunger = this.hunger - decrease;
        }
    }

    public void livingTick() {
        super.livingTick();

        if (this.world.isRemote) {
            this.sheepTimer = Math.max(0, this.sheepTimer - 1);
        }

        if (!this.world.isRemote) {
            if (hunger <= 72000) {
                hunger++;
            }
            if (hunger <= 36000) {
                timeForGrowth++;
            }
            if (maxCoatLength > 0 && (timeForGrowth >= (24000 / maxCoatLength))) {
                timeForGrowth = 0;
                if (maxCoatLength > currentCoatLength) {
                    currentCoatLength++;
                    setCoatLength(currentCoatLength);
                }
            }

            if(pregnant) {

                gestationTimer++;
                int days = ConfigHandler.COMMON.gestationDays.get();
                if (days/2 < gestationTimer) {
                    setSheepStatus(EntityState.PREGNANT.toString());
                }
                if (hunger > days*(0.75) && days !=0) {
                    pregnant = false;
                    setSheepStatus(EntityState.ADULT.toString());
                }
                if (gestationTimer >= days) {
                    pregnant = false;
                    gestationTimer = 0;
                    setSheepStatus(EntityState.MOTHER.toString());
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

                    for (int i = 0; i <= numberOfLambs; i++) {
                        EnhancedSheep enhancedsheep = ENHANCED_SHEEP.create(this.world);
                        int[] babyGenes = getLambGenes();
                        enhancedsheep.setGenes(babyGenes);
                        enhancedsheep.setSharedGenes(babyGenes);
                        enhancedsheep.setMaxCoatLength();
                        enhancedsheep.currentCoatLength = enhancedsheep.maxCoatLength;
                        enhancedsheep.setCoatLength(enhancedsheep.currentCoatLength);
                        enhancedsheep.setGrowingAge(-24000);
                        enhancedsheep.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0F);
//                        enhancedsheep.setMotherUUID(this.getUniqueID().toString());
                        this.world.addEntity(enhancedsheep);
                    }
                }
            }

            if (this.isChild()) {
                if (getEntityStatus().equals(EntityState.CHILD_STAGE_ONE.toString()) && this.getGrowingAge() < -16000) {
                    if(hunger < 5000) {
                        setEntityStatus(EntityState.CHILD_STAGE_TWO.toString());
                    } else {
                        this.setGrowingAge(-16500);
                    }
                } else if (getEntityStatus().equals(EntityState.CHILD_STAGE_TWO.toString()) && this.getGrowingAge() < -8000) {
                    if(hunger < 5000) {
                        setEntityStatus(EntityState.CHILD_STAGE_THREE.toString());
                    } else {
                        this.setGrowingAge(-8500);
                    }
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

    protected void playStepSound(BlockPos pos, BlockState blockIn) {
        this.playSound(SoundEvents.ENTITY_SHEEP_STEP, 0.15F, 1.0F);
    }

    public void eatGrassBonus() {
        this.setSheared(false);
        if (!this.isChild() && (maxCoatLength > currentCoatLength)){
            this.currentCoatLength ++ ;
            setCoatLength(currentCoatLength);
        }

    }

    @Override
    public boolean isShearable(ItemStack item, net.minecraft.world.IWorldReader world, BlockPos pos) {
        if (!this.world.isRemote && currentCoatLength >=1) {
            return true;
        }
        return false;
    }

    @Override
    public java.util.List<ItemStack> onSheared(ItemStack item, net.minecraft.world.IWorld world, BlockPos pos, int fortune) {
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

                int spots = 0;

                if (genes[8] == 2 && genes[9] == 2){
                    spots = this.rand.nextInt(3);
                }

                if (genes[4] == 1 || genes[5] == 1 || ((genes[0] >= 4 && genes[1] >= 4) && (genes[0] != 5 && genes[1] != 5)) && spots != 2) {
                    if ((genes[2] == 1 || genes[3] == 1) && (genes[0] != 3 && genes[1] != 3)) {
                        ret.add(new ItemStack(Blocks.BLACK_WOOL));
                    } else {
                        ret.add(new ItemStack(Blocks.BROWN_WOOL));
                    }
                }else if ((genes[0] == 2 || genes[1] == 2) && getFleeceDyeColour() == DyeColor.WHITE) {
                        ret.add(new ItemStack(Blocks.LIGHT_GRAY_WOOL));
                }else {
                    switch (this.getFleeceDyeColour()) {
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

        }
        currentCoatLength = 0;
        setCoatLength(currentCoatLength);
        return ret;
    }

//    public void setMotherUUID(String motherUUID) {
//        this.motherUUID = motherUUID;
//    }
//
//    public String getMotherUUID() {
//        return this.motherUUID;
//    }

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

    public boolean isBreedingItem(ItemStack stack) {
        //TODO set this to a separate item or type of item for force breeding
        return BREED_ITEMS.test(stack);
    }

    public AgeableEntity createChild(AgeableEntity ageable) {
        if(pregnant) {
            ((EnhancedSheep)ageable).pregnant = true;
            ((EnhancedSheep)ageable).setMateGenes(this.genes);
            ((EnhancedSheep)ageable).mixMateMitosisGenes();
            ((EnhancedSheep)ageable).mixMitosisGenes();
        } else {
            pregnant = true;
            this.mateGenes = ((EnhancedSheep) ageable).getGenes();
            mixMateMitosisGenes();
            mixMitosisGenes();
        }

        //TODO figure out whats wrong with pregnancy
            this.setGrowingAge(10);
            this.resetInLove();
            ageable.setGrowingAge(10);
            ((EnhancedSheep) ageable).resetInLove();

        ServerPlayerEntity entityplayermp = this.getLoveCause();
        if (entityplayermp == null && ((EnhancedSheep)ageable).getLoveCause() != null) {
            entityplayermp = ((EnhancedSheep)ageable).getLoveCause();
        }

        if (entityplayermp != null) {
            entityplayermp.addStat(Stats.ANIMALS_BRED);
            CriteriaTriggers.BRED_ANIMALS.trigger(entityplayermp, this, ((EnhancedSheep)ageable), (AgeableEntity)null);
        }

        return null;
    }

    public boolean getSheared()
    {
        return (this.dataManager.get(DYE_COLOUR) & 16) != 0;
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
                pattern = pattern + 1;
            }

            //basic spots
            if (genesForText[8] == 2 && genesForText[9] == 2){
                if (Character.isDigit(uuidArry[1])){
                    spots = 2;
                }else {
                    spots = 1;
                }
            }


         this.sheepTextures.add(SHEEP_TEXTURES_UNDER[under]);
         if (pattern != 0) {
             this.sheepTextures.add(SHEEP_TEXTURES_PATTERN[pattern]);
         }
         if (grey != 0) {
             this.sheepTextures.add(SHEEP_TEXTURES_GREY[grey]);
         }
            if (spots != 0) {
                this.sheepTextures.add(SHEEP_TEXTURES_SPOTS[spots]);
            }
         this.sheepTextures.add(SHEEP_TEXTURES_SKIN[skin]);
         this.sheepTextures.add(SHEEP_TEXTURES_HOOVES[hooves]);
         this.sheepTextures.add(SHEEP_TEXTURES_FUR[fur]);
         this.sheepTextures.add(SHEEP_TEXTURES_EYES[eyes]);


        }
    }


//TODO ask Saemon if this is needed any more
    @OnlyIn(Dist.CLIENT)
    public String getSheepFleeceTexture() {
        if (this.sheepFleeceTextures.isEmpty()) {
            this.setFleeceTexturePaths();
        }
        return this.sheepFleeceTextures.stream().collect(Collectors.joining(", ","[","]"));

    }

    @OnlyIn(Dist.CLIENT)
    public String[] getVariantFleeceTexturePaths() {
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
    public void handleStatusUpdate(byte id) {
        if (id == 10) {
            this.sheepTimer = 40;
        } else {
            super.handleStatusUpdate(id);
        }
    }


    //Eating Animation
    @OnlyIn(Dist.CLIENT)
    public float getHeadRotationPointY(float partialTickTime) {
        if (this.sheepTimer <= 0)
        {
            return 0.0F;
        }
        else if (this.sheepTimer >= 4 && this.sheepTimer <= 36)
        {
            return 0.2F;
        }
        else
        {
            return this.sheepTimer < 4 ? ((float)this.sheepTimer - partialTickTime) / 4.0F : -((float)(this.sheepTimer - 40) - partialTickTime) / 4.0F;
        }
    }
    //Eating Animation
    @OnlyIn(Dist.CLIENT)
    public float getHeadRotationAngleX(float partialTickTime) {
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


    @Override
    public boolean processInteract(PlayerEntity entityPlayer, Hand hand) {
        ItemStack itemStack = entityPlayer.getHeldItem(hand);
        Item item = itemStack.getItem();
        if (!this.world.isRemote) {
            if (item == Items.WATER_BUCKET) {
                this.setFleeceDyeColour(DyeColor.WHITE);
            } else if (item instanceof DyeItem) {
                DyeColor enumdyecolor = ((DyeItem)item).getDyeColor();
                if (enumdyecolor != this.getFleeceDyeColour()) {
                    this.setFleeceDyeColour(enumdyecolor);
                    if (!entityPlayer.abilities.isCreativeMode) {
                        itemStack.shrink(1);
                    }
                }
            } else if (item == Items.GLASS_BOTTLE && !entityPlayer.abilities.isCreativeMode && !this.isChild() && getSheepStatus().equals(EntityState.MOTHER.toString())) {
                entityPlayer.playSound(SoundEvents.ENTITY_COW_MILK, 1.0F, 1.0F);
                itemStack.shrink(1);
                if (itemStack.isEmpty()) {
                    entityPlayer.setHeldItem(hand, new ItemStack(ModItems.Milk_Bottle));
                } else if (!entityPlayer.inventory.addItemStackToInventory(new ItemStack(ModItems.Milk_Bottle))) {
                    entityPlayer.dropItem(new ItemStack(ModItems.Milk_Bottle), false);
                }
            } else if (item == Items.BUCKET && !entityPlayer.abilities.isCreativeMode && !this.isChild() && getSheepStatus().equals(EntityState.MOTHER.toString())) {
                entityPlayer.playSound(SoundEvents.ENTITY_COW_MILK, 1.0F, 1.0F);
                itemStack.shrink(1);
                if (itemStack.isEmpty()) {
                    entityPlayer.setHeldItem(hand, new ItemStack(ModItems.OneThird_Milk_Bucket));
                } else if (!entityPlayer.inventory.addItemStackToInventory(new ItemStack(ModItems.OneThird_Milk_Bucket))) {
                    entityPlayer.dropItem(new ItemStack(ModItems.OneThird_Milk_Bucket), false);
                }
            } else if (item == ModItems.OneThird_Milk_Bucket && !entityPlayer.abilities.isCreativeMode && !this.isChild() && getSheepStatus().equals(EntityState.MOTHER.toString())) {
                entityPlayer.playSound(SoundEvents.ENTITY_COW_MILK, 1.0F, 1.0F);
                itemStack.shrink(1);
                if (itemStack.isEmpty()) {
                    entityPlayer.setHeldItem(hand, new ItemStack(ModItems.TwoThirds_Milk_Bucket));
                } else if (!entityPlayer.inventory.addItemStackToInventory(new ItemStack(ModItems.TwoThirds_Milk_Bucket))) {
                    entityPlayer.dropItem(new ItemStack(ModItems.TwoThirds_Milk_Bucket), false);
                }
            } else if (item == ModItems.TwoThirds_Milk_Bucket && !entityPlayer.abilities.isCreativeMode && !this.isChild() && getSheepStatus().equals(EntityState.MOTHER.toString())) {
                entityPlayer.playSound(SoundEvents.ENTITY_COW_MILK, 1.0F, 1.0F);
                itemStack.shrink(1);
                if (itemStack.isEmpty()) {
                    entityPlayer.setHeldItem(hand, new ItemStack(Items.MILK_BUCKET));
                } else if (!entityPlayer.inventory.addItemStackToInventory(new ItemStack(Items.MILK_BUCKET))) {
                    entityPlayer.dropItem(new ItemStack(Items.MILK_BUCKET), false);
                }
            } else if (item instanceof DebugGenesBook) {
                Minecraft.getInstance().keyboardListener.setClipboardString(this.dataManager.get(SHARED_GENES));
            } else if (TEMPTATION_ITEMS.test(itemStack)) {
                decreaseHunger(6000);
                itemStack.shrink(1);
            }
        }
        return super.processInteract(entityPlayer, hand);
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

        //store this sheeps's genes
        ListNBT geneList = new ListNBT();
        for (int i = 0; i < genes.length; i++) {
            CompoundNBT nbttagcompound = new CompoundNBT();
            nbttagcompound.putInt("Gene", genes[i]);
            geneList.add(nbttagcompound);
        }
        compound.put("Genes", geneList);

        //store this sheeps's mate's genes
        ListNBT mateGeneList = new ListNBT();
        for (int i = 0; i < mateGenes.length; i++) {
            CompoundNBT nbttagcompound = new CompoundNBT();
            nbttagcompound.putInt("Gene", mateGenes[i]);
            mateGeneList.add(nbttagcompound);
        }
        compound.put("FatherGenes", mateGeneList);
        compound.putFloat("CoatLength", this.getCoatLength());

        compound.putBoolean("Pregnant", this.pregnant);
        compound.putInt("Gestation", this.gestationTimer);

        compound.putString("Status", getEntityStatus());
        compound.putInt("Hunger", hunger);

//        compound.putString("MotherUUID", this.motherUUID);

    }

    /**
     * (abstract) Protected helper method to read subclass entity assets from NBT.
     */
    public void readAdditional(CompoundNBT compound) {

        currentCoatLength = compound.getInt("CoatLength");
        this.setCoatLength(currentCoatLength);

        super.readAdditional(compound);

        this.setFleeceDyeColour(DyeColor.byId(compound.getByte("Colour")));

        ListNBT geneList = compound.getList("Genes", 10);
        for (int i = 0; i < geneList.size(); ++i) {
            CompoundNBT nbttagcompound = geneList.getCompound(i);
            int gene = nbttagcompound.getInt("Gene");
            genes[i] = gene;
        }

        ListNBT mateGeneList = compound.getList("FatherGenes", 10);
        for (int i = 0; i < mateGeneList.size(); ++i) {
            CompoundNBT nbttagcompound = mateGeneList.getCompound(i);
            int gene = nbttagcompound.getInt("Gene");
            mateGenes[i] = gene;
        }

        this.pregnant = compound.getBoolean("Pregnant");
        this.gestationTimer = compound.getInt("Gestation");

        setEntityStatus(compound.getString("Status"));
        hunger = compound.getInt("Hunger");

//        this.motherUUID = compound.getString("MotherUUID");

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

        //resets the max so we don't have to store it
        setMaxCoatLength();
//        configureAI();

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
    public ILivingEntityData onInitialSpawn(IWorld inWorld, DifficultyInstance difficulty, SpawnReason spawnReason, @Nullable ILivingEntityData livingdata, @Nullable CompoundNBT itemNbt) {
        livingdata = super.onInitialSpawn(inWorld, difficulty, spawnReason, livingdata, itemNbt);
        int[] spawnGenes;

        if (livingdata instanceof GroupData) {
            spawnGenes = ((GroupData) livingdata).groupGenes;
        } else {
            spawnGenes = createInitialGenes(inWorld);
            livingdata = new GroupData(spawnGenes);
        }

        this.genes = spawnGenes;
        setSharedGenes(genes);
        setMaxCoatLength();
        this.currentCoatLength = this.maxCoatLength;
        setCoatLength(this.currentCoatLength);

        //"White" is considered no dye
        this.setFleeceDyeColour(DyeColor.WHITE);

//        configureAI();
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

    private int[] createInitialGenes(IWorld inWorld) {
        int[] initialGenes = new int[GENES_LENGTH];
        //TODO create biome WTC variable [hot and dry biomes, hot and wet biomes, cold biomes] WTC is all others


        //[ 0=minecraft wildtype, 1=jungle wildtype, 2=savanna wildtype, 3=cold wildtype, 4=swamp wildtype ]
        int wildType = 0;
        Biome biome = inWorld.getBiome(new BlockPos(this));

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
        if (ThreadLocalRandom.current().nextInt(100) > WTC/2) {
            initialGenes[28] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[28] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC/2) {
            initialGenes[29] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[29] = (1);
        }

        //added wool length 6 [ wildtype, wool3 ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC/2) {
            initialGenes[30] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[30] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC/2) {
            initialGenes[31] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[31] = (1);
        }

        //added wool length 7 [ wildtype, wool3 ]
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

        //added wool length 8 [ wildtype, wool3 ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[34] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[34] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[35] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[35] = (1);
        }

        //multi-horned gene [multi-horn, wildtype+]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[36] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[36] = (2);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[37] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[37] = (2);
        }

        //wool growth area extension [extended, wildtype+, limiter]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[38] = (ThreadLocalRandom.current().nextInt(3) + 1);

        } else {
            initialGenes[38] = (2);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[39] = (ThreadLocalRandom.current().nextInt(3) + 1);

        } else {
            initialGenes[39] = (2);
        }

        //wool growth area extension [extended, wildtype+]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[40] = (ThreadLocalRandom.current().nextInt(3) + 1);

        } else {
            initialGenes[40] = (2);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[41] = (ThreadLocalRandom.current().nextInt(3) + 1);

        } else {
            initialGenes[41] = (2);
        }

        //allows wool surrounding face [face wool, wildtype+]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[42] = (ThreadLocalRandom.current().nextInt(3) + 1);

        } else {
            initialGenes[42] = (2);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[43] = (ThreadLocalRandom.current().nextInt(3) + 1);

        } else {
            initialGenes[43] = (2);
        }

        //fertility modifier [ -1, 0, +1]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[44] = (ThreadLocalRandom.current().nextInt(3) + 1);

        } else {
            initialGenes[44] = (2);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[45] = (ThreadLocalRandom.current().nextInt(3) + 1);

        } else {
            initialGenes[45] = (2);
        }

        //Shedding/Rooing Sheep[ Shedding, non shedding]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[46] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[46] = (2);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[47] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[47] = (2);
        }

        //White Shading 1 [ Shaded+, non shaded]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[48] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[48] = (2);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[49] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[49] = (2);
        }

        //White Shading 2 [ Shaded+, non shaded]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[50] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[50] = (2);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[51] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[51] = (2);
        }

        //White Shading Enhancer [ Shaded+, non shaded]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[52] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[52] = (2);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[53] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[53] = (2);
        }

        return initialGenes;
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
//            wanderEatingGoal = new EnhancedWaterAvoidingRandomWalkingEatingGoal(this, speed, 12, 0.001F, 120, 2);
//            this.goalSelector.addGoal(6, wanderEatingGoal);
//        }
//        aiConfigured = true;
//    }

    public void setGenes(int[] genes) {
        this.genes = genes;
    }

    public int[] getGenes() {
        return this.genes;
    }

    public void setMateGenes(int[] mateGenes){
        this.mateGenes = mateGenes;
    }

    public static class GroupData implements ILivingEntityData {

        public int[] groupGenes;

        public GroupData(int[] groupGenes) {
            this.groupGenes = groupGenes;
        }

    }
}
