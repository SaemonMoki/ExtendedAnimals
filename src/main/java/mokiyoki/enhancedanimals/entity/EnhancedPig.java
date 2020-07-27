package mokiyoki.enhancedanimals.entity;

import mokiyoki.enhancedanimals.ai.general.EnhancedTemptGoal;
import mokiyoki.enhancedanimals.ai.general.pig.EnhancedWaterAvoidingRandomWalkingEatingGoalPig;
import mokiyoki.enhancedanimals.entity.util.GeneticsInitialiser;
import mokiyoki.enhancedanimals.init.ModBlocks;
import mokiyoki.enhancedanimals.init.ModItems;
import mokiyoki.enhancedanimals.config.EanimodCommonConfig;
import mokiyoki.enhancedanimals.util.Genes;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.stats.Stats;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import static mokiyoki.enhancedanimals.util.handlers.EventRegistry.ENHANCED_PIG;

public class EnhancedPig extends EnhancedAnimalRideableAbstract implements EnhancedAnimal {

    //avalible UUID spaces : [ S X 2 3 4 5 6 7 - 8 9 10 11 - 12 13 14 15 - 16 17 18 19 - 20 21 22 23 24 25 26 27 28 29 30 31 ]

    private static final String[] PIG_TEXTURES_SKINBASE = new String[] {
            "", "skin_pink.png", "skin_grey.png", "skin_black.png"
    };

    private static final String[] PIG_TEXTURES_SKINMARKINGS_SPOTS = new String[] {
            "", "skin_spots.png", ""
    };

    private static final String[] PIG_TEXTURES_SKINMARKINGS_BELTED = new String[] {
            "", "skin_pink.png", "skin_belt.png", "skin_patchy.png"
    };

    private static final String[] PIG_TEXTURES_SKINMARKINGS_BERKSHIRE = new String[] {
            "", "skin_tux.png", "skin_berkshire.png"
    };

    private static final String[] PIG_TEXTURES_COATRED = new String[] {
            "pigbase.png", "solid_white.png", "solid_milk.png", "solid_cream.png", "solid_carmel.png", "solid_orange.png", "solid_ginger.png", "solid_red.png", "solid_brown.png"
    };
    private static final String[] PIG_TEXTURES_COATBLACK = new String[] {
            "red", "black_solid.png", "black_wildtype.png", "black_brindle.png", "black_brindlesmall.png",
                   "choc_solid.png", "choc_wildtype.png", "choc_brindle.png", "choc_brindlesmall.png",
                   "lightchoc_wildtype.png"
    };

    private static final String[] PIG_TEXTURES_SPOT_SPOTS = new String[] {
            //discontinued genes
            "", "spot_spots.png", "spot_roanspots.png"
    };

    private static final String[] PIG_TEXTURES_SPOT_BELTED = new String[] {
            "", "spot_white.png", "spot_belt.png", "spot_patchy.png", "spot_roan.png", "spot_roanbelted.png"
    };

    private static final String[] PIG_TEXTURES_SPOT_BERKSHIRE = new String[] {
            "", "spot_tux.png", "spot_berkshire.png", "spot_extended_berkshire"
    };

    private static final String[] PIG_TEXTURES_COAT = new String[] {
            "coat_normal.png", "coat_wooly.png"
    };

    private static final String[] PIG_TEXTURES_EYES = new String[] {
            "eyes_black.png", "eyes_brown.png", "eyes_blue.png"
    };

    private static final String[] PIG_TEXTURES_HOOVES = new String[] {
            "hooves_black.png", "hooves_brown.png", "hooves_pink.png"
    };

    private static final String[] PIG_TEXTURES_TUSKS = new String[] {
            "", "tusks.png"
    };

    private static final String[] PIG_TEXTURES_ALPHA = new String[] {
            "bald", "sparse.png", "medium.png", "furry.png", "wooly.png"
    };

    private static final Ingredient TEMPTATION_ITEMS = Ingredient.fromItems(Blocks.MELON, Blocks.PUMPKIN, Blocks.GRASS, Blocks.HAY_BLOCK, Items.CARROT, Items.POTATO, Items.WHEAT, Items.BEETROOT, Items.ROTTEN_FLESH, Items.APPLE, Items.COOKED_CHICKEN, Items.COOKED_BEEF, Items.COOKED_MUTTON, Items.COOKED_RABBIT, Items.COOKED_SALMON, Items.COOKED_COD, Blocks.BROWN_MUSHROOM, Blocks.DARK_OAK_SAPLING, Blocks.OAK_SAPLING, Items.MILK_BUCKET, Items.BREAD, ModItems.COOKEDCHICKEN_DARK, ModItems.COOKEDCHICKEN_DARKBIG, ModItems.COOKEDCHICKEN_DARKSMALL, ModItems.COOKEDCHICKEN_PALE, ModItems.COOKEDCHICKEN_PALESMALL, ModItems.COOKEDRABBIT_SMALL);
    private static final Ingredient BREED_ITEMS = Ingredient.fromItems(Items.CARROT, Items.BEETROOT, Items.POTATO);

    private static final int SEXLINKED_GENES_LENGTH = 2;
    private static final int GENES_LENGTH = 60;

    private UUID angerTargetUUID;
    private int angerLevel;

    private EnhancedWaterAvoidingRandomWalkingEatingGoalPig wanderEatingGoal;

//    private boolean boosting;
//    private int boostTime;
//    private int totalBoostTime;

    public EnhancedPig(EntityType<? extends EnhancedPig> entityType, World worldIn) {
        super(entityType, worldIn, SEXLINKED_GENES_LENGTH, GENES_LENGTH, TEMPTATION_ITEMS, BREED_ITEMS, createFoodMap(), true);
        this.setPigSize();
    }

    private static Map<Item, Integer> createFoodMap() {
        return new HashMap() {{
            put(new ItemStack(Blocks.MELON).getItem(), 10000);
            put(new ItemStack(Blocks.PUMPKIN).getItem(), 10000);
            put(new ItemStack(Items.TALL_GRASS).getItem(), 6000);
            put(new ItemStack(Items.GRASS).getItem(), 3000);
            put(new ItemStack(Items.VINE).getItem(), 3000);
            put(new ItemStack(Items.COOKED_BEEF).getItem(), 3000);
            put(new ItemStack(ModItems.COOKEDCHICKEN_DARKBIG).getItem(), 3000);
            put(new ItemStack(ModItems.COOKEDCHICKEN_DARK).getItem(), 2000);
            put(new ItemStack(ModItems.COOKEDCHICKEN_DARKSMALL).getItem(), 1000);
            put(new ItemStack(Items.COOKED_CHICKEN).getItem(), 3000);
            put(new ItemStack(ModItems.COOKEDCHICKEN_PALE).getItem(), 2000);
            put(new ItemStack(ModItems.COOKEDCHICKEN_PALESMALL).getItem(), 1000);
            put(new ItemStack(Items.COOKED_COD).getItem(), 1500);
            put(new ItemStack(Items.COOKED_MUTTON).getItem(), 3000);
            put(new ItemStack(Items.COOKED_RABBIT).getItem(), 1500);
            put(new ItemStack(ModItems.COOKEDRABBIT_SMALL).getItem(), 750);
            put(new ItemStack(Items.COOKED_SALMON).getItem(), 3000);
            put(new ItemStack(Blocks.HAY_BLOCK).getItem(), 54000);
            put(new ItemStack(Blocks.OAK_LEAVES).getItem(), 1000);
            put(new ItemStack(Blocks.DARK_OAK_LEAVES).getItem(), 1000);
            put(new ItemStack(Items.CARROT).getItem(), 3000);
            put(new ItemStack(Items.POTATO).getItem(), 3000);
            put(new ItemStack(Items.BEETROOT).getItem(), 3000);
            put(new ItemStack(Items.WHEAT).getItem(), 6000);
            put(new ItemStack(Items.SUGAR).getItem(), 1500);
            put(new ItemStack(Items.APPLE).getItem(), 1500);
            put(new ItemStack(Items.BREAD).getItem(), 18000);
            put(new ItemStack(Items.WHEAT_SEEDS).getItem(), 100);
            put(new ItemStack(Items.MELON_SEEDS).getItem(), 100);
            put(new ItemStack(Items.PUMPKIN_SEEDS).getItem(), 100);
            put(new ItemStack(Items.BEETROOT_SEEDS).getItem(), 100);
            put(new ItemStack(Items.SWEET_BERRIES).getItem(), 100);
            put(new ItemStack(Items.ROTTEN_FLESH).getItem(), 500);
            put(new ItemStack(Items.BROWN_MUSHROOM).getItem(), 1000);
            put(new ItemStack(Items.OAK_SAPLING).getItem(), 1000);
            put(new ItemStack(Items.DARK_OAK_SAPLING).getItem(), 1000);
            put(new ItemStack(Items.MILK_BUCKET).getItem(), 1500);
            put(new ItemStack(ModBlocks.UNBOUNDHAY_BLOCK).getItem(), 54000);
        }};
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        wanderEatingGoal = new EnhancedWaterAvoidingRandomWalkingEatingGoalPig(this, 1.0D, 7, 0.001F, 120, 2, 20);
        this.goalSelector.addGoal(5, new MeleeAttackGoal(this, 1.0D, true));
//        this.goalSelector.addGoal(1, new PanicGoal(this, 1.25D));
        this.goalSelector.addGoal(5, wanderEatingGoal);
        this.goalSelector.addGoal(4, new EnhancedTemptGoal(this, 1.2D, false, TEMPTATION_ITEMS));
        this.targetSelector.addGoal(2, new EnhancedPig.TargetAggressorGoal(this));
        this.targetSelector.addGoal(1, new EnhancedPig.HurtByAggressorGoal(this));
    }

    @Override
    public boolean canHaveBridle() {
        return false;
    }

    @Override
    protected void updateAITasks() {

        LivingEntity livingentity = this.getRevengeTarget();
        if (this.isAngry()) {

            --this.angerLevel;
            LivingEntity livingentity1 = livingentity != null ? livingentity : this.getAttackTarget();
            if (!this.isAngry() && livingentity1 != null) {
                if (!this.canEntityBeSeen(livingentity1)) {
                    this.setRevengeTarget((LivingEntity)null);
                    this.setAttackTarget((LivingEntity)null);
                } else {
                    this.angerLevel = this.angerAmount();
                }
            }
        }

        if (this.isAngry() && this.angerTargetUUID != null && livingentity == null) {
            PlayerEntity playerentity = this.world.getPlayerByUuid(this.angerTargetUUID);
            this.setRevengeTarget(playerentity);
            this.attackingPlayer = playerentity;
            this.recentlyHit = this.getRevengeTimer();
        }
        this.animalEatingTimer = this.wanderEatingGoal.getEatingGrassTimer();
        super.updateAITasks();
    }

    protected void registerData() {
        super.registerData();
    }

    protected String getSpecies() {
        return "Pig";
    }

    protected int getAdultAge() { return 60000;}

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
//            return this.saddlePig(itemStack, entityPlayer, this);
//        }

        return super.processInteract(entityPlayer, hand);
    }

//    public boolean saddlePig(ItemStack stack, PlayerEntity playerIn, LivingEntity target) {
//        EnhancedPig pigentity = (EnhancedPig)target;
//        if (pigentity.isAlive() && !pigentity.getSaddled() && !pigentity.isChild()) {
//            pigentity.setSaddled(true);
//            pigentity.world.playSound(playerIn, pigentity.getPosX(), pigentity.getPosY(), pigentity.getPosZ(), SoundEvents.ENTITY_PIG_SADDLE, SoundCategory.NEUTRAL, 0.5F, 1.0F);
//            stack.shrink(1);
//            return true;
//        }
//
//        return false;
//    }

    protected void dropInventory() {
        super.dropInventory();
//        if (this.getSaddled()) {
//            this.entityDropItem(Items.SADDLE);
//        }
    }

//    public boolean getSaddled() {
//        return this.dataManager.get(SADDLED);
//    }
//
//    public void setSaddled(boolean saddled) {
//        if (saddled) {
//            this.dataManager.set(SADDLED, true);
//        } else {
//            this.dataManager.set(SADDLED, false);
//        }
//    }

//    public void travel(Vec3d p_213352_1_) {
//        if (this.isAlive()) {
//            Entity entity = this.getPassengers().isEmpty() ? null : this.getPassengers().get(0);
//            if (this.isBeingRidden() && this.canBeSteered()) {
//                this.rotationYaw = entity.rotationYaw;
//                this.prevRotationYaw = this.rotationYaw;
//                this.rotationPitch = entity.rotationPitch * 0.5F;
//                this.setRotation(this.rotationYaw, this.rotationPitch);
//                this.renderYawOffset = this.rotationYaw;
//                this.rotationYawHead = this.rotationYaw;
//                this.stepHeight = 1.0F;
//                this.jumpMovementFactor = this.getAIMoveSpeed() * 0.1F;
//                if (this.boosting && this.boostTime++ > this.totalBoostTime) {
//                    this.boosting = false;
//                }
//
//                if (this.canPassengerSteer()) {
//                    float f = (float)this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getValue() * 0.225F;
//                    if (this.boosting) {
//                        f += f * 1.15F * MathHelper.sin((float)this.boostTime / (float)this.totalBoostTime * (float)Math.PI);
//                    }
//
//                    this.setAIMoveSpeed(f);
//                    super.travel(new Vec3d(0.0D, 0.0D, 1.0D));
//                    this.newPosRotationIncrements = 0;
//                } else {
//                    this.setMotion(Vec3d.ZERO);
//                }
//
//                this.prevLimbSwingAmount = this.limbSwingAmount;
//                double d1 = this.getPosX() - this.prevPosX;
//                double d0 = this.getPosZ() - this.prevPosZ;
//                float f1 = MathHelper.sqrt(d1 * d1 + d0 * d0) * 4.0F;
//                if (f1 > 1.0F) {
//                    f1 = 1.0F;
//                }
//
//                this.limbSwingAmount += (f1 - this.limbSwingAmount) * 0.4F;
//                this.limbSwing += this.limbSwingAmount;
//            } else {
//                this.stepHeight = 0.5F;
//                this.jumpMovementFactor = 0.02F;
//                super.travel(p_213352_1_);
//            }
//        }
//    }
//
//    public boolean boost() {
//        if (this.boosting) {
//            return false;
//        } else {
//            this.boosting = true;
//            this.boostTime = 0;
//            this.totalBoostTime = this.getRNG().nextInt(841) + 140;
//            this.getDataManager().set(BOOST_TIME, this.totalBoostTime);
//            return true;
//        }
//    }

    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_PIG_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.ENTITY_PIG_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_PIG_DEATH;
    }

    protected void playStepSound(BlockPos pos, BlockState blockIn) {
        this.playSound(SoundEvents.ENTITY_PIG_STEP, 0.15F, 1.0F);
        if (!this.isSilent() && this.getBells()) {
            this.playSound(SoundEvents.BLOCK_NOTE_BLOCK_CHIME, 1.5F, 0.5F);
        }
    }

    protected float getSoundVolume() {
        return 0.4F;
    }

    protected void registerAttributes() {
        super.registerAttributes();
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(10.0D);
        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
        char[] uuidArry = getCachedUniqueIdString().toCharArray();
        double tusks = 2.0D;
        if (!isChild()) {
            if ((Character.isLetter(uuidArry[0]) || uuidArry[0] - 48 >= 8)) {
                //tusks if "male"
                tusks = 3.0D;
            }
        }
        this.getAttributes().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(tusks);
    }

    public void livingTick() {
        super.livingTick();
    }

    @Override
    protected void runExtraIdleTimeTick() {
    }

    @Override
    protected int gestationConfig() {
        return EanimodCommonConfig.COMMON.gestationDaysPig.get();
    }

    protected  void incrementHunger() {
        if(sleeping) {
            hunger = hunger + 1.0F;
        } else {
            hunger = hunger + 2.0F;
        }
    }

    @Override
    protected int getNumberOfChildren() {
        int[] genes = this.genetics.getAutosomalGenes();
        int pigletAverage = 11;
        int pigletRange = 4;
        int age = getAge();

        if (genes[58] == 1 || genes[59] == 1) {
            pigletAverage = 4;
            pigletRange = 3;
        } else if (genes[58] == 2 && genes[59] == 2) {
            pigletAverage = 8;
            pigletRange = 3;
        } else if (genes[58] == 2 || genes[59] == 2) {
            pigletAverage = 8;
        }

        if (age < 108000) {
            if (age > 100000) {
                pigletAverage = (pigletAverage*5)/6;
            } else if (age > 92000) {
                pigletAverage = (pigletAverage*4)/6;
            } else if (age > 84000) {
                pigletAverage = (pigletAverage*3)/6;
            } else if (age > 76000) {
                pigletAverage = (pigletAverage*2)/6;
            } else if (age > 68000) {
                pigletAverage = pigletAverage/6;
            } else {
                pigletAverage = pigletAverage/12;
            }
        }

        return (ThreadLocalRandom.current().nextInt(pigletRange*2) - (pigletRange)) + pigletAverage;
    }

    protected void createAndSpawnEnhancedChild(World inWorld) {
        EnhancedPig enhancedpig = ENHANCED_PIG.create(this.world);
//        int[] babyGenes = getPigletGenes(this.mitosisGenes, this.mateMitosisGenes);
        Genes babyGenes = new Genes(this.genetics).makeChild(this.mateGenetics);
        defaultCreateAndSpawn(enhancedpig, inWorld, babyGenes, -60000);

        this.world.addEntity(enhancedpig);
    }

    @Override
    protected boolean canBePregnant() {
        return true;
    }

    @Override
    protected boolean canLactate() {
        return false;
    }


    static class HurtByAggressorGoal extends HurtByTargetGoal {
        public HurtByAggressorGoal(EnhancedPig entity) {
            super(entity);
            this.setCallsForHelp(new Class[]{EnhancedPig.class});
        }

        protected void setAttackTarget(MobEntity mobIn, LivingEntity targetIn) {
            if (mobIn instanceof EnhancedPig && this.goalOwner.canEntityBeSeen(targetIn) && ((EnhancedPig)mobIn).becomeAngryAt(targetIn)) {
                mobIn.setAttackTarget(targetIn);
            }

        }
    }

    static class TargetAggressorGoal extends NearestAttackableTargetGoal<PlayerEntity> {
        public TargetAggressorGoal(EnhancedPig entity) {
            super(entity, PlayerEntity.class, true);
        }

        /**
         * Returns whether the EntityAIBase should begin execution.
         */
        public boolean shouldExecute() {
            return ((EnhancedPig)this.goalOwner).isAngry() && super.shouldExecute();
        }
    }

    @Override
    public boolean attackEntityAsMob(Entity entityIn) {
        boolean flag = entityIn.attackEntityFrom(DamageSource.causeMobDamage(this), (float)((int)this.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getValue()));
        if (flag) {
            this.applyEnchantments(this, entityIn);
        }

        return flag;
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        if (this.isInvulnerableTo(source)) {
            return false;
        } else {
            Entity entity = source.getTrueSource();
            if (entity instanceof PlayerEntity && !((PlayerEntity)entity).isCreative() && this.canEntityBeSeen(entity)) {
                this.becomeAngryAt(entity);
            }

            return super.attackEntityFrom(source, amount);
        }
    }

    private boolean becomeAngryAt(Entity entity) {
        this.angerLevel = this.angerAmount();
//        this.randomSoundDelay = this.rand.nextInt(40);
        if (entity instanceof LivingEntity) {
            this.setRevengeTarget((LivingEntity)entity);
        }

        return true;
    }

    @Override
    public void setRevengeTarget(@Nullable LivingEntity livingBase) {
        super.setRevengeTarget(livingBase);
        if (livingBase != null) {
            this.angerTargetUUID = livingBase.getUniqueID();
        }

    }

    private int angerAmount() {
        return 400 + this.rand.nextInt(400);
    }

    private boolean isAngry() {
        return this.angerLevel > 0;
    }

    private void setPigSize() {
        int[] genes = this.genetics.getAutosomalGenes();
        float size = 0.4F;

            // [44/45] (1-3) potbelly dwarfism [wildtype, dwarfStrong, dwarfWeak]
            // [46/47] (1-2) potbelly dwarfism2 [wildtype, dwarf]
            // [48/49] (1-15) size genes reducer [wildtype, smaller smaller smallest...]
            // [50/51] (1-15) size genes adder [wildtype, bigger bigger biggest...]
            // [52/53] (1-3) size genes varient1 [wildtype, smaller, smallest]
            // [54/55] (1-3) size genes varient2 [wildtype, larger, largest]

        size = size - (genes[48] - 1)*0.0125F;
        size = size - (genes[49] - 1)*0.0125F;
        size = size + (genes[50] - 1)*0.0125F;
        size = size + (genes[51] - 1)*0.0125F;

        if (genes[44] != 1 && genes[45] != 1) {
            if (genes[44] == 2 || genes[45] == 2) {
                //smaller rounder
                size = size * 0.9F;
            } else {
                //smaller roundest
                size = size * 0.8F;
            }
        }

        if (genes[46] == 2 && genes[47] == 2) {
            //smaller rounder
            size = size * 0.9F;
        }

        if (genes[52] == 2 || genes[53] == 2) {
            size = size * 0.975F;
        } else if (genes[52] == 3 || genes[53] == 3) {
            size = size * 0.925F;
        }

        if (genes[54] == 2 || genes[55] == 2) {
            size = size * 1.025F;
        } else if (genes[54] == 3 || genes[55] == 3) {
            size = size * 1.075F;
        }

        if (size > 0.8F) {
            size = 0.8F;
        }

        size = size + 0.7F;

        //        0.7F <= size <= 1.5F
        this.setAnimalSize(size);
    }

    @Override
    protected boolean canDropLoot() { return true; }

    protected void dropSpecialItems(DamageSource source, int looting, boolean recentlyHitIn) {
        super.dropSpecialItems(source, looting, recentlyHitIn);
        int[] genes = this.genetics.getAutosomalGenes();
        int size = (int)((this.getAnimalSize()-0.7F)*10);
        int age = this.getAge();
        int meatDrop;
        int meatChanceMod;

        // max of 4
        meatDrop = size/2;

        if (genes[45] != 1 && genes[46] != 1) {
            meatChanceMod = (genes[45] + genes[46]) - 4;
            if (meatChanceMod > 4) {
                if (meatChanceMod == 8) {
                    //100% chance to + 1
                    if (this.rand.nextInt(4) == 0) {
                        // 25% chance to + 1
                        meatDrop = meatDrop + 2;
                    } else {
                        meatDrop++;
                    }
                } else if (meatChanceMod == 7) {
                    if (this.rand.nextInt(4) != 0) {
                        //75% chance to + 1
                        meatDrop++;
                    }
                    if (this.rand.nextInt(4) == 0) {
                        //25% chance to + 1
                        meatDrop++;
                    }
                } else if (meatChanceMod == 6) {
                    // 50% chance to + 1
                    if (this.rand.nextInt(2) == 0) {
                        meatDrop++;
                    }
                } else {
                    // 25% chance to + 1
                    if (this.rand.nextInt(4) == 0) {
                        meatDrop++;
                    }
                }
            } else if (meatChanceMod < 4){
                if (meatChanceMod == 3) {
                    // 25% chance to - 1
                    if (this.rand.nextInt(4) == 0) {
                        meatDrop--;
                    }
                } else if (meatChanceMod == 2) {
                    // 50% chance to - 1
                    if (this.rand.nextInt(2) == 0) {
                        meatDrop--;
                    }
                } else if (meatChanceMod == 1) {
                    if (this.rand.nextInt(4) != 0) {
                        //75% chance to - 1
                        meatDrop--;
                    }
                    if (this.rand.nextInt(4) == 0) {
                        //25% chance to - 1
                        meatDrop--;
                    }
                } else {
                    //100% chance to - 1
                    meatDrop--;
                    if (this.rand.nextInt(4) == 0) {
                        // 25% chance to - 1
                        meatDrop--;
                    }
                }
            }
        }

        if (meatDrop <= 0) {
            meatDrop = 1;
        }

        if (age < 108000) {
            if (age >= 90000) {
                meatDrop = meatDrop - 1;
                meatChanceMod = (age-90000)/180;
            } else if (age >= 72000) {
                meatDrop = meatDrop - 2;
                meatChanceMod = (age-72000)/180;
            } else if (age >= 54000) {
                meatDrop = meatDrop - 3;
                meatChanceMod = (age-54000)/180;
            } else if (age >= 36000) {
                meatDrop = meatDrop - 4;
                meatChanceMod = (age-36000)/180;
            } else if (age >= 18000) {
                meatDrop = meatDrop - 5;
                meatChanceMod = (age-18000)/180;
            } else {
                meatDrop = meatDrop - 6;
                meatChanceMod = age/180;
            }

            int i = this.rand.nextInt(100);
            if (meatChanceMod > i) {
                meatDrop++;
            }
        }

        if (meatDrop > 6) {
            meatDrop = 6;
        } else if (meatDrop < 0) {
            meatDrop = 0;
        }

        if (this.isBurning()){
            ItemStack cookedPorkStack = new ItemStack(Items.COOKED_PORKCHOP, meatDrop);
            this.entityDropItem(cookedPorkStack);
        }else {
            ItemStack porkStack = new ItemStack(Items.PORKCHOP, meatDrop);
            this.entityDropItem(porkStack);
        }
    }

    public void lethalGenes(){

        //TODO lethal genes go here
        
    }

    public void eatGrassBonus() {
//        if (this.isChild()) {
//            this.addGrowth(60);
//        }
    }

    @OnlyIn(Dist.CLIENT)
    public String getPigTexture() {
        if (this.enhancedAnimalTextures.isEmpty()) {
            this.setTexturePaths();
        }

        return getCompiledTextures("enhanced_pig");
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    protected void setTexturePaths() {
        int[] genesForText = getSharedGenes().getAutosomalGenes();
        if (genesForText != null) {
            boolean agouti = false;
            int spotMod = 0;
            int eyes = 0;
            int red = 5;
            int black = 0;
            int spot = 0;
            int belt = 0;
            int berk = 0;
            int coat = 0;
            int skin = 0;
            boolean tusks = false;

            char[] uuidArry = getCachedUniqueIdString().toCharArray();

            if (genesForText[0] == 1 || genesForText[1] == 1){
                //solid black
                black = 1;
            }else if (genesForText[0] == 2 || genesForText[1] == 2){
                agouti = true;
                black = 2;
            }else if (genesForText[0] == 3 || genesForText[1] == 3){
                //red with spots
                if (genesForText[0] == 3 && genesForText[1] == 3){
                    //big spots
                    black = 3;
                }else{
                    //small spots
                    black = 4;
                }
            }

            if (agouti){
                if (genesForText[2] == 1 || genesForText[3] == 1){
                    //shaded black
                    red = 8;
                }else if (genesForText[2] == 2 || genesForText[3] == 2){
                    //shaded brown
                    black = 6;
                }else if (genesForText[2] == 4 && genesForText[3] == 4) {
                    //recessive black
                    black = 1;
                }
            }

            if (genesForText[4] == 1 || genesForText[5] == 1){
                // reduce red in coat colour
                red = red - 2;
            }

            if (genesForText[6] == 2 && genesForText[7] == 2){
                //black turns to dark chocolate
                //reduce red in coat slightly
                if (black != 6) {
                    black = black + 4;
                }else{
                    black = 9;
                }
                red = red - 1;

            }

//TODO change this one

//            if (genesForText[8] == 1 || genesForText[9] == 1){
//                //turns black to blue/roan blue
//
//            }

            if (genesForText[10] != 1 && genesForText[11] != 1) {
                if (genesForText[10] == 2 || genesForText[11] == 2){
                    //spotted
                    spot = 1;
                } else {
                    //roan spotted
                    spot = 2;
                }
            }


            if (genesForText[12] == 1 || genesForText[13] == 1){
                //dominant white
                eyes = 2;
                belt = 1;
            }else if (genesForText[12] == 2 || genesForText[13] == 2){
                //belted
                belt = 2;
            } else if (genesForText[12] == 4 || genesForText[13] == 4) {
                //patchy
            } else if (genesForText[12] == 5 || genesForText[13] == 5) {
                //roan
            }

            if (genesForText[14] != 1 && genesForText[15] != 1){
                if (genesForText[14] == 2 || genesForText[15] == 2){
                    //tuxedo
                    berk = 1;
                }else{
                    //berkshire
                    berk = 2;
                }
            }

            //TODO make textures for this
//            if (genesForText[16] == 1 || genesForText[17] == 1){
//                spotMod = 2;
//            }else if (genesForText[16] == 2 || genesForText[17] == 2){
//                spotMod = 1;
//            }

            if (belt == 1) {
                skin = 1;
            } else if (black == 1 || black == 2) {
                skin = 3;
            }else{
                skin = 2;
            }

//            if (genesForText[36] != 1 && genesForText[37] != 1) {
//                if ((genesForText[34] == 1 || genesForText[35] == 1) && (genesForText[34] != 3 && genesForText[35] != 3)) {
//                    //furry
//                    skin = skin * 4;
//                }else if (genesForText[34] == 2 || genesForText[35] == 2) {
//                    //normal
//                    skin = skin * 3;
//                }else{
//                    //sparse
//                    skin = skin * 2;
//                }
//            }
//
//            if ((genesForText[38] == 1 || genesForText[39] == 1) && skin <= 12) {
//                skin = skin + 3;
//            }

            if (genesForText[36] != 1 && genesForText[37] != 1) {
                if (genesForText[38] == 1 || genesForText[39] == 1) {
                    //wooly
                    coat = 1;
                }
            }

            if (!isChild()) {
                if ((Character.isLetter(uuidArry[0]) || uuidArry[0] - 48 >= 8)) {
                    //tusks if "male"
                    tusks = true;
                }
            }

            this.enhancedAnimalTextures.add(PIG_TEXTURES_SKINBASE[skin]);
            this.texturesIndexes.add(String.valueOf(skin));
            if (spot == 1) {
                this.enhancedAnimalTextures.add(PIG_TEXTURES_SKINMARKINGS_SPOTS[spot]);
                this.texturesIndexes.add(String.valueOf(spot));
            }
            if (belt != 0) {
                this.enhancedAnimalTextures.add(PIG_TEXTURES_SKINMARKINGS_BELTED[belt]);
                this.texturesIndexes.add(String.valueOf(belt));
            }
            if (berk != 0) {
                this.enhancedAnimalTextures.add(PIG_TEXTURES_SKINMARKINGS_BERKSHIRE[berk]);
                this.texturesIndexes.add(String.valueOf(berk));
            }
            if (genesForText[36] != 1 && genesForText[37] != 1) {
            this.enhancedAnimalTextures.add("alpha_group_start");
                this.enhancedAnimalTextures.add(PIG_TEXTURES_COATRED[red]);
                this.texturesIndexes.add(String.valueOf(red));
                if (black != 0) {
                    this.enhancedAnimalTextures.add(PIG_TEXTURES_COATBLACK[black]);
                    this.texturesIndexes.add(String.valueOf(black));
                }
                if (spot != 0) {
                    this.enhancedAnimalTextures.add(PIG_TEXTURES_SPOT_SPOTS[spot]);
                    this.texturesIndexes.add(String.valueOf(spot));
                }
                if (belt != 0) {
                    this.enhancedAnimalTextures.add(PIG_TEXTURES_SPOT_BELTED[belt]);
                    this.texturesIndexes.add(String.valueOf(belt));
                }
                if (berk != 0) {
                    this.enhancedAnimalTextures.add(PIG_TEXTURES_SPOT_BERKSHIRE[berk]);
                    this.texturesIndexes.add(String.valueOf(berk));
                }
                this.enhancedAnimalTextures.add(PIG_TEXTURES_COAT[coat]);
                this.texturesIndexes.add(String.valueOf(coat));
            this.enhancedAnimalTextures.add("alpha_group_end");
            }
            this.enhancedAnimalTextures.add(PIG_TEXTURES_EYES[eyes]);
            this.texturesIndexes.add(String.valueOf(eyes));
            this.enhancedAnimalTextures.add(PIG_TEXTURES_HOOVES[0]);
            this.texturesIndexes.add(String.valueOf(0));
            if (tusks){
                this.enhancedAnimalTextures.add(PIG_TEXTURES_TUSKS[1]);
                this.texturesIndexes.add(String.valueOf(1));
            }
        }
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    protected void setAlphaTexturePaths() {
        int[] genesForText = getSharedGenes().getAutosomalGenes();
        if (genesForText != null) {
            int coat = 0;

            if (genesForText[36] != 1 && genesForText[37] != 1) {
                if ((genesForText[34] == 1 || genesForText[35] == 1) && (genesForText[34] != 3 && genesForText[35] != 3)) {
                    //furry
                    coat = 3;
                }else if (genesForText[34] == 2 || genesForText[35] == 2) {
                    //normal
                    coat = 2;
                }else{
                    //sparse
                    coat = 1;
                }

                if (genesForText[38] == 1 || genesForText[39] == 1) {
                    coat = coat + 1;
                }
            }

            //todo do the alpha textures

            if (coat != 0) {
                this.enhancedAnimalAlphaTextures.add(PIG_TEXTURES_ALPHA[coat]);
            }

        }
    }
    
    //TODO put item interactable stuff here like saddling pigs


    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);

        compound.putShort("Anger", (short)this.angerLevel);
        if (this.angerTargetUUID != null) {
            compound.putString("HurtBy", this.angerTargetUUID.toString());
        } else {
            compound.putString("HurtBy", "");
        }

//        compound.putBoolean("Saddle", this.getSaddled());
    }

    /**
     * (abstract) Protected helper method to read subclass entity assets from NBT.
     */
    public void readAdditional(CompoundNBT compound) {

        super.readAdditional(compound);

        this.angerLevel = compound.getShort("Anger");
        String s = compound.getString("HurtBy");
        if (!s.isEmpty()) {
            this.angerTargetUUID = UUID.fromString(s);
            PlayerEntity playerentity = this.world.getPlayerByUuid(this.angerTargetUUID);
            this.setRevengeTarget(playerentity);
            if (playerentity != null) {
                this.attackingPlayer = playerentity;
                this.recentlyHit = this.getRevengeTimer();
            }
        }
    }

    protected void initilizeAnimalSize() {
        setPigSize();
    }

    @Nullable
    @Override
    public ILivingEntityData onInitialSpawn(IWorld inWorld, DifficultyInstance difficulty, SpawnReason spawnReason, @Nullable ILivingEntityData livingdata, @Nullable CompoundNBT itemNbt) {
        return commonInitialSpawnSetup(inWorld, livingdata, 60000, 30000, 80000);
    }

//    @Override
//    protected int[] createInitialSpawnChildGenes(int[] spawnGenes1, int[] spawnGenes2, int[] mitosis, int[] mateMitosis) {
//        return replaceGenes(getPigletGenes(mitosis, mateMitosis), spawnGenes1);
//    }

    private int[] replaceGenes(int[] resultGenes, int[] groupGenes) {
        resultGenes[20] = groupGenes[20];
        resultGenes[21] = groupGenes[21];
        resultGenes[22] = groupGenes[22];
        resultGenes[23] = groupGenes[23];
        resultGenes[24] = groupGenes[24];
        resultGenes[25] = groupGenes[25];
        resultGenes[26] = groupGenes[26];
        resultGenes[27] = groupGenes[27];
        resultGenes[28] = groupGenes[28];
        resultGenes[29] = groupGenes[29];
        resultGenes[30] = groupGenes[30];
        resultGenes[31] = groupGenes[31];

        return resultGenes;
    }

    @Override
    protected Genes createInitialGenes(IWorld world, BlockPos pos) {
        return new GeneticsInitialiser.PigGeneticsInitialiser().generateNewPigGenetics(world, pos);
    }

//    @Override
//    protected int[] createInitialGenes(IWorld inWorld) {
//        int[] initialGenes = new int[GENES_LENGTH];
//        //TODO create biome WTC variable [hot and dry biomes, hot and wet biomes, cold biomes] WTC is all others
//        int wildType = 2;
//        Biome biome = inWorld.getBiome(new BlockPos(this));
//
//        if (biome.getCategory().equals(Biome.Category.PLAINS))
//        {
//            wildType = 1;
//        }
//
//
//        if (false) {
//            return new int[] {4,3,3,3,2,2,1,2,1,1,1,1,3,3,1,1,3,3,1,1,12,27,5,31,20,28,19,20,14,39,3,28,2,2,2,2,2,2,2,2,1,1,2,1};
//        } else {
//
//            //Extension [ Dom.Black, Wildtype+, brindle, red ]
//            if (ThreadLocalRandom.current().nextInt(100) > WTC) {
//                initialGenes[0] = (ThreadLocalRandom.current().nextInt(4) + 1);
//
//            } else {
//                initialGenes[0] = (2);
//            }
//            if (ThreadLocalRandom.current().nextInt(100) > WTC) {
//                initialGenes[1] = (ThreadLocalRandom.current().nextInt(4) + 1);
//
//            } else {
//                initialGenes[1] = (2);
//            }
//
//            //Agouti [ Black Enhancer, brown, wildtype? ]
//            if (ThreadLocalRandom.current().nextInt(100) > WTC) {
//                initialGenes[2] = (ThreadLocalRandom.current().nextInt(1) + 1);
//
//            } else {
//                initialGenes[2] = (3);
//            }
//            if (ThreadLocalRandom.current().nextInt(100) > WTC) {
//                initialGenes[3] = (ThreadLocalRandom.current().nextInt(1) + 1);
//
//            } else {
//                initialGenes[3] = (3);
//            }
//
//            //Chinchilla [ chinchilla, wildtype ]
//            if (ThreadLocalRandom.current().nextInt(100) > WTC) {
//                initialGenes[4] = (ThreadLocalRandom.current().nextInt(2) + 1);
//
//            } else {
//                initialGenes[4] = (2);
//            }
//            if (ThreadLocalRandom.current().nextInt(100) > WTC) {
//                initialGenes[5] = (ThreadLocalRandom.current().nextInt(2) + 1);
//
//            } else {
//                initialGenes[5] = (2);
//            }
//
//            //Subtle Dilute [ Wildtype+, dilute ]
//            if (ThreadLocalRandom.current().nextInt(100) > WTC) {
//                initialGenes[6] = (ThreadLocalRandom.current().nextInt(2) + 1);
//
//            } else {
//                initialGenes[6] = (1);
//            }
//            if (ThreadLocalRandom.current().nextInt(100) > WTC) {
//                initialGenes[7] = (ThreadLocalRandom.current().nextInt(2) + 1);
//
//            } else {
//                initialGenes[7] = (1);
//            }
//
//            //Blue Dilute [ Dilute, wildtype+ ]
////            if (ThreadLocalRandom.current().nextInt(100) > WTC) {
//////                initialGenes[8] = (ThreadLocalRandom.current().nextInt(2) + 1);
////
////            } else {
//                initialGenes[8] = (1);
////            }
////            if (ThreadLocalRandom.current().nextInt(100) > WTC) {
//////                initialGenes[9] = (ThreadLocalRandom.current().nextInt(2) + 1);
////
////            } else {
//                initialGenes[9] = (1);
////            }
//
//            //this one will combo with agouti I guess
//            //White Spots [ Wildtype+, spotted, roanSpots ]
//            if (ThreadLocalRandom.current().nextInt(100) > WTC) {
//                initialGenes[10] = (ThreadLocalRandom.current().nextInt(3) + 1);
//
//            } else {
//                initialGenes[10] = (1);
//            }
//            if (ThreadLocalRandom.current().nextInt(100) > WTC) {
//                initialGenes[11] = (ThreadLocalRandom.current().nextInt(3) + 1);
//
//            } else {
//                initialGenes[11] = (1);
//            }
//
//            //Dom.White and Belted [ Dom.White, Belted, wildtype+, Patch, Roan]
//            if (ThreadLocalRandom.current().nextInt(100) > WTC) {
//                initialGenes[12] = (ThreadLocalRandom.current().nextInt(5) + 1);
//                if (wildType == 1) {
//                    initialGenes[13] = (1);
//                } else {
//                    initialGenes[13] = (3);
//                }
//
//            } else {
//                if (wildType == 1) {
//                    initialGenes[12] = (1);
//                    initialGenes[13] = (1);
//                } else {
//                    initialGenes[12] = (3);
//                    initialGenes[13] = (3);
//                }
//            }
//
//            //Berkshire spots [ Wildtype+, tuxedo, berkshire ]
//            if (ThreadLocalRandom.current().nextInt(100) > WTC) {
//                initialGenes[14] = (ThreadLocalRandom.current().nextInt(3) + 1);
//                initialGenes[15] = (1);
//
//            } else {
//                initialGenes[14] = (1);
//                initialGenes[15] = (1);
//            }
//
//            //White Extension [ Over marked, Medium, undermarked+ ]
//            if (ThreadLocalRandom.current().nextInt(100) > WTC) {
//                initialGenes[16] = (ThreadLocalRandom.current().nextInt(3) + 1);
//
//            } else {
//                initialGenes[16] = (3);
//            }
//            if (ThreadLocalRandom.current().nextInt(100) > WTC) {
//                initialGenes[17] = (ThreadLocalRandom.current().nextInt(3) + 1);
//
//            } else {
//                initialGenes[17] = (3);
//            }
//
//            //face squash gene 1 [ Wildtype+, long, medium, short, squashed ]
//            if (wildType == 1 || ThreadLocalRandom.current().nextInt(100) > WTC) {
//                initialGenes[18] = (ThreadLocalRandom.current().nextInt(5) + 1);
//
//            } else {
//                initialGenes[18] = (1);
//            }
//            if (wildType == 1 || ThreadLocalRandom.current().nextInt(100) > WTC) {
//                initialGenes[19] = (ThreadLocalRandom.current().nextInt(5) + 1);
//
//            } else {
//                initialGenes[19] = (1);
//            }
//
//            //inbreeding detector A
//            initialGenes[20] = (ThreadLocalRandom.current().nextInt(20) + 1);
//            initialGenes[21] = (ThreadLocalRandom.current().nextInt(20) + 20);
//
//            //inbreeding detector B
//            initialGenes[22] = (ThreadLocalRandom.current().nextInt(20) + 1);
//            initialGenes[23] = (ThreadLocalRandom.current().nextInt(20) + 20);
//
//            //inbreeding detector C
//            initialGenes[24] = (ThreadLocalRandom.current().nextInt(20) + 1);
//            initialGenes[25] = (ThreadLocalRandom.current().nextInt(20) + 20);
//
//            //inbreeding detector D
//            initialGenes[26] = (ThreadLocalRandom.current().nextInt(20) + 1);
//            initialGenes[27] = (ThreadLocalRandom.current().nextInt(20) + 20);
//
//            //inbreeding detector E
//            initialGenes[28] = (ThreadLocalRandom.current().nextInt(20) + 1);
//            initialGenes[29] = (ThreadLocalRandom.current().nextInt(20) + 20);
//
//            //inbreeding detector F
//            initialGenes[30] = (ThreadLocalRandom.current().nextInt(20) + 1);
//            initialGenes[31] = (ThreadLocalRandom.current().nextInt(20) + 20);
//
//
//            //Waddles [ waddles, wildtype ]
//            if (ThreadLocalRandom.current().nextInt(100) > WTC) {
//                initialGenes[32] = (ThreadLocalRandom.current().nextInt(2) + 1);
//
//            } else {
//                initialGenes[32] = (2);
//            }
//            if (ThreadLocalRandom.current().nextInt(100) > WTC) {
//                initialGenes[33] = (ThreadLocalRandom.current().nextInt(2) + 1);
//
//            } else {
//                initialGenes[33] = (2);
//            }
//
//            //hair density [ furry, wildtype, sparse ]
//            if (ThreadLocalRandom.current().nextInt(100) > WTC) {
//                initialGenes[34] = (ThreadLocalRandom.current().nextInt(3) + 1);
//
//            } else {
//                initialGenes[34] = (2);
//            }
//            if (ThreadLocalRandom.current().nextInt(100) > WTC) {
//                initialGenes[35] = (ThreadLocalRandom.current().nextInt(3) + 1);
//
//            } else {
//                initialGenes[35] = (2);
//            }
//
//            //baldness [ Bald, wildtype ]
//            if (ThreadLocalRandom.current().nextInt(100) > WTC) {
//                initialGenes[36] = (ThreadLocalRandom.current().nextInt(2) + 1);
//
//            } else {
//                if (wildType == 1) {
//                    initialGenes[36] = (1);
//                } else {
//                    initialGenes[36] = (2);
//                }
//            }
//            if (ThreadLocalRandom.current().nextInt(100) > WTC) {
//                initialGenes[37] = (ThreadLocalRandom.current().nextInt(2) + 1);
//
//            } else {
//                initialGenes[37] = (2);
//            }
//
//            //wooly [ wooly, wildtype ]
//            if (ThreadLocalRandom.current().nextInt(100) > WTC) {
//                initialGenes[38] = (ThreadLocalRandom.current().nextInt(2) + 1);
//
//            } else {
//                initialGenes[38] = (2);
//            }
//            if (ThreadLocalRandom.current().nextInt(100) > WTC) {
//                initialGenes[39] = (ThreadLocalRandom.current().nextInt(2) + 1);
//
//            } else {
//                initialGenes[39] = (2);
//            }
//
//            //thick hair [ thick hair+, less hair ]
//            if (ThreadLocalRandom.current().nextInt(100) > WTC) {
//                initialGenes[40] = (ThreadLocalRandom.current().nextInt(2) + 1);
//
//            } else {
//                initialGenes[40] = (1);
//            }
//            if (ThreadLocalRandom.current().nextInt(100) > WTC) {
//                initialGenes[41] = (ThreadLocalRandom.current().nextInt(2) + 1);
//
//            } else {
//                initialGenes[41] = (1);
//            }
//
//            //face squash gene 2 [ longest, normal, short, shortest]
//            if (wildType == 1 || ThreadLocalRandom.current().nextInt(100) > WTC) {
//                initialGenes[42] = (ThreadLocalRandom.current().nextInt(4) + 1);
//
//            } else {
//                initialGenes[42] = (1);
//            }
//            if (wildType == 1 || ThreadLocalRandom.current().nextInt(100) > WTC) {
//                initialGenes[43] = (ThreadLocalRandom.current().nextInt(4) + 1);
//
//            } else {
//                initialGenes[43] = (1);
//            }
//
//            //potbelly dwarfism [wildtype, dwarfStrong, dwarfWeak]
//            if (ThreadLocalRandom.current().nextInt(100) > WTC) {
//                initialGenes[44] = (ThreadLocalRandom.current().nextInt(3) + 1);
//                initialGenes[45] = (1);
//
//            } else {
//                initialGenes[44] = (1);
//                initialGenes[45] = (1);
//            }
//
//            //potbelly dwarfism2 [wildtype, dwarf]
//                if (ThreadLocalRandom.current().nextInt(100) > WTC) {
//                    initialGenes[46] = (ThreadLocalRandom.current().nextInt(2) + 1);
//
//                } else {
//                    initialGenes[46] = (1);
//                }
//                if (ThreadLocalRandom.current().nextInt(100) > WTC) {
//                    initialGenes[47] = (ThreadLocalRandom.current().nextInt(2) + 1);
//
//                } else {
//                    initialGenes[47] = (1);
//                }
//
//            //size genes reducer [wildtype, smaller smaller smallest...]
//            if (ThreadLocalRandom.current().nextInt(100) > WTC) {
//                initialGenes[48] = (ThreadLocalRandom.current().nextInt(16) + 1);
//
//            } else {
//                initialGenes[48] = (1);
//            }
//            if (ThreadLocalRandom.current().nextInt(100) > WTC) {
//                initialGenes[49] = (ThreadLocalRandom.current().nextInt(16) + 1);
//
//            } else {
//                initialGenes[49] = (1);
//            }
//
//            //size genes adder [wildtype, bigger bigger biggest...]
//            if (ThreadLocalRandom.current().nextInt(100) > WTC) {
//                initialGenes[50] = (ThreadLocalRandom.current().nextInt(16) + 1);
//
//            } else {
//                initialGenes[50] = (1);
//            }
//            if (ThreadLocalRandom.current().nextInt(100) > WTC) {
//                initialGenes[51] = (ThreadLocalRandom.current().nextInt(16) + 1);
//
//            } else {
//                initialGenes[51] = (1);
//            }
//
//            //size genes varient1 [wildtype, smaller, smallest]
//            if (ThreadLocalRandom.current().nextInt(100) > WTC) {
//                initialGenes[52] = (ThreadLocalRandom.current().nextInt(3) + 1);
//
//            } else {
//                initialGenes[52] = (1);
//            }
//            if (ThreadLocalRandom.current().nextInt(100) > WTC) {
//                initialGenes[53] = (ThreadLocalRandom.current().nextInt(3) + 1);
//
//            } else {
//                initialGenes[53] = (1);
//            }
//
//            //size genes varient2 [wildtype, smaller, smallest]
//            if (ThreadLocalRandom.current().nextInt(100) > WTC) {
//                initialGenes[54] = (ThreadLocalRandom.current().nextInt(3) + 1);
//
//            } else {
//                initialGenes[54] = (1);
//            }
//            if (ThreadLocalRandom.current().nextInt(100) > WTC) {
//                initialGenes[55] = (ThreadLocalRandom.current().nextInt(3) + 1);
//
//            } else {
//                initialGenes[55] = (1);
//            }
//
//            //body type [wildtype, smallest to largest] if mod with lard/fat smallest size has least fat, largest has most fat
//            if (ThreadLocalRandom.current().nextInt(100) > WTC) {
//                initialGenes[56] = (ThreadLocalRandom.current().nextInt(6) + 1);
//
//            } else {
//                initialGenes[56] = (1);
//            }
//            if (ThreadLocalRandom.current().nextInt(100) > WTC) {
//                initialGenes[57] = (ThreadLocalRandom.current().nextInt(6) + 1);
//
//            } else {
//                initialGenes[57] = (1);
//            }
//
//            //litter size reduction [wildtype (half), weak reduction (2/3), prolific]
//            if (ThreadLocalRandom.current().nextInt(100) > WTC) {
//                initialGenes[58] = (ThreadLocalRandom.current().nextInt(3) + 1);
//
//            } else {
//                initialGenes[58] = (1);
//            }
//            if (ThreadLocalRandom.current().nextInt(100) > WTC) {
//                initialGenes[59] = (ThreadLocalRandom.current().nextInt(3) + 1);
//
//            } else {
//                initialGenes[59] = (1);
//            }
//
//            //ear size [xsmall, small, medium, large, xlarge]
//
//            //ear angle [up, medium, down
//
//        }
//
//        return initialGenes;
//    }
}
