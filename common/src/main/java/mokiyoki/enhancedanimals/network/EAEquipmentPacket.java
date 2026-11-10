package mokiyoki.enhancedanimals.network;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import mokiyoki.enhancedanimals.platform.network.PacketContext;

import static mokiyoki.enhancedanimals.util.Reference.MODID;

public class EAEquipmentPacket implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<EAEquipmentPacket> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(MODID, "equipment"));
    public static final StreamCodec<RegistryFriendlyByteBuf, EAEquipmentPacket> STREAM_CODEC = StreamCodec.ofMember(EAEquipmentPacket::writePacketData, EAEquipmentPacket::new);

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

    public EAEquipmentPacket(RegistryFriendlyByteBuf buf) {
        this.entityID = buf.readVarInt();
        this.equipmentSlot = buf.readVarInt();
        this.itemStack = ItemStack.OPTIONAL_STREAM_CODEC.decode(buf);
    }

    public void readPacketData(RegistryFriendlyByteBuf buf){
        this.entityID = buf.readVarInt();
        this.equipmentSlot = buf.readVarInt();
        this.itemStack = ItemStack.OPTIONAL_STREAM_CODEC.decode(buf);
    }

    public void writePacketData(RegistryFriendlyByteBuf buf){
        buf.writeVarInt(this.entityID);
        buf.writeVarInt(this.equipmentSlot);
        ItemStack.OPTIONAL_STREAM_CODEC.encode(buf, this.itemStack);
    }

    public void processPacket(PacketContext context) {
        EAPacketHandler.handleAnimalInventorySync(this);
    }

    @Override
    public CustomPacketPayload.Type<EAEquipmentPacket> type() {
        return TYPE;
    }

    public ItemStack getItemStack() {
        return this.itemStack;
    }

    public int getEntityID() {
        return this.entityID;
    }

    public int getEquipmentSlot() {
        return this.equipmentSlot;
    }
}
