package mokiyoki.enhancedanimals.fabric.platform;

import mokiyoki.enhancedanimals.platform.network.INetworkHelper;
import mokiyoki.enhancedanimals.platform.network.PacketContext;
import mokiyoki.enhancedanimals.platform.network.PacketRegistrar;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

public class FabricNetworkHelper implements INetworkHelper {

    @Override
    public void sendToServer(CustomPacketPayload payload) {
        ClientPlayNetworking.send(payload);
    }

    @Override
    public void sendToPlayer(ServerPlayer player, CustomPacketPayload payload) {
        ServerPlayNetworking.send(player, payload);
    }

    @Override
    public void sendToPlayersTrackingEntity(Entity entity, CustomPacketPayload payload) {
        for (ServerPlayer player : PlayerLookup.tracking(entity)) {
            ServerPlayNetworking.send(player, payload);
        }
    }

    public static PacketContext context(Player player, Executor executor) {
        return new PacketContext() {
            @Override
            public Player player() {
                return player;
            }

            @Override
            public void enqueueWork(Runnable task) {
                executor.execute(task);
            }
        };
    }

    public static final class Registrar implements PacketRegistrar {
        private final List<ClientReceiver<?>> clientReceivers = new ArrayList<>();

        @Override
        public <T extends CustomPacketPayload> void playToClient(CustomPacketPayload.Type<T> type, StreamCodec<? super RegistryFriendlyByteBuf, T> codec, PacketHandler<T> handler) {
            PayloadTypeRegistry.playS2C().register(type, codec);
            this.clientReceivers.add(new ClientReceiver<>(type, handler));
        }

        @Override
        public <T extends CustomPacketPayload> void playToServer(CustomPacketPayload.Type<T> type, StreamCodec<? super RegistryFriendlyByteBuf, T> codec, PacketHandler<T> handler) {
            PayloadTypeRegistry.playC2S().register(type, codec);
            ServerPlayNetworking.registerGlobalReceiver(type,
                    (payload, context) -> handler.handle(payload, context(context.player(), context.server())));
        }

        public List<ClientReceiver<?>> clientReceivers() {
            return List.copyOf(this.clientReceivers);
        }
    }

    public record ClientReceiver<T extends CustomPacketPayload>(CustomPacketPayload.Type<T> type, PacketRegistrar.PacketHandler<T> handler) {
    }
}
