package mokiyoki.enhancedanimals.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class CarrySyncPacket {
    private int carrierId;
    private ItemStack carried = ItemStack.EMPTY;

    public CarrySyncPacket() {
    }

    public CarrySyncPacket(int carrierId, ItemStack carried) {
        this.carrierId = carrierId;
        this.carried = carried;
    }

    public CarrySyncPacket(FriendlyByteBuf buf) {
        this.carrierId = buf.readVarInt();
        this.carried = buf.readItem();
    }

    public void writePacketData(FriendlyByteBuf buf) {
        buf.writeVarInt(this.carrierId);
        buf.writeItem(this.carried);
    }

    public boolean processPacket(Supplier<NetworkEvent.Context> contextSupplier) {
        EAPacketHandler.handleCarrySync(this);
        return true;
    }

    @OnlyIn(Dist.CLIENT)
    public int getCarrierId() {
        return this.carrierId;
    }

    @OnlyIn(Dist.CLIENT)
    public ItemStack getCarried() {
        return this.carried;
    }
}
