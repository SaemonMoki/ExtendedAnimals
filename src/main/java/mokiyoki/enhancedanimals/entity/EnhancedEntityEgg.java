package mokiyoki.enhancedanimals.entity;

import com.google.common.collect.Lists;
import mokiyoki.enhancedanimals.blocks.EnhancedChickenEggBlock;
import mokiyoki.enhancedanimals.capability.egg.EggCapabilityProvider;
import mokiyoki.enhancedanimals.capability.nestegg.EggHolder;
import mokiyoki.enhancedanimals.items.EnhancedEgg;
import mokiyoki.enhancedanimals.tileentity.ChickenNestTileEntity;
import mokiyoki.enhancedanimals.util.Genes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.FireworkRocketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkHooks;

import java.util.List;

import static mokiyoki.enhancedanimals.init.ModEntities.ENHANCED_CHICKEN;
import static mokiyoki.enhancedanimals.init.ModEntities.ENHANCED_ENTITY_EGG_ENTITY_TYPE;

public class EnhancedEntityEgg extends ThrowableItemProjectile {

    private static final EntityDataAccessor<String> GENES = SynchedEntityData.<String>defineId(EnhancedEntityEgg.class, EntityDataSerializers.STRING);
    private static final EntityDataAccessor<String> SIRE = SynchedEntityData.<String>defineId(EnhancedEntityEgg.class, EntityDataSerializers.STRING);
    private static final EntityDataAccessor<String> DAM = SynchedEntityData.<String>defineId(EnhancedEntityEgg.class, EntityDataSerializers.STRING);

    private boolean hasParents = false;

    public EnhancedEntityEgg(EntityType<? extends EnhancedEntityEgg> entityIn, Level worldIn) {
        super(entityIn, worldIn);
    }

    protected Item getDefaultItem() {
        return Items.EGG;
    }

    public EnhancedEntityEgg(Level worldIn, double x, double y, double z, Item egg) {
        super(ENHANCED_ENTITY_EGG_ENTITY_TYPE.get(), x, y, z,worldIn);
        this.setItem(new ItemStack(egg, 1));
    }

    public EnhancedEntityEgg(Level worldIn, Player playerIn, Genes eggGenes, String sireName, String damName, Item egg, boolean hasParents) {
        super(ENHANCED_ENTITY_EGG_ENTITY_TYPE.get(), playerIn, worldIn);
        this.setGenes(eggGenes);
        this.setParentNames(sireName, damName);
        this.setItem(new ItemStack(egg, 1));
        this.hasParents = hasParents;
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.getEntityData().define(GENES, new String());
        this.getEntityData().define(SIRE, new String());
        this.getEntityData().define(DAM, new String());
    }

    public void setGenes(Genes eggGenes) {
        if (eggGenes != null) {
            this.getEntityData().set(GENES, eggGenes.getGenesAsString());
        } else {
            this.getEntityData().set(GENES, "INFERTILE");
        }
    }

    public String getGenes() {
        return this.entityData.get(GENES);
    }

    public void setParentNames(String sireName, String damName) {
        if (sireName!=null && !sireName.equals("")) {
            this.getEntityData().set(SIRE, sireName);
        }
        if (damName!=null && !damName.equals("")) {
            this.getEntityData().set(DAM, damName);
        }
    }

    public String getSire() {
        String sireName = this.entityData.get(SIRE);
        if (sireName!=null && !sireName.equals("")) {
            return sireName;
        } else {
            return "???";
        }
    }

    public String getDam() {
        String damName = this.entityData.get(DAM);
        if (damName!=null && !damName.equals("")) {
            return damName;
        } else {
            return "???";
        }
    }

    public void setEggData(EggHolder eggHolder) {
        this.setGenes(eggHolder.getGenes());
        this.setParentNames(eggHolder.getSire(), eggHolder.getDam());
        this.hasParents = eggHolder.hasParents();
    }

    @OnlyIn(Dist.CLIENT)
    public void handleEntityEvent(byte id) {
        if (id == 3) {
            double d0 = 0.08D;

            for (int i = 0; i < 8; ++i) {
                this.level.addParticle(new ItemParticleOption(ParticleTypes.ITEM, this.getItem()), this.getX(), this.getY(), this.getZ(), ((double)this.random.nextFloat() - 0.5D) * 0.08D, ((double)this.random.nextFloat() - 0.5D) * 0.08D, ((double)this.random.nextFloat() - 0.5D) * 0.08D);
            }
        }
    }

    protected void onHitEntity(EntityHitResult p_213868_1_) {
        super.onHitEntity(p_213868_1_);
        p_213868_1_.getEntity().hurt(DamageSource.thrown(this, this.getOwner()), 0.0F);
    }

    /**
     * Called when this EntityThrowable hits a block or entity.
     */
    protected void onHit(HitResult result) {
        super.onHit(result);

        if (this.level instanceof ServerLevel && this.level.getBlockEntity(this.blockPosition()) instanceof ChickenNestTileEntity nest) {
            ItemStack egg = this.getItem();
            ((EnhancedEgg) egg.getItem()).setHasParents(egg, hasParents);
            if (!getGenes().equals("INFERTILE")) {
                egg.getCapability(EggCapabilityProvider.EGG_CAP, null).orElse(new EggCapabilityProvider()).setEggData(new Genes(getGenes()), getSire(), getDam());
                CompoundTag nbtTagCompound = egg.serializeNBT();
                egg.deserializeNBT(nbtTagCompound);
            }
            nest.addEggToNest(egg);
            this.level.broadcastEntityEvent(this, (byte) 3);
            this.remove(RemovalReason.DISCARDED);
            return;
        }

        boolean isCreeper = false;
        if (!getGenes().equals("INFERTILE") && !getGenes().isEmpty()) {
            Genes genetics = new Genes(getGenes());
            if (genetics.isHomozygousFor(70, 2)) {
                isCreeper = true;
                if (this.level.isClientSide) {
                    this.level.createFireworks(this.getX(), this.getY(), this.getZ(), 1.0D, 1.0D, 1.0D, this.makeCreeperFirework());
                }
            }
        } else if (this.level instanceof ServerLevel && !this.hasParents) {
            EnhancedChicken enhancedchicken = ENHANCED_CHICKEN.get().create(this.level);
            Genes chickenGenes = enhancedchicken.createInitialBreedGenes((ServerLevel) this.level, this.blockPosition(), "WanderingTrader");
            enhancedchicken.setGenes(chickenGenes);
            enhancedchicken.setSharedGenesFromEntityEgg(chickenGenes.getGenesAsString());
            enhancedchicken.setGrowingAge();
            enhancedchicken.initilizeAnimalSize();
            enhancedchicken.setBirthTime();
            enhancedchicken.moveTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), 0.0F);
            enhancedchicken.setSireName(getSire());
            enhancedchicken.setDamName(getDam());
            this.level.addFreshEntity(enhancedchicken);
        }

        if (this.level instanceof ServerLevel) {
            if (!getGenes().equals("INFERTILE") && !getGenes().isEmpty()) {
                if (!isCreeper) {
                    if (this.random.nextFloat() < 0.125F) {
                        EnhancedChicken enhancedchicken = ENHANCED_CHICKEN.get().create(this.level);
                        enhancedchicken.setGenes(new Genes(getGenes()));
                        enhancedchicken.setSharedGenesFromEntityEgg(getGenes());
                        enhancedchicken.setGrowingAge();
                        enhancedchicken.initilizeAnimalSize();
                        enhancedchicken.setBirthTime();
                        enhancedchicken.moveTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), 0.0F);
                        enhancedchicken.setSireName(getSire());
                        enhancedchicken.setDamName(getDam());
                        this.level.addFreshEntity(enhancedchicken);
                    }
                }
            }
            this.level.broadcastEntityEvent(this, (byte) 3);
            this.remove(RemovalReason.DISCARDED);
        }
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        String genes = compound.getString("genes");
        this.getEntityData().set(GENES, genes);
        this.getEntityData().set(SIRE, compound.getString("SireName"));
        this.getEntityData().set(DAM, compound.getString("DamName"));
        this.hasParents = compound.getBoolean("hasParents");
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        String genes = this.getGenes();
        if (!genes.isEmpty()) {
            compound.putString("genes", genes);
        }
        compound.putString("SireName", this.getSire());
        compound.putString("DamName", this.getDam());
        compound.putBoolean("hasParents", this.hasParents);
    }

    private CompoundTag makeCreeperFirework() {
        ItemStack itemstack = new ItemStack(Items.FIREWORK_ROCKET, 1);
        ItemStack itemstack1 = new ItemStack(Items.FIREWORK_STAR);
        CompoundTag compoundnbt = itemstack1.getOrCreateTagElement("Explosion");

        List<Integer> list = Lists.newArrayList();
        list.add(DyeColor.LIME.getFireworkColor());
        list.add(DyeColor.LIGHT_GRAY.getFireworkColor());
        list.add(DyeColor.GREEN.getFireworkColor());
        list.add(DyeColor.WHITE.getFireworkColor());
        list.add(DyeColor.GREEN.getFireworkColor());
        list.add(DyeColor.LIME.getFireworkColor());
        compoundnbt.putIntArray("Colors", list);

        List<Integer> fadeList = Lists.newArrayList();
        fadeList.add(DyeColor.GREEN.getFireworkColor());
        fadeList.add(DyeColor.GRAY.getFireworkColor());
        fadeList.add(DyeColor.GREEN.getFireworkColor());
        fadeList.add(DyeColor.GRAY.getFireworkColor());
        compoundnbt.putIntArray("FadeColors", fadeList);

        compoundnbt.putBoolean("Flicker", true);

        compoundnbt.putByte("Type", (byte) FireworkRocketItem.Shape.CREEPER.getId());
        CompoundTag compoundnbt1 = itemstack.getOrCreateTagElement("Fireworks");
        ListTag listnbt = new ListTag();
        CompoundTag compoundnbt2 = itemstack1.getTagElement("Explosion");
        if (compoundnbt2 != null) {
            listnbt.add(compoundnbt2);
        }

        compoundnbt1.putByte("Flight", (byte)-1);
        if (!listnbt.isEmpty()) {
            compoundnbt1.put("Explosions", listnbt);
        }

        return itemstack.getTagElement("Fireworks");
    }

    @Override
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

}
