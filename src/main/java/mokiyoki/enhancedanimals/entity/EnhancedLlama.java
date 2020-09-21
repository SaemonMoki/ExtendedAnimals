package mokiyoki.enhancedanimals.entity;

import mokiyoki.enhancedanimals.ai.ECLlamaFollowCaravan;
import mokiyoki.enhancedanimals.ai.ECRunAroundLikeCrazy;
import mokiyoki.enhancedanimals.ai.general.EnhancedPanicGoal;
import mokiyoki.enhancedanimals.ai.general.EnhancedWaterAvoidingRandomWalkingEatingGoal;
import mokiyoki.enhancedanimals.entity.Genetics.LlamaGeneticsInitialiser;
import mokiyoki.enhancedanimals.init.ModBlocks;
import mokiyoki.enhancedanimals.config.EanimodCommonConfig;
import mokiyoki.enhancedanimals.util.Genes;
import mokiyoki.enhancedanimals.util.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SoundType;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.RangedAttackGoal;
import net.minecraft.entity.ai.goal.TargetGoal;
import net.minecraft.entity.merchant.villager.WanderingTraderEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.ShearsItem;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.*;

import static mokiyoki.enhancedanimals.util.handlers.EventRegistry.ENHANCED_LLAMA;

public class EnhancedLlama extends EnhancedAnimalRideableAbstract implements IRangedAttackMob, net.minecraftforge.common.IShearable, EnhancedAnimal {

    //avalible UUID spaces : [ S X X 3 X 5 X 7 - 8 9 10 11 - 12 13 14 15 - 16 17 18 19 - 20 21 22 23 24 25 26 27 28 29 30 31 ]

    private static final DataParameter<Integer> DATA_STRENGTH_ID = EntityDataManager.createKey(EnhancedLlama.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> COAT_LENGTH = EntityDataManager.createKey(EnhancedLlama.class, DataSerializers.VARINT);

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

    private static final String[] LLAMA_TEXTURES_DECO = new String[] {
            "blanket_trader.png", "blanket_black.png", "blanket_blue.png", "blanket_brown.png", "blanket_cyan.png", "blanket_grey.png", "blanket_green.png", "blanket_lightblue.png", "blanket_lightgrey.png", "blanket_lime.png", "blanket_magenta.png", "blanket_orange.png", "blanket_pink.png", "blanket_purple.png", "blanket_red.png", "blanket_white.png", "blanket_yellow.png"
    };

    private static final Ingredient TEMPTATION_ITEMS = Ingredient.fromItems(Blocks.HAY_BLOCK, Items.WHEAT, Items.CARROT, Items.SUGAR_CANE, Items.BEETROOT, Items.GRASS, Items.TALL_GRASS);
    private static final Ingredient BREED_ITEMS = Ingredient.fromItems(Blocks.HAY_BLOCK, ModBlocks.UNBOUNDHAY_BLOCK);

    public float destPos;

    private static final int SEXLINKED_GENES_LENGTH = 2;

    private int maxCoatLength;
    private int currentCoatLength;
    private int timeForGrowth = 0;

    protected String motherUUID = "";

    private boolean didSpit;

    private int despawnDelay = -1;
    private boolean resetTexture = true;

    private EnhancedWaterAvoidingRandomWalkingEatingGoal wanderEatingGoal;

    @Nullable
    private EnhancedLlama caravanHead;
    @Nullable
    private EnhancedLlama caravanTail;

    public EnhancedLlama(EntityType<? extends EnhancedLlama> entityType, World worldIn) {
        super(entityType, worldIn, SEXLINKED_GENES_LENGTH, Reference.LLAMA_AUTOSOMAL_GENES_LENGTH, TEMPTATION_ITEMS, BREED_ITEMS, createFoodMap(), true);
        this.setPathPriority(PathNodeType.WATER, 0.0F);
    }

    private static Map<Item, Integer> createFoodMap() {
        return new HashMap() {{
            put(new ItemStack(Items.TALL_GRASS).getItem(), 6000);
            put(new ItemStack(Items.GRASS).getItem(), 3000);
            put(new ItemStack(Blocks.HAY_BLOCK).getItem(), 54000);
            put(new ItemStack(Items.CARROT).getItem(), 1500);
            put(new ItemStack(Items.WHEAT).getItem(), 6000);
            put(new ItemStack(Items.SUGAR).getItem(), 1500);
            put(new ItemStack(Items.APPLE).getItem(), 1500);
            put(new ItemStack(ModBlocks.UNBOUNDHAY_BLOCK).getItem(), 54000);
        }};
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        //Todo add the temperamants
        this.wanderEatingGoal = new EnhancedWaterAvoidingRandomWalkingEatingGoal(this, 1.0D, 7, 0.001F, 120, 2, 50);
//        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new ECRunAroundLikeCrazy(this, 1.2D));
        this.goalSelector.addGoal(2, new ECLlamaFollowCaravan(this, (double)2.1F));
        this.goalSelector.addGoal(3, new RangedAttackGoal(this, 1.25D, 40, 20.0F));
        this.goalSelector.addGoal(3, new EnhancedPanicGoal(this, 1.2D));
//        this.goalSelector.addGoal(4, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(5, this.wanderEatingGoal);
//        this.goalSelector.addGoal(5, new FollowParentGoal(this, 1.0D));
//        this.goalSelector.addGoal(6, new WaterAvoidingRandomWalkingGoal(this, 0.7D));
//        this.goalSelector.addGoal(7, new EnhancedLookAtGoal(this, PlayerEntity.class, 6.0F));
//        this.goalSelector.addGoal(8, new EnhancedLookRandomlyGoal(this));
        this.targetSelector.addGoal(1, new EnhancedLlama.HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new EnhancedLlama.DefendTargetGoal(this));
    }


    protected void registerData() {
        super.registerData();
        this.dataManager.register(DATA_STRENGTH_ID, 0);
        this.dataManager.register(COAT_LENGTH, -1);
    }

    protected String getSpecies() {
        return I18n.format("entity.eanimod.enhanced_llama");
    }

    protected int getAdultAge() { return 120000;}

    @Override
    public Boolean isAnimalSleeping() {
        if (isLeashedToTrader()) {
            return false;
        }
        return super.isAnimalSleeping();
    }

    @Override
    protected int gestationConfig() {
        return EanimodCommonConfig.COMMON.gestationDaysLlama.get();
    }

    private void setStrength(int strengthIn) {
        this.dataManager.set(DATA_STRENGTH_ID, strengthIn);
    }

    public int getStrength() {
        return this.dataManager.get(DATA_STRENGTH_ID);
    }

    private void setCoatLength(int coatLength) {
        this.dataManager.set(COAT_LENGTH, coatLength);
    }

    public int getCoatLength() {
        return this.dataManager.get(COAT_LENGTH);
    }

//    public void onInventoryChanged(IInventory invBasic) {
//        super.onInventoryChanged(invBasic);
//        if (this.ticksExisted > 20 && dyecolor1 != 0 && dyecolor1 != dyecolor) {
//            this.playSound(SoundEvents.ENTITY_LLAMA_SWAG, 0.5F, 1.0F);
//            resetTexture = true;
//        }
//    }

//    public int getSaddleNumber() {
//        int saddle = 0;
//        if (this.animalInventory != null) {
//            if (!this.animalInventory.getStackInSlot(1).isEmpty()) {
//                Item saddleType = this.animalInventory.getStackInSlot(1).getItem();
//                if (saddleType == ModItems.SADDLE_BASIC_LEATHER) {
//                    saddle = 2;
//                } else if (saddleType == ModItems.SADDLE_BASIC_CLOTH) {
//                    saddle = 3;
//                } else {
//                    saddle = 1;
//                }
//            }
//        }
//        return saddle;
//    }

//    public int getBridleNumber() {
//        int bridle = 0;
//        if (this.animalInventory != null) {
//            if (!this.animalInventory.getStackInSlot(3).isEmpty()) {
//                Item saddleType = this.animalInventory.getStackInSlot(3).getItem();
//                if (saddleType == ModItems.BRIDLE_BASIC_LEATHER) {
//                    bridle = 1;
//                } else if (saddleType == ModItems.BRIDLE_BASIC_CLOTH) {
//                    bridle = 2;
//                }
//            }
//        }
//        return bridle;
//    }

//    public int getBlanketNumber() {
//            int colour = 0;
//            if (this.animalInventory != null) {
//                Item blanketColour = this.animalInventory.getStackInSlot(4).getItem();
//                if (blanketColour == Items.BLACK_CARPET) {
//                    colour = 1;
//                } else if (blanketColour == Items.BLUE_CARPET) {
//                    colour = 2;
//                } else if (blanketColour == Items.BROWN_CARPET) {
//                    colour = 3;
//                } else if (blanketColour == Items.CYAN_CARPET) {
//                    colour = 4;
//                } else if (blanketColour == Items.GRAY_CARPET) {
//                    colour = 5;
//                } else if (blanketColour == Items.GREEN_CARPET) {
//                    colour = 6;
//                } else if (blanketColour == Items.LIGHT_BLUE_CARPET) {
//                    colour = 7;
//                } else if (blanketColour == Items.LIGHT_GRAY_CARPET) {
//                    colour = 8;
//                } else if (blanketColour == Items.LIME_CARPET) {
//                    colour = 9;
//                } else if (blanketColour == Items.MAGENTA_CARPET) {
//                    colour = 10;
//                } else if (blanketColour == Items.ORANGE_CARPET) {
//                    colour = 11;
//                } else if (blanketColour == Items.PINK_CARPET) {
//                    colour = 12;
//                } else if (blanketColour == Items.PURPLE_CARPET) {
//                    colour = 13;
//                } else if (blanketColour == Items.RED_CARPET) {
//                    colour = 14;
//                } else if (blanketColour == Items.WHITE_CARPET) {
//                    colour = 15;
//                } else if (blanketColour == Items.YELLOW_CARPET) {
//                    colour = 16;
//                }
//            }
//            return colour;
//        }

//    public int getHarnessNumber() {
//        int bridle = 0;
//        if (this.animalInventory != null) {
//            if (!this.animalInventory.getStackInSlot(5).isEmpty()) {
//                Item saddleType = this.animalInventory.getStackInSlot(5).getItem();
//                if (saddleType == ModItems.BRIDLE_BASIC_LEATHER) {
//                    bridle = 1;
//                } else if (saddleType == ModItems.BRIDLE_BASIC_CLOTH) {
//                    bridle = 2;
//                }
//            }
//        }
//        return bridle;
//    }

    @Override
    public void updatePassenger(Entity passenger) {
        if (this.isPassenger(passenger)) {
            float f = MathHelper.cos(this.renderYawOffset * ((float)Math.PI / 180F));
            float f1 = MathHelper.sin(this.renderYawOffset * ((float)Math.PI / 180F));
            float f2 = 0.3F;
            passenger.setPosition(this.getPosX() + (double)(0.3F * f1), this.getPosY() + this.getMountedYOffset() + passenger.getYOffset(), this.getPosZ() - (double)(0.3F * f));
        }
    }

    public double getMountedYOffset() {
        return (double)this.getHeight() * 0.67D;
    }

    protected boolean isMovementBlocked() {
        return this.getHealth() <= 0.0F;
    }

    protected void registerAttributes() {
        super.registerAttributes();
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(4.0D);
        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.175F);
    }

    @Override
    public boolean processInteract(PlayerEntity entityPlayer, Hand hand) {
        ItemStack itemStack = entityPlayer.getHeldItem(hand);
        Item item = itemStack.getItem();

        if (this.isBeingRidden()) {
            return super.processInteract(entityPlayer, hand);
        }

        if (!this.world.isRemote && !hand.equals(Hand.OFF_HAND)) {
            if (item instanceof ShearsItem) {
                List<ItemStack> woolToDrop = onSheared(itemStack, null, null, 0);
                java.util.Random rand = new java.util.Random();
                woolToDrop.forEach(d -> {
                    net.minecraft.entity.item.ItemEntity ent = this.entityDropItem(d, 1.0F);
                    ent.setMotion(ent.getMotion().add((double) ((rand.nextFloat() - rand.nextFloat()) * 0.1F), (double) (rand.nextFloat() * 0.05F), (double) ((rand.nextFloat() - rand.nextFloat()) * 0.1F)));
                });
            }
        }
        return super.processInteract(entityPlayer, hand);
    }

    public void livingTick() {
        super.livingTick();
        this.destPos = (float)((double)this.destPos + (double)(this.onGround ? -1 : 4) * 0.3D);
        this.destPos = MathHelper.clamp(this.destPos, 0.0F, 1.0F);
        if (!this.world.isRemote) {
            if (this.despawnDelay != -1) {
                this.tryDespawn();
            }
        }
    }

    protected void runExtraIdleTimeTick() {
        if (hunger <= 36000) {
            timeForGrowth++;
        }
        if (timeForGrowth >= 24000) {
            timeForGrowth = 0;
            if (maxCoatLength > currentCoatLength) {
                currentCoatLength++;
                setCoatLength(currentCoatLength);
            }
        }
    }

    @Override
    protected void lethalGenes() {
    }


    protected  void incrementHunger() {
        if(sleeping) {
            hunger = hunger + (0.5F*getHungerModifier());
        } else {
            hunger = hunger + (1.0F*getHungerModifier());
        }
    }

    protected void createAndSpawnEnhancedChild(World inWorld) {
        EnhancedLlama enhancedllama = ENHANCED_LLAMA.create(this.world);
        Genes babyGenes = new Genes(this.genetics).makeChild(this.getIsFemale(), this.mateGender, this.mateGenetics);
        defaultCreateAndSpawn(enhancedllama, inWorld, babyGenes, -120000);
        enhancedllama.setStrengthAndInventory();
        enhancedllama.setMaxCoatLength();
        enhancedllama.currentCoatLength = enhancedllama.maxCoatLength;
        enhancedllama.setCoatLength(enhancedllama.currentCoatLength);

        this.world.addEntity(enhancedllama);
    }

    @Override
    protected boolean canBePregnant() {
        return true;
    }

    @Override
    protected boolean canLactate() {
        return false;
    }


    protected void setLlamaSize(){
        int[] genes = this.genetics.getAutosomalGenes();
        float size = 1.0F;

        if (genes[0] < 3) {
            size = size - 0.025F;
            if (genes[0] < 2) {
                size = size - 0.025F;
            }
        }
        if (genes[1] < 3) {
            size = size - 0.025F;
            if (genes[1] < 2) {
                size = size - 0.025F;
            }
        }
        if (genes[2] < 3) {
            size = size - 0.025F;
            if (genes[2] < 2) {
                size = size - 0.025F;
            }
        }
        if (genes[3] < 3) {
            size = size - 0.025F;
            if (genes[3] < 2) {
                size = size - 0.025F;
            }
        }
        this.setAnimalSize(size);
    }

    @Override
    @Nullable
    protected ResourceLocation getLootTable() {
        return null;
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

    protected void playStepSound(BlockPos pos, BlockState blockIn) {
        this.playSound(SoundEvents.ENTITY_LLAMA_STEP, 0.15F, 1.0F);
        if (!this.isSilent() && this.getBells()) {
            this.playSound(SoundEvents.BLOCK_NOTE_BLOCK_CHIME, 1.5F, 0.75F);
        }
    }

    protected void playChestEquipSound() {
        this.playSound(SoundEvents.ENTITY_LLAMA_CHEST, 1.0F, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
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
        int[] genes = this.genetics.getAutosomalGenes();
        if (!this.world.isRemote && !isChild()) {
            if (currentCoatLength == 1) {
                int i = this.rand.nextInt(4);
                if (i>3){
                    ret.add(new ItemStack(getWoolBlocks(genes)));
                }
            } else if (currentCoatLength == 2) {
                int i = this.rand.nextInt(2);
                if (i>0){
                    ret.add(new ItemStack(getWoolBlocks(genes)));
                }
            } else if (currentCoatLength == 3) {
                int i = this.rand.nextInt(4);
                if (i>0){
                    ret.add(new ItemStack(getWoolBlocks(genes)));
                }
            } else if (currentCoatLength == 4) {
                ret.add(new ItemStack(getWoolBlocks(genes)));
            }

        }
        currentCoatLength = -1;
        setCoatLength(currentCoatLength);
        return ret;
    }

    private Block getWoolBlocks (int[] genes) {
        Block returnBlocks;

            if (genes[6] == 1 || genes[7] == 1) {
                returnBlocks = Blocks.WHITE_WOOL;
            } else if (genes[14] == 1 || genes[15] == 1) {
                returnBlocks = Blocks.BLACK_WOOL;
            } else if (genes[14] == 3 || genes[15] == 3) {
                returnBlocks = Blocks.BROWN_WOOL;
            } else {
                if (genes[16] == 1 || genes[17] == 1) {
                    returnBlocks = Blocks.YELLOW_WOOL;
                } else if (genes[16] >= 5 && genes[17] >= 5) {
                    if (genes[16] == 5 || genes[17] == 5) {
                        returnBlocks = Blocks.RED_WOOL;
                    } else {
                        returnBlocks = Blocks.BLACK_WOOL;
                    }
                } else {
                    returnBlocks = Blocks.BROWN_WOOL;
                }
            }

            if (returnBlocks.equals(Blocks.BLACK_WOOL) && (genes[18] == 1 || genes[19] == 1)) {
                returnBlocks = Blocks.GRAY_WOOL;
            }

            if (genes[10] == 2 && genes[11] == 2) {
                boolean i = rand.nextBoolean();
                if (i) {
                    returnBlocks = Blocks.WHITE_WOOL;
                }
            }

            if (genes[12] == 1 || genes[13] == 1) {
                boolean i = rand.nextBoolean();
                if (i) {
                    returnBlocks = Blocks.WHITE_WOOL;
                }
            }

        return returnBlocks;
    }

    @Override
    protected boolean canDropLoot() { return true; }

    protected void dropSpecialItems(DamageSource source, int looting, boolean recentlyHitIn) {
        super.dropSpecialItems(source, looting, recentlyHitIn);
        int[] genes = this.genetics.getAutosomalGenes();
        int age = this.getAge();
        boolean woolDrop = false;
        int lootCount = 0;

        if (!this.isBurning()) {

            int i = rand.nextInt(100);
            if ((age/1200) > i) {

                if (genes[20] != 1 && genes[21] != 1) {
                    woolDrop = true;
                    lootCount = 1;
                    if (currentCoatLength > 2 && age > 80000) {
                        if (rand.nextBoolean()) {
                            lootCount++;
                        }
                    }
                } else {
                    lootCount = rand.nextInt(3);
                    if (lootCount !=0 && age < 120000) {
                        lootCount--;
                        if (lootCount !=0 && age < 80000) {
                            lootCount--;
                        }
                    }
                }

            }

            if (woolDrop) {
                ItemStack fleeceStack = new ItemStack(getWoolBlocks(genes), lootCount);
                this.entityDropItem(fleeceStack);
            } else {
                ItemStack leatherStack = new ItemStack(Items.LEATHER, lootCount);
                this.entityDropItem(leatherStack);
            }
        }
    }

    private void tryDespawn() {
        if (this.canDespawn()) {
            this.despawnDelay = this.isLeashedToTrader() ? ((WanderingTraderEntity)this.getLeashHolder()).getDespawnDelay() - 1 : this.despawnDelay - 1;
            if (this.despawnDelay <= 0) {
                this.clearLeashed(true, false);
                this.remove();
            }

        }
    }

    private boolean canDespawn() {
        return !this.isTame() && !this.isLeashedToStranger() && !this.isOnePlayerRiding();
    }

    private boolean isLeashedToTrader() {
        return this.getLeashHolder() instanceof WanderingTraderEntity;
    }

    private boolean isLeashedToStranger() {
        return this.getLeashed() && !this.isLeashedToTrader();
    }

    @OnlyIn(Dist.CLIENT)
    public String getLlamaTexture() {
        if (this.enhancedAnimalTextures.isEmpty()) {
            this.setTexturePaths();
        } else if (resetTexture) {
            resetTexture = false;
            this.texturesIndexes.clear();
            this.enhancedAnimalTextures.clear();
            this.setTexturePaths();
        }

        return getCompiledTextures("enhanced_llama");
    }

    @OnlyIn(Dist.CLIENT)
    protected void setTexturePaths() {
        if (this.getSharedGenes() != null) {
            int[] genesForText = getSharedGenes().getAutosomalGenes();

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


        this.enhancedAnimalTextures.add(LLAMA_TEXTURES_GROUND[ground]);
            this.texturesIndexes.add(String.valueOf(ground));

        if (pattern != 0) {
            this.enhancedAnimalTextures.add(LLAMA_TEXTURES_PATTERN[pattern]);
            this.texturesIndexes.add(String.valueOf(pattern));
        }

        if (roan != 0) {
            this.enhancedAnimalTextures.add(LLAMA_TEXTURES_ROAN[roan]);
            this.texturesIndexes.add(String.valueOf(roan));
        }

        if (tux != 0) {
            this.enhancedAnimalTextures.add(LLAMA_TEXTURES_TUXEDO[tux]);
            this.texturesIndexes.add(String.valueOf(tux));
        }

        if (piebald != 0) {
            this.enhancedAnimalTextures.add(LLAMA_TEXTURES_PIEBALD[piebald]);
            this.texturesIndexes.add(String.valueOf(piebald));
        }

        if (domwhite != 0) {
            this.enhancedAnimalTextures.add(LLAMA_TEXTURES_DOMWHITE[domwhite]);
            this.texturesIndexes.add(String.valueOf(domwhite));
        }

        if (fur != 0) {
            this.enhancedAnimalTextures.add(LLAMA_TEXTURES_FUR[fur]);
            this.texturesIndexes.add(String.valueOf(fur));
        }

        this.enhancedAnimalTextures.add(LLAMA_TEXTURES_EYES[eyes]);
            this.texturesIndexes.add(String.valueOf(eyes));

        this.enhancedAnimalTextures.add(LLAMA_TEXTURES_SKIN[skin]);
            this.texturesIndexes.add(String.valueOf(skin));

        } //if genes are not null end bracket

//        this.enhancedAnimalTextures.add("d_collar.png");
//            this.texturesIndexes.add(String.valueOf(0));
//        this.enhancedAnimalTextures.add("collar_ringiron.png");
//            this.texturesIndexes.add(String.valueOf(0));
//        this.enhancedAnimalTextures.add("collar_belliron.png");
//            this.texturesIndexes.add(String.valueOf(0));

    } // setTexturePaths end bracket

    @Override
    protected void setAlphaTexturePaths() {
    }

    @Override
    protected void initilizeAnimalSize() {
        setLlamaSize();
    }


    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);

        compound.putInt("Strength", this.getStrength());
        compound.putFloat("CoatLength", this.getCoatLength());

        if (this.despawnDelay != -1) {
            compound.putInt("DespawnDelay", this.despawnDelay);
        }

    }

    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
        this.setStrength(compound.getInt("Strength"));
        currentCoatLength = compound.getInt("CoatLength");
        this.setCoatLength(currentCoatLength);

        if (compound.contains("DespawnDelay", 99)) {
            this.despawnDelay = compound.getInt("DespawnDelay");
        }

        //resets the max so we don't have to store it
        setMaxCoatLength();

        if (!compound.getString("breed").isEmpty()) {
            this.currentCoatLength = this.maxCoatLength;
            this.setCoatLength(this.currentCoatLength);
        }
    }

    @Nullable
    @Override
    public ILivingEntityData onInitialSpawn(IWorld inWorld, DifficultyInstance difficulty, SpawnReason spawnReason, @Nullable ILivingEntityData livingdata, @Nullable CompoundNBT itemNbt) {
        livingdata =  commonInitialSpawnSetup(inWorld, livingdata, 120000, 20000, 500000);

        if (spawnReason == SpawnReason.EVENT) {
            this.targetSelector.addGoal(1, new EnhancedLlama.FollowTraderGoal(this));
            this.despawnDelay = 49999;
            this.setBirthTime("");
            this.animalInventory.setInventorySlotContents(4, Items.BLUE_CARPET.getDefaultInstance().setDisplayName(new StringTextComponent("Trader's Blanket")));
        }

        setStrengthAndInventory();
        setMaxCoatLength();
        this.currentCoatLength = this.maxCoatLength;
        setCoatLength(this.currentCoatLength);

        return livingdata;
    }

//    @Override
//    protected int[] createInitialSpawnChildGenes(int[] spawnGenes1, int[] spawnGenes2, int[] mitosis, int[] mateMitosis) {
//        return getCriaGenes(mitosis, mateMitosis);
//    }

    private void setStrengthAndInventory() {
        int[] genes = this.genetics.getAutosomalGenes();
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
    }

    private void setMaxCoatLength() {
        int[] genes = this.genetics.getAutosomalGenes();
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

    @Override
    protected Genes createInitialGenes(IWorld world, BlockPos pos, boolean isDomestic) {
        return new LlamaGeneticsInitialiser().generateNewGenetics(world, pos, isDomestic);
    }

    @Override
    protected Genes createInitialBreedGenes(IWorld world, BlockPos pos, String breed) {
        return new LlamaGeneticsInitialiser().generateWithBreed(world, pos, breed);
    }

    private void spit(LivingEntity target) {
        EnhancedEntityLlamaSpit entityllamaspit = new EnhancedEntityLlamaSpit(this.world, this);
        double d0 = target.getPosX() - this.getPosX();
        double d1 = target.getBoundingBox().minY + (double)(target.getHeight() / 3.0F) - entityllamaspit.getPosY();
        double d2 = target.getPosZ() - this.getPosZ();
        float f = MathHelper.sqrt(d0 * d0 + d2 * d2) * 0.2F;
        entityllamaspit.shoot(d0, d1 + (double)f, d2, 1.5F, 10.0F);
        this.world.playSound((PlayerEntity)null, this.getPosX(), this.getPosY(), this.getPosZ(), SoundEvents.ENTITY_LLAMA_SPIT, this.getSoundCategory(), 1.0F, 1.0F + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F);
        this.world.addEntity(entityllamaspit);
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

            BlockState blockstate = this.world.getBlockState(new BlockPos(this.getPosX(), this.getPosY() - 0.2D - (double)this.prevRotationYaw, this.getPosZ()));
            if (!blockstate.isAir() && !this.isSilent()) {
                SoundType soundtype = blockstate.getSoundType();
                this.world.playSound((PlayerEntity)null, this.getPosX(), this.getPosY(), this.getPosZ(), soundtype.getStepSound(), this.getSoundCategory(), soundtype.getVolume() * 0.5F, soundtype.getPitch() * 0.75F);
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

    protected SoundEvent getAngrySound() {
        return SoundEvents.ENTITY_LLAMA_ANGRY;
    }

    /**
     * Attack the specified entity using a ranged attack.
     */

    public void attackEntityWithRangedAttack(LivingEntity target, float distanceFactor) {
        this.spit(target);
    }

    static class DefendTargetGoal extends NearestAttackableTargetGoal<WolfEntity> {
        public DefendTargetGoal(EnhancedLlama llama) {
            super(llama, WolfEntity.class, 16, false, true, (p_220789_0_) -> {
                return !((WolfEntity)p_220789_0_).isTamed();
            });
        }

        protected double getTargetDistance() {
            return super.getTargetDistance() * 0.25D;
        }
    }

    static class HurtByTargetGoal extends net.minecraft.entity.ai.goal.HurtByTargetGoal {
        public HurtByTargetGoal(EnhancedLlama llama) {
            super(llama);
        }

        /**
         * Returns whether an in-progress EntityAIBase should continue executing
         */
        public boolean shouldContinueExecuting() {
            if (this.goalOwner instanceof EnhancedLlama) {
                EnhancedLlama llamaentity = (EnhancedLlama)this.goalOwner;
                if (llamaentity.didSpit) {
                    llamaentity.setDidSpit(false);
                    return false;
                }
            }

            return super.shouldContinueExecuting();
        }
    }

    public class FollowTraderGoal extends TargetGoal {
        private final EnhancedLlama enhancedllama;
        private LivingEntity targetEntity;
        private int revengeTimer;

        public FollowTraderGoal(EnhancedLlama enhancedLlama) {
            super(enhancedLlama, false);
            this.enhancedllama = enhancedLlama;
            this.setMutexFlags(EnumSet.of(Goal.Flag.TARGET));
        }

        /**
         * Returns whether the EntityAIBase should begin execution.
         */
        public boolean shouldExecute() {
            if (!this.enhancedllama.getLeashed()) {
                return false;
            } else {
                Entity entity = this.enhancedllama.getLeashHolder();
                if (!(entity instanceof WanderingTraderEntity)) {
                    return false;
                } else {
                    WanderingTraderEntity wanderingtraderentity = (WanderingTraderEntity)entity;
                    this.targetEntity = wanderingtraderentity.getRevengeTarget();
                    int i = wanderingtraderentity.getRevengeTimer();
                    return i != this.revengeTimer && this.isSuitableTarget(this.targetEntity, EntityPredicate.DEFAULT);
                }
            }
        }

        public void startExecuting() {
            this.goalOwner.setAttackTarget(this.targetEntity);
            Entity entity = this.enhancedllama.getLeashHolder();
            if (entity instanceof WanderingTraderEntity) {
                this.revengeTimer = ((WanderingTraderEntity)entity).getRevengeTimer();
            }

            super.startExecuting();
        }
    }
}
