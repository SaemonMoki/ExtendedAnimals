package mokiyoki.enhancedanimals.network;

import mokiyoki.enhancedanimals.platform.network.PacketContext;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

import static mokiyoki.enhancedanimals.util.Reference.MODID;

public class CarryTogglePacket implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<CarryTogglePacket> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(MODID, "carry_toggle"));
    public static final StreamCodec<FriendlyByteBuf, CarryTogglePacket> STREAM_CODEC = StreamCodec.ofMember(CarryTogglePacket::writePacketData, CarryTogglePacket::new);

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

    public static void processPacket(CarryTogglePacket packet, PacketContext context) {
        context.enqueueWork(() -> {
            if (context.player() instanceof ServerPlayer serverPlayer) {
                EAPacketHandler.handleCarryToggle(serverPlayer, packet.targetEntityId);
            }
        });
    }

    @Override
    public CustomPacketPayload.Type<CarryTogglePacket> type() {
        return TYPE;
    }
}
