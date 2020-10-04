package mokiyoki.enhancedanimals.entity;

import mokiyoki.enhancedanimals.init.ModItems;
import mokiyoki.enhancedanimals.util.Genes;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
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

import static mokiyoki.enhancedanimals.util.handlers.EventRegistry.ENHANCED_CHICKEN;
import static mokiyoki.enhancedanimals.util.handlers.EventRegistry.ENHANCED_ENTITY_EGG_ENTITY_TYPE;

public class EnhancedEntityEgg extends ProjectileItemEntity {

    private static final DataParameter<String> GENES = EntityDataManager.<String>createKey(EnhancedEntityEgg.class, DataSerializers.STRING);
    private static final DataParameter<String> SIRE = EntityDataManager.<String>createKey(EnhancedEntityEgg.class, DataSerializers.STRING);
    private static final DataParameter<String> DAM = EntityDataManager.<String>createKey(EnhancedEntityEgg.class, DataSerializers.STRING);

    public EnhancedEntityEgg(EntityType<? extends EnhancedEntityEgg> entityIn, World worldIn) {
        super(entityIn, worldIn);
    }


    //TODO return the right default egg colour type... personal note: FUUUUUUUCCCCCKKKKKK!!!!!
    protected Item getDefaultItem() {
        return Items.EGG;
    }

    public EnhancedEntityEgg(World worldIn, double x, double y, double z) {
        super(ENHANCED_ENTITY_EGG_ENTITY_TYPE, x, y, z,worldIn);
    }

    public EnhancedEntityEgg(World worldIn, PlayerEntity playerIn, Genes eggGenes, String sireName, String damName) {
        super(ENHANCED_ENTITY_EGG_ENTITY_TYPE, playerIn, worldIn);
        this.setGenes(eggGenes);
        this.setParentNames(sireName, damName);
    }

    protected void registerData() {
        this.getDataManager().register(GENES, new String());
        this.getDataManager().register(SIRE, new String());
        this.getDataManager().register(DAM, new String());
    }

    public void setGenes(Genes eggGenes) {
//        if (eggGenes != null) {
//            StringBuilder sb = new StringBuilder();
//            for (int i = 0; i < eggGenes.length; i++){
//                sb.append(eggGenes[i]);
//                if (i != eggGenes.length -1){
//                    sb.append(",");
//                }
//            }
//            this.getDataManager().set(GENES, sb.toString());
//        } else {
//            this.getDataManager().set(GENES, "INFERTILE");
//        }
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

    /**
     * Called when this EntityThrowable hits a block or entity.
     */
    protected void onImpact(RayTraceResult result) {
        if (result.getType() == RayTraceResult.Type.ENTITY) {
            ((EntityRayTraceResult)result).getEntity().attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), 0.0F);
        }

        if (!this.world.isRemote) {
            if (!getGenes().equals("INFERTILE")) {
                //            if (this.rand.nextInt(8) == 0) {
//                int i = 1;
//                if (this.rand.nextInt(32) == 0) {
//                    i = 4;
//                }
//                for (int j = 0; j < i; ++j) {
                EnhancedChicken enhancedchicken = ENHANCED_CHICKEN.create(this.world);
                enhancedchicken.setGenes(new Genes(getGenes()));
                enhancedchicken.setSharedGenesFromEntityEgg(getGenes());
                enhancedchicken.setGenes(enhancedchicken.getSharedGenes());
                enhancedchicken.setGrowingAge(-60000);
                enhancedchicken.setBirthTime(String.valueOf(this.world.getGameTime()));
                enhancedchicken.setLocationAndAngles(this.getPosX(), this.getPosY(), this.getPosZ(), this.rotationYaw, 0.0F);
                enhancedchicken.setSireName(getSire());
                enhancedchicken.setDamName(getDam());
                this.world.addEntity(enhancedchicken);
//                }
//            }
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

}