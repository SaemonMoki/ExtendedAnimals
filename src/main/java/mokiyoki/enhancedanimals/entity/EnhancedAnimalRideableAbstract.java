package mokiyoki.enhancedanimals.entity;

//import mokiyoki.enhancedanimals.ai.general.EnhancedTemptGoal;
import mokiyoki.enhancedanimals.entity.util.Colouration;
import mokiyoki.enhancedanimals.entity.util.Equipment;
import mokiyoki.enhancedanimals.init.ModItems;
import mokiyoki.enhancedanimals.items.CustomizableBridle;
import mokiyoki.enhancedanimals.items.CustomizableCollar;
import mokiyoki.enhancedanimals.items.CustomizableSaddleEnglish;
import mokiyoki.enhancedanimals.items.CustomizableSaddleVanilla;
import mokiyoki.enhancedanimals.items.CustomizableSaddleWestern;
import mokiyoki.enhancedanimals.items.DebugGenesBook;
import mokiyoki.enhancedanimals.items.MilkBottleHalf;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PlayerRideableJumping;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.Container;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SaddleItem;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.item.TridentItem;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public abstract class EnhancedAnimalRideableAbstract extends EnhancedAnimalChestedAbstract implements PlayerRideableJumping {
    //TODO needs something universal to tell the model that what the equipment is

    private static final EntityDataAccessor<Byte> STATUS = SynchedEntityData.defineId(EnhancedAnimalRideableAbstract.class, EntityDataSerializers.BYTE);
    protected static final Attribute JUMP_STRENGTH = (new RangedAttribute( "ea.jumpStrength", 0.7D, 0.0D, 2.0D)).setSyncable(true);
    private static final EntityDataAccessor<Boolean> HAS_SADDLE = SynchedEntityData.defineId(EnhancedAnimalRideableAbstract.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> BOOST_TIME = SynchedEntityData.defineId(EnhancedAnimalRideableAbstract.class, EntityDataSerializers.INT);

    private static final String[] TEXTURES_SADDLE = new String[] {
            "d_saddle_vanilla.png", "d_saddle_western.png", "d_saddle_english.png"
    };

    private static final String[] TEXTURES_SADDLE_LEATHER = new String[] {
            "saddle_vanilla_leather.png", "saddle_western_leather.png", "saddle_english_leather.png",
            "saddle_vanilla_clothseat.png", "saddle_western_clothseat.png", "saddle_english_clothseat.png"
    };

    private static final String[] TEXTURES_SADDLE_HARDWARE = new String[] {
            "stirrups_iron.png", "stirrups_gold.png", "stirrups_diamond.png", "stirrups_wood.png",
            "stirrups_western_iron.png","stirrups_western_gold.png", "stirrups_western_diamond.png", "stirrups_western_wood.png"
    };

    protected boolean isAnimalJumping;
    protected float jumpPower;
    private float prevRearingAmount;
    private int jumpRearingCounter;
    protected int gallopTime;
    private boolean allowStandSliding;
    private boolean boosting;
    private int boostTime;
    private int totalBoostTime;
    private int maxRideTime;
    private int rideTime;
    protected float playerJumpPendingScale;
    protected int gallopSoundCounter;
    protected float dwarf = -1.0F;

    protected int temper;

    protected EnhancedAnimalRideableAbstract(EntityType<? extends EnhancedAnimalAbstract> type, Level worldIn, int SgenesSize, int AgenesSize, boolean bottleFeedable) {
        super(type, worldIn, SgenesSize, AgenesSize, bottleFeedable);
        this.maxUpStep = 1.1F;
    }

    protected static void registerAnimalAttributes() {
        Mob.createMobAttributes().add(JUMP_STRENGTH);
    }

//    public static AttributeModifierMap.MutableAttribute createBaseHorseAttributes() {
//        return MobEntity.createMobAttributes().createMutableAttribute(JUMP_STRENGTH);
//    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(STATUS, (byte)0);
        this.entityData.define(HAS_SADDLE, false);
        this.entityData.define(BOOST_TIME, 0);
    }

    public void onSyncedDataUpdated(EntityDataAccessor<?> key) {
        if (BOOST_TIME.equals(key) && this.level.isClientSide) {
            this.boosting = true;
            this.boostTime = 0;
            this.totalBoostTime = this.entityData.get(BOOST_TIME);
        }

        super.onSyncedDataUpdated(key);
    }

    @Override
    public Boolean isAnimalSleeping() {
        if (this.entityData.get(HAS_SADDLE)) {
            return false;
        } else if (this.isVehicle()) {
            return false;
        }
            return super.isAnimalSleeping();
    }

    protected boolean getRideableWatchableBoolean(int byteNumber) {
        return (this.entityData.get(STATUS) & byteNumber) != 0;
    }

    protected void setRideableWatchableBoolean(int byteNumber, boolean p_110208_2_) {
        byte b0 = this.entityData.get(STATUS);
        if (p_110208_2_) {
            this.entityData.set(STATUS, (byte)(b0 | byteNumber));
        } else {
            this.entityData.set(STATUS, (byte)(b0 & ~byteNumber));
        }
    }

    @Override
    public boolean causeFallDamage(float distance, float damageMultiplier, DamageSource damageSource) {
        if (distance > 1.0F) {
            this.playSound(SoundEvents.HORSE_LAND, 0.4F, 1.0F);
        }

        int i = this.calculateFallDamage(distance, damageMultiplier);
        if (i <= 0) {
            return false;
        } else {
            if (this.isVehicle()) {
                for(Entity entity : this.getIndirectPassengers()) {
                    entity.hurt(DamageSource.FALL, (float)i);
                }
            } else {
                this.hurt(DamageSource.FALL, (float)i);
            }

            this.playBlockFallSound();
            return false;
        }
    }

    @Override
    protected int calculateFallDamage(float distance, float damageMultiplier) {
        return Mth.ceil((distance * 0.5F - 3.0F) * damageMultiplier);
    }

    public boolean isAnimalJumping() {
        return this.isAnimalJumping;
    }

    public void setAnimalJumping(boolean jumping) {
        this.isAnimalJumping = jumping;
    }

    public double getJumpStrength() {
        return this.getAttribute(JUMP_STRENGTH).getValue();
    }


    @Override
    public void onPlayerJump(int jumpPowerIn) {
        if (this.entityData.get(HAS_SADDLE)) {
            if (jumpPowerIn < 0) {
                jumpPowerIn = 0;
            } else {
                this.allowStandSliding = true;
                this.makeRear();
            }

            if (jumpPowerIn >= 90) {
                this.jumpPower = 1.0F;
            } else {
                this.jumpPower = 0.4F + 0.4F * (float)jumpPowerIn / 90.0F;
            }
        }
    }

    public boolean canJump() {
        return this.entityData.get(HAS_SADDLE);
    }

    public void handleStartJump(int p_184775_1_) {
        this.allowStandSliding = true;
        this.makeRear();
    }

    public void handleStopJump() {
    }

    private void makeRear() {
        if (this.isControlledByLocalInstance() || this.isEffectiveAi()) {
            this.jumpRearingCounter = 1;
            this.setRearing(true);
        }

    }

    //Byte 32 is rearing
    public boolean isRearing() {
        return this.getRideableWatchableBoolean(32);
    }

    public void setRearing(boolean rearing) {
        this.setRideableWatchableBoolean(32, rearing);
    }

    protected void playStepSound(BlockPos pos, BlockState blockIn) {
        if (!blockIn.getMaterial().isLiquid()) {
            BlockState blockstate = this.level.getBlockState(pos.above());
            SoundType soundtype = blockIn.getSoundType(level, pos, this);
            if (blockstate.getBlock() == Blocks.SNOW) {
                soundtype = blockstate.getSoundType(level, pos, this);
            }

            if (this.isVehicle()) {
                ++this.gallopTime;
                if (this.gallopTime > 5 && this.gallopTime % 3 == 0) {
                    this.playGallopSound(soundtype);
                } else if (this.gallopTime <= 5) {
                    this.playSound(SoundEvents.HORSE_STEP_WOOD, soundtype.getVolume() * 0.15F, soundtype.getPitch());
                }
            }
        }
    }

    protected void playGallopSound(SoundType soundtype) {
        this.playSound(SoundEvents.HORSE_GALLOP, soundtype.getVolume() * 0.15F, soundtype.getPitch());
    }

    @Override
    public boolean canHaveSaddle() {
        return getEnhancedAnimalAge() >= (3*getAdultAge()/4);
    }

    public void setSaddled(boolean saddled) {
        this.entityData.set(HAS_SADDLE, saddled);
        List<String> previousSaddleTextures = equipmentTextures.get(Equipment.SADDLE);
        List<String> newSaddleTextures = getSaddleTextures();

        if(saddled) {
//            if(previousSaddleTextures == null || !previousSaddleTextures.containsAll(newSaddleTextures)){
                equipmentTextures.put(Equipment.SADDLE, newSaddleTextures);
//            }
        } else {
            if(previousSaddleTextures != null){
                equipmentTextures.remove(Equipment.SADDLE);
            }
        }
        this.compiledEquipmentTexture = null; //reset compiled string
    }

    @Override
    protected void updateInventorySlots() {
        ItemStack saddleStack = this.animalInventory.getItem(1);
        this.setSaddled(!saddleStack.isEmpty() && !(saddleStack.getItem() instanceof CustomizableCollar) && this.canHaveSaddle());
        super.updateInventorySlots();
    }

    public void makeMad() {
        SoundEvent soundevent = this.getAngrySound();
        if (soundevent != null) {
            this.playSound(soundevent, this.getSoundVolume(), this.getVoicePitch());
        }

    }

    @Nullable
    protected SoundEvent getAngrySound() {
        this.makeRear();
        return null;
    }

    public int increaseTemper(int p_110198_1_) {
        int i = Mth.clamp(this.getTemper() + p_110198_1_, 0, this.getMaxTemper());
        this.setTemper(i);
        return i;
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

    @Override
    public InteractionResult mobInteract(Player entityPlayer, InteractionHand hand) {
        ItemStack itemStack = entityPlayer.getItemInHand(hand);
        Item item = itemStack.getItem();
        int age = getAge();
        int adultAge = getAdultAge();

        if (age >= (3*adultAge)/4 && (item == Items.SADDLE || item instanceof CustomizableSaddleVanilla || item instanceof CustomizableSaddleWestern || item instanceof CustomizableSaddleEnglish)){
            return InteractionResult.sidedSuccess(this.saddleAnimal(itemStack, entityPlayer, hand, this));
        }

        if ((!this.level.isClientSide && this.isFoodItem(itemStack)) || (!this.level.isClientSide && isBreedingItem(itemStack)) || item instanceof DebugGenesBook || (this.canHaveBridle() && item instanceof CustomizableBridle) || (this.canHaveBlanket() && isCarpet(itemStack)) || item instanceof CustomizableCollar) {
            return super.mobInteract(entityPlayer, hand);
        }

        if (this.isVehicle()) {
            return super.mobInteract(entityPlayer, hand);
        } else if (!entityPlayer.isSecondaryUseActive() && age >= adultAge && !(hand.equals(InteractionHand.OFF_HAND)) && (itemStack == ItemStack.EMPTY || itemStack.isEmpty() || item instanceof TieredItem || item instanceof TridentItem || item instanceof ProjectileWeaponItem)) {
            this.mountTo(entityPlayer);
            return InteractionResult.SUCCESS;
        }

        return super.mobInteract(entityPlayer, hand);
    }

    public void equipAnimal(boolean hasChest, boolean hasSaddle, DyeColor blanketColour) {
        if (hasSaddle) {
            this.animalInventory.setItem(1, new ItemStack(Items.SADDLE, 1));
            this.setSaddled(true);
        }
        super.equipAnimal(hasChest, blanketColour);
    }

    public boolean saddleAnimal(ItemStack saddleItemStack, Player player, InteractionHand hand, LivingEntity target) {
        EnhancedAnimalRideableAbstract enhancedAnimal = (EnhancedAnimalRideableAbstract) target;
        if (enhancedAnimal.isAlive() && !enhancedAnimal.isBaby()) {
            ItemStack otherSaddle = this.getEnhancedInventory().getItem(1);
            if (saddleItemStack.getItem() == Items.SADDLE) {
                this.animalInventory.setItem(1, new ItemStack(saddleItemStack.getItem(), 1));
            } else {
                this.animalInventory.setItem(1, getReplacementItemWithColour(saddleItemStack));
            }
            this.playSound(SoundEvents.HORSE_SADDLE, 0.5F, 1.0F);
            saddleItemStack.shrink(1);
            if (!otherSaddle.isEmpty()) {
                player.setItemInHand(hand, otherSaddle);
            }
        }
        return true;
    }

    public void containerChanged(Container invBasic) {
        boolean flag = this.entityData.get(HAS_SADDLE);
        this.updateInventorySlots();
        if (this.tickCount > 20 && !flag && this.entityData.get(HAS_SADDLE)) {
            this.playSound(SoundEvents.HORSE_SADDLE, 0.5F, 1.0F);
        }
    }

    @Override
    public boolean setSlot(int inventorySlot, ItemStack itemStackIn) {
        int i = inventorySlot - 400;
        if (i >= 0 && i < 2 && i < this.animalInventory.getContainerSize()) {
            if (i != 1 && (itemStackIn.getItem() instanceof CustomizableSaddleVanilla || itemStackIn.getItem() == Items.SADDLE)) {
                this.animalInventory.setItem(i, itemStackIn);
                this.updateInventorySlots();
                return true;
            } else {
                return false;
            }
        }

        return super.setSlot(inventorySlot, itemStackIn);
    }

    @Nullable
    public Entity getControllingPassenger() {
        return this.getPassengers().isEmpty() ? null : this.getPassengers().get(0);
    }

    protected void mountTo(Player player) {
        this.setRearing(false);
        if (!this.level.isClientSide) {
            player.setYRot(this.getYRot());
            player.setXRot(this.getXRot());
            player.startRiding(this);
        }
    }

    @Override
    public boolean canBeControlledByRider() {
        Entity entity = this.getControllingPassenger();
        if (!(entity instanceof Player)) {
            return false;
        } else {
            Player playerentity = (Player)entity;
            if (playerentity.getMainHandItem().getItem() == Items.CARROT_ON_A_STICK || playerentity.getOffhandItem().getItem() == Items.CARROT_ON_A_STICK) {
                return true;
            }
            return super.canBeControlledByRider();
        }
    }

    @Override
    public void travel(Vec3 p_213352_1_) {
        //TODO bareback and blankets need to be options for riding with a drawback for how long you can ride.
        //TODO maybe create some different effects on speed, jump height and ride time based on the saddle.
        //TODO different animals (especially equines) should make more or less of a bobbing animation depending on the individual animal
        if (this.isAlive()) {
            if (this.isVehicle() && this.canBeControlledByRider()) {
                LivingEntity livingentity = (LivingEntity) this.getControllingPassenger();
                if (this.hasBridle()) {
                    this.setYRot(livingentity.getYRot());
                    this.yRotO = this.getYRot();
                    this.setXRot(livingentity.getXRot() * 0.5F);
                    this.setRot(this.getYRot(), this.getXRot());
                    this.yBodyRot = this.getYRot();
                    this.yHeadRot = this.yBodyRot;
                    float f = livingentity.xxa * 0.5F;
                    float f1 = livingentity.zza;
                    if (f1 <= 0.0F) {
                        f1 *= 0.25F;
                        this.gallopSoundCounter = 0;
                    }

                    if (this.onGround && this.playerJumpPendingScale == 0.0F && !this.allowStandSliding) {
                        f = 0.0F;
                        f1 = 0.0F;
                    }

                    if (this.jumpPower > 0.0F && !this.isAnimalJumping() && this.onGround) {
                        double d0 = this.getJumpHeight() * (double) this.jumpPower * (double) this.getBlockJumpFactor();
                        double d1;
                        if (this.hasEffect(MobEffects.JUMP)) {
                            d1 = d0 + (double) ((float) (this.getEffect(MobEffects.JUMP).getAmplifier() + 1) * 0.1F);
                        } else {
                            d1 = d0;
                        }

                        Vec3 vec3d = this.getDeltaMovement();
                        this.setDeltaMovement(vec3d.x, d1, vec3d.z);
                        this.setAnimalJumping(true);
                        this.hasImpulse = true;
                        if (f1 > 0.0F) {
                            float f2 = Mth.sin(this.getYRot() * ((float) Math.PI / 180F));
                            float f3 = Mth.cos(this.getYRot() * ((float) Math.PI / 180F));
                            this.setDeltaMovement(this.getDeltaMovement().add((double) (-0.4F * f2 * this.jumpPower), 0.0D, (double) (0.4F * f3 * this.jumpPower)));
                            this.playJumpSound();
                        }

                        this.jumpPower = 0.0F;
                    }

                    this.flyingSpeed = this.getSpeed() * getJumpFactorModifier();
                    if (this.isControlledByLocalInstance()) {
                        this.setSpeed((float) this.getAttribute(Attributes.MOVEMENT_SPEED).getValue() * getMovementFactorModifier());
                        super.travel(new Vec3((double) f, p_213352_1_.y, (double) f1));
                    } else if (livingentity instanceof Player) {
                        this.setDeltaMovement(Vec3.ZERO);
                    }

                    if (this.onGround) {
                        this.jumpPower = 0.0F;
                        this.setAnimalJumping(false);
                    }

                    this.animationSpeedOld = this.animationSpeed;
                    double d2 = this.getX() - this.xo;
                    double d3 = this.getZ() - this.zo;
                    float f4 = Mth.sqrt((float) (d2 * d2 + d3 * d3)) * 4.0F;
                    if (f4 > 1.0F) {
                        f4 = 1.0F;
                    }

                    this.animationSpeed += (f4 - this.animationSpeed) * 0.4F;
                    this.animationPosition += this.animationSpeed;
                } else {
                    this.setYRot(livingentity.getYRot());
                    this.yRotO = this.getYRot();
                    this.setXRot(livingentity.getXRot() * 0.5F);
                    this.setRot(this.getYRot(), this.getXRot());
                    this.yBodyRot = this.getYRot();
                    this.yHeadRot = this.getYRot();
                    this.maxUpStep = 1.1F;
                    this.flyingSpeed = this.getSpeed() * getJumpFactorModifier();
                    if (this.boosting && this.boostTime++ > this.totalBoostTime) {
                        this.boosting = false;
                    }

                    if (this.isControlledByLocalInstance()) {
                        float f = (float)this.getAttribute(Attributes.MOVEMENT_SPEED).getValue() * getMovementFactorModifier() * 0.225F;
                        if (this.boosting) {
                            f += f * 1.15F * Mth.sin((float)this.boostTime / (float)this.totalBoostTime * (float)Math.PI);
                        }

                        this.setSpeed(f);
                        super.travel(new Vec3(0.0D, 0.0D, 1.0D));
                        this.lerpSteps = 0;
                    } else {
                        this.setDeltaMovement(Vec3.ZERO);
                    }

                    this.animationSpeedOld = this.animationSpeed;
                    double d1 = this.getX() - this.xo;
                    double d0 = this.getZ() - this.zo;
                    float f1 = Mth.sqrt((float) (d1 * d1 + d0 * d0)) * 4.0F;
                    if (f1 > 1.0F) {
                        f1 = 1.0F;
                    }

                    this.animationSpeed += (f1 - this.animationSpeed) * 0.4F;
                    this.animationPosition += this.animationSpeed;
                }
            } else {
                this.maxUpStep = 1.1F;
                this.flyingSpeed = 0.02F;
                super.travel(p_213352_1_);
            }
        }
    }

    protected float getJumpHeight() {
        return 1F;
    }

    protected float getJumpFactorModifier() {
        return 0.1F;
    }

    protected float getMovementFactorModifier() {
        return 1F;
    }

    public boolean boost() {
        if (this.boosting) {
            return false;
        } else {
            this.boosting = true;
            this.boostTime = 0;
            this.totalBoostTime = this.getRandom().nextInt(841) + 140;
            this.getEntityData().set(BOOST_TIME, this.totalBoostTime);
            return true;
        }
    }

    protected void playJumpSound() {
        this.playSound(SoundEvents.HORSE_JUMP, 0.4F, 1.0F);
    }

    @OnlyIn(Dist.CLIENT)
    public void handleEntityEvent(byte id) {
        if (id == 7) {
            this.spawnParticles(true);
        } else if (id == 6) {
            this.spawnParticles(false);
        } else {
            super.handleEntityEvent(id);
        }
    }

    @OnlyIn(Dist.CLIENT)
    protected void spawnParticles(boolean p_110216_1_) {
        ParticleOptions iparticledata = p_110216_1_ ? ParticleTypes.HEART : ParticleTypes.SMOKE;

        for(int i = 0; i < 7; ++i) {
            double d0 = this.random.nextGaussian() * 0.02D;
            double d1 = this.random.nextGaussian() * 0.02D;
            double d2 = this.random.nextGaussian() * 0.02D;
            this.level.addParticle(iparticledata, this.getRandomX(1.0D), this.getRandomY() + 0.5D, this.getRandomZ(1.0D), d0, d1, d2);
        }
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public Colouration getRgb() {
        this.colouration = super.getRgb();
        ItemStack saddle = this.getEnhancedInventory().getItem(1);
        if (saddle.getItem() instanceof SaddleItem) {
            this.colouration.setSaddleColour(Colouration.getEquipmentColor(saddle));
        }

//        if (armour != ItemStack.EMPTY) {
//            this.colouration.setHarnessColour(Colouration.getEquipmentColor(armour));
//        }

        return this.colouration;
    }

    private List<String> getSaddleTextures() {
        List<String> saddleTextures = new ArrayList<>();

        if (this.getEnhancedInventory() != null) {
            ItemStack saddleSlot = this.animalInventory.getItem(1);
            if (saddleSlot != ItemStack.EMPTY) {
                Item saddle = saddleSlot.getItem();
                if (saddle == ModItems.SADDLE_BASIC_CLOTH.get()) {
                    setSaddledTextures(saddleTextures, 0, -1, 0);
                } else if (saddle == ModItems.SADDLE_BASIC_CLOTH_GOLD.get()) {
                    setSaddledTextures(saddleTextures, 0, -1, 1);
                } else if (saddle == ModItems.SADDLE_BASIC_CLOTH_DIAMOND.get()) {
                    setSaddledTextures(saddleTextures, 0, -1, 2);
                } else if (saddle == ModItems.SADDLE_BASIC_CLOTH_WOOD.get()) {
                    setSaddledTextures(saddleTextures, 0, -1, 3);
                } else if (saddle == ModItems.SADDLE_BASIC_LEATHER.get() || saddle == Items.SADDLE) {
                    setSaddledTextures(saddleTextures,0,0, 0 );
                } else if (saddle == ModItems.SADDLE_BASIC_LEATHER_GOLD.get()) {
                    setSaddledTextures(saddleTextures,0,0, 1 );
                } else if (saddle == ModItems.SADDLE_BASIC_LEATHER_DIAMOND.get()) {
                    setSaddledTextures(saddleTextures,0,0, 2 );
                } else if (saddle == ModItems.SADDLE_BASIC_LEATHER_WOOD.get()) {
                    setSaddledTextures(saddleTextures,0,0, 3 );
                } else if (saddle == ModItems.SADDLE_BASIC_LEATHERCLOTHSEAT.get()) {
                    setSaddledTextures(saddleTextures,0,3, 0 );
                } else if (saddle == ModItems.SADDLE_BASIC_LEATHERCLOTHSEAT_GOLD.get()) {
                    setSaddledTextures(saddleTextures,0,3, 1 );
                } else if (saddle == ModItems.SADDLE_BASIC_LEATHERCLOTHSEAT_DIAMOND.get()) {
                    setSaddledTextures(saddleTextures,0,3, 2 );
                } else if (saddle == ModItems.SADDLE_BASIC_LEATHERCLOTHSEAT_WOOD.get()) {
                    setSaddledTextures(saddleTextures,0,3, 3 );
                } else if (saddle == ModItems.SADDLE_BASICPOMEL_CLOTH.get()) {
                    setSaddledTextures(saddleTextures, 1, -1, 4);
                } else if (saddle == ModItems.SADDLE_BASICPOMEL_CLOTH_GOLD.get()) {
                    setSaddledTextures(saddleTextures, 1, -1, 5);
                } else if (saddle == ModItems.SADDLE_BASICPOMEL_CLOTH_DIAMOND.get()) {
                    setSaddledTextures(saddleTextures, 1, -1, 6);
                } else if (saddle == ModItems.SADDLE_BASICPOMEL_CLOTH_WOOD.get()) {
                    setSaddledTextures(saddleTextures, 1, -1, 7);
                } else if (saddle == ModItems.SADDLE_BASICPOMEL_LEATHER.get()) {
                    setSaddledTextures(saddleTextures,1,1, 4 );
                } else if (saddle == ModItems.SADDLE_BASICPOMEL_LEATHER_GOLD.get()) {
                    setSaddledTextures(saddleTextures,1,1, 5 );
                } else if (saddle == ModItems.SADDLE_BASICPOMEL_LEATHER_DIAMOND.get()) {
                    setSaddledTextures(saddleTextures,1,1, 6 );
                } else if (saddle == ModItems.SADDLE_BASICPOMEL_LEATHER_WOOD.get()) {
                    setSaddledTextures(saddleTextures,1,1, 7 );
                } else if (saddle == ModItems.SADDLE_BASICPOMEL_LEATHERCLOTHSEAT.get()) {
                    setSaddledTextures(saddleTextures,1,4, 4 );
                } else if (saddle == ModItems.SADDLE_BASICPOMEL_LEATHERCLOTHSEAT_GOLD.get()) {
                    setSaddledTextures(saddleTextures,1,4, 5 );
                } else if (saddle == ModItems.SADDLE_BASICPOMEL_LEATHERCLOTHSEAT_DIAMOND.get()) {
                    setSaddledTextures(saddleTextures,1,4, 6 );
                } else if (saddle == ModItems.SADDLE_BASICPOMEL_LEATHERCLOTHSEAT_WOOD.get()) {
                    setSaddledTextures(saddleTextures,1,4, 7 );
                }if (saddle == ModItems.SADDLE_ENGLISH_CLOTH.get()) {
                    setSaddledTextures(saddleTextures, 2, -1, 0);
                } else if (saddle == ModItems.SADDLE_ENGLISH_CLOTH_GOLD.get()) {
                    setSaddledTextures(saddleTextures, 2, -1, 1);
                } else if (saddle == ModItems.SADDLE_ENGLISH_CLOTH_DIAMOND.get()) {
                    setSaddledTextures(saddleTextures, 2, -1, 2);
                } else if (saddle == ModItems.SADDLE_ENGLISH_CLOTH_WOOD.get()) {
                    setSaddledTextures(saddleTextures, 2, -1, 3);
                } else if (saddle == ModItems.SADDLE_ENGLISH_LEATHER.get()) {
                    setSaddledTextures(saddleTextures,2,2, 0 );
                } else if (saddle == ModItems.SADDLE_ENGLISH_LEATHER_GOLD.get()) {
                    setSaddledTextures(saddleTextures,2,2, 1 );
                } else if (saddle == ModItems.SADDLE_ENGLISH_LEATHER_DIAMOND.get()) {
                    setSaddledTextures(saddleTextures,2,2, 2 );
                } else if (saddle == ModItems.SADDLE_ENGLISH_LEATHER_WOOD.get()) {
                    setSaddledTextures(saddleTextures,2,2, 3 );
                } else if (saddle == ModItems.SADDLE_ENGLISH_LEATHERCLOTHSEAT.get()) {
                    setSaddledTextures(saddleTextures,2,5, 0 );
                } else if (saddle == ModItems.SADDLE_ENGLISH_LEATHERCLOTHSEAT_GOLD.get()) {
                    setSaddledTextures(saddleTextures,2,5, 1 );
                } else if (saddle == ModItems.SADDLE_ENGLISH_LEATHERCLOTHSEAT_DIAMOND.get()) {
                    setSaddledTextures(saddleTextures,2,5, 2 );
                } else if (saddle == ModItems.SADDLE_ENGLISH_LEATHERCLOTHSEAT_WOOD.get()) {
                    setSaddledTextures(saddleTextures,2,5, 3 );
                }

            }
        }

        return saddleTextures;
    }

    private void setSaddledTextures(List<String> saddleTextures, int saddle, int material, int hardware) {
        if (saddle != -1) {
            saddleTextures.add(TEXTURES_SADDLE[saddle]);
        }
        if (material != -1) {
            saddleTextures.add(TEXTURES_SADDLE_LEATHER[material]);
        }
        if (hardware != -1) {
            saddleTextures.add(TEXTURES_SADDLE_HARDWARE[hardware]);
        }
    }

}
