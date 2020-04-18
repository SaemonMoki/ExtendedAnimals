package mokiyoki.enhancedanimals.entity;

import mokiyoki.enhancedanimals.ai.general.EnhancedTemptGoal;
import mokiyoki.enhancedanimals.ai.general.EnhancedWaterAvoidingRandomWalkingEatingGoal;
import mokiyoki.enhancedanimals.ai.general.EnhancedWaterAvoidingRandomWalkingGoal;
import mokiyoki.enhancedanimals.init.ModItems;
import mokiyoki.enhancedanimals.items.DebugGenesBook;
import mokiyoki.enhancedanimals.util.handlers.ConfigHandler;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.BreedGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.TemptGoal;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.AirItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.Path;
import net.minecraft.stats.Stats;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import static mokiyoki.enhancedanimals.util.handlers.EventRegistry.ENHANCED_CAT;

public class EnhancedCat extends AnimalEntity implements EnhancedAnimal {

    private static final DataParameter<String> SHARED_GENES = EntityDataManager.<String>createKey(EnhancedCat.class, DataSerializers.STRING);
    protected static final DataParameter<String> CAT_STATUS = EntityDataManager.createKey(EnhancedCat.class, DataSerializers.STRING);
    protected static final DataParameter<Boolean> SLEEPING = EntityDataManager.createKey(EnhancedCat.class, DataSerializers.BOOLEAN);
    protected static final DataParameter<Boolean> HEMIZYGOTE = EntityDataManager.createKey(EnhancedCat.class, DataSerializers.BOOLEAN);

    private static final String[] CAT_SKIN = new String[] {
            "" //this is for the base skin colour it should be solid, also make darker versions here for colourpoint
    };

    private static final String[] CAT_SKINPATTERN = new String[] {
            "" //this is for anything that causes darker markings on the skin
    };

    private static final String[] CAT_SKINCOLOURPOINT = new String[] {
            "" //this is for the area that skin is lightened by colourpoint
    };

    private static final String[] CAT_SKINSPOTS = new String[] {
            "" //this is for the pink spots you might see on a cat's skin, you'll need one for each gene that causes pink spots on the skin (white spots on the fur)
    };

    private static final String[] CAT_TOESPOTS = new String[] {
            "" //this is for the pink spots you see on toes to help diversify the possible spots (optional)
    };

    private static final String[] CAT_NOSESPOTS = new String[] {
            "" //this is for the pink spots you see on nose to help diversify the possible spots (optional)
    };

    private static final String[] CAT_UNDERCOAT = new String[] {
            "" //this is for the undercoat to help give the colouring more depth
    };

    private static final String[] CAT_BACKGROUND = new String[] {
            "" //this is for the body colour under the stripes, spots and ticking
    };

    private static final String[] CAT_PATTERN = new String[] {
            "" //cat stripes, leopard spots, ticking and other patterns that come in black, this will need a second copy for orange pattern
    };

    private static final String[] CAT_COLOURPOINT = new String[] {
            "" //this is for the area that coat is lightened by colourpoint
    };

    private static final String[] CAT_SPOTS = new String[] {
            //this is for the white spots you might see on a cat's fur, you'll need one for each gene that causes white spots, tux and locket markings go here
            "" //dominant white, white spots, and birman belong here, we can break these up into different body regions
    };

    private static final String[] CAT_SPOT_ROAN = new String[] {
            "" //for various levels and randomizers for roan cats
    };

    private static final String[] CAT_SPOT_KARPATI = new String[] {
            "" //for various levels and randomizers for karpati "reverse colour-point" cats
    };

    private static final String[] CAT_SPOT_SMALL = new String[] {
            "" //for small markings and toe markings that are thought to be unrelated to the white spotting gene
    };

    private static final String[] CAT_EYEL = new String[] {
            "" //this is for a cat's eye shape
    };

    private static final String[] CAT_EYER = new String[] {
            "" //this is for a cat's eye shape
    };

    private static final String[] CAT_TORTIE = new String[] {
            //lots of calico and tortieshell spot distributions
    };

    private static final String[] CAT_COATTYPE = new String[] {
            //fur coverage patterns go here like sphynx, lykoi, and minskin. great place to detail having thinner fur in the ears
    };

    private static final Ingredient TEMPTATION_ITEMS = Ingredient.fromItems(Items.CHICKEN, Items.BEEF, Items.MUTTON, Items.RABBIT, Items.TROPICAL_FISH, Items.SALMON, Items.COD);
    private static final Ingredient MILK_ITEMS = Ingredient.fromItems(ModItems.Milk_Bottle, ModItems.Half_Milk_Bottle);
    private static final Ingredient BREED_ITEMS = Ingredient.fromItems(Items.TROPICAL_FISH, Items.SALMON, Items.COD);

    Map<Item, Integer> foodWeightMap = new HashMap() {{
//        put(new ItemStack(Items.TALL_GRASS).getItem(), 6000);
//        put(new ItemStack(Items.GRASS).getItem(), 3000);
//        put(new ItemStack(Items.CARROT).getItem(), 3000);
//        put(new ItemStack(Items.GOLDEN_CARROT).getItem(), 12000);
//        put(new ItemStack(Items.SWEET_BERRIES).getItem(), 1500);
//        put(new ItemStack(Items.DANDELION).getItem(), 1500);
//        put(new ItemStack(Items.ROSE_BUSH).getItem(), 1500);
    }};

    private final List<String> catTextures = new ArrayList<>();
    private final List<String> catAlphaTextures = new ArrayList<>();

    private int maxCoatLength;
    private int currentCoatLength;
    private int timeForGrowth = 0;

    private int gestationTimer = 0;
    private boolean pregnant = false;

    private float[] catColouration = null;
    private int hunger = 0;
    private int healTicks = 0;
    protected String motherUUID = "";
    protected Boolean sleeping;
    protected Boolean hemizygote = false;
    protected int awokenTimer = 0;

    private static final int WTC = ConfigHandler.COMMON.wildTypeChance.get();
    private static final int GENES_LENGTH = 60;
    private int[] genes = new int[GENES_LENGTH];
    private int[] mateGenes = new int[GENES_LENGTH];
    private int[] mitosisGenes = new int[GENES_LENGTH];
    private int[] mateMitosisGenes = new int[GENES_LENGTH];

    private EnhancedWaterAvoidingRandomWalkingEatingGoal wanderEatingGoal;

    public EnhancedCat(EntityType<? extends EnhancedCat> entityType, World worldIn) {
        super(ENHANCED_CAT, worldIn);
//        this.setSize(0.4F, 0.5F);
    }

    @Override
    protected void registerGoals() {
        this.wanderEatingGoal = new EnhancedWaterAvoidingRandomWalkingEatingGoal(this, 1.0D, 7, 0.001F, 120, 2, 100);
        this.goalSelector.addGoal(1, new SwimGoal(this));
        this.goalSelector.addGoal(2, new BreedGoal(this, 0.8D));
        this.goalSelector.addGoal(3, new EnhancedTemptGoal(this, 1.0D, false, TEMPTATION_ITEMS));
        this.goalSelector.addGoal(4, new EnhancedCat.AIAvoidEntity<>(this, PlayerEntity.class, 8.0F, 2.2D, 2.2D));
        this.goalSelector.addGoal(4, new EnhancedCat.AIAvoidEntity<>(this, WolfEntity.class, 10.0F, 2.2D, 2.2D));
        this.goalSelector.addGoal(4, new EnhancedCat.AIAvoidEntity<>(this, MonsterEntity.class, 4.0F, 2.2D, 2.2D));
        this.goalSelector.addGoal(5, this.wanderEatingGoal);
//        this.goalSelector.addGoal(6, new EnhancedWaterAvoidingRandomWalkingGoal(this, 0.6D));
        this.goalSelector.addGoal(7, new LookAtGoal(this, PlayerEntity.class, 10.0F));
        this.goalSelector.addGoal(8, new LookRandomlyGoal(this));


    }

    protected float getJumpUpwardsMotion() {
        if (!this.collidedHorizontally && (!this.moveController.isUpdating() || !(this.moveController.getY() > this.getPosY() + 0.5D))) {
            Path path = this.navigator.getPath();
            if (path != null && path.getCurrentPathIndex() < path.getCurrentPathLength()) {
                Vec3d vec3d = path.getPosition(this);
                if (vec3d.y > this.getPosY() + 0.5D) {
                    return 0.5F;
                }
            }

            return this.moveController.getSpeed() <= 0.6D ? 0.2F : 0.3F;
        } else {
            return 0.5F;
        }
    }

    public void setZygosity() {
        Random rand = new Random();
        boolean hemizygote = rand.nextBoolean();
        this.dataManager.set(HEMIZYGOTE, hemizygote);
    }

    public boolean getZygosity() {
        hemizygote = this.dataManager.get(HEMIZYGOTE);
        return hemizygote;
    }

    public void setSleeping(Boolean sleeping) {
        this.sleeping = sleeping;
        this.dataManager.set(SLEEPING, sleeping); }

    @Override
    public Boolean isAnimalSleeping() {
        if (this.sleeping == null) {
            return false;
        } else {
            sleeping = this.dataManager.get(SLEEPING);
            return sleeping;
        }
    }

    @Override
    public void awaken() {
        this.awokenTimer = 200;
        setSleeping(false);
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

    /**
     * Causes this entity to do an upwards motion (jumping).
     */
    protected void jump() {
        super.jump();
        double d0 = this.moveController.getSpeed();
        if (d0 > 0.0D) {
            double d1 = horizontalMag(this.getMotion());
            if (d1 < 0.01D) {
                this.moveRelative(0.1F, new Vec3d(0.0D, 0.0D, 1.0D));
            }
        }

        if (!this.world.isRemote) {
            this.world.setEntityState(this, (byte)1);
        }

    }

    protected void registerData() {
        super.registerData();
        this.dataManager.register(SHARED_GENES, new String());
//        this.dataManager.register(COAT_LENGTH, 0);
        this.dataManager.register(CAT_STATUS, new String());
    }

    protected void setCatStatus(String status) { this.dataManager.set(CAT_STATUS, status); }

    public String getCatStatus() { return this.dataManager.get(CAT_STATUS); }

//    private void setCoatLength(int coatLength) {
//        this.dataManager.set(COAT_LENGTH, coatLength);
//    }
//
//    public int getCoatLength() {
//        return this.dataManager.get(COAT_LENGTH);
//    }

    @Override
    public boolean processInteract(PlayerEntity entityPlayer, Hand hand) {
        ItemStack itemStack = entityPlayer.getHeldItem(hand);
        Item item = itemStack.getItem();

        if (!this.world.isRemote && !hand.equals(Hand.OFF_HAND)) {
            if (item instanceof AirItem) {
                ITextComponent message = getHungerText();
                entityPlayer.sendMessage(message);
                if (pregnant) {
                    message = getPregnantText();
                    entityPlayer.sendMessage(message);
                }
            } else if (item instanceof DebugGenesBook) {
                Minecraft.getInstance().keyboardListener.setClipboardString(this.dataManager.get(SHARED_GENES));
            } else if (!getCatStatus().equals(EntityState.CHILD_STAGE_ONE.toString()) && TEMPTATION_ITEMS.test(itemStack) && hunger >= 6000) {
                if (this.foodWeightMap.containsKey(item)) {
                    decreaseHunger(this.foodWeightMap.get(item));
                } else {
                    decreaseHunger(6000);
                }
                if (!entityPlayer.abilities.isCreativeMode) {
                    itemStack.shrink(1);
                }
            } else if (this.isChild() && MILK_ITEMS.test(itemStack) && hunger >= 6000) {

                if (!entityPlayer.abilities.isCreativeMode) {
                    if (item == ModItems.Half_Milk_Bottle) {
                        decreaseHunger(6000);
                        if (itemStack.isEmpty()) {
                            entityPlayer.setHeldItem(hand, new ItemStack(Items.GLASS_BOTTLE));
                        } else if (!entityPlayer.inventory.addItemStackToInventory(new ItemStack(Items.GLASS_BOTTLE))) {
                            entityPlayer.dropItem(new ItemStack(Items.GLASS_BOTTLE), false);
                        }
                    } else if (item == ModItems.Milk_Bottle) {
                        if (hunger >= 12000) {
                            decreaseHunger(12000);
                            if (itemStack.isEmpty()) {
                                entityPlayer.setHeldItem(hand, new ItemStack(Items.GLASS_BOTTLE));
                            } else if (!entityPlayer.inventory.addItemStackToInventory(new ItemStack(Items.GLASS_BOTTLE))) {
                                entityPlayer.dropItem(new ItemStack(Items.GLASS_BOTTLE), false);
                            }
                        } else {
                            decreaseHunger(6000);
                            if (itemStack.isEmpty()) {
                                entityPlayer.setHeldItem(hand, new ItemStack(ModItems.Half_Milk_Bottle));
                            } else if (!entityPlayer.inventory.addItemStackToInventory(new ItemStack(ModItems.Half_Milk_Bottle))) {
                                entityPlayer.dropItem(new ItemStack(ModItems.Half_Milk_Bottle), false);
                            }
                        }
                    }

                }
            }
        }
        return super.processInteract(entityPlayer, hand);
    }

    private ITextComponent getHungerText() {
        String hungerText = "";
        if (this.hunger < 1000) {
            hungerText = "eanimod.hunger.not_hungry";
        } else if (this.hunger < 4000) {
            hungerText = "eanimod.hunger.hungry";
        } else if (this.hunger < 9000) {
            hungerText = "eanimod.hunger.very_hunger";
        } else if (this.hunger < 16000) {
            hungerText = "eanimod.hunger.starving";
        } else if (this.hunger > 24000) {
            hungerText = "eanimod.hunger.dying";
        }
        return new TranslationTextComponent(hungerText);
    }

    private ITextComponent getPregnantText() {
        String pregnancyText;
        int days = ConfigHandler.COMMON.gestationDaysCat.get();
        if (gestationTimer > (days/5 * 4)) {
            pregnancyText = "eanimod.pregnancy.near_birth";
        } else if (gestationTimer > days/2 ) {
            pregnancyText = "eanimod.pregnancy.obviously_pregnant";
        } else if (gestationTimer > days/3) {
            pregnancyText = "eanimod.pregnancy.pregnant";
        } else if (gestationTimer > days/5) {
            pregnancyText = "eanimod.pregnancy.only_slightly_showing";
        } else {
            pregnancyText = "eanimod.pregnancy.not_showing";
        }
        return new TranslationTextComponent(pregnancyText);
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

//    public float getEyeHeight()
//    {
//        return this.height;
//    }

    protected void registerAttributes() {
        super.registerAttributes();
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(4.0D);
        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
    }

    public void livingTick() {
        super.livingTick();
        if (!this.world.isRemote) {

            if (!this.world.isDaytime() && awokenTimer == 0 && !sleeping) {
                setSleeping(true);
                healTicks = 0;
            } else if (awokenTimer > 0) {
                awokenTimer--;
            } else if (this.world.isDaytime() && sleeping) {
                setSleeping(false);
            }

            if (this.getIdleTime() < 100) {

                if (hunger <= 72000) {
                    if (sleeping) {
                        int days = ConfigHandler.COMMON.gestationDaysCat.get();
                        if (hunger <= days * (0.50) && (ticksExisted % 8 == 0)) {
                            hunger = hunger++;
                        }
                        healTicks++;
                        if (healTicks > 100 && hunger < 6000 && this.getMaxHealth() > this.getHealth()) {
                            this.heal(2.0F);
                            hunger = hunger + 1000;
                            healTicks = 0;
                        }
                    } else {
                        if (ticksExisted % 4 == 0) {
                            hunger++;
                        }
                    }
                }

                //TODO add a limiter to time for growth if the animal is extremely hungry
                if (hunger <= 36000) {
                    timeForGrowth++;
                }
//                if (maxCoatLength == 1){
//                    if (timeForGrowth >= 48000) {
//                        timeForGrowth = 0;
//                        if (maxCoatLength > currentCoatLength) {
//                            currentCoatLength++;
//                            setCoatLength(currentCoatLength);
//                        }
//                    }
//                }else if (maxCoatLength == 2){
//                    if (timeForGrowth >= 24000) {
//                        timeForGrowth = 0;
//                        if (maxCoatLength > currentCoatLength) {
//                            currentCoatLength++;
//                            setCoatLength(currentCoatLength);
//                        }
//                    }
//                }else if (maxCoatLength == 3){
//                    if (timeForGrowth >= 16000) {
//                        timeForGrowth = 0;
//                        if (maxCoatLength > currentCoatLength) {
//                            currentCoatLength++;
//                            setCoatLength(currentCoatLength);
//                        }
//                    }
//                }else if (maxCoatLength == 4){
//                    if (timeForGrowth >= 12000) {
//                        timeForGrowth = 0;
//                        if (maxCoatLength > currentCoatLength) {
//                            currentCoatLength++;
//                            setCoatLength(currentCoatLength);
//                        }
//                    }
//                }
            }

            if(pregnant) {
                gestationTimer++;
                int days = ConfigHandler.COMMON.gestationDaysCat.get();
                if (hunger > days*(0.75) && days !=0) {
                    pregnant = false;
                }
                if (gestationTimer >= days) {
                    pregnant = false;
                    gestationTimer = 0;
                    int kitAverage = 1;
                    int kitRange = 2;

                    if ( Size() <= 0.4 ){
//                        kitAverage = 1;
                        kitRange = 1;
                    }else if ( Size() <= 0.5 ){
                        kitAverage = 2;
                        kitRange = 1;
                    }else if ( Size() <= 0.6 ){
                        kitAverage = 4;
//                        kitRange = 2;
                    }else if ( Size() <= 0.7 ){
                        kitAverage = 5;
//                        kitRange = 2;
                    }else if ( Size() <= 0.8 ){
                        kitAverage = 6;
                        kitRange = 3;
                    }else if ( Size() <= 0.9 ){
                        kitAverage = 7;
                        kitRange = 3;
                    }else{
                        kitAverage = 8;
                        kitRange = 4;
                    }

                    if (genes[56] == 2 && genes[57] == 2){
                        if (genes[58] == 1 && genes[59] == 1){
                            kitRange++;
                        }
                    }else{
                        if (genes[58] == 2 && genes[59] == 2){
                            kitRange--;
                        }
                    }

                    int numberOfKittens = ThreadLocalRandom.current().nextInt(kitRange)+kitAverage;

                    for (int i = 0; i <= numberOfKittens; i++) {
                        mixMateMitosisGenes();
                        mixMitosisGenes();
                        EnhancedCat enhancedcat = ENHANCED_CAT.create(this.world);
                        enhancedcat.setZygosity();
                        enhancedcat.setGrowingAge(0);
                        int[] babyGenes = getKittenGenes(this.mitosisGenes, this.mateMitosisGenes);
                        enhancedcat.setGenes(babyGenes);
                        enhancedcat.setSharedGenes(babyGenes);
//                        enhancedcat.setMaxCoatLength();
//                        enhancedcat.currentCoatLength = enhancedcat.maxCoatLength;
//                        enhancedcat.setCoatLength(enhancedcat.currentCoatLength);
                        enhancedcat.setGrowingAge(-48000);
                        enhancedcat.setCatStatus(EntityState.CHILD_STAGE_ONE.toString());
                        enhancedcat.setLocationAndAngles(this.getPosX(), this.getPosY(), this.getPosZ(), this.rotationYaw, 0.0F);
//                        enhancedcat.setMotherUUID(this.getUniqueID().toString());
                        this.world.addEntity(enhancedcat);
                    }

                }
            }

            if (this.isChild()) {
                if (getCatStatus().equals(EntityState.CHILD_STAGE_ONE.toString()) && this.getGrowingAge() < -16000) {
                    if(hunger < 5000) {
                        setCatStatus(EntityState.CHILD_STAGE_TWO.toString());
                    } else {
                        this.setGrowingAge(-16500);
                    }
                } else if (getCatStatus().equals(EntityState.CHILD_STAGE_TWO.toString()) && this.getGrowingAge() < -8000) {
                    if(hunger < 5000) {
                        setCatStatus(EntityState.CHILD_STAGE_THREE.toString());
                    } else {
                        this.setGrowingAge(-8500);
                    }
                }
            } else if (getCatStatus().equals(EntityState.CHILD_STAGE_THREE.toString())) {
                setCatStatus(EntityState.ADULT.toString());

                //TODO remove the child follow mother ai

            }
            lethalGenes();
        }

    }

//    public class JumpHelperController extends JumpController {
//        private final EnhancedCat cat;
//        private boolean canJump;
//
//        public JumpHelperController(EnhancedCat cat) {
//            super(cat);
//            this.cat = cat;
//        }
//
//        public boolean getIsJumping() {
//            return this.isJumping;
//        }
//
//        public boolean canJump() {
//            return this.canJump;
//        }
//
//        public void setCanJump(boolean canJumpIn) {
//            this.canJump = canJumpIn;
//        }
//
//        /**
//         * Called to actually make the entity jump if isJumping is true.
//         */
//        public void tick() {
//            if (this.isJumping) {
//                this.cat.startJumping();
//                this.isJumping = false;
//            }
//
//        }
//    }
//
//    private boolean isCarrotEaten() {
//        return this.carrotTicks == 0;
//    }
//
//    static class MoveHelperController extends MovementController {
//        private final EnhancedCat cat;
//        private double nextJumpSpeed;
//
//        public MoveHelperController(EnhancedCat cat) {
//            super(cat);
//            this.cat = cat;
//        }
//
//        public void tick() {
//            if (this.cat.onGround && !this.cat.isJumping && !((EnhancedCat.JumpHelperController)this.cat.jumpController).getIsJumping()) {
//                this.cat.setMovementSpeed(0.0D);
//            } else if (this.isUpdating()) {
//                this.cat.setMovementSpeed(this.nextJumpSpeed);
//            }
//
//            super.tick();
//        }
//
//        /**
//         * Sets the speed and location to move to
//         */
//        public void setMoveTo(double x, double y, double z, double speedIn) {
//            if (this.cat.isInWater()) {
//                speedIn = 1.5D;
//            }
//
//            super.setMoveTo(x, y, z, speedIn);
//            if (speedIn > 0.0D) {
//                this.nextJumpSpeed = speedIn;
//            }
//
//        }
//    }


    static class AIAvoidEntity<T extends LivingEntity> extends net.minecraft.entity.ai.goal.AvoidEntityGoal<T> {
        private final EnhancedCat cat;

        public AIAvoidEntity(EnhancedCat cat, Class<T> p_i46403_2_, float p_i46403_3_, double p_i46403_4_, double p_i46403_6_) {
            super(cat, p_i46403_2_, p_i46403_3_, p_i46403_4_, p_i46403_6_);
            this.cat = cat;
        }

        /**
         * Returns whether the EntityAIBase should begin execution.
         */
        public boolean shouldExecute() {
            return super.shouldExecute();
        }
    }

    static class EvilAttackGoal extends MeleeAttackGoal {
        public EvilAttackGoal(EnhancedCat cat) {
            super(cat, 1.4D, true);
        }

        protected double getAttackReachSqr(LivingEntity attackTarget) {
            return (double)(4.0F + attackTarget.getWidth());
        }
    }

    public float Size(){
        float size = 1F; // [minimum size = 0.3 maximum size = 1]

        if (genes[46] < 5){
            size = size - 0.07F;
            if (genes[46] < 4){
                size = size - 0.07F;
                if (genes[46] < 3){
                    size = size - 0.07F;
                    if (genes[46] < 2){
                        size = size - 0.03F;
                    }
                }
            }
        }
        if (genes[46] < 5){
            size = size - 0.07F;
            if (genes[46] < 4){
                size = size - 0.07F;
                if (genes[46] < 3){
                    size = size - 0.07F;
                    if (genes[46] < 2){
                        size = size - 0.03F;
                    }
                }
            }
        }
        if (genes[48] == 3 && genes[49] == 3){
            size = size - 0.075F;
        }else if (genes[48] == 2 && genes[49] == 2){
            size = size - 0.05F;
        }else if (genes[48] == 2 || genes[49] == 2){
            size = size - 0.025F;
        }

        if (genes[34] == 2 || genes[35] == 2){
            size = 0.3F + ((size - 0.3F)/2F);
        }

        return size;
    }

//    @Override
//    @Nullable
//    protected ResourceLocation getLootTable() {
//
//        if (!this.world.isRemote) {
//
//        }
//
//        return new ResourceLocation(Reference.MODID, "enhanced_cat");
//    }

    public void lethalGenes(){

        if(genes[34] == 2 && genes[35] == 2) {
            this.remove();
        }
    }

    protected SoundEvent getAmbientSound()
    {
        return SoundEvents.ENTITY_CAT_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn)
    {
        return SoundEvents.ENTITY_CAT_HURT;
    }

    protected SoundEvent getDeathSound()
    {
        return SoundEvents.ENTITY_CAT_DEATH;
    }

    public boolean isBreedingItem(ItemStack stack) {
        //TODO set this to a separate item or type of item for force breeding
        return BREED_ITEMS.test(stack);
    }

    @Override
    public AgeableEntity createChild(AgeableEntity ageable) {
        if(pregnant) {
            ((EnhancedCat)ageable).pregnant = true;
            ((EnhancedCat)ageable).setMateGenes(this.genes);
            ((EnhancedCat)ageable).mixMateMitosisGenes();
            ((EnhancedCat)ageable).mixMitosisGenes();
        } else {
            pregnant = true;
            this.mateGenes = ((EnhancedCat) ageable).getGenes();
            mixMateMitosisGenes();
            mixMitosisGenes();
        }

        this.setGrowingAge(10);
        this.resetInLove();
        ageable.setGrowingAge(10);
        ((EnhancedCat)ageable).resetInLove();

        ServerPlayerEntity entityplayermp = this.getLoveCause();
        if (entityplayermp == null && ((EnhancedCat)ageable).getLoveCause() != null) {
            entityplayermp = ((EnhancedCat)ageable).getLoveCause();
        }

        if (entityplayermp != null) {
            entityplayermp.addStat(Stats.ANIMALS_BRED);
            CriteriaTriggers.BRED_ANIMALS.trigger(entityplayermp, this, ((EnhancedCat)ageable), (AgeableEntity)null);
        }

        return null;
    }

    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);

        //store this cats's genes
        ListNBT geneList = new ListNBT();
        for (int i = 0; i < genes.length; i++) {
            CompoundNBT nbttagcompound = new CompoundNBT();
            nbttagcompound.putInt("Gene", genes[i]);
            geneList.add(nbttagcompound);
        }
        compound.put("Genes", geneList);

        //store this cats's mate's genes
        ListNBT mateGeneList = new ListNBT();
        for (int i = 0; i < mateGenes.length; i++) {
            CompoundNBT nbttagcompound = new CompoundNBT();
            nbttagcompound.putInt("Gene", mateGenes[i]);
            mateGeneList.add(nbttagcompound);
        }
        compound.put("FatherGenes", mateGeneList);

//        compound.putFloat("CoatLength", this.getCoatLength());

        compound.putBoolean("Pregnant", this.pregnant);
        compound.putInt("Gestation", this.gestationTimer);

        compound.putString("Status", getCatStatus());
        compound.putInt("Hunger", hunger);

        compound.putBoolean("Zygosity", this.getZygosity());
    }

    /**
     * (abstract) Protected helper method to read subclass entity assets from NBT.
     */
    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);

        currentCoatLength = compound.getInt("CoatLength");
//        this.setCoatLength(currentCoatLength);

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

        setCatStatus(compound.getString("Status"));
        hunger = compound.getInt("Hunger");

        hemizygote = compound.getBoolean("Zygosity");

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

        this.pregnant = compound.getBoolean("Pregnant");
        this.gestationTimer = compound.getInt("Gestation");

        setSharedGenes(genes);

        //resets the max so we don't have to store it
//        setMaxCoatLength();

    }

    @OnlyIn(Dist.CLIENT)
    public String getCatTexture() {
        if (this.catTextures.isEmpty()) {
            this.setTexturePaths();
        }
        return this.catTextures.stream().collect(Collectors.joining("/","enhanced_cat",""));

    }

    @OnlyIn(Dist.CLIENT)
    public String[] getVariantTexturePaths() {
        if (this.catTextures.isEmpty()) {
            this.setTexturePaths();
        }

        return this.catTextures.stream().toArray(String[]::new);
    }

    @OnlyIn(Dist.CLIENT)
    public String[] getVariantAlphaTexturePaths()
    {
        if (this.catAlphaTextures.isEmpty()) {
            this.setAlphaTexturePaths();
        }


        //todo this is only temporarity until we have alpha textures
        if (this.catAlphaTextures.isEmpty()) {
            return null;
        }

        return this.catAlphaTextures.stream().toArray(String[]::new);
    }

    @OnlyIn(Dist.CLIENT)
    private void setTexturePaths() {
        int[] genesForText = getSharedGenes();
        if (genesForText != null) {
            int pattern = 0;
            int tabbyMod = 0;
            int eyeType = 0;
            boolean tortie = false;
            char[] uuidArry = getCachedUniqueIdString().toCharArray();

            if ((genesForText[0] >= 2 && genesForText[1] >= 2) && (genesForText[20] == 1 && genesForText[21] == 1)) {
                //self colored cat
            } else {
                if (genesForText[4] == 2 && genesForText[5] == 2) {
                    //homozygous ticked tabby
                } else if (genesForText[4] == 2 || genesForText[5] == 2) {
                    //heterozygous ticked tabby
                } else {
                    //tabby
                    if (genesForText[2] == 1 || genesForText[3] == 1) {
                        if (genesForText[10] == 2 && genesForText[11] == 2) {
                            //spotted tabby
                        } else if (genesForText[10] == 2 || genesForText[11] == 2) {
                            //broken mackerel tabby
                        } else {
                            //mackerel tabby
                        }
                    } else {
                        //classic tabby
                    }

                    //Tabby modifiers
                    if (genesForText[6] == 2 && genesForText[7] == 2) {
                        tabbyMod = 1;
                    }

                    if (genesForText[8] == 2 || genesForText[9] == 2) {
                        if (tabbyMod == 0) {
                            tabbyMod = 2;
                        } else {
                            tabbyMod = 3;
                        }
                    }

                }
            }

            if (genesForText[20] == 2 && genesForText[21] == 2) {
                //red
            } else if (genesForText[20] == 2 || genesForText[21] == 2) {
                //tortie
                tortie = true;
            }

            if (genesForText[12] != 1 && genesForText[13] != 1) {
                if (genesForText[12] == 2 || genesForText[13] == 2) {
                    if (genesForText[12] == 3 || genesForText[13] == 3) {
                        //siamocha
                    } else if (genesForText[12] == 4 || genesForText[13] == 4) {
                        //mink
                    } else {
                        //pointed
                    }
                } else if (genesForText[12] == 3 || genesForText[13] == 3) {
                    if (genesForText[12] == 4 || genesForText[13] == 4) {
                        //burmocha
                    } else {
                        //mocha
                    }
                } else if (genesForText[12] == 4 || genesForText[13] == 4) {
                    //sepia
                } else if (genesForText[12] == 5 || genesForText[13] == 5) {
                    //blue-eyed albino
                } else {
                    //pink-eyed albino
                }
            }

            if (genesForText[22] == 1 || genesForText[23] == 1) {
                //dominant white
            } else if (genesForText[22] == 2 && genesForText[23] == 2) {
                //over 50% white spotting
            } else if (genesForText[22] == 2 || genesForText[23] == 2) {
                //less than 50% white spotting
            } else if (genesForText[22] == 4 && genesForText[23] == 4) {
                //white gloves (birman)
            }

            if (genesForText[24] == 1 || genesForText[25] == 1) {
                //silver/smoke
            }



//            if


            // if (false) {
            // this is where the code happens
            //} else {
            // this is where the other code happens
            // }


//                if (genesForText[12] == 2 && genesForText[13] == 2) {
//                    //dutch patterned
//                    if ( Character.isDigit(uuidArry[5]) ){
//                        dutch = 1 + (uuidArry[5]-48);
//                    } else {
//                        char d = uuidArry[5];
//
//                        switch (d) {
//                            case 'a':
//                                dutch = 11;
//                                break;
//                            case 'b':
//                                dutch = 12;
//                                break;
//                            case 'c':
//                                dutch = 13;
//                                break;
//                            case 'd':
//                                dutch = 14;
//                                break;
//                            case 'e':
//                                dutch = 15;
//                                break;
//                            case 'f':
//                                dutch = 16;
//                                break;
//                            default:
//                                dutch = 0;
//                        }
//                    }
//                }


            this.catTextures.add(CAT_UNDERCOAT[pattern]);
            this.catTextures.add(CAT_BACKGROUND[pattern]);
            this.catTextures.add(CAT_PATTERN[pattern]);
            this.catTextures.add(CAT_COLOURPOINT[pattern]);
            this.catTextures.add(CAT_SPOTS[pattern]);
            if (tortie) {
                this.catTextures.add("alpha_group_start");
                this.catTextures.add(CAT_SKIN[pattern]);
                this.catTextures.add(CAT_SKINPATTERN[pattern]);
                this.catTextures.add(CAT_SKINCOLOURPOINT[pattern]);
                this.catTextures.add(CAT_SKINSPOTS[pattern]);
                this.catTextures.add("alpha_group_end");
            }
            this.catTextures.add("alpha_group_start");
            this.catTextures.add(CAT_SKIN[pattern]);
            this.catTextures.add(CAT_SKINPATTERN[pattern]);
            this.catTextures.add(CAT_SKINCOLOURPOINT[pattern]);
            this.catTextures.add(CAT_SKINSPOTS[pattern]);
            this.catTextures.add("alpha_group_end");
            this.catTextures.add(CAT_EYEL[eyeType]);
            this.catTextures.add(CAT_EYER[eyeType]);


        } //if genes are not null end bracket

    } // setTexturePaths end bracket

    @OnlyIn(Dist.CLIENT)
    private void setAlphaTexturePaths() {
        int[] genesForText = getSharedGenes();
        if (genesForText != null) {
            int coat = 0;
            int tortie = 0;

            //TODO genes for sphynx, Lykoi, and other fur distribution genes go here including stuff that just makes the fur thin

            //TODO put the logic for figuring out what the tortie/calico spots should look like

            if (coat != 0) {
                this.catAlphaTextures.add(CAT_COATTYPE[coat]);
            }
            if (tortie !=0) {
                this.catAlphaTextures.add(CAT_COATTYPE[coat]);
            }

        }
    }

    @OnlyIn(Dist.CLIENT)
    public float[] getRgb() {
        if (catColouration == null) {
            catColouration = new float[6];
            int[] genesForText = getSharedGenes();

            float blackR = 15.0F;
            float blackG = 7.0F;
            float blackB = 7.0F;

            float redR = 134.0F;
            float redG = 79.0F;
            float redB = 41.0F;

            float eyelR = 134.0F;
            float eyelG = 79.0F;
            float eyelB = 41.0F;

            float eyerR = 134.0F;
            float eyerG = 79.0F;
            float eyerB = 41.0F;



            if (genesForText[14] == 1 || genesForText[15] == 1) {
                //black
            } else if (genesForText[14] == 2 || genesForText[15] == 2) {
                //chocolate
            } else {
                //cinammon
            }

            if (genesForText[16] == 2 && genesForText[17] == 2) {
                if (genesForText[18] == 1 || genesForText[19] == 1) {
                    //caramel/apricot
                } else {
                    //dilute
                }
            }

            //TODO TEMP AF
            //black
            catColouration[0] = blackR;
            catColouration[1] = blackG;
            catColouration[2] = blackB;

            //red
            catColouration[3] = redR;
            catColouration[4] = redG;
            catColouration[5] = redB;

            catColouration[6] = eyelR;
            catColouration[7] = eyelG;
            catColouration[8] = eyelB;

            catColouration[9] = eyerR;
            catColouration[10] = eyerG;
            catColouration[11] = eyerB;

            for (int i = 0; i <= 11; i++) {
                if (catColouration[i] > 255.0F) {
                    catColouration[i] = 255.0F;
                }
                catColouration[i] = catColouration[i] / 255.0F;
            }

        }
        return catColouration;
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

    public int[] getKittenGenes(int[] mitosis, int[] mateMitosis) {
        Random rand = new Random();
        int[] kittenGenes = new int[GENES_LENGTH];

        for (int i = 0; i < genes.length; i = (i + 2)) {
            boolean thisOrMate = rand.nextBoolean();
            if (thisOrMate) {
                kittenGenes[i] = mitosis[i];
                kittenGenes[i+1] = mateMitosis[i+1];
            } else {
                kittenGenes[i] = mateMitosis[i];
                kittenGenes[i+1] = mitosis[i+1];
            }
        }



        return kittenGenes;
    }

    @Nullable
    @Override
    public ILivingEntityData onInitialSpawn(IWorld inWorld, DifficultyInstance difficulty, SpawnReason spawnReason, @Nullable ILivingEntityData livingdata, @Nullable CompoundNBT itemNbt) {
        livingdata = super.onInitialSpawn(inWorld, difficulty, spawnReason, livingdata, itemNbt);
        int[] spawnGenes;

        if (livingdata instanceof GroupData) {
            int[] spawnGenes1 = ((GroupData) livingdata).groupGenes;
            int[] mitosis = new int[GENES_LENGTH];
            punnetSquare(mitosis, spawnGenes1);

            int[] spawnGenes2 = ((GroupData) livingdata).groupGenes;
            int[] mateMitosis = new int[GENES_LENGTH];
            punnetSquare(mateMitosis, spawnGenes2);
            spawnGenes = getKittenGenes(mitosis, mateMitosis);
        } else {
            spawnGenes = createInitialGenes(inWorld);
            livingdata = new GroupData(spawnGenes);
        }

        this.genes = spawnGenes;
        setSharedGenes(genes);
        setZygosity();
//        setMaxCoatLength();
//        this.currentCoatLength = this.maxCoatLength;
//        setCoatLength(this.currentCoatLength);
        return livingdata;
    }

//    private void setMaxCoatLength() {
//        float angora = 0.0F;
//
//        if ( genes[26] == 2 && genes[27] == 2){
//            if (genes[50] == 1 && genes[51] == 1 || genes[50] == 3 && genes[51] == 3){
//                angora = 1;
//            }else if ( genes[50] == 1 || genes[51] == 1 || genes[50] == 3 || genes[51] == 3){
//                angora = 2;
//            }else{
//                angora = 3;
//            }
//
//            if ( genes[52] >= 2 && genes[53] >= 2){
//                angora = angora + 1;
//                if ( genes[52] == 3 && genes[53] == 3 && angora <= 3){
//                    angora = angora + 1;
//                }
//            }
//
//            if ( genes[54] == 1 || genes[55] == 1 && angora >= 2){
//                angora = angora - 1;
//                if ( genes[54] == 1 && genes[55] == 1 && angora >= 2){
//                    angora = angora - 1;
//                }
//            }
//        }
//
//        this.maxCoatLength = (int)angora;
//
//    }



    private int[] createInitialGenes(IWorld inWorld) {
        int[] initialGenes = new int[GENES_LENGTH];

//        String pureBreed = "false";

        //[ 0=minecraft wildtype, 1=jungle wildtype, 2=savanna wildtype, 3=cold wildtype, 4=swamp wildtype ]
        int wildType = 0;
//        Biome biome = inWorld.getBiome(new BlockPos(this));

//        if (biome.getDefaultTemperature() >= 0.9F && biome.getDownfall() > 0.8F) // hot and wet (jungle)
//        {
//            wildType = 1;
//        } else if (biome.getDefaultTemperature() >= 0.9F && biome.getDownfall() < 0.3F) // hot and dry (savanna)
//        {
//            wildType = 2;
//        } else if (biome.getDefaultTemperature() < 0.3F) // cold (mountains)
//        {
//            wildType = 3;
//        } else if (biome.getDefaultTemperature() >= 0.8F && biome.getDownfall() > 0.8F) {
//            wildType = 4;
//        }

//        if (pureBreed.equalsIgnoreCase("false")) {
//             if(wildType == 1) {
//                initialGenes = new int[]{1,1,6,1,1,1,6,1,1,1,10,10,10,10,10,10,10,10,10,10,1,1,1,1,2,2,2,2,2,2,2,2,3,3,2,2,1,1,2,2,1,1,2,2,1,1,3,3,2,2,1,1,2,2,3,3,2,2,3,3,2,2,2,2,3,3,3,3,2,2,1,1,1,1,2,2,2,2,1,1,2,2,2,2,2,2,1,1,1,1,1,1,1,1,1,1,1,1,2,2,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,10,10,1,7,4,4,5,5,6,6,4,6,5,5,2,2,1,1};
//            } else if(wildType == 2) {
//                initialGenes = new int[]{1,1,6,1,1,1,1,1,1,1,10,10,10,10,10,10,10,10,10,10,1,1,1,1,4,2,1,2,2,2,2,2,3,3,1,2,1,1,2,2,1,1,2,2,1,1,3,3,2,2,1,1,2,2,3,3,2,2,3,3,2,2,2,2,3,3,3,3,2,2,1,1,1,1,2,2,2,2,1,1,2,2,1,2,2,2,1,1,1,1,1,1,1,1,1,1,1,1,2,2,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,10,10,1,7,5,5,3,3,7,7,3,7,2,2,2,2,1,1};
//            } else if(wildType == 3) {
//                initialGenes = new int[]{1,1,3,1,1,1,1,1,1,1,10,10,10,10,10,10,10,10,10,10,1,1,1,1,4,4,1,1,1,1,1,1,3,3,2,2,1,1,2,2,1,1,2,2,3,3,2,2,2,2,1,1,2,2,3,3,2,2,3,3,2,2,2,2,3,3,3,3,1,1,1,1,1,1,3,3,2,2,2,1,2,2,2,2,2,2,2,2,1,1,2,2,1,1,1,1,1,1,2,2,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,2,2,1,1,1,1,5,5,5,5,11,11,3,3,5,5,21,21,2,2,1,1,1,1};
//            } else if(wildType == 4) {
//                initialGenes = new int[]{1,1,3,1,1,1,1,1,1,1,10,10,10,10,10,10,10,10,10,10,1,1,1,1,4,4,1,1,1,1,1,1,3,3,2,2,1,1,2,2,1,1,2,2,3,3,2,2,2,2,1,1,2,2,3,3,2,2,3,3,2,2,2,2,3,3,3,3,1,1,1,1,1,1,3,3,2,2,2,1,2,2,2,2,2,2,2,2,1,1,2,2,1,1,1,1,1,1,2,2,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,2,2,1,1,1,1,5,5,5,5,11,11,3,3,5,5,21,21,2,2,1,1,1,1};
//            } else {
//                 initialGenes = new int[]{1,1,3,1,1,1,1,1,1,1,10,10,10,10,10,10,10,10,10,10,1,1,1,1,4,4,1,1,1,1,1,1,3,3,2,2,1,1,2,2,1,1,2,2,3,3,2,2,2,2,1,1,2,2,3,3,2,2,3,3,2,2,2,2,3,3,3,3,1,1,1,1,1,1,3,3,2,2,2,1,2,2,2,2,2,2,2,2,1,1,2,2,1,1,1,1,1,1,2,2,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,2,2,1,1,1,1,5,5,5,5,11,11,3,3,5,5,21,21,2,2,1,1,1,1};
//             }
//        }


        /**
         * Example:
         *
         *         //Name of the gene [ "normal gene goes first", "allele variants go after"]                                   <-- this is just a label, inside the [] write the allel names in order so 1 should be the wildtype, and every number after is a mutation/allele
         *         if (ThreadLocalRandom.current().nextInt(100) > WTC) {                                                        <-- this line determines the rarity of the gene, don't mess with it for now
         *             initialGenes[0] = (ThreadLocalRandom.current().nextInt("number of allels including the wildtype") + 1);  <-- initialGenes[0] sets the position the allele is on each one needs a different number in order, the first is always even with the second being the odd number following so Agouti is 0 and 1.
         *
         *         } else {
         *             initialGenes[0] = ("the number that wildtype is usually 1");                                             <-- under some cases its easier for wildtype to not be 1 but its not the recommended way to do things.
         *         }
         *         if (ThreadLocalRandom.current().nextInt(100) > WTC) {                                                        <-- this is the second half of the gene its usually identical to the first.
         *             initialGenes[1] = (ThreadLocalRandom.current().nextInt(2) + 1);
         *
         *         } else {
         *             initialGenes[1] = (1);
         *         }
         *
         */


        /**
         * Pattern Genes <-- this is a label just to give you an idea of what sort of things that gene does, obviously some genes blur the lines
         */

        //Agouti [ Agouti+, self/non agouti, Savanna, Asian Leopard Cat, (and other wild cats) ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[0] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[0] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[1] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[1] = (1);
        }

        //TODO research Tabby Spotted gene
        //Tabby Type [ Mackerel, classic ]
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

        // Ticked [Non-ticked+, Ticked ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[4] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[4] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[5] = (ThreadLocalRandom.current().nextInt(2) + 1);

        } else {
            initialGenes[5] = (1);
        }

        // Tabby Size Mod [ Large Pattern+, small Patterm ]
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

        // Australian Mist Tabby Spots [ Wildtype+, Tabby Spots Modifier  ]
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

        // Spotted Tabby (egyptian mau) [ Wiltype+, Spotted ]
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

        // Colorpoint [ Wildtype+, pointed, mocha, sepia, blue-eyes albino, pink-eyed albino ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[12] = (ThreadLocalRandom.current().nextInt(6) + 1);

        } else {
            initialGenes[12] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[13] = (ThreadLocalRandom.current().nextInt(6) + 1);

        } else {
            initialGenes[13] = (1);
        }

        //Eumelanin [ Black+, chocolate, cinnamon ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[14] = (ThreadLocalRandom.current().nextInt(3) + 1);

        } else {
            initialGenes[14] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[15] = (ThreadLocalRandom.current().nextInt(3) + 1);

        } else {
            initialGenes[15] = (1);
        }

        //Dilute [ Wildtype+, dilute ]
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

        //Dilute Modifier [ Caramel/Apricot, wildtype+ ]
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

        //Sex-linked Red [ Red, black+ ] sexlinked
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

        //White [ Dominant White, white Spotting, non-white+, gloving ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[22] = (ThreadLocalRandom.current().nextInt(4) + 1);

        } else {
            initialGenes[22] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[23] = (ThreadLocalRandom.current().nextInt(4) + 1);

        } else {
            initialGenes[23] = (1);
        }

        //Silver [ Silver, wildtype+ ]
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

        //Extension [ Wildtype+, amber, russet ]
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[26] = (ThreadLocalRandom.current().nextInt(3) + 1);

        } else {
            initialGenes[26] = (1);
        }
        if (ThreadLocalRandom.current().nextInt(100) > WTC) {
            initialGenes[27] = (ThreadLocalRandom.current().nextInt(3) + 1);

        } else {
            initialGenes[27] = (1);
        }

        //Sunshine [Wildtype, sunshine ]
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

        //Thai White Spotting [  ] not really sure if resecessive or dominant yet
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

        //Karpati [ wildtype, Karpati ]
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

        //Dilute

        //Dilute Mod

        //Amber

        //Colour Inhibitor

        //Sunshine

        //Wide band



        return initialGenes;

    }

    public void setGenes(int[] genes) {
        this.genes = genes;
    }

    public int[] getGenes() {
        return this.genes;
    }

    public void setMateGenes(int[] mateGenes){ this.mateGenes = mateGenes; }

    public static class GroupData implements ILivingEntityData {

        public int[] groupGenes;

        public GroupData(int[] groupGenes) {
            this.groupGenes = groupGenes;
        }

    }

}
