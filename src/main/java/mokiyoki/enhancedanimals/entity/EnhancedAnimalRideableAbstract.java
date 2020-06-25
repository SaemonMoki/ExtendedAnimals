package mokiyoki.enhancedanimals.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.IJumpingMount;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.RangedAttribute;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.AirItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.Effects;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.Map;

public abstract class EnhancedAnimalRideableAbstract extends EnhancedAnimalChestedAbstract implements IJumpingMount {

    private static final DataParameter<Byte> STATUS = EntityDataManager.createKey(EnhancedAnimalRideableAbstract.class, DataSerializers.BYTE);
    protected static final IAttribute JUMP_STRENGTH = (new RangedAttribute((IAttribute)null, "ea.jumpStrength", 0.7D, 0.0D, 2.0D)).setDescription("Jump Strength").setShouldWatch(true);


    protected boolean isAnimalJumping;
    protected float jumpPower;
    private float prevRearingAmount;
    private int jumpRearingCounter;
    protected int gallopTime;
    private boolean allowStandSliding;

    protected int temper;

    protected EnhancedAnimalRideableAbstract(EntityType<? extends EnhancedAnimalAbstract> type, World worldIn, int genesSize, Ingredient temptationItems, Ingredient breedItems, Map<Item, Integer> foodWeightMap, boolean bottleFeedable) {
        super(type, worldIn, genesSize, temptationItems, breedItems, foodWeightMap, bottleFeedable);
        this.stepHeight = 1.0F;
    }

    protected void registerData() {
        super.registerData();
        this.dataManager.register(STATUS, (byte)0);
    }

    protected void registerAttributes() {
        super.registerAttributes();
        this.getAttributes().registerAttribute(JUMP_STRENGTH);
    }

    protected boolean getRideableWatchableBoolean(int p_110233_1_) {
        return (this.dataManager.get(STATUS) & p_110233_1_) != 0;
    }

    protected void setRideableWatchableBoolean(int p_110208_1_, boolean p_110208_2_) {
        byte b0 = this.dataManager.get(STATUS);
        if (p_110208_2_) {
            this.dataManager.set(STATUS, (byte)(b0 | p_110208_1_));
        } else {
            this.dataManager.set(STATUS, (byte)(b0 & ~p_110208_1_));
        }

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
    public void setJumpPower(int jumpPowerIn) {
        if (this.hasSaddle()) {
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
        return this.hasSaddle();
    }

    public void handleStartJump(int p_184775_1_) {
        this.allowStandSliding = true;
        this.makeRear();
    }

    public void handleStopJump() {
    }

    private void makeRear() {
        if (this.canPassengerSteer() || this.isServerWorld()) {
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

    @Override
    public boolean canHaveSaddle() {
        return true;
    }

    //Byte 4 is Saddle
    public boolean hasSaddle() {
        return this.getRideableWatchableBoolean(4);
    }

    public void setSaddled(boolean saddled) {
        this.setRideableWatchableBoolean(4, saddled);
    }

    protected void updateInventorySlots() {
        if (!this.world.isRemote) {
            this.setSaddled(!this.animalInventory.getStackInSlot(1).isEmpty() && this.canHaveSaddle());
        }
    }

    public void makeMad() {
        SoundEvent soundevent = this.getAngrySound();
        if (soundevent != null) {
            this.playSound(soundevent, this.getSoundVolume(), this.getSoundPitch());
        }

    }

    @Nullable
    protected SoundEvent getAngrySound() {
        this.makeRear();
        return null;
    }

    public int increaseTemper(int p_110198_1_) {
        int i = MathHelper.clamp(this.getTemper() + p_110198_1_, 0, this.getMaxTemper());
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
    public boolean processInteract(PlayerEntity entityPlayer, Hand hand) {
        ItemStack itemStack = entityPlayer.getHeldItem(hand);
        Item item = itemStack.getItem();

        if (this.isBeingRidden()) {
            return super.processInteract(entityPlayer, hand);
        }

        if (!this.isBeingRidden() && item instanceof AirItem && !entityPlayer.isSecondaryUseActive()) {
            this.mountTo(entityPlayer);
            return true;
        }

        if (item == Items.SADDLE){
            return this.saddleAnimal(itemStack, entityPlayer, this);
        }

        return super.processInteract(entityPlayer, hand);
    }

    public boolean saddleAnimal(ItemStack itemStack, PlayerEntity playerIn, LivingEntity target) {
        EnhancedAnimalRideableAbstract enhancedAnimal = (EnhancedAnimalRideableAbstract) target;
        if (enhancedAnimal.isAlive() && !enhancedAnimal.hasSaddle() && !enhancedAnimal.isChild()) {
            this.animalInventory.setInventorySlotContents(1, itemStack);
            this.playSound(SoundEvents.ENTITY_HORSE_SADDLE, 0.5F, 1.0F);
            itemStack.shrink(1);
            return true;
        }

        return true;
    }

    public void onInventoryChanged(IInventory invBasic) {
        boolean flag = this.hasSaddle();
        this.updateInventorySlots();
        if (this.ticksExisted > 20 && !flag && this.hasSaddle()) {
            this.playSound(SoundEvents.ENTITY_HORSE_SADDLE, 0.5F, 1.0F);
        }

    }

    @Nullable
    public Entity getControllingPassenger() {
        return this.getPassengers().isEmpty() ? null : this.getPassengers().get(0);
    }

    public void updatePassenger(Entity passenger) {
        super.updatePassenger(passenger);
        if (passenger instanceof MobEntity) {
            MobEntity mobentity = (MobEntity)passenger;
            this.renderYawOffset = mobentity.renderYawOffset;
        }

        if (this.prevRearingAmount > 0.0F) {
            float f3 = MathHelper.sin(this.renderYawOffset * ((float)Math.PI / 180F));
            float f = MathHelper.cos(this.renderYawOffset * ((float)Math.PI / 180F));
            float f1 = 0.7F * this.prevRearingAmount;
            float f2 = 0.15F * this.prevRearingAmount;
            passenger.setPosition(this.getPosX() + (double)(f1 * f3), this.getPosY() + this.getMountedYOffset() + passenger.getYOffset() + (double)f2, this.getPosZ() - (double)(f1 * f));
            if (passenger instanceof LivingEntity) {
                ((LivingEntity)passenger).renderYawOffset = this.renderYawOffset;
            }
        }

    }

    protected void mountTo(PlayerEntity player) {
        this.setRearing(false);
        if (!this.world.isRemote) {
            player.rotationYaw = this.rotationYaw;
            player.rotationPitch = this.rotationPitch;
            player.startRiding(this);
        }

    }

    public boolean canBeSteered() {
        return this.getControllingPassenger() instanceof LivingEntity;
    }

    public void travel(Vec3d p_213352_1_) {
        if (this.isAlive()) {
            if (this.isBeingRidden() && this.canBeSteered() && this.hasSaddle()) {
                LivingEntity livingentity = (LivingEntity)this.getControllingPassenger();
                this.rotationYaw = livingentity.rotationYaw;
                this.prevRotationYaw = this.rotationYaw;
                this.rotationPitch = livingentity.rotationPitch * 0.5F;
                this.setRotation(this.rotationYaw, this.rotationPitch);
                this.renderYawOffset = this.rotationYaw;
                this.rotationYawHead = this.renderYawOffset;
                float f = livingentity.moveStrafing * 0.5F;
                float f1 = livingentity.moveForward;
                if (f1 <= 0.0F) {
                    f1 *= 0.25F;
                    this.gallopTime = 0;
                }

                if (this.onGround && this.jumpPower == 0.0F && this.isRearing() && !this.allowStandSliding) {
                    f = 0.0F;
                    f1 = 0.0F;
                }

                if (this.jumpPower > 0.0F && !this.isAnimalJumping() && this.onGround) {
                    double d0 = this.getJumpStrength() * (double)this.jumpPower * (double)this.getJumpFactor();
                    double d1;
                    if (this.isPotionActive(Effects.JUMP_BOOST)) {
                        d1 = d0 + (double)((float)(this.getActivePotionEffect(Effects.JUMP_BOOST).getAmplifier() + 1) * 0.1F);
                    } else {
                        d1 = d0;
                    }

                    Vec3d vec3d = this.getMotion();
                    this.setMotion(vec3d.x, d1, vec3d.z);
                    this.setAnimalJumping(true);
                    this.isAirBorne = true;
                    if (f1 > 0.0F) {
                        float f2 = MathHelper.sin(this.rotationYaw * ((float)Math.PI / 180F));
                        float f3 = MathHelper.cos(this.rotationYaw * ((float)Math.PI / 180F));
                        this.setMotion(this.getMotion().add((double)(-0.4F * f2 * this.jumpPower), 0.0D, (double)(0.4F * f3 * this.jumpPower)));
                        this.playJumpSound();
                    }

                    this.jumpPower = 0.0F;
                }

                this.jumpMovementFactor = this.getAIMoveSpeed() * 0.1F;
                if (this.canPassengerSteer()) {
                    this.setAIMoveSpeed((float)this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getValue());
                    super.travel(new Vec3d((double)f, p_213352_1_.y, (double)f1));
                } else if (livingentity instanceof PlayerEntity) {
                    this.setMotion(Vec3d.ZERO);
                }

                if (this.onGround) {
                    this.jumpPower = 0.0F;
                    this.setAnimalJumping(false);
                }

                this.prevLimbSwingAmount = this.limbSwingAmount;
                double d2 = this.getPosX() - this.prevPosX;
                double d3 = this.getPosZ() - this.prevPosZ;
                float f4 = MathHelper.sqrt(d2 * d2 + d3 * d3) * 4.0F;
                if (f4 > 1.0F) {
                    f4 = 1.0F;
                }

                this.limbSwingAmount += (f4 - this.limbSwingAmount) * 0.4F;
                this.limbSwing += this.limbSwingAmount;
            } else {
                this.jumpMovementFactor = 0.02F;
                super.travel(p_213352_1_);
            }
        }
    }

    protected void playJumpSound() {
        this.playSound(SoundEvents.ENTITY_HORSE_JUMP, 0.4F, 1.0F);
    }

    @OnlyIn(Dist.CLIENT)
    public void handleStatusUpdate(byte id) {
        if (id == 7) {
            this.spawnParticles(true);
        } else if (id == 6) {
            this.spawnParticles(false);
        } else {
            super.handleStatusUpdate(id);
        }
    }

    @OnlyIn(Dist.CLIENT)
    protected void spawnParticles(boolean p_110216_1_) {
        IParticleData iparticledata = p_110216_1_ ? ParticleTypes.HEART : ParticleTypes.SMOKE;

        for(int i = 0; i < 7; ++i) {
            double d0 = this.rand.nextGaussian() * 0.02D;
            double d1 = this.rand.nextGaussian() * 0.02D;
            double d2 = this.rand.nextGaussian() * 0.02D;
            this.world.addParticle(iparticledata, this.getPosXRandom(1.0D), this.getPosYRandom() + 0.5D, this.getPosZRandom(1.0D), d0, d1, d2);
        }

    }

}
