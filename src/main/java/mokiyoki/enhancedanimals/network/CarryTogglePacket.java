package mokiyoki.enhancedanimals.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class CarryTogglePacket {
    private final int targetEntityId;

    public CarryTogglePacket(int targetEntityId) {
        this.targetEntityId = targetEntityId;
    }

    public CarryTogglePacket(FriendlyByteBuf buf) {
        this.targetEntityId = buf.readVarInt();
    }

    public void writePacketData(FriendlyByteBuf buf) {
        buf.writeVarInt(this.targetEntityId);
    }

    public boolean processPacket(Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> EAPacketHandler.handleCarryToggle(context.getSender(), this.targetEntityId));
        return true;
    }
}
