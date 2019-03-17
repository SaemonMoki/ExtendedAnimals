package mokiyoki.enhancedanimals.entity;

import mokiyoki.enhancedanimals.init.ModItems;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Particles;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ItemParticleData;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import static mokiyoki.enhancedanimals.util.handlers.RegistryHandler.ENHANCED_ENTITY_EGG_ENTITY_TYPE;

public class EnhancedEntityEgg extends EntityThrowable {

    private static final DataParameter<String> GENES = EntityDataManager.<String>createKey(EnhancedEntityEgg.class, DataSerializers.STRING);

    public EnhancedEntityEgg(World worldIn) {
        super(ENHANCED_ENTITY_EGG_ENTITY_TYPE, worldIn);
    }

    public EnhancedEntityEgg(World worldIn, EntityLivingBase throwerIn) {
        super(ENHANCED_ENTITY_EGG_ENTITY_TYPE, throwerIn, worldIn);
    }

    public EnhancedEntityEgg(World worldIn, double x, double y, double z) {
        super(ENHANCED_ENTITY_EGG_ENTITY_TYPE, x, y, z,worldIn);
    }

    public EnhancedEntityEgg(World worldIn, EntityPlayer playerIn, int[] eggGenes) {
        super(ENHANCED_ENTITY_EGG_ENTITY_TYPE, playerIn, worldIn);
        this.setGenes(eggGenes);
    }

    protected void registerData() {
        this.getDataManager().register(GENES, new String());
    }

    public void setGenes(int[] eggGenes) {
        if (eggGenes != null) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < eggGenes.length; i++){
                sb.append(eggGenes[i]);
                if (i != eggGenes.length -1){
                    sb.append(",");
                }
            }
            this.getDataManager().set(GENES, sb.toString());
        } else {
            this.getDataManager().set(GENES, "INFERTILE");
        }

    }

    public String getGenes() {
        String genes = ((String)this.dataManager.get(GENES)).toString();
        return genes;
    }

    /**
     * Handler for {@link World#setEntityState}
     */
    @OnlyIn(Dist.CLIENT)
    public void handleStatusUpdate(byte id) {
        if (id == 3) {
            double d0 = 0.08D;

            for (int i = 0; i < 8; ++i) {
                this.world.spawnParticle(new ItemParticleData(Particles.ITEM, new ItemStack(ModItems.Egg_White)), this.posX, this.posY, this.posZ, ((double)this.rand.nextFloat() - 0.5D) * 0.08D, ((double)this.rand.nextFloat() - 0.5D) * 0.08D, ((double)this.rand.nextFloat() - 0.5D) * 0.08D);
            }
        }
    }

    /**
     * Called when this EntityThrowable hits a block or entity.
     */
    protected void onImpact(RayTraceResult result) {
        if (result.entity != null) {
            result.entity.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), 0.0F);
        }

        if (!this.world.isRemote) {
            if (!getGenes().equals("INFERTILE")) {
                //            if (this.rand.nextInt(8) == 0) {
//                int i = 1;
//                if (this.rand.nextInt(32) == 0) {
//                    i = 4;
//                }

//                for (int j = 0; j < i; ++j) {
                EnhancedChicken enhancedchicken = new EnhancedChicken(this.world);
                enhancedchicken.setSharedGenesFromEntityEgg(getGenes());
                enhancedchicken.setGenes(enhancedchicken.getSharedGenes());
                enhancedchicken.setGrowingAge(-24000);
                enhancedchicken.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0F);
                this.world.spawnEntity(enhancedchicken);
//                }
//            }
            }
            this.world.setEntityState(this, (byte)3);
            this.remove();
        }
    }

    @Override
    public void readAdditional(NBTTagCompound compound) {
        super.readAdditional(compound);
        String genes = compound.getString("genes");
        this.getDataManager().set(GENES, genes);

    }

    @Override
    public void writeAdditional(NBTTagCompound compound) {
        super.writeAdditional(compound);
        String genes = this.getGenes();
        if (!genes.isEmpty()) {
            compound.setString("genes", genes);
        }

    }


}