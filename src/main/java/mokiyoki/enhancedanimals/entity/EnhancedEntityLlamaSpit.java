package mokiyoki.enhancedanimals.entity;

import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.LlamaSpit;
import net.minecraft.network.protocol.Packet;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkHooks;

import static mokiyoki.enhancedanimals.init.ModEntities.ENHANCED_LLAMA_SPIT;

public class EnhancedEntityLlamaSpit extends LlamaSpit {

    public EnhancedEntityLlamaSpit(EntityType<? extends EnhancedEntityLlamaSpit> llama, Level world) {
        super(llama, world);
    }

    public EnhancedEntityLlamaSpit(Level worldIn, EnhancedLlama llama) {
        this(ENHANCED_LLAMA_SPIT.get(), worldIn);
        super.setOwner(llama);
        this.setPos(llama.getX() - (double)(llama.getBbWidth() + 1.0F) * 0.5D * (double)Mth.sin(llama.yBodyRot * ((float)Math.PI / 180F)), llama.getY() + (double)llama.getEyeHeight() - (double)0.1F, llama.getZ() + (double)(llama.getBbWidth() + 1.0F) * 0.5D * (double)Mth.cos(llama.yBodyRot * ((float)Math.PI / 180F)));
    }

    @OnlyIn(Dist.CLIENT)
    public EnhancedEntityLlamaSpit(Level worldIn, double x, double y, double z, double p_i47274_8_, double p_i47274_10_, double p_i47274_12_) {
        this(ENHANCED_LLAMA_SPIT.get(), worldIn);
        this.setPos(x, y, z);

        for(int i = 0; i < 7; ++i) {
            double d0 = 0.4D + 0.1D * (double)i;
            worldIn.addParticle(ParticleTypes.SPIT, x, y, z, p_i47274_8_ * d0, p_i47274_10_, p_i47274_12_ * d0);
        }

        this.setDeltaMovement(p_i47274_8_, p_i47274_10_, p_i47274_12_);
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
