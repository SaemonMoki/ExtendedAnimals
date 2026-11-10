package mokiyoki.enhancedanimals.fabric.client;

import mokiyoki.enhancedanimals.fabric.platform.FabricNetworkHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

final class FabricClientNetworking {

    private FabricClientNetworking() {
    }

    static void registerReceivers(FabricNetworkHelper.Registrar registrar) {
        registrar.clientReceivers().forEach(FabricClientNetworking::register);
    }

    private static <T extends CustomPacketPayload> void register(FabricNetworkHelper.ClientReceiver<T> receiver) {
        ClientPlayNetworking.registerGlobalReceiver(receiver.type(),
                (payload, context) -> receiver.handler().handle(payload, FabricNetworkHelper.context(context.player(), context.client())));
    }
}
