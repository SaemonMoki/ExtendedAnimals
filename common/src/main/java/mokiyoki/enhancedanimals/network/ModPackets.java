package mokiyoki.enhancedanimals.network;

import mokiyoki.enhancedanimals.network.axolotl.AxolotlBucketTexturePacket;
import mokiyoki.enhancedanimals.platform.network.PacketRegistrar;

public final class ModPackets {

    public static final String PROTOCOL_VERSION = "1.0";

    private ModPackets() {
    }

    public static void register(PacketRegistrar registrar) {
        registrar.playToClient(EAEquipmentPacket.TYPE, EAEquipmentPacket.STREAM_CODEC, EAEquipmentPacket::processPacket);
        registrar.playToServer(AxolotlBucketTexturePacket.TYPE, AxolotlBucketTexturePacket.STREAM_CODEC, AxolotlBucketTexturePacket::processPacket);
        registrar.playToServer(CarryTogglePacket.TYPE, CarryTogglePacket.STREAM_CODEC, CarryTogglePacket::processPacket);
        registrar.playToClient(CarrySyncPacket.TYPE, CarrySyncPacket.STREAM_CODEC, CarrySyncPacket::processPacket);
    }
}
