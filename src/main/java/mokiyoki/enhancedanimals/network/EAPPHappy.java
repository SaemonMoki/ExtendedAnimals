package mokiyoki.enhancedanimals.network;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;

public class EAPPHappy extends EAParticlePacket {

    public EAPPHappy(int[] blockPosList) {
        super(blockPosList);
    }

    public EAPPHappy(FriendlyByteBuf buf) {
        super(buf);
    }

    @Override
    ParticleOptions getParticle() {
        return ParticleTypes.HEART;
    }
}
