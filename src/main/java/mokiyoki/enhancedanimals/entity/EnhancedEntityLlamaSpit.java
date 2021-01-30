package mokiyoki.enhancedanimals.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.LlamaSpitEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import static mokiyoki.enhancedanimals.util.handlers.EventRegistry.ENHANCED_LLAMA_SPIT;

public class EnhancedEntityLlamaSpit extends LlamaSpitEntity {

    public EnhancedEntityLlamaSpit(EntityType<? extends EnhancedEntityLlamaSpit> llama, World world) {
        super(llama, world);
    }

    public EnhancedEntityLlamaSpit(World worldIn, EnhancedLlama llama) {
        this(ENHANCED_LLAMA_SPIT, worldIn);
        super.setShooter(llama);
        this.setPosition(llama.getPosX() - (double)(llama.getWidth() + 1.0F) * 0.5D * (double)MathHelper.sin(llama.renderYawOffset * ((float)Math.PI / 180F)), llama.getPosY() + (double)llama.getEyeHeight() - (double)0.1F, llama.getPosZ() + (double)(llama.getWidth() + 1.0F) * 0.5D * (double)MathHelper.cos(llama.renderYawOffset * ((float)Math.PI / 180F)));
    }

    @OnlyIn(Dist.CLIENT)
    public EnhancedEntityLlamaSpit(World worldIn, double x, double y, double z, double p_i47274_8_, double p_i47274_10_, double p_i47274_12_) {
        this(ENHANCED_LLAMA_SPIT, worldIn);
        this.setPosition(x, y, z);

        for(int i = 0; i < 7; ++i) {
            double d0 = 0.4D + 0.1D * (double)i;
            worldIn.addParticle(ParticleTypes.SPIT, x, y, z, p_i47274_8_ * d0, p_i47274_10_, p_i47274_12_ * d0);
        }

        this.setMotion(p_i47274_8_, p_i47274_10_, p_i47274_12_);
    }

}
