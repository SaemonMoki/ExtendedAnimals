package mokiyoki.enhancedanimals.entity;

import mokiyoki.enhancedanimals.init.ModItems;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Particles;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ItemParticleData;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import static mokiyoki.enhancedanimals.util.handlers.RegistryHandler.ENHANCED_ENTITY_EGG_ENTITY_TYPE;

public class EnhancedEntityEgg extends EntityThrowable {
    public EnhancedEntityEgg(World worldIn) {
        super(ENHANCED_ENTITY_EGG_ENTITY_TYPE, worldIn);
    }

    public EnhancedEntityEgg(World worldIn, EntityLivingBase throwerIn) {
        super(ENHANCED_ENTITY_EGG_ENTITY_TYPE, throwerIn, worldIn);
    }

    public EnhancedEntityEgg(World worldIn, double x, double y, double z) {
        super(ENHANCED_ENTITY_EGG_ENTITY_TYPE, x, y, z,worldIn);
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
            if (this.rand.nextInt(8) == 0) {
                int i = 1;
                if (this.rand.nextInt(32) == 0) {
                    i = 4;
                }

                for (int j = 0; j < i; ++j) {
                    EnhancedChicken enhancedchicken = new EnhancedChicken(this.world);
//                    enhancedchicken.setSharedGenes(eggGenes);
                    enhancedchicken.setGrowingAge(-24000);
                    enhancedchicken.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0F);
                    this.world.spawnEntity(enhancedchicken);
                }
            }

            this.world.setEntityState(this, (byte)3);
            this.remove();
        }
    }
}