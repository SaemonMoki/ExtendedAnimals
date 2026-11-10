package mokiyoki.enhancedanimals.neoforge.platform;

import mokiyoki.enhancedanimals.platform.network.INetworkHelper;
import mokiyoki.enhancedanimals.platform.network.PacketContext;
import mokiyoki.enhancedanimals.platform.network.PacketRegistrar;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

public class NeoForgeNetworkHelper implements INetworkHelper {

    @Override
    public void sendToServer(CustomPacketPayload payload) {
        PacketDistributor.sendToServer(payload);
    }

    @Override
    public void sendToPlayer(ServerPlayer player, CustomPacketPayload payload) {
        PacketDistributor.sendToPlayer(player, payload);
    }

    @Override
    public void sendToPlayersTrackingEntity(Entity entity, CustomPacketPayload payload) {
        PacketDistributor.sendToPlayersTrackingEntity(entity, payload);
    }

    public record Registrar(PayloadRegistrar registrar) implements PacketRegistrar {

        @Override
        public <T extends CustomPacketPayload> void playToClient(CustomPacketPayload.Type<T> type, StreamCodec<? super RegistryFriendlyByteBuf, T> codec, PacketHandler<T> handler) {
            this.registrar.playToClient(type, codec, (payload, context) -> handler.handle(payload, wrap(context)));
        }

        @Override
        public <T extends CustomPacketPayload> void playToServer(CustomPacketPayload.Type<T> type, StreamCodec<? super RegistryFriendlyByteBuf, T> codec, PacketHandler<T> handler) {
            this.registrar.playToServer(type, codec, (payload, context) -> handler.handle(payload, wrap(context)));
        }

        private static PacketContext wrap(IPayloadContext context) {
            return new PacketContext() {
                @Override
                public Player player() {
                    return context.player();
                }

                @Override
                public void enqueueWork(Runnable task) {
                    context.enqueueWork(task);
                }
            };
        }
    }
}
