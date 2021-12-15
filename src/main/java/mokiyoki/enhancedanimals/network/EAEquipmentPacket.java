package mokiyoki.enhancedanimals.network;

import net.minecraft.world.item.ItemStack;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class EAEquipmentPacket {
    private int entityID;
    private int equipmentSlot;
    private ItemStack itemStack = ItemStack.EMPTY;

    public EAEquipmentPacket() {
    }

    public EAEquipmentPacket(int entityIdIn, int equipSlot, ItemStack itemStackIn) {
        this.entityID = entityIdIn;
        this.equipmentSlot = equipSlot;
        this.itemStack = itemStackIn.copy();
    }

    public EAEquipmentPacket(FriendlyByteBuf buf) {
        this.entityID = buf.readVarInt();
        this.equipmentSlot = buf.readVarInt();
        this.itemStack = buf.readItem();
    }

    public void readPacketData(FriendlyByteBuf buf){
        this.entityID = buf.readVarInt();
        this.equipmentSlot = buf.readVarInt();
        this.itemStack = buf.readItem();
    }

    public void writePacketData(FriendlyByteBuf buf){
        buf.writeVarInt(this.entityID);
        buf.writeVarInt(this.equipmentSlot);
        buf.writeItem(this.itemStack);
    }

    public boolean processPacket(Supplier<NetworkEvent.Context> contextSupplier) {
        EAPacketHandler.handleAnimalInventorySync(this);
        return true;
    }

    @OnlyIn(Dist.CLIENT)
    public ItemStack getItemStack() {
        return this.itemStack;
    }

    @OnlyIn(Dist.CLIENT)
    public int getEntityID() {
        return this.entityID;
    }

    @OnlyIn(Dist.CLIENT)
    public int getEquipmentSlot() {
        return this.equipmentSlot;
    }
}