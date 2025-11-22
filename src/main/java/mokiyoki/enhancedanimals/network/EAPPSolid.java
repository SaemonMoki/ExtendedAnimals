package mokiyoki.enhancedanimals.network;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;

public class EAPPSolid extends EAParticlePacket {

    public EAPPSolid(int[] blockPosList) {
        super(blockPosList);
    }

    public EAPPSolid(FriendlyByteBuf buf) {
        super(buf);
    }

    @Override
    ParticleOptions getParticle() {
        return ParticleTypes.ANGRY_VILLAGER;
    }
}
