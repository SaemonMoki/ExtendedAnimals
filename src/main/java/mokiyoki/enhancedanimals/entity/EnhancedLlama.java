package mokiyoki.enhancedanimals.entity;

import mokiyoki.enhancedanimals.EnhancedAnimals;
import mokiyoki.enhancedanimals.ai.ECLlamaFollowCaravan;
import mokiyoki.enhancedanimals.ai.ECRunAroundLikeCrazy;
import mokiyoki.enhancedanimals.ai.general.EnhancedLookAtGoal;
import mokiyoki.enhancedanimals.ai.general.EnhancedLookRandomlyGoal;
import mokiyoki.enhancedanimals.ai.general.EnhancedPanicGoal;
import mokiyoki.enhancedanimals.ai.general.EnhancedWaterAvoidingRandomWalkingEatingGoal;
import mokiyoki.enhancedanimals.gui.EnhancedAnimalContainer;
import mokiyoki.enhancedanimals.init.ModBlocks;
import mokiyoki.enhancedanimals.init.ModItems;
import mokiyoki.enhancedanimals.items.DebugGenesBook;
import mokiyoki.enhancedanimals.config.EanimodCommonConfig;
import mokiyoki.enhancedanimals.util.EnhancedAnimalInfo;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CarpetBlock;
import net.minecraft.block.SoundType;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.BreedGoal;
import net.minecraft.entity.ai.goal.FollowParentGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.RangedAttackGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.TargetGoal;
import net.minecraft.entity.merchant.villager.WanderingTraderEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.passive.horse.AbstractChestedHorseEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.AirItem;
import net.minecraft.item.DyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.ShearsItem;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.stats.Stats;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import static mokiyoki.enhancedanimals.util.handlers.EventRegistry.ENHANCED_ANIMAL_CONTAINER;
import static mokiyoki.enhancedanimals.util.handlers.EventRegistry.ENHANCED_LLAMA;

public class EnhancedLlama extends AbstractChestedHorseEntity implements IRangedAttackMob, net.minecraftforge.common.IShearable, EnhancedAnimal {

    //avalible UUID spaces : [ S X X 3 X 5 X 7 - 8 9 10 11 - 12 13 14 15 - 16 17 18 19 - 20 21 22 23 24 25 26 27 28 29 30 31 ]

    private static final DataParameter<Integer> DATA_STRENGTH_ID = EntityDataManager.createKey(EnhancedLlama.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> DATA_INVENTORY_ID = EntityDataManager.createKey(EnhancedLlama.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> COAT_LENGTH = EntityDataManager.createKey(EnhancedLlama.class, DataSerializers.VARINT);
    private static final DataParameter<String> SHARED_GENES = EntityDataManager.<String>createKey(EnhancedLlama.class, DataSerializers.STRING);
    protected static final DataParameter<Boolean> SLEEPING = EntityDataManager.createKey(EnhancedLlama.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Integer> DATA_COLOR_ID = EntityDataManager.createKey(EnhancedLlama.class, DataSerializers.VARINT);
    private static final DataParameter<String> LLAMA_STATUS = EntityDataManager.createKey(EnhancedLlama.class, DataSerializers.STRING);
    private static final DataParameter<String> BIRTH_TIME = EntityDataManager.<String>createKey(EnhancedLlama.class, DataSerializers.STRING);

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

    private static final String[] LLAMA_TEXTURES_DECO = new String[] {
            "", "blanket_trader.png", "blanket_black.png", "blanket_blue.png", "blanket_brown.png", "blanket_cyan.png", "blanket_grey.png", "blanket_green.png", "blanket_lightblue.png", "blanket_lightgrey.png", "blanket_lime.png", "blanket_magenta.png", "blanket_orange.png", "blanket_pink.png", "blanket_purple.png", "blanket_red.png", "blanket_white.png", "blanket_yellow.png"
    };


    private final List<String> llamaTextures = new ArrayList<>();

    private static final Ingredient TEMPTATION_ITEMS = Ingredient.fromItems(Blocks.HAY_BLOCK, Items.WHEAT, Items.CARROT, Items.SUGAR_CANE, Items.BEETROOT, Items.GRASS, Items.TALL_GRASS);
    private static final Ingredient MILK_ITEMS = Ingredient.fromItems(ModItems.Milk_Bottle, ModItems.Half_Milk_Bottle);
    private static final Ingredient BREED_ITEMS = Ingredient.fromItems(Blocks.HAY_BLOCK);

    Map<Item, Integer> foodWeightMap = new HashMap() {{
        put(new ItemStack(Items.TALL_GRASS).getItem(), 6000);
        put(new ItemStack(Items.GRASS).getItem(), 3000);
        put(new ItemStack(Blocks.HAY_BLOCK).getItem(), 54000);
        put(new ItemStack(Items.CARROT).getItem(), 1500);
        put(new ItemStack(Items.WHEAT).getItem(), 6000);
        put(new ItemStack(Items.SUGAR).getItem(), 1500);
        put(new ItemStack(Items.APPLE).getItem(), 1500);
        put(new ItemStack(ModBlocks.UnboundHay_Block).getItem(), 54000);
    }};

    public float destPos;
    private String dropMeatType;

    private static final int WTC = EanimodCommonConfig.COMMON.wildTypeChance.get();
    private static final int GENES_LENGTH = 34;
    private int[] genes = new int[GENES_LENGTH];
    private int[] mateGenes = new int[GENES_LENGTH];
    private int[] mitosisGenes = new int[GENES_LENGTH];
    private int[] mateMitosisGenes = new int[GENES_LENGTH];

    protected int temper;

    private int maxCoatLength;
    private int currentCoatLength;
    private int timeForGrowth = 0;

    private int gestationTimer = 0;
    private boolean pregnant = false;

    private float hunger = 0;
    private int healTicks = 0;
    protected String motherUUID = "";
    protected Boolean sleeping = false;
    protected int awokenTimer = 0;

    private boolean didSpit;

    private int despawnDelay = -1;

    private EnhancedWaterAvoidingRandomWalkingEatingGoal wanderEatingGoal;

    @Nullable
    private EnhancedLlama caravanHead;
    @Nullable
    private EnhancedLlama caravanTail;

    public EnhancedLlama(EntityType<? extends EnhancedLlama> entityType, World worldIn) {
        super(entityType, worldIn);
//        this.setSize(0.9F, 1.87F);
        this.setPathPriority(PathNodeType.WATER, 0.0F);
    }

    @Override
    protected void registerGoals() {
        //Todo add the temperamants
        this.wanderEatingGoal = new EnhancedWaterAvoidingRandomWalkingEatingGoal(this, 1.0D, 7, 0.001F, 120, 2, 50);
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new ECRunAroundLikeCrazy(this, 1.2D));
        this.goalSelector.addGoal(2, new ECLlamaFollowCaravan(this, (double)2.1F));
        this.goalSelector.addGoal(3, new RangedAttackGoal(this, 1.25D, 40, 20.0F));
        this.goalSelector.addGoal(3, new EnhancedPanicGoal(this, 1.2D));
        this.goalSelector.addGoal(4, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(5, this.wanderEatingGoal);
        this.goalSelector.addGoal(5, new FollowParentGoal(this, 1.0D));
//        this.goalSelector.addGoal(6, new WaterAvoidingRandomWalkingGoal(this, 0.7D));
        this.goalSelector.addGoal(7, new EnhancedLookAtGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.addGoal(8, new EnhancedLookRandomlyGoal(this));
        this.targetSelector.addGoal(1, new EnhancedLlama.HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new EnhancedLlama.DefendTargetGoal(this));
    }


    protected void registerData() {
        super.registerData();
        this.dataManager.register(SHARED_GENES, new String());
        this.dataManager.register(DATA_STRENGTH_ID, 0);
        this.dataManager.register(DATA_INVENTORY_ID, 0);
        this.dataManager.register(COAT_LENGTH, -1);
        this.dataManager.register(DATA_COLOR_ID, -1);
        this.dataManager.register(SLEEPING, false);
        this.dataManager.register(LLAMA_STATUS, new String());
        this.dataManager.register(BIRTH_TIME, "0");
    }

    private void setLlamaStatus(String status) {
        this.dataManager.set(LLAMA_STATUS, status);
    }

    public String getLlamaStatus() {
        return this.dataManager.get(LLAMA_STATUS);
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

    protected void setBirthTime(String birthTime) {
        this.dataManager.set(BIRTH_TIME, birthTime);
    }

    public String getBirthTime() { return this.dataManager.get(BIRTH_TIME); }

    private int getAge() {
        if (!(getBirthTime() == null) && !getBirthTime().equals("") && !getBirthTime().equals(0)) {
            return (int)(this.world.getWorldInfo().getGameTime() - Long.parseLong(getBirthTime()));
        } else {
            return 500000;
        }
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

    @Override
    public Inventory getEnhancedInventory() {
        Inventory inventory =  new Inventory(15);
        inventory.setInventorySlotContents(2, new ItemStack(Items.APPLE));
        return inventory;
    }

    public float getHunger(){
        return hunger;
    }

    public void decreaseHunger(float decrease) {
        if (this.hunger - decrease < 0) {
            this.hunger = 0;
        } else {
            this.hunger = this.hunger - decrease;
        }
    }

    public void updatePassenger(Entity passenger) {
        if (this.isPassenger(passenger)) {
            float f = MathHelper.cos(this.renderYawOffset * ((float)Math.PI / 180F));
            float f1 = MathHelper.sin(this.renderYawOffset * ((float)Math.PI / 180F));
            float f2 = 0.3F;
            passenger.setPosition(this.getPosX() + (double)(0.3F * f1), this.getPosY() + this.getMountedYOffset() + passenger.getYOffset(), this.getPosZ() - (double)(0.3F * f));
        }
    }

    /**
     * Returns the Y offset from the entity's position for any entity riding this one.
     */
    public double getMountedYOffset() {
        return (double)this.getHeight() * 0.67D;
    }

    /**
     * returns true if all the conditions for steering the entity are met. For pigs, this is true if it is being ridden
     * by a player and the player is holding a carrot-on-a-stick
     */
    public boolean canBeSteered() {
        return false;
    }

    protected boolean handleEating(PlayerEntity player, ItemStack stack) {
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
            this.world.addParticle(ParticleTypes.HAPPY_VILLAGER, this.getPosX() + (double)(this.rand.nextFloat() * this.getWidth() * 2.0F) - (double)this.getWidth(), this.getPosY() + 0.5D + (double)(this.rand.nextFloat() * this.getWidth()), this.getPosZ() + (double)(this.rand.nextFloat() * this.getWidth() * 2.0F) - (double)this.getWidth(), 0.0D, 0.0D, 0.0D);
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
            this.world.playSound((PlayerEntity)null, this.getPosX(), this.getPosY(), this.getPosZ(), SoundEvents.ENTITY_LLAMA_EAT, this.getSoundCategory(), 1.0F, 1.0F + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F);
        }

        return flag;
    }

    /**
     * Dead and sleeping entities cannot move
     */
    protected boolean isMovementBlocked() {
        return this.getHealth() <= 0.0F || this.isEatingHaystack();
    }

    @Override
    public boolean processInteract(PlayerEntity entityPlayer, Hand hand) {
        ItemStack itemStack = entityPlayer.getHeldItem(hand);
        Item item = itemStack.getItem();

        if (!this.isChild()) {
            if (this.isTame() && entityPlayer.isSecondaryUseActive()) {
                this.openGUI(entityPlayer);
                return true;
            }

            if (this.isBeingRidden()) {
                return super.processInteract(entityPlayer, hand);
            }
        }

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
            } else if (item instanceof ShearsItem) {
                List<ItemStack> woolToDrop = onSheared(itemStack, null, null, 0);
                java.util.Random rand = new java.util.Random();
                woolToDrop.forEach(d -> {
                    net.minecraft.entity.item.ItemEntity ent = this.entityDropItem(d, 1.0F);
                    ent.setMotion(ent.getMotion().add((double)((rand.nextFloat() - rand.nextFloat()) * 0.1F), (double)(rand.nextFloat() * 0.05F), (double)((rand.nextFloat() - rand.nextFloat()) * 0.1F)));
                });
            } else if (!getLlamaStatus().equals(EntityState.CHILD_STAGE_ONE.toString()) && TEMPTATION_ITEMS.test(itemStack) && hunger >= 6000) {
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

    @Override
    public void openGUI(PlayerEntity playerEntity) {
        EnhancedAnimalInfo animalInfo = new EnhancedAnimalInfo();
        animalInfo.sleeping = this.isAnimalSleeping();
        animalInfo.testRandom = ThreadLocalRandom.current().nextInt();
        EnhancedAnimals.proxy.setEnhancedAnimalInfo(animalInfo);

        if (!this.world.isRemote && (!this.isBeingRidden() || this.isPassenger(playerEntity)) && this.isTame()) {
            this.openInfoInventory(this, this.horseChest, playerEntity);
        }

    }

    public void openInfoInventory(EnhancedLlama enhancedLlama, IInventory inventoryIn, PlayerEntity playerEntity) {
//        if (this.openContainer != this.container) {
//            this.closeScreen();
//        }
        if(!playerEntity.world.isRemote) {

            if(playerEntity instanceof ServerPlayerEntity) {
                ServerPlayerEntity entityPlayerMP = (ServerPlayerEntity)playerEntity;
                NetworkHooks.openGui(entityPlayerMP, new INamedContainerProvider() {
                    @Override
                    public Container createMenu(int windowId, PlayerInventory inventory, PlayerEntity player) {
                        return new EnhancedAnimalContainer(windowId, inventory, enhancedLlama);
                    }

                    @Override
                    public ITextComponent getDisplayName() {
                        return new TranslationTextComponent("eanimod.animalinfocontainer");
                    }
                });
            }
        }
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
        int days = EanimodCommonConfig.COMMON.gestationDaysLlama.get();
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

            if (this.despawnDelay != -1) {
                this.tryDespawn();
            }

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
                        int days = EanimodCommonConfig.COMMON.gestationDaysLlama.get();
                        if ((hunger <= days*(0.50)) && (ticksExisted % 2 == 0)) {
                            hunger = hunger++;
                        }
                        healTicks++;
                        if (healTicks > 100 && hunger < 6000 && this.getMaxHealth() > this.getHealth()) {
                            this.heal(2.0F);
                            hunger = hunger + 1000;
                            healTicks = 0;
                        }
                    } else {
                        hunger++;
                    }
                }


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

            if(pregnant) {
                gestationTimer++;
                int days = EanimodCommonConfig.COMMON.gestationDaysLlama.get();
                if (hunger > days*(0.75) && days !=0) {
                    pregnant = false;
                }
                if (gestationTimer >= days) {
                    pregnant = false;
                    gestationTimer = 0;

                    mixMateMitosisGenes();
                    mixMitosisGenes();
                    createAndSpawnEnhancedChild(this.world);
                }
            }

            if (this.isChild()) {
                if (getLlamaStatus().equals(EntityState.CHILD_STAGE_ONE.toString()) && this.getGrowingAge() < -16000) {
                    if(hunger < 5000) {
                        setLlamaStatus(EntityState.CHILD_STAGE_TWO.toString());
                    } else {
                        this.setGrowingAge(-16500);
                    }
                } else if (getLlamaStatus().equals(EntityState.CHILD_STAGE_TWO.toString()) && this.getGrowingAge() < -8000) {
                    if(hunger < 5000) {
                        setLlamaStatus(EntityState.CHILD_STAGE_THREE.toString());
                    } else {
                        this.setGrowingAge(-8500);
                    }
                }
            } else if (getLlamaStatus().equals(EntityState.CHILD_STAGE_THREE.toString())) {
                setLlamaStatus(EntityState.ADULT.toString());

                //TODO remove the child follow mother ai

            }

        }


    }

    protected void createAndSpawnEnhancedChild(World inWorld) {
        int[] babyGenes = getCriaGenes(this.mitosisGenes, this.mateMitosisGenes);
        EnhancedLlama enhancedllama = ENHANCED_LLAMA.create(this.world);
        enhancedllama.setGrowingAge(0);
        enhancedllama.setGenes(babyGenes);
        enhancedllama.setSharedGenes(babyGenes);
        enhancedllama.setStrengthAndInventory();
        enhancedllama.setMaxCoatLength();
        enhancedllama.currentCoatLength = enhancedllama.maxCoatLength;
        enhancedllama.setCoatLength(enhancedllama.currentCoatLength);
        enhancedllama.setGrowingAge(-120000);
        enhancedllama.setBirthTime(String.valueOf(inWorld.getGameTime()));
        enhancedllama.setLlamaStatus(EntityState.CHILD_STAGE_ONE.toString());
        enhancedllama.setLocationAndAngles(this.getPosX(), this.getPosY(), this.getPosZ(), this.rotationYaw, 0.0F);
//                        enhancedllama.setMotherUUID(this.getUniqueID().toString());
        this.world.addEntity(enhancedllama);
    }

    @OnlyIn(Dist.CLIENT)
    public boolean hasColor() {
        return this.getColor() != null;
    }

    @Override
    @Nullable
    protected ResourceLocation getLootTable() {

//        if (genes[20] == 1 || genes[21] == 1){
//            //drops leather
//            dropMeatType = "leather";
//        }else{
//            //drops wool
//            if (genes[6] == 1 || genes[7] == 1) {
//                dropMeatType = "white_wool";
//            } else if (genes[14] == 1 || genes[15] == 1) {
//                dropMeatType = "black_wool";
//            } else if (genes[14] == 3 || genes[15] == 3) {
//                dropMeatType = "brown_wool";
//            } else {
//                if (genes[16] == 1 || genes[17] == 1) {
//                    dropMeatType = "yellow_wool";
//                } else if (genes[16] >= 5 && genes[17] >= 5) {
//                    if (genes[16] == 5 || genes[17] == 5) {
//                        dropMeatType = "red_wool";
//                    } else {
//                        dropMeatType = "black_wool";
//                    }
//                } else {
//                    dropMeatType = "brown_wool";
//                }
//            }
//
//            if (dropMeatType.contains("black") && (genes[18] == 1 || genes[19] == 1)) {
//                dropMeatType = "gray_wool";
//            }
//
//            if (genes[10] == 2 && genes[11] == 2) {
//                boolean i = rand.nextBoolean();
//                if (i) {
//                    dropMeatType = "white_wool";
//                }
//            }
//
//            if (genes[12] == 1 || genes[13] == 1) {
//                boolean i = rand.nextBoolean();
//                if (i) {
//                    dropMeatType = "white_wool";
//                }
//            }
//
//            if (currentCoatLength == 4) {
//                dropMeatType = dropMeatType + "_two";
//            } else if (currentCoatLength == 3) {
//                boolean i = rand.nextBoolean();
//                if (i) {
//                    dropMeatType = dropMeatType + "_two";
//                }
//            }
//        }

//        return new ResourceLocation(Reference.MODID, "enhanced_llama");
        return null;
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

    protected void playStepSound(BlockPos pos, BlockState blockIn) {
        this.playSound(SoundEvents.ENTITY_LLAMA_STEP, 0.15F, 1.0F);
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
        if (!this.world.isRemote && !isChild()) {
            if (currentCoatLength == 1) {
                int i = this.rand.nextInt(4);
                if (i>3){
                    ret.add(new ItemStack(getWoolBlocks()));
                }
            } else if (currentCoatLength == 2) {
                int i = this.rand.nextInt(2);
                if (i>0){
                    ret.add(new ItemStack(getWoolBlocks()));
                }
            } else if (currentCoatLength == 3) {
                int i = this.rand.nextInt(4);
                if (i>0){
                    ret.add(new ItemStack(getWoolBlocks()));
                }
            } else if (currentCoatLength == 4) {
                ret.add(new ItemStack(getWoolBlocks()));
            }

        }
        currentCoatLength = -1;
        setCoatLength(currentCoatLength);
        return ret;
    }

    private Block getWoolBlocks () {
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
                ItemStack fleeceStack = new ItemStack(getWoolBlocks(), lootCount);
                this.entityDropItem(fleeceStack);
            } else {
                ItemStack leatherStack = new ItemStack(Items.LEATHER, lootCount);
                this.entityDropItem(leatherStack);
            }
        }
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


    public boolean isBreedingItem(ItemStack stack) {
        //TODO set this to a separate item or type of item for force breeding
        return BREED_ITEMS.test(stack);
    }

    public AgeableEntity createChild(AgeableEntity ageable) {
        if(pregnant) {
            ((EnhancedLlama)ageable).pregnant = true;
            ((EnhancedLlama)ageable).setMateGenes(this.genes);
            ((EnhancedLlama)ageable).mixMateMitosisGenes();
            ((EnhancedLlama)ageable).mixMitosisGenes();
        } else {
            pregnant = true;
            this.mateGenes = ((EnhancedLlama) ageable).getGenes();
            mixMateMitosisGenes();
            mixMitosisGenes();
        }

        this.setGrowingAge(10);
        this.resetInLove();
        ageable.setGrowingAge(10);
        ((EnhancedLlama)ageable).resetInLove();

        ServerPlayerEntity entityplayermp = this.getLoveCause();
        if (entityplayermp == null && ((EnhancedLlama)ageable).getLoveCause() != null) {
            entityplayermp = ((EnhancedLlama)ageable).getLoveCause();
        }

        if (entityplayermp != null) {
            entityplayermp.addStat(Stats.ANIMALS_BRED);
            CriteriaTriggers.BRED_ANIMALS.trigger(entityplayermp, this, ((EnhancedLlama)ageable), (AgeableEntity)null);
        }

        return null;
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

        int blanket = 0;
        if (this.isLeashedToTrader()) {
            blanket = 1;
        } else {
            blanket = 17;
        }


        if (blanket != 0) {
            this.llamaTextures.add(LLAMA_TEXTURES_DECO[blanket]);
        }

    } // setTexturePaths end bracket



    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);

        //store this llamas's genes
        ListNBT geneList = new ListNBT();
        for (int i = 0; i < genes.length; i++) {
            CompoundNBT nbttagcompound = new CompoundNBT();
            nbttagcompound.putInt("Gene", genes[i]);
            geneList.add(nbttagcompound);
        }
        compound.put("Genes", geneList);

        //store this llamas's mate's genes
        ListNBT mateGeneList = new ListNBT();
        for (int i = 0; i < mateGenes.length; i++) {
            CompoundNBT nbttagcompound = new CompoundNBT();
            nbttagcompound.putInt("Gene", mateGenes[i]);
            mateGeneList.add(nbttagcompound);
        }
        compound.put("FatherGenes", mateGeneList);

        compound.putInt("Strength", this.getStrength());
        compound.putInt("Inventory", this.getInventory());
        compound.putFloat("CoatLength", this.getCoatLength());
        if (!this.horseChest.getStackInSlot(1).isEmpty()) {
            compound.put("DecorItem", this.horseChest.getStackInSlot(1).write(new CompoundNBT()));
        }

        compound.putBoolean("Pregnant", this.pregnant);
        compound.putInt("Gestation", this.gestationTimer);

        compound.putString("Status", getLlamaStatus());
        compound.putFloat("Hunger", hunger);

        compound.putString("BirthTime", this.getBirthTime());

        if (this.despawnDelay != -1) {
            compound.putInt("DespawnDelay", this.despawnDelay);
        }

    }

    public void readAdditional(CompoundNBT compound) {
        this.setStrength(compound.getInt("Strength"));
        this.setInventory(compound.getInt("Inventory"));
        currentCoatLength = compound.getInt("CoatLength");
        this.setCoatLength(currentCoatLength);
        super.readAdditional(compound);

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

        setLlamaStatus(compound.getString("Status"));
        hunger = compound.getFloat("Hunger");

        this.setBirthTime(compound.getString("BirthTime"));

        if (compound.contains("DespawnDelay", 99)) {
            this.despawnDelay = compound.getInt("DespawnDelay");
        }

        setSharedGenes(genes);

        if (compound.contains("DecorItem", 10)) {
            this.horseChest.setInventorySlotContents(1, ItemStack.read(compound.getCompound("DecorItem")));
        }

        this.updateHorseSlots();

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

    public int[] getCriaGenes(int[] mitosis, int[] mateMitosis) {
        Random rand = new Random();
        int[] criaGenes = new int[GENES_LENGTH];

        for (int i = 0; i < genes.length; i = (i + 2)) {
            boolean thisOrMate = rand.nextBoolean();
            if (thisOrMate) {
                criaGenes[i] = mitosis[i];
                criaGenes[i+1] = mateMitosis[i+1];
            } else {
                criaGenes[i] = mateMitosis[i];
                criaGenes[i+1] = mitosis[i+1];
            }
        }

        return criaGenes;
    }

    @Nullable
    @Override
    public ILivingEntityData onInitialSpawn(IWorld inWorld, DifficultyInstance difficulty, SpawnReason spawnReason, @Nullable ILivingEntityData livingdata, @Nullable CompoundNBT itemNbt) {
//        livingdata = super.onInitialSpawn(inWorld, difficulty, spawnReason, livingdata, itemNbt);
        int[] spawnGenes;

        if (spawnReason == SpawnReason.EVENT) {
            this.targetSelector.addGoal(1, new EnhancedLlama.FollowTraderGoal(this));
            this.despawnDelay = 49999;
        }

        if (livingdata instanceof GroupData) {
            int[] spawnGenes1 = ((GroupData) livingdata).groupGenes;
            int[] mitosis = new int[GENES_LENGTH];
            punnetSquare(mitosis, spawnGenes1);

            int[] spawnGenes2 = ((GroupData) livingdata).groupGenes;
            int[] mateMitosis = new int[GENES_LENGTH];
            punnetSquare(mateMitosis, spawnGenes2);
            spawnGenes = getCriaGenes(mitosis, mateMitosis);
        } else {
            spawnGenes = createInitialGenes(inWorld);
            livingdata = new GroupData(spawnGenes);
        }

        this.genes = spawnGenes;
        setSharedGenes(genes);
        setStrengthAndInventory();
        setMaxCoatLength();
        this.currentCoatLength = this.maxCoatLength;
        setCoatLength(this.currentCoatLength);

        int birthMod = ThreadLocalRandom.current().nextInt(20000, 500000);
        this.setBirthTime(String.valueOf(inWorld.getWorld().getGameTime() - birthMod));
        if (birthMod < 120000) {
            this.setGrowingAge(birthMod - 120000);
        }

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

    private int[] createInitialGenes(IWorld inWorld) {
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

        //nose placement genes [ +0.1, +0.15/+0.05, 0, -0.1 ]
        if(ThreadLocalRandom.current().nextInt(100)>WTC){
            initialGenes[28] = (ThreadLocalRandom.current().nextInt(4)+1);

        } else {
            initialGenes[28] = (2);
        }
        if(ThreadLocalRandom.current().nextInt(100)>WTC){
            initialGenes[29] = (ThreadLocalRandom.current().nextInt(4)+1);

        } else {
            initialGenes[29] = (2);
        }

        //nose placement genes [ +0.1, +0.15/0.05, 0, -0.1 ]
        if(ThreadLocalRandom.current().nextInt(100)>WTC){
            initialGenes[30] = (ThreadLocalRandom.current().nextInt(4)+1);

        } else {
            initialGenes[30] = (1);
        }
        if(ThreadLocalRandom.current().nextInt(100)>WTC){
            initialGenes[31] = (ThreadLocalRandom.current().nextInt(4)+1);

        } else {
            initialGenes[31] = (1);
        }

        //nose placement genes [ +0.2, +0.15, 0, -0.15, -0.2 ]
        if(ThreadLocalRandom.current().nextInt(100)>WTC){
            initialGenes[32] = (ThreadLocalRandom.current().nextInt(5)+1);

        } else {
            initialGenes[32] = (1);
        }
        if(ThreadLocalRandom.current().nextInt(100)>WTC){
            initialGenes[33] = (ThreadLocalRandom.current().nextInt(5)+1);

        } else {
            initialGenes[33] = (1);
        }



        return initialGenes;
    }



    public void setGenes(int[] genes) {
        this.genes = genes;
    }

    public int[] getGenes() {
        return this.genes;
    }

    public void setMateGenes(int[] mateGenes){
        this.mateGenes = mateGenes;
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
            this.setColor(getCarpetColor(this.horseChest.getStackInSlot(1)));
        }
    }

    private void setColor(@Nullable DyeColor color) {
        this.dataManager.set(DATA_COLOR_ID, color == null ? -1 : color.getId());
    }

    @Nullable
    private static DyeColor getCarpetColor(ItemStack p_195403_0_) {
        Block block = Block.getBlockFromItem(p_195403_0_.getItem());
        return block instanceof CarpetBlock ? ((CarpetBlock)block).getColor() : null;
    }

    @Nullable
    public DyeColor getColor() {
        int i = this.dataManager.get(DATA_COLOR_ID);
        return i == -1 ? null : DyeColor.byId(i);
    }

    public boolean canMateWith(AnimalEntity otherAnimal) {
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

    public void setSwingingArms(boolean swingingArms) {
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

    public static class GroupData implements ILivingEntityData {

        public int[] groupGenes;

        public GroupData(int[] groupGenes) {
            this.groupGenes = groupGenes;
        }

    }
}
