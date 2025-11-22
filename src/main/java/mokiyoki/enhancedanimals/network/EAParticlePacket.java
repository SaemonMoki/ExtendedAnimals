package mokiyoki.enhancedanimals.network;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public abstract class EAParticlePacket {
    private int[] pos;

    public EAParticlePacket() {}

    public EAParticlePacket(int[] blockPosList) {
        this.pos = blockPosList;
    }

    public EAParticlePacket(FriendlyByteBuf buf) {
        this.pos = buf.readVarIntArray();
    }

    public void readPacketData(FriendlyByteBuf buf){
        this.pos = buf.readVarIntArray();
    }

    public void writePacketData(FriendlyByteBuf buf) {
        buf.writeVarIntArray(this.pos);
    }

    public boolean processPacket(Supplier<NetworkEvent.Context> contextSupplier) {
        EAPacketHandler.handleParticleSync(this);
        return true;
    }

    @OnlyIn(Dist.CLIENT)
    public int[] getPos() {
        return pos;
    }

    @OnlyIn(Dist.CLIENT)
    abstract ParticleOptions getParticle();
}
