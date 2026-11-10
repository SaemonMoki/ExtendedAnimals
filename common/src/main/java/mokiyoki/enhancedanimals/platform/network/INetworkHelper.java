package mokiyoki.enhancedanimals.platform.network;

import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;

public interface INetworkHelper {

    void sendToServer(CustomPacketPayload payload);

    void sendToPlayer(ServerPlayer player, CustomPacketPayload payload);

    void sendToPlayersTrackingEntity(Entity entity, CustomPacketPayload payload);
}
