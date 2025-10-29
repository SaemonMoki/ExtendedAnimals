package mokiyoki.enhancedanimals.entity;

import mokiyoki.enhancedanimals.ai.EnhancedEatPlantsGoal;
import mokiyoki.enhancedanimals.ai.general.EnhancedAvoidEntityGoal;
import mokiyoki.enhancedanimals.ai.general.EnhancedBreedGoal;
import mokiyoki.enhancedanimals.ai.general.EnhancedLookAtGoal;
import mokiyoki.enhancedanimals.ai.general.EnhancedLookRandomlyGoal;
import mokiyoki.enhancedanimals.ai.general.EnhancedTemptGoal;
import mokiyoki.enhancedanimals.ai.general.EnhancedWanderingGoal;
import mokiyoki.enhancedanimals.ai.general.GrazingGoal;
import mokiyoki.enhancedanimals.ai.general.SeekShelterGoal;
import mokiyoki.enhancedanimals.ai.general.StayShelteredGoal;
import mokiyoki.enhancedanimals.ai.rabbit.EnhancedRabbitPanicGoal;
import mokiyoki.enhancedanimals.entity.genetics.RabbitGeneticsInitialiser;
import mokiyoki.enhancedanimals.init.FoodSerialiser;
import mokiyoki.enhancedanimals.init.ModBlocks;
import mokiyoki.enhancedanimals.init.ModItems;
import mokiyoki.enhancedanimals.model.modeldata.AnimalModelData;
import mokiyoki.enhancedanimals.model.modeldata.RabbitModelData;
import mokiyoki.enhancedanimals.util.Genes;
import mokiyoki.enhancedanimals.util.Reference;
import mokiyoki.enhancedanimals.config.GeneticAnimalsConfig;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.JumpControl;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.FollowParentGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.animal.Fox;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import static mokiyoki.enhancedanimals.init.FoodSerialiser.rabbitFoodMap;
import static mokiyoki.enhancedanimals.init.ModEntities.ENHANCED_RABBIT;
import static mokiyoki.enhancedanimals.renderer.textures.RabbitTexture.calculateRabbitTextures;
import static mokiyoki.enhancedanimals.util.Reference.RABBIT_AUTOSOMAL_GENES_LENGTH;

public class EnhancedRabbit extends EnhancedAnimalAbstract implements net.minecraftforge.common.IForgeShearable {

    //avalible UUID spaces : [ S X X X X X X 7 - 8 9 10 11 - 12 13 14 15 - 16 17 18 19 - 20 21 22 23 24 25 26 27 28 29 30 31 ]

    private static final EntityDataAccessor<Integer> COAT_LENGTH = SynchedEntityData.defineId(EnhancedRabbit.class, EntityDataSerializers.INT);

    //TODO find broken texture spawns in desert

    private int jumpTicks;
    private int jumpDuration;
    public int[] noseTwitch = {0,0,0,0}; //how long to twitch/not twitch, how fast to twitch, count up or down, twitch cycle
    private boolean wasOnGround;
    private int currentMoveTypeDuration;
    public int carrotTicks;
    private String dropMeatType;

    private int maxCoatLength;
    private int currentCoatLength;
    private int timeForGrowth = 0;

    @OnlyIn(Dist.CLIENT)
    public RabbitModelData rabbitModelData;

    private static final int SEXLINKED_GENES_LENGTH = 2;

    private GrazingGoal grazingGoal;

    public EnhancedRabbit(EntityType<? extends EnhancedRabbit> entityType, Level worldIn) {
        super(entityType, worldIn,SEXLINKED_GENES_LENGTH, RABBIT_AUTOSOMAL_GENES_LENGTH, true);
//        this.setSize(0.4F, 0.5F);
        this.jumpControl = new EnhancedRabbit.JumpHelperController(this);
        this.moveControl = new EnhancedRabbit.MoveHelperController(this);
        this.setMovementSpeed(0.0D);
    }

    private Map<Block, EnhancedEatPlantsGoal.EatValues> createGrazingMap() {
        Map<Block, EnhancedEatPlantsGoal.EatValues> ediblePlants = new HashMap<>();
        ediblePlants.put(Blocks.CARROTS, new EnhancedEatPlantsGoal.EatValues(4, 1, 750));
        ediblePlants.put(Blocks.BEETROOTS, new EnhancedEatPlantsGoal.EatValues(3, 1, 750));
        ediblePlants.put(Blocks.WHEAT, new EnhancedEatPlantsGoal.EatValues(2, 1, 750));
        ediblePlants.put(Blocks.AZURE_BLUET, new EnhancedEatPlantsGoal.EatValues(3, 2, 750));
        ediblePlants.put(ModBlocks.GROWABLE_AZURE_BLUET.get(), new EnhancedEatPlantsGoal.EatValues(3, 2, 750));
        ediblePlants.put(Blocks.BLUE_ORCHID, new EnhancedEatPlantsGoal.EatValues(7, 3, 375));
        ediblePlants.put(ModBlocks.GROWABLE_BLUE_ORCHID.get(), new EnhancedEatPlantsGoal.EatValues(7, 2, 375));
        ediblePlants.put(Blocks.CORNFLOWER, new EnhancedEatPlantsGoal.EatValues(7, 3, 375));
        ediblePlants.put(ModBlocks.GROWABLE_CORNFLOWER.get(), new EnhancedEatPlantsGoal.EatValues(7, 2, 375));
        ediblePlants.put(Blocks.DANDELION, new EnhancedEatPlantsGoal.EatValues(3, 2, 750));
        ediblePlants.put(ModBlocks.GROWABLE_DANDELION.get(), new EnhancedEatPlantsGoal.EatValues(3, 2, 750));
        ediblePlants.put(Blocks.OXEYE_DAISY, new EnhancedEatPlantsGoal.EatValues(7, 3, 750));
        ediblePlants.put(ModBlocks.GROWABLE_OXEYE_DAISY.get(), new EnhancedEatPlantsGoal.EatValues(7, 2, 750));
        ediblePlants.put(Blocks.ROSE_BUSH, new EnhancedEatPlantsGoal.EatValues(4, 3, 375));
        ediblePlants.put(ModBlocks.GROWABLE_ROSE_BUSH.get(), new EnhancedEatPlantsGoal.EatValues(4, 2, 375));
        ediblePlants.put(Blocks.SUNFLOWER, new EnhancedEatPlantsGoal.EatValues(4, 3, 375));
        ediblePlants.put(ModBlocks.GROWABLE_SUNFLOWER.get(), new EnhancedEatPlantsGoal.EatValues(4, 2, 375));
        ediblePlants.put(Blocks.GRASS, new EnhancedEatPlantsGoal.EatValues(1, 1, 750));
        ediblePlants.put(ModBlocks.GROWABLE_GRASS.get(), new EnhancedEatPlantsGoal.EatValues(1, 1, 750));
        ediblePlants.put(Blocks.TALL_GRASS, new EnhancedEatPlantsGoal.EatValues(1, 1, 750));
        ediblePlants.put(ModBlocks.GROWABLE_TALL_GRASS.get(), new EnhancedEatPlantsGoal.EatValues(1, 1, 750));
        ediblePlants.put(Blocks.FERN, new EnhancedEatPlantsGoal.EatValues(1, 1, 750));
        ediblePlants.put(ModBlocks.GROWABLE_FERN.get(), new EnhancedEatPlantsGoal.EatValues(1, 1, 750));
        ediblePlants.put(Blocks.LARGE_FERN, new EnhancedEatPlantsGoal.EatValues(1, 1, 750));
        ediblePlants.put(ModBlocks.GROWABLE_LARGE_FERN.get(), new EnhancedEatPlantsGoal.EatValues(1, 1, 750));
        ediblePlants.put(Blocks.SWEET_BERRY_BUSH, new EnhancedEatPlantsGoal.EatValues(1, 1, 1000));
        ediblePlants.put(Blocks.CACTUS, new EnhancedEatPlantsGoal.EatValues(1, 1, 3000));
//        ediblePlants.put(Blocks.PUMPKIN, new EnhancedEatPlantsGoal.EatValues(1, 1, 3000));
//        ediblePlants.put(Blocks.MELON, new EnhancedEatPlantsGoal.EatValues(1, 1, 3000));

        return ediblePlants;
    }

    @Override
    protected void registerGoals() {
        int napmod = this.random.nextInt(1200);
        this.grazingGoal = new GrazingGoal(this, 1.0D);
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(2, new EnhancedRabbitPanicGoal(this, 2.2D));
        this.goalSelector.addGoal(3, new EnhancedAvoidEntityGoal<>(this, Wolf.class, 10.0F, 2.2D, 2.2D, null));
        this.goalSelector.addGoal(3, new EnhancedAvoidEntityGoal<>(this, Cat.class, 10.0F, 2.2D, 2.2D, null));
        this.goalSelector.addGoal(3, new EnhancedAvoidEntityGoal<>(this, Fox.class, 10.0F, 2.2D, 2.2D, null));
        this.goalSelector.addGoal(3, new EnhancedAvoidEntityGoal<>(this, EnhancedPig.class, 6.0F, 2.2D, 2.2D, null));
        this.goalSelector.addGoal(3, new EnhancedAvoidEntityGoal<>(this, Monster.class, 4.0F, 2.2D, 2.2D, null));
        this.goalSelector.addGoal(4, new EnhancedBreedGoal(this, 0.8D));
        this.goalSelector.addGoal(5, new EnhancedTemptGoal(this, 1.0D, 1.2D, false, Items.AIR));
        this.goalSelector.addGoal(6, new EnhancedAvoidEntityGoal<>(this, Player.class, 8.0F, 2.2D, 2.2D, null));
        this.goalSelector.addGoal(7, new FollowParentGoal(this, 1.1D));
        this.goalSelector.addGoal(8, new StayShelteredGoal(this, 4000, 7500, napmod));
        this.goalSelector.addGoal(9, new SeekShelterGoal(this, 1.0D, 4000, 7500, napmod));
//        this.goalSelector.addGoal(9, new EnhancedRabbitRaidFarmGoal(this));
        this.goalSelector.addGoal(10, new EnhancedEatPlantsGoal(this, createGrazingMap()));
        this.goalSelector.addGoal(11, this.grazingGoal);
//        this.goalSelector.addGoal(5, new EnhancedRabbitEatPlantsGoal(this));
//        this.goalSelector.addGoal(6, new EnhancedWaterAvoidingRandomWalkingGoal(this, 0.6D));
        this.goalSelector.addGoal(12, new EnhancedWanderingGoal(this, 1.0D));
        this.goalSelector.addGoal(13, new EnhancedLookAtGoal(this, Player.class, 10.0F));
        this.goalSelector.addGoal(13, new EnhancedLookAtGoal(this, Monster.class, 10.0F));
        this.goalSelector.addGoal(14, new EnhancedLookRandomlyGoal(this));
    }

    @Override
    protected FoodSerialiser.AnimalFoodMap getAnimalFoodType() {
        return rabbitFoodMap();
    }

    public static boolean checkRabbitSpawnRules(EntityType<EnhancedRabbit> p_29699_, LevelAccessor p_29700_, MobSpawnType p_29701_, BlockPos p_29702_, Random p_29703_) {
        return p_29700_.getBlockState(p_29702_.below()).is(BlockTags.RABBITS_SPAWNABLE_ON) && isBrightEnoughToSpawn(p_29700_, p_29702_);
    }

    protected float getJumpPower() {
        if (!this.horizontalCollision && (!this.moveControl.hasWanted() || !(this.moveControl.getWantedY() > this.getY() + 0.5D))) {
            Path path = this.navigation.getPath();
            if (path != null && path.getNextNodeIndex() < path.getNodeCount()) {
                Vec3 vec3d = path.getNextEntityPos(this);
                if (vec3d.y > this.getY() + 0.5D) {
                    return 0.5F;
                }
            }

            return this.moveControl.getSpeedModifier() <= 0.6D ? 0.2F : 0.3F;
        } else {
            return 0.5F;
        }
    }

    /**
     * Causes this entity to do an upwards motion (jumping).
     */
    protected void jumpFromGround() {
        super.jumpFromGround();
        double d0 = this.moveControl.getSpeedModifier();
        if (d0 > 0.0D) {
            double d1 = this.distanceToSqr(this.getDeltaMovement());
            if (d1 < 0.01D) {
                this.moveRelative(0.1F, new Vec3(0.0D, 0.0D, 1.0D));
            }
        }

        if (!this.level.isClientSide) {
            this.level.broadcastEntityEvent(this, (byte)1);
        }

    }

    @OnlyIn(Dist.CLIENT)
    public float getJumpCompletion(float tick) {
        return this.jumpDuration == 0 ? 0.0F : ((float)this.jumpTicks + tick) / (float)this.jumpDuration;
    }

    public void setMovementSpeed(double newSpeed) {
        this.getNavigation().setSpeedModifier(newSpeed);
        this.moveControl.setWantedPosition(this.moveControl.getWantedX(), this.moveControl.getWantedY(), this.moveControl.getWantedZ(), newSpeed);
    }

    public void setJumping(boolean jumping) {
        super.setJumping(jumping);
        if (jumping) {
            this.playSound(this.getJumpSound(), this.getSoundVolume(), ((this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F) * 0.8F);
        }

    }

    public void startJumping() {
        this.setJumping(true);
        this.jumpDuration = 10;
        this.jumpTicks = 0;
    }

    @OnlyIn(Dist.CLIENT)
    public int getNoseTwitch() { return this.noseTwitch[3]; }

    @Override
    public EntityDimensions getDimensions(Pose poseIn) {
        return EntityDimensions.scalable(0.6F, 0.6F).scale(this.getScale());
    }

    @Override
    public float getScale() {
        float size = (this.getAnimalSize() > 0.0F ? this.getAnimalSize() : 1.0F)*1.25F;
        float newbornSize = 0.35F;
        return this.isGrowing() ? (newbornSize + ((size-newbornSize) * (this.growthAmount()))) : size;
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(COAT_LENGTH, 0);
//        this.dataManager.register(NOSE_WIGGLING, false);
    }

    @Override
    protected String getSpecies() { return "entity.eanimod.enhanced_rabbit"; }

    @Override
    protected int getAdultAge() {
        if (this.adultAge != null) return this.adultAge;
        this.adultAge = GeneticAnimalsConfig.COMMON.adultAgeRabbit.get();
        return this.adultAge;
    }

    @Override
    protected int gestationConfig() {
        return GeneticAnimalsConfig.COMMON.gestationDaysRabbit.get();
    }

    private void setCoatLength(int coatLength) {
        this.entityData.set(COAT_LENGTH, coatLength);
    }

    public int getCoatLength() {
        return this.entityData.get(COAT_LENGTH);
    }

    public void customServerAiStep() {
        if (this.currentMoveTypeDuration > 0) {
            --this.currentMoveTypeDuration;
        }

        if (this.onGround) {
            if (!this.wasOnGround) {
                this.setJumping(false);
                this.checkLandingDelay();
            }

            EnhancedRabbit.JumpHelperController enhancedRabbit$rabbitjumphelper = (EnhancedRabbit.JumpHelperController)this.jumpControl;
            if (!enhancedRabbit$rabbitjumphelper.getIsJumping()) {
                if (this.moveControl.hasWanted() && this.currentMoveTypeDuration == 0) {
                    Path path = this.navigation.getPath();
                    Vec3 vec3d = new Vec3(this.moveControl.getWantedX(), this.moveControl.getWantedY(), this.moveControl.getWantedZ());
                    if (path != null && path.getNextNodeIndex() < path.getNodeCount()) {
                        vec3d = path.getNextEntityPos(this);
                    }

                    this.calculateRotationYaw(vec3d.x, vec3d.z);
                    this.startJumping();
                }
            } else if (!enhancedRabbit$rabbitjumphelper.canJump()) {
                this.enableJumpControl();
            }
        }

        this.wasOnGround = this.onGround;
    }

    private void calculateRotationYaw(double x, double z) {
        this.setYRot((float)(Mth.atan2(z - this.getZ(), x - this.getX()) * (double)(180F / (float)Math.PI)) - 90.0F);
    }

    private void enableJumpControl() {
        ((EnhancedRabbit.JumpHelperController)this.jumpControl).setCanJump(true);
    }

    private void disableJumpControl() {
        ((EnhancedRabbit.JumpHelperController)this.jumpControl).setCanJump(false);
    }

    private void updateMoveTypeDuration() {
        if (this.moveControl.getSpeedModifier() < 2.2D) {
            this.currentMoveTypeDuration = 10;
        } else {
            this.currentMoveTypeDuration = 1;
        }

    }

    private void checkLandingDelay() {
        this.updateMoveTypeDuration();
        this.disableJumpControl();
    }


    @OnlyIn(Dist.CLIENT)
    public void handleEntityEvent(byte id) {
        super.handleEntityEvent(id);
        if (id == 1) {
            this.spawnSprintParticle();
            this.jumpDuration = 10;
            this.jumpTicks = 0;
        }
    }

    @Override
    protected void fixGeneLengths() {
        if (this.genetics.getNumberOfAutosomalGenes() < RABBIT_AUTOSOMAL_GENES_LENGTH) {
            this.genetics.setAutosomalGenes(Arrays.copyOf(this.genetics.getAutosomalGenes(), RABBIT_AUTOSOMAL_GENES_LENGTH));
        }
    }

    public static AttributeSupplier.Builder prepareAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 3.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.3D);
    }

    public void aiStep() {
        super.aiStep();
        if (this.jumpTicks != this.jumpDuration) {
            ++this.jumpTicks;
        } else if (this.jumpDuration != 0) {
            this.jumpTicks = 0;
            this.jumpDuration = 0;
            this.setJumping(false);
        }
    }

    @Override
    protected void runLivingTickClient() {
        super.runLivingTickClient();
        //how long to twitch/not twitch, how fast to twitch, twitch cycle
        if (this.sleeping) {
            if (noseTwitch[0] == 0) {
                noseTwitch[0] = -random.nextInt(900); //dont twitch nose for up to 45 seconds
            } else if (noseTwitch[0] == -1){
                noseTwitch[0] = random.nextInt(300); //twitch for up to 15 seconds
            }
            if (noseTwitch[0] > 0) {
                noseTwitch[1] = 1;
            }
        } else {
            if (noseTwitch[0] == 0) {
                noseTwitch[0] = random.nextInt(1500) - 200; //twitch nose continuously for up to a minute, may stop for up to 10 seconds;
            } else if (noseTwitch[0] > 0) {
                if (noseTwitch[0] > 900) {
                    noseTwitch[1] = 1;
                } else {
                    noseTwitch[1] = 2;
                }
            }
        }

        if (noseTwitch[0] > 0) {
            noseTwitch[0] = noseTwitch[0] - 1;
            if (noseTwitch[2] == 0) {
                if (noseTwitch[3] <= -1) {
                    noseTwitch[2] = 1;
                } else {
                    noseTwitch[3]--;
                }
            } else {
                if (noseTwitch[3] >= 1) {
                    noseTwitch[2] = 0;
                } else {
                    noseTwitch[3]++;
                }
            }
        } else {
            noseTwitch[0] = noseTwitch[0] + 1;
        }

    }

    @Override
    protected void incrementHunger() {
        if (sleeping) {
            hunger = hunger + (0.125F*getHungerModifier());
        } else {
            hunger = hunger + (0.25F*getHungerModifier());
        }
    }

    @Override
    protected void runExtraIdleTimeTick() {
        if (hunger <= 36000) {
            timeForGrowth++;
        }

        int age = this.getEnhancedAnimalAge();

        int maxcoat = age >= this.getAdultAge() ? this.maxCoatLength : (int)(this.maxCoatLength*(((float)age/(float)this.getAdultAge())));

        if (maxcoat == 1){
            if (timeForGrowth >= 48000 / GeneticAnimalsConfig.COMMON.woolMultiplierRabbit.get()) {
                resetTimeForGrowthAndCheckCoatGrowth(maxcoat);
            }
        }else if (maxcoat == 2){
            if (timeForGrowth >= 24000 / GeneticAnimalsConfig.COMMON.woolMultiplierRabbit.get()) {
                resetTimeForGrowthAndCheckCoatGrowth(maxcoat);
            }
        }else if (maxcoat == 3){
            if (timeForGrowth >= 16000 / GeneticAnimalsConfig.COMMON.woolMultiplierRabbit.get()) {
                resetTimeForGrowthAndCheckCoatGrowth(maxcoat);
            }
        }else if (maxcoat == 4){
            if (timeForGrowth >= 12000 / GeneticAnimalsConfig.COMMON.woolMultiplierRabbit.get()) {
                resetTimeForGrowthAndCheckCoatGrowth(maxcoat);
            }
        }
    }

    private void resetTimeForGrowthAndCheckCoatGrowth(int maxcoat) {
        timeForGrowth = 0;
        if (maxcoat > currentCoatLength) {
            currentCoatLength++;
            setCoatLength(currentCoatLength);
        }
    }

    @Override
    public InteractionResult mobInteract(Player entityPlayer, InteractionHand hand) {
        ItemStack itemStack = entityPlayer.getItemInHand(hand);
        Item item = itemStack.getItem();

        if (item == ModItems.ENHANCED_RABBIT_EGG.get()) {
            return InteractionResult.SUCCESS;
        }

        return super.mobInteract(entityPlayer, hand);
    }

    @Override
    protected int getNumberOfChildren() {
        int[] genes = this.genetics.getAutosomalGenes();
        float size = this.getAnimalSize();
        int kitAverage = 1;
        int kitRange = 2;

        if (size <= 0.4 ){
//                        kitAverage = 1;
            kitRange = 1;
        }else if (size <= 0.5 ){
            kitAverage = 2;
            kitRange = 1;
        }else if (size <= 0.6 ){
            kitAverage = 4;
//                        kitRange = 2;
        }else if (size <= 0.7 ){
            kitAverage = 5;
//                        kitRange = 2;
        }else if (size <= 0.8 ){
            kitAverage = 6;
            kitRange = 3;
        }else if (size <= 0.9 ){
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

        if (kitRange<1) {
            kitRange = 1;
        }

        return ThreadLocalRandom.current().nextInt(kitRange)+kitAverage;
    }

    @Override
    protected EnhancedAnimalAbstract createEnhancedChild(Level level, EnhancedAnimalAbstract otherParent) {
        EnhancedRabbit enhancedrabbit = ENHANCED_RABBIT.get().create(this.level);
        Genes babyGenes = new Genes(this.genetics).makeChild(this.getOrSetIsFemale(), otherParent.getOrSetIsFemale(), otherParent.getGenes());
        enhancedrabbit.setGenes(babyGenes);
        enhancedrabbit.setSharedGenes(babyGenes);
        enhancedrabbit.setSireName(otherParent.getCustomName()==null ? "???" : otherParent.getCustomName().getString());
        enhancedrabbit.setDamName(this.getCustomName()==null ? "???" : this.getCustomName().getString());
        enhancedrabbit.setParent(this.getUUID().toString());
        enhancedrabbit.setGrowingAge();
        enhancedrabbit.setBirthTime();
        enhancedrabbit.initilizeAnimalSize();
        enhancedrabbit.setEntityStatus(EntityState.CHILD_STAGE_ONE.toString());
        enhancedrabbit.setMaxCoatLength();
        enhancedrabbit.currentCoatLength = 0;
        enhancedrabbit.setCoatLength(0);
        enhancedrabbit.moveTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), 0.0F);
        return enhancedrabbit;
    }

    protected void createAndSpawnEnhancedChild(Level level) {
        EnhancedRabbit enhancedrabbit = ENHANCED_RABBIT.get().create(this.level);
        Genes babyGenes = new Genes(this.genetics).makeChild(this.getOrSetIsFemale(), this.mateGender, this.mateGenetics);
        defaultCreateAndSpawn(enhancedrabbit, level, babyGenes, -this.getAdultAge());
        enhancedrabbit.setMaxCoatLength();
        enhancedrabbit.currentCoatLength = 0;
        enhancedrabbit.setCoatLength(0);

        this.level.addFreshEntity(enhancedrabbit);
    }

    @Override
    protected boolean canBePregnant() {
        return true;
    }

    @Override
    protected boolean canLactate() {
        return false;
    }

    public class JumpHelperController extends JumpControl {
        private final EnhancedRabbit rabbit;
        private boolean canJump;

        public JumpHelperController(EnhancedRabbit rabbit) {
            super(rabbit);
            this.rabbit = rabbit;
        }

        public boolean getIsJumping() {
            return this.jump;
        }

        public boolean canJump() {
            return this.canJump;
        }

        public void setCanJump(boolean canJumpIn) {
            this.canJump = canJumpIn;
        }

        /**
         * Called to actually make the entity jump if isJumping is true.
         */
        public void tick() {
            if (this.jump) {
                this.rabbit.startJumping();
                this.jump = false;
            }

        }
    }

    public boolean isCarrotEaten() {
        return this.carrotTicks == 0;
    }

    static class MoveHelperController extends MoveControl {
        private final EnhancedRabbit rabbit;
        private double nextJumpSpeed;

        public MoveHelperController(EnhancedRabbit rabbit) {
            super(rabbit);
            this.rabbit = rabbit;
        }

        public void tick() {
            if (this.rabbit.onGround && !this.rabbit.jumping && !((EnhancedRabbit.JumpHelperController)this.rabbit.jumpControl).getIsJumping()) {
                this.rabbit.setMovementSpeed(0.0D);
            } else if (this.hasWanted()) {
                this.rabbit.setMovementSpeed(this.nextJumpSpeed);
            }

            super.tick();
        }

        /**
         * Sets the speed and location to move to
         */
        public void setWantedPosition(double x, double y, double z, double speedIn) {
            if (this.rabbit.isInWater()) {
                speedIn = 1.5D;
            }

            super.setWantedPosition(x, y, z, speedIn);
            if (speedIn > 0.0D) {
                this.nextJumpSpeed = speedIn;
            }

        }
    }

    @Override
    protected boolean shouldDropExperience() {
        int i = random.nextInt(100);
        if (this.getEnhancedAnimalAge()/480 >= i) {
            return true;
        } else {
            return false;
        }
    }

    protected void dropCustomDeathLoot(DamageSource source, int looting, boolean recentlyHitIn) {
        super.dropCustomDeathLoot(source, looting, recentlyHitIn);
        int age = this.getEnhancedAnimalAge();
        float size = getAnimalSize();

        if ((((size-0.3F) * 71.4286F) + 25) > random.nextInt(100)) {
            ItemStack meatStack = new ItemStack(Items.RABBIT, 1 + looting);
            if (size <= 0.65F || age < 48000 || (size < 0.8F && (size-0.65F)/0.0015F < random.nextInt(100))) {
                //small meat
                if (isOnFire()) {
                    meatStack = new ItemStack(ModItems.COOKEDRABBIT_SMALL.get(), 1 + looting);
                } else {
                    meatStack = new ItemStack(ModItems.RAWRABBIT_SMALL.get(), 1 + looting);
                }
            } else if (isOnFire()) {
                meatStack = new ItemStack(Items.COOKED_RABBIT, 1 + looting);
            }

            this.spawnAtLocation(meatStack);
        }

        if (!this.isOnFire() && ((((size-0.3F) * 71.4286F) + 25) > random.nextInt(100))) {
            ItemStack coatStack = new ItemStack(Items.RABBIT_HIDE, 1 + looting);
            if (maxCoatLength != 0 && currentCoatLength >= 1) {
                if (currentCoatLength == 1) {
                    int i = this.random.nextInt(4);
                    if (i==0){
                        coatStack = new ItemStack(Blocks.WHITE_WOOL, 1 + looting);
                    }
                } else if (currentCoatLength == 2) {
                    int i = this.random.nextInt(2);
                    if (i==1){
                        coatStack = new ItemStack(Blocks.WHITE_WOOL, 1 + looting);
                    }
                } else if (currentCoatLength == 3) {
                    int i = this.random.nextInt(4);
                    if (i!=0){
                        coatStack = new ItemStack(Blocks.WHITE_WOOL, 1 + looting);
                    }
                } else if (currentCoatLength == 4) {
                    coatStack = new ItemStack(Blocks.WHITE_WOOL, 1 + looting);
                }
            }
            this.spawnAtLocation(coatStack);
        }

        if (age > 48000 && random.nextInt(20) >= 18-looting) {
            this.spawnAtLocation(Items.RABBIT_FOOT, 1);
        }
    }

    @Override
    @Nullable
    protected ResourceLocation getDefaultLootTable() {

        if (!this.level.isClientSide) {
            if (this.getAnimalSize() <= 0.8F || this.getEnhancedAnimalAge() < 48000) {
                dropMeatType = "rawrabbit_small";
            } else {
                dropMeatType = "rawrabbit";
            }
        }

        return new ResourceLocation(Reference.MODID, "enhanced_rabbit");
    }

    public void lethalGenes(){
        int[] genes = this.genetics.getAutosomalGenes();
        if(genes[34] == 2 && genes[35] == 2) {
            this.remove(RemovalReason.KILLED);
        }
    }

    public String getDropMeatType() {
        return dropMeatType;
    }

    protected SoundEvent getJumpSound() {
        if (!this.isSilent() && this.getBells()) {
            this.playSound(SoundEvents.NOTE_BLOCK_CHIME, 1.75F, 2.5F);
        }
        return SoundEvents.RABBIT_JUMP;
    }

    protected SoundEvent getAmbientSound() {
        if (isAnimalSleeping()) {
            return null;
        }
        return SoundEvents.RABBIT_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn)
    {
        return SoundEvents.RABBIT_HURT;
    }

    protected SoundEvent getDeathSound()
    {
        return SoundEvents.RABBIT_DEATH;
    }

    protected void playStepSound(BlockPos pos, BlockState blockIn) {
        if (!this.isSilent() && this.getBells()) {
            this.playSound(SoundEvents.NOTE_BLOCK_CHIME, 1.5F, 2.5F);
        }
    }

    @Override
    public boolean isShearable(ItemStack item, Level world, BlockPos pos) {
        return !this.level.isClientSide && currentCoatLength >= 1;
    }

    @Override
    public java.util.List<ItemStack> onSheared(Player player, ItemStack item, Level world, BlockPos pos, int fortune) {
        java.util.List<ItemStack> ret = new java.util.ArrayList<>();
        if (!this.level.isClientSide) {
            if (currentCoatLength == 1) {
                int i = this.random.nextInt(4);
                if (i==0){
                    ret.add(new ItemStack(Blocks.WHITE_WOOL));
                }
            } else if (currentCoatLength == 2) {
                int i = this.random.nextInt(2);
                if (i==0){
                    ret.add(new ItemStack(Blocks.WHITE_WOOL));
                }
            } else if (currentCoatLength == 3) {
                int i = this.random.nextInt(4);
                if (i!=0){
                    ret.add(new ItemStack(Blocks.WHITE_WOOL));
                }
            } else if (currentCoatLength == 4) {
                ret.add(new ItemStack(Blocks.WHITE_WOOL));
            }

        }
        currentCoatLength = 0;
        setCoatLength(currentCoatLength);
        return ret;
    }

    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);

        compound.putFloat("CoatLength", this.getCoatLength());
        compound.putBoolean("Pregnant", this.pregnant);
        compound.putInt("Gestation", this.gestationTimer);
    }

    /**
     * (abstract) Protected helper method to read subclass entity assets from NBT.
     */
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);

        currentCoatLength = compound.getInt("CoatLength");
        this.setCoatLength(currentCoatLength);

        this.pregnant = compound.getBoolean("Pregnant");
        this.gestationTimer = compound.getInt("Gestation");

        //resets the max so we don't have to store it
        setMaxCoatLength();

        if (!compound.getString("breed").isEmpty()) {
            int age = this.getEnhancedAnimalAge();
            this.currentCoatLength = age >= this.getAdultAge() ? this.maxCoatLength : (int)(this.maxCoatLength*(((float)age/(float)this.getAdultAge())));
            this.setCoatLength(this.currentCoatLength);
        }
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public RabbitModelData getModelData() {
        return this.rabbitModelData;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void setModelData(AnimalModelData animalModelData) {
        this.rabbitModelData = (RabbitModelData) animalModelData;
    }

    @OnlyIn(Dist.CLIENT)
    public String getTexture() {
        if (this.enhancedAnimalTextureGrouping == null) {
            this.setTexturePaths();
        } else if (this.reload) {
            this.reload = false;
            this.reloadTextures();
        }

        return getCompiledTextures("enhanced_rabbit");
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    protected void reloadTextures() {
        this.texturesIndexes.clear();
        this.enhancedAnimalTextures.clear();
        this.enhancedAnimalTextureGrouping = null;
        this.compiledTexture = null;
        this.colouration.setMelaninColour(-1);
        this.colouration.setPheomelaninColour(-1);
        this.setTexturePaths();
    }

    @OnlyIn(Dist.CLIENT)
    protected void setTexturePaths() {
        if (this.getGenes() != null) {
            calculateRabbitTextures(this, this.getGenes().getAutosomalGenes(), getStringUUID().toCharArray());
        }
    }

    @Override
    protected void setAlphaTexturePaths() {
    }

    @Override
    public void initilizeAnimalSize() {
        int[] genes = this.genetics.getAutosomalGenes();
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

        this.setAnimalSize(size);
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor inWorld, DifficultyInstance difficulty, MobSpawnType spawnReason, @Nullable SpawnGroupData livingdata, @Nullable CompoundTag itemNbt) {
        livingdata = commonInitialSpawnSetup(inWorld, livingdata, getAdultAge(), 30000, 80000, spawnReason);

        setInitialCoat();

        return livingdata;
    }

    @Override
    protected void initializeHealth(EnhancedAnimalAbstract animal, float health) {
//        int[] genes = animal.genetics.getAutosomalGenes();

        health = 3.0F;

        super.initializeHealth(animal, health);
    }

    private void setMaxCoatLength() {
        int[] genes = this.genetics.getAutosomalGenes();
        int angora = 0;

        if ( genes[26] == 2 && genes[27] == 2){
            if (genes[50] == 1 && genes[51] == 1 || genes[50] == 3 && genes[51] == 3){
                angora = 1;
            }else if ( genes[50] == 1 || genes[51] == 1 || genes[50] == 3 || genes[51] == 3){
                angora = 2;
            }else{
                angora = 3;
            }

            if ( genes[52] >= 2 && genes[53] >= 2){
                angora = angora + 1;
                if ( genes[52] == 3 && genes[53] == 3 && angora <= 3){
                    angora = angora + 1;
                }
            }

            if ( genes[54] == 1 || genes[55] == 1 && angora >= 2){
                angora = angora - 1;
                if ( genes[54] == 1 && genes[55] == 1 && angora >= 2){
                    angora = angora - 1;
                }
            }
        }

        this.maxCoatLength = angora;

    }

    @Override
    protected Genes createInitialGenes(LevelAccessor world, BlockPos pos, boolean isDomestic) {
        return new RabbitGeneticsInitialiser().generateNewGenetics(world, pos, isDomestic);
    }

    @Override
    public Genes createInitialBreedGenes(LevelAccessor world, BlockPos pos, String breed) {
        return new RabbitGeneticsInitialiser().generateWithBreed(world, pos, breed);
    }

    public void setInitialCoat() {
        setMaxCoatLength();
        int age = this.getEnhancedAnimalAge();
        this.currentCoatLength = age >= this.getAdultAge() ? this.maxCoatLength : (int)(this.maxCoatLength*(((float)age/(float)this.getAdultAge())));
        setCoatLength(this.currentCoatLength);
    }
}
