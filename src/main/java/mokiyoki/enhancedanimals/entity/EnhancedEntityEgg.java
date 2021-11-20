package mokiyoki.enhancedanimals.entity;

import com.google.common.collect.Lists;
import mokiyoki.enhancedanimals.util.Genes;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.DyeColor;
import net.minecraft.item.FireworkRocketItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ItemParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkHooks;

import java.util.List;

import static mokiyoki.enhancedanimals.util.handlers.EventRegistry.ENHANCED_CHICKEN;
import static mokiyoki.enhancedanimals.util.handlers.EventRegistry.ENHANCED_ENTITY_EGG_ENTITY_TYPE;

public class EnhancedEntityEgg extends ProjectileItemEntity {

    private static final DataParameter<String> GENES = EntityDataManager.<String>createKey(EnhancedEntityEgg.class, DataSerializers.STRING);
    private static final DataParameter<String> SIRE = EntityDataManager.<String>createKey(EnhancedEntityEgg.class, DataSerializers.STRING);
    private static final DataParameter<String> DAM = EntityDataManager.<String>createKey(EnhancedEntityEgg.class, DataSerializers.STRING);

    public EnhancedEntityEgg(EntityType<? extends EnhancedEntityEgg> entityIn, World worldIn) {
        super(entityIn, worldIn);
    }

    protected Item getDefaultItem() {
        return Items.EGG;
    }

    public EnhancedEntityEgg(World worldIn, double x, double y, double z, Item egg) {
        super(ENHANCED_ENTITY_EGG_ENTITY_TYPE, x, y, z,worldIn);
        this.setItem(new ItemStack(egg, 1));
    }

    public EnhancedEntityEgg(World worldIn, PlayerEntity playerIn, Genes eggGenes, String sireName, String damName, Item egg) {
        super(ENHANCED_ENTITY_EGG_ENTITY_TYPE, playerIn, worldIn);
        this.setGenes(eggGenes);
        this.setParentNames(sireName, damName);
        this.setItem(new ItemStack(egg, 1));
    }

    protected void registerData() {
        super.registerData();
        this.getDataManager().register(GENES, new String());
        this.getDataManager().register(SIRE, new String());
        this.getDataManager().register(DAM, new String());
    }

    public void setGenes(Genes eggGenes) {
        if (eggGenes != null) {
            this.getDataManager().set(GENES, eggGenes.getGenesAsString());
        } else {
            this.getDataManager().set(GENES, "INFERTILE");
        }
    }

    public String getGenes() {
        return this.dataManager.get(GENES);
    }

    public void setParentNames(String sireName, String damName) {
        if (sireName!=null && !sireName.equals("")) {
            this.getDataManager().set(SIRE, sireName);
        }
        if (damName!=null && !damName.equals("")) {
            this.getDataManager().set(DAM, damName);
        }
    }

    public String getSire() {
        String sireName = this.dataManager.get(SIRE);
        if (sireName!=null && !sireName.equals("")) {
            return sireName;
        } else {
            return "???";
        }
    }

    public String getDam() {
        String damName = this.dataManager.get(DAM);
        if (damName!=null && !damName.equals("")) {
            return damName;
        } else {
            return "???";
        }
    }

    /**
     * Handler for {@link World#setEntityState}
     */
    @OnlyIn(Dist.CLIENT)
    public void handleStatusUpdate(byte id) {
        if (id == 3) {
            double d0 = 0.08D;

            for (int i = 0; i < 8; ++i) {
                this.world.addParticle(new ItemParticleData(ParticleTypes.ITEM, this.getItem()), this.getPosX(), this.getPosY(), this.getPosZ(), ((double)this.rand.nextFloat() - 0.5D) * 0.08D, ((double)this.rand.nextFloat() - 0.5D) * 0.08D, ((double)this.rand.nextFloat() - 0.5D) * 0.08D);
            }
        }
    }

    protected void onEntityHit(EntityRayTraceResult p_213868_1_) {
        super.onEntityHit(p_213868_1_);
        p_213868_1_.getEntity().attackEntityFrom(DamageSource.causeThrownDamage(this, this.func_234616_v_()), 0.0F);
    }

    /**
     * Called when this EntityThrowable hits a block or entity.
     */
    protected void onImpact(RayTraceResult result) {
        super.onImpact(result);

        if(this.world.isRemote) {
            int i =0;
        }

        boolean isCreeper = false;
        if (!getGenes().equals("INFERTILE")) {
            Genes genetics = new Genes(getGenes());
            if (genetics.testGenes(70, 1, 2)) {
                isCreeper = true;
                if (this.world.isRemote) {
                    this.world.makeFireworks(this.getPosX(), this.getPosY(), this.getPosZ(), 1.0D, 1.0D, 1.0D, this.makeCreeperFirework());
                }
            }
        }

        if (!this.world.isRemote) {
            if (!getGenes().equals("INFERTILE")) {
                if (!isCreeper) {
                    EnhancedChicken enhancedchicken = ENHANCED_CHICKEN.create(this.world);
                    enhancedchicken.setGenes(new Genes(getGenes()));
                    enhancedchicken.setSharedGenesFromEntityEgg(getGenes());
                    enhancedchicken.setGrowingAge();
                    enhancedchicken.initilizeAnimalSize();
                    enhancedchicken.setBirthTime(String.valueOf(this.world.getGameTime()));
                    enhancedchicken.setLocationAndAngles(this.getPosX(), this.getPosY(), this.getPosZ(), this.rotationYaw, 0.0F);
                    enhancedchicken.setSireName(getSire());
                    enhancedchicken.setDamName(getDam());
                    this.world.addEntity(enhancedchicken);
                }
            }
            this.world.setEntityState(this, (byte)3);
            this.remove();
        }
    }

    @Override
    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
        String genes = compound.getString("genes");
        this.getDataManager().set(GENES, genes);
        this.getDataManager().set(SIRE, compound.getString("SireName"));
        this.getDataManager().set(DAM, compound.getString("DamName"));

    }

    @Override
    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        String genes = this.getGenes();
        if (!genes.isEmpty()) {
            compound.putString("genes", genes);
        }
        compound.putString("SireName", this.getSire());
        compound.putString("DamName", this.getDam());


    }

    private CompoundNBT makeCreeperFirework() {
        ItemStack itemstack = new ItemStack(Items.FIREWORK_ROCKET, 1);
        ItemStack itemstack1 = new ItemStack(Items.FIREWORK_STAR);
        CompoundNBT compoundnbt = itemstack1.getOrCreateChildTag("Explosion");

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

        compoundnbt.putByte("Type", (byte) FireworkRocketItem.Shape.CREEPER.getIndex());
        CompoundNBT compoundnbt1 = itemstack.getOrCreateChildTag("Fireworks");
        ListNBT listnbt = new ListNBT();
        CompoundNBT compoundnbt2 = itemstack1.getChildTag("Explosion");
        if (compoundnbt2 != null) {
            listnbt.add(compoundnbt2);
        }

        compoundnbt1.putByte("Flight", (byte)-1);
        if (!listnbt.isEmpty()) {
            compoundnbt1.put("Explosions", listnbt);
        }

        return itemstack.getChildTag("Fireworks");
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

}