package mokiyoki.enhancedanimals.network;

import net.minecraft.client.network.play.IClientPlayNetHandler;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkEvent;

import java.io.IOException;
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

    public EAEquipmentPacket(PacketBuffer buf) {
        this.entityID = buf.readVarInt();
        this.equipmentSlot = buf.readVarInt();
        this.itemStack = buf.readItemStack();
    }

    public void readPacketData(PacketBuffer buf){
        this.entityID = buf.readVarInt();
        this.equipmentSlot = buf.readVarInt();
        this.itemStack = buf.readItemStack();
    }

    public void writePacketData(PacketBuffer buf){
        buf.writeVarInt(this.entityID);
        buf.writeVarInt(this.equipmentSlot);
        buf.writeItemStack(this.itemStack);
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