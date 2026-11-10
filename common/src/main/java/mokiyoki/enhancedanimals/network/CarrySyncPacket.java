package mokiyoki.enhancedanimals.network;

import mokiyoki.enhancedanimals.platform.network.PacketContext;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import static mokiyoki.enhancedanimals.util.Reference.MODID;

public class CarrySyncPacket implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<CarrySyncPacket> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(MODID, "carry_sync"));
    public static final StreamCodec<RegistryFriendlyByteBuf, CarrySyncPacket> STREAM_CODEC = StreamCodec.ofMember(CarrySyncPacket::writePacketData, CarrySyncPacket::new);

    private int carrierId;
    private ItemStack carried = ItemStack.EMPTY;

    public CarrySyncPacket(int carrierId, ItemStack carried) {
        this.carrierId = carrierId;
        this.carried = carried;
    }

    public CarrySyncPacket(RegistryFriendlyByteBuf buf) {
        this.carrierId = buf.readVarInt();
        this.carried = ItemStack.OPTIONAL_STREAM_CODEC.decode(buf);
    }

    public void writePacketData(RegistryFriendlyByteBuf buf) {
        buf.writeVarInt(this.carrierId);
        ItemStack.OPTIONAL_STREAM_CODEC.encode(buf, this.carried);
    }

    public static void processPacket(CarrySyncPacket packet, PacketContext context) {
        EAPacketHandler.handleCarrySync(packet);
    }

    public int getCarrierId() {
        return this.carrierId;
    }

    public ItemStack getCarried() {
        return this.carried;
    }

    @Override
    public CustomPacketPayload.Type<CarrySyncPacket> type() {
        return TYPE;
    }
}
