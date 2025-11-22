package mokiyoki.enhancedanimals.network;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;

public class EAPPAir extends EAParticlePacket {

    public EAPPAir(int[] blockPosList) {
        super(blockPosList);
    }

    public EAPPAir(FriendlyByteBuf buf) {
        super(buf);
    }

    @Override
    ParticleOptions getParticle() {
        return ParticleTypes.DRAGON_BREATH;
    }
}
